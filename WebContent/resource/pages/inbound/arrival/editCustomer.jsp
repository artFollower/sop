<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<h4 class="modal-title">添加货批信息</h4>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal addGoodsForm">
						<div class="form-group">
							<label class="control-label col-md-2">货主<span class="required">*</span></label>
							<div class="col-md-10">
								<div class="input-group">
									<span class="input-group-addon"> <i class="icon-user"></i>
									</span> <select class="form-control customer" data-placeholder="Select..." data-required="1">
										<option value="">请选择</option>
										<c:forEach var="customer" items="${customer.data}" begin="0">
											<option value="<c:out value="${customer.id}"/>"><c:out value="${customer.name}" /></option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2">货物编码<span class="required">*</span></label>
							<div class="col-md-10">
								<div class="input-group">
									<span class="input-group-addon"> <i class="icon-truck"></i>
									</span> <select class="form-control select2me product" data-placeholder="Select..." data-required="1">
										<option value="">请选择</option>
										<c:forEach var="product" items="${product.data}" begin="0">
											<option value="<c:out value="${product.id}"/>"><c:out value="${product.name}" /></option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2">货物数量<span class="required">*</span></label>
							<div class="col-md-10">
								<input id="mask_number" type="text" name="name" data-required="1" class="form-control productAmount" onkeyup="clearNoNum(this)" onafterpaste="clearNoNum(this)" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">到港要求</label>
							<div class="col-md-10">
								<textarea class="form-control requare" rows="3"></textarea>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">关闭</button>
			<button type="button" class="btn blue button-add-goods">添加</button>
		</div>
	</div>
	<!-- /.modal-content -->
</div>
<script type="text/javascript">
	$(function() {
		//添加货批
		$(".button-add-goods").click(function() {
			var isOk = true;
			$(".addGoodsForm").find("input").each(function() {
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
			$(".addGoodsForm").find("select").each(function() {
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
					url : settings.base.domain + "arrivalPlan/saveGood",
					data : {
						'arrivalId' : $(".planID").val(),
						"clientId" : $(".customer").val(),
						'productId' : $(".product").val(),
						'goodsTotal' : $(".productAmount").val(),
						'requirement' : $(".requare").val()
					},
					dataType : "json",
					success : function(data) {
						if (data.code == "0000") {
							alert("货批添加成功");
							$('#ajax').modal('hide');
							openMenu(null, "<%=basePath%>arrivalPlan/get?method=1&id="+ $(".planID").val(), 0);
						} else {
							alert("货批添加失败");
						}
					}
				});
				
			}
		});
	});
</script>