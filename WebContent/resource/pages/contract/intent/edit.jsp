<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<!-- BEGIN PAGE HEADER-->
	
	<div class="portlet">
					<div class="portlet-title">
				<div class="caption">
					<i class="fa  fa-files-o"></i>修改合同意向信息
				</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form class="form-horizontal intent-update-form">
				<div class="form-body">
					<table width="100%">
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">订单意向号<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" readOnly name="code" maxlength="16" data-required="1" data-type="Require" class="form-control code"  />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">标题<span class="required">*</span></label>
									<div class="col-md-7">
										<input type="text" name="title" maxlength="64" data-required="1" data-type="Require" class="form-control intentTitle" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">客户组</label>
									<div class="col-md-8">
									<input id="clientGroupId" type="text" data-provide="typeahead"  class="form-control clientGroupId">
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">客户<span class="required">*</span></label>
									<div class="col-md-7" id="client">
									<input id="clientId" type="text" data-provide="typeahead" data-required="1" data-type="Require" class="form-control clientId">
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
									<label class="control-label col-md-5">合同类型<span class="required">*</span></label>
									<div class="col-md-7" id="select-type">
										<input id="type" type="text" data-provide="typeahead" data-required="1" data-type="Require" class="form-control type">
									</div>
								</div>
							</td>
						</tr>
						<tr>
						<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">预计数量(吨)<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text"  data-required="1" maxlength="9" data-type="Require" class="form-control quantity" name="quantity"   onkeyup="Intent.addNum(this,3)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">仓储费(元/吨)</label>
									<div class="col-md-8">
										<input type="text" maxlength="8"  class="form-control storagePrice" name="storagePrice" onkeyup="Intent.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">码头通过费(元/吨)</label>
									<div class="col-md-7">
										<input type="text" maxlength="8"  class="form-control passPrice" name="passPrice" onkeyup="Intent.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">超期费(元/吨/天)</label>
									<div class="col-md-8">
										<input type="text" maxlength="8"  class="form-control overtimePrice" name="overtimePrice" onkeyup="Intent.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">港口设施保安费(元/吨)</label>
									<div class="col-md-7">
										<input type="text"  maxlength="8" class="form-control portSecurityPrice" name="portSecurityPrice" onkeyup="Intent.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">港务费(元/吨)</label>
									<div class="col-md-8">
										<input type="text"  maxlength="8" class="form-control portServicePrice" name="portServicePrice" onkeyup="Intent.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">其他费(元)</label>
									<div class="col-md-7">
										<input type="text" maxlength="8" class="form-control otherPrice" name="otherPrice" onkeyup="Intent.addNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">损耗率(‰)</label>
									<div class="col-md-8">
										<input type="text" maxlength="6" class="form-control lossRate" name="lossRate" onkeyup="Intent.addNum(this)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">预计总金额(元)<span class="required">*</span></label>
									<div class="col-md-7">
										<input type="text" maxlength="16" data-required="1" data-type="Require" class="form-control totalPrice"  name="totalPrice" onkeyup="config.clearNoNum(this,2)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">合同周期(天)<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" maxlength="16" data-required="1" data-type="Require"  class="form-control period" name="period" onkeyup="config.clearNoNum(this)" onafterpaste="config.clearNoNum(this)" />
									</div>
								</div>
							</td>
							
						</tr>
						<tr class="create">
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">创建人</label>
									<div class="col-md-8">
										<input type="text" readonly class="form-control createUserName" name="createUserName"  />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">创建日期</label>
									<div class="col-md-7">
										<input type="text" readonly class="form-control createTime" name="createTime"  />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6" colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">备注</label>
									<div class="col-md-10">
										<textarea class="form-control description" rows="3" name="description"></textarea>
									</div>
								</div>
							</td>
						</tr>
					</table>
					<div class="check" style="display: none">
						<h4 class="form-section">&nbsp;审核信息</h4>
						<table width="100%">
						
						<tr class="check" style="display: none">
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">审核人</label>
									<div class="col-md-8">
										<input type="text" readonly class="form-control checkUser" name="createUserName"  />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-5">审核日期</label>
									<div class="col-md-7">
										<input type="text" readonly class="form-control checkTime" name="createTime"  />
									</div>
								</div>
							</td>
						</tr>
					</table>
					</div>
				</div>
				<div class="modal-footer">
					<input type="hidden" class="form-control inputId"  name="inputId"/>
					<input type="hidden" class="form-control editTime"  name="editTime"/>
					
					
					<a href="#/instent" class="btn btn-default">返回</a>
					<shiro:hasPermission name="AINTENTUPDATE">
					<a href="javascript:void(0)" onclick="Intent.doEdit(0)" class="btn btn-primary saveButton" style="display: none">保存</a>
					<a href="javascript:void(0)" onclick="Intent.doEdit(1)" class="btn btn-primary submitButton" style="display: none">提交</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="AINTENTVERIFY">
					<a href="javascript:void(0)" onclick="Intent.doSubmit(0,2)" class="btn btn-primary passButton" style="display: none">通过</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="ACHANGETOCONTRACT">
					<a href="javascript:void(0)"  onclick="Intent.toContract()"  class="btn btn-primary contractButton" style="display: none">转为合同</a>
					</shiro:hasPermission>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
	<!-- 页面内容开结束-->

</body>