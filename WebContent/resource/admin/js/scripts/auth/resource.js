var Resource = function() {
	
	var initPmenu = function($element) {
		$.ajax({
    		url: config.getDomain()+"/auth/resource/get",
    		data: 'pagesize=0&category=MENU',
    		type: "POST",
    		success: function(data){
    			if(data.code == "0000"){
    				var option = "";
    				for(var i=0;i<data.data.length;i++) {
    					option += "<option value='"+data.data[i].id+"'>"+data.data[i].name+"</option>";
    				}
    				$($element).append(option);
    			}
    		}
    	});
	};
	
	var add = function() {
		dataGrid = $('[data-role="resourceGrid"]');
		$.get(config.getResource()+"/pages/auth/resource/template.jsp").done(function(data){
			dialog = $(data);
			initPmenu(dialog.find('select[name="parent.id"]'));
			dialog.find('#close').on('click',function(){
				dialog.remove();
			});
			dialog.find('#save').on('click',function(){
				$(this).attr('disabled', 'disabled');
				if(config.validateForm($(".resource-form"))) {
					$.ajax({
			    		url: config.getDomain()+"/auth/resource/create",
			    		data: $('.resource-form').serialize(),
			    		type: "POST",
			    		success: function(data){
			    			if(data.code == "0000"){
			    				dialog.remove();
			    				$("body").message({
									type: 'success',
									content: data.msg
								});
			    				dataGrid.grid('refresh');
			    			}else{
			    				dialog.find('#save').removeAttr('disabled');
			    				$("body").message({
									type: 'error',
									content: data.msg
								});
			    			}
			    		}
			    	});
				}else {
					dialog.find('#save').removeAttr('disabled');
				}
			}).end().modal({
				keyboard: true
			});
			
		});
		
	};
	
	var modify = function(resourceId) {
		dataGrid = $('[data-role="resourceGrid"]');
		$.get(config.getResource()+"/pages/auth/resource/template.jsp").done(function(data){
			dialog = $(data);
			$.ajax({
	    		url: config.getDomain()+"/auth/resource/get?id="+resourceId,
	    		type: "POST",
	    		success: function(data){
	    			if(data.code == "0000"){
	    				var _data = data.data[0];
	    				initFormParams(dialog.find(".resource-form"),_data);
	    				dialog.find("select[name=status]").children("option").each(function() {
	    					if($(this).val() == _data.status) {
	    						$(this).attr("selected","true");
	    					}
	    				});
	    				

	    				$.ajax({
	    		    		url: config.getDomain()+"/auth/resource/get",
	    		    		data: 'pagesize=0&category=MENU',
	    		    		type: "POST",
	    		    		success: function(data){
	    		    			if(data.code == "0000"){
	    		    				var option = "";
	    		    				for(var i=0;i<data.data.length;i++) {
	    		    					if(data.data[i].id == _data.parentId) {
	    		    						option += "<option selected='selected' value='"+data.data[i].id+"'>"+data.data[i].name+"</option>";
	    		    					}else {
	    		    						option += "<option value='"+data.data[i].id+"'>"+data.data[i].name+"</option>";
	    		    					}
	    		    				}
	    		    				dialog.find('select[name="parent.id"]').append(option);
	    		    			}
	    		    		}
	    		    	});
	    			
	    				
	    			}else{
	    				dialog.remove();
	    				$("body").message({
							type: 'error',
							content: data.msg
						});
	    			}
	    		}
	    	});
			
			dialog.find('#close').on('click',function(){
				dialog.remove();
			});
			dialog.find('#save').on('click',function(){
				$(this).attr('disabled', 'disabled');
				if(config.validateForm($(".resource-form"))) {
					$.ajax({
			    		url: config.getDomain()+"/auth/resource/update?id="+resourceId,
			    		data: $(".resource-form").serialize(),
			    		type: "POST",
			    		success: function(data){
			    			if(data.code == "0000"){
			    				dialog.remove();
			    				$("body").message({
									type: 'success',
									content: data.msg
								});
			    				dataGrid.grid('refresh');
			    			}else{
			    				dialog.find('#save').removeAttr('disabled');
			    				$("body").message({
									type: 'error',
									content: data.msg
								});
			    			}
			    		}
			    	});
				}else {
					dialog.find('#save').removeAttr('disabled');
				}
			}).end().modal({
				keyboard: true
			});
		});
		
	};
	
	var deleteResource = function(data) {
        dataGrid = $('[data-role="resourceGrid"]');
        
        dataGrid.confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
				 $.post(config.getDomain()+'/auth/resource/remove', "ids="+data).done(function (data) {
			            if (data.code == "0000") {
			            	$("body").message({
			                    type: 'success',
			                    content: data.msg
			                });
			                dataGrid.grid('refresh');
			            } else {
			                $('body').message({
			                    type: 'error',
			                    content: data.msg
			                });
			            }
			        }).fail(function (data) {
			        	$("body").message({
			                type: 'error',
			                content: '网络繁忙,请稍后再试'
			            });
			        });
			}
		});
        
    };
    
    var initRoleTree = function() {//权限树结构
    	$.get(config.getResource()+"/pages/auth/resource/roles.jsp").done(function(data){
			dialog = $(data);
			
			dialog.find(".modal-body").jstree({
		           "core" : {
		               "themes" : {
		                   "responsive": false
		               }, 
		               // so that create works
		               "check_callback" : true,
		               'data' : {
		                   'url' : function (node) {
		                     return config.getDomain()+"/auth/role/getGrantPermission";
		                   },
		                   'data' : function (node) {
		                     return { 'parent' : node.id };
		                   }
		               }
		           },
		           "types" : {
		               "default" : {
		                   "icon" : "fa fa-folder icon-state-warning icon-lg"
		               },
		               "file" : {
		                   "icon" : "fa fa-file icon-state-warning icon-lg"
		               }
		           },
		           "state" : { "key" : "demo3" },
		           "plugins" : [ "dnd", "state", "types" ]
		       }).on('move_node.jstree', function(e,data){
	               
		    	   $.ajax({
			    		url: config.getDomain()+"/auth/resource/move",
			    		data: "id="+data.node.id+"&parentId="+data.node.parent,
			    		type: "POST",
			    		success: function(data){
			    			if(data.code != "0000"){
			    				$("body").message({
									type: 'error',
									content: "移动失败"
								});
			    			}
			    		}
			    	});
	               
	            });

			
			dialog.find('#close').on('click',function(){
				dialog.remove();
			}).end().modal({
				keyboard: true
			});			
		});
    }
	
	var init = function() {
		$('[data-role="resourceGrid"]').grid({
            identity:"id",
            isShowIndexCol : false,
            url : config.getDomain()+"/auth/resource/get",
            columns: [{
				title : "名称",
				name : "name"
			}, {
				title : "类型",
				name : "category",
				render : function(item, name, index) {
					if(item[name] == 'MENU') {
						return "菜单资源";
					}else if(item[name] == 'URL') {
						return "URL资源";
					}else {
						return "操作资源";
					}
				} 
			}, {
				title : "状态",
				name : "status",
				render : function(item, name, index) {
					return item[name] == 1 ?  
							'<span class="glyphicon glyphicon-remove" style="color:#D9534F;margin-left:15px;"></span>'
							:'<span class="glyphicon glyphicon-ok" style="color:#5CB85C;margin-left:15px;"></span>';
				}  
			}, {
				title : "标识",
				name : "indentifier" 
			},{
				title : "描述",
				name : "description"
			}
			/*,{
				title : "操作",
				name : "id",
				render: function(item, name, index){
					return '<div class="btn-group btn-group-xs btn-group-solid">'
						+'<button class="btn btn-primary" type="button" title="编辑" onclick="Resource.modify('+item.id+')"><span class="glyphicon glyphicon-edit"></span></button>'
						+'<button class="btn red" type="button" title="撤销" onclick="Resource.deleteResource('+item.id+')"><span class="glyphicon glyphicon-remove"></span></button>'
						+'</div>';
				}
			}*/
            ]
     });
		
		//初始化按钮操作
		$(".btn-add").click(function() {
			 add();
		});
		
		$(".btn-remove").click(function() {
			
			var data = $('[data-role="resourceGrid"]').getGrid().selectedRowsIndex();
			if (data.length == 0) {
				$("body").message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}
			deleteResource(data.join(","));
		});
		$(".btn-modify").click(function() {
			var data = $('[data-role="resourceGrid"]').getGrid().selectedRowsIndex();
			if (data.length == 0) {
				$("body").message({
					type : 'warning',
					content : '请选择要修改的资源'
				});
				return;
			}
			 modify($('[data-role="resourceGrid"]').getGrid().selectedRows()[0].id);
		});
		$(".btn-search").click(function() {
			$("#queryDivId").slideToggle("slow");
		});
		$("#managerSearch").click(function() {
			var params = {};
            $("#resourceListForm").find('.form-control').each(function(){
                var $this = $(this);
                var name = $this.attr('name');
                 if(name){
                    params[name] = $this.val();
                }
            });
           $('[data-role="resourceGrid"]').getGrid().search(params);
		});
		//
		$(".btn-role-tree").click(function() {//权限树
			initRoleTree();
		});
	};
	
	return {
		init : init,
		deleteResource : deleteResource,
		modify : modify
	};
}();