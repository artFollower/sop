//船舶出库
var Outbound = function() {
	var columnCheckArray;
	var goodsPlanTotal=0;
	var systemUserId;//登陆人id
	var systemUserName;//登陆人名称
	var isTransports=1;//1，出港 2 转输输出
	//切换tab
	function changetab(obj,status,item){
		$("#isShowAll").attr('data',status);
		$("#isShowAll").attr('key',item);
		$(obj).parent().addClass("active").siblings().removeClass("active");
		if ($(obj).attr("id")=='tab1') {
			$(".mainCtr").show();
		} else {
			$(".mainCtr").hide();
		} 
		var params=getSearchCondition();
		params.isTransport=isTransports;
		params.status=$("#isShowAll").is(":checked")?(status+',54'):status;
		$('[data-role="outboundGrid"]').getGrid().search(params);
	};
   function init(isTransport){
	   isTransports=isTransport;
	   initSearchCtr(isTransport);
	   initTable(isTransport);
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
   };
	function initSearchCtr(isTransport){
		if(isTransport&&isTransport==2){
			$(".notTransport").hide();
		}
		util.initTimePicker($(".serialList")) ;
		util.urlHandleTypeaheadAllData("/product/select",$('#productId'));
		$(".search").click(function(){
			var params=getSearchCondition();
			params.isTransport=isTransport;
	       $('[data-role="outboundGrid"]').getGrid().search(params);
		});
		$(".reset").click(function(){
			cleanSearchMsg();
			$(".search").click();
		});
		$("#isShowAll").click(function(){
			$this=$(this);
			var status=$this.attr('data');
			var params=getSearchCondition();
			params.isTransport=isTransport;
			params.status=$this.is(":checked")?(status+',54'):status;
			$('[data-role="outboundGrid"]').getGrid().search(params);  
			});
	};
	function getSearchCondition(){
		return {
			shipName:$("#shipName").val(),
			productId:$("#productId").attr('data'),
			status:$("#statuskey").val(),
			arrivalType:$("#arrivalType").val(),
			startTime:util.formatLong($("#startTime").val()+" 00:00:00"),
			endTime:util.formatLong($("#endTime").val()+" 23:59:59"),
			itemTab:$("#isShowAll").attr('key'),
			isShowAll:$("#isShowAll").is(":checked")?1:0
		}
	};
	function cleanSearchMsg(){
		$("#shipName,#productId,#startTime,#endTime").val("").removeAttr('data');
		$("#statuskey").val("50,51,52,53");
		$("#arrivalType").val("0");
	};
	function initTable(isTransport){
		var columns=[{title:"预计到港",render:function(item){
			if(item.arrivalTime)
			return item.arrivalTime.substring(0,10);
			else
				return "";
		}},{
			title : "船舶英文名",render:function(item){
				if(config.hasPermission('ACHANGEARRIVALBASEINFO')){
					return '<a href="javascript:void(0);" onclick="Outbound.updateArrivalInfo('+item.id+');" class="blue">'+(isTransport&&isTransport==2?"转输输出":item.shipName)+'</a>';
				}else{
					return  item.shipName;
				}
			}},{title:"船舶中文名",render:function(item){
				
				if(item.type==2){return item.refName;}else if(item.type==5){
				return item.refName+'<label style="color:#00aa99">(通过)<label>';
			}}},{title:"泊位",name:"berthName"
			},{title:"申报状态",name:"report"
			},{title:"货品",name:"productName"
			},{title:"数量（吨）",render:function(item){
				return util.toDecimal3(item.totalNum,true);
			}},{title:"提货单位",name:'ladingClientName',render:function(item){
				if(item.ladingClientName){
				var data=item.ladingClientName.split(",");
				if(data!=null&&data.length>1){
				var html='<table class="table inmtable" style="margin-bottom: 0px;">';
			    for(var i=0;i<data.length;i++){
			    	html+='<tr><td style="border-bottom:1px solid #ddd;white-space:nowrap;"><label>'+data[i]+'</label></td></tr>';
			    	}
			    html += '</table>';
			    return html;
				}else if(data!=null&&data.length==1){
					return '<label>'+data[0]+'</label>';
				}}else{
				return "";
		}
			}},{title:'货主单位',width:130,name:'clientName',render:function(item){

				if(item.clientName){
				var data=item.clientName.split(",");
				if(data!=null&&data.length>1){
				var html='<table class="table inmtable" style="margin-bottom: 0px;">';
			    for(var i=0;i<data.length;i++){
			    	html+='<tr><td style="border-bottom:1px solid #ddd;white-space:nowrap;"><label>'+data[i]+'</label></td></tr>';
			    	}
			    html += '</table>';
			    return html;
				}else if(data!=null&&data.length==1){
					return '<label>'+data[0]+'</label>';
				}}else{
				return "";
		}
			}},{title:"发货提单号",render:function(item){
					if(item.ladingEvidence){
					var data=item.ladingEvidence.split(",");
					if(data!=null&&data.length>1){
					var html='<table class="table inmtable" style="margin-bottom: 0px;">';
				    for(var i=0;i<data.length;i++){
				    	html+='<tr><td style="border-bottom:1px solid #ddd;white-space:nowrap;"><label>'+data[i]+'</label></td></tr>';
				    	}
				    html += '</table>';
				    return html;
					}else if(data!=null&&data.length==1){
						return '<label>'+data[0]+'</label>';
					}}else{
					return "";
			}}},{ title:"开票状态",render:function(item){
				if(item.isInvoice&&item.isInvoice>0){
                    return "<font color='#99CC33'>已开票</font>";					
				}else{
					return "<font color='#FF9966'>未开票</font>";
				}}
				},{title:"流程状态",render:function(item){
                   return "<font color='"+getStatusColor(item.status)+"'>"+item.statusName+"</font>";
			}
			},{
			title : "操作",
			render: function(item){
				return '<div style="width:100px;"  class="input-group-btn">'
				+'<a href="#/'+(isTransport==2?'outboundzhuanshu':'outboundserial')+'/get?status='+(item.type==5&&item.status==54?51:item.status)
				+'&id='+item.id+'" style="margin:0 2px;" class="btn btn-xs blue"><span class="glyphicon glyphicon glyphicon-eye-open" title="详细">'
				+'</span></a>'+
     			'<a href="javascript:void(0)" onclick="Outbound.fileManage('+item.id+',1)" style="margin:0 2px;" class="btn btn-xs blue">'
     			+'<span class="fa fa-file" title="附件管理"></span></a></div>';
			}
		}];
		if($('[data-role="outboundGrid"]').getGrid()!=null){
		$('[data-role="outboundGrid"]').getGrid().destory();
		}
		$('[data-role="outboundGrid"]').grid({
			identity:'id',
			columns:columns,
			searchCondition:{status:'50,51,52,53',isTransport:isTransport},
			isShowIndexCol : false,
			isShowPages : true,
			url:config.getDomain()+"/outboundserial/list",
			gridName:(isTransports==1?"outboundOperation":"zhuanshushuchu"),
			stateSave:true,
			backCallBack:function(data){
				$("#tab"+data.itemTab).parent().addClass("active").siblings().removeClass("active");
				$("#isShowAll").attr('key',data.itemTab);
				if(data.isShowAll==1)
					$("#isShowAll").attr('checked',true);
				isTransports=data.isTransport;
				if(data.itemTab==1)
						$(".mainCtr").show();
				 else   
						$(".mainCtr").hide();
					 
			},
			callback:function(){
				$('.inmtable').closest("td").css('padding','0px');
				$('[data-role="outboundGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
				if(isTransport&&isTransport==2){
				$('[data-role="outboundGrid"]').find(".grid-table-head th[index='0']").text('预计转输');
				$('[data-role="outboundGrid"]').find(".grid-table-head th[index='1']").text('转输类型');
				$('[data-role="outboundGrid"]').find('td[index=2],th[index=2],td[index=3],th[index=3],td[index=4],th[index=4]').addClass("hidden");
				}else
				util.setColumnsVisable($('div[data-role="outboundGrid"]'),[0,12],columnCheckArray,function(){columnCheckArray=util.getColumnsCheckArray();});
			}
		});
	}
	//获取流程状态颜色
	function getStatusColor(status){
		if(status){
			if(status==50){
				return '#99CC33';
			}else if(status==51){
				return '#56452d';
			}else if(status==52){
				return '#9966CC';
			}else if(status==53){
				return '#FF9966';
			}else if(status==54){
				return '#0099CC';
			}
		}
	};
	
/***********************************************************************/
	//更新出港信息
	function updateArrivalInfo(id){
		$.get(config.getResource()+"/pages/outbound/outbound/update_outboundoperation.jsp").done(function(data){
    		dialog = $(data);
    		initFormIput();
    		initDialogArrivalInfoControl(dialog);
    		initDialogArrivalInfoMsg(dialog,id);
    		handleIsTransport(dialog);
		});
	};
	function initDialogArrivalInfoControl(dialog){
		dialog.modal({keyboard: true});
		dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
		util.urlHandleTypeahead("/berth/list",dialog.find("#berthId"));//泊位	
  		util.urlHandleTypeaheadAllData("/shipagent/select",dialog.find("#shipAgentId"),function(item){return item.name});//船代
  		util.initTimePicker(dialog.find(".arrivalTimeDiv"));
  		dialog.find("#save").click(function(){
			var time=util.getTimeVal(dialog.find(".arrivalTime"));
			if(time.length==10){
				time=time+" 00:00:00";
			}
			$.ajax({
				type : "post",
				url : config.getDomain()+"/outboundserial/updateBaseInfo",
				data:{
					"shipAgentId":dialog.find("#shipAgentId").attr("data"),//船代
					"shipArrivalDraught":util.isNull(dialog.find("#shipArrivalDraught").val(),1),//吃水
					"shipId":dialog.find("#shipName").attr('data'),
					"report":(util.isNull(dialog.find("#report").val(),1)==1?'已申报':'未申报'),//申报状态
					"berthId":dialog.find("#berthId").attr("data"),//泊位
					"shipRefName":dialog.find("#shipRefName").val(),
					"productId":util.isNull($('#productId').text(),1),
					"arrivalTime":time,
					"arrivalInfoId":dialog.find("#arrivalInfoId").text(),
					"arrivalId":dialog.find("#id").text()
				},
				dataType : 'json',
				success : function(data) {
					util.ajaxResult(data,'修改',function(){
						initDialogArrivalInfoMsg(dialog,dialog.find("#id").text());
						
						if($('[data-role="outboundGrid"]').getGrid()!=undefined)
							$('[data-role="outboundGrid"]').getGrid().refresh();
//							util.updateGridRow($('[data-role="outboundGrid"]'),{id:dialog.find("#id").text(),url:'/outboundserial/list'});
						
					});
				}
			});
		});
  		
  		
	};
	function initDialogArrivalInfoMsg(dialog,id){
		dialog.find("#id").text(id);
		config.load();
		$.ajax({
			type:'get',
			url:config.getDomain()+'/outboundserial/list',
			data:{id:id},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取基本信息',function(ndata){
					if(ndata&&ndata.length==1){
						var itemMsg=ndata[0];
						dialog.find("#arrivalInfoId").text(itemMsg.arrivalInfoId);
						dialog.find("#shipName").text(util.isNull(itemMsg.shipName)).attr('data',itemMsg.shipId);
						dialog.find("#shipRefName").val(util.isNull(itemMsg.refName)).attr('data',util.isNull(itemMsg.refName));
						dialog.find("#shipAgentId").val(itemMsg.agentName).attr("data",itemMsg.shipAgentId);
						dialog.find("#shipArrivalDraught").val(itemMsg.shipArrivalDraught) ;
						dialog.find("#productId").text(itemMsg.productId);
						dialog.find("#berthId").val(itemMsg.berthName).attr("data",itemMsg.berthId) ;
						if(itemMsg.report=="已申报"){
							dialog.find("#report").attr("readonly",'readonly');
							dialog.find("#report").attr("disabled","disabled");
						}else{
							dialog.find("#report").removeAttr("readonly");
							dialog.find("#report").removeAttr("disabled");
						}
						dialog.find("#report").val((itemMsg.report=="已申报"?1:0));
						util.initTimeVal(dialog.find(".arrivalTime"),itemMsg.arrivalTime+":00") ;	
					}
				},true);
			}
		});
		
	};
	function handleIsTransport(dialog){
		//如果是转输
		if(isTransports==2){
			dialog.find(".notTransport").hide();
			dialog.find("#tp1").text("预计转输:");
			dialog.find("#tp2").text("生成调度日志");
			dialog.find("#tp3").text("否");
			dialog.find("#tp4").text("是");
		}
	};
/*******************************************************************************************************/
	function initView(status,id,isTransport){
		if(util.isNull(id,1)!=0){
		initOutboundTabMsg(id);
		initBaseGoodsInfo(id);
		}
		if(util.isNull(systemUserId,1)==0){
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
		if(isTransport==2){
			$("#tab2,.notTransport").hide();
		}
		$("#tab1,#tab2,#tab3,#tab4").unbind("click");
		//作业计划
		$("#tab1").click(function(){
			$(this).parent().addClass("active").siblings().removeClass("active") ;
			$("#tab").load(config.getResource()+"/pages/outbound/outbound/shipforecast/add.jsp",function(){preArrivalInfo(id,isTransport);});
		}) ;
		//靠泊方案
		$("#tab2").click(function(){
			$(this).parent().addClass("active").siblings().removeClass("active") ;
			$("#tab").load(config.getResource()+"/pages/outbound/outbound/berthplan/add.jsp",function(){preBerthProgram(id);});
		});
		//发货准备
		$("#tab3").click(function(){
			$(this).parent().addClass("active").siblings().removeClass("active") ;
			$("#tab").load(config.getResource()+"/pages/outbound/outbound/readydischarge/add.jsp",function(){preDeliverReady(id,isTransport);});
		});
		//数量确认
		$("#tab4").click(function(){
			$(this).parent().addClass("active").siblings().removeClass("active") ;
			$("#tab").load(config.getResource()+"/pages/outbound/outbound/confirmrelease/add.jsp",function(){preAmountAffirm(id,isTransport);});
		});
		if(status==50)
		{
	    	$("#tab1").click();
	    }
	    if(status==51)
	    {
	    	$("#tab2").click();
	    }
	    if(status==52)
	    {
	    	$("#tab3").click();
	    }
	    if(status==53||status==54)
	    {
		    $("#tab4").click();
		};
	};
	
	function initOutboundTabMsg(id){
		$.ajax({
			type:"post",
			url:config.getDomain()+"/outboundserial/getArrivalById",
			data:{"arrivalId":id},
			dataType:"json",
			async:false,
			success : function(data) {
				util.ajaxResult(data,"获取基本信息",function(ndata){
					if(ndata&&ndata.length==1){
						var itemdata=ndata[0];
						$("#base_id").text(itemdata.id);//arrivalid
						if(itemdata.type==5){
							$("#arrivalType").text(5);
							$("#tab3,#tab4").hide();
							$("#base_shipName").append(itemdata.shipName+'<label style="color:#00aa99">(通过)<label>') ;
						}else{
							$("#base_shipName").text(util.isNull(itemdata.shipName));
							if(itemdata.shipName=='转输'||itemdata.shipName=='ZHUANSHU')
								isTransports=2;	
						}
						$("#base_productNum").text(util.toDecimal3(itemdata.totalNum,true));
						$("#base_productName").text(util.isNull(itemdata.productName)).attr('data',util.isNull(itemdata.productId)) ;
						$("#base_arrivalTime").text(util.isNull(itemdata.startTime)) ;
						$("#base_shipLength").text(util.isNull(itemdata.shipLenth)) ;
						$("#base_shipWidth").text(util.isNull(itemdata.shipWidth)) ;
						$("#base_netTons").text(util.toDecimal3(itemdata.netTons,true)) ;
						$("#base_draught").text(util.toDecimal3(itemdata.shipArrivalDraught,true)) ;
						$("#base_shipLoad").text(util.toDecimal3(itemdata.loadCapacity,true)) ;
						$("#base_berth_id").attr('data',util.isNull(itemdata.berthId)).text(util.isNull(itemdata.berthName));
						$("#productId").text(util.isNull(itemdata.productId));
					}
					if(isTransports&&isTransports==2){
						$("#tp2").text("预计转输时间:");
						$("#tp1").text("转输类型:");
						$("#base_shipName").text("转输输出");
					}
					
				},true);
			}
		});
	}
	//初始化货体信息
	function initBaseGoodsInfo(id){
		var columns=[{title:"入库船信",name:"shipInfo"},{title:"货品",name:"productName"
					},{title:"货批号",name:"cargoCode"},{title:"货体号",name:"goodsCode"
					},{title:"提货单位",name:"ladingClientName"},{title:"提单号",name:"ladingCode"
					},{title:"数量(吨)",name:"goodsTotal"},{title:"流向",name:"flow"
					},{title:"是否核销",name:"isVerification"}];
		if($('[data-role="deliverGoodsInfo"]').getGrid() != null)
			$('[data-role="deliverGoodsInfo"]').getGrid().destory();
		
		$('[data-role="deliverGoodsInfo"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain()+"/outboundserial/getbasegoodsinfo?arrivalId="+id,
			callback:function(){
				var mdata=$('[data-role="deliverGoodsInfo"]').getGrid().getAllItems();
	        	var total=0;
	        	for(var i in mdata){
	        		if(mdata[i]&&mdata[i].goodsTotal){
	        			total=util.FloatAdd(total,mdata[i].goodsTotal);
	        		}
	        	}
	        	goodsPlanTotal=total;
                 var html="<div class='form-group totalGridDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计划量：</label>" +
                 		"<label class='control-label' style='margin-left: 10px;margin-right:20px;' id='totalGoodsNum'>"+util.toDecimal3(total,true)+"</label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>吨</label></div>";	
                 $(".totalGridDiv").remove();
	        	$('[data-role="deliverGoodsInfo"]').find(".grid-body").parent().append(html);
	        	$('[data-role="deliverGoodsInfo"]').find(".grid-body").attr('style','min-height:0px;');
	        	$(".totalGridDiv").closest("td").css('padding','0px');
			}
		});
	};
/*************************************************************************************************/
	//作业计划
	function preArrivalInfo(arrivalId,isTransport){
        initArrivalInfoControl($(".arrivalInfo"),arrivalId,isTransport);		
		initArrivalInfoMsg($(".arrivalInfo"),arrivalId);
		handleArrivalInfoIsTransport($(".arrivalInfo"),isTransport);
	}
	//靠泊方案
	function preBerthProgram(arrivalId){
		initBerthProgramControl($(".berthPlanDiv"),arrivalId);
		initBerthProgramMsg($(".berthPlanDiv"),arrivalId);
	}
	function preBerthAssess(arrivalId){
		$.ajax({
			type:'post',
			url:config.getDomain()+"/outboundserial/getBerthAssess",
			data:{arrivalId:arrivalId},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取靠泊评估',function(ndata){
					if(ndata&&ndata.length==1&&ndata[0].status){
						var itemMsg=ndata[0];
						if(itemMsg.status!=2){
							initBerthAssessDialog(itemMsg);
						}else{
							initCheckBerthAssessDialog(itemMsg);
						}
					}else{
						initBerthAssessDialog(undefined);
					}
				},true);
			}
		});
	}
	//发货准备
	function preDeliverReady(arrivalId,isTransport){
		initDeliverReadyControl($(".deliverReadyDiv"),arrivalId,isTransport);
		initDeliverReadyMsg($(".deliverReadyDiv"),arrivalId,isTransport);
	}
	//数量确认
	function preAmountAffirm(arrivalId){
		initAmountAffirmControl($(".confirmreleaseDiv"),arrivalId);
		initAmountAffirmMsg($(".confirmreleaseDiv"),arrivalId);
		handleAmountAffirmIsTransport($(".confirmreleaseDiv"));
	}

/*******************************************************************************************************/
	function initArrivalInfoControl(obj,arrivalId,isTransport){
		util.initTimePicker(obj);
		obj.find("#save,#submit").click(function(){
          var status=$(this).attr("key");//保存，提交
				$.ajax({
					type : "post",
					url : config.getDomain()+"/outboundserial/addorupdatearrivalinfo",
					data : {
						"id":obj.find("#arrivalInfoId").text(),
						"cjTime":util.formatLong(util.getTimeVal(obj.find(".cjTime"))),
						"tcTime":util.formatLong(util.getTimeVal(obj.find(".tcTime"))),
						"norTime":util.formatLong(util.getTimeVal(obj.find(".norTimeNOR"))),
						"portNum":obj.find("#portNum").val(),
						"report":obj.find("#report").val(),
						"shipInfo":obj.find("#shipInfo").val(),
						"note":obj.find("#note").val(),
						"overTime":obj.find("#overTime").val(),
						"repatriateTime":obj.find("#repatriateTime").val(),
						"lastLeaveTime":util.formatLong($("#lastLeaveTime").val()),
						"arrivalId":arrivalId,
						"status":status,
						"isTransport":isTransport
				},
					dataType : "json",
					success : function(data) {
						util.ajaxResult(data,status==0?'保存':'提交',function(ndata,nmap){
							if(util.isNull(obj.find("#arrivalInfoId").text(),1)==0){
								obj.find("#arrivalInfoId").text(nmap.id);
							}
							initArrivalInfoMsg(obj,arrivalId);	
						});
					}
				});
		});
	};		
	function initArrivalInfoMsg(obj,arrivalId){
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain()+"/outboundserial/getArrivalInfoById",
			data : {"arrivalId":arrivalId},
			dataType : "json",
			success : function(data) {
				util.ajaxResult(data,'获取作业计划信息',function(ndata){
					if(ndata&&ndata.length==1){
						var itemMsg=ndata[0];
					obj.find("#arrivalInfoId").text(itemMsg.id);//arrivalInfoId	
					//初始化时间	
					util.initTimeVal(obj.find(".cjTime"),itemMsg.cjTime);
					util.initTimeVal(obj.find(".tcTime"),itemMsg.tcTime) ;
					util.initTimeVal(obj.find(".norTimeNOR"),itemMsg.norTime) ;
					obj.find("#tubeInfo").val(util.isNull(itemMsg.tubeInfo));//管线安排
					obj.find("#tankInfo").val(util.isNull(itemMsg.tankInfo));//罐安排	
					obj.find("#portNum").val(util.isNull(itemMsg.portNum));//年度航次
					obj.find("#report").val(util.isNull(itemMsg.report));//申报状态
					obj.find("#shipInfo").val(util.isNull(itemMsg.shipInfo));//船舶信息表
					obj.find("#note").val(util.isNull(itemMsg.note));//备注
					obj.find("#overTime").val(util.isNull(itemMsg.overTime));//超期时间
					obj.find("#repatriateTime").val(util.isNull(itemMsg.repatriateTime));//速遣时间
					obj.find("#lastLeaveTime").val(util.isNull(itemMsg.lastLeaveTime));//最大离港时间
					 if(itemMsg.status>50){
						 obj.find("#submit").hide();
					 }else{
						 obj.find("#submit").show();
					 }
					}
				},true);
			}
		});
	};
	//同步计算最大在港时间
	function countLastLeaveTime() {
		if (util.getTimeVal($(".norTimeNOR")) != "") {
			var time = util.getTimeVal($(".norTimeNOR"));
			if(time.length==10){
				time=time+" 00:00:00";
			}
			var time2 = time.replace(/-/g, "/");
			if(time2.length==10){
				time2=time2+" 00:00:00";
			}else{
				$("#lastLeaveTime").val("");
			}
			var d = new Date(time2);
			d.setTime(util.FloatAdd(d.getTime(),(6 + goodsPlanTotal / 150) * 3600000));
			var t = new Date(d.getTime());
			if((d.getTime()+"").length!=13){
				$("body").message({
					type:'warning',
					content:"货品总量过大，无法计算最大在港时间"
				});
				$("#lastLeaveTime").val("");
			}else{
			$("#lastLeaveTime").val(t.Format("yyyy-MM-dd HH:mm:ss"));
			}
		}else{
			$("#lastLeaveTime").val("");
		}
	};
	function handleArrivalInfoIsTransport(obj,isTransport){
		if(isTransport&&isTransport==2)
		obj.find(".notTransport").hide();
	};
/*******************************************************************************************************/
	function initBerthProgramControl(obj,arrivalId){
		util.urlHandleTypeahead("/auth/user/getUserByPermission?permission="+obj.find("#reviewCodeUserId").attr("code"),obj.find("#reviewCodeUserId"));
		obj.find("#berthAssess").click(function(){
			preBerthAssess(arrivalId);
		});
		obj.find("#checkBerth").click(function(){
			if(util.validateData(obj.find('[data-role="berthGrid"]'))){
				var data=obj.find('[data-role="berthGrid"]').getGrid().selectedRowsIndex();
				initGrid(data[0]);
			}});
		obj.find("#reset").click(function(){
			$("#checkBerth").show();
			initGrid(-1);
		});
		obj.find("#back").click(function(){
			var data = $(obj).find('[data-role="berthGrid"]').getGrid().getAllItems()[0];//泊位信息
			var berthProgram={
					"id":obj.find("#berthProgramId").text(),	
					"berthId":data.id,
					"arrivalId":arrivalId,
					"comment":'',
					"status":0
			}
			$.ajax({
				type : "post",
	  			url : config.getDomain()+"/outboundserial/addOrUpdateBerthProgram",
	  			dataType : "json",
	  			data:berthProgram,
	  			success : function(data) {
	  				util.ajaxResult(data,"回退",function(){
	  					initBerthProgramMsg(obj,arrivalId);	
	  				},true);
	  			}});
		});
		
		
		
		obj.find(".save").click(function(){
			$this=$(this);
			var status=$(this).attr("key");
			var data = $(obj).find('[data-role="berthGrid"]').getGrid().getAllItems()[0];//泊位信息
			var securityCode=$this.attr("code");
			var isOk=true;
			var isShowCheck=true;
			var warningstr="";
			var berthProgram={
					"id":obj.find("#berthProgramId").text(),
	  				"berthId":data.id,
	  				"safeInfo":obj.find("#safeInfo").val(),
	  				"richDraught":obj.find("#richDraught").val(),
	  				"windPower":obj.find("#windPower").val(),
	  				"arrivalId":arrivalId,
	  				"status":-1	
			};
			if(util.isNull(obj.find("#safeInfo").val())==''){
				$('body').message({
					type:'warning',
					content:'请填写安全措施！'
				});
				isOk=false;
				isShowCheck=false;
			}
			//提交是校验是否符合限制条件
			if(status==1){
				if(obj.find('#checkBerth').css('display')!='none'){
					$('body').message({
						type:'warning',
						content:'请选择泊位！'
					});
					isOk=false;
			}else{
			if(parseFloat(data.limitLength)<parseFloat(util.isNull($("#base_shipLength").text(),1))){
				isOk=false;
				warningstr+='泊位船长不足，';
			}
			if(parseFloat(data.limitDrought)<parseFloat(util.isNull($("#base_draught").text(),1))){
				isOk=false;
				warningstr+='泊位吃水不足，';
			}
			if(data.id!=1){
			if(parseFloat(data.limitDisplacement)<parseFloat(util.isNull($("#base_shipLoad").text(),1))){
				isOk=false;
				warningstr+='泊位最大载重吨不足，';
			}}
			if(!isOk&&isShowCheck){
				berthProgram.createUserId=systemUserId;
				berthProgram.createTime=util.formatLong(util.currentTime(0));
				berthProgram.status=-1;
				$(obj).confirm({
					content:warningstr+'是否申请靠泊评估?',
					callBack:function(){
						if(!config.validateForm(obj.find(".dockFeeDiv"))){
						$.ajax({
							type : "post",
				  			url : config.getDomain()+"/outboundserial/addOrUpdateBerthProgram",
				  			dataType : "json",
				  			data:berthProgram,
				  			success : function(data) {
				  				util.ajaxResult(data,'保存',function(){
				  					initOutboundTabMsg(arrivalId);
				  					preBerthAssess(arrivalId);	
				  				},true);
				  			}});
						}
					}
				});
			}
			}
			}
			if(isOk){
				berthProgram.status=status;
			var resultContent;
			if(status<2){
				berthProgram.createUserId=systemUserId;
				berthProgram.createTime=util.formatLong(util.currentTime(0));
				resultContent=(status==0?'保存':'提交');
			}else{
				if(status==2&&(util.isNull(obj.find("#comment").val())==''||util.isNull(obj.find("#comment").val())=='不通过')){
					obj.find("#comment").val('通过');
				}else if(status==3&&(util.isNull(obj.find("#comment").val())==''||util.isNull(obj.find("#comment").val())=='通过')){
					obj.find("#comment").val('不通过');
				}
				berthProgram.comment=obj.find("#comment").val();
				berthProgram.reviewUserId=systemUserId;
				berthProgram.reviewTime=util.formatLong(util.currentTime(0));
				resultContent=(status==2?'通过':'驳回');
				//验证码审核
	        	   if(securityCode&&securityCode.length>1){
         			  berthProgram.securityCode=util.isNull(obj.find("#securityCode").val());
         			 berthProgram.isSecurity=1;
         			berthProgram.object=securityCode;   
         	   }else{
         		  berthProgram.isSecurity=0;
         	   }
			}	
					$.ajax({
						type : "post",
			  			url : config.getDomain()+"/outboundserial/addOrUpdateBerthProgram",
			  			dataType : "json",
			  			data:berthProgram,
			  			success : function(data) {
			  				util.ajaxResult(data,resultContent,function(){
			  					initBerthProgramMsg(obj,arrivalId);	
			  				},true);
			  			}});	
				
			}
		});
	};
	function initBerthProgramMsg(obj,arrivalId){
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain()+"/outboundserial/getBerthProgramByArrivalId",
			data : {"arrivalId":arrivalId},
			dataType : "json",
			success : function(data) {
				util.ajaxResult(data,'获取泊位信息',function(ndata){
					if(ndata&&ndata.length==1){
						var itemMsg=ndata[0];
						obj.find("#berthProgramId").text(itemMsg.id);
						obj.find("#richDraught").val(util.toDecimal2(itemMsg.richDraught));//富裕水深
						obj.find("#windPower").val(util.isNull(itemMsg.windPower));
						obj.find("#comment").val(util.isNull(itemMsg.comment)) ;
						util.isNull(itemMsg.berthId,1)!=0?initGrid(itemMsg.berthId,true):initGrid(-1);
						obj.find("#safeInfo").val(util.isNull(itemMsg.safeInfo));	
						obj.find("#createUserId").attr("data",itemMsg.createUserId).val(itemMsg.createUserName);
						obj.find("#createTime").val(itemMsg.createTime);
						obj.find("#reviewUserId").attr("data",itemMsg.reviewUserId).val(itemMsg.reviewUserName);
						obj.find("#reviewTime").val(itemMsg.reviewTime);
						if(itemMsg.bpstatus==0){
							obj.find("button[key='0'],button[key='1']").show();
							obj.find("button[key='2'],button[key='3'],.createDiv,.reviewDiv,.commentDiv,.securityCodeDiv,#back").hide();
						}else if(itemMsg.bpstatus==1){
							obj.find("button[key='0'],button[key='1'],.reviewDiv,.securityCodeDiv,#back").hide();
							obj.find("button[key='2'],button[key='3'],.createDiv,.commentDiv,.securityCodeDiv").show();
						}else if(itemMsg.bpstatus==2){
							obj.find(".createDiv,.reviewDiv,.commentDiv,#back").show();
							obj.find("button[key='0'],button[key='1'],button[key='2'],button[key='3'],.securityCodeDiv").hide();
						}else if(itemMsg.bpstatus==3){
							obj.find("button[key='2'],button[key='3'],.createDiv,.securityCodeDiv,#back").hide();
							obj.find("button[key='0'],button[key='1'],.reviewDiv,.commentDiv").show();
						}else{
							obj.find("button[key='0'],button[key='1']").show();
							obj.find("button[key='2'],button[key='3'],.createDiv,.reviewDiv,.commentDiv,.securityCodeDiv,#back").hide();	
						}
						//验证码内容
						obj.find("#securityCodeContent").text("船名："+$("#base_shipName").text()+"；到港日期："+$("#base_arrivalTime").text()+"；"+
						"货品： "+$("#base_productName").text()+" 数量："+$("#totalGoodsNum").text()+"吨；计划泊位："+$("#base_berth_id").text()+"；富裕水深："+
						util.toDecimal2(itemMsg.richDraught)+"米。");	
					}else{
						obj.find("button[key='0'],button[key='1']").show();
						obj.find("button[key='2'],button[key='3'],.createDiv,.reviewDiv,.commentDiv,.securityCodeDiv,#back").hide();	
					}
					obj.find(".dialog-warning5").attr("hidden",true).removeAttr("style");
				},true);
			}
		});
	};
	function initGrid(id,isSave){
			var columns=[{title:"泊位",render:function(item){
						if(id!=-1){
							$("#description").val(item.description);
							if(!isSave)
							$("#safeInfo").val(item.safeInfo);
						}
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
			if($('[data-role="berthGrid"]').getGrid()!=null){
				$('[data-role="berthGrid"]').getGrid().destory();
			}
			var murl=config.getDomain() + "/berth/list"
			var isshow=true;
			if(id!=-1){
				murl+="?id="+id;
				isshow=false;
				columns.pop();
				 $("#checkBerth").hide();
			}
			$('[data-role="berthGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : isshow,
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
		};
/*******************************************************************************************************/
	function initBerthAssessDialog(itemMsg){
		$.get(config.getResource()+ "/pages/outbound/outbound/berthassess/add.jsp").done(function(data) {
			var dialog=$(data);
			initBerthAssessDialogControl(dialog);
			initBerthAssessDialogMsg(dialog,itemMsg);
			dialog.modal({
				keyboard : true
			});
			});
		};
		
		function initBerthAssessDialogControl(dialog){
			initFormIput();
			util.initTimePicker(dialog);
			util.urlHandleTypeahead("/berth/list",dialog.find("#berthId"));
			util.urlHandleTypeahead("/auth/user/getUserByPermission?permission="+dialog.find("#reviewCodeUserId").attr("code"),dialog.find("#reviewCodeUserId"));
			dialog.find("#submit,#save").click(function(){
				$this=$(this);
	           var status=util.isNull($this.attr("key"),1);	
	           var nData;
	           var resultContent;
	           
	           if(status<2){
	        	   nData={'id':util.isNull(dialog.find("#berthAssessId").text(),1),
						'arrivalId':util.isNull(dialog.find("#arrivalId").text(),1),
						'createUserId':systemUserId,
						'createTime':util.formatLong(util.currentTime(0)),
						'weather':dialog.find("#weather").val(),
						'windDirection':dialog.find("#windDirection").val(),
						'windPower':dialog.find("#windPower").val(),
						'reason':dialog.find("#reason").val(),
						'security':dialog.find("#security").val(),
						'reviewStatus':status
	        	   }
	        	   resultContent=(status==0?'保存':'提交');
	           }else{
	        	   if(status==2&&(util.isNull(dialog.find("#comment").val())==''||util.isNull(dialog.find("#comment").val())=='不通过')){
	        		   dialog.find("#comment").val('通过');
					}else if(status==3&&(util.isNull(dialog.find("#comment").val())==''||util.isNull(dialog.find("#comment").val())=='通过')){
						dialog.find("#comment").val('不通过');
					}
	        	   nData={
	        			   'id':util.isNull(dialog.find("#berthAssessId").text(),1),
							'arrivalId':util.isNull(dialog.find("#arrivalId").text(),1),
							'comment':util.isNull(dialog.find("#comment").val()),
							'reviewUserId':systemUserId,
						   'reviewTime':util.formatLong(util.currentTime(0)),
						   'reviewStatus':status// 保存
	        	   }
	        	   resultContent=(status==2?'通过':'驳回');
	        	   //验证码审核
	        	   if($this.attr('code')&&$this.attr('code').length>1){
            			   nData.securityCode=util.isNull(dialog.find("#securityCode").val());
                		   nData.isSecurity=1;
                		   nData.object=$this.attr('code');   
            	   }else{
            		   nData.isSecurity=0;
            	   }
	           }
	         config.load();
				$.ajax({
					type:"post",
					url:config.getDomain()+"/outboundserial/addOrUpdateBerthAssess",
					data:nData,
					dataType:"json",
					success:function(data){
						util.ajaxResult(data,resultContent,function(){
							dialog.remove();
							preBerthAssess(util.isNull(dialog.find("#arrivalId").text(),1));
						});
				}});
		});}
		function initBerthAssessDialogMsg(dialog,itemMsg){
			dialog.find("#berthId").attr("data",$("#base_berth_id").attr('data')).val($("#base_berth_id").text());
			dialog.find("#arrivalId").text($("#base_id").text());	
			if(!itemMsg){
				dialog.find(".commentDiv,.createDiv,.reviewDiv,.secDiv").hide();
				dialog.find(".firDiv").show();
			}else{
				dialog.find("#berthAssessId").text(itemMsg.id); 
				dialog.find("#arrivalId").text(itemMsg.arrivalId);	
				dialog.find("#weather").val(util.isNull(itemMsg.weather));
				dialog.find("#windDirection").val(util.isNull(itemMsg.windDirection));
				dialog.find("#windPower").val(util.isNull(itemMsg.windPower));
				dialog.find("#reason").val(util.isNull(itemMsg.reason));
				dialog.find("#comment").val(util.isNull(itemMsg.comment));	
				dialog.find("#security").val(util.isNull(itemMsg.security));
		if(itemMsg.status==3||itemMsg.status==1){
			dialog.find("#createUserId").val(util.isNull(itemMsg.createUserName)).attr("data",itemMsg.createUserId);
			dialog.find("#reviewUserId").val(util.isNull(itemMsg.reviewUserName)).attr("data",itemMsg.reviewUserId);
		}
		if(itemMsg.createTime)
			dialog.find("#createTime").val(itemMsg.createTime.split(" ")[0]);
		if(itemMsg.reviewTime)
			dialog.find("#reviewTime").val(itemMsg.reviewTime.split(" ")[0]);
		
		 if(itemMsg.status==1){//审核人信息初始化(获取登陆信息)
			 dialog.find(".commentDiv,.createDiv,.secDiv,.securityCodeDiv").show();
			 dialog.find(".reviewDiv,.firDiv").hide();
		}else if(itemMsg.status==3){//驳回
			dialog.find(".secDiv,.securityCodeDiv").hide();
			dialog.find(".commentDiv,.reviewDiv,.createDiv,.firDiv").show();
		}else{//指定人信息初始化(获取登陆人信息)
			dialog.find(".commentDiv,.createDiv,.reviewDiv,.secDiv,.securityCodeDiv").hide();
			dialog.find(".firDiv").show();
			}
		 dialog.find(".dialog-warning4").attr("hidden",true).removeAttr("style");
			//验证码内容
			dialog.find("#securityCodeContent").text("船名："+$("#base_shipName").text()+"；到港日期："+$("#base_arrivalTime").text()+"方案如下：计划发货："+
			"货品："+$("#base_productName").text()+"，数量："+$("#totalGoodsNum").text()+"吨；计划泊位："+$("#base_berth_id").text()+"；申请原因："+util.isNull(itemMsg.reason)+"。");		
			}
		};
/**********************************************************************************************/		
		
	function initCheckBerthAssessDialog(itemMsg){
		$.get(config.getResource()+ "/pages/outbound/outbound/berthassess/get.jsp")
		.done(function(data){
			var dialog = $(data);
			dialog.find(".back").hide();
			dialog.find("#berthId").text(itemMsg.berthName);
			dialog.find("#weather").text(util.isNull(itemMsg.weather));
			dialog.find("#windDirection").text(util.isNull(itemMsg.windDirection));
			dialog.find("#windPower").text(util.isNull(itemMsg.windPower));
			dialog.find("#reason").val(util.isNull(itemMsg.reason));
			dialog.find("#comment").val(util.isNull(itemMsg.comment));
			dialog.find("#security").val(util.isNull(itemMsg.security));
			dialog.find("#createUserId").text(util.isNull(itemMsg.createUserName));
			dialog.find("#reviewUserId").text(util.isNull(itemMsg.reviewUserName));
			dialog.find("#createTime").text(itemMsg.createTime.split(" ")[0]);
			dialog.find("#reviewTime").text(itemMsg.reviewTime.split(" ")[0]);
			dialog.modal({
				keyboard : true
			});
		});
	};	
/*******************************************************************************************************/
	function initDeliverReadyControl(obj,arrivalId,isTransport){
		//commitReadyDischarge
		obj.find("#save,#submit").click(function(){
			var status=$(this).attr('key');
			var transportProgram ={
					"id":util.isNull(obj.find("#transportId").text(),1)==0?undefined:util.isNull(obj.find("#transportId").text(),1),
					"type":2,
					"flow":getImgXml(),
					"svg":getSVG(),
					"arrivalId":arrivalId,
					"tubeInfo":$("#tubeIds").val(),
					"tankInfo":$("#tankIds").val(),
					"pumpInfo":$("#pupmIds").val(),
					"comment":$("#comment").val(),
					"status":status
		} ;
		if(!validateSVG()){
			$('body').message({
				type:'warning',
				content:'工艺流程未填写完整'
			});
			return;
		}
		$.ajax({
			type:'post',
			url : config.getDomain()+"/outboundserial/addOrUpdateTransportProgram",
			dataType : "json",
			data:transportProgram,
			async:false,
			success : function(data) {
				util.ajaxResult(data,status==0?'保存':'提交',function(){
					initDeliverReadyMsg(obj,arrivalId,isTransport);
				});
			}
		});
		});
		
		
		
	};
	function initDeliverReadyMsg(obj,arrivalId,isTransport){
		obj.find("#berthName").val($("#base_berth_id").text()).attr("data",$("#base_berth_id").attr('data'));
		if(isTransport&&isTransport==2){
			$("#dockshipNotice,#dynamicshipNotice").hide();
			$("#zhuanshuNotice").show();
		}else{
			$("#dockshipNotice,#dynamicshipNotice").show();
			$("#zhuanshuNotice").hide();
		}
       config.load();
		$.ajax({
			type:'post',
			url : config.getDomain()+"/outboundserial/getDeliverReadyMsg",
			dataType : "json",
			data:{"arrivalId":arrivalId},
			async:false,
			success : function(data) {
				util.ajaxResult(data,'获取发货准备信息',function(ndata){
					if(ndata&&ndata.length==1&&ndata[0].isInvoice){
					var itemMsg=ndata[0];
					if(util.isNull(itemMsg.isInvoice,1)==0){
						$("#contentDiv,.firDiv").hide();
						$("body").message({
							type:'warning',
							content:'请前往开票'
						});
					}else if(ndata[0].id){
						obj.find("#contentDiv,.firDiv").show();
						obj.find("#dockWork").append(itemMsg.dockWork) ;
						obj.find("#tankIds").val(itemMsg.tankInfo) ;
						obj.find("#tubeIds").val(itemMsg.tubeInfo) ;
						obj.find("#pupmIds").val(itemMsg.pumpInfo) ;
						obj.find("#openPumpTime").text(util.isNull(itemMsg.openPumpTime));
						obj.find("#stopPumpTime").text(util.isNull(itemMsg.stopPumpTime));
						obj.find("#transportId").text(itemMsg.id) ;
						obj.find("#comment").val(util.isNull(itemMsg.comment)) ;
						obj.find("#checkUser").val(itemMsg.checkUser) ;
						obj.find("#checkTime").val(itemMsg.checkTime) ;
						if(itemMsg.noticeCodeA&&itemMsg.noticeCodeA.indexOf('L')!=-1){
							obj.find("#zhuanshuNotice").attr("data",itemMsg.noticeCodeA);
						}else{
							obj.find("#dockshipNotice").attr("data",itemMsg.noticeCodeA);
						}
						obj.find("#dynamicshipNotice").attr('data',itemMsg.noticeCodeB);
//						flow(xml,hasTool,enabled,obj,hasPark,hasPupm,pumpType,productId,productName,hasBerth)
						if(itemMsg.flow!=null&&itemMsg.flow!=""){
							$(obj).find("#graphContainer").empty();
							graphCleanCache();
							flow(itemMsg.flow,null,true,undefined,false,true,"",$("#base_productName").attr('data'),$("#base_productName").text());
							initCache(","+itemMsg.tankIds,","+itemMsg.tubeIds);
						}
					}else{
						graphCleanCache();
						flow(undefined,null,true,undefined,false,true,"",$("#base_productName").attr('data'),$("#base_productName").text());
					}
					}else{
						$("body").message({
							type:'error',
							content:'系统错误，请联系管理员'
						});
					}
				},true);
			}
		});
	
	};
/********************************************************************************************************/
	//码头船发作业通知单
	function dialogDockOutboundNotify(obj){
		var id=util.isNull($("#transportId").text(),1);
		if(id!=0){
		var arrivalId=$("#base_id").text();
		 notify.init(4,$("#dockshipNotice").attr('data'),undefined,undefined,arrivalId,true);
		}else{
			$('body').message({type:'warning',content:'请保存后再点击！'});
		}
	};
	//操作班船发作业通知单
	function dialogOperationOutboundNotify(obj){
		var id=util.isNull($("#transportId").text(),1);
		if(id!=0){
		var arrivalId=$("#base_id").text();
		notify.init(5,$("#dynamicshipNotice").attr('data'),undefined,undefined,arrivalId,true);
		}else{
			$('body').message({type:'warning',content:'请保存后再点击！'});
		}
	};
	function dialogZhuanshuNotify(obj){
		var id=util.isNull($("#transportId").text(),1);
		if(id!=0){
		var arrivalId=$("#base_id").text();
		var taskmsg="转输：【"+$("#base_productNum").text()+"】【"+$("#totalFee").text()+"】(吨)";
		notify.init(7,$(obj).attr('data'),id,taskmsg,null,true);
		}else{
			$('body').message({type:'warning',content:'请保存后再点击！'});
		}
	}
/*******************************************************************************************************/
	function initAmountAffirmControl(obj,arrivalId){
		util.initTimePicker(obj.find(".outTimeDiv"),true);
		util.urlHandleTypeahead("/auth/user/getUserByPermission?permission="+obj.find("#reviewCodeUserId").attr("code"),obj.find("#reviewCodeUserId"));
		obj.find(".save").click(function(){
			$this=$(this);
			var status=util.isNull($this.attr("key"),1);
			var securityCode=$this.attr("code");
			var reStr="";
			var nData={};
			if(status==0){
				reStr="保存";
			}else if(status==1){
				reStr="数量确认";
			}else if(status==2){
				//提交审核不能为同一个人
				if(systemUserId==util.isNull(obj.find("#createUserId").attr('data'),1)){
					$('body').message({
				    	type:'warning',
				    	content:'提交人和审核人不能为同一个人'
				    });
					return ;
				}
				reStr="复核通过";
			}else if(status==3){
				reStr="复核不通过";
			}else if(status==4){
				status=3;
				reStr="回退修改";
			}
			
			var goodsLogArray = new Array() ;
			//如果outTime修改
			var outTime=util.formatLong(util.getTimeVal(obj.find("#outTime"),true))==obj.find("#outTime").attr("data")?undefined:util.formatLong(util.getTimeVal(obj.find("#outTime"),true));
			var choiceTime=outTime>obj.find("#outTime").attr("data")?obj.find("#outTime").attr("data"):outTime;
			//校验是否称重
			if(util.isNull(obj.find("#weighStatus").text(),1)!=2){
			    $('body').message({
			    	type:'warning',
			    	content:'请前往船发称重进行称重'
			    });
				return false;
			}
			var reActualNum=0,nowActual=0,goodsCurrentNum=0;
			var isPass=true,isChange=false;
			$("#statisticsList>tr").each(function(){
				//验证------------------------------
				$itemTr=$(this) ;
				 reActualNum=$itemTr.find(".actualNum").attr("data");//修改前实发量
				 nowActual=$itemTr.find(".actualNum").val();//修改后实发量
				 goodsCurrentNum=$itemTr.find(".goodsNum").text();//货体当前结存量
					 if(parseFloat(util.FloatSub(util.FloatAdd(reActualNum,goodsCurrentNum,3),nowActual,3))<0){
					 $('body').message({
					    	type:'warning',
					    	content:'实发数修改不能超过结存量'
					    });
					 	isPass=false;
						return false;
				 }
				 if(isPass){
				//如果修改了出库时间
				if(outTime){
					outTime=outTime+3;
					isChange=true;
				}
				//校验是否修改了实发量
				if(parseFloat(util.FloatSub($itemTr.find(".actualNum").val(),$itemTr.find(".actualNum").attr("data"),3))!=0){
					isChange=true;
					var goodslog ={
							"id":$itemTr.find(".goodslogId").text(),
							"actualNum":$itemTr.find(".actualNum").val(),//现在实发量
							"createTime":outTime,
							"goodsChange":util.FloatMul($itemTr.find(".actualNum").val(),-1,3),
							"deliverNum":$itemTr.find(".deliverNum").text(),
							"serial":$itemTr.find(".serial").text(),
							"afUpNum":$itemTr.find(".actualNum").val(),//调整后的量
							"tempDeliverNum":util.isNum($itemTr.find(".actualNum").attr('data')),//之前实发量
							"goodsId":$itemTr.find(".goodsId").text(),
							"actualType":1
					};
					goodsLogArray.push(goodslog);
				}}
			});
			 if(isPass){
			var approve={
					"id":util.isNull(obj.find("#approveId").text(),1)==0?undefined:util.isNull(obj.find("#approveId").text(),1),
					"status":status,
					"comment":$("#comment").val(),
					"refId":arrivalId
			};
			//验证码审核
			if(status==2||status==3){
				//验证码审核
				
				if(securityCode&&securityCode.length>1){
						   nData.securityCode=util.isNull(obj.find("#securityCode").val());
						   nData.isSecurity=1;
						   nData.object=securityCode;   
				}else{
					   nData.isSecurity=0;
				}
			}
			nData.ouboundData=JSON.stringify({
				"goodsLoglist":goodsLogArray,
				"arrivalId":arrivalId,
				"status":obj.find("#arrivalStatus").text(),
				"approve":approve,
				"choiceTime":choiceTime,
				"isChange":isChange
		});
			$.ajax({
				type : "post",
				url : config.getDomain()+"/outboundserial/confirmData",
				data : nData,
				dataType : "json",
				async:false,
				success : function(data) {
					util.ajaxResult(data,reStr,function(){
						initAmountAffirmMsg(obj,arrivalId);
						});
					}
			});
			}
		});
		
	};
	function initAmountAffirmMsg(obj,arrivalId){
		config.load();
		$.ajax({
			type:"post",
			url:config.getDomain()+"/outboundserial/getAmountAffirmMsg",
			data:{ "arrivalId":arrivalId},
			dataType:"json",
			success:function(data) {
				util.ajaxResult(data,"初始化出库数据",function(ndata){
					if(ndata){
						var dataList=ndata[0];
						var appoveList=ndata[1];
						var html="",totalHtml="";
						var totalDeliverNum=0,totalActualNum=0;
						var itemMsg;
						if(dataList&&dataList.length>0){
						//初始化出库时间
						util.initTimeVal(obj.find("#outTime"),dataList[0].createTimeStr,true);
						obj.find("#outTime").attr("data",dataList[0].createTimeLong);
						//是否已经称重 
						if(dataList[0].weighStatus&&util.isNull(dataList[0].weighStatus,1)==2){
						obj.find("#weighStatus").text(2);
						}
						//流程状态
						obj.find("#arrivalStatus").text(dataList[0].status);
						$(".timepicker-24:focus").blur();
						for(var i=0,len=dataList.length;i<len;i++){
							itemMsg=dataList[i];
							totalDeliverNum=util.FloatAdd(totalDeliverNum,itemMsg.deliverNum,3);
							totalActualNum=util.FloatAdd(totalActualNum,itemMsg.actualNum,3);
							html+="<tr  class='outBoundDataTr'>"+
							"<td class='serial'>"+itemMsg.serial+"</td>" +
							"<td><label class='createTime hidden'>"+itemMsg.createTimeLong+"</label>"+
							"<label class='hidden goodslogId'>"+itemMsg.id+"</label>"+
							"<label class='hidden goodsId' >"+itemMsg.goodsId+"</label>"+itemMsg.ladingClientName+"</td>" +
							"<td>"+itemMsg.ladingEvidence+"</td>" +
							"<td>"+itemMsg.productName+"</td>" +
							"<td class='goodsNum'>"+util.FloatAdd(itemMsg.goodsCurrent,0,3)+"</td>" +
							"<td class='waitAmount'>"+(util.isNull(itemMsg.actualNum,1)==0?util.FloatAdd(itemMsg.deliverNum,0,3):0)+"</td>"+
							"<td>"+util.isNull(itemMsg.ladingCode)+"</td>" +
							"<td>"+itemMsg.goodsCode+"</td>" +
							"<td class='deliverNum'>"+util.FloatAdd(itemMsg.deliverNum,0,3)+"</td>" +
							"<td><input type='text' style='width:100px;' class='form-control actualNum' onkeyup='config.clearNoNum(this)' data="+util.FloatAdd(itemMsg.actualNum,0,3)+" value="+util.FloatAdd(itemMsg.actualNum,0,3)+" /></td>" +
							"<td>"+(util.isNull(itemMsg.tempDeliverNum,1)==0?util.toDecimal3(itemMsg.actualNum,true):util.toDecimal3(itemMsg.tempDeliverNum,true))+"</td>" +
							"</tr>" ;
						}	
						totalHtml+="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总开票量：</label>" +
						"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(totalDeliverNum,true)+"</label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>吨</label>"+
						(util.isNull(totalActualNum,1)==0?"":("<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总实发量：</label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(totalActualNum,true)+"</label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>吨</label>"));	
						obj.find("#statisticsList").empty().append(html) ;
						obj.find(".totalOutboundDiv").empty().append(totalHtml);
						//验证码内容
						obj.find("#securityCodeContent").text("船名："+$("#base_shipName").text()+"；到港日期："+$("#base_arrivalTime").text()+"；内容日下：发货"+
						"货品："+$("#base_productName").text()+"，总开票量 "+util.toDecimal3(totalDeliverNum,true)+"吨，总实发量："+util.toDecimal3(totalActualNum,true)+"吨。");	
						}
						if(appoveList&&appoveList.length==1)
						{
							itemMsg=appoveList[0];
							obj.find(".actualNum").removeAttr("disabled");
							if((itemMsg.status==0||itemMsg.status==3)&&dataList[0].status==53){//未提交和已回退
								obj.find("#save,#submit").show();
								obj.find("#pass,#refuse,#modify,.createDiv,.reviewDiv,.securityCodeDiv").hide();
							}else if(itemMsg.status==1&&dataList[0].status==53){//提交审核
								obj.find("#save,#submit,#modify,.reviewDiv").hide();
								obj.find("#pass,#refuse,.createDiv,.securityCodeDiv").show();
							}else if(itemMsg.status==2&&dataList[0].status==54){//审核通过
								obj.find(".actualNum").attr("disabled","disabled");
								obj.find("#save,#pass,#refuse,#submit,.securityCodeDiv").hide();
								obj.find("#modify,.createDiv,.reviewDiv").show();//修改权限，发回到未提交状态
							}else{
								obj.find("#save,#submit").show();
								obj.find("#pass,#refuse,#modify,.createDiv,.reviewDiv,.securityCodeDiv").hide();
							}
							obj.find("#approveId").text(itemMsg.id);
							obj.find("#createUserId").text(itemMsg.createUserName).attr("data",itemMsg.checkUserId);
							obj.find("#createTime").text(itemMsg.createTimeStr);
							obj.find("#reviewUserId").text(itemMsg.checkUser);
							obj.find("#reviewTime").text(itemMsg.checkTime);
						}else{
							if(dataList[0].status==54){
							obj.find("#save,#pass,#refuse,#submit,.securityCodeDiv").hide();
							obj.find("#modify,.createDiv,.reviewDiv").show();//修改权限，发回到未提交状态
							}else{
								obj.find("#save,#submit").show();
								obj.find("#modify,#pass,#refuse,.createDiv,.reviewDiv,.securityCodeDiv").hide(); 
							}
						}
						obj.find(".dialog-warning5").attr("hidden",true).removeAttr("style");
					}
				},true);
			}
		});
	};
	function handleAmountAffirmIsTransport(obj){
		if(isTransports&&isTransports==2)
		obj.find("#tp1").text("转输时间：");
	};
/*******************************************************************************************************/
	//作业文件管理
	function fileManage(id,type){
		$.get(config.getResource()+"/pages/outbound/outbound/fileAdd.jsp").done(function(data){
			var dialog = $(data);
			initFileManageCtr(dialog);
			initFileManageMsg(dialog,id,type);
		});
	};
	
	function initFileManageCtr(dialog){
		dialog.modal({keyboard: true});
		dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
		
		dialog.find("#upBtn").click(function(){
			dialog.find('#lefile').click();
		});
		
		dialog.find('#lefile').change(function() {  
			dialog.find('#photoCover').val($(this).val());
			});  
		
		dialog.find(".buttonUp-outbound").click(function(){
				dialog.find('#workFileForm').ajaxSubmit({  
					success:function(data){
						util.ajaxResult(data,"上传",function(){
							cleanFileManageMsg(dialog);
							dialog.find('[data-role="uploadFileList"]').grid('refresh');
							if($('[data-role="feeBillGrid"]').getGrid())
								$('[data-role="feeBillGrid"]').getGrid().refresh();
						});
					}});   
			});
	};
	
	function cleanFileManageMsg(dialog){
		dialog.find("#lefile,#photoCover,#description").val("");
	};
	function initFileManageMsg(dialog,id,type){
		 dialog.find("#id").val(id);
		 dialog.find("#type").val(type);
		var columns = [ {title : "文件名",name : "fileName"},{title : "描述",name : "description"},{
			title : "操作",name : "url",render:function(item){
				return "<div style='width:50px;'  class='input-group-btn'>"
				+"<a href='"+config.getDomain()+"/resource/upload/file/"+item.fileName+"' class='btn btn-xs blue'><span class='fa fa-cloud-download' title='下载'></span></a>"
				+"<a href='javascript:void(0);' style='margin:0 2px;'class='btn btn-xs red' onclick='Outbound.deleteFile("+item.id+")' ><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a></div>";
			}
		}];
		if(dialog.find('[data-role="uploadFileList"]').getGrid())
			dialog.find('[data-role="uploadFileList"]').getGrid().destory();
		dialog.find('[data-role="uploadFileList"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain()+"/outboundserial/getFileList?arrivalId="+id+"&type="+type
		});
	};
	
	//删除文件
	function deleteFile(id){
		$.ajax({
			type : "post",
			url : config.getDomain()+"/outboundserial/deleteUploadFile?id="+id,
			dataType : "json",
			success : function(data) {
				if(data.code=="0000"){
					$('[data-role="uploadFileList"]').grid('refresh');
					if($('[data-role="feeBillGrid"]').getGrid())
						$('[data-role="feeBillGrid"]').getGrid().refresh();
				}
			}
		});
	}
/*******************************************************************************************************/
	function exportOutBound(){
		var url=config.getDomain()+'/outboundserial/exportOutbound?';
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
/*******************************************************************************************************/	
	return {
		changetab:changetab,//改变标签
		init:init,//初始化列表
		updateArrivalInfo:updateArrivalInfo,//更新出港信息
		initView:initView,
		countLastLeaveTime:countLastLeaveTime,//计算最大最大在港时间
		dialogDockOutboundNotify:dialogDockOutboundNotify,//码头船发作业通知单
		dialogOperationOutboundNotify:dialogOperationOutboundNotify,//操作班船发作业通知单
		dialogZhuanshuNotify:dialogZhuanshuNotify,
		fileManage:fileManage,
		exportOutBound:exportOutBound,
		deleteFile:deleteFile
	}
}();