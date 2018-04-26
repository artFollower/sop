/**
 * @Title:OperationLog.java
 * @Package com.skycloud.oa.system.model
 * @Description TODO
 * @autor jiahy
 * @date 2016年8月26日下午3:14:14
 * @version V1.0
 */
package com.skycloud.oa.system.model;

/**
 * 操作日志
 * @ClassName OperationLog
 * @Description TODO
 * @author jiahy
 * @date 2016年8月26日下午3:14:14
 */
public class OperationLog {

	public Integer id;//id
	public Integer type;//
	public Integer operateType;// 
	public String preContent;//之前日志
	public String curContent;//操作后日志
	public Integer operationUserId;//操作人
	public Long operationTime;//操作时间
}
