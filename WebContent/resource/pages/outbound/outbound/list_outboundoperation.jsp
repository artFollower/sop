<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="rows">
	<div
		class="row page-header-fixed page-sidebar-closed-hide-logo page-sidebar-closed page-quick-sidebar-over-content page-container-bg-solid page-compact">
		<div class="col-md-12">
			<div class="portlet">
				<div class="portlet-title">
					<div class="caption">
						<span id="tabTitle" class="fa fa-yelp">船发列表</span>
						 <shiro:hasPermission name="MSHIPDELIVERYEXCEL">
						<span class="pull-right">
		<a style="padding-left: 20px;" class="hidden-print" onclick="Outbound.exportOutBound()">
		 <i class="fa fa-file-excel-o">&nbsp;导出</i></a>
		 </span>
		 </shiro:hasPermission>
					</div>
				</div>
				<div class="portlet-body">
					<div class="row">
						<div class="col-md-12 tabbable-custom">
							<ul class="nav nav-tabs">
								<li class="active"><a href="javascript:void(0);"
									onclick="Outbound.changetab(this,'50,51,52,53',1)"
									data-toggle="tab" id="tab1"> 作业列表</a></li>
									 <shiro:hasPermission name="MSHIPDELIVERYPLAN">
								<li><a href="javascript:void(0);"
									onclick="Outbound.changetab(this,'50,51,52,53',2)" data-toggle="tab"
									id="tab2"> 作业计划</a></li>
									</shiro:hasPermission>
									 <shiro:hasPermission name="MSHIPDELIVERYBERTHPLAN">
								<li class="notTransport"><a href="javascript:void(0);"
									onclick="Outbound.changetab(this,'51,52,53',3)" data-toggle="tab"
									id="tab3"> 靠泊方案 </a></li>
									</shiro:hasPermission>
									 <shiro:hasPermission name="MSHIPDELIVERYBERTHREADY">
								<li><a href="javascript:void(0);"
									onclick="Outbound.changetab(this,'52,53',4)" data-toggle="tab"
									id="tab4"> 发货准备 </a></li>
									</shiro:hasPermission>
									 <shiro:hasPermission name="MSHIPDELIVERYAMOUNTAFFIRM">
								<li><a href="javascript:void(0);"
									onclick="Outbound.changetab(this,'53',5)" data-toggle="tab"
									id="tab5"> 数量确认 </a></li>
									</shiro:hasPermission>
							</ul>

							<div class="tab-content"
								style="padding-top: 0px; padding-bottom: 0px;">
								<div class="portlet light">
									<div class="row">
										<div class="col-md-12 col-sm-12">
											<div class="portlet-body">
												<form action="#" class="form-horizontal form-bordered">
													<div id="roleManagerQueryDivId" hidden="true"
														class="serialList">
														<div class="col-md-12">
															<div class="col-md-3 notTransport">
																<div class="form-group">
																	<label class="control-label col-md-4">船名</label>
																	<div class="col-md-8">
																		<input class="form-control" name="shipName" type="text" id="shipName">
																	</div>
																</div>
															</div>
															<div class="col-md-3">
																<div class="form-group">
																	<label class="control-label col-md-4">货品</label>
																	<div class="col-md-8">
																		<input id="productId" data-provide="typeahead" class="form-control" name="productId"
																			maxlength='64' type="text" />
																	</div>
																</div>
															</div>
															<div class="col-md-3 mainCtr">
																<div class="form-group">
																	<label class="control-label col-md-4"
																		style="padding-left: 0px;">作业状态</label>
																	<div class="col-md-8">
																		<select class="form-control" id="statuskey" name="status">
																			<option value="50,51,52,53" selected>正在进行</option>
																			<option value="50,51,52,53,54">全部出库</option>
																			<option value="50">作业计划</option>
																			<option class="notTransport" value="51">靠泊方案</option>
																			<option value="52">发货准备</option>
																			<option value="53">数量确认</option>
																			<option value="54">确认完成</option>
																		</select>
																	</div>
																</div>
															</div>
															<div class="col-md-3 notTransport">
																<div class="form-group">
																	<label class="control-label col-md-4"
																		style="padding-left: 0px;">到港类型</label>
																	<div class="col-md-8">
																		<select class="form-control" name="arrivalType" id="arrivalType">
																			<option value="0" selected>所有</option>
																			<option value="2">非通过船舶</option>
																			<option value="5">通过船舶</option>
																		</select>
																	</div>
																</div>
															</div>
														</div>
														<div class="col-md-12">
															<div class="col-md-6">
																<div class="form-group">
																	<label class="control-label col-md-2"
																		style="padding-left: 0px;">时间区间</label>
																	<div class="col-md-10">
																		<div class="input-group date-picker input-daterange"
																			data-date-format="yyyy-mm-dd">
																			<input type="text" class="form-control" name="startTime" id="startTime" >
																			<span class="input-group-addon">到</span> <input
																				type="text" class="form-control" name="endTime" id="endTime">
																		</div>
																	</div>
																</div>
															</div>
															<div class="col-md-3">
																<div class="col-md-8 btn-group" style="float: right;">
																	<button type="button" class="btn btn-success search">
																		<span class="fa fa-search"></span>&nbsp;
																	</button>
																	<button type="button" style="margin-left: 8px;"
																		class="btn btn-primary reset">
																		<span class="fa fa-undo" title="重置"></span>&nbsp;
																	</button>
																</div>
															</div>
														</div>
													</div>
												</form>
											</div>
										</div>
										<div class="btn-group buttons col-md-12">
											<button class="btn btn-default btn-circle mar-r-10 "
												type="button" onclick="util.querySlideToggle()">
												<span class="fa fa-search"></span><span class="text">搜索</span>
											</button>
											<div class="checkbox itemCtr" style="float:right;">
		                                 <label class="checkbox-inline"> <input
													type="checkbox" data='50,51,52,53' key="1" id="isShowAll" value="option2">显示全部记录
												</label>
		                              </div>
										</div>
										<div class="col-md-12">
											<div class="tab-content">
												<div data-role="outboundGrid"></div>
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