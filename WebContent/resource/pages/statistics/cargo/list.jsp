<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="row">
	<div class="col-md-12">
		<div class="portlet">
		
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-file-text-o"></i>货批列表 <span class="pull-right">
						<a style="padding-left: 20px;" class="hidden-print"
						onclick="Cargo.exportExcel(1)"> <i class="fa fa-file-excel-o">导出</i></a>
					</span> <span class="pull-right"> <a style="padding-left: 20px;"
						class="hidden-print" onclick="Cargo.exportExcel(2)"> <i
							class="fa fa-file-excel-o">导出报表</i></a></span> <span class="pull-right">
						<a style="padding-left: 20px;" class="hidden-print"
						onclick="Cargo.dayStatics()"> <i class="fa fa-file-excel-o">导出历史日报表</i></a>
					</span>
				</div>
			</div>
			
			<div class="portlet-body">
				<form id="cargoListForm" action="#" class="form-horizontal">
					<div class="row">
						<div class="form-body">
							<div class="form-group">
								<div class="col-md-3">
									<label class="control-label col-md-4 ">货批号:</label>
									<div class="col-md-8">
										<input id="cargoCode" type="text" class="form-control">
									</div>
								</div>
								<div class="col-md-3">
									<label class="control-label col-md-4 ">货体号:</label>
									<div class="col-md-8">
										<input id="goodsCode" type="text" class="form-control">
									</div>
								</div>
								<div class="col-md-3">
									<label class="control-label col-md-4 ">货主:</label>
									<div class="col-md-8">
										<input id="clientId" type="text" class="form-control">
									</div>
								</div>
								<div class="col-md-3">
									<label class="control-label col-md-4">货品:</label>
									<div class="col-md-8">
										<input id="productId" type="text" class="form-control">
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-6">
									<label class="control-label col-md-2 ">统计日期:</label>
									<div class="col-md-10">
										<div class="input-group  date-picker input-daterange"
											data-date-format="yyyy-mm-dd">
											<input type="text" class="form-control" id="startTime">
											<span class="input-group-addon">到</span>
											<input type="text" class="form-control" id="endTime">
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<label class="control-label col-md-4">汇总方式:</label>
									<div class="col-md-8">
										<select id="isFinish"  class="form-control">
										<option selected value="0">未清盘</option>
										<option value="1">全部</option>
										</select>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group ">
										<div class="col-md-8 col-md-offset-4 input-group-btn">
											<button type="button" class="btn btn-success" id="searchCargo">
												<span class="fa fa-search"></span>&nbsp;
											</button>
											<button type="button" style="margin-left: 8px;"
												class="btn btn-primary reset">
												<span class="fa fa-undo" title="重置"></span>&nbsp;
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>


				<div class="col-md-12">
					
				
					<div class="checkbox" style="float: right;">
						<!-- <button class="btn btn-default" id="isFinish" data="0"
							style="margin-right: 10px;" data-placement="bottom"
							data-content="未清盘" type="button"></button> -->
						<button class="btn btn-default" id="showVir" data="1"
							style="margin-right: 10px;" data-placement="bottom"
							data-content="主库" type="button"></button>
						<button class="btn btn-default" id="showVirTime" data="2"
							style="margin-right: 30px;" data-placement="bottom"
							data-content="转账时间" type="button"></button>
						<label class="checkbox-inline" style="margin-right: 10px;">
							<input type="checkbox" data='1' id="isShowAll" value="option2">显示统计信息
						</label>
					</div>
				</div>

				<div class="col-md-12">
					<div class="col-md-12" data-role="cargoListGrid"></div>
				</div>
			</div>
		</div>
	</div>
</div>

