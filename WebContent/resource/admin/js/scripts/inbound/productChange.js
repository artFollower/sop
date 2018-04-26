var ProductChange = function() {
	
	var dialog 	= null;    		//对话框
	var dataGrid 	= null; 		//Grid对象
    var mType=1;
	var goodsTotal=0;
	var mDialog=null;

	
	
	var handleRecords = function() {
		util.urlHandleTypeahead("/client/select",$("#clientId"));

		
		var columns = [ {
			title : "货批编号",
			name : "cargodata",
			render: function(item, name, index){
				return "<a href='javascript:void(0)' onclick='CargoLZ.openCargoLZ("
				+ item[name].id
				+ ")' >"
				+ item[name].code + "</a>";
				}
		}, {
			title : "货主",
			name : "cargodata",
				render: function(item, name, index){
					return item[name].clientName;
				}
		}, {
			title : "货品名称",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].productName;
			}
		},{
			title : "总量(吨)",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].goodsTotal;
			}
		}, {
			title : "内控放行数(吨)",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].goodsOutPass;
			}
		}, 
		{
			title : "说明",
			name : "cargodata",
			render: function(item, name, index){
				return item[name].description;
			}
		}, 
		{
			title : "操作",
			name : "cargodata",
			render: function(item, name, index){
				var re="<a href='javascript:void(0)' onclick='ProductChange.editCargo("+index+")' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a>";
				if(item[name].goodsOutPass==0){
					re+="<a href='javascript:void(0)' onclick='CargoPK.deleteCargo("+item[name].id+","+item[name].code+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a>"
						
				}
				//				if(item[name].fileUrl){
//					re+="<a href='javascript:void(0)' onclick='Storage.openFileModal("+item[name].id+")' class='btn btn-xs blue'><span class='fa fa-arrow-up   ' title='上传文件'></span></a><a href='"+getRootPath()+item.fileUrl+"' onclick='' class='btn btn-xs blue'><span class='fa fa-arrow-down   ' title='查看文件'></span></a>";
//					
//				}else{
//					re+="<a href='javascript:void(0)' onclick='Storage.openFileModal("+item[name].id+")' class='btn btn-xs blue'><span class='fa fa-arrow-up   ' title='上传文件'></span></a>";
//					
//				}
//				re+="<a href='javascript:void(0)' onclick='VirtualCargo.Grafting("+item[name].id+")' class='btn btn-xs blue'><span class='fa  fa-cut   ' title='嫁接'>";
//				
             	return re;
			}
		}  ];

		
		/*解决id冲突的问题*/
		$('[data-role="virtualGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			url : config.getDomain()+"/storageConfirm/list?cargoStatus=5"
		});
		
		
//		//初始化按钮操作
//		$(".btn-modify").unbind('click'); 
//		$(".btn-modify").click(function() {
//			window.location.href = "#/storageEdit?id="+$('[data-role="virtualGrid"]').getGrid().selectedRows()[0].cargodata.id;
//		});
		$(".btn-search").unbind('click'); 
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		
		
	
		
		
	
		
		
		
	};
	
	
	
	
	
	
	
	
	function openModal(){
//		$('#ajax').load(config.getResource()+"/pages/inbound/arrival/addCustomer.jsp");
//		$('#ajax').modal('show');
		$.get(config.getResource()+"/pages/inbound/productChange/addCustomer.jsp").done(function(data){
			dialog = $(data) ;
			initDialog(dialog);
			
			
			//添加货批
			dialog.find("#button-add-goods").click(function() {
				var isOk = false;
				if(config.validateForm(dialog.find(".form-horizontal"))){
						config.load();
						$.ajax({
							type : "post",
							url : config.getDomain() + "/arrivalPlan/addCargo",
							data : {
								'arrivalId' : 2,
								'clientId' : dialog.find(".clientId").attr("data"),
								'productId' : dialog.find(".productId").attr("data"),
								'goodsTotal' : dialog.find(".productAmount").val(),
								'taxType' : dialog.find(".tradeType").val(),
								'code' : dialog.find(".code").val(),
								'contractId' : dialog.find(".order").attr("data"),
								'tankId': dialog.find(".tankId").attr("data"),
								'status':5
							},
							dataType : "json",
							success : function(data) {
								config.unload();
								if (data.code == "0000") {
									$("body").message({
					                    type: 'success',
					                    content: '货批添加成功'
					                });
									dialog.remove();
									 $('[data-role="virtualGrid"]').grid('refresh');
								} else {
									$("body").message({
					                    type: 'error',
					                    content: '货批添加失败,请检查是否重号'
					                });
								}
							}
						});
					}
			});
			
			
			dialog.modal({
				keyboard: true
			});
		});
	}

	
	

	//删除货批
	function deleteCargo(id,code){

		$.get(config.getResource()+"/pages/approve/approveReason.jsp").done(function(data){
			dialog = $(data) ;
			initApproveModal(dialog,"/storageConfirm/deleteCargo?cargoId="+id,code);
			dialog.modal({
				keyboard: true
			});
		});
		
		
	}
	
	var searchDialog = function(dialog) {
		
		var params = {};
	    dialog.find("#approveForm").find('.form-control').each(function(){
	        var $this = $(this);
	        var name = $this.attr('name');
	         if(name){
	        	 if($this.attr("data")){
	        		 params[name] = $this.attr("data");
	        	 }
	        }
	    });
	    dialog.find('[data-role="approveGrid"]').getGrid().search(params);
	};
	function initApproveModal(dialog,url,code){
		
		
		var content="";
		//删除货批
			content="删除货批【"+code+"】";
			dialog.find("#approveText").text(content);
		
		$.ajax({
				type : "get",
				url : config.getDomain()+"/auth/role/get",
				dataType : "json",
				success : function(data) {
					dialog.find('.department').typeahead({
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
	  						dialog.find('.department').attr("data",client.id)
	  					}
	  					searchDialog(dialog);
	  					return item;
						},
					//移除控件时调用的方法
					noselect:function(query){
						var client = _.find(data.data, function (p) {
	                    return p.name == query;
	                });
						//匹配不到就去掉id，让内容变空，否则给id
						if(client==null){
							dialog.find('.department').removeAttr("data");
							dialog.find('.department').val("");
							searchDialog(dialog);
						}else{
							dialog.find('.department').attr("data",client.id);
						}
					}
					});
				}
			});
		
		var columns = [{
			title : "姓名",
			name : "name"
		},
		{
			title : "部门",
			name : "roleName"
		}];
		dialog.find('[data-role="approveGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain()+"/auth/role/getUser"
		});
		
		
		dialog.find(".button-ok").click(function(){
			
			
			
			
			var reviewId="";
			var co=0;
			$.each(dialog.find('[data-role="approveGrid"]').getGrid().selectedRows(), function (i, role) {
				co=i;
				reviewId=role.userId;
	        });
			if(co>0){
				 $("body").message({
					 type : 'error',
					 content : '只能选择一个审批人！'
				 });
				 return;
			}
			$.ajax({
	  			type : "post",
	  			data:{
	  				url:url,
	  				reviewUserId:reviewId,
	  				content:content,
	  				status:1,
	  				reason:dialog.find("#reason").val(),
	  				part:2
	  			},
	  			url : config.getDomain()+"/approve/save",
	  			dataType : "json",
	  			success : function(data) {
					 if (data.code == "0000") {
						 $("body").message({
							 type : 'success',
							 content : '申请成功'
						 });
//						 dialog.remove();
					 }else {
						 $("body").message({
							 type : 'error',
							 content : '申请失败'
						 });
					 }
				 }
	  			});
			dialog.remove();
			 
		});
	}
	
	
	
	//编辑货批
	function editCargo(index){
		$.get(config.getResource()+"/pages/inbound/productChange/editCustomer.jsp").done(function(data){
			dialog = $(data) ;
			initDialog(dialog);

			var data=$('[data-role="virtualGrid"]').getGrid().getAllItems()[index].cargodata;
			
			dialog.find(".clientId").attr("data",data.clientId);
			dialog.find(".productId").attr("data",data.productId);
			dialog.find(".productAmount").val(data.goodsTotal);
			dialog.find(".tradeType").val(data.taxType);
			dialog.find(".code").val(data.code),
			dialog.find(".order").attr("data",data.contractId);
			
			dialog.find(".clientId").val(data.clientName);
			dialog.find(".productId").val(data.productName);
			dialog.find(".order").val(data.contractCode);
			
			
			//修改货批
			dialog.find("#button-add-goods").click(function() {
				var isOk = false;
				if(config.validateForm(dialog.find(".form-horizontal"))){
						config.load();
						$.ajax({
							type : "post",
							url : config.getDomain() + "/arrivalPlan/updateCargo",
							data : {
								'clientId' : dialog.find(".clientId").attr("data"),
								'productId' : dialog.find(".productId").attr("data"),
								'goodsTotal' : dialog.find(".productAmount").val(),
								'taxType' : dialog.find(".tradeType").val(),
								'code' : dialog.find(".code").val(),
								'contractId' : dialog.find(".order").attr("data"),
								'id' : data.id
							},
							dataType : "json",
							success : function(data) {
								config.unload();
								if (data.code == "0000") {
									$("body").message({
										type: 'success',
										content: '货批修改成功'
									});
									dialog.remove();
									
									$('[data-role="virtualGrid"]').grid('refresh');
								}else if(data.code=="1006"){
									$(this).confirm({
					    				content : '该货批下已存在交易过的货体，故无法修改货主，请退回货物后方可修改。',
					    				concel:true,
					    				callBack : function() {
					    				}
					    			});
								} else {
									$("body").message({
										type: 'error',
										content: '货批修改失败'
									});
								}
							}
						});
					}
			});
			
			
			
			dialog.modal({
				keyboard: true
			});
		});
	}
	
	
	
	//初始化对话框
	var initDialog=function(dialog){
		config.load();
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/tradeType/select",
			dataType : "json",
			success : function(data) {
				var htm="<select class='form-control tradeType' data-placeholder='选择贸易类型..' name='tradeType' data-live-search='true'>";
				for(var i=0;i<data.data.length;i++){
					htm+="<option value="+data.data[i].key+">"+data.data[i].value+"</option>";
				}
				htm+="</select>";
				dialog.find("#select-tradeType").append(htm);
			}
		});
		
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/product/select",
			dataType : "json",
			success : function(data) {
				dialog.find('#productId').typeahead({
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
  						dialog.find('#productId').removeAttr("data");
  						dialog.find('#productId').val("");
  					}else{
  						dialog.find('#productId').attr("data",client.id);
  					}
			}
		});
			}
		});
		
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/tank/list?page=0&pagesize=0",
  			dataType : "json",
  			success : function(data) {
  				config.unload();
  				dialog.find('.tankId').typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product.code;
  	                    });
  	                    process(results);
  					},
  					
					//移除控件时调用的方法
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.code == query;
                    });
  					//匹配不到就去掉id，让内容变空，否则给id
  					if(client==null){
  						dialog.find('.tankId').removeAttr("data");
  						dialog.find('.tankId').val("");
  					}else{
  						dialog.find('.tankId').attr("data",client.id);
  					}
  				}
  				});
  			}
  		});
		
		
		
		$.ajax({
			async:false,
		type : "get",
		url : config.getDomain()+"/order/list?status=2&page=0&pagesize=0",
		dataType : "json",
		success : function(data) {
				var _in=dialog.find(".order");
				var _code=dialog.find(".code");
				$(_in).typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product.code+"-"+product.title;
  	                    });
  	                    process(results);
  					},
  					updater:function(item){
  						var client = _.find(data.data, function (p) {
	  						return (p.code+"-"+p.title) == item;
	                    });
  						if(client!=null){
  							if($(_code).val()==""){
  								$.ajax({
  									async:false,
  									type : "post",
  									data:{
  										"code":client.code
  									},
  									url : config.getDomain()+"/arrivalWork/getcode",
  									dataType : "json",
  									success : function(data) {
  										if(data.code=="0000"){
  											$(_code).val(data.map.code);
  										}
  									}
  								});
  							}
  						}
  						return item;
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
  						return (p.code+"-"+p.title) == query;
                    });
  					if(client==null){
  						$(_in).removeAttr("data");
  						$(_in).val("");
//  						$(_code).val("");
  					}else{
  						$(_in).attr("data",client.id);
  					}
  				}
			});
			
	}
	});
		
		
		
		
		$.ajax({
			async:false,
  			type : "get",
  			url : config.getDomain()+"/client/select",
  			dataType : "json",
  			success : function(data) {
  				config.unload();
  				dialog.find('#clientId').typeahead({
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
  						dialog.find('#clientId').removeAttr("data");
  						dialog.find('#clientId').val("");
  					}else{
  						dialog.find('#clientId').attr("data",client.id);
  					}
  				}
  				});
  			}
  		});
		
		
	}
	
	
	
	return {
		init : function() {
			handleRecords();
		},
		doOpen:function(){
			openModal();
		},
		editCargo:function(index){
			editCargo(index);
		},
		deleteCargo:function(id,code){
			deleteCargo(id,code);
		},

		search : function() {
			var params = {};
//			params["arrivalcode"]= $("#storageListForm").find('.arrivalcode').val();
//			params["clientId"]= $("#storageListForm").find('.clientId').attr("data");
            $("#storageListForm").find('.form-control').each(function(){
                var $this = $(this);
                var name = $this.attr('name');
                var data=$this.attr('data');
                if(data&&data!=""){
                	params[name]=$this.attr('data');
                }else
                 if(name){
                	 if($this.val()!=""){
                		 params[name] = $this.val();
                	 }
                }
            });
            console.log(params);
           $('[data-role="virtualGrid"]').getGrid().search(params);
		},
		
		
	};
	
	
	
}();