<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<body>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet">
				<div class="portlet-title">
					<div class="caption">
						<i class="fa fa-paperclip"></i>出港信息列表
					</div>
				</div>
				<div>
					<form action="#" id="intentListForm" target="_self"
						class="form-horizontal form-bordered">
						<div id="roleManagerQueryDivId" hidden="true">
							<div class="row">
								<div class="form-body">
									<div class="col-md-12">
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label col-md-4">船舶名</label>
												<div class="col-md-8">
													<input class="form-control" type="text" name="shipName" id="shipName">
												</div>
											</div>
										</div>
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label col-md-4">货品</label>
												<div class="col-md-8">
													<input class="form-control" type="text" data-provide="typeahead" name="productId" id="productId">
												</div>
											</div>
										</div>
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label col-md-4">提货单位</label>
												<div class="col-md-8">
													<input class="form-control" type="text" data-provide="typeahead" name="ladingClientId" id="ladingClientId">
												</div>
											</div>
										</div>
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label col-md-4">提单号</label>
												<div class="col-md-8">
													<input class="form-control" type="text" id="ladingEvidence" name="ladingEvidence" style="text-transform: uppercase;font-size: 14px;">
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-12">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-2">到港时间</label>
												<div class="col-md-10">
													<div class="input-group date-picker input-daterange"
														data-date-format="yyyy-mm-dd">
														<input type="text" class="form-control" name="startTime"  id="startTime"> <span
															class="input-group-addon">到</span> <input type="text"
															class="form-control" name="endTime" id="endTime">
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label col-md-4">类型</label>
												<div class="col-md-8">
													<select class="form-control" id="type">
														<option value="0" selected>未作废</option>
														<option value="1">已作废</option>
													</select>
												</div>
											</div>
										</div>
										<div class="col-md-2 col-md-offset-1">
											<div class="form-group btn-group" style="float: left;">
												
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
					</form>
				</div>
				<br>
				<div class="btn-group buttons col-md-12">
					<shiro:hasPermission name="AOUTBOUNDADD">
						<button class="btn btn-default btn-circle mar-r-10 btn-add"
							type="button">
							<span class="fa fa-plus"></span><span class="text">添加</span>
						</button>
					</shiro:hasPermission>
					<button class="btn btn-default btn-circle mar-r-10 " onclick="util.querySlideToggle()"
						type="button">
						<span class="fa fa-search"></span><span class="text">搜索</span>
					</button>
					<div class="checkbox itemCtr" style="float:right;">
					<label class="checkbox-inline" style="padding-right:20px;">
                                  <input type="checkbox"    id="isWarning"  value="option1"><i id="isWarningIcon" class="fa fa-exclamation-triangle" data-placement="bottom"  data-content="出港计划提单号与发货开票提单号不一致" style="color:red;margin-top: 4px;"></i>
                                  </label>
                                 <label class="checkbox-inline">
                                  <input type="checkbox" id="isShowAll" value="option2">显示全部记录
										</label>
                              </div>
				</div>
				<div class="col-md-12">
				<div data-role="shipArrivalGrid"></div>
				</div>
			</div>
		</div>
	</div>

</body>


