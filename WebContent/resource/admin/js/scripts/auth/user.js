var User = function() {

    var add = function() {
        dataGrid = $('[data-role="userGrid"]');
        $.get(config.getResource() + "/pages/auth/user/template.jsp").done(function(data) {
            dialog = $(data);
            dialog.find("input[name='password']").parents(".form-group").show();
            initCategory(dialog);
//            initClient(dialog);
            initSelect('/auth/organization/get?pagesize=0&category=JOB',dialog.find("select[name='jobId']"),null);
       	 initSelect('/clientgroup/list?pagesize=0',dialog.find("select[name='clientGroupId']"),null);
       	 
            $.ajax({
    			type : "get",
    			url : config.getDomain() + "/client/select",
    			dataType : "json",
    			
    			success : function(data) {
            $('#clientId').typeahead({
					source: function(query, process) {
						var results = _.map(data.data, function (product) {
	                        return  product.name+"["+product.code+"]";
	                    });
	                    process(results);
					},updater: function (item) {
  					var client = _.find(data.data, function (p) {
                        return p.name+"["+p.code+"]" == item;
                    });
  					if(client!=null){
  						$('#client').val(client.id);
  						
  					}
  					return item;
					},
				noselect:function(query){
					var client = _.find(data.data, function (p) {
                    return p.name+"["+p.code+"]" == query;
                });
					if(client==null){
						$('#client').val("");
					}else{
						$('#client').val(client.id);
					}
				}
				});
    			}
            });
            
            dialog.find('#save').on('click',
            function() {
                $(this).attr('disabled', 'disabled');
                if (config.validateForm(dialog.find(".add-user-form"))) {
                    config.load();
                    $.ajax({
                        url: config.getDomain() + "/auth/user/create",
                        data: dialog.find(".add-user-form").serialize(),
                        type: "POST",
                        success: function(data) {
                            config.unload();
                            dialog.find('#save').removeAttr('disabled');
                            $("body").message({
                                type: data.code == "0000" ? 'success' : 'error',
                                content: data.msg
                            });
                            if (data.code == "0000") {
                                dialog.remove();
                                dataGrid.grid('refresh');
                            }
                        },
                        fail: function(data) {
                            config.unload();
                            $("body").message({
                                type: 'error',
                                content: data.msg
                            });
                        }
                    });
                } else {
                    dialog.find('#save').removeAttr('disabled');
                }
            }).end().modal({
                keyboard: true
            });
            dialog.find('#close').on('click',
            function() {
                dialog.remove();
            });
        });

    };
    
    /**
     * 初始化用户类型选择
     */
    var initCategory = function(continer) {
    	$(continer).find("select[name='category']").change(function() {
    		$(continer).find("#clientId").attr("disabled","disabled");
    		$(continer).find("select[name='clientGroupId']").attr("disabled","disabled");
    		$(continer).find("select[name='jobId']").attr("disabled","disabled");
    		if($(this).val() == 'USER') {
    			$(continer).find("#clientId").removeAttr("disabled");
    			$(continer).find("select[name='clientGroupId']").removeAttr("disabled");
    			$(continer).find("select[name='jobId']").val("");
    		}else if('EMPLOYEE' == $(this).val()) {
    			$(continer).find("#clientId").val("");
    			$(continer).find("input[name='client']").val("");
    			
    			$(continer).find("select[name='clientGroupId']").val("");
    			$(continer).find("select[name='jobId']").removeAttr("disabled");
    		}else {
    			$(continer).find("select[name='jobId']").val("");
    			$(continer).find("#clientId").val("");
    			$(continer).find("input[name='client']").val("");
    			
    			$(continer).find("select[name='clientGroupId']").val("");
    		}
    	});
    };
    
    /**
     * 初始化加载客户跟客户组
     */
    var initClient = function(continer,callback) {
    	 initSelect('/auth/organization/get?pagesize=0&category=JOB',$(continer).find("select[name='jobId']"),callback);
    	 initSelect('/clientgroup/list?pagesize=0',$(continer).find("select[name='clientGroupId']"),callback);
    	 initSelect('/client/select?pagesize=0',$(continer).find("select[name='clientId']"),callback);
    	 
    	 
    	 
    };
    
    /**
     * 初始化下拉框公用方法
     */
    var initSelect = function(url,continer,callback) {
    	$.post(config.getDomain() + url).done(function(data) {
            if (data.code == "0000") {
           	 for (var i = 0; i < data.data.length; i++) {
                    var par = data.data[i];
                    continer.append("<option value='" + par.id + "'>" + par.name + "</option>");
                }
	           	if(callback != null) {
	           		callback();
	           	}
            }
        });
    };

    var modify = function(userId) {
        dataGrid = $('[data-role="userGrid"]');
        $.get(config.getResource() + "/pages/auth/user/template.jsp").done(function(data) {
            dialog = $(data);
            dialog.find("input[name='password']").parents(".form-group").hide();
            config.load();
            $.ajax({
                url: config.getDomain() + "/auth/user/get?id=" + userId,
                type: "POST",
                success: function(data) {
                    config.unload();
                    if (data.code == "0000") {
                        var _data = data.data[0];
                        initFormParams(dialog.find(".add-user-form"), _data);
                        dialog.find("select[name=status]").children("option").each(function() {
                            if ($(this).val() == _data.status) {
                                $(this).attr("selected", "true");
                            }
                        });
                    } else {
                        dialog.remove();
                        $("body").message({
                            type: 'error',
                            content: data.msg
                        });
                    }
                }
            });

            dialog.find('#close').on('click',
            function() {
                dialog.remove();
            });
            dialog.find('#save').on('click',
            function() {
                $(this).attr('disabled', 'disabled');
                if (config.validateForm(dialog.find(".add-user-form"))) {
                    config.load();
                    $.ajax({
                        url: config.getDomain() + "/auth/user/update?id=" + userId,
                        data: dialog.find(".add-user-form").serialize(),
                        type: "POST",
                        success: function(data) {
                            config.unload();
                            if (data.code == "0000") {
                                dialog.remove();
                                $("body").message({
                                    type: 'success',
                                    content: data.msg
                                });
                                dataGrid.grid('refresh');
                            } else {
                                dialog.find('#save').removeAttr('disabled');
                                $("body").message({
                                    type: 'error',
                                    content: data.msg
                                });
                            }
                        }
                    });
                } else {
                    dialog.find('#save').removeAttr('disabled');
                }
            }).end().modal({
                keyboard: true
            });
        });

    };

    var addInit = function() {
        $('#save').on('click',
        function() {
            $(this).attr('disabled', 'disabled');
            if (config.validateForm($(".form-horizontal"))) {
                config.load();
                $.ajax({
                    url: config.getDomain() + "/auth/user/create",
                    data: $('.form-horizontal').serialize(),
                    type: "POST",
                    success: function(data) {
                        config.unload();
                        if (data.code == "0000") {
                            window.location.href = "#user/list";
                            $("body").message({
                                type: 'success',
                                content: data.msg
                            });
                            $('[data-role="userGrid"]').getGrid().refresh();
                        } else {
                            $('#save').removeAttr('disabled');
                            $("body").message({
                                type: 'error',
                                content: data.msg
                            });
                        }
                    }
                });
            } else {
                $('#save').removeAttr('disabled');
            }
        });
    };

    var deleteUser = function(data) {
        dataGrid = $('[data-role="userGrid"]');

        dataGrid.confirm({
            content: '确定要撤销所选记录吗?',
            callBack: function() {
                config.load();
                $.post(config.getDomain() + '/auth/user/remove', "ids=" + data).done(function(data) {
                    config.unload();
                    if (data.code == "0000") {
                        $("body").message({
                            type: 'success',
                            content: '删除成功'
                        });
                        dataGrid.grid('refresh');
                    } else {
                        $('body').message({
                            type: 'error',
                            content: data.msg
                        });
                    }
                }).fail(function(data) {
                    config.unload();
                    $("body").message({
                        type: 'error',
                        content: '删除失败'
                    });
                });
            }
        });

    };

    var grantRole = function(userId, name) {
        $(".caption").text("用户[" + name + "]角色分配");
        var columns = [{
            title: "角色名称",
            name: "name",
            width: 80
        },
        {
            title: "角色描述",
            name: "description",
            width: 100
        }];

        $('#notGrantAuthoritiesToUserGrid').grid({
            identity: 'id',
            columns: columns,
            isShowPages: false,
            url: config.getDomain() + "/auth/user/getUnGrantRole?id=" + userId
        });

        $('#grantAuthoritiesToUserGrid').grid({
            identity: 'id',
            columns: columns,
            isShowPages: false,
            url: config.getDomain() + "/auth/user/getGrantRole?id=" + userId
        });
        $('#grantAuthorityToUserButton').on('click',
        function() {
            var grantRolesToUserTableItems = $('#notGrantAuthoritiesToUserGrid').getGrid().selectedRowsIndex(); // .selectedRows();
            if (grantRolesToUserTableItems.length == 0) {
                $('#notGrantAuthoritiesToUserGrid').message({
                    type: 'warning',
                    content: '请选择要需要被授权的角色'
                });
                return;
            }
            config.load();
            $.post(config.getDomain() + "/auth/user/grantRole", "userId=" + userId + "&roleIds=" + grantRolesToUserTableItems.join(",")).done(function(data) {
                config.unload();
                if (data.code == '0000') {
                    $("body").message({
                        type: 'success',
                        content: '为用户授权角色成功'
                    });
                    $('#notGrantAuthoritiesToUserGrid').grid('refresh');
                    $('#grantAuthoritiesToUserGrid').grid('refresh');
                } else {
                    $("body").message({
                        type: 'success',
                        content: data.msg
                    });
                }
            });
        });
        $('#notGrantAuthorityToUserButton').on('click',
        function() {
            var notGrantRolesToUserGridItems = $('#grantAuthoritiesToUserGrid').getGrid().selectedRowsIndex();

            if (notGrantRolesToUserGridItems.length == 0) {
                $("body").message({
                    type: 'warning',
                    content: '请选择要需要撤销的角色'
                });
                return;
            }

            config.load();
            $.post(config.getDomain() + "/auth/user/recoverRole", "ids=" + notGrantRolesToUserGridItems.join(",")).done(function(data) {
                config.unload();
                if (data.code == '0000') {
                    $("body").message({
                        type: 'success',
                        content: '撤销用户的角色成功！'
                    });
                    $('#notGrantAuthoritiesToUserGrid').grid('refresh');
                    $('#grantAuthoritiesToUserGrid').grid('refresh');
                } else {
                    $("body").message({
                        type: 'success',
                        content: data.msg
                    });
                }
            });

        });
    };

    var init = function() {
        $('[data-role="userGrid"]').grid({
            identity: "id",
            isPrint : false,
            isShowIndexCol : false,
            url: config.getDomain() + "/auth/user/get",
            columns: [{
                title: "别名",
                name: "name",
                render: function(item, name, index) {
                    return "<a href='#/user/acount?id=" + item.id + "'>" + item[name] + "</a>";
                }
            },
            {
                title: "帐号",
                name: "account"
            },
            {
                title: "邮箱",
                name: "email"
            },
            {
                title: "手机号",
                name: "phone"
            },
            {
                title: "状态",
                name: "status",
                unPrint : true,
                render: function(item, name, index) {
                    return item[name] == 1 ? '<span class="glyphicon glyphicon-remove" style="color:#D9534F;margin-left:15px;"></span>': '<span class="glyphicon glyphicon-ok" style="color:#5CB85C;margin-left:15px;"></span>';
                }
            },
            {
                title: "用户描述",
                name: "description"
            },
            {
                title: "操作",
                name: "id",
                unPrint : true,
                render: function(item, name, index) {
                	var re='<div class="btn-group btn-group-xs btn-group-solid">';
                	if(config.hasPermission('userUpdate')){
                		
                		re+='<button class="btn blue" type="button" title="分配角色" onclick="window.location.href = \'#user/grantRole?id=' + item.id + '&name=' + item.name + '\'"><span class="glyphicon glyphicon-th-large"></span></button>';
                	}
                	if(config.hasPermission('userDelete')){
                		
                		re+='<button class="btn red" type="button" title="撤销" onclick="User.deleteUser(' + item.id + ')"><span class="glyphicon glyphicon-remove"></span></button>' ;
                	}
                     
                     re+='</div>';
                     return re;
                }
            }]
        });

        // 初始化按钮操作
        $(".btn-add").click(function() {
            add();
            // window.location.href = "#user/add";
        });

        $(".btn-modify").click(function() {
            var data = $('[data-role="userGrid"]').getGrid().selectedRowsIndex();
            if (data.length == 0) {
                $("body").message({
                    type: 'warning',
                    content: '请选择要修改的用户'
                });
                return;
            }
            modify($('[data-role="userGrid"]').getGrid().selectedRows()[0].id);
        });

        $(".btn-remove").click(function() {
            var data = $('[data-role="userGrid"]').getGrid().selectedRowsIndex();
            if (data.length == 0) {
                $("body").message({
                    type: 'warning',
                    content: '请选择要撤销的记录'
                });
                return;
            }
            deleteUser(data.join(","));
        });
        $(".btn-search").click(function() {
            $("#queryUserDivId").slideToggle("slow");
        });
        $("#managerSearch").click(function() {
            var key = $("#queryUserDivId").find('.search-key').val();
            var params = {
                'name': key,
                'account': key,
                'email': key,
                'phone': key
            };
            $('[data-role="userGrid"]').getGrid().search(params);
        });

        $(".btn-grant-role").click(function() {
            var data = $('[data-role="userGrid"]').getGrid().selectedRows();
            if (data.length == 0) {
                $("body").message({
                    type: 'warning',
                    content: '请选择要分配角色的用户'
                });
                return;
            }
            window.location.href = "#user/grantRole?id=" + data[0].id + "&name=" + data[0].name;
        });
        // -----------------------
    };

    var initAccount = function(id) {
        if (id != $("body").attr("ng-data") && !config.hasPermission("userUpdate")) {
            id = $("body").attr("ng-data");
        }
        $.post(config.getDomain() + '/auth/user/get?id=' + id).done(function(data) {
            if (data.code == "0000") {
                var _data = data.data[0];
                $(".profile-userpic").children("img").attr("src", config.isNull(_data.photo) ? config.getDomain() + "/resource/admin/pages/media/profile/profile_user.jpg": _data.photo);
                $(".profile-usertitle-name").html(_data.name);
                try{
	                for (var i = 0; i < _data.roles.length; i++) {//列出用户所属角色
	                    $(".profile-usertitle-role").append("<button class='btn btn-circle green-haze btn-sm mar-10' type='button' data='" + _data.roles[i].id + "'>" + _data.roles[i].name + "</button>");
	                }
                }catch(err) {
                	
                }
                
                $(".profile-usertitle-role").find("button").click(function() {//点击查看角色信息
                	window.location.href = "#/role/role?id="+$(this).attr("data");
                });
                $.post(config.getDomain() + '/auth/organization/get?pagesize=0').done(function(data) {
                    if (data.code == "0000") {
                        for (var i = 0; i < data.data.length; i++) {
                            var par = data.data[i];
                            if (par.category === "COMPANY") {
                                $(".userInfoForm").find("select[name='company']").append("<option value='" + par.id + "'>" + par.name + "</option>");
                            } else {
                                $(".userInfoForm").find("select[name='parties.id']").append("<option value='" + par.id + "'>" + par.name + "</option>");
                            }
                        }
                        if (_data.deps != null && _data.deps.length > 0) {
                            $(".profile-usertitle-job").html(_data.deps[0].name);
                            $(".userInfoForm").find("select[name='parties.id']").val(_data.deps[0].id);
                            for (var i = 0; i < data.data.length; i++) {
                                if (_data.deps[0].id == data.data[i].id) {
                                    $(".userInfoForm").find("select[name='company']").val(data.data[i].parentId);
                                }
                            }
                        }
                    }
                });
                initCategory($(".userBaseInfoForm"));
                initClient($(".userBaseInfoForm"),function() {
                	initFormParams($(".tab-content"), _data);
                	$("select[name='clientGroupId'],select[name='clientId'],select[name='jobId']").each(function() {
                		if($(this).val() != "") {
                			$(this).removeAttr("disabled");	
                		}
                	});
                });
            }
            $("input[name='password']").val("");

        });
        $(".userInfoForm").find("input[name='type']").change(function() {
            if ($(this).val() == "0") {
                $(".userInfoForm").find(".client-group").removeClass("hidden");
            } else {
                $(".userInfoForm").find(".client-group").addClass("hidden");
                $(".userInfoForm").find("select[name='client.id']").val("");
                $(".userInfoForm").find("select[name='clientGroup.id']").val("");
            }
        });
        $(".formUserId").val(id);
        $(".userInfoForm").find("select[name='company']").change(function() {
            $.post(config.getDomain() + '/auth/organization/get?pagesize=0&parties.id=' + $(this).val()).done(function(data) {
                if (data.code == "0000") {
                    $(".userInfoForm").find("select[name='parties.id']").children().remove();
                    for (var i = 0; i < data.data.length; i++) {
                        var par = data.data[i];
                        if (par.category === "DEPARTMENT") {
                            $(".userInfoForm").find("select[name='parties.id']").append("<option value='" + par.id + "'>" + par.name + "</option>");
                        }
                    }
                }
            });
        });
    };
    

    var changeUserPassword = function() {
        if (config.validateForm($(".userPswdForm"))) {
            if ($(".userPswdForm").find("input[name='password']").val() != $(".userPswdForm").find("input[name='repswd']").val()) {
                $("body").message({
                    type: 'error',
                    content: "与新密码不一致"
                });
                config.showErrorMessage($(".userPswdForm").find("input[name='repswd']"));
                return;
            }
            config.load();
            $.ajax({
                url: config.getDomain() + "/auth/user/changeUserPaswd",
                data: $('.userPswdForm').serialize(),
                type: "POST",
                success: function(data) {
                    config.unload();
                    $("body").message({
                        type: data.code == "0000" ? 'success': 'error',
                        content: data.msg
                    });
                }
            });
        }
    };
    var modifyBaseInfo = function() {
        if (config.validateForm($(".userBaseInfoForm"))) {
            config.load();
            $.ajax({
                url: config.getDomain() + "/auth/user/update",
                data: $('.userBaseInfoForm').serialize(),
                type: "POST",
                success: function(data) {
                    config.unload();
                    $("body").message({
                        type: data.code == "0000" ? 'success': 'error',
                        content: data.msg
                    });
                }
            });
        }
    };
    var modifyInfo = function() {
        if (config.validateForm($(".userInfoForm"))) {
            if ($(".userInfoForm").find("input[name='type']") == "0" && $(".userInfoForm").find("select[name='client.id']") == "") {
                $("body").message({
                    type: 'warning',
                    content: '客户不能为空'
                });
                config.showErrorMessage($(".userInfoForm").find("select[name='client.id']"));
                return;
            }
            config.load();
            $.ajax({
                url: config.getDomain() + "/auth/organization/updateEmployee",
                data: $('.userInfoForm').serialize(),
                type: "POST",
                success: function(data) {
                    config.unload();
                    $("body").message({
                        type: data.code == "0000" ? 'success': 'error',
                        content: data.msg
                    });
                }
            });
        }
    };
    var uploadPhoto = function() {
    	var userid = $(".formUserId").val();
    	var photo = $(".fileinput-preview").children("img").attr("src");
    	if(!config.isNull(userid) && !config.isNull(photo)) {
    		 config.load();
             $.ajax({
                 url: config.getDomain() + "/auth/user/uploadPhoto",
                 data: "id="+userid+"&photo="+photo,
                 type: "POST",
                 success: function(data) {
                     config.unload();
                     $("body").message({
                         type: data.code == "0000" ? 'success': 'error',
                         content: data.msg
                     });
                 }
             });
    	}else {
    		 $("body").message({
                 type: 'error',
                 content: '请选择用户头像'
             });
    	}
    	
    };
    var changeMyPaswd = function(id) {
        $.get(config.getResource() + "/pages/auth/user/changePassword.jsp").done(function(data) {
            dialog = $(data);
            dialog.find('#save').on('click',
            function() {
                $(this).attr('disabled', 'disabled');
                if (config.validateForm($(".change-passwd-form"))) {
                    if ($(".change-passwd-form").find("input[name='password']").val() != $(".change-passwd-form").find("input[name='repswd']").val()) {
                        $("body").message({
                            type: 'error',
                            content: "与新密码不一致"
                        });
                        config.showErrorMessage($(".change-passwd-form").find("input[name='repswd']"));
                        return;
                    }
                    config.load();
                    $.ajax({
                        url: config.getDomain() + "/auth/user/changePaswd?id=" + id,
                        data: $(".change-passwd-form").serialize(),
                        type: "POST",
                        success: function(data) {
                            config.unload();
                            dialog.remove();
                            $("body").message({
                                type: data.code == "0000" ? 'success': 'error',
                                content: data.msg
                            });
                        }
                    });
                } else {
                    dialog.find('#save').removeAttr('disabled');
                }
            }).end().modal({
                keyboard: true
            });
            dialog.find('#close').on('click',
            function() {
                dialog.remove();
            });
        });
    };
    /**
     * 退出
     */
    var logout = function(logoutUrl,loginUrl) {
    	config.load();
    	$.ajax({
            url: config.getDomain() + "/logout",
            success: function(data) {
            	config.unload();
            	$(".logout-frame").attr("src",logoutUrl);
            	$(".logout-frame").load(function() {
            		window.location.href = loginUrl;
            	});
            }
        });
    };
    return {
        init: init,
        deleteUser: deleteUser,
        grantRole: grantRole,
        addInit: addInit,
        modify: modify,
        modifyBaseInfo: modifyBaseInfo,
        uploadPhoto: uploadPhoto,
        changeUserPassword: changeUserPassword,
        initAccount: initAccount,
        changeMyPaswd: changeMyPaswd,
        modifyInfo: modifyInfo,
        logout : logout
    };
} ();