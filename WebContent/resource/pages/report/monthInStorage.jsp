<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--月仓储入库明细表-->
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
									  <button type="button" class="btn btn-success search" onclick="MonthInStorage.search()">
											<span class="glyphicon glyphicon-search"></span>&nbsp;
									  </button>
								    </div>
								</div>
							</div>
					</form>
					<div data-role="monthInStorageGrid"></div>
				</div>
		</div>