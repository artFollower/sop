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
   .dropdown-checkboxes label {
    color: #333;
    display: block;
    font-weight: 300;
    margin-bottom: 4px;
    margin-top: 4px;
}
-->
</style>
<div class="rows">
<div class="row page-header-fixed page-sidebar-closed-hide-logo page-sidebar-closed page-quick-sidebar-over-content page-container-bg-solid page-compact">
	<div class="col-md-12">
		<div class="portlet">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-yelp"></i>账单管理
		</div>
		<div class="portlet-body">
		<!-- 侧边栏 -->
		<div class="row">
		<div class="col-md-12 tabbable-custom">
			    <ul class="nav nav-tabs">
			    <li class='active'>
				<a data-toggle="tab" href="javascript:void(0);" onclick="feeBill.changeTab(this,1)" id="tab1" >收费项列表</a>
				</li>
				<li>
				<a data-toggle="tab" href="javascript:void(0);" class="base" onclick="feeBill.changeTab(this,2)" id="tab2">账单列表
				</a>
				</li>
			    </ul>
             <div class="tab-content" style="padding-top: 0px; padding-bottom: 0px;">
             <div class="portlet light" style="padding-left: 0px;">
			   <div class="portlet-body">
				<div class="row">
				<div class="col-md-12 col-sm-12">
				<!-- 搜索条件 -->
				<form action="#" class="form-horizontal form-bordered">
				<div id="roleManagerQueryDivId">
						<div class="row">
						  <div class="col-md-12  ">
						  <div class="col-md-3 feeBillDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">账单号</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="billCode">
						  </div>
						  </div>
						  <div class="col-md-3 " style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">货主</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="clientName">
						  </div>
						  </div>
						  <div class="col-md-3" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">开票抬头</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="feeHead">
						  </div>
						  </div>
						  <div class="col-md-3 feeBillDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">发票号</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="billingCode">
						  </div>
						  </div>
						  <div class="col-md-3 chargeDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">类型</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <select class="form-control" id="type">
													    <option value="0" >全部</option>
														<option value="1" selected>未生成账单</option>
														<option value="2">已生成账单</option>
														</select>
						  </div>
						  </div>
						  
					</div>
					<div class="col-md-12"  style="margin-top:15px;">
					 <div class="col-md-3 feeBillDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">费用类型</label>
						  <div class="actions col-md-8  actions  pull-right "> <div class="btn-group ">
                                        
						  <button aria-expanded="true" data-toggle="dropdown"  id="feeType" class="btn btn-default  ">请选择费用类型
						  </button>
						  <button aria-expanded="true" data-toggle="dropdown" class="btn btn-default dropdown-toggle" type="button"><i class="fa fa-angle-down" style="height:14px;margin-top:6px;"></i></button>
						  <div id="feeTypeDiv" class="dropdown-menu hold-on-click dropdown-checkboxes  pull-right">
						  <label><input type="checkbox" value="1" checked >仓储费</label>
						  <label><input type="checkbox" value="2" checked >保安费</label>
						  <label><input type="checkbox" value="3" checked >包罐费</label>
						  <label><input type="checkbox" value="4" checked >超量费</label>
						  <label><input type="checkbox" value="5" checked >超期费</label>
						  <label><input type="checkbox" value="6" checked >其他费</label>
						  <label><input type="checkbox" value="8" checked >通过费</label>
						  </div></div></div>
						  </div>
						  <div class="col-md-3 feeBillDiv">
						   <label class="control-label col-md-4" style="padding-left: 0px;">货品</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control"   id="productId">
						  </div>
						  </div>
						  <div class="col-md-3 feeBillDiv">
						   <label class="control-label col-md-4" style="padding-left: 0px;">货批</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control"   id="cargoId">
						  </div>
						  </div>
						  <div class="col-md-3 feeBillDiv">
						   <label class="control-label col-md-4" style="padding-left: 0px;">发票金额</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" onkeyup="config.clearNoNum(this)"  id="totalFee">
						  </div>
						  </div>
						  	</div>
					<div class="col-md-12" style="margin-top:15px;">
					<div class="col-md-6" style="padding-left: 0px;padding-right:0px;">
					<div class="col-md-2" style="padding-left: 0px;">
																	 <select class="form-control time"   id="timeType">
																			<option value="2">开票时间</option>
																			<option value="1">账单时间</option>
																			</select>  
																		</div>
							<div class="col-md-10" style="padding-left: 0px;">
								<div class="input-group date-picker input-daterange"
									data-date-format="yyyy-mm-dd">
									<input type="text" class="form-control"
										 id="startTime">
									<span class="input-group-addon">到</span> <input
										type="text" class="form-control"  id="endTime">
								</div>
							</div>
						</div>
						<div class="col-md-3 feeBillDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">账单状态</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <select class="form-control" id="billStatus">
													    <option value="-1" selected>全部</option>
														<option value="0">未提交</option>
														<option value="1">审核中</option>
														<option value="2">已审核</option>
														<option value="3">未到账</option>
														<option value="4">未开票</option>
														<option value="5">已发票</option>
														<option value="6">已到账</option>
														<option value="7">已完成</option>
														<option value="8">未完成</option>
														</select>
						  </div>
						  </div>
						<div class="col-md-2 col-md-offset-1 btn-group"
								style="float: left; padding-left: 0px;">
								<button type="button"
									class="btn btn-success" id="searchFee">
									<span class="glyphicon glyphicon-search"></span>&nbsp;
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
	            <!-- <button class="btn btn-default btn-circle mar-r-10 addFee" type="button">
				<span class="fa fa-plus"></span>&nbsp;添加&nbsp;</button> -->
	            <!-- <button class="btn btn-default btn-circle mar-r-10 roleManagerQuery" type="button" onclick="util.querySlideToggle()">
			    <span class="fa fa-search"></span><span class="text">搜索</span>
		      </button> -->
		       <shiro:hasPermission name="AFEEBILLEXCEL">
		        <a  class="btn "  onclick="feeBill.exportXML(this,52)"><span class="fa fa-file-excel-o">&nbsp;导出账单开票列表</span>&nbsp;
								</a>
								<a class="btn "  onclick="feeBill.exportXML(this,54)"><span class="fa fa-file-excel-o">&nbsp;导出货批账单开票列表</span>&nbsp;
								</a>
					 </shiro:hasPermission>
					<div class="checkbox feeBillDiv" style="float:right;">
                                 <label class="checkbox-inline"> <input
											type="checkbox" data='1' id="isShowAll" value="option2">显示统计信息
										</label>
                              </div>			
	            </div>
	            <!-- 搜索按钮 -->
	            <!-- 列表 -->
	            <div class="col-md-12">
	            <div data-role="feeBillGrid"></div>
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