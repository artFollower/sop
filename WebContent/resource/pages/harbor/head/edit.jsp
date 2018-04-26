<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<div class="portlet box grey">
	<div class="portlet-body form">
		<!-- BEGIN FORM-->
		<form class="form-horizontal headForm">
			<input type="hidden" name="id"/>
			<div class="form-body">
				<h4 class="form-section">报文信息</h4>

				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">报文类型<span class="required"> * </span>
							</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="messageType" readonly="readonly" value="COARRI" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">文件说明<span class="required"> * </span>
							</label>
							<div class="col-md-9">
								<select name="fileDescription" class="form-control">
									<option value="DISCHARGE">卸货</option>
									<option value="LOAD REPORT">装货</option>
								</select>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">发送方<span class="required"> * </span>
							</label>
							<div class="col-md-9">
								<input type="text" class="form-control" value="长江石化" readonly="readonly"/>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">接收方代码<span class="required"> * </span>
							</label>
							<div class="col-md-9">
								<input type="text" class="form-control" value="信息中心" readonly="readonly"/>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">发送港代码 </label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="sendPortCode" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">接收港代码 </label>
							<div class="col-md-9">
								<input type="text" name="receiverPortCode" class="form-control" />
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">文件功能 </label>
							<div class="col-md-9">
								<input type="text" class="form-control" value="原始" readonly="readonly"/>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">文件建立时间 </label>
							<div class="col-md-9">
								<div class="input-group date datetime">
									<input type="text" size="16" readonly class="form-control" name="fileCreateTime"> <span class="input-group-btn">
										<button class="btn default date-set" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
