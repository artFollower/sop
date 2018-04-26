<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>外贸进出港统计表</title>
<style type="text/css">
	.tabStyle{
		text-align: center;
		vertical-align: middle;
		line-height: 24px;
	}
</style>
</head>
<body>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet">
				<div class="portlet-title hidden">
					<div class="caption">
						<i class="fa fa-list-alt"></i>外贸进出港统计表
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
												<label class="control-label col-md-2" style="padding-left: 0px;">时间区间:</label>
												<div class="col-md-10">
													<div class="input-group date-picker input-daterange">
														<input type="text" class="form-control"  id="startTime"
															id="startTime"> <span class="input-group-addon">到</span>
														<input type="text" class="form-control" id="endTime">
													</div>
												</div>
											</div>
										</div>
									<div class="col-md-4 col-md-offset-2">
									  <button type="button" class="btn btn-success search" onclick="TradePort.queryResult();">
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
					<table class="table table-striped table-bordered table-hover" id="tradePort">
						<thead>
							<tr>
							    <th>货品</th>
							    <th>泊位</th>
							    <th>进港/出港</th>
							    <th>重量</th>
							    <th>船舶总数</th>
						  	</tr>
						</thead>
						<tbody id="tradePortBody">
						</tbody>
					</table> 
				</div>
			</div>
		 </div>
		</div>
	</div>
</body>
</html>