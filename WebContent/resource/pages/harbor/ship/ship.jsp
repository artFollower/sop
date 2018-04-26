<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
<!--
#flow {
	width: 100%;
}
-->
</style>
<div class="modal fade">
	<div class="modal-dialog">

		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">选择船舶</h4>
			</div>
			<div class="modal-body">
				<div class="col-md-12 search" style="padding-left: 45px; padding-right: 65px;">
					<form id="goodsSearchForm" target="_self" class="form-horizontal searchCondition">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-4">英文名:</label>
								<div class="col-md-8">
									<input id="englishName" class="form-control" type="text" />
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group ">
								<div class="col-md-8 btn-group" style="float: right;">
									<button type="button" class="btn btn-success search">
										<span class="glyphicon glyphicon-search"></span>&nbsp;
									</button>
									<button type="button" style="margin-left: 8px;" class="btn btn-primary reset">
										<span class="fa fa-undo"></span>&nbsp;
									</button>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="col-md-12" style="padding-left: 45px; padding-right: 65px;">
					<div id="ship"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="close">取消</button>
					<button type="button" class="btn btn-success" id="save">提交</button>
				</div>
			</div>
		</div>
	</div>
</div>