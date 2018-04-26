package com.skycloud.oa.config;


/**
 * 系统所有常量类
 * @ClassName: Constant 
 * @Description: TODO
 * @author xie
 * @date 2014年12月26日 上午10:55:00
 */
public class Constant {

	public static final String SESSION_KEY = "session";//session
	public static final String TOTALRECORD = "totalRecord";//总的记录数
	
	public static final String OWEN_TICKET = "SSO_OWEN_TICKET";//总的记录数

	/**用户状态 0：未激活；1：已激活，未登录；2：已登录过 */
	public static final Integer USER_STATUS_NOT_ACTIVE = 0;
	public static final Integer USER_STATUS_ACTIVE = 1;
	public static final Integer USER_STATUS_LOGGED = 2;

	public static final String EMPTY = "";

	/**
	 * 系统返回代码定义
	 */
	public static final String SYS_CODE_SYS_ERR = "9999";//系统错误
	public static final String SYS_CODE_DB_ERR = "9998";//数据库错误
	public static final String SYS_CODE_NULL_ERR = "9997";//空指针错误
	public static final String SYS_CODE_PARAM_NULL = "9996";//参数为空错误
	public static final String SYS_CODE_SUCCESS = "0000";//成功
	public static final String SYS_CODE_UNLOGIN = "9995";//未登录
	public static final String SYS_CODE_UNAUTHORIZED = "9994";//未登录
	public static final String SYS_CODE_PARAM_ERROR = "9993";//未登录
	public static final String SYS_CODE_TIME_OUT = "9992";//过期
	
	public static final String SYS_CODE_EMPTYUSER = "1000";//用户不存在
	public static final String SYS_CODE_PASSWORDERROR = "1001";//密码错误
	public static final String SYS_CODE_EXIT = "1002";//已存在
	public static final String SYS_CODE_DISABLED = "1003";//已禁用
	public static final String SYS_CODE_UNAUTHORIZE = "1004";//验证失败
	public static final String SYS_CODE_NOT_EXIT = "1005";//不存在
	public static final String SYS_CODE_CONFLICT = "1006";//冲突
	
	public static final String SYS_CODE_NETWORK = "9000";//网络问题
	
	public static final String PAGE_SUCCESS = "common/success";//成功
	public static final String PAGE_ERROR = "common/error";//失败
	
	public static final String METHOD_SELECT = "0";//失败
	public static final String METHOD_DO = "1";//失败
	
	public static final Integer ENCRYPTTIME = 2;

}
