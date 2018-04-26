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
	
</head>
<body>
	<!-- END PORTLET-->
	<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-yelp"></i>提单管理<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right"><a style="padding-left: 20px;" class="hidden-print" onclick="javascript:Lading.exportExcel();"> <i class="fa fa-file-excel-o">&nbsp;导出</i></a></span>
			</div>
		</div>
		
		<div class="portlet">
			<div class="row">
				<div class="col-md-12">
					<div class="tabbable-custom">
							<ul class="nav nav-tabs">
						<li class="active"><a href="javascript:void(0)"
							onclick="Lading.changetab(this,1)" data-toggle="tab"
							id="tab1">出库提单</a>
						</li>
						<li><a href="javascript:void(0)"
							onclick="Lading.changetab(this,2)" data-toggle="tab"
							id="tab2">原始货体</a>
						</li>
					</ul>
						<div class="tab-content">
						<div class="tab-pane active" id="portlet_tab1">
							<div class="portlet light">
		
		
		
		<div class="row">
				<div class="col-md-12">
				<form id="ladingListForm" target="_self" class="form-horizontal searchCondition ">
				<div  id="roleManagerQueryDivId"  >
				          <div class="form-group " id="row1">
				              <label class="control-label col-md-2 " style="padding-left:0px;padding-right:0px;">&nbsp;货品名:</label>
				              <div style="margin-left:15px;float:left;padding-left:0px;padding-right:0px;" class="col-md-2">
				                  <input name="productId" maxlength="32" id="productId" data-provide="typeahead" class="form-control productId" style="padding-left:0px;padding-right:0px;" type="text" />
				              </div>
				
				              <label class="control-label col-md-2 " style="width:70px;float:left;padding-left:0px;padding-right:0px;">&nbsp;接收单位:</label>
				              <div style="margin-left:15px;float:left;padding-left:0px;padding-right:0px;" class="col-md-2">
				                  		<input id="ladingClientId"  type="text" style="padding-left:0px;padding-right:0px;" name="buyClientId" data-provide="typeahead"  class="form-control ladingClientId">
				              </div>
				              
				      <label class="control-label col-md-2 ladingStatus" style="width:70px;float:left;padding-left:0px;padding-right:0px;">&nbsp;提单类型:</label>
				              <div style="margin-left:15px;float:left;padding-left:0px;padding-right:0px;" class="col-md-2">
				              <select class="form-control  select2me type" id="type"  name="type" style="padding-left:0px;padding-right:0px;" >
												<option value="">请选择提单类型...</option>
												<option value="1">转卖</option>
												<option value="2">发货</option>
								</select>
				              </div>
				          </div>
				 
				          <div class="form-group ">
				              <label class="control-label col-md-2 searchCode" style="padding-left:0px;padding-right:0px;">&nbsp;提单号:</label>
				              <div style="margin-left:15px;float:left;padding-left:0px;padding-right:0px;" class="col-md-2">
				                  <input name="code" maxlength="32" class="form-control code0" style="padding-left:0px;padding-right:0px;" type="text" />
				              </div>
				
				              <label class="control-label col-md-2 searchClient" style="width:70px;float:left;padding-left:0px;padding-right:0px;">&nbsp;发货单位:</label>
				              <div style="margin-left:15px;float:left;padding-left:0px;padding-right:0px;" class="col-md-2">
				                  		<input id="clientId0"  type="text" style="padding-left:0px;padding-right:0px;" name="clientId" data-provide="typeahead"  class="form-control clientId">
				              </div>
				              
				      <label class="control-label col-md-2 ladingStatus" style="width:70px;float:left;padding-left:0px;padding-right:0px;">&nbsp;提单状态:</label>
				              <div style="margin-left:15px;float:left;padding-left:0px;padding-right:0px;" class="col-md-2">
				              <select class="form-control select2me status" id="status"  name="status" style="padding-left:0px;padding-right:0px;" >
												<option value="">请选择提单状态...</option>
												<option value="1">锁定</option>
												<option value="2">激活(部分)</option>
												<option value="3">激活</option>
												
								</select>
				              </div>
				          <button class="btn btn-primary reset" onclick="Lading.reset()" style="float:right;margin-left:5px" type="button">
												<span title="重置" class="fa fa-undo"></span>&nbsp;
											</button>
				      <button onclick="Lading.search()" type="button"  class="btn btn-success " style="float:right;margin-left:5px" ><span class="glyphicon glyphicon-search"></span>&nbsp;</button>
				          </div>
				             
				</div>
				</form>
				      
			</div>
			</div>

			</div>
			</div>
		<div class="btn-group buttons">
		<shiro:hasPermission name="ALADINGADD">
				<button class="btn btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-plus"></span><span class="text">添加</span>
				</button>
				</shiro:hasPermission>
				
				<button class="btn btn-default btn-circle mar-r-10 btn-searchguoqi" type="button">
					<span class="text">有余量的过期提单</span>
				</button>
				<button class="btn btn-default btn-circle  btn-moban" onclick="Lading.printmb()" type="button">
					<i class="fa  "></i><span class="pad-l-5" style="line-height: 16px;">打印调拨单模板</span>
				</button>
				<shiro:hasPermission name="ALADINGUPDATE">
				<button class="btn btn-default btn-circle mar-r-10 btn-modify" type="button" style="display: none">
						<span class="fa fa-edit"></span><span class="pad-l-5">修改</span>
					</button>
					</shiro:hasPermission>
				<!-- <button class="btn btn-default btn-circle mar-r-10 btn-remove" type="button">
						<span class="fa fa-remove"></span><span class="pad-l-5">撤销</span>
					</button>  -->
				<!-- <button class="btn btn-default btn-circle  btn-search" type="button">
					<i class="fa  fa-search"></i><span class="pad-l-5" style="line-height: 16px;">搜索</span>
				</button> -->
			</div>
			
			<div  data-role="ladingGrid" >
			</div>
			</div>
			</div>
			</div>
	</div>
	</div>
	</div>
	</div>
	</div>
</body>
</html>