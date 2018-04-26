//clientQualification  客户资质 //dialogClientQualification
//berth  泊位//dialogBerth
//tube   管线//dialogTube
//park  车位//dialogPark
//port  港口//dialogPort
//tank   储罐//dialogTank
//dialogCleanLog 清洗记录//
var BaseInfo = function() {
    var prevLetter='';
	var currentPage = 1;
	var dialog ;
	var fileGrid;
	var excelParams={};
	var u;
	//导出通知单
	function exportExcel(){
		var url = config.getDomain()+"/baseController/exportExcel?type="+currentPage;
		window.open(url) ;
	}
	
	
	//客户组里添加客户
	function openClientAdd(olddialog,groupId){
		$.get(config.getResource()+"/pages/auth/baseinfo/client/clientAdd.jsp").done(function(data){
			dialog = $(data) ;
			initClientAdd(dialog,olddialog,groupId);
			dialog.modal({
				keyboard: true
			});
		});
	}
	
	function addTruck(){
			$.get(config.getResource()+"/pages/auth/baseinfo/truck/addTruct.jsp").done(function(data){
				dialog = $(data) ;
				dialog.find("#save").click(function(){
					if(!config.validateForm(dialog.find(".form-body"))){
						return false ;
					}
					var truckCode = dialog.find("#code").val();
					if(truckCode.length < 6)
					{
						$("body").message({
							type : 'error',     
							content : '请输入长度大于等于6且由数字字母组成的车牌号！'
						});
						
						return;
					}
					$.ajax({
						type : "post",
						url : config.getDomain()+"/truck/save",
						dataType : "json",
						async:false,
						data:{
							"code":dialog.find("#code").val(),
							"name":dialog.find("#name").val() ,
							"loadCapacity":dialog.find("#loadCapacity").val() ,
							"company":dialog.find("#company").val() ,
							"maxLoadCapacity":util.isNull(dialog.find("#maxLoadCapacity").val())==''?null:util.isNull(dialog.find("#maxLoadCapacity").val(),1),
							"description":dialog.find("#description").val()
						},
						success : function(data) {
							if(data.code=="0000"){
								$("body").message({
									type : 'success',     
									content : '添加车辆成功'
								});
								if($('[data-role="baseInfoGrid"]').length>0){
									$('[data-role="baseInfoGrid"]').getGrid().refresh();
								}
								dialog.remove();
								
							}else{
								$("body").message({
									type : 'error',     
									content : '添加失败，存在同名冲突，请重新尝试。'
								});
							}
						}
					});
				});
				dialog.modal({
					keyboard: true
				});
			});
	}
	
	function initClientAdd(dialog,olddialog,groupId){
		var groupName="";
		//货主
		$.ajax({
			async:false,
  			type : "get",
  			url : config.getDomain()+"/client/select",
  			dataType : "json",
  			success : function(data) {
  				config.unload();
					dialog.find(".dClientId").typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product.name;
  	                    });
  	                    process(results);
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.name == query;
                    });
  					if(client==null){
  						dialog.find(".dClientId").removeAttr("data");
  						dialog.find(".dClientId").val("");
  					}else{
  						dialog.find(".dClientId").attr("data",client.id);
  						groupName=client.clientGroupName;
  					}
  				}
  				});
  			}
  		});
		
		
		
		dialog.find(".button-ok").click(function() {
			if(config.validateForm(dialog.find(".clientform"))){
			if(groupName!=null&&groupName!=""){
				$(this).confirm({
					content : "目前该客户在【"+groupName+"】组内，确定要更改么？",
					concel:false,
					callBack : function() {
						config.load();
						$.ajax({
							async:false,
				  			type : "post",
				  			url : config.getDomain()+"/client/update",
				  			data:{
				  				"id":dialog.find(".dClientId").attr("data"),
								"clientGroupId":groupId
				  			},
				  			dataType : "json",
				  			success : function(data) {
								config.unload();
								if (data.code == "0000") {
									$("body").message({
					                    type: 'success',
					                    content: '操作成功'
					                });
									dialog.remove();
									
									olddialog.find('[data-role="clientGrid"]').grid('refresh');
								} else {
									$("body").message({
					                    type: 'error',
					                    content: '操作失败'
					                });
								}
							
				  			}
						});
					}
				});
			}else{
				config.load();
				$.ajax({
					async:false,
		  			type : "post",
		  			url : config.getDomain()+"/client/update",
		  			data:{
		  				"id":dialog.find(".dClientId").attr("data"),
						"clientGroupId":groupId
		  			},
		  			dataType : "json",
		  			success : function(data) {
						config.unload();
						if (data.code == "0000") {
							$("body").message({
			                    type: 'success',
			                    content: '操作成功'
			                });
							dialog.remove()
							olddialog.find('[data-role="clientGrid"]').grid('refresh');
						} else {
							$("body").message({
			                    type: 'error',
			                    content: '操作失败'
			                });
						}
					
		  			}
				});
			}
			}
		});
		
	}
	
	//查看客户组里的客户
	function openClientInGroup(groupId){
		$.get(config.getResource()+"/pages/auth/baseinfo/client/clientList.jsp").done(function(data){
			dialog = $(data) ;
			initClientInGroup(dialog,groupId);
			dialog.modal({
				keyboard: true
			});
		});
	}
	function initClientInGroup(dialog,groupId){
		columns = [ {
			title : "客户名称",
			name : "name"
		}];
		dialog.find('[data-role="clientGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain() + "/client/list?clientGroupId="+groupId
		});
		
		
		dialog.find(".btn-add-client").click(function() {
			openClientAdd(dialog,groupId);
			
		});
		
		dialog.find(".btn-remove-client").click(function() {
			var data = dialog.find('[data-role="clientGrid"]').getGrid().selectedRowsIndex();
			var $this = dialog.find('[data-role="clientGrid"]');
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}else{
				var ids="";
				$.each(dialog.find('[data-role="clientGrid"]').getGrid().selectedRows(), function (i, role) {
		        	ids+=role.id+",";
		        	ids = ids.substring(0, ids.length - 1);
		        });
				
				
				$.ajax({
					type : "post",
					url : config.getDomain()+"/client/removeGroup",
					dataType : "json",
					data:{
						"clientId":ids,
					},
					success : function(data) {
						if (data.code == "0000") {
							$("body").message({
								type: 'success',
								content: '删除成功'
							});
							dialog.find('[data-role="clientGrid"]').grid('refresh');
						} else {
							$("body").message({
								type: 'error',
								content: '删除失败'
							});
						}
					}
				});
			}
			
		});
		
		
	}
	//客户组列表
	function initClientGroup() {
		columns = [ {
			title : "客户组名称",
			name : "name",
			render: function(item, name, index){
//				if(config.hasPermission('AUPDATEBASECLIENTGROUP')){
				return "<a href='javascript:void(0)' onClick='BaseInfo.openClientGroupDialog("+index+")'>"+item.name+"</a>";
//				}else{
//					return item.name;
//				}
		},
		}, {
			title : "描述",
			name : "description"
		},
		{
			title : "最后编辑人",
			name : "editUserName",
		},
		{
			title : "编辑时间",
			name : "editTime",
			render: function(item, name, index){
				if(item.editTime){
					
					return item.editTime.substring(0,item.editTime.length-2);
				}else{
					return "";
				}
			}
		},
		{
			title : "操作",
			name : "id",
			width : 80,
			render: function(item, name, index){
				if(config.hasPermission('AUPDATEBASECLIENTGROUP')){
					return "<a href='javascript:void(0)' onClick='BaseInfo.openClientInGroup("+item.id+")'  class='btn btn-xs blue'><span class=' fa fa-exchange ' title='查看客户'></span></a>";
			}
			}
		}
		];
		
		// 清空grid缓存
		if ($('[data-role="baseInfoGrid"]').getGrid() != null) {
			$('[data-role="baseInfoGrid"]').getGrid().destory();
		}
		$('[data-role="baseInfoGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain() + "/clientgroup/list"
		});
		
		$(".btn-add").unbind('click'); 
		
		
		
		if(!config.hasPermission('AADDBASECLIENTGROUP')){
			$(".btn-add").hide();
		}else{
			$(".btn-add").show();
		}
		
		$(".btn-add").click(function(){
			openClientGroupDialog(null);
		});
		$("#managerSearch").unbind('click'); 
		$("#managerSearch").click(function() {
			var key = $("#queryUserDivId").find('.search-key').val();
			var params = {
				'name':	key
			};
			excelParams=params;
           $('[data-role="baseInfoGrid"]').getGrid().search(params);
		});
		$(".modify").unbind('click'); 
		if(!config.hasPermission('AUPDATEBASECLIENTGROUP')){
			$(".modify").hide();
		}else{
			$(".modify").show();
		}
		$(".modify").click(function() {
			var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要修改的记录'
				});
				return;
			}
			dataGrid = $(this);
			openClientGroupDialog($('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0]);
		});
		
		$(".btn-remove").unbind('click'); 
		if(!config.hasPermission('ADELETEBASECLIENTGROUP')){
			$(".btn-remove").hide();
		}else{
			$(".btn-remove").show();
		}
		$(".btn-remove").click(function() {
			var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
			var $this = $('[data-role="baseInfoGrid"]');
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}
				 $this.confirm({
					 content : '确定要撤销所选记录吗?该操作会解除该客户组下所有客户的组别。',
					 callBack : function() {
						 deleteClientGroup($('[data-role="baseInfoGrid"]').getGrid().selectedRows(), $this);
					 }
				 });
		});
	}
	
	
	//删除客户组
	var deleteClientGroup = function(clients, grid) {
        dataGrid = grid;

        var data = "";
        $.each(clients, function (i, role) {
            data += ("ids=" + role.id + "&");
        });
        data = data.substring(0, data.length - 1);
        
        $.post(config.getDomain()+'/clientgroup/delete', data).done(function (data) {
            if (data.code == "0000") {
                dataGrid.message({
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
        }).fail(function (data) {
            dataGrid.message({
                type: 'error',
                content: '删除失败'
            });
        });
    };
	//客户组编辑dialog
	function openClientGroupDialog(editData){
		var obj ;
		if(typeof editData == 'number'){
			obj = $('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(editData) ;
		}else{
			obj = editData ;
		}
		
		$.get(config.getResource()+"/pages/auth/baseinfo/client/addclientgroup.jsp").done(function(data){
			dialog = $(data) ;
			dialog.modal({
				keyboard: true
			});
			if(obj==null){
				if(!config.hasPermission('AADDBASECLIENTGROUP')){
					
					dialog.find("#addClientGroup").hide();
				}
				dialog.find("#addClientGroup").click(function() {
					var isOk = false;
					if(config.validateForm(dialog.find(".form-clientgroup"))){
						isOk=true;
					}
					if(isOk) {
						$.ajax({
							type : "post",
							url : config.getDomain()+"/clientgroup/save",
							data : {
								"name" : dialog.find(".name").val(),
								"description" : dialog.find(".description").val()
							},
							dataType : "json",
							success : function(data) {
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '客户添加成功'
									});
									$('[data-role="baseInfoGrid"]').grid('refresh');
									
									dialog.remove();
								}else {
									$("body").message({
										type: 'error',
										content: '客户添加失败，可能存在同名冲突，请重新尝试。'
									});
									dialog.remove();
								}
							}
						});
					}
				});
			}else{
				dialog.find(".name").val(obj.name);
				
				
				dialog.find(".description").val(obj.description);
				
				if(!config.hasPermission('AUPDATEBASECLIENTGROUP')){
					
					dialog.find("#addClientGroup").hide();
				}
				
				dialog.find("#addClientGroup").click(function() {
					var isOk = false;
					if(config.validateForm(dialog.find(".form-clientgroup"))){
						isOk=true;
					}
					if(isOk) {
						$.ajax({
							type : "post",
							url : config.getDomain()+"/clientgroup/update",
							data : {
								"id" : obj.id,
								"name" : dialog.find(".name").val(),
								"description" : dialog.find(".description").val()
							},
							// data:JSON.stringify(sendGroup),
							dataType : "json",
							success : function(data) {
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '客户组修改成功'
									});
//									$('[data-role="baseInfoGrid"]').grid('refresh');
									if($('[data-role="baseInfoGrid"]').getGrid()!=undefined){
//										console.log(1);
										util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:obj.id,url:'/clientgroup/list'});
									}
									
									dialog.remove();
								}else {
									$("body").message({
										type: 'error',
										content: '客户组修改失败'
									});
									dialog.remove();
								}
							}
						});
					}
				});
			}
		});
	}
	
	
	//客户列表
	function initClient() {
		columns = [ {
			title : "客户编码",
			name : "code"
		},{
			title : "客户名称",
			name : "name",
			render: function(item, name, index){
//				if(config.hasPermission('AUPDATEBASECLIENT')){
				return "<a href='javascript:void(0)' onClick='BaseInfo.openClientDialog("+index+")'>"+item.name+"</a>";
//				}else{
//					return item.name
//				}
				},
		}, {
			title : "联系人",
			name : "contactName"
		}, {
			title : "联系电话",
			name : "contactPhone"
		}, {
			title : "联系地址",
			name : "address"
		}, 
		 {
			title : "品名",
			name : "postcode"
		}, {
			title : "提货放货指令",
			name : "bankAccount"
		}, {
			title : "货权转移放货指令",
			name : "bankName"
		},{
			title : "最后编辑人",
			name : "editUserName",
		},
		{
			title : "编辑时间",
			name : "editTime",
			render: function(item, name, index){
				if(item.editTime){
					
					return item.editTime.substring(0,item.editTime.length-2);
				}else{
					return "";
				}
			}
		},{
			title : "操作",
			name : "id",
			width: 120,
			render: function(item, name, index){
				var re="";
				if(config.hasPermission('ABASECLIENTFILE')){
					re+="<a href='javascript:void(0)' onclick='BaseInfo.openClientFileDialog("+index+")' class='btn btn-xs blue'><span class='fa fa-arrow-up   ' title='上传'></span></a>";
					
				}
				return re;
			}
		}
		];
		
		// 清空grid缓存
		if ($('[data-role="baseInfoGrid"]').getGrid() != null) {
			$('[data-role="baseInfoGrid"]').getGrid().destory();
		}
		$('[data-role="baseInfoGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain() + "/client/list"
		});
		
		
		
		$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
		 $('.all').addClass('ln-selected');
		 prevLetter='all';
		 $('a','.ln-letters').unbind('click'); 
		$('a','.ln-letters').click(function(){
			  var $this = $(this),
             letter = $this.attr('class').split(' ')[0];
			  
			  if ( prevLetter !== letter ) {
				  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
				  $this.addClass('ln-selected');
				  prevLetter=letter;
			  }
			  console.log(letter);

				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
						'code':	key,
						'name':	key,
						'contactName' : key,
						'contactPhone': key,
					'letter':letter
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			
			  
		});
		
		
		$(".btn-add").unbind('click'); 
		
		if(!config.hasPermission('AADDBASECLIENT')){
			$(".btn-add").hide();
		}else{
			$(".btn-add").show();
		}
		if(!config.hasPermission('ADELETEBASECLIENT')){
			$(".btn-remove").hide();
		}else{
			$(".btn-remove").show();
		}
		$(".btn-add").click(function(){
			openClientDialog(null);
		});
		
		$(".btn-file").unbind('click');
		$(".btn-file").click(function() {
			var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要修改的记录'
				});
				return;
			}
			dataGrid = $(this);
			openClientFileDialog($('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0]);
			
		});
		
		
		$(".modify").unbind('click'); 
		if(!config.hasPermission('AUPDATEBASECLIENT')){
			$(".modify").hide();
		}else{
			$(".modify").show();
		}
		$(".modify").click(function() {
			var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要修改的记录'
				});
				return;
			}
			dataGrid = $(this);
			openClientDialog($('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0]);
		});
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$("#managerSearch").unbind('click'); 
		$("#managerSearch").click(function() {
			var key = $("#queryUserDivId").find('.search-key').val();
			var params = {
				'code':	key,
				'name':	key,
				'contactName' : key,
				'contactPhone': key
			};
			excelParams=params;
           $('[data-role="baseInfoGrid"]').getGrid().search(params);
		});
		$(".btn-remove").unbind('click'); 
		$(".btn-remove").click(function() {
			var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
			var $this = $('[data-role="baseInfoGrid"]');
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}
				 $this.confirm({
					 content : '确定要撤销所选记录吗?',
					 callBack : function() {
						 deleteClient($('[data-role="baseInfoGrid"]').getGrid().selectedRows(), $this);
					 }
				 });
		});
	}
	
	
	//删除客户
	var deleteClient = function(clients, grid) {
        dataGrid = grid;

        var data = "";
        $.each(clients, function (i, role) {
            data += ("clientIds=" + role.id + "&");
        });
        data = data.substring(0, data.length - 1);
        
        $.post(config.getDomain()+'/client/delete', data).done(function (data) {
            if (data.code == "0000") {
                dataGrid.message({
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
        }).fail(function (data) {
            dataGrid.message({
                type: 'error',
                content: '删除失败'
            });
        });
    };
    
    function deleteFile(id,url){
    	 var data = "id="+id+"&url='"+u+"'";
    	$.post(config.getDomain()+'/client/deleteFile', data).done(function (data) {
            if (data.code == "0000") {
                fileGrid.message({
                    type: 'success',
                    content: '删除成功'
                });
                fileGrid.grid('refresh');
            } else {
                $('body').message({
                    type: 'error',
                    content: data.msg
                });
            }
        }).fail(function (data) {
        	fileGrid.message({
                type: 'error',
                content: '删除失败'
            });
        });
    }
    
function openClientFileDialog(obj,callback){
	var editData ;
	if(typeof obj == 'number'){
		editData = $('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(obj) ;
	}else{
		editData = obj ;
	}
			$.get(config.getResource() + "/pages/auth/baseinfo/client/clientFile.jsp").done(function(data) {
				dialog = $(data);
				
				dialog.find("btn-add").unbind('click'); 
				dialog.find(".btn-add").click(function() {
					
					openFileModal(editData.id);
					
				});
				var columns = [
								{
									title : "文件名",
									name : "name"
								},
								{
									title : "操作",
									name : "clientName",
									render : function(item, name, index) {
										var re="";
										
										u=getRootPath()+item.url;
										
										
										re+="<a href='"+getRootPath()+item.url+"' onclick='' class='btn btn-xs blue'><span class='fa fa-arrow-down   ' title='查看文件'></span></a>";
										if(config.hasPermission('ABASECLIENTFILEEDIT')){
											re+= "<a href='javascript:void(0)' onclick='BaseInfo.deleteFile("+item.id+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a>";
										}
										return re;
									}
								}];
				
				
				
				dialog.find('[data-role="clientFileGrid"]').grid({
					identity : 'id',
					columns : columns,
					isShowIndexCol : false,
					isShowPages : false,
					url : config.getDomain() + "/client/getClientFile?clientId="+editData.id
				});
				fileGrid =dialog.find('[data-role="clientFileGrid"]');
				dialog.modal({
					keyboard : true
				});
			});
		
	}
//上传文件
function openFileModal(id){
	$.get(config.getResource()+"/pages/auth/baseinfo/client/fileAdd.jsp").done(function(data){
		dialog = $(data) ;
			initFileModal(dialog,id);
		dialog.modal({
			keyboard: true
		});
	});
}

function initFileModal(dialog,id){
	dialog.find('.addFile').click(function(){
		dialog.find('input[id=lefile]').click();
	});
	dialog.find('input[id=lefile]').change(function() {  
		dialog.find('#photoCover').val($(this).val());  
		});  
	dialog.find("#id").val(id);
		dialog.find(".button-ok").click(function(){
		// 使用 jQuery异步提交表单
			
//			dialog.find('#fileForm').submit();
//			dialog.find('#fileForm').ajaxSubmit(function(data){
//				});
			dialog.find('#fileForm').ajaxSubmit({  
				success:function(data){
					if(data.code=="0000"){
						$("body").message({
							type : 'success',
							content : '上传成功'
						});
						dialog.remove();
						fileGrid.grid('refresh');
//						$('[data-role="contractGrid"]').grid('refresh');
					}else{
						$("body").message({
							type : 'error',
							content : '上传失败,文件名不能重复'
						});
						dialog.remove();
				}
				     
				}
				});   
		});
	
}
    
	//客户编辑dialog
	function openClientDialog(obj,callback){
		
		
		var editData ;
		if(typeof obj == 'number'){
			editData = $('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(obj) ;
		}else{
			editData = obj ;
		}
		$.get(config.getResource()+"/pages/auth/baseinfo/client/add.jsp").done(function(data){
			$("[data-toggle='tooltip']").tooltip();
			
			dialog = $(data) ;
			dialog.modal({
				keyboard: true
			});
			dialog.find('[data-dismiss="modal"]').click(function(){
				dialog.remove();
			});
			 $.ajax({
				 async:false,
		  			type : "get",
		  			url : config.getDomain()+"/clientgroup/list",
		  			dataType : "json",
		  			success : function(data) {
		  				dialog.find('#clientGroupId').typeahead({
		  					source: function(query, process) {
		  						var results = _.map(data.data, function (product) {
		  	                        return product.name;
		  	                    });
		  	                    process(results);
		  					},
		  					noselect:function(query){
			  					var client = _.find(data.data, function (p) {
			                        return p.name == query;
			                    });
			  					if(client==null){
			  						dialog.find('#clientGroupId').removeAttr("data");
			  						dialog.find('#clientGroupId').val("");
			  					}else{
			  						dialog.find('#clientGroupId').attr("data",client.id)
			  					}
		  					}
		  				});
		  			}
			 });
			
			if(editData==null){
				dialog.find("#clientCode").focus(function(){
					var clientCode = dialog.find("#clientCode").val();
					$.ajax({
						type:"post",
						url:config.getDomain()+"/baseController/queryClientCount",
					    data:{
					    	"queryStr":clientCode
					    },
					    dataType:"json",
					    success:function(resp){
					    	var dataTip = "以" + dialog.find("#clientCode").val() + "开头的客户编码总共有"+resp.data[0].nums+"个";
					    	dialog.find("#clientCode").attr("title",dataTip);
					    }
					});
				});
				if(!config.hasPermission('AADDBASECLIENT')){
					
					dialog.find("#addClient").hide();
				}
				dialog.find("#addClient").click(function() {
					var isOk = false;
					if(config.validateForm(dialog.find(".form-client"))){
						isOk=true;
					}
					if(isOk) {
						$.ajax({
							type : "post",
							url : config.getDomain()+"/client/save",
							data : {
								"code" : dialog.find(".code").val(),
								"name" : dialog.find(".name").val(),
								"phone" : dialog.find(".phone").val(),
								"guestId" : dialog.find(".guestId").val(),
								"email" : dialog.find(".email").val(),
								"fax" : dialog.find(".fax").val(),
								"address" : dialog.find(".address").val(),
								"postcode" : dialog.find(".postcode").val(),
								"contactName" : dialog.find(".contactName").val(),
								"contactPhone" : dialog.find(".contactPhone").val(),
								"bankAccount" : dialog.find(".bankAccount").val(),
								"bankName" : dialog.find(".bankName").val(),
								"creditGrade" : dialog.find(".creditGrade").val(),
								"paymentGrade" : dialog.find(".paymentGrade").val(),
								"description" : dialog.find(".description").val(),
								"clientGroupId": dialog.find('#clientGroupId').attr("data")
							},
							// data:JSON.stringify(sendGroup),
							dataType : "json",
							success : function(data) {
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '客户添加成功'
									});
									if(callback){
										callback(data.map.id,dialog.find(".name").val());
									}
									
									$('[data-role="baseInfoGrid"]').grid('refresh');
									dialog.remove();
								}else {
									$("body").message({
										type: 'error',
										content: '客户添加失败，可能存在同名冲突，请重新尝试。'
									});
									dialog.remove();
								}
							}
						});
					}
				});
			}else{
				
				if(!config.hasPermission('AUPDATEBASECLIENT')){
					
					dialog.find("#addClient").hide();
				}
				
				dialog.find(".code").val(editData.code);
				dialog.find(".name").val(editData.name);
				dialog.find(".phone").val(editData.phone);
				dialog.find(".guestId").val(editData.guestId);
				dialog.find(".email").val(editData.email);
				dialog.find(".fax").val(editData.fax);
				dialog.find(".address").val(editData.address);
				dialog.find(".postcode").val(editData.postcode);
				dialog.find(".contactName").val(editData.contactName);
				dialog.find(".contactPhone").val(editData.contactPhone);
				dialog.find(".bankAccount").val(editData.bankAccount);
				dialog.find(".bankName").val(editData.bankName);
				dialog.find(".creditGrade").val(editData.creditGrade);
				dialog.find(".paymentGrade").val(editData.paymentGrade);
				dialog.find(".description").val(editData.description);
				dialog.find('#clientGroupId').val(editData.clientGroupName);
				 dialog.find('#clientGroupId').attr("data",editData.clientGroupId);
				dialog.find("#addClient").click(function() {
					var isOk = false;
					if(config.validateForm(dialog.find(".form-client"))){
						isOk=true;
					}
					if(isOk) {
						$.ajax({
							type : "post",
							url : config.getDomain()+"/client/update",
							data : {
								"id" : editData.id,
								"code" : dialog.find(".code").val(),
								"name" : dialog.find(".name").val(),
								"phone" : dialog.find(".phone").val(),
								"guestId" : dialog.find(".guestId").val(),
								"email" : dialog.find(".email").val(),
								"fax" : dialog.find(".fax").val(),
								"address" : dialog.find(".address").val(),
								"postcode" : dialog.find(".postcode").val(),
								"contactName" : dialog.find(".contactName").val(),
								"contactPhone" : dialog.find(".contactPhone").val(),
								"bankAccount" : dialog.find(".bankAccount").val(),
								"bankName" : dialog.find(".bankName").val(),
								"creditGrade" : dialog.find(".creditGrade").val(),
								"paymentGrade" : dialog.find(".paymentGrade").val(),
								"description" : dialog.find(".description").val(),
								"clientGroupId": dialog.find('#clientGroupId').attr("data")
							},
							// data:JSON.stringify(sendGroup),
							dataType : "json",
							success : function(data) {
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '客户修改成功'
									});
//									$('[data-role="baseInfoGrid"]').grid('refresh');
									if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
										util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:editData.id,url:'/client/list'});
									
									dialog.remove();
								}else {
									$("body").message({
										type: 'error',
										content: '客户修改失败'
									});
									dialog.remove();
								}
							}
						});
					}
				});
			}
		});
	}
	
	
	
	//货品列表
	function initProduct() {
		columns = [ {
			title : "货品代码",
			name : "code",
				render: function(item, name, index){
//					if(config.hasPermission('AUPDATEBASEPRODUCT')){
					return "<a href='javascript:void(0)' onClick='BaseInfo.openProductDialog("+index+")'>"+item.code+"</a>";
//					}else{
//						return item.code;
//					}
					},
		}, {
			title : "货品名称",
			name : "name"
		}, {
			title : "货品类型",
			name : "value"
		},{
			title : "最后编辑人",
			name : "editUserName",
		},
		{
			title : "编辑时间",
			name : "editTime",
			render: function(item, name, index){
				if(item.editTime){
					
					return item.editTime.substring(0,item.editTime.length-2);
				}else{
					return "";
				}
			}
		}
		];
		
		// 清空grid缓存
		if ($('[data-role="baseInfoGrid"]').getGrid() != null) {
			$('[data-role="baseInfoGrid"]').getGrid().destory();
		}
		$('[data-role="baseInfoGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain() + "/product/list"
		});
		
		
		$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
		 $('.all').addClass('ln-selected');
		 prevLetter='all';
		 $('a','.ln-letters').unbind('click'); 
		$('a','.ln-letters').click(function(){
			  var $this = $(this),
            letter = $this.attr('class').split(' ')[0];
			  
			  if ( prevLetter !== letter ) {
				  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
				  $this.addClass('ln-selected');
				  prevLetter=letter;
			  }
			  console.log(letter);

				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
						'code':	key,
						'name':	key,
						'value' : key,
					'letter':letter
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			
			  
		});
		
		
		$(".btn-add").unbind('click'); 
		if(!config.hasPermission('AADDBASEPRODUCT')){
			$(".btn-add").hide();
		}else{
			$(".btn-add").show();
		}
		
		if(!config.hasPermission('ADELETEBASEPRODUCT')){
			$(".btn-remove").hide();
		}else{
			$(".btn-remove").show();
		}
		
		$(".btn-add").click(function(){
			openProductDialog(null);
		});
		$(".modify").unbind('click'); 
		if(!config.hasPermission('AUPDATEBASEPRODUCT')){
			$(".modify").hide();
		}else{
			$(".modify").show();
		}
		$(".modify").click(function() {
			var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要修改的记录'
				});
				return;
			}
			dataGrid = $(this);
			openProductDialog($('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0]);
		});
		$("#managerSearch").unbind('click'); 
		$("#managerSearch").click(function() {
			var key = $("#queryUserDivId").find('.search-key').val();
			var params = {
				'code':	key,
				'name':	key,
				'value' : key
			};
			excelParams=params;
           $('[data-role="baseInfoGrid"]').getGrid().search(params);
		});
		$(".btn-remove").unbind('click'); 
		$(".btn-remove").click(function() {
			var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
			var $this = $('[data-role="baseInfoGrid"]');
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}
				 $this.confirm({
					 content : '确定要撤销所选记录吗?',
					 callBack : function() {
						 deleteProduct($('[data-role="baseInfoGrid"]').getGrid().selectedRows(), $this);
					 }
				 });
		});
	}
	
	
	//删除货品
	var deleteProduct = function(products, grid) {
        dataGrid = grid;

        var data = "";
        $.each(products, function (i, role) {
            data += ("id=" + role.id + "&");
        });
        data = data.substring(0, data.length - 1);
        
        $.post(config.getDomain()+'/product/delete', data).done(function (data) {
            if (data.code == "0000") {
                dataGrid.message({
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
        }).fail(function (data) {
            dataGrid.message({
                type: 'error',
                content: '删除失败'
            });
        });
    }
	//货品编辑dialog
	function openProductDialog(obj){
		var editData ;
		if(typeof obj == 'number'){
			editData = $('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(obj) ;
		}else{
			editData = obj ;
		}
		$.get(config.getResource()+"/pages/auth/baseinfo/product/add.jsp").done(function(data){
			dialog = $(data) ;
			dialog.find('.colorpicker-default').colorpicker({
	            format: 'hex'
	        });
			dialog.modal({
				keyboard: true
			});
			$.ajax({
				async:false,
	  			type : "get",
	  			url : config.getDomain()+"/product/typeList",
	  			dataType : "json",
	  			success : function(data) {
	  				dialog.find('#type').typeahead({
	  					source: function(query, process) {
	  						var results = _.map(data.data, function (product) {
	  	                        return product.value;
	  	                    });
	  	                    process(results);
	  					},
	  				noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.value == query;
	                    });
	  					if(client==null){
	  						dialog.find('#type').removeAttr("data");
	  						dialog.find('#type').val("");
	  					}else{
	  						dialog.find('#type').attr("data",client.key)
	  					}
	  				}
	  			  });
	  			}
	  		});
			if(editData==null){
				
				if(!config.hasPermission('AADDBASEPRODUCT')){
					
					dialog.find("#addProduct").hide();
				}
				dialog.find("#addProduct").click(function() {
					var isOk = false;
					if(config.validateForm(dialog.find(".form-product"))){
						isOk=true;
					}
					if(isOk) {
						$.ajax({
							type : "post",
							url : config.getDomain()+"/product/save",
							data : {
								"code" : dialog.find(".code").val(),
								"name" : dialog.find(".name").val(),
								"type" : dialog.find(".type").attr("data"),
								"oils" : dialog.find(".oils").val(),
								"fontColor":dialog.find(".colorPicker").val(),
								"standardDensity" : dialog.find(".standardDensity").val(),
								"volumeRatio" : dialog.find(".volumeRatio").val(),
								"description" : dialog.find(".description").val()
							},
							// data:JSON.stringify(sendGroup),
							dataType : "json",
							success : function(data) {
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '货品添加成功'
									});
									$('[data-role="baseInfoGrid"]').grid('refresh');
									dialog.remove();
								}else {
									$("body").message({
										type: 'error',
										content: '货品添加失败，可能存在同名冲突，请重新尝试。'
									});
									dialog.remove();
								}
							}
						});
					}
				});
			}else{
				dialog.find(".code").val(editData.code);
				dialog.find(".name").val(editData.name);
				dialog.find(".type").attr("data",editData.type);
				dialog.find(".type").val(editData.value);
				dialog.find(".oils").val(editData.oils);
				dialog.find(".colorPicker").val(editData.fontColor);
				dialog.find(".standardDensity").val(editData.standardDensity);
				dialog.find(".volumeRatio").val(editData.volumeRatio);
				dialog.find(".description").val(editData.description);
				if(!config.hasPermission('AUPDATEBASEPRODUCT')){
					
					dialog.find("#addProduct").hide();
				}
				dialog.find("#addProduct").click(function() {
					var isOk = false;
					if(config.validateForm(dialog.find(".form-product"))){
						isOk=true;
					}
					if(isOk) {
						$.ajax({
							type : "post",
							url : config.getDomain()+"/product/update",
							data : {
								"id" : editData.id,
								"code" : dialog.find(".code").val(),
								"name" : dialog.find(".name").val(),
								"type" : dialog.find(".type").attr("data"),
								"oils" : dialog.find(".oils").val(),
								"fontColor":dialog.find(".colorPicker").val(),
								"standardDensity" : dialog.find(".standardDensity").val(),
								"volumeRatio" : dialog.find(".volumeRatio").val(),
								"description" : dialog.find(".description").val()
							},
							// data:JSON.stringify(sendGroup),
							dataType : "json",
							success : function(data) {
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '货品修改成功'
									});
//									$('[data-role="baseInfoGrid"]').grid('refresh'); 
									if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
										util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:editData.id,url:'/product/list'});
									
									dialog.remove();
								}else {
									$("body").message({
										type: 'error',
										content: '货品修改失败'
									});
									dialog.remove();
								}
							}
						});
					}
				});
			}
		});
	}

	
	//货代列表
	function initCargoAgent() {
		columns = [ {
			title : "货代简称",
			name : "code",
				render: function(item, name, index){
//					if(config.hasPermission('AUPDATEBASECARGOAGENT')){
					return "<a href='javascript:void(0)' onClick='BaseInfo.openCargoAgentDialog("+index+")'>"+item.code+"</a>";
//					}else{
//						return item.code;
//					}
					},
		}, {
			title : "货代名称",
			name : "name"
		}, {
			title : "联系人",
			name : "contactName"
		}, {
			title : "联系电话",
			name : "contactPhone"
		},{
			title : "最后编辑人",
			name : "editUserName",
		},
		{
			title : "编辑时间",
			name : "editTime",
			render: function(item, name, index){
				if(item.editTime){
					
					return item.editTime.substring(0,item.editTime.length-2);
				}else{
					return "";
				}
			}
		}
		];
		
		// 清空grid缓存
		if ($('[data-role="baseInfoGrid"]').getGrid() != null) {
			$('[data-role="baseInfoGrid"]').getGrid().destory();
		}
		$('[data-role="baseInfoGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain() + "/cargoagent/list"
		});
		
		$(".btn-add").unbind('click'); 
		if(!config.hasPermission('AADDBASECARGOAGENT')){
			$(".btn-add").hide();
		}else{
			$(".btn-add").show();
		}
		
		if(!config.hasPermission('ADELETEBASECARGOAGENT')){
			$(".btn-remove").hide();
		}else{
			$(".btn-remove").show();
		}
		
		
		$(".btn-add").click(function(){
			openCargoAgentDialog(null);
		});
		
		
		$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
		 $('.all').addClass('ln-selected');
		 prevLetter='all';
		 $('a','.ln-letters').unbind('click'); 
		$('a','.ln-letters').click(function(){
			  var $this = $(this),
           letter = $this.attr('class').split(' ')[0];
			  
			  if ( prevLetter !== letter ) {
				  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
				  $this.addClass('ln-selected');
				  prevLetter=letter;
			  }
			  console.log(letter);

				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
						'code':	key,
						'name':	key,
						'contactName' : key,
						'contactPhone' : key,
					'letter':letter
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			
			  
		});
		
		
		$("#managerSearch").unbind('click'); 
		$("#managerSearch").click(function() {
			var key = $("#queryUserDivId").find('.search-key').val();
			var params = {
				'code':	key,
				'name':	key,
				'contactName' : key,
				'contactPhone' : key
			};
			excelParams=params;
           $('[data-role="baseInfoGrid"]').getGrid().search(params);
		});
		$(".modify").unbind('click'); 
		if(!config.hasPermission('AUPDATEBASECARGOAGENT')){
			$(".modify").hide();
		}else{
			$(".modify").show();
		}
		$(".modify").click(function() {
			var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要修改的记录'
				});
				return;
			}
			dataGrid = $(this);
			openCargoAgentDialog($('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0]);
		});
		
		$(".btn-remove").unbind('click'); 
		$(".btn-remove").click(function() {
			var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
			var $this = $('[data-role="baseInfoGrid"]');
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}
				 $this.confirm({
					 content : '确定要撤销所选记录吗?',
					 callBack : function() {
						 deleteCargoAgent($('[data-role="baseInfoGrid"]').getGrid().selectedRows(), $this);
					 }
				 });
		});
	}
	
	
	//删除客户
	var deleteCargoAgent = function(cargoAgents, grid) {
        dataGrid = grid;

        var data = "";
        $.each(cargoAgents, function (i, role) {
            data += ("id=" + role.id + "&");
        });
        data = data.substring(0, data.length - 1);
        
        $.post(config.getDomain()+'/cargoagent/delete', data).done(function (data) {
            if (data.code == "0000") {
                dataGrid.message({
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
        }).fail(function (data) {
            dataGrid.message({
                type: 'error',
                content: '删除失败'
            });
        });
    };
	//货代编辑dialog
	function openCargoAgentDialog(obj){
		var editData ;
		if(typeof obj == 'number'){
			editData = $('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(obj) ;
		}else{
			editData = obj ;
		}
		$.get(config.getResource()+"/pages/auth/baseinfo/cargoagent/add.jsp").done(function(data){
			dialog = $(data) ;
			dialog.modal({
				keyboard: true
			});
			if(editData==null){
				
				if(!config.hasPermission('AADDBASECARGOAGENT')){
					
					dialog.find("#addCargoAgent").hide();
				}
				
				dialog.find("#addCargoAgent").click(function() {
					var isOk = false;
					if(config.validateForm(dialog.find(".form-cargoagent"))){
						isOk=true;
					}
					if(isOk) {
						$.ajax({
							type : "post",
							url : config.getDomain()+"/cargoagent/save",
							data : {
								"code" : dialog.find(".code").val(),
								"name" : dialog.find(".name").val(),
								"contactName" : dialog.find(".contactName").val(),
								"contactPhone" : dialog.find(".contactPhone").val(),
								"contactEmail" : dialog.find(".contactEmail").val(),
								"description" : dialog.find(".description").val()
							},
							// data:JSON.stringify(sendGroup),
							dataType : "json",
							success : function(data) {
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '货代添加成功'
									});
									$('[data-role="baseInfoGrid"]').grid('refresh');
									dialog.remove();
								}else {
									$("body").message({
										type: 'error',
										content: '货代添加失败，可能存在同名冲突，请重新尝试。'
									});
									dialog.remove();
								}
							}
						});
					}
				});
			}else{
				

				if(!config.hasPermission('AUPDATEBASECARGOAGENT')){
					
					dialog.find("#addCargoAgent").hide();
				}
				
				dialog.find(".code").val(editData.code);
				dialog.find(".name").val(editData.name);
				dialog.find(".contactName").val(editData.contactName);
				dialog.find(".contactPhone").val(editData.contactPhone);
				dialog.find(".contactEmail").val(editData.contactEmail);
				dialog.find(".description").val(editData.description);
				
				dialog.find("#addCargoAgent").click(function() {
					var isOk = false;
					if(config.validateForm(dialog.find(".form-cargoagent"))){
						isOk=true;
					}
					if(isOk) {
						$.ajax({
							type : "post",
							url : config.getDomain()+"/cargoagent/update",
							data : {
								"id" : editData.id,
								"code" : dialog.find(".code").val(),
								"name" : dialog.find(".name").val(),
								"contactName" : dialog.find(".contactName").val(),
								"contactPhone" : dialog.find(".contactPhone").val(),
								"contactEmail" : dialog.find(".contactEmail").val(),
								"description" : dialog.find(".description").val()
							},
							// data:JSON.stringify(sendGroup),
							dataType : "json",
							success : function(data) {
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '货代修改成功'
									});
//									$('[data-role="baseInfoGrid"]').grid('refresh');
									if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
										util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:editData.id,url:'/cargoagent/list'});
									
									dialog.remove();
								}else {
									$("body").message({
										type: 'error',
										content: '货代修改失败'
									});
									dialog.remove();
								}
							}
						});
					}
				});
			}
		});
	}
	
	
	//船舶列表
	function initShip() {
		columns = [  {
			title : "船舶英文名",
			name : "name",
			render: function(item, name, index){
//				if(config.hasPermission('AUPDATEBASESHIP')){
				return "<a href='javascript:void(0)' onClick='BaseInfo.openShipDialog("+index+")'>"+item.name+"</a>";
//				}else{
//					return item.name;
//				}
				}
		}, {
			title : "船舶中文名",
			name : "shipRefNames",
			render:function(item,name,index){
				if(item.shipRefNames!=null){
					return item.shipRefNames.replace(new RegExp(",","gm"),"<br></br>");
				}
				else{
					return "";
				}
			}
		},{
			title : "船籍",
			name : "shipRegistry"
		}, {
			title : "船主",
			name : "owner"
		}, {
			title : "船长",
			name : "shipLenth"
		}, {
			title : "船宽",
			name : "shipWidth"
		}, {
			title : "最大吃水",
			name : "shipDraught"
		}, {
			title : "建造年份",
			name : "buildYear"
		}, {
			title : "载重",
			name : "loadCapacity"
		}, {
			title : "总吨",
			name : "grossTons"
		}, {
			title : "净吨",
			name : "netTons"
		}, {
			title : "负责人",
			name : "manager"
		}, {
			title : "联系人",
			name : "contactName"
		}, {
			title : "联系电话",
			name : "contactPhone"
		},{
			title : "最后编辑人",
			name : "editUserName",
		},
		{
			title : "编辑时间",
			name : "editTime",
			render: function(item, name, index){
				if(item.editTime){
					
					return item.editTime.substring(0,item.editTime.length-2);
				}else{
					return "";
				}
			}
		}
		];
		
		
		 $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
		 $('.all').addClass('ln-selected');
		 prevLetter='all';
		 $('a','.ln-letters').unbind('click'); 
		$('a','.ln-letters').click(function(){
			  var $this = $(this),
              letter = $this.attr('class').split(' ')[0];
			  
			  if ( prevLetter !== letter ) {
				  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
				  $this.addClass('ln-selected');
				  prevLetter=letter;
			  }
			  console.log(letter);

				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
					'code':	key,
					'name':	key,
					'refCode' : key,
					'owner' : key,
					'contactName' : key,
					'manager' : key,
					'refName' : key,
					'letter':letter
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			
			  
		});
		
		
		$("#managerSearch").unbind('click'); 
		$("#managerSearch").click(function() {
			var key = $("#queryUserDivId").find('.search-key').val();
			var params = {
				'code':	key,
				'name':	key,
				'refCode' : key,
				'owner' : key,
				'contactName' : key,
				'manager' : key,
				'refName' : key
			};
			excelParams=params;
           $('[data-role="baseInfoGrid"]').getGrid().search(params);
		});
		// 清空grid缓存
		if ($('[data-role="baseInfoGrid"]').getGrid() != null) {
			$('[data-role="baseInfoGrid"]').getGrid().destory();
		}
		$('[data-role="baseInfoGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain() + "/ship/list"
		});
		
		$(".btn-add").unbind('click');
		if(!config.hasPermission('AADDBASESHIP')){
			$(".btn-add").hide();
		}else{
			$(".btn-add").show();
		}
		
		if(!config.hasPermission('ADELETEBASESHIP')){
			$(".btn-remove").hide();
		}else{
			$(".btn-remove").show();
		}
		
		
		if(!config.hasPermission('AUPDATEBASESHIP')){
			$(".modify").hide();
		}else{
			$(".modify").show();
		}
		$(".btn-add").click(function(){
			openShipDialog(null);
		});
		
		$(".modify").unbind('click'); 
		$(".modify").click(function() {
			var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要修改的记录'
				});
				return;
			}
			dataGrid = $(this);
			openShipDialog($('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0]);
		});
		
		$(".btn-remove").unbind('click'); 
		$(".btn-remove").click(function() {
			var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
			var $this = $('[data-role="baseInfoGrid"]');
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}
				 $this.confirm({
					 content : '确定要撤销所选记录吗?',
					 callBack : function() {
						 deleteShip($('[data-role="baseInfoGrid"]').getGrid().selectedRows(), $this);
					 }
				 });
		});
	}
	
	
	//删除船舶
	var deleteShip = function(ships, grid) {
        dataGrid = grid;

        var data = "";
        $.each(ships, function (i, role) {
            data += ("shipIds=" + role.id + "&");
        });
        data = data.substring(0, data.length - 1);
        
        $.post(config.getDomain()+'/ship/delete', data).done(function (data) {
            if (data.code == "0000") {
                dataGrid.message({
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
        }).fail(function (data) {
            dataGrid.message({
                type: 'error',
                content: '删除失败'
            });
        });
    };
    
    function openCertifyDialog(id){
	$.get(config.getResource()+"/pages/auth/baseinfo/certify/addCertifyCustomer.jsp").done(function(data){
		dialog = $(data) ;
		config.load() ;
		$.ajax({
			type : "post",
			url : config.getDomain()+"/certify/get?id="+id,
			dataType : "json",
			async:false,
			success : function(data) {
				dialog.find("#code").val(data.data[0].code) ;
				dialog.find("#name").val(data.data[0].name) ;
				dialog.find("#contactName").val(data.data[0].contactName) ;
				dialog.find("#contactPhone").val(data.data[0].contactPhone) ;
				dialog.find("#contactEmail").val(data.data[0].contactEmail) ;
			}
		});
		
		if(!config.hasPermission('AUPDATEBASECERTIFY')){
			
			dialog.find("#save").hide();
		}
		
		dialog.find("#save").click(function(){
			if(!config.validateForm(dialog.find(".form-body"))){
				return false ;
			}
			$.ajax({
				type : "post",
				url : config.getDomain()+"/certify/update",
				dataType : "json",
				async:false,
				data:{
					"code":dialog.find("#code").val(),
					"name":dialog.find("#name").val() ,
					"contactName":dialog.find("#contactName").val() ,
					"contactPhone":dialog.find("#contactPhone").val() ,
					"contactEmail":dialog.find("#contactEmail").val() ,
					"id":id
				},
				success : function(data) {
					if(data.code=="0000"){
						$("body").message({
							type : 'success',     
							content : '更新船代成功'
						});
//						$('[data-role="baseInfoGrid"]').getGrid().refresh();
						if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
							util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:id,url:'/certify/list'});
						
						dialog.remove();
					}else{
						$("body").message({
							type : 'error',   
							content : '更新船代失败'
						});
					}
				}
			});
		}) ;
		config.unload() ;
		dialog.modal({
			keyboard: true
		});
	});
    }
	//船舶编辑dialog
	function openShipDialog(obj){
		var editData ;
		if(typeof obj == 'number'){
			editData = $('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(obj) ;
		}else{
			editData = obj ;
		}
		$.get(config.getResource()+"/pages/auth/baseinfo/ship/add.jsp").done(function(data){
			dialog = $(data) ;
			dialog.modal({
				keyboard: true
			});
			dialog.find("#shipRefAdd").unbind('click') ;
			dialog.find("#shipRefAdd").click(function(){
				var str = "<tr class='shipRef'><td><input type='hidden' name='id' /><input type='text' name='shipRefName' class='form-control' /></td><td><a class='btn btn-xs blue' onclick='BaseInfo.deleteRefInfo(this);' href='javascript:void(0)'><span title='撤销' class='glyphicon glyphicon glyphicon-remove'></span></a></td></tr>" ;
				dialog.find("#tbody").append(str) ;
				return false ;
			});
			if(editData==null){
				
				if(!config.hasPermission('AADDBASESHIP')){
					
					dialog.find("#addShip").hide();
				}
				
				dialog.find("#addShip").click(function() {
					var isOk = false;
					if(config.validateForm(dialog.find(".form-ship"))){
						isOk=true;
					}
					if(isOk) {
						var  shipInfo ={
								"code" : dialog.find(".code").val(),
								"name" : dialog.find(".name").val(),
								"refCode" : dialog.find(".refCode").val(),
								"refName" : dialog.find(".refName").val(),
								"shipRegistry" : dialog.find(".shipRegistry").val(),
								"shipLenth" : dialog.find(".shipLenth").val(),
								"shipWidth" : dialog.find(".shipWidth").val(),
								"shipDraught" : dialog.find(".shipDraught").val(),
								"loadCapacity" : dialog.find(".loadCapacity").val(),
								"grossTons" : dialog.find(".grossTons").val(),
								"netTons" : dialog.find(".netTons").val(),
								"notice" : dialog.find(".notice").val(),
								"owner" : dialog.find(".owner").val(),
								"manager" : dialog.find(".manager").val(),
								"contactName" : dialog.find(".contactName").val(),
								"contactPhone" : dialog.find(".contactPhone").val(),
								"port": dialog.find(".port").val(),
								"description" : dialog.find(".description").val()
						};
						if(dialog.find(".buildYear").val()){
							shipInfo.buildYear = dialog.find(".buildYear").val() ;
						}
						var shipRefInfoList = new Array() ;
						dialog.find("#tbody tr").each(function(){
							if($(this).find("input[name=shipRefName]").val()!=""&&$(this).find("input[name=shipRefName]").val()!=null){
								var shipRefInfo = {
										"refName":$(this).find("input[name=shipRefName]").val()
								} ;
								shipRefInfoList.push(shipRefInfo);
							}
						}) ;
						var shipDtoList = {"shipRefInfoList":shipRefInfoList,"ship":shipInfo} ;
						$.ajax({
							type : "post",
							url : config.getDomain()+"/ship/save",
							data :{"shipDtoList":JSON.stringify(shipDtoList)},
							dataType : "json",
							success : function(data) {
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '船舶添加成功'
									});
									$('[data-role="baseInfoGrid"]').grid('refresh');
									dialog.remove();
								}else {
									$("body").message({
										type: 'error',
										content: '船舶添加失败，可能存在同名冲突，请重新尝试。'
									});
									dialog.remove();
								}
							}
						});
					}
				});
			}else{
				
				if(!config.hasPermission('AUPDATEBASESHIP')){
					
					dialog.find("#addShip").hide();
				}
				
				dialog.find(".code").val(editData.code);
				dialog.find(".name").val(editData.name);
				dialog.find(".refCode").val(editData.refCode);
				dialog.find(".refName").val(editData.refName);
				dialog.find(".shipRegistry").val(editData.shipRegistry);
				dialog.find(".shipLenth").val(editData.shipLenth);
				dialog.find(".shipWidth").val(editData.shipWidth);
				dialog.find(".shipDraught").val(editData.shipDraught);
				dialog.find(".buildYear").val(editData.buildYear);
				dialog.find(".loadCapacity").val(editData.loadCapacity);
				dialog.find(".grossTons").val(editData.grossTons);
				dialog.find(".netTons").val(editData.netTons);
				dialog.find(".notice").val(editData.notice);
				dialog.find(".owner").val(editData.owner);
				dialog.find(".manager").val(editData.manager);
				dialog.find(".port").val(editData.port);
				dialog.find(".contactName").val(editData.contactName);
				dialog.find(".contactPhone").val(editData.contactPhone);
				dialog.find(".description").val(editData.description);
				$.ajax({
					type : "post",
					url : config.getDomain()+"/ship/getShipRefList",
					data:{"shipId":editData.id},
					dataType : "json",
					async:false,
					success : function(data) {
							if(data.data.length>0){
								var shipRefList = data.data ;
								var html ;
								for(var i=0;i<shipRefList.length;i++){
									html += "<tr class='shipRef'><td><input type='hidden' name='id'  value="+shipRefList[i].id+" /><input type='text' name='shipRefName' value='"+shipRefList[i].refName+"' class='form-control' /></td><td><a class='btn btn-xs blue' onclick='BaseInfo.deleteRefInfo(this)' href='javascript:void(0)'><span title='撤销' class='glyphicon glyphicon glyphicon-remove'></span></a></td></tr>" ;
								}
								dialog.find("#tbody").append(html) ;
								}
							}
					});
				
				dialog.find("#addShip").click(function() {
					var isOk = false;
					if(config.validateForm(dialog.find(".form-ship"))){
						isOk=true;
					}
					if(isOk) {
						var  shipInfo ={
								"id" : editData.id,
								"code" : dialog.find(".code").val(),
								"name" : dialog.find(".name").val(),
								"refCode" : dialog.find(".refCode").val(),
								"refName" : dialog.find(".refName").val(),
								"shipRegistry" : dialog.find(".shipRegistry").val(),
								"shipLenth" : dialog.find(".shipLenth").val(),
								"shipWidth" : dialog.find(".shipWidth").val(),
								"shipDraught" : dialog.find(".shipDraught").val(),
								"buildYear" : dialog.find(".buildYear").val(),
								"loadCapacity" : dialog.find(".loadCapacity").val(),
								"grossTons" : dialog.find(".grossTons").val(),
								"netTons" : dialog.find(".netTons").val(),
								"notice" : dialog.find(".notice").val(),
								"owner" : dialog.find(".owner").val(),
								"manager" : dialog.find(".manager").val(),
								"contactName" : dialog.find(".contactName").val(),
								"contactPhone" : dialog.find(".contactPhone").val(),
								"port": dialog.find(".port").val(),
								"description" : dialog.find(".description").val()
						};
						var shipRefInfoList = new Array() ;
						dialog.find("#tbody tr").each(function(){
							
							if($(this).find("input[name=shipRefName]").val()){
								var shipRefInfo = {
										"refName":dialog.find(this).find("input[name=shipRefName]").val()
								} ;
								if($(this).find("input[name=id]").val()){
									shipRefInfo.id = $(this).find("input[name=id]").val() ;
								}
								shipRefInfoList.push(shipRefInfo);
							}
							
						}) ;
						var shipDtoList = {"shipRefInfoList":shipRefInfoList,"ship":shipInfo} ;
						$.ajax({
							type : "post",
							url : config.getDomain()+"/ship/update",
							data:{"shipDtoList":JSON.stringify(shipDtoList)},
							dataType : "json",
							success : function(data) {
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '船舶修改成功'
									});
//									$('[data-role="baseInfoGrid"]').grid('refresh');
									util.updateGridRow($("[data-role='baseInfoGrid']"),{url:'/ship/list',id:editData.id});

									dialog.remove();
								}else {
									$("body").message({
										type: 'error',
										content: '船舶修改失败'
									});
									dialog.remove();
								}
							}
						});
					}
				});
			}
		});
	}
	function openInspectAgentDialog(id){
	$.get(config.getResource()+"/pages/auth/baseinfo/inspectAgent/addInspectAgent.jsp").done(function(data){
		dialog = $(data) ;
		config.load() ;
		$.ajax({
			type : "post",
			url : config.getDomain()+"/inspectAgent/get?id="+id,
			dataType : "json",
			async:false,
			success : function(data) {
				dialog.find("#code").val(data.data[0].code) ;
				dialog.find("#name").val(data.data[0].name) ;
				dialog.find("#contactName").val(data.data[0].contactName) ;
				dialog.find("#contactPhone").val(data.data[0].contactPhone) ;
				dialog.find("#contactEmail").val(data.data[0].contactEmail) ;
			}
		});
		
		if(!config.hasPermission('AUPDATEBASEINSPECT')){
			
			dialog.find("#save").hide();
		}
		
		dialog.find("#save").click(function(){
			if(!config.validateForm(dialog.find(".form-body"))){
				return false ;
			}
			$.ajax({
				type : "post",
				url : config.getDomain()+"/inspectAgent/update",
				dataType : "json",
				async:false,
				data:{
					"code":dialog.find("#code").val(),
					"name":dialog.find("#name").val() ,
					"contactName":dialog.find("#contactName").val() ,
					"contactPhone":dialog.find("#contactPhone").val() ,
					"contactEmail":dialog.find("#contactEmail").val() ,
					"id":id
				},
				success : function(data) {
					if(data.code=="0000"){
						$("body").message({
							type : 'success',     
							content : '更新船代成功'
						});
//						$('[data-role="baseInfoGrid"]').getGrid().refresh();
						if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
							util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:id,url:'/inspectAgent/list'});
						
					}else{
						$("body").message({
							type : 'error',     
							content : '更新船代失败'
						});
					}
				}
			});
		}) ;
		config.unload() ;
		dialog.modal({
			keyboard: true
		});
	});}
	function openShipAgentDialog(id){
		$.get(config.getResource()+"/pages/auth/baseinfo/shipAgent/addShipAgent.jsp").done(function(data){
			dialog = $(data) ;
			config.load() ;
			$.ajax({
				type : "post",
				url : config.getDomain()+"/shipagent/get?id="+id,
				dataType : "json",
				async:false,
				success : function(data) {
					dialog.find("#code").val(data.data[0].code) ;
					dialog.find("#name").val(data.data[0].name) ;
					dialog.find("#contactName").val(data.data[0].contactName) ;
					dialog.find("#contactPhone").val(data.data[0].contactPhone) ;
					dialog.find("#contactEmail").val(data.data[0].contactEmail) ;
					dialog.find("#description").val(data.data[0].description) ;
				}
			});
			
			if(!config.hasPermission('AUPDATEBASESHIPAGENT')){
				
				dialog.find("#save").hide();
			}
			
			dialog.find("#save").click(function(){
				if(!config.validateForm(dialog.find(".form-body"))){
					return false ;
				}
				$.ajax({
					type : "post",
					url : config.getDomain()+"/shipagent/update",
					dataType : "json",
					async:false,
					data:{
						"code":dialog.find("#code").val(),
						"name":dialog.find("#name").val() ,
						"contactName":dialog.find("#contactName").val() ,
						"contactPhone":dialog.find("#contactPhone").val() ,
						"contactEmail":dialog.find("#contactEmail").val() ,
						"description":dialog.find("#description").val() ,
						"id":id
					},
					success : function(data) {
						if(data.code=="0000"){
							$("body").message({
								type : 'success',     
								content : '更新船代成功'
							});
							$(dialog).remove() ;
//							$('[data-role="baseInfoGrid"]').getGrid().refresh();
							if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
								util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:id,url:'/shipagent/list'});
							
						}else{
							$("body").message({
								type : 'error',     
								content : '更新船代失败'
							});
						}
					}
				});
			}) ;
			config.unload() ;
			dialog.modal({
				keyboard: true
			});
		});
	}
	
	function openTruckDialog(id){
		$.get(config.getResource()+"/pages/auth/baseinfo/truck/addTruct.jsp").done(function(data){
			dialog = $(data) ;
//			Invoice.initTruckPlate(dialog) ;
			config.load() ;
			$.ajax({
				type : "post",
				url : config.getDomain()+"/truck/get?id="+id,
				dataType : "json",
				async:false,
				success : function(data) {	
					dialog.find("#code").val(data.data[0].code) ;
					dialog.find("#name").val(data.data[0].name) ;
					dialog.find("#loadCapacity").val(data.data[0].loadCapacity) ;
					dialog.find("#maxLoadCapacity").val(util.isNull(data.data[0].maxLoadCapacity));
					dialog.find("#company").val(data.data[0].company) ;
					dialog.find("#description").val(data.data[0].description) ;
				}
			});
			
if(!config.hasPermission('AUPDATEBASETRUCK')){
				
				dialog.find("#save").hide();
			}
			
			dialog.find("#save").click(function(){
				if(!config.validateForm(dialog.find(".form-body"))){
					return false ;
				}
				
				var truckCode = dialog.find("#code").val();
				if(truckCode.length < 6)
				{
					$("body").message({
						type : 'error',
						content : '请输入长度大于等于6且由数字字母组成的车牌号！'
					});
					
					return;
				}
				
				$.ajax({
					type : "post",
					url : config.getDomain()+"/truck/update",
					dataType : "json",
					async:false,
					data:{
						"code":dialog.find("#code").val(),
						"name":dialog.find("#name").val() ,
						"loadCapacity":dialog.find("#loadCapacity").val() ,
						"maxLoadCapacity":util.isNull(dialog.find("#maxLoadCapacity").val())==''?null:util.isNull(dialog.find("#maxLoadCapacity").val(),1),
						"company":dialog.find("#company").val() ,
						"description":dialog.find("#description").val() ,
						"id":id
					},
					success : function(data) {
						if(data.code=="0000"){
							$("body").message({
								type : 'success',     
								content : '更新车辆信息成功'
							});
//							$('[data-role="baseInfoGrid"]').getGrid().refresh();
							if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
								util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:id,url:'/truck/list'});
							
							dialog.remove();
						}else{
							$("body").message({
								type : 'error',     
								content : '更新车辆信息失败'
							});
						}
					}
				});
			}) ;
			config.unload() ;
			dialog.modal({
				keyboard: true
			});
		});
	}
	function init() {
//		initClientGroup();
	
	}
		
	function changetab(obj, item) {
		if(obj!=""){
			$(obj).parent().addClass("active").siblings().removeClass("active");
		}else{
			$("#tab"+item).parent().addClass("active").siblings().removeClass("active");
		}
		$(".search-key").val("") ;
		currentPage = item;
		$('.ln-letters').show();
		if(item==14){
			$(".showVir").show();
		}else{
			$(".showVir").hide();
		}
		
		if(item!=1){
			$(".btn-file").hide();
		}
		if(item==0){
			//客户组
			initClientGroup();
		}
		if (item == 1) {
			// 客户资料
			initClient();
			if(config.hasPermission('ABASECLIENTFILE')){
			$(".btn-file").show();
			}
		}
		if (item == 2) {
			// 货品资料
			initProduct();
		}
		if (item == 3) {
			// 货代资料
			initCargoAgent();
		}
		if (item == 4) {
			// 船舶资料
			initShip();
		}
		if (item == 5) {
			if ($('[data-role="baseInfoGrid"]').getGrid() != null) {
				$('[data-role="baseInfoGrid"]').getGrid().destory();
			}
			//船代资料
			var columns = [  {
				title : "船代名称",
				name : "name",
				render: function(item, name, index){
//					if(config.hasPermission('AUPDATEBASESHIPAGENT')){
					return "<a href='javascript:void(0)' onClick='BaseInfo.openShipAgentDialog("+item.id+")'>"+item.name+"</a>";
//			}else{
//				return item.name;
//			}
					}
			}, {
				title : "联系人",
				name : "contactName",
			}, {
				title : "联系电话",
				name : "contactPhone",
			},{
				title : "最后编辑人",
				name : "editUserName",
			},
			{
				title : "编辑时间",
				name : "editTime",
				render: function(item, name, index){
					if(item.editTime){
						
						return item.editTime.substring(0,item.editTime.length-2);
					}else{
						return "";
					}
				}
			}];
			$("#managerSearch").unbind('click'); 
			$("#managerSearch").click(function() {
				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
					'code':	key,
					'name':	key,
					'contactName' : key,
					'contactPhone' : key
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			});
			// 船代资料
			$(".btn-remove").unbind('click'); 
			$(".btn-remove").click(function() {
				var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
				if (data.length == 0) {
					$('[data-role="baseInfoGrid"]').message({
						type : 'warning',
						content : '请选择要撤销的记录'
					});
					return;
				}
				dataGrid = $('[data-role="baseInfoGrid"]');
			    dataGrid.confirm({
					content : '确定要撤销所选记录吗?',
					callBack : function() {
						 $.post(config.getDomain()+'/shipagent/delete', "id="+data).done(function (data) {
					            if (data.code == "0000") {
					                dataGrid.message({
					                    type: 'success',
					                    content: '删除成功'
					                });
					                dataGrid.getGrid().refresh();
					            } else {
					                $('body').message({
					                    type: 'error',
					                    content: data.msg
					                });
					            }
					        }).fail(function (data) {
					            dataGrid.message({
					                type: 'error',
					                content: '删除失败'
					            });
					        });
					}
				});
			});
			/*解决id冲突的问题*/
			$('[data-role="baseInfoGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : true,
				url : config.getDomain()+"/shipagent/list"
			});
			
			//初始化按钮操作
			$(".btn-add").unbind('click'); 
			if(!config.hasPermission('AADDBASESHIPAGENT')){
				$(".btn-add").hide();
			}else{
				$(".btn-add").show();
			}
			
			if(!config.hasPermission('ADELETEBASESHIPAGENT')){
				$(".btn-remove").hide();
			}else{
				$(".btn-remove").show();
			}
			
			if(!config.hasPermission('AUPDATEBASESHIPAGENT')){
				$(".modify").hide();
			}else{
				$(".modify").show();
			}
			$(".btn-add").click(function() {
				$.get(config.getResource()+"/pages/auth/baseinfo/shipAgent/addShipAgent.jsp").done(function(data){
					dialog = $(data) ;
					
					if(!config.hasPermission('AADDBASESHIPAGENT')){
						
						dialog.find("#save").hide();
					}
					
					dialog.find("#save").click(function(){
						if(!config.validateForm(dialog.find(".form-body"))){
							return false ;
						}
						$.ajax({
							type : "post",
							url : config.getDomain()+"/shipagent/save",
							dataType : "json",
							async:false,
							data:{
								"code":dialog.find("#code").val(),
								"name":dialog.find("#name").val() ,
								"contactName":dialog.find("#contactName").val() ,
								"contactPhone":dialog.find("#contactPhone").val() ,
								"contactEmail":dialog.find("#contactEmail").val() ,
								"description":dialog.find("#description").val()
							},
							success : function(data) {
								if(data.code=="0000"){
									$("body").message({
										type : 'success',     
										content : '添加船代成功'
									});
									$(dialog).remove() ;
									$('[data-role="baseInfoGrid"]').getGrid().refresh();
								}else{
									$("body").message({
										type : 'error',     
										content : '添加船代失败，可能存在同名冲突，请重新尝试。'
									});
								}
							}
						});
					});
					dialog.modal({
						keyboard: true
					});
				});
			});
			
			$(".modify").unbind('click'); 
			$(".modify").click(function() {
				var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
				var $this = $(this);
				if (data.length == 0) {
					$('[data-role="baseInfoGrid"]').message({
						type : 'warning',
						content : '请选择要修改的记录'
					});
					return;
				}
				var id = $('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0].id;
				openShipAgentDialog(id) ;
			});
			
			$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
			 $('.all').addClass('ln-selected');
			 prevLetter='all';
			 $('a','.ln-letters').unbind('click'); 
			$('a','.ln-letters').click(function(){
				  var $this = $(this),
	           letter = $this.attr('class').split(' ')[0];
				  
				  if ( prevLetter !== letter ) {
					  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
					  $this.addClass('ln-selected');
					  prevLetter=letter;
				  }
				  console.log(letter);

					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'code':	key,
							'name':	key,
							'contactName' : key,
							'contactPhone' : key,
						'letter':letter
					};
					excelParams=params;
		           $('[data-role="baseInfoGrid"]').getGrid().search(params);
				
				  
			});
			
			$(".btn-search").unbind('click'); 
			$(".btn-search").click(function() {
				$("#roleManagerQueryDivId").slideToggle("slow");
			});
			$(".search").unbind('click'); 
			$(".search").click(function() {
				var shipArrivalDto = {};
		        $("#intentListForm").find('.form-control').each(function(){
		            var $this = $(this);
		            var name = $this.attr('name');
		             if(name){
		            	 shipArrivalDto[name] = $this.val();
		            }
		        });
		       $('[data-role="baseInfoGrid"]').getGrid().search(shipArrivalDto);
			});
			
		}
		if (item == 6) {
			if ($('[data-role="baseInfoGrid"]').getGrid() != null) {
				$('[data-role="baseInfoGrid"]').getGrid().destory();
			}
			//车辆资料
			var columns = [  {
				title : "车牌号",
				name : "code",
				render: function(item, name, index){
//					if(config.hasPermission('AUPDATEBASETRUCK')){
					return "<a href='javascript:void(0)' onClick='BaseInfo.openTruckDialog("+item.id+")'>"+item.code+"</a>";
//					}else{
//						return item.code;
//					}
					
					},
			}, {
				title : "历史最大载重吨",
				render:function(item){
					return util.isNull(item.loadCapacity);
				}
			},{
				title : "最大允许荷载吨",
				render:function(item){
					return util.isNull(item.maxLoadCapacity);
				}
			}, {
				title : "所属单位",
				name : "company",
			}, {
				title : "描述",
				name : "description",
			},{
				title : "最后编辑人",
				name : "editUserName",
			},
			{
				title : "编辑时间",
				name : "editTime",
				render: function(item, name, index){
					if(item.editTime){
						
						return item.editTime.substring(0,item.editTime.length-2);
					}else{
						return "";
					}
				}
			}];
			
			$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
			 $('.all').addClass('ln-selected');
			 prevLetter='all';
			 $('a','.ln-letters').unbind('click'); 
			$('a','.ln-letters').click(function(){
				  var $this = $(this),
	           letter = $this.attr('class').split(' ')[0];
				  
				  if ( prevLetter !== letter ) {
					  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
					  $this.addClass('ln-selected');
					  prevLetter=letter;
				  }
				  console.log(letter);

					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'code':	key,
							'name':	key,
							'company' : key,
						'letter':letter
					};
					excelParams=params;
		           $('[data-role="baseInfoGrid"]').getGrid().search(params);
				
				  
			});
			
			$("#managerSearch").unbind('click'); 
			$("#managerSearch").click(function() {
				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
					'code':	key,
					'name':	key,
					'company' : key
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			});
			// 车辆资料
			$(".btn-remove").unbind('click'); 
			$(".btn-remove").click(function() {
				var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
				if (data.length == 0) {
					$('[data-role="baseInfoGrid"]').message({
						type : 'warning',
						content : '请选择要撤销的记录'
					});
					return;
				}
				dataGrid = $('[data-role="baseInfoGrid"]');
			    dataGrid.confirm({
					content : '确定要撤销所选记录吗?',
					callBack : function() {
						 $.post(config.getDomain()+'/truck/delete', "truckIds="+data).done(function (data) {
					            if (data.code == "0000") {
					                dataGrid.message({
					                    type: 'success',
					                    content: '删除成功'
					                });
					                dataGrid.getGrid().refresh();
					            } else {
					                $('body').message({
					                    type: 'error',
					                    content: data.msg
					                });
					            }
					        }).fail(function (data) {
					            dataGrid.message({
					                type: 'error',
					                content: '删除失败'
					            });
					        });
					}
				});
			});
			/*解决id冲突的问题*/
			$('[data-role="baseInfoGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : true,
				url : config.getDomain()+"/truck/list"
			});
			
			//初始化按钮操作
			$(".btn-add").unbind('click'); 
			if(!config.hasPermission('AADDBASETRUCK')){
				$(".btn-add").hide();
			}else{
				$(".btn-add").show();
			}
			if(!config.hasPermission('ADELETEBASETRUCK')){
				$(".btn-remove").hide();
			}else{
				$(".btn-remove").show();
			}
			if(!config.hasPermission('AUPDATEBASETRUCK')){
				$(".modify").hide();
			}else{
				$(".modify").show();
			}
			
			$(".btn-add").click(addTruck);
			
			$(".modify").unbind('click'); 
			$(".modify").click(function() {
				var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
				var $this = $(this);
				if (data.length == 0) {
					$('[data-role="baseInfoGrid"]').message({
						type : 'warning',
						content : '请选择要修改的记录'
					});
					return;
				}
				var id = $('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0].id;
				openTruckDialog(id) ;
			});
			$(".btn-search").unbind('click'); 
			$(".btn-search").click(function() {
				$("#roleManagerQueryDivId").slideToggle("slow");
			});
			$(".search").unbind('click'); 
			$(".search").click(function() {
				var shipArrivalDto = {};
		        $("#intentListForm").find('.form-control').each(function(){
		            var $this = $(this);
		            var name = $this.attr('name');
		             if(name){
		            	 shipArrivalDto[name] = $this.val();
		            }
		        });
		       $('[data-role="baseInfoGrid"]').getGrid().search(shipArrivalDto);
			});
			
		}
		if (item == 7) {
			if ($('[data-role="baseInfoGrid"]').getGrid() != null) {
				$('[data-role="baseInfoGrid"]').getGrid().destory();
			}
			//商检单位
			var columns = [  {
				title : "商检简称",
				name : "code",
				render: function(item, name, index){
//					if(config.hasPermission('AUPDATEBASEINSPECT')){
					return "<a href='javascript:void(0)' onClick='BaseInfo.openInspectAgentDialog("+item.id+")'>"+item.code+"</a>";
//					}else{
//						return item.code;
//					}
					},
			},{
				title : "商检全称",
				name : "name"
			}, {
				title : "联系人",
				name : "contactName",
			}, {
				title : "联系人电子邮箱",
				name : "contactEmail",
			}, {
				title : "联系人电话",
				name : "contactPhone",
			},{
				title : "最后编辑人",
				name : "editUserName",
			},
			{
				title : "编辑时间",
				name : "editTime",
				render: function(item, name, index){
					if(item.editTime){
						
						return item.editTime.substring(0,item.editTime.length-2);
					}else{
						return "";
					}
				}
			}];
			
			$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
			 $('.all').addClass('ln-selected');
			 prevLetter='all';
			 $('a','.ln-letters').unbind('click'); 
			$('a','.ln-letters').click(function(){
				  var $this = $(this),
	           letter = $this.attr('class').split(' ')[0];
				  
				  if ( prevLetter !== letter ) {
					  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
					  $this.addClass('ln-selected');
					  prevLetter=letter;
				  }
				  console.log(letter);

					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'code':	key,
							'name':	key,
							'contactName' : key,
							'contactPhone' : key,
						'letter':letter
					};
					excelParams=params;
		           $('[data-role="baseInfoGrid"]').getGrid().search(params);
				
				  
			});
			
			$("#managerSearch").unbind('click'); 
			$("#managerSearch").click(function() {
				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
					'code':	key,
					'name':	key,
					'contactName' : key,
					'contactPhone' : key
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			});
			// 商检单位
			$(".btn-remove").unbind('click'); 
			$(".btn-remove").click(function() {
				var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
				if (data.length == 0) {
					$('[data-role="baseInfoGrid"]').message({
						type : 'warning',
						content : '请选择要撤销的记录'
					});
					return;
				}
				dataGrid = $('[data-role="baseInfoGrid"]');
			    dataGrid.confirm({
					content : '确定要撤销所选记录吗?',
					callBack : function() {
						 $.post(config.getDomain()+'/inspectAgent/delete', "inspectAgentIds="+data).done(function (data) {
					            if (data.code == "0000") {
					                dataGrid.message({
					                    type: 'success',
					                    content: '删除成功'
					                });
					                dataGrid.getGrid().refresh();
					            } else {
					                $('body').message({
					                    type: 'error',
					                    content: data.msg
					                });
					            }
					        }).fail(function (data) {
					            dataGrid.message({
					                type: 'error',
					                content: '删除失败'
					            });
					        });
					}
				});
			});
			/*解决id冲突的问题*/
			$('[data-role="baseInfoGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : true,
				url : config.getDomain()+"/inspectAgent/list"
			});
			
			//初始化按钮操作
			$(".btn-add").unbind('click'); 
			if(!config.hasPermission('AADDBASEINSPECT')){
				$(".btn-add").hide();
			}else{
				$(".btn-add").show();
			}
			
			if(!config.hasPermission('ADELETEBASEINSPECT')){
				$(".btn-remove").hide();
			}else{
				$(".btn-remove").show();
			}
			
			if(!config.hasPermission('AUPDATEBASEINSPECT')){
				$(".modify").hide();
			}else{
				$(".modify").show();
			}
			$(".btn-add").click(function() {
				$.get(config.getResource()+"/pages/auth/baseinfo/inspectAgent/addInspectAgent.jsp").done(function(data){
					dialog = $(data) ;
					
					if(!config.hasPermission('AADDBASEINSPECT')){
						
						dialog.find("#save").hide();
					}
					
					dialog.find("#save").click(function(){
						if(!config.validateForm(dialog.find(".form-body"))){
							return false ;
						}
						$.ajax({
							type : "post",
							url : config.getDomain()+"/inspectAgent/save",
							dataType : "json",
							async:false,
							data:{
								"code":dialog.find("#code").val(),
								"name":dialog.find("#name").val() ,
								"contactName":dialog.find("#contactName").val() ,
								"contactPhone":dialog.find("#contactPhone").val() ,
								"contactEmail":dialog.find("#contactEmail").val() 
							},
							success : function(data) {
								if(data.code=="0000"){
									$("body").message({
										type : 'success',     
										content : '添加船代成功'
									});
									$('[data-role="baseInfoGrid"]').getGrid().refresh();
								}else{
									$("body").message({
										type : 'error',     
										content : '添加船代失败，可能存在同名冲突，请重新尝试。'
									});
								}
							}
						});
					});
					dialog.modal({
						keyboard: true
					});
				});
			});
			
			$(".modify").unbind('click'); 
			$(".modify").click(function() {
				var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
				var $this = $(this);
				if (data.length == 0) {
					$('[data-role="baseInfoGrid"]').message({
						type : 'warning',
						content : '请选择要修改的记录'
					});
					return;
				}
				var id = $('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0].id;
				openInspectAgentDialog(id) ;
			});
			$(".btn-search").unbind('click'); 
			$(".btn-search").click(function() {
				$("#roleManagerQueryDivId").slideToggle("slow");
			});
			$(".search").unbind('click'); 
			$(".search").click(function() {
				var shipArrivalDto = {};
		        $("#intentListForm").find('.form-control').each(function(){
		            var $this = $(this);
		            var name = $this.attr('name');
		             if(name){
		            	 shipArrivalDto[name] = $this.val();
		            }
		        });
		       $('[data-role="baseInfoGrid"]').getGrid().search(shipArrivalDto);
			});
			
		}
		if (item == 8) {
			if ($('[data-role="baseInfoGrid"]').getGrid() != null) {
				$('[data-role="baseInfoGrid"]').getGrid().destory();
			}
			//开证单位
			var columns = [  {
				title : "开证单位简称",
				name : "code",
				render: function(item, name, index){
//					if(config.hasPermission('AUPDATEBASECERTIFY')){
					return "<a href='javascript:void(0)' onClick='BaseInfo.openCertifyDialog("+item.id+")'>"+item.code+"</a>";
//					}else{
//						return item.code;
//					}
					},
			},{
				title : "开证单位全称",
				name : "name"
			}, {
				title : "联系人",
				name : "contactName",
			}, {
				title : "联系人电子邮箱",
				name : "contactEmail",
			}, {
				title : "联系人电话",
				name : "contactPhone",
			},{
				title : "最后编辑人",
				name : "editUserName",
			},
			{
				title : "编辑时间",
				name : "editTime",
				render: function(item, name, index){
					if(item.editTime){
						
						return item.editTime.substring(0,item.editTime.length-2);
					}else{
						return "";
					}
				}
			}];
			
			$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
			 $('.all').addClass('ln-selected');
			 prevLetter='all';
			 $('a','.ln-letters').unbind('click'); 
			$('a','.ln-letters').click(function(){
				  var $this = $(this),
	           letter = $this.attr('class').split(' ')[0];
				  
				  if ( prevLetter !== letter ) {
					  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
					  $this.addClass('ln-selected');
					  prevLetter=letter;
				  }
				  console.log(letter);

					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'code':	key,
							'name':	key,
							'contactName' : key,
							'contactPhone' : key,
						'letter':letter
					};
					excelParams=params;
		           $('[data-role="baseInfoGrid"]').getGrid().search(params);
				
				  
			});
			
			$("#managerSearch").unbind('click'); 
			$("#managerSearch").click(function() {
				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
					'code':	key,
					'name':	key,
					'contactName' : key,
					'contactPhone' : key
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			});
			// 开证单位
			$(".btn-remove").unbind('click'); 
			$(".btn-remove").click(function() {
				var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
				if (data.length == 0) {
					$('[data-role="baseInfoGrid"]').message({
						type : 'warning',
						content : '请选择要撤销的记录'
					});
					return;
				}
				dataGrid = $('[data-role="baseInfoGrid"]');
			    dataGrid.confirm({
					content : '确定要撤销所选记录吗?',
					callBack : function() {
						 $.post(config.getDomain()+'/certify/delete', "ids="+data).done(function (data) {
					            if (data.code == "0000") {
					                dataGrid.message({
					                    type: 'success',
					                    content: '删除成功'
					                });
					                dataGrid.getGrid().refresh();
					            } else {
					                $('body').message({
					                    type: 'error',
					                    content: data.msg
					                });
					            }
					        }).fail(function (data) {
					            dataGrid.message({
					                type: 'error',
					                content: '删除失败'
					            });
					        });
					}
				});
			});
			/*解决id冲突的问题*/
			$('[data-role="baseInfoGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : true,
				url : config.getDomain()+"/certify/list"
			});
			
			//初始化按钮操作
			$(".btn-add").unbind('click'); 
			if(!config.hasPermission('AADDBASECERTIFY')){
				$(".btn-add").hide();
			}else{
				$(".btn-add").show();
			}
			
			if(!config.hasPermission('ADELETEBASECERTIFY')){
				$(".btn-remove").hide();
			}else{
				$(".btn-remove").show();
			}
			
			if(!config.hasPermission('AUPDATEBASECERTIFY')){
				$(".modify").hide();
			}else{
				$(".modify").show();
			}
			$(".btn-add").click(function() {
				$.get(config.getResource()+"/pages/auth/baseinfo/certify/addCertifyCustomer.jsp").done(function(data){
					dialog = $(data) ;
					
					if(!config.hasPermission('AADDBASECERTIFY')){
						
						dialog.find("#save").hide();
					}
					
					dialog.find("#save").click(function(){
						if(!config.validateForm(dialog.find(".form-body"))){
							return false ;
						}
						$.ajax({
							type : "post",
							url : config.getDomain()+"/certify/save",
							dataType : "json",
							async:false,
							data:{
								"code":dialog.find("#code").val(),
								"name":dialog.find("#name").val() ,
								"contactName":dialog.find("#contactName").val() ,
								"contactPhone":dialog.find("#contactPhone").val() ,
								"contactEmail":dialog.find("#contactEmail").val() 
							},
							success : function(data) {
								if(data.code=="0000"){
									$("body").message({
										type : 'success',     
										content : '添加船代成功'
									});
									$('[data-role="baseInfoGrid"]').getGrid().refresh();
									dialog.remove();
								}else{
									$("body").message({
										type : 'error',     
										content : '添加船代失败，可能存在同名冲突，请重新尝试。'
									});
								}
							}
						});
					});
					dialog.modal({
						keyboard: true
					});
				});
			});
			
			$(".modify").unbind('click'); 
			$(".modify").click(function() {
				var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
				var $this = $(this);
				if (data.length == 0) {
					$('[data-role="baseInfoGrid"]').message({
						type : 'warning',
						content : '请选择要修改的记录'
					});
					return;
				}
				var id = $('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0].id;
				openCertifyDialog(id) ;
				
			});
			$(".btn-search").unbind('click'); 
			$(".btn-search").click(function() {
				$("#roleManagerQueryDivId").slideToggle("slow");
			});
			$(".search").unbind('click'); 
			$(".search").click(function() {
				var shipArrivalDto = {};
		        $("#intentListForm").find('.form-control').each(function(){
		            var $this = $(this);
		            var name = $this.attr('name');
		             if(name){
		            	 shipArrivalDto[name] = $this.val();
		            }
		        });
		       $('[data-role="baseInfoGrid"]').getGrid().search(shipArrivalDto);
			});
		}
/************************************clientQualification**********************************************************/		
			if (item == 9) {
			var columns=[{title:"资质名",name:"name",render: function(item, name, index){
//				if(config.hasPermission('AUPDATEBASEQUALIFICATION')){
				return "<a href='javascript:void(0)' onClick='BaseInfo.dialogClientQualification(1,"+index+")'>"+item.name+"</a>";
//				}else{
//					return item.name;
//				}
				},},{title:"描述",name:"description"},{
				title : "最后编辑人",
				name : "editUserName",
			},
			{
				title : "编辑时间",
				name : "editTime",
				render: function(item, name, index){
					if(item.editTime){
						
						return item.editTime.substring(0,item.editTime.length-2);
					}else{
						return "";
					}
				}
			}];
			
			$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
			 $('.all').addClass('ln-selected');
			 prevLetter='all';
			 $('a','.ln-letters').unbind('click'); 
			$('a','.ln-letters').click(function(){
				  var $this = $(this),
	           letter = $this.attr('class').split(' ')[0];
				  
				  if ( prevLetter !== letter ) {
					  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
					  $this.addClass('ln-selected');
					  prevLetter=letter;
				  }
				  console.log(letter);

					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'name':	key,
						'letter':letter
					};
					excelParams=params;
		           $('[data-role="baseInfoGrid"]').getGrid().search(params);
				
				  
			});
			
			$("#managerSearch").unbind('click'); 
			$("#managerSearch").click(function() {
				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
					'name':	key
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			});
			if($('[data-role="baseInfoGrid"]').getGrid()!=null){
				$('[data-role="baseInfoGrid"]').getGrid().destory();
			}
			$('[data-role="baseInfoGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : true,
				url : config.getDomain() + "/qualification/list"
			});
			$(".btn-add,.modify").unbind('click');
			if(!config.hasPermission('AADDBASEQUALIFICATION')){
				$(".btn-add").hide();
			}else{
				$(".btn-add").show();
			}
			if(!config.hasPermission('ADELETEBASEQUALIFICATION')){
				$(".btn-remove").hide();
			}else{
				$(".btn-remove").show();
			}
			if(!config.hasPermission('AUPDATEBASEQUALIFICATION')){
				$(".modify").hide();
			}else{
				$(".modify").show();
			}
			
			$(".btn-add,.modify").click(function(){
				if(item==9){
				$this=$(this);
				if($this.attr("class").indexOf("modify")!=-1){
				if(validateData($('[data-role="baseInfoGrid"]'))){
					dialogClientQualification(1);//修改
				}}else{
				dialogClientQualification(0);//增加
				}
				}});
			$(".btn-remove").unbind('click');
			$(".btn-remove").click(function(){
				if(item==9){
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
							url : config.getDomain() + "/qualification/delete",
							data : {
								"ids":ids
							},
							dataType : "json",
							success:function(data){
								config.unload();
								handleResult(data,2);
								$('[data-role="baseInfoGrid"]').getGrid().refresh();
							}					 
					 });}
				});
				}
				}});
		}
/**********************************berth*******************************************/
		if (item == 10) {
			//泊位资料
			var columns=[{
				title:"泊位",
				name:"name",
				width:100,
				render: function(item, name, index){
//					if(config.hasPermission('AUPDATEBASEBERTH')){
					return "<a href='javascript:void(0)' onClick='BaseInfo.dialogBerth(1,"+index+")'>"+item.name+"</a>";
//					}else{
//						return item.name;
//					}
					},
			},{
				title:"船舶最大长度（米）",
				name:"limitLength",
				width:100,
				render:function(item,name,index){
					return  item.limitLength;
				}
			},{
				title:"泊位长度(米)",
				name:"length",
				width:110,
				render:function(item,name,index){
					return item.length;
				}
			},{
				title:"前沿水深(米)",
				name:"frontDepth",
				width:110,
				render:function(item,name,index){
					return item.frontDepth;
				}
			},{
				title:"船舶最小长度（米）",
				name:"minLength",
				width:110,
				render:function(item,name,index){
					return  util.isNull(item.minLength,1);
				}
			},{
				title:"船舶最大吃水（米）",
				name:"limitDrought",
				width:100,
				render:function(item,name,index){
					return  item.limitDrought;
				}
			},{
				title:"船舶最大载重（吨）",
				name:"limitDisplacement",
				width:130,
				render:function(item,name,index){
					return  item.limitDisplacement;
				}
			},{
				title:"船舶最大排水量（吨）",
				width:130,
				name:"limitTonnage"
			},{
				title:"泊位信息",
				name:"description",
				width:200
			},{
				title:"安全措施",
				name:"safeInfo",
				width:200
			},{
				title : "最后编辑人",
				name : "editUserName",
			},
			{
				title : "编辑时间",
				name : "editTime",
				render: function(item, name, index){
					if(item.editTime){
						
						return item.editTime.substring(0,item.editTime.length-2);
					}else{
						return "";
					}
				}
			}];
			$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
			 $('.all').addClass('ln-selected');
			 prevLetter='all';
			 $('a','.ln-letters').unbind('click'); 
			$('a','.ln-letters').click(function(){
				  var $this = $(this),
	           letter = $this.attr('class').split(' ')[0];
				  
				  if ( prevLetter !== letter ) {
					  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
					  $this.addClass('ln-selected');
					  prevLetter=letter;
				  }
				  console.log(letter);

					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'name':	key,
						'letter':letter
					};
					excelParams=params;
		           $('[data-role="baseInfoGrid"]').getGrid().search(params);
				
				  
			});
			$("#managerSearch").unbind('click'); 
			$("#managerSearch").click(function() {
				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
					'name':	key
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			});
			if($('[data-role="baseInfoGrid"]').getGrid()!=null){
				$('[data-role="baseInfoGrid"]').getGrid().destory();
			}
			$('[data-role="baseInfoGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : false,
				pageSize: 7,
				url : config.getDomain() + "/berth/list"
			});
			$(".btn-add,.modify").unbind('click');
			if(!config.hasPermission('AADDBASEBERTH')){
				$(".btn-add").hide();
			}else{
				$(".btn-add").show();
			}
			if(!config.hasPermission('ADELETEBASEBERTH')){
				$(".btn-remove").hide();
			}else{
				$(".btn-remove").show();
			}
			if(!config.hasPermission('AUPDATEBASEBERTH')){
				$(".modify").hide();
			}else{
				$(".modify").show();
			}
			
			$(".btn-add,.modify").click(function(){
				if(item==10){
				$this=$(this);
				if($this.attr("class").indexOf("modify")!=-1){
					if(validateData($('[data-role="baseInfoGrid"]'))){
						dialogBerth(1);//修改
					}}else{
				dialogBerth(0);//增加
				}
				}});
			$(".btn-remove").unbind('click');
			$(".btn-remove").click(function(){
				if(item==10){
				if(validateData($('[data-role="baseInfoGrid"]'),1)){
					var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
				 var ids="";	
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
							url : config.getDomain() + "/berth/delete",
							data : {
								"ids":ids
							},
							dataType : "json",
							success:function(data){
								config.unload();
								handleResult(data,2);
								$('[data-role="baseInfoGrid"]').getGrid().refresh();
							}					 
					 });}
				});
				}
				}});
			
		}
/***********************tube****************************************/
		if (item == 11) {
			//管线资料 
			var columns=[{
				title:"管名",
				name:"name",
				render: function(item, name, index){
//					if(config.hasPermission('AUPDATEBASETUBE')){
					return "<a href='javascript:void(0)' onClick='BaseInfo.dialogTube(1,"+index+")'>"+item.name+"</a>";
//					}else{
//						return item.name;
//					}
					},
			},{
				title:"类型",
				name:"type",
				render:function(item,name,index){
					if(item.type==0){
						return "主管线";
					}else {
						return "过渡管线";
					}
				}
			},{
				title:"前期存储物料",
				name:"productName"
			},{
				title:"使用情况",
				name:"description"
			},{
				title : "最后编辑人",
				name : "editUserName",
			},
			{
				title : "编辑时间",
				name : "editTime",
				render: function(item, name, index){
					if(item.editTime){
						
						return item.editTime.substring(0,item.editTime.length-2);
					}else{
						return "";
					}
				}
			},{
				title:"清洗记录",
				render:function(item,name,index){
					return '<a class="btn btn-xs blue" href="javascript:void(0)" onClick="BaseInfo.dialogCleanLog('+item.id+',2)">'
					+'<span class="fa  fa-list" title="详细" style="margin-top: 1px; top: 1px; margin-bottom: 1px;"></span></a>';
				}
			}];
			$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
			 $('.all').addClass('ln-selected');
			 prevLetter='all';
			 $('a','.ln-letters').unbind('click'); 
			$('a','.ln-letters').click(function(){
				  var $this = $(this),
	           letter = $this.attr('class').split(' ')[0];
				  
				  if ( prevLetter !== letter ) {
					  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
					  $this.addClass('ln-selected');
					  prevLetter=letter;
				  }
				  console.log(letter);

					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'name':	key,
							'productName':	key,
						'letter':letter
					};
					excelParams=params;
		           $('[data-role="baseInfoGrid"]').getGrid().search(params);
				
				  
			});
			$("#managerSearch").unbind('click'); 
			$("#managerSearch").click(function() {
				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
					'name':	key,
					'productName':	key
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			});
			if($('[data-role="baseInfoGrid"]').getGrid()!=null){
				$('[data-role="baseInfoGrid"]').getGrid().destory();
			}
			$('[data-role="baseInfoGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : true,
				url : config.getDomain() + "/tube/list"
			});
			$(".btn-add,.modify").unbind('click');
			if(!config.hasPermission('AADDBASETUBE')){
				$(".btn-add").hide();
			}else{
				$(".btn-add").show();
			}
			if(!config.hasPermission('ADELETEBASETUBE')){
				$(".btn-remove").hide();
			}else{
				$(".btn-remove").show();
			}
			if(!config.hasPermission('AUPDATEBASETUBE')){
				$(".modify").hide();
			}else{
				$(".modify").show();
			}
			
			$(".btn-add,.modify").click(function(){
				if(item==11){
				$this=$(this);
				if($this.attr("class").indexOf("modify")!=-1){
					if(validateData($('[data-role="baseInfoGrid"]'))){
						dialogTube(1);//修改
					}
				}else{
				dialogTube(0);//增加
				}
				}});
			$(".btn-remove").unbind('click');
			$(".btn-remove").click(function(){
				if(item==11){
				var data = $('[data-role="baseInfoGrid"]').getGrid().selectedRowsIndex();
				isOK=true;
				if (data.length == 0) {
				$("body").message({
				type : 'warning',
				content : '请选择要删除的管线'
				});
				isOK=false;
				return;
				}
				if(isOK){
				 var ids="";	
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
							url : config.getDomain() + "/tube/delete",
							data : {
								"ids":ids
							},
							dataType : "json",
							success:function(data){
								config.unload();
								handleResult(data,2);
								$('[data-role="baseInfoGrid"]').getGrid().refresh();
							}					 
					 });}
				});
				}
				}});
		}
/********************park****************************************/
		if (item == 12) {
			//车位资料
			var columns=[{title:"序号",render:function(item,name,index){
//				if(config.hasPermission('AUPDATEBASEPARK')){
				return "<a href='javascript:void(0)' onClick='BaseInfo.dialogPark(1,"+index+")'>"+(index+1)+"</a>";
//				}else{
//					return index+1;
//				}
				}},{title:"名称",name:"name"},{
				title:"货品名",
				name:"productName"
			},{title:"关联泵号",name:"pumpName"},{
				title:"使用情况",
				name:"description"
			},{
				title : "最后编辑人",
				name : "editUserName",
			},
			{
				title : "编辑时间",
				name : "editTime",
				render: function(item, name, index){
					if(item.editTime){
						
						return item.editTime.substring(0,item.editTime.length-2);
					}else{
						return "";
					}
				}
			},];
			
			$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
			 $('.all').addClass('ln-selected');
			 prevLetter='all';
			 $('a','.ln-letters').unbind('click'); 
			$('a','.ln-letters').click(function(){
				  var $this = $(this),
	           letter = $this.attr('class').split(' ')[0];
				  
				  if ( prevLetter !== letter ) {
					  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
					  $this.addClass('ln-selected');
					  prevLetter=letter;
				  }
				  console.log(letter);

					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'name':	key,
						'letter':letter
					};
					excelParams=params;
		           $('[data-role="baseInfoGrid"]').getGrid().search(params);
				
				  
			});
			
			$("#managerSearch").unbind('click'); 
			$("#managerSearch").click(function() {
				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
					'name':	key
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			});
			if($('[data-role="baseInfoGrid"]').getGrid()!=null){
				$('[data-role="baseInfoGrid"]').getGrid().destory();
			}
			$('[data-role="baseInfoGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : true,
				url : config.getDomain() + "/park/list"
			});
			$(".btn-add,.modify").unbind('click');
			if(!config.hasPermission('AADDBASEPARK')){
				$(".btn-add").hide();
			}else{
				$(".btn-add").show();
			}
			if(!config.hasPermission('ADELETEBASEPARK')){
				$(".btn-remove").hide();
			}else{
				$(".btn-remove").show();
			}
			if(!config.hasPermission('AUPDATEBASEPARK')){
				$(".modify").hide();
			}else{
				$(".modify").show();
			}
			
			$(".btn-add,.modify").click(function(){
				if(item==12){
				$this=$(this);
				if($this.attr("class").indexOf("modify")!=-1){
					if(validateData($('[data-role="baseInfoGrid"]'))){
						dialogPark(1);//修改
					}
				}else{
				dialogPark(0);//增加
				}
				}});
			$(".btn-remove").unbind('click');
			$(".btn-remove").click(function(){
				if(item==12){
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
							url : config.getDomain() + "/park/delete",
							data : {
								"ids":ids
							},
							dataType : "json",
							success:function(data){
								config.unload();
								handleResult(data,2);
								$('[data-role="baseInfoGrid"]').getGrid().refresh();
							}					 
					 });}
				});
				}
				}});
		}
		/*******************************port*********************************************/
		else if(item==13){
			//
			var columns=[{title:"编号",name:"code",render: function(item, name, index){
//				if(config.hasPermission('AUPDATEBASEPORT')){
				return "<a href='javascript:void(0)' onClick='BaseInfo.dialogPort(1,"+index+")'>"+item.code+"</a>";
//				}else{
//					return item.code;
//				}
				},},{title:"名称",name:"name"},{title:"描述",name:"description"},{
				title : "最后编辑人",
				name : "editUserName",
			},
			{
				title : "编辑时间",
				name : "editTime",
				render: function(item, name, index){
					if(item.editTime){
						
						return item.editTime.substring(0,item.editTime.length-2);
					}else{
						return "";
					}
				}
			},];
			
			$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
			 $('.all').addClass('ln-selected');
			 prevLetter='all';
			 $('a','.ln-letters').unbind('click'); 
			$('a','.ln-letters').click(function(){
				  var $this = $(this),
	           letter = $this.attr('class').split(' ')[0];
				  
				  if ( prevLetter !== letter ) {
					  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
					  $this.addClass('ln-selected');
					  prevLetter=letter;
				  }
				  console.log(letter);

					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'name':	key,
							'code':	key,
						'letter':letter
					};
					excelParams=params;
		           $('[data-role="baseInfoGrid"]').getGrid().search(params);
				
				  
			});
			
			$("#managerSearch").unbind('click'); 
			$("#managerSearch").click(function() {
				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
					'code':	key,
					'name':key
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			});
			if($('[data-role="baseInfoGrid"]').getGrid()!=null){
				$('[data-role="baseInfoGrid"]').getGrid().destory();
			}
			$('[data-role="baseInfoGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : true,
				url : config.getDomain() + "/port/list"
			});
			$(".btn-add,.modify").unbind('click');
			if(!config.hasPermission('AADDBASEPORT')){
				$(".btn-add").hide();
			}else{
				$(".btn-add").show();
			}
			
			if(!config.hasPermission('ADELETEBASEPORT')){
				$(".btn-remove").hide();
			}else{
				$(".btn-remove").show();
			}
			if(!config.hasPermission('AUPDATEBASEPORT')){
				$(".modify").hide();
			}else{
				$(".modify").show();
			}
			
			$(".btn-add,.modify").click(function(){
				if(item==13){
				$this=$(this);
				if($this.attr("class").indexOf("modify")!=-1){
					if(validateData($('[data-role="baseInfoGrid"]'))){
						dialogPort(1);//修改
					}
				}else{
				dialogPort(0);//增加
				}
				}});
			$(".btn-remove").unbind('click');
			$(".btn-remove").click(function(){
				if(item==13){
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
							url : config.getDomain() + "/port/delete",
							data : {
								"ids":ids
							},
							dataType : "json",
							success:function(data){
								config.unload();
								handleResult(data,2);
								$('[data-role="baseInfoGrid"]').getGrid().refresh();
							}					 
					 });}
				});
				}
				}});
		}
		/************************tank*************************************/
		else if(item==14){
			var columns=[{title:"罐号",name:"code",render: function(item, name, index){
//				if(config.hasPermission('AUPDATEBASETANK')){
				return "<a href='javascript:void(0)' onClick='BaseInfo.dialogTank(1,"+index+")'>"+item.code+"</a>";
				
//				}else{
//					return item.code;
//				}
				},},{title:"货品",name:"productName"},{title:"所属泵棚",name:"pumpShedName"},{title:"最大储存量(吨)",name:"capacityTotal",
	            	 render: function(item, name, index){
							if(item.capacityTotal){
								
								return parseFloat(item.capacityTotal).toFixed(3);
							}else{
								return "";
							}
						}},
			             {title:"实际储存量(吨)",name:"capacityCurrent",
	            	 render: function(item, name, index){
							if(item.capacityCurrent){
								
								return parseFloat(item.capacityCurrent).toFixed(3);
							}else{
								return "";
							}
						}},{title:"可储容量(吨)",name:"capacityFree",
			            	 render: function(item, name, index){
								if(item.capacityFree){
									
									return parseFloat(item.capacityFree).toFixed(3);
								}else{
									return "";
								}
							}},{title:"使用情况",name:"description"},{
			 				title : "最后编辑人",
							name : "editUserName",
						},
						{
							title : "编辑时间",
							name : "editTime",
							render: function(item, name, index){
								if(item.editTime){
									
									return item.editTime.substring(0,item.editTime.length-2);
								}else{
									return "";
								}
							}
						},
			             {
			 				title:"清洗记录",
			 				render:function(item,name,index){
			 					return '<a class="btn btn-xs blue" href="javascript:void(0)" onClick="BaseInfo.dialogCleanLog('+item.id+',1)">'
			 					+'<span class="fa  fa-list" title="详细" style="margin-top: 1px; top: 1px; margin-bottom: 1px;"></span></a><a class="btn btn-xs blue" href="javascript:void(0)" onClick="BaseInfo.dialogCleanLog('+item.id+',3)">'
			 					+'<span class="fa  fa-list" title="详细" style="margin-top: 1px; top: 1px; margin-bottom: 1px;"></span></a>';
			 				}
			 			}];
			if($('[data-role="baseInfoGrid"]').getGrid()!=null){
				$('[data-role="baseInfoGrid"]').getGrid().destory();
			}
			$('[data-role="baseInfoGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : true,
				autoLoad:false,
				url : config.getDomain() + "/tank/list"
			});
			var key = $("#queryUserDivId").find('.search-key').val();
			var params = {
					'code':key,
					'name':	key,
					'productName':	key,
//					'type':1,
//				'letter':'T'
			};
			excelParams=params;
			$('[data-role="baseInfoGrid"]').getGrid().search(params);
			
			$(".showVir").unbind('click'); 
			$(".showVir").click(function(){
				if(!$(".showVir").hasClass("blue")){
					$(".showVir").addClass("blue");
					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'code':key,
							'name':	key,
							'productName':	key,
//						'letter':'',
						'type':1
					};
					excelParams=params;
					$('[data-role="baseInfoGrid"]').getGrid().search(params);
		           
				}else{
					
					$(".showVir").removeClass("blue");

					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'code':key,
							'name':	key,
							'productName':	key,
//							'type':1,
//							'letter':'T'
					};
					excelParams=params;
					$('[data-role="baseInfoGrid"]').getGrid().search(params);
				}
			});
			
			$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
			 $('.all').addClass('ln-selected');
			 prevLetter='all';
			 $('a','.ln-letters').unbind('click'); 
			$('a','.ln-letters').click(function(){
				  var $this = $(this),
	           letter = $this.attr('class').split(' ')[0];
				  
				  if ( prevLetter !== letter ) {
					  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
					  $this.addClass('ln-selected');
					  prevLetter=letter;
				  }
				  console.log(letter);

					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'code':key,
							'name':	key,
							'productName':	key,
						'letter':letter
					};
					excelParams=params;
		           $('[data-role="baseInfoGrid"]').getGrid().search(params);
				
				  
			});
			
			$("#managerSearch").unbind('click'); 
			$("#managerSearch").click(function() {
				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
					'code':	key,
					'name':key,
					'productName':key
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			});
			$(".btn-add,.modify").unbind('click');
			if(!config.hasPermission('AADDBASETANK')){
				$(".btn-add").hide();
			}else{
				$(".btn-add").show();
			}
			if(!config.hasPermission('ADELETEBASETANK')){
				$(".btn-remove").hide();
			}else{
				$(".btn-remove").show();
			}
			if(!config.hasPermission('AUPDATEBASETANK')){
				$(".modify").hide();
			}else{
				$(".modify").show();
			}
			
			$(".btn-add,.modify").click(function(){
				if(item==14){
				$this=$(this);
				if($this.attr("class").indexOf("modify")!=-1){
					if(validateData($('[data-role="baseInfoGrid"]'))){
						dialogTank(1);//修改
					}
				}else{
				dialogTank(0);//增加
				}
				}});
			$(".btn-remove").unbind('click');
			$(".btn-remove").click(function(){
				if(item==14){
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
					content : '确定要撤销所选记录吗?此操作会同步删除该储罐台账所有记录',
					callBack : function() {
						config.load();
					 $.ajax({
						 type : "post",
							url : config.getDomain() + "/tank/delete",
							data : {
								"ids":ids
							},
							dataType : "json",
							success:function(data){
								config.unload();
								handleResult(data,2);
								$('[data-role="baseInfoGrid"]').getGrid().refresh();
							}					 
					 });}
				});
				}
				}});
		}
		if (item == 15) {
			//泵资料
			var columns=[{title:"名称",name:"name",render:function(item,name,index){
//				if(config.hasPermission('AUPDATEBASEPUMP')){
				return "<a href='javascript:void(0)' onClick='BaseInfo.dialogPump(1,"+index+")'>"+item.name+"</a>";
//				}else{
//					return item.name;
//				}
				}},{title:"泵棚",name:"pumpShedName"},{
				title:"前期存储物料",
				name:"productName"
			},{title:"泵性质",render:function(item){
				if(item.type==1){
					return "船发泵";
				}else if(item.type==2){
					return "车发泵 ";
				}else {
					return "";
				}
				
			}},{
				title:"使用情况",
				name:"description"
			},{
 				title : "最后编辑人",
				name : "editUserName",
			},
			{
				title : "编辑时间",
				name : "editTime",
				render: function(item, name, index){
					if(item.editTime){
						
						return item.editTime.substring(0,item.editTime.length-2);
					}else{
						return "";
					}
				}
			}];
			$('a.ln-selected', '.ln-letters').removeClass('ln-selected');
			 $('.all').addClass('ln-selected');
			 prevLetter='all';
			 $('a','.ln-letters').unbind('click'); 
			$('a','.ln-letters').click(function(){
				  var $this = $(this),
	           letter = $this.attr('class').split(' ')[0];
				  
				  if ( prevLetter !== letter ) {
					  $('a.ln-selected', '.ln-letters').removeClass('ln-selected');
					  $this.addClass('ln-selected');
					  prevLetter=letter;
				  }
				  console.log(letter);

					var key = $("#queryUserDivId").find('.search-key').val();
					var params = {
							'name':	key,
						'letter':letter
					};
					excelParams=params;
		           $('[data-role="baseInfoGrid"]').getGrid().search(params);
				
				  
			});
			$("#managerSearch").unbind('click'); 
			$("#managerSearch").click(function() {
				var key = $("#queryUserDivId").find('.search-key').val();
				var params = {
					'name':	key
				};
				excelParams=params;
	           $('[data-role="baseInfoGrid"]').getGrid().search(params);
			});
			if($('[data-role="baseInfoGrid"]').getGrid()!=null){
				$('[data-role="baseInfoGrid"]').getGrid().destory();
			}
			$('[data-role="baseInfoGrid"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : true,
				isShowPages : true,
				url : config.getDomain() + "/pump/list"
			});
			$(".btn-add,.modify").unbind('click');
			if(!config.hasPermission('AADDBASEPUMP')){
				$(".btn-add").hide();
			}else{
				$(".btn-add").show();
			}
			
			if(!config.hasPermission('ADELETEBASEPUMP')){
				$(".btn-remove").hide();
			}else{
				$(".btn-remove").show();
			}
			
			if(!config.hasPermission('AUPDATEBASEPUMP')){
				$(".modify").hide();
			}else{
				$(".modify").show();
			}
			$(".btn-add,.modify").click(function(){
				if(item==15){
				$this=$(this);
				if($this.attr("class").indexOf("modify")!=-1){
					if(validateData($('[data-role="baseInfoGrid"]'))){
						dialogPump(1);//修改
					}
				}else{
					dialogPump(0);//增加
				}
				}});
			$(".btn-remove").unbind('click');
			$(".btn-remove").click(function(){
				if(item==15){
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
							url : config.getDomain() + "/pump/delete",
							data : {
								"ids":ids
							},
							dataType : "json",
							success:function(data){
								config.unload();
								handleResult(data,2);
								$('[data-role="baseInfoGrid"]').getGrid().refresh();
							}					 
					 });}
				});
				}
				}});
		}else if(item == 16){
			PumpShed.init();
		}
	};
	
	function  dialogClientQualification(item,index){
		 $.get(config.getResource()+ "/pages/auth/baseinfo/clientqualification/dialog_clientqualification.jsp")
			.done(function(data) {
				initFormIput();
			var	dialog = $(data);
			//初始化修改
			if(item==1){

				if(!config.hasPermission('AUPDATEBASEQUALIFICATION')){
					
					dialog.find("#save").hide();
				}
				var data ;
				if(typeof index=="number"){
					data =$('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(index) ;
				}else{
					
					data=$('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0];
				}
                 dialog.find("#name").val(data.name);				
                 dialog.find("#description").val(data.description);	
				 dialog.find("#id").val(data.id);
			}else{
				if(!config.hasPermission('AADDBASEQUALIFICATION')){
					
					dialog.find("#save").hide();
				}
			}
				dialog.find("#save").click(function(){
					if(config.validateForm(dialog.find(".form-horizontal"))){
					var url="";
					if(item==1){
						url=config.getDomain() + "/qualification/update";
					}else{
						url=config.getDomain() + "/qualification/add";
					}
					config.load();
					$.ajax({
						type : "post",
						url : url,
						data : {
							"id" : dialog.find("#id").val(),
							"name":dialog.find("#name").val(),
							"description":dialog.find("#description").val()
						},
						dataType : "json",
						success:function(data){
							config.unload();
							handleResult(data,item);
							if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
								util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:dialog.find("#id").val(),url:'/qualification/list'});
							
//							$('[data-role="baseInfoGrid"]').getGrid().refresh();
							dialog.remove();
						}
					});
					}
				});
				dialog.modal({
					keyboard : true
				});
			});
	};
	
	function  dialogBerth(item,index){
		 $.get(config.getResource()+ "/pages/auth/baseinfo/clientqualification/dialog_berth.jsp")
			.done(function(data) {
				initFormIput();
			var	dialog = $(data);
			//初始化修改
			if(item==1){
				
				if(!config.hasPermission('AUPDATEBASEBERTH')){
					
					dialog.find("#save").hide();
				}
				
				
				var data ;
				if(typeof index=="number"){
					data =$('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(index) ;
				}else{
					
					data=$('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0];
				}
                 dialog.find("#name").val(data.name);				
				 dialog.find("#limitLength").val(data.limitLength);
				 dialog.find("#length").val(data.length);
				 dialog.find("#frontDepth").val(data.frontDepth);
				 dialog.find("#minLength").val(data.minLength);
				 dialog.find("#limitDrought").val(data.limitDrought);
				 dialog.find("#limitDisplacement").val(data.limitDisplacement);
				 dialog.find("#limitTonnage").val(data.limitTonnage);
				 dialog.find("#description").val(data.description);
				 dialog.find("#safeInfo").val(data.safeInfo);
				 dialog.find("#id").val(data.id);
			}else{
				if(!config.hasPermission('AADDBASEBERTH')){
					
					dialog.find("#save").hide();
				}

			}
				dialog.find("#save").click(function(){
					if(config.validateForm(dialog.find(".form-horizontal"))){
					var url="";
					if(item==1){
						url=config.getDomain() + "/berth/update";
					}else{
						url=config.getDomain() + "/berth/add";
					}
					config.load();
					$.ajax({
						type : "post",
						url : url,
						data : {
							"id" : dialog.find("#id").val(),
							"name":dialog.find("#name").val(),
							"limitLength":dialog.find("#limitLength").val(),
							"length":dialog.find("#length").val(),
							"frontDepth":dialog.find("#frontDepth").val(),
							"minLength":dialog.find("#minLength").val(),
							"limitDrought":dialog.find("#limitDrought").val(),
							"limitDisplacement":dialog.find("#limitDisplacement").val(),
							"limitTonnage":dialog.find("#limitTonnage").val(),
							"description":dialog.find("#description").val(),
							"safeInfo":dialog.find("#safeInfo").val()
							},
						dataType : "json",
						success:function(data){
							config.unload();
							handleResult(data,item);
							if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
								util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:dialog.find("#id").val(),url:'/berth/list'});
							
//							$('[data-role="baseInfoGrid"]').getGrid().refresh();
							dialog.remove();
						}
					});
					}
				});
				dialog.modal({
					keyboard : true
				});
			});
	};
	
	
	function  dialogTube(item,index){
		 $.get(config.getResource()+ "/pages/auth/baseinfo/clientqualification/dialog_tube.jsp")
			.done(function(data) {
				initFormIput();
			var	dialog = $(data);
			//初始化修改
			if(item==1){
				
				if(!config.hasPermission('AUPDATEBASETUBE')){
					
					dialog.find("#save").hide();
				}
				
				var data ;
				if(typeof index=="number"){
					data =$('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(index) ;
				}else{
					
					data=$('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0];
				}
             dialog.find("#name").val(data.name);
                	 dialog.find("#type").val(data.type);
				 dialog.find("#productId").val(data.productName);
				 dialog.find("#productId").attr("data",data.productId);
				 dialog.find("#description").val(data.description);
				 dialog.find("#id").val(data.id);
			}else{
				
				if(!config.hasPermission('AADDBASETUBE')){
					
					dialog.find("#save").hide();
				}
				
			}
			//货品
			$.ajax({
	  			type : "get",
	  			url : config.getDomain()+"/product/select",
	  			dataType : "json",
	  			success : function(data) {
	  				config.unload();
	  				//货品加载
	  				dialog.find('#productId').typeahead({
	  					source: function(query, process) {
	  						var results = _.map(data.data, function (product) {
	  	                        return product.name;
	  	                    });
	  	                    process(results);
	  					},
	  					noselect: function (query) {
		  					var item = _.find(data.data, function (p) {
		                        return p.name == query;
		                    });
		  					if(item==null){
		  						dialog.find('#productId').removeAttr("data");
		  						dialog.find('#productId').val("");
		  					}else{
		  						dialog.find('#productId').attr("data",item.id)
		  					}
		  					
		  				}
	  				});
	  			}
			});
				dialog.find("#save").click(function(){
					if(config.validateForm(dialog.find(".form-horizontal"))){
					var url="";
					if(item==1){
						url=config.getDomain() + "/tube/update";
					}else{
						url=config.getDomain() + "/tube/add";
					}
					config.load();
					$.ajax({
						type : "post",
						url : url,
						data : {
							"id" : dialog.find("#id").val(),
							"name":dialog.find("#name").val(),
							"type":dialog.find("#type").val(),
							"productId":dialog.find("#productId").attr("data"),
							"description":dialog.find("#description").val()
							},
						dataType : "json",
						success:function(data){
							config.unload();
							handleResult(data,item);
							if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
								util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:dialog.find("#id").val(),url:'/tube/list'});
							
//							$('[data-role="baseInfoGrid"]').getGrid().refresh();
							dialog.remove();
						}
					});
					}
				});
				dialog.modal({
					keyboard : true
				});
			});
	};
	function  dialogPump(item,index){
		 $.get(config.getResource()+ "/pages/auth/baseinfo/pump/addPump.jsp")
			.done(function(data) {
				initFormIput();
			var	dialog = $(data);
			//初始化修改
			if(item==1){
				
				if(!config.hasPermission('AUPDATEBASEPUMP')){
					
					dialog.find("#save").hide();
				}
				
				console.log(index);
				var data ;
				if(typeof index=="number"){
					data =$('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(index) ;
				}else{
					
					data=$('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0];
				}
                dialog.find("#name").val(data.name);
				 dialog.find("#productId").val(data.productName);
				 dialog.find("#pumpShedName").val(util.isNull(data.pumpShedName)).attr('data',data.pumpShedId);
				 dialog.find("#productId").attr("data",data.productId);
				 dialog.find("#description").val(data.description);
				 if(data.type)
				 dialog.find("#type").val(data.type);
				 dialog.find("#id").val(data.id);
			}else{
				if(!config.hasPermission('AADDBASEPUMP')){
					
					dialog.find("#save").hide();
				}
				
			}
			util.urlHandleTypeaheadAllData("/product/select",dialog.find('#productId'));
			util.urlHandleTypeaheadAllData("/pumpshed/list",dialog.find('#pumpShedName'));
				dialog.find("#save").click(function(){
					if(config.validateForm(dialog.find(".form-horizontal"))){
					var url="";
					if(item==1){
						url=config.getDomain() + "/pump/update";
					}else{
						url=config.getDomain() + "/pump/add";
					}
					config.load();
					$.ajax({
						type : "post",
						url : url,
						data : {
							"id" : dialog.find("#id").val(),
							"name":dialog.find("#name").val(),
							"productId":dialog.find("#productId").attr("data"),
							"pumpShedId":dialog.find("#pumpShedName").attr('data'),
							"type":dialog.find("#type").val(),
							"description":dialog.find("#description").val()
							},
						dataType : "json",
						success:function(data){
							config.unload();
							handleResult(data,item);
							if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
								util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:dialog.find("#id").val(),url:'/pump/list'});
							
//							$('[data-role="baseInfoGrid"]').getGrid().refresh();
							dialog.remove();
						}
					});
					}
				});
				dialog.modal({
					keyboard : true
				});
			});
	};
	
	
	function  dialogPark(item,index){
		 $.get(config.getResource()+ "/pages/auth/baseinfo/clientqualification/dialog_park.jsp")
			.done(function(data) {
				initFormIput();
			var	dialog = $(data);
			//初始化修改
			if(item==1){
				
				if(!config.hasPermission('AUPDATEBASEPARK')){
					
					dialog.find("#save").hide();
				}
				
				
				var data ;
				if(typeof index=="number"){
					data =$('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(index) ;
				}else{
					
					data=$('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0];
				}
                 dialog.find("#name").val(data.name);
				 dialog.find("#productId").val(data.productName);
				 dialog.find("#productId").attr("data",data.productId);
				 dialog.find("#description").val(data.description);
				 dialog.find("#id").val(data.id);
			}else{
				
				if(!config.hasPermission('AADDBASEPARK')){
					
					dialog.find("#save").hide();
				}
				
			}
			//货品
			$.ajax({
	  			type : "get",
	  			url : config.getDomain()+"/product/select",
	  			dataType : "json",
	  			success : function(data) {
	  				config.unload();
	  				//货品加载
	  				dialog.find('#productId').typeahead({
	  					source: function(query, process) {
	  						var results = _.map(data.data, function (product) {
	  	                        return product.name;
	  	                    });
	  	                    process(results);
	  					},
	  					noselect: function (query) {
		  					var item = _.find(data.data, function (p) {
		                        return p.name == query;
		                    });
		  					if(item==null){
		  						dialog.find('#productId').removeAttr("data");
		  						dialog.find('#productId').val("");
		  					}else{
		  						dialog.find('#productId').attr("data",item.id)
		  					}
		  					
		  				}
	  				});
	  			}
			});
				dialog.find("#save").click(function(){
					if(config.validateForm(dialog.find(".form-horizontal"))){
					var url="";
					if(item==1){
						url=config.getDomain() + "/park/update";
					}else{
						url=config.getDomain() + "/park/add";
					}
					config.load();
					$.ajax({
						type : "post",
						url : url,
						data : {
							"id" : dialog.find("#id").val(),
							"name":dialog.find("#name").val(),
							"productId":dialog.find("#productId").attr("data"),
							"description":dialog.find("#description").val()
							},
						dataType : "json",
						success:function(data){
							config.unload();
							handleResult(data,item);
							if($('[data-role="baseInfoGrid"]').getGrid()!=undefined){
								console.log(dialog.find("#id").val());
								util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:dialog.find("#id").val(),url:'/park/list'});
							}
//							$('[data-role="baseInfoGrid"]').getGrid().refresh();
							dialog.remove();
						}
					});
					}
				});
				dialog.modal({
					keyboard : true
				});
			});
	};
	
	function dialogPort(item,index){
		 $.get(config.getResource()+ "/pages/auth/baseinfo/clientqualification/dialog_port.jsp")
			.done(function(data) {
				initFormIput();
			var	dialog = $(data);
			//初始化修改
			if(item==1){
				
				

				if(!config.hasPermission('AUPDATEBASEPORT')){
					
					dialog.find("#save").hide();
				}
				
				var data ;
				if(typeof index=="number"){
					data =$('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(index) ;
				}else{
					
					data=$('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0];
				}
				 dialog.find("#code").val(data.code);
                 dialog.find("#name").val(data.name);
                 dialog.find("#description").val(data.description);
				 dialog.find("#id").val(data.id);
			}else{

				if(!config.hasPermission('AADDBASEPORT')){
					
					dialog.find("#save").hide();
				}
			}
				dialog.find("#save").click(function(){
					if(config.validateForm(dialog.find(".form-horizontal"))){
					var url="";
					if(item==1){
						url=config.getDomain() + "/port/update";
					}else{
						url=config.getDomain() + "/port/add";
					}
					config.load();
					$.ajax({
						type : "post",
						url : url,
						data : {
							"id" : dialog.find("#id").val(),
							"name":dialog.find("#name").val(),
							"code":dialog.find("#code").val(),
							"description":dialog.find("#description").val()
							},
						dataType : "json",
						success:function(data){
							config.unload();
							handleResult(data,item);
							if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
								util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:dialog.find("#id").val(),url:'/port/list'});
							
//							$('[data-role="baseInfoGrid"]').getGrid().refresh();
							dialog.remove();
						}
					});
					}
				});
				dialog.modal({
					keyboard : true
				});
			});
	}
	
	function dialogTank(item,index){
		 $.get(config.getResource()+ "/pages/auth/baseinfo/tank/addTank.jsp")
			.done(function(data) {
				initFormIput();
			var	dialog = $(data);
			//初始化修改
			if(item==1){
				
				if(!config.hasPermission('AUPDATEBASETANK')){
					
					dialog.find("#save").hide();
				}
				
				var data ;
				if(typeof index=="number"){
					data =$('[data-role="baseInfoGrid"]').getGrid().getItemByIndex(index) ;
				}else{
					
					data =$('[data-role="baseInfoGrid"]').getGrid().selectedRows()[0];
				}
				 dialog.find("#code").val(data.code);
                //dialog.find("#name").val(data.name);
                dialog.find("#type").val(data.type);
                dialog.find("#productId").val(data.productName).attr("data",data.productId);
                dialog.find("#capacityTotal").val(data.capacityTotal);
                dialog.find("#pumpShedName").val(util.isNull(data.pumpShedName)).attr('data',data.pumpShedId);
                dialog.find("#capacityCurrent").val(data.capacityCurrent);
                dialog.find("#capacityFree").val(data.capacityFree);
                dialog.find("#description").val(data.description);
                dialog.find("#testDensity").val(data.testDensity);
                dialog.find("#testTemperature").val(data.testTemperature);
                dialog.find("#tankTemperature").val(data.tankTemperature);
				dialog.find("#KEY").val(data.key);
				dialog.find("#id").val(data.id);
				if(data.isVir==1){
					dialog.find("#isVir").attr("checked",true);
				}
			}else{
				
				if(!config.hasPermission('AADDBASETANK')){
					
					dialog.find("#save").hide();
				}
				
			}
			util.urlHandleTypeaheadAllData("/product/select",dialog.find('#productId'));
			util.urlHandleTypeaheadAllData("/pumpshed/list",dialog.find('#pumpShedName'));
				dialog.find("#save").click(function(){
					if(config.validateForm(dialog.find(".form-horizontal"))){
					var url="";
					if(item==1){
						url=config.getDomain() + "/tank/update";
					}else{
						url=config.getDomain() + "/tank/add";
					}
					config.load();
					
					var capacityTotal = dialog.find("#capacityTotal").val() ;
					var capacityCurrent = dialog.find("#capacityCurrent").val() ;
					var capacityFree = util.FloatSub(capacityTotal,capacityCurrent);
					var isVir=0;
					if(dialog.find("#isVir").is(':checked')){
						isVir=1;
					}
					$.ajax({
						type : "post",
						url : url,
						data : {
							"id" : dialog.find("#id").val(),
							"name":dialog.find("#name").val(),
							"code":dialog.find("#code").val(),
							"type":dialog.find("#type").val(),
							"pumpShedId":dialog.find("#pumpShedName").attr('data'),
							"description":dialog.find("#description").val(),
							"capacityTotal":capacityTotal,
							"capacityCurrent":capacityCurrent,
							"capacityFree":capacityFree,
							"productId":dialog.find("#productId").attr("data"),
							"testDensity":dialog.find("#testDensity").val(),
							"testTemperature":dialog.find("#testTemperature").val(),
							"tankTemperature":dialog.find("#tankTemperature").val(),
							"key":dialog.find("#KEY").val(),
							"isVir":isVir
							},
						dataType : "json",
						success:function(data){
							config.unload();
							handleResult(data,item);
							if($('[data-role="baseInfoGrid"]').getGrid()!=undefined)
								util.updateGridRow($('[data-role="baseInfoGrid"]'),{id:dialog.find("#id").val(),url:'/tank/list'});
							
//							$('[data-role="baseInfoGrid"]').getGrid().refresh();
							dialog.remove();
						}
					});
					}
				});
				dialog.modal({
					keyboard : true
				});
			});
	}
	
	function msgContent(item,isSucceed){
		if(isSucceed){
			if(item==0){
				return "添加成功";
			}else if(item==1){
				return "修改成功";
			}else if(item==2){
				return "删除成功";
			}
		}else{
			if(item==0){
				return "添加失败，可能存在同名冲突，请重新尝试。";
			}else if(item==1){
				return "修改失败";
			}else if(item==2){
				return "删除失败";
			} 			
		}
	}
	//校验数据的长度
	function validateData(obj,item){
		var data = $(obj).getGrid().selectedRowsIndex();
		var isOK=true;
		if (data.length == 0) {
		$("body").message({
		type : 'warning',
		content : '请选择要处理的选项'
		});
		isOK=false;
		}else if(data.length>1){
			if(item!=1){//不校验大于1的情况
			$('body').message({
				type:"warning",
				content:'只能选择一个选项'
			});
			isOK=false;
			}
		}
		return isOK;
	};
	//处理异步请求的结果
	function handleResult(data,item){
		if(data.code=='0000'){
			$('body').message({
				type:'success',
				content:msgContent(item,true)	
			});
		}else{
			$('body').message({
				type:'error',
				content:msgContent(item,false)	
			});
		}
	}
	function deleteRefInfo(obj){
		var id = $(obj).parents(".shipRef").find("input[name=id]").val();
		if(id){
			$.ajax({
				type : "post",
				url :config.getDomain()+"/ship/deleteShipRefInfo",
				data : {
					"shipRefId" : id,
					},
				dataType : "json",
				success:function(data){
					if(data.code=='0000'){
						$('body').message({
							type:'success',
							content:msgContent(2,true)	
						});
					}else{
						$('body').message({
							type:'error',
							content:msgContent(2,false)	
						});
					}
				}
			});
		}
		$(obj).parents(".shipRef").remove() ;
	};
	function dialogCleanLog(id,type){
		 $.get(config.getResource()+ "/pages/auth/baseinfo/cleanLog.jsp")
			.done(function(data) {
				initFormIput();
			var	dialog = $(data);
			if(type==1){//储罐
                dialog.find("#title").text("储罐清洗记录");
    			var columns=[{title:"罐号",name:"tankCode"},{title:"货品",name:"productName"},{title:"使用情况",name:"description"},
    			             {title:"修改人",name:"editUserName"},{title:"修改时间",name:"editTime"}];
    			dialog.find("#clean").grid({
    				identity : 'id',
    				columns : columns,
    				isShowIndexCol : false,
    				isShowPages : true,
    				url : config.getDomain() + "/cleanlog/tanklist?tankId="+id
    			});
                
				
			}else if(type==2){//管线
				dialog.find("#title").text("管线清洗记录");
				var columns=[{title:"管线名",name:"tubeName"},{title:"货品",name:"productName"},{title:"使用情况",name:"description"},
    			             {title:"修改人",name:"editUserName"},{title:"修改时间",name:"editTime"}];
    			dialog.find("#clean").grid({
    				identity : 'id',
    				columns : columns,
    				isShowIndexCol : false,
    				isShowPages : true,
    				url : config.getDomain() + "/cleanlog/tubelist?tubeId="+id
    			});
				
			}  else if(type==3){
				var columns=[{title:"罐号",name:"code"},{title:"货品",name:"productName"},{title:"最大储存量(吨)",name:"capacityTotal",
		            	 render: function(item, name, index){
								if(item.capacityTotal){
									
									return parseFloat(item.capacityTotal).toFixed(3);
								}else{
									return "";
								}
							}},
				             {title:"实际储存量(吨)",name:"capacityCurrent",
		            	 render: function(item, name, index){
								if(item.capacityCurrent){
									
									return parseFloat(item.capacityCurrent).toFixed(3);
								}else{
									return "";
								}
							}},{title:"可储容量(吨)",name:"capacityFree",
				            	 render: function(item, name, index){
									if(item.capacityFree){
										
										return parseFloat(item.capacityFree).toFixed(3);
									}else{
										return "";
									}
								}},{title:"使用情况",name:"description"},{
				 				title : "最后编辑人",
								name : "editUserName",
							},
							{
								title : "编辑时间",
								name : "editTime",
								render: function(item, name, index){
									if(item.editTime){
										
										return item.editTime.substring(0,item.editTime.length-2);
									}else{
										return "";
									}
								}
							}];
				dialog.find("#clean").grid({
    				identity : 'id',
    				columns : columns,
    				isShowIndexCol : false,
    				isShowPages : true,
    				url : config.getDomain() + "/tank/updatelogList"
    			});
			}
				dialog.modal({
					keyboard : true
				});
			});
	};
	return {
		changetab : changetab,
		init : init,
		deleteRefInfo:function(obj){
			deleteRefInfo(obj);
		},
		openClientInGroup:function(groupId){
			openClientInGroup(groupId);
		},
		addTruck:function(){
			addTruck() ;
		},
		dialogCleanLog:dialogCleanLog,
		openClientGroupDialog:function(obj){
			openClientGroupDialog(obj) ;
			initFormIput();
		},
		openClientDialog:function(obj,callback){
			openClientDialog(obj,callback) ;
		},
		openProductDialog:function(obj){
			openProductDialog(obj) ;
		},
		openCargoAgentDialog:function(obj){
			openCargoAgentDialog(obj) ;
		},
		openShipDialog:function(obj){
			openShipDialog(obj) ;
		},
		openShipAgentDialog:function(id){
			openShipAgentDialog(id) ;
		},
		openTruckDialog:function(id){
			openTruckDialog(id) ;
		},
		openInspectAgentDialog:function(id){
			openInspectAgentDialog(id) ;
		},
		openCertifyDialog:function(id){
			openCertifyDialog(id) ;
		},
		dialogClientQualification:function(f,index){
			dialogClientQualification(f,index) ;
		},
		dialogBerth:function(f,index){
			dialogBerth(f,index) ;
		},
		dialogTube:function(f,index){
			dialogTube(f,index) ;
		},
		dialogPark:function(f,index){
			dialogPark(f,index) ;
		},
		dialogPort:function(f,index){
			dialogPort(f,index) ;
		},
		dialogTank:function(f,index){
			dialogTank(f,index) ;
		},
		dialogPump:function(f,index){
			dialogPump(f,index) ;
		},
		exportExcel:exportExcel,
		deleteFile:deleteFile,
		openClientFileDialog:openClientFileDialog
	}

}();