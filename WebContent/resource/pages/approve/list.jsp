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
					<i class="fa fa  fa-files-o"></i>审批中心
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
				<form id="approveListForm" target="_self" class="form-horizontal searchCondition">
				<div  id="roleManagerQueryDivId" >
				<table border="0" cellspacing="0" cellpadding="0">
				  <tr>
				      <td>
				          <div class="form-group">
				
				              <label class="control-label" style="width:70px;float:left;">&nbsp;审批模块:</label>
				              <div style="margin-left:15px;float:left;">
				              <select class="form-control select2me part" id="part"  name="part"  >
												<option value="0">请选择审批模块...</option>
												<option value="1">入港计划</option>
												<option value="2">入库确认</option>
												<option value="3">提单管理</option>
								</select>
				              </div>
				              <label class="control-label" style="width:70px;float:left;">&nbsp;审批状态:</label>
				              <div style="margin-left:15px;float:left;">
				              <select class="form-control select2me status" id="status"  name="status"  >
												<option value="0">请选择审批状态...</option>
												<option value="1">未审批</option>
												<option value="2">已通过</option>
												<option value="3">不通过</option>
												<option value="4">已失效</option>
								</select>
				              </div>
				          </div>
				      </td>
				      <td style="vertical-align: bottom;" ><button onclick="Approve.search()" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-success"><span class="glyphicon glyphicon-search"></span>&nbsp;</button></td>
				  </tr>
				</table>	
				</div>
				</form>
			</div>
				
			<div data-role="approveGrid">
			</div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->

