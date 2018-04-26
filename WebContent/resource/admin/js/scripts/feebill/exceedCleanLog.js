var ExceedCleanLog = function() {
	
	var initDialog = function(cargoId,ladingId) {
		$.get(config.getResource()+ "/pages/inbound/storageFee/dialog_exceedcleanlog.jsp").done(function(data){
			var dialog=$(data);
			initDialogCtr(dialog,cargoId,ladingId);
			initDialogMsg(dialog,cargoId,ladingId);
		});
	};
	
	var initDialogCtr = function(dialog,cargoId,ladingId) {
		dialog.modal({keyboard:true});
		dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
		
		dialog.find(".save").click(function(){
			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+'/exceedfee/addExceedCleanLog',
				dataType:'json',
				data:{
					content:dialog.find("#content").val(),
					cargoId:cargoId,
					ladingId:ladingId,
					createTime:util.formatLong(util.currentTime(0))
				},
				success:function(data){
					util.ajaxResult(data,"保存",function(){
						initDialogMsg(dialog,cargoId,ladingId);
					});
				}
			});
			
		});
		
	};
	
	var initDialogMsg = function(dialog,cargoId,ladingId) {
		dialog.find("#cargoId").text(cargoId);
		dialog.find("#ladingId").text(ladingId);
		if(dialog.find('[data-role="cleanlogsTable"]').getGrid())
			dialog.find('[data-role="cleanlogsTable"]').getGrid().destory();
		dialog.find('[data-role="cleanlogsTable"]').grid({
			identity : 'id',
			url : config.getDomain()+"/exceedfee/getExceedCleanLog",
			columns:getColumns(),
			isShowIndexCol : false,
			isShowPages : false,
			searchCondition:{
				cargoId:cargoId,
				ladingId:ladingId
			},
		});
	};
	
	var getColumns = function() {
		
		return [{title:"内容",name:"content"},{title:"创建人",name:"createUserName"}
		,{title:"创建时间",name:"createTime"},{title:"操作",render:function(item){
			return '<a href="javascript:void(0)" class="btn btn-xs red" onclick="ExceedCleanLog.deleteLog('+item.id+')"><span class="fa fa-remove" title="删除"></span></a>';
		}}];
	};
	
	var deleteLog = function(id) {
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/exceedfee/deleteExceedCleanLog",
			data:{id:id},
			success:function(data){
				$('[data-role="cleanlogsTable"]').getGrid().refresh();
			}
		});
	};
	return {
		initDialog:initDialog,
		deleteLog:deleteLog
	}
}();