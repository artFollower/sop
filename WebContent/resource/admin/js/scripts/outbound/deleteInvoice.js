var DeleteInvoice=function(){
	function initDialog(){
		$.get(config.getResource()+ "/pages/outbound/invoice/deleteInvoice.jsp").done(function(data) {
			var dialog = $(data);
			initDialogTable(dialog);
			initDialogControl(dialog);
			
		});
	};
	function initDialogControl(dialog){
		dialog.modal({ keyboard : true });
		util.urlHandleTypeaheadAllData("/product/select",dialog.find('#productName')); // 初始化货品
		util.urlHandleTypeaheadAllData("/baseController/getClientName",dialog.find('#clientName'),function(item){return item.name+"["+item.code+"]";});
		//搜索
		dialog.find(".search").click(function(){
			dialog.find('[data-role="deleteInvoiceGrid"]').getGrid().search(getSearchCondition(dialog));
		});
		//重置
		dialog.find(".reset").click(function(){
			dialog.find("#vehicleShipNo,#serial,#ladingEvidence").val('');
			dialog.find("#productName").val("").removeAttr();
			dialog.find("#clientName").val("").removeAttr();
			dialog.find("#deliverType").val(0);
			dialog.find("#cancelType").val(-1);
			dialog.find(".search").click();
		});
		
	};
	
	function getSearchCondition(dialog){
		return {
			type:8,
		  vsName:dialog.find("#vehicleShipNo").val(),
	       productId:util.isNull(dialog.find("#productName").attr("data"),1),
	       clientId:util.isNull(dialog.find("#clientName").attr("data"),1),
	       deliverType:dialog.find("#deliverType").val(),//车发，船发，全部
	       cancelType:dialog.find("#cancelType").val(),//0，开票删除，1.冲销作废
	       ladingEvidence:dialog.find("#ladingEvidence").val(),//提单号
	       serial:dialog.find("#serial").val()
		};
	};
	
	function initDialogTable(dialog){
		var columns=[{title:"通知单号",name:"serial"},{title:"客户编号",name:"clientCode"},{title:"车船号",render: function(item){
			if(item.deliverType==1){
				return "<label style='width:90px;'>"+util.isNull(item.vsName)+"</label>";
			}else{
				return "<label style='width:90px;'>"+util.isNull(item.vsName)+"/"+util.isNull(item.shipArrivalTime)+"</label>";
			}
		}},
		             {title:"开票时间",name:"invoiceTime"},{title:"货体号",name:"code"},{title:"货品",name:"productName"},
		             {title:"货主",name:"clientName"},{title:"提货单位",name:"ladingClientName"},{title:"原号",name:"yuanhao"},
		             {title:"调号",name:"diaohao"},{title:"提单号",name:"ladingEvidence"},{title:"开票量(吨)",render: function(item){
		     			return util.toDecimal3(item.deliverNum,true);
		     		}},{title:"开票人",name:"createUserName"},{title:"实发量(吨)",name:"actualNum"},{title:"冲销理由",render:function(item){
		     			if(item.actualType==1)
		     				return item.deliverNo;
		     				else
		     					return "";
		     		}}];
		/*解决id冲突的问题*/
		dialog.find('[data-role="deleteInvoiceGrid"]').grid({
			identity : 'id',
			columns : columns,
			showPage:8,
			isShowIndexCol : false,
			isShowPages : true,
			searchCondition:getSearchCondition(dialog),
			url : config.getDomain()+"/invoice/list",
			callback:function(){
				dialog.find('[data-role="deleteInvoiceGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
			}
		});	
	};
	
	function exportExcel(obj){
		var _dialog=$(obj).closest(".modal-dialog");
		var url=config.getDomain()+'/invoice/exportExcel?type=8';
		
	       if(util.isNull(_dialog.find("#vehicleShipNo").val())!='')
	    	   url+='&vsName='+_dialog.find("#vehicleShipNo").val();
	       if(util.isNull(_dialog.find("#productName").attr("data"),1)!=0)
	    	   url+='&productId='+_dialog.find("#productName").attr("data");
	       if(util.isNull(_dialog.find("#clientName").attr("data"),1)!=0)
	    	   url+='&clientId='+_dialog.find("#clientName").attr("data");	   
		   if(util.isNull(_dialog.find("#deliverType").val(),1)!=0)
			   url+='&deliverType='+_dialog.find("#deliverType").val();
		   if(util.isNull(_dialog.find("#cancelType").val(),1)!=0)
			   url+='&cancelType='+_dialog.find("#cancelType").val()
		   if(util.isNull(_dialog.find("#ladingEvidence").val())!='')
			   url+='&ladingEvidence='+_dialog.find("#ladingEvidence").val()
		   if(util.isNull(_dialog.find("#serial").val())!='')
			   url+='&serial='+_dialog.find("#serial").val()
			window.open(url);
	};
	
	return {
		initDialog:initDialog,
		exportExcel:exportExcel
	}
}();