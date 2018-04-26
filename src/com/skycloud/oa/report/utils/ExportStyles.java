/**
 * 
 * @Project:sop
 * @Title:ExportStyles.java
 * @Package:com.skycloud.oa.report.utils
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月4日 下午4:23:22
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * <p>导出样式</p>
 * @ClassName:ExportStyles
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月4日 下午4:23:22
 * 
 */
public class ExportStyles 
{
	/**
	 * 设置导出Excel一级表头样式
	 * @Title:ExportStyles
	 * @Description:
	 * @param workbook
	 * @return
	 * @Date:2015年11月4日 下午4:29:14
	 * @return:HSSFCellStyle 
	 * @throws
	 */
	public static HSSFCellStyle headStyles(HSSFWorkbook workbook)
	{
		HSSFFont headFont = workbook.createFont();
        headFont.setFontName("微软雅黑");
        headFont.setFontHeightInPoints((short)16);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        
        HSSFCellStyle headStyle = workbook.createCellStyle();
        headStyle.setFont(headFont);
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
		return headStyle;
	}
	
	/**
	 * 设置导出Excel二级表头
	 * @Title:ExportStyles
	 * @Description:
	 * @param workbook
	 * @return
	 * @Date:2015年11月4日 下午4:31:23
	 * @return:HSSFCellStyle 
	 * @throws
	 */
	public static HSSFCellStyle headTitles(HSSFWorkbook workbook)
	{
		HSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("微软雅黑");
        titleFont.setFontHeightInPoints((short)14);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        
        HSSFCellStyle headTitle = workbook.createCellStyle();
        headTitle.setFont(titleFont);
        headTitle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        headTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        return headTitle;
	}
	
	/**
	 * 设置导出Excel正常表头样式
	 * @Title:ExportStyles
	 * @Description:
	 * @param workbook
	 * @return
	 * @Date:2015年11月4日 下午4:32:46
	 * @return:HSSFCellStyle 
	 * @throws
	 */
	public static HSSFCellStyle normalTitles(HSSFWorkbook workbook)
	{
		HSSFFont normalFont = workbook.createFont();
        normalFont.setFontName("微软雅黑");
        normalFont.setFontHeightInPoints((short)12);
        
        HSSFCellStyle normalTitle = workbook.createCellStyle();
        normalTitle.setFont(normalFont);
        normalTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        normalTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        normalTitle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
        normalTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        normalTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        normalTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        
        return normalTitle;
	}
	
	/**
	 * 设置导出Excel正文样式
	 * @Title:ExportStyles
	 * @Description:
	 * @param workbook
	 * @return
	 * @Date:2015年11月4日 下午4:34:04
	 * @return:HSSFCellStyle 
	 * @throws
	 */
	public static HSSFCellStyle tabTitles(HSSFWorkbook workbook)
	{
		HSSFFont tabFont = workbook.createFont();
        tabFont.setFontName("微软雅黑");
        tabFont.setFontHeightInPoints((short)10);
        
        HSSFCellStyle tabTitle = workbook.createCellStyle();
        tabTitle.setFont(tabFont);
        tabTitle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        tabTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        tabTitle.setBorderRight((short) 1); 
        tabTitle.setBorderBottom((short) 1);
        tabTitle.setBorderLeft((short) 1);
        tabTitle.setBorderTop((short) 1);
        
        return tabTitle;
	}
	
	/**
	 * 设置导出Excel表尾样式
	 * @Title:ExportStyles
	 * @Description:
	 * @param workbook
	 * @return
	 * @Date:2015年11月4日 下午4:35:10
	 * @return:HSSFCellStyle 
	 * @throws
	 */
	public static HSSFCellStyle tailTitles(HSSFWorkbook workbook)
	{
		HSSFFont lastFont = workbook.createFont();
        lastFont.setFontName("微软雅黑");
        lastFont.setFontHeightInPoints((short)12);
        
        HSSFCellStyle lastTitle = workbook.createCellStyle();
        lastTitle.setFont(lastFont);
        lastTitle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        lastTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        return lastTitle;
	}
}