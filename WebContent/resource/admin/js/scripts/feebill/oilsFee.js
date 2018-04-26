var OilsFee=function(){
	var params;//查询条件
	var systemUserId;//登陆人id
	var systemUserName;//登陆人名称
	//初始化油品计算
	function initOilsFeeDialog(id){
		$.get(config.getResource()+ "/pages/inbound/storageFee/dialog_oilsfee.jsp").done(function(data) {
			var dialog = $(data);
			initDialogControl(dialog,id);
			if(id){
				initDialogMsg(dialog,id);
			}
			dialog.modal({
				keyboard : true
			});
		});
		
		
	};
	//初始化控件
	function initDialogControl(dialog,id){
		if(!id){
			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+"/initialfee/getcodenum",
				dataType:'json',
				success:function(data){
					config.unload();
					if(data.code=='0000'){
						dialog.find("#code").text(data.data[0].codeNum);
					}else{
						$('body').message({
							type:'error',
							content:'获取编号失败'
						});
					}
				}
			});
		}
		initFormIput(dialog);
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
		util.initTimePicker(dialog);
		dialog.find("#accountTime").datepicker("setDate",util.currentTime(0));
		dialog.find('[data-dismiss="modal"]').click(function(){
		dialog.remove();
		});
		//客户
		util.urlHandleTypeaheadAllData("/baseController/getClientName",dialog.find("#clientName"),function(item){return item.name+"["+item.code+"]";},undefined,function(data){
			if(data){
				dialog.find(".feeHead").val(data.name);
				dialog.find(".feeHead").attr('data',data.id);
			}else{
				dialog.find(".feeHead").val("");
				dialog.find(".feeHead").removeAttr('data');
			}
		});
		//货品
		util.urlHandleTypeahead("/product/select",dialog.find('#productName'),undefined,undefined,undefined);//货品
		//查询该货主的船发车发记录
		dialog.find("#checkOutBoundList").click(function(){
			var clientId=dialog.find("#clientName").attr("data");
			var productId=dialog.find('#productName').attr("data");
			var startTime=util.formatLong(dialog.find("#startTime").val()+" 00:00:00");
			var endTime=util.formatLong(dialog.find("#endTime").val()+" 23:59:59");
			if(clientId&&clientId!=0&&productId&&productId!=0&&startTime&&startTime!=-1&&endTime!=-1){
				 params={
					'clientId':clientId,
					'productId':productId,
					'startTime':startTime,
					'endTime':endTime,
					'type':1
				};
				var columns=[{title:"",render:function(item){
					return '<a href="javascript:void(0);" onclick="OilsFee.addTable(this,'+item.deliverType+')"><span class="fa fa-plus-square-o" style="font-size:17px"></span></a>';
				}},{title:"发货类型",name:"deliverTypeStr"},{title:"货品",name:"productName"},{title:"总发货数量",name:"actualNum"}];
				if(dialog.find('[data-role="otherFeeGrid"]').getGrid() != null)
					dialog.find('[data-role="otherFeeGrid"]').getGrid().destory();
				dialog.find('[data-role="otherFeeGrid"]').grid({
					columns :columns ,
					isShowIndexCol : false,
					isShowPages : true,
					searchCondition:params,
					url : config.getDomain()+"/initialfee/getoutboundtotallist",
					callback:function(){
						var data=dialog.find('[data-role="otherFeeGrid"]').getGrid().getAllItems();
						var total=0;
						var msg="油品结算：其中";
						if(data&&data.length>0){
							for(var i=0;i<data.length;i++){
								total=util.FloatAdd(total,data[i].actualNum);
								if(data[i].deliverType==2){
									msg+="船发"+data[i].actualNum+"吨，";
								}else if(data[i].deliverType==1){
									msg+="车发"+data[i].actualNum+"吨，";
								}
							}
						}
						dialog.find("#goodsTotal").val(total);
						if(util.isNull(dialog.find("#description").val(),1)==0){
							dialog.find("#description").val(msg);
						}
					}
			});
			}
		});
		
		  //添加费用项
		dialog.find(".addFeeCharge").click(function(){
			   $(getFeeChargeHtml(dialog.find('#clientName').attr('data'),dialog.find('#clientName').val())).insertBefore(dialog.find("#totalTr"));
			   dialog.find(".feeType").each(function(){
				   $this=$(this);
				   util.handleTypeahead([{'key':1,'value':'仓储费'},{'key':2,'value':'保安费'},{'key':3,'value':'包罐费'},{'key':4,'value':'超量费'},{'key':8,'value':'通过费'}],$this,'value','key',undefined,function(data,obj){
					   if(data)
						dialog.closest('tr').attr('data',data.key);
				   });
			   });
			   initTableControl(dialog);
		   });
		   //编辑
		   dialog.find(".editFeeCharge").click(function(){
					   $this=$(this);
					   var key=$this.attr('key');
					   if(key==0){
						   dialog.find('.removeFeeCharge').show();
						   $this.attr('key',1);
					   }else{
						   dialog.find('.removeFeeCharge').hide();
						   $this.attr('key',0);
					   }
				   });
		dialog.find("#save,#submit").click(function(){
			$this=$(this);
			var status=$this.attr('key');
			var id=util.isNull(dialog.find("#id").text(),1);
			var iFeeDto={
					'initialFee':{
						id:id==0?undefined:id,
						code:dialog.find("#code").text(),
						description:dialog.find("#description").val(),
						createTime:util.formatLong(util.currentTime(0)),
						accountTime:util.formatLong(dialog.find("#accountTime").val()),
						startTime:util.formatLong(dialog.find("#startTime").val()),
						endTime:util.formatLong(dialog.find("#endTime").val()),
						contractNum:dialog.find("#goodsTotal").val(),
						clientId:dialog.find('#clientName').attr('data'),
						productId:dialog.find('#productName').attr('data'),
						totalFee:dialog.find("#totalFee").text(),
						createUserId:systemUserId,
						isFinish:0,
						type:3,//油品结算单
						status:status
					}
			}
			var dataList=new Array();
			dialog.find('#feeTbody>tr').each(function(){
				$this=$(this);
				if($this.attr("id")!="totalTr"){
					if(util.isNum($this.find('.totalFee').val())&&util.isNum($this.find('.totalFee').val())!=0){
				var item={
						type:status,
						feeType:$this.find(".feeType").attr('data'),
						unitFee:util.isNum($this.find('.unitFee').val()),
						feeCount:util.isNum($this.find('.feeCount').val()),
						totalFee:util.isNum($this.find('.totalFee').val()),
						discountFee:util.isNum($this.find('.totalFee').val()),
						createTime:util.formatLong(util.currentTime(0)),
						feeHead:$this.find('.feeHead').attr('data'),
						clientId:dialog.find('#clientName').attr('data'),
						productId:dialog.find('#productName').attr('data'),
				};
				if($this.find('.feeId').attr('data')){
					item.id=$this.find('.feeId').attr('data');
				}
				dataList.push(item);}
				}
			});
			iFeeDto.feechargelist=dataList;
			
			$.ajax({
				type:'post',
				url:config.getDomain()+"/initialfee/addorupdate", 
			    dataType:"json",
			    data:{iFeeDto:JSON.stringify(iFeeDto)},
			    success:function(data){
			    	util.ajaxResult(data,status==1?'提交':'保存',function(ndata,nmap){
			    		var iniId=(id==0?nmap.id:id);
			    		initDialogMsg(dialog,iniId);
			    	});
			    }
		});
		});
	};
	//初始化数据
	function initDialogMsg(dialog,id){
		dialog.find("#id").text(id);
		 config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+"/initialfee/getoilsfeemsg",
				data:{id:id},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'获取基本信息',function(ndata){
                   if(ndata&&ndata.length==1){
   					dialog.find("#clientName").val(util.isNull(ndata[0].clientName)).attr("data",ndata[0].clientId);
   					dialog.find("#productName").val(util.isNull(ndata[0].productName)).attr("data",ndata[0].productId);;
   					dialog.find("#goodsTotal").val(util.isNull(ndata[0].contractNum,1));
   					dialog.find("#code").text(ndata[0].code);
   					dialog.find("#accountTime").val(util.getSubTime(ndata[0].accountTime,1));
   					dialog.find("#description").val(util.isNull(ndata[0].description));
   					dialog.find("#startTime").val(util.getSubTime(ndata[0].startTime,1));
   					dialog.find("#endTime").val(util.getSubTime(ndata[0].endTime,1));
   					dialog.find("#checkOutBoundList").click();
   					if(ndata[0].feecharge&&ndata[0].feecharge.length>0){
   						dialog.find("#feeTbody").empty();
   						dialog.find("#feeTbody").append(initFeeChargeVal(dialog,ndata[0].feecharge));	
						 initTableControl(dialog);
   					}
                 	if(ndata[0].status&&ndata[0].status==1){
                 		dialog.find("#save").text("回退");
                 		dialog.find("#submit").hide();
                 		dialog.find("#createUserId").text(util.isNull(ndata[0].createUserName)).attr('data',ndata[0].createUserId);
                 		dialog.find("#createUserDiv").show();
						}else{
							dialog.find("#save").text("保存");
							dialog.find("#submit").show();
							dialog.find("#createUserDiv").hide();
						}
                   }
					},true);
				}
			});
	};
	
	function addTable(obj,deliverType){
		$(obj).find('span').attr('class');
		if($(obj).find('span').attr('class')=='fa fa-plus-square-o'){
			$(obj).find('span').removeClass('fa fa-plus-square-o').addClass('fa fa-minus-square-o');
		var iTr=$(obj).closest('tr');
		var html='<tr class="detail"><td colspan="6">'
			+'<div class="col-md-12"><div data-role="detailOutBoundMsgGrid'+deliverType+'"></div>'
			+'</td></tr>';
	    $(html).insertAfter(iTr);
	    initDetailTable($(obj).closest('.modal-dialog'),deliverType);
		}else{
			$(obj).find('span').removeClass('fa fa-minus-square-o').addClass('fa fa-plus-square-o ');
			var iTr=$(obj).closest('tr');
			iTr.next('tr[class="detail"]').remove();
		}
	};
	
	function initDetailTable(dialog,deliverType){
		params.type=2;
		params.deliverType=deliverType;
		var columns=[{title:"车船号",name:"vsName"},{title:"出库时间",name:"createTime"},{title:"实发量",name:"actualNum"}];
		if(dialog.find('[data-role="detailOutBoundMsgGrid'+deliverType+'"]').getGrid() != null)
			dialog.find('[data-role="detailOutBoundMsgGrid'+deliverType+'"]').getGrid().destory();
		dialog.find('[data-role="detailOutBoundMsgGrid'+deliverType+'"]').grid({
			columns :columns ,
			isShowIndexCol : false,
			isShowPages : false,
			searchCondition:params,
			url : config.getDomain()+"/initialfee/getoutboundtotallist"
	});
	}
	//初始化table
	function initTableControl(obj){
		
		   /**自动计算*/
			$(obj).find(".feeIn").change(function(){
				$this=$(this);
				var nTr=$this.closest("tr");
				$(nTr).find('.totalFee').val(util.FloatMul($(nTr).find(".unitFee").val(),$(nTr).find(".feeCount").val(),2));
				var allTotalFee=0;
				$(obj).find(".totalFee").each(function(){
					$a=$(this);
					allTotalFee=util.FloatAdd(allTotalFee,$a.val(),2);
				});
				$(obj).find("#totalFee").text(allTotalFee);
			});
			/**自动计算*/
			$(obj).find(".feeAll").change(function(){
				var allTotalFee=0;
				$(obj).find(".totalFee").each(function(){
					$a=$(this);
					allTotalFee=util.FloatAdd(allTotalFee,$a.val(),2);
				});
				$(obj).find("#totalFee").text(allTotalFee);
			});
			
			 $(obj).find(".reFeeCharge").click(function(){
				  $this=$(this);
				  $nTr=$this.closest('tr');
				  var id=$nTr.find(".feeId").attr('data');
				  if(id){//存在id
					  $.ajax({
						  type:'post',
						  url:config.getDomain()+"/feecharge/delete", 
						  data:{id:id},
						  dataType:'json',
						  success:function(data){
							  $nTr.remove();
							  var allTotalFee=0;
								$(obj).find(".totalFee").each(function(){
									$a=$(this);
									allTotalFee=util.FloatAdd(allTotalFee,$a.val(),2);
								});
								$(obj).find("#totalFee").text(allTotalFee);
						  }
					  });
				  }else{
					  $nTr.remove();
					  var allTotalFee=0;
						$(obj).find(".totalFee").each(function(){
							$a=$(this);
							allTotalFee=util.FloatAdd(allTotalFee,$a.val(),2);
						});
						$(obj).find("#totalFee").text(allTotalFee);
				  } 
			   });
	};
	function getFeeChargeHtml(id,name){
		 var tHtml5="<tr class='feeTypeTd'><td style='text-align:center' ><input type='text'  style='width:100%;text-align:center' class='form-control feeType'></td>";
		 var tHtml6="<td style='text-align:center' ><input type='text' onkeyup='config.clearNoNum(this,2)' maxlength='20'  style='width:100%' class='form-control unitFee maskA feeIn' ></td>" +
			"<td style='text-align:center' ><input type='text' onkeyup='config.clearNoNum(this)' maxlength='16'  style='width:100%' class='form-control feeCount maskB feeIn'  ></td>" +
			"<td style='text-align:center' ><input type='text' onkeyup='config.clearNoNum(this,2)' maxlength='20'  style='width:100%' class='form-control totalFee maskC feeAll' ></td>"+
			"<td style='text-align:center' ><input type='text' style='width:100%' class='form-control feeHead maskC' value='"+name+"' data='"+id+"' ></td>"+
			"<td style='text-align:center;display:none' class='removeFeeCharge'><a class='btn btn-xs red reFeeCharge'  href='javascript:void(0)'><span title='移除' class='glyphicon glyphicon glyphicon-remove'></span></a></td></tr>";
			return tHtml5+tHtml6;
	}
	//初始化费用项数据
	function  initFeeChargeVal(obj,chargeData){
		var html="";
		var allTotalFee=0;//总费用
		var isShowBack=true;
		for(var i in chargeData){
			if(chargeData[i]&&chargeData[i].feeType){
				//如果有一个费用项生成账单，则无法回退
				if(chargeData[i].feebillId&&chargeData[i].feebillId!=0){
					isShowBack=false;
				}
			var unitFee=0,feeCount=0,totalFee=0;//单价，数据，总计
				if(chargeData[i].feeCount){
					feeCount=chargeData[i].feeCount;
				}
				if(chargeData[i].unitFee){
					unitFee=chargeData[i].unitFee;
				}
				if(chargeData[i].totalFee){
					totalFee=chargeData[i].totalFee;
				}else{
					totalFee=util.FloatMul(unitFee,feeCount,2);
				}
				allTotalFee=util.FloatAdd(allTotalFee,totalFee,2);
			html+="<tr  class='feeTypeTd' data="+chargeData[i].feeType+"><td style='text-align:center'class='feeId' data="+chargeData[i].id+" >"+StorageFee.feeTypeVal(chargeData[i].feeType)+"</td>"+
			"<td style='text-align:center' ><input type='text' onkeyup='config.clearNoNum(this,2)' maxlength='20'  style='width:100%' class='form-control unitFee maskA feeIn' value="+util.toDecimal2(unitFee,true)+" ></td>" +
			"<td style='text-align:center' ><input type='text' onkeyup='config.clearNoNum(this)' maxlength='16'  style='width:100%' class='form-control feeCount maskB feeIn' value="+util.toDecimal3(feeCount,true)+" ></td>" +
			"<td style='text-align:center' ><input type='text' onkeyup='config.clearNoNum(this,2)' maxlength='20'  style='width:100%' class='form-control totalFee maskC feeAll' value="+util.toDecimal2(totalFee,true)+" ></td>"+
			"<td style='text-align:center' ><input type='text' style='width:100%' class='form-control feeHead maskC' data="+chargeData[i].feeHead+" value="+util.isNull(chargeData[i].feeHeadName)+" ></td>"+
			"<td style='text-align:center;display:none;' class='removeFeeCharge'><a class='btn btn-xs red reFeeCharge'  href='javascript:void(0)'><span title='移除' class='glyphicon glyphicon glyphicon-remove'></span></a></td></tr>";
		}}
		if(isShowBack){
			$(obj).find('#save').show();
		}
		
		html+="<tr id='totalTr'><td colspan='5'>&nbsp;&nbsp;&nbsp;合计金额：<label id='totalFee'>"+util.toDecimal2(allTotalFee,true)+"</label>&nbsp;元</td></tr>";
		return html;
	};
	return {
	initOilsFeeDialog:initOilsFeeDialog,
	addTable:addTable
	};
}();