/**
 * @Title:ExcelOutputUtil.java
 * @Package com.skycloud.oa.utils
 * @Description TODO
 * @autor jiahy
 * @date 2016年9月9日上午9:29:39
 * @version V1.0
 */
package com.skycloud.oa.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @ClassName ExcelOutputUtil
 * @Description TODO
 * @author jiahy
 * @date 2016年9月9日上午9:29:39
 */
public class ExcelOutputUtil {

	public interface CallBack {
		public HSSFWorkbook getWorkBook(HttpServletRequest request);
	}

	public static void handleExcelOutput(HttpServletRequest request, HttpServletResponse response,CallBack callBack) {
		response.setContentType("application/vnd.ms-excel");
		OutputStream fOut = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String file_name=sdf.format(new Date());
		try {
			if(request.getParameter("name")!=null)
			 file_name =URLDecoder.decode(request.getParameter("name") , "utf-8");
			System.out.println(file_name);
			response.setHeader("content-disposition", "attachment;filename=" + new String(file_name.getBytes(),"iso-8859-1") + ".xls");
			HSSFWorkbook workbook = callBack.getWorkBook(request);
			fOut = response.getOutputStream();
			if (fOut != null) {
				workbook.write(fOut);
			} else {
			}
		} catch (Exception e) {

		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
			}
		}
	}
}
