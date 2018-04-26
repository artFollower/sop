<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
svg{
min-width:0;
min-height:0;
}
　tr.first{
     height:1px;
     font-size:1px;
     line-height:1px;
   } 
</style>
</head>
<div class="modal fade notice">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h5 class="modal-title">&nbsp;码头船发作业通知单</h5>
			</div>
			<div class="modal-body notice-modalbody" style="padding-left: 35px; padding-right: 35px;">
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
						<shiro:hasPermission name="ANOTICEDOCKSHIPEXEL">
							<a class="hidden-print" style="color: white;" onclick="notify.exportXML(this,29)"> <i class="fa fa-file-excel-o">&nbsp;导出</i>
							</a>
							</shiro:hasPermission>
						</div>
					</div>
					<div class="portlet-body ">
						<!-- BEGIN FORM-->
						<form action="#" class="form-horizontal form-bordered">
							<div class="form-body">
								<table width="100%">
									<tr>
										<td colspan="2"><h5>&nbsp;一.作业任务</h5></td>
									</tr>
									<tr>
										<td class="col-md-12">
											<div class="col-md-12" style="padding-left:0px;padding-right:0px;">
												<textarea class="form-control" rows="3" id="taskMsg" ></textarea>
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="2"><h5>&nbsp;二.作业工艺流程</h5></td>
									</tr>
									<tr>
										<td >
											<div class="col-md-12" style="overflow: hidden;padding-left:0px;padding-right:0px;">
											<div id="contentDiv" class="content"  style="border: 1px solid rgb(216, 216, 216); position: relative; overflow-x: hidden; overflow-y: auto; cursor: move; width: 631px; height: 350px;">
                      						</div>
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<h5>
												&nbsp;三.作业要求&nbsp;<a href="javascript:void(0);" onclick="notify.dialogShowLog(this,11)"><i class="fa fa-file-text-o"></i></a>&nbsp;<a onclick="notify.saveLog(this,11)" href="javascript:void(0);" title="保存"><i class="fa fa-floppy-o"></i></a>
											</h5>
										</td>
									</tr>
									<tr>
										<td class="col-md-12">
											<div class="col-md-12" style="padding-left:0px;padding-right:0px;">
												<textarea class="form-control  assignwork" rows="1" id="taskRequire"></textarea>
											</div>
										</td>
									</tr>
									<tr><td colspan="2">
									<div class="col-md-12 surecheck">
									</div>
										</td></tr>
									<tr><td colspan="2">
									<shiro:hasPermission name="ANOTICEDISPATCH">
									<div class="modal-footer isModify sureDiv">
									   <div class="col-md-8 form-horizontal isNoModify">
									   <div class="form-group">
									   <label class="col-md-4 control-label">调度<span class="required">*</span></label>
                                        <div class="col-md-4">
                                        <input type="text"  id="sureTaskUserId" disabled="disabled" class="form-control" data-required="1"/>
                                        </div>     									   
									   </div>
									   </div>
										<button type="button" class="btn btn-success" key="0" status="0" id="saveSureTask" data-required="1">保存</button>
										<button type="button" class="btn btn-primary isNoModify" key="0" status="1" id="submitSureTask" data-required="1">确认</button>
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
												<p>&nbsp;&nbsp;1.正确佩戴个人防护用品；</p>
												<p>&nbsp;&nbsp;2.管线、船名是否与作业要求一致；</p>
												<p>&nbsp;&nbsp;3.关注船舶干弦，注意风力变化；</p>
												<p>&nbsp;&nbsp;4.关注作业船舶周边水域情况；</p>
												<p>&nbsp;&nbsp;5.作业期间加强与船方联系，防止发生溢料。</p>
												<p>&nbsp;&nbsp;6.发生异常情况及时与调度联系。</p>
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
												<td class="col-md-4"><h6 style="text-align: center">危险和潜在的事件</h6></td>
												<td class="col-md-4"><h6 style="text-align: center">主要后果</h6></td>
												<td class="col-md-4"><h6 style="text-align: center">安全措施</h6></td>
											</tr>
											<tr>
												<td class="col-md-4"><h6 style="text-align: left">1、个人防护用品不适用或未正确佩戴</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;人身伤害</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确佩戴使用的防护用品</h6></td>
											</tr>
											<tr>
												<td class="col-md-4"><h6 style="text-align: left">2、使用非防爆工具，产生火花</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;火灾、爆炸</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;使用防爆工具</h6></td>
											</tr>
											<tr>
												<td class="col-md-4"><h6 style="text-align: left">3、发生异常情况时现场无应急设施</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;火灾、爆炸、污染环境</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;按要求正确放置消防及防污染设施</h6></td>
											</tr>
											<tr>
												<td class="col-md-4"><h6 style="text-align: left">4、人体静电未释放接触可燃气体</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;火灾、爆炸</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;进入作业场所前释放人体静电</h6></td>
											</tr>
											<tr>
												<td class="col-md-4"><h6 style="text-align: left">5、作业人员接错管线</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;发错料，泵、管线损坏</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;根据作业通知单认真检查管线状态，与调度保持联系、确认。</h6></td>
											</tr>
											<tr>
												<td class="col-md-4"><h6 style="text-align: left">6、油品船未布设围油栏</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;按要求</h6></td>
											</tr>
											<tr>
												<td class="col-md-4"><h6 style="text-align: left">7、超发引起船上溢料</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;码头保持与船方联系，调度加强监控，防止超发。</h6></td>
											</tr>
											<tr>
												<td class="col-md-4"><h6 style="text-align: left">8、管线上阀门丝杆处泄漏</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;使用不锈钢器具及时盛接、回收</h6></td>
											</tr>
											<tr>
												<td class="col-md-4"><h6 style="text-align: left">9、软管没有正确连接，扭曲损坏造成物料渗漏</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境</h6></td>
												<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确连接软管，加强检查</h6></td>
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
													<strong>&nbsp;作业前检查</strong>&nbsp;<a
															href="javascript:void(0)" name="before" id="beforeCheckAll"><i
															class="fa fa-square-o"></i> </a></h6></td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>1.个人防护用品是否正确佩戴</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox" name="a"  id="a1" > 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="a" id="a2"> 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>2.通讯系统是否通畅</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox" name="b"  id="b1" value="b"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="b"  id="b2"> 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>3.消防器材是否到位</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox" name="c"  id="c1" value="c"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="c" id="c2"> 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>4.是否释放身体静电</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox" name="d"  id="d1" value="d"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="d" id="d2"> 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>5.确认工艺流程是否正确</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox" name="e"  id="e1" value="e"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="e" id="e2"> 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>6.确认船方是否处于适装状态</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox" name="f"  id="f1" value="f"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="f" id="f2"> 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>7.管线是否与船上接口安全可靠连接</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox" name="g"  id="g1" value="g"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="g" id="g2"> 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>8.装货船舶周边水域是否安全</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox" name="h"  id="h1" > 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="h" id="h2" > 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>9.中控室SCADA系统运行是否正常</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list spCB">
														<label class="checkbox-inline"> <input type="checkbox" name="i"  id="i1" value="i"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="i"  id="i2" > 否
														</label>
														<div class="iDiv" style="float: right;">
											<shiro:hasPermission name="ANOTICEDISPATCHANDDOCKSHIP">
												<button style="padding: 3px 8px;" type="button" key="1"
													class="btn btn-primary isModify spBtn" id="iSureBtn">确认</button>
											</shiro:hasPermission>
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
									<div class="modal-footer isModify beforeDiv" >
									<shiro:hasPermission name="ANOTICEDODOCKSHIP">
									   <div class="col-md-8 form-horizontal isNoModify">
									   <div class="form-group">
									   <label class="col-md-4 control-label">作业人员：<span class="required">*</span></label>
                                        <div class="col-md-4">
                                        <input type="text"  id="beforeTaskUserId" disabled="disabled" class="form-control " data-required="1" />
                                        </div>     									   
									   </div>
									   </div>
									   </shiro:hasPermission>
									   <shiro:hasPermission name="ANOTICEDISPATCHANDDOCKSHIP">
										<button type="button" class="btn btn-success" key="1" status="0" id="saveBeforeTask">保存</button>
										</shiro:hasPermission>
										<shiro:hasPermission name="ANOTICEDODOCKSHIP">
										<button type="button" class="btn btn-primary isNoModify" key="1" status="1" id="submitBeforeTask">确认</button>
										</shiro:hasPermission>
									</div>
								</div>
								<div class="form-body">
									<table width="100%" class="inTable">
										<tr>
											<td colspan="2"><h6>
													<strong>&nbsp;作业中检查</strong>&nbsp;<a
															href="javascript:void(0)" name="in" id="inCheckAll"><i
															class="fa fa-square-o"></i> </a></h6></td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>1.发货过程是否正常</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox"  name="j" id="j1" value="a"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="j" id="j2" value="2"> 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>2.装货船舶周边水域是否安全</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox"  name="k" id="k1" value="b"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="k" id="k2" value="4"> 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>3.是否无物料滴漏</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox"  name="l" id="l1" value="c"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="l" id="l2" value="6"> 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>4.是否按要求进行巡检</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox"  name="m" id="m1" value="d"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="m" id="m2" value="6"> 否
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
									<div class="modal-footer isModify inDiv">
									<shiro:hasPermission name="ANOTICEDODOCKSHIP">
										<div class="col-md-8 form-horizontal isNoModify">
										   <div class="form-group">
										   <label class="col-md-4 control-label">作业人员：<span class="required">*</span></label>
	                                        <div class="col-md-4">
	                                        <input type="text"   id="inTaskUserId" disabled="disabled" class="form-control" data-required="1" />
	                                        </div>     									   
										   </div>
									   </div>
									   </shiro:hasPermission>
									   <shiro:hasPermission name="ANOTICEDISPATCHANDDOCKSHIP">
										<button type="button" class="btn btn-success" key="2" status="0" id="saveInTask">保存</button>
										</shiro:hasPermission>
										<shiro:hasPermission name="ANOTICEDODOCKSHIP">
										<button type="button" class="btn btn-primary isNoModify" key="2" status="1" id="submitInTask">确认</button>
										</shiro:hasPermission>
									</div>
								</div>
								<div class="form-body">
									<table width="100%" class="afterTable">
										<tr>
											<td colspan="2"><h6>
													<strong>&nbsp;作业后检查</strong>&nbsp;<a
															href="javascript:void(0)" name="after" id="afterCheckAll"><i
															class="fa fa-square-o"></i> </a></h6></td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>1.阀门是否关闭</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox"   name="n" id="n1" value="a"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="n" id="n2" value=""> 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>2.管线是否拆除复位，并加封盲板</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox"  name="o" id="o1" value="b"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="o" id="o2" value=""> 否
														</label>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td class="col-md-8">
												<h6>3.管线是否泄压</h6>
											</td>
											<td class="col-md-4">
												<div class="form-group">
													<div class="checkbox-list">
														<label class="checkbox-inline"> <input type="checkbox"  name="p" id="p1" value="c"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="p" id="p2" value=""> 否
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
														<label class="checkbox-inline"> <input type="checkbox"  name="q" id="q1" value="d"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="q" id="q2" value=""> 否
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
														<label class="checkbox-inline"> <input type="checkbox"  name="r" id="r1" value="e"> 是
														</label> <label class="checkbox-inline"> <input type="checkbox" name="r" id="r2" value=""> 否
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
									<div class="modal-footer isModify afterDiv">
									<shiro:hasPermission name="ANOTICEDODOCKSHIP">
									    <div class="col-md-8 form-horizontal isNoModify">
										   <div class="form-group">
										      <label class="col-md-4 control-label">作业人员：<span class="required">*</span></label>
	                                          <div class="col-md-4">
	                                              <input type="text"   id="afterTaskUserId" disabled="disabled" class="form-control " data-required="1" />
	                                          </div>     									   
										   </div>
									    </div>
									    </shiro:hasPermission>
									    <shiro:hasPermission name="ANOTICEDISPATCHANDDOCKSHIP">
										<button type="button" class="btn btn-success" key="3" status="0" id="saveAfterTask">保存</button>
										</shiro:hasPermission>
										<shiro:hasPermission name="ANOTICEDODOCKSHIP">
										<button type="button" class="btn btn-primary isNoModify" key="3" status="1" id="submitAfterTask">确认</button>
										</shiro:hasPermission>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
				     <label class="hidden"  id="transportId"></label>
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
<script>



</script>