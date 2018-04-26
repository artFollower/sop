var v_outbound = function() {
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
	};
	/*初始化查询列表控件*/
	function initSearchCtr(){
		util.initDatePicker();
		util.urlHandleTypeaheadAllData("/baseController/getClientName",$('#clientName'),function(item){return item.name+"["+item.code+"]";});//初始化提货单位下拉菜单
		util.urlHandleTypeaheadAllData("/product/select",$('#productName'));
		util.urlHandleTypeaheadAllData("/baseController/getCanMakeInvoiceTruck",$('#trainId'));
		
		//列表检索信息展开或收起
		$(".roleManagerQuery").click(function(){
			$("#roleManagerQueryDivId").slideToggle("slow");
		}) ;
		
		$(".reset").click(function(){
			$("#trainId,#clientName,#productName,#serialNum,#startTime,#endTime,#ladingEvidence").val("").removeAttr();
			$("#status").val("");
			$(".search").click();
		});
		//车发列表信息查询
		$(".search").click(function(){
	       $('[data-role="v_outboundGrid"]').getGrid().search(getSearchCondition());
		});
		
	};
	function getSearchCondition(){
		return {
			trainId:$("#trainId").attr('data'),
			serialNum:$("#serialNum").val(),
			clientId:$("#clientName").attr('data'),
			productId:$("#productName").attr('data'),
			ladingCode:$("#ladingEvidence").val(),
			status:$("#status").val(),
			startTime:util.formatLong($("#startTime").val()+" 00:00:00"),
			endTime:util.formatLong($("#endTime").val()+" 23:59:59")	
	};
	};
	
	
	/*初始化列表*/
	function initTable(){
		var columns = [{title : "车牌号",render: function(item){
			return '<a href="#/outboundtruckserial/get?status='+item.status+'&id='+item.id+'&serial='+item.serialData[0].serial+'" class="blue">'+item.plateName+'</a>';
			}
		},{title:"开票号",render:function(item){
				var data=item.serialData;
				if(data!=null&&data.length>1){
				var html='<table class="table inmtable" style="margin-bottom: 0px;">';
			    for(var i=0;i<data.length;i++){
			    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label>'+util.isNull(data[i].serial)+'</label></td></tr>';
			    	}
			    html += '</table>';
			    return html;
				}else if(data!=null&&data.length==1){
					return '<label>'+util.isNull(data[0].serial)+'</label>';
				}else{
				return "";
			}}
		},{title : "开票日期",render:function(item){
					var data=item.serialData;
					if(data!=null&&data.length>1){
					var html='<table class="table inmtable" style="margin-bottom: 0px;">';
				    for(var i=0;i<data.length;i++){
				    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label>'+util.isNull(data[i].invoiceTime)+'</label></td></tr>';
				    	}
				    html += '</table>';
				    return html;
					}else if(data!=null&&data.length==1){
						return '<label>'+util.isNull(data[0].invoiceTime)+'</label>';
					}else{
					return "";
				}}
		},{title:"货品名称",render:function(item){
			var data=item.serialData;
			if(data!=null&&data.length>1){
			var html='<table class="table inmtable" style="margin-bottom: 0px;">';
		    for(var i=0;i<data.length;i++){
		    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label>'+util.isNull(data[i].productName)+'</label></td></tr>';
		    	}
		    html += '</table>';
		    return html;
			}else if(data!=null&&data.length==1){
				return '<label>'+util.isNull(data[0].productName)+'</label>';
			}else{
			return "";
		}}
		},{title:"开票数(吨)",render:function(item){
			var data=item.serialData;
			if(data!=null&&data.length>1){
			var html='<table class="table inmtable" style="margin-bottom: 0px;">';
		    for(var i=0;i<data.length;i++){
		    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label>'+util.toDecimal3(data[i].deliverNum,true)+'</label></td></tr>';
		    	}
		    html += '</table>';
		    return html;
			}else if(data!=null&&data.length==1){
				return '<label>'+util.toDecimal3(data[0].deliverNum,true)+'</label>';
			}else{
			return "";
		}}
		},{title:"罐号",render:function(item){

			var data=item.serialData;
			if(data!=null&&data.length>1){
			var html='<table class="table inmtable" style="margin-bottom: 0px;">';
		    for(var i=0;i<data.length;i++){
		    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label>'+util.isNull(data[i].tankName)+'</label></td></tr>';
		    	}
		    html += '</table>';
		    return html;
			}else if(data!=null&&data.length==1){
				return '<label>'+util.isNull(data[0].tankName)+'</label>';
			}else{
			return "";
		}	
		}},{title:"客户名称",name : "clientName"
		},{title:"实发数量（吨）",render: function(item){
				var data=item.serialData;
			    var amount=0;
			    if(data!=null&&data.length>0){
			    for(var i=0;i<data.length;i++){
				if(item.status&&item.status==43){
					amount=util.FloatAdd(amount,data[i].actualNum);
				}else{
					amount=util.FloatAdd(amount,data[i].deliverNum);
				}}}
				return util.toDecimal3(amount,true);
			}
		},{title : "开票人",name:"userName"
		},{title : "流程状态",render: function(item){
				if(item.status==40){
					return "<font color='#826858'>"+item.statusValue+"</font>";
				}else if(item.status==41){
					return "<font color='#56452d'>"+item.statusValue+"</font>";
				}else if(item.status==42){
					return "<font color='#f15a22'>"+item.statusValue+"</font>";
				}else if(item.status==43){
					return "<font color='#1d953f'>"+item.statusValue+"</font>";
				}else if(item.status==44){
					return "<font color='#78a355'>"+item.statusValue+"</font>";
				}
			}
		},{title : "操作",render: function(item){
				var html ="<div style='width:100px;'  class='input-group-btn'>" ;
				if(item.status ==40)
				{html+='<a href="#/outboundtruckserial/get?status='+item.status+'&id='+item.id+'&serial='+item.serialData[0].serial+'" style="margin:0 2px;" class="btn btn-xs blue"><span class="glyphicon glyphicon glyphicon-eye-open" title="详细"></span></a>'+
				'<a href="javascript:void(0)" onclick="v_outbound.deleteOutbound('+item.id+')" style="margin:0 2px;" class="btn btn-xs red"><span class="glyphicon glyphicon glyphicon-remove" title="撤销"></span></a>';
				}else if(item.status >= 41 && item.status <= 43)
				{
					html+='<a href="#/outboundtruckserial/get?status='+item.status+'&id='+item.id+'&serial='+item.serialData[0].serial+'" style="margin:0 2px;" class="btn btn-xs blue"><span class="glyphicon glyphicon glyphicon-eye-open" title="详细"></span></a>';
				}else{
					html+= "<a href='javascript:void(0)' disabled='disabled' style='margin:0 2px;' class=\"btn btn-xs blue\"><span class=\"glyphicon glyphicon glyphicon-eye-open\" title=\"详细\"></span></a>" ;
				}
				html+= "</div>";
				return html ;
			}
		}];
		if($('div[data-role="v_outboundGrid"]').getGrid()){
			$('div[data-role="v_outboundGrid"]').getGrid().destory();
		};
		$('[data-role="v_outboundGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			gridName:"UnloadingPlanManager",
			stateSave:true,
			url : config.getDomain()+"/outboundtruckserial/list",
			callback:function(){
				$('[data-role="v_outboundGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
				$('.inmtable').closest("td").css('padding','0px');
			}
		});
	};
/***********************************************************************************************************************/	
	function initTab(status,id,serial){
			if(util.isNull(id,1)!=0){
			$.ajax({
				type:"post",
				url:config.getDomain()+"/outboundtruckserial/getTrainInfoById",
				data:{"id" : id,"serial" : serial},
				dataType:"json",
				success:function(data) {
					util.ajaxResult(data,"获取车发信息",function(ndata){
						//重新定位
						if(ndata[0].status!=status)
						window.location = "#/outboundtruckserial/get?status="+data.data[0].status+"&id="+id ;
						
						$("#tabId").text(ndata[0].id);//车发id
						$("#tabClientId").attr("data",ndata[0].clientId).text(ndata[0].clientName) ;
						$("#tabProductId").attr("data",ndata[0].productId).text(ndata[0].productName) ;
						$("#tabAmount").text(util.toDecimal3(ndata[0].amount,true));
						$("#tabInvoiceTime").text(ndata[0].deliverTime) ;
						$("#vehiclePlate").attr("data",ndata[0].plateId).text(ndata[0].plateName) ;
						$("#tabLadingClientId").text(util.isNull(ndata[0].ladingName));
					},true);
				}
			});
			}
			//发货开票栏目
				$("#tab1").click(function(){
				$(this).parent().addClass("active").siblings().removeClass("active") ;
				$("#tab").load(config.getResource()+"/pages/outbound/truckwork/truckInvoice.jsp",function(){
					truckInvoice(id);
					});	
				});
				//称重计量栏目
				$("#tab2").click(function(){
					$(this).parent().addClass("active").siblings().removeClass("active") ;
					$("#tab").load(config.getResource()+"/pages/outbound/truckwork/truckready.jsp",function(){
						truckReady(id);
						});
				});
				//数量确认栏目
				$("#tab3").click(function(){
					$(this).parent().addClass("active").siblings().removeClass("active") ;
					$("#tab").load(config.getResource()+"/pages/outbound/truckwork/checkamount.jsp",function(){
						checkAmount(status,id);
						});
				});
				if(status==40)
				$("#tab1").click();
			    if(status==41)
			    $("#tab2").click();
			    if(status==42||status==43)
			    $("#tab3").click();
	};
	
	//发货开票信息
	function truckInvoice(id){
		$.ajax({
			type : "post",
			url : config.getDomain()+"/outboundtruckserial/getTruckInvoiceMsg",
			data : {"trainId":id},
			dataType : "json",
			async:false,
			success : function(data) {
				util.ajaxResult(data,"获取开票信息",function(ndata){
					if(ndata&&ndata.length>0){
						var html="";
						for(var i=0;i<ndata.length;i++){
							html+="<tr><td>"+ndata[i].serial+"</td><td><input type='hidden' class='vehiclePlanId' value="+ndata[i].id+" />"+ndata[i].code+"</td>" +
							"<td>"+util.isNull(ndata[i].clientCode)+"</td>" +
							"<td>"+util.isNull(ndata[i].yuanshihuoti)+"</td>" +
							"<td>"+util.isNull(ndata[i].shipInfo)+"</td>" +
							"<td>"+util.isNull(ndata[i].yuanhao)+"</td>" +
							"<td>"+util.isNull(ndata[i].diaohao)+"</td>" +
							"<td>"+util.isNull(ndata[i].tankName)+"</td>" +
							"<td>"+ util.toDecimal3(ndata[i].goodsCurrent,true)+"</td>" +
							"<td>"+util.toDecimal3(ndata[i].deliverNum,true)+"</td>"+
							"<td>"+util.isNull(ndata[i].storageInfo)+"</td>"+
							"</tr>" ;
						}
						$("#billInfo").empty().append(html) ;
						}
				},true);
			}
		});
	};
/*****************************************************************************************************/	
	
	//车发作业
	function truckReady(id){
		initTruckReadyMsg(id);
		initTruckReadyCtr();
	};
	//初始化车发作业状态控件
	function initTruckReadyCtr(){
		
		//提交
		$("#submitTruckReady").click(function(){
			
			if(util.isNull($("#wbStatus").attr('data'),1)!=3&&util.isNull($("#wbId").val(),1)!=0){
				$('body').message({
					type:'warning',
					content:'称重还未结束'
				});
				return false;
			}else{
				
				
				if(util.formatLong(util.getTimeVal($(".outTime"),true))!=util.isNull($(".outTime").attr('data'),1)&&util.isNull($("#wbId").val(),1)!=0){
					$('body').confirm({
						content : '确认要修改出库时间吗?',
						callBack : function(){
							dosubmitTruckReady(true);
							},
						cancel:function(){
							//还原出库时间
							util.initTimeVal($(".outTime"),util.formatString($(".outTime").attr('data')),true);
							dosubmitTruckReady(false);
							}
						});
				}else if(util.isNull($("#wbId").val(),1)==0){
					dosubmitTruckReady(true);
				}else{
					dosubmitTruckReady(false);
				}
				
			}
	});
	};
	
	function dosubmitTruckReady(isChangeOutTime){
		var choiceTime=null;
		var wb={
				'id':util.isNull($("#wbId").val(),1),
				'serial':$(".serialIn option:selected").text(),
				'inWeigh':util.isNull($("#inWeigh").val(),1),
				'outWeigh':util.isNull($("#outWeigh").val(),1),
				"tankId":util.isNull($("#tankCodem").attr("data"),1),
				"inPort":$("#inPort").val(),
				"status":3,
				"intoTime":util.formatLong(util.getTimeVal($(".inTime"),true)),
				"description":util.isNull($("#description").val())
		}
		
		if(isChangeOutTime){
			wb.outTime=util.formatLong(util.getTimeVal($(".outTime"),true));
	     choiceTime=util.formatLong(util.getTimeVal($(".outTime"),true))>util.isNull($(".outTime").attr('data'),1)?util.isNull($(".outTime").attr('data'),1):util.formatLong(util.getTimeVal($(".outTime"),true));
		}
		
		 var oils=util.isNull($(".serialIn option:selected").attr('data'),1);
		if(oils==0){//化学品
			wb.type=0;
		if(util.isNull($("#wbId").val(),1)==0){
			wb.deliveryNum=util.FloatSub(wb.outWeigh,wb.inWeigh,3);
			wb.actualRoughWeight=wb.outWeigh;
			wb.actualTareWeight=wb.inWeigh;
			wb.createTime=util.currentTime(1);
			wb.inStockPersonId=systemUserId;
			wb.outStockPersonId=systemUserId;
			choiceTime=util.formatLong(util.getTimeVal($(".outTime"),true));
		}else{
			wb.actualRoughWeight=util.isNull($("#actualRoughWeigh").val())==''?undefined:util.isNull($("#actualRoughWeigh").val(),1);
			wb.actualTareWeight=util.isNull($("#actualTareWeigh").val())==''?undefined:util.isNull($("#actualTareWeigh").val(),1);
			if(wb.actualRoughWeight&&wb.actualTareWeight){
			wb.deliveryNum=util.FloatSub(wb.actualRoughWeight,wb.actualTareWeight,3);
			}else{
			wb.deliveryNum=util.FloatSub(wb.outWeigh,wb.inWeigh,3);	
			}
		}
		}else if(oils==1){//油品
			wb.type=1;
			if(util.isNull($("#wbId").val(),1)==0){
			wb.deliveryNum=util.isNull($("#actualNum").val(),1);
			wb.actualRoughWeight=wb.deliveryNum;
			wb.createTime=util.currentTime(1);
			choiceTime=util.formatLong(util.getTimeVal($(".outTime"),true))
			}else{
				wb.actualRoughWeight=util.isNull($("#afUpNum").val(),1);
				wb.actualTareWeight=0;
				wb.deliveryNum=util.isNull($("#afUpNum").val(),1);
			}	
		}

		$.ajax({
			type : "post",
			url : config.getDomain()+"/outboundtruckserial/saveWeighBridge",
			dataType : "json",
			data:{"weighBridge":JSON.stringify(wb),"trainId":$("#tabId").text(),'choiceTime':choiceTime},
			success:function(data){
				util.ajaxResult(data,"提交",function(){
					initTruckReadyMsg(util.isNull($("#tabId").text(),1));
				});
			}
		});	
	}
	
	
	//初始化车发作业状态信息
	function initTruckReadyMsg(id){
		if(id){
			$.ajax({
				type:'post',
				url: config.getDomain()+'/outboundtruckserial/getTruckReadyMsg',
				dataType:'json',
				data:{trainId:id},
				success:function(data){
					util.ajaxResult(data,'获取通知单称重信息',function(ndata){
	                       if(ndata&&ndata.length>0){
	                    	   serialData=ndata;
	                    	   var selectItem='<option selected data="'+ndata[0].oils+'" value=0>'+ndata[0].serial+'</option>'
	                    	   for(var i=1;i<ndata.length;i++){
	                    		   selectItem+='<option value='+i+' data="'+ndata[0].oils+'" >'+ndata[i].serial+'</option>'
	                    	   }
	                    	   $(".serialIn").empty().append(selectItem);
	                    	   initTruckReadyHtml(ndata[0],ndata[0].oils);
	                       } 					
					},true);
				}
			});
			}	
		     $(".serialIn").unbind("change");
			 $(".serialIn").change(function(){
				 var i=$(".serialIn").val();
				 var flag=$(".serialIn option:selected").attr('data');
				 initTruckReadyHtml(serialData[i],flag);
	  	   });
	};
	//初始化车发作业页面
   function	initTruckReadyHtml(data,oils){

		var htmlStr = "" ;
		if(oils==0){//化学品
				$("#change").text("切换到流量计量");
				$("#weigh_div").show();
				$("#measure_div").hide();
				if(data!=null&&data.id!=0){
				htmlStr = "<tr><td colspan=2>称重量"+
				"</td><td><input type='hidden' value='"+data.id+"' id='wbId' /><input type='hidden' data='"+data.status+"' id='wbStatus'/><input type='text' readonly='readonly' class='form-control' style='width:120px;' id='inWeigh' value='"+util.toDecimal3(data.inWeigh,true)+"' />"+
				"</td><td><input type='text' class='form-control' style='width:120px;' readonly='readonly'  id='outWeigh' value='"+util.toDecimal3(data.outWeigh,true)+"' />"+
				"</td><td><input type='text' class='form-control' style='width:120px;' readonly='readonly'  id='netWeigh' value='"+util.toDecimal3(data.netWeigh,true)+"' />"+
				'</td>'+
				"</tr><tr><td colspan=2>调整量"+
				"</td><td><input type='text' class='form-control' style='width:120px;'  onkeyup='config.clearNoNum(this,3);' id='actualTareWeigh' value='"+util.isNull(data.actualTareWeight)+"' />"+
				"</td><td><input type='text' class='form-control' style='width:120px;' onkeyup='config.clearNoNum(this,3);' id='actualRoughWeigh' value='"+util.isNull(data.actualRoughWeight)+"' />"+
				"</td><td><input type='text' class='form-control' style='width:120px;' disabled='disabled' id='actualNetWeigh' value='' />"+
				'</td>'+
				"</tr><tr><td colspan=2>差异量"+
				"</td><td><input type='text' class='form-control' style='width:120px;' readonly='readonly' id='diffTareWeigh' value='' />"+
				"</td><td><input type='text' class='form-control' style='width:120px;' readonly='readonly' id='diffRoughWeigh' value='' />"+
				"</td><td><input type='text' class='form-control' style='width:120px;' readonly='readonly' id='diffNetWeigh' value='' />"+
				'</td>'+
				"</tr>"+
				"</tr><tr><td colspan=2>罐号"+
				"</td><td><input type='text' class='form-control intention' maxlength='5' style='width:120px;' id='tankCodem' data='"+data.tankId+"' value='"+util.isNull(data.tankName)+"' />"+
				"</td><td>发货口"+
				"</td><td><input type='text' class='form-control' style='width:120px;' id='inPort' value='"+util.isNull(data.inPort)+"' />"+
				"</td>" +
				"</tr><tr><td colspan=2>入库时间"+
				"</td><td><div class='input-group inTime'  >"+
				"<div class='col-md-4 col-md-offset-2' style='padding-right: 0px;'>"+
				"<input style='text-align: right; border-right: 0;' class='form-control form-control-inline date-picker col-md-8' type='text' />"+
				"</div>"+
				"<div class='col-md-3' style='padding-left: 0px;'>"+
				"<input style='border-left: 0;' type='text' class='form-control col-md-4  timepicker timepicker-24'>"+
				"</div>"+
				"</div>"+
				"</td><td>出库时间"+
				"</td><td><div class='input-group outTime'>"+
				"<div class='col-md-4 col-md-offset-2' style='padding-right: 0px;'>"+
				"<input style='text-align: right; border-right: 0;'  class='form-control form-control-inline date-picker col-md-8' type='text' />"+
				"</div>"+
				"<div class='col-md-3' style='padding-left: 0px;'>"+
				"<input style='border-left: 0;' type='text' class='form-control col-md-4  timepicker timepicker-24'>"+
				"</div>"+
				"</div>"+
				"</td></tr>"+
				"<tr><td >备注 "+
				"</td><td colspan=2><div class='form-group'>"+
				"<textarea class='form-control ' style='width:70%' maxlength='100' rows='1' id='description'>"+util.isNull(data.description)+"</textarea>"+
				"</div></td></tr>"
				}else{
					htmlStr = "<tr><td colspan=2>称重量"+
					"</td><td><input type='hidden'  id='wbId' /><input type='text' class='form-control' style='width:120px;'  id='inWeigh'  />"+
					"</td><td><input type='text' class='form-control' style='width:120px;'  id='outWeigh'  />"+
					"</td><td><input type='text' class='form-control' style='width:120px;' readonly='readonly'  id='netWeigh'  />"+
					'</td>'+
					"</tr><tr><td colspan=2>罐号"+
					"</td><td><input type='text' class='form-control' maxlength='5' style='width:120px;' id='tankCodem'   />"+
					"</td><td>发货口"+
					"</td><td><input type='text' class='form-control' style='width:120px;' id='inPort'  />"+
					"</td>"+
					"</tr><tr><td colspan=2>入库时间"+
					"</td><td><div class='input-group inTime' >"+
					"<div class='col-md-4 col-md-offset-2' style='padding-right: 0px;'>"+
					"<input style='text-align: right; border-right: 0;' class='form-control form-control-inline date-picker col-md-8' type='text' />"+
					"</div>"+
					"<div class='col-md-3' style='padding-left: 0px;'>"+
					"<input style='border-left: 0;' type='text' class='form-control col-md-4  timepicker timepicker-24'>"+
					"</div>"+
					"</div>"+
					"</td><td>出库时间"+
					"</td><td><div class='input-group outTime'>"+
					"<div class='col-md-4 col-md-offset-2' style='padding-right: 0px;'>"+
					"<input style='text-align: right; border-right: 0;'  class='form-control form-control-inline date-picker col-md-8' type='text' />"+
					"</div>"+
					"<div class='col-md-3' style='padding-left: 0px;'>"+
					"<input style='border-left: 0;' type='text' class='form-control col-md-4  timepicker timepicker-24'>"+
					"</div>"+
					"</div>"+
					"</td></tr>"+
					"<tr><td >备注 "+
					"</td><td colspan=2><div class='form-group'>"+
					"<textarea class='form-control ' maxlength='100' rows='1' id='description'>"+util.isNull(data.description)+"</textarea>"+
					"</div></td></tr>" ;
				}
				$("#vehicleWeigh").empty().append(htmlStr);
				util.initTimePicker($("#vehicleWeigh"),true);
				if(data.id!=0){
				util.initTimeVal($(".inTime"),data.intoTimeStr,true);
				util.initTimeVal($(".outTime"),data.outTimeStr,true);
				$(".inTime").attr('data',data.intoTime);
				$(".outTime").attr('data',data.outTime);
				$("#actualTareWeigh,#actualRoughWeigh").change(function(){
					$("#actualNetWeigh").val(util.FloatSub($("#actualRoughWeigh").val(),$("#actualTareWeigh").val()));
					$("#diffTareWeigh").val(util.FloatSub($("#actualTareWeigh").val(),$("#inWeigh").val()));
					$("#diffRoughWeigh").val(util.FloatSub($("#actualRoughWeigh").val(),$("#outWeigh").val()));
					$("#diffNetWeigh").val(util.FloatSub($("#actualNetWeigh").val(),$("#netWeigh").val()));
				});
				if(data.actualTareWeight&&data.actualRoughWeight&&parseFloat(util.FloatAdd(data.actualTareWeight,data.actualRoughWeight))!=0){
				$("#actualNetWeigh").val(util.FloatSub($("#actualRoughWeigh").val(),$("#actualTareWeigh").val()));
				$("#diffTareWeigh").val(util.FloatSub($("#actualTareWeigh").val(),$("#inWeigh").val()));
				$("#diffRoughWeigh").val(util.FloatSub($("#actualRoughWeigh").val(),$("#outWeigh").val()));
				$("#diffNetWeigh").val(util.FloatSub($("#actualNetWeigh").val(),$("#netWeigh").val()));
				}
				
				}else{
				 util.initTimeVal($(".inTime,.outTime"),util.currentTime(1),true);
				 $("#inWeigh,#outWeigh").change(function(){
						$("#netWeigh").val(util.FloatSub($("#outWeigh").val(),$("#inWeigh").val()));
					});
				}
				$(".timepicker-24:focus").blur();
				
			}else if(oils==1){//油品
				$("#change").text("切换到称重计量");
				$("#weigh_div").hide();
				$("#measure_div").show();
				if(data!=null&&data.id!=0){
				htmlStr = "<tr><td >表返重量（吨）"+
				"</td><td><input type='hidden' id='wbId' value='"+data.id+"' /><input type='hidden' data='"+data.status+"' id='wbStatus'/><input type='text' class='form-control' style='width:120px;' readonly='readonly'    id='measureWeigh'  value='"+util.isNull(data.measureWeigh)+"'/>"+
				"</td>"+
				/*<td>发运数"+
				"</td><td><input type='text' class='form-control' style='width:120px;'  id='deliveryNum' value='"+util.isNull(data.deliveryNum)+"' />"+
				'</td>'+*/
				"</tr><tr><td >实发量"+
				"</td><td><input type='text' class='form-control' maxlength='5' style='width:120px;' id='actualNum' value='"+util.isNull(data.deliveryNum)+"'   />"+
				"</td><td>调整量"+
				"</td><td><input type='text' class='form-control' style='width:120px;' id='afUpNum' value='"+util.isNull(data.afUpNum)+"'  />"+
				"</td>"+
				"</tr><tr><td >罐号"+
				"</td><td><input type='text' class='form-control' maxlength='5' style='width:120px;' id='tankCodem'  data='"+data.tankId+"' value='"+util.isNull(data.tankName)+"'  />"+
				"</td><td>发货口"+
				"</td><td><input type='text' class='form-control' style='width:120px;' id='inPort' value='"+util.isNull(data.inPort)+"' />"+
				"</td>"+
				"</tr><tr><td >出库时间"+
				"</td><td><div class='input-group outTime'>"+
				"<div class='col-md-4 col-md-offset-2' style='padding-right: 0px;'>"+
				"<input style='text-align: right; border-right: 0;'  class='form-control form-control-inline date-picker col-md-8' type='text' />"+
				"</div>"+
				"<div class='col-md-3' style='padding-left: 0px;'>"+
				"<input style='border-left: 0;' type='text' class='form-control col-md-4  timepicker timepicker-24'>"+
				"</div>"+
				"</div>"+
				"</td></tr>"+
				"<tr><td >备注 "+
				"</td><td colspan=2><div class='form-group'>"+
				"<textarea class='form-control ' maxlength='100' rows='1' id='description'>"+util.isNull(data.description)+"</textarea>"+
				"</div></td></tr>" ;
				}else{
					
					htmlStr = "<tr><td colspan=2>表返重量（吨）"+
					"</td><td><input type='hidden' id='wbId' value='"+data.id+" /><input type='text' class='form-control' style='width:120px;' readonly='readonly' id='measureWeigh'  />"+
					"</td>"+
					/*<td>发运数"+
					"</td><td><input type='text' class='form-control' style='width:120px;'  id='deliveryNum' />"+
					'</td>'+*/
					"</tr><tr><td colspan=2>实发量"+
					"</td><td><input type='text' class='form-control' maxlength='5' style='width:120px;' id='actualNum'   />"+
					"</td>"+
					"</tr><tr><td colspan=2>罐号"+
					"</td><td><input type='text' class='form-control' maxlength='5' style='width:120px;' id='tankCodem'    />"+
					"</td><td>发货口"+
					"</td><td><input type='text' class='form-control' style='width:120px;' id='inPort'  />"+
					"</td>"+
					"</tr><tr><td colspan=2>出库时间"+
					"</td><td><div class='input-group outTime'>"+
					"<div class='col-md-4 col-md-offset-2' style='padding-right: 0px;'>"+
					"<input style='text-align: right; border-right: 0;'  class='form-control form-control-inline date-picker col-md-8' type='text' />"+
					"</div>"+
					"<div class='col-md-3' style='padding-left: 0px;'>"+
					"<input style='border-left: 0;' type='text' class='form-control col-md-4  timepicker timepicker-24'>"+
					"</div>"+
					"</div>"+
					"</td></tr>"+
					"<td >备注 "+
					"<tr><td colspan=2><div class='form-group'>"+
					"<textarea class='form-control ' maxlength='100' rows='1' id='description'>"+util.isNull(data.description)+"</textarea>"+
					"</div></td></tr>" ;
				}
				$("#vehicleMeasure").empty().append(htmlStr);
				
				util.initTimePicker($("#vehicleMeasure"),true);
				if(data.id!=0){
					util.initTimeVal($(".outTime"),data.outTimeStr,true);
					$(".outTime").attr('data',data.outTime);
					}else{
					 util.initTimeVal($(".inTime,.outTime"),util.currentTime(1),true);
					}
					$(".timepicker-24:focus").blur();
				
			}
		util.urlHandleTypeahead("/park/list",$('#inPort'));
		util.urlHandleTypeahead("/baseController/getTankCode",$('#tankCodem'),'code');
	};
	
/*****************************************************************************************************/	
	//数量确认
	function checkAmount(status,id){
		initCheckAmountCtr();
		initCheckAmount(id);
	};
	
	function initCheckAmountCtr(){
		
		$("#passButton,#notpassButton").unbind('click').click(function(){
			var status=util.isNull($(this).attr('data'),1);
			//校验是否称重
			if(util.isNull($("#weighStatus").val(),1)!=3){
			    $('body').message({
			    	type:'warning',
			    	content:'请前往车发称重进行称重'
			    });
				return false;
			}
			if(status!=2&&status!=3){
				$('body').message({
			    	type:'error',
			    	content:'确认失败'
			    });
				return false;
			}
			
			
			var goodsLogList=new Array();
			var nGoodsCurrent=0;
			var nActualNum=0;
			var nAfUpNum=0;
			var isPass=true;
			$("#checkAmountList>tr").each(function(){
				$itemTr=$(this);
				
				goodsLogList.push({
					id:util.isNull($itemTr.find(".goodsLogId").text(),1),
					goodsId:util.isNull($itemTr.find(".goodsId").text(),1),
					createTime:util.isNull($itemTr.find(".createTime").text(),1),
					actualNum:util.isNull($itemTr.find(".actualNum").text(),1),
					afUpNum:util.isNull($itemTr.find(".afUpNum").text(),1),
					afDiffNum:util.isNull($itemTr.find(".afDiffNum").text(),1)
				});
				nGoodsCurrent=util.isNull($itemTr.find(".goodsCurrent").text(),1);
				nActualNum=util.isNull($itemTr.find(".actualNum").text(),1);
				nAfUpNum=util.isNull($itemTr.find(".afUpNum").text(),1);
				if(parseFloat(util.FloatSub(util.FloatAdd(nGoodsCurrent,nActualNum,3),nAfUpNum,3))<0){
					$("body").message({
                         type:'error',
						content:'调整量超过实际结存量'
					});
					isPass=false;
					return;
				}});
				if(isPass){
					var approve = {
							"status":status,
							"comment":$("#comment").val(),
							"refId":util.isNull($("#tabId").text(),1),
							"id":util.isNull($("input[name=approveId]").val(),1)
					};
					var vehiclePlan ={
							"goodsLoglist":goodsLogList,
							"approve":approve
					};
					$.ajax({
						type : "post",
						url : config.getDomain()+"/outboundtruckserial/confirmData",
						data:{
							"vehiclePlanList":JSON.stringify(vehiclePlan),
							"trainId":util.isNull($("#tabId").text(),1)
						},
						dataType:"json",
						success:function(data){
							util.ajaxResult(data,(status==2?'通过':'不通过'),function(){
								initCheckAmount(util.isNull($("#tabId").text(),1));
							});
						}
					});	
				}
				
			});
		
	};

	function initCheckAmount(id){
		
        if(id){
		$.ajax({
			type:"post",
			url:config.getDomain()+"/outboundtruckserial/getCheckMountMsg",
			data:{"trainId":id},
			dataType : "json",
			async:false,
			success:function(data) {
				util.ajaxResult(data,'获取确认信息',function(ndata){
					if(ndata&&ndata.length>0){
						initCheckAmountHtml(ndata[0]);
						initApproveInfo(ndata[1],util.isNull(ndata[0][0].isChange,1));
					}
				},true);
			}});
        }
	};
	
	function initCheckAmountHtml(data){
		if(data&&data.length>0){
			var caList=data;
			var caSize=caList.length;
			//是否称重完成
			if(util.isNull(caList[0].weighStatus,1)==3){
				$("#weighStatus").val(3);
			}
			//是否修改了调整量
			if(util.isNull(caList[0].isChange,1)==1){
				$(".approveInfo").hide();
				$(".changeNum,#passButton,#notpassButton,.approveDiv").show();
			}else{
				
				$(".changeNum").hide();
				$("#passButton,#notpassButton").hide();
			}
			var html='';
			for(var i=0;i<caSize;i++){
				html+='<tr><td><label class="hidden goodsLogId" >'+caList[i].id+'</label>'+
				       '<label class="hidden goodsId" >'+caList[i].goodsId+'</label>'+
				       '<label class="hidden createTime" >'+caList[i].createTime+'</label>'+
				       caList[i].serial+'</td>'+
					  '<td>'+caList[i].createTimeStr+'</td>'+
				      '<td>'+util.isNull(caList[i].ladingEvidence)+'</td>'+
				      '<td>'+util.isNull(caList[i].ladingClientName)+'</td>'+
				      '<td>'+caList[i].goodsCode+'</td>'+
				      '<td>'+util.isNull(caList[i].ladingCode)+'</td>'+
				      '<td>'+caList[i].productName+'</td>'+
				      '<td class="goodsCurrent">'+util.toDecimal3(caList[i].goodsCurrent,true)+'</td>'+
				      '<td>'+util.toDecimal3(caList[i].deliverNum,true)+'</td>'+
				      '<td class="actualNum">'+util.toDecimal3(caList[i].actualNum,true)+'</td>';
                        if(util.isNull(caList[i].isChange,1)==1){
                        if(caList[i].afUpNum!=null&&caList[i].afDiffNum!=null){	
                 html+='<td class="afUpNum">'+util.toDecimal3(caList[i].afUpNum,true)+'</td>'+
                        '<td class="afDiffNum">'+util.toDecimal3(caList[i].afDiffNum,true)+'</td>';
                        }else{
                        	 html+='<td class="afUpNum">'+util.toDecimal3(caList[i].actualNum,true)+'</td>'+
                             '<td class="afDiffNum">0.000</td>';
                        }
                        }
				html+='</tr>'
			}
			$("#checkAmountList").empty().append(html);
		}
		
	};
	function initApproveInfo(data,isChange){
		if(data.length>0){
		$("input[name=status]").val(data[0].status) ;
		$("input[name=approveId]").val(data[0].id) ;
		$("#checkUser").val(data[0].checkUser) ;
		$("#checkTime").val(data[0].checkTime) ;
		$("#comment").val(data[0].comment);
		if(util.isNull(data[0].status,1)==2&&isChange==0){
			$(".approveInfo,.approveDiv").show();
		}
		}
	};
	//导出excel
	function exportExcel(){
		var url=config.getDomain()+'/outboundtruckserial/exportTruckExcel?1=1';
		if(util.isNull($("#trainId").attr('data'),1)!=0){
			url+="&trainId="+$("#trainId").attr('data');
		}
		if(util.isNull($("#serialNum").val())!=''){
			url+="&serialNum='"+$("#serialNum").val()+"'";
		}
		if(util.isNull($("#clientName").attr('data'),1)!=0){
			url+="&clientId="+$("#clientName").attr('data');
		}
		if(util.isNull($("#ladingEvidence").val())!=''){
			url+="&ladingEvidence='"+$("#ladingEvidence").val()+"'";
		}
		if(util.isNull($("#status").val(),1)!=0){
			url+="&status="+$("#status").val();
		}
		if(util.isNull(util.formatLong($("#startTime").val()+" 00:00:00"),1)!=-1){
			url+="&startTime="+util.formatLong($("#startTime").val()+" 00:00:00");
		}else{
			$(body).message({type:"warning",content:"请填写起始日期"});
			return ;
		}
		if(util.isNull(util.formatLong($("#endTime").val()+" 23:59:59"),1)!=-1){
			url+="&endTime="+util.formatLong($("#endTime").val()+" 23:59:59");
		}else{
			$(body).message({type:"warning",content:"请填写止计日期"});
			return ;
		}
		window.open(url);
	};
	
return {
	init:init,	
	initTab:initTab,
	exportExcel:exportExcel
}
}();