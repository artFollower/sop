<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">添加资源</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
				<form class="form-horizontal resource-form">
					<div class="form-group">
						<label class="col-lg-3 control-label">名称:<span class="required">*</span></label>
						<div class="col-lg-9">
							<input type="text" class="form-control" name="name" data-required="1" data-type="Require">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">标识:<span class="required">*</span></label>
						<div class="col-lg-9">
							<input type="text" class="form-control" name="indentifier" data-required="1" data-type="Require">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">类型:<span class="required">*</span></label>
						<div class="col-lg-9">
							<select  name="category" class="form-control category" data-required="1"  data-type="Require">
				          	 <option value="MENU">菜单资源</option>
				             <option value="URL">URL资源</option>
				             <option value="ACCESS">操作资源</option>
				           </select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">状态:<span class="required">*</span></label>
						<div class="col-lg-9">
							<select  name="status" class="form-control" data-required="1"  data-type="Require">
				          	 <option value="0">活跃</option>
				             <option value="1">锁定</option>
				           </select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">所属:</label>
						<div class="col-lg-9">
							<select  name="parent.id" class="form-control">
				          	 <option value="">请选择所属</option>
				           </select>
						</div>
					</div>
					<!-- <div class="form-group menu-par">
						<label class="col-lg-3 control-label">父菜单:<span class="required">*</span></label>
						<div class="col-lg-9">
							<select  name="category" class="form-control parentMenu">
				           </select>
						</div>
					</div>
					<div class="form-group menu-icon">
						<label class="col-lg-3 control-label">图标:<span class="required">*</span></label>
						<div class="col-lg-9">
                            <span id="menuIcon" class="menu-icon"></span>
                            <input type="hidden" name="menuIcon" id="icon"/>
                            <button id="iconBtn" type="button" class="btn btn-info">浏览图片</button>
                        </div>
					</div> -->
					<div class="form-group">
						<label class="col-lg-3 control-label">描述:</label>
						<div class="col-lg-9">
							<input type="text" class="form-control" name="description">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="close">取消</button>
				<button type="button" class="btn btn-success" id="save">保存</button>
			</div>
		</div>
	</div>
</div>
