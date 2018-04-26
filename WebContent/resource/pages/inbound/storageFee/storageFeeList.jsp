<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="rows">
<div class="row page-header-fixed page-sidebar-closed-hide-logo page-sidebar-closed page-quick-sidebar-over-content page-container-bg-solid page-compact">
	<div class="col-md-12">
		<div class="portlet">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-yelp"></i>仓储费<span>
		<a style="padding-left: 20px;" id="printId" class="hidden-print" onclick="InitialFee.exportExcelList()"> 
		 <i class="fa fa-file-excel-o">&nbsp;导出</i></a>
		 </span>
			</div>
		</div>
		<div class="portlet-body">
		<!-- 侧边栏 -->
		<div class="row">
		<div class="col-md-12 tabbable-custom">
		<label class="hidden" id="tabSelect">1</label>
			    <ul class="nav nav-tabs">
			    <li class='active'><a href="javascript:void(0);" onclick="StorageFee.changeTab(this,1)" data-toggle="tab"  id="tab1" >首期费</a></li>
				<li><a href="javascript:void(0);" onclick="StorageFee.changeTab(this,3)" data-toggle="tab" id="tab3">超期货批</a></li>
				<li><a href="javascript:void(0);" onclick="StorageFee.changeTab(this,4)" data-toggle="tab" id="tab4">超期提单</a></li>
				<li><a href="javascript:void(0);" onclick="StorageFee.changeTab(this,2)" data-toggle="tab" id="tab5">超期费列表</a></li>
				<li><a href="javascript:void(0);" onclick="StorageFee.changeTab(this,5)" data-toggle="tab" id="tab6">其他费列表</a></li>
			    </ul>
			      
             <div class="tab-content" style="padding-top: 0px; padding-bottom: 0px;">
             <div class="portlet light" style="padding-left: 0px;">
			   <div class="portlet-body">
				<div class="row">
				<div class="col-md-12 col-sm-12">
				<!-- 搜索条件 -->
				<div style="margin-bottom:15px;">
				
				<div class="clearfix  initialClearFix" style="margin-bottom:5px;" data='1'>
                                <div data-toggle="buttons" class="btn-group ">
                                    <label class="btn btn-default active"  onclick="InitialFee.init(1);">
                                    <input type="radio" class="toggle" >入库货批
                                    </label>
                                    <label class="btn btn-default"  onclick="InitialFee.init(2);">
                                    <input type="radio" class="toggle" >包罐合同
                                    </label>
                                    <label class="btn btn-default"  onclick="InitialFee.init(3);">
                                    <input type="radio" class="toggle" >出库结算
                                    </label>
                                    <label class="btn btn-default"  onclick="InitialFee.init(4);">
                                    <input type="radio" class="toggle" >出口保安费
                                    </label>
                                    <label class="btn btn-default"  onclick="InitialFee.init(5);">
                                    <input type="radio" class="toggle" >通过结算
                                    </label>
                                </div>
                            </div>
				
				<form action="#" class="form-horizontal form-bordered">
				<div id="roleManagerQueryDivId">
						<div class="row">
						  <div class="col-md-12">
						  <div class="col-md-3 cargoDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">货批号:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="cargoCode">
						  </div>
						  </div>
						  <div class="col-md-3 ladingDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">提单号:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="ladingCode">
						  </div>
						  </div>
						  <div class="col-md-3" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">货主:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="clientName">
						  </div>
						  </div>
						  <div class="col-md-3" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">货品:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="productName">
						  </div>
						  </div>
						  <div class="col-md-3 initialDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">单号:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="initialCode">
						  </div>
						  </div>
						  <div class="col-md-3 initialDiv shipDiv" style="padding-left: 0px;display:none">
						  <label class="control-label col-md-4" style="padding-left: 0px;">船名:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="shipId">
						  </div>
						  </div>
						  <div class="col-md-3 exceedDiv "  style="padding-left: 0px;display:none;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">类型:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <select class="form-control" id="exceedType">
						                                <option value="-1" selected>全部</option>
													    <option value="1" >货批</option>
														<option value="2">提单</option>
														</select>
						  </div>
						  </div>
						  <div class="col-md-3 exceedCLDiv"  style="padding-left: 0px;">
						  <label class="control-label col-md-4 " style="padding-left: 0px;">结算类型:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <select class="form-control" id="isFinish">
						                                <option value="0" selected>未结清</option>
													    <option value="1" >已结清</option>
														<option value="2">全部</option>
														</select>
						  </div>
						  </div>
					</div>
					<div class="col-md-12" style="margin-top:25px;">
					<div class="col-md-3 initialDiv goodsInspectDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">商检数量:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						   <input class="form-control" maxlength='64' id="goodsInspect">
						  </div>
						  </div>
						<div class="col-md-3 initialDiv tradeTypeDiv" >
						<label class="control-label col-md-4" style="padding-left: 0px;">贸易类型：</label>
						 <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						<select class="form-control" id="tradeType">
						                                <option value="-1" selected>全部</option>
													    <option value="1" >内贸</option>
														<option value="2">外贸</option>
														</select>
														</div>
						</div>
						<div class="col-md-3" id="timeDiv1" style="padding-left: 0px;padding-right:0px;">
							<label class="control-label col-md-4" id="timeDiv2" style="padding-left: 0px;">时间:</label>
							<div class="col-md-8"  id="timeDiv3" style="padding-left: 0px;">
								<div class="input-group date-picker input-daterange"
									data-date-format="yyyy-mm-dd">
									<input type="text" class="form-control"
										 id="startTime">
									<span class="input-group-addon">到</span> <input
										type="text" class="form-control"  id="endTime">
								</div>
							</div>
						</div>
						<div class="col-md-3 statusDiv"  style="padding-left: 0px;">
						  <label class="control-label col-md-4 " style="padding-left: 0px;">结算状态:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <select class="form-control" id="status">
						                                <option value="-1" selected>全部</option>
													    <option value="0" >未提交</option>
														<option value="1">已提交</option>
														<option value="2">已生成账单</option>
														<option value="3">已开票</option>
														<option value="4">已完成</option>
														</select>
						  </div>
						  </div>
						<div class="col-md-2 col-md-offset-1 btn-group"
								style="float: left; padding-left: 0px;">
								<button type="button"
									class="btn btn-success" id="searchFeeData">
									<span class="fa fa-search"></span>&nbsp;
								</button>
								<button type="button" style="margin-left: 8px;"
									class="btn btn-primary " id="reset">
									<span class="fa fa-undo"></span>&nbsp;
								</button>
							</div>
					</div>
					</div>
					</div>
			    </form>
				</div>
				<!-- 搜索条件 -->
				<!-- 搜索按钮 -->
				<div class="btn-group buttons col-md-12 searchDiv">
	            <button class="btn btn-default btn-circle mar-r-10 " id="addFee" type="button" onclick="StorageFee.addStorageFee()">
				<span class="fa fa-plus"></span>&nbsp;添加&nbsp;</button>
				<!-- <button class="btn btn-default btn-circle mar-r-10 " id="addOilsFee" type="button" onclick="OilsFee.initOilsFeeDialog()">
				<span class="fa fa-plus"></span>&nbsp;添加&nbsp;</button> -->
		      <button type="button" style="margin-left: 8px;display:none;"
									class="btn btn-default btn-circle mar-r-10" id="reback">
									<span class="fa fa-reply "></span><span class="text">返回</span>
								</button>
	            </div>
	            <!-- 搜索按钮 -->
	            <!-- 列表 -->
	            <div class="col-md-12">
	            <div data-role="storageFeeGrid"></div>
	            </div>
	            <!-- 列表 -->
				</div>
				</div>
              </div>
             </div>
		  </div>
		</div>
		</div>
		</div>
		<!-- 侧边栏结束 -->
		
						</div>
					</div>
				</div>
			</div>
