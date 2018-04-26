var Approve = function() {
	
	var dialog 	= null;    		//对话框
	var dataGrid 	= null; 		//Grid对象
	
	var userId=0;
	
	var doApprove=function(index){
		
		var indexData=$('[data-role="approveGrid"]').getGrid().getItemByIndex(index);
		$.get(config.getResource()+"/pages/approve/approveDialog.jsp").done(function(data){
			dialog = $(data) ;
			dialog.modal({
				keyboard: true
			});
			
			dialog.find("#createUserName").val(indexData.createUserName);
			dialog.find("#reviewUserName").val(indexData.reviewUserName);
			dialog.find("#createTime").val(indexData.mCreateTime);
			dialog.find("#reviewTime").val(indexData.mReviewTime);
			dialog.find("#content").val(indexData.content);
			dialog.find("#reason").val(indexData.reason);
			if(indexData.status!=1){
				dialog.find(".noPass").hide();
				dialog.find(".pass").hide();
			}
			
			if(indexData.reviewUserId!=userId){
				dialog.find(".noPass").hide();
				dialog.find(".pass").hide();
			}
			
			dialog.find(".noPass").click(function() {
				$.ajax({
					 async:false,
			  			type : "get",
			  			url : config.getDomain()+"/approve/updateStatus?status=3&id="+indexData.id,
			  			dataType : "json",
			  			success : function(data) {
							 if (data.code == "0000") {
								 $("body").message({
									 type : 'success',
									 content : '操作成功'
								 });
								 dialog.remove();
								$('[data-role="approveGrid"]').grid('refresh');
							 }else {
								 $("body").message({
									 type : 'error',
									 content : '操作失败'
								 });
							 }
						 }
				});
			});
			
			dialog.find(".pass").click(function() {
				
				$.ajax({
					 async:false,
			  			type : "get",
			  			url : config.getDomain()+indexData.url,
			  			dataType : "json",
			  			success : function(data) {
							 if (data.code == "0000") {
								 
								 $.ajax({
									 async:false,
							  			type : "get",
							  			url : config.getDomain()+"/approve/updateStatus?status=2&id="+indexData.id,
							  			dataType : "json",
							  			success : function(data) {
											 if (data.code == "0000") {
												 $("body").message({
													 type : 'success',
													 content : '操作成功'
												 });
												 dialog.remove();
												$('[data-role="approveGrid"]').grid('refresh');
											 }else {
												 $("body").message({
													 type : 'error',
													 content : '操作失败'
												 });
											 }
										 }
								});
								 
							 }else if(data.code == "1003"){
								 $("body").message({
									 type : 'error',
									 content : data.msg
								 });
								 
								 //当前情况无法完成，审批失效
								 $.ajax({
									 async:false,
							  			type : "get",
							  			url : config.getDomain()+"/approve/updateStatus?status=4&id="+indexData.id,
							  			dataType : "json",
							  			success : function(data) {
											 if (data.code == "0000") {
												 dialog.remove();
												$('[data-role="approveGrid"]').grid('refresh');
											 }else {
												 $("body").message({
													 type : 'error',
													 content : '操作失败'
												 });
											 }
										 }
								});
								 
								 
							 }else{
								 $("body").message({
									 type : 'error',
									 content : '操作失败'
								 });
							 }
						 }
				});
			
				
				
				
				
			});
			
			
		});
	}
	
	var handleRecords = function() {
		userId=config.findUserId();
		var columns = [ {
			title : "提交人",
			name : "createUserName",
			width:60
		}, {
			title : "模块",
			name : "part",
			width:60,
			render: function(item, name, index){
				if(item.part==1){
					return "入港计划";
					
				}else if(item.part==2){
					return "入库确认";
				}else if(item.part==3){
					return "提单管理";
				}
				
			}
		}, {
			title : "提交内容",
			name : "content",
			width:300
		},{
			title : "提交理由",
			name : "reason",
			width:300
		},{
			title : "提交时间",
			name : "mCreateTime",
			width:100
		}, {
			title : "审核人",
			name : "reviewUserName",
			width:60
		}, {
			title : "审核时间",
			name : "mReviewTime",
			width:100
		}, {
			title : "状态",
			name : "status",
			width:60,
			render: function(item, name, index){
				if(item.status==1){
					return "未审核";
				}else if(item.status==2){
					return "已通过";
				}else if(item.status==3){
					return "不通过";
				}else if(item.status==4){
					return "已失效";
				}
				
			}
		}, {
			title : "操作",
			name : "id",
			width:40,
			render: function(item, name, index){
					return "<a href='javascript:void(0)' onclick='Approve.doApprove("+index+")' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-eye-open' title='查看'></span></a></shiro:hasPermission>";
			
			}
		}  ];

		/*解决id冲突的问题*/
		$('[data-role="approveGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			url : config.getDomain()+"/approve/list?createUserId="+userId
		});
		
		
	};

	return {
		init : function() {
			initFormIput();
			handleRecords();
		},
		search : function() {
			var params = {};
            $("#approveListForm").find('.form-control').each(function(){
                var $this = $(this);
                var name = $this.attr('name');
                 if(name){
                    params[name] = $this.val();
                }
            });
           $('[data-role="approveGrid"]').getGrid().search(params);
		},
		doApprove:doApprove
	};
	
	
	
}();