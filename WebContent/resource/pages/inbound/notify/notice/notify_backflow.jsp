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
			<shiro:hasPermission name="ANOTICEBACKFLOWEXEL">
				<a class="hidden-print" style="color: white;"
					onclick="notify.exportXML(this,25)"> <i
					class="fa fa-file-excel-o">&nbsp;导出</i>
				</a>
			</shiro:hasPermission>
		</div>
		<!-- <div class="tools">
							<a class="hidden-print" style="color: white;" onclick="javascript:window.print();"> <i class="fa fa-print">&nbsp;打印</i>
							</a>
						</div> -->
	</div>
	<div class="portlet-body ">
		<!-- BEGIN FORM-->
		<form action="#" class="form-horizontal form-bordered">
			<div class="form-body">
			<label class="control-label hidden" id="transportId"></label>
				<table width="100%">
					<tr>
						<td colspan="2"><h5>&nbsp;一.作业任务</h5></td>
					</tr>
					<tr>
						<td class="col-md-12"><textarea class="form-control"
								maxlength="150" rows="3" id="taskMsg"></textarea></td>
					</tr>
					<tr>
						<td colspan="2"><h5>&nbsp;二.作业工艺流程</h5></td>
					</tr>
					<tr>
						<td >
											<div class="col-md-7">
												<div id="contentDiv" class="content" style="border: 1px solid #d8d8d8; border-radius: 3px; float: left; height: 340px; width: 100%;">
									<div id="toolbarContainer"
										style="overflow: hidden; width: 100px; height: 20px; margin-top: 0px; z-index: 999; border: 1px solid #d8d8d8; border-radius: 3px;"></div>
									<div id="graphContainer"
										style="overflow: hidden; width: 100%; height: 320px; margin-top: 0px;"></div>
								</div></div></td>
					</tr>
					<tr>
						<td colspan="2">
							<h5>
								&nbsp;三.作业要求&nbsp;<a href="javascript:void(0);"
									onclick="notify.dialogShowLog(this,3)"><i
									class="fa fa-file-text-o"></i></a> &nbsp;<a
									href="javascript:void(0);" onclick="notify.saveLog(this,3)"><i
									class="fa fa-floppy-o"></i></a>
							</h5>
						</td>
					</tr>
					<tr>
						<td class="col-md-12"><textarea
								class="form-control assignwork" maxlength="150" id="taskRequire"
								rows="2"></textarea></td>
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
												<input type="text" id="sureTaskUserId" readonly
													class="form-control" data-required="1" />
											</div>
										</div>
									</div>
									<button type="button" class="btn btn-success " key="0"
										status="0" id="saveSureTask">保存</button>
									<button type="button" class="btn btn-primary isNoModify"
										key="0" status="1" id="submitSureTask">确认</button>
								</div>
							</shiro:hasPermission></td>
					</tr>
					<tr>
						<td colspan="2">
							<h5>
								&nbsp;四.作业注意事项&nbsp;<a href="javascript:void(0)"
									onclick="InboundOperation.openWarning(1);"><i
									class="fa fa-question-circle"></i></a>
							</h5>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="dialog-warning1"><h6>
								<p>&nbsp;&nbsp;1.正确佩戴劳保用品</p>
								<p>&nbsp;&nbsp;2.使用防爆工具</p>
								<p>&nbsp;&nbsp;3.接管前确认管线号</p>
								<p>&nbsp;&nbsp;4.作业中对管线的压力情况加强检查及时汇报调度</p>
								<p>&nbsp;&nbsp;5.作业过程中做好对呼吸阀的检查工作</p>
								<p>&nbsp;&nbsp;6.倒罐结束后严格按照调度命令拆除管线</p>
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
			<div class="portlet-body dialog-warning2">
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
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确佩戴使用的防护用品</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">2、未使用防爆工具</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;火灾爆发</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;严格按规定使用防爆工具</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">3、作业前管线未确认</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;开错阀门，造成串料</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;及时与调度联系，加强核对</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">4、作业过程中呼吸阀异常造成储罐憋压或抽瘪</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;损坏储罐，引发火灾爆炸</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;按要求做好呼吸阀的检查</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">5、打回流过程未及时复查，巡检</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;打回流时阀门，管线泄漏未能及时发现，造成安全隐患</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;在打回流过程中及时关注管线压力，按要求进行巡检</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">6、液位低引起泵异常振动引起泵或管线损坏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;物料泄漏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;加强作业过程中的巡回检查</h6></td>
							</tr>
						</table>
					</div>
				</form>
			</div>
			<div class="modal-footer openNextDiv">
				<button type="button" class="btn btn-primary openNext">确认</button>
			</div>
		</div>
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
									<strong>&nbsp;作业前检查</strong><a href="javascript:void(0)"
										name="before" id="beforeCheckAll"><i
										class="fa fa-square-o"></i></a>
								</h6></td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>1.高位报警和联锁是否测试正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="b" id="b1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="b" id="b2" value=""> 否
										</label>
										<div class="bDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDBACKFLOW">
												<button style="padding: 3px 8px;" type="button" key="1"
													class="btn btn-primary isModify spBtn" id="bSureBtn">确认</button>
											</shiro:hasPermission>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>2.个人防护用品是否正确佩戴</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="c" id="c1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="c" id="c2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>3.现场流程是否和调度指令一致</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="d" id="d1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="d" id="d2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>4.储罐,管线法兰处是否无渗漏</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="e" id="e1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="e" id="e2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>5.是否释放身体静电</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="f" id="f1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="f" id="f2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>6.使用软管的品种是否匹配</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="g" id="g1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="g" id="g2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>7.垫圈是否正确放置</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="h" id="h1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="h" id="h2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>8.螺丝是否上全无滴漏</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="i" id="i1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="i" id="i2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>9.是否排污口倒空</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="j" id="j1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="j" id="j2" value=""> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="j" id="j3" value=""> 不适用
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>10.若是排污口倒空，储罐阀门及泄压阀是否关闭</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="k" id="k1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="k" id="k2" value=""> 否
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
						<shiro:hasPermission name="ANOTICEDOBACKFLOW">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDBACKFLOW">
							<button type="button" class="btn btn-success " key="1" status="0"
								id="saveBeforeTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOBACKFLOW">
							<button type="button" class="btn btn-primary isNoModify" key="1"
								status="1" id="submitBeforeTask">确认</button>
						</shiro:hasPermission>
					</div>
				</div>

				<div class="form-body">
					<table width="100%" class="inTable">
						<tr>
							<td colspan="2"><h6>
									<strong>&nbsp;作业中检查</strong><a href="javascript:void(0)"
										name="in" id="inCheckAll"><i class="fa fa-square-o"></i></a>
								</h6></td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>1.管线压力是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="p" id="p1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="p" id="p2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>2.软管是否正常无颤动</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="q" id="q1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="q" id="q2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>3.呼吸阀是否工作正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="r" id="r1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="r" id="r2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>4.泵运行是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="s" id="s1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="s" id="s2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>5.中控室SCADA系统液位是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="t" id="t1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="t" id="t2" value=""> 否
										</label> <label class="checkbox-inline"><input type="checkbox"
											name="t" id="t3">不适用</label>
										<div class="tDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDBACKFLOW">
												<button style="padding: 3px 5px;" type="button" key="2"
													class="btn btn-primary isModify spBtn" id="tSureBtn">确认</button>
											</shiro:hasPermission>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>6.中控室SCADA系统流量是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="u" id="u1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="u" id="u2" value=""> 否
										</label>
										<div class="uDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDBACKFLOW">
												<button style="padding: 3px 8px;" type="button" key="2"
													class="btn btn-primary  isModify spBtn" id="uSureBtn">确认</button>
											</shiro:hasPermission>
										</div>
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
						<shiro:hasPermission name="ANOTICEDOBACKFLOW">
							<div class="col-md-8 form-horizontal isNoModify">
								<div class="form-group">
									<label class="col-md-4 control-label">作业人员：<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" id="inTaskUserId" readonly
											class="form-control" data-required="1" />
									</div>
								</div>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDISPATCHANDBACKFLOW">
							<button type="button" class="btn btn-success " key="2" status="0"
								id="saveInTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOBACKFLOW">
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
										name="after" id="afterCheckAll"><i class="fa fa-square-o"></i></a>
								</h6></td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>1.结束后是否按调度命令泄压</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="v" id="v1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="v" id="v2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>2.作业结束现场是否整理干净</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="w" id="w1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="w" id="w2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>3.作业结束阀门，管线，储罐是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="x" id="x1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="x" id="x2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>4.拆管物料是否回收</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="y" id="y1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="y" id="y2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>5.记录是否完善</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="z" id="z1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="z" id="z2" value=""> 否
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
						<shiro:hasPermission name="ANOTICEDOBACKFLOW">
							<div class="col-md-8 form-horizontal isNoModify">
								<div class="form-group">
									<label class="col-md-4 control-label">作业人员：<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" id="afterTaskUserId" readonly
											class="form-control" data-required="1" />
									</div>
								</div>
							</div>
						</shiro:hasPermission>
						<!-- start -->
						<shiro:hasPermission name="ANOTICEDISPATCHANDBACKFLOW">
							<button type="button" class="btn btn-success " key="3" status="0"
								id="saveAfterTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOBACKFLOW">
							<button type="button" class="btn btn-primary isNoModify" key="3"
								status="1" id="submitAfterTask">确认</button>
						</shiro:hasPermission>
						<!-- end -->
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