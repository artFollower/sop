<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="input-group col-md-12">
	<label class="col-md-2 control-label" style="text-align: right;">日期:</label>
	<input style="text-align: center; border: 1px solid #ccc"
		id="createTime" class="date-picker col-md-3" type="text" /> <label
		class="col-md-2 col-md-offset-2 control-label"
		style="text-align: right;">编号:</label> <label
		class="col-md-3 control-label" key="0" style="text-align: left"
		id="code"></label>
</div>
<div class="portlet box blue">
	<div class="portlet-title">
		<div class="tools">
			<shiro:hasPermission name="ANOTICEBENZOLHOTEXEL">
				<a class="hidden-print" style="color: white;"
					onclick="notify.exportXML(this,16)"> <i
					class="fa fa-file-excel-o">&nbsp;导出</i>
				</a>
			</shiro:hasPermission>
		</div>
		<!-- <div class="tools">
							<a class="hidden-print" style="color: white;" onclick="javascript:window.print();"> <i class="fa fa-print">&nbsp;打印</i>
							</a>
						</div> -->
	</div>

	<div class="portlet-body">
		<!-- BEGIN FORM-->
		<form action="#" class="form-horizontal form-bordered">
			<div class="form-body">
				<table width="100%">
					<tr>
						<td colspan="2"><h5>&nbsp;一、作业任务:</h5></td>
					</tr>
					<tr>
						<td class="col-md-12">
							<div class="col-md-12"
								style="padding-left: 0px; padding-right: 0px;">
								<textarea class="form-control" rows="3" maxlength="150"
									id="taskMsg"></textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><h5>&nbsp;二、工艺要求:</h5></td>
					</tr>
					<tr>
						<td class="col-md-12">
							<div class="form-body">
								<table width="100%"
									class="table table-condensed table-striped table-bordered notice-table">
									<tr>
										<td class="col-md-6">
											<div class="form-group"
												style="margin-bottom: 15px; margin-top: 15px;">
												<label class="control-label col-md-4">罐号：</label>
												<div class="col-md-4">
													<input id="tankId" maxlength="16" class="form-control">
												</div>
												<div class="checkbox-list col-md-4"
													style="border-left-width: 30px; padding-left: 45px;">
													<label class="checkbox-inline"> <input id="tankCB"
														type="checkbox" value="option1" name="a"> 不适用
													</label>
												</div>
											</div>
										</td>
										<td class="col-md-6">
											<div class="form-group"
												style="margin-bottom: 15px; margin-top: 15px;">
												<label class="control-label col-md-4">管线：</label>
												<div class="col-md-4">
													<input id="tubeId" maxlength="25" class="form-control">
												</div>
												<div class="checkbox-list col-md-4"
													style="padding-left: 45px;">
													<label class="checkbox-inline"> <input id="tubeCB"
														type="checkbox" value="option1" name="a"> 不适用
													</label>
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td class="col-md-6">
											<div class="form-group"
												style="margin-bottom: 15px; margin-top: 15px;">
												<label class="control-label col-md-4">设定温度：</label>
												<div class="col-md-4">
													<input onkeyup="config.clearNoNum(this)" maxlength="16"
														id="tankT" class="form-control">
												</div>
												<label class="control-label col-md-4"
													style="text-align: left; padding-left: 0px;">℃</label>
											</div>
										</td>
										<td class="col-md-6">
											<div class="form-group"
												style="margin-bottom: 15px; margin-top: 15px;">
													<div class="col-md-5" style="padding-left: 180px;">
													  <input id="ctrlNum"  class="form-control">  
												</div>
												<div class="checkbox-list col-md-3"
													style="padding-left: 0px;">
													<label class="checkbox-inline">#号加热控制箱
													</label>
												</div>
												<div class="checkbox-list col-md-4"
													style="padding-left: 45px;">
													<label class="checkbox-inline"> <input id="tubeCBA"
														type="checkbox" name="a" value="option1"> 不适用
													</label>
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td class="col-md-6">
											<div class="form-group"
												style="margin-bottom: 15px; margin-top: 15px;">
												<label class="control-label col-md-2" >加热起始：</label> <label
													class="control-label col-md-2"
													style="padding-left: 0px; padding-right: 0px;">储罐温度:</label>
												<div class="col-md-3">
													<input class="form-control" maxlength="4"
														onkeyup="config.clearNoNum(this,2)" id="tankStartT">
												</div>
												<div class="col-md-4"
													style="padding-left: 0px; padding-right: 0px;">
													<label class="control-label col-md-2"
														style="text-align: left; padding-left: 0px; padding-right: 15px;">℃</label>
													<div class="checkbox-list col-md-10"
														style="padding-left: 30px;">
														<label class="checkbox-inline"
															style="border-left-width: 6px;"> <input
															id="tankStartTCB" type="checkbox" name="a"
															value="option1"> 不适用
														</label>
													</div>
												</div>
											</div>
										</td>
										<td class="col-md-6" style="width: 156px;">
											<div class="form-group" style="margin-bottom: 15px; margin-top: 15px;">
												<label class="control-label col-md-3" style="padding-right: 0px; padding-left: 0px;">管线温度：</label>
												<div class="col-md-2" style="padding-left: 0px; padding-right: 0px;">
													<input class="form-control" maxlength="4" onkeyup="config.clearNoNum(this,2)" id="tubeStartT">
												</div>
												<label class="control-label col-md-1" style="text-align: left; padding-left: 8px; padding-right: 0px;">℃</label>
													<div class="checkbox-list col-md-6"
														style="padding-left: 30px;">
														<label class="checkbox-inline"
															style="border-left-width: 6px;"> <input
															id="tubeStartTCB" type="checkbox"  
															value="option1"> 不适用
														</label>
													</div>
											</div>
										</td>
									</tr>
									<tr>
										<td class="col-md-6">
											<div class="form-group" style="margin-bottom: 15px; margin-top: 15px;">
												<label class="control-label col-md-2">加热时间：</label>
												<div class="col-md-8 input-group" style="padding-left: 0px;">
														<div class="input-group" id="startTime" >
															<div class="col-md-7" style="padding-right: 0px;">
																<input style="text-align: right; border-right: 0;"  onchange="notify.getDifferTime()"
																	class="form-control form-control-inline date-picker col-md-8"
																	type="text" data-required="1" />
															</div>
															<div class="col-md-5" style="padding-left: 0px; padding-right: 0px;">
																<input style="border-left: 0;" type="text"  onchange="notify.getDifferTime()"
																	class="form-control col-md-4  timepicker timepicker-24">
															</div>
														</div>
														<span class="input-group-addon">到</span>
														<div class="input-group" id="endTime">
															<div class="col-md-7" style="padding: 0px;">
																<input style="text-align: right; border-right: 0;"  onchange="notify.getDifferTime()"
																	class="form-control form-control-inline date-picker col-md-8"
																	type="text" data-required="1" />
															</div>
															<div class="col-md-5"
																style="padding-left: 0px; padding-right: 0px;">
																<input style="border-left: 0;" type="text"  onchange="notify.getDifferTime()"
																	class="form-control col-md-4  timepicker timepicker-24">
															</div>
														</div>
												</div>
											</div>
										</td>
										<td class="col-md-6">
											<div class="form-group" style="margin-bottom: 15px; margin-top: 15px;">
												<label class="control-label col-md-2" style="text-align:right;">共计</label>
												<label class="control-label col-md-1" id="hourTime"></label>
												<label class="control-label col-md-2" style="text-align:left">小时</label>
											</div>
										</td>
									</tr>
								</table>
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
									<div class="col-md-8 isNoModify form-horizontal">
										<div class="form-group">
											<label class="col-md-4 control-label">调度<span
												class="required">*</span>
											</label>
											<div class="col-md-4">
												<input type="text" id="sureTaskUserId" readonly
													class="form-control" data-required="1" />
											</div>
										</div>
									</div>
									<button type="button" class="btn btn-success" key="0"
										status="0" id="saveSureTask">保存</button>
									<button type="button" class="btn isNoModify btn-primary"
										key="0" status="1" id="submitSureTask">确认</button>
								</div>
							</shiro:hasPermission></td>
					</tr>
					<tr>
						<td colspan="2"><h5>
								&nbsp;三、作业注意事项:&nbsp;<a href="javascript:void(0)"
									onclick="InboundOperation.openWarning(1);"><i
									class="fa fa-question-circle"></i> </a>
							</h5></td>
					</tr>
					<tr>
						<td colspan="2" class="dialog-warning1"><h6>
								<p>&nbsp;&nbsp;1、正确佩戴个人防护用品;</p>
								<p>&nbsp;&nbsp;2、加热时阀门应正确切换;</p>
								<p>&nbsp;&nbsp;3、作业时检查储罐油气回收装置是否正常，如有异常及时与调度联系;</p>
								<p>&nbsp;&nbsp;4、作业过程中加强检查，与调度保持联系;</p>
								<p>&nbsp;&nbsp;5、中控室加强对储罐温度的监控;</p>
								<p>&nbsp;&nbsp;6、加强管线温度检查</p>
							</h6></td>
					</tr>
				</table>
			</div>
		</form>
		<!-- END FORM-->

		<div class="portlet box white notice-wn">
			<div class="portlet-title">
				<div class="caption"
					style="font-size: 13px; color: #333333; margin-left: 3px;">
					四、作业过程中的风险分析及相关措施:&nbsp;<a href="javascript:void(0)"
						onclick="InboundOperation.openWarning(2);"><i
						class="fa fa-question-circle"></i> </a>
				</div>
			</div>
			<div class="portlet-body form dialog-warning2">
				<form action="#" class="form-horizontal">
					<div class="form-body">
						<table width="100%"
							class="table table-condensed table-striped table-bordered notice-table">
							<tr>
								<td class="col-md-4"><h6 style="text-align: center">危害或潜在事件</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: center">主要后果</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: center">安全措施</h6>
								</td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">1、个人防护用品不适用或未正确佩戴</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;人身伤害</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确佩戴合适的个人防护用品</h6>
								</td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">2、使用非防爆工具，产生火花</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;火灾、爆炸</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;使用防爆工具</h6>
								</td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">3、人体静电接触可燃气体</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;火灾、爆炸</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;进入作业场所前释放人体静电</h6>
								</td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">4、未按要求开启阀门</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;物料无法循环加热</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;按要求开启阀门</h6>
								</td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">5、阀门泄漏</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;使用不锈钢器具及时盛接、回收</h6>
								</td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">6、储罐油气回收装置故障</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;储罐涨裂</h6>
								</td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;作业中加强检查油气回收装置</h6>
								</td>
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
				<div class="caption"
					style="font-size: 13px; color: #333333; margin-left: 3px;">五、作业过程的检查</div>
			</div>
			<div class="portlet-body">
				<div class="form-body">
					<table width="100%" class="beforeTable">
						<tr>
							<td colspan="2"><h6>
									<strong>&nbsp;作业前检查</strong>&nbsp;<a href="javascript:void(0)"
										name="before" id="beforeCheckAll"><i
										class="fa fa-square-o"></i> </a>
								</h6></td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>1、个人防护用品是否正确佩戴</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="a" id="a1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="a" id="a2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>2、通讯系统是否通畅</h6>
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
								<h6>3、消防器材是否到位</h6>
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
								<h6>4、是否释放身体静电</h6>
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
								<h6>5、确认工艺流程是否正确</h6>
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
								<h6>6、加热装置设定温度是否正常</h6>
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
							<td class="col-md-8">
								<h6>7、确认相关阀门是否按要求开启、关闭</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="g" id="g1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="g" id="g2" value="option2"> 否
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
						<shiro:hasPermission name="ANOTICEDOBENZOLHOT">
							<div class="col-md-8 form-horizontal isNoModify">
								<div class="form-group">
									<label class="col-md-4 control-label">作业人员：<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" id="beforeTaskUserId" readonly
											class="form-control" data-required="1" />
									</div>
								</div>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDISPATCHANDBENZOLHOT">
							<button type="button" class="btn btn-success " key="1" status="0"
								id="saveBeforeTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOBENZOLHOT">
							<button type="button" class="btn btn-primary isNoModify" key="1"
								status="1" id="submitBeforeTask">确认</button>
						</shiro:hasPermission>
					</div>
				</div>
				<div class="form-body">
					<table width="100%" class="inTable">
						<tr>
							<td colspan="2"><h6>
									<strong>&nbsp;作业中检查</strong>&nbsp;<a href="javascript:void(0)"
										name="in" id="inCheckAll"><i class="fa fa-square-o"></i> </a>
								</h6></td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>1、储罐油气回收装置是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="h" id="h1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="h" id="h2" value="option2"> 否
										</label><label class="checkbox-inline"> <input type="checkbox"
											name="h" id="h3" value="option3"> 不适用
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>2、储罐温度是否正常上升</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="i" id="i1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="i" id="i2" value="option2"> 否
										</label><label class="checkbox-inline"> <input type="checkbox"
											name="i" id="i3" value="option3"> 不适用
										</label>
										<div class="iDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDBENZOLHOT">
												<button style="padding: 3px 8px;" type="button" key="2"
													class="btn btn-primary isModify spBtn" id="iSureBtn">确认</button>
											</shiro:hasPermission>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>3、现场管线温度是否正常上升</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="j" id="j1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="j" id="j2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>4、加热装置是否运行正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="k" id="k1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="k" id="k2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>5、循环泵是否运行正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="l" id="l1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="l" id="l2" value="option2"> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="l" id="l3" value="option2"> 不适用
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>6、是否有物料滴漏</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="m" id="m1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="m" id="m2" value="option2"> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="m" id="m3" value="option2"> 不适用
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
					<div class="modal-footer isModify inDiv">
						<shiro:hasPermission name="ANOTICEDOBENZOLHOT">
							<div class="col-md-8 form-horizontal isNoModify">
								<div class="form-group">
									<label class="col-md-4 control-label">工作人员：<span
										class="required">*</span>
									</label>
									<div class="col-md-4">
										<input type="text" id="inTaskUserId" readonly
											class="form-control" data-required="1" />
									</div>
								</div>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDISPATCHANDBENZOLHOT">
							<button type="button" class="btn btn-success" key="2" status="0"
								id="saveInTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOBENZOLHOT">
							<button type="button" class="btn btn-primary isNoModify" key="2"
								status="1" id="submitInTask">确认</button>
						</shiro:hasPermission>
					</div>
				</div>
				<div class="form-body">
					<table width="100%" class="afterTable">
						<tr>
							<td colspan="2"><h6>
									<strong>&nbsp;作业后检查</strong>&nbsp;<a href="javascript:void(0)"
										name="after" id="afterCheckAll"><i class="fa fa-square-o"></i>
									</a>
								</h6></td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>1、阀门是否复位</h6>
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
								<h6>2、加热装置是否关闭</h6>
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
								<h6>3、循环泵是否停止运行</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="p" id="p1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="p" id="p2" value="option2"> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="p" id="p3" value="option3"> 不适用
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>4、现场是否清理干净</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="q" id="q1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="q" id="q2" value="option2"> 否
										</label>
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
						<shiro:hasPermission name="ANOTICEDOBENZOLHOT">
							<div class="col-md-8 form-horizontal isNoModify">
								<div class="form-group">
									<label class="col-md-4 control-label">工作人员：<span
										class="required">*</span>
									</label>
									<div class="col-md-4">
										<input type="text" id="afterTaskUserId" readonly
											class="form-control" data-required="1" />
									</div>
								</div>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDISPATCHANDBENZOLHOT">
							<button type="button" class="btn btn-success" key="3" status="0"
								id="saveAfterTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOBENZOLHOT">
							<button type="button" class="btn btn-primary isNoModify" key="3"
								status="1" id="submitAfterTask">确认</button>
						</shiro:hasPermission>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<input type="hidden" class="form-control" id="transportprogramId">
<div class="modal-footer">
	<label key='0' class="modifyNotice hidden"></label>
	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	<shiro:hasPermission name="ANOTICEDISPATCH">
		<button type="button" class="btn btn-primary" id="reset">重置</button>
		<button type="button" class="btn btn-primary" id="modifyNotice">修改</button>
	</shiro:hasPermission>
</div>