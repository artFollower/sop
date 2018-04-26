<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="rows">
<div class="row page-header-fixed page-sidebar-closed-hide-logo page-sidebar-closed page-quick-sidebar-over-content page-container-bg-solid page-compact">
	<div class="col-md-12">
		<div class="portlet">
		<div class="portlet-title">
		<div class="caption">
		<i class="fa fa-rebel"></i>
		<span id="tabTitle" class="mar-l-5">入库作业</span>
		<!-- 导出入库信息 -->
		<span class="pull-right">
		<shiro:hasPermission name="AINBOUNDTABLEEXCEL">
		<a style="padding-left: 20px;" class="hidden-print" onclick="InboundOperation.exportInbound()">
		 <i class="fa fa-file-excel-o">&nbsp;导出</i></a>
		 </shiro:hasPermission>
		 </span>
		</div>
		</div>
		<div class="portlet-body">
			<div class="row">
				<div class="col-md-12 tabbable-custom">
				<label class="hidden" id="isTransport"></label>
						<ul class="nav nav-tabs">
						<shiro:hasPermission name="MINBOUNDWORKALL">
							<li class="active"><a href="javascript:void(0);"
								onclick="InboundOperation.changetab(this,1)" data-toggle="tab"
								id="inbound_tab1"> 作业列表</a></li>
								</shiro:hasPermission>
							<shiro:hasPermission name="MINBOUNDWORKPLAN">
							<li><a href="javascript:void(0);"
								onclick="InboundOperation.changetab(this,2)" data-toggle="tab"
								id="inbound_tab2"> 作业计划</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="MINBOUNDBERTHING">
							<li class="notTransport"><a href="javascript:void(0);"
								onclick="InboundOperation.changetab(this,3)" data-toggle="tab"
								id="inbound_tab3"> 靠泊方案 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="MUNLOADINGPROGRAM">
							<li><a href="javascript:void(0);"
								onclick="InboundOperation.changetab(this,4)" data-toggle="tab"
								id="inbound_tab4"> 接卸方案 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="MUNLOADINGWORK">
							<li><a href="javascript:void(0);"
								onclick="InboundOperation.changetab(this,5)" data-toggle="tab"
								id="inbound_tab5"> 接卸准备 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="MBACKFLOWPROGRAM">
							<li class="notTransport"><a href="javascript:void(0);"
								onclick="InboundOperation.changetab(this,6)" data-toggle="tab"
								id="inbound_tab6"> 打循环方案</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="MAMOUNTCONFIRM">
								<li><a href="javascript:void(0);"
								onclick="InboundOperation.changetab(this,7)" data-toggle="tab"
								id="inbound_tab7"> 数量审核 </a></li>
								</shiro:hasPermission>
							</ul>
									
						<div class="tab-content" style="padding-top: 0px; padding-bottom: 0px;">
							<div class="portlet light">
								<div class="portlet-body">
									<div class="row">
										<div class="col-md-12 col-sm-12">
							<form id="inboundopeartionListForm" target="_self"
								class="form-horizontal searchCondition">
								<div id="roleManagerQueryDivId" hidden="true">
								<div class="col-md-12">
										<div class="col-md-3 notTransport">
											<div class="form-group">
												<label class="control-label col-md-4">船名:</label>
												<div class="col-md-8">
													<input name="shipName"  id="shipId"
														class="form-control" maxlength='64' type="text" />
												</div>
											</div>
										</div>
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label col-md-4">货品:</label>
												<div class="col-md-8">
													<input name="productId" data-provide="typeahead" key="productId" id="productId"
														class="form-control" maxlength='64' type="text" />
												</div>
											</div>
										</div>
										<div class="col-md-3 mainCtr">
											<div class="form-group">
												<label class="control-label col-md-4" style="padding-left: 0px;">作业状态:</label>
												<div class="col-md-8">
													<select class="form-control" name="statuskey">
													    <option value="2,3,4,5,6,8" selected>正在进行</option>
														<option value="2,3,4,5,6,8,9">全部入库</option>
														<option value="2">作业计划</option>
														<option value="3">靠泊评估</option>
														<option class="notTransport" value="3,4">靠泊方案</option>
														<option id="op5" value="5">接卸方案</option>
														<option id="op6" value="6">接卸准备</option>
														<option value="8">数量审核</option>
														<option value="9">入库完成</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-3 notTransport">
											<div class="form-group">
												<label class="control-label col-md-4" style="padding-left: 0px;">到港类型:</label>
												<div class="col-md-8">
													<select class="form-control" name="arrivalType">
													    <option value="0" selected>所有</option>
														<option value="1">非通过船舶</option>
														<option value="3">通过船舶</option>
													</select>
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-12">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-2" style="padding-left: 0px;">时间区间:</label>
												<div class="col-md-10">
													<div class="input-group date-picker input-daterange"
														data-date-format="yyyy-mm-dd">
														<input type="text" class="form-control" name="startTime" id="startTime">
														 <span class="input-group-addon">到</span>
														<input type="text" class="form-control"  name="endTime" 
															id="endTime">
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-3">
										 <div class="form-group ">
										  <div class="col-md-8 btn-group" style="float:right;">
											<button type="button" class="btn btn-success inboundSearch">
												<span class="fa fa-search" title="查询"></span>&nbsp;
											</button>
											<button  type="button" style="margin-left:8px;"
												class="btn btn-primary reset">
												<span class="fa fa-undo" title="重置"></span>&nbsp;
											</button>
											</div>
										</div>
										</div>
									</div>
								</div>
							</form>
						</div>
						<div class="btn-group  buttons col-md-12">
							<shiro:hasPermission name="AINBOUNDSEARCH">
							<button class="btn btn-default btn-circle mar-r-10 planManagerQuery " onclick="util.querySlideToggle()" type="button" >
								<span class="fa fa-search"></span>&nbsp;搜索&nbsp;
							</button>
							</shiro:hasPermission>
							<a href="http://58.213.133.199/Default.aspx" class="mainCtr" target="_blank" style="float:right;text-decoration:none;"
								>&nbsp;海事申报网&nbsp;</a>
							<a
								href="http://221.224.9.194:10046/yjpt/" class="mainCtr" style="float:right;text-decoration:none;"
								target="_blank" >&nbsp;港务局申报网&nbsp;</a>
								<div class="checkbox itemCtr" style="float:right;">
                                 <label class="checkbox-inline"> <input
											type="checkbox" data='1' id="isShowAll" value="option2">显示全部记录
										</label>
                              </div>
						</div>
                        <div class="col-md-12">
						<div class="tab-content">
                              <div data-role="inboundoperationGrid"></div>
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
</div>