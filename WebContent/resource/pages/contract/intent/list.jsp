<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!-- BEGIN PAGE HEADER-->
<!-- END PAGE HEADER-->
<!-- BEGIN MAIN CONTENT -->
<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa  fa-files-o"></i>合同意向列表
				</div>
				<div class="actions" style="display: none">
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
			<div>
				<form id="intentListForm" target="_self" class="form-horizontal searchCondition">
				<div  id="roleManagerQueryDivId" >
				<table border="0" cellspacing="0" cellpadding="0">
				  <tr>
				      <td>
				          <div class="form-group">
				              <label class="control-label" style="width:100px;float:left;">&nbsp;意向号:</label>
				              <div style="margin-left:15px;float:left;">
				                  <input name="code" maxlength="32" class="form-control" type="text" style="width:180px;"/>
				              </div>
				
				              <label class="control-label" style="width:100px;float:left;">&nbsp;标题:</label>
				              <div style="margin-left:15px;float:left;">
				                  <input name="title" maxlength="32" class="form-control" type="text" style="width:180px;"/>
				              </div>
				              <label class="control-label" style="width:70px;float:left;">&nbsp;意向状态:</label>
				              <div style="margin-left:15px;float:left;">
				              <select class="form-control select2me status" id="status"  name="status"  >
												<option value="">请选择合同意向状态...</option>
												<option value="0">未提交</option>
												<option value="1">已提交</option>
												<option value="2">已通过</option>
												<option value="4">已删除</option>
								</select>
				              </div>
				          </div>
				      </td>
				      <td style="vertical-align: bottom;" ><button onclick="Intent.search()" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-success"><span class="glyphicon glyphicon-search"></span>&nbsp;</button></td>
				  </tr>
				</table>	
				</div>
				</form>
			</div>
				<div class="btn-group buttons">
				<shiro:hasPermission name="AINTENTADD">
				<button class="btn btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-plus"></span><span class="text">添加</span>
				</button>
				</shiro:hasPermission>
				<!--<shiro:hasPermission name="AINTENTUPDATE">
				<button class="btn btn-default btn-circle mar-r-10 btn-modify" type="button">
						<span class="fa fa-edit"></span><span class="pad-l-5">修改</span>
					</button>
					</shiro:hasPermission>
					<shiro:hasPermission name="AINTENTDELETE">
				<button class="btn btn-default btn-circle mar-r-10 btn-remove" type="button">
						<span class="fa fa-remove"></span><span class="pad-l-5">撤销</span>
					</button> 
					</shiro:hasPermission>-->
				<button class="btn btn-default btn-circle  btn-search" type="button">
					<i class="fa  fa-search"></i><span class="pad-l-5" style="line-height: 16px;">搜索</span>
				</button>
			</div>
			<div data-role="intentGrid">
			</div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->

