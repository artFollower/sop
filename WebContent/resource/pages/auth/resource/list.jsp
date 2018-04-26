<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!-- BEGIN MAIN CONTENT -->
<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title hidden">
				<div class="caption">
					<i class="fa fa-shopping-cart"></i>资源列表
				</div>
			</div>
			<div>
				<form id="resourceListForm" target="_self" class="form-horizontal searchCondition">
						<table border="0" cellspacing="0" cellpadding="0">
						  <tr>
						    <td>
					          	<div class="form-group">
						          <label class="control-label" style="margin-left:25px;float:left;">名称:&nbsp;</label>
						            <div style="float:left;">
							            <input name="name" class="form-control" type="text" style="width:180px;" />
							        </div>
						            <label class="control-label" style="margin-left:15px;float:left;">标识:&nbsp;</label>
						            <div style="float:left;">
							            <input name="indentifier" class="form-control" type="text" style="width:180px;" id="descID"  />
							        </div>
							        <label class="control-label" style="margin-left:15px;float:left;">类型:&nbsp;</label>
						           <div style="float:left;">
							           <select  name="category" class="form-control"  style = "width:120px;margin-left:15px;float:left;">
							          	 <option value="">全部</option>
							             <option value="MENU">菜单</option>
							             <option value="URL">URL</option>
							             <option value="ACCESS">操作</option>
							           </select>
						           </div>
						           <label class="control-label" style="margin-left:15px;float:left;">状态:&nbsp;</label>
						           <div style="float:left;">
							           <select  name="status" class="form-control"  style = "width:120px;margin-left:15px;float:left;">
							          	 <option value="">全部</option>
							             <option value="0">激活</option>
							             <option value="1">禁用</option>
							           </select>
						           </div>
					            </div>
					            </td>
						       <td style="vertical-align: bottom;">
						       		<button class="btn btn-default btn-circle btn-search" type="button" id="managerSearch"  style="position:relative; margin-left:35px; top: -15px">
										<i class="fa  fa-search"></i><span class="pad-l-5" style="line-height: 16px;">搜索</span>
									</button>
									<button class="btn btn-default btn-circle btn-role-tree" type="button" style="position:relative; margin-left:35px; top: -15px">
										<i class="fa  fa-edit"></i><span class="pad-l-5" style="line-height: 16px;">权限树</span>
									</button>
						       </td>
						  </tr>
						</table>	
				</form>
			</div>
			<div class="btn-group buttons hidden">
				
				<button class="btn btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-plus"></span><span class="text">添加</span>
				</button>
				<button class="btn btn-default btn-circle mar-r-10 btn-modify" type="button">
					<span class="fa fa-edit"></span><span class="pad-l-5">修改</span>
				</button>
				<button class="btn btn-default btn-circle mar-r-10 btn-remove" type="button">
					<span class="fa fa-remove"></span><span class="pad-l-5">撤销</span>
				</button>
				<button class="btn btn-default btn-circle btn-search" type="button">
					<i class="fa  fa-search"></i><span class="pad-l-5" style="line-height: 16px;">搜索</span>
				</button>
			</div>
			<div data-role="resourceGrid">
			</div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->