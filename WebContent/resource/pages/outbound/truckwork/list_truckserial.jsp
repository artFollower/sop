<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="rows">
	<div class="row page-header-fixed page-sidebar-closed-hide-logo page-sidebar-closed page-quick-sidebar-over-content page-container-bg-solid page-compact">
		<div class="col-md-12">
			<div class="portlet">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-truck"></i>车发列表
						<span class="pull-right">
		<a style="padding-left: 20px;" class="hidden-print" onclick="v_outbound.exportExcel()">
		 <i class="fa fa-file-excel-o">&nbsp;导出</i></a>
		 </span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="row">
						<div class="col-md-12  ">
							<div class="portlet light" style="padding-left: 0px;">
								<div class="portlet-body">
									<div class="row">
										<div class="col-md-12 col-sm-12">
											<!-- 搜索条件 -->
											<form action="#" class="form-horizontal form-bordered">
												<div id="roleManagerQueryDivId" hidden="true">
													<div class="row">
														<div class="form-body">
															<div class="col-md-12">
																<div class="col-md-3">
																	<div class="form-group">
																		<label class="control-label col-md-4">提单号</label>
																		<div class="col-md-8">
																			<input type="text" class="form-control"
																				name="ladingEvidence" id="ladingEvidence">
																		</div>
																	</div>
																</div>
																<div class="col-md-3">
																	<div class="form-group">
																		<label class="control-label col-md-4">通知单号</label>
																		<div class="col-md-8">
																			<input type="text" class="form-control"
																				name="serialNum" id="serialNum">
																		</div>
																	</div>
																</div>
																<div class="col-md-3">
																	<div class="form-group">
																		<label class="control-label col-md-4">客户名称</label>
																		<div class="col-md-8">
																			<input class="form-control code" type="text" data-provide="typeahead" name="clientId"
																				id="clientName">
																		</div>
																	</div>
																</div>
																<div class="col-md-3">
																	<div class="form-group">
																		<label class="control-label col-md-4">货品名称</label>
																		<div class="col-md-8">
																			<input class="form-control" type="text" data-provide="typeahead" name="productId"
																				id="productName">
																		</div>
																	</div>
																</div>
															</div>
															<div class="col-md-12">
																<div class="col-md-3">
																	<div class="form-group">
																		<label class="control-label col-md-4">车牌号</label>
																		<div class="col-md-8">
																			<input type="text" class="form-control"data-provide="typeahead" 
																				name="trainId" id="trainId">
																		</div>
																	</div>
																</div>
																<div class="col-md-3">
																	<div class="form-group">
																		<label class="control-label col-md-4">流程状态</label>
																		<div class="col-md-8">
																			<select id="status" name="status" class="form-control">
																				<option value="">全部车发</option>
																				<option value="40">发货开票</option>
																				<option value="41">车发作业</option>
																				<option value="42">数量确认</option>
																				<option value="43">打单出库</option>
																			</select>
																		</div>
																	</div>
																</div>
																<div class="col-md-3">
																	<div class="form-group">
																		<label class="control-label col-md-4">日期</label>
																		<div class="col-md-8">
																			<div class="input-group date-picker input-daterange"
																				data-date-format="yyyy-mm-dd">
																				<input type="text" class="form-control"
																					name="startTime"  id="startTime">
																				<span class="input-group-addon">到</span> <input
																					type="text" class="form-control" name="endTime"
																					id="endTime">
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-md-3">
																	<div class="form-group">
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
														</div>
													</div>
												</div>
											</form>
										</div>
										<!-- 搜索条件 -->
										<!-- 搜索按钮 -->
										<div class="btn-group buttons col-md-12 searchDiv">
											<button
												class="btn btn-default btn-circle mar-r-10 roleManagerQuery"  type="button">
												<span class="fa fa-search"></span><span class="text">搜索</span>
											</button>
										</div>
										<!-- 搜索按钮 -->
										<!-- 列表 -->
										<div class="col-md-12">
											<div data-role="v_outboundGrid"></div>
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