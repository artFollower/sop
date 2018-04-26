<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<style type="text/css">
#pumpShedRotation > thead > tr > th,#pumpShedRotation > thead > tr > td, #pumpShedRotation > tbody > tr > td {
    border-top: 1px solid #ddd;
    line-height: 1.42857;
    padding: 8px;
    text-align:center;
    vertical-align: middle;
   } 
</style>
</head>
<div class="row">
	<div class="portlet">
		<div class="col-md-12">
			<form action="#" class="form-horizontal ">
				<div class="row">
					<div class="form-body">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-2" style="padding-left: 0px;">时间区间:</label>
								<div class="col-md-10">
									<div class="input-group date-picker input-daterange">
										<input type="text" class="form-control" id="startTime">
										 <span class="input-group-addon">到</span>
										<input type="text" class="form-control" id="endTime">
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-2">
							<button type="button" class="btn btn-success" id="pumpSRSearch">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</div>
					</div>
				</div>
			</form>
			<div class="portlet-body">
				<div class="table-container">
					<table class="table table-striped table-hover table-bordered " id="pumpShedRotation" style="width: 100%;">
						<thead>
								<tr>
									<th>作业种类</th>
									<th>1#泵棚</th>
									<th>2#泵棚</th>
									<th>3#泵棚</th>
									<th>4#泵棚</th>
									<th>5#泵棚</th>
									<th>6#泵棚</th>
									<th>7#泵棚</th>
									<th>总计</th>
								</tr>
							</thead>
						<tbody id="pumpShedRotationBody">
						</tbody>
					</table> 
				</div>
			</div>
		</div>
	</div>
</div>