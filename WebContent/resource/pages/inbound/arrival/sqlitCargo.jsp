<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width:900px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">拆分货批</h4>
			</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet-body form">
						<form class="form-horizontal sqlitCargo">
							<div class="form-body">
								<table width="100%">
									<tr>
										<td class=" col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4">客户名称<span class="required">*</span></label>
												<div class="col-md-8">
													<input type="text" id="dClientId" maxlength='32' class="form-control dClientId" data-required="1" data-type="Require"  />
												</div>
											</div>
										</td>
										<td class=" col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4">货品数量(吨)<span class="required">*</span></label>
												<div class="col-md-8">
													<input type="text" id="dGoodsTotal" maxlength='10' class="form-control dGoodsTotal" readonly />
												</div>
											</div>
										</td>
									</tr>
									
									<!-- <tr>
										<td class=" col-md-4">
										<div class="form-group">
										<div class="col-md-2"></div>
										<div class="col-md-8"> 
												<label class="checkbox-inline"> <input type="checkbox" value="" id="isCreateGoods" name="isCreateGoods"> 同步生成新货体
												</label> 
													</div>
										</div>
										</td>
									</tr> -->
									
								</table>
							</div>
						</form>
					</div>
					<div class="portlet box blue" style="border-color: #777">
						<div class="portlet-title" style="background-color: #777">
							<div class="caption">
								<i class="icon-edit"></i>原货批货体信息
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body">
							<table class="table table-striped table-hover table-bordered" id="sqlitCargoTable">
								<thead>
									<tr>
										<th style="width1: 8px;"><input type="checkbox" class="group-checkable" style="display: none" data-set="sqlitCargoTable .checkboxes" /></th>
										<th style="text-align: center">货体编号</th>
										<th style="text-align: center">货体总量(吨)</th>
										<th style="text-align: center">当前存量(吨)</th>
										<th style="text-align: center">放入新货批数量(吨)</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
			<div>
			<label class="control-label">总计：</label>
			<label class="control-label" id="count">0</label>
			</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">关闭</button>
			<button type="button" class="btn btn-primary button-add-cargo">确定</button>
		</div>
	</div>
	<!-- /.modal-content -->
	</div>
</div>
