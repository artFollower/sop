<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
<style type="text/css">
 .grid-table-body table td {
    border-color: transparent #dddddd #dddddd transparent !important;
    border-width: 0 1px 1px 0 !important;
    vertical-align: top !important;
}
</style>
<script type="text/javascript">
    // 路径配置
    require.config({
        paths: {
            echarts: '<%=basePath %>/resource/global/scripts/dist'
        }
    });
</script>
</head>
<body>
<!-- BEGIN MAIN CONTENT -->

<form name="tanklogFirstForm" id="tanklogFirstForm" target="_self" class="form-horizontal searchFirstCondition ">
			<div class="form-group">
			
			 <label class="control-label col-md-2" style="width:80px;float:left;">年份:</label>
												<div class="col-md-2">
													<div
														class="input-group  date-picker input-daterange time1"
														data-date-format="yyyy">
														<input type="text" class="form-control" name="year" id="startTime1">
													</div>
												</div>
			
			
						 <label class="control-label " style="width:80px;float:left;" >货品:</label>
						<div class="col-md-3">
							<input id="product1" type="text" name="productId" data-provide="typeahead"  class="form-control product1">
						</div>
						
						<label class="control-label col-md-2" style="width:80px;float:left;" >罐号:</label>
						<div class="col-md-2" id="tId">
							<input id="tankId1" type="text" name="tankId" data-provide="typeahead"  class="form-control tankId">
						</div>
						
				          </div>
					</form>
					
					
					
<div class="col-md-12 clearfix  first"   style="">



		<div class="row">
			<div class="btn-group buttons col-md-6 col-sm-6">
			<!--  <button class="btn btn-primary  plus" type="button"><span class="glyphicon glyphicon-plus"></span>添加</button>-->
			<shiro:hasPermission name="ASTORELEDGEADD">
			<button class="btn btn-default btn-circle mar-r-10 add" type="button">
						<span class="fa fa-plus"></span><span class="pad-l-5">添加</span>
					</button>
					</shiro:hasPermission>
			</div>
		</div>
         <div class="col-md-12" style="display: block;height:500px;overflow: auto;width: 100%;" id="pie"></div>

</div>
	<div class="col-md-12   second" style="display: none;" >
		<!-- Begin: life time stats -->
		<div class="portlet">
			<div class="portlet-title ">
				<div class="caption">
					<i class="fa fa-list-alt"></i>储罐台账
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
						</ul>
					</div>
				</div>
			</div>
			
			
			<div class="portlet col-md-12">
			
			
			<form name="tanklogListForm" id="tanklogListForm" target="_self" class="form-horizontal searchCondition" style="display: none">
			<div class="form-group">
				
												 <label class="control-label col-md-2" style="width:80px;float:left;">年份:</label>
												<div class="col-md-2">
													<div
														class="input-group  date-picker input-daterange time2"
														data-date-format="yyyy">
														<input type="text" class="form-control" name="year" id="startTime"
															id="startTime">
													</div>
												</div>
						 <label class="control-label col-md-2" style="width:80px;float:left;" >货品:</label>
						<div class="col-md-2">
							<input id="product" type="text" name="productId" data-provide="typeahead"  class="form-control product">
						</div>
						<label class="control-label col-md-2" style="width:80px;float:left;" >罐号:</label>
						<div class="col-md-2">
							<input id="tankId" type="text" name="tankId" data-provide="typeahead"  class="form-control tankId">
						</div>
				          </div>
					</form>
			
			
			
			<div class="row">
			<div class="btn-group buttons col-md-6 col-sm-6">
			<!--  <button class="btn btn-primary  plus" type="button"><span class="glyphicon glyphicon-plus"></span>添加</button>-->
			<shiro:hasPermission name="ASTORELEDGEADD">
			<button class="btn btn-default btn-circle mar-r-10 add" type="button">
						<span class="fa fa-plus"></span><span class="pad-l-5">添加</span>
					</button>
					</shiro:hasPermission>
			<shiro:hasPermission name="ASTORELEDGEUPDATE">
			<button class="btn btn-default btn-circle mar-r-10 modify" type="button">
						<span class="fa fa-edit"></span><span class="pad-l-5">修改</span>
					</button>
					</shiro:hasPermission>
				<shiro:hasPermission name="ASTORELEDGEUPDATE">
					<button class="btn btn-default btn-circle mar-r-10 btn-remove" type="button">
						<span class="fa fa-remove"></span><span class="pad-l-5">删除</span>
					</button> 
					</shiro:hasPermission>
			<button class="btn btn-default btn-circle mar-r-10 excel" onclick="TankLog.exportExcel()" type="button">
						<span class="fa fa-plus"></span><span class="pad-l-5">导出</span>
					</button>
			</div>
			
			
		</div>
		 <!-- <div class="scroller" style="height:350px;">  -->
		 <!-- <div id="scrollbar">
           	<div class="viewport"> -->
		<div data-role="tanklogGrid" ></div>
		<!-- </div>
		</div> -->
		<!-- </div>  -->
			<div class="modal-footer">
				<button type="button"  class="btn btn-default back" data-dismiss="modal">返回</button>
			</div>
	</div>
</div>
</div>
</body>