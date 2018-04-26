<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<!-- BEGIN MAIN CONTENT -->
<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title hidden">
				<div class="caption">
					<i class="fa fa-shopping-cart"></i>用户列表
				</div>
				<div class="actions">
					<div class="btn-group">
						<a class="btn default yellow-stripe" href="javascript:void(0)"
							data-toggle="dropdown"> <i class="fa fa-share"></i> <span
							class="hidden-480"> 工具 </span> <i class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu pull-right">
							<li><a href="javascript:void(0)"> 导出EXCEL </a></li>
							<li><a href="javascript:void(0)"> 导出CSV </a></li>
							<li><a href="javascript:void(0)"> 导出XML </a></li>
							<li class="divider"></li>
							<li><a href="javascript:void(0)"> 打印 </a></li>
							<li><a href="#/intentAdd">添加</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="search-form-default col-md-12 col-sm-12"
					id="queryLogDivId">
					<form name="logSearchForm" target="_self"
						class="form-horizontal searchCondition">
						<table border="0" cellspacing="0" cellpadding="0" width="100%"
							style="margin-left: 15px; margin-left: 15px;">
							<tr>
								<td>
									<div class="form-group">
										<label class="control-label"
											style="float: left; text-align: right;">用户:</label>
										<div style="margin-left: 15px; float: left;">
											<input name="user" data-provide="typeahead" class="form-control" type="text"
												style="width: 180px;" />
										</div>
										<label class="control-label" style="float: left;">对象:</label>
										<div style="margin-left: 15px; float: left;">
											<select name="object" class="form-control">
												<option value="">请选择对象</option>
											</select>
										</div>
										<label class="control-label" style="float: left;">操作:</label>
										<div style="margin-left: 15px; float: left;">
											<select name="type" class="form-control">
												<option value="">请选择操作</option>
											</select>
										</div>
										<label class="control-label" style="float: left;">时间:</label>
										<div class="col-md-4">
											<div
												class="input-group input-large date-picker input-daterange"
												data-date-format="yyyy-mm-dd">
												<input type="text" class="form-control" name="startTime"
													id="startTime" id="startTime"> <span
													class="input-group-addon">到</span> <input type="text"
													class="form-control" name="endTime" name="endTime"
													id="endTime">
											</div>
										</div>
										
										<label class="control-label"
											style="float: left; text-align: right;">关键字:</label>
										<div style="margin-left: 15px; float: left;">
											<input name="content"  class="form-control" type="text"
												style="width: 180px;" />
										</div>
										
									</div>
								</td>
								<td style="vertical-align: bottom;">
									<button id="logSearch" type="button"
										style="position: relative; margin-left: 35px; top: -15px"
										class="btn btn-success glyphicon glyphicon-search"></button>
									<button id="logClear" type="button"
										style="position: relative; top: -15px"
										class="btn btn-success fa fa-undo"></button>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
			<div data-role="logGrid"></div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->