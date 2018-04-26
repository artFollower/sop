/**方案管理*/
var PlanManager=function(){
	var systemUserId;
	var systemUserName;
	//初始化管理
	function init(item){
		$("#planType").val(item);
		initBtn();//初始化控件
   		if(item==1){//靠泊方案
   			$('.unloadingDiv').hide();
   			initBerthPlan();
   		}else if(item==2){//接卸方案
   			initUnloadingPlan();
   		}else if(item==3){//倒罐方案
   			$(".addChangeTank").show();
   			$(".addBackFlow,.shipDiv").hide();
   			initBackFlowPlan(3);
   		}else if(item==4){//打循环
   			$(".addChangeTank").hide();
   			$(".addBackFlow").show();	
   			initBackFlowPlan(1);
   		}
	};
	//初始化列表控件
	function initBtn(){
		initFormIput();
		util.urlHandleTypeahead("/product/select",$('#productId'));
		util.urlHandleTypeahead("/berth/list",$('#berthId'));
		//搜索
		$(".roleManagerQuery").click(function(){
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$("#searchPlan").click(function(){
			var params={
					type:$("#type").text(),
					shipName:$('#shipId').val(),
					productId:$("#productId").attr('data'),
					berthId:$("#berthId").attr('data'),
					startTime:util.formatLong($("#startTime").val()+" 00:00:00"),
					endTime:util.formatLong($("#endTime").val()+" 23:59:59"),
					status:$('#status').val(),
					item:$("#planType").val()
			};
			$('[data-role="managerGrid"]').getGrid().search(params);
		});
		$("#reset").click(function(){
			$("#shipId,#berthId,#productId,#startTime,#endTime").val("").attr("data","");
			$("#status").val(-1);
			$("#searchPlan").click();
		});
		util.initDatePicker();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/initialfee/getsystemuser",
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取系统信息',function(ndata){
					systemUserId=ndata[0].userId;
					systemUserName=ndata[0].userName;
				},true);
			}
		});
	}
	//初始化靠泊方案
	function initBerthPlan(){
		var columns=[{title:'制定日期',name:"createTime"},{title:'类型',render:function(item){
			return  (item.arrivalType==1||item.arrivalType==3)?"入库":"出库";
		}},{title:"船名",name:"shipName"},{title:"到港日期",name:"arrivalTime"},
		             {title:"泊位",name:'berthName'},{title:"货品",name:'productName'},
		     		{title:"计划量(吨)",name:"count"},
		     		{title:"开票量(吨)",name:"count_plan"},
		     		{title:"状态",render:function(item){
		            	 if(item.status||item.status==0){
		            		 if(item.status==0){ return "<label style='color:#666699;font-weight:bold;'>未提交</label>";}
		            		 else if(item.status==1){return "<label style='color:#99CC33;font-weight:bold;'>审核中</label>";}
		            		 else if(item.status==2){return "<label style='color:#FF9966;font-weight:bold;'>已完成</label>";}
		            	 }
		            	 
		             }},{title:'操作',render:function(item){
		            	 var html='<div style="width:60px;" class="input-group-btn">';
		            	 if(item.arrivalType==1||item.arrivalType==3){//入库
		            		html+='<a href="#/inboundoperation/get?state=2&arrivalId='+item.arrivalId+'" style="margin:0 2px;" class="btn btn-xs blue" >'
		            		+'<span class="glyphicon glyphicon-eye-open" title="查看"></span></a>'; 
		            	 }else if(item.arrivalType==2){//出库
		            		 html+='<a href="#/outboundserial/get?status=51&id='+item.arrivalId+'&startTime='+util.getSubTime(item.createTime,1)+'" style="margin:0 2px;" class="btn btn-xs blue" >'
			            		+'<span class="glyphicon glyphicon-eye-open" title="查看"></span></a>';
		            	 }
		            	 html+='<a href="javascript:void(0)" onclick="PlanManager.exportBerthProgram('+item.arrivalId+')" style="margin:0 2px;" class="btn btn-xs blue" >'
		            		 +'<span class="fa fa-file-excel-o" title="导出"></span></a></div>';
		            	 return html;
		             }}];
		if($('[data-role="managerGrid"]').getGrid()!=null){
			$('[data-role="managerGrid"]').getGrid().destory();
		}
		$('[data-role="managerGrid"]').grid({
			    identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : true,
				gridName:"berthPlanManager",
				stateSave:true,
				backCallBack:function(data){
					if(data.item==1){//靠泊方案
			   			$('.unloadingDiv').hide();
			   		}else if(data.item==2){//接卸方案
			   		}else if(data.item==3){//倒罐方案
			   			$(".addChangeTank").show();
			   		}else if(data.item==4){//打循环
			   			$(".addChangeTank").hide();
			   			$(".addBackFlow").show();			
			   		}
				},
		       url:config.getDomain()+"/planmanager/berthplanlist" 
		});
	};
	//初始化接卸方案
	function initUnloadingPlan(){
		var columns=[{title:'制定日期',name:"createTime"},{title:"船名",name:"shipName"},{title:"到港日期",name:"arrivalTime"},{title:"泊位",name:"berthName"},{title:"货品",name:'productName'},
		{title:"数量(吨)",name:"count"},{title:"罐号",name:"tank"},{title:"管线号",name:"tube"},
		{title:"状态",render:function(item){
		            	 if(item.status||item.status==0){
		            		 if(item.status==0){ return "<label style='color:#666699;font-weight:bold;'>未提交</label>";}
		            		 else if(item.status==1){return "<label style='color:#99CC33;font-weight:bold;'>审核中</label>";}
		            		 else if(item.status==4){return "<label style='color:#99CC33;font-weight:bold;'>品质审核通过</label>";}
		            		 else if(item.status==5){return "<label style='color:#99CC33;font-weight:bold;'>工艺审核通过</label>";}
		            		 else if(item.status==2){return "<label style='color:#FF9966;font-weight:bold;'>已完成</label>";}
		            	 }
		             }},{title:'操作',render:function(item){
		            	 if(item.arrivalType==1){//入库
		            		 var html='<div style="width:100px;" class="input-group-btn">';
		            		 if(item.refName&&item.refName=='转输'){
		            			  
		            			 html+='<a href="#/inboundzhuanshu/get?state=3&productId='+item.productId+'&arrivalId='+item.arrivalId+'&orderNum='+item.orderNum+'&isTransport=2" style="margin:0 2px;" class="btn btn-xs blue" >'
				            		+'<span class="glyphicon glyphicon-eye-open" title="查看"></span></a>'; 
		            		 }else{
		            			 html+='<a href="#/inboundoperation/get?state=3&productId='+item.productId+'&arrivalId='+item.arrivalId+'&orderNum='+item.orderNum+'&isTransport=1" style="margin:0 2px;" class="btn btn-xs blue" >'
				            		+'<span class="glyphicon glyphicon-eye-open" title="查看"></span></a>';  
		            		 }
		            		 html+='<a href="javascript:void(0)" onclick="PlanManager.exportTransportProgram('+item.id+')" style="margin:0 2px;" class="btn btn-xs blue" >'
		            		 +'<span class="fa fa-file-excel-o" title="导出"></span></a></div>'
		            		 return html;
		            	 }else if(item.arrivalType==2){//出库
		            		 return '<div style="width:60px;" class="input-group-btn">'
			            		+'<a href="#/outboundserial/get?status=52&id='+item.arrivalId+'&startTime='+util.getSubTime(item.createTime,1)+'" style="margin:0 2px;" class="btn btn-xs blue" >'
			            		+'<span class="glyphicon glyphicon-eye-open" title="查看"></span></a></div>';
		            	 }
		             }}];
		if($('[data-role="managerGrid"]').getGrid()!=null){
			$('[data-role="managerGrid"]').getGrid().destory();
		}
		$('[data-role="managerGrid"]').grid({
			    identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : true,
				gridName:"UnloadingPlanManager",
				stateSave:true,
				backCallBack:function(data){
					if(data.item==1){//靠泊方案
			   			$('.unloadingDiv').hide();
			   		}else if(data.item==2){//接卸方案
			   		}else if(data.item==3){//倒罐方案
			   			$(".addChangeTank").show();
			   		}else if(data.item==4){//打循环
			   			$(".addChangeTank").hide();
			   			$(".addBackFlow").show();			
			   		}
				},
		       url:config.getDomain()+"/planmanager/unloadingplanlist" 
		});
	};
	//初始化倒罐方案
	function initBackFlowPlan(type){
		$("#type").text(type);
		var columns=[{title:'制定日期',name:"createTime"},{title:"船名",name:"shipName"},{title:"到港日期",name:"arrivalTime"},{title:"货品",name:'productName'},
		{title:"数量(吨)",name:"count"},{title:"倒出罐号",name:"outTank"},
		{title:"倒入罐号",name:"inTank"},{title:"管线",name:"tube"},{title:"泵号",name:"pump"},
		{title:"状态",render:function(item){
		            	 if(item.status||item.status==0){
		            		 if(item.status==0){ return "<label style='color:#666699;font-weight:bold;'>未提交</label>";}
		            		 else if(item.status==1){return "<label style='color:#99CC33;font-weight:bold;'>审核中</label>";}
		            		 else if(item.status==4){return "<label style='color:#99CC33;font-weight:bold;'>品质审核通过</label>";}
		            		 else if(item.status==5){return "<label style='color:#99CC33;font-weight:bold;'>工艺审核通过</label>";}
		            		 else if(item.status==2){return "<label style='color:#FF9966;font-weight:bold;'>已完成</label>";}
		            	 }
		             }},{title:'操作',render:function(item){
		            	 if(item.arrivalId){//入库
		            		return '<div style="width:60px;" class="input-group-btn">'
		            		+'<a href="#/inboundoperation/get?state=5&productId='+item.productId+'&arrivalId='+item.arrivalId+'&backflowId='+item.id+'" style="margin:0 2px;" class="btn btn-xs blue" >'
		            		+'<span class="glyphicon glyphicon-eye-open" title="查看"></span></a><a href="javascript:void(0)" onclick="PlanManager.exportChangeTankProgram('+item.id+',1)" style="margin:0 2px;" class="btn btn-xs blue" >'
		            		 +'<span class="fa fa-file-excel-o" title="导出"></span></a></div>'; 
		            	 }else{
		            		 
		            		 var url=(type==1?'#/backflowplan/getbackflow?':'#/changetankplan/getchangetank?')
		            		  var html='<div style="width:60px;" class="input-group-btn">'
			            		+'<a href="'+url+'id='+item.id+'&status='+item.status+'" style="margin:0 2px;" class="btn btn-xs blue" >'
			            		+'<span class="glyphicon glyphicon-eye-open" title="查看"></span></a>'
			            		;
		            		  if(item.status!=2){
		            		  html+='<a href="javascript:void(0)" style="margin:0 2px;" onclick="PlanManager.deleteBackFlow('+item.id+')" class="btn btn-xs red " >'
			            		+'<span class="glyphicon glyphicon-remove" title="删除"></span></a>';
		            		  }
		            		  html+='<a href="javascript:void(0)" onclick="PlanManager.exportChangeTankProgram('+item.id+','+(type==1?1:0)+')" style="margin:0 2px;" class="btn btn-xs blue" >'
			            		 +'<span class="fa fa-file-excel-o" title="导出"></span></a></div>';
			            	return html;
		            	 }
		             }}];
         if($('[data-role="managerGrid"]').getGrid()!=null){
			$('[data-role="managerGrid"]').getGrid().destory();
		}
		$('[data-role="managerGrid"]').grid({
			    identity : 'id',
				columns : columns,
				searchCondition:{type:type},
				isShowIndexCol : false,
				isShowPages : true,
				gridName:(type==3?"BackFlowManager":"backFlowFManger"),
				stateSave:true,
				backCallBack:function(data){
					if(data.item==1){//靠泊方案
			   			$('.unloadingDiv').hide();
			   		}else if(data.item==2){//接卸方案
			   		}else if(data.item==3){//倒罐方案
			   			$(".addChangeTank").show();
			   		}else if(data.item==4){//打循环
			   			$(".addChangeTank").hide();
			   			$(".addBackFlow").show();			
			   		}
				},
		       url:config.getDomain()+"/planmanager/backflowlist",
		       callback:function(){
		    	   if(type==3)
		    		   $('[data-role="managerGrid"]').find("th[index=1],td[index=1],th[index=2],td[index=2]").hide();
		       }
		});
	}; 	
	
	function initBackFLow(id,status,type){
		var obj=$(".backflowDiv");
		initBackFlowControl(obj,type,id);
		initBackFlowVal(obj,id,status);
		$.ajax({
			type:'post',
			url:config.getDomain()+"/initialfee/getsystemuser",
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取系统信息',function(ndata){
					systemUserId=ndata[0].userId;
					systemUserName=ndata[0].userName;
				},true);
			}
		});
	}
	//初始化按钮
	function initBackFlowControl(obj,type,id){
		util.initTimePicker(obj);
		$(obj).find(".bf").hide();
		if(!id&&type){
			if(type==1){
				$(obj).find("#isBackFlow").attr("checked",true);	
			}else if(type==3){
				$(obj).find("#isBackFlow").attr("checked",false);	
			}
			isShowBackFlow(obj);
		}
		util.urlHandleTypeahead("/auth/user/getUserByPermission?permission="+$(obj).find(".reviewCodeUserId").attr("code"),$(obj).find(".reviewCodeUserId"));
		
		util.urlHandleTypeahead("/product/select",$(obj).find('#productId'),undefined,undefined,undefined,function(data){
			if(data){
			$(obj).find("#flowmessage,#intankIds,#outtankIds,#tubeIds,#pupmIds").val("");
			$(obj).find("#intankIds,#outtankIds,#tubeIds,#pupmIds").attr('data','');
			graphCleanCache();
			$(obj).find("#graphContainer,#toolbarContainer").empty();
			
			if($(obj).find("#isBackFlow").is(":checked")){
				flow(null,true,true,obj,false,true,1,data.id,data.name);
			}else{
				flow(null,true,true,obj,true,true,1,data.id,data.name,false);
			}
			}
		});
		
		$(obj).find("#flowmessage").change(function(){
				if(util.FloatSub($(obj).find("#flowmessage").val(),$(obj).find("#tankCount").val())>=0){
					$(obj).find("#checkMsg").show();
				}else{
					$(obj).find("#checkMsg").hide();
				}
			});
		
		//是否是打循环
		$(obj).find("#isBackFlow").click(function(){
			isShowBackFlow(obj);
			});
	
		//按钮
		$(obj).find(".save,.reback").click(function(){
			$this=$(this);
			var status=$this.attr("key");//操作状态
			var currentStatus=util.isNull($(obj).find("#currentStatus").text());//当前状态
			var securityCode=$this.attr("code");//权限
			var securityCodeMsg=$this.closest(".form-horizontal").find("#securityCode").val();
			if(validateStatus(obj,status,currentStatus,securityCode)){
				doSubmit(obj,status,currentStatus,securityCode,securityCodeMsg);
			}
		});
		
		$(obj).find("#savePumpTime").click(function(){
			config.load();
			var tpId=util.isNull($(obj).find("#id").text(),1);
			$.ajax({
				type:'post',
				url:config.getDomain()+'/inboundoperation/updatetransportprogram',
				data:{
					"id":tpId,
					"type":$(obj).find("#type").attr('data'),
					"openPumpTime":util.formatLong(util.getTimeVal($(obj).find(".openPumpTime"))),
					"stopPumpTime":util.formatLong(util.getTimeVal($(obj).find(".stopPumpTime")))
				},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'保存',function(){
						 window.location.href = (type==1?'#/backflowplan/getbackflow':'#/changetankplan/getchangetank')+"?id="+tpId+"&status=0";						
					});
				}
			});
			
		
		});
		
		//校验
		$(obj).find("#flowmessage").change(function(){
			if(util.FloatSub($(obj).find("#flowmessage").val(),$(obj).find("#tankCount").val())>=0){
				$(obj).find("#checkMsg").show();
			}else{
				$(obj).find("#checkMsg").hide();
			}
		});
	};
	
	function isShowBackFlow(obj){
		graphCleanCache();
		$(obj).find("#flowmessage,#intankIds,#outtankIds,#tubeIds,#pupmIds").val("");
		$(obj).find("#intankIds,#outtankIds,#tubeIds,#pupmIds").attr('data','');
		$(obj).find("#graphContainer,#toolbarContainer").empty();
		if($(obj).find("#isBackFlow").is(":checked")){//
			$(obj).find("#dockBackFlowNotice,#storeBackFlowNotice,.bf").show();
			$(obj).find("#backFlowNotice,.ct").hide();
			$(obj).find("#type").attr('data',1);//打循环
			if($(obj).find("#productId").attr('data')&&$(obj).find("#productId").attr('data')!=0)
			flow(null,true,true,obj,false,true,1,$(obj).find("#productId").attr('data'),$(obj).find("#productId").val());
		}else{
			$(obj).find("#dockBackFlowNotice,#storeBackFlowNotice,.bf").hide();
			$(obj).find("#backFlowNotice,.ct").show();
			$(obj).find("#type").attr('data',3);//倒罐
			if($(obj).find("#productId").attr('data')&&$(obj).find("#productId").attr('data')!=0)
			flow(null,true,true,obj,true,true,1,$(obj).find("#productId").attr('data'),$(obj).find("#productId").val(),false);
		}
		obj.find(".dialog-warning5,.dialog-warning6").attr("hidden",true).removeAttr("style");
	}
	
	
	
	 function validateStatus(obj,status,currentStatus,securityCode) {
		var isPass=true;
		if(status==0||status==1){
			if(!config.validateForm($("#backflow-form"))) {
				isPass = false;
			}
			if(util.FloatSub($(obj).find("#flowmessage").val(),$(obj).find("#tankCount").val())<0){
				$('body').message({
					type : 'warning',
					content : '罐空容不足'
				});
				isPass = false;
			}
			if(!config.validateForm($(obj).find(".tankCountDiv"))){
				isPass = false;
			}
			if(!validateSVG()){
				$('body').message({
					type:'warning',
					content:'工艺流程未填写完整'
				});
				isPass = false;
			}
		}
		return isPass;
	};
	
	function doSubmit(obj,status,currentStatus,securityCode,securityCodeMsg) {
		var msg='';
		var tpId=util.isNull($(obj).find("#id").text(),1);
		var type=util.isNull($(obj).find("#type").attr('data'),1);
		var data={
				"id":tpId,
				"type":type,
				"productId":$(obj).find('#productId').attr('data'),
		        "status":status	
		}
		if(status==-1){
			msg="回退";
		}else if(status==0||status==1){
			msg=(status==0?'保存':'提交');
			data.flow=getImgXml();
			data.svg=getSVG();
			data.createUserId=systemUserId;
			data.createTime=util.formatLong(util.currentTime(1));
		}else if(status==2||status==3){//直接通过或者不通过
			msg=(status==2?'审核':'驳回');
			if(currentStatus==4){
				data.reviewUserId=systemUserId;
				data.reviewTime=util.formatLong(util.currentTime(1));
			}else if(currentStatus==5){
				data.reviewCraftUserId=systemUserId;
				data.reviewCraftTime=util.formatLong(util.currentTime(1));
			}else{
				data.reviewUserId=systemUserId;
				data.reviewTime=util.formatLong(util.currentTime(1));
				data.reviewCraftUserId=systemUserId;
				data.reviewCraftTime=util.formatLong(util.currentTime(1));
			}
		}else if(status==4){//品质通过
			msg='品质审核';
			if(currentStatus==5) data.status=2;
			data.reviewUserId=systemUserId;
			data.reviewTime=util.formatLong(util.currentTime(1));
		}else if(status==5){//工艺通过
			msg='工艺审核';
			if(currentStatus==4) data.status=2;
			data.reviewCraftUserId=systemUserId;
			data.reviewCraftTime=util.formatLong(util.currentTime(1));
		}
		status=data.status;
		//验证码
  	   if(securityCode&&securityCode.length>1){
 			   data.securityCode=securityCodeMsg;
 			   data.isSecurity=1;
 			   data.object=securityCode;
  	   }else{  data.isSecurity=0;}
		
  	   var url=config.getDomain()+(tpId==0?"/planmanager/addtransportprogram":"/inboundoperation/updatetransportprogram");
		config.load();
		$.ajax({
		type:'post',
		url:url,
		data:data,
		dataType:'json',
		success:function(data){
			if(!tpId){
				tpId=data.map.id;
			}
			util.ajaxResult(data,msg,function(){
				if(status==0||status==1){
				var infoId=util.isNull($(obj).find("#infoId").val(),1);
				var infoUrl=config.getDomain()+(infoId==0?"/inboundoperation/addtransportinfo":"/inboundoperation/updatetransportinfo");
				$.ajax({
					type:'post',
					url:infoUrl,
					data:{'id':infoId,'transportId':tpId,
						'message':$(obj).find("#flowmessage").val(),
						'inTankNames':$(obj).find("#intankIds").val(),
						'inTankIds':$(obj).find("#intankIds").attr("data"),
						'outTankNames':$(obj).find("#outtankIds").val(),
						'outTankIds':$(obj).find("#outtankIds").attr("data"),	
						'tubeNames':$(obj).find("#tubeIds").val(),
						'tubeIds':$(obj).find("#tubeIds").attr("data"),	
						'pupmNames':$(obj).find("#pupmIds").val(),
						'pupmIds':$(obj).find("#pupmIds").attr("data"),	
						'tankCount':$(obj).find("#tankCount").val(),
						'description':$(obj).find("#description").val(),
						'transferPurpose':$(obj).find("#transferPurpose").val()},
				   dataType:'json',
				   success:function(data){
					   util.ajaxResult(data,msg,function(){
						   window.location.href = (type==1?'#/backflowplan/getbackflow':'#/changetankplan/getchangetank')+"?id="+tpId+"&status="+status;
					   },true);  
				   }});
				}else{
					 window.location.href = (type==1?'#/backflowplan/getbackflow':'#/changetankplan/getchangetank')+"?id="+tpId+"&status="+status;	
				}
			});
		}
		});
	};
	
	
	
	//初始化数据
	function initBackFlowVal(obj,id,status){
		obj.find(".dialog-warning5,.dialog-warning6").attr("hidden",true).removeAttr("style");
		if(id){
			$(obj).find(".createBtn,.createDiv,.reviewCraftDiv,.reviewDiv,.reviewBtn,.securityCodeDiv,.rebackBtn").hide();
		$(obj).find("#id").text(id);
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain()+"/inboundoperation/list",
			data : {"id":id,
				"transportIds" :id,
				"result":4},
			dataType : "json",
			success:function(data){
				util.ajaxResult(data,'获取信息',function(ndata){
					if(ndata&&ndata.length>0){
					var mdata=ndata[0];
					$(obj).find("#outtankIds").val(mdata.outTankNames).attr("data",mdata.outTankIds);
					$(obj).find("#intankIds").val(mdata.inTankNames).attr("data",mdata.inTankIds);
					$(obj).find("#pupmIds").val(mdata.pupmNames).attr("data",mdata.pupmIds);
					$(obj).find("#transferPurpose").val(util.isNull(mdata.transferPurpose));
					if(util.isNull(mdata.productId,1)!=0){
						$(obj).find("#productId").val(mdata.productName);
						$(obj).find("#productId").attr("data",mdata.productId);
					}
					if(mdata.status!=0&&mdata.status!=3)
						$(obj).find("#productId,#description,#transferPurpose").attr("disabled","disabled");
					
					$(obj).find("#tankCount").val(mdata.tankCount);
					$(obj).find("#tubeIds").val(mdata.infotubeNames);
					$(obj).find("#tubeIds").attr("data",mdata.infotubeIds);
					$(obj).find("#type").attr("data",mdata.type);
					$(obj).find("#description").val(util.isNull(mdata.description));
					util.initTimeVal($(obj).find(".openPumpTime"),mdata.openPumpTime);	
					util.initTimeVal($(obj).find(".stopPumpTime"),mdata.stopPumpTime);	
					if(mdata.type==1){
					$(obj).find("#dockBackFlowNotice").attr("data",mdata.noticeCodeA);
					$(obj).find("#storeBackFlowNotice").attr("data",mdata.noticeCodeB);
					$(obj).find("#isBackFlow").attr('checked',true);
					$(obj).find("#dockBackFlowNotice,#storeBackFlowNotice").show();
					$(obj).find("#backFlowNotice").hide();
					}else if(mdata.type==3){
						$(obj).find("#backFlowNotice").attr("data",mdata.noticeCodeA);
						$(obj).find("#isBackFlow").attr('checked',false);
						$(obj).find("#dockBackFlowNotice,#storeBackFlowNotice").hide();
						$(obj).find("#backFlowNotice").show();
					}
					$(obj).find("#isBackFlow").attr('disabled','disabled');
					if(mdata.message!=null&&mdata.message!="")
					$(obj).find("#flowmessage").val(mdata.message);
					checkBackFlowNum();
					$(obj).find("#infoId").val(mdata.infoId);
					
					$(obj).find("#createUserId").text(util.isNull(mdata.createUserName)).attr("data",mdata.createUserId);
					$(obj).find("#createTime").text(mdata.createTime);
					$(obj).find("#reviewUserId").text(util.isNull(mdata.reviewUserName)).attr("data",mdata.reviewUserId);
					$(obj).find("#reviewTime").text(mdata.reviewTime);
					$(obj).find("#reviewCraftUserId").text(util.isNull(mdata.reviewCraftUserName)).attr("data",mdata.reviewCraftUserId);
					$(obj).find("#reviewCraftTime").text(mdata.reviewTime);
					if(util.isNull(mdata.svg)!=''||util.isNull(mdata.flow)!=''){
						$(obj).find("#flow").val(mdata.flow);
						$(obj).find("#svg").val(mdata.svg);
						graphCleanCache();
						flow(mdata.flow,true,true,obj,true,true,1,mdata.productId,mdata.productName,mdata.type==1?true:false);
						initCache(null,","+mdata.infotubeIds,","+mdata.inTankIds,","+mdata.outTankIds,","+mdata.pupmIds,mdata.message);
					}else{
						graphCleanCache();
						flow(null,true,true,obj,true,true,1,mdata.productId,mdata.productName,mdata.type==1?true:false);
					}
					var item=mdata.status;
					$(obj).find("#currentStatus").text(item);
					if(item==1){
						$(obj).find(".createDiv,.reviewBtn,.securityCodeDiv").show();
						$(obj).find(".createBtn,.reviewCraftDiv,.reviewDiv,.rebackBtn,.pumpTimDiv").hide();
					}else if(item==2){
						$(obj).find(".createDiv,.reviewCraftDiv,.reviewDiv,.rebackBtn,.pumpTimDiv").show();
						$(obj).find(".createBtn,.reviewBtn,.securityCodeDiv").hide();
					}else if(item==3){
						$(obj).find(".createDiv,.createBtn").show();
						$(obj).find(".reviewBtn,.reviewCraftDiv,.reviewDiv,.securityCodeDiv,.rebackBtn,.pumpTimDiv").hide();
					}else if(item==4){
						$(obj).find(".createDiv,.reviewBtn,.reviewDiv,.securityCodeDiv").show();
						$(obj).find(".createBtn,.reviewCraftDiv,button[key='4'],button[key='2'],.rebackBtn,.pumpTimDiv").hide();
					}else if(item==5){
						$(obj).find(".createDiv,.reviewBtn,.reviewCraftDiv,.securityCodeDiv").show();
						$(obj).find(".createBtn,.reviewDiv,button[key='5'],button[key='2'],.rebackBtn,.pumpTimDiv").hide();
					}else{//保存
						$(obj).find(".createDiv,.reviewCraftDiv,.reviewDiv,.reviewBtn,.securityCodeDiv,.rebackBtn,.pumpTimDiv").hide();
						$(obj).find(".createBtn").show();
					}
					if(mdata.type==1){
						$(obj).find(".ctDiv").hide();
						$(obj).find(".bfDiv").show();
					}else{
						$(obj).find(".bfDiv").hide();
						$(obj).find(".ctDiv").show();
					}
					
					$(obj).find(".securityCodeContent").text("储罐计划进行"+(mdata.type==1?"打循环方案":"倒罐方案")+", 货品："+mdata.productName+'；倒出罐号：'+util.isNull(mdata.outTankNames)+",倒出"+mdata.tankCount+
							'吨；泵号：'+mdata.pupmNames+'；管线：'+util.isNull(mdata.infotubeNames)+"；倒入罐号："+util.isNull(mdata.inTankNames)+"；目的："+util.isNull(mdata.transferPurpose)+"。");	
					}	
				},true);
			}
		});
		}else{
			$(obj).find(".createDiv,.reviewCraftDiv,.reviewDiv,.reviewBtn,.securityCodeDiv,.rebackBtn,.pumpTimDiv").hide();
			$(obj).find(".createBtn").show();
		}
	};
	function  checkBackFlowNum(){
		if(util.FloatSub($("#flowmessage").val(),$("#tankCount").val())>0){
			$("#checkMsg").show();
		}else{
			$("#checkMsg").hide();
		}
	}
	function deleteBackFlow(id){
		if(id){
			$('body').confirm({
				content:'确定要删除吗?',
				callBack:function(){
					$.ajax({
					type:'post',
					url:config.getDomain()+"/inboundoperation/deletebackflow",
					data:{transportId:id},
					dataType:'json',
					success:function(data){
						util.ajaxResult(data,'删除',function(){
							$('[data-role="managerGrid"]').getGrid().refresh();
						});
					}
					
				});
					}
			});
		
		}
	}
	
	/**码头打循环作业通知单*/ 
	function dialogDockBackFlowNotify(obj) {
		var id=$(obj).closest(".backflowDiv").find('#id').text();
	    if(id){
		var taskmsg="打循环：【"+$("#tankCount").val()+"】(吨)";
		notify.init(0,$(obj).attr("data"),$(obj).closest(".backflowDiv").find('#id').text(),taskmsg,null,true);
	    }else{
	    	$('body').message({type:'warning',content:'请保存后再点击！'});
	    }
	};
	/**库区打循环作业通知单*/ 
	function dialogStoreBackFlowNotify(obj) {
		var id=$(obj).closest(".backflowDiv").find('#id').text();
	    if(id){
		var taskmsg="打循环：【"+$("#tankCount").val()+"】(吨)";
		notify.init(1,$(obj).attr("data"),$(obj).closest(".backflowDiv").find('#id').text(),taskmsg,null,true);
	}else{
    	$('body').message({type:'warning',content:'请保存后再点击！'});
    }
	};
	/**倒罐作业通知单*/ 
	function dialogBackFlowNotify(obj){
		var id=$(obj).closest(".backflowDiv").find('#id').text();
	    if(id){
		var taskmsg="倒罐：【"+$("#tankCount").val()+"】(吨)";
		notify.init(6,$(obj).attr("data"),$(obj).closest(".backflowDiv").find('#id').text(),taskmsg,null,true);
	}else{
    	$('body').message({type:'warning',content:'请保存后再点击！'});
    }
	};
	function exportExcel(){
		var itemType=0;
		if (util.isNull($("#planType").val(), 1) == 4)
			itemType = 3;
		else
			itemType = util.isNull($("#planType").val(), 1);
		var url=config.getDomain()+'/planmanager/exportExcel?itemType='+itemType;
		if(util.isNull($("#type").text(),1)!=0){
			url+='&type='+util.isNull($("#type").text(),1);
		}
		if(util.isNull($('#shipId').val())!=''){
			url+='&shipName='+$('#shipId').val();
		}
		if(util.isNull($("#productId").attr('data'),1)!=0){
			url+='&productId='+$("#productId").attr('data');
		}
		if(util.isNull($("#berthId").attr('data'),1)!=0){
			url+='&berthId='+$("#berthId").attr('data');
		}
		url+='&status='+$("#status").val();
		if(util.isNull($("#startTime").val(),1)!=0){
			url+='&startTime='+util.formatLong($("#startTime").val()+" 00:00:00");
		}else{
			$('body').message({
				type:'warning',
				content:'请选择起始日期'	
			});
			return;
		}
		if(util.isNull($("#startTime").val(),1)!=0){
			url+='&endTime='+util.formatLong($("#endTime").val()+" 23:59:59");
		}else{
			$('body').message({
				type:'warning',
				content:'请选择起始日期'	
			});
			return;
		}
		window.open(url);
	};
	function exportTransportProgram(id){
		var url=config.getDomain()+'/planmanager/exportTransportProgram?transportId='+id;
		window.open(url);
	}
	function exportChangeTankProgram(id,type){
		var url=config.getDomain()+'/planmanager/exportChangeTankProgram?transportId='+id+'&type='+type;
		window.open(url);
	}
	function exportBerthProgram(arrivalId){
		var url=config.getDomain()+'/planmanager/exportBerthProgram?arrivalId='+arrivalId;
		window.open(url);
	}
 return{
init:init,
initBackFLow:initBackFLow,
dialogDockBackFlowNotify:dialogDockBackFlowNotify,
dialogStoreBackFlowNotify:dialogStoreBackFlowNotify,
dialogBackFlowNotify:dialogBackFlowNotify,
deleteBackFlow:deleteBackFlow,
exportExcel:exportExcel,
exportTransportProgram:exportTransportProgram,
exportChangeTankProgram:exportChangeTankProgram,
exportBerthProgram:exportBerthProgram
};
}();