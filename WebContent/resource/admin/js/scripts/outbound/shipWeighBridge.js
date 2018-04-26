var shipWeighBridge = function(){
	var LODOP; //声明为全局变量 
	var CreatedOKLodop7766=null;	 
	var goodsLogList;//通知单对应列表（  联单信息）
	var weightBridgeList;
	var systemUserId;
	var systemUserName;
	var isHasActual=false;
	var isajax=true;
//初始化车发称重信息
var initWeight=function(obj){
	$(obj).find("#serialNum").val(util.findDate());//通知单初始化年月日
	//准备执行
	$(obj).find(".button-open").click(function(){
		//TODO 如果已经输入通知单，并且不存在weightbridge
     var key=$(this).attr('key');		
		if(key==0){
			$('body').message({
				type:'warning',
				content:'请填写通知单'
			});
		}else if(key==2){
			$('body').message({
			type:'warning',
			content:'该单已经称重'});
		}else if(key==1){
			if($(obj).find('#outWeigh').val()&&$(obj).find('#outWeigh').val()!=0){
				$(obj).find("#intoTime").val(new Date().format("yyyy-MM-dd hh:mm:ss")) ;//入库时间
				$(obj).find("#outTime").val(new Date().format("yyyy-MM-dd hh:mm:ss")) ;//入库时间
				$(obj).find("#inWeigh").val(0);//入库重
				$(obj).find("#deliveryNum").val(util.FloatSub($(obj).find('#outWeigh').val(),0,3));//发运数
				handleNotifyList(util.FloatSub($(obj).find('#outWeigh').val(),0,3),obj);//处理联单或单个单子显示列表
				$(obj).find(".button-close").removeClass("hidden");
				$(this).addClass("hidden");
				$(obj).find(".confirm").removeAttr("disabled");
			}else{
				$(obj).find('#outWeigh').pulsate({
					color : "#399bc3",
					repeat : false
				});
			}
		}
	});
	//取消准备
	$(obj).find(".button-close").click(function(){
		$(obj).find("#intoTime").val('') ;//入库时间
		$(obj).find("#inWeigh").val('');//入库重
		$(this).addClass("hidden");
		$(obj).find(".button-open").removeClass("hidden");
		$(obj).find(".confirm").attr("disabled","disabled");
	});
	//确认提交
	$(obj).find(".confirm").click(function(){
		if(!isHasActual&&isajax){
			config.load();
			isajax=false;
			$(this).attr("disabled","disabled");
		$.ajax({
			type:'post',
			url:config.getDomain()+"/weighBridge/confirmship",
			data:{goodsLogList:JSON.stringify(goodsLogList),
				  weighBridgeList:JSON.stringify(weightBridgeList)
				  },
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'提交',function(ndata){
					getSerialInfo($(obj).find("#serialNum"));
					isajax=true;
				});
			}
		});
		}else{
			$('body').message({
				type:'warning',
				content:'已经手动实发，无法称重'
			});
		}
	});
	//复位
	$(obj).find(".reset").click(function(){
		$(obj).find("#intoTime,#outTime,#inWeigh,#outWeigh,#deliveryNum").val("") ;
		$(obj).find(".button-close").addClass("hidden");
		$(obj).find(".button-open").removeClass("hidden");
		$(obj).find(".confirm").attr("disabled","disabled");
		$(obj).find("#serialNum").val(util.findDate());
		$(obj).find('.showNotifyList').hide();
		$(obj).find('.itemNotifyList').remove();
		$(obj).find("#notifyCount").text('');
		$(obj).find(".intention").val('');
	});
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
	
//获取通知单信息	 obj ==input [id=serialNo]
var getSerialInfo=function(serial){
	$(obj).find('.showNotifyList').hide();
     var obj=$(serial).closest('.shipWeighBridgeBody');
     
     $(serial).keydown(function(event){ 
			var nums = $("#serialNum").val();
			var e = event || window.event || arguments.callee.caller.arguments[0];
			var newStr = nums.substring(0,10);
			if(e && e.keyCode == 8)
			{
				newStr = nums;
			}
			if(nums&&nums.length==11){
				if(e && e.keyCode>=48 && e.keyCode <= 57)
				{ 
		            newStr=nums.substring(0,10)+''+(e.keyCode-48);   
		        }
			}
			
			$(serial).val(newStr);
		}); 
     
     
	   if($(serial).val().length==11){
		   //获取是否存在联单
			$.ajax({
				type : "post",
				url : config.getDomain()+"/weighBridge/findTicket",
				data:{
					"ticketNo":$(serial).val(),
					"deliverType":'2,3'
				},
				dataType:"json",
				async:false,
				success : function(data) {
					util.ajaxResult(data,'获取联单信息',function(ndata){
						if(ndata&&ndata.length>0){
							goodsLogList=new Array();
							var totalNum=0;
							var notifyList='';
							 isHasActual=false; 
						  for(var i=0 ;i<ndata.length;i++){
							  totalNum=util.FloatAdd(totalNum,ndata[i].deliverNum,3);
							  if(util.isNull(ndata[i].actualNum,1)>0){
								  isHasActual=true;
							  }
							  goodsLogList.push({
								  'id':ndata[i].id,
								  'batchId':ndata[i].batchId,
								  'goodsId':ndata[i].goodsId,
								  'serial':ndata[i].serial,
								  'actualNum':ndata[i].actualNum,
								  'deliverNum':ndata[i].deliverNum,
								  'deliverType':ndata[i].deliverType,
								  'weightBridgeId':ndata[i].weightBridgeId,
							  });
							  notifyList+='<div class="form-group col-md-12 itemNotifyList">'
									+'<label class="col-md-4" style="text-align: right;">'+ndata[i].serial+'</label>'
									+'<label class="col-md-4" style="text-align: right;">'+ndata[i].deliverNum+'</label>'
									+'<label class="col-md-4" style="text-align: right;">'+ndata[i].actualNum+'</label>'
									+'</div>'
						  }	
						  $(obj).find("#notifyCount").text(ndata.length);
						  $(obj).find("#totalAmount").val(totalNum);
						  $(obj).find('.showNotifyList').show();
						  $(obj).find('.itemNotifyList').remove();
						  $(obj).find('.dataNotifyList').append(notifyList);
						}else{
							$(obj).find('.showNotifyList').hide();
							 $(obj).find('.itemNotifyList').remove();
						}
					},true);
				}
			});

			//初始化通知单详情
			$.ajax({
				type : "post",
				url : config.getDomain()+"/weighBridge/queryWeighBridgeInfoStatement",
				data:{
					"serialNo":$(serial).val(),
				},
				dataType : "json",
				async:false,
				success : function(data) {
					if(data.data.length>0)
					{
						var goodsInfo = data.data[0] ;
						var weighBridge = data.data[1] ;
						var cancelStatus = goodsInfo.cancelStatus;
						
						$(obj).find("#goodsId").val(goodsInfo.id) ;
						$(obj).find("#yuanshihuozhu").val(goodsInfo.yuanshihuoti) ;
						$(obj).find("#ladingClientCode").val(goodsInfo.ladingClientCode) ;
						$(obj).find("#ladingClientName").val(goodsInfo.ladingClientName) ;
						$(obj).find("#ladingEvidence").val(goodsInfo.ladingEvidence) ;
						$(obj).find("#shipInfo").val(goodsInfo.shipInfo) ;
						$(obj).find("#yuanhao").val(goodsInfo.yuanhao) ;
						$(obj).find("#cargoCode").val(goodsInfo.cargoCode) ;
						$(obj).find("#tank").val(goodsInfo.tank);
						$(obj).find("#diaohao").val(goodsInfo.diaohao) ;
						$(obj).find("#vsName").val(goodsInfo.vsName) ;
						$(obj).find("#createUserName").val(goodsInfo.createUserName) ;
						$(obj).find("#deliverNum").val(util.FloatAdd(goodsInfo.deliverNum,0,3)) ;
						$(obj).find("#measureWeigh").val(0) ;
						$(obj).find("#desc").val(goodsInfo.storageInfo);
						$(obj).find("#productName").css("color",goodsInfo.fontColor);
						$(obj).find("#productName").text(goodsInfo.productName);
						$(obj).find("#productIdHidden").val(goodsInfo.productId);
						$(obj).find("#oilsHidden").val(goodsInfo.oils);
						$(obj).find("#outBoundStatus").val(goodsInfo.outBoundStatus);
						//设置地磅数据
						//设置地磅信息
						if(weighBridge)
						{
							var olis = $("#oilsHidden").val();
							
							$(obj).find("#reviewStatus").val(weighBridge.reviewStatus);
							$("#id").val(weighBridge.id) ;
							$("#status").val(weighBridge.status);
							$("#type").val(weighBridge.type) ;
							$("#intoTime").val(weighBridge.intoTime1) ;
							$("#inWeigh").val(weighBridge.inWeigh) ;
							$("#outWeigh").val(weighBridge.outWeigh) ;
							
							$("#outTime").val(weighBridge.outTime1) ;
							$("#deliveryNum").val(weighBridge.outWeigh);
							$("#inPort").val(weighBridge.inPort) ;
							$("#description").val(weighBridge.description) ;
//							$("#inStockPerson").val(weighBridge.inStockPerson);
							$("#outStockPerson").val(weighBridge.outStockPerson);
						}
						//如果存在通知单
						if(goodsInfo.id&&goodsInfo.id!=0){
							$(obj).find('.button-open').attr('key',1);//0.表示没有通知单1.表示存在通知单没有weighbridge 2.表示已经执行成功不需要再执行
							$(obj).find(".confirm").attr("disabled","disabled");
							$(obj).find(".button-close").addClass("hidden");
							$(obj).find(".button-open").removeClass("hidden");
						}
						if(weighBridge&&weighBridge.status&&weighBridge.status==2){//执行成功
							$(obj).find(".button-close").addClass("hidden");
							$(obj).find(".button-open").removeClass("hidden");
							$(obj).find('.button-open').attr('key',2);
							$(obj).find(".confirm").attr("disabled","disabled");
						}
					}
				}
			}) ;
	        }
			$(".button-close").addClass("hidden");
			$(".button-open").removeClass("hidden");
	
}	

//处理联单显示列表
function handleNotifyList(actNum,obj){
		if(goodsLogList&&goodsLogList.length>0){
			var notifyList='';
			weightBridgeList=new Array();
			 for(var i=0 ;i<goodsLogList.length;i++){
				 var itemNotify=goodsLogList[i];
				 if(util.FloatSub(actNum,itemNotify.deliverNum,3)>0){
					 if(i==goodsLogList.length-1){//如果只有一个
						 itemNotify.actualNum=actNum;
						//如果实发数大于开票数的10%给警告
						 if(itemNotify.deliverNum!=0&&util.FloatDiv(util.FloatSub(actNum,itemNotify.deliverNum,3),itemNotify.deliverNum,3)>0.05){
							 
							 $("body").message({
								type:'warning',
								 content:'发运数和开票数差距大于5% ！'
							 });
						 }
					 }else{
						 actNum=util.FloatSub(actNum,itemNotify.deliverNum,3)
						 itemNotify.actualNum=itemNotify.deliverNum;
					 }
					 
				 }else{
					 itemNotify.actualNum=actNum;
					 actNum=0;
				 }
				 goodsLogList[i]=itemNotify;
				 
				 weightBridgeList.push({
					   "id":(util.isNull(itemNotify.weightBridgeId,1)==0?undefined:itemNotify.weightBridgeId),
						"status":2,
						"type":1,
						"totalAmount":$(obj).find("totalAmount").val(),
						"ticketNum":itemNotify.actualNum,
						"intoTime":util.formatLong($(obj).find("#intoTime").val()),
						"inWeigh":0,
						"serial":itemNotify.serial,
						"outTime":util.formatLong($("#outTime").val()),
						"outWeigh":itemNotify.actualNum,
						"measureWeigh":itemNotify.actualNum,
						"deliveryNum":itemNotify.deliverNum,
//						"createTime":new Date().format("yyyy-MM-dd hh:mm:ss"),
						"description":$(obj).find("#description").val(),
//						"inStockPersonId":systemUserId,
						"outStockPersonId":systemUserId
				 });
				  notifyList+='<div class="form-group col-md-12 itemNotifyList">'
						+'<label class="col-md-4" style="text-align: right;">'+goodsLogList[i].serial+'</label>'
						+'<label class="col-md-4" style="text-align: right;">'+goodsLogList[i].deliverNum+'</label>'
						+'<label class="col-md-4" style="text-align: right;">'+goodsLogList[i].actualNum+'</label>'
						+'</div>'
			  }	
			  $(obj).find('.showNotifyList').show();
			  $(obj).find('.itemNotifyList').remove();
			  $(obj).find('.dataNotifyList').append(notifyList);
		}
	};
/************************************************打印单子******************************************************************************/	
	//打印
	function print(){
		var status =$("#status").val() ;
		var morePrint =$(".printAll").is(":checked")?0:1;
		var outBoundStatus=$("#outBoundStatus").val();
		var reviewStatus=$("#reviewStatus").val();
		if(outBoundStatus>=53&&reviewStatus>0){
		}else{
			$("body").message({type : 'warning',content : '请出库确认后打印'});
			return;
		}
		if(status==2){
		$.ajax({
		 type:"post",
		 url:config.getDomain()+"/weighBridge/getDeliverInvoiceInfo",
		 dataType:"json",
		 data:{"serialNo":$("#serialNum").val(),"type":2,'morePrint':morePrint},
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
						 CreateShipPrintPage(ndata);
					 }else{
					  $("body").message({type : 'warning',content : '还有未完成的单子'}); 
					 }
				 }else{
				 $("body").message({type : 'warning',content : '没有可以打印的单子'});	 
				 }},true);
			 }});
		}else{
		$("body").message({type : 'warning',content : '只有完成才能打印'});
		}
	}
	  // 打印单个或者联单
	  function CreateShipPrintPage(objlist) 
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
		LODOP.ADD_PRINT_TEXT(195+top,145+left,260,30,obj.createTime); //发货日期
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(195+top,380+left,100,30,inOut);//计量人
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(68+top,300+left,200,30,obj.yuanhao);   //原号
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(40+top,500+left,200,30,util.isNull(obj.remark));   //备注
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
//		if(oils == 0){
//			LODOP.ADD_PRINT_TEXT(208+top,60+left,400,30,"进出重（吨）："+util.toDecimal3(obj.inWeigh)+" / "+util.toDecimal3(obj.outWeigh));  //入库重和出库重
//			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
//		}else if(oils==1){
//			LODOP.ADD_PRINT_TEXT(208+top,60+left,800,30,"视密度："+util.isNull(obj.viewDensity,1)+" 标密度："+util.isNull(obj.normalDensity,1)
//					+" 体积比："+util.isNull(obj.volumeRatio,1)+" 视体积："+util.isNull(obj.viewVolume,1)+" 标体积："+util.isNull(obj.normalVolume,1)+"(公升)");   //原号
//		}
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
	
	
/******************************************************************************************************************************/	
return {
initWeight:initWeight,//初始化车发称重信息
getSerialInfo:getSerialInfo,//获取通知单信息
handleNotifyList:handleNotifyList,//处理联单显示列表
print:print
	}
}() ;