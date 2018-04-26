<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
	<div class="row">
	<div class="col-md-12">
		<div class="portlet">
		<div class="portlet-title">
			<div class="caption" style="width: 100%;">
				<i class="fa fa-list-alt"></i>开票信息<span>
		<a style="padding-left: 20px;" class="hidden-print" onclick="Invoice.exportExcel()">
		 <i class="fa fa-file-excel-o">&nbsp;导出</i></a>
		 </span>
			</div>
		</div>
			<div>
				<form action="#" class="form-horizontal contract-update-form">
				<div id="roleManagerQueryDivId" hidden="true">
					<div class="row">
					<div class="form-body">
						<div class="form-group">
						 <div class="col-md-3">
						 	<label class="control-label col-md-4">货品 </label>
						<div class="col-md-8">
						<input type="text" id="productName" class="form-control intentTitle" />
						</div>
						 </div>
						<div class="col-md-3">
						<label class="control-label col-md-4">货主 </label>
						<div class="col-md-8">
						<input type="text" id="clientName" class="form-control intentTitle" />
						</div>
						</div>
						<div class="col-md-3">
						<label class="control-label col-md-4">开票类型 </label>
						<div class="col-md-8">
						<select class="form-control" id="deliverType">
					    <option value="0" selected>全部</option>
						<option value="1">车发</option>
						<option value="2">船发</option>
						<option value="3">转输</option>
						</select>
						</div>
						</div>
						<div class="col-md-3">
						<label class="control-label col-md-4">出库类型</label>
						<div class="col-md-8">
						<select class="form-control" id="actualType">
					    <option value="-1" selected>全部</option>
						<option value="0">待提</option>
						<option value="1">已提</option>
						</select>
						</div>
						</div>
						</div>
						
						<div class="form-group">
						<div class="col-md-3">
						<label class="control-label col-md-4">车船号</label>
							<div class="col-md-8" id="vsInfo">
								<input type="text" id="vehicleShipNo"
									class="form-control intentTitle"   />
							</div>
						</div>
						<div class="col-md-3">
						<label class="control-label col-md-4">通知单号</label>
							<div class="col-md-8">
								<input type="text" id="serial"
									class="form-control intentTitle"  maxlength="30"/>
							</div>
						</div>
						<div class="col-md-3">
						<label class="control-label col-md-4">提单号</label>
							<div class="col-md-8">
								<input type="text" id="ladingEvidence"
									class="form-control intentTitle"  maxlength="30" style="text-transform: uppercase;font-size: 14px;"/>
							</div>
						</div>
						<div class="col-md-3">
						 <div class="form-group ">
						  <div class="col-md-8 col-md-offset-4 input-group-btn" >
							<button type="button" class="btn btn-success search">
								<span class="fa fa-search"></span>&nbsp;
							</button>
							<button  type="button" style="margin-left:8px;"
								class="btn btn-primary reset">
								<span class="fa fa-undo" title="重置"></span>&nbsp;
							</button>
							</div>
						</div>
						</div>
					</div>
					</div>
					</div>
					</div>
			</form>
			</div>
			<div class="btn-group buttons col-md-12">
			<shiro:hasPermission name="MADDINVOICE">
				<button class="btn  btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-edit"></span><span class="text">开票</span>
				</button>
				</shiro:hasPermission>
				<button class="btn  btn-default btn-circle mar-r-10 btn-search" type="button">
					<span class="fa  fa-search"></span><span class="text">搜索</span>
				</button>
				<shiro:hasPermission name="ATRADEINVOICE">
				<button class="btn  btn-default btn-circle mar-r-10 " onclick="TradeInvoice.initDialog()" type="button">
					<span class="fa  fa-random"></span><span class="text">开票冲销</span>
				</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="ATRADEINVOICE">
				<button class="btn  btn-default btn-circle mar-r-10 " onclick="DeleteInvoice.initDialog()" type="button">
					<span class="fa  fa-list"></span><span class="text">作废记录</span>
				</button>
				</shiro:hasPermission>
				<button class="btn  btn-default btn-circle mar-r-10 " onclick="InvoiceSyncFlowMeter.initDialog()" type="button">
					<span class="fa  fa-exchange"></span><span class="text">手动同步流量计</span>
				</button>
			</div>
			<div class="col-md-12">
			<div data-role="invoicelGrid"></div>
			</div>
		</div>
		</div>
	</div>

