<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<div class="portlet box grey">
		<div class="portlet-title">
			<div class="caption">填写方案</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form class="form-horizontal">
				<div class="form-body">
						<button type="button" class="btn btn-info" onclick="Outbound.showkbpg() ;">靠泊评估</button>
								<div class="form-group">
									<label class="control-label col-md-2">靠泊泊位</label>
									<div class="col-md-82">
										<label class="control-label">1#泊位</label>
									</div>
									<label class="control-label col-md-2">富裕水深</label>
									<div class="col-md-2">
										<label class="control-label">20</label>
									</div>
								</div>
								<div class="form-group">
								<label class="control-label col-md-2">泊位船长</label>
									<div class="col-md-2">
										<label class="control-label">150</label>
									</div>
									<label class="control-label col-md-2">泊位吃水</label>
									<div class="col-md-2">
										<label class="control-label">4</label>
									</div>
									<label class="control-label col-md-2">泊位最大排水量</label>
									<div class="col-md-2">
										<label class="control-label">54343</label>
									</div>
								</div>
								<div class="form-group">
								<label class="col-md-2 control-label">泊位信息</label>
									<div class="col-md-10">
										<label class="control-label">大型船舶需减载后靠离泊，严格控制3000吨级以下海轮靠泊。靠离泊风速<12.3m/s(6级风)……</label>
									</div>
									</div>
								<div class="form-group">
								<label class="col-md-2 control-label">安全措施</label>
									<div class="col-md-10">
										<label class="control-label">1、靠泊时使用2艘拖轮，带缆人员6名，现场风力小于6级</label>
									</div>
									</div>
								<div class="form-group">
									<label class="col-md-2 control-label">审核意见</label>
									<div class="col-md-10">
										<label class="control-label">通过</label>
									</div>
								</div>
				</div>
			
				<div class="form-actions fluid">
					<div class="col-md-offset-3 col-md-9">
						<button type="button" class="btn default" onclick="javascript:history.go(-1);">返回</button>
					</div>
				</div>
				</form>
			<!-- END FORM-->
		</div>
	</div>
	<!-- END PAGE -->
	<div class="modal fade" id="ajax" tabindex="-1" role="basic" aria-hidden="true">
		<img src="resources/admin/img/ajax-modal-loading.gif" alt="" class="loading">
	</div>
	<!-- 页面内容开结束-->
	<script type="text/javascript">
	
		$(function() {
			$(".sbtn").click(function() {
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
						url : settings.base.domain+"client/save",
						data : {
							"code" : $(".code").val(),
							"name" : $(".name").val(),
							"phone" : $(".phone").val(),
							"guestId" : $(".guestId").val(),
							"email" : $(".email").val(),
							"fax" : $(".fax").val(),
							"address" : $(".address").val(),
							"postcode" : $(".postcode").val(),
							"contactName" : $(".contactName").val(),
							"contactPhone" : $(".contactPhone").val(),
							"bankAccount" : $(".bankAccount").val(),
							"bankName" : $(".bankName").val(),
							"creditGrade" : $(".creditGrade").val(),
							"paymentGrade" : $(".creditGrade").val(),
							"description" : $(".description").val()
						},
						// data:JSON.stringify(sendGroup),
						dataType : "json",
						success : function(data) {
							if (data.code == "0000") {
								alert("客户添加成功");
								openMenu(null,"<%=basePath%>client/list",1);
							}else {
								alert("客户添加失败");
							}
						}
					});
				}
			});
			});
	</script>
</body>
</html>