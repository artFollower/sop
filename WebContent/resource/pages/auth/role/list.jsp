<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<!-- BEGIN MAIN CONTENT -->
<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title hidden">
				<div class="caption">
					<i class="fa fa-shopping-cart"></i>角色列表
				</div>
			</div>
			<div>
				<form name="userListForm" id="${formId}" target="_self" class="form-horizontal searchCondition">
					<div id="userManagerQueryDivId" class="panel" hidden="true">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<div class="form-group">
										<label class="control-label" style="width: 100px; float: left;">用户名称:&nbsp;</label>
										<div style="margin-left: 15px; float: left;">
											<input name="name" class="form-control" type="text" style="width: 180px;" id="nameID" dataType="Chinese" require="false" />
										</div>
										<label class="control-label" style="width: 100px; float: left;">用户帐号:&nbsp;</label>
										<div style="margin-left: 15px; float: left;">
											<input name="userAccount" class="form-control" type="text" style="width: 180px;" id="descID" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label" style="width: 100px; float: left;">用户描述:&nbsp;</label>
										<div style="margin-left: 15px; float: left;">
											<input name="description" class="form-control" type="text" style="width: 180px;" />
										</div>
										<label class="control-label" style="width: 100px; float: left;">状态:&nbsp;</label> <select name="disabled" class="form-control" style="width: 180px; margin-left: 15px; float: left;">
											<option value="">全部</option>
											<option value="0">激活</option>
											<option value="1">禁用</option>
										</select>
									</div>
								</td>
								<td style="vertical-align: bottom;"><button id="userManagerSearch" type="button" style="position: relative; margin-left: 35px; top: -15px" class="btn btn-success glyphicon glyphicon-search"></button></td>
							</tr>
						</table>
					</div>
				</form>
			</div>
			<div class="btn-group buttons">

				<button class="btn btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-plus"></span><span class="text">添加</span>
				</button>
				<!-- <button class="btn btn-default btn-circle mar-r-10 btn-modify" type="button">
					<span class="fa fa-edit"></span><span class="pad-l-5">修改</span>
				</button>
				<button class="btn btn-default btn-circle mar-r-10 btn-remove" type="button">
					<span class="fa fa-remove"></span><span class="pad-l-5">撤销</span>
				</button>
				<button class="btn btn-default btn-circle btn-grant-permission" type="button">
					<i class="fa fa-lock"></i><span class="pad-l-5" style="line-height: 16px;">授权</span>
				</button> -->
			</div>
			<div data-role="roleGrid"></div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->