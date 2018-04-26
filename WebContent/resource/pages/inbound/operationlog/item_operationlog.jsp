<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
<style type="text/css">
.grid-table-body table td {
	border-color: transparent #dddddd #dddddd transparent !important;
	border-width: 0 1px 1px 0 !important;
	vertical-align: top !important;
}
</style>
</head>
<div class="row">
	<div class="col-md-12">
		<div class="portlet tabbable">
			<div class="portlet-title ">
				<div class="caption">
					<i class=" fa fa-th"></i>调度日志
				</div>
			</div>
			<div class="portlet-body ">
				<div class="tabbable-custom">
					<ul class="nav nav-tabs">
						<li class="active"><a href="javascript:void(0)"
							onclick="ItemOperation.changetab(this,1)" data-toggle="tab"
							id="tab1">日志记录</a>
						</li>
						<li><a href="javascript:void(0)"
							onclick="ItemOperation.changetab(this,2)" data-toggle="tab"
							id="tab3">当班调度作业记录</a>
						</li>
						<li><a href="javascript:void(0)"
							onclick="ItemOperation.changetab(this,3)" data-toggle="tab"
							id="tab2">调度值班记录</a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="portlet_tab1">
							<!--日志记录  -->
							<form action="#" class="form-horizontal log-form">
								<table width="100%">
									<tr>
										<td colspan="2"><h4>基本信息</h4>
										</td>
									</tr>
									<tr>
										<td>
											<div class="form-group">
												<label class="control-label col-md-2">日期:</label>
												<div class="col-md-6">
													<label class="control-label datetime"></label>
												</div>
											</div></td>
									</tr>
									<tr>
										<td>
											<div class="form-group">
												<label class="control-label col-md-2">天气:</label>
												<div class="col-md-6">
													<input id="weather" maxlength="64" type="text" data-provide="typeahead"
														data-required="1" data-type="Require"
														class="form-control weather">
												</div>
											</div></td>
										<td>
											<div class="form-group">
												<label class="control-label col-md-2">风向:</label>
												<div class="col-md-6">
													<input id="windD" maxlength="64" type="text" data-provide="typeahead"
														data-required="1" data-type="Require"
														class="form-control windD">
												</div>
											</div></td>
									
									</tr>
									
									<tr>
										<td>
											<div class="form-group">
												<label class="control-label col-md-2">风力:</label>
												<div class="col-md-6">
													<input id="windP" maxlength="64" type="text" data-provide="typeahead"
														data-required="1" data-type="Require"
														class="form-control windP">
												</div>
											</div></td>
									<td>
											<div class="form-group">
												<label class="control-label col-md-2">温度:</label>
												<div class="col-md-6">
													<input id="temp" maxlength="64" type="text" data-provide="typeahead"
														data-required="1" data-type="Require"
														class="form-control temp">
												</div>
											</div></td>
									</tr>
									
									<tr>
									
									
									<td>
											<div class="form-group">
												<label class="control-label col-md-2">调度:</label>
												<!-- <div class="col-md-6">
													<input id="dispatch" type="text" data-provide="typeahead"
														data-required="1" data-type="Require"
														class="form-control dispatch">
												</div>
												 -->
												 <div class="col-md-5">
												  <div class="select2-container select2-container-multi form-control select2" style="max-width: 200px" id="dispatch1"></div>
												 </div>
												 
												 <div class="col-md-5">
												  <div class="select2-container select2-container-multi form-control select2" style="max-width: 200px" id="dispatch2"></div>
												 </div>
												 
											</div></td>
										<td>
											<div class="form-group">
												<label class="control-label col-md-2">发货班:</label>
												<!--<div class="col-md-6">
													<input id="delivery" type="text" data-provide="typeahead"
														data-required="1" data-type="Require"
														class="form-control delivery">
												</div>
												-->
												
												<div class="col-md-5">
												  <div class="select2-container select2-container-multi form-control select2" style="max-width: 200px" id="delivery1"></div>
												 </div>
												 
												 <div class="col-md-5">
												  <div class="select2-container select2-container-multi form-control select2" style="max-width: 200px" id="delivery2"></div>
												 </div>
												
											</div></td>
									
									
										
										
									</tr>
									<tr>
										
										<td>
											<div class="form-group">
												<label class="control-label col-md-2">操作日班:</label>
												<!-- <div class="col-md-6">
													<input id="dayWord" type="text" data-provide="typeahead"
														data-required="1" data-type="Require"
														class="form-control dayWord">
												</div> -->
												
												<div class="col-md-5">
												  <div class="select2-container select2-container-multi form-control select2" style="max-width: 200px" id="dayWord1"></div>
												 </div>
												 
												 <div class="col-md-5" style="display: none">
												  <div class="select2-container select2-container-multi form-control select2" style="max-width: 200px" id="dayWord2"></div>
												 </div>
												
											</div></td>
										
											
											<td>
											<div class="form-group">
												<label class="control-label col-md-2">码头值班:</label>
												<!-- <div class="col-md-6">
													<input id="dock" type="text" data-provide="typeahead"
														data-required="1" data-type="Require"
														class="form-control dock">
												</div> -->
												
												<div class="col-md-5">
												  <div class="select2-container select2-container-multi form-control select2" style="max-width: 200px" id="dock1"></div>
												 </div>
												 
												 <div class="col-md-5">
												  <div class="select2-container select2-container-multi form-control select2" style="max-width: 200px" id="dock2"></div>
												 </div>
												
												
											</div></td>
										
											
									</tr>
									
									<tr>
									<td>
											<div class="form-group">
												 <label class="control-label col-md-2">动力班:</label>
												<!--<div class="col-md-6">
													<input id="power" type="text" data-provide="typeahead"
														data-required="1" data-type="Require"
														class="form-control power">
												</div>
												 -->
												 
												 <div class="col-md-5">
												  <div class="select2-container select2-container-multi form-control select2" style="max-width: 200px" id="power1"></div>
												 </div>
												 
												 <div class="col-md-5">
												  <div class="select2-container select2-container-multi form-control select2" style="max-width: 200px" id="power2"></div>
												 </div>
												 
											</div></td>
											
											
											
									</tr>
									
								</table>
							</form>
							<div class="modal-footer">
								<div class="col-md-offset-3 col-md-9">
									<input type="hidden" class="form-control inputId"
										name="inputId" /> <input type="hidden"
										class="form-control arrivalId" name="arrivalId" />
									<shiro:hasPermission name="ADISPATCHLOGUPDATE">
										<button type="button" class="btn btn-primary saveButton">保存</button>
									</shiro:hasPermission>
								</div>
							</div>
							<div class="btn-group buttons" style="display: none">
								<button class="btn btn-default btn-circle mar-r-10 btn-modify" type="button">
									<span class="fa fa-edit"></span><span class="pad-l-5">修改</span>
								</button>
						</div>
							<div class="loggrid" style="width:100%" data-role="logGrid"></div>
						</div>
						<div class="tab-pane" id="portlet_tab2">
							<!-- 调度值班记录  -->

							<div class="row">
								<div class="col-md-12">
									<!-- BEGIN EXAMPLE TABLE PORTLET-->
										<h4>配管记录</h4>
									<div class="portlet-body">
										<!-- <div data-role="logmsgGrid" style="width:100%"></div> -->


									<table class="table table-striped table-hover table-bordered" id="logmsgTable">
					<thead>
						<tr>
							<th style="text-align: center">内容</th>
							<th style="text-align: center;width:90px;">记录人</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>



									</div>
									<h4>其它记录</h4>
									<div class="portlet-body">
										<!-- <div data-role="logOhtermsgGrid" style="width:100%"></div> -->
										<table class="table table-striped table-hover table-bordered" id="logOthermsgTable">
					<thead>
						<tr>
						<th style="text-align: center;width:90px;">通知单</th>
						<th style="text-align: center;width:160px;">作业要求</th>
							<th style="text-align: center">内容</th>
							<th style="text-align: center;width:90px;">记录人</th>
							<th style="text-align: center;width:90px;">开泵时间</th>
							<th style="text-align: center;width:90px;">停泵时间</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
									</div>
									<h4>备注</h4>
									<div class="portlet-body">
										<textarea class="form-control description" rows="3" name="description"></textarea>
									</div>
									<div class="modal-footer">
								<div class="col-md-offset-3 col-md-9">
									<shiro:hasPermission name="ADISPATCHLOGUPDATE">
										<button type="button" class="btn btn-primary saveDescription">保存</button>
									</shiro:hasPermission>
								</div>
							</div>
									<!-- END EXAMPLE TABLE PORTLET-->
								</div>
							</div>
						</div>
						<div class="tab-pane" id="portlet_tab3">
							<!-- 当班调度作业记录 -->
							<div class="portlet-body form">
								<!-- BEGIN FORM-->
								<h4>过程记录</h4>
								<div class="btn-group buttons">
									<shiro:hasPermission name="ADUTYADD">
										<button id="sample_editable_1_new"
											class="btn btn-default btn-circle mar-r-10 pluswork"
											type="button">
											<span class="fa fa-plus"></span><span class="text">添加</span>
										</button>
									</shiro:hasPermission>
								</div>
								<div data-role="logworkGrid" style="width:100%"></div>
								
								<h4>天气预报</h4>
								<div class="btn-group buttons">
									<shiro:hasPermission name="ADUTYADD">
										<button id="addWeather"
											class="btn btn-default btn-circle mar-r-10 addWeather"
											type="button">
											<span class="fa fa-plus"></span><span class="text">添加</span>
										</button>
									</shiro:hasPermission>
								</div>
								<div data-role="weatherGrid" style="width:100%"></div>
								
								
								<h4>系统检查(早班)</h4>
								<div data-role="sysGrid" style="width:100%"></div>
								
								
								
								<h4>系统参数检查(早班)</h4>
								<div data-role="sysParGrid" style="width:100%"></div>
								
								
								<h4>系统检查(晚班)</h4>
								<div data-role="sysGridw" style="width:100%"></div>
								
								
								
								<h4>系统参数检查(晚班)</h4>
								<div data-role="sysParGridw" style="width:100%"></div>
								
								
								
								
								
								<!--  <div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"  onclick="window.location='#/itemoperationlog'">返回</button>
				<button type="button" class="btn btn-success" id="save">提交</button>
			</div>-->
								<!-- END FORM-->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>