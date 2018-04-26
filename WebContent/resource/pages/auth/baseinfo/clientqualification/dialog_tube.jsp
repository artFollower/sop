<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">管线信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			     <form action="#" class="form-horizontal">
                     <div class="form-body">
                       <div class="form-group">
                       <label class="col-md-4 control-label">管名<span class="required">*</span></label>
                       <div class="col-md-8">
                       <input class="form-control" maxlength="16" data-type="Require" id="name" data-required="1">
                       </div>
                       </div>   
                       <div class="form-group">
                       <label class="col-md-4 control-label">类型<span class="required">*</span></label>
                       <div class="col-md-8">
                      <select class="form-control" id="type" data-required="1">
														<option value="0" selected>主管线</option>
														<option value="1">过渡管线</option>
													</select>
                       </div>
                       </div>                   
                     <div class="form-group">
                       <label class="col-md-4 control-label">前期存储物料:</label>
                       <div class="col-md-8">
                       <input class="form-control" maxlength="16" data-type="Require" id="productId">
                       </div>
                       </div> 
                       <div class="form-group">
                       <label class="col-md-4 control-label">使用情况</label>
                       <div class="col-md-8">
                        <select class="form-control" id="description">
														<option value="使用中" selected>使用中</option>
														<option value="已清洗">已清洗</option>
													</select>
                       </div>
                       </div> 
                       <input type="hidden" id="id" class="form-control">
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