var Role = function() {
	
	var add = function() {
		dataGrid = $('[data-role="roleGrid"]');
		$.get(config.getResource()+"/pages/auth/role/template.jsp").done(function(data){
			dialog = $(data);
			
			dialog.find('#close').on('click',function(){
				dialog.remove();
			});
			dialog.find('#save').on('click',function(){
				$(this).attr('disabled', 'disabled');
				if(config.validateForm($(".role-add-form"))) {
					$.ajax({
			    		url: config.getDomain()+"/auth/role/create",
			    		data: $('.role-add-form').serialize(),
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
	
	var modify = function(roleId) {
		dataGrid = $('[data-role="roleGrid"]');
		$.get(config.getResource()+"/pages/auth/role/template.jsp").done(function(data){
			dialog = $(data);
			$.ajax({
	    		url: config.getDomain()+"/auth/role/get?id="+roleId,
	    		type: "POST",
	    		success: function(data){
	    			if(data.code == "0000"){
	    				var _data = data.data[0];
	    				initFormParams(dialog.find(".role-add-form"),_data);
	    				dialog.find("select[name=status]").children("option").each(function() {
	    					if($(this).val() == _data.status) {
	    						$(this).attr("selected","true");
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
				if(config.validateForm($(".role-add-form"))) {
					$.ajax({
			    		url: config.getDomain()+"/auth/role/update?id="+roleId,
			    		data: $('.role-add-form').serialize(),
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
	
	var deleteRole = function(data) {
        dataGrid = $('[data-role="roleGrid"]');
        
        dataGrid.confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
				 $.post(config.getDomain()+'/auth/role/remove', "ids="+data).done(function (data) {
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
    
    var grantPermission = function(roleId,name) {
    	$(".caption").text("角色["+name+"]权限分配");
   	 var columns = [{
            title : "权限名称",
            name : "name",
            width : 80
        }, {
            title : "类型",
            name : "category",
            width : 80,
			render : function(item, name, index) {
				if(item[name] == 'MENU') {
					return "菜单资源";
				}else if(item[name] == 'URL') {
					return "URL资源";
				}else {
					return "操作资源";
				}
			}
        }];

        $('#notGrantPermissionToRoleGrid').grid({
            identity: 'id',
            columns: columns,
            isShowPages: false,
            url: config.getDomain()+"/auth/role/getUnGrantPermission?id="+roleId
        });

        $('#grantPermissionToRoleGrid').grid({
            identity: 'id',
            columns: columns,
            isShowPages: false,
            url: config.getDomain()+"/auth/role/getGrantPermission?id="+roleId
        });
        $('#grantPermissionToRoleButton').on('click',function(){
            var grantRolesToUserTableItems =  $('#notGrantPermissionToRoleGrid').getGrid().selectedRowsIndex();//.selectedRows();

            if(grantRolesToUserTableItems.length == 0){
            	$("body").message({
                    type: 'warning',
                    content: '请选择要需要被授权的角色'
                });
                return;
            }
            $.post(config.getDomain()+"/auth/role/grantPermission", "roleId="+roleId+"&permissionIds="+grantRolesToUserTableItems.join(",")).done(function(data) {
                if(data.code == '0000'){
                	$("body").message({
                        type: 'success',
                        content: '为用户授权角色成功'
                    });
                    $('#notGrantPermissionToRoleGrid').grid('refresh');
                    $('#grantPermissionToRoleGrid').grid('refresh');
                }else {
                	$("body").message({
                        type: 'success',
                        content: data.msg
                    });
                }
            });
        });
        $('#notGrantPermissionToRoleButton').on('click',function(){
            var notGrantRolesToUserGridItems = $('#grantPermissionToRoleGrid').getGrid().selectedRowsIndex();

            if(notGrantRolesToUserGridItems.length == 0){
            	$("body").message({
                    type: 'warning',
                    content: '请选择要需要撤销的角色'
                });
                return;
            }

            $.post(config.getDomain()+"/auth/role/recoverPermission", "ids="+notGrantRolesToUserGridItems.join(",")).done(function(data) {
                if(data.code == '0000'){
                	$("body").message({
                        type: 'success',
                        content: '撤销用户的角色成功！'
                    });
                    $('#notGrantPermissionToRoleGrid').grid('refresh');
                    $('#grantPermissionToRoleGrid').grid('refresh');
                }else {
                	$("body").message({
                        type: 'success',
                        content: data.msg
                    });
                }
            });

        });
   };
   
   var openRole = function(roleId) {
	   
	   if(roleId != undefined && roleId != null && roleId !="") {
		   $.ajax({
		   		url: config.getDomain()+"/auth/role/get?id="+roleId,
		   		type: "POST",
		   		success: function(data){
		   			if(data.code == "0000"){
		   				var _data = data.data[0];
		   				initFormParams($(".role-add-form"),_data);
		   			}
		   		}
		   });
		   
	   }
	   $(".roleResourceForm").jstree({
           "core" : {
               "themes" : {
                   "responsive": false
               }, 
               // so that create works
               "check_callback" : true,
               'data' : {
                   'url' : function (node) {
                     return config.getDomain()+"/auth/role/getGrantPermission"+((roleId != undefined && roleId != null && roleId !="")?("?id="+roleId):"");
                   },
                   'data' : function (node) {
                     return { 'parent' : node.id };
                   }
               }
           },
           'checkbox' : {
        	   'three_state' : false,
        	   'cascade' : 'undetermined' 
           },
           "plugins" : [ "checkbox", "types","search" ]
       });
	   
		$("#save").click(function() {
			if(roleId != undefined && roleId != null && roleId !="") {
				$.ajax({
			   		url: config.getDomain()+"/auth/role/update?id="+roleId,
			   		type: "POST",
			   		data : $('.role-add-form').serialize() +"&permissions="+$(".roleResourceForm").jstree("get_selected").toString(),
			   		success: function(data){
		   				$("body").message({
	                        type: data.code == "0000" ?'success':'error',
	                        content: data.msg
	                    });
			   		}
			   });
			}else{
				$.ajax({
			   		url: config.getDomain()+"/auth/role/create",
			   		type: "POST",
			   		data : $('.role-add-form').serialize() +"&permissions="+$(".roleResourceForm").jstree("get_selected").toString(),
			   		success: function(data){
		   				$("body").message({
	                        type: data.code == "0000" ?'success':'error',
	                        content: data.msg
	                    });
			   		}
			   });
			}
		});
	};
	
	var cheakUser = function(id) {
		$.get(config.getResource()+"/pages/auth/role/users.jsp").done(function(data){
			dialog = $(data);
			
			dialog.find('.modal-body').grid({
	            identity: "id",
	            isPrint : false,
	            isShowIndexCol : false,
	            url: config.getDomain() + "/auth/role/getUser?id="+id,
	            columns: [{
	                title: "别名",
	                name: "name"
	            },
	            {
	                title: "帐号",
	                name: "account"
	            },
	            {
	                title: "角色",
	                name: "roleName"
	            }]
	        });
			
			dialog.find('#close').on('click',function(){
				dialog.remove();
			}).end().modal({
				keyboard: true
			});
		});
	}
	
	var init = function() {
		$('[data-role="roleGrid"]').grid({
            identity:"id",
            isShowIndexCol : false,
            url : config.getDomain()+"/auth/role/get",
            columns: [{
				title : "名称",
				name : "name",
				render : function(item, name, index) {
					return "<a href='#role/role?id="+item.id+"'>"+item[name]+"<a/>";
				}
			}, {
				title : "类型",
				name : "type",
				render : function(item, name, index) {
					if(item[name] == 'ADMINISTRATOR') {
						return "系统管理员";
					}else if(item[name] == 'ADMIN') {
						return "管理员";
					}else if(item[name] == 'MONITOR') {
						return "监管人员";
					}else if(item[name] == 'USER') {
						return "操作人员";
					}else {
						return "普通用户";
					}
				} 
			}, {
				title : "状态",
				name : "status",
				render : function(item, name, index) {
					return item[name] == 1 ?  '<span class="glyphicon glyphicon-remove" style="color:#D9534F;margin-left:15px;"></span>':'<span class="glyphicon glyphicon-ok" style="color:#5CB85C;margin-left:15px;"></span>';
				}  
			},{
				title : "描述",
				name : "description"
			}, {
				title : "操作",
				name : "id",
				render: function(item, name, index){
					return '<div class="btn-group btn-group-xs btn-group-solid">'
							//+'<button class="btn btn-primary" type="button" title="编辑" onclick="Role.modify('+item.id+')"><span class="glyphicon glyphicon-edit"></span></button>'
							+'<button class="btn red" type="button" title="撤销" onclick="Role.deleteRole('+item.id+')"><span class="glyphicon glyphicon-remove"></span></button>'
							+'<button class="btn blue" type="button" title="查看用户" onclick="Role.cheakUser('+item.id+')"><span class="fa fa-eye"></span></button>'
							+'</div>';
				}
			}
            ]
     });
		
		//初始化按钮操作
		$(".btn-add").click(function() {
//			 add();
			window.location.href = "#role/role";
		});
		
		$(".btn-remove").click(function() {
			
			var data = $('[data-role="roleGrid"]').getGrid().selectedRowsIndex();
			if (data.length == 0) {
				$("body").message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}
			deleteRole(data.join(","));
		});
		
		$(".btn-modify").click(function() {
			var data = $('[data-role="roleGrid"]').getGrid().selectedRowsIndex();
			if (data.length == 0) {
				$("body").message({
					type : 'warning',
					content : '请选择要修改的角色'
				});
				return;
			}
			 modify($('[data-role="roleGrid"]').getGrid().selectedRows()[0].id);
		});
		
		$(".btn-grant-permission").click(function() {
			var data = $('[data-role="roleGrid"]').getGrid().selectedRows();
			if (data.length == 0) {
				$("body").message({
					type : 'warning',
					content : '请选择要授权的角色'
				});
				return;
			}
			window.location.href = "#/role/grantPermission?id="+data[0].id+"&name="+data[0].name;
		});
	};
	
	return {
		init : init,
		deleteRole : deleteRole,
		grantPermission : grantPermission,
		modify : modify,
		openRole : openRole,
		cheakUser : cheakUser
	};
}();