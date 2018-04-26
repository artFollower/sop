	var weighBridge = function(){
	var LODOP; //声明为全局变量 
	var CreatedOKLodop7766=null;	 
	var goodsLogList;//通知单对应列表（  联单信息）
	var weightBridgeList;//发货称重列表
	var systemUserId,systemUserName;
	var isajax=true;
	
	/**初始化*/
	function initWeight(){
		initFormIput();
		getSystemUser();
		initCtr(); 
	};
	
	/**初始化用户信息*/
	function getSystemUser(){
		if(!systemUserId){
			$.ajax({
				type:'post',
				url:config.getDomain()+"/initialfee/getsystemuser",
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'获取系统信息',function(ndata){
						systemUserId=ndata[0].userId;
						systemUserName=ndata[0].userName;
						return systemUserId;
					},true);
			}});
			}else{
				return systemUserId;
			}
	};
	
	
	/**初始化控件*/
	function initCtr(){
		//初始化通知单号年月日
		$("#serialNo").val(util.findDate());	
		//初始化储罐信息
		util.urlHandleTypeahead("/baseController/getTankCode?productId=0",$("#tankName"),"code");
		//查询地磅信息
		initFlat();
		//获取地磅数据 
		config.getWeight();
		//通知单获取焦点
		$("#serialNo").keydown( 
				function(event) {
					var nums = $("#serialNo").val();
					var e = event|| window.event||arguments.callee.caller.arguments[0];
					var newStr= nums.substring(0, 10);
					//点击BACKSPACE只删除1个数字
					if (e && e.keyCode == 8)
						newStr = nums;
					//如果通知单已经完全，点击数字修改最后一个数字
					if (nums && nums.length == 11&&e && e.keyCode >= 48 && e.keyCode <= 57) 
							newStr = nums.substring(0, 10) + ''+ (e.keyCode - 48);
					$("#serialNo").val(newStr);
				});
		//通知单失去焦点
		$("#serialNo").keyup(function(){
			getGoodsLogInfo();
		});
		//提交
		$(".confirm").click(function(){
			var status=util.isNull($("#status").val(),1);
			if(status==2||status==3){
			if(util.isNull($("#inPort").val())==''){
				$('body').message({type:'warning',content:'发货口未填写'}); 
				return ;
			}
			if(util.isNull($("#tankName").attr("data"),1)==0){
				$('body').message({type:'warning',content:'罐号未填写'}); 
				return ;
			}}
			if(isajax&&status!=3){
				if(weightBridgeList&&weightBridgeList.length>0){
		          for(var i=0;i<weightBridgeList.length;i++){
		        	  weightBridgeList[i].tankId=util.isNull($("#tankName").attr("data"),1);//罐号
		        	  weightBridgeList[i].inPort=$("#inPort").val();//发货口
		          }			
				}
				config.load();
				isajax=false;
				$(".confirm").attr("disabled","disabled");
			$.ajax({
				type:'post',
				url:config.getDomain()+"/weighBridge/confirmtruck",
				data:{goodsLogList:JSON.stringify(goodsLogList),
					  weighBridgeList:JSON.stringify(weightBridgeList)},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'提交',function(ndata){
						if(status==2||status==3)
							$("#dVal").val(0);
						getGoodsLogInfo();
						isajax=true;
					},undefined,function(){
						getGoodsLogInfo();
						isajax=true;
					});
				}
			});
			}else{
				$('body').message({
					type:'warning',
					content:'已经实发，无法称重'
				});
			}
		});
		
		//准备执行
		$(".button-open").click(function(){
		 $("#serialNo").attr('disabled','disabled');
		 var oils=util.isNull($("#oilsHidden").val(),1);//货品类型0化学品1油品
		 var status= util.isNull($("#status").val(),1);//当前状态
		 if(status!=3){
			 var isTrue=true;
			if(oils==1){//油品
					$("#intoTime").val("");
					$("#inWeigh").val(0.00);
					$("#outTime").val(util.currentTime(1)) ;
					$("#outWeigh").val(0.00);
					$("#deliveryNum").val($("#deliverNum").val()) ;//发运数=开票数
					$("#status").val(2);
					checkFlowMeter(1);
					handleNotifyList(util.isNull($("#deliveryNum").val()),1,2);
			}else{//化学品
				if(status==1||status==0){//入库称重
					$("#intoTime").val(util.currentTime(1)) ;
					$("#inWeigh").val(util.toDecimal3($("#dVal").val()/1000,true)) ;
					handleNotifyList(util.isNull($("#inWeigh").val()),0,1);
				}else if(status==2){//出库称重
					$("#outTime").val(util.currentTime(1));
					$("#outWeigh").val(util.toDecimal3($("#dVal").val()/1000,true));
					$("#deliveryNum").val(util.FloatSub($("#outWeigh").val(),$("#inWeigh").val(),3));
					var actTotalNum=util.FloatSub(parseFloat(util.toDecimal3($("#dVal").val()/1000)),$("#inWeigh").val(),3);//总实发
					checkFlowMeter(0,actTotalNum);
					handleNotifyList(util.FloatSub($("#outWeigh").val(),$("#inWeigh").val(),3),0,2);
					}
				}
		 }else{
			 $("#serialNo").removeAttr('disabled');
			 $(".confirm").attr("disabled","disabled");
			 $('body').message({
					type:'warning',
					content:'已经实发，无法称重'
				});
		 }
		});
		
		//取消准备
		$(".button-close").click(function() {
			$(".button-open").removeClass("hidden");
			$("#serialNo").removeAttr("disabled");
			$(this).addClass("hidden");
			$(".confirm").attr("disabled","disabled");
		});
		
		};
		//校验表返与出库量
		function checkFlowMeter(oils,actToNum){
			var flowMeterNum=0,actualNum=0,differNum=0;
			//通过发货口校验表返是否返回
			if(util.isNull($("#inPort").val())==""){
			$('body').confirm({
				content:'表返值还未同步过来，请在罐车出库后，重新录入单子。',
				callBack:function(){
					$(".confirm").attr("disabled","disabled");
					return;
				}});
		      return;		
		   }
			if(util.isNull($("#tankName").val(),1)==0){
				$('body').confirm({
					content:'请填写罐号。',
					callBack:function(){
						$(".confirm").attr("disabled","disabled");
						return;
					}});
			      return;		
			   }
			
			//油品
			if(oils&&oils==1){
				flowMeterNum=util.isNull($("#measureWeigh").val(),1);//表返
				actualNum=util.isNull($("#deliverNum").val(),1);//开票
				differNum=util.FloatSub(flowMeterNum,actualNum,3);//差值
				  if(Math.abs(parseFloat(differNum))>0.01){
					  $('body').confirm({
							content:'表返与开单量相差'+differNum+'吨',
							callBack:function(){}});
				  }
			//化学品	  
			}else if(oils&&oils==0){
				flowMeterNum=util.isNull($("#flowMeterNum").val(),1);//总表返
				actualNum=actToNum;//总实发
				differNum=util.FloatSub(flowMeterNum,actualNum,3);
				var deliverNum = util.isNull($("#totalAmount").val(),1);//总开票数
				//表返差异
			  if(Math.abs(parseFloat(differNum))>1){
				  $('body').confirm({
						content:'表返与实发量差值'+differNum+'吨',
						callBack:function(){}});
			  }
			}
	};
	function initFlat(){
		$.ajax({
			type : "post",
			url : config.getDomain()+"/baseController/getUrl",
			dataType : "text",
			success : function(data) {
				var urls = data.split(",") ;
				if(urls.length>0){
				var html ;
				for(var i=0;i<urls.length;i++){
				if(urls[i]=="10.10.1.15"){
					html+="<option value='"+urls[i]+"'>北地磅</option>" ;
				}else{
					html+="<option value='"+urls[i]+"'>南地磅</option>" ;
				}}
				 $("#dibang").append(html) ;
				 $.ajax({
				type : "post",
				url : config.getDomain()+"/weighBridge/findPlat",
				dataType : "json",
				success : function(data) {
					util.ajaxResult(data,'获取地磅',function(ndata){
						if(ndata&&ndata.length>0){
							for(var i=0;i<ndata.length;i++){
								if(config.findUserId()==ndata[i].platUser){
									$("#dibang > option").each(function(){
										if($(this).val() ==ndata[i].platIp)
											$(this).attr("selected","selected");
									});
								}
							}
						}
					},true);
						}});	
				}
			}
		});
	};
	//复位操作
	var resetData = function(){
		if($(".button-open").hasClass("hidden")){
			$("body").message({
				type : 'warning',
				content : '您已经点击了准备执行，请点击取消准备或者确定才能复位。'
			});
			return;
		}else{
		$("#serialNo").val(util.findDate());
		getGoodsLogInfo();
		}
	};
	/*****************************************************************/
	function getGoodsLogInfo(nSerial){
		var serial=(!nSerial?util.isNull($("#serialNo").val()):nSerial);
		$("#inPort,#measureWeigh,#deliveryNum").val("");
		$("#overCount,#overMeasureCount").text("");
		$("#serialNo").removeAttr('disabled');
		 if(serial.length==11){
			 config.load();
			 $(".button-open").attr("disabled","disabled");
			 //是否存在联单
				$.ajax({
					type : "post",
					url : config.getDomain()+"/weighBridge/findTicket",
					data:{"ticketNo":serial,"deliverType":1},
					dataType:"json",
					async:false,
					success : function(data){
						if(data.data&&data.data.length>0){
	                       var ndata=data.data;
	                       var nmap=data.map;
							 $(".button-open").removeAttr("disabled");
							goodsLogList=new Array();	
							var totalNum=0;//总开票量
							var notifyList='';//联单html
							var totalMeasureWeigh=0;
							 isHasActual=false; 
							 if(nmap&&nmap.flag){
								 $(".truckState").show();
								 if(nmap.flag==0){//未开始装货color:#0099CC
									 $("#truckState").text(nmap.msg).attr("style","color:#9966CC");
								 }else if(nmap.flag==1){//正在装货
									 $("#truckState").text(nmap.msg).attr("style","color:#99CC33");
								 }else if(nmap.flag==2){//装货完成
									 $(".truckState").hide(); 
								 }
							 }else{
								$(".truckState").hide(); 
							 }
							if(ndata&&ndata.length>0){
						  for(var i=0;i<ndata.length;i++){
							  totalNum=util.FloatAdd(totalNum,ndata[i].deliverNum,3);
							  if(util.isNull(ndata[i].actualNum,1)>0){
								  isHasActual=true;
							  }
							  if(ndata[i].oils==1){//油品只有第一单
								  if(ndata[i].serial==serial){
								  goodsLogList.push({
									  'id':ndata[i].id,
									  'batchId':ndata[i].batchId,
									  'goodsId':ndata[i].goodsId,
									  'serial':ndata[i].serial,
									  'actualNum':ndata[i].actualNum,
									  'deliverNum':ndata[i].deliverNum,
									  'deliverType':ndata[i].deliverType,
									  'weightBridgeId':ndata[i].weightBridgeId,
									  'isCleanInvoice':ndata[i].isCleanInvoice
								  });
								  if(util.isNull(ndata[i].inPort)==''){
									  
								  }else{
									  $("#measureWeigh").val(ndata[i].measureWeigh) ;//表返值
									  $("#inPort").val(ndata[i].inPort);//发货口		
								  }
								  }
							  }else{//化学品
								  totalMeasureWeigh=util.FloatAdd(ndata[i].measureWeigh,totalMeasureWeigh,3);
								  goodsLogList.push({
									  'id':ndata[i].id,
									  'batchId':ndata[i].batchId,
									  'goodsId':ndata[i].goodsId,
									  'serial':ndata[i].serial,
									  'actualNum':ndata[i].actualNum,
									  'deliverNum':ndata[i].deliverNum,
									  'deliverType':ndata[i].deliverType,
									  'measureWeigh':ndata[i].measureWeigh,
									  'inPort':ndata[i].inPort,
									  'weightBridgeId':ndata[i].weightBridgeId,
									  'isCleanInvoice':ndata[i].isCleanInvoice
								  });
								  if(util.isNull(ndata[i].inPort)!=''&&ndata[i].serial==serial){
									  $("#measureWeigh").val(ndata[i].measureWeigh) ;//表返值
									  $("#inPort").val(ndata[i].inPort);//发货口	
								  }
							  }
							  
							  notifyList+='<div class="form-group col-md-12 itemNotifyList">'
									+'<label class="col-md-4" style="text-align: right;">'+ndata[i].serial+'</label>'
									+'<label class="col-md-4" style="text-align: right;">'+util.toDecimal3(ndata[i].deliverNum,true)+'</label>'
									+'<label class="col-md-4" style="text-align: right;">'+util.toDecimal3(ndata[i].actualNum,true)+'</label>'
									+'</div>'
						  }	
						  $("#flowMeterNum").val(totalMeasureWeigh);
						  
						  $("#notifyCount").text(ndata.length);//联单数
						  $("#totalAmount").val(totalNum);//总开票量
						  $('.itemNotifyList').remove();
						  $('.dataNotifyList').append(notifyList);//联单html
							} 
						}else{
							$("body").message({type:'error',content:'获取联单信息失败，请联系管理员。'});
						}
					}
				}); 
			//获取查询通知单详情	
				$.ajax({
					type : "post",
					url : config.getDomain()+"/weighBridge/queryWeighBridgeInfoStatement",
					data:{"serialNo":serial},
					dataType : "json",
					async:false,
					success:function(data){	
						util.ajaxResult(data,'获取通知单详情',function(ndata){
							 $(".button-open").removeAttr("disabled");
							if(ndata&&ndata.length>0){
								var goodsInfo=ndata[0];
								var weighBridge=ndata[1];
								//撤销的单子
								if(util.isNull(goodsInfo.cancelStatus,1)==44){
									$("body").message({type:'error',content:'该记录已经撤销，不能称重！'});
									return;}
								//
								if(goodsInfo){
								$("#goodsId").val(goodsInfo.id);
								$("#isCleanInvoice").val(goodsInfo.isCleanInvoice) ;//是否清单
								$("#yuanshihuozhu").val(goodsInfo.yuanshihuoti) ;//原始货主
								$("#ladingClientCode").val(goodsInfo.ladingClientCode) ;//客户编号
								$("#ladingClientName").val(goodsInfo.ladingClientName) ;//提货单位
								$("#ladingEvidence").val(goodsInfo.ladingEvidence) ;//提货凭证
								$("#shipInfo").val(goodsInfo.shipInfo) ;//船信
								$("#yuanhao").val(goodsInfo.yuanhao) ;//原号
								$("#cargoCode").val(goodsInfo.cargoCode) ;//货批号
								$("#tank").val(goodsInfo.tank);//储罐
								$("#diaohao").val(goodsInfo.diaohao) ;//调号
								$("#vsName").val(goodsInfo.vsName) ;//车船号
								$("#createUserName").val(goodsInfo.createUserName) ;//开单人
								$("#deliverNum").val(util.toDecimal3(goodsInfo.deliverNum,true)) ;//开票量
								$("#desc").val(util.isNull(goodsInfo.remark));//备注
								$("#storageInfo").val(util.isNull(goodsInfo.storageInfo));//仓位
								$("#productName").css("color",goodsInfo.fontColor);//货品颜色
								$("#productName").text(goodsInfo.productName);//货品名称
								$("#productIdHidden").val(goodsInfo.productId);//货品id
								$("#oilsHidden").val(goodsInfo.oils);//油品，化学品
								}
								if(weighBridge){
									$("#id").val(weighBridge.id);//weightBridgeId  
									$("#status").val(weighBridge.status) ;//称重状态 1，2，3
									$("#type").val(weighBridge.type) ;//1 称重2计量 (无效)
									$("#tankName").attr("data",weighBridge.tankId).val(weighBridge.tankName) ;
									
									var actualTareWeight = weighBridge.actualTareWeight;//修改后的
									var actualRoughWeight = weighBridge.actualRoughWeight;//修改后的
									var deliveryNum = weighBridge.deliveryNum;//单个单子的开票量
									var oils =goodsInfo.oils;//油品/化学品
									$("#intoTime").val(weighBridge.intoTime1) ;//入库时间
									$("#outTime").val(weighBridge.outTime1) ;//出库时间
									if(util.isNull($("#measureWeigh").val(),1)==0){
										$("#measureWeigh").val(weighBridge.measureWeigh) ;//表返值
									}
									if(util.isNull($("#inPort").val(),1)==0){
										$("#inPort").val(weighBridge.inPort);//发货口
									}
									$("#description").val(util.isNull(weighBridge.description));//备注
									$("#inStockPerson").val(weighBridge.inStockPerson);//入库称重人
									$("#outStockPerson").val(weighBridge.outStockPerson);//出库称重人
									$("#inStockPersonId").val(weighBridge.inStockPersonId);//入库称重人id
									if(actualTareWeight&&actualRoughWeight&&parseFloat(util.FloatAdd(actualTareWeight,actualRoughWeight))!=0)
									{
										$("#inWeigh").val(actualTareWeight) ;
										$("#outWeigh").val(actualRoughWeight) ;
									} else {
										$("#inWeigh").val(weighBridge.inWeigh) ;
										$("#outWeigh").val(weighBridge.outWeigh) ;
									}
									
									if(oils == 1){//油品  
										if(util.isNull(weighBridge.status,1)==3){
										if(util.isNull(goodsInfo.actualType,0)==1){
										$("#deliveryNum").val(util.toDecimal3(goodsInfo.actualNum,true));//发运数
										}else{
										 $("#deliveryNum").val(util.toDecimal3(goodsInfo.deliverNum,true));
										}
										}
									}else{//化学品
										if(util.isNull(goodsInfo.actualType,0)==1)
										{
										 $("#deliveryNum").val(util.toDecimal3(weighBridge.deliveryNum,true));
										}else{
										 $("#deliveryNum").val(0.000);
										}
									}
									if(weighBridge.status&&weighBridge.status==4)
									{
										$(".button-open").attr("disabled","disabled") ;
									}
								}
								if(util.isNull($("#tankName").val(),1)==0){
								$("#tankName").attr("data",goodsInfo.tankId).val(goodsInfo.tank) ;
								}
								$(".button-close").addClass("hidden");
								$(".button-open").removeClass("hidden");
							}
						},true);
					}});
		 };
	}
	/******************************************************************/
	//处理联单显示列表   出入库量，是否油品，状态
	function handleNotifyList(actNum,oils,status){
		if(goodsLogList&&goodsLogList.length>0){
			weightBridgeList=new Array();
			var actualNum=actNum;
			var itemNotify;
			var notifyList='';
			var serial=util.isNull($("#serialNo").val());
			var inWeight=$("#inWeigh").val();
			var differNum=0;
			var isTrue=true;//清单校验
			var isTrueA=true;//实发超量校验
			var isTrueB=true;//实发超量5%校验
			var msg;
			for(var i=0,len=goodsLogList.length;i<len;i++){
				itemNotify=goodsLogList[i];
				if(oils==1&&serial==itemNotify.serial){//油品
					itemNotify.createTime=util.formatLong($("#outTime").val());
					itemNotify.actualNum=actNum;
					weightBridgeList.push({
					id:(util.isNull(itemNotify.weightBridgeId,1)==0?undefined:itemNotify.weightBridgeId),
	                status:3,//状态为3					
	                serial:itemNotify.serial,//通知单号
	                outTime:util.formatLong($("#outTime").val()),//出库时间
	                deliveryNum:itemNotify.actualNum,//发运数
	                inPort:$("#inPort").val(),//发货口
	                measureWeigh:util.isNull($("#measureWeigh").val(),1),
	                tankId:$("#tankName").attr("data"),
					description:util.isNull($("#description").val()),
					outStockPersonId:systemUserId,
					createUserId:systemUserId,
					});	
				}else{//化学品
					if(status==1){//入库称重
						weightBridgeList.push({
						id:(util.isNull(itemNotify.weightBridgeId,1)==0?undefined:itemNotify.weightBridgeId),
						status:2,
						serial:itemNotify.serial,
						intoTime:util.formatLong($("#intoTime").val()),
						description:util.isNull($("#description").val()),		
						tankId:$("#tankName").attr("data"),		
						createUserId:systemUserId,
						inWeigh:actNum,
						inStockPersonId:systemUserId,
						});		
					}else if(status==2){//出库称重
						var inPort=itemNotify.inPort;
						if(serial==itemNotify.serial&&itemNotify.inPort!=$("#inPort").val()){
							inPort=$("#inPort").val();
						}
						if(util.FloatSub(actNum,itemNotify.deliverNum,3)>0){
							 if(i==goodsLogList.length-1){//最后一个单子
								 itemNotify.actualNum=actNum;
								 differNum=util.FloatSub(actNum,itemNotify.deliverNum,3);
								  if(itemNotify.deliverNum!=0 && differNum > 0){
									 isTrueA=false;
									 //如果实发数大于开票数的5%给警告
								 } 
								  if(itemNotify.deliverNum!=0&& Math.abs(differNum)>0.2){
									isTrueB=false;
								 }
								 //如果是清单
								 if(itemNotify.isCleanInvoice==1){
									  if(differNum>=0.01){
										 msg='清单发货量超量';
										 isTrue=false;
										 itemNotify.actualNum=itemNotify.deliverNum;
										}else{
											//重置出库重和发运数
											$("#outWeigh").val(util.FloatSub($("#outWeigh").val(),differNum,3));
											$("#deliveryNum").val(util.FloatSub($("#deliveryNum").val(),differNum,3));
											//修改实发量
											actualNum=util.FloatSub(actualNum,differNum,3);
											itemNotify.actualNum=itemNotify.deliverNum;
										} 
								 }
							 }else{
								 actNum=util.FloatSub(actNum,itemNotify.deliverNum,3);
								 itemNotify.actualNum=itemNotify.deliverNum;
							 }
						 }else{
							 differNum=util.FloatSub(actNum,itemNotify.deliverNum,3);
							 if(itemNotify.isCleanInvoice==1&&differNum<0){
								 msg='清单发货量不足';
								 isTrue=false;
								}
							 itemNotify.actualNum=actNum;
							 actNum=0;
						 }
						itemNotify.createTime=util.formatLong($("#outTime").val())+i;
						weightBridgeList.push({
							    id:(util.isNull(itemNotify.weightBridgeId,1)==0?undefined:itemNotify.weightBridgeId),
								status:3,
								serial:itemNotify.serial,
								outTime:(util.formatLong($("#outTime").val())+i),
								description:util.isNull($("#description").val()),		
								deliveryNum:itemNotify.actualNum,
								tankId:$("#tankName").attr("data"),		
								inPort:inPort,//发货口
								inWeigh:inWeight,
								outWeigh:util.FloatAdd(inWeight,itemNotify.actualNum,3),
								outStockPersonId:systemUserId,
								});
						inWeight=util.FloatAdd(inWeight,itemNotify.actualNum,3);
						
						notifyList+='<div class="form-group col-md-12 itemNotifyList">'
							+'<label class="col-md-4" style="text-align: right;">'+itemNotify.serial+'</label>'
							+'<label class="col-md-4" style="text-align: right;">'+util.toDecimal3(itemNotify.deliverNum,true)+'</label>'
							+'<label class="col-md-4" style="text-align: right;">'+util.toDecimal3(itemNotify.actualNum,true)+'</label>'
							+'</div>'
					}
				}
			}
			if(!isTrue){
				 $('body').confirm({
						content:msg+',请按单子补平。',
						callBack:function(){
							 $(".confirm").attr("disabled","disabled");
						}});
				 $(".confirm").attr("disabled","disabled");
			}else if(!isTrueB){
				$('body').confirm({
					content:'实发量与开票量差值超过0.2吨,是否继续。',
					callBack:function(){
						$(".button-close").removeClass("hidden");
						 $(".button-open").addClass("hidden");
					  $(".confirm").removeAttr("disabled");
					}});
				$(".confirm").attr("disabled","disabled");
			}else if(!isTrueA){
				$('body').confirm({
					content:'实发量超过开票量,是否继续。',
					callBack:function(){
						$(".button-close").removeClass("hidden");
						 $(".button-open").addClass("hidden");
					  $(".confirm").removeAttr("disabled");
					}});
				 $(".confirm").attr("disabled","disabled");
			}else{
				$(".button-close").removeClass("hidden");
				 $(".button-open").addClass("hidden");
				 $(".confirm").removeAttr("disabled");
			};
			if(oils==0&&status==2){
				 $('.itemNotifyList').remove();
				 if(notifyList.length>0)
				  $('.dataNotifyList').append(notifyList);//联单html	
			}
			goodsLogList[0].actualNum=actualNum;
		}
		};
	 
		
	/****************************打印*********************************/
		//打印
		function print(){
			var status =$("#status").val() ;
			var morePrint =$(".printAll").is(":checked")?0:1;
			if(status==3){
			$.ajax({
			 type:"post",
			 url:config.getDomain()+"/weighBridge/getDeliverInvoiceInfo",
			 dataType:"json",
			 data:{"serialNo":$("#serialNo").val(),"type":$("#type").val(),"morePrint":morePrint},
			 success:function(data) {
				 util.ajaxResult(data,'打印',function(ndata){
					 if(ndata&&ndata.length>0){
						 var isActualType=true;
						 for(var i=0;i<ndata.length;i++){
							 if(ndata[i].actualType==0){
								 isActualType=false;
								 break;
							 }
						 }
						 if(isActualType){
							 CreatePrintPage(ndata);
						 }else{
						  $("body").message({type : 'error',content : '还有未称重完成的单子'}); 
						 }
					 }else{
					 $("body").message({type : 'error',content : '没有可以打印的单子'});	 
					 }},true);
				 }});
			}else{
			$("body").message({type : 'error',content : '只有称重完成才能打印'});
			}
		}
		  // 打印单个或者联单
		  function CreatePrintPage(objlist) 
		  {
		     LODOP=getLodop();
	        for(var i = 0;i<objlist.length;i++)
	        { LODOP.PRINT_INIT("");
				if(i == (objlist.length-1)){
				    LODOP.SET_PRINT_PAGESIZE(0,"831px","467px","");
				}else{
				    LODOP.SET_PRINT_PAGESIZE(0,"831px","265px","");
				}
				if($(".printAll").is(":checked")){
					printCz(objlist[i]);
					LODOP.PRINT();
				}else{
					if(i==0){
					printCz(objlist[0]);
					LODOP.PRINT();
					}
				}
			 }
		  };    
		  
		//打印输出
		  function printCz(obj){
			var top = 0;
			var left = -20;
			var inStock = $("#inStockPerson").val();
			var outStock = $("#outStockPerson").val();
			var oils = obj.oils;
			var inOut = outStock;
			if(inStock){
				inOut = inStock + "," + outStock;
			}
			if(util.isNull(obj.customLading)!=''){
			LODOP.ADD_PRINT_TEXT(6+top,60+left,220,30,"海运提单号："+util.isNull(obj.customLading)); //入库船信
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(28+top,60+left,220,30,"海关放行时间："+util.isNull(obj.customLadingTime)); //入库船信
			}
			if(obj.shipInfo&&obj.shipInfo!=null&&obj.shipInfo!='null'){			
			LODOP.ADD_PRINT_TEXT(50+top,60+left,220,30,"船名："+obj.shipInfo); //入库船信
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			}
			LODOP.ADD_PRINT_TEXT(60+top,500+left,100,30,"JL2321"); //体系编号JL2321
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(60+top,590+left,100,30,obj.serial); //开票号
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(95+top,145+left,250,30,obj.ladingClientName);   // 提货单位
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(90+top,452+left,120,30,obj.ladingEvidence);  //提单号  
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(105+top,452+left,200,30,util.isNull(obj.tankName)+' '+util.isNull(obj.storageInfo));  //罐号
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(97+top,600+left,100,30,obj.vsName); //车船号
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(138+top,145+left,120,30,obj.productName);  //货物名称
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(156+top,145+left,120,30,obj.cargoCode); //货批号
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(148+top,380+left,80,30,util.toDecimal3(obj.deliverNum,true));  //开票数据
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(148+top,580+left,80,30,util.toDecimal3(obj.actualNum,true));  //  实发数
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(190+top,145+left,260,30,obj.createTime); //发货日期
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(195+top,380+left,100,30,inOut);//计量人
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(68+top,300+left,200,30,obj.yuanhao);   //原号
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(40+top,500+left,200,30,util.isNull(obj.remark));   //备注
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			if(oils == 0){
				if(util.isNull(obj.actualRoughWeight,0)!=0||util.isNull(obj.actualTareWeight,1)!=0){
					LODOP.ADD_PRINT_TEXT(208+top,60+left,400,30,"进出重（吨）："+util.toDecimal3(obj.actualTareWeight)+" / "+util.toDecimal3(obj.actualRoughWeight));  //入库重和出库重
				}else{
					LODOP.ADD_PRINT_TEXT(208+top,60+left,400,30,"进出重（吨）："+util.toDecimal3(obj.inWeigh)+" / "+util.toDecimal3(obj.outWeigh));  //入库重和出库重
				}
				LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			}else if(oils==1){
				LODOP.ADD_PRINT_TEXT(208+top,60+left,800,30,"视密度："+util.isNull(obj.viewDensity,1)+" 标密度："+util.isNull(obj.normalDensity,1)
						+" 体积比："+util.isNull(obj.volumeRatio,1)+" 视体积："+util.isNull(obj.viewVolume,1)+" 标体积："+util.isNull(obj.normalVolume,1)+"(公升)");   //原号
			}
		  };
		//打印控件
			function getLodop(oOBJECT,oEMBED){
			/**************************
			  本函数根据浏览器类型决定采用哪个页面元素作为Lodop对象：
			  IE系列、IE内核系列的浏览器采用oOBJECT，
			  其它浏览器(Firefox系列、Chrome系列、Opera系列、Safari系列等)采用oEMBED,
			  如果页面没有相关对象元素，则新建一个或使用上次那个,避免重复生成。
			  64位浏览器指向64位的安装程序install_lodop64.exe。
			**************************/
				var strHtmInstall="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='"+config.getDomain()+"/resource/plugins/install_lodop32.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
		        var strHtmUpdate="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='"+config.getDomain()+"/resource/plugins/install_lodop32.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
		        var strHtm64_Install="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='"+config.getDomain()+"/resource/plugins/install_lodop64.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
		        var strHtm64_Update="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='"+config.getDomain()+"/resource/plugins/install_lodop64.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
		        var strHtmFireFox="<br><br><font color='#FF00FF'>（注意：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它）</font>";
		        var strHtmChrome="<br><br><font color='#FF00FF'>(如果此前正常，仅因浏览器升级或重安装而出问题，需重新执行以上安装）</font>";
		        var LODOP;		
				try{	
				     //=====判断浏览器类型:===============
				     var isIE	 = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
				     var is64IE  = isIE && (navigator.userAgent.indexOf('x64')>=0);
				     //=====如果页面有Lodop就直接使用，没有则新建:==========
				     if (oOBJECT!=undefined || oEMBED!=undefined) { 
			               	 if (isIE) 
				             LODOP=oOBJECT; 
				         else 
				             LODOP=oEMBED;
				     } else { 
					 if (CreatedOKLodop7766==null){
			          	     LODOP=document.createElement("object"); 
					     LODOP.setAttribute("width",0); 
			                     LODOP.setAttribute("height",0); 
					     LODOP.setAttribute("style","position:absolute;left:0px;top:-100px;width:0px;height:0px;");  		     
			                     if (isIE) LODOP.setAttribute("classid","clsid:2105C259-1E0C-4534-8141-A753534CB4CA"); 
					     else LODOP.setAttribute("type","application/x-print-lodop");
					     document.documentElement.appendChild(LODOP); 
				             CreatedOKLodop7766=LODOP;		     
			 	         } else 
			                     LODOP=CreatedOKLodop7766;
				     };
				     //=====判断Lodop插件是否安装过，没有安装或版本过低就提示下载安装:==========
				     if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
				    	 $.get(config.getResource()+"/plugins/addplugins.jsp").done(function(data){
					 			dialog = $(data) ;
					 			if (navigator.userAgent.indexOf('Chrome')>=0)
					 				 dialog.find(".plugins").append(strHtmChrome) ;
					             if (navigator.userAgent.indexOf('Firefox')>=0)
					            	 dialog.find(".plugins").append(strHtmFireFox) ;
					             if (is64IE) document.write(dialog.find(".plugins").append(strHtm64_Install)); else
					             if (isIE)   document.write(dialog.find(".plugins").append(strHtmInstall));    else
					                 dialog.find(".plugins").append(strHtmInstall) ;
					 			dialog.modal({
					 				keyboard: true
					 			});
					 		});
				     } else 
				     if (LODOP.VERSION<"6.1.9.6") {
				             if (is64IE) document.write(strHtm64_Update); else
				             if (isIE) document.write(strHtmUpdate); else
				             document.documentElement.innerHTML=strHtmUpdate+document.documentElement.innerHTML;
				    	     return LODOP;
				     };
				     //=====如下空白位置适合调用统一功能(如注册码、语言选择等):====	     
	
				     LODOP.SET_LICENSES("昆明易极信息技术有限公司","055716580837383919278901905623","","");
				     //============================================================	     
				     return LODOP; 
				} catch(err) {
				     if (is64IE)	
			            document.documentElement.innerHTML="Error:"+strHtm64_Install+document.documentElement.innerHTML;else
			            document.documentElement.innerHTML="Error:"+strHtmInstall+document.documentElement.innerHTML;
				     return LODOP; 
				};
			}	  
	  return{
		  initWeight:initWeight,
		  resetData:resetData,
		  print:print
	  }
	}() ;
