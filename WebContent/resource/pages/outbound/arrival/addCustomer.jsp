<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 100%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">添加出港货品信息<label class="checkbox-inline checkDiv" style="float:right;display:none"><input type="checkbox"  checked id="isCheckedNum" value="option2">修改是否校验数量
										</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h4> 
				
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal addGoodsForm">
					<div class="form-body">
					<label class="col-md-8 hidden " id="id"></label>
					<label class="col-md-8 hidden " id="status"></label>
					<label class="col-md-8 hidden " id="goodsLeft"></label>
					<label class="col-md-8 hidden " id="goodsPlanLeft"></label>
					<div class="form-group">
					<div class="col-md-6 cnClientDiv">
							<label class="control-label col-md-4">提货单位<span class="required">*</span></label>
							<div class="col-md-7 cn">
							<input type="text" class=" form-control" id="ladingClientId" data-required="1" data-type="Require" maxlength="60"> 
							</div>
							<div class="col-md-1" style="padding-left:0px;padding-right:0px;">
                           <label class="checkbox-inline" style="float:left;"> <input type="checkbox" onchange="shipArrival.initClientCtr(this);" id="allClient" value="option2">全部
										</label>
                            </div>
							</div>
							<div class="col-md-6">
						<label class="control-label col-md-4">货主单位<span class="required">*</span></label>
							<div class="col-md-8">
							<input type="text" class=" form-control" id="clientId" data-required="1" data-type="Require"  maxlength="60"> 
							</div>
						</div></div>
						<div class="form-group">
						<div class="col-md-6">
									<label class="control-label col-md-4">出港类型<span class="required">*</span></label>
									<div class="col-md-8">
									<input type="text" class=" form-control" data-required="1" data-type="Require" id="tradeTypeId" maxlength="10">
									</div></div>
									<div class="col-md-6">
								<label class="control-label col-md-4">货品<span class="required">*</span></label>
								<label class="control-label col-md-8" style="text-align:left;" id="dProductId"></label>
								</div></div>
                       <div class="form-group"><hr color="gray" style="margin:5px auto;"/> </div>
							<div class="form-group">
							<div class="col-md-6">
							<label class="control-label col-md-4">货体号</label>
									<div class="col-md-8 cargoIdDiv">
									<input type="text" class=" form-control" readonly="readonly" id="goodsId" >
									</div></div>
									<div class="col-md-6">
									<label class="control-label col-md-4">货批号</label>
									<div class="col-md-8 cargoIdDiv">
									<input type="text" class=" form-control" readonly="readonly" id="cargoId" >
									</div></div>
								</div>	
					      <div class="form-group">
					      <div class="col-md-6">
									<label class="control-label col-md-4">入库船信</label>
									<div class="col-md-8 ">
									<input type="text" class="form-control" disabled="disabled" id="inboundMsg" maxlength="60"> 
									</div>
									</div><div class="col-md-6">
									<label class="control-label col-md-4">罐号</label>
								<div class="col-md-8 ">
								<input type="text" class="form-control" id="tankCodes" maxlength="60"> 
								</div></div>
								</div>
								<div class="form-group">
								<div class="col-md-6">
								<label class="control-label col-md-4">报关单位</label>
								<div class="col-md-8 ">
								<input type="text" class="form-control" disabled="disabled" id="clearanceClientId"  maxlength="60"> 
								</div></div>
								<div class="col-md-6">
								<label class="control-label col-md-4">实际提货单位</label>
								<div class="col-md-8 ">
								<input type="text" class="form-control" id="actualLadingClientId"  maxlength="60"> 
								</div></div>
							</div>
							<div class="form-group"><hr color="gray" style="margin:5px auto;"/> </div>
						<div class="form-group">
						<div class="col-md-6">
							<label class="control-label col-md-4">开票提单号<span class="required">*</span></label>
							<div class="col-md-8">
								<input class="form-control " id="ladingCode" style="text-transform: uppercase;"  data-required="1" data-type="Require"  type="text" maxlength="100"/>
							</div></div>
							<div class="col-md-6">
							<label class="control-label col-md-4">货物数量(吨)<span class="required">*</span></label>
							<div class="col-md-8">
								<input class="form-control " id="productAmount"  type="text" data-required="1" data-type="Require" onkeyup="config.clearNoNum(this)" maxlength="10"/>
							</div></div>
						</div>
						<div class="form-group">
						<div class="col-md-6">
							<label class="control-label col-md-4">是否核销</label>
							<div class="col-md-8">
								<select class="form-control" id="isVerification"><option value="1">是</option><option value="2">否</option></select>
							</div></div>
							<div class="col-md-6">
							<label class="control-label col-md-4">流向</label>
							<div class="col-md-8">
								<input class="form-control " id="flow" type="text" maxlength="60"/>
							</div></div>
						</div>
					<div class="form-group"><h4 class="form-section">货体信息</h4>
					<hr width="100%" color="gray" style="margin:5px auto;" /> 
					</div>
						<div data-role="goodsGrid">
						<div style="min-height: 150px; width: 100%; overflow-y: auto;" class="grid-body">
						<table style="margin: 0px; border: 0px;" class="table table-responsive table-bordered table-hover">
						<thead class="grid-table-head" style="width: 100%;">
						<tr>
							<th style="text-align:center;" index="0">货批号<div class="colResize"></div></th>
							<th style="text-align:center;" index="1">货体号<div class="colResize"></div></th>
							<th style="text-align:center;" index="2">原号<div class="colResize"></div></th>
							<th style="text-align:center;" index="3">调号<div class="colResize"></div></th>
							<th style="text-align:center;" index="4">上级货主<div class="colResize"></div></th>
							<th style="text-align:center;" index="5">有效期/起计日期<div class="colResize"></div></th>
							<th style="text-align:center;" index="6">货权属性<div class="colResize"></div></th>
							<th style="text-align:center;" index="7">批量(吨)<div class="colResize"></div></th>
							<th style="text-align:center;" index="8">封量(吨)<div class="colResize"></div></th>
							<th style="text-align:center;" index="9">当前存量(吨)<div class="colResize"></div></th>
							<th style="text-align:center;" index="10">待提量(吨)<div class="colResize"></div></th>
							<th style="text-align:center;" index="11">计划量(吨)<div class="colResize"></div></th>
							<th style="text-align:center;" index="12">剩余可提量(吨)<div class="colResize"></div></th>
							<th width="auto" style="text-align:center;" index="13">罐号<div class="colResize"></div></th>
							</tr></thead>
							<tbody class="grid-table-body" id="goodsTbody" style="width:100%; height: auto; position: relative;">
							</tbody>
							</table>
							</div>
								</div>
									</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary hidden" id="resetItemArrival" >重置</button>
				<shiro:hasPermission name="AOUTBOUNDITEMADD">
				<button type="button" class="btn btn-primary" id="addItemArrial" >提交</button>
				</shiro:hasPermission>
			</div>
		</div>
	</div>
</div>
