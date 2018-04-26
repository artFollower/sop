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
					<i class="fa fa-file-text-o"></i>分类日志查询<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right"><a style="padding-left: 20px;" class="hidden-print" onclick="javascript:GoodsLog.exportExcel();"> <i class="fa fa-file-excel-o">&nbsp;导出</i></a></span>
				</div>
				
			</div>
				
			<div>
				<form id="logForm" target="_self" class="form-horizontal searchCondition">
				
				<div class="form-group">
				
				<label class="control-label col-md-1 " style="width:80px;float:left;display: none" >货体号:</label>
						<div class="col-md-2" style="display: none">
							<input id="goodsCode" type="text" name="goodsCode"   class="form-control goodsCode">
						</div>
						
						<label class="control-label col-md-1 " style="width:80px;float:left;" >货批号:</label>
						<div class="col-md-2 cCode">
							<input id="cargoCode" type="text" name="cargoCode"  class="form-control cargoCode">
						</div>
				
				<label class="control-label col-md-2 " style="width:120px;float:left;" >提单:</label>
						<div class="col-md-4">
							<input id="ladingCode" type="text" name="ladingCode"   class="form-control ladingCode">
						</div>
						<label class="checkbox-inline" > <input type="checkbox" value="" id="isDim" name="isDim"> 模糊
								</label> 
				</div>
				
				
				<div class="form-group">
				
				
				<label class="control-label col-md-2 " style="width:120px;float:left;" >货主:</label>
						<div class="col-md-4 cClient">
							<input id="client" type="text" name="clientId" data-provide="typeahead"  class="form-control client">
						</div>
						
				<label class="control-label col-md-2 " style="width:120px;float:left;" >货品:</label>
						<div class="col-md-4">
							<input id="product" type="text" name="productId" data-provide="typeahead"  class="form-control product">
						</div>
				</div>
				
				<div class="form-group">
				
				      
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
												
												<label class="control-label col-md-2 " style="width:120px;float:left;" ></label>
						<div class="col-md-4" >
						<input type=radio name=radio checked data='0' class='i'>全部
  						<input type=radio name=radio data='1' class='i'>入库(汇总)
  						<input type=radio name=radio data='12' class='i'>入库(详细)
  						<input type=radio name=radio data='4' class='i'>封放
  						<input type=radio name=radio data='2' class='i'>调入
  						<input type=radio name=radio data='3' class='i'>调出
  						<input type=radio name=radio data='10' class='i'>扣损
  						<input type=radio name=radio data='5' class='i'>发货
  						<input type=radio name=radio data='51' class='i'>车发
  						<input type=radio name=radio data='52' class='i'>船发
  						<input type=radio name=radio data='53' class='i'>转输
  						
  						<input type=radio name=radio data='20' class='i'>待提
  						<input type=radio name=radio data='21' id='io'>进出存
  						<input type=radio name=radio data='22' id='i'>发货清单
  						<input type=radio name=radio data='11' id='i'>通过货批
  						
  						
												
												
												</div>
												<button onclick="GoodsLog.search()" type="button" style="position:relative; margin-left:35px; top: -15px" class="btn btn-success"><span class="glyphicon glyphicon-search"></span>&nbsp;</button>
												<button  id="inout"  type="button" style="display:none;position:relative; margin-left:35px; top: -15px" class="btn blue inout"><span >简</span>&nbsp;</button>
									<button class="btn btn-primary reset" onclick="GoodsLog.reset()" style="position:relative; margin-left:35px; top: -15px" type="button">
												<span title="重置" class="fa fa-undo"></span>&nbsp;
											</button>
				<button class="btn btn-default btn-square showVirTime" data-placement="bottom" data-content="原始时间" style="text-align: right;float:right;margin-right: 20px"type="button">
					
				</button>
				</div>
				<div id="logGrid">
						<div data-role="GoodslogGrid">
			</div>			
				</div>					
				</form>
			
			</div>
		</div>
		<!-- End: life time stats -->
	</div>
</div>
<!-- END MAIN CONTENT -->
</body>
</html>

