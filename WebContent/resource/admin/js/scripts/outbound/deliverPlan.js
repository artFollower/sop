var DeliverPlan = function() {

	var dialog = null; // 对话框
	var dataGrid = null; // Grid对象
	var cId = 0;
	var commomSelect = function() {
		$.ajax({
			type : "get",
			url : config.getDomain() + "/client/select",
			dataType : "json",
			success : function(data) {
				var htm = "";
				for ( var i = 0; i < data.data.length; i++) {
					htm += "<option value=" + data.data[i].id + ">"
							+ data.data[i].name + "</option>";
				}
				$("#deliverPlan_client").append(htm);
			}
		});
	};
	var editTable = function(obj){
		var $o = $(obj).hide().parents("tr").find(".edit_2").show().end().find(".edit_1").hide() ;
	};
	function initEdit(id) {
		commomSelect();
		$
				.ajax({
					type : "get",
					url : config.getDomain() + "/deliverPlan/get?id=" + id,
					dataType : "json",
					success : function(data) {
						var intent = data.data[0];
						var invoiceList = data.data[0].invoiceList;
						$.ajax({
							type : "get",
							url : config.getDomain()
									+ "/baseController/getLadingCode?id=&&code=",
							dataType : "json",
							success : function(data) {
								var htm = "";
								for ( var i = 0; i < data.data.length; i++) {
									htm += "<option value=" + data.data[i].id + ">"
											+ data.data[i].code + "</option>";
								}
								$("#deliverPlan_client").val(intent.clientId);
								$("#deliverPlan-amount").val(intent.amount);
								if (invoiceList != null) {
									for ( var i = 0; i < invoiceList.length; i++) {
										var invoiceTr = "<tr>"; 
										invoiceTr += "<td class='td_amount' data='"+invoiceList[i].amount+ "'><span class='edit_1'>"+invoiceList[i].amount+"</span><input type='text' class='edit_2' style='display: none;' value='"+invoiceList[i].amount+"'/></td>";
										invoiceTr += "<td class='invoicelading' data='"+invoiceList[i].ladingId+"'><span class='edit_1'>"+invoiceList[i].code+
										"</span><select class='form-control ladingCode edit_2'  style='display: none;' data-placeholder='选择提单号..' id='invoice_lading' data-required='1' value="+invoiceList[i].ladingId+">"+htm+"</select>"+
										"</td>";
										invoiceTr += "<td>"
													+ "<a href='javascript:void(0)' class='btn btn-xs blue' onclick='DeliverPlan.editTable(this)'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a>"
													+ "<a href='javascript:void(0)' class='blue submit edit_2' onclick='DeliverPlan.saveTable("
													+ invoiceList[i].id
													+ ",this)' style='display: none;'> <i class='icon-edit'></i>保存</a> "
													+ "<a href='javascript:void(0)' class='blue concel' onclick='DeliverPlan.concel(this)' style='display: none;'> <i class='icon-edit'></i>取消</a>"
													+ "</td>";
											invoiceTr += "</tr>";
											$("#deliverPlan_invoiceTable").children("tbody")
													.append(invoiceTr);
									}
								}
							}
						});
					}
				});
	}
	
	var deleteItem = function(id) {
		var data = $('[data-role="deliverPlanGrid"]').getGrid()
				.selectedRowsIndex();
		var $this = $('[data-role="deliverPlanGrid"]');

		$this.confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
				// deleteIntent($('[data-role="contractGrid"]').getGrid().selectedRows(),
				// $this);

				var data = "";
				data += ("id=" + id);

				$.post(config.getDomain() + '/deliverPlan/delete', data).done(
						function(data) {
							if (data.code == "0000") {
								$('[data-role="deliverPlanGrid"]').message({
									type : 'success',
									content : '删除成功'
								});
								$('[data-role="deliverPlanGrid"]').grid(
										'refresh');
							} else {
								$('body').message({
									type : 'error',
									content : data.msg
								});
							}
						}).fail(function(data) {
					$('[data-role="contractGrid"]').message({
						type : 'error',
						content : '删除失败'
					});
				});

			}
		});
	};

	// 初始化对话框
	var initDialog = function(dialog) {
		$.ajax({
			type : "get",
			url : config.getDomain()
					+ "/baseController/getLadingCode?id=&&code=",
			dataType : "json",
			success : function(data) {
				var htm = "";
				for ( var i = 0; i < data.data.length; i++) {
					htm += "<option value=" + data.data[i].id + ">"
							+ data.data[i].code + "</option>";
				}
				dialog.find("#invoice_lading").append(htm);
			}
		});
		// 添加提单号
		dialog
				.find("#button-add-goods")
				.click(
						function() {
							var isOk = true;
							dialog
									.find("#addGoodsForm")
									.find("input select")
									.each(
											function() {
												if ($(this).attr(
														"data-required") == 1) {
													if ($(this).val() == null
															|| $(this).val() == "") {
														$(this).pulsate({
															color : "#bf1c56",
															repeat : false
														});
														isOk = false;
														return;
													}
												}
											});
							if (isOk) {
								var invoiceTr = "<tr>";
								invoiceTr += "<td class='td_amount' data='"
										+ dialog.find("#invoice-amount").val()
										+ "'>"
										+ dialog.find("#invoice-amount").val()
										+ "</td>";
								invoiceTr += "<td class='invoicelading' data='"
										+ dialog
												.find(
														"#invoice_lading option:selected")
												.val()
										+ "'>"
										+ dialog
												.find(
														"#invoice_lading option:selected")
												.text() + "</td>";
								invoiceTr += "<td><a href='javascript:void(0)' class='blue' onclick='DeliverPlan.removeInvoice(this)'> <i class='icon-remove'></i>删除</a></td>";
								invoiceTr += "</tr>";
								$("#deliverPlan_invoiceTable")
										.children("tbody").append(invoiceTr);
							}
						});
	};

	var removeInvoice = function(obj) {
		$(obj).parents("tr").remove();
	};

	var deleteIntent = function(intents, grid) {
		dataGrid = grid;

		var data = "";
		$.each(intents, function(i, role) {
			data += ("id=" + role.id + "&");
		});
		data = data.substring(0, data.length - 1);

		$.post(config.getDomain() + '/deliverPlan/delete', data).done(
				function(data) {
					if (data.code == "0000") {
						dataGrid.message({
							type : 'success',
							content : '删除成功'
						});
						dataGrid.grid('refresh');
					} else {
						$('body').message({
							type : 'error',
							content : data.msg
						});
					}
				}).fail(function(data) {
			dataGrid.message({
				type : 'error',
				content : '删除失败'
			});
		});
	};

	var handleRecords = function() {
		var columns = [
				{
					title : "流水号",
					name : "id"
				},
				{
					title : "船名",
					name : "name"
				},
				{
					title : "客户电话",
					name : "phone",
				},
				{
					title : "状态",
					name : "statusName"
				},
				{
					title : "提货总量",
					name : "amount"
				},
				{
					title : "操作",
					name : "id",
					render : function(item, name, index) {
						return "<a href='#/deliverPlanGet?id="
								+ item.id
								+ "'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a>"
								+ "<a href='javascript:void(0)' onclick='DeliverPlan.deleteItem("
								+ item.id
								+ ")' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a>";
					}
				} ];

		/* 解决id冲突的问题 */
		$('[data-role="deliverPlanGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain() + "/deliverPlan/list"
		});

		// 初始化按钮操作
		$(".btn-add").click(function() {
			window.location.href = "#/deliverPlanAdd?i=" + Math.random();
		});

		$(".btn-remove").click(
				function() {
					var data = $('[data-role="deliverPlanGrid"]').getGrid()
							.selectedRowsIndex();
					var $this = $('[data-role="deliverPlanGrid"]');
					if (data.length == 0) {
						$this.message({
							type : 'warning',
							content : '请选择要撤销的记录'
						});
						return;
					}
					$this.confirm({
						content : '确定要撤销所选记录吗?',
						callBack : function() {
							deleteIntent($('[data-role="deliverPlanGrid"]')
									.getGrid().selectedRows(), $this);
						}
					});
				});

		$(".btn-modify").click(
				function() {
					var data = $('[data-role="deliverPlanGrid"]').getGrid()
							.selectedRowsIndex();
					var $this = $(this);
					if (data.length == 0) {
						$this.message({
							type : 'warning',
							content : '请选择要修改的记录'
						});
						return;
					}
					window.location.href = "#/deliverPlanGet?id="
							+ $('[data-role="arrivalGrid"]').getGrid()
									.selectedRows()[0].id;
				});

		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});

	};
	function openAdd() {
		$.get(config.getResource() + "/pages/outbound/deliverPlan/invoice.jsp")
				.done(function(data) {
					dialog = $(data);
					initDialog(dialog);
					dialog.modal({
						keyboard : true
					});
				});
	}
	
	var saveTable=function(obj){
		$(obj).hide().parents("tr").find(".edit_1").show().end().find(".edit_2").hide() ;
	};
	
	var initAdd = function() {
		commomSelect();
		$(".planSubmit")
				.click(
						function() {
							var isOk = true;
							$(".planInfo")
									.find("input,select")
									.each(
											function() {
												if ($(this).attr(
														"data-required") == 1) {
													if ($(this).val() == null
															|| $(this).val() == "") {
														$(this).pulsate({
															color : "#bf1c56",
															repeat : false
														});
														isOk = false;
														return;
													}
												}
											});
							if (isOk) {
								var deliverPlan = {
									'clientId' : $("#deliverPlan_client").val(),
									'amount' : $("#deliverPlan-amount").val()
								};
								var deliverInvoiceList = new Array();
								$("#deliverPlan_invoiceTable")
										.children("tbody")
										.children("tr")
										.each(
												function() {
													deliverInvoiceList
															.push({
																'amount' : $(
																		this)
																		.find(
																				".td_amount")
																		.attr(
																				"data"),
																'ladingId' : $(
																		this)
																		.find(
																				".invoicelading")
																		.attr(
																				"data")
															});
												});
								var deliver_add_dto = {
									"deliverPlan" : deliverPlan,
									"deliverInvoiceList" : deliverInvoiceList
								};
								$
										.ajax({
											type : "post",
											url : config.getDomain()
													+ "/deliverPlan/save",
											data : {
												"deliver_add_dto" : JSON
														.stringify(deliver_add_dto)
											},
											dataType : "json",
											success : function(data) {
												if (data.code == "0000") {
													alert("到港计划添加成功");
													openMenu(
															null,
															"<%=basePath%>deliverPlan/list",
															0);
												} else {
													alert("到港计划添加失败");
												}
											}
										});

							}

						});
	};
	return {
		init : function() {
			handleRecords();
		},
		initAdd : function() {
			initAdd();
		},
		openAdd : function() {
			openAdd();
		},
		removeInvoice : function(obj) {
			removeInvoice(obj);
		},
		deleteItem : function(id) {
			deleteItem(id);
		},
		initEdit:function(id){
			initEdit(id) ;
		},
		editTable:function(obj){
			editTable(obj) ;
		},
		saveTable:function(obj){
			saveTable(obj) ;
		}
	};

}();