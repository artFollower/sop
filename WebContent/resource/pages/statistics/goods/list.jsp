<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>
<div class="row">
	<div class="col-md-12">
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-file-text-o"></i>货体列表<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right"><a style="padding-left: 20px;" class="hidden-print" onclick="javascript:Goods.exportExcel();"> <i class="fa fa-file-excel-o">&nbsp;导出</i></a></span>
					<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right"><a style="padding-left: 20px;" class="hidden-print" onclick="javascript:Goods.dayStatics();"> <i class="fa fa-file-excel-o">&nbsp;&nbsp;&nbsp;导出历史日报表</i></a></span>
				</div>
				<div class="actions" style="display: none">
					<div class="btn-group">
						<a class="btn default yellow-stripe" href="javascript:void(0)" data-toggle="dropdown"> <i class="fa fa-share"></i> <span class="hidden-480"> 工具 </span> <i class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu pull-right">
							<li><a href="javascript:void(0)"> 导出EXCEL </a></li>
							<li><a href="javascript:void(0)"> 导出CSV </a></li>
							<li><a href="javascript:void(0)"> 导出XML </a></li>
							<li class="divider"></li>
							<li><a href="javascript:void(0)"> 打印 </a></li>
							<li><a href="#/contractAdd">添加</a></li>
						</ul>
					</div>
				</div>
			</div>
				
			<div>
				<form id="goodsListForm" target="_self" class="form-horizontal searchCondition">
				<div class="form-group">
				
				<label class="control-label col-md-1 " style="width:70px;float:left;display: none" >货体号:</label>
						<div class="col-md-2 "style="display: none">
							<input id="code" type="text" name="code"  class="form-control code">
						</div>
						
						<label class="control-label col-md-1 " style="width:70px;float:left;" >货批号:</label>
						<div class="col-md-2">
							<input id="cargoCode" type="text" name="cargoCode"   class="form-control cargoCode">
						</div>
				
				<label class="control-label col-md-2 " style="width:120px;float:left;" >货主:</label>
						<div class="col-md-4">
							<input id="client" type="text" name="clientId" data-provide="typeahead"  class="form-control client">
						</div>
						
				</div>
				
				<div class="form-group">
				
				      <label class="control-label col-md-1 " style="width:70px;float:left;" >货品:</label>
						<div class="col-md-2">
							<input id="product" type="text" name="productId" data-provide="typeahead"  class="form-control product">
						</div>
						<label class="control-label col-md-1 " style="width:70px;float:left;" >罐号:</label>
						<div class="col-md-2">
							<input id="tankId" type="text" name="tankId" data-provide="typeahead"  class="form-control tankId">
						</div>
				      						<label class="control-label col-md-4" style="width:120px;float:left;">统计日期:</label>
												<div class="col-md-4">
													<div
														class="input-group input-large date-picker input-daterange"
														data-date-format="yyyy-mm-dd">
														<input type="text" class="form-control" name="sStartTime" 
															id="startTime"><span class="input-group-addon">到</span>
														<input type="text" class="form-control" name="sEndTime" 
															id="endTime">
													</div>
												</div>
												
												
												
																			<button onclick="Goods.search()" type="button" style="position:relative; margin-left:35px" class="btn btn-success"><span class="glyphicon glyphicon-search"></span>&nbsp;</button>
						
												<div class="checkbox feeBillDiv" style="float:right;">
												
                                 <label class="checkbox-inline"> <input
											type="checkbox" data='1' id="isShowAll" value="option2">显示统计信息
										</label>
                              </div>	
												
												
												</div>
												
				</form>
			<div class="btn-group buttons" >
				<button class="btn btn-default btn-circle mar-r-10 btn-pass" style="display: none" type="button">
					<span class="fa fa-edit"></span><span class="text">海关放行</span>
				</button>
				<button id="sample_editable_2_new" class="btn btn-default btn-circle mar-r-10 btn-changeTank" onclick="Goods.changeTank()" type="button">
					<span class="fa fa-plus"></span><span class="text">货体换罐</span>
				</button>
				<button class="btn btn-default btn-circle mar-r-10 order-tank" type="button">
					<span class="text">罐号</span><span class="fa fa-arrows-v"></span>
				</button>
				<button class="btn btn-default btn-circle mar-r-10 order-jc" type="button">
					<span class="text">建仓</span><span class="fa fa-arrows-v"></span>
				</button>
				
				<button class="btn btn-default btn-circle mar-r-10 btn-simple" type="button">
					<span class="text simple">点击切换精简模式</span>
				</button>
				
				<button class="btn btn-default btn-circle mar-r-10 show" type="button">
					<span class="text" id="showText">显示全部</span>
				</button><span class="text" id="sText">当前显示未清盘</span>
					
				
			</div>
			
			<button class="btn btn-default btn-square showVir"  data-placement="bottom" data-content="主库" style="text-align: right;float:right"type="button">
					
				</button>
				
				<button class="btn btn-default btn-square showVirTime" data-placement="bottom" data-content="转账时间" style="text-align: right;float:right;margin-right: 20px"type="button">
					
				</button>
					<div id="scrollbar"> 
           	<div class="viewport">
				<div id="grid">
			<div data-role="goodsListGrid" class="overview">
			</div>
			</div>
			</div>
			</div>
			</div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->
</body>
</html>

