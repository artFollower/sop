<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<script type="text/javascript">
	/* Init Metronic's core jquery plugins and layout scripts */
	$(document).ready(function() {
		config.initSidebar();
	});
</script>
<div class="page-sidebar navbar-collapse collapse">
	<!-- BEGIN SIDEBAR MENU -->
	<ul class="page-sidebar-menu" data-keep-expanded="false"
		data-auto-scroll="true" data-slide-speed="200">
		<!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->
		<!-- <li class="sidebar-search-wrapper">
			<form class="sidebar-search sidebar-search-bordered" method="POST">
				<a href="javascript:;" class="remove">
				<i class="icon-close"></i>
				</a>
				<div class="input-group">
					<input type="text" class="form-control" placeholder="搜索...">
					<span class="input-group-btn">
					<a href="javascript:;" class="btn submit"><i class="icon-magnifier"></i></a>
					</span>
				</div>
			</form> 
		</li>-->
		<li class="start"><a href="#/dashboard"> <i
				class="fa fa-desktop"></i> <span class="title">控制台</span>
		</a></li>
		<shiro:hasPermission name="MCONTRACT">
			<li><a href="javascript:;" class="base"> <i
					class="fa fa-suitcase"></i> <span class="title">合同管理</span> <span
					class="arrow "></span>
			</a>
				<ul class="sub-menu">
					<shiro:hasPermission name="MINTENT">
						<li><a href="#/instent"> <i class="fa  fa-files-o"></i> <span
								class="mar-l-5">意向管理</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MCONTRACT">
						<li><a href="#/contract"> <i class="fa fa-file-text-o"></i>
								<span class="mar-l-5">合同管理</span>
						</a></li>
					</shiro:hasPermission>

				</ul></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="MINBOUND">
			<li><a href="javascript:;" class="base"> <i
					class="fa fa-dribbble"></i> <span class="title">入库管理</span> <span
					class="arrow "></span>
			</a>
				<ul class="sub-menu">
					<shiro:hasPermission name="MARRIVALPLAN">
						<li><a href="#/arrival"> <i class="fa fa-indent"></i><span
								class="mar-l-5">入港计划</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MARRVIALFORESHOW">
				<li><a href="#/arrivalForeshow"> <i class="fa fa-indent"></i><span
								class="mar-l-5">船期预告</span>
						</a></li>
						</shiro:hasPermission>
					<shiro:hasPermission name="MINBOUNDWORK">
						<li><a href="#/inboundoperation"> <i class="fa fa-rebel"></i><span
								class="mar-l-5">入库作业</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MINBOUNDTRANSPORTWORK">
						<li><a href="#/inboundzhuanshu"> <i class="fa fa-rebel"></i><span
								class="mar-l-5">转输输入</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MPIERFEE">
						<li><a href="#/arrivalBill/list"> <i class="icon-puzzle"></i><span
								class="mar-l-5">码头规费</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MINSTORE">
						<li><a href="#/storage"> <i class="fa fa-paperclip"></i><span
								class="mar-l-5">入库确认</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MGOODSMANAGER">
					<li><a href="#/goodsManager"> <i class="fa fa-yelp"></i><span class="mar-l-5">货体管理</span>
						</a></li>
						</shiro:hasPermission>
						
						<li><a href="javascript:;" class="base"> <i
					class="fa fa-dribbble"></i> <span class="title">货批管理</span> 
					</a>
					<ul class="sub-menu">
					 <shiro:hasPermission name="MVIRCARGO">
						<li ><a href="#/virtualCargo"> <i class="fa fa-paperclip"></i><span
								class="mar-l-5">副库</span>
						</a></li>
					</shiro:hasPermission> 
					<shiro:hasPermission name="MPANKU">
						<li ><a href="#/cargoPK"> <i class="fa fa-paperclip"></i><span
								class="mar-l-5">盘库</span>
						</a></li>
					</shiro:hasPermission> 
					
					<shiro:hasPermission name="MCHANGEPRODUCT">
						<li ><a href="#/productChange"> <i class="fa fa-paperclip"></i><span
								class="mar-l-5">品名更换</span>
						</a></li>
					</shiro:hasPermission> 
					</ul>
					</li>
					
				</ul></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="MOUTBOUND">
			<li><a href="javascript:;" class="base"> <i
					class="fa fa-empire"></i> <span class="title">出库管理</span> <span
					class="arrow "></span>
			</a>
				<ul class="sub-menu">
						
					<shiro:hasPermission name="MLADING">
						<li><a href="#/lading"> <i class="fa fa-yelp"></i><span
								class="mar-l-5">提单管理</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MOUTBOUNDPLAN">
						<li><a href="#/shipArrival"> <i class="fa fa-indent"></i><span
								class="mar-l-5">出港计划</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MBILLING">
						<li><a href="#/invoice/list"> <i
								class="fa fa-credit-card"></i><span class="mar-l-5">发货开票</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MSHIPDELIVERY">
						<li><a href="#/outboundserial/list"> <i
								class="fa fa-life-bouy"></i><span class="mar-l-5">船舶出库</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MOUTBOUNDTRANSPORT">
						<li><a href="#/outboundzhuanshu/list"> <i
								class="fa fa-life-bouy"></i><span class="mar-l-5">转输输出</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MCARDELIVERY">
						<li><a href="#/outboundtruckserial/list"> <i
								class="fa fa-truck"></i><span class="mar-l-5">车发出库</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="WEIGHBRIDGE">
						<li><a href="#/weighBridge"> <i class="fa fa-truck"></i><span
								class="mar-l-5">车发称重</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="SHIPWEIGHBRIDGE">
					<li><a href="#/shipWeighBridge"> <i
								class="fa fa-ship"></i><span class="mar-l-5">船发计量</span>
						</a></li>
						</shiro:hasPermission>
				</ul></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="MACCOUNTMANAGE">
			<li><a href="javascript:;" class="base"> <i
					class="fa fa-empire"></i> <span class="title">台账管理</span> <span
					class="arrow "></span>
			</a>
				<ul class="sub-menu">
					<shiro:hasPermission name="MDISPATCHLOG">
						<li><a href="#/operationlog"> <i class="fa fa-th"></i><span
								class="mar-l-5">调度日志</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MSTORELEFGER">
						<li><a href="#/tanklog"> <i class="fa fa-list-alt"></i><span
								class="mar-l-5">储罐台账</span>
						</a></li>
					</shiro:hasPermission>
					 <shiro:hasPermission name="MSHIPDELIVERYMEASURE">
						<li><a href="#/shipDeliveryMeasure/list"> <i
								class="fa fa-yelp"></i><span class="mar-l-5">流量计台账</span>
						</a></li>
					</shiro:hasPermission> 
					<shiro:hasPermission name="MSHIPLEDGER">
						<li style="display: none"><a href="#/o_operationlog"> <i class="fa fa-tasks"></i><span
								class="mar-l-5">分流台账</span>
						</a></li>
					</shiro:hasPermission>
					<li><a href="#/tankmeasure/list"> <i class="fa fa-tasks"></i><span
								class="mar-l-5">油品参数表</span>
						</a></li>
						<shiro:hasPermission name="MCUSTOMSRELEASE">
						<li><a href="#/customsrelease/list"> <i class="fa fa-tasks"></i><span
								class="mar-l-5">海关放行表</span>
						</a></li>
						</shiro:hasPermission>
					<shiro:hasPermission name="WEIGHDAILYSTATEMENT">
						<li><a href="#/weighDailyStatement/list"> <i
								class="fa fa-tasks"></i><span class="mar-l-5">称重信息统计</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MDISPATCHLOGDETAIL">
						<li><a href="#/inboundbook/list"> <i
								class="fa fa-tasks"></i><span class="mar-l-5">调度日志明细表</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MSHIPLEDGERDETAIL">
						<li><a href="#/outboundbook/list"> <i
								class="fa fa-tasks"></i><span class="mar-l-5">分流台账明细表</span>
						</a></li>
					</shiro:hasPermission>
				</ul></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="MBILL">
		<li><a href="javascript:;" class="base"><i
					class="fa fa-folder-o"></i> <span class="title">账单管理</span><span
					class="arrow "></span>
			</a>
			<ul class="sub-menu">
			<shiro:hasPermission name="STORAGEFEE">
						<li><a href="#/storageFee"> <i class="fa fa-money"></i><span
								class="mar-l-5">仓储费</span>
						</a></li>
					</shiro:hasPermission>
			<shiro:hasPermission name="MFEEBILL"> 
			<li><a href="#/feebill"> <i class="fa  fa-list-alt "></i>
								<span class="mar-l-5">商务部账单</span>
						</a></li>
						 </shiro:hasPermission>
						<shiro:hasPermission name="MDOCKFEEBILL"> 
			<li><a href="#/dockfeebill"> <i class="fa  fa-list-alt "></i>
								<span class="mar-l-5">生产运行部账单</span>
						</a></li>
						 </shiro:hasPermission> 
			</ul>
			
			</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="MPLANMANAGER">
		<li><a href="javascript:;" class="base"><i
					class="fa fa-folder-o"></i> <span class="title">方案管理</span><span
					class="arrow "></span>
			</a>
			<ul class="sub-menu">
			<shiro:hasPermission name="MBERTHPROGRAM">
			<li><a href="#/berthplan"> <i class="fa  fa-list-alt "></i>
								<span class="mar-l-5">靠泊方案</span>
						</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="MTRANSPORTPROGRAM">
			<li><a href="#/unloadingplan"> <i class="fa  fa-list-alt "></i>
								<span class="mar-l-5">接卸方案</span>
						</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="MTANKPROGRAM">
			<li><a href="#/changetankplan"> <i class="fa  fa-list-alt "></i>
					<span class="mar-l-5">倒罐方案</span>
			</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="MBACKFLOWPROGRAM">
			<li><a href="#/backflowplan"> <i class="fa  fa-list-alt "></i>
								<span class="mar-l-5">打循环方案</span>
						</a></li>
						</shiro:hasPermission>
						
			</ul>
			
			</li>
			</shiro:hasPermission>
		<shiro:hasPermission name="MOTHERNOTICE">
			<li><a href="#/notify?item=-1" class="base"> <i
					class="fa fa-folder-o"></i> <span class="title">通知单</span>
			</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="MMESSAGE">
			<li><a href="javascript:;" class="base"> <i
					class="fa  fa-comments-o"></i> <span class="title">消息管理</span> <span
					class="arrow "></span>
			</a>
				<ul class="sub-menu">
					<shiro:hasPermission name="MMESSAGE">
						<li><a href="#/message"> <i class="fa  fa-comment"></i> <span
								class="mar-l-5">消息中心</span>
						</a></li>
					</shiro:hasPermission>
					
					<shiro:hasPermission name="MMESSAGE">
						<li><a href="#/approve"> <i class="fa  fa-comment"></i> <span
								class="mar-l-5">审批中心</span>
						</a></li>
					</shiro:hasPermission>
					
				</ul></li>
		</shiro:hasPermission>

		<shiro:hasPermission name="MSTATISTICS">
			<li><a href="javascript:;" class="base"> <i
					class="fa  fa-columns"></i> <span class="title">统计报表</span> <span
					class="arrow "></span>
			</a>
				<ul class="sub-menu">
					<shiro:hasPermission name="MCARGOLIST">
						<li><a href="#/cargolist"> <i class="fa  fa-list-alt "></i>
								<span class="mar-l-5">货批汇总</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MGOODSLIST">
						<li><a href="#/goodslist"> <i class="fa  fa-list"></i> <span
								class="mar-l-5">货体汇总</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MFENLEISELECT">
						<li><a href="#/loglist"> <i class="fa  fa-list"></i> <span
								class="mar-l-5">分类查询</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MQUERYDELIVERGOODS">
						<li><a href="#/invoicequery/list"> <i class="fa  fa-list"></i> <span
								class="mar-l-5">发货查询</span>
						</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="MPRODUCTIONREPORT">
					<li><a href="#/reportform/list"> <i class="fa  fa-list"></i> <span
								class="mar-l-5">生产报表</span>
						</a></li>
						</shiro:hasPermission>
				</ul></li>
		</shiro:hasPermission>


		<shiro:hasPermission name="MSYSCONFIG">
			<li><a href="javascript:;" class="base"> <i
					class="fa icon-settings"></i> <span class="title">系统管理</span> <span
					class="arrow "></span>
			</a>
				<ul class="sub-menu">
					<shiro:hasPermission name="MUSER">
						<li><a href="#/user/list"> <i class="fa fa-user"></i><span
								class="mar-l-5">用户管理</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MROLE">
						<li><a href="#/role/list"> <i class="fa fa-users"></i><span
								class="mar-l-5">角色管理</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MPERMISSION">
						<li><a href="#/resource/list"> <i
								class="fa fa-unlock-alt"></i><span class="mar-l-5">权限管理</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MORGANIZATION">
						<li><a href="#/organization/list"> <i
								class="fa fa-sitemap"></i><span class="mar-l-5">组织架构</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="BASEINFO">
						<li><a href="#/baseinfo/list?item=0"> <i
								class="fa fa-navicon "></i><span class="mar-l-5">基础信息</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="MOPERATELOG">
						<li><a href="#/sys/log"> <i class="fa fa-navicon "></i><span
								class="mar-l-5">系统日志</span>
						</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="sessionList">
						<li><a href="#/session"> <i class="fa fa-navicon "></i><span
								class="mar-l-5">在线用户</span>
						</a></li>
					</shiro:hasPermission>
				</ul></li>
		</shiro:hasPermission>
	</ul>
	<!-- END SIDEBAR MENU -->
</div>
