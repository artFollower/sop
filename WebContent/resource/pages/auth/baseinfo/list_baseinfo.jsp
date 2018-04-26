<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
	<head>
	<style type="text/css">
	.todo-sidebar {
    float: left;
    margin-right: 20px;
    width: 170px;
}
	</style>
	</head>
<div class="rows">
<div class="row page-header-fixed page-sidebar-closed-hide-logo page-sidebar-closed page-quick-sidebar-over-content page-container-bg-solid page-compact">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet"><span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right"><a style="padding-left: 20px;" class="hidden-print" onclick="javascript:BaseInfo.exportExcel();"> <i class="fa fa-file-excel-o">&nbsp;导出</i></a></span>
			<div class="row">
				<div class="col-md-12">
					<div class="todo-ui" style="margin-top: 0px;">
						<div class="todo-sidebar" style="padding-top: 0px; padding-bottom: 0px;">
							<div class="portlet light">
								<div class="portlet-title">
									<div data-target=".todo-project-list-content"
										data-toggle="collapse" class="caption">
										<span class="caption-subject font-green-sharp bold uppercase">标题
										</span> 
								</div> 
								</div>
								<div class="portlet-body todo-project-list-content"
									style="height: auto;">
									<div class="todo-project-list">
						<ul class="nav nav-pills nav-stacked  tabparent">
						
						<shiro:hasPermission name="ABASECLIENTGROUP">
						<li class="active"><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,0)" data-toggle="tab"
								id="tab0"><span class="fa fa-file-text-o"></span> 客户组资料</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASECLIENT">
							<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,1)" data-toggle="tab"
								id="tab1"><span class="fa fa-file-text-o"></span> 客户资料</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASEPRODUCT">
							<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,2)" data-toggle="tab"
								id="tab2"><span class="fa fa-file-text-o"></span> 货品资料</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASECARGOAGENT">
							<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,3)" data-toggle="tab"
								id="tab3"><span class="fa fa-file-text-o"></span> 货代资料 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASESHIP">
							<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,4)" data-toggle="tab"
								id="tab4"><span class="fa fa-file-text-o"></span> 船舶资料 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASESHIPAGENT">
							<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,5)" data-toggle="tab"
								id="tab5"><span class="fa fa-file-text-o"></span> 船代资料 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASETRUCK">
							<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,6)" data-toggle="tab"
								id="tab6"><span class="fa fa-file-text-o"></span> 车辆资料 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASEINSPECT">
							<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,7)" data-toggle="tab"
								id="tab7"><span class="fa fa-file-text-o"></span> 商检单位 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASECERTIFY">
								<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,8)" data-toggle="tab"
								id="tab8"><span class="fa fa-file-text-o"></span> 开证单位 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASEQUALIFICATION">
								<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,9)" data-toggle="tab"
								id="tab9"><span class="fa fa-file-text-o"></span> 客户资质 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASEBERTH">
								<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,10)" data-toggle="tab"
								id="tab10"><span class="fa fa-file-text-o"></span> 泊位资料 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASETUBE">
								<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,11)" data-toggle="tab"
								id="tab11"><span class="fa fa-file-text-o"></span> 管线资料 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASEPARK">
								<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,12)" data-toggle="tab"
								id="tab12"><span class="fa fa-file-text-o"></span> 车位资料 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASEPORT">
								<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,13)" data-toggle="tab"
								id="tab13"><span class="fa fa-file-text-o"></span> 港口资料 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASETANK">
								<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,14)" data-toggle="tab"
								id="tab14"><span class="fa fa-file-text-o"></span> 储罐资料 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASEPUMP">
								<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,15)" data-toggle="tab"
								id="tab15"><span class="fa fa-file-text-o"></span> 泵资料 </a></li>
								</shiro:hasPermission>
								 <shiro:hasPermission name="ABASEPUMPSHED">
								<li><a href="javascript:void(0);"
								onclick="BaseInfo.changetab(this,16)" data-toggle="tab"
								id="tab15"><span class="fa fa-file-text-o"></span> 泵棚资料 </a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ABASETANKHISTROY">
								<li>
								<a href="http://192.168.1.232/index#/storageTankInfo"
								id="tab16" target="_blank"><span class="fa fa-file-text-o"></span> 储罐存量记录 </a>
								</li>
								</shiro:hasPermission>
							</ul>
									</div>
								</div>
						</div>
						</div>
						<div class="todo-content" style="padding-top: 0px; padding-bottom: 0px;">
							<div class="portlet light">
								<div class="portlet-title">
									<div class="caption">
										<i class="icon-bar-chart font-green-sharp hide"></i> <span
											class="caption-subject font-green-sharp bold uppercase">内容</span>
									</div>
								</div>
								<div class="portlet-body">
									<div class="row">
										<div class="col-md-12 col-sm-12">
						<div class="btn-group buttons  col-md-6 col-sm-6">
							<button class="btn btn-default btn-circle mar-r-10 btn-add" type="button" data-toggle="modal">
								<span class="fa fa-plus"></span><span class="text">添加</span>
							</button>
							<button class="btn btn-default btn-circle mar-r-10 modify" type="button">
								<span class="fa fa-edit"></span><span class="text">修改</span>
							</button>
							<button class="btn btn-default btn-circle mar-r-10 btn-remove"  type="button">
						<span class="fa fa-remove"></span><span class="pad-l-5">删除</span>
					</button> 
					
					<button class="btn btn-default btn-circle mar-r-10 btn-file" style="display: none" type="button">
						<span class="fa fa-edit"></span><span class="pad-l-5">文件管理</span>
					</button> 
					
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
				<button class="btn btn-default btn-square showVir"  style="text-align: right;float:right;margin-right: 15px""type="button">
			</button>
						<div class="form-horizontal ln-letters" style="float:right;margin-right: 15px" ><a href="#" class="all">ALL</a><a href="#" class="a">A</a><a href="#" class="b">B</a><a href="#" class="c">C</a><a href="#" class="d">D</a><a href="#" class="e">E</a><a href="#" class="f">F</a><a href="#" class="g">G</a><a href="#" class="h">H</a><a href="#" class="i">I</a><a href="#" class="j">J</a><a href="#" class="k ">K</a><a href="#" class="l">L</a><a href="#" class="m">M</a><a href="#" class="n">N</a><a href="#" class="o ">O</a><a href="#" class="p">P</a><a href="#" class="q">Q</a><a href="#" class="r">R</a><a href="#" class="s">S</a><a href="#" class="t">T</a><a href="#" class="u">U</a><a href="#" class="v">V</a><a href="#" class="w">W</a><a href="#" class="x">X</a><a href="#" class="y">Y</a><a href="#" class="z  ln-last">Z</a></div>
                        <div class="col-md-12">
						<div class="tab-content">
                              <div data-role="baseInfoGrid"></div>
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
	</div>
</div>
</div>
