<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<head>
<style type="text/css">
.ladingGoods > thead > tr > th,.ladingGoods > thead > tr > td, .ladingGoods > tbody > tr > td {
    border-top: 1px solid #ddd;
    line-height: 1.42857;
    padding: 8px;
    text-align:center;
    vertical-align: middle;
}
</style>
</head>
	<div class="portlet" style="font-size: 14px;">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-list-alt"></i>添加开票  
			</div><span class="pull-right" style="font-size: small;margin: 0;padding-right: 12px;">
			<a onclick="javascript:Invoice.print();" class="hidden-print" style="padding-left: 12px;"> 
			<i class="fa fa-print">&nbsp;打印</i></a>
			</span>
			<span class="pull-right" style="font-size: small;margin: 0;padding-right: 0px;">
			<label class="checkbox-inline">
			<input type="checkbox" value="" id="allClient" class="liandan" data-placement="bottom"  data-content="车发勾选联单可以开在同一个车次。">联单
			</label>
			</span>
		</div>
				<form class="form-horizontal">
					<div class="form-body fhkp">
									<div class="form-group" style="margin-bottom: 5px">
										<label class="control-label col-md-1" style="font-size: 14px;">货品名称<span
											class="required">*</span>
										</label>
										<div class="col-md-2">
											<input type="text" id="productName" class="form-control intentTitle" data-required="1" data-type="Require" style="font-size: 14px;"/>
										</div>
										<label class="control-label col-md-1" style="font-size: 14px;">客户名称<span class="required">*</span>
										</label>
										<div class="cn col-md-4">
											<input type="text" id="clientName" class="form-control intentTitle" data-required="1" data-type="Require" style="font-size: 14px;"/>
										</div>
										<div class="col-md-1 " style="float:left;">
                                 <label class="checkbox-inline"> <input type="checkbox" onchange="Invoice.initClientCtr(this);" id="allClient" value="option2">全部
										</label>
                                         </div>
										<label class="control-label col-md-1" style="font-size: 14px;">发货类型<span
											class="required">*</span>
										</label>
										<div class="col-md-2">
											<select id="select_option" name="deliverType" style="font-size: 14px;" class="form-control" onchange="Invoice.initVehicleShipCtr(this.value);" data-required="1" data-type="Require">
											<option value='1'>车发</option>
											<option value='2'>船发</option>
											<option value='3'>管输</option>
											</select>
										</div>
									</div>
									<div class="form-group" style="margin-bottom: 5px">
									 <input type="hidden" id="loadCapacityHidden"/>
										<label class="control-label col-md-1" id="vsLaber" style="font-size: 14px;">车船号<span class="required">*</span>
										</label>
										<div class="col-md-2" id="vsInfo">
											<input type="text" id="vehicleShipNo"
												class="form-control intentTitle" data-required="1" data-type="Require" maxlength="15" size="15" style="text-transform: uppercase;font-size: 14px;"/>
										</div>
										<label class="control-label col-md-1" style="font-size: 14px;">提货单位<span
											class="required">*</span>
										</label>
										<div class="lcn col-md-4">
											<input type="text" id="ladingClientId"
												class="form-control intentTitle ladingClient" data-required="1" data-type="Require" style="font-size: 14px;"/>
											</div>
											<div class="col-md-1" style="float:left;">
			                                 <label class="checkbox-inline"> <input type="checkbox" onchange="Invoice.initLadingClientCtr(this);" id="allLadingClient" value="option2">全部
													</label>
			                                         </div>
										<label class="control-label col-md-1" style="font-size: 14px;">提单号<span
											class="required">*</span>
										</label>
										<div class="col-md-2">
											<input type="text" id="ladingEvidence" data-required="1" data-type="Require"  onkeyup="Invoice.getOutArrivalShip(this)"
												class="form-control intentTitle" maxlength="30" style="font-size: 14px;text-transform: uppercase;"/>
											
										</div>
									</div>
									<div class="form-group">
									<label class="control-label col-md-1" style="font-size: 14px;">通知单号</label>
									<div class="col-md-2" style="margin: 0;padding-right:1;">
													<input type="text" id="sinfo"
												class="form-control intentTitle" disabled="disabled" style="font-size: 14px;"/>
									</div>
									<label class="control-label col-md-1" style="font-size: 14px;">开单时间</label>
									<div class="col-md-2" style="margin: 0;padding-right:1;">
													<input type="text" id="curTime"
												class="form-control intentTitle" disabled="disabled" style="font-size: 14px;"/>
									</div>
									<div class="col-md-5 pull-right" style="margin: 0;padding-right:1;text-align:right; ">
									<a href="#/invoice/list" type="button" class="btn btn-default" data-dismiss="modal" style="font-size: 14px;">返回</a>
									<button type="button" onclick="Invoice.clearInfo()" class="btn green" style="font-size: 14px;">清空</button>
									<button type="button" class="btn btn-primary" id="preOpera" data-dismiss="modal" style="font-size: 14px;">准备执行</button>
									<button type="button" class="btn btn-primary" id="save" data-dismiss="modal" disabled style="font-size: 14px;">提交</button>
									</div>
					</div>
					<div class="form-group">
					<label class="control-label col-md-1" style="font-size: 14px;text-align:right;">开票数(吨)</label>
					 <label id="invoiceActualNum" class="control-label col-md-1" style="font-size: 15px; text-align:left;color:blue"></label>
					 <label class="control-label col-md-2" style="font-size: 14px;text-align:right;">历史最大发货量(吨)</label>
					 <label id="loadCapacityNum" class="control-label col-md-1" style="font-size: 15px; text-align:left;color:blue"></label>
					  <label id="maxLoadCapacityNum" class="hidden" ></label>
					</div>
					</div>
					<div class="form-group lastMsgDiv " style="display: none;">
					<label class="control-label col-md-1" style="font-size: 14px;text-align:right;">上次开票记录:</label>
					 <label id="lastMsg" class="control-label " style="font-size: 15px; text-align:left;color:blue"></label>
					</div>
					<h4 class="form-section col-md-12  shipMsg" > <a onclick="InboundOperation.openHide(this,3);" href="javascript:void(0)">
					<i class="fa fa-chevron-left"></i></a></h4>
					 <div class="table-scrollable dialog-warning3"  hidden="true">  
					<table class="table table-striped table-bordered table-hover table-condensed ladingGoods shipMsg" id="arrivalPlanTable">
						<thead>
							<tr>
							<th style="width:170px;">提货单位 </th>
							<th style="width:170px;">货主单位</th>
							<th>货体号</th>
							<th>货批号</th>
							<th style="width:170px;">入库船信</th>
							<th style="width:170px;">实际提货人</th>
							<th style="width:100px;">罐号</th>
							<th>开票提单号</th>
							<th>计划量(吨)</th>
							<th>流向</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					<div class="col-md-12 form-group totalOutboundDiv shipMsg" style="margin-left: 0px; margin-right: 0px;"></div>
					</div>	
				</form>
				<h4 class="form-section col-md-12" >货体信息<span style="display: block; float: right; height: 100%;"><label class="checkbox-inline"> <input type="checkbox" id="isCleanInvoice">是否清单
														</label><label class="checkbox-inline"> <input type="checkbox" id="zeroFlag"> 全部货体
														</label></span></h4>
                <div class="portlet-body">			
				<div class="table-scrollable" >
					<table class="table table-striped table-bordered table-hover table-condensed ladingGoods"
						id="ladingGoodsTable">
						<thead>
							<tr>
							    <th style="width:70px;">单序</th>
								<th>批号</th>
								<th>货体号</th>
								<th>原号</th>
								<th>调号</th>
								<th>有效期(天)</th>
								<th>起计日期</th>
								<th>批量</th>
								<th>封量</th>
								<th>剩余可提量(吨)</th>
								<th>船发计划量(吨)</th>
								<th>待提量(吨)</th>
								<th>开单数量(吨)</th>
								<th>罐号</th>
								<th>仓位</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody id="add_goods_tbody">
						</tbody>
					</table>
					</div>	
					</div>
		</div>

