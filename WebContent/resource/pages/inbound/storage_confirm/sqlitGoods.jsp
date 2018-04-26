<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width:900px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">请输入需要拆分的数量</h4>
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
												<label class="control-label col-md-4">罐号</label>
												<div class="col-md-8">
													<input type="text" id="dTankId" class="form-control dTankId"  />
												</div>
											</div>
										</td>
										<td class=" col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4">要拆分的罐检量<span class="required">*</span></label>
												<div class="col-md-8">
													<input type="text" id="dGoodsTank" onkeyup="config.clearNoNum(this)"  data-required="1" data-type="Require" class="form-control dGoodsTank"  />
												</div>
											</div>
										</td>
										
									</tr>
									
									
									<tr>
										
										<td class=" col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4">拆分的货体总量</label>
												<div class="col-md-8">
													<input type="text" id="dGoodsTotal"  readonly data-required="1" data-type="Require" class="form-control dGoodsTotal"  />
												</div>
											</div>
										</td>
										<td class=" col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4 mi">要拆分的商检量</label>
												<div class="col-md-8">
													<input type="text" id="dGoodsInspect" onkeyup="config.clearNoNum(this)"  data-required="1" data-type="Require" class="form-control dGoodsInspect"  />
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
