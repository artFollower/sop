<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
</head>
<body>
<!-- BEGIN PAGE HEADER-->
	<div class="portlet">
					<div class="portlet-title">
				<div class="caption">
					<i class="fa  fa-files-o"></i>添加合同意向信息
				</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form class="form-horizontal savecheck">
				<div class="form-body">
					<table width="100%">
						<tr>
							<!-- <td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">计单意向号<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" name="name" data-required="1" class="form-control" />
									</div>
								</div>
							</td> -->
							<td class=" col-md-6" >
								<div class="form-group">
									<label class="control-label col-md-4">标题<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" name="title" maxlength="64" data-required="1" data-type="Require" class="form-control intentTitle" />
									</div>
								</div>
							</td>
							<td class=" col-md-6" >
								<div class="form-group">
									<label class="control-label col-md-5">客户组</label>
									<div class="col-md-7" >
									<input id="clientGroupId" type="text"  data-provide="typeahead"  class="form-control clientGroupId">
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">合同类型<span class="required">*</span></label>
									<div class="col-md-8" id="select-type">
										<input id="type" type="text"  data-provide="typeahead" data-required="1" data-type="Require" class="form-control type">
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">客户<span class="required">*</span></label>
									<div class="col-md-7 " id="client">
									<input id="clientId" type="text"  data-provide="typeahead" data-required="1" data-type="Require" class="form-control clientId">
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货品<span class="required">*</span></label>
									<!-- <div class="col-md-8" id="select-product">
									<input id="productId" type="text" maxlength="32" data-provide="typeahead" data-required="1" data-type="Require" class="form-control productId">
									</div> -->
									<div class="col-md-8">
												  <div class="select2-container select2-container-multi form-control select2" data-required="1" data-type="Require"  id="productId"></div>
												 </div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">预计数量(吨)<span class="required">*</span></label>
									<div class="col-md-7">
										<input type="text" data-required="1" maxlength="9" class="form-control quantity" data-required="1" data-type="Require" name="quantity" onkeyup="Intent.addNum(this,3)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">仓储费(元/吨)</label>
									<div class="col-md-8">
										<input type="text"  class="form-control storagePrice" maxlength="8" name="storagePrice" onkeyup="Intent.addNum(this,2)"  onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">码头通过费(元/吨)</label>
									<div class="col-md-7">
										<input type="text"  class="form-control passPrice" maxlength="8" name="passPrice" onkeyup="Intent.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">超期费(元/吨/天)</label>
									<div class="col-md-8">
										<input type="text"  class="form-control overtimePrice" maxlength="8" name="overtimePrice" onkeyup="Intent.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">港口设施保安费(元/吨)</label>
									<div class="col-md-7">
										<input type="text"  class="form-control portSecurityPrice" maxlength="8" name="portSecurityPrice" onkeyup="Intent.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">港务费(元/吨)</label>
									<div class="col-md-8">
										<input type="text"  class="form-control portServicePrice" maxlength="8" name="portServicePrice" onkeyup="Intent.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">其他费(元)</label>
									<div class="col-md-7">
										<input type="text"  class="form-control otherPrice" maxlength="8" name="otherPrice" onkeyup="Intent.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">损耗率(‰)</label>
									<div class="col-md-8">
										<input type="text"  class="form-control lossRate" maxlength="6" name="lossRate" onkeyup="Intent.addNum(this)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">预计总金额(元)<span class="required">*</span></label>
									<div class="col-md-7">
										<input type="text" data-required="1" maxlength="16" class="form-control totalPrice" name="totalPrice" data-required="1" data-type="Require" onkeyup="config.clearNoNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">合同周期(天)<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text"  class="form-control period" maxlength="16" name="period" onkeyup="config.clearNoNum(this)" data-required="1" data-type="Require" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							
						</tr>
						<tr>
							<td class=" col-md-6" colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">备注</label>
									<div class="col-md-10">
										<textarea class="form-control description" maxlength="100" rows="3" name="description"></textarea>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<a href="#/instent" class="btn btn-default">返回</a>
					<shiro:hasPermission name="AINTENTADD">
					<a href="javascript:void(0)" onclick="Intent.doAdd(0)" class="btn btn-primary saveButton">保存</a>
					<a href="javascript:void(0)" onclick="Intent.doAdd(1)" class="btn btn-primary saveButton">提交</a>
					</shiro:hasPermission>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>

</body>