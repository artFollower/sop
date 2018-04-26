$(document)
		.ready(
				function() {
					$(".form_datetime").datetimepicker(
							{
								autoclose : true,
								isRTL : App.isRTL(),
								format : "yyyy-mm-dd hh:ii:ss",
								pickerPosition : (App.isRTL() ? "bottom-right"
										: "bottom-left")
							});

					var oTable3 = $('#table_three').dataTable({
						"aoColumnDefs" : [ {
							'bSortable' : false,
							'aTargets' : [ 0 ]
						} ]
					});
					var oTable1 = $('#table_one').dataTable();
					var oTable2 = $('#table_two').dataTable();
					// 存储
					function restoreRow(oTable, nRow) {
						var aData = oTable.fnGetData(nRow);
						var jqTds = $('>td', nRow);

						for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
							oTable.fnUpdate(aData[i], nRow, i, false);
						}

						oTable.fnDraw();
					}
					function editRow(oTable, nRow) {
						// var aData = oTable.fnGetData(nRow);
						var jqTds = $('>td', nRow);
						for (var i = 0; i < jqTds.length - 1; i++) {
							jqTds[i].innerHTML = '<input type="text" class="form-control " style="width:70px" value="'
									+ $(jqTds)[i].innerHTML + '">';
						}
						jqTds[jqTds.length - 1].innerHTML = '<a class="edit" key="1"  href=""><i class="icon-edit"></i>保存</a><a class="cancel" href=""> <i class="icon-remove"></i>取消</a>';
					}
					function editRowT(oTable, nRow, item) {
						// var aData = oTable.fnGetData(nRow);
						var jqTds = $('>td', nRow);
						for (var i = 0; i < jqTds.length - 1; i++) {
							jqTds[i].innerHTML = '<input type="text" class="form-control " style="width:auto" value="'
									+ $(jqTds)[i].innerHTML + '">';
						}
						jqTds[jqTds.length - 1].innerHTML = '<a class="edit" key="1" mtable="'
								+ item
								+ '" href=""><i class="icon-edit"></i>保存</a>';
					}
					function saveRow(oTable, nRow) {
						var jqTds = $('td', nRow);
						var jqInputs = $('input', nRow);
						for (var i = 0; i < jqInputs.length; i++) {
							jqTds[i].innerHTML = jqInputs[i].value;
						}
						jqTds[jqInputs.length].innerHTML = '<a class="edit" href=""><i class="icon-edit"></i>修改</a><a class="delete" href=""><i class="icon-remove"></i>删除</a>';
					}
					function saveRowT(oTable, nRow, item) {
						var jqTds = $('td', nRow);
						var jqInputs = $('input', nRow);
						for (var i = 0; i < jqInputs.length; i++) {
							jqTds[i].innerHTML = jqInputs[i].value;
						}
						jqTds[jqInputs.length].innerHTML = '<a class="edit" mtable="'
								+ item
								+ '" href=""><i class="icon-edit"></i>修改</a>';
					}
					jQuery('#table_one_wrapper').find(".row")
							.addClass("hidden"); // modify table search input
					$("#table_one").children("th").removeClass("sorting");
					jQuery('#table_two_wrapper').find(".row")
							.addClass("hidden"); // modify table search input
					$("#table_two").children("th").removeClass("sorting");
					jQuery('#table_three_wrapper').find(".row").addClass(
							"hidden"); // modify table search input
					$("#table_three").children("th").removeClass("sorting");
					var nEditing = null;

					$('#table_three a.delete').live('click', function(e) {
						var oTable = oTable3;
						e.preventDefault();
						if (confirm("确定删除吗？") == false) {
							return;
						}
						var nRow = $(this).parents('tr')[0].remove();
						alert("删除成功！");
					});

					$('#table_three a.cancel').live('click', function(e) {
						var oTable = oTable3;
						e.preventDefault();
						if ($(this).attr("data-mode") == "new") {
							$(this).parents('tr')[0].remove();
						} else {
							editRow(oTable, nEditing);
						}
					});

					$('#table_three a.edit,#table_one a.edit,#table_two a.edit')
							.live(
									'click',
									function(e) {
										e.preventDefault();
										var oTable;
										var item;
										if ($(this).attr("mtable") == "1") {
											oTable = oTable1;
											item = 1;
										} else if ($(this).attr("mtable") == "2") {
											oTable = oTable2;
											item = 2;
										} else {
											oTable = oTable3;
											item = 3;
										}
										var nRow = $(this).parents('tr')[0];
										if ($(this).attr("key") != null
												&& $(this).attr("key") == "1") {// 保存
											if (item == 3) {
												saveRow(oTable, nRow);
											} else {
												saveRowT(oTable, nRow, item);
											}
											nEditing = null;
											alert("保存成功！");
										} else {// 编辑
											if (item == 3) {
												editRow(oTable, nRow);
											} else {
												editRowT(oTable, nRow, item);
											}
											nEditing = nRow;
										}
									});

				});
function tabAdd(obj) {
	var nTable=$(obj).parents("#table_three");
	jsTrs = $(nTable).find("th");
	var htm = '<tr>';
	for (var i = 0; i < jsTrs.length - 1; i++) {
		htm += '<td><input type="text" class="form-control " style="width:70px" ></td>';
	}
	htm += '<td><a class="edit" key="1" mtable="3" href="#"><i class="icon-edit"></i>保存</a><a class="cancel" data-mode="new" href=""> <i class="icon-remove"></i>取消</a></td></tr>';
	$(nTable).append(htm);
}
function openGoods(obj) {
	var _plan;
	_plan = $(obj).parents("tr").next();
	if ($(_plan).is(":hidden")) {
		$(obj).attr("class", "row-details row-details-open");
		$(_plan).show();
	} else {
		$(_plan).hide();
		$(obj).attr("class", "row-details row-details-close");
	}
}
function removeMain(obj) {
	$(obj).parents("tr").next().remove();
	$(obj).parents("tr").remove();
}
function editMain(obj) {
	if ($(obj).attr("key") == "1") {
		var nRow = $(obj).parents("tr");
		var jqTds = $('td', nRow);
		for (var i = 1; i < jqTds.length - 1; i++) {
			jqTds[i].innerHTML = '<input type="text" class="form-control " style="width:auto" value="'
					+ $(nRow).children("td")[i].innerHTML + '" >';
		}
		jqTds[jqTds.length - 1].innerHTML = '<a class="blue" onclick="editMain(this)" key="2" href="#"><i class="icon-edit"></i>保存</a>';
	} else {
		var nRow = $(obj).parents("tr");
		var jqInputs = $('input', nRow);
		var jqTds = $("td", nRow);
		for (var i = 0; i < jqInputs.length; i++) {
			jqTds[i + 1].innerHTML = $(jqInputs)[i].value;
		}
		jqTds[jqTds.length - 1].innerHTML = '<a class="blue" onclick="editMain(this)" key="1" href="#"><i class="icon-edit"></i>修改</a>'
				+ '<a href="#" class="blue" onclick="removeMain(this)"> <i class="icon-remove"></i>删除</a>';
	}

}
function addMainItem(obj) {
	// $("#example_add").show();
	if(obj=="1"){
	$("#table_main")
			.append(
					"<tr class='odd gradeX'>"
							+ "<td><span class='row-details row-details-close' onclick='openGoods(this)'></span></td>"
							+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
							+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
							+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
							+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
							+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
							+ "<td><a href='javascript:void(0);' class='blue' onclick='editMain(this)' key='2' data-target='#ajax'> <i class='icon-edit'></i>保存</a></td>"
							+ "</tr>");
	$("#table_main")
			.append(
					"<tr style='display: none;' ><td colspan='7' style='padding-left: 50px;'><table class='table table-striped table-bordered table-hover'><tr>"
							+ "<td class='odd gradeX' colspan='6'><table id='table_one' class='table table-striped table-bordered table-hover'><tr>"
							+ "<th align='center'>卸货数量</th>"
							+ "<th align='center'>到港时间</th>"
							+ "<th align='center'>开泵时间</th>"
							+ "<th align='center'>停泵时间</th>"
							+ "<th align='center'>离港时间</th>"
							+ "<th align='center'>操作</th>"
							+ "</tr>"
							+ "<tr>"
							+ "<td><input type='text' class='form-control ' style='width: 140px' value=''></td>"
							+ "<td><input type='text' class='form-control ' style='width:140px' value=''></td>"
							+ "<td><input type='text' class='form-control ' style='width: 140px' value=''></td>"
							+ "<td><input type='text' class='form-control ' style='width: 140px' value=''></td>"
							+ "<td><input type='text' class='form-control ' style='width: 140px' value=''></td>"
							+ "<td><a class='edit' key='1' mtable='1' href=''><i class='icon-edit'></i>保存</a></td>"
							+ "</tr></table></td>"
							+ "</tr>"
							 +"<tr>"
							 +"<td class='odd gradeX' colspan='7'>"
							 +"<table id='table_three' class='table table-striped table-bordered table-hover'>"
							 +"<tr>"
							 +"<td></td>"
							 +"<td colspan='2' align='center'>机房液位（mm）</td>"
							 +"<td colspan='2' align='center'>机房重量（t）</td>"
							 +"<td colspan='2' align='center'>温度（℃）</td>"
							 +"<td colspan='3' align='center'>实收数（t）</td>"
							 +"<td colspan='1' align='left'><div class='table-toolbar'> <a href='javascript:void(0);' onclick='tabAdd(this)' class='blue'> <i class='icon-plus'></i>添加 </a>"
							 +"</div></td>"
							 +"</tr>"
							 +"<tr>"
							 +"<th align='center'>罐区</th>"
							 +"<th align='center'>前尺</th>"
							 +"<th align='center'>后尺</th>"
							 +"<th align='center'>前尺</th>"
							 +"<th align='center'>后尺</th>"
							 +"<th align='center'>前尺</th>"
							 +"<th align='center'>后尺</th>"
							 +"<th align='center'>机房</th>"
							 +"<th align='center'>计量</th>"
							 +"<th align='center'>差异</th>"
							 +"<th align='center'>操作</th>"
							 +"</tr>"
							 +"</table></td></tr>"
							 +"<tr>"
							 +"<td class='odd gradeX' colspan='6'>"
							 +"<table id='table_two' class='table table-striped table-bordered table-hover'>"
							 +"<tr>"
							 +"<th colspan='4' align='center'>储品接卸前验证记录</th>"
							 +"</tr>"
							 +"<tr>"
							 +"<th align='center'>送达时间</th>"
							 +"<th align='center'>报告号</th>"
							 +"<th align='center'>验证结论</th>"
							 +"<th align='center'>通知人</th>"
							 +"<th align='center'>服务评价</th>"
							 +"<th align='center'>评价人</th>"
							 +"<th align='center'>操作</th>"
							 +"</tr>"
							 +"<tr>"
							 +"<td><input type='text' class='form-control' style='width: 130px' value=''></td>"
							 +"<td><input type='text' class='form-control' style='width: 130px' value=''></td>"
							 +"<td><input type='text' class='form-control' style='width: 130px' value=''></td>"
							 +"<td><input type='text' class='form-control' style='width:130px' value=''></td>"
							 +"<td><input type='text' class='form-control' style='width: 130px' value=''></td>"
							 +"<td><input type='text' class='form-control' style='width: 130px' value=''></td>"
							 +"<td><a class='edit' key='1' mtable='2' href='#'><i class='icon-edit'></i>保存</a></td>"
							 +"</tr>"
							 +"</table>"
							 +"</td>"
							 +"</tr>"
							+ "</tbody>" + "</table>" + "</td>" + "</tr>");
	}else{
		$("#table_main")
		.append(
				"<tr class='odd gradeX'>"
						+ "<td><span class='row-details row-details-close' onclick='openGoods(this)'></span></td>"
						+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
						+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
						+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
						+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
						+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
						+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
						+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
						+ "<td><input type='text' class='form-control ' style='width:auto' value=''></td>"
						+ "<td><a href='javascript:void(0);' class='blue' onclick='editMain(this)' key='2' data-target='#ajax'> <i class='icon-edit'></i>保存</a></td>"
						+ "</tr>");
$("#table_main")
		.append(
				"<tr style='display: none;' ><td colspan='7' style='padding-left: 50px;'><table class='table table-striped table-bordered table-hover'>"
						 +"<tr>"
						 +"<td class='odd gradeX' colspan='7'>"
						 +"<table id='table_three' class='table table-striped table-bordered table-hover'>"
						 +"<tr>"
						 +"<td></td>"
						 +"<td colspan='2' align='center'>计量手尺液位（mm）</td>"
						 +"<td colspan='2' align='center'>机房液位（mm）</td>"
						 +"<td colspan='2' align='center'>计量手尺重量（t）</td>"
						 +"<td colspan='2' align='center'>机房重量（t）</td>"
						 +"<td colspan='2' align='center'>温度（℃）</td>"
						 +"<td colspan='3' align='center'>实发数（t）</td>"
						 +"<td></td>"
						 +"<td></td>"
						 +"<td colspan='2' align='left'><div class='table-toolbar'> <a href='javascript:void(0);' onclick='tabAdd(this)' class='blue'> <i class='icon-plus'></i>添加 </a>"
						 +"</div></td>"
						 +"</tr>"
						 +"<tr>"
						 +"<th align='center'>罐区</th>"
						 +"<th align='center'>前尺</th>"
						 +"<th align='center'>后尺</th>"
						 +"<th align='center'>前尺</th>"
						 +"<th align='center'>后尺</th>"
						 +"<th align='center'>前尺</th>"
						 +"<th align='center'>后尺</th>"
						 +"<th align='center'>前尺</th>"
						 +"<th align='center'>后尺</th>"
						 +"<th align='center'>前尺</th>"
						 +"<th align='center'>后尺</th>"
						 +"<th align='center'>机房</th>"
						 +"<th align='center'>计量</th>"
						 +"<th align='center'>差异</th>"
						 +"<th align='center'>计量人员</th>"
						 +"<th align='center'>复合人员</th>"
						 +"<th align='center'>操作</th>"
						 +"</tr>"
						 +"</table></td></tr>"
						 +"<tr>"
						 +"<td class='odd gradeX' colspan='6'>"
						 +"<table id='table_two' class='table table-striped table-bordered table-hover'>"
						 +"<tr>"
						 +"<th align='center'>服务评价</th>"
						 +"<th align='center'>评价人</th>"
						 +"<th align='center'>操作</th>"
						 +"</tr>"
						 +"<tr>"
						 +"<td><input type='text' class='form-control' style='width:130px' value=''></td>"
						 +"<td><input type='text' class='form-control' style='width: 130px' value=''></td>"
						 +"<td><input type='text' class='form-control' style='width: 130px' value=''></td>"
						 +"<td><a class='edit' key='1' mtable='2' href='#'><i class='icon-edit'></i>保存</a></td>"
						 +"</tr>"
						 +"</table>"
						 +"</td>"
						 +"</tr>"
						+ "</tbody>" + "</table>" + "</td>" + "</tr>");
	}
}