<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 长江石化管道运输通过明细表 -->
<head>
<style type="text/css">
	.tabStyle{
		text-align: center;
		vertical-align: middle;
		line-height: 24px;
		border: 1px solid #CCC;
	}
</style>
</head>
<div class="row">
		<div class="col-md-12">
					<form action="#" class="form-horizontal">
							<div class="row">
								<div class="form-body ">
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
									  <button type="button" class="btn btn-success search" onclick="PipeDetail.search()">
											<span class="glyphicon glyphicon-search"></span>&nbsp;
									  </button>
								    </div>
								</div>
							</div>
					</form>
					  <div class="portlet-body">
					  <div style="min-height: 150px; width: 100%; overflow-y: auto;" class="grid-body">
						<table id="goodsTable" style="margin: 0px; border: 0px;width: 100%;" class="table table-responsive table-bordered table-hover">
						<thead class="grid-table-head" style="width: 100%;">
						<tr class="tabStyle">
							<th style="text-align:center;" rowspan="3" index="0">序号<div class="colResize"></div></th>
							<th style="text-align:center;" rowspan="3" index="1">日期<div class="colResize"></div></th>
							<th style="text-align:center;"  colspan="4" index="2">船舶起止时间<div class="colResize"></div></th>
							<th style="text-align:center;" rowspan="3" index="3">泊位<div class="colResize"></div></th>
							<th style="text-align:center;" rowspan="3" index="4">批次号<div class="colResize"></div></th>
							<th style="text-align:center;" rowspan="3" index="5">客户名称<div class="colResize"></div></th>
							<th style="text-align:center;" rowspan="3" index="6">货品<div class="colResize"></div></th>
							<th style="text-align:center;" rowspan="3" index="7">通过数(吨)<div class="colResize"></div></th>
							<th style="text-align:center;" rowspan="3" index="8">船名<div class="colResize"></div></th>
							<th style="text-align:center;" rowspan="3" index="9">备注<div class="colResize"></div></th>
							</tr>
							<tr class="tabStyle">
							    <th colspan="2" style="text-align:center;">到港</th>
							    <th colspan="2" style="text-align:center;">离港</th>
							</tr>
							<tr class="tabStyle">
							    <th style="text-align:center;">DAY</th>
							    <th style="text-align:center;">TIME</th>
							    <th style="text-align:center;">DAY</th>
							    <th style="text-align:center;">TIME</th>
							</tr>
							</thead>
							<tbody class="grid-table-body" id="pipeDetailBody" style="width:100%; height: auto; position: relative;">
							</tbody>
							</table>
							</div>
			</div>
				</div>
		</div>
