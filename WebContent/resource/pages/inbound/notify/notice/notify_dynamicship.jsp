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
			<shiro:hasPermission name="ANOTICEDYNAMICSHIPEXEL">
				<a class="hidden-print" style="color: white;"
					onclick="notify.exportXML(this,30)"> <i
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
							<div class="col-md-12"
								style="padding-left: 0px; padding-right: 0px;">
								<textarea class="form-control" rows="3" id="taskMsg"></textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><h5>&nbsp;二.作业工艺流程</h5></td>
					</tr>
					<tr>
						<td>
							<div class="col-md-12"
								style="padding-left: 0px; padding-right: 0px;">
								<div id="contentDiv" class="content"
									style="border: 1px solid rgb(216, 216, 216); position: relative; overflow-x: hidden; overflow-y: auto; cursor: move; width: 631px; height: 350px;">
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><h5>
								&nbsp;三.作业要求&nbsp;<a href="javascript:void(0);"
									onclick="notify.dialogShowLog(this,12)"><i
									class="fa fa-file-text-o"></i></a>&nbsp;<a
									id="saveRequire" onclick="notify.saveLog(this,12)" href="javascript:void(0);" title="保存"><i
									class="fa fa-floppy-o"></i></a>
							</h5></td>
					</tr>
					<tr>
						<td class="col-md-12">
							<div class="col-md-12"
								style="padding-left: 0px; padding-right: 0px;">
								<textarea class="form-control assignwork" rows="1"
									id="taskRequire"></textarea>
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
													class="form-control" data-required="1" />
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
								&nbsp;四.作业注意事项&nbsp;<a href="javascript:void(0)"
									onclick="InboundOperation.openWarning(1);"><i
									class="fa fa-question-circle"></i></a>
							</h5></td>
					</tr>
					<tr>
						<td colspan="2" class="dialog-warning1"><h6>
								<p>&nbsp;&nbsp;1.正确佩戴个人防护用品；</p>
								<p>&nbsp;&nbsp;2.发货时储罐应开进口阀；</p>
								<p>&nbsp;&nbsp;3.发货时检查储罐呼吸阀是否正常，如有异常及时与调度联系；</p>
								<p>&nbsp;&nbsp;4.作业过程中加强检查，与调度保持联系；</p>
								<p>&nbsp;&nbsp;5.中控室加强对储罐的监控；</p>
								<p>&nbsp;&nbsp;6.放气作业结束，放气过程中产生的物料及时回收入库；</p>
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
					五.作业中的风险分析&nbsp;<a href="javascript:void(0)"
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
								<td class="col-md-4"><h6 class="form-section "
										style="text-align: left">1、个人防护用品不适用或未正确佩戴</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;人身伤害</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确佩戴使用的防护用品</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">2、使用非防爆工具，产生火花</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;火灾、爆炸</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;使用防爆工具</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">3、人体静电接触可燃气体</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;火灾、爆炸</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;进入作业场所前释放人体静电</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">4、作业前未检查管线状态</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;发错罐、泵损坏、物料串罐</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;根据作业通知单认真检查管线状态，与调度保持联系、确认</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">5、未按要求开启储罐阀门</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;物料出现品质问题</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;按要求开启作业罐的阀门</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">6、阀门泄漏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;使用不锈钢器具及时盛接、回收</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">7、罐呼吸阀故障</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;造成瘪罐</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;严格按操作规程执行</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">8、发货过程未及时复查、巡检</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;发货时阀门、管线泄漏未能及时发现，造成安全隐患</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;在发货过程中继续关注设备运行情况，按要求进行巡检</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">9、压管线压力过高</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;管线破裂、阀门渗漏、泵坏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;关注压力，开旁路压管线，及时关泵</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">10、结束后管线及时泄压</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;管线涨压破裂</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;结束后及时与调度联系，及时开启泄压阀</h6></td>
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
					style="font-size: 13px; color: #333333; margin-left: 3px;">六.作业过程的检查</div>
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
											type="checkbox" name="c" id='c2' value="option2"> 否
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
								<h6>6、确认相关阀门是否按要求开启、关闭</h6>
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
								<h6>7、泵是否送电、点动试车</h6>
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
							<td class="col-md-8">
								<h6>8、中控室SCADA系统参数是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group ">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="h" id="h1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="h" id="h2" value="option2"> 否
										</label>
										<div class="hDiv" style="float: right;">
											<button style="padding: 3px 8px;" type="button" key="1"
												class="btn btn-primary isModify spBtn" id="hSureBtn">确认</button>
										</div>
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
						<div class="col-md-8 form-horizontal  isNoModify">
							<div class="form-group">
								<label class="col-md-4 control-label">作业人员：<span
									class="required">*</span></label>
								<div class="col-md-4">
									<input type="text" id="beforeTaskUserId" disabled="disabled"
										class="form-control" data-required="1" />
								</div>
							</div>
						</div>
						<button type="button" class="btn btn-success" key="1" status="0"
							id="saveBeforeTask">保存</button>
						<button type="button" class="btn btn-primary isNoModify" key="1"
							status="1" id="submitBeforeTask">确认</button>
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
								<h6>1、中控室SCADA系统是否正常下降</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="i" id="i1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="i" id="i2" value="option2"> 否
										</label>
										<div class="iDiv" style="float: right;">
											<button style="padding: 3px 8px;" type="button" key="2"
												class="btn btn-primary isModify spBtn" id="iSureBtn">确认</button>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>2、装货流速是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="j" id="j1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="j" id="j2" value="option2"> 否
										</label>
										<div class="jDiv" style="float: right;">
											<button style="padding: 3px 8px;" type="button" key="2"
												class="btn btn-primary isModify spBtn" id="jSureBtn">确认</button>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>3、储罐呼吸阀是否正常</h6>
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
								<h6>4、泵运行是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="l" id="l1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="l" id="l2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>5、是否无物料滴漏</h6>
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
								<h6>6、是否按要求进行巡检</h6>
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
							<td colspan="2">
								<div class="col-md-12 incheck"></div>
							</td>
						</tr>
					</table>
					<div class="modal-footer isModify inDiv">
						<shiro:hasPermission name="ANOTICEDODYNAMICSHIP">
							<div class="col-md-8 form-horizontal isNoModify">
								<div class="form-group">
									<label class="col-md-4 control-label">作业人员：<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" id="inTaskUserId" disabled="disabled"
											class="form-control" data-required="1" />
									</div>
								</div>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDISPATCHANDDYNAMICSHIP">
							<button type="button" class="btn btn-success" key="2" status="0"
								id="saveInTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDODYNAMICSHIP">
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
								<h6>1、泵是否停止</h6>
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
								<h6>2、管线是否泄压</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="p" id="p1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="p" id="p2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>3、现场是否整理干净</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="q" id="q1" value="option2"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="q" id="q2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>4、物料是否正确回收并标记入库</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="r" id="r1" value="option2"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="r" id="r2" value="option2"> 否
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
											name="s" id="s1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="s" id="s2" value="option2"> 否
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
						<shiro:hasPermission name="ANOTICEDODYNAMICSHIP">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDDYNAMICSHIP">
							<button type="button" class="btn btn-success" key="3" status="0"
								id="saveAfterTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDODYNAMICSHIP">
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
	<label class="hidden" id="transportId"></label> <label key='0'
		class="modifyNotice hidden"></label>
	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	<shiro:hasPermission name="ANOTICEDISPATCH">
		<button type="button" class="btn btn-primary" id="reset">重置</button>
		<button type="button" class="btn btn-primary" id="modifyNotice">修改</button>
	</shiro:hasPermission>
</div>