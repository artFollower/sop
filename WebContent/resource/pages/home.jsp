<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[if IE 8]> <html lang="en" class="ie8 no-js" data-ng-app="MetronicApp"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js" data-ng-app="MetronicApp"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" data-ng-app="MetronicApp">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<title>江苏长江石油化工有限公司业务管理系统</title>

<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />

<!-- BEGIN GLOBAL MANDATORY STYLES -->

<link href="<%=basePath %>/resource/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/resource/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<!-- <link href="<%=basePath %>/resource/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" /> -->
<link href="<%=basePath %>/resource/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN DYMANICLY LOADED CSS FILES(all plugin and page related styles must be loaded between GLOBAL and THEME css files ) -->
<link id="ng_load_plugins_before" />
<!-- END DYMANICLY LOADED CSS FILES -->

<!-- BEGIN THEME STYLES -->
<!-- DOC: To use 'rounded corners' style just load 'components-rounded.css' stylesheet instead of 'components.css' in the below style tag -->
<link href="<%=basePath %>/resource/global/css/components-rounded.css" id="style_components" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/resource/global/css/plugins.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/admin/layout/css/layout.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/admin/layout/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="<%=basePath %>/resource/admin/layout/css/custom.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/admin/pages/css/style.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/resource/admin/pages/css/timeline-old.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/plugins/jquery.treegrid/css/jquery.treegrid.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/plugins//listnav.css" rel="stylesheet" type="text/css" />

<link href="<%=basePath %>/resource/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->

<!-- BEGIN PLUGN STYTLE -->
<link href="<%=basePath %>/resource/global/plugins/bootstrap-datepicker/css/datepicker.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/plugins/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/plugins/bootstrap-colorpicker/css/colorpicker.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/plugins/message/tasks.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/plugins/message/todo.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/plugins/select2/select2.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/resource/global/css/plugins.css" rel="stylesheet" type="text/css" />
<!-- END PLUGN STYTLE -->

<link rel="shortcut icon" href="<%=basePath%>/resource/pages/favicon.ico" />
<script src="<%=basePath %>/resource/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="<%=basePath %>/resource/global/plugins/bootstrap-toastr/toastr.min.js"></script>
<script src="<%=basePath %>/resource/admin/js/config.js" type="text/javascript"></script>
<script type="text/javascript">
	mxBasePath = '<%=request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ request.getContextPath() %>/resource/global/plugins/mxgraph/src';
	$(function() {
		config.setDomain('<%=basePath %>');
		config.setResource('<%=basePath %>/resource');
		$.post(config.getDomain()+"/auth/user/getPermission", "id=<shiro:principal property='id'/>").done(function(data) {
            if(data.code == '0000'){
            	config.setPermission(data.data);
            }
        });
		toastr.options = {
				  "closeButton": true,
				  "debug": false,
				  "positionClass": "toast-bottom-right",
				  "onclick": null,
				  "showDuration": "1000",
				  "hideDuration": "1000",
				  "timeOut": "5000",
				  "extendedTimeOut": "1000",
				  "showEasing": "swing",
				  "hideEasing": "linear",
				  "showMethod": "fadeIn",
				  "hideMethod": "fadeOut"
				}
	});
</script>
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body id="homePage" ng-controller="AppController" class="page-header-fixed page-sidebar-closed-hide-logo page-quick-sidebar-over-content page-on-load" ng-class="{'page-container-bg-solid': settings.layout.pageBodySolid, 'page-sidebar-closed': settings.layout.pageSidebarClosed}" ng-data="<shiro:principal property='id'/>">

	<!-- BEGIN PAGE SPINNER -->
	<div ng-spinner-bar class="page-spinner-bar">
		<div class="bounce1"></div>
		<div class="bounce2"></div>
		<div class="bounce3"></div>
	</div>
	<!-- END PAGE SPINNER -->

	<!-- BEGIN HEADER -->
	<div data-ng-include="'<%=basePath %>/resource/admin/tpl/header.jsp'" data-ng-controller="HeaderController" class="page-header navbar navbar-fixed-top"></div>
	<!-- END HEADER -->

	<div class="clearfix"></div>

	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<div data-ng-include="'<%=basePath %>/resource/admin/tpl/sidebar.jsp'" data-ng-controller="SidebarController" class="page-sidebar-wrapper"></div>
		<!-- END SIDEBAR -->

		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<div class="page-content clearfix">
				<!-- BEGIN STYLE CUSTOMIZER(optional)
				<div data-ng-include="'tpl/theme-panel.html'" data-ng-controller="ThemePanelController" class="theme-panel hidden-xs hidden-sm"></div> -->
				<!-- END STYLE CUSTOMIZER -->
				<!-- BEGIN PAGE HEADER green-meadow-->
				<div class="portlet page-bar">
					<ul class="page-breadcrumb">
						<li class="base"><i class="fa fa-home"></i> <a href="#/dashboard">控制台</a></li>
					</ul>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN ACTUAL CONTENT -->
				<div ui-view class="fade-in-up"></div>
				<!-- END ACTUAL CONTENT -->
			</div>
		</div>
		<!-- END CONTENT -->

		<!-- BEGIN QUICK SIDEBAR -->
		<a href="javascript:;" class="page-quick-sidebar-toggler"><i class="icon-close"></i></a>
		<div data-ng-include="'<%=basePath %>/resource/admin/tpl/quick-sidebar.jsp'" data-ng-controller="QuickSidebarController" class="page-quick-sidebar-wrapper"></div>
		<!-- END QUICK SIDEBAR -->
	</div>
	<!-- END CONTAINER -->

	<!-- BEGIN FOOTER -->
	<div data-ng-include="'<%=basePath %>/resource/admin/tpl/footer.html'" data-ng-controller="FooterController" class="page-footer"></div>
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<iframe class="hidden logout-frame"></iframe>
	<!-- BEGIN CORE JQUERY PLUGINS -->
	<!--[if lt IE 9]>
	<script src="<%=basePath %>/resource/global/plugins/respond.min.js"></script>
	<script src="<%=basePath %>/resource/global/plugins/excanvas.min.js"></script> 
	<![endif]-->
	<script src="<%=basePath %>/resource/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/jquery.form.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/jquery-slimscroll/jquery.slimscroll.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<!-- <script src="<%=basePath %>/resource/global/plugins/uniform/jquery.uniform.js" type="text/javascript"></script> -->
	<script src="<%=basePath %>/resource/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
	<!-- END CORE JQUERY PLUGINS -->

	<!-- BEGIN CORE ANGULARJS PLUGINS -->
	<script src="<%=basePath %>/resource/global/plugins/angularjs/angular.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/angularjs/angular-sanitize.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/angularjs/angular-touch.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/angularjs/plugins/angular-ui-router.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/angularjs/plugins/ocLazyLoad.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/angularjs/plugins/ui-bootstrap-tpls.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/angularjs/plugins/ui-bootstrap-tpls.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/pages/scripts/components-pickers.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/bootstrap/js/bootstrap-tooltip.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/bootstrap/js/bootstrap-popover.js" type="text/javascript"></script>	 
	
	<script src="<%=basePath %>/resource/admin/js/skycloud-ui.plugin.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/treeTable/jquery.treeTable.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/bootstrap-select/bootstrap-select.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/moment.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/fullcalendar/fullcalendar.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/fullcalendar/lang-all.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/typeahead/bootstrap2-typeahead.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/typeahead/underscore.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/jstree/dist/jstree.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/mxgraph/src/js/mxClient.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/skycloud.message.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/message/tasks.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/message/todo.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/xdate.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/select2/select2.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/bootstrap-switch/js/bootstrap-switch.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/scripts/highcharts/js/highcharts.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/scripts/highcharts/js/modules/exporting.js" type="text/javascript"></script>
	
	
	<!-- END CORE ANGULARJS PLUGINS -->

	<!-- BEGIN APP LEVEL ANGULARJS SCRIPTS -->
	<script src="<%=basePath %>/resource/admin/js/app.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/directives.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/jquery.pulsate.min.js" type="text/javascript"></script>
	<!-- END APP LEVEL ANGULARJS SCRIPTS -->

	<!-- BEGIN APP LEVEL JQUERY SCRIPTS -->
	<script src="<%=basePath %>/resource/global/scripts/metronic.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/layout/scripts/layout.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>

	<script src="<%=basePath %>/resource/global/plugins/jquery.tinyscrollbar.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/scripts/dist/echarts.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/jquery.treegrid/js/jquery.treegrid.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/system/print.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/scripts/jquery.jqprint-0.3.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/scripts/skycloud.treeTable.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/fuelux/js/spinner.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/global/plugins/jquery-mixitup/jquery.mixitup.min.js" type="text/javascript"></script>
	<!-- END APP LEVEL JQUERY SCRIPTS -->

	<!-- 模块中的js脚本开始-->
	<script src="<%=basePath %>/resource/admin/js/scripts/feebill/storageFee.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/feebill/initialFee.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/feebill/oilsFee.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/feebill/feebill.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/feebill/dockfeebill.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/feebill/dockfeebillDialog.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/feebill/dockFee.js" type="text/javascript"></script>
	
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/arrivalBill.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/contract/intent.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/contract/contract.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/arrival.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/storage.js" type="text/javascript"></script>
	
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/lading.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/outArrival.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/vehicleDeliveryStatement.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/invoice.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/invoiceSyncFlowMeter.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/outBound.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/weighBridge.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/list_truckserial.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/listoperation.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/deliverPlan.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/shipDeliveryMeasure.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/shipflowbook/shipFlowBook.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/auth/user.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/auth/role.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/auth/resource.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/auth/organization.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/inboundoperation.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/inboundOperation/inboundPrint.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/notify.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/notifyList.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/util.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/listoperationlog.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/tanklog.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/itemoperationlog.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/message/message.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/flow.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/auth/baseinfo.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/auth/pumpShed.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/system/log.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/statistics/goods.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/statistics/cargo.js" type="text/javascript"></script>
    <script src="<%=basePath %>/resource/admin/js/scripts/dashboard.js" type="text/javascript"></script>
    <script src="<%=basePath %>/resource/admin/js/scripts/outbound/goodsLZ.js" type="text/javascript"></script>
    <script src="<%=basePath %>/resource/admin/js/scripts/outbound/cargoLZ.js" type="text/javascript"></script>
	
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/goodsManager.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/planmanager/planmanager.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/cargoladingturn.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/virtualCargo.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/statistics/log.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/auth/Session.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/monthInStorage.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/inOutPort.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/tankRate.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/pipeDetail.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/throughput.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/dockPort.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/tradePort.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/storageState.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/stationDeliver.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/unitStatis.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/yearPipe.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/inBoundBook.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/outBoundBook.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/yearStorage.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/yearBerth.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/monthBerth.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/pumpShedRotation.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/productionTarget.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/auth/tankMeasure.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/customsRelease.js" type="text/javascript"></script>
	
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/ladingTurn.js" type="text/javascript"></script>
	
	<script src="<%=basePath%>/resource/admin/js/scripts/IM/jsjac.js" type="text/javascript"></script>
	<script src="<%=basePath%>/resource/admin/js/scripts/IM/jquery.easydrag.js" type="text/javascript"></script>
	<script src="<%=basePath%>/resource/admin/js/scripts/IM/local.chat-2.0.js" type="text/javascript"></script>
	<script src="<%=basePath%>/resource/admin/js/scripts/IM/remote.jsjac.chat-2.0.js" type="text/javascript"></script>
	<script src="<%=basePath%>/resource/admin/js/scripts/IM/send.message.editor-1.0.js" type="text/javascript"></script>
	<!--<script src="<%=basePath%>/resource/admin/js/scripts/IM/roster.js" type="text/javascript"></script>  -->
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/shipWeighBridge.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/reportform/reportform.js" type="text/javascript"></script>
		<script src="<%=basePath %>/resource/admin/js/scripts/outbound/weighDataTable.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/cargoPK.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/productChange.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/approve/approve.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/statistics/invoiceQuery.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/arrivalForeshow.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/tradeInvoice.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/hisTank.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/report/berthDetail.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/SecurityCode.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/inbound/SendMsg.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/outbound/deleteInvoice.js" type="text/javascript"></script>
	<script src="<%=basePath %>/resource/admin/js/scripts/feebill/exceedCleanLog.js" type="text/javascript"></script>
	<!-- 模块中的js脚本结束 -->

	<script type="text/javascript">
		/* Init Metronic's core jquery plugins and layout scripts */
		$(document).ready(function() {
			Metronic.init(); // Run metronic theme
			Metronic.setAssetsPath('<%=basePath %>/resource/'); // Set the assets folder path	
			Metronic.setDomain('<%=basePath %>');
			
			$.ajaxSetup({
				complete : function(data) {
					if(data.responseJSON != null && data.responseJSON != undefined) {
						if(data.responseJSON.code == "9995") {
							$("body").message({
			                    type: 'warning',
			                    content: '您的登陆已失效,请您重新登陆!'
			                });
							setTimeout(function() {
								window.location.href = "<%=basePath %>/login";
							},1000); 
						}
					}
				}
			});
			
		});
	</script>
	<!-- END JAVASCRIPTS -->

</body>
<!-- END BODY -->
</html>