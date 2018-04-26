var goodsManager= function() {
	
	var dialog 	= null;    		//对话框
	var dataGrid 	= null; 		//Grid对象
	
	var productId;
	
	var order=0;//0：建仓倒序  1：建仓正序  2：罐号正序 3：罐号倒序
	
	var show=1;//0:未清盘  1:全部
	
	var simple=1;//0：详细  1：精简
	
	var showVir=1;//0:不显示虚拟库   1：显示虚拟库
	
	
	
	
	function goodsLoss(){
		
		
		var data = $('[data-role="storageGrid"]').getGrid().selectedRowsIndex();
		var $this = $(this);
		if (data.length == 0) {
			$('[data-role="storageGrid"]').message({
				type : 'warning',
				content : '请选择要扣损的货体'
			});
			return;
		}
		if (data.length >1) {
			$('[data-role="storageGrid"]').message({
				type : 'warning',
				content : '只能选择一个货体'
			});
			return;
		}
		dataGrid = $(this);
//		$.get(config.getResource()+"/pages/contract/intent/edit.jsp").done(function(_data){
//			dataGrid = _data;
//		});
		
		
			var goodsId="";
			var productId="";
			if(data.length ==1){
					goodsId = $('[data-role="storageGrid"]').getGrid().selectedRows()[0].goodsId;
//					productId = $('[data-role="goodsListGrid"]').getGrid().selectedRows()[0].productId;
					
					openLossModal(goodsId);
				
			}
		
		
//		var goodsId="";
//		if($('input[type="checkbox"][name="chk_list"]:checked').size()>1){
//			$("body").message({
//				type: 'error',
//				content: '只能选择一个货体'
//			});
//			return;
//		}
//		if($('input[type="checkbox"][name="chk_list"]:checked').size()==0){
//			$("body").message({
//				type: 'error',
//				content: '请选择一个货体'
//			});
//			return;
//		}
//		if($('input[type="checkbox"][name="chk_list"]:checked').size()==1){
//			$('input[type="checkbox"][name="chk_list"]:checked').each(function(){
//				var _tr = $(this).parents("tr");
//				goodsId = $(_tr).find(".goodsId").val();
//			});
//			openLossModal(goodsId);
//		}
}
	
	
	//打开货体扣损dialog
	function openLossModal(goodsId){
		$.get(config.getResource()+"/pages/inbound/storage_confirm/editGoodsTotal.jsp").done(function(data){
			dialog = $(data) ;
				initLossModal(dialog,goodsId);
			dialog.modal({
				keyboard: true
			});
		});
	}
	
	function initLossModal(dialog,goodsId){
		
		dialog.find(".gName").text("扣损量:");
		
		dialog.find(".button-ok").click(function(){
			if(config.validateForm(dialog.find(".sqlitform"))){
				
				var editCount=dialog.find("#dGoodsTotal").val();
			
				config.load();
				$.ajax({
					async:false,
				type : "post",
				url : config.getDomain()+"/storageConfirm/goodsLoss",
				dataType : "json",
				data:{
					"goodsId":goodsId,
					"lossCount":editCount
				},
				success : function(data) {

					config.unload();
					if (data.code == "0000") {
						$("body").message({
							type: 'success',
							content: '操作成功'
						});
						dialog.remove();
						goodsManager.search();
					}else if(data.code=="1003"){
						$("body").message({
							type: 'error',
							content: data.msg,
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
	
	//货体换罐
	function changeTank(){
		
//			var goodsId="";
//			if($('input[type="checkbox"][name="chk_list"]:checked').size()>1){
//				$("body").message({
//					type: 'error',
//					content: '只能选择一个货体'
//				});
//				return;
//			}
//			if($('input[type="checkbox"][name="chk_list"]:checked').size()==0){
//				$("body").message({
//					type: 'error',
//					content: '请选择一个货体'
//				});
//				return;
//			}
//			if($('input[type="checkbox"][name="chk_list"]:checked').size()==1){
//				$('input[type="checkbox"][name="chk_list"]:checked').each(function(){
//					var _tr = $(this).parents("tr");
//					goodsId = $(_tr).find(".goodsId").val();
//				});
//				
//				openTankModal(goodsId);
//				
//			}
//			
			
			
			
			
			var data = $('[data-role="storageGrid"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (data.length == 0) {
				$('[data-role="storageGrid"]').message({
					type : 'warning',
					content : '请选择要换罐的货体'
				});
				return;
			}
			if (data.length >1) {
				$('[data-role="storageGrid"]').message({
					type : 'warning',
					content : '只能选择一个货体'
				});
				return;
			}
			dataGrid = $(this);
//			$.get(config.getResource()+"/pages/contract/intent/edit.jsp").done(function(_data){
//				dataGrid = _data;
//			});
			
			
				var goodsId="";
//				var productId="";
				if(data.length ==1){
						goodsId = $('[data-role="storageGrid"]').getGrid().selectedRows()[0].goodsId;
						productId = $('[data-role="storageGrid"]').getGrid().selectedRows()[0].productId;
						
						openTankModal(goodsId);
					
				}
			
			
			
			
		
	}
	//打开拆分货体dialog
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
  			url : config.getDomain()+"/tank/list?pagesize=0&type=1&productId="+productId,
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
				"tankId":tankId,
				"tankCodes":dialog.find("#dTankId").val()
			},
			success : function(data) {

				config.unload();
				if (data.code == "0000") {
					$("body").message({
						type: 'success',
						content: '货体换罐成功'
					});
					dialog.remove();
					goodsManager.search();
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
	
	
	
	//打开拆分货体dialog
	function openModal(goodsId,canSqlit,type){
		$.get(config.getResource()+"/pages/outbound/goodsManager/sqlitGoods.jsp").done(function(data){
			dialog = $(data) ;
				initModal(dialog,goodsId,canSqlit,type);
			dialog.modal({
				keyboard: true
			});
		});
	}
	
	function initModal(dialog,goodsId,canSqlit,type){
		
		config.load();
		
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
		
		
		dialog.find(".button-ok").click(function(){
			if(config.validateForm(dialog.find(".sqlitform"))){
				if(parseFloat(dialog.find("#dGoodsTotal").val())>parseFloat(canSqlit)){
					$(this).confirm({
						content : '货体可操作量不足！无法拆分！请放行或修改拆分量！',
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
			url : config.getDomain()+"/storageConfirm/goodsSqlit",
			dataType : "json",
			data:{
				"oldGoodsId":goodsId,
				"goodsTotal":dialog.find("#dGoodsTotal").val(),
				"tankId":dialog.find("#dTankId").attr("data"),
				"type":type
			},
			success : function(data) {

				config.unload();
				if (data.code == "0000") {
					$("body").message({
						type: 'success',
						content: '货体拆分成功'
					});
					dialog.remove();
//					initEdit($(".cargoId").val());
					goodsManager.search();
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
	
	
	
	//货体拆分
	function sqlit(){
		var goodsId="";
		var oldGoodsTotal;
		var rootGoodsId;
		var resourceGoodsId;
		var type=0;
//		if($('input[type="checkbox"][name="chk_list"]:checked').size()>1){
//			$("body").message({
//				type: 'error',
//				content: '只能选择一个货体'
//			});
//			return;
//		}
//		if($('input[type="checkbox"][name="chk_list"]:checked').size()==0){
//			$("body").message({
//				type: 'error',
//				content: '请选择一个货体'
//			});
//			return;
//		}
//		if($('input[type="checkbox"][name="chk_list"]:checked').size()==1){
//			$('input[type="checkbox"][name="chk_list"]:checked').each(function(){
//				var _tr = $(this).parents("tr");
//				
//				goodsId = $(_tr).find(".goodsId").val();
////				oldGoodsTotal=$(_tr).find("#goodsTotal").text();
//				
//				oldGoodsTotal=util.FloatSub(parseFloat($(_tr).find(".goodsOutPass").val()),util.FloatAdd(parseFloat($(_tr).find(".goodsDevily").val()),parseFloat($(_tr).find(".goodsOut").val())));
//				console.log(oldGoodsTotal);
//				console.log(parseFloat($(_tr).find(".goodsOut").val()));
//				rootGoodsId=$(_tr).find(".rootGoodsId").val();
//				sourceGoodsId=$(_tr).find(".sourceGoodsId").val();
//	  			 if(rootGoodsId==0&&sourceGoodsId==0){
//					 type=0;
//				 }else{
//					 type=1;
//				 }
//			});
//			openModal(goodsId,oldGoodsTotal,type);
//			
//			
			

			
			var data = $('[data-role="storageGrid"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (data.length == 0) {
				$('[data-role="storageGrid"]').message({
					type : 'warning',
					content : '请选择要拆分的货体'
				});
				return;
			}
			if (data.length >1) {
				$('[data-role="storageGrid"]').message({
					type : 'warning',
					content : '只能选择一个货体'
				});
				return;
			}
			dataGrid = $(this);
//			$.get(config.getResource()+"/pages/contract/intent/edit.jsp").done(function(_data){
//				dataGrid = _data;
//			});
			
			
				var goodsId="";
				var canSqlit=0;
				var type=-1;
//				var productId="";
				if(data.length ==1){
					 if($('[data-role="storageGrid"]').getGrid().selectedRows()[0].rootGoodsId==0&&$('[data-role="storageGrid"]').getGrid().selectedRows()[0].sourceGoodsId==0){
						 type=0;
						 
							$('[data-role="storageGrid"]').message({
								type : 'warning',
								content : '该货体为原始货体，请到入库确认进行拆分!'
							});
							return;
						 
					 }else{
						 type=1;
					 }
					var goodsCurrent=$('[data-role="storageGrid"]').getGrid().selectedRows()[0].goodsCurrent;
					var goodsWait=$('[data-role="storageGrid"]').getGrid().selectedRows()[0].goodsWait;
						goodsId = $('[data-role="storageGrid"]').getGrid().selectedRows()[0].goodsId;
//						productId = $('[data-role="storageGrid"]').getGrid().selectedRows()[0].productId;
						canSqlit=util.FloatSub(goodsCurrent,goodsWait);
						openModal(goodsId,canSqlit,type);
					
				}
			
			
		}
	
	
	
	function initEdit(id){
		config.load();
		$.ajax({
			type : "get",
			url : config.getDomain()+"/storageConfirm/goods?cargoId="+id,
			dataType : "json",
			success : function(data) {
				config.unload();
				
				var intent=data.data[0];
				$(".cargoId").val(intent.cargodata.id);
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
							var goodsTank=goodsdata[i].goodsTank==null?0:goodsdata[i].goodsTank;
							var goodsInspect=goodsdata[i].goodsInspect==null?0:goodsdata[i].goodsInspect;
							var goodsOut=goodsdata[i].goodsOut==null?0:goodsdata[i].goodsOut;
							var goodsOutPass=goodsdata[i].goodsOutPass==null?0:goodsdata[i].goodsOutPass;
							var description=goodsdata[i].description==null?"":goodsdata[i].description;
							var goodsCurrent=goodsdata[i].goodsCurrent==null?0:goodsdata[i].goodsCurrent;
							var goodsTotal=goodsdata[i].goodsTotal==null?0:goodsdata[i].goodsTotal;
							var goodsDevily= util.FloatSub(util.FloatSub(
										goodsTotal,goodsOut),goodsCurrent);
							
							
							var rootGoodsId=goodsdata[i].rootGoodsId==null?0:goodsdata[i].rootGoodsId;
							var resourceGoodsId=goodsdata[i].resourceGoodsId==null?0:goodsdata[i].resourceGoodsId;
							
							var isPredict=goodsdata[i].isPredict==1?"预入库":"";
							var goods = "<tr>";
							goods+="<input type='hidden' value='"+goodsdata[i].id+"' class='goodsId' /><input type='hidden' value='"+goodsdata[i].isPredict+"' class='isPredict' /><input type='hidden' value='"+resourceGoodsId+"' class='sourceGoodsId' /> <input type='hidden' value='"+rootGoodsId+"' class='rootGoodsId' />"+
							"<td><input type='checkbox' class='checkboxes' name='chk_list'/></td>"+
//							"<td>"+goodsdata[i].code+"     "+isPredict+"</td>"+
							"<td>   <a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("+ goodsdata[i].id+ ")' id='goodsShow'>"+goodsdata[i].code+"</a> </td>"+
							"<td><span id='goodsTotal'>"+goodsdata[i].goodsTotal+"</span></td>"+
							"<td>"+tankcode+"</td>"+
							"<td><input type='text' class='form-control  goodsTank' maxlength='14' onkeyup='config.clearNoNum(this)' value='"+goodsTank+"' style='display:none'/>"+
								"<span id='goodsTankSp'>"+goodsTank+"</span></td>"+
							"<td><input type='text' class='form-control goodsInspect' maxlength='14' onkeyup='config.clearNoNum(this)' value='"+goodsInspect+"' style='display:none'/>"+
								"<span id='goodsInspectSp'>"+goodsInspect+"</span></td>"+
							"<td><input type='text' class='form-control  goodsOutPass' maxlength='14' onkeyup='config.clearNoNum(this)' value='"+goodsOutPass+"' style='display:none'/>"+
								"<span id='goodsOutPassSp'>"+goodsOutPass+"</span></td>"+
							"<td><input type='text' class='form-control  goodsOut' maxlength='14' onkeyup='config.clearNoNum(this)' value='"+goodsOut+"' style='display:none'/>"+
								"<span id='goodsOutSp'>"+goodsOut+"</span></td>"+
								"<td><input type='text' class='form-control  goodsDevily' maxlength='14' onkeyup='config.clearNoNum(this)' value='"+goodsDevily+"' style='display:none'/>"+
								"<span id='goodsDevilySp'>"+goodsDevily+"</span></td>"+
								"<td><input type='text' class='form-control  goodsCurrent' maxlength='14' onkeyup='config.clearNoNum(this)' value='"+goodsCurrent+"' style='display:none'/>"+
								"<span id='goodsCurrentSp'>"+goodsCurrent+"</span></td>"+
							"<td><input type='text' class='form-control  description' maxlength='14'  value='"+description+"' style='display:none'/>"+
							"<span id='descriptionSp'>"+description+"</span></td>";
							goods +="</tr>";
							$("#goods-table-storage").children("tbody").append(goods);
							
						}

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
	

	
	


//	
    
	var handleRecords = function() {
		
		var firstDate = new Date();

		firstDate.setDate(1); //第一天
		
		$('#startTime').val(new XDate(firstDate).toString('yyyy-MM-dd'));
		$('#endTime').val(new Date().Format("yyyy-MM-dd"));
		
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
	}).on('changeDate', function(ev){
		
	});
		
		config.load();
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
//	  					search();
	  					return item;
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.name == query;
                    });
  					if(client==null){
  						$('#product').removeAttr("data");
  						$('#product').val("");
//  						search();
  					}else{
  						$('#product').attr("data",client.id)
  					}
  				}
  				});
			}
		});
		
		$.ajax({
  			type : "get",
  			async:false,
  			url : config.getDomain()+"/tank/list?pagesize=0&type=1",
  			dataType : "json",
  			success : function(data) {
  				$('#tankId').typeahead({
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
	  					if(client!=null){
	  						$('#tankId').attr("data",client.id);
	  						
	  					}
//	  					search();
	  					return item;
  					},
					//移除控件时调用的方法
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.code == query;
                    });
  					//匹配不到就去掉id，让内容变空，否则给id
  					if(client==null){
  						$('#tankId').removeAttr("data");
  						$('#tankId').val("");
//  						search();
  					}else{
  						$('#tankId').attr("data",client.id);
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
  					menu:"<ul class=\"typeahead dropdown-menu\" style='height: 260px;width:"+$('#client').css("width")+";overflow-y: auto;overflow-x: hidden;'></ul>",
  					updater: function (item) {
	  					var client = _.find(data.data, function (p) {
	                        return p.name+" ["+p.code+"]" == item;
	                    });
	  					if(client!=null){
	  						$('#client').attr("data",client.id);
	  					}
//	  					search();
	  					return item;
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.name+" ["+p.code+"]" == query;
                    });
  					if(client==null){
  						$('#client').removeAttr("data");
  						$('#client').val("");
//  						search();
  					}else{
  						$('#client').attr("data",client.id)
  					}
  				}
  				});
  			}
  		});
		
		
		
		
		$.ajax({
  			type : "get",
  			async:false,
  			url : config.getDomain()+"/tank/list?pagesize=0&type=1",
  			dataType : "json",
  			success : function(data) {
  				$('#tankId').typeahead({
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
	  					if(client!=null){
	  						$('#tankId').attr("data",client.id);
	  						
	  					}
	  					return item;
  					},
					//移除控件时调用的方法
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.code == query;
                    });
  					//匹配不到就去掉id，让内容变空，否则给id
  					if(client==null){
  						$('#tankId').removeAttr("data");
  						$('#tankId').val("");
  					}else{
  						$('#tankId').attr("data",client.id);
  					}
  				}
  				});
  			}
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
			title : "货批号",
			width : 100,
			name : "cargoCode",
			render : function(item, name, index) {
				
				if(item.cargoCode!=null){
					return "<a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("
					+ item.goodsId
					+ ")' id='goodsShow'>"+item.cargoCode+"</a>";
					
				}else{
					return "";
				}
				

			}
		},{
			title : "货品",
			width : 50,
			name : "productName"
		}, {
			title : "罐号",
			width : 50,
			name : "tankCode"
		},{
			title : "货主",
			width : 80,
			name : "clientName",
			render: function(item, name, index){
				if(item.ladingClientName){
					return item.ladingClientName;
				}else{
					return item.clientName;
				}
			}
		},{
			title : "提单类型",
			width : 60,
			name : "clientName",
			render: function(item, name, index){
				if(item.ladingType==1){
					return "转卖";
				}else if(item.ladingType==2){
					return "发货";
				}else{
					return "";
				}
			}
		},{
			title : "上家货主",
			width : 80,
			name : "sClientName"
		},{
			title : "上家提单",
			width : 80,
			name : "sLadingCode",
			render: function(item, name, index){
				if(item.sLadingCode!=null&&item.sLadingCode!=""){
					return item.sLadingCode;
				}else{
					return "";
				}
			}
		},{
			title : "提单起止/有效期",
			width : 80,
			name : "ladingTime",
			render: function(item, name, index){
				if(item.ladingTime){
					return item.ladingTime.split(" ")[0];
				}else{
					return "";
				}
			}
		},{
			title : "原号",
			width : 80,
			name : "rootCode",
				render: function(item, name, index){
					if(item.rootCode!=null&&item.rootCode!=""){
						return item.rLadingCode;
					}else if((item.rootCode==null||item.rootCode=="")&&item.sourceCode!=null&&item.sourceCode!=""){
						return item.ladingCode;
					}
					else{
						return item.cargoCode;
					}
					
				}
		},{
			title : "调号",
			width : 80,
			name : "sourceCode",
			render: function(item, name, index){
				if(item.sourceCode!=null&&item.sourceCode!=""){
					return item.ladingCode;
				}else{
					return "";
				}
			}
		},{
			title : "入库(吨)",
			width : 80,
			name : "goodsInbound"
		},
		{
			title : "当前封量(吨)",
			width : 80,
			name : "goodsSave"
		},
		{
			title : "现结存量(吨)",
			width : 80,
			name : "goodsCurrent"
		},
		{
			title : "待提量(吨)",
			width : 60,
			name : "goodsWait"
		},
		{
			title : "参量(吨)",
			width : 70,
			name : "goodsCurrent",
			render: function(item, name, index){
				return util.FloatSub(item.goodsCurrent,item.goodsWait);
			}
		}
		 
		 ];
		
		
		
		
		
		
		
		
		
//		var columns = [ {
//			title : "货批编号",
//			name : "cargodata",
//			render: function(item, name, index){
//				if(item[name].code){
//					return "<a href='javascript:void(0)' onclick='CargoLZ.openCargoLZ("
//					+ item[name].id
//					+ ")' >"
//					+item[name].code+ "</a>";
//					
//				}
//				return "";
//			}
//		}, {
//			title : "货主",
//			name : "cargodata",
//				render: function(item, name, index){
//					return item[name].clientName;
//				}
//		}, {
//			title : "货品名称",
//			name : "cargodata",
//			render: function(item, name, index){
//				return item[name].productName;
//			}
//		},{
//			title : "进货船",
//			width : 100,
//			name : "cargodata",
//			render: function(item, name, index){
//				
//					return item[name].shipName;
//				}
//		},{
//			title : "到港日期",
//			width : 100,
//			name : "cargodata",
//			render: function(item, name, index){
//				if(item[name].arrivalTime){
//					return item[name].arrivalTime.split(" ")[0];
//				}else{
//					return "";
//				}
//				}
//		},{
//			title : "罐检数(吨)",
//			name : "cargodata",
//			render: function(item, name, index){
//				return item[name].goodsTank;
//			}
//		}, {
//			title : "商检数(吨)",
//			name : "cargodata",
//			render: function(item, name, index){
//				return item[name].goodsInspect;
//			}
//		}, 
//		{
//			title : "内控放行数(吨)",
//			name : "cargodata",
//			render: function(item, name, index){
//				return item[name].goodsOutPass;
//			}
//		}, 
//		{
//			title : "说明",
//			name : "cargodata",
//			render: function(item, name, index){
//				return item[name].description;
//			}
//		}, 
//		{
//			title : "操作",
//			name : "cargodata",
//			render: function(item, name, index){
//             	return "<a href='#/goodsManagerEdit?id="+item[name].id+"&productId="+item[name].productId+"'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a>";
//			}
//		}  ];

		
		
		$('[data-role="storageGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			autoLoad:false,
			url : config.getDomain()+"/statistics/getgoods?showVirTime=2"
		});
		
		order=0;
		show=0;
		goodsManager.search();
		
		$(".show").unbind('click'); 
		$(".show").click(function(){
			if(show==1){
				$("#showText").html("显示全部");
				$('#sText').html("当前显示未清盘");
				//当前显示未清盘
				show=0;
			}else{
				$("#showText").html("显示未清盘");
				$('#sText').html("当前显示全部");
				//当前显示全部
				show=1;
			}
			goodsManager.search();
		});
		
		
	};

	
	function setProductId(id){
		productId=id;
	}
	
	return {
		init : function() {
			initFormIput();
			handleRecords();
		},
		search : function() {
//			console.log($(".tankNum").val());
//			var tank = $(".tankNum").val();
//			initTank(tank);
//			
			if($('#startTime').val()==""||$('#endTime').val()==""){
				$("body").message({
					type : 'error',
					content : '请选择时间！'
				});
				return;
			}	
			var params = {};
	        $("#goodsListForm").find('.form-control').each(function(){
	            var $this = $(this);
	            var name = $this.attr('name');
	             if(name){
	            	 if($this.attr("data")){
	            		 params[name] = $this.attr("data");
	            	 }else{
	            		 params[name] = $this.val();
	            	 }
	            }
	        });
	        
	        params['showVir']=showVir;
	        
	        params['order']=order;
	        
	        params['isFinish']=show;
	        
	       $('[data-role="storageGrid"]').getGrid().search(params);
	       $("#isShowAll").attr('checked',false);
	      	$(".totalFeeDiv").remove();
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
		changeTank:function(){
			changeTank();
		},
		setProductId:function(id){
			setProductId(id);
		},goodsLoss:function(){
			goodsLoss();
		}
		
	};
	
	
	
}();