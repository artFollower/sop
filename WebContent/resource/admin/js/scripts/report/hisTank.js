var HisTank=function(){
	function initList(){
		$.get(config.getResource()+ "/pages/report/HisTankList.jsp").done(
         function(data) {
		var dialog = $(data);
		initSearchCondition(dialog);			
		initTable(dialog);
		dialog.modal({
			keyboard : true
		}); });	
	};
function initSearchCondition(dialog){
	util.urlHandleTypeaheadAllData("/product/select",dialog.find('#productId'));
	util.urlHandleTypeaheadAllData("/baseController/getTankCode",dialog.find('#tankId'),function(item){return item.code});	
	util.initDatePicker(dialog);
	dialog.find("#startTime").val(util.currentTime(0));
	dialog.find("#endTime").val(util.currentTime(0));
	dialog.find("#search").click(function(){
		dialog.find('[data-role="hisTankTable"]').getGrid().search(getSearchCondition(dialog));
	});
	
}	
function getSearchCondition(dialog){
	return {
		productId:dialog.find("#productId").attr("data"),
		tankId:dialog.find("#tankId").attr("data"),
		startTime:util.formatLong(dialog.find("#startTime").val()+" 00:00:00"),
		endTime:util.formatLong(dialog.find("#endTime").val()+" 23:59:59")
	}
}	
function initTable(dialog){
	var columns=[{title:"日期",name:"DTimeStr"},{title:"储罐",name:"tankCode",render:function(item){
		if(config.hasPermission('AHISTANKLOGUPDATE')){
		 return '<a href="javascript:void(0);" onclick="HisTank.editTankLog('+"'"+item.tank+"'"+','+item.DTime+')" >'+item.tankCode+'</a>';
		}else{
			return item.tankCode;
		}
	}},{title:"货品",name:"productName"},{title:"当时温度(°c)",name:"temperature"},
	{title:"当时存储(吨)",name:"materialWeight"},{title:"当时最大存储(吨)",name:"capacityTotal"}
	,{title:"类型",render:function(item){
	            	 if(item.tankType){
	            		 if(item.tankType==0){
	            			 return "内贸";
	            		 }else if(item.tankType==1){
	            			 return "外贸";
	            		 }else if(item.tankType==2){
	            			 return "保税非包罐";
	            		 }else if(item.tankType==3){
	            			 return "保税包罐";
	            		 }else{
	            			 return "内贸";
	            		 }
	            	 }else{
	            		 return "内贸";
	            	 }
	             }},{title:"状态",render:function(item){
	            	 if(item.isUse&&item.isUse==1){
	            		 return "使用中";
	            	 }else{
	            		 return "已停用";
	            	 }
	            	 
	             }}];
	dialog.find('[data-role="hisTankTable"]').grid({
		identity : 'id',
		columns : columns,
		showPage:8,
		searchCondition:getSearchCondition(dialog),
		isShowIndexCol : false,
		isShowPages : true,
		url : config.getDomain()+"/histank/list",
		callback:function(){
			dialog.find('[data-role="hisTankTable"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
		}
	});
};

	function editTankLog(tank,dtime){
		$.get(config.getResource()+ "/pages/report/editHisTank.jsp").done(function(data){
				var dialog = $(data);
				initEditTankLogCtr(dialog,tank,dtime);
				initEditTankLogMsg(dialog,tank,dtime);
		 });
	};
	
	function initEditTankLogCtr(dialog,tank,dtime){
		dialog.modal({keyboard : true});
		dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
		util.urlHandleTypeaheadAllData("/product/select",dialog.find('#productName'),function(item){return item.name},function(item){return item.code}); // 初始化货品
		dialog.find("#save").click(function(){
			$.ajax({
				type:'post',
				url:config.getDomain()+'/histank/update',
				dataType:'json',
				data:{
					tank:tank,
					DTime:dtime,
					tankType:dialog.find('#type').val(),
					isUse:dialog.find('#isUse').val(),
					capacityTotal:dialog.find('#capacityTotal').val(),
					goods:dialog.find('#productName').attr('data')
				},
				success:function(data){
					util.ajaxResult(data,'保存',function(){
						initEditTankLogMsg(dialog,tank,dtime);
						$('[data-role="hisTankTable"]').getGrid().refresh();
					});
				}
			});
		});
	};
	
	function initEditTankLogMsg(dialog,tank,dtime){
		$.ajax({
			type:'post',
			url:config.getDomain()+"/histank/list",
			dataType:'json',
			data:{'tank':tank,'Dtime':dtime},
			success:function(data){
				util.ajaxResult(data,'获取储罐历史信息',function(ndata){
					if(ndata&&ndata.length>0){
						var msg=ndata[0];
						dialog.find('#tankCode').val(msg.tankCode).attr('data',msg.tank);
						dialog.find('#Dtime').val(msg.DTimeStr).attr('data',msg.Dtime);
						dialog.find('#productName').val(msg.productName).attr('data',msg.goods);
						dialog.find('#type').val(msg.tankType);
						dialog.find('#temperature').val(msg.temperature);
						dialog.find('#materialWeight').val(msg.materialWeight);
						dialog.find('#capacityTotal').val(msg.capacityTotal);
						dialog.find('#isUse').val(msg.isUse);
					}
				},true);
			}
		});
	};
	
return{
 initList:initList,
 editTankLog:editTankLog	
}
	
}();