<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="btn-group buttons" style="width: 100%;">
				<button id="btn" class="btn btn-primary" style="margin-left:4px" onclick="javascript:window.location='#/notify?item=17' ;">
					<i class="icon-edit"></i>车发换罐作业通知单
				</button>
			<!-- 	<button class="btn btn-default btn-circle mar-r-10 re_move" id="change"
					 style="display: block;float: right;">切换到流量计量</button> -->
			</div>
	<input type="hidden" name="status">
	<input type="hidden" name="approveId">
	
	<div class="portlet box blue" style="border-color: #777;margin-top: 6px;" >
		<div class="portlet-title " style="background-color: #777;">
			<div class="caption serialType">
				<i class="icon-reorder"></i>称重单
			</div>
		</div>
		<div class="portlet-body ">
		<div class="form-body ">
		<div class="form-group serialDiv">
		<div class="col-md-4">
		<label class="control-label col-md-4" style="text-align:right;padding-top:10px;">通知单号</label>
		 <div class="col-md-8 input-group">
                        <select class="form-control serialIn" type="text" ></select>
                        
		</div>
		</div>
		</div>
		</div>
		<h4 class="form-section"></h4>
		<table id="weigh_div" class="table " >
				<thead align=center>
					<tr>
						<td colspan=2>类型</td>
						<td>皮 重 (吨)</td>
						<td>毛 重 (吨)</td>
						<td>净 重 (吨)</td>
					</tr>
				</thead>
				<tbody align=center id="vehicleWeigh">
				</tbody>
			</table>
			<table id="measure_div" class="table "  style="display:none;" >
			<thead align=center></thead>
				<tbody align=center id="vehicleMeasure">
				   
				</tbody>
			</table>
		</div>
	</div>

	 	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal" onclick="window.location ='#/outboundtruckserial/list' ">返回</button>
		<shiro:hasPermission name="ACARDELIVERYWORKUPDATE"><button type="button" class="btn btn-primary re_move h" id="submitTruckReady" >提交</button></shiro:hasPermission>
		</div>
