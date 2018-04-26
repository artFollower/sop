<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style>
<!--
#flow {
	width:100%;
}
-->
</style>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">系统检查</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
				<form action="#" class="form-horizontal">
                     <div class="form-body">
                      <div class="form-group">
                       <label class="col-md-4 control-label">检查情况</label>
                       <div class="col-md-8">
                       <textarea class="form-control" maxlength="100" rows="1" id="sysStatus" ></textarea>
                       </div></div>
                       <div class="form-group">
                       <label class="col-md-4 control-label">处理情况</label>
                       <div class="col-md-8">
                       <textarea class="form-control" maxlength="100" rows="1" id="result" ></textarea>
                       </div></div>
                       
                       <input type="hidden" id="id" class="form-control">
                     </div>			     
			     </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" id="close">关闭</button>
				<button type="button" class="btn btn-success" id="save">提交</button>
			</div>
		</div>
	</div>
</div>