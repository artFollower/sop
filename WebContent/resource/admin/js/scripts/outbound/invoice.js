var Invoice = function() {
	var LODOP; //声明为全局变量 
	//打印控件
	var CreatedOKLodop7766=null;
	var columnCheckArray;
	/**初始化开票列表页 */
	function initList(){
		initSearchCtr();
		initTable();
	}
	function getSearchCondition(){
		return {
				vsName:$("#vehicleShipNo").val(),
		       productId:util.isNull($("#productName").attr("data"),1),
		       clientId:util.isNull($("#clientName").attr("data"),1),
		       deliverType:$("#deliverType").val(),//车发，船发，全部
		       actualType:$("#actualType").val(),//待提，已提
		       ladingEvidence:$("#ladingEvidence").val(),//提单号
		       serial:$("#serial").val()
	};
	}
	function initSearchCtr(){
		initFormIput();
		util.urlHandleTypeaheadAllData("/product/select",$('#productName')); // 初始化货品
		util.urlHandleTypeaheadAllData("/baseController/getClientName",$('#clientName'),function(item){return item.name+"["+item.code+"]";});
		//初始化按钮操作
	 	$(".btn-add").click(function(){
			window.location.href = "#/invoice/make?i="+Math.random();
		});
		//查询下拉
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		//搜索
		$(".search").click(function(){
	       $('[data-role="invoicelGrid"]').getGrid().search(getSearchCondition());
		});
		//重置
		$(".reset").click(function(){
			$("#vehicleShipNo,#serial,#ladingEvidence").val('');
			$("#productName").val("").removeAttr();
			$("#clientName").val("").removeAttr();
			$("#deliverType").val(0);
			$("#actualType").val(-1);
			$(".search").click();
		});
	};
	function initTable(){
		var columns = [{title : "通知单号",render: function(item){
			if(config.hasPermission('MUPDATEINVOICE'))
			return '<a href="javascript:void(0);" onclick="Invoice.dialogInvoice('+item.id+')"  class="blue">'+item.serial+'</a>';
			else
				return item.serial;

		}},{
		title : "客户<br>编号",
		name : "clientCode"
	},{
		title : "车船号",
		render: function(item){
			if(item.deliverType==1){
				return "<label style='width:90px;'>"+util.isNull(item.vsName)+"</label>";
			}else{
				return "<label style='width:90px;'>"+util.isNull(item.vsName)+"/"+util.isNull(item.shipArrivalTime)+"</label>";
			}
			
		}
	},{
		title : "开票时间",
		render: function(item){
			return "<label style='width:120px;'>"+item.invoiceTime+"</label>" ;
		}
	},{
		title : "货体号",
		name : "code"
	},{
		title : "货品名称",
		name : "productName"
	},{
		title : "货主名称",
		name : "clientName"
	},{
		title : "提货单位",
		name : "ladingClientName"
	},{
		title : "原号",
		name : "yuanhao"
	},{
		title : "调号",
		name : "diaohao"
	},{
		title:"提单号",
		name:"ladingEvidence"
	},{
		title : "开单量(吨)",
		name : "deliverNum",
		render: function(item){
			return util.toDecimal3(item.deliverNum,true);
		}
	},{
		title:"开票罐号",
		render:function(item){
			return util.isNull(item.tankName);
		}
	},{
		title : "提货<br>状态",
		name : "waitAmount",
		render: function(item, name, index){
			return item.waitAmount;
		}
	},{
		title : "操作",
		render: function(item){
			var str = "<div class='input-group-btn' style='width:40px;'>" ;
			if(config.hasPermission('MUPDATEINVOICE'))
			str += "<a href='javascript:void(0);' onclick='Invoice.dialogInvoice("+item.id+")' style='margin:0 2px;'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a>";
			
			if(item.waitAmount=="待提"&&config.hasPermission('MDELETEINVOICE'))			
				str += "<a href='javascript:void(0);' style='margin:0 2px;' onclick='Invoice.delInvoice("+item.id+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='撤销'></span></a>" ;
			str+="</div>";
			return str ;
		}
	}];
	/*解决id冲突的问题*/
	$('[data-role="invoicelGrid"]').grid({
		identity : 'id',
		columns : columns,
		showPage:8,
		isShowIndexCol : false,
		isShowPages : true,
		url : config.getDomain()+"/invoice/list",
		callback:function(){
			$('[data-role="invoicelGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
			util.setColumnsVisable($('div[data-role="invoicelGrid"]'),[0,14],[0,1,2,3,4,5,6,7,8,9,10,11,13,14],function(){columnCheckArray=util.getColumnsCheckArray();});
		}
	});	
	}
/***************************************************************************/
	//开票撤销功能
	function delInvoice(obj){
		dataGrid = $('[data-role="invoicelGrid"]');
        dataGrid.confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
				 $.post(config.getDomain()+"/invoice/delete", "ids="+obj).done(function (data) {
			            if (data.code == "0000") {
			                dataGrid.message({
			                    type: 'success',
			                    content: '撤销成功'
			                });
			                dataGrid.grid('refresh');
			            } else {
			                $('body').message({
			                    type: 'error',
			                    content: data.msg
			                });
			            }
			        }).fail(function (data) {
			            dataGrid.message({
			                type: 'error',
			                content: '撤销失败'
			            });
			        });
			}
		});
		
	}
/****************************************************************************/
	/**初始化开票页面信息*/
	function initMake(){
		initSelect();
		$("#allClient").popover();//联单tips
		initLadingClientCtr($("#allLadingClient"));//初始化提货单位控件
		$("#invoiceActualNum").val('').removeAttr();//初始化显示的开票提单数
		//准备执行
		$("#preOpera").click(function(){
			//TODO 校验车船号和提货单位
			//联单不能点击
			$("#curTime").val(util.currentTime(1));
			$(".liandan").attr('disabled','disabled');
			if(config.validateForm($(".fhkp"))){
			var curTime =$("#curTime").val().split(" ")[0].replace(/-/g,'');//通知单之前日期
			var vsName = $("#vehicleShipNo").val();//车船号
			var batchId=util.isNull($("#vehicleShipNo").attr('key'),1);//船发管输对应batchId,车发对应vehicleShipId
			var deliverType = util.isNull($("#select_option").val(),1);//车船类型 
			var isOils=util.isNull($("#productName").attr('key'),1);//是否是油品
			var isNullTankName=false;
			
			var tankNames='';//校验罐是否在油品参数表存在
			$("#add_goods_tbody tr").each(function(){
				$this=$(this);
				if(util.isNull($this.find(".add_goods_deliverNum").val(),1)>0){
					tankNames+=$this.find(".tankName").val()+',';
					if(isOils==1&&util.isNull($this.find(".tankName").val())==''&&deliverType==1){
						$("body").message({
							type : 'warning',
							content : '请填写罐号！'
						});
						isNullTankName=true;
						return;
					}					
				}
			});
			if(isNullTankName)return;
			tankNames+='A';
			if(deliverType==1&&vsName.length<6){
				$("body").message({
					type : 'error',
					content : '请输入长度大于等于6且由数字字母组成的车牌号！'
				});
			}else if(batchId==0&&deliverType!=1){
				$("body").message({
					type : 'error',
					content :deliverType==2?'请确认出港计划是否完成':'请确认管输计划是否完成'
				});
			}else{//TODO 校验是否联单
				config.load();
				$.ajax({
					type:'post',
					url:config.getDomain()+"/invoice/checkiskupono",
					dataType:'json',
					data:{vsName:vsName,deliverType:deliverType,serial:curTime,batchId:batchId,tankNames:tankNames,
						isOils:util.isNull($("#productName").attr('key'),1)},
					success:function(data){
						util.ajaxResult(data,'校验是否是联单',function (ndata){
						var isOilsHas=false;//油品开同仓位
						if(ndata&&ndata.length>0){
								//是否存在对应油品参数
							if(ndata[0].isHasOilMeasure&&ndata[0].isHasOilMeasure==0){
								$("body").message({
								type : 'error',
								content :'罐号没有对应油品参数信息，不能开票'
								});
								$("#save").attr("disabled","disabled");
								return;
							}
							// 不存在对应的联单
							if(ndata[0].count==0){	
								$(".liandan").attr('checked',false);
								isOilsHas=handleShowDeliverNum(0,deliverType==1,ndata[0].storageInfos);
							//存在联单情况，并获取到联单的数量count,
							}else if(ndata[0].count>0){
								$(".liandan").attr('checked',true); 
								isOilsHas=handleShowDeliverNum(ndata[0].deliverNum,deliverType==1,ndata[0].storageInfos);
							}
							
							//如果不是油品联单则可以确认
							if(!isOilsHas)$("#save").removeAttr("disabled");
							//初始化通知单号
							$("#sinfo").val(ndata[0].serial);
						}     
					},true);}});
					}
			//如果是清单填写清单
			if($("#isCleanInvoice").is(":checked")){
			$("#add_goods_tbody tr").each(function(){
				$this=$(this);
				if(util.isNull($this.find(".add_goods_deliverNum").val(),1)>0)
					$this.find(".remark").val("清单"+util.isNull($this.find(".remark").val()));
			});
			}
			}});
		//提交
		$("#save").click(function(){
			if(config.validateForm($(".fhkp"))){
				if(util.isNull($("#ladingClientId").attr("data"),1)==0){
					$("body").message({type : 'warning',content : '请重新填写提货单位!'});
					return;
				};
				var goodsLogList=new Array();
				var deliverType=$("#select_option").val();
				if( util.isNull($("#vehicleShipNo").attr("data"),1) == 0 && deliverType != 1){
					$("body").message({type : 'warning',content : '关联车船号失败，请点击清空按钮重开!'});
					return;
				};
				var isNotOutNum=true;//是否提取量大于结存量
				var isEmptyStorageInfo=false;//是否为空仓位
				//获取goodslog信息
				var goodsLog;
				var leftNum;
				var deliverNum;
				var delverNo=$("#curTime").val().split(" ")[0].replace(/-/g,'')+''+$("#curTime").val().split(" ")[1].replace(/:/g,'');
				$("#add_goods_tbody tr").each(function(){
					$this=$(this);
					var  deliverNum=util.isNull($this.find(".add_goods_deliverNum").val(),1);
					var i=1;
					if(deliverNum!=0){
						leftNum=util.isNull($this.find(".deliverNum").text(),1);//可提量
						//可提量大于开票并没有超过最大结存量
						if(isNotOutNum&&parseFloat(util.FloatSub(deliverNum,leftNum,3))>0){
							$("body").message({type : 'error',content : '开票量不能超过可提量'});
							$("#save").attr("disabled","disabled");
							isNotOutNum=false;
						}else if(isNotOutNum){
							//仓位校验
							if(util.isNull($this.find(".storageInfo").val(),1)==0&&deliverType==1){
								$("#save").attr("disabled","disabled");
								isEmptyStorageInfo=true;
							}else{
							 goodsLog ={
									"serial":$("#sinfo").val(),
									"deliverNo":delverNo,
									"createTime":util.formatLong(util.currentTime(1))+i,
									"invoiceTime":util.formatLong(util.currentTime(1))+i,
									"orderNum":util.isNull($this.find(".orderNum").val(),1),//排序
									"clientId":$("#clientName").attr("data"),
									"ladingId":$this.find(".ladingId").val(),
									"ladingType":$this.find(".ladingType").val(),
									"ladingClientId":$("#ladingClientId").attr("data"),
									"vehicleShipId":util.isNull($("#vehicleShipNo").attr("data"),1),
									"ladingEvidence":$("#ladingEvidence").val().toLocaleUpperCase(),
									"deliverType":deliverType,
									"goodsId":$this.find(".add_lading_id").val(),
									"actualNum":0,
									"batchId":deliverType==1?undefined:$("#vehicleShipNo").attr("key"),
									"deliverNum":$this.find(".add_goods_deliverNum").val(),
									"goodsChange":-$this.find(".add_goods_deliverNum").val(),
									"storageInfo":$this.find(".storageInfo").val(),
									"goodsSave":util.toDecimal3($this.find(".goodsSave").text(),true),
									"tankName":$this.find(".tankName").val(),
									"surplus":util.toDecimal3($this.find(".wait").val(),true),
									"isCleanInvoice":$("#isCleanInvoice").is(":checked")?1:0,
									"remark":util.isNull($this.find(".remark").val()),
									"type":5
							};
							goodsLogList.push(goodsLog);
							i++;
							}
						}else{
							return;
						}
					}
				});
				 if(!isNotOutNum){
					return false;
				}else if(isEmptyStorageInfo){
					$("body").message({type : 'error',content : '仓位不能为空'});
					return false;
				}else if(goodsLogList.length>0){
					var goodsLogDto={
							"goodsLogList":handleGoodsLogList(goodsLogList),
							"deliverType":deliverType,
				    		"productId":$("#productName").attr("data"),//货品id
				    		"productCode":$("#productName").attr("code"),//货品简称
				    		"isOils":$("#productName").attr("key"),//是否是油品 0否1是
				    		"clientId":$("#clientName").attr("data"),
				    		"vehicleShipId":util.isNull($("#vehicleShipNo").attr("data"),1),
				    		"vehiclePlate":$("#vehicleShipNo").val(),
				    		"serial":$("#sinfo").val(),
				    		"isKupono":$(".liandan").is(":checked")?1:0	
					};
					$.ajax({
						type:'post',
						url:config.getDomain()+"/invoice/add",
						data:{"invoiceDto":JSON.stringify(goodsLogDto)},
						async: false,
						dataType:"json",
						success:function(data){
							util.ajaxResult(data,'开票',function(ndata,nmap,nmsg){
								if(util.isNull(nmsg,1)!=0){
									$("body").message({type : 'warning',content :nmsg});
								}else if(nmap&&nmap.serial){
									$("#sinfo").val(nmap.serial);
								}else{}
								$("#save").attr("disabled","disabled");
								$(".liandan").removeAttr('disabled');
								$("#isCleanInvoice").attr('checked',false);
								searchInvoiceGoodsList();//重新加载开票数据信息
							});
							}});
				}else{
					$("body").message({type : 'error',content : '没有可开票数据!'});
				}
			}
	});
	};
	
/***************************************************************************/
	function clearInfo(){
		$("#productName,#clientName").val('').removeAttr('data');	
		$("#select_option").val(1);
		$("#vehicleShipNo").val("").removeAttr("key").removeAttr("data");
		$("#invoiceActualNum,#maxLoadCapacityNum").text('').attr('data',0).css('color','blue');
		$("#maxLoadCapacityNum").text("");
		$("#ladingClientId,#ladingEvidence,#sinfo,#curTime").val("");
		$("#save").attr("disabled",true);
		$("#add_goods_tbody").empty();
	}
/***************************************************************************/
	//查询开票数据信息
	function searchInvoiceGoodsList(){
		var deliverType=util.isNull($("#select_option").val(),1);
		if(util.isNull($("#productName").attr("data"),1)==0){
			$("body").message({type : 'error',content : '请选择货品'});
			return false ;
		}
		if(util.isNull($("#clientName").attr("data"),1)==0){
			$("body").message({type : 'error',content : '请选择客户名称'});
			return false ;
		}
		var invoiceDto ={
	    		"productId":$("#productName").attr("data"),
	    		"clientId":$("#clientName").attr("data"),
	    		"zeroFlag":$("#zeroFlag").is(":checked")?1:0,//是否显示全部
	    	} ;
		   config.load();
	    	$.ajax({
				type : "post",
				url : config.getDomain()+"/invoice/searchLading",
				data:invoiceDto,
				dataType : "json",
				success : function(data){
					util.ajaxResult(data,'获取货体',function(ndata){
						var productType=util.isNull($("#productName").attr("key"),1);
						if(ndata!=null && ndata.length>0){
							var html="";
							var isOutStr="";
							var ladingTime="";
							var mDeliverNum=0;
							for(var i=0;i<ndata.length;i++){
								//是否超过有效期
								isOutStr=isOutDate(ndata[i].ladingType,ndata[i].endTime)?'disabled="disabled"':'';
								if(ndata[i].startTime)
									ladingTime=ndata[i].startTime;	
								if(!ndata[i].sourceGoodsId&&!ndata[i].rootGoodsId&&ndata[i].tArrivalStartTime&&ndata[i].period)
									ladingTime=new Date(ndata[i].tArrivalStartTime*1000+ndata[i].period*86400000).Format("yyyy-MM-dd");
								if(deliverType==1)
									mDeliverNum=util.FloatSub(util.FloatSub(ndata[i].goodsCurrent,ndata[i].waitAmount,3),util.isNull(ndata[i].planAmount,1));
								else
									mDeliverNum=util.FloatSub(ndata[i].goodsCurrent,ndata[i].waitAmount,3);
								html+='<tr><td style="width: 60px;"><div class="spinnerDiv"><div class="input-group ">'+
                                '<input type="text" class="spinner-input form-control orderNum" maxlength="3" readonly style="padding-right: 0px; padding-left: 0px;text-align:center;width:30px">'+
                                '<div class="spinner-buttons input-group-btn btn-group-vertical">'+               
                                '<button type="button" class="btn spinner-up btn-xs blue"><i class="fa fa-chevron-up"></i></button>'+                 
                                '<button type="button" class="btn spinner-down btn-xs blue"><i class="fa fa-chevron-down"></i></button>'+                 
                                '</div></div></div></td>'+
								'<td><input type="hidden" class="add_lading_id" value='+ndata[i].id+' />'+
								'<input type="hidden" class="wait" value='+util.toDecimal3(ndata[i].goodsCurrent,true)+' />'+
								'<input type="hidden" class="ladingId" value='+ndata[i].ladingId+' />'+
								'<input type="hidden" class="ladingType" value='+ndata[i].ladingType+' />'+ndata[i].cargoCode+'</td>'+
								'<td><a href="javascript:void(0);" onclick="GoodsLZ.openGoodsLS('+ndata[i].id+');" >'+ndata[i].goodsCode+'</a></td>' +
								'<td>'+util.isNull(ndata[i].yuanhao)+'</td>' +
								'<td>'+util.isNull(ndata[i].diaohao)+'</td>' +
								'<td>'+getIndate(ndata[i].ladingType,ndata[i].endTime)+'</td>' +
								'<td>'+ladingTime+'</td>'+
								'<td>'+ndata[i].goodsTotal+'</td>' +
								'<td class="goodsSave">'+util.toDecimal3(ndata[i].fengliang,true)+'</td>'+
								'<td class="deliverNum">'+util.toDecimal3(mDeliverNum,true)+'</td>'+
								'<td>'+util.toDecimal3(util.isNull(ndata[i].planAmount,1),true)+'</td>'+
								'<td>'+util.toDecimal3(ndata[i].waitAmount,true)+'</td>'+
								'<td style="width: 100px;"><input style="width:90px;" '+isOutStr+' class="add_goods_deliverNum form-control" onkeyup="config.clearNoNum(this)" type="text" /></td>'+
								'<td style="width: 100px;"><input style="width:90px;" '+isOutStr+'  data-provide="typeahead" class="tankName form-control" value="'+((util.isNull(ndata[i].tankCode,1)==0&&productType==0)?'':util.isNull(ndata[i].tankCode))+'" type="text" /></td>'+ 
								'<td style="width: 80px;"><select style="width:70px;" '+isOutStr+' class="storageInfo form-control" type="text" style="padding-left: 0px; padding-right: 0px;text-align:center;">'+
								'<option value="" selected> </option>'+
								'<option value="1">1</option><option value="2">2</option>'+
								'<option value="3">3</option><option value="4">4</option>'+
								'<option value="5">5</option><option value="6">6</option>'+
								'<option value="7">7</option><option value="8">8</option>'+
								'<option value="9">9</option>'+
								'</select></td>'+
								'<td style="width: 120px;"><input style="width:110px;" '+isOutStr+' class="remark form-control" type="text" /></td>'+ 
								'</td></tr>' ;
							}
							$("#add_goods_tbody").empty().append(html) ;
							// 添加事件
							$("#add_goods_tbody").find(".spinnerDiv").each(function(){
								$(this).spinner({value:1, min:1, max:99});
							});
							$.ajax({
								type:'post',
								url:config.getDomain()+"/baseController/getTankCode",
								dataType:'json',
								data:{productId:$("#productName").attr("data")},
								success:function(data){
									util.ajaxResult(data,'获取成功',function(ndata){
										if(ndata){
											$(".tankName").each(function(){util.handleTypeahead(ndata,$(this),"code",undefined,true);});	
										}
									},true);
								}
							});
						}else{
							$("#add_goods_tbody").empty();
						}
					},true);
				}
			});
	};
/****************************************************************************/
//处理页面显示的开票数,是否是车发，仓位
function handleShowDeliverNum(count,isTruck,storageInfos){
	var mDeliverNum=util.isNull(count,1);
	var arrayB=new Array();
	$("#add_goods_tbody tr").each(function(){//获取填写的数量计算总开票量，获取仓位号
		if(util.isNull($(this).find(".add_goods_deliverNum").val(),1)>0){
			mDeliverNum =util.FloatAdd($(this).find(".add_goods_deliverNum").val(),mDeliverNum); 
			arrayB.push($(this).find(".storageInfo").val());
		}
	});
	//校验油品同仓位不能开联单
	if(util.isNull(count,1)>0&&isTruck&&storageInfos&&storageInfos.length>0){
		var arrayA=storageInfos.split(',');
		//是否有同仓位联单情况
		var isHas=false;
		for(var i=0;i<arrayB.length;i++){
			if($.inArray(arrayB[i],arrayA)!=-1){
				isHas=true;
				break;
			}
		}
		if(isHas){
			//如果是油品，提醒无法开单
			if(util.isNull($("#productName").attr('key'),1)==1){
			$("body").message({
				type : 'error',
				content : '油品联单同仓位不能开票！'
			});
			$("#save").attr("disabled","disabled");
			return true;	
			}else{
				$('body').confirm({
				content : '存在同仓位联单是否继续？',
				callBack : function(){return false;},
				cancel:function(){
					$("#save").attr('disabled','disabled');
					return true;}
				});
				}
			}
		}
	
	//校验最大允许荷载量和历史最大荷载量
	if(util.isNull($("#maxLoadCapacityNum").text())!=''&&parseFloat(util.FloatSub($("#maxLoadCapacityNum").text(),mDeliverNum))<0){
		$('body').confirm({
			content : '该车(船)次最大允许荷载吨为 '+util.isNull($("#maxLoadCapacityNum").text())+" 吨,请重新开票",
			callBack : function(){
				return true;
			}});
		return true;
	}else if(isTruck&&parseFloat(util.FloatSub($("#loadCapacityHidden").val(),mDeliverNum))<0){//如果车载量小于联单总和
		$("#invoiceActualNum").text(util.toDecimal3(mDeliverNum,true)).attr('data',1).css('color','red');//标记为1，表示需要更新车载数量
		$("body").message({type : 'warning',content : '开票量大于历史最大发货量！'});
	}else{
		$("#invoiceActualNum").text(util.toDecimal3(mDeliverNum,true)).attr('data',0).css('color','blue');
	}
	return false;
};

/****************************************************************************/
	//初始化下拉菜单
	function initSelect(){
		//初始化货品
		util.urlHandleTypeaheadAllData("/product/select",$('#productName'),undefined,undefined,function(data){
			$("#allLadingClient,#allClient").removeAttr('checked');
			$(".cn").empty().append('<input type="text" id="clientName"	class="form-control intentTitle" data-required="1" data-type="Require"/> ') ;
			if(data){
				$('#productName').attr('key',data.oils);//货品类型：0化学品1，油品
				$('#productName').attr('code',data.code);//货品简称
				//初始化货主
				util.urlHandleTypeaheadAllData("/baseController/getClientNameByProductId?productId="+data.id,$('#clientName'),function(item){return item.name+"["+item.code+"]";},undefined,function(ndata){
					if(ndata){
						searchInvoiceGoodsList();
					}
				});
			}
		},200); 
		initVehicleShipCtr(1);//默认初始化为罐车
	}
/****************************************************************************/
	//选择性初始化提货单位控件
	function initLadingClientCtr(obj){
		$(".lcn").empty().append('<input type="text" id="ladingClientId" class="form-control intentTitle" data-required="1" data-type="Require"/> ') ;
		if(obj.checked==true){
			util.urlHandleTypeaheadAllData("/baseController/getClientName",$('#ladingClientId'),function(item){return item.name+"["+item.code+"]";},undefined,undefined);
		}else{
			util.urlHandleTypeaheadAllData("/baseController/getHistoryClientName",$('#ladingClientId'),function(item){return item.name+"["+item.code+"]";},undefined,undefined);
		}
	}
/****************************************************************************/
	//选择性初始化货主控件
	function initClientCtr(obj){
		$(".cn").empty().append('<input type="text" id="clientName"	class="form-control intentTitle" data-required="1" data-type="Require"/> ') ;
		if(obj.checked==true){
			util.urlHandleTypeaheadAllData("/baseController/getClientName",$('#clientName'),function(item){return item.name+"["+item.code+"]";},undefined,function(ndata){
				if(ndata){
					searchInvoiceGoodsList();
				}
			});
		}else{
			var pid = $("#productName").attr("data") ;
			util.urlHandleTypeaheadAllData("/baseController/getClientNameByProductId?productId="+pid,$('#clientName'),function(item){return item.name+"["+item.code+"]";},undefined,function(ndata){
				if(ndata){
					searchInvoiceGoodsList();
				}
			},1000);
		}
	}
/****************************************************************************/
	function initVehicleShipCtr(val){
		//清空内容
		$("#vsInfo").empty().append('<input type="text" id="vehicleShipNo" data-required="1" maxlength="15" class="form-control intentTitle" />') ;
		initFormIput();
		if(val==1){
			$(".shipMsg").hide();
			if(!$("#vsLaber>span").length)
			$("#vsLaber").append("<span	class=\"required\">*</span>") ;
			var data = [{name:"苏"},{name:"沪"},{name:"浙"},{name:"皖"},{name:"闽"},{name:"赣"},{name:"鄂"},{name:"湘"},{name:"鲁"},{name:"豫"},{name:"黑"},{name:"辽"},{name:"南"},{name:"新"},{name:"寅"},{name:"军"},{name:"青"},{name:"吉"},{name:"冀"},{name:"粤"},{name:"桂"}] ;
			$('#vehicleShipNo').typeahead({
	 			items:21,
	 			menu:"<ul class=\"typeahead dropdown-menu\" style='height: 160px;width:"+$('#vehicleShipNo').css("width")+";overflow: auto;'></ul>",
				source: function(query, process) {
					var results = _.map(data, function (product) {
						return product.name;
					});
					process(results);
				}
			});			
            //初始化keyup事件
            $('#vehicleShipNo').change(function(){
   			 var truckCode = $('#vehicleShipNo').val();
   			 if(util.isNull($("#select_option").val(),1)==1){
   			 $.ajax({
   				type : "post",
   				url : config.getDomain()+"/baseController/queryOneCar",
   				dataType : "json",
   				async:false,
   				data:{"carNo":util.isNull(truckCode,1)==0?null:util.isNull(truckCode)},
   				success : function(data) {
   					if(data.data && data.data.length > 0){
   						$("#loadCapacityHidden").val(util.isNull(data.data[0].loadCapacity,1));
   						$("#loadCapacityNum").text(util.toDecimal3(util.isNull(data.data[0].loadCapacity,1),true));
   						$('#vehicleShipNo').attr('data',data.data[0].id);
   						$("#maxLoadCapacityNum").text(data.data[0].maxLoadCapacity);
   					}else{
   						$('#vehicleShipNo').removeAttr('data');
   						$("#maxLoadCapacityNum,#loadCapacityNum").text("");
   						$("#loadCapacityHidden").val("");
   					}
   				}
   			 });}
   		});
        $('#vehicleShipNo').keyup(function(){
        	this.value=this.value.toUpperCase();
        });
		}else if(val==2){
			$(".shipMsg").show();
			if(!$("#vsLaber>span").length)
			$("#vsLaber").append("<span	class=\"required\">*</span>") ;
			getCanMakeInvoiceShipName(null,null,true);
			if($("#add_goods_tbody").text().length>0)
			searchInvoiceGoodsList();
		}else if(val==3){
			$(".shipMsg").show();
			$("#vsLaber>span").remove();
			$("#vehicleShipNo").removeAttr("data-required").attr("disabled","disabled");
			getCanMakeInvoiceShipName(null,null,false);
			if($("#add_goods_tbody").text().length>0)
				searchInvoiceGoodsList();
		}
		
	};
/****************************************************************************/
	//初始化可开票的船舶下拉列表
	function getCanMakeInvoiceShipName(ladingEvidence,productId,isNoTransport){
		var str='';
		if(ladingEvidence){
			str="&ladingCode="+ladingEvidence;
		}
		if(isNoTransport){
			str+="&isNoTransport=1";
		}
		
		util.urlHandleTypeaheadAllData("/baseController/getCanMakeInvoiceShipName?productId="+(productId?productId:$("#productName").attr("data"))+str,$("#vehicleShipNo"),
				function(item){return item.shipInfo},function(item){ return item.shipRefId},function(item,obj){
			if(item){
				$(obj).attr('key',item.id);
				initArrivalPlanMsg(item.id);	
				$("#maxLoadCapacityNum").text(util.isNull(item.loadCapacity,1));
			}
		});
	}
	//根据提单号，查询出库船信 船发和管输
	function getOutArrivalShip(){
		if($("#ladingEvidence").val()&&$("#ladingEvidence").val().length>2&&util.isNull($("#select_option").val(),1)!=1){
			$.ajax({
				type:'post',
				url:config.getDomain()+"/baseController/getCanMakeInvoiceShipName",
				dataType:'json',
				data:{ladingCode:util.isNull($("#ladingEvidence").val().toLocaleUpperCase())},
				success:function(data){
					util.ajaxResult(data,'获取船信',function(ndata){
						$("#vehicleShipNo").val("").removeAttr('data').removeAttr('key');
						if(ndata&&ndata.length>0){
							$("#vehicleShipNo").val(ndata[0].shipInfo).attr('data',ndata[0].shipRefId).attr('key',ndata[0].id);	
							$("#maxLoadCapacityNum").text(util.isNull(ndata[0].loadCapacity,1));
							initArrivalPlanMsg(ndata[0].id);
						}
					},true);
				}
			});
		}
	}
	//联单排序
	function handleGoodsLogList(listData){
		if(listData&&listData.length==1){
			return listData;
		}else if(listData&&listData.length>1){
			return listData.sort(function(a,b){
				return a.orderNum-b.orderNum;
			});
		}
	}
	//获取有效期
	function getIndate(ladingType,endTime){
		if(ladingType&&ladingType==2){
			if(util.validateFormat(endTime)){
				var date=util.getDifferTime(util.currentTime(0)+" 00:00:00",endTime+" 00:00:00",2);
				if(date>=0){
					return date;
				}else if(date<0){
					return '<label style="color:red;font-weight:bold;font-size: 14px;">'+date+'</label>';
				}
			}
		}
		return '';
	};
	//校验是否超期
	function isOutDate(ladingType,endTime){
		if(ladingType&&ladingType==2){
			if(util.validateFormat(endTime)){
				var date=util.getDifferTime(util.currentTime(0)+" 00:00:00",endTime+" 00:00:00",2);
				if(date>=0){
					return false;
				}else if(date<0){
					return true;
				}
			}
		}
		return false;
	}
	
	function initArrivalPlanMsg(arrivalId){
		config.load();
		$.ajax({
			type:"post",
			url:config.getDomain()+"/shipArrival/get?id="+arrivalId,
			dataType:"json",
			success:function(data) {
				util.ajaxResult(data,'初始化',function(ndata){
					if(ndata){
					var outboundArrial=ndata[0];	
					var outboundArrialPlan=ndata[1];	
					var length=outboundArrialPlan.length;
					var htmlB='';
					var itemPlan;
					var totalNum=0;
					var totalNumHtml='';
					for(var i=0;i<length;i++){
						itemPlan=outboundArrialPlan[i];
						if(itemPlan.status&&itemPlan.status==2){
							htmlB+='<tr>'+
							'<td>'+util.isNull(itemPlan.ladingClientName)+'</td>'+
							'<td>'+util.isNull(itemPlan.clientName)+'</td>'+
							'<td>'+util.isNull(itemPlan.goodsCode)+'</td>'+
							'<td>'+util.isNull(itemPlan.cargoCode)+'</td>'+
							'<td>'+util.isNull(itemPlan.inboundMsg)+'</td>'+
							'<td>'+util.isNull(itemPlan.actualLadingClientName)+'</td>'+
							'<td>'+util.isNull(itemPlan.tankCodes)+'</td>'+
							'<td>'+util.isNull(itemPlan.ladingCode)+'</td>'+
							'<td>'+util.toDecimal3(itemPlan.goodsTotal,true)+'</td>'+
							'<td>'+util.isNull(itemPlan.flow)+'</td>';
							totalNum=util.FloatAdd(totalNum,itemPlan.goodsTotal,true);//计算总量
						}
					}
					$("#arrivalPlanTable>tbody").empty().append(htmlB);
					totalNumHtml="<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计划量：</label>" +
					"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.toDecimal3(totalNum,true)+"</label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>吨</label>";
					$(".totalOutboundDiv").empty().append(totalNumHtml);
					}
				},true);
			}});
	}
/****************************************************************************/
	function dialogInvoice(id){
		$.get(config.getResource()+ "/pages/outbound/invoice/edit.jsp").done(function(data) {
			var dialog = $(data);
			initFormIput(dialog);
			initEdit(dialog,id);
			dialog.modal({
				keyboard : true
			});
		});
	};
	function getSerial(){
		config.load();
		$("#curTime").val(util.currentTime(1));//初始化开单时间
		$.ajax({
			type:'post',
			url:config.getDomain()+"/invoice/getSerial",
			data:{"serial":$("#curTime").val().split(" ")[0].replace(/-/g,'')},
			async: false,
			dataType:"json",
			success:function(data){
				util.ajaxResult(data,"获取通知单",function(ndata,nMap){
					
					$("#sinfo").val(ndata[0].serial)
				},true);
				}
			});
	};
/****************************************************************************/
	/**初始化编辑页*/
	function initEdit(dialog,id){
		initFormIput();
		//初始化提货单位
		util.urlHandleTypeahead("/baseController/getClientName",dialog.find('#ladingClientId')) ;
		dialog.find("#idHidden").val(id);
		dialog.find("#id").val(id) ;
		//查询开票信息
		$.ajax({
			type : "post",
			url : config.getDomain()+"/invoice/get?id="+id,
			dataType : "json",
			async:false,
			success : function(data) {
				util.ajaxResult(data,"获取开票信息",function(ndata){
				if(ndata&&ndata.length>0){
					dialog.find("#serial").val(ndata[0].serial);
					dialog.find("#clientName").val(ndata[0].clientName+'['+ndata[0].clientCode+"]");
					dialog.find("#productName").val(ndata[0].productName).attr('data',ndata[0].productId);
					dialog.find("#batchId").val(ndata[0].batchId);
					dialog.find("#vehicleShipNo").val(ndata[0].vsName).attr("data",ndata[0].vehicleShipId) ;
					dialog.find("#deliverType").attr('data',ndata[0].deliverType);
					if(ndata[0].deliverType==1){
						dialog.find("#deliverType").val("车发") ;
						util.urlHandleTypeahead("/baseController/getCanMakeInvoiceTruck",dialog.find('#vehicleShipNo')) ;
					}else if(ndata[0].deliverType==2){
						dialog.find("#deliverType").val("船发") ;
						dialog.find("#vehicleShipNo").attr("key",ndata[0].batchId);
						getCanMakeInvoiceShipName(ndata[0].ladingEvidence,ndata[0].productId);
					}
					else if(ndata[0].deliverType==2&&ndata[0].vsName=='GS'){
						dialog.find("#deliverType").val("管输");
						dialog.find("#vehicleShipNo").attr("key",ndata[0].batchId);
						getCanMakeInvoiceShipName(ndata[0].ladingEvidence,ndata[0].productId);
					}
					dialog.find("#goodsCurrent").val(util.toDecimal3(ndata[0].goodsCurrent,true)) ;	
					dialog.find("#deliverNum").val(util.toDecimal3(ndata[0].deliverNum,true)).attr('data',util.toDecimal3(ndata[0].deliverNum,true)) ;
					dialog.find("#ladingEvidence").val(ndata[0].ladingEvidence) ;
					dialog.find("#ladingCode").val(ndata[0].ladingCode) ;
					dialog.find("#tankName").val(ndata[0].tankName) ;
					dialog.find("#blockCode").val(ndata[0].storageInfo);
					dialog.find("#remark").val(util.isNull(ndata[0].remark));
					dialog.find("#code").val(ndata[0].goodsCode);
					dialog.find("#cargoCode").val(ndata[0].cargoCode);
					dialog.find("#yuanshihuozhu").val(util.isNull(ndata[0].yuanshihuozhu));
					dialog.find("#shangjihuozhu").val(util.isNull(ndata[0].shangjihuozhu));
					dialog.find("#actualNum").val(util.toDecimal3(ndata[0].actualNum,true));
					dialog.find("#ladingClientId").attr("data",ndata[0].ladingClientId).val(ndata[0].ladingClientName) ;
					dialog.find("#createUserId").text(util.isNull(ndata[0].createUserName));
					dialog.find("#createTime").text(util.isNull(ndata[0].createTime));
					if(ndata[0].actualType==1){
						dialog.find("#deliverNum").attr('disabled','disabled');
					}
				}
				},true);
			}
		});
	}
/****************************************************************************/
		/**进入编辑界面  初始化编辑信息*/
		function doEdit(obj){
			dialog=$(obj).closest(".modal-dialog");
			if(config.validateForm(dialog.find(".kpxg"))){
			var cur = util.isNull(dialog.find("#goodsCurrent").val(),1) ;
			var dnum = util.isNull(dialog.find("#deliverNum").val(),1) ;
			var tempDelivery=util.isNull(dialog.find("#deliverNum").attr('data'),1) ;
			if(parseFloat(dnum-cur-tempDelivery)>0){
				$("body").message({
					type : 'error',
					content : '开票量不能大于实际可提量'
				});
				return  false ;
			}
			var goodsLog = {
					"id":dialog.find("#id").val(),
					"serial":dialog.find("#serial").val(),
					"batchId":util.isNull(dialog.find("#deliverType").attr("data"),1)==2?dialog.find("#vehicleShipNo").attr("key"):dialog.find("#batchId").val(),
					"vehicleShipId":dialog.find("#vehicleShipNo").attr("data"),
					"deliverType":dialog.find("#deliverType").attr("data"),
					"ladingCode":dialog.find("#ladingCode").val(),
					"deliverNum":dialog.find("#deliverNum").val(),
					"goodsChange":(util.isNull(dialog.find("#actualNum").val(),1)==0?-dialog.find("#deliverNum").val():undefined),
					"ladingEvidence":dialog.find("#ladingEvidence").val().toLocaleUpperCase(),
					"ladingClientId":dialog.find("#ladingClientId").attr("data"),
					"storageInfo":dialog.find("#blockCode").val(),
					"tankName":dialog.find("#tankName").val(),
					"remark":dialog.find("#remark").val()
				};
			$.ajax({
				type : "post",
				url : config.getDomain()+"/invoice/changeInvoice",
				dataType : "json",
				data:{"goodsLog":JSON.stringify(goodsLog)},
				success : function(data) {
				util.ajaxResult(data,'更新',function(){
                 initEdit(dialog,dialog.find("#id").val());
                 if($('[data-role="invoicelGrid"]'))
                 util.updateGridRow($('[data-role="invoicelGrid"]'),{id:dialog.find("#id").val(),url:'/invoice/list'});
					});
				}
			});
			}
		};
		
/****************************************************************************/		
	function exportExcel(){
		var url=config.getDomain()+'/invoice/exportExcel?type=5';
		
	       if(util.isNull($("#vehicleShipNo").val())!='')
	    	   url+='&vsName='+$("#vehicleShipNo").val();
	       if(util.isNull($("#productName").attr("data"),1)!=0)
	    	   url+='&productId='+$("#productName").attr("data");
	       if(util.isNull($("#clientName").attr("data"),1)!=0)
	    	   url+='&clientId='+$("#clientName").attr("data");	   
		   if(util.isNull($("#deliverType").val(),1)!=0)
			   url+='&deliverType='+$("#deliverType").val();
		   if(util.isNull($("#actualType").val(),1)!=-1)
			   url+='&actualType='+$("#actualType").val()
		   if(util.isNull($("#ladingEvidence").val())!='')
			   url+='&ladingEvidence='+$("#ladingEvidence").val()
		   if(util.isNull($("#serial").val())!='')
			   url+='&serial='+$("#serial").val()
			window.open(url);
	
	}	
/****************************************************************************/
	/**
	  * 打印编辑开票信息
	  */
	 var printDetail = function(){
		 var ticketId = $("#idHidden").val();
		 
		 $.ajax({
				type : "post",
				url : config.getDomain()+"/invoice/list",
				dataType : "json",
				data:{
					"id":ticketId
				},
				success : function(resp) {
					var objList=  resp.data ;
					if(objList && objList.length>0)
					{
						CreatePrintPage(objList);
					}
					else
					{
						$("body").message({
							type : 'error',
							content : '没有可打印的开票信息'
						});
					}
				}
			});
	 };
	 //打印方法
	 function print(item,obj){
		 var deliverType,serial,isKupono;//是否开联单
		 if(item&&item==1){
			 dialog=$(obj).closest(".modal-dialog");
			 deliverType=dialog.find("#deliverType").attr("data")
			 serial=dialog.find("#serial").val();
			 isKupono=dialog.find(".liandan").is(":checked")?1:0;//是否开联单
		 }else{
			 deliverType = util.isNull($("#select_option option:selected").val(),1);//1.车发，2.船发，3.管输
			serial=util.isNull($("#sinfo").val());//通知单号
			isKupono=$(".liandan").is(":checked")?1:0;//是否开联单
		 }
		 
		 
		 if(deliverType==0){
			 $("body").message({
					type : 'error',     
					content : '发货类型不能为空'
				});
			 return false ;
		}
		if(serial==''){
			$("body").message({
				type : 'error',    
				content : '通知单号不能为空'
			});
		 return false ; 
		}
		$.ajax({
			type:'post',
			url:config.getDomain()+"/invoice/getprintlist",
			dataType:"json",
			data:{
				"deliverType":deliverType,
				"printType":isKupono,
				"serial":serial},
			success:function(data){
				util.ajaxResult(data,'开票',function(ndata){
					if(ndata&&ndata.length>0){
						CreatePrintPage(ndata);
					}else{
						$("body").message({
							type : 'error',
							content : '没有可打印的开票信息'
						});
					}
				},true);
			}
		
		});
		
	 }
/****************************************************************************/
	 //   创建打印页
	  function CreatePrintPage(objlist) 
	  {
	    LODOP=getLodop();  
	    for(var i = 0 ;i<objlist.length ;i++)
	    {
	    	LODOP.PRINT_INIT("");	
			if(i == (objlist.length-1))
			{
			    LODOP.SET_PRINT_PAGESIZE(0,"831px","550px","");
			}
			else
			{
				LODOP.SET_PRINT_PAGESIZE(0,"831px","350px","");
			}
			printKp(objlist[i]);
			LODOP.PRINT();
			//LODOP.PREVIEW();
	    }
	  };
/****************************************************************************/
	//打印成单子 
	function printKp(obj)
	  {
	    var top = -5;
	    var left = 0;
	    
	    if(util.isNull(obj.customLading)!=''){
			LODOP.ADD_PRINT_TEXT(6+top,60+left,220,30,"海运提单号："+util.isNull(obj.customLading)); //入库船信
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(28+top,60+left,220,30,"海关放行时间："+util.isNull(obj.customLadingTime)); //入库船信
			}
	    if(obj.shipInfo&&obj.shipInfo!=null&&obj.shipInfo!='null'){	
		LODOP.ADD_PRINT_TEXT(46+top,60+left,200,30,"船名："+util.isNull(obj.shipInfo)); //船信
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
	    }
		LODOP.ADD_PRINT_TEXT(60+top,320+left,200,30,util.isNull(obj.yuanhao)); //原号
        LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(53+top,500+left,120,30,"JL2320"); //体系编号JL2320
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);        
		LODOP.ADD_PRINT_TEXT(53+top,590+left,120,30,obj.serial); //通知单号
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(85+top,160+left,280,30,util.isNull(obj.clientName)); //供货单位
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(88+top,525+left,150,30,util.isNull(obj.cargoCode)); //"批号"
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(132+top,160+left,280,30,util.isNull(obj.ladingClientName)); //提货单位
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(135+top,525+left,150,30,util.isNull(obj.ladingEvidence)); //"提单号"
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(174+top,160+left,120,30,util.isNull(obj.productName)); //"货物名称"
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(174+top,358+left,80,30,""+util.toDecimal3(obj.deliverNum,true)); //"发货数量"
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(174+top,525+left,130,30,util.isNull(obj.tankName)); //"发货罐号"
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(235+top,160+left,160,30,util.isNull(obj.vsName)); //"车、船号"
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(235+top,360+left,100,30,obj.createUserName); //"开票人"
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(220+top,597+left,150,30,util.getSubTime(obj.invoiceTime,1)); //"开票日期"
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(273+top,360+left,270,30,util.isNull(obj.storageInfo)+"  "+util.isNull(obj.remark)); //仓号、备注
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(218+top,360+left,200,30,util.isNull(obj.diaohao)); //调号
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		LODOP.ADD_PRINT_TEXT(218+top,170+left,200,30,util.toDecimal3(obj.loadCapacity,true)+"吨"); //核载量
		LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
	  }
/****************************************************************************/
	function getLodop(oOBJECT,oEMBED){
		/**************************
		  本函数根据浏览器类型决定采用哪个页面元素作为Lodop对象：
		  IE系列、IE内核系列的浏览器采用oOBJECT，
		  其它浏览器(Firefox系列、Chrome系列、Opera系列、Safari系列等)采用oEMBED,
		  如果页面没有相关对象元素，则新建一个或使用上次那个,避免重复生成。
		  64位浏览器指向64位的安装程序install_lodop64.exe。
		**************************/
			var strHtmInstall="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='"+config.getDomain()+"/resource/plugins/install_lodop32.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
	        var strHtmUpdate="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='"+config.getDomain()+"/resource/plugins/install_lodop32.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
	        var strHtm64_Install="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='"+config.getDomain()+"/resource/plugins/install_lodop64.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
	        var strHtm64_Update="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='"+config.getDomain()+"/resource/plugins/install_lodop64.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
	        var strHtmFireFox="<br><br><font color='#FF00FF'>（注意：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它）</font>";
	        var strHtmChrome="<br><br><font color='#FF00FF'>(如果此前正常，仅因浏览器升级或重安装而出问题，需重新执行以上安装）</font>";
	        var LODOP;		
			try{	
			     //=====判断浏览器类型:===============
			     var isIE	 = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
			     var is64IE  = isIE && (navigator.userAgent.indexOf('x64')>=0);
			     //=====如果页面有Lodop就直接使用，没有则新建:==========
			     if (oOBJECT!=undefined || oEMBED!=undefined) { 
		               	 if (isIE) 
			             LODOP=oOBJECT; 
			         else 
			             LODOP=oEMBED;
			     } else { 
				 if (CreatedOKLodop7766==null){
		          	     LODOP=document.createElement("object"); 
				     LODOP.setAttribute("width",0); 
		                     LODOP.setAttribute("height",0); 
				     LODOP.setAttribute("style","position:absolute;left:0px;top:-100px;width:0px;height:0px;");  		     
		                     if (isIE) LODOP.setAttribute("classid","clsid:2105C259-1E0C-4534-8141-A753534CB4CA"); 
				     else LODOP.setAttribute("type","application/x-print-lodop");
				     document.documentElement.appendChild(LODOP); 
			             CreatedOKLodop7766=LODOP;		     
		 	         } else 
		                     LODOP=CreatedOKLodop7766;
			     };
			     //=====判断Lodop插件是否安装过，没有安装或版本过低就提示下载安装:==========
			     if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
			    	 $.get(config.getResource()+"/plugins/addplugins.jsp").done(function(data){
				 			dialog = $(data) ;
				 			if (navigator.userAgent.indexOf('Chrome')>=0)
				 				 dialog.find(".plugins").append(strHtmChrome) ;
				             if (navigator.userAgent.indexOf('Firefox')>=0)
				            	 dialog.find(".plugins").append(strHtmFireFox) ;
				             if (is64IE) document.write(dialog.find(".plugins").append(strHtm64_Install)); else
				             if (isIE)   document.write(dialog.find(".plugins").append(strHtmInstall));    else
				                 dialog.find(".plugins").append(strHtmInstall) ;
				 			dialog.modal({
				 				keyboard: true
				 			});
				 		});
			     } else 
			     if (LODOP.VERSION<"6.1.9.6") {
			             if (is64IE) document.write(strHtm64_Update); else
			             if (isIE) document.write(strHtmUpdate); else
			             document.documentElement.innerHTML=strHtmUpdate+document.documentElement.innerHTML;
			    	     return LODOP;
			     };
			     //=====如下空白位置适合调用统一功能(如注册码、语言选择等):====	     

			     LODOP.SET_LICENSES("昆明易极信息技术有限公司","055716580837383919278901905623","","");
			     //============================================================	     
			     return LODOP; 
			} catch(err) {
			     if (is64IE)	
		            document.documentElement.innerHTML="Error:"+strHtm64_Install+document.documentElement.innerHTML;else
		            document.documentElement.innerHTML="Error:"+strHtmInstall+document.documentElement.innerHTML;
			     return LODOP; 
			};
		}
/****************************************************************************/
	return {
		initList:initList,//初始化开票列表页 
		delInvoice:delInvoice,
		initMake:initMake,//初始化开票页面
		clearInfo:clearInfo,//清空内容
		initClientCtr:initClientCtr,//选择性初始化货主控件
		initLadingClientCtr:initLadingClientCtr,//选择性初始化提货单位控件
		initVehicleShipCtr:initVehicleShipCtr,//选择性初始化车船号
		getOutArrivalShip:getOutArrivalShip,//根据提单号获取车船信息（船发，管输）
		initEdit:initEdit,
		dialogInvoice:dialogInvoice,
		doEdit:doEdit,
		print:print,//打印单子
		exportExcel:exportExcel
	};
}();
