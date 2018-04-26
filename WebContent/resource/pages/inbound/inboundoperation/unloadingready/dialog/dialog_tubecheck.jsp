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
				<h4 class="modal-title">管线的准备及检查情况</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
				<form action="#" class="form-horizontal">
                     <div class="form-body">
                     <div class="form-group">
                       <label class="col-md-4 control-label">岗位:</label>
                       <label style="text-align:left" class="col-md-8 control-label" id="checkType"></label>
                       </div>  
                       <div class="form-group checkTime">
                       <label class="col-md-4 control-label">日期</label>
                       <label class="col-md-8 control-label" id="checkTime" style="text-align:left"></label>
                       </div>   
                       <div class="form-group">
                       <label class="col-md-4 control-label">检查情况:<span
							class="required">*</span></label>
                       <div class="col-md-8">
                       <input class="form-control" maxlength="64" data-required="1" id="content">
                       </div>
                       </div>                     
                     <div class="form-group">
                       <label class="col-md-4 control-label">检查人</label>
                       <div class="col-md-8">
                       <input class="form-control" readonly id="checkUserId">
                       </div>
                       </div> 
                       <div class="form-group">
                       <label class="col-md-4 control-label">备注</label>
                       <div class="col-md-8">
                       <textarea class="form-control" maxlength="100" rows="3" id="description" ></textarea>
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