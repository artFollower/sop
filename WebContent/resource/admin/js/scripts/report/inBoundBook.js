/**
 * 分流台账明细报表
 */

var InBoundBook= function(){
	var columnCheckArray;
	/**
	 * 初始化查询条件
	 */
	var initCondition = function(){
		util.initDatePicker();
		
		
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
		
		$("#startTime").val(util.currentTime(0));
		$("#endTime").val(util.currentTime(0));
	};
	
	/**
	 * 形成表格
	 */
	function buildTab(){
		//表单字段
		var columns = [ {
			title : "船名",
			width: 90,
			name : "shipName"
		}, {
			title : "品种",
			width: 70,
			name : "productName"
		},{
			title : "卸货数量(吨)",
			width: 80,
			name : "tankAmount"
		},{
			title : "工艺管线",
			width: 80,
			name : "tubeName",
			render:function(item,name,index){
				if(item.tubeName!=null){
					return item.tubeName.replace(new RegExp(",","gm"),"<br></br>");
				}
				else{
					return "";
				}
			}
		},{
			title : "泊位",
			width: 60,
			name : "berthName"
			
		},{
			title : "到港时间",
			width: 90,
			name : "arrivalTime",
			render:function(item,name,index){
				if(item.arrivalTime==null||item.arrivalTime==0){
					return "";
				}
				return util.getSubTime(new Date(item.arrivalTime*1000).Format("yyyy-MM-dd HH:mm:ss"),2);
			}
		},{
			title : "开泵时间",
			width: 90,
			name : "openPump",
			render:function(item,name,index){
				if(item.openPump==null||item.openPump==0){
					return "";
				}
				return util.getSubTime(new Date(item.openPump*1000).Format("yyyy-MM-dd HH:mm:ss"),2);
			}
		},{
			title : "停泵时间",
			width: 90,
			name : "stopPump",
			render:function(item,name,index){
				if(item.stopPump==null||item.stopPump==0){
					return "";
				}
				return util.getSubTime(new Date(item.stopPump*1000).Format("yyyy-MM-dd HH:mm:ss"),2);
			}
		},{
			title : "拆管时间",
			width: 90,
			name : "tearPipeTime",
			render:function(item,name,index){
				if(item.tearPipeTime==null||item.tearPipeTime==0){
					return "";
				}
				return util.getSubTime(new Date(item.tearPipeTime*1000).Format("yyyy-MM-dd HH:mm:ss"),2);
			}
		},{
			title : "离港时间",
			width: 90,
			name : "leaveTime",
			render:function(item,name,index){
				if(item.leaveTime==null||item.leaveTime==0){
					return "";
				}
				return util.getSubTime(new Date(item.leaveTime*1000).Format("yyyy-MM-dd HH:mm:ss"),2);
			}
		},{
			title : "接收罐号",
			width: 80,
			name : "tankCode",
			render:function(item,name,index){
				 var testObject=item.tankCode;
				 var tankId=item.tankId;
				var testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					var text=testObject[i];
					if(i>0){
						if(testObject[i]==testObject[i-1]){
							text="&nbsp";
						}
					}
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">  <a href="#/tanklog?tankId='+tankId[i]+'&tankName='+text+'&type=1">'+text+'</a> </td></tr>';
					}else{
						testObjectHtml+='<tr><td> <a href="#/tanklog?tankId='+tankId[i]+'&tankName='+text+'&type=1">'+text+'</a> </td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "前尺液位(毫米)",
			name : "startLevel",
			width: 80,
			render:function(item,name,index){
				 testObject=item.startLevel;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "后尺液位(毫米)",
			width: 80,
			name : "endLevel",
			render:function(item,name,index){
				 testObject=item.endLevel;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "前尺重量(吨)",
			width: 80,
			name : "startWeight",
			render:function(item,name,index){
				 testObject=item.startWeight;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "后尺重量(吨)",
			width: 80,
			name : "endWeight",
			render:function(item,name,index){
				 testObject=item.endWeight;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "前尺温度(℃)",
			width: 80,
			name : "startTemperature",
			render:function(item,name,index){
				 testObject=item.startTemperature;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "后尺温度(℃)",
			width: 80,
			name : "endTemperature",
			render:function(item,name,index){
				 testObject=item.endTemperature;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "机房实收(吨)",
			width: 80,
			name : "realAmount",
			render:function(item,name,index){
				 testObject=item.realAmount;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;" >'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "计量实收(吨)",
			width: 80,
			name : "measureAmount",
			render:function(item,name,index){
				 testObject=item.measureAmount;
				 testObjectHtml='<table class="table inmtable"  style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "实收差异(吨)",
			width: 80,
			name : "differAmount",
			render:function(item,name,index){
				 testObject=item.differAmount;
				 testObjectHtml='<table class="table inmtable"  style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "货主",
			width: 145,
			name : "clientName",
			render:function(item,name,index){
				if(item.clientName!=null){
					return item.clientName.replace(new RegExp(",","gm"),"<br></br>");
					
				}
				else{
					return "";
				}
			}
		},{
			title : "服务评价",
			width: 90,
			name : "evaluate"
		},{
			title : "评价人",
			width: 70,
			name : "evaluateUserName"
		},{
			title : "备注",
			width: 100,
			name : "description"
		},{
			title : "验证时间",
			width: 90,
			name : "notificationTime",
			render:function(item,name,index){
				 testObject=item.notificationTime;
				 testObjectHtml='<table class="table inmtable"  style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "报告号",
			width: 100,
			name : "notificationNum",
			render:function(item,name,index){
				 testObject=item.notificationNum;
				 testObjectHtml='<table class="table inmtable"  style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "验证结论",
			width: 90,
			name : "notification",
			render:function(item,name,index){
				 testObject=item.notification;
				 testObjectHtml='<table class="table inmtable"  style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "验证人",
			width: 80,
			name : "notificationUserName",
			render:function(item,name,index){
				 testObject=item.notificationUserName;
				 testObjectHtml='<table class="table inmtable"  style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		}];
		if($('div[data-role="inBoundBookGrid"]').getGrid()!=null){
			$('div[data-role="inBoundBookGrid"]').getGrid().destory();
		}
		$('div[data-role="inBoundBookGrid"]').grid({
			    identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : false,
				searchCondition:{"startTime": new Date(new Date( $("#startTime").val()).toLocaleDateString()).getTime()/1000,
				    "endTime": (new Date(new Date( $("#endTime").val()).toLocaleDateString()).getTime()+86400000)/1000,
				    "shipId":$("#shipId").attr("data")},
				    
		       url:config.getDomain()+"/inboundserial/loglist",
		       callback:function(){
		    	   $('.inmtable').closest("td").css('padding','0px');
		    	   util.setColumnsVisable($('div[data-role="inBoundBookGrid"]'),undefined,columnCheckArray,function(){columnCheckArray=util.getColumnsCheckArray();});
  
		       }
		});
	};
	
	/**
	 * 查询按钮点击事件
	 */
	var search = function(){
		var statisMonth = $("#startTime").val();
		if(statisMonth == null || statisMonth == "")
		{
			 $("body").message({
				type : 'warning',     
				content : '日期不能为空！'
			});
			return;
		}
//		buildTab();
		
		
		

		var params = {};
            if($("#shipId").attr("data")&&$("#shipId").attr("data")!=""){
            	params["shipId"]=$("#shipId").attr('data');
            }
            params["startTime"]= new Date(new Date( $("#startTime").val()).toLocaleDateString()).getTime()/1000;
            params["endTime"]= (new Date(new Date( $("#endTime").val()).toLocaleDateString()).getTime()+86400000)/1000;
       $('[data-role="inBoundBookGrid"]').getGrid().search(params);
	
		
	};
	
	return {
		init:function(){
			//初始化查询条件
			initCondition();
			buildTab();
		},
		search:function(){
			search();
		}
	}
	
}();