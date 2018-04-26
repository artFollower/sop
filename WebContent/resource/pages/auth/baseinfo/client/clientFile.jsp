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
	<div class="modal-dialog " >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">查看文件</h4>
			</div>
		<div class="modal-body">
			<div class="row">
			
			<div class="btn-group buttons  col-md-6 col-sm-6">
			<shiro:hasPermission name="ABASECLIENTFILEEDIT">
							<button class="btn btn-default btn-circle mar-r-10 btn-add" type="button" data-toggle="modal">
								<span class="fa fa-plus"></span><span class="text">添加</span>
							</button>
					</shiro:hasPermission>
						</div>
			
				<div class="col-md-12">
					
					<div  data-role="clientFileGrid" >
				</div>
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