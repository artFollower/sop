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
				<h4 class="modal-title">接卸前各岗位最后的检查确认</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
				<form action="#" class="form-horizontal">
                     <div class="form-body">
                       <div class="form-group createTime">
                       <label class="col-md-4 control-label">日期</label>
                        <label class="col-md-8 control-label" id="createTime"></label>
                       </div>   
                       <div class="form-group">
                       <label class="col-md-4 control-label">岗位:</label>
                       <div class="col-md-8">
                       <label class="control-label" id="post"></label>
                       </div>
                       </div>                     
                     <div class="form-group">
                       <label class="col-md-4 control-label">检查内容：</label>
                       <div class="col-md-8">
                       <label class="control-label" maxlength="64" id="checkContent"></label>
                       </div>
                       </div> 
                        <div class="form-group">
                       <label class="col-md-4 control-label">检查结果</label>
                       <div class="col-md-8">
                       <select class="form-control" id="result" >
                          <option data="1" value="一致">一致</option>
                          <option data="1" value="不一致">不一致</option>
                          <option data="2" value="正常">正常</option>
                          <option data="2" value="不正常">不正常</option>
                       </select>
                       </div>
                       </div> 
                       <div class="form-group">
                       <label class="col-md-4 control-label">处理情况</label>
                       <div class="col-md-8">
                       <textarea class="form-control" maxlength="100" rows="1" id="solve" ></textarea>
                       </div></div>
                       <div class="form-group">
                       <label class="col-md-4 control-label">确认人</label>
                       <div class="col-md-8">
                       <input class="form-control"  id="createUserId" >
                       </div>
                       </div> 
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