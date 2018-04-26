<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width: 900px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">船舶信息</h4>
			</div>
			<div class="modal-body" style="padding-left:65px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal form-ship">
				<div class="form-body">
					<table width="100%">
						<tr>
							
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">船舶英文名<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" name="name" maxlength="32" data-required="1" class="form-control name"  />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
							
								<div style="border-color: #777;" class="portlet box blue ng-scope">
									<div style="background-color: #777;" class="portlet-title">
										<div class="caption">
											<i class="icon-edit"></i>船舶中文名称
										</div>
										<div class="tools">
											<a class="collapse" href="javascript:;" data-original-title="" title=""></a>
										</div>
									</div>
									<div class="portlet-body">
										<div class="table-toolbar">
											<div class="btn-group">
													<button class="btn btn-default btn-circle mar-r-10" id="shipRefAdd">
														<span class="fa fa-plus"></span>添加
													</button>
											</div>
										</div>
										<table id="goods-table" class="table table-striped table-hover table-bordered">
											<thead>
												<tr>
													<th>船舶中文名称</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody id="tbody">
											</tbody>
										</table>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">船籍</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="shipRegistry"  class="form-control shipRegistry" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">船长（米）</label>
									<div class="col-md-8">
										<input type="text" maxlength="10" name="shipLenth"  onkeyup="config.clearNoNum(this)"   class="form-control shipLenth"  />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">船宽（米）</label>
									<div class="col-md-8">
										<input type="text" maxlength="10" name="shipWidth"  onkeyup="config.clearNoNum(this)"   class="form-control shipWidth" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">满载吃水（米）</label>
									<div class="col-md-8">
										<input type="text" maxlength="10" name="shipDraught"  onkeyup="config.clearNoNum(this)"   class="form-control shipDraught"  />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">建造年份</label>
									<div class="col-md-8">
										<input type="text" maxlength="5" name="buildYear" onkeyup="config.clearNoNum(this)"   class="form-control buildYear" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">载重（吨）</label>
									<div class="col-md-8">
										<input type="text" maxlength="10" name="loadCapacity"  onkeyup="config.clearNoNum(this)"   class="form-control loadCapacity"  />
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">总吨（吨）</label>
									<div class="col-md-8">
										<input type="text" maxlength="10"  name="grossTons"  onkeyup="config.clearNoNum(this)"   class="form-control grossTons" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">净吨（吨）</label>
									<div class="col-md-8">
										<input type="text" maxlength="10" name="netTons"  onkeyup="config.clearNoNum(this)"   class="form-control netTons"  />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">干舷</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="notice"  class="form-control notice" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">船主</label>
									<div class="col-md-8">
										<input type="text"   name="owner"  class="form-control owner"  />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">负责人</label>
									<div class="col-md-8">
										<input type="text" maxlength="16"  name="manager"  class="form-control manager" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">联系人</label>
									<div class="col-md-8">
										<input type="text" maxlength="16"  name="contactName"  class="form-control contactName"  />
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">联系电话</label>
									<div class="col-md-8">
										<input type="text" maxlength="16"  name="contactPhone" class="form-control contactPhone" />
									</div>
								</div>
							</td>
							<td class=" col-md-6">
								<div class="form-group">
									<label class="control-label col-md-4">船舶代码</label>
									<div class="col-md-8">
										<input type="text" maxlength="20"  name="code"  class="form-control code" />
									</div>
								</div>
							</td>
						</tr>
						<tr>
						<td class=" col-md-6">
						<div class="form-group" style="display: none">
									<label class="control-label col-md-4">港序</label>
									<div class="col-md-8">
										<input type="text" maxlength="16"  name="port"  class="form-control port" />
									</div>
								</div>
						</td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="form-group">
									<label class="col-md-2 control-label">描述</label>
									<div class="col-md-10">
										<textarea class="form-control description" maxlength="100"  rows="3" name="description"></textarea>
									</div>
								</div>
							</td>
						</tr>

						
					</table>
				</div>
			
				</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="addShip" >提交</button>
			</div>
		</div>
	</div>
</div>
<script>
		
	
		
	</script>