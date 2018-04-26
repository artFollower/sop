<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width:800px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">请选择审批人</h4>
			</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
				
				<form name="approveForm" id="approveForm" target="_self" class="form-horizontal searchFirstCondition">
				<div class="form-group">
				<label class="control-label " style="width:600px;float:left;" id="approveText"></label>
				</div>
			<div class="form-group">
						 <label class="control-label " style="width:80px;float:left;" >部门:</label>
						<div class="col-md-3">
							<input id="department" type="text" name="id" data-provide="typeahead"  class="form-control department">
						</div>
						<label class="control-label " style="width:80px;float:left;" >理由:</label>
						<div class="col-md-3">
							<input id="reason" type="text" name="reason"   class="form-control reason">
						</div>
				          </div>
					</form>
				
				
			<div data-role="approveGrid">
			</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">关闭</button>
			<button type="button" class="btn btn-primary button-ok">确定</button>
		</div>
	</div>
	<!-- /.modal-content -->
	</div>
</div>
