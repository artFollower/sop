<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog " style="width:400px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">请输入需要换的储罐</h4>
			</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet-body form">
						<form class="form-horizontal sqlitform">
							<div class="form-body">
								<table width="100%">
									
									<tr>
										
										<td class=" col-md-12">
											<div class="form-group">
												<label class="control-label col-md-4">罐号</label>
												<div class="col-md-8">
													<input type="text" id="dTankId" class="form-control dTankId"  />
												</div>
											</div>
										</td>
										
										
									</tr>
								</table>
							</div>
						</form>
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
