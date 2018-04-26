<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/resource/global/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js" type="text/javascript"></script>
<!-- BEGIN MAIN CONTENT -->
<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-ship"></i>添加港务信息
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="portlet box " id="form_wizard"><!-- green-meadow -->
						<div class="portlet-title hidden">
							<div class="caption">
								<i class="fa fa-gift"></i> 港务信息添加 - <span class="step-title"> (1/4) </span>
							</div>
						</div>
						<div class="portlet-body form">
							<form class="form-horizontal" id="submit_form" method="POST">
								<div class="form-wizard">
									<div class="form-body">
										<ul class="nav nav-pills nav-justified steps">
											<li><a href="#tab1" data-toggle="tab" class="step"> <span class="number"> 1 </span> <span class="desc"> <i class="fa fa-check"></i>报文头信息
												</span>
											</a></li>
											<li><a href="#tab2" data-toggle="tab" class="step"> <span class="number"> 2 </span> <span class="desc"> <i class="fa fa-check"></i>船舶信息
												</span>
											</a></li>
											<li><a href="#tab3" data-toggle="tab" class="step active"> <span class="number"> 3 </span> <span class="desc"> <i class="fa fa-check"></i>货物信息
												</span>
											</a></li>
										</ul>
										<div id="bar" class="progress progress-striped" role="progressbar">
											<div class="progress-bar progress-bar-success"></div>
										</div>
										<div class="tab-content">
											<div class="alert alert-danger display-none">
												<button class="close" data-dismiss="alert"></button>
												You have some form errors. Please check below.
											</div>
											<div class="alert alert-success display-none">
												<button class="close" data-dismiss="alert"></button>
												Your form validation is successful!
											</div>
											<div class="tab-pane active form-body" id="tab1">
												<h4 class="form-section">报文信息</h4>

												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">报文类型<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" name="messageType" readonly="readonly" value="COARRI"/>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">文件说明<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<select  name="fileDescription" class="form-control">
														          	 <option value="DISCHARGE REPORT">卸货</option>
														             <option value="LOAD REPORT">装货</option>
													         	</select>
															</div>
														</div>
													</div>
												</div>

												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">发送方<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control"  readonly="readonly" value="太仓长江石化"/>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">接收方<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<!-- <input type="text" name="receiverCode" class="form-control" data-required="1"  data-type="Require"/> -->
																<input type="text" class="form-control" readonly="readonly" value="信息中心"/>
															</div>
														</div>
													</div>
												</div>

												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">发送港代码<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" name="sendPortCode" readonly="readonly" value="CNTAI"/>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">接收港代码<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<input type="text" name="receiverPortCode" class="form-control" readonly="readonly" value="CNTAI"/>
															</div>
														</div>
													</div>
												</div>

												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">文件功能<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<!-- <input type="text" class="form-control" name="fileFunction" /> -->
																<input type="text" class="form-control" readonly="readonly" value="原始"/>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">文件建立时间
															</label>
															<div class="col-md-9">
																<div class="input-group date datetime">
																	<input type="text" size="16" readonly class="form-control" name="fileCreateTime" data-data="new Date()">
																	<span class="input-group-btn">
																	<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
																	</span>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="tab-pane form-body" id="tab2">
												<h4 class="form-section">船舶信息</h4>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">英文船名<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<div class="input-group">
																	<input type="text" id="vesselCode" maxlength="13" class="form-control" name="vesselCode" data-required="1"  data-type="Require"/>
																	<span class="input-group-btn">
																		<button class="btn default ship_select" type="button"><i class="fa fa-ship"></i></button>
																	</span>
																</div>
																<!-- <input type="text" id="vesselCode"  data-provide="typeahead" class="form-control" name="vesselCode" data-required="1"  data-type="Require"/> -->
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">中文船名<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<input type="text" name="vessel" maxlength="35" class="form-control" data-required="1"  data-type="Require"/>
															</div>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">船舶国籍<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" maxlength="2" name="nationality" data-required="1"  data-type="Require"/>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">航次号<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" maxlength="6" name="voyage"  data-required="1"  data-type="Require"/>
															</div>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">船舶识别号<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" maxlength="15" name="shipUniqueNum" data-required="1"  data-type="Require"/>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">泊位代码<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control" maxlength="10" name="port" data-required="1"  data-type="Require"/>
															</div>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">班轮<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<select  name="liner" class="form-control" readonly="readonly">
														             <option value="N" selected="selected">非班轮</option>
													         	</select>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">预报/确报<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<select  name="confirgFlag" class="form-control" readonly="readonly">
														             <option value="1">确报</option>
													         	</select>
															</div>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">到港时间<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<div class="input-group date datetime">
																	<input type="text" size="16" readonly class="form-control" name="arrivlTime" data-required="1"  data-type="Require">
																	<span class="input-group-btn">
																	<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
																	</span>
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">离港时间<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<div class="input-group date datetime">
																	<input type="text" size="16" readonly class="form-control" name="sailingTime" data-required="1"  data-type="Require">
																	<span class="input-group-btn">
																	<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
																	</span>
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">卸船开始时间<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<div class="input-group date datetime">
																	<input type="text" size="16" readonly class="form-control" name="dischStartTime" data-required="1"  data-type="Require">
																	<span class="input-group-btn">
																	<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
																	</span>
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">卸船结束时间<span class="required"> * </span></label>
															<div class="col-md-9">
																<div class="input-group date datetime">
																	<input type="text" size="16" readonly class="form-control" name="dicshEndTime" data-required="1"  data-type="Require">
																	<span class="input-group-btn">
																	<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
																	</span>
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">装船开始时间<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<div class="input-group date datetime">
																	<input type="text" size="16" readonly class="form-control" name="loadStartTime" data-required="1"  data-type="Require">
																	<span class="input-group-btn">
																	<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
																	</span>
																</div>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">装船结束时间<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<div class="input-group date datetime">
																	<input type="text" size="16" readonly class="form-control" name="loadEndTime" data-required="1"  data-type="Require">
																	<span class="input-group-btn">
																	<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
																	</span>
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">进港吃水(米)<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<input type="text" maxlength="5" class="form-control number" name="befterDraft" data-required="1"  data-type="Require"/>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">离港吃水(米)<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<input type="text" maxlength="5" class="form-control number" name="afterDraft" data-required="1"  data-type="Require"/>
															</div>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">实载客量
															</label>
															<div class="col-md-9">
																<input type="text" maxlength="5" class="form-control number" name="realCapacity"/>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">本港上下客
															</label>
															<div class="col-md-9">
																<input type="text" maxlength="5" class="form-control number" name="portPassenger"/>
															</div>
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">装卸货物数量(吨)<span class="required"> * </span>
															</label>
															<div class="col-md-9">
																<input type="text" class="form-control number" maxlength="5" name="netWeight" data-required="1"  data-type="Require"/>
															</div>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="control-label col-md-3">货物描述
															</label>
															<div class="col-md-9">
																<textarea maxlength="100" class="form-control" name="cargoDes"></textarea>
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="tab-pane" id="tab3">
												<h4 class="form-section">货物信息</h4>
												<div class="btn-group buttons">
													<button class="btn btn-primary btn-add" type="button">
														<span class="glyphicon glyphicon-plus"></span>添加
													</button>
												</div>
												<div data-role="cargoGrid"></div>
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<div class="col-md-offset-3 col-md-9">
										<a href="javascript:;" class="btn default button-previous"> <i class="m-icon-swapleft"></i> 前一步
										</a> <a href="javascript:;" class="btn blue button-next"> 下一步 <i class="m-icon-swapright m-icon-white"></i>
										</a> <a href="javascript:;" class="btn green button-submit"> 提交 <i class="m-icon-swapright m-icon-white"></i>
										</a>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>

		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->
