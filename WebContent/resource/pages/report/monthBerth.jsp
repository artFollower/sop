<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>月度泊位利用率</title>
<style type="text/css">
#monthBerth > thead > tr > th,#monthBerth > thead > tr > td, #monthBerth > tbody > tr > td {
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
						<i class="fa fa-list-alt"></i>泊位利用率
						<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right">
							<a style="padding-left: 20px;" class="hidden-print" onclick="MonthBerth.exportMonthBerth();">
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
												<label class="control-label col-md-2" style="padding-left: 0px;">时间区间:</label>
												<div class="col-md-10">
													<div class="input-group date-picker input-daterange">
														<input type="text" class="form-control"   
															id="startTime"> <span class="input-group-addon">到</span>
														<input type="text" class="form-control" id="endTime">
													</div>
												</div>
											</div>
										</div>
									<div class="col-md-4">
									  <button type="button"   class="btn btn-success search" onclick="MonthBerth.queryResult();">
											<span class="fa fa-search"></span>&nbsp;
									  </button>
									   <button type="button" class="btn  btn-primary " onclick="BerthDetail.initList()">
											<span class="fa fa-list-ul"></span>&nbsp;
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
					<table class="table table-striped table-hover table-bordered " id="monthBerth" style="width: 100%;">
						<thead>
						  <tr >
						    <th >泊位</th>
						    <th >乙二醇</th>
						    <th >甲醇</th>
						    <th >苯</th>
						    <th >正构烷烃</th>
						    <th >LAB</th>
						    <th >0#</th>
						   <!--  <th >93#</th> -->
						    <th >92#</th>
						    <th >LPG</th>
						    <th >VCM</th>
						    <th >BP厂</th>
						    <th >华东厂</th>
						    <th >总计</th>
						  </tr>
						</thead>											
						<tbody id="monthBerthBody">
						</tbody>
					</table> 
				</div>
			</div>
		 </div>
		</div>
	</div>
</body>
</html>