var Lading = function() {

	var dialog = null; // 对话框
	var dataGrid = null; // Grid对象
var loadCount=3;

	var guoqi=0;
	// 获取项目路径
	function getRootPath() {
		var curWwwPath = window.document.location.href;
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		var localhostPaht = curWwwPath.substring(0, pos);
		var projectName = pathName.substring(0,
				pathName.substr(1).indexOf('/') + 1);
		return (localhostPaht + projectName);
	}
	
	//导出通知单
	function exportExcel(){
		
		var params = {};
		$("#ladingListForm").find('.form-control').each(function() {
			var $this = $(this);
			var name = $this.attr('name');
			var data=$this.attr('data');
			if(data&&data!=""){
            	params[name]=$this.attr('data');
            }else
            	if(name){
               	 if($this.val()!=""){
               		 params[name] = $this.val();
               	 }else{
               		 
               	 }
               }
		});
		
		if(guoqi==1){
			 params["status"]=4;
		}
		
		var productId=params["productId"]?params["productId"]:'0';
		var buyClientId=params["buyClientId"]?params["buyClientId"]:'0';
		var type=params["type"]?params["type"]:'0';
		var code=params["code"]?params["code"]:'';
		var clientId=params["clientId"]?params["clientId"]:'';
		var status=params["status"]?params["status"]:'0';
		
	
		
			
		
		var url = config.getDomain()+"/lading/exportExcel?buyClientId="+buyClientId+"&productId="+productId+"&type="+type+"&code="+code+"&clientId="+clientId+"&status="+status;
		window.open(url) ;
	}
	
	var reset=function(){
		$("#productId").val("");
		$("#productId").attr("data","");
		
		$("#ladingClientId").val("");
		$("#ladingClientId").attr("data","");
		$(".code0").val("");
		$("#clientId0").val("");
		$("#clientId0").attr("data","");
		$("#type").val("").trigger("change"); 

		$("#status").val("").trigger("change"); 

//		Lading.search();
	}
	

	function openFileModal(id) {
		$.get(config.getResource() + "/pages/outbound/lading/fileAdd.jsp")
				.done(function(data) {
					dialog = $(data);
					initFileModal(dialog, id);
					dialog.modal({
						keyboard : true
					});
				});
	}

	function initFileModal(dialog, id) {
		dialog.find('.addFile').click(function() {
			dialog.find('input[id=lefile]').click();
		});
		dialog.find('input[id=lefile]').change(function() {
			dialog.find('#photoCover').val($(this).val());
		});
		dialog.find("#id").val(id);
		dialog.find(".button-ok").click(function() {
			// 使用 jQuery异步提交表单

			// dialog.find('#fileForm').submit();
			// dialog.find('#fileForm').ajaxSubmit(function(data){
			// });
			dialog.find('#fileForm').ajaxSubmit({
				success : function(data) {
					if (data.code == "0000") {
						$("body").message({
							type : 'success',
							content : '上传成功'
						});
						dialog.remove();
						$('[data-role="ladingGrid"]').grid('refresh');
					} else {
						$("body").message({
							type : 'error',
							content : '上传失败'
						});
						dialog.remove();
					}

				}
			});
		});

	}
	// 改变tab
	function changetab(obj, item) {
//		$(obj).parent().addClass("active").siblings().removeClass("active");
		$(this).parent().addClass("active").siblings().removeClass("active");
		$("#portlet_tab"+item).addClass("active").siblings().removeClass("active");
		$(".search-key").val("");
		currentPage = item;
		if (item == 1) {
			// 提单
			$(".searchCode").text("提单号:");
			$(".searchClient").text("发货单位:");
			$(".buttons").show();
			$(".ladingStatus").show();
			$(".status").show();
//			$(".status").find("option[value='']").attr("selected","selected"); 

			$(".select2-chosen").text("请选择提单状态...");
			$("#clientId0").val("");
			$(".code0").val("");
			$("#row1").show();
			handleRecords();
		}
		if (item == 2) {
			// 原始货体
			$(".searchCode").text("货体号:");
			$(".searchClient").text("货主:");
			$(".buttons").hide();
			$(".ladingStatus").hide();
			$(".status").hide();
			$("#row1").hide();
//			$(".status").val("");
			$(".select2-chosen").text("请选择提单状态...");
			$("#clientId0").val("");
			$(".code0").val("");
			initGoodsList();
		}
	}

	function initGoodsList() {

		var columns = [
				{
					title : "货体号",
					name : "code"
				},
				{
					title : "货主",
					name : "clientName"
				},
				{
					title : "货品名",
					name : "productName"
				}, {
					title : "进货船信",
					width : 100,
					name : "shipName",
					render: function(item, name, index){
						
						var shipName=item.shipName==null?"":item.shipName;
						if(item.arrivalStartTime){
							return shipName+" "+item.arrivalStartTime.split("-")[1]+"/"+item.arrivalStartTime.split("-")[2].split(" ")[0];
						}
						}
				},
				{
					title : "当前存量(吨)",
					name : "goodsCurrent",
				    render : function(item, name, index) {
						return util.toDecimal3(item.goodsCurrent,true);
					}

				},
				{
					title : "调出量(吨)",
					name : "goodsOut",
					render : function(item, name, index) {
						var goodsOut=item.goodsOut==""?0.0:item.goodsOut;
						return util.toDecimal3(goodsOut,true);
					}

				},
				{
					title : "已发量(吨)",
					name : "goodsCurrent",
					render : function(item, name, index) {
						var goodsTotal = item.goodsTotal == "" ? 0
								: item.goodsTotal;
						var goodsOut = item.goodsOut == "" ? 0 : item.goodsOut;
						var goodsCurrent = item.goodsCurrent == "" ? 0
								: item.goodsCurrent;
						return (util.FloatSub(util.FloatSub(goodsTotal,
								goodsOut), goodsCurrent));
					}

				},
				{
					title : "放行数量(吨)",
					name : "goodsOutPass",
					render : function(item, name, index) {
						var goodsOutPass=item.goodsOutPass==""?0.0:item.goodsOutPass;
						return util.toDecimal3(goodsOutPass,true);
					}

				},
				{
					title : "操作",
					name : "id",
					render : function(item, name, index) {
						return "<a href='javascript:void(0)' class='btn btn-xs blue look' onclick='GoodsLZ.openGoodsLZ("
								+ item.id
								+ ")' data-target='#ajax' data-toggle='modal' id='goodsShow'><span class='glyphicon glyphicon glyphicon-eye-open' title='查看'></span></a>";

					}
				} ];

		// 清空grid缓存
		if ($('[data-role="ladingGrid"]').getGrid() != null) {
			$('[data-role="ladingGrid"]').getGrid().destory();
		}

		/* 解决id冲突的问题 */
		$('[data-role="ladingGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			url : config.getDomain() + "/lading/getOriginalGoods"
			
		});
	}

	function addNewClient() {
		$
				.get(
						config.getResource()
								+ "/pages/auth/baseinfo/client/add.jsp")
				.done(
						function(data) {
							dialog = $(data);
							dialog.modal({
								keyboard : true
							});

							$
									.ajax({
										async : false,
										type : "get",
										url : config.getDomain()
												+ "/clientgroup/list",
										dataType : "json",
										success : function(data) {
											dialog
													.find('#clientGroupId')
													.typeahead(
															{
																source : function(
																		query,
																		process) {
																	var results = _
																			.map(
																					data.data,
																					function(
																							product) {
																						return product.name;
																					});
																	process(results);
																},
																noselect : function(
																		query) {
																	var client = _
																			.find(
																					data.data,
																					function(
																							p) {
																						return p.name == query;
																					});
																	if (client == null) {
																		dialog
																				.find(
																						'#clientGroupId')
																				.removeAttr(
																						"data");
																		dialog
																				.find(
																						'#clientGroupId')
																				.val(
																						"");
																	} else {
																		dialog
																				.find(
																						'#clientGroupId')
																				.attr(
																						"data",
																						client.id)
																	}
																}
															});
										}
									});

							dialog
									.find("#addClient")
									.click(
											function() {
												if (config.validateForm(dialog
														.find(".form-client"))) {
													$
															.ajax({
																type : "post",
																url : config
																		.getDomain()
																		+ "/client/save",
																data : {
																	"code" : dialog
																			.find(
																					".code")
																			.val(),
																	"name" : dialog
																			.find(
																					".name")
																			.val(),
																	"phone" : dialog
																			.find(
																					".phone")
																			.val(),
																	"guestId" : dialog
																			.find(
																					".guestId")
																			.val(),
																	"email" : dialog
																			.find(
																					".email")
																			.val(),
																	"fax" : dialog
																			.find(
																					".fax")
																			.val(),
																	"address" : dialog
																			.find(
																					".address")
																			.val(),
																	"postcode" : dialog
																			.find(
																					".postcode")
																			.val(),
																	"contactName" : dialog
																			.find(
																					".contactName")
																			.val(),
																	"contactPhone" : dialog
																			.find(
																					".contactPhone")
																			.val(),
																	"bankAccount" : dialog
																			.find(
																					".bankAccount")
																			.val(),
																	"bankName" : dialog
																			.find(
																					".bankName")
																			.val(),
																	"creditGrade" : dialog
																			.find(
																					".creditGrade")
																			.val(),
																	"paymentGrade" : dialog
																			.find(
																					".paymentGrade")
																			.val(),
																	"description" : dialog
																			.find(
																					".description")
																			.val(),
																	"clientGroupId" : dialog
																			.find(
																					'#clientGroupId')
																			.attr(
																					"data")
																},
																// data:JSON.stringify(sendGroup),
																dataType : "json",
																success : function(
																		data) {
																	if (data.code == "0000") {
																		$(
																				"body")
																				.message(
																						{
																							type : 'success',
																							content : '客户添加成功'
																						});
																		dialog
																				.remove();
																		$(
																				".receiveClientId")
																				.val(
																						dialog
																								.find(
																										".name")
																								.val());
																		$(
																				".receiveClientId")
																				.attr(
																						"data",
																						data.map.id);
																	} else {
																		$(
																				"body")
																				.message(
																						{
																							type : 'error',
																							content : '客户添加失败'
																						});
																		dialog
																				.remove();
																	}
																}
															});
												}
											});
						});
	}

	// 根据提单类型选择不同时间
	function changeType() {
		if ($("#type").val() == -1) {
			$("#tdStartTime").hide();
			$("#startTime").removeAttr("data-required");
			$("#startTime").removeAttr("data-type");
			$("#tdEndTime").hide();
			$("#endTime").removeAttr("data-required");
			$("#endTime").removeAttr("data-type");
		}
		if ($("#type").val() == 1) {
			$("#tdStartTime").show();
			$("#startTime").attr("data-required",1);
			$("#startTime").attr("data-type","Require");
			$("#tdEndTime").hide();
			$("#endTime").removeAttr("data-required");
			$("#endTime").removeAttr("data-type");
		}
		if ($("#type").val() == 2) {
			$("#tdStartTime").hide();
			$("#startTime").removeAttr("data-required");
			$("#startTime").removeAttr("data-type");
			$("#tdEndTime").show();
//			$("#endTime").attr("data-required",1);
//			$("#endTime").attr("data-type","Require");
		}

	}
	// 打开编辑货体dialog
	function openModal(type) {
		// $('#ajax').load(config.getResource()+"/pages/inbound/arrival/addCustomer.jsp");
		// $('#ajax').modal('show');
		$.get(config.getResource() + "/pages/outbound/lading/ladingGoods.jsp")
				.done(function(data) {
					dialog = $(data);
					initModal(dialog, type);
					dialog.modal({
						keyboard : true
					});
				});
	}

//	// 打开树状图dialog
//	function openModal1(id) {
//		// $('#ajax').load(config.getResource()+"/pages/inbound/arrival/addCustomer.jsp");
//		// $('#ajax').modal('show');
//		// $.get(config.getResource()+"/pages/inbound/arrival/showGoods.jsp").done(function(data){
//		// dialog = $(data) ;
//		// initModal1(dialog,id);
//		// dialog.modal({
//		// keyboard: true
//		// });
//		// });
//
//		$.get(config.getResource() + "/pages/inbound/arrival/mShowGoods.jsp")
//				.done(function(data) {
//					dialog = $(data);
//					initModal2(dialog, id);
//					dialog.modal({
//						keyboard : true
//					});
//				});
//
//	}
//
//	// 打开货体流水表dialog
//	function openModal2(id) {
//
//		$.get(config.getResource() + "/pages/outbound/lading/goodsLS.jsp")
//				.done(function(data) {
//					dialog = $(data);
//					initModal3(dialog, id);
//					dialog.modal({
//						keyboard : true
//					});
//				});
//
//	}
//
//	// 货体流水表
//	function initModal3(dialog, id) {
//		var colums = [
//				{
//					title : "上下家",
//					name : "clientName"
//				// render: function(item, name, index){
//				// if(item.type==5){
//				// return item.ladingClientName;
//				// }else{
//				// return item.clientName;
//				// }
//				// }
//				},
//				{
//					title : "提货单位",
//					name : "ladingClientName"
//				},
//				{
//					title : "方式",
//					name : "type",
//					render : function(item, name, index) {
//						switch (item.type) {
//						case 1:
//							return "入库";
//							break;
//						case 2:
//							return "调入";
//							break;
//						case 3:
//							return "调出";
//							break;
//						case 4:
//							return "封放";
//							break;
//						case 5:
//							return "发货";
//							break;
//						case 6:
//							return "退回";
//							break;
//						case 7:
//							return "预入库";
//							break;
//						case 9:
//							return "扣损";
//							break;
//						}
//					}
//				},
//				{
//					title : "日期",
//					name : "createTime",
//					render : function(item, name, index) {
//						return new Date(item.createTime * 1000)
//								.Format("yyyy-MM-dd hh:mm:ss");
//					}
//				}, {
//					title : "单号",
//					name : "ladingCode"
//				}, {
//					title : "货量变化(吨)",
//					name : "goodsChange"
//				}, {
//					title : "存量(吨)",
//					name : "surplus"
//				}, {
//					title : "封量(吨)",
//					name : "goodsSave"
//				}, {
//					title : "发货单",
//					name : "deliverNo"
//				}, {
//					title : "车船号",
//					name : "carshipCode"
//				} ];
//
//		dialog.find('[data-role="goodsLSGrid"]').grid({
//			identity : 'id',
//			columns : colums,
//			isShowIndexCol : false,
//			isShowPages : false,
//			lockWidth : false,
//			// isUserLocalData: true,
//			// localData : data,
//			url : config.getDomain() + "/lading/getGoodsLS?goodsId=" + id
//		});
//	}
//
//	function initModal2(dialog, id) {
//		var columns = [
//				{
//					title : "货主名",
//					name : "name",
//					width : 250,
//					render : function(item, name, index) {
//						if (item.goodsCurrent > 0) {
//							return "<a href='javascript:void(0)' onclick='Lading.openModal2("
//									+ item.id
//									+ ")' ><span class='fa fa-square' title='有量'></span>"
//									+ item.name + "</a>";
//						} else {
//							return "<a href='javascript:void(0)' onclick='Lading.openModal2("
//									+ item.id
//									+ ")'><span class='fa fa-square-o' title='无量'></span>"
//									+ item.name + "</a>";
//						}
//					}
//				},
//				{
//					title : "提单代码",
//					name : "ladingCode"
//				},
//				{
//					title : "提单类型",
//					name : "ladingType"
//				},
//				{
//					title : "进货量(吨)",
//					name : "goodsTotal"
//				},
//				{
//					title : "调出总量(吨)",
//					name : "goodsOut"
//				},
//				{
//					title : "发货量(吨)",
//					name : "send",
//					render : function(item, name, index) {
//						return util.toDecimal3(util.toDecimal3(item.goodsTotal
//								- item.goodsOut)
//								- item.goodsCurrent);
//					}
//				}, {
//					title : "当前存量(吨)",
//					name : "goodsCurrent",
//					render : function(item, name, index) {
//						return util.toDecimal3(item.goodsCurrent);
//					}
//				} ];
//
//		dialog.find('[data-role="goodsTreeGrid"]').treeGrid({
//			identity : 'id',
//			columns : columns,
//			isShowIndexCol : false,
//			isShowPages : false,
//			url : config.getDomain() + "/lading/show?id=" + id,
//			tree : {
//				column : 'name'
//			}
//		});
//
//	}

	function initModal1(dialog, id) {

		config.load();
		$.ajax({
			type : "get",
			url : config.getDomain() + "/lading/show?id=" + id,
			dataType : "json",
			success : function(data) {
				config.unload();
				var goodsList = data.data;
				if (goodsList != "") {
					var goods = "";
					for (var i = 0; i < goodsList.length; i++) {
						goods += goodsList[i].l_rows;
					}
					dialog.find("#treeTable1").children("tbody").append(goods);
				}

			}
		});

		var option = {
			theme : 'default',
			expandLevel : 4,
			beforeExpand : function($treeTable, id) {
				// 判断id是否已经有了子节点，如果有了就不再加载，这样就可以起到缓存的作用
				if (dialog.find('.' + id, $treeTable).length) {
					return;
				}

				$treeTable.addChilds(html);
			},
			onSelect : function($treeTable, id) {
				window.console && console.log('onSelect:' + id);

			}

		};
		dialog.find('#treeTable1').treeTable(option);
		option.theme = 'vsStyle';
		dialog.find('#treeTable2').treeTable(option);
	}

	// .find('input[type="checkbox"][name="chk_list"]:checked').each(function(){
	// var _tr = $(this).parents("tr");
	// count =
	// parseFloat(parseFloat(count)+parseFloat($(_tr).find(".ladingCount").val()));
	// });
	// $(dialog).parents("tr").parents(".portlet-body").find(".count").val(count);

	function initModal(dialog, type) {
		dialog.find('#ladingGoodsTable .group-checkable').change(function() {
			var set = $(this).attr("data-set");
			var checked = $(this).is(":checked");
			$(set).each(function() {
				if (checked) {
					$(this).attr("checked", true);
				} else {
					$(this).attr("checked", false);
				}
			});
//			$.uniform.update(set);
		});
		var clientId;
		var productId;
		var ladingType;
		if (type == 1) {
			dialog.find("#serchCustName").val($("#clientId").val());
			dialog.find("#getCustName").val($("#receiveClientId").val());
			dialog.find("#serchGoodsName").val($("#productName").val());
			dialog.find("#ladingType").val(
					$("#type").find("option:selected").text());
			
			
			clientId = $("#clientId").attr("data");
			productId = $("#productName").attr("data");
			ladingType = $("#type").val();
		} else {
			dialog.find("#serchCustName").val($("#custName").val());
			dialog.find("#getCustName").val($("#reviveCustName").val());
			dialog.find("#serchGoodsName").val($("#productName").val());
			dialog.find("#ladingType").val($("#type").val());
			clientId = $("#custName").attr("data");
			productId = $("#productName").attr("data");
			ladingType = $("#type").attr("data");
		}
		dialog
				.find(".serchButton")
				.click(
						function() {
							config.load();
							$
									.ajax({
										type : "post",
										url : config.getDomain()
												+ "/lading/serchGoods",
										data : {
											"clientId" : clientId,
											"productId" : productId,
											"ladingType" : ladingType,
											"ladingId" : $("#inputId").val()
										},
										// data:JSON.stringify(sendGroup),
										dataType : "json",
										success : function(data) {
											config.unload();
											if (data.code == "0000") {
												dialog.find("#ladingGoodsTable").children("tbody").children("tr").remove();
												var goods = "";
												for (var i = 0; i < data.data.length; i++) {
													var goodsInfo = data.data[i];
													for (var j = 0; j < goodsInfo.goodsdata.length; j++) {
														var goodsIn = goodsInfo.goodsdata[j];
														if (Math
																.min(
																		(goodsIn.goodsOutPass - (goodsIn.goodsTotal - goodsIn.goodsCurrent+goodsIn.goodsWait)),
																		goodsIn.goodsInPass,
																		goodsIn.goodsOutPass) != 0) {
															goods += "<tr>";
															goods += "<input type='hidden' id='ladingGoodsId' value='"
																	+ goodsIn.id
																	+ "' />";
															goods += "<td><input type='checkbox' class='checkboxes' name='chk_list'/></td>";
															goods += "<td class='code'><div style='width:80px;'><a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("
																	+ goodsIn.id
																	+ ")' id='goodsShow'>"+ goodsIn.code+"</a>"
																+ "</div></td>";
															var ladingCode=goodsIn.ladingCode==null?"":goodsIn.ladingCode;
															goods += "<td class='code'><div style='width:50px;'></div>"
																	+ ladingCode
																	+ "</td>";
															
															var rLadingCode="";
															if(goodsIn.rootGoodsId==null){
																rLadingCode=goodsIn.cargoCode;
															}else if(goodsIn.rootGoodsId==0){
																rLadingCode=goodsIn.ladingCode==null?"":goodsIn.ladingCode;
															}else{
																rLadingCode=goodsIn.rLadingCode==null?"":goodsIn.rLadingCode;
															}
															goods += "<td class='code'><div style='width:50px;'></div>"
																+ rLadingCode
																+ "</td>";
															var sourceClientName=goodsIn.goodsLadingClientName==null?"":goodsIn.goodsLadingClientName;
															
															goods += "<td class='clientName'><div style='width:50px;'></div>"
																+ sourceClientName
																+ "</td>";
															var ladingClientName=goodsIn.ladingClientName==null?goodsIn.clientName:goodsIn.ladingClientName;
															
															goods += "<td class='clientName'><div style='width:50px;'></div>"
																	+ ladingClientName
																	+ "</td>";
															goods += "<td class='cargoCode'><div style='width:50px;'></div>"
																	+ goodsIn.cargoCode
																	+ "</td>";
															
															var originCode=goodsIn.originGoodsCode==null?(goodsIn.sourceCode==null?goodsIn.code:goodsIn.sourceCode):goodsIn.originGoodsCode;
															goods += "<td class='originGoodsCode'><div style='width:50px;'></div>"
																+ originCode
																+ "</td>";
															if(goodsIn.arrivalStartTime){
																var shipName=goodsIn.shipName==null? "":goodsIn.shipName;
																goods += "<td class='arrivalInfo'><div style='width:50px;'></div>"
																	+ shipName+" "+goodsIn.arrivalStartTime.split("-")[1]+"/"+goodsIn.arrivalStartTime.split("-")[2].split(" ")[0]
																+ "</td>";
															}else{
																goods += "<td class='arrivalInfo'><div style='width:50px;'></div>"
																	
																+ "</td>";
															}
															
															var ladingType="";
															if(goodsIn.ladingType==1){
																ladingType="转卖";
															}
															if(goodsIn.ladingType==2){
																ladingType="发货";
															}
															goods += "<td class='ladingType'><div style='width:50px;'></div>"
																+ ladingType
																+ "</td>";
															
															var ladingTime="";
															if(goodsIn.ladingStartTime){
																ladingTime=goodsIn.ladingStartTime.substring(0,goodsIn.ladingStartTime.length-2);
															}else if(goodsIn.ladingEndTime){
																
																if(new Date().getTime()>new Date(goodsIn.ladingEndTime).getTime()){
																	ladingTime=  "<span style='color:red'>"+ goodsIn.ladingEndTime.substring(0,goodsIn.ladingEndTime.length-2)+"</span>";
																}else{
																	
																	ladingTime=goodsIn.ladingEndTime.substring(0,goodsIn.ladingEndTime.length-2);
																}
																
															}	
															
															if(!goodsIn.sourceGoodsId&&!goodsIn.rootGoodsId&&goodsIn.tArrivalStartTime&&goodsIn.period){
																
																ladingTime=new Date(goodsIn.tArrivalStartTime*1000+goodsIn.period*86400000).Format("yyyy-MM-dd");
																
															}
															
															
															goods += "<td class='ladingTime'><div style='width:50px;'></div>"
																+ ladingTime
																+ "</td>";
															goods += "<td class='productName'><div style='width:50px;'></div>"
																	+ goodsIn.productName
																	+ "</td>";
															
															goods += "<td class='tankCode'><div style='width:50px;'></div>"
																+ (goodsIn.tankCode?goodsIn.tankCode:"")
																+ "</td>";
															// goods += "<td
															// class='goodsCuent'>"+Math.min((util.FloatSub(goodsIn.goodsOutPass,(util.FloatSub(goodsIn.goodsTotal,goodsIn.goodsCurrent)))),goodsIn.goodsInPass,goodsIn.goodsOutPass)+"</td>";
															goods += "<td class='goodsTotal'><div style='width:50px;'></div>"
																+ goodsIn.goodsTotal
																+ "</td>";
															goods += "<td class='goodsSave'><div style='width:50px;'></div>"
																+ util.FloatSub(goodsIn.goodsTotal,goodsIn.goodsOutPass)
																+ "</td>";
															goods += "<td class='goodsWait'><div style='width:50px;'></div>"
																+ goodsIn.goodsWait
																+ "</td>";
															
															var shipCount=goodsIn.shipCount?goodsIn.shipCount:0;
															goods += "<td class='shipCount'><div style='width:50px;'></div>"
																+ shipCount
																+ "</td>";
															goods += "<td class='goodsCuent'><div style='width:50px;'></div>"
																	+ util
																			.FloatSub(
																					util.FloatSub(util.FloatSub(goodsIn.goodsCurrent,goodsIn.shipCount),goodsIn.goodsWait),
																					util
																							.FloatSub(
																									goodsIn.goodsTotal,
																									Math
																											.min(
																													goodsIn.goodsInPass,
																													goodsIn.goodsOutPass)))
																	+ "</td>";

															goods += "<td><input type='text' maxlength='9' onkeyup='config.clearNoNum(this)'  class='ladingCount' value='0' /></td>";
															goods += "</tr>";
														}
													}
												}
												dialog
														.find(
																"#ladingGoodsTable")
														.children("tbody")
														.append(goods);

												dialog
														.find(
																"#ladingGoodsTable")
														.children("tbody")
														.each(
																function() {
																	$(this)
																			.find(
																					'.checkboxes')
																			.change(
																					function() {
																						var count = 0;
																						dialog
																								.find(
																										'input[type="checkbox"][name="chk_list"]:checked')
																								.each(
																										function() {
																											var _tr = $(
																													this)
																													.parents(
																															"tr");
																											count = parseFloat(util
																													.FloatAdd(
																															parseFloat(count),
																															parseFloat($(
																																	_tr)
																																	.find(
																																			".ladingCount")
																																	.val())));
																										});
																						dialog
																								.find(
																										"#count")
																								.text(
																										count);
																					});

																	$(this)
																			.find(
																					'.ladingCount')
																			.keyup(
																					function() {
																						var count = 0;
																						dialog
																								.find(
																										'input[type="checkbox"][name="chk_list"]:checked')
																								.each(
																										function() {
																											var _tr = $(
																													this)
																													.parents(
																															"tr");
																											count = parseFloat(util
																													.FloatAdd(
																															parseFloat(count),
																															parseFloat($(
																																	_tr)
																																	.find(
																																			".ladingCount")
																																	.val())));
																										});
																						dialog
																								.find(
																										"#count")
																								.text(
																										count);
																					});
																});
											} else {
												$(this).confirm({
													content : '货体检索失败!',
													concel : true,
													callBack : function() {
													}
												});
											}
										}
									});
						});

		// 添加货批
		dialog
				.find(".button-add-goods")
				.click(
						function() {
							var cando = true;
							var noCheck = true;
							dialog
									.find("#ladingGoodsTable")
									.children("tbody")
									.each(
											function() {
												dialog
														.find(
																'input[type="checkbox"][name="chk_list"]:checked')
														.each(
																function() {
																	noCheck = false;
																	var _tr = $(
																			this)
																			.parents(
																					"tr");
																	if (parseFloat($(
																			_tr)
																			.find(
																					".ladingCount")
																			.val()) > parseFloat($(
																			_tr)
																			.find(
																					".goodsCuent")
																			.text())) {
																		$(this)
																				.confirm(
																						{
																							content : '放入提单数量不可大于可提量，请重新输入！',
																							concel : true,
																							callBack : function() {
																							}
																						});
																		cando = false;
																		return;
																	}
																});
											});
							if (noCheck) {
								$(this).confirm({
									content : '请至少选择一个货体！',
									concel : true,
									callBack : function() {
									}
								});
								return;
							}
							if (cando) {
								var goodsIdList = "";
								var goodsCount = "";

								var goods = "";
								var checkResult = true;
								var ladingCount = 0;
								dialog
										.find(
												'input[type="checkbox"][name="chk_list"]:checked')
										.each(
												function() {
													var _tr = $(this).parents(
															"tr");
													$("#goods-table")
															.children("tbody")
															.children("tr")
															.each(
																	function() {
																		var inCode = $(
																				this)
																				.find(
																						".goodsCode")
																				.text();
																		var addCode = $(
																				_tr)
																				.find(
																						".code")
																				.text();
																		if (inCode == addCode) {
																			checkResult = false;
																		}
																	});
													if (goodsIdList == "") {
														goodsIdList += $(_tr)
																.find(
																		"#ladingGoodsId")
																.val();
													} else {
														goodsIdList += ","
																+ $(_tr)
																		.find(
																				"#ladingGoodsId")
																		.val();
													}
													if (goodsCount == "") {
														goodsCount += $(_tr)
																.find(
																		".ladingCount")
																.val();
													} else {
														goodsCount += ","
																+ $(_tr)
																		.find(
																				".ladingCount")
																		.val();
													}

													goods += "<tr>"
													goods += "<input type='hidden' id='ladingGoodsId' value='"
															+ $(_tr)
																	.find(
																			"#ladingGoodsId")
																	.val()
															+ "' />";
													goods += "<td class='goodsCode'>"
															+ $(_tr).find(
																	".code")
																	.text()
															+ "</td>";
													goods += "<td class='clientName'>"
															+ $(_tr)
																	.find(
																			".clientName")
																	.text()
															+ "</td>";
													goods += "<td class='productName'>"
															+ $(_tr)
																	.find(
																			".productName")
																	.text()
															+ "</td>";
													goods += "<td class='goodsCuent'>"
															+ $(_tr)
																	.find(
																			".goodsCuent")
																	.text()
															+ "</td>";
													goods += "<td class='ladingCount'>"
															+ $(_tr)
																	.find(
																			".ladingCount")
																	.val()
															+ "</td>";
													goods += "<td><a href='javascript:void(0)' class='blue' onclick='Lading.removeGoodAdd(this)'> <i class='icon-remove'></i>删除</a></td>";
													goods += "</tr>"
													ladingCount = parseFloat(util
															.FloatAdd(
																	parseFloat(ladingCount),
																	parseFloat($(
																			_tr)
																			.find(
																					".ladingCount")
																			.val())));
												});
								if (checkResult == false) {
									$(this).confirm({
										content : '相同编号的货体已经添加，不能重复添加！',
										concel : true,
										callBack : function() {
										}
									});
									return;
								} else {
									if (type == 1) {
										$("#goods-table").children("tbody")
												.append(goods);
										if (ladingCount != 0) {
											var totalCount = $("#totalCount")
													.val() == "" ? 0 : $(
													"#totalCount").val();
											$("#totalCount")
													.val(
															util
																	.FloatAdd(
																			parseFloat(totalCount),
																			parseFloat(ladingCount)));
										}
										dialog.remove();
									}
									if (type == 2) {
										var $this = dialog;
										$(this)
												.confirm(
														{
															content : '确定要添加所选货体吗?',
															callBack : function() {
																config.load();
																$
																		.ajax({
																			type : "post",
																			url : config
																					.getDomain()
																					+ "/lading/addgoods",
																			data : {
																				"id" : $(
																						"#inputId")
																						.val(),
																				"code" : $(
																						"#code")
																						.val(),
																				"goodGroupId" : $(
																						".goodsGroupId")
																						.val(),
																				"goodsIdList" : goodsIdList,
																				"goodsCount" : goodsCount,
																			},
																			// data:JSON.stringify(sendGroup),
																			dataType : "json",
																			success : function(
																					data) {
																				config
																						.unload();
																				if (data.code == "0000") {
																					$(
																							"body")
																							.message(
																									{
																										type : 'success',
																										content : '添加成功'
																									});
																					dialog
																							.remove();
																					initEdit($(
																							"#inputId")
																							.val());
																				} else {
																					$(
																							"body")
																							.message(
																									{
																										type : 'error',
																										content : '添加失败'
																									});
																				}
																			}
																		});
															}
														});

									}

								}
							}

						});
	}

	function removeGoodAdd(obj) {
		var trObj = $(obj).parents("tr");
		var totalCount = $("#totalCount").val() == "" ? 0 : $("#totalCount")
				.val();
		$("#totalCount").val(
				(util.FloatSub(parseFloat(totalCount), parseFloat($(trObj)
						.find(".ladingCount").html()))));
		$(trObj).remove();
		if ($("#totalCount").val() == 0) {
			$("#totalCount").val("");
		}
	}

	function removeGood(id, obj, ladingId) {
		$(obj).confirm({
			content : '确定删除该货体？',
			callBack : function() {
				config.load();
				$.ajax({
					type : "post",
					url : config.getDomain() + "/lading/removeGood",
					data : {
						"id" : id,
						"ladingId" : ladingId
					},
					dataType : "json",
					success : function(data) {
						config.unload();
						if (data.code == "0000") {
							$("body").message({
								type : 'success',
								content : '撤回成功'
							});
							initEdit(ladingId);
							// var trObj = $(obj).parents("tr");
							// var count=$(trObj).find(".ladingCount");
							// $(".totalCount").val(parseFloat(parseFloat($(".totalCount").val())-parseFloat(count)));
							// $(trObj).remove();
						} else {
							$("body").message({
								type : 'error',
								content : '撤回失败'
							});
						}
					}
				});
			}
		});
	}

	function getGoods(id) {
		config.load();
		$
				.ajax({
					type : "get",
					url : config.getDomain() + "/lading/getgoods?ladingId="
							+ id,
					dataType : "json",
					success : function(data) {
						config.unload();
						var haveout=0;
						var goodsList = data.data;
						$("#goods-table").children("tbody").each(function() {
							$(this).children("tr").remove();
						});
						$("#goods-table").children("tbody").empty();
						if (goodsList != "") {

							for (var i = 0; i < goodsList.length; i++) {
								var goods = "<tr>";
								goods += "<input id='goodsId' type='hidden' data='"
										+ goodsList[i].id + "' />";
								goods += "<td id='goodsCode' data='"
										+ goodsList[i].code + "'>"
										+ goodsList[i].code + "</td>";
								goods += "<td id='productName' data='"
										+ goodsList[i].productName + "'>"
										+ goodsList[i].productName + "</td>";
								goods += "<td id='custName' data='"
										+ goodsList[i].clientId + "'>"
										+ goodsList[i].clientName + "</td>";
								
								goods += "<td><input type='text' maxlength='9' class='form-control input-small goodsTotal' onkeyup='config.clearNoNum(this)' value='"
									+ goodsList[i].goodsTotal
									+ "' style='display:none'/>"
									+ "<span id='goodsTotalSp'>"
									+ goodsList[i].goodsTotal
									+ "</span></td>";
								
								goods += "<td id='goodsCurrent' data='"
										+ goodsList[i].goodsCurrent + "'>"
										+ goodsList[i].goodsCurrent + "</td>";
								goods += "<td id='goodsOut' data='"
										+ goodsList[i].goodsOut + "'>"
										+ goodsList[i].goodsOut + "</td>";
								goods += "<td id='haveout' >"
										+ util.FloatSub(util.FloatSub(
												goodsList[i].goodsTotal,
												goodsList[i].goodsOut),
												goodsList[i].goodsCurrent)
										+ "</td>";
								var o=util.FloatSub(util.FloatSub(
										goodsList[i].goodsTotal,
										goodsList[i].goodsOut),
										goodsList[i].goodsCurrent);
								haveout=util.FloatAdd(o,haveout);
								goods += "<td><input type='text' maxlength='9' class='form-control input-small goodsInLading' onkeyup='config.clearNoNum(this)' value='"
										+ goodsList[i].goodsOutPass
										+ "' style='display:none'/>"
										+ "<span id='goodsInLadingSp'>"
										+ goodsList[i].goodsOutPass
										+ "</span></td>";
								goods += "<td style='vertical-align:middle;'>";
								if (parseFloat(intent.goodsDelivery) == 0
										&& parseFloat(intent.goodsOut) == 0&&parseFloat(goodsList[i].goodsWait==0)) {
									if(config.hasPermission('ADELETELADINGGOODS')){
									goods += "<a href='javascript:void(0)' class='btn btn-xs blue remove' onclick='Lading.removeGood("
											+ goodsList[i].id
											+ ",this,"
											+ data.data[0].id
											+ ")'> <span class='glyphicon glyphicon glyphicon-remove' title='撤销'></span></a>";
									}
									if(config.hasPermission('ALADINGUPDATE')){
									goods+="<a href='javascript:void(0)' class='btn btn-xs blue' onclick='GoodsLZ.openGoodsLZ("
											+ goodsList[i].id
											+ ")' data-target='#ajax' data-toggle='modal' id='goodsShow'><span class='glyphicon glyphicon glyphicon-eye-open' title='查看'></span></a><a href='javascript:void(0)' class='btn btn-xs blue edit' onclick='Storage.initEditTable(this)'> <span class='glyphicon glyphicon glyphicon-edit' title='编辑'></span></a>"
											+ "<a href='javascript:void(0)' class='btn btn-xs blue confirm' onclick='Storage.confirm(this)' style='display:none'> <span class='glyphicon glyphicon glyphicon-ok' title='确认'></span></a>"
											+ "<a href='javascript:void(0)' class='btn btn-xs blue concel' onclick='Storage.concel(this)' style='display: none;'> <span class='glyphicon glyphicon glyphicon-remove' title='取消'></span></a>";
									}
									
									}
								if (parseFloat(intent.goodsDelivery) > 0
										|| parseFloat(intent.goodsOut) > 0) {
									if(config.hasPermission('ALADINGUPDATE')){
									goods += "<a href='javascript:void(0)' class='btn btn-xs blue look' onclick='Lading.openDialog1("
											+ goodsList[i].id
											+ ")' data-target='#ajax' data-toggle='modal' id='goodsShow'><span class='glyphicon glyphicon glyphicon-eye-open' title='查看'></span></a><a href='javascript:void(0)' class='btn btn-xs blue edit' onclick='Storage.initEditTable(this)'> <span class='glyphicon glyphicon glyphicon-edit' title='编辑'></span></a>"
											+ "<a href='javascript:void(0)' class='btn btn-xs blue confirm' onclick='Storage.confirm(this)' style='display:none'> <span class='glyphicon glyphicon glyphicon-ok' title='确认'></span></a>"
											+ "<a href='javascript:void(0)' class='btn btn-xs blue concel' onclick='Storage.concel(this)' style='display: none;'> <span class='glyphicon glyphicon glyphicon-remove' title='取消'></span></a>";
								}
								}

								goods += "</td>";
								goods += "</tr>";
								$("#goods-table").children("tbody").append(
										goods);

							}
							$('#deliveryCount').val(haveout);
							
						}
					}
				});
	}

	var initEditTable = function(obj) {
		$(obj).hide();
		var _tr = $(obj).parents("tr");
		$(_tr).find(".goodsInLading").show();
		$(_tr).find("#goodsInLadingSp").hide();
		$(_tr).find(".goodsTotal").show();
		$(_tr).find("#goodsTotalSp").hide();
		$(_tr).find(".order").show();
		$(_tr).find(".orderSpan").hide();
		$(_tr).find(".edit").hide();
		$(_tr).find(".remove").hide();
		$(_tr).find(".look").hide();
		$(_tr).find(".confirm").show();
		$(_tr).find(".concel").show();
	}
	var concel = function(obj) {
		$(obj).hide();
		var _tr = $(obj).parents("tr");
		var _goodsOutPass = $(_tr).find(".goodsInLading");
		$(_goodsOutPass).hide();
		var _goodsOutPassSp = $(_tr).find("#goodsInLadingSp");
		$(_goodsOutPassSp).show();
		$(_tr).find(".orderSpan").attr("readonly", true);
		$(_tr).find(".order").hide();
		$(_tr).find(".orderSpan").show();
		$(_tr).find(".edit").show();
		$(_tr).find(".remove").show();
		$(_tr).find(".look").show();
		$(_tr).find(".confirm").hide();
		$(_tr).find(".concel").hide();
		
		var _goodsTotal = $(_tr).find(".goodsTotal");
		$(_goodsTotal).hide();
		var _goodsTotalSp = $(_tr).find("#goodsTotalSp");
		$(_goodsTotalSp).show();
		$(_goodsTotal).val($(_goodsTotalSp).text());
		
		
		$(_goodsOutPass).val($(_goodsOutPassSp).text());
	} 

	var confirm = function(obj) {
		var _tr = $(obj).parents("tr");
		var goodsOutPass = parseFloat($(_tr).find(".goodsInLading").val());
		var goodsOutPassOld = parseFloat($(_tr).find("#goodsInLadingSp").text());
		var goodsTotal = parseFloat($(_tr).find(".goodsTotal").val());
		var goodsTotalOld = parseFloat($(_tr).find("#goodsTotalSp").val());
        
		var goodsOut = parseFloat($(_tr).find("#goodsOut").text());
		var goodsDelivery= parseFloat($(_tr).find("#haveout").text());
		if (parseFloat(goodsOutPass) > parseFloat(goodsTotal) || parseFloat(goodsOutPass) < 0) {
			$(this).confirm({
				content : '放行量不能大于货体总量或小于0，请重新输入！',
				concel : true,
				callBack : function() {
				}
			});
			return;
		}
		if (parseFloat(goodsOutPass) < (parseFloat(goodsOut)+parseFloat(goodsDelivery))) {
			$(this).confirm({
				content : '放行量不能小于已操作量，请重新输入！',
				concel : true,
				callBack : function() {
				}
			});
			return;
		}
		
		if(parseFloat(goodsTotal) < (parseFloat(goodsOut)+parseFloat(goodsDelivery))){
			$(this).confirm({
				content : '货体量不能小于已操作量，请重新输入！',
				concel : true,
				callBack : function() {
				}
			});
			return;
		}
		

		if ((parseFloat(goodsOutPass) - parseFloat(goodsOutPassOld)) != 0
				|| $(_tr).find(".order").attr("data") != null||(parseFloat(goodsTotal) - parseFloat(goodsTotalOld)) != 0) {

			config.load();
			$.ajax({
				type : "post",
				url : config.getDomain() + "/lading/goodsPass",
				data : {
					"ladingId" : $("#inputId").val(),
					"goodsId" : $(_tr).find("#goodsId").attr("data"),
					"contractId" : $(_tr).find(".order").attr("data"),
					"count" : util.FloatSub(goodsOutPass, goodsOutPassOld),
					"goodsTotal":goodsTotal
				},
				dataType : "json",
				success : function(data) {
					config.unload();
					if (data.code == "0000") {
						$("body").message({
							type : 'success',
							content : '修改成功'
						});
						initEdit($("#inputId").val());
					}else if(data.code=="1003"){
						$("body").message({
							type: 'error',
							content: '数据冲突，请刷新界面！'
						});
					}else if(data.code=="1004"){
						$("body").message({
							type: 'error',
							content: '货体数量不能大于上级放行数，请重新输入！'
						});
					} else {
						$("body").message({
							type : 'error',
							content : '修改失败'
						});
					}
				}
			});

		} else {
			concel(obj);
		}

		// var _goodsOutPass = $(_tr).find(".goodsInLading");
		// var _goodsOutPassSp = $(_tr).find("#goodsInLadingSp");
		// // var _outPassChage = parseFloat($(".cargoGoodsOutPass").val()) +
		// parseFloat($(_goodsOutPass).val()) -
		// parseFloat($(_goodsOutPassSp).text());
		// // $(".cargoGoodsOutPass").val(_outPassChage);
		// $(_goodsOutPassSp).text($(_goodsOutPass).val());
		// $(_goodsOutPass).hide();
		// $(_goodsOutPassSp).show();
		//		
		// $(_tr).find(".edit").show();
		// $(_tr).find(".remove").show();
		// $(_tr).find(".look").show();
		// $(_tr).find(".confirm").hide();
		// $(_tr).find(".concel").hide();

	}

	// 编辑界面初始化
	function initEdit(id) {
		$(".backButton").unbind('click'); 
		$(".backButton").click(function() {
			var $this = $(".backButton");
			$this.confirm({
				content : '此操作将会退回该提单内所有货体，并终止提单，是否确认？',
				callBack : function() {
					config.load();
					$.ajax({
						type : "post",
						url : config.getDomain() + "/lading/backGoods",
						data : {
							"ladingId" : $("#inputId").val()
						},
						dataType : "json",
						success : function(data) {
							config.unload();
							
							if(data.code=="1003"){
								$("body").message({
									type : 'error',
									content : '提单内还有开票未提，无法退回！'
								});
							}
							else if (data.code == "0000") {
								$("body").message({
									type : 'success',
									content : '提单退回成功'
								});
								window.location.href = "#/lading";
							} else {
								$("body").message({
									type : 'error',
									content : '提单退回失败'
								});
							}
						}
					});
				}
			});

		});

		
		
		$(".againstButton").unbind('click'); 
		$(".againstButton").click(function() {
			var $this = $(".againstButton");
			var deliveryCount=0;
			deliveryCount=parseFloat($("#deliveryCount").val());
			var goodsOut=0;
			goodsOut=parseFloat($("#goodsOut").val());
			
			if(deliveryCount+goodsOut==0){
				openApproveModal("/lading/againstLading?ladingId="+$("#inputId").val(),1);
			}else{
				$("body").message({
					type : 'error',
					content : '提单已经有后续操作，无法冲销！'
				});
			}
			

		});
		
		
		$(".lossButton").unbind('click'); 
		$(".lossButton").click(function() {
			var $this = $(".lossButton");
			$this.confirm({
				content : '此操作将会扣除该提单未提的数量，并终止提单，是否确认？',
				callBack : function() {
					config.load();
					$.ajax({
						type : "post",
						url : config.getDomain() + "/lading/lossGoods",
						data : {
							"ladingId" : $("#inputId").val()
						},
						dataType : "json",
						success : function(data) {
							config.unload();
							if(data.code=="1003"){
								$("body").message({
									type : 'error',
									content : '提单内还有开票未提，无法扣损！'
								});
							}
							else if (data.code == "0000") {
								$("body").message({
									type : 'success',
									content : '提单扣损成功'
								});
								window.location.href = "#/lading";
							} else {
								$("body").message({
									type : 'error',
									content : '提单扣损失败'
								});
							}
						}
					});
				}
			});

		});

		config.load();
		$
				.ajax({
					type : "get",
					url : config.getDomain() + "/lading/list?id=" + id,
					dataType : "json",
					success : function(data) {
						config.unload();
						var intent = data.data[0];
						$(".inputId").val(id);
						$(".goodsGroupId").val(intent.goodsGroupId);
						$(".intentionId").val(intent.intentionId);
						$(".code").val(intent.code);
						$("#customsPassCode").val(intent.customsPassCode);
						if (intent.type == 1) {
							$(".type").val("转卖提单");
							$(".type").attr("data", intent.type);
							$("#tdStartTime").show();
							$("#tdEndTime").hide();
							$(".backButton").hide();
						} else {
							$(".type").val("发货提单");
							$(".type").attr("data", intent.type);
							$("#tdStartTime").hide();
							$("#tdEndTime").show();
							$(".backButton").show();
						}
						$(".custName").val(intent.clientName);
						$(".custName").attr("data", intent.clientId);
						$(".reviveCustName").val(intent.receiveClientName);
						$(".reviveCustName").attr("data",
								intent.receiveClientId);
						$(".productName").val(intent.productName);
						$(".productName").attr("data", intent.productId);
						$(".totalCount").val(intent.goodsTotal);
						$(".passCount").val(intent.goodsPass);
//						$(".deliveryCount").val(intent.goodsDelivery);
						$(".startTime").val(intent.startTime);
						$(".endTime").val(intent.endTime);
						$(".description").val(intent.description);
						$(".status").val(intent.status);
						$(".goodsOut").val(intent.goodsOut);
						$(".createTime").val(intent.createTime.substring(0,intent.createTime.length-2));
						$(".createUser").val(intent.createUserName);
						
						if(intent.isLong==1){
							$("#isLong").attr("checked",true);
						}
						
						if (intent.status == 2) {
							$(".lossButton").hide();
							$(".backButton").hide();
						}

						if (parseFloat(intent.goodsDelivery) == 0
								&& parseFloat(intent.goodsOut) == 0) {
							$("#goodsAdd").show();
						}
						if (parseFloat(intent.goodsDelivery) > 0
								|| parseFloat(intent.goodsOut) > 0) {
							$("#goodsAdd").hide();
						}
						var goodsList = data.data[0].goodsList;
						$("#goods-table").children("tbody").each(function() {
							$(this).children("tr").remove();
						});
						$("#goods-table").children("tbody").empty();
						if (goodsList != "") {
							var haveout=0;
							for (var i = 0; i < goodsList.length; i++) {
								
								var rootCode;
								
								if(goodsList[i].rootCode!=null&&goodsList[i].rootCode!=""){
									rootCode= goodsList[i].rLadingCode;
								}else if((goodsList[i].rootCode==null||goodsList[i].rootCode=="")&&goodsList[i].sourceCode!=null&&goodsList[i].sourceCode!=""){
									rootCode= data.data[0].code;
								}
								else{
									rootCode= goodsList[i].cargoCode;
								}
								
								var sourceCode;
								
								if(goodsList[i].sourceCode!=null&&goodsList[i].sourceCode!=""){
									sourceCode =data.data[0].code;
								}else{
									sourceCode ="";
								}
								
								
								var sCode;
								
								if(goodsList[i].sLadingCode!=null&&goodsList[i].sLadingCode!=""){
									sCode= goodsList[i].sLadingCode;
								}
								else{
									sCode= "";
								}
								
								var tankCode = goodsList[i].tankCode == null ? ""
										: goodsList[i].tankCode;
								var goods = "<tr>";
								goods += "<input id='goodsId' type='hidden' data='"
										+ goodsList[i].id
										+ "' /><input id='goodsTotal' type='hidden' value='"
										+ goodsList[i].goodsTotal + "'/>";
								goods += "<td style='width:100px' id='goodsCode' data='"
										+ goodsList[i].code + "'>"
										+ goodsList[i].code + "</td>";
								goods += "<td style='width:100px' id='cargoCode' data='"
										+ goodsList[i].cargoCode + "'>"
										+ goodsList[i].cargoCode + "</td>";
								
								if(goodsList[i].arrivalStartTime){
									var shipName=goodsList[i].shipName==null?"":goodsList[i].shipName;
									goods += "<td style='width:100px' id='shipInfo'>"
										+ shipName+" "+goodsList[i].arrivalStartTime.split("-")[1]+"/"+goodsList[i].arrivalStartTime.split("-")[2].split(" ")[0] + "</td>";
								}
								else{
									goods += "<td style='width:100px' id='shipInfo'>"
										"</td>";
								
								}
								goods += "<td style='width:100px' id='rootCode' data='" + rootCode
								+ "'>" + rootCode + "</td>";
								goods += "<td style='width:100px' id='sourceCode' data='"
										+ sourceCode + "'>" + sourceCode
										+ "</td>";
								goods += "<td style='width:100px' id='sourceCode' data='"
									+ sCode + "'>" + sCode
									+ "</td>";
							
								
								goods += "<td style='width:80px' id='tankCode' data='" + tankCode
										+ "'>" + tankCode + "</td>";
								goods += "<td style='width:120px' id='productName' data='"
										+ goodsList[i].productName + "'>"
										+ goodsList[i].productName + "</td>";
								goods += "<td style='width:100px' id='custName' data='"
										+ goodsList[i].clientId + "'>"
										+ goodsList[i].clientName + "</td>";
								goods += "<td style='width:100px'><input type='text'  maxlength='9' class='form-control input-small goodsTotal' onkeyup='config.clearNoNum(this)' value='"
									+ goodsList[i].goodsTotal
									+ "' style='display:none'/>"
									+ "<span id='goodsTotalSp'>"
									+ goodsList[i].goodsTotal
									+ "</span></td>";
								goods += "<td style='width:100px' id='goodsCurrent' data='"
										+ goodsList[i].goodsCurrent + "'>"
										+ goodsList[i].goodsCurrent + "</td>";
								goods += "<td style='width:100px' id='goodsOut' data='"
										+ goodsList[i].goodsOut + "'>"
										+ goodsList[i].goodsOut + "</td>";
								goods += "<td style='width:100px' id='haveout' >"
										+ (util.FloatSub(util.FloatSub(
												goodsList[i].goodsTotal,
												goodsList[i].goodsOut),
												goodsList[i].goodsCurrent))
										+ "</td>";
								var o=util.FloatSub(util.FloatSub(
										goodsList[i].goodsTotal,
										goodsList[i].goodsOut),
										goodsList[i].goodsCurrent);
								haveout=util.FloatAdd(o,haveout);
								// goods +="<td id='goodsInLading'
								// data='"+goodsList[i].goodsOutPass+"'>"+goodsList[i].goodsOutPass+"</td>";
								goods += "<td style='width:100px'><input maxlength='9' type='text' class='form-control input-small goodsInLading' onkeyup='config.clearNoNum(this)' value='"
										+ goodsList[i].goodsOutPass
										+ "' style='display:none'/>"
										+ "<span id='goodsInLadingSp'>"
										+ goodsList[i].goodsOutPass
										+ "</span></td>";

								if (goodsList[i].contractId == 0) {
									goods += "<shiro:hasPermission name='AGETCONTRACT'><td><input type='text' style='width:100px' class='form-control  orderSpan' readonly value='未关联合同' />";
									goods += "<input type='text' class='form-control  order' name='order' data-provide='typeahead'  placeholder='请输入合同' style='display:none;width:100px'/></td></shiro:hasPermission>";

								}
								if (goodsList[i].contractId != 0) {
									goods += "<shiro:hasPermission name='AGETCONTRACT'><td><input type='text' style='width:100px' class='form-control orderSpan' readonly value='"
											+ goodsList[i].contractCode
											+ "-"
											+ goodsList[i].contractTitle
											+ "' />";
									goods += "<input type='text' class='form-control input-medium order' name='order' data-provide='typeahead' value='"
											+ goodsList[i].contractCode
											+ "-"
											+ goodsList[i].contractTitle
											+ "' data='"
											+ goodsList[i].contractId
											+ "' placeholder='请输入合同' style='display:none;width:100px'/></td></shiro:hasPermission>";

								}

								goods += "<td  style='vertical-align:middle;width:200px'>";
								if (parseFloat(intent.goodsDelivery) == 0
										&& parseFloat(intent.goodsOut) == 0&&parseFloat(goodsList[i].goodsWait)==0) {
									goods += "<a href='javascript:void(0)' class='btn btn-xs blue remove' onclick='Lading.removeGood("
											+ goodsList[i].id
											+ ",this,"
											+ data.data[0].id
											+ ")'> <span class='glyphicon glyphicon glyphicon-remove' title='撤销'></span></a><a href='javascript:void(0)' class='btn btn-xs blue look' onclick='GoodsLZ.openGoodsLZ("
											+ goodsList[i].id
											+ ")' data-target='#ajax' data-toggle='modal' id='goodsShow'><span class='glyphicon glyphicon glyphicon-eye-open' title='查看'></span></a><a href='javascript:void(0)' class='btn btn-xs blue edit' onclick='Lading.initEditTable(this)'> <span class='glyphicon glyphicon glyphicon-edit' title='编辑'></span></a>"
											+ "<a href='javascript:void(0)' class='btn btn-xs blue confirm' onclick='Lading.confirm(this)' style='display:none'> <span class='glyphicon glyphicon glyphicon-ok' title='确认'></span></a>"
											+ "<a href='javascript:void(0)' class='btn btn-xs blue concel' onclick='Lading.concel(this)' style='display: none;'> <span class='glyphicon glyphicon glyphicon-remove' title='取消'></span></a>";
								}
								else {
									goods += "<a href='javascript:void(0)' class='btn btn-xs blue look' onclick='GoodsLZ.openGoodsLZ("
											+ goodsList[i].id
											+ ")' data-target='#ajax' data-toggle='modal' id='goodsShow'><span class='glyphicon glyphicon glyphicon-eye-open' title='查看'></span></a><a href='javascript:void(0)' class='btn btn-xs blue edit' onclick='Lading.initEditTable(this)'> <span class='glyphicon glyphicon glyphicon-edit' title='编辑'></span></a>"
											+ "<a href='javascript:void(0)' class='btn btn-xs blue confirm' onclick='Lading.confirm(this)' style='display:none'> <span class='glyphicon glyphicon glyphicon-ok' title='确认'></span></a>"
											+ "<a href='javascript:void(0)' class='btn btn-xs blue concel' onclick='Lading.concel(this)' style='display: none;'> <span class='glyphicon glyphicon glyphicon-remove' title='取消'></span></a>";
								}
								goods += "</td>";
								goods += "</tr>";
								$("#goods-table").children("tbody").append(
										goods);

								

							}
							
							// 合同
							config.load();
							$
									.ajax({
										async : false,
										type : "get",
										url : config.getDomain()
												+ "/order/list?status=2&page=0&pagesize=0",
										dataType : "json",
										success : function(data) {
											config.unload();
											$("#goods-table")
													.children("tbody")
													.children("tr")
													.each(
															function() {
																var _this = this;
																var _in = $(
																		_this)
																		.find(
																				".order");
																$(_in)
																		.typeahead(
																				{
																					source : function(
																							query,
																							process) {
																						var results = _
																								.map(
																										data.data,
																										function(
																												product) {
																											return product.code
																													+ "-"
																													+ product.title;
																										});
																						process(results);
																					},
																					updater : function(
																							item) {
																						var client = _
																								.find(
																										data.data,
																										function(
																												p) {
																											return (p.code
																													+ "-" + p.title) == item;
																										});
																						return item;
																					},
																					noselect : function(
																							query) {
																						var client = _
																								.find(
																										data.data,
																										function(
																												p) {
																											return (p.code
																													+ "-" + p.title) == query;
																										});
																						if (client == null) {
																							$(
																									_in)
																									.removeAttr(
																											"data");
																							$(
																									_in)
																									.val(
																											"");
																							// $(_code).val("");
																						} else {
																							$(
																									_in)
																									.attr(
																											"data",
																											client.id);
																						}
																					}
																				});
															});

										}
									});
							
							$("#deliveryCount").val(haveout);
						}
					}
				});

		$(".form-horizontal").find("select").each(function() {
			if ($(this).attr("data-required") == 1) {
				if ($(this).val() == null || $(this).val() == "-1") {
					$(this).pulsate({
						color : "#bf1c56",
						repeat : false
					});
					isOk = false;
					return;
				}
			}
		});
		var goodsIdList = "";
		var goodsCount = "";
		$(".saveButton").unbind('click'); 
		$(".saveButton")
				.click(
						function() {
							var isOk = false;
							if (config.validateForm($(".lading-update-form"))) {
								isOk = true;
							}
							$("#goods-table")
									.children("tbody")
									.children("tr")
									.each(
											function() {
												if (goodsIdList == "") {
													goodsIdList += $(this)
															.find("#goodsId")
															.attr("data");
												} else {
													goodsIdList += ","
															+ $(this)
																	.find(
																			"#goodsId")
																	.attr(
																			"data");
												}
												if (goodsCount == "") {
													goodsCount += $(this).find(
															"#goodsInLading")
															.attr("data");
												} else {
													goodsCount += ","
															+ $(this)
																	.find(
																			"#goodsInLading")
																	.attr(
																			"data");
												}
											});
							if (parseFloat($("#passCount").val()) < (util
									.FloatAdd(parseFloat($("#deliveryCount")
											.val()), parseFloat($("#goodsOut")
											.val())))
									|| parseFloat($("#passCount").val()) > parseFloat($(
											"#totalCount").val())) {
								$(this).confirm({
									content : '放行数量错误！请重新输入！',
									concel : true,
									callBack : function() {
									}
								});
								return;
							}
							if (isOk) {
								
								var isLong=0;
								if($("#isLong").is(':checked')){
									isLong=1;
								}
								
								config.load();
								var status = 0;
								if (parseFloat($("#passCount").val()) > 0&&($("#startTime")
										.val()||$("#endTime").val())||isLong==1) {
									status = 1;
								}
								$.ajax({
											type : "post",
											url : config.getDomain()
													+ "/lading/update",
											data : {
												"id" : $("#inputId").val(),
												"code" : $("#code").val(),
												"customsPassCode" : $(
														"#customsPassCode")
														.val(),
												"clientId" : $("#custName")
														.attr("data"),
												"type" : $("#type")
														.attr("data"),
												"goodsTotal" : $("#totalCount")
														.val(),
												"goodsPass" : $("#passCount")
														.val(),
												"goodsDelivery" : $(
														"#deliveryCount").val(),
												"goodsOut" : $("#goodsOut")
														.val(),
												"stTime" : $("#startTime")
														.val(),
												"edTime" : $("#endTime").val(),
												"status" : status,
												"description" : $(
														"#description").val(),
												"productId" : $("#productName")
														.attr("data"),
												"receiveClientId" : $(
														"#reviveCustName")
														.attr("data"),
												"goodsIdList" : goodsIdList,
												"goodGroupId" : $(
														".goodsGroupId").val(),
												"goodsCount" : goodsCount,
												"isLong":isLong
											},
											// data:JSON.stringify(sendGroup),
											dataType : "json",
											success : function(data) {
												config.unload();
												if (data.code == "0000") {
													$("body").message({
														type : 'success',
														content : '提单修改成功'
													});
													initEdit($("#inputId")
															.val());
													// window.location.href =
													// "#/ladingEdit?id="+$("#inputId").val();
												}else if(data.code=="1004"){
													$("body").message({
														type : 'error',
														content : '提单有效期不能晚于上级提单有效期'
													});
												} else {
													$("body").message({
														type : 'error',
														content : '提单修改失败'
													});
												}
											}
										});
							}
						});

	}

	function checkNum(obj) {
		if (obj == "") {
			return 0;
		} else {
			return obj;
		}
	}

	// 初始化select标签
	var initAddSelection = function() {
		config.load();
		// $.ajax({
		// async:false,
		// type : "get",
		// url : config.getDomain()+"/product/select",
		// dataType : "json",
		// success : function(data) {
		// var htm="<select class='form-control select2me productName'
		// data-placeholder='选择货品...' id='productName'
		// data-live-search='true'>";
		// for(var i=0;i<data.data.length;i++){
		// htm+="<option
		// value="+data.data[i].id+">"+data.data[i].name+"</option>";
		// }
		// htm+="</select>";
		// $("#select-product").append(htm);
		// }
		// });
var lcount=0;
		$.ajax({
			async : false,
			type : "get",
			url : config.getDomain() + "/product/select",
			dataType : "json",
			success : function(data) {
				lcount+=1;
				if(lcount==loadCount){
					config.unload();
					lcount=0;
				}
				$('#productName').typeahead({
					source : function(query, process) {
						var results = _.map(data.data, function(product) {
							return product.name;
						});
						process(results);
					},
					// 移除控件时调用的方法
					noselect : function(query) {
						var client = _.find(data.data, function(p) {
							return p.name == query;
						});
						// 匹配不到就去掉id，让内容变空，否则给id
						if (client == null) {
							$('#productName').removeAttr("data");
							$('#productName').val("");
						} else {
							$('#productName').attr("data", client.id)
						}
					}
				});
			}
		});

		
		
		
		$.ajax({
			type : "get",
			url : config.getDomain() + "/client/select",
			dataType : "json",
			
			success : function(data) {
				lcount+=1;
				if(lcount==loadCount){
					config.unload();
					lcount=0;
				}
				$('#clientId').typeahead({
					source : function(query, process) {
						var results = _.map(data.data, function(product) {
							return product.name+"["+product.code+"]";
						});
						process(results);
					},
					updater : function(query) {
						var client = _.find(data.data, function(p) {
							 return p.name+"["+p.code+"]" == query;
						});
						if(client!=null){
	  						$('#clientId').attr("data",client.id);
	  					}
						return query;
					},
					noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.name+"["+p.code+"]" == query;
	                    });
	  					if(client==null){
	  						$('#clientId').removeAttr("data");
	  						$('#clientId').val("");
	  					}else{
	  						$('#clientId').attr("data",client.id)
	  					}
	  				}
				});
			}
		});
		
		
		$.ajax({
			type : "get",
			url : config.getDomain() + "/client/select",
			dataType : "json",
			
			success : function(data) {
				lcount+=1;
				if(lcount==loadCount){
					config.unload();
					lcount=0;
				}
				$('#receiveClientId').typeahead({
					source : function(query, process) {
						var results = _.map(data.data, function(product) {
							return product.name+"["+product.code+"]";
						});
						process(results);
					},
					updater : function(query) {
						var client = _.find(data.data, function(p) {
							 return p.name+"["+p.code+"]" == query;
						});
						if(client!=null){
	  						$('#receiveClientId').attr("data",client.id);
	  					}
						return query;
					},
					noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.name+"["+p.code+"]" == query;
	                    });
	  					if(client==null){
	  						$('#receiveClientId').removeAttr("data");
	  						$('#receiveClientId').val("");
	  					}else{
	  						$('#receiveClientId').attr("data",client.id)
	  					}
	  				}
				});
			}
		});
//		util.urlHandleTypeaheadAllData("/client/select",$('#clientId'),function(item){return item.name+"["+item.code+"]";});
//		
//		util.urlHandleTypeaheadAllData("/client/select",$('#receiveClientId'),function(item){return item.name+"["+item.code+"]";});
		
//		$('#clientId').blur(function(){
//			
//			if($('#clientId').attr("data")){
//				console.log(111);
//				$('.clientGoods').show();
//			}
//			
//			
//		});
		
		
//		$.ajax({
//			async : false,
//			type : "get",
//			url : config.getDomain() + "/client/select",
//			dataType : "json",
//			success : function(data) {
//				config.unload();
//				$('#clientId').typeahead({
//					source : function(query, process) {
//						var results = _.map(data.data, function(product) {
//							return product.name;
//						});
//						process(results);
//					},
//					noselect : function(query) {
//						var client = _.find(data.data, function(p) {
//							return p.name == query;
//						});
//						if (client == null) {
//							$('#clientId').removeAttr("data");
//							$('#clientId').val("");
//						} else {
//							$('#clientId').attr("data", client.id)
//						}
//					}
//				});
//
//				$('#receiveClientId').typeahead({
//					source : function(query, process) {
//						var results = _.map(data.data, function(product) {
//							return product.name;
//						});
//						process(results);
//					},
//					noselect : function(query) {
//						var client = _.find(data.data, function(p) {
//							return p.name == query;
//						});
//						if (client == null) {
//							$('#receiveClientId').removeAttr("data");
//							$('#receiveClientId').val("");
//						} else {
//							$('#receiveClientId').attr("data", client.id)
//						}
//					}
//				});
//
//			}
//		});

		// $.ajax({
		// type : "get",
		// url : config.getDomain()+"/client/select",
		// dataType : "json",
		// success : function(data) {
		// var htm="<select class='form-control select2me custName'
		// data-placeholder='选择客户...' id='custName' data-live-search='true'>";
		// for(var i=0;i<data.data.length;i++){
		// htm+="<option
		// value="+data.data[i].id+">"+data.data[i].name+"</option>";
		// }
		// htm+="</select>";
		//				
		// var htm1="<select class='form-control select2me reviveCustName'
		// data-placeholder='选择客户...' id='reviveCustName'
		// data-live-search='true'>";
		// for(var i=0;i<data.data.length;i++){
		// htm1+="<option
		// value="+data.data[i].id+">"+data.data[i].name+"</option>";
		// }
		// htm1+="</select>";
		//				
		// $("#select-client").append(htm);
		// $("#select-client1").append(htm1);
		// }
		// });

		
		$("#goodsAdd").unbind('click'); 
		$("#goodsAdd").click(function() {
			$(".button-add-goods").attr("data-dismiss", "");
		});
		$("#goodsAdd").hide();
		$("#code,#clientId,#productName,#type,#reviveCustName").blur(
				function() {
					if (checkValue() == true) {
						$("#goodsAdd").show();
					} else {
						$("#goodsAdd").hide();
					}
				});
		$(".saveButton").unbind('click'); 
		$(".saveButton")
				.click(
						function() {
							var isOk = false;
							if (config.validateForm($(".loading-form"))) {
								isOk = true;
							}

							$(".form-horizontal")
									.find("select")
									.each(
											function() {
												if ($(this).attr(
														"data-required") == 1) {
													if ($(this).val() == null
															|| $(this).val() == "-1") {
														$(this).pulsate({
															color : "#bf1c56",
															repeat : false
														});
														isOk = false;
														return;
													}
												}
											});
							var goodsIdList = "";
							var goodsCount = "";
							$("#goods-table")
									.children("tbody")
									.children("tr")
									.each(
											function() {
												if (goodsIdList == "") {
													goodsIdList += $(this)
															.find(
																	"#ladingGoodsId")
															.val();
												} else {
													goodsIdList += ","
															+ $(this)
																	.find(
																			"#ladingGoodsId")
																	.val();
												}
												if (goodsCount == "") {
													goodsCount += $(this).find(
															".ladingCount")
															.text();
												} else {
													goodsCount += ","
															+ $(this)
																	.find(
																			".ladingCount")
																	.text();
												}
											});

							if (isOk) {
								
								var isLong=0;
								if($("#isLong").is(':checked')){
									isLong=1;
								}
								if($("#type").val()==2&&!$("#endTime").val()&&isLong!=1){
									$(
									"body")
									.message(
											{
												type : 'error',
												content : '请填写提单有效期！'
											});
									return;
								}
								config.load();
								$
										.ajax({
											type : "post",
											url : config.getDomain()
													+ "/lading/save",
											data : {
												"code" : $("#code").val(),
												"customsPassCode" : $(
														"#customsPassCode")
														.val(),
												"clientId" : $("#clientId")
														.attr("data"),
												"type" : $("#type").val(),
												"goodsTotal" : $("#totalCount")
														.val(),
												"goodsPass" : 0,
												"goodsDelivery" : 0,
												"goodsOut" : 0,
												"stTime" : $("#startTime")
														.val(),
												"edTime" : $("#endTime").val(),
												"status" : 1,
												"description" : $(
														"#description").val(),
												"productId" : $("#productName")
														.attr("data"),
												"receiveClientId" : $(
														"#receiveClientId")
														.attr("data"),
												"goodsIdList" : goodsIdList,
												"goodsCount" : goodsCount,
												"buyClientId" : $(
														"#receiveClientId")
														.attr("data"),
												"isLong":isLong
											},
											// data:JSON.stringify(sendGroup),
											dataType : "json",
											success : function(data) {
												
												if(data.code=="1003"){
													$(
													"body")
													.message(
															{
																type : 'error',
																content : '提单号有重复，请重新填写'
															});
													
												}else if (data.code=="1004"){
													$(
													"body")
													.message(
															{
																type : 'error',
																content : '提单有效期不能晚于上级提单有效期'
															});
												}else
												if (data.code == "0000") {

													$
															.ajax({
																type : "post",
																url : config
																		.getDomain()
																		+ "/lading/upgoods",
																data : {
																	"id" : data.data[0].id,
																	"goodsIds" : data.data[0].goodsIds,
																	"buyClientId" : data.data[0].buyClientId,
																	"ladingStatus" : data.data[0].ladingStatus,
																	"goodsGroupId" : data.data[0].goodsGroupId
																},
																dataType : "json",
																success : function(
																		data1) {
																	config
																			.unload();
																	if (data1.code == "0000") {
																		$(
																				"body")
																				.message(
																						{
																							type : 'success',
																							content : '提单添加成功'
																						});
																		// window.location.href
																		// =
																		// "#/lading";
																		window.location.href = "#/ladingEdit?id="
																				+ data.map.id;
																	}else if(data1.code=="1003"){
																		$(
																		"body")
																		.message(
																				{
																					type : 'error',
																					content : '数据错误，请刷新！'
																				});
																	} else {
																		$(
																				"body")
																				.message(
																						{
																							type : 'error',
																							content : '提单添加失败'
																						});
																	}
																}
															});
												}  else {
													$("body").message({
														type : 'error',
														content : '提单添加失败'
													});
												}
												config
												.unload();
												
											}
										});
							}
						});
	}

	function checkValue() {
		if ($("#code").val() == "") {
			return false;
		}
		if ($("#productName").val() == "") {
			return false;
		}
		if ($("#clientId").val() == "") {
			return false;
		}
		if ($("#type").val() == -1) {
			return false;
		}
		if ($("#reviveCustName").val() == "") {
			return false;
		}
		return true;
	}

	var deleteItem = function(id) {
		var data = $('[data-role="ladingGrid"]').getGrid().selectedRowsIndex();
		var $this = $('[data-role="ladingGrid"]');

		$this.confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
				// deleteIntent($('[data-role="contractGrid"]').getGrid().selectedRows(),
				// $this);

				var data = "";
				data += ("id=" + id);

				$.post(config.getDomain() + '/lading/cancel', data).done(
						function(data) {
							if (data.code == "0000") {
								$('[data-role="intentGrid"]').message({
									type : 'success',
									content : '删除成功'
								});
								$('[data-role="ladingGrid"]').grid('refresh');
							} else {
								$('body').message({
									type : 'error',
									content : data.msg
								});
							}
						}).fail(function(data) {
					$('[data-role="ladingGrid"]').message({
						type : 'error',
						content : '删除失败'
					});
				});

			}
		});
	}

	var deleteLading = function(intents, grid) {
		dataGrid = grid;
		var cando = true;
		var data = "";
		$.each(intents, function(i, role) {
			if (role.goodsDelivery > 0) {
				$(this).confirm({
					content : '存在无法删除的提单！！请重新选择！',
					concel : true,
					callBack : function() {
					}
				});
				cando = false;
				return;
			}
			data += ("id=" + role.id + ",");
		});
		data = data.substring(0, data.length - 1);
		if (cando) {
			$.post(config.getDomain() + '/lading/cancel', data).done(
					function(data) {
						if (data.code == "0000") {
							$('body').message({
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
				$('body').message({
					type : 'error',
					content : '删除失败'
				});
			});
		}
	};

	var handleRecords = function() {

		var lcount=0;
		config.load();
		$.ajax({
			type : "get",
			url : config.getDomain() + "/client/select",
			dataType : "json",
			
			success : function(data) {
				
				$('#clientId0').typeahead({
					source : function(query, process) {
						var results = _.map(data.data, function(product) {
							return product.name+"["+product.code+"]";
						});
						process(results);
					},
					
					
					updater : function(item) {
						var client = _.find(data.data, function(p) {
							return p.name+"["+p.code+"]" == item;
						});
						if(client!=null){
	  						$('#clientId0').attr("data",client.id);
	  					}
						return item;
					},
					noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.name+"["+p.code+"]" == query;
	                    });
	  					if(client==null){
	  						$('#clientId0').removeAttr("data");
	  						$('#clientId0').val("");
	  					}else{
	  						$('#clientId0').attr("data",client.id)
	  					}
	  				}
					
					
				});
				lcount+=1;
				if(lcount==loadCount){
					config.unload();
					lcount=0;
				}
			}
		});
		
		
		$.ajax({
			type : "get",
			url : config.getDomain() + "/client/select",
			dataType : "json",
			
			success : function(data) {
				lcount+=1;
				if(lcount==loadCount){
					config.unload();
					lcount=0;
				}
				$('#ladingClientId').typeahead({
					source : function(query, process) {
						var results = _.map(data.data, function(product) {
							return product.name+"["+product.code+"]";
						});
						process(results);
					},
					updater : function(item) {
						var client = _.find(data.data, function(p) {
							return p.name+"["+p.code+"]" == item;
						});
						if(client!=null){
	  						$('#ladingClientId').attr("data",client.id);
	  					}
						return item;
					},
					noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.name+"["+p.code+"]" == query;
	                    });
	  					if(client==null){
	  						$('#ladingClientId').removeAttr("data");
	  						$('#ladingClientId').val("");
	  					}else{
	  						$('#ladingClientId').attr("data",client.id)
	  					}
	  				}
				});
			}
		});
		
		$.ajax({
			type : "get",
			url : config.getDomain() + "/product/select",
			dataType : "json",
			success : function(data) {
				lcount+=1;
				if(lcount==loadCount){
					config.unload();
					lcount=0;
				}
				$('#productId').typeahead({
					source : function(query, process) {
						var results = _.map(data.data, function(product) {
							return product.name;
						});
						process(results);
					},
					updater : function(item) {
						var client = _.find(data.data, function(p) {
							return p.name == item;
						});
						if(client!=null){
	  						$('#productId').attr("data",client.id);
	  					}
						return item;
					},
					noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.name == query;
	                    });
	  					if(client==null){
	  						$('#productId').removeAttr("data");
	  						$('#productId').val("");
	  					}else{
	  						$('#productId').attr("data",client.id)
	  					}
	  				}
				});
			}
		});
		

		var columns = [
				{
					title : "提单号",
					name : "code",
					width : 100,
					render : function(item, name, index) {
//						return "<a href='#/ladingEdit?id=  " + item.id + "&type=1'>"
//								+ item.code + "</a>";
						return "<a href='javascript:void(0)'  onclick='LadingTurn.init("+ item.id+ ",2)'>"+ item.code + "</a>";
						
					}
				},
				{
					title : "发货单位",
					width : 100,
					name : "clientName"
				},
				{
					title : "接收单位",
					width : 100,
					name : "receiveClientName"
				},
				{
					title : "类型",
					width : 100,
					name : "type",
					render : function(item, name, index) {
						var flag = item.type;
						var status = "";
						if (flag == 1) {
							status = "转卖提单";
						} else if (flag == 2) {
							status = "发货提单";
						}
						return status;
					}
				},
				{
					title : "货品名称",
					width : 100,
					name : "productName"
				},
				{
					title : "提单数量(吨)",
					width : 100,
					name : "goodsTotal"
				},
				{
					title : "可提数量(吨)",
					width : 100,
					name : "goodsCan",
					render : function(item, name, index) {
						var goodsPass = item.goodsPass == "" ? 0
								: item.goodsPass;
						
						var goodsOut = item.goodsOut == "" ? 0 : item.goodsOut;
						var goodsDelivery = item.goodsDelivery == "" ? 0
								: item.goodsDelivery;
						var goodsWait = item.goodsWait == "" ? 0
								: item.goodsWait;
						var goodsLoss = item.goodsLoss == "" ? 0
								: item.goodsLoss;
						return (util.FloatSub(util
								.FloatSub(goodsPass, goodsOut), util.FloatAdd(util.FloatAdd(goodsDelivery,goodsWait),goodsLoss)));
					}
				},
				{
					title : "仓储费起计日",
					width : 100,
					name : "startTime",
					render : function(item, name, index) {
						var flag = item.type;
						var status = "";
						if (flag == 1) {
							status = item.startTime;
						} else if (flag == 2) {
							status = "";
						}
						return status;
					}
				},
				{
					title : "提单有效期",
					width : 100,
					name : "endTime",
					render : function(item, name, index) {
						

						var flag = item.type;
						var status = "";
						if (flag == 1) {
							status = "";
						} else if (flag == 2) {
							if(item.isLong==1){
								status = "长期";
						}else{
							status = item.endTime;
						}
						}
						return status;
					
						
						
						
					}
				},
				{
					title : "状态",
					width : 100,
					name : "status",
					render : function(item, name, index) {
						switch (item.status) {
						case 0:
							return "<span style='color:orange'>锁定</span>";
							break;
						case 1:
							

							var goodsPass = item.goodsPass == "" ? 0
									: item.goodsPass;
							
							var goodsOut = item.goodsOut == "" ? 0 : item.goodsOut;
							var goodsDelivery = item.goodsDelivery == "" ? 0
									: item.goodsDelivery;
							var goodsWait = item.goodsWait == "" ? 0
									: item.goodsWait;
							var goodsLoss = item.goodsLoss == "" ? 0
									: item.goodsLoss;
						
							
							var goodsCurrent=util.FloatSub(util.FloatSub(item.goodsTotal,goodsOut),util.FloatAdd(goodsDelivery,goodsLoss));
							
							if(util.FloatSub(item.goodsTotal,goodsPass)==0){
								return "<span style='color:green'>激活</span>";
							}else if(goodsPass==0){
								return "<span style='color:orange'>锁定</span>";
							}else{
								return "<span style='color:green'>激活(部分)</span>";
							}
							
							
//							if(goodsCurrent==0){
//								return "<span style='color:red'>终止</span>";
//							}else if(item.type==2&&item.isLong!=1&&new Date(item.mEndTime).getTime()<new Date().getTime()&&item.endTime){
//								return "<span style='color:red'>过期</span>";
//							}else{
//								
//								return "<span style='color:green'>激活</span>";
//							}
							
							break;
						case 2:
//							return "<span style='color:green'>激活(<span style='color:red'>中止</span>)</span>";
							return "<span style='color:green'>激活</span>";
							break;
						}
					}
				},{
					title : "通知单编号",
					width : 100,
					name : "No"
				},
				{
					title : "创建人",
					width : 100,
					name : "createUserName"
				},
				{
					title : "创建时间",
					width : 100,
					name : "createTime",
					render : function(item, name, index) {
						return item.createTime.substring(0,item.createTime.length-2);
					}
				},
				{
					title : "操作",
					width : 100,
					name : "id",
					render : function(item, name, index) {
						var s = "<div class='input-group-btn'>";
						if (item.status == 2) {
							if(config.hasPermission('ALADINGUPDATE')){
								s += "<shiro:hasPermission name='ALADINGUPDATE'><a href='#/ladingEdit?id="
										+ item.id
										+ "' style='margin:0 2px;font-size: 9px;' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a></shiro:hasPermission>";
								}
							return s;
						}
						if (item.goodsDelivery > 0) {
							if(config.hasPermission('ALADINGUPDATE')){
							s += "<shiro:hasPermission name='ALADINGUPDATE'><a href='#/ladingEdit?id="
									+ item.id
									+ "' style='margin:0 2px;font-size: 9px;' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a></shiro:hasPermission>";
							}
							// "<shiro:hasPermission name='ALADINGUPDATE'><a
							// href='javascript:void(0)' class='btn btn-xs
							// blue'><span class='glyphicon glyphicon
							// glyphicon-file'
							// title='审单'></span></a></shiro:hasPermission>";
						} else {
							if(config.hasPermission('ALADINGUPDATE')){
							s += "<shiro:hasPermission name='ALADINGUPDATE'><a href='#/ladingEdit?id="
									+ item.id
									+ "' style='margin:0 2px;font-size: 9px;' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a></shiro:hasPermission>";
							}
							// "<shiro:hasPermission name='ALADINGUPDATE'><a
							// href='javascript:void(0)' class='btn btn-xs
							// blue'><span class='glyphicon glyphicon
							// glyphicon-file'
							// title='审单'></span></a></shiro:hasPermission>";
							// "<a href='javascript:void(0)'
							// onclick='Lading.deleteItem("+item.id+")'
							// class='btn btn-xs blue'><span class='glyphicon
							// glyphicon glyphicon-remove'
							// title='撤销'></span></a>";
						}
						if(config.hasPermission('ALADINGFILE')){
						if (item.fileUrl) {
							s += "<a href='javascript:void(0)' onclick='Lading.openFileModal("
									+ item.id
									+ ")' class='btn btn-xs blue'><span class='fa fa-arrow-up   ' title='上传提单'></span></a><a href='"
									+ getRootPath()
									+ item.fileUrl
									+ "' onclick='' style='margin:0 2px;font-size: 9px;' class='btn btn-xs blue'><span class='fa fa-arrow-down   ' title='查看文件'></span></a>";

						} else {
							s += "<a href='javascript:void(0)' onclick='Lading.openFileModal("
									+ item.id
									+ ")' class='btn btn-xs blue'><span class='fa fa-arrow-up   ' style='margin:0 2px;font-size: 9px;' title='上传提单'></span></a>";

						}
						}
						if(item.type==1){
							
							if(config.hasPermission('APRINTLADING')){
								s+="<a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='Lading.print("+item.id+")' class='btn btn-xs blue'><span class='fa fa-file-text-o   ' title='打印调拨通知单'></span></a>";
							}
						}
						s+="</div>";
						return s;

					}
				} ];

		// 清空grid缓存
		if ($('[data-role="ladingGrid"]').getGrid() != null) {
			$('[data-role="ladingGrid"]').getGrid().destory();
		}
		/* 解决id冲突的问题 */
		$('[data-role="ladingGrid"]').grid({
			identity : 'id',
			columns : columns,
			gridName:'tidanguanli',
			stateSave:true,
			callback : function() {
				$('[data-role="ladingGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
				$(".grid-table-body tr").each(function() {
					$(this).find("td:last").attr("nowrap", "");
				});
			},
			isShowIndexCol : false,
			isShowPages : true,
			url : config.getDomain() + "/lading/list"
		});

		// 初始化按钮操作
		$(".btn-add").unbind('click'); 
		$(".btn-add").click(function() {
			window.location.href = "#/ladingAdd";
		});

		
		// 初始化按钮操作
		$(".btn-searchguoqi").unbind('click'); 
		$(".btn-searchguoqi").click(function() {
//			$("#status")
//			$("#status").val("4").trigger("change"); 
			$("#status").val("4");
			guoqi=1;
			Lading.search();
			guoqi=0;
			Lading.reset();
		});
		
		$(".btn-remove").unbind('click'); 
		$(".btn-remove").click(
				function() {
					var data = $('[data-role="ladingGrid"]').getGrid()
							.selectedRowsIndex();
					var $this = $('[data-role="ladingGrid"]');
					if (data.length == 0) {
						$('body').message({
							type : 'warning',
							content : '请选择要撤销的记录'
						});
						return;
					}
					$this.confirm({
						content : '确定要撤销所选记录吗?',
						callBack : function() {
							deleteLading($('[data-role="ladingGrid"]')
									.getGrid().selectedRows(), $this);
						}
					});
				});

		
		$(".btn-modify").unbind('click'); 
		$(".btn-modify").click(
				function() {
					// window.location.href = "#/intentGet?id="+data.id;
					var indexs = $('[data-role="ladingGrid"]').getGrid()
							.selectedRows();
					var $this = $(this);
					if (indexs.length == 0) {
						$("body").message({
							type : 'warning',
							content : '请选择要修改的记录'
						});
						return;
					}
					if (indexs[0].status == 2) {
						$("body").message({
							type : 'warning',
							content : '提单已经终止，无法修改'
						});
						return;
					}
					window.location.href = "#/ladingEdit?id=" + indexs[0].id;
				});

		$(".btn-search").unbind('click'); 
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});

	};

	
	
	function print(id){
		$.get(config.getResource() + "/pages/outbound/lading/ladingPrint.jsp").done(function(data) {
			dialog = $(data);
			initprint(dialog, id);
			dialog.modal({
				keyboard : true
			});
		});
	}
	
	function initprint(dialog,id){
		
		config.load();
		
		$.ajax({
			type:'get',
			url:config.getDomain()+"/auth/user/get?id="+config.findUserId(),
			dataType:'json',
			success:function(data){
				if(data.code=='0000'){
					
					dialog.find('#fh').text(data.data[0].name);
		
		$
		.ajax({
			type : "get",
			url : config.getDomain() + "/lading/list?id=" + id,
			dataType : "json",
			success : function(data) {
				config.unload();
				var intent = data.data[0];
				dialog.find("#code").text(intent.code);
				if (intent.type == 1) {
					dialog.find("#type").text("货权转让");
					dialog.find("#time").text(intent.startTime);
				} else {
					dialog.find("#type").text("发货");
					dialog.find("#time").text(intent.endTime);
				}
				dialog.find("#clientCode").text(intent.clientCode);
				dialog.find("#clientName").text(intent.clientName);
				dialog.find("#receiveClientName").text(intent.receiveClientName);
				dialog.find("#productName").text(intent.productName);
				dialog.find("#goodsTotal").text(intent.goodsTotal);
				dialog.find("#passCount").text(intent.goodsPass);
				dialog.find("#description").text(intent.description);
				dialog.find("#createTime").text(intent.createTime.substring(0,intent.createTime.length-10));
				dialog.find("#createUser").text(intent.createUserName);
				
				dialog.find("#now").text(new Date().Format("yyyy-MM-dd"));
				if(intent.No){
					dialog.find("#No").text(intent.No);
				}
//				else{
//					dialog.find("#No").text(util.getNo((parseFloat(intent.NoCount.split('.')[1])+1)));
//				}
//				
//				
//				var No=dialog.find("#No")[0].innerHTML;
//				
				
				dialog.find(".btn-print").unbind('click'); 
				dialog.find(".btn-print").click(function() {
//					
//					$.ajax({
//						type : "get",
//						url : config.getDomain()+"/lading/updateLadingNo?id="+intent.id+"&No="+No,
//						dataType : "json",
//						success : function(data) {
//							
//						}
//					});
					dialog.find('.modal-body').jqprint();
				});
				
				
			}
		});
		
				}
			}
			});
		
	}
	
	
	function getClientGoods(){
		
		if($("#clientId").attr("data")){
			$.get(config.getResource() + "/pages/outbound/lading/clientGoods.jsp").done(function(data) {
				dialog = $(data);
				
				dialog.find(".btn-print").click(function() {
					dialog.find('.modal-body').jqprint();
				});
				
				var columns = [
								{
									title : "货体号",
									name : "code",
									width : 90
								},
								{
									title : "调号",
									width : 60,
									name : "clientName",
									render : function(item, name, index) {
										var ladingCode=item.ladingCode==null?"":item.ladingCode;
										return ladingCode
									}
								},
								{
									title : "原号",
									width : 60,
									name : "receiveClientName",
									render : function(item, name, index) {
										var rLadingCode="";
										if(item.rootGoodsId==null){
											rLadingCode=item.cargoCode;
										}else if(item.rootGoodsId==0){
											rLadingCode=item.ladingCode==null?"":item.ladingCode;
										}else{
											rLadingCode=item.rLadingCode==null?"":item.rLadingCode;

									}
										return rLadingCode;
									}
								},{
									title : "上级货主",
									width : 100,
									name : "receiveClientName",
									render : function(item, name, index) {
										var sourceClientName=item.goodsLadingClientName==null?"":item.goodsLadingClientName;
										return sourceClientName;
									}
								},{
									title : "货主",
									width : 100,
									name : "clientName",
										render : function(item, name, index) {
											var sourceClientName=item.ladingClientName==null?item.clientName:item.ladingClientName;
											return sourceClientName;
										}
								},
								{
									title : "货批号",
									width : 70,
									name : "cargoCode",
									render : function(item, name, index) {
										
										if(item.cargoCode!=null){
											return "<a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("
											+ item.id
											+ ")' id='goodsShow'>"+item.cargoCode+"</a>";
											
										}else{
											return "";
										}
										

									}
								},{
									title : "罐号",
									width : 70,
									name : "tankCode"
								},{
									title : "货批原号",
									width : 90,
									name : "receiveClientName",
									render : function(item, name, index) {
										var originCode=item.originGoodsCode==null?(item.sourceCode==null?item.code:item.sourceCode):item.originGoodsCode;

										return originCode;
									}
								},{
									title : "货批船信",
									width : 100,
									name : "receiveClientName",
									render : function(item, name, index) {
										if(item.arrivalStartTime){
										var shipName=item.shipName==null? "":item.shipName;
										return shipName+" "+item.arrivalStartTime.split("-")[1]+"/"+item.arrivalStartTime.split("-")[2].split(" ")[0];
										
									}else{
										return "";
									}
									}
								},
								
								{
									title : "提单类型",
									width : 50,
									name : "ladingType",
									render : function(item, name, index) {
										var flag = item.ladingType;
										var status = "";
										if (flag == 1) {
											status = "转卖";
										} else if (flag == 2) {
											status = "发货";
										}
										return status;
									}
								},{
									title : "调拨日期",
									width : 40,
									name : "ladingCreateTime",
									render : function(item, name, index) {
										var ladingTime="";
										if(item.ladingCreateTime){
											 ladingTime=item.ladingCreateTime.substring(0,item.ladingCreateTime.length-10);
											
										}
											
											
											return ladingTime;
										}
								},{
									title : "起计/有效期",
									width : 50,
									name : "type",
									render : function(item, name, index) {
										
									var ladingTime="";
										if(item.ladingStartTime&&item.ladingType==1){
									
										ladingTime=item.ladingStartTime.substring(0,item.ladingStartTime.length-10);
									}else if(item.ladingEndTime){
										
										if(new Date().getTime()>new Date(item.ladingEndTime).getTime()){
											ladingTime=  "<span style='color:red'>"+ item.ladingEndTime.substring(0,item.ladingEndTime.length-10)+"</span>";
										}else{
											
											ladingTime=item.ladingEndTime.substring(0,item.ladingEndTime.length-10);
										}
										
//										ladingTime=item.ladingEndTime.substring(0,item.ladingEndTime.length-10);
										}else if(item.isLong==1){
											ladingTime= "长期";
										}
										
										if(!item.sourceGoodsId&&!item.rootGoodsId&&item.tArrivalStartTime&&item.period){
											
											ladingTime=new Date(item.tArrivalStartTime*1000+item.period*86400000).Format("yyyy-MM-dd");
											
										}
										
										
										return ladingTime;
									}
								},
								{
									title : "货品名称",
									width : 60,
									name : "productName"
								},
								{
									title : "货体总量(吨)",
									width : 60,
									name : "goodsTotal"
								},
								{
									title : "待提量(吨)",
									width : 50,
									name : "goodsWait"
								},
								{
									title : "封量(吨)",
									width : 50,
									name : "goodsSave",
									render : function(item, name, index) {

										return  util.FloatSub(item.goodsTotal,Math.min(item.goodsInPass,item.goodsOutPass));
									
									}
								},
								{
									title : "船发计划量(吨)",
									width : 50,
									name : "shipCount",
									render : function(item, name, index) {

										return  util.FloatSub(item.shipCount);
									
									}
								},
								{
									title : "可提数量(吨)",
									width : 60,
									name : "goodsCan",
									render : function(item, name, index) {

										return  util
											.FloatSub(
													util.FloatSub(util.FloatSub(item.goodsCurrent,item.shipCount),item.goodsWait),
													util
															.FloatSub(
																	item.goodsTotal,
																	Math
																			.min(
																					item.goodsInPass,
																					item.goodsOutPass)))

									
									}
								}];
				var type=$("#type").val()==-1?2:$("#type").val();
				var productId=$("#productName")
				.attr("data")?$("#productName")
						.attr("data"):-1;
				
				//获取条件下的统计数据
				$.ajax({
                    type:'get',
					url:config.getDomain()+"/lading/clientGoodstotal?isAll=1&productId="+productId+"&ladingType="+type+"&clientId="+$("#clientId").attr("data"),
//					data:params,
					dataType:'json',
					success:function(data){
						util.ajaxResult(data,'统计',function(ndata){
							var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
			                   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>可提总量: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+parseFloat(data.map.goodsTotal).toFixed(3)+" 吨</label>"	+	
							        "</div>";
							dialog.find('[data-role="clientGoodsGrid"]').find(".grid-body").parent().append(totalHtml);
						},true);
					}
				});
				
				
				dialog.find('[data-role="clientGoodsGrid"]').grid({
					identity : 'id',
					columns : columns,
					isShowIndexCol : false,
					isShowPages : false,
					url : config.getDomain() + "/lading/clientGoods?isAll=1&productId="+productId+"&ladingType="+type+"&clientId="+$("#clientId").attr("data")
				});
				
				dialog.modal({
					keyboard : true
				});
			});
		}else{
			$(
			"body")
			.message(
					{
						type : 'error',
						content : '请先选择货主'
					});
		}
		
	}
	
	//type: 1 冲销  2：修改调入量
	function openApproveModal(url,type){
		$.get(config.getResource()+"/pages/approve/approveReason.jsp").done(function(data){
			dialog = $(data) ;
			initApproveModal(dialog,url,type);
			dialog.modal({
				keyboard: true
			});
		});
		
	}
	
	function initApproveModal(dialog,url,type){
		
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/auth/role/get",
  			dataType : "json",
  			success : function(data) {
  				dialog.find('.department').typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product.name;
  	                    });
  	                    process(results);
  					},
  					updater: function (item) {
	  					var client = _.find(data.data, function (p) {
	                        return p.name == item;
	                    });
	  					if(client!=null){
	  						dialog.find('.department').attr("data",client.id)
	  					}
	  					search(dialog);
	  					return item;
  					},
					//移除控件时调用的方法
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.name == query;
                    });
  					//匹配不到就去掉id，让内容变空，否则给id
  					if(client==null){
  						dialog.find('.department').removeAttr("data");
  						dialog.find('.department').val("");
  						search(dialog);
  					}else{
  						dialog.find('.department').attr("data",client.id);
  					}
  				}
  				});
  			}
  		});
		
		var columns = [{
			title : "姓名",
			name : "name"
		},
		{
			title : "部门",
			name : "roleName"
		}];
		dialog.find('[data-role="approveGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain()+"/auth/role/getUser"
		});
		
		
		dialog.find(".button-ok").click(function(){
			
			var content="";
			//提单冲销
			if(type==1){
				content="提单【"+$("#code").val()+"】 申请冲销";
			}
			//修改调入量
			else if(type==2){
				
			}
			//修改合同
			else if(type==3){
				
			}
			
			
			
			var reviewId="";
			var co=0;
			$.each(dialog.find('[data-role="approveGrid"]').getGrid().selectedRows(), function (i, role) {
				co=i;
				reviewId=role.userId;
	        });
			if(co>0){
				 $("body").message({
					 type : 'error',
					 content : '只能选择一个审批人！'
				 });
				 return;
			}
			$.ajax({
	  			type : "post",
	  			data:{
	  				url:url,
	  				reviewUserId:reviewId,
	  				content:content,
	  				status:1,
	  				reason:dialog.find("#reason").val(),
	  				part:3
	  			},
	  			url : config.getDomain()+"/approve/save",
	  			dataType : "json",
	  			success : function(data) {
					 if (data.code == "0000") {
						 $("body").message({
							 type : 'success',
							 content : '申请成功'
						 });
//						 dialog.remove();
					 }else {
						 $("body").message({
							 type : 'error',
							 content : '申请失败'
						 });
					 }
				 }
	  			});
			dialog.remove();
			 
		});
	}
	
	
	var search = function(dialog) {
		
		var params = {};
        dialog.find("#approveForm").find('.form-control').each(function(){
            var $this = $(this);
            var name = $this.attr('name');
             if(name){
            	 if($this.attr("data")){
            		 params[name] = $this.attr("data");
            	 }
            }
        });
        dialog.find('[data-role="approveGrid"]').getGrid().search(params);
	};
	
	function printmb(){
		$.get(config.getResource() + "/pages/outbound/lading/ladingPrintmb.jsp").done(function(data) {
			dialog = $(data);
			initprintmb(dialog);
			
			dialog.modal({
				keyboard : true
			});
		});
	}
	function initprintmb(dialog){
								dialog.find(".btn-print").unbind('click'); 
								dialog.find(".btn-print").click(function() {
//									
									dialog.find('.modal-body').jqprint();
								});
								
					
	}
	
	
	return {
		changetab : changetab,
		init : function() {
			initFormIput();
			handleRecords();
		},
		search : function() {
			var params = {};
			$("#ladingListForm").find('.form-control').each(function() {
				var $this = $(this);
				var name = $this.attr('name');
				var data=$this.attr('data');
				if(data&&data!=""){
                	params[name]=$this.attr('data');
                }else
                	if(name){
                   	 if($this.val()!=""){
                   		 params[name] = $this.val();
                   	 }else{
                   		 
                   	 }
                   }
			});
			
			if(guoqi==1){
				 params["status"]=4;
			}
			
			$('[data-role="ladingGrid"]').getGrid().search(params);
		},
		doAdd : function() {
			save();
		},
		doEdit : function() {
			update();
		},
		doDelete : function() {

		},
		initAdd : function() {
			initFormIput();
			initAddSelection();
		},
		doChange : function(obj) {
			initChange(obj);
		},
		addNum : function(obj) {
			addNum(obj);
		},
		initEdit : function(id,type) {
			
			if(type==2){
				$(".ladingBack").attr("href","#/storageFee?type=4");
			}
			
			initFormIput();
			initEdit(id);
		},
		openDialog : function(type) {
			openModal(type);
		},
		openDialog1 : function(id) {
			openModal1(id);
		},
		openModal2 : function(id) {
			openModal2(id);
		},
		deleteItem : function(id) {
			deleteItem(id);
		},
		removeGood : function(id, obj, ladingId) {
			removeGood(id, obj, ladingId);
		},
		removeGoodAdd : function(obj) {
			removeGoodAdd(obj);
		},
		changeType : function() {
			changeType();
		},
		initEditTable : function(obj) {
			initEditTable(obj);
		},
		concel : function(obj) {
			concel(obj);
		},
		confirm : function(obj) {
			confirm(obj);
		},
		addNewClient : function() {
			addNewClient();
		},
		openFileModal : function(id) {
			openFileModal(id);
		},
		print:function(id){
			print(id);
		},openApproveModal:function (url,type){
			openApproveModal(url,type);
		},
		getClientGoods:getClientGoods,
		reset:reset,
		printmb:printmb,
		exportExcel:exportExcel 
	};

}();