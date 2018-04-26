//开票冲销和发货换货体
TradeInvoice=function(){
	function initDialog(){
		$.get(config.getResource()+ "/pages/outbound/tradeInvoice/tradeInvoice.jsp").done(function(data) {
			var dialog = $(data);
			initDialogCtr(dialog);
			dialog.modal({
				keyboard : true
			});
		});
	}
	function initDialogCtr(dialog){
		util.initTimePicker(dialog) ;
		util.urlHandleTypeaheadAllData("/baseController/getClientName",dialog.find('#clientId'),function(item){return item.name+"["+item.code+"]";},undefined,function(item){
			handleGoodsCtr(item,dialog);
		});
		util.urlHandleTypeaheadAllData("/product/select",dialog.find('#productId'),function(item){return item.name;},undefined,function(item){
			handleGoodsCtr(item,dialog);
		}); 
		dialog.find("#goodsId").focus(function(){
			var clientId=util.isNull(dialog.find('#clientId').attr('data'),1);
			var productId=util.isNull(dialog.find('#productId').attr('data'),1);
			if(clientId==0){
				$('body').message({
					type:'warning',
					content:'请选择货主单位'
				});
				return false;
			}else if(productId==0){
				$('body').message({
					type:'warning',
					content:'请选择货品'
				});
				return false;
			}
		});
		dialog.find("#searchGoodsLog").click(function(){
			var serial=util.isNull(dialog.find("#serial").val());
			var goodsId=util.isNull(dialog.find("#goodsId").attr("data"),1);
			if(util.isNull(serial,1)==0&&goodsId==0){
				$('body').message({
					type:'warning',
					content:'货体号和通知单号请至少选择一项'
				});
				return false;
			}else{
				initDialogTable(dialog,getSearchCondition(dialog));
			}
		});
		dialog.find("#reset").click(function(){
			dialog.find("#serial,#clientId,#productId,#startTime,#endTime").val('').removeAttr();
			dialog.find(".goodsDiv").empty().append('<input type="text" id="goodsId" class="form-control">');
		});
		//冲销
		dialog.find("#tradeInvoice").click(function(){
		var selectRows=	dialog.find("div[data-role='goodsLogGrid']").getGrid().selectedRows();
		if(selectRows&&selectRows.length>0){
            var goodsLogIds='';   				
			for(var i=0,len=selectRows.length;i<len;i++){
				goodsLogIds+=selectRows[i].id+",";
			}	
			goodsLogIds=goodsLogIds.substring(0,goodsLogIds.length-1);
			config.load();
			$.ajax({
				type:"post",
				url:config.getDomain()+"/tradeInvoice/tradeInvoice",
				dataType:'json',
				data:{goodsLogIds:goodsLogIds,remark:dialog.find("#remark").val()},
				success:function(data){
					util.ajaxResult(data,"开票冲销",function(){
						initDialogTable(dialog,getSearchCondition(dialog));
						if($("div[data-role='invoicelGrid']").getGrid()){
							$("div[data-role='invoicelGrid']").getGrid().refresh();
						}
					});
				}
			});
		}else{
				$('body').message({
					type:'warning',
					content:'至少选择一条记录冲销'
				});
			}
		});
		//更换货体
		dialog.find("#changeGoods").click(function(){
			
		});
		
		dialog.find("#checkHistory").click(function(){
			tradeInvoiceLog();
		})
		
		
	};
	function getSearchCondition(dialog){
		return {serial:util.isNull(dialog.find("#serial").val()),
			goodsId:util.isNull(dialog.find("#goodsId").attr("data"),1),
			productId:util.isNull(dialog.find('#productId').attr('data'),1),
			startTime:util.formatLong(dialog.find("#startTime").val()+" 00:00:00"),
			endTime:util.formatLong(dialog.find("#endTime").val()+"23:59:59")}
	}
	function initDialogTable(dialog,params){
		var columns=[{title:"通知单号",name:"serial"}, {title:"开票时间",name:"invoiceTime"},{title:"车船号",name:"vsName"},
		             {title:"货品",name:"productName"},{title:"货体号",name:"goodsCode",render:function(item){
		            	 return '<a href="javascript:void(0);" onclick="GoodsLZ.openGoodsLZ('+item.goodsId+');" >'+item.goodsCode+'</a>';
		             }},{title:"原号",name:"yuanhao"},{title:"调号",name:"diaohao"},
		             {title:"货主单位",name:"clientName"},{title:"提货单位",name:"ladingClientName"},
		             {title:"提货凭证",name:"ladingEvidence"},{title:"出库时间",name:"createTime"},
		             {title:"开票量(吨)",name:"deliverNum"},{title:"实发量(吨)",name:"actualNum"}];
		if(dialog.find("div[data-role='goodsLogGrid']").getGrid()) 
			dialog.find("div[data-role='goodsLogGrid']").getGrid().destory();
		
		dialog.find("div[data-role='goodsLogGrid']").grid({
			identity : 'id',
			columns:columns,
			showPage:8,
			pageSize:10,
			searchCondition:params,
			isShowIndexCol:true,
			isShowPages : true,
			url:config.getDomain()+"/tradeInvoice/getOutboundList",
			callback:function(){
				dialog.find("div[data-role='goodsLogGrid']").find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
			}
		});
	};
	//处理货批和货品的编辑框
	function handleGoodsCtr(item,dialog){
		var clientId=util.isNull(dialog.find('#clientId').attr('data'),1);
		var productId=util.isNull(dialog.find('#productId').attr('data'),1);
if(clientId!=0&&productId!=0){
	$.ajax({
		type:"post",
		url:config.getDomain()+"/tradeInvoice/getgoodslist",
		dataType:'json',
		data:{clientId:clientId,productId:productId},
		success:function(data){
			util.ajaxResult(data,'获取货体',function(ndata){
				dialog.find(".goodsDiv").empty().append('<input type="text" id="goodsId" class="form-control">');
				util.handleTypeaheadAllData(ndata,dialog.find('#goodsId'),function(item){return item.code});
			},true);
		}
	});
 }
	
	};
	
	//冲销历史纪录
	function tradeInvoiceLog(){
		$.get(config.getResource()+ "/pages/outbound/tradeInvoice/tradeInvoiceLog.jsp").done(function(data) {
			var dialog = $(data);
			
			var columns=[{title:"通知单号",name:"serial"}, {title:"开票时间",name:"invoiceTime"},{title:"车船号",name:"vsName"},
			             {title:"货品",name:"productName"},{title:"货体号",name:"goodsCode",render:function(item){
			            	 return '<a href="javascript:void(0);" onclick="GoodsLZ.openGoodsLZ('+item.goodsId+');" >'+item.goodsCode+'</a>';
			             }},{title:"原号",name:"yuanhao"},{title:"调号",name:"diaohao"},
			             {title:"货主单位",name:"clientName"},{title:"提货单位",name:"ladingClientName"},
			             {title:"提货凭证",name:"ladingEvidence"},{title:"出库时间",name:"createTime"},
			             {title:"开票量(吨)",name:"deliverNum"},{title:"实发量(吨)",name:"actualNum"},{title:"冲销理由",name:"deliverNo"}];
			if(dialog.find("div[data-role='logGrid']").getGrid()) 
				dialog.find("div[data-role='logGrid']").getGrid().destory();
			
			dialog.find("div[data-role='logGrid']").grid({
				identity : 'id',
				columns:columns,
				showPage:8,
				pageSize:10,
				isShowIndexCol:true,
				isShowPages : true,
				url:config.getDomain()+"/tradeInvoice/getTradeInvoiceLogList",
				callback:function(){
					dialog.find("div[data-role='logGrid']").find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
				}
			});
			
			
			dialog.modal({
				keyboard : true
			});
		});
	};
	
	
	return{
		initDialog:initDialog
	}
}();