<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--长江石化码头货物进出港通过量统计-->
	<div class="row">
		<div class="col-md-12">
					<form action="#" class="form-horizontal ">
							<div class="row">
								<div class="form-body">
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
									<div class="col-md-4">
									<label class="control-label col-md-3">客户名称:</label>
									<div class="col-md-9" style="display: block;padding-right: 0;">
									    <select class="form-control" id="clientId" name="clientId">
										    <option value="59" selected="selected">BP（中国）工业油品有限公司</option>
											<option value="2794">太仓东华能源燃气有限公司</option>
											<option value="4027">中国石油天然气股份有限公司华东润滑油厂</option>
											<option value="1869">苏州华苏塑料有限公司</option>
											<option value="909">建滔（太仓）化工有限公司</option>
										</select>
									</div>
									</div>
									<div class="col-md-2">
									  <button type="button" class="btn btn-success search" onclick="InOutPort.search();">
											<span class="glyphicon glyphicon-search"></span>&nbsp;
									  </button>
								    </div>
								</div>
						</div>
					</form>
					<div data-role="inOutPortGrid"></div>
				</div>
			</div>
