var Storage = function() {
	
	var dialog 	= null;    		//对话框
	var dataGrid 	= null; 		//Grid对象
	var mType=1;
	
	var cGoodsInspect=0;
	var cGoodsInspectSp=0;
	var cGoodsCode="";
	
//	var pGoodsInspect=0;
var mProductId=0;


function reset(){
	$(".arrivalcode").val("");
	$("#clientId").removeAttr("data");
	$("#clientId").val("");
	$("#shipId").val("");
	$("#shipId").removeAttr("data");
	$("#productId").val("");
	$("#productId").removeAttr("data");
	$("#startTime").val("");
	$("#endTime").val("");
	
	$("#taxType").val("13").trigger("change"); 
	$("#passStatus").val("13").trigger("change");
}


//导出通知单
function exportExcel(){
	var code = $(".arrivalcode").val() ;
	var clientId = $(".clientId").attr("data")==undefined?"": $(".clientId").attr("data") ;
	var shipId = $(".shipId").attr("data")?$(".shipId").attr("data"):'' ;
	var taxType = $("#taxType").val()? $("#taxType").val():'' ;
	var startTime=$("#startTime").val()?$("#startTime").val():'';
	var endTime=$("#endTime").val()?$("#endTime").val():'';
	var productId=$("#productId").attr("data")?$("#productId").attr("data"):'' ;
	
	var url = config.getDomain()+"/storageConfirm/exportExcel?productId="+productId+"&startTime="+startTime+"&endTime="+endTime+"&taxType="+taxType+"&type="+mType+"&&arrivalcode="+code+"&&clientId="+clientId+"&&shipId="+shipId ;
	window.open(url) ;
}
function deleteCargo(id){
	
	$.ajax({
			type : "get",
			async:false,
			url : config.getDomain()+"/storageConfirm/deleteCargo?cargoId="+id,
			dataType : "json",
			success : function(data) {
				if(data.code=="0000"){
					$("body").message({
						type: 'success',
						content: '删除成功'
					});
					$('[data-role="storageGrid"]').grid('refresh');
				}else{
					$("body").message({
						type: 'error',
						content: '删除失败'
					});
				}
			}
				});
			
}

	//货体换罐
	function changeTank(){
		
			var goodsId="";
			if($('input[type="checkbox"][name="chk_list"]:checked').size()>1){
				$("body").message({
					type: 'error',
					content: '只能选择一个货体'
				});
				return;
			}
			if($('input[type="checkbox"][name="chk_list"]:checked').size()==0){
				$("body").message({
					type: 'error',
					content: '请选择一个货体'
				});
				return;
			}
			if($('input[type="checkbox"][name="chk_list"]:checked').size()==1){
				$('input[type="checkbox"][name="chk_list"]:checked').each(function(){
					var _tr = $(this).parents("tr");
					goodsId = $(_tr).find(".goodsId").val();
				});
				
				openTankModal(goodsId);
				
			}
		
	}
	
	//打开货体换罐dialog
	function openTankModal(goodsId){
		$.get(config.getResource()+"/pages/outbound/goodsManager/changeTank.jsp").done(function(data){
			dialog = $(data) ;
				initTankModal(dialog,goodsId);
			dialog.modal({
				keyboard: true
			});
		});
	}

function initTankModal(dialog,goodsId){
		
		config.load();
	
		$.ajax({
  			type : "get",
  			async:false,
  			url : config.getDomain()+"/tank/list?pagesize=0&type=1&productId="+mProductId,
  			dataType : "json",
  			success : function(data) {
  				config.unload();
  				dialog.find('#dTankId').typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product.code;
  	                    });
  	                    process(results);
  					},
  					
					//移除控件时调用的方法
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.code == query;
                    });
  					//匹配不到就去掉id，让内容变空，否则给id
  					if(client==null){
  						dialog.find('#dTankId').removeAttr("data");
  						dialog.find('#dTankId').val("");
  					}else{
  						dialog.find('#dTankId').attr("data",client.id);
  					}
  				}
  				});
  			}
  		});

		

		
		
		dialog.find(".button-ok").click(function(){
			config.load();
			var tankId=-1;
			if(dialog.find('#dTankId').attr("data")){
				tankId=dialog.find("#dTankId").attr("data");
			}
			$.ajax({
				async:false,
			type : "post",
			url : config.getDomain()+"/storageConfirm/changeTank",
			dataType : "json",
			data:{
				"id":goodsId,
				"tankId":tankId
			},
			success : function(data) {

				config.unload();
				if (data.code == "0000") {
					$("body").message({
						type: 'success',
						content: '货体换罐成功'
					});
					dialog.remove();
					initEdit($(".cargoId").val());
				} else {
					$("body").message({
						type: 'error',
						content: '货体换罐失败'
					});
					dialog.remove();
				}
			
			}
			});
		});
		
	}
	
	
	function addNewClient() {
		$.get(config.getResource()+ "/pages/auth/baseinfo/client/add.jsp").done(
						function(data) {
							dialog = $(data);
							dialog.modal({
								keyboard : true
							});

							$.ajax({
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
																source : function(query,process) {
																	var results = _.map(data.data,function(product) {
																						return product.name;
																					});
																	process(results);
																},
																noselect : function(query) {
																	var client = _.find(data.data,function(p) {
																						return p.name == query;
																					});
																	if (client == null) {
																		dialog.find('#clientGroupId').removeAttr("data");
																		dialog.find('#clientGroupId').val("");
																	} else {
																		dialog.find('#clientGroupId').attr("data",client.id)
																	}
																}
															});
										}
									});

							dialog.find("#addClient").click(
											function() {
												if (config.validateForm(dialog.find(".form-client"))) {
													$.ajax({
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
																		$("body").message(
																			{
																				type : 'success',
																				content : '客户添加成功'
																			});
																		dialog.remove();
																		$("#clearanceClientId").val(dialog.find(".name").val());
																		$("#clearanceClientId").attr("data",data.map.id);
																	} else {
																		$("body").message(
																			{
																				type : 'error',
																				content : '客户添加失败'
																			});
																		dialog.remove();
																	}
																}
															});
												}
											});
						});
	}
	
	
	
	
	
	
	//打开拆分货体dialog
	function openEditGoodsTotalModal(goodsId,goodsTotal,isPredict){
		$.get(config.getResource()+"/pages/inbound/storage_confirm/editGoodsTotal.jsp").done(function(data){
			dialog = $(data) ;
				initEditGoodsTotalModal(dialog,goodsId,goodsTotal,isPredict);
			dialog.modal({
				keyboard: true
			});
		});
	}
	
	function initEditGoodsTotalModal(dialog,goodsId,goodsTotal,isPredict){
		
		dialog.find(".button-ok").click(function(){
			if(config.validateForm(dialog.find(".sqlitform"))){
				
				var editCount=util.FloatAdd(parseFloat(dialog.find("#dGoodsTotal").val()),parseFloat(goodsTotal));
			
			
			config.load();
			$.ajax({
				async:false,
			type : "post",
			url : config.getDomain()+"/storageConfirm/editGoodsTotal",
			dataType : "json",
			data:{
				"goodsId":goodsId,
				"goodsTotal":editCount,
				"isPredict":isPredict
			},
			success : function(data) {

				config.unload();
				if (data.code == "0000") {
					$("body").message({
						type: 'success',
						content: '操作成功'
					});
					dialog.remove();
					initEdit($(".cargoId").val());
				}else if(data.code=="1003"){
					$("body").message({
						type: 'error',
						content: '内控放行量不足（或余量不足），无法修改入库量  ',
					});
				} else {
					$("body").message({
						type: 'error',
						content: '操作失败',
					});
					dialog.remove();
				}
			}
			});
		
		}
	});
	}
	
	//打开拆分货体dialog
	function openModal(goodsId,oldGoodsTotal,oldGoodsInspect){
		$.get(config.getResource()+"/pages/inbound/storage_confirm/sqlitGoods.jsp").done(function(data){
			dialog = $(data) ;
				initModal(dialog,goodsId,oldGoodsTotal,oldGoodsInspect);
			dialog.modal({
				keyboard: true
			});
		});
	}
	
	function initModal(dialog,goodsId,oldGoodsTotal,oldGoodsInspect){
		console.log(oldGoodsInspect);
		var countType=0;
		if(oldGoodsInspect>0){
			dialog.find('#dGoodsInspect').show();
			dialog.find('.mi').show();
			countType=1;
		}else{
			dialog.find('#dGoodsInspect').hide();
			dialog.find('.mi').hide();
			countType=0;
		}
		
		var lossRate = $(".lossRate").val();
		var mGoodsInspect=dialog.find('#dGoodsInspect').val();
		var mGoodsTank=dialog.find('#dGoodsTank').val();
		if(countType==1){
			
			dialog.find('#dGoodsInspect').unbind("keyup").keyup(function(){
				if(lossRate>0){
					
					dialog.find('#dGoodsTotal').val(util.FloatSub(dialog.find('#dGoodsInspect').val(),(util.FloatMul(dialog.find('#dGoodsInspect').val(),lossRate)/1000).toFixed(3)));
				}else{
					dialog.find('#dGoodsTotal').val(dialog.find('#dGoodsInspect').val());
				}
				
			});
			
		}else{
			
			dialog.find('#dGoodsTank').unbind("keyup").keyup(function(){
				if(lossRate>0){
				dialog.find('#dGoodsTotal').val(util.FloatSub(dialog.find('#dGoodsTank').val(),(util.FloatMul(dialog.find('#dGoodsTank').val(),lossRate)/1000).toFixed(3)));
			}else{
				dialog.find('#dGoodsTotal').val(dialog.find('#dGoodsTank').val());
			}
			});
			
		}
		
		
		config.load();
//		util.urlHandleTypeaheadAllData("/baseController/getClientName",dialog.find('#dClientId'),function(item){return item.name+"["+item.code+"]";});
//		$.ajax({
//			 async:false,
//	  			type : "get",
//	  			url : config.getDomain()+"/client/select",
//	  			dataType : "json",
//	  			success : function(data) {
//	  				dialog.find('#dClientId').typeahead({
//	  					source: function(query, process) {
//	  						var results = _.map(data.data, function (product) {
//	  	                        return product.name;
//	  	                    });
//	  	                    process(results);
//	  					},
//	  				noselect:function(query){
//	  					var client = _.find(data.data, function (p) {
//	                        return p.name == query;
//	                    });
//	  					if(client==null){
//	  						dialog.find('#dClientId').removeAttr("data");
//	  						dialog.find('#dClientId').val("");
//	  					}else{
//	  						dialog.find('#dClientId').attr("data",client.id)
//	  					}
//	  				}
//	  				});
//	  			}
//	  		});
		
		$.ajax({
  			type : "get",
  			async:false,
  			url : config.getDomain()+"/tank/list?pagesize=0&type=1",
  			dataType : "json",
  			success : function(data) {
  				config.unload();
  				dialog.find('#dTankId').typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product.code;
  	                    });
  	                    process(results);
  					},
  					
					//移除控件时调用的方法
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.code == query;
                    });
  					//匹配不到就去掉id，让内容变空，否则给id
  					if(client==null){
  						dialog.find('#dTankId').removeAttr("data");
  						dialog.find('#dTankId').val("");
  					}else{
  						dialog.find('#dTankId').attr("data",client.id)
  					}
  				}
  				});
  			}
  		});
		
//		$.ajax({
//			async:false,
//		type : "get",
//		url : config.getDomain()+"/order/list?status=2",
//		dataType : "json",
//		success : function(data) {
//			config.unload();
//			dialog.find('#dContract').typeahead({
//					source: function(query, process) {
//						var results = _.map(data.data, function (product) {
//	                        return product.code;
//	                    });
//	                    process(results);
//					},
//					
//				//移除控件时调用的方法
//				noselect:function(query){
//					var client = _.find(data.data, function (p) {
//                    return p.code == query;
//                });
//					//匹配不到就去掉id，让内容变空，否则给id
//					if(client==null){
//						dialog.find('#dContract').removeAttr("data");
//						dialog.find('#dContract').val("");
//					}else{
//						dialog.find('#dContract').attr("data",client.id);
//					}
//				}
//				});
//			}
//		});
		
		dialog.find(".button-ok").click(function(){
			if(config.validateForm(dialog.find(".sqlitform"))){
				if(parseFloat(dialog.find("#dGoodsTotal").val())>parseFloat(oldGoodsTotal)){
					$(this).confirm({
						content : '数量不能大于该货体封量，请重新输入！',
						concel:true,
						callBack : function() {
						}
					});
					return;
				}
			}
			
//			var newGoodsList = new Array() ;
//					var newGoods = {
//							"goodsTotal":dialog.find("#dGoodsTotal").val(),
//							"tankId":dialog.find("#dTankId").attr("data"),
//					};
//					newGoodsList.push(newGoods);
			
			config.load();
			$.ajax({
				async:false,
			type : "post",
			url : config.getDomain()+"/storageConfirm/sqlitGoods",
			dataType : "json",
			data:{
				"oldGoodsId":goodsId,
				"goodsTotal":dialog.find("#dGoodsTotal").val(),
				"goodsTank":dialog.find("#dGoodsTank").val(),
				"goodsInspect":dialog.find("#dGoodsInspect").val(),
				"tankId":dialog.find("#dTankId").attr("data"),
				"type":countType
			},
			success : function(data) {

				config.unload();
				if (data.code == "0000") {
					$("body").message({
						type: 'success',
						content: '货体拆分成功'
					});
					dialog.remove();
					initEdit($(".cargoId").val());
				} else {
					$("body").message({
						type: 'error',
						content: '货体拆分失败'
					});
					dialog.remove();
				}
			
			}
			});
		});
		
		
	}
	function editGoodsTotal(){

		var goodsId="";
		var goodsPass="";
		var goodsTotal="";
		if($('input[type="checkbox"][name="chk_list"]:checked').size()>1){
			$("body").message({
				type: 'error',
				content: '只能选择一个货体'
			});
			return;
		}
		if($('input[type="checkbox"][name="chk_list"]:checked').size()==0){
			$("body").message({
				type: 'error',
				content: '请选择一个货体'
			});
			return;
		}
		if($('input[type="checkbox"][name="chk_list"]:checked').size()==1){
			var isP=0;
			$('input[type="checkbox"][name="chk_list"]:checked').each(function(){
				var _tr = $(this).parents("tr");
				var isPredict = $(_tr).find(".isPredict").val();
				isP=isPredict;
//				if(isPredict==1){
//					$("body").message({
//						type: 'error',
//						content: '不能选择预入库货体！'
//					});
//					return;
//				}
				goodsId = $(_tr).find(".goodsId").val();
				goodsPass=$(_tr).find(".goodsPass").val();
				goodsTotal=$(_tr).find("#goodsTotal").attr("data");
				
			});
			openEditGoodsTotalModal(goodsId,goodsTotal,isP);
			
		}
	
	}
	function predict(){
		if($('input[type="checkbox"][name="chk_list"]:checked').size()<2){
			$("body").message({
				type: 'error',
				content: '请选择一个预入库货体和一个确认货体！'
			});
			return;
		}
		if($('input[type="checkbox"][name="chk_list"]:checked').size()==0){
			$("body").message({
				type: 'error',
				content: '请选择一个预入库货体和一个确认货体！'
			});
			return;
		}
		if($('input[type="checkbox"][name="chk_list"]:checked').size()==2){
			var predictGoodsId;
			var goodsId;
			var predictTotal;
			var goodsTotal;
			var goodsOutPass;
			var goodsInPass;
			$('input[type="checkbox"][name="chk_list"]:checked').each(function(){
				var _tr = $(this).parents("tr");
				
				var isPredict = $(_tr).find(".isPredict").val();
				if(isPredict==1){
					predictGoodsId=$(_tr).find(".goodsId").val();
					predictTotal=$(_tr).find("#goodsTotal").text();
				}else{
					goodsId=$(_tr).find(".goodsId").val();
					goodsTotal=$(_tr).find("#goodsTotal").text();
					
					goodsOutPass=$(_tr).find(".goodsPass").val();
					
					goodsInPass=$(_tr).find(".goodsPass").val();
				}
			});
			
			if(goodsOutPass&&goodsInPass){
				if(parseFloat(goodsOutPass)>0||parseFloat(goodsInPass)>0){
					$("body").message({
						type: 'error',
						content: '已经放行的货体无法关联预入库货体，请取消放行后重试！'
					});
					return;
				}
			}
			
			if(predictGoodsId&&goodsId){
				if(parseFloat(predictTotal)>parseFloat(goodsTotal)){
					$("body").message({
						type: 'error',
						content: '预入库货体数量不能大于所关联货体！'
					});
				}else{
					
					//预入库与确认货体关联
					$(this).confirm({
						content : '确定要关联预入库吗?此操作无法悔改。',
						callBack : function() {
							config.load();
							$.ajax({
								type : "get",
								url : config.getDomain()+"/storageConfirm/predict?cargoId="+$(".cargoId").val()+"&predictGoodsId="+predictGoodsId+"&goodsId="+goodsId,
								dataType : "json",
								success : function(data) {
									config.unload();
									if (data.code == "0000") {
										$("body").message({
											type: 'success',
											content: '关联成功'
										});
										initEdit($(".cargoId").val());
									} else {
										$("body").message({
											type: 'error',
											content: '关联失败'
										});
									}
								}
							});
						}
					});
				}
			}else{
				$("body").message({
					type: 'error',
					content: '请选择一个预入库货体和一个确认货体！'
				});
			}
			
			
			
			return;
		}
		
		
	}
	
	//货体拆分
	function sqlit(){
		var goodsId="";
		if($('input[type="checkbox"][name="chk_list"]:checked').size()>1){
			$("body").message({
				type: 'error',
				content: '只能选择一个货体'
			});
			return;
		}
		if($('input[type="checkbox"][name="chk_list"]:checked').size()==0){
			$("body").message({
				type: 'error',
				content: '请选择一个货体'
			});
			return;
		}
		if($('input[type="checkbox"][name="chk_list"]:checked').size()==1){
			var goodsId;
			var oldGoodsTotal;
			var oldGoodsInspect;
			
			$('input[type="checkbox"][name="chk_list"]:checked').each(function(){
				var _tr = $(this).parents("tr");
				goodsId = $(_tr).find(".goodsId").val();
				oldGoodsTotal=parseFloat($(_tr).find("#goodsTotal").text())-parseFloat($(_tr).find("#goodsPassSp").text());
				oldGoodsInspect=parseFloat($(_tr).find("#goodsInspectSp").text());
			});
			openModal(goodsId,oldGoodsTotal,oldGoodsInspect);
			
		}
	}
	
	//货体合并
	function merge(){
		var ids="";
		var cando=true;
		if($('input[type="checkbox"][name="chk_list"]:checked').size()>1){
			$('input[type="checkbox"][name="chk_list"]:checked').each(function(){
				var _tr = $(this).parents("tr");
				var id = $(_tr).find(".goodsId").val();
				ids+=id+",";
				if($(_tr).find(".isPredict").val()==1){
					cando=false;
				}
			});
			
			if(!cando){
				$(this).confirm({
					content : '不能选择预入库货体！',
					concel:true,
					callBack : function() {
					}
				});
			}else{
				
				ids=ids.substring(0, ids.length - 1);
				$(this).confirm({
					content : '确定要合并货体吗?',
					callBack : function() {
						config.load();
						$.ajax({
							type : "get",
							url : config.getDomain()+"/storageConfirm/mergegoods?ids="+ids,
							dataType : "json",
							success : function(data) {
								config.unload();
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '货体合并成功'
									});
									initEdit($(".cargoId").val());
								} else {
									$("body").message({
										type: 'error',
										content: '货体合并失败'
									});
								}
							}
						});
					}
				});
			}
			
		}
		if($('input[type="checkbox"][name="chk_list"]:checked').size()<=1){
    		$(this).confirmm({
				content : '请至少选择两个货体！',
				concel:true,
				callBack : function() {
				}
			});
		}
	}
	
	function initEdit(id){
		
		$('.date-picker').datepicker({
			  rtl: Metronic.isRTL(),
			    orientation: "left",
			    format: "yyyy-mm-dd",
			    showInputs:true,
			    startView:"days",
			    minViewMode:"days",
			    showDay:"false",
	            disableMousewheel:false,
	            changeMonth :false,
			    autoclose: true,
	});
		
		
		config.load();
		util.urlHandleTypeaheadAllData("/baseController/getClientName",$('#clearanceClientId'),function(item){return item.name+"["+item.code+"]";});
		$(".print").unbind('click'); 
		$(".print").click(function(){
			print(id);
		});
//		 $.ajax({
//			 async:false,
//	  			type : "get",
//	  			url : config.getDomain()+"/client/select",
//	  			dataType : "json",
//	  			success : function(data) {
//	  				$('#clearanceClientId').typeahead({
//	  					source: function(query, process) {
//	  						var results = _.map(data.data, function (product) {
//	  	                        return product.name;
//	  	                    });
//	  	                    process(results);
//	  					},
//	  				noselect:function(query){
//	  					var client = _.find(data.data, function (p) {
//	                        return p.name == query;
//	                    });
//	  					if(client==null){
//	  						$('#clearanceClientId').removeAttr("data");
//	  						$('#clearanceClientId').val("");
//	  					}else{
//	  						$('#clearanceClientId').attr("data",client.id)
//	  					}
//	  				}
//	  				});
//	  			}
//	  		});
		
		
		
		
		
		$.ajax({
			type : "get",
			url : config.getDomain()+"/storageConfirm/list?cargoId="+id,
			dataType : "json",
			success : function(data) {
				config.unload();
				var intent=data.data[0];
//				console.log(parseFloat(intent.cargodata.goodsTotal).toFixed(3));
//				console.log(parseFloat(intent.cargodata.goodsOutPass).toFixed(3));
				
				if(intent.cargodata.contractId!=0&&intent.cargodata.contractId!=undefined&&parseFloat(intent.cargodata.goodsTotal).toFixed(3)==parseFloat(intent.cargodata.goodsOutPass).toFixed(3)){
					$(".print").show();
				}else{
					$(".print").hide();
				}
				
				var lossRate=intent.cargodata.lossRate==null?0:intent.cargodata.lossRate;
				$(".cargoId").val(intent.cargodata.id);
				$(".cargoCode").val(intent.cargodata.code);
				$(".clientName").val(intent.cargodata.clientName);
				$(".goodsName").val(intent.cargodata.productName);
				mProductId=intent.cargodata.productId;
				$(".cargoGoodsTank").val(intent.cargodata.goodsTank==null?0:intent.cargodata.goodsTank);
				$(".cargoGoodsInspect").val(intent.cargodata.goodsInspect==null?0:intent.cargodata.goodsInspect);
				$(".cargoGoodsInPass").val(intent.cargodata.goodsInPass==null?0:intent.cargodata.goodsInPass);
				$(".cargoGoodsOutPass").val(intent.cargodata.goodsOutPass==null?0:intent.cargodata.goodsOutPass);
				$(".lossRate").val(lossRate);
				
				$(".customLading").val(intent.cargodata.customLading);
				$(".customPassCount").val(intent.cargodata.customPassCount==null?0:intent.cargodata.customPassCount);
				if(intent.cargodata.customLadingTime){
					
					$("#sCustomLadingTime").val(intent.cargodata.customLadingTime);
				}else{
//					$(".sCustomLadingTime").val(new Date().Format("yyyy-MM-dd"));
				}
				
//				$(".customLading").attr("data",intent.cargodata.customLading);
				
				$(".saveClearance").unbind('click'); 
				$(".saveClearance").click(function() {
					
					if($('#customPassCount').val()&&(intent.cargodata.customPassCount==0||intent.cargodata.customPassCount==null)){
						$("#sCustomLadingTime").val(new Date().Format("yyyy-MM-dd"));
					}
					
					config.load();
					$.ajax({
						type : "post",
						url : config.getDomain()+"/arrivalPlan/updateCargo",
						dataType : "json",
						data:{
							"id":$(".cargoId").val(),
							"customLading":$('#customLading').val(),
							"customPassCount":$('#customPassCount').val(),
							"sCustomLadingTime":$('#sCustomLadingTime').val()
						},
						success : function(data) {
							config.unload();
							if (data.code == "0000") {
								$("body").message({
									type: 'success',
									content: '保存成功'
								});
								initEdit($(".cargoId").val());
							} else {
								$("body").message({
									type: 'error',
									content: '保存失败'
								});
							}
						}
					});
				});
				
				if(intent.cargodata.arrivalTime){
					$(".arrivalInfo").val(intent.cargodata.shipName+" "+intent.cargodata.arrivalTime.split("-")[1]+"/"+intent.cargodata.arrivalTime.split("-")[2].split(" ")[0]);
				}else{
					$(".arrivalInfo").val();
				}
				
				
				
				var isfinish=intent.cargodata.cargoStatus;
				
				
				if(intent.cargodata.goodsInPass>0||intent.cargodata.goodsOutPass>0){
					$(".btn-merge").hide();
//					$(".btn-sqlit").hide();
					
				}else{
					$(".btn-merge").show();
//					$(".btn-sqlit").show();
				}
				
				var goodsdata=data.data[0].goodsdata;
				$("#goods-table-storage").children("tbody").each(function(){
					$(this).children("tr").remove();
				});
				if(goodsdata!=""){
						
						for(var i =0 ;i<goodsdata.length;i++){
							var tankcode="";
							if(goodsdata[i].tankCode!=null){
								tankcode=goodsdata[i].tankCode;
							}
							
							var goodsCurrent=goodsdata[i].goodsCurrent==null?0:goodsdata[i].goodsCurrent;
							
							var goodsTank=goodsdata[i].goodsTank==null?0:goodsdata[i].goodsTank;
							var goodsInspect=goodsdata[i].goodsInspect==null?0:goodsdata[i].goodsInspect;
							var goodsInPass=goodsdata[i].goodsInPass==null?0:goodsdata[i].goodsInPass;
							var goodsOutPass=goodsdata[i].goodsOutPass==null?0:goodsdata[i].goodsOutPass;
							var customInPass=goodsdata[i].customInPass==null?0:goodsdata[i].customInPass;
							var customOutPass=goodsdata[i].customOutPass==null?0:goodsdata[i].customOutPass;
							
							var clearanceClientId=goodsdata[i].clearanceClientName==null?"":goodsdata[i].clearanceClientName;
							var description=goodsdata[i].description==null?"":goodsdata[i].description;
							var isPredict=goodsdata[i].isPredict==1?"预入库":"";
							var goods = "<tr>";
							goods+="<input type='hidden' value='"+goodsdata[i].id+"' class='goodsId' /><input type='hidden' value='"+goodsdata[i].isPredict+"' class='isPredict' /><input type='hidden' value='"+goodsCurrent+"' class='goodsCurrent' />"+
							"<td><input type='checkbox' class='checkboxes' name='chk_list'/></td>"+
//							"<td>"+goodsdata[i].code+"     "+isPredict+"</td>"+
							"<td><input type='text' class='form-control  code'  value='"+goodsdata[i].code+"' style='display:none'/>"+
							"<span id='codeSp'>"+goodsdata[i].code+"</span><span>"+isPredict+"</span></td>"+
							"<td><span id='goodsTotal' data='"+goodsdata[i].goodsTotal+"'>"+goodsdata[i].goodsTotal+"</span></td>"+
							"<td>"+tankcode+"</td>"+
//							"<td><input type='text' class='form-control tankCodeSp' maxlength='14'  value='"+tankcode+"' data='"++"' style='display:none'/>"+
//							"<span id='tankCodeSp'>"+tankcode+"</span></td>"+
							"<td><input type='text' maxlength='32' style='width:100px' class='form-control input-medium clearanceClientIdSp' id='clearanceClientIdSp' readonly value='"+clearanceClientId+"' data='"+goodsdata[i].clearanceClientId+"'/>"+
							"<input type='text' class='form-control input-medium clearanceClientId' id='clearanceClientId' data="+goodsdata[i].clearanceClientId+" name='clientId' data-provide='typeahead' value='"+clearanceClientId+"' style='display:none;width:100px'/></td>"+
							
							"<td>"+goodsTank+"</td>"+
							
						
							
							"<td>"+util.FloatSub(goodsTank,util.FloatDiv(util.FloatMul(goodsTank,lossRate),1000))+"</td>"+
							"<td><input type='text' class='form-control goodsInspect' maxlength='14' onkeyup='Storage.calGoodsTotal(this)' value='"+goodsInspect+"' style='display:none'/>"+
								"<span id='goodsInspectSp'>"+goodsInspect+"</span></td>"+
							"<td><input type='text' class='form-control  goodsInPass' maxlength='14' onkeyup='config.clearNoNum(this)' value='"+customInPass+"' style='display:none'/>"+
								"<span id='goodsInPassSp'>"+customInPass+"</span></td>"+
							"<td><input type='text' class='form-control  goodsOutPass' maxlength='14' onkeyup='config.clearNoNum(this)' value='"+customOutPass+"' style='display:none'/>"+
								"<span id='goodsOutPassSp'>"+customOutPass+"</span></td>"+
								"<td><input type='text' class='form-control  goodsPass' maxlength='14' onkeyup='config.clearNoNum(this)' value='"+goodsOutPass+"' style='display:none'/>"+
								"<span id='goodsPassSp'>"+goodsOutPass+"</span></td>"+
							"<td><input type='text' class='form-control  description' maxlength='14'  value='"+description+"' style='display:none'/>"+
							"<span id='descriptionSp'>"+description+"</span></td>";
							if(isfinish==9&&isPredict==1){
								goods+="<td></td>";
							}else{
								if(config.hasPermission('ACHANGESTORAGEGOODS')){
								goods+="<td style='vertical-align:middle;' nowrap=''><a href='javascript:void(0)' class='btn btn-xs blue edit' onclick='Storage.initEditTable(this)'> <span class='glyphicon glyphicon glyphicon-edit' title='编辑'></span></a>"+
								"<a href='javascript:void(0)' class='btn btn-xs blue confirm' onclick='Storage.confirm(this)' style='display:none'> <span class='glyphicon glyphicon glyphicon-ok' title='确认'></span></a>"+
								"<a href='javascript:void(0)' class='btn btn-xs blue concel' onclick='Storage.concel(this)' style='display: none;'> <span class='glyphicon glyphicon glyphicon-remove' title='取消'></span></a>"+
								"</td>";
								}
							}
							goods +="</tr>";
							$("#goods-table-storage").children("tbody").append(goods);
							
						}

						
						//货主
						$.ajax({
							async:false,
				  			type : "get",
				  			url : config.getDomain()+"/client/select",
				  			dataType : "json",
				  			success : function(data) {
				  				config.unload();
				  				$("#goods-table-storage").children("tbody").each(function(){
									var _in=$(this).find(".clearanceClientId");
									$(_in).typeahead({
				  					source: function(query, process) {
				  						var results = _.map(data.data, function (product) {
				  	                        return product.name;
				  	                    });
				  	                    process(results);
				  					},
				  				noselect:function(query){
				  					var client = _.find(data.data, function (p) {
				                        return p.name == query;
				                    });
				  					if(client==null){
				  						$(_in).removeAttr("data");
				  						$(_in).val("");
				  					}else{
				  						$(_in).attr("data",client.id);
				  					}
				  				}
				  				});
				  				});
				  			}
				  		});
						
//		var columns = [ {
//			title : "货体编号",
//			name : "code",
//			render: function(item, name, index){
//				return "<input type='text' class='form-control input-medium goodsId' id='goodsId' value='"+item.id+"' style='display:none'/>"+
//				"<span id='code'>"+item.code+"</span>";
//			}
//		},
//		{
//			title : "罐号",
//			name : "tankCode",
//		},{
//			title : "罐检数（单位：吨）",
//			name : "goodsTank",
//			render: function(item, name, index){
//				return "<input type='text' class='form-control input-medium goodsTank' id='goodsTank' value='"+item.goodsTank+"' style='display:none'/>"+
//				"<span id='goodsTankSp'>"+item.goodsTank+"</span>" ;
//			}
//		}, {
//			title : "商检数（单位：吨）",
//			name : "goodsInspect",
//			render: function(item, name, index){
//				return "<input type='text' class='form-control input-medium goodsInspect' id='goodsInspect' value='"+item.goodsInspect+"' style='display:none'/>"+
//				"<span id='goodsInspectSp'>"+item.goodsInspect+"</span>" ;
//			}
//		}, {
//			title : "入库放行数（单位：吨）",
//			name : "goodsInPass",
//			render: function(item, name, index){
//				return "<input type='text' class='form-control input-medium goodsInPass' id='goodsInPass' value='"+item.goodsInPass+"' style='display:none'/>"+
//				"<span id='goodsInPassSp'>"+item.goodsInPass+"</span>" ;
//			}
//		},
//		{
//			title : "出库放行数（单位：吨）",
//			name : "goodsOutPass",
//			render: function(item, name, index){
//				return "<input type='text' class='form-control input-medium goodsOutPass' id='goodsOutPass' value='"+item.goodsOutPass+"' style='display:none'/>"+
//				"<span id='goodsOutPassSp'>"+item.goodsOutPass+"</span>"  ;
//			}
//		}, 
//		{
//			title : "操作",
//			name : "id",
//			render: function(item, name, index){
//             	return "<a href='javascript:void(0)' class='blue edit' id='edit' onclick='Storage.initEditTable(this)'> <i class='icon-edit'></i>编辑</a>"+
//				"<a href='javascript:void(0)' class='blue confirm' id='confirm' onclick='Storage.confirm(this)' style='display:none'> <i class='icon-edit'></i>确认</a>"+
//				"<a href='javascript:void(0)' class='blue concel' id='concel' onclick='Storage.concel(this)' style='display: none;'> <i class='icon-edit'></i>取消</a>";
//			}
//		}  ];
//
//		
//		/*解决id冲突的问题*/
//		$('[data-role="goodsGrid"]').grid({
//			identity : 'id',
//			columns : columns,
//			isShowIndexCol : true,
//			isShowPages : false,
//			isUserLocalData: true,
//			localData : data.data[0].goodsdata
////			url : config.getDomain()+"/storageConfirm/get?cargoId="+id
//		});
			}
				$('#goods-table-storage .group-checkable').change(function() {
					var set = $(this).attr("data-set");
					var checked = $(this).is(":checked");
					$(set).each(function() {
						if (checked) {
							$(this).attr("checked", true);
						} else {
							$(this).attr("checked", false);
						}
					});
					$.uniform.update(set);
				});
			}
		
		});
		
		
	}
	
	var calGoodsTotal=function(obj){
		config.clearNoNum(obj);
		var _tr = $(obj).parents("tr");
		var goodsInspect=$(_tr).find(".goodsInspect").val()==""?0:parseFloat($(_tr).find(".goodsInspect").val());
		var lossRate=parseFloat($(".lossRate").val());
		if(goodsInspect!=0){
			$(_tr).find("#goodsTotal").text(util.FloatSub(goodsInspect,(util.FloatMul(goodsInspect,lossRate)/1000).toFixed(3)));
		}else{
			$(_tr).find("#goodsTotal").text($(_tr).find("#goodsTotal").attr('data'));
		}
	}
	
	
	
	
	var initEditTable = function (obj) {
		$(obj).hide();
		var _tr = $(obj).parents("tr");
//		pGoodsInspect=$(_tr).find("#goodsTotal").text();
		$(_tr).find(".goodsTank").show();
		$(_tr).find("#goodsTankSp").hide();
		$(_tr).find(".goodsInspect").show();
		$(_tr).find("#goodsInspectSp").hide();
		$(_tr).find(".goodsInPass").show();
		$(_tr).find("#goodsInPassSp").hide();
		$(_tr).find(".goodsOutPass").show();
		$(_tr).find("#goodsOutPassSp").hide();
		$(_tr).find(".goodsPass").show();
		$(_tr).find("#goodsPassSp").hide();
		$(_tr).find(".description").show();
		$(_tr).find("#descriptionSp").hide();
		$(_tr).find(".clearanceClientId").show();
		$(_tr).find(".clearanceClientIdSp").hide();
		$(_tr).find(".code").show();
		$(_tr).find("#codeSp").hide();
		$(_tr).find(".edit").hide();
		$(_tr).find(".confirm").show();
		$(_tr).find(".concel").show();
	}
	var concel=function(obj) {
		$(obj).hide();
		var _tr = $(obj).parents("tr");
		$(_tr).find("#goodsTotal").text($(_tr).find("#goodsTotal").attr('data'));
		var _goodsTank = $(_tr).find(".goodsTank");
		$(_goodsTank).hide();
		var _goodsTankSp = $(_tr).find("#goodsTankSp");
		$(_goodsTankSp).show();
		var _goodsInspect = $(_tr).find(".goodsInspect");		
		$(_goodsInspect).hide();
		var _goodsInspectSp = $(_tr).find("#goodsInspectSp");
		$(_goodsInspectSp).show();
		var _goodsInPass = $(_tr).find(".goodsInPass");
		$(_goodsInPass).hide();
		var _goodsInPassSp = $(_tr).find("#goodsInPassSp");
		$(_goodsInPassSp).show();
		var _goodsOutPass = $(_tr).find(".goodsOutPass");
		$(_goodsOutPass).hide();
		var _goodsOutPassSp = $(_tr).find("#goodsOutPassSp");
		$(_goodsOutPassSp).show();
		
		var _goodsPass = $(_tr).find(".goodsPass");
		$(_goodsPass).hide();
		var _goodsPassSp = $(_tr).find("#goodsPassSp");
		$(_goodsPassSp).show();
		
		
		var _goodsOutPass = $(_tr).find(".goodsOutPass");
		$(_goodsOutPass).hide();
		var _goodsOutPassSp = $(_tr).find("#goodsOutPassSp");
		$(_goodsOutPassSp).show();
		
		
		var clearanceClientId = $(_tr).find(".clearanceClientId");
		$(clearanceClientId).hide();
		var clearanceClientIdSp = $(_tr).find(".clearanceClientIdSp");
		$(clearanceClientIdSp).show();
		
		var _code = $(_tr).find(".code");
		$(_code).hide();
		var _codeSp = $(_tr).find("#codeSp");
		$(_codeSp).show();
		
		$(_tr).find(".edit").show();
		$(_tr).find(".confirm").hide();
		$(_tr).find(".concel").hide();
		
		$(_goodsTank).val($(_goodsTankSp).text());
		$(_goodsInspect).val($(_goodsInspectSp).text());
		$(_goodsInPass).val($(_goodsInPassSp).text());
		$(_goodsOutPass).val($(_goodsOutPassSp).text());
		$(_description).val($(_descriptionSp).text());
		$(clearanceClientId).val($(clearanceClientIdSp).text());
		$(_code).val($(_codeSp).text());
	}
	
	var confirm=function(obj){
		
		var _tr = $(obj).parents("tr");
		var goodsTank=parseFloat($(_tr).find(".goodsTank").val());
		var goodsInspect=parseFloat($(_tr).find(".goodsInspect").val());
		var goodsInPass=parseFloat($(_tr).find(".goodsInPass").val());
		var goodsOutPass=parseFloat($(_tr).find(".goodsOutPass").val());
		var goodsPass=parseFloat($(_tr).find(".goodsPass").val());
//		if(goodsTank<goodsInspect||goodsTank<goodsInPass||goodsTank<goodsOutPass){
//    		$(this).confirm({
//				content : '数量不能大于罐检数，请重新输入！',
//				concel:true,
//				callBack : function() {
//				}
//			});
//			return;
//		}
		var clearanceClientId=$(_tr).find(".clearanceClientId").attr("data");
		var goodsTotal=parseFloat($(_tr).find("#goodsTotal").text());
		if(goodsTotal<goodsPass){
    		$(this).confirm({
				content : '内控放行数量不能大于货体总数，请重新输入！',
				concel:true,
				callBack : function() {
				}
			});
			return;
		}
		
		var goodsCurrent=parseFloat($(_tr).find(".goodsCurrent").val());
		console.log(goodsTotal);
		
		if(goodsPass<parseFloat($(_tr).find("#goodsTotal").attr('data')-goodsCurrent).toFixed(3)){
    		$(this).confirm({
				content : '内控放行数量不能小于于货体已操作量，请重新输入！',
				concel:true,
				callBack : function() {
				}
			});
			return;
		}
		
		
		console.log(parseFloat($(_tr).find("#goodsTotal").attr('data')-goodsCurrent));
		if(goodsTotal<parseFloat($(_tr).find("#goodsTotal").attr('data')-goodsCurrent).toFixed(3)){
    		$(this).confirm({
				content : '货体总数小于货体已操作量，请重新输入！',
				concel:true,
				callBack : function() {
				}
			});
			return;
		}
		
		
		config.load();
		var isInspect;
		if(parseFloat($(_tr).find(".goodsInspect").val()>0)){
			isInspect=1;
		}
		
		var _goodsInspect = $(_tr).find(".goodsInspect").val();
		var _goodsInspectSp = $(_tr).find("#goodsInspectSp").text();
		 cGoodsInspect=_goodsInspect;
		 cGoodsInspectSp=_goodsInspectSp;
		 cGoodsCode=$(_tr).find("#codeSp").text();
		if(parseFloat(_goodsInspect)!=parseFloat(_goodsInspectSp)&&_goodsInspectSp!=0){
			var url="/storageConfirm/updategoods?id="+$(_tr).find(".goodsId").val()+"&cargoId="+$(".cargoId").val()+"&goodsTotal="+$(_tr).find("#goodsTotal").text()+"&goodsInspect="+$(_tr).find(".goodsInspect").val()+
			"&isInspect=1";
			openApproveModal(url,1);
		}else{
			
		
		
		
		$.ajax({
			type : "post",
			url : config.getDomain()+"/storageConfirm/updategoods",
			dataType : "json",
			data : {
				'id': $(_tr).find(".goodsId").val(),
				'cargoId': $(".cargoId").val(),
				'goodsTotal':$(_tr).find("#goodsTotal").text(),
				'goodsTank': $(_tr).find(".goodsTank").val(),
				'goodsInspect': $(_tr).find(".goodsInspect").val(),
				'customInPass':$(_tr).find(".goodsInPass").val(),
				'customOutPass':$(_tr).find(".goodsOutPass").val(),
				'goodsInPass':$(_tr).find(".goodsPass").val(),
				'goodsOutPass':$(_tr).find(".goodsPass").val(),
				'description':$(_tr).find(".description").val(),
				'code':$(_tr).find(".code").val(),
				'isInspect':isInspect,
				'isPredict':$(_tr).find(".isPredict").val(),
				'passStatus' : 1,
				'clearanceClientId':clearanceClientId
			},
			success : function(data) {
				config.unload();
				if (data.code == "0000") {
					$("body").message({
						type : 'success',
						content : '货体信息修改成功'
					});
					
					initEdit($(".cargoId").val());
				}else if(data.code == "1003"){
					$("body").message({
						type : 'error',
						content : '该货批已经生成首期费，无法修改商检量！'
					});
				} else {
					$("body").message({
						type : 'error',
						content : '货体信息修改失败'
					});
				}
			}
		});
		
		}
		
	}

//	var doEdit=function() {
//		var cargoInfo = {
//				'id': $(".cargoId").val(),
//				'code': $(".cargoCode").val(),
//				'goodsTank': $(".cargoGoodsTank").val(),
//				'goodsInspect':$(".cargoGoodsInspect").val(),
//				'goodsInPass':$(".cargoGoodsInPass").val(),
//				'goodsOutPass':$(".cargoGoodsOutPass").val()
//			};
//		
//		var cagoGoodsList = new Array();
//		$("#goods-table-storage").children("tbody").children("tr").each(function() {
//			cagoGoodsList.push({
//				'id': $(this).find(".goodsId").val(),
//				'cargoId': $(".cargoId").val(),
//				'goodsTank': $(this).find("#goodsTankSp").text(),
//				'goodsInspect': $(this).find("#goodsInspectSp").text(),
//				'goodsInPass':$(this).find("#goodsInPassSp").text(),
//				'goodsOutPass':$(this).find("#goodsOutPassSp").text()
//			});
//		});
//		var cargoDto = {
//				"cargo" : cargoInfo,
//				"goodsList" : cagoGoodsList
//		};
//		$.ajax({
//			type : "post",
//			url : config.getDomain() + "/storageConfirm/update",
//			data : {
//				'cargoDto' : JSON.stringify(cargoDto)
//			},
//			dataType : "json",
//			success : function(data) {
//				if (data.code == "0000") {
//					alert("货批信息修改成功");
//					window.location.href = "#/storage";
//				} else {
//					alert("货批信息修改失败");
//				}
//			}
//		});
//	}
//	
    
	
	
	
	var handleRecords = function() {
//		util.urlHandleTypeaheadAllData("/baseController/getClientName",$('#clientId'),function(item){return item.name+"["+item.code+"]";});
//		util.urlHandleTypeahead("/baseController/getClientName",$("#clientId"));
		util.urlHandleTypeahead("/ship/select",$("#shipId"));
		util.urlHandleTypeahead("/product/select",$("#productId"));

		config.load();
		$.ajax({
			type : "get",
			url : config.getDomain() + "/client/select",
			dataType : "json",
			
			success : function(data) {
				config.unload();
				$('#clientId').typeahead({
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
	  						$('#clientId').attr("data",client.id);
	  					}
						return item;
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
		
		
		
		
		$('.date-picker').datepicker({
        autoclose: true,
        isRTL: Metronic.isRTL(),
        pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
    });
	
		
//		$.ajax({
//  			type : "get",
//  			url : config.getDomain()+"/client/select",
//  			dataType : "json",
//  			success : function(data) {
//  				$('#clientId').typeahead({
//  					source: function(query, process) {
//  						var results = _.map(data.data, function (product) {
//  	                        return product.name;
//  	                    });
//  	                    process(results);
//  					},
//  				updater: function (item) {
//  					var client = _.find(data.data, function (p) {
//                        return p.name == item;
//                    });
//  					return item;
//  				}
//  				});
//  			}
//  		});
		
		var columns = [ {
			title : "货批编号",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].code;
			}
		}, {
			title : "货主",
			name : "cargodata",
				render: function(item, name, index){
					return item[name].clientName;
				}
		}, {
			title : "货品名称",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].productName;
			}
		},{
			title : "进货船",
			width : 100,
			name : "cargodata",
			render: function(item, name, index){
				
					return item[name].shipName;
				}
		},{
			title : "到港日期",
			width : 100,
			name : "cargodata",
			render: function(item, name, index){
				if(item[name].arrivalTime){
					return item[name].arrivalTime.split(" ")[0];
				}else{
					return "";
				}
				}
		},{
			title : "贸易类型",
			width : 100,
			name : "cargodata",
			render: function(item, name, index){
				if(item[name].taxType==1){
					return "内贸";
				}else if(item[name].taxType==2){
					return "外贸";
				}else if(item[name].taxType==3){
					return "保税";
				}
				}
		},{
			title : "计划量(吨)",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].goodsPlan;
			}
		},{
			title : "罐检数(吨)",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].goodsTank;
			}
		}, {
			title : "商检数(吨)",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].goodsInspect;
			}
		}, {
			title : "内控放行数(吨)",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].goodsOutPass;
			}
		}, 
		{
			title : "说明",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].description;
			}
		}, {
			title : "入库单号",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].inboundNo;
			}
		}, 
		{
			title : "操作",
			name : "cargodata",
			render: function(item, name, index){
				var re="<div class='input-group-btn'>";
				if(config.hasPermission('AUPDATEHAIYUN')){
				re+="<a href='#/storageEdit?id="+item[name].id+"' style='margin:0 2px;font-size: 9px;' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a>";
				
				if(item[name].goodsTotal==0){
					re+="<a href='javascript:void(0)' onclick='Storage.deleteCargo("+item[name].id+")' style='margin:0 2px;font-size: 9px;' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a>";
					
				}
				}
				if(config.hasPermission('ASTORAGEFILE')){
				if(item[name].fileUrl){
					re+="<a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='Storage.openFileModal("+item[name].id+")' class='btn btn-xs blue'><span class='fa fa-arrow-up   ' title='上传文件'></span></a><a href='"+getRootPath()+item.fileUrl+"' onclick='' class='btn btn-xs blue'><span class='fa fa-arrow-down   ' title='查看文件'></span></a>";
					
				}else{
					re+="<a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='Storage.openFileModal("+item[name].id+")' class='btn btn-xs blue'><span class='fa fa-arrow-up   ' title='上传文件'></span></a>";
					
				}
				}
//				console.log(parseFloat(item[name].goodsTotal).toFixed(3));
//				console.log(parseFloat(item[name].goodsOutPass).toFixed(3));
				if((config.hasPermission('APRINTSTORAGE')&&parseFloat(item[name].goodsTotal).toFixed(3)==parseFloat(item[name].goodsOutPass).toFixed(3))||item[name].taxType==3){
				re+="<a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='Storage.print("+item[name].id+")' class='btn btn-xs blue'><span class='fa fa-file-text-o   ' title='打印入库及损耗通知单'></span></a>";
				}
				re+="</div>";
             	return re;
			}
		}  ];

		
		/*解决id冲突的问题*/
		$('[data-role="storageGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			gridName:'rukuqueren',
			stateSave:true,
			autoLoad:false,
			url : config.getDomain()+"/storageConfirm/list?"
		});
		
//		$.ajax({
//            type:'get',
//			url:config.getDomain()+"/storageConfirm/getInspectTotal",
//			dataType:'json',
//			success:function(data){
//				util.ajaxResult(data,'统计',function(ndata){
//					var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
//	                   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>商检总数: </label>" +
//	                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].goodsTotal+" 吨</label>"	+				
//					        
//	                   		"</div>";
//					$('[data-role="storageGrid"]').find(".grid-body").parent().append(totalHtml);
//				},true);
//			}
//		});
		
		
		search(1);
		//初始化按钮操作
		$(".btn-modify").unbind('click'); 
		$(".btn-modify").click(function() {
//			window.location.href = "#/intentGet?id="+data.id;
			var indexs = $('[data-role="storageGrid"]').getGrid().selectedRows();
			var $this = $(this);
			if (indexs.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要修改的记录'
				});
				return;
			}
			dataGrid = $(this);
//			$.get(config.getResource()+"/pages/contract/intent/edit.jsp").done(function(_data){
//				dataGrid = _data;
//			});
			window.location.href = "#/storageEdit?id="+$('[data-role="storageGrid"]').getGrid().selectedRows()[0].cargodata.id;
		});
		$(".btn-search").unbind('click'); 
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		
	};
	
	
	
	//上传文件
	function openFileModal(id){
		$.get(config.getResource()+"/pages/inbound/storage_confirm/fileAdd.jsp").done(function(data){
			dialog = $(data) ;
				initFileModal(dialog,id);
			dialog.modal({
				keyboard: true
			});
		});
	}
	
	function initFileModal(dialog,id){
		dialog.find('.addFile').click(function(){
			dialog.find('input[id=lefile]').click();
		});
		dialog.find('input[id=lefile]').change(function() {  
			dialog.find('#photoCover').val($(this).val());  
			});  
		dialog.find("#id").val(id);
			dialog.find(".button-ok").click(function(){
			// 使用 jQuery异步提交表单
				
//				dialog.find('#fileForm').submit();
//				dialog.find('#fileForm').ajaxSubmit(function(data){
//					});
				dialog.find('#fileForm').ajaxSubmit({  
					success:function(data){
						if(data.code=="0000"){
							$("body").message({
								type : 'success',
								content : '上传成功'
							});
							dialog.remove();
							$('[data-role="storageGrid"]').grid('refresh');
						}else{
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
	
	
	
	function printmb(){
		$.get(config.getResource() + "/pages/inbound/storage_confirm/inboundPrintmb.jsp").done(function(data) {
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
	
	function print(id){
		$.get(config.getResource() + "/pages/inbound/storage_confirm/inboundPrint.jsp").done(function(data) {
			dialog = $(data);
			initprint(dialog, id);
			
			dialog.modal({
				keyboard : true
			});
		});
	}
	
	function initprint(dialog,id){
		dialog.find('#pTime').val(new Date().Format("yyyy-MM-dd"));
		
		dialog.find('.date-picker').datepicker({
					  rtl: Metronic.isRTL(),
					    orientation: "left",
					    format: "yyyy-mm-dd",
					    showInputs:true,
					    startView:"days",
					    minViewMode:"days",
					    showDay:"false",
			            disableMousewheel:false,
			            changeMonth :false,
					    autoclose: true,
			}).on('changeDate', function(ev){
				
				dialog.find("#createTime").text(dialog.find('#pTime').val());
			});
		$.ajax({
			type:'get',
			url:config.getDomain()+"/auth/user/get?id="+config.findUserId(),
			dataType:'json',
			success:function(data){
				if(data.code=='0000'){
					dialog.find("#createUser").text(data.data[0].name);
					
					
					$.ajax({
						type:'get',
						url:config.getDomain()+"/storageConfirm/getCargo?cargoId="+id,
						dataType:'json',
						success:function(data){
							if(data.code=='0000'){
								
								var arrivalTime=data.data[0].cargodata.arrivalTime==null?"":data.data[0].cargodata.arrivalTime.split(" ")[0];
								var description=data.data[0].cargodata.description==null?"":data.data[0].cargodata.description;
								var lossRate=data.data[0].cargodata.lossRate==null?0:data.data[0].cargodata.lossRate;
								var goodsTotal=data.data[0].cargodata.goodsInspect>0?data.data[0].cargodata.goodsInspect:data.data[0].cargodata.goodsTotal;
								var customLading=data.data[0].cargodata.customLading==null?"":data.data[0].cargodata.customLading;
								dialog.find("#clientName").text(data.data[0].cargodata.clientName);
								dialog.find("#clientCode").text(data.data[0].cargodata.clientCode);
								dialog.find("#shipName").text(data.data[0].cargodata.shipName);
								dialog.find("#arrivalTime").text(arrivalTime);
								dialog.find("#productName").text(data.data[0].cargodata.productName);
								dialog.find("#code").text(data.data[0].cargodata.code);
								dialog.find("#goodsTotal").text(data.data[0].cargodata.goodsTotal+"(吨)");
								dialog.find("#goodsInspect").text(data.data[0].cargodata.goodsInspect+"(吨)");
//								dialog.find("#lossRate").text(lossRate*(goodsTotal/1000).toFixed(3)+"(吨)");
								
								if(data.data[0].cargodata.goodsInspect==0){
									dialog.find("#lossRate").text("0"+"(吨)");
								}
								else{
									
									dialog.find("#lossRate").text((data.data[0].cargodata.goodsInspect-data.data[0].cargodata.goodsTotal).toFixed(3)+"(吨)");
								}
//								console.log("++++"+lossRate*(goodsTotal/1000).toFixed(3));
								
								dialog.find("#ladingCode").text(customLading);
								
								dialog.find("#description").text(description);
								dialog.find("#createTime").text(new Date().Format("yyyy-MM-dd"));
								dialog.find("#goodsPlan").text(data.data[0].cargodata.goodsPlan+"(吨)");
								
								
								if(data.data[0].cargodata.inboundNo){
									dialog.find("#No").text(data.data[0].cargodata.inboundNo);
								}
								else{
									console.log(data.data[0].inboundNoCount);
									dialog.find("#No").text(util.getNo((parseFloat(data.data[0].cargodata.inboundNoCount.split('.')[1])+1)));
								}
								
								
								var No=dialog.find("#No")[0].innerHTML;
								
								dialog.find(".btn-print").unbind('click'); 
								dialog.find(".btn-print").click(function() {
//									
									$.ajax({
										type : "get",
										url : config.getDomain()+"/arrivalPlan/updateCargoinboundNo?cargoId="+data.data[0].cargodata.id+"&inboundNo="+No,
										dataType : "json",
										success : function(data) {
											
										}
									});
									dialog.find('.modal-body').jqprint();
								});
								
								
								
							}
						}
					});
					
					
					
					
					
				}
			}
		});
		
		
	}
	
function search(type){
	var params = {};
//params["arrivalcode"]= $("#storageListForm").find('.arrivalcode').val();
//params["clientId"]= $("#storageListForm").find('.clientId').attr("data");
$("#storageListForm").find('.form-control').each(function(){
    var $this = $(this);
    var name = $this.attr('name');
    var data=$this.attr('data');
    if(data&&data!=""){
    	params[name]=$this.attr('data');
    }else
     if(name){
    	 if($this.val()!=""){
    		 params[name] = $this.val();
    	 }
    }
});

if(type==1){
	params['type']=1;
	mType=1;
	
}else{
	params['type']=2;
	mType=2;
}



$('[data-role="storageGrid"]').getGrid().search(params);
$.ajax({
	type:'get',
	url:config.getDomain()+"/storageConfirm/getInspectTotal",
	data:params,
	dataType:'json',
	success:function(data){
//		$('[data-role="storageGrid"]').find(".grid-body").parent().remove(".totalFeeDiv");
		$(".totalFeeDiv").remove();
		util.ajaxResult(data,'统计',function(ndata){
			var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
			"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>商检总数: </label>" +
			"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].goodsTotal+" 吨</label>"	+				
			
			"</div>";
			$('[data-role="storageGrid"]').find(".grid-body").parent().append(totalHtml);
		},true);
	}
});
}


function openApproveModal(url,type){
	$.get(config.getResource()+"/pages/approve/approveReason.jsp").done(function(data){
		dialog = $(data) ;
		initApproveModal(dialog,url,type);
		dialog.modal({
			keyboard: true
		});
	});
	
}


var searchDialog = function(dialog) {
	
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


function initApproveModal(dialog,url,type){
	
	
	console.log(1);
	var content="";
	//修改商检量
	if(type==1){
		content="货体【"+cGoodsCode+"】 申请修改商检量由'"+cGoodsInspectSp+"'修改为'"+cGoodsInspect+"'";
		console.log(content);
		dialog.find("#approveText").text(content);
	}
	//修改调入量
	else if(type==2){
		
	}
	//修改合同
	else if(type==3){
		
	}
	
	
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
  					searchDialog(dialog);
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
						searchDialog(dialog);
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
  				part:2
  			},
  			url : config.getDomain()+"/approve/save",
  			dataType : "json",
  			success : function(data) {
				 if (data.code == "0000") {
					 $("body").message({
						 type : 'success',
						 content : '申请成功'
					 });
//					 dialog.remove();
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



	return {
		init : function() {
			initFormIput();
			handleRecords();
		},
		//type 1  不含虚拟库   2 含虚拟库
		search : function(type) {
			search(type);
			
		},
		doAdd : function() {
			save();
		},
		doDelete : function() {
			
		},
		addNum : function(obj){
			addNum(obj);
		},
		initEdit : function(id){
			initEdit(id);
		},
		initEditTable : function(obj){
			initEditTable(obj);
		},
		concel : function(obj){
			concel(obj);
		},
		confirm : function(obj){
			confirm(obj);
		},
		doEdit : function(){
			doEdit();
		},

		merge:function(){
			merge();
		},
		sqlit:function(){
			sqlit();
		},
		predict:function(){
			predict();
		},
		editGoodsTotal:function(){
			editGoodsTotal();
		},
		calGoodsTotal:function(obj){
			calGoodsTotal(obj);
		},
		openFileModal:function (id){
			openFileModal(id);
		},
		addNewClient:function (){
			addNewClient();
		},
		print:function(id){
			print(id);
		},
		changeTank:changeTank,
		exportExcel:exportExcel,
		openApproveModal:function (url,type){
			openApproveModal(url,type);
		},
		printmb:printmb,
		deleteCargo:deleteCargo,
		reset:reset
	};
	
	
	
}();