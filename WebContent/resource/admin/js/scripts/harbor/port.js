var Port = function() {
	
	var edit = function(id) {
		var flag = 0;
		headEdit(id);
		$(".btn-head").click(function() {
			headEdit(id);
			flag = 0;
		});
		
		$(".btn-ship").click(function() {
			shipEdit(id);
			flag = 1;
		});
		
		$(".btn-cargo").click(function() {
			cargoEdit(id);
			flag = 2;
		});
		
		$(".saveButton").click(function() {
			if(flag == 0) {
				var messagehead = {
					'id' : $(".headForm").find("[name='id']").val(),
    				'messageType':$(".headForm").find("[name='messageType']").val(),
    				'fileDescription':$(".headForm").find("[name='fileDescription']").val(),
    				'sendCode':$(".headForm").find("[name='sendCode']").val(),
    				'receiverCode':$(".headForm").find("[name='receiverCode']").val(),
    				'sendPortCode':$(".headForm").find("[name='sendPortCode']").val(),
    				'receiverPortCode':$(".headForm").find("[name='receiverPortCode']").val(),
    				'fileFunction':$(".headForm").find("[name='fileFunction']").val(),
    				'fileCreateTime':$(".headForm").find("[name='fileCreateTime']").val()
        		};
				doEdit("/esb/messageHead/update",messagehead);
			}else if(flag == 1) {
				var ship = {
						'id' : $(".shipForm").find("[name='id']").val(),
        				'vesselCode':$(".shipForm").find("[name='vesselCode']").val(),
        				'vessel':$(".shipForm").find("[name='vessel']").val(),
        				'nationality':$(".shipForm").find("[name='nationality']").val(),
        				'voyage':$(".shipForm").find("[name='voyage']").val(),
        				'liner':$(".shipForm").find("[name='liner']").val(),
        				'confirgFlag':$(".shipForm").find("[name='confirgFlag']").val(),
        				'arrivlTime':$(".shipForm").find("[name='arrivlTime']").val(),
        				'sailingTime':$(".shipForm").find("[name='sailingTime']").val(),
        				'dischStartTime':$(".shipForm").find("[name='dischStartTime']").val(),
        				'dicshEndTime':$(".shipForm").find("[name='dicshEndTime']").val(),
        				'loadStartTime':$(".shipForm").find("[name='loadStartTime']").val(),
        				'loadEndTime':$(".shipForm").find("[name='loadEndTime']").val(),
        				'shipUniqueNum':$(".shipForm").find("[name='shipUniqueNum']").val(),
        				'port':$(".shipForm").find("[name='port']").val(),
        				'befterDraft':$(".shipForm").find("[name='befterDraft']").val(),
        				'afterDraft':$(".shipForm").find("[name='afterDraft']").val(),
        				'realCapacity':$(".shipForm").find("[name='realCapacity']").val(),
        				'portPassenger':$(".shipForm").find("[name='portPassenger']").val(),
        				'netWeight':$(".shipForm").find("[name='netWeight']").val(),
        				'cargoDes':$(".shipForm").find("[name='cargoDes']").val()
        		};
				doEdit("/esb/harborShip/update",ship);
			}else {
				$("body").message({
					type: 'success',
					content: '更新成功'
				});
				window.location.href = "#port";
			}
		});
		
		$(".cargo-add").click(function() {

        	$.get(config.getResource()+"/pages/harbor/port/cargo.jsp").done(function(data){
    			dialog = $(data);
    			dialog.find('#save').on('click',function(){
    				
    				if(config.validateForm($(".cargo-form"))) {
    					doSaveCargo(id);
    				}
    			}).end().modal({
    				keyboard: true
    			});
    			dialog.find('#close').on('click',function(){
    				dialog.remove();
    			});
    			
    		});
    	
		});
	};
	
	/**
	 * 保存提单信息到数据库
	 */
	var doSaveCargo = function(id) {

		var ship = {
				'bl':dialog.find("[name='bl']").val(),
				'shipper':dialog.find("[name='shipper']").val(),
				'consignee':dialog.find("[name='consignee']").val(),
				'consignor':dialog.find("[name='consignor']").val(),
				'cargoName':dialog.find("[name='cargoName']").val(),
				'cargoType':dialog.find("[name='cargoType']").val(),
				'grossWeight':dialog.find("[name='grossWeight']").val(),
				'pay':dialog.find("[name='pay']").val(),
				'danger':dialog.find("[name='danger']").val(),
				'dangerNO':dialog.find("[name='dangerNO']").val(),
				'transfer':dialog.find("[name='transfer']").val(),
				'ifcsumType':dialog.find("[name='ifcsumType']").val(),
				'startPort':dialog.find("[name='startPort']").val(),
				'disPort':dialog.find("[name='disPort']").val(),
				'dischargePortCode':dialog.find("[name='dischargePortCode']").val(),
				'dischargePort':dialog.find("[name='dischargePort']").val(),
				'loadPortCode':dialog.find("[name='loadPortCode']").val(),
				'loadPort':dialog.find("[name='loadPort']").val(),
				'deliveryPlaceCode':dialog.find("[name='deliveryPlaceCode']").val(),
				'deliveryPlace':dialog.find("[name='deliveryPlace']").val(),
				'catogary' : 'BULK',
				'ship' : {'id':id}
		};
		
		$.ajax({
    		url: config.getDomain()+"/esb/harborBill/create",
    		data: "bill="+JSON.stringify(ship),
    		type: "POST",
    		success: function(data){
    			$("body").message({
					type: data.code == "0000" ?'success' : 'error',
					content: data.msg
				});
    			$(".tab").getGrid().refresh();
    			return data.code == "0000" ?true : false;
    		},
    		fail : function(data) {
    			$("body").message({
					type: 'error',
					content: data.msg
				});
    			return false;
    		}
    	});
		dialog.remove();
	};
	
	/**
	 * 持久化操作
	 */
	var doEdit = function(url,data) {
		config.load();
		$.ajax({
    		url: config.getDomain()+url,
    		type: "POST",
    		data: data,
    		success: function(data){
    			config.unload();
    			if(data.code == "0000"){
    				$("body").message({
						type: 'success',
						content: data.msg
					});
    				window.location.href = "#port";
    			}else {
    				$("body").message({
						type: 'error',
						content: data.msg
					});
    			}
    		}
    	});
	};
	
	/**
	 * 修改报文头
	 */
	var headEdit = function(id) {
		$(".cargo-add").hide();
		$(".tab").children().remove();
		config.load();
		$(".tab").load(config.getResource()+"/pages/harbor/head/edit.jsp",null,function() {
			$(".datetime").datetimepicker({
                autoclose: true,
                isRTL: Metronic.isRTL(),
                format: "yyyy-mm-dd hh:ii:ss",
                pickerPosition: "bottom-left"
            });
			$.ajax({
	    		url: config.getDomain()+"/esb/messageHead/get",
	    		type: "POST",
	    		data: 'head='+JSON.stringify({'ship' : {'id':id}}),
	    		success: function(data){
	    			config.unload();
	    			if(data.code == "0000"){
	    				var _data = data.data[0];
	    				initFormParams($(".headForm"),_data);
	    			}
	    		}
	    	});
		});
		
	};
	
	/**
	 * 船舶信息修改
	 */
	var shipEdit = function(id) {
		
		$(".cargo-add").hide();
		$(".tab").children().remove();
		config.load();
		$(".tab").load(config.getResource()+"/pages/harbor/ship/edit.jsp",null,function() {
			
			$(".ship_select").click(function() {
	    		$.get(config.getResource()+"/pages/harbor/port/shipselect.jsp").done(function(data){shipSelect($(data));});
	    	});
			
			$(".datetime").datetimepicker({
                autoclose: true,
                isRTL: Metronic.isRTL(),
                format: "yyyy-mm-dd hh:ii:ss",
                pickerPosition: "bottom-left"
            });
			$.ajax({
	    		url: config.getDomain()+"/esb/harborShip/get?id="+id,
	    		type: "POST",
	    		success: function(data){
	    			config.unload();
	    			if(data.code == "0000"){
	    				var _data = data.data[0];
	    				initFormParams($(".shipForm"),_data);
	    			}
	    		}
	    	});
		});
	};
	
	/**
	 * 修改提单信息
	 */
	var cargoEdit = function(id) {
		$(".cargo-add").show();
		config.load();
		$(".tab").children().remove();
		
		if($(".tab").getGrid()) {
			$(".tab").getGrid().destory();
		}
		 $(".tab").grid({
             identity:"id",
             isShowIndexCol : false,
             isShowPages : true,
             url : config.getDomain()+"/esb/harborBill/get?bill="+JSON.stringify({'ship' : {'id':id,'catogary':'BULK'}}),
             columns: [{title : "提单号",name : "bl",
		                	render: function(item, name, index){
		    					return "<a href='javascript:void(0);' onclick='Port.billEdit("+index+")'>"+item[name]+"</a>";
		    				}
             		  },
             		 {title : "收货人",name : "shipper"},
                     {title : "托运人",name : "consignor"}, 
                     {title : "货物名称",name : "cargoName"},
                     {title : "货物类别",name : "cargoType"},
                     {title : "净重",name : "grossWeight"},
                       {title : "危险品标志",name : "danger",
		                	render: function(item, name, index){
		                		if(item[name] == 'Y') {
		                			return "危险品";
		                		}else {
		                			return "非危险品";
		                		}
		    				}
             		  },
                       {title : "启运港",name : "startPort"},
                       {title : "目的港",name : "disPort"},
                       {title : "操作",name : "id",
           				render: function(item, name, index){
           					return '<div class="btn-group btn-group-xs btn-group-solid">'
           							+'<button class="btn red" type="button" title="撤销" onclick="Port.cargoDelete(\''+item[name]+'\')"><span class="glyphicon glyphicon-remove"></span></button>'
           							+'</div>';
           				}
           			 }
             ]
         });
		 config.unload();
	};
	
	var cargoDelete = function(data) {
        
		$(".tab").confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
				config.load();
				 $.post(config.getDomain()+'/esb/harborBill/remove', "ids="+data+"&catogary=BULK").done(function (data) {
					 	config.unload();
			            if (data.code == "0000") {
			            	$("body").message({
			                    type: 'success',
			                    content: '删除成功'
			                });
			            	$(".tab").grid('refresh');
			            } else {
			                $('body').message({
			                    type: 'error',
			                    content: data.msg
			                });
			            }
			        }).fail(function (data) {
			        	config.unload();
			        	$("body").message({
			                type: 'error',
			                content: '删除失败'
			            });
			        });
			}
		});
        
    };
	
	var billEdit = function(index) {
		var item = $(".tab").getGrid().getItemByIndex(index);
		$.get(config.getResource()+"/pages/harbor/port/cargo.jsp").done(function(data){
			dialog = $(data);
			dialog.find("#copy").removeClass("hidden");
			initFormParams(dialog.find(".cargo-form"),item);
			
			dialog.find('#save').on('click',function(){
				
				if(!config.validateForm($(".cargo-form"))) {
					return;
				}
				
				dialog.find(".form-control").each(function() {
					item[$(this).attr("name")] = $(this).val();
				});
				config.load();
				$(".tab").getGrid().updateRows(item.id,item);
				$.ajax({
		    		url: config.getDomain()+"/esb/harborBill/update",
		    		data: "bill="+JSON.stringify($(".tab").getGrid().getItemByIndex(index)),
		    		type: "POST",
		    		success: function(data){
		    			config.unload();
		    			if(data.code == "0000"){
		    				dialog.remove();
		    			}
		    			$("body").message({
							type: data.code == "0000" ? 'success' :'error',
							content: data.msg
						});
		    		},
		    		fail : function(data) {
		    			config.unload();
		    			$("body").message({
							type: 'error',
							content: data.msg
						});
		    		}
		    	});
			}).end().modal({
				keyboard: true
			});
			dialog.find('#close').on('click',function(){
				dialog.remove();
			});
			
			copyCargo(dialog,true,item.portId);
		});
	};
	
	var deleteCargo = function(index) {
		var indexes = new Array();
		indexes.push(index);
		
		$("body").confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
				$('[data-role="cargoGrid"]').getGrid().removeRows(indexes);
			}
		});
	};
	
	/**
	 * 获取提单信息
	 */
	var getCargo = function(index) {
		var item = $('[data-role="cargoGrid"]').getGrid().getItemByIndex(index);
		$.get(config.getResource()+"/pages/harbor/port/cargo.jsp").done(function(data){
			dialog = $(data);
			if(index != null) {
				dialog.find("#copy").removeClass("hidden");
			}
			initFormParams(dialog.find(".cargo-form"),item);
			
			dialog.find('#save').on('click',function(){
				var cargo = new Object();
				dialog.find(".form-control").each(function() {
					cargo[$(this).attr("name")] = $(this).val();
				});
				$('[data-role="cargoGrid"]').getGrid().updateRows(cargo.bl,cargo);
				dialog.remove();
			}).end().modal({
				keyboard: true
			});
			dialog.find('#close').on('click',function(){
				dialog.remove();
			});
			copyCargo(dialog,false,null);//初始化肤质事件
		});
		
	};
	
	var cacheCargo = function(dialog) {
		var cargo = new Object();
		dialog.find(".form-control").each(function() {
			cargo[$(this).attr("name")] = $(this).val();
		});
		$('[data-role="cargoGrid"]').getGrid().insertRows(cargo);
		dialog.remove();
	};
	
	/**
	 * 复制提单信息
	 */
	var copyCargo = function(dialog,isAdd,id) {
		dialog.find("#copy").click(function() {
			dialog.find("#copy").hide();
			dialog.find('#save').unbind("click");
			
			dialog.find('#save').on('click',function(){
				if(!isAdd) {
					cacheCargo(dialog);
				}else {
					doSaveCargo(id);
				}
			});
			
		});
	};
	
	var deletePort = function(id) {
		$('[data-role="portGrid"]').confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
				config.load();
				 $.post(config.getDomain()+'/esb/harborShip/remove', "ids="+id+"&catogary=BULK").done(function (data) {
					 	config.unload();
					 	$('[data-role="portGrid"]').message({
		                    type: data.code == "0000" ? 'success' : 'error',
		                    content: data.msg
		                });
			            if (data.code == "0000") {
			            	$('[data-role="portGrid"]').grid('refresh');
			            }
			        }).fail(function (data) {
			        	config.unload();
			        	$("body").message({
			                type: 'error',
			                content: '删除失败'
			            });
			        });
			}
		});
        
    };
    
    var upload = function(id,catogary) {
    	$('[data-role="portGrid"]').confirm({
			content : '确定要上传该报文吗?',
			callBack : function() {
				config.load();
				 $.post(config.getDomain()+'/esb/harborShip/upload', "id="+id+"&catogary="+catogary).done(function (data) {
					 	config.unload();
					 	$('[data-role="portGrid"]').message({
		                    type: data.code == "0000" ? 'success' : 'error',
		                    content: data.msg
		                });
			        }).fail(function (data) {
			        	config.unload();
			        	$("body").message({
			                type: 'error',
			                content: '删除失败'
			            });
			        });
			}
		});
    };
    
    var initContainer = function() {
	   	 $('[data-role="cargoGrid"]').grid({
	         identity:"bl",
	         isShowIndexCol : false,
	         isUserLocalData : true,
	         isShowPages : false,
	         localData : [],
	         columns: [{title : "提单号",name : "bl",
		                	render: function(item, name, index){
		    					return "<a href='javascript:void(0);' onclick='Port.getCargo("+index+")'>"+item[name]+"</a>";
		    				}
	         		  },
	         		 {title : "收货人",name : "shipper"},
                     {title : "托运人",name : "consignor"}, 
                     {title : "货物名称",name : "cargoName"},
                     {title : "货物类别",name : "cargoType"},
                     {title : "净重",name : "grossWeight"},
	                   {title : "危险品标志",name : "danger",
		                	render: function(item, name, index){
		                		if(item[name] == 'Y') {
		                			return "危险品";
		                		}else {
		                			return "非危险品";
		                		}
		    				}
	         		  },
	                   {title : "启运港",name : "startPort"},
	                   {title : "目的港",name : "disPort"},
	                   {title : "操作",name : "bl",
	       				render: function(item, name, index){
	       					return '<div class="btn-group btn-group-xs btn-group-solid">'
	       							+'<button class="btn red" type="button" title="撤销" onclick="Port.deleteCargo(\''+item[name]+'\')"><span class="glyphicon glyphicon-remove"></span></button>'
	       							+'</div>';
	       				}
	       			 }
	         ]
	     });
	     
	     $(".btn-add").click(function() {
	     	$.get(config.getResource()+"/pages/harbor/port/cargo.jsp").done(function(data){
	    			dialog = $(data);
	    			dialog.find('#save').on('click',function(){
	    				
	    				initFormIput();
	    				
	    				if(config.validateForm($(".cargo-form"))) {
		    				var cargo = new Object();
		    				dialog.find(".form-control").each(function() {
		    					cargo[$(this).attr("name")] = $(this).val();
		    				});
		    				$('[data-role="cargoGrid"]').getGrid().insertRows(cargo);
		    				dialog.remove();
	    				}
	    			}).end().modal({
	    				keyboard: true
	    			});
	    			dialog.find('#close').on('click',function(){
	    				dialog.remove();
	    			});
	    		});
	 	});
	};
	
	var shipSelect = function(dialog) {
		
		dialog.find('[data-role="shipGrid"]').grid({
            identity:"id",
            url : config.getDomain()+"/esb/esbShip/get",
            columns: [{
				title : "英文",
				name : "englishName"
			},{
				title : "中文",
				name : "chineseName"
			}]
     });
		
		dialog.find("#managerSearch").click(function() {
			var params = {
				'englishName':dialog.find('.search-key').val()
			};
			dialog.find('[data-role="shipGrid"]').getGrid().search(params);
		});
		
		dialog.find('#save').on('click',function(){
			var data = dialog.find('[data-role="shipGrid"]').getGrid().selectedRowsIndex();
			if (data.length == 0) {
				$("body").message({
					type : 'warning',
					content : '请选船舶资料信息'
				});
				return;
			}else {
				if (data.length > 1) {
					$("body").message({
						type : 'warning',
						content : '请仅选择要添加的一条船舶资料信息'
					});
					return;
				}
				var _data = dialog.find('[data-role="shipGrid"]').getGrid().selectedRows()[0];
				$("input[name='vesselCode']").val(_data.englishName);
				$("input[name='vessel']").val(_data.chineseName);
				$("input[name='nationality']").val(_data.category);
				$("input[name='shipUniqueNum']").val(_data.registNO);
				dialog.remove();
			}
		}).end().modal({
			keyboard: true
		});
		dialog.find('#close').on('click',function(){
			dialog.remove();
		});
	};
    
    var initCargo = function() {
    	
    	$(".ship_select").click(function() {
    		$.get(config.getResource()+"/pages/harbor/port/shipselect.jsp").done(function(data){shipSelect($(data));});
    	});
    	
//    	$("#vesselCode").change(function() {
//    		$.ajax({
//    			async:false,
//    			type : "post",
//    			url : config.getDomain()+"/esb/esbShip/get?englishName="+$(this).val(),
//    			success : function(data) {
//    				if(data.code == "0000") {
//    					$("input[name='vessel']").val(data.data[0].chineseName);
//    					$("input[name='nationality']").val(data.data[0].category);
//    					$("input[name='shipUniqueNum']").val(data.data[0].registNO);
//    				}
//    			}
//    		});
//    	});
    	
    	 $('[data-role="cargoGrid"]').grid({
             identity:"bl",
             isShowIndexCol : false,
             isUserLocalData : true,
             isShowPages : false,
             localData : [],
             columns: [{title : "提单号",name : "bl",
		                	render: function(item, name, index){
		    					return "<a href='javascript:void(0);' onclick='Port.getCargo("+index+")'>"+item[name]+"</a>";
		    				}
             		  },
                       {title : "收货人",name : "shipper"},
                       {title : "托运人",name : "consignor"}, 
                       {title : "货物名称",name : "cargoName"},
                       {title : "货物类别",name : "cargoType"},
                       {title : "净重",name : "grossWeight"},
                       {title : "危险品标志",name : "danger",
		                	render: function(item, name, index){
		                		if(item[name] == 'Y') {
		                			return "危险品";
		                		}else {
		                			return "非危险品";
		                		}
		    				}
             		  },
                       {title : "启运港",name : "startPort"},
                       {title : "目的港",name : "disPort"},
                       {title : "操作",name : "bl",
           				render: function(item, name, index){
           					return '<div class="btn-group btn-group-xs btn-group-solid">'
           							+'<button class="btn red" type="button" title="撤销" onclick="Port.deleteCargo(\''+item[name]+'\')"><span class="glyphicon glyphicon-remove"></span></button>'
           							+'</div>';
           				}
           			 }
             ]
         });
         
         $(".btn-add").click(function() {
         	$.get(config.getResource()+"/pages/harbor/port/cargo.jsp").done(function(data){
	    			dialog = $(data);
	    			
	    			dialog.find('#save').on('click',function(){
	    				
	    				if(config.validateForm($(".cargo-form"))) {
		    				var cargo = new Object();
		    				dialog.find(".form-control").each(function() {
		    					cargo[$(this).attr("name")] = $(this).val();
		    				});
		    				$('[data-role="cargoGrid"]').getGrid().insertRows(cargo);
		    				dialog.remove();
	    				}
	    			}).end().modal({
	    				keyboard: true
	    			});
	    			dialog.find('#close').on('click',function(){
	    				dialog.remove();
	    			});
	    		});
     	});
    };
	
	var init = function() {
		$('[data-role="portGrid"]').grid({
            identity:"id",
            isShowIndexCol : false,
            url : config.getDomain()+"/esb/harborShip/get",
            columns: [{
				title : "船舶英文名",
				name : "vesselCode",
				render: function(item, name, index){
					return '<a href="#port/edit?id='+item.id+'">'+item.vesselCode+'</a>';
				}
			},{
				title : "船舶中文名",
				name : "vessel"
			},{
				title : "船舶识别号",
				name : "shipUniqueNum"
			},{
				title : "到港时间",
				name : "arrivlTime"
			},{
				title : "类型",
				name : "status",
				width : 80,
				render: function(item, name, index){
					if(item[name] == 1) {
						return "接卸";
					}else {
						return "装船";
					}
				}
			},{
				title : "状态",
				name : "status",
				width : 80,
				render: function(item, name, index){
					if(item[name] == 2) {
						return "失败";
					}else if(item[name] == 1) {
						return "已发送";
					}else {
						return "未发送";
					}
				}
			}, {
				title : "操作",
				name : "status",
				width : 100,
				render: function(item, name, index){
					var html = "";
					if(item[name] != 1) {
						html = '<div class="btn-group btn-group-xs btn-group-solid">';
						if(item[name] == 2) {
							html += '<button class="btn btn-primary" type="button" title="重发" onclick="Port.upload(\''+item.id+'\',\''+item.catogary+'\')"><span class="fa fa-file-text"></span></button>';
						}else {
							html += '<button class="btn btn-primary" type="button" title="编辑" onclick="window.location.href = \'#port/edit?id='+item.id+'\'"><span class="glyphicon glyphicon-edit"></span></button>';
							html += '<button class="btn red" type="button" title="撤销" onclick="Port.deletePort('+item.id+')"><span class="glyphicon glyphicon-remove"></span></button>';
							html += '<button class="btn btn-primary" type="button" title="上传" onclick="Port.upload(\''+item.id+'\',\''+item.catogary+'\')"><span class="fa fa-file-text"></span></button>';
						}
						html += '</div>';
					}else {
						html = '<div class="btn-group btn-group-xs btn-group-solid">';
							html += '<button class="btn btn-arrow-down" type="button" title="重发" onclick="Port.upload(\''+item.id+'\',\''+item.catogary+'\')"><span class="fa fa-file-text"></span></button>';
						html += '</div>';
					}
					return html;
				}
			}
            ]
		});
		
		$(".btn-add-bulk").click(function() {
			window.location.href = "#port/bulk";
		});
		
		$(".btn-add-container").click(function() {
			window.location.href = "#port/container";
		});
	};
	
	return {
		init : init,
		deleteCargo : deleteCargo,
		cargoDelete : cargoDelete,
		getCargo : getCargo,
		edit : edit,
		billEdit : billEdit,
		deletePort : deletePort,
		upload : upload,
		initCargo : initCargo,
		initContainer : initContainer
	};
}();