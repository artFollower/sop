<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style type="text/css">
.modal-dialog {
	margin: 30px auto;
	width:50%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
					</div>
				<div class="modal-body"
					style="padding-left: 25px; padding-right: 45px;">
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-shopping-cart"></i>查看记录详情
							</div>
						</div>
					<div class="portlet-body ">
					  <div id="dialogGrid" data-role="msgGrid" style="width: 100%;"></div>
                            
					</div>
					</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-success" id="suremsg">确认</button>
			</div>
		</div>
	</div>
</div>