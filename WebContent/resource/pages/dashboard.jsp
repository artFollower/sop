<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
<style>
.timeline > li .timeline-time {
    margin-top:10px;
}
.timeline>li .timeline-time span {
	display: block;
	text-align: left;
}

.timeline::before {
    width: 6px;
    left : 19.8%;
}

.timeline li.timeline-grey .timeline-body::after {
	border-right-color: #999;
}

.timeline li.timeline-grey .timeline-body {
	background: none repeat scroll 0 0 #999;
}

.timeline li.timeline-grey .timeline-time span.time {
	color: #999;
}
.timeline > li .timeline-time span.time {
    font-size: 18px;
    font-weight: 300;
    line-height: 38px;
}
.timeline > li .timeline-icon {
    height: 12px;
    width: 12px;
    left : 22%;
    top : 19px;
    box-shadow: 0 0 0 3px #ccc;
    
}
</style>
</head>
<div>
	<div class="row">
		<div class="col-md-6 col-sm-12">
			<div class="portlet light">
				<div class="portlet-title tabbable-line">
					<div class="caption caption-md">
						<i class="icon-globe font-green-haze hide"></i>
							<span class="caption-subject font-green-haze bold uppercase">个人信息</span>
					</div>
				</div>
				<div class="portlet-body">
					<div class="row">
						<div class="col-md-4 col-md-offset-1"  style="padding-left: 0px; padding-right: 0px;">
										<img id="photo" width="200" height="200" src="resource/admin/pages/media/profile/profile-img.jpg" style="box-shadow: 0 0 0 1px #aaa;padding:5px;" class="img-responsive" alt="">
						</div>
						<div class="col-md-7">
							<div class="form-body">
								<div class="row" >
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label font-green-haze col-md-5" style="text-align:right;">姓名:</label> <label class="control-label col-md-7" style="text-align:left;" id="name"> </label>
										</div>
									</div>
								</div>
								<!--/row-->
								<div class="row" style="margin-top: 20px;">
									<!--/span-->
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label font-green-haze col-md-5" style="text-align:right;">电子邮箱:</label> <label class="control-label col-md-7" style=";text-align:left;" id="email"> </label>
										</div>
									</div>
									<!--/span-->
								</div>
								<div class="row" style="margin-top: 20px;">
									<!--/span-->
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label font-green-haze col-md-5" style="text-align:right;">联系电话:</label> <label class="control-label col-md-7" style="text-align:left;" id="phone"> </label>
										</div>
									</div>
									<!--/span-->
								</div>
							</div>
						</div>
					</div>
                     <div class="row">
                     <!--/row-->
								<div class="row" style="margin-top: 20px;">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label font-green-haze col-md-2" style="text-align:right;">部门:</label> <label class="control-label col-md-10" style="text-align:left;" id="deps"> </label>
										</div>
									</div>
								</div>
								<div class="row" style="margin-top: 20px;">
									<!--/span-->
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label font-green-haze col-md-2" style="text-align:right;">角色:</label> <label class="control-label col-md-10" style="text-align:left;" id="roles"> </label>
										</div>
									</div>
									<!--/span-->
								</div>
                     
                     </div>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-12">
			<div class="portlet light">
				<div class="portlet-title tabbable-line">
					<div class="caption caption-md">
						<i class="icon-globe font-green-haze hide"></i>
							<span class="caption-subject font-green-haze bold uppercase">任务</span>
					</div>
					<div class="actions">
						<div class="btn-group">
							<a class="btn btn-default btn-sm" href="#message">更多</a>
						</div>
					</div>
				</div>
				<div class="portlet-body">
					<!--BEGIN TABS-->
					<div class="task-content">
						<div class="scroller" style="height: 300px;" data-always-visible="1" data-rail-visible1="0" data-handle-color="#D7DCE2">
							<ul class="feeds operationMsgUL">

							</ul>
						</div>
					</div>
					<!--END TABS-->
				</div>
			</div>

		</div>
	</div>
	<div class="clearfix"></div>
	<div class="row">
		<div class="col-md-6 col-sm-12">
			<div class="portlet light">
				<div class="portlet-title tabbable-line">
					<div class="caption caption-md">
						<i class="icon-globe font-green-haze hide"></i>
							<span class="caption-subject font-green-haze bold uppercase">系统消息</span>
					</div>
					<div class="actions">
						<div class="btn-group">
							<a class="btn btn-default btn-sm" href="#message">更多</a>
						</div>
					</div>
				</div>
				<div class="portlet-body">
					<!--BEGIN TABS-->
					<div class="task-content">
						<div class="scroller" style="height: 300px;" data-always-visible="1" data-rail-visible1="0" data-handle-color="#D7DCE2">
							<ul class="feeds sysMsgUL">

							</ul>
						</div>
					</div>
					<!--END TABS-->
				</div>
			</div>
		</div>
		<div class="col-md-6 col-sm-12">
			<!-- BEGIN REGIONAL STATS PORTLET-->
			<div class="portlet light ">
				<div class="portlet-title">
					<div class="caption">
							<span class="caption-subject font-green-haze bold uppercase">最近操作</span>
					</div>
					<div class="actions">
						<div class="btn-group">
							<a class="btn btn-default btn-sm" href="#/sys/log">更多</a>
						</div>
					</div>
				</div>
				<div class="portlet-body">
					<div class="scroller" style="height: 300px;" data-always-visible="1" data-rail-visible="0">
						<div class="row">
							<div class="col-md-12">
								<ul class="timeline operationLog">
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- END REGIONAL STATS PORTLET-->
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<script>
	$(function() {
		dashboard.init(<shiro:principal property="id"/>);
	});
</script>
