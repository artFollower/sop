var VirtualCargo = function() {
	
	var dialog 	= null;    		//对话框
	var dataGrid 	= null; 		//Grid对象
    var mType=1;
	var goodsTotal=0;
	var mDialog=null;
	//执行嫁接
	function doGrafting(){
		
		
		
		
		if(config.validateForm($(".grafting-add-form"))){
			
			//检查虚拟库是否有待提，有的就不能转
			config.load();
			$.ajax({
				async:false,
	  			type : "get",
	  			url : config.getDomain()+"/grafting/checkWaitOut?goodsId="+$('.goodsCode1').attr("data"),
	  			dataType : "json",
	  			success : function(data) {
	  				config.unload();
	  				if (data.code == "0000") {
	  					if(data.data[0].count>0){
	  						$("body").message({
	  							type : 'error',
	  							content : '虚拟库有待提！无法嫁接！'
	  						});
	  						return;
	  					}
	  					
	  					
	  					config.load();
	  					$.ajax({
	  						async:false,
	  			  			type : "get",
	  			  			url : config.getDomain()+"/grafting/checkRealWaitOut?goodsId="+$('.goodsCode2').attr("data"),
	  			  			dataType : "json",
	  			  			success : function(data) {
	  			  				config.unload();
	  			  				if (data.code == "0000") {
	  			  					if(data.data[0].count>0){
	  			  						$("body").message({
	  			  							type : 'error',
	  			  							content : '真实货体有待提！无法嫁接！'
	  			  						});
	  			  						return;
	  			  					}
	  			  					
	  			  				//如果可转入量<实际转入量
	  			  					if(parseFloat($('.canIn2').val())<parseFloat($('.tIn2').val())){
	  			  						$("body").message({
	  			  							type : 'error',
	  			  							content : '可转入量小于实转入量，请重新选择！'
	  			  						});
	  			  						return;
	  			  					}
	  			  					
	  			  				
	  			  					if($('.productId1').val()!=$('.productId2').val()){
	  			  						$("body").message({
	  			  							type : 'error',
	  			  							content : '货品不一致，请重新选择！'
	  			  						});
	  			  						return;
	  			  					}
	  			  					
	  			  					
	  			  					//保存嫁接前历史记录
	  			  				config.load();
	  		  					$.ajax({
	  		  						async:false,
	  		  			  			type : "post",
	  		  			  		data : {
									"outClientId" : $(".clientId1").attr("data"),
									"inClientId" : $(".clientId2").attr("data"),
									"count" : $('.tIn2').val(),
									"type" : mType,
									"inGoodsCode" : $(".goodsCode2").val(),
									"outGoodsCode" : $(".goodsCode1").val(),
									"inGoodsId" :$(".goodsCode2").attr("data"),
									"outGoodsId" :$(".goodsCode1").attr("data"),
									"productName":$(".productId1").val(),
									"inCargoCode":$(".inCargoCode").val(),
									"outCargoCode":$(".outCargoCode").val(),
									"inCargoId":$(".inCargoCode").attr("data"),
									"outCargoId":$(".outCargoCode").attr("data")
									
	  		  			  		},
	  		  			  			url : config.getDomain()+"/grafting/addHistory",
	  		  			  			dataType : "json",
	  		  			  			success : function(data) {
	  		  			  				config.unload();
	  		  			  			if (data.code == "0000") {
	  		  			  		//如果实转数量==调出方总量，则整批
		  			  					if(parseFloat($('.tIn2').val())==parseFloat($('.goodsIn1').val())){
		  			  						config.load();
		  			  						$.ajax({
		  			  							type : "get",
		  			  							async:false,
		  			  							url : config.getDomain()+"/grafting/graftingAll?virtualGoodsId="+$('.goodsCode1').attr("data")+"&realId="+$('.goodsCode2').attr("data"),
		  			  							dataType : "json",
		  			  							success : function(data) {
		  			  								config.unload();
		  			  								if (data.code == "0000") {
		  			  									$("body").message({
		  			  										type : 'success',
		  			  										content : '嫁接成功！'
		  			  									});
		  			  								window.location.href = "#/virtualCargo";
//		  			  									cleanTurnInData();
//		  			  									cleanTurnOutData();
		  			  									
		  			  								}else{
		  			  									$("body").message({
		  			  										type : 'error',
		  			  										content : '嫁接失败！'
		  			  									});
		  			  								}
		  			  							}
		  			  						});
		  			  						
		  			  					}
		  			  					//如果实转<调出进量，则部分
		  			  					else if(parseFloat($('.tIn2').val())<parseFloat($('.goodsIn1').val())){
		  			  						config.load();
		  			  						$.ajax({
		  			  							type : "get",
		  			  							async:false,
		  			  							url : config.getDomain()+"/grafting/graftingPart?virtualGoodsId="+$('.goodsCode1').attr("data")+"&realId="+$('.goodsCode2').attr("data")+"&count="+$('.tIn2').val(),
		  			  							dataType : "json",
		  			  							success : function(data) {
		  			  								config.unload();
		  			  								if (data.code == "0000") {
		  			  									$("body").message({
		  			  										type : 'success',
		  			  										content : '嫁接成功！'
		  			  									});
		  			  								window.location.href = "#/virtualCargo";
//		  			  									cleanTurnInData();
//		  			  									cleanTurnOutData();
		  			  									
		  			  								}else{
		  			  									$("body").message({
		  			  										type : 'error',
		  			  										content : '嫁接失败！'
		  			  									});
		  			  								}
		  			  							}
		  			  						});
		  			  						
		  			  					}else{
		  			  						$("body").message({
		  			  							type : 'error',
		  			  							content : '实转入量大于调出方总量，请重新输入！'
		  			  						});
		  			  						return;
		  			  					}
	  		  			  			}else{
	  			  						$("body").message({
	  			  							type : 'error',
	  			  							content : '添加历史记录失败！'
	  			  						});
	  			  						return;
	  			  					}
	  		  			  			}
	  		  					});
	  			  					
	  			  					
	  			  					
	  			  					
	  			  					
	  			  				}else{
	  			  					$("body").message({
	  		  							type : 'error',
	  		  							content : '系统错误！'
	  		  						});
	  		  						return;
	  			  				}
	  			  				
	  			  			}});
	  					
	  					
	  					
	  				
	  				}else{
	  					$("body").message({
  							type : 'error',
  							content : '系统错误！'
  						});
  						return;
	  				}
	  				
	  			}
			});
			
			
			
			
		}
	}
	
	
	
	function setOutGrid(url){
		$('[data-role="outGrid"]').remove();
		$('.out').append("<div data-role='outGrid'></div>");
		
		
		var columns = [ {
			title : "货批编号",
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
		}, {
			title : "货主",
			name : "clientName",
		}, {
			title : "货品名称",
			name : "productName",
		},{
			title : "进量(吨)",
			name : "goodsTotal",
		}, {
			title : "存量(吨)",
			name : "goodsCurrent",
		}, 
		{
			title : "出量(吨)",
			name : "cargodata",
			render: function(item, name, index){
				return util.FloatSub(item.goodsTotal,item.goodsCurrent);
			}
		}, 
		{
			title : "操作",
			name : "cargodata",
			render: function(item, name, index){
				var re="<a href='javascript:void(0)' onclick='VirtualCargo.getOut("+index+")' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a>";
//				
             	return re;
			}
		}  ];

		
		/*解决id冲突的问题*/
		$('[data-role="outGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			url : url
		});
		
	}
	
	function getOut(index){
		var data=$('[data-role="outGrid"]').getGrid().getAllItems()[index];
		
		setTurnOutData(data);
		
		
	}
	
	
	function getIn(index){
		var data=$('[data-role="inGrid"]').getGrid().getAllItems()[index];
		
		setTurnInData(data);
		
		
	}
	
	
	function setInGrid(url){
		
		$('[data-role="inGrid"]').remove();
		$('.in').append("<div data-role='inGrid'></div>");
		
		
		var columns = [ {
			title : "货批编号",
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
		}, {
			title : "货主",
			name : "clientName",
		}, {
			title : "货品名称",
			name : "productName",
		},{
			title : "进量(吨)",
			name : "goodsTotal",
		}, {
			title : "存量(吨)",
			name : "goodsCurrent",
		}, 
		{
			title : "出量(吨)",
			name : "cargodata",
			render: function(item, name, index){
				return util.FloatSub(item.goodsTotal,item.goodsCurrent);
			}
		}, {
			title : "可转进量(吨)",
			name : "cargodata",
			render: function(item, name, index){
				return util.FloatSub(item.goodsCurrent,(item.goodsTotal-item.goodsOutPass));
			}
		},
		{
			title : "操作",
			name : "cargodata",
			render: function(item, name, index){
				var re="<a href='javascript:void(0)' onclick='VirtualCargo.getIn("+index+")' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a>";
//				
             	return re;
			}
		}  ];
		
		
		
		/*解决id冲突的问题*/
		$('[data-role="inGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			url : url
		});
		
	}
	
	
	
	//初始化嫁接页面
	function initGrafting(){
		
		
		$('.type1').on('switchChange.bootstrapSwitch', function(event, state) {  
//			  console.log(this); // DOM element  
//			  console.log(event); // jQuery event  
//			  console.log(state); // true | false  
			  if(state){
				  mType=1;
				  $(".tIn2").attr("readonly",true);
				  $(".tIn2").val(goodsTotal);
			  }else{
				  mType=2;
				  $(".tIn2").removeAttr("readonly");
			  }
			});  
		
		
//		config.load();
//		$.ajax({
//				async:false,
//  			type : "get",
//  			url : config.getDomain()+"/grafting/getGraftingGoods",
//  			dataType : "json",
//  			success : function(data) {
//  				$('.goodsCode1').typeahead({
//  					source: function(query, process) {
//  						var results = _.map(data.data, function (product) {
//  	                        return product.code;
//  	                    });
//  	                    process(results);
//  					},
//  				updater: function (item) { 
//  					var client = _.find(data.data, function (p) {
//                        return p.code == item;
//                    });
//  					$('.goodsCode1').attr("data",client.id);
//  					if(client.sourceGoodsId!=0&&client.sourceGoodsId!=null){
//  						$('.clientId2').removeAttr("readonly");
//  						}else{
//  							$('.clientId2').attr("readonly",true);
//  						}
//  					setTurnOutData(client);
//  					setInTypethread();
//  
//  					
//  					return item;
//  				},
//  				noselect:function(query){
//  					var client = _.find(data.data, function (p) {
//                        return p.code == query;
//                    });
//  					if(client==null){
//  						$('.goodsCode1').removeAttr("data");
//  						$('.goodsCode1').val("");
//  						cleanTurnOutData();
//  					}else{
//  						$('.goodsCode1').attr("data",client.id);
//  						if(client.sourceGoodsId!=0&&client.sourceGoodsId!=null){
//  	  						$('.clientId2').removeAttr("readonly");
//  	  						}else{
//  	  							$('.clientId2').attr("readonly",true);
//  	  						}
//  						setTurnOutData(client);
//  						setInTypethread();
//  						
//  					
//  						
//  					}
//  				}
//  				});
//  			}
//  		});
		
//		$.ajax({
//			async:false,
//			type : "get",
//			url : config.getDomain()+"/grafting/toGraftingGoods",
//			dataType : "json",
//			success : function(data) {
//				$('.goodsCode2').typeahead({
//					source: function(query, process) {
//						var results = _.map(data.data, function (product) {
//	                        return product.code;
//	                    });
//	                    process(results);
//					},
//				updater: function (item) {
//					var client = _.find(data.data, function (p) {
//                    return p.code == item;
//                });
//					$('.goodsCode2').attr("data",client.id);
//					setTurnInData(client);
//					return item;
//				},
//				noselect:function(query){
//					var client = _.find(data.data, function (p) {
//                    return p.code == query;
//                });
//					if(client==null){
//						$('.goodsCode2').removeAttr("data");
//						$('.goodsCode2').val("");
//						cleanTurnInData();
//					}else{
//						$('.goodsCode2').attr("data",client.id);
//						setTurnInData(client);
//					}
//				}
//				});
//			}
//		});
		
		
		
		
		$.ajax({
			 async:false,
	  			type : "get",
	  			url : config.getDomain()+"/grafting/getClient",
	  			dataType : "json",
	  			success : function(data) {
	  				config.unload();
	  				$('.clientId1').typeahead({
	  					source: function(query, process) {
	  						var results = _.map(data.data, function (product) {
	  	                        return product.clientName;
	  	                    });
	  	                    process(results);
	  					},
	  				updater: function (item) {
	  					var client = _.find(data.data, function (p) {
	                        return p.clientName == item;
	                    });
	  					$('.clientId1').attr("data",client.clientId);
	  					$('.clientId2').val(client.clientName);
	  					$('.clientId2').attr("data",client.clientId);
	  					//根据客户查询对应货体
	  					
	  					setOutGrid(config.getDomain()+"/grafting/getGraftingGoods?clientId="+client.clientId);
	  					setInGrid(config.getDomain()+"/grafting/toGraftingGoods?clientId="+client.clientId);
		  				
//	  					
//	  					
//	  					config.load();
//	  					
//	  					//转入
//	  					$.ajax({
//	  						async:false,
//				  			type : "get",
//				  			url : config.getDomain()+"/grafting/toGraftingGoods?clientId="+client.clientId,
//				  			dataType : "json",
//				  			success : function(data) {
//				  				config.unload();
//				  				setInGrid(config.getDomain()+"/grafting/toGraftingGoods?clientId="+client.clientId);
//				  				$('.goodsCode2').typeahead('hide');
//				  				$('.goodsCode2').remove();
//				  				$('.goodsId2').append("<input id='goodsCode2' type='text' data-provide='typeahead' data-required='1' data-type='Require' class='form-control goodsCode2'>");
//				  				$('.goodsCode2').typeahead({
//				  					source: function(query, process) {
//				  						var results = _.map(data.data, function (product) {
//				  	                        return product.code;
//				  	                    });
//				  	                    process(results);
//				  					},
//				  				updater: function (item) {
//				  					var client = _.find(data.data, function (p) {
//				                        return p.code == item;
//				                    });
//				  					$('.goodsCode2').attr("data",client.id);
//				  					setTurnInData(client);
//				  					
//			  						return item;
//				  				},
//				  				noselect:function(query){
//				  					var client = _.find(data.data, function (p) {
//				                        return p.code == query;
//				                    });
//				  					if(client==null){
//				  						$('.goodsCode2').removeAttr("data");
//				  						$('.goodsCode2').val("");
//				  						cleanTurnInData();
//				  					}else{
//				  						$('.goodsCode2').attr("data",client.id);
//				  						setTurnInData(client);
//				  					}
//				  				}
//				  				});
//				  			}
//				  		});
//	  					
//	  					//转出
//	  					$.ajax({
//	  						async:false,
//				  			type : "get",
//				  			url : config.getDomain()+"/grafting/getGraftingGoods?clientId="+client.clientId,
//				  			dataType : "json",
//				  			success : function(data) {
//				  				config.unload();
//				  				setOutGrid(config.getDomain()+"/grafting/getGraftingGoods?clientId="+client.clientId);
//				  				
//				  				$('.goodsCode1').typeahead('hide');
//				  				$('.goodsCode1').remove();
//				  				$('.goodsId1').append("<input id='goodsCode1' type='text' data-provide='typeahead' data-required='1' data-type='Require' class='form-control goodsCode1'>");
//				  				$('.goodsCode1').typeahead({
//				  					source: function(query, process) {
//				  						var results = _.map(data.data, function (product) {
//				  	                        return product.code;
//				  	                    });
//				  	                    process(results);
//				  					},
//				  				updater: function (item) {
//				  					var client = _.find(data.data, function (p) {
//				                        return p.code == item;
//				                    });
//				  					$('.goodsCode1').attr("data",client.id);
//				  					if(client.sourceGoodsId!=0&&client.sourceGoodsId!=null){
//				  						$('.clientId2').removeAttr("readonly");
//				  						}else{
//				  							$('.clientId2').attr("readonly",true);
//				  						}
//				  					setTurnOutData(client);
//				  					setInTypethread();
//			  						return item;
//				  				},
//				  				noselect:function(query){
//				  					var client = _.find(data.data, function (p) {
//				                        return p.code == query;
//				                    });
//				  					if(client==null){
//				  						$('.goodsCode1').removeAttr("data");
//				  						$('.goodsCode1').val("");
//				  						cleanTurnOutData();
//				  					}else{
//				  						$('.goodsCode1').attr("data",client.id);
//				  						if(client.sourceGoodsId!=0&&client.sourceGoodsId!=null){
//				  	  						$('.clientId2').removeAttr("readonly");
//				  	  						}else{
//				  	  							$('.clientId2').attr("readonly",true);
//				  	  						}
//				  						setTurnOutData(client);
//				  						setInTypethread();
//				  					}
//				  				}
//				  				});
//				  			}
//				  		});
	  					return item;
	  				},
	  				noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.clientName == query;
	                    });
	  					if(client==null){
	  						$('.clientId1').val("");
	  						cleanTurnOutData();
	  						cleanTurnInData();
	  						
	  						
//	  					//根据客户查询对应货体
//	  						config.load();
//	  						
//	  					//转入
//		  					$.ajax({
//		  						async:false,
//					  			type : "get",
//					  			url : config.getDomain()+"/grafting/toGraftingGoods",
//					  			dataType : "json",
//					  			success : function(data) {
//					  				config.unload();
//					  				$('.goodsCode2').typeahead('hide');
//					  				$('.goodsCode2').remove();
//					  				$('.goodsId2').append("<input id='goodsCode2' type='text' data-provide='typeahead' data-required='1' data-type='Require' class='form-control goodsCode2'>");
//					  				$('.goodsCode2').typeahead({
//					  					source: function(query, process) {
//					  						var results = _.map(data.data, function (product) {
//					  	                        return product.code;
//					  	                    });
//					  	                    process(results);
//					  					},
//					  				updater: function (item) {
//					  					var client = _.find(data.data, function (p) {
//					                        return p.code == item;
//					                    });
//					  					$('.goodsCode2').attr("data",client.id);
//					  					setTurnInData(client);
//				  						return item;
//					  				},
//					  				noselect:function(query){
//					  					var client = _.find(data.data, function (p) {
//					                        return p.code == query;
//					                    });
//					  					if(client==null){
//					  						$('.goodsCode2').removeAttr("data");
//					  						$('.goodsCode2').val("");
//					  						cleanTurnInData();
//					  					}else{
//					  						$('.goodsCode2').attr("data",client.id);
//					  						setTurnInData(client);
//					  					}
//					  				}
//					  				});
//					  			}
//					  		});
//	  						
//	  						
//	  						
//	  						
//	  						//转出
//		  					$.ajax({
//		  						async:false,
//					  			type : "get",
//					  			url : config.getDomain()+"/grafting/getGraftingGoods",
//					  			dataType : "json",
//					  			success : function(data) {
//					  				
//					  				config.unload();
//					  				$('.goodsCode1').typeahead('hide');
//					  				$('.goodsCode1').remove();
//					  				$('.goodsId1').append("<input id='goodsCode1' type='text' data-provide='typeahead' data-required='1' data-type='Require'  class='form-control goodsCode1'>");
//					  				$('.goodsCode1').typeahead({
//					  					source: function(query, process) {
//					  						var results = _.map(data.data, function (product) {
//					  	                        return product.code;
//					  	                    });
//					  	                    process(results);
//					  					},
//					  				updater: function (item) {
//					  					var client = _.find(data.data, function (p) {
//					                        return p.code == item;
//					                    });
//					  					$('.goodsCode1').attr("data",client.id);
//					  					if(client.sourceGoodsId!=0&&client.sourceGoodsId!=null){
//					  						$('.clientId2').removeAttr("readonly");
//					  						}else{
//					  							$('.clientId2').attr("readonly",true);
//					  						}
//					  					setTurnOutData(client);
//					  					setInTypethread();
//				  						return item;
//					  				},
//					  				noselect:function(query){
//					  					var client = _.find(data.data, function (p) {
//					                        return p.code == query;
//					                    });
//					  					if(client==null){
//					  						$('.goodsCode1').removeAttr("data");
//					  						$('.goodsCode1').val("");
//					  						cleanTurnOutData();
//					  					}else{
//					  						$('.goodsCode1').attr("data",client.id);
//					  						if(client.sourceGoodsId!=0&&client.sourceGoodsId!=null){
//					  	  						$('.clientId2').removeAttr("readonly");
//					  	  						}else{
//					  	  							$('.clientId2').attr("readonly",true);
//					  	  						}
//					  						setTurnOutData(client);
//					  						setInTypethread();
//					  						
//					  					}
//					  				}
//					  				});
//					  			}
//					  		});
	  				}else{
	  					$('.clientId1').attr("data",client.clientId);
	  					$('.clientId2').val(client.clientName);
	  					$('.clientId2').attr("data",client.clientId);
	  				}
	  					
	  			}
	  				});
	  			}
	  		});
		
		
		//转入货主
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/client/select",
  			dataType : "json",
  			success : function(data) {
  				config.unload();
  				$('.clientId2').typeahead({
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
	  						$('.clientId2').attr("data",client.id);
	  					//转入
		  					$.ajax({
		  						async:false,
					  			type : "get",
					  			url : config.getDomain()+"/grafting/toGraftingGoods?clientId="+client.id,
					  			dataType : "json",
					  			success : function(data) {
					  				setInGrid(config.getDomain()+"/grafting/toGraftingGoods?clientId="+client.id);
					  				
					  				config.unload();
					  				$('.goodsCode2').typeahead('hide');
					  				$('.goodsCode2').remove();
					  				$('.goodsId2').append("<input id='goodsCode2' type='text' data-provide='typeahead' data-required='1' data-type='Require' class='form-control goodsCode2'>");
					  				$('.goodsCode2').typeahead({
					  					source: function(query, process) {
					  						var results = _.map(data.data, function (product) {
					  	                        return product.code;
					  	                    });
					  	                    process(results);
					  					},
					  				updater: function (item) {
					  					var client = _.find(data.data, function (p) {
					                        return p.code == item;
					                    });
					  					$('.goodsCode2').attr("data",client.id);
					  					setTurnInData(client);
					  					
				  						return item;
					  				},
					  				noselect:function(query){
					  					var client = _.find(data.data, function (p) {
					                        return p.code == query;
					                    });
					  					if(client==null){
					  						$('.goodsCode2').removeAttr("data");
					  						$('.goodsCode2').val("");
					  						cleanTurnInData();
					  					}else{
					  						$('.goodsCode2').attr("data",client.id);
					  						setTurnInData(client);
					  					}
					  				}
					  				});
					  			}
					  		});
	  					}else{
	  					//转入
		  					$.ajax({
		  						async:false,
					  			type : "get",
					  			url : config.getDomain()+"/grafting/toGraftingGoods",
					  			dataType : "json",
					  			success : function(data) {
					  				config.unload();
					  				$('.goodsCode2').typeahead('hide');
					  				$('.goodsCode2').remove();
					  				$('.goodsId2').append("<input id='goodsCode2' type='text' data-provide='typeahead' data-required='1' data-type='Require' class='form-control goodsCode2'>");
					  				$('.goodsCode2').typeahead({
					  					source: function(query, process) {
					  						var results = _.map(data.data, function (product) {
					  	                        return product.code;
					  	                    });
					  	                    process(results);
					  					},
					  				updater: function (item) {
					  					var client = _.find(data.data, function (p) {
					                        return p.code == item;
					                    });
					  					$('.goodsCode2').attr("data",client.id);
					  					setTurnInData(client);
				  						return item;
					  				},
					  				noselect:function(query){
					  					var client = _.find(data.data, function (p) {
					                        return p.code == query;
					                    });
					  					if(client==null){
					  						$('.goodsCode2').removeAttr("data");
					  						$('.goodsCode2').val("");
					  						cleanTurnInData();
					  					}else{
					  						$('.goodsCode2').attr("data",client.id);
					  						setTurnInData(client);
					  					}
					  				}
					  				});
					  			}
					  		});
	  						
	  					}
	  					return item;
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.name == query;
                    });
  					if(client==null){
  						$('.clientId2').removeAttr("data");
  						$('.clientId2').val("");
  					//转入
	  					$.ajax({
	  						async:false,
				  			type : "get",
				  			url : config.getDomain()+"/grafting/toGraftingGoods",
				  			dataType : "json",
				  			success : function(data) {
				  				
				  				config.unload();
				  				$('.goodsCode2').typeahead('hide');
				  				$('.goodsCode2').remove();
				  				$('.goodsId2').append("<input id='goodsCode2' type='text' data-provide='typeahead' data-required='1' data-type='Require' class='form-control goodsCode2'>");
				  				$('.goodsCode2').typeahead({
				  					source: function(query, process) {
				  						var results = _.map(data.data, function (product) {
				  	                        return product.code;
				  	                    });
				  	                    process(results);
				  					},
				  				updater: function (item) {
				  					var client = _.find(data.data, function (p) {
				                        return p.code == item;
				                    });
				  					$('.goodsCode2').attr("data",client.id);
				  					setTurnInData(client);
			  						return item;
				  				},
				  				noselect:function(query){
				  					var client = _.find(data.data, function (p) {
				                        return p.code == query;
				                    });
				  					if(client==null){
				  						$('.goodsCode2').removeAttr("data");
				  						$('.goodsCode2').val("");
				  						cleanTurnInData();
				  					}else{
				  						$('.goodsCode2').attr("data",client.id);
				  						setTurnInData(client);
				  					}
				  				}
				  				});
				  			}
				  		});
  						
  					}else{
  						$('.clientId2').attr("data",client.id);
  					//转入
	  					$.ajax({
	  						async:false,
				  			type : "get",
				  			url : config.getDomain()+"/grafting/toGraftingGoods?clientId="+client.id,
				  			dataType : "json",
				  			success : function(data) {
				  				setInGrid(config.getDomain()+"/grafting/toGraftingGoods?clientId="+client.id);
				  				
				  				config.unload();
				  				$('.goodsCode2').typeahead('hide');
				  				$('.goodsCode2').remove();
				  				$('.goodsId2').append("<input id='goodsCode2' type='text' data-provide='typeahead' data-required='1' data-type='Require' class='form-control goodsCode2'>");
				  				$('.goodsCode2').typeahead({
				  					source: function(query, process) {
				  						var results = _.map(data.data, function (product) {
				  	                        return product.code;
				  	                    });
				  	                    process(results);
				  					},
				  				updater: function (item) {
				  					var client = _.find(data.data, function (p) {
				                        return p.code == item;
				                    });
				  					$('.goodsCode2').attr("data",client.id);
				  					setTurnInData(client);
				  					
			  						return item;
				  				},
				  				noselect:function(query){
				  					var client = _.find(data.data, function (p) {
				                        return p.code == query;
				                    });
				  					if(client==null){
				  						$('.goodsCode2').removeAttr("data");
				  						$('.goodsCode2').val("");
				  						cleanTurnInData();
				  					}else{
				  						$('.goodsCode2').attr("data",client.id);
				  						setTurnInData(client);
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
		
		
		
	}
	
	
	
	function setInTypethread(){
		//转入
			config.load();
			$.ajax({
				async:false,
	  			type : "get",
	  			url : config.getDomain()+"/grafting/toGraftingGoods?clientId="+$('.clientId1').attr("data")+"&productId="+$('.productId1').attr("data"),
	  			dataType : "json",
	  			success : function(data) {
	  				
	  				config.unload();
	  				
	  				setInGrid(config.getDomain()+"/grafting/toGraftingGoods?clientId="+$('.clientId1').attr("data")+"&productId="+$('.productId1').attr("data"));
	  				
	  				
	  				$('.goodsCode2').typeahead('hide');
	  				$('.goodsCode2').remove();
	  				$('.goodsId2').append("<input id='goodsCode2' type='text' data-provide='typeahead' data-required='1' data-type='Require' class='form-control goodsCode2'>");
	  				$('.goodsCode2').typeahead({
	  					source: function(query, process) {
	  						var results = _.map(data.data, function (product) {
	  	                        return product.code;
	  	                    });
	  	                    process(results);
	  					},
	  				updater: function (item) {
	  					var client = _.find(data.data, function (p) {
	                        return p.code == item;
	                    });
	  					$('.goodsCode2').attr("data",client.id);
	  					setTurnInData(client);
						return item;
	  				},
	  				noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.code == query;
	                    });
	  					if(client==null){
	  						$('.goodsCode2').removeAttr("data");
	  						$('.goodsCode2').val("");
	  						cleanTurnInData();
	  					}else{
	  						$('.goodsCode2').attr("data",client.id);
	  						setTurnInData(client);
	  					}
	  				}
	  				});
	  			}
	  		});
	}
	
	//转入方赋值
	function setTurnInData(client){
		$('.goodsCode2').attr("data",client.id);
		$('.goodsCode2').val(client.code);
		$(".getTree2").show();
		$(".inCargoCode").val(client.cargoCode);
		$(".inCargoCode").attr("data",client.cargoId);
		$('.clientId2').val(client.clientName);
		$('.clientId2').attr("data",client.clientId);
		$('.productId2').val(client.productName);
		$('.productId2').attr("data",client.productId);
		$('.goodsCurrent2').val(client.goodsCurrent);
		$('.goodsIn2').val(client.goodsTotal);
		$('.goodsOut2').val(util.FloatSub(client.goodsTotal,client.goodsCurrent));
		if(client.ladingCode){
			$('.rootCode2').val(client.ladingCode);
			$('.resourceCode2').val(client.ladingCode);
		}
		$(".canIn2").val(util.FloatSub(client.goodsCurrent,(client.goodsTotal-client.goodsOutPass)));
		
	}
	
	//清空转入方数据
	function cleanTurnInData(){
		$(".getTree2").hide();
		$('.productId2').val("");
		$('.productId2').removeAttr("data");
		$('.goodsCurrent2').val("");
		$('.goodsIn2').val("");
		$('.goodsOut2').val("");
		$('.rootCode2').val("");
		$('.resourceCode2').val("");
		$(".canIn2").val("");
		$(".inCargoCode").val("");
	}
	
	
	//转出方数据
	function setTurnOutData(client){
		$('.goodsCode1').attr("data",client.id);
		$('.goodsCode1').val(client.code);
		goodsTotal=client.goodsTotal;
		$(".outCargoCode").val(client.cargoCode);
		$(".outCargoCode").attr("data",client.cargoId);
		$(".getTree").show();
		$('.clientId1').val(client.clientName);
		$('.clientId1').attr("data",client.clientId);
		$('.productId1').val(client.productName);
		$('.productId1').attr("data",client.productId);
		$('.goodsCurrent1').val(client.goodsCurrent);
		$('.goodsIn1').val(client.goodsTotal);
		$('.goodsOut1').val(util.FloatSub(client.goodsTotal,client.goodsCurrent));
		
		if(client.ladingCode){
			$('.rootCode1').val(client.ladingCode);
			$('.resourceCode1').val(client.ladingCode);
		}
		
		$(".tIn2").val(client.goodsTotal);
		
	}
	
	//清空转出方数据
	function cleanTurnOutData(){
		$('.clientId2').val("");
		$('.clientId2').removeAttr("data");
		$(".getTree").hide();
		$('.clientId1').val("");
		$('.clientId1').removeAttr("data");
		$('.productId1').val("");
		$('.productId1').removeAttr("data");
		$('.goodsCurrent1').val("");
		$('.goodsIn1').val("");
		$('.goodsOut1').val("");
		$('.rootCode1').val("");
		$('.resourceCode1').val("");
		$(".tIn2").val("");
		$(".outCargoCode").val("");
		
	}
	
	//转入方货体流转图
	function getTree(){
		GoodsLZ.openGoodsLZ($('.goodsCode1').attr("data"));
	}
	//转出方货体流转图
	function getTree2(){
		GoodsLZ.openGoodsLZ($('.goodsCode2').attr("data"));
	}
	var handleRecords = function() {
		util.urlHandleTypeahead("/client/select",$("#clientId"));

		
		var columns = [ {
			title : "货批编号",
			name : "cargodata",
			render: function(item, name, index){
				return "<a href='javascript:void(0)' onclick='CargoLZ.openCargoLZ("
				+ item[name].id
				+ ")' >"
				+ item[name].code + "</a>";
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
			title : "总量(吨)",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].goodsTotal;
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
		}, 
		{
			title : "操作",
			name : "cargodata",
			render: function(item, name, index){
				var re="<a href='javascript:void(0)' onclick='VirtualCargo.editCargo("+index+")' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a>";
				if(item[name].goodsOutPass==0){
					re+="<a href='javascript:void(0)' onclick='CargoPK.deleteCargo("+item[name].id+","+item[name].code+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a>"
						
				}
				//				if(item[name].fileUrl){
//					re+="<a href='javascript:void(0)' onclick='Storage.openFileModal("+item[name].id+")' class='btn btn-xs blue'><span class='fa fa-arrow-up   ' title='上传文件'></span></a><a href='"+getRootPath()+item.fileUrl+"' onclick='' class='btn btn-xs blue'><span class='fa fa-arrow-down   ' title='查看文件'></span></a>";
//					
//				}else{
//					re+="<a href='javascript:void(0)' onclick='Storage.openFileModal("+item[name].id+")' class='btn btn-xs blue'><span class='fa fa-arrow-up   ' title='上传文件'></span></a>";
//					
//				}
//				re+="<a href='javascript:void(0)' onclick='VirtualCargo.Grafting("+item[name].id+")' class='btn btn-xs blue'><span class='fa  fa-cut   ' title='嫁接'>";
//				
             	return re;
			}
		}  ];

		
		/*解决id冲突的问题*/
		$('[data-role="virtualGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			url : config.getDomain()+"/storageConfirm/list?cargoStatus=3"
		});
		
		
//		//初始化按钮操作
//		$(".btn-modify").unbind('click'); 
//		$(".btn-modify").click(function() {
//			window.location.href = "#/storageEdit?id="+$('[data-role="virtualGrid"]').getGrid().selectedRows()[0].cargodata.id;
//		});
		$(".btn-search").unbind('click'); 
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		
		
		$(".btn-grafting").click(function() {
			window.location.href = "#/graftingCargo";
		});
		
		
		$(".btn-history").click(function() {
			getHistory();
		});
		
		
		
	};
	
	function getHistory(){
		$.get(config.getResource()+"/pages/inbound/virtualCargo/history.jsp").done(function(data){
			dialog = $(data) ;
			mDialog=dialog;
				initHistory(dialog);
			dialog.modal({
				keyboard: true
			});
		});
		
	}
	
	function initHistory(dialog){
		var colums = [
{
	title : "货品",
	name : "productName", 
},{
	title : "转出方",
	name : "outClientName",
	render : function(item, name, index) {
		
	return "<a href='javascript:void(0)'  onclick='VirtualCargo.openGoodsLZ("
	+ index
	+ ",1)' id='goodsShow'>"+item.outClientName+"</a>";
	}
},{
	title : "转出方货批",
	name : "outCargoCode",
},
{
	title : "转入方",
	name : "inClientName",
	render : function(item, name, index) {
		
		return "<a href='javascript:void(0)'  onclick='VirtualCargo.openGoodsLZ("
		+ index
		+ ",2)' id='goodsShow'>"+item.inClientName+"</a>";
		}
},{
	title : "转入方货批",
	name : "inCargoCode",
},
{
	title : "嫁接方式",
	name : "type",
	render : function(item, name, index) {
		
		if(item.type==1){
			return "整批";
		}
		if(item.type==2){
			return "部分";
		}
	}
}, {
	title : "数量",
	name : "count",
}, {
	title : "操作时间",
	name : "createTime",
	render : function(item, name, index) {
		return new Date(item.createTime * 1000)
				.Format("yyyy-MM-dd HH:mm:ss");
	}
} ];

dialog.find('[data-role="historyGrid"]').grid({
identity : 'id',
columns : colums,
isShowIndexCol : false,
isShowPages : true,
lockWidth : false,
// isUserLocalData: true,
// localData : data,
url : config.getDomain() + "/grafting/getHistory"
});
	}
	
	
	
	function openGoodsLZ(index,type){
		$.get(config.getResource()+"/pages/inbound/virtualCargo/showLZ.jsp").done(function(data){
			dialog = $(data) ;
			var data;
			var id=0;
			var reg=new RegExp("=","g"); 
			var reg1=new RegExp(",","g"); 
			var reg2=new RegExp("}","g"); 
			var reg3=new RegExp("}',","g");
			if(type==1){
				data=eval(mDialog.find('[data-role="historyGrid"]').getGrid().getAllItems()[index].outLZ);
				
//				 data=eval(mDialog.find('[data-role="historyGrid"]').getGrid().getAllItems()[index].outLZ.replace(reg,":'").replace(reg1,"',").replace(reg2,"'}").replace(reg3,"},"));
				 id=mDialog.find('[data-role="historyGrid"]').getGrid().getAllItems()[index].outGoodsId;
			}else if(type==2){
				data=eval(mDialog.find('[data-role="historyGrid"]').getGrid().getAllItems()[index].inLZ);
				
//				data=eval(mDialog.find('[data-role="historyGrid"]').getGrid().getAllItems()[index].inLZ.replace(reg,":'").replace(reg1,"',").replace(reg2,"'}").replace(reg3,"},"));
				id=mDialog.find('[data-role="historyGrid"]').getGrid().getAllItems()[index].inGoodsId;
			}
			console.log(data);
			
			
			var tree =dialog.find('.jstree').cloudTreeGrid(
					{
						columns : [
								{
						title : "货主名",
						name : "NAME",
						width : 1250,
						render : function(item, name, index) {
							
							var bold="";
							if(id==item.id){
								bold="style='font-weight:bold;'";
							}
							if (item.goodsCurrent > 0) {
								return "<label href='javascript:void(0)'"+bold+" ><span class='fa fa-square' title='有量'></span>"
										+ item.NAME + "</a>";
							} else {
								return "<label href='javascript:void(0)'"+bold+" ><span class='fa fa-square-o' title='无量'></span>"
										+ item.NAME + "</a>";
							}
						}
					},{
									'title' : '提单代码',
									'data' : 'ladingCode',
									'width' : 200,
									render : function(item, name, index) {

										var bold="";
										if(id==item.id){
											bold="style='font-weight:bold;'";
										}
										
										if(!item.ladingCode){
											return "";
										}else{
											return '<label '+bold+'>'+item.ladingCode+'</label>';
										}
									}
								}, {
									'title' : '提单类型',
									'data' : 'ladingType',
									render : function(item, name, index) {
										
										var bold="";
										if(id==item.id){
											bold="style='font-weight:bold;'";
										}
										
										if(!item.ladingType){
											return "";
										}else{
											return '<label '+bold+'>'+item.ladingType+'</label>';
										}
									}
								}, {
									'title' : '起计日期/有效期',
									'data' : 'ladingTime',
									'width' : 220,
									render : function(item, name, index) {
										
										var bold="";
										if(id==item.id){
											bold="style='font-weight:bold;'";
										}
										
										if(!item.ladingTime){
											return "";
										}else{
											return '<label '+bold+'>'+item.ladingTime.substring(0,item.ladingTime.length-2)+'</label>';
										}
									}
									
								},{
									'title' : '进货量(吨)',
									'data' : 'goodsTotal',
									'width' : 100,
									render : function(item, name, index) {
										
										var bold="";
										if(id==item.id){
											bold="style='font-weight:bold;'";
										}
										
										if(item.goodsInspect!=0&&item.goodsInspect!=null){
											return '<label '+bold+'>'+item.goodsInspect+'</label>';
										}
										else{
											var goods=item.goodsTank=='null'?"0":item.goodsTank;
											
											return '<label '+bold+'>'+goods+'</label>';
										}
									}
								}, {
									'title' : '调出总量(吨)',
									'data' : 'goodsOut',
									'width' : 100,
									render : function(item, name, index) {
										
										var bold="";
										if(id==item.id){
											bold="style='font-weight:bold;'";
										}
										
										if(!item.goodsOut){
											return '<label '+bold+'>'+item.goodsOut+'</label>';
										}else{
											return '<label '+bold+'>'+0+'</label>';
										}
									}
								}, {
									'title' : '发货量(吨)',
									'data' : "send",
									'width' : 100,
									render : function(item, name, index) {
										
										var bold="";
										if(id==item.id){
											bold="style='font-weight:bold;'";
										}
										
										
										return '<label '+bold+'>'+util.FloatSub(util.FloatSub(item.goodsTotal
												, item.goodsOut)
												,item.goodsCurrent)+'</label>';
									}
								},  {
									'title' : '待提量(吨)',
									'data' : 'goodsWait',
									'width' : 100,
									 render : function(item, name, index) {
										 var bold="";
											if(id==item.id){
												bold="style='font-weight:bold;'";
												}
											return '<label '+bold+'>'+util.toDecimal3(item.goodsWait,true)+'</label>';
									 }
								},{
									'title' : '当前存量(吨)',
									'data' : 'goodsCurrent',
									'width' : 100,
									render : function(item, name, index) {
										var bold="";
										if(id==item.id){
											bold="style='font-weight:bold;'";
										}
										
										return '<label '+bold+'>'+util.toDecimal3(item.goodsCurrent,true)+'</label>';
									}
								} ],

								localdata : data
					});
			
			dialog.modal({
				keyboard: true
			});
		});
	}
	
	
	
	
	
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
//				console.log(dialog.find('#fileForm').ajaxSubmit());
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
							$('[data-role="virtualGrid"]').grid('refresh');
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
	function openModal(){
//		$('#ajax').load(config.getResource()+"/pages/inbound/arrival/addCustomer.jsp");
//		$('#ajax').modal('show');
		$.get(config.getResource()+"/pages/inbound/virtualCargo/addCustomer.jsp").done(function(data){
			dialog = $(data) ;
			initDialog(dialog);
			
			
			//添加货批
			dialog.find("#button-add-goods").click(function() {
				var isOk = false;
				if(config.validateForm(dialog.find(".form-horizontal"))){
						config.load();
						$.ajax({
							type : "post",
							url : config.getDomain() + "/arrivalPlan/addCargo",
							data : {
								'arrivalId' : 0,
								'clientId' : dialog.find(".clientId").attr("data"),
								'productId' : dialog.find(".productId").attr("data"),
								'goodsTotal' : dialog.find(".productAmount").val(),
								'taxType' : dialog.find(".tradeType").val(),
								'code' : dialog.find(".code").val(),
								'contractId' : dialog.find(".order").attr("data"),
								'tankId': dialog.find(".tankId").attr("data"),
								'status':3
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
									 $('[data-role="virtualGrid"]').grid('refresh');
								} else {
									$("body").message({
					                    type: 'error',
					                    content: '货批添加失败'
					                });
								}
							}
						});
					}
			});
			
			
			dialog.modal({
				keyboard: true
			});
		});
	}

	//编辑货批
	function editCargo(index){
		$.get(config.getResource()+"/pages/inbound/virtualCargo/editCustomer.jsp").done(function(data){
			dialog = $(data) ;
			initDialog(dialog);

			var data=$('[data-role="virtualGrid"]').getGrid().getAllItems()[index].cargodata;
			
			dialog.find(".clientId").attr("data",data.clientId);
			dialog.find(".productId").attr("data",data.productId);
			dialog.find(".productAmount").val(data.goodsTotal);
			dialog.find(".tradeType").val(data.taxType);
			dialog.find(".code").val(data.code),
			dialog.find(".order").attr("data",data.contractId);
			
			dialog.find(".clientId").val(data.clientName);
			dialog.find(".productId").val(data.productName);
			dialog.find(".order").val(data.contractCode);
			
			
			//修改货批
			dialog.find("#button-add-goods").click(function() {
				var isOk = false;
				if(config.validateForm(dialog.find(".form-horizontal"))){
						config.load();
						$.ajax({
							type : "post",
							url : config.getDomain() + "/arrivalPlan/updateCargo",
							data : {
								'clientId' : dialog.find(".clientId").attr("data"),
								'productId' : dialog.find(".productId").attr("data"),
								'goodsTotal' : dialog.find(".productAmount").val(),
								'taxType' : dialog.find(".tradeType").val(),
								'code' : dialog.find(".code").val(),
								'contractId' : dialog.find(".order").attr("data"),
								'id' : data.id
							},
							dataType : "json",
							success : function(data) {
								config.unload();
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '货批修改成功'
									});
									dialog.remove();
									
									$('[data-role="virtualGrid"]').grid('refresh');
								}else if(data.code=="1006"){
									$(this).confirm({
					    				content : '该货批下已存在交易过的货体，故无法修改货主，请退回货物后方可修改。',
					    				concel:true,
					    				callBack : function() {
					    				}
					    			});
								} else {
									$("body").message({
										type: 'error',
										content: '货批修改失败'
									});
								}
							}
						});
					}
			});
			
			
			
			dialog.modal({
				keyboard: true
			});
		});
	}
	
	
	
	//初始化对话框
	var initDialog=function(dialog){
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
  			type : "get",
  			url : config.getDomain()+"/tank/list?page=0&pagesize=0",
  			dataType : "json",
  			success : function(data) {
  				config.unload();
  				dialog.find('.tankId').typeahead({
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
  						dialog.find('.tankId').removeAttr("data");
  						dialog.find('.tankId').val("");
  					}else{
  						dialog.find('.tankId').attr("data",client.id);
  					}
  				}
  				});
  			}
  		});
		
		
		
		$.ajax({
			async:false,
		type : "get",
		url : config.getDomain()+"/order/list?status=2&page=0&pagesize=0",
		dataType : "json",
		success : function(data) {
				var _in=dialog.find(".order");
				var _code=dialog.find(".code");
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
  						}
  						return item;
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
  						return (p.code+"-"+p.title) == query;
                    });
  					if(client==null){
  						$(_in).removeAttr("data");
  						$(_in).val("");
//  						$(_code).val("");
  					}else{
  						$(_in).attr("data",client.id);
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
		
		
	}
	
	
	//删除货批
	function deleteCargo(id,code){

		$.get(config.getResource()+"/pages/approve/approveReason.jsp").done(function(data){
			dialog = $(data) ;
			initApproveModal(dialog,"/storageConfirm/deleteCargo?cargoId="+id,code);
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
	function initApproveModal(dialog,url,code){
		
		
		var content="";
		//删除货批
			content="删除货批【"+code+"】";
			dialog.find("#approveText").text(content);
		
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
	
	
	
	
	return {
		init : function() {
			handleRecords();
		},
		doOpen:function(){
			openModal();
		},
		editCargo:function(index){
			editCargo(index);
		},
		initGrafting:function(){
			initGrafting();
			
		},
		getTree:function(){
			getTree();
			
		},
		getTree2:function(){
			getTree2();
			
		},
		getIn:function(index){
			getIn(index);
		},
		getOut:function(index){
			getOut(index);
		},
		doGrafting:function(){
			doGrafting();
		},
		openGoodsLZ:function(index,type){
			openGoodsLZ(index,type);
		},
		deleteCargo:function(id,code){
			deleteCargo(id,code);
		},
		search : function() {
			var params = {};
//			params["arrivalcode"]= $("#storageListForm").find('.arrivalcode').val();
//			params["clientId"]= $("#storageListForm").find('.clientId').attr("data");
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
            console.log(params);
           $('[data-role="virtualGrid"]').getGrid().search(params);
		},
		
		
	};
	
	
	
}();