<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<!-- <div class="col-md-12">
			<ul class="page-breadcrumb breadcrumb">
				<li><i class="icon-home"></i> <a href="index.html">控制台</a> <i class="icon-angle-right"></i></li>
				<li><a href="#">船期预报</a></li>
			</ul>
		</div> -->
	</div>
	<div class="portlet box grey">
		<div class="portlet-title">
			<div class="caption">添加船期预报</div>
			<div class="tools">
                        <a class="collapse" href="javascript:;"></a>
                     </div>
		</div>
		<div class="portlet-body form ">
			<!-- BEGIN FORM-->
			<form action="#" class="form-horizontal">
				<div class="form-body">
				<h4 class="form-section">时间信息</h4>
								<div class="form-group">
									<label class="control-label col-md-2">长江口时间:</label>
									<div class="col-md-4">
										<div class="input-group date form_datetime">                                       
		                                  <label class="control-label">2014-12-23 15:00:32</label>
		                                 </div>
									</div>
									<label class="control-label col-md-2">太仓锚地时间:</label>
									<div class="col-md-4">
											<div class="input-group date form_datetime">                                       
		                                  <label class="control-label">2014-12-23 15:00:32</label>
		                                 </div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">NOR发出时间:</label>
									<div class="col-md-4">
									<div class="input-group date form_datetime">                                       
		                                    <label class="control-label">2014-12-23 15:00:32</label>
		                                 </div>
									</div>
									<label class="control-label col-md-2">预计靠泊日期:</label>
									<div class="col-md-4">
										<div class="input-group date form_datetime">                                       
		                                   <label class="control-label">2014-12-23 15:00:32</label>
		                                 </div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">预计靠泊时间:</label>
									<div class="col-md-4">
											<div class="input-group date form_datetime">                                       
		                                   <label class="control-label">2014-12-23 15:00:32</label>
		                                 </div>
									</div>
									<label class="control-label col-md-2">预计开泵时间:</label>
									<div class="col-md-4">
										<div class="input-group date form_datetime">                                       
		                                  <label class="control-label">2014-12-23 15:00:32</label>
		                                 </div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">预计停泵时间:</label>
									<div class="col-md-4">
										<div class="input-group date form_datetime">                                       
		                                  <label class="control-label">2014-12-23 15:00:32</label>
		                                 </div>
									</div>
									<label class="control-label col-md-2">预计作业时间:</label>
									<div class="col-md-4">
											<div class="input-group date form_datetime">                                       
		                                   <label class="control-label">2014-12-23 15:00:32</label>
		                                 </div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">预计离港时间:</label>
									<div class="col-md-4">
									<div class="input-group date form_datetime">                                       
		                                    <label class="control-label">2014-12-23 15:00:32</label>
		                                 </div>
									</div>
								</div>
								<h4  class="form-section">其他信息</h4>
								<div class="form-group">
									<label class="control-label col-md-2">港序:</label>
									<div class="col-md-2">
										<label class="control-label">201</label>
									</div>
									<label class="control-label col-md-2">管线安排:</label>
									<div class="col-md-2">
										<label class="control-label">12</label>
									</div>
									<label class="control-label col-md-2">罐安排:</label>
									<div class="col-md-2">
									<label class="control-label">大D</label>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">本年度航次:</label>
									<div class="col-md-2">
										<label class="control-label">33</label>
									</div>
										<label class="control-label col-md-2">申报状态:</label>
									<div class="col-md-2">
										<label class="control-label">已申报</label>
									</div>
									<label class="control-label col-md-2">超期时间（小时）:</label>
									<div class="col-md-2">
										<label class="control-label">3</label>
								</div>
								</div>
								<div class="form-group">
								<label class="control-label col-md-2">拆管时间:</label>
									<div class="col-md-2">
									<label class="control-label">2日18:30</label>
									</div>
									<label class="control-label col-md-2">船舶信息表:</label>
									<div class="col-md-2">
									<label class="control-label">0.33</label>
									</div>
									<label class="control-label col-md-2">速遣时间（小时）:</label>
									<div class="col-md-2">
										<label class="control-label">0.33</label>
								</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-4">允许最大在港时间（以卸货速度150t/h计算）:</label>
									<div class="col-md-2">
										<label class="control-label">0.33</label>
									</div>
									<label class="control-label col-md-2">备注:</label>
									<div class="col-md-4">
										<label class="control-label">无</label>
								</div>
								</div>
			</form>
			<!-- END FORM-->
		</div>
	</div>
	<!-- 页面内容开结束-->
	<script type="text/javascript">
		$(function() {
			 
			 $('body').removeClass("modal-open"); // fix bug when inline picker is used in modal
			 
			 $(".intentionId").change(function() {
				 $.ajax({
						type : "post",
						url : settings.base.domain+"orderInstent/getIntent?id="+$(this).val(),
						dataType : "json",
						success : function(data) {
							if (data.code == "0000") {
								if(data.data.length > 0) {
									var intent = data.data[0];
									$(".quantity").val(intent.quantity);
									$(".unitPrice").val(intent.unitPrice);
									$(".totalPrice").val(intent.totalPrice);
									$(".description").val(intent.description);
									$(".type").val(intent.type);
									$(".productId").val(intent.productId);
									$(".clientId").val(intent.clientId);
								}
							}
						}
					});
			 });
			
			$(".submitButton").click(function() {
				var isOk = true;
				$(".form-horizontal").find("input").each(function() {
					if($(this).attr("data-required") == 1) {
						if($(this).val() == null || $(this).val() == "") {
							$(this).pulsate({
				                color: "#bf1c56",
				                repeat: false
				            });
							isOk = false;
							return;
						}
					}
				});
				if(isOk) {
					$.ajax({
						type : "post",
						url : settings.base.domain+"order/save",
						data : {
							"title" : $(".orderTitle").val(),
							"intentionId" : $(".intentionId").val(),
							"type" : $(".type").val(),
							"clientId" : $(".clientId").val(),
							"productId" : $(".productId").val(),
							"quantity" : $(".quantity").val(),
							"unitPrice" : $(".unitPrice").val(),
							"totalPrice" : $(".totalPrice").val(),
							"description" : $(".description").val(),
							"signDate" : $(".signDate").val(),
							"status" :1
						},
						// data:JSON.stringify(sendGroup),
						dataType : "json",
						success : function(data) {
							if (data.code == "0000") {
								alert("合同添加成功");
								openMenu(null,"<%=basePath%>order/list",0);
							}else {
								alert("合同添加失败");
							}
						}
					});
				}
			});
			
			$(".saveButton").click(function() {
				var isOk = true;
				$(".form-horizontal").find("input").each(function() {
					if($(this).attr("data-required") == 1) {
						if($(this).val() == null || $(this).val() == "") {
							$(this).pulsate({
				                color: "#bf1c56",
				                repeat: false
				            });
							isOk = false;
							return;
						}
					}
				});
				if(isOk) {
					$.ajax({
						type : "post",
						url : settings.base.domain+"order/save",
						data : {
							"title" : $(".orderTitle").val(),
							"intentionId" : $(".intentionId").val(),
							"type" : $(".type").val(),
							"clientId" : $(".clientId").val(),
							"productId" : $(".productId").val(),
							"quantity" : $(".quantity").val(),
							"unitPrice" : $(".unitPrice").val(),
							"totalPrice" : $(".totalPrice").val(),
							"description" : $(".description").val(),
							"signDate" : $(".signDate").val()
						},
						// data:JSON.stringify(sendGroup),
						dataType : "json",
						success : function(data) {
							if (data.code == "0000") {
								alert("合同保存成功");
								openMenu(null,"<%=basePath%>order/list",0);
							}else {
								alert("合同保存失败");
							}
						}
					});
				}
			});
		});
	</script>
</body>