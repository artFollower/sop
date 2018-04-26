var Intent = function() {
	
	var dialog 	= null;    		//对话框
	var dataGrid 	= null; 		//Grid对象
	

	function doSubmit(id,status){
		var mId=id;
		$(this).confirm({
			content : '确定要进行操作吗?',
			callBack : function() {
				if(id==0){
					id=$(".inputId").val();
				}
				config.load();
				var reviewTime="";
				if(status==2){
					reviewTime=new Date();
				}
				$.ajax({
					type : "post",
					url : config.getDomain()+"/orderInstent/updateStatus",
					data:{
						"id":id,
						"status":status,
						"reviewTime":reviewTime
					},
					dataType : "json",
					success : function(data) {
						config.unload();
						if (data.code == "0000") {
							$("body").message({
								type : 'success',
								content : '操作成功'
							});
							if(mId==0){
//								window.location.href = "#/intentGet?id="+id;
								initEdit(id);
							}else{
								$('[data-role="intentGrid"]').grid('refresh');
							}
						}else {
							$("body").message({
								type : 'error',
								content : '操作失败'
							});
						}
					}
				});
			}
		});
	}
	
	function toContract(){
		window.location.href = "#/contractAdd?intentId="+$(".inputId").val();
	}
	
	//初始化修改界面
	function initEdit(id){
		config.load();
		$.ajax({
			type : "get",
			url : config.getDomain()+"/orderInstent/list?id="+id,
			dataType : "json",
			success : function(data) {
				config.unload();
				var intent=data.data[0];
				$(".inputId").val(id);
				
				$(".editTime").val(intent.editTime);
				
				$(".code").val(intent.code);
				$(".intentTitle").val(intent.title);
				$(".quantity").val(intent.quantity);
				$(".storagePrice").val(intent.storagePrice);
				$(".passPrice").val(intent.passPrice);
				$(".overtimePrice").val(intent.overtimePrice);
				$(".portSecurityPrice").val(intent.portSecurityPrice);
				$(".portServicePrice").val(intent.portServicePrice);
				$(".otherPrice").val(intent.otherPrice);
				$(".lossRate").val(intent.lossRate);
				$(".period").val(intent.period);
				$(".totalPrice").val(intent.totalPrice);
				$(".description").val(intent.description);
				$(".type").val(intent.typeName);
				$(".type").attr("data",intent.type);
//				$(".productId").val(intent.productName);
//				$(".productId").attr("data",intent.productId);
				$("#productId").attr("value",intent.productIdList);
				$(".clientId").val(intent.clientName);
				$(".clientId").attr("data",intent.clientId);
				$(".clientGroupId").val(intent.clientGroupName);
				$(".createUserName").val(intent.createUserName);
				$(".createUserName").attr("data",intent.createUserId);
				$(".createTime").val(intent.mCreateTime);
				$(".checkUser").val(intent.reviewUserName);
				$(".checkTime").val(intent.mReviewTime);
				if(intent.status==0){
					$(".passButton").hide();
					$(".contractButton").hide();
					$(".saveButton").show();
					$(".submitButton").show();
					$(".check").hide();
				}
				if(intent.status==1){
					$(".saveButton").hide();
					$(".submitButton").hide();
					$(".contractButton").hide();
					$(".passButton").show();
					$(".check").hide();
				}else if(intent.status==2){
					$(".saveButton").hide();
					$(".submitButton").hide();
					$(".passButton").hide();
					$(".contractButton").show();
					$(".check").show();
				}
				
				
				initSelect();
				
			}
		});
	}
	
	//计算总价
	function addNum(obj,x){
		config.clearNoNum(obj,x);
		
		var storagePrice=parseFloat(checkNum($(".storagePrice").val()));
		var passPrice=parseFloat(checkNum($(".passPrice").val()));
		var portSecurityPrice=parseFloat(checkNum($(".portSecurityPrice").val()));
		var portServicePrice=parseFloat(checkNum($(".portServicePrice").val()));
		var quantity=parseFloat(checkNum($(".quantity").val()));
		var otherPrice=parseFloat(checkNum($(".otherPrice").val()));
		if(parseFloat($(".lossRate").val())>1000){
			$(".lossRate").val(1000);
		}
//		$(".totalPrice").val(storagePrice*quantity+passPrice*quantity);
		$(".totalPrice").val(util.FloatAdd(util.FloatMul(storagePrice,quantity),util.FloatMul(passPrice,quantity)).toFixed(2));
		
	}
	
	
	function checkNum(obj){
	if(obj==""){
		return 0;
	}else{
		return obj;
	}
	}
	
	//初始化select标签
	var initAddSelection=function(){
		
		config.load();
//		$.ajax({
//			async:false,
//			type : "get",
//			url : config.getDomain()+"/product/select",
//			dataType : "json",
//			success : function(data) {
//				$('#productId').typeahead({
//  					source: function(query, process) {
//  						var results = _.map(data.data, function (product) {
//  	                        return product.name;
//  	                    });
//  	                    process(results);
//  					},
//  					//移除控件时调用的方法
//  				noselect:function(query){
//  					var client = _.find(data.data, function (p) {
//                        return p.name == query;
//                    });
//  					//匹配不到就去掉id，让内容变空，否则给id
//  					if(client==null){
//  						$('#productId').removeAttr("data");
//  						$('#productId').val("");
//  					}else{
//  						$('#productId').attr("data",client.id)
//  					}
//  				}
//  				});
//			}
//		});
		
		
		 $.ajax({
			 async:false,
	  			type : "get",
	  			url : config.getDomain()+"/client/select",
	  			dataType : "json",
	  			success : function(data) {
	  				$('#clientId').typeahead({
	  					source: function(query, process) {
	  						var results = _.map(data.data, function (product) {
	  	                        return product.name;
	  	                    });
	  	                    process(results);
	  					},
	  					//移除控件时调用的方法
	  				noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.name == query;
	                    });
	  					//匹配不到就去掉id，让内容变空，否则给id
	  					if(client==null){
	  						$('#clientId').removeAttr("data");
	  						$('#clientId').val("");
	  					}else{
	  						$('#clientId').attr("data",client.id)
	  					}
	  				}
	  				});
	  			}
		 

	  		});
		
		
		 $.ajax({
			 async:false,
	  			type : "get",
	  			url : config.getDomain()+"/clientgroup/list",
	  			dataType : "json",
	  			success : function(data) {
	  				$('#clientGroupId').typeahead({
	  					source: function(query, process) {
	  						var results = _.map(data.data, function (product) {
	  	                        return product.name;
	  	                    });
	  	                    process(results);
	  					},
	  				updater: function (item) {
	  					var client = _.find(data.data, function (p) {
	                        return p.name == item;
	                    });
	  					if(client!=null){
	  						
	  					//根据客户组id查询该组客户
	  					config.load();
	  					$.ajax({
				  			type : "get",
				  			url : config.getDomain()+"/client/select?clientGroupId="+client.id,
				  			dataType : "json",
				  			success : function(data) {
				  				config.unload();
				  				$('#clientId').typeahead('hide');
				  				$('#clientId').remove();
				  				$('#client').append("<input id='clientId' type='text' data-provide='typeahead' data-required='1' data-type='Require' class='form-control clientId'>");
				  				$('#clientId').typeahead({
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
				  						$('#clientId').removeAttr("data");
				  						$('#clientId').val("");
				  					}else{
				  						$('#clientId').attr("data",client.id)
				  					}
				  				}
				  				});
				  			}
				  		});
	  					}
	  					return item;
	  				},
	  				noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.name == query;
	                    });
	  					if(client==null){
	  						$('#clientGroupId').val("");
	  					//根据客户组id查询该组客户
	  						config.load();
		  					$.ajax({
					  			type : "get",
					  			url : config.getDomain()+"/client/select?clientGroupId",
					  			dataType : "json",
					  			success : function(data) {
					  				config.unload();
					  				$('#clientId').typeahead('hide');
					  				$('#clientId').remove();
					  				$('#client').append("<input id='clientId' type='text' data-provide='typeahead' data-required='1' data-type='Require' class='form-control clientId'>");
					  				$('#clientId').typeahead({
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
					  						$('#clientId').removeAttr("data");
					  						$('#clientId').val("")
					  					}else{
					  						$('#clientId').attr("data",client.id)
					  					}
					  				}
					  				});
					  			}
					  		});
	  				}
	  			}
		 
	  		});
	  			}
		 });
		
		
		
//		$.ajax({
//			type : "get",
//			url : config.getDomain()+"/client/select",
//			dataType : "json",
//			success : function(data) {
//				var htm="<select class='form-control clientId' data-placeholder='选择客户...' name='clientId' data-live-search='true'>";
//				for(var i=0;i<data.data.length;i++){
//					htm+="<option value="+data.data[i].id+">"+data.data[i].name+"</option>";
//				}
//				htm+="</select>";
//				$(".clientId").remove();
//				$("#select-client").append(htm);
//			}
//		});
		
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/contractType/select",
			dataType : "json",
			success : function(data) {
				config.unload();
				$('#type').typeahead({
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
  						$('#type').removeAttr("data");
  						$('#type').val("");
  					}else{
  						$('#type').attr("data",client.key);
  					}
  				}
  				});
			}
		});
		
		
	}
	
	
	
	
	var add = function(grid) {
		dataGrid = grid;
		$.get(config.getResource()+"/pages/contract/intent/add.jsp").done(function(data){
			dialog = $(data);
			dialog.find('#save').on('click',function(){
				$(this).attr('disabled', 'disabled');
				save();
			}).end().modal({
				keyboard: false
			}).on({
				'hidden.bs.modal': function(){
					$(this).remove();
				},
				'complete': function(){
					dataGrid.message({
						type: 'success',
						content: '保存成功'
					});
					$(this).modal('hide');
					dataGrid.grid('refresh');
				}
			});
		});
		
	};
	
	//保存合同意向
	var save = function(status) {
		var isOk = false;
		if(config.validateForm($(".savecheck"))){
				isOk=true;
		}
		if(isOk) {
			config.load();
			var productNameList="";
			for(var i=0;i<$("#productId").select2("data").length;i++){
				productNameList+=$("#productId").select2("data")[i].text+",";
			}
			productNameList=productNameList.substring(0,productNameList.length-1);
			$.ajax({
				type : "post",
				url : config.getDomain()+"/orderInstent/save",
				data : {
					"title" : $(".intentTitle").val(),
					"type" : $(".type").attr("data"),
					"clientId" : $(".clientId").attr("data"),
//					"clientGroupId" : $(".clientGroupId").attr("data"),
//					"productId" : $(".productId").attr("data"),
					"productNameList" : productNameList,
					"productIdList":$("#productId").val(),
					"quantity" : $(".quantity").val(),
					"storagePrice" : $(".storagePrice").val(),
					"passPrice" : $(".passPrice").val(),
					"overtimePrice" : $(".overtimePrice").val(),
					"portSecurityPrice" : $(".portSecurityPrice").val(),
					"portServicePrice" : $(".portServicePrice").val(),
					"otherPrice" : $(".otherPrice").val(),
					"lossRate" : $(".lossRate").val(),
					"period" : $(".period").val(),
					"totalPrice" : $(".totalPrice").val(),
					"description" : $(".description").val(),
					"status" : status
				},
				// data:JSON.stringify(sendGroup),
				dataType : "json",
				success : function(data) {
					config.unload();
					if (data.code == "0000") {
						$("body").message({
							type : 'success',
							content : '订单意向添加成功'
						});
						
						window.location.href = "#/intentGet?id="+data.map.id;
					}else {
						$("body").message({
							type : 'error',
							content : '订单意向添加失败'
						});
					}
				}
			});
		}
	};
	
	
	//修改合同意向
	var update = function(status) {
		var isOk = false;
		if(config.validateForm($(".intent-update-form"))){
			isOk=true;
		}
		if(isOk) {
			config.load();
			var productNameList="";
			for(var i=0;i<$("#productId").select2("data").length;i++){
				productNameList+=$("#productId").select2("data")[i].text+",";
			}
			productNameList=productNameList.substring(0,productNameList.length-1);
			var reviewTime="";
			if(status==2){
				reviewTime=new Date().Format("yyyy-MM-dd hh:mm:ss");
			}
			$.ajax({
				type : "post",
				url : config.getDomain()+"/orderInstent/update",
				data : {
					"id" : $(".inputId").val(),
					"title" : $(".intentTitle").val(),
					"code" : $(".code").val(),
					"type" : $(".type").attr("data"),
					"clientId" : $(".clientId").attr("data"),
//					"productId" : $(".productId").attr("data"),
					"productNameList" : productNameList,
					"productIdList":$("#productId").val(),
					
					"quantity" : $(".quantity").val(),
					"storagePrice" : $(".storagePrice").val(),
					"passPrice" : $(".passPrice").val(),
					"overtimePrice" : $(".overtimePrice").val(),
					"portSecurityPrice" : $(".portSecurityPrice").val(),
					"portServicePrice" : $(".portServicePrice").val(),
					"otherPrice" : $(".otherPrice").val(),
					"lossRate" : $(".lossRate").val(),
					"period" : $(".period").val(),
					"totalPrice" : $(".totalPrice").val(),
					"description" : $(".description").val(),
					"status" : status,
					"tReviewTime": reviewTime,
					
					"mEditTime":$(".editTime").val()
				},
				// data:JSON.stringify(sendGroup),
				dataType : "json",
				success : function(data) {
					config.unload();
					if (data.code == "0000") {
						$("body").message({
							type : 'success',
							content : '订单意向修改成功'
						});
						window.location.href = "#/instent";
					}else if(data.code=="1003"){
						$("body").message({
							type: 'error',
							content: '数据冲突，请刷新界面！'
						});
					}else {
						$("body").message({
							type : 'error',
							content : '订单意向修改失败'
						});
					}
				}
			});
		}
	}
	
	//单条删除
	var deleteItem =function (id){
		var data = $('[data-role="intentGrid"]').getGrid().selectedRowsIndex();
		var $this = $('[data-role="intentGrid"]');
		
		$this.confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
//				deleteIntent($('[data-role="contractGrid"]').getGrid().selectedRows(), $this);
				
				var data = "";
		            data += ("intentionIds=" +id);
		        
		        $.post(config.getDomain()+'/orderInstent/delete', data).done(function (data) {
		            if (data.code == "0000") {
		            	$('[data-role="intentGrid"]').message({
		                    type: 'success',
		                    content: '删除成功'
		                });
		                $('[data-role="intentGrid"]').grid('refresh');
		            } else {
		                $('body').message({
		                    type: 'error',
		                    content: data.msg
		                });
		            }
		        }).fail(function (data) {
		        	$('[data-role="intentGrid"]').message({
		                type: 'error',
		                content: '删除失败'
		            });
		        });
				
			}
		});
	}
	
	var initSelect=function(){
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/product/select",
			dataType : "json",
			success : function(data) {
				var array1=new Array();
				for(var i=0;i<data.data.length;i++){
					array1.push({id:data.data[i].id,text:data.data[i].name});
				}
				$("#productId").select2({
		            tags: function(query){
		            	return array1;
		            	
		            }
				});
			}
		});
	}
	
	
	//多条删除
	var deleteIntent = function(intents, grid) {
        dataGrid = grid;

        var data = "";
        $.each(intents, function (i, role) {
            data += ("intentionIds=" + role.id + "&");
        });
        data = data.substring(0, data.length - 1);
        
        $.post(config.getDomain()+'/orderInstent/delete', data).done(function (data) {
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

	var handleRecords = function() {
		var columns = [ {
			title : "意向号",
			name : "code",
			width:80,
			render: function(item, name, index){
				return "<a href='#/intentGet?id="+item.id+"'>"+item.code+"</a>";
			}
		}, {
			title : "标题",
			name : "title",
			width:200
		}, {
			title : "货品",
			name : "productNameList",
			width:170
		},{
			title : "类型",
			name : "typeName",
			width:50
		}, {
			title : "客户",
			name : "clientName",
			width:200
		}, {
			title : "预计数量(吨)",
			name : "quantity",
			width:75
		},
		{
			title : "预计总价(元)",
			name : "totalPrice",
			width:75
		}, {
			title : "状态",
			name : "statusName",
			width:70,
			render: function(item, name, index){
				switch (item.status) {
				case 0:
					return "<span style='color:orange'>"+item.statusName+"</span>";
					break;
				case 1:
					return "<span style='color:blue'>"+item.statusName+"</span>";
					break;
				case 2:
					return "<span style='color:green'>"+item.statusName+"</span>";
					break;
				case 4:
					return "<span style='color:red'>"+item.statusName+"</span>";
					break;
				}
			}
		}, {
			title : "操作",
			name : "id",
			width:120,
			render: function(item, name, index){
				if(item.status==0){
					return "<shiro:hasPermission name='AINTENTUPDATE'><a href='#/intentGet?id="+item.id+"'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a></shiro:hasPermission>"+
					"<shiro:hasPermission name='AINTENTDELETE'><a href='javascript:void(0)' onclick='Intent.deleteItem("+item.id+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a></shiro:hasPermission>"+
					"<shiro:hasPermission name='AINTENTUPDATE'><a href='javascript:void(0)' onclick='Intent.doSubmit("+item.id+",1)' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-send' title='提交'></span></a></shiro:hasPermission>";
				}else if(item.status==1){
					return "<shiro:hasPermission name='AINTENTUPDATE'><a href='#/intentGet?id="+item.id+"'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-eye-open' title='查看'></span></a></shiro:hasPermission>"+
					"<shiro:hasPermission name='AINTENTVERIFY'><a href='javascript:void(0)' onclick='Intent.doSubmit("+item.id+",2)' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-check' title='确认'></span></a></shiro:hasPermission>";
				}else if(item.status==4){
					return "<shiro:hasPermission name='AINTENTUPDATE'><a href='#/intentGet?id="+item.id+"'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-eye-open' title='查看'></span></a></shiro:hasPermission>";
				}else {
					return "<shiro:hasPermission name='AINTENTUPDATE'><a href='#/intentGet?id="+item.id+"'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-eye-open' title='查看'></span></a></shiro:hasPermission>"+
					"<shiro:hasPermission name='ACHANGETOCONTRACT'><a href='#/contractAdd?intentId="+item.id+"'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-file' title='转为合同'></span></a></shiro:hasPermission>";
				}
			
			}
		}  ];

		/*解决id冲突的问题*/
		$('[data-role="intentGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			gridName:'intent',
			stateSave:true,
			url : config.getDomain()+"/orderInstent/list"
		});
		
		//初始化按钮操作
		$(".btn-add").unbind('click'); 
		$(".btn-add").click(function() {
			window.location.href = "#/intentAdd";
		});
		$(".btn-remove").unbind('click'); 
		$(".btn-remove").click(function() {
			var data = $('[data-role="intentGrid"]').getGrid().selectedRowsIndex();
			var $this = $('[data-role="intentGrid"]');
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}
			
			var cando=true;
			 $.each($('[data-role="intentGrid"]').getGrid().selectedRows(), function (i, role) {
		        	if(role.status>0){
		        		$this.confirm({
		    				content : '存在无法删除的合同意向！！请重新选择！',
		    				concel:true,
		    				callBack : function() {
		    				}
		    			});
		        		cando=false;
		        		return false;
		        	}
		        });
			 if(cando){
				 $this.confirm({
					 content : '确定要撤销所选记录吗?',
					 callBack : function() {
						 deleteIntent($('[data-role="intentGrid"]').getGrid().selectedRows(), $this);
					 }
				 });
			 }
			
		});
		$(".btn-modify").unbind('click'); 
		$(".btn-modify").click(function() {
			var data = $('[data-role="intentGrid"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要修改的记录'
				});
				return;
			}
			dataGrid = $(this);
//			$.get(config.getResource()+"/pages/contract/intent/edit.jsp").done(function(_data){
//				dataGrid = _data;
//			});
			window.location.href = "#/intentGet?id="+$('[data-role="intentGrid"]').getGrid().selectedRows()[0].id;
		});
		$(".btn-search").unbind('click'); 
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		
	};

	return {
		init : function() {
			initFormIput();
			handleRecords();
		},
		search : function() {
			var params = {};
            $("#intentListForm").find('.form-control').each(function(){
                var $this = $(this);
                var name = $this.attr('name');
                 if(name){
                    params[name] = $this.val();
                }
            });
           $('[data-role="intentGrid"]').getGrid().search(params);
		},
		doAdd : function(status) {
			save(status);
		},
		doEdit : function(status) {
			update(status);
		},
		doDelete : function() {
			
		},
		initAdd:function(){
			initFormIput();
			initAddSelection();
		},
		addNum : function(obj,x){
			addNum(obj,x);
		},
		initEdit : function(id){
			initEdit(id);
		},
		deleteItem : function(id){
			deleteItem(id);
		},
		doSubmit : function(id,status){
			doSubmit(id,status);
		},
		toContract : function(){
			toContract();
		},
		initSelect:function(){
			initSelect();
		}
	};
	
	
	
}();