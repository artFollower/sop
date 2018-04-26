<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="input-group col-md-12">
	<label class="col-md-2 control-label" style="text-align: right;">日期:</label>
	<input style="text-align: center; border: 1px solid #ccc;"
		id="createTime" class="date-picker col-md-3" type="text" /> <label
		class="col-md-2 col-md-offset-2 control-label"
		style="text-align: right;">编号:</label> <label
		class="col-md-3 control-label" key="0" style="text-align: left"
		id="code"></label>
</div>
<div class="portlet box blue">
	<div class="portlet-title">
		<div class="tools">
			<shiro:hasPermission name="ANOTICEEXCHANGEPOTEXEL">
				<a class="hidden-print" style="color: white;"
					onclick="notify.exportXML(this,31)"> <i
					class="fa fa-file-excel-o">&nbsp;导出</i>
				</a>
			</shiro:hasPermission>
		</div>
	</div>
	<div class="portlet-body">
		<!-- BEGIN FORM-->
		<form action="#" class="form-horizontal form-bordered">
			<div class="form-body">
				<table width="100%">
					<tr>
						<td colspan="2"><h5>&nbsp;一.作业任务</h5></td>
					</tr>
					<tr>
						<td class="col-md-12">
							<div class="col-md-12">
								<textarea class="form-control description" rows="1" id="taskMsg"></textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><h5>&nbsp;二.作业工艺流程</h5></td>
					</tr>
					<tr>
						<td>
							<div class="col-md-7">
								<div id="contentDiv" class="content"
									style="border: 1px solid #d8d8d8; border-radius: 3px; float: left; height: 200px; width: 100%;">
									<!-- Img DIV-->
									<div id="toolbarContainer"
										style="overflow: hidden; width: 100px; height: 20px; margin-top: 0px; z-index: 999; border: 1px solid #d8d8d8; border-radius: 3px;"></div>
									<div id="graphContainer"
										style="overflow: hidden; width: 100%; height: 200px; margin-top: 0px;"></div>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div class="col-md-12 surecheck"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><shiro:hasPermission name="ANOTICEDISPATCH">
								<div class="modal-footer isModify sureDiv">
									<div class="col-md-8 form-horizontal isNoModify">
										<div class="form-group">
											<label class="col-md-4 control-label">调度<span
												class="required">*</span></label>
											<div class="col-md-4">
												<input type="text" id="sureTaskUserId" disabled="disabled"
													class="form-control" data-required="1" /> <input
													type="hidden" name="transportId">
											</div>
										</div>
									</div>
									<button type="button" class="btn btn-success" key="0"
										status="0" id="saveSureTask" data-required="1">保存</button>
									<button type="button" class="btn btn-primary isNoModify"
										key="0" status="1" id="submitSureTask" data-required="1">确认</button>
								</div>
							</shiro:hasPermission></td>
					</tr>
					<tr>
						<td colspan="2"><h5>
								&nbsp;三.作业注意事项&nbsp;<a href="javascript:void(0)"
									onclick="InboundOperation.openWarning(1);"><i
									class="fa fa-question-circle"></i></a>
							</h5></td>
					</tr>
					<tr>
						<td colspan="2" class="dialog-warning1"><h6>
								<p>&nbsp;&nbsp;1.正确佩戴防护用品</p>
								<p>&nbsp;&nbsp;2.第一车注意可能有气，适当少发</p>
								<p>&nbsp;&nbsp;3.关注泵运行情况</p>
								<p>&nbsp;&nbsp;4.如油品车发换罐，应检查是否有水</p>
								<p>&nbsp;&nbsp;5.管线内有气，防止发货时鹤管跳动</p>
							</h6></td>
					</tr>
				</table>
			</div>
		</form>
		<!-- END FORM-->

		<div class="portlet box white notice-wn">
			<div class="portlet-title">
				<div class="caption" style="font-size: 13px; color: #333333">
					四.作业中的风险分析&nbsp;<a href="javascript:void(0)"
						onclick="InboundOperation.openWarning(2);"><i
						class="fa fa-question-circle"></i></a>
				</div>
			</div>
			<div class="portlet-body form dialog-warning2">
				<form action="#" class="form-horizontal">
					<div class="form-body">
						<table width="100%"
							class="table table-condensed table-striped table-bordered notice-table">
							<tr>
								<td class="col-md-4"><h6 style="text-align: center">危险和潜在的事件</h6></td>
								<td class="col-md-4"><h6 style="text-align: center">主要后果</h6></td>
								<td class="col-md-4"><h6 style="text-align: center">安全措施</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">1、未按规定佩戴劳动防护用品</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;人身伤害</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确佩戴个人防护用品</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">2、进出口阀门开错</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;杂质、水带出影响品质</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;根据储罐阀门标识开进口阀发货</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">3、吹扫口阀未关严造成物料泄漏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境、危害健康</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;关闭阀门，做好检查</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">4、闷盖未封好造成物料泄漏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境、危害健康</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;封堵好闷盖，做好检查</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">5、阀门丝杆处物料泄漏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境、危害健康</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;加强检查、及时维修</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">6、管线腐蚀造成物料泄漏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境、危害健康</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;加强检查、及时维修</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">7、开错储罐</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;发错物料</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;根据提单罐号加强核对</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">8、呼吸阀故障造成胀罐</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;造成储罐损坏
									</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;做好维护、加强作业时的检查</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">9、装车系统故障</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;引发事故</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;做好维护、加强作业时的检查</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">10、发货量超过车辆装载量</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;造成溢料</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;加强核对、正确安放防溢开关</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">11、泵运行时发生故障</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;引发事故</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;做好保养、加强巡回检查</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">12、未打开泵回流阀</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;在不作业时管线憋压</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;打开回流阀、加强巡回检查</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">13、泵放气时物料喷溅</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境、危害健康</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;按规程操作，及时盛接</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left"></h6>14、鹤管锁紧装置未锁死</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;鹤管抖动造成物料喷溅</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;锁紧鹤管加强观察</h6></td>
							</tr>
						</table>
					</div>
				</form>
			</div>
			<div class="modal-footer openNextDiv">
				<button type="button" class="btn btn-primary openNext">确认</button>
			</div>
		</div>
		<!--  -->
		<div class="portlet box white notice-check" hidden="true">
			<div class="portlet-title">
				<div class="caption" style="font-size: 13px; color: #333333">五.作业过程的检查</div>
			</div>
			<div class="portlet-body">
				<div class="form-body">
					<table width="100%" class="beforeTable">
						<tr>
							<td colspan="2"><h6>
									<strong>&nbsp;作业前检查</strong><a href="javascript:void(0)"
										name="before" id="beforeCheckAll"><i
										class="fa fa-square-o"></i></a>
								</h6></td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>1.个人防护用品是否正确佩戴</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="b" id="b1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="b" id="b2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>2、工艺流程是否正确</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="c" id="c1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="c" id="c2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>3、管线、阀门是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="d" id="d1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="d" id="d2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>4、装车系统是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="e" id="e1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="e" id="e2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>5、是否无跑冒滴漏</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="f" id="f1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="f" id="f2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="col-md-12 beforecheck"></div>
							</td>
						</tr>
					</table>
					<div class="modal-footer isModify beforeDiv">
						<shiro:hasPermission name="ANOTICEDOEXCHANGEPOT">
							<div class="col-md-8 form-horizontal isNoModify">
								<div class="form-group">
									<label class="col-md-4 control-label">作业人员：<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" id="beforeTaskUserId" disabled="disabled"
											class="form-control " data-required="1" />
									</div>
								</div>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDISPATCHANDEXCHANGEPOT">
							<button type="button" class="btn btn-success" key="1" status="0"
								id="saveBeforeTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOEXCHANGEPOT">
							<button type="button" class="btn btn-primary isNoModify" key="1"
								status="1" id="submitBeforeTask">确认</button>
						</shiro:hasPermission>
					</div>
				</div>
				<div class="form-body">
										<table width="100%" class="inTable">
											<tr>
												<td colspan="2"><h6>
														<strong>&nbsp;作业中检查</strong>&nbsp;<a
															href="javascript:void(0)" name="in" id="inCheckAll"><i
															class="fa fa-square-o"></i></a>
													</h6></td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>1、中控室SCADA系统液位是否正常下降</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list spCB">
															<label class="checkbox-inline"> <input
																type="checkbox" name="g" id="g1" value="option1">
																是
															</label> <label class="checkbox-inline"> <input
																type="checkbox" name="g" id="g2" value="option2">
																否
															</label>
															<div class="gDiv" style="float: right;">
															<shiro:hasPermission name="ANOTICEDISPATCHANDEXCHANGEPOT">
																<button style="padding: 3px 8px;" type="button" key="2"
																	class="btn btn-primary isModify spBtn" id="gSureBtn">确认</button>
																	</shiro:hasPermission>
															</div>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>2、放气时是否有物料溢出并盛接</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list ">
															<label class="checkbox-inline"> <input
																type="checkbox" name="h" id="h1" value="option1">
																是
															</label> <label class="checkbox-inline"> <input
																type="checkbox" name="h" id="h2" value="option2">
																否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>3、泵运行是否正常</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input
																type="checkbox" name="i" id="i1" value="option1">
																是
															</label> <label class="checkbox-inline"> <input
																type="checkbox" name="i" id="i2" value="option2">
																否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>4、装车系统是否正常</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input
																type="checkbox" name="j" id="j1" value="option1">
																是
															</label> <label class="checkbox-inline"> <input
																type="checkbox" name="j" id="j2" value="option2">
																否
															</label> 
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>5、油气回收是否正常</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input
																type="checkbox" name="k" id="k1" value="option1">
																是
															</label> <label class="checkbox-inline"> <input
																type="checkbox" name="k" id="k2" value="option2">
																否
															</label> <label class="checkbox-inline"> <input
																type="checkbox" name="k" id="k3" value="option3">
																不适用
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<div class="col-md-12 incheck"></div>
												</td>
											</tr>
										</table>
										<div class="modal-footer inDiv isModify">
										<shiro:hasPermission name="ANOTICEDOEXCHANGEPOT">
											<div class="col-md-8 form-horizontal isNoModify">
												<div class="form-group">
													<label class="col-md-4 control-label">工作人员：<span
														class="required">*</span></label>
													<div class="col-md-4">
														<input type="text" id="inTaskUserId" readonly
															class="form-control" data-required="1" />
													</div>
												</div>
											</div>
											</shiro:hasPermission>
											<shiro:hasPermission name="ANOTICEDISPATCHANDEXCHANGEPOT">
											<button type="button" class="btn btn-success" key="2"
												status="0" id="saveInTask">保存</button>
												</shiro:hasPermission>
												<shiro:hasPermission name="ANOTICEDOEXCHANGEPOT">
											<button type="button" class="btn btn-primary isNoModify" key="2"
												status="1" id="submitInTask">确认</button>
												</shiro:hasPermission>
										</div>
									</div>
				<div class="form-body">
					<table width="100%" class="afterTable">
						<tr>
							<td colspan="2"><h6>
									<strong>&nbsp;作业后检查</strong><a href="javascript:void(0)"
										name="after" id="afterCheckAll"><i class="fa fa-square-o"></i> </a>
								</h6></td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>1、第一车是否有水</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="l" id="l1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="l" id="l2" value="option2"> 否
										</label><label class="checkbox-inline"> <input
											type="checkbox" name="l" id="l3" value="option3">
											不适用
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>2、是否没有存在超发现象</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="m" id="m1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="m" id="m2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>3、鹤管、梯子是否收起</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="n" id="n1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="n" id="n2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>4、垫木、禁止启动牌、静电夹是否归位</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="o" id="o1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="o" id="o2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>5、记录是否完善</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="p" id="p1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="p" id="p2" value="option2"> 否
										</label> <label class="checkbox-inline"><input type="checkbox"
											name="p" id="p3" value="option3">不适用</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="col-md-12 aftercheck"></div>
							</td>
						</tr>
					</table>
					<div class="modal-footer isModify afterDiv">
						<shiro:hasPermission name="ANOTICEDOEXCHANGEPOT">
							<div class="col-md-8 form-horizontal isNoModify">
								<div class="form-group">
									<label class="col-md-4 control-label">作业人员：<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" id="afterTaskUserId" disabled="disabled"
											class="form-control" data-required="1" />
									</div>
								</div>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDISPATCHANDEXCHANGEPOT">
							<button type="button" class="btn btn-success" key="3" status="0"
								id="saveAfterTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOEXCHANGEPOT">
							<button type="button" class="btn btn-primary isNoModify" key="3"
								status="1" id="submitAfterTask">确认</button>
						</shiro:hasPermission>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal-footer">
	<label key='0' class="modifyNotice hidden"></label>
	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	<shiro:hasPermission name="ANOTICEDISPATCH">
		<button type="button" class="btn btn-primary" id="reset">重置</button>
		<button type="button" class="btn btn-primary" id="modifyNotice">修改</button>
	</shiro:hasPermission>
</div>