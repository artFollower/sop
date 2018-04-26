<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.table> tbody > tr > td{
vertical-align:middle;
}
</style>
<div class="col-md-12 confirmreleaseDiv">
			<form class="form-horizontal">
			<label class="hidden" id="weighStatus"></label>
			<label class="hidden" id="arrivalStatus"></label>
			<label class="hidden" id="approveId"></label>
			<div class="col-md-12 outTimeDiv">
			<label class="control-label col-md-2 " id="tp1" style="text-align: right;">出库时间:</label>
							<div class="col-md-4">
							<div class="input-group" id="outTime">
								<div class="col-md-7" style="padding-right: 0px;">
									<input style="text-align: right; border-right: 0;"
										class="form-control form-control-inline date-picker col-md-8"
										type="text" />
								</div>
								<div class="col-md-5"
									style="padding-left: 0px; padding-right: 0px;">
									<input style="border-left: 0;" type="text"
										class="form-control col-md-4  timepicker timepicker-24">
								</div>
							</div> 
							</div>
			</div>
			</form>
			<div class="portlet box blue" style="border-color: #777;margin-top:50px;">
				<div class="portlet-title"  style="background: #777">
					<div class="caption">出库放行确认单</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
					<div id="list-table_wrapper" class="dataTables_wrapper" role="grid">
						<div class="portlet-body form">
							<form action="#" class="form-horizontal">
								<div class="form-inline form-table">
									<table width="100%">
										<tr>
											<td>
												<table id="table_notify"  style="margin-bottom:0px;"
													class="table table-striped table-bordered table-hover table-condensed">
													<thead align=center>
														<tr>
														    <td>发货通知单号</td>
															<td>提货单位</td>
															<td>发货提单号</td>
															<td>货物名称</td>
															<td>货体剩余可提量(吨)</td>
															<td>待提量(吨)</td>
															<td>提单号</td>
															<td>货体编号</td>
															<td>开票数</td>
															<td style="width:108px;">发货量(吨)</td>
															<td>原始发货量(吨)</td>
														</tr>
													</thead>
													<tbody align=center id="statisticsList">
													</tbody>
												</table>
											<div class="col-md-12 form-group totalOutboundDiv" style="margin-left: 0px; margin-right: 0px;"></div>
											</td>
										</tr>
									</table>
								</div>
							<div class="form-group createDiv" style="display: none;margin-top:30px;" >
								<label style="text-align:right;" class="control-label col-md-3">确认人:</label>
									<label style="text-align:left;"  class="control-label col-md-3" id="createUserId" ></label>
								<label style="text-align:right;" class="control-label col-md-3">确认时间:</label>
								 <label style="text-align:left;" class="control-label col-md-3" id="createTime"></label>
						</div>
						<div class="form-group reviewDiv" style="display: none;margin-top:30px;">
								<label style="text-align:right;" class="control-label col-md-3">复核人:</label>
									<label style="text-align:left;" class="control-label col-md-3" id="reviewUserId" ></label>
								<label style="text-align:right;"  class="control-label col-md-3">复核时间:</label> 
								<label style="text-align:left;"  class="control-label col-md-3" id="reviewTime"></label>
						</div>
							</form>
							<shiro:hasPermission name="AOUTBOUNDAMOUNTVERIFYSC">
				<div class="form-body" style="padding-bottom:20px;">
					<h6 class="form-section col-md-12  securityCodeDiv" style="text-align:right;" >验证码审批<a onclick="InboundOperation.openHide(this,5);" href="javascript:void(0)">
					<i class="fa fa-chevron-left"></i></a></h6>
					</div>
					</shiro:hasPermission>
						</div>
					</div>
				
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" onclick="javascript:history.go(-1);">返回</button>
				<shiro:hasPermission name="AOUTBOUNDAMOUNTREBACK"><button type="button" class="btn btn-primary save" id="modify" key="4"  >回退</button></shiro:hasPermission>
				<shiro:hasPermission name="AOUTBOUNDAMOUNTUPDATE"><button type="button" class="btn btn-primary save" id="save" key="0"  >保存</button></shiro:hasPermission>
				<shiro:hasPermission name="AOUTBOUNDAMOUNTUPDATE"><button type="button" class="btn btn-primary save" id="submit" key="1"  >数量确认</button></shiro:hasPermission>
				<shiro:hasPermission name="AOUTBOUNDAMOUNTVERIFY"><button type="button" class="btn btn-primary save" id="pass" key="2" style="display: none" >复核通过</button></shiro:hasPermission>
				<shiro:hasPermission name="AOUTBOUNDAMOUNTVERIFY"><button type="button" class="btn btn-primary save" id="refuse" key="3" style="display: none" >复核不通过</button></shiro:hasPermission>
			</div>
				<!-- 调度验证码审批 -->
			<shiro:hasPermission name="AOUTBOUNDAMOUNTVERIFYSC">
			<div class=" modal-footer dialog-warning5" hidden="true">
			<div class=" form-horizontal col-md-12" style="padding-right: 0px;">
			<div class="input-group col-md-6 col-md-offset-6" style="float:right;">
			 <input type="text" class="form-control" style="width:150px;float:right;" code="AOUTBOUNDAMOUNTVERIFY"  id="reviewCodeUserId" >	
                                 <span class="input-group-btn">
                                 <button type="button" style="width:100px;text-align:center;" disabled class="btn btn-default">复核人</button>
                                 </span>
                                 <label id="securityCodeContent" class="hidden"></label>	
                                <input type="text" class="form-control" style="width:200px;float:right;" placeholder='请输入验证码' id="securityCode" >	
                                 <span class="input-group-btn">
                                 <button type="button" style="width:100px;text-align:center;" class="btn btn-primary" data="0" code="AOUTBOUNDAMOUNTVERIFY" onClick="SecurityCode.init(this)" id="securityCodeBtn">发送验证码</button>
                                 </span>
                              </div>
             <div class="form-group col-md-6 col-md-offset-6" style="margin-top:20px;float:right;" >
             <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			 <button type="button" key="2" code="AOUTBOUNDAMOUNTVERIFY" class="btn btn-primary save" id="pass">复核通过</button>
			 <button type="button" key="3" code="AOUTBOUNDAMOUNTVERIFY" class="btn btn-primary save" id="refuse">复核不通过</button>
             </div>
             </div>
			 </div>
			 </shiro:hasPermission>
				</div>
			</div>
		</div>