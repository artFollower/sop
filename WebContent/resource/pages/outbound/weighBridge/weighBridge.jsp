<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
<style type="text/css">
.form-group {
	margin-bottom: 6px;
	margin-top: 6px;
}
</style>
<!-- <script>
    jQuery(document).ready(function() {       
       ComponentsPickers.init();
    });   
</script> -->
</head>
<body id="wBody">
    <input type="hidden" id="oilsHidden"/>
    <input type="hidden" id="isCleanInvoice"/>
    <input type="hidden" id="serialHidden">
	<div class="portlet">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-file-text-o"></i>称重开票信息
			</div>
		</div>
		<div class="portlet-body weighBridge">
			<div class="row">
			<form action="#" class="form-horizontal contract-update-form">
				<fieldset class="col-md-12" style=" border: 1px solid #000000; -moz-border-radius: 9px; margin-bottom: 1px; margin-top: 1px;">
					<legend><strong style="font-size: 14px;">提货单位和货品</strong></legend>
					<div class="form-group ">
						<label class="control-label col-md-1" style="font-size: 14px;">原始货主</label>
						<div class="col-md-4" id="select-intentionId">
							<input type="text" id="yuanshihuozhu" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control intention">
						</div>
						<label class="control-label col-md-1" style="font-size: 14px;">客户编号</label>
						<div class="col-md-3" id="select-intentionId">
							<input type="text" id="ladingClientCode" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control input-sm intention">
						</div>
						<label class="control-label col-md-1" style="font-size: 14px;">提货凭证</label>
						<div class="col-md-2" id="select-intentionId">
							<input type="text" id="ladingEvidence" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control input-sm intention">
						</div>
					</div>
					<div class="form-group ">
						<label class="control-label col-md-1" style="font-size: 14px;">提货单位</label>
						<div class="col-md-4" id="select-intentionId">
							<input type="text" id="ladingClientName" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control input-sm intention">
						</div>
						<label class="control-label col-md-1" style="font-size: 14px;">进货船信</label>
						<div class="col-md-3" id="select-intentionId">
							<input type="text" id="shipInfo" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control input-sm intention">
						</div>

						<label class="control-label col-md-1" style="font-size: 14px;">车船号</label>
						<div class="col-md-2" id="select-intentionId">
							<input type="text" id="vsName" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control input-sm intention">
						</div>

					</div>
					<div class="form-group ">
						<label class="control-label col-md-1" style="font-size: 14px;">原号</label>
						<div class="col-md-2" id="select-intentionId">
							<input type="text" id="yuanhao" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control input-sm intention">
						</div>
						<label class="control-label col-md-1" style="font-size: 14px;">批号</label>
						<div class="col-md-2" id="select-intentionId">
							<input type="text" id="cargoCode" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control input-sm intention">
						</div>
						<label class="control-label col-md-1" style="font-size: 14px;">调号</label>
						<div class="col-md-2" id="select-intentionId">
							<input type="text" id="diaohao" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control input-sm intention">
						</div>
						<label class="control-label col-md-1" style="font-size: 14px;">开单人</label>
						<div class="col-md-2" id="select-intentionId">
						    <input type="text" id="createUserName" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control input-sm intention">
							<!-- <input type="text" id="createUserName1" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control input-sm intention"> -->
						</div>
					</div>
					<div class="form-group ">
					    <label class="control-label col-md-1" style="font-size: 14px;">开票罐号</label>
						<div class="col-md-2" id="select-intentionId">
							<input type="text" id="tank" disabled="disabled" data-provide="typeahead" data-type="Require" class="form-control input-sm intention" maxlength="255">
						</div>
						<label class="control-label col-md-1" style="font-size: 14px;">仓位</label>
						<div class="col-md-2" id="select-intentionId">
							<input type="text" id="storageInfo" disabled="disabled" data-provide="typeahead" data-type="Require" class="form-control input-sm intention" maxlength="255">
						</div>
						<label class="control-label col-md-1" style="font-size: 14px;">备注</label>
						<div class="col-md-2" id="select-intentionId">
							<input type="text" id="desc" disabled="disabled" data-provide="typeahead" data-type="Require" class="form-control input-sm intention" maxlength="255">
						</div>
						<label class="control-label col-md-1 truckState" style="font-size: 14px;display: none;" >车发状态：</label>
						<label class="control-label col-md-1 truckState" style="font-size: 14px;text-align:left;display: none;" id="truckState"></label>
					</div>
					<div class="clearfix"></div>
				</fieldset>
				<fieldset class="col-md-7 pull-left" style=" border: 1px solid #000000; -moz-border-radius: 9px;">
					<legend><strong style="font-size: 14px;">发货情况</strong></legend>
					<div class="form-group ">
						<label class="control-label col-md-2" style="font-size: 14px;">入库时间</label>
						<div class="col-md-4" id="select-intentionId">
							<input type="hidden" class="form-control input-sm " id="type" />
							<input type="hidden" class="form-control input-sm " id="goodsId" />
							 <input type="hidden" class="form-control input-sm " id="id" />
							  <input type="hidden" class="form-control input-sm " id="status" />
							  <input type="hidden" class="form-control input-sm " id="flowMeterNum" />
							   <input type="text" id="intoTime" data-provide="typeahead"
								disabled="disabled" data-type="Require" class="form-control input-sm intention">
						</div>
						<label class="control-label col-md-2" style="font-size: 14px;">进库重（吨）</label>
						<div class="col-md-4" id="select-intentionId">
							<input type="text" id="inWeigh" disabled="disabled" data-provide="typeahead" onkeyup="weighBridge.setDeliverNum()" data-type="Require" class="form-control input-sm intention" maxlength="19">
						</div>
					</div>
					<div class="form-group ">
						<label class="control-label col-md-2" style="font-size: 14px;">出库时间</label>
						<div class="col-md-4" id="select-intentionId">
							<input type="text" id="outTime" data-provide="typeahead"  disabled="disabled" data-type="Require" class="form-control input-sm intention">
						</div>
						<label class="control-label col-md-2" style="font-size: 14px;">出库重（吨）</label>
						<div class="col-md-4" id="select-intentionId">
							<input type="text" id="outWeigh" disabled="disabled" data-provide="typeahead" onkeyup="weighBridge.setDeliverNum()" data-type="Require" class="form-control input-sm intention" maxlength="19">
						</div>
					</div>
					<div class="form-group ">
						<label class="control-label col-md-2" style="font-size: 14px;">开单数量（吨）</label>
						<div class="col-md-4" id="select-intentionId">
						    <input type="hidden" id="deliverNum1">
							<input type="text" id="deliverNum" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control input-sm intention">
						</div>
						<label class="control-label col-md-2" style="font-size: 14px;">发运数（吨）</label>
						<div class="col-md-4" id="select-intentionId">
							<input type="text" id="deliveryNum" data-provide="typeahead" data-type="Require" disabled="disabled" class="form-control input-sm intention" maxlength="19">
						</div>
					</div>
					<div class="form-group ">
						<label class="control-label col-md-2" style="font-size: 14px;">罐号</label>
						<div class="col-md-4" id="select-intentionId">
							<input type="text" id="tankName" data-provide="typeahead" data-type="Require" class="form-control input-sm intention" maxlength="10">
						</div>
						<label class="control-label col-md-2" style="font-size: 14px;">发货口</label>
						<div class="col-md-4" id="select-intentionId">
							<input type="text" id="inPort" data-provide="typeahead" data-type="Require" class="form-control input-sm intention" maxlength="3">
						</div>
					</div>
					<div class="form-group ">
						<label class="control-label col-md-2" style="font-size: 14px;">表返重量（吨）</label>
						<div class="col-md-4" id="select-intentionId">
							<input type="text" id="measureWeigh" data-provide="typeahead" data-type="Require" class="form-control input-sm intention" disabled="disabled">
						</div>
						<label class="control-label col-md-2" style="font-size: 14px;">出库称重人</label>
						<div class="col-md-4" id="select-intentionId">
							<input type="hidden" class="form-control input-sm inputId" id="outStockPersonId" />
						    <input type="text" id="outStockPerson" data-provide="typeahead" data-type="Require"  class="form-control input-sm intention" disabled="disabled">
						</div>
					</div>
					<div class="form-group ">
						<label class="control-label col-md-2" style="font-size: 14px;">进库称重人</label>
						<div class="col-md-4" id="select-intentionId">
						    <input type="hidden" class="form-control input-sm inputId" id="inStockPersonId" />
							<input type="text" id="inStockPerson" data-provide="typeahead" data-type="Require" class="form-control input-sm intention" disabled="disabled">
						</div>
						<label class="control-label col-md-2" style="font-size: 14px;">总开单数（吨）</label>
						<div class="col-md-4" id="select-intentionId">
							<input type="text" id="totalAmount" data-provide="typeahead" data-type="Require" class="form-control input-sm intention" disabled="disabled">
						</div>
					</div>
					<div class="form-group ">
						<label class="control-label col-md-2" style="font-size: 14px;">说明</label>
						<div class="col-md-10" id="select-intentionId">
							<input type="text" id="description" data-provide="typeahead" data-type="Require" class="form-control input-sm intention" maxlength="100">
						</div>
					</div>
					<div class="form-group" >
					<div class="col-md-6 overCountDiv" style="display:none;">
					<label class="control-label col-md-4" style="font-size: 14px;">实发超出量(吨)</label>
					<label class="control-label col-md-8" id="overCount" style="color:red;text-align:left;"></label>
					</div>
					<div class="col-md-6 overCountDiv" style="display:none;">
					<label class="control-label col-md-4" style="font-size: 14px;">表返超出量(吨)</label>
					<label class="control-label col-md-8" id="overMeasureCount" style="color:red;text-align:left;"></label>
					</div>
					</div>
				</fieldset>
				<fieldset class="col-md-5" style=" border: 1px solid #000000; -moz-border-radius: 9px;">
					<legend><strong style="font-size: 14px;">发货通知单情况</strong></legend>
					<div class="form-group col-md-13">
						<label class="control-label col-md-2" style="font-size: 14px;">单号</label>
						<div class="col-md-6" id="select-intentionId">
							<input type="text" id="serialNo" data-provide="typeahead" data-type="Require" class="form-control input-sm intention"   maxlength="11">
						</div>
						<div class="col-md-2" id="select-intentionId">
							<button class="btn blue" style="position: relative; margin-left: 18 px;" type="button" onclick="weighBridge.resetData();">复位</button>
						</div>
						<div class="col-md-2" id="select-intentionId" style="display: none;">
							<button class="btn btn-success search" style="position: relative; margin-left: 18 px;" onclick="weighBridge.getGoodsLogInfo()" type="button">
								<span class="fa fa-search"></span>&nbsp;
							</button>
						</div>
					</div>
					<div class="form-group col-md-13">
						<label class="control-label col-md-2" style="font-size: 14px;">地磅</label>
						<div class="col-md-4" id="select-intentionId">
							<select class="form-control" id="dibang" ></select>
						</div>
						<div class="col-md-2">
						    <shiro:hasPermission name="WEIGHREADY"> 
								<button style="margin-right: 10px;" type="button" class="btn success button-open red">准备执行</button>
								<button type="button" class="btn success hidden button-close red">取消准备</button>
							</shiro:hasPermission>
						</div>
						<div class="col-md-1" style="margin-left: 10px;">
							<shiro:hasPermission name="WEIGHCONFIRM">
								<button type="button" class="btn blue confirm" disabled style="margin-left: 6px;font-size: 14px;">确定</button>
							</shiro:hasPermission>
						</div>
					</div>
					<div class="form-group col-md-12">
					    <input type="hidden" id="productIdHidden"/>
					    <label class="control-label col-md-3" style="font-size: 14px;">联单数 </label>
					   <label class="control-label col-md-2" style="font-size: 16px;color:red;text-align: left;" id="notifyCount"></label>
					   <label class="control-label col-md-5" style="font-weight:bolder;font-size: 13px;text-align:left;" id="productName"></label>
					</div>
					<div class="form-group col-md-12">
						<div class="col-md-12 text-red hidden" id="dVal-info">
							<i class="fa fa-exclamation mar-r-10 text-yellow"></i>网络错误,当前读数也许不是最新值,请联系管理员.
						</div>
						<div class="col-md-12">
							<input class="form-control"   id="dVal"  style="width: 100%; font-size: 40px;color:#228B22;height:70px; background-color: #000;"/> 
						</div>
					</div>
					<h4 class="form-section col-md-12"  >
					<a onclick="InboundOperation.openHide(this,3);"
						href="javascript:void(0)"><i class="fa fa-chevron-left"></i></a></h4>
					 <div class="form-group col-md-12 dialog-warning3" hidden="true">
					<div class="form-group col-md-11 showNotifyList"  >
					<label class="col-md-4" style="text-align: right;">通知单号</label>
					<label class="col-md-4" style="text-align: right;">开票数</label>
					<label class="col-md-4" style="text-align: right;">实发数</label>
					</div>
					<div class="form-group col-md-12">
					<div class=" scroller dataNotifyList" style="height:80px; overflow-y:scroll;">
					</div>
					</div>
					</div>
					<div class="form-group col-md-12 pull-right">
					    <input type="checkbox" id="printAll" class="printAll"/>联印
						<a onclick="javascript:weighBridge.print();" class="hidden-print"> <i class="fa fa-print" style="font-size: 14px;">&nbsp;打印</i></a>
					</div>
				</fieldset>
			</form>
	</div>
		</div>
	</div>
