package com.skycloud.oa.utils;
import java.io.BufferedInputStream;import java.io.BufferedOutputStream;import java.io.BufferedReader;import java.io.BufferedWriter;import java.io.File;import java.io.FileInputStream;import java.io.FileNotFoundException;import java.io.FileOutputStream;import java.io.IOException;import java.io.InputStream;import java.io.InputStreamReader;import java.io.OutputStream;import java.io.OutputStreamWriter;import java.nio.channels.FileLock;import java.util.ArrayList;import java.util.List;import java.util.Map;import javax.servlet.http.HttpServletRequest;import jcifs.smb.NtlmPasswordAuthentication;import jcifs.smb.SmbFile;import jcifs.smb.SmbFileOutputStream;import org.apache.log4j.Logger;import org.springframework.core.env.Environment;import org.springframework.web.multipart.MultipartFile;import com.skycloud.oa.config.Constant;import com.skycloud.oa.config.Global;public final class FileHelper {		private static Logger LOG = Logger.getLogger(FileHelper.class);		/**	 * 写入文件	 * @param file 文件	 * @param content 内容	 * @return	 */
	public static boolean writeFile(File file, String content) {
		return writeFile(file.getPath(), content, false);
	}		/**	 * 写入文件	 * @param filePath 文件路径	 * @param content 内容	 * @return	 */
	public static boolean writeFile(String filePath, String content) {
		return writeFile(filePath, content, false);
	}		/**	 * 写入文件	 * @param filePath	 * @param content	 * @param append	 * @return	 */
	public static boolean writeFile(String filePath, String content, boolean append) {
		try {
			File file = new File(filePath);
			FileOutputStream out = new FileOutputStream(file, append);
			OutputStreamWriter fwout = new OutputStreamWriter(out, Global.CHARSET);
			BufferedWriter bw = new BufferedWriter(fwout);
			FileLock fl = out.getChannel().tryLock();
			if (fl.isValid()) {
				bw.write(content);
				fl.release();
			}
			bw.flush();
			fwout.flush();
			out.flush();
			bw.close();
			fwout.close();
			out.close();
			bw = null;
			fwout = null;
			out = null;
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}		/**	 * 写文件	 * @param filePath	 * @param content	 * @param request	 * @return	 */
	public static boolean writeFile(String filePath, String content, HttpServletRequest request) {
		boolean flag = writeFile(filePath, content);
		if (!flag) {
			writeLog(request, "error", "File: " + filePath + " write error.");
		}
		return flag;
	}		/**	 * 记录日志	 * @param request	 * @param fileName	 * @param log	 */
	public static void writeLog(HttpServletRequest request, String fileName, String log) {				Map<String, Object> sConfig = (Map<String, Object>) (Map<String, Object>) Global.cloudConfig;
		int timestamp = (Integer) Common.time();
		String timeoffset = Common.getTimeOffset(sConfig);
		String onlineIP = Common.getOnlineIP(request);
		int supe_uid = 0;
		String requestURI = (String) request.getAttribute("requestURI");
		writeLog(timestamp, timeoffset, onlineIP, supe_uid, requestURI, fileName, log);
	}
		/**	 * 记录日志	 * @param timestamp	 * @param timeoffset	 * @param onlineIP	 * @param supe_uid	 * @param requestURI	 * @param fileName	 * @param log	 */
	public static void writeLog(int timestamp, String timeoffset, String onlineIP, int supe_uid,
			String requestURI, String fileName, String log) {
		char split = '\t';
		StringBuffer logContent = new StringBuffer();
		logContent.append(Common.gmdate("yyyy-MM-dd HH:mm:ss", timestamp, timeoffset));
		logContent.append(split);
		logContent.append(onlineIP);
		logContent.append(split);
		logContent.append(supe_uid);
		logContent.append(split);
		logContent.append(requestURI);
		logContent.append(split);
		logContent.append(log.trim().replaceAll("(\r\n|\r|\n)", " "));
		logContent.append('\n');
		String yearMonth = Common.gmdate("yyyyMM", timestamp, timeoffset);
		String logDir = Global.Root + "data/log/";
		File logDirFile = new File(logDir);
		if (!logDirFile.isDirectory()) {
			logDirFile.mkdirs();
		}
		String logFileName = logDir + yearMonth + "_" + fileName + ".log";
		File logFile = new File(logFileName);
		if (logFile.length() > 2048000) {
			File[] files = logDirFile.listFiles();
			int id = 0;
			int maxid = 0;
			for (File file : files) {
				String name = file.getName();
				if (name.matches("^" + yearMonth + "_" + fileName + "_(\\d)*\\.log$")) {
					id = Integer.valueOf(name.substring(name.lastIndexOf("_") + 1, name.lastIndexOf(".")));
					if (id > maxid) {
						maxid = id;
					}
				}
			}
			files = null;
			logDirFile = null;
			logFile.renameTo(new File(logDir + yearMonth + "_" + fileName + "_" + (maxid + 1) + ".log"));
		}
		writeFile(logFileName, logContent.toString(), true);
	}		/**	 * 分块写入文件	 * @param filePath	 * @param content	 * @param off	 * @param len	 * @return	 */
	public static boolean writeFile(String filePath, String content, int off, int len) {
		File file = new File(filePath);
		if (file.exists()) {
			FileOutputStream outputStream = null;
			OutputStreamWriter outputWriter = null;
			BufferedWriter bufWriter = null;
			try {
				outputStream = new FileOutputStream(filePath);
				outputWriter = new OutputStreamWriter(outputStream, Global.CHARSET);
				bufWriter = new BufferedWriter(outputWriter);
				FileLock fl = outputStream.getChannel().tryLock();
				if (fl.isValid()) {
					bufWriter.write(content, off, len);
					fl.release();
				}
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					bufWriter.close();
					outputWriter.close();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}		/**	 * 分块读取文件	 * @param file	 * @param len	 * @return	 */
	public static String readFile(File file, int len) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			byte datas[] = new byte[len];
			bis.read(datas, 0, len);
			return new String(datas,Global.CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}		/**	 * 读取路径读取	 * @param filePath	 * @return	 */
	public static String readFile(String filePath) {
		return readFile(new File(filePath));
	}		/**	 * 通过文件读取	 * @param file	 * @return	 */
	public static String readFile(File file) {
		StringBuffer content = new StringBuffer();
		if (file != null && file.exists()) {
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				fis = new FileInputStream(file);
				isr = new InputStreamReader(fis, Global.CHARSET);
				br = new BufferedReader(isr);
				String temp = null;
				while ((temp = br.readLine()) != null) {
					content.append(temp);
					content.append("\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null) {
						br.close();
					}
					if (isr != null) {
						isr.close();
					}
					if (fis != null) {
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content.toString().trim();
	}		/**	 * 把文件内容转为list	 * @param file	 * @return	 */
	public static List<String> readFileToList(File file) {
		List<String> lines = new ArrayList<String>();
		if (file != null && file.exists()) {
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				fis = new FileInputStream(file);
				isr = new InputStreamReader(fis, Global.CHARSET);
				br = new BufferedReader(isr);
				String temp = null;
				while ((temp = br.readLine()) != null) {
					lines.add(temp);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null) {
						br.close();
					}
					if (isr != null) {
						isr.close();
					}
					if (fis != null) {
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return lines;
	}		/**	 * 通过流的形式得到资源	 * @param resource	 * @return	 * @throws FileNotFoundException	 */
	public static InputStream getResourceAsStream(String resource) throws FileNotFoundException {
		String stripped = resource.startsWith("/") ? resource.substring(1) : resource;
		InputStream stream = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			stream = classLoader.getResourceAsStream(stripped);
		}
		if (stream == null) {
			stream = Environment.class.getResourceAsStream(resource);
		}
		if (stream == null) {
			stream = Environment.class.getClassLoader().getResourceAsStream(stripped);
		}
		if (stream == null) {
			throw new FileNotFoundException(resource + " not found");
		}
		return stream;
	}		/**	 * **************************************************************	* @Title: copy 	* @Description: 文件复制	* @param srcImgPath	* @param destPath	* @return void	* @author huadong huadong19890803@163.com	* @date 2012-3-4 	* @version V1.0	 */	public static void copy(String srcImgPath,String destPath) throws IOException{		File fileOld = new File(srcImgPath);		File fileNew = new File(destPath);		if(fileOld.exists()){			FileInputStream fis = new FileInputStream(fileOld);			FileOutputStream fos = new FileOutputStream(fileNew);			try {				int read = 0;				while ((read = fis.read()) != -1) {					fos.write(read);					fos.flush();				}			}finally{				fos.close();				fis.close();			}		}	}		public static String remote(String filePath) {		InputStream in = null;		OutputStream out = null;		try {			NtlmPasswordAuthentication npa = new NtlmPasswordAuthentication("",Global.cloudConfig.get("esb.message.user").toString(),Global.cloudConfig.get("esb.message.password").toString());			File localFile = new File(filePath);			SmbFile remoteFile = new SmbFile(Global.cloudConfig.get("esb.message.smb").toString()+localFile.getName(),npa);			in = new BufferedInputStream(new FileInputStream(localFile));			out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));			byte []buffer = new byte[1024];			while((in.read(buffer)) != -1){				out.write(buffer);				buffer = new byte[1024];			}			out.flush();		} catch (Exception e) {			LOG.error("上传报文错误",e);			return Constant.SYS_CODE_NETWORK;		}finally{			try {				out.close();				in.close();			} catch (IOException e) {				LOG.error("上传报文:关闭文件流错误",e);			}		}		return Constant.SYS_CODE_SUCCESS;	}		/**	 * 创建文件	 * @Description: TODO	 * @author xie	 * @date 2015年1月26日 下午5:05:59  	 * @param path	 * @return	 */	public static boolean createDir(String path) {		try {			File dir = new File(path);			if(!dir.exists()) {				dir.mkdirs();			}			return true;		}catch(Exception e) {			e.printStackTrace();		}		return false;	}		/**	 * 写文件	 * @Description: TODO	 * @author xie	 * @date 2015年4月21日 下午7:36:20  	 * @param file	 * @param path	 * @return	 */	public static boolean writeFile(MultipartFile file,String path) {		try {			FileOutputStream out = new FileOutputStream(new File(path));			out.write(file.getBytes());			out.flush();			out.close();			return true;		}catch(Exception e) {			return false;		}	}}