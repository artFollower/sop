<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	
	<div class="portlet ">
		<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-file-text-o"></i>主备嫁接
				</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			

				<div class="row">
					
					<div class="col-md-6 col-sm-6">
					<div class="portlet  box blue-steel">
					
					<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-bell-o"></i>转出方
							</div>
							</div>
					<div class="portlet-body">
					<form action="#" class="form-horizontal  grafting-add-form">
					<table width="100%">
					<tr>
					<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">货主<span class="required">*</span></label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="clientId" data-required="1" data-type="Require" class="form-control clientId1" />
									</div>
								</div>
							</td>
							
							<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">货体号<span class="required">*</span></label>
									<div class="col-md-6 goodsId1" >
										<input type="text" maxlength="16" readonly name="goodsCode" data-required="1" data-type="Require" class="form-control goodsCode1" />
										<input type="text" maxlength="16" name="outCargoCode" data-required="1" data-type="Require" style="display: none" class="form-control outCargoCode" />
									
									</div>
									<div class="col-md-2 " >
								<button type="button" onclick="VirtualCargo.getTree()" class="btn btn-primary getTree" style="display: none" >查看</button>
							</div>
								</div>
							</td>
							
					</tr>
					
					<tr>
					
					<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">货品</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="productId" readonly class="form-control productId1" />
									</div>
								</div>
							</td>
					
					<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">存量</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="goodsCurrent" readonly class="form-control goodsCurrent1" />
									</div>
								</div>
							</td>
					</tr>
					
					
					<tr>
					<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">原号</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="rootCode" readonly class="form-control rootCode1" />
									</div>
								</div>
							</td>
							
							<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">调号</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="resourceCode" readonly class="form-control resourceCode1" />
									</div>
								</div>
							</td>
					</tr>
					
					
					
				<tr>
					<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">进量</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="goodsIn" readonly class="form-control goodsIn1" />
									</div>
								</div>
							</td>
							
							<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">出量</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="goodsOut" readonly class="form-control goodsOut1" />
									</div>
								</div>
							</td>
							
					</tr>
					<tr>
					<td class="col-md-6">
					</td>
					<td class="col-md-6">
							<div class="form-group">
							<label class="control-label col-md-4">方式</label>
							<div class="col-md-8">
								<input type="checkbox" class="make-switch type1" data-on-color="success" data-off-color="warning" checked data-on-text="整批" data-off-text="部分">
							</div>
						</div>
						</td>
					</tr>
					</table>
					</form>
					</div>
					</div>
					</div>
					
					<div class="col-md-6 col-sm-6">
					<div class="portlet box green-haze tasks-widget">
					
					<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-bell-o"></i>转入方
							</div>
							</div>
					<div class="portlet-body">
					<form action="#" class="form-horizontal  grafting-add-form">
										<table width="100%">
					<tr>
					<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">货主</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="clientId" readonly class="form-control clientId2" />
									</div>
								</div>
							</td>
							
							<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">货体号<span class="required">*</span></label>
									<div class="col-md-6 goodsId2">
										<input type="text" maxlength="16" readonly name="goodsCode" data-required="1" data-type="Require" class="form-control goodsCode2" />
									<input type="text" maxlength="16" name="inCargoCode" data-required="1" data-type="Require" style="display: none" class="form-control inCargoCode" />
									</div>
								<div class="col-md-2 " >
								<button type="button" onclick="VirtualCargo.getTree2()" class="btn btn-primary getTree2" style="display: none" >查看</button>
							</div>
								</div>
							</td>
							
					</tr>
					
					
					<tr>
					
					<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">货品</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="productId" readonly class="form-control productId2" />
									</div>
								</div>
							</td>
					
					
					<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">存量</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="goodsCurrent" readonly class="form-control goodsCurrent2" />
									</div>
								</div>
							</td>
							
							
							
					</tr>
					
					<tr>
					<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">原号</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="rootCode" readonly  class="form-control rootCode2" />
									</div>
								</div>
							</td>
							
							<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">调号</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="resourceCode" readonly class="form-control resourceCode2" />
									</div>
								</div>
							</td>
					</tr>
					
					
					
				<tr>
					<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">进量</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="goodsIn" readonly class="form-control goodsIn2" />
									</div>
								</div>
							</td>
							
							<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">出量</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="goodsOut" readonly class="form-control goodsOut2" />
									</div>
								</div>
							</td>
							
					</tr>
					
					
					
					
					<tr>
					<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">可转进量</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="canIn" readonly class="form-control canIn2" />
									</div>
								</div>
							</td>
							
							<td class=" col-md-6">
					<div class="form-group">
									<label class="control-label col-md-4">实转进量</label>
									<div class="col-md-8">
										<input type="text" maxlength="16" name="tIn" readonly class="form-control tIn2" />
									</div>
								</div>
							</td>
							
					</tr>
					
					
					</table>
					
					
					
					</form>
					</div>
					</div>
				</div>
				
				
				
				
				
				<div class="col-md-6 col-sm-6  out">
					<div data-role="outGrid">
				</div>
				</div>
				<div class="col-md-6 col-sm-6 in">
					<div data-role="inGrid">
				</div>
				</div>
				
				</div>

			<div class="modal-footer">
					<div class="col-md-offset-3 col-md-9">
						<a href="#/virtualCargo" class="btn btn-default">返回</a>
						<a href="javascript:void(0)" onclick="VirtualCargo.doGrafting()" class="btn btn-primary saveButton">嫁接</a>
					</div>
				</div>
				
		</div>
	</div>
	<!-- END PAGE -->
	<div class="modal fade" id="ajax" tabindex="-1" role="basic" aria-hidden="true">
	</div>
	<!-- 页面内容开结束-->
</body>