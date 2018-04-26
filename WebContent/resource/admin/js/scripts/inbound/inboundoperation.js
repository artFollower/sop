/**入库作业*/
var InboundOperation =function() {
//	initTab,//初始化入库列表	
//	changetab,//入库列表tab修改
//	initBtn,//初始化列表按钮
//	initSearch,//初始化搜索选项
//	search,//初始化搜素功能
//	initView,//初始化详情头部基本信息
//	initArrivalInfo//初始化入库计划
//  initBerthAssess//初始化靠泊评估
//	initBerthPlan//初始化靠泊方案
//	initTransportProgram//初始化接卸方案
//	initUnloadingReady//初始化接卸准备
//	initBackFlowPlan//初始化打循环方案
//	initAmountAffirm//初始化数量确认
//  cleanToStatus//回退功能	
//	dialogMfInbound;//修改单个入库列表信息
//	dialogTubeCheck//管线检查
//	dialogAllCheck;//最后检查
//	dialogAmountAffirm;//修改数量审核
//	showBerthAssess//靠泊评估
//	dialogDockNotify//(码头)接卸任务通知单
//	dialogDynamicNotify//(动力班)接卸任务通知单
//	dialogDockBackFlowNotify//码头打循环任务通知单
//  dialogStoreBackFlowNotify//库区打循环任务通知单
//	dialogShowMsg//选择历史记录
//	checkgoodsdetail;//显示货品详情
 
//	openWarning//显示隐藏部分
//	openHide//隐藏显示部分
//	countAllGoodsNum//计算所有货品数量和
//  exportInbound//导出入库信息
	var dialog = null; // dialog对话框
	var clientData=null;//数量确认货主数据
	var status = "";//状态
    var itemDetailArrival=null;	
    var isAll=false;
    var itemGoodsData=null;
    var cargoAgentData=null;
    var unloadingOrderNum=0;//保存当前的接卸方案
    var leftBackFlowId=0;//保存当前记录的打循环id
    var isUnloadingPlan=true;//接卸方案防止反复刷新
    var isUnloadingReady=true;//接卸准备防止反复刷新
    var isFirstBackFlow=true;//打循环防止反复刷新
    var isDeleteUnloading=true;//是否是处理删除多次接卸，如果删除不能刷新页面
    var isDeleteBackFlow=true;//是否处理删除多次打循环，如果删除不能刷新页面
    var isTransport=1;
    var tabArray=[{//入库列表tab属性
		'state':0,//判断是否显示查看操作按钮和page做对比
		'params':{'statuskey':'2,3,4,5,6,7,8'}//搜索条件
	},{//入库计划
		'state':1,
		'params':{'statuskey':'2,3,4,5,6,7,8'}
	},{//靠泊方案
		'state':2,
		'params':{'statuskey':'3,4,5,6,7,8'}
	},{//接卸方案
		'state':3,
		'params':{'statuskey':'5,6,7,8'}
	},{//接卸准备
		'state':4,
		'params':{'statuskey':'6,7,8'}
	},{//打循环方案
		'state':5,
		'params':{'statuskey':'7,8'}
	},{//数量确认
		'state':6,
		'params':{'statuskey':'8'}
	}];
    /*******************************initTab初始化入库列表************************************************/
    /** 初始化入库作业列表*/
	var initTab = function(item,state,params) {
		//入库作业 1  转输作业2
		params.item=item;
		params.isTransport=util.isNull($("#isTransport").text(),1);
		isTransport=parseInt(util.isNull($("#isTransport").text(),1));
		params.isAllChecked=$("#isShowAll").is(":checked");
		var columns=[
		 {title : "预计到港",render:function(item){
			 return util.getSubTime(item.arrivalStartTime,2);
		 }},{title : "船舶英文名",render:function(item){
			return "<a href='javascript:void(0)' onClick='InboundOperation.dialogMfInbound("+item.id+")'>"+(isTransport&&isTransport==2?"转输输入":item.shipName)+"</a>";
		}},{title : "船舶中文名",render:function(item){
			if(item.arrivalType==1){return item.shipRefName;			
			}else if(item.arrivalType==3){return item.shipRefName+'<label style="color:#00aa99">(通过)<label>';
			}
		}},{title : "船代", name : "shipAgentCode"},{title : "泊位", name : "berthName"}
		,{title : "申报状态", name : "report"},{title : "货品", render : function(item, name, index) {
						if(item.goodsMsg!=null&&item.goodsMsg!=""&&item.goodsMsg.length>0){	
					  var data=handleGoodsData(item.goodsMsg);
						var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					    for(var itemGoods=0;itemGoods<data.length;itemGoods++){
					    	if(itemGoods!='contains'){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label><a href="javascript:void(0)"'
					    		+' onclick="InboundOperation.checkgoodsdetail('+data[itemGoods].productId+','+index+')">'
					    		+ data[itemGoods].productName + '</a></label></td></tr>';
					    	}}
					    html += '</table>';
					    return html;
						}
						return "";
					    }}
		,{title : "数量(吨)",render : function(item, name, index) {
						if(item.goodsMsg!=null&&item.goodsMsg!=""&&item.goodsMsg.length>0){	
					  var data=handleGoodsData(item.goodsMsg);
						var html='<table class="table inmtable" style="margin-bottom: 0px;">';
						 for(var itemGoods=0;itemGoods<data.length;itemGoods++){
					    	if(itemGoods!='contains'){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label>'
								+ util.toDecimal3(data[itemGoods].goodsNum,true)+ '</label></td></tr>';
					    	}
					    }
					    html += '</table>';
					    return html;
						}
						return "";
					}}
		,{title : "流程状态", render : function(item, name, index) {
			    var flag=item.statusKey;//流程状态 2,3,4,5,6,7,8,9
			     if(flag==2){//入库计划
			      return "<label style='color:#99CC33;margin-left: 8px;'>"+item.statusValue+handleStatus(item.alifreviewStatus)+"</label>";
			     }
			     else if(flag==3){//靠泊评估
			    	 if(item.bhasreviewStatus!=0&&item.bhasreviewStatus!=2){
							return "<label style='color:#3399CC;margin-left: 8px;'>靠泊评估"+handleStatus(item.bhasreviewStatus)+"</label>";
							}else{
							return "<label style='color:#666699;margin-left: 8px;'>靠泊方案"+handleStatus(item.bhpnreviewStatus)+"</label>";
							} 
			     }
			     else if(flag==4){//靠泊方案
			     return "<label style='color:#666699;margin-left: 8px;'>靠泊方案"+handleStatus(item.bhpnreviewStatus)+"</label>"; 
			     }else{
					if(item.goodsMsg!=null&&item.goodsMsg!=""&&item.goodsMsg.length>0){	
						  var data=handleGoodsData(item.goodsMsg);
							  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
							  for(var itemGoods=0;itemGoods<data.length;itemGoods++){
							    	if(itemGoods!='contains'){
							    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
										+ handleGoodsStatus(data[itemGoods],item.arrivalType) + '</td></tr>';
							    	}}
							    html += '</table>';
							    return html;
					} 
				}}
					},{title : "操作",width:50,render : function(item, name, index){
							var flag= item.statusKey;
							if(flag<5&&flag!=null){
							return '<div style="width:50px;"  class="input-group-btn"><a href="#/'+(isTransport==2?'inboundzhuanshu':'inboundoperation')+'/get?state='+ getPage(flag)+'&arrivalId='+ item.id+'&isTransport='+isTransport+'" class="btn btn-xs blue" style=" margin-left: 8px;">'
									+'<span class="glyphicon glyphicon-eye-open" title="详细"></span></a>'
									+'<a href="javascript:void(0)" onclick="Outbound.fileManage('+item.id+',1)" style="margin:0 2px;" class="btn btn-xs blue"><span class="fa fa-file" title="附件管理"></span></a>'
									+'<a href="javascript:void(0)" onclick="InboundOperation.allDownload('+item.transportId+','+item.id+')" style="margin:0 2px;" class="btn btn-xs blue"><span class="fa fa-file-excel-o" title="一键导出"></span></a></div>';
							}else{
								if(item.goodsMsg!=null&&item.goodsMsg!=""&&item.goodsMsg.length>0){	
									  var data=handleGoodsData(item.goodsMsg);
									  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
									  for(var itemGoods=0;itemGoods<data.length;itemGoods++){
									    	if(itemGoods!='contains'){
									    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label style="color:orange;margin-left: 8px;">'
												+ '<a href="#/'+(isTransport==2?'inboundzhuanshu':'inboundoperation')+'/get?state='+ getPage(flag,data[itemGoods])+'&arrivalId='+ item.id+'&productId='+data[itemGoods].productId+'&isTransport='+isTransport+'" class="btn btn-xs blue">'
												+'<span class="glyphicon glyphicon glyphicon-eye-open" title="详细" style="margin-top: 1px; top: 1px; margin-bottom: 1px;"></span></a>' 
												+'<a href="javascript:void(0)" onclick="Outbound.fileManage('+item.id+',1)" style="margin:0 2px;" class="btn btn-xs blue"><span class="fa fa-file" title="附件管理"></span></a>'
												+'<a href="javascript:void(0)" onclick="InboundOperation.allDownload('+data[itemGoods].transportId+','+item.id+')" style="margin:0 2px;" class="btn btn-xs blue"><span class="fa fa-file-excel-o" title="一键导出"></span></a></label></td></tr>';
									    	}
									    }
									  
									    html += '</table>';
									    return html;
								}	
							}
						}
					}];
		if(item==6){
			columns.splice(8,2
			,{title:"操作",render:function(item,name,index){
				var flag=item.statuskey;
				if(item.goodsMsg!=null&&item.goodsMsg!=""&&item.goodsMsg.length>0){	
					  var data=handleGoodsData(item.goodsMsg);
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var itemGoods=0;itemGoods<data.length;itemGoods++){
					    	if(itemGoods!='contains'){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label style="color:orange;margin-left: 8px;">'
								+ '<a href="#/'+(isTransport==2?'inboundzhuanshu':'inboundoperation')+'/get?state=5&arrivalId='+ item.id+'&productId='+data[itemGoods].productId+'&isTransport='+isTransport+'" class="btn btn-xs blue">'
								+'<span class="glyphicon glyphicon glyphicon-eye-open" title="详细" style="margin-top: 1px; top: 1px; margin-bottom: 1px;"></span></a>' + '</label></td></tr>';
					    	}
					    }
					    html += '</table>';
					    return html;
				}
			}});
		}else if(item!=6&&item!=1){
		columns.splice(8,2,
				{title : "流程状态", render : function(item, name, index) {
		    var flag=item.statusKey;//流程状态 2,3,4,5,6,7,8,9
		     if(flag==2){//入库计划
		      return "<label style='color:#99CC33;margin-left: 8px;'>"+item.statusValue+handleStatus(item.alifreviewStatus)+"</label>";
		     }
		     else if(flag==3){//靠泊评估
		    	 if(item.bhasreviewStatus!=0&&item.bhasreviewStatus!=2){
						return "<label style='color:#3399CC;margin-left: 8px;'>靠泊评估"+handleStatus(item.bhasreviewStatus)+"</label>";
						}else{
						return "<label style='color:#666699;margin-left: 8px;'>靠泊方案"+handleStatus(item.bhpnreviewStatus)+"</label>";
						} 
		     }
		     else if(flag==4){//靠泊方案
		     return "<label style='color:#666699;margin-left: 8px;'>靠泊方案"+handleStatus(item.bhpnreviewStatus)+"</label>"; 
		     }else{
				if(item.goodsMsg!=null&&item.goodsMsg!=""&&item.goodsMsg.length>0){	
					  var data=handleGoodsData(item.goodsMsg);
						  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
						  for(var itemGoods=0;itemGoods<data.length;itemGoods++){
						    	if(itemGoods!='contains'){
						    	html+='<tr><td style="border-bottom:1px solid #ddd;">'
									+ handleGoodsStatus(data[itemGoods],item.arrivalType) + '</td></tr>';
						    	}}
						    html += '</table>';
						    return html;
				} 
			}}
				},
				{title:"操作",render:function(item,name,index){
			var flag=item.statusKey;
			if(state<3){
				return '<div style="width:50px;"  class="input-group-btn"><a href="#/'+(isTransport==2?'inboundzhuanshu':'inboundoperation')+'/get?state='+ state+'&arrivalId='+ item.id+'&isTransport='+isTransport+'" class="btn btn-xs blue" style=" margin-left: 8px;">'
				+'<span class=" glyphicon glyphicon-eye-open" title="详细"</span></a>'
				+'<a href="javascript:void(0)" onclick="Outbound.fileManage('+item.id+',1)" style="margin:0 2px;" class="btn btn-xs blue"><span class="fa fa-file" title="附件管理"></span></a></div>';
			}else{
				if(item.goodsMsg!=null&&item.goodsMsg!=""&&item.goodsMsg.length>0){	
					  var data=handleGoodsData(item.goodsMsg);
					  var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					  for(var itemGoods=0;itemGoods<data.length;itemGoods++){
					    	if(itemGoods!='contains'){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label style="color:orange;margin-left: 8px;">'
								+ '<a href="#/'+(isTransport==2?'inboundzhuanshu':'inboundoperation')+'/get?state='+ state+'&arrivalId='+ item.id+'&productId='+data[itemGoods].productId+'&isTransport='+isTransport+'" class="btn btn-xs blue">'
								+'<span class="glyphicon glyphicon glyphicon-eye-open" title="详细"></span></a>' 
								+'<a href="javascript:void(0)" onclick="Outbound.fileManage('+item.id+',1)" style="margin:0 2px;" class="btn btn-xs blue"><span class="fa fa-file" title="附件管理"></span></a></label></td></tr>';
					    	}
					    }
					    html += '</table>';
					    return html;
				}
			}
		}});
		}//第7个开始删除2个添加两个
		//清空grid缓存
		if($('[data-role="inboundoperationGrid"]').getGrid() != null)
		$('[data-role="inboundoperationGrid"]').getGrid().destory();
		
		$('[data-role="inboundoperationGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : true,
				searchCondition:params,
				gridName:(isTransport==1?"inboundOperation":"zhuanshushuru"),
				stateSave:true,
				backCallBack:function(data){
					$("#isTransport").text(data.isTransport);
					$("#inbound_tab"+data.item).parent().addClass("active").siblings().removeClass("active");
					$("#isShowAll").attr('data',data.item);
					if (data.item == 1) {
						$(".mainCtr").show();
						$(".itemCtr").hide();
					} else {
						$(".itemCtr").show();
						$(".mainCtr").hide();
					} 
					if(data.isAllChecked)
						$("#isShowAll").attr('checked',true);
				},
				url : config.getDomain() + "/inboundoperation/list",
				callback:function(){
					$('.inmtable').closest("td").css('padding','0px');
					if(isTransport&&isTransport==2){
						$('[data-role="inboundoperationGrid"]').find(".grid-table-head th[index='0']").text('预计转输');
						$('[data-role="inboundoperationGrid"]').find(".grid-table-head th[index='1']").text('转输类型');
						$('[data-role="inboundoperationGrid"]').find('td[index=2],th[index=2],td[index=3],th[index=3],td[index=4],th[index=4],td[index=5],th[index=5]').addClass("hidden");
					}
				}
		});
	};
	
	/**入库列表tab改变*/
	function changetab(obj, item){
		$("#isShowAll").attr('data',item);
		$(obj).parent().addClass("active").siblings().removeClass("active");
		if (item == 1) {
			$(".mainCtr").show();
			$(".itemCtr").hide();
		} else {
			$(".itemCtr").show();
			$(".mainCtr").hide();
		} 
		if($("#isShowAll").is(":checked")&&item!=1){//
			initTab(item,tabArray[item-1].state,{'statuskey':tabArray[item-1].params.statuskey.toString()+',9'});    
		}else{
			initTab(item,tabArray[item-1].state,tabArray[item-1].params);
		}
	};
	
	
	/**初始化搜索功能船名，产品名*/
	var initSearch=function(type){
		initFormIput();
		if(type==2){
			$(".notTransport").hide();
		}
  		util.urlHandleTypeaheadAllData("/product/select",$('#productId'));
		$(".reset").click(function(){
			$("#shipId,#productId,#startTime,#endTime").val("");
			$("#shipId,#productId").removeAttr("data");
			$("select[name='statuskey']").val('2,3,4,5,6,8');
			$("select[name='arrivalType']").val(0);
			$(".inboundSearch").click();
		});
		$(".inboundSearch").click(function(){
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
	            	 }else{
	            		 params[name]=null;
	            	 }
	             }
	         });
			 var item=$("#isShowAll").attr('data');
			 if($("#isShowAll").is(":checked")&&item&&item!=1){
				 params.statuskey=tabArray[item-1].params.statuskey.toString()+",9";
			 }else if(item&&item!=1){
				 params.statuskey=tabArray[item-1].params.statuskey
			 }
			 params.item=$("#isShowAll").attr('data');
			 params.isTransport=isTransport;
			 params.isAllChecked=$("#isShowAll").is(":checked");
			 $('[data-role="inboundoperationGrid"]').getGrid().search(params);
		});
		
		$("#isShowAll").click(function(){
			$this=$(this);
			var item=$this.attr('data');
			if($this.is(":checked")){//
				if(item&&item!=1)
				initTab(item,tabArray[item-1].state,{'statuskey':tabArray[item-1].params.statuskey.toString()+',9'});    
			}else{
				if(item&&item!=1)
				initTab(item,tabArray[item-1].state,tabArray[item-1].params);
			}
			});
		
}
/********************************************************initView*****************************************************************/
  /**入库作业详情初始化 page 流程位置，state 查看位置，arrivalId 到港id,index 项目项,productId 选择的货品id ，isTransports 是否是转输*/
function initView(state,arrivalId,productId,orderNum,backflowId,isTransports) {
       if(orderNum&&orderNum!=0){
    	   unloadingOrderNum=orderNum;
       }  
       isTransport=isTransports;
       //打循环定位
       if(backflowId&&backflowId!=0){
    	   leftBackFlowId=backflowId; 
       }
       if(isTransport&&isTransport==2){
    	   $("#tab2,.notTransport").hide();
    	   $("#tp1").text("转输类型:");
    	   $("#tp2").text("预计转输日期:");
    	   $("#tab3").text("接卸方案");
    	   $("#tab4").text("转输准备");
       }
       
	$.ajax({ type:"post",
        	     url:config.getDomain()+"/inboundoperation/list",
        	     data:{'id':arrivalId,'orderNum':unloadingOrderNum,'result':0,isTransport:isTransport},
        	     dataType:'json',
        	     success:function(data){
        	    	  var flag=data.data[0].statusKey;
        	    	  if(data.data[0].shipRefName=="转输"){
        	    		  isTransport==2; 
        	    	  }else{
        	    		  isTransport==1;
        	    	  }
       	    		  if(data.data[0].goodsMsg!=null&&data.data[0].goodsMsg!=""&&data.data[0].goodsMsg.length>0){	
       	    		       var mdata=handleGoodsData(data.data[0].goodsMsg);
       	    		       var mpage=getPage(flag,mdata[0]);
       	    		       if(productId==null||productId==undefined){
       	    		    	  productId=mdata[0].productId;
       	    		       }else{
       	    		       for(var i=0;i<mdata.length;i++){
                       	       if(productId==mdata[i].productId){
                       	    	   mpage=getPage(flag,mdata[i]);
                       	       }
       	    		       }} 
       	    		       if(!state){
       	    		    	   handleItem(mpage,mpage,productId,data.data[0]);  
       	    		       }else{
       	    		    	handleItem(mpage,state,productId,data.data[0]);  
       	    		       }
       	    		  } 
        	      
                }});
	};
function  handleItem(page,state,productId,itemData){
			 isUnloadingPlan=true;
		     isUnloadingReady=true;
		     isFirstBackFlow=true;
	
		   itemDetailArrival=itemData;
		   var data=handleGoodsData(itemData.goodsMsg);
		if(!productId){
			productId=data[0].productId;
			itemGoodsData=data[0];
			initDetailView(page,state,false);
			isAll=false;
		}else{
			for(var item=0;item<data.length;item++){
				if(data[item].productId==productId){
					itemGoodsData=data[item];
					break;
				}
			}
			initDetailView(page,state,true);
			isAll=true;
		}
	}
function initDetailView(page,state,isOk){
	if(itemDetailArrival.arrivalType==3){//通过船舶
		$("#tab3,#tab4,#tab5,#tab6").hide();
		if(state>2){
			state=2;
		}
	}
	if("0,1,2".indexOf(state)!=-1){
		isOk=false;
	}
	//隐藏添加多次接卸，多次打循环按钮
	if(page>6){
		$("#addUnloading").hide();
	}else{
		$("#addUnloading").show();
	}
	
	 $("#tab1,#tab2,#tab3,#tab4,#tab5,#tab6,#addUnloading,#addBackFlow").unbind("click");//在事件之前解绑不能在事件之后解绑
	//加载不同页面
	$("#tab1").click(function() {
		                initTopMsg(page,false);
						$(this).parent().addClass("active").siblings().removeClass("active");
						isUnloadingPlan=true;
						isUnloadingReady=true;
						isFirstBackFlow=true;
						if (page > 1) {
							$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/inboundplan/add.jsp",function(){
								$(this).find("#submit").hide();
							});
						} else {
							$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/inboundplan/add.jsp");
						}
					});

	$("#tab2").click(function() {
                		$(this).parent().addClass("active").siblings().removeClass("active");
		                initTopMsg(page,false);
		                isUnloadingPlan=true;
		                isUnloadingReady=true;
		                isFirstBackFlow=true;
						if (page > 2) {
							$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/berthplan/get.jsp");
						} else {
							$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/berthplan/add.jsp");
						}
					});
	$("#tab3").click(function() {
	                	$(this).parent().addClass("active").siblings().removeClass("active");
	                	isFirstBackFlow=true;
	                	isUnloadingReady=true;
                        if(isUnloadingPlan){
                        isUnloadingPlan=false;
						initTopMsg(page,true);
						if($("#tab3_"+unloadingOrderNum).attr("status")){
							page=getPage($("#tab3_"+unloadingOrderNum).attr("status"),itemGoodsData);
						};
						if (page > 3){
							$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/unloadingplan/get.jsp");
						} else {
							$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/unloadingplan/add.jsp");
						}
                        }
					});
	$("#tab4").click(function() {
		                $(this).parent().addClass("active").siblings().removeClass("active");
						isUnloadingPlan=true;
						isFirstBackFlow=true;
						if(isUnloadingReady){
						initTopMsg(page,true);
						isUnloadingReady=false;
						if (page > 4) {
							$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/unloadingready/get.jsp");
						} else {
							$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/unloadingready/add.jsp");
						}
						}
					});
	$("#tab5").click(function() {
		               initTopMsg(page,true);
		               $(this).parent().addClass("active").siblings().removeClass("active");
		               isUnloadingPlan=true;
		               isUnloadingReady=true;
		               if(isFirstBackFlow){
		            	  initBackFlow();//初始化打循环
                          isFirstBackFlow=false;		            	   
		               }
					});

	$("#tab6").click(function() {
		                initTopMsg(page,true);
						$(this).parent().addClass("active").siblings().removeClass("active");
						isUnloadingPlan=true;
						isUnloadingReady=true;
						initUnloading(function(){
							if (page > 6) {//初始化多次接卸，多次打循环
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/amountaffirm/get.jsp");
							} else {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/amountaffirm/add.jsp");
							}
						});
						
					});
	//添加多次接卸
	$("#addUnloading").click(function(){
		config.load();
		var num=$(".ul_tab3 li").length-2;
		$.ajax({
			type:'post',
			url:config.getDomain()+"/inboundoperation/addunloading",
			data:{arrivalId:itemDetailArrival.id,productId:itemGoodsData.productId,orderNum:num},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'添加',function(ndata){
					initUnloading();
				});
			}
		});
	});
	//添加多次打循环
	$("#addBackFlow").click(function(){
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/inboundoperation/addbackflow",
			data:{arrivalId:itemDetailArrival.id,productId:itemGoodsData.productId,orderNum:unloadingOrderNum},
			dataType:'json',
			success:function(data){
              util.ajaxResult(data,"添加",function(ndata){
                     initBackFlow();            	  
              });				
			}
		});
	});
	initUnloading();
	   //点击这个tab
	   if(state==7){
			   $("#tab6").click();
	   }else{
		   $("#tab"+state).click();
		  //多点击一次清除下拉列表
		   if(state==3||state==4||state==5){
			   $("#tab"+state).click(); 
		   }
	   }
	};
	/**初始化多次接卸方案，多次接卸准备，多次打循环*/
	function initUnloading(callback){
		config.load();
		$.ajax({
			type:"post",
			url:config.getDomain() + "/inboundoperation/list",
			data:{arrivalId:itemDetailArrival.id,productId:itemGoodsData.productId,unloadingType:1,result:7},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,"多次接卸",function(ndata){
				var unloadingData=ndata[0].unloadingplan;//多次接卸	
				   $(".unloadingLi").remove();
					//多次接卸
				   for(var i=0;i<unloadingData.length;i++){
						if(unloadingData[i].orderNum==0){
                        $("#tab3_0").attr('data',unloadingData[i].transportId);	
                        $("#tab4_0,.tab4_0").attr("data",unloadingData[i].workId);
                        $("#tab3_0").attr("status",unloadingData[i].status);
					    $("#tab4_0,.tab4_0").attr("status",unloadingData[i].reviewStatus);
						}else if(unloadingData[i].orderNum!=0){
						var html='<li class="unloadingLi '+ isActive(unloadingData[i].orderNum)+'"><a onclick="InboundOperation.changeUnloadData('+unloadingData[i].orderNum+',this,3);return false;"  data="'+unloadingData[i].transportId+'" status="'+unloadingData[i].status+'" id="tab3_'+unloadingData[i].orderNum+'" href="javascript:void(0);">接卸方案'+unloadingData[i].orderNum
						+'<span title="清除" class="glyphicon glyphicon-remove " onclick="InboundOperation.deleteUnloading('+unloadingData[i].transportId+','+unloadingData[i].workId+','+unloadingData[i].status+','+unloadingData[i].orderNum+')" style="padding-left:40px;"></span></a></li>';
						$(html).insertBefore($(".ul_tab3>.divider"));
						var html1='<li class="unloadingLi '+ isActive(unloadingData[i].orderNum)+'"><a onclick="InboundOperation.changeUnloadData('+unloadingData[i].orderNum+',this,4);return false;" data="'+unloadingData[i].workId+'" id="tab4_'+unloadingData[i].orderNum+'" href="javascript:void(0);">接卸准备'+unloadingData[i].orderNum+'</a></li>';
						$(html1).insertBefore($(".ul_tab4>.divider"));
						}
					}
				   if(unloadingOrderNum!=0){
					   $("#tab3_0").parent().removeClass('active');
				   }
				   
				   if(callback)
					   callback();
				},true);
			}
		});
	}
	function  isActive(orderNum){
		if(orderNum==unloadingOrderNum){
			return 'active';
		}else{
			return '';
		}
	};
	//初始化多次打循环
	function  initBackFlow(){
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/inboundoperation/list",
			data:{arrivalId:itemDetailArrival.id,productId:itemGoodsData.productId,unloadingType:2,orderNum:unloadingOrderNum,result:7},
			dataType:'json',
			success:function(data){
               util.ajaxResult(data,'打循环初始化',function(ndata){
            	   $(".backflowLi").remove();
            	   var backflowData=ndata[0].backflowplan;
            	   var mItem=0;
            	   var leftItem=0;
            	   var minId=backflowData[0].backflowId; 
            	   for(var i=0;i<backflowData.length;i++){
            		   if(backflowData[i].orderNum||backflowData[i].orderNum==0){
            			   if(leftBackFlowId!=0){//是否打循环遗留
            				   if(leftBackFlowId==backflowData[i].backflowId){
            					   leftItem=i;
            			   }
            			   }else{//否则选择第一个
            				   if(backflowData[i].backflowId<=minId){
            					   mItem=i;
                                 minId=backflowData[i].backflowId;           				   
                			   }
            			   }
            			   
            			   var item=i==0?'':i+'<span title="清除" class="glyphicon glyphicon-remove " onclick="InboundOperation.deleteBackFlow('+backflowData[i].backflowId+')" style="padding-left:40px;"></span>';
            			   var html='<li class="backflowLi"><a onclick="InboundOperation.changeBackFlowData('+backflowData[i].backflowId+','+backflowData[i].status+',this)" id="tab5_'+backflowData[i].backflowId+'">打循环方案'+item
            			   +'</a></li>';
            			   $(html).insertBefore($(".ul_tab5>.divider"));
            		   }
            	   }
            	   if(leftBackFlowId!=0){
            		   leftBackFlowId=backflowData[leftItem].backflowId;
            		   $("#tab5_"+leftBackFlowId).parent().addClass('active');
            		   itemGoodsData.backflowId=leftBackFlowId;
            		   itemGoodsData.backflowStatus=backflowData[leftItem].status;
            	   }else{
            		   $("#tab5_"+minId).parent().addClass('active');
            		   itemGoodsData.backflowId=minId;
            		   itemGoodsData.backflowStatus=backflowData[mItem].status;
            	   }
            	   $("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/backflowplan/add.jsp");
               },true);				
			}
		});
	}
/**初始化头部基本信息*/
function initTopMsg(page,isOk){
	//初始化头部基本信息
	$("#arrivaldetailForm").find('.mda').each(function(){
        var $this = $(this);
           var key=$this.attr('key');
           if(key=="goodsMsg"){
        	   if($('[data-role="goodsmsgGrid"]').getGrid()){
        		   $('[data-role="goodsmsgGrid"]').getGrid().destory();
        	   }
        	   var columns=[{title:"货品",name:"productName"},{title:"货主",name:"clientName"},{title:"数量(吨)",
					name:"goodsPlan",render:function(item){
						return util.toDecimal3(item.goodsPlan,true);
					}},{title:"货代",name:"cargoAgentCode"},{title:"备注",name:"description"}];
				$('[data-role="goodsmsgGrid"]').grid({
					columns:columns,
					isShowIndexCol : false,
					isShowPages : false,
					isUserLocalData:true,
					localData:getGoodsMsg(itemDetailArrival.goodsMsg,page,isOk,itemGoodsData.productId)
				});
				var  totalHtml="<div class='form-group' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>";
				var handleGoods=handleGoodsData(getGoodsMsg(itemDetailArrival.goodsMsg,page,isOk,itemGoodsData.productId));
				for(var i=0;i<handleGoods.length;i++){
					if(handleGoods[i].productName){
                   totalHtml+="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>"+handleGoods[i].productName+": </label>" +
                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(handleGoods[i].goodsNum,true)+" 吨</label>"					
					}
				}
				totalHtml+="</div>";
				$('[data-role="goodsmsgGrid"]').find("div table tbody tr td").append(totalHtml);
           }else if(key=="arrivalStartTime"){
        	   $this.text(itemDetailArrival[key].substring(0,16));
           }else{
        	   $this.text(itemDetailArrival[key]);
           }
    });
	if(isTransport&&isTransport==2)
	 $("label[key='shipName']").text("转输输入");
}
/*******************************************************入库计划initArrivalInfo****************************************************/
	/**初始化入库计划*/
	function initArrivalInfo(obj,items){
		initArrivalInfoCtr(obj,items);
		initArrivalInfoMsg(obj,items);
		handleArrivalInfoIsTransport(obj);
	};
	function initArrivalInfoCtr(obj,items){
		initFormIput();
		util.handleTypeahead([{'key':'1','value':'国内港'},{'key':'2','value':'国外港'}],$(obj).find("#portType"),'value','key');
		$(obj).find("#save,#submit").click(function(){
			$this=$(this);
			var status=$this.attr("key");
			if(config.validateForm($(obj).find(".arrivalinfo"))) {
			config.load();
			$.ajax({
				type : "post",
				url : config.getDomain() + "/inboundoperation/updatearrivalinfo",
				data : {
					"id" :itemDetailArrival.arrivalinfoId,
					"cjTime":util.formatLong(util.getTimeVal($(obj).find("#cjTime"))),
					"tcTime":util.formatLong(util.getTimeVal($(obj).find("#tcTime"))),
					"norTime":util.formatLong(util.getTimeVal($(obj).find("#norTime"))),
					"anchorTime":util.formatLong(util.getTimeVal($(obj).find("#anchorTime"))),
					"pumpOpenTime":util.formatLong(util.getTimeVal($(obj).find("#pumpOpenTime"))),
					"pumpStopTime":util.formatLong(util.getTimeVal($(obj).find("#pumpStopTime"))),
					"leaveTime":util.formatLong(util.getTimeVal($(obj).find("#leaveTime"))),
					"tearPipeTime":util.formatLong(util.getTimeVal($(obj).find("#tearPipeTime"))),
					"lastLeaveTime":util.formatLong($(obj).find("#lastLeaveTime").val()),
					"port":$(obj).find("#port").val(),
					"portType":$(obj).find("#portType").attr('data'),
					"portNum":$(obj).find("#portNum").val(),
					"arrivalId":itemDetailArrival.id,
					"report":$(obj).find("#report").val(),
					"shipInfo":$(obj).find("#shipInfo").val(),
					"note":$(obj).find("#note").val(),
					"createUserId":itemDetailArrival.createUserId,
					"status":status==0?undefined:status,
					"isTransport":isTransport
				},
				dataType : "json",
				success:function(data){
					util.ajaxResult(data,(status==0?'保存':'提交'),function(){
						if(itemDetailArrival.port!=$(obj).find("#port").val()){
							$.ajax({
								type:"post",
								url:config.getDomain()+"/ship/update",
								data:{"shipDtoList":JSON.stringify({"ship":{'id':itemDetailArrival.shipId,'port':$(obj).find("#port").val()}})},
								dataType:"json",
								success : function(data){
									util.ajaxResult(data,'保存',function(){
										refreshViewData(1,itemDetailArrival.id,null);
									},true);
									}
							});
						}else{
							refreshViewData(1,itemDetailArrival.id,null);	
						}
					});
				}
			});
			}
		});
	};
	
	function initArrivalInfoMsg(obj,items){
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain() + "/inboundoperation/list",
			data : {"id":itemDetailArrival.arrivalinfoId,"result":1},
			dataType : "json",
			success:function(data){
				util.ajaxResult(data,"获取入库计划信息",function(ndata){
					if(ndata&&ndata.length>0){
						var mdata=ndata[0];
						if(items==1){//未提交
							try {for( var itemName in mdata) {
									if(itemName.indexOf('Time') != -1 && util.validateFormat(mdata[itemName])) {
										util.initTimeVal($(obj).find("#" + itemName),mdata[itemName]);
									}
								}} catch(err){}		
								util.initTimeVal($(obj).find("#anchorTime"),itemDetailArrival.arrivalStartTime);	
								insertAll();
								$(obj).find("#workTime").text(util.getDifferTime(mdata.pumpOpenTime,mdata.pumpStopTime));
								$(obj).find("#lastLeaveTime").val(mdata.lastLeaveTime);
								$(obj).find("#port").val(mdata.port==null?itemDetailArrival.port:mdata.port);
								$(obj).find("#portNum").val(mdata.portNum);
								$(obj).find("#report").val((util.isNull(mdata.report)==''?'未申报':mdata.report));
								$(obj).find("#shipInfo").val((util.isNull(mdata.shipInfo)==''?'未收到':mdata.shipInfo));
								$(obj).find("#note").val(mdata.note);
								$(obj).find("#portType").val((util.isNull(mdata.portType,1)==1?'国内港':'国外港')).attr('data',mdata.portType);
								$(obj).find("#overTime").val(mdata.overTime);//滞港时间
								$(obj).find("#repatriateTime").val(mdata.repatriateTime);//派遣时间
								$(obj).find("#shipAgentId").val(itemDetailArrival.shipAgentCode);//船代
								$(obj).find(".timepicker-24:focus").blur();//初始化使获得焦点的失去焦点
						}
					}
				},true);
			}
		});
		//管安排，罐安排(获取该船的所有罐和管)
		var ids="";
		for(var item=0;item<handleGoodsData().length;item++){
			if(handleGoodsData()[item].transportId!=undefined)
			ids+=handleGoodsData()[item].transportId+",";
		}
		$.ajax({
			type : "post",
			url : config.getDomain() + "/inboundoperation/list",
			data : {
				"id":itemGoodsData.transportId,
				"transportIds":ids.substring(0,ids.length-1),
				"result":4
			},
			dataType : "json",
			success:function(data){
				util.ajaxResult(data,"获取管罐信息",function(ndata){
					if(ndata&&ndata.length>0){
						if(items==1){
							$("#tankNames").val(util.isNull(ndata[0].tankNames));
							$("#tubeNames").val(util.isNull(ndata[0].tubeNames));	
						}
					}
				},true);
				}
		});
	};
	
	function handleArrivalInfoIsTransport(obj){
		if(isTransport&&isTransport==2){
			$(obj).find("#tp1").text("预计转输开始时间:");
			$(obj).find("#tp2").text("预计转输结束时间:");
			$(obj).find(".notTransport").hide();
		}
	}
/*******************************************************靠泊评估initBerthAssess****************************************************/
	// 查看靠泊评估
	function showBerthAssess(item) {
		initFormIput();
		if(item!=2){//未通过
		$.get(config.getResource()+ "/pages/inbound/inboundoperation/berthassess/add.jsp")
				.done(function(data) {
					var mdialog = $(data);
					//初始化时间控件
					util.initTimePicker(mdialog);
					 if(item==1){//审核人信息初始化(获取登陆信息)
						mdialog.find("#reviewUserId").attr("data",itemDetailArrival.createUserId);
						mdialog.find("#reviewUserId").val(itemDetailArrival.createUserName);
						mdialog.find("#reviewTime").val(util.currentTime(0));
						mdialog.find(".commentDiv,.createDiv,.secDiv,.securityCodeDiv").show();
						mdialog.find(".reviewDiv,.firDiv").hide();
					}else if(item==3){//驳回
						mdialog.find("#createUserId").attr("data",itemDetailArrival.createUserId);
						mdialog.find("#createUserId").val(itemDetailArrival.createUserName);
						mdialog.find("#createTime").val(util.currentTime(0));
						mdialog.find(".secDiv,.securityCodeDiv").hide();
						mdialog.find(".commentDiv,.reviewDiv,.createDiv,.firDiv").show();
					}else{//指定人信息初始化(获取登陆人信息)
						mdialog.find("#createUserId").attr("data",itemDetailArrival.createUserId);
						mdialog.find("#createUserId").val(itemDetailArrival.createUserName);
						mdialog.find("#createTime").val(util.currentTime(0));
						mdialog.find(".commentDiv,.createDiv,.reviewDiv,.secDiv,.securityCodeDiv").hide();
						mdialog.find(".firDiv").show();
						}
					mdialog.find("#berthId").attr("data",itemDetailArrival.berthId);
					mdialog.find("#berthId").val(itemDetailArrival.berthName);
					//初始化信息
					$.ajax({
						type:"get",
						url:config.getDomain()+"/inboundoperation/list?id="+itemDetailArrival.berthassessId+"&result="+2,
						dataType:"json",
						success:function(data){
							if(data.code=='0000'){
								var mdata=data.data[0];
								mdialog.find("#weather").val(util.isNull(mdata.weather));
								mdialog.find("#windDirection").val(util.isNull(mdata.windDirection));
								mdialog.find("#windPower").val(util.isNull(mdata.windPower));
								mdialog.find("#reason").val(util.isNull(mdata.reason));
								mdialog.find("#comment").val(util.isNull(mdata.comment));
								if(mdata.security!=null)
								mdialog.find("#security").val(util.isNull(mdata.security));
								if(item==3||item==1){
								mdialog.find("#createUserId").val(util.isNull(mdata.createUserName));
								mdialog.find("#createUserId").attr("data",mdata.createUserId);
								mdialog.find("#reviewUserId").val(util.isNull(mdata.reviewUserName));
								mdialog.find("#reviewUserId").attr("data",mdata.reviewUserId);
								}
								if(mdata.createTime!="")
								mdialog.find("#createTime").val(mdata.createTime.split(" ")[0]);
								if(mdata.reviewTime!="")
								mdialog.find("#reviewTime").val(mdata.reviewTime.split(" ")[0]);
								//验证码内容
								mdialog.find("#securityCodeContent").text("船名："+itemDetailArrival.shipRefName+"；到港日期："+util.getSubTime(itemDetailArrival.arrivalStartTime,1)+"；计划接卸"+
								getGoodsMsgStr(itemDetailArrival.goodsMsg)+"；泊位："+itemDetailArrival.berthName+"；申请原因："+util.isNull(mdata.reason)+"。"		
								);
							}else{
								$("body").message({
									type:'error',
									content:'同步信息失败'
								})
							}
						}
					});
					
					//泊位	
			  		util.urlHandleTypeahead("/berth/list",mdialog.find("#berthId"));
			  		util.urlHandleTypeahead("/auth/user/getUserByPermission?permission="+mdialog.find("#reviewCodeUserId").attr("code"),mdialog.find("#reviewCodeUserId"));
				mdialog.find("#submit,#save").click(function(){
					$this=$(this);
                   var  status=$this.attr("key");	
                   var  nData;
                   if(status==0||status==1){
                	   nData={'id':itemDetailArrival.berthassessId,
							'arrivalId':itemDetailArrival.id,
							'weather':mdialog.find("#weather").val(),
							'windDirection':mdialog.find("#windDirection").val(),
							'windPower':mdialog.find("#windPower").val(),
							'reason':mdialog.find("#reason").val(),
							'security':mdialog.find("#security").val(),
							'createUserId':mdialog.find("#createUserId").attr("data"),
							'createTime':util.formatLong(mdialog.find("#createTime").val()+" 00:00:00"),
							'reviewUserId':mdialog.find("#reviewUserId").attr("data"),
							'reviewTime':util.formatLong(mdialog.find("#reviewTime").val()+" 00:00:00"),
							'reviewStatus':status// 保存
                	   }
                   }else{
                	   if(status==2&&(util.isNull(mdialog.find("#comment").val())==''||util.isNull(mdialog.find("#comment").val())=='不通过')){
                		   mdialog.find("#comment").val('通过');
   					}else if(status==3&&(util.isNull(mdialog.find("#comment").val())==''||util.isNull(mdialog.find("#comment").val())=='通过')){
   						mdialog.find("#comment").val('不通过');
   					}
                	   
                	   nData={
                			   'id':itemDetailArrival.berthassessId,
   							   'arrivalId':itemDetailArrival.id,
   							   'comment':mdialog.find("#comment").val(),
   							   'reviewUserId':mdialog.find("#reviewUserId").attr("data"),
							   'reviewTime':util.formatLong(mdialog.find("#reviewTime").val()+" 00:00:00"),
							   'reviewStatus':status// 保存
                	   }
                	   if($this.attr('code')&&$this.attr('code').length>1){
                			   nData.securityCode=util.isNull(mdialog.find("#securityCode").val());
                    		   nData.isSecurity=1;
                    		   nData.object=$this.attr('code');   
                	   }else{
                		   nData.isSecurity=0;
                	   }
                   }
                   
                   
					if(config.validateForm(mdialog.find(".berthassess"))) {
					//更新泊位信息
					if(mdialog.find("#berthId").attr('data')!=null&&mdialog.find("#berthId").attr('data')!=""&&(status==0||status==1)){
						$.ajax({
								type:"post",
								url:config.getDomain()+ "/inboundoperation/updateberthprogram",
								data:{
									'id':itemDetailArrival.berthprogramId,
									'berthId':mdialog.find("#berthId").attr('data'),
									'status':-1
								},
								dataType:"json",
							  success:function(data){}
						});
						}
					config.load();
					$.ajax({
						type:"post",
						url:config.getDomain()+"/inboundoperation/updateberthassess",
						data:nData,
						dataType:"json",
						success:function(data){
							util.ajaxResult(data,msgContent(status),function(ndata){
								if(status!=0){ 
									mdialog.remove();
								}
									refreshViewData(2,itemDetailArrival.id,null);
							});
							}
					});
					
					}
				});	
					mdialog.modal({
						keyboard : true
					});
				});
		}else{
			$.get(config.getResource()+ "/pages/inbound/inboundoperation/berthassess/get.jsp")
			.done(function(data) {
				var mdialog = $(data);
				mdialog.find("#berthId").text(itemDetailArrival.berthName);
				$.ajax({
					type:"get",
					url:config.getDomain()+"/inboundoperation/list?id="+itemDetailArrival.berthassessId+"&result="+2,
					dataType:"json",
					success:function(data){
						if(data.code=='0000'){
							var mdata=data.data[0];
							mdialog.find("#weather").text(util.isNull(mdata.weather));
							mdialog.find("#windDirection").text(util.isNull(mdata.windDirection));
							mdialog.find("#windPower").text(util.isNull(mdata.windPower));
							mdialog.find("#reason").val(util.isNull(mdata.reason));
							mdialog.find("#comment").val(util.isNull(mdata.comment));
							mdialog.find("#security").val(util.isNull(mdata.security));
							mdialog.find("#createUserId").text(util.isNull(mdata.createUserName));
							mdialog.find("#reviewUserId").text(util.isNull(mdata.reviewUserName));
							mdialog.find("#createTime").text(mdata.createTime.split(" ")[0]);
							mdialog.find("#reviewTime").text(mdata.reviewTime.split(" ")[0]);
						}else{
							$("body").message({
								type:'error',
								content:'获取信息失败'
							})
						}
					}
				});
				mdialog.modal({
					keyboard : true
				});
			});
		}
	};
/*******************************************************靠泊方案initBerthPlan****************************************************/	
	/**初始化靠泊方案*/
	function initBerthPlan(obj,iItem){
		if(iItem==1){
		initBerthPlanCtr(obj);
		initBerthPlanMsg(obj);
		}else{
			$(obj).find(".berthAssess").unbind('click').bind('click',function(){
				showBerthAssess(itemDetailArrival.bhasreviewStatus);
			});
		$.ajax({
			type : "post",
				url : config.getDomain()+"/inboundoperation/list",
				dataType : "json",
				data:{"id":itemDetailArrival.berthprogramId,"result":3},
				success : function(data) {
	                var mdata=data.data[0];
	                $(obj).find("#safeInfo").text(util.isNull(mdata.safeInfo));
	                $(obj).find("#comment").text(util.isNull(mdata.comment));
	                $(obj).find("#richDraught").text(util.isNull(mdata.richDraught));
	                $(obj).find("#windPower").text(util.isNull(mdata.windPower));
	                $(obj).find("#createUserId").text(util.isNull(mdata.createUserName));
	                $(obj).find("#createTime").text(mdata.createTime);
	                $(obj).find("#reviewUserId").text(util.isNull(mdata.reviewUserName));
	                $(obj).find("#reviewTime").text(mdata.reviewTime);
	                initGrid(mdata.berthId);
				}
		});
	}
	};
	function initBerthPlanCtr(obj){
		initFormIput();
		util.initTimePicker($(".berthplan"));
		util.urlHandleTypeahead("/auth/user/getUserByPermission?permission="+$(obj).find("#reviewCodeUserId").attr("code"),$(obj).find("#reviewCodeUserId"));
		
		$(obj).find(".berthAssess").click(function(){
			showBerthAssess(itemDetailArrival.bhasreviewStatus);
		});
		
		$(obj).find("#reset").click(function(){
			$(".buttons").show();
			initGrid(-1);
		});
		
		$(obj).find(".check").click(function(){
			if(util.validateData($(obj).find('[data-role="berthGrid"]'))){
				var data=$(obj).find('[data-role="berthGrid"]').getGrid().selectedRowsIndex();
				initGrid(data[0]);
			}
			});
		
		$(obj).find("#save,#submit").click(function(){
			var $this = $(this);
			var berthMsg= $(obj).find('[data-role="berthGrid"]').getGrid().getAllItems()[0];
			var status=$this.attr("key");
			if(validateBerthProgramStatus(obj,berthMsg,status)){
				var data={
						"id":itemDetailArrival.berthprogramId,
		  				"berthId":berthMsg.id,
						"arrivalId":itemDetailArrival.id,
		  				"status":status	};
				if(status==0||status==1){
					data.safeInfo=$(obj).find("#safeInfo").val();
					data.richDraught=$(obj).find("#richDraught").val();
					data.windPower=$(obj).find("#windPower").val();
					data.createUserId=itemDetailArrival.createUserId;
					data.createTime=util.formatLong(util.currentTime(1));
				}else if(status==2||status==3){
					 if(status==2&&(util.isNull($(obj).find("#comment").val())==''||util.isNull($(obj).find("#comment").val())=='不通过')){
						 $(obj).find("#comment").val('通过');
 					}else if(status==3&&(util.isNull($(obj).find("#comment").val())==''||util.isNull($(obj).find("#comment").val())=='通过')){
 						$(obj).find("#comment").val('不通过');
 					}
					data.comment=$(obj).find("#comment").val();
					data.reviewUserId=itemDetailArrival.createUserId;
					data.reviewTime=util.formatLong(util.currentTime(1));
				}
				if($this.attr('code')&&$this.attr('code').length>1){
					data.securityCode=util.isNull($(obj).find("#securityCode").val());
					data.isSecurity=1;
					data.object=$this.attr('code');
           	   	}else{
           		   	data.isSecurity=0;
           	   	}
				config.load();
				$.ajax({
					type : "post",
		  			url : config.getDomain()+"/inboundoperation/updateberthprogram",
		  			dataType : "json",
		  			data:data,
		  			success : function(data) {
		  				util.ajaxResult(data,msgContent(status),function(ndata){
		  					if(status==2){
		  						$.ajax({
									type : "post",
									url : config.getDomain() + "/inboundoperation/updatework",
									data : {'arrivalId':itemDetailArrival.id,'status':itemDetailArrival.arrivalType==3?9:5},
									dataType : "json",
									success:function(data){
										util.ajaxResult(data,"提交",function(ndata){refreshViewData(2,itemDetailArrival.id,null);});
									}});
		  					}else{
		  						$('body').message({type: 'success',content: msgContent(status,1)});
								refreshViewData(2,itemDetailArrival.id,null);	
		  					}
		  				},true);
				}
			});
			}
			});
	};
	function validateBerthProgramStatus(obj,data,status){
		var isPass=true;
		if(status==1&&util.isNull($(obj).find("#safeInfo").val())==''){
			$('body').message({type:'warning',content:'请填写安全措施！'});
			return false;
		}
		if($(obj).find('.isChoiceBerth').css('display')!='none'){
			$('body').message({type:'warning',content:'请选择泊位！'});
			return false;
		}
		if(status==1&&itemDetailArrival.bhasreviewStatus!=2){
			var warningstr="";
			if(parseFloat(data.limitLength)<parseFloat(util.isNull(itemDetailArrival.shipLenth)))
				warningstr+='泊位船长不足，';
			if(parseFloat(data.limitDrought)<parseFloat(util.isNull(itemDetailArrival.shipArrivalDraught)))
				warningstr+='泊位吃水不足，';
			if(parseFloat(data.id)!=1){
			if(parseFloat(data.limitDisplacement)<parseFloat(util.isNull(itemDetailArrival.loadCapacity)))
				warningstr+='泊位最大载重吨不足，';}			
			itemDetailArrival.berthId=data.id;
			itemDetailArrival.berthName=data.name;	
			if(warningstr.length!=0){
				$(obj).confirm({
					content:warningstr+'是否申请靠泊评估?',
					callBack:function(){
						$.ajax({
							type : "post",
				  			url : config.getDomain()+"/inboundoperation/updateberthprogram",
				  			dataType : "json",
				  			data:{
				  				"id":itemDetailArrival.berthprogramId,
				  				"berthId":data.id,
				  				"safeInfo":$(obj).find("#safeInfo").val(),
				  				"richDraught":$(obj).find("#richDraught").val(),
				  				"windPower":$(obj).find("#windPower").val(),
				  				"arrivalId":itemDetailArrival.id,
				  				"status":-1},
				  			success : function(data) {
				  				util.ajaxResult(data,'保存',function(){showBerthAssess(itemDetailArrival.bhasreviewStatus);},true);
				  			}});
					}
				});
				return false;
			}
		}
		return true;
	};
	function initBerthPlanMsg(obj){
		if(itemDetailArrival.bhasreviewStatus==1){
			$(obj).find(".berthAssess").click();
		}
		initGrid((util.isNull(itemDetailArrival.berthId,1)!=0?itemDetailArrival.berthId:-1));
		config.load();
		$.ajax({
			type : "post",
				url : config.getDomain()+"/inboundoperation/list",
				dataType : "json",
				data:{"id":itemDetailArrival.berthprogramId,"result":3},
				success : function(data){
					util.ajaxResult(data,"获取靠泊方案信息",function(ndata){
						if(ndata&&ndata.length>0){
                             var mdata=ndata[0];  
			                $(obj).find("#safeInfo").val(util.isNull(mdata.safeInfo));
			                $(obj).find("#comment").val(util.isNull(mdata.comment));
			                $(obj).find("#richDraught").val(util.isNull(mdata.richDraught));
			                $(obj).find("#windPower").val(util.isNull(mdata.windPower));
			            	$(obj).find("#createUserId").val(util.isNull(mdata.createUserName)).attr("data",mdata.createUserId);
			            	$(obj).find("#reviewUserId").val(util.isNull(mdata.reviewUserName)).attr("data",mdata.reviewUserId);
							$(obj).find("#createTime").val(mdata.createTime);
							$(obj).find("#reviewTime").val(mdata.reviewTime);
							var item=mdata.status;
							if(item==1){
								$(obj).find(".createDiv,.commentDiv,.secDiv,.securityCodeDiv").show();
								$(obj).find(".reviewDiv,.firDiv").hide();
							}else if(item==3){
								$(obj).find(".secDiv,.securityCodeDiv").hide();
								$(obj).find(".commentDiv,.reviewDiv,.createDiv,.firDiv").show();
							}else{
								$(obj).find(".createDiv,.commentDiv,.reviewDiv,.secDiv,.securityCodeDiv").hide();
								$(obj).find(".firDiv").show();
							}
						//验证码内容
						$(obj).find("#securityCodeContent").text("船名："+itemDetailArrival.shipRefName+"；到港日期："+util.getSubTime(itemDetailArrival.arrivalStartTime,1)+"；计划接卸"+
						getGoodsMsgStr(itemDetailArrival.goodsMsg)+"；泊位："+itemDetailArrival.berthName+"；富裕水深："+mdata.richDraught+"米。"+"；现场风力："+mdata.windPower+"级。");
						}
					},true);
				}});
	};
	function initGrid(id){
		var columns=[{title:"泊位",name:"name"
		},{title:"泊位长度（米）",render:function(item){
			return item.length;
		}},{title:"前沿水深（米）",render:function(item){
			return item.frontDepth;
		}},{title:"船舶最大载重（吨）",render:function(item){
			return  util.isNull(item.limitDisplacement,1);
		}},{title:"船舶最大排水量（吨）",render:function(item){
			return  util.isNull(item.limitTonnage,1);
		}},{title:"船舶最大长度（米）",render:function(item){
			return  item.limitLength;
		}},{title:"船舶最小长度（米）",render:function(item){
			return  util.isNull(item.minLength,1);
		}},{title:"船舶最大吃水（米）",render:function(item){
			return  util.isNull(item.limitDrought,1);
		}},{title:"泊位信息",name:"description",width:200}];
		var murl=config.getDomain() + "/berth/list"
		if(id!='-1'){
			murl+="?id="+id;
			columns=[{title:"泊位",render:function(item){
					$("#description").val(item.description);
					if(util.isNull($("#safeInfo").val())=="")
					$("#safeInfo").val(item.safeInfo);
					return item.name;
				}},{title:"泊位长度（米）",render:function(item){
					return item.length;
				}},{title:"前沿水深（米）",render:function(item){
					return item.frontDepth;
				}},{title:"船舶最大载重（吨）",render:function(item){
					return  util.isNull(item.limitDisplacement,1);
				}},{title:"船舶最大排水量（吨）",render:function(item){
					return  util.isNull(item.limitTonnage,1);
				}},{title:"船舶最大长度（米）",render:function(item){
					return  item.limitLength;
				}},{title:"船舶最小长度（米）",render:function(item){
					return  util.isNull(item.minLength,1);
				}},{title:"船舶最大吃水（米）",render:function(item){
					return  util.isNull(item.limitDrought,1);
				}}];
		$(".buttons").hide();}
		if($('[data-role="berthGrid"]').getGrid()!=null){
			$('[data-role="berthGrid"]').getGrid().destory();
		}
		$('[data-role="berthGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol :(id==-1),
			isShowPages : false,
			url : murl,
			callback:function(){
			    if(id==1){
			        $('[data-role="berthGrid"]').find("th[index='3'],td[index='3']").hide();
				}else if(id!=-1&&id!=1){
					 $('[data-role="berthGrid"]').find("th[index='4'],td[index='4']").hide();
				}
			}
		});
	}
	
/*******************************************************接卸方案initTransportProgram****************************************************/
	/**初始化接卸方案*/
	function initTransportProgram(obj,iItem){
		initFormIput();
		if(iItem==1){
			initTransportProgramMsg(obj);//初始化接卸方案信息
			initTransportProgramCtr(obj);//初始化接卸方案数据
		}else{
			$(obj).find("#berthName").text(itemDetailArrival.berthName);
			config.load();
			$.ajax({
				type:"post",
				url:config.getDomain() + "/inboundoperation/list",
				data:{"id":itemGoodsData.transportId,"transportIds" :itemGoodsData.transportId,"result":4},
				dataType : "json",
				success:function(data){
					util.ajaxResult(data,"获取接卸方案信息",function(ndata){
						var mdata=ndata[0];
						$(obj).find("#unloadingmsg").val(util.toDecimal3(mdata.comment,true));
						checkFreeNum();
						$(obj).find("#tankNames").text(mdata.tankNames);
						$(obj).find("#tubeNames").text(mdata.tubeNames);
						$(obj).find("#createUserName").text(util.isNull(mdata.createUserName));
						$(obj).find("#createTime").text(mdata.createTime.substring(0,10));
						$(obj).find("#reviewUserName").text(util.isNull(mdata.reviewUserName));
						$(obj).find("#reviewTime").text(mdata.reviewTime.substring(0,10)),
						$(obj).find("#reviewCraftUserName").text(util.isNull(mdata.reviewCraftUserName));
						$(obj).find("#reviewCraftTime").text(mdata.reviewCraftTime.substring(0,10));
						$(obj).find("#contentDiv").append(mdata.svg);
						},true);
				}
			});
		}
	};
	//初始化接卸方案信息
	function initTransportProgramMsg(obj){
		$(obj).find("#berthName").val(itemDetailArrival.berthName);
		$(obj).find(".createDiv,.secDiv,.securityCodeDiv,.firDiv,.reviewCraftDiv,.reviewDiv").hide();
		if(itemGoodsData.transportId&&itemGoodsData.transportId!=0){
			config.load();
			$.ajax({
				type : "post",
				url : config.getDomain() + "/inboundoperation/list",
				data : { "id":itemGoodsData.transportId,"transportIds":itemGoodsData.transportId,"result":4},
				dataType : "json",
				success:function(data){
					util.ajaxResult(data,"获取接卸方案信息",function(ndata){
						if(ndata&&ndata.length>0){
						var mdata=ndata[0];
						$(obj).find("#tankIds").val(mdata.tankNames).attr("data",mdata.tankIds);
						$(obj).find("#tubeIds").val(mdata.tubeNames).attr("data",mdata.tubeIds);
						$(obj).find("#unloadingmsg").val(util.toDecimal3(mdata.comment,true));//空容
						checkFreeNum();//显示空容是否符合信息
						//工艺流程
						if(mdata.flow&&mdata.flow.length>0){
							$(obj).find("#graphContainer").empty();
							graphCleanCache();
							flow(mdata.flow,true,true,obj,false,false,0,itemGoodsData.productId,itemGoodsData.productName);
							initCache(","+mdata.tankIds,","+mdata.tubeIds);
						}else{
							graphCleanCache();
							flow(null,true,true,obj,false,false,0,itemGoodsData.productId,itemGoodsData.productName);
						}
						$(obj).find("#createUserId").val(util.isNull(mdata.createUserName)).attr("data",mdata.createUserId);
						$(obj).find("#createTime").val(mdata.createTime);
						$(obj).find("#reviewCraftUserId").val(util.isNull(mdata.reviewCraftUserName)).attr("data",mdata.reviewCraftUserId);
						$(obj).find("#reviewCraftTime").val(mdata.reviewCraftTime);
						$(obj).find("#reviewUserId").val(util.isNull(mdata.reviewUserName)).attr("data",mdata.reviewUserId);
						$(obj).find("#reviewTime").val(mdata.reviewTime);
							//根据状态初始化信息
							var item=util.isNull(mdata.status,1);
							$(obj).find("#currentStatus").text(item);//保存当前状态
							if(item==1){
								$(obj).find(".createDiv,.secDiv,.securityCodeDiv").show();
								$(obj).find(".firDiv,.reviewCraftDiv,.reviewDiv").hide();
							}else if(item==3){
								$(obj).find(".createDiv,.firDiv").show();
								$(obj).find(".secDiv,.reviewCraftDiv,.reviewDiv,.securityCodeDiv").hide();
							}else if(item==4){
								$(obj).find(".createDiv,.secDiv,.reviewDiv,.securityCodeDiv").show();
								$(obj).find(".firDiv,.reviewCraftDiv,button[key='4'],button[key='2']").hide();
							}else if(item==5){
								$(obj).find(".createDiv,.secDiv,.reviewCraftDiv,.securityCodeDiv").show();
								$(obj).find(".firDiv,.reviewDiv,button[key='5'],button[key='2']").hide();
							}else{//保存
								$(obj).find(".createDiv,.reviewCraftDiv,.reviewDiv,.secDiv,.securityCodeDiv").hide();
								$(obj).find(".firDiv").show();
							}
						//验证码内容
						$(obj).find("#securityCodeContent").text("船名："+itemDetailArrival.shipRefName+"；到港日期："+util.getSubTime(itemDetailArrival.arrivalStartTime,1)+"；计划接卸"+
						getGoodsMsgStr(itemDetailArrival.goodsMsg,itemGoodsData.productId)+"；罐号："+mdata.tankNames+";管线号："+mdata.tubeNames+"。");
						}else{
							$(obj).find(".createDiv,.reviewCraftDiv,.reviewDiv,.secDiv,.securityCodeDiv").hide();
							$(obj).find(".firDiv").show();
						}
					},true);
				}
			});}
	};
	//初始化接卸方案数据
	function initTransportProgramCtr(obj){
		//初始化验证码审批人
		util.urlHandleTypeahead("/auth/user/getUserByPermission?permission="+$(obj).find("#reviewCodeUserId").attr("code"),$(obj).find("#reviewCodeUserId"));
		//空容量修改时
		$(obj).find("#unloadingmsg").change(function(){
			if(util.FloatSub($(obj).find("#unloadingmsg").val(),itemGoodsData.goodsNum)>=0){
				$(obj).find("#checkMsg").show();
			}else{
				$(obj).find("#checkMsg").hide();
			}
		});
		//重置
		$(obj).find("#reset").click(function(){
			$(obj).find("#graphContainer").empty();
			$(obj).find("#tankIds,#tubeIds").val("").attr("data","");
			$(obj).find("#unloadingmsg").val('0');
			graphCleanCache();
			flow(null,true,true,obj,false,false,0,itemGoodsData.productId,itemGoodsData.productName);
		});
		
		$(obj).find(".save").click(function(){
			$this=$(this);
			var status=$this.attr("key");//要操作的状态
			var securityCode=$this.attr("code");
			var currentStatus=util.isNull($(obj).find("#currentStatus").text(),1);//当前状态
			//TODO 根据要提交状态获取数据，根据要提交状态校验
			if(validateStatus(obj,status,currentStatus,securityCode)){
				doSubmit(obj,status,currentStatus,securityCode);
			}
		});
	};
	function validateStatus(obj,status,currentStatus,securityCode){
		var isPass=true;
		if(status!=0&&isTransport==1&&util.isNull(itemDetailArrival.statusKey,1)<5){
            	isPass=false;
            	$(obj).confirm({
					content:'靠泊方案还未审核,请先处理靠泊方案。',
					callBack:function(){
						$('#tab2').click();
					}
				});
            	return;
		}
		if(status==1){
			if(util.isNull($(obj).find("#tankIds").val())==""||util.isNull($(obj).find("#tubeIds").val())==""||!validateSVG()){
				isPass=false;
				$('body').message({ type:'warning',content:'工艺流程未完善！'});
				return;
			}
			var isOnlyTransport=($(".ul_tab3 li").length<4);//存在几个接卸方案，如果是一个校验，如果不是则不校验
			if(util.FloatSub($(obj).find("#unloadingmsg").val(),itemGoodsData.goodsNum)<0&&isOnlyTransport){
				isPass=false;
				$(obj).confirm({
					content:'罐空容不足,点击确认继续提交',
					callBack:function(){
						doSubmit(obj,status,currentStatus,securityCode);	
					}});
			}
		}
		return isPass;
	};
	function doSubmit(obj,status,currentStatus,securityCode){
		var msg="";
		var data={
				"id":itemGoodsData.transportId,
				"arrivalId":itemDetailArrival.id,
				"workId":itemGoodsData.workId,
				"type":0,	
				"status":status};
		if(status==0||status==1){
			msg=(status==0?'保存':'提交');
			data.comment=$(obj).find("#unloadingmsg").val();
			data.tankIds=$(obj).find("#tankIds").attr("data");
			data.tubeIds=$(obj).find("#tubeIds").attr("data");
			data.createUserId=itemDetailArrival.createUserId;
			data.createTime=util.formatLong(util.currentTime(1));
			data.flow=getImgXml();
			data.svg=getSVG();
		}else if(status==2||status==3){//直接通过或者不通过
			msg=(status==2?'审核':'驳回');
			if(currentStatus==4){
				data.reviewUserId=itemDetailArrival.createUserId;
				data.reviewTime=util.formatLong(util.currentTime(1));
			}else if(currentStatus==5){
				data.reviewCraftUserId=itemDetailArrival.createUserId;
				data.reviewCraftTime=util.formatLong(util.currentTime(1));
			}else{
				data.reviewUserId=itemDetailArrival.createUserId;
				data.reviewTime=util.formatLong(util.currentTime(1));
				data.reviewCraftUserId=itemDetailArrival.createUserId;
				data.reviewCraftTime=util.formatLong(util.currentTime(1));
			}
		}else if(status==4){//品质通过
			msg='品质审核';
			if(currentStatus==5) data.status=2;
			data.reviewUserId=itemDetailArrival.createUserId;
			data.reviewTime=util.formatLong(util.currentTime(1));
		}else if(status==5){//工艺通过
			msg='工艺审核';
			if(currentStatus==4) data.status=2;
			data.reviewCraftUserId=itemDetailArrival.createUserId;
			data.reviewCraftTime=util.formatLong(util.currentTime(1));
		}
		//验证码
  	   if(securityCode&&securityCode.length>1){
 			   data.securityCode=util.isNull($(obj).find("#securityCode").val());
 			   data.isSecurity=1;
 			   data.object=securityCode;
  	   }else{  data.isSecurity=0;}
  	   config.load();
		$.ajax({
			type : "post",
			url : config.getDomain() + "/inboundoperation/updatetransportprogram",
			data : data,
			dataType : "json",
			success:function(data){
				util.ajaxResult(data,msg,function(ndata){
				 refreshViewData(3,itemDetailArrival.id,itemGoodsData.productId);	
				});
			}
		});
	};
	function checkFreeNum(){
		if(itemGoodsData){
		if(util.FloatSub($("#unloadingmsg").val(),itemGoodsData.goodsNum)>0){
			$("#checkMsg").show();
		}else{
			$("#checkMsg").hide();
		}
		}
	}
/*******************************************************接卸准备initUnloadingReady****************************************************/
	/**初始化接卸准备*/
	function initUnloadingReady(obj,items){
		initUnloadingReadyMsg(obj,items);
		initUnloadingReadyCtr(obj,items);
		handleUnloadingReadyIsTransport(obj);
	};
	//初始化接卸准备信息
	function initUnloadingReadyMsg(obj,items){
		//一、初始化罐安排
		var columns=[{title:"罐号",name:"tankCode"},{title:"前期存储物料",name:"productName"},{title:"使用情况",name:"tankDescription"}];
		$(obj).find('[data-role="tankGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain() + "/inboundoperation/list?id="+itemGoodsData.transportId+"&result=11"
		});
		
		//二、初始化管安排
		 columns=[{title:"管名",name:"tubeName"},{title:"前期存储物料",name:"productName"},{title:"使用情况",name:"tubeDescription"}];
		$(obj).find('[data-role="tubeGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain() + "/inboundoperation/list?id="+itemGoodsData.transportId+"&result=12"
		});
		
		//三、初始化管线准备与检查
		 //初始化接卸方案工艺流程
		$.ajax({type:"post",
			url:config.getDomain() + "/inboundoperation/list",
			data:{"id":itemGoodsData.transportId,"transportIds" :itemGoodsData.transportId,"result":4},
			dataType:"json",
			success:function(data){
				util.ajaxResult(data,"获取工艺流程",function(ndata){
					if(ndata){
						var mdata=ndata[0];
						var svg=mdata.svg;
						$(obj).find("#contentDiv").append(svg);
						$(obj).find("#dockNotice").attr("data",mdata.noticeCodeA);
						$(obj).find("#dynamicNotice").attr("data",mdata.noticeCodeB);
					} },true);
			}
		});
		 columns=[{title:"日期",render:function(item){return util.getSubTime(item.checkTime,2);}
		    },{title:"岗位",name:"checkType",render:function(item,name,index){
			if(item.checkType==1){
				if(config.hasPermission('AUPLOADINGTUBECHECKBYDYNAMIC')){
					return "<a href='javascript:void(0);' id='dynamicTr' onClick='InboundOperation.dialogTubeCheck("+index+")'>消防动力班</a>";						
				}else{
					return "<label id='dynamicTr'>消防动力班</label>";
				}}else if(item.checkType==2){
					if(config.hasPermission('AUPLOADINGTUBECHECKBYDOCK')){
						return "<a href='javascript:void(0);' id='dockTr' onClick='InboundOperation.dialogTubeCheck("+index+")'>码头操作班</a>";
					}else{ return "<label id='dockTr'>码头操作班</label>";}
			}}},{title:"检查情况",name:"content"},{title:"检查人",name:"checkUserName"},{title:"备注",name:"description"}];
		$(obj).find('[data-role="tubeCheckGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain() + "/inboundoperation/list?id="+itemGoodsData.transportId+"&result=13",
			callback:function(){
				$(obj).find("#dynamicTr").closest("tr").hide().siblings().hide();
				if(config.hasPermission('AINBOUNDTUBECHECKSHOWDOCK')){//校验是否有操作班权限
					$(obj).find("#dockTr").closest("tr").show();
				}
				if(config.hasPermission('AINBOUNDTUBECHECKSHOWDYNAMIC')){//检验是否有动力班权限
					$(obj).find("#dynamicTr").closest("tr").show();
				}
				if(isTransport&&isTransport==2){
					$(obj).find("#dockTr").closest("tr").remove();	
				}
			}});
		
		//四、初始化岗位检查表
		 columns=[{title:"日期",render:function(item){return util.getSubTime(item.createTime,2);}},{
			title:"岗位",name:"post",render:function(item,name,index){
				if(util.isNull(item.job,1)==1){
					if(config.hasPermission('AALLCHECKUPDATEBYDOCK')){
						return "<a href='javascript:void(0);' id='job"+item.job+"' onClick='InboundOperation.dialogAllCheck("+index+","+index+")'>"+item.post+"</a>"
					}else{
						return "<label id='job"+item.job+"'>码头</label>";
					}
				}else if(util.isNull(item.job,1)==2){
					if(config.hasPermission('AALLCHECKUPDATEBYDYNAMIC')){
						return "<a href='javascript:void(0);' id='job"+item.job+"' onClick='InboundOperation.dialogAllCheck("+index+","+index+")'>"+item.post+"</a>"
					}else{
						return "<label id='job"+item.job+"'>动力班</label>";
					}
				}else if(util.isNull(item.job,1)==3){
					if(config.hasPermission('AALLCHECKUPDATEBYMEASURE')){
						return "<a href='javascript:void(0);' id='job"+item.job+"' onClick='InboundOperation.dialogAllCheck("+index+","+index+")'>"+item.post+"</a>"
					}else{
						return "<label id='job"+item.job+"'>计量班</label>";
					}
				}else if(util.isNull(item.job,1)==4){
					if(config.hasPermission('AALLCHECKUPDATEBYDISPATCH')){
						return "<a href='javascript:void(0);' id='job"+item.job+"' onClick='InboundOperation.dialogAllCheck("+index+","+index+")'>"+item.post+"</a>"
					}else{
						return "<label id='job"+item.job+"'>调度及中控室</label>";
					}
				}}},{title:"检查内容",render:function(item){return "<label>"+item.checkContent+"</label>";}},{
			title:"检查结果",name:"result"},{title:"处理情况",name:"solve"},{title:"确认人",name:"createUserName"}];
		$(obj).find('[data-role="jobCheckGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain() + "/inboundoperation/list?id="+itemGoodsData.transportId+"&result=14",
			callback:function(){
				$(obj).find("#job1").closest("tr").hide().siblings().hide();
				if(config.hasPermission('AINBOUNDTUBECHECKSHOWDOCK')){//码头操作班
					$(obj).find("#job1").closest("tr").show();
				}
				if(config.hasPermission('AINBOUNDJOBCHECKSHOWDYNAMIC')){//动力班
					$(obj).find("#job2").closest("tr").show();
				}
				if(config.hasPermission('AINBOUNDJOBCHECKSHOWMEASURE')){//计量室
					$(obj).find("#job3").closest("tr").show();
				}
				 if(config.hasPermission('AINBOUNDJOBCHECKSHOWDISPATH')){//调度
					$(obj).find("#job4").closest("tr").show();
				}
				 if(isTransport&&isTransport==2){
						$(obj).find("#job1").closest("tr").remove();	
					}
			}
		});
		
		//五、船舶接卸过程
		//1.调度日志基本细信息
		if(isTransport&&isTransport==2){
		$(obj).find("#shipRefName").text("转输输入");
		}else{
			$(obj).find("#shipRefName").text(itemDetailArrival.shipName+'-'+itemDetailArrival.shipRefName);//船名	
		}
		$(obj).find("#berthName").text(itemDetailArrival.berthName);//泊位号
		$.ajax({
			type : "post",
			url : config.getDomain() + "/inboundoperation/list",
			data : {"id" :$("#tab4_0").attr('data'),"result":15},
			dataType : "json",
			success:function(data){
				var mdata=data.data[0];
				if(mdata!==undefined&&mdata!=null){
					//实际到港，离港，开泵，停泵
					$(obj).find("#arrivalTime").text(util.getSubTime(mdata.arrivalTime,2));
					$(obj).find("#leaveTime").text(util.getSubTime(mdata.leaveTime,2));
					$(obj).find("#openPump").text(util.getSubTime(mdata.openPump,2));
					$(obj).find("#stopPump").text(util.getSubTime(mdata.stopPump,2));
					//累计在港，累计工作
					$(obj).find("#stayTime").text(mdata.stayTime==0?"":mdata.stayTime);
					$(obj).find("#workTime").text(mdata.workTime==0?"":mdata.workTime);
					var ndata={"startTime" :util.formatLong(mdata.arrivalTime.substring(0,10)),
							"endTime" :util.formatLong(mdata.arrivalTime.substring(0,10))}
					if(mdata.leaveTime!=null&&mdata.leaveTime!=undefined&&util.formatLong(mdata.leaveTime.substring(0,10))!=-1){
						ndata.endTime=util.formatLong(mdata.leaveTime.substring(0,10));
					}
					//值班记录
					if(ndata.startTime&&ndata.startTime!=-1&&ndata.endTime&&ndata.endTime!=-1){
					$.ajax({
						type : "post",
						url : config.getDomain() + "/inboundserial/getDispatchConnect",
						data :ndata,
						dataType : "json",
						success:function(data){
							if(data.code=='0000'){
							var dockUser="",powerUser="",dispatchUser="";
							if(data.data.length>0){
								for(var item=0;item<data.data.length;item++){
									if(data.data[item].type==11||data.data[item].type==12)
										dispatchUser+=data.data[item].userName+",";
									if(data.data[item].type==41||data.data[item].type==42)
										dockUser+=data.data[item].userName+",";
									if(data.data[item].type==51||data.data[item].type==52)
										powerUser+=data.data[item].userName+",";
								}
								$(obj).find("#dockBUserId").text(util.isNull(dockUser.substring(0,dockUser.length-1)));
								$(obj).find("#dynamicBUserId").text(util.isNull(powerUser.substring(0,powerUser.length-1)));
								$(obj).find("#dispatchBUserId").text(util.isNull(dispatchUser.substring(0,dispatchUser.length-1)));
							}
							}
						}});
					}
					//评价，评价时间，评价人
					$(obj).find("#evaluate").text(util.isNull(mdata.evaluate));
					$(obj).find("#evaluateTime").text(util.getSubTime(mdata.evaluateTime,2));
					$(obj).find("#evaluateUserId").text(util.isNull(mdata.evaluateUserName));
					$(obj).find("#evaluateUserId").attr("data",mdata.evaluateUserId);
					handle();
				}
			}
		});
		
		//2.开泵前的验证
		$.ajax({
			type:"post",
			url:config.getDomain()+"/inboundoperation/list",
			data:{'workId':$("#tab4_0").attr('data'),'result':21},
			dataType:'json',
			success:function(data){
                 if(data.code=='0000'){
                	 var html='';
                	 if(data.data!=null&&data.data.length!=0){
                	 for(var item=0;item<data.data.length;item++){
                		 if(data.data[item].notification!=undefined){
                		 html+='<div class="form-group">'
 							+'<div class="col-md-4">'
							+'<label class="control-label col-md-4">通知人:</label>'
							+'<div class="col-md-8">'
								+'<label class="control-label">'+util.isNull(data.data[item].notificationUserName)+'</label>'
							+'</div>'
						+'</div>'
						+'<div class="col-md-4">'
							+'<label class="control-label col-md-5">通知时间:</label>'
							+'<div class="col-md-7">'
								+'<label class="control-label">'+util.getSubTime(data.data[item].notificationTime,2)+'</label>'
							+'</div>'
						+'</div>'
						+'<div class="col-md-4">'
							+'<label class="control-label col-md-4">通知内容:</label>'
							+'<div class="col-md-8">'
								+'<label class="control-label">'+data.data[item].notification+'</label>'
							+'</div>'
						+'</div></div>';
                	 }}
                		 }else{
                		 html+='<div class="form-group ">'
  							+'<div class="col-md-4">'
 							+'<label class="control-label col-md-4">通知人:</label>'
 							+'<div class="col-md-8">'
 								+'<label class="control-label" id="notificationUserId" data-required="1"></label>'
 							+'</div>'
 						+'</div>'
 						+'<div class="col-md-4">'
 							+'<label class="control-label col-md-5">通知时间:</label>'
 							+'<div class="col-md-7">'
 								+'<label class="control-label" id="notificationTime" data-required="1"></label>'
 							+'</div>'
 						+'</div>'
 						+'<div class="col-md-4">'
 							+'<label class="control-label col-md-4">通知内容:</label>'
 							+'<div class="col-md-8">'
 								+'<label class="control-label" id="notification" data-required="1"></label>'
 							+'</div>'
 						+'</div></div>';
                	 }
                	 $(obj).find(".workNotification").empty();
                	 $(obj).find(".workNotification").append(html);
                 }				
			}
		});
		
		//3.开泵前确认
		$.ajax({
			type:'post',
			url:config.getDomain()+"/inboundoperation/list",
			data:{"id":itemGoodsData.workId,"result":15},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取数据',function(ndata){
					var mdata=ndata[0];
					//联检时间
					
					if(items==1&&mdata.checkTime!=""){
						util.initTimeVal($(obj).find(".checkTime"),mdata.checkTime);
						$(obj).find(".timepicker-24:focus").blur();//初始化使获得焦点的失去焦点
						}else{
					$(obj).find("#checkTime").text(mdata.checkTime);
					}
					//开泵前确认--动力班
					$(obj).find("#dynamicUserId").val(util.isNull(mdata.dynamicUserName));
					$(obj).find("#dynamicUserId").attr("data",mdata.dynamicUserId);
					$(obj).find("#dynamicContent").val(util.isNull(mdata.dynamicContent));
					if(mdata.dynamicContent!=null){
						$(obj).find("#dynamicContent").attr("readonly","readonly");
						$(obj).find("#dynamicBtn").hide();
					}
					//开泵前确认--码头
					$(obj).find("#dockUserId").val(util.isNull(mdata.dockUserName));
					$(obj).find("#dockUserId").attr("data",mdata.dockUserUserId);
					$(obj).find("#dockContent").val(util.isNull(mdata.dockContent));
					if(mdata.dockContent!=null){
						$(obj).find("#dockContent").attr("readonly","readonly");
						$(obj).find("#dockBtn").hide();
					}
					//开泵前确认--船方（调度）
					$(obj).find("#shipClientId").val(mdata.shipClientName);
					$(obj).find("#shipClientId").attr("data",mdata.shipClientId);
					$(obj).find("#shipClientContent").val(util.isNull(mdata.shipClientContent));
					if(mdata.shipClientContent!=null){
						$(obj).find("#shipClientContent").attr("readonly","readonly");
						$(obj).find("#shipClientBtn").hide();
					}
					//码头动力阀测试--调度
					$(obj).find("#dockCheckClientId").val(mdata.createRUserName);
					$(obj).find("#dockCheckClientId").attr("data",mdata.createRUserId);
						$(obj).find("#dockCheck").val(util.isNull(mdata.dockCheck));
					if(mdata.dockCheck!=null){
						$(obj).find("#dockCheck").attr("readonly","readonly");
						$(obj).find("#dockCheckBtn").hide();
					}
					//异常处理记录
					$(obj).find("#unusualLog").val(util.isNull(mdata.unusualLog));	
				},true);
			}
		});
		
		//4.入库完成记录
		 columns=[{title:"储罐",name:"tankCode"},{title:"货品",name:"productName"},{
			title:"机房收货量(吨)",name:"realAmount"},{title:"手检收货量(吨)",name:"measureAmount"
		},{title:"差异",render:function(item){
				if(item.message!=null&&item.message!="")
				 $("#saveAllCheck").attr("key",2);
				if(item.measureAmount==null||item.realAmount==null||item.realAmount==0){
				return "";	
				}else{
				var differrate=(Math.abs(item.measureAmount-item.realAmount)*1000/item.realAmount).toFixed(0);
				if(!$.isNumeric(differrate)){
					return "";
				}else{
					return differrate+"‰";
				}}}}];
		$(obj).find('[data-role="totalstoreGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain() + "/tanklog/getTankLogStore?arrivalId="+itemDetailArrival.id+"&productId="+itemGoodsData.productId,
			callback:function(){
				var iData=null;
				if($(obj).find('[data-role="totalstoreGrid"]').getGrid()!=undefined){
				 iData=$(obj).find('[data-role="totalstoreGrid"]').getGrid().getAllItems();
				}
				$(obj).find('[data-role="totalstoreGrid"]').find(".totalTankDiv").remove();
				$.ajax({
					type:'post',
					url:config.getDomain()+"/inboundoperation/gettotalgoodstank",
					data:{arrivalId:itemDetailArrival.id,productId:itemGoodsData.productId},
					dataType:'json',
					success:function(data){
					util.ajaxResult(data,'获取总储罐',function(ndata){
						if(ndata&&ndata[0].totalGoodsTank){
							var  totalHtml="<div class='form-group totalTankDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>";
							if(iData){
							var  mRealAmount=0,mMeasureAmount=0; 
							for(var i=0;i<iData.length;i++){
								if(iData[i].realAmount){
									mRealAmount=util.FloatAdd(mRealAmount,iData[i].realAmount);
								}
								if(iData[i].measureAmount){
									mMeasureAmount=util.FloatAdd(mMeasureAmount,iData[i].measureAmount);
								}
							}
							if(mMeasureAmount&&mMeasureAmount!=0){
								totalHtml+="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>手检收货量: </label>" +
								"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+mMeasureAmount+" 吨</label>"
							}
							if(mRealAmount&&mRealAmount!=0){
							totalHtml+="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>机房收货量: </label>" +
							"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+mRealAmount+" 吨</label>"
							}
							if(ndata[0].totalGoodsTank&&ndata[0].totalGoodsTank!=0){
								totalHtml+="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>储罐收货量: </label>" +
								"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(ndata[0].totalGoodsTank,true)+" 吨</label>"
								}
							if(ndata[0].totalGoodsTank&&ndata[0].totalGoodsTank!=0&&mRealAmount&&mRealAmount!=0){
								totalHtml+="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>收货量总差异: </label>" +
								"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+(Math.abs(mRealAmount-ndata[0].totalGoodsTank)*1000/mRealAmount).toFixed(2)+"‰</label>"
							}
							totalHtml+="</div>"
							if((mRealAmount&&mRealAmount!=0)||(mMeasureAmount&&mMeasureAmount!=0)){
								$(obj).find('[data-role="totalstoreGrid"]').find(".grid-body").parent().append(totalHtml);
							}		
							}
						}
					},true);	
					}
				});
			}
		});
	};
	
	//初始化接卸准备控件
	function initUnloadingReadyCtr(obj,items){
		initFormIput();
		//开泵前确认
		//初始化码头
		$.ajax({ url:config.getDomain()+"/auth/user/get?pagesize=0&category=JOB",
			dataType:'json',
			success : function(data) {
				var array1=new Array();
				for(var i=0;i<data.data.length;i++){
					if(data.data[i].sn=="MT"){
						array1.push({id:data.data[i].id,name:data.data[i].name});
					}
				}
				util.handleTypeahead(array1,$(obj).find("#dockUserId"),null,null,true);
			}
		});
		util.handleTypeahead([{"key":"正常"},{"key":"不正常"}],$(obj).find("#dockContent"),"key","key",false);
		util.handleTypeahead([{"key":"正常"},{"key":"不正常"}],$(obj).find("#dynamicContent"),"key","key",false);
		util.handleTypeahead([{"key":"正常"},{"key":"不正常"}],$(obj).find("#shipClientContent"),"key","key",false);
		util.handleTypeahead([{"key":"合格"},{"key":"不合格"}],$(obj).find("#evaluate"),"key","key",false);
		util.handleTypeahead([{"key":"正常"},{"key":"不正常"}],$(obj).find("#dockCheck"),"key","key",false);
		$(obj).find("#dynamicBtn,#dockBtn,#shipClientBtn,#dockCheckBtn").click(function(){
			$this=$(this);
			var data;
			if(config.validateForm($this.closest('.form-group'))){
			if($this.attr("key")==1){
			 $(obj).find("#dynamicUserId").val(itemDetailArrival.createUserName).attr("data",itemDetailArrival.createUserId);
				data={
						"id":itemGoodsData.workId,
						"dynamicUserId":$(obj).find("#dynamicUserId").attr("data"),
						"dynamicContent":$(obj).find("#dynamicContent").val()
					}
			}else if($this.attr("key")==2){
			 $(obj).find("#dockUserId").val(itemDetailArrival.createUserName).attr("data",itemDetailArrival.createUserId);
				data={
						"id":itemGoodsData.workId,
						"dockUserId":$(obj).find("#dockUserId").attr("data"),
						"dockContent":$(obj).find("#dockContent").val()
					}
			}else if($this.attr("key")==3){
			 $(obj).find("#shipClientId").val(itemDetailArrival.createUserName).attr("data",itemDetailArrival.createUserId);
				data={
						"id":itemGoodsData.workId,
						"shipClientId":$(obj).find("#shipClientId").attr("data"),
						"shipClientContent":$(obj).find("#shipClientContent").val()
					}
			}else if($this.attr("key")==4){
			 $(obj).find("#dockCheckClientId").val(itemDetailArrival.createUserName).attr("data",itemDetailArrival.createUserId);
				data={
						"id":itemGoodsData.workId,
						"createRUserId":$(obj).find("#dockCheckClientId").attr("data"),
						"dockCheck":$(obj).find("#dockCheck").val()
					}
			}
			config.load();
			$.ajax({
				type : "post",
				url : config.getDomain() + "/inboundoperation/updatework",
				data:data,
				dataType:"json",
				success:function(data){
					util.ajaxResult(data,'提交',function(ndata){ $this.hide(); });
				}});
			}
		});
		$(obj).find("#saveCheckTime,#saveLog").click(function(){
			var key=$(this).attr("key");
			var data={
					"id":itemGoodsData.workId,
					"stayTime":$(obj).find("#stayTime").text(),
					"workTime":$(obj).find("#workTime").text()
			}
			if(key&&key==1){
			if(util.formatLong(util.getTimeVal($(obj).find(".checkTime")))!=-1){
				data.checkTime=util.formatLong(util.getTimeVal($(obj).find(".checkTime")));
			}else{
				$('body').message({type : 'warning',content : '请填写联检时间'});
				return
			}
			}else{
				data.unusualLog=util.isNull($(obj).find("#unusualLog").val());
			}
			config.load();
			$.ajax({
				type : "post",
				url : config.getDomain() + "/inboundoperation/updatework",
				data:data,
				dataType:"json",
				success:function(data){
				util.ajaxResult(data,'保存');
				}
			});
		});
		//提交接卸准备
		$(obj).find("#submitBtn").click(function(){
			var isOk=true;
			$(obj).find(".unloadprogram label").each(function(){
				if ($(this).attr("data-required")==1) {
					if ($(this).text()==null||$(this).text()==""){
						isOk = false;
						$(this).parent().prev("label").css('color','red');
					}
				}
				if ($(this).attr("data-required")==0) {
					if ($(this).text()==null||$(this).text()==""){
						$(this).parent().prev("label").css('color','red');
					}
				}
			});
			$(obj).find(".unloadprogram input").each(function(){
				if ($(this).attr("data-required") == 1) {
					if ($(this).val()==null||$(this).val()==""){
						isOk = false;
						$(this).parent().prev("label").css('color','red');
					}
				}
			});
			if($(obj).find('div[data-role="noData"]').length>0){
				isOk = false;
				$(obj).find('div[data-role="noData"]').text('请前往关联储罐台账');
				$(obj).find('div[data-role="noData"]').css('color','red');
			};
//			$(obj).find('div[data-role="totalstoreGrid"]').find(".grid-table-body").find("td[index='2']").each(function(){
//				if(util.isNull($(this).text(),1)==0){
//					isOk = false;
//					$(this).css('background','red');
//				}
//			});
			
			
			$(obj).find("[data-role='tubeCheckGrid']").find(".grid-table-body").find("td").each(function(){
				if($(this).attr('index')!=null&&$(this).attr('index')!=4){
				if(util.isNull($(this).text())==null||util.isNull($(this).text())==''){
					isOk = false;
					$(this).css('background','red');
				}}
			});
		$(obj).find("[data-role='jobCheckGrid']").find(".grid-table-body").find("td").each(function(){
			if($(this).attr('index')!=null&&$(this).attr('index')!=4){
			if(util.isNull($(this).text())==null||util.isNull($(this).text())==''){
				isOk = false;
				$(this).css('background','red');
			}}
		});
			if(!isOk){
				$('body').message({
					type:'warning',
					content:'信息未填写完整'
				});
				return isOk;
			}
			if(isOk&&config.validateForm($(obj).find(".unloadprogram"))) {
			//更新状态
			config.load();
			$.ajax({
				type : "post",
				url : config.getDomain() + "/inboundoperation/updatework",
				data : {'id':itemGoodsData.workId,'status':8,
					"stayTime":$(obj).find("#stayTime").text(),
					"workTime":$(obj).find("#workTime").text() },
				dataType : "json",
				success:function(data){
					util.ajaxResult(data,'提交',function(ndata){
						refreshViewData(4,itemDetailArrival.id,itemGoodsData.productId);
					});
				}}); 
			}
		});
	};
	function handleUnloadingReadyIsTransport(obj){
		//是否是转输
		if(isTransport&&isTransport==2){
			$("#dockNotice").hide();
			$("#dynamicNotice").show();
			$(obj).find(".notTransport").hide();
			$(obj).find("#berthName,#dockBUserId,#dockCheck,#dockContent").attr('data-required',0);
			$(obj).find("#tp1").text("四、转输前各岗位的最后检查确认工作");
			$(obj).find("#tp2").text("五、转输输入过程");
			$(obj).find("#tp3").text("转输类型:");
			$(obj).find("#tp4").text("本次转输输入提供服务评价:");
			$(obj).find("#tp5").text("六、转输输入过程中异常情况及处置记录");
		}else{
			$("#dockNotice,#dynamicNotice").show();
		}
	}
/*******************************************************打循环方案initBackFlowPlan****************************************************/
	/**初始化打循环方案*/
	function initBackFlowPlan(obj,iItem){
			initBackFlowPlanCtr(obj);
			initBackFlowPlanMsg(obj);
	};
	
	function initBackFlowPlanCtr(obj){
		initFormIput();
		util.urlHandleTypeahead("/auth/user/getUserByPermission?permission="+$(obj).find("#reviewCodeUserId").attr("code"),$(obj).find("#reviewCodeUserId"));
		$(obj).find("#flowmessage").change(function(){
			if(util.FloatSub($(obj).find("#flowmessage").val(),$(obj).find("#tankCount").val())>=0){
				$(obj).find("#checkMsg").show();
			}else{
				$(obj).find("#checkMsg").hide();
			}
		});
		
		$(obj).find("#reset").click(function(){
			$(obj).find("#toolbarContainer,#graphContainer").empty();
			$(obj).find("#flowmessage,#intankIds,#outtankIds,#tubeIds,#pupmIds").val("").removeAttr('data');
			$(obj).find("#unloadingmsg").val('0');
			graphCleanCache();
			flow(null,true,true,obj,false,true,1,itemGoodsData.productId,itemGoodsData.productName);
		});
		
		$(obj).find(".save,.reback").click(function(){
			$this=$(this);
			var status=$this.attr("key");
			var securityCode=$this.attr("code");
			var currentStatus=$(obj).find("#currentStatus").text();
	        if(validateBackFlowStatus(obj,status,currentStatus,securityCode)){
	        	doBackFlowSubmit(obj,status,currentStatus,securityCode);
	        }
		});
		$(obj).find("#savePumpTime").click(function(){
			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+'/inboundoperation/updatetransportprogram',
				data:{
					"id":itemGoodsData.backflowId,
					"arrivalId":itemDetailArrival.id,
					"type":1,
					"openPumpTime":util.formatLong(util.getTimeVal($(obj).find(".openPumpTime"))),
					"stopPumpTime":util.formatLong(util.getTimeVal($(obj).find(".stopPumpTime")))
				},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'保存',function(){
						refreshViewData(5,itemDetailArrival.id,itemGoodsData.productId);						
					});
				}
			});
			
		});
	};
	function validateBackFlowStatus(obj,status,currentStatus,securityCode) {
		var isPass=true;
		if(status==0||status==1){
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
	
	function doBackFlowSubmit(obj,status,currentStatus,securityCode) {
		var msg="";
		var data={
				"id":itemGoodsData.backflowId,
				"arrivalId":itemDetailArrival.id,
				"type":1,
		        "status":status
			};
		if(status==-1){
			msg="回退";
		}else if(status==0||status==1){
			msg=(status==0?'保存':'提交');
			data.flow=getImgXml();
			data.svg=getSVG();
			data.createUserId=itemDetailArrival.createUserId;
			data.createTime=util.formatLong(util.currentTime(1));
		}else if(status==2||status==3){//直接通过或者不通过
			msg=(status==2?'审核':'驳回');
			if(currentStatus==4){
				data.reviewUserId=itemDetailArrival.createUserId;
				data.reviewTime=util.formatLong(util.currentTime(1));
			}else if(currentStatus==5){
				data.reviewCraftUserId=itemDetailArrival.createUserId;
				data.reviewCraftTime=util.formatLong(util.currentTime(1));
			}else{
				data.reviewUserId=itemDetailArrival.createUserId;
				data.reviewTime=util.formatLong(util.currentTime(1));
				data.reviewCraftUserId=itemDetailArrival.createUserId;
				data.reviewCraftTime=util.formatLong(util.currentTime(1));
			}
		}else if(status==4){//品质通过
			msg='品质审核';
			if(currentStatus==5) data.status=2;
			data.reviewUserId=itemDetailArrival.createUserId;
			data.reviewTime=util.formatLong(util.currentTime(1));
		}else if(status==5){//工艺通过
			msg='工艺审核';
			if(currentStatus==4) data.status=2;
			data.reviewCraftUserId=itemDetailArrival.createUserId;
			data.reviewCraftTime=util.formatLong(util.currentTime(1));
		}
		//验证码
  	   if(securityCode&&securityCode.length>1){
 			   data.securityCode=util.isNull($(obj).find("#securityCode").val());
 			   data.isSecurity=1;
 			   data.object=securityCode;
  	   }else{  data.isSecurity=0;}
  	 config.load();
 	$.ajax({
		type : "post",
		url : config.getDomain() + "/inboundoperation/updatetransportprogram",
		data : data,
		dataType : "json",
		success:function(data){
			util.ajaxResult(data,msg,function(){
				if(status==0||status==1){
					var infoId=util.isNull($(obj).find("#infoId").val(),1);
					var infoUrl=config.getDomain()+ "/inboundoperation/"+(infoId==0?'addtransportinfo':'updatetransportinfo');
					$.ajax({
						type:"post",
						url:infoUrl,
						data:{
							'id':infoId,
							'transportId':itemGoodsData.backflowId,
							'message':$(obj).find("#flowmessage").val(),
							'inTankNames':$(obj).find("#intankIds").val(),
							'inTankIds':$(obj).find("#intankIds").attr("data"),
							'outTankNames':$(obj).find("#outtankIds").val(),
							'outTankIds':$(obj).find("#outtankIds").attr("data"),	
							'tubeNames':$(obj).find("#tubeIds").val(),
							'tubeIds':$(obj).find("#tubeIds").attr("data"),	
							'pupmNames':$(obj).find("#pupmIds").val(),
							'pupmIds':$(obj).find("#pupmIds").attr("data"),	
							'tankCount':$(obj).find("#tankCount").val()},
						dataType:'json',
						success:function(data){
							util.ajaxResult(data,'更新附属信息',function(){
								refreshViewData(5,itemDetailArrival.id,itemGoodsData.productId);	
							},true);
						}
					});
				}else{
					refreshViewData(5,itemDetailArrival.id,itemGoodsData.productId);
				}
			});
		}
 	});  
	};
	
	function initBackFlowPlanMsg(obj){
		$(obj).find("#berthName").val(itemDetailArrival.berthName);
		$(obj).find("#shipName").text("\""+itemDetailArrival.shipRefName+"\"");
		$(obj).find(".createDiv,.secDiv,.securityCodeDiv,.firDiv,.reviewCraftDiv,.reviewDiv,.nfDiv").hide();
		 if(util.isNull(itemGoodsData.backflowId,1)!=0){
			   config.load();
				$.ajax({
					type:"post",
					url:config.getDomain() + "/inboundoperation/list",
					data:{"id":itemGoodsData.backflowId,"transportIds" :itemGoodsData.backflowId,"result":4},
					dataType:"json",
					success:function(data){
						util.ajaxResult(data,"获取打循环方案信息",function(ndata){
							if(ndata&&ndata.length>0){
							var mdata=ndata[0];
							$(obj).find("#outtankIds").val(mdata.outTankNames).attr("data",mdata.outTankIds);
							$(obj).find("#intankIds").val(mdata.inTankNames).attr("data",mdata.inTankIds);
							$(obj).find("#pupmIds").val(mdata.pupmNames).attr("data",mdata.pupmIds);
							$(obj).find("#tubeIds").val(mdata.infotubeNames).attr("data",mdata.infotubeIds);
							$(obj).find("#tankCount").val(mdata.tankCount);
							$(obj).find("#dockBackFlowNotice").attr("data",mdata.noticeCodeA);
							$(obj).find("#storeBackFlowNotice").attr("data",mdata.noticeCodeB);
							$(obj).find("#flowmessage").val(util.isNull(mdata.message));
							util.initTimeVal($(obj).find(".openPumpTime"),mdata.openPumpTime);	
							util.initTimeVal($(obj).find(".stopPumpTime"),mdata.stopPumpTime);	
							checkBackFlowNum();
							$(obj).find("#infoId").val(mdata.infoId);
							
							if(util.isNull(mdata.flow)!=""){
								$(obj).find("#flow").val(mdata.flow);
								$(obj).find("#svg").val(mdata.svg);
								graphCleanCache();
								flow(mdata.flow,true,true,obj,false,true,1,itemGoodsData.productId,itemGoodsData.productName);
								initCache(null,","+mdata.infotubeIds,","+mdata.inTankIds,","+mdata.outTankIds,","+mdata.pupmIds,mdata.message);
							}else{
								graphCleanCache();
								flow(null,true,true,obj,false,true,1,itemGoodsData.productId,itemGoodsData.productName);
							}
							$(obj).find("#createUserId").val(util.isNull(mdata.createUserName)).attr("data",mdata.createUserId);
							$(obj).find("#createTime").val(mdata.createTime);
							$(obj).find("#reviewCraftUserId").val(util.isNull(mdata.reviewCraftUserName)).attr("data",mdata.reviewCraftUserId);
							$(obj).find("#reviewCraftTime").val(mdata.reviewCraftTime);
							$(obj).find("#reviewUserId").val(util.isNull(mdata.reviewUserName)).attr("data",mdata.reviewUserId);
							$(obj).find("#reviewTime").val(mdata.reviewTime);
								//根据状态初始化信息
								var item=util.isNull(mdata.status,1);
								$(obj).find("#currentStatus").text(item);//保存当前状态
								if(item==1){
									$(obj).find(".createDiv,.secDiv,.securityCodeDiv").show();
									$(obj).find(".firDiv,.reviewCraftDiv,.reviewDiv,.nfDiv,.pumpTimDiv").hide();
								}else if(item==2){
									$(obj).find(".createDiv,.reviewCraftDiv,.reviewDiv,.nfDiv,.pumpTimDiv").show();
									$(obj).find(".firDiv,.secDiv,.securityCodeDiv").hide();
								}else if(item==3){
									$(obj).find(".createDiv,.firDiv").show();
									$(obj).find(".secDiv,.reviewCraftDiv,.reviewDiv,.securityCodeDiv,.nfDiv,.pumpTimDiv").hide();
								}else if(item==4){
									$(obj).find(".createDiv,.secDiv,.reviewDiv,.securityCodeDiv").show();
									$(obj).find(".firDiv,.reviewCraftDiv,button[key='4'],button[key='2'],.nfDiv,.pumpTimDiv").hide();
								}else if(item==5){
									$(obj).find(".createDiv,.secDiv,.reviewCraftDiv,.securityCodeDiv").show();
									$(obj).find(".firDiv,.reviewDiv,button[key='5'],button[key='2'],.nfDiv,.pumpTimDiv").hide();
								}else{//保存
									$(obj).find(".createDiv,.reviewCraftDiv,.reviewDiv,.secDiv,.securityCodeDiv,.nfDiv,.pumpTimDiv").hide();
									$(obj).find(".firDiv").show();
								}
								
							//验证码内容
                            $(obj).find("#securityCodeContent").text("船名："+itemDetailArrival.shipRefName+"；到港日期："+util.getSubTime(itemDetailArrival.arrivalStartTime,1)+"；"+
							itemGoodsData.productName+"；倒出罐号："+mdata.outTankNames+"倒出"+mdata.tankCount+"吨；泊位："+itemDetailArrival.berthName+"；泵号："+
							mdata.pupmNames+"；管线号："+mdata.infotubeNames+"；倒入罐号："+mdata.inTankNames+"。目的："+"\""+itemDetailArrival.shipRefName+"\"卸货结束打循环。");
							}else{
								$(obj).find(".createDiv,.reviewCraftDiv,.reviewDiv,.secDiv,.securityCodeDiv").hide();
								$(obj).find(".firDiv").show();
							}
						},true);
					}
				});
				 }
	};
	
	function  checkBackFlowNum(){
		if(util.FloatSub($("#flowmessage").val(),$("#tankCount").val())>0){
			$("#checkMsg").show();
		}else{
			$("#checkMsg").hide();
		}
	}
/*******************************************************数量确认initAmountAffirm****************************************************/
	/**数量确认*/
	function initAmountAffirm(obj,iItem){
		$(obj).find("#inboundPrint").attr('data',itemDetailArrival.id);
		initFormIput();
		if(isTransport&&isTransport==2){
			$(obj).find("#tp1").text('转输日期:');
		}
		//数量确认状态
		var item=$(".tab4_0").attr("status");
		 if(iItem==1){
			 if(item==0){//保存状态
				 $(obj).find("#createUserId").attr("data",itemDetailArrival.createUserId);
				 $(obj).find("#createUserId").val(itemDetailArrival.createUserName);
				 $(obj).find("#createTime").val(util.currentTime(2));
				 $(obj).find("#reviewTime").val(util.currentTime(2));
				 $(obj).find(".createDiv,.reviewDiv,.secDiv,.securityCodeDiv").hide();
				 $(obj).find(" .firDiv").show();
			 }else if(item==1){//提交后状态
				 $(obj).find("#reviewUserId").attr("data",itemDetailArrival.createUserId);
				 $(obj).find("#reviewUserId").val(itemDetailArrival.createUserName);
				 $(obj).find("#reviewTime").val(util.currentTime(2));
				 $(obj).find("#createTime").val(util.currentTime(2));
				 $(obj).find(".createDiv,.secDiv,.securityCodeDiv").show();
				 $(obj).find(".reviewDiv,.firDiv").hide();
			 }else if(item==3){//驳回后状态
				 $(obj).find(".secDiv,.securityCodeDiv").hide();
				 $(obj).find(" .reviewDiv,.createDiv,.firDiv").show();
			 }else{
				 $('body').message({
					 type:'error',
					 content:'获取数据出错'
				 });
				 $(obj).find(".secDiv").hide();
				 $(obj).find(" .reviewDiv,.createDiv,.firDiv").show();
			 }
			//初始化人名数据
			 $.ajax({
				type:'get',
				url:config.getDomain()+"/inboundoperation/list?id="+$(".tab4_0").attr("data")+"&result="+15,
				dataType:'json',
				success:function(data){
					if(data.code=='0000'){
						 $(obj).find("#inboundTime").text(util.getSubTime(util.isNull(data.data[0].arrivalTime),2));
						if(data.data[0].createUserName!=null&&data.data[0].createUserName!=""){
						 $(obj).find("#createUserId").val(data.data[0].createUserName);	
						 $(obj).find("#createTime").val(data.data[0].createTime.substring(0,10));
						 }
						if(data.data[0].reviewUserName!=null&&data.data[0].reviewUserName!=""){
						 $(obj).find("#reviewUserId").val(data.data[0].reviewUserName);
						 $(obj).find("#reviewTime").val(data.data[0].reviewTime.substring(0,10));}
					}
				}
			 });
			 util.urlHandleTypeahead("/auth/user/getUserByPermission?permission="+$(obj).find("#reviewCodeUserId").attr("code"),$(obj).find("#reviewCodeUserId"));
			        	//获取cargoGoods信息
			        	$.ajax({
							 type:"get",
							 url:config.getDomain() + "/inboundoperation/list",
							 data:{'id':itemDetailArrival.id,'result':16,'productId':itemGoodsData.productId,'cargoGoodsStatus':'2'},
							 dataType:"json",
							 success:function(data){
								 util.ajaxResult(data,'初始化',function(mdata){
									 if(!clientData)
									 clientData=new Object();
									 
									 //初始化clientData
									 for(var i=0;i<mdata.length;i++){
				                         var cargoData=mdata[i].cargodata;//货批
				                         var goodsData=mdata[i].goodsdata;//货体
				                         var item="a"+cargoData.id;
				                         clientData[item]={
				                        		   'tankTotal':cargoData.goodsTank,//罐检
				         							'goodsShip':cargoData.goodsShip,//船检
				         							'lossRate':cargoData.lossRate,//损耗
				         							'orderType':cargoData.type,//包罐混罐
				         							'ctLossRate':cargoData.ctLossRate//合同损耗率
				                         };
				                         var tankmsg=new Array();
				                         if(goodsData.length>0){
				                         for(var j=0;j<goodsData.length;j++){
				                        	 var itemgoods=goodsData[j];
				                        	 tankmsg.push({
				                        		    'goodsId':goodsData[j].id,//货体id
				                                    'tankId':goodsData[j].tankId,//储罐id
				                                    'tankCode':goodsData[j].tankCode,//储罐
				                                    'tankCodes':goodsData[j].tankCodes,//混罐
				                                    'tankNum':goodsData[j].goodsTank//储罐数量
				                        	 });
				                         }
				                         if(!clientData[item].tankMsg)
				         				  clientData[item].tankMsg=tankmsg;
				                         }
				                         }
									   initTable(obj);
								 },true);
							 }});
		  
			 //提交
			 $(obj).find("#save,#submit").click(function(){
				 $this=$(this);
				 var status=$this.attr("key");//状态
				 var isOK=true;
				 if(status==1){
					 $(obj).find("td[index='5']").each(function(){
						$this=$(this);
						if($this&&$this.length>0){
							if($this.text()==null||$this.text()<=0){
								$("body").message({
		                        	   type:'warning',
		                        	   content:'请填入罐检数量'
		                           });						 
								 isOK=false;
								 return;
							}
						}
					 });
				 }
				 //提交
				 if(isOK){
					 var istrue=(status==2?true:false);
					 var  nData={
								"cargolist":JSON.stringify(getCargoList()),//货批
								"goodslist":JSON.stringify(getGoodsList(istrue)),//货体
//								"id":$(".tab4_0").attr("data"),
								"arrivalId":itemDetailArrival.id,
								"productId":itemGoodsData.productId,
								"shipId":itemDetailArrival.shipId,
								"createUserId":$(obj).find("#createUserId").attr("data"),//制定人
								"createTime":util.formatLong(util.currentTime(2)),//制定时间
								"reviewUserId":$(obj).find("#reviewUserId").attr("data"),//审批人
								"reviewTime":util.formatLong(util.currentTime(2)),//审批时间
								"reviewStatus":status//状态
							};;
					 if(status==2){
						 istrue=true;
		             	   if($this.attr('code')&&$this.attr('code').length>1){
		             			   nData.securityCode=util.isNull($(obj).find("#securityCode").val());
		             			   nData.isSecurity=1;
		             			   nData.reviewUserId=$(obj).find("#reviewCodeUserId").attr("data");
		             			   nData.object=$this.attr('code');   
		             	   }else{
		             		   nData.isSecurity=0;
		             	   }
					 }
					 
 					//提交后台
					 config.load();
					 $.ajax({
						 type : "post",
							url : config.getDomain() + "/inboundoperation/updatecargogoodslist",
							data:nData,
							dataType:"json",
							success:function(data){
								config.unload();
								if(data.code=="0000"){	
									$('body').message({
									type : 'success',
									content : msgContent(status,1)
								});
							//初始化多次接卸，多次打循环
								refreshViewData(6,itemDetailArrival.id,itemGoodsData.productId);
							}else{
									$('body').message({
										type:'error',
										content:msgContent(status,2)
									});
								}
								}	
							 });
				}
			 });
			 //获取货批信息
			 function  getCargoList(){
				 var cargoMsg=getGoodsMsg(itemDetailArrival.goodsMsg,6,true,itemGoodsData.productId);
				 var cargoList=new Array();
				 for(var i=0;i<cargoMsg.length;i++){
					 if(clientData['a'+cargoMsg[i].cargoId]!=undefined&&clientData['a'+cargoMsg[i].cargoId]!=null){
					 var itemCargo={
							'id':cargoMsg[i].cargoId,
							'goodsTotal':util.FloatSub(clientData['a'+cargoMsg[i].cargoId].tankTotal,util.FloatDiv(util.FloatMul(clientData['a'+cargoMsg[i].cargoId].tankTotal,clientData['a'+cargoMsg[i].cargoId].ctLossRate),1000)),
							'goodsCurrent':util.FloatSub(clientData['a'+cargoMsg[i].cargoId].tankTotal,util.FloatDiv(util.FloatMul(clientData['a'+cargoMsg[i].cargoId].tankTotal,clientData['a'+cargoMsg[i].cargoId].ctLossRate),1000)),
							'goodsTank':util.toDecimal3(clientData['a'+cargoMsg[i].cargoId].tankTotal,true),
							'goodsShip':util.toDecimal3(clientData['a'+cargoMsg[i].cargoId].goodsShip,true),
							'lossRate':util.toDecimal3(clientData['a'+cargoMsg[i].cargoId].lossRate),
							'type':clientData['a'+cargoMsg[i].cargoId].orderType,
							'goodsInspect':0,
							'goodsInPass':0,
							'goodsOutPass':0
					       }
					 cargoList.push(itemCargo);
               		}};
               		return cargoList;
			 }
			 //获取货体信息
			 function  getGoodsList(isTrue){
				 var goodsList=new Array();
					var cargoMsg=getGoodsMsg(itemDetailArrival.goodsMsg,6,true,itemGoodsData.productId);//货批信息
					for(var i=0;i<cargoMsg.length;i++){
						if(clientData['a'+cargoMsg[i].cargoId]){
							var itemCargoData=clientData['a'+cargoMsg[i].cargoId];
						var itemtankMsg=itemCargoData.tankMsg;//单个货品的货体信息
						if(itemtankMsg){
						for(var j=0;j<itemtankMsg.length;j++){
					    var itemGoods={
					    	 'id':itemtankMsg[j].goodsId,
							 'code':cargoMsg[i].cargoCode+'  '+(j+1)+'/'+itemtankMsg.length,
							 'cargoId':cargoMsg[i].cargoId,
							 'clientId':cargoMsg[i].clientId,
							 'productId':cargoMsg[i].productId,
							 'contractId':cargoMsg[i].contractId,
							 'tankId':itemtankMsg[j].tankId,
							 'tankCode':itemtankMsg[j].tankCode,
							 'tankCodes':itemtankMsg[j].tankCodes,
							 'goodsTotal':util.FloatSub(itemtankMsg[j].tankNum,util.FloatDiv(util.FloatMul(itemtankMsg[j].tankNum,clientData['a'+cargoMsg[i].cargoId].ctLossRate),1000)),
							 'goodsTank':util.toDecimal3(itemtankMsg[j].tankNum,true),
							 'goodsInspect':0,
							 'goodsIn':0,
							 'goodsOut':0,
							 'goodsCurrent':util.FloatSub(itemtankMsg[j].tankNum,util.FloatDiv(util.FloatMul(itemtankMsg[j].tankNum,clientData['a'+cargoMsg[i].cargoId].ctLossRate),1000)),
							 'goodsInPass':0,
							 'goodsOutPass':0,
							 'lossRate':clientData['a'+cargoMsg[i].cargoId].lossRate,
							 'status':isTrue?0:2
					 };
					 goodsList.push(itemGoods);
//						}
						}
							}
						}
					}
					return goodsList;
			 };
			 
		 }else{
//			 $(obj).find("#inboundTime").text(itemDetailArrival.arrivalStartTime);
			//初始化人名数据
			 $.ajax({
				type:'get',
				url:config.getDomain()+"/inboundoperation/list?id="+itemGoodsData.workId+"&result="+15,
				dataType:'json',
				success:function(data){
					if(data.code=='0000'){
						 $(obj).find("#inboundTime").text(util.getSubTime(data.data[0].arrivalTime,2));
						if(data.data[0].createUserName!=null&&data.data[0].createUserName!=""){
						 $(obj).find("#createUserId").text(data.data[0].createUserName);	
						 $(obj).find("#createTime").text(data.data[0].createTime.substring(0,10));
						 }
						if(data.data[0].reviewUserName!=null&&data.data[0].reviewUserName!=""){
						 $(obj).find("#reviewUserId").text(data.data[0].reviewUserName);
						 $(obj).find("#reviewTime").text(data.data[0].reviewTime.substring(0,10));}
					}
				}
			 });
			 var columns=[{title:"批号",name:"code",render:function(item,name,index){
//				return "<a href='javascript:void(0);' onClick='InboundOperation.dialogAmountAffirm("+index+")'>"+item.cargodata.code+"</a>";
				 return item.cargodata.code;
			 }},{
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
						return util.toDecimal3(item.cargodata.goodsPlan,true);
					}
				},{
					title:"货品罐号",
					name:"tankName",
					render:function(item,name,index){
						var goodsData=item.goodsdata;
						var cargoData=item.cargodata;
						if(cargoData.type==1){
							if(goodsData.length>0){
								var tCodes="";
								for(var i=0;i<goodsData.length;i++){
	                                  if(goodsData[i]&&goodsData[i].tankCodes)								
								tCodes=util.isNull(goodsData[i].tankCodes);
								}
								return tCodes;
								}else {return "";}
						}else{
						if(goodsData.length>0){
						var tankHtml='<table class="table inmtable" style="margin-bottom: 0px;">';
						for(var i=0;i<goodsData.length;i++){
							tankHtml+='<tr><td style="border-bottom:1px solid #ddd;" >'+goodsData[i].tankCode+'</td></tr>';
						}
                           tankHtml+="</table>";						
						return tankHtml;
						 }
						}
						return "";
					}
				},{
					title:"储罐数量(吨)",
					name:"tankTotal",
					render:function(item,name,index){
						return util.toDecimal3(item.cargodata.goodsTank,3);
						
					}
				},{
					title:"扣损后数量(吨)",
					render:function(item,name,index){
						if(item.cargodata.ctLossRate!=null&&item.cargodata.ctLossRate!=""){
							return util.FloatSub(item.cargodata.goodsTank,util.FloatDiv(util.FloatMul(item.cargodata.goodsTank,item.cargodata.ctLossRate),1000));
						}else{
							return util.toDecimal3(item.cargodata.goodsTank,3);
						}
					}
				},{
					title:"合同损耗率",
					render:function(item,name,index){
						if(item.cargodata.ctLossRate!=null&&item.cargodata.ctLossRate!=undefined&&item.cargodata.ctLossRate!=''){
				        return item.cargodata.ctLossRate+'‰';		
				        }else{
				        	return "0‰";
				        }
					}
				},{title:"备注",name:"description"},{title:"操作",render:function(item){
					return '<a style="padding-left: 20px;"   class="hidden-print" data='+itemDetailArrival.id+' onclick="InboundPrint.print(this,'+item.cargodata.id+')">'+
					 '<i class="fa fa-print" title="打印"></i></a>';
				}}
				];
			 
			 $(obj).find('[data-role="clientAmountGrid"]').grid({
					identity:'id',
				    columns:columns,
					isShowIndexCol : false,
					isShowPages : false,
					searchCondition:{'productId':itemGoodsData.productId,'cargoGoodsStatus':'0'},
					callback:function(){
						//计算总计
						var  totalHtml="<div class='form-group' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>";
						var iData=$(obj).find('[data-role="clientAmountGrid"]').getGrid().getAllItems();
						var  mGoodsPlan=0,mTankTotal=0,mLeftAmount=0; 
						for(var i=0;i<iData.length;i++){
							if(iData[i].cargodata!=undefined){
							if(iData[i].cargodata.goodsPlan){
								mGoodsPlan=util.FloatAdd(mGoodsPlan,iData[i].cargodata.goodsPlan);
							}
							if(iData[i].cargodata.goodsTank){
								mTankTotal=util.FloatAdd(mTankTotal,iData[i].cargodata.goodsTank);
							}
							if(iData[i].cargodata.ctLossRate&&$.isNumeric(iData[i].cargodata.ctLossRate)){
								var iLeftAmout=util.FloatSub(iData[i].cargodata.goodsTank,util.FloatDiv(util.FloatMul(iData[i].cargodata.goodsTank,iData[i].cargodata.ctLossRate),1000));
								mLeftAmount=util.FloatAdd(mLeftAmount,iLeftAmout);
							}else{
								mLeftAmount=util.FloatAdd(mLeftAmount,iData[i].cargodata.goodsTank);
							}
							}
						}
						if(mGoodsPlan&&mGoodsPlan!=0){
						totalHtml+="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>计划数量: </label>" +
						"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(mGoodsPlan,true)+" 吨</label>"
						}
						if(mTankTotal&&mTankTotal!=0){
							totalHtml+="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>储罐数量: </label>" +
							"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(mTankTotal,true)+" 吨</label>"
						}
						if(mLeftAmount&&mLeftAmount!=0){
							totalHtml+="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>扣损后数量: </label>" +
							"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(mLeftAmount,true)+" 吨</label>"
						}
						totalHtml+="</div>"
						if((mGoodsPlan&&mGoodsPlan!=0)||(mTankTotal&&mTankTotal!=0)||(mLeftAmount&&mLeftAmount!=0)){
							$(obj).find('[data-role="clientAmountGrid"]').find(".grid-body").parent().append(totalHtml);
						}
					},
					url : config.getDomain() + "/inboundoperation/list?id="+itemDetailArrival.id+"&result="+16
				});
		 } 
	 }
	
	function  initTable(obj){	 
		 var columns=[{title:"批号",
			          name:"cargoCode",
			          render:function(item,name,index){
			        	  return "<a href='javascript:void(0);' onClick='InboundOperation.dialogAmountAffirm("+index+")'>"+item.cargoCode+"</a>";
			          }
			 },{
				title:"货品",
				name:"productName"
			},{
				title:"货主",
				name:"clientName"
			},{
				title:"计划数量(吨)",
				name:"goodsPlan",
				render:function(item,name,index){
					return util.toDecimal3(item.goodsPlan,true);
				}
			},{
				title:"货品罐号",
				name:"tankName",
				render:function(item,name,index){
					
					if(clientData['a'+item.cargoId]&&clientData['a'+item.cargoId].orderType==2){
					if(clientData!=null&&clientData['a'+item.cargoId]!=null){
						var tankMsg=clientData['a'+item.cargoId].tankMsg;
						var tankHtml='<table class="table inmtable" style="margin-bottom: 0px;">';
						if(tankMsg!=null&&tankMsg!=undefined){
						for(var i=0;i<tankMsg.length;i++){
							if(tankMsg[i].tankCode!=null)
							tankHtml+='<tr><td style="border-bottom:1px solid #ddd;" >'+tankMsg[i].tankCode+'</td></tr>';
						}
                          tankHtml+="</table>";						
						return tankHtml;
						}else{
							return "";
						}}else{
						return "";
					}}else{
						if(clientData!=null&&clientData['a'+item.cargoId]!=null){
							var tankMsg=clientData['a'+item.cargoId].tankMsg;
							if(tankMsg!=null&&tankMsg!=undefined){
								var tankCodes='';
							for(var i=0;i<tankMsg.length;i++){
								tankCodes+=tankMsg[i].tankCodes+",";
							}
							return tankCodes.substring(0,tankCodes.length-1);
							}else{
								return "";
							}
							}else{
								return "";
							}}
					}
			}
			,{
				title:"储罐数量(吨)",
				name:"tankTotal",
				render:function(item,name,index){
					if(clientData!=null&&clientData['a'+item.cargoId]!=null){
					return util.toDecimal3(clientData['a'+item.cargoId].tankTotal,true);
				}else{
				return "0.000";	
				}
				}
			}
			,{
				title:"扣损后数量(吨)",
				name:"LeftAmount",
				render:function(item,name,index){
					if(clientData['a'+item.cargoId]&&clientData['a'+item.cargoId].ctLossRate!=null&&clientData['a'+item.cargoId].ctLossRate!=""){
						var nLossRate=util.FloatMul(clientData['a'+item.cargoId].tankTotal,clientData['a'+item.cargoId].ctLossRate);
						var mLossRate=util.FloatDiv(nLossRate,1000);
						return util.FloatSub(clientData['a'+item.cargoId].tankTotal,mLossRate,3);
					}else{
						return util.toDecimal3(clientData['a'+item.cargoId].tankTotal,true);
					}
				}
			},{
				title:"合同损耗率",
				render:function(item,name,index){
					if(clientData['a'+item.cargoId]&&clientData['a'+item.cargoId].ctLossRate!=null&&clientData['a'+item.cargoId].ctLossRate!=""){
			        return clientData['a'+item.cargoId].ctLossRate+'‰';		
			        }else{
			        	return "0‰";
			        }
				}
			},{title:"备注",name:"description"}
			];
		 $(obj).find('[data-role="clientAmountGrid"]').grid({
				columns:columns,
				isShowIndexCol : false,
				isShowPages : false,
				isUserLocalData:true,
				localData:getGoodsMsg(itemDetailArrival.goodsMsg,6,true,itemGoodsData.productId)
			});
		//计算总计
			var  totalHtml="<div class='form-group' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>";
			var iData=getGoodsMsg(itemDetailArrival.goodsMsg,6,true,itemGoodsData.productId);
			var  mGoodsPlan=0,mTankTotal=0,mLeftAmount=0; 
			for(var i in iData){
				if(iData[i].goodsPlan){
					mGoodsPlan=util.FloatAdd(mGoodsPlan,iData[i].goodsPlan);
				}
				if(iData[i].cargoId&&clientData['a'+iData[i].cargoId]&&clientData['a'+iData[i].cargoId].tankTotal){
					mTankTotal=util.FloatAdd(mTankTotal,clientData['a'+iData[i].cargoId].tankTotal);
				}
				if(iData[i].cargoId&&clientData['a'+iData[i].cargoId]&&util.isNull(clientData['a'+iData[i].cargoId].ctLossRate,1)!=0){
					var nLossRate=util.FloatMul(clientData['a'+iData[i].cargoId].tankTotal,clientData['a'+iData[i].cargoId].ctLossRate);
					var mLossRate=util.FloatDiv(nLossRate,1000);
					var iLeftAmout=util.FloatSub(clientData['a'+iData[i].cargoId].tankTotal,mLossRate);
					
					mLeftAmount=util.FloatAdd(mLeftAmount,iLeftAmout);
				}else{
					mLeftAmount=util.FloatAdd(mLeftAmount,clientData['a'+iData[i].cargoId].tankTotal);
				}
			}
			if(mGoodsPlan&&mGoodsPlan!=0){
			totalHtml+="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>计划数量: </label>" +
			"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(mGoodsPlan,true)+" 吨</label>"
			}
			if(mTankTotal&&mTankTotal!=0){
				totalHtml+="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>储罐数量: </label>" +
				"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(mTankTotal,true)+" 吨</label>"
			}
			if(mLeftAmount&&mLeftAmount!=0){
				totalHtml+="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>扣损后数量: </label>" +
				"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(mLeftAmount,true)+" 吨</label>"
			}
			totalHtml+="</div>"
			if((mGoodsPlan&&mGoodsPlan!=0)||(mTankTotal&&mTankTotal!=0)||(mLeftAmount&&mLeftAmount!=0)){
				$(obj).find('[data-role="clientAmountGrid"]').find(".grid-body").parent().append(totalHtml);
				//验证码内容
				$(obj).find("#securityCodeContent").text("船名："+itemDetailArrival.shipRefName+"；到港日期："+util.getSubTime(itemDetailArrival.arrivalStartTime,1)+"内容如下:入库："+
						itemGoodsData.productName+";计划量："+util.toDecimal3(mGoodsPlan,true)+"吨，储罐量："+util.toDecimal3(mTankTotal,true)+
						"吨，扣损后量："+util.toDecimal3(mLeftAmount,true)+"吨。");
			}
			var isShow=true;
			//校验是否有货批号
			for(var i in iData){
				if(iData[i]&&iData[i].cargoId){
					if((iData[i].cargoCode==null||iData[i].cargoCode=='')&&isShow){
						 isShow=false;
						$(obj).confirm({
							content:'您的货品没有合同批次号，无法操作，请前往入港计划填写合同批次号。',
							callBack:function(){
								$(obj).find(".firDiv").hide();
//								window.location.href='#/arrivalGet?id='+itemDetailArrival.id;	
							}
					});
				}
			}
		}   
			checkUnloading(obj);
		};
		//校验该货品是否已经全部到数量审核阶段
	function checkUnloading(obj){
		$.ajax({
			type:'post',
			url:config.getDomain()+"/inboundoperation/list",
			data:{arrivalId:itemDetailArrival.id,productId:itemGoodsData.productId,unloadingType:1,result:7},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,"",function(ndata){
					var unloadingData=ndata[0].unloadingplan;
					var isShow=true;
					for(var i=0;i<unloadingData.length;i++){
						if(unloadingData[i].status!=8&&isShow){
							$(obj).find(".firDiv").hide();
							isShow=false;
							$(obj).confirm({
								content:'该货品的其他接卸还未完成请等待。。',
								callBack:function(){
								}
							});
							return;
						}
					}
					
				},true);
			}
		});
	}
/*****************************************************cleanToStatus***************************************************************************/	
	/**回退功能*/
    function cleanToStatus(status,isMsg){
    	var data={id:itemDetailArrival.id,statuskey:status,productId:itemGoodsData.productId};
    	if(status>=5){
    		data.workId=$(".tab4_0").attr("data");
    		data.transportId=itemGoodsData.transportId;
    		data.orderNum=unloadingOrderNum;
    		data.backflowId=itemGoodsData.backflowId;
    	}
    	if(itemGoodsData.itemStatus==9){
    		$.ajax({
				 type:"get",
				 url:config.getDomain() + "/inboundoperation/list",
				 data:{'id':itemDetailArrival.id,'result':16,'productId':itemGoodsData.productId,'cargoGoodsStatus':'0'},
				 dataType:"json",
				 success:function(data){
					 if(data.code=='0000'){
						 if(data.data!=null){
			                       var mdata=data.data;		
			                      //初始化clientData
			                       var count=0;
								 for(var i=0;i<mdata.length;i++){
			                         var goodsData=mdata[i].goodsdata;
			                         if(goodsData.length>0){
				                         for(var j=0;j<goodsData.length;j++){
				                        count=util.FloatAdd(count,goodsData[j].goodsInPass);
				                        count=util.FloatAdd(count,goodsData[j].goodsOutPass);
				                         }
				                         }
				                        }
								 
								 if(count!=0){
									 $('body').message({
										type:'warning',
										content:'该流程无法回退！'
									 });
								 }else{ajaxCleanToStatus();}
								 }else{
									 ajaxCleanToStatus();}}}});
    	}else{
    		ajaxCleanToStatus();
    	}
    	function  ajaxCleanToStatus(){
    		$.ajax({
        		type:"post",
        		url:config.getDomain()+"/inboundoperation/cleantostatus",
        		data:data,
        		dataType:'json',
        		success:function(data){
        			if(isMsg!=undefined&&isMsg){
        				refreshViewData(getState(status),itemDetailArrival.id,itemGoodsData.productId);
        			}else{
        			if(data.code=="0000"){
        				$('body').message({
        					type:'success',
        					content:'回退成功'	
        				});
        					refreshViewData(getState(status),itemDetailArrival.id,itemGoodsData.productId);
//        				setTimeout(function(){window.location.href='#inboundoperation';}, 1000);
        			}else{
        				$('body').message({
                            type:'error',
                            content:'回退失败'
        				});
        			}
        			}
        		}
        	});
    	}
    };
    function getState(status){
    	if(status==2){
    		return 1;
    	}
    	else if(status==3){
    		return 2;
    	}
    	else if(status==4){
    		return 2;
    	}
    	else if(status==5){
    		return 3;
    	}
    	else if(status==6){
    		return 4;
    	}
    	else if(status==7){
    		return 5;
    	}
    	else if(status==8){
    		return 6;
    	}
    }
/******************************************************dialog**********************************************************************/	
	/**修改单个入库列表项*/
	function  dialogMfInbound(id){
	 $.get(config.getResource()+ "/pages/inbound/inboundoperation/update_inboundoperation.jsp").done(
				function(data) {
					dialog = $(data);
					initDialogMfInboundCtr(dialog);
					initDialogMfInboundMsg(dialog,id);
					handleIsTransport(dialog);
					dialog.modal({
						keyboard : true
					});
				}); 
		};
		function initDialogMfInboundCtr(dialog){
			initFormIput(dialog);
			util.initTimePicker(dialog);
			//泊位	
	  		util.urlHandleTypeahead("/berth/list",dialog.find("#berthId"));
			//船代
	  		util.urlHandleTypeaheadAllData("/shipagent/select",dialog.find("#shipAgentId"),function(item){return item.name});
			dialog.find("#save").click(function(){
				var shipRefName=(dialog.find("#shipRefName").val()==dialog.find("#shipRefName").attr('key')?undefined:dialog.find("#shipRefName").val());
				var isMakeDispatchLog=1;
				if(dialog.find("#report").val()==1&&dialog.find("#report").val()==dialog.find("#report").attr('key')){
					isMakeDispatchLog=0;
				}
				var time=util.getTimeVal(dialog.find(".arrivalStartTime"));
				if(time.length==10){
					time=time+" 00:00:00";
				}
				$.ajax({type:"post",
					url:config.getDomain()+ "/inboundoperation/updateInboundInfo",
					data:{
						'arrivalId':dialog.find("#arrivalId").text(),
						'berthprogramId':dialog.find("#berthProgramId").text(),
						'berthId':dialog.find("#berthId").attr('data'),
						'isMakeDispatchLog':isMakeDispatchLog,
						'arrivalTime':util.formatLong(util.getTimeVal(dialog.find(".arrivalStartTime"))),
						'report':(util.isNull(dialog.find("#report").val(),1)==1?'已申报':'未申报'),
						'shipArrivalDraught':util.isNull(dialog.find("#shipArrivalDraught").val(),1),
						'shipAgentId':(util.isNull(dialog.find("#shipAgentId").attr("data"),1)==0?-1:dialog.find("#shipAgentId").attr("data")),
						'shipId':dialog.find("#shipName").attr('data'),
						'arrivalStartTime':time,
						'shipRefName':shipRefName
					},
				  dataType:"json",
				  success:function(data){
					  util.ajaxResult(data,"修改",function(){
						  $('[data-role="inboundoperationGrid"]').getGrid().refresh();
						  dialog.remove();
					  });
				  }
			});
			});
		};
		function initDialogMfInboundMsg(dialog,id){
			if(id){
				$.ajax({ type:"post",
	        	     url:config.getDomain()+"/inboundoperation/list",
	        	     data:{'id':id,'result':0,"isTransport":isTransport},
	        	     dataType:'json',
	        	     success:function(data){
	        	    	 util.ajaxResult(data,'获取信息',function(ndata){
	        	    		 if(ndata&&ndata.length>0){
	        	    			 var itemdata=ndata[0];
	        	    			 	util.initTimeVal(dialog.find(".arrivalStartTime"),itemdata.arrivalStartTime);
	        	    				dialog.find("#shipRefName").val(util.isNull(itemdata.shipRefName)).attr("key",util.isNull(itemdata.shipRefName));
	        	    				dialog.find("#shipName").text(itemdata.shipName).attr('data',itemdata.shipId);
	        	    				dialog.find("#shipArrivalDraught").val(itemdata.shipArrivalDraught);
	        	    				dialog.find("#shipAgentId").val(itemdata.shipAgentName).attr("data",itemdata.shipAgentId);
	        	    				dialog.find("#report").val((itemdata.report=="已申报"?1:0)).attr("key",(itemdata.report=="已申报"?1:0));
	        	    				dialog.find("#berthProgramId").text(itemdata.berthprogramId);
	        	    				dialog.find("#arrivalId").text(itemdata.id);
	        	    				if(itemdata.report=="已申报"){
	        	    					dialog.find("#report").attr("readonly",'readonly');
	        	    					dialog.find("#report").attr("disabled","disabled");
	        	    				}else{
	        	    					dialog.find("#report").removeAttr("readonly");
	        	    					dialog.find("#report").removeAttr("disabled");
	        	    				}
	        	    				dialog.find("#berthId").val(util.isNull(itemdata.berthName)).attr("data",itemdata.berthId);
	        	    				if(itemdata.statusKey>4)
	        	    				dialog.find("#berthId").attr('readonly','readonly');
	        	    				dialog.find(".timepicker-24:focus").blur();//初始化使获得焦点的失去焦点
	        	    		 }
	        	    	 },true);
	        	      }
	                });
			}
		};
		function handleIsTransport(dialog){
			//如果是转输
			if(isTransport==2){
				dialog.find(".notTransport").hide();
				dialog.find("#tp1").text("预计转输:");
				dialog.find("#tp2").text("生成调度日志");
				dialog.find("#tp3").text("否");
				dialog.find("#tp4").text("是");
			}
		};
/******************************************************************************************************************/
	 /**修改管线确认*/
	 function dialogTubeCheck(index){
		 $.get(config.getResource()+ "/pages/inbound/inboundoperation/unloadingready/dialog/dialog_tubecheck.jsp")
			.done(function(data) {
				dialog = $(data);
				initFormIput(dialog);
				util.handleTypeahead([{"key":"正常"},{"key":"不正常"}],dialog.find("#content"),"key","key",false);
				var mdata=null;
				if(index==undefined){
					mdata=$('[data-role="tubeCheckGrid"]').getGrid().selectedRows()[0];
				}else{
					mdata=$('[data-role="tubeCheckGrid"]').getGrid().getAllItems()[index];
				}
				dialog.find("#content").val(mdata.content);
				dialog.find("#checkUserId").val(mdata.checkUserName==null?itemDetailArrival.createUserName:mdata.checkUserName);
				dialog.find("#checkUserId").attr("data",mdata.checkUserId==0?itemDetailArrival.createUserId:mdata.checkUserId);
				dialog.find("#id").val(mdata.id);
				dialog.find("#description").val(mdata.description);
				if(mdata.checkUserName!=null){
					dialog.find("#save").hide();
					dialog.find(".checkTime").show();
					dialog.find("#checkTime").text(util.getSubTime(mdata.checkTime,2));
				}else{
					dialog.find(".checkTime").hide();
					dialog.find("#save").show();
				}
				
				if(mdata.checkType==1){
					dialog.find("#checkType").text("消防动力班");
				}else if(mdata.checkType==2){
					dialog.find("#checkType").text("码头操作班");
				}
		  		util.urlHandleTypeahead("/auth/user/get",dialog.find("#checkUserId"));
				
				dialog.find("#save").click(function(){
					if(config.validateForm(dialog)){
					config.load();
					$.ajax({
						type : "post",
						url : config.getDomain() + "/inboundoperation/updateworkcheck",
						data : {
							"id":mdata.id,
							"transportId":itemGoodsData.transportId,
//							"checkType":1,
							"checkTime":util.formatLong(util.currentTime(1)),
							"checkUserId":dialog.find("#checkUserId").attr("data"),
							"content":dialog.find("#content").val(),
							"description":dialog.find("#description").val()
						},
						dataType : "json",
						success:function(data){
							util.ajaxResult(data,'提交',function(){
								$('[data-role="tubeCheckGrid"]').getGrid().refresh();
								dialog.remove();
							});
						}
					});
					}
				});
				
				dialog.modal({
					keyboard : true
				});
			});
	 };
	 
	 /**修改最后检查*/
	 function dialogAllCheck(item,index){
		 $.get(config.getResource()+ "/pages/inbound/inboundoperation/unloadingready/dialog/dialog_allcheck.jsp")
			.done(function(data) {
				dialog = $(data);
				util.initTimePicker(dialog);
				initFormIput(dialog);
				if(item==0){
					dialog.find('option[data="2"]').remove();
				}else{
					dialog.find('option[data="1"]').remove();
				}
				var mdata=null;
				if(index==undefined){
				 mdata=$('[data-role="jobCheckGrid"]').getGrid().selectedRows()[0];
				}else{
					mdata=$('[data-role="jobCheckGrid"]').getGrid().getAllItems()[index];	
				}
				dialog.find("#createTime").val(mdata.createTime.split(" ")[0]);
				dialog.find("#post").text(mdata.post);
				dialog.find("#checkContent").text(mdata.checkContent);
				if(mdata.solve!=null)
				dialog.find("#solve").val(mdata.solve);
				if(mdata.result!=null)
				dialog.find("#result").val(mdata.result);
				dialog.find("#createUserId").val(mdata.createUserName==null?itemDetailArrival.createUserName:mdata.createUserName);
				dialog.find("#createUserId").attr("data",mdata.createUserId==0?itemDetailArrival.createUserId:mdata.createUserId);
				dialog.find("#id").val(mdata.id);
		  		util.urlHandleTypeahead("/auth/user/get",dialog.find("#createUserId"));
		  		if(mdata.createUserName!=null){
					dialog.find("#save").hide();
					dialog.find(".createTime").show();
					dialog.find("#createTime").text(util.getSubTime(mdata.createTime,2));
				}else{
					dialog.find(".createTime").hide();
					dialog.find("#save").show();
				}
				dialog.find("#save").click(function(){
					config.load();
					$.ajax({
						type : "post",
						url : config.getDomain() + "/inboundoperation/updatejobcheck",
						data : {
							"id" : dialog.find("#id").val(),
							"solve":dialog.find("#solve").val(),
							"result":dialog.find("#result").val(),
							"createTime":util.formatLong(util.currentTime(1)),
							"createUserId":dialog.find("#createUserId").attr("data")
						},
						dataType : "json",
						success:function(data){
							util.ajaxResult(data,'提交',function(ndata){
								$('[data-role="jobCheckGrid"]').getGrid().refresh();
								dialog.remove();
							});
						}
					});
				});
				dialog.modal({
					keyboard : true
				});
			});
	 };
    /**数量审核*/
	 function dialogAmountAffirm(index){
		 $.get(config.getResource()+ "/pages/inbound/inboundoperation/amountaffirm/dialog/dialog_amountaffirm.jsp")
			.done(function(data) {
				var mdata;
				var mTankCodes=null;
				var goodsId=null;
				dialog = $(data);
				initFormIput(dialog);
				if(index||index==0){
					mdata=$('[data-role="clientAmountGrid"]').getGrid().getAllItems()[index];
				}else{
					mdata=$('[data-role="clientAmountGrid"]').getGrid().getAllItems()[0];
				}
				var item="a"+mdata.cargoId;
				if(clientData==null||clientData==undefined){
					clientData={};
				}
				if(clientData[item]==null||clientData[item]==undefined){
					clientData[item]={'tankMsg':new Array(),
							'tankTotal':0,
							'goodsShip':0,
							'goodsLoss':0,
							'lossRate':0,
							'orderType':1
                               };
				}else if(clientData[item].orderType==undefined){
					clientData[item].orderType=1;
				}
				
				//处理合同类型
				dialog.find('.tankDiv').hide();
				dialog.find('#orderType').change(function(){
					if(dialog.find('#orderType').val()==1){
						dialog.find('.tankDiv').hide();
						dialog.find('.countDiv').show();
					}else{
						dialog.find('.tankDiv').show();
						dialog.find('.countDiv').hide();
					}
				});
				if(clientData[item].orderType&&clientData[item].orderType==2){
					dialog.find('.tankDiv').show();
					dialog.find('.countDiv').hide();
					dialog.find('#orderType').val('2');
				}else{
					dialog.find('.tankDiv').hide();
					dialog.find('.countDiv').show();
					dialog.find('#orderType').val('1');
				}
				if(clientData[item].tankMsg&&clientData[item].tankMsg.length>0)
				dialog.find('#orderType').attr('disabled','disabled');
				if(clientData[item].orderType==1){
					if(clientData[item].tankMsg&&clientData[item].tankMsg.length>0){
						goodsId=clientData[item].tankMsg[0].goodsId;
                         if(clientData[item].tankMsg[0]&&clientData[item].tankMsg[0].tankCodes){
                        	 mTankCodes=util.isNull(clientData[item].tankMsg[0].tankCodes);
                        	
                         }else{
                        	 mTankCodes=null;
                         }							
						}else { mTankCodes=null;}
				}
				
				
				
				dialog.find("#clientName").text(mdata.clientName);
				dialog.find("#productName").text(mdata.productName);
				dialog.find("#goodsPlan").text(util.toDecimal3(mdata.goodsPlan,true));
				dialog.find("#goodsLoss").text(util.isNull(clientData[item].goodsLoss,1));
				dialog.find("#lossRate").text(clientData[item].lossRate+"‰");
				
				dialog.find("#ctLossRate").val(clientData[item].ctLossRate);
				//拼装罐列表
				$.ajax({
					type:'get',
					url:config.getDomain()+"/tanklog/getTankLogStoreSum?arrivalId="+itemDetailArrival.id+"&productId="+itemGoodsData.productId,
					success:function(data){
						var ndata=data.data;
						var allTankNum=0;
						var allTank=new Array();
						var allTankIds="";
						var allTankNames="";
						var splitNum=0;
						var itemHtml="<tbody>";
						for(var i=0;i<ndata.length;i++){
							if(ndata[i].productId==mdata.productId){
								var tankNum=0;
								if(clientData[item]==null||clientData[item].tankMsg==undefined||clientData[item].tankMsg.length==0){
									tankNum==0;
								}else{
									var tankMsg=clientData[item].tankMsg;
									for(var j=0;j<tankMsg.length;j++){
										if(ndata[i].tankId==tankMsg[j].tankId&&clientData[item].orderType==2){
										tankNum=tankMsg[j].tankNum;
										ndata[i].goodsId=tankMsg[j].goodsId;
										break;
										}
									}
									}
								itemHtml+="<tr>"
								allTankIds+=ndata[i].tankId+",";
								allTankNames+=ndata[i].tankCode+",";
								allTank.push({id:ndata[i].tankCode,text:ndata[i].tankCode});
								allTankNum=util.FloatAdd(allTankNum,ndata[i].realAmount);
								itemHtml+="<td class='goodsId' style='display:none;' data='"+ndata[i].goodsId+"'></td>"
									    +"<td  class='tankCode' style='vertical-align:middle' data='"+ndata[i].tankId+"'>"+ndata[i].tankCode+"</td>"
									    +"<td  class='productName' style='vertical-align:middle' >"+ndata[i].productName+"</td>"
									    +"<td class='realAmount' style='vertical-align:middle' >"+util.toDecimal3(ndata[i].realAmount,true)+"</td>"
									    +"<td width='150' style='vertical-align:middle'><input type='text'  style='width:100%' class='tankNum form-control' data='"+util.isNull(tankNum,1)+"' value='"+util.toDecimal3(tankNum,true)+"'></td></tr>"
							}
						}
						itemHtml+="</tbody>";
						dialog.find("#amountTable").append(itemHtml);
						//混罐
						dialog.find("#allTankNum").text(util.toDecimal3(allTankNum,true));
//						dialog.find("#allTankNames").text(allTankNames.substring(0,allTankNames.length-1));
						if(mTankCodes!=undefined&&mTankCodes!=null){
							dialog.find("#allTankNames").val(mTankCodes);
						}else{
							dialog.find("#allTankNames").val(allTankNames.substring(0,allTankNames.length-1));
						}
						dialog.find("#allTankNames").select2({tags:allTank});
						if(clientData[item].tankMsg!=undefined&&clientData[item].tankMsg[0]!=undefined&&clientData[item].tankMsg[0].tankNum!=undefined)
						dialog.find("#splitNum").val(util.isNull(clientData[item].tankMsg[0].tankNum,1));
						
						
						
						
						
						
					}
				});
				/**将混罐的数据同步到包罐的xml上去*/
				function  sameToTable(splitNum){
					var tankMsga=new Array();
					 tankMsga.push({   'goodsId':goodsId,
						               'tankCodes':dialog.find("#allTankNames").val(),
										'tankNum':dialog.find("#splitNum").val()});
					var loss=util.FloatSub(dialog.find("#goodsPlan").text(),dialog.find("#splitNum").val());
					var lossRate=util.FloatDiv(loss*1000,dialog.find("#goodsPlan").text());
					clientData[item]={
							'tankMsg':tankMsga,
							'tankTotal':dialog.find("#splitNum").val(),
							'goodsShip':0,
							'goodsLoss':loss,
							'lossRate':lossRate,
							'orderType':1,
							'ctLossRate':dialog.find("#ctLossRate").val()
					}
				}
				dialog.find("#save").click(function(){
						//处理混罐
					var isSure=true;
						if(dialog.find("#orderType").val()==1){
							if(config.validateForm(dialog.find(".splitNumDiv"))){
//								if(parseFloat(dialog.find("#splitNum").val())>parseFloat(dialog.find("#goodsPlan").text())){
//									$('body').message({
//										type:'warning',
//										content:'拆分数量不能大于计划数量'
//									});
//									isSure=false;
//								}else{
									sameToTable(parseFloat(dialog.find("#splitNum").val()));
//								}							
								}else{
								isSure=false;
							}
							}else{
					var tankMsg=clientData[item].tankMsg;
					var tankTotal=clientData[item].tankTotal;
					var goodsShip=clientData[item].goodsShip;
					var goodsLoss=clientData[item].goodsLoss;
					var lossRate=clientData[item].lossRate;
					dialog.find("#amountTable").children("tbody").each(function(){
						    tankMsg=new Array();
						    tankTotal=0;
						    goodsLoss=0;
						    lossRate=0;
							dialog.find('td[class="tankCode"]').each(function(){
								if(isSure){
//								if($(this).is(":checked")){	
								var _tr=$(this).parents("tr");
								if(parseFloat($(_tr).find('input[class="tankNum form-control"]').val())!=0){
								var count=$(_tr).find(".realAmount").text();
//								if(parseFloat($(_tr).find('input[class="tankNum form-control"]').val())>count){
//									$('body').message({
//										type:'error',
//										content:'操作量不能大于储罐数量'
//									});
//									isSure=false;
//									return;
//								}
								if(isSure){
								var item={'goodsId':$.isNumeric($(_tr).find(".goodsId").attr("data"))?$(_tr).find(".goodsId").attr("data"):null,
										'tankId':$(_tr).find(".tankCode").attr("data"),
										'tankCode':$(_tr).find(".tankCode").text(),
										'tankNum':parseFloat($(_tr).find('input[class="tankNum form-control"]').val())
								};
								tankMsg.push(item);
								tankTotal=util.FloatAdd(tankTotal,$(_tr).find('input[class="tankNum form-control"]').val());
								}
								}}});
					});
					//损耗率
					if(isSure){
						var loss=util.FloatSub(dialog.find("#goodsPlan").text(),tankTotal);
							goodsLoss=Math.abs(loss);
							lossRate= util.FloatDiv(loss*1000,parseFloat(dialog.find("#goodsPlan").text()));
							
							clientData[item]={
									'tankMsg':tankMsg,
									'tankTotal':tankTotal,
									'goodsShip':0,
									'goodsLoss':goodsLoss,
									'lossRate':lossRate,
									'orderType':2,
									'ctLossRate':dialog.find("#ctLossRate").val()
							}
					}
				}     
						if(isSure){
						$('[data-role="clientAmountGrid"]').getGrid().refresh();
	                   dialog.remove();	
						}
				});
				
				dialog.find("#close").click(function(){
					dialog.remove();
				});
				dialog.modal({
					keyboard : true
				});
			});
	 }
	 
	/**码头接卸作业通知单*/ 
		function dialogDockNotify(obj) {
			var taskmsg="接卸：【"+itemDetailArrival.shipRefName+"】【"+itemGoodsData.productName+"】【"+itemGoodsData.goodsNum+"】(吨)";
			notify.init(2,$(obj).attr("data"),itemGoodsData.transportId,taskmsg,null,true);
					};
		/**动力班接卸作业通知单*/ 
		function dialogDynamicNotify(obj) {
			var taskmsg="接卸：【"+itemDetailArrival.shipRefName+"】【"+itemGoodsData.productName+"】【"+itemGoodsData.goodsNum+"】(吨)";
			notify.init(3,$(obj).attr("data"),itemGoodsData.transportId,taskmsg,null,true);
		};
		/**码头打循环作业通知单*/ 
		function dialogDockBackFlowNotify(obj) {
			var id=$(obj).closest(".backflowplan").find("#infoId").val();
			if(id&&id!=0){
			var taskmsg="打循环：【"+itemDetailArrival.shipRefName+"】【"+itemGoodsData.productName+"】【"+$("#tankCount").val()+"】(吨)";
			notify.init(0,$(obj).attr("data"),itemGoodsData.backflowId,taskmsg,null,true);
			}else{
				$('body').message({type:'warning',content:'请保存后再点击！'});
			}
		};
		function dialogZhuanShuNotify(obj){
			var taskmsg="转输：【"+itemGoodsData.productName+"】【"+itemGoodsData.goodsNum+"】(吨)";
			notify.init(7,$(obj).attr("data"),itemGoodsData.transportId,taskmsg,null,true);
		}
		
		/**库区打循环方案通知单*/ 
		function dialogStoreBackFlowNotify(obj) {
			var id=$(obj).closest(".backflowplan").find("#infoId").val();
			if(id&&id!=0){
			var taskmsg="打循环：【"+itemDetailArrival.shipRefName+"】【"+itemGoodsData.productName+"】【"+$("#tankCount").val()+"】(吨)";
			notify.init(1,$(obj).attr("data"),itemGoodsData.backflowId,taskmsg,null,true);
			}else{
				$('body').message({type:'warning',content:'请保存后再点击！'});
			}
		};
    
		
	     /**打开显示货品详情*/
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
	 							render:function(item){
	 								return util.toDecimal3(item.goodsPlan,true);
	 							}
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
	 	
	 	function handleItemGoods(obj){
			var nRow = $(obj).closest("tr");
			var jqTds = $('td', nRow);
			if($(obj).attr('key')==1){
			jqTds[4].innerHTML='<input type="text" class="form-control cargoAgentId"  id="cargoAgentId"></input>';
			jqTds[5].innerHTML='<a onclick="InboundOperation.handleItemGoods(this)" key="2" class="btn btn-xs blue submit" href="javascript:void(0)">'
				+'<span title="提交" class="glyphicon glyphicon glyphicon-ok"></span></a>'
		  				$(nRow).find("#cargoAgentId").typeahead({
		  					source: function(query, process) {
		  						var results = _.map(cargoAgentData, function (berth) {
		  	                        return berth.code;
		  	                    });
		  	                    process(results);
		  					},
		  					noselect: function (query) {
			  					var item = _.find(cargoAgentData, function (p) {
			                        return p.code == query;
			                    });
			  					if(item==null){
			  						$(nRow).find("#cargoAgentId").attr("data","");
			  						$(nRow).find("#cargoAgentId").val("");
			  					}else{
			  						$(nRow).find("#cargoAgentId").attr("data",item.id)
			  					}
			  				}
		  				});
		  }else{
			  var id=$(nRow).find("#cargoAgentId").attr("data");
			  var value=$(nRow).find("#cargoAgentId").val();
			  jqTds[4].innerHTML='<label class="form-label cargoAgentId" id="cargoAgentId" data="'+id+'" >'+value+'</label>';
			  jqTds[5].innerHTML="<a href='javascript:void(0)' class='btn btn-xs blue edit' key='1' onclick='InboundOperation.handleItemGoods(this)'>"
				+" <span class='glyphicon glyphicon glyphicon-edit' title='编辑'></span></a>";
		  }
		}
	 	
	 	/**显示隐藏部分*/
		 function openWarning(item){
				if(item==1){
					$(".dialog-warning1").slideToggle("slow");
				}else if(item==2){
					$(".dialog-warning2").slideToggle("slow");
				}else{
					$(".dialog-warning3").slideToggle("slow");
				}
				
			};
		 /**显示隐藏部分*/
		 function openHide(obj,item){
//				if(item==1){
					$(".dialog-warning"+item).slideToggle("slow");
					var $this=$(obj);
					if($this.find("i").attr("class")=="fa fa-chevron-left"){
						$this.find("i").removeClass();
						$this.find("i").addClass("fa fa-chevron-down");
					}else if($this.find("i").attr("class")=="fa fa-chevron-down"){
						$this.find("i").removeClass();
						$this.find("i").addClass("fa fa-chevron-left");
					}
//				}else {
//					$(".dialog-warning2").slideToggle("slow");
//				}
			};
			
			function formatstr(time,nosec){
				if(nosec==0){
				var length=time.length;
				if(length==10){
					return time+" "+ "00:00:00";
				}
				if(length==16){
					return time+":00";
				}
				}else if(nosec==1){
					return time.substring(0,16);
				}else if(nosec==2){
					return time.substring(0,10);
				}
			}
			//时间格式化
			Date.prototype.Format = function(fmt) {
					var o = {
						"M+" : this.getMonth() + 1, //月份 
						"d+" : this.getDate(), //日 
						"h+" : this.getHours(), //小时 
						"m+" : this.getMinutes(), //分 
						"s+" : this.getSeconds(), //秒 
						"q+" : Math.floor((this.getMonth() + 3) / 3), //季度 
						"S" : this.getMilliseconds()
					//毫秒 
					};
					if (/(y+)/.test(fmt))
						fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
								.substr(4 - RegExp.$1.length));
					for ( var k in o)
						if (new RegExp("(" + k + ")").test(fmt))
							fmt = fmt.replace(RegExp.$1,
									(RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
											.substr(("" + o[k]).length)));
					return fmt;
				};
				//计算所有货品数量
				function countAllGoodsNum(){
				if(itemDetailArrival!=null){
					var goodsData=itemDetailArrival.goodsMsg;
					var goodsAllNum=0;
					for(var i=0;i<goodsData.length;i++){
						goodsAllNum=util.FloatAdd(goodsAllNum,goodsData[i].goodsPlan);
					}
					return goodsAllNum;
				}else{
				return 0;	
				}
			};
				/**处理提示信息*/
				function msgContent(status,success){
					if(success&&success==1){
						if(status==0){
							return "保存成功";
						}else if(status==1){
							return "提交成功";
						}else if(status==2){
							return "审核成功";
						}else if(status==3){
							return "驳回成功";
						}else if(status==4){
							return "品质审核成功";
						}else if(status==5){
							return "工艺审核成功";
						}else if(status==6){
							return "审核成功";
						}
					}else if(success&&success==0){
						if(status==0){
							return "保存失败";
						}else if(status==1){
							return "提交失败";
						}else if(status==2){
							return "审核失败";
						}else if(status==3){
							return "驳回失败";
						}else if(status==4){
							return "品质审核失败";
						}else if(status==5){
							return "工艺审核失败";
						}else if(status==6){
							return "审核失败";
						}
					}else{
						if(status==0){
							return "保存";
						}else if(status==1){
							return "提交";
						}else if(status==2){
							return "审核";
						}else if(status==3){
							return "驳回";
						}else if(status==4){
							return "品质审核";
						}else if(status==5){
							return "工艺审核";
						}else if(status==6){
							return "审核";
						}
					}
				};
				//处理货品
		function  handleGoodsData(data){
			if(data!=null){
				var goodsmsg=data;
			}else{
				var goodsmsg=itemDetailArrival.goodsMsg;
			}
			 var ptIds='';
			 var goodsNum=new Array();
			 var goodsName=new Array();
			 var transPorts=new Array();//接卸方案
			 var transPortsStatus=new Array();//接卸方案状态
			 var works=new Array();//入库作业
			 var goodsPt=new Array();
			 var itemStatus=new Array();//单项的 流程状态
			 var leaveTime=new Array();//离港时间
			 var atamreviewsStatus=new Array();//数量确认审批状态
			 var result=new Array();
			 var j=0;
			 if(goodsmsg!=null&&goodsmsg!=""&&goodsmsg.length>0){
			 for (var i = 0; i < goodsmsg.length; i++) {
					var itemgoodsmsg=goodsmsg[i];
					if(ptIds.indexOf('a'+itemgoodsmsg.productId+'a')==-1){
						ptIds+='a'+itemgoodsmsg.productId+'a';
						goodsPt[j]="a"+itemgoodsmsg.productId;
						goodsNum["a"+itemgoodsmsg.productId]=itemgoodsmsg.goodsPlan;
						goodsName["a"+itemgoodsmsg.productId]=itemgoodsmsg.productName;
						transPorts["a"+itemgoodsmsg.productId]=itemgoodsmsg.transportprogramId;
						transPortsStatus["a"+itemgoodsmsg.productId]=itemgoodsmsg.ttpmreviewStatus;
						works["a"+itemgoodsmsg.productId]=itemgoodsmsg.workId;
						leaveTime["a"+itemgoodsmsg.productId]=itemgoodsmsg.leaveTime;
						itemStatus["a"+itemgoodsmsg.productId]=itemgoodsmsg.itemStatusKey;
						atamreviewsStatus["a"+itemgoodsmsg.productId]=itemgoodsmsg.atamreviewStatus;
						j++;
					}else{
						goodsNum["a"+itemgoodsmsg.productId]=util.FloatAdd(goodsNum["a"+itemgoodsmsg.productId],itemgoodsmsg.goodsPlan);
					}
				}
			 
			 for (var i = 0; i < goodsPt.length; i++) {
					var pt=goodsPt[i];
					result.push({
						'productId':pt.substr(1,pt.length),//货品Id
						'productName':goodsName[pt],//货品名
						'goodsNum':goodsNum[pt],//货品数量
						'transportId':transPorts[pt],//接卸方案Id
						'transportStatus':transPortsStatus[pt],//接卸方案审批状态
//						'backflowId':backFlows[pt],//打循环id
//						'backflowStatus':backFlowStatus[pt],//打循环状态
						'workId':works[pt],//workId
						'leaveTime':leaveTime[pt],
						'itemStatus':itemStatus[pt],//单个货品的流程状态
						'atamreviewsStatus':atamreviewsStatus[pt]//数量确认的审批流程状态
					});
				}
			 }
			 return result;
		}	
		/**处理小流程状态*/
		function  handleStatus(status){
			if(status==0){
				return "-未提交";
			}else if(status==1){
				return "-审核中";
			}else if(status==2){
				return "-已完成";
			}else if(status==3){
				return "-已退回";
			}else if(status==4){
				return "-工艺审核中";
			}else if(status==5){
				return "-品质审核中"
			}else if(status==6){
				return "-准备"
			}
		};		
	       /**处理单个货品流程状态*/	
			function handleGoodsStatus(item,arrivalType,leaveTime){
				if(item.itemStatus==5){
					return '<label style="color:#9966CC;margin-left:8px;">接卸方案'+handleStatus(item.transportStatus)+'</label>';
				}else if(item.itemStatus==6){
					return '<label style="color:#9966CC;margin-left:8px;">接卸准备-未提交</label>';
				}else if(item.itemStatus==7){
//					return '<label style="color:#996699">打循环方案'+handleStatus(item.backflowStatus)+'</label>';
					return '<label style="color:#996699;margin-left:8px;">打循环方案</label>';
				}else if(item.itemStatus==8){
					return '<label style="color:#FF9966;margin-left:8px;">数量审核'+handleStatus(item.atamreviewsStatus)+'</label>';
				}else if(item.itemStatus==9){
					if(arrivalType&&arrivalType==3&&util.isNull(item.leaveTime,1)==0)
						return '<label style="color:#9966CC;margin-left:8px;">接卸中</label>';
					else
						return '<label style="color:#0099CC;margin-left:8px;">入库完成</label>';
				}
			};	
			/**处理单个货品的打循环流程*/
			function handlebackflowGoodsStatus(item){
				if(item.itemStatus>6){
//				return '<label style="color:#996699">打循环方案'+handleStatus(item.backflowStatus)+'</label>';
					return  '<label style="color:#996699;margin-left:8px;">打循环方案</label>';
				}
			};
			//根据状态获取tab的页数
			function getPage(flag,data){
				if(flag==2){//入库计划
					return 1;
				}else if(flag==3){//靠泊评估
					return 2;
				}else if(flag==4){//靠泊方案
					return 2;
				}else {
					if(data!=null&&data!=undefined){//
					if(data.itemStatus==5){
						return 3;//接卸方案
					}else if(data.itemStatus==6){
						return 4;//接卸准备
					}else if(data.itemStatus==7){
						return 5;//打循环方案
					}else if(data.itemStatus==8){
						return 6;//数量确认
					}else if(data.itemStatus==9){
						return 7;//入库完成
					}
				}else{
					return 1;
				}
				}
			};
			//处理选中的货品
			function getGoodsMsg(data,page,isOk,productId){
//				if(page<3){
//					return data;
//				}
				if(!isOk){
					return data;
				}else{
					var mdata=new Array();
					for(var i=0;i<data.length;i++){
						if(data[i].productId==productId){
							mdata.push(data[i]);
						}
					}
					return mdata;
				}
			};
			
            /**刷新数据(处理为首界面请求)*/			
			function  refreshViewData(state,arrivalId,productId){
				if($('[data-role="inboundoperationGrid"]').getGrid() != null)
					$('[data-role="inboundoperationGrid"]').getGrid().destory();
				initView(state,arrivalId,productId,undefined,undefined,isTransport);
			};
			//改变数据
			function changeUnloadData(orderNum,obj,state){
				if(isDeleteUnloading){
				unloadingOrderNum=orderNum;
				$.ajax({ type:"post",
	        	     url:config.getDomain()+"/inboundoperation/list",
	        	     data:{'id':itemDetailArrival.id,'orderNum':orderNum,'productId':itemGoodsData.productId,'result':0,isTransport:isTransport},
	        	     cache:false,
	        	     dataType:'json',
	        	     success:function(data){
	        	    	 util.ajaxResult(data,"",function(ndata){
	        	    		 var goodsMsg=handleGoodsData(ndata[0].goodsMsg);
	        	    		 itemGoodsData=goodsMsg[0];
                            //重新点击	        	    	
	        	    	var page=getPage(5,itemGoodsData);
	        	    	$("#tab3_"+orderNum).parent().addClass("active").siblings().removeClass("active");
	        	    	$("#tab4_"+orderNum).parent().addClass("active").siblings().removeClass("active");
	        	    	if($(obj).closest(".dropdown").hasClass('open')){
	        	    		$(obj).closest(".dropdown").click();
	        	    	}
	        	    	
	        	    	if(state==3){
//	        	    		$(obj).parent().addClass("active").siblings().removeClass("active");
//		        	    	$("#tab").empty();
							if (page > 3) {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/unloadingplan/get.jsp");
							} else {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/unloadingplan/add.jsp");
							}
	        	    	}else if(state==4){
//	        	    		$(obj).parent().addClass("active").siblings().removeClass("active");
//		        	    	$("#tab").empty();
	        	    		if (page > 4) {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/unloadingready/get.jsp");
							} else {
								$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/unloadingready/add.jsp");
							}
	        	    	}
	        	    	 },true);
	        	     }});}
				isDeleteUnloading=true;
			};
			function deleteUnloading(transportId,workId,status,orderNum){
				if(transportId&&workId&&status&&status==5){
					isDeleteUnloading=false;
					$('body').confirm({
						content:'确定要删除该接卸方案吗？',
						callBack:function(){
							deleteUnloadingBackFlow(orderNum);
					$.ajax({
						type:'post',
						url:config.getDomain()+"/inboundoperation/deleteunloading",
						data:{transportId:transportId,workId:workId},
						dataType:'json',
						success:function(data){
							util.ajaxResult(data,'删除',function(ndata){
								unloadingOrderNum=0;
								initUnloading();
								$("#tab3_0").click();
							});
						}
							});}
					});
				}else{
					$('body').message({
						type:'warning',
						content:'请将改接卸回退到接卸方案！'
					});
				}
			}
			//改变打循环
			function changeBackFlowData(bcId,status,obj){
				if(isDeleteBackFlow){
					leftBackFlowId=bcId;//记录当前打循环id
				itemGoodsData.backflowId=bcId;
				itemGoodsData.backflowStatus=status;
				$(obj).parent().addClass("active").siblings().removeClass("active");
				$("#tab").load(config.getResource()+ "/pages/inbound/inboundoperation/backflowplan/add.jsp");
				}
				isDeleteBackFlow=true;
			};
			function deleteBackFlow(id){
				if(id){
					isDeleteBackFlow=false;
					$('body').confirm({
						content:'确定要删除该打循环方案吗？',
						callBack:function(){
							$.ajax({
								type:'post',
								url:config.getDomain()+"/inboundoperation/deletebackflow",
								data:{transportId:id},
								dataType:'json',
								success:function(data){
									util.ajaxResult(data,'删除',function(){
										initBackFlow();
									});
								}
							});
						}
					});
				}
			};
			//当删除接卸方案时同步删除对应所有打循环
			function deleteUnloadingBackFlow(orderNum){
				config.load();
				$.ajax({
					type:'post',
					url:config.getDomain()+"/inboundoperation/list",
					data:{arrivalId:itemDetailArrival.id,productId:itemGoodsData.productId,unloadingType:2,orderNum:orderNum,result:7},
					dataType:'json',
					success:function(data){
		               util.ajaxResult(data,'打循环初始化',function(ndata){
		            	   $(".backflowLi").remove();
		            	   var backflowData=ndata[0].backflowplan;
		            	   var minId=backflowData[0].backflowId; 
		            	   for(var i in backflowData){
		            		   if(backflowData[i].orderNum||backflowData[i].orderNum==0){
		            			   $.ajax({
										type:'post',
										url:config.getDomain()+"/inboundoperation/deletebackflow",
										data:{transportId:backflowData[i].backflowId},
										dataType:'json',
										success:function(data){
											util.ajaxResult(data,'删除',function(){
											},true);
										}
									});
		            		   }
		            	   }
		               },true);				
					}
				});
			};
			function initGlobalValue(){
				   unloadingOrderNum=0;//保存当前的接卸方案
				   leftBackFlowId=0;//保存当前记录的打循环id
			}
/************************************************************************************/			
			//货品数量字符串拼接
			function getGoodsMsgStr(goodsDataList,productId){
				if(goodsDataList&&goodsDataList.length>0){
				var data=handleGoodsData(goodsDataList);
					var str='';
					for(var i=0;i<data.length;i++){
						if(productId&&data[i].productId==productId){
							str+="货品："+data[i].productName+",数量："+data[i].goodsNum+"吨;"							
						}else if(!productId){
							str+="货品："+data[i].productName+",数量："+data[i].goodsNum+"吨;"
						}
					}
				}
				return str;
			}

/**************************************************************************************/
			function exportInbound(){
			var url=config.getDomain()+'/inboundoperation/exportInbound?';
				if(util.isNull($("#startTime").val(),1)!=0){
					url+='startTime='+$("#startTime").val();
				}else{
					$('body').message({
						type:'warning',
						content:'请选择起始日期'	
					});
					return;
				}
				if(util.isNull($("#endTime").val(),1)!=0){
					url+='&endTime='+$("#endTime").val();
				}else{
					$('body').message({
						type:'warning',
						content:'请选择止计日期'	
					});
					return;
				}
				window.open(url);
			}
			
			//一键导出
			function allDownload(transportprogramId,arrivalId){
				

				 $.get(config.getResource()+ "/pages/inbound/inboundoperation/dialog_alldownload.jsp").done(
							function(data) {
								dialog = $(data);
								//接卸方案
								dialog.find("#alldown1").unbind('click').bind('click',function(){
									PlanManager.exportTransportProgram(transportprogramId);
								});
								//靠泊方案
								dialog.find("#alldown2").unbind('click').bind('click',function(){
									PlanManager.exportBerthProgram(arrivalId);
								});
								//接卸通知单动力班
								dialog.find("#alldown3").unbind('click').bind('click',function(){
									
									 //初始化接卸方案工艺流程
									$.ajax({type:"post",
										url:config.getDomain() + "/inboundoperation/list",
										data:{"id":transportprogramId,"transportIds" :transportprogramId,"result":4},
										dataType:"json",
										success:function(data){
											util.ajaxResult(data,"获取工艺流程",function(ndata){
												if(ndata){
													var mdata=ndata[0];
													$.ajax({
														type:"post",
														url:config.getDomain()+"/notify/list",
														data:{'code':mdata.noticeCodeB,'types':14,'isList':1},
														dataType:"json",
														success:function(data){
															sdata=data.data[0];
															mContent=JSON.parse(sdata.content);
															var url = config.getDomain()+'/notify/exportItemExcel?types='+14+"&name="+'接卸作业通知单（动力班）'+'&code='
															+sdata.code+"&transportId="+transportprogramId
															+'&taskMsg='+mContent.taskMsg.replace(/\#/g,"%23")+"&taskRequire="+mContent.taskRequire.replace(/\#/g,"%23");
															window.open(url);
															
														}
													});
													
												} },true);
										}
									});
									
									
								});
								//接卸通知单码头
								dialog.find("#alldown4").unbind('click').bind('click',function(){
									
									 //初始化接卸方案工艺流程
									$.ajax({type:"post",
										url:config.getDomain() + "/inboundoperation/list",
										data:{"id":transportprogramId,"transportIds" :transportprogramId,"result":4},
										dataType:"json",
										success:function(data){
											util.ajaxResult(data,"获取工艺流程",function(ndata){
												if(ndata){
													var mdata=ndata[0];
													$.ajax({
														type:"post",
														url:config.getDomain()+"/notify/list",
														data:{'code':mdata.noticeCodeA,'types':13,'isList':1},
														dataType:"json",
														success:function(data){
															sdata=data.data[0];
															mContent=JSON.parse(sdata.content);
															var url = config.getDomain()+'/notify/exportItemExcel?types='+13+"&name="+'接卸作业通知单（码头）'+'&code='
															+sdata.code+"&transportId="+transportprogramId
															+'&taskMsg='+mContent.taskMsg.replace(/\#/g,"%23")+"&taskRequire="+mContent.taskRequire.replace(/\#/g,"%23");
															window.open(url);
															
														}
													});
													
												} },true);
										}
									});
									
								});
								//打循环方案
								dialog.find("#alldown5").unbind('click').bind('click',function(){
									PlanManager.exportChangeTankProgram(transportprogramId,1);
									
									
								});
								//倒罐方案
								dialog.find("#alldown6").unbind('click').bind('click',function(){
									PlanManager.exportChangeTankProgram(transportprogramId,0);
									
								});
								dialog.modal({
									keyboard : true
								});
							}); 
					
				
			}
			
			return {
				initTab : initTab,//初始化入库列表	
				changetab : changetab,//入库列表tab修改
				initSearch:initSearch,//初始化搜索选项
				initView : initView,//初始化详情头部基本信息
				initArrivalInfo:initArrivalInfo,//初始化入库计划
				initBerthPlan:initBerthPlan,//初始化靠泊方案
				initTransportProgram:initTransportProgram,//初始化接卸方案
				initUnloadingReady:initUnloadingReady,//初始化接卸准备
				initBackFlowPlan:initBackFlowPlan,//初始化打循环方案
				initAmountAffirm:initAmountAffirm,//初始化数量确认
				cleanToStatus:cleanToStatus,//回退
				dialogMfInbound:dialogMfInbound,//修改个人信息
				showBerthAssess:showBerthAssess,//靠泊评估
				dialogDockNotify: dialogDockNotify,//(码头)接卸任务通知单
				dialogDynamicNotify:dialogDynamicNotify,//(动力班)接卸任务通知单
				dialogZhuanShuNotify:dialogZhuanShuNotify,//转输作业通知单
				dialogDockBackFlowNotify :dialogDockBackFlowNotify,//打循环任务通知单
				dialogStoreBackFlowNotify :dialogStoreBackFlowNotify,//打循环任务通知单
				checkgoodsdetail:checkgoodsdetail,//显示货品详情
				dialogTubeCheck:dialogTubeCheck,//管线检查
				dialogAllCheck:dialogAllCheck,//最后检查
				dialogAmountAffirm:dialogAmountAffirm,//修改数量审核
				handleItemGoods:handleItemGoods,//处理单个货品
				openWarning:openWarning,//显示隐藏部分
				openHide:openHide,//隐藏显示部分
				countAllGoodsNum:countAllGoodsNum,//计算所有货品数量和
				handleGoodsData:handleGoodsData,
				checkFreeNum:checkFreeNum,
				checkBackFlowNum:checkBackFlowNum,
				changeUnloadData:changeUnloadData,
				deleteUnloading:deleteUnloading,
				changeBackFlowData:changeBackFlowData,
				deleteBackFlow:deleteBackFlow,
				initGlobalValue:initGlobalValue,
				exportInbound:exportInbound,//导出入库信息
				allDownload:allDownload
			};	
}();