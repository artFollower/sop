<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.hisTank{
	margin: 30px auto;
	width: 60%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog hisTank">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">编辑历史储罐信息</h4>
			</div>
			<div class="modal-body" style="padding-left: 25px; padding-right: 35px;">
				<div class="portlet box blue ">
					<div class="portlet-title">
					<div class="tools"></div>
					</div>
					<div class="portlet-body">
						<form action="#" class="form-horizontal">
							<div class="form-body">
								<div class="form-group">
									<label class="control-label col-md-2">储罐：</label>
									<div class="col-md-4">
										<input type="text" id="tankCode" readonly="readonly" class="form-control">
									</div>
									<label class="control-label col-md-2">日期：</label>
									<div class="col-md-4">
										<input type="text" id="Dtime" readonly="readonly" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">货品：</label>
									<div class="col-md-4">
										<input type="text" id="productName" class="form-control">
									</div>
									<label class="control-label col-md-2">类型：</label>
									<div class="col-md-4">
										<select  id="type" class="form-control">
											 <option value="0" selected>内贸</option>
											<option value="1">外贸</option>
											<option value="2">保税非包罐</option>
											<option value="3">保税包罐</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">温度（°c）：</label>
									<div class="col-md-4">
										<input type="text" id="temperature"  readonly="readonly"  class="form-control" />
									</div>
									<label class="control-label col-md-2">当时存储(吨)：</label>
									<div class="col-md-4">
										<input type="text" id="materialWeight"  readonly="readonly"  class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">当时最大存储(吨)：</label>
									<div class="col-md-4">
										<input type="text" id="capacityTotal" class="form-control"  />
									</div>
									<label class="control-label col-md-2">当时使用状态：</label>
									<div class="col-md-4">
										<select  id="isUse" class="form-control">
										 <option value="0" selected>已停用</option>
										 <option value="1">使用中</option>
										</select>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="save" >保存</button>
				</div>
			</div>
		</div>
	</div>
</div>