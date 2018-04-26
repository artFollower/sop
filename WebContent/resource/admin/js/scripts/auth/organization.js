var Organization = function () {
	
	var saveOrUpdate = function(title,url,id,type,node) {
		var parentId = id;
		
		$.get(config.getResource()+"/pages/auth/organization/template.jsp").done(function(data){
			dialog = $(data);
			dialog.find(".modal-title").html(title);

			if(node != null && node != undefined) {
				dialog.find('input[name="name"]').val(node.text);
				dialog.find('textarea[name="description"]').val(node.original.description);
			}
			dialog.find('#save').on('click',function(){
				$(this).attr('disabled', 'disabled');
				var data = "";
				if(node != null && node != undefined) {
					data = $('.add-organization-form').serialize() +"&category="+type+"&parties.id="+node.original.parentId+"&id="+node.id;
				}else {
					data = $('.add-organization-form').serialize() +"&category="+type+"&parties.id="+parentId;
				}
				if(config.validateForm($(".add-organization-form"))) {
					$.ajax({
			    		url: config.getDomain()+url,//config.getDomain()+"/auth/user/create",
			    		data: data,
			    		type: "POST",
			    		success: function(data){
			    			if(data.code == "0000"){
			    				dialog.remove();
			    				$("#organization").message({
									type: 'success',
									content: data.msg
								});
			    				$("#organization").jstree("refresh");
			    			}else{
			    				dialog.find('#save').removeAttr('disabled');
			    				dialog.message({
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
		
			dialog.find('#close').on('click',function(){
				dialog.remove();
			});
		});
	};
	
	var initDetail = function(id) {
		$('[data-role="organizationGrid"]').grid({
            identity:"id",
            url : config.getDomain()+"/auth/organization/getRelationUser",
            columns: [{
				title : "别名",
				name : "name"
			},{
				title : "帐号",
				name : "account"
			}, {
				title : "邮箱",
				name : "email"
			},{
				title : "手机号",
				name : "phone"
			},{
				title : "操作",
				name : "id",
				render: function(item, name, index){
					return '<div class="btn-group btn-group-xs btn-group-solid">'
							+'<button class="btn red" type="button" title="解除关系" onclick="Organization.disrelationUser('+item.id+')"><span class="glyphicon glyphicon-remove"></span></button>'
							+'</div>';
				}
			}
            ]
		});
	};
	
	var reltionUser = function(id) {
			$.get(config.getResource()+"/pages/auth/organization/relationUser.jsp").done(function(data){
				unRelationUserGrid = $(data);
				
				unRelationUserGrid.find('[data-role="unRelationUserGrid"]').grid({
		            identity:"id",
		            url : config.getDomain()+"/auth/organization/getUnRelationUser?id="+id,
		            columns: [{
						title : "别名",
						name : "name"
					},{
						title : "帐号",
						name : "account"
					}, {
						title : "邮箱",
						name : "email"
					},{
						title : "手机号",
						name : "phone"
					}
		            ]
				});

				unRelationUserGrid.find('#save').on('click',function(){
					$(this).attr('disabled', 'disabled');
					
					var data = $('[data-role="unRelationUserGrid"]').getGrid().selectedRowsIndex();
					if (data.length == 0) {
						$('body').message({
							type : 'warning',
							content : '请选择要添加的用户'
						});
						return;
					}
					
					$.ajax({
			    		url: config.getDomain()+"/auth/organization/relationUser",
			    		data: "userIds="+data.join(",")+"&departmentId="+id,
			    		type: "POST",
			    		success: function(data){
			    			if(data.code == "0000"){
			    				unRelationUserGrid.remove();
			    				$("#organization").message({
									type: 'success',
									content: data.msg
								});
			    				$('[data-role="organizationGrid"]').getGrid().refresh();
			    			}else{
			    				unRelationUserGrid.find('#save').removeAttr('disabled');
			    				unRelationUserGrid.message({
									type: 'error',
									content: data.msg
								});
			    			}
			    		},
			    		fail : function (data) {
				        	$('body').message({
				                type: 'error',
				                content: data.msg
				            });
				        }
			    	});
				});
			
				unRelationUserGrid.find('#close').on('click',function(){
					unRelationUserGrid.remove();
				}).end().modal({
				keyboard: true
			});
				
			});
		};
		
	var disrelationUser = function(data) {
        $('body').confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
				 $.post(config.getDomain()+'/auth/organization/remove', "ids="+data).done(function (data) {
			            if (data.code == "0000") {
			            	$('body').message({
			                    type: 'success',
			                    content: data.msg
			                });
			            	$('[data-role="organizationGrid"]').getGrid().refresh();
			            } else {
			                $('body').message({
			                    type: 'error',
			                    content: data.msg
			                });
			            }
			        }).fail(function (data) {
			        	$('body').message({
			                type: 'error',
			                content: data.msg
			            });
			        });
			}
		});
    };
	
	var init = function() {
		$("#organization").jstree({
			"ui" : {
				"select_limit" : 1
			},
            "core" : {
                "themes" : {
                    "responsive": false
                }, 
                // so that create works
                "check_callback" : true,
                'data' : {
                    'url' : function (node) {
                      return config.getDomain()+'/auth/organization/getOrganization';
                    },
                    'data' : function (node) {
                      return { 'parent' : node.id ,'category' : node.category};
                    }
                }
            },
            contextmenu : {
            	items : function(o, cb) {
            		var item  = {};
            		if(o.original.category === "COMPANY") {
            			item["addCompany"] = {
        					"separator_before"	: false,
        					"separator_after"	: true,
        					"_disabled"			: false, //(this.check("create_node", data.reference, {}, "last")),
        					"label"				: "添加公司",
        					"action"			: function (data) {
        						var inst = $.jstree.reference(data.reference),
        							obj = inst.get_node(data.reference);
        						saveOrUpdate("添加子公司","/auth/organization/create",obj.id,"COMPANY");
        					}
        				};
            		}
            		item["addDep"] = {
    					"separator_before"	: false,
    					"separator_after"	: true,
    					"_disabled"			: false, //(this.check("create_node", data.reference, {}, "last")),
    					"label"				: "添加部门",
    					"action"			: function (data) {
    						var inst = $.jstree.reference(data.reference),
    							obj = inst.get_node(data.reference);
    						saveOrUpdate("添加部门","/auth/organization/create",obj.id,"DEPARTMENT");
    					}
    				};
            		item["edit"] = {
        					"separator_before"	: false,
        					"separator_after"	: true,
        					"_disabled"			: false, //(this.check("create_node", data.reference, {}, "last")),
        					"label"				: "修改",
        					"action"			: function (data) {
        						var inst = $.jstree.reference(data.reference),
        							obj = inst.get_node(data.reference);
        						saveOrUpdate("修改","/auth/organization/update","",o.original.category,obj);
        					}
        				};
            		item["delete"] = {
        					"separator_before"	: false,
        					"separator_after"	: true,
        					"_disabled"			: false, //(this.check("create_node", data.reference, {}, "last")),
        					"label"				: "删除",
        					"action"			: function (data) {
        						var inst = $.jstree.reference(data.reference),
        							obj = inst.get_node(data.reference);
        						$('body').confirm({
        							content : '确定要撤销所选记录吗?',
        							callBack : function() {
        								 $.post(config.getDomain()+'/auth/organization/remove', "ids="+obj.id).done(function (data) {
        							            if (data.code == "0000") {
        							            	$('body').message({
        							                    type: 'success',
        							                    content: '删除成功'
        							                });
        							            	$("#organization").jstree("refresh");
        							            } else {
        							                $('body').message({
        							                    type: 'error',
        							                    content: data.msg
        							                });
        							            }
        							        }).fail(function (data) {
        							        	$('body').message({
        							                type: 'error',
        							                content: '删除失败'
        							            });
        							        });
        							}
        						});
        					}
        				};
            		return item; 
            	}
            },
            "plugins" : [ "contextmenu", "dnd", "state", "types" ,"search"]
        });
		
		initDetail(0);
		
		$("#organization").on("changed.jstree", function (e, data) {
			$('[data-role="organizationGrid"]').getGrid().search({'id':data.selected[0]});
	    });
		$("#organization").on("move_node.jstree", function (e, data) {
			var newNode = $("#organization").jstree("get_node",data.parent);
			if(newNode.original.category == "DEPARTMENT" && data.node.original.category == "COMPANY") {
				$('body').message({
					type : 'error',
					content : '公司不能属于部门'
				});
				$("#organization").jstree("refresh");
			}else {
				$.ajax({
		    		url: config.getDomain()+"/auth/organization/update",
		    		data: "id="+data.node.id+"&parties.id="+data.parent,
		    		type: "POST",
		    		success: function(data){
		    			if(data.code == "0000"){
		    				$('body').message({
		    					type : 'success',
		    					content : '组织结构划分成功'
		    				});
		    			}
		    		}
		    	});
			}
	    });
		
		$(".reload").click(function() {
			$("#organization").jstree("refresh");
		});
		
		$(".company-add").click(function() {
			saveOrUpdate("添加公司","/auth/organization/create","","COMPANY");
		});
		$(".department-add").click(function() {
			var nodes = $("#organization").jstree("get_selected");
			if(nodes.length == 0) {
				$('body').message({
					type : 'warning',
					content : '请选择公司'
				});
				return;
			}
			//saveOrUpdate("添加部门","/auth/organization/create",nodes[0],"DEPARTMENT");
		});
		
		$(".btn-modify").click(function() {
			var nodes = $("#organization").jstree("get_selected");
			if(nodes.length == 0) {
				$('body').message({
					type : 'warning',
					content : '请选择要修改的对象'
				});
				return;
			}
			saveOrUpdate("修改","/auth/organization/update",nodes[0]);
		});
		
		$(".btn-remove").click(function() {
			var nodes = $("#organization").jstree("get_selected");
			if(nodes.length == 0) {
				$('body').message({
					type : 'warning',
					content : '请选择要删除的对象'
				});
				return;
			}
			$('body').confirm({
				content : '确定要撤销所选记录吗?',
				callBack : function() {
					 $.post(config.getDomain()+'/auth/organization/remove', "ids="+nodes[0]).done(function (data) {
				            if (data.code == "0000") {
				            	$('body').message({
				                    type: 'success',
				                    content: '删除成功'
				                });
				            	$("#organization").jstree("refresh");
				            } else {
				                $('body').message({
				                    type: 'error',
				                    content: data.msg
				                });
				            }
				        }).fail(function (data) {
				        	$('body').message({
				                type: 'error',
				                content: '删除失败'
				            });
				        });
				}
			});
		});
		
		$(".relation-user").click(function() {
			reltionUser($("#organization").jstree("get_selected")[0]);
		});
		$(".disrelation-user").click(function() {
			var data = $('[data-role="organizationGrid"]').getGrid().selectedRowsIndex();
			if (data.length == 0) {
				$('body').message({
					type : 'warning',
					content : '请选择要解除关系的用户'
				});
				return;
			}
			disrelationUser(data.join(","));
		});
	};
	
	return {
		init : init,
		disrelationUser : disrelationUser
	};
}();