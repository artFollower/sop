<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="row">
	<div class="col-md-12">
		<h4 class="form-section" style="margin-left: 26px;">基本信息</h4>
		<hr>
		<div class="portlet-body">

			<form class="form-horizontal" role="form">
				<label class="col-md-8 hidden " id="arrivalType"></label>
					<label class="hidden" id="base_id" ></label>
					<label class="hidden" id="productId" ></label> 
				<div class="form-group ">
					<label id="tp1" class="col-md-1 control-label ">船名:</label>
					<div class="col-md-2 ">
						<label class="control-label" id="base_shipName"></label>
					</div>
					<label id="tp2" class="control-label col-md-1">预计到港时间: </label>
					<div class="col-md-2">
						<label class="control-label" id="base_arrivalTime"></label>
					</div>
					<label class="control-label col-md-1 notTransport">泊位: </label>
					<div class="col-md-2 notTransport">
						<label class="control-label"  id="base_berth_id"></label>
					</div>
					<label class="control-label col-md-1">货物名称: </label>
					<div class="col-md-2">
						<label class="control-label" id="base_productName"></label>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-1">货物数量（吨）: </label>
					<div class="col-md-2">
						<label class="control-label" id="base_productNum"></label>
					</div>
					<label class="control-label col-md-1 notTransport">载重吨（吨）: </label>
					<div class="col-md-2 notTransport">
						<label class="control-label" id="base_shipLoad"></label>
					</div>
					<label class="control-label col-md-1 notTransport">净吨（吨）: </label>
					<div class="col-md-2 notTransport">
						<label class="control-label" id="base_netTons"></label>
					</div>
					<label class="control-label col-md-1 notTransport">到港吃水（米）: </label>
					<div class="col-md-2 notTransport">
						<label class="control-label" id="base_draught"></label>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-1 notTransport">船长（米）: </label>
					<div class="col-md-2 notTransport" >
						<label class="control-label" id="base_shipLength"></label>
					</div>
					<label class="control-label col-md-1 notTransport">船宽（米）: </label>
					<div class="col-md-2 notTransport" >
						<label class="control-label" id="base_shipWidth"></label>
					</div>
				</div>
				<h4 class="form-section" style="margin-left: 26px;">
					货体信息&nbsp;<a onclick="InboundOperation.openHide(this,3);"
						href="javascript:void(0)"><i id="goodsinfo_i"
						class="fa fa-chevron-left"></i></a>
				</h4>
				<div class="form-group dialog-warning3" hidden="true">
					<div class="col-md-8 col-md-offset-2">
						<div data-role="deliverGoodsInfo" ></div>
					</div>
				</div>
			</form>
		</div>

		<div class="tabs-top tabbable tabbable-custom boxless portlet-body">
			<ul class="nav nav-tabs ">
			 <shiro:hasPermission name="MSHIPDELIVERYPLAN">
				<li><a data-toggle="tab1" href="javascript:void(0);" id="tab1">作业计划</a>
				</li>
				</shiro:hasPermission>
				 <shiro:hasPermission name="MSHIPDELIVERYBERTHPLAN">
				<li><a data-toggle="tab" href="javascript:void(0);" class="notTransport" id="tab2">靠泊方案</a>
				</li>
				</shiro:hasPermission>
				 <shiro:hasPermission name="MSHIPDELIVERYBERTHREADY">
				 <li><a data-toggle="tab" href="javascript:void(0);" id="tab3">发货准备</a>
				</li>
				</shiro:hasPermission>
				 <shiro:hasPermission name="MSHIPDELIVERYAMOUNTAFFIRM">
				<li><a data-toggle="tab" href="javascript:void(0);" id="tab4">数量确认</a>
				</li>
				</shiro:hasPermission>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active green" id="tab"></div>
			</div>
		</div>
	</div>
</div>