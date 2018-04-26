<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="modal fade">
	<div class="modal-dialog modal-full">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">货体流转图</h4>
			</div>
			<div class="modal-body">
				<!-- <div data-role="goodsTreeGrid" style="width: 100%;min-height: 400px"></div> -->


				<label class="control-label ">&nbsp;&nbsp;&nbsp;货批编号:</label> <label
					class=" control-label  code" style="text-align: left"> </label> <label
					class="control-label ">&nbsp;&nbsp;&nbsp;货品:</label> <label
					class=" control-label  productName" style="text-align: left">
				</label>
				<div style="overflow-x: auto;height:700px;overflow-y:scroll;" class="scroller">
					<div class="jstree jstree-default" style="margin:auto;">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn default btn-print">打印</button>
				<button type="button" class="btn default" data-dismiss="modal">关闭</button>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>
</div>
