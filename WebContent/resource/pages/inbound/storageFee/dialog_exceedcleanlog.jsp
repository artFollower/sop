<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog" style="width:50%">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">日志记录</h4>
			</div>
			<div class="modal-body" style="padding-left:25px; padding-right:35px;">
			<div class="row">
				<div class="col-md-12">
				<form class="form-horizontal">
				<div class="form-group">
			  <div class="col-md-12" data-role="cleanlogsTable"></div>
			  </div>
			    <div class="form-group">
			     <label class="hidden" id="cargoId"></label>
			      <label class="hidden" id="ladingId"></label>
			    <label class="col-md-2 control-label">备注：</label>
			    <div class="col-md-10">
			    <textarea id="content" class="form-control" rows="1"></textarea>
			    </div>
			    </div>
			    </form>
			    </div>
			</div>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-success save" >保存</button>
			</div>
		</div>
	</div>
</div>