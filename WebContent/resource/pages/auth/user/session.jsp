<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<!-- BEGIN MAIN CONTENT -->
<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title hidden">
				<div class="caption">
					<i class="fa fa-shopping-cart"></i>在线用户列表
				</div>
			</div>
			<div class="row">
				<div class="btn-group buttons col-md-6 col-sm-6">
					<button class="btn btn-default btn-circle mar-r-10 refresh" type="button">
						<span class="fa fa-refresh"></span><span class="pad-l-5">刷新</span>
					</button>
				</div>

			</div>
			<div data-role="sessionGrid"></div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->
