<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- <title>储罐周转率</title> -->
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
										<!-- <div class="col-md-1" id="tankIds"></div> -->
									<div class="col-md-4 col-md-offset-1">
									  <button type="button" class="btn btn-success " onclick="TankRate.search()">
											<span class="glyphicon glyphicon-search"></span>&nbsp;
									  </button>
									  <button type="button" class="btn  btn-primary " onclick="HisTank.initList()">
											<span class="fa fa-list-ul"></span>&nbsp;
									  </button>
								    </div>
								</div>
							</div>
					</form>
				</div>
		</div>
			   <div class="portlet-body">
				<div class="table-container">
					<table class="table table-striped table-bordered table-hover" id="tankRate">
						<thead>
							<tr>
								<th>品名</th>
								<th>类型</th>
								<th>最大可存储量（吨）</th>
								<th>本月进货数量（吨）</th>
								<th>累计进货数量（吨）</th>
								<th>本月储罐周转率（次）</th>
								<th>累计周转率（次）</th>
							</tr>
						</thead>
						<tbody id="tankRateBody">
						</tbody>
					</table>
				</div>
			</div>