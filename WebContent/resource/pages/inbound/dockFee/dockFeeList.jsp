<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="rows">
<div class="row page-header-fixed page-sidebar-closed-hide-logo page-sidebar-closed page-quick-sidebar-over-content page-container-bg-solid page-compact">
	<div class="col-md-12">
		<div class="portlet">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-yelp"></i>码头规费
				 <shiro:hasPermission name="APIERFEEEXCEL">
				<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right">
				<a style="padding-left: 20px;" class="hidden-print" onclick="DockFee.exportExcel();"> <i class="fa fa-file-excel-o">&nbsp;导出</i></a>
				</span>
				<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right">
				<a style="padding-left: 20px;" class="hidden-print" onclick="DockFee.exportListExcel();"> <i class="fa fa-file-excel-o">&nbsp;列表导出</i></a>
				</span>
				</shiro:hasPermission>
			</div>
		</div>
		<div class="portlet-body">
		<div class="row">
		<div class="col-md-12 tabbable-custom">
			    <ul class="nav nav-tabs">
			     <shiro:hasPermission name="MBOUNDTAB">
			    <li>
				<a data-toggle="tab" href="javascript:void(0);" onclick="DockFee.changeTab(this,1)" id="tab1" >出入港列表</a>
				</li>
				</shiro:hasPermission>
				<li class='active'>
				<a data-toggle="tab" href="javascript:void(0);" class="base" onclick="DockFee.changeTab(this,2)" id="tab2">结算单列表
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
				<div id="roleManagerQueryDivId" hidden="true">
						<div class="row">
						  <div class="col-md-12  ">
						  <div class="col-md-3 dockFeeDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">结算单号:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="code">
						  </div>
						  </div>
						  <div class="col-md-3" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">船名:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="shipName">
						  </div>
						  </div>
						  <div class="col-md-3" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">发生单位:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='64' id="clientName">
						  </div>
						  </div>
						  <div class="col-md-3" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">到港类型:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <select class="form-control" id="arrivalType">
						  <option value="0">全部</option>
						  <option value="1">入港</option>
						  <option value="2">出港</option>
						  </select>
						  </div>
						  </div>
					</div>
					<div class="col-md-12 " style="margin-top:25px;">
					<div class="col-md-3 dockFeeDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">发票类型:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <select class="form-control" id="billType">
						  <option value="0">全部</option>
						  <option value="1">手撕发票</option>
						  <option value="2">增值税发票</option>
						  </select>
						  </div>
						  </div>
					<div class="col-md-3 dockFeeDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">状态:</label>
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
					<div class="col-md-3" style="padding-left: 0px;padding-right:0px;">
							<label class="control-label col-md-4" style="padding-left: 0px;">到港时间:</label>
							<div class="col-md-8" style="padding-left: 0px;">
								<div class=" date-picker input-daterange"
									data-date-format="yyyy-mm-dd">
									<input type="text" class="form-control"
										 id="startTime">
								</div>
							</div>
						</div>
						<div class="col-md-2 col-md-offset-1 btn-group" style="float: left; padding-left: 0px;">
								<button type="button" class="btn btn-success" id="searchFee">
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
	            <button class="btn btn-default btn-circle mar-r-10 roleManagerQuery" onclick="util.querySlideToggle()" type="button">
			    <span class="fa fa-search"></span><span class="text">搜索</span>
		      </button>
	            </div>
	            <!-- 搜索按钮 -->
	            <!-- 列表 -->
	            <div class="col-md-12">
	            <div data-role="dockFeeGrid"></div>
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