<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width:900px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">上传作业附件</h4>
			</div>
		<div class="modal-body">
			<div class="row"><div class="col-md-12">
					<div class="portlet-body form">
					<form action="<%=request.getContextPath()%>/outboundserial/uploadWorkFile" id="workFileForm" method="post" class="form-horizontal" enctype="multipart/form-data"  target="id_iframe"> 
									<div class="form-group"> 
										<input id="lefile" type="file"  name="file" style="display:none"/>  
										<input id="id" type="text"  name="id" style="display:none"/> 
										<input id="type" type="text"  name="type" style="display:none"/> 
										<label class="control-label col-md-2">文件</label>
										<div class="col-md-3">
											<input id="photoCover" class="form-control" type="text">  
										</div>
									<div class="col-md-1"><button id="upBtn" class='btn btn-success'>浏览..</button></div>
									<label class="control-label col-md-1">说明</label>
									<div class="col-md-3">
									<input  type="text" id="description" name="description" class="form-control">
									</div>
								<div class="col-md-1"><input type="button" class="btn btn-primary buttonUp-outbound" value="提交"/></div></div>
					</form>
					 <iframe id="id_iframe" name="id_iframe" style="display:none;"></iframe>   
					</div><div data-role="uploadFileList"></div>
				</div>
				
				</div>
			
		</div>
	</div>
	<!-- /.modal-content -->
	</div>
</div>
	  
