<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">泵棚信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			     <form action="#" class="form-horizontal">
                     <div class="form-body">
                       <div class="form-group">
                       <label class="col-md-4 control-label">泵棚名称<span class="required">*</span></label>
                       <div class="col-md-8">
                       <input class="form-control" maxlength="10" data-type="Require" id="name" data-required="1">
                       </div>
                       </div>   
                       <label class="hidden" id="id" ></label>
                     </div>		
                      <div class="form-group">
                       <label class="col-md-4 control-label">描述:</label>
                       <div class="col-md-8">
                       <textarea class="form-control" rows="1" id="description"></textarea>
                       </div>
                       </div> 	     
			     </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" id="close">取消</button>
				<button type="button" class="btn btn-success" id="save">提交</button>
			</div>
		</div>
	</div>
</div>