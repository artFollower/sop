<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width:900px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">请输入需要修改的入库数量</h4>
			</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet-body form">
						<form class="form-horizontal sqlitform">
							<div class="form-body">
								<table width="100%">
									
									<tr>
										<td class=" col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4 gName">增减量：<span class="required">*</span></label>
												<div class="col-md-8">
													<input type="text" id="dGoodsTotal" onkeyup="config.clearNoNum(this)"  data-required="1" data-type="Require" class="form-control dGoodsTotal"  />
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
