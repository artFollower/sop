var Goods= function() {

	var searchCount=0;
	
	var order=0;//0：建仓倒序  1：建仓正序  2：罐号正序 3：罐号倒序
	
	var show=0;//0:未清盘  1:全部
	
	var simple=0;//0：详细  1：精简
	
	var showVir=0;//0:不显示虚拟库   1：显示虚拟库
	var showVirTime=2;
	
	
	
	//获取项目路径
	function getRootPath(){
		var curWwwPath=window.document.location.href;    
		var pathName=window.document.location.pathname;    
		var pos=curWwwPath.indexOf(pathName);    
		var localhostPaht=curWwwPath.substring(0,pos);    
		var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);    
		return(localhostPaht+projectName);
	}
	
	function dayStatics(type){
		

		$.get(config.getResource()+"/pages/statistics/cargo/day_statics.jsp").done(function(data){
			dialog = $(data) ;
			
			dialog.modal({
				keyboard: true
			});
			
			
			dialog.find('.time').datepicker({
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

				var params = {};
				 var name = "time";
		                params[name] = dialog.find(".time").val();
		                dialog.find('[data-role="dayGrid"]').getGrid().search(params);
			
			
		});
			
			
			
			var columns2 = [  {
				title : "日期",
				name : "time"
			},{
				title : "操作",
				name : "id",
				render: function(item, name, index){
						return "<a href='"+getRootPath()+item.url+"' onclick='' class='btn btn-xs blue'><span class='fa fa-arrow-down   ' title='导出'></span></a>";
				}
			}];
			
			
			//出库列表
			dialog.find('[data-role="dayGrid"]').grid({
				identity : 'id',
				columns : columns2,
				isShowIndexCol : false,
				isShowPages : false,
				autoLoad:true,
				url : config.getDomain()+"/statistics/getDayStatics?type=1"
			});
			
			
		});
	
		
		
		
	}
	
	
	//货体换罐
	function changeTank(){
		
		var data = $('[data-role="goodsListGrid"]').getGrid().selectedRowsIndex();
		var $this = $(this);
		if (data.length == 0) {
			$('[data-role="goodsListGrid"]').message({
				type : 'warning',
				content : '请选择要换罐的货体'
			});
			return;
		}
		if (data.length >1) {
			$('[data-role="goodsListGrid"]').message({
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
					goodsId = $('[data-role="goodsListGrid"]').getGrid().selectedRows()[0].goodsId;
					productId = $('[data-role="goodsListGrid"]').getGrid().selectedRows()[0].productId;
					
				openTankModal(goodsId,productId);
				
			}
		
	}
	//打开拆分货体dialog
	function openTankModal(goodsId,productId){
		$.get(config.getResource()+"/pages/outbound/goodsManager/changeTank.jsp").done(function(data){
			dialog = $(data) ;
				initTankModal(dialog,goodsId,productId);
			dialog.modal({
				keyboard: true
			});
		});
	}

function initTankModal(dialog,goodsId,productId){
		
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
					search();
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
	
	
	
	//导出通知单 
	function exportExcel(){
		var st = $("#startTime").val() ;
		var ed = $("#endTime").val() ;
		var pid = util.isNull($("#product").attr("data"),1) ;
		var cid = util.isNull($("#client").attr("data"),1) ;
		var code=$(".code").val();
		var cargoCode=$(".cargoCode").val();
		var tankId=util.isNull($("#tankId").attr("data"),1) ;
		var url = config.getDomain()+"/statistics/exportGoodsExcel?tankId="+tankId+"&&showVirTime="+showVirTime+"&showVir="+showVir+"&&isFinish="+show+"&&name=货体汇总&&productId="+pid+"&&clientId="+cid+"&&code="+code+"&&cargoCode="+cargoCode+"&&sStartTime="+st+"&&sEndTime="+ed+"&&type="+simple ;
		window.open(url) ;
	}
	 
	var search = function() {
//		console.log($(".tankNum").val());
//		var tank = $(".tankNum").val();
//		initTank(tank);
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
        params['showVirTime']=showVirTime;
        params['order']=order;
        
        params['isFinish']=show;
        
       $('[data-role="goodsListGrid"]').getGrid().search(params);
       $("#isShowAll").attr('checked',false);
      	$(".totalFeeDiv").remove();
	};
	
	

	var setDetail=function(){
		$(".simple").text("点击切换精简模式");
		$('[data-role="goodsListGrid"]').remove();
		
		$("#grid").append("<div data-role='goodsListGrid'>");
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
			width : 60,
			name : "productName"
		}, {
			title : "进货船",
			name : "shipName",
			render: function(item, name, index){
				var shipName="";
				if(item.shipName!=null){
					shipName=item.shipName;
					return  shipName;
				}else{
					return "";
				}
			}
		},{
			title : "到港日期",
			name : "shipName",
			render: function(item, name, index){
				if(item.arrivalStartTime){
				return item.arrivalStartTime.split(" ")[0];
				}else{
					return "";
				}
			}
		}, {
			title : "罐号",
			width : 50,
			name : "tankCode"
		},{
			title : "货体",
			width : 150,
			name : "goodsCode"
		},{
			title : "货主",
			width : 100,
			name : "clientName",
			render: function(item, name, index){
				if(item.ladingClientName){
					return item.ladingClientName;
				}else{
					return item.clientName;
				}
			}
		},{
			title : "原号",
			width : 100,
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
			width : 100,
			name : "sourceCode",
			render: function(item, name, index){
				if(item.sourceCode!=null&&item.sourceCode!=""){
					return item.ladingCode;
				}else{
					return "";
				}
			}
		},{
			title : "起计/有效期",
			width : 80,
			name : "ladingTime",
			render: function(item, name, index){
				if(item.ladingTime=="长期"){
					return item.ladingTime;
				}
				if(item.ladingTime){
					return item.ladingTime.split(" ")[0];
				}else{
					return "";
				}
			}
		},{
			title : "建仓日期",
			width : 100,
			name : "arrivalStartTime",
				render: function(item, name, index){
					if(item.createTime)
					return item.createTime.split(" ")[0];
					}
		}, {
			title : "入库(吨)",
			width : 80,
			name : "goodsInbound"
		}, {
			title : "货权调入(吨)",
			width : 80,
			name : "goodsIn"
		},{
			title : "提货权调入(吨)",
			width : 80,
			name : "goodsTIn"
		},
		{
			title : "货权调出(吨)",
			width : 80,
			name : "goodsOut"
		}, {
			title : "提货权调出(吨)",
			width : 80,
			name : "goodsTOut"
		}, {
			title : "实发总数(吨)",
			width : 80,
			name : "totalCount"
		},
		{
			title : "当时封量(吨)",
			width : 80,
			name : "goodsSave"
		},{
			title : "之前发货(吨)",
			width : 80,
			name : "goodsOld"
		},{
			title : "本段实发数(吨)",
			width : 80,
			name : "goodsToday"
		},{
			title : "现待提量(吨)",
			width : 60,
			name : "goodsWait"
		},{
			title : "当时结存量(吨)",
			width : 80,
			name : "goodsSurplus"
		}
//		{
//			title : "现结存量(吨)",
//			width : 80,
//			name : "goodsCurrent"
//		}
		 
		 ];

	
		/*解决id冲突的问题*/
		$('[data-role="goodsListGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			autoLoad:false,
			url : config.getDomain()+"/statistics/getgoods"
		});
		order=0;
		show=0;
		search();
	};
	
	
	var setSimple=function(){
		$(".simple").text("点击切换全部模式");
		$('[data-role="goodsListGrid"]').remove();
		
		$("#grid").append("<div data-role='goodsListGrid'>");
		
		
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
			title : "起计/有效期",
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
		},{
			title : "扣损(吨)",
			width : 80,
			name : "goodsLoss"
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

	
		/*解决id冲突的问题*/
		$('[data-role="goodsListGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			autoLoad:false,
			url : config.getDomain()+"/statistics/getgoods"
		});
		order=0;
		show=0;
		search();
	};
	
	
	
	var handleRecords = function() {
		simple=0;
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
		
		if(searchCount==1){
			searchCount=0;
		}else{
//			search();
			searchCount+=1;
		}
		
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
			width : 60,
			name : "productName"
		},
//		{
//			title : "海关放行编号",
//			width : 100,
//			name : "customsPassCode"
//		}, 
		{
			title : "进货船",
			name : "shipName",
			render: function(item, name, index){
				var shipName="";
				if(item.shipName!=null){
					shipName=item.shipName;
					return  shipName;
				}else{
					return "";
				}
			}
		},{
			title : "到港日期",
			name : "shipName",
			render: function(item, name, index){
				if(item.arrivalStartTime){
				return item.arrivalStartTime.split(" ")[0];
				}else{
					return "";
				}
			}
		}, {
			title : "罐号",
			width : 50,
			name : "tankCode"
		},{
			title : "货体",
			width : 150,
			name : "goodsCode"
		},{
			title : "货主",
			width : 100,
			name : "clientName",
			render: function(item, name, index){
				if(item.ladingClientName){
					return item.ladingClientName;
				}else{
					return item.clientName;
				}
			}
		},{
			title : "原号",
			width : 100,
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
			width : 100,
			name : "sourceCode",
			render: function(item, name, index){
				if(item.sourceCode!=null&&item.sourceCode!=""){
					return item.ladingCode;
				}else{
					return "";
				}
			}
		},{
			title : "起计/有效期",
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
			title : "建仓日期",
			width : 100,
			name : "arrivalStartTime",
				render: function(item, name, index){
					if(item.createTime)
					return item.createTime.split(" ")[0];
					}
		}, {
			title : "入库(吨)",
			width : 80,
			name : "goodsInbound"
		}, {
			title : "货权调入(吨)",
			width : 80,
			name : "goodsIn"
		},{
			title : "提货权调入(吨)",
			width : 80,
			name : "goodsTIn"
		},
		{
			title : "货权调出(吨)",
			width : 80,
			name : "goodsOut"
		}, {
			title : "提货权调出(吨)",
			width : 80,
			name : "goodsTOut"
		}, {
			title : "实发总数(吨)",
			width : 80,
			name : "totalCount"
		},
		{
			title : "当时封量(吨)",
			width : 80,
			name : "goodsSave"
		},{
			title : "扣损(吨)",
			width : 80,
			name : "goodsLoss"
		},{
			title : "之前发货(吨)",
			width : 80,
			name : "goodsOld"
		},{
			title : "本段实发数(吨)",
			width : 80,
			name : "goodsToday"
		},{
			title : "当时结存量(吨)",
			width : 80,
			name : "goodsSurplus"
		},
//		{
//			title : "现结存量(吨)",
//			width : 80,
//			name : "goodsCurrent"
//		}
		 
		 ];

	
		/*解决id冲突的问题*/
		$('[data-role="goodsListGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			autoLoad:false,
			url : config.getDomain()+"/statistics/getgoods"
		});
		order=0;
		show=0;
		
		showVir=1;
		showVirTime=2;
		search();
		
		
		
		$("#isShowAll").unbind('click'); 
		$("#isShowAll").click(function(){
			$this=$(this);
			if($this.is(":checked")){//
				
				if($('#startTime').val()==""){
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
				 params['showVirTime']=showVirTime;
			        params['order']=order;
			        
			        params['isFinish']=show;
					//获取条件下的统计数据
					$.ajax({
                        type:'get',
						url:config.getDomain()+"/statistics/getgoodstotal",
						data:params,
						dataType:'json',
						success:function(data){
							util.ajaxResult(data,'统计',function(ndata){
								var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
				                   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>当时剩余存量: </label>" +
				                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+data.map.goodsTotal+" 吨</label>"	+	
						                   "<label class='control-label' style='margin-left: 30px;margin-right:10px;'>本段实发总量: </label>" +
					                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+data.map.goodsToday+" 吨</label>"	+
								        "</div>";
								$('[data-role="goodsListGrid"]').find(".grid-body").parent().append(totalHtml);
							},true);
						}
					});
				
			}else{
				$this.attr('checked',false);
	        	$(".totalFeeDiv").remove();
			}
			});
		
		
		
		
		$(".btn-simple").unbind('click'); 
		$(".btn-simple").click(function(){
			
			if(simple>0){
				simple=0;
				
				setDetail();
				
			}else{
				simple=1;
				
				setSimple();
			}
			
			
		});
		
		$(".order-jc").unbind('click'); 
		$(".order-jc").click(function(){
			if(order>0){
				order=0;
			}else{
				order=1;
			}
			search();
		});
		$(".order-tank").unbind('click'); 
		$(".order-tank").click(function(){
			if(order==2){
				order=3;
			}else{
				order=2;
			}
			search();
		});
		
		
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
			search();
		});
		
		
		$(".showVir").unbind('click'); 
		$(".showVir").popover();
		$(".showVir").click(function(){
			if(showVir==1){
				$(".showVir").addClass("blue");
				showVir=2;
				$(".showVir").attr("data-content","主副库");
			}else if(showVir==2){
				$(".showVir").removeClass("blue");
				$(".showVir").addClass("red");
				showVir=3;
				$(".showVir").attr("data-content","副库");
			}else{
				
				$(".showVir").removeClass("red");
				showVir=1;
				$(".showVir").attr("data-content","主库");
			}
//			search();
		});
		
		$(".showVirTime").unbind('click'); 
		$(".showVirTime").popover();
		$(".showVirTime").click(function(){
			if(showVirTime==2){
				$(".showVirTime").addClass("green");
				showVirTime=1;
				$(".showVirTime").attr("data-content","原始时间");
			}else{
				
				$(".showVirTime").removeClass("green");
				showVirTime=2;
				$(".showVirTime").attr("data-content","转账时间");
			}
//			search();
		});
		
		$(".btn-pass").unbind('click'); 
		$(".btn-pass").click(function(){
			var data = $('[data-role="goodsListGrid"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (data.length == 0) {
				$('[data-role="goodsListGrid"]').message({
					type : 'warning',
					content : '请选择要放行的货体'
				});
				return;
			}
			if (data.length >1) {
				$('[data-role="goodsListGrid"]').message({
					type : 'warning',
					content : '只能选择一个放行的货体'
				});
				return;
			}
			dataGrid = $(this);
//			$.get(config.getResource()+"/pages/contract/intent/edit.jsp").done(function(_data){
//				dataGrid = _data;
//			});
			if($('[data-role="goodsListGrid"]').getGrid().selectedRows()[0].goodsOut>0){
				$('[data-role="goodsListGrid"]').message({
					type : 'warning',
					content : '已经调出的货体无法海关放行，请先退回！'
				});
				return;
			}else{
				openPassModal($('[data-role="goodsListGrid"]').getGrid().selectedRows()[0].goodsId);
			}
		});
	};

	function openPassModal(goodsId){
		$.get(config.getResource()+"/pages/statistics/goods/pass.jsp").done(function(data){
			dialog = $(data) ;
			dialog.find(".button-pass").click(function(){
				 if(config.validateForm($(".passForm"))){
					 var goodsInfo=$('[data-role="goodsListGrid"]').getGrid().selectedRows()[0];
					 
					 if(parseFloat($('.passCount').val())>parseFloat(goodsInfo.goodsTotal)){
							$(dialog).message({
								type : 'warning',
								content : '放行量不能大于货体总量，请重新输入！'
							});
							return;
					 }
					 
					 var type;
					 
					 if(goodsInfo.rootGoodsId!=""&&goodsInfo.rootGoodsId!=null){
						 type=1;
					 }else{
						 type=0;
					 }
					 
				$.ajax({
		  			type : "post",
		  			url : config.getDomain()+"/statistics/custompass",
		  			data:{
		  				"oldGoodsId":goodsId,
		  				"goodsTotal":$('.passCount').val(),
						"customsPassCode":$('.passCode').val(),
						"type":type
		  			},
		  			dataType : "json",
		  			success : function(data) {
		  				if (data.code == "0000") {
							$("body").message({
								type : 'success',
								content : '操作成功'
							});
								$('[data-role="goodsListGrid"]').grid('refresh');
								dialog.remove();
						}else {
							$("body").message({
								type : 'error',
								content : '操作失败'
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
	
	
	
	
	
	
	return {
		init : function() {
			handleRecords();
		},
		changeTank:function(){
			changeTank();
		},
		exportExcel:exportExcel,
		search:search,
		dayStatics:dayStatics

	};
	
	
	
}();