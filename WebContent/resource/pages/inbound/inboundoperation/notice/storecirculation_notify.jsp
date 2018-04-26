<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<style type="text/css">
.modal-dialog {
	margin: 30px auto;
	width: 750px;
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
	<div class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h5 class="modal-title">&nbsp;打循环作业通知单(库区)</h5>
				</div>
				<div class="modal-body notice-modalbody" style="padding-left: 35px; padding-right: 35px;">
				<div class="input-group col-md-12">
				<label class="col-md-2 control-label"  style="text-align:right;">日期:</label>
			         <input style="text-align:left;border:0;" id="createTime"  class="date-picker col-md-3"  type="text"/>
				<label class="col-md-2 col-md-offset-2 control-label" style="text-align:right;">编号:</label>
				<label class="col-md-3 control-label" key="0" style="text-align:left" id="code"></label>
			    </div>
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="tools">
								<a class="hidden-print" style="color: white;" onclick="InboundOperation.exportXML(this,14)"> <i class="fa fa-file-excel-o">&nbsp;导出</i>
								</a>
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
													<textarea class="form-control" rows="3" id="taskMsg"></textarea>
											</td>
										</tr>
										<tr>
											<td colspan="2"><h5>&nbsp;二.作业工艺流程</h5></td>
										</tr>
										<tr>
										<td class="col-md-12 ">
										<div><div  id="zoomInSVG" style="float:left; background-color: transparent; border: 1px solid gray; text-align: center; font-size: 10px; width: 16px; height: 16px; left: 0px; top: 0px;">+</div>
											<div id="zoomOutSVG" style="float:left; background-color: transparent; border: 1px solid gray; text-align: center; font-size: 10px; width: 16px; height: 16px; left: 16px; top: 0px;">-</div></div>
											</td>
									</tr>
										<tr>
											<td class="col-md-12">
													<div  id="contentDiv" style="border: 1px solid #d8d8d8;position: relative; overflow: auto; cursor: move; width: 631px; height: 350px;"></div>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<div class="col-md-12 surecheck"></div>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<div class="modal-footer sureDiv">
													<div class="col-md-8 form-horizontal">
														<div class="form-group">
															<label class="col-md-4 control-label">调度<span
																class="required">*</span> </label>
															<div class="col-md-4">
																<input type="text" id="sureTaskUserId" readonly
																	class="form-control" data-required="1" />
															</div>
														</div>
													</div>
													<button type="button" class="btn btn-success" key="19"
														status="0" id="saveSureTask">保存</button>
													<button type="button" class="btn btn-primary" key="19"
														status="1" id="submitSureTask">确认</button>
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="2"><h5>
													&nbsp;三、作业注意事项:&nbsp;<a href="javascript:void(0)" onclick="InboundOperation.openWarning(1);"><i class="fa fa-question-circle"></i></a>
												</h5></td>
										</tr>
										<tr>
											<td colspan="2" class="dialog-warning1" hidden="true"><h6>
													<p>&nbsp;&nbsp;1、正确佩戴劳动防护用品</p>
													<p>&nbsp;&nbsp;2、注意管线压力变化；</p>
													<p>&nbsp;&nbsp;3、检查管线阀门状态是否正确;</p>
													<p>&nbsp;&nbsp;4、做好储罐附件和管线沿线的检查</p>
												</h6></td>
										</tr>
									</table>
								</div>
							</form>
							<!-- END FORM-->

							<div class="portlet box white notice-wn">
								<div class="portlet-title">
									<div class="caption" style="font-size: 13px; color: #333333;margin-left:3px;">
										四、作业中的风险分析:&nbsp;<a href="javascript:void(0)" onclick="InboundOperation.openWarning(2);"><i class="fa fa-question-circle"></i></a>
									</div>
								</div>
								<div class="portlet-body form dialog-warning2" hidden="true">
									<form action="#" class="form-horizontal">
										<div class="form-body">
											<table width="100%" class="table table-condensed table-striped table-bordered notice-table">
												<tr>
													<td class="col-md-4"><h6 style="text-align: center">危害或潜在事件</h6></td>
													<td class="col-md-4"><h6 style="text-align: center">主要后果</h6></td>
													<td class="col-md-4"><h6 style="text-align: center">安全措施</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">1、未按规定佩戴劳动防护用品</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;人身伤害</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确佩戴个人防护用品</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">2、接错管线</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;影响品质、管线涨压</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;调度做好核对确认工作</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">3、吹扫口阀未关好或未封好</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;物料泄漏</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;开阀门前做好检查工作</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">4、阀门不严密造成物料泄漏</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;物料泄漏</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;做好巡回检查</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">5、管线腐蚀穿孔</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;物料泄漏</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;做好巡回检查</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">6、开错储罐阀门</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;发错物料、影响品质</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;认真按作业单进行操作，调度加强核对</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">7、呼吸阀故障</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;储罐损坏</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;作业过程中对呼吸阀工作情况进行检查</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">8、SCADA系统液位异常</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;无法监控液位、流量</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;和计量人员做好核对工作</h6></td>
												</tr>
												<tr>
													<td class="col-md-4"><h6 style="text-align: left">9、泵运行故障</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;引发事故</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;做好保养、作业中加强检查</h6></td>
												</tr>
                                                <tr>
													<td class="col-md-4"><h6 style="text-align: left">10、管线内气阻造成泵压力异常升高</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;损坏泵及管线引发事故</h6></td>
													<td class="col-md-4"><h6 style="text-align: left">&nbsp;加强操作人员加强现场观察，有异常是及时汇报调度</h6></td>
												</tr>
											</table>
										</div>
									</form>
								</div>
							</div>
							<!--  -->
							<div class="portlet box white">
								<div class="portlet-title">
									<div class="caption" style="font-size: 13px; color: #333333;margin-left:3px;">五、作业过程的检查</div>
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
													<h6>1、个人防护用品是否正确佩戴</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="a" id="a1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="a" id="a2" value="option2"> 否
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
															<label class="checkbox-inline"> <input type="checkbox" name="b" id="b1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="b" id="b2" value="option2"> 否
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
															<label class="checkbox-inline"> <input type="checkbox" name="c" id="c1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="c" id="c2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>4、SCADA系统是否正常</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list spCB">
															<label class="checkbox-inline"> <input type="checkbox" name="d" id="d1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="d" id="d2" value="option2"> 否
															</label>
														<div class="dDiv" style="float:right;">
														<button style="padding:3px 8px;" type="button" key="20" class="btn btn-primary spBtn" id="dSureBtn">确认</button>
														</div>
														</div>
													</div>
												</td>
											</tr>
										<tr><td colspan="2">
									<div class="col-md-12 beforecheck">
									</div>
										</td></tr>
										</table>
										<div class="modal-footer beforeDiv">
										<div class="col-md-8 form-horizontal">
									   <div class="form-group">
									   <label class="col-md-4 control-label">工作人员：<span class="required">*</span></label>
                                        <div class="col-md-4">
                                        <input type="text"  id="beforeTaskUserId" class="form-control" readonly data-required="1" />
                                        </div>     									   
									   </div>
									   </div>
										  <button type="button" class="btn btn-success" key="20" status="0" id="saveBeforeTask">保存</button>
										<button type="button" class="btn btn-primary" key="20" status="1" id="submitBeforeTask">确认</button>
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
													<h6>1、中控室SCADA系统液位是否正常</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list spCB">
															<label class="checkbox-inline"> <input type="checkbox" name="e" id="e1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="e" id="e2" value="option2"> 否
															</label>
														<div class="eDiv" style="float:right;">
														<button style="padding:3px 8px;" type="button" key="21" class="btn btn-primary spBtn" id="eSureBtn">确认</button>
														</div>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>2、中控室SCADA系统流量是否正常</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list spCB">
															<label class="checkbox-inline"> <input type="checkbox" name="f" id="f1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="f" id="f2" value="option2"> 否
															</label>
														<div class="fDiv" style="float:right;">
														<button style="padding:3px 8px;" type="button" key="21" class="btn btn-primary spBtn" id="fSureBtn">确认</button>
														</div>
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
															<label class="checkbox-inline"> <input type="checkbox" name="g" id="g1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="g" id="g2" value="option2"> 否
															</label> 
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>4、管线阀门是否无跑冒滴漏</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="h" id="h1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="h" id="h2" value="option2"> 否
															</label> <label class="checkbox-inline"> <input type="checkbox" name="h" id="h3" value="option2"> 不适用
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
										<div class="modal-footer inDiv">
										<div class="col-md-8 form-horizontal">
									   <div class="form-group">
									   <label class="col-md-4 control-label">工作人员：<span class="required">*</span></label>
                                        <div class="col-md-4">
                                        <input type="text"  id="inTaskUserId" class="form-control" readonly data-required="1"/>
                                        </div>     									   
									   </div>
									   </div>
											<button type="button" class="btn btn-success" key="21" status="0" id="saveInTask">保存</button>
										<button type="button" class="btn btn-primary" key="21" status="1" id="submitInTask">确认</button>
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
													<h6>1、管线是否泄压</h6>
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
													<h6>2、吹扫接口是否封堵</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="j" id="j1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="j" id="j2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>3、物料是否正确回收并标记入库</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="k" id="k1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="k" id="k2" value="option2"> 否
															</label>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="col-md-8">
													<h6>4、现场是否整理干净</h6>
												</td>
												<td class="col-md-4">
													<div class="form-group">
														<div class="checkbox-list">
															<label class="checkbox-inline"> <input type="checkbox" name="l" id="l1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="l" id="l2" value="option2"> 否
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
															<label class="checkbox-inline"> <input type="checkbox" name="m" id="m1" value="option1"> 是
															</label> <label class="checkbox-inline"> <input type="checkbox" name="m" id="m2" value="option2"> 否
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
										<div class="modal-footer afterDiv">
										<div class="col-md-8 form-horizontal">
									   <div class="form-group">
									   <label class="col-md-4 control-label">工作人员：<span class="required">*</span></label>
                                        <div class="col-md-4">
                                        <input type="text" id="afterTaskUserId" class="form-control" readonly data-required="1" />
                                        </div>     									   
									   </div>
									   </div>
											<button type="button" class="btn btn-success" key="22" status="0" id="saveAfterTask">保存</button>
										<button type="button" class="btn btn-primary" key="22" status="1" id="submitAfterTask">确认</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<input type="hidden" class="form-control" id="transportprogramId">
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
<!-- 						<shiro:hasPermission name="user:create"> -->
<!-- 							<button type="button" class="btn btn-success" id="save">保存</button> -->
<!-- 						</shiro:hasPermission> -->
<!-- 						<button type="button" class="btn btn-success" id="save">提交</button> -->
					</div>
				</div>
			</div>
		</div>
	</div>