<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!-- <style>
td
{
  font-size: 13px;
  margin-left: 30px;
}

td[type="col-6"]
{
width: 25%;
   margin-left: 30px;
  font-size: 13px;
}

div[type="form-group"]
{

   margin-left: 15px;
   margin-top:1px;
  font-size: 13px;
}


label[type="col-4"]
{
	width: 100%;
    float: left;
        min-height: 1px;
    padding-left: 30px;
        margin-bottom: 0px;
}

label[type="col-8"]
{
	width: 100%;
    float: left;
        min-height: 1px;
    padding-left: 30px;
        margin-bottom: 0px;
}

</style> -->
<link href="<%=basePath %>/resource/admin/layout/css/inboundPrint.css" rel="stylesheet" type="text/css"  media="print"/>

<div class="modal fade">
	<div class="modal-dialog modal-wide" style="width:600px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">入库及损耗通知单</h4>
			</div>
			<div class="form-group" style="padding-top: 10px;">
									<label class="control-label col-md-4">打印时间</label>
			<div
											class="input-group date-picker input-daterange"
											data-date-format="yyyy-mm-dd">
											<input type="text" class="form-control pTime" 
												name="from" id="pTime">
												</div>
												</div>
			<div class="modal-body "  >

				<div style="height: 1%"></div>
				<div style="height: 31%">
				<h4 style="text-align: right ; float:right" id="No"></h4>
				<h4 style="text-align: center;font-family:宋体">江苏长江石油化工有限公司</h4>
				<h4 style="text-align: center;font-family:宋体">入库及损耗通知单</h4>
				<label class="control-label " style="text-align: right;font-size: 12px;float:right;font-family:宋体">客户编号:<span id='clientCode'></span></label>
				
				<label class="control-label " style="text-align: left;font-size: 12px;float:left;font-family:宋体" id="clientName"></label>
				
				
				<table class="printS" type="inbound" style=" width: 100%;font-size: 13px;line-height: 25px " border='0'  cellspacing='0'  align='center'>
				
				
				
				 <tr>
				    <td type="col-6" >
								<div >
									<label  type="col-4" >船名</label>
								</div>
							</td>
							 <td type="col-6" >
								<div >
									<label  type="col-8"  id="shipName"> </label>
								</div>
							</td>
							<td  type="col-6" >
								<div >
									<label  type="col-4" >进货日期</label>
								</div>
							</td>
							<td  type="col-6"  >
								<div >
									<label type="col-8" id="arrivalTime"> 2015-10-07 </label>
								</div>
							</td>
				    
				  </tr>
				  
				  <tr>
				    
							
							<td  type="col-6"  >
								<div >
									<label  type="col-4" >品名</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="productName"></label>
								</div>
							</td>
				    
				    <td  type="col-6"  >
								<div >
									<label  type="col-4"  >批号</label>
								</div>
							</td>
							 <td  type="col-6"  >
								<div >
									<label  type="col-8" id="code"> </label>
								</div>
							</td>
							
				  </tr>
				  
				  <tr>
							
							<td  type="col-6" >
								<div >
									<label  type="col-4" >提单数</label>
								</div>
							</td>
				    
				    <td  type="col-6"  >
								<div >
									<label  type="col-8" id="goodsPlan"> </label>
								</div>
							</td>
				    
				    
							<td  type="col-6" >
								<div >
									<label  type="col-4"  >商检数</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="goodsInspect">  </label>
								</div>
							</td>
							
				  </tr>
				  
				 <tr>
							
							<td  type="col-6" >
								<div >
									<label  type="col-4" >损耗</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="lossRate"> </label>
								</div>
							</td>
							
				    <td  type="col-6" >
								<div >
									<label  type="col-4" >实际入库量</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="goodsTotal"> </label>
								</div>
							</td>
				  </tr>
				
				 <tr>
				    <td  type="col-6" >
								<div >
									<label  type="col-4"  >提单号</label>
								</div>
							</td>
							
							   <td  type="col-6"  >
								<div >
									<label  type="col-8" id="ladingCode"> </label>
								</div>
							</td>
							
							
				     <td  type="col-6"  >
								<div >
									<label  type="col-4" >备注</label>
								</div>
							</td>
							<td  type="col-6" >
								<div >
									<label  type="col-8" id="description"> </label>
								</div>
							</td>
							
							
				  </tr>
				  
				 
				
				  
					<tr>
				    <td  type="col-6" >
								<div >
									<label  type="col-4"  >编制人</label>
								</div>
							</td>
							
							   <td  type="col-6"  >
								<div >
									<label  type="col-8" id="createUser"> </label>
								</div>
							</td>
							
							
				     <td  type="col-6"  >
								<div >
									<label  type="col-4" >复核人</label>
								</div>
							</td>
							<td  type="col-6" >
								<div >
									<label  type="col-8" id=""> </label>
								</div>
							</td>
							
							
				  </tr>
				  
				  

				</table>
				
				
				
				
				<label   style="font-size: 12px;font-family:宋体">编制日期:</label>
				<label   style="font-size: 12px;font-family:宋体" id="createTime">  </label>
				<label style="text-align: right ; float:right;font-size: 12px;font-family:宋体"> 第一联：存根 </label>
				
				</div>
				
				
				<div style="height: 31%">
				<h4 style="text-align: right ; float:right" id="No"></h4>
				<h4 style="text-align: center;font-family:宋体">江苏长江石油化工有限公司</h4>
				<h4 style="text-align: center;font-family:宋体">入库及损耗通知单</h4>
				<label class="control-label " style="text-align: right;font-size: 12px;float:right;font-family:宋体">客户编号:<span id='clientCode'></span></label>
				
				<label class="control-label " style="text-align: left;font-size: 12px;float:left;font-family:宋体" id="clientName"></label>
				
				<table class="printS" type="inbound" style=" width: 100%;font-size: 13px;line-height: 25px " border='0'  cellspacing='0'  align='center'>
				
				
				
				 <tr>
				    <td type="col-6" >
								<div >
									<label  type="col-4" >船名</label>
								</div>
							</td>
							 <td type="col-6" >
								<div >
									<label  type="col-8"  id="shipName"> </label>
								</div>
							</td>
							<td  type="col-6" >
								<div >
									<label  type="col-4" >进货日期</label>
								</div>
							</td>
							<td  type="col-6"  >
								<div >
									<label type="col-8" id="arrivalTime"> 2015-10-07 </label>
								</div>
							</td>
				    
				  </tr>
				  
				  <tr>
				    
							
							<td  type="col-6"  >
								<div >
									<label  type="col-4" >品名</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="productName"></label>
								</div>
							</td>
				    
				    <td  type="col-6"  >
								<div >
									<label  type="col-4"  >批号</label>
								</div>
							</td>
							 <td  type="col-6"  >
								<div >
									<label  type="col-8" id="code"> </label>
								</div>
							</td>
							
				  </tr>
				  
				  <tr>
							
							<td  type="col-6" >
								<div >
									<label  type="col-4" >提单数</label>
								</div>
							</td>
				    
				    <td  type="col-6"  >
								<div >
									<label  type="col-8" id="goodsPlan"> </label>
								</div>
							</td>
				    
				    
							<td  type="col-6" >
								<div >
									<label  type="col-4"  >商检数</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="goodsInspect">  </label>
								</div>
							</td>
							
				  </tr>
				  
				 <tr>
							
							<td  type="col-6" >
								<div >
									<label  type="col-4" >损耗</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="lossRate"> </label>
								</div>
							</td>
							
				    <td  type="col-6" >
								<div >
									<label  type="col-4" >实际入库量</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="goodsTotal"> </label>
								</div>
							</td>
				  </tr>
				
				 <tr>
				    <td  type="col-6" >
								<div >
									<label  type="col-4"  >提单号</label>
								</div>
							</td>
							
							   <td  type="col-6"  >
								<div >
									<label  type="col-8" id="ladingCode"> </label>
								</div>
							</td>
							
							
				     <td  type="col-6"  >
								<div >
									<label  type="col-4" >备注</label>
								</div>
							</td>
							<td  type="col-6" >
								<div >
									<label  type="col-8" id="description"> </label>
								</div>
							</td>
							
							
				  </tr>
				  
				 
				
				  
					<tr>
				    <td  type="col-6" >
								<div >
									<label  type="col-4"  >编制人</label>
								</div>
							</td>
							
							   <td  type="col-6"  >
								<div >
									<label  type="col-8" id="createUser"> </label>
								</div>
							</td>
							
							
				     <td  type="col-6"  >
								<div >
									<label  type="col-4" >复核人</label>
								</div>
							</td>
							<td  type="col-6" >
								<div >
									<label  type="col-8" id=""> </label>
								</div>
							</td>
							
							
				  </tr>
				  
				  

				</table>
				
				
				
				
				<label   style="font-size: 12px;font-family:宋体">编制日期:</label>
				<label  style="font-size: 12px;font-family:宋体" id="createTime">  </label>
				<label style="text-align: right ; float:right;font-size: 12px;font-family:宋体"> 第二联：商务部 </label>
				
				</div>
				<div style="height: 30%">
				<h4 style="text-align: right ; float:right" id="No"></h4>
				<h4 style="text-align: center;font-family:宋体">江苏长江石油化工有限公司</h4>
				<h4 style="text-align: center;font-family:宋体">入库及损耗通知单</h4>
				<label class="control-label " style="text-align: right;font-size: 12px;float:right;font-family:宋体">客户编号:<span id='clientCode'></span></label>
				
				<label class="control-label " style="text-align: left;font-size: 12px;float:left;font-family:宋体" id="clientName"></label>
				
				<table class="printS" type="inbound" style=" width: 100%;font-size: 13px;line-height: 25px " border='0'  cellspacing='0'  align='center'>
				
				
				
				 <tr>
				    <td type="col-6" >
								<div >
									<label  type="col-4" >船名</label>
								</div>
							</td>
							 <td type="col-6" >
								<div >
									<label  type="col-8"  id="shipName"> </label>
								</div>
							</td>
							<td  type="col-6" >
								<div >
									<label  type="col-4" >进货日期</label>
								</div>
							</td>
							<td  type="col-6"  >
								<div >
									<label type="col-8" id="arrivalTime"> 2015-10-07 </label>
								</div>
							</td>
				    
				  </tr>
				  
				  <tr>
				    
							
							<td  type="col-6"  >
								<div >
									<label  type="col-4" >品名</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="productName"></label>
								</div>
							</td>
				    
				    <td  type="col-6"  >
								<div >
									<label  type="col-4"  >批号</label>
								</div>
							</td>
							 <td  type="col-6"  >
								<div >
									<label  type="col-8" id="code"> </label>
								</div>
							</td>
							
				  </tr>
				  
				  <tr>
							
							<td  type="col-6" >
								<div >
									<label  type="col-4" >提单数</label>
								</div>
							</td>
				    
				    <td  type="col-6"  >
								<div >
									<label  type="col-8" id="goodsPlan"> </label>
								</div>
							</td>
				    
				    
							<td  type="col-6" >
								<div >
									<label  type="col-4"  >商检数</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="goodsInspect">  </label>
								</div>
							</td>
							
				  </tr>
				  
				 <tr>
							
							<td  type="col-6" >
								<div >
									<label  type="col-4" >损耗</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="lossRate"> </label>
								</div>
							</td>
							
				    <td  type="col-6" >
								<div >
									<label  type="col-4" >实际入库量</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="goodsTotal"> </label>
								</div>
							</td>
				  </tr>
				
				 <tr>
				    <td  type="col-6" >
								<div >
									<label  type="col-4"  >提单号</label>
								</div>
							</td>
							
							   <td  type="col-6"  >
								<div >
									<label  type="col-8" id="ladingCode"> </label>
								</div>
							</td>
							
							
				     <td  type="col-6"  >
								<div >
									<label  type="col-4" >备注</label>
								</div>
							</td>
							<td  type="col-6" >
								<div >
									<label  type="col-8" id="description"> </label>
								</div>
							</td>
							
							
				  </tr>
				  
				 
				
				  
					<tr>
				    <td  type="col-6" >
								<div >
									<label  type="col-4"  >编制人</label>
								</div>
							</td>
							
							   <td  type="col-6"  >
								<div >
									<label  type="col-8" id="createUser"> </label>
								</div>
							</td>
							
							
				     <td  type="col-6"  >
								<div >
									<label  type="col-4" >复核人</label>
								</div>
							</td>
							<td  type="col-6" >
								<div >
									<label  type="col-8" id=""> </label>
								</div>
							</td>
							
							
				  </tr>
				  
				  

				</table>
				
				
				
				
				<label  style="font-size: 12px;font-family:宋体">编制日期:</label>
				<label  style="font-size: 12px;font-family:宋体" id="createTime">  </label>
				<label style="text-align: right ; float:right;font-size: 12px;font-family:宋体"> 第三联：客户联</label>
				
				</div>
				
			</div>
			
			
			<div class="modal-footer">
				<button type="button" class="btn default btn-print">打印</button>
				<button type="button" class="btn default" data-dismiss="modal">关闭</button>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>
</div>
