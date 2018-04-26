<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
					<i class="fa fa-yelp"></i>编辑提单信息
				</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form class="form-horizontal lading-update-form">
				<div class="form-body">
					<table width="100%">
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">提单类型<span class="required">*</span></label>
									<div class="col-md-8">
											<input readonly type="text" id="type" data-required="1" class="form-control type" value="转卖提单" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">客户提单号<span class="required">*</span></label>
									<div class="col-md-8">
										<input  type="text" maxlength='30' id="code" data-required="1" class="form-control code" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">发货单位<span class="required">*</span></label>
									<div class="col-md-8">
										<input readonly type="text"  id="custName" data-required="1" class="form-control custName" />
									</div>
								</div>
							</td>
							<td class=" col-md-6" style="display: none">
								<div class="form-group">
									<label class="control-label col-md-4">海关放行编号</label>
									<div class="col-md-8">
										<input type="text" maxlength='32' id="customsPassCode" readonly class="form-control customsPassCode" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">接收单位<span class="required">*</span></label>
									<div class="col-md-8">
										<input readonly type="text"  id="reviveCustName" data-required="1" class="form-control reviveCustName" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货品名称<span class="required">*</span></label>
									<div class="col-md-8">
										<input readonly type="text"  id="productName" data-required="1" class="form-control productName" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">放行数量(吨)<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" id="passCount" readonly maxlength='16' data-required="1" onkeyup="config.clearNoNum(this)" class="form-control passCount" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">提单数量(吨)<span class="required">*</span></label>
									<div class="col-md-8">
										<input readonly type="text"  id="totalCount" data-required="1" class="form-control totalCount" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6" id="tdStartTime">
								<div class="form-group">
									<label class="control-label col-md-4">仓储费起计日期</label>
									<div class="col-md-8">
										<div class="input-group date date-picker" data-date-viewmode="years" data-date-format="yyyy-mm-dd" >
											<input class="form-control startTime"  type="text" readonly="" id="startTime"  > <span class="input-group-btn">
												<button class="btn default" type="button">
													<i class="icon-calendar"></i>
												</button>
											</span>
										</div>
									</div>
								</div>
							</td>
							<td class=" col-md-6" id="tdEndTime">
								<div class="form-group">
									<label class="control-label col-md-4">提单有效期</label>
									<div class="col-md-6">
										<div class="input-group date date-picker" data-date-viewmode="years" data-date-format="yyyy-mm-dd" >
											<input class="form-control endTime" type="text"  id="endTime" > <span class="input-group-btn">
												<button class="btn default" type="button">
													<i class="icon-calendar"></i>
												</button>
											</span>
										</div>
									</div>
									<label class="checkbox-inline" > <input type="checkbox" value="" id="isLong" name="isLong"> 长期
									
								</label> 
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">已发货数量(吨)<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" data-required="1"  readonly class="form-control deliveryCount" id="deliveryCount" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6" style="display: none">
								<div class="form-group">
									<label class="control-label col-md-4">提单状态<span class="required">*</span></label>
									<div class="col-md-8">
										<select class="form-control select2me status" id="status"  data-placeholder="选择提单状态...">
											<option value="-1">请选择提单状态...</option>
												<option value="0">锁定</option>
												<option value="1">激活</option>
												<option value="2">终止</option>
										</select>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">已调出数量(吨)</label>
									<div class="col-md-8">
										<input type="text"  readonly class="form-control goodsOut" id="goodsOut" />
									</div>
								</div>
							</td>
							
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">创建人</label>
									<div class="col-md-8">
										<input type="text" id="createUser" readonly maxlength='16'class="form-control createUser" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">创建时间</label>
									<div class="col-md-8">
										<input readonly type="text"  id="createTime" data-required="1" class="form-control createTime" />
									</div>
								</div>
							</td>
						</tr>
						
						
						<tr>
							<td class=" col-md-6" colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">备注</label>
									<div class="col-md-10">
										<textarea class="form-control description" maxlength='100' rows="3" id="description"></textarea>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="portlet box blue" style="border-color: #777">
					<div class="portlet-title" style="background-color: #777">
						<div class="caption">
							<i class="icon-edit"></i>货体列表
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a>
						</div>
					</div>
					<div class="portlet-body" style="overflow-x:auto;">
						<div class="table-toolbar">
							<div class="btn-group">
							<shiro:hasPermission name="AADDLADINGGOODS">
								<a href="javascript:void(0);" onclick="Lading.openDialog(2)" data-target="#ajax" data-toggle="modal" id="goodsAdd">
									<button id="sample_editable_1_new" class="btn btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-plus"></span><span class="text">添加</span>
				</button>
								</a>
								</shiro:hasPermission>
							</div>
						</div>
						<table class="table table-striped table-hover table-bordered" id="goods-table" >
							<thead>
								<tr>
									<th style="text-align: center">货体代码</th>
									<th style="text-align: center">货批编号</th>
									<th style="text-align: center">货批船信</th>
									<th style="text-align: center">原号</th>
									<th style="text-align: center">调号</th>
									<th style="text-align: center">上级调号</th>
									<th style="text-align: center">罐号</th>
									<th style="text-align: center">货品名称</th>
									<th style="text-align: center">货主</th>
									<th style="text-align: center">货体量(吨)</th>
									<th style="text-align: center">当前存量(吨)</th>
									<th style="text-align: center">调出量(吨)</th>
									<th style="text-align: center">已发量(吨)</th>
									<th style="text-align: center">放行数量(吨)</th>
									<th style="text-align: center">合同号</th>
									<th style="text-align: center">操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer ">
					<div class="col-md-offset-3 col-md-9">
					<input id="goodsGroupId" type="hidden" class="goodsGroupId" />
						<input type="hidden" class="inputId" id="inputId">
						<a href="#/lading" class="btn btn-default ladingBack">返回</a>
						<shiro:hasPermission name="ALADINGCHONGXIAO">
						<button type="button" class="btn btn-primary againstButton">提单冲销</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ALADINGCHONGXIAO">
						<button type="button"  style="display: none" class="btn btn-primary backButton">余量退回</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ALADINGKOUSUN">
						<button type="button" class="btn btn-primary lossButton">提单扣损</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ALADINGUPDATE">
						<button type="button" class="btn btn-primary saveButton">保存</button>
						</shiro:hasPermission>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
	<!-- END PAGE -->
	<!-- 页面内容开结束-->
</body>
</html>