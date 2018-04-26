<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<!-- BEGIN MAIN CONTENT -->
<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title hidden">
				<div class="caption">
					<i class="fa fa-shopping-cart"></i>组织架构列表
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
				<div class="col-md-4">
					<div class="portlet blue-hoki box">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-cogs"></i>组织架构
							</div>
							<div class="tools">
								<a href="javascript:;" class="reload"></a>
							</div>
						</div>
						<div class="portlet-body">
							<div class="btn-group btn-group-xs btn-group-solid hidden">
							
								<button class="btn btn-default btn-circle mar-r-10 company-add" type="button">
									<span class="fa fa-plus"></span><span class="text">添加公司</span>
								</button>
								<button class="btn btn-default btn-circle mar-r-10 department-add" type="button">
									<span class="fa fa-plus"></span><span class="pad-l-5">添加部门</span>
								</button>
								<!-- <button class="btn btn-default btn-circle mar-r-10 btn-modify" type="button">
									<span class="fa fa-edit"></span><span class="pad-l-5">修改</span>
								</button> -->
								<button class="btn btn-default btn-circle btn-remove" type="button">
									<i class="fa  fa-remove"></i><span class="pad-l-5" style="line-height: 16px;">撤销</span>
								</button>
							</div>
							<div id="organization" style="min-height: 480px;max-height: 500px;"></div>
						</div>
					</div>
				</div>
				<div class="col-md-8">
					<div class="portlet green-meadow box">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-cogs"></i>详情
							</div>
						</div>
						<div class="portlet-body">
							<div class="btn-group btn-group-xs btn-group-solid">
								<button class="btn btn-default btn-circle mar-r-10 relation-user" type="button">
									<span class="fa fa-plus"></span><span class="text">添加员工</span>
								</button>
								<button class="btn btn-default btn-circle disrelation-user" type="button">
									<i class="fa  fa-remove"></i><span class="pad-l-5" style="line-height: 16px;">解除关系</span>
								</button>
							</div>
							<div id="detail" style="min-height: 450px;" data-role="organizationGrid"></div>
						</div>
					</div>
				</div>
			</div>

		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->
