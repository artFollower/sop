<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width:300px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">请选择要回退到的状态</h4>
			</div>
		<div class="modal-body">
		  <div class="form-group" style="margin-bottom:10px;">
              <div class="col-md-12">
				<select class="form-control" id="backStatus">
					    <option value="0" selected>未提交</option>
						<option value="2">已审核</option>
						</select>
              </div>
              </div> 
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">关闭</button>
			<button type="button" class="btn btn-primary " id="btnSubmit">确定</button>
		</div>
	</div>
	<!-- /.modal-content -->
	</div>
</div>
