<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
<!--
#timeType{
   border: solid 0px #000;
   text-align:right;
   padding-right:20px;
   padding-left:0px;
    appearance:none;
    -moz-appearance:none;
    -webkit-appearance:none;
    background:url("resource/admin/pages/img/arrow.png") no-repeat scroll right center transparent;
   }
-->
</style>
	<div class="row">
	<div class="col-md-12">
		<div class="portlet">
		<div class="portlet-title">
			<div class="caption" style="width: 100%;">
				<i class="fa fa-list-alt"></i>发货查询</div>
		</div>
			<div>
				<form action="#" class="form-horizontal">
				<div id="roleManagerQueryDivId" hidden="true">
					<div class="row">
					<div class="form-body">
						<div class="form-group">
							<div class="col-md-3">
							<label class="control-label col-md-4">车船号</label>
								<div class="col-md-8" id="vsInfo">
									<input  id="vsName"
										class="form-control" maxlength="11"/>
								</div>
							</div>
							<div class="col-md-3">
							<label class="control-label col-md-4">通知单号</label>
								<div class="col-md-8">
									<input  id="serial"
										class="form-control"  maxlength="30"/>
								</div>
							</div>
							<div class="col-md-3">
							<label class="control-label col-md-4">提单号</label>
								<div class="col-md-8">
									<input  id="ladingEvidence"
										class="form-control"  maxlength="30"/>
								</div>
							</div>
							<div class="col-md-3">
							 	<label class="control-label col-md-4">货品 </label>
							<div class="col-md-8">
							<input  id="productName" class="form-control"/>
							</div>
							 </div>
						</div>
						<div class="form-group">
							<div class="col-md-3">
								<label class="control-label col-md-4">开票类型 </label>
								<div class="col-md-8">
								<select class="form-control" id="deliverType">
							    <option value="0" selected>全部</option>
								<option value="1">车发</option>
								<option value="2">船发</option>
								<option value="3">转输</option>
								</select>
								</div>
							</div>
						<div class="col-md-3">
							<label class="control-label col-md-4">发货状态 </label>
							<div class="col-md-8">
							<select class="form-control" id="status">
						    <option value="-1" selected>全部</option>
							<option value="0">未出库</option>
							<option value="1">已出库</option>
							</select>
							</div>
						</div>
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
													<input  class="form-control" id="startTime"
														id="startTime"> <span class="input-group-addon">到</span>
													<input  class="form-control" id="endTime">
												</div>
											</div>
										</div>
						 </div>
						</div>
						<div class="form-group">
							 <div class="col-md-3">
								<label class="control-label col-md-4">货批号</label>
								<div class="col-md-8" id="vsInfo">
									<input  id="cargoCode" class="form-control" maxlength="11"/>
								</div>
							</div>
									<div class="col-md-3 col-md-offset-6">
										<div class="form-group ">
											<div class="col-md-8 col-md-offset-4 input-group-btn">
												<button type="button" class="btn btn-success" id="search">
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
					</div>
			</form>
			</div>
			<div class="btn-group buttons col-md-12">
				<button class="btn  btn-default btn-circle mar-r-10 btn-search" onclick="util.querySlideToggle()" type="button">
					<span class="fa  fa-search"></span><span class="text">搜索</span>
				</button>
				
				<div class="checkbox itemCtr" style="float: right; margin-right: 10px;">
											<label class="checkbox-inline"> <input
												type="checkbox" data='1' id="isShowAll" value="option2">显示统计信息
											</label>
											<button class="btn btn-default " id="refreshQuery"  data-placement="bottom"  data-content="点击实时刷新数据"  type="button">
				</button>
										</div>
				
			</div>
			 <div class="col-md-12">
			<div data-role="invoicequeryGrid"></div>
			</div>
		</div>
		</div>
	</div>


