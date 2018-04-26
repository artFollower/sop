<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<link href="<%=basePath %>/resource/admin/pages/css/style.css" rel="stylesheet" type="text/css"  media="print"/>
<div class="modal fade">
	<div class="modal-dialog  modal-full">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">打印预览</h4>
			</div>
			<div class="modal-body" style="min-height:400px;" id="gridPrintPriview"></div>
			<div class="modal-footer">
				<a class="btn btn-default btn-sm btn-print" href="javascript:void(0);"> <i class="fa fa-print"></i> 打印</a>
				<a class="btn btn-default btn-sm btn-close" href="javascript:void(0);"> <i class="fa fa-close"></i> 关闭</a>
			</div>
		</div>
	</div>
</div>