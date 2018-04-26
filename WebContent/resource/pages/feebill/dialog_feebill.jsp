<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 69%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog">
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
								<a class="hidden-print" style="color: white;" onclick="feeBill.exportDetailBillFee(this)"> <i class="fa fa-file-excel-o">&nbsp;导出</i>
								</a>
							</div> 
						</div>
						<div class="portlet-body">
				<form action="#" class="form-horizontal">
                     <div class="form-body">
                      <div class="row">
                      <label class="col-md-8 hidden feeKey" id="id"></label>
                      <label class=" hidden " id="feeType"></label>
                      <hr color="gray"  style="margin:5px auto;width:100%"/>
                       <div class="form-group  billingCodeDiv hideDiv" style="display:none">
                       <div class="col-md-6" >
                        <label class="col-md-4 control-label">发票号:</label>
                        <div class="col-md-8 ">
                        <input class="form-control feeKey" data-required=1 id="billingCode">
                        </div>
                        </div>
                        <div class="col-md-6 " >
                       <label class="col-md-4 control-label">开票日期:</label>
                       <div class="col-md-6 input-group">
						<input type="text"  class="form-control billingTime date date-picker" data-required="1">
                       </div>
                       </div>
                       </div>  
                       <div class="form-group billingCodeDiv hideDiv" style="display:none">
                      <div class="col-md-6">
                      <label class="col-md-4 control-label" >未税金额(元):</label>
                      <div class="col-md-8">
                      <input type="text"  class="form-control" onkeyup="config.clearNoNum(this,2)" id="unTaxFee" >
                      </div>
                      </div>
                      <div class="col-md-6">
                      <label class="col-md-4 control-label" >税率:</label>
                      <div class="col-md-6 input-group">
                      <input type="text"  class="form-control" id="taxRate" onkeyup="config.clearNoNum(this,3)" >
                      </div>
                      </div>
                      </div>
                      <div class="form-group billingCodeDiv hideDiv" style="display:none">
                      <div class="col-md-6">
                      <label class="col-md-4 control-label" >税额(元):</label>
                      <div class="col-md-8">
                      <input type="text"  class="form-control" onkeyup="config.clearNoNum(this,2)" id="taxFee" >
                      </div>
                      </div>
                      <div class="col-md-6">
                      <label class="col-md-4 control-label" >开票说明:</label>
                      <div class="col-md-6 input-group">
                      <input type="text"  class="form-control"  id="billingContent" > 
                      </div>
                      </div>
                      </div>
                       <div class="form-group billingCodeDiv hideDiv" style="display:none">
                      <div class="col-md-offset-6 col-md-6">
                       <shiro:hasPermission name="AFEEBILLBILLING">
                        <div class="col-md-offset-4" style="padding-left:0px;padding-right:0px;">
                         <button type="button" class="btn btn-primary sureBilling save"  key="5" >确认开票</button>
                        </div>
                        </shiro:hasPermission>
                        </div>
                        </div>
                         <hr color="gray"  style="margin:5px auto;width:100%"/>  
                        <div class="form-group " >
                        <div class="col-md-6" >
                       <label class="col-md-4 control-label">开票抬头:</label>
                       <div class="col-md-8 input-group">
                       <input class="form-control feekey" disabled data-required="1" id="feeHeadName">
                        <shiro:hasPermission name="AFEEBILLHEAD">
                       <div class="input-group-btn" id="feeHeadDiv" style="padding-left:0px;padding-right:0px;">
                       <button type="button" class="btn btn-primary modifyFeeHead">修改</button>
                       <button class="btn btn-primary addFeeHead" type="button" style="padding-left: 10px; padding-right: 10px;">+</button>
                       </div>
                       </shiro:hasPermission>
                       </div>
                       </div>
                       </div>
                      
                     <div class="form-group" style="margin-top:25px;">
                     <div class="btn-group buttons" style="padding-left:35px;">
							<button class="btn btn-default btn-circle mar-r-10 editFeeCharge" key="0" type="button">
								<span class="fa fa-edit">编辑</span>
							</button>
						</div>
						<div class="scroller " style="max-height:400px;overflow-y:scroll;margin-right:35px;">
                     <div class="col-md-12 chargeDetailDiv" style="padding-left:35px;">
							<div class="col-md-12" id="detailFeeBillCharge"></div>
                        </div>
                        </div>
                       </div> 
                       <div class="form-group">
                        <div class="col-md-6 ">
                        <label class="col-md-4 control-label ">已到账(元)：</label>
                        <label class="col-md-6 control-label " id="fundsAllTotalFee" maxlength="16" style="text-align:left;"></label>
                        </div>
                        <div class="col-md-6 ">
                         <label class="col-md-4 control-label">未到账(元)：</label>
                       <label class="col-md-6 control-label" id="hasTotalFee" maxlength="16" style="text-align:left;"></label>
                       </div>
                       </div>
                        <hr color="gray"  style="margin:5px auto;width:100%"/> 
                        <shiro:hasPermission name="AFEEBILLFUND">
                       <div class="form-group feeDiv hideDiv" >
                       <div class="col-md-6 ">
                       <label class="col-md-4 control-label" >本次到账(元)：</label>
                       <div class="col-md-6" style="padding-right:0px;">
                        <input class="form-control" style="padding-right:0px;" onkeyup="config.clearNoNum(this,2)" data-required=1 maxlength="16" id="fundsTotalFee">
                        </div>
                        <div class="col-md-1" style="padding-left:0px;padding-right:0px;">
                          <button type="button" class="btn btn-primary save" key="4" >确认到账</button>
                        </div>
                        </div>
                        <div class="col-md-6 " id="funttime">
                       <label class="col-md-4 control-label">到账日期：</label>
                       <div class="col-md-6 input-group">
						<input type="text"  class="form-control fundsTime date date-picker" data-required="1">
                       </div>
                       </div>
                      </div>
                      </shiro:hasPermission>
                       </div>
                       <div class="form-group" >
                          <label class="col-md-2 control-label">备注:</label>
                          <div class="col-md-9">
                          <textarea class="form-control" rows="1" maxlength="100" id="description"></textarea>
                          </div>
                       </div> 
                        <hr color="gray"  style="margin:5px auto;width:100%"/> 
                       <div class="form-group commentDiv hideDiv" hidden="true">
                       <label class="col-md-2 control-label">审批意见</label>
                       <div class="col-md-9">
                       <textarea rows="3" class="form-control" maxlength="100" id="comment"></textarea>
                       </div>
                       </div>
                        
                       <div class="form-group createDiv hideDiv">
                        <div class="col-md-6 col-md-offset-6">
                         <label class="col-md-4 control-label">制单:</label>
                        <label class="col-md-8 control-label feeKey" style="text-align:left" id="createUserName"></label>
                        </div>
                       </div> 
                       <div class="form-group commentsDiv hideDiv"></div>
                       <div class="form-group fundsDiv hideDiv" style="display:none">
                         <div class="col-md-6">
                         <label class="col-md-4 control-label">到账确认人:</label>
                        <label class="col-md-8 control-label" style="text-align:left" id="fundsUserId">张三</label>
                        </div>
                        <div class="col-md-6">
                         <label class="col-md-4 control-label">到账日期:</label>
                        <label class="col-md-5 control-label" style="text-align:left" id="fundsTime">2015-01-23</label>
                        <label class="col-md-3 control-label" style="text-align:left" ><a href="javascript:void(0)" key="0" id="openFundsTable"><i class="fa fa-reorder"></i></a></label>  
                        </div>
                       </div> 
                       <div class="form-group fundsDetailDiv"  style="padding-left:55px;padding-right:55px;">
                       <div data-role='fundsDetailTable'></div>
                       </div>
                       <div class="form-group billingDiv hideDiv" style="display:none">
                         <div class="col-md-6">
                         <label class="col-md-4 control-label">开票确认人:</label>
                        <label class="col-md-8 control-label" style="text-align:left" id="billingUserId">李四</label>
                        </div>
                        <div class="col-md-6">
                         <label class="col-md-4 control-label">开票日期:</label>
                        <label class="col-md-8 control-label" style="text-align:left" id="billingTime">2015-01-24</label>
                       </div>
                       </div>
                         <div class="form-group subtoDiv hideDiv">
						<div class="col-md-6">
						<div class=" col-md-4" style="padding-right: 0px;" >
							<button type="button" onclick="Contract.openApproveModal(3)"  class="btn btn-primary ">提交至...</button>
							</div>
							<div class="col-md-8" style="padding-left: 0px;">
								<input type="text" data-required="1" data-type="Require"  class="form-control submitto"  readonly  />
							</div>
						</div>
						<div class="col-md-6">
						<div class=" col-md-4" style="padding-right: 0px;" >
							<button type="button"  onclick="Contract.openCopyModal()"  class="btn btn-primary ">抄送至...</button>
							</div>
							<div class="col-md-8" style="padding-left: 0px;">
								<input type="text"  class="form-control copyto"  readonly  />
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
				<shiro:hasPermission name="AFEEBILLSUBMIT">
				<button type="button" class="btn btn-success save" key="0" >保存</button>
				<button type="button" class="btn btn-primary save" key="1" >提交</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="AFEEBILLREVIEW">
				<button type="button" class="btn btn-primary " id="submitto">通过并提交</button>
				<button type="button" class="btn btn-primary save" key="2" >通过</button>
				<button type="button" class="btn btn-primary save" key="3" >不通过</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="AFEEBILLREBACK">
				<button type="button" class="btn btn-primary" style="display:none;" id="back" >回退</button>
			 	</shiro:hasPermission>
			</div>
		</div>
	</div>
</div>