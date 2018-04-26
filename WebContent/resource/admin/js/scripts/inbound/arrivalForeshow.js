var ArrivalForeshow = function() {
	
	var dialog 	= null;    		//对话框
	var dataGrid 	= null; 		//Grid对象
	var sureLength=0;
	
	var goodsList=[];
	
	var goodsPlanList=[];
	
	var goodsSureList=[];
	
	var mSureList=[];
	
	var nArrivalId=0;
	var addDialog=null;
	
	var mDialog=null;
	
	var columnCheckArray;
	var order=2;
	//导出通知单
	function exportExcel(){
		var shipId = $("#shipId").attr("data") ;
		var startTime = $("#startTime").val() ;
		var endTime = $("#endTime").val() ;
		var url = config.getDomain()+"/arrivalForeshow/exportExcel?shipId="+shipId+"&startTime="+startTime+"&&endTime="+endTime ;
		window.open(url) ;
	}
	
	
	
	
	
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
		
		
		 var firstDate = new Date();

			firstDate.setMonth(firstDate.getMonth(), 1); //第一天
			
			$('#startTime').val(new XDate(firstDate).toString('yyyy-MM-dd'));
//			$('#endTime').val(new Date().Format("yyyy-MM-dd"));
		
			$(".order-time").unbind('click'); 
			$(".order-time").click(function(){
				
				if(order==1){
					order=2;
				}else{
					order=1;
				}
				ArrivalForeshow.search();
			});
			
			
		var columns = [  
		    
		
		{
			title : "长江口时间",
			name : "sCjTime",
			width : 70,
			render: function(item, name, index){
					if(item.sCjTime){
//						if(item.status==1){
//							return  "<span style='color:red'>"+ util.getSubTime(item.sCjTime,2)+"</span>";
//							
//						}
//						if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//							return  "<span style='color:blue'>"+ util.getSubTime(item.sCjTime,2)+"</span>";
//						}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//							return  "<span style='color:blue'>"+ util.getSubTime(item.sCjTime,2)+"</span>";
//						}else {
//							return util.getSubTime(item.sCjTime,2);
//						}
					
						if(item.color){
							
							if(item.color==1){
								return util.getSubTime(item.sCjTime,2);
							}else if(item.color==2){
								return  "<span style='color:blue'>"+ util.getSubTime(item.sCjTime,2)+"</span>";
							}else if(item.color==3){
								return  "<span style='color:green'>"+ util.getSubTime(item.sCjTime,2)+"</span>";
							}else if(item.color==4){
								return  "<span style='color:red'>"+ util.getSubTime(item.sCjTime,2)+"</span>";
							}
							
						}else{
							return util.getSubTime(item.sCjTime,2);
						}
						
						
					}
				}
			
		},{
			title : "太仓锚地时间",
			name : "sTcTime",
			width : 75,
			render: function(item, name, index){
//				


				if(item.sTcTime){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+ item.sTcTime+"</span>";
//				}
//					
//					if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//						return  "<span style='color:blue'>"+ item.sTcTime+"</span>";
//					}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//						return  "<span style='color:blue'>"+ item.sTcTime+"</span>";
//					}else{
//						return item.sTcTime;
//					}
					
					
					if(item.color){
						
						if(item.color==1){
							return util.getSubTime(item.sTcTime,2);
						}else if(item.color==2){
							return  "<span style='color:blue'>"+ util.getSubTime(item.sTcTime,2)+"</span>";
						}else if(item.color==3){
							return  "<span style='color:green'>"+ util.getSubTime(item.sTcTime,2)+"</span>";
						}else if(item.color==4){
							return  "<span style='color:red'>"+ util.getSubTime(item.sTcTime,2)+"</span>";
						}
						
					}else{
						return util.getSubTime(item.sTcTime,2);
					}
					
				}
			
				
				
			}
		},{
			title : "NOR发出时间",
			name : "sNorTime",
			width : 75,
			render: function(item, name, index){
				
				if(item.sNorTime){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+ util.getSubTime(item.sNorTime,2)+"</span>";
//				}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ util.getSubTime(item.sNorTime,2)+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ util.getSubTime(item.sNorTime,2)+"</span>";
//				}else {
//					return util.getSubTime(item.sNorTime,2);
//				}
//				
				if(item.color){
					
					if(item.color==1){
						return util.getSubTime(item.sNorTime,2);
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ util.getSubTime(item.sNorTime,2)+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+ util.getSubTime(item.sNorTime,2)+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ util.getSubTime(item.sNorTime,2)+"</span>";
					}
					
				}else{
					return util.getSubTime(item.sNorTime,2);
				}
				
				}
			}
		},{
			title : "预计靠泊日期",
			name : "sAnchorDate",
			width : 75,
			render: function(item, name, index){
				if(item.sAnchorTime){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+item.sAnchorTime.split(" ")[0]+"</span>";
//						}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+item.sAnchorTime.split(" ")[0]+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+item.sAnchorTime.split(" ")[0]+"</span>";
//				}
//				else{
//							return item.sAnchorTime.split(" ")[0];
//						}
				
				if(item.color){
					
					if(item.color==1){
						return item.sAnchorTime.split(" ")[0];
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.sAnchorTime.split(" ")[0]+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+ item.sAnchorTime.split(" ")[0]+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.sAnchorTime.split(" ")[0]+"</span>";
					}
					
				}else{
					return item.sAnchorTime.split(" ")[0];
				}
				
				
				
				}
			}
		},{
			title : "预计靠泊时间",
			name : "sAnchorTime",
			width : 75,
			render: function(item, name, index){
				if(item.sAnchorTime){
//					if(item.status==1){
//						return  "<span style='color:red'>"+ item.sAnchorTime.split(" ")[1]+"</span>";
//				}
//					
//					if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//						return  "<span style='color:blue'>"+ item.sAnchorTime.split(" ")[1]+"</span>";
//					}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//						return  "<span style='color:blue'>"+ item.sAnchorTime.split(" ")[1]+"</span>";
//					}else {
//						return item.sAnchorTime.split(" ")[1];
//					}
					
					if(item.color){
						
						if(item.color==1){
							return item.sAnchorTime.split(" ")[1];
						}else if(item.color==2){
							return  "<span style='color:blue'>"+ item.sAnchorTime.split(" ")[1]+"</span>";
						}else if(item.color==3){
							return  "<span style='color:green'>"+ item.sAnchorTime.split(" ")[1]+"</span>";
						}else if(item.color==4){
							return  "<span style='color:red'>"+ item.sAnchorTime.split(" ")[1]+"</span>";
						}
						
					}else{
						return item.sAnchorTime.split(" ")[1];
					}
					
					
				}
			}
		},{
			title : "预计开泵时间",
			name : "sPumpOpenTime",
			width : 75,
			render: function(item, name, index){
				if(item.sPumpOpenTime){
//					if(item.status==1){
//						return  "<span style='color:red'>"+ util.getSubTime(item.sPumpOpenTime,2)+"</span>";
//				}
//					if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//						return  "<span style='color:blue'>"+ util.getSubTime(item.sPumpOpenTime,2)+"</span>";
//					}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//						return  "<span style='color:blue'>"+ util.getSubTime(item.sPumpOpenTime,2)+"</span>";
//					}else {
//						return util.getSubTime(item.sPumpOpenTime,2);
//					}
					
					if(item.color){
						
						if(item.color==1){
							return util.getSubTime(item.sPumpOpenTime,2);
						}else if(item.color==2){
							return  "<span style='color:blue'>"+ util.getSubTime(item.sPumpOpenTime,2)+"</span>";
						}else if(item.color==3){
							return  "<span style='color:green'>"+ util.getSubTime(item.sPumpOpenTime,2)+"</span>";
						}else if(item.color==4){
							return  "<span style='color:red'>"+ util.getSubTime(item.sPumpOpenTime,2)+"</span>";
						}
						
					}else{
						return util.getSubTime(item.sPumpOpenTime,2);
					}
					
					
				}
			}
		},{
			title : "预计停泵时间",
			name : "sPumpStopTime",
			width : 75,
			render: function(item, name, index){
				if(item.sPumpStopTime){
					
					
//					 if(item.status==1){
//						return  "<span style='color:red'>"+ util.getSubTime(item.sPumpStopTime,2)+"</span>";
//				}
//					
//					if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//						return  "<span style='color:blue'>"+ util.getSubTime(item.sPumpStopTime,2)+"</span>";
//					}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//						return  "<span style='color:blue'>"+ util.getSubTime(item.sPumpStopTime,2)+"</span>";
//					}else{
//						return util.getSubTime(item.sPumpStopTime,2);
//					}
					
					if(item.color){
						
						if(item.color==1){
							return util.getSubTime(item.sPumpStopTime,2);
						}else if(item.color==2){
							return  "<span style='color:blue'>"+ util.getSubTime(item.sPumpStopTime,2)+"</span>";
						}else if(item.color==3){
							return  "<span style='color:green'>"+ util.getSubTime(item.sPumpStopTime,2)+"</span>";
						}else if(item.color==4){
							return  "<span style='color:red'>"+ util.getSubTime(item.sPumpStopTime,2)+"</span>";
						}
						
					}else{
						return util.getSubTime(item.sPumpStopTime,2);
					}
					
				}
			}
		},{
			title : "预计作业时间(小时)",
			width : 75,
			name : "workTime",
			render: function(item, name, index){
				if(item.workTime){
//					
//					 if(item.status==1){
//						 return  "<span style='color:red'>"+  item.workTime+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.workTime+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.workTime+"</span>";
//				}else{
//						return  item.workTime;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.workTime;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.workTime+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+ item.workTime+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.workTime+"</span>";
					}
					
				}else{
					return item.workTime;
				}
				
				}
			}
		},{
			title : "预计离港时间",
			name : "sLeaveTime",
			width : 75,
			render: function(item, name, index){
				if(item.sLeaveTime){
//					if(item.status==1){
//						return  "<span style='color:red'>"+ util.getSubTime(item.sLeaveTime,2)+"</span>";
//					}
//					if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//						return  "<span style='color:blue'>"+ util.getSubTime(item.sLeaveTime,2)+"</span>";
//					}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//						return  "<span style='color:blue'>"+ util.getSubTime(item.sLeaveTime,2)+"</span>";
//					}else{
//						return  util.getSubTime(item.sLeaveTime,2);
//					}
					
					if(item.color){
						
						if(item.color==1){
							return util.getSubTime(item.sLeaveTime,2);
						}else if(item.color==2){
							return  "<span style='color:blue'>"+ util.getSubTime(item.sLeaveTime,2)+"</span>";
						}else if(item.color==3){
							return  "<span style='color:green'>"+ util.getSubTime(item.sLeaveTime,2)+"</span>";
						}else if(item.color==4){
							return  "<span style='color:red'>"+ util.getSubTime(item.sLeaveTime,2)+"</span>";
						}
						
					}else{
						return util.getSubTime(item.sLeaveTime,2);
					}
					
				}
				
				
				
			}
		},{
			title : "泊位",
			name : "berthName",
			width : 40,
			render: function(item, name, index){
				if(item.berthName){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.berthName+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.berthName+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.berthName+"</span>";
//				}else{
//						return item.berthName;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.berthName;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.berthName+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.berthName+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.berthName+"</span>";
					}
					
				}else{
					return item.berthName;
				}
				
				}
			}
		},{
			title : "船名",
			name : "shipName",
			width : 100,
			render: function(item, name, index){
				return "<a href='javascript:void(0)' onclick='javascript:ArrivalForeshow.editForeshow("+index+")' >"+item.shipRefName+"/"+item.shipName;+"</a>";
//					return item.shipName+"/"+item.shipRefName;
			}
		},{
			title : "物料名称",
			width : 60,
			name : "productNames",
			render: function(item, name, index){
				if(item.productNames){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.productNames+"</span>";
//					}
					
					if(item.arrivalId){
						if(item.arrivalType==1){
							return "<a href='javascript:void(0)' onclick='javascript:ArrivalForeshow.getarrivalCargoInfo("+item.arrivalId+",1)' >"+item.productNames+"</a>";
							
						}else if(item.arrivalType==2){
							return "<a href='javascript:void(0)' onclick='javascript:ArrivalForeshow.getarrivalCargoInfo("+item.arrivalId+",2)' >"+item.productNames+"</a>";
							
						}else if(item.arrivalType==3){
							return "<a href='javascript:void(0)' onclick='javascript:ArrivalForeshow.getarrivalCargoInfo("+item.arrivalId+",1)' >"+item.productNames+"</a>";
							
						}else if(item.arrivalType==5){
							return "<a href='javascript:void(0)' onclick='javascript:ArrivalForeshow.getarrivalCargoInfo("+item.arrivalId+",2)' >"+item.productNames+"</a>";
							
						} else {
							return  item.productNames;
						}
					}else{
						
						if(item.color){
							
							if(item.color==1){
								return item.productNames;
							}else if(item.color==2){
								return  "<span style='color:blue'>"+ item.productNames+"</span>";
							}else if(item.color==3){
								return  "<span style='color:green'>"+item.productNames+"</span>";
							}else if(item.color==4){
								return  "<span style='color:red'>"+ item.productNames+"</span>";
							}
							
						}else{
							return item.productNames;
						}
						
						
						
//						return item.productNames;
					}
					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					
//					
////					return  "<span style='color:blue'>"+ item.productNames+"</span>";
//				}else if(item.outboundstatus==54&&item.arrivalType==2){
//					return  "<span style='color:blue'>"+ item.productNames+"</span>";
//				}else{
//						return item.productNames;
//				}
				
				}
			}
		},{
			title : "数量(吨)",
			width : 55,
			name : "count",
			render: function(item, name, index){
				if(item.count){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.count+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.count+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.count+"</span>";
//				}else{
//						return item.count;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.count;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.count+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.count+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.count+"</span>";
					}
					
				}else{
					return item.count;
				}
				
				}
			}
		},{
			title : "货主",
			name : "clientNames",
			width : 80,
			render: function(item, name, index){
				
				
				
				if(item.clientNames){
					
//					if(item.taxTypes){
//						var ide=item.taxTypes.split(",").indexOf("3");
//						
//						var tps=item.clientNames.split(",");
//						
//						for(var i ;i<tps.length;i++){
//							
//						}
						
//						if(item.taxTypes.split(",").indexOf("3")>=0){
							
							var re="";
							var isred=0;
							var cls=item.clientNames.split(",");
							for(var i=0;i<cls.length;i++){
								if(cls[i].indexOf(";")>=0){
									isred=1;
									re+="<span style='color:red'>"+  cls[i]+",</span>";
								}else{
									re+=cls[i];
//									re+="<span style='color:blue'>"+  cls[i]+",</span>";
								}
							}
							
							if(isred==1){
								
								return  re.replace(";", "");
							}else {
								if(item.color){
									
									if(item.color==1){
										return item.clientNames;
									}else if(item.color==2){
										return  "<span style='color:blue'>"+ item.clientNames+"</span>";
									}else if(item.color==3){
										return  "<span style='color:green'>"+item.clientNames+"</span>";
									}else if(item.color==4){
										return  "<span style='color:red'>"+ item.clientNames+"</span>";
									}
									
								}else
								
								{
									return item.clientNames;
								}
								
								
							}
//						};
//					}
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.clientNames+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.clientNames+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.clientNames+"</span>";
//				}else{
//						return item.clientNames;
//				}
				
				
				
				}
			}
		},{
			title : "船长(米)",
			width : 55,
			name : "sShipLenth",
			render: function(item, name, index){
				if(item.sShipLenth){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.sShipLenth+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.sShipLenth+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.sShipLenth+"</span>";
//				}else{
//						return   item.sShipLenth;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.sShipLenth;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.sShipLenth+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.sShipLenth+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.sShipLenth+"</span>";
					}
					
				}else{
					return item.sShipLenth;
				}
				}
			}
		},{
			title : "吃水(米)",
			width : 55,
			name : "shipArrivalDraught",
			render: function(item, name, index){
				if(item.shipArrivalDraught){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.shipArrivalDraught+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.shipArrivalDraught+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.shipArrivalDraught+"</span>";
//				}else{
//						return item.shipArrivalDraught;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.shipArrivalDraught;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.shipArrivalDraught+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.shipArrivalDraught+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.shipArrivalDraught+"</span>";
					}
					
				}else{
					return item.shipArrivalDraught;
				}
				}
			}
		},{
			title : "船舶代理",
			width : 80,
			name : "shipAgentName",
			render: function(item, name, index){
				if(item.shipAgentName){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.shipAgentName+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.shipAgentName+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.shipAgentName+"</span>";
//				}else{
//						return item.shipAgentName;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.shipAgentName;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.shipAgentName+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.shipAgentName+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.shipAgentName+"</span>";
					}
					
				}else{
					return item.shipAgentName;
				}
				}
			}
		},
		{
			title : "货物代理",
			width : 80,
			name : "cargoAgentNames",
			render: function(item, name, index){
				if(item.cargoAgentNames){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.cargoAgentNames+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.cargoAgentNames+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.cargoAgentNames+"</span>";
//				}else{
//						return item.cargoAgentNames;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.cargoAgentNames;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.cargoAgentNames+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.cargoAgentNames+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.cargoAgentNames+"</span>";
					}
					
				}else{
					return item.cargoAgentNames;
				}
				
				}
			}
		},
		{
			title : "港序",
			width : 40,
			name : "port",
			render: function(item, name, index){
				if(item.port){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.port+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.port+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.port+"</span>";
//				}else{
//						return item.port;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.port;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.port+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.port+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.port+"</span>";
					}
					
				}else{
					return item.port;
				}
				
				}
			}
		},
		{
			title : "接卸通知单",
			width : 70,
			name : "unloadNotify",
			render: function(item, name, index){
				if(item.unloadNotify){
					

//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.unloadNotify+"</span>";
//					}
//					
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.unloadNotify+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.unloadNotify+"</span>";
//				}else{
//						return item.unloadNotify;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.unloadNotify;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.unloadNotify+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.unloadNotify+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.unloadNotify+"</span>";
					}
					
				}else{
					return item.unloadNotify;
				}
				
				
				}
			}
		},
		{
			title : "海关是否同意卸货",
			width : 50,
			name : "isCustomAgree",
			render: function(item, name, index){
				var isc="";
			if(item.isCustomAgree==0){
				isc= "否";
			}else if(item.isCustomAgree==1){
				isc= "是";
			}else {
				isc="不适用";
			}

//			if(item.status==1){
//				return  "<span style='color:red'>"+ isc+"</span>";
//			}
//			
//			if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//				return  "<span style='color:blue'>"+ isc+"</span>";
//			}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//				return  "<span style='color:blue'>"+ isc+"</span>";
//			}else{
//				return isc;
//			}
		
			if(item.color){
				
				if(item.color==1){
					return isc;
				}else if(item.color==2){
					return  "<span style='color:blue'>"+ isc+"</span>";
				}else if(item.color==3){
					return  "<span style='color:green'>"+isc+"</span>";
				}else if(item.color==4){
					return  "<span style='color:red'>"+ isc+"</span>";
				}
				
			}else{
				return isc;
			}
			
			
		}
				
		},
		{
			title : "备注",
			width : 50,
			name : "note",
			render: function(item, name, index){
				if(item.note){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.note+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.note+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.note+"</span>";
//				}else{
//					return item.note;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.note;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.note+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.note+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.note+"</span>";
					}
					
				}else{
					return item.note;
				}
				
				}
			}
		},
		{
			title : "本年度航次",
			name : "portNum",
			width : 40,
			render: function(item, name, index){
				if(item.portNum){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.portNum+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.portNum+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.portNum+"</span>";
//				}else{
//						return item.portNum;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.portNum;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.portNum+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.portNum+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.portNum+"</span>";
					}
					
				}else{
					return item.portNum;
				}
				
				}
			}
		},{
			title : "船舶信息表",
			name : "shipInfo",
			width : 80,
			render: function(item, name, index){
				if(item.shipInfo){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.shipInfo+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.shipInfo+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.shipInfo+"</span>";
//				}else{
//						return item.shipInfo;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.shipInfo;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.shipInfo+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.shipInfo+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.shipInfo+"</span>";
					}
					
				}else{
					return item.shipInfo;
				}
				
				}
			}
		},
		{
			title : "申报",
			name : "report",
			width : 40,
			render: function(item, name, index){
				if(item.report){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.report+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.report+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.report+"</span>";
//				}else{
//					return item.report;	
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.report;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.report+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.report+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.report+"</span>";
					}
					
				}else{
					return item.report;
				}
				
				}
			}
				
		},
		{
			title : "允许最大在港时间(以卸货速度150t/h计算)",
			name : "sLastLeaveTime",
			width : 80,
			render: function(item, name, index){
				if(item.sLastLeaveTime){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.sLastLeaveTime+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.sLastLeaveTime+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.sLastLeaveTime+"</span>";
//				}else{
//						return item.sLastLeaveTime;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.sLastLeaveTime;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.sLastLeaveTime+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.sLastLeaveTime+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.sLastLeaveTime+"</span>";
					}
					
				}else{
					return item.sLastLeaveTime;
				}
				
				
				}else if(item.sNorTime){
					 
					
					
					
					
					
					

						var time = item.sNorTime;
						if(time.length==10){
							time=time+" 00:00:00";
						}
						var time2 = time.replace(/-/g, "/");
						if(time2.length==10){
							time2=time2+" 00:00:00";
						}
						var d = new Date(time2);
						var num =item.count;
						d.setTime(util.FloatAdd(d.getTime(),(6 + num / 150) * 3600000));
						var dtime = new Date(d.getTime());
						
						
						
						
//				        var dtime = new Date(dayTime);  
				        dtime.setTime(dtime.getTime()+365*100);  
				        var date = new Date(dtime.getTime());  
				        var year = date.getFullYear();  
				        var month = date.getMonth()+1;  
				        var day = date.getDate();  
				        var hour = date.getHours();  
				        var minute = date.getMinutes();  
				        var second = date.getSeconds();  
				        var date1 = year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day)+" "+(hour<10?"0"+hour:hour)+":"+(minute<10?"0"+minute:minute)+":"+(second<10?"0"+second:second); 
						
						
						
						
//						item.sLastLeaveTime=t.Format("yyyy-MM-dd HH:mm:ss");
				        item.sLastLeaveTime=date1;
						if(item.color){
							
							if(item.color==1){
								return item.sLastLeaveTime;
							}else if(item.color==2){
								return  "<span style='color:blue'>"+ item.sLastLeaveTime+"</span>";
							}else if(item.color==3){
								return  "<span style='color:green'>"+item.sLastLeaveTime+"</span>";
							}else if(item.color==4){
								return  "<span style='color:red'>"+ item.sLastLeaveTime+"</span>";
							}
							
						}else{
							return item.sLastLeaveTime;
						}
					
					
				}
			}
		},
		{
			title : "拆管时间",
			name : "sTearPipeTime",
			width : 70,
			render: function(item, name, index){
				if(item.sTearPipeTime){
//					if(item.status==1){
//						return  "<span style='color:red'>"+  util.getSubTime(item.sTearPipeTime,2)+"</span>";
//					}
//					if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//						return  "<span style='color:blue'>"+ util.getSubTime(item.sTearPipeTime,2)+"</span>";
//					}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//						return  "<span style='color:blue'>"+ util.getSubTime(item.sTearPipeTime,2)+"</span>";
//					}else{
//							return util.getSubTime(item.sTearPipeTime,2);
//					}
					
					if(item.color){
						
						if(item.color==1){
							return util.getSubTime(item.sTearPipeTime,2);
						}else if(item.color==2){
							return  "<span style='color:blue'>"+ util.getSubTime(item.sTearPipeTime,2)+"</span>";
						}else if(item.color==3){
							return  "<span style='color:green'>"+util.getSubTime(item.sTearPipeTime,2)+"</span>";
						}else if(item.color==4){
							return  "<span style='color:red'>"+ util.getSubTime(item.sTearPipeTime,2)+"</span>";
						}
						
					}else{
						return util.getSubTime(item.sTearPipeTime,2);
					}
					
					
				}
			}
		},
		{
			title : "速遣时间(小时)",
			width : 40,
			name : "repatriateTime",
			render: function(item, name, index){
				if(item.repatriateTime){
					
//					if(item.status==1){
//						return  "<span style='color:red'>"+  item.repatriateTime+"</span>";
//					}
//					
//				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
//					return  "<span style='color:blue'>"+ item.repatriateTime+"</span>";
//				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
//					return  "<span style='color:blue'>"+ item.repatriateTime+"</span>";
//				}else{
//						return item.repatriateTime;
//				}
				
				if(item.color){
					
					if(item.color==1){
						return item.repatriateTime;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.repatriateTime+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.repatriateTime+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.repatriateTime+"</span>";
					}
					
				}else{
					return item.repatriateTime;
				}
				
				}else if(item.sLeaveTime&&item.sLastLeaveTime){
					var time=util.getDifferSQTime(item.sLeaveTime,item.sLastLeaveTime);
					if(time>0){
						//速遣
						
						
						if(item.color){
							
							if(item.color==1){
								return time;
							}else if(item.color==2){
								return  "<span style='color:blue'>"+ time+"</span>";
							}else if(item.color==3){
								return  "<span style='color:green'>"+time+"</span>";
							}else if(item.color==4){
								return  "<span style='color:red'>"+ time+"</span>";
							}
						
					}else{
						//超期
						return "";
					}
				}
			}
		}
		},
		{
			title : "超期时间(小时)",
			width : 40,
			name : "overTime",
			render: function(item, name, index){
				
				
				
				if(item.overTime){
				
				if(item.color){
					
					if(item.color==1){
						return item.overTime;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ item.overTime+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+item.overTime+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ item.overTime+"</span>";
					}
					
				}else{
					return item.overTime;
				}
				
				}else if(item.sLeaveTime&&item.sLastLeaveTime){
					
					var time=util.getDifferSQTime(item.sLeaveTime,item.sLastLeaveTime);
					if(time>0){
						//速遣
						return "";
					}else if (time<0){
						if(item.color){
							
							if(item.color==1){
								return -time;
							}else if(item.color==2){
								return  "<span style='color:blue'>"+ (-time)+"</span>";
							}else if(item.color==3){
								return  "<span style='color:green'>"+(-time)+"</span>";
							}else if(item.color==4){
								return  "<span style='color:red'>"+ (-time)+"</span>";
							}
					}
				}else {
					return "";
				}

				
				
				
				}
			}
		},
		{
			title : "状态",
			width : 40,
			name : "repatriateTime",
			render: function(item, name, index){
					var stat="";
					if(item.status==1){
						stat="作废";
//						return  "<span style='color:red'>作废</span>";
					}
					
				if(item.inboundstatus==0&&(item.arrivalType==1 || item.arrivalType==3)){
					
					if(item.arrivalType==3){
						if(item.wleaveTime){
							stat="已完成";
						}else{
							stat="未完成";
						}
					}else{
						
						stat="已完成";
					}
					
//					return  "<span style='color:blue'>已完成</span>";
				}else if(item.outboundstatus==54&&(item.arrivalType==2||item.arrivalType==5)){
					stat="已完成";
//					return  "<span style='color:blue'>已完成</span>";
				}else if(item.arrivalId){
					stat="未完成";
//						return "未完成";
				}else{
					stat="未关联";
//					return "未关联";
				}
				
				if(item.color){
					
					if(item.color==1){
						return stat;
					}else if(item.color==2){
						return  "<span style='color:blue'>"+ stat+"</span>";
					}else if(item.color==3){
						return  "<span style='color:green'>"+stat+"</span>";
					}else if(item.color==4){
						return  "<span style='color:red'>"+ stat+"</span>";
					}
					
				}else{
					return stat;
				}
				
				
			}
		},
		{
			title : "操作",
			name : "id",
			width : 100,
			render: function(item, name, index){
				var permi="<div class='input-group-btn'>";
				
				
				if(item.status==1){
					if(config.hasPermission('AARRIVALFORESHOWDELETE')){
						
						permi+= "<a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='ArrivalForeshow.deleteItem("+item.id+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a>";
						
					}
				}else{
					
					if(config.hasPermission('AARRIVALFORESHOWUPDATE')){
						permi+= "<a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='javascript:ArrivalForeshow.editForeshow("+index+")' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a>";
						
					}
					if(config.hasPermission('AARRIVALFORESHOWDELETE')){
						
						permi+= "<a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='ArrivalForeshow.deleteItem("+item.id+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a>";
						permi+= "<a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='ArrivalForeshow.zfItem("+item.id+")' class='btn btn-xs red'><span class='fa fa-trash-o ' title='作废'></span></a>";
						
					}
					if(config.hasPermission('AARRIVALFORESHOWUPDATE')){
						
						permi+="<a href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' onclick='ArrivalForeshow.copyToForeshow("+item.arrivalId+","+item.shipId+","+item.id+")' class='btn btn-xs blue'><span class='fa fa-anchor' title='关联实际入港'></span></a>";
					}
				}
				permi+="</div>";
				
				
				if(config.hasPermission('AARRIVALFORESHOWUPDATE')){
				
				permi+="<div  style='margin-top:5px'>";
				permi+="<button href='javascript:void(0)' style='margin:0 2px;font-size: 9px;background:#000000' class='btn  ' title='未完成' onclick='ArrivalForeshow.changeColor("+item.id+",1)' ></button>";
				permi+="<button href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' class='btn  blue' title='已完成' onclick='ArrivalForeshow.changeColor("+item.id+",2)' ></button>";
//				permi+="<button href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' class='btn  green' onclick='ArrivalForeshow.changeColor("+item.id+",3)' ></button>";
				permi+="<button href='javascript:void(0)' style='margin:0 2px;font-size: 9px;' class='btn  red' title='计划取消' onclick='ArrivalForeshow.changeColor("+item.id+",4)' ></button>";
				permi+="</div>";
				}
				return permi;
			}
		}  ];

		
		/*解决id冲突的问题*/
		$('[data-role="arrivalForeshowGrid"]').grid({
			
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : false,
			autoLoad:false,
//			data　: getData()
//			isUserLocalData : true,
//			localData : $.parseJSON(getData())
			url : config.getDomain()+"/arrivalForeshow/list",
			callback:function(){
				util.setColumnsVisable($('[data-role="arrivalForeshowGrid"]'),[0],columnCheckArray,function(){columnCheckArray=util.getColumnsCheckArray();});
			}
			
		});
		ArrivalForeshow.search();
		//初始化按钮操作
		$(".btn-add").unbind('click'); 
		$(".btn-add").click(function() {
			addForeshow();
		});
		
		//删除
		$(".update-all").unbind('click'); 
		$(".update-all").click(function() {
			var data = $('[data-role="arrivalForeshowGrid"]').getGrid().selectedRowsIndex();
			var $this = $('[data-role="arrivalForeshowGrid"]');
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要更新的记录'
				});
				return;
			}
				 $.each($('[data-role="arrivalForeshowGrid"]').getGrid().selectedRows(), function (i, role) {
			        	if(role.arrivalId){
							config.load();
							//更新船期预告
							$.ajax({
								async : false,
								type : "get",
								url : config.getDomain()
										+ "/arrivalForeshow/updateForeshowBySQL?arrivalId="+role.arrivalId,
								dataType : "json",
								success : function(data) {
								config.unload();
								if (data.code == "0000") {
									$("body").message(
											{
												type : 'success',
												content : '更新成功'
											});
									
								}else{
									$("body").message(
											{
												type : 'error',
												content : '更新失败'
											});
								}
								}
							});
							
			        	}
			        });
				 
				 $('[data-role="arrivalForeshowGrid"]').getGrid().refresh();
//					 $this.confirm({
//						 content : 'arrivalForeshowGrid?',
//						 callBack : function() {
//							 deleteIntent($('[data-role="arrivalGrid"]').getGrid().selectedRows(), $this);
//						 }
//					 });
				
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
		
		
	};
    
	

	
	
	function addForeshow (){

		$.get(config.getResource() + "/pages/inbound/arrivalForeshow/editForeshow.jsp")
				.done(function(data) {
					dialog = $(data);
					mDialog=dialog;
					initAddDialog(dialog);
					dialog.modal({
						keyboard : true
					});
				});

	
	}
	
	function initAddDialog(dialog){
		util.initTimePicker(dialog.find(".arrivalForeshowForm"));
		
		$.ajax({
			type : "get",
			url : config.getDomain()+"/shipref/list?shipId=0",
			dataType : "json",
			success : function(data) {
				dialog.find('#shipRefId').typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product.refName+"["+product.shipName+"]";
  	                    });
  	                    process(results);
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (product) {
                        return product.refName+"["+product.shipName+"]" == query;
                    });
  					if(client==null){
  						dialog.find('#shipRefId').removeAttr("data");
  						dialog.find('#shipRefId').val("");
  					}else{
  						dialog.find('#shipRefId').attr("data",client.id);
  					}
  				}
  				});
				
			}
		});
		
		$.ajax({
			type : "get",
			url : config.getDomain()+"/shipagent/list",
			dataType : "json",
			success : function(data) {
				dialog.find('#shipAgentId').typeahead({
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
  						dialog.find('#shipAgentId').removeAttr("data");
  						dialog.find('#shipAgentId').val("");
  					}else{
  						dialog.find('#shipAgentId').attr("data",client.id);
  					}
  				}
  				});
				
			}
		});
		
		
		$.ajax({
			type : "get",
			url : config.getDomain()+"/berth/list",
			dataType : "json",
			success : function(data) {
				dialog.find('#berth').typeahead({
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
  						dialog.find('#berth').removeAttr("data");
  						dialog.find('#berth').val("");
  					}else{
  						dialog.find('#berth').attr("data",client.id);
  					}
  				}
  				});
				
			}
		});
		
		
		dialog.find("#save").click(function(){
			config.load();
			$.ajax({
				type : "post",
				url : config.getDomain()+"/arrivalForeshow/addForeshow",
				dataType : "json",
				data : {
					"shipRefId":dialog.find("#shipRefId").attr("data"),
					"productNames":dialog.find("#productNames").val(),
					"berth":dialog.find("#berth").attr("data"),
					"count":dialog.find("#count").val(),
					"shipArrivalDraught":dialog.find("#shipArrivalDraught").val(),
					"shipAgentId":dialog.find("#shipAgentId").attr("data"),
					"cargoAgentNames":dialog.find("#cargoAgentNames").val(),
					"port":dialog.find("#port").val(),
					"unloadNotify":dialog.find("#unloadNotify").val(),
					"isCustomAgree":dialog.find("#isCustomAgree").val(),
					"note":dialog.find("#note").val(),
					"portNum":dialog.find("#portNum").val(),
					"shipInfo":dialog.find("#shipInfo").val(),
					"clientNames":dialog.find("#clientNames").val(),
					"report":dialog.find("#report").val(),
					"lastLeaveTime":util.formatLong(dialog.find("#lastLeaveTime").val()),
					"repatriateTime":dialog.find("#repatriateTime").val(),
					"overTime":dialog.find("#overTime").val(),
					"workTime":dialog.find("#workTime").text(),
					
					"cjTime":util.formatLong(util.getTimeVal(dialog.find("#cjTime"))),
					"tcTime":util.formatLong(util.getTimeVal(dialog.find("#tcTime"))),
					"norTime":util.formatLong(util.getTimeVal(dialog.find("#norTime"))),
					"anchorTime":util.formatLong(util.getTimeVal(dialog.find("#anchorTime"))),
					"pumpOpenTime":util.formatLong(util.getTimeVal(dialog.find("#pumpOpenTime"))),
					"pumpStopTime":util.formatLong(util.getTimeVal(dialog.find("#pumpStopTime"))),
					"leaveTime":util.formatLong(util.getTimeVal(dialog.find("#leaveTime"))),
					"tearPipeTime":util.formatLong(util.getTimeVal(dialog.find("#tearPipeTime"))),
				},
				success : function(data) {
					config.unload();
					if (data.code == "0000") {
						$("body").message({
							type: 'success',
							content: '添加成功'
								
						});
						dialog.remove();
						$('[data-role="arrivalForeshowGrid"]').getGrid().refresh();
					} else {
						$("body").message({
							type: 'error',
							content: '添加失败'
						});
					}
				}
			});
		});
		
		
		
		
	}
	
	
	function editForeshow(index) {
		$.get(config.getResource() + "/pages/inbound/arrivalForeshow/editForeshow.jsp")
				.done(function(data) {
					dialog = $(data);
					mDialog=dialog;
					initDialog(dialog, index);
					dialog.modal({
						
						keyboard : true
					});
					
					
				});

	}
	
	function initDialog(dialog, index){
		
		var indexData=$('[data-role="arrivalForeshowGrid"]').getGrid().getItemByIndex(index);
		
		util.initTimePicker(dialog.find(".arrivalForeshowForm"));
		

		dialog.find('#norCleanBtn').click(function(){
			countLastLeaveTime();
		});
		
		
		$.ajax({
			type : "get",
			url : config.getDomain()+"/shipref/list?shipId=0",
			dataType : "json",
			success : function(data) {
				dialog.find('#shipRefId').typeahead({
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
  						dialog.find('#shipRefId').removeAttr("data");
  						dialog.find('#shipRefId').val("");
  					}else{
  						dialog.find('#shipRefId').attr("data",client.id);
  					}
  				}
  				});
				
			}
		});
		
		$.ajax({
			type : "get",
			url : config.getDomain()+"/shipagent/list",
			dataType : "json",
			success : function(data) {
				dialog.find('#shipAgentId').typeahead({
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
  						dialog.find('#shipAgentId').removeAttr("data");
  						dialog.find('#shipAgentId').val("");
  					}else{
  						dialog.find('#shipAgentId').attr("data",client.id);
  					}
  				}
  				});
				
			}
		});
		
		
		$.ajax({
			type : "get",
			url : config.getDomain()+"/berth/list",
			dataType : "json",
			success : function(data) {
				dialog.find('#berth').typeahead({
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
  						dialog.find('#berth').removeAttr("data");
  						dialog.find('#berth').val("");
  					}else{
  						dialog.find('#berth').attr("data",client.id);
  					}
  				}
  				});
				
			}
		});
		
		util.initTimeVal(dialog.find("#anchorTime"),indexData.sAnchorTime.substring(0,19));
		
		util.initTimeVal(dialog.find("#pumpOpenTime"), indexData.sPumpOpenTime);
		util.initTimeVal(dialog.find("#pumpStopTime"), indexData.sPumpStopTime);
		util.initTimeVal(dialog.find("#tearPipeTime"), indexData.sTearPipeTime);
		util.initTimeVal(dialog.find("#leaveTime"), indexData.sLeaveTime);
		util.initTimeVal(dialog.find("#cjTime"), indexData.sCjTime);
		util.initTimeVal(dialog.find("#tcTime"), indexData.sTcTime);
		util.initTimeVal(dialog.find("#norTime"), indexData.sNorTime);
//		dialog.find("#tcTime").val( indexData.sTcTime);
		dialog.find("#lastLeaveTime").val(indexData.sLastLeaveTime);
		dialog.find("#shipRefId").attr("data",indexData.shipRefId);
		dialog.find("#shipRefId").val(indexData.shipRefName);
		dialog.find("#productNames").val(indexData.productNames);
		dialog.find("#berth").val(indexData.berthName);
		dialog.find("#berth").attr("data",indexData.berth);
		dialog.find("#count").val(indexData.count);
		dialog.find("#shipArrivalDraught").val(indexData.shipArrivalDraught);
		dialog.find("#shipAgentId").attr("data",indexData.shipAgentId);
		dialog.find("#shipAgentId").val(indexData.shipAgentName);
		dialog.find("#cargoAgentNames").val(indexData.cargoAgentNames);
		dialog.find("#port").val(indexData.port);
		dialog.find("#unloadNotify").val(indexData.unloadNotify);
		dialog.find("#isCustomAgree").val(indexData.isCustomAgree);
		dialog.find("#note").val(indexData.note);
		dialog.find("#portNum").val(indexData.portNum);
		dialog.find("#shipInfo").val(indexData.shipInfo);
		if(indexData.clientNames){
			
			dialog.find("#clientNames").val(indexData.clientNames.replace(";",""));
		}
		dialog.find("#report").val(indexData.report);
		
		if(indexData.sNorTime&&!dialog.find("#lastLeaveTime").val()){
//			alert(dialog.find("#lastLeaveTime").val());
//			alert(indexData.sNorTime);
			countLastLeaveTime();
		}
		
		if(indexData.repatriateTime){
			
			dialog.find("#repatriateTime").val(indexData.repatriateTime);
		}else if(indexData.sLeaveTime&&dialog.find("#lastLeaveTime").val()){

			var time=util.getDifferSQTime(util.getTimeVal(dialog.find("#leaveTime")),dialog.find("#lastLeaveTime").val());
			if(time>0){
			//速遣
				dialog.find("#repatriateTime").val(time);
				dialog.find("#overTime").val("");
			}else{
			//超期
				dialog.find("#overTime").val(-time);
				dialog.find("#repatriateTime").val("");
			}
			
		}
		
		
		
		if(indexData.overTime){
			
			dialog.find("#overTime").val(indexData.overTime);
		}else if(indexData.sLeaveTime&&dialog.find("#lastLeaveTime").val()){

			var time=util.getDifferSQTime(util.getTimeVal(dialog.find("#leaveTime")),dialog.find("#lastLeaveTime").val());
			if(time>0){
			//速遣
				dialog.find("#repatriateTime").val(time);
				dialog.find("#overTime").val("");
			}else{
			//超期
				dialog.find("#overTime").val(-time);
				dialog.find("#repatriateTime").val("");
			}
			
		}
		
		dialog.find("#workTime").text(indexData.workTime==null?"":indexData.workTime);
		
		
		
		dialog.find("#save").click(function(){
			config.load();
			$.ajax({
				type : "post",
				url : config.getDomain()+"/arrivalForeshow/updateForeshow?id="+indexData.id,
				dataType : "json",
				data : {
					"arrivalId":indexData.arrivalId,
					"shipRefId":dialog.find("#shipRefId").attr("data")?dialog.find("#shipRefId").attr("data"):-1,
					"productNames":dialog.find("#productNames").val()!=""?dialog.find("#productNames").val():-1,
					"berth":dialog.find("#berth").attr("data")?dialog.find("#berth").attr("data"):-1,
					"count":dialog.find("#count").val()!=""?dialog.find("#count").val():-1,
					"shipArrivalDraught":dialog.find("#shipArrivalDraught").val()!=""?dialog.find("#shipArrivalDraught").val():-1,
					"shipAgentId":dialog.find("#shipAgentId").attr("data")?dialog.find("#shipAgentId").attr("data"):-1,
					"cargoAgentNames":dialog.find("#cargoAgentNames").val()!=""?dialog.find("#cargoAgentNames").val():-1,
					"port":dialog.find("#port").val()!=""?dialog.find("#port").val():-1,
					"unloadNotify":dialog.find("#unloadNotify").val()!=""?dialog.find("#unloadNotify").val():-1,
					"isCustomAgree":dialog.find("#isCustomAgree").val()?dialog.find("#isCustomAgree").val():-1,
					"note":dialog.find("#note").val()!=""?dialog.find("#note").val():-1,
					"portNum":dialog.find("#portNum").val()!=""?dialog.find("#portNum").val():-1,
					"shipInfo":dialog.find("#shipInfo").val()!=""?dialog.find("#shipInfo").val():-1,
					"clientNames":dialog.find("#clientNames").val()!=""?dialog.find("#clientNames").val():-1,
					"report":dialog.find("#report").val()!=""?dialog.find("#report").val():-1,
					"lastLeaveTime":util.formatLong(dialog.find("#lastLeaveTime").val())?util.formatLong(dialog.find("#lastLeaveTime").val()):-1,
					"repatriateTime":dialog.find("#repatriateTime").val()?dialog.find("#repatriateTime").val():-1,
					"overTime":dialog.find("#overTime").val()!=""?dialog.find("#overTime").val():-1,
					"workTime":dialog.find("#workTime").text()!=""?dialog.find("#workTime").text():-1,
					
					"cjTime":util.formatLong(util.getTimeVal(dialog.find("#cjTime")))?util.formatLong(util.getTimeVal(dialog.find("#cjTime"))):-1,
//					"tcTime":dialog.find("#tcTime").val()!=""?dialog.find("#tcTime").val():-1,
					"tcTime":util.formatLong(util.getTimeVal(dialog.find("#tcTime")))?util.formatLong(util.getTimeVal(dialog.find("#tcTime"))):-1,
											
					"norTime":util.formatLong(util.getTimeVal(dialog.find("#norTime")))?util.formatLong(util.getTimeVal(dialog.find("#norTime"))):-1,
					"anchorTime":util.formatLong(util.getTimeVal(dialog.find("#anchorTime")))?util.formatLong(util.getTimeVal(dialog.find("#anchorTime"))):-1,
					"pumpOpenTime":util.formatLong(util.getTimeVal(dialog.find("#pumpOpenTime")))?util.formatLong(util.getTimeVal(dialog.find("#pumpOpenTime"))):-1,
					"pumpStopTime":util.formatLong(util.getTimeVal(dialog.find("#pumpStopTime")))?util.formatLong(util.getTimeVal(dialog.find("#pumpStopTime"))):-1,
					"leaveTime":util.formatLong(util.getTimeVal(dialog.find("#leaveTime")))?util.formatLong(util.getTimeVal(dialog.find("#leaveTime"))):-1,
					"tearPipeTime":util.formatLong(util.getTimeVal(dialog.find("#tearPipeTime")))?util.formatLong(util.getTimeVal(dialog.find("#tearPipeTime"))):-1,
				},
				success : function(data) {
					config.unload();
					if (data.code == "0000") {
						$("body").message({
							type: 'success',
							content: '更新成功'
								
						});
						dialog.remove();
						$('[data-role="arrivalForeshowGrid"]').getGrid().refresh();
					} else {
						$("body").message({
							type: 'error',
							content: '更新失败'
						});
					}
				}
			});
		});
		
		
		
	}
	
	
	
	


	//计算作业时间
	function getDifferTime(){
		
		mDialog.find("#workTime").text(util.getDifferTime(util.getTimeVal(mDialog.find("#pumpOpenTime")),util.getTimeVal(mDialog.find("#pumpStopTime"))));
	}
	
	//计算超期/速遣时间
	function getSQTime(){
	var time=util.getDifferSQTime(util.getTimeVal(mDialog.find("#tearPipeTime")),mDialog.find("#lastLeaveTime").val());
	if(time>0){
	//速遣
	mDialog.find("#repatriateTime").val(time);
	mDialog.find("#overTime").val("");
	}else{
	//超期
	mDialog.find("#overTime").val(-time);
	mDialog.find("#repatriateTime").val("");
	}
	}
	
	//同步计算最大在港时间
	function countLastLeaveTime() {
		if (util.getTimeVal(mDialog.find("#norTime")) != "") {
			var time = util.getTimeVal(mDialog.find("#norTime"));
			if(time.length==10){
				time=time+" 00:00:00";
			}
			var time2 = time.replace(/-/g, "/");
			if(time2.length==10){
				time2=time2+" 00:00:00";
			}
			var d = new Date(time2);
			var num = mDialog.find("#count").val();
			d.setTime(util.FloatAdd(d.getTime(),(6 + num / 150) * 3600000));
			var t = new Date(d.getTime());
			if((d.getTime()+"").length!=13){
				mDialog.find("body").message({
					type:'warning',
					content:"货品总量过大，无法计算最大在港时间"
				});
			}else{
				
				var dtime = new Date(d.getTime());
				
				dtime.setTime(t.getTime()+365*100);  
		        var date = new Date(dtime.getTime());  
		        var year = date.getFullYear();  
		        var month = date.getMonth()+1;  
		        var day = date.getDate();  
		        var hour = date.getHours();  
		        var minute = date.getMinutes();  
		        var second = date.getSeconds();  
		        var date1 = year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day)+" "+(hour<10?"0"+hour:hour)+":"+(minute<10?"0"+minute:minute)+":"+(second<10?"0"+second:second); 
				
				
			mDialog.find("#lastLeaveTime").val(date1);
			}
		}else{
			mDialog.find("#lastLeaveTime").val("");
		}
		
		if(mDialog.find("#tearPipeTime").val()){
		getSQTime();
		}
		
		
	};
	
	function zfItem(id){
		$(this).confirm({
			content : '确定要作废?',
			callBack : function() {
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain()+"/arrivalForeshow/zfForeshow?id="+id,
			dataType : "json",
			
			success : function(data) {
				config.unload();
				if (data.code == "0000") {
					$("body").message({
						type: 'success',
						content: '作废成功'
							
					});
					$('[data-role="arrivalForeshowGrid"]').getGrid().refresh();
				} else {
					$("body").message({
						type: 'error',
						content: '作废失败'
					});
				}
			}
		});
			}
		});
	}
	
	
	function deleteItem(id){
		$(this).confirm({
			content : '确定要删除?',
			callBack : function() {
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain()+"/arrivalForeshow/deleteForeshow?id="+id,
			dataType : "json",
			
			success : function(data) {
				config.unload();
				if (data.code == "0000") {
					$("body").message({
						type: 'success',
						content: '删除成功'
							
					});
					$('[data-role="arrivalForeshowGrid"]').getGrid().refresh();
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
	
	
	function copyToForeshow(arrivalId,shipId,id){
		//如果已经关联过则更新
		if(arrivalId){
			$(this).confirm({
				content : '是否更新到最新状态？',
				concel:false,
				callBack : function() {
					config.load();
					//更新船期预告
					$.ajax({
						async : false,
						type : "get",
						url : config.getDomain()
								+ "/arrivalForeshow/updateForeshowBySQL?arrivalId="+arrivalId,
						dataType : "json",
						success : function(data) {
						config.unload();
						if (data.code == "0000") {
							$("body").message(
									{
										type : 'success',
										content : '更新成功'
									});
							$('[data-role="arrivalForeshowGrid"]').getGrid().refresh();
						}else{
							$("body").message(
									{
										type : 'error',
										content : '更新失败'
									});
						}
						}
					});
					
					
				}
			});
		}
		//否则弹框关联
		else{
			$.get(config.getResource() + "/pages/inbound/arrivalForeshow/search_connect.jsp")
			.done(function(ndata) {
				dialog = $(ndata);
				dialog.find(".modal-title").text("选择关联的入港");
				initForeshowDialog(dialog,shipId,id);
				nDialog=dialog;
				dialog.modal({
					keyboard : true
				});
			});

		}
		
		
		
		
			
	}
	
	
	function initForeshowDialog(dialog,shipId,foreshowId){
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
							if(item.mArrivalTime){
								return item.mArrivalTime;
							}
						}
		   		},{
		   			title : "类型",
		   			name : "type",
		   			render: function(item, name, index){
						if(item.type==1){
							return "入港";
						}else if(item.type==2){
							return "出港";
						}else if(item.type==3){
							return "通过";
						}
					}
	   		},
		   		{
		   			title : "操作",
		   			name : "shipName",
		   			render: function(item, name, index){
		   				return "<shiro:hasPermission name='AARRIVALPLANUPDATE'><a href='javascript:void(0)' onclick='ArrivalForeshow.updateForeshow("+item.id+","+foreshowId+")'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='选择'></span></a></shiro:hasPermission>";
						
					}
	   		}];
		
		/*解决id冲突的问题*/
		dialog.find('[data-role="searchForeshow"]').grid({
			
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
//			isUserLocalData : true,
//			localData : data.data
			url : config.getDomain()+"/arrivalPlan/list?shipId="+shipId+"&arrivalStatus=5",
			
		});
		
	}
	
	function getarrivalCargoInfo(arrivalId,type){
		var url="";
		if(type==1){
			url="/arrivalPlan/getsure?arrivalId="+arrivalId;
		}else if(type==2){
			url="/arrivalForeshow/getOutArrivalCargo?arrivalId="+arrivalId;
		}
		$.get(config.getResource()+ "/pages/inbound/inboundoperation/checkgoodsdetail.jsp")
			.done(function(data) {
				dialog = $(data);
						
						var columns=[{
 							title:"货品",
 							width : 40,
 							name:"productName"
 						},{
 							title:"货主",
 							name:"clientName"
 						},{
 							title:"批号",
 							name:"code",
 							render:function(item){
 								if(type==1){
 									
 									return item.code;
 								}else{
 									return item.cargoCode;
 								}
 							}
 						},{
 							title:"数量(吨)",
 							render:function(item){
 								if(type==1){
 									
 									return util.toDecimal3(item.goodsPlan,true);
 								}else{
 									return util.toDecimal3(item.goodsTotal,true);
 								}
 							}
 						}];
						dialog.find('[data-role="goodsDetailGrid"]').grid({
							identity : 'id',
 							columns:columns,
 							isShowIndexCol : false,
 							isShowPages : false,
 							url : config.getDomain()+url,
 						});
						dialog.modal({
							
							keyboard : true
						});
			}
			);
		
	}
	
	function updateForeshow(arrivalId,foreshowId){
		//更新船期预告
		$.ajax({
			async : false,
			type : "get",
			url : config.getDomain()
					+ "/arrivalForeshow/connectForeshow?id="+foreshowId+"&&arrivalId="+arrivalId,
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
				$('[data-role="arrivalForeshowGrid"]').getGrid().refresh();
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
	
	function changeColor(id,color){
		
		$.ajax({
			type : "post",
			url : config.getDomain()+"/arrivalForeshow/updateForeshow?id="+id,
			dataType : "json",
			data : {
				"color":color,
			},
			success : function(data) {
				config.unload();
				if (data.code == "0000") {
					$("body").message({
						type: 'success',
						content: '更新成功'
							
					});
					$('[data-role="arrivalForeshowGrid"]').getGrid().refresh();
				} else {
					$("body").message({
						type: 'error',
						content: '更新失败'
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
            params["order"]=order;
           $('[data-role="arrivalForeshowGrid"]').getGrid().search(params);
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
		exportExcel:exportExcel,
		editForeshow:editForeshow,
		addForeshow:addForeshow,
		getSQTime:getSQTime,
		countLastLeaveTime:countLastLeaveTime,
		getDifferTime:getDifferTime,
		deleteItem:deleteItem,
		copyToForeshow:copyToForeshow,
		updateForeshow:updateForeshow,
		zfItem:zfItem,
		getarrivalCargoInfo:getarrivalCargoInfo,
		
		changeColor:changeColor
		
	};
	
	
	
}();