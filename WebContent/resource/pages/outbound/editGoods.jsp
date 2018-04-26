<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal-dialog" style="width:900px">
	<div class="modal-content" >
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<h4 class="modal-title">编辑货体</h4>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet-body form">
						<form class="form-horizontal">
							<div class="form-body">
								<table width="100%">
									<tr>
										<td class=" col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4">客户名称<span class="required">*</span></label>
												<div class="col-md-8">
													<input type="text" id="serchCustName" class="form-control intentTitle" readonly />
												</div>
											</div>
										</td>
										<td class=" col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4">货品名称<span class="required">*</span></label>
												<div class="col-md-8">
													<input type="text" id="serchGoodsName" class="form-control intentTitle" readonly />
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td class=" col-md-6">
											<div class="form-group">
												<label class="control-label col-md-4">提单类型<span class="required">*</span></label>
												<div class="col-md-8">
													<input type="text" id="ladingType" class="form-control intentTitle" readonly />
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<div class="form-group">
												<div class="col-md-10">
													<div class="fluid">
														<div class="col-md-offset-7 col-md-9">
															<button type="button" class="btn green serchButton">检索</button>
														</div>
													</div>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</form>
					</div>
					<div class="portlet box yellow">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-edit"></i>货体信息
							</div>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a>
							</div>
						</div>
						<div class="portlet-body">
							<table class="table table-striped table-hover table-bordered" id="ladingGoodsTable">
								<thead>
									<tr>
										<th style="width1: 8px;"><input type="checkbox" class="group-checkable" data-set="#ladingGoodsTable .checkboxes" /></th>
										<th>货体编号</th>
										<th>客户名称</th>
										<th>货品名称</th>
										<th>当前存量(吨)</th>
										<th>放入提单数量(吨)</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">关闭</button>
			<button type="button" class="btn blue button-add-goods">确定</button>
		</div>
	</div>
	<!-- /.modal-content -->
</div>
<script type="text/javascript">
	$(function() {
		jQuery('#ladingGoodsTable .group-checkable').change(function() {
			var set = jQuery(this).attr("data-set");
			var checked = jQuery(this).is(":checked");
			jQuery(set).each(function() {
				if (checked) {
					$(this).attr("checked", true);
				} else {
					$(this).attr("checked", false);
				}
			});
			jQuery.uniform.update(set);
		});
		$("#serchCustName").val($("#custName").val());
		$("#serchGoodsName").val($("#productName").val());
		$("#ladingType").val($("#type").val());
		$(".serchButton").click(function(){
			$.ajax({
				type : "post",
				url : settings.base.domain+"lading/serchGoods",
				data : {
					"clientId" : $("#custName").attr("data"),
					"productId" : $("#productName").attr("data"),
					"ladingType":$("#type").attr("data") 
				},
				// data:JSON.stringify(sendGroup),
				dataType : "json",
				success : function(data) {
					if (data.code == "0000") {
						$("#ladingGoodsTable").children("tbody").children("tr").remove();
						var goods = "";
						for (var i = 0; i < data.data.length; i++) {
							var goodsInfo = data.data[i];
							for(var j = 0; j < goodsInfo.goodsdata.length; j++){
								var goodsIn = goodsInfo.goodsdata[j];
								goods += "<tr>";
								goods += "<input type='hidden' id='ladingGoodsId' value='"+goodsIn.id+"' />";
								goods += "<td><input type='checkbox' class='checkboxes' name='chk_list'/></td>";
								goods += "<td class='code'>"+goodsIn.code+"</td>";
								goods += "<td class='clientName'>"+goodsIn.clientName+"</td>"
								goods += "<td class='productName'>"+goodsIn.productName+"</td>";
								goods += "<td class='goodsCuent'>"+goodsIn.goodsCurrent+"</td>";
								goods += "<td><input type='text' class='ladingCount' value='"+goodsIn.goodsCurrent+"' /></td>";
								goods += "</tr>";
							}
						}
						$("#ladingGoodsTable").children("tbody").append(goods);
					}else {
						alert("货体检索失败");
					}
				}
			});
		});
		
		//添加货批
		$(".button-add-goods").click(function() {
			var goods = "";
			var checkResult = true;
			$('input[type="checkbox"][name="chk_list"]:checked').each(function(){
				var _tr = $(this).parents("tr");
				$("#goods-table").children("tbody").children("tr").each(function(){
					var inCode = $(this).find(".goodsCode").text();
					var addCode = $(_tr).find(".code").text();
					if(inCode == addCode){
						checkResult = false;
					}
				});
				goods +="<tr>"
				goods +="<input type='hidden' id='ladingGoodsId' value='"+$(_tr).find("#ladingGoodsId").val()+"' />";
				goods += "<td class='goodsCode'>"+$(_tr).find(".code").text()+"</td>";
				goods += "<td class='clientName'>"+$(_tr).find(".clientName").text()+"</td>";
				goods += "<td class='productName'>"+$(_tr).find(".productName").text()+"</td>";
				goods += "<td class='goodsCuent'>"+$(_tr).find(".goodsCuent").text()+"</td>";
				goods += "<td class='ladingCount'>"+$(_tr).find(".ladingCount").val()+"</td>";
				goods +="<td><a href='javascript:void(0)' class='blue' onclick='removeGood(this)'> <i class='icon-remove'></i>删除</a></td>";
				goods +="</tr>"
			});
			if(checkResult == false){
				alert("相同编号的货体已经添加，不能重复添加！");
				return;
			}else{
				$("#goods-table").children("tbody").append(goods);
				$('#ajax').modal('hide');
			}		
		});
	});
</script>