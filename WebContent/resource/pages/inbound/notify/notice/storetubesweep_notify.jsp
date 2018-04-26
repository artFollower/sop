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
			<shiro:hasPermission name="ANOTICESTORETUBESWEEPEXEL">
				<a class="hidden-print" style="color: white;"
					onclick="notify.exportXML(this,20)"> <i
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
						<td class="col-md-12"><textarea class="form-control"
								maxlength="150" rows="3" id="taskMsg"></textarea></td>
					</tr>
					<tr>
						<td colspan="2"><h5>&nbsp;二、作业工艺流程:</h5></td>
					</tr>
					<tr>
						<td class="col-md-12">
							<div id="toolbarContainer"
								style="width: 100px; height: 22px; z-index: 999; border: 1px solid #d8d8d8; border-radius: 3px;"></div>
							<div id="contentDiv" class="content"
								style="border: 1px solid #d8d8d8; border-radius: 3px; float: left; height: 60px; width: 625px;">
								<div id="graphContainer"
									style="width: 625px; height: 60px; margin-top: 0px;"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><h5>
								&nbsp;三、作业前状态:&nbsp;<a href="javascript:void(0)" id="copyGraph"><i
									class="fa fa-copy" title="复制工艺流程"></i></a>
							</h5></td>
					</tr>
					<tr>
						<td class="col-md-12">
							<div id="toolbarContainer1"
								style="width: 100px; height: 22px; z-index: 999; border: 1px solid #d8d8d8; border-radius: 3px;"></div>
							<div id="contentDiv1" class="content"
								style="border: 1px solid #d8d8d8; border-radius: 3px; float: left; height: 60px; width: 625px;">
								<div id="graphContainer1"
									style="width: 625px; height: 60px; margin-top: 0px;"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><h5>
								&nbsp;四、作业要求:&nbsp;<a href="javascript:void(0);"
									onclick="notify.dialogShowLog(this,8)"><i
									class="fa fa-file-text-o"></i></a> &nbsp;<a
									href="javascript:void(0);" onclick="notify.saveLog(this,8)"><i
									class="fa fa-floppy-o"></i></a>
							</h5></td>
					</tr>
					<tr>
						<td class="col-md-12"><textarea
								class="form-control assignwork" maxlength="150" rows="1"
								id="taskRequire"></textarea></td>
					</tr>
					<tr>
						<td colspan="2">
							<div class="col-md-12 surecheck"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><shiro:hasPermission name="ANOTICEDISPATCH">
								<div class="modal-footer sureDiv isModify">
									<div class="col-md-8 form-horizontal isNoModify">
										<div class="form-group">
											<label class="col-md-4 control-label">调度<span
												class="required">*</span></label>
											<div class="col-md-4">
												<input type="text" id="sureTaskUserId" readonly
													class="form-control" data-required="1" />
											</div>
										</div>
									</div>
									<button type="button" class="btn btn-success" key="0"
										status="0" id="saveSureTask">保存</button>
									<button type="button" class="btn btn-primary isNoModify"
										key="0" status="1" id="submitSureTask">确认</button>
								</div>
							</shiro:hasPermission></td>
					</tr>
					<tr>
						<td colspan="2"><h5>
								&nbsp;五、作业注意事项:&nbsp;<a href="javascript:void(0)"
									onclick="InboundOperation.openWarning(1);"><i
									class="fa fa-question-circle"></i></a>
							</h5></td>
					</tr>
					<tr>
						<td colspan="2" class="dialog-warning1"><h6>
								<p>&nbsp;&nbsp;1、正确佩戴个人防护用品;</p>
								<p>&nbsp;&nbsp;2、扫线时储罐阀门应正确切换;</p>
								<p>&nbsp;&nbsp;3、作业时检查储罐呼吸阀是否正常,如有异常及时与调度联系;</p>
								<p>&nbsp;&nbsp;4、中控室加强对储罐液位的监控;</p>
								<p>&nbsp;&nbsp;5、收球筒阀门应处于正确位置;</p>
								<p>&nbsp;&nbsp;6、扫线时需打开储罐测量孔;</p>
								<p>&nbsp;&nbsp;7、扫线后管线内余压不得卸入罐内;</p>
								<p>&nbsp;&nbsp;8、作业过程中加强检查,与调度保持联系。</p>
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
					六、作业过程中的风险分析及相关措施:&nbsp;<a href="javascript:void(0)"
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
								<td class="col-md-4"><h6 style="text-align: center">危害或潜在事件</h6></td>
								<td class="col-md-4"><h6 style="text-align: center">主要后果</h6></td>
								<td class="col-md-4"><h6 style="text-align: center">安全措施</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">1、个人防护用品不适用或未正确佩戴</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;人身伤害</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确佩戴合适的个人防护用品</h6></td>
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
								<td class="col-md-4"><h6 style="text-align: left">4、未按要求开启储罐阀门</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;无法扫线</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;按要求开启作业罐的阀门</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">5、阀门泄露</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;使用不锈钢器具及时盛接、回收</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">6、罐呼吸阀故障</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;储罐涨裂</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;作业中加强检查呼吸阀</h6></td>
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
					style="font-size: 13px; color: #333333; margin-left: 3px;">七、作业过程的检查</div>
			</div>
			<div class="portlet-body">
				<div class="form-body">
					<table width="100%" class="beforeTable">
						<tr>
							<td colspan="2"><h6>
									<strong>&nbsp;作业前检查</strong>&nbsp;<a href="javascript:void(0)"
										name="before" id="beforeCheckAll"><i
										class="fa fa-square-o"></i></a>
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
								<h6>2、通讯系统是否正常</h6>
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
							<td colspan="2">
								<div class="col-md-12 beforecheck"></div>
							</td>
						</tr>
					</table>
					<div class="modal-footer isModify beforeDiv">
						<shiro:hasPermission name="ANOTICEDOSTORETUBESWEEP">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDSTORETUBESWEEP">
							<button type="button" class="btn btn-success " key="1" status="0"
								id="saveBeforeTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOSTORETUBESWEEP">
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
										name="in" id="inCheckAll"><i class="fa fa-square-o"></i></a>
								</h6></td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>1、中控室SCADA系统液位是否正常上升</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="g" id="g1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="g" id="g2" value="option2"> 否
										</label><label class="checkbox-inline"> <input
											type="checkbox" name="g" id="g3" value="option2"> 不适用
										</label>
										<div class="gDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDSTORETUBESWEEP">
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
								<h6>2、储罐呼吸阀是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="h" id="h1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="h" id="h2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>3、是否无物料滴漏</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="i" id="i1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="i" id="i2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>4、扫线球是否收到</h6>
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
							<td colspan="2">
								<div class="col-md-12 incheck"></div>
							</td>
						</tr>
					</table>
					<div class="modal-footer inDiv isModify">
						<shiro:hasPermission name="ANOTICEDOSTORETUBESWEEP">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDSTORETUBESWEEP">
							<button type="button" class="btn btn-success" key="2" status="0"
								id="saveInTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOSTORETUBESWEEP">
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
										name="after" id="afterCheckAll"><i class="fa fa-square-o"></i></a>
								</h6></td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>1、阀门是否关闭</h6>
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
								<h6>2、管线是否泄压</h6>
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
								<h6>3、扫线球是否完整</h6>
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
								<h6>4、现场是否清理干净</h6>
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
								<h6>5、物料是否已回收</h6>
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
								<h6>6、记录是否完善</h6>
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
							<td colspan="2">
								<div class="col-md-12 aftercheck"></div>
							</td>
						</tr>
					</table>
					<div class="modal-footer afterDiv isModify">
						<shiro:hasPermission name="ANOTICEDOSTORETUBESWEEP">
							<div class="col-md-8 form-horizontal isNoModify">
								<div class="form-group">
									<label class="col-md-4 control-label">工作人员：<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" id="afterTaskUserId" readonly
											class="form-control" data-required="1" />
									</div>
								</div>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDISPATCHANDSTORETUBESWEEP">
							<button type="button" class="btn btn-success" key="3" status="0"
								id="saveAfterTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOSTORETUBESWEEP">
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