/**
 * 分流台账明细报表
 */

var OutBoundBook= function(){
	var columnCheckArray;
	/**
	 * 初始化查询条件
	 */
	var initCondition = function(){
		util.initDatePicker();
		$("#startTime").val(util.currentTime(0));
		$("#endTime").val(util.currentTime(0));
	};
	
	/**
	 * 形成表格
	 */
	function buildTab(){
		var columns=[{title:'序号',
			width:40,
			render:function(item,name,index){
			return index+1;
		}},{
			title : "船名",
			width:140,
			name : "shipName"
		}, {
			title : "品种",
			width:80,
			name : "productName",
		}, {
			title : "开票数(吨)",
			width:70,
			name : "totalNum"
		},  {
			title : "发货罐号",
			width:70,
			name : "tankName",
				render:function(item,name,index){
					 var testObject=item.outFlowData;
					 if(testObject&&testObject.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<testObject.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i].tankName+'</a></td></tr>';
					    	}
					    html += '</table>';
					    return html;
				
				}else if(testObject&&testObject.length==1){
					return testObject[0].tankName;
				}else{
					return "";
				}
					 }
		}, {
			title : "管线状态",
			width: 60,
			name : "tubeStatus"
		}, {
			title : "管线",
			width:70,
			render:function(item){
				var data=item.tubeNames;
				if(data&&data.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
								+ data[i].tubeName + '</td></tr>';
					    	}
					    html += '</table>';
					    return html;
				}else if(data&&data.length==1){
					return util.isNull(data[0].tubeName);
				}else{return ""};
			}
		}, {
			title : "泊位",
			width:60,
			name : "berthName"
		},{
			title : "到港时间",
			width:90,
			name:"arrivalTime"
		}, {
			title : "开泵时间",
			width:90,
			name:"openPump"
		}, {
			title : "停泵时间",
			width:90,
			name:"stopPump"
		},{
			title : "离港时间",
			width:90,
			name:"leaveTime"
		},{
			title : "计量前尺<br>液位(米)",
			width:90,
			render:function(item,name,index){
				var data=item.outFlowData;
				if(data&&data.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
								+ util.isNull(data[i].startHandLevel) + '</td></tr>';
					    	}
					    html += '</table>';
					    return html;
				}else if(data&&data.length==1){
					return util.isNull(data[0].startHandLevel);
				}else{
					return "";
				}
			}
		},{
			title : "计量后尺<br>液位(米)",
			width:90,
			render:function(item,name,index){
				var data=item.outFlowData;
			if(data&&data.length>1){
				  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
				  for(var i=0;i<data.length;i++){
				    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
							+ util.isNull(data[i].endHandLevel) + '</td></tr>';
				    	}
				    html += '</table>';
				    return html;
			}else if(data&&data.length==1){
				return util.isNull(data[0].endHandLevel);
			}else{
				return "";
			}
			}
		},{
			title : "机房前<br>液位(米)",
			width:90,
			render:function(item,name,index){
				var data=item.outFlowData;
				if(data&&data.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
								+ util.isNull(data[i].startLevel) + '</td></tr>';
					    	}
					    html += '</table>';
					    return html;
				}else if(data&&data.length==1){
					return util.isNull(data[0].startLevel);
				}else{
					return "";
				}
			}
		},{
			title : "机房后<br>液位(米)",
			width:90,
			render:function(item,name,index){
				var data=item.outFlowData;
				if(data&&data.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
								+ util.isNull(data[i].endLevel) + '</td></tr>';
					    	}
					    html += '</table>';
					    return html;
				}else if(data&&data.length==1){
					return util.isNull(data[0].endLevel);
				}else{
					return "";
				}
			}
		},{
			title : "计量前尺<br>重量(吨)",
			width:90,
			render:function(item,name,index){
				var data=item.outFlowData;
				if(data&&data.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
								+ util.isNull(data[i].startHandWeight) + '</td></tr>';
					    	}
					    html += '</table>';
					    return html;
				}else if(data&&data.length==1){
					return util.isNull(data[0].startHandWeight);
				}else{
					return "";
				}
			}
		},{
			title : "计量后尺<br>重量(吨)",
			width:90,
			render:function(item,name,index){
				var data=item.outFlowData;
				if(data&&data.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
								+ util.isNull(data[i].endHandWeight) + '</td></tr>';
					    	}
					    html += '</table>';
					    return html;
				}else if(data&&data.length==1){
					return util.isNull(data[0].endHandWeight);
				}else{
					return "";
				}
			}
		},{
			title : "机房前<br>重量(吨)",
			width:90,
			render:function(item,name,index){
				var data=item.outFlowData;
				if(data&&data.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
								+ util.isNull(data[i].startWeight) + '</td></tr>';
					    	}
					    html += '</table>';
					    return html;
				}else if(data&&data.length==1){
					return util.isNull(data[0].startWeight);
				}else{
					return "";
				}
			}
		},{
			title : "机房后<br>重量(吨)",
			width:90,
			render:function(item,name,index){
				var data=item.outFlowData;
				if(data&&data.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
								+ util.isNull(data[i].endWeight) + '</td></tr>';
					    	}
					    html += '</table>';
					    return html;
				}else if(data&&data.length==1){
					return util.isNull(data[0].endWeight);
				}else{
					return "";
				}
			}
		},{
			title : "前温度(℃)",
			width:70,
			render:function(item,name,index){
				var data=item.outFlowData;
				if(data&&data.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
								+ util.isNull(data[i].startTemperature) + '</td></tr>';
					    	}
					    html += '</table>';
					    return html;
				}else if(data&&data.length==1){
					return util.isNull(data[0].startTemperature);
				}else{
					return "";
				}
			}
		},{
			title : "后温度(℃)",
			width:70,
			render:function(item,name,index){
				var data=item.outFlowData;
				if(data&&data.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
								+ util.isNull(data[i].endTemperature) + '</td></tr>';
					    	}
					    html += '</table>';
					    return html;
				}else if(data&&data.length==1){
					return util.isNull(data[0].endTemperature);
				}else{
					return "";
				}
			}
		},{
			title : "机房(吨)",
			width:90,
			render:function(item,name,index){
				var data=item.outFlowData;
				if(data&&data.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
								+ util.isNull(-data[i].realAmount) + '</td></tr>';
					    	}
					    html += '</table>';
					    return html;
				}else if(data&&data.length==1){
					return util.isNull(-data[0].realAmount);
				}else{
					return "";
				}
				}	
		},{
			title : "计量(吨)",
			width:90,
			render:function(item){
				var data=item.outFlowData;
				if(data&&data.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
								+ util.isNull(-data[i].measureAmount) + '</td></tr>';
					    	}
					    html += '</table>';
					    return html;
				}else if(data&&data.length==1){
					return util.isNull(-data[0].measureAmount);
				}else{
					return "";
				}
				}
		},{
			title : "差异(吨)",
			width:90,
				render:function(item,name,index){
					var data=item.outFlowData;
					if(data&&data.length>1){
						  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
						  for(var i=0;i<data.length;i++){
						    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
									+ util.isNull(-data[i].differAmount) + '</td></tr>';
						    	}
						    html += '</table>';
						    return html;
					}else if(data&&data.length==1){
						return util.isNull(-data[0].differAmount);
					}else{
						return "";
					}
					}
		},{title:"去向",width:70,name:"outboundPlace"},{
			title : "计量人员",
			width:70,
			name : "user1"
		},{
			title : "复核人员",
			width:70,
			name : "user2"
		},{
			title : "货主",
			width:140,
			render:function(item){
				return util.isNull(item.clientName[0].clientName);
			}
		},{
			title : "服务评价",
			width:100,
			name : "evaluate"
		},{
			title : "评价人",
			width:70,
			name : "evaluateUserName"
		},{
			title : "审核状态",
			width:70,
			name : "status"
		},{
			title : "备注",
			width:100,
			name : "comment"
		},{
			title:"平均速度<br>（吨/小时）",
			width:80,
			render:function(item){
				var data=item.outFlowData;
				var total=0
				if(data&&data.length>0){
					  for(var i=0;i<data.length;i++){
					    	total=util.FloatAdd(total,data[i].realAmount);
					    	}
				}
				return -util.FloatDiv(total,util.getDifferTime(item.openPump+":00",item.stopPump+':00'),3);
			}
		},{
			title:"在港时间<br>（小时）",
			width:70,
			render:function(item){
				return util.getDifferTime(item.arrivalTime+":00",item.leaveTime+':00');
			}
		},{
			title:"作业时间<br>（小时）",
			width:70,
			render:function(item){
				return util.getDifferTime(item.openPump+":00",item.stopPump+':00');
			}
		}];
		if($('div[data-role="outBoundBookGrid"]').getGrid()!=null){
			$('div[data-role="outBoundBookGrid"]').getGrid().destory();
		}
		$('div[data-role="outBoundBookGrid"]').grid({
			    identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : true,
				searchCondition:{"startTime": $("#startTime").val(),
				    "endTime": $("#endTime").val(),
				    "shipName":$("#shipName").val()},
		       url:config.getDomain()+"/report/outboundbooklist",
		       callback:function(){
		    	   $('.inmtable').closest("td").css('padding','0px');
		    	   util.setColumnsVisable($('div[data-role="outBoundBookGrid"]'),undefined,columnCheckArray,function(){columnCheckArray=util.getColumnsCheckArray();});
  
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
		buildTab();
	};
	
	return {
		init:function(){
			//初始化查询条件
			initCondition();
		},
		search:function(){
			search();
		}
	}
	
}();