<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">选择关联的日志</h4>
			</div>
			<div class="modal-body"
				style="padding-left:45px; padding-right:65px;">
				
				
				<div class="row">
				<div class="col-md-12">
					
				

				
				
										<label class="control-label col-md-4">到港时间:</label>
											         <div class="col-md-6" >
											         <input  id="arrivalTime2" key="1"  class="form-control form-control-inline date-picker  arrivalTime2"  type="text" />
												 </div>
				<!-- <span class="input-group-btn">
						<button type="button"  class="btn green-haze" id="searchConnect">
									搜索 <i class="m-icon-swapright m-icon-white"></i>
								</button>
							</span> -->
				<div class="col-md-12" data-role="search2Grid"></div>
		</div>
		
		
	</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			<%-- 				<shiro:hasPermission name="user:create"><button type="button" class="btn btn-success" id="save">保存</button></shiro:hasPermission> --%>
			<!-- <button type="button" class="btn blue" id="save">确定</button> -->
		</div>
</div>
</div>
</div>
</div>

<script>
	
</script>