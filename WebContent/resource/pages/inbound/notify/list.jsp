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
						<i class="fa fa-folder-o"></i><span class="mar-l-5">通知单</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="notice hidden"></div>
					<div class="row notice-list2">
						<div class="col-md-12">
							<div class="tiles todo-list">
							<div class="col-md-12">
								<div class="tile double  bg-blue-steel"
									onclick="notify.changeTab(this,0)">
									<div class="tile-body">
										<i class="fa fa-minus"></i>
									</div>
									<div class="tile-object">
										<div class="name">配管作业通知单</div>
										<div class="number"></div>
									</div>
								</div>
								<div class="tile double  bg-blue-steel" onclick="notify.changeTab(this,1)">
									<div class="tile-body">
										<i class="fa fa-fire"></i>
									</div>
									<div class="tile-object">
										<div class="name">苯加热作业通知单</div>
									</div>
								</div>
								<div class="tile bg-red-intense" onclick="notify.changeTab(this,2)">
									<div class="tile-body">
										<i class="fa fa-refresh"></i>
									</div>
									<div class="tile-object">
										<div class="name">打循环作业通知单(码头)</div>
									</div>
								</div>
								<div class="tile bg-red-intense" onclick="notify.changeTab(this,3)">
									<div class="tile-body">
										<i class="fa fa-refresh"></i>
									</div>
									<div class="tile-object">
										<div class="name">打循环作业通知单(库区)</div>
									</div>
								</div>
								<div class="tile bg-blue-steel" onclick="notify.changeTab(this,4)">
									<div class="tile-body">
										<i class="fa fa-magic"></i>
									</div>
									<div class="tile-object">
										<div class="name">管线清洗作业通知单(码头)</div>
									</div>
								</div>
								<div class="tile bg-blue-steel" onclick="notify.changeTab(this,5)">
									<div class="tile-body">
										<i class="fa fa-magic"></i>
									</div>
									<div class="tile-object">
										<div class="name">管线清洗通知单(库区)</div>
									</div>
								</div>
								</div>
								<div class="col-md-12">
								<div class="tile bg-blue-steel" onclick="notify.changeTab(this,6)">
									<div class="tile-body">
										<i class="fa fa-arrows-h"></i>
									</div>
									<div class="tile-object">
										<div class="name">扫线作业通知单(码头)</div>
									</div>
								</div>
								<div class="tile bg-blue-steel" onclick="notify.changeTab(this,7)">
									<div class="tile-body">
										<i class="fa fa-arrows-h"></i>
									</div>
									<div class="tile-object">
										<div class="name">扫线作业通知单(库区)</div>
									</div>
								</div>
								<div class="tile double  bg-blue-steel" onclick="notify.changeTab(this,8)">
									<div class="tile-body">
										<i class="fa fa-square-o"></i>
									</div>
									<div class="tile-object">
										<div class="name">清罐作业通知单</div>
									</div>
								</div>
								<div class="tile double  bg-blue-steel" onclick="notify.changeTab(this,9)">
									<div class="tile-body">
										<i class="fa fa-caret-square-o-up"></i>
									</div>
									<div class="tile-object">
										<div class="name">储罐放水作业通知单</div>
									</div>
								</div>
								<div class="tile double  bg-blue-steel" onclick="notify.changeTab(this,10)">
									<div class="tile-body">
										<i class="fa fa-circle-o-notch"></i>
									</div>
									<div class="tile-object">
										<div class="name">储罐开人孔作业通知单</div>
									</div>
								</div>
								</div>
								<div class="col-md-12">
								<div class="tile double  bg-blue-steel" onclick="notify.changeTab(this,11)">
									<div class="tile-body">
										<i class="fa fa-random"></i>
									</div>
									<div class="tile-object">
										<div class="name">转输作业通知单</div>
									</div>
								</div>
								<div class="tile double  bg-blue-steel" onclick="notify.changeTab(this,12)">
									<div class="tile-body">
										<i class="fa fa-exchange"></i>
									</div>
									<div class="tile-object">
										<div class="name">倒罐作业通知单</div>
									</div>
								</div>
								<div class="tile bg-red-intense" onclick="notify.changeTab(this,13)">
									<div class="tile-body">
										<i class="fa fa-sort-amount-asc"></i>
									</div>
									<div class="tile-object">
										<div class="name">接卸作业通知单(码头)</div>
									</div>
								</div>
								<div class="tile bg-red-intense" onclick="notify.changeTab(this,14)">
									<div class="tile-body">
										<i class="fa fa-sort-amount-asc"></i>
									</div>
									<div class="tile-object">
										<div class="name">接卸作业通知单(动力班)</div>
									</div>
								</div>
								<div class="tile bg-red-intense" onclick="notify.changeTab(this,15)">
									<div class="tile-body">
										<i class="fa fa-ship"></i>
									</div>
									<div class="tile-object">
										<div class="name">船发作业通知单(码头)</div>
									</div>
								</div>
								<div class="tile bg-red-intense" onclick="notify.changeTab(this,16)">
									<div class="tile-body">
										<i class="fa fa-ship"></i>
									</div>
									<div class="tile-object">
										<div class="name">船发作业通知单(库区)</div>
									</div>
								</div>
								</div>
								<div class="col-md-12">
								<div class="tile double  bg-blue-steel" onclick="notify.changeTab(this,17)">
									<div class="tile-body">
										<i class="fa fa-truck"></i>
									</div>
									<div class="tile-object">
										<div class="name">车发换罐作业通知单</div>
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
