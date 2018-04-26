package com.skycloud.oa.config;

public interface C {

	interface AUTH {
		// 用户类型
		String USER_CATEGORY_EMPLOYEE = "EMPLOYEE_USER";// 雇员
		String USER_CATEGORY_CLIENT = "CLIENT_USER";// 客户

		// 组织类型
		String PERTIES_CATEGORY_COMPANY = "COMPANY";// 公司
		String PERTIES_CATEGORY_DEPARTMENT = "DEPARTMENT";// 部门
		String PERTIES_CATEGORY_JOB = "JOB";// 职位
		String PERTIES_CATEGORY_POST = "POST";// 岗位
		String PERTIES_CATEGORY_EMPLOYEE = "EMPLOYEE";// 员工

		// 安全资源类型
		String SECURITY_CATEGORY_MENU = "MENU_RESOURCE";
		String SECURITY_CATEGORY_PAGE_ELEMENT = "PAGE_ELEMENT_RESOURCE";
		String SECURITY_CATEGORY_URL_ACCESS = "URL_ACCESS_RESOURCE";

		// 组织关系类型
		String ORGANIZATION_CATEGORY_ORGANIZATIONLINEMANAGEMENT = "ORGANIZATIONLINEMANAGEMENT";
		String ORGANIZATION_CATEGORY_EMPLOYEEPOSTHOLDING = "EMPLOYEEPOSTHOLDING";
	}

	interface ERROR_CODE {
		// String EMPTYUSER = "1000";//用户不存在
		// String PASSWORDERROR = "1001";//密码错误
		// String EXIT = "1002";//已存在
		// String DISABLED = "1003";//已禁用
		// String UNAUTHORIZE = "1004";//验证失败
		//
		// String SYSTEMERROR = "9999";//系统错误
		// String DBERROR = "9998";//数据库错误
	}

	interface LOG_TYPE {
		String CREATE = "1";// 添加1
		String UPDATE = "2";// 更新1
		String DELETE = "3";// 删除1
		String VERIFY = "4";// 审核1
		String LOGIN = "5";// 登陆1
		String LOGOUT = "6";// 退出1
		String UPLOAD = "7";// 上传1
		String CONFIRM = "8";// 确认1
		String MERGE = "9"; // 合并1
		String SPLIT = "10";// 拆分1
		String QUERY = "11";// 查询1
		String GRANT = "12";// 分配1
		String RECOVER = "13";// 回收1
		String LOSS = "14";// 扣损1
		String GRAFT = "15";// 嫁接1
		String AGAINST = "16";// 冲销1
		String REBACK = "17";// 回退
		String SAVEORUPDATE = "18";// 保存或更新
		String SEND="19";//发送
	}

	interface LOG_OBJECT {
		String AUTH_USER = "auth_user";// 用户1
		String AUTH_ROLE = "auth_role";// 角色1
		String AUTH_POWER = "auth_power";// 权限1
		String AUTH_ORG = "auth_org";// 组织架构1
		String AUTH_EMPLOYEE = "auth_employee";// 组织架构

		String ESB_HARBOR = "esb_harbor";// 港务动态信息1
		String ESB_SHIP = "esb_ship";// 港务动态船舶信息1
		String ESB_BILL = "esb_bill";// 港务动态提单信息1
		String ESB_HEAD = "esb_head";// 港务动态报文头信息1

		String PCS_INTENTION = "pcs_intention";// 合同意向1
		String PCS_ORDER = "pcs_order";// 合同1

		String PCS_INBOUND_ARRIVAL = "pcs_arrival";// 入库到港1
		String PCS_INBOUND_ARRIVALPLAN = "pcs_inbund_arrivalplan";// 到港计划1
		String PCS_OPERATIONLOG = "pcs_operationlog";// 调度日志1
		String PCS_TANKLOG = "pcs_tanklog";// 储罐台账1
		String PCS_CARGO = "pcs_cargo";// 货批1
		String PCS_GOODS = "pcs_goods";// 货体1
		String PCS_LADING = "pcs_lading";// 提单1
		String PCS_PREDICTGOODS = "pcs_predictgoods";// 预入库货体1
        //验证码
		String PCS_SECURITYCODE="pcs_securitycode";//验证码
		//入库
		String PCS_INBOUND_ALLITEMS = "pcs_inbound_allitems";// 入库添加所有相关1
		String PCS_INBOUND_ARRIVAL_INFO = "pcs_inbound_arrival_info";// 入库附加信息1
		String PCS_INBOUND_BERTH_ASSESS = "pcs_inbound_berth_assess";// 入库靠泊评估1
		String PCS_INBOUND_BERTH_PROGRAM = "pcs_inbound_berth_program";// 入库靠泊方案1
		String PCS_INBOUND_TRANSPORT_PROGRAM = "pcs_inbound_transport_program";// 入库接卸方案1
		String PCS_INBOUND_WORK = "pcs_inbound_work";// 入库作业1
		String PCS_INBOUND_WORK_CHECK = "pcs_inbound_work_check";// 岗位检查1
		String PCS_INBOUND_JOB_CHECK = "pcs_inbound_job_check";// 工作检查1
		String PCS_INBOUND_NOTIFY = "pcs_inbound_notify";// 通知单1
		String PCS_INBOUND_TRANS = "pcs_inbound_trans";// 管线表1
		String PCS_INBOUND_STORE = "pcs_inbound_store";// 存储罐1
		String PCS_INBOUND_TRANSPORT_INFO = "pcs_inbound_info";// 入库接卸附加信息1
		String PCS_INBOUND_CARGO_GOODS = "pcs_inbound_cargo_goods";// 入库货品货体1
		String PCS_MORE_INBOUND_PROGRAM = "pcs_more_inbound_program";// 多次接卸-
		String PCS_MORE_BACK_FLOW_PROGRAM = "pcs_more_back_flow_program";// 多次打循环-
		String PCS_BACKFLOW_PLAN="pcs_backFlow_plan";//倒罐方案
		// 仓储费
		String PCS_DOCKFEE = "pcs_dockfee";// 码头规费-
		String PCS_EXCEEDFEE = "pcs_exceedfee";// 超期费
		String PCS_INITIALFEE = "pcs_initialfee";// 仓储费
		String PCS_OTHERFEE = "pcs_otherfee";// 其他费用
		String PCS_FEECHARGE = "pcs_feecharge";// 费用项（商务部）
		String PCS_DOCKFEECHARGE = "pcs_dockfeecharge";// 费用项（生产运行部）
		// 账单
		String PCS_FEEBILL = "pcs_feebill";// 商务部账单
		String PCS_DOCKFEEBILL = "pcs_dockfeebill";// 生产运行部部账单
		String PCS_ACCOUNTBILLLOG = "pcs_accountbilllog";// 到账历史纪录
		// 出港计划
		String PCS_OUTARRIVAL = "pcs_outarrival";// 出港计划1
		String PCS_OUTARRIVAL_ITEM = "pcs_outarrival_item";// 出港计划出港明细
		// 船舶出库
		String PCS_OUTBOUND_ARRIVALINFO = "pcs_outbound_arrivalinfo";// 出库作业计划-
		String PCS_OUTBOUND_BASEINFO = "pcs_outbound_baseinfo";// 出库列表信息
		String PCS_OUTBOUND_BERTHASSESS = "pcs_outbound_berthassess";// 出库靠泊评估
		String PCS_OUTBOUND_BERTHPROGRAM = "pcs_outbound_berthprogram";// 出库靠泊方案
		String PCS_OUTBOUND_TRANSPORTPROGRAM = "pcs_outbound_transportprogram";// 出库发货准备
		String PCS_OUTBOUND_AMOUNTAFFIRM = "pcs_outbound_amountaffirm";// 出库数量确认
		// 流量计台账
		String PCS_OUTDELIVERYMEASURE = "pcs_outdeliverymeasure"; // 流量计台账
		// 发货冲销
		String PCS_TRADEINVOICE = "pcs_tradeinvoice";// 发货冲销
		// 车发出库
		String PCS_TRAIN = "pcs_train";// 车发出库1
        // 称重
		String PCS_WEIGHTBRIDGE="pcs_weightbridge";//车发称重
		String PCS_SHIPWEIGHBRIDGE="pcs_shipweighbridge";//船发计量
		//海关放行
		String PCS_CUSTOMSRELEASE = "pcs_customsrelease";// 海关放行统计-
		// 
		String PCS_GOODSLOG = "pcs_goodslog";// 开票记录1

		String PCS_WEIGHBRIDGE = "pcs_weighbridge"; // 称重1
		String PCS_DELIVERYMEASURE = "pcs_deliverymeasure";// 船发流量计量1
		String PCS_DELIVERYSTATEMENT = "pcs_deliverystatement";// 报表1
		String PCS_ARRIVALBILL = "pcs_arrivalbill"; // 码头规费1
		String PCS_OUTARRIVALINFO = "pcs_outarrivalinfo"; // 船发计划1
		String PCS_OUTTRANSPORT = "pcs_outtransport";// 作业计划 工艺流程图1
		String PCS_OUTWORKCHECK = "pcs_workcheck"; // 检查人 作业人1
		String PCS_OUTBERTHASSESS = "pcs_outberthassess"; // 靠泊方案1
		String PCS_OUTSTORE = "pcs_outstore"; // 罐信息1
		String PCS_OUTWORK = "pcs_outwork";// 作业任务1
		String PCS_OUTBERTHPROGRAM = "pcs_outberthprogram";// 靠泊评估1
		String PCS_OUTBERTH = "pcs_outberth"; // 泊位信息1
		String PCS_OUTFILEUPLOADINFO = "pcs_outfileuploadinfo";// 文件上传1
		String PCS_MEASURE = "pcs_measure"; // 流量计1
		String PCS_STORAGEFEE = "pcs_storagefee";// 仓储费1

		String PCS_PRODUCT = "t_pcs_product";// 货品1
		String PCS_PUMP = "t_pcs_pump"; // 泵1
		String PCS_PORT = "t_pcs_port"; // 港口1
		String PCS_PARK = "t_pcs_park"; // 车位1
		String PCS_QUALIFICATION = "t_pcs_qualification"; // 资质类型1
		String PCS_INSPECT_AGENT = "t_pcs_inspect_agent"; // 商检单位1
		String PCS_CLIENT_QUALIFICATION = "t_pcs_client_qualification"; // 客户资质表1
		String PCS_CLIENT = "t_pcs_client"; // 客户资料1
		String PCS_CLIENT_GROUP = "t_pcs_client_group"; // 客户资料1
		String PCS_CERTIFY_AGENT = "t_pcs_certify_agent"; // 开证单位1
		String PCS_CARGO_AGENT = "t_pcs_cargo_agent"; // 货物代理
		String PCS_BERTH = "t_pcs_berth"; // 泊位表1
		String PCS_SHIP = "t_pcs_ship"; // 船舶1
		String PCS_SHIP_AGENT = "t_pcs_ship_agent"; // 船代1
		String PCS_TANK = "t_pcs_tank"; // 货罐1
		String PCS_TRUCK = "t_pcs_truck"; // 车辆1
		String PCS_TUBE = "t_pcs_tube"; // 管线1
		String PCS_PUMP_SHED="t_pcs_pump_shed";//泵棚
		

		// 补充
		String PCS_INBOUND_ARRIVALWORK = "pcs_inbund_arrivalwork";// 到港联系1
		String PCS_INBOUND_ARRIVALFORESHOW = "pcs_inbund_arrivalforeshow";// 船期预告1
		String PCS_GOODSHISTORY = "pcs_goodshistory";// 嫁接历史1
		String PCS_OPERATIONLOGNOTIY = "pcs_operationlognotiy";// 调度日志验证信息1
		String PCS_OPERATIONLOGWEATHER = "pcs_operationlogweather";// 调度日志天气预报1
		String PCS_NOTICELOG = "pcs_noticelog";// 通知记录
	}

}
