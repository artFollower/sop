var CargoLZ = function() {
	
	var mType=2;
	
	// 打开树状图dialog
	function openCargoLZ(id,ladingId) {
		// $('#ajax').load(config.getResource()+"/pages/inbound/arrival/addCustomer.jsp");
		// $('#ajax').modal('show');
		// $.get(config.getResource()+"/pages/inbound/arrival/showGoods.jsp").done(function(data){
		// dialog = $(data) ;
		// initModal1(dialog,id);
		// dialog.modal({
		// keyboard: true
		// });
		// });

		$.get(config.getResource() + "/pages/inbound/arrival/mShowGoods.jsp")
				.done(function(data) {
					dialog = $(data);
					initCargoLZ(dialog, id,ladingId);
					dialog.find(".btn-print").click(function() {
						dialog.find('.modal-body').jqprint();
					});
					dialog.modal({
						keyboard : true
					});
				});

	}

	// 打开货体流水表dialog
	function openCargoLS(id) {

		$.get(config.getResource() + "/pages/outbound/lading/goodsLS.jsp")
				.done(function(data) {
					dialog = $(data);
					initCargoLS(dialog, id);
					dialog.modal({
						keyboard : true
					});
				});

	}

	// 货体流水表
	function initCargoLS(dialog, id) {
		
		
		$.ajax({
			async : false,
			type : "get",
			url : config.getDomain()
					+ "/lading/getGoods?goodsId="+id,
			dataType : "json",
			success : function(data) {
				if(data.code=="0000"){
					dialog.find('.client').html(data.data[0].clientName);
					dialog.find('.code').html(data.data[0].code);
					dialog.find('.productName').html(data.data[0].productName);
				}
				
			}
		});
		
		
		dialog.find(".type1").unbind('click'); 
		dialog.find(".type1").click(function(){
			if(mType==2){
				$(".type1").addClass("green");
				mType=1;
			}else{
				
				$(".type1").removeClass("green");
				mType=2;
			}
//			search();
			dialog.find('[data-role="goodsLSGrid"]').grid('refresh');
		});
		
		
		var colums = [
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
				{
					title : "上下家",
					name : "ladingClientName"
				},
				{
					title : "方式",
					name : "type",
					render : function(item, name, index) {
						switch (item.type) {
						case 1:
							return "入库";
							break;
						case 2:
							return "调入";
							break;
						case 3:
							return "调出";
							break;
						case 4:
							return "封放";
							break;
						case 5:
							if(item.actualType>0){
								return "发货";
								
							}else{
								return "发货待提";
							}
							break;
						case 6:
							return "退回";
							break;
						case 61:
							return "调入退回";
							break;
						case 62:
							return "调出退回";
							break;
						case 7:
							return "预入库";
							break;
						case 9:
							return "扣损";
							break;
						case 10:
							if(item.deliverNo=="1"){
								return "扣损";
							}else{
								
								return "商检&封放";
							}
							break;
						case 11:
							return "清盘";
							break;
						case 12:
							return "复盘";
							break;
						}
					}
				},
				{
					title : "日期",
					name : "createTime",
					render : function(item, name, index) {
						if(mType==1){
							return item.mCreateTime;
//							return new Date(item.createTime * 1000)
//							.Format("yyyy-MM-dd HH:mm:ss");
						}else{
//							return new Date(item.originalTime * 1000)
//							.Format("yyyy-MM-dd HH:mm:ss");
							return item.mOriginalTime;
						}
					}
				}, {
					title : "单号",
					name : "ladingCode1",
					render : function(item, name, index) {
						
						if(item.type==1){
							return item.customLading==null?"":item.customLading;
						}
						
						if(item.ladingEvidence!=null&&item.ladingEvidence!=""){
							return item.ladingEvidence;
						}
						if(item.nextLadingCode!=null&&item.nextLadingCode!=""){
							return item.nextLadingCode;
							
						}else{
							return item.ladingCode1;
						}
					}
				}, {
					title : "货量变化(吨)",
					name : "goodsChange",
					render : function(item, name, index) {
						return parseFloat(item.goodsChange).toFixed(3);
					}
				}, {
					title : "存量(吨)",
					name : "surplus",
					render : function(item, name, index) {
//						
//						if(item.type==5&&item.actualNum==0){
//							return "";
//						}else{
							return parseFloat(item.surplus).toFixed(3);
//						}
					}
				}, {
					title : "封量(吨)",
					name : "goodsSave",
					render : function(item, name, index) {
						if(item.type==5&&item.actualNum==0){
							return "";
						}else{
						return parseFloat(item.goodsSave).toFixed(3);
						}
					}
				}, {
					title : "发货单",
					name : "serial",
					render : function(item, name, index) {
						if(item.type==5){
							return item.serial;
						}else{
							return "";
						}
					}
				}, {
					title : "车船号",
					name : "carshipCode",
					render : function(item, name, index) {
						if(item.type==1){
							return item.arrivalShip;
						}else{
							return item.carshipCode;
						}
					}
				} ];

		dialog.find('[data-role="goodsLSGrid"]').grid({
			identity : 'id',
			columns : colums,
			isShowIndexCol : false,
			isShowPages : false,
			lockWidth : false,
			// isUserLocalData: true,
			// localData : data,
			url : config.getDomain() + "/lading/getGoodsLS?goodsId=" + id
		});
	}

	function initCargoLZ(dialog, id,ladingId) {
		
		
		$.ajax({
			async : false,
			type : "get",
			url : config.getDomain()
					+ "/storageConfirm/getCargo?cargoId="+id,
			dataType : "json",
			success : function(data) {
				if(data.code=="0000"){
					
					dialog.find('.code').html(data.data[0].cargodata.code);
					dialog.find('.productName').html(data.data[0].cargodata.productName);
				}
				
			}
		});
		
//		var columns = [
//				{
//					title : "货主名",
//					name : "name",
//					width : 250,
//					render : function(item, name, index) {
//						if (item.goodsCurrent > 0) {
//							return "<a href='javascript:void(0)' onclick='GoodsLZ.openGoodsLS("
//									+ item.id
//									+ ")' ><span class='fa fa-square' title='有量'></span>"
//									+ item.name + "</a>";
//						} else {
//							return "<a href='javascript:void(0)' onclick='GoodsLZ.openGoodsLS("
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
//			url : config.getDomain() + "/lading/showCaroLZ?id=" + id,
//			tree : {
//				column : 'name'
//			}
//		});
		
		
		if(ladingId){
			
		}else{
			ladingId=-1;
		}
		var tree =dialog.find('.jstree').cloudTreeGrid(
				{
					columns : [
							{
					title : "货主名",
					name : "NAME",
					width : 1250,
					render : function(item, name, index) {
						var bold="";
						if(ladingId==item.ladingId){
							bold="style='font-weight:bold;color:#008B8B;'";
						}
						if (item.goodsCurrent > 0) {
							return "<a href='javascript:void(0)'"+bold+" onclick='GoodsLZ.openGoodsLS("
									+ item.id
									+ ")' ><span class='fa fa-square' title='有量'></span>"
									+ item.NAME + "</a>";
						} else {
							return "<a href='javascript:void(0)'"+bold+" onclick='GoodsLZ.openGoodsLS("
									+ item.id
									+ ")'><span class='fa fa-square-o' title='无量'></span>"
									+ item.NAME + "</a>";
						}
					}
				},{
								'title' : '提单代码',
								'data' : 'ladingCode',
								'width' : 200,
								render : function(item, name, index) {
									var bold="";
									if(ladingId==item.ladingId){
										bold="style='font-weight:bold;color:#008B8B;'";
									}
									if(item.ladingCode==null||item.ladingCode==undefined){
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
									if(ladingId==item.ladingId){
										bold="style='font-weight:bold;color:#008B8B;'";
									}
									if(item.ladingType==null){
										return "";
									}else{
										return '<label '+bold+'>'+item.ladingType+'</label>';
									}
								}
							},{
								'title' : '仓储费起计日期/提单有效期',
								'data' : 'ladingTime',
								'width' : 220,
								render : function(item, name, index) {
									var bold="";
									if(ladingId==item.ladingId){
										bold="style='font-weight:bold;color:#008B8B;'";
									}
									if(!item.sourceGoodsId&&!item.rootGoodsId&&item.tArrivalStartTime&&item.period){
										
										ladingTime=new Date(item.tArrivalStartTime*1000+item.period*86400000).Format("yyyy-MM-dd")+" 00:00:00";
										return '<label '+bold+'>'+ladingTime+'</label>';
									}
									if(item.ladingTime==null){
										return "";
									}else{
										return '<label '+bold+'>'+item.ladingTime.substring(0,item.ladingTime.length-2)+'</label>';
									}
									
									
								}
								
							}, {
								'title' : '进货量(吨)',
								'data' : 'goodsTotal',
								width : 100,
								render : function(item, name, index) {
									var bold="";
									if(ladingId==item.ladingId){
										bold="style='font-weight:bold;color:#008B8B;'";
									}
									if(item.goodsInspect!=0&&item.goodsInspect!=null){
										return '<label '+bold+'>'+item.goodsInspect+'</label>';
									}
									else{
										var goods=item.goodsTank==null?"0":item.goodsTank;
										
										return '<label '+bold+'>'+goods+'</label>';
									}
								}
							}, {
								'title' : '调出总量(吨)',
								width : 100,
								'data' : 'goodsOut',
								render : function(item, name, index) {
									var bold="";
									if(ladingId==item.ladingId){
										bold="style='font-weight:bold;color:#008B8B;'";
									}
									if(item.goodsOut!=null){
										return '<label '+bold+'>'+item.goodsOut+'</label>';
									}else{
										return '<label '+bold+'>'+0+'</label>';
									}
								}
							}, {
								'title' : '发货量(吨)',
								'data' : "send",
								width : 100,
								render : function(item, name, index) {
									var bold="";
									if(ladingId==item.ladingId){
										bold="style='font-weight:bold;color:#008B8B;'";
									}
									return '<label '+bold+'>'+util.FloatSub(util.FloatSub(item.goodsTotal
											,item.goodsOut)
											,item.goodsCurrent-item.goodsLoss)+'</label>';
								}
							}, {
								'title' : '待提量(吨)',
								'data' : 'goodsWait',
								 render : function(item, name, index) {
									 var bold="";
										if(ladingId==item.ladingId){
											bold="    style='font-weight:bold;color:#008B8B;'";
										}
										return '<label '+bold+'>'+util.toDecimal3(item.goodsWait,true)+'</label>';
								 }
							},{
								'title' : '扣损量(吨)',
								'data' : 'goodsLoss',
								width : 100,
								 render : function(item, name, index) {
									 var bold="";
										if(id==item.id){
											bold="    style='font-weight:bold;color:#008B8B;'";
										}
										return '<label '+bold+'>'+util.toDecimal3(item.goodsLoss,true)+'</label>';
								 }
							},{
								'title' : '当前存量(吨)',
								'data' : 'goodsCurrent',
								width : 100,
								 render : function(item, name, index) {
									 var bold="";
										if(ladingId==item.ladingId){
											bold="    style='font-weight:bold;color:#008B8B;'";
										}
										return '<label '+bold+'>'+util.toDecimal3(item.goodsCurrent,true)+'</label>';
								 }
							} ],

							url : config.getDomain() + "/lading/showCaroLZ?cargoId=" + id
				});
		
	}
	
	
	
	return {
		openCargoLZ : function(id,ladingId) {
			openCargoLZ(id,ladingId);
		},
		openGoodsLS : function(id) {
			openGoodsLS(id);
		}
};
	}();