//出港计划
var shipArrival = function() {
	var goodsData;//查询的货体信息
	var columnCheckArray;
	var systemUserId,systemUserName;
	var actualType=-1;//开票状态
	var init = function(){
		initSearchCtr();
		initTable();
	};
	function initSearchCtr(){
		util.initDatePicker();
		util.urlHandleTypeaheadAllData("/product/select",$('#productId'));// 初始化货品
		util.urlHandleTypeaheadAllData("/baseController/getClientName",$('#ladingClientId'),function(item){return item.name+"["+item.code+"]";});
		//初始化按钮操作
		$(".btn-add").click(function(){
			window.location.href = "#/shipArrivalAdd?i="+Math.random();
		});
		$(".search").click(function() {
	       $('[data-role="shipArrivalGrid"]').getGrid().search(getSearchCondition());
		});
		$(".reset").click(function(){
			cleanSearchMsg();
			$('[data-role="shipArrivalGrid"]').getGrid().search(getSearchCondition());
		});
		$("#isShowAll").click(function(){
			 $('[data-role="shipArrivalGrid"]').getGrid().search(getSearchCondition());
		});
		$("#isWarningIcon").popover();
		$("#isWarning").click(function(){
			if($(this).is(":checked"))
				$("#isShowAll").attr('checked',true);				
			 $('[data-role="shipArrivalGrid"]').getGrid().search(getSearchCondition());
		});
	};
	
	function initTable(){
		var columns = [{title : "预计到港",name:"startTime"
		},{title : "船舶英文名",render: function(item){
				return "<a href='#/shipArrivalGet?id="+item.id+"'  class='blue'>"+item.shipName+"</a>";
		}},{title : "船舶中文名",width:80,render:function(item){
			if(item.type==2){
				return item.refName;			
			}else if(item.type==5){
				return item.refName+'<label style="color:#00aa99">(通过)<label>';
			}else {
				return item.refName;
			}
		}},{title:"货品",name:"productName"
		},{title:"提货单位",render:function(item){
			return getChildTable(item.arrivalPlan,'ladingClientName');
		}},{title:"货主单位",render:function(item){
			return getChildTable(item.arrivalPlan,'clientName');
		}},{title:"货批号",render:function(item){
			return getChildTable(item.arrivalPlan,'cargoCode');
		}},{title:"货体号",render:function(item){
			return getChildTable(item.arrivalPlan,'goodsCode');
		}},{title:"提单号",render:function(item){
			return getChildTable(item.arrivalPlan,'ladingCode');
		}},{title:"计划量(吨)",render:function(item){
			return getChildTable(item.arrivalPlan,'goodsTotal');
		}},{title:"总计划量(吨)",render:function(item){
			var goodsTotals=0;
			if(item.arrivalPlan&&item.arrivalPlan.length>0){
				for(var i=0,len=item.arrivalPlan.length;i<len;i++){
					goodsTotals=util.FloatAdd(goodsTotals,item.arrivalPlan[i].goodsTotal);
				}
			}
			return util.toDecimal3(goodsTotals,true);
		}},{title : "状态",width:50,render: function(item){
			      if(item.status>=50&&item.actualType){
			    	  var array=item.actualType.split(",");
				   if(item.status==54&&$.inArray(0,array)==-1){
					   return "<font color='#0099CC'>已出库</font>";
				   }else{
					   return "<font color='#9966CC'>未出库</font>";
				   }}else if(!item.actualType){
					   if(item.status==54){
					   return "<font color='#0099CC'>已出库</font>";
					   }else if(item.status==50){
						return "<font color='#826858'>未开票</font>";
					}else if(item.status==1){
						return "<font color='#9966CC'>未审批</font>";
					}else{
						return "<font color='#1d953f'>未提交</font>";
					}
			   }else{
				   return "<font color='#9966CC'>未审批</font>";
			   }
			}//1已提交，50已审批，>50,actualType{0}已开票，已出库
		}, {title : "操作",render: function(item){
			var html="<div style='width:60px;'  class='input-group-btn'>";
			if(config.hasPermission('AOUTBOUNDCHECK')){
				html+=" <a href='#/shipArrivalGet?id="+item.id+"' style='margin:0 2px;'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a>";
			}
			 if(config.hasPermission('AOUTBOUNDDELETE')){
				 html+=(item.status==54||item.actualType?"":"<a href='javascript:void(0)' onclick='shipArrival.deleteOutbound("+item.id+")' style='margin:0 2px;' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='作废'></span></a>")
			 }
			 if(util.isNull(item.isEqual,1)>0)
			 html+="<a href='javascript:void(0);' onclick='shipArrival.handleDifLadingMsgDialog("+item.id+")' style='margin:0 2px;'  class='btn btn-xs'><span class='fa  fa-exclamation-triangle' style='color:red;font-size:17px' title='计划提单号与开票提单号不一致'></span></a>";
			 
			 html+="</div>";
			 return html;
			}
		}];
		
		/*解决id冲突的问题*/
		$('[data-role="shipArrivalGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol:false,
			searchCondition:getSearchCondition(),
			gridName:'shipArrival',
			stateSave:true,
			backCallBack:function(data){
				if(data.isShowAll==1)
					$("#isShowAll").attr('checked',true);
				if(data.isWarning==1)
					$("#isWarning").attr('checked',true);
			},
			isShowPages : true,
			url : config.getDomain()+"/shipArrival/list",
			callback:function(){
				$('.inmtable').closest("td").css('padding','0px');
				$('[data-role="shipArrivalGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
				util.setColumnsVisable($('div[data-role="shipArrivalGrid"]'),[0,11],columnCheckArray,function(){columnCheckArray=util.getColumnsCheckArray();});
			}
		});
	};
	function getSearchCondition(){
		return {
			shipName:$("#shipName").val(),
			productId:$("#productId").attr("data"),
			ladingClientId:$("#ladingClientId").attr('data'),
			ladingEvidence:$("#ladingEvidence").val(),
			startTime:util.formatLong($("#startTime").val()+" 00:00:00"),
			endTime:util.formatLong($("#endTime").val()+" 23:59:59"),
			type:$("#type").val(),
			isShowAll:$("#isShowAll").is(":checked")?1:0,
			isWarning:$("#isWarning").is(":checked")?1:0
		}
	};
	function cleanSearchMsg(){
		$("#shipName,#productId,#ladingClientId,#ladingEvidence,#startTime,#endTime").val("").removeAttr('data');
		$("#type").val(0);
	};
	function getChildTable(data,name){
		if(data){
			if(data!=null&&data.length>1){
			var html='<table class="table inmtable" style="margin-bottom: 0px;">';
		    for(var i=0;i<data.length;i++){
		    	html+='<tr><td style="border-bottom:1px solid #ddd;white-space:nowrap;"><label>'+util.isNull(data[i][name])+'</label></td></tr>';
		    	}
		    html += '</table>';
		    return html;
			}else if(data!=null&&data.length==1){
				return '<label>'+util.isNull(data[0][name])+'</label>';
			}}else{
			return "";
	}
	};
/******************************************************************************************************/
	function initOutBoundPlan(id){
		//获取用户名
		if(!systemUserId){
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
		if(id){
		 initOutboundPlanMsg(id);
		}else{
		$(".hasApproveDiv").hide();
		}
		initOutboundPlanControl();
	};
/******************************************************************************************************/	
	function initOutboundPlanControl(){
		util.urlHandleTypeaheadAllData("/ship/select",$("#shipId"),function(item){return item.name;},function(item){return item.id;},function(item){
			if(item){
				$("#shipInfo").show();
				$("#addShipInfo").hide();
				$("#shipRef").empty().append('<input type="text" id="shipRefId" data-required="1" data-type="Require" data=""  class="form-control" />');
				util.urlHandleTypeahead("/shipref/list?shipId="+item.id,$('#shipRefId'),'refName');
			}else{
				$("#shipInfo").hide();
				$("#addShipInfo").show();
			}
		},100);
		$("#shipId").change(function(){
			if(util.isNull($(".shipId").attr('data'),1)==0){
				$("#shipInfo").hide();
				$("#addShipInfo").show();
			}else{
				$("#shipInfo").show();
				$("#addShipInfo").hide();
			}
		});
		//TODO 通过船必须没有关联货体，非通过船必须关联货体
		$("#isPassShip").change(function(){
			$this=$(this);
			if($this.is(":checked")){//校验是否存在关联货体的计划
				$("#goodsTable>tbody").find('tr').each(function(){
					$tr=$(this);
					if(util.isNull($tr.find(".goodsId").attr("data"),1)!=0){
						$('body').message({
							type:'warning',
							content:'通过船无法添加关联货体的计划，请确认后点击。'
						});
						$this.attr('checked',false);
						return;
					}
				});
			} 
		});
		
		util.urlHandleTypeaheadAllData("/product/select",$('#productId'));
		$(".save").click(function(){
			var status=$(this).attr("key");
			var resultMsg='';
			var unSurePlan=0;
			var outBoundPlanId=util.isNull($('#outBoundPlanId').text(),1)==0?undefined:util.isNull($('#outBoundPlanId').text(),1);
			if(config.validateForm($(".outBoundForm"))){
				var arrival ={
						'id':outBoundPlanId,
						'shipId': $("#shipId").attr("data"),
						'description': $("#description").val(),
						'type':$("#isPassShip").is(":checked")?5:2,//5 表示出库通过，2表示正常出库
						'arrivalStartTime':util.isNull($("#startTime").val(),1)==0?undefined:($("#startTime").val()+" 00:00:00"),
						'arrivalEndTime' :util.isNull($("#endTime").val(),1)==0?undefined:($("#endTime").val()+" 00:00:00"),
						'productId':$("#productId").attr("data"),
						'shipRefId':$("#shipRefId").attr("data"),
						'status':(status==3?-1:status)
					};
				
				if(status==50||status==3){
					resultMsg=(status==50?'通过':'驳回');
					arrival.reviewUserId=systemUserId;
					arrival.reviewTime=util.formatLong(util.currentTime(1));
				}else{
					resultMsg=(status==0?'保存':'提交');
					arrival.createUserId=systemUserId;
					arrival.createTime=util.formatLong(util.currentTime(1));
				}
				
				var arrivalPlanList=new Array();
				$("#goodsTable>tbody").find('tr').each(function(){
					$this=$(this);
					unSurePlan++;
					arrivalPlanList.push({
						'id':util.isNull($this.find(".itemPlanId").attr("data"),1)==0?undefined:util.isNull($this.find(".itemPlanId").attr("data"),1),
						'arrivalId':outBoundPlanId,
						'ladingClientId':util.isNull($this.find(".ladingClientId").attr("data"),1),//提货单位
						'clientId': util.isNull($this.find(".clientId").attr("data"),1),//货主单位
						'tradeType': $this.find(".tradeTypeId").attr("data"),//贸易类型
						'goodsId':util.isNull($this.find(".goodsId").attr("data"),1),//货体id
						'actualLadingClientId':util.isNull($this.find(".actualLadingClientId").attr("data"),1),//实际提货人
						'clearanceClientId':util.isNull($this.find(".clearanceClientId").attr('data'),1),
						'cargoId':util.isNull($this.find(".cargoId").attr("data"),1),//货批号
						'tankCodes':$this.find(".tankCodes").text(),
						'goodsTotal': $this.find(".productAmount").text(),//计划总量
						'ladingCode': $this.find(".ladingCode").text(),//发货提单号
						'isVerification': $this.find(".isVerification").attr("data"),//是否核销
						'flow': $this.find(".flow").text(),//流向
						'requirement':($("#isPassShip").is(":checked")?$this.find(".inboundMsg").text():null),
						'createUserId':(status<3?systemUserId:undefined),
						'type':$("#isPassShip").is(":checked")?5:2,//类型
						'status':0		
					});
				});
				
				if(outBoundPlanId){//如果存在id,查看已审批的是否存在
					$("#hasApproveGoodsTable>tbody").find('tr').each(function(){
						$this=$(this);
						arrivalPlanList.push({
							'id':util.isNull($this.find(".itemPlanId").attr("data"),1),
							'arrivalId':outBoundPlanId,
							'ladingClientId':util.isNull($this.find(".ladingClientId").attr("data"),1),//提货单位
							'clientId': util.isNull($this.find(".clientId").attr("data"),1),//货主单位
							'tradeType': $this.find(".tradeTypeId").attr("data"),//贸易类型
							'goodsId':util.isNull($this.find(".goodsId").attr("data"),1),//货体id
							'actualLadingClientId':util.isNull($this.find(".actualLadingClientId").attr("data"),1),//实际提货人
							'clearanceClientId':util.isNull($this.find(".clearanceClientId").attr('data'),1),
							'cargoId':util.isNull($this.find(".cargoId").attr("data"),1),//货批号
							'tankCodes':$this.find(".tankCodes").text(),
							'goodsTotal': $this.find(".productAmount").text(),//计划总量
							'ladingCode': $this.find(".ladingCode").text(),//发货提单号
							'isVerification': $this.find(".isVerification").attr("data"),//是否核销
							'createUserId':(status<3?systemUserId:undefined),
							'requirement':($("#isPassShip").is(":checked")?$this.find(".inboundMsg").text():null),
							'flow': $this.find(".flow").text(),//流向
							'type':$("#isPassShip").is(":checked")?5:2,//类型
							'status':2		
						});
					});
				}
				if(arrivalPlanList.length==0){
					$("body").message({
						type : 'error',
						content : '不存在出港记录'
					});
				}else if(status==50&&unSurePlan!=0){
					$("body").message({
						type : 'warnning',
						content : '还存在未确认的记录'
					});
				}else{
					$.ajax({
						type:"post",
						url:config.getDomain()+"/shipArrival/add",
						data:{"shipArrivalDto":JSON.stringify({"arrival":arrival,"arrivalPlanList":arrivalPlanList})},
						dataType : "json",
					    success:function(data){
						  util.ajaxResult(data,resultMsg,function(ndata,nmap){
							  if(nmap.id){
							  initOutboundPlanMsg(nmap.id);
							  }
						  });
						  }
						});
					
				}
			}
		});
		$("#planBack").click(function(){
			//TODO 回退到未提交状态， 1.已经出库无法回退，开始做船舶出库，提示会清除船舶信息，清调度确认，2.已经开票开票冲销后回退
			var arrivalStatus=util.isNull($("#status").val(),1);
			var arrivalId=util.isNull($("#outBoundPlanId").text(),1);
			if(actualType==0){
				$("body").message({
					type : 'warning',
					content : '该计划已经开票，如需回退请联系开票室进行开票冲销。'
				});
				return false ;
			}else if(arrivalStatus>50){
				$('body').confirm({
					content:'该计划已进行船舶出库操作，回退将清除后续数据，确认要回退吗？',
					callBack:function(){
						doPlanBack(arrivalId);
						return false;
					},
					cancel:function(){
						   return false;
					   }}	
				);
				return false;
			}
			doPlanBack(arrivalId);

		});
	};
	
	function doPlanBack(arrivalId){
		$.ajax({
		type:"post",
		url:config.getDomain()+"/shipArrival/reback",
		data:{arrivalId:arrivalId},
		success:function(data){
			util.ajaxResult(data,'回退',function(){
				initOutboundPlanMsg(arrivalId);
			});
		}
	});
	}
	
/******************************************************************************************************/	
function initOutboundPlanMsg(id){
	 $(".hasApproveDiv").show();
	$("#outBoundPlanId").text(id);
	config.load();
	$.ajax({
		type:"post",
		url:config.getDomain()+"/shipArrival/get?id="+id,
		dataType:"json",
		success:function(data) {
			util.ajaxResult(data,'初始化',function(ndata){
				if(ndata){
				var outboundArrial=ndata[0];	
				var outboundArrialPlan=ndata[1];	
				$("#shipId").attr("data",outboundArrial[0].shipId).val(outboundArrial[0].shipName) ;
				$("#shipInfo").show();
				$("#addShipInfo").hide();
				$("#startTime").datepicker("setDate",outboundArrial[0].startTime);
				$("#endTime").datepicker("setDate",outboundArrial[0].endTime);
				$("#description").val(outboundArrial[0].description);
				$("#status").val(outboundArrial[0].status);
				$("#productId").attr("data",outboundArrial[0].productId).val(outboundArrial[0].productName);
				$("#shipRefId").attr("data",outboundArrial[0].shipRefId).val(outboundArrial[0].shipRefName);	
				$("#createUserId").text(util.isNull(outboundArrial[0].createUserName));
				$("#createTime").text(util.getSubTime(outboundArrial[0].createTime,1));
				$("#reviewUserId").text(util.isNull(outboundArrial[0].reviewUserName));
				$("#reviewTime").text(util.getSubTime(outboundArrial[0].reviewTime,1));
				if(outboundArrial[0].status==1){
					$(".reviewDiv,.createDiv,#planSave,#planSubmit").hide();
					$("#planUnPass,#planPass,#planBack").show();
				}else if(outboundArrial[0].status>=50){
					$(".reviewDiv,.createDiv,#planBack").show();
					$("#planSave,#planSubmit,#planUnPass,#planPass").hide();
				}else{//未提交
					$("#planSave,#planSubmit").show();
					$(".reviewDiv,.createDiv,#planUnPass,#planPass,#planBack").hide();
				}
				if(outboundArrial[0].type&&outboundArrial[0].type==5){
					$("#isPassShip").attr('checked',true);
				}else{
					$("#isPassShip").attr('checked',false);
				}	
				var length=outboundArrialPlan.length;
				var htmlA='';
				var htmlB='';
				var itemPlan;
				var totalNum=0;
				var totalNumHtml='';
				for(var i=0;i<length;i++){
					itemPlan=outboundArrialPlan[i];
					actualType=Math.max.apply(null,[actualType,itemPlan.actualType]);
					if(itemPlan.status&&itemPlan.status==2){
						htmlB+='<tr>'+
						'<td><label class="itemPlanId hidden" data="'+itemPlan.id+'" ></label><label class="status hidden" data="2" ></label>'+
						'<label class="actualLadingClientId hidden" data="'+itemPlan.actualLadingClientId+'" key="'+itemPlan.actualLadingClientName+'" ></label>'+
						'<label class="ladingClientId" data="'+itemPlan.ladingClientId+'">'+util.isNull(itemPlan.ladingClientName)+'</label></td>'+
						'<td><label class="tradeTypeId" data="'+itemPlan.tradeType+'">'+util.isNull(itemPlan.tradeTypeValue)+'</label></td>'+
						'<td><label class="clientId" data="'+itemPlan.clientId+'" >'+util.isNull(itemPlan.clientName)+'</label></td>'+
						'<td><label class="goodsId" data="'+itemPlan.goodsId+'" >'+util.isNull(itemPlan.goodsCode)+'</label></td>'+
						'<td><label class="cargoId" data="'+itemPlan.cargoId+'">'+util.isNull(itemPlan.cargoCode)+'</label></td>'+
						'<td><label class="inboundMsg" >'+util.isNull(itemPlan.inboundMsg)+'</label></td>'+
						'<td><label class="clearanceClientId" data="'+itemPlan.clearanceClientId+'">'+util.isNull(itemPlan.clearanceClientName)+'</label></td>'+
						'<td><label class="tankCodes">'+util.isNull(itemPlan.tankCodes)+'</label></td>'+
						'<td><label class="ladingCode">'+util.isNull(itemPlan.ladingCode)+'</label></td>'+
						'<td><label class="productAmount">'+util.toDecimal3(itemPlan.goodsTotal,true)+'</label></td>'+
						'<td><label class="isVerification" data="'+itemPlan.isVerification+'">'+(itemPlan.isVerification==1?'是':'否')+'</label></td>'+
						'<td><label class="flow">'+util.isNull(itemPlan.flow)+'</label></td>'+
						'<td><div style="width:70px;" class="input-group-btn">';
						if(config.hasPermission('AOUTBOUNDITEMREBACK')&&outboundArrial[0].status<54){
							htmlB+='<a href="javascript:void(0)" class="btn btn-xs red" style="margin:0 2px;" onclick="shipArrival.removeItemArrial(this,'+id+','+itemPlan.actualType+','+outboundArrial[0].status+')"> <span class="fa fa-reply" title="回退"></span></a>';
						};
						htmlB+='</div></td></tr>';
						totalNum=util.FloatAdd(totalNum,itemPlan.goodsTotal,true);//计算总量
					}else{
						htmlA+='<tr>'+
						'<td><label class="itemPlanId hidden" data="'+itemPlan.id+'" ></label><label class="status hidden" data="0" ></label>'+
						'<label class="actualLadingClientId hidden" data="'+itemPlan.actualLadingClientId+'" key="'+itemPlan.actualLadingClientName+'" ></label>'+
						'<label class="ladingClientId" data="'+itemPlan.ladingClientId+'">'+util.isNull(itemPlan.ladingClientName)+'</label></td>'+
						'<td><label class="tradeTypeId" data="'+itemPlan.tradeType+'">'+util.isNull(itemPlan.tradeTypeValue)+'</label></td>'+
						'<td><label class="clientId" data="'+itemPlan.clientId+'" >'+util.isNull(itemPlan.clientName)+'</label></td>'+
						'<td><label class="goodsId" data="'+itemPlan.goodsId+'" >'+util.isNull(itemPlan.goodsCode)+'</label></td>'+
						'<td><label class="cargoId" data="'+itemPlan.cargoId+'">'+util.isNull(itemPlan.cargoCode)+'</label></td>'+
						'<td><label class="inboundMsg" >'+util.isNull(itemPlan.inboundMsg)+'</label></td>'+
						'<td><label class="clearanceClientId" data="'+itemPlan.clearanceClientId+'">'+util.isNull(itemPlan.clearanceClientName)+'</label></td>'+
						'<td><label class="tankCodes">'+util.isNull(itemPlan.tankCodes)+'</label></td>'+
						'<td><label class="ladingCode">'+util.isNull(itemPlan.ladingCode)+'</label></td>'+
						'<td><label class="productAmount">'+util.toDecimal3(itemPlan.goodsTotal,true)+'</label></td>'+
						'<td><label class="isVerification" data="'+itemPlan.isVerification+'">'+(itemPlan.isVerification==1?'是':'否')+'</label></td>'+
						'<td><label class="flow">'+util.isNull(itemPlan.flow)+'</label></td>'+
						'<td><div style="width:70px;" class="input-group-btn">';
						if(config.hasPermission('AOUTBOUNDITEMVERIFY')){
							htmlA+='<a href="javascript:void(0)" class="btn btn-xs blue" style="margin:0 2px;" onclick="shipArrival.submitItemArrial(this)"><span class="glyphicon glyphicon-ok" title="审核通过"></span></a>';
						}
						if(config.hasPermission('AOUTBOUNDITEMUPDATE')){
							htmlA+=(util.isNull(itemPlan.id,1)==0?'':'<a href="javascript:void(0)" class="btn btn-xs blue" style="margin:0 2px;" onclick="shipArrival.addItemArrial(this)"><span class="glyphicon glyphicon-edit" title="修改"></span></a>');
						}
						if(config.hasPermission('AOUTBOUNDITEMDELETE')){
							htmlA+='<a href="javascript:void(0)" class="btn btn-xs red" style="margin:0 2px;" onclick="shipArrival.removeItemArrial(this,'+id+')"> <span class="glyphicon glyphicon-remove" title="删除"></span></a>';
						}
						htmlB+='</div></td></tr>';
					}
				}
				if(actualType==1)
				$("#planBack").hide();
				$("#goodsTable>tbody").empty().append(htmlA);
				$("#hasApproveGoodsTable>tbody").empty().append(htmlB);
				totalNumHtml="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计划量：</label>" +
				"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(totalNum,true)+"</label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>吨</label>";
				$(".totalOutboundDiv").empty().append(totalNumHtml);
				}
			},true);
		}});
	};
/******************************************************************************************************/
	function addItemArrial(obj,actualType){
	if(!$("#productId").val()){
		$("body").message({
			type : 'error',
			content : '请先选择货品'
		});
		return false ;
	}
	$.get(config.getResource()+"/pages/outbound/arrival/addCustomer.jsp").done(function(data){
	    var dialog = $(data);
	    dialog.find("#dProductId").text($("#productId").val());
	    
	    //初始化控件
	    if(obj){
	    	if(actualType==1){//已经发货只能查看
	    		dialog.find("#addItemArrial").addClass("hidden");	
	    	}else{
	    		dialog.find(".checkDiv").show();
	    		dialog.find("#resetItemArrival").removeClass('hidden');
	    	}
           initDialogMsg(dialog,obj);	    	
	    }
		initDialogControl(dialog);
		dialog.modal({
			keyboard: true
		});
	    });
		};
/********************************************************************************************************/		
	function initDialogControl(dialog){
		//{'key':'2','value':'外贸'},{'key':'3','value':'保税'}
		util.handleTypeahead([{'key':'1','value':'内贸'},{'key':'2','value':'出口'},{'key':'3','value':'转口'}],dialog.find('#tradeTypeId'),"value","key") ;
		$.ajax({
			type:'post',
			url:config.getDomain()+"/baseController/getClientName",
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取信息',function(mdata){
					util.handleTypeaheadAllData(mdata,dialog.find('#clientId'),function(item){return item.name+"["+item.code+"]";});//提货单位
					util.handleTypeaheadAllData(mdata,dialog.find('#actualLadingClientId'),function(item){return item.name+"["+item.code+"]";});//实际提货人	
					util.handleTypeaheadAllData(mdata,dialog.find('#clearanceClientId'),function(item){return item.name+"["+item.code+"]";});//报关单位	
					
				},true);
			}
		});
		//初始化提货单位
		initClientCtr(dialog.find("#allClient"),true);
		
		if($("#isPassShip").is(":checked"))
			dialog.find("#clearanceClientId,#inboundMsg").removeAttr("disabled");
		
		dialog.find("#addItemArrial").click(function(){
			var outBoundPlanId=util.isNull($('#outBoundPlanId').text(),1)==0?undefined:util.isNull($('#outBoundPlanId').text(),1);//arrivalId;
			var id=util.isNull(dialog.find("#id").text(),1);
			var status=util.isNull(dialog.find("#status").text(),1);
			//校验是否关联货体
			if(!$("#isPassShip").is(":checked")&&util.isNull(dialog.find("#goodsId").attr('data'),1)==0){
				$('body').message({
					type:'warning',
					content:'非通过船必须关联货体，请选择货体。'
				});
				return false;			
			}
			
			if(dialog.find("#isCheckedNum").is(":checked")){
			if((!outBoundPlanId||outBoundPlanId&&id==0)&&util.isNull(dialog.find("#goodsLeft").text(),1)!=0){
				if(util.isNull(dialog.find("#productAmount").val(),1)-util.isNull(dialog.find("#goodsLeft").text(),1)>0){
					$('body').message({
						type:'warning',
						content:'计划量不能超过剩余结存量'
					});
					return false;
				}
			}else if(outBoundPlanId&&id!=0&&util.isNull(dialog.find("#goodsLeft").text(),1)!=0&&util.isNull(dialog.find("#goodsPlanLeft").text(),1)!=0){
				if(util.isNull(dialog.find("#productAmount").val(),1)-util.isNull(dialog.find("#goodsPlanLeft").text(),1)-util.isNull(dialog.find("#goodsLeft").text(),1)>0){
					$('body').message({
						type:'warning',
						content:'计划量不能超过剩余结存量'
					});
					return false;
				}
			}}
			if(!id!=0){
			if(!validateGoodsAndLadingEvidence({goodsId:util.isNull(dialog.find("#goodsId").attr('data'),1),ladingEvidence:dialog.find("#ladingCode").val().toLocaleUpperCase()})){
				return false;
			}
			}
			
			if(config.validateForm(dialog.find("form"))){//校验必填项
				if(!outBoundPlanId){
				var itemHtml='<tr><td><label class="itemPlanId hidden" data="'+dialog.find("#id").text()+'" ></label><label class="status hidden" data="'+dialog.find("#status").text()+'" ></label>'+
				'<label class="actualLadingClientId hidden" data="'+dialog.find("#actualLadingClientId").attr('data')+'" key="'+util.isNull(dialog.find("#actualLadingClientId").val())+'"></label>'+
			'<label class="ladingClientId" data="'+dialog.find("#ladingClientId").attr('data')+'">'+dialog.find("#ladingClientId").val()+'</label></td>'+
			'<td><label class="tradeTypeId" data="'+dialog.find("#tradeTypeId").attr("data")+'">'+dialog.find("#tradeTypeId").val()+'</label></td>'+
			'<td><label class="clientId" data="'+dialog.find("#clientId").attr('data')+'" >'+dialog.find("#clientId").val()+'</label></td>'+
			'<td><label class="goodsId" data="'+dialog.find("#goodsId").attr('data')+'">'+dialog.find("#goodsId").val()+'</label></td>'+
			'<td><label class="cargoId" data="'+dialog.find("#cargoId").attr('data')+'">'+dialog.find("#cargoId").val()+'</label></td>'+
			'<td><label class="inboundMsg" >'+dialog.find("#inboundMsg").val()+'</label></td>'+
			'<td><label class="clearanceClientId" data="'+dialog.find("#clearanceClientId").attr('data')+'">'+dialog.find("#clearanceClientId").val()+'</label></td>'+
			'<td><label class="tankCodes">'+dialog.find("#tankCodes").val()+'</label></td>'+
			'<td><label class="ladingCode">'+dialog.find("#ladingCode").val().toLocaleUpperCase()+'</label></td>'+
			'<td><label class="productAmount">'+util.toDecimal3(dialog.find("#productAmount").val(),true)+'</label></td>'+
			'<td><label class="isVerification" data="'+dialog.find("#isVerification").val()+'">'+dialog.find("#isVerification").find("option:selected").text()+'</label></td>'+
			'<td><label class="flow">'+dialog.find("#flow").val()+'</label></td>'+
			'<td><div style="width:70px;" class="input-group-btn">';
				if(config.hasPermission('AOUTBOUNDITEMDELETE')){
					itemHtml+='<a href="javascript:void(0)" class="btn btn-xs red" style="margin:0 2px;" onclick="shipArrival.removeItemArrial(this)"> <span class="glyphicon glyphicon-remove" title="删除"></span></a>';	
				}
				itemHtml+='</div></td></tr>';
			$("#goodsTable>tbody").append(itemHtml);
			dialog.remove();
             }else{
            	 var murl=config.getDomain()+"/shipArrival/updatePlan";
            	 if(id==0){
            		 murl=config.getDomain()+"/shipArrival/saveArrivalPlan";
            	 }
            	$.ajax({
            		type:'post',
            		url:murl,
            		data:{
            			id:(id==0?undefined:id),
            			arrivalId:outBoundPlanId,
            			type:$("#isPassShip").is(":checked")?5:2,
            			status:status,
            			ladingClientId:dialog.find("#ladingClientId").attr('data'),
            			clientId:dialog.find("#clientId").attr('data'),
            			tradeType:dialog.find("#tradeTypeId").attr("data"),
            			goodsId:dialog.find("#goodsId").attr('data'),
            			cargoId:dialog.find("#cargoId").attr('data'),
            			clearanceClientId:dialog.find("#clearanceClientId").attr('data'),
            			actualLadingClientId:dialog.find("#actualLadingClientId").attr('data'),
            			tankCodes:dialog.find("#tankCodes").val(),
            			ladingCode:dialog.find("#ladingCode").val().toLocaleUpperCase(),
            			goodsTotal:dialog.find("#productAmount").val(),
            			isVerification:dialog.find("#isVerification").val(),
            			requirement:($("#isPassShip").is(":checked")?dialog.find("#inboundMsg").val():undefined),
            			flow:dialog.find("#flow").val()
            		},
            		dataType:'json',
            		success:function(data){
            			util.ajaxResult(data,'提交',function(){
            				initOutboundPlanMsg(outBoundPlanId);
            				dialog.remove();
            			});
            		}
            	});
             };
			}
		});
		
		dialog.find("#resetItemArrival").click(function(){
			dialog.find("#cargoId,#goodsId,#inboundMsg,#tankCodes,#clearanceClientId").val('').removeAttr('data');
			initDialogTable(dialog,{clientId:dialog.find("#clientId").attr('data'),productId:$("#productId").attr("data")});
		});
	}
/********************************************************************************************************/
	function initDialogMsg(dialog,obj){
		var dataTr=obj.closest('tr');
		dialog.find("#id").text($(dataTr).find(".itemPlanId").attr('data'));
		dialog.find("#status").text($(dataTr).find(".status").attr('data'));
		dialog.find("#ladingClientId").val(util.isNull($(dataTr).find(".ladingClientId").text())).attr('data',$(dataTr).find(".ladingClientId").attr('data'));
		dialog.find("#tradeTypeId").attr('data',util.isNull($(dataTr).find(".tradeTypeId").attr('data'))).val(getValueTradeType(util.isNull($(dataTr).find(".tradeTypeId").attr('data'),1)));
		dialog.find("#clientId").val(util.isNull($(dataTr).find(".clientId").text())).attr('data',$(dataTr).find(".clientId").attr('data'));
		dialog.find("#goodsId").val(util.isNull($(dataTr).find(".goodsId").text())).attr('data',$(dataTr).find(".goodsId").attr('data'));
		dialog.find("#cargoId").val(util.isNull($(dataTr).find(".cargoId").text())).attr('data',$(dataTr).find(".cargoId").attr('data'));
		dialog.find("#inboundMsg").val(util.isNull($(dataTr).find(".inboundMsg").text()));
		dialog.find("#clearanceClientId").val(util.isNull($(dataTr).find(".clearanceClientId").text())).attr('data',$(dataTr).find(".clearanceClientId").attr('data'));
		dialog.find("#actualLadingClientId").val(util.isNull($(dataTr).find(".actualLadingClientId").attr('key'))).attr('data',$(dataTr).find(".actualLadingClientId").attr('data'));
		dialog.find("#tankCodes").val(util.isNull($(dataTr).find(".tankCodes").text()));
		dialog.find("#ladingCode").val(util.isNull($(dataTr).find(".ladingCode").text()));
		dialog.find("#productAmount").val(util.toDecimal3($(dataTr).find(".productAmount").text(),true));
		dialog.find("#isVerification").val($(dataTr).find(".isVerification").attr('data'));
		dialog.find("#flow").val(util.isNull($(dataTr).find(".flow").text()));
		dialog.find("#goodsPlanLeft").text(util.toDecimal3($(dataTr).find(".productAmount").text(),true));
		initDialogTable(dialog,{clientId:$(dataTr).find(".clientId").attr('data'),productId:$("#productId").attr("data"),goodsId:$(dataTr).find(".goodsId").attr('data')});
		
		
	};
	function getValueTradeType(key){
		switch(key){
		case 1:
			return '内贸';
		case 2:
			return '出口';
		case 3:
			return '转口';
		 default:
			 return '';
		}
	}
	
	
/******************************************************************************************************/
	//删除回退单个出库项
	function removeItemArrial(obj,arrivalId,actualType,arrivalStatus){
		var dataTr=obj.closest('tr');
		var id=util.isNull( $(dataTr).find(".itemPlanId").attr('data'),1);
		var status=util.isNull($(dataTr).find(".status").attr('data'),1);
		if(id==0){
			$(dataTr).remove();
		}else{
			if(status==2){
				//TODO 提示主流程回退，提示是否开票，请将开票冲销
				  if(actualType==1){//已实发
					$('body').message({
						type:'warning',
						content:'计量已经称重，无法回退更改'
					});
					return false;
				}else if(actualType==0){//已开票
					$('body').message({
						type:'warning',
						content:'该记录已经开票，请通知开票室开票冲销后操作。'
					});
					return false;
				}
//				else if(actualType==-1){//未开票
//					if(arrivalStatus>=50){
//					$('body').message({
//						type:'warning',
//						content:'该计划已经审批，请回退整个计划后，再操作。'
//					});
//					return false;
//					}
//				}
			}
			$.ajax({
				type:'post',
				url:config.getDomain()+"/shipArrival/deleteShipPlanById",
				data:{arrivalPlanId:id,status:status},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,(status==2?'回退':'删除'),function(){
						if(arrivalId){
							initOutboundPlanMsg(arrivalId);
						}else{
							$(dataTr).remove();
						}
					});
					
				}
			});
		}
	};
/******************************************************************************************************/	
	function submitItemArrial(obj){
		var outBoundPlanId=util.isNull($('#outBoundPlanId').text(),1)==0?undefined:util.isNull($('#outBoundPlanId').text(),1);//arrivalId;
		var dataTr=obj.closest('tr');
		var id=util.isNull( $(dataTr).find(".itemPlanId").attr('data'),1);
		if(id!=0&&outBoundPlanId){
			$.ajax({
				type:'post',
				url:config.getDomain()+"/shipArrival/updateShipPlanStatus",
				data:{arrivalPlanId:id,status:2},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'审批',function(){
						initOutboundPlanMsg(outBoundPlanId);
					});
				}
			});
		}
	};
/******************************************************************************************************/
	function validateGoodsAndLadingEvidence(data){
		var goodsList=new Array();
		var isPass=true;
		//TODO 获取所有的货体和发货提单号
		if(data.goodsId!=0&&util.isNull(data.ladingEvidence)!=''){
		$("#goodsTable>tbody").find('tr').each(function(){
			$this=$(this);
			goodsList.push({
				'goodsId':util.isNull($this.find(".goodsId").attr("data"),1),
				'ladingEvidence': $this.find(".ladingCode").text(),//发货提单号
			});
		});
		
		$("#hasApproveGoodsTable>tbody").find('tr').each(function(){
			 $this=$(this);
			 goodsList.push({
					'goodsId':util.isNull($this.find(".goodsId").attr("data"),1),
					'ladingEvidence': $this.find(".ladingCode").text(),//发货提单号
				});
			});
		//TODO 校验
		for(var i=0,len=goodsList.length;i<len;i++){
			if(goodsList[i].goodsId==data.goodsId&&goodsList[i].ladingEvidence==data.ladingEvidence){
                isPass=false;
                $('body').message({
					type:'warning',
					content:'不支持同样货体开同样的提单号，请重新输入提单号或更改货体。'
				});
                break;
			}
		}
		}
		return isPass;
	};
/******************************************************************************************************/
			//根据id删除到港信息
			function deleteOutbound(id){
				if(id){
		    $('body').confirm({
				content : '确定要撤销所选记录吗?',
				callBack : function() {
					$.ajax({
						type:'post',
						url:config.getDomain()+'/shipArrival/delete',
						dataType:'json',
						data:{ids:id},
						success:function(data){
						util.ajaxResult(data,'删除',function(ndata,nmap){
							if(nmap&&nmap.reMsg){
								$('body').message({
									type:'warning',
									content:nmap.reMsg
								});
							}else{
								$('body').message({
									type:'success',
									content:'撤销成功'
								});
								$('div[data-role="shipArrivalGrid"]').getGrid().refresh();
							}
							
						},true);	
						}
					});
					
				}});
				}	
		};
/******************************************************************************************************/
		//弹出船信息dialog 并显示船的基本信息
		function showShipInfo(){
			 var shipId=util.isNull($("#shipId").attr('data'),1);
			 if(shipId!=0){
				 $.get(config.getResource()+"/pages/outbound/arrival/shipInfoDialog.jsp").done(function(data){
						var dialog = $(data) ;
						dialog.modal({
							keyboard: true
						});
						$.ajax({
							type:'post',
							url:config.getDomain()+"/ship/list",
							dataType:'json',
							data:{id:shipId},
							success:function(data){
								util.ajaxResult(data,'初始化',function(ndata){
									if(ndata){
									dialog.find(".shipLenth").val(ndata[0].shipLenth);
									dialog.find(".shipWidth").val(ndata[0].shipWidth);
									dialog.find(".shipDraught").val(ndata[0].shipDraught);
									dialog.find(".loadCapacity").val(ndata[0].loadCapacity);
									dialog.find(".shipRegistry").val(ndata[0].shipRegistry);
									dialog.find(".buildYear").val(ndata[0].buildYear);
									dialog.find(".grossTons").val(ndata[0].grossTons);
									dialog.find(".netTons").val(ndata[0].netTons);
									dialog.find(".owner").val(ndata[0].owner);
									dialog.find(".manager").val(ndata[0].manager);
									dialog.find(".contactName").val(ndata[0].contactName);
									dialog.find(".contactPhone").val(ndata[0].contactPhone);									
									}
								},true);
							}
						});
					});
			 }
		}
/******************************************************************************************************/
		//获取有效期
		function getIndate(ladingType,endTime,startTime){
			if(ladingType){
				var time=(ladingType==2?endTime:startTime);
				if(util.validateFormat(time)){
					var date=util.getDifferTime(util.currentTime(0)+" 00:00:00",time+" 00:00:00",2);
					if(date>=0||ladingType==1){
						return time;
					}else if(date<0){
						return '<label style="color:red;font-weight:bold;font-size: 14px;">'+time+'</label>';
					}
				}
			}
			return '';
		};
/******************************************************************************************************/
		//选择性初始化货主控件
		function initClientCtr(obj,isClean){
			var dialog=$(obj).closest(".modal-dialog");
			if(!isClean){
				dialog.find(".cn").empty().append('<input type="text" class=" form-control" id="ladingClientId" data-required="1" data-type="Require" maxlength="60">');				
				goodsData=undefined;
				dialog.find("#cargoId,#goodsId,#inboundMsg,#tankCodes,#clearanceClientId").val('').removeAttr('data');
				dialog.find("#goodsTbody").empty();
			}
	
				util.urlHandleTypeaheadAllData((obj.checked?"/baseController/getClientName":"/baseController/getClientNameByProductId?productId="+$("#productId").attr("data")),dialog.find('#ladingClientId'),function(item){return item.name+"["+item.code+"]";},undefined,function(item){
					//初始化货批信息
					dialog.find("#cargoId,#goodsId,#inboundMsg,#tankCodes,#clearanceClientId").val('').removeAttr('data');
					if(item&&!$("#isPassShip").is(":checked")){
						initDialogTable(dialog,{clientId:item.id,productId:$("#productId").attr("data")});
					}
				},1000);//提货单位
		};
		//初始化dialogtable
	  function	initDialogTable(dialog,params){

			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+"/shipArrival/getgoodsmsg",
				dataType:'json',
				data:params,
				success:function(data){
					util.ajaxResult(data,'获取货体信息',function(ndata){
						goodsData=ndata;
					if(ndata&&ndata.length>0){
						var html=''
					for (var i=0;i<ndata.length;i++){
						html+='<tr data="'+i+'"><td>'+ ndata[i].cargoCode+'</td>'+
						'<td><a href="javascript:void(0);" onclick="GoodsLZ.openGoodsLZ('+ndata[i].id+');" >'+util.isNull(ndata[i].goodsCode)+'</a></td>'+
						'<td>'+util.isNull(ndata[i].yuanhao)+'</td>'+
						'<td>'+util.isNull(ndata[i].diaohao)+'</td>'+
						'<td>'+util.isNull(ndata[i].shangjihuozhu)+'</td>'+
						'<td>'+getIndate(ndata[i].ladingType,ndata[i].endTime,ndata[i].startTime)+'</td>'+
						'<td>'+(ndata[i].ladingType&&ndata[i].ladingType==2?'提货权':'货权')+'</td>'+
						'<td>'+util.toDecimal3(ndata[i].goodsTotal,true)+'</td>'+
						'<td>'+util.toDecimal3(ndata[i].goodsSave,true)+'</td>'+
						'<td>'+util.toDecimal3(ndata[i].goodsCurrent,true)+'</td>'+
						'<td>'+util.toDecimal3(ndata[i].deliverNum,true)+'</td>'+
						'<td>'+util.toDecimal3(ndata[i].arrivalPlanAmount,true)+'</td>'+
						'<td>'+util.FloatSub(util.FloatSub(util.FloatSub(ndata[i].goodsCurrent,ndata[i].goodsSave,3),ndata[i].deliverNum),ndata[i].arrivalPlanAmount)+'</td>'+
						'<td>'+util.isNull(ndata[i].tankCodes)+'</td></tr>'
					}
					dialog.find("#goodsTbody").empty().append(html);
					
					if(params&&params.goodsId&&ndata.length==1){
						dialog.find("#goodsLeft").text(util.FloatSub(util.FloatSub(util.FloatSub(ndata[0].goodsCurrent,ndata[0].goodsSave,3),ndata[0].deliverNum),ndata[0].arrivalPlanAmount));
					}
					
					dialog.find("#goodsTbody").on('click','tr',function(){
						$(this).addClass('success').siblings().removeClass("success");
						var itemData=goodsData[$(this).attr('data')];
						dialog.find("#goodsId").val(itemData.goodsCode).attr("data",itemData.id);
						dialog.find("#cargoId").val(itemData.cargoCode).attr("data",itemData.cargoId);
						dialog.find("#inboundMsg").val(util.isNull(itemData.inboundMsg));
						dialog.find("#tankCodes").val(util.isNull(itemData.tankCodes));
						dialog.find("#clearanceClientId").attr('data',itemData.clearanceClientId).val(util.isNull(itemData.clearanceClientName));
						dialog.find("#goodsLeft").text(util.FloatSub(util.FloatSub(util.FloatSub(itemData.goodsCurrent,itemData.goodsSave,3),itemData.deliverNum),itemData.arrivalPlanAmount));
						if(itemData.taxType&&itemData.taxType==3){
							dialog.find("#clearanceClientId").removeAttr("disabled");
						}else{
							dialog.find("#clearanceClientId").attr("disabled","disabled");
						}
					});
						
					}
					
					},true);
					
				}
			});
		};
/******************************************************************************************************/
		function handleDifLadingMsgDialog(arrivalId){
			 $.get(config.getResource()+"/pages/outbound/arrival/difPlanAndInvoiceDialog.jsp").done(function(data){
				 var dialog=$(data);
				 initDLMDialogCtr(dialog);
				 initDLMDialogMsg(dialog,arrivalId);
			 });
		};
		function initDLMDialogCtr(dialog){
			initFormIput(dialog);
			dialog.modal({keyboard:true});
			dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
		};
		
		function initDLMDialogMsg(dialog,arrivalId){
			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+'/shipArrival/getDifPlanAndInvoiceMsg',
				data:{arrivalId:arrivalId},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'获取计划和开票不同提单号',function(ndata){
						if(ndata&&ndata.length>0){
							var html='';
							for(var i=0,len=ndata.length;i<len;i++){
								html+='<tr><td>'+util.isNull(ndata[i].cargoCode)+'</td>'+
								'<td>'+util.isNull(ndata[i].goodsCode)+'</td>'+
								'<td>'+ndata[i].inboundMsg+'</td>'+
								'<td>'+util.toDecimal3(ndata[i].goodsTotal,true)+'</td>'+
								'<td>'+ndata[i].serial+'</td>'+
								'<td>'+util.toDecimal3(ndata[i].deliverNum,true)+'</td>'+
								'<td id="ladingCode">'+util.isNull(ndata[i].ladingCode)+'</td>'+
								'<td>'+util.isNull(ndata[i].ladingEvidence)+'</td>'+
								'<td id="operateTd"><div class="input-group-btn"><a href="javascript:void(0)"  style="margin:0 4px" '
								+' onclick="shipArrival.editDialogTr(this,'+ndata[i].id+')" class="btn btn-xs blue" ><span class="glyphicon glyphicon-edit" title="修改"></span></a></div></td></tr>' 
							}
							dialog.find("#planAndInvoiceTbody").empty().append(html);
						}
					},true);
				}
			});
		};
		
		function editDialogTr(obj,id){
			var nTr=$(obj).closest("tr");
			var ladingCode=$(nTr).find("#ladingCode").text();
			$(nTr).find("#ladingCode").empty().append('<input type="text" class="form-control" style="width:100%" data='+ladingCode+' value='+ladingCode+' >');
			$(nTr).find("#operateTd").empty().append('<div class="input-group-btn"><a href="javascript:void(0)"  style="margin:0 4px" '
			 +' onclick="shipArrival.sureDialogTr(this,'+id+')" class="btn btn-xs blue" ><span class="glyphicon glyphicon-ok" title="确定"></span></a>'+
			 '<a href="javascript:void(0)" onclick="shipArrival.cancelDialogTr(this,'+id+')" style="margin:0 4px" class="btn btn-xs red " >'
	            +'<span class="fa fa-undo" title="取消"></span></a></div>');
		};
		
		function sureDialogTr(obj,id){
			var nTr=$(obj).closest("tr");
			var ladingCode=$(nTr).find("#ladingCode input").val();
			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+'/shipArrival/updateArrivalPlan',
				data:{id:id,ladingCode:ladingCode},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'确认',function(){
						$(nTr).find("#ladingCode input").attr('data',ladingCode);
						cancelDialogTr(obj,id);
						util.deleteGridRow($('[data-role="shipArrivalGrid"]'));
					});
				}
			});
		};
		
		function cancelDialogTr(obj,id){
			var nTr=$(obj).closest("tr");
			var ladingCode=$(nTr).find("#ladingCode input").attr('data');
			$(nTr).find("#ladingCode").empty().append(ladingCode);
			$(nTr).find("#operateTd").empty().append('<div class="input-group-btn"><a href="javascript:void(0)"  style="margin:0 4px" '
								+' onclick="shipArrival.editDialogTr(this,'+id+')" class="btn btn-xs blue" ><span class="glyphicon glyphicon-edit" title="修改"></span></a></div>');
		}
/******************************************************************************************************/
	return {
		init:init,//初始化出港计划列表
		initOutBoundPlan:initOutBoundPlan,//初始化添加页面
		showShipInfo:showShipInfo,//显示船舶基本信息
		removeItemArrial:removeItemArrial,//删除单个出库记录
		addItemArrial:addItemArrial,//添加/编辑单个出库记录
		submitItemArrial:submitItemArrial,//提交出库记录
		deleteOutbound:deleteOutbound,
		initClientCtr:initClientCtr,
		handleDifLadingMsgDialog:handleDifLadingMsgDialog,
		editDialogTr:editDialogTr,
		sureDialogTr:sureDialogTr,
		cancelDialogTr:cancelDialogTr
	};
}();