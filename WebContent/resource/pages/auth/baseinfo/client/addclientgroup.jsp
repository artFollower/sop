<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width: 900px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">客户组信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal form-clientgroup">
				<div class="form-body">
					<table width="100%">
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="control-label col-md-2">客户组名称<span class="required">*</span></label>
									<div class="col-md-10">
										<input type="text" maxlength="32" name="name" data-required="1" class="form-control name" />
									</div>
								</div>
							</td>
							
						</tr>
						
						
						
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">描述</label>
									<div class="col-md-10">
										<textarea class="form-control description" rows="3" maxlength="100" name="description"></textarea>
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
				<button type="button" class="btn btn-primary" id="addClientGroup" >提交</button>
			</div>
		</div>
	</div>
</div>
<script>
		
	
		
	</script>