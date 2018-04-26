var TankLog = function() {
	var dataSelected=null;
	var tankCode=null;
	var tankId=null;
	var productName=null;
	var productId=null;
	var dialog1=null;
	var searchCount=0;
	var isclick=0;
	//导出通知单 
	function exportExcel(){
		var params = {};
		 $("#tanklogFirstForm").find('.form-control').each(function(){
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
		
		
		var productId = params["productId"] ;
		var tankId = params["tankId"];
		var year = params["year"] ;
		var url = config.getDomain()+"/tanklog/exportExcel?tankId="+tankId+"&&year="+year+"&productId="+productId ;
		window.open(url) ;
	}
	
	
	//取消入库关联
	function updateInbounddisconnect(tanklogStoreId,type,logId){
		
		config.load();
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/tanklog/disconnectInbound?tanklogStoreId="+tanklogStoreId+"&type="+type+"&logId="+logId,
  			dataType : "json",
  			success : function(data) {
  				config.unload();
  				if (data.code == "0000") {
					$("body").message({
	                    type: 'success',
	                    content: '取消关联成功'
	                });
				} else {
					$("body").message({
	                    type: 'error',
	                    content: '取消关联失败'
	                });
  			}
  				dialog.remove();
  			}
		});
		
	}
	
	//取消前尺关联
	function disconnectInbound(i){

		$.get(config.getResource()+"/pages/inbound/tanklog/search_disconnect.jsp").done(function(data){
			dialog = $(data) ;
			
			dialog.modal({
				keyboard: true
			});
			
			var columns1 = [  {
				title : "前尺-液位(毫米)",
				name : "startLevel",
				render: function(item, name, index){
					if(item.startLogId==$("#id").val()&&item.startType==i){
						return "<label style='font-weight:bold;' class='text'>"+item.startLevel+"</label>";
					}
					return item.startLevel;
			}
			},{
				title : "前尺-重量(吨)",
				name : "startWeight",
				render: function(item, name, index){
					if(item.startLogId==$("#id").val()&&item.startType==i){
						return "<label style='font-weight:bold;' class='text'>"+item.startWeight+"</label>";
					}
					return item.startWeight;
			}
			},{
				title : "前尺-温度(℃)",
				name : "startTemperature",
				render: function(item, name, index){
					if(item.startLogId==$("#id").val()&&item.startType==i){
						return "<label style='font-weight:bold;' class='text'>"+item.startTemperature+"</label>";
					}
					return item.startTemperature;
			}
			},{
				title : "后尺-液位(毫米)",
				name : "endLevel",
				render: function(item, name, index){
					if(item.endLogId==$("#id").val()&&item.endType==i){
						return "<label style='font-weight:bold;' class='text'>"+item.endLevel+"</label>";
					}
					return item.endLevel;
			}
			},{
				title : "后尺-重量(吨)",
				name : "endWeight",
				render: function(item, name, index){
					if(item.endLogId==$("#id").val()&&item.endType==i){
						return "<label style='font-weight:bold;' class='text'>"+item.endWeight+"</label>";
					}
					return item.endWeight;
			}
			},{
				title : "后尺-温度(℃)",
				name : "endTemperature",
				render: function(item, name, index){
					if(item.endLogId==$("#id").val()&&item.endType==i){
						return "<label style='font-weight:bold;' class='text'>"+item.endTemperature+"</label>";
					}
					return item.endTemperature;
			}
			}, {
				title : "操作",
				name : "id",
				render: function(item, name, index){
						return "<a href='javascript:void(0)' onclick='TankLog.updateInbounddisconnect("+item.id+","+i+","+$("#id").val()+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='取消关联'></span></a>";
				}
			}];
			
			
			//入库列表
			dialog.find('[data-role="searchGrid"]').grid({
				identity : 'id',
				columns : columns1,
				isShowIndexCol : false,
				isShowPages : false,
				url : config.getDomain()+"/tanklog/getinboundDisconnect?logId="+$("#id").val()+"&type="+i
			});
			
		});
	
	}
	
	
	// 改变tab
	function changeConnectTab(obj, item) {
//		$(obj).parent().addClass("active").siblings().removeClass("active");
//		$(obj).parent().addClass("active").siblings().removeClass("active");
		$("#portlet_tab"+item).addClass("active").siblings().removeClass("active");
//		$(".search-key").val("");
	}
	
	//入库关联（前尺关联了前尺）
	function connectInboundToF(arrivalId){
		var data={ 
				//log的前尺
				"startType":1,
				"startLogId":$("#id").val(),
				"arrivalId": arrivalId,
  	            "tankId":$("#tank").attr("data"),
  	            "startLevel":$("#fLiquidLevel").val(),
  	            "startWeight":$("#fWeight").val(),
  	            "startTemperature":$("#fTemperature").val(),
  	            "startHandLevel":$("#fHandLevel").val(),
  	            "startHandWeight":$("#fHandWeight").val()
  	            };
		
		
		config.load();
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/tanklog/addTankLogStore",
  			dataType : "json",
  			data:data,
  			success : function(data) {
  				config.unload();
  				if (data.code == "0000") {
					$("body").message({
	                    type: 'success',
	                    content: '关联成功'
	                });
				} else {
					$("body").message({
	                    type: 'error',
	                    content: '关联失败'
	                });
  			}
  				dialog.remove();
  			}
		});
		
		
	}
	//入库关联（后尺关联了前尺）
	function connectInboundToB(arrivalId){
		var data={ 
				//log的后尺
				"startType":2,
				"startLogId":$("#id").val(),
				"arrivalId": arrivalId,
  	            "tankId":$("#tank").attr("data"),
  	            "startLevel":$("#bLiquidLevel").val(),
  	            "startWeight":$("#bWeight").val(),
  	            "startTemperature":$("#bTemperature").val(),
  	            "startHandLevel":$("#bHandLevel").val(),
  	            "startHandWeight":$("#bHandWeight").val()
  	            };
		
		config.load();
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/tanklog/addTankLogStore",
  			dataType : "json",
  			data:data,
  			success : function(data) {
  				config.unload();
  				if (data.code == "0000") {
					$("body").message({
	                    type: 'success',
	                    content: '关联成功'
	                });
				} else {
					$("body").message({
	                    type: 'error',
	                    content: '关联失败'
	                });
  			}
  				dialog.remove();
  			}
		});
		
	}
	
	
	
	function updateInboundB(id,i){
		var data;
		if(i==1){
			
			data={ 
					
					"endType":i,
					"endLogId":$("#id").val(),
					"id": id,
					"endLevel":$("#fLiquidLevel").val(),
					"endWeight":$("#fWeight").val(),
					"endTemperature":$("#fTemperature").val(),
					"endHandLevel":$("#fHandLevel").val(),
					"endHandWeight":$("#fHandWeight").val(),
			};
			
		}else{
			
			data={ 
					
					"endType":i,
					"endLogId":$("#id").val(),
					"id": id,
					"endLevel":$("#bLiquidLevel").val(),
					"endWeight":$("#bWeight").val(),
					"endTemperature":$("#bTemperature").val(),
					"endHandLevel":$("#bHandLevel").val(),
					"endHandWeight":$("#bHandWeight").val(),
			};
		}
		
		config.load();
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/tanklog/updateTankLogStore",
  			dataType : "json",
  			data:data,
  			success : function(data) {
  				config.unload();
  				if (data.code == "0000") {
					$("body").message({
	                    type: 'success',
	                    content: '关联成功'
	                });
				} else {
					$("body").message({
	                    type: 'error',
	                    content: '关联失败'
	                });
  			}
  				dialog.remove();
  			}
		});
		
		
		
	}
	
	
	//入库关联（前尺关联了后尺）
	function connectInboundToFDialog(arrivalId){
		$.get(config.getResource()+"/pages/inbound/tanklog/search_Bconnect.jsp").done(function(data){
			dialog = $(data) ;
			
			dialog.modal({
				keyboard: true
			});
			
			var columns1 = [  {
				title : "前尺-液位(毫米)",
				name : "startLevel"
			},{
				title : "前尺-重量(吨)",
				name : "startWeight"
			},{
				title : "前尺-温度(℃)",
				name : "startTemperature"
			},{
				title : "后尺-液位(毫米)",
				name : "endLevel"
			},{
				title : "后尺-重量(吨)",
				name : "endWeight"
			},{
				title : "后尺-温度(℃)",
				name : "endTemperature"
			}, {
				title : "操作",
				name : "id",
				render: function(item, name, index){
						return "<a href='javascript:void(0)' onclick='TankLog.updateInboundB("+item.id+",1)' class='btn btn-xs blue'><span class='fa  fa-fast-forward' title='关联后尺'></span></a>";
				}
			}];
			
			
			//入库列表
			dialog.find('[data-role="searchGrid"]').grid({
				identity : 'id',
				columns : columns1,
				isShowIndexCol : false,
				isShowPages : false,
				url : config.getDomain()+"/tanklog/getinboundBconnect?tankId="+$('#tank').attr("data")+"&arrivalId="+arrivalId
			});
			
		});
	}
	//入库关联（后尺关联了后尺）
	function connectInboundToBDialog(arrivalId){
		$.get(config.getResource()+"/pages/inbound/tanklog/search_Bconnect.jsp").done(function(data){
			dialog = $(data) ;
			
			dialog.modal({
				keyboard: true
			});
			
			var columns1 = [  {
				title : "前尺-液位(毫米)",
				name : "startLevel"
			},{
				title : "前尺-重量(吨)",
				name : "startWeight"
			},{
				title : "前尺-温度(℃)",
				name : "startTemperature"
			},{
				title : "后尺-液位(毫米)",
				name : "endLevel"
			},{
				title : "后尺-重量(吨)",
				name : "endWeight"
			},{
				title : "后尺-温度(℃)",
				name : "endTemperature"
			},{
				title : "操作",
				name : "id",
				render: function(item, name, index){
						return "<a href='javascript:void(0)' onclick='TankLog.updateInboundB("+item.id+",2)' class='btn btn-xs blue'><span class='fa  fa-fast-forward' title='关联后尺'></span></a>";
				}
			}];
			
			
			//入库列表
			dialog.find('[data-role="searchGrid"]').grid({
				identity : 'id',
				columns : columns1,
				isShowIndexCol : false,
				isShowPages : false,
				url : config.getDomain()+"/tanklog/getinboundBconnect?tankId="+$('#tank').attr("data")+"&arrivalId="+arrivalId
			});
			
		});
	}
	
	
	
	//关联调用关联接口
	function connectToF(connectType,transportId,transportType,storeId){
		var data;
		if(connectType==1){
			data={ 
					"id":storeId,
					//前尺
					"logType":1,
					//关联了前尺
					"connectType":connectType,
					"logId":$("#id").val(),
//					"messageType": $("#type").val(),
//	  	            "messageType":1,
	  	            "tankId":$("#tank").attr("data"),
//	  	            "mStartTime":$(".startTime1").val()+" "+$("#startTime2").val()+":00",
//	  	            "mEndTime":$(".endTime1").val()+" "+$("#endTime2").val()+":00",
	  	            "startLevel":$("#fLiquidLevel").val(),
	  	            "startWeight":$("#fWeight").val(),
	  	            "startTemperature":$("#fTemperature").val(),
	  	            "startHandLevel":$("#fHandLevel").val(),
	  	            "startHandWeight":$("#fHandWeight").val(),
	  	            "startDiffer":$("#fDiffer").val(),
//	  	            "endLevel":$("#bLiquidLevel").val(),
//	  	            "endWeight":$("#bWeight").val(),
//	  	            "endTemperature":$("#bTemperature").val(),
//	  	            "endHandLevel":$("#bHandLevel").val(),
//	  	            "endHandWeight":$("#bHandWeight").val(),
//	  	            "endDiffer":$("#bDiffer").val(),
//	  	            "realAmount":$("#outWeight").val(),
//	  	            "message":$("#message").val(),
//	  	            "measureAmount":$("#handWeight").val(),
	  	            "transportId":transportId,
	  	            "type" : transportType
	  	            };
		}
		if(connectType==2){
			data={ 
					"id":storeId,
					//前尺
					"logType":1,
					//关联了后尺
					"connectType":connectType,
					"logId":$("#id").val(),
//					"messageType": $("#type").val(),
	  	            "tankId":$("#tank").attr("data"),
	  	            "endLevel":$("#fLiquidLevel").val(),
	  	            "endWeight":$("#fWeight").val(),
	  	            "endTemperature":$("#fTemperature").val(),
	  	            "endHandLevel":$("#fHandLevel").val(),
	  	            "endHandWeight":$("#fHandWeight").val(),
	  	            "endDiffer":$("#fDiffer").val(),
	  	            "transportId":transportId,
	  	            "type" : transportType
	  	            };
		}
		config.load();
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/tanklog/updateStore",
  			dataType : "json",
  			data:data,
  			success : function(data) {
  				config.unload();
  				if (data.code == "0000") {
					$("body").message({
	                    type: 'success',
	                    content: '关联成功'
	                });
					$(".disconnectF").show();
				} else {
					$("body").message({
	                    type: 'error',
	                    content: '关联失败'
	                });
  			}
  				dialog.remove();
  			}
		});
	}
	
	
	function connectToB(connectType,transportId,transportType,storeId){
		var data;
		if(connectType==1){
			data={ 
					"id":storeId,
					//后尺
					"logType":2,
					//关联了前尺
					"connectType":connectType,
					"logId":$("#id").val(),
//					"messageType": $("#type").val(),
//	  	            "messageType":1,
	  	            "tankId":$("#tank").attr("data"),
//	  	            "mStartTime":$(".startTime1").val()+" "+$("#startTime2").val()+":00",
//	  	            "mEndTime":$(".endTime1").val()+" "+$("#endTime2").val()+":00",
	  	            "startLevel":$("#bLiquidLevel").val(),
	  	            "startWeight":$("#bWeight").val(),
	  	            "startTemperature":$("#bTemperature").val(),
	  	            "startHandLevel":$("#bHandLevel").val(),
	  	            "startHandWeight":$("#bHandWeight").val(),
	  	            "startDiffer":$("#bDiffer").val(),
//	  	            "endLevel":$("#bLiquidLevel").val(),
//	  	            "endWeight":$("#bWeight").val(),
//	  	            "endTemperature":$("#bTemperature").val(),
//	  	            "endHandLevel":$("#bHandLevel").val(),
//	  	            "endHandWeight":$("#bHandWeight").val(),
//	  	            "endDiffer":$("#bDiffer").val(),
//	  	            "realAmount":$("#outWeight").val(),
//	  	            "message":$("#message").val(),
//	  	            "measureAmount":$("#handWeight").val(),
	  	            "transportId":transportId,
	  	            "type" : transportType
	  	            };
		}
		if(connectType==2){
			data={ 
					"id":storeId,
					//后尺
					"logType":2,
					//关联了后尺
					"connectType":connectType,
					"logId":$("#id").val(),
//					"messageType": $("#type").val(),
	  	            "tankId":$("#tank").attr("data"),
	  	            "endLevel":$("#bLiquidLevel").val(),
	  	            "endWeight":$("#bWeight").val(),
	  	            "endTemperature":$("#bTemperature").val(),
	  	            "endHandLevel":$("#bHandLevel").val(),
	  	            "endHandWeight":$("#bHandWeight").val(),
	  	            "endDiffer":$("#bDiffer").val(),
	  	            "transportId":transportId,
	  	            "type" : transportType
	  	            };
		}
		config.load();
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/tanklog/updateStore",
  			dataType : "json",
  			data:data,
  			success : function(data) {
  				config.unload();
  				if (data.code == "0000") {
					$("body").message({
	                    type: 'success',
	                    content: '关联成功'
	                });
					$(".disconnectB").show();
					
				} else {
					$("body").message({
	                    type: 'error',
	                    content: '关联失败'
	                });
  			}
  				dialog.remove();
  			}
		});
	}
	
	
	function disconnect(i){
		//取消前尺
		if(i==1){
			
			config.load();
			$.ajax({
	  			type : "get",
	  			url : config.getDomain()+"/tanklog/disconnect?tankLogId="+$("#id").val()+"&type="+i,
	  			dataType : "json",
	  			success : function(data) {
	  				config.unload();
	  				if(data.code=="0000"){
	  					$("body").message({
		                    type: 'success',
		                    content: '取消关联成功'
		                });
	  					$(".disconnectF").hide();
	  				}else{
	  					$("body").message({
		                    type: 'error',
		                    content: '取消关联失败'
		                });
	  				}
	  			}
			});
			
			
			
		}
		//取消后尺
		if(i==2){
			config.load();
			$.ajax({
	  			type : "get",
	  			url : config.getDomain()+"/tanklog/disconnect?tankLogId="+$("#id").val()+"&type="+i,
	  			dataType : "json",
	  			success : function(data) {
	  				config.unload();
	  				if(data.code=="0000"){
	  					$("body").message({
		                    type: 'success',
		                    content: '取消关联成功'
		                });
	  					$(".disconnectB").hide();
	  				}else{
	  					$("body").message({
		                    type: 'error',
		                    content: '取消关联失败'
		                });
	  				}
	  			}
			});
		}
		
	}
	
	
	
	function connect(i){
		$.get(config.getResource()+"/pages/inbound/tanklog/search_connect.jsp").done(function(data){
			dialog = $(data) ;
			
			dialog.modal({
				keyboard: true
			});
			
			
			dialog.find('.arrivalTime2').datepicker({
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
				 var name = "startTime";
		                params[name] = dialog.find(".arrivalTime2").val()+" 00:00:00";
		                dialog.find('[data-role="search2Grid"]').getGrid().search(params);
			
			
		});
			
			
			
			
			
			if($(".startTime1").val()){
				dialog.find(".arrivalTime2").val($(".startTime1").val());
			}else{
				dialog.find(".arrivalTime2").val(new Date().Format("yyyy-MM-dd"));
			}
			
			
			
			
			
			var columns2 = [  {
				title : "船名",
				name : "shipName"
			},{
				title : "编号",
				name : "storeId"
			},{
				title : "到港时间",
				name : "arrivalTime",
				render: function(item, name, index){
					switch (item.transportType) {
					case 0:
						return item.arrivalTime;
						break;
					case 2:
						return item.arrivalStartTime.substring(0, item.arrivalStartTime.length -2);
						break;
					}
				}
			},{
				title : "类型",
				name : "transportType",
				render: function(item, name, index){
					switch (item.transportType) {
					case 0:
						return "入库";
						break;
					case 2:
						return "船发";
						break;
					}
				}
			}, {
				title : "操作",
				name : "id",
				render: function(item, name, index){
					if(i==1){
						return "<a href='javascript:void(0)' onclick='TankLog.connectToF(1,"+item.transportId+","+item.transportType+","+item.storeId+")' class='btn btn-xs blue'><span class='fa fa-fast-backward ' title='关联前尺'></span></a>"+
						"<a href='javascript:void(0)' onclick='TankLog.connectToF(2,"+item.transportId+","+item.transportType+","+item.storeId+")' class='btn btn-xs blue'><span class='fa  fa-fast-forward' title='关联后尺'></span></a>";
					}
					if(i==2){
						return "<a href='javascript:void(0)' onclick='TankLog.connectToB(1,"+item.transportId+","+item.transportType+","+item.storeId+")' class='btn btn-xs blue'><span class='fa fa-fast-backward ' title='关联前尺'></span></a>"+
						"<a href='javascript:void(0)' onclick='TankLog.connectToB(2,"+item.transportId+","+item.transportType+","+item.storeId+")' class='btn btn-xs blue'><span class='fa  fa-fast-forward' title='关联后尺'></span></a>";
					}
				}
			}];
			
			
			//出库列表
			dialog.find('[data-role="search2Grid"]').grid({
				identity : 'id',
				columns : columns2,
				isShowIndexCol : false,
				isShowPages : false,
				autoLoad:false,
				url : config.getDomain()+"/tanklog/getconnect?tankId="+$('#tank').attr("data")
			});
			
			var params = {};
			 var name = "startTime";
			 //出库参数
	                params[name] = dialog.find(".arrivalTime2").val()+" 00:00:00";
	                dialog.find('[data-role="search2Grid"]').getGrid().search(params);
	                
			
		});
	}
	
	
	
	
	function inboundConnect(i){
		$.get(config.getResource()+"/pages/inbound/tanklog/search_inboundconnect.jsp").done(function(data){
//			dialog = $(data) ;
//			dialog.modal({
//				keyboard: true
//			});
			
			
			dialog1=$(data) ;
			dialog1.modal({
				keyboard: true
			});
			
			
			dialog1.find('.arrivalTime1').datepicker({
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
//			if(searchCount==1){
//				searchCount=0;
//			}else{
				var params = {};
				 var name = "startTime";
		                params[name] = dialog1.find(".arrivalTime1").val()+" 00:00:00";
		                dialog1.find('[data-role="search1Grid"]').getGrid().search(params);
//			
//				searchCount+=1;
//			}
			
		});
			
			
			
			if($(".startTime1").val()){
				dialog1.find(".arrivalTime1").val($(".startTime1").val());
			}else{
				dialog1.find(".arrivalTime1").val(new Date().Format("yyyy-MM-dd"));
			}
			
			
			
			
			var columns1 = [  {
				title : "船名",
				name : "shipName"
			},{
				title : "到港时间",
				name : "arrivalStartTime",
				render: function(item, name, index){
					return item.arrivalStartTime.substring(0, item.arrivalStartTime.length -2);
			}
				
			},{
				title : "类型",
				name : "transportType",
				render: function(item, name, index){
						return "入库";
				}
			}, {
				title : "操作",
				name : "id",
				render: function(item, name, index){
					if(i==1){
						return "<a href='javascript:void(0)' onclick='TankLog.connectInboundToF("+item.arrivalId+")' class='btn btn-xs blue'><span class='fa fa-fast-backward ' title='关联前尺'></span></a>"+
						"<a href='javascript:void(0)' onclick='TankLog.connectInboundToFDialog("+item.arrivalId+")' class='btn btn-xs blue'><span class='fa  fa-fast-forward' title='关联后尺'></span></a>";
					}
					if(i==2){
						return "<a href='javascript:void(0)' onclick='TankLog.connectInboundToB("+item.arrivalId+")' class='btn btn-xs blue'><span class='fa fa-fast-backward ' title='关联前尺'></span></a>"+
						"<a href='javascript:void(0)' onclick='TankLog.connectInboundToBDialog("+item.arrivalId+")' class='btn btn-xs blue'><span class='fa  fa-fast-forward' title='关联后尺'></span></a>";
					}
				}
			}];
			
			
			//入库列表
			dialog1.find('[data-role="search1Grid"]').grid({
				identity : 'id',
				columns : columns1,
				isShowIndexCol : false,
				isShowPages : false,
				autoLoad:false,
				url : config.getDomain()+"/tanklog/getinboundconnect?tankId="+$('#tank').attr("data")
			});
			
			
			var params = {};
			 var name = "startTime";
	                
	                //入库参数
	                params[name] = dialog1.find(".arrivalTime1").val()+" 00:00:00";
	                dialog1.find('[data-role="search1Grid"]').getGrid().search(params);
		});
	}
	
	
	
	
	
	function addSJNum(obj){
		config.clearNoNum(obj);
		if($("#fHandWeight").val()&&$("#bHandWeight").val()){
			var fHandWeight=parseFloat(checkNum($("#fHandWeight").val()));
			var bHandWeight=parseFloat(checkNum($("#bHandWeight").val()));
			
//			$("#handWeight").val(Math.abs(util.FloatSub(bHandWeight,fHandWeight)));
			$("#handWeight").val(util.FloatSub(bHandWeight,fHandWeight));
			
		}else{
			$("#handWeight").val("");
		}
		
	}
	
	
	function addFNum(obj){
		config.clearNoNum(obj);
		var fHandLevel=parseFloat(checkNum($("#fHandLevel").val()));
		var fLiquidLevel=parseFloat(checkNum($("#fLiquidLevel").val()));
		
//		$("#fDiffer").val(fHandLevel-fLiquidLevel);
		
		$("#fDiffer").val(util.FloatSub(fHandLevel,fLiquidLevel));
		
		
	}
	
	function addBNum(obj){
		config.clearNoNum(obj);
		
		var bHandLevel=parseFloat(checkNum($("#bHandLevel").val()));
		var bLiquidLevel=parseFloat(checkNum($("#bLiquidLevel").val()));
		
//		$("#bDiffer").val(bHandLevel-bLiquidLevel);
		$("#bDiffer").val(util.FloatSub(bHandLevel,bLiquidLevel));
	}
	
	
	function addRNum(obj){
		config.clearNoNum(obj);
		if($("#fWeight").val()&&$("#bWeight").val()){
			var fWeight=parseFloat(checkNum($("#fWeight").val()));
			var bWeight=parseFloat(checkNum($("#bWeight").val()));
			
//			$("#outWeight").val(Math.abs(util.FloatSub(bWeight,fWeight)));
			$("#outWeight").val(util.FloatSub(bWeight,fWeight));
		}else{
			$("#outWeight").val("");
		}
	}
	
	
	function checkNum(obj){
		if(obj==""){
			return 0;
		}else{
			return obj;
		}
		}
	
	
	function initAdd(tankId,tankName){
		util.initTimePicker($(".gtime"));
		util.urlHandleTypeahead("/product/select",$("#productId"));
//		//初始化时间控件
//		$('.date-picker').datepicker({
//		    rtl: Metronic.isRTL(),
//		    orientation: "right",
//		    format: "yyyy-mm-dd",
//		    autoclose: true
//		});
//		$('.timepicker-24').timepicker({
//	        autoclose: true,
//	        minuteStep: 1,
//	        defaultTime:"12:00",
//	        showSeconds: false,
//	        showMeridian: false
//	    });
		if(tankId!=0){
			$('#tank').val(tankName);
			$('#tank').attr("data",tankId);
			
			$.ajax({
	  			type : "get",
	  			url : config.getDomain()+"/tank/list?id="+tankId,
	  			dataType : "json",
	  			success : function(data) {
	  				if(data.code=="0000"){
	  					if(data.data[0].productId){
	  						$("#productId").attr("data",data.data[0].productId);
	  						$("#productId").val(data.data[0].productName);
	  						
	  					}
	  				else{
	  					$(this).confirm({
	  						content : '该货罐内没有货品，请前往基础资料添加！',
	  						concel:true,
	  						callBack : function() {
	  						}
	  					});
	  				}
	  				}
	  				
	  			}
			});
			
		}
		
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/baseController/getTankCode",
  			dataType : "json",
  			success : function(data) {
  				$('#tank').typeahead({
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
	  					if(client.productId){
	  						$("#productId").attr("data",client.productId);
	  						$("#productId").val(client.productName);
	  					}else{
	  						$(this).confirm({
		  						content : '该货罐内没有货品，请前往基础资料添加！',
		  						concel:true,
		  						callBack : function() {
		  						}
		  					});
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
  						$('#tank').removeAttr("data");
  						$('#tank').val("");
  						$("#productId").removeAttr("data");
						$("#productId").val("");
  					}else{
  						$('#tank').attr("data",client.id);
//  						$("#productId").attr("data",client.productId);
//  						$("#productId").val(client.productName);
  					}
  				}
  				});
  			}
  		});
		
	}
	
	
	//添加
	function doAdd(){
		if(config.validateForm($(".tank-log-form-add"))){
			config.load();
	    	$.ajax({
	  			type : "post",
	  			url : config.getDomain()+"/tanklog/add",
	  			dataType : "json",
	  			data:{
	  	            "messageType": $("#type").val(),
//	  	            "messageType":1,
	  	            "tankId":$("#tank").attr("data"),
	  	            "productId":$("#productId").attr("data"),
//	  	            "transportId":$("#transportId").val(),
	  	            "mStartTime":util.getTimeVal($(".gstart")),
//	  	            $(".startTime1").val()+" "+$("#startTime2").val()+":00",
	  	            "mEndTime":util.getTimeVal($(".gend")),
//	  	            $(".endTime1").val()+" "+$("#endTime2").val()+":00",
	  	            "startLevel":$("#fLiquidLevel").val(),
	  	            "startWeight":$("#fWeight").val(),
	  	            "startTemperature":$("#fTemperature").val(),
	  	            "startHandLevel":$("#fHandLevel").val(),
	  	            "startHandWeight":$("#fHandWeight").val(),
	  	            "startDiffer":$("#fDiffer").val(),
	  	            "endLevel":$("#bLiquidLevel").val(),
	  	            "endWeight":$("#bWeight").val(),
	  	            "endTemperature":$("#bTemperature").val(),
	  	            "endHandLevel":$("#bHandLevel").val(),
	  	            "endHandWeight":$("#bHandWeight").val(),
	  	            "endDiffer":$("#bDiffer").val(),
	  	            "realAmount":$("#outWeight").val(),
	  	            "message":$("#message1").val(),
	  	            "measureAmount":$("#handWeight").val()
	  			},
	  			success : function(data) {
	  				config.unload();
	  				if (data.code == "0000") {
						$("body").message({
		                    type: 'success',
		                    content: '添加成功'
		                });
						tankCode=$("#tank1").val();
						tankId=$("#tank1").attr("data");
						
//						window.location.href = "#/tanklog";
						window.location.href = "#/updatetanklog?id="+data.map.id;
					} else {
						$("body").message({
		                    type: 'error',
		                    content: '添加失败'
		                });
	  			}
	    	}
	    });
		}
	}
	
	
	//保存更新
	function doSave(){
    	var isOk = false;
		if(config.validateForm($(".tank-log-form"))){
			isOk=true;
		}
		if(isOk) {
    	config.load();
    	$.ajax({
  			type : "post",
  			url : config.getDomain()+"/tanklog/update",
  			dataType : "json",
  			data:{
  	            "id":$("#id").val(),
  	            "messageType": $("#type").val(),
//  	            "messageType":1,
  	            "tankId":$("#tank").attr("data"),
  	            "productId":$("#productId").attr("data"),
//  	            "transportId":$("#transportId").val(),
//  	            "mStartTime":$(".startTime1").val()+" "+$("#startTime2").val()+":00",
//  	            "mEndTime":$(".endTime1").val()+" "+$("#endTime2").val()+":00",
  	            "mStartTime":util.getTimeVal($(".gstart")).length==10?(util.getTimeVal($(".gstart"))+" 00:00:00"):util.getTimeVal($(".gstart")),
	            "mEndTime":util.getTimeVal($(".gend")).length==10?(util.getTimeVal($(".gend"))+" 00:00:00"):util.getTimeVal($(".gend")),
  	            "startLevel":$("#fLiquidLevel").val(),
  	            "startWeight":$("#fWeight").val(),
  	            "startTemperature":$("#fTemperature").val(),
  	            "startHandLevel":$("#fHandLevel").val(),
  	            "startHandWeight":$("#fHandWeight").val(),
  	            "startDiffer":$("#fDiffer").val(),
  	            "endLevel":$("#bLiquidLevel").val(),
  	            "endWeight":$("#bWeight").val(),
  	            "endTemperature":$("#bTemperature").val(),
  	            "endHandLevel":$("#bHandLevel").val(),
  	            "endHandWeight":$("#bHandWeight").val(),
  	            "endDiffer":$("#bDiffer").val(),
  	            "realAmount":$("#outWeight").val(),
  	            "message":$("#message1").val(),
  	            "measureAmount":$("#handWeight").val()
  			},
  			success : function(data) {
  				config.unload();
  				if (data.code == "0000") {
					$("body").message({
	                    type: 'success',
	                    content: '修改成功'
	                });
					tankCode=$("#tank").val();
					tankId=$("#tank").attr("data");
//					window.location.href = "#/tanklog";
				} else {
					$("body").message({
	                    type: 'error',
	                    content: '修改失败'
	                });
  			}
    	}
    });
		}

	}
	
	var initBtn = function() {
		$(".btn-remove").unbind('click'); 
		$(".btn-remove").click(function() {
			var data = $('[data-role="tanklogGrid"]').getGrid().selectedRowsIndex();
			var $this = $('[data-role="tanklogGrid"]');
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}
					 $this.confirm({
						 content : '确定要撤销所选记录吗?此操作会同步删除关联的调度日志。',
						 callBack : function() {
//							 deleteIntent($('[data-role="arrivalGrid"]').getGrid().selectedRows(), $this);
							 dataGrid = $this;
						        var data = "";
						        $.each($('[data-role="tanklogGrid"]').getGrid().selectedRows(), function (i, role) {
						            data += ("ids=" + role.id + "&");
						        });
						        data = data.substring(0, data.length - 1);
						        	$.post(config.getDomain()+'/tanklog/delete', data).done(function (data) {
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
						 }
					 });
				
		});
		
		$(".add").unbind('click'); 
		$(".add").click(function() {

			// var headTable=$(".grid-table-head").find(".table");
			// var thNum=$(headTable).find("th");
			// var bodyTable=$(".grid-table-body").find(".table");
			// var trNum=$(bodyTable).find("tr");
			// var trHtm="<tr><td><div data-value='1' data-role='indexCheckbox'
			// indexvalue='0'
			// class='checkerbox'></div></td>"+(trNum.length+1)+"<td></td>";
			// for(var i=2;i<thNum.length;i++){
			// trHtm+='<td><input type="text" class="form-control "
			// style="width:100px" ></td>'
			// }
			// trHtm+="</tr>";
			// $(bodyTable).insertBefore(trHtm,$(bodyTable).children("tr")[0]);
			var tankName=0;
			var tankId=0;
			if($('#tankId1').val()){
				tankId=$('#tankId1').attr("data");
				tankName=$('#tankId1').val();
				window.location.href = "#/addtanklog?tankId="+tankId+"&tankName="+tankName;
			}else {
//				$("body").message({
//					type : 'warning',
//					content : '请选择储罐'
//				});
				window.location.href = "#/addtanklog?tankId=0"+tankId+"&tankName=";
			}
			
		});
		$(".modify").unbind('click'); 
		$(".modify").click(
				function() {
					var index = $('[data-role="tanklogGrid"]').getGrid().selectedRowsIndex();
					var $this = $(this);
					if (index.length == 0) {
						$this.message({
							type : 'warning',
							content : '请选择要查看的储罐台账'
						});
						return;
					}
//					console.log(index);
//					dataSelected=$('[data-role="tanklogGrid"]').getGrid().selectedRows()[0];
//					console.log(dataSelected);
					window.location.href = "#/updatetanklog?id="+$('[data-role="tanklogGrid"]').getGrid().selectedRows()[0].id;

				});
		$(".tankQuery").unbind('click'); 
		$(".tankQuery").click(function() {
			$("#tankQueryDivId").slideToggle("slow");
		});

	};
	
	
	var searchFirst = function() {
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain() + "/tanklog/tanklist",
			dataType : "json",
			data:{"year":$("input[name='year']").val(),"productId":$("input[name='productId']").attr("data"),"tankId":$("input[name='tankId']").attr("data")},
			success : function(data) {
			config.unload() ;
			if(data.code=="0000"){
				setChartData(data) ;
			}
			}
		});
//		$("input[name='tankId']").attr("data","");
//		$("input[name='tankId']").val("");
		
//		console.log($(".tankNum").val());
//		var tank = $(".tankNum").val();
//		initTank(tank);
//		
		
		/*var params = {};
        $("#tanklogFirstForm").find('.form-control').each(function(){
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
       $('[data-role="tankGrid"]').getGrid().search(params);*/
	};
	
	
	var search = function(obj) {
//		console.log($(".tankNum").val());
//		var tank = $(".tankNum").val();
//		initTank(tank);
//		
		var params = {};
        $("#tanklogFirstForm").find('.form-control').each(function(){
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
        if(obj) {
        	params["productId"] = obj.productId;
        	params["tankId"] = obj.tankId;
        }
        console.log(params);
       $('[data-role="tanklogGrid"]').getGrid().search(params);
	};
	function initUpdate(id) {
		util.initTimePicker($(".gtime"));
		util.urlHandleTypeahead("/product/select",$("#productId"));
//		//初始化时间控件
//		$('.date-picker').datepicker({
//		    rtl: Metronic.isRTL(),
//		    orientation: "right",
//		    format: "yyyy-mm-dd",
//		    autoclose: true
//		});
//		$('.timepicker-24').timepicker({
//	        autoclose: true,
//	        minuteStep: 1,
//	        defaultTime:"12:00",
//	        showSeconds: false,
//	        showMeridian: false
//	    });
		var data=dataSelected;
//		console.log(data);
		
		config.load();
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/tanklog/list?id="+id,
  			dataType : "json",
  			success: function(data) {
  				config.unload();
  				if(data.code=="0000"){
  					var data1=data.data[0];
  					
  					
  					if(data1.mStartTime!=""){
  						util.initTimeVal($(".startTime"),data1.mStartTime) ;
  						//$("#startTime1").val(data1.mStartTime.split(" ")[0]);
  						//$("#startTime2").val(data1.mStartTime.split(" ")[1].substring(0,5));
  					}
  					if(data1.mEndTime!=""){
  						util.initTimeVal($(".endTime"),data1.mEndTime) ;
  						//$("#endTime1").val(data1.mEndTime.split(" ")[0]);
  						//$("#endTime2").val(data1.mEndTime.split(" ")[1].substring(0,5));
  					}
  						$("#tankIdHidden").val(data1.tankId);
					    $("#tankNameHidden").val(data1.tankCode);
  			            $("#id").val(data1.id);
  			            $("#type").val(data1.messageType);
  			            $("#tank").val(data1.tankCode);
  			            $("#tank").attr("data",data1.tankId);
  			            $("#productId").val(data1.productName);
  			            $("#productId").attr("data",data1.productId);
//  			            $("#startTime").val(data.mStartTime);
//  			            $("#endTime").val(data.mEndTime);
  			            $("#fLiquidLevel").val(data1.startLevel);
  			            $("#fWeight").val(data1.startWeight);
  			            $("#fTemperature").val(data1.startTemperature);
  			            $("#fHandLevel").val(data1.startHandLevel);
  			            $("#fHandWeight").val(data1.startHandWeight);
  			            $("#fDiffer").val(data1.startDiffer);
  			            $("#bLiquidLevel").val(data1.endLevel);
  			            $("#bWeight").val(data1.endWeight);
  			            $("#bTemperature").val(data1.endTemperature);
  			            $("#bHandLevel").val(data1.endHandLevel);
  			            $("#bHandWeight").val(data1.endHandWeight);
  			            $("#bDiffer").val(data1.endDiffer);
  			            $("#outWeight").val(data1.realAmount);
  			            $("#message1").val(data1.message);
  			            $("#handWeight").val(data1.measureAmount);
//  			            $("#transportId").val(data.transportId);
  			            
  			            
  			            if(data1.startStoreId!=null&&data1.startStoreId!=0){
  			            	$(".disconnectF").show();
  			            	$(".startStoreId").val(data1.startStoreId);
  			            }
  			            if(data1.endStoreId!=null&&data1.endStoreId!=0){
  			            	$(".disconnectB").show();
  			            	$(".endStoreId").val(data1.endStoreId);
  			            }

  			            
  			            tankCode=$("#tank").val();
  						tankId=$("#tank").attr("data");
  			            
  						productName=$("#productId").val();
						productId=$("#productId").attr("data");
  					
  				}else{
  					$(this).message({
						type : 'warning',
						content : '数据错误'
					});
					return;
  				}
  			}
		});
		
		
		
		
	}

	function toSecond(obj){
		//$('#tankId').attr("data",$(obj).attr("data"));
		//$('#tankId').val($(obj).text());
		$(".first").hide();
		$(".second").show();
//		var params = {};
//        params.tankName = obj ;
//       $('[data-role="tanklogGrid"]').getGrid().search(params);
		search(obj);
	}
	
	
	function getTank(productId){
		var strProductId="";
		if(productId!=0){
			strProductId="?productId="+productId;
		}
		config.load();
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/tanklog/tanklist"+strProductId,
  			dataType : "json",
  			success : function(data) {
  				config.unload();
  				$('#tankId1').typeahead('hide');
  				$('#tankId1').remove();
  				$('#tId').append("<input id='tankId1' type='text' name='tankId'  data-provide='typeahead'  class='form-control tankId1'>");
  				
  				$('#tankId1').typeahead({
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
	  						$('#tankId1').attr("data",client.tankId);
	  						$('#tankId1').val(client.code);
	  					}
	  					searchFirst();
	  					search();
	  					return item;
  					},
					//移除控件时调用的方法
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.code == query;
                    });
  					
  					//匹配不到就去掉id，让内容变空，否则给id
  					if(client==null){
  						$('#tankId1').removeAttr("data");
  						$('#tankId1').val("");
  						searchFirst();
  						search();
  					}else{
  						$('#tankId1').attr("data",client.tankId);
  						searchFirst();
  						search();
  					}
  				}
  				});
  				
  				
  			}
  		});
	}
	
	
	function setChartData(obj){
		
		var serArr = new Array() ;
		var total = obj.map.totalRecord ;
		var xNum = Math.round(total/5) ;
		var height = (xNum+(total%5==0?0:1))*160+280;
		var x = ['10%','30%','50%','70%','90%'] ;
		if(height>500){
			$("#pie").css("height",height+"px") ;
		}
		
		require(
		        [
		            'echarts',
		            'echarts/chart/pie',
		            'echarts/chart/funnel'
		        ],
		        function (ec) {
		            // 基于准备好的dom，初始化echarts图表
		            var areaChart = ec.init(document.getElementById('pie')); 
		            
		            var labelTop = {
		            	    normal : {
		            	        label : {
		            	            show : true,
		            	            position : 'center',
		            	            formatter : '{b}',
		            	            textStyle: {
		            	                baseline : 'bottom'
		            	            }
		            	        },
		            	        labelLine : {
		            	            show : false
		            	        }
		            	    }
		            	};
		            	var labelFromatter = {
		            	    normal : {
		            	    	color:['#f2eada'],
		            	        label : {
		            	            formatter : function (params){
		            	                return "空容"+(100 - params.value) + '%'
		            	            },
		            	            textStyle: {
		            	                baseline : 'top'
		            	            }
		            	        }
		            	    },
		            	}
		            	var labelBottom = {
		            	    normal : {
		            	    	color:['#70a19f'],
		            	        label : {
		            	            show : true,
		            	            position : 'center'
		            	        },
		            	        labelLine : {
		            	            show : false
		            	        }
		            	    },
		            	    emphasis: {
		            	        color: 'rgba(0,0,0,0)'
		            	    }
		            	};
		            	var tanklist = obj.data ;
		        		var radius = [40, 55];
		        		for(var i = 1 ;i<=tanklist.length;i++){
		        			var yy  = tanklist[i-1].yy ;
		        			var wy = tanklist[i-1].wy ;
		        			var v1 = 0 ;
		        			var v2 = 1 ;
		        			if(yy!=0&&wy!=0){
		        				v1 = (yy/(yy+wy))*100 ;
			        			v2 = (wy/(yy+wy))*100 ;
		        			}
		        			v1 = Math.round(v1) ;
		        			v2 = Math.round(v2) ;
		        			var sar = {
		        	            type : 'pie',
		        	            center : [x[(i%5==0?5:i%5)-1], (Math.floor((i-1)/5)+1)*160],
		        	            radius : radius,
		        	            x: '0%', // for funnel
		        	            itemStyle : labelFromatter,
		        	            data : [
		        	                {name:tanklist[i-1].code+"\n"+tanklist[i-1].productName, value:v1,itemStyle : labelBottom},
		        	                {name:tanklist[i-1].code+"\n"+tanklist[i-1].productName, value:v2,itemStyle : labelTop}
		        	            ]
		        	        }
		        			serArr.push(sar) ;
		        		}
		            	option = {
		            	    title : {
		            	        x: 'center',
		            	        textStyle: {
		            	        	fontSize: 14,
		            	        	fontWeight: 'normal',
		            	        	color: '#000000'
		            	        }
		            	    },
		            	    series : serArr
		            	};
		            	                    
		            // 为echarts对象加载数据 
		            areaChart.setOption(option);
		            var ecConfig = require('echarts/config');
		            function eConsole(param) {
		            	$("#tankId1").val(obj.data[param.seriesIndex].code);
		            	$("#tankId1").attr("data",obj.data[param.seriesIndex].tankId);
		            	toSecond(obj.data[param.seriesIndex]) ;
		            }
		            areaChart.on(ecConfig.EVENT.CLICK, eConsole);
		            }
		        );
	}
	
	
	function initTank(type,mTankId,mTankName) {
//		console.log(2);
		$('#startTime').val(new Date().Format("yyyy"));
		$('.time2').datepicker({
			  rtl: Metronic.isRTL(),
			    language: "zh-CN",
			    orientation: "left",
			    format: "yyyy",
			    showInputs:true,
			    startView:"years",
			    minViewMode:"years",
			    showDay:"false",
	            disableMousewheel:false,
	            changeMonth :false,
			    autoclose: true,
	}).on('changeDate', function(ev){
		search();
		searchFirst();
	});
		
		$('#startTime1').val(new Date().Format("yyyy"));
		
		$('.time1').datepicker({
			  rtl: Metronic.isRTL(),
			    language: "zh-CN",
			    orientation: "left",
			    format: "yyyy",
			    showInputs:true,
			    startView:"years",
			    minViewMode:"years",
			    showDay:"false",
	            disableMousewheel:false,
	            changeMonth :false,
			    autoclose: true,
	})
	.on('changeDate', function(ev){
		search();
		searchFirst();
	})
	;
		
		
			/*var columns=[{
				title:"罐号",
				name:"code",
				render: function(item, name, index){
					return "<a href='javascript:void(0)' onclick='TankLog.toSecond(this)' data="+item.tankId+">"+item.code+"</a>";
				}
			}
//			,
//			{title:"货品",name:"productName"}
//			,{title:"最大储存量(吨)",name:"capacityTotal"},
//			{title:"实际储存量(吨)",name:"capacityCurrent"},{title:"可储容量(吨)",name:"capacityFree"},{title:"使用情况",name:"description"}
			];
			if($('[data-role="tankGrid"]').getGrid()!=null){
				$('[data-role="tankGrid"]').getGrid().destory();
			}
			$('[data-role="tankGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : false,
				autoLoad:false,
				url : config.getDomain() + "/tanklog/tanklist"
			});*/
			
		
			

		config.load();
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/product/select",
			dataType : "json",
			success : function(data) {
				config.unload();
				
				$('#product1').typeahead({
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
	  						$('#product1').attr("data",client.id);
	  						
	  					}
	  					$('#tankId1').removeAttr("data");
  						$('#tankId1').val("");
	  					searchFirst();
	  					isclick=client.id;
	  					getTank(client.id);
	  					return item;
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.name == query;
                    });
  					if(client==null){
  						$('#product1').removeAttr("data");
  						$('#product1').val("");
  						$('#tankId1').removeAttr("data");
  						$('#tankId1').val("");
  						
  						searchFirst();
  						getTank(0);
  					}else{
  						$('#product1').attr("data",client.id);
  						if(isclick==client.id){
  							
//  							isclick=0;
  						}else{
  							searchFirst();
  							
  						}
  						getTank(client.id);
  					}
  				}
  				});
				
				
				
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
	  					search();
	  					return item;
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.name == query;
                    });
  					if(client==null){
  						$('#product').removeAttr("data");
  						$('#product').val("");
  						search();
  					}else{
  						$('#product').attr("data",client.id)
  					}
  				}
  				});
			}
		});
		
		getTank(0);
		
		var columns = [ 
//		{
//			title : "序号",
//			width : 100,
//			name : "id"
//		}, 
		{
			title : "罐号",
			width : 100,
			name : "tankCode"
		},{
			title : "货品",
			width : 100,
			name : "productName"
		},
//		{
//			title : "类型",
//			width : 100,
//			name : "message",
//			render: function(item, name, index){
//				switch (item.messageType) {
//				case 1:
//					return "接卸";
//					break;
//				case 2:
//					return "船发";
//					break;
//				case 3:
//					return "打循环";
//					break;
//				case 4:
//					return "清罐";
//					break;
//				case 5:
//					return "压管线";
//					break;
//				case 6:
//					return "倒罐";
//					break;
//				}
//			}
//		},
		{
			title : "开始时间",
			width : 100,
			name : "mStartTime",
		}, {
			title : "结束时间",
			width : 100,
			name : "mEndTime"
		}, {
			title : "前尺-液位(毫米)",
			width : 100,
			name : "startLevel"
		}, {
			title : "前尺-重量(吨)",
			width : 100,
			name : "startWeight"
		}, {
			title : "前尺-温度(℃)",
			width : 100,
			name : "startTemperature"
		}, {
			title : "前尺-手检尺液位(毫米)",
			width : 100,
			name : "startHandLevel"
		}, {
			title : "前尺-差异(毫米)",
			width : 100,
			name : "startDiffer"
		}, {
			title : "后尺-液位(毫米)",
			width : 100,
			name : "endLevel"
		}, {
			title : "后尺-重量(吨)",
			width : 100,
			name : "endWeight"
		}, {
			title : "后尺-温度(℃)",
			width : 100,
			name : "endTemperature"
		}, {
			title : "后尺-手检尺液位(毫米)",
			width : 100,
			name : "endHandLevel"
		}, {
			title : "后尺-差异(毫米)",
			width : 100,
			name : "endDiffer"
		}, {
			title : "机房重量",
			width : 100,
			name : "realAmount"
		}, {
			title : "事件",
			width : 300,
			name : "message"
		} ];
//		if ($('[data-role="tanklogGrid"]').getGrid() != null) {
//			$('[data-role="tanklogGrid"]').getGrid().destory();
//		}
		$('[data-role="tanklogGrid"]').grid(
				{
					identity : 'id',
					columns : columns,
					isShowIndexCol : true,
					isShowPages : true,
					autoLoad:false,
					gridName:'chuguantaizhang',
					stateSave:true,
					url : config.getDomain()
							+ "/tanklog/list"
				});
		search();
		$(".back").unbind('click'); 
		$(".back").click(function() {
			$(".first").show();
			$(".second").hide();
			$("#pie").empty() ;
			$("input[name='tankId']").attr("data","");
			$("input[name='tankId']").val("");
			searchFirst();
		});
		
		
		
		
		if(tankId!=null&&tankCode!=null){
			$('#tankId').attr("data",tankId);
			$('#tankId').val(tankCode);
			$("#product1").val(productName);
			$("#product1").attr("data",productId);
			getTank(productId);
			$(".first").hide();
			$(".second").show();
//			search();
			searchFirst();
			tankId=null;
			tankCode=null;
			productName=null;
			productId=null;
		}else if(type==1){
			$('#tankId1').attr("data",mTankId);
			$('#tankId1').val(mTankName);
			$('#tankId').attr("data",mTankId);
			$('#tankId').val(mTankName);
			
			$(".first").hide();
			$(".second").show();
			searchFirst();
			
			var params=[];
			params.tankId=mTankId;
			search(params);
			tankId=null;
			tankCode=null;
			productName=null;
			productId=null;
		}else{
		
			$(".first").show();
			$(".second").hide();
			
		}
		
		
	}
	
	return {
		changeConnectTab:function(obj,item){
			changeConnectTab(obj,item);
		},
		initTank : initTank,
		initBtn : initBtn,
		initUpdate:function(id){
			initFormIput();
			initUpdate(id);
		},
		returnPage:function(){
			var tankId = $("#tankIdHidden").val();
			var tankName = $("#tankNameHidden").val();
			window.location='#/tanklog?tankId='+tankId+"&tankName="+tankName;
		},
		initAdd:function(tankId,tankName){
			initFormIput();
			initAdd(tankId,tankName);
		},
		search : search,
		//计算前尺液位差异
		addFNum:function(obj){
			addFNum(obj);
		},
		//计算后尺液位差异
		addBNum:function(obj){
			addBNum(obj);
		},
		//计算实际重量差异
		addRNum:function(obj){
			addRNum(obj);
		},
		exportExcel:exportExcel,
		//计算手检计量重量差异
		addSJNum:function(obj){
			addSJNum(obj);
		},
		doSave: doSave,
		doAdd:doAdd,
		connect:function(i){
			connect(i);
		},
		disconnect:function(i){
			disconnect(i);
		},
		connectToF:function(connectType,transportId,transportType){
			connectToF(connectType,transportId,transportType);
		},
		connectToB:function(connectType,transportId,transportType){
			connectToB(connectType,transportId,transportType);
		},
		//入库前尺关联了前尺
		connectInboundToF:function(arrivalId){
			connectInboundToF(arrivalId);
		},
		//入库后尺关联了前尺
		connectInboundToB:function(arrivalId){
			connectInboundToB(arrivalId);
		},
		//入库前尺关联了后尺
		connectInboundToFDialog:function(arrivalId){
			connectInboundToFDialog(arrivalId);
		},
		//入库后尺关联了后尺
		connectInboundToBDialog:function(arrivalId){
			connectInboundToBDialog(arrivalId);
		},
		//更新后尺    i：log的前尺1/后尺2
		updateInboundB:function(arrivalId,i){
			updateInboundB(arrivalId,i);
		},
		disconnectInbound:function(i){
			disconnectInbound(i);
		},
		updateInbounddisconnect:function(tanklogStoreId,type,logId){
			updateInbounddisconnect(tanklogStoreId,type,logId);
		},
		inboundConnect:function(i){
			inboundConnect(i);
		},
		toSecond:function(obj){
			toSecond(obj);
		}
	}
}();