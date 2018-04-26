<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
	.row-details-close {
	    background: url("<%=basePath%>resources/admin/img/datatable-row-openclose.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
	    cursor: pointer;
	    display: inline-block;
	    height: 14px;
	    margin-top: 3px;
	    width: 14px;
	}
	.row-details-open {
	    background: url("<%=basePath%>resources/admin/img/datatable-row-openclose.png") no-repeat scroll 0 -23px rgba(0, 0, 0, 0);
	    cursor: pointer;
	    display: inline-block;
	    height: 14px;
	    margin-top: 3px;
	    width: 14px;
	}
</style>

</head>
<body>
	<!-- BEGIN PAGE HEADER-->
	<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-list-alt"></i><!-- 车发流量计历史记录 -->累计量历史记录
			</div>
		</div>
			<div>
				<form action="#" class="form-horizontal contract-update-form cflltzlb">
				<div id="roleManagerQueryDivId" hidden="true">
					<div class="row">
					<div class="form-body vehicleHistoryCumulantStatement">
						<div class="form-group parkDailyStatement" >
						<label class="control-label col-md-1">车位</label>
						<div class="col-md-2">
								<input class="form-control" id="parkId" style="width:180px;" type="text"	placeholder="车位">
						</div>
						<label class="control-label col-md-2">日期:</label>
							<div class="col-md-2"  style="display: block;padding-right: 0;">
								<input class="form-control  date-picker" id="date" style="width:180px;" type="text"
									placeholder="日期">
							</div>
						<div class="col-md-2"><button  type="button"
							class="btn btn-success search ">
							<span class="glyphicon glyphicon-search"></span>&nbsp;
						</button></div>
					</div>
					</div>
					</div>
					</div>
			</form>
			</div>
			<!-- END FORM-->
			<div class="btn-group buttons">
				<button class="btn  btn-default btn-circle mar-r-10 btn-search" type="button">
					<span class="fa  fa-search"></span><span class="text">搜索</span>
				</button>
			</div>
			<div data-role="vehicleHistoryCumulantStatement">
			</div>
		</div>
		</div>
	</div>
	<!-- END PORTLET-->
	
	
</body>
</html>

