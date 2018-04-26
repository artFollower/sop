<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 80%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog ">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">出港计划与开票差异详情</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal addGoodsForm">
					<div class="form-body">
						<div data-role="planAndInvoiceGrid">
						<div style="min-height: 150px; width: 100%; overflow-y: auto;" class="grid-body">
						<table style="margin: 0px; border: 0px;" class="table table-responsive table-bordered table-hover">
						<thead class="grid-table-head" style="width: 100%;">
						<tr>
							<th style="text-align:center;" index="0">货批号<div class="colResize"></div></th>
							<th style="text-align:center;" index="1">货体号<div class="colResize"></div></th>
							<th style="text-align:center;" index="2">船信<div class="colResize"></div></th>
							<th style="text-align:center;" index="3">计划量<div class="colResize"></div></th>
							<th style="text-align:center;" index="4">开票通知单号<div class="colResize"></div></th>
							<th style="text-align:center;" index="5">开票量<div class="colResize"></div></th>
							<th style="text-align:center;" index="6">计划提单号<div class="colResize"></div></th>
							<th style="text-align:center;" index="7">开票提单号<div class="colResize"></div></th>
							<th width="auto" style="text-align:center;" index="8">操作<div class="colResize"></div></th>
							</tr></thead>
							<tbody class="grid-table-body" id="planAndInvoiceTbody" style="width:100%; height: auto; position: relative;">
							</tbody>
							</table>
							</div>
								</div>
									</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>
