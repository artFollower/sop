<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal-dialog" style="width:900px">
	<div class="modal-content" >
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<h4 class="modal-title">编辑货体</h4>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet-body form">
						<form class="form-horizontal">
							<div class="form-body">
								<table width="100%">
									<tr>
										<td class=" col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4">客户名称<span class="required">*</span></label>
												<div class="col-md-8">
													<input type="text" id="serchCustName" class="form-control intentTitle" readonly />
												</div>
											</div>
										</td>
										<td class=" col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4">货品名称<span class="required">*</span></label>
												<div class="col-md-8">
													<input type="text" id="serchGoodsName" class="form-control intentTitle" readonly />
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td class=" col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4">提单类型<span class="required">*</span></label>
												<div class="col-md-8">
													<input type="text" id="ladingType" class="form-control intentTitle" readonly />
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<div class="form-group">
												<div class="col-md-10">
													<div class="fluid">
														<div class="col-md-offset-7 col-md-9">
															<button type="button" class="btn green serchButton">检索</button>
														</div>
													</div>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</form>
					</div>
					<div class="portlet box yellow">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-edit"></i>货体信息
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body">
							<table class="table table-striped table-hover table-bordered" id="ladingGoodsTable">
								<thead>
									<tr>
										<th style="width1: 8px;"><input type="checkbox" class="group-checkable" data-set="#ladingGoodsTable .checkboxes" /></th>
										<th>货体编号</th>
										<th>客户名称</th>
										<th>货品名称</th>
										<th>当前存量(吨)</th>
										<th>放入提单数量(吨)</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">关闭</button>
			<button type="button" class="btn blue button-add-goods">确定</button>
		</div>
	</div>
	<!-- /.modal-content -->
</div>
