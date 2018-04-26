//目录
//changeTab------入库列表改变
//initNotifyBtn----初始化列表按钮增删改
//init-----初始化通知单
//initSpecialBtn----初始化通知单特殊字段
//changeCode-----改变日期同时改变通知单号
//initContent---------初始化数据
//getNoticeResult---------处理通知的结果
//initCheckBox------初始化复选框
//initSpecialContent--------初始化不同通知单的数据
//initNotice--------初始化控件
//initCheckAll-----全选按钮
//valicateCheckBox---校验是否全选复选框
//getIdsCheckBox---获取被选中的复选框的id
	//getSpecialContent---获取不同通知单的数据
//dialogShowLog----获取通知单历史记录
//saveLog------保存历史记录
//exportXML----导出exel
/** 通知单 */
var notify = function() {
var notifyItem = null;// 通知单类型对应
var code = null;// 通知单编号
var dialog = null;// 通知单
var graph = null;
var graph1 = null;

var notifyUrl=["/pages/inbound/notify/notice/piping_notify.jsp",//配管作业通知单 0
               "/pages/inbound/notify/notice/benzolhot_notify.jsp",//苯加热作业通知单 1
               "/pages/inbound/notify/notice/notify_dockcirculation.jsp",//打循环作业通知单(码头)2
               "/pages/inbound/notify/notice/notify_storecirculation.jsp",//打循环作业通知单(库区)3
               "/pages/inbound/notify/notice/docktubeclean_notify.jsp",//管线清洗作业通知单(码头)4
               "/pages/inbound/notify/notice/storetubeclean_notify.jsp",//管线清洗作业通知单(库区)5
               "/pages/inbound/notify/notice/docktubesweep_notify.jsp",//扫线作业通知单(码头)6
               "/pages/inbound/notify/notice/storetubesweep_notify.jsp",//扫线作业通知单(库区)7
               "/pages/inbound/notify/notice/tankclean_notify.jsp",//清罐作业通知单8
               "/pages/inbound/notify/notice/tankdrawwater_notify.jsp",//储罐放水作业通知单9
               "/pages/inbound/notify/notice/tankopenhole_notify.jsp",//储罐开人孔作业通知单10
               "/pages/inbound/notify/notice/transfusion_notify.jsp",//转输作业通知单11
               "/pages/inbound/notify/notice/notify_backflow.jsp",//倒罐作业通知单12
               "/pages/inbound/notify/notice/notify_dock.jsp",//码头接卸作业通知单13
               "/pages/inbound/notify/notice/notify_dynamic.jsp",//动力班接卸作业通知单14
               "/pages/inbound/notify/notice/notify_dockship.jsp",//码头船发作业通知单15
               "/pages/inbound/notify/notice/notify_dynamicship.jsp",//码头船发作业通知单16
               "/pages/inbound/notify/notice/exchangepot_notify.jsp"//车发换罐作业通知单17
               ];
var notifyDialogUrl=[
               "/pages/inbound/notify/notice/dockcirculation_notify.jsp",//打循环作业通知单(码头)0
               "/pages/inbound/notify/notice/storecirculation_notify.jsp",//打循环作业通知单(库区)1
               "/pages/inbound/notify/notice/dock_notify.jsp",//码头接卸作业通知单2
               "/pages/inbound/notify/notice/dynamic_notify.jsp",//动力班接卸作业通知单3
               "/pages/inbound/notify/notice/dockship_notify.jsp",//码头船发作业通知单4
               "/pages/inbound/notify/notice/dynamicship_notify.jsp",//码头船发作业通知单5
               "/pages/inbound/notify/notice/backflow_notify.jsp",//倒罐作业通知单12
               "/pages/inbound/notify/notice/notify_transfusion.jsp"//转输作业通知单11
               ];
var notifyTitles = [ "配管作业通知单", "苯加热作业通知单", "打循环作业通知单(码头)", "打循环作业通知单(库区)",
			"管线清洗作业通知单(码头)", "管线清洗作业通知单(库区)", "扫线作业通知单(码头)", "扫线作业通知单(库区)",
			"清罐作业通知单", "储罐放水作业通知单", "储罐开人孔作业通知单", "转输作业通知单", "倒罐作业通知单",
			"接卸作业通知单(码头)", "接卸作业通知单(动力班)", "船发作业通知单(码头)", "船发作业通知单(库区)",
			"车发换罐作业通知单" ];  
//http://localhost:8080/sop/inboundoperation/updatetransportprogram
/********************************通知单列表***********************************************************/
/**入库列表tab改变*/
function changeTab(obj, type) {
    window.location.href = "#/notify/detail?item=" + type;
	notifyItem=type;
	$(".todo-detail-title").text(notifyTitles[type]);
};

   /**初始化列表按钮增删改*/
   function initNotifyBtn(){
	   $(".notifyBack,.notifyAdd,.notifyModify.notifyRemove").unbind("click");
	   $(".notifyBack").click(function() {
		   window.location.href = config.getDomain() + "/#/notify";
	   });
	   $(".notifyAdd,.notifyModify").click(function(){
		if($(this).hasClass("notifyAdd")){
			init(notifyItem);
		}else{
			if(util.validateData($('[data-role="notifyGrid"]'))){
			var itemdata=$('[data-role="notifyGrid"]').getGrid().selectedRows()[0];	
				init(itemdata.type,itemdata.code);
			}
		}});
	   $(".notifyRemove").click(function(){
		   if(util.validateData($('[data-role="notifyGrid"]'),1)){
				var data=$('[data-role="notifyGrid"]').getGrid().selectedRows();
				var codes="";
				  for(var item=0;item<data.length;item++){
					  if(data[item].type||data[item].type==0)
					  codes+="'"+data[item].code+"',";
				  }	
                $.ajax({
                	type:"post",
                	url:config.getDomain()+"/notify/delete",
                	data:{"codes":codes.substring(0,codes.length-1)},
                	dataType:'json',
                	success:function(data){
                		util.ajaxResult(data,"删除",function(){
                		 $('[data-role="notifyGrid"]').getGrid().refresh();
                		});
                	}
                });}});
	   
	   $(".notifySearch").click(function(){
		   $("#roleManagerQueryDivId").slideToggle("slow");
	   });
	   
	   $(".searchNotify").click(function(){
			 $('[data-role="notifyGrid"]').getGrid().search(getSearchCondition());
	   });
	   
	   $(".reset").click(function(){
		   $("#codeF,#startTime,#endTime").val("");
	   });
   };
   function getSearchCondition(){
	   var params={'isList':1};
		 $("#notifyListForm").find('.form-control').each(function(){
           var $this = $(this);
          	 var name=$this.attr('name');
          	 if(name&&$this.val()!=""){
          		 if(name.indexOf("Time")!=-1){
          			params[name]=util.formatLong($this.val()); 
          		 }else{
          		 params[name]=$this.val();
          		 }
          	 }else{
          		 params[name]=null;
          	 }
       });
		 return params;
   }
   /******************************************通知单***************************************************************/
   //item---每个通知单的标识，code----通知单号（包括字母），transportId,taskMsg---流程通知单传递，batchId----出库
	var init=function(item,code,transportId,taskMsg,batchId,isDialog){
		config.load();
		if(isDialog == null || isDialog == undefined) {
			$(".notice-list").addClass("hidden");
			$(".hidden-print").addClass("hidden");
			$(".notice").removeClass("hidden");
			$(".notice").load(config.getResource()+notifyUrl[item],function(data) {
				dialog=$(".notice");
				  //如果是添加的通知单，可以编辑日期
					dialog.find("#createTime").change(function(){
						if(!code&&dialog.find("#code").attr('key')==0)
							changeCode(this,item);
					});
					
					dialog.find('[data-dismiss="modal"]').click(function(){
						
						$(".notice").addClass("hidden");
						$(".notice-list").removeClass("hidden");
						$(".hidden-print").removeClass("hidden");
						$(".notice").children().remove();
						$(".scroll-to-top").click();
						
						if($('[data-role="notifyGrid"]').getGrid() != null)
							$('[data-role="notifyGrid"]').getGrid().refresh();
					});
					//流程内的通知单保留transportId
					if(transportId!=undefined)
					dialog.find("#transportId").text(transportId);
					
					dialog.find("#code").attr('flowstatus',0);
					//初始化各自的控件
					initSpecialBtn(dialog,item);
					
				    //初始化公共部分
					if(code){//修改
						initContent(dialog,code,item);//初始化数据
						initNotice(dialog,item,true,transportId,taskMsg,batchId);//初始化控件
					}else{//添加
						initNotice(dialog,item,false,transportId,taskMsg,batchId);//初始化控件
						
						if(dialog.find(".modifyNotice").attr('key')=='0'&&config.hasPermission('ANOTICEDISPATCH')){
							//未点击修改
							dialog.find('#saveBeforeTask,#saveInTask,#saveAfterTask').hide();
						}else if(dialog.find(".modifyNotice").attr('key')=='1'&&config.hasPermission('ANOTICEDISPATCH')){
							//点击修改
							dialog.find('#saveBeforeTask,#saveInTask,#saveAfterTask').show(); 						
						}
					}
					config.unload();
			});
		}else {
			$.get(config.getResource()+notifyDialogUrl[item]).done( function(data) {
				if(item==0){
					item=2;
				}else if(item==1){
					item=3;
				}else if(item==2){
					item=13;
				}else if(item==3){
					item=14;
				}else if(item==4){
					item=15;
				}else if(item==5){
					item=16;
				}else if(item==6){
					item=12;
				}else if(item==7){
					item=11;
				}
			    dialog=$(data);
			  //如果是添加的通知单，可以编辑日期
				dialog.find("#createTime").change(function(){
					if(!code&&dialog.find("#code").attr('key')==0)
						changeCode(this,item);
				});
				
				dialog.find('[data-dismiss="modal"]').click(function(){
						dialog.remove();
					});
				
				//流程内的通知单保留transportId
				if(transportId!=undefined)
				dialog.find("#transportId").text(transportId);
				dialog.find("#code").attr('flowstatus',0);
				//初始化各自的控件
				initSpecialBtn(dialog,item);
				
			    //初始化公共部分
				if(code){//修改
					initContent(dialog,code,item);//初始化数据
					initNotice(dialog,item,true,transportId,taskMsg,batchId);//初始化控件
				}else{//添加
					initNotice(dialog,item,false,transportId,taskMsg,batchId);//初始化控件
					
					if(dialog.find(".modifyNotice").attr('key')=='0'&&config.hasPermission('ANOTICEDISPATCH')){
						//未点击修改
						dialog.find('#saveBeforeTask,#saveInTask,#saveAfterTask').hide();
					}else if(dialog.find(".modifyNotice").attr('key')=='1'&&config.hasPermission('ANOTICEDISPATCH')){
						//点击修改
						dialog.find('#saveBeforeTask,#saveInTask,#saveAfterTask').show(); 						
					}
				}
				dialog.modal({
					keyboard : true
				});
				config.unload();
			});
		}
	}
	/**初始化数据*/
	function  initContent(obj,code,item){
		$.ajax({
			type:"post",
			url:config.getDomain()+"/notify/list",
			data:{'code':code,'types':item},
			dataType:"json",
			success:function(data){
				util.ajaxResult(data,'初始化',function(ndata){
					if(ndata[0].code){
						//初始化数据
						var isHasEmptyA=true,isHasEmptyB=true,isHasEmptyC=true;
						var a=0,b=0,c=0;//时间
						for(var i=0;i<ndata.length;i++){
							var itemData=ndata[i];
							if(itemData.contentType==0){//作业要求
								//初始化基本通知单信息
								$(obj).find("#code").text(itemData.code.substring(1,itemData.code.length));
								$(obj).find("#code").attr('data',itemData.code);
								$(obj).find("#code").attr("key",1);
								$(obj).find("#createTime").val(itemData.createTime.substring(0,10));
								//处理通知的结果
								getNoticeResult($(obj).find(".surecheck"),itemData,"SureTask");
								//处理不同通知的内容
								if(itemData.content)
								initSpecialContent(obj,itemData.type,itemData.content);//初始化不同通知单的调度的数据
							}else if(itemData.contentType==1){//作业前检查
								var isInita=true;
								if(itemData.flag!=1){
							    //是否初始化cb
								isInita= util.compareTime(a,itemData.sureTime);
								if(isInita){//后一个时间大于前一个时候
									//获取修改项
									a=itemData.sureTime;}
								}
								getNoticeResult($(obj).find(".beforecheck"),itemData,"BeforeTask",isHasEmptyA,isInita);
								if(itemData.flag!=1){
									isHasEmptyA=false;
								}
							}else if(itemData.contentType==2){//作业中检查
								var isInitb=true;
								if(itemData.flag!=1){
									isInitb=util.compareTime(b,itemData.sureTime);
									if(isInitb){b=itemData.sureTime;}
								}
								getNoticeResult($(obj).find(".incheck"),itemData,"InTask",isHasEmptyB,isInitb);
								if(itemData.flag!=1){
									isHasEmptyB=false;
								}
							}else if(itemData.contentType==3){//作业后检查
								var isInitc=true;
								if(itemData.flag!=1){
								isInitc= util.compareTime(c,itemData.sureTime);
								if(isInitc){c=itemData.sureTime;}
								}
								getNoticeResult($(obj).find(".aftercheck"),itemData,"AfterTask",isHasEmptyC,isInitc);
								if(itemData.flag!=1){
									isHasEmptyC=false;
								}
							}
						}
					}else{//根据通知单号查询失败
						changeCode(this,item);
						initNotice(dialog,item,false,transportId,taskMsg,batchId);//初始化控件
					}
					//调度控制按钮显隐
					if($(obj).find(".modifyNotice").attr('key')=='0'&&config.hasPermission('ANOTICEDISPATCH')){
						//未点击修改
						$(obj).find('#saveBeforeTask,#saveInTask,#saveAfterTask').hide();
					}else if($(obj).find(".modifyNotice").attr('key')=='1'&&config.hasPermission('ANOTICEDISPATCH')){
						//点击修改
						$(obj).find('#saveBeforeTask,#saveInTask,#saveAfterTask').show(); 						
					}
				},true);
			}
		});
	};
	/**初始化控件*/
	function initNotice(obj,item,isMF,transportId,taskMsg,batchId){
		var muserId=null;
		var muserName=null;
		util.initTimePicker(obj);
		$(obj).find("#modifyNotice").hide();
		if(!isMF){
		$(obj).find("#createTime").datepicker("setDate",util.currentTime(0));
		dialog.find("#createTime").change();
		}
		//总是获取原数据
		if(transportId&&taskMsg){
			$(obj).find("#taskMsg").val(taskMsg);
			$.ajax({
				type : "post",
				url : config.getDomain() + "/inboundoperation/list",
				data : {"id":transportId,"transportIds" :transportId,"result":4},
				dataType : "json",
				success:function(data){
					util.ajaxResult(data,'获取工艺流程',function(ndata){
						if(ndata&&ndata[0].svg){
							$(obj).find("#contentDiv").empty();
							$(obj).find("#contentDiv").append(ndata[0].svg);
						}},true);
				}
			});
		}
		
		//船发相关初始化 transporgramId,taskMsg,svg
		if(batchId){
			$.ajax({
				type:'post',
				url : config.getDomain()+"/outboundserial/queryDockNotifyInfo",
				dataType : "json",
				data:{"arrivalId": batchId},
				async:false,
				success : function(data) {
					var arrivalList = data.data[1] ;
					var tpList=data.data[0];
					var workTaskStr = "【"+arrivalList[0].shipName+"】 发货【"+arrivalList[0].productName+"】 共【"+arrivalList[0].totalNum+"】吨;" ;
					
					$(obj).find("#taskMsg").val(workTaskStr) ;
						var svg = tpList[0].svg ;
						$(obj).find("#transportId").text(tpList[0].id) ;//设置传输方案id
						$(obj).find("#contentDiv").empty().append(svg) ;//设置工艺流程图
					}
			}) ;
		}
		$.ajax({
			type:'post',
			url:config.getDomain()+"/initialfee/getsystemuser",
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取系统信息',function(ndata){
					muserId=ndata[0].userId;
					muserName=ndata[0].userName;
				},true);
			}
		});
		//初始化全选按钮
		initCheckAll(obj);//全选按钮
		
		//重置
		$(obj).find("#reset").unbind('click');
		$(obj).find("#reset").click(function(){
            $.ajax({
                 type:"get",            	
            	 url:config.getDomain()+"/notify/reset?code="+$(obj).find("#code").attr('data'),
            	dataType:"json",
            	success:function(data){
                     if(data.code=='0000'){
//                    	 if($('[data-role="notifyGrid"]').getGrid()){
//								$('[data-role="notifyGrid"]').getGrid().refresh();
//							} 
//                    	 initContent(obj,$(obj).find("#code").attr('data'));
                    	 var iCode=$(obj).find("#code").attr('data');
                    	 if(item==2&&$('#dockBackFlowNotice').length!=0&&$("#transportId").text()){
                    		 dialog.remove();
                    		 $('#dockBackFlowNotice').click();
                    	 }else if(item==3&&$('#storeBackFlowNotice').length!=0&&$("#transportId").text()){
                    		 dialog.remove();
                    		 $('#dockBackFlowNotice').click();
                    	 }else if(item==11&&$('#zhuanshuNotice').length!=0&&$("#transportId").text()){
                    		 dialog.remove();
                    		 $('#zhuanshuNotice').click();
                    	 }else if(item==12&&$('#backFlowNotice').length!=0&&$("#transportId").text()){
                    		 dialog.remove();
                    		 $('#backFlowNotice').click();
                    	 }else if(item==13&&$('#dockNotice').length!=0){
                    		 dialog.remove();
                    		 $('#dockNotice').click();
                    	 }else if(item==14&&$('#dynamicNotice').length!=0){
                    		 dialog.remove();
                    		 $('#dynamicNotice').click();
                    	 }else if(item==15&&$("#dockshipNotice").length!=0){
                    		 dialog.remove();
                    		 $("#dockshipNotice").click();
                    	 }else if(item==16&&$("#dynamicshipNotice").length!=0){
                    		 dialog.remove();
                    		 $("#dynamicshipNotice").click();
                    	 }else{
//                    		 initContent(obj,$(obj).find("#code").attr('data'));
//                    		 dialog.remove();
                    		 init(item,iCode,transportId,taskMsg,batchId);
                    	 }
                     }            		
            	}
            });   
		});
		
		//通知单特殊选项处理
		$(obj).find(".spBtn").each(function(){
			$thisBtn=$(this);
			$thisBtn.unbind('click');
			$thisBtn.click(function(){
				if($(obj).find("#code").attr('flowstatus')==1||$(obj).find(".sureDiv").css("display")=='none'||$(obj).find(".modifyNotice").attr('key')=='1'){
					$this=$(this);
				var id=$this.attr('data');	
				var itemId=$this.attr('id');
				var ids=itemId.substring(0,1);
				if(($(obj).find("#"+ids+"1").is(":checked")^$(obj).find("#"+ids+"3").is(":checked"))&&!$(obj).find("#"+ids+"2").is(":checked")){
					var content="";
					if($(obj).find("#"+ids+"1").is(":checked")){
						content=ids+"1";
					}else{
						content=ids+"3";
					}
					var data={
							 "code":$(obj).find("#code").attr('data'),
							 "contentType":$this.attr('key'),
							 "content":content,
							 "sureUserId":muserId,
							 "sureTime":(new Date().getTime()/1000).toFixed(0),
							 "type":item,
							 "status":1,
							 "flag":1
							 };
					
					var url="";
					if(id){
						data.id=id;
						url=config.getDomain()+"/notify/update";
					}else{
						url=config.getDomain()+"/notify/add";
					}
				
				$.ajax({
					type:"post",
					url:url,
					data:data,
					dataType:"json",
					success:function(data){
						config.unload();
						if(data.code=='0000'){
						//初始化
							if($(obj).find(".modifyNotice").attr('key')==0){
								initContent(obj,$(obj).find("#code").attr('data'),item);
							}
						$('body').message({
							type : 'success',
							content : '提交成功'
						});
						if($(obj).find(".modifyNotice").attr('key')=='0'){
							$this.hide();
						}
					}else{
						$('body').message({
							type : 'error',
							content : '提交失败'
						});	
					}
					}
				});
			}else{
				$('body').message({
					type:'warning',
					content:'正确选择才可以提交'
				});
			}}else{
				$("body").message({
					type:"warning",
					content:"还没有确认通知单!"
				});
			}
				});
		});
		//通知单的保存提交
		$(obj).find("#saveSureTask,#submitSureTask,#saveBeforeTask,#submitBeforeTask,#saveInTask,#submitInTask,#saveAfterTask,#submitAfterTask").unbind('click');
		$(obj).find("#saveSureTask,#submitSureTask,#saveBeforeTask,#submitBeforeTask,#saveInTask,#submitInTask,#saveAfterTask,#submitAfterTask").click(
				function(){
					if($(obj).find("#code").attr('flowstatus')==1||$(obj).find(".sureDiv").css("display")=='none'||$(this).attr("id")=="saveSureTask"||$(this).attr("id")=="submitSureTask"||$(obj).find(".modifyNotice").attr('key')=='1'){
					$this=$(this);
					var contentType=$this.attr('key');//提交类型	
					var status=$this.attr('status');//0，保存1，提交
					//如果是修改status=1
					if($(obj).find(".modifyNotice").attr('key')=='1'){
						status=1;
					}
					
					var id=$this.attr('data');//null,添加，id,更新
					var data=null;
					//正常
					if(status==1&&contentType!=0){//提交时进行校验 调度确认不校验
						if(valicateCheckBox($this.closest(".form-body").find("table"))){
						}else{ return;}
					}  
					//修改情况
					if(status==0&&$(obj).find(".modifyNotice").attr('key')=='1'&&contentType!=0){
						if(valicateCheckBox($this.closest(".form-body").find("table"))){
						}else{ return;}
					}
					
					   //填写登陆人
					   $this.parent().find('input').val(muserName);
					   $this.parent().find('input').attr('data',muserId);
						var content='';
						if(contentType!=0){//是检查项的
							content=getIdsCheckBox($this.closest(".form-body").find("table"));
							data= {
								"id":id,
								"code":$(obj).find("#code").attr('data'),
								"type":item,
								"contentType" :contentType,
								"content":content,
								"sureUserId":muserId,
								"sureTime":util.formatLong(util.currentTime(1)),
								"status":status,
								"flag":0
							};
							if($(obj).find(".modifyNotice").attr('key')=='1'){
								data.flag=2;
								data.description=getChangeCheckBoxItems(contentType);
							}
							
							if(status==1&&$(obj).find(".modifyNotice").attr('key')!='1'){
								if(contentType==1){
									data.state=2;
								}else if(contentType==2){
									data.state=3;
								}else if(contentType==3){
									data.state=4;
								}
							}
							
						}else if(contentType==0){
							content=getSpecialContent(obj,item);
							data={
								"id":id,
								"code":$(obj).find("#code").attr('data'),
								"type":item,
								"contentType":contentType,	
								"content":content,
								"createTime":util.formatLong($(obj).find("#createTime").val()),
								"createUserId":muserId,
								"sureUserId":muserId,
								"sureTime":util.formatLong(util.currentTime(1)),
								"status":status,
								"flag":0
							}
							if(graph){
								data.firPFDSvg=getSvg(graph);
							}
							if(graph1){
								data.secPFDSvg=getSvg(graph1);
							}
							if((item==2||item==3||item==12)&&!$(obj).find("#transportId").text()){
								data.firPFDSvg=getSVG();
							}
							
							//通知单状态
							if($(obj).find(".modifyNotice").attr('key')!='1'){
								data.state=status;
							}
							
						}
						var url="";
						if(id){
							url=config.getDomain() + "/notify/update";
						}else{
							url=config.getDomain() + "/notify/add";
						}
						config.load();
						$.ajax({
							type : "post",
							url : url,
							data : data,
							dataType : "json",
							success:function(data){
								config.unload();
								if(data.code=='0000'){
									$('body').message({
										type:"success",
										content:msgContent(status,true)
									});
									initContent(obj,$(obj).find("#code").attr('data'),item);
									if(contentType==0){
										//确认后就刷新数据,列表添加一条数据
										if($('[data-role="notifyGrid"]').getGrid()){
											$('[data-role="notifyGrid"]').getGrid().refresh();
										}
										if(transportId){
											saveNoticeCode(transportId,item,$(obj).find("#code").attr('data'));
										}
										if(batchId){
											saveNoticeCode($(obj).find('#transportId').text(),item,$(obj).find("#code").attr('data'));
										}
									}
								}else{
									$('body').message({
										type:"error",
										content:msgContent(status,false)
									});
								}
								}
				               });
					}else{
						$("body").message({
							type:"warning",
							content:"还没有确认通知单!"
						});
					}
				});
		//打开操作界面
		$(obj).find(".openNext").click(function(){
			$(obj).find(".notice-check").show();
			$(obj).find(".dialog-warning1,.dialog-warning2,.openNextDiv").hide();
		});
		//checkbox点击事件
		$(obj).find("input[type='checkbox']").click(function(){
			$this=$(this);
			if($this.is(":checked")){
               $this.parent('label').siblings().find("input[type='checkbox']").attr("checked",false);           				
			}
		});
		
		
		//修改
		$(obj).find("#modifyNotice").unbind('click');
		$(obj).find("#modifyNotice").click(function(){
			$this=$(this);
			var key=$(obj).find(".modifyNotice").attr('key');
			if(key=='0'){
				dialog.find("#createTime").removeAttr('disabled');
				$(obj).find(".modifyNotice").attr('key','1');
				$this.text('确认修改');
				$(obj).find(".isModify").show();
				$(obj).find(".isNoModify").hide();
				initContent(obj,$(obj).find("#code").attr('data'),item);
			}else if(key=='1'){
				dialog.find("#createTime").attr('disabled','disabled');
				$(obj).find(".modifyNotice").attr('key','0');
				$this.text('修改');
				$(obj).find(".isModify").hide();
				$(obj).find(".isNoModify").show();
				initContent(obj,$(obj).find("#code").attr('data'),item);
			}
			
		});
	};  
	    //全选
	    function initCheckAll(obj){
	    	//初始化全选按钮
			$(obj).find("#beforeCheckAll,#inCheckAll,#afterCheckAll").click(function(){
				$this=$(this);
				var str=$this.attr('name');
				if($this.find("i").attr("class")=="fa fa-square-o"){
					$(obj).find("."+str+"Table input[type='checkbox']").each(function(){
						$item=$(this);	
						if($item.attr('id').indexOf('1')!=-1){
							$item.attr('checked',true);
						}else if($item.attr('id').indexOf('2')!=-1){
							$item.attr('checked',false);
						}
							});
					$this.find("i").removeClass();
					$this.find("i").attr("class","fa fa-check-square-o");
				}else{
					$(obj).find("."+str+"Table input[type='checkbox']").each(function(){
						$item=$(this);		
						$item.attr('checked',false);
							});
					$(this).find("i").removeClass();
					$(this).find("i").attr("class","fa fa-square-o");
				}
			});	
	    }
	    //清空选择
	    function  cleanCheckBox(obj){
	    	
	    	var str=$(obj).attr('name');
	    	dialog.find("."+str+"Table input[type='checkbox']").each(function(){
				$item=$(this);	
				if(!$item.closest("div").hasClass("spCB")){
					$item.attr('checked',false);
				}
					});
	    };
	    
		//初始化checkbox
		function initCheckBox(obj,data){
			if(data&&data!=""){
           	var dataArray=data.split(",");	
           	for(var i=0;i<dataArray.length;i++)
			$(obj).find("#"+dataArray[i]).attr("checked",true);
			}
		};
		
		//校验checkbox
		function valicateCheckBox(obj){
			var result=true;
			$(obj).find("input[type='checkbox']").each(function(){
				$this=$(this);
				if($this.attr("id").indexOf('1')!=-1&&!$this.closest("div").hasClass("spCB")){
					if($(obj).find("#"+$this.attr("id").replace("1","3")).length!=0){
						if(!$this.is(':checked')^$(obj).find("#"+$this.attr("id").replace("1","3")).is(':checked')){
							result=false;
							$('body').message({type:'warning',content:'请正确选择！'});
							return result;
						}
					}else{
					if(!$this.is(':checked')){
						result=false;
						$('body').message({type:'warning',content:'请正确选择！'});
						return result;
					}
					}
				}
				else if($this.attr("id").indexOf('2')!=-1&&!$this.closest("div").hasClass("spCB")){
					if($this.is(':checked')){
						result=false;
						$('body').message({type:'warning',content:'请正确选择！'});
						return result;
					}
				}
			}
			);
			return result;
		};
		
		//获取选中的checkbox id的集合
		function getIdsCheckBox(obj){
			var  content="";
			$(obj).find("input[type='checkbox']").each(function(){
				$this=$(this);
				if($this.is(':checked')&&!$this.closest("div").hasClass("spCB")){
					content+=$this.attr("id")+",";
				}else if($this.closest("div").hasClass("spCB")){
					$this.attr("checked",false);
				}
			}
			);
			return content.substring(0,content.length-1);
		};
		
		
		//处理通知的结果
		function getNoticeResult(obj,data,btn,isHasEmpty,isInitCheckBox){
			dialog.find("#save"+btn).parent().find("input").val("");
			//初始化选项
			if(isInitCheckBox){
			if(data.contentType==1){
				if(data.flag!=1){
					cleanCheckBox(dialog.find("#beforeCheckAll"));
				dialog.find("#beforeCheckAll").attr("data",data.content);//保存最新的
				}
				initCheckBox(dialog.find(".beforeTable"),data.content);
			}else if(data.contentType==2){
				if(data.flag!=1){
					cleanCheckBox(dialog.find("#inCheckAll"));
				dialog.find("#inCheckAll").attr("data",data.content);
				}
				initCheckBox(dialog.find(".inTable"),data.content);
			}else if(data.contentType==3){
				if(data.flag!=1){
					cleanCheckBox(dialog.find("#afterCheckAll"));
				dialog.find("#afterCheckAll").attr("data",data.content);
				}
				initCheckBox(dialog.find(".afterTable"),data.content);
			  }}
			
			if(data.status==1&&(data.flag==0||data.flag==2)){//提交
				var jobName='作业人员：';
				if(data.contentType==0){//更该岗位名称
					jobName='调度：';
				}
				if(data.flag==2){
					jobName='修改人员：'
				}
			if(isHasEmpty==undefined||isHasEmpty){	
			$(obj).empty();//清空
			}
			//初始化作业人员信息
			if(data.contentType==0){//调度
				dialog.find("#code").attr('flowstatus','1');
				$(obj).append('<div class="form-group" style="margin-top:5px;">'
						+'<label class="col-md-2 control-label"><strong>'+jobName+'</strong></label>'
						+'<label class="col-md-2 control-label" style="text-align:left;">'+data.sureUserName+'</label>'
//						+'<label class="col-md-2 control-label" style="text-align:right;"><strong>日期:</strong></label>'
//						+'<label class="col-md-4 control-label" style="text-align:left;">'+data.sureTime+'</label>'
						+'</div>');
				
			}else{//检查
				if(data.flag==2){
					$(obj).append('<div class="form-group col-md-12" style="margin-top:5px;">'
							+'<label class="col-md-2 control-label"><strong>'+jobName+'</strong></label>'
							+'<label class="col-md-2 control-label" style="text-align:left;">'+data.sureUserName+'</label>'
							+'<label class="col-md-1 control-label" style="text-align:right;padding-left:0px;"><strong>日期:</strong></label>'
							+'<label class="col-md-4 control-label" style="text-align:left;">'+util.getSubTime(data.sureTime,2)+'</label>'
							+'<label class="col-md-3 control-label" style="text-align:left;">'+util.isNull(data.description)+'</label>'
							+'</div>');	
				}else{
					$(obj).append('<div class="form-group col-md-12" style="margin-top:5px;">'
							+'<label class="col-md-2 control-label"><strong>'+jobName+'</strong></label>'
							+'<label class="col-md-2 control-label" style="text-align:left;">'+data.sureUserName+'</label>'
							+'<label class="col-md-1 control-label" style="text-align:right; padding-left:0px;"><strong>日期:</strong></label>'
							+'<label class="col-md-4 control-label" style="text-align:left;">'+util.getSubTime(data.sureTime,2)+'</label>'
							+'</div>');	
				}
			}
			//移除操作人员id
			dialog.find("#save"+btn).removeAttr('data');
			dialog.find("#submit"+btn).removeAttr('data');
			//隐藏按钮
			if(dialog.find(".modifyNotice").attr('key')=='0'){
			if(data.contentType==0){
					dialog.find("#createTime").attr('disabled','disabled');
					dialog.find(".sureDiv").hide();
				dialog.find("#save"+btn).attr('data',data.id);
			}else if(data.contentType==1){
				dialog.find(".beforeDiv").hide();
			}else if(data.contentType==2){
				
			}else if(data.contentType==3){
				if(data.type==17){
					dialog.find("#checkIsFinish").hide();
				}
				dialog.find(".openNext").click();
				dialog.find("#modifyNotice").show();
				dialog.find(".inDiv").hide();
				dialog.find(".afterDiv").hide();
			  }
			}
			
			if(data.contentType==0){
			dialog.find("#save"+btn).attr('data',data.id);
		}
		}else if(data.flag==0){//保存
			//初始化人员id,名字
			dialog.find("#save"+btn).parent().find("input").val(data.sureUserName);
			dialog.find("#save"+btn).attr('data',data.id);
			dialog.find("#submit"+btn).attr('data',data.id);
		}else if(data.flag==1){//特殊处理
			//保存id 到控件上
			dialog.find("."+data.content.substring(0,1)+"Div>button").attr('data',data.id);
			dialog.find("."+data.content.substring(0,1)+"Div>label").remove();
			if(dialog.find(".modifyNotice").attr('key')=='0'){
			dialog.find("."+data.content.substring(0,1)+"Div>button").hide();
			dialog.find("."+data.content.substring(0,1)+"Div").append('<label class="control-label isNoModify" style="text-align:left;color:#996699">'+data.sureUserName+'</label>');
			}
		}
		};
	
		/**消息*/
		function msgContent(item,isSucceed){
			if(isSucceed){
				if(item==0){
					return "保存成功";
				}else if(item==1){
					return "提交成功";
				}else if(item==2){
					return "删除成功";
				}
			}else{
				if(item==0){
					return "保存失败";
				}else if(item==1){
					return "提交失败";
				}else if(item==2){
					return "删除失败";
				} 			
			}
		};
		
		//初始化不同通知单数据
		function initSpecialContent(obj,type,content){
			var contentData=null;
			if(type==1||type==10){
				contentData=JSON.parse(content);
			}else{
				var mContentData=JSON.stringify(content);
				mContentData=mContentData.substring(1,mContentData.length-1);
				mContentData="{"+mContentData.replace(/{\\"/g,"\"").replace(/\\":\\"/g,"\":\"").replace(/\\"\,\\"/g,"\"\,\"").replace(/\\"}/g,"\"")+"}";
				contentData=eval("("+mContentData+")");
			}
		  if(type==0){
			  initXml(graph,contentData.pipingTask);
			  initXml(graph1,contentData.pipingState);
			  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }
		  else if(type==1){
			  $(obj).find("#taskMsg").val(contentData.taskMsg);
			  $(obj).find("#tankId").val(contentData.tankId);
			  $(obj).find("#tankCB").attr('checked',contentData.tankCB);
			  $(obj).find("#tubeId").val(contentData.tubeId);
			  $(obj).find("#tubeCB").attr('checked',contentData.tubeCB);
			  $(obj).find("#tankT").val(contentData.tankT);
			  $(obj).find("#ctrlNum").val(util.isNull(contentData.ctrlNum));
			  $(obj).find("#tubeCBA").attr('checked',contentData.tubeCBA);
			  $(obj).find("#tankStartT").val(contentData.tankStartT);
			  $(obj).find("#tankStartTCB").attr('checked',contentData.tankStartTCB);
			  $(obj).find("#tubeStartT").val(contentData.tubeStartT);
			  $(obj).find("#tubeStartTCB").attr('checked',contentData.tubeStartTCB);
			  util.initTimeVal($(obj).find("#startTime"),contentData.startTime);
			  util.initTimeVal($(obj).find("#endTime"),contentData.endTime);
			  $(obj).find("#hourTime").text(util.isNull(contentData.hourTime,0));
		  }
		  else if(type==2){
			  if(!$(obj).find("#taskMsg").val()||$(obj).find("#code").attr('flowstatus')==1){
				  $(obj).find("#taskMsg").val(contentData.taskMsg);
				  if(contentData.transportId){
				  $(obj).find("#contentDiv").empty();
				  $(obj).find("#contentDiv").append(contentData.contentDivSVG.replace(/\\/g,""));
				  $(obj).find("#transportId").text(contentData.transportId);
//				  $.ajax({
//						type : "post",
//						url : config.getDomain() + "/inboundoperation/list",
//						data : {
//							"id":contentData.transportId,
//							"transportIds" :contentData.transportId,
//							"result":4
//						},
//						dataType : "json",
//						success:function(data){
//							config.unload();
//							var svg=data.data[0].svg;
//							if(svg){
//								$(obj).find("#contentDiv").empty();
//								$(obj).find("#contentDiv").append(svg);
//							}
//						}
//					});
				  }else{
//					  initXml(graph,contentData.contentDiv);
					  $(obj).find("#toolbarContainer,#graphContainer").empty();
					  flow(contentData.contentDiv,true,true,obj,false,false,"",null,null) ;
				  }
			  }
		  }
		  else if(type==3){
			  if(!$(obj).find("#taskMsg").val()||$(obj).find("#code").attr('flowstatus')==1){
			  $(obj).find("#taskMsg").val(contentData.taskMsg);
			  if(contentData.transportId){
			  $(obj).find("#contentDiv").empty();
			  $(obj).find("#contentDiv").append(contentData.contentDivSVG.replace(/\\/g,""));
			  $(obj).find("#transportId").text(contentData.transportId);
//			  $.ajax({
//					type : "post",
//					url : config.getDomain() + "/inboundoperation/list",
//					data : {
//						"id":contentData.transportId,
//						"transportIds" :contentData.transportId,
//						"result":4
//					},
//					dataType : "json",
//					success:function(data){
//						config.unload();
//						var svg=data.data[0].svg;
//						if(svg){
//							$(obj).find("#contentDiv").empty();
//							$(obj).find("#contentDiv").append(svg);
//						}
//					}
//				});
			  }else{
//				  initXml(graph,contentData.contentDiv);
				  $(obj).find("#toolbarContainer,#graphContainer").empty();
				  flow(contentData.contentDiv,true,true,obj,false,false,"",null,null) ;
			  }
			  }
		  }
		  else if(type==4){
			  $(obj).find("#tubeId").val(contentData.tubeId);
			  $(obj).find("#productId").val(contentData.productId);
			  initXml(graph,contentData.contentDiv);
			  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }
		  else if(type==5){
			  $(obj).find("#tubeId").val(contentData.tubeId);
			  $(obj).find("#productId").val(contentData.productId);
			  initXml(graph,contentData.contentDiv);
			  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }
		  else if(type==6){
			  $(obj).find("#taskMsg").val(contentData.taskMsg);
			  initXml(graph,contentData.contentDiv);
			  initXml(graph1,contentData.beforeState);
			  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }
		  else if(type==7){
			  $(obj).find("#taskMsg").val(contentData.taskMsg);
			  initXml(graph,contentData.contentDiv);
			  initXml(graph1,contentData.beforeState);
			  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }
		  else if(type==8){
			  $(obj).find("#tankId").val(contentData.tankId);
//			  $(obj).find("#tubeId").val(contentData.tubeId);
			  $(obj).find("#productId").val(contentData.productId);
			  $(obj).find("#goodsNum").val(contentData.goodsNum);
			  $(obj).find("#pupmId").val(contentData.pupmId);
			  initXml(graph,contentData.contentDiv);
			  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }
		  else if(type==9){
			  initXml(graph1,contentData.beforeState);
			  $(obj).find("#taskMsg").val(contentData.taskMsg);
		  }
		  else if(type==10){
			  $(obj).find("#tankId").val(contentData.tankId);
			  $(obj).find("#productId").val(contentData.productId);
			  $(obj).find("#taskMsg").val(contentData.taskMsg);
			  $(obj).find("#scadaLevel").val(contentData.scadaLevel);
			  $(obj).find("#handLevel").val(contentData.handLevel);
		  }
		  else if(type==11){
			  if(!$(obj).find("#taskMsg").val()||$(obj).find("#code").attr('flowstatus')==1){
//				  $(obj).find("#taskMsg").val(contentData.taskMsg);
				  if(contentData.transportId){
				  $(obj).find("#contentDiv").empty();
				  $(obj).find("#contentDiv").append(contentData.contentDivSVG.replace(/\\/g,""));
				  $(obj).find("#transportId").text(contentData.transportId);
//				  $.ajax({
//						type : "post",
//						url : config.getDomain() + "/inboundoperation/list",
//						data : {
//							"id":contentData.transportId,
//							"transportIds" :contentData.transportId,
//							"result":4
//						},
//						dataType : "json",
//						success:function(data){
//							config.unload();
//							var svg=data.data[0].svg;
//							if(svg){
//								$(obj).find("#contentDiv").empty();
//								$(obj).find("#contentDiv").append(svg);
//							}
//						}
//					});
				  }else{
//					  initXml(graph,contentData.contentDiv);
					  $(obj).find("#toolbarContainer,#graphContainer").empty();
					  flow(contentData.contentDiv,true,true,null,false,false,"",null,null) ;
				  }
				  }
			  $(obj).find("#productId").val(contentData.productId);
//			  initXml(graph,contentData.contentDiv);
			  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }
		  else if(type==12){
			  if(!$(obj).find("#taskMsg").val()||$(obj).find("#code").attr('flowstatus')==1){
				  $(obj).find("#taskRequire").val(contentData.taskRequire);
				  $(obj).find("#taskMsg").val(contentData.taskMsg);
				  if(contentData.transportId){
				  $(obj).find("#contentDiv").empty();
				  $(obj).find("#contentDiv").append(contentData.contentDivSVG.replace(/\\/g,""));
				  $(obj).find("#transportId").text(contentData.transportId);
//				  $.ajax({
//						type : "post",
//						url : config.getDomain() + "/inboundoperation/list",
//						data : {
//							"id":contentData.transportId,
//							"transportIds" :contentData.transportId,
//							"result":4
//						},
//						dataType : "json",
//						success:function(data){
//							config.unload();
//							var svg=data.data[0].svg;
//							if(svg){
//								$(obj).find("#contentDiv").empty();
//								$(obj).find("#contentDiv").append(svg);
//							}
//						}
//					});
				  }else{
//					  initXml(graph,contentData.contentDiv);
					  $(obj).find("#toolbarContainer,#graphContainer").empty();
					  flow(contentData.contentDiv,true,true,obj,false,false,"",null,null) ;
				  }
			  }
			  
//			  $(obj).find("#taskMsg").val(contentData.taskMsg);
//			  initXml(graph,contentData.contentDiv);
//			  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }else if(type==13){
			  if(!$(obj).find("#taskMsg").val()||$(obj).find("#code").attr('flowstatus')==1){
			  $(obj).find("#taskMsg").val(contentData.taskMsg);
			  if(contentData.transportId){
			  $(obj).find("#contentDiv").empty();
			  $(obj).find("#contentDiv").append(contentData.contentDiv.replace(/\\/g,""));
			  $(obj).find("#transportId").text(contentData.transportId);
//			  $.ajax({
//					type : "post",
//					url : config.getDomain() + "/inboundoperation/list",
//					data : {
//						"id":contentData.transportId,
//						"transportIds" :contentData.transportId,
//						"result":4
//					},
//					dataType : "json",
//					success:function(data){
//						config.unload();
//						var svg=data.data[0].svg;
//						if(svg){
//							$(obj).find("#contentDiv").empty();
//							$(obj).find("#contentDiv").append(svg);
//						}
//					}
//				});
			  }
			  }
			  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }else if(type==14){
			  if(!$(obj).find("#taskMsg").val()||$(obj).find("#code").attr('flowstatus')==1){
			  $(obj).find("#taskMsg").val(contentData.taskMsg);
			  if(contentData.transportId){
			  $(obj).find("#contentDiv").empty();
			  $(obj).find("#contentDiv").append(contentData.contentDiv.replace(/\\/g,""));
			  
			  $(obj).find("#transportId").text(contentData.transportId);
//			  $.ajax({
//					type : "post",
//					url : config.getDomain() + "/inboundoperation/list",
//					data : {
//						"id":contentData.transportId,
//						"transportIds" :contentData.transportId,
//						"result":4
//					},
//					dataType : "json",
//					success:function(data){
//						config.unload();
//						var svg=data.data[0].svg;
//						if(svg){
//							$(obj).find("#contentDiv").empty();
//							$(obj).find("#contentDiv").append(svg);
//						}
//					}
//				});
			  }
			  }
			  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }else if(type==15){
			  if(!$(obj).find("#taskMsg").val()||$(obj).find("#code").attr('flowstatus')==1){
				  $(obj).find("#taskMsg").val(contentData.taskMsg);
				  if(contentData.transportId){
				  $(obj).find("#contentDiv").empty();
				  $(obj).find("#contentDiv").append(contentData.contentDiv.replace(/\\/g,""));
				  $(obj).find("#transportId").text(contentData.transportId);
//				  $.ajax({
//						type : "post",
//						url : config.getDomain() + "/inboundoperation/list",
//						data : {
//							"id":contentData.transportId,
//							"transportIds" :contentData.transportId,
//							"result":4
//						},
//						dataType : "json",
//						success:function(data){
//							config.unload();
//							var svg=data.data[0].svg;
//							if(svg){
//								$(obj).find("#contentDiv").empty();
//								$(obj).find("#contentDiv").append(svg);
//							}
//						}
//					});
				  }
				  }
				  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }else if(type==16){
			  if(!$(obj).find("#taskMsg").val()||$(obj).find("#code").attr('flowstatus')==1){
				  $(obj).find("#taskMsg").val(contentData.taskMsg);
				  if(contentData.transportId){
				  $(obj).find("#contentDiv").empty();
				  $(obj).find("#contentDiv").append(contentData.contentDiv.replace(/\\/g,""));
				  $(obj).find("#transportId").text(contentData.transportId);
//				  $.ajax({
//						type : "post",
//						url : config.getDomain() + "/inboundoperation/list",
//						data : {
//							"id":contentData.transportId,
//							"transportIds" :contentData.transportId,
//							"result":4
//						},
//						dataType : "json",
//						success:function(data){
//							config.unload();
//							var svg=data.data[0].svg;
//							if(svg){
//								$(obj).find("#contentDiv").empty();
//								$(obj).find("#contentDiv").append(svg);
//							}
//						}
//					});
				  }
				  }
				  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }else if(type==17){
			  $(obj).find("#taskMsg").val(contentData.taskMsg);
			  $(obj).find("#toolbarContainer,#graphContainer").empty();
			  flow(contentData.contentDiv,true,true,obj,true,true,"",null,null) ;
			  $(obj).find("#taskRequire").val(contentData.taskRequire);
		  }
		};
		//获取不同通知单数据
		function getSpecialContent(obj,type){
			var content=null;
			if(type==0){
				content={
						pipingTask:getXml(graph),
						pipingTaskSVG:getSvg(graph),
						pipingState:getXml(graph1),
						taskRequire:$(obj).find("#taskRequire").val()
				}
			}
			  else if(type==1){
				  content={
						'taskMsg':$(obj).find("#taskMsg").val(),
						'tankId':$(obj).find("#tankId").val(),
						'tankCB':$(obj).find("#tankCB").is(':checked'),
						'tubeId':$(obj).find("#tubeId").val(),
						'tubeCB':$(obj).find("#tubeCB").is(':checked'),
						'tankT':$(obj).find("#tankT").val(),
						'ctrlNum':$(obj).find("#ctrlNum").val(),
						'tubeCBA':$(obj).find("#tubeCBA").is(':checked'),
						'tankStartT':$(obj).find("#tankStartT").val(),
						'tankStartTCB':$(obj).find("#tankStartTCB").is(':checked'),
						'tubeStartT':$(obj).find("#tubeStartT").val(),
						'tubeStartTCB':$(obj).find("#tubeStartTCB").val(),
						'startTime':util.getTimeVal($(obj).find("#startTime")),
						 'endTime':util.getTimeVal($(obj).find("#endTime")),
						 'hourTime':$(obj).find("#hourTime").text()
				  }
			  }
			  else if(type==2){
				  content={
						  'taskMsg':$(obj).find("#taskMsg").val(),
						  'contentDiv':getXml(),
						  'contentDivSVG':$(obj).find("#contentDiv").html().trim(),
						  'transportId':$(obj).find("#transportId").text()
				  }
			  }
			  else if(type==3){
				  content={
						  'taskMsg':$(obj).find("#taskMsg").val(),
						  'contentDiv':getXml(),
						  'contentDivSVG':$(obj).find("#contentDiv").html().trim(),
						  'transportId':$(obj).find("#transportId").text()
				  }
			  }
			  else if(type==4){
				  content={
						  'tubeId':$(obj).find("#tubeId").val(),
						  'productId':$(obj).find("#productId").val(),
						  'contentDiv':getXml(graph),
						  'contentDivSVG':getSvg(graph),
						  'taskRequire':$(obj).find("#taskRequire").val()
				  }
			  }
			  else if(type==5){
				  content={
						  'tubeId':$(obj).find("#tubeId").val(),
						  'productId':$(obj).find("#productId").val(),
						  'contentDiv':getXml(graph),
						  'contentDivSVG':getSvg(graph),
						  'taskRequire':$(obj).find("#taskRequire").val()
				  }
			  }
			  else if(type==6){
				  content={
						  'taskMsg':$(obj).find("#taskMsg").val(),
						  'contentDiv':getXml(graph),
						  'contentDivSVG':getSvg(graph),
						  'beforeState':getXml(graph1),
						  'taskRequire':$(obj).find("#taskRequire").val()
				  }
			  }
			  else if(type==7){
				  content={
						  'taskMsg':$(obj).find("#taskMsg").val(),
						  'contentDiv':getXml(graph),
						  'contentDivSVG':getSvg(graph),
						  'beforeState':getXml(graph1),
						  'taskRequire':$(obj).find("#taskRequire").val()
				  }
			  }
			  else if(type==8){
				   content={
						    'tankId':$(obj).find("#tankId").val(),
							'productId':$(obj).find("#productId").val(),
							'goodsNum':$(obj).find("#goodsNum").val(),
							'pupmId':$(obj).find("#pupmId").val(),
							'contentDiv':getXml(graph),
							'contentDivSVG':getSvg(graph),
							'taskRequire':$(obj).find("#taskRequire").val(),
				     }
			  }
			  else if(type==9){
				  content={
						  'taskMsg':$(obj).find("#taskMsg").val(),
						  'beforeState':getXml(graph1),
				  }
			  }
			  else if(type==10){
				  content={
						  'tankId':$(obj).find("#tankId").val(),
						  'productId':$(obj).find("#productId").val(),
						  'taskMsg':$(obj).find("#taskMsg").val(),
						  'scadaLevel':$(obj).find("#scadaLevel").val(),
						  'handLevel':$(obj).find("#handLevel").val()
				  }
			  }
			  else if(type==11){
				  content={
						  'productId':$(obj).find("#productId").val(),
						  'contentDiv':getXml(graph),
						  'contentDivSVG':getSvg(graph),
						  'taskRequire':$(obj).find("#taskRequire").val(),
				  }
			  }
			  else if(type==12){
				  content={
						  'taskMsg':$(obj).find("#taskMsg").val(),
						  'contentDiv':getXml(),
						  'contentDivSVG':$(obj).find("#contentDiv").html().trim(),
						  'taskRequire':$(obj).find("#taskRequire").val(),
						  'transportId':$(obj).find("#transportId").text()
				  }
			  }
			  else if(type==13){
				  content={
						  'taskMsg':$(obj).find("#taskMsg").val(),
						  'contentDiv':$(obj).find("#contentDiv").html().trim(),
						  'taskRequire':$(obj).find("#taskRequire").val(),
						  'transportId':$(obj).find("#transportId").text()
				  }
			  }
			  else if(type==14){
				  content={
						  'taskMsg':$(obj).find("#taskMsg").val(),
						  'contentDiv':$(obj).find("#contentDiv").html().trim(),
						  'taskRequire':$(obj).find("#taskRequire").val(),
						  'transportId':$(obj).find("#transportId").text()
				  } 
			  }
			  else if(type==15){
				  content={
						  'taskMsg':$(obj).find("#taskMsg").val(),
						  'contentDiv':$(obj).find("#contentDiv").html().trim(),
						  'taskRequire':$(obj).find("#taskRequire").val(),
						  'transportId':$(obj).find("#transportId").text()	  
				  }
			  }else if(type==16){
				  content={
						  'taskMsg':$(obj).find("#taskMsg").val(),
						  'contentDiv':$(obj).find("#contentDiv").html().trim(),
						  'taskRequire':$(obj).find("#taskRequire").val(),
						  'transportId':$(obj).find("#transportId").text()	  
				  }
			  }else if(type==17){
				  content={
						  'taskMsg':$(obj).find("#taskMsg").val(),
						  'contentDiv':getXml(),
						  'contentDivSVG':getSVG(),
						  'taskRequire':$(obj).find("#taskRequire").val()
				  }
			  }
			return JSON.stringify(content);
		}
		
		function initSpecialBtn(obj,type){
			  graph=null;
			  graph1=null;
			  if(type==0){
				 graph=noticeFlow($(obj).find("#graphContainer"),$(obj).find("#toolbarContainer"));
				 graph1=noticeFlow($(obj).find("#graphContainer1"),$(obj).find("#toolbarContainer1"));
				 //复制工艺流程图
				 $(obj).find("#copyGraph").click(function(){
					 initXml(graph1,getXml(graph));
				 });
				 
			  }
			  else if(type==1){
				  util.urlHandleTypeahead("/tank/list",$(obj).find("#tankId"),'code');
			  }
			  else if(type==2){
				  if(!$(obj).find("#transportId").text()){
					  flow(null,true,true,obj,false,true,"",null,null);
				  }
//				  graph=noticeFlow($(obj).find("#graphContainer"),$(obj).find("#toolbarContainer"));
			  }
			  else if(type==3){
				  if(!$(obj).find("#transportId").text()){
				  flow(null,true,true,obj,false,true,"",null,null);
				  }
//				  graph=noticeFlow($(obj).find("#graphContainer"),$(obj).find("#toolbarContainer"));
			  }
			  else if(type==4){
				  graph=noticeFlow($(obj).find("#graphContainer"),$(obj).find("#toolbarContainer"));
				  util.urlHandleTypeaheadAllData("/tube/list",$(obj).find("#tubeId"));
				  util.urlHandleTypeaheadAllData("/product/select",$(obj).find("#productId"),undefined,undefined,function(data){
					  if(data){
						$(obj).find(".tubeId").empty().append('<input class="form-control" maxlength="64" id="tubeId">'); 
						util.urlHandleTypeaheadAllData("/tube/list?productId="+data.id,$(obj).find("#tubeId")); 
					  }
				  });
			  }
			  else if(type==5){
				  graph=noticeFlow($(obj).find("#graphContainer"),$(obj).find("#toolbarContainer"));
				  util.urlHandleTypeaheadAllData("/tube/list",$(obj).find("#tubeId"));
				  util.urlHandleTypeaheadAllData("/product/select",$(obj).find("#productId"),undefined,undefined,function(data){
					  if(data){
						$(obj).find(".tubeId").empty().append('<input class="form-control" maxlength="64" id="tubeId">'); 
						util.urlHandleTypeaheadAllData("/tube/list?productId="+data.id,$(obj).find("#tubeId")); 
					  }
				  });
			  }
			  else if(type==6){
				  graph=noticeFlow($(obj).find("#graphContainer"),$(obj).find("#toolbarContainer"));
					 graph1=noticeFlow($(obj).find("#graphContainer1"),$(obj).find("#toolbarContainer1"));
					 //复制工艺流程图
					 $(obj).find("#copyGraph").click(function(){
						 initXml(graph1,getXml(graph));
					 });
			  }
			  else if(type==7){
				  graph=noticeFlow($(obj).find("#graphContainer"),$(obj).find("#toolbarContainer"));
				  graph1=noticeFlow($(obj).find("#graphContainer1"),$(obj).find("#toolbarContainer1"));
				  //复制工艺流程图
					 $(obj).find("#copyGraph").click(function(){
						 initXml(graph1,getXml(graph));
					 });
			  }
			  else if(type==8){
				  graph=noticeFlow($(obj).find("#graphContainer"),$(obj).find("#toolbarContainer"));
				     util.urlHandleTypeahead("/tank/list",$(obj).find("#tankId"),'code');
					 util.urlHandleTypeahead("/product/select",$(obj).find("#productId"));
					 util.urlHandleTypeahead("/pump/list",$(obj).find("#pupmId"));
			  }
			  else if(type==9){
				  graph1=noticeFlow($(obj).find("#graphContainer1"),$(obj).find("#toolbarContainer1"));
			  }
			  else if(type==10){
				  util.urlHandleTypeahead("/tank/list",$(obj).find("#tankId"),'code');
					 util.urlHandleTypeahead("/product/select",$(obj).find("#productId"));
			  }
			  else if(type==11){
				  if(!$(obj).find("#transportId").text()){
					  flow(null,true,true,obj,false,true,"",null,null);
					  }
//				  graph=noticeFlow($(obj).find("#graphContainer"),$(obj).find("#toolbarContainer"));
				  util.urlHandleTypeahead("/product/select",$(obj).find("#productId"));
			  }
			  else if(type==12){
				  if(!$(obj).find("#transportId").text()){
				  flow(null,true,true,obj,true,true,"",null,null,false);
				  }
//				  graph=noticeFlow($(obj).find("#graphContainer"),$(obj).find("#toolbarContainer"));
			  }else if(type==17){
				  flow(null,true,true,obj,true,true,"",null,null);
			  }
		}
		
		//改变通知单编号
		function changeCode(obj,item){
			if(util.validateFormat($(obj).val())){
			$.ajax({
				type:"post",
				url:config.getDomain()+"/notify/getcodenum?types="+item+"&isCodeNum="+1,
				dataType:'json',
//				data:{'createTime':util.formatLong($(obj).val())},
				success:function(data){
	                 if(data.code=='0000'){				
//					$(obj).parent().find("#code").text("TZD"+item+$(obj).val().replace(/-/g, "").substring(2,10)+data.data[0].codeNum);
	                $(obj).parent().find("#code").text(data.data[0].codeNum);
	                
	                $(obj).parent().find("#code").attr('data',util.getAZ(""+item+"")+''+data.data[0].codeNum);
	                 }
				}
			});
			}};
		//获取修改项item 前 ---1 中---2后---3
			function getChangeCheckBoxItems(item){
				if(item==0){return ""}
				else if(item==1){
					var nowContent=getIdsCheckBox(dialog.find(".beforeTable"));
					var lastContent=dialog.find("#beforeCheckAll").attr("data");
					var ss= getChangeCheckBoxResult(nowContent.split(','),lastContent.split(','));
					if(ss.length>0){
						return "修改项： "+ss;
					}
				}
				else if(item==2){
					var nowContent=getIdsCheckBox(dialog.find(".inTable"));
					var lastContent=dialog.find("#inCheckAll").attr("data");
					var ss= getChangeCheckBoxResult(nowContent.split(','),lastContent.split(','));
					if(ss.length>0){
						return "修改项： "+ss;
					}
				}
				else if(item==3){
					var nowContent=getIdsCheckBox(dialog.find(".afterTable"));
					var lastContent=dialog.find("#afterCheckAll").attr("data");
					if(nowContent&&lastContent){
						var ss= getChangeCheckBoxResult(nowContent.split(','),lastContent.split(','));
						if(ss.length>0){
							return "修改项： "+ss;
						}
					}else {
						return "";
					}
					
				}
			}
			//处理新旧选项对比获取对应选项
			function  getChangeCheckBoxResult(nowContent,lastContent){
				var result="";
				for(var i=0;i<nowContent.length;i++){
					if(nowContent[i]!=lastContent[i]){
                        result+=dialog.find("#"+nowContent[i]).closest('tr').find('h6').text().substring(0,1)+",";	
					}
				}
                 if(result.length>1){
                	 return result.substring(0,result.length-1);
                 }else{
                	 return "";
                 }
			}
			
		/**获取通知单历史记录列表*/
		function  dialogShowLog(obj,type){
			$.get(config.getResource()+ "/pages/inbound/inboundoperation/notice/dialog_message.jsp")
			.done(function(data) {
				var mdialog = $(data);
				//1.接卸（码头）通知单记录，2.接卸（动力班）通知单记录3.打循环通知单记录，4.配管通知单记录
				function initMsg(obj,type){
					var pageSize,pageNo;
			    	 var columns=[{
			    		 title:"序号",
			    		 render:function(item,name,index){
			    			 pageSize=$(obj).getGrid().pageSize;
			   			  pageNo=$(obj).getGrid().pageNo;
			   			 return pageSize*pageNo+index+1;
			    		 }
			    	 },{
			    		 title:"时间",
			    		 name:"createTime"
			    	 },{
			    		 title:"记录",
			    		 name:"content"
			    	 }];
			    	 if($(obj).getGrid()!=null){
			    		 $(obj).getGrid().destory();
			    	 }
			      $(obj).grid({
			    	  identity : 'id',
						columns : columns,
						isShowIndexCol : true,
						isShowPages : true,
						url : config.getDomain() + "/noticelog/list?type="+type
			      }).on();    	 
			     };
				initMsg(mdialog.find("[data-role=msgGrid]"),type);
				
				mdialog.find("#suremsg").on('click',function(){					
					if(util.validateData(mdialog.find("[data-role=msgGrid]"))){
					var data=mdialog.find("[data-role=msgGrid]").getGrid().selectedRows()[0];
					$(obj).parents('table').find('#taskRequire').val(data.content);
					mdialog.remove();
					}
				});
				
				mdialog.modal({
					keyboard : true
				});
			});
		};	
		function saveLog(obj,type){
			if($(obj).parents('table').find("#taskRequire").val()&&$(obj).parents('table').find("#taskRequire").val()!=""){
			config.load();
			$.ajax({
				type:"post",
				url:config.getDomain()+"/noticelog/add",
				data:{
					'type':type,
					'content':$(obj).parents('table').find("#taskRequire").val(),
					'createTime':util.formatLong(util.currentTime(1))
				},
				dataType:"json",
				success:function(data){
					config.unload();
					if(data.code=='0000'){
						$("body").message({
							type:"success",
							content:"保存成功"
						});
					}else{
						$("body").message({
							type:'error',
							content:"保存失败"
						});
					}
				}
			});
			}else{
				$("body").message({
					type:'warning',
					content:"记录不能为空"
				});
			}
		};
		
		//处理流程内的code
		function saveNoticeCode(transportId,item,code){
			var data=null;
			if(item==2){
				data={id:transportId,noticeCodeA:code,type:1}
				if($('#dockBackFlowNotice'))
					$('#dockBackFlowNotice').attr('data',code);
			}
			else if(item==3){
				data={id:transportId,noticeCodeB:code,type:1}
				if($('#storeBackFlowNotice'))
					$('#storeBackFlowNotice').attr('data',code);
			}
			else if(item==11){
				data={id:transportId,noticeCodeA:code,type:2}
				if($('#zhuanshuNotice'))
					$('#zhuanshuNotice').attr('data',code);
			}
			else if(item==12){
				data={id:transportId,noticeCodeA:code,type:3}
				if($('#backFlowNotice'))
					$('#backFlowNotice').attr('data',code);
			}
			else if(item==13){
				data={id:transportId,noticeCodeA:code,type:0}
				if($('#dockNotice'))
					$('#dockNotice').attr('data',code);
			}else if(item==14){
				data={id:transportId,noticeCodeB:code,type:0}
				if($('#dynamicNotice'))
					$('#dynamicNotice').attr('data',code);
			}else if(item==15){
				data={id:transportId,noticeCodeA:code,type:2}
				if($("#dockshipNotice"))
					$("#dockshipNotice").attr('data',code);
			}else if(item==16){
				data={id:transportId,noticeCodeB:code,type:2}
				if($("#dynamicshipNotice"))
					$("#dynamicshipNotice").attr('data',code);
			}
			
			$.ajax({
				type : "post",
				url : config.getDomain() + "/inboundoperation/updatetransportprogram",
				data : data,
				dataType : "json",
				success:function(data){
					if(data.code=='0000'){}
					else{
						$("body").message({
						type:'error',
						content:'保存通知单号失败'
						});
					}
					
				}});
		}
		
/****************************************************************exportXML********************************************************************/		
		var excelNames = [ "配管作业通知单", "苯加热作业通知单","管线清洗作业通知单(码头)", "管线清洗作业通知单(库区)", "扫线作业通知单(码头)", "扫线作业通知单(库区)",
		         			"清罐作业通知单", "储罐放水作业通知单", "储罐开人孔作业通知单", "转输作业通知单", "倒罐作业通知单",
		         			"接卸作业通知单(码头)", "接卸作业通知单(动力班)", "打循环作业通知单(码头)","船发作业通知单(码头)", "船发作业通知单(库区)",
		         			"车发换罐作业通知单","打循环作业通知单(库区)" ];  
		
		//导出exel	
		function exportXML(obj,type){
			var name=util.getNameByEncode(excelNames[type-15]);
			//type 11 码头接卸 12动力班接卸13打循环码头14打循环动力班15配管16苯加热17管线清洗（码头）18管线清洗（库区）19扫线（码头）20扫线（库区）21清罐22储罐放水23储罐开人孔24传输25打回流26码头接卸27动力班接卸
			
			var _dialog=$(obj).parents(".notice");
			var title=new Array();
			var value=new Array();
			var url = config.getDomain()+"/common/exportExcel?type="+type+"&&name="+name+"&&code="+_dialog.find("#code").attr('data');
			if(type==15){//配管作业通知单
				title=['一、配管任务:','二、配管前管线状态:','三、作业要求:','四、注意事项:','五、作业中的风险分析：'];
				value.push("svg1");
				value.push("svg2");
				var taskRequire=_dialog.find("#taskRequire").val().replace(/\#/g,"%23");
				value.push(taskRequire);
				url+="&&t1="+title[0]+"&&v1="+value[0]+"&&t2="+title[1]+"&&v2="+value[1]+"&&t3="+title[2]+"&&v3="+value[2]+"&&t4="+title[3]+"&&t5="+title[4];
			}else if(type==16){//苯加热作业通知单
				title=['作业任务','工艺要求'];
				value.push(_dialog.find("#taskMsg").val().replace(/\#/g,"%23"));
				var content={
						'tankId':_dialog.find("#tankId").val().replace(/\#/g,"%23"),
						'tankCB':_dialog.find("#tankCB").is(':checked'),
						'tubeId':_dialog.find("#tubeId").val().replace(/\#/g,"%23"),
						'tubeCB':_dialog.find("#tubeCB").is(':checked'),
						'tankT':_dialog.find("#tankT").val().replace(/\#/g,"%23"),
						'tubeCBA':_dialog.find("#tubeCBA").is(':checked'),
						'tankStartT':util.isNull(_dialog.find("#tankStartT").val()),
						'tankStartTCB':_dialog.find("#tankStartTCB").is(':checked'),
						'tubeStartT':util.isNull(_dialog.find("#tubeStartT").val()),
						'tubeStartTCB':_dialog.find("#tubeStartTCB").is(':checked'),
						 'startTime':util.getTimeVal(_dialog.find("#startTime")),
						 'endTime':util.getTimeVal(_dialog.find("#endTime")),
						 'hourTime':util.isNull(_dialog.find("#hourTime").text())
				  }
				value.push(JSON.stringify(content));
				url+="&&t1="+title[0]+"&&v1="+value[0]+"&&t2="+title[1]+"&&content="+value[1];
				console.log(url);
			}else if(type==17||type==18){//管线清洗作业通知单
				title=['一、清洗流程：','二、作业要求：'];
				value.push("svg");
				value.push(_dialog.find("#taskRequire").val().replace(/\#/g,"%23"));
				url+="&&t1="+title[0]+"&&v1="+value[0]+"&&t2="+title[1]+"&&v2="+value[1]+"&&tubeName="+_dialog.find("#tubeId").val()+"&&productName="+_dialog.find("#productId").val().replace(/\#/g,"%23");
			}else if(type==19||type==20){//扫线作业通知单
				title=['一、作业任务：  ','二、作业工艺流程： ','三、作业前状态：','四、作业要求：'];
				value.push(_dialog.find("#taskMsg").val().replace(/\#/g,"%23"));
				value.push("svg1");
				value.push("svg2");
				value.push(_dialog.find("#taskRequire").val().replace(/\#/g,"%23"));
				url+="&&t1="+title[0]+"&&v1="+value[0]+"&&t2="+title[1]+"&&v2="+value[1]+"&&t3="+title[2]+"&&v3="+value[2]+"&&t4="+title[3]+"&&v4="+value[3];
			}else if(type==21){//
				title=["一、工艺流程：","二、作业要求："];
				value.push("svg1");
				value.push(_dialog.find("#taskRequire").val().replace(/\#/g,"%23"));
				 content={
						    'tankId':_dialog.find("#tankId").val(),
							'productId':_dialog.find("#productId").val().replace(/\#/g,"%23"),
							'goodsNum':_dialog.find("#goodsNum").val(),
							'pupmId':_dialog.find("#pupmId").val(),
				     }
				value.push(JSON.stringify(content));
				 url+="&&t1="+title[0]+"&&v1="+value[0]+"&&t2="+title[1]+"&&v2="+value[1]+"&&content="+value[2];
				 
			}else if(type==22){
				title=['作业任务','放水前作业状态'];
				value.push(_dialog.find("#taskMsg").val().replace(/\#/g,"%23"));
				value.push("svg1");
				url+="&&t1="+title[0]+"&&v1="+value[0]+"&&t2="+title[1]+"&&v2="+value[1];
			}else if(type==23){
				title=['作业任务','开人孔前储罐状态'];
				value.push(_dialog.find("#taskMsg").val().replace(/\#/g,"%23"));
				content={
						  'tankId':_dialog.find("#tankId").val(),
						  'productId':_dialog.find("#productId").val().replace(/\#/g,"%23"),
						  'scadaLevel':_dialog.find("#scadaLevel").val(),
						  'handLevel':_dialog.find("#handLevel").val()
				  }
				value.push(JSON.stringify(content));
				url+="&&t1="+title[0]+"&&v1="+value[0]+"&&t2="+title[1]+"&&content="+value[1];
			}else if(type==24){
				title=['作业任务','作业工艺流程','作业要求'];
				value.push(_dialog.find("#productId").val().replace(/\#/g,"%23"));
				value.push("svg1");
				value.push(_dialog.find("#taskRequire").val().replace(/\#/g,"%23"));
				url+="&&t1="+title[0]+"&&v1="+value[0]+"&&t2="+title[1]+"&&v2="+value[1]+"&&t3="+title[2]+"&&v3="+value[2];
			}else if(type==25){//倒罐
				title=['作业任务','作业工艺流程','作业要求'];
				value.push(_dialog.find("#taskMsg").val().replace(/\#/g,"%23"));
//				var svg=_dialog.find("#graphContainer").children().html().replace(/(^\s*)|(\s*$)/g, "");
//				svg=svg.substr(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg.substr(5);
				value.push("svg");
				value.push(_dialog.find("#taskRequire").val().replace(/\#/g,"%23"));
				url+="&&t1="+title[0]+"&&v1="+value[0]+"&&t2="+title[1]+"&&v2="+value[1]+"&&t3="+title[2]+"&&v3="+value[2];
			}else if(type==26||type==27){//接卸
				var url = config.getDomain()+'/notify/exportItemExcel?types='+(type-13)+"&name="+name+'&code='
				+_dialog.find("#code").attr('data')+"&transportId="+_dialog.find("#transportId").text()
				+'&taskMsg='+_dialog.find("#taskMsg").val().replace(/\#/g,"%23")+"&taskRequire="+_dialog.find("#taskRequire").val().replace(/\#/g,"%23");
				window.open(url);
			}else if(type==28){//打循环
				title=['作业任务','作业工艺流程'];
				value.push(_dialog.find("#taskMsg").val().replace(/\#/g,"%23"));
//				var svg=_dialog.find("#contentDiv").html().replace(/(^\s*)|(\s*$)/g, "");
//				svg = svg.substr(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg.substr(5) ;
//				value.push(encodeURIComponent(svg));
				value.push(_dialog.find("#transportId").text());
				url+="&&t1="+title[0]+"&&v1="+value[0]+"&&t2="+title[1]+"&&v2="+value[1];
			}else if(type==29){//码头船发接卸
				var docktaskmsg = _dialog.find("#taskMsg").val().replace(/\#/g,"%23") ;
				var taskrequire = _dialog.find("#taskRequire").val().replace(/\#/g,"%23") ;
				var url = config.getDomain()+"/common/exportExcel?type=1&&name=船发出库码头&&t1=作业任务&&v1="+docktaskmsg+"&&t2=作业工艺流程&&tpId="+_dialog.find("#transportId").text()+"&&t3=作业要求&&v3="+taskrequire+"&&code="+_dialog.find("#code").attr('data');
				window.open(url) ;
			}else if(type==30){//动力部船发接卸
				var docktaskmsg = _dialog.find("#taskMsg").val().replace(/\#/g,"%23") ;
				//var svg = dialog.find("#contentDiv").html() ;
				//svg = svg.substr(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg.substr(5) ;
				var taskrequire = _dialog.find("#taskRequire").val().replace(/\#/g,"%23") ;
				var url = config.getDomain()+"/common/exportExcel?type=1&&name=船发出库动力班&&t1=作业任务&&v1="+docktaskmsg+"&&t2=作业工艺流程&&tpId="+_dialog.find("#transportId").text()+"&&t3=作业要求&&v3="+taskrequire+"&&code="+_dialog.find("#code").attr('data');
				window.open(url);
			}else if(type==31){
				var docktaskmsg = _dialog.find("#taskMsg").val().replace(/\#/g,"%23") ;
				var svg = _dialog.find("#graphContainer>div").html() ;
				svg = svg.substr(0,4)+" xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' "+svg.substr(5) ;
				url+="&&t1=作业任务&&v1="+docktaskmsg+"&&t2=作业工艺流程&&v2="+encodeURIComponent(svg);
				window.open(url) ;
			}
			if(type!=26&&type!=27&&type!=29&&type!=30&&type!=31)
			window.open(url) ;
		};
		
		function exportExcelList(){
			var code=$("#codeF").val();
		    var keyWord=$("#keyWord").val();
			var startTime = $("#startTime").val() ;
			var endTime = $("#endTime").val() ;
			var Type=util.isNull($("#type").val(),1);
			var name=util.getNameByEncode(notifyTitles[Type]);
		    var url = config.getDomain()+"/common/exportExcel?Type="+Type+"&&name="+name+"&&code="+code+"&&keyWord="+keyWord+"&&startTime="+startTime+"&&endTime="+endTime;
			window.open(url);
		}
		
		function getDifferTime(){
			$("#hourTime").text(util.getDifferTime(util.getTimeVal($("#startTime")),util.getTimeVal($("#endTime"))));
		}
return{
		init:init,
		changeTab:changeTab,
		initNotifyBtn:initNotifyBtn,
		changeCode:changeCode,//改变code
		initCheckAll:initCheckAll,//全选按钮
		initCheckBox:initCheckBox,//初始化选择
		getIdsCheckBox:getIdsCheckBox,//获取除去特殊情况的通知单的选择的id
		valicateCheckBox:valicateCheckBox,//校验选择
		dialogShowLog:dialogShowLog,//显示历史记录列表
		saveLog:saveLog,//保存历史记录列表
		exportXML:exportXML,
		exportExcelList:exportExcelList,
		getDifferTime:getDifferTime
	};
}();