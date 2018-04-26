<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<link href="<%=basePath %>/resource/admin/pages/css/style.css" rel="stylesheet" type="text/css" media="print"/>
<div class="modal fade">
	<div class="modal-dialog modal-full" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">查看货体</h4>
			</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					
					<div  data-role="clientGoodsGrid" >
				</div>
			</div>
		</div>
		<div class="modal-footer">
		<button type="button" class="btn default btn-print">打印</button>
			<button type="button" class="btn default" data-dismiss="modal">关闭</button>
		</div>
	</div>
	<!-- /.modal-content -->
	</div>
</div>
</div>