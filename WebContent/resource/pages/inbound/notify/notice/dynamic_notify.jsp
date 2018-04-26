<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<style type="text/css">
.modal-dialog {
	margin: 30px auto;
	width: 65%;
}

.portlet>.portlet-title>.caption {
	font-size: 18px;
}

.notice-table  tbody>tr>td {
	padding: 0px;
}

.notice-wn {
	margin-bottom: 0px
}

.notice-modalbody {
	padding-top: 0px;
	position: relative;
}
</style>
</head>
<body>
	<div class="modal fade notice">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h5 class="modal-title">&nbsp;动力班接卸作业通知单</h5>
				</div>
				<div class="modal-body notice-modalbody" style="padding-left: 35px; padding-right: 35px;">
				<div class="input-group col-md-12">
				<label class="col-md-2 control-label"  style="text-align:right;">日期:</label>
			         <input style="text-align:center;border: 1px solid #ccc" id="createTime"  class="date-picker col-md-3"  type="text"/>
				<label class="col-md-2 col-md-offset-2 control-label" style="text-align:right;">编号:</label>
				<label class="col-md-3 control-label" key="0" style="text-align:left" id="code"></label>
			    </div>
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="tools">
							 <shiro:hasPermission name="ANOTICEDYNAMICEXEL">
								<a class="hidden-print" style="color: white;" onclick="notify.exportXML(this,27)"> <i class="fa fa-file-excel-o">&nbsp;导出</i>
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
								<label class="control-label hidden" id="transportId"></label>
									<table width="100%">
										<tr>
											<td colspan="2"><h5>&nbsp;一.作业任务</h5></td>
										</tr>
										<tr>
											<td class="col-md-12">
													<textarea class="form-control" maxlength="150" rows="3" id="taskMsg"></textarea>
											</td>
										</tr>
										<tr>
											<td colspan="2"><h5>&nbsp;二.作业工艺流程</h5></td>
										</tr>
										<!-- <tr>
										<td class="col-md-12 ">
										<div><div  id="zoomInSVG" style="float:left; background-color: transparent; border: 1px solid gray; text-align: center; font-size: 10px; width: 16px; height: 16px; left: 0px; top: 0px;">+</div>
											<div id="zoomOutSVG" style="float:left; background-color: transparent; border: 1px solid gray; text-align: center; font-size: 10px; width: 16px; height: 16px; left: 16px; top: 0px;">-</div></div>
											</td>
									</tr> -->
										<tr>
											<td class="col-md-12">
													<div id="contentDiv" class="content" style="border: 1px solid #d8d8d8;position: relative;  cursor: move; width: 631px; height: 350px;"></div>
											</td>
										</tr>
										<tr>
											<td colspan="2"><h5>
													&nbsp;三.作业要求&nbsp;<a href="javascript:void(0);" onclick="notify.dialogShowLog(this,2)"><i class="fa fa-file-text-o"></i></a>
													&nbsp;<a href="javascript:void(0);" onclick="notify.saveLog(this,2)" id="saveRequire"><i class="fa fa-floppy-o"></i></a>
												</h5></td>
										</tr>
										<tr>
											<td class="col-md-12">
													<textarea class="form-control assignwork" maxlength="150" rows="2" id="taskRequire"></textarea>
											</td>
										</tr>
										<tr><td colspan="2">
									<div class="col-md-12 surecheck">
									</div>
										</td></tr>
									<tr><td colspan="2">
									<shiro:hasPermission name="ANOTICEDISPATCH">
									<div class="modal-footer sureDiv isModify">
									   <div class="col-md-8 form-horizontal isNoModify">
									   <div class="form-group">
									   <label class="col-md-4 control-label">调度<span class="required">*</span></label>
                                        <div class="col-md-4">
                                        <input type="text"  id="sureTaskUserId"  class="form-control" readonly data-required="1"/>
                                        </div>     									   
									   </div>
									   </div>
									<button type="button" class="btn btn-success" key="0" status="0" id="saveSureTask">保存</button>
										<button type="button" class="btn btn-primary isNoModify"  key="0" status="1" id="submitSureTask">确认</button>
									</div>
									</shiro:hasPermission>
									</td></tr>
										<tr>
											<td colspan="2"><h5>
													&nbsp;四.作业注意事项&nbsp;<a href="javascript:void(0)" onclick="InboundOperation.openWarning(1);"><i class="fa fa-question-circle"></i></a>
												</h5></td>
										</tr>
										<tr>
											<td colspan="2" class="dialog-warning1"><h6>
													<p>&nbsp;&nbsp;1.正确佩戴个人防护用品</p>
													<p>&nbsp;&nbsp;2.储罐应开进口阀，泄压阀应关闭；</p>
													<p>&nbsp;&nbsp;3.卸货时检查罐呼吸阀是否正常，如有异常及时与调度联系;</p>
													<p>&nbsp;&nbsp;4.卸货过程中加强检查，及时与调度联系</p>
												</h6></td>
										</tr>
									</table>
								</div>
							</form>
							<!-- END FORM-->

							<div class="portlet box white notice-wn">
								<div class="portlet-title">
									<div class="caption" style="font-size: 13px; color: #333333;margin-left:3px;">
										五.作业中的风险分析&nbsp;<a href="javascript:void(0)" onclick="InboundOperation.openWarning(2);"><i class="fa fa-question-circle"></i></a>
									</div>
								</div>
								<div class="portlet-body form dialog-warning2">
									<form action="#" class="form-horizontal">
										<div class="form-body">
											<table width="100%" class="table table-condensed table-striped table-bordered notice-table">
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
													<td class="col-md-4"><h6 style="text-align: left">2、管道腐蚀、垫片损坏造成物料泄露</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境、引发火灾爆炸</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;加强巡检、系统报警时及时查明原因</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">3、未正确启闭阀门，造成管线憋压损坏引起物料泄漏</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境、引发火灾爆炸</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;根据调度作业通知单正确启闭阀门，加强核对</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">4、开错阀门造成物料串罐</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;造成冒罐引发火灾爆炸</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;根据调度作业通知单正确启闭阀门，加强核对</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">5、未正确开启储罐阀门和关闭相关的泄压阀</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;阀门泄露及泄压阀被异物堵塞</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确开启相关阀门及关闭相关的泄压阀</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">6、进料时未及时复查、进货过程中未保持巡查</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;进料时阀门和管线泄露未能及时发现，而造成事故</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;在进料后进行相关储罐、管线的复检并在进货时保持巡查</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">7、上罐检查时人员跌倒、滑落、碰撞致伤</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;人身伤害</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;上罐时手扶扶梯</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">8、作业过程中呼吸阀异常造成储罐憋压</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;储罐胀裂物料泄漏</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;按规定在作业过程中对呼吸进行检查</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">9、结束后未及时关闭相关储罐阀门和及时开启相关的泄压阀</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;物料串罐或管线涨压</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;结束后及时与调度联系，关闭相关的储罐阀门，及时开启相应的泄压阀</h6></td>
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
									<div class="caption" style="font-size: 13px; color: #333333;margin-left:3px;">六.作业过程的检查</div>
								</div>
								<div class="portlet-body">
									<div class="form-body">
										<table width="100%" class="beforeTable">
											<tr>
												<td colspan="2"><h6>
														<strong>&nbsp;作业前检查</strong>&nbsp;<a href="javascript:void(0)" name="before" id="beforeCheckAll"><i class="fa fa-square-o"></i></a>
													</h6></td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>1.高位报警及阀门连锁测试是否正常</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list spCB">
															<label class="checkbox-inline"> <input type="checkbox" name="a" id="a1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="a" id="a2" value="option2"> 否
															</label>
															<div class="aDiv" style="float:right;">
															 <shiro:hasPermission name="ANOTICEDISPATCHANDDYNAMIC">
														<button style="padding:3px 8px;" type="button" key="1" class="btn btn-primary isModify spBtn" id="aSureBtn">确认</button>
														</shiro:hasPermission>
														</div>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>2.消防泵处于正常备用状态，检查确认消防设备，设施处于正常运行状态</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="b" id="b1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="b" id="b2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>3.确认消防管线内消防水压力处于稳压状态，所有阀位正确</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="c" id="c1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="c" id="c2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>4.通讯系统（对讲机，电话）处于良好状态，巡检工具准备完好</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="d" id="d1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="d" id="d2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>5.相关储罐及管线连接是否正确</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="e" id="e1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="e" id="e2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>6.相关储罐及管线的阀门开启是否正确</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="f" id="f1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="f" id="f2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
										<tr><td colspan="2">
									<div class="col-md-12 beforecheck">
									</div>
										</td></tr>
										</table>
										<div class="modal-footer isModify beforeDiv">
										<shiro:hasPermission name="ANOTICEDODYNAMIC">
										<div class="col-md-8 form-horizontal isNoModify">
									   <div class="form-group">
									   <label class="col-md-4 control-label">作业人员：<span class="required">*</span></label>
                                        <div class="col-md-4">
                                        <input type="text"  id="beforeTaskUserId" readonly class="form-control" data-required="1"/>
                                        </div>     									   
									   </div>
									   </div>
									   </shiro:hasPermission>
									   <shiro:hasPermission name="ANOTICEDISPATCHANDDYNAMIC">
										<button type="button" class="btn btn-success " key="1" status="0" id="saveBeforeTask">保存</button>
										</shiro:hasPermission>
										<shiro:hasPermission name="ANOTICEDODYNAMIC">
										<button type="button" class="btn btn-primary isNoModify" key="1" status="1" id="submitBeforeTask">确认</button>
										</shiro:hasPermission>
										</div>
									</div>
									<div class="form-body">
										<table width="100%" class="inTable">
											<tr>
												<td colspan="2"><h6>
														<strong>&nbsp;作业中检查</strong>&nbsp;<a href="javascript:void(0)" name="in" id="inCheckAll"><i class="fa fa-square-o"></i></a>
													</h6></td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>1.对罐区管线、低位排污、配管站、储罐呼吸阀等主要部位进行检查是否正常</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="h" id="h1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="h" id="h2" value="option2"> 否
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
															<label class="checkbox-inline"> <input type="checkbox" name="i" id="i1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="i" id="i2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>3.新封罐，进货后是否每隔半小时对人孔及排污口处进行检查?是否正常</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="j" id="j1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="j" id="j2" value="option2"> 否
															</label> <label class="checkbox-inline"> <input type="checkbox" name="j" id="j3" value="option2"> 不适用
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>4.相关管线上的压力表显示压力是否正常</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="k" id="k1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="k" id="k2" value="option2"> 否
															</label> <label class="checkbox-inline"> <input type="checkbox" name="k" id="k3" value="option2"> 不适用
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>5.如浮顶罐首次进料，浮盘是否已正常起升</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="l" id="l1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="l" id="l2" value="option2"> 否
															</label> <label class="checkbox-inline"> <input type="checkbox" name="l" id="l3" value="option2"> 不适用
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr><td colspan="2">
									<div class="col-md-12 incheck">
									</div>
										</td></tr>
										</table>
										<div class="modal-footer inDiv isModify">
										<shiro:hasPermission name="ANOTICEDODYNAMIC">
										<div class="col-md-8 form-horizontal isNoModify">
									   <div class="form-group">
									   <label class="col-md-4 control-label">作业人员：<span class="required">*</span></label>
                                        <div class="col-md-4">
                                        <input type="text"  id="inTaskUserId" class="form-control" readonly data-required="1"/>
                                        </div>     									   
									   </div>
									   </div>
									   </shiro:hasPermission>
									    <shiro:hasPermission name="ANOTICEDISPATCHANDDYNAMIC">
											<button type="button" class="btn btn-success" key="2" status="0" id="saveInTask">保存</button>
											</shiro:hasPermission>
											<shiro:hasPermission name="ANOTICEDODYNAMIC">
										<button type="button" class="btn btn-primary isNoModify" key="2" status="1" id="submitInTask">确认</button>
										</shiro:hasPermission>
										</div>
									</div>
									<div class="form-body">
										<table width="100%" class="afterTable">
											<tr>
												<td colspan="2"><h6>
														<strong>&nbsp;作业后检查</strong>&nbsp;<a href="javascript:void(0)" name="after" id="afterCheckAll"><i class="fa fa-square-o"></i></a>
													</h6></td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>1.储罐是否已结束处理</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="m" id="m1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="m" id="m2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>2.相关的泄压阀是否已打开</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="n" id="n1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="n" id="n2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>3.储罐人孔等法兰连接处是否无物料滴漏</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="o" id="o1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="o" id="o2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>4.记录是否完善</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="p" id="p1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="p" id="p2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr><td colspan="2">
									<div class="col-md-12 aftercheck">
									</div>
										</td></tr>			
										</table>
										<div class="modal-footer afterDiv isModify">
										<shiro:hasPermission name="ANOTICEDODYNAMIC">
										<div class="col-md-8 form-horizontal isNoModify">
									   <div class="form-group">
									   <label class="col-md-4 control-label">作业人员：<span class="required">*</span></label>
                                        <div class="col-md-4">
                                        <input type="text" id="afterTaskUserId" class="form-control" readonly data-required="1" />
                                        </div>     									   
									   </div>
									   </div>
									   </shiro:hasPermission>
									    <shiro:hasPermission name="ANOTICEDISPATCHANDDYNAMIC">
											<button type="button" class="btn btn-success" key="3" status="0" id="saveAfterTask">保存</button>
											</shiro:hasPermission>
											<shiro:hasPermission name="ANOTICEDODYNAMIC">
										<button type="button" class="btn btn-primary isNoModify" key="3" status="1" id="submitAfterTask">确认</button>
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
				</div>
			</div>
		</div>
	</div>