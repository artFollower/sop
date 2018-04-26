<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width:800px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">预入库的货体</h4>
			</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
                <label class="hidden" id="goodsTotal"> </label>
					<div class="btn-group buttons">
				<button class="btn btn-default btn-circle mar-r-10 btn-add" type="button">
					<span class="fa fa-plus"></span><span class="text">添加</span>
				</button>
				<button class="btn btn-default btn-circle mar-r-10 btn-modify" type="button" style="display: none">
						<span class="fa fa-edit"></span><span class="pad-l-5">修改</span>
					</button>
				<button class="btn btn-default btn-circle mar-r-10 btn-remove" type="button">
						<span class="fa fa-remove"></span><span class="pad-l-5">删除</span>
					</button> 
			</div>
			<div data-role="goodsGrid">
			</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">关闭</button>
		</div>
	</div>
	<!-- /.modal-content -->
	</div>
</div>
