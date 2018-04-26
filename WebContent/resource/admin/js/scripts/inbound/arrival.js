var Arrival = function() {
	
	var dialog 	= null;    		//对话框
	var dataGrid 	= null; 		//Grid对象
	var sureLength=0;
	
	var goodsList=[];
	
	var goodsPlanList=[];
	
	var goodsSureList=[];
	
	var mSureList=[];
	
	var nArrivalId=0;
	var addDialog=null;
	
	//导出通知单
	function exportExcel(){
		var shipId = $("#shipId").attr("data") ;
		var startTime = $("#startTime").val() ;
		var endTime = $("#endTime").val() ;
		var sType=$("#type").val();
		var clientId=$("#client").attr("data")?$("#client").attr("data"):-1;
		var productId=$("#product").attr("data")?$("#product").attr("data"):-1;
		
		var url = config.getDomain()+"/arrivalPlan/exportExcel?clientId="+clientId+"&productId="+productId+"&type="+sType+"&startTime="+startTime+"&&endTime="+endTime ;
		window.open(url) ;
	}
	
	function reset(){
		$("#shipId").val("");
		$("#shipId").attr("data",0);
		$("#startTime").val("");
		$("#endTime").val("");
		$("#type").val("13").trigger("change"); 

		$("#arrivalStatus").val("3").trigger("change"); 
		$(".product").val("");
		$(".product").removeAttr("data");
		$(".client").val("");
		$(".client").removeAttr("data");
		$(".inboundCode").val("");
	}
	function updateForeshow(id,arrivalId){
		//更新船期预告
		$.ajax({
			async : false,
			type : "get",
			url : config.getDomain()
					+ "/arrivalForeshow/connectForeshow?id="+id+"&&arrivalId="+arrivalId,
			dataType : "json",
			success : function(data) {
			config.unload();
			if (data.code == "0000") {
				$("body").message(
						{
							type : 'success',
							content : '关联成功'
						});
				nDialog.remove();
			}else{
				$("body").message(
						{
							type : 'error',
							content : '关联失败'
						});
			}
			}
		});
	}
	
	function copyToForeshow(id,shipRefId){
		config.load();
		$.ajax({
			async : false,
			type : "get",
			url : config.getDomain()
					+ "/arrivalForeshow/checkForeshow?id="+id,
			dataType : "json",
			success : function(data) {
			config.unload();
				if (data.code == "0000") {
					if(data.map.count>0){
						$(this).confirm({
							content : '已有该船期预告，是否覆盖？',
							concel:false,
							callBack : function() {
								config.load();
								//更新船期预告
								$.ajax({
									async : false,
									type : "get",
									url : config.getDomain()
											+ "/arrivalForeshow/updateForeshowBySQL?arrivalId="+id,
									dataType : "json",
									success : function(data) {
									config.unload();
									if (data.code == "0000") {
										$("body").message(
												{
													type : 'success',
													content : '覆盖成功'
												});
									}else{
										$("body").message(
												{
													type : 'error',
													content : '覆盖失败'
												});
									}
									}
								});
								
								
							}
						});
					}else{
						config.load();
						
						$.ajax({
							async : false,
							type : "get",
							url : config.getDomain()
									+ "/arrivalForeshow/list?arrivalStatus=4&shipRefId="+shipRefId,
							dataType : "json",
							success : function(data) {
							config.unload();
							if (data.code == "0000") {
//								alert(data.data);
								if(data.data.length>0){
									$.get(config.getResource() + "/pages/inbound/arrivalForeshow/search_connect.jsp")
									.done(function(ndata) {
										dialog = $(ndata);
										initForeshowDialog(dialog,data,id);
										nDialog=dialog;
										dialog.modal({
											keyboard : true
										});
									});

								}else{

									//添加船期预告
									$.ajax({
										async : false,
										type : "get",
										url : config.getDomain()
												+ "/arrivalForeshow/addForeshowBySQL?arrivalId="+id,
										dataType : "json",
										success : function(data) {
										config.unload();
										if (data.code == "0000") {
											$("body").message(
													{
														type : 'success',
														content : '添加成功'
													});
										}else{
											$("body").message(
													{
														type : 'error',
														content : '添加失败'
													});
										}
										}
									});
								}
							}else{
								$("body").message(
										{
											type : 'error',
											content : '系统错误'
										});
							}
							}
						});
						
					}
				}else{
					$("body").message(
							{
								type : 'error',
								content : '系统错误'
							});
				}
			
			}
		});
			
	}
	
	
	function initForeshowDialog(dialog,data,arrivalId){
		var columns = [  
		   		    {
		   			
		   			title : "船名",
		   			name : "shipName",
		   			render: function(item, name, index){
//						return "<a href='javascript:void(0)' onclick='javascript:ArrivalForeshow.editForeshow("+index+")' >"+item.shipName+"/"+item.shipRefName;+"</a>";
						return item.shipRefName+"/"+item.shipName;
				}
		   		    },
		   			{
			   			title : "预计靠泊日期",
			   			name : "shipName",
			   			render: function(item, name, index){
							if(item.sAnchorTime){
								return item.sAnchorTime.split(" ")[0];
							}
						}
		   		},
		   		{
		   			title : "操作",
		   			name : "shipName",
		   			render: function(item, name, index){
		   				return "<shiro:hasPermission name='AARRIVALPLANUPDATE'><a href='javascript:void(0)' onclick='Arrival.updateForeshow("+item.id+","+arrivalId+")'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='选择'></span></a></shiro:hasPermission>";
						
					}
	   		}];
		
		/*解决id冲突的问题*/
		dialog.find('[data-role="searchForeshow"]').grid({
			
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			isUserLocalData : true,
			localData : data.data
//			url : config.getDomain()+"/arrivalForeshow/list",
			
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
																		addDialog.find(".clientId").val(dialog.find(".name").val());
																		addDialog.find(".clientId").attr("data",data.map.id);
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
	
	
	
	
	//货批拆分
	function doSqlitCargo(cargoId,goodsTotal,status){
		if(status==8){
			$(this).confirm({
				content : '该货批正在进行数量确认，暂时无法拆分!',
				concel:true,
				callBack : function() {
				}
			});
		}else{
			$.get(config.getResource()+"/pages/inbound/arrival/sqlitCargo.jsp").done(function(data){
				dialog = $(data) ;
				initSqlitCargo(dialog,cargoId,goodsTotal,status);
				dialog.modal({
					keyboard: true
				});
			});
			
		}
		
		
	}
	//货批拆分
	function initSqlitCargo(dialog,cargoId,goodsTotal,status){
		config.load();
		$.ajax({
			 async:false,
	  			type : "get",
	  			url : config.getDomain()+"/client/select",
	  			dataType : "json",
	  			success : function(data) {
	  				dialog.find('#dClientId').typeahead({
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
	  						dialog.find('#dClientId').removeAttr("data");
	  						dialog.find('#dClientId').val("");
	  					}else{
	  						dialog.find('#dClientId').attr("data",client.id)
	  					}
	  				}
	  				});
	  			}
	  		});
		
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/arrivalPlan/serchGoods?cargoId="+cargoId,
  			dataType : "json",
  			success : function(data) {
  				config.unload();
				if (data.code == "0000") {
					dialog.find("#sqlitCargoTable").children("tbody").children("tr").remove();
					var goods = "";
					var cargoGoodsTotal=0;
					for (var i = 0; i < data.data.length; i++) {
						var goodsInfo = data.data[i];
						cargoGoodsTotal+=parseFloat(goodsInfo.goodsTotal);
							goods += "<tr>";
							goods += "<input type='hidden' id='goodsId' value='"+goodsInfo.id+"' />";
							goods += "<td><input type='checkbox' class='checkboxes' name='chk_list'/></td>";
							goods += "<td class='code'>"+goodsInfo.code+"</td>";
							goods += "<td class='goodsTotal'>"+goodsInfo.goodsTotal+"</td>";
							goods += "<td class='goodsCurrent'>"+goodsInfo.goodsCurrent+"</td>";
							if(goodsInfo.goodsCurrent<goodsInfo.goodsTotal){
								goods += "<td class='goodsTotal'>"+"已交易的货体无法拆分"+"</td>";
							}else{
								goods += "<td><input type='text'  onkeyup='config.clearNoNum(this)' maxlength='10'  class='newCargoCount' value='0' /></td>";
							}
							goods += "</tr>";
					}
					dialog.find("#sqlitCargoTable").children("tbody").append(goods);
					
					var noGoodsCargo="";
					noGoodsCargo += "<tr>";
					noGoodsCargo += "<td><input type='checkbox' class='checkboxes' name='chk_list'/></td>";
					noGoodsCargo += "<td class='code'>"+"货批剩余数量"+"</td>";
					noGoodsCargo += "<td class='goodsTotal'>"+util.FloatSub(goodsTotal,cargoGoodsTotal)+"</td>";
					noGoodsCargo += "<td></td>";
					noGoodsCargo += "<td><input type='text'  onkeyup='config.clearNoNum(this)'  class='newCargoCount' value='0' /></td>";
					noGoodsCargo += "</tr>";
					dialog.find("#sqlitCargoTable").children("tbody").append(noGoodsCargo);
					
					dialog.find("#sqlitCargoTable").children("tbody").each(function(){
						$(this).find('.checkboxes').change(function(){
							var count=0;
							dialog.find('input[type="checkbox"][name="chk_list"]:checked').each(function(){
								var _tr = $(this).parents("tr");
									 count = parseFloat(util.FloatAdd(parseFloat(count),parseFloat($(_tr).find(".newCargoCount").val())));
								});
							dialog.find("#count").text(count);
							dialog.find("#dGoodsTotal").val(count);
						});
						
						$(this).find('.newCargoCount').keyup(function(){
							var count=0;
							dialog.find('input[type="checkbox"][name="chk_list"]:checked').each(function(){
								var _tr = $(this).parents("tr");
									 count = parseFloat(util.FloatAdd(parseFloat(count),parseFloat($(_tr).find(".newCargoCount").val())));
								});
							dialog.find("#count").text(count);
							dialog.find("#dGoodsTotal").val(count);
						});
					});
				}else {
	        		$(this).confirm({
	    				content : '货体检索失败!',
	    				concel:true,
	    				callBack : function() {
	    				}
	    			});
				}
				
  			}
		});
		
		//添加货批
		dialog.find(".button-add-cargo").click(function() {
			
			if(config.validateForm(dialog.find(".sqlitCargo"))){
				
			
			var cando=true;
			var noCheck=true;
			dialog.find("#sqlitCargoTable").children("tbody").each(function(){
					dialog.find('input[type="checkbox"][name="chk_list"]:checked').each(function(){
						noCheck=false;
						var _tr = $(this).parents("tr");
						if(parseFloat($(_tr).find(".newCargoCount").val())>parseFloat($(_tr).find(".goodsTotal").text())){
			        		$(this).confirm({
			    				content : '拆分不能大于原来的数量，请重新输入！',
			    				concel:true,
			    				callBack : function() {
			    				}
			    			});
							cando=false;
							return;
						}
						});
			});
			if(noCheck){
				$(this).confirm({
    				content : '请至少选择一个货体！',
    				concel:true,
    				callBack : function() {
    				}
    			});
				return;
			}
			if(cando){
				var isCreateGoods=0;
				
				if(status==9){
					isCreateGoods=1;
				}
				
//				if($("#isCreateGoods").is(':checked')){
//				}
				var goodsIdList="";
				var goodsCount="";
				dialog.find('input[type="checkbox"][name="chk_list"]:checked').each(function(){
					var _tr = $(this).parents("tr");
					if(goodsIdList == ""){
						if($(_tr).find("#goodsId").val()){
							goodsIdList += $(_tr).find("#goodsId").val(); 
						}
					}else{
						if($(_tr).find("#goodsId").val()){
						goodsIdList += ","+$(_tr).find("#goodsId").val(); 
						}
					}
					if(goodsCount == ""){
						if($(_tr).find("#goodsId").val()){
						goodsCount += $(_tr).find(".newCargoCount").val(); 
						}
					}else{
						if($(_tr).find("#goodsId").val()){
						goodsCount += ","+$(_tr).find(".newCargoCount").val(); 
						}
					}
				});
						$(this).confirm({
							content : '确定要拆分货批?',
							callBack : function() {
								config.load();
								$.ajax({
									type : "post",
									url : config.getDomain()+"/arrivalPlan/sqlitCargo",
									data : {
										"cargoId" :cargoId,
										"goodsIdList": goodsIdList,
										"goodsCount" : goodsCount,
										"cargoCount" : dialog.find("#dGoodsTotal").val(),
										"isCreateGoods" : isCreateGoods,
										"clientId" : dialog.find("#dClientId").attr("data")
									},
									// data:JSON.stringify(sendGroup),
									dataType : "json",
									success : function(data) {
										config.unload();
										if (data.code == "0000") {
											$("body").message({
												type : 'success',
												content : '添加成功'
											});
											dialog.remove();
											initEdit($(".planId").val());
										}else {
											$("body").message({
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
		});
		
		
	}
	
	//预入库货体列表
	function doPredict(productId,cargoId,goodsTotal){
		
  						$.get(config.getResource()+"/pages/inbound/arrival/predict.jsp").done(function(data){
  							dialog = $(data) ;
  								initModal(productId,dialog,cargoId,goodsTotal);
  								dialog.find("#goodsTotal").text(goodsTotal);
  								dialog.find('[data-dismiss="modal"]').click(function(){
  									dialog.remove();
  								});
  							dialog.modal({
  								keyboard: true
  							});
  						});
  						
  						
		
		
	}
	
	//预入库货体列表
	function AddPredict(productId,oldDialog,cargoId,goodsTotal){
		$.get(config.getResource()+"/pages/inbound/arrival/predictAdd.jsp").done(function(data){
			dialog = $(data) ;
			dialog.find('[data-dismiss="modal"]').click(function(){
				dialog.remove();
			});
				initModalAdd(productId,oldDialog,dialog,cargoId,goodsTotal);
			dialog.modal({
				keyboard: true
			});
		});
	}
	function initModalAdd(productId,oldDialog,dialog,cargoId,goodsTotal){
		config.load();
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/tank/list?productId="+productId,
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
			
			if(config.validateForm(dialog.find(".predictform"))){
				if(parseFloat(dialog.find("#dGoodsTotal").val())>parseFloat(goodsTotal)){
					$(this).confirm({
	    				content : '预入库数量不能大于货批数量，请重新填写',
	    				concel:true,
	    				callBack : function() {
	    				}
	    			});
					return;
				}
					//预入库
					$.ajax({
			  			type : "post",
			  			url : config.getDomain()+"/arrivalPlan/predict",
			  			dataType : "json",
			  			data:{
			  				"cargoId":cargoId,
			  				"goodsTotal":dialog.find("#dGoodsTotal").val(),
			  				"tankId":dialog.find("#dTankId").attr("data")
			  			},
			  			success : function(data) {
			  				if (data.code == "0000") {
								$("body").message({
									type: 'success',
									content: '预入库成功！请前往入库确认放行。'
								});
								dialog.remove();
								oldDialog.find('[data-role="goodsGrid"]').grid('refresh');
								getSure(nArrivalId,false);
							} else {
								$("body").message({
									type: 'error',
									content: '预入库失败'
								});
							}
			  			}
					});
			}
			});
	}
	
	
	//预入库货体列表
	function EditPredict(productId,goodsId,oldDialog,cargoId,tankId,goodsTotal,tankCode,goodsInPass,mGoodsTotal){
		$.get(config.getResource()+"/pages/inbound/arrival/predictAdd.jsp").done(function(data){
			dialog = $(data) ;
				initModalEdit(productId,goodsId,oldDialog,dialog,cargoId,tankId,goodsTotal,tankCode,goodsInPass,mGoodsTotal);
			dialog.modal({
				keyboard: true
			});
		});
	}
	function initModalEdit(productId,goodsId,oldDialog,dialog,cargoId,tankId,goodsTotal,tankCode,goodsInPass,mGoodsTotal){
			config.load();
		$.ajax({
  			type : "get",
  			async:false,
  			url : config.getDomain()+"/tank/list?productId="+productId,
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
		
		
		dialog.find("#dTankId").val(tankCode);
		dialog.find("#dTankId").attr("data",tankId);
		dialog.find("#dGoodsTotal").val(goodsTotal);
		
		
			dialog.find(".button-ok").click(function(){
			
			if(config.validateForm(dialog.find(".predictform"))){
				if(parseFloat(dialog.find("#dGoodsTotal").val())>parseFloat(mGoodsTotal)){
					$(this).confirm({
	    				content : '预入库数量不能大于货批数量，请重新填写',
	    				concel:true,
	    				callBack : function() {
	    				}
	    			});
					return;
				}
				if(dialog.find("#dGoodsTotal").val()<goodsInPass){
					$(this).confirm({
	    				content : '修改数量不能小于已放行数',
	    				concel:true,
	    				callBack : function() {
	    				}
	    			});
				}else{
					
					//预入库
					$.ajax({
						type : "post",
						url : config.getDomain()+"/arrivalPlan/predict",
						dataType : "json",
						data:{
							"goodsId":goodsId,
							"cargoId":cargoId,
							"goodsTotal":dialog.find("#dGoodsTotal").val(),
							"tankId":dialog.find("#dTankId").attr("data")
						},
						success : function(data) {
							if (data.code == "0000") {
								$("body").message({
									type: 'success',
									content: '修改成功！请前往入库确认放行。'
								});
								dialog.remove();
								oldDialog.find('[data-role="goodsGrid"]').grid('refresh');
							} else {
								$("body").message({
									type: 'error',
									content: '预入库失败'
								});
							}
						}
					});
				}
			}
			});
	}
	
	
	
	function initModal(productId,dialog,cargoId,goodsTotal){
		
		
		var columns = [  {
			title : "货体代码",
			name : "code"
		},{
			title : "货体总量(吨)",
			name : "goodsTotal"
		},{
			title:"现存量(吨)",
			name:"goodsCurrent"
		},{
			title : "罐号",
			name : "tankCode"
		}, {
			title : "入库放行量(吨)",
			name : "goodsInPass"
		},{
			title : "出库放行量(吨)",
			name : "goodsOutPass"
		}];
		
		
		dialog.find('[data-role="goodsGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : false,
//			data　: getData()
//			isUserLocalData : true,
//			localData : $.parseJSON(getData())
			url : config.getDomain()+"/arrivalPlan/serchGoods?cargoId="+cargoId+"&isPredict=1"
		});
         
		
		
		
		dialog.find(".btn-add").click(function() {
			var mData=dialog.find('[data-role="goodsGrid"]').getGrid().getAllItems();
			goodsTotal=dialog.find("#goodsTotal").text();
			if(mData&&mData.length>0){
				for(var item in mData){
					if(mData[item].goodsTotal){
					goodsTotal=util.FloatSub(goodsTotal,mData[item].goodsTotal);
					}
				}
			}
			
			
			//预入库
			$.ajax({
	  			type : "post",
	  			url : config.getDomain()+"/arrivalPlan/serchGoods?cargoId="+cargoId+"&isPredict=1",
	  			dataType : "json",
	  			data:{
	  				"cargoId":cargoId,
	  			},
	  			success : function(data) {
	  				if (data.code == "0000") {
	  					if(data.data.length>0){
	  						$("body").message({
	  							type: 'error',
	  							content: '该货批已有预入库货体，请前往入库确认修改预入库入库量！'
	  						});
	  					}else{
	  						
	  						
	  						AddPredict(productId,dialog,cargoId,goodsTotal);
	  						
	  					}
	  					
					} else {
						$("body").message({
							type: 'error',
							content: '系统错误，请联系管理员'
						});
					}
	  			}
			});
			
			
		});
		
		//删除
		dialog.find(".btn-remove").click(function() {
			var data = dialog.find('[data-role="goodsGrid"]').getGrid().selectedRowsIndex();
			var $this = dialog.find('[data-role="goodsGrid"]');
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}
			var cando=true;
			var ids="";
				 $.each(dialog.find('[data-role="goodsGrid"]').getGrid().selectedRows(), function (i, role) {
			        	if(role.goodsCurrent<role.goodsTotal){
			        		$this.confirm({
			    				content : '已交易过的货体无法删除！',
			    				concel:true,
			    				callBack : function() {
			    				}
			    			});
			        		cando=false;
			        		return false;
			        	}
			        	ids+=role.id+",";
			        });
				 ids = ids.substring(0, ids.length - 1);
				 if(cando){
					 $this.confirm({
						 content : '确定要撤销所选记录吗?',
						 callBack : function() {
							 $.ajax({
									type : "post",
									url : config.getDomain()+"/arrivalPlan/deletePredict",
									dataType : "json",
									data:{
										"ids":ids,
									},
									success : function(data) {
										if (data.code == "0000") {
											$("body").message({
												type: 'success',
												content: '删除成功'
											});
											dialog.find('[data-role="goodsGrid"]').grid('refresh');
										} else {
											$("body").message({
												type: 'error',
												content: '删除失败'
											});
										}
									}
								});
						 }
					 });
				 }
				
		});
		
		//修改
		dialog.find(".btn-modify").click(function() {
			var data =dialog.find('[data-role="goodsGrid"]').getGrid().selectedRows();
			var $this = $(this);
			if (data.length == 0) {
				$("body").message({
					type : 'warning',
					content : '请选择要修改的记录'
				});
				return;
			}
			
			var cando=true;
				 $.each(dialog.find('[data-role="goodsGrid"]').getGrid().selectedRows(), function (i, role) {
			        	if(role.goodsCurrent<role.goodsTotal){
			        		$this.confirm({
			    				content : '已交易过的货体无法修改！',
			    				concel:true,
			    				callBack : function() {
			    				}
			    			});
			        		cando=false;
			        		return false;
			        	}
			        });
				 if(cando){
			
			dataGrid = $(this);
//			$.get(config.getResource()+"/pages/contract/intent/edit.jsp").done(function(_data){
//				dataGrid = _data;
//			});
			var mData=dialog.find('[data-role="goodsGrid"]').getGrid().getAllItems();
			var goodsTotal=dialog.find("#goodsTotal").text();
			if(mData&&mData.length>0){
				for(var item in mData){
					if(mData[item].goodsTotal){
					goodsTotal=util.FloatSub(goodsTotal,mData[item].goodsTotal);
					}
				}
			}
			
			EditPredict(productId,data[0].id,dialog,cargoId,data[0].tankId,data[0].goodsTotal,data[0].tankCode,data[0].goodsInPass,util.FloatAdd(data[0].goodsTotal,goodsTotal));
				 }
		});
		
	}
	
	function shipInfoDialog(){
		$.get(config.getResource()+"/pages/inbound/arrival/shipInfoDialog.jsp").done(function(data){
			dialog = $(data) ;
			dialog.modal({
				keyboard: true
			});
			config.load();
			 $.ajax({
				 async:false,
		  			type : "get",
		  			url : config.getDomain()+"/ship/select?id="+$(".shipId").attr("data"),
		  			dataType : "json",
		  			success : function(data) {
		  				config.unload();
		  				if(data.code=="0000"){
		  					dialog.find(".shipLenth").val(data.data[0].shipLenth);
		  					dialog.find(".shipWidth").val(data.data[0].shipWidth);
		  					dialog.find(".shipDraught").val(data.data[0].shipDraught);
		  					dialog.find(".loadCapacity").val(data.data[0].loadCapacity);
		  					dialog.find(".shipRegistry").val(data.data[0].shipRegistry);
		  					dialog.find(".buildYear").val(data.data[0].buildYear);
		  					dialog.find(".grossTons").val(data.data[0].grossTons);
		  					dialog.find(".netTons").val(data.data[0].netTons);
		  					dialog.find(".owner").val(data.data[0].owner);
		  					dialog.find(".manager").val(data.data[0].manager);
		  					dialog.find(".contactName").val(data.data[0].contactName);
		  					dialog.find(".contactPhone").val(data.data[0].contactPhone);
		  				}
		  			}
			 });
		});
	}
	
	
	function getData(){
		$.ajax({
			type : "get",
			url : config.getDomain()+"/arrivalPlan/list?id=1",
			dataType : "json",
			success : function(data) {
				return data.data[0];
			}
		});
			}
	
	function openModal(type){
//		$('#ajax').load(config.getResource()+"/pages/inbound/arrival/addCustomer.jsp");
//		$('#ajax').modal('show');
		$.get(config.getResource()+"/pages/inbound/arrival/addCustomer.jsp").done(function(data){
			dialog = $(data) ;
			addDialog=dialog;
			initFormIput(dialog);
			initDialog(dialog,type);
			dialog.modal({
				keyboard: true
			});
		});
	}
	

	
	function openGoods(obj){
		var _id = $(obj).attr("data");
		var _plan = $("#"+_id);
		if($(_plan).is(":hidden")) {
			$(obj).attr("class","row-details-open");
			$(_plan).show();
		}else {
			$(_plan).hide();
			$(obj).attr("class","row-details-close");
		}
	}
	
	
	//得到未确认的计划
	function getPlan(id){
		
		$('#goods-table .group-checkable').change(function() {
			var set = $(this).attr("data-set");
			var checked = $(this).is(":checked");
			$(set).each(function() {
				if (checked) {
					$(this).attr("checked", true);
				} else {
					$(this).attr("checked", false);
				}
			});
		});
		
		config.load();
		$.ajax({
			type : "get",
			url : config.getDomain()+"/arrivalPlan/getplan?arrivalId="+id,
			dataType : "json",
			success : function(data) {
				config.unload();
				var planList=data.data;
				$("#goods-table").children("tbody").each(function(){
					$(this).children("tr").remove();
				});
				$("#plantotal-table").children("tbody").each(function(){
					$(this).children("tr").remove();
				});
				goodsPlanList=[];
				if(planList!=""){
					var goods="";
					for(var i =0 ;i<planList.length;i++){
						var productName=planList[i].productName==null?"":planList[i].productName;
						var tradeTypeName=planList[i].tradeTypeName==null?"":planList[i].tradeTypeName;
						var clientName=planList[i].clientName==null?"":planList[i].clientName;
						var isDeclareCustom=planList[i].isDeclareCustom==0?"否":"是";
						var isCustomAgree=planList[i].isCustomAgree==0?"否":"是";
						var customLading=planList[i].customLading==null?"":planList[i].customLading;
						var customLadingCount=planList[i].customLadingCount==null?"":planList[i].customLadingCount;
						goods += "<tr>";
						goods += "<input type='hidden' id='planId' value='"+planList[i].id+"' />";
						goods += "<input type='hidden' id='shipId' value='"+planList[i].shipId+"' />";
						goods += "<td><input type='checkbox' class='checkboxes' name='chk_list'/></td>";
						goods +="<td id='clientId' data='"+planList[i].clientId+"'>"+clientName+"</td>";
						goods +="<td id='productId' data='"+planList[i].productId+"'>"+productName+"</td>";
						goods +="<td id='tradeType' data='"+planList[i].tradeType+"'>"+tradeTypeName+"</td>";
						goods +="<td id='goodTotal' data='"+planList[i].goodsTotal+"'>"+planList[i].goodsTotal+"</td>";
						goods +="<td id='isDeclareCustom' data='"+planList[i].isDeclareCustom+"'>"+isDeclareCustom+"</td>";
//						goods +="<td id='isCustomAgree' data='"+planList[i].isCustomAgree+"'>"+isCustomAgree+"</td>";
						goods +="<td id='customLading' data='"+customLading+"'>"+customLading+"</td>";
						goods +="<td id='customLadingCount' data='"+customLadingCount+"'>"+customLadingCount+"</td>";
						goods +="<td id='requirement' data='"+planList[i].requirement+"'>"+planList[i].requirement+"</td>";
						goods +="<td>" +
								"<shiro:hasPermission name='AAPCARGOMODIFY'><a href='javascript:void(0)' class='btn btn-xs red' onclick='Arrival.removePlan("+planList[i].id+")'> <span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a></shiro:hasPermission>" +
//								"<shiro:hasPermission name='AAPGOODSVERIFY'><a href='javascript:void(0)' class='btn btn-xs blue' onclick='Arrival.openPrintPlan("+planList[i].id+","+id+")' class='blue'> <span class=' fa fa-file-text-o' title='打印船舶进港确认单'></span></a></shiro:hasPermission>"+
//								"<shiro:hasPermission name='AAPGOODSVERIFY'><a href='javascript:void(0)' class='btn btn-xs blue' onclick='Arrival.openPrintconfirm("+planList[i].id+","+id+")' class='blue'> <span class=' fa fa-file-text-o' title='打印船舶进港工作联系单'></span></a></shiro:hasPermission>"+
								"<shiro:hasPermission name='AAPGOODSVERIFY'><a href='javascript:void(0)' class='btn btn-xs blue' onclick='Arrival.confirmPlan("+planList[i].id+","+id+","+planList[i].shipId+")' class='blue'> <span class='glyphicon glyphicon glyphicon-check' title='审批'></span></a></shiro:hasPermission>"+
								
								"</td>";
						goods +="</tr>";
						
						var doAdd=true;
						 Array.prototype.contains = function(item){
		                	   return RegExp("\\b"+item+"\\b").test(this);
		                   };
							if(goodsPlanList.contains(planList[i].productId)){
								doAdd=false;
							}
						

								if (doAdd) {
									goodsPlanList.push(planList[i].productId);
									var total = "<tr>";
									total += "<td id='productId' data='"
											+ planList[i].productId + "' >"
											+ productName + "</td>";
									total += "<td id='goodTotal' class='"
											+ planList[i].productId + "'>"
											+ planList[i].goodsTotal + "</td>";
									total += "</tr>";
									$("#plantotal-table").children("tbody")
											.append(total);
								}else{
							var _this=$("#plantotal-table").children("tbody");
							$(_this).find("."+planList[i].productId+"").text((util.FloatAdd(parseFloat($(_this).find("."+planList[i].productId+"").text()),parseFloat(planList[i].goodsTotal))));
						}
						
					}
					$("#goods-table").children("tbody").append(goods);
					
					
				}
			}
		});
	}
	
	//得到确认过的计划（货批）
	function getSure(id,doPlan){
		config.load();
		$.ajax({
			type : "get",
			url : config.getDomain()+"/arrivalPlan/getsure?arrivalId="+id,
			dataType : "json",
			success : function(data) {
				config.unload();
				var sureList=data.data;
				$("#goods-table-sure").children("tbody").each(function(){
					$(this).children("tr").remove();
				});
				$("#suretotal-table").children("tbody").each(function(){
					$(this).children("tr").remove();
				});
				sureLength=sureList.length;
				if(doPlan){
					getPlan(id);
				}
				goodsSureList=[];
				
				mSureList=sureList;
				
				if(sureList!=""){
					var contract;
					var contract1;
				
							for(var i =0 ;i<sureList.length;i++){
								var productName=sureList[i].productName==null?"":sureList[i].productName;
								var typeName=sureList[i].typeName==null?"":sureList[i].typeName;
								var clientName=sureList[i].clientName==null?"":sureList[i].clientName;
								var description=sureList[i].requirement==null?"":sureList[i].requirement;
								var originalArea=sureList[i].originalArea==null?"":sureList[i].originalArea;
								var storageType = sureList[i].storageType==null?"":sureList[i].storageType;
								var isDeclareCustom=sureList[i].isDeclareCustom==0?"否":"是";
								var isCustomAgree=sureList[i].isCustomAgree==0?"否":"是";
								var customLading=sureList[i].customLading==null?"":sureList[i].customLading;
								var customLadingCount=sureList[i].customLadingCount==null?"":sureList[i].customLadingCount;
								var goodsInspect=sureList[i].goodsInspect==null?"":sureList[i].goodsInspect;
								var goods = "<tr>";
//								goods +="<td id='clientId' data='"+sureList[i].clientId+"'>"+clientName+"</td>";
								goods+="<td><input type='text' maxlength='32' style='width:280px' class='form-control  clientIdSpan' readonly value='"+clientName+"' data='"+sureList[i].clientId+"'/>";
								goods+="<input type='text' class='form-control  clientId' data="+sureList[i].clientId+" name='clientId' data-provide='typeahead' value='"+clientName+"' style='display:none;width:280px'/></td>";
							
								
								goods +="<td id='productId' data='"+sureList[i].productId+"'><div style='width:50px;'></div>"+productName+"</td>";
//								goods +="<td id='tradeType' data='"+sureList[i].taxType+"'><div style='width:80px;'></div>"+typeName+"</td>";
								
								
								goods +="<td><input type='text' maxlength='32' style='width:100px'  class='form-control tradeType' readonly value='"+typeName+"' /></td>";
//								
								
								goods +="<td><input type='text' maxlength='10'  style='width:100px' onkeyup='config.clearNoNum(this)' class='form-control amount' readonly value="+sureList[i].goodsPlan+" /></td>";
//								goods +="<td id='goodTotal' data='"+sureList[i].goodsPlan+"'>"+sureList[i].goodsPlan+"</td>";
								
								if(sureList[i].code==null){
									goods+="<td><input type='text' style='width:200px' class='form-control  codeSpan' readonly value='无合同批次号' />";
									goods+="<input type='text' maxlength='16' class='form-control  code' name='code' data-provide='typeahead'  placeholder='请输入合同批次号' style='display:none;width:200px'/></td>";
								
								}
								if(sureList[i].code!=null){
									goods+="<td><input type='text' style='width:200px' class='form-control  codeSpan' readonly value='"+sureList[i].code+"' />";
									goods+="<input type='text' maxlength='16' class='form-control  code' name='order' data-provide='typeahead' value='"+sureList[i].code+"'  placeholder='请输入合同批次号' style='display:none;width:200px'/></td>";

								}
								
								if(sureList[i].contractId==0){
									goods+="<shiro:hasPermission name='AGETCONTRACT'><td><span id='s_code'><input type='text' style='width:200px' class='form-control  orderSpan' readonly value='未关联合同' />";
									goods+="<input type='text' maxlength='16' class='form-control  order' name='order' data-provide='typeahead'  placeholder='请输入合同' style='display:none;width:200px'/></span></td></shiro:hasPermission>";
									
								}
								if(sureList[i].contractId!=0){
									goods+="<shiro:hasPermission name='AGETCONTRACT'><td><span id='s_code'><input type='text' style='width:200px' class='form-control  orderSpan' readonly value='"+sureList[i].contractCode+"-"+sureList[i].contractTitle+"' />";
									goods+="<input type='text' maxlength='16' class='form-control  order' name='order' data-provide='typeahead' value='"+sureList[i].contractCode+"-"+sureList[i].contractTitle+"' data='"+sureList[i].contractId+"' placeholder='请输入合同' style='display:none;width:200px'/></span></td></shiro:hasPermission>";

								}
//								goods+="<div class='col-md-8' id='select-contract'>" +contract1+"</div></td>";
								
								if(sureList[i].cargoAgentId==0){
									goods+="<td><input type='text' style='width:100px' class='form-control cargoAgentSpan' readonly value='未选择货代' />";
									goods+="<input type='text' maxlength='16' class='form-control  cargoAgent' name='cargoAgent' data-provide='typeahead'  placeholder='请输入货代' style='display:none;width:100px'/></td>";
									
			 					}
								if(sureList[i].cargoAgentId!=0){
									goods+="<td><input type='text' style='width:100px' class='form-control  cargoAgentSpan' readonly value='"+sureList[i].cargoAgentCode+"' />";
									goods+="<input type='text' maxlength='16'  class='form-control  cargoAgent' name='cargoAgent' value='"+sureList[i].cargoAgentCode+"' data='"+sureList[i].cargoAgentId+"' data-provide='typeahead'  placeholder='请输入货代' style='display:none;width:100px'/></td>";

								}
//								//产地
//								if(sureList[i].originalArea==null||sureList[i].originalArea=="null"){
//									goods+="<td><input type='text' style='width:100px' class='form-control originalAreaSpan' readonly value='未选择产地' />";
//									goods+="<input type='text' maxlength='16' class='form-control  originalArea' name='originalArea' data-provide='typeahead'  placeholder='请输入产地' style='display:none;width:100px'/></td>";
//									
//								}
//								if(sureList[i].originalArea!=null&&sureList[i].originalArea!="null"){
//									goods+="<td><input type='text' style='width:100px' class='form-control  originalAreaSpan' readonly value='"+sureList[i].originalArea+"' />";
//									goods+="<input type='text' maxlength='16' class='form-control  originalArea' name='originalArea' value='"+sureList[i].originalArea+"'  data-provide='typeahead'  placeholder='请输入产地' style='display:none;width:100px'/></td>";
//									
//								}
								
								
								//仓储性质
								if(sureList[i].storageType==null||sureList[i].storageType=="null"||sureList[i].storageType==0){
									goods+="<td><input type='text' style='width:100px' class='form-control storageTypeSpan' readonly value='未填写性质' />";
//									goods+="<input type='text' maxlength='16' class='form-control  storageType' name='storageType' data-provide='typeahead'  placeholder='请输入性质' style='display:none;width:100px'/></td>";
									goods+='<select name="storageType" style="display:none;width:100px" class="form-control storageType"><option selected value="0"></option><option value="1">一般</option><option value="2">保税临租</option><option value="3">包罐</option><option    value="5">通过</option></select>';
										
								}
								else if(sureList[i].storageType!=null&&sureList[i].storageType!="null"&&sureList[i].storageType!=0){
									var sType="";
									if(sureList[i].storageType==1){
										sType='一般';
									}
									if(sureList[i].storageType==2){
										sType='保税临租';
									}
									if(sureList[i].storageType==3){
										sType='包罐';
									}
									if(sureList[i].storageType==4){
										sType='临租';
									}
									if(sureList[i].storageType==5){
										sType='通过';
									}
									
									goods+="<td><input type='text' style='width:100px' class='form-control  storageTypeSpan' data='"+sureList[i].storageType+"' readonly value='"+sType+"' />";
//									goods+="<input type='text' maxlength='16' class='form-control  storageType' name='storageType' value='"+sureList[i].storageType+"'  data-provide='typeahead'  placeholder='请输入性质' style='display:none;width:100px'/></td>";
									goods+='<select name="storageType" style="display:none;width:100px" class="form-control storageType"><option selected value="0"></option><option value="1">一般</option><option value="2">保税临租</option><option value="3">包罐</option><option    value="5">通过</option></select>';
										
								}
								
								
								//商检单位
								if(sureList[i].inspectAgentIds==null||sureList[i].inspectAgentNames==null){
									goods+="<td><input type='text' style='width:100px' data='' class='form-control  inspectAgentSpan' readonly value='无选择商检单位' />";
//									goods+="<input type='text' maxlength='16' class='form-control  inspectAgent' name='inspectAgent' data-provide='typeahead'  placeholder='请输入商检单位' style='display:none;width:100px'/></td>";
									goods+='<div class="select2-container select2-container-multi form-control select2 inspectAgent"  style="display:none;width:200px" id="inspectAgentSpan"></div>';
									
								}
								if(sureList[i].inspectAgentIds!=null&&sureList[i].inspectAgentNames!=null){
									goods+="<td><input type='text' style='width:100px' data='"+sureList[i].inspectAgentIds+"' class='form-control  inspectAgentSpan' readonly value='"+sureList[i].inspectAgentNames+"' />";
//									goods+="<input type='text' maxlength='16' class='form-control  inspectAgent' name='inspectAgent' data-provide='typeahead' value='"+sureList[i].inspectAgentName+"' data='"+sureList[i].inspectAgentId+"'  placeholder='请输入商检单位' style='display:none;width:100px'/></td>";
									goods+='<div class="select2-container select2-container-multi form-control select2 inspectAgent"  style="display:none;width:200px" id="inspectAgent"></div>';
									
								}
								
									goods+="<shiro:hasPermission name='AGETCONTRACT'><td id='tdspass' class='tdspass'><span id='s_code'><input type='text' style='width:100px' onkeyup='config.clearNoNum(this)' maxlength='10' class='form-control  passShipSpan' readonly value='"+goodsInspect+"' />";
									goods+="<input type='text' maxlength='16' class='form-control  passShip' name='passShip' data-provide='typeahead' value='"+goodsInspect+"' data='"+goodsInspect+"' placeholder='请输入通过船商检量' style='display:none;width:100px'/></span></td></shiro:hasPermission>";
								
								
								goods+="<td><div style='width:80px;'><input type='text' style='width:60px' class='form-control  isDeclareCustomSpan' readonly value='"+isDeclareCustom+"' />";
//								goods+="<input type='text' maxlength='100' class='form-control  isDeclareCustom'  name='isDeclareCustom'  value='"+isDeclareCustom+"' style='display:none;width:100px'/></td>";
								if(isDeclareCustom=="是"){
									goods+='<select name="isDeclareCustom" style="display:none;width:60px" class="form-control isDeclareCustom"><option value="0">否</option><option selected value="1">是</option></select></div></td>'
										
								}else{
									goods+='<select name="isDeclareCustom" style="display:none;width:60px" class="form-control isDeclareCustom"><option selected value="0">否</option><option  value="1">是</option></select></div></td>'
										
								}
							
//								goods+="<td><input type='text' style='width:100px' class='form-control  isCustomAgreeSpan' readonly value='"+isCustomAgree+"' />";
//								goods+="<input type='text' maxlength='100' class='form-control  isCustomAgree'  name='isCustomAgree'  value='"+isCustomAgree+"' style='display:none;width:100px'/></td>";
								
//								if(isCustomAgree=="是"){
//									goods+='<select name="isCustomAgree" style="display:none;width:100px" class="form-control isCustomAgree"><option value="0">否</option><option selected   value="1">是</option></select>'
//									
//								}else{
//									goods+='<select name="isCustomAgree" style="display:none;width:100px" class="form-control isCustomAgree"><option selected value="0">否</option><option value="1">是</option></select>'
//										
//								}
									
								goods+="<td><input type='text' style='width:100px' class='form-control  customLadingSpan' readonly value='"+customLading+"' />";
								goods+="<input type='text' maxlength='16' class='form-control  customLading'  name='customLading'  value='"+customLading+"' style='display:none;width:100px'/></td>";
							
								goods+="<td><input type='text' style='width:100px' class='form-control  customLadingCountSpan' readonly value='"+customLadingCount+"' />";
								goods+="<input type='text' maxlength='16' class='form-control  customLadingCount'  name='customLadingCount'  value='"+customLadingCount+"' style='display:none;width:100px'/></td>";
							
								
								
								goods+="<td><input type='text' style='width:100px' class='form-control  descriptionSpan' readonly value='"+description+"' />";
								goods+="<input type='text' maxlength='100' class='form-control  description'  name='description'  value='"+description+"' style='display:none;width:100px'/></td>";
							
								if(sureList[i].cargoStatus!=0&&sureList[i].cargoStatus!=null){
									if(sureList[i].cargoStatus>=5&&sureList[i].cargoStatus<=8){
										goods +="<td id='requirement'  data='"+sureList[i].cargoStatus+"'><div style='width:50px;'></div>"+"正在入库"+"</td>";
									}else if(sureList[i].cargoStatus>=2&&sureList[i].cargoStatus<=4){
										goods +="<td id='requirement'  data='"+sureList[i].cargoStatus+"'><div style='width:50px;'></div>"+"计划入库"+"</td>";
									}else{
										goods +="<td id='requirement'  data='"+sureList[i].cargoStatus+"'><div style='width:50px;'></div>"+"已入库"+"</td>";
									}
								}else{
									goods +="<td id='requirement'  data='"+sureList[i].cargoStatus+"'><div style='width:50px;'></div>"+"计划入库"+"</td>";
								}
								goods +="<td style='vertical-align:middle;'>";
								if(config.hasPermission('AAPCARGOMODIFY')){
									goods +="<shiro:hasPermission name='AAPCARGOMODIFY'><a href='javascript:void(0)' class='btn btn-xs blue edit' onclick='Arrival.editTable(this)'> <span class='glyphicon glyphicon glyphicon-edit' title='编辑'></span></a>";
									
								}
								goods+="<a href='javascript:void(0)' class='btn btn-xs blue submit' onclick='Arrival.doEdit("+sureList[i].id+","+id+","+sureList[i].arrivalId+","+sureList[i].cargoStatus+",this)' style='display: none'><span class='glyphicon glyphicon glyphicon-ok' title='提交'></span></a>"+
								"<a href='javascript:void(0)' class='btn btn-xs blue concel' onclick='Arrival.concel(this)' style='display: none'><span class='glyphicon glyphicon glyphicon-remove' title='取消'></span></a>";
								if(sureList[i].code!=null&&((sureList[i].cargoStatus>=2&&sureList[i].cargoStatus<=8)||sureList[i].cargoStatus==null||sureList[i].cargoStatus==0)){
									if(config.hasPermission('AYURUKU')){
									goods+="<a href='javascript:void(0)' class='btn btn-xs blue predict' onclick='Arrival.doPredict("+sureList[i].productId+","+sureList[i].id+","+sureList[i].goodsPlan+")'> <span class='fa fa-sign-in ' title='预入库'></span></a>";
									}
								}
								if(config.hasPermission('ASPLITECARGO')){
								goods+="<a href='javascript:void(0)' class='btn btn-xs blue sqlitCargo' onclick='Arrival.doSqlitCargo("+sureList[i].id+","+sureList[i].goodsTotal+","+sureList[i].cargoStatus+")'> <span class='fa fa-external-link  ' title='货批拆分'></span></a></shiro:hasPermission>";
								}
								if(sureList[i].goodsCount==0&&((sureList[i].cargoStatus>=2&&sureList[i].cargoStatus<=8)||sureList[i].cargoStatus==null||sureList[i].cargoStatus==0)){
									if(config.hasPermission('AARRIVALDELETECARGO')){
									goods+="<a href='javascript:void(0)' onclick='Arrival.deleteCargo("+sureList[i].id+","+sureList[i].arrivalId+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a>"
									}
								}
								if(config.hasPermission('APRINTARRIVALCONNECT')){
								goods+="<a href='javascript:void(0)' class='btn btn-xs blue printconfirm' onclick='Arrival.printconfirm("+i+")'> <span class='fa fa-file-text-o  ' title='打印进港工作联系单'></span></a>";
								}
									if(config.hasPermission('APRINTARRIVALSURE')){
									goods+="<a href='javascript:void(0)' class='btn btn-xs blue printPlan' onclick='Arrival.printPlan("+sureList[i].id+","+i+")'> <span class='fa fa-file-text-o  ' title='打印进港确认单'></span></a>";
								}
								
								
								
								goods+="<div style='width:100px;'></div></td>";
								goods +="</tr>";
								
								
								var doAdd=true;
								 Array.prototype.contains = function(item){
				                	   return RegExp("\\b"+item+"\\b").test(this);
				                   };
									if(goodsSureList.contains(sureList[i].productId)){
										doAdd=false;
									}
								
								if(doAdd){
									goodsSureList.push(sureList[i].productId);
									var total="<tr>";
									total +="<td id='productId' data='"+sureList[i].productId+"' >"+productName+"</td>";
									total +="<td id='goodTotal' class='"+sureList[i].productId+"'>"+sureList[i].goodsPlan+"</td>";
									total+="</tr>";
									$("#suretotal-table").children("tbody").append(total);
								}else{
									var _this=$("#suretotal-table").children("tbody");
									$(_this).find("."+sureList[i].productId+"").text((util.FloatAdd(parseFloat($(_this).find("."+sureList[i].productId+"").text()),parseFloat(sureList[i].goodsPlan))));
								}
								
								
								$("#goods-table-sure").children("tbody").append(goods);
								
								if($("#isPassShip").is(':checked')){
									$("#goods-table-sure").children("tbody").children("tr").each(function(){
										$(this).find(".tdspass").show();
//										$(this).find(".passShip").show();
									});
									$("#spass").show();
								}else{
									$("#goods-table-sure").children("tbody").children("tr").each(function(){
										$(this).find(".tdspass").hide();
//										$(this).find(".passShip").hide();
									});
									$("#spass").hide();
								}
								
							}
							$("#goods-table-sure").children("tbody").children("tr").each(function(){
								var _this=this;
								var _in=$(_this).find(".storageType");
								if($(_this).find(".storageTypeSpan").attr("data")){
									
									$(_in).val($(_this).find(".storageTypeSpan").attr("data"));
								}
							});
							
							//贸易类型
							$("#goods-table-sure").children("tbody").children("tr").each(function(){
								var _this=this;
								var _in=$(_this).find(".tradeType");
							$(_in).typeahead({
		  					      source: function(query, process) {
		  					    	 process(["内贸", "外贸","保税"]);
		  					      },
		  					      noselect:function(query){
		  					    	  
		  					    	  switch(query){
		  					    	  case "内贸":
		  					    		$(_in).attr("data","1");
		  					    		break;
		  					    	case "外贸":
		  					    		$(_in).attr("data","2");
		  					    		break;
		  					    	case "保税":
		  					    		$(_in).attr("data","3");
		  					    		break;
		  					    	case "":
		  					    		$(_in).removeAttr("data");
		  					    		break;
		  					    	  }
		  					    	  
		  					      }
		  					   });
		  					});
							
							
							
							
							
							config.load();
							
							
							//合同下的所有货批
							$("#goods-table-sure").children("tbody").children("tr").each(function(){
								var _this=this;
								var _in=$(_this).find(".order");
								var _span=$(_this).find("#s_code");
								
								
								var orderId=$(_in).attr("data");
								if(orderId){
									
									$.ajax({
										async:false,
										type : "get",
										url : config.getDomain()+"/order/getCargoCode?id="+orderId,
										dataType : "json",
										success : function(data) {
											if(data.data[0].code){
												$(_span).attr("title",data.data[0].code.replace(/,/g,'\n'));
												
											}else{
												$(_span).attr("title","");
											}
										}
									});
								}
								
							});
							
							
							//合同
							$.ajax({
								async:false,
							type : "get",
							url : config.getDomain()+"/order/list?status=2&page=0&pagesize=0",
							dataType : "json",
							success : function(data) {
								$("#goods-table-sure").children("tbody").children("tr").each(function(){
									var _this=this;
									var _in=$(_this).find(".order");
									var _code=$(_this).find(".code");
									var _span=$(_this).find("#s_code");
									$(_in).typeahead({
					  					source: function(query, process) {
					  						var results = _.map(data.data, function (product) {
					  	                        return product.code+"-"+product.title;
					  	                    });
					  	                    process(results);
					  					},
					  					updater:function(item){
					  						var client = _.find(data.data, function (p) {
						  						return (p.code+"-"+p.title) == item;
						                    });
					  						if(client!=null){
					  							if($(_code).val()==""){
					  								$.ajax({
					  									async:false,
					  									type : "post",
					  									data:{
					  										"code":client.code
					  									},
					  									url : config.getDomain()+"/arrivalWork/getcode",
					  									dataType : "json",
					  									success : function(data) {
					  										if(data.code=="0000"){
					  											$(_code).val(data.map.code);
					  										}
					  									}
					  								});
					  							}
					  							
					  							$.ajax({
													async:false,
													type : "get",
													url : config.getDomain()+"/order/getCargoCode?id="+client.id,
													dataType : "json",
													success : function(data) {
														if(data.data[0].code){
															console.log(data.data[0].code);
															$(_span).attr("title",data.data[0].code.replace(/,/g,'\n'));
														}
														else{
															$(_span).attr("title","");
														}
													}
												});
					  							
					  							
					  						}
					  						return item;
					  					},
					  				noselect:function(query){
					  					var client = _.find(data.data, function (p) {
					  						return (p.code+"-"+p.title) == query;
					                    });
					  					if(client==null){
					  						$(_in).attr("data",-1);
					  						$(_in).val("");
//					  						$(_code).val("");
					  					}else{
					  						$(_in).attr("data",client.id);
					  						$.ajax({
												async:false,
												type : "get",
												url : config.getDomain()+"/order/getCargoCode?id="+client.id,
										 		dataType : "json",
												success : function(data) {  
													if(data.data[0].code){
														
														$(_span).attr("title",data.data[0].code.replace(/,/g,'\n'));
													}
													else{
														$(_span).attr("title","");
													}
												}
											});
					  						
					  					}
					  				}
					  				});
								});
								
						}
						});
							
							//货主
							$.ajax({
								async:false,
					  			type : "get",
					  			url : config.getDomain()+"/client/select",
					  			dataType : "json",
					  			success : function(data) {
					  				config.unload();
					  				$("#goods-table-sure").children("tbody").each(function(){
										var _in=$(this).find(".clientId");
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
							
							
							
							//货代
							$.ajax({
								async:false,
								type : "get",
								url : config.getDomain()+"/baseController/getCargoAgent",
								dataType : "json",
								success : function(data) {
									config.unload();
									$("#goods-table-sure").children("tbody").each(function(){
										var _in=$(this).find(".cargoAgent");
										$(_in).typeahead({
						  					source: function(query, process) {
						  						var results = _.map(data.data, function (product) {
						  	                        return product.code;
						  	                    });
						  	                    process(results);
						  					},
						  				noselect:function(query){
						  					var client = _.find(data.data, function (p) {
						  						return p.code == query;
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
							
							
							//商检
							$.ajax({
								async:false,
								type : "get",
								url : config.getDomain()+"/inspectAgent/get",
								dataType : "json",
								success : function(data) {
									config.unload();
									$("#goods-table-sure").children("tbody").children("tr").each(function(){
										var _in=$(this).find(".inspectAgent");
										
										var _in1=$(this).find(".inspectAgentSpan");
										if($(_in1).attr("data")){
											$(_in).attr("value",$(_in1).attr("data"));
											var array=new Array();
											for(var j=0;j<$(_in1).attr("data").split(",").length;j++){
												array.push({id:$(_in1).attr("data").split(",")[j],text:$(_in1).val().split(",")[j]});
											}
											$(_in).select2("data",array);
											
										}
										
										
										var array1=new Array();
										for(var i=0;i<data.data.length;i++){
											array1.push({id:data.data[i].id,text:data.data[i].code});
										}
										$(_in).select2({
								            tags: function(query){
								            	return array1;
								            	
								            }
										});
									
										
//										$(_in).typeahead({
//						  					source: function(query, process) {
//						  						var results = _.map(data.data, function (product) {
//						  	                        return product.code;
//						  	                    });
//						  	                    process(results);
//						  					},
//						  				noselect:function(query){
//						  					var client = _.find(data.data, function (p) {
//						  						return p.code == query;
//						                    });
//						  					if(client==null){
//						  						$(_in).removeAttr("data");
//						  						$(_in).val("");
//						  					}else{
//						  						$(_in).attr("data",client.id);
//						  					}
//						  				}
//						  				});
									});
								}
							});
							
				}
				
			}
		});
	}
	
	function initEdit(id){
		nArrivalId=id;
		config.load();
		$.ajax({
			type : "get",
			url : config.getDomain()+"/arrivalPlan/list?id="+id,
			dataType : "json",
			success : function(data) {
				config.unload();
				var intent=data.data[0];
				$(".planId").val(id);
				$(".shipId").val(intent.shipName);
				$(".shipId").attr("data",intent.shipId);
//				$(".shipRefId").val(intent.shipRefName);
				$(".shipRefId").attr("data",intent.shipRefId);
				$(".arrivalTime").val(intent.mArrivalTime);
				$(".endTime").val(intent.mEndTime);
				$(".description").val(intent.description);
				$(".customLading").val(intent.customLading);
				$(".customLadingCount").val(intent.customLadingCount);
				if(intent.originalArea!="null"){
					
					$(".originalArea").val(intent.originalArea);
				}
				if(intent.arrivalInfoId){
					$(".shipArrivalDraught").val(intent.shipArrivalDraught);
					$(".shipArrivalDraught").attr("data",1);
				}else{
					$(".shipArrivalDraught").attr("data",0);
				}
				
				if(intent.isCustomAgree==1){
					$("#isCustomAgree").attr('checked','checked');
				}
				if(intent.isDeclareCustom==1){
					$("#isDeclareCustom").attr('checked','checked');
				}
				if(intent.type==3){
					$("#isPassShip").attr("checked",true);
				}
				if(intent.isTrim==1){
					$("#isTrim").attr("checked",true);
				}
				
				
				if(intent.status>1){
					$("#confirmArrival").hide();
				}
				
//				$("#isPassShip").hide();
//				$(".shipLenth").val(intent.shipLenth);
//				$(".shipWidth").val(intent.shipWidth);
//				$(".shipDraught").val(intent.shipDraught);
//				$(".loadCapacity").val(intent.loadCapacity);
				$(".shipInfo").show();
				if(intent.status>=2){
					$(".check").show();
					$(".checkUser").val(intent.reviewArrivalUserName);
					$(".checkTime").val(intent.mReviewArrivalTime);
				}
				
				
				if($(".shipRefId").val()!="转输"){
					$(".check").hide();
				}
				
				 $.ajax({
						type : "get",
						url : config.getDomain()+"/shipref/list?shipId="+intent.shipId,
						dataType : "json",
						success : function(data) {
							$('#shipRefId').typeahead('hide');
							$('#shipRefId').remove();
			  				$('#shipRef').append("<input id='shipRefId' type='text' data-provide='typeahead' value='"+intent.shipRefName+"' class='form-control shipRefId'>");
			  				$('#shipRefId').typeahead({
			  					source: function(query, process) {
			  						var results = _.map(data.data, function (product) {
			  	                        return product.refName;
			  	                    });
			  	                    process(results);
			  					},
			  				noselect:function(query){
			  					var client = _.find(data.data, function (p) {
			                        return p.refName == query;
			                    });
			  					if(client==null){
			  						$('#shipRefId').removeAttr("data");
			  						$('#shipRefId').val("");
			  					}else{
			  						$('#shipRefId').attr("data",client.id);
			  					}
			  				}
			  				});
							
						}
					});
				 getSure(id,true);
				 
				 if(intent.type==3){
					 $("#goods-table-sure").children("tbody").children("tr").each(function(){
						$(this).find(".tdspass").show();
//						$(this).find(".passShip").show();
					});
					$("#spass").show();
					}else{
						$("#goods-table-sure").children("tbody").children("tr").each(function(){
							$(this).find(".tdspass").hide();
//							$(this).find(".passShip").hide();
						});
						$("#spass").hide();
					}
				 
			}
		});
		
		
		$("#isPassShip").click(function(){
			if($("#isPassShip").is(':checked')){
				$("#goods-table-sure").children("tbody").children("tr").each(function(){
					$(this).find(".tdspass").show();
//					$(this).find(".passShip").show();
				});
				$("#spass").show();
			}else{
				$("#goods-table-sure").children("tbody").children("tr").each(function(){
					$(this).find(".tdspass").hide();
//					$(this).find(".passShip").hide();
				});
				$("#spass").hide();
			}
		})
		
		
		
	}
	
	var editTable=function(obj) {
		$("#goods-table-sure").children("tbody").children("tr").each(function(){
			
			$(this).find(".edit").hide();
			
		});
		$(obj).hide();
		var _tr = $(obj).parents("tr");
		$(_tr).find(".amount").attr("readonly", false);
		$(_tr).find(".tradeType").attr("readonly", false);
//		$(_tr).find(".order").attr("readonly", false);
		$(_tr).find(".order").show();
		$(_tr).find(".orderSpan").hide();
		$(_tr).find(".code").show();
		$(_tr).find(".codeSpan").hide();
		$(_tr).find(".cargoAgent").show();
		$(_tr).find(".cargoAgentSpan").hide();
		$(_tr).find(".inspectAgent").show();
		$(_tr).find(".inspectAgentSpan").hide();
		$(_tr).find(".clientId").show();
		$(_tr).find(".clientIdSpan").hide();
		$(_tr).find(".passShip").show();
		$(_tr).find(".passShipSpan").hide();
		$(_tr).find(".description").show();
		$(_tr).find(".descriptionSpan").hide();
		$(_tr).find(".originalArea").show();
		$(_tr).find(".originalAreaSpan").hide();
		$(_tr).find(".storageType").show();
		$(_tr).find(".storageTypeSpan").hide();
		
		$(_tr).find(".isDeclareCustom").show();
		$(_tr).find(".isDeclareCustomSpan").hide();
//		$(_tr).find(".isCustomAgree").show();
//		$(_tr).find(".isCustomAgreeSpan").hide();
		$(_tr).find(".customLading").show();
		$(_tr).find(".customLadingSpan").hide();
		$(_tr).find(".customLadingCount").show();
		$(_tr).find(".customLadingCountSpan").hide();
		
		
		$(_tr).find(".submit").show();
		$(_tr).find(".concel").show();
		$(_tr).find(".predict").hide();
		$(_tr).find(".sqlitCargo").hide();
		
	}
	
	var concel=function(obj) {
		$("#goods-table-sure").children("tbody").children("tr").each(function(){
			
			$(this).find(".edit").show();
			
		});
		$(obj).hide();
		var _tr = $(obj).parents("tr");
		$(_tr).find(".submit").hide();
		$(_tr).find(".edit").show();
		$(_tr).find(".predict").show();
		$(_tr).find(".sqlitCargo").show();
		$(_tr).find(".amount").attr("readonly", true);
		$(_tr).find(".tradeType").attr("readonly", true);
		$(_tr).find(".codeSpan").attr("readonly", true);
		$(_tr).find(".code").hide();
		$(_tr).find(".codeSpan").show();
		$(_tr).find(".orderSpan").attr("readonly", true);
		$(_tr).find(".order").hide();
		$(_tr).find(".orderSpan").show();
		$(_tr).find(".passShip").hide();
		$(_tr).find(".passShipSpan").show();
		$(_tr).find(".passShipSpan").attr("readonly", true);
		$(_tr).find(".cargoAgentSpan").attr("readonly", true);
		$(_tr).find(".cargoAgent").hide();
		$(_tr).find(".cargoAgentSpan").show();
		$(_tr).find(".inspectAgentSpan").attr("readonly", true);
		$(_tr).find(".inspectAgent").hide();
		$(_tr).find(".inspectAgentSpan").show();
		
		
		
		$(_tr).find(".clientIdSpan").attr("readonly", true);
		$(_tr).find(".clientId").hide();
		$(_tr).find(".clientIdSpan").show();
		$(_tr).find(".descriptionSpan").attr("readonly", true);
		$(_tr).find(".description").hide();
		$(_tr).find(".descriptionSpan").show();
		$(_tr).find(".originalAreaSpan").attr("readonly", true);
		$(_tr).find(".originalArea").hide();
		$(_tr).find(".originalAreaSpan").show();
		
		$(_tr).find(".storageTypeSpan").attr("readonly", true);
		$(_tr).find(".storageType").hide();
		$(_tr).find(".storageTypeSpan").show();
		
		$(_tr).find(".isDeclareCustomSpan").attr("readonly", true);
		$(_tr).find(".isDeclareCustom").hide();
		$(_tr).find(".isDeclareCustomSpan").show();
//		$(_tr).find(".isCustomAgreeSpan").attr("readonly", true);
//		$(_tr).find(".isCustomAgree").hide();
//		$(_tr).find(".isCustomAgreeSpan").show();
		$(_tr).find(".customLadingSpan").attr("readonly", true);
		$(_tr).find(".customLading").hide();
		$(_tr).find(".customLadingSpan").show();
		$(_tr).find(".customLadingCountSpan").attr("readonly", true);
		$(_tr).find(".customLadingCount").hide();
		$(_tr).find(".customLadingCountSpan").show();
	}

	var doEdit=function(id, planid, arrivalId,status,obj) {
		var _tr = $(obj).parents("tr");

		
		if(status==8){
			
			if($(_tr).find(".clientIdSpan").val()!=$(_tr).find(".clientId").val()){
				
				$(this).confirm({
					content : '该货批正在进行数量确认，暂时无法修改货主!',
					concel:true,
					callBack : function() {
					}
				});
				return ;
			}
			
		}
		
		if($(_tr).find(".tradeType").val()==""||$(_tr).find(".tradeType").val()==null){
			$("body").message({
				type: 'error',
				content : '请填写该货批的贸易类型！'
			});
			return ;
		}
		
		
		if($(_tr).find(".storageType").val()==""||$(_tr).find(".storageType").val()==null||$(_tr).find(".storageType").val()==0){
			$("body").message({
				type: 'error',
				content : '请填写该货批的仓储性质！'
			});
			return ;
		}
		
		if($(_tr).find(".code").val()==""||$(_tr).find(".code").val()==null){
			$("body").message({
				type: 'error',
				content : '请填写该货批的批号！'
			});
			return ;
		}
		
		
		$(obj).hide();
		
		
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain() + "/arrivalWork/checkcode",
			data : {
				"code":$(_tr).find(".code").val()
			},
			dataType:"json",
			success:function(data){
				if(data.map.count<=1){
					var inspectAgentList="";
					var inspectAgentIds="";
					for(var i=0;i<$(_tr).find(".inspectAgent").select2("data").length;i++){
						inspectAgentList+=$(_tr).find(".inspectAgent").select2("data")[i].text+",";
						inspectAgentIds+=$(_tr).find(".inspectAgent").select2("data")[i].id+",";
					}
					inspectAgentList=inspectAgentList.substring(0,inspectAgentList.length-1);
					inspectAgentIds=inspectAgentIds.substring(0,inspectAgentIds.length-1);
					

					if($(_tr).find(".clientId").attr("data")!=$(_tr).find(".clientIdSpan").attr("data")){
						$.ajax({
							type : "get",
							url : config.getDomain() + "/initialfee/checkcargofee?cargoId="+id,
							dataType : "json",
							success : function(data) {
								config.unload();
								if (data.code == "0000") {
									
									var cando=0;
									
									if(data.map.result=='0'){
										config.load();
										$.ajax({
											type : "post",
											url : config.getDomain() + "/arrivalWork/update",
											data : {
												"cargoId" : id,
												"code":$(_tr).find(".code").val(),
												'contractId' : $(_tr).find(".order").attr("data"),
												'arrivalId' : arrivalId,
												'goodsTotal' : $(_tr).find(".amount").val(),
												'cargoAgentId':$(_tr).find(".cargoAgent").attr("data"),
//												'inspectAgentId':$(_tr).find(".inspectAgent").attr("data"),
												'inspectAgentNames':inspectAgentList,
												'inspectAgentIds':inspectAgentIds,
												'clientId':$(_tr).find(".clientId").attr("data"),
												'requirement':$(_tr).find(".description").val(),
												'originalArea':$(_tr).find(".originalArea").val(),
												'storageType':$(_tr).find(".storageType").val(),
												'tradeType':$(_tr).find(".tradeType").attr("data"),
												
												'isDeclareCustom':$(_tr).find(".isDeclareCustom").val(),
												'isCustomAgree':$(_tr).find(".isCustomAgree").val(),
												'customLading':$(_tr).find(".customLading").val(),
												'customLadingCount':$(_tr).find(".customLadingCount").val(),
												'goodsInspect':$(_tr).find(".passShip").val(),
											},
											dataType : "json",
											success : function(data) {
												config.unload();
												if (data.code == "0000") {
													$("body").message({
														type: 'success',
														content: '货批修改成功'
													});
													getSure(arrivalId,false);
												}else if(data.code=="1003"){
													$(this).confirm({
									    				content : '货批号重复！请重新填写',
									    				concel:true,
									    				callBack : function() {
									    				}
									    			});
													getSure(arrivalId,false);
												}else if(data.code=="1006"){
													$(this).confirm({
									    				content : '该货批下已存在交易过的货体，故无法修改货主，请退回货物后方可修改。',
									    				concel:true,
									    				callBack : function() {
									    				}
									    			});
													getSure(arrivalId,false);
												}else if(data.code=="1004"){
													getSure(arrivalId,false);
													
													$(this).confirm({
														content : '由于重新关联了合同，入库量需要重新扣损，是否前往？',
														concel:false,
														callBack : function() {
															window.location.href = "#/storageEdit?id="+data.map.cargoId;
														}
													});
													
												} else {
													$("body").message({
														type: 'error',
														content: '货批修改失败'
													});
													getSure(arrivalId,false);
												}
											}
										});
										
									}else if(data.map.result=='1'){
										$(this).confirm({
											content : data.map.msg+",是否确定继续修改？",
											callBack : function() {
												config.load();
												
															$.ajax({
																type : "post",
																url : config.getDomain() + "/arrivalWork/update",
																data : {
																	"cargoId" : id,
																	"code":$(_tr).find(".code").val(),
																	'contractId' : $(_tr).find(".order").attr("data"),
																	'arrivalId' : arrivalId,
																	'goodsTotal' : $(_tr).find(".amount").val(),
																	'cargoAgentId':$(_tr).find(".cargoAgent").attr("data"),
//																	'inspectAgentId':$(_tr).find(".inspectAgent").attr("data"),
																	'inspectAgentNames':inspectAgentList,
																	'inspectAgentIds':inspectAgentIds,
																	'clientId':$(_tr).find(".clientId").attr("data"),
																	'requirement':$(_tr).find(".description").val(),
																	'originalArea':$(_tr).find(".originalArea").val(),
																	'storageType':$(_tr).find(".storageType").val(),
																	'tradeType':$(_tr).find(".tradeType").attr("data"),
																	
																	'isDeclareCustom':$(_tr).find(".isDeclareCustom").val(),
																	'isCustomAgree':$(_tr).find(".isCustomAgree").val(),
																	'customLading':$(_tr).find(".customLading").val(),
																	'customLadingCount':$(_tr).find(".customLadingCount").val(),
																	'goodsInspect':$(_tr).find(".passShip").val(),
																},
																dataType : "json",
																success : function(data) {
																	config.unload();
																	if (data.code == "0000") {
																		
//																		$.ajax({
//																			type : "get",
//																			url : config.getDomain() + "/initialfee/updatecargofee?cargoId="+id+"&clientId="+$(_tr).find(".clientId").attr("data"),
//																			dataType : "json",
//																			success : function(data) {
//																				if (data.code == "0000") {
//																					
																					$("body").message({
																						type: 'success',
																						content: '货批修改成功'
																					});
																					getSure(arrivalId,false);
//																				}
//																			}
//																		});
																	}else if(data.code=="1003"){
																		$(this).confirm({
														    				content : '货批号重复！请重新填写',
														    				concel:true,
														    				callBack : function() {
														    				}
														    			});
																		getSure(arrivalId,false);
																	}else if(data.code=="1006"){
																		$(this).confirm({
														    				content : '该货批下已存在交易过的货体，故无法修改货主，请退回货物后方可修改。',
														    				concel:true,
														    				callBack : function() {
														    				}
														    			});
																		getSure(arrivalId,false);
																	}else if(data.code=="1004"){
																		getSure(arrivalId,false);
																		
																		$(this).confirm({
																			content : '由于重新关联了合同，入库量需要重新扣损，是否前往？',
																			concel:false,
																			callBack : function() {
																				window.location.href = "#/storageEdit?id="+data.map.cargoId;
																			}
																		});
																		
																	} else {
																		$("body").message({
																			type: 'error',
																			content: '货批修改失败'
																		});
																		getSure(arrivalId,false);
																	}
																}
															});
													
												
												
												
												
												
											}
										});
									}else{
										
										$(this).confirm({
											content : '已经生成账单无法修改货主。',
											callBack : function() {
												
											}
										});
									}
								}
							}
						});
					}
					
					else{

						config.load();
						
									$.ajax({
										type : "post",
										url : config.getDomain() + "/arrivalWork/update",
										data : {
											"cargoId" : id,
											"code":$(_tr).find(".code").val(),
											'contractId' : $(_tr).find(".order").attr("data"),
											'arrivalId' : arrivalId,
											'goodsTotal' : $(_tr).find(".amount").val(),
											'cargoAgentId':$(_tr).find(".cargoAgent").attr("data"),
//											'inspectAgentId':$(_tr).find(".inspectAgent").attr("data"),
											'inspectAgentNames':inspectAgentList,
											'inspectAgentIds':inspectAgentIds,
											'clientId':$(_tr).find(".clientId").attr("data"),
											'requirement':$(_tr).find(".description").val(),
											'originalArea':$(_tr).find(".originalArea").val(),
											'storageType':$(_tr).find(".storageType").val(),
											'tradeType':$(_tr).find(".tradeType").attr("data"),
											
											'isDeclareCustom':$(_tr).find(".isDeclareCustom").val(),
											'isCustomAgree':$(_tr).find(".isCustomAgree").val(),
											'customLading':$(_tr).find(".customLading").val(),
											'customLadingCount':$(_tr).find(".customLadingCount").val(),
											'goodsInspect':$(_tr).find(".passShip").val(),
										},
										dataType : "json",
										success : function(data) {
											config.unload();
											if (data.code == "0000") {
												
//												$.ajax({
//													type : "get",
//													url : config.getDomain() + "/initialfee/updatecargofee?cargoId="+id+"&clientId="+$(_tr).find(".clientId").attr("data"),
//													dataType : "json",
//													success : function(data) {
//														if (data.code == "0000") {
//															
															$("body").message({
																type: 'success',
																content: '货批修改成功'
															});
															getSure(arrivalId,false);
//														}
//													}
//												});
											}else if(data.code=="1003"){
												$(this).confirm({
								    				content : '货批号重复！请重新填写',
								    				concel:true,
								    				callBack : function() {
								    				}
								    			});
												getSure(arrivalId,false);
											}else if(data.code=="1006"){
												$(this).confirm({
								    				content : '该货批下已存在交易过的货体，故无法修改货主，请退回货物后方可修改。',
								    				concel:true,
								    				callBack : function() {
								    				}
								    			});
												getSure(arrivalId,false);
											}else if(data.code=="1004"){
												getSure(arrivalId,false);
												
												$(this).confirm({
													content : '由于重新关联了合同，入库量需要重新扣损，是否前往？',
													concel:false,
													callBack : function() {
														window.location.href = "#/storageEdit?id="+data.map.cargoId;
													}
												});
												
											} else {
												$("body").message({
													type: 'error',
													content: '货批修改失败'
												});
												getSure(arrivalId,false);
											}
										}
									});
							
						
						
						
						
						
					
					}
					
					
					
				}else{
					config.unload();
					$(this).confirm({
	    				content : '存在相同批次号的货批，请重新填写',
	    				concel:true,
	    				callBack : function() {
	    				}
	    			});
					getSure(arrivalId,false);
				}
			}
			});
		
	}

	//确认到港
	var confirmArrival=function(){
		$.ajax({
			type : "post",
			url : config.getDomain() + "/arrivalPlan/confirmArrival",
			data : {
				'arrivalId' : $(".planId").val(),
			},
			dataType : "json",
			success : function(data) {
				config.unload();
				if (data.code == "0000") {
					$("body").message({
	                    type: 'success',
	                    content: '到港信息确认成功'
	                });
					initEdit($(".planId").val());
				} else {
					$("body").message({
	                    type: 'error',
	                    content: '到港信息确认失败'
	                });
				}
			}
		});
	}
	
	var arrayEdit=function() {
		var isOk = false;
		if(config.validateForm($(".arrival-form"))){
			isOk=true;
		}
		if(isOk) {
			config.load();
			var isCustomAgree=0;
			var isDeclareCustom=0;
			if($("#isCustomAgree").is(':checked')){
				isCustomAgree=1;
			}
			if($("#isDeclareCustom").is(':checked')){
				isDeclareCustom=1;
			}
			var isPassShip=1;
			if($("#isPassShip").is(':checked')){
				isPassShip=3;
			}
			
			var isTrim=0;
			if($("#isTrim").is(':checked')){
				isTrim=1;
			}
			
		$.ajax({
			type : "post",
			url : config.getDomain() + "/arrivalPlan/update",
			data : {
				'id' : $(".planId").val(),
				'type':isPassShip,
				'shipId': $(".shipId").attr("data"),
				'shipRefId' : $(".shipRefId").attr("data"),
				"arrivalStartTime" : $(".arrivalTime").val()+" 00:00:00",
//				"arrivalEndTime" : $(".endTime").val()+" 00:00:00",
				'description' : $(".description").val(),
				"customLading":$(".customLading").val(),
				"customLadingCount":$(".customLadingCount").val(),
				"isCustomAgree":isCustomAgree,
				"isDeclareCustom":isDeclareCustom,
				"isTrim":isTrim,
				"shipArrivalDraught":$(".shipArrivalDraught").val(),
				"isCreateInfo":$(".shipArrivalDraught").attr("data"),
				"originalArea":$(".originalArea").val()
			},
			dataType : "json",
			success : function(data) {
				config.unload();
				if (data.code == "0000") {
					$("body").message({
	                    type: 'success',
	                    content: '到港信息修改成功'
	                });
					$(".shipArrivalDraught").attr("data",1);
//					window.location.reload();
				}else if(data.code=="1003"){
					$("body").message({
	                    type: 'error',
	                    content: '已经入库完成的非通过船无法修改为通过！'
	                });
				} else {
					$("body").message({
	                    type: 'error',
	                    content: '到港信息修改失败'
	                });
				}
			}
		});
		}
	
	}
	
	
	
	//初始化对话框
	var initDialog=function(dialog,type){
		config.load();
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/tradeType/select",
			dataType : "json",
			success : function(data) {
				var htm="<select class='form-control tradeType' data-placeholder='选择贸易类型..' name='tradeType' data-live-search='true'>";
				for(var i=0;i<data.data.length;i++){
					htm+="<option value="+data.data[i].key+">"+data.data[i].value+"</option>";
				}
				htm+="</select>";
				dialog.find("#select-tradeType").append(htm);
			}
		});
		
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/product/select",
			dataType : "json",
			success : function(data) {
				dialog.find('#productId').typeahead({
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
  						dialog.find('#productId').removeAttr("data");
  						dialog.find('#productId').val("");
  					}else{
  						dialog.find('#productId').attr("data",client.id);
  					}
			}
		});
			}
		});
		$.ajax({
			async:false,
  			type : "get",
  			url : config.getDomain()+"/client/select",
  			dataType : "json",
  			success : function(data) {
  				config.unload();
  				dialog.find('#clientId').typeahead({
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
  						dialog.find('#clientId').removeAttr("data");
  						dialog.find('#clientId').val("");
  					}else{
  						dialog.find('#clientId').attr("data",client.id);
  					}
  				}
  				});
  			}
  		});
		
		
		//添加货批
		dialog.find("#button-add-goods").click(function() {
			var isOk = false;
			if(config.validateForm(dialog.find(".form-horizontal"))){
				isOk=true;
			}
			if(isOk) {
				if(type==1){
					
					var isCustomAgree='否';
					var a=0;
					var isDeclareCustom='否';
					var b=0;
					if(dialog.find("#isCustomAgree").is(':checked')){
						isCustomAgree='是';
						a=1;
					}
					if(dialog.find("#isDeclareCustom").is(':checked')){
						isDeclareCustom='是';
						b=1;
					}
					
					var goods = "<tr>";
					goods +="<td id='clientId' data='"+dialog.find(".clientId").attr("data")+"'>"+dialog.find(".clientId").val()+"</td>";
					goods +="<td id='productId' data='"+dialog.find(".productId").attr("data")+"'>"+dialog.find(".productId").val()+"</td>";
					goods +="<td id='tradeType' data='"+dialog.find(".tradeType").val()+"'>"+dialog.find(".tradeType option:selected").text()+"</td>";
					goods +="<td id='goodTotal' data='"+dialog.find(".productAmount").val()+"'>"+dialog.find(".productAmount").val()+"</td>";
					goods +="<td id='isDeclareCustom' data='"+b+"'>"+isDeclareCustom+"</td>";
//					goods +="<td id='isCustomAgree' data='"+a+"'>"+isCustomAgree+"</td>";
					goods +="<td id='customLading' data='"+dialog.find(".customLading").val()+"'>"+dialog.find(".customLading").val()+"</td>";
					goods +="<td id='customLadingCount' data='"+dialog.find(".customLadingCount").val()+"'>"+dialog.find(".customLadingCount").val()+"</td>";
					
					goods +="<td id='requirement' data='"+dialog.find(".requare").val()+"'>"+dialog.find(".requare").val()+"</td>";
					goods +="<td style='vertical-align:middle;''><a href='javascript:void(0)' class='btn btn-xs blue' onclick='Arrival.removeGood(this)'> <span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a></td>";
					goods +="</tr>";
					$("#goods-table").children("tbody").append(goods);
					
					var doAdd=true;
					 Array.prototype.contains = function(item){
	                	   return RegExp("\\b"+item+"\\b").test(this);
	                   };
						if(goodsList.contains(dialog.find(".productId").attr("data"))){
							doAdd=false;
						}
					
					if(doAdd){
						goodsList.push(dialog.find(".productId").attr("data"));
						var total="<tr>";
						total +="<td id='productId' data='"+dialog.find(".productId").attr("data")+"' >"+dialog.find(".productId").val()+"</td>";
						total +="<td id='goodTotal' class='"+dialog.find(".productId").attr("data")+"'>"+dialog.find(".productAmount").val()+"</td>";
						total+="</tr>";
						$("#total-table").children("tbody").append(total);
					}else{
						var _this=$("#total-table").children("tbody");
						$(_this).find("."+dialog.find(".productId").attr("data")+"").text((util.FloatAdd(parseFloat($(_this).find("."+dialog.find(".productId").attr("data")+"").text()),parseFloat(dialog.find(".productAmount").val()))));
					}
					
					dialog.remove();
				}else{
					
					var isCustomAgree='否';
					var a=0;
					var isDeclareCustom='否';
					var b=0;
					if(dialog.find("#isCustomAgree").is(':checked')){
						isCustomAgree='是';
						a=1;
					}
					if(dialog.find("#isDeclareCustom").is(':checked')){
						isDeclareCustom='是';
						b=1;
					}
					
					config.load();
					$.ajax({
						type : "post",
						url : config.getDomain() + "/arrivalPlan/saveGood",
						data : {
							'arrivalId' : $(".planId").val(),
							'clientId' : dialog.find(".clientId").attr("data"),
							'productId' : dialog.find(".productId").attr("data"),
							'goodsTotal' : dialog.find(".productAmount").val(),
							'isDeclareCustom':b,
							'isCustomAgree':a,
							'customLading':dialog.find(".customLading").val(),
							'customLadingCount':dialog.find(".customLadingCount").val(),
							'requirement' : dialog.find(".requare").val(),
							'tradeType' : dialog.find(".tradeType").val()
						},
						dataType : "json",
						success : function(data) {
							config.unload();
							if (data.code == "0000") {
								$("body").message({
				                    type: 'success',
				                    content: '货批添加成功'
				                });
								dialog.remove();
								getPlan($(".planId").val());
							} else {
								$("body").message({
				                    type: 'error',
				                    content: '货批添加失败'
				                });
							}
						}
					});
				}
				
				
			}
		});
		
	}
	
	//删除未确认的计划单
	var removePlan=function(id){
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain() + "/arrivalPlan/deletePlan",
			data : {
				"ids" : id
			},
			dataType : "json",
			success : function(data) {
				config.unload();
				if (data.code == "0000") {
					$("body").message({
	                    type: 'success',
	                    content: '已删除该货批'
	                });
					getPlan($(".planId").val());
				} else {
					$("body").message({
	                    type: 'error',
	                    content: '删除失败'
	                });
				}
			}
		});
	}
	
	//批量确认货批
	var checkGoods=function(){
		var ids="";
		var noCheck=true;
		var shipId;
		$("#goods-table").children("tbody").each(function(){
			$('input[type="checkbox"][name="chk_list"]:checked').each(function(){
				noCheck=false;
			});
		});
		if(noCheck){
			$(this).confirm({
				content : '请至少选择一个货批计划！',
				concel:true,
				callBack : function() {
				}
			});
			return;
		}
		$(this).confirm({
			content : '确定要确认货批吗?',
			callBack : function() {
				config.load();
		$("#goods-table").children("tbody").each(function(){
			$('input[type="checkbox"][name="chk_list"]:checked').each(function(){
				var _tr = $(this).parents("tr");
				shipId=$(_tr).find("#shipId").val();
				ids+=$(_tr).find("#planId").val()+",";
				});
	});
		 ids = ids.substring(0, ids.length - 1);
		checkPlan(ids,$(".planId").val(),shipId);
			}
		});
	}
	
	
	//确认货批调用后台
	var checkPlan=function(id,arrivalId,shipId){
		$.ajax({
			type : "post",
			url : config.getDomain() + "/arrivalPlan/confirm",
			data : {
				"ids" : id
			},
			dataType : "json",
			success : function(data) {
				config.unload();
				if (data.code == "0000") {
					
					$.ajax({
						type:"post",
						url:config.getDomain()+"/inboundoperation/additem",
						data:{
							"id":arrivalId,
							"shipId":shipId
						},
						dataType:"json",
							success : function(data) {
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '货批确认成功'
											
									});
									
								}else{
									$("body").message({
										type: 'error',
										content: '生成入库作业失败'
											
									});
								}
							}
					});
					
					getSure(arrivalId,true);
				}else if(data.code=="1003"){
					$("body").message({
						type: 'error',
						content: '数据冲突，请刷新界面！'
					});
				} else {
					$("body").message({
						type: 'error',
						content: '货批确认失败'
					});
				}
			}
		});
	}
	
	
	//单个确认计划单
	var confirmPlan=function(id,arrivalId,shipId){
		$(this).confirm({
			content : '确定要确认货批吗?',
			callBack : function() {
				config.load();
				checkPlan(id,arrivalId,shipId);
			}
		});
	}
	
	var removeGood=function(obj) {
		var remData=$(obj).parents("tr").find("#goodTotal").text();
		var remProductId=$(obj).parents("tr").find("#productId").attr("data");
		var _this=$("#total-table").children("tbody");
		if(remData&&remProductId){
			if(util.FloatSub(parseFloat($(_this).find("."+remProductId+"").text()),remData)<=0){
				for(var i in goodsList){
					if(goodsList[i]==remProductId){
						goodsList.splice(i,1);
						break;
					}
				}
				$(_this).find("."+remProductId+"").parents("tr").remove();
			}else{
				$(_this).find("."+remProductId+"").text((util.FloatSub(parseFloat($(_this).find("."+remProductId+"").text()),remData)));
			}
		}
		$(obj).parents("tr").remove();
	}
	
	//查询重量，3秒钟一次
	var queryWeight=function(id){
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain() + "/comm/read",
			data : {},
			dataType : "json",
			success : function(data) {
				config.unload();
				if (data.code == "0000") {
					$("#weight").val(data.msg);
				} else {
					$("#weight").val("0");
				}
				setTimeout(function() {queryWeight()},3000);
			}
		});
	}
	
	//初始化add
	var initAddSelection=function(){
		goodsList=[];
		config.load();
		 $.ajax({
			 async:false,
	  			type : "get",
	  			url : config.getDomain()+"/ship/select",
	  			dataType : "json",
	  			success : function(data) {
	  				$('#shipId').typeahead({
	  					source: function(query, process) {
	  						var results = _.map(data.data, function (product) {
	  	                        return product.name;
	  	                    });
	  	                    process(results);
	  					},
	  				updater: function (item) {
	  					var ship = _.find(data.data, function (p) {
	                        return p.name == item;
	                    });
	  					$('#shipId').attr("data",ship.id)
	  					$(".shipInfo").show();
	  					
	  					
	  					//根据船名查中文名
	  					config.load();
	  					$.ajax({
				  			type : "get",
				  			url : config.getDomain()+"/shipref/list?shipId="+ship.id,
				  			dataType : "json",
				  			success : function(data) {
				  				config.unload();
				  				$('#shipRefId').typeahead('hide');
				  				$('#shipRefId').remove();
				  				$('#shipRef').append("<input id='shipRefId' type='text' data-required='1' data-type='Require' data-provide='typeahead'  class='form-control shipRefId'>");
				  				$('#shipRefId').typeahead({
				  					source: function(query, process) {
				  						var results = _.map(data.data, function (product) {
				  	                        return product.refName;
				  	                    });
				  	                    process(results);
				  					},
				  				noselect:function(query){
				  					var ship = _.find(data.data, function (p) {
				  						return p.refName == query;
				                    });
				  					if(ship==null){
				  						$('#shipRefId').removeAttr("data");
				  						$('#shipRefId').val("");
				  					}else{
				  						$('#shipRefId').attr("data",ship.id);
				  					}
				  				}
				  				});
				  			}
				  		});
	  					return item;
	  				},
	  				noselect:function(query){
	  					var ship = _.find(data.data, function (p) {
	  						return p.name == query;
	                    });
	  					if(ship==null){
	  						$('#shipId').removeAttr("data");
	  						$('#shipId').val("");
	  						$(".shipInfo").hide();
	  						
	  					//根据船名查中文名
	  						config.load();
		  					$.ajax({
					  			type : "get",
					  			url : config.getDomain()+"/shipref/list?shipId=0",
					  			dataType : "json",
					  			success : function(data) {
					  				config.unload();
					  				$('#shipRefId').typeahead('hide');
					  				$('#shipRefId').remove();
					  				$('#shipRef').append("<input id='shipRefId' type='text' data-required='1' data-type='Require' data-provide='typeahead'  class='form-control shipRefId'>");
					  				$('#shipRefId').typeahead({
					  					source: function(query, process) {
					  						var results = _.map(data.data, function (product) {
					  	                        return product.refName;
					  	                    });
					  	                    process(results);
					  					},
					  				noselect:function(query){
					  					var ship = _.find(data.data, function (p) {
					  						return p.refName == query;
					                    });
					  					if(ship==null){
					  						$('#shipRefId').removeAttr("data");
					  						$('#shipRefId').val("");
					  					}else{
					  						$('#shipRefId').attr("data",ship.id);
					  					}
					  				}
					  				});
					  			}
					  		});
	  						
	  					}
	  				}
	  				});
	  			}
	  		});
		 
		 
		
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/shipref/list?shipId=0",
			dataType : "json",
			success : function(data) {
				config.unload();
				$('#shipRefId').typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product.refName;
  	                    });
  	                    process(results);
  					},
  					noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.refName == query;
	                    });
	  					if(client==null){
	  						$('#shipRefId').removeAttr("data");
	  						$('#shipRefId').val("");
	  					}else{
	  						$('#shipRefId').attr("data",client.id);
	  					}
  				}
  				});
			}
		});
		
		
		//提交
		$(".planSubmit").unbind('click'); 
		$(".planSubmit").click(function() {
			var isOk = false;
			if(config.validateForm($(".arrival-update-form"))){
				isOk=true;
			}
			if(isOk) {
				var isCustomAgree=0;
				var isDeclareCustom=0;
				var isPassShip=1;
				if($("#isCustomAgree").is(':checked')){
					isCustomAgree=1;
				}
				if($("#isDeclareCustom").is(':checked')){
					isDeclareCustom=1;
				}
				if($("#isPassShip").is(':checked')){
					isPassShip=3;
				}
				var arrival = {
					
					'description': $(".description").val(),
					'shipId': $(".shipId").attr("data"),
					'shipRefId' : $(".shipRefId").attr("data"),
					'type':isPassShip,
					'arrivalStartTime': $(".arrivalTime").val()+" 00:00:00",
//					'arrivalEndTime' : $(".endTime").val()+" 00:00:00",
					"customLading":$(".customLading").val(),
					"customLadingCount":$(".customLadingCount").val(),
					"isCustomAgree":isCustomAgree,
					"isDeclareCustom":isDeclareCustom,
					"originalArea":$(".originalArea").val()
//					'endTime': $(".endTime").val()+$(".hour").val()+':'+$(".minute").val()+':'+'00'
				};
				
				var arrivalPlanList = new Array();
				$("#goods-table").children("tbody").children("tr").each(function() {
					arrivalPlanList.push({
						'productId': $(this).find("#productId").attr("data"),
						'clientId': $(this).find("#clientId").attr("data"),
						'tradeType': $(this).find("#tradeType").attr("data"),
						'goodsTotal': $(this).find("#goodTotal").attr("data"),
						'isDeclareCustom':$(this).find("#isDeclareCustom").attr("data"),
						'isCustomAgree':$(this).find("#isCustomAgree").attr("data"),
						'customLading':$(this).find("#customLading").attr("data"),
						'customLadingCount':$(this).find("#customLadingCount").attr("data"),
						'requirement': $(this).find("#requirement").attr("data"),
						'type':0,
						'createUserId':1
					});
				});
				var arrivalPlanDto = {
						"arrival" : arrival,
						"arrivalPlanList" : arrivalPlanList
				};
				config.load();
				$.ajax({
					type : "post",
					url : config.getDomain()+"/arrivalPlan/save",
					data : {
						"arrivalPlanDto" : JSON.stringify(arrivalPlanDto)
					},
					dataType : "json",
					success : function(data) {
						config.unload();
						if (data.code == "0000") {
							$("body").message({
			                    type: 'success',
			                    content: '到港计划添加成功'
			                });
							
							
							
							if($("#shipRefId").val()=="转输"){
								$(".planId").val(data.map.id);
								Arrival.confirmArrival();
							}
							window.location.href = "#/arrivalGet?id="+data.map.id;
						} else {
							$("body").message({
			                    type: 'error',
			                    content: '到港计划添加失败'
			                });
						}
					}
				});

			}

		});
		
		
	}
	
	var initChange = function(obj){
		
			 $.ajax({
					type : "get",
					url : config.getDomain()+"/shipref/list?shipId="+$(obj).val(),
					dataType : "json",
					success : function(data) {
						var htm="<select class='form-control shipRefId' data-placeholder='选择船名' name='shipRefId' data-live-search='true'>";
						for(var i=0;i<data.data.length;i++){
							htm+="<option value="+data.data[i].id+">"+data.data[i].refName+"</option>";
						}
						htm+="</select>";
						$(".shipRefId").remove();
						$("#select-shipRefId").append(htm);
					}
				});
	}
	
	
	var deleteItem = function(id){
		
		var $this = $('[data-role="arrivalGrid"]');
		
		$this.confirm({
			content : '确定要作废所选记录吗?',
			callBack : function() {
//				deleteIntent($('[data-role="contractGrid"]').getGrid().selectedRows(), $this);
				
				var data = "";
		            data += ("id=" +id);
		        
		        $.post(config.getDomain()+'/arrivalPlan/delete', data).done(function (data) {
		            if (data.code == "0000") {
		            	$('[data-role="arrivalGrid"]').message({
		                    type: 'success',
		                    content: '作废成功'
		                });
		                $('[data-role="arrivalGrid"]').grid('refresh');
		            } else {
		                $('body').message({
		                    type: 'error',
		                    content: data.msg
		                });
		            }
		        }).fail(function (data) {
		        	$('[data-role="arrivalGrid"]').message({
		                type: 'error',
		                content: '作废失败'
		            });
		        });
				
			}
		});
		
		
	}
	
	
	//删除
	var deleteIntent = function(intents, grid) {
        dataGrid = grid;
        var data = "";
        $.each(intents, function (i, role) {
            data += ("id=" + role.id + "&");
        });
        data = data.substring(0, data.length - 1);
        	$.post(config.getDomain()+'/arrivalPlan/delete', data).done(function (data) {
        		if (data.code == "0000") {
        			dataGrid.message({
        				type: 'success',
        				content: '删除成功'
        			});
        			dataGrid.grid('refresh');
        		} else {
        			$('body').message({
        				type: 'error',
        				content: data.msg
        			});
        		}
        	}).fail(function (data) {
        		dataGrid.message({
        			type: 'error',
        			content: '删除失败'
        		});
        	});
    };

	var handleRecords = function() {
		
//		util.urlHandleTypeahead("/ship/select",$('#shipId'));
		
		
		 $.ajax({
			 async:false,
	  			type : "get",
	  			url : config.getDomain()+"/ship/select",
	  			dataType : "json",
	  			success : function(data) {
	  				$('#shipId').typeahead({
	  					source: function(query, process) {
	  						var results = _.map(data.data, function (product) {
	  	                        return product.name;
	  	                    });
	  	                    process(results);
	  					},
	  				updater: function (item) {
	  					var ship = _.find(data.data, function (p) {
	                        return p.name == item;
	                    });
	  					$('#shipId').attr("data",ship.id)
	  					
	  					return item;
	  				},
	  				noselect:function(query){
	  					var ship = _.find(data.data, function (p) {
	  						return p.name == query;
	                    });
	  					if(ship==null){
	  						$('#shipId').attr("data",0);
	  						$('#shipId').val("");
	  					}else{
	  						$('#shipId').attr("data",ship.id);
	  					}
	  				}
	  				});
	  			}
	  		});
		
		
		 $.ajax({
				async:false,
				type : "get",
				url : config.getDomain()+"/product/select",
				dataType : "json",
				success : function(data) {
					$('#product').typeahead({
	  					source: function(query, process) {
	  						var results = _.map(data.data, function (product) {
	  	                        return product.name;
	  	                    });
	  	                    process(results);
	  					},updater: function (item) {
		  					var client = _.find(data.data, function (p) {
		                        return p.name == item;
		                    });
		  					if(client!=null){
		  						$('#product').attr("data",client.id);
		  					}
//		  					search();
		  					return item;
	  					},
	  				noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.name == query;
	                    });
	  					if(client==null){
	  						$('#product').removeAttr("data");
	  						$('#product').val("");
//	  						search();
	  					}else{
	  						$('#product').attr("data",client.id)
	  					}
	  				}
	  				});
				}
			});
		 
		 
		 $.ajax({
	  			type : "get",
	  			url : config.getDomain()+"/client/select",
	  			dataType : "json",
	  			success : function(data) {
	  				config.unload();
	  				$('#client').typeahead({
	  					source: function(query, process) {
	  						var results = _.map(data.data, function (product) {
	  	                        return product.name+" ["+product.code+"]";
	  	                    });
	  	                    process(results);
	  					},
	  					items:100,
	  					menu:"<ul class=\"typeahead dropdown-menu\" style='height: 260px;width:280px;overflow-y: auto;overflow-x: hidden;'></ul>",
	  					updater: function (item) {
		  					var client = _.find(data.data, function (p) {
		                        return p.name+" ["+p.code+"]" == item;
		                    });
		  					if(client!=null){
		  						$('#client').attr("data",client.id);
		  					}
//		  					search();
		  					return item;
	  					},
	  				noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.name+" ["+p.code+"]" == query;
	                    });
	  					if(client==null){
	  						$('#client').removeAttr("data");
	  						$('#client').val("");
//	  						search();
	  					}else{
	  						$('#client').attr("data",client.id)
	  					}
	  				}
	  				});
	  			}
	  		});
		
		
		var columns = [  
		    {
			title:' ',
			width:30,
			render:function(item,name,index){
				
				return '<a href="javascript:void(0);" onclick="Arrival.addTable(this,'+item.id+')"><span class="fa fa-plus-square-o" style="font-size:17px"></span></a>';
			}
			},{
			title : "船舶英文名",
			name : "shipName",
			render: function(item, name, index){
				if(config.hasPermission('AARRIVALPLANUPDATE')){
					return "<a href='#/arrivalGet?id="+item.id+"'>"+item.shipName+"</a>";
				}else{
					return item.shipName;
				}
			}
		},{
			title : "中文名",
			name : "shipRefName",
			render: function(item,name,index){
				if(item.type==3){
					return item.shipRefName+"  (通过)";
				}
				return item.shipRefName;
			}
		}, {
			title : "船期",
			name : "mArrivalTime",
			render: function(item,name,index){
				if(item.mEndTime==null){
					return item.mArrivalTime;
				}
				return item.mArrivalTime+"  至    "+item.mEndTime;
			}
		},{
			title : "船长(米)",
			width : 80,
			name : "shipLenth"
		},{
			title : "船宽(米)",
			width : 80,
			name : "shipWidth"
		},{
			title : "满载吃水(米)",
			width : 80,
			name : "shipDraught"
		},{
			title : "载重(吨)",
			width : 80,
			name : "loadCapacity"
		},{
			title : "理货",
			width : 80,
			name : "isTrim",
			render: function(item, name, index){
					if(item.isTrim==1){
						return "已理货";
					}else{
						return "";
					}
				}	
		},{
			title : "海关卸货",
			width : 80,
			name : "isCustomAgree",
			render: function(item, name, index){
					if(item.isCustomAgree==1){
						return "同意";
					}else{
						return "";
					}
				}	
		},{
			title : "未关联合同数",
			width : 80,
			name : "unContract",
			render: function(item, name, index){
				if(item.unContract>=1){
					return "<span style='color:red'>"+item.unContract+"</span>";
				}else{
					return item.unContract;
				}
			}	
		},
		
		{
			title : "状态",
			width : 50,
			name : "statusName",
			render: function(item, name, index){
				
				if(item.type==10){
					return "<span style='color:red'>已作废</span>";
				}
				if(item.status>1){
					return "<span style='color:green'>已确认</span>";
				}else{
					return "<span style='color:orange'>待确认</span>";
				}
			}
		}, {
			title : "操作",
			name : "id",
			width : 80,
			render: function(item, name, index){
				var permi="<div class='input-group-btn'>";
				
				if(item.status>3){
					
					if(config.hasPermission('AARRIVALPLANUPDATE')){
					permi+= "<shiro:hasPermission name='AARRIVALPLANUPDATE'><a href='#/arrivalGet?id="+item.id+"' style='margin:0 2px;font-size: 9px;' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a></shiro:hasPermission>";
					}
					if(config.hasPermission('AARRIVALFORESHOWADD')){
						permi+="<shiro:hasPermission name='AARRIVALPLANUPDATE'><a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='Arrival.copyToForeshow("+item.id+","+item.shipRefId+")' class='btn btn-xs blue'><span class='fa fa-anchor' title='船期预告'></span></a></shiro:hasPermission>";
						
					}
					
					if(item.type==3){
						if(config.hasPermission('AARRIVALDELETE')){
						permi+="<shiro:hasPermission name='AARRIVALDELETE'><a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='Arrival.deleteItem("+item.id+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='作废'></span></a></shiro:hasPermission>";
						}
					}
					
				}else{
					if(config.hasPermission('AARRIVALPLANUPDATE')){
						permi+= "<shiro:hasPermission name='AARRIVALPLANUPDATE'><a href='#/arrivalGet?id="+item.id+"' style='margin:0 2px;font-size: 9px;'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a></shiro:hasPermission>";
						
					}
					if(config.hasPermission('AARRIVALDELETE')){
						permi+="<shiro:hasPermission name='AARRIVALDELETE'><a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='Arrival.deleteItem("+item.id+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='作废'></span></a></shiro:hasPermission>";
						
					}
					if(config.hasPermission('AARRIVALFORESHOWADD')){
						
						permi+="<shiro:hasPermission name='AARRIVALPLANUPDATE'><a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='Arrival.copyToForeshow("+item.id+","+item.shipRefId+")' class='btn btn-xs blue'><span class='fa fa-anchor' title='船期预告'></span></a></shiro:hasPermission>";
					}
				}
				
				permi+="</div>";
				if(item.type==10){
					return "";
				}else{
					
					return permi;
				}
				
			}
		}  ];

		
		/*解决id冲突的问题*/
		$('[data-role="arrivalGrid"]').grid({
			
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			autoLoad:false,
			gridName:'daogangjihua',
			stateSave:true,
//			data　: getData()
//			isUserLocalData : true,
//			localData : $.parseJSON(getData())
			url : config.getDomain()+"/arrivalPlan/list"
		});
		Arrival.search();
		//初始化按钮操作
		$(".btn-add").unbind('click'); 
		$(".btn-add").click(function() {
			window.location.href = "#/arrivalAdd";
		});
		
		//删除
		$(".btn-remove").unbind('click'); 
		$(".btn-remove").click(function() {
			var data = $('[data-role="arrivalGrid"]').getGrid().selectedRowsIndex();
			var $this = $('[data-role="arrivalGrid"]');
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}
			var cando=true;
				 $.each($('[data-role="arrivalGrid"]').getGrid().selectedRows(), function (i, role) {
			        	if(role.status>1){
			        		$this.confirm({
			    				content : '存在无法删除的到港信息！！请重新选择！',
			    				concel:true,
			    				callBack : function() {
			    				}
			    			});
			        		cando=false;
			        		return false;
			        	}
			        });
				 if(cando){
					 $this.confirm({
						 content : '确定要撤销所选记录吗?',
						 callBack : function() {
							 deleteIntent($('[data-role="arrivalGrid"]').getGrid().selectedRows(), $this);
						 }
					 });
				 }
				
		});
		
		//修改
		$(".btn-modify").unbind('click'); 
		$(".btn-modify").click(function() {
			var data = $('[data-role="arrivalGrid"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (data.length == 0) {
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
			window.location.href = "#/arrivalGet?id="+$('[data-role="arrivalGrid"]').getGrid().selectedRows()[0].id;
		});
		$(".btn-search").unbind('click'); 
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		
		
	};
    
	
	function deleteCargo(cargoId,arrivalId){
		$(this).confirm({
			content : '确定要删除货批吗?',
			callBack : function() {
				
				config.load();
				$.ajax({
					type : "post",
					url : config.getDomain() + "/arrivalWork/deleteCargo",
					data : {
						"cargoId":cargoId
					},
					dataType:"json",
					success:function(data){
						config.unload();
						if (data.code == "0000") {
							$("body").message({
								type: 'success',
								content: '货批删除成功'
							});
							getSure(arrivalId,false);
						} else {
							$("body").message({
								type: 'error',
								content: '货批删除失败'
							});
							getSure(arrivalId,false);
						}
					}
				});
				
			}
		});
		
	}
	
//	


	
	
	
	
	function printPlan(cargoId,i){
		$.get(config.getResource() + "/pages/inbound/arrival/shipConfirm.jsp").done(function(data) {
			dialog = $(data);
			iprintPlan(dialog, cargoId,i);
			
			dialog.modal({
				keyboard : true
			});
		});
	}
	
	
	function iprintPlan(dialog,cargoId,i){
		
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
				dialog.find(".createTime").text(dialog.find('#pTime').val());
				dialog.find("#createTime").text(dialog.find('#pTime').val());
			});
		
					
					dialog.find('#shipName').text($(".shipId").val());
					dialog.find('#taxType').text(mSureList[i].typeName==null?"":mSureList[i].typeName);
					dialog.find('#arrivalTime').text($(".arrivalTime").val());
					dialog.find('#productName').text(mSureList[i].productName);
					dialog.find('#goodsTotal').text(mSureList[i].goodsPlan+"(吨)");
					dialog.find('#clientName').text(mSureList[i].clientName);
					dialog.find("#require").text(mSureList[i].requirement);
					dialog.find("#createTime").text(new Date().Format("yyyy-MM-dd"));
					dialog.find("#cargoCode").text(mSureList[i].code);
					
					if(mSureList[i].connectNo){
						dialog.find("#No").text(mSureList[i].connectNo);
					}
					
					
//					var No=dialog.find("#No")[0].innerHTML;
					dialog.find(".btn-print").unbind('click'); 
					dialog.find(".btn-print").click(function() {
//						
//						$.ajax({
//							type : "get",
//							url : config.getDomain()+"/arrivalPlan/updateNo?planId="+data.data[0].id+"&No="+No,
//							dataType : "json",
//							success : function(data) {
//								
//							}
//						});
						console.log(dialog.find("#pTime").val().replace("-",""));
						console.log(dialog.find('#arrivalTime').text().replace("-",""));
						
//						if(dialog.find("#pTime").val().replace("/-/g","")<dialog.find('#shipTime').text().replace("/-/g","")){
//							$("body").message({
//								type: 'error',
//								content: '制定时间不能晚于到港时间'
//							});
//						}else{
//							
//						}
						dialog.find('.modal-body').jqprint();
					});
		
	}
	

	
	function printconfirm(i){
		$.get(config.getResource() + "/pages/inbound/arrival/shipPlan.jsp").done(function(data) {
			dialog = $(data);
			iprintConfirm(dialog, i);
			
			dialog.modal({
				keyboard : true
			});
		});
	}
	
	
	function iprintConfirm(dialog,i){
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
				dialog.find(".createTime").text(dialog.find('#pTime').val());
			});
		$.ajax({
			type:'get',
			url:config.getDomain()+"/auth/user/get?id="+config.findUserId(),
			dataType:'json',
			success:function(data){
				if(data.code=='0000'){
					
					dialog.find('#open').text(data.data[0].name);
					
					var contractCode =mSureList[i].code==null?"":mSureList[i].code;
					
					
					
					dialog.find('#contract').text(contractCode);
					dialog.find('#shipName').text($(".shipId").val());
					dialog.find('#taxType').text(mSureList[i].typeName==null?"":mSureList[i].typeName);
					dialog.find('#shipTime').text($(".arrivalTime").val());
					dialog.find('#productName').text(mSureList[i].productName);
					dialog.find('#count').text(mSureList[i].goodsPlan+"(吨)");
					dialog.find('#clientName').text(mSureList[i].clientName);
					dialog.find("#require").text(mSureList[i].requirement);
					dialog.find("#createTime").text(new Date().Format("yyyy-MM-dd"));
					
					
					var originalArea =$(".originalArea").val()==""?"":$(".originalArea").val();
					dialog.find("#originalArea").text(originalArea);
					
					
					
					
					
					if(mSureList[i].No){
						dialog.find("#No").text(mSureList[i].No);
					}
//					else{
//						
//						dialog.find("#No").text(util.getNo((parseFloat(mSureList[i].NoCount.split('.')[1])+1)));
//					}
//					
//					
//					var No=dialog.find("#No")[0].innerHTML;
//					
					dialog.find(".btn-print").unbind('click'); 
					dialog.find(".btn-print").click(function() {
//						
//						$.ajax({
//							type : "get",
//							url : config.getDomain()+"/arrivalPlan/updateCargoNo?cargoId="+mSureList[i].id+"&No="+No,
//							dataType : "json",
//							success : function(data) {
//								getSure(nArrivalId,false);
//							}
//						});
						
//						if(dialog.find("#pTime").val().replace("/-/g","")<dialog.find('#shipTime').text().replace("/-/g","")){
//							$("body").message({
//								type: 'error',
//								content: '制定时间不能晚于到港时间'
//							});
//						}else{
//							
//							dialog.find('.modal-body').jqprint();
//						}
						
						dialog.find('.modal-body').jqprint();
					});
					
				}
				}
		});
		
		
		
		
	}
	
	
	function addTable(obj,id){
	  	$(obj).find('span').attr('class');
		if($(obj).find('span').attr('class')=='fa fa-plus-square-o'){
			$(obj).find('span').removeClass('fa fa-plus-square-o').addClass('fa fa-minus-square-o');
		var iTr=$(obj).closest('tr');
		var html='<tr class="detail" data-role="'+id+'"><td colspan="13">'+
			'<form action="#" class="form-horizontal">'+
		'<div class="form-body inboundplan" >'+
			'<h4 class="form-section">时间信息</h4>'+
		'<div class="form-group">'+
		'<div class="col-md-6">'+
			'<label class="control-label col-md-4">长江口时间</label>'+
			'<div class="col-md-8">'+
				'<div class="input-group" id="cjTime">'+
					'<div class="col-md-6" style="padding-right: 0px;">'+
						'<input style="text-align: right; border-right: 0;" '+
							'class="form-control form-control-inline date-picker" '+
							'type="text" />'+
					'</div>'+
					'<div class="col-md-4"'+
						'style="padding-left: 0px; padding-right: 0px;">'+
						'<input style="border-left: 0; float: left;" type="text" '+
							'class="form-control  timepicker timepicker-24">'+
					'</div>'+
					'<div class="col-md-2"'+
						'style="padding-left: 0Px; padding-right: 0px;">'+
						'<button type="button" onclick="util.cleanTime(this)" '+
							'class="btn btn-primary form-control">清空</button>'+
					'</div>'+
				'</div>'+
			'</div>'+
		'</div>'+
		'<div class="col-md-6">'+
			'<label class="control-label col-md-4">太仓锚地时间</label>'+
			'<div class="col-md-8">'+
				'<div class="input-group" id="tcTime">'+
					'<div class="col-md-6" style="padding-right: 0px;">'+
						'<input style="text-align: right; border-right: 0;"'+
							'class="form-control form-control-inline date-picker col-md-8"'+
							'type="text" />'+
					'</div>'+
					'<div class="col-md-4"'+
						'style="padding-left: 0px; padding-right: 0px;">'+
						'<input style="border-left: 0;" type="text"'+
							'class="form-control col-md-4  timepicker timepicker-24">'+
					'</div>'+
					'<div class="col-md-2"'+
						'style="padding-left: 0Px; padding-right: 0px;">'+
						'<button type="button" onclick="util.cleanTime(this)"'+
							'class="btn btn-primary form-control">清空</button>'+
					'</div>'+
				'</div>'+
			'</div>'+
		'</div>'+
	'</div>'+

	'<div class="form-group">'+
		'<div class="col-md-6">'+
			'<label class="control-label col-md-4">NOR发出时间</label>'+
			'<div class="col-md-8">'+
				'<div class="input-group" id="norTime">'+
					'<div class="col-md-6" style="padding-right: 0px;">'+
						'<input style="text-align: right; border-right: 0;" '+
							'id="norTime1" " '+
							'class="form-control form-control-inline date-picker col-md-8 norTime1" '+
							'type="text" />'+
					'</div>'+
					'<div class="col-md-4" '+
						'style="padding-left: 0px; padding-right: 0px;">'+
						'<input style="border-left: 0;" type="text" '+
							' id="norTime2" '+
							'class="form-control col-md-4  timepicker timepicker-24 norTime2">'+
					'</div>'+
					'<div class="col-md-2" '+
						'style="padding-left: 0Px; padding-right: 0px;">'+
						'<button type="button" onclick="util.cleanTime(this)" '+
							'id="norCleanBtn" class="btn btn-primary form-control">清空</button>'+
					'</div>'+
				'</div>'+
			'</div>'+
		'</div>'+
		'<div class="col-md-6" style="display:none;">'+
			'<label class="control-label col-md-4">预计到港时间<span class="required">*</span></label>'+
			'<div class="col-md-8">'+
				'<div class="input-group" id="anchorTime" key="1">'+
					'<div class="col-md-7" style="padding-right: 0px;">'+
						'<input style="text-align: right; border-right: 0;" '+
							'class="form-control form-control-inline date-picker col-md-8" '+
							'type="text" data-required="1" />'+
					'</div>'+
					'<div class="col-md-5" '+
						'style="padding-left: 0px; padding-right: 0px;">'+
						'<input style="border-left: 0;" type="text" '+
							'class="form-control col-md-4  timepicker timepicker-24">'+
					'</div>'+
				'</div>'+
			'</div>'+
		'</div>'+
	'</div>'+
	'<div class="form-group">'+
		'<div class="col-md-6">'+
			'<label class="control-label col-md-4">预计开泵时间<span class="required">*</span></label>'+
			'<div class="col-md-8">'+
				'<div class="input-group" id="pumpOpenTime">'+
					'<div class="col-md-7" style="padding-right: 0px;">'+
						'<input style="text-align: right; border-right: 0;" " '+
							'class="form-control form-control-inline date-picker col-md-8" '+
							'type="text" data-required="1" />'+
					'</div>'+
					'<div class="col-md-5" '+
						'style="padding-left: 0px; padding-right: 0px;"> '+
						'<input style="border-left: 0;" type="text" " '+
							'class="form-control col-md-4  timepicker timepicker-24"> '+
					'</div> '+
				'</div> '+
			'</div> '+
		'</div> '+
		'<div class="col-md-6"> '+
			'<label class="control-label col-md-4">预计停泵时间<span '+
				'class="required">*</span></label> '+
			'<div class="col-md-8"> '+
				'<div class="input-group" id="pumpStopTime"> '+
					'<div class="col-md-7" style="padding-right: 0px;"> '+
						'<input style="text-align: right; border-right: 0;" " '+
							'class="form-control form-control-inline date-picker col-md-8" '+
							'type="text" data-required="1" /> '+
					'</div> '+
					'<div class="col-md-5" '+
						'style="padding-left: 0px; padding-right: 0px;"> '+
						'<input style="border-left: 0;" type="text" " '+
							'class="form-control col-md-4  timepicker timepicker-24"> '+
					'</div> '+
				'</div> '+
			'</div> '+
		'</div> '+
	'</div> '+

	'<div class="form-group"> '+
		'<div class="col-md-6"> '+
			'<label class="control-label col-md-4">预计拆管时间<span '+
				'class="required">*</span></label> '+
			'<div class="col-md-8"> '+
				'<div class="input-group" id="tearPipeTime"> '+
					'<div class="col-md-7" style="padding-right: 0px;"> '+
						'<input style="text-align: right; border-right: 0;" '+
							'class="form-control form-control-inline date-picker col-md-8" '+
							'type="text" data-required="1" /> '+
					'</div> '+
					'<div class="col-md-5" '+
						'style="padding-left: 0px; padding-right: 0px;"> '+
						'<input style="border-left: 0;" type="text" '+
							'class="form-control col-md-4  timepicker timepicker-24"> '+
					'</div> '+
				'</div> '+
			'</div> '+
		'</div> '+
		'<div class="col-md-6"> '+
			'<label class="control-label col-md-4">预计离港时间<span '+
				'class="required">*</span></label> '+
			'<div class="col-md-8"> '+
				'<div class="input-group" id="leaveTime"> '+
					'<div class="col-md-7" style="padding-right: 0px;"> '+
						'<input style="text-align: right; border-right: 0;" '+
							'class="form-control form-control-inline date-picker col-md-8" '+
							'type="text" data-required="1" /> '+
					'</div> '+
					'<div class="col-md-5" '+
						'style="padding-left: 0px; padding-right: 0px;"> '+
						'<input style="border-left: 0;" type="text" '+
							'class="form-control col-md-4  timepicker timepicker-24"> '+
					'</div> '+
				'</div> '+
			'</div> '+
		'</div> '+
		'</div> '+
	' </div>'+
		
		' <div class="modal-footer"> '+
		' <button type="button" key="0" class="btn btn-primary" id="save">保存</button> '+
		' </div> '+
		' </form></td></tr>';
	    $(html).insertAfter(iTr);
	    initDetailTable(id);
//	    util.initTimePicker($(".inboundplan"));
		}else{
			$(obj).find('span').removeClass('fa fa-minus-square-o').addClass('fa fa-plus-square-o ');
			var iTr=$(obj).closest('tr');
			iTr.next('tr[class="detail"]').remove();
		}
	};
	
	function initDetailTable(id){
		$mthis=$('[data-role='+id+']');
//		$mthis.find("#leaveTime").val(121);
//		console.log($mthis.find("#leaveTime").val());
		config.load();
		//初始化数据
		$.ajax({
			type : "post",
			url : config.getDomain() + "/arrivalPlan/getArrivalInfo",
			data : {"id" : id},
			dataType : "json",
			success:function(data){
					config.unload();
					if(data.code="0000"){
						var mdata=data.data[0];
						//获取arrival到港时间并初始化	
						util.initTimePicker($mthis.find(".inboundplan"));
						util.initTimeVal($mthis.find("#anchorTime"),mdata.arrivalStartTime.substring(0,19));
						
						util.initTimeVal($mthis.find("#pumpOpenTime"), mdata.nPumpOpenTime);
						util.initTimeVal($mthis.find("#pumpStopTime"), mdata.nPumpStopTime);
						util.initTimeVal($mthis.find("#tearPipeTime"), mdata.nTearPipeTime);
						//      util.initTimeVal($("#workTime"), time);
						util.initTimeVal($mthis.find("#leaveTime"), mdata.nLeaveTime);
						
						
						util.initTimeVal($mthis.find("#cjTime"), mdata.nCjTime);
						util.initTimeVal($mthis.find("#tcTime"), mdata.nTcTime);
						util.initTimeVal($mthis.find("#norTime"), mdata.nNorTime);
						
						
							var time = util.getTimeVal($mthis.find("#anchorTime"));
							
//						$mthis.find("#anchorTime").find(".form-control").each(function(){
//								$this=$(this);
//								console.log($this.hasClass("date-picker"));
//							});
							if (util.getTimeVal($mthis.find("#pumpOpenTime"))=="") {
								util.initTimeVal($mthis.find("#pumpOpenTime"), time);
							}
							if (util.getTimeVal($mthis.find("#pumpStopTime")) == "") {
								util.initTimeVal($mthis.find("#pumpStopTime"), time);
							}
							if (util.getTimeVal($mthis.find("#tearPipeTime")) == "") {
								util.initTimeVal($mthis.find("#tearPipeTime"), time);
							}
							if (util.getTimeVal($mthis.find("#leaveTime")) == "") {
								util.initTimeVal($mthis.find("#leaveTime"), time);
						}
						
							$mthis.find("#save").click(function(){
								if(config.validateForm($mthis.find(".arrivalinfo"))) {
									config.load();
								$.ajax({
									type : "post",
									url : config.getDomain() + "/arrivalPlan/updatearrivalinfo",
									data : {
										"arrivalId" :id,
										"cjTime":util.formatLong(util.getTimeVal($mthis.find("#cjTime"))),
										"tcTime":util.formatLong(util.getTimeVal($mthis.find("#tcTime"))),
										"norTime":util.formatLong(util.getTimeVal($mthis.find("#norTime"))),
										"anchorTime":util.formatLong(util.getTimeVal($mthis.find("#anchorTime"))),
										"pumpOpenTime":util.formatLong(util.getTimeVal($mthis.find("#pumpOpenTime"))),
										"pumpStopTime":util.formatLong(util.getTimeVal($mthis.find("#pumpStopTime"))),
										"leaveTime":util.formatLong(util.getTimeVal($mthis.find("#leaveTime"))),
										"tearPipeTime":util.formatLong(util.getTimeVal($mthis.find("#tearPipeTime"))),
									},
									dataType : "json",
									success:function(data){
										config.unload();
										if(data.code="0000"){
											$("body").message({
												type: 'success',
												content: '保存成功'
											});
										}else{
											$("body").message({
												type: 'error',
												content: '保存失败'
											});
										}
									}
								});
								}
							});
							
							
					}else {
						$("body").message({
							type: 'error',
							content: '货批删除失败'
						});
					}
			}
		});
	}
	
	return {
		init : function() {
			initFormIput();
			handleRecords();
		},
		search : function() {
			var params = {};
            $("#arrivalListForm").find('.form-control').each(function(){
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
                		 params[name] = "-1";
                	 }
                }
            });
            
           $('[data-role="arrivalGrid"]').getGrid().search(params);
		},
		doAdd : function() {
			save();
		},
		doEdit : function() {
			update();
		},
		doDelete : function() {
			
		},
		initAdd:function(){
			initFormIput();
			initAddSelection();
		},
		doChange:function(obj){
			initChange(obj);
		},
		addNum : function(obj){
			addNum(obj);
		},
		initEdit : function(id){
			initFormIput();
			initEdit(id);
		},
		openGoods : function(obj){
			openGoods(obj);
		},
		doOpen : function(type){
			openModal(type);
		},
		initDialog : function(){
			initDialog();
		},
		getData : function(){
			getData();
		},
		removeGood : function(obj){
			removeGood(obj);
		},
		editTable : function(obj){
			editTable(obj);
		},
		concel : function(obj){
			concel(obj);
		},
		doEdit : function(id, planid, arrivalId,status, obj){
			doEdit(id, planid, arrivalId,status, obj);
		},
		doPredict:function(productId,cargoId,goodsTotal){
			doPredict(productId,cargoId,goodsTotal);
		},
		doSqlitCargo:function(cargoId,goodsTotal,status){
			doSqlitCargo(cargoId,goodsTotal,status);
		},
		arrayEdit: function(){
			arrayEdit();
		},
		confirmArrival:function(){
			confirmArrival();
		},
		deleteItem: function(id){
			deleteItem(id);
		},
		confirmPlan : function(id,arrivalId,shipId){
			confirmPlan(id,arrivalId,shipId);
		},
		removePlan : function(id){
			removePlan(id);
		},
		shipInfoDialog:function(){
			shipInfoDialog();
		},
		queryWeight:function(){
			queryWeight();
		},
		deleteCargo:function(cargoId,arrivalId){
			deleteCargo(cargoId,arrivalId);
		},
		checkGoods:checkGoods,
		addNewClient:addNewClient,
		openPrintPlan:function(planId){
			openPrintPlan(planId);
		},
		openPrintconfirm:function(planId){
			openPrintconfirm(planId);
		},
		printPlan:function (cargoId,i){
			printPlan(cargoId,i);
		},
		printconfirm:function (cargoId){
			printconfirm(cargoId);
		},
		exportExcel:exportExcel,
		addTable:addTable,
		copyToForeshow:copyToForeshow,
		updateForeshow:updateForeshow,
		reset:reset
	};
	
	
	
}();