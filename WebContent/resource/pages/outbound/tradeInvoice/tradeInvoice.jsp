<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 100%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">开票冲销</h4>
			</div>
			<div class="modal-body" style="padding-left: 25px; padding-right: 35px;">
				<div class="portlet box blue ">
				<div class="portlet-title">
					</div>
				<div class="portlet-body">
						<form action="#" class="form-horizontal">
							<div class="form-body">
								<div class="form-group">
								 <div class="col-md-3">
									<label class="control-label col-md-3">通知单号</label>
									<div class="col-md-9">
										<input type="text" id="serial" class="form-control">
									</div>
									</div>
									<div class="col-md-3">
									<label class="control-label col-md-3">货主单位</label>
									<div class="col-md-9" >
										<input type="text" id="clientId" class="form-control">
									</div>
									</div> 
									<div class="col-md-3">
									<label class="control-label col-md-3">货品</label>
									<div class="col-md-9" >
										<input type="text" id="productId" class="form-control">
									</div></div>
									 <div class="col-md-3">
									<label class="control-label col-md-3">货体号</label>
									<div class="col-md-9 goodsDiv" >
										<input type="text" id="goodsId" class="form-control">
									</div></div>
									</div>
									<div class="form-group">
									<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2"
											style="padding-left: 0px;">出库时间</label>
										<div class="col-md-10">
											<div class="input-group date-picker input-daterange" data-date-format="yyyy-mm-dd">
											<input type="text" class="form-control" id="startTime">
											<span class="input-group-addon">到</span> 
											<input type="text" class="form-control" id="endTime">
											</div>
										</div>
									</div>
									</div>
									
									 
									<div class="col-md-2 col-md-offset-1 btn-group" style="float: left; padding-left: 0px;">
									<button type="button" class="btn btn-success" id="searchGoodsLog">
									<span class="glyphicon glyphicon-search" title="查询"></span>&nbsp; </button>
									<button type="button" style="margin-left: 8px;" class="btn btn-primary " id="reset">
									<span class="fa fa-undo" title="重置"></span>&nbsp; </button>
									
									</div>
									<div class="col-md-2 col-md-offset-1 btn-group" style="float: left; padding-left: 0px;">
									<button type="button" style="margin-left: 8px;" class="btn btn-primary " id="checkHistory">
									<span class="glyphicon glyphicon-list"  ></span>&nbsp;冲销记录 </button>
									</div>
								</div>
							</div>
						</form>
						<h4>发货记录<span style="display: block; float: right; height: 100%;"></span></h4>
						<div class="portlet-body">			
						<div class="table-scrollable" >
						<div data-role="goodsLogGrid"></div>
						
						<form action="#" class="form-horizontal ">
							<div class="form-body">
							<div class="form-group">
						<label class="control-label col-md-2">冲销理由：</label>
						<div class="col-md-4">
						<textarea rows="1" class="form-control " id="remark"></textarea>
						</div>
						</div>
						<div class="form-group hidden">
						<div class="col-md-3">
						<label class="control-label col-md-3">货主</label>
						<label class="col-md-9" style="text-align:left;" id="changeClientId"></label>
						</div>
						<div class="col-md-3">
						<label class="control-label col-md-3">货品</label>
						<label class="col-md-9" style="text-align:left;" id="changeProductId"></label>
						</div>
						<div class="col-md-3">
						<label class="control-label col-md-3">变更货体</label>
						<div class="col-md-9 goodsDiv" >
							<input type="text" id="goodsId" class="form-control">
						</div></div>
						<div class="col-md-3">
						<label class="control-label col-md-3">货体结存量(吨)</label>
						<label class="col-md-9" style="text-align:left;" id="changeGoodsCurrent"></label>
						</div>
						</div>
						<div class="form-group hidden">			
						<div class="col-md-3">
						<label class="control-label col-md-3">拆分数量</label>
						<div class="col-md-9" >
							<input type="text" id="splitNum"  class="form-control">
						</div></div>
						</div></div>
						</form>
						<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="tradeInvoice" >冲销发货</button>
					<button type="button" class="btn btn-primary hidden" id="changeGoods" >更换货体</button>
					</div>
						</div>
						</div>
						</div>
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>