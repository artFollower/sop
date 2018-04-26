<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="row">
	<div class="col-md-12">
		<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-list-alt"></i>流量计台帐<span>
		<a style="padding-left: 20px;" class="hidden-print" onclick="shipDeliveryMeasure.exportExcel()">
		 <i class="fa fa-file-excel-o">&nbsp;导出</i></a>
		 </span>
				</div>
			</div>
			<div>
				<form action="#" class="form-horizontal sdm">
					<div id="roleManagerQueryDivId" hidden="true">
						<div class="row">
							<div class="form-body">
								<div class="form-group">
									<div class="col-md-3">
										<label class="control-label col-md-4">船舶名</label>
										<div class="col-md-8">
											<input class="form-control" id="ship" type="text">
										</div>
									</div>
									<div class="col-md-6">
										<label class="control-label col-md-2">日期:</label>
										<div class="col-md-10">
											<div class="input-group date-picker input-daterange">
												<input type="text" class="form-control" id="startTime">
												 <span class="input-group-addon">到</span>
												<input type="text" class="form-control" id="endTime">
											</div>
										</div>
									</div>
									<div class="col-md-3">
										<button type="button" class="btn btn-success search ">
											<span class="glyphicon glyphicon-search"></span>&nbsp;
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="btn-group buttons">
				<button class="btn btn-default btn-circle mar-r-10 btn-search" type="button">
					<span class="fa fa-search"></span><span class="text">搜索</span>
				</button>
			</div>
			<div class="col-md-12">
				<div data-role="shipDeliveryMeasure" ></div>
			</div>
		</div>
	</div>
</div>

