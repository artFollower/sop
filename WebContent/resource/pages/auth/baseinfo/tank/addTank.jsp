<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" >
		<div class="modal-content"  style="width: 800px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">储罐</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
			<form action="#" class="form-horizontal">
							<div class="form-body">
								<div class="form-group">
									<input type="hidden" id="id"> <label
										class="control-label col-md-2">储罐代码<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" id="code" data-required="1"
											data-type="Require" class="form-control" />
									</div>
									 <label class="control-label col-md-2">所属泵棚<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" id="pumpShedName" data-required="1"
											data-type="Require" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">储罐类型</label>
									<div class="col-md-4">
										<select id="type" class="form-control">
											<option value="0">内贸</option>
											<option value="1">外贸</option>
											<option value="2">保税非包罐</option>
											<option value="3">保税包罐</option>
										</select>
									</div>
									<label class="control-label col-md-2">装载货品</label>
									<div class="col-md-4">
										<input type="text" id="productId" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">最大储存量（t）</label>
									<div class="col-md-4">
										<input type="text" id="capacityTotal" maxlength="10" data-type="Require" onkeyup="config.clearNoNum(this)" 
											 class="form-control" />
									</div>
									<label class="control-label col-md-2">实际储存量（t）</label>
									<div class="col-md-4">
										<input type="text" id="capacityCurrent" disabled="disabled" onkeyup="config.clearNoNum(this)" 
											 class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">可储容量（t）</label>
									<div class="col-md-4">
										<input type="text" id="capacityFree" disabled="disabled" onkeyup="config.clearNoNum(this);"
											 class="form-control" />
									</div>
									<label class="control-label col-md-2">实验密度（ρ）</label>
									<div class="col-md-4">
										<input type="text" id="testDensity" maxlength="10" data-type="Require" onkeyup="config.clearNoNum(this)"
											 class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">实验温度（℃）</label>
									<div class="col-md-4">
										<input type="text" id="testTemperature" maxlength="10" data-type="Require" onkeyup="config.clearNoNum(this)"
											 class="form-control" />
									</div>
									<label class="control-label col-md-2">储罐温度（℃）</label>
									<div class="col-md-4">
										<input type="text" id="tankTemperature" maxlength="10" data-type="Require" onkeyup="config.clearNoNum(this,1)"
											 class="form-control" />
									</div>
								</div>
								<div class="form-group">
								<label class="control-label col-md-2">使用情况</label>
									<div class="col-md-4">
										<select id="description" class="form-control">
											<option value="使用中">使用中</option>
											<option value="已清洗">已清洗</option>
											<option value="检修中">检修中</option>
										</select>
									</div>
									<label class="control-label col-md-2">KEY</label>
									<div class="col-md-4">
										<input type="text" id="KEY" maxlength="16" data-type="Require" class="form-control" />
									</div>
								</div>
								<div class="form-group">
								<label class="control-label col-md-2">虚拟罐</label>
								<div class="col-md-4">
									<input type="checkbox" value="" id="isVir" name="isVir"> 
								</div>
								</div>
								
							</div>
						</form>
	</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="save" >提交</button>
			</div>
		</div>
	</div>
</div>
