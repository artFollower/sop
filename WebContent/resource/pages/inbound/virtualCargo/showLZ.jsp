<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="modal fade">
	<div class="modal-dialog modal-full" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">货体流转图</h4>
			</div>
			<div class="modal-body">
				<!-- <div data-role="goodsTreeGrid" style="width: 100%;min-height: 400px"></div> -->
				
		<!-- 		<div class="table-container">
		<table class="tree table table-striped table-bordered table-hover no-footer">
		</table>
	</div>
	 -->
	<div style="overflow-x: auto;" class="scroller">
					<div class="jstree jstree-default" style="margin:auto;">
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn default" data-dismiss="modal">关闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>
</div>
