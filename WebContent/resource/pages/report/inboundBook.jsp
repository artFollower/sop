<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!--分流台账明细报表-->
	<div class="row">
	<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					&nbsp;<i class="fa fa-indent"></i>调度日志明细表
					<shiro:hasPermission name="ADISPATCHLOGDETAILEXCEL">
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
									<div class="col-md-6">
											<div class="form-group">
											<input id="reportListTab" class="hidden" value="14">
												<label class="control-label col-md-2" style="padding-left: 0px;">时间区间:</label>
												<div class="col-md-6">
													<div class="input-group date-picker input-daterange">
														<input type="text" class="form-control"  id="startTime"> 
														<span class="input-group-addon">到</span>
														<input type="text" class="form-control" id="endTime">
													</div>
												</div>
												
												<label class="control-label" style="width:50px;float:left;">船名:</label>
				              <div style="margin-left:15px;float:left;">
				                  <input name="shipId" maxlength="32" class="form-control" type="text"id="shipId" data="0" style="width:180px;"/>
				              </div>
											</div>
										</div>
									<div class="col-md-4 col-md-offset-2">
									  <button type="button" class="btn btn-success search" onclick="InBoundBook.search();">
											<span class="glyphicon glyphicon-search"></span>&nbsp;
									  </button>
								    </div>
								</div>
							</div>
					</form>
					<div id="scrollbar">
					<div class="viewport">
					<div data-role="inBoundBookGrid" class="overview"></div>
					</div>
					</div>
				</div>
				</div></div>
			<div>
		 </div>
		</div>