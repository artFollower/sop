package com.skycloud.oa.statistics.quartz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.skycloud.oa.config.Global;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.model.EveryDayStatics;
import com.skycloud.oa.statistics.dto.StatisticsDto;
import com.skycloud.oa.statistics.service.StatisticsService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.HibernateUtils;
import com.skycloud.oa.utils.OaMsg;

/**
 * <一句话功能简述>串口读取数据任务<br/>
 * <功能详细描述>如果一旦发现任务不在运行或者运行失败，则立即重启任务，再重新读取当前数据
 * 
 * @author  Administrator
 * @version  [版本号, 2015-3-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DownLoadExcel
{
    private static Logger LOG = Logger.getLogger(DownLoadExcel.class);
    
    private static boolean isRunning = false;
    @Autowired
    private StatisticsService statisticsService;
    
    private static String mUrl="/opt/tomcat/webapps/sop/resource/statistics";
//    private static String mUrl="F:/apache-tomcat-7.0.6/webapps/mSop/resource/statistics";
    
    
    
    public void run()
    {

        LOG.debug("start CommJobTimer job...");
        if (!isRunning)
        {
            isRunning = true;
            try
            {
                execCargo();
                execGoods();
                Session session = HibernateUtils.getCurrentSession();
    			if (session != null) {
    				session.getTransaction().commit();
    			}
            }
            catch (Exception e)
            {
                LOG.debug("exec CommJobTimer job fail", e);
            }
            finally
            {
                isRunning = false;// 执行完毕
                LOG.debug("end CommJobTimer job");
            }
        }
        else
        {
            LOG.debug("the pre CommJobTimer job is running, exit this job.");
        }
        
    

        
    }
	private void execGoods() {
//		OutputStream fOut = null;
		FileOutputStream fos1 = null; 
		try 
		{
			// 产生工作簿对象
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			// 进行转码，使其支持中文文件名 type 1-10 是出库用 20开始入库用
//			response.setHeader("content-disposition", "attachment;filename=" + new String(name.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
//			response.setHeader("content-disposition", "attachment;filename=" + sdf.format(new Date()) + ".xls");
			StatisticsDto statisticsDto=new StatisticsDto();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date date = calendar.getTime();
			
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			  statisticsDto.setsStartTime(formatter.format(date));
			  statisticsDto.setsEndTime(formatter.format(date));
			  statisticsDto.setShowVirTime(1);
			  
			  
			if(!Common.empty(statisticsDto.getsStartTime())){
				long startTime;
				try {
					startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
					statisticsDto.setStartTime(startTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!Common.empty(statisticsDto.getsEndTime())){
				long endTime;
				try {
					endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
					statisticsDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		OaMsg msg=statisticsService.getGoods(statisticsDto, new PageView(0,0),false);
		HSSFWorkbook workbook=statisticsService.exportGoodsExcel(msg,2);
			
			
			
			
			
			byte[] bytes =workbook.getBytes();
			  File dirFile = new File(mUrl);
//			  File dirFile = new File("F:/apache-tomcat-7.0.6/webapps/mSop/resource/statistics");
	            if(!dirFile.exists()){
	                if(!dirFile.mkdir())
						try {
							throw new Exception("目录不存在，创建失败！");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            }
	            String url=mUrl+"/"+statisticsDto.getsEndTime()+"_g.xls";  
//	            String url="F:/apache-tomcat-7.0.6/webapps/mSop/resource/statistics/"+statisticsDto.getsEndTime()+".xls";  
	            fos1 = new FileOutputStream(url);    
	            workbook.write(fos1);
	            
	            EveryDayStatics everyDayStatics=new EveryDayStatics();
	            everyDayStatics.setType(1);
	            everyDayStatics.setUrl("/resource/statistics/"+statisticsDto.getsEndTime()+"_g.xls" );
	            everyDayStatics.setTime(statisticsDto.getsEndTime());
	            OaMsg om=statisticsService.addEveryDayStatics(everyDayStatics);
	            
	            
	            
//				fos.write(bytes); 
		} 
		catch (Exception e) 
		{
		
		} 
		finally 
		{
			try 
			{
				fos1.flush();
				fos1.close();
			} 
			catch (IOException e) 
			{
				
			}
		}
	}
	private void execCargo() {
//		OutputStream fOut = null;
		FileOutputStream fos = null; 
		try 
		{
			// 产生工作簿对象
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			// 进行转码，使其支持中文文件名 type 1-10 是出库用 20开始入库用
//			response.setHeader("content-disposition", "attachment;filename=" + new String(name.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
//			response.setHeader("content-disposition", "attachment;filename=" + sdf.format(new Date()) + ".xls");
			StatisticsDto statisticsDto=new StatisticsDto();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date date = calendar.getTime();
			
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			  statisticsDto.setsStartTime(formatter.format(date));
			  statisticsDto.setsEndTime(formatter.format(date));
			  statisticsDto.setIsFinish(0);
			  
			  
			if(!Common.empty(statisticsDto.getsStartTime())){
				long startTime;
				try {
					startTime = sdf.parse(statisticsDto.getsStartTime()).getTime() / 1000;
					statisticsDto.setStartTime(startTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!Common.empty(statisticsDto.getsEndTime())){
				long endTime;
				try {
					endTime = sdf.parse(statisticsDto.getsEndTime()).getTime() / 1000;
					statisticsDto.setEndTime(endTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		OaMsg msg=statisticsService.getCargo(statisticsDto,  new PageView(0,0));
			
			
			HSSFWorkbook workbook=statisticsService.exportCargoExcel(msg,1,null,statisticsDto);
			
			
			
			
			byte[] bytes =workbook.getBytes();
			  File dirFile = new File(mUrl);
//			  File dirFile = new File("F:/apache-tomcat-7.0.6/webapps/mSop/resource/statistics");
	            if(!dirFile.exists()){
	                if(!dirFile.mkdir())
						try {
							throw new Exception("目录不存在，创建失败！");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            }
	            String url=mUrl+"/"+statisticsDto.getsEndTime()+"_c.xls";  
//	            String url="F:/apache-tomcat-7.0.6/webapps/mSop/resource/statistics/"+statisticsDto.getsEndTime()+".xls";  
	            fos = new FileOutputStream(url);    
	            workbook.write(fos);
	            
	            EveryDayStatics everyDayStatics=new EveryDayStatics();
	            everyDayStatics.setType(2);
	            everyDayStatics.setUrl("/resource/statistics/"+statisticsDto.getsEndTime()+"_c.xls" );
	            everyDayStatics.setTime(statisticsDto.getsEndTime());
	            OaMsg om=statisticsService.addEveryDayStatics(everyDayStatics);
	            
	            
//				fos.write(bytes); 
		} 
		catch (Exception e) 
		{
		
		} 
		finally 
		{
			try 
			{
				fos.flush();
				fos.close();
			} 
			catch (IOException e) 
			{
				
			}
		}
	}
    
}
