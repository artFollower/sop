<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content" style="width: 850px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">选择船舶</h4>
			</div>
			<div class="modal-body" style="padding-left: 45px; padding-right: 65px;">
				<div class="search-form-default col-md-12 col-sm-12" id="queryUserDivId">
					<div class="input-group">
						<input type="text" placeholder="输入搜索信息" class="form-control search-key btn-circle-l" /> <span class="input-group-btn">
							<button type="button" class="btn green-haze btn-circle-r btn-default" id="managerSearch" style="border-top-width: 0px; border-bottom-width: 0px;">
								搜索 <i class="m-icon-swapright m-icon-white"></i>
							</button>
						</span>
					</div>
				</div>
				<div data-role="shipGrid" class="col-md-12 col-sm-12"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="close">取消</button>
					<button type="button" class="btn btn-success" id="save">提交</button>
				</div>
			</div>
		</div>
	</div>
</div>
