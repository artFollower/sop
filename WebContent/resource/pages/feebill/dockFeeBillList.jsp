<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
<!--
#timeType{
   border: solid 0px #000;
   text-align:right;
   padding-right:20px;
   padding-left:0px;
    appearance:none;
    -moz-appearance:none;
    -webkit-appearance:none;
    background:url("resource/admin/pages/img/arrow.png") no-repeat scroll right center transparent;
   }
-->
</style>

<div class="rows">
	<div
		class="row page-header-fixed page-sidebar-closed-hide-logo page-sidebar-closed page-quick-sidebar-over-content page-container-bg-solid page-compact">
		<div class="col-md-12">
			<div class="portlet">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-yelp"></i>生产运行部账单
					</div>
				</div>
				<div class="portlet-body">
					<div class="row">
						<div class="col-md-12 tabbable-custom">
							<ul class="nav nav-tabs">
								<li class='active'><a data-toggle="tab"
									href="javascript:void(0);"
									onclick="DockFeeBill.changeTab(this,1)" id="tab1">费用项列表</a></li>
								<li><a data-toggle="tab" href="javascript:void(0);"
									class="base" onclick="DockFeeBill.changeTab(this,2)" id="tab2">账单列表
								</a></li>
							</ul>
							<div class="tab-content"
								style="padding-top: 0px; padding-bottom: 0px;">
								<div class="portlet light" style="padding-left: 0px;">
									<div class="portlet-body">
										<div class="row">
											<div class="col-md-12 col-sm-12">
												<form action="#" class="form-horizontal form-bordered">
														<div class="row">
															<div class="col-md-12  ">
																<div class="col-md-3 feeBillDiv"
																	style="padding-left: 0px;">
																	<label class="control-label col-md-4"
																		style="padding-left: 0px;">账单号:</label>
																	<div class="col-md-8" style="padding:auto 0px;">
																		<input class="form-control" maxlength='64'
																			id="feeBillCode">
																	</div>
																</div>
																<div class="col-md-3 " style="padding-left: 0px;">
																	<label class="control-label col-md-4"
																		style="padding-left: 0px;">开票抬头:</label>
																	<div class="col-md-8" style="padding:auto 0px;">
																	<input class="form-control" id="feeHead">
																	</div>
																</div>
																<!-- <div class="col-md-3 chargeDiv"
																	style="padding-left: 0px;">
																	<label class="control-label col-md-4" style="padding-left: 0px;">发票类型:</label>
																	<div class="col-md-8" style="padding:auto 0px;">
																		<select class="form-control" id="billType">
																			<option value="0" selected>全部</option>
																			<option value="1">手撕发票</option>
																			<option value="2">增值税发票</option>
																		</select>
																	</div>
																</div> -->
																<div class="col-md-3" style="padding-left: 0px;">
																	<label class="control-label col-md-4"
																		style="padding-left: 0px;">贸易类型:</label>
																	<div class="col-md-8" style="padding:auto 0px;">
																		<select class="form-control" id="tradeType">
																			<option value="0" selected>全部</option>
																			<option value="2">内贸</option>
																			<option value="1">外贸</option>
																		</select>
																	</div>
																</div>
																<div class="col-md-3 chargeDiv"
																	style="padding-left: 0px;">
																	<label class="control-label col-md-4"
																		style="padding-left: 0px;">类型:</label>
																	<div class="col-md-8" style="padding:auto 0px;">
																		<select class="form-control" id="type">
																			<option value="0">全部</option>
																			<option value="1" selected>未生成账单</option>
																			<option value="2">已生成账单</option>
																		</select>
																	</div>
																</div>
																<div class="col-md-3 feeBillDiv"
																	style="padding-left: 0px;">
																	<label class="control-label col-md-4"
																		style="padding-left: 0px;">账单状态:</label>
																	<div class="col-md-8"
																		style="padding-left: 0px; padding-right: 0px;">
																		<select class="form-control" id="billStatus">
																			 <option value="-1" selected>全部</option>
																			 <option value="0">未提交</option>
																			 <option value="1">审核中</option>
																			 <option value="2">已审核</option>
																			 <option value="3">未到账</option>
																			 <option value="4">未开票</option>
																			 <option value="5">已开票</option>
																			 <option value="6">已到账</option>
																			 <option value="7">已完成</option>
																			 <option value="8">未完成</option>
																		</select>
																	</div>
																</div>
															</div>
															<div class="col-md-12" style="margin-top: 25px;">
																<div class="col-md-6" style="padding:auto 0px;">
																<label class="control-label col-md-2 chargeDiv" id="arrivalTimeType" style="padding-left: 0px;">到港日期:&nbsp&nbsp&nbsp&nbsp</label>
																	<div class="col-md-2 feeBillDiv feeBillTimeDiv"  style="padding-left: 0px;display:none" >
																		<select class="form-control time" id="timeType">
																			<option value="2">开票时间</option>
																			<option value="1">账单时间</option>
																		</select>
																	</div>
																	<div class="col-md-10 timeDiv" style="padding-left: 0px;">
																		<div class="input-group date-picker input-daterange"
																			data-date-format="yyyy-mm-dd">
																			<input type="text" class="form-control"  
																				id="startTime"> <span
																				class="input-group-addon">到</span> <input
																				type="text" class="form-control"  id="endTime">
																		</div>
																	</div>
																</div>
																
																<div class="col-md-3  chargeDivType"
																	style="padding-left: 0px;">
																	<label class="control-label col-md-4" style="padding-left: 0px;">发票类型:</label>
																	<div class="col-md-8" style="padding:auto 0px;">
																		<select class="form-control" id="billType">
																			<option value="0" selected>全部</option>
																			<option value="1">手撕发票</option>
																			<option value="2">增值税发票</option>
																		</select>
																	</div>
																</div>
																
																
																<div class="col-md-3  btn-group"
																	style="float: left; padding-left: 0px;">
																	<button type="button" class="btn btn-success"
																		id="searchFee">
																		<span class="glyphicon glyphicon-search"></span>&nbsp;
																	</button>
																	<button type="button" style="margin-left: 8px;"
																		class="btn btn-primary " id="reset">
																		<span class="fa fa-undo"></span>&nbsp;
																	</button>
																</div>
															</div>
										<div class="col-md-12" style="margin-top: 25px;">
										<div class="col-md-3 billTimeDiv"
																	style="padding-left: 0px;">
											<label class="control-label col-md-4"
																		">账单时间:</label>
											<div class="col-md-8 " style="padding-left: 0px;">
																		<div class="input-group date-picker input-daterange"
																			data-date-format="yyyy-mm-dd">
																			<input type="text" class="form-control"  
																				id="billTime"> 
																		</div>
																	</div>
																	</div>
																	
																	<div class="col-md-9 "
																	style="padding-left: 0px;">
																	<div class="btn-group buttons col-md-12 searchDiv" >
											
												<button class="btn btn-default btn-circle mar-r-10 chargeDiv"
													type="button" id="makeBill">
													<span class="fa fa-share"></span><span class="text">一键生成</span>
												</button>
												<shiro:hasPermission name="APIERFEEEXCEL">
												 <a class="btn " onclick="DockFeeBill.exportExcel(1)"><span class="fa fa-file-excel-o">
												 &nbsp;导出船代结算列表</span>&nbsp;</a>
												 </shiro:hasPermission>
												 <shiro:hasPermission name="ADOCKFEELISTEXCEL">
												 <a class="btn " onclick="DockFeeBill.exportExcel(2)"><span class="fa fa-file-excel-o">
												 &nbsp;导出账单开票列表(明细)</span>&nbsp;</a>
												 <a class="btn " onclick="DockFeeBill.exportExcel(3)"><span class="fa fa-file-excel-o">
												 &nbsp;导出账单开票列表(汇总)</span>&nbsp;</a>
												 </shiro:hasPermission>
											</div>
											</div>
																	
																	</div>
														</div>
												</form>
											</div>
											
											
											<div class="col-md-12">
												<div data-role="dockFeeBillGrid"></div>

											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>