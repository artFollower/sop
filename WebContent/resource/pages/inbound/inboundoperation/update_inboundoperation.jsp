<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
<style type="text/css">
.modal-dialog {
	margin: 30px auto;
	width: 65%;
} 
</style>
</head>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
	  <h4 class="modal-title">修改入库信息</h4>
			</div>
			<div class="modal-body" >
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal" style="padding:10px;">
									<div class="form-group">
									<div class="col-md-6">
									<label id="berthProgramId" class="hidden"></label>
									<label id="arrivalId" class="hidden"></label>
											<label id="tp1" class="col-md-4 control-label">预计到港:</label>
											<div class="col-md-8">
											<div class="input-group arrivalStartTime">
											         <div class="col-md-7" style="padding-right: 0px;"><input style="text-align:right;border-right:0;" id="arrivalStartTime1"  
											         class="form-control form-control-inline date-picker col-md-8"  type="text"/>
												 </div>
											         <div class="col-md-5" style="padding-left: 0px;"><input style="border-left:0;" type="text"  id="arrivalStartTime2" 
											         class="form-control col-md-4  timepicker timepicker-24"> </div>
												</div>
												</div>
												</div>
										</div>


									<div class="form-group notTransport">
										<div class="col-md-6">
											<label class="col-md-4 control-label">船英文名:</label>
											<div class="col-md-8">
												<label  class="control-label"  id="shipName"></label>
											</div>
										</div>
										<div class="col-md-6">
											<label class="col-md-4 control-label">船中文名:</label>
											<div class="col-md-8">
											<input class="form-control" maxlength="20"  id="shipRefName">
											</div>
										</div>
									</div>
                                     <div class="form-group notTransport">
										<div class="col-md-6">
											<label class="col-md-4 control-label">船代:</label>
											<div class="col-md-8">
												<input  class="form-control" maxlength="20" id="shipAgentId">
											</div>
										</div>
										<div class="col-md-6">
											<label class="col-md-4 control-label" style="padding-left: 0px;">到港吃水(米):</label>
											<div class="col-md-8">
												<input class="form-control" maxlength="10" data-type="Require" onkeyup="config.clearNoNum(this,3)"  id="shipArrivalDraught">
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="col-md-6 notTransport">
											<label class="col-md-4 control-label">泊位:</label>
											<div class="col-md-8">
												<Input  class="form-control" maxlength="10" id="berthId">
											</div>
										</div>
										<div class="col-md-6">
											<label id="tp2" class="col-md-4 control-label">申报状态:</label>
											<div    class="col-md-8">
												<select disabled class="form-control" id="report">
												<option id="tp3" value="0">未申报</option>
												<option id="tp4" value="1">已申报</option>
												</select>
											</div>
										</div>
										</div>
					</form>
					</div>
				</div>
			</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-success" id="save" >提交</button>
			</div>
		</div>
		</div>
	</div>
