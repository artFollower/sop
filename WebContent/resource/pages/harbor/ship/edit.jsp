<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<div class="portlet box grey">
	<div class="portlet-body form">
		<!-- BEGIN FORM-->
		<form class="form-horizontal shipForm">
			<input type="hidden" name="id" />
			<div class="form-body">
				<h4 class="form-section">船舶信息</h4>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">英文船名<span class="required"> * </span>
							</label>
							<div class="col-md-9">
								<div class="input-group">
									<input type="text" id="vesselCode" class="form-control" name="vesselCode" maxLength="25" data-required="1" data-type="Require" /> <span class="input-group-btn">
										<button class="btn default ship_select" type="button">
											<i class="fa fa-ship"></i>
										</button>
									</span>
								</div>
								<!-- <input type="text" id="vesselCode"  data-provide="typeahead" class="form-control" name="vesselCode" data-required="1"  data-type="Require"/> -->
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">中文船名<span class="required"> * </span>
							</label>
							<div class="col-md-9">
								<input type="text" name="vessel" class="form-control" data-required="1" data-type="Require" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">船舶国籍<span class="required"> * </span>
							</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="nationality" data-required="1" data-type="Require" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">航次号<span class="required"> * </span>
							</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="voyage" data-required="1" data-type="Require" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">班轮 </label>
							<div class="col-md-9">
								<select name="liner" class="form-control">
									<option value="">请选择</option>
									<option value="Y">班轮</option>
									<option value="N">非班轮</option>
									<option value="H">核心班轮</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">预报/确报<span class="required"> * </span>
							</label>
							<div class="col-md-9">
								<select name="confirgFlag" class="form-control" data-required="1" data-type="Require">
									<option value="0">预报</option>
									<option value="1">确报</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">到港时间 </label>
							<div class="col-md-9">
								<div class="input-group date datetime">
									<input type="text" size="16" readonly class="form-control" name="arrivlTime"> <span class="input-group-btn">
										<button class="btn default date-set" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">离港时间 </label>
							<div class="col-md-9">
								<div class="input-group date datetime">
									<input type="text" size="16" readonly class="form-control" name="sailingTime"> <span class="input-group-btn">
										<button class="btn default date-set" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">卸船开始时间 </label>
							<div class="col-md-9">
								<div class="input-group date datetime">
									<input type="text" size="16" readonly class="form-control" name="dischStartTime"> <span class="input-group-btn">
										<button class="btn default date-set" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">卸船结束时间</label>
							<div class="col-md-9">
								<div class="input-group date datetime">
									<input type="text" size="16" readonly class="form-control" name="dicshEndTime"> <span class="input-group-btn">
										<button class="btn default date-set" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">装船开始时间 </label>
							<div class="col-md-9">
								<div class="input-group date datetime">
									<input type="text" size="16" readonly class="form-control" name="loadStartTime"> <span class="input-group-btn">
										<button class="btn default date-set" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">装船结束时间 </label>
							<div class="col-md-9">
								<div class="input-group date datetime">
									<input type="text" size="16" readonly class="form-control" name="loadEndTime"> <span class="input-group-btn">
										<button class="btn default date-set" type="button">
											<i class="fa fa-calendar"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">船舶识别号<span class="required"> * </span>
							</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="shipUniqueNum" data-required="1" data-type="Require" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">泊位代码<span class="required"> * </span>
							</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="port" data-required="1" data-type="Require" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">进港吃水 (米)</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="befterDraft" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">离港吃水(米) </label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="afterDraft" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">实载客量 </label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="realCapacity" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">本港上下客 </label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="portPassenger" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">装卸货物数量(吨)<span class="required"> * </span>
							</label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="netWeight" data-required="1" data-type="Require" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label col-md-3">货物描述 </label>
							<div class="col-md-9">
								<input type="text" class="form-control" name="cargoDes" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
