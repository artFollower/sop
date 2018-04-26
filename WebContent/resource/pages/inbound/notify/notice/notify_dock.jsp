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
			<shiro:hasPermission name="ANOTICEDOCKEXEL">
				<a class="hidden-print" style="color: white;"
					onclick="notify.exportXML(this,26)"> <i
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
						<td class="col-md-12">
							<div class="col-md-12"
								style="padding-left: 0px; padding-right: 0px;">
								<textarea class="form-control" maxlength="150" rows="3"
									id="taskMsg"></textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"><h5>&nbsp;二.作业工艺流程</h5></td>
					</tr>
					<tr>
						<td class="col-md-12 ">
							<div id="contentDiv"
								style="border: 1px solid #d8d8d8; position: relative; cursor: move; width: 631px; height: 350px;"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<h5>
								&nbsp;三.作业要求&nbsp;<a href="javascript:void(0);"
									onclick="notify.dialogShowLog(this,1)"><i
									class="fa fa-file-text-o"></i></a> &nbsp;<a
									href="javascript:void(0);" onclick="notify.saveLog(this,1)" id="saveRequire"><i
									class="fa fa-floppy-o"></i></a>
							</h5>
						</td>
					</tr>
					<tr>
						<td class="col-md-12">
							<div class="col-md-12"
								style="padding-left: 0px; padding-right: 0px;">
								<textarea class="form-control  assignwork" rows="2"
									maxlength="150" id="taskRequire"></textarea>
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
								&nbsp;四.作业注意事项&nbsp;<a href="javascript:void(0)"
									onclick="InboundOperation.openWarning(1);"><i
									class="fa fa-question-circle"></i></a>
							</h5></td>
					</tr>
					<tr>
						<td colspan="2" class="dialog-warning1"><h6>
								<p>&nbsp;&nbsp;1.正确佩戴个人防护用品</p>
								<p>&nbsp;&nbsp;2.加强船岸检查</p>
								<p>&nbsp;&nbsp;3.关注船舶干弦</p>
								<p>&nbsp;&nbsp;4.注意风力变化及关注潮水及缆绳情况</p>
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
								<td class="col-md-4"><h6 style="text-align: center">危害或潜在事件</h6></td>
								<td class="col-md-4"><h6 style="text-align: center">主要后果</h6></td>
								<td class="col-md-4"><h6 style="text-align: center">安全措施</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">1、个人防护用品不适用或未正确佩戴</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;人身伤害</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确佩戴适用的个人防护用品</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">2、带缆作业时未穿戴救生衣</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;人员坠落江里，造成人员伤害</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;严格执行规定穿戴救生衣</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">3、起重机故障造成软管损坏，引起物料泄漏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;按照操作规程，加强检查</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">4、未正确进行接管作业</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;物料渗漏污染水体或发生火灾，影响货物品质</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;按照规程正确操作，加强核对及确认</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">5、作业前未与调度及船方及时沟通</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;阀门未正确开启就开始卸货，造成管线涨压泄漏或船泵故障，而造成事故</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;按照规程正确操作，加强核对及确认</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">6、作业过程中人员脱岗，软管未及时调整造成挤压，拉断，引起物料泄漏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境，引起火灾爆炸</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;严格执行劳动纪律，通过检查加强现场监管</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">7、压力超过工作压力，导致软管破裂，引发物料泄漏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境，引发火灾爆炸</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;现场值班人员加强检查，调度人员通过SCADA系统进行监控</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">8、缆绳未及时调整，导致船舶位移或缆绳断裂，拉断软管，引起物料泄漏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境，引发火灾爆炸</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;加强现场值班监管</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">9、结束后未及时关闭相关阀门和软管未封盲板</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;物料渗漏污染水体或发生火灾</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;按照规程正确操作，加强核对及确认</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">10、作业结束未及时清理管线内物料，吊装软管时物料洒落</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境，人身伤害</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;作业结束及时清理物料</h6></td>
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
										class="fa fa-square-o"></i></a>
								</h6></td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>1.软管是否适合拟装卸货物，其长度是否满足当日潮位差的要求</h6>
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
								<h6>2.软管是否做过压力测试，其额定的工作压力是否满足作业压力要求</h6>
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
								<h6>3.与作业相关的区域是否实施动火及施工管制的检查</h6>
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
								<h6>4.放置消防器材，检查消防系统是否处于随时使用状态</h6>
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
								<h6>5.通讯系统是否正常</h6>
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
								<h6>6.人员保护器材检查是否良好可用</h6>
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
								<h6>7.作业船舶周围水域环境是否良好</h6>
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
								<h6>8.防污设施，须布设围油栏的，通知围油栏公司进行布设</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="i" id="i1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="i" id="i2" value=""> 否
										</label> <label class="checkbox-inline"><input type="checkbox"
											name="i" id="i3">不适用</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>9.码头围堰与船舶甲板排水口是否有效堵塞并保持常关</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="j" id="j1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="j" id="j2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>10.作业船舶停靠位置是否正确</h6>
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
							<td class="col-md-8">
								<h6>11.按照《船/岸检查表》的内容与船方进行各项检查并签字。商定卸货程序</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="l" id="l1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="l" id="l2" value=""> 否
										</label>
										<div class="lDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDDOCK">
												<Button style="padding: 3px 8px;" key="1"
													class="btn btn-primary isModify spBtn" id="lSureBtn">确认</Button>
											</shiro:hasPermission>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>12.装卸物料接口处围油堰的所有孔是否已堵塞</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="m" id="m1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="m" id="m2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>13.码头作业人员是否已触摸人体静电释放装置，泄放人体静电</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="n" id="n1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="n" id="n2" value=""> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>14.船岸相关管线及阀门开启是否正确</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="o" id="o1" value="1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="o" id="o2" value="2"> 否
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
						<shiro:hasPermission name="ANOTICEDODOCK">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDDOCK">
							<button type="button" class="btn btn-success " key="1" status="0"
								id="saveBeforeTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDODOCK">
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
								<h6>1.卸货压力是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="p" id="p1" value="1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="p" id="p2" value="2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>2.相关管线及阀门是否无跑冒滴漏</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="q" id="q1" value="3"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="q" id="q2" value="4"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>3.软管及缆绳是否及时调整</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="r" id="r1" value="5"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="r" id="r2" value="6"> 否
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
						<shiro:hasPermission name="ANOTICEDODOCK">
							<div class="col-md-8 form-horizontal isNoModify">
								<div class="form-group">
									<label class="col-md-4 control-label">作业人员<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" id="inTaskUserId" readonly
											class="form-control" data-required="1" />
									</div>
								</div>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDISPATCHANDDOCK">
							<button type="button" class="btn btn-success" key="2" status="0"
								id="saveInTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDODOCK">
							<button type="button" class="btn isNoModify btn-primary" key="2"
								status="1" id="submitInTask">确认</button>
						</shiro:hasPermission>
					</div>
				</div>
				<div class="form-body">
					<table width="100%" class="afterTable">
						<tr>
							<td colspan="2"><h6>
									<strong>&nbsp;作业后检查</strong>&nbsp;<a href="javascript:void(0)"
										name='after' id="afterCheckAll"><i class="fa fa-square-o"></i></a>
								</h6></td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>1.船方管线是否经氮气吹扫（包括软管）</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="s" id="s1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="s" id="s2" value=""> 否
										</label><label class="checkbox-inline"> <input
											type="checkbox" name="s" id="s3" value=""> 不适用
										</label>
										<div class="sDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDDOCK">
												<button style="padding: 3px 8px;" type="button" key="3"
													class="btn isModify btn-primary spBtn" id="sSureBtn">确认</button>
											</shiro:hasPermission>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>2.是否进行过打回流操作或压管线操作或扫线操作</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list spCB">
										<label class="checkbox-inline"> <input type="checkbox"
											name="t" id="t1" value=""> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="t" id="t2" value=""> 否
										</label>
										<div class="tDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDDOCK">
												<button style="padding: 3px 8px;" type="button" key="3"
													class="btn btn-primary isModify spBtn" id="tSureBtn">确认</button>
											</shiro:hasPermission>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>3.管线阀门是否已经关闭（包括吹扫口）</h6>
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
							<td class="col-md-8">
								<h6>4.软管是否已经正确封堵盲板并及时复位</h6>
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
								<h6>5.物料是否及时回收入库并正确标识</h6>
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
								<h6>6.现场是否整理干净</h6>
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
								<h6>7.记录是否完善</h6>
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
							<td colspan="2">
								<div class="col-md-12 aftercheck"></div>
							</td>
						</tr>
					</table>
					<div class="modal-footer isModify afterDiv">
						<shiro:hasPermission name="ANOTICEDODOCK">
							<div class="col-md-8 form-horizontal isNoModify">
								<div class="form-group">
									<label class="col-md-4 control-label">作业人员<span
										class="required">*</span></label>
									<div class="col-md-4">
										<input type="text" id="afterTaskUserId" readonly
											class="form-control " data-required="1" />
									</div>
								</div>
							</div>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDISPATCHANDDOCK">
							<button type="button" class="btn btn-success" key="3" status="0"
								id="saveAfterTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDODOCK">
							<button type="button" class="btn btn-primary isNoModify" key="3"
								status="1" id="submitAfterTask">确认</button>
						</shiro:hasPermission>
					</div>
					<div class="col-md-12">
						<label class="col-md-10 col-md-offset-2 control-label">备注：外轮由调度确认，内轮由码头操作工确认</label>
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
