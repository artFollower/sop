<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>



	<input type="hidden" value="" class="planId" />
	<!-- BEGIN PAGE HEADER-->
	<div class="portlet">
		<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-indent"></i>到港计划详情信息
				</div>
				<!-- 
				<div class="col-md-8">
				    <input type="text" id="weight" class="form-control" />
				    <script>Arrival.queryWeight();</script>
				</div>
				 -->
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form class="form-horizontal  arrival-form">
				<div class="form-body">
					<table width="100%">
						<tr>
							<td colspan="2"><h4 class="form-section">基本信息</h4></td>
						</tr>
						<tr>
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">船英文名<span class="required">*</span></label>
									<div class="col-md-8 " id="ship">
									<input id="shipId"  type="text" data-provide="typeahead" data-required="1" data-type="Require" class="form-control shipId">
									</div>
								</div>
							</td>
							<td class=" col-md-4" >
								<div class="form-group">
									<label class="control-label col-md-4">船名<span
										class="required">*</span></label>
									<div class="col-md-8 " id="shipRef">
									<input id="shipRefId"  type="text" data-provide="typeahead" data-required="1" data-type="Require" class="form-control shipRefId">
									</div>
								</div>
							</td>
						</tr>
						
						
						<tr>
							
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">预计到港区间<span
										class="required">*</span></label>
									<div class="col-md-8">
										<div
											class="input-group date-picker input-daterange"
											data-date-format="yyyy-mm-dd">
											<input type="text" class="form-control arrivalTime"  data-required="1" data-type="Require"
												name="from" id="arrivalTime">  <input  type="text" 
												class="form-control endTime" style="display: none" name="to" id="endTime">
										</div>
									</div>
								</div>
							</td>
							<td class=" col-md-4">
								<div class="form-group">
								<div class="col-md-4"></div>
								<div class="col-md-8">
									<button type="button" style="display: none" onclick="Arrival.shipInfoDialog()" class="btn btn-primary shipInfo">查看船舶基本信息</button>
									<label class="checkbox-inline" > <input type="checkbox" value="" id="isPassShip"  name="isPassShip"> 通过船
									
								</label> 
									<label class="checkbox-inline" > <input type="checkbox" value="" id="isTrim" name="isTrim"> 已理货
								</label> 
								<label style="display: none" id='isPassShow'>此为通过船</label>
								</div>
								</div>
							</td>
						</tr>
						<tr >
						<td class=" col-md-4">
						<div class="form-group">
						<div class="col-md-4"></div>
						<div class="col-md-8"> 
								<!-- <label class="checkbox-inline"> <input  type="checkbox" value="" id="isDeclareCustom" name="isDeclareCustom"> 申报海关
								</label>  -->
								<label class="checkbox-inline"> <input type="checkbox" value="" id="isCustomAgree" name="isCustomAgree"> 海关同意卸货
									</label>
									</div>
						</div>
						</td>
						
						<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">到港吃水</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" class="form-control shipArrivalDraught" name="shipArrivalDraught"  onkeyup="config.clearNoNum(this)"  />
									</div>
								</div>
							</td>
						
						</tr>
						
						<tr >
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">产地</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" class="form-control originalArea" name="originalArea" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-4" colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">备注</label>
									<div class="col-md-10">
										<textarea class="form-control description" maxlength="100" rows="2"></textarea>
									</div>
								</div>
							</td>
						</tr>
						
						<tr class="check" style="display: none">
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">审核人</label>
									<div class="col-md-8">
										<input type="text" readonly  class="form-control checkUser" name="checkUser"  />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">审核日期</label>
									<div class="col-md-8">
										<input type="text" readonly  class="form-control checkTime" name="checkTime"  />
									</div>
								</div>
							</td>
						</tr>
						
						
					</table>
					<div class="modal-footer">
						<div class="col-md-offset-3 col-md-9">
						<shiro:hasPermission name="AARRIVALPLANUPDATE">
							<button type="button" class="btn btn-primary" onclick="Arrival.arrayEdit()">保存</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="ACONFIRMARRIVAL">
							<button type="button" class="btn btn-primary" id="confirmArrival" onclick="Arrival.confirmArrival()">确认到港</button>
							</shiro:hasPermission>
						</div>
					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>

	
		<div class="portlet box blue" style="border-color: #777">
			<div class="portlet-title" style="background-color: #777">
				<div class="caption">
					<i class="icon-edit"></i>未审批货批信息
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
				</div>
			</div>
			<div class="portlet-body">
				<div class="table-toolbar">
					<div class="btn-group">
					<shiro:hasPermission name="AARRIVALPLANADD">
							<button id="goodsAdd"  onclick="Arrival.doOpen(2)" class="btn btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-plus"></span><span class="text">添加</span>
				</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="AAPCARGOMODIFY">
							<button id="goodsCheck" onclick="Arrival.checkGoods()" class="btn btn-default btn-circle mar-r-10 btn-check" type="button">
					<span class="fa fa-file-o"></span><span class="text">批量确认</span>
				</button>
						</shiro:hasPermission>
					</div>
				</div>
				
				<table class="table table-striped table-hover table-bordered" id="goods-table">
					<thead>
						<tr>
							<th style="width: 8px;">
								<input type="checkbox" class="group-checkable" data-set="#goods-table .checkboxes" />
							</th>
							<th style="text-align: center">货主</th>
							<th style="text-align: center">货品</th>
							<th style="text-align: center">贸易类型</th>
							<th style="text-align: center">计划数量(吨)</th>
							<th style="text-align: center">申报海关</th>
							<!-- <th style="text-align: center">海关同意卸货</th> -->
							<th style="text-align: center">海运提单号</th>
							<th style="text-align: center">海运提单数量</th>
							<th style="text-align: center">接卸要求</th>
							<th style="text-align: center">操作</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div>
		</div>

		<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<h4 class="form-section">统计</h4>
					<table class="table table-striped table-hover table-bordered"
						id="plantotal-table">
						<thead>
							<tr>
								<th style="text-align: center">货品</th>
								<th style="text-align: center">总量(吨)</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>

	
		<div class="portlet box blue" style="border-color: #777">
			<div class="portlet-title" style="background-color: #777">
				<div class="caption">
					<i class="icon-edit"></i>已审批货批信息
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
				</div>
			</div>
			<div class="portlet-body" style="overflow-x:auto;">
				<table class="table table-striped table-hover table-bordered"   id="goods-table-sure">
					<thead>
						<tr>
							<th style="text-align: center">货主</th>
							<th style="text-align: center">货品</th>
							<th style="text-align: center">贸易类型</th>
							<th style="text-align: center">计划数量(吨)</th>
							<th style="text-align: center">合同批次号</th>
							<th style="text-align: center">关联合同</th>
							<th style="text-align: center">货代</th>
							<!-- <th style="text-align: center">产地</th> -->
							<th style="text-align: center">仓储性质</th>
							<th style="text-align: center">商检单位</th>
							<th style="text-align: center" id="spass">通过商检数</th>
							<th style="text-align: center">申报海关</th>
							<!-- <th style="text-align: center">海关同意卸货</th> -->
							<th style="text-align: center">海运提单号</th>
							<th style="text-align: center">海运提单数量</th>
							<th style="text-align: center">接卸要求</th>
							<th style="text-align: center">状态</th>
							<th style="text-align: center">操作</th>
						</tr>
					</thead>
					<tbody >

					</tbody>
				</table>
			</div>
		</div>
		
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<h4 class="form-section">统计</h4>
			<table class="table table-striped table-hover table-bordered"
				id="suretotal-table">
				<thead>
					<tr>
						<th style="text-align: center">货品</th>
						<th style="text-align: center">总量(吨)</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		
	<div class="modal-footer">
		<div class="col-md-offset-3 col-md-9">
			<a href="#/arrival" class="btn btn-default">返回</a>
		</div>
	</div>
	<!-- END PAGE -->
