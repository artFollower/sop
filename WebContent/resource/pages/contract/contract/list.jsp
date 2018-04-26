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
<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-file-text-o"></i>合同列表<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right"><a style="padding-left: 20px;" class="hidden-print" onclick="javascript:Contract.exportExcel();"> <i class="fa fa-file-excel-o">&nbsp;导出</i></a></span>
				</div>
				<div class="actions" style="display: none">
					<div class="btn-group">
						<a class="btn default yellow-stripe" href="javascript:void(0)" data-toggle="dropdown"> <i class="fa fa-share"></i> <span class="hidden-480"> 工具 </span> <i class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu pull-right">
							<li><a href="javascript:void(0)"> 导出EXCEL </a></li>
							<li><a href="javascript:void(0)"> 导出CSV </a></li>
							<li><a href="javascript:void(0)"> 导出XML </a></li>
							<li class="divider"></li>
							<li><a href="javascript:void(0)"> 打印 </a></li>
							<li><a href="#/contractAdd">添加</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div>
				<form id="contractListForm" target="_self" class="form-horizontal searchCondition">
				<div  id="roleManagerQueryDivId"  >
				<table border="0" cellspacing="0" cellpadding="0">
				  <tr>
				      <td>
				          <div class="form-group">
				              <label class="control-label" style="width:70px;float:left;">&nbsp;合同编号:</label>
				              <div style="margin-left:15px;float:left;">
				                  <input name="code" maxlength="64" class="form-control code" type="text" style="width:100px;"/>
				              </div>
				
				              <label class="control-label" style="width:70px;float:left;">&nbsp;合同名称:</label>
				              <div style="margin-left:15px;float:left;">
				                  <input name="title" maxlength="64" class="form-control title" type="text" style="width:100px;"/>
				              </div>
				              <label class="control-label" style="width:70px;float:left;">&nbsp;类型:</label>
				              <div style="margin-left:15px;float:left;">
				              <select class="form-control select2me type" id="type"  name="type"  >
												<option value="">请选择合同类型...</option>
												<option value="1">包罐</option>
												<option value="2">包量</option>
												<option value="3">全年</option>
												<option value="4">临租</option>
												<option value="5">通过</option>
								</select>
				              </div>
				              <label class="control-label" style="width:70px;float:left;">&nbsp;状态:</label>
				              <div style="margin-left:15px;float:left;">
				              <select class="form-control select2me status" id="status"  name="status"  >
												<option value="">请选择合同状态...</option>
												<option value="0">未提交</option>
												<option value="1">已提交</option>
												<option value="2">已通过</option>
												<option value="3">已退回</option>
												<option value="4">已删除</option>
												<!-- <option value="5">已失效</option> -->
												<option value="6">已作废</option>
								</select>
				              </div>
				          </div>
				      </td>
				      <td style="vertical-align: bottom;">
				      
				      <button onclick="Contract.search()" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-success"><span class="glyphicon glyphicon-search"></span>&nbsp;</button>
				      
				      <button class="btn btn-primary reset" onclick="Contract.reset()" style="position:relative; margin-left:35px; top: -15px" type="button">
												<span title="重置" class="fa fa-undo"></span>&nbsp;
											</button>
				      
				      </td>
				  	
				  </tr>
				</table>	
				</div>
				</form>
			</div>
			<div class="btn-group buttons">
			<shiro:hasPermission name="ACONTRACTADD">
				<button class="btn btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-plus"></span><span class="text">添加</span>
				</button>
				</shiro:hasPermission>
				<!--  <shiro:hasPermission name="ACONTRACTUPDATE">
				<button class="btn btn-default btn-circle mar-r-10 btn-modify" type="button">
						<span class="fa fa-edit"></span><span class="pad-l-5">修改</span>
					</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="ACONTRACTDELETE">
				<button class="btn btn-default btn-circle mar-r-10 btn-remove" type="button">
						<span class="fa fa-remove"></span><span class="pad-l-5">撤销</span>
					</button> 
					</shiro:hasPermission>-->
				<button class="btn btn-default btn-circle  btn-search" type="button">
					<i class="fa  fa-search"></i><span class="pad-l-5" style="line-height: 16px;">搜索</span>
				</button>
			</div>
			<div data-role="contractGrid">
			</div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->
<script>
	$(function() {
		Contract.init();		
	});
</script>
</body>
</html>

