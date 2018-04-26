<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width:800px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">上传合同文件</h4>
			</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet-body form">
					<form action="<%=request.getContextPath()%>/lading/updatefile" id="fileForm" method="post" enctype="multipart/form-data"  target="id_iframe"> 
					
						<input id="lefile" type="file" name="file" style="display:none"/>  
							<div class="input-append">  
							<input id="id" type="text" name="id" style="display:none"/> 
    						<input id="photoCover" class="input-large " type="text" style="height:35px;">  
    						<a class="btn btn-primary addFile" >浏览</a>  
							</div>  
							</form>
							<iframe id="id_iframe" name="id_iframe" style="display:none;"></iframe> 
					</div>
					
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">关闭</button>
			<button type="button" class="btn btn-primary button-ok">确定</button>
		</div>
	</div>
	<!-- /.modal-content -->
	</div>
</div>
	  
