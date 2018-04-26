var DockFeeBillDialog=function(){
	var systemUserId,systemUserName;
	
/****************************************************************************************/
	function dialogDockFillBillList(index,clientName,tradeType){
		$.get(config.getResource()+ "/pages/feebill/dialog_feebilllist.jsp").done(function(data){
			var dialog = $(data);
			var columns=[{title:'账单号',render:function(item){
				return '<a href="javascript:void(0)" onclick="DockFeeBillDialog.insertToFeeBill('+item.id+','+item.totalFee+','+item.feeType+','+item.accountBillId+','+index+')"  >'+item.code+'</a>'
			}},{title:'开票抬头',name:'feeHead'}];
			if(dialog.find('[data-role="feeBillListTable"]').getGrid()!=null)
				dialog.find('[data-role="feeBillListTable"]').getGrid().destory();
			if(clientName){
			dialog.find('[data-role="feeBillListTable"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : true,
		       url:config.getDomain()+"/dockfeebill/list?billStatus=0" 
			});
			}
			dialog.modal({keyboard : true});
		});
	};
	
	function insertToFeeBill(feebillId,feebillTotalFee,feeType,accountBillId,index){
		var feeChargeData=$('[data-role="feeDetailGrid'+index+'"]').getGrid().selectedRows();
		var totalFee=$("#choiceTotalFee"+index).text();
		$('body').confirm({
			content:'确定要添加到已有账单中吗',
			callBack:function(){
				var dfDto={};
				dfDto.dockfeebill={
						id:feebillId,
						totalFee:util.FloatAdd(feebillTotalFee,totalFee,2),//总价
				}
				dfDto.feechargeList=feeChargeData;
				if(util.isNull(feeType,1)==1){
				dfDto.accountBillList=null;
//					[{
//					id:accountBillId,
//					accountFee:util.FloatAdd(feebillTotalFee,totalFee,2),
//					dockfeebillId:feebillId
//				}];
					}
				config.load();
				$.ajax({
					type:'post',
					url:config.getDomain()+'/dockfeebill/addorupdate',
					dataType:'json',
					data:{"dfDto":JSON.stringify(dfDto)},
					success:function(data){
						util.ajaxResult(data,'添加',function(ndata,nmap){
							$('.addLivingFeeBill').remove();
							$('[data-role="feeDetailGrid'+index+'"]').getGrid().refresh();
							dialogDockFeeBill(nmap.id);
						});
					}
				});
			}
			});
	};
/****************************************************************************************/	
	function dialogDockFeeBill(id,index){
		if(id||!id&&checkIsHasItemFeeCharge(index)){
		$.get(config.getResource()+ "/pages/feebill/dialog_dockfeebill.jsp").done(function(data) {
			var dialog = $(data);
			initDockFeeBillCtr(dialog);
			if(id)
				initDockFeeBillMsg(dialog,id);
			else
				initDockFeeBillBaseMsg(dialog,index);
		});
		}
	};
	
	function checkIsHasItemFeeCharge(index){
		var	feeData=$('[data-role="feeDetailGrid'+index+'"]').getGrid().selectedRows();	
		if(feeData==undefined||feeData.length==0){
			$('body').message({type:'warning',content:'请选择一个费用项'});
			return false;
		}else{
			return true;
		}
	};
/*******************************************************************/	
	function initDockFeeBillCtr(dialog){
		initFormIput(dialog);
		getSystemUserMsg();
		util.initTimePicker(dialog);
		dialog.modal({keyboard:true});
		dialog.find(".save,.hideDiv").hide();
		dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
		dialog.find("#fundsTime,#accountTime,#billingTime").datepicker('setDate',util.currentTime(0));
		dialog.find("#unTaxFee,#taxRate").keyup(function(){
			if(util.isNull(dialog.find("#unTaxFee").val(),1)!=0&&util.isNull(dialog.find("#taxRate").val(),1)!=0)
				dialog.find("#taxFee").val(util.FloatMul(util.isNull(dialog.find("#unTaxFee").val(),1),util.isNull(dialog.find("#taxRate").val(),1),2));
		});
		
		dialog.find(".save").click(function(){
			var status=$(this).attr('key');
			if(validateFeeBill(dialog,status)){
				doFeeBillSubmit(dialog,status);
			}
		});
		
		dialog.find("#back").click(function(){

			var id=dialog.find("#id").text();
			$.get(config.getResource()+ "/pages/feebill/reback.jsp").done(function(data) {
				var mdialog = $(data);
				initFormIput(mdialog);
				mdialog.find("#btnSubmit").click(function(){
					$.ajax({
						type:'post',
						url:config.getDomain()+"/dockfeebill/rollback",
						data:{id:id,status:mdialog.find("#backStatus").val()},
						dataType:'json',
						success:function(data){
							util.ajaxResult(data,'回退',function(ndata){
								mdialog.remove();
								initDockFeeBillMsg(dialog,id);
								if($('[data-role="dockFeeBillGrid"]'))
           						 util.updateGridRow($('[data-role="dockFeeBillGrid"]'),{id:id,url:'/dockfeebill/list'});
							});
						}
					});
				});
				mdialog.modal({
					keyboard : true
				});
			});
		});
		
		dialog.find("#openFundsTable").click(function(){
			$this=$(this);
			var key=$this.attr('key');
			var id=dialog.find("#id").text();
			if(id&&key==0){
				showFundsTable(dialog,id);
				$this.attr('key',1);
			}else{
				dialog.find('[data-role="fundsDetailTable"]').hide();
				$this.attr('key',0);
			}
		});
		dialog.find("button[key='5']").click(function(){
			var $this=$(this);
			if($this.text()=='开票修改'){
				$this.text('确认开票');
				dialog.find(".billingCodeDiv input").removeAttr('disabled');
			}else{
				var status=$(this).attr('key');
				if(validateFeeBill(dialog,status)){
					doFeeBillSubmit(dialog,status);
				}
			}
		});
	};
	
	function validateFeeBill(dialog,status){
		var feebillId=util.isNull(dialog.find('#id').text(),1);
		switch(parseInt(status)){
		case 0:
			return config.validateForm(dialog.find(".feeHeadDiv"));
		case 1:
			return config.validateForm(dialog.find(".feeHeadDiv"));
		case 2:
			return true;
		case 3:
			return true;
		case 4:
			if(config.validateForm(dialog.find(".feeDiv"))){
				if(util.isNull(dialog.find("#fundsTotalFee").val(),1)>util.isNull(dialog.find("#unAccountFee").text(),1)){
					$('body').message({type:'warning',content:'本次到账不合理'});
					return false;
				}else 
					return true;
			}else
				return false;
		case 5:
			if(feebillId==0){
				$('body').message({type:'warning',content:'请先保存'});
				return false;
			}
			return config.validateForm(dialog.find(".billingCodeDiv"));
		default:
			return true;
		}
	};
	
	function getFeeBillData(dialog,status){
		var dfDto={};
		var resultStr;
		var feebillId=util.isNull(dialog.find('#id').text(),1);
		switch(parseInt(status)){
		case 0:
			dfDto.dockfeebill={
							id:feebillId==0?undefined:feebillId,
							feeType:dialog.find("#feeBillType").text(),
							code:dialog.find("#code").text(),
							feeHead:dialog.find("#feeHeadName").val(),
							totalFee:dialog.find("#totalFee").text(),//总价
							description:dialog.find("#description").val(),
							accountTime:util.formatLong(dialog.find("#accountTime").val()),
							createUserId:systemUserId,
							createTime:util.formatLong(util.currentTime(0)),
	                        status:status					
					}
			dfDto.feechargeList=getFeeChargeData(dialog,dialog.find("#fBChargeList").getGrid().getAllItems());
			if(util.isNull(dialog.find("#feeBillType").text(),1)==1){
			dfDto.accountBillList=[{
				id:util.isNull(dialog.find("#accountBillId").text(),1),
				accountUserId:systemUserId,
				accountTime:util.formatLong(util.currentTime(0)),
				accountFee:dialog.find("#totalFee").text(),
				dockfeebillId:feebillId==0?undefined:feebillId
			}];}
			dfDto.resultStr="保存";
			break;
		case 1:
			dfDto.dockfeebill={
				id:feebillId==0?undefined:feebillId,
				feeType:dialog.find("#feeBillType").text(),
				code:dialog.find("#code").text(),
				feeHead:dialog.find("#feeHeadName").val(),
				totalFee:dialog.find("#totalFee").text(),//总价
				description:dialog.find("#description").val(),
				accountTime:util.formatLong(dialog.find("#accountTime").val()),
				createUserId:systemUserId,
				createTime:util.formatLong(util.currentTime(0)),
                status:status					
					}
			dfDto.feechargeList=getFeeChargeData(dialog,dialog.find("#fBChargeList").getGrid().getAllItems());
//			if(util.isNull(dialog.find("#feeBillType").text(),1)==1){
//			dfDto.accountBillList=[{
//				id:util.isNull(dialog.find("#accountBillId").text(),1),
//				accountUserId:systemUserId,
//				accountTime:util.formatLong(util.currentTime(0)),
//				accountFee:dialog.find("#totalFee").text(),
//				dockfeebillId:feebillId==0?undefined:feebillId
//			}];}
			dfDto.resultStr="提交";
			break;
		case 2:
			dfDto.dockfeebill={
				id:feebillId,
				reviewUserId:systemUserId,
				reviewTime:util.formatLong(util.currentTime(0)),
				comment:dialog.find("#comment").val(),
				status:status};
			dfDto.resultStr="通过";
			break;
		case 3:
			dfDto.dockfeebill={
				id:feebillId,
				reviewUserId:systemUserId,
				reviewTime:util.formatLong(util.currentTime(0)),
				comment:dialog.find("#comment").val(),
				status:status};
			dfDto.resultStr="不通过";
			break;
		case 4:
			dfDto.dockfeebill={id:feebillId};
			if(util.isNull(dialog.find("#fundsTotalFee").val(),1)==util.isNull(dialog.find("#unAccountFee").text(),1))
				dfDto.dockfeebill.fundsStatus=2;
			else
				dfDto.dockfeebill.fundsStatus=1;
			
			dfDto.accountBillList=[{
				accountUserId:systemUserId,
				accountTime:util.formatLong(dialog.find("#fundsTime").val()),
				accountFee:dialog.find("#fundsTotalFee").val(),
				dockfeebillId:feebillId==0?undefined:feebillId
			}];
			dfDto.resultStr="到账";
			break;
		case 5:
			dfDto.dockfeebill={
				id:feebillId==0?undefined:feebillId,
				billingCode:dialog.find("#billingCode").val(),
				billingTime:util.formatLong(dialog.find("#billingTime").val()),
				unTaxFee:dialog.find("#unTaxFee").val(),
				taxRate:dialog.find("#taxRate").val(),
				taxFee:dialog.find("#taxFee").val(),
				billingStatus:1,
				billingContent:dialog.find("#billingContent").val(),
				billingUserId:systemUserId,
		}
			dfDto.resultStr="开票";
			break;
		default:
			break;
		}
		return dfDto;
	};
	
	function getFeeChargeData(dialog,feeChargeData){
		if(feeChargeData){
			for(var i in feeChargeData){
				if(dialog)
				feeChargeData[i].discountFee=dialog.find('input[key="'+feeChargeData[i].id+'"]').val();
			}
		}
		return feeChargeData;
	};
	
	function doFeeBillSubmit(dialog,status){
		
		var feeBillData=getFeeBillData(dialog,status);
		console.log(JSON.stringify(feeBillData));
		$.ajax({
			type:'post',
			url:config.getDomain()+'/dockfeebill/addorupdate',
			dataType:'json',
			data:{"dfDto":JSON.stringify(feeBillData)},
			success:function(data){
				util.ajaxResult(data,feeBillData.resultStr,function(ndata,nmap){
					initDockFeeBillMsg(dialog,nmap.id);
					if($('[data-role="dockFeeBillGrid"]'))
  						 util.updateGridRow($('[data-role="dockFeeBillGrid"]'),{id:nmap.id,url:'/dockfeebill/list'});
				});
			}
		});
		
	};
	
	function showFundsTable(dialog,id){
		var columns=[{title:'到账确认人',name:'accountUserName'},{title:'到账日期',render:function(item){
			return util.getSubTime(item.accountTimeStr,1);
		}},{title:'到账金额(元)',name:'accountFee'},{title:'操作',render:function(item){
			 return  '<div style="width:100px;" class="input-group-btn"><a href="javascript:void(0)" '+
			 ' style="margin:0 4px" data='+item.accountUserId+'  onclick="DockFeeBillDialog.updateAccountFee(this,'+
			 item.id+','+id+')" class="btn btn-xs blue" ><span class="glyphicon glyphicon-edit" title="修改"></span></a>'+
	         '<a href="javascript:void(0)" onclick="DockFeeBillDialog.deleteAccountFee(this,'+item.id+','+id+
	         ')" style="margin:0 4px" class="btn btn-xs red " ><span class="glyphicon glyphicon-remove" title="删除"></span></a></div>';
		}}];
		dialog.find('[data-role="fundsDetailTable"]').show();
		if(dialog.find('[data-role="fundsDetailTable"]').getGrid()!=undefined)
			dialog.find('[data-role="fundsDetailTable"]').getGrid().destory();
			
		dialog.find('[data-role="fundsDetailTable"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			searchCondition:{dockfeebillId:id},
	        url:config.getDomain()+"/accountbilllog/list"
		});
	};
/*******************************************************************/		
	function initDockFeeBillMsg(dialog,id){
		dialog.find(".hideDiv,.save").hide();
		if(id){
			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+'/dockfeebill/getDockFeeBillMsg',
				data:{id:id},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,"获取账单明细",function(ndata){
						if(ndata&&ndata.length>0){
							var msg=ndata[0];
							dialog.find("#id").text(msg.id);
	    					dialog.find("#code").text(msg.code);
	    					dialog.find("#accountTime").datepicker('setDate',util.getSubTime(msg.accountTimeStr,1));
	    					dialog.find('#feeHeadName').val(msg.feeHead).attr('data',msg.feeHead);
	    					dialog.find('#unTaxFee').val(util.isNull(msg.unTaxFee));
	    					dialog.find('#taxRate').val(util.isNull(msg.taxRate));
	    					dialog.find('#taxFee').val(util.isNull(msg.taxFee));
	    					dialog.find('#feeBillType').text(msg.feeType);
	    					dialog.find("#billingCode").val(util.isNull(msg.billingCode));
	    					if(msg.billingTimeStr&&msg.billingTimeStr.length>0)
	    					dialog.find("#billingTime").datepicker('setDate',util.getSubTime(msg.billingTimeStr,1));
	    					
	    					dialog.find("#accountBillId").text(msg.accountBillId);
	    					dialog.find("#accountFee").text(util.isNull(msg.accountTotalFee));
	    					dialog.find("#unAccountFee").text(util.FloatSub(msg.totalFee,util.isNull(msg.accountTotalFee,1),2))
	    					.attr('data',util.FloatSub(msg.totalFee,util.isNull(msg.accountTotalFee,1),2));
	    					dialog.find("#description").val(msg.description);
	    					dialog.find("#comment").val(util.isNull(msg.comment));
	    					dialog.find("#createUserName").text(util.isNull(msg.createUserName));
	    					dialog.find("#reviewUserId").text(util.isNull(msg.reviewUserName));
	    					dialog.find("#reviewTime").text(util.getSubTime(msg.reviewTimeStr,1));
	    					dialog.find("#fundsUserId").text(util.isNull(msg.fundsUserName));
	    					if(msg.fundsTimeStr&&msg.fundsTimeStr.length>0)
	    						dialog.find("#fundsTimeStr").text(util.getSubTime(msg.fundsTimeStr,1));
	    					dialog.find("#billingUserId").text(util.isNull(msg.billingUserName));
	    					dialog.find("#billingTimeStr").text(util.getSubTime(msg.billingTimeStr,1));
	    					initFeeChargeGrid(dialog,msg.feeChargeList,function(){
	    						if(msg.status!=0&&msg.status!=3)
	    							dialog.find('th[index="7"],td[index="7"]').addClass('hidden');
	    					});
							showFeeBillCtr(dialog,msg.status,msg.fundsStatus,msg.billingStatus);
							if(parseFloat(util.FloatSub(msg.totalFee,util.isNull(msg.accountTotalFee,1),2))<=0)
	    						dialog.find(".feeDiv").hide();
	    					
						}},true);
				}});
		}
	};
	
	function showFeeBillCtr(dialog,status,fundsStatus,billingStatus){
		if(status==0||status==3){
			dialog.find(".billingCodeDiv,button[key='0'],button[key='1']").show();
		}else if(status==1){
			dialog.find(".billingCodeDiv,.commentDiv,button[key='2'],button[key='3']").show();
			dialog.find("#description,#feeHeadName").attr('disabled','disabled');
		}else if(status==2){
			dialog.find(".createDiv,.reviewDiv,.billingCodeDiv,.commentDiv,#back").show();
			dialog.find("#comment,#description,#feeHeadName").attr('disabled','disabled');
			dialog.find('th[index="7"],td[index="7"]').addClass('hidden');
		}
		if(fundsStatus==0)
			dialog.find("button[key='4'],.feeDiv").show();
		else if(fundsStatus==1)
			dialog.find("button[key='4'],.fundsDiv,.feeDiv").show();
		else if(fundsStatus==2)
			dialog.find(".fundsDiv,.feeDiv").show();
		
		if(billingStatus==0){
			dialog.find("button[key='5']").text("确认开票");
		dialog.find(".billingDiv").show();
		}else{
			dialog.find("button[key='5']").text("开票修改");
			dialog.find(".billingDiv").show();
			dialog.find(".billingCodeDiv input").attr('disabled','disabled');
		}
	};
/*****************************************************************************************/	
	function initDockFeeBillBaseMsg(dialog,index){
		getCodeNum(dialog);
		showFeeBillCtr(dialog,0,0,0);
		var dockFeeData=$('[data-role="feeDetailGrid'+index+'"]').getGrid().selectedRows();
		
		initFeeChargeGrid(dialog,dockFeeData,function(totalFee,hasAccountFee){
			dialog.find('th[index="7"],td[index="7"]').addClass('hidden');
			dialog.find('#feeBillType').text(dockFeeData[0].billType);//发票类型
			 dialog.find('#feeHeadName').val(dockFeeData[0].clientName);//开票抬头
	         dialog.find("#unAccountFee").text(util.FloatSub(totalFee,hasAccountFee,2)).attr('data',util.FloatSub(totalFee,hasAccountFee,2));//未到账
	         dialog.find("#accountFee").text(util.toDecimal2(hasAccountFee,true));//到账
	         if(util.isNull(util.FloatSub(totalFee,hasAccountFee,2),1)==0)
	        	 dialog.find(".feeDiv").hide();
		});
	};
	
	function initFeeChargeGrid(dialog,dockFeeData,callback){
		if(dialog.find("#fBChargeList").getGrid())
			dialog.find("#fBChargeList").getGrid().destory();
		dialog.find("#fBChargeList").grid({
			identity:'id',
			columns:getFeeBillColumns(),
			isShowIndexCol:false,
			isShowPages:false,
			isUserLocalData:true,
			localData:dockFeeData,
			callback:function(){
				if(dockFeeData&&dockFeeData.length>0){
					var totalFee=0,hasAccountFee=0;
					for(var i in dockFeeData){
						if(dockFeeData[i].discountFee){
							totalFee=util.FloatAdd(totalFee,dockFeeData[i].discountFee,2);
							if(dockFeeData[i].billType==1)
								hasAccountFee=util.FloatAdd(hasAccountFee,dockFeeData[i].discountFee,2);
						}
					}
			         var html="<div class='form-group totalGridDiv' style='margin-left: 0px; margin-right: 0px;'>"+
			         			"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总金额：</label>" +
			         			"<label class='control-label' style='margin-left: 10px;margin-right:20px;' id='totalFee'>"+
			         			util.toDecimal2(totalFee,true)+"</label><label class='control-label' style='margin-left: 10px;'>元</label></div>";	
			         dialog.find(".totalGridDiv").remove();
			         dialog.find("#fBChargeList").find(".grid-body").attr('style','min-height:0px;').parent().append(html);
			         initTaxMsg(dialog,dockFeeData[0].billType,dockFeeData[0].feeType,totalFee);
			         dialog.find("#billType").text((dockFeeData[0].billType==1?'手撕发票':'增值税发票'));
			         dialog.find("#feeType").text(dockFeeData[0].feeType);
					if(callback)
						callback(totalFee,hasAccountFee);
					}
			}
		});
	};
	
	function getCodeNum(dialog){
		$.ajax({
			type:'post',
			url:config.getDomain()+"/dockfeebill/getcodenum",
			dataType:'json',
			success:function(data){
			 util.ajaxResult(data,'获取编号',function(ndata,nmap){ dialog.find("#code").text(nmap.codeNum); },true);
			}});
	};
	
	function getFeeBillColumns(){
		return [{title:'费用项',name:'feeTypeStr'},{title:'对方单位',name:'clientName'},{title:'发票代码',name:'billCode'},
	             {title:'结算单编号',render:function(item){ return '<a href="javascript:void(0);" onclick="DockFee.initDockFeeDialog('+
	            	 item.dockFeeId+','+item.arrivalId+')">'+item.code+'</a>'}}, {title:'发生日期',name:'arrivalTime'},{title:'金额',name:'totalFee'},
	             {title:'折扣后金额(元)',render:function(item){
	            	 return '<div class="discountFeeTd"><label>'+util.toDecimal2(item.discountFee,true)+'</label></div>';
	 			}},{title:"操作",render:function(item){
	 				return '<div class="input-group-btn operateTd"><a href="javascript:void(0)"  style="margin:0 4px" '
					+' onclick="DockFeeBillDialog.editDialogTr(this,'+item.id+')" class="btn btn-xs blue" >'
					+'<span class="glyphicon glyphicon-edit" title="修改"></span></a></div>';
	 			}}];
	};
/*********************************************************************************/
	function editDialogTr(obj,id){
		var nTr=$(obj).closest("tr");
		var discountFee=$(nTr).find(".discountFeeTd label").text();
		$(nTr).find(".discountFeeTd").empty().append('<input class="form-control" style="width:100%;" onkeyup="config.clearNoNum(this,2)"'+
 				' onchange="DockFeeBillDialog.changeTotalFee(this)" maxlength="20"  data="'+util.toDecimal2(discountFee,true)
 				+'" value="'+util.toDecimal2(discountFee,true)+'">');
		$(nTr).find(".operateTd").empty().append('<a href="javascript:void(0)"  style="margin:0 4px"  class="btn btn-xs blue" '+
		 'onclick="DockFeeBillDialog.sureDialogTr(this,'+id+')"><span class="glyphicon glyphicon-ok" title="确定"></span></a>'+
		 '<a href="javascript:void(0)" style="margin:0 4px" class="btn btn-xs blue" onclick="DockFeeBillDialog.cancelDialogTr(this,'+id+')" >'+
         '<span class="fa fa-undo" title="取消"></span></a><a href="javascript:void(0)" style="margin:0 4px" class="btn btn-xs red"'+
         ' onclick="DockFeeBillDialog.deleteDialogTr(this,'+id+')"><span class="glyphicon glyphicon-remove" title="删除"></span></a> ');
	};
	
	function sureDialogTr(obj,id){
		var nTr=$(obj).closest("tr");
		var dialog=$(obj).closest(".modal-dialog");
		var feebillId=util.isNull(dialog.find('#id').text(),1);
		var dfDto={};
		dfDto.dockfeebill={
					id:feebillId==0?undefined:feebillId,
					feeType:dialog.find("#feeBillType").text(),
					code:dialog.find("#code").text(),
					feeHead:dialog.find("#feeHeadName").val(),
					totalFee:dialog.find("#totalFee").text(),//总价
					accountTime:util.formatLong(dialog.find("#accountTime").val()),
					createUserId:systemUserId,
					createTime:util.formatLong(util.currentTime(0)),
			}
		dfDto.feechargeList=[{
			id:id,
			discountFee:nTr.find('.discountFeeTd input').val()
		}];
		if(util.isNull(dialog.find("#feeBillType").text(),1)==1){
		dfDto.accountBillList=[{
			id:util.isNull(dialog.find("#accountBillId").text(),1),
			accountUserId:systemUserId,
			accountTime:util.formatLong(util.currentTime(0)),
			accountFee:dialog.find("#totalFee").text(),
			dockfeebillId:feebillId==0?undefined:feebillId
		}];}
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+'/dockfeebill/addorupdate',
			data:{"dfDto":JSON.stringify(dfDto)},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,"修改",function(ndata,nmap){
					initDockFeeBillMsg(dialog,nmap.id);
					if($('[data-role="dockFeeBillGrid"]'))
 						 util.updateGridRow($('[data-role="dockFeeBillGrid"]'),{id:nmap.id,url:'/dockfeebill/list'});
				});
			}
		});
	};
	
	function cancelDialogTr(obj,id){
		var nTr=$(obj).closest("tr");
		var dialog=$(obj).closest(".modal-dialog");
		var feeBillType=util.isNull(dialog.find("#feeBillType").text(),1);
		var discountFee=$(nTr).find(".discountFeeTd input").attr('data');
		$(nTr).find(".discountFeeTd").empty().append('<label>'+util.toDecimal2(discountFee,true)+'</label>');
		$(nTr).find(".operateTd").empty().append('<a href="javascript:void(0)"  style="margin:0 4px" '
				+' onclick="DockFeeBillDialog.editDialogTr(this,'+id+')" class="btn btn-xs blue" >'
				+'<span class="glyphicon glyphicon-edit" title="修改"></span></a>');
		
		dialog.find('#totalFee').text(dialog.find('#totalFee').attr('data'));
		dialog.find('#accountFee').text(dialog.find('#accountFee').attr('data'));
		
		initTaxMsg(dialog,feeBillType,dialog.find('#totalFee').text());
	};
	
	function deleteDialogTr(obj,id){
		var nTr=$(obj).closest("tr");
		var dialog=$(obj).closest(".modal-dialog");
		var feebillId=util.isNull(dialog.find('#id').text(),1);
		var discountFee=$(nTr).find(".discountFeeTd input").attr('data');
		if(dialog.find("#fBChargeList").getGrid().getAllItems().length==1){
			$('body').message({type:'warning',content:'账单至少包含一个费用项'});
		}else{
		/**/
		var dfDto={};
		dfDto.dockfeebill={
					id:feebillId==0?undefined:feebillId,
					feeType:dialog.find("#feeBillType").text(),
					code:dialog.find("#code").text(),
					feeHead:dialog.find("#feeHeadName").val(),
					totalFee:util.FloatSub(dialog.find("#totalFee").text(),discountFee,2),//总价
					accountTime:util.formatLong(dialog.find("#accountTime").val()),
					createUserId:systemUserId,
					createTime:util.formatLong(util.currentTime(0)),
			}
		dfDto.feechargeList=[{
			id:id 
		}];
		if(util.isNull(dialog.find("#feeBillType").text(),1)==1){
		dfDto.accountBillList=[{
			id:util.isNull(dialog.find("#accountBillId").text(),1),
			accountUserId:systemUserId,
			accountTime:util.formatLong(util.currentTime(0)),
			accountFee:util.FloatSub(dialog.find("#totalFee").text(),discountFee,2),
			dockfeebillId:feebillId==0?undefined:feebillId
		}];}
		/**/
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+'/dockfeebill/removeFeecharge',
			data:{"dfDto":JSON.stringify(dfDto)},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,"删除",function(ndata,nmap){
					initDockFeeBillMsg(dialog,nmap.id);
					if($('[data-role="dockFeeBillGrid"]'))
 						 util.updateGridRow($('[data-role="dockFeeBillGrid"]'),{id:nmap.id,url:'/dockfeebill/list'});
				});
			}
		});
		}
	};
/**********************************************************************************/
	function changeTotalFee(obj){
		var dialog=$(obj).closest(".modal-dialog");
		var feeBillType=util.isNull(dialog.find("#feeBillType").text(),1);
		var feeType=util.isNull(dialog.find("#feeType").text(),1);
		var difFee=util.FloatSub($(obj).val(),$(obj).attr('data'),2);
		dialog.find('#totalFee').attr('data',dialog.find('#totalFee').text())
				.text(util.FloatAdd(dialog.find('#totalFee').text(),difFee,2));
		if(feeBillType==1)
		dialog.find('#accountFee').attr('data',dialog.find('#accountFee').text())
		.text(util.FloatAdd(dialog.find('#accountFee').text(),difFee,2));
		
		initTaxMsg(dialog,feeBillType,feeType,dialog.find('#totalFee').text());
	};
	
	//初始化开票信息
	function initTaxMsg(dialog,feeBillType,feeType,totalFee){
		if(feeType==1){//手撕发票  -未税金额，税率，税额
			if(util.isNull(dialog.find("#unTaxFee").val(),1)==0)
				dialog.find("#unTaxFee").val(util.FloatDiv(totalFee,1.06,2));
			if(util.isNull(dialog.find("#taxRate").val(),1)==0)
				dialog.find("#taxRate").val(0.06);
			if(util.isNull(dialog.find("#taxFee").val(),1)==0)
				dialog.find("#taxFee").val(util.FloatMul(util.FloatDiv(totalFee,1.06,2),0.06,2));
        }else if(feeType==2){
        	if(util.isNull(dialog.find("#unTaxFee").val(),1)==0)
       	 		dialog.find("#unTaxFee").val(util.FloatDiv(totalFee,1.11,2));
        	if(util.isNull(dialog.find("#taxRate").val(),1)==0)
        		dialog.find("#taxRate").val(0.11);
        	if(util.isNull(dialog.find("#taxFee").val(),1)==0)
        		dialog.find("#taxFee").val(util.FloatMul(util.FloatDiv(totalFee,1.11,2),0.11,2));
        }
	};
/**********************************************************************************/
	function updateAccountFee(obj,id,feebillId){
		var nTr=$(obj).closest("tr");
		var userId=$(obj).attr('data');
		var userName=$(nTr).find('td[index="0"]').text();
		var time=$(nTr).find('td[index="1"]').text();
		var count=$(nTr).find('td[index="2"]').text();	
		nTr.empty();
		$(nTr).append('<td index="0"><input type="text" class="form-control" style="width:100%" data='+userId+' value='+userName+' ></td>'
				+'<td index="1"><input type="text" class="form-control date date-picker" style="width:100%" ></td>'
				+'<td index="2"><input type="text" class="form-control" style="width:100%" data='+count+' value='+count+' ></td>'
				+'<td index="3"><div style="width:100px;" class="input-group-btn">'
				+'<a href="javascript:void(0)"  style="margin:0 4px" onclick="DockFeeBillDialog.sureAccountFee(this,'+id+','+feebillId+')" class="btn btn-xs blue" >'
				+'<span class="glyphicon glyphicon glyphicon-ok" title="确定"></span></a>'
   	            +'<a href="javascript:void(0)" onclick="DockFeeBillDialog.cancelAccountFee(this,'+id+','+feebillId+')" style="margin:0 4px" class="btn btn-xs blue " >'
   	            +'<span class="fa fa-undo" title="取消"></span></a></div></td>');
		$.ajax({
			url:config.getDomain()+"/auth/user/get",
			dataType:'json',
			success : function(data) {
				var array1=new Array();
				for(var i=0;i<data.data.length;i++){
						array1.push({id:data.data[i].id,name:data.data[i].name});
				}
				util.handleTypeahead(array1,$(nTr).find("td[index='0'] input"),null,null,true);
			}
		});
		util.initTimePicker($(nTr));
		$(nTr).find("td[index='1'] input").datepicker('setDate',time);
	};
	function sureAccountFee(obj,id,feebillId){
		var mDialog=$(obj).closest(".modal-dialog");
		var tabe=$(obj).closest('div [data-role="fundsDetailTable"]');
		var nTr=$(obj).closest("tr");
		var userId=$(nTr).find("td[index='0'] input").attr('data');
		var time=util.formatLong($(nTr).find("td[index='1'] input").val());
		var count=$(nTr).find("td[index='2'] input").val();
		var leftCount=$(nTr).find("td[index='2'] input").attr('data');
		
		if(count>util.FloatAdd(leftCount,$(mDialog).find("#unAccountFee").text())){
			$('body').message({type:'warning',content:'修改的金额不合理'});
		}else{
			var fundsStatus=0;
			if(util.FloatAdd($(mDialog).find("#unAccountFee").text(),0)>0&&util.FloatSub(util.FloatSub(count,leftCount),$(mDialog).find("#unAccountFee").text())==0)//当前状态为1，修改后为2
				fundsStatus=2;
			else if($(mDialog).find("#unAccountFee").text()==0&&count!=leftCount)
				fundsStatus=1;
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/accountbilllog/update",
			data:{id:id,
				accountUserId:userId,
				accountTime:time,
				accountFee:count},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'修改',function(){
					var tabe=$(obj).closest('div [data-role="fundsDetailTable"]');
			    	tabe.getGrid().refresh();
				});
			}
		});
		}
	};
	
	 function cancelAccountFee(obj){
	    	var tabe=$(obj).closest('div [data-role="fundsDetailTable"]');
	    	tabe.getGrid().refresh();
	    };
	    
	function deleteAccountFee(obj,id,feebillId){
			if(id){
		var mDialog=$(obj).closest(".modal-dialog");	
				
		$('body').confirm({
			content:'确定要删除吗?',
			callBack:function(){
			var mDialog=$(obj).closest(".modal-dialog");
			var tabe=$(obj).closest('div [data-role="fundsDetailTable"]');
			var fundsStatus=0;
			if($(mDialog).find("#unAccountFee").text()==0){
				fundsStatus=1;
			}
			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+"/accountbilllog/delete",
				data:{id:id},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'删除',function(){
						var tabe=$(obj).closest('div [data-role="fundsDetailTable"]');
				    	tabe.getGrid().refresh();
					});
				}
			});
			}});}
		};
/*********************************************************************************/
	//获取用户
	function getSystemUserMsg(){
		if(systemUserId){
			return systemUserId;
		}else{
			$.ajax({
				type:'post',
				url:config.getDomain()+"/initialfee/getsystemuser",
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'获取系统信息',function(ndata){
						systemUserId=ndata[0].userId;
						systemUserName=ndata[0].userName;
						return systemUserId;},true);
				}});}
	};
	
	function exportDetailExcel(obj){
		var id=$(obj).closest(".modal-dialog").find("#id").text();
		var url=config.getDomain()+"/dockfeebill/exportDetailFee?id="+id;
		window.open(url);
	};
	return {
		dialogDockFillBillList:dialogDockFillBillList,
		dialogDockFeeBill:dialogDockFeeBill,
		insertToFeeBill:insertToFeeBill,
		editDialogTr:editDialogTr,
		sureDialogTr:sureDialogTr,
		cancelDialogTr:cancelDialogTr,
		deleteDialogTr:deleteDialogTr,
		changeTotalFee:changeTotalFee,
		updateAccountFee:updateAccountFee,
		deleteAccountFee:deleteAccountFee,
		cancelAccountFee:cancelAccountFee,
		sureAccountFee:sureAccountFee,
		deleteAccountFee:deleteAccountFee,
		exportDetailExcel:exportDetailExcel
	};
}();