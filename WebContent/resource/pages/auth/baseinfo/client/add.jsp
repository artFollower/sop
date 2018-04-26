<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width: 900px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">客户信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal form-client">
				<div class="form-body">
					<table width="100%">
						<tr>
							<!-- <td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">客户编码<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" id="clientCode" maxlength="32" name="code" data-required="1" class="form-control code" 
										       data-toggle="tooltip" data-placement="right"/>
									</div>
								</div>
							</td> -->
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">客户名称<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" maxlength="60" name="name" data-required="1" class="form-control name"  />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
						<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">客户组</label>
									<div class="col-md-8" >
									<input id="clientGroupId" maxlength="32" type="text" data-provide="typeahead"  class="form-control clientGroupId">
									</div>
								</div>
							</td>
							<!--  <td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">客户账户<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" name="guestId" data-required="1" class="form-control guestId" />
									</div>
								</div>
							</td>-->
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">电话</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="phone"   class="form-control phone" />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">联系人</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="contactName"  class="form-control contactName" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">联系人电话</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="contactPhone"   class="form-control contactPhone" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">联系地址</label>
									<div class="col-md-8">
										<input type="text" maxlength="64" name="address" class="form-control address" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">邮编</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="postcode" onkeyup="config.clearNoNum(this)" class="form-control postcode" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">品名</label>
									<div class="col-md-8">
										<input type="text" maxlength="32" name="email" class="form-control email" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">传真</label>
									<div class="col-md-8">
										<input type="text" maxlength="32" name="fax"  class="form-control fax" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">提货放货指令</label>
									<div class="col-md-8">
										<input type="text" maxlength="32" name="bankAccount" onkeyup="config.clearNoNum(this)" class="form-control bankAccount" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货权转移放货指令</label>
									<div class="col-md-8">
										<input type="text" maxlength="32" name="bankName" class="form-control bankName" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">信用等级</label>
									<div class="col-md-8">
										<select class="form-control creditGrade"  name="creditGrade" placeholder="选择客户类型...">
											<option value="">请选择...</option>
											<option value="1">较差</option>
											<option value="2">一般</option>
											<option value="3">良好</option>
										</select>
									</div>
								</div>
							</td>
							
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">付款情况</label>
									<div class="col-md-8">
										<select class="form-control paymentGrade" name="paymentGrade" placeholder="选择客户类型...">
											<option value="">请选择...</option>
											<option value="1">较差</option>
											<option value="2">一般</option>
											<option value="3">良好</option>
										</select>
									</div>
								</div>
							</td>
							
						</tr>
						
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">描述</label>
									<div class="col-md-10">
										<textarea class="form-control description" maxlength="100" rows="3" name="description"></textarea>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
			
				</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="addClient" >提交</button>
			</div>
		</div>
	</div>
</div>
<script>
		
	
		
	</script>