<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>吞吐量统计表</title>
<style type="text/css">
	.tabStyle{
		text-align: center;
		vertical-align: middle;
		line-height: 22px;
	}
</style>
</head>
<body>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet">
				<div class="portlet-title hidden">
					<div class="caption">
						<i class="fa fa-list-alt"></i>吞吐量统计表
						<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right">
							<a style="padding-left: 20px;" class="hidden-print">
								<i class="fa fa-file-excel-o">&nbsp;导出</i>
							</a>
						</span>
					</div>
				</div>
				<div>
					<form action="#" class="form-horizontal contract-update-form cflltzlb">
						<div id="roleManagerQueryDivId">
							<div class="row">
								<div class="form-body inOutPort">
									<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4" style="padding-left: 0px;">起止日期:</label>
												<div class="col-md-8">
													<div class="input-group date-picker input-daterange">
														<input type="text" class="form-control"id="startTime"> 
														  <span class="input-group-addon">到</span>
														<input type="text" class="form-control" id="endTime"> 
													</div>
												</div>
											</div>
										</div>
									<div class="col-md-3">
									  <button type="button" class="btn btn-success search" onclick="Throughput.queryResult();">
											<span class="glyphicon glyphicon-search"></span>&nbsp;
									  </button>
								    </div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div>
			  <div class="portlet-body">
			  <div class="col-md-12" style="padding:20px">
			  <div class="row msgDiv" >
				<textarea rows="10" style="width:100%" id=throughtMsg></textarea>				
				</div>
				 <div class="row" style="float:right;">
				 <button type="button"  class="btn btn-primary " disabled id="save">保存</button>
				 </div>
				 </div>
				<div class="table-container col-md-12" style="margin-top:20px;">
					<table class="table-bordered tabStyle" id="throughput" style="width: 100%;">
						<thead>
							<tr class="tabStyle">
							    <th rowspan="2" class="tabStyle">品种</th>
							    <th rowspan="2" class="tabStyle">吞吐量/车发量</th>
							    <th rowspan="2" class="tabStyle">去年同期</th>
							    <th rowspan="2" class="tabStyle">增长率（%）</th>
							    <th rowspan="2" class="tabStyle">船舶数/车次</th>
							    <th rowspan="2" class="tabStyle">去年同期</th>
							    <th rowspan="2" class="tabStyle">增长率（%）</th>
							    <th colspan="2" class="tabStyle">上累</th>
							    <th colspan="4" class="tabStyle" id="monthTh">1-10月累计</th>
							</tr>
							<tr class="tabStyle">
							<th class="tabStyle" >吞吐量/车发量</th>
							    <th class="tabStyle" >船舶数/车次</th>
							    <th class="tabStyle" id="thisYearTh">2015年吞吐量</th>
							    <th class="tabStyle" id="lastYearTh">2014年吞吐量</th>
							    <th class="tabStyle">增长率（%）</th>
							    <th class="tabStyle" id="thisYearCountTh">2015年船舶数</th>
							</tr>
						</thead>
						<tbody id="throughputBody">
						</tbody>
					</table> 
				</div>
			</div>
		 </div>
		</div>
	</div>
</body>
</html>