<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<body>
	<!-- BEGIN PAGE HEADER-->
	<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
		<div class="portlet-title">
			<div class="caption" style="width: 100%;">
				<i class="fa fa-list-alt"></i>油品参数表<span style="font-size: small;margin: 0;padding-right: 18px;"><a style="padding-left: 20px;" class="hidden-print" onclick="TankMeasure.exportExcel();"> <i class="fa fa-file-excel-o">&nbsp;导出</i></a></span>
			</div>
		</div>
			<div>
				<form action="#" class="form-horizontal">
				<div id="roleManagerQueryDivId" hidden="true">
					<div class="row">
					<div class="form-body">
						<div class="form-group">
						 <div class="col-md-3">
						 <label class="control-label col-md-4">货品 </label>
						<div class="col-md-8">
						<input type="text" id="productName" class="form-control" />
						</div>
						 </div>
						<div class="col-md-3">
						<label class="control-label col-md-4">储罐 </label>
						<div class="col-md-8">
						<input type="text" id="tankName" class="form-control"/>
						</div>
						</div>
						
						<div class="col-md-3">
						 <div class="form-group ">
						  <div class="col-md-8 col-md-offset-4 input-group-btn" >
							<button type="button" class="btn btn-success search">
								<span class="fa fa-search"></span>&nbsp;
							</button>
							<button  type="button" style="margin-left:8px;"
								class="btn btn-primary reset">
								<span class="fa fa-undo" title="重置"></span>&nbsp;
							</button>
							</div>
						</div>
						</div>
					</div>
					</div>
					</div>
					</div>
			</form>
			</div>
			
			<div class="btn-group buttons">
				<button class="btn  btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-edit"></span><span class="text">添加</span>
				</button>
				<button class="btn  btn-default btn-circle mar-r-10 btn-search" onclick="util.querySlideToggle()" type="button">
					<span class="fa  fa-search"></span><span class="text">搜索</span>
				</button>
			</div>
			<div data-role="tankmeasureGrid"></div>
		</div>
		</div>
	</div>
</body>


