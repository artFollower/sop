<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width: 900px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">货品代理信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form action="#" class="form-horizontal form-cargoagent">
				<div class="form-body">
					<table width="100%">
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货代简称<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" name="name" data-required="1" class="form-control code" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">货代名称<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" name="name" data-required="1" class="form-control name" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
							<div class="form-group">
							<label class="control-label col-md-4">联系人</label>
									<div class="col-md-8">
										<input type="text" name="name"  class="form-control contactName" />
									</div>
								</div></td>
							<td class=" col-md-6">
							<div class="form-group">
							<label class="control-label col-md-4">联系电话</label>
									<div class="col-md-8">
										<input type="text" name="name"  class="form-control contactPhone" />
									</div>
								</div></td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">联系人邮箱</label>
									<div class="col-md-8">
										<input type="text" name="name"  class="form-control contactEmail" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
						<td colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">描述</label>
									<div class="col-md-10">
										<textarea class="form-control description" rows="2"></textarea>
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
				<button type="button" class="btn btn-primary" id="addCargoAgent" >提交</button>
			</div>
		</div>
	</div>
</div>
<script>
		
	
		
	</script>