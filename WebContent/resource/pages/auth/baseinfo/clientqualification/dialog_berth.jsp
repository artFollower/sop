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
				<h4 class="modal-title">泊位信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
				<form action="#" class="form-horizontal">
                     <div class="form-body">
                       <div class="form-group">
                       <label class="col-md-4 control-label">泊位名<span class="required">*</span></label>
                       <div class="col-md-8">
                       <input class="form-control" id="name" data-required="1">
                       </div>
                       </div>   
                       <div class="form-group">
                       <label class="col-md-4 control-label">船长(米)<span class="required">*</span></label>
                       <div class="col-md-8">
                         <input class="form-control" id="limitLength" maxlength="10" data-type="Require" onkeyup="config.clearNoNum(this,3)" data-required="1">
                       </div>
                       </div>   
                       <div class="form-group">
                       <label class="col-md-4 control-label">泊位长度(米)<span class="required">*</span></label>
                       <div class="col-md-8">
                         <input class="form-control" id="length" maxlength="10" data-type="Require" onkeyup="config.clearNoNum(this,3)" data-required="1">
                       </div>
                       </div>  
                       <div class="form-group">
                       <label class="col-md-4 control-label">前沿水深(米)<span class="required">*</span></label>
                       <div class="col-md-8">
                         <input class="form-control" id="frontDepth" maxlength="10" data-type="Require" onkeyup="config.clearNoNum(this,3)" data-required="1">
                       </div>
                       </div>  
                       <div class="form-group">
                       <label class="col-md-4 control-label">最小船长(米)<span class="required">*</span></label>
                       <div class="col-md-8">
                         <input class="form-control" id="minLength" maxlength="10" data-type="Require" onkeyup="config.clearNoNum(this,3)" data-required="1">
                       </div>
                       </div>  
                       <div class="form-group">
                       <label class="col-md-4 control-label">吃水(米)<span class="required">*</span></label>
                       <div class="col-md-8">
                         <input class="form-control" id="limitDrought" maxlength="10" data-type="Require" onkeyup="config.clearNoNum(this,3)" data-required="1">
                       </div>
                       </div>
                       <div class="form-group">
                       <label class="col-md-4 control-label">最大载重吨(吨)</label>
                       <div class="col-md-8">
                         <input class="form-control" id="limitDisplacement" maxlength="10"   onkeyup="config.clearNoNum(this,3)">
                       </div>
                       </div> 
                       <div class="form-group">
                       <label class="col-md-4 control-label">最大排水量(吨)</label>
                       <div class="col-md-8">
                         <input class="form-control" id="limitTonnage" maxlength="10" onkeyup="config.clearNoNum(this,3)" >
                       </div>
                       </div>
                       <div class="form-group">
                       <label class="col-md-4 control-label">泊位信息</label>
                       <div class="col-md-8">
                       <textarea  id="description" rows="3"maxlength="255" data-type="Require" class="form-control"></textarea>
                       </div>                  
                     </div>	
                     <div class="form-group">
                       <label class="col-md-4 control-label">安全措施</label>
                       <div class="col-md-8">
                       <textarea  id="safeInfo" rows="3"maxlength="1023" data-type="Require" class="form-control"></textarea>
                       </div>                  
                     </div>		     
                     <input type="hidden" id="id">
                     </div>
			     </form>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal"  class="btn btn-default" id="close">取消</button>
				<button type="button" class="btn btn-success" id="save">提交</button>
			</div>
		</div>
	</div>
</div>