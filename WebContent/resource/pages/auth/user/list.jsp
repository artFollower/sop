<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
						<a class="btn default yellow-stripe" href="javascript:void(0)" data-toggle="dropdown"> <i class="fa fa-share"></i> <span class="hidden-480"> 工具 </span> <i class="fa fa-angle-down"></i>
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
				<div class="btn-group buttons col-md-6 col-sm-6">
					<shiro:hasPermission name="userAdd">
						<button class="btn btn-default btn-circle mar-r-10 btn-add" type="button">
							<span class="fa fa-plus"></span><span class="text">添加</span>
						</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="userDelete">
					<button class="btn btn-default btn-circle mar-r-10 btn-remove" type="button">
						<span class="fa fa-remove"></span><span class="pad-l-5">撤销</span>
					</button>
					</shiro:hasPermission>
				</div>

				<div class="search-form-default col-md-6 col-sm-6" id="queryUserDivId">
					<form name="userListForm" target="_self" class="form-horizontal searchCondition">
						<div class="input-group">
							<input type="text" placeholder="输入搜索信息" class="form-control search-key btn-circle-l" /> <span class="input-group-btn">
								<button type="button" class="btn green-haze btn-circle-r btn-default" id="managerSearch" style="border-top-width: 0px; border-bottom-width: 0px;">
									搜索 <i class="m-icon-swapright m-icon-white"></i>
								</button>
							</span>
						</div>
					</form>
				</div>
			</div>
			<div data-role="userGrid"></div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->
