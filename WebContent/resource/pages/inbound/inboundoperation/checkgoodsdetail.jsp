<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
					<h4 class="modal-title">查看货品详情</h4>
					</div>
				<div class="modal-body "
					style="padding-left: 25px; padding-right: 45px;">
					<div class="portlet box">
<!-- 						<div class="portlet-title"> -->
<!-- 							<div class="caption"> -->
<!-- 								<i class="fa fa-shopping-cart"></i> -->
<!-- 							</div> -->
<!-- 						</div> -->
					<div class="portlet-body ">
					<div data-role="goodsDetailGrid"  style="width:100%"></div>
					</div>
					</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>