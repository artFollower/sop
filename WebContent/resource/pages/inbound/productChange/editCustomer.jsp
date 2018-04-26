<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">修改货批</h4>
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
							<label class="control-label col-md-4">货批号<span class="required">*</span></label>
							<div class="col-md-8">
								<input id="mask_number"maxlength='30' type="text" name="name" data-required="1" class="form-control code" " />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4">合同</label>
							<div class="col-md-8">
								<input id="mask_number"maxlength='30' type="text" name="name"  class="form-control order" />
							</div>
						</div>
						<div class="form-group">
									<label class="control-label col-md-4">贸易类型<span
										class="required">*</span></label>
									<div class="col-md-8" id="select-tradeType">
										
									</div>
								</div>
								
								
							<!-- 	<div class="form-group">
							<label class="control-label col-md-4">罐号</label>
							<div class="col-md-8">
								<input id="mask_number"maxlength='10' type="text" name="name" class="form-control tankId" />
							</div>
						</div> -->
								
						<div class="form-group">
							<label class="control-label col-md-4">货物数量(吨)<span class="required">*</span></label>
							<div class="col-md-8">
								<input id="mask_number"maxlength='10' readonly type="text" name="name" data-required="1" class="form-control productAmount" onkeyup="clearNoNum(this)" onafterpaste="clearNoNum(this)" />
							</div>
						</div>
						
						
						
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<shiro:hasPermission name="AUPDATECHANGEPROCARGO">
				<button type="button" class="btn btn-primary" id="button-add-goods" >提交</button>
				</shiro:hasPermission>
			</div>
		</div>
	</div>
</div>
<script>
		
	
		
	</script>