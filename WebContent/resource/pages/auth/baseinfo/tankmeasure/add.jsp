<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 900px;
}
</style>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">油品参数</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal addGoodsForm">
					<label class="col-md-8 hidden " id="id"></label>
					<label class="col-md-8 hidden " id="status"></label>
						<div class="form-group">
							<label class="control-label col-md-2">识别号<span class="required">*</span></label>
							<div class="col-md-4">
							<input type="text" class=" form-control" id="notify"  data-required="1" data-type="Require"  > 
							</div>
							<label class="control-label col-md-2">货品<span class="required">*</span></label>
							<div class="col-md-4">
							<input type="text" class=" form-control" id="productId" data-required="1" data-type="Require"  > 
							</div>
						</div>
						<div class="form-group">
									<label class="control-label col-md-2">罐号<span class="required">*</span></label>
							<div class="col-md-4 tankDiv">
							<input type="text" class=" form-control" id="tankId" data-required="1" data-type="Require"  > 
							</div>
							<label class="control-label col-md-2">发货口<span class="required">*</span></label>
							<div class="col-md-4">
							<input type="text" class=" form-control" id="port" data-required="1" data-type="Require"  maxlength="10"> 
							</div>
								</div>
								
								<div class="form-group">
									<label class="control-label col-md-2">标准密度<span class="required">*</span></label>
							<div class="col-md-4">
							<input type="text" class=" form-control" id="normalDensity" onkeyup="config.clearNoNum(this,4)" data-required="1" data-type="Require"  maxlength="10"> 
							</div>
							<label class="control-label col-md-2">视密度<span class="required">*</span></label>
							<div class="col-md-4">
							<input type="text" class=" form-control" id="textDensity" onkeyup="config.clearNoNum(this,4)" data-required="1" data-type="Require"  maxlength="10"> 
							</div>
								</div>
								
								<div class="form-group">
									<label class="control-label col-md-2">视温度(℃)<span class="required">*</span></label>
							<div class="col-md-4">
							<input type="text" class=" form-control" id="textTemperature" onkeyup="config.clearNoNum(this,1)" data-required="1" data-type="Require"  maxlength="10"> 
							</div>
							<label class="control-label col-md-2">货罐温度(℃)<span class="required">*</span></label>
							<div class="col-md-4 ">
							<input type="text" class=" form-control" id="tankTemperature" onkeyup="config.clearNoNum(this,1)" data-required="1" data-type="Require"  maxlength="10"> 
							</div>
								</div>
								
							<!-- 	<div class="form-group ">
									<label class="control-label col-md-2">视体积<span class="required">*</span></label>
							<div class="col-md-4">
							<input type="text" class=" form-control" id="viewVolume"  onkeyup="config.clearNoNum(this,3)" data-required="1" data-type="Require"  maxlength="10"> 
							</div>
							<label class="control-label col-md-2">标体积<span class="required">*</span></label>
							<div class="col-md-4">
							<input type="text" class=" form-control" id="normalVolume" onkeyup="config.clearNoNum(this,3)" data-required="1" data-type="Require"  maxlength="10"> 
							</div>
								</div> -->
						
						<div class="form-group">
									<!-- <label class="control-label col-md-2">视密度<span class="required">*</span></label>
							<div class="col-md-4">
							<input type="text" class=" form-control" id="viewDensity" onkeyup="config.clearNoNum(this,4)" data-required="1" data-type="Require"  maxlength="10"> 
							</div> -->
							<label class="control-label col-md-2">体积比<span class="required">*</span></label>
							<div class="col-md-4">
							<input type="text" class=" form-control" id="volumeRatio" onkeyup="config.clearNoNum(this,4)" data-required="1" data-type="Require"  maxlength="10"> 
							</div>
								</div>
								
								<div class="form-group">
									<label class="control-label col-md-2">说明</label>
							<div class="col-md-10">
							<input type="text" class=" form-control" id="description"   maxlength="30"> 
							</div>
								</div>
								
							<div class="form-group">
									<label class="control-label col-md-2">提交时间<span class="required">*</span></label>
							<div class="col-md-4">
							<div class="input-group" id="createTime">
								<div class="col-md-7" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;"
										class="form-control form-control-inline date-picker col-md-8"
										type="text" data-required="1" />
								</div>
								<div class="col-md-5"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text"
										class="form-control col-md-4  timepicker timepicker-24">
								</div>
							</div> 
							</div>
							<label class="control-label user col-md-2">提交人</label>
							<label class="control-label user col-md-4" style="text-align:left;" id="userId"></label>
								</div>	
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="addTankMeasure" >提交</button>
			</div>
		</div>
	</div>
</div>
