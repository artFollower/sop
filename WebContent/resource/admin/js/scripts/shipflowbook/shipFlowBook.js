//分流台账
var ShipFlowBook = function() {
	Date.prototype.format =function(format){
		var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
		};
		if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
		(this.getFullYear()+"").substr(4- RegExp.$1.length));
		for(var k in o)if(new RegExp("("+ k +")").test(format))
		format = format.replace(RegExp.$1,
		RegExp.$1.length==1? o[k] :
		("00"+ o[k]).substr((""+ o[k]).length));
		return format;
		};
	
	//获取列表详情
	function init(startTime,endTime){
		var columns= [{
			title : "船名",width: 100,render:function(item){
				return '<a href="#/updateshipflowbooklog?id='+item.arrivalId+'">'+item.shipName+'</a>' ;
			}},{title : "品种",width: 100,name : "productName"},{title : "开票数(吨)",width: 100,name : "totalNum"
			},{title : "发货罐号",width: 100,render:function(item){
					 var testObject=item.outFlowData;
					 if(testObject&&testObject.length>1){
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var i=0;i<testObject.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;"><a href="#/tanklog?tankId='+testObject[i].tankId+'&tankName='+testObject[i].tankName+'&type=1">'+testObject[i].tankName+'</a></td></tr>';
					    	}
					    html += '</table>';
					    return html;
				
				}else if(testObject&&testObject.length==1){
					return '<a href="#/tanklog?tankId='+testObject[0].tankId+'&tankName='+testObject[0].tankName+'&type=1">'+testObject[0].tankName+'</a>';
				}else{
					return "";
				}
			}},{title : "管线状态",width: 100,render:function(item){
					   var  val ="" ;
					   if(item.tubeStatus){
					   if(item.tubeStatus==1){
						   val = "充满" ;
					   }else if(item.tubeStatus==2){
						   val = "局部空" ;
					   }else if(item.tubeStatus==3){
						   val = "全空" ;
					   }}
	                   return val;
			}}, {title : "管线",width: 100,render:function(item){
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
			}},{title : "泊位",width: 100,name : "berthName"},{title : "到港时间",width: 100,render:function(item){
				return util.getSubTime(item.arrivalTime,2);
			}},{title : "开泵时间",width: 100,render:function(item){
				return util.getSubTime(item.openPump,2);
			}},{title : "停泵时间",width: 100,render:function(item){
				return util.getSubTime(item.stopPump,2);
			}},{title : "离港时间",width: 100,render:function(item){
				return util.getSubTime(item.leaveTime,2);
			}},{title : "计量前尺液位(毫米)",width: 110,render:function(item){
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
			}},{title : "计量后尺液位(毫米)",width: 110,name : "endHandLevel",render:function(item){
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
			}},{title : "机房前液位(毫米)",width: 100,render:function(item){
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
		},{title : "机房后液位(毫米)",width: 100,render:function(item){
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
		}},{title : "计量前尺重量(吨)",width: 110,render:function(item){
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
		}},{title : "计量后尺重量(吨)",width: 110,render:function(item){
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
		}},{title : "机房前重量(吨)",width: 100,render:function(item){
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
		},{title : "机房后重量(吨)",width: 100,render:function(item){
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
		}},{title : "前温度(℃)",width: 100,render:function(item){
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
		}},{title : "后温度(℃)",width: 100,render:function(item){
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
		}},{title : "机房(吨)",width: 100,render:function(item){
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
		}},{title : "计量(吨)",width: 100,render:function(item){
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
		}},{title : "差异(吨)",width: 100,render:function(item){
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
		}},{title : "计量人员",width: 100,name : "user1"},{title : "复核人员",width: 100,name : "user2"
		},{title : "货主",width: 100,render:function(item){
				var clientName='';
				if(item.clientName&&item.clientName.length>0){
					for(var i=0;i<item.clientName.length;i++){
						clientName+=item.clientName[i].clientName+",";
					}
					clientName=clientName.substring(0,clientName.length-1);
				}
				return util.isNull(clientName);
			}
		},{title : "服务评价",width: 100,render:function(item){
			return util.isNull(item.evaluate);
		}},{title : "评价人",width: 100,name : "evaluateUserName"
		},{title : "审核状态",width: 100,render:function(item){
				if(item.status==54){return "数量已确认" ;
				}else{return "数量未确认" ;}
		}},{title : "备注",width: 100,name : "comment"}];
		
		$('[data-role="shipflowbook"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain()+"/outboundserial/getshipflowlist?startTime="+startTime+"&endTime="+endTime,
			callback:function(){
				$('.inmtable').closest("td").css('padding','0px');
			}
		});
		
	};
	//编辑分流台账
	function initEdit(id){
		initEditCtr(id);
		initEditMsg(id);
	}
	function initEditCtr(id){
		util.initTimePicker($(".shipFlowInfo"));//初始化时间控件
		//获取用户信息
		$.ajax({
			url:config.getDomain()+"/auth/user/get?pagesize=0&category=JOB",
			dataType:'json',
			success : function(data) {
				config.unload();
				util.ajaxResult(data,'获取用户',function(ndata){
					if(ndata&&ndata.length>0){
						var msgM=new Array();
						var msgR=new Array();
						for(var i=0,len=ndata.length;i<len;i++){
							if(ndata[i].sn=="JL")
							{
								msgM.push({id:ndata[i].id,name:ndata[i].name});
							}
							if(ndata[i].sn=="DD")
							{
								msgR.push({id:ndata[i].id,name:ndata[i].name});
							}
						}
						util.handleTypeaheadAllData(msgM,$("#measureUserId")); 
						util.handleTypeaheadAllData(msgR,$("#reviewUserId")); 
					}
				},true);
			}
		});
		util.handleTypeahead([{id:1,name:"合格"},{id:0,name:"不合格"}],$("#evaluate"),undefined,undefined,true);
		//保存
		$("#save").click(function(){
			
			var work={
				"id":util.isNull($("#workId").text(),1),
				"openPump":util.formatLong(util.getTimeVal($(".openPump"))),
				"stopPump":util.formatLong(util.getTimeVal($(".stopPump"))),
				"arrivalTime":util.formatLong(util.getTimeVal($(".arrivalTime"))),
				"leaveTime":util.formatLong(util.getTimeVal($(".leaveTime"))),
				"evaluate":$("#evaluate").val(),
				"description":$("#comment").val(),
				"evaluateUser":util.isNull($("#evaluateUser").val()),
				"arrivalId":id
			};
			var  checkArray=new Array();
			checkArray.push({
				"id":util.isNull($("#measureCheckId").text(),1),
				"checkType":21,	
				"checkUserId":$("#measureUserId").attr('data'),
				"transportId":util.isNull($("#transportId").text(),1)
			});
			checkArray.push({
				"checkType":22,	
				"checkUserId":$("#reviewUserId").attr('data'),
				"transportId":util.isNull($("#transportId").text(),1)	
			});
			var deliveryShip={
				"transportId":util.isNull($("#transportId").text(),1),
				"tubeStatus":$("#tubeStatus").val()
			};
			var shipFlow={
					'work':work,
					'workCheckList':checkArray,
					'arrivalId':id,
					'deliveryShip':deliveryShip
			};
			$.ajax({
				 type:'post',
				 url:config.getDomain()+'/outboundserial/saveshipflow',
				 dataType:'json',
				 data:{shipFlow:JSON.stringify(shipFlow)},
				 success:function(data){
					 util.ajaxResult(data,'保存',function(){
						 initEditMsg(id); 
					 });
				 }
			});
		});
	};
	function initEditMsg(id){
		if(util.isNull(id,1)!=0){
			config.load();
			$.ajax({
			 type:'post',
			 url:config.getDomain()+'/outboundserial/getshipflowinfo',
			 dataType:'json',
			 data:{arrivalId:id},
			 success:function(data){
				 util.ajaxResult(data,'获取分流台账信息',function(ndata){
					 if(ndata&&ndata.length>0){
						 var itemMsg=ndata[0];
						 $("#workId").text(itemMsg.workId);
						 $("#transportId").text(itemMsg.transportId);
						 $("#shipName").val(itemMsg.shipRefName).attr('data',itemMsg.shipId);
						 $("#productName").val(itemMsg.productName).attr('data',itemMsg.productId);
						 $("#totalNum").val(util.toDecimal3(itemMsg.totalNum,true));
						 if(util.validateFormat(itemMsg.arrivalTime)){
						 util.initTimeVal($(".arrivalTime"),itemMsg.arrivalTime) ;
							}else{
						 util.initTimeVal($(".arrivalTime"),itemMsg.arrivalStartTime);
							}
						if(util.validateFormat(itemMsg.leaveTime))
						util.initTimeVal($(".leaveTime"),itemMsg.leaveTime);
						if(util.validateFormat(itemMsg.openPump))
						util.initTimeVal($(".openPump"),itemMsg.openPump);
						if(util.validateFormat(itemMsg.stopPump))
						util.initTimeVal($(".stopPump"),itemMsg.stopPump);
						$(".timepicker-24:focus").blur();//初始化使获得焦点的失去焦点	
						$("#evaluateUser").val(util.isNull(itemMsg.evaluateUser));
						$("#evaluate").val(util.isNull(itemMsg.evaluate));
						$("#comment").val(util.isNull(itemMsg.description));	
						$("#tubeStatus").val(util.isNull(itemMsg.tubeStatus));
						if(itemMsg.checkMsg&&itemMsg.checkMsg.length>0){
							for(var i=0;i<itemMsg.checkMsg.length;i++){
								if(util.isNull(itemMsg.checkMsg[i].checkType,1)==21){
									$("#measureUserId").attr('data',itemMsg.checkMsg[i].checkUserId).val(itemMsg.checkMsg[i].checkUserName);
									$("#measureCheckId").text(itemMsg.checkMsg[i].id);
								}else if(util.isNull(itemMsg.checkMsg[i].checkType,1)==22){
									$("#reviewUserId").attr('data',itemMsg.checkMsg[i].checkUserId).val(itemMsg.checkMsg[i].checkUserName);
									$("#reviewCheckId").text(itemMsg.checkMsg[i].id);
								}
							}
						}
						if(itemMsg.tubeName&&itemMsg.tubeName.length>0){
							var tubeNames='';
							for(var i=0;i<itemMsg.tubeName.length;i++){
								tubeNames+=itemMsg.tubeName[i].tubeName+",";
							}
							if(tubeNames.length>1){
								$("#tubes").val(tubeNames.substring(0,tubeNames.length-1));
							}
						}
					 }
				 },true);
			 }
			});
		}
	};
	return {
		init :init,
		initEdit:initEdit
	}
}();