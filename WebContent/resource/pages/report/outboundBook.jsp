<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!--分流台账明细报表-->
	<div class="row">
		<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					&nbsp;<i class="fa fa-indent"></i>分流台账明细表
					<shiro:hasPermission name="ASHIPLEDGERDETAILEXCEL">
					<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right">
							<a style="padding-left: 20px;" class="hidden-print" onclick="ReportForm.exportExcel();" >
								<i class="fa fa-file-excel-o">&nbsp;导出</i>
							</a>
						</span>
						</shiro:hasPermission>
				</div>
             </div>
             <div class="portlet-body">
		<div class="col-md-12">
					<form action="#" class="form-horizontal">
							<div class="row">
								<div class="form-body ">
								<div class="col-md-4">
								<div class="form-group">
								  <label class="control-label col-md-4">船名：</label>
									<div class="col-md-8">
									<input type="text" class="form-control" id="shipName">
									</div>
								</div>
								
								
								</div>
									<div class="col-md-4">
											<div class="form-group">
											<input id="reportListTab" class="hidden" value="15">
												<label class="control-label col-md-2" style="padding-left: 0px;">时间区间:</label>
												<div class="col-md-10">
													<div class="input-group date-picker input-daterange">
														<input type="text" class="form-control"  id="startTime"> 
														<span class="input-group-addon">到</span>
														<input type="text" class="form-control" id="endTime">
													</div>
												</div>
											</div>
										</div>
									<div class="col-md-2 col-md-offset-2">
									  <button type="button" class="btn btn-success search" onclick="OutBoundBook.search();">
											<span class="glyphicon glyphicon-search"></span>&nbsp;
									  </button>
								    </div>
								</div>
							</div>
					</form>
					<div id="scrollbar">
					<div class="viewport">
					<div data-role="outBoundBookGrid" class="overview"></div>
					</div>
					</div>
				</div>
			<div></div></div>
		 </div>
		</div>