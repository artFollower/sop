<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet-body form">
				<form class="form-horizontal">
					<div class="form-body">
							<h4 class="form-section">基本信息</h4>
									<div class="form-group">
										<label class="control-label col-md-1">客户名称:</label>
										<label class="control-label col-md-3" id="tabClientId" style="text-align:left;"></label>
										<label class="control-label col-md-1">货品名称:</label>
										<label class="control-label col-md-3" id="tabProductId" style="text-align:left;"></label>
		                                <label class="control-label col-md-2">货品数量（吨）:</label>
		                                <label class="control-label col-md-2" id="tabAmount" style="text-align:left;"></label>
		                                <label  id="tabId" class="hidden"></label>
									</div>
									<div class="form-group">
										<label class="control-label col-md-1">车船号:</label>
										<label class="control-label col-md-3" id="vehiclePlate" style="text-align:left;"></label>
										<label class="control-label col-md-1">开票时间:</label>
										<label class="control-label col-md-3" id="tabInvoiceTime" style="text-align:left;"></label>
										<label class="control-label col-md-2">提货单位:</label>
										<label class="control-label col-md-2" id="tabLadingClientId" style="text-align:left;"></label>
									</div>
				</div>
				</form>
			</div>
			<div class="tabbable tabbable-custom boxless ">
				<ul class="nav nav-tabs ">
					<li class=""><a data-toggle="tab" href="javascript:void(0);" id="tab1">发货开票</a>
					</li>
					<li class=""><a data-toggle="tab" href="javascript:void(0);" id="tab2">车发作业</a>
					</li>
					<li class=""><a data-toggle="tab" href="javascript:void(0);" id="tab3">数量确认</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active green" id="tab"></div>
				</div>
			</div>
		</div>
	</div>