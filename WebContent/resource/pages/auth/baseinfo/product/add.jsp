<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade" role="dialog">
	<div class="modal-dialog modal-wide" style="width: 900px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">货品信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form action="#" class="form-horizontal form-product">
				<div class="form-body">
					<table width="100%">
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货品代码
									<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="code" data-required="1" class="form-control code" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货品名称
									<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="name" data-required="1" class="form-control name" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
						    <td class=" col-md-6">
								<div class="form-group">
								<label class="control-label col-md-4">货品属性</label>
									<div class="col-md-8">
										<select class="form-control oils" name="oils" placeholder="选择货品属性...">
											<option value="">请选择</option>
											<option value="1">油品</option>
											<option value="0">化学品</option>
										</select>
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
								<label class="control-label col-md-4">货品类型</label>
									<div class="col-md-8">
									    <input type="text" id="type" class="form-control type" style="width:160px;" type="text" placeholder="选择货品类型...">
									    <button class="btn btn-default mar-r-8 btn-add" type="button" data-toggle="modal">
											<span class="fa fa-plus"></span><span class="text">添加</span>
										</button> 
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">标准密度</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="standardDensity"  class="form-control standardDensity" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">体积比</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="volumeRatio"  class="form-control volumeRatio" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
							     <div class="form-group">
									<label class="control-label col-md-4">货品颜色</label>
									<div class="col-md-8">
										<div class="input-group color colorpicker-default" data-color="#3865a8" data-color-format="rgba">
											<input type="text" id="colorPicker" style="height: 33px;" class="form-control colorPicker" readonly>
											<span class="input-group-btn">
												<button class="btn default" type="button"><i style="background-color: #3865a8;"></i>&nbsp;</button>
											</span>
										</div> 
									</div>
								</div>
							</td>
							<td class="col-md-6">&nbsp;</td>
						</tr>
					 	<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">描述</label>
									<div class="col-md-10">
										<textarea class="form-control description" rows="2"></textarea>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
		 </form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="addProduct" >提交</button>
			</div>
		</div>
	</div>
</div>