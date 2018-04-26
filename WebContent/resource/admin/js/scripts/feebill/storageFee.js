/** 仓储费结算 */
var StorageFee=function(){
	var systemUserId, systemUserName; 
	var otherFeeCargoData;
	function init(){
		initSearchBtn();
		initTable();
	};
	function changeTab(obj,itemTab,condition){
		$("#tabSelect").text(itemTab);
		$(obj).parent().addClass("active").siblings().removeClass("active");
		cleanSearchMsg();
		initTable(condition);
	};
	function initSearchBtn(){
		getSystemUser();
		util.initDatePicker();
		util.urlHandleTypeaheadAllData("/product/select", $('#productName')); 
		util.urlHandleTypeaheadAllData("/baseController/getClientName",$('#clientName'),
				function(item) {return item.name+"["+item.code+"]"; });  
		
		$("#searchFeeData").click(function() {
			$('[data-role="storageFeeGrid"]').getGrid().search(getSearchCondition());
		});
		
		$("#reset").click(function(){
			cleanSearchMsg();
			$('[data-role="storageFeeGrid"]').getGrid().search(getSearchCondition());
		});
	};
	function cleanSearchMsg(){
		$("#clientName,#productName,#startTime,#goodsInspect,#endTime,#cargoCode,#ladingCode,#initialCode,#shipId").val("").removeAttr("data");
		$("#status,#tradeType").val(-1);
	};
	
	function getSearchCondition(){
		var itemTab=util.isNull($("#tabSelect").text(),1);
		switch(itemTab){
		case 1:
			var itemInitialTab=util.isNull($(".initialClearFix").attr('data'),1);
			return InitialFee.getInitialCondition(itemInitialTab);
		case 3:
			return {
			cargoCode: $("#cargoCode").val(),
			clientId: $("#clientName").attr('data'),
			productId: $("#productName").attr('data'),
			isFinish: $("#isFinish").val(),	
			status:  util.isNull($("#status").val(), 1),
			startTime: util.formatLong($("#startTime").val() + " 00:00:00"),
			endTime: util.formatLong($("#endTime").val() + " 23:59:59")
			};
		case 4:
			return {
			ladingCode: $("#ladingCode").val(),
			clientId:null,
			receiveClientId: $("#clientName").attr('data'),
			productId: $("#productName").attr('data'),
			isFinish: $("#isFinish").val(),	
			status:  util.isNull($("#status").val(), 1),
			startTime: util.formatLong($("#startTime").val() + " 00:00:00"),
			endTime: util.formatLong($("#endTime").val() + " 23:59:59")
			};
		case 2:
			return {
			cargoCode: $("#cargoCode").val(),
			ladingCode: $("#ladingCode").val(),
			clientId: $("#clientName").attr('data'),
			productId: $("#productName").attr('data'),
			type:$("#exceedType").val(),
			status: util.isNull($("#status").val(), 1),
			startTime: util.formatLong($("#startTime").val() + " 00:00:00"),
			endTime: util.formatLong($("#endTime").val() + " 23:59:59")
			};
		case 5:
			return {
			clientId: $("#clientName").attr('data'),
			productId: $("#productName").attr('data'),
			status:  util.isNull($("#status").val(), 1),
			startTime: util.formatLong($("#startTime").val() + " 00:00:00"),
			endTime: util.formatLong($("#endTime").val() + " 23:59:59")
			};
		};
	}
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
/***********************************************************/	
	function initTable(condition){
		var itemTab=util.isNull($("#tabSelect").text(),1);
		initSearchCtrShowOrHide(itemTab);
		if(itemTab==1){
			InitialFee.init(1);
		}else{
		if ($('[data-role="storageFeeGrid"]').getGrid() != null)
			$('[data-role="storageFeeGrid"]').getGrid().destory();
		$('[data-role="storageFeeGrid"]').grid({
			identity: getTableIdentity(itemTab),
			columns: getTableColumns(itemTab),
			searchCondition: $.extend(getSearchCondition(),condition),
			isShowIndexCol: false,
			pageSize: 10,
			showPage: 20,
			isShowPages: true,
			url: config.getDomain() + getUrl(itemTab),
			callback: function() {
				initTableGridCallBack(itemTab);
			}
		});
		}
	};
	function getTableIdentity(itemTab){
		if(itemTab==3)
			return 'cargoId';
		else if(itemTab==4)
			return 'ladingId';
		else
			return 'id';
	}
	
	function initSearchCtrShowOrHide(type){
		if (type == 1) { // 首期费
			$("#addFee,.cargoDiv,.initialDiv,.initialClearFix,.statusDiv,#printId").show();
			$(".ladingDiv,.exceedDiv,.exceedCLDiv").hide();
		} else if (type == 2) { // 超期费
			$("#addFee,.exceedDiv,.cargoDiv,.statusDiv,#printId").show();
			$(".initialDiv,.ladingDiv,.exceedCLDiv,.initialClearFix").hide();
			$("#timeDiv1").removeClass().addClass('col-md-6');
			$("#timeDiv2").removeClass().addClass('control-label col-md-2');
			$("#timeDiv3").removeClass().addClass('col-md-10');
		} else if (type == 3) { // 超期货批
			$(".cargoDiv,.exceedCLDiv").show();
			$(".addFee,.initialDiv,.exceedDiv,.ladingDiv,.initialClearFix,.statusDiv,#printId").hide();
			$("#timeDiv1").removeClass().addClass('col-md-6');
			$("#timeDiv2").removeClass().addClass('control-label col-md-2');
			$("#timeDiv3").removeClass().addClass('col-md-10');
		} else if (type == 4) { // 超期提单
			$(".ladingDiv,.exceedCLDiv").show();
			$("#addFee,.initialDiv,.exceedDiv,.cargoDiv,.initialClearFix,.statusDiv,#printId").hide();
			$("#timeDiv1").removeClass().addClass('col-md-6');
			$("#timeDiv2").removeClass().addClass('control-label col-md-2');
			$("#timeDiv3").removeClass().addClass('col-md-10');
		} else if (type == 5) { //其他费
			$("#addFee,.statusDiv,#printId").show();
			$(".initialDiv,.cargoDiv,.ladingDiv,.exceedDiv,.exceedCLDiv,.initialClearFix").hide();
			$("#timeDiv1").removeClass().addClass('col-md-6');
			$("#timeDiv2").removeClass().addClass('control-label col-md-2');
			$("#timeDiv3").removeClass().addClass('col-md-10');
		}
	};
	
	function getUrl(status) {
		switch(status){
		case 2:
			return "/exceedfee/list";
		case 3:
			return "/exceedfee/cargolist";
		case 4:
			return "/exceedfee/ladinglist";
		case 5:
			return "/otherfee/list";
		}
	};
	
	function initTableGridCallBack(status) {
		$('[data-role="storageFeeGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width', '100%');
		$('.inmtable').closest("td").css('padding', '0px');
		var params;
		var indexThStr="";
		if(status==2){
			indexThStr="th[index='0'],th[index='1'],th[index='12']";
		}else if(status==3){
			indexThStr="th[index='0'],th[index='5'],th[index='8']";
		}else if(status==4){
			indexThStr="th[index='0'],th[index='8']";
		}else if(status==5){
			indexThStr="th[index='0'],th[index='1'],th[index='7']";
		}
		$('[data-role="storageFeeGrid"]').find(indexThStr).unbind('click').bind('click', function() {
			params = getSearchCondition();
			params.indexTh =$(this).attr('index');
			$('[data-role="storageFeeGrid"]').getGrid().search(params);
		});
	};
/****************************************************************/
	function ajaxExceedFee(id) {
		if (id) {
			$.ajax({
				type : 'post',
				url : config.getDomain() + "/exceedfee/list",
				data : {id : id},
				dataType : 'json',
				success : function(data) {
					util.ajaxResult(data, '初始化', function(ndata) {
						if (ndata[0].cargoId) {
							dialogExceedFee( 1,id);
						} else if (ndata[0].ladingId) {
							dialogExceedFee(2,id);
						}
					}, true);

				}
			});
		}
	};
	function ajaxOtherFee(id) {
		if (id) {
			dialogOtherFee(id);
		}
	}
/***************************************************************/		

	function dialogExceedFee(type,id,cargoLadingId){
		$.get(config.getResource()+ "/pages/inbound/storageFee/dialog_exceedfee.jsp").done(function(data){
			var dialog=$(data);
            initExceedFeeCtr(dialog,type);	
            if(id)
            	initExceedFeeMsg(dialog,type,id);
            else
            	initBaseExceedFeeMsg(dialog,type,cargoLadingId);
		});
	};	
	
	function dialogOtherFee(id) {
		$.get(config.getResource() + "/pages/feebill/dialog_otherfee.jsp").done(
				function(data) {
					var dialog = $(data);
					initOtherFeeControl(dialog);
					initOtherFeeMsg(dialog, id);
					
				});
	};
/***********************dialogBaseExceedFee****************************************/
	function initExceedFeeCtr(dialog,type){
		initFormIput(dialog);
		util.initTimePicker(dialog); 
		dialog.find(".itemDayFeeDiv").hide();
		dialog.find('[data-dismiss="modal"]').click(function() {dialog.remove();});
		dialog.modal({keyboard : true});
		dialog.find("#exceedType").text(type);
		if(type==1){
			dialog.find(".ladingDiv").hide();
			dialog.find(".cargoDiv").show();
		}else{
			dialog.find(".ladingDiv").show();
			dialog.find(".cargoDiv").hide();
		}
		dialog.find("#startTime,#endTime").change(function() {
			getEveryDayTable(dialog);
		});	
		dialog.find(".unitFee").blur(function(){
			if ($.isNumeric(dialog.find(".unitFee").val()))
				getEveryDayTable(dialog, true);
		});
		
		dialog.find('#openTurnList').click(function(){
					if (type == 1) 
						CargoLadingTurn.init(dialog.find("#cargoId").text(), 1);
					else if (type == 2) 
						CargoLadingTurn.init(dialog.find("#ladingId").text(), 2);
				});
		
		dialog.find(".save").click(function(){
			$this = $(this);
			var status = $this.attr('key');
			if(validateExceed(dialog,status)){
				doExceedSubmit(dialog,status,type);
			}
		});
		
		dialog.find(".back").click(function(){
			var data={};
			var itemId=dialog.find("#id").text();
			data.id =itemId;
			data.type=type;
			if (type == 1) 
				data.cargoId = dialog.find("#cargoId").text();
			else if (type == 2) 
				data.ladingId = dialog.find("#ladingId").text();
			data.status=0;
					$.ajax({url : config.getDomain()+ "/exceedfee/backstatus",
							data :data,
							dataType : 'json',
							success : function(data) {
								util.ajaxResult(data,'回退',function() {
									initExceedFeeMsg(dialog,type,itemId);
								}); }
				  });
		});
		
		dialog.find(".finishFee").click(function(){
			if (type == 1) {
				var cargoId = dialog.find("#cargoId").text();
				cleanExceedCargo(cargoId, 1);
			} else if (type == 2) {
				var ladingId = dialog.find("#ladingId").text();
				cleanExceedLading(ladingId, 1);
			}
///			$(this).hide();
		});
		
	};			
	
	function validateExceed(dialog,status){
		var isPass=true;
		if (parseFloat(util.FloatSub(dialog.find("#exceedTotalFee").val(),dialog.find(".tableTotalFee").text()))!= 0){
			isPass=false;
			$("body").message({type : "error",content : "总金额与计算金额不一致"});
		}
		return isPass;
	};
	
	function doExceedSubmit(dialog,status,type){
		var exceedFee={
			id:util.isNull(dialog.find("#id").text(), 1)==0?undefined:util.isNull(dialog.find("#id").text(), 1),
			code:dialog.find("#code").text(),
			accountTime:util.formatLong(dialog.find("#accountTime").val()),	
			startTime:util.formatLong(dialog.find("#startTime").val()),
			endTime:util.formatLong(dialog.find("#endTime").val()),
			clientId:dialog.find("#clientName").attr("data"),
			productId:dialog.find("#productName").attr("data"),
			exceedFee:dialog.find("#exceedFee").val(),
			exceedTotalFee:dialog.find("#exceedTotalFee").val(),
			description:dialog.find("#description").val(),
			createTime:util.formatLong(util.currentTime(1)),
			createUserId:getSystemUser(),
			type:type,
			isFinish:0,
			status:status
		};
		if(type==1)
			exceedFee.cargoId=dialog.find("#cargoId").text();
		else if(type==2) 
			exceedFee.ladingId=dialog.find("#ladingId").text();
		var itemTab=$("#tabSelect").text();
		var params={};
		if(itemTab==2){
			params.id=exceedFee.id;
			params.url=getUrl(2);
		}else{
			if(type==1){
				params.cargoId=exceedFee.cargoId;
				params.url=getUrl(3);
				params.key='cargoId';
			}else if(type==2){
				params.ladingId=exceedFee.ladingId;
				params.url=getUrl(4);
				params.key='ladingId';
			}
		}
		
		var feeCharge={
				id :util.isNull(dialog.find("#feeId").text(), 1)==0?undefined:util.isNull(dialog.find("#feeId").text(), 1),
				type:status,
				feeType:5,
				unitFee:dialog.find('.unitFee').val(),
				totalFee:dialog.find('.totalFee').val(),
				discountFee:dialog.find('.totalFee').val(),
				feeHead:dialog.find('.feeHead').attr('data'),
				clientId :dialog.find('#clientName').attr('data'),
				productId :dialog.find('#productName').attr('data'),
				createTime:util.formatLong(util.currentTime(1)),
		};
		var eFeeDto={exceedFee:exceedFee,feeCharge:feeCharge};
		$.ajax({
			type:'post',
			url:config.getDomain()+'/exceedfee/addorupdate',
			data:{'eFeeDto':JSON.stringify(eFeeDto)},
			dataType:'json',
			success:function(data){
			 util.ajaxResult(data,(status==0?'保存':'提交'),function(ndata,nmap){
				 //
				 addOrUpdateItemExceedFeeData(getItemExceedFeeData(dialog.find('[data-role="exceedFeeGrid"]'),nmap.id),
						 function(){util.ajaxResult(data,(status==0?'保存':'提交'),function(ndata) {
								initExceedFeeMsg(dialog,type,nmap.id);
								util.updateGridRow($('[data-role="storageFeeGrid"]'),params);
								});
							});	
				});
			}
			});
	};
	
	function initExceedFeeMsg(dialog,type,id){
		$.ajax({
			type:'post',
			url:config.getDomain()+'/exceedfee/getexceedfeemsg',
			data:{id:id,type:type},
			dataType:'json',
			success:function(data){
              util.ajaxResult(data,'获取超期结算单信息',function(ndata){
            	  if(ndata&&ndata.length>0){
            		 var msg=ndata[0];
            		 dialog.find("#id").text(msg.id); 
            		 if(type==1){
            			 dialog.find("#cargoId").text(msg.cargoId);
            			 dialog.find("#cargoCode").text(msg.cargoCode).attr('data',msg.cargoId);
            		 }else{
            			 dialog.find("#ladingId").text(msg.ladingId);
            			 dialog.find("#ladingCode").text(msg.ladingCode).attr('data',msg.ladingId);
            			 dialog.find("#ladingCargoCode").text(msg.cargoCodes);
            		 }
            		 dialog.find("#code").text(msg.code);
            		 dialog.find("#accountTime").val(msg.accountTime);
            		 dialog.find("#productName").text(msg.productName).attr('data',msg.productId);
            		 dialog.find("#clientName").text(msg.clientName).attr('data',msg.clientId);
            		 dialog.find("#sendClientName").text(msg.sendClientName).attr('data',msg.sendClientId);
            		 dialog.find("#startTime").val(msg.startTime);
            		 dialog.find("#endTime").val(msg.endTime);
            		 dialog.find("#description").val(util.isNull(msg.description));
            		 dialog.find("#createUserName").text(msg.createUserName).attr('data',msg.createUserId);
            		 if(msg.feeCharge&&msg.feeCharge.length>0){
            			 var feeCharge=msg.feeCharge[0];
            			 dialog.find("#feeId").text(feeCharge.id);
            			 dialog.find(".unitFee").val(feeCharge.unitFee);
            			 dialog.find(".feeHead").val(feeCharge.feeHeadName).attr('data',feeCharge.feeHead);
            			 dialog.find("#exceedTotalFee").val(feeCharge.totalFee);
            			 getEveryDayTable(dialog, false, msg.id,msg.status);
            		 }
            		 handleStatus(dialog,msg.status);
            		 if(util.isNull(msg.isFinish)==1)
            			 dialog.find(".finishFee").hide();
            	  }
              },true);				
			}
		});
	};
	
	function handleStatus(dialog,status){
		switch(status){
		case 0:
			dialog.find('input,textarea').removeAttr('readonly');
			dialog.find(".create,.finishFee,.back").hide();
			dialog.find(".save").show();
			break;
		case 1:
			dialog.find('input,textarea').attr('readonly','readonly');
			dialog.find(".save").hide();
			dialog.find(".finishFee,.back,.create").show();
			break;
		case 2:
			dialog.find('input,textarea').attr('readonly','readonly');
			dialog.find(".save,.back").hide();
			dialog.find(".finishFee,.create").show();
			break;
		case 3:
			dialog.find('input,textarea').attr('readonly','readonly');
			dialog.find(".save,.back").hide();
			dialog.find(".finishFee,.create").show();
			break;
		case 4:
			dialog.find('input,textarea').attr('readonly','readonly');
			dialog.find(".save,.back").hide();
			dialog.find(".finishFee,.create").show();
			break;
		}
	}
	
	
	function initBaseExceedFeeMsg(dialog,type,cargoLadingId){
		getExceedCodeNum(dialog);//生产结算单表白
		dialog.find("#accountTime").datepicker("setDate",util.currentTime(0));//初始化结算单日期
		handleStatus(dialog,0);
		if(type==1)
			dialog.find("#cargoId").text(cargoLadingId);
		else
			dialog.find("#ladingId").text(cargoLadingId);
		if(cargoLadingId){
			$.ajax({
				type:'post',
				url:config.getDomain()+(type==1?'/exceedfee/cargolist':'/exceedfee/ladinglist'),
				data:(type==1?{cargoId:cargoLadingId}:{ladingId:cargoLadingId}),
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'获取基本信息',function(ndata){
						if(ndata&&ndata.length>0){
							var feeData=ndata[0];
							dialog.find("#productName").text(feeData.productName).attr("data", feeData.productId);
							dialog.find("#exceedFee").val(feeData.overtimePrice);
							
						if(type==1){//货批
							dialog.find("#cargoCode").text(feeData.cargoCode).attr("data",feeData.cargoId);
							dialog.find("#clientName").text(feeData.clientName).attr("data", feeData.clientId);
							dialog.find(".feeHead").val(feeData.clientName).attr('data', feeData.clientId);
							
							if(feeData.endTime){
								dialog.find("#startTime").datepicker("setDate",util.addDate(feeData.endTime, 1));
							 } else if (feeData.periodTime){
								dialog.find("#startTime").datepicker("setDate",util.addDate(feeData.arrivalTime,feeData.periodTime));
							 } else{
								$('body').message({type : 'warning',content : '货批没有关联合同,请前往关联。'});
							 }
									
							dialog.find("#endTime").datepicker("setDate",util.currentTime(0));	
								
						}else{//提单
							dialog.find("#sendClientName").text(feeData.sendClientName).attr("data", feeData.sendClientId);
							dialog.find("#clientName").text(feeData.receiveClientName).attr('data', feeData.receiveClientId);
							dialog.find(".feeHead").val(feeData.receiveClientName).attr('data', feeData.receiveClientId);
							dialog.find("#ladingCode").text(feeData.ladingCode).attr('data',feeData.ladingId);
							var msg = "";
							if (feeData.inboundMsg != null && feeData.inboundMsg.length > 0){
								var idMsg = feeData.inboundMsg;
								for (var i = 0; i < idMsg.length; i++) {
									if (i == idMsg.length - 1)
										msg += idMsg[i].cargoCode;
									else 
										msg += idMsg[i].cargoCode + "/";
								}
							}
							dialog.find("#ladingCargoCode").text(msg);
							
							if(feeData.endTime){
								dialog.find("#startTime").datepicker("setDate",util.addDate(feeData.endTime, 1)); 
							} else if (feeData.startLadingTime){
								dialog.find("#startTime").datepicker("setDate",util.getSubTime(feeData.startLadingTime, 1));
							} else {// 没有起计日期的话
								$('body').message({type : 'warning',content : '提单没有起计日期,请前往填写。'});
							}
							dialog.find("#endTime").datepicker("setDate",util.currentTime(0));
							
						}
						getEveryDayTable(dialog);
						}
					},true);
				}
			});
		}
	};
	
/***************************************************************/
	/** 结清超期货批 */
	function cleanExceedCargo(cargoId, isFinish) {
		if (cargoId) {
			$('body').confirm({content:'确定要'+(isFinish == 1 ? '结清' : '复位')+ '该货批吗?',
						callBack : function() {
							$.ajax({type : 'post',
								url:config.getDomain()+ "/exceedfee/cleancargo",
								data:{cargoId : cargoId,isFinish : isFinish},
								dataType:'json',
								success:function(data) {
									util.ajaxResult(data, isFinish == 1 ? '结清': '复位', function() {
										util.deleteGridRow($('[data-role="storageFeeGrid"]'));
										$(".finishFee").hide();
									});}
							});}
					});
			
			}
	};

	/** 结清超期提单 */
	function cleanExceedLading(ladingId, isFinish) {
		if (ladingId) {
			$('body').confirm({
						content:'确定要'+(isFinish==1?'结清':'复位')+'该提单吗?',
						callBack:function(){
							$.ajax({
								type:'post',
								url:config.getDomain()+ "/exceedfee/cleanlading",
								data:{ladingId : ladingId,isFinish : isFinish},
								dataType:'json',
								success:function(data) {
									util.ajaxResult(data, isFinish == 1 ? '结清': '复位', function() {
										util.deleteGridRow($('[data-role="storageFeeGrid"]'));
										$(".finishFee").hide();
									});
								}});
						}});
			}};
/***************************************************************/
	function getExceedCodeNum(dialog){
		config.load();
		$.ajax({
			type : 'post',
			url : config.getDomain() + "/exceedfee/getcodenum",
			dataType : 'json',
			success : function(data) {
				util.ajaxResult(data,'获取编号',function(ndata){
					dialog.find("#code").text(ndata[0].codeNum);
				},true);
			}
		});
	};
	/** 获取每日超期货批，超期提单列表 */
	function getEveryDayTable(obj, isPass, exceedId, status) {
		if ($(obj).find("#startTime").val().length == 10&& $(obj).find("#endTime").val().length == 10){
			$(obj).find(".itemDayFeeDiv").show();
			var columns = [{title : "时间",width : 90,name : "l_time"
					},{title : "上下家",width : 190,name : "contactClientName"
					},{title : "科目",width : 100,render : function(item) {
							return util.isNull(item.isCurrent, 1) == 1 ? "超期费" : "";
					}},{title : "性质",width : 90,render : function(item) {
							if (item.type == 0) 
								return "";
							 else if (item.type == 1) 
								return turnType == 1 ? "入库" : "调入";
							 else if (item.type == 2) 
								return "提货";
							 else if (item.type == 3) 
								return "货权转移";
							 else if (item.type == 4) 
								return "起计";
							 else if (item.type == 5) 
								return "扣损";
							
						}},{title : "调单号",width : 100,name : "l_ladingCode"
					},{title : "车船号",width : 100,name : "truckCode"
					},{title : "操作量",width : 100,render : function(item) {
							if (item.type == 1) 
								return util.toDecimal3(item.currentNum, true);
							 else if (item.type == 2 || item.type == 3|| item.type == 5) 
								return util.toDecimal3(-item.operateNum, true);
					}},{title : '结存量',width : 100,name : 'currentNum'
					},{title : '日储费',width : 100,render : function(item) {
							return util.isNull(item.isCurrent, 1) == 1 ?
									util.FloatMul(util.isNum($(obj).find(".unitFee").val()), item.currentNum, 2) : "";
						}
					},{title : '天数',width : 100,render : function(item) {
							return util.isNull(item.isCurrent, 1) == 1 ? item.col: "";
					}},{title : '储费累计',width : 110,render : function(item) {
							return util.isNull(item.isCurrent, 1) == 1 ? 
									util.FloatMul(util.FloatMul(util.isNum($(obj).find(".unitFee").val()),
											item.currentNum, 2),item.col,2) : "";
						}
					} ];

			if (isPass) {// 填写单价，本地数据加载
				var imData = $(obj).find('[data-role="exceedFeeGrid"]').getGrid().getAllItems();
				$(obj).find('[data-role="exceedFeeGrid"]').grid({
					columns : columns,
					isShowIndexCol : false,
					isShowPages : false,
					isUserLocalData : true,
					localData : imData
				});
				initEveryDayTableTotalMsg(obj, imData);
			} else {
				var params = {
					startTime : util.formatLong($(obj).find("#startTime").val()),
					endTime : util.formatLong($(obj).find("#endTime").val())
				};
				var mUrl;
				var turnType = 2;
				if (util.isNull($(obj).find("#cargoCode").attr("data"), 1) != 0) {
					params.cargoId = $(obj).find("#cargoCode").attr("data");
					turnType = 1;
					mUrl = "/exceedfee/cargoitemlist";
				} else if (util.isNull($(obj).find("#ladingCode").attr("data"), 1) != 0) {
					params.ladingId = $(obj).find("#ladingCode").attr("data");
					turnType = 2;
					mUrl = "/exceedfee/ladingitemlist";
				} 
				if (util.isNull(exceedId, 1) != 0) {
					params.exceedId = util.isNull(exceedId, 1);
					
					mUrl = "/exceedfee/getexceedfeelog";
				}
				if ($(obj).find('[data-role="exceedFeeGrid"]').getGrid() != null)
					$(obj).find('[data-role="exceedFeeGrid"]').getGrid().destory();
				$(obj).find('[data-role="exceedFeeGrid"]').grid({
							identity : 'id',
							columns : columns,
							searchCondition : params,
							isShowIndexCol : false,
							isShowPages : false,
							url : config.getDomain() + mUrl,
							callback : function(){
								initEveryDayTableTotalMsg(obj, $(obj).find('[data-role="exceedFeeGrid"]').getGrid().getAllItems());
							}
						});

			}
		}
	};
	
	 function initEveryDayTableTotalMsg(obj, iData) {
		var num = 0,colDay = 0,total = 0;
		if (iData && iData.length > 0) {
			for ( var i in iData) {
				if (iData[i] && iData[i].currentNum && iData[i].isCurrent&& iData[i].isCurrent == 1) {
					num = util.FloatAdd(num, util.FloatMul(iData[i].currentNum,iData[i].col, 3));
					total = util.FloatAdd(total, util.FloatMul(util.FloatMul(util.isNum($(obj).find(".unitFee").val()),
							iData[i].currentNum, 2), iData[i].col, 2), 2);
					colDay = util.FloatAdd(colDay, iData[i].col);
				}
			}
		} else {
			$(obj).find('div[ data-role="noData"]').text('结算单约束条件不成立。');
		}
		if ($(obj).find("#id").text()&& $(obj).find('button[key="0"]').css('display') == 'none'
				&& parseFloat(util.FloatSub($(obj).find("#exceedTotalFee").val(),total)) != 0) {
			$('body').message({
				type : 'error',
				content : '计算金额与总金额不一致请重新结算'
			});
		} else {
			$(obj).find('#exceedTotalFee').val(total);
		}
		var html = "<div class='form-group everyDayExceedFee' style='margin-left: 0px; margin-right: 0px;'>"
				+ "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：         总天数："
				+ parseFloat(colDay)+ "</label>"+ "<label>储费总金额：</label>"
				+"<label class='control-label  tableTotalFee' style='margin-left: 10px;margin-right:20px;'>"
				+ util.toDecimal2(total, true) + "</label><label> 元</label></div>";
		$(obj).find('[data-role="exceedFeeGrid"]').find(".everyDayExceedFee").remove();
		$(obj).find('[data-role="exceedFeeGrid"]').find(".grid-body").attr('style','min-height:0px;');
		$(obj).find('[data-role="exceedFeeGrid"]').find(".grid-body").parent().append(html);
	};
	function getItemExceedFeeData(obj,exceedId){
		var gridData = $(obj).getGrid().getAllItems();
		var arrayData = new Array();
		if (gridData && gridData.length > 0) {
			for (var i = 0,len=gridData.length; i < len; i++) {
				arrayData.push({
					id : util.isNull(gridData[i].lId, 0) == 0 ? undefined: gridData[i].lId,
					exceedId : exceedId,
					createTime : gridData[i].l_time,
					type : gridData[i].type,
					ladingId : gridData[i].l_ladingId,
					truckCode : gridData[i].truckCode,
					ladingCode : gridData[i].l_ladingCode,
					operateNum : gridData[i].operateNum,
					currentNum : gridData[i].currentNum,
					col : gridData[i].col,
					isCurrent : gridData[i].isCurrent,
					clientName : gridData[i].clientName,
					contactClientName : gridData[i].contactClientName
				});
			}
		}
		return arrayData;
	};
	
	function addOrUpdateItemExceedFeeData(itemData, callback){
		if (itemData && itemData.length > 0) {
			var eflogsDto = {
				efList : itemData
			}
			$.ajax({
				type : 'post',
				url : config.getDomain() + '/exceedfee/addexceedfeelogs',
				dataType : 'json',
				data : {'eflogsDto' : JSON.stringify(eflogsDto)},
				success : function(data) {
					if (callback)
						callback();
				}
			});
		}
	};
	
/**************************************************************/
	function initOtherFeeControl(dialog) {
		dialog.find(".handleOtherFee").hide();// 隐藏按钮，当货主和货品存在再显示
		util.initTimePicker(dialog);// 初始化时间
		util.urlHandleTypeaheadAllData("/client/select", dialog.find("#clientName"),undefined, undefined,function() {
					checkClientAndProduct(dialog);
				}); 
		util.urlHandleTypeaheadAllData("/product/select", dialog.find('#productName'),undefined, undefined, function() {
					checkClientAndProduct(dialog);
				}); 
		dialog.modal({keyboard : true});
		// 单价改变
		dialog.find("#unitFee").change(function() {
					var count = 0;
					var totalFee = 0;
					dialog.find(".itemCargoCount").each(function(){
						$a = $(this);
						count = util.FloatAdd(count, $a.val(), 3);
					});
					dialog.find("#feeCount").text(count);

					dialog.find(".itemCargoTotalFee").each(function(){
						$this = $(this);
						var nTr = $this.closest("tr");
						$(nTr).find(".itemCargoTotalFee").val(util.FloatMul($(nTr).find(".itemCargoCount").val(),
								dialog.find("#unitFee").val(), 2));
						totalFee = util.FloatAdd(util.isNull($this.val(), 1), totalFee, 2);
					 });
					dialog.find("#totalFee").val(totalFee);
				});

		// 添加货批
		dialog.find(".addOtherFeeCharge").click(function() {
			var html = "<tr class='itemCargo' ><td style='text-align:center'>"
				+"<input type='text'  maxlength='64'  style='width:100%;text-align:center' class='form-control cargoCode' ></td>"
				+ "<td style='text-align:center'><input type='text' onkeyup='config.clearNoNum(this,3)' maxlength='64'  "
				+"style='width:100%;text-align:center' class='form-control itemCargoCount' ></td>"
				+ "<td style='text-align:center'><input type='text' onkeyup='config.clearNoNum(this,2)'  maxlength='16'"
				+"  style='width:100%;text-align:center' class='form-control itemCargoTotalFee' ></td>"
				+ "<td style='text-align:center;display:none;' class='removeOtherFeeCharge'>"
				+"<a class='btn btn-xs red reOtherFeeCharge'  href='javascript:void(0)'><span title='移除'"
				+" class='glyphicon glyphicon glyphicon-remove'></span></a></td></tr>"
			$(html).insertBefore(dialog.find("#lastHiddenTr"));
			initOtherFeeTableControl(dialog);
		});
		// 编辑
		dialog.find(".editOtherFeeCharge").click(function() {
			$this = $(this);
			var key = $this.attr('key');
			if (key == 0) {
				dialog.find('.removeOtherFeeCharge').show();
				$this.attr('key', 1);
			} else {
				dialog.find('.removeOtherFeeCharge').hide();
				$this.attr('key', 0);
			}
		});

		dialog.find(".save").click(
				function() {
					$this = $(this);
					var status = $this.attr('key');
					var id = util.isNum(dialog.find("#id").text());
					var data = {
						id : id,
						code : dialog.find("#code").text() + "",
						clientId : dialog.find("#clientName").attr("data"),
						productId : dialog.find("#productName").attr("data"),
						description : dialog.find("#description").val(),
						createUserId : systemUserId,
						createTime : util.formatLong(util.currentTime(1)),
						accountTime : util.formatLong(dialog.find("#accountTime").val()),
						status : status
					};
					var feecharge = {
						id : util.isNum(dialog.find("#feechargeId").text()),
						type : status,// 0，表示还未提交的费用项，1表示已经提交 的费用项
						feeType : 6,// 费用类型1.首期费2.保安费3,包罐费,4.超量费,5.超期费，6，其他费
						unitFee : dialog.find("#unitFee").val(),// 费用单价
						feeCount : dialog.find("#feeCount").text(),// 费用数量
						totalFee : dialog.find("#totalFee").val(),// 全部费用
						discountFee : dialog.find("#totalFee").val(),// 全部费用
						feeHead : dialog.find("#clientName").attr("data"),// 开票抬头
						productId : dialog.find("#productName").attr("data"),// 货品id
						initialId : id,// 首期费id
						createTime : util.formatLong(util.currentTime(1)),
						clientId : dialog.find("#clientName").attr("data"),// 货主id
					};
					var otherFeeCargos = new Array();
					dialog.find(".itemCargo").each(function() {
						$this = $(this);
						otherFeeCargos.push({
							id : util.isNum($this.find(".feeId").attr('data')),
							otherFeeId : id,
							cargoId : util.isNull($this.find(".cargoCode").attr('data'),1),
							count : $this.find(".itemCargoCount").val(),
							itemFee : $this.find(".itemCargoTotalFee").val()
						});
					});
					data.otherFeeCargos = otherFeeCargos;
					data.feecharge = feecharge;
					$.ajax({
						type : "post",
						url : config.getDomain()+ (id ? "/otherfee/update" : "/otherfee/add"),
						dataType : 'json',
						data : {"otherfeedto" : JSON.stringify(data)},
						success : function(data) {
							util.ajaxResult(data, status == 0 ? "保存" : "提交",function(ndata, nmap) {
										if (id) {
											initOtherFeeMsg(dialog, id);
											util.updateGridRow($('[data-role="storageFeeGrid"]'),{id:id,url:"/otherfee/list"});
										} else if (nmap.id) {
											initOtherFeeMsg(dialog, nmap.id);
											util.deleteGridRow($('[data-role="storageFeeGrid"]'));
										}
									});
						}});
				});

		dialog.find(".back").click(function() {
			var itemId = dialog.find("#id").text();
			if (itemId){
				var data = {id : itemId,status : 0};
				$.ajax({url : config.getDomain()+ "/otherfee/update",
							data : {"otherfeedto" : JSON.stringify(data)},
							dataType : 'json',
							success : function(data) {
								util.ajaxResult(data,'回退',function() {
									initOtherFeeMsg(dialog, itemId);
									util.updateGridRow($('[data-role="storageFeeGrid"]'),{id:itemId,url:"/otherfee/list"});
								});
							}});
				}
			});

	};
	function checkClientAndProduct(dialog, isHide) {
		if (dialog.find("#clientName").attr("data")&& dialog.find("#productName").attr("data")) {
			$.ajax({
				type : 'post',
				url : config.getDomain() + "/otherfee/getcargomsg",
				dataType : 'json',
				data : {
					clientId : dialog.find("#clientName").attr("data"),
					productId : dialog.find("#productName").attr("data")
				},
				success : function(data) {
					util.ajaxResult(data, "获取货批信息", function(ndata) {
						otherFeeCargoData = ndata;
						if (!isHide) {
							dialog.find(".handleOtherFee").show();
						}
					}, true);
				}
			});
		}
		if (dialog.find(".itemCargo")) 
			dialog.find(".itemCargo").remove();
	};

	function initOtherFeeTableControl(dialog) {

		dialog.find(".cargoCode").each(function() {
			if (otherFeeCargoData)
				util.handleTypeahead(otherFeeCargoData, $(this), 'cargoCode');
		});
		dialog.find(".itemCargoCount").unbind("change").bind('change',function() {
			$this = $(this);
			var nTr = $this.closest("tr");
			$(nTr).find(".itemCargoTotalFee").val(util.FloatMul($(this).val(), dialog.find("#unitFee").val(), 2));
			var totalFee = 0;
			dialog.find(".itemCargoTotalFee").each(function() {
						$a = $(this);
						totalFee = util.FloatAdd(totalFee, util.isNull($a.val(), 1), 2);
					});
			dialog.find("#feeCount").text(util.FloatAdd(util.isNull(dialog.find("#feeCount").text(), 1), util.isNull($this.val(), 1), 3));
			dialog.find("#totalFee").val(totalFee);
		 });

		dialog.find(".itemCargoTotalFee").unbind("change").bind('change',function() {
			var totalFee = 0;
			dialog.find(".itemCargoTotalFee").each(function() {
				$a = $(this);
				totalFee = util.FloatAdd(totalFee, util.isNull($a.val(), 1), 2);
			});
			dialog.find("#totalFee").val(totalFee);
		});

		dialog.find(".reOtherFeeCharge").unbind("click").bind('click',function() {
					$this = $(this);
					$nTr = $this.closest('tr');
					var id = $nTr.find(".feeId").attr('data');
					if (id) {// 存在id
						$.ajax({
							type : 'post',
							url : config.getDomain() + "/otherfee/deletefeecargo",
							data : {id : id},
							dataType : 'json',
							success : function(data){
								$nTr.remove();
								var count = 0;
								var totalFee = 0;
								dialog.find(".itemCargoTotalFee").each(function() {
											$a = $(this);
											totalFee = util.FloatAdd(totalFee, util.isNull($a.val(), 1), 2);
										});
								dialog.find(".itemCargoCount").each(function() {
											$a = $(this);
											count = util.FloatAdd(count, util.isNull($a.val(), 1), 2);
										});
								dialog.find("#feeCount").text(count);
								dialog.find("#totalFee").val(totalFee);
							}
						});
					} else {
						$nTr.remove();
						var count = 0;
						var totalFee = 0;
						dialog.find(".itemCargoTotalFee").each(function() {
									$a = $(this);
									totalFee = util.FloatAdd(totalFee, util.isNull($a.val(), 1), 2);
								});
						dialog.find(".itemCargoCount").each(function() {
							$a = $(this);
							count = util.FloatAdd(count, $a.val(), 3);
						});
						dialog.find("#feeCount").text(count);
						dialog.find("#totalFee").val(totalFee);
					}});

	}
	function initOtherFeeMsg(dialog, id) {

		if (!id) {// 不存在id，添加，获取通知单号
			config.load();
			$.ajax({
				type : 'post',
				url : config.getDomain() + "/otherfee/getcodenum",
				dataType : 'json',
				success : function(data) {
					util.ajaxResult(data, "获取编号", function(ndata) {
						dialog.find("#code").text(ndata[0].codeNum);
					}, true);
				}
			});
			dialog.find("#accountTime").datepicker("setDate", util.currentTime(0));// 初始化结算时间
			dialog.find("input,textarea").removeAttr('readonly');
			dialog.find(".create,.back").hide();
			dialog.find(".save").show();
		} else {
		$.ajax({
			type : 'post',
			url : config.getDomain() + "/otherfee/list",
			data : {id : id},
			dataType : 'json',
			success : function(data) {
			util.ajaxResult(data,"获取数据",function(ndata) {
				if (ndata) {
					dialog.find("#id").text(ndata[0].id);
					dialog.find("#code").text(ndata[0].code + "");
					dialog.find("#feechargeId").text(ndata[0].feechargeId);
					dialog.find("#feechargeType").text(ndata[0].feechargeType);
					dialog.find("#feeCount").text(ndata[0].feeCount);
					dialog.find("#unitFee").val(util.toDecimal2(ndata[0].unitFee,true));
					dialog.find("#totalFee").val(util.toDecimal2(ndata[0].totalFee,true));
					dialog.find("#clientName").val(ndata[0].clientName).attr("data",ndata[0].clientId);
					dialog.find("#productName").val(ndata[0].productName).attr("data",ndata[0].productId);
					dialog.find("#accountTime").datepicker("setDate",ndata[0].accountTime);
					dialog.find("#createUserName").text(util.isNull(ndata[0].createUserName));
					dialog.find("#description").val(util.isNull(ndata[0].description));
					checkClientAndProduct(dialog,ndata[0].status == 1 ? true: false);
					if(ndata[0].status==0){
						dialog.find("input,textarea").removeAttr('readonly');
						dialog.find(".create,.back").hide();
						dialog.find(".save").show();
					}else if(ndata[0].status==1){
						dialog.find("input,textarea").attr('readonly','readonly');
						dialog.find(".create,.back").show();
						dialog.find(".save").hide();
					}else if(ndata[0].status>1){
						dialog.find("input,textarea").attr('readonly','readonly');
						dialog.find(".create,.back,.save").hide();
					}

					$.ajax({type : 'post',
							url : config.getDomain()+ "/otherfee/getotherfeecargo",
							dataType : 'json',
							data : {id : ndata[0].id},
							success : function(data) {
							 util.ajaxResult(data,"获取费用货批信息",function(indata) {
							 if (indata) {
								var html = "";
								for (var i = 0; i < indata.length; i++) {
									html += "<tr class='itemCargo' ><td class='hidden'><input class='feeId' data='"+ indata[i].id
									+ "'/></td>"
									+ "<td style='text-align:center'><input type='text'  maxlength='64'  "
									+"style='width:100%;text-align:center' class='form-control cargoCode' value='"
									+ util.isNull(indata[i].cargoCode)+"' data='"+ indata[i].cargoId+ "' ></td>"
									+ "<td style='text-align:center'><input type='text' onkeyup='config.clearNoNum(this,3)' "
									+" maxlength='64'  style='width:100%;text-align:center' class='form-control itemCargoCount'"
									+" value='"+ util.toDecimal3(indata[i].count,true)+ "' ></td>"
									+ "<td style='text-align:center'><input type='text' onkeyup='config.clearNoNum(this,2)' "
									+" maxlength='16'  style='width:100%;text-align:center' class='form-control itemCargoTotalFee'"
									+" value='"+ util.toDecimal2(indata[i].itemFee,true)+ "' ></td>"
									+ "<td style='text-align:center;display:none;' class='removeOtherFeeCharge'>"
									+"<a class='btn btn-xs red reOtherFeeCharge'  href='javascript:void(0)'>"
									+"<span title='移除' class='glyphicon glyphicon glyphicon-remove'></span></a></td></tr>"
								}
								dialog.find(".itemCargo").remove();
								$(html).insertBefore(dialog.find("#lastHiddenTr"));
								initOtherFeeTableControl(dialog);
								if (ndata[0].status == 1) {
									dialog.find('input,textarea').attr('readonly','readonly');
								}
								}
							},
							true);
					 }});
				}
			}, true);
			}
		});
		};
	};
/***************************************************************/	
	function getTableColumns(type) {
		switch (type) {
		case 2:
			return [{
				title:
				"结算编号",
				render: function(item) {
					if(config.hasPermission("AEXCEEDFEECHECK")){
				 	    return '<a href="javascript:void(0)" onclick="StorageFee.dialogExceedFee('+item.type+','+item.id+')">'
				 	    +item.code+'</a>';
				 	    }else{
				 	    	return item.code;
				 	    }
				}
			},
			{
				title: "结算日期",
				name: "accountTime"
			},
			{
				title: "货批号",
				render: function(item) {
					if (item.cargoCode != null) {
						return '<a href="javascript:void(0)" onclick="CargoLZ.openCargoLZ(' + item.cargoId + ')">' 
						+ item.cargoCode + '</a>';
					} else {
						return getChildTable(item,function(data) {
							return '<a onclick="CargoLZ.openCargoLZ(' + data.cargoId + ',' + item.ladingId +
							')" href="javascript:void(0)">' + util.isNull(data.cargoCode) + '</a>';
						});
					}
				}
			},
			{
				title: '转卖数量',
				render: function(item) {
					return (item.cargoCode ? '': getChildTable(item,
					function(data) {
						return util.toDecimal3(data.goodsTotal, true);
					}));
				}
			},
			{
				title: "货主单位",
				render: function(item) {
					return (item.ladingCode ? item.receiveClientName: item.clientName);
				}
			},
			{
				title: "货品",
				name: "productName"
			},
			{
				title: "上下家单位",
				render: function(item) {
					return (item.ladingCode ? item.sendClientName: '');
				}
			},
			{
				title: "提单号",
				name: "ladingCode"
			},
			{
				title: "单价(元)",
				render: function(item) {
					return util.toDecimal2(item.exceedFee, true);
				}
			},
			{
				title: "金额(元)",
				render: function(item) {
					return util.toDecimal2(item.exceedTotalFee, true);
				}
			},
			{
				title: "起计日期",
				name: "startTime"
			},
			{
				title: "止计日期",
				name: "endTime"
			},
			{
				title: "结算状态",
				render: function(item) {
					return InitialFee.getTableStatus(item.status);
				}
			},
			{
				title: "操作",
				render: function(item) {
				var html='<div class="input-group-btn" style="width:80px;">';
		 	    if(config.hasPermission("AEXCEEDFEECHECK")){
		 	    html+='<a href="javascript:void(0)" style="margin:0 2px;" onclick="StorageFee.dialogExceedFee('
		 	    	+item.type+','+item.id+')" class="btn btn-xs blue" ><span class="glyphicon glyphicon-edit" title="修改"></span></a>'
		 	    }
		 	    if(config.hasPermission("AEXCEEDFEEDELETE")&&util.isNull(item.status,1)<2){
		 	    html+='<a href="javascript:void(0)" style="margin:0 2px;" onclick="StorageFee.cleanExceedFee('+item.id+ 
		 	    ')" class="btn btn-xs red " ><span class="glyphicon glyphicon-remove" title="清除"></span></a>';
		 	    }
		 	  
		 	    html+='</div>';
		 	     return html;
				}
			}];
		case 3:
			return [{
				title:
				"货批号",
				render: function(item) {
					return '<a href="javascript:void(0)" onclick="CargoLZ.openCargoLZ(' + item.cargoId + ')">' 
					+ item.cargoCode + '</a>';
				}
			},
			{
				title: "货主",
				name: "clientName"
			},
			{
				title: "货品",
				name: "productName"
			},
			{
				title: "数量(吨)",
				render: function(item) {
					return util.toDecimal3(item.goodsTotal, true);
				}
			},
			{
				title: "入库船信",
				render: function(item) {
					var msgName = util.isNull(item.shipName) + "/" + util.isNull(item.shipRefName);
					return (msgName.length == 2 ? "": msgName);
				}
			},
			{
				title: "到港时间",
				name: "arrivalTime"
			},
			{
				title: "合同号",
				name: "contractCode"
			},
			{
				title: "合同类型",
				render: function(item) {
					return InitialFee.getContractTypeVal(item.contractType);
				}
			},
			{
				title: "入库日期",
				name: "arrivalTime"
			},
			{
				title: "合同周期",
				name: "periodTime"
			},
			{
				title: "起计日期",
				render: function(item) {
					var status = getExceedStatus(item.status, item.feebillCount);
					if (status == 0) {
						return util.addDate(item.arrivalTime, item.periodTime);
					} else if (status == 1 || status == 2) {
						return util.getSubTime(item.startTime, 1);
					} else if (status == 3) {
						return util.addDate(util.getSubTime(item.endTime, 1), 1);
					}
				}
			},
			{
				title: '结算状态',
				render: function(item) {
					if (item.isFinish == 1)
					return "<label style='color:#ff7711;margin-left: 8px;'>已结清</label>";
					else
					return getStrStatus(getExceedStatus(item.status, item.feebillCount), item.eFCount);
				}
			},
			{
			title: "操作",
			render: function(item) {
			var html = '<div class="input-group-btn" style="width:100px;">';
			if (item.isFinish == 0) {
				if (getExceedStatus(item.status, item.feebillCount)==0||getExceedStatus(item.status, item.feebillCount)==3){
					html += '<a href="javascript:void(0)" style="margin:0 2px;font-size: 9px;"'
					 +'onclick="StorageFee.dialogExceedFee(1,undefined,'+item.cargoId+')"  class="btn btn-xs blue">去结算</a>'
				}
				html += '<a href="javascript:void(0)" style="margin:0 2px;" onclick="StorageFee.cleanExceedCargo('
				 + item.cargoId + ',1)" class="btn btn-xs blue " ><span class="glyphicon glyphicon-check" title="结清"></span></a>'
			} else if (item.isFinish == 1) {
				html += '<a href="javascript:void(0)" style="margin:0 2px;" onclick="StorageFee.cleanExceedCargo(' 
					+ item.cargoId + ',0)" class="btn btn-xs blue " ><span class="glyphicon glyphicon-check" title="复位"></span></a>'
			}
			html+='<a href="javascript:void(0)" style="margin:0 2px;" onclick="ExceedCleanLog.initDialog('+item.cargoId+ 
			')" class="btn btn-xs blue " ><span class="fa fa-bookmark" title="日志"></span></a>';
			html += '<a href="javascript:void(0)" style="margin:0 2px;" onclick="StorageFee.checkItemExceedFee(' 
				+ item.cargoId + ',1)"  class="btn btn-xs blue" ><span title="查看" class="glyphicon glyphicon-eye-open"></span></a>'
				+ '<a href="javascript:void(0)" style="margin:0 2px;" onclick="CargoLadingTurn.init(' 
				+ item.cargoId + ',1)"  class="btn btn-xs blue" ><span title="货批流转" class="fa fa-bars"></span></a></div>';
			return html;}
			}];
		case 4:
			return [{
				title:
				"提单号",
				render: function(item) {
					return '<a href="#/ladingEdit?id=' + item.ladingId + '&type=2">' + item.ladingCode + '</a>';
				}
			},
			{
				title: '货批号',
				render: function(item) {
					return getChildTable(item,function(data) {
						return '<a onclick="CargoLZ.openCargoLZ(' + data.cargoId + ',' + item.ladingId +
						')" href="javascript:void(0)">'+ util.isNull(data.cargoCode) + '</a>';
					});

				}
			},
			{
				title: '转卖数量',
				render: function(item) {
					return getChildTable(item,
					function(data) {
						return util.toDecimal3(data.goodsTotal, true);
					});
				}
			},
			{
				title: '入库船信',
				render: function(item) {
					return getChildTable(item,
					function(data) {
						return util.isNull(data.inboundMsg);
					});
				}
			},
			{
				title: "发货单位",
				name: "clientName"
			},
			{
				title: "接收单位",
				name: "receiveClientName"
			},
			{
				title: "货品",
				name: "productName"
			},
			{
				title: "数量(吨)",
				render: function(item) {
					return util.toDecimal3(item.goodsTotal, true);
				}
			},
			{
				title: "创建时间",
				name: "createTime"
			},
			{
				title: "起计日期",
				render: function(item) {
					var status = getExceedStatus(item.status, item.feebillCount);
					if (status == 0) {
						return util.getSubTime(item.startLadingTime, 1);
					} else if (status == 1 || status == 2) {
						return util.getSubTime(item.startTime, 1);
					} else if (status == 3) {
						return util.addDate(util.getSubTime(item.endTime, 1), 1);
					}
				}
			},
			{
				title: '结算状态',
				render: function(item) {
					if (item.isFinish == 1)
						return "<label style='color:#ff7711;margin-left: 8px;'>已结清</label>";
						else
					return getStrStatus(getExceedStatus(item.status, item.feebillCount), item.eFCount);
				}
			},
			{
				title: "操作",
				render: function(item, name, index) {
					var status = getExceedStatus(item.status, item.feebillCount);
					
					var html = '<div class="input-group-btn" style="width:100px;">';
					if (item.isFinish == 0) {
						if (status == 0 || status == 3) {
							html += '<a href="javascript:void(0)" style="margin:0 2px;font-size: 9px;"'
								+' onclick="StorageFee.dialogExceedFee(2,undefined,'+item.ladingId+
								')" class="btn btn-xs blue">去结算</a>'
						}
						html += '<a href="javascript:void(0)" style="margin:0 2px;" onclick="StorageFee.cleanExceedLading('
							+ item.ladingId + ',1)" class="btn btn-xs blue " >'
							+'<span class="glyphicon glyphicon-check" title="结清"></span></a>'
					} else if (item.isFinish == 1) {
						html += '<a href="javascript:void(0)" style="margin:0 2px;" onclick="StorageFee.cleanExceedLading('
							+ item.ladingId + ',0)" class="btn btn-xs blue " >'
							+'<span class="glyphicon glyphicon-check" title="复位"></span></a>'
					}
					html+='<a href="javascript:void(0)" style="margin:0 2px;" onclick="ExceedCleanLog.initDialog(undefined,'+item.ladingId+ 
					')" class="btn btn-xs blue " ><span class="fa fa-bookmark" title="日志"></span></a>';
					html += '<a href="javascript:void(0)" style="margin:0 2px;" onclick="StorageFee.checkItemExceedFee('
						+ item.ladingId + ',2)"  class="btn btn-xs blue" >'
						+'<span title="查看" class="glyphicon glyphicon-eye-open"></span></a>' 
						+ '<a href="javascript:void(0)" style="margin:0 2px;" onclick="CargoLadingTurn.init('
						+ item.ladingId + ',2)"  class="btn btn-xs blue" ><span title="货批流转" class="fa fa-bars"></span></a></div>';
					return html;
				}
			}];
		case 5:
			return [{
				title: "结算单编号",
				render: function(item) {
					if(config.hasPermission("AOTHERFEECHECK"))
						return '<a href="javascript:void(0)" onclick="StorageFee.dialogOtherFee(' + item.id + ')">' + item.code + '</a>';
					else
						return item.code;
				}
			},
			{
				title: "结算日期",
				name: "accountTime"
			},
			{
				title: "货主",
				name: "clientName"
			},
			{
				title: "货品",
				name: "productName"
			},
			{
				title: "结算单价",
				render: function(item) {
					return util.toDecimal2(item.unitFee, true);
				}
			},
			{
				title: "数量",
				render: function(item) {
					return util.toDecimal3(item.feeCount, true);
				}
			},
			{
				title: '总金额',
				render: function(item) {
					return util.toDecimal2(item.totalFee, true);
				}
			},
			{
				title: "当前状态",
				render: function(item) {
					return InitialFee.getTableStatus(item.status);
				}
			},
			{
				title: "操作",
				render: function(item) {

					var html='<div class="input-group-btn" style="width:80px;">';
			 	    if(config.hasPermission("AOTHERFEECHECK")){
			 	    html+='<a href="javascript:void(0)" style="margin:0 2px;"'
						+'onclick="StorageFee.dialogOtherFee(' + item.id + ')" class="btn btn-xs blue" >'
						+'<span class="glyphicon glyphicon-edit" title="修改"></span></a>'
			 	    }
			 	    if(config.hasPermission("AOTHERFEEDELETE")&&util.isNull(item.status,1)<2){
			 	    html+='<a href="javascript:void(0)" style="margin:0 2px;" onclick="StorageFee.cleanOtherFee('  
						+ item.id + ')" class="btn btn-xs red " >'
						+'<span class="glyphicon glyphicon-remove" title="清除"></span></a>';
			 	    }
			 	    html+='</div>';
			 	     return html;
					
				}
			}];

		}
	};
	 
	function getExceedStatus(status, count) {
		if (status == 0 && count == 0) {
			return 0;  
		} else if (status == 0 && count > 0) {
			return 1;  
		} else if (status>=1 && count > 0) {
			return 2;  
		} else if (status >= 1 && count == 0) {
			return 3;  
		} else {
			return 0;
		}
	};
	 
	function getStrStatus(status, eFCount) {
		if (status == 0 || status == 3) {
			if (util.isNull(eFCount, 1) > 0) {
				return "<label style='color:#99CC33;margin-left: 8px;'>未结算</label><label style='color:#ff0011'>(" 
				+ eFCount + ")<label>";
			} else {
				return "<label style='color:#99CC33;margin-left: 8px;'>未结算</label>";
			}
		} else if (status == 1) {
			return "<label style='color:#3399CC;margin-left: 8px;'>结算未提交</label>";
		} else if (status == 2) {
			return "<label style='color:#FF9966;margin-left: 8px;'>已提交</label>";
		}
	};
	
	function getChildTable(item, callback) {
		if (item.inboundMsg != null) {
			var data = item.inboundMsg;
			if (data != null && data.length > 1) {
				var html = '<table class="table inmtable" style="margin-bottom: 0px;">';
				for (var i = 0; i < data.length; i++) {
					html += '<tr><td style="border-bottom:1px solid #ddd;white-space:nowrap; overflow:hidden; text-overflow: ellipsis;"><label>' + callback(data[i]) + '</label></td></tr>';
				}
				html += '</table>';
				return html;
			} else if (data != null && data.length == 1) {
				return '<label>' + callback(data[0]) + '</label>';
			} else {
				return "";
			}
		}
	};
	
/***************************************************************/
	/** 导出exel */
	function exportXML(obj, type) {
		var _dialog = $(obj).parents(".modal-content");
		var url = config.getDomain() + "/common/exportExcel?type=" + type;
		if (type == 50) {
			if ($.isNumeric(_dialog.find("#id").text())) {
				url += "&&name=" + util.getSubTime(util.currentTime(1), 1)
						+ "&&id=" + _dialog.find("#id").text();
				url += "&&initialType=" + _dialog.find(".initialType").text();
				window.open(url);
			} else {
				$('body').message({
					type : 'warning',
					content : '还未生成结算单'
				});
			}
		} else if (type == 51) {
			if ($.isNumeric(_dialog.find("#id").text())) {
				url += "&&name=" + util.getSubTime(util.currentTime(1), 1)
						+ "&&id=" + _dialog.find("#id").text();
				window.open(url);
			} else {
				$('body').message({
					type : 'warning',
					content : '还未生成结算单'
				});
			}
		} else if (type == 53) {
			if ($.isNumeric(_dialog.find("#id").text())) {
				url += "&&name=" + util.getSubTime(util.currentTime(1), 1)
						+ "&&id=" + _dialog.find("#id").text() + "&&exceedType="
						+ _dialog.find("#exceedType").text();
				window.open(url);
			} else {
				$('body').message({
					type : 'warning',
					content : '还未生成结算单'
				});
			}
		} else if (type == 54) {
			url += "&&name=" + util.getSubTime(util.currentTime(1), 1);
			window.open(url);
		} else if (type == 56) {
			if ($.isNumeric(_dialog.find("#id").text())) {
				url += "&&name=" + util.getSubTime(util.currentTime(1), 1)
						+ "&&id=" + _dialog.find("#id").text();
				window.open(url);
			} else {
				$('body').message({
					type : 'warning',
					content : '还未生成结算单'
				});
			}
		}
	};
/**************************************************************/
	function addStorageFee(){
		var itemTab=$("#tabSelect").text();
		if(itemTab==1||!itemTab){//首期费
			var itemInitialTab=$(".initialClearFix").attr('data');
			if(itemInitialTab==2)
				InitialFee.dialogInitialContractFee();
			else if(itemInitialTab==3)
				InitialFee.dialogInitialOutboundFee();
		}else if(itemTab==5){
		 StorageFee.dialogOtherFee();//其他基本费用
		}
	};
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
			            $('[data-role="storageFeeGrid"]').getGrid().refresh();
			          }
			        }, true);
			      }
			    });
			  }
			});
	};

	/** 清除超期费 */
	function cleanExceedFee(id) {
		$('body').confirm({
					content:'确定要清除吗?',
					callBack:function() {
						config.load();
						$.ajax({
							type:'get',
							url:config.getDomain()+"/exceedfee/delete?id="+id,
							dataType:'json',
							success:function(data) {
							util.ajaxResult(data,'清除',function(ndata,map, mMsg){
									if (mMsg && mMsg.length > 0) {
										$('body').message({type:'warning',content:mMsg});
									} else {
										$('body').message({type : 'success',content : '清除成功'});
										util.deleteGridRow($('[data-role="storageFeeGrid"]'));
									}
								}, true);
							}
						});
					}
				});
	};

	function cleanOtherFee(id){
		if (id) {
			$('body').confirm({
				content:'确定要清除吗?',
				callBack:function() {
							config.load();
							$.ajax({type:'get',
								url:config.getDomain()+ "/otherfee/delete?id=" + id,
								dataType : 'json',
								success : function(data) {
									util.ajaxResult(data,'清除',function(ndata,map, mMsg) {
										if (mMsg && mMsg.length > 0) {
											$('body').message({type:'warning',content:mMsg});
										} else {
											$('body').message({type:'success',content:'清除成功'});
											util.deleteGridRow($('[data-role="storageFeeGrid"]'));
										}
									}, true);}
							});}
					});}
	}
	/** 结清超期费 */
	function settleExceedFee(id) {
		if (id) {
			$('body').confirm({
						content : '确定要结清吗?',
						callBack : function() {
							config.load();
							$.ajax({type:'post',
								url:config.getDomain()+"/exceedfee/update",
								data:{id:id,isFinish:1},
								dataType:'json',
								success:function(data) {
									util.ajaxResult(data,'结清', function(){util.deleteGridRow($('[data-role="storageFeeGrid"]'));}, true);
								}
							});
						}
					});
		}
	};

	/** 查看超期货批，超期提单的结算单 */
	function checkItemExceedFee(id, type){
		var params=new Object();
		params.type=type;
		if (type==1){
			params.cargoId=id;
		} else if(type==2){
			params.ladingId=id;
		}
		$("#reback").show();
		initBackControl(params);
		changeTab($("#tab5"),2,params);
	}

	function initBackControl(params) {
		$("#reback").unbind("click").bind('click',function() {
			if (params.type == 1) {
				params.type=undefined;
				changeTab($("#tab3"),3,params);
			} else if (params.type == 2) {
				params.type=undefined;
				changeTab($("#tab4"), 4,params);
			}
			$(this).hide();
		});

	}
/**************************************************************/
	function exportExcelList(item){
		var url=config.getDomain()+(item==2?'/exceedfee/exportExcelList?1=1':'/otherfee/exportExcelList?1=1')
		+util.getStrSearchCondition(getSearchCondition());
		window.open(url);
	};
/*************************************************************/
	return {
		init:init,
		changeTab:changeTab,
		ajaxExceedFee : ajaxExceedFee,
		ajaxOtherFee : ajaxOtherFee,
		dialogExceedFee:dialogExceedFee,
		dialogOtherFee : dialogOtherFee,
		cleanExceedCargo : cleanExceedCargo,
		cleanExceedLading : cleanExceedLading,
		cleanInitialFee:cleanInitialFee,
		cleanExceedFee:cleanExceedFee,
		cleanOtherFee:cleanOtherFee,
		addStorageFee:addStorageFee,
		checkItemExceedFee:checkItemExceedFee,
		exportXML:exportXML,
		exportExcelList:exportExcelList
	};
}();