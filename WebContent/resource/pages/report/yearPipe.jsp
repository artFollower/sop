<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>年度管道运输汇总表</title>
<style type="text/css">
#yearPipe > thead > tr > th,#yearPipe > thead > tr > td, #yearPipe > tbody > tr > td {
    border-top: 1px solid #ddd;
    line-height: 1.42857;
    padding: 8px;
    text-align:center;
    vertical-align: middle;
}
</style>


</head>
<body>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet">
				<div class="portlet-title hidden">
					<div class="caption">
						<i class="fa fa-list-alt"></i>年度管道运输汇总表
						<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right">
							<a style="padding-left: 20px;" class="hidden-print" onclick="YearPipe.exportYearPipe();">
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
									<label class="control-label col-md-1">年份:</label>
									<div class="col-md-2" style="display: block;padding-right: 0;">
										<input class="form-control year-picker" id="date" style="width:180px;" type="text" placeholder="日期">
									</div>
									<div class="col-md-2">
									  <button type="button" class="btn btn-success search" onclick="YearPipe.queryResult();">
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
				<div class="table-container">
					<table class="table table-striped table-bordered table-hover" id="yearPipe">
						<thead>
						  <tr>
						    <th rowSpan='2'>月份</th>
						    <th>甲醇</th>
						    <th>VCM</th>
						    <th>基础油（BP厂）</th>
						     <th colSpan='2'>基础油（华东厂）</th>
						     <th colSpan='2'>LPG</th>
						      <th>苯</th>
						       <th>正构烷烃</th>
						        <th>直链烷基苯</th>
						  </tr>
						  <tr>
						  <th>进港</th>
						  <th>进港</th>
						  <th>进港</th>
						  <th>进港</th>
						  <th>出港</th>
						  <th>进港</th>
						  <th>出港</th>
						  <th>进港</th>
						  <th>进港</th>
						   <th>出港</th>
						  </tr>
						</thead>
						<tbody id="yearPipeBody">
						</tbody>
					</table> 
				</div>
			</div>
		 </div>
		</div>
	</div>
</body>
</html>