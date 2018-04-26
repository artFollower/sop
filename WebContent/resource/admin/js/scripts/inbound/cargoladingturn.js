
//货批，转卖提单流转记录
CargoLadingTurn=function(){
   //type 1 货批   type 2 转卖提单
	function init(id,type){
		var trunType=type;
		$.get(config.getResource()+ "/pages/inbound/storageFee/dialog_turnlist.jsp").done(function(data) {
			var dialog = $(data);
			dialog.find('[data-dismiss="modal"]').click(function(){
				dialog.remove();
			});
			
			var columns=[{title:"时间",name:"l_time"},
//			             {title:"货主",name:"clientName"},
			             {title:"上下级",name:"contactClientName"},{title:"性质",render:function(item){
				if(item.type==0){
					return "";
				}else if(item.type==1){
					return trunType==1?"入库":"调入";
				}else if(item.type==2){
					return "提货";
				}else if(item.type==3){
					return "货权转移";
				}else if(item.type==4){
					return "起计";
				}else if(item.type==5){
					return "扣损";
				}
			}},{title:"调单号",name:"l_ladingCode"},{title:"车船号",name:"truckCode"},{title:"操作量",render:function(item){
				
				if(item.type==1){
					return item.operateNum;
				}else if(item.type==2||item.type==3||item.type==5){
					return util.isNull(-item.operateNum);
				}
			}},{title:'结存量',name:'currentNum'}];
			
			if(dialog.find('[data-role="exceedFeeGrid"]').getGrid()!=null){
				dialog.find('[data-role="exceedFeeGrid"]').getGrid().destory();
			}	
			var mUrl;
			if(type==1){
				mUrl="/exceedfee/cargoturnlist?cargoId="+id;
			}else if(type==2){
				mUrl="/exceedfee/ladingturnlist?ladingId="+id;
			}
			dialog.find('[data-role="trunlistTable"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : false,
				url : config.getDomain()+mUrl
	            });
			dialog.modal({
				keyboard : true
			});
			
			
			dialog.find("#print").unbind('click'); 
			dialog.find("#print").click(function(){
				dialog.find('.modal-body').jqprint();
			});
			
			
			dialog.find("#excel").unbind('click'); 
			dialog.find("#excel").click(function(){
				
				var t=type;
				var url;
				if(type==2){
					t=3;
					url=config.getDomain()+"/exceedfee/excel?sType="+t+"&ladingId="+id;
				}else{
					url=config.getDomain()+"/exceedfee/excel?sType="+t+"&cargoId="+id;
				}
				window.open(url) ;
			});
			
			
		});
	}
	return{
		init:init
	}
}();