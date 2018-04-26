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
  font-family:"宋体";
}

td[type="col-5"]
{
width: 50%;
   margin-left: 30px;
  font-size: 13px;
  font-family:"宋体";
}

div[type="form-group"]
{

   margin-left: 15px;
   margin-top:1px;
  font-size: 13px;
  font-family:"宋体";
}


label[type="col-3"]
{
	width: 33.3333%;
    float: left;
        min-height: 1px;
    padding-left: 30px;
        margin-bottom: 0px;
        font-family:"宋体";
}

label[type="col-7"]
{
	width: 66.6667%;
    float: left;
        min-height: 1px;
    padding-left: 30px;
        margin-bottom: 0px;
        font-family:"宋体";
}

</style> -->
<link href="<%=basePath %>/resource/admin/layout/css/shipPlan.css" rel="stylesheet" type="text/css" media="print"/>

<div class="modal fade">
	<div class="modal-dialog modal-full" style="width:600px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">船舶进港工作联系单</h4>
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
			
			<div class="modal-body " style="margin: 0 auto; "  >
				<div style="height: 31%">
				<label class="printS" style="text-align: right ; float:right" id="No">No.000001</label>
				<h4 class="printS" style="text-align: center;font-family:宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;江苏长江石油化工有限公司</h4>
				<label class="printS" style="text-align: right;float:right;font-size: 11px;">JL2104</label>
				<h4 class="printS" style="text-align: center;font-family:宋体">船舶进港工作联系单</h4>
				
				<table class="printS" style="border:1px solid #000; width: 100%;font-size: 13px; " cellpadding="0" cellspacing="0" align='center'>
				
				 <tr>
				 
				    <td class="printS" style="padding-left: 20px;padding-bottom:5px">生产运行部:</td>
				  </tr>
				  <tr>
				    <td class="printS" style="padding-left: 30px;padding-bottom:5px" colspan="2">现将有关货物及船舶工业进港情况报告如下:</td>
				  </tr>
				
				 <tr>
				    <td type="col-5" style="padding-left: 40px;">
								<div >
									<label  type="col-3" >合同号</label>
								</div>
							</td>
							
							
							<td type="col-5" >
								<div >
									<label  type="col-7" id="contract" > </label>
								</div>
							</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div >
									<label  type="col-3"  >性质</label>
								</div>
							</td>
				    
				    <td  type="col-5"  >
								<div >
									<label  type="col-7" id="taxType"> </label>
								</div>
							</td>
				    
				  </tr>
				  
				  <tr>
				    <td  type="col-5"  style="padding-left: 40px;">
								<div >
									<label  type="col-3" >船名</label>
								</div>
							</td>
							
					 <td  type="col-5" >
								<div >
									<label type="col-7" id="shipName">  </label>
								</div>
							</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div >
									<label  type="col-3" >货物名称</label>
								</div>
							</td>
				    
				    <td  type="col-5" >
								<div >
									<label  type="col-7" id="productName"> </label>
								</div>
							</td>
				    
				  </tr>
				  
				  <tr>
				    <td  type="col-5"  style="padding-left: 40px;">
								<div >
									<label  type="col-3"  >船期</label>
								</div>
							</td>
							
							<td  type="col-5" >
								<div >
									<label  type="col-7" id="shipTime">  </label>
								</div>
							</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div >
									<label  type="col-3" >货主单位</label>
								</div>
							</td>
				    		<td  type="col-5" >
								<div >
									<label  type="col-7" id="clientName">  </label>
								</div>
							</td>
				  </tr>
				  
				  <tr>
				    <td  type="col-5"  style="padding-left: 40px;">
								<div >
									<label  type="col-3" >数量</label>
								</div>
							</td>
							
							
							<td  type="col-5"  >
								<div >
									<label  type="col-7" id="count">  </label>
								</div>
							</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div >
									<label  type="col-3"  >仓储要求</label>
								</div>
							</td>
				    		<td  type="col-5"  >
								<div >
									<label  type="col-7" id="require">  </label>
								</div>
							</td>
				  </tr>
				
				 <tr>
				    <td  type="col-5"  style="padding-left: 40px;">
								<div >
									<label  type="col-3"  >产地</label>
								</div>
							</td>
				    <td  type="col-5" >
								<div >
									<label  type="col-7" id="originalArea"> </label>
								</div>
							</td>
				  </tr>
				  
				 
				   <tr>
				    <td  type="col-5" style="padding-left: 40px;" >
								请予确认。
							</td>
							 <td  type="col-5" style="padding-left: 20px;">
								商务部
							</td>
				  </tr>
				  
					 <tr>
					 <td></td>
				    
							
				    
				  </tr>
				  
				   <tr >
				    <td  type="col-5"  style="padding-left: 40px;">
								<div  style="padding-bottom: 5px">
									<label type="col-3">开单:</label>
								</div>
					 		</td>
							
							<td  type="col-5"  ">
								<div  style="padding-bottom: 5px">
									<label type="col-7"  id="open">  </label>
								</div>
					 		</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div  style="padding-bottom: 5px">
									<label type="col-3" >复核:</label>
								</div>
							</td>
				    <td  type="col-5"  >
								<div  style="padding-bottom: 5px">
									<label type="col-7" id="check" >  </label>
								</div>
							</td>
				  </tr>
				  

				</table>
				
				<label class="control-label "  style="font-size: 12px;font-family:宋体">编制日期:</label>
				<label class="control-label createTime"  style="font-size: 12px;font-family:宋体" id="createTime">  </label>
				<label style="text-align: right ; float:right;font-size: 12px;font-family:宋体"> 第一联：商务部 </label>
				
				</div>
				
				<div style="height: 1%"></div>
				<div style="height: 31%">
				<label class="printS" style="text-align: right ; float:right" id="No">No.000001</label>
				<h4 class="printS" style="text-align: center;font-family:宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;江苏长江石油化工有限公司</h4>
				<label class="printS" style="text-align: right;float:right;font-size: 11px;">JL2104</label>
				<h4 class="printS" style="text-align: center;font-family:宋体">船舶进港工作联系单</h4>
				
				<table class="printS" style="border:1px solid #000; width: 100%;font-size: 13px; " cellpadding="0" cellspacing="0" align='center'>
				
				 <tr>
				 
				    <td class="printS" style="padding-left: 20px;padding-bottom:5px">生产运行部:</td>
				  </tr>
				  <tr>
				    <td class="printS" style="padding-left: 30px;padding-bottom:5px" colspan="2">现将有关货物及船舶工业进港情况报告如下:</td>
				  </tr>
				
				 <tr>
				    <td type="col-5" style="padding-left: 40px;">
								<div >
									<label  type="col-3" >合同号</label>
								</div>
							</td>
							
							
							<td type="col-5" >
								<div >
									<label  type="col-7" id="contract" > </label>
								</div>
							</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div >
									<label  type="col-3"  >性质</label>
								</div>
							</td>
				    
				    <td  type="col-5"  >
								<div >
									<label  type="col-7" id="taxType"> </label>
								</div>
							</td>
				    
				  </tr>
				  
				  <tr>
				    <td  type="col-5"  style="padding-left: 40px;">
								<div >
									<label  type="col-3" >船名</label>
								</div>
							</td>
							
					 <td  type="col-5" >
								<div >
									<label type="col-7" id="shipName">  </label>
								</div>
							</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div >
									<label  type="col-3" >货物名称</label>
								</div>
							</td>
				    
				    <td  type="col-5" >
								<div >
									<label  type="col-7" id="productName"> </label>
								</div>
							</td>
				    
				  </tr>
				  
				  <tr>
				    <td  type="col-5"  style="padding-left: 40px;">
								<div >
									<label  type="col-3"  >船期</label>
								</div>
							</td>
							
							<td  type="col-5" >
								<div >
									<label  type="col-7" id="shipTime">  </label>
								</div>
							</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div >
									<label  type="col-3" >货主单位</label>
								</div>
							</td>
				    		<td  type="col-5" >
								<div >
									<label  type="col-7" id="clientName">  </label>
								</div>
							</td>
				  </tr>
				  
				  <tr>
				    <td  type="col-5"  style="padding-left: 40px;">
								<div >
									<label  type="col-3" >数量</label>
								</div>
							</td>
							
							
							<td  type="col-5"  >
								<div >
									<label  type="col-7" id="count">  </label>
								</div>
							</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div >
									<label  type="col-3"  >仓储要求</label>
								</div>
							</td>
				    		<td  type="col-5"  >
								<div >
									<label  type="col-7" id="require">  </label>
								</div>
							</td>
				  </tr>
				
				 <tr>
				    <td  type="col-5"  style="padding-left: 40px;">
								<div >
									<label  type="col-3"  >产地</label>
								</div>
							</td>
				    <td  type="col-5" >
								<div >
									<label  type="col-7" id="originalArea"> </label>
								</div>
							</td>
				  </tr>
				  
				 
				   <tr>
				    <td  type="col-5" style="padding-left: 40px;" >
								请予确认。
							</td>
							 <td  type="col-5" style="padding-left: 20px;">
								商务部
							</td>
				  </tr>
				  
					 <tr>
					 <td></td>
				    
							
				    
				  </tr>
				  
				   <tr >
				    <td  type="col-5"  style="padding-left: 40px;">
								<div  style="padding-bottom: 5px">
									<label type="col-3">开单:</label>
								</div>
					 		</td>
							
							<td  type="col-5"  ">
								<div  style="padding-bottom: 5px">
									<label type="col-7"  id="open">  </label>
								</div>
					 		</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div  style="padding-bottom: 5px">
									<label type="col-3" >复核:</label>
								</div>
							</td>
				    <td  type="col-5"  >
								<div  style="padding-bottom: 5px">
									<label type="col-7" id="check" >  </label>
								</div>
							</td>
				  </tr>
				  

				</table>
				
				<label class="control-label "  style="font-size: 12px;font-family:宋体">编制日期:</label>
				<label class="control-label createTime"  style="font-size: 12px;font-family:宋体" id="createTime">  </label>
				<label style="text-align: right ; float:right;font-size: 12px;font-family:宋体"> 第二联：生产运行部 </label>
				
				</div>
				
				
				<div style="height: 1%"></div>
				<div style="height: 30%">
				<label class="printS" style="text-align: right ; float:right" id="No">No.000001</label>
				<h4 class="printS" style="text-align: center;font-family:宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;江苏长江石油化工有限公司</h4>
				<label class="printS" style="text-align: right;float:right;font-size: 11px;">JL2104</label>
				<h4 class="printS" style="text-align: center;font-family:宋体">船舶进港工作联系单</h4>
				
				<table class="printS" style="border:1px solid #000; width: 100%;font-size: 13px; " cellpadding="0" cellspacing="0" align='center'>
				
				 <tr>
				 
				    <td class="printS" style="padding-left: 20px;padding-bottom:5px">生产运行部:</td>
				  </tr>
				  <tr>
				    <td class="printS" style="padding-left: 30px;padding-bottom:5px" colspan="2">现将有关货物及船舶工业进港情况报告如下:</td>
				  </tr>
				
				 <tr>
				    <td type="col-5" style="padding-left: 40px;">
								<div >
									<label  type="col-3" >合同号</label>
								</div>
							</td>
							
							
							<td type="col-5" >
								<div >
									<label  type="col-7" id="contract" > </label>
								</div>
							</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div >
									<label  type="col-3"  >性质</label>
								</div>
							</td>
				    
				    <td  type="col-5"  >
								<div >
									<label  type="col-7" id="taxType"> </label>
								</div>
							</td>
				    
				  </tr>
				  
				  <tr>
				    <td  type="col-5"  style="padding-left: 40px;">
								<div >
									<label  type="col-3" >船名</label>
								</div>
							</td>
							
					 <td  type="col-5" >
								<div >
									<label type="col-7" id="shipName">  </label>
								</div>
							</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div >
									<label  type="col-3" >货物名称</label>
								</div>
							</td>
				    
				    <td  type="col-5" >
								<div >
									<label  type="col-7" id="productName"> </label>
								</div>
							</td>
				    
				  </tr>
				  
				  <tr>
				    <td  type="col-5"  style="padding-left: 40px;">
								<div >
									<label  type="col-3"  >船期</label>
								</div>
							</td>
							
							<td  type="col-5" >
								<div >
									<label  type="col-7" id="shipTime">  </label>
								</div>
							</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div >
									<label  type="col-3" >货主单位</label>
								</div>
							</td>
				    		<td  type="col-5" >
								<div >
									<label  type="col-7" id="clientName">  </label>
								</div>
							</td>
				  </tr>
				  
				  <tr>
				    <td  type="col-5"  style="padding-left: 40px;">
								<div >
									<label  type="col-3" >数量</label>
								</div>
							</td>
							
							
							<td  type="col-5"  >
								<div >
									<label  type="col-7" id="count">  </label>
								</div>
							</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div >
									<label  type="col-3"  >仓储要求</label>
								</div>
							</td>
				    		<td  type="col-5"  >
								<div >
									<label  type="col-7" id="require">  </label>
								</div>
							</td>
				  </tr>
				
				 <tr>
				    <td  type="col-5"  style="padding-left: 40px;">
								<div >
									<label  type="col-3"  >产地</label>
								</div>
							</td>
				    <td  type="col-5" >
								<div >
									<label  type="col-7" id="originalArea"> </label>
								</div>
							</td>
				  </tr>
				  
				 
				   <tr>
				    <td  type="col-5" style="padding-left: 40px;" >
								请予确认。
							</td>
							 <td  type="col-5" style="padding-left: 20px;">
								商务部
							</td>
				  </tr>
				  
					 <tr>
					 <td></td>
				    
							
				    
				  </tr>
				  
				   <tr >
				    <td  type="col-5"  style="padding-left: 40px;">
								<div  style="padding-bottom: 5px">
									<label type="col-3">开单:</label>
								</div>
					 		</td>
							
							<td  type="col-5"  ">
								<div  style="padding-bottom: 5px">
									<label type="col-7"  id="open">  </label>
								</div>
					 		</td>
							
							<td  type="col-5"  style="padding-left: 20px;">
								<div  style="padding-bottom: 5px">
									<label type="col-3" >复核:</label>
								</div>
							</td>
				    <td  type="col-5"  >
								<div  style="padding-bottom: 5px">
									<label type="col-7" id="check" >  </label>
								</div>
							</td>
				  </tr>
				  

				</table>
				
				<label class="control-label "  style="font-size: 12px;font-family:宋体">编制日期:</label>
				<label class="control-label createTime"  style="font-size: 12px;font-family:宋体" id="createTime">  </label>
				<label style="text-align: right ; float:right;font-size: 12px;font-family:宋体"> 第三联：生产运行部计量 </label>
				
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
