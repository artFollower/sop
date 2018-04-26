<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!-- BEGIN MAIN CONTENT -->
<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-shopping-cart"></i>港务信息更新
				</div>
			</div>
			<div class="btn-group buttons col-md-6 col-sm-6">
				<button class="btn btn-primary btn-head" type="button">
					<span class="glyphicon glyphicon-edit"></span>报文头信息
				</button>
				<button class="btn btn-info btn-ship" type="button">
					<span class="glyphicon glyphicon-edit"></span>船舶信息
				</button>
				<button class="btn btn-primary btn-cargo" type="button">
					<span class="glyphicon glyphicon-edit"></span>货物信息
				</button>
				<button class="btn btn-info cargo-add" type="button">
					<span class="glyphicon glyphicon-add"></span>添加货物
				</button>
			</div>
			<div class="col-md-12 tab tab-content"></div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<div class="modal-footer">
	<a href="#/port" class="btn btn-default">返回</a>
	<a href="javascript:void(0)" class="btn btn-primary saveButton">保存</a>
</div>
<!-- END MAIN CONTENT -->
