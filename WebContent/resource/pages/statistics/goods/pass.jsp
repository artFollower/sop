<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">添加货批</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal passForm">
						<div class="form-group">
									<label class="control-label col-md-4">海关放行编号<span
										class="required">*</span></label>
									<div class="col-md-8" id="select-tradeType">
										<input id="passCode" type="text"  data-required="1" data-type="Require" class="form-control passCode">
									</div>
								</div>
						<div class="form-group">
							<label class="control-label col-md-4">放行数量(吨)<span class="required">*</span></label>
							<div class="col-md-8">
								<input id="passCount" type="text" name="name" data-required="1" class="form-control passCount" onkeyup="clearNoNum(this)" onafterpaste="clearNoNum(this)" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary button-pass" id="button-pass" >提交</button>
			</div>
		</div>
	</div>
</div>
<script>
		
	
		
	</script>