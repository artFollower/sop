<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 69%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">江苏长江石油化工有限公司储品出口保安费结算清单</h4>
			</div>
			<div class="modal-body" style="padding-left:25px; padding-right:35px;">
			<div class="input-group col-md-12">
				<label class="col-md-2 control-label"  style="text-align:right;">结算日期:</label>
			         <input style="text-align:center;border: 1px solid #ccc" id="accountTime"  class="date-picker col-md-3"  type="text"/>
				<label class="col-md-2 col-md-offset-2 control-label" style="text-align:right;">结算单编号:</label>
				<label class="col-md-3 control-label feeKey" key="0" style="text-align:left" id="code"></label>
				<label class="hidden" id="arrivalId"></label>
				<label class="hidden initialType">4</label>
			    </div>
			    <div class="portlet box blue">
						<div class="portlet-title">
							<div class="tools">
								<a class="hidden-print" style="color: white;" onclick="InitialFee.exportExcel(this,50)"> <i class="fa fa-file-excel-o">&nbsp;导出</i>
								</a>
							</div>
						</div>
						<div class="portlet-body">
				<form action="#" class="form-horizontal">
                     <div class="form-body">
                      <div class="row">
                      <label class="col-md-8 hidden feeKey" id="id"></label>
                       <div class="form-group">
                        <div class="col-md-6">
                        <label class="col-md-4 control-label">提货单位:</label>
                        <label class="col-md-8 control-label feeKey" style="text-align:left" id="clientName"></label>
                        </div>
                       <div class="col-md-6">
                       <label class="col-md-4 control-label">货物名称:</label>
                        <label class="col-md-8 control-label feeKey" style="text-align:left" id="productName"></label>
                      
                       </div>
                       </div>  
                       <div class="form-group">
                        <div class="col-md-6">
                        <label class="col-md-4 control-label">船舶英文名:</label>
                        <label class="col-md-8 control-label feeKey" style="text-align:left" id="shipName"></label>
                        </div>
                       <div class="col-md-6">
                       <label class="col-md-4 control-label">船舶中文名:</label>
                        <label class="col-md-8 control-label feeKey" style="text-align:left" id="shipRefName"></label>
                       </div>
                       </div> 
                        <div class="form-group">
                       <div class="col-md-6">
                       <label class="col-md-4 control-label">到港日期:</label>
                        <label class="col-md-8 control-label " style="text-align:left" id="arrivalTime"></label>
                       </div>
                       <div class="col-md-6">
                        <label class="col-md-4 control-label">实发数(吨):</label>
                        <label class="col-md-8 control-label feeKey" style="text-align:left" id="contractNum"></label>
                       </div>
                       </div> 
                     <div class="form-group" style="margin-top:25px;">
                     <div class="btn-group buttons handleFee" style="padding-left:35px;">
							<button class="btn btn-default btn-circle mar-r-10 addFeeCharge" type="button">
								<span class="fa fa-plus"></span>添加
							</button><button class="btn btn-default btn-circle mar-r-10 editFeeCharge" key="0" type="button">
								<span class="fa fa-edit"></span>编辑
							</button>
						</div>
                     <div class="col-md-12" style="padding-left:35px;padding-right:35px;margin-top:5px;">
							<table class="table table-striped table-hover table-bordered" style="width:100%"
								id="feeTable">
								<thead>
									<th style="text-align:center" width="120">费用类型</th>
									<th style="text-align:center" width="120">单价(元/吨)</th>
									<th style="text-align:center" width="120">数量(吨)</th>
									<th style="text-align:center" width="120">合计金额(元)</th>
									<th style="text-align:center" width="120">开票抬头</th>
									<th style="text-align:center;display:none" width="60" class='removeFeeCharge'>操作</th>
								</thead>
								<tbody id="feeTbody">
								
								<tr id='totalTr'><td colspan='5'>&nbsp;&nbsp;&nbsp;合计金额：<label id='totalFee'></label>&nbsp;元</td></tr>
								</tbody>
							</table>
                        </div>
                       </div> 
                       <div class="form-group" style="margin-top:25px;">
                          <label class="col-md-2 control-label">备注:</label>
                          <div class="col-md-9">
                          <textarea class="form-control" rows="2" maxlength="100" id="description"></textarea>
                          </div>
                       </div> 
                       <div class="form-group" id="createUserDiv">
                        <div class="col-md-6 col-md-offset-6">
                         <label class="col-md-4 control-label">制单:</label>
                        <label class="col-md-8 control-label feeKey" style="text-align:left" id="createUserName"></label>
                        </div>
                       </div> 
                       </div>
                       </div>
			     </form>
			</div>
			</div>
			</div>
			<div class="modal-footer controlDiv">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary back " style="display:none;">回退</button>
				<button type="button" class="btn btn-success save" key="0" >保存</button>
				<button type="button" class="btn btn-primary save" key="1" >提交</button>
			</div>
		</div>
	</div>
</div>