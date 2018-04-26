//油品参数表
var TankMeasure=function(){
	
	var systemUserId,systemUserName;//登陆人名称
	
	function init(){
		initSearchCtr();
		initTable();
		//获取系统userId
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
	
	function initSearchCtr(){
		initFormIput();
		util.urlHandleTypeaheadAllData("/product/select",$('#productName'),undefined,undefined,function(data){
			if(data){
				$('.tankDiv').empty().append('<input type="text" id="tankId" class="form-control"/>');
				util.urlHandleTypeaheadAllData("/baseController/getTankCode?productId="+data.id,$('#tankName'),function(item){return item.code});
			}
		});
		util.urlHandleTypeaheadAllData("/baseController/getTankCode",$('#tankName'),function(item){return item.code});
		$(".reset").click(function(){
			$('#productName,#tankName').removeAttr('data').val('');
			$('div[data-role="tankmeasureGrid"]').getGrid().search();
		});
		$(".search").click(function(){
			$('div[data-role="tankmeasureGrid"]').getGrid().search({
				productId:$('#productName').attr('data'),
				tankId:$('#tankName').attr('data')
			});
		});
		$(".btn-add").click(function(){
			initTankMeasure();
		});
	};
	
	function initTable(){
		var columns=[{title:"识别号",width:100,name:"notify"},{title:"货品",width:100,name:"productName"},{title:"罐号",width:100,name:"tankName"},
		             {title:"发货口",width:100,name:"port"},{title:"标准密度",width:100,name:"normalDensity"},{title:"视密度",width:100,name:"textDensity"},
		             {title:"视温度",width:100,name:"textTemperature"},{title:"体积比",width:100,name:"volumeRatio"},{title:"货罐温度",width:100,name:"tankTemperature"},
		             {title:"提交时间",width:140,name:"createTimeStr"},{title:"说明",width:140,name:"description"}, {title:"操作",width:120,render:function(item){
		            	 return '<div style="width:100px;" class="input-group-btn"><a href="javascript:void(0)" style="margin:0 2px;" onclick="TankMeasure.initTankMeasure('+item.id+')" class="btn btn-xs blue" ><span class="glyphicon glyphicon-edit" title="修改"></span></a>'
	        	         +'<a href="javascript:void(0)" style="margin:0 2px;" onclick="TankMeasure.deleteTankMeasuer('+item.id+')" class="btn btn-xs red " ><span class="glyphicon glyphicon-remove" title="清除"></span></a></div>';
		             }}];
		if($('div[data-role="tankmeasureGrid"]').getGrid()){
			$('div[data-role="tankmeasureGrid"]').getGrid().destory();
		}
		
		$('div[data-role="tankmeasureGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			url : config.getDomain()+"/tankmeasure/list",
			callback:function(){
				$('[data-role="tankmeasureGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
			}
		});
		
		
	};
	
	function deleteTankMeasuer(id){
		if(id){
			$('body').confirm({
				content:'确定要删除吗?',
				callBack:function(){
			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+"/tankmeasure/delete",
				data:{id:id},
				dataType:'json',
				success:function(data){
					config.unload();
					util.ajaxResult(data,'删除',function(){
						$('[data-role="tankmeasureGrid"]').getGrid().refresh();
					},true);
				}
			});}
			});
		}
	};
	
	function initTankMeasure(id){
		$.get(config.getResource()+"/pages/auth/baseinfo/tankmeasure/add.jsp").done(function(data){
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
	};
	
	function initDialogCtr(dialog){
		if(util.isNull(dialog.find("#id").text(),1)==0){
			$.ajax({
				type:'get',
				url:config.getDomain()+"/tankmeasure/getcodenum",
				success:function(data){
					util.ajaxResult(data,'获取识别号',function(ndata,nmap){
						dialog.find("#notify").val(nmap.codenum);
					},true);
				}
			});
		}
		
		
		util.urlHandleTypeaheadAllData("/product/select?oils=1",dialog.find('#productId'),undefined,undefined,function(item){
			if(item){
				dialog.find(".tankDiv").empty().append('<input type="text" class=" form-control" id="tankId" data-required="1" data-type="Require"  >');
				util.urlHandleTypeaheadAllData("/baseController/getTankCode?productId="+item.id,dialog.find('#tankId'),function(itemData){return itemData.code});
			}
		});
		
		util.initTimePicker(dialog,true);
		util.initTimeVal(dialog.find("#createTime"),util.currentTime(1),true);
		util.urlHandleTypeaheadAllData("/baseController/getTankCode",dialog.find("#tankId"),function(item){ return item.code},undefined,undefined,200);
		
		dialog.find("#addTankMeasure").click(function(){
			
			if(config.validateForm(dialog.find(".addGoodsForm"))){
			
			var id=util.isNull(dialog.find("#id").text(),1);
			var data={
					id:id==0?undefined:id,
					notify:dialog.find("#notify").val(),
					tankId:dialog.find("#tankId").attr('data'),
					productId:dialog.find("#productId").attr('data'),
					port:dialog.find("#port").val(),
					normalDensity:dialog.find("#normalDensity").val(),
					textDensity:dialog.find("#textDensity").val(),
					textTemperature:dialog.find("#textTemperature").val(),
					volumeRatio:dialog.find("#volumeRatio").val(),
					tankTemperature:dialog.find("#tankTemperature").val(),
					description:dialog.find("#description").val(),
					createTime:util.formatLong(util.getTimeVal(dialog.find("#createTime"),true)),
					userId:systemUserId
			}
			$.ajax({
				type:'post',
				url:config.getDomain()+'/tankmeasure/'+(id==0?'add':'update'),
				dataType:'json',
				data:data,
				success:function(data){
				util.ajaxResult(data,id==0?'添加':'更新',function(undefined,nmap){
					if(nmap&&nmap.id){
						dialog.find("#id").text(nmap.id);
						dialog.find("#userId").text(systemUserName);
					}
					$('div[data-role="tankmeasureGrid"]').getGrid().refresh();
					dialog.remove();
				});
					
				}
			});
			}
		});
	};
	function initDialogData(id,dialog){
		if(id){
		$.ajax({
				type:'post',
				url:config.getDomain()+"/tankmeasure/list",
				data:{id:id},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'获取数据',function(ndata){
						if(ndata&&ndata.length==1){
						
							dialog.find("#notify").val(util.isNull(ndata[0].notify));
						    dialog.find("#tankId").val(util.isNull(ndata[0].tankName)).attr('data',ndata[0].tankId);
						    dialog.find("#productId").val(util.isNull(ndata[0].productName)).attr('data',ndata[0].productId);
						    dialog.find("#port").val(util.isNull(ndata[0].port));
						    dialog.find("#normalDensity").val(util.isNull(ndata[0].normalDensity));
						    dialog.find("#textDensity").val(util.isNull(ndata[0].textDensity));
						    dialog.find("#textTemperature").val(util.isNull(ndata[0].textTemperature));
						    dialog.find("#volumeRatio").val(util.isNull(ndata[0].volumeRatio));
						    dialog.find("#tankTemperature").val(util.isNull(ndata[0].tankTemperature));
						    dialog.find("#description").val(util.isNull(ndata[0].description));
						    dialog.find("#userId").text(util.isNull(ndata[0].userName)).attr('data',ndata[0].userId);
						    util.initTimeVal(dialog.find("#createTime"),ndata[0].createTimeStr,true);
						}	
					},true);}
				});
		}
		
	}; 
	
	function exportExcel(){
		var url=config.getDomain()+'/tankmeasure/exportexcel?id=id';
		if(util.isNull($("#productName").attr('data'),1)!=0)
			url+='&productId='+$("#productName").attr('data');
		if(util.isNull($("#tankName").attr('data'),1)!=0)
			url+='&tankId='+$("#tankName").attr('data');
		window.open(url);
	};
	
return {
	init:init,
	initTankMeasure:initTankMeasure,
	deleteTankMeasuer:deleteTankMeasuer,
	exportExcel:exportExcel
};	
	
}();