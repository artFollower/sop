<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
<!--
#timeType {
	border: solid 0px #000;
	text-align: right;
	padding-right: 20px;
	padding-left: 0px;
	appearance: none;
	-moz-appearance: none;
	-webkit-appearance: none;
	background: url("resource/admin/pages/img/arrow.png") no-repeat scroll
		right center transparent;
}
-->
</style>
<div class="row">
	<div class="col-md-12">

		<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-list-alt"></i>称重信息统计 <span
						style="font-size: small; margin: 0; padding-right: 18px;"
						class="pull-right"> </span>
				</div>
			</div>
			<div class="portlet-body">
				<div class="col-md-12 tabbable-custom">
					<form action="#" class="form-horizontal contract-update-form">
						<div id="roleManagerQueryDivId" hidden="true">
							<div class="row">
								<div class="form-body">
									<div class="col-md-12">
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label col-md-4 inPort">发货口</label>
												<div class="col-md-8">
													<input class="form-control" id="inPort">
												</div>
											</div>
										</div>
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label col-md-4 productId">货品</label>
												<div class="col-md-8">
													<input class="form-control" id="productId">
												</div>
											</div>
										</div>
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label col-md-4 clientId">货主</label>
												<div class="col-md-8">
													<input class="form-control" id="clientId">
												</div>
											</div>
										</div>
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label col-md-4 tankId">储罐</label>
												<div class="col-md-8">
													<input class="form-control" id="tankId">
												</div>
											</div>
										</div>
									</div>

									<div class="col-md-12">
										<div class="col-md-6">
											<div class="form-group">
												<div class="col-md-2" style="padding-left: 0px;">
													<select class="form-control time" id="timeType">
														<option value="1">开票时间</option>
														<option value="2">出库时间</option>
													</select>
												</div>
												<div class="col-md-10">
													<div class="input-group date-picker input-daterange"
														data-date-format="yyyy-mm-dd">
														<input type="text" class="form-control" name="startTime"
															id="startTime"> <span class="input-group-addon">到</span>
														<input type="text" class="form-control" name="endTime"
															id="endTime">
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-3">
											<div class="form-group">
												<label class="control-label col-md-4 year">年</label>
												<div class="col-md-3 "  style="padding-right: 0px">
														<input class="form-control year-picker" id="year">
												</div>
												<label class="control-label col-md-1 month" style="padding:auto 0px">月</label>
												<div class="col-md-4">
													<select class="form-control " id="month">
														<option value="0"></option>
														<option value="1">一月</option>
														<option value="2">二月</option>
														<option value="3">三月</option>
														<option value="4">四月</option>
														<option value="5">五月</option>
														<option value="6">六月</option>
														<option value="7">七月</option>
														<option value="8">八月</option>
														<option value="9">九月</option>
														<option value="10">十月</option>
														<option value="11">十一月</option>
														<option value="12">十二月</option>
													</select>
												</div>
											</div>
										</div>

										<div class="col-md-3">
											<div class="form-group ">
												<div class="col-md-8 btn-group" style="float: right;">
													<button type="button" class="btn btn-success" id="search">
														<span class="fa fa-search"></span>&nbsp;
													</button>
													<button type="button" style="margin-left: 8px;" id="reset"
														class="btn btn-primary ">
														<span class="fa fa-undo" title="重置"></span>&nbsp;
													</button>
												</div>
											</div>
										</div>

									</div>
								</div>
							</div>
						</div>
					</form>
					<!-- </div> -->
					<div class="btn-group buttons col-md-12">
						<button class="btn  btn-default btn-circle mar-r-10"
							id="searchBtn" type="button">
							<span class="fa  fa-search"></span><span class="text">搜索</span>
						</button>

						<div class="checkbox itemCtr"
							style="float: right; margin-left: 10px;">
							<label class="checkbox-inline"> <input type="checkbox"
								data='2' id="isShowTruck" checked value="option2">车发信息
							</label> <label class="checkbox-inline" style="margin-left: 30px;">
								<input type="checkbox" data='2' checked id="isShowOutbound"
								value="option2">船发信息
							</label> <label class="checkbox-inline" style="margin-left: 30px;">
								<input type="checkbox" data='1' id="isShowAll" value="option2">显示统计信息
							</label>
						</div>
						<shiro:hasPermission name="AWEIGHDAILYSTATEMENTEXCEL">
							<a class="btn " style="float: left; margin-left: 10px;" id="ex3"
								type="hidden" onclick="weighDataTable.exportXML(3)"><span
								class="fa fa-file-excel-o">&nbsp;导出发货明细表</span>&nbsp; </a>
							<a class="btn " style="float: left; margin-left: 10px;" id="ex1"
								type="hidden" onclick="weighDataTable.exportXML(1)"><span
								class="fa fa-file-excel-o">&nbsp;导出发货储品统计表</span>&nbsp; </a>
							<a class="btn " style="float: left; margin-left: 10px;" id="ex2"
								type="hidden" onclick="weighDataTable.exportXML(2)"><span
								class="fa fa-file-excel-o">&nbsp;导出车发发货口统计表</span>&nbsp; </a>
							<a class="btn " style="float: left; margin-left: 10px;" id="ex4"
								type="hidden" onclick="weighDataTable.exportXML(4)"><span
								class="fa fa-file-excel-o">&nbsp;导出车发储罐统计表</span>&nbsp; </a>
							<a class="btn " style="float: left; margin-left: 10px;" id="ex5"
								type="hidden" onclick="weighDataTable.exportXML(5)"><span
								class="fa fa-file-excel-o">&nbsp;导出全车位统计表</span>&nbsp; </a>
						</shiro:hasPermission>
					</div>
					<div class="col-md-12">
						<!--  	<div class="tab-content">-->
						<div data-role="weighData"></div>
						<!-- 	</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 			</div>
		</div>
	</div>
</div> -->
