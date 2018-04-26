<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>年度泊位利用率</title>
</head>
<body>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet">
				<div class="portlet-title hidden">
					<div class="caption">
						<i class="fa fa-list-alt"></i>年度泊位利用率
						<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right">
							<a style="padding-left: 20px;" class="hidden-print" onclick="YearBerth.exportYearBerth();">
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
									<label class="control-label col-md-1">日期:</label>
									<div class="col-md-2" style="display: block;padding-right: 0;">
										<input class="form-control year-picker" id="date" style="width:180px;" type="text" placeholder="日期">
									</div>
									<label class="control-label col-md-1">货品:</label>
									<div class="col-md-2" style="display: block;padding-right: 0;">
										<input type="text" id="productName" class="form-control intentTitle" style="width:180px;" placeholder="货品"/>
									</div>
									<div class="col-md-2">
									  <button type="button" class="btn btn-success search" onclick="YearBerth.queryResult();">
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
					<table class="table table-striped table-bordered table-hover" id="yearBerth" style="width: 100%;">
						<thead>
						  <tr>
						    <th>泊位</th>
						    <th>1#泊位</th>
						    <th>2#泊位</th>
						    <th>3#泊位</th>
						    <th>4#泊位</th>
						    <th>5#泊位</th>
						    <th>6#泊位</th>
						    <th>7#泊位</th>
						  </tr>
						</thead>
						<tbody id="yearBerthBody">
						</tbody>
					</table> 
				</div>
			</div>
		 </div>
		</div>
	</div>
</body>
</html>