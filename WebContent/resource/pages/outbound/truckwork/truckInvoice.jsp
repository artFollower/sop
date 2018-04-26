<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
	<div class="portlet box blue" style="border-color: #777;">
		<div class="portlet-title" style="background-color: #777;">
			<div class="caption">
				<i class="icon-user"></i>开票信息
			</div>
		</div>
		<input type="hidden" name="status">
		<input type="hidden" name="approveId">
		<div class="portlet-body">
			<div id="list-table_wrapper" class="dataTables_wrapper form-inline" role="grid">
				<div class="table-scrollable">
					<table class="table table-striped table-bordered table-hover" id="list-table">
						<thead>
							<tr>
							    <th>开票号</th>
								<th>货体号</th>
								<th>客户编号</th>
								<th>原始货主</th>
								<th>进船信息</th>
								<th>原号</th>
								<th>调号</th>
								<th>罐号</th>
								<th>当前存量(吨)</th>
								<th>开单数量(吨)</th>
								<th>仓位信息</th>
							</tr>
						</thead>
								<tbody id="billInfo">
								</tbody>
					</table>
				</div>

			</div>
		</div>
	</div>
	<div class="modal-footer">
	<button type="button" class="btn btn-default" data-dismiss="modal" onclick="window.location ='#/outboundtruckserial/list' ">返回</button>
	</div>


