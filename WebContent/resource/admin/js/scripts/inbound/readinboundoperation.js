var InboundOperation = function() {
	var dialog = null; // 对话框
	var dataGrid = null; // Grid对象
	var clientData=null;//数量确认货主数据
	var tankData=null;//数量确认储罐数据
	var status = "";
	var page = "";
	var pNameHtml = '';
	var pNumHtml = '';
    var itemDetailArrival=null;	
    var choiceItem=null;
	 //显示隐藏控件
	 function openWarning(item){
			if(item==1){
				$(".dialog-warning1").slideToggle("slow");
			}else{
				$(".dialog-warning2").slideToggle("slow");
			}
		}
	 function openHide(obj,item){
			if(item==1){
				$(".dialog-warning1").slideToggle("slow");
				var $this=$(obj);
				if($this.find("i").attr("class")=="fa fa-chevron-left"){
					$this.find("i").removeClass();
					$this.find("i").addClass("fa fa-chevron-down");
				}else if($this.find("i").attr("class")=="fa fa-chevron-down"){
					$this.find("i").removeClass();
					$this.find("i").addClass("fa fa-chevron-left");
				}
				
			}else{
				$(".dialog-warning2").slideToggle("slow");
			}
		}
	 //打开显示货品详情
	function checkgoodsdetail(productId,index) {
			$.get(config.getResource()+ "/pages/inbound/inboundoperation/checkgoodsdetail.jsp")
					.done(function(data) {
						dialog = $(data);
						//处理
						var detaildata=new Array();
						var j=0;
                         var goodsmsg=$('[data-role="inboundoperationGrid"]').getGrid().getAllItems()[index].goodsMsg;						
						for(var i=0;i<goodsmsg.length;i++){
							var item=goodsmsg[i];
							if(item.productId==productId){
								detaildata[j]=goodsmsg[i];
								j++;
							}
						}
						var columns=[{
							title:"货品",
							name:"productName"
						},{
							title:"货主",
							name:"clientName"
						},{
							title:"数量(吨)",
							name:"goodsPlan"
						}];
						dialog.find('[data-role="goodsDetailGrid"]').grid({
							columns:columns,
							isShowIndexCol : false,
							isShowPages : false,
							isUserLocalData:true,
							localData:detaildata
						}).on();
						dialog.find('#save').on('click', function() {
						}).end().modal({
							keyboard : true
						});
					});

	};
     //打开通知单历史记录dialog
	 function dialogShowMsg(){
		 $.get(config.getResource()+ "/pages/inbound/inboundoperation/notice/dialog_message.jsp")
			.done(function(data) {
				dialog = $(data);
				initMsg(dialog.find("[data-role=msgGrid]"));
				dialog.find("#suremsg").on('click',function(){					
					var index=$("[data-role=msgGrid]").getGrid().selectedRowsIndex();
					var $this = $(this);
					if (index.length == 0) {
						$("#dialogGrid").message({
							type : 'warning',
							content : '请选择要修改的入库作业'
						});
						return;
					}
					var data=$("[data-role=msgGrid]").getGrid().selectedRows()[0];
					$(".assignwork").val(data.msg);
					dialog.remove();
				}).end().modal({
					keyboard:true
				});
				
				dialog.modal({
					keyboard : true
				});
			});
	 };
	 //获取通知单历史记录
     function initMsg(obj){
    	 var columns=[{
    		 title:"序号",
    		 name:"id"
    	 },{
    		 title:"时间",
    		 name:"time"
    	 },{
    		 title:"记录",
    		 name:"msg"
    	 }];
    	 if($(obj).getGrid()!=null){
    		
    		 $(obj).getGrid().destory();
    	 }
      $(obj).grid({
    	  identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain() + "/inboundserial/testmsglist"
      }).on();    	 
     };	
	
	// 查看靠泊评估
	function showBerthAssess() {
		$.get(config.getResource()+ "/pages/inbound/inboundoperation/berthassess/get.jsp")
				.done(function(data) {
					dialog = $(data);
					dialog.modal({
						keyboard : true
					});
				});
	}
	;
/*********************************************************通知单*************************************************************/
	// 查看码头接卸作业通知单
	function dialogDockNotify() {
		$.get(config.getResource()+ "/pages/inbound/inboundoperation/notice/dock_notify.jsp")
				.done(function(data) {
					dialog = $(data);
					//初始化通知单
					dialog.find("#transportprogramId").val(dataGrid.getAllItems()[choiceItem].transportprogramId);
					  //拼接作业任务
					var taskmsg="";
					var goodsmsg=dataGrid.getAllItems()[choiceItem].goodsMsg;
					 var ptIds='';
					 var goodsNum=new Array();
					var goodsName=new Array();
					 var goodsPt=new Array();
					 var j=0;
					 if(goodsmsg!=null&&goodsmsg!=""&&goodsmsg.length>0){
						 taskmsg+="入库"
					 for (var i = 0; i < goodsmsg.length; i++) {
							var itemgoodsmsg=goodsmsg[i];
							if(ptIds.indexOf('a'+itemgoodsmsg.productId+'a')==-1){
								ptIds+='a'+itemgoodsmsg.productId+'a';
								goodsPt[j]="a"+itemgoodsmsg.productId;
								goodsNum["a"+itemgoodsmsg.productId]=itemgoodsmsg.goodsPlan;
								goodsName["a"+itemgoodsmsg.productId]=itemgoodsmsg.productName;
								j++;
							}else{
								goodsNum["a"+itemgoodsmsg.productId]=parseFloat(goodsNum["a"+itemgoodsmsg.productId])+parseFloat(itemgoodsmsg.goodsPlan);
							}
						}
					 
					 for (var i = 0; i < goodsPt.length; i++) {
							var pt=goodsPt[i];
							taskmsg+="货品:"+goodsName[pt]+",总量:"+goodsNum[pt]+"吨；";
						}
					 }
					 dialog.find("#docktaskmsg").val(taskmsg);//任务要求
					
					$.ajax({
						type : "post",
						url : config.getDomain() + "/inboundoperation/list",
						data : {
							"id" :dataGrid.getAllItems()[choiceItem].transportprogramId,
							"result":4
						},
						dataType : "json",
						success:function(data){
							var mdata=data.data[0];
							var svg=mdata.svg;
							var vb="viewBox=\"0 -30 800 250\"";
							  svg=svg.replace("px","");
							  svg=svg.replace("px","");
							    svg=svg.replace("svg","svg "+vb+" ");
								dialog.find("#contentDiv").append(svg);
						}
					});
					
					//作业前确认
					dialog.find("#saveBeforeTask").click(function(){
						if(dialog.find("#saveBeforeTask").attr("class")=="btn btn-default"){
							dialog.find("#saveBeforeTask").message({
								type : 'warning',
								content : '已经提交过了'
							}); 	
						return;
						}	
					
						if(dialog.find("#b1").is(':checked')&&dialog.find("#c1").is(':checked')&&dialog.find("#d1").is(':checked')&&dialog.find("#e1").is(':checked')&&dialog.find("#f1").is(':checked')&&dialog.find("#g1").is(':checked')&&dialog.find("#h1").is(':checked')&&dialog.find("#i1").is(':checked')
								&&dialog.find("#j1").is(':checked')&&dialog.find("#k1").is(':checked')&&dialog.find("#l1").is(':checked')&&dialog.find("#m1").is(':checked')&&dialog.find("#n1").is(':checked')&&dialog.find("#o1").is(':checked')){
							$.ajax({
								type : "post",
								url : config.getDomain() + "/inboundoperation/addworkcheck",
								data : {
									"checkType" :5,
									"checUserId":$("#beforeTaskUserId").attr("data"),
									"content":$("#taskrequire").val(),
									"checkTime":(new Date().getTime()/1000).toFixed(0),
									"transportId":dataGrid.getAllItems()[choiceItem].transportprogramId
								},
								dataType : "json",
								success:function(data){
									dialog.find("#saveBeforeTask").removeClass("btn-success");
									dialog.find("#saveBeforeTask").addClass("btn-default");
									alert("提交成功");
								}
							});
						}else{
							alert("全部确认正确才可以提交");
						}
						
					});
							
					//作业中确认
					dialog.find("#saveInTask").click(function(){
						console.log(dialog.find("#p1").is(':checked')+"==="+dialog.find("#q1").is(':checked')+"==="+dialog.find("#r1").is(':checked'));
						
						if(dialog.find("#p1").is(':checked')&&dialog.find("#q1").is(':checked')&&dialog.find("#r1").is(':checked')){
							$.ajax({
								type : "post",
								url : config.getDomain() + "/inboundoperation/addworkcheck",
								data : {
									"checkType" :6,
									"checUserId":$("#inTaskUserId").attr("data"),
									"content":$("#taskrequire").val(),
									"checkTime":(new Date().getTime()/1000).toFixed(0),
									"transportId":dataGrid.getAllItems()[choiceItem].transportprogramId
								},
								dataType : "json",
								success:function(data){
									alert("提交成功");}
							});
						}else{
							alert("全部确认正确才可以提交");
						}
					});
					
					//作业后确认
					dialog.find("#saveAfterTask").click(function(){
						if(dialog.find("#saveAfterTask").attr("class")=="btn btn-default"){
							dialog.find("#saveAfterTask").message({
								type : 'warning',
								content : '已经提交过了'
							}); 	
						return;
						}
						if(dialog.find("#s1").is(':checked')&&dialog.find("#t1").is(':checked')&&dialog.find("#u1").is(':checked')&&dialog.find("#v1").is(':checked')&&dialog.find("#w1").is(':checked')&&dialog.find("#x1").is(':checked')&&dialog.find("#y1").is(':checked')){
							$.ajax({
								type : "post",
								url : config.getDomain() + "/inboundoperation/addworkcheck",
								data : {
									"checkType" :7,
									"checUserId":$("#afterTaskUserId").attr("data"),
									"content":$("#taskrequire").val(),
									"checkTime":(new Date().getTime()/1000).toFixed(0),
									"transportId":dataGrid.getAllItems()[choiceItem].transportprogramId
								},
								dataType : "json",
								success:function(data){
									dialog.find("#saveAfterTask").removeClass("btn-success");
									dialog.find("#saveAfterTask").addClass("btn-default");
									alert("提交成功");
								}
							});
						}else{
							alert("全部确认正确才可以提交");
						}
					});
							
					dialog.modal({
						keyboard : true
					});
				});
	}
	;

	// 查看配管作业通知单
	function dialogPipingNotify(type) {
		$.get(config.getResource()+ "/pages/inbound/inboundoperation/notice/piping_notify.jsp")
				.done(function(data) {
					dialog = $(data);
					var id='';
					//初始化通知单
					if(type==1){
						//接卸
						dialog.find("#transportprogramId").val(dataGrid.getAllItems()[choiceItem].transportprogramId);
						id=dataGrid.getAllItems()[choiceItem].transportprogramId;
					}else{
						//打回流
						dialog.find("#transportprogramId").val(dataGrid.getAllItems()[choiceItem].backflowplanId);
						id=dataGrid.getAllItems()[choiceItem].backflowplanId;
					}
					//工艺流程图
					$.ajax({
						type : "post",
						url : config.getDomain() + "/inboundoperation/list",
						data : {
							"id" :id,
							"result":4
						},
						dataType : "json",
						success:function(data){
							var mdata=data.data[0];
							var svg=mdata.svg;
							var vb="viewBox=\"0 -30 800 250\"";
							  svg=svg.replace("px","");
							  svg=svg.replace("px","");
							    svg=svg.replace("svg","svg "+vb+" ");
								dialog.find("#contentDiv").append(svg);
						}
					});
					
					//初始化管线状态
					var columns=[{
						title:"管名",
						name:"tubeName"
					},{
						title:"前期存储物料",
						name:"productName"
					},{
						title:"使用情况",
						name:"tubeDescription"
					}];
					$('[data-role="tubeGrid"]').grid({
						identity : 'id',
						columns : columns,
						isShowIndexCol : true,
						isShowPages : false,
						url : config.getDomain() + "/inboundoperation/list?id="+id+"&result=12"
					}).on();
					
					
					//作业前确认
					dialog.find("#saveBeforeTask").click(function(){
						if(dialog.find("#saveBeforeTask").attr("class")=="btn btn-default"){
							dialog.find("#saveBeforeTask").message({
								type : 'warning',
								content : '已经提交过了'
							}); 	
						return;
						}	
					
						if(dialog.find("#a1").is(':checked')&&dialog.find("#b1").is(':checked')&&dialog.find("#c1").is(':checked')&&dialog.find("#d1").is(':checked')&&dialog.find("#e1").is(':checked')&&dialog.find("#f1").is(':checked')&&dialog.find("#g1").is(':checked')){
							$.ajax({
								type : "post",
								url : config.getDomain() + "/inboundoperation/addworkcheck",
								data : {
									"checkType" :8,
									"checUserId":$("#beforeTaskUserId").attr("data"),
									"content":$("#taskrequire").val(),
									"checkTime":new Date().getTime()/1000,
									"transportId":id
								},
								dataType : "json",
								success:function(data){
									dialog.find("#saveBeforeTask").removeClass("btn-success");
									dialog.find("#saveBeforeTask").addClass("btn-default");
									alert("提交成功");
								}
							});
						}else{
							alert("全部确认正确才可以提交");
						}
						
					});
							
					//作业后确认
					dialog.find("#saveAfterTask").click(function(){
								if(dialog.find("#saveAfterTask").attr("class")=="btn btn-default"){
									dialog.find("#saveAfterTask").message({
										type : 'warning',
										content : '已经提交过了'
									}); 	
								return;
								}
								if(dialog.find("#m1").is(':checked')&&dialog.find("#n1").is(':checked')&&dialog.find("#o1").is(':checked')&&dialog.find("#p1").is(':checked')){
									$.ajax({
										type : "post",
										url : config.getDomain() + "/inboundoperation/addworkcheck",
										data : {
											"checkType" :9,
											"checUserId":$("#afterTaskUserId").attr("data"),
											"content":$("#taskrequire").val(),
											"checkTime":new Date().getTime()/1000,
											"transportId":id
										},
										dataType : "json",
										success:function(data){
											dialog.find("#saveAfterTask").removeClass("btn-success");
											dialog.find("#saveAfterTask").addClass("btn-default");
											alert("提交成功");
										}
									});
								}else{
									alert("全部确认正确才可以提交");
								}
							});	
					dialog.modal({
						keyboard : true
					});
				});
	}
	;

	// 查看动力班接卸作业通知单
	function dialogDynamicNotify(type) {
		$.get(config.getResource()+ "/pages/inbound/inboundoperation/notice/dynamic_notify.jsp")
				.done(function(data) {
					dialog = $(data);
					var id='';
					//初始化通知单
					if(type==1){
						dialog.find("#transportprogramId").val(dataGrid.getAllItems()[choiceItem].transportprogramId);
                       id=dataGrid.getAllItems()[choiceItem].backflowplanId;						
					}else{
						dialog.find("#transportprogramId").val(dataGrid.getAllItems()[choiceItem].transportprogramId);
						id=dataGrid.getAllItems()[choiceItem].backflowplanId;	
					}
					  //拼接作业任务
					var taskmsg="";
					var goodsmsg=dataGrid.getAllItems()[choiceItem].goodsMsg;
					 var ptIds='';
					 var goodsNum=new Array();
					var goodsName=new Array();
					 var goodsPt=new Array();
					 var j=0;
					 if(goodsmsg!=null&&goodsmsg!=""&&goodsmsg.length>0){
						 taskmsg+="入库"
					 for (var i = 0; i < goodsmsg.length; i++) {
							var itemgoodsmsg=goodsmsg[i];
							if(ptIds.indexOf('a'+itemgoodsmsg.productId+'a')==-1){
								ptIds+='a'+itemgoodsmsg.productId+'a';
								goodsPt[j]="a"+itemgoodsmsg.productId;
								goodsNum["a"+itemgoodsmsg.productId]=itemgoodsmsg.goodsPlan;
								goodsName["a"+itemgoodsmsg.productId]=itemgoodsmsg.productName;
								j++;
							}else{
								goodsNum["a"+itemgoodsmsg.productId]=parseFloat(goodsNum["a"+itemgoodsmsg.productId])+parseFloat(itemgoodsmsg.goodsPlan);
							}
						}
					 
					 for (var i = 0; i < goodsPt.length; i++) {
							var pt=goodsPt[i];
							taskmsg+="货品:"+goodsName[pt]+",总量:"+goodsNum[pt]+"吨；";
						}
					 }
					 dialog.find("#dynamictaskmsg").val(taskmsg);//任务要求
					
					$.ajax({
						type : "post",
						url : config.getDomain() + "/inboundoperation/list",
						data : {
							"id" :id,
							"result":4
						},
						dataType : "json",
						success:function(data){
							var mdata=data.data[0];
							var svg=mdata.svg;
							var vb="viewBox=\"0 -30 800 250\"";
							  svg=svg.replace("px","");
							  svg=svg.replace("px","");
							    svg=svg.replace("svg","svg "+vb+" ");
								dialog.find("#contentDiv").append(svg);
						}
					});
					
					//作业前确认
					dialog.find("#saveBeforeTask").click(function(){
						if(dialog.find("#saveBeforeTask").attr("class")=="btn btn-default"){
							dialog.find("#saveBeforeTask").message({
								type : 'warning',
								content : '已经提交过了'
							}); 	
						return;
						}	
					
						if(dialog.find("#a1").is(':checked')&&dialog.find("#b1").is(':checked')&&dialog.find("#c1").is(':checked')&&dialog.find("#d1").is(':checked')&&dialog.find("#e1").is(':checked')&&dialog.find("#f1").is(':checked')&&dialog.find("#g1").is(':checked')){
							$.ajax({
								type : "post",
								url : config.getDomain() + "/inboundoperation/addworkcheck",
								data : {
									"checkType" :10,
									"checUserId":$("#beforeTaskUserId").attr("data"),
									"content":$("#taskrequire").val(),
									"checkTime":new Date().getTime()/1000,
									"transportId":id
								},
								dataType : "json",
								success:function(data){
									dialog.find("#saveBeforeTask").removeClass("btn-success");
									dialog.find("#saveBeforeTask").addClass("btn-default");
									alert("提交成功");
								}
							});
						}else{
							alert("全部确认正确才可以提交");
						}
						
					});
							
					//作业中确认
					dialog.find("#saveInTask").click(function(){
						if(dialog.find("#h1").is(':checked')&&dialog.find("#i1").is(':checked')&&dialog.find("#j1").is(':checked')&&dialog.find("#k1").is(':checked')&&dialog.find("#l1").is(':checked')){
							$.ajax({
								type : "post",
								url : config.getDomain() + "/inboundoperation/addworkcheck",
								data : {
									"checkType" :11,
									"checUserId":$("#inTaskUserId").attr("data"),
									"content":$("#taskrequire").val(),
									"checkTime":new Date().getTime()/1000,
									"transportId":id
								},
								dataType : "json",
								success:function(data){
									alert("提交成功");
							}});
						}else{
							alert("全部确认正确才可以提交");
						}
					});
					
					//作业后确认
					dialog.find("#saveAfterTask").click(function(){
						if(dialog.find("#saveAfterTask").attr("class")=="btn btn-default"){
							dialog.find("#saveAfterTask").message({
								type : 'warning',
								content : '已经提交过了'
							}); 	
						return;
						}
						if(dialog.find("#m1").is(':checked')&&dialog.find("#n1").is(':checked')&&dialog.find("#o1").is(':checked')&&dialog.find("#p1").is(':checked')){
							$.ajax({
								type : "post",
								url : config.getDomain() + "/inboundoperation/addworkcheck",
								data : {
									"checkType" :12,
									"checUserId":$("#afterTaskUserId").attr("data"),
									"content":$("#taskrequire").val(),
									"checkTime":new Date().getTime()/1000,
									"transportId":id
								},
								dataType : "json",
								success:function(data){
									dialog.find("#saveAfterTask").removeClass("btn-success");
									dialog.find("#saveAfterTask").addClass("btn-default");
									alert("提交成功");
								}
							});
						}else{
							alert("全部确认正确才可以提交");
						}
					});
							
					dialog.modal({
						keyboard : true
					});
				});
	}
	;

	// 查看打回流作业通知单
	function dialogBackFlowNotify() {
		$.get(config.getResource()+ "/pages/inbound/inboundoperation/notice/backflow_notify.jsp")
				.done(function(data) {
					dialog = $(data);
					//初始化通知单
					dialog.find("#transportprogramId").val(dataGrid.getAllItems()[choiceItem].backflowplanId);
					  //拼接作业任务
					var taskmsg="";
					var goodsmsg=dataGrid.getAllItems()[choiceItem].goodsMsg;
					 var ptIds='';
					 var goodsNum=new Array();
					var goodsName=new Array();
					 var goodsPt=new Array();
					 var j=0;
					 if(goodsmsg!=null&&goodsmsg!=""&&goodsmsg.length>0){
						 taskmsg+="入库"
					 for (var i = 0; i < goodsmsg.length; i++) {
							var itemgoodsmsg=goodsmsg[i];
							if(ptIds.indexOf('a'+itemgoodsmsg.productId+'a')==-1){
								ptIds+='a'+itemgoodsmsg.productId+'a';
								goodsPt[j]="a"+itemgoodsmsg.productId;
								goodsNum["a"+itemgoodsmsg.productId]=itemgoodsmsg.goodsPlan;
								goodsName["a"+itemgoodsmsg.productId]=itemgoodsmsg.productName;
								j++;
							}else{
								goodsNum["a"+itemgoodsmsg.productId]=parseFloat(goodsNum["a"+itemgoodsmsg.productId])+parseFloat(itemgoodsmsg.goodsPlan);
							}
						}
					 
					 for (var i = 0; i < goodsPt.length; i++) {
							var pt=goodsPt[i];
							taskmsg+="货品:"+goodsName[pt]+",总量:"+goodsNum[pt]+"吨；";
						}
					 }
					 dialog.find("#bakcflowtaskmsg").val(taskmsg);//任务要求
					
					$.ajax({
						type : "post",
						url : config.getDomain() + "/inboundoperation/list",
						data : {
							"id" :dataGrid.getAllItems()[choiceItem].backflowplanId,
							"result":4
						},
						dataType : "json",
						success:function(data){
							var mdata=data.data[0];
							var svg=mdata.svg;
							var vb="viewBox=\"0 -30 800 250\"";
							  svg=svg.replace("px","");
							  svg=svg.replace("px","");
							    svg=svg.replace("svg","svg "+vb+" ");
								dialog.find("#contentDiv").append(svg);
						}
					});
					
					//作业前确认
					dialog.find("#saveBeforeTask").click(function(){
						if(dialog.find("#saveBeforeTask").attr("class")=="btn btn-default"){
							dialog.find("#saveBeforeTask").message({
								type : 'warning',
								content : '已经提交过了'
							}); 	
						return;
						}	
					
						if(dialog.find("#b1").is(':checked')&&dialog.find("#c1").is(':checked')&&dialog.find("#d1").is(':checked')&&dialog.find("#e1").is(':checked')&&dialog.find("#f1").is(':checked')&&dialog.find("#g1").is(':checked')&&dialog.find("#h1").is(':checked')&&dialog.find("#i1").is(':checked')
								&&dialog.find("#j1").is(':checked')&&dialog.find("#k1").is(':checked')){
							$.ajax({
								type : "post",
								url : config.getDomain() + "/inboundoperation/addworkcheck",
								data : {
									"checkType" :13,
									"checUserId":$("#beforeTaskUserId").attr("data"),
									"content":$("#taskrequire").val(),
									"checkTime":(new Date().getTime()/1000).toFixed(0),
									"transportId":dataGrid.getAllItems()[choiceItem].backflowplanId
								},
								dataType : "json",
								success:function(data){
									dialog.find("#saveBeforeTask").removeClass("btn-success");
									dialog.find("#saveBeforeTask").addClass("btn-default");
									alert("提交成功");
								}
							});
						}else{
							alert("全部确认正确才可以提交");
						}
						
					});
							
					//作业中确认
					dialog.find("#saveInTask").click(function(){
						console.log(dialog.find("#p1").is(':checked')+"==="+dialog.find("#q1").is(':checked')+"==="+dialog.find("#r1").is(':checked'));
						
						if(dialog.find("#p1").is(':checked')&&dialog.find("#q1").is(':checked')&&dialog.find("#r1").is(':checked')&&dialog.find("#s1").is(':checked')&&dialog.find("#t1").is(':checked')&&dialog.find("#u1").is(':checked')){
							$.ajax({
								type : "post",
								url : config.getDomain() + "/inboundoperation/addworkcheck",
								data : {
									"checkType" :14,
									"checUserId":$("#inTaskUserId").attr("data"),
									"content":$("#taskrequire").val(),
									"checkTime":(new Date().getTime()/1000).toFixed(0),
									"transportId":dataGrid.getAllItems()[choiceItem].backflowplanId
								},
								dataType : "json",
								success:function(data){
									alert("提交成功");}
							});
						}else{
							alert("全部确认正确才可以提交");
						}
					});
					
					//作业后确认
					dialog.find("#saveAfterTask").click(function(){
						if(dialog.find("#saveAfterTask").attr("class")=="btn btn-default"){
							dialog.find("#saveAfterTask").message({
								type : 'warning',
								content : '已经提交过了'
							}); 	
						return;
						}
						if(dialog.find("#v1").is(':checked')&&dialog.find("#w1").is(':checked')&&dialog.find("#x1").is(':checked')&&dialog.find("#y1").is(':checked')&&dialog.find("#z1").is(':checked')){
							$.ajax({
								type : "post",
								url : config.getDomain() + "/inboundoperation/addworkcheck",
								data : {
									"checkType" :15,
									"checUserId":$("#afterTaskUserId").attr("data"),
									"content":$("#taskrequire").val(),
									"checkTime":(new Date().getTime()/1000).toFixed(0),
									"transportId":dataGrid.getAllItems()[choiceItem].backflowplanId
								},
								dataType : "json",
								success:function(data){
									dialog.find("#saveAfterTask").removeClass("btn-success");
									dialog.find("#saveAfterTask").addClass("btn-default");
									alert("提交成功");
								}
							});
						}else{
							alert("全部确认正确才可以提交");
						}
					});
					
					
					
					dialog.modal({
						keyboard : true
					});
				});
	};
	/****************************************************************入库详情********************************************************************/
	
	//入库作业详情初始化
	function initView(page, state,index) {
		choiceItem=index;
		itemDetailArrival=dataGrid.getAllItems()[index];
		$("#arrivaldetailForm").find('.mda').each(function(){
            var $this = $(this);
               var key=$this.attr('key');
               if(key=="goodsMsg"){
            	   var columns=[{
						title:"货品",
						name:"productName"
					},{
						title:"货主",
						name:"clientName"
					},{
						title:"数量(吨)",
						name:"goodsPlan"
					}];
					$('[data-role="goodsmsgGrid"]').grid({
						columns:columns,
						isShowIndexCol : false,
						isShowPages : false,
						isUserLocalData:true,
						localData:itemDetailArrival.goodsMsg
					}).on();
               }else{
            	   $this.text(itemDetailArrival[key]);
               }
        });
		
		$("#tab1").click(
						function() {
							$(this).parent().addClass("active").siblings().removeClass("active");
							if (page > 1) {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/inboundplan/get.jsp");
							} else {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/inboundplan/add.jsp");
							}
						});

		$("#tab2").click(
						function() {
							$(this).parent().addClass("active").siblings().removeClass("active");
							if (page > 2) {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/berthplan/get.jsp");
							} else {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/berthplan/add.jsp");
							}
						});
		$("#tab3").click(
						function() {
							$(this).parent().addClass("active").siblings().removeClass("active");
							if (page > 3) {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/unloadingplan/get.jsp");
							} else {$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/unloadingplan/add.jsp");
							}
						});
		$("#tab4").click(
						function() {
							$(this).parent().addClass("active").siblings().removeClass("active");
							if (page > 4) {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/unloadingready/get.jsp");
							} else {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/unloadingready/add.jsp");
							}
						});
		$("#tab5").click(function() {
							$(this).parent().addClass("active").siblings().removeClass("active");
							if (page > 5) {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/backflowplan/get.jsp");
							} else {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/backflowplan/add.jsp");
							}
						});

		$("#tab6").click(
						function() {
							$(this).parent().addClass("active").siblings().removeClass("active");
							if (page > 6) {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/backflowready/get.jsp");
							} else {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/backflowready/add.jsp");
							}
						});

		$("#tab7")
				.click(
						function() {
							$(this).parent().addClass("active").siblings().removeClass("active");
							if (page > 7) {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/amountaffirm/get.jsp");
							} else {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/amountaffirm/add.jsp");
							}
						});
		if (state == 1) {
			$("#tab1").click();
		}
		if (state == 2) {
			$("#tab2").click();
		}
		if (state == 3) {
			$("#tab3").click();
		}
		if (state == 4) {
			$("#tab4").click();
		}
		if (state == 5) {
			$("#tab5").click();
		}
		if (state == 6) {
			$("#tab6").click();
		}
		if (state == 7) {
			$("#tab7").click();
		};
	};
	//初始化入库计划
	function initArrivalInfo(obj,item){
		if(item==1){
		$(obj).find("#arrivalInfoId").val(dataGrid.getAllItems()[choiceItem].arrivalinfoId);		
		$(obj).find("#arrivalId").val(dataGrid.getAllItems()[choiceItem].id);
		$(obj).find("#createUserId").attr("data",dataGrid.getAllItems()[choiceItem].createUserId);
		$(obj).find("#createUserName").val(dataGrid.getAllItems()[choiceItem].createUserName);
			$(obj).find("#arrivalInfoId").text(dataGrid.getAllItems()[choiceItem].arrivalinfoId);		
			$(obj).find("#arrivalId").text(dataGrid.getAllItems()[choiceItem].id);
			$.ajax({
				type : "post",
				url : config.getDomain() + "/inboundoperation/list",
				data : {
					"id" : dataGrid.getAllItems()[choiceItem].arrivalinfoId,
					"result":1
				},
				dataType : "json",
				success:function(data){
					var mdata=data.data[0];
					$("#cjTime").text(mdata.cjTime);
					$("#tcTime").text(mdata.tcTime);
					$("#norTime").text(mdata.norTime);
					var anchor=new Array();
					anchor=mdata.anchorTime.split(" ");
					$("#anchorTime1").text(anchor[0]);
					$("#anchorTime2").text(anchor[1]);
					$("#pumpOpenTime").text(mdata.pumpOpenTime);
					$("#pumpStopTime").text(mdata.pumpStopTime);
					$("#workTime").text(mdata.workTime);
					$("#leaveTime").text(mdata.leaveTime);
					$("#tearPipeTime").text(mdata.tearPipeTime);
					$("#port").text(mdata.port);
					$("#portNum").text(mdata.portNum);
					$("#report").text(mdata.report);
					$("#shipInfo").text(mdata.shipInfo);
					$("#note").text(mdata.note);
					$("#overTime").text(mdata.overTime);
					$("#repatriateTime").text(mdata.repatriateTime);
					$("#lastLeaveTime").text(mdata.lastLeaveTime);
					$("#createUserName").text(mdata.createUserName);
					$("#createTime").text(mdata.createTime.split(" ")[0]);
					$("#reviewUserName").text(mdata.reviewUserName);
					$("#reviewTime").text(mdata.reviewTime);
				}
			});
		}
	};
	//初始化靠泊方案
	function initBerthPlan(obj,item){
		if(item==1){
		$(obj).find("#berthprogramId").val(dataGrid.getAllItems()[choiceItem].berthprogramId);
		$(obj).find("#anchorTime").val(dataGrid.getAllItems()[choiceItem].anchorTime);
		$(obj).find("#arrivalId").val(dataGrid.getAllItems()[choiceItem].id);
		$(obj).find("#createUserId").attr("data",dataGrid.getAllItems()[choiceItem].createUserId);
		$(obj).find("#createUserName").val(dataGrid.getAllItems()[choiceItem].createUserName);
	}else{
		$(obj).find("#berthprogramId").text(dataGrid.getAllItems()[choiceItem].berthprogramId);
		$(obj).find("#anchorTime").text(dataGrid.getAllItems()[choiceItem].anchorTime);
		$(obj).find("#arrivalId").text(dataGrid.getAllItems()[choiceItem].id);
		$.ajax({
			type : "post",
				url : config.getDomain()+"/inboundoperation/list",
				dataType : "json",
				data:{
					"id":dataGrid.getAllItems()[choiceItem].berthprogramId,
					"result":3
				},
				success : function(data) {
	                var mdata=data.data[0];
	             $("#safeInfo").text(mdata.safeInfo);
	             $("#comment").text(mdata.comment);
				 $("#createUserName").text(mdata.createUserName);
				 $("#createTime").text(mdata.createTime.split(" ")[0]);
				 $("#reviewUserName").text(mdata.reviewUserName);
				 $("#reviewTime").text(mdata.reviewTime);
				 initGrid(mdata.berthId);
				}
		});
	}
	};
	//初始化接卸方案
	function initTransportProgram(obj,item){
		if(item==1){
			$(obj).find("#transportprogramId").val(dataGrid.getAllItems()[choiceItem].transportprogramId);
			$(obj).find("#anchorTime").val(dataGrid.getAllItems()[choiceItem].anchorTime);
			$(obj).find("#arrivalId").val(dataGrid.getAllItems()[choiceItem].id);
			$(obj).find("#berthName").val(dataGrid.getAllItems()[choiceItem].berthName);
			$(obj).find("#createUserId").attr("data",dataGrid.getAllItems()[choiceItem].createUserId);
			$(obj).find("#createUserName").val(dataGrid.getAllItems()[choiceItem].createUserName);
		}else{
			$(obj).find("#transportprogramId").text(dataGrid.getAllItems()[choiceItem].transportprogramId);
			$(obj).find("#anchorTime").text(dataGrid.getAllItems()[choiceItem].anchorTime);
			$(obj).find("#arrivalId").text(dataGrid.getAllItems()[choiceItem].id);
			$(obj).find("#berthName").text(dataGrid.getAllItems()[choiceItem].berthName);
			$.ajax({
				type : "post",
				url : config.getDomain() + "/inboundoperation/list",
				data : {
					"id" :dataGrid.getAllItems()[choiceItem].transportprogramId,
					"result":4
				},
				dataType : "json",
				success:function(data){
					var mdata=data.data[0];
					$("#tankNames").text(mdata.tankNames);
					$("#tubeNames").text(mdata.tubeNames);
					$("#createUserName").text(mdata.createUserName);
					$("#createTime").text(mdata.createTime);
					$("#reviewUserName").text(mdata.reviewUserName);
					$("#reviewTime").text(mdata.reviewTime),
					$("#reviewCraftUserName").text(mdata.reviewCraftUserName);
					$("#reviewCraftTime").text(mdata.reviewTime);
					$("#flow").text(mdata.flow);
					$("#graphContainer").append(mdata.flow);
					flow(mdata.flow,false,false);
				}
			});
		}
	};
	//初始化接卸准备
	function initUnloadingReady(obj,item){
			//初始化罐安排
			var columns=[{
				title:"罐号",
				name:"tankCode"
			},{
				title:"罐名",
				name:"tankName"
			},{
				title:"前期存储物料",
				name:"productName"
			},{
				title:"使用情况",
				name:"tankDescription"
			}];
			$('[data-role="tankGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : false,
				url : config.getDomain() + "/inboundoperation/list?id="+dataGrid.getAllItems()[choiceItem].transportprogramId+"&result=11"
			}).on();
			$(".mftankstate").click(function(){
				var data = $('[data-role="tankGrid"]')
				.getGrid().selectedRowsIndex();
					if (data.length == 0) {
						$(".mftankstate").message({
							type : 'warning',
							content : '请选择要修改的选项'
						});
						return;
					}
				dialogTankState();
				
			});
			
			
			//初始化管安排
			var columns=[{
				title:"管名",
				name:"tubeName"
			},{
				title:"前期存储物料",
				name:"productName"
			},{
				title:"使用情况",
				name:"tubeDescription"
			}];
			$('[data-role="tubeGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : false,
				url : config.getDomain() + "/inboundoperation/list?id="+dataGrid.getAllItems()[choiceItem].transportprogramId+"&result=12"
			}).on();
			$(".mftubestate").click(function(){
				dialogTubeState();
			});
			
			
			//初始化管线准备与检查
			var columns=[{
				title:"日期",
				name:"checkTime",
				render:function(item, name, index){
					return item.checkTime.split(" ")[0];
				}
			},{
				title:"内容",
                 name:"content"
                	 },{
				title:"作业人",
				name:"checkUserName"
				
			},{
				title:"备注",
				name:"checkDescription"
			}];
			$('[data-role="tubeCheckGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : false,
				url : config.getDomain() + "/inboundoperation/list?id="+dataGrid.getAllItems()[choiceItem].transportprogramId+"&result=13"
			}).on();
			$(".mftubecheck").click(function(){
				var data = $('[data-role="tubeCheckGrid"]')
				.getGrid().selectedRowsIndex();
					 var $this = $(this);
					if (data.length == 0) {
						$this.message({
							type : 'warning',
							content : '请选择要修改的选项'
						});
						return;
					}
				
				dialogTubeCheck();
			});
			
			//初始化岗位检查表
			var columns=[{
				title:"日期",
				name:"createTime",
				render:function(item, name, index){
					return item.createTime.split(" ")[0];
				}
			},{
				title:"岗位",
                 name:"post"
                	 },{
				title:"检查内容",
				name:"checkContent"
				
			},{
				title:"检查结果",
				name:"result"
			},{
				title:"处理情况",
				name:"solve"
			},{
				title:"确认人",
				name:"createUserName",
				render:function(item,name,index){
					if(index==0){
						$("#dockBUserId").val(item.createUserName);
					}else if(index==1){
						$("#dynamicBUserId").val(item.createUserName);
					}else if(index==3){
						$("#dispathBUserId").val(item.createUserName);
					}
					
					
					return item.createUserName;
				}
			}];
			$('[data-role="jobCheckGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : false,
				url : config.getDomain() + "/inboundoperation/list?id="+dataGrid.getAllItems()[choiceItem].transportprogramId+"&result=14"
			}).on();
			$(".mfallcheck").click(function(){
				var data = $('[data-role="jobCheckGrid"]')
				.getGrid().selectedRowsIndex();
					 var $this = $(this);
					if (data.length == 0) {
						$this.message({
							type : 'warning',
							content : '请选择要修改的选项'
						});
						return;
					}
				dialogAllCheck();
			})
		//接卸过程
		$("#shipName").text(dataGrid.getAllItems()[choiceItem].shipName);	
		$("#berthName").text(dataGrid.getAllItems()[choiceItem].berthName);
		$.ajax({
			type : "post",
			url : config.getDomain() + "/inboundoperation/list",
			data : {
				"id" :dataGrid.getAllItems()[choiceItem].id,
				"result":15
			},
			dataType : "json",
			success:function(data){
				var mdata=data.data[0];
				if(item==1){
			$("#stayTime").val(mdata.stayTime);
			$("#checkTime").val(mdata.checkTime);
			$("#arrivalTime").val(mdata.arrivalTime);
			$("#openPump").val(mdata.openPump);
			$("#stopPump").val(mdata.stopPump);
			$("#workTime").val(mdata.workTime);
            $("#measureAmount").val(mdata.measureAmount); 				
			$("#tankAmount").val(mdata.tankAmount);
			$("#differAmount").val(mdata.differAmount);
			$("#evaluate").val(mdata.evaluate==null?"":mdata.evaluate);
			$("#evaluateTime").val(mdata.evaluateTime);
			$("#evaluateUserId").val(mdata.evaluateUserName);
			$("#evaluateUserId").attr("data",mdata.evaluateUserId);
			$("#dynamicUserId").val(mdata.dynamicUserName);
			$("#dynamicUserId").attr("data",mdata.dynamicUserUserId);
			$("#dockUserId").val(mdata.dockUserName);
			$("#dockUserId").attr("data",mdata.dockUserUserId);
			$("#shipClientId").val(mdata.shipClientName);
			$("#shipClientId").attr("data",mdata.shipClientId);
			$("#unusualLog").val(mdata.unusualLog==null?"":mdata.unusualLog);	
			$("#dockCheck").val(mdata.dockCheck);
				}else{
					$("#stayTime").text(mdata.stayTime);
					$("#checkTime").text(mdata.checkTime);
					$("#arrivalTime").text(mdata.arrivalTime);
					$("#openPump").text(mdata.openPump);
					$("#stopPump").text(mdata.stopPump);
					$("#workTime").text(mdata.workTime);
		            $("#measureAmount").text(mdata.measureAmount); 				
					$("#tankAmount").text(mdata.tankAmount);
					$("#differAmount").text(mdata.differAmount);
					$("#evaluate").val(mdata.evaluate==null?"":mdata.evaluate);
					$("#evaluateTime").text(mdata.evaluateTime);
					$("#evaluateUserId").text(mdata.evaluateUserName);
					$("#evaluateUserId").attr("data",mdata.evaluateUserId);
					$("#dynamicUserId").text(mdata.dynamicUserName);
					$("#dynamicUserId").attr("data",mdata.dynamicUserUserId);
					$("#dockUserId").text(mdata.dockUserName);
					$("#dockUserId").attr("data",mdata.dockUserUserId);
					$("#shipClientId").text(mdata.shipClientName);
					$("#shipClientId").attr("data",mdata.shipClientId);
					$("#unusualLog").val(mdata.unusualLog==null?"":mdata.unusualLog);	
					$("#dockCheck").val(mdata.dockCheck);
						
				}
			}
		});
			$("#saveWork,#saveLog").click(function(){
				$.ajax({
					type : "post",
					url : config.getDomain() + "/inboundoperation/updatework",
					data:{
						"id":dataGrid.getAllItems()[choiceItem].workId,
						"arrivalTime":formatlong($("#arrivalTime").val()),
						"checkTime":formatlong($("#checkTime").val()),
						"stayTime":$("#stayTime").val(),
						"workTime":$("#workTime").val(),
						"openPump":formatlong($("#openPump").val()),
						"stopPump":formatlong($("#stopPump").val()),
						"measureAmount":$("#measureAmount").val(),
						"tankAmount":$("#tankAmount").val(),
						"differAmount":$("#differAmount").val(),
						"evaluate":$("#evaluate").val(),
						"evaluateTime":formatlong($("#evaluateTime").val()),
						"evaluateUserId":$("#evaluateUserId").attr("data"),
						"dynamicUserId":$("#dynamicUserId").attr("data"),
						"dockUserId":$("#dockUserId").attr("data"),
						"shipClientId":$("#shipClientId").attr("data"),
						"unusualLog":$("#unusualLog").val(),
						"dockCheck":$("#dockCheck").val()
					},
					dataType:"json",
					success:function(data){
						alert("提交成功");
					}
				});
			});
			$("#saveAll").click(function(){
				$.ajax({
					type : "post",
					url : config.getDomain() + "/inboundoperation/updatearrival",
					data:{
						"id":dataGrid.getAllItems()[choiceItem].id,
						"status":9
					},
					dataType:"json",
					success:function(data){
						alert("提交成功");
					}
				});  				
			});
	};
	//初始化打回流方案
	function initBackFlowPlan(obj,item){
		if(item==1){
			$(obj).find("#backflowplanId").val(dataGrid.getAllItems()[choiceItem].backflowplanId);
			$(obj).find("#anchorTime").val(dataGrid.getAllItems()[choiceItem].anchorTime);
			$(obj).find("#arrivalId").val(dataGrid.getAllItems()[choiceItem].id);
			$(obj).find("#berthName").val(dataGrid.getAllItems()[choiceItem].berthName);
			$(obj).find("#createUserId").attr("data",dataGrid.getAllItems()[choiceItem].createUserId);
			$(obj).find("#createUserName").val(dataGrid.getAllItems()[choiceItem].createUserName);
		}else{
			$(obj).find("#backflowplanId").text(dataGrid.getAllItems()[choiceItem].backflowplanId);
			$(obj).find("#anchorTime").text(dataGrid.getAllItems()[choiceItem].anchorTime);
			$(obj).find("#arrivalId").text(dataGrid.getAllItems()[choiceItem].id);
			$(obj).find("#berthName").text(dataGrid.getAllItems()[choiceItem].berthName);
			$.ajax({
				type : "post",
				url : config.getDomain() + "/inboundoperation/list",
				data : {
					"id" :dataGrid.getAllItems()[choiceItem].backflowplanId,
					"result":4
				},
				dataType : "json",
				success:function(data){
					var mdata=data.data[0];
					$("#tankNames").text(mdata.tankNames);
					$("#tubeNames").text(mdata.tubeNames);
					$("#createUserName").text(mdata.createUserName);
					$("#createTime").text(mdata.createTime);
					$("#reviewUserName").text(mdata.reviewUserName);
					$("#reviewTime").text(mdata.reviewTime),
					$("#reviewCraftUserName").text(mdata.reviewCraftUserName);
					$("#reviewCraftTime").text(mdata.reviewTime);
					$("#flow").text(mdata.flow);
					$("#graphContainer").append(mdata.flow);
					flow(mdata.flow,false,false);
				}
			});
		}
	};
	
	 function initAmountAffirm(obj,item){
		 if(item==1){
		 $(obj).find("#createUserId").attr("data",dataGrid.getAllItems()[choiceItem].createUserId);
			$(obj).find("#createUserName").val(dataGrid.getAllItems()[choiceItem].createUserName);
		 var columns=[{
				title:"货品",
				name:"productName"
			},{
				title:"货主",
				name:"clientName"
			},{
				title:"计划数量(吨)",
				name:"goodsPlan"
			},{
				title:"货品罐号",
				name:"tankName",
				render:function(item,name,index){
					if(clientData!=null&&clientData['a'+index]!=null){
						var tankMsg=clientData['a'+index].tankMsg;
						console.debug(tankMsg);
						var tankHtml='<table class="table inmtable">';
						for(var i=0;i<tankMsg.length;i++){
							tankHtml+='<tr><td style="border-bottom:1px solid #ddd;" >'+tankMsg[i].tankCode+'</td></tr>';
						}
                           tankHtml+="</table>";						
						return tankHtml;
					}else{
						return "0";
					}
				}
			},{
				title:"罐内数量",
				name:"tankNum",
				render:function(item,name,index){
					if(clientData!=null&&clientData['a'+index]!=null){
						var tankMsg=clientData['a'+index].tankMsg;
						console.debug(tankMsg);
						var tankHtml='<table class="table inmtable">';
						for(var i=0;i<tankMsg.length;i++){
							tankHtml+='<tr><td style="border-bottom:1px solid #ddd;" >'+tankMsg[i].tankNum+'</td></tr>';
						}
                           tankHtml+="</table>";						
						return tankHtml;
					}else{
						return "0";
					}
				}
			},{
				title:"实际总量",
				name:"tankTotal",
				render:function(item,name,index){
					if(clientData!=null&&clientData['a'+index]!=null){
						console.debug(clientData['a'+index]);
					return clientData['a'+index].tankTotal;
				}else{
				return "0";	
				}
				}
			},{
				title:"船检数量",
				name:"goodsShip",
				render:function(item,name,index){
					if(clientData!=null&&clientData['a'+index]!=null){
						return clientData['a'+index].goodsShip;
					}else{
					return "0";	
					}
				}
			},{
				title:"入库损耗",
				name:"goodsLoss",
				render:function(item,name,index){
					if(clientData!=null&&clientData['a'+index]!=null){
						return clientData['a'+index].goodsLoss;
					}else{
					return "0";	
					}
				}
			},{
				title:"入库损耗率",
				name:"lossRate",
				render:function(item,name,index){
					if(clientData!=null&&clientData['a'+index]!=null){
						return clientData['a'+index].lossRate+"‰";
					}else{
					return "0";	
					}
				}
			}
			];
		 $('[data-role="clientAmountGrid"]').grid({
				columns:columns,
				isShowIndexCol : true,
				isShowPages : false,
				isUserLocalData:true,
				localData:itemDetailArrival.goodsMsg
			});
		 var columns=[{
				title:"罐号",
				name:"tankCode"
			},{
				title:"货品",
				name:"productName"
			},{
				title:"罐检数量",
				name:"realAmount"
			},{
				title:"操作剩余量",
				name:"leftAmount",
				render:function(item,name,index){
					if(tankData!=null&&tankData["a"+item.tankId]!=null){
						return tankData["a"+item.tankId];
					}else{
						return item.realAmount;
					}
					
				}
			}]; 
			 $('[data-role="tankAmountGrid"]').grid({
				 identity : 'id',
					columns : columns,
					isShowIndexCol : true,
					isShowPages : false,
					url : config.getDomain() + "/inboundoperation/list?id="+dataGrid.getAllItems()[choiceItem].transportprogramId+"&result="+6 
			 });
			 $(".modifyClient").click(function(){
				 var data = $('[data-role="clientAmountGrid"]').getGrid().selectedRowsNo();
						 var $this = $(this);
						if (data.length == 0) {
							$this.message({
								type : 'warning',
								content : '请选择要修改的选项'
							});
							return;
						}
						dialogAmountAffirm();
			 });
			 //提交
			 $("#save").click(function(){
				 //更新货批
				 var cargoMsg=itemDetailArrival.goodsMsg;
				 var cargoList=new Array();
				 for(var i=0;i<cargoMsg.length;i++){
					 if(clientData['a'+i]!=undefined&&clientData['a'+i]!=null){
					 var itemCargo={
							'id':cargoMsg[i].cargoId,
							'goodsTotal':clientData['a'+i].tankTotal,
							'goodsTank':clientData['a'+i].tankTotal,
							'lossRate':clientData['a'+i].lossRate
					       }
					 cargoList.push(itemCargo);
               		}}
				 $.ajax({
					 type : "post",
						url : config.getDomain() + "/inboundoperation/updatecargolist",
						data:{
							"cargolist":JSON.stringify(cargoList)
						},
						dataType:"json",
						success:function(data){
						}	
						 });
				 
				 //生成货体
				 var goodsList=new Array();
				var goodsMsg= itemDetailArrival.goodsMsg;
				for(var i=0;i<goodsMsg.length;i++){
					if(clientData['a'+i]!=undefined&&clientData['a'+i]!=null){
					var itemtankMsg=clientData['a'+i].tankMsg;
					for(var j=0;j<itemtankMsg.length;j++){
				 var itemGoods={
						 'code':goodsMsg[i].cargoCode+'-'+(j+1)+'/'+itemtankMsg.length,
						 'cargoId':goodsMsg[i].cargoId,
						 'clientId':goodsMsg[i].clientId,
						 'productId':goodsMsg[i].productId,
						 'contractId':goodsMsg[i].contractId,
						 'tankId':itemtankMsg[j].tankId,
						 'goodsTotal':itemtankMsg[j].tankNum,
						 'goodsTank':itemtankMsg[j].tankNum,
						 'goodsInspect':0,
						 'goodsIn':0,
						 'goodsOut':0,
						 'goodsCurrent':itemtankMsg[j].tankNum,
						 'goodsInPass':0,
						 'goodsOutPass':0,
						 'lossRate':0
				 };
				 goodsList.push(itemGoods);
					}
					}
				}
				 $.ajax({
					 type : "post",
						url : config.getDomain() + "/inboundoperation/addgoodslist",
						data:{
							"goodslist":JSON.stringify(goodsList)
						},
						dataType:"json",
						success:function(data){
							alert("提交成功");
						}	
						 });
				 
			 });
		 }else{
			 var columns=[{
					title:"货品",
					name:"productName",
					render:function(item,name,index){
					return 	item.cargodata.productName;
					}
				},{
					title:"货主",
					name:"clientName",
					render:function(item,name,index){
						return item.cargodata.clientName;
					}
				},{
					title:"计划数量(吨)",
					name:"goodsPlan",
					render:function(item,name,index){
						return item.cargodata.goodsPlan;
					}
				},{
					title:"货品罐号",
					name:"tankName",
					render:function(item,name,index){
						var goodsData=item.goodsdata;
						if(goodsData.length>0){
						var tankHtml='<table class="table inmtable">';
						for(var i=0;i<goodsData.length;i++){
							tankHtml+='<tr><td style="border-bottom:1px solid #ddd;" >'+goodsData[i].tankCode+'</td></tr>';
						}
                           tankHtml+="</table>";						
						return tankHtml;
						}
						return "";
					}
				},{
					title:"罐内数量",
					name:"tankNum",
					render:function(item,name,index){
						var goodsData=item.goodsdata;
						if(goodsData.length>0){
						var tankHtml='<table class="table inmtable">';
						for(var i=0;i<goodsData.length;i++){
							tankHtml+='<tr><td style="border-bottom:1px solid #ddd;" >'+goodsData[i].goodsTank+'</td></tr>';
						}
                           tankHtml+="</table>";						
						return tankHtml;
						}
						return "";
					}
				},{
					title:"实际总量",
					name:"tankTotal",
					render:function(item,name,index){
						return item.cargodata.goodsTank;
						
					}
				},{
					title:"船检数量",
					name:"goodsShip",
					render:function(item,name,index){
						return item.cargodata.goodsShip;
					}
				},{
					title:"入库损耗",
					name:"goodsLoss",
					render:function(item,name,index){
						return parseFloat(item.cargodata.goodsShip)-parseFloat(item.cargodata.goodsTank);
					}
				},{
					title:"入库损耗率",
					name:"lossRate",
					render:function(item,name,index){
				        return item.cargodata.lossRate;		
					}
				}
				];
			 
			 $('[data-role="clientAmountGrid"]').grid({
					identity:'id',
				    columns:columns,
					isShowIndexCol : true,
					isShowPages : false,
					url : config.getDomain() + "/inboundoperation/list?id="+dataGrid.getAllItems()[choiceItem].id+"&result="+16
				});
			 var columns=[{
					title:"罐号",
					name:"tankCode"
				},{
					title:"货品",
					name:"productName"
				},{
					title:"罐检数量",
					name:"realAmount"
				}]; 
				 $('[data-role="tankAmountGrid"]').grid({
					 identity : 'id',
						columns : columns,
						isShowIndexCol : true,
						isShowPages : false,
						url : config.getDomain() + "/inboundoperation/list?id="+dataGrid.getAllItems()[choiceItem].transportprogramId+"&result="+6 
				 });
			 
		 } 
	 }
	
	/***********************************************接卸准备修改dialog****************************************************************/
	//数量确认clientData 数组  clientData[item] 对象{tankMsg;total}
	 function dialogAmountAffirm(){
		 $.get(config.getResource()+ "/pages/inbound/inboundoperation/amountaffirm/dialog/dialog_amountaffirm.jsp")
			.done(function(data) {
				dialog = $(data);
				var item=$('[data-role="clientAmountGrid"]').getGrid().selectedRowsNo()[0];
				item="a"+item;
				if(clientData==null){
					clientData=new Array();
					clientData[item]={};
				}
				if(clientData[item]==undefined||clientData[item].tankMsg==undefined){
					clientData[item]={
							'tankMsg':new Array(),
							'tankTotal':0,
							'goodsShip':0,
							'goodsLoss':0,
							'lossRate':0
					}
				}
				if(tankData==null){
					tankData=new Array();
				}
				
				var mdata=$('[data-role="clientAmountGrid"]').getGrid().selectedRows()[0];
				dialog.find("#clientName").text(mdata.clientName);
				dialog.find("#productName").text(mdata.productName);
				dialog.find("#goodsPlan").text(mdata.goodsPlan);
				dialog.find("#goodsShip").text(clientData[item].goodsShip);
				dialog.find("#goodsLoss").text(clientData[item].goodsLoss);
				dialog.find("#lossRate").text(clientData[item].lossRate);
				var ndata=$('[data-role="tankAmountGrid"]').getGrid().getAllItems();
				var itemHtml="<tbody>";
				var j=0;
				for(var i=0;i<ndata.length;i++){
					if(ndata[i].productId==mdata.productId){
						
					var left=tankData['a'+ndata[i].tankId];
						if(left==null||left==undefined){
							left=ndata[i].realAmount;
						}
						var tankNum=0;
						if(clientData[item]==null||clientData[item].tankMsg.length==0){
							tankNum==0;
						}else{
							var tankMsg=clientData[item].tankMsg;
							if(j<tankMsg.length){
								tankNum=tankMsg[j].tankNum;
								j++;
								console.debug("dfffffffffffff"+tankMsg[i]);
							}
							}
					 
						itemHtml	+="<tr>"
					   +"<td width='50px'><input width='100px' type='checkbox' class='checkboxes' name='chk_list'></td>"
				   +"<td  class='tankCode' data='"+ndata[i].tankId+"'>"+ndata[i].tankCode+"</td>"
				   +"<td  class='productName' >"+ndata[i].productName+"</td>"
				   +"<td class='realAmount'>"+ndata[i].realAmount+"</td>"
				   +"<td class='leftAmount'>"+left+"</td>"
				   +"<td width='auto'><input type='text'  style='width:150px' class='tankNum' data='"+tankNum+"' value='"+tankNum+"'></td></tr>"
					}
				}
				itemHtml+="</tbody>";
				dialog.find("#amountTable").append(itemHtml);
				dialog.find("#save").click(function(){
					var tankMsg=clientData[item].tankMsg;
					var tankTotal=clientData[item].tankTotal;
					var goodsShip=clientData[item].goodsShip;
					var goodsLoss=clientData[item].goodsLoss;
					var lossRate=clientData[item].lossRate;
					var i=0;
					dialog.find("#amountTable").children("tbody").each(function(){
							dialog.find('input[type="checkbox"][name="chk_list"]:checked').each(function(){
								var _tr=$(this).parents("tr");
								tankData['a'+$(_tr).find(".tankCode").attr("data")]=parseFloat($(_tr).find(".leftAmount").text())-parseFloat($(_tr).find('input[class="tankNum"]').val())
								+parseFloat($(_tr).find('input[class="tankNum"]').attr("data"));//tank
								var item={
										'tankId':$(_tr).find(".tankCode").attr("data"),
										'tankCode':$(_tr).find(".tankCode").text(),
										'tankNum':$(_tr).find('input[class="tankNum"]').val()
								};
								tankMsg.push(item);
								tankTotal+=parseFloat($(_tr).find('input[class="tankNum"]').val());
								i++;
							});
					});
					//损耗率
					goodsShip=dialog.find("#goodsShip").val();
					var reg=/^[0-9]*$/;
					if(reg.test(goodsShip)){
						var loss=parseFloat(goodsShip)-parseFloat(tankTotal);
						if(loss>0){
							goodsLoss=loss;
							lossRate=loss*1000/parseFloat(goodsShip);
						}
					}else{
						dialog.find("#goodsShip").val("");
						goodsShip=0;
					}
					clientData[item]={
							'tankMsg':tankMsg,
							'tankTotal':tankTotal,
							'goodsShip':goodsShip,
							'goodsLoss':goodsLoss,
							'lossRate':lossRate.toFixed(0)
					}
					
					
					$('[data-role="clientAmountGrid"]').getGrid().refresh();
					$('[data-role="tankAmountGrid"]').getGrid().refresh();
                   dialog.remove();					
					
				});
				
				dialog.find("#close").click(function(){
					dialog.remove();
				});
				dialog.modal({
					keyboard : true
				});
			});
	 }
	 //修改管线安排
	 function dialogTubeState(){
		 $.get(config.getResource()+ "/pages/inbound/inboundoperation/unloadingready/dialog/dialog_tubestate.jsp")
			.done(function(data) {
				dialog = $(data);
				var mdata=$('[data-role="tubeGrid"]').getGrid().selectedRows()[0];
				dialog.find("#tubeName").text(mdata.tubeName);
				dialog.find("#tubeName").attr("data",mdata.tubeId);
				dialog.find("#productId").val(mdata.productName);
				dialog.find("#productId").attr("data",mdata.productId);
				dialog.find("#tubeDescription").val(mdata.tubeDescription==null?"":mdata.tubeDescription);
				
				dialog.find("#save").click(function(){
					$.ajax({
						type : "post",
						url : config.getDomain() + "/tube/update",
						data : {
							"id" : dialog.find("#tubeName").attr("data"),
							"productId":dialog.find("#productId").attr("data"),
							"description":dialog.find("#tubeDescription").val()
						},
						dataType : "json",
						success:function(data){
								$('[data-role="tubeGrid"]').getGrid().refresh();
								dialog.remove();
						}
					});
				}).end().modal({
					keyboard : true
				});
				
				dialog.find("#close").click(function(){
					dialog.remove();
				});
				dialog.modal({
					keyboard : true
				});
			});
	 };
	 
	 //修改储罐状态
	 function dialogTankState(){
		 $.get(config.getResource()+ "/pages/inbound/inboundoperation/unloadingready/dialog/dialog_tankstate.jsp")
			.done(function(data) {
				dialog = $(data);
				var mdata=$('[data-role="tankGrid"]').getGrid().selectedRows()[0];
				dialog.find("#tankCode").text(mdata.tankCode);
				dialog.find("#tankCode").attr("data",mdata.tankId);
				dialog.find("#tankName").text(mdata.tankName);
				dialog.find("#productId").val(mdata.productName);
				dialog.find("#productId").attr("data",mdata.productId);
				dialog.find("#tankDescription").val(mdata.tankDescription==null?"":mdata.tankDescription);
				dialog.find("#id").val(mdata.id);
				dialog.find("#save").click(function(){
					$.ajax({
						type : "post",
						url : config.getDomain() + "/tank/update",
						data : {
							"id" : dialog.find("#tankCode").attr("data"),
							"productId":dialog.find("#productId").attr("data"),
							"description":dialog.find("#tankDescription").val()
						},
						dataType : "json",
						success:function(data){
								$('[data-role="tankGrid"]').getGrid().refresh();
								dialog.remove();
						}
					});
				}).end().modal({
					keyboard : true
				});
				
				dialog.find("#close").click(function(){
					dialog.remove();
				});
				
				dialog.modal({
					keyboard : true
				});
			});
	 };
	 
	 //修改管线确认
	 function dialogTubeCheck(){
		 $.get(config.getResource()+ "/pages/inbound/inboundoperation/unloadingready/dialog/dialog_tubecheck.jsp")
			.done(function(data) {
				dialog = $(data);
				dialog.find('.date-picker').datepicker({
					rtl : Metronic.isRTL(),
					orientation : "right",
					format : "yyyy-mm-dd",
					autoclose : true
				});
				var mdata=$('[data-role="tubeCheckGrid"]').getGrid().selectedRows()[0];
				dialog.find("#checkTime").val(mdata.checkTime.split(" ")[0]);
				dialog.find("#content").text(mdata.content);
				dialog.find("#checkUserId").val(mdata.checkUserName);
				dialog.find("#checkUserId").attr("data",mdata.checkUserId);
				dialog.find("#id").val(mdata.id);
				
				
				dialog.find("#save").click(function(){
					$.ajax({
						type : "post",
						url : config.getDomain() + "/inboundoperation/updateworkcheck",
						data : {
							"id" : dialog.find("#id").val(),
							"checkTime":formatlong(dialog.find("#checkTime").val()+" 00:00:00"),
							"checkUserId":dialog.find("#checkUserId").attr("data")
						},
						dataType : "json",
						success:function(data){
								$('[data-role="tubeCheckGrid"]').getGrid().refresh();
								dialog.remove();
						}
					});
				}).end().modal({
					keyboard : true
				});
				
				dialog.find("#close").click(function(){
					dialog.remove();
				});
				
				
				dialog.modal({
					keyboard : true
				});
			});
	 };
	 
	 //修改最后检查
	 function dialogAllCheck(){
		 $.get(config.getResource()+ "/pages/inbound/inboundoperation/unloadingready/dialog/dialog_allcheck.jsp")
			.done(function(data) {
				dialog = $(data);
				dialog.find('.date-picker').datepicker({
					rtl : Metronic.isRTL(),
					orientation : "right",
					format : "yyyy-mm-dd",
					autoclose : true
				});
				var mdata=$('[data-role="jobCheckGrid"]').getGrid().selectedRows()[0];
				dialog.find("#createTime").val(mdata.createTime.split(" ")[0]);
				dialog.find("#post").text(mdata.post);
				dialog.find("#checkContent").val(mdata.checkContent);
				dialog.find("#solve").val(mdata.solve==null?"":mdata.solve);
				dialog.find("#result").val(mdata.result==null?"":mdata.result);
				dialog.find("#createUserId").val(mdata.createUserName==null?"":mdata.createUserName);
				dialog.find("#createUserId").attr("data",mdata.createUserId);
				dialog.find("#id").val(mdata.id);
				dialog.find("#save").click(function(){
					$.ajax({
						type : "post",
						url : config.getDomain() + "/inboundoperation/updatejobcheck",
						data : {
							"id" : dialog.find("#id").val(),
							"solve":dialog.find("#solve").val(),
							"result":dialog.find("#result").val(),
							"createTime":formatlong(dialog.find("#createTime").val()+" 00:00:00"),
							"createUserId":dialog.find("#createUserId").attr("data")
						},
						dataType : "json",
						success:function(data){
								$('[data-role="jobCheckGrid"]').getGrid().refresh();
								dialog.remove();
						}
					});
				}).end().modal({
					keyboard : true
				});
				
				dialog.find("#close").click(function(){
					dialog.remove();
				});
				dialog.modal({
					keyboard : true
				});
			});
	 };
 
	/********************************************************************************************************************************/
    //入库作业列表tab
	function changetab(obj, item) {
		$(obj).parent().addClass("active").siblings().removeClass("active");
		if (item == 1) {
			$(".buttons").show();
			$("#inboundopeartionListForm").show();
			initTab(item,0);
		} else if (item == 2) {
			$(".buttons").hide();
			initTab(item,1);
		} else if (item == 3) {
			$(".buttons").hide();
			$("#inboundopeartionListForm").hide();
			initTab(item,2);
		} else if (item == 4) {
			$(".buttons").hide();
			$("#inboundopeartionListForm").hide();
			initTab(item,3);
		} else if (item == 5) {
			$(".buttons").hide();
			$("#inboundopeartionListForm").hide();
			initTab(item,4);
		} else if (item == 6) {
			$(".buttons").hide();
			$("#inboundopeartionListForm").hide();
			initTab(item,5);
		} else if (item == 7) {
			$(".buttons").hide();
			$("#inboundopeartionListForm").hide();
			initTab(item,6);
		} else if (item == 8) {
			$(".buttons").hide();
			$("#inboundopeartionListForm").hide();
			initTab(item,7);
		} else if (item == 9) {
			$(".buttons").hide();
			$("#inboundopeartionListForm").hide();
			initTab(item,4);
		} else if (item == 10) {
			$(".buttons").hide();
			$("#inboundopeartionListForm").hide();
			initTab(item,4);
		} else if (item == 11) {
			$(".buttons").hide();
			$("#inboundopeartionListForm").hide();
			initTab(item,6);
		} else if (item == 12) {
			$(".buttons").hide();
			$("#inboundopeartionListForm").hide();
			initTab(item,6);
		}
	};
    //初始化查询按钮
	var initBtn=function(){
		$(".planManagerQuery").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$(".modify")
				.click(
						function() {
							var data = $('[data-role="inboundoperationGrid"]')
									.getGrid().selectedRowsIndex();
							var $this = $(this);
							if (data.length == 0) {
								$this.message({
									type : 'warning',
									content : '请选择要修改的入库作业'
								});
								return;
							}
							$.get(config.getResource()+ "/pages/inbound/inboundoperation/update_inboundoperation.jsp")
									.done(
											function(data) {
												dialog = $(data);
												dialog.find(".form_datetime").datetimepicker({
												    autoclose: true,
												    isRTL: Metronic.isRTL(),
												    format: "yyyy-mm-dd hh:ii:ss",
												    todayBtn:true,
												    pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
												});
												
												dialog.find('#save').on(
														'click', function() {
														}).end().modal({
													keyboard : true
												});
											});
						});
		$(".check").click(
				function() {
					var data = $('[data-role="inboundoperationGrid"]')
							.getGrid().selectedRowsIndex();
					var $this = $(this);
					if (data.length == 0) {
						$this.message({
							type : 'warning',
							content : '请选择要查看的入库作业'
						});
						return;
					}
					window.location.href = "#/inboundoperation/get?page=" + page
							+ "&state=" + page;
				});
	};
	
	
	// 初始化入库作业列表
	var initTab = function(item,state) {
		var columns;
		if(item==1){
		 columns = [
				{
					title : "船号",
					name : "shipName"
				},
				{
					title : "船名",
					name : "shipRefName"
				},
				{
					title : "预计到港",
					name : "arrivalStartTime"
				},
				{
					title : "货品",
					render : function(item, name, index) {
						 var goodsmsg = item.goodsMsg;
						 var ptIds='';
						pNameHtml = '<table class="table inmtable">';
						if(goodsmsg!=null&&goodsmsg!=""&&goodsmsg.length>0){
						for (var i = 0; i < goodsmsg.length; i++) {
							var itemgoodsmsg=goodsmsg[i];
							if(ptIds.indexOf('a'+itemgoodsmsg.productId+'a')==-1){
								ptIds+='a'+itemgoodsmsg.productId+'a';
								pNameHtml += '<tr><td style="border-bottom:1px solid #ddd;"><a href="javascript:void(0)" onclick="InboundOperation.checkgoodsdetail('+itemgoodsmsg.productId+','+index+')">'
									+ itemgoodsmsg.productName + '</a></td></tr>';
							}
						}
						pNameHtml += '</table>';
						return pNameHtml;
						}
						return "";
					}
				},
				{
					title : "数量",
					name : "testgoodstotal",
					render : function(item, name, index) {
						 var goodsmsg = item.goodsMsg;
						 var ptIds='';
						 var goodsNum=new Array();
						 var goodsPt=new Array();
						 var j=0;
						 if(goodsmsg!=null&&goodsmsg!=""&&goodsmsg.length>0){
						 for (var i = 0; i < goodsmsg.length; i++) {
								var itemgoodsmsg=goodsmsg[i];
								if(ptIds.indexOf('a'+itemgoodsmsg.productId+'a')==-1){
									ptIds+='a'+itemgoodsmsg.productId+'a';
									goodsPt[j]="a"+itemgoodsmsg.productId;
									goodsNum["a"+itemgoodsmsg.productId]=itemgoodsmsg.goodsPlan;
									j++;
								}else{
									goodsNum["a"+itemgoodsmsg.productId]=parseFloat(goodsNum["a"+itemgoodsmsg.productId])+parseFloat(itemgoodsmsg.goodsPlan);
								}
							}
							pNumHtml = '<table class="table inmtable">'
								for (var i = 0; i < goodsPt.length; i++) {
									var hh=goodsPt[i];
										pNumHtml += '<tr><td style="border-bottom:1px solid #ddd;">'
											+ goodsNum[hh] + '</td></tr>';
								}
							pNumHtml += '</table>';
							return pNumHtml;
						 }
						 return "";
					}
				},
				{
					title : "泊位",
					name : "berthName"
				},
				{
					title : "流程状态",
					render : function(item, name, index) {
						flag = item.statusKey;
						if (flag == 2) {
							page = 1;
						} else if (flag == 3) {
							page = 2;
							return "作业方案";
						} else if (flag == 4) {
							page = 2;
							return "作业方案";
						} else if (flag == 5) {
							page = 3;
							return "作业方案";
						}else if (flag == 6) {
							page = 4;
						}else if (flag == 7) {
							page = 5;
						}else if (flag == 8) {
							page = 6;
						}else if (flag == 9) {
							page = 7;
						}
						return item.statusValue;
					}
				},
				{
					title : "操作",
					name : "unitPrice",
					render : function(item, name, index) {
						return '<a href="#/inboundoperation/get?page='
								+ page
								+ '&state='
								+ page
								+'&index='+index+'" class="btn btn-xs blue"><span class="glyphicon glyphicon glyphicon-eye-open" title="详细"></span></a>';
					}
				} ];
		}else{
			columns = [
							{
								title : "流水号",
								name : "id"
							},
							{
								title : "船号",
								name : "shipName"
							},
							{
								title : "船名",
								name : "shipCNname",
								render : function(item, name, index) {
									return "长江号";
								}
							},
							{
								title : "预计到港",
								name : "arrivalTime"
							},{
								title : "货品",
								render : function(item, name, index) {
									var goodsmsg = item.goodsMsg;
									 var ptIds='';
									pNameHtml = '<table class="table inmtable">';
									for (var i = 0; i < goodsmsg.length; i++) {
										var itemgoodsmsg=goodsmsg[i];
										if(ptIds.indexOf('a'+itemgoodsmsg.productId+'a')==-1){
											ptIds+='a'+itemgoodsmsg.productId+'a';
											pNameHtml += '<tr><td style="border-bottom:1px solid #ddd;"><a href="javascript:void(0)" onclick="InboundOperation.checkgoodsdetail('+itemgoodsmsg.productId+','+index+')">'
												+ itemgoodsmsg.productName + '</a></td></tr>';
										}
									}
									pNameHtml += '</table>';
									return pNameHtml;
								}
							},
							{
								title : "数量",
								name : "testgoodstotal",
								render : function(item, name, index) {
									 var goodsmsg = item.goodsMsg;
									 var ptIds='';
									 var goodsNum=new Array();
									 var goodsPt=new Array();
									 var j=0;
									 for (var i = 0; i < goodsmsg.length; i++) {
											var itemgoodsmsg=goodsmsg[i];
											if(ptIds.indexOf('a'+itemgoodsmsg.productId+'a')==-1){
												ptIds+='a'+itemgoodsmsg.productId+'a';
												goodsPt[j]="a"+itemgoodsmsg.productId;
												goodsNum["a"+itemgoodsmsg.productId]=itemgoodsmsg.goodsPlan;
												j++;
											}else{
												goodsNum["a"+itemgoodsmsg.productId]=parseFloat(goodsNum["a"+itemgoodsmsg.productId])+parseFloat(itemgoodsmsg.goodsPlan);
											}
										}
										pNumHtml = '<table class="table inmtable">'
											for (var i = 0; i < goodsPt.length; i++) {
												var hh=goodsPt[i];
													pNumHtml += '<tr><td style="border-bottom:1px solid #ddd;">'
														+ goodsNum[hh] + '</td></tr>';
											}
										pNumHtml += '</table>';
										return pNumHtml;
								}
							},
							{
								title : "泊位",
								name : "goodsPlan",
								render : function(item, name, index) {
									return "#2";
								}
							},
							{
								title : "操作",
								name : "unitPrice",
								render : function(item, name, index) {
									flag = item.productId;
									if (flag == 1) {
										page = 1;
									} else if (flag == 2) {
										page = 2;
									} else if (flag == 3) {
										page = 3;
									} else if (flag == 4) {
										page = 4;
									} else if (flag == 5) {
										page = 5;
									} else if (flag == 6) {
										page = 6;
									} else if (flag == 7) {
										page = 7;
									} else if (flag == 8) {
										page = 8;
									} else if (flag == 8) {
										page = 8;
									}
									return '<a href="#/inboundoperation/get?page='
											+ page
											+ '&state='
											+ state
											+ '" class="btn btn-xs blue"><span class="glyphicon glyphicon glyphicon-eye-open" title="详细"></span></a>';
								}
							} ];
		}
		// js异步加载URL数据
		if($('[data-role="inboundoperationGrid"]').getGrid() != null) {
			$('[data-role="inboundoperationGrid"]').getGrid().destory();
			
		}
			$('[data-role="inboundoperationGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : true,
				url : config.getDomain() + "/inboundoperation/list"
			});
			dataGrid=$('[data-role="inboundoperationGrid"]').getGrid();
	};
	
	var search=function(){
		var params={};
		 $("#inboundopeartionListForm").find('.form-control').each(function(){
             var $this = $(this);
             var key = $this.attr('key');
              if(key){
                 params[key] = $this.attr('data');
             }else{
            	 
            	 var name=$this.attr('name');
            	 if(name){
            		 params[name]=$this.val();
            	 }
            	 
             }
         });
		 $('[data-role="inboundoperationGrid"]').getGrid().search(params);
	}
	var initSearch=function(){
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/product/select",
  			dataType : "json",
  			success : function(data) {
  				$('#productId').typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product.name;
  	                    });
  	                    process(results);
  					},
  				updater: function (item) {
  					var client = _.find(data.data, function (p) {
                        return p.name == item;
                    });
  					$('#productId').attr("data",client.id)
  					return item;
  				}
  				});
  			}
		});
		
		$.ajax({
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
  					var client = _.find(data.data, function (p) {
                        return p.name == item;
                    });
  					$('#shipId').attr("data",client.id)
  					return item;
  				}
  				});
  			}
		});
		}
	
	/******************************************************************************************************************************/
	return {
		initTab : initTab,
		changetab : changetab,
		initSearch:initSearch,
		initBtn:initBtn,
		search:search,
		initView : initView,
		dialogDockNotify: dialogDockNotify,//(码头)接卸任务通知单
		dialogPipingNotify:dialogPipingNotify,//配管作业通知单
		dialogDynamicNotify:dialogDynamicNotify,//(动力班)接卸任务通知单
		dialogBackFlowNotify :dialogBackFlowNotify,//打回流任务通知单
		dialogShowMsg:dialogShowMsg,//选择历史记录
		checkgoodsdetail : checkgoodsdetail,
		openWarning:openWarning,
		openHide:openHide,
		initArrivalInfo:initArrivalInfo,
		showBerthAssess:showBerthAssess,//靠泊评估
		initBerthPlan:initBerthPlan,
		initTransportProgram:initTransportProgram,
		initUnloadingReady:initUnloadingReady,
		initBackFlowPlan:initBackFlowPlan,
		initAmountAffirm:initAmountAffirm
		};

}();