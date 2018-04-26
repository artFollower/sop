<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">添加货批</h4>
			</div>
			<div class="modal-body" style="padding-left:0px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal addGoodsForm">
						<div class="form-group">
							<label class="control-label col-md-4">货主<span class="required">*</span></label>
							<div class="col-md-6 " id="client">
								<input id="clientId" type="text" maxlength='32' data-provide="typeahead" data-required="1" data-type="Require" class="form-control clientId">
							</div>
							<div class="col-md-2 " >
								<button type="button" onclick="Arrival.addNewClient()" class="btn btn-primary newClient" >新建客户</button>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4">货品名<span class="required">*</span></label>
							<div class="col-md-8" >
								<input id="productId" type="text" maxlength='16' data-provide="typeahead" data-required="1" data-type="Require" class="form-control productId">
							</div>
						</div>
						<div class="form-group">
									<label class="control-label col-md-4">贸易类型<span
										class="required">*</span></label>
									<div class="col-md-8" id="select-tradeType">
										
									</div>
								</div>
						<div class="form-group">
							<label class="control-label col-md-4">货物数量(吨)<span class="required">*</span></label>
							<div class="col-md-8">
								<input id="mask_number"maxlength='10' type="text" name="name" data-required="1" class="form-control productAmount" onkeyup="clearNoNum(this)" onafterpaste="clearNoNum(this)" />
							</div>
						</div>
						
						
						<div class="form-group">
						<div class="col-md-4"></div>
						<div class="col-md-8"> 
								<label class="checkbox-inline"> <input type="checkbox" value="" id="isDeclareCustom" name="isDeclareCustom"> 申报海关
								</label> 
								<label class="checkbox-inline" style="display: none"> <input type="checkbox" value="" id="isCustomAgree" name="isCustomAgree"> 海关同意卸货
									</label>
									</div>
						</div>
						
								<div class="form-group">
									<label class="control-label col-md-4">海运提单号</label>
									<div class="col-md-8">
										<input type="text" maxlength="32" class="form-control customLading" name="customLading" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-4">海运提单数量(吨)</label>
									<div class="col-md-8">
										<input type="text" maxlength="10" class="form-control customLadingCount" onkeyup="config.clearNoNum(this)" name="customLadingCount"  />
									</div>
								</div>
								
						<div class="form-group">
							<label class="col-md-4 control-label">接卸要求</label>
							<div class="col-md-8">
								<textarea maxlength='100' class="form-control requare" rows="3"></textarea>
							</div>
						</div>
						
						
						
						
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="button-add-goods" >提交</button>
			</div>
		</div>
	</div>
</div>
<script>
		
	
		
	</script>