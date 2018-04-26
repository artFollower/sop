var PumpShed=function(){
	var systemUserId;
	function init(){
		initSearch();
		initTable();
		getSystemMsg();
	};
	
	function getSystemMsg(){
		if(!systemUserId){
			$.ajax({
				type:'post',
				url:config.getDomain()+"/initialfee/getsystemuser",
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'获取系统信息',function(ndata){
						systemUserId=ndata[0].userId;
						return systemUserId;
					},true);
				}
			});
		}else{
			return systemUserId;
		}
	}
	
	function initSearch(){
		$(".modify").hide();
		$("#managerSearch").unbind('click').click(function() {
			var key = $("#queryUserDivId").find('.search-key').val();
			var params = {'name':key};
           $('[data-role="baseInfoGrid"]').getGrid().search(params);
		});
		if(!config.hasPermission('AADDBASEPUMPSHED')){
			$(".btn-add").hide();
		}else{
			$(".btn-add").show();
		}
		if(!config.hasPermission('ADELETEBASEPUMPSHED')){
			$(".btn-remove").hide();
		}else{
			$(".btn-remove").show();
		}
		$(".btn-add").unbind('click').click(function(){
				initDialog();
			});
		
		$(".btn-remove").unbind('click').click(function(){
			if(validateData($('[data-role="baseInfoGrid"]'),1)){
			 var ids="";	
			 var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
            for(var i=0;i<data.length;i++){
            	if(i!=data.length-1){
            		ids+=data[i]+",";
            	}else{
            		ids+=data[i];
            	}
            }
            $("body").confirm({
				content : '确定要撤销所选记录吗?',
				callBack : function() {
					config.load();
				 $.ajax({
					 type : "post",
						url : config.getDomain() + "/pumpshed/delete",
						data : {"ids":ids},
						dataType : "json",
						success:function(data){
							util.ajaxResult(data,'撤销',function(){
								$('[data-role="baseInfoGrid"]').getGrid().refresh();								
							});
						}					 
				 });}
			});
			}
			 });
		
	};
	
	function initTable(){
		var columns=[{title:'名称',render:function(item){
			if(config.hasPermission('AUPDATEBASEPUMPSHED'))
			return '<a href="javascript:void(0)" onClick="PumpShed.initDialog('+item.id+')">'+item.name+'</a>';
			else
				return item.name;
		}},{title:'描述',name:'description'},{title:'最后编辑人',name:'editUserName'},{title:'最后编辑时间',name:'editTimeStr'}];
		if ($('[data-role="baseInfoGrid"]').getGrid() != null) 
			$('[data-role="baseInfoGrid"]').getGrid().destory();
		
		$('[data-role="baseInfoGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain() + "/pumpshed/list"
		});
		
	};
	
	function initDialog(id){
		$.get(config.getResource()+"/pages/auth/baseinfo/pumpShed/dialog_pumpShed.jsp").done(function(data){
			var dialog=$(data);
			initDialogCtr(dialog);
			initDialogMsg(dialog,id);
		});
	};
	
	function initDialogCtr(dialog){
		initFormIput(dialog);
		dialog.modal({keyboard: true});
		dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
		dialog.find("#save").click(function(){
			var id=util.isNull(dialog.find("#id").text(),1)==0?undefined:util.isNull(dialog.find("#id").text(),1);
			if(config.validateForm(dialog.find(".modal-body"))){
				$.ajax({
					type:'post',
					url:config.getDomain()+(id?'/pumpshed/update':'/pumpshed/add'),
					dataType:'json',
					data:{
						id:id,
						name:dialog.find('#name').val(),
						description:dialog.find("#description").val(),
						type:0,
						editUserId:getSystemMsg(),
						editTime:util.formatLong(util.currentTime(1)),
						status:0
					},
					success:function(data){
						util.ajaxResult(data,(id?'更新':'保存'),function(ndata,nmap){
							if(id){
								initDialogMsg(dialog,id);
								util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:id,url:'/pumpshed/list'});
							}else{
								initDialogMsg(dialog,nmap.id);
								$('[data-role="baseInfoGrid"]').getGrid().refresh();
							}
							dialog.remove();
						});
					}
				});
				
				
				
			}
		});
	};
	
	function initDialogMsg(dialog,id){
		if(id){
			$.ajax({
				type:'post',
				url:config.getDomain()+'/pumpshed/list',
				dataType:'json',
				data:{id:id},
				success:function(data){
					util.ajaxResult(data,'获取泵棚信息',function(ndata){
						if(ndata&&ndata.length>0){
							var itemMsg=ndata[0];
						dialog.find("#id").text(id);
						dialog.find("#name").val(itemMsg.name);
						dialog.find('#description').val(util.isNull(itemMsg.description));
						}
					},true);
				}
			});
		}
	};
	
	return{
		init:init,
		initTable:initTable,
		initDialog:initDialog
	}
}();