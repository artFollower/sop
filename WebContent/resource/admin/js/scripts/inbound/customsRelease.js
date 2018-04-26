//海关放行统计表
var customsRelease=function(){
	var systemUserId;//登陆人id
	var systemUserName;//登陆人名称
	function init(){
		initSearchCtr();
		initTable();
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
	
	function initSearchCtr(){
		initFormIput();
		util.urlHandleTypeaheadAllData("/product/select",$('#productName'),undefined,undefined,function(data){
			if(data){
				$('.tankDiv').empty().append('<input type="text" id="tankId" class="form-control"/>');
				util.urlHandleTypeaheadAllData("/baseController/getTankCode?productId="+data.id,$('#tankId'),function(item){return item.code});
			}
		});
		util.urlHandleTypeaheadAllData("/baseController/getTankCode",$('#tankId'),function(item){return item.code});
		//初始化 时间控件
		util.initDatePicker();
		 //高级搜索显示
		$("#searchBtn").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$(".reset").click(function(){
			$('#productName,#shipId,#startTime,#endTime').removeAttr('data').val('');
			$("#type").val(0);
			$(".search").click();
		});
		$(".search").click(function(){
			$('div[data-role="customsreleaseGrid"]').getGrid().search(getSearchCondition());
			checkTotalMsg($("#isShowAll"));
		});
		$("#isShowAll").click(function(){
			checkTotalMsg($(this));
		});
	};
	
	//获取查询条件
	function getSearchCondition(){
		return {
			tankId:$("#tankId").attr("data"),
			productId:$('#productName').attr('data'),
			shipName:$('#shipId').val(),
			type:$("#type").val(),
			startTime:util.formatLong($("#startTime").val()+" 00:00:00"),
			endTime:util.formatLong($("#endTime").val()+" 23:59:59")
		}
	};
	
	function initTable(){
		var columns=[{title:"日期",name:"inboundTime"},{title:"船名",render:function(item){
			return item.shipName+"/"+item.shipRefName;
		}},
		             {title:"品名",name:"productName"}
		,{title:"罐号",name:"tankName",render:function(item){
			if(item.tankType){
				if(item.tankType>=2){
					return  "<span style='color:red'>"+ item.tankName+"</span>";
					
				}else{
					return item.tankName;
				}
			}else{
				return item.tankName;
			}
			
			
       	 
        }
		},
		             {title:"海关已放行量(吨)",render:function(item){
		            	 return util.toDecimal3(util.isNull(item.hasCustomsPass,1),true);
		             }},{title:"卸货前罐量(吨)",render:function(item){
		            	 if(util.isNull(item.beforeInboundTank,1)==0){
		            		 return util.toDecimal3(item.startWeight,true);
		            	 }else{
		            		 return util.toDecimal3(item.beforeInboundTank,true);
		            	 }
		             }},{title:"卸货后罐量(吨)",render:function(item){
		            	 if(util.isNull(item.afterInboundTank,1)==0){
		            		 return util.toDecimal3(item.endWeight,true);
		            	 }else{
		            		 return util.toDecimal3(item.afterInboundTank,true);
		            	 } 
		             }},
		             {title:"卸货数量(吨)",render:function(item){
		            	 if(util.isNull(item.inboundCount,1)==0){
		            		 return util.FloatSub(item.endWeight,item.startWeight);
		            	 }else{
		            		 return util.toDecimal3(item.inboundCount,true);
		            	 }  
		             }},{title:"可发数量(吨)",render:function(item){
		            	 return util.toDecimal3(util.isNull(item.outBoundCount,1),true);
		             }},{title:"不可发数量(吨)",render:function(item){
		            	 if(util.isNull(item.inboundCount,1)==0){
		            		 return util.FloatSub(item.endWeight,item.startWeight);
		            	 }else{
		            		 return util.toDecimal3(util.isNull(item.unOutBoundCount,1),true);
		            	 } 
		             }},
		             {title:"备注",name:"description"},
		             {title:"状态",render:function(item){
		            	 var inboundCount=(item.inboundCount?item.inboundCount:util.isNull(util.FloatSub(item.endWeight,item.startWeight),1));
		            	  if(util.isNull(item.hasCustomsPass,1)>0&&util.isNull(item.hasCustomsPass,1)<parseFloat(inboundCount)){
		            		 return "<label style='color:#666699'>部分放行</label>";
		            	 }else if(util.isNull(item.hasCustomsPass,1)==parseFloat(inboundCount)&&item.hasCustomsPass){
		            		 return "<label style='color:#9966CC'>已放行</label>";
		            	 }else {
			            	 return "<label style='color:#99CC33'>未放行</label>";
		            	 }
		             }},
		             {title:"操作",render:function(item){
		            	 return '<div  class="input-group-btn" style="width:60px;"><a href="javascript:void(0)" style="margin:0 2px;" onclick="customsRelease.initCustomsReleaseDialog('+item.id+')" class="btn btn-xs blue" ><span class="glyphicon glyphicon-edit" title="修改"></span></a></div>';;
		             }}];
		if($('div[data-role="tankmeasureGrid"]').getGrid()){
			$('div[data-role="tankmeasureGrid"]').getGrid().destory();
		}
		
		$('div[data-role="customsreleaseGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			url : config.getDomain()+"/customsrelease/list",
			callback:function(){
				$('[data-role="customsreleaseGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
			}
		});
	};
	function checkTotalMsg(obj){
		$this=$(obj);
		if($this.is(":checked")){//
			if($('div[data-role="customsreleaseGrid"]')!=null){
				//获取条件下的统计数据
				$.ajax({
                    type:'post',
					url:config.getDomain()+"/customsrelease/getTotalNum",
					data:getSearchCondition(),
					dataType:'json',
					success:function(data){
						util.ajaxResult(data,'统计',function(ndata){
							var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
							"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总卸货量: </label>" +
	                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(ndata[0].totalInboundCount,true)+" 吨</label>"	+	
			                   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总可发量: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(ndata[0].totalOutBoundCount,true)+" 吨</label>"	+				
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总不可发量: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(ndata[0].totalUnOutBoundCount,true)+" 吨</label>"	+
							        "</div>";
							$(".totalFeeDiv").remove();
							$('[data-role="customsreleaseGrid"]').find(".grid-body").parent().append(totalHtml);
						},true);
					}
				});
			} else{
				$this.attr('checked',false);
			}
		}else{
			$this.attr('checked',false);
        	$(".totalFeeDiv").remove();
		}
	};
	
	
	
	function initCustomsReleaseDialog(id){
		if(id){
			$.get(config.getResource()+"/pages/inbound/customsrelease/edit.jsp").done(function(data){
				  var dialog = $(data);
				  
				   if(id){
					   dialog.find("#id").text(id);
					   initDialogData(id,dialog);
				   }else{
					   dialog.find(".user").hide();
				   }
				   initDialogCtr(dialog);
					dialog.modal({
						keyboard: true
					});
				});
		}	
	};
	function initDialogCtr(dialog){
		dialog.find(".createUserDiv").hide();
		dialog.find("#beforeInboundTank,#afterInboundTank").change(function(){
			if(util.isNull(dialog.find("#beforeInboundTank").val())!=''&&util.isNull(dialog.find("#afterInboundTank").val())!=''){
			dialog.find("#inboundCount").val(util.FloatSub(dialog.find("#afterInboundTank").val(),dialog.find("#beforeInboundTank").val()));
			}
			if(util.isNull(dialog.find("#hasCustomsPass").val())!=''){
				dialog.find("#unOutBoundCount").val(util.FloatSub($(this).val(),dialog.find("#hasCustomsPass").val()));
			}
		});
		dialog.find("#hasCustomsPass").change(function(){
		   dialog.find("#outBoundCount").val( util.toDecimal3($(this).val(),true));
			if(util.isNull(dialog.find("#inboundCount").val())!=''){
				dialog.find("#unOutBoundCount").val(util.FloatSub(dialog.find("#inboundCount").val(),$(this).val()));
			}
		});
		dialog.find("#addCustomsRelease").click(function(){
			
			if(config.validateForm(dialog.find(".addGoodsForm"))){
			
			var id=util.isNull(dialog.find("#id").text(),1);
			var data={
					id:id,
					hasCustomsPass:util.isNull(dialog.find("#hasCustomsPass").val()),
					beforeInboundTank:util.isNull(dialog.find("#beforeInboundTank").val()),
					afterInboundTank:util.isNull(dialog.find("#afterInboundTank").val()),
					inboundCount:util.isNull(dialog.find("#inboundCount").val()),
					outBoundCount:util.isNull(dialog.find("#outBoundCount").val()),
					unOutBoundCount:util.isNull(dialog.find("#unOutBoundCount").val()),
					description:dialog.find("#description").val(),
					createTime:util.formatLong(util.currentTime(1)),
					userId:systemUserId
			}
			$.ajax({
				type:'post',
				url:config.getDomain()+'/customsrelease/update',
				dataType:'json',
				data:data,
				success:function(data){
				util.ajaxResult(data,'更新',function(undefined,nmap){
					util.updateGridRow($('div[data-role="customsreleaseGrid"]'),{url:'/customsrelease/list',id:id});
					initDialogData(id,dialog);
				});
					
				}
			});
			}
		});
		
	};
	
	function initDialogData(id,dialog){
		$.ajax({
			type:'post',
			url:config.getDomain()+"/customsrelease/list",
			data:{id:id},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取信息',function(ndata){
					if(ndata){
						dialog.find("#inboundTime").text(util.isNull(ndata[0].inboundTime));
						dialog.find("#shipName").text(util.isNull(ndata[0].shipName+"/"+ndata[0].shipRefName));
						dialog.find("#productName").text(util.isNull(ndata[0].productName));
						dialog.find("#tankName").text(util.isNull(ndata[0].tankName));
						dialog.find("#hasCustomsPass").val(util.isNull(ndata[0].hasCustomsPass));
						if(util.isNull(ndata[0].beforeInboundTank,1)==0){
							dialog.find("#beforeInboundTank").val(util.toDecimal3(ndata[0].startWeight,true));
						}else{
							dialog.find("#beforeInboundTank").val(util.toDecimal3(ndata[0].beforeInboundTank,true));
						}
						if(util.isNull(ndata[0].afterInboundTank,1)==0){
							dialog.find("#afterInboundTank").val(util.toDecimal3(ndata[0].endWeight,true));
						}else{
							dialog.find("#afterInboundTank").val(util.toDecimal3(ndata[0].afterInboundTank,true));
						}
						dialog.find("#afterInboundTank").change();
						if(util.isNull(ndata[0].inboundCount,1)!=0){
							dialog.find("#inboundCount").val(util.isNull(ndata[0].inboundCount));
							dialog.find("#outBoundCount").val(util.isNull(ndata[0].outBoundCount));
							dialog.find("#unOutBoundCount").val(util.isNull(ndata[0].unOutBoundCount));
						}else{
							dialog.find("#hasCustomsPass").val(0);
							dialog.find("#hasCustomsPass").change();
						}
						dialog.find("#description").val(util.isNull(ndata[0].description));
						if(util.isNull(ndata[0].userId,1)!=0){
							dialog.find(".createUserDiv").show();
							dialog.find("#userId").text(util.isNull(ndata[0].userName)).attr('data',ndata[0].userId);
							dialog.find("#createTime").text(ndata[0].createTimeStr);
						}
					    var columns=[{title:"货批号",name:"cargoCode"
//					    	render:function(item){return '<label><a onclick="CargoLZ.openCargoLZ('+item.cargoId+')" href="javascript:void(0)">'+util.isNull(item.cargoCode)+'</a></label>';}
					    },
					   {title:"货主",name:"clientName"},{title:"计划量",render:function(item){ return util.toDecimal3(item.goodsPlan,true);}},
					   {title:"放行量",render:function(item){return util.toDecimal3(item.cargoPass,true);}}];
					    if(ndata[0].baseCargoMsg&&ndata[0].baseCargoMsg.length>0){
					    dialog.find('div[data-role="baseCargoPassGrid"]').grid({
					    	identity : 'id',
							columns : columns,
							isShowIndexCol : false,
							isShowPages : false,
							isUserLocalData:true,
							localData:ndata[0].baseCargoMsg
					    });
					    	var  baseTotalPass=0;
					    for(var i=0;i<ndata[0].baseCargoMsg.length;i++)
					    	baseTotalPass=util.FloatAdd(baseTotalPass,ndata[0].baseCargoMsg[i].cargoPass,3);
					    
					    var  baseTotalHtml="<div class='form-group baseTotalPassDiv' style='margin:auto 10px'>"+
					    "<label class='control-label' style='margin:auto 10px'>一般贸易放行量：</label>"+
		                "<label class='control-label' style='margin:auto 10px'>"+baseTotalPass+" 吨</label>"+
		                "<label class='control-label' style='margin:auto 10px'>已分配：</label>"+
		                "<label class='control-label' style='margin:auto 10px'>"+ndata[0].hasBasePass+" 吨</label>"+
//		                "<label class='control-label' style='margin:auto 10px'>未分配：</label>"+
//		                "<label class='control-label' style='margin:auto 10px'>"+util.FloatSub(baseTotalPass,ndata[0].hasBasePass,3)+" 吨</label>"+
		                "</div>";
					    dialog.find(".baseTotalPassDiv").remove();
					    dialog.find('div[data-role="baseCargoPassGrid"]').find(".grid-body").attr("style",'min-height:0');
					    dialog.find('div[data-role="baseCargoPassGrid"]').find(".grid-body").parent().append(baseTotalHtml);
					    }
					    if(ndata[0].bondedCargoMsg&&ndata[0].bondedCargoMsg.length>0){
					    dialog.find('div[data-role="bondedCargoPassGrid"]').grid({
					    	identity : 'id',
							columns : columns,
							isShowIndexCol : false,
							isShowPages : false,
							isUserLocalData:true,
							localData:ndata[0].bondedCargoMsg
					    });
					    	var  bondedTotalPass=0;
					    for(var i=0;i<ndata[0].bondedCargoMsg.length;i++)
					    	bondedTotalPass=util.FloatAdd(bondedTotalPass,ndata[0].bondedCargoMsg[i].cargoPass,3);
					    
					    var  bondedTotalHtml="<div class='form-group bondedTotalPassDiv' style='margin:auto 10px'>"+
					    "<label class='control-label' style='margin:auto 10px'>保税贸易放行量：</label>"+
		                "<label class='control-label' style='margin:auto 10px'>"+bondedTotalPass+" 吨</label>"+
		                "<label class='control-label' style='margin:auto 10px'>已分配：</label>"+
		                "<label class='control-label' style='margin:auto 10px'>"+ndata[0].hasBondedPass+" 吨</label>"+
//		                "<label class='control-label' style='margin:auto 10px'>未分配：</label>"+
//		                "<label class='control-label' style='margin:auto 10px'>"+util.FloatSub(bondedTotalPass,ndata[0].hasBondedPass,3)+" 吨</label>"+
		                "</div>";
					    dialog.find(".bondedTotalPassDiv").remove();
					    dialog.find('div[data-role="bondedCargoPassGrid"]').find(".grid-body").attr("style",'min-height:0');
					    dialog.find('div[data-role="bondedCargoPassGrid"]').find(".grid-body").parent().append(bondedTotalHtml);
					    }
					}
				},true);
			}
		});
	};
	function exportXML(){
		var url = config.getDomain()+"/customsrelease/exportExcel?";
		if(util.isNull($("#startTime").val(),1)!=0){
			url+="&&startTime="+util.formatLong($("#startTime").val()+" 00:00:00");
		}else{
			$("body").message({
				type:'warning',
				content:'请填写起始日期'
			});
			return false;
		}
		if(util.isNull($("#endTime").val(),1)!=0){
			url+="&&endTime="+util.formatLong($("#endTime").val()+" 23:59:59");
		}else{
			$("body").message({
				type:'warning',
				content:'请填写截止日期'
			});
			return false;
		}
		 url+="&&type="+util.isNull($("#type").val(),1);
		
		if(util.isNull($("#productName").attr("data"),1)!=0){
			url+="&&productId="+util.isNull($("#productName").attr("data"),1);
		}
		
		if(util.isNull($('#shipId').attr('data'),1)!=0){
			url+="&&shipId="+util.isNull($('#shipId').attr('data'),1);
		}
			window.open(url);
	};
return {
	init:init,
	initCustomsReleaseDialog:initCustomsReleaseDialog,
	exportXML:exportXML
};	
	
	
}();