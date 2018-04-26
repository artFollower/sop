<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style>
.dockFeeBill {
	margin: 30px auto;
	width: 69%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog dockFeeBill">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">账单明细</h4>
			</div>
			<div class="modal-body" style="padding:0 24px">
			<div class="input-group col-md-12">
				<label class="col-md-2 control-label"  style="text-align:right;">账单日期:</label>
			         <input style="text-align:center;border: 1px solid #ccc" id="accountTime"  class="date-picker col-md-3"  type="text"/>
				<label class="col-md-2 col-md-offset-2 control-label" style="text-align:right;">账单号:</label>
				<label class="col-md-3 control-label feeKey" key="0" style="text-align:left" id="code"></label>
			    </div>
			    <div class="portlet box blue">
						<div class="portlet-title">
							 <div class="tools">
								<a class="hidden-print" style="color: white;" onclick="DockFeeBillDialog.exportDetailExcel(this)"><i class="fa fa-file-excel-o">&nbsp;导出</i>
								</a>
							</div> 
						</div>
			<div class="portlet-body">
				<form action="#" class="form-horizontal">
                     <div class="form-body">
                      <div class="row">
                      <label class="hidden feeKey" id="id"></label>
                      <label class="hidden" id="feeBillType"></label>
                       <label class="hidden" id="feeType"></label>
                      <label class="hidden" id="accountBillId"></label>
                      
                      <hr color="gray"  style="margin:5px auto;width:100%"/>

									<div class="billingCodeDiv  hideDiv">
										<div class="form-group">
											<div class="col-md-6">
												<label class="col-md-4 control-label">发票号:<span
											class="required">*</span></label>
												<div class="col-md-6 ">
													<input class="form-control feeKey" data-required="1"
														id="billingCode">
												</div>
											</div>
											<div class="col-md-6 ">
												<label class="col-md-4 control-label">开票日期:<span
											class="required">*</span></label>
												<div class="col-md-6 input-group">
													<input type="text" id="billingTime"
														class="form-control date-picker" data-required="1">
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class="col-md-6">
												<label class="col-md-4 control-label">开票类型:</label> <label
													class="col-md-6 control-label" style="text-align: left;"
													id="billType"></label>
											</div>
											<div class="col-md-6">
												<label class="col-md-4 control-label">未税金额(元):<span
											class="required">*</span></label>
												<div class="col-md-6 input-group">
													<input class="form-control "
														onkeyup="config.clearNoNum(this,2)"  data-required="1" id="unTaxFee">
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-6">
												<label class="col-md-4 control-label">税率:<span
											class="required">*</span></label>
												<div class="col-md-6 ">
													<input class="form-control" id="taxRate"  data-required="1"
														onkeyup="config.clearNoNum(this,3)">
												</div>
											</div>
											<div class="col-md-6">
												<label class="col-md-4 control-label">税额(元):<span
											class="required">*</span></label>
												<div class="col-md-6 input-group">
													<input class="form-control"
														onkeyup="config.clearNoNum(this,2)" data-required="1" id="taxFee">
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class="col-md-6">
												<label class="col-md-4 control-label">开票说明:</label>
												<div class="col-md-6 ">
													<input type="text" class="form-control" id="billingContent">
												</div>
											</div>
											<div class="col-md-6">
												<div class="col-md-offset-4" style="padding: 0 0px;">
												<shiro:hasPermission name="ADOCKFEEBILLBILLING">
													<button type="button" class="btn btn-primary " key="5" >确认开票</button>
													</shiro:hasPermission>
												</div>
											</div>
										</div>
									</div>

						<hr color="gray"  style="margin:5px auto;width:100%"/>  
						
                       <div class="form-group feeHeadDiv">
							<div class="col-md-6">
								<label class="col-md-4 control-label">开票抬头:<span
											class="required">*</span></label>
								<div class="col-md-6 input-group">
									<input class="form-control " data-required="1" id="feeHeadName">
								</div>
							</div>
							
						</div>

						<div class="form-group" style="margin-top: 25px;">
							<div class="scroller "
								style="max-height: 400px; overflow-y: scroll; margin-right: 35px;">
								<div class="col-md-12 chargeDetailDiv" style="padding-left: 35px;">
									<div class="col-md-12" id="fBChargeList"></div>
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="col-md-6 ">
								<label class="col-md-4 control-label ">已到账(元)：</label> <label
									class="col-md-6 control-label " id="accountFee"
									maxlength="16" style="text-align: left;"></label>
							</div>
							<div class="col-md-6 ">
								<label class="col-md-4 control-label">未到账(元)：</label> <label
									class="col-md-6 control-label" id="unAccountFee"
									maxlength="16" style="text-align: left;"></label>
							</div>
						</div>

					<shiro:hasPermission name="ADOCKFEEBILLFUND">
						<div class="form-group feeDiv hideDiv">
							<div class="col-md-6 ">
								<label class="col-md-4 control-label">本次到账(元)：</label>
								<div class="col-md-6" style="padding-right: 0px;">
									<input class="form-control" style="padding-right: 0px;"
										onkeyup="config.clearNoNum(this,2)" data-required=1
										maxlength="16" id="fundsTotalFee">
								</div>
								<div class="col-md-1"
									style="padding-left: 0px; padding-right: 0px;">
									<button type="button" class="btn btn-primary save" key="4">确认到账</button>
								</div>
							</div>
							<div class="col-md-6 ">
								<label class="col-md-4 control-label">到账日期：</label>
								<div class="col-md-6 input-group">
									<input type="text" id="fundsTime" class="form-control date-picker" data-required="1">
								</div>
							</div>
						</div>
						</shiro:hasPermission>
					<hr color="gray"  style="margin:5px auto;width:100%"/>
					
					<div class="form-group">
						<label class="col-md-2 control-label">备注:</label>
						<div class="col-md-9">
							<textarea class="form-control" rows="1" maxlength="100"
								id="description"></textarea>
						</div>
					</div>

					<div class="form-group commentDiv hideDiv">
						<label class="col-md-2 control-label">审批意见:</label>
						<div class="col-md-9">
							<textarea class="form-control" rows="1" maxlength="100"
								id="comment"></textarea>
						</div>
					</div>

					 <hr color="gray"  style="margin:5px auto;width:100%"/> 
                        
                       <div class="form-group createDiv hideDiv">
                        <div class="col-md-6 col-md-offset-6">
                         <label class="col-md-4 control-label">制单:</label>
                        <label class="col-md-8 control-label feeKey" style="text-align:left" id="createUserName"></label>
                        </div>
                       </div> 
                       
                       <div class="form-group reviewDiv hideDiv">
                       <div class="col-md-6">
                       <label class="col-md-4 control-label">审核人:</label>
                       <label class="col-md-8 control-label" style="text-align:left" id="reviewUserId"></label>
                       </div>
                       <div class="col-md-6">
                       <label class="col-md-4 control-label">审核日期:</label>
                       <label class="col-md-8 control-label" style="text-align:left" id="reviewTime"></label>
                       </div>
                       </div>
                       
                       <div class="form-group fundsDiv hideDiv">
						<div class="col-md-6">
							<label class="col-md-4 control-label">到账确认人:</label>
							<label class="col-md-8 control-label" style="text-align: left" id="fundsUserId"></label>
						</div>
						<div class="col-md-6">
							<label class="col-md-4 control-label">到账日期:</label> 
							<label class="col-md-5 control-label" style="text-align: left" id="fundsTimeStr"></label> 
							<label class="col-md-3 control-label" style="text-align: left">
							<a href="javascript:void(0)" key="0" id="openFundsTable"><i class="fa fa-reorder"></i></a></label>
						</div>
						</div> 
						
                       <div class="form-group fundsDetailDiv"  style="padding:0 55px;">
                       <div data-role='fundsDetailTable'></div>
                       </div>
                       
					<div class="form-group billingDiv hideDiv">
						<div class="col-md-6">
							<label class="col-md-4 control-label">开票确认人:</label>
							<label class="col-md-8 control-label" style="text-align: left" id="billingUserId"></label>
						</div>
						<div class="col-md-6">
							<label class="col-md-4 control-label">开票日期:</label>
							<label class="col-md-8 control-label" style="text-align: left" id="billingTimeStr"></label>
						</div>
					</div>
						
				    </div>
				    </div>
			     </form>
			</div>
			</div>
			</div>
			<div class="modal-footer controlDiv">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<shiro:hasPermission name="ADOCKFEEBILLSUBMIT">
				<button type="button" class="btn btn-success save" key="0" >保存</button>
				<button type="button" class="btn btn-primary save" key="1" >提交</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="ADOCKFEEBILLREVIEW">
				<button type="button" class="btn btn-primary save" key="2" >通过</button>
				<button type="button" class="btn btn-primary save" key="3" >不通过</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="ADOCKFEEBILLREBACK">
				<button type="button" class="btn btn-primary hideDiv" id="back" >回退</button>
			 	</shiro:hasPermission>
			</div>
		</div>
	</div>
</div>