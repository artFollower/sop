
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
			<shiro:hasPermission name="ANOTICESTORETUBECLEANEXEL">
				<a class="hidden-print" style="color: white;"
					onclick="notify.exportXML(this,18)"> <i
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
						<td class="col-md-12"><div class="col-md-12 form-group">
								<div class="col-md-6">
									<label class="col-md-4 control-label">管线号:</label>
									<div class="col-md-8 tubeId">
										<input class="form-control" maxlength="64" id="tubeId">
									</div>
								</div>
								<div class="col-md-6">
									<label class="col-md-4 control-label">物料名称:</label>
									<div class="col-md-8">
										<input class="form-control" maxlength="64" id="productId">
									</div>
								</div>
							</div></td>
					</tr>
					<tr>
						<td colspan="2"><h5>&nbsp;一、清洗流程:</h5></td>
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
								&nbsp;二、作业要求:&nbsp;<a href="javascript:void(0);"
									onclick="notify.dialogShowLog(this,6)"><i
									class="fa fa-file-text-o"></i></a> &nbsp;<a
									href="javascript:void(0);" onclick="notify.saveLog(this,6)"><i
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
								&nbsp;三、作业注意事项:&nbsp;<a href="javascript:void(0)"
									onclick="InboundOperation.openWarning(1);"><i
									class="fa fa-question-circle"></i></a>
							</h5></td>
					</tr>
					<tr>
						<td colspan="2" class="dialog-warning1"><h6>
								<p>&nbsp;&nbsp;1、清洗管线时作业人员要佩戴防护眼镜,使用乳胶手套;</p>
								<p>&nbsp;&nbsp;2、在扫线时需佩戴护耳罩,防止放气时的噪声损伤耳朵;</p>
								<p>&nbsp;&nbsp;3、工艺管线在放气时人员要站在上风向;</p>
								<p>&nbsp;&nbsp;4、在低位排污口放料时需用软管连接后放入铁桶内,且在放料时要加强关注防止物料溢出桶外</p>
								<p>&nbsp;&nbsp;5、清洗产生的废水及时收集后排入污水处理站,严禁排入江内;</p>
								<p>&nbsp;&nbsp;6、作业过程与调度保持联系,及时汇报现场情况、</p>
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
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;正确佩戴个人防护用品</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">2、氮气压力低</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;扫线球卡堵在管线内</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;作业前准备好气源</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">3、吹扫口接口泄漏</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;氮气泄露、物料飞溅</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;作业前做好扫管线的连接</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">4、阀门丝杆处物料泄露</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;做好检查、及时封堵</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">5、管线腐蚀造成物料泄露</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;做好管线沿线的巡回检查工作</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">6、放错扫线球</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;卡球或球不走</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;严格执行调度指令,调度做好确认工作</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">7、低位放料</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;污染环境</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;加强关注防止物料溢出桶外</h6></td>
							</tr>
							<tr>
								<td class="col-md-4"><h6 style="text-align: left">8、放气</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;窒息、噪声</h6></td>
								<td class="col-md-4"><h6 style="text-align: left">&nbsp;人员要站在上风向、佩戴好护耳罩</h6></td>
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
					style="font-size: 13px; color: #333333; margin-left: 3px;">五、作业过程检查:</div>
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
								<h6>3、管线、阀门是否正常</h6>
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
								<h6>4、低位排污口盛接铁桶是否到位</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="d" id="d1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="d" id="d2" value="option2"> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="d" id="d3" value="option2"> 不适用
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>5、软管与移动槽车是否已连接并固定</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="e" id="e1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="e" id="e2" value="option2"> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="e" id="e3" value="option2"> 不适用
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>6、扫线球是否放置正确</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="f" id="f1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="f" id="f2" value="option2"> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="f" id="f3" value="option2"> 不适用
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>7、低位排污口是否闷堵</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="g" id="g1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="g" id="g2" value="option2"> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="g" id="g3" value="option2"> 不适用
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
						<shiro:hasPermission name="ANOTICEDOSTORETUBECLEAN">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDSTORETUBECLEAN">
							<button type="button" class="btn btn-success " key="1" status="0"
								id="saveBeforeTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOSTORETUBECLEAN">
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
								<h6>1、管线出水是否正常</h6>
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
								<h6>2、压力是否正常</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="i" id="i1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="i" id="i2" value="option2"> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="i" id="i3" value="option2"> 不适用
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
											name="j" id="j1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="j" id="j2" value="option2"> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="j" id="j3" value="option3"> 不适用
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>4、管线、阀门是否无跑冒滴漏</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="k" id="k1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="k" id="k2" value="option2"> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="k" id="k3" value="option2"> 不适用
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>5、管线低位物料是否已放空并回收</h6>
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
								<h6>6、管线低位污水是否已放空并回收</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="v" id="v1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="v" id="v2" value="option2"> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="v" id="v3" value="option2"> 不适用
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
						<shiro:hasPermission name="ANOTICEDOSTORETUBECLEAN">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDSTORETUBECLEAN">
							<button type="button" class="btn btn-success" key="2" status="0"
								id="saveInTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOSTORETUBECLEAN">
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
								<h6>1、管线低位是否已干燥无水</h6>
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
							<td class="col-md-8">
								<h6>2、管线阀门是否关闭</h6>
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
								<h6>3、吹扫接口是否封堵</h6>
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
								<h6>4、管线低位是否已封堵</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="p" id="p1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="p" id="p2" value="option2"> 否
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="p" id="p3" value="option2"> 不适用
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>5、槽车内污水是否回收</h6>
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
							<td class="col-md-8">
								<h6>6、物料是否吊装入库</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="r" id="r1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="r" id="r2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>7、氮气阀门是否关闭</h6>
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
							<td class="col-md-8">
								<h6>8、现场是否整理干净</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="t" id="t1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="t" id="t2" value="option2"> 否
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="col-md-8">
								<h6>9、记录是否完善</h6>
							</td>
							<td class="col-md-4">
								<div class="form-group">
									<div class="checkbox-list">
										<label class="checkbox-inline"> <input type="checkbox"
											name="u" id="u1" value="option1"> 是
										</label> <label class="checkbox-inline"> <input
											type="checkbox" name="u" id="u2" value="option2"> 否
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
						<shiro:hasPermission name="ANOTICEDOSTORETUBECLEAN">
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
						<shiro:hasPermission name="ANOTICEDISPATCHANDSTORETUBECLEAN">
							<button type="button" class="btn btn-success" key="3" status="0"
								id="saveAfterTask">保存</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="ANOTICEDOSTORETUBECLEAN">
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