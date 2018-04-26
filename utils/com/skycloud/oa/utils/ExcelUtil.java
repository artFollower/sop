/**
 * 
 */
package com.skycloud.oa.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 *
 * @author jiahy
 * @version 2015年9月10日 下午1:39:28
 */
public class ExcelUtil {
	/**
	 * 模板Excel文件路径
	 */
	private String excelPath = "";
	private CallBack callBack;
	private CallBackSheets callBackSheets;
	public ExcelUtil(String excelPath,CallBack callBack) {
		super();
		this.excelPath = excelPath;
		this.callBack=callBack;
	}
	public ExcelUtil(String excelPath,CallBackSheets callBackSheets) {
		super();
		this.excelPath = excelPath;
		this.callBackSheets=callBackSheets;
	}
	public ExcelUtil() {
		super();
	}

	public interface CallBack{
		public void setSheetValue(HSSFSheet sheet);
	};
	public interface CallBackSheets{
		public void setSheetsValue(HSSFWorkbook workbook );
	};
	public HSSFWorkbook  getWorkbook(){
	        try {
	            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excelPath));
	            HSSFSheet sheet = workbook.getSheetAt(0);
	            callBack.setSheetValue(sheet);  
	            return workbook;
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			return null;
	} 
	
	public HSSFWorkbook  getWorkbookWithSheets(){
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excelPath));
            callBackSheets.setSheetsValue(workbook);  
            return workbook;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
} 

	/**
	 *@author jiahy
	 * @param args
	 */
	public static void main(String[] args) {
//		ExcelUtil excelUtil=new ExcelUtil("exceedfee.xls",new CallBack() {
//			@Override
//			public void setSheetValue(HSSFSheet sheet) {
//				sheet.getRow(1).getCell(1).setCellValue("dddd");
//			}
//		});
//		  FileOutputStream out = null;
//          try {
//              out = new FileOutputStream("exceedfee1.xls");
//              excelUtil.getWorkbook().write(out);
//          } catch (IOException e) {
//              e.printStackTrace();
//          } finally {
//              try {
//                  out.close();
//              } catch (IOException e) {
//                  e.printStackTrace();
//              }
//          }
//		
//		
//		System.out.println(String.format(" %02d", 2));
//		System.out.println(String.format(" %02d", "12"));
//		//获取月份天数

//			Calendar rightNow = Calendar.getInstance();
//
//			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM"); //如果写成年月日的形式的话，要写小d，如："yyyy/MM/dd"
//
//			
//			try {
//				rightNow.setTime(simpleDate.parse("2016-02".replace("-", "/")));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} //要计算你想要的月份，改变这里即可
//
//			
//
//			System.out.println(rightNow.getActualMaximum(Calendar.DAY_OF_MONTH));
		
//            System.out.println((char)(65));
		String str="=SUMPRODUCT(MOD(COLUMN(C4:K4),2),C4:K4)";
		if(5>1){
			for(int i=1;i<5;i++){
				if(i!=5-1){
				str+=String.valueOf((char)(2*i+66))+""+4+",";
				}else{
					str+=String.valueOf((char)(2*i+66))+""+4+")";
				}
			}
			System.out.println(str);
			System.out.println(String.valueOf((char)(67)));
		}
	}
}
