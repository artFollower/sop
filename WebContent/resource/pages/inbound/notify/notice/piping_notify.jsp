<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!-- 通知单内容开始 -->
<div class="input-group col-md-12">
	<label class="col-md-2 control-label" style="text-align: right;">日期:</label>
	<input style="text-align: center; border: 1px solid #ccc"
		id="createTime" class="date-picker col-md-3" type="text" /> <label
		class="col-md-2 col-md-offset-2 control-label"
		style="text-align: right;">编号:</label> <label
		class="col-md-3 control-label" key="0" style="text-align: left"
		id="code"></label>
</div>
<!-- end -->
<div class="portlet box blue">
	<div class="portlet-title">
		<div class="tools">
			<shiro:hasPermission name="ANOTICEPIPINGEXEL">
				<a class="hidden-print" style="color: white;"
					onclick="notify.exportXML(this,15)"> <i
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
						<td colspan="2"><h5>&nbsp;一.配管任务:</h5></td>
					</tr>
					<tr>
						<td class="col-md-12 tubeTask">
							<div id="toolbarContainer"
								style="width: 100px; height: 22px; z-index: 999; border: 1px solid #d8d8d8; border-radius: 3px;"></div>
							<div id="contentDiv" class="content"
								style="border: 1px solid #d8d8d8; border-radius: 3px; float: left; height: 120px; width: 625px;">
								<div id="graphContainer"
									style="width: 625px; height: 120px; margin-top: 0px;"></div>
							</div>
						</td>
					</tr>
					<tr></tr>
					<tr></tr>
					<tr>
						<td colspan="2"><h5>
								&nbsp;二.配管前管线状态:&nbsp;<a href="javascript:void(0)"
									id="copyGraph"><i class="fa fa-copy" title="复制工艺流程"></i></a>
							</h5></td>
					</tr>
					<tr>
						<td class="col-md-12 tubeState">
							<div id="toolbarContainer1"
								style="width: 100px; height: 22px; z-index: 999; border: 1px solid #d8d8d8; border-radius: 3px;"></div>
							<div id="contentDiv1" class="content"
								style="border: 1px solid #d8d8d8; border-radius: 3px; float: left; height: 120px; width: 625px;">
								<div id="graphContainer1"
									style="width: 625px; height: 120px; margin-top: 0px;"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><h5>
								&nbsp;三.作业要求:&nbsp;<a href="javascript:void(0);"
									onclick="notify.dialogShowLog(this,4)"><i
									class="fa fa-file-text-o"></i></a> &nbsp;<a
									href="javascript:void(0);" onclick="notify.saveLog(this,4)"><i
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
									<!-- start -->
									<button type="button" class="btn btn-success" key="0"
										status="0" id="saveSureTask">保存</button>
									<button type="button" class="btn btn-primary isNoModify"
										key="0" status="1" id="submitSureTask">确认</button>
									<!-- end -->
								</div>
							</shiro:hasPermission></td>
					</tr>
					<tr>
						<td colspan="2"><h5>
								&nbsp;四.作业注意事项:&nbsp;<a href="javascript:void(0)"
									onclick="InboundOperation.openWarning(1);"><i
									class="fa fa-question-circle"></i></a>
							</h5></td>
					</tr>
					<tr>
						<td colspan="2" class="dialog-warning1"><h6>
								<p>&nbsp;&nbsp;1.正确佩戴劳保用品</p>
								<p>&nbsp;&nbsp;2.使用防爆工具</p>
								<p>&nbsp;&nbsp;3.接管前确认主管线泄压罐及管线阀门状态</p>
								<p>&nbsp;&nbsp;4.接好后听令泄压</p>
								<p>&nbsp;&nbsp;5.作业过程中做好物料的盛接工作</p>
								<p>&nbsp;&nbsp;6.配管结束后废料回收入库</p>
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
					五.作业中的风险分析:&nbsp;<a href="javascript:void(0)"
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
								<td class="col-md-4"><h6 style="text-align: left">1、个人防护用品不适用或未佩戴</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;人身伤害</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确佩戴使用的防护用品</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">2、作业前管线阀门状态未确认</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;接错管线，造成串料</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;及时与调度联系，加强核对</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">3、未使用防爆工具</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;产生火花,有燃烧爆炸隐患</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;按规定使用防爆工具</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">4、管线物料未吹扫净，放气时物料喷溅</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;人身伤害</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;放气时人员注意躲避，尽量吹净管内物料</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">5、作业后管线阀门状态未确认</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;管线未泄压，或造成串料</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;完成后及时与调度联系，加强核对</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">6、结束后废料未回收入库</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境，或有火灾隐患</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;及时回收入库</h6></td>
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
					style="font-size: 13px; color: #333333; margin-left: 3px;">六.作业过程的检查:</div>
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
								<h6>1.个人防护用品是否正确佩戴</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="b" id="b1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="b" id="b2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>2.是否使用防爆工具</h6>
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
								<h6>3.通讯系统是否通畅</h6>
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
								<h6>4.消防器材是否到位</h6>
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
								<h6>6.确认工艺流程是否正确</h6>
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
								<h6>7.确认相关阀门是否按要求开启，关闭</h6>
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
								<h6>8.是否按调度命令吹尽软管中的物料</h6>
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
							<td colspan="2">
								<div class="col-md-12 beforecheck"></div>
							</td>
						</tr>
					</table>
					<div class="modal-footer isModify beforeDiv">
						<shiro:hasPermission name="ANOTICEDOPIPING">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDPIPING">
							<button type="button" class="btn btn-success " key="1" status="0"
								id="saveBeforeTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOPIPING">
							<button type="button" class="btn btn-primary isNoModify" key="1"
								status="1" id="submitBeforeTask">确认</button>
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
								<h6>1.垫圈是否正确放置</h6>
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
								<h6>2.螺丝是否上全</h6>
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
								<h6>3.是否无滴漏</h6>
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
								<h6>4.现场是否整理干净</h6>
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
								<h6>5.废料是否回收入库</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="t" id="t1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="t" id="t2" value=""> 否
										</label> <label class="checkbox-inline"><input type="checkbox"
											name="t" id="t3">不适用</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>6.确认相关阀门是否按要求开启，关闭</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="u" id="u1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="u" id="u2" value=""> 否
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
						<shiro:hasPermission name="ANOTICEDOPIPING">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDPIPING">
							<button type="button" class="btn btn-success" key="3" status="0"
								id="saveAfterTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOPIPING">
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
<input type="hidden" class="form-control" id="transportprogramId">
<div class="modal-footer">
	<label key='0' class="modifyNotice hidden"></label>
	<button type="button" class="btn btn-default" data-dismiss="modal">后退</button>
	<shiro:hasPermission name="ANOTICEDISPATCH">
		<button type="button" class="btn btn-primary" id="reset">重置</button>
		<button type="button" class="btn btn-primary" id="modifyNotice">修改</button>
	</shiro:hasPermission>
</div>
<!-- 通知单内容结束 -->
