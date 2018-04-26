<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
<style type="text/css">
.todo-sidebar {
	float: left;
	margin-right: 20px;
	width: 240px;
}
</style>
</head>
<div class="rows">
	<div
		class="row page-header-fixed page-sidebar-closed-hide-logo page-sidebar-closed page-quick-sidebar-over-content page-container-bg-solid page-compact">
		<div class="col-md-12">
			<div class="portlet">
				<div class="portlet-title">
					<div class="caption subtitle">
						<i class="fa fa-folder-o"></i><span
							class="mar-l-5 todo-detail-title">通知单详情</span>
							<shiro:hasPermission name="ANOTICEPIPINGEXEL">
			   	              <a style="padding-left: 20px;" class="hidden-print" onclick="notify.exportExcelList();">
		                      <i class="fa fa-file-excel-o">&nbsp;导出</i></a>                      
				              </shiro:hasPermission>
					</div>
				</div>
				<div class="portlet-body">
					<div class="notice hidden"></div>
					<div class="row notice-list">
						<div class="col-md-12 todo-detail-body">
							<div style="margin-bottom: 5px;">
								<form id="notifyListForm" target="_self"
									class="form-horizontal searchCondition">
									<div id="roleManagerQueryDivId" hidden="true">
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label col-md-4">编号:</label>
													<div class="col-md-8">
														<input name="codeF" id="codeF" class="form-control"
															maxlength='64' type="text" />
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label col-md-4">关键字:</label>
													<div class="col-md-8">
														<input name="keyWord" id="keyWord" class="form-control"
															maxlength='64' type="text" />
														<input name="type" id="type"  class="form-control" type="hidden" />
													</div>
												</div>
											</div>
											<div class="col-md-4 hidden">
												<div class="form-group">
													<label class="control-label col-md-4"
														style="padding-left: 0px;">通知单:</label>
													<div class="col-md-8">
														<select class="form-control" name="types" disabled>
															<option value="0,1,4,5,6,7,8,9,10,11">全部</option>
															<option value="0">配管</option>
															<option value="1">苯加热</option>
															<option value="2">打循环(码头)</option>
															<option value="3">打循环(库区)</option>
															<option value="4">管线清洗(码头)</option>
															<option value="5">管线清洗(库区)</option>
															<option value="6">扫线作业(码头)</option>
															<option value="7">扫线作业(库区)</option>
															<option value="8">清罐</option>
															<option value="9">储罐放水</option>
															<option value="10">储罐开人孔</option>
															<option value="11">转输</option>
															<option value="12">倒罐</option>
															<option value="13">码头接卸</option>
															<option value="14">动力班接卸</option>
															<option value="15">码头船发</option>
															<option value="16">操作班船发</option>
															<option value="17">车发换罐</option>
														</select>
														
													</div>
												</div>
											</div>
											
											<!-- </div> -->
											<div class="col-md-4">
												<div class="form-group">
													<label class="control-label col-md-4"
														style="padding-left: 0px;">时间区间:</label>
													<div class="col-md-8">
														<div class="input-group date-picker input-daterange"
															data-date-format="yyyy-mm-dd">
															<input type="text" class="form-control" name="startTime"
																id="startTime" id="startTime"> <span
																class="input-group-addon">到</span> <input type="text"
																class="form-control" name="endTime" name="endTime"
																id="endTime">
														</div>
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="row">
													<div class="col-md-8 btn-group"
														style="float: right; padding-left: 0px; padding-right: 0px;">
														<button type="button" class="btn btn-success searchNotify">
															<span class="fa fa-search"></span>&nbsp;
														</button>
														<button type="button" style="margin-left: 8px;"
															class="btn btn-primary reset">
															<span class="fa fa-undo"></span>&nbsp;
														</button>
													 </div>
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
							<div class="btn-group notifyButtons col-md-12">
									<button class="btn btn-default btn-circle mar-r-10 notifyBack"
										type="button">
										<span class="fa fa-reply"></span><span class="text">返回</span>
									</button>
								<shiro:hasPermission name="ANOTICEDISPATCH">
									<button class="btn btn-default btn-circle mar-r-10 notifyAdd"
										type="button">
										<span class="fa fa-plus"></span><span class="text">添加</span>
									</button>
								</shiro:hasPermission>
								<button class="btn btn-default btn-circle mar-r-10 notifyModify"
									type="button">
									<span class="fa fa-edit"></span><span class="pad-l-5">修改</span>
								</button>
								<shiro:hasPermission name="ANOTICEDISPATCH">
									<button
										class="btn btn-default btn-circle mar-r-10 notifyRemove"
										type="button">
										<span class="fa fa-remove"></span><span class="pad-l-5">删除</span>
									</button>
								</shiro:hasPermission>
								<button class="btn btn-default btn-circle  notifySearch"
									type="button">
									<i class="fa  fa-search"></i><span class="pad-l-5">搜索</span>
								</button>
							</div>
							<div class="col-md-12">
								<div class="tab-content">
									<div data-role="notifyGrid"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
