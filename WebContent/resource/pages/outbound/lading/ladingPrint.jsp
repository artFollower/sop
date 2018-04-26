<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<link href="<%=basePath %>/resource/admin/layout/css/ladingPrint.css" rel="stylesheet" type="text/css"  media="print" />

<div class="modal fade printS" style="font-family:宋体"  >
	<div class="modal-dialog modal-wide" style="width:600px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">调拨通知单</h4>
			</div>
			<div class="modal-body "  >
				
				<div style="height: 30%">
				<h6 style="text-align: right ; float:right" id="No"></h6>
				<h4 style="text-align: center;font-family:宋体">江苏长江石油化工有限公司</h4>
				<h4 style="text-align: center;font-family:宋体">货权转让通知单</h4>
				<!-- <h6 style="text-align: right;font-family:宋体;float:right">流水号:<span id='clientCode'></span></h6>
				 -->
				
				<table class="printS" type="lading" style=" width: 100%;font-size: 13px;line-height: 25px " border='0'  cellspacing='0'  align='center'>
				
				
				
				 <tr>
				    <td type="col-6" >
								<div >
									<label  type="col-4" >收货人</label>
								</div>
							</td>
							 <td type="col-6" >
								<div >
									<label  type="col-8"  id="receiveClientName">  </label>
								</div>
							</td>
							<td  type="col-6" >
								<div >
									<label  type="col-4" >货权出让方</label>
								</div>
							</td>
							<td  type="col-6"  >
								<div >
									<label type="col-8" id="clientName">  </label>
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
									<label  type="col-4"  >数量（吨）</label>
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
									<label  type="col-4" >提单类型</label>
								</div>
							</td>
				    
				    <td  type="col-6"  >
								<div >
									<label  type="col-8" id="type"> </label>
								</div>
							</td>
				    
				    
							<td  type="col-6" >
								<div >
									<label  type="col-4"  >提单号</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="code">  </label>
								</div>
							</td>
							
				  </tr>
				  
				 <tr>
							
							<td  type="col-6" >
								<div >
									<label  type="col-4" >转让日期</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="createTime"> </label>
								</div>
							</td>
							
				    	<td  type="col-6" >
								<div >
									<label  type="col-4" >仓储费起计日期</label>
								</div>
							</td>
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id=time> </label>
								</div>
							</td>
				  </tr>
				
				 <tr>
				   
							
				     <td  type="col-6"  >
								<div >
									<label  type="col-4" >备注</label>
								</div>
							</td>
							<td colspan="3" type="col-6" >
								<div >
									<label  type="col-8" id="description"> </label>
								</div>
							</td>
							
							
				  </tr>
				  
				 
				
				  
					  <tr>
							
							<td  type="col-6" >
								<div >
									<label  type="col-4" >审单</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="createUser"> </label>
								</div>
							</td>
							
				    	<td  type="col-6" >
								<div >
									<label  type="col-4" >复核</label>
								</div>
							</td>
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id=fh> </label>
								</div>
							</td>
				  </tr>
				  
				  

				</table>
				<label class="control-label "  style="font-size: 12px;font-family:宋体">编制日期:</label>
				<label class="control-label "  style="font-size: 12px;font-family:宋体" id="now">  </label>
				<label style="text-align: right ; float:right;font-size: 12px;font-family:宋体"> 第一联：收货人 </label>
				
				</div>
				
			
				<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
				<div style="height: 3%"></div>
				<div style="height: 28%">
				<h6 style="text-align: right ; float:right" id="No"></h6>
				<h4 style="text-align: center;font-family:宋体">江苏长江石油化工有限公司</h4>
				<h4 style="text-align: center;font-family:宋体">货权转让通知单</h4>
				<!-- <h6 style="text-align: right;font-family:宋体;float:right">流水号:<span id='clientCode'></span></h6>
				 -->
				<table class="printS" type="lading" style=" width: 100%;font-size: 13px;line-height: 25px " border='0'  cellspacing='0'  align='center'>
				
				
				
				 <tr>
				    <td type="col-6" >
								<div >
									<label  type="col-4" >收货人</label>
								</div>
							</td>
							 <td type="col-6" >
								<div >
									<label  type="col-8"  id="receiveClientName">  </label>
								</div>
							</td>
							<td  type="col-6" >
								<div >
									<label  type="col-4" >货权出让方</label>
								</div>
							</td>
							<td  type="col-6"  >
								<div >
									<label type="col-8" id="clientName">  </label>
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
									<label  type="col-4"  >数量（吨）</label>
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
									<label  type="col-4" >提单类型</label>
								</div>
							</td>
				    
				    <td  type="col-6"  >
								<div >
									<label  type="col-8" id="type"> </label>
								</div>
							</td>
				    
				    
							<td  type="col-6" >
								<div >
									<label  type="col-4"  >提单号</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="code">  </label>
								</div>
							</td>
							
				  </tr>
				  
				 <tr>
							
							<td  type="col-6" >
								<div >
									<label  type="col-4" >转让日期</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="createTime"> </label>
								</div>
							</td>
							
				    	<td  type="col-6" >
								<div >
									<label  type="col-4" >仓储费起计日期</label>
								</div>
							</td>
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id=time> </label>
								</div>
							</td>
				  </tr>
				
				 <tr>
				   
							
				     <td  type="col-6"  >
								<div >
									<label  type="col-4" >备注</label>
								</div>
							</td>
							<td colspan="3" type="col-6" >
								<div >
									<label  type="col-8" id="description"> </label>
								</div>
							</td>
							
							
				  </tr>
				  
				 
				
				  
					  <tr>
							
							<td  type="col-6" >
								<div >
									<label  type="col-4" >审单</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="createUser"> </label>
								</div>
							</td>
							
				    	<td  type="col-6" >
								<div >
									<label  type="col-4" >复核</label>
								</div>
							</td>
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id=fh> </label>
								</div>
							</td>
				  </tr>
				  
				  

				</table>
				<label class="control-label "  style="font-size: 12px;font-family:宋体">编制日期:</label>
				<label class="control-label "  style="font-size: 12px;font-family:宋体" id="now">  </label>
				<label style="text-align: right ; float:right;font-size: 12px;font-family:宋体"> 第二联：货权出让方 </label>
				
				</div>
				<hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />
				
				<div style="height: 30%">
				<h6 style="text-align: right ; float:right" id="No"></h6>
				<h4 style="text-align: center;font-family:宋体">江苏长江石油化工有限公司</h4>
				<h4 style="text-align: center;font-family:宋体">货权转让通知单</h4>
				<!-- <h6 style="text-align: right;font-family:宋体;float:right">流水号:<span id='clientCode'></span></h6>
				 -->
				
				<table class="printS" type="lading" style=" width: 100%;font-size: 13px;line-height: 25px " border='0'  cellspacing='0'  align='center'>
				
				
				
				 <tr>
				    <td type="col-6" >
								<div >
									<label  type="col-4" >收货人</label>
								</div>
							</td>
							 <td type="col-6" >
								<div >
									<label  type="col-8"  id="receiveClientName">  </label>
								</div>
							</td>
							<td  type="col-6" >
								<div >
									<label  type="col-4" >货权出让方</label>
								</div>
							</td>
							<td  type="col-6"  >
								<div >
									<label type="col-8" id="clientName">  </label>
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
									<label  type="col-4"  >数量（吨）</label>
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
									<label  type="col-4" >提单类型</label>
								</div>
							</td>
				    
				    <td  type="col-6"  >
								<div >
									<label  type="col-8" id="type"> </label>
								</div>
							</td>
				    
				    
							<td  type="col-6" >
								<div >
									<label  type="col-4"  >提单号</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="code">  </label>
								</div>
							</td>
							
				  </tr>
				  
				 <tr>
							
							<td  type="col-6" >
								<div >
									<label  type="col-4" >转让日期</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="createTime"> </label>
								</div>
							</td>
							
				    	<td  type="col-6" >
								<div >
									<label  type="col-4" >仓储费起计日期</label>
								</div>
							</td>
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id=time> </label>
								</div>
							</td>
				  </tr>
				
				 <tr>
				   
							
				     <td  type="col-6"  >
								<div >
									<label  type="col-4" >备注</label>
								</div>
							</td>
							<td colspan="3" type="col-6" >
								<div >
									<label  type="col-8" id="description"> </label>
								</div>
							</td>
							
							
				  </tr>
				  
				 
				
				  
					  <tr>
							
							<td  type="col-6" >
								<div >
									<label  type="col-4" >审单</label>
								</div>
							</td>
							
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id="createUser"> </label>
								</div>
							</td>
							
				    	<td  type="col-6" >
								<div >
									<label  type="col-4" >复核</label>
								</div>
							</td>
							<td  type="col-6"  >
								<div >
									<label  type="col-8" id=fh> </label>
								</div>
							</td>
				  </tr>
				  
				  

				</table>
				<label class="control-label "  style="font-size: 12px;font-family:宋体">编制日期:</label>
				<label class="control-label "  style="font-size: 12px;font-family:宋体" id="now">  </label>
				<label style="text-align: right ; float:right;font-size: 12px;font-family:宋体"> 第三联：存根</label>
				
				</div>
				
			
			
			<!-- /.modal-content -->
		</div>
			<div class="modal-footer">
				<button type="button" class="btn default btn-print">打印</button>
				<button type="button" class="btn default" data-dismiss="modal">关闭</button>
			</div>
			</div>
			
</div>
</div>