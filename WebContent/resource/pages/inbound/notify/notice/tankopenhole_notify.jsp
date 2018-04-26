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
			<shiro:hasPermission name="ANOTICETANKOPENHOLEEXEL">
				<a class="hidden-print" style="color: white;"
					onclick="notify.exportXML(this,23)"> <i
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
						<td class="col-md-12">
							<div class="col-md-12 form-group">
								<div class="col-md-6">
									<label class="col-md-4 control-label">罐号:</label>
									<div class="col-md-8">
										<input class="form-control" maxlength="16" id="tankId">
									</div>
								</div>
								<div class="col-md-6">
									<label class="col-md-4 control-label">物料名称:</label>
									<div class="col-md-8">
										<input class="form-control" maxlength="16" id="productId">
									</div>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><h5>&nbsp;一、作业任务:</h5></td>
					</tr>
					<tr>
						<td class="col-md-12">
							<div class="col-md-12">
								<textarea class="form-control" maxlength="150" rows="3"
									id="taskMsg"></textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><h5>&nbsp;二、开人孔前储罐状态 :</h5></td>
					</tr>
					<tr>
						<td class="col-md-12">
							<div class="col-md-12 form-group">
								<div class="col-md-6"
									style="padding-left: 0px; padding-right: 0px;">
									<label class="col-md-6 control-label">SCADA系统雷达液位:</label>
									<div class="col-md-5">
										<input class="form-control" maxlength="16"
											onkeyup="config.clearNoNum(this)" id="scadaLevel">
									</div>
									<label class="col-md-1 control-label"
										style="padding-left: 0px;">mm</label>
								</div>
								<div class="col-md-6"
									style="padding-left: 0px; padding-right: 0px;">
									<label class="col-md-6 control-label">计量手尺液位:</label>
									<div class="col-md-5">
										<input class="form-control" maxlength="16"
											onkeyup="config.clearNoNum(this)" id="handLevel">
									</div>
									<label class="col-md-1 control-label"
										style="padding-left: 0px;">mm</label>
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
								&nbsp;三、作业注意事项:&nbsp;<a href="javascript:void(0)"
									onclick="InboundOperation.openWarning(1);"><i
									class="fa fa-question-circle"></i></a>
							</h5></td>
					</tr>
					<tr>
						<td colspan="2" class="dialog-warning1"><h6>
								<p>&nbsp;&nbsp;1、正确佩戴个人防护用品;</p>
								<p>&nbsp;&nbsp;2、消防器材到位;</p>
								<p>&nbsp;&nbsp;3、监护人员到位，双人确认，作业期间不得离开现场;</p>
								<p>&nbsp;&nbsp;4、中控室加强对储罐的监控;</p>
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
					四、作业中的风险分析:&nbsp;<a href="javascript:void(0)"
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
								<td class="col-md-4"><h6 style="text-align: left">1、未按规定佩戴劳动防护用品</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;人身伤害</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确佩戴个人劳动防护用品</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">2、使用非防爆工具，产生火花</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;火灾、爆炸</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;使用防爆工具</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">3、人体静电接触可燃气体</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;着火、爆炸</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;进入罐区释放人体静电</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">4、储罐内油气挥发</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;人体中毒</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确使用劳动防护用品</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">5、开错储罐人孔</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;物料泄露，环境污染</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;作业前加强核对确认</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">6、开人孔时储罐内物料高度高于人孔下沿</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境，造成料漏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;作业前加强储罐内物料量的确认</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">7、人员坠落</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;人身伤害</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确佩戴劳动防护用品，加强现场监护</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">8、螺栓、螺母腐蚀，咬死</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;用力过度，人身伤害</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;平时做好螺母、螺栓的保养工作</h6></td>
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
					style="font-size: 13px; color: #333333; margin-left: 3px;">五、作业过程的检查:</div>
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
								<h6>2、消防器材是否到位</h6>
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
								<h6>3、是否释放身体静电</h6>
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
								<h6>4、是否使用防爆工具</h6>
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
								<h6>5、储罐罐号与作业单是否一致</h6>
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
								<h6>6、确认储罐是否与主管线断开</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="f" id="f1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="f" id="f2" value="option2"> 否
										</label><label class="checkbox-inline"> <input
											type="checkbox" name="f" id="f3" value="option3"> 不适用
										</label>
										<div class="fDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDTANKOPENHOLE">
												<button style="padding: 3px 8px;" type="button" key="1"
													class="btn btn-primary isModify spBtn" id="fSureBtn">确认</button>
											</shiro:hasPermission>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>7、确认储罐尾部管线是否断开</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="g" id="g1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="g" id="g2" value="option2"> 否
										</label><label class="checkbox-inline"> <input
											type="checkbox" name="g" id="g3" value="option3"> 不适用
										</label>
										<div class="gDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDTANKOPENHOLE">
												<button style="padding: 3px 8px;" type="button" key="1"
													class="btn btn-primary isModify spBtn" id="gSureBtn">确认</button>
											</shiro:hasPermission>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>8、储罐液位是否确认低于人孔</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="h" id="h1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="h" id="h2" value="option2"> 否
										</label>
										<div class="hDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDTANKOPENHOLE">
												<button style="padding: 3px 8px;" type="button" key="1"
													class="btn btn-primary isModify spBtn" id="hSureBtn">确认</button>
											</shiro:hasPermission>
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
						<shiro:hasPermission name="ANOTICEDOTANKOPENHOLE">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDTANKOPENHOLE">
							<button type="button" class="btn btn-success " key="1" status="0"
								id="saveBeforeTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOTANKOPENHOLE">
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
								<h6>1、现场监护人员是否到位</h6>
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
								<h6>2、螺栓、螺母是否没有被腐蚀，咬死</h6>
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
								<h6>3、罐内是否有残余物料</h6>
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
							<td colspan="2">
								<div class="col-md-12 incheck"></div>
							</td>
						</tr>
					</table>
					<div class="modal-footer inDiv isModify">
						<shiro:hasPermission name="ANOTICEDOTANKOPENHOLE">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDTANKOPENHOLE">
							<button type="button" class="btn btn-success" key="2" status="0"
								id="saveInTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOTANKOPENHOLE">
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
								<h6>1、是否按要求打开人孔</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="l" id="l1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="l" id="l2" value="option2"> 否
										</label>
										<div class="lDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDTANKOPENHOLE">
												<button style="padding: 3px 8px;" type="button" key="3"
													class="btn btn-primary isModify spBtn" id="lSureBtn">确认</button>
											</shiro:hasPermission>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>2、人孔附件是否完好</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="m" id="m1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="m" id="m2" value="option2"> 否
										</label>
										<div class="mDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDTANKOPENHOLE">
												<button style="padding: 3px 8px;" type="button" key="3"
													class="btn btn-primary isModify spBtn" id="mSureBtn">确认</button>
											</shiro:hasPermission>
										</div>
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
								<h6>4、记录是否完善</h6>
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
							<td colspan="2">
								<div class="col-md-12 aftercheck"></div>
							</td>
						</tr>
					</table>
					<div class="modal-footer afterDiv isModify">
						<shiro:hasPermission name="ANOTICEDOTANKOPENHOLE">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDTANKOPENHOLE">
							<button type="button" class="btn btn-success" key="3" status="0"
								id="saveAfterTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOTANKOPENHOLE">
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