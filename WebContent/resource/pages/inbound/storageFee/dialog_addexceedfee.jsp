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
				<h4 class="modal-title">江苏长江石油化工有限公司储品超期费结算清单</h4>
			</div>
			<div class="modal-body" style="padding-left:25px; padding-right:35px;">
			<div class="input-group col-md-12">
				<label class="col-md-2 control-label"  style="text-align:right;">结算日期:</label>
			         <input style="text-align:center;border: 1px solid #ccc" id="accountTime"  class="date-picker col-md-3"  type="text"/>
				<label class="col-md-2 col-md-offset-2 control-label" style="text-align:right;">结算单编号:</label>
				<label class="col-md-3 control-label feeKey" key="0" style="text-align:left" id="code"></label>
			    </div>
			    <div class="portlet box blue">
						<div class="portlet-title">
							<div class="tools">
								<a class="hidden-print" style="color: white;" onclick="StorageFee.exportXML(this,51)"> <i class="fa fa-file-excel-o">&nbsp;导出</i>
								</a>
							</div>
						</div>
						<div class="portlet-body">
				<form action="#" class="form-horizontal">
                     <div class="form-body">
                      <div class="row">
                      <label class="col-md-8 hidden feeKey" id="id"></label>
                      <label class="col-md-8 hidden feeKey" id="exceedType"></label>
                       <label class="col-md-8 hidden feeKey" id="cargoId"></label>
                       <label class="col-md-8 hidden feeKey" id="ladingId"></label>
                       <label class="col-md-8 hidden " id="feeId"></label>
                       <div class="form-group">
                        <div class="col-md-6">
                        <label class="col-md-4 control-label">货主单位:</label>
                        <div class="col-md-8">
                        <input class="form-control" maxlength="64" id="clientName">
                        </div>
                        </div>
                       <div class="col-md-6">
                       <label class="col-md-4 control-label">货物名称:</label>
                        <div class="col-md-8">
                        <input class="form-control" maxlength="64" id="productName">
                        </div>
                       </div>
                       </div>  
                       <div class="form-group">
                       <div class="col-md-6 cargoDiv">
                       <label class="col-md-4 control-label">货批号:</label>
                        <div class="col-md-8 cargoCode">
                        <input class="form-control" maxlength="64" id="cargoCode">
                        </div>
                       </div>
                       <div class="col-md-6 ladingDiv">
                       <label class="col-md-4 control-label">提单号:</label>
                        <div class="col-md-8 ladingCode">
                        <input class="form-control" maxlength="64" id="ladingCode">
                        </div>
                       </div>
                        </div>  
                       <div class="form-group">
                       <div class="col-md-6">
                       <label class="col-md-4 control-label">起计日期:</label>
                       <div class="col-md-8">
                       <input class="form-control date-picker" id="startTime">
                       </div>
                       </div>
                       <div class="col-md-6">
                       <label class="col-md-4 control-label">止计日期:</label>
                       <div class="col-md-8">
                       <input class="form-control date-picker" id="endTime">
                       </div>
                       </div>
                       </div>
                       <div class="form-group">
                       <div class="col-md-6">
                        <label class="col-md-4 control-label">超期费单价:</label>
                        <div class="col-md-8">
                        <input class="form-control unitFee" onkeyup="config.clearNoNum(this,2)" id="exceedFee" maxlength="16" >
                        </div>
                       </div>
                       <div class="col-md-6 hidden">
                       <label class="col-md-4 control-label">总数量(吨):</label>
                       <div class="col-md-8">
                       <input class="form-control feeCount" onkeyup="config.clearNoNum(this)" maxlength="16"  id="exceedCount">
                       </div>
                       </div>
                       </div> 
                       <div class="form-group">
                       <div class="col-md-6">
                       <label class="col-md-4 control-label">总金额(元):</label>
                       <div class="col-md-8">
                       <input class="form-control totalFee" disabled onkeyup="config.clearNoNum(this,2)" maxlength="16" id="exceedTotalFee">
                       </div>
                       </div>
                       <div class="col-md-6">
                       <label class="col-md-4 control-label">开票抬头:</label>
                       <div class="col-md-8">
                       <input class="form-control feeHead" maxlength="16" id="exceedBillHead">
                       </div>
                       </div>
                       </div>
                       
                       <div class="col-md-12 itemDayFeeDiv">
                      <div class="col-md-12 btn-group" >
                        <a href="javascript:void(0)" class="btn" style="float:right;"  id="openTurnList"><i class="fa fa-reorder">&nbsp;查看</i></a>
                       <a href="javascript:void(0)" class="btn" style="float:right;" onclick="StorageFee.exportXML(this,53)"><i class="fa fa-file-excel-o" >&nbsp;导出</i>
								</a>
								</div>
                       <div class="scroller" style="height:210px;overflow-y:scroll;">
                       <div data-role="exceedFeeGrid"></div>
                       </div>
                       </div> 
                       
                       <div class="form-group" style="margin-top:35px;">
                          <label class="col-md-2 control-label">备注:</label>
                          <div class="col-md-9">
                          <textarea class="form-control" rows="2" maxlength="100" id="description"></textarea>
                          </div>
                       </div> 
                       
                       <div class="form-group create">
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
				<button type="button" class="btn btn-primary back" style="display:none;">回退</button>
				<button type="button" class="btn btn-success save" key="0" >保存</button>
				<button type="button" class="btn btn-primary save" key="1" >提交</button>
			</div>
		</div>
	</div>
</div>