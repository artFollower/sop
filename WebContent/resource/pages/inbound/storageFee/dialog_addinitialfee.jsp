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
				<h4 class="modal-title">江苏长江石油化工有限公司储品仓储费结算清单</h4>
			</div>
			<div class="modal-body" style="padding-left:25px; padding-right:35px;">
			<div class="input-group col-md-12 accountTime">
				<label class="col-md-2 control-label"  style="text-align:right;">结算日期:</label>
			         <input style="text-align:center;border: 1px solid #ccc" id="accountTime"  class="date-picker col-md-3"  type="text"/>
				<label class="col-md-2 col-md-offset-2 control-label" style="text-align:right;">结算单编号:</label>
				<label class="col-md-3 control-label feeKey" key="0" style="text-align:left" id="code"></label>
				<label class="hidden initialType">2</label>
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
                        <label class="col-md-4 control-label">货主单位:</label>
                        <div class="col-md-8">
                        <input class=" form-control feeKey" style="text-align:left" maxlength="64" id="clientName">
                        </div>
                        </div>
                       <div class="col-md-6">
                       <label class="col-md-4 control-label">货物名称:</label>
                        <div class="col-md-8">
                        <input class=" form-control feeKey" style="text-align:left" maxlength="64" id="productName">
                        </div>
                       </div>
                       </div>  
                       <div class="form-group">
                       <div class="col-md-6">
                         <label class="col-md-4 control-label">合同号:</label>
                          <div class="col-md-8 ">
                        <div class="input-group contractCode">
                                 <input type="text" class=" form-control feeKey" style="text-align:left" maxlength="64" id="contractCode">
                              </div>
                        </div>
                        </div>
                          <div class="col-md-6">
                         <label class="col-md-4 control-label">合同类型:</label>
                          <div class="col-md-8">
                        <label class=" control-label " style="text-align:left"  id="contractType"></label>
                        </div>
                        </div>
                        
                       </div>  
                       <div class="form-group">
                       <div class="col-md-6">
                        <label class="col-md-4 control-label">货批号:</label>
                        <div class="col-md-8">
                         <input class=" form-control feeKey" style="text-align:left" maxlength="64" id="cargoCode">
                        </div>
                       </div>
                        <div class="col-md-6">
                       <label class=" col-md-4 control-label">数量(吨):</label>
                       <div class="col-md-8">
                       <input class="form-control feeKey" onkeyup="config.clearNoNum(this)" maxlength="16" style="text-align:left" id="goodsInspect">
                       </div>
                       </div>
                       </div>   
                       <div class="form-group">
                       <div class="col-md-7">
                          <label class="col-md-2 control-label">周期<a href="javascript:void(0)" 
						onclick="InboundOperation.openHide(this,3);"><i
						class="fa fa-chevron-left"></i></a></label> 
                          <div class="col-md-10">
						<div class="input-group date-picker input-daterange"
							data-date-format="yyyy-mm-dd">
							<input type="text" class="form-control" name="startTime" id="startTime"
								id="startTime"> <span class="input-group-addon">到</span>
							<input type="text" class="form-control" name="endTime" 
								id="endTime">
								<div class="input-group-btn" style="padding-left:0px;padding-right:0px;">
                               <button type="button" class="btn btn-primary " id="checkCargoList">确认</button>
                        </div>
						</div>
					</div>
                        </div> 
                        </div>
                        
                        <div class="form-group col-md-12 dialog-warning3" hidden="true">
                          <div class="scroller " style="height:210px;overflow-y:scroll;padding-left:35px;padding-right:15px;">
	                      <div data-role="otherFeeGrid"></div>
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
                     <div class="col-md-12" style="padding-left:35px;padding-right:35px;">
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
			<label class="control-label col-md-12" style="text-align:right"><span class="required">*</span>该添加首期费只针对包罐合同</label>
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