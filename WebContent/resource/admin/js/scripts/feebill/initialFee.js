//首期费js
var InitialFee=function(){
	
	var systemUserId,systemUserName;
	var columnCheckArrayA,columnCheckArrayB,columnCheckArrayC,columnCheckArrayD,columnCheckArrayE;
	
	function init(type){
		cleanSearchMsg();
		initSearchCtrShowOrHide(type);
		getSystemUser();
		initTable(type);
	};
	
	function cleanSearchMsg(){
		$("#clientName,#productName,#startTime,#goodsInspect").val("").removeAttr("data");
		$("#endTime,#cargoCode,#ladingCode,#initialCode,#shipId").val("").removeAttr("data");
		$("#tradeType,#status").val(-1);
	};
	
	function initSearchCtrShowOrHide(type){
		$(".shipDiv").hide();
		if(type==1){
			$(".goodsInspectDiv,.cargoDiv").show();
			$(".tradeTypeDiv,#addFee").hide();
			$("#timeDiv1").removeClass().addClass('col-md-3');
			$("#timeDiv2").removeClass().addClass('control-label col-md-4');
			$("#timeDiv3").removeClass().addClass('col-md-8');
		}else if(type==2){
			$(".cargoDiv,#addFee").show();
			$(".goodsInspectDiv,.tradeTypeDiv,#addOilsFee").hide();
			$("#timeDiv1").removeClass().addClass('col-md-6');
			$("#timeDiv2").removeClass().addClass('control-label col-md-2');
			$("#timeDiv3").removeClass().addClass('col-md-10');
		}else if(type==3){
			$("#addFee").show();
			$(".goodsInspectDiv,.cargoDiv,.tradeTypeDiv").hide();
			$("#timeDiv1").removeClass().addClass('col-md-6');
			$("#timeDiv2").removeClass().addClass('control-label col-md-2');
			$("#timeDiv3").removeClass().addClass('col-md-10');
		}else if(type==4){
			$(".shipDiv,.tradeTypeDiv").show();
			$(".goodsInspectDiv,.cargoDiv,#addFee").hide();
			$("#timeDiv1").removeClass().addClass('col-md-3');
			$("#timeDiv2").removeClass().addClass('control-label col-md-4');
			$("#timeDiv3").removeClass().addClass('col-md-8');
		}else if(type==5){
			$(".cargoDiv").show();
			$(".goodsInspectDiv,.tradeTypeDiv,#addFee").hide();
			$("#timeDiv1").removeClass().addClass('col-md-6');
			$("#timeDiv2").removeClass().addClass('control-label col-md-2');
			$("#timeDiv3").removeClass().addClass('col-md-10');
		}
	};
	
	function getSystemUser(){
		if(!systemUserId){
			$.ajax({
				type:'post',
				url:config.getDomain()+"/initialfee/getsystemuser",
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'获取系统信息',function(ndata){
						systemUserId=ndata[0].userId;
						systemUserName=ndata[0].userName;
						return systemUserId;
					},true);
			}});
			}else{
				return systemUserId;
			}
	};
	
	function initTable(type){
		$(".initialClearFix").attr('data',type);
		if($('[data-role="storageFeeGrid"]').getGrid()!=null)
			$('[data-role="storageFeeGrid"]').getGrid().destory();
		$('[data-role="storageFeeGrid"]').grid({
			identity:'id',
			columns:getTableColumns(type),
			isShowIndexCol:false,
			searchCondition:getInitialCondition(type),
			pageSize:10,
			showPage:20,
			isShowPages:true,
			url:config.getDomain()+"/initialfee/list",
			callback:function(){
				initTableGridCallBack(type);
			}
		});
	};
	
	function getInitialCondition(type){
		switch(type){
		case 1:
			return {
			cargoCode: $("#cargoCode").val(),
			clientId: $("#clientName").attr('data'),
			productId: $("#productName").attr('data'),
			initialCode:$("#initialCode").val(),
			type:type,
			status:  util.isNull($("#status").val(), 1),
			goodsInspect:$("#goodsInspect").val(),
			startTime: util.formatLong($("#startTime").val() + " 00:00:00"),
			endTime: util.formatLong($("#endTime").val() + " 23:59:59")
		};
		case 2:
			return {
			cargoCode: $("#cargoCode").val(),
			clientId: $("#clientName").attr('data'),
			productId: $("#productName").attr('data'),
			initialCode:$("#initialCode").val(),
			type:type,
			status:  util.isNull($("#status").val(), 1),
			startTime: util.formatLong($("#startTime").val() + " 00:00:00"),
			endTime: util.formatLong($("#endTime").val() + " 23:59:59")
		};
		case 3:
			return {
			clientId: $("#clientName").attr('data'),
			productId: $("#productName").attr('data'),
			initialCode:$("#initialCode").val(),
			type:type,
			status:  util.isNull($("#status").val(), 1),
			startTime: util.formatLong($("#startTime").val() + " 00:00:00"),
			endTime: util.formatLong($("#endTime").val() + " 23:59:59")
		};
		case 4:
			return {
			clientId: $("#clientName").attr('data'),
			productId: $("#productName").attr('data'),
			initialCode:$("#initialCode").val(),
			type:type,
			shipName:$("#shipId").val(),
			tradeType:util.isNull($("#tradeType").val(), 1),
			status:  util.isNull($("#status").val(), 1),
			startTime: util.formatLong($("#startTime").val() + " 00:00:00"),
			endTime: util.formatLong($("#endTime").val() + " 23:59:59")
		};
		case 5:
			return {
			clientId: $("#clientName").attr('data'),
			productId: $("#productName").attr('data'),
			initialCode:$("#initialCode").val(),
			type:type,
			status:  util.isNull($("#status").val(), 1),
			startTime: util.formatLong($("#startTime").val() + " 00:00:00"),
			endTime: util.formatLong($("#endTime").val() + " 23:59:59")
		};
		}
	};
	
	function initTableGridCallBack(type){
		$('[data-role="storageFeeGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
		$('.inmtable').closest("td").css('padding','0px');
		var indexThStr="";
		if(type==1){
			indexThStr="th[index='0'],th[index='1'],th[index='2'],th[index='4'],th[index='11']";
			util.setColumnsVisable($('[data-role="storageFeeGrid"]'),[0,1,2,12],columnCheckArrayA,function(){columnCheckArrayA=util.getColumnsCheckArray();});
		}else if(type==2){
			indexThStr="th[index='0'],th[index='1'],th[index='2'],th[index='6']";
		}else if(type==3){
			indexThStr="th[index='0'],th[index='1'],th[index='5']";
		}else if(type==4){
			indexThStr="th[index='0'],th[index='1'],th[index='3'],th[index='12']";
			util.setColumnsVisable($('[data-role="storageFeeGrid"]'),[0,1,13],columnCheckArrayD,function(){columnCheckArrayD=util.getColumnsCheckArray();});
		}else if(type==5){
			indexThStr="th[index='0'],th[index='1'],th[index='2'],th[index='4'],th[index='11']";
			util.setColumnsVisable($('[data-role="storageFeeGrid"]'),[0,1,2,12],columnCheckArrayE,function(){columnCheckArrayE=util.getColumnsCheckArray();});
		}
		$('[data-role="storageFeeGrid"]').find(indexThStr).unbind('click').bind('click',function(){
			var params=getInitialCondition(type);
			params.type=type;
			params.indexTh=$(this).attr('index');
			$('[data-role="storageFeeGrid"]').getGrid().search(params);
		});
	};
/*******************************************************************************************************************/	
	//入库首期费
	function dialogInitialInboundFee(id,code){
		$.get(config.getResource()+"/pages/inbound/storageFee/dialog_initialfee.jsp").done(function(data){
			var dialog=$(data);
			initInitialInboundCtr(dialog);
			initInitialInboundMsg(dialog,id,code);
		});
	};
	//合同首期费
	function dialogInitialContractFee(id){
		$.get(config.getResource()+"/pages/inbound/storageFee/dialog_addinitialfee.jsp").done(function(data){
			var dialog=$(data);
			initInitialContractInboundCtr(dialog,id);
			if(util.isNull(id,1)!=0)
				initInitialContractInboundMsg(dialog,id);
		});
	};
	//出库结算首期费
	function dialogInitialOutboundFee(id){
		$.get(config.getResource()+"/pages/inbound/storageFee/dialog_oilsfee.jsp").done(function(data) {
			var dialog=$(data);
			initInitialOutboundCtr(dialog,id);
			if(util.isNull(id,1)!=0)
				initInitialOutboundMsg(dialog,id);
		});
	};
	//外贸出口保安费
	function dialogInitialOutboundSecurityFee(id,arrivalId){
		$.get(config.getResource()+"/pages/inbound/storageFee/dialog_outboundinitialfee.jsp").done(function(data) {
			var dialog=$(data);
			initInitialOutboundSecurityCtr(dialog);
			if(util.isNull(id,1)!=0)
				initInitialOutboundSecurityMsg(dialog,id);
			else
				initInitialOutboundSecurityBaseMsg(dialog,arrivalId);
		});
	};
	//入库通过费
	function dialogInitialInboundPassFee(id,code){
		$.get(config.getResource()+"/pages/inbound/storageFee/dialog_initialfee.jsp").done(function(data){
			var dialog=$(data);
			initInitialInboundPassCtr(dialog);
			initInitialInboundPassMsg(dialog,id,code);
		});
	};
	
/*****************************dialogInitialInboundFee****************************************************/	
	function initInitialInboundCtr(dialog){
		handleInitialFeeBaseCtr(dialog);
		
		dialog.find(".save").click(function(){
			$this = $(this);
			var status = $this.attr('key');
			if(validateInitial(dialog,1)){
				doInitialSubmit(dialog,status,1,function(id){
					initInitialInboundMsg(dialog,id,dialog.find("#code").text());
				});
			}});
		
		dialog.find(".back").click(function(){
			var itemId = dialog.find("#id").text();
			doInitialBack(itemId,function(){
				initInitialInboundMsg(dialog,itemId,dialog.find("#code").text());
			});
		});
		
	};
	
	function initInitialInboundMsg(dialog,id,code){
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/initialfee/getinitialfeemsg",
			data:{id:id,type:1},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取首期费',function(ndata){
					if(ndata&&ndata.length>0){
						var msg=ndata[0];
						dialog.find(".feeKey").each(function(){
						$this = $(this);
						var key = $this.attr('id');
						$this.text(util.isNull(msg[key]));
						});
						
						var fileHtml='';
						if (util.isNull(msg.fileUrl)!='')
							fileHtml='<a type="button" class=" blue loadFire" href="'+ getRootPath()+ msg.fileUrl
							+ '"><span class="fa fa-arrow-down"></span></a>';
						else 
							fileHtml='<a type="button" class=" gray loadFire" href="javascript:void(0);">'
								+'<span class="fa fa-arrow-down" style="color:gray;"></span></a>'
						dialog.find(".loadFire").remove();
						dialog.find("#contractCode").append(fileHtml);
						
						dialog.find("#accountTime").val(msg.accountTime);
						dialog.find("#clientName").attr('data', msg.clientId);
						dialog.find("#productName").attr('data', msg.productId);
						dialog.find("#contractType").text(getContractTypeVal(msg.contractType));
						dialog.find("#createUserName").attr('data',msg.createUserId);
						dialog.find("#description").val(util.isNull(msg.description));
						dialog.find("#periodTime").text(msg.arrivalTime+"至 "+
								util.addDate(msg.arrivalTime,util.FloatSub(msg.periodTime, 1)));
						
						initAddFeeChargeBtn(dialog,{clientId:msg.clientId,clientName:msg.clientName,
							feeTypeArray:[{'key':1,'value':'仓储费'},{'key':2,'value':'保安费'},{'key' : 8,'value' : '通过费'}]});
						
						if(msg.feeChargeList&&msg.feeChargeList.length>0)
							handleFeeChargeTable(dialog,msg.feeChargeList);							
						else if(util.isNull(code)==''||!msg.accountTime)
							initInitialInboundBaseMsg(dialog,msg);
						handleInitialFeeStatus(dialog,util.isNull(msg.status,1));
						
					}
				},true);
			}
			});
		};
	
	function initInitialInboundBaseMsg(dialog,msg){
		dialog.find("#accountTime").datepicker("setDate",util.currentTime(0));
		getInitialCodeNum(dialog);
		var feeChargeList=new Array();
		if(util.isNull(msg.contractType,1)!=1){
			var unitFee=(msg.taxType==3?msg.protectStoragePrice:msg.storagePrice);
			if(msg.productName=='甲醇'||msg.productName=='乙二醇')
				unitFee=util.FloatAdd(msg.passPrice,unitFee,2);
			feeChargeList.push({
				feeType:1,unitFee:unitFee,feeCount:msg.goodsInspect,
				totalFee:util.FloatMul(unitFee, msg.goodsInspect, 2),
				feeHead:msg.clientId,feeHeadName:msg.clientName
			});
		}
		
		if(util.isNull(msg.taxType,1)!=1){
			feeChargeList.push({
				feeType:2,unitFee:msg.portSecurityPrice,feeCount:msg.goodsInspect,
				totalFee:util.FloatMul(msg.portSecurityPrice, msg.goodsInspect, 2),
				feeHead:msg.clientId,feeHeadName:msg.clientName
			});
		}
		
		handleFeeChargeTable(dialog,feeChargeList);
	};
	
/***************************************dialogInitialContractFee*************************************************************/	
	function initInitialContractInboundCtr(dialog,id){
		handleInitialFeeBaseCtr(dialog);
		
		if(!id){
			dialog.find("#accountTime").datepicker("setDate", util.currentTime(0));
			getInitialCodeNum(dialog);
			handleInitialFeeStatus(dialog,0);
		 	handleFeeChargeTable(dialog,[{
				feeType:3,unitFee:0,feeCount:0,totalFee:0},{feeType:4,unitFee:0,feeCount:0,totalFee:0}]);
		}
		
		util.urlHandleTypeaheadAllData("/client/select",dialog.find("#clientName"),undefined,undefined,function(data) {
							dialog.find("#contractCode").val("").removeAttr('data');
							dialog.find("#periodTime,#contractType").text("");
							dialog.find(".loadFire").remove();
							if (data){
								dialog.find(".feeHead").val(data.name).attr('data',data.id);
								initAddFeeChargeBtn(dialog,{clientId:data.id,clientName:data.name,
									feeTypeArray:[{'key':1,'value':'仓储费'},{'key':2,'value':'保安费'},{
										'key' : 3,'value' : '包罐费'},{'key' : 4,'value' : '超量费'},{
										'key' : 8,'value' : '通过费'}]});
							} else {
								dialog.find(".feeHead").val("").removeAttr('data');
							}
						}); 
		
		util.urlHandleTypeaheadAllData("/product/select",dialog.find('#productName'),undefined,undefined,function() {
							dialog.find("#contractCode").val("").removeAttr('data');
							dialog.find("#periodTime,#contractType").text("");
							dialog.find(".loadFire").remove();
						}); 
		
		dialog.find("#checkCargoList").click(function(){
					var contractId = dialog.find("#contractCode").attr("data");
					var startTime = dialog.find("#startTime").val();
					var endTime = dialog.find("#endTime").val();
					if (util.isNull(contractId,1)!= 0&&startTime&& startTime.length>2&&endTime&&endTime.length>2){
						getContractCargoGrid(dialog,{contractId:contractId,startTime:util.formatLong(startTime),
							endTime:util.formatLong(endTime)});
					}});
		
		dialog.find("#contractCode").focus(function(){getContractCode(dialog);});
		
		dialog.find(".save").click(function(){
			$this = $(this);
			var status = $this.attr('key');
			if(validateInitial(dialog,2)){
				doInitialSubmit(dialog,status,2,function(id){
					initInitialContractInboundMsg(dialog,id);
				});
			}
		});
		
		dialog.find(".back").click(function(){
			var itemId = dialog.find("#id").text();
			doInitialBack(itemId,function(){
				initInitialContractInboundMsg(dialog,itemId);
			});
		});
		
	};
	
	function getContractCargoGrid(dialog,data){
		$.ajax({
			type:'post',
			url:config.getDomain()+ "/initialfee/getcontractcargolist",
			data:data,
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,"获取货批列表",function(ndata){
					if(ndata&&ndata.length>0){
						var cargoNum = 0;
						for(var i=0; i<ndata.length; i++)
							cargoNum = util.FloatAdd(cargoNum,ndata[i].goodsTotal);
						dialog.find("#goodsInspect").val(util.toDecimal3(cargoNum,true));
						var columns = [{title:'货批号',name:'cargoCode'},
						               {title:'数量',render:function(item){
						            	   return util.toDecimal3(item.goodsTotal,true);
						               }},{title:"入库日期",name:"arrivalTime"}];
						
						if (dialog.find('[data-role="otherFeeGrid"]').getGrid() != null)
							dialog.find('[data-role="otherFeeGrid"]').getGrid().destory();
						dialog.find('[data-role="otherFeeGrid"]').grid({
							columns:columns,
							isShowIndexCol:false,
							isShowPages:false,
							isUserLocalData:true,
							localData:ndata});
						}},true);
			}
		});
	};
	
	function getContractCode(dialog){
		if(util.isNull(dialog.find('#clientName').attr("data"),1)==0){
			$('body').message({type : 'warning',content : '请选择货主'});
		}else if(util.isNull(dialog.find('#productName').attr("data"),1)==0){
			$('body').message({type : 'warning',content : '请选择货品'});
		}else {
			dialog.find(".contractCode").empty().append('<input class=" form-control feeKey" style="text-align:left" id="contractCode">');
			util.urlHandleTypeahead("/order/list?types=1&status=2&productId="+dialog.find('#productName').attr("data")
									+"&clientId="+dialog.find('#clientName').attr("data"),dialog.find("#contractCode"),
							'code','id',undefined,function(data){
								if (data) {
									dialog.find("#contractType").text(data.typeName);
									dialog.find(".loadFire").remove();
									var html='';
									if(util.isNull(data.fileUrl)!='')
										html='<span class="input-group-btn loadFire"><a type="button" class="btn blue" href="'
											 + getRootPath()+ data.fileUrl+'"><span class="fa fa-arrow-down"></span></a></span>';
									else
										html='<span class="input-group-btn loadFire"><a type="button" class="btn gray"'
											+' href="javascript:void(0);"><span class="fa fa-arrow-down"></span></a></span>';
									
									dialog.find(".contractCode").append(html);
								}
							});
		}
	};
	
	function initInitialContractInboundMsg(dialog,id){
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/initialfee/getinitialfeemsg",
			data:{id:id,type:2},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,"获取合同首期费",function(ndata){
					if(ndata&&ndata.length>0){
						var msg=ndata[0];
						dialog.find("#id").text(msg.id);
						dialog.find("#code").text(msg.code);
						dialog.find("#accountTime").val(msg.accountTime);
						dialog.find("#clientName").val(msg.clientName).attr('data', msg.clientId);
						dialog.find("#productName").val(msg.productName).attr('data', msg.productId);
						dialog.find("#contractCode").val(msg.contractCode).attr("data",msg.contractId);
						var fileHtml='';
						if (util.isNull(msg.fileUrl)!='')
							fileHtml='<a type="button" class=" blue loadFire" href="'+ getRootPath()+ msg.fileUrl
							+ '"><span class="fa fa-arrow-down"></span></a>';
						else 
							fileHtml='<a type="button" class=" gray loadFire" href="javascript:void(0);">'
								+'<span class="fa fa-arrow-down" style="color:gray;"></span></a>';
						dialog.find(".loadFire").remove();
						dialog.find("#contractCode").append(fileHtml);
						
						dialog.find("#contractType").text(getContractTypeVal(msg.contractType));
						dialog.find("#cargoCode").val(util.isNull(msg.cargoCodes));
						dialog.find("#goodsInspect").val(util.toDecimal3(msg.contractNum,true));
						dialog.find("#startTime").val(msg.startTime);
						dialog.find("#endTime").val(msg.endTime);
						dialog.find("#createUserName").text(msg.createUserName).attr('data',msg.createUserId);
						dialog.find("#description").val(util.isNull(msg.description));
						if (util.isNull(msg.contractId,1)!=0&&startTime&& startTime.length>2&&endTime&&endTime.length>2)
							getContractCargoGrid(dialog,{contractId:msg.contractId,startTime:util.formatLong(msg.startTime),
								endTime:util.formatLong(msg.endTime)});
						
						if(msg.feeChargeList&&msg.feeChargeList.length>0)
							handleFeeChargeTable(dialog,msg.feeChargeList);	
						handleInitialFeeStatus(dialog,util.isNull(msg.status,1));
				
					}
				},true);
			}});
	};
	
	
/********************************dialogInitialOutboundFee**************************************************************************************************************************/	
	function initInitialOutboundCtr(dialog,id){
		handleInitialFeeBaseCtr(dialog);
		
		if(!id){
			getInitialCodeNum(dialog);
			dialog.find("#accountTime").datepicker("setDate", util.currentTime(0));
		 	handleFeeChargeTable(dialog,[{feeType:1,unitFee:0,feeCount:0,totalFee:0},{feeType:8,unitFee:0,feeCount:0,totalFee:0}]);
		 	handleInitialFeeStatus(dialog,0);
		}
		 
		util.urlHandleTypeaheadAllData("/product/select",dialog.find('#productName'));

		util.urlHandleTypeaheadAllData("/baseController/getClientName",dialog.find("#clientName"),
				function(item){return item.name+"["+item.code+"]";},undefined,function(data){
			if(data){
				dialog.find(".feeHead").val(data.name).attr('data',data.id);
				initAddFeeChargeBtn(dialog,{clientId:data.id,clientName:data.name,
					feeTypeArray:[{'key':1,'value':'仓储费'},{'key':2,'value':'保安费'},{
						'key' : 3,'value' : '包罐费'},{'key' : 4,'value' : '超量费'},{
						'key' : 8,'value' : '通过费'}]});
			}else{			
				dialog.find(".feeHead").val("").removeAttr('data');
			}
		});
		
		
		dialog.find("#checkOutBoundList").click(function(){
			var clientId=dialog.find("#clientName").attr("data");
			var productId=dialog.find('#productName').attr("data");
			var startTime=util.formatLong(dialog.find("#startTime").val()+" 00:00:00");
			var endTime=util.formatLong(dialog.find("#endTime").val()+" 23:59:59");
			if(util.isNull(clientId,1)!=0&&util.isNull(productId,1)!=0&&startTime&&startTime!=-1&&endTime&&endTime!=-1){
				getOutboundShipAndTruckNum(dialog,{'clientId':clientId,'productId':productId,'startTime':startTime,'endTime':endTime,'type':1});
			}
		});
		
		dialog.find(".save").click(function(){
			$this=$(this);
			var status = $this.attr('key');
			if(validateInitial(dialog,3)){
				doInitialSubmit(dialog,status,3,function(id){
					initInitialOutboundMsg(dialog,id);
				});
			}
		});
       
		dialog.find(".back").click(function(){
			var itemId = dialog.find("#id").text();
			doInitialBack(itemId,function(){
				initInitialOutboundMsg(dialog,itemId);
			});
		});
		
	};
	
	function getOutboundShipAndTruckNum(dialog,params){
		var columns=[{title:"",render:function(item){
			return '<a href="javascript:void(0);" onclick="InitialFee.addOutboundShipAndTruckTable(this,'+item.deliverType+
			')"><span class="fa fa-plus-square-o" style="font-size:17px"></span></a>';
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
	};
	
	function addOutboundShipAndTruckTable(obj,deliverType){
		$(obj).find('span').attr('class');
		if($(obj).find('span').attr('class')=='fa fa-plus-square-o'){
			$(obj).find('span').removeClass('fa fa-plus-square-o').addClass('fa fa-minus-square-o');
		var iTr=$(obj).closest('tr');
		var html='<tr class="detail"><td colspan="6">'
			+'<div class="col-md-12"><div data-role="detailOutBoundMsgGrid'+deliverType+'"></div>'
			+'</td></tr>';
	    $(html).insertAfter(iTr);
	    initOutboundShipAndTruckDetailTable($(obj).closest('.modal-dialog'),deliverType);
		}else{
			$(obj).find('span').removeClass('fa fa-minus-square-o').addClass('fa fa-plus-square-o ');
			var iTr=$(obj).closest('tr');
			iTr.next('tr[class="detail"]').remove();
		}
	};
	
	function initOutboundShipAndTruckDetailTable(dialog,deliverType){
		var params={
		 type:2,
		 deliverType:deliverType,
		 clientId:dialog.find("#clientName").attr("data"),
		 productId:dialog.find('#productName').attr("data"),
		 startTime:util.formatLong(dialog.find("#startTime").val()+" 00:00:00"),
		 endTime:util.formatLong(dialog.find("#endTime").val()+" 23:59:59")
		 };
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
	};
	
	function initInitialOutboundMsg(dialog,id){
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/initialfee/getinitialfeemsg",
			data:{id:id,type:3},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取基本信息',function(ndata){
               if(ndata&&ndata.length>0){
            	   var msg=ndata[0];
            	    dialog.find("#id").text(msg.id);
            	   	dialog.find("#code").text(msg.code);
            	   	dialog.find("#accountTime").val(msg.accountTime);
					dialog.find("#clientName").val(util.isNull(msg.clientName)).attr("data",msg.clientId);
					dialog.find("#productName").val(util.isNull(msg.productName)).attr("data",msg.productId);;
					dialog.find("#goodsTotal").val(util.isNull(msg.contractNum,1));
					dialog.find("#startTime").val(msg.startTime);
					dialog.find("#endTime").val(msg.endTime);
					dialog.find("#description").val(util.isNull(msg.description));
					dialog.find("#createUserName").text(util.isNull(msg.createUserName)).attr('data',msg.createUserId);
					getOutboundShipAndTruckNum(dialog,{'clientId':msg.clientId,'productId':msg.productId,
						'startTime':util.formatLong(msg.startTime+" 00:00:00"),'endTime':util.formatLong(msg.endTime+" 23:59:59"),'type':1});
					
					if(msg.feeChargeList&&msg.feeChargeList.length>0)
						handleFeeChargeTable(dialog,msg.feeChargeList);		
					handleInitialFeeStatus(dialog,util.isNull(msg.status,1));
               }
				},true);
			}
		});
	};
/*********************************dialogInitialOutboundSecurityFee*************************************************************************************************************************/	
	function initInitialOutboundSecurityCtr(dialog){
		handleInitialFeeBaseCtr(dialog);
		
		dialog.find(".save").click(function(){
			$this = $(this);
			var status = $this.attr('key');
			if(validateInitial(dialog,4)){
				doInitialSubmit(dialog,status,4,function(id){
					initInitialOutboundSecurityMsg(dialog,id);
				});
			}
		});
		
		dialog.find(".back").click(function(){
			var itemId = dialog.find("#id").text();
			doInitialBack(itemId,function(){
				initInitialOutboundSecurityMsg(dialog,itemId);
			});
		});
	};
	
	function initInitialOutboundSecurityMsg(dialog,id){
		 config.load();
		 $.ajax({
			 type:'post',
			 url:config.getDomain()+'/initialfee/getinitialfeemsg',
			 data:{id:id,type:4},
			 dataType:'json',
			 success:function(data){
				 util.ajaxResult(data,'获取信息',function(ndata){
					 if(ndata&&ndata.length>0){
				 var msg=ndata[0];
				 	dialog.find("#id").text(msg.id);
				 	dialog.find("#code").text(msg.code);
				 	dialog.find("#accountTime").val(msg.accountTime);
					dialog.find("#arrivalId").text(msg.arrivalId); 
					dialog.find("#clientName").text(util.isNull(msg.ladingClientNames)).attr('data',msg.ladingClientIds); 
					dialog.find("#productName").text(util.isNull(msg.productName)).attr('data',msg.productId); 	
					dialog.find("#shipName").text(util.isNull(msg.shipName)); 	
					dialog.find("#shipRefName").text(util.isNull(msg.shipRefName)); 
					dialog.find("#arrivalTime").text(util.isNull(msg.arrivalTime)); 
					dialog.find("#contractNum").text(util.toDecimal3(msg.actualNum,true)); 
   					dialog.find("#description").val(util.isNull(msg.description));
   					dialog.find("#createUserName").text(util.isNull(msg.createUserName)).attr('data',msg.createUserId);
   					
   					if(msg.feeChargeList&&msg.feeChargeList.length>0)
						handleFeeChargeTable(dialog,msg.feeChargeList);	
   					
   					handleInitialFeeStatus(dialog,util.isNull(msg.status,1));
					 }
				 },true);
			 }
		 });
	};
	
	function initInitialOutboundSecurityBaseMsg(dialog,arrivalId){
		dialog.find("#accountTime").datepicker("setDate", util.currentTime(0));
		getInitialCodeNum(dialog);
	 	dialog.find("#feeTbody").empty();
	 	handleInitialFeeStatus(dialog,0);
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/initialfee/list",
			dataType:'json',
			data:{id:arrivalId,type:4},
			success:function(data){
				util.ajaxResult(data,'获取基本数据',function(ndata){
					if(ndata&&ndata.length>0){
						var msg=ndata[0];
						dialog.find("#arrivalId").text(arrivalId);//到港id	
						dialog.find("#clientName").text(util.isNull(msg.ladingClientNames)).attr('data',msg.ladingClientIds);//多个不同提货单位	
						dialog.find("#productName").text(util.isNull(msg.productName)).attr('data',msg.productId);//货品	
						dialog.find("#shipName").text(util.isNull(msg.shipName));//船英文名		
						dialog.find("#shipRefName").text(util.isNull(msg.shipRefName));//船中文名
						dialog.find("#arrivalTime").text(util.isNull(msg.arrivalTime));//到港时间
						dialog.find("#contractNum").text(util.toDecimal3(msg.actualNum,true));//总数量
						var feeChargeList=new Array();
						feeChargeList.push({
							feeType:(msg.arrivalType!=5?2:8),
							unitFee:(msg.arrivalType!=5?0.25:25),
							feeCount:util.toDecimal3(msg.actualNum),
							totalFee:util.FloatMul((msg.arrivalType!=5?0.25:25),util.toDecimal3(msg.actualNum), 2),
							feeHead:msg.ladingClientIds.split(",")[0],
							feeHeadName:msg.ladingClientNames.split(",")[0]
						});
						
						 handleFeeChargeTable(dialog,feeChargeList);
						initAddFeeChargeBtn(dialog,{clientId:msg.ladingClientIds.split(",")[0],clientName:msg.ladingClientNames.split(",")[0],
							feeTypeArray:[{'key':2,'value':'保安费'},{'key' : 8,'value' : '通过费'}]});
					}
				},true);
			}
		});
	};
/*********************************dialogInitialInboundPassFee******************************************************************/	
	function initInitialInboundPassCtr(dialog){
		handleInitialFeeBaseCtr(dialog);
		
		dialog.find(".save").click(function(){
			$this = $(this);
			var status = $this.attr('key');
			if(validateInitial(dialog,5)){
				doInitialSubmit(dialog,status,5,function(id){
					initInitialInboundMsg(dialog,id,dialog.find("#code").text());
				});
			}
		});
		
		dialog.find(".back").click(function(){
			var itemId = dialog.find("#id").text();
			doInitialBack(itemId,function(){
				initInitialInboundMsg(dialog,itemId,dialog.find("#code").text());
			});
		});
	};
	
	function initInitialInboundPassMsg(dialog,id,code){
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/initialfee/getinitialfeemsg",
			data:{id:id,type:5},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取首期费',function(ndata){
					if(ndata&&ndata.length>0){
						var msg=ndata[0];
						dialog.find(".feeKey").each(function(){
						$this = $(this);
						var key = $this.attr('id');
						$this.text(util.isNull(msg[key]));
						});
						
						var fileHtml='';
						if (util.isNull(msg.fileUrl)!='')
							fileHtml='<a type="button" class=" blue loadFire" href="'+ getRootPath()+ msg.fileUrl
							+ '"><span class="fa fa-arrow-down"></span></a>';
						else 
							fileHtml='<a type="button" class=" gray loadFire" href="javascript:void(0);">'
								+'<span class="fa fa-arrow-down" style="color:gray;"></span></a>'
						dialog.find(".loadFire").remove();
						dialog.find("#contractCode").append(fileHtml);
						
						dialog.find("#accountTime").val(msg.accountTime);
						dialog.find("#clientName").attr('data', msg.clientId);
						dialog.find("#productName").attr('data', msg.productId);
						dialog.find("#contractType").text(getContractTypeVal(msg.contractType));
						dialog.find("#createUserName").attr('data',msg.createUserId);
						dialog.find("#description").val(util.isNull(msg.description));
						if (util.isNull(msg.contractType, 1)!=5){
							dialog.find("periodTimeDiv").show();
							dialog.find("#periodTime").text(msg.arrivalTime+"至 "+util.addDate(msg.arrivalTime, util.FloatSub(msg.periodTime, 1)));
						}else if (util.isNull(msg.contractType,1)==5){
							dialog.find("periodTimeDiv").hide();
							dialog.find("#periodTime").text("");
						}
						
						initAddFeeChargeBtn(dialog,{clientId:msg.clientId,clientName:msg.clientName,
							feeTypeArray:[{'key':1,'value':'仓储费'},{'key':2,'value':'保安费'},{
								'key' : 3,'value' : '包罐费'},{'key' : 4,'value' : '超量费'},{
									'key' : 8,'value' : '通过费'}]});
						
						if(msg.feeChargeList&&msg.feeChargeList.length>0)
							handleFeeChargeTable(dialog,msg.feeChargeList);							
						else if(util.isNull(code)=='')
							initInitialInboundPassBaseMsg(dialog,msg);
						
						handleInitialFeeStatus(dialog,util.isNull(msg.status,1));	
					}
				},true);
			}
			});
	};
	
	function initInitialInboundPassBaseMsg(dialog,msg){
		dialog.find("#accountTime").datepicker("setDate",util.currentTime(0));
		getInitialCodeNum(dialog);
		var feeChargeList=new Array();
		if(util.isNull(msg.contractType,1)==5){
			feeChargeList.push({
				feeType:8,unitFee:0,feeCount:msg.goodsInspect,
				totalFee:0,
				feeHead:msg.clientId,feeHeadName:msg.clientName
			});
		}
		
		if(util.isNull(msg.taxType,1)!=1){
			feeChargeList.push({
				feeType:2,unitFee:msg.portSecurityPrice,feeCount:msg.goodsInspect,
				totalFee:util.FloatMul(msg.portSecurityPrice, msg.goodsInspect, 2),
				feeHead:msg.clientId,feeHeadName:msg.clientName
			});
		}
		
		handleFeeChargeTable(dialog,feeChargeList);
	};
/********************************************************************************************************/	
	function validateInitial(dialog,initialType){
		var isPass=true;
		if(initialType==1&&util.isNull(dialog.find("#goodsInspect").text(),1)==0){
			$('body').message({
				type : 'warning',
				content :'该货批还未商检，请前往商检'
			});
			isPass=false;
		}else if(initialType==2){
			isPass=true;
		}
		return isPass;
	};
	function doInitialSubmit(dialog,status,initialType,callback){
		var id=dialog.find("#id").text();
		var iFeeDto={
				'initialFee':{
					id:id==0?undefined:id,
					code:dialog.find("#code").text(),
					description:dialog.find("#description").val(),
					createTime:util.formatLong(util.currentTime(1)),
					accountTime:util.formatLong(dialog.find("#accountTime").val()),
					clientId:dialog.find('#clientName').attr('data'),
					productId:dialog.find('#productName').attr('data'),
					totalFee:dialog.find("#totalFee").text(),
					type:initialType,
					createUserId:getSystemUser(),
					status:status
				}
		}
		if(initialType==2){
			iFeeDto.initialFee.contractId=dialog.find("#contractCode").attr('data');
			iFeeDto.initialFee.cargoCodes=dialog.find("#cargoCode").val();
			iFeeDto.initialFee.startTime=util.formatLong(dialog.find("#startTime").val());
			iFeeDto.initialFee.endTime=util.formatLong(dialog.find("#endTime").val());
			iFeeDto.initialFee.contractNum=util.isNull(dialog.find("#goodsInspect").val(),1);
		 }else if(initialType==3){
			 iFeeDto.initialFee.startTime=util.formatLong(dialog.find("#startTime").val());
			 iFeeDto.initialFee.endTime=util.formatLong(dialog.find("#endTime").val());
			 iFeeDto.initialFee.contractNum=util.isNull(dialog.find("#goodsTotal").val(),1);
		 }else if(initialType==4){
			 iFeeDto.initialFee.arrivalId=dialog.find("#arrivalId").text();
		 }else if(initialType==5){
			 iFeeDto.initialFee.type=1;
		 }
		
		var dataList=new Array();
		dialog.find('#feeTbody>tr').each(function(){
			$this=$(this);
			if($this.attr("id")!="totalTr"){
				if(util.isNum($this.find('.totalFee').val())&&util.isNum($this.find('.totalFee').val())!=0){
					dataList.push({
					id:(util.isNull($this.find('.feeId').attr('data'),1)==0?undefined:$this.find('.feeId').attr('data')),
					type:(status>=1?1:0),
					feeType:$this.attr('data'),
					unitFee:util.isNum($this.find('.unitFee').val()),
					feeCount:util.isNum($this.find('.feeCount').val()),
					totalFee:util.isNum($this.find('.totalFee').val()),
					discountFee:util.isNum($this.find('.totalFee').val()),
					createTime:util.formatLong(util.currentTime(0)),
					feeHead:$this.find('.feeHead').attr('data'),
					clientId:dialog.find('#clientName').attr('data'),
					productId:dialog.find('#productName').attr('data'),
			});
			}}
		});
		//如果结算单没有费用项，结算单状态直接变成已完成
		if(dataList.length==0&&status==1)
			iFeeDto.initialFee.status=4;
		iFeeDto.feechargelist=dataList;
		config.load();
		$.ajax({
			type : 'post',
			url : config.getDomain()+ "/initialfee/addorupdate",
			data : {iFeeDto:JSON.stringify(iFeeDto)},
			dataType : 'json',
			success : function(data){
				util.ajaxResult(data,(status==0?'保存':'提交'),function(ndata,nmap){
					if(callback) callback(nmap.id);
					if(iFeeDto.initialFee.id)
						util.updateGridRow($('[data-role="storageFeeGrid"]'),{id:nmap.id,type:initialType,url:"/initialfee/list"});
					else
						$('[data-role="storageFeeGrid"]').getGrid().refresh();
				});
			}});
	};
	
	function doInitialBack(id,callback){
		if(id){
			config.load();
			$.ajax({type : 'post',
					url : config.getDomain()+ "/initialfee/backstatus",
					data : {id : id,status : 0},
					dataType : 'json',
					success : function(data){
							util.ajaxResult(data,'回退',function() {
								if(callback)
									callback();
							$('[data-role="storageFeeGrid"]').getGrid().refresh();
							});
			}});
		}
	};
/********************************************************************************************************************/	
	function getInitialCodeNum(dialog){
		config.load();
		$.ajax({
			type : 'post',
			url : config.getDomain() + "/initialfee/getcodenum",
			dataType : 'json',
			success : function(data) {
				util.ajaxResult(data,'获取编号',function(ndata){
					dialog.find("#code").text(ndata[0].codeNum);
				},true);
			}
		});
	};
	
	function handleInitialFeeBaseCtr(dialog){
		initFormIput(dialog);
		util.initTimePicker(dialog);
		dialog.modal({keyboard:true});
		dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
	};
	
	function handleInitialFeeStatus(dialog,status){
		switch(status){
		case 0:
			dialog.find('#createUserDiv,.back').hide();
			dialog.find('.save,.handleFee').show();
			dialog.find('input,textarea').removeAttr('disabled');
			break;
		case 1:
			dialog.find('.save,.handleFee').hide();
			dialog.find('#createUserDiv,.back').show();
			dialog.find('input,textarea').attr('disabled', 'disabled');
			break;
		case 2:
			dialog.find('.save,.handleFee,.back').hide();
			dialog.find('#createUserDiv').show();
			dialog.find('input,textarea').attr('disabled', 'disabled');
			break;
		case 3:
			dialog.find('.save,.handleFee,.back').hide();
			dialog.find('#createUserDiv').show();
			dialog.find('input,textarea').attr('disabled', 'disabled');
			break;
		case 4:
			dialog.find('.save,.handleFee,.back').hide();
			dialog.find('#createUserDiv').show();
			dialog.find('input,textarea').attr('disabled', 'disabled');
			break;
		default:
			break;
		}
	}
	function getFeeChargeTypeVal(type) {
		switch (type) {
		case 1:
			return "仓储费";
		case 2:
			return "保安费";
		case 3:
			return "包罐费";
		case 4:
			return "超量费";
		case 8:
			return "通过费";
	}
	};
	
	function getContractTypeVal(type){
		switch (type){
		case 1:
			return "包罐";
		case 2:
			return "保量";
		case 3:
			return "全年";
		case 4:
			return "临租";
		case 5:
			return "通过";
		}
	};
	function getTaxTypeVal(type){
		switch(type){
		case 1:
			return "内贸";
		case 2:
			return "外贸";
		case 3:
			return "保税";
		default:
			return "";
		}
	};
/********************************handleFeeChargeTable******************************************************************/	
	//初始化费用项数据
	function handleFeeChargeTable(dialog,feeChargeList){
		dialog.find("#feeTbody").empty().append(initFeeChargeTableMsg(dialog,feeChargeList));	
		initFeeChargeTableControl(dialog);
	};
	
	function initFeeChargeTableMsg(dialog,chargeData){
		var html="";
		var allTotalFee=0;//总费用
		var passFeeCount=0;
		for(var i=0;i<chargeData.length;i++){
			allTotalFee=util.FloatAdd(allTotalFee,chargeData[i].totalFee,2);
			if(chargeData[i].feeType&&8==chargeData[i].feeType){
				passFeeCount=util.FloatAdd(passFeeCount,chargeData[i].feeCount,3);				
			}
			html+="<tr  class='feeTypeTd' data="+chargeData[i].feeType+"><td style='text-align:center'class='feeId' data="+util.isNull(chargeData[i].id,1)+" >"+getFeeChargeTypeVal(chargeData[i].feeType)+"</td>"+
			"<td style='text-align:center' ><input type='text' onkeyup='config.clearNoNum(this,2)' maxlength='20'  style='width:100%' class='form-control unitFee maskA feeIn' value='"+util.toDecimal2(chargeData[i].unitFee,true)+"' ></td>" +
			"<td style='text-align:center' ><input type='text' onkeyup='config.clearNoNum(this)' maxlength='16'  style='width:100%' class='form-control feeCount maskB feeIn' value='"+util.toDecimal3(chargeData[i].feeCount,true)+"' ></td>" +
			"<td style='text-align:center' ><input type='text' onkeyup='config.clearNoNum(this,2)' maxlength='20'  style='width:100%' class='form-control totalFee maskC feeAll' value='"+util.toDecimal2(chargeData[i].totalFee,true)+"' ></td>"+
			"<td style='text-align:center' ><input type='text' style='width:100%' class='form-control feeHead maskC' data='"+chargeData[i].feeHead+"' value='"+util.isNull(chargeData[i].feeHeadName)+"' ></td>"+
			"<td style='text-align:center;display:none;' class='removeFeeCharge'><a class='btn btn-xs red reFeeCharge'  href='javascript:void(0)'><span title='移除' class='glyphicon glyphicon glyphicon-remove'></span></a></td></tr>";
		}	
		var totalStr="&nbsp;&nbsp;&nbsp;合计金额：<label id='totalFee'>"+util.toDecimal2(allTotalFee,true)+"</label>&nbsp;元";
		if(passFeeCount>0){
			totalStr+="&nbsp;&nbsp;&nbsp;通过费合计数量：<label id='passFeeCount'>"+util.toDecimal3(passFeeCount,true)+"</label>&nbsp;吨";
		}
		html+="<tr id='totalTr'><td colspan='6'>"+totalStr+"</td></tr>";
		return html;
	};
	
	function initFeeChargeTableControl(dialog){
		     //单价和数量
			dialog.find(".feeIn").unbind('change').bind('change',function(){
				$this=$(this);
				var nTr=$this.closest("tr");
				$(nTr).find('.totalFee').val(util.FloatMul($(nTr).find(".unitFee").val(),$(nTr).find(".feeCount").val(),2));
				calculateTotalFee(dialog);
			});
			//总价
			dialog.find(".feeAll").unbind('change').bind('change',function(){
				calculateTotalFee(dialog);
			});
			//删除费用项
			dialog.find(".reFeeCharge").unbind('click').bind('click',function(){
				  $this=$(this);
				  $nTr=$this.closest('tr');
				  var id=$nTr.find(".feeId").attr('data');
				  $nTr.remove();
				  if(util.isNull(id,1)!=0) 
					  removeFeeCharge(id,function(){calculateTotalFee(dialog);});
				   else 
					  calculateTotalFee(dialog);
			   });
			//初始化开票抬头
			dialog.find("#feeTbody").find(".feeHead").each(function(){
				util.urlHandleTypeahead("/baseController/getClientName",$(this));
			});
		};
	
	function calculateTotalFee(dialog){
		var allTotalFee=0;
		var passFeeCount=0;
		dialog.find(".totalFee").each(function(){
			$a=$(this);
			allTotalFee=util.FloatAdd(allTotalFee,$a.val(),2);
		});
		dialog.find(".feeCount").each(function(){
			$b=$(this);
			$nTr=$b.closest('tr');
			var feeType=util.isNull($nTr.find(".feeType").attr('data'),1);
			if(feeType==8){
				passFeeCount=util.FloatAdd(passFeeCount,$b.val(),3);	
			}
		});
		if(passFeeCount!=0)
			dialog.find("#passFeeCount").text(util.toDecimal3(passFeeCount,true));
		
		dialog.find("#totalFee").text(allTotalFee);
	}
	
	function removeFeeCharge(id,callback){
		if(id){//存在id
			config.load();
			  $.ajax({
				  type:'post',
				  url:config.getDomain()+"/feecharge/delete", 
				  data:{id:id},
				  dataType:'json',
				  success:function(data){
					  util.ajaxResult(data,'删除费用项',function(){
						 if(callback) callback(); 
					  });
				  }
			  });
		  }
	};
	//初始化费用项控件
	function initAddFeeChargeBtn(dialog,feeChargeMsg){
		 dialog.find(".addFeeCharge").unbind('click').bind('click',function(){
	    	 $(getFeeChargeHtml(feeChargeMsg.clientId,feeChargeMsg.clientName)).insertBefore(dialog.find("#totalTr"));
			   dialog.find(".feeType").each(function(){
				   $this=$(this);
				   util.handleTypeahead(feeChargeMsg.feeTypeArray,$this,'value','key',undefined,function(data,obj){
					   if(data)
						 $(obj).closest('tr').attr('data',data.key);
				   });
			   });
			   initFeeChargeTableControl(dialog);
	     });    
		 
		 dialog.find(".editFeeCharge").unbind('click').bind('click',function() {
				$this = $(this);
				var key = $this.attr('key');
				if (key == 0) {
					dialog.find('.removeFeeCharge').show();
					$this.attr('key', 1);
				} else {
					dialog.find('.removeFeeCharge').hide();
					$this.attr('key', 0);
				}
			});
	};
	
	function getFeeChargeHtml(id,name){
		return "<tr class='feeTypeTd'><td><input type='text'  style='width:100%;text-align:center' class='form-control feeType'></td>"+
		  	"<td><input type='text' onkeyup='config.clearNoNum(this,2)' maxlength='20'  style='width:100%;text-align:center' class='form-control unitFee maskA feeIn' ></td>" +
			"<td><input type='text' onkeyup='config.clearNoNum(this)' maxlength='16'  style='width:100%;text-align:center' class='form-control feeCount maskB feeIn'  ></td>" +
			"<td><input type='text' onkeyup='config.clearNoNum(this,2)' maxlength='20'  style='width:100%;text-align:center' class='form-control totalFee maskC feeAll' ></td>"+
			"<td><input type='text' style='width:100%' class='form-control feeHead maskC' value='"+name+"' data='"+id+"' ></td>"+
			"<td style='text-align:center;display:none' class='removeFeeCharge'><a class='btn btn-xs red reFeeCharge'  href='javascript:void(0)'>"+
			"<span title='移除' class='glyphicon glyphicon-remove'></span></a></td></tr>";
	};
	
/**********************************************************************************************************************/	
	function getTableColumns(type){
		switch(type){
		case 1:
			return [{title : "货批号",render:function(item){
						return '<a href="javascript:void(0)" onclick="CargoLZ.openCargoLZ('+ item.cargoId+ ')">'+ item.cargoCode+ '</a>';}},
	           {title : "结算单编号",name:"code"},{title : "结算日期",name:"accountTime"},{title : "入库船信",name:"shipName"},
	           {title:"到港时间",name:"arrivalTime"},{title:"合同类型",render:function(item){
	        	   return getContractTypeVal(item.contractType);}},
	           {title : "贸易类型",render:function(item){ return getTaxTypeVal(item.taxType);}},
	           {title : "货主单位",name:"clientName"},{title : "货物名称",name:"productName"},
	           {title : "商检数(吨)",render:function(item){ return util.toDecimal3(item.goodsInspect,true);}},
	           {title : "结算金额(元)",render:function(item){return util.toDecimal2(item.totalFee,true);}},
	           {title : "结算状态",render:function(item){ return getTableStatus(util.isNull(item.status,1));}},
	           {title : "操作",render:function(item){ return getOperationsHtml(item,1);}}];
		case 2:
			return [{title:"货批号",name:"cargoCodes"},{title : "结算单编号",render:function(item){
	        	   return '<a href="javascript:void(0)" onclick="InitialFee.dialogInitialContractFee('+item.id+')">'+util.isNull(item.code)+'</a>'
	           }},{title : "结算日期",name:"accountTime"},{title : "货主单位",name:"clientName"},{title : "货物名称",name:"productName"},
	           {title : "结算金额(元)",render:function(item){return util.toDecimal2(item.totalFee,true);}},
	           {title : "结算状态",render:function(item){ return getTableStatus(util.isNull(item.status,1));}},
	           {title : "操作",render:function(item){ return getOperationsHtml(item,2);}}];
		case 3:
			return [{title : "结算单编号",render:function(item){
		      	   return '<a href="javascript:void(0)" onclick="InitialFee.dialogInitialOutboundFee('+item.id+')">'+util.isNull(item.code)+'</a>'
		         }},{title : "结算日期",name:"accountTime"},{title : "货主单位",name:"clientName"},{title : "货物名称",name:"productName"},
		         {title : "结算金额(元)",render:function(item){return util.toDecimal2(item.totalFee,true);}},
		         {title : "结算状态",render:function(item){ return getTableStatus(util.isNull(item.status,1));}},
		         {title : "操作",render:function(item){ return getOperationsHtml(item,3);}}];
		case 4:
			return [{title : "结算单编号",render:function(item){
				      	   return '<a href="javascript:void(0)" onclick="InitialFee.dialogInitialOutboundSecurityFee('+item.id+')">'+util.isNull(item.code)+'</a>'
				         }},
				         {title : "结算日期",name:"accountTime"},{title:"出库船舶",render:function(item){
				        		 return item.shipNames+(item.arrivalType==5?'<label style="color:#00aa99">(通过)<label>':'');
				         }},{title:"出库时间",name:"arrivalTime"},{title : "货物名称",name:"productName"},
				         {title : "货主单位",render:function(item){ return getChildTable(item.clientNames,function(item){return item;});}},
				         {title : "提货单位",render:function(item){ return getChildTable(item.ladingClientName,function(item){return item;});}},
				         {title:"贸易类型",render:function(item){ 
				        	 return getChildTable(item.tradeType,function(item){return getTradeTypeStr(item);});
				         }},{title : "提单号",render:function(item){ return getChildTable(item.ladingEvidence,function(item){return item;});
				         }},{title : "实发量(吨)",render:function(item){ return getChildTable(item.actualNums,function(item){return item;});
				         }},{title : "总实发量(吨)",render:function(item){return util.toDecimal3(item.actualNum,true);}},
				         {title : "结算金额(元)",render:function(item){return util.toDecimal2(item.totalFee,true);}},
				         {title : "结算状态",render:function(item){ return getTableStatus(util.isNull(item.status,1));}},
				         {title : "操作",render:function(item){ return getOperationsHtml(item,4);}}];
		case 5:
			return [{title : "货批号",render:function(item){
				return '<a href="javascript:void(0)" onclick="CargoLZ.openCargoLZ('+ item.cargoId+ ')">'+ item.cargoCode+ '</a>';
	           }},{title : "结算单编号",name:"code"},{title : "结算日期",name:"accountTime"},
	           {title : "入库船信",name:"shipName"},{title:"到港时间",name:"arrivalTime"},
	           {title:"合同类型",render:function(item){ return getContractTypeVal(item.contractType);}},
	           {title : "贸易类型",render:function(item){return getTaxTypeVal(item.taxType);}},
	           {title : "货主单位",name:"clientName"},{title : "货物名称",name:"productName"},
	           {title : "商检数(吨)",render:function(item){ return util.toDecimal3(item.goodsInspect,true);}},
	           {title : "结算金额(元)",render:function(item){return util.toDecimal2(item.totalFee,true);}},
	           {title : "结算状态",render:function(item){ return getTableStatus(util.isNull(item.status,1));}},
	           {title : "操作",render:function(item){ return getOperationsHtml(item,5);}}];
	           }
	};
	
	
	function getTableStatus(status){
	if(status==0){ return "<label style='color:#99CC33'>未提交</label>";}
	   else if(status==1){ return "<label style='color:#666699'>已提交</label>";}
	   else if(status==2){ return "<label style='color:#9966CC'>已生成账单</label>";}
	   else if(status==3){ return "<label style='color:#d64635'>已开票</label>";}
	   else if(status==4){ return "<label style='color:#FF9966'>已完成</label>";}
	};
	
	function getOperationsHtml(item,type){
		 var html='<div class="input-group-btn" style="width:80px;">';
 	    if(config.hasPermission("AINTIALFEECHECK")){
 	    html+='<a href="javascript:void(0)" style="margin:0 2px;" onclick="'+initDiffInitialFee(item,type)+'" class="btn btn-xs blue" ><span class="glyphicon glyphicon-edit" title="修改"></span></a>'
 	    }
 	    if(config.hasPermission("AINTIALFEEDELETE")&&util.isNull(item.status,1)<2){
 	    html+='<a href="javascript:void(0)" style="margin:0 2px;" onclick="InitialFee.cleanInitialFee('+item.id+','+item.type+
 	    ')" class="btn btn-xs red " ><span class="glyphicon glyphicon-remove" title="清除"></span></a>';
 	    }
 	    html+='</div>';
 	     return html;
	};
	
	function initDiffInitialFee(item,type,isShow){
		if(isShow){
			switch(type){
			case 1:
				return  InitialFee.dialogInitialInboundFee(item.id,item.code);
			case 2:
				return  InitialFee.dialogInitialContractFee(item.id);
			case 3:
				return  InitialFee.dialogInitialOutboundFee(item.id);
			case 4:
				return  InitialFee.dialogInitialOutboundSecurityFee(item.id);
			case 5:
				return  InitialFee.dialogInitialInboundPassFee(item.id,item.code);
			}	
		}else{
			switch(type){
			case 1:
				return 'InitialFee.dialogInitialInboundFee('+item.id+','+item.code+')';
			case 2:
				return 'InitialFee.dialogInitialContractFee('+item.id+')';
			case 3:
				return 'InitialFee.dialogInitialOutboundFee('+item.id+')';
			case 4:
				return 'InitialFee.dialogInitialOutboundSecurityFee('+item.id+','+item.arrivalId+')';
			case 5:
				return 'InitialFee.dialogInitialInboundPassFee('+item.id+','+item.code+')';
			}
		}
	};
	
	function getChildTable(msg,callback){
		if(msg){
			var data=msg.split(",");
			if(data!=null&&data.length>1){
			var html='<table class="table inmtable" style="margin-bottom: 0px;">';
		    for(var i=0;i<data.length;i++){
		    	html+='<tr><td style="border-bottom:1px solid #ddd;white-space:nowrap; overflow:hidden; text-overflow: ellipsis; "><label>'+callback(data[i])+'</label></td></tr>';
		    	}
		    html += '</table>';
		    return html;
			}else if(data!=null&&data.length==1){
				return '<label>'+callback(data[0])+'</label>';
			}}
		else
			return "";
	};
	
	function getTradeTypeStr(type){
		if(type){
			if(type==1)
				return '内贸';
			else 
				return '外贸';
		}else{
			return '';
		}
	};
/**************************************************************************************************************************/
	/** 清除首期费 */
	function cleanInitialFee(id, type) {
		$('body').confirm({
			  content: '确定要清除吗?',
			  callBack: function() {
			    config.load();
			    $.ajax({
			      type: 'get',
			      url: config.getDomain() + "/initialfee/delete?id=" + id + "&type=" + type,
			      dataType: 'json',
			      success: function(data) {
			        util.ajaxResult(data, '清除',function(ndata, map, mMsg) {
			          if (mMsg && mMsg.length > 0) {
			            $('body').message({type: 'warning', content: mMsg});
			          } else {
			            $('body').message({type: 'success',content: '清除成功'});
			            if(type==1||type==5||type==4)
			            	util.updateGridRow($('[data-role="storageFeeGrid"]'),{id:id,type:type,url:"/initialfee/list"});
			            else
			            	$('[data-role="storageFeeGrid"]').getGrid().refresh();
			          }
			        }, true);
			      }
			    });
			  }
			});
	};
/**************************************************************************************************************************/
	function ajaxInitialFee(id){
		if(id){
			$.ajax({
				type : 'post',
				url : config.getDomain() + "/initialfee/list",
				data : {id : id},
				dataType : 'json',
				success : function(data) {
					util.ajaxResult(data,'获取首期费',function(ndata){
						if(ndata&&ndata.length>0){
							if(ndata[0].type==1&&ndata.contractType==5)
								initDiffInitialFee(ndata[0],5,true);
							else
								initDiffInitialFee(ndata[0],ndata[0].type,true);
						}}, true);
				}
			});
		}
	};
	
	function exportExcel(obj, type) {
		var _dialog = $(obj).parents(".modal-content");
		var url = config.getDomain() + "/common/exportExcel?type=" + type;
			if ($.isNumeric(_dialog.find("#id").text())) {
				url += "&&id=" + _dialog.find("#id").text()+"&&initialType=" + _dialog.find(".initialType").text();
				window.open(url);
			} else {
				$('body').message({
					type : 'warning',
					content : '还未生成结算单'
				});
			}
	};
	
	function exportExcelList(){
		var itemTab=util.isNull($("#tabSelect").text(),1);
		if(itemTab==1){
			var type=util.isNull($(".initialClearFix").attr('data'),0);
			var url=config.getDomain()+"/initialfee/exportExcel?1=1"+util.getStrSearchCondition(getInitialCondition(parseInt(type)));
			window.open(url); 			
		}else if(itemTab==2||itemTab==5){
			StorageFee.exportExcelList(itemTab);
		}
	};
/**************************************************************************************************************************/
	return{
		init:init,
		initTable:initTable,
		dialogInitialInboundFee:dialogInitialInboundFee,//入库货批首期费
		dialogInitialContractFee:dialogInitialContractFee,//合同首期费
		dialogInitialOutboundFee:dialogInitialOutboundFee,//出库油品结算
		dialogInitialOutboundSecurityFee:dialogInitialOutboundSecurityFee,//出口保安费
		dialogInitialInboundPassFee:dialogInitialInboundPassFee,//入库通过货批结算
		addOutboundShipAndTruckTable:addOutboundShipAndTruckTable,
		getFeeChargeTypeVal:getFeeChargeTypeVal,
		getContractTypeVal:getContractTypeVal,
		getTableStatus:getTableStatus,
		getInitialCondition:getInitialCondition,
		ajaxInitialFee:ajaxInitialFee,
		cleanInitialFee:cleanInitialFee,
		exportExcel:exportExcel,
		exportExcelList:exportExcelList
	};
}();