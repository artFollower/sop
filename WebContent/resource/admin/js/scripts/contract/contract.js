var Contract = function() {
	
	var dialog 	= null;    		//对话框
	var dataGrid 	= null; 		//Grid对象
	
	//导出通知单
	function exportExcel(){
		var code = $(".code").val() ;
		var title = $(".title").val() ;
		var type = $("#type").val() ;
		var status = $("#status").val() ;
		var url = config.getDomain()+"/order/exportExcel?"+"code="+code+"&&title="+title+"&&type="+type+"&&status="+status ;
		window.open(url) ;
	}
	
	function reset(){
		$(".code").val("");
		$(".title").val("");
		$("#type").val("").trigger("change"); 

		$("#status").val("").trigger("change"); 
		
	}
	function addNewClient() {
		$.get(config.getResource()+ "/pages/auth/baseinfo/client/add.jsp").done(
						function(data) {
							dialog = $(data);
							dialog.modal({
								keyboard : true
							});

							$.ajax({
										async : false,
										type : "get",
										url : config.getDomain()
												+ "/clientgroup/list",
										dataType : "json",
										success : function(data) {
											dialog
													.find('#clientGroupId')
													.typeahead(
															{
																source : function(query,process) {
																	var results = _.map(data.data,function(product) {
																						return product.name;
																					});
																	process(results);
																},
																noselect : function(query) {
																	var client = _.find(data.data,function(p) {
																						return p.name == query;
																					});
																	if (client == null) {
																		dialog.find('#clientGroupId').removeAttr("data");
																		dialog.find('#clientGroupId').val("");
																	} else {
																		dialog.find('#clientGroupId').attr("data",client.id)
																	}
																}
															});
										}
									});

							dialog.find("#addClient").click(
											function() {
												if (config.validateForm(dialog.find(".form-client"))) {
													$.ajax({
																type : "post",
																url : config
																		.getDomain()
																		+ "/client/save",
																data : {
																	"code" : dialog
																			.find(
																					".code")
																			.val(),
																	"name" : dialog
																			.find(
																					".name")
																			.val(),
																	"phone" : dialog
																			.find(
																					".phone")
																			.val(),
																	"guestId" : dialog
																			.find(
																					".guestId")
																			.val(),
																	"email" : dialog
																			.find(
																					".email")
																			.val(),
																	"fax" : dialog
																			.find(
																					".fax")
																			.val(),
																	"address" : dialog
																			.find(
																					".address")
																			.val(),
																	"postcode" : dialog
																			.find(
																					".postcode")
																			.val(),
																	"contactName" : dialog
																			.find(
																					".contactName")
																			.val(),
																	"contactPhone" : dialog
																			.find(
																					".contactPhone")
																			.val(),
																	"bankAccount" : dialog
																			.find(
																					".bankAccount")
																			.val(),
																	"bankName" : dialog
																			.find(
																					".bankName")
																			.val(),
																	"creditGrade" : dialog
																			.find(
																					".creditGrade")
																			.val(),
																	"paymentGrade" : dialog
																			.find(
																					".paymentGrade")
																			.val(),
																	"description" : dialog
																			.find(
																					".description")
																			.val(),
																	"clientGroupId" : dialog
																			.find(
																					'#clientGroupId')
																			.attr(
																					"data")
																},
																// data:JSON.stringify(sendGroup),
																dataType : "json",
																success : function(
																		data) {
																	if (data.code == "0000") {
																		$("body").message(
																			{
																				type : 'success',
																				content : '客户添加成功'
																			});
																		dialog.remove();
																		$("#clientId").val(dialog.find(".name").val());
																		$("#clientId").attr("data",data.map.id);
																	} else {
																		$("body").message(
																			{
																				type : 'error',
																				content : '客户添加失败'
																			});
																		dialog.remove();
																	}
																}
															});
												}
											});
						});
	}
	
	
	
	
	
	//选择审核人   type  1:添加  2：更新 3:审核提交给
	var doApprove=function(type){
		var str=$(".submitto").attr("data");
		 if(type==1){
		 save(1,str);
	 }
	 
	 if(type==2){
		 if(config.validateForm($(".contract-update-form"))){
			 
		 config.load();
		 $.ajax({
			 async:false,
			 type : "post",
			 url : config.getDomain()+"/order/sendcheck",
			 dataType : "json",
			 data:{
				 "ids":str,
				 "contractId":$(".inputId").val()
			 },
			 success : function(data) {
				 config.unload();
				 if (data.code == "0000") {
					 update(1);
				 }else {
					 $("body").message({
						 type : 'error',
						 content : '操作失败'
					 });
				 }
			 }
		 });

		 //如果有抄送
		 if($(".copyto").attr("data")){
			 $.ajax({
				 async:false,
				 type : "post",
				 url : config.getDomain()+"/order/sendcopy",
				 dataType : "json",
				 data:{
					 "ids":$(".copyto").attr("data"),
					 "contractId":$(".inputId").val()
				 },
				 success : function(data) {
					 if (data.code == "0000") {
						 $("body").message({
							 type : 'success',
							 content : '抄送成功'
						 });
					 }else {
						 $("body").message({
							 type : 'error',
							 content : '抄送失败'
						 });
					 }
				 }
			 });
		 }
		 }
		 
		 
	 }
	 
	 if(type==3){
		 if(config.validateForm($(".contract-update-form"))){
		 config.load();
		 var des=$(".checkDes").text()?$(".checkDes").text():"通过";
		 $.ajax({
			 type : "post",
			 async:false,
			 url : config.getDomain()+"/order/sendcheck",
			 dataType : "json",
			 data:{
				 "ids":str,
				 "contractId":$(".inputId").val(),
				 "content":des
			 },
			 success : function(data) {
				 config.unload();
				 if (data.code == "0000") {
					 $("body").message({
						 type : 'success',
						 content : '操作成功'
					 });
					 initEdit($(".inputId").val());
				 }else {
					 $("body").message({
						 type : 'error',
						 content : '操作失败'
					 });
				 }
			 }
		 });
		 //如果有抄送
		 if($(".copyto").attr("data")){
			 $.ajax({
				 async:false,
				 type : "post",
				 url : config.getDomain()+"/order/sendcopy",
				 dataType : "json",
				 data:{
					 "ids":$(".copyto").attr("data"),
					 "contractId":$(".inputId").val()
				 },
				 success : function(data) {
					 if (data.code == "0000") {
						 $("body").message({
							 type : 'success',
							 content : '抄送成功'
						 });
//						 dialog.remove();
					 }else {
						 $("body").message({
							 type : 'error',
							 content : '抄送失败'
						 });
					 }
				 }
			 });
		 }
	 }
		 
		 
		 
	 }
	}
	
	
	var search = function(dialog) {
		
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
	
	
	var searchCopy = function(dialog) {
		
		var params = {};
        dialog.find("#copyForm").find('.form-control').each(function(){
            var $this = $(this);
            var name = $this.attr('name');
             if(name){
            	 if($this.attr("data")){
            		 params[name] = $this.attr("data");
            	 }
            }
        });
        dialog.find('[data-role="copyGrid"]').getGrid().search(params);
	};
	
	//获取项目路径
	function getRootPath(){
		var curWwwPath=window.document.location.href;    
		var pathName=window.document.location.pathname;    
		var pos=curWwwPath.indexOf(pathName);    
		var localhostPaht=curWwwPath.substring(0,pos);    
		var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);    
		return(localhostPaht+projectName);
	}
	
	
	//抄送
	function openCopyModal(){
		$.get(config.getResource()+"/pages/contract/contract/copy.jsp").done(function(data){
			dialog = $(data) ;
				initCopyModal(dialog);
			dialog.modal({
				keyboard: true
			});
		});
	}
	
	
	function initCopyModal(dialog){
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
	  					searchCopy(dialog);
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
  						searchCopy(dialog);
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
		dialog.find('[data-role="copyGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain()+"/auth/role/getUser"
		});
		
		dialog.find(".button-ok").click(function(){
			var data="";
			var name="";
			$.each(dialog.find('[data-role="copyGrid"]').getGrid().selectedRows(), function (i, role) {
	            data += role.userId + ",";
	            name+=role.name+",";
	        });
			 data = data.substring(0, data.length - 1);
			 name = name.substring(0, name.length - 1);
			 $(".copyto").val(name);
			 $(".copyto").attr("data",data);
			 dialog.remove();
			 
		});
	}
	
	
	function openApproveModal(type){
		$.get(config.getResource()+"/pages/contract/contract/approve.jsp").done(function(data){
			dialog = $(data) ;
			initApproveModal(dialog,type);
			dialog.modal({
				keyboard: true
			});
		});
		
	}
	
	
	function initApproveModal(dialog,type){
		
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
	  					search(dialog);
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
  						search(dialog);
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
			var str="";
			var name= "";
			$.each(dialog.find('[data-role="approveGrid"]').getGrid().selectedRows(), function (i, role) {
				str += role.userId + ",";
				name+=role.name+",";
	        });
			str = str.substring(0, str.length - 1);
			name = name.substring(0, name.length - 1);
			$(".submitto").val(name);
			$(".submitto").attr("data",str);
			dialog.remove();
			 
		});
	}
	
	//上传文件
	function openFileModal(id){
		$.get(config.getResource()+"/pages/contract/contract/fileAdd.jsp").done(function(data){
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
				
//				dialog.find('#fileForm').submit();
//				dialog.find('#fileForm').ajaxSubmit(function(data){
//					});
				dialog.find('#fileForm').ajaxSubmit({  
					success:function(data){
						if(data.code=="0000"){
							$("body").message({
								type : 'success',
								content : '上传成功'
							});
							dialog.remove();
							if($('[data-role="contractGrid"]').getGrid()!=undefined){
//								console.log(1);
								util.updateGridRow($('[data-role="contractGrid"]'),{id:id,url:'/order/list'});
							} 
//							$('[data-role="contractGrid"]').grid('refresh');
						}else{
							$("body").message({
								type : 'error',
								content : '上传失败'
							});
							dialog.remove();
					}
					     
					}
					});   
			});
		
	}
	
	function doSubmit(id,status){
		var mId=id;
		$(this).confirm({
			content : '确定要进行操作吗?',
			callBack : function() {
				if(id==0){
					id=$(".inputId").val();
				}
				config.load();
				$.ajax({
					type : "post",
					url : config.getDomain()+"/order/updatestatus",
					dataType : "json",
					data:{
						"id":id,
						"status":status,
						"reviewContent":$(".checkDes").val()
					},
					success : function(data) {
						config.unload();
						if (data.code == "0000") {
							$("body").message({
								type : 'success',
								content : '操作成功'
							});
							if(mId==0){
								window.location.href = "#/contract";
							}else{
								$('[data-role="contractGrid"]').grid('refresh');
							}
						}else {
							$("body").message({
								type : 'error',
								content : '操作失败'
							});
						}
					}
				});
				
				
				//如果有抄送
				 if($(".copyto").attr("data")){
					 $.ajax({
						 type : "post",
						 url : config.getDomain()+"/order/sendcopy",
						 dataType : "json",
						 data:{
							 "ids":$(".copyto").attr("data"),
							 "contractId":$(".inputId").val()
						 },
						 success : function(data) {
							 if (data.code == "0000") {
								 $("body").message({
									 type : 'success',
									 content : '抄送成功'
								 });
								 
							 }else {
								 $("body").message({
									 type : 'error',
									 content : '抄送失败'
								 });
							 }
						 }
					 });
				 }
				
				
			}
		});
		
		
		
		
	}
	
	function initContractChange(id){
		config.load();
		$.ajax({
			type : "get",
			url : config.getDomain()+"/order/list?id="+id,
			dataType : "json",
			success : function(data) {
				config.unload();
				var intent=data.data[0];
				if(intent.mSignDate!=""){
					$("#signDate1").val(intent.mSignDate.split(" ")[0]);
					$("#signDate2").val(intent.mSignDate.split(" ")[1].substring(0,5));
				}
				if(intent.mStartDate!=""&&intent.mStartDate!=null){
					$("#startDate").val(intent.mStartDate.split(" ")[0]);
					$("#startDate1").val(intent.mStartDate.split(" ")[1].substring(0,5));
				}
				
				if(intent.mEndDate!=""&&intent.mEndDate!=null){
					$("#endDate").val(intent.mEndDate.split(" ")[0]);
					$("#endDate1").val(intent.mEndDate.split(" ")[1].substring(0,5));
				}
				if(intent.type==3){
					$(".sTimeText").text("起用日期");
					$(".endTimeText").text("终止日期");
				}else if(intent.type==4){
					$(".divArrivalTime").show();
				}
				if(intent.mArrivalTime!=""&&intent.mArrivalTime!=null){
					$("#arrivalTime").val(intent.mArrivalTime.split(" ")[0]);
				}
				
				if(intent.type==3){
						$(".sTimeText").text("起用日期");
						$(".endTimeText").text("终止日期");
					}else if(intent.type==4){
						$(".divArrivalTime").show();
					}
				$(".spDes").val(intent.spDes);
				$(".protectStoragePrice").val(intent.protectStoragePrice);
				
				$(".inputId").val(id);
				$(".intention").attr("data",intent.intentionId);
				$(".intention").val(intent.intentionTitle);
				$(".code").val(intent.code);
				$(".orderTitle").val(intent.title);
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
				
//				$(".tradeType").val(intent.tradeTypeName);
//				$(".tradeType").attr("data",intent.tradeType);
				$(".tradeType").val(intent.tradeTypeNameList);
//				$(".productId").val(intent.productName);
//				$(".productId").attr("data",intent.productId);
				$("#productId").attr("value",intent.productIdList);
				$(".clientId").val(intent.clientName);
				$(".clientId").attr("data",intent.clientId);
				$(".clientGroupId").val(intent.clientGroupName);
//				$(".signDate").val(intent.mSignDate);
				$(".type").val(intent.typeName);
				$(".type").attr("data",intent.type);
				$(".createUserName").val(intent.createUserName);
				$(".createUserName").attr("data",intent.createUserId);
				$(".createTime").val(intent.mCreateTime);
				
				initSelect();
				
			}
		});
	}
	
	
	
	function initEdit(id){
		$(".g1").remove();
		
		
		//缓存
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/order/checkcache?workType=1",
  			dataType : "json",
  			success : function(data) {
  				if(data.data.length>0){
  					for(var i=0;i<data.data.length;i++){
  						if(data.data[i].type==1){
  							$(".submitto").val(data.data[i].name);
  							$(".submitto").attr("data",data.data[i].ids);
  							
  						}else{
  							$(".copyto").val(data.data[i].name);
  							$(".copyto").attr("data",data.data[i].ids);
  						}
  					}
  				}
  			}
  			});
		
		
		
		
		
		config.load();
		$.ajax({
//			async:false,
			type : "get",
			url : config.getDomain()+"/order/list?id="+id,
			dataType : "json",
			success : function(data) {
				config.unload();
				var intent=data.data[0];
				if(intent.sourceContractId!=null&&intent.sourceContractId!=0){
					$("#source").text("此为变更合同!");
				}
				if(intent.mSignDate!=""){
					$("#signDate1").val(intent.mSignDate.split(" ")[0]);
					$("#signDate2").val(intent.mSignDate.split(" ")[1].substring(0,5));
				}
				if(intent.mStartDate!=""&&intent.mStartDate!=null){
					$("#startDate").val(intent.mStartDate.split(" ")[0]);
					$("#startDate1").val(intent.mStartDate.split(" ")[1].substring(0,5));
				}
				
				if(intent.mEndDate!=""&&intent.mEndDate!=null){
					$("#endDate").val(intent.mEndDate.split(" ")[0]);
					$("#endDate1").val(intent.mEndDate.split(" ")[1].substring(0,5));
				}
				if(intent.type==3){
					$(".sTimeText").text("起用日期");
					$(".endTimeText").text("终止日期");
				}else if(intent.type==4){
					$(".divArrivalTime").show();
				}
				if(intent.mArrivalTime!=""&&intent.mArrivalTime!=null){
					$("#arrivalTime").val(intent.mArrivalTime.split(" ")[0]);
				}
				$(".spDes").val(intent.spDes);
				
				$(".protectStoragePrice").val(intent.protectStoragePrice);
				$(".inputId").val(id);
				$(".intention").attr("data",intent.intentionId);
				$(".intention").val(intent.intentionTitle);
				$(".code").val(intent.code);
				$(".orderTitle").val(intent.title);
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
//				$(".tradeType").val(intent.tradeTypeName);
//				$(".tradeType").attr("data",intent.tradeType);
				$(".tradeType").val(intent.tradeTypeNameList);
				$("#productId").attr("value",intent.productIdList);
//				$(".productId").val(intent.productName);
//				$(".productId").attr("data",intent.productId);
				$(".clientId").val(intent.clientName);
				$(".clientId").attr("data",intent.clientId);
				$(".clientGroupId").val(intent.clientGroupName);
//				$(".signDate").val(intent.mSignDate);
				$(".type").val(intent.typeName);
				$(".type").attr("data",intent.type);
				$(".createUserName").val(intent.createUserName);
				$(".createUserName").attr("data",intent.createUserId);
				$(".createTime").val(intent.mCreateTime);
				$(".checkUser").val(intent.reviewUserName);
				$(".checkTime").val(intent.mReviewTime);
				$(".checkDes").val(intent.reviewContent);
				
				if(intent.status==0){
					$(".passButton").hide();
					$(".notpassButton").hide();
					$(".saveButton").show();
					$(".submitButton").show();
					$("#ac").show();
				}
				if(intent.status==1){
					$(".saveButton").hide();
					$(".submitButton").hide();
					$("#ac").hide();
					
					$.ajax({
//						async:false,
						type : "get",
						url : config.getDomain()+"/order/getapprovecontent?type="+1+"&workId="+id+"&user=1",
						dataType : "json",
						success : function(data) {
							$(".checkgroup").show();
							if(data.data.length>0){
								if(data.data[0].typeStatus==0){
									$(".saveButton").hide();
									$(".submitButton").hide();
									$(".submitCheckButton").show();
									$(".passButton").show();
									$(".notpassButton").show();
									$(".checkContent").show();
									$("#ac").show();
								}else{
									$(".saveButton").hide();
									$(".submitButton").hide();
									$(".submitCheckButton").hide();
									$(".passButton").hide();
									$(".notpassButton").hide();
									$(".checkContent").hide();
									$("#ac").hide();
								}
							}
						}
					});
					
					
				}else if(intent.status==2){
					$(".saveButton").hide();
					$(".submitButton").hide();
					$(".passButton").hide();
					$(".notpassButton").hide();
					$(".checkgroup").show();
					$("#ac").hide();
			}else if(intent.status==3){
				$(".passButton").hide();
				$(".notpassButton").hide();
				$(".submitCheckButton").hide();
				$(".saveButton").show();
				$(".submitButton").show();
				$("#ac").show();
				
			}
				
				
				
				$.ajax({
//					async:false,
					type : "get",
					url : config.getDomain()+"/order/getapprovecontent?type="+1+"&workId="+id+"&user=0",
					dataType : "json",
					success : function(data) {
						if(data.data.length>0){
							for(var i=0;i<data.data.length;i++){
								var typeStatus=data.data[i].typeStatus;
								var content=data.data[i].content?data.data[i].content:"";
								if(typeStatus!=0){
									var statusName="";
									if(typeStatus==1){
										statusName="结果：通过";
									}else{
										statusName="结果：不通过";
									}
									var tr="";
									tr+="<tr class='g1'><td class='col-md-6'>"+
									"<div class='form-group'>"+
									"<label class='control-label col-md-4'>审核人</label>"+
									"<div class='col-md-8'>"+
									"<input type='text' readonly class='form-control ' value='"+data.data[i].userName+"' /></div></div></td>"+
									"<td class=' col-md-6'>"+
									"<div class='form-group'>"+
									"<label class='control-label col-md-5'>审核日期</label>"+
									"<div class='col-md-7'>"+
									"<input type='text' readonly class='form-control'  value='"+data.data[i].mReviewTime+"'  /></div></div></td></tr>"+
									
									"<tr class='g1'>"+
									"<td class=' col-md-6' colspan='2'>"+
									"<div class='form-group'>"+
									"<label class='col-md-2 control-label'>审批意见</label>"+
									"<div class='col-md-10'>"+
									"<textarea class='form-control' readonly rows='3'  >"+statusName+"    意见："+content+"</textarea></div></div></td></tr>";
									$("#checkTable").append(tr);
									$(".checkgroup").show();
								}
							}
						}
					}
				});
				
				initSelect();
			}
		});
		
		
		
		
		
		
//		$(".passButton").click(function(){
//			if(intent.status==7){
//				doSubmit(0,8);
//			}
//			if(intent.status==8){
//				doSubmit(0,9);
//			}
//			if(intent.status==9){
//				doSubmit(0,2);
//			}
//		});
		
		
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
	var initAddSelection=function(intentId){
		util.initTimePicker($(".sTime"));
//		//初始化时间控件
//		$('.date-picker').datepicker({
//		    rtl: Metronic.isRTL(),
//		    orientation: "right",
//		    format: "yyyy-mm-dd",
//		    autoclose: true
//		});
//		$('.timepicker-24').timepicker({
//	        autoclose: true,
//	        minuteStep: 1,
//	        defaultTime:"12:00",
//	        showSeconds: false,
//	        showMeridian: false
//	    });
		config.load();
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/orderInstent/list?status=2&page=0&pagesize=0",
			dataType : "json",
			success : function(data) {
				
				$('#intention').typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product.title;
  	                    });
  	                    process(results);
  					},
  					updater:function(item){
  						var client = _.find(data.data, function (p) {
  	                        return p.title == item;
  	                    });
  						$('#intention').attr("data",client.id);
  						initChange(client.id);
  						return item;
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.title == query;
                    });
  					if(client==null){
  						$('#intention').removeAttr("data");
  						$('#intention').val("");
  					}else{
  						$('#intention').attr("data",client.id);
//  						initChange(client.id);
  					}
  				}
  				});
				
				if(intentId!=0){
					$(".intention").attr("data",intentId);
					
					config.load();
					 $.ajax({
						 async:false,
							type : "post",
							url : config.getDomain()+"/orderInstent/list?id="+intentId,
							dataType : "json",
							success : function(data) {
								config.unload();
								if (data.code == "0000") {
									if(data.data.length > 0) {
										var intent = data.data[0];
										$("#intention").val(intent.title);
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
										$("#productId").attr("value",intent.productIdList);
//										$(".productId").val(intent.productName);
//										$(".productId").attr("data",intent.productId);
										$(".clientId").val(intent.clientName);
										$(".clientId").attr("data",intent.clientId);
										$(".clientGroupId").val(intent.clientGroupName);
									}
								}
							}
						});
					
					
				}
				
				
//				var htm="<select class='form-control intentionId' onchange='Contract.doChange(this)' data-placeholder='选择已有意向...' name='intentionId' data-live-search='true'>";
//				htm+="<option value='0'>请选择意向</option>";
//				for(var i=0;i<data.data.length;i++){
//					htm+="<option value="+data.data[i].id+">"+data.data[i].title+"</option>";
//				}
//				htm+="</select>";
//				$(".intentionId").remove();
//				$("#select-intentionId").append(htm);
//				if(intentId!=0){
//					$(".intentionId").val(intentId);
//					config.load();
//					 $.ajax({
//						 async:false,
//							type : "post",
//							url : config.getDomain()+"/orderInstent/getIntent?id="+intentId,
//							dataType : "json",
//							success : function(data) {
//								config.unload();
//								if (data.code == "0000") {
//									if(data.data.length > 0) {
//										var intent = data.data[0];
//										$(".quantity").val(intent.quantity);
//										$(".storagePrice").val(intent.storagePrice);
//										$(".passPrice").val(intent.passPrice);
//										$(".overtimePrice").val(intent.overtimePrice);
//										$(".portSecurityPrice").val(intent.portSecurityPrice);
//										$(".portServicePrice").val(intent.portServicePrice);
//										$(".otherPrice").val(intent.otherPrice);
//										$(".lossRate").val(intent.lossRate);
//										$(".period").val(intent.period);
//										$(".totalPrice").val(intent.totalPrice);
//										$(".description").val(intent.description);
//										$(".type").val(intent.typeName);
//										$(".type").attr("data",intent.type);
//										$(".productId").val(intent.productName);
//										$(".productId").attr("data",intent.productId);
//										$(".clientId").val(intent.clientName);
//										$(".clientId").attr("data",intent.clientId);
//										$(".clientGroupId").val(intent.clientGroupName);
//									}
//								}
//							}
//						});
//					
//				}
			}
		});
		
		
		
		
//		$.ajax({
//			async:false,
//			type : "get",
//			url : config.getDomain()+"/tradeType/select",
//			dataType : "json",
//			success : function(data) {
//				$('#tradeType').typeahead({
//  					source: function(query, process) {
//  						var results = _.map(data.data, function (product) {
//  	                        return product.value;
//  	                    });
//  	                    process(results);
//  					},
//  				noselect:function(query){
//  					var client = _.find(data.data, function (p) {
//                        return p.value == query;
//                    });
//  					if(client==null){
//  						$('#tradeType').removeAttr("data");
//  						$('#tradeType').val("");
//  					}else{
//  						$('#tradeType').attr("data",client.key)
//  					}
//  				}
//  				});
//			}
//		});
		
		
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
//  				noselect:function(query){
//  					var client = _.find(data.data, function (p) {
//                        return p.name == query;
//                    });
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
	  	                        return product.name+"["+product.code+"]";
	  	                    });
	  	                    process(results);
	  					},
	  				noselect:function(query){
	  					var client = _.find(data.data, function (p) {
	                        return p.name+"["+p.code+"]" == query;
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
	  					//根据客户组id查询该组客户
	  					config.load();
	  					$.ajax({
	  						async:false,
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
				  				updater: function (item) {
				  					var client = _.find(data.data, function (p) {
				                        return p.name == item;
				                    });
				  					$('#clientId').attr("data",client.id)
				  					return item;
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
		  						async:false,
					  			type : "get",
					  			url : config.getDomain()+"/client/select?clientGroupId",
					  			dataType : "json",
					  			success : function(data) {
					  				config.unload();
					  				$('#clientId').typeahead('hide');
					  				$('#clientId').remove();
					  				$('#client').append("<input id='clientId' type='text' data-provide='typeahead' data-required='1' data-type='Require'  class='form-control clientId'>");
					  				$('#clientId').typeahead({
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
					  					$('#clientId').attr("data",client.id)
					  					return item;
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
	  					
	  			}
	  				});
	  			}
	  		});
		 
		 
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
  						$(".sTimeText").text("起计日期");
						$(".endTimeText").text("截止日期");
  					}else{
  						if(client.key==3){
  							$(".sTimeText").text("起用日期");
  							$(".endTimeText").text("终止日期");
  						}else if(client.key==4){
  							$(".divArrivalTime").show();
  						}else{
  							$(".sTimeText").text("起计日期");
  							$(".endTimeText").text("截止日期");
  							$(".divArrivalTime").hide();
  						}
  						$('#type').attr("data",client.key);
  					}
  				}
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
	
	
	var initChange = function(id){
		config.load();
			 $.ajax({
				 async:false,
					type : "post",
					url : config.getDomain()+"/orderInstent/list?id="+id,
					dataType : "json",
					success : function(data) {
						config.unload();
						if (data.code == "0000") {
							if(data.data.length > 0) {
								var intent = data.data[0];
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
//								$("#productId").attr("value",intent.productIdList);
								var array=new Array();
								
								
								for(var i=0;i<intent.productIdList.split(",").length;i++){
									array.push({id:intent.productIdList.split(",")[i],text:intent.productNameList.split(",")[i]});
								}
								$("#productId").select2("data",array);
								
								
//								$(".productId").val(intent.productName);
//								$(".productId").attr("data",intent.productId);
								$(".clientId").val(intent.clientName);
								$(".clientId").attr("data",intent.clientId);
								$(".clientGroupId").val(intent.clientGroupName);
							}
						}
					}
				});
	}
	
	
	var add = function(grid) {
		dataGrid = grid;
		$.get(config.getResource()+"/pages/contract/contract/add.jsp").done(function(data){
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
	
	var save = function(status,ids) {
		var isOk = false;
		if(config.validateForm($(".contract-add-form"))){
			
			isOk=true;
		}
		
		
		if(isOk) {
			config.load();
			
			var productNameList="";
			for(var i=0;i<$("#productId").select2("data").length;i++){
				productNameList+=$("#productId").select2("data")[i].text+",";
			}
			productNameList=productNameList.substring(0,productNameList.length-1);
			
			var productId=-1;
			if($("#productId").select2("data").length==1){
				productId=$("#productId").select2("data")[0].id;
			}
			
			$.ajax({
				type : "post",
				url : config.getDomain()+"/order/check",
				dataType : "json",
				data:{
					'code':$(".code").val()
				},
				success : function(data) {
					if(data.code=="0000"){
						if(data.map.totalRecord>=1){
							$("body").message({
								type : 'error',
								content : '存在相同的合同号，请重新输入！'
							});
							config.unload();
							return;
						}else{
							
							var ssTime=util.getTimeVal($(".ssTime"))==""?"":util.getTimeVal($(".ssTime"))+" 00:00:00";
							var eeTime=util.getTimeVal($(".eeTime"))==""?"":util.getTimeVal($(".eeTime"))+" 00:00:00";
							var aaTime=util.getTimeVal($(".arrivalTime"))==""?"":util.getTimeVal($(".arrivalTime"))+" 00:00:00";
							$.ajax({
								type : "post",
								url : config.getDomain()+"/order/save",
								data : {
									"ids":ids,
									"sourceContractId" : $(".inputId").val(),
									"code" : $(".code").val(),
									"title" : $(".orderTitle").val(),
									"intentionId" : $(".intention").attr("data"),
									"type" : $(".type").attr("data"),
									"clientId" : $(".clientId").attr("data"),
//					"clientGroupId" : $(".clientGroupId").attr("data"),
									"productId" : productId,
					"productIdList":$("#productId").val(),
					"productNameList":productNameList,
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
//					"signDate" : $(".signDate1").val()+" "+$("#signDate2").val()+":00",
									"signDate" :util.getTimeVal($(".gTime"))+" 00:00:00",
									"spDes" : $(".spDes").val(),
									"tArrivalTime" :aaTime,
									"tStartDate" :ssTime,
									"tEndDate" :eeTime,
									"protectStoragePrice":$(".protectStoragePrice").val(),
//					"tradeType" : $(".tradeType").attr("data"),
									"tradeTypeNameList" : $(".tradeType").val(),
									"taxType" : $(".taxType").val(),
									"status" :status
								},
								// data:JSON.stringify(sendGroup),
								dataType : "json",
								success : function(data) {
									config.unload();
									if (data.code == "0000") {
										$("body").message({
											type : 'success',
											content : '合同添加成功'
										});
//						initEdit(data.map.id);
										window.location.href = "#/contractGet?id="+data.map.id;
//										window.location.href = "#/contract";
									}else {
										$("body").message({
											type : 'error',
											content : '合同添加失败'
										});
									}
								}
							});
							
							}
						}
					}
				});
			
			
		}
	}
	
	var change = function(status,ids) {
		var isOk = false;
		if(config.validateForm($(".contract-add-form"))){
			isOk=true;
		}
		
		
		if(isOk) {
			config.load();
			var productNameList="";
			for(var i=0;i<$("#productId").select2("data").length;i++){
				productNameList+=$("#productId").select2("data")[i].text+",";
			}
			productNameList=productNameList.substring(0,productNameList.length-1);
			
			var productId=-1;
			if($("#productId").select2("data").length==1){
				productId=$("#productId").select2("data")[0].id;
			}
			
//			$.ajax({
//				type : "post",
//				url : config.getDomain()+"/order/check?code="+$(".code").val(),
//				dataType : "json",
//				success : function(data) {
//					if(data.code=="0000"){
//						if(data.map.totalRecord>1){
//							$("body").message({
//								type : 'error',
//								content : '存在相同的合同号，请重新输入！'
//							});
//							config.unload();
//							return;
//						}else{
			var ssTime=util.getTimeVal($(".ssTime"))==""?"":util.getTimeVal($(".ssTime"))+" 00:00:00";
			var eeTime=util.getTimeVal($(".eeTime"))==""?"":util.getTimeVal($(".eeTime"))+" 00:00:00";
			var aaTime=util.getTimeVal($(".arrivalTime"))==""?"":util.getTimeVal($(".arrivalTime"))+" 00:00:00";
							$.ajax({
								type : "post",
								url : config.getDomain()+"/order/save",
								data : {
									"ids":ids,
									"sourceContractId" : $(".inputId").val(),
									"code" : $(".code").val(),
									"title" : $(".orderTitle").val(),
									"intentionId" : $(".intention").attr("data"),
									"type" : $(".type").attr("data"),
									"clientId" : $(".clientId").attr("data"),
//					"clientGroupId" : $(".clientGroupId").attr("data"),
									"productId" : productId,
					"productIdList":$("#productId").val(),
					"productNameList":productNameList,
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
//					"signDate" : $(".signDate1").val()+" "+$("#signDate2").val()+":00",
									"signDate" :util.getTimeVal($(".gTime")),
									"spDes" : $(".spDes").val(),
									"tArrivalTime" :aaTime,
									"tStartDate" :ssTime,
									"tEndDate" :eeTime,
									"protectStoragePrice":$(".protectStoragePrice").val(),
//					"tradeType" : $(".tradeType").attr("data"),
									"tradeTypeNameList" : $(".tradeType").val(),
									"taxType" : $(".taxType").val(),
									"status" :status
								},
								// data:JSON.stringify(sendGroup),
								dataType : "json",
								success : function(data) {
									config.unload();
									if (data.code == "0000") {
										$("body").message({
											type : 'success',
											content : '合同添加成功'
										});
//						initEdit(data.map.id);
										window.location.href = "#/contractGet?id="+data.map.id;
//										window.location.href = "#/contract";
									}else {
										$("body").message({
											type : 'error',
											content : '合同添加失败'
										});
									}
								}
							});
							
//							}
//						}
//					}
//				});
			
			
		}
	}
	
	var update = function(status) {
		
		if(status==0){
			$(".submitto").removeAttr("data-required");
			$(".submitto").removeAttr("data-type");
		}else{
			$(".submitto").attr("data-required","1");
			$(".submitto").attr("data-type","Require");
		}
		
		var isOk = false;
		if(config.validateForm($(".contract-update-form"))){
			isOk=true;
		}
		if(isOk) {
			var reviewTime="";
			var reviewContent="";
//			if(status>=7){
				reviewTime=new Date();
				reviewContent=$(".checkDes").val();
//			}
			config.load();
			var productNameList="";
			for(var i=0;i<$("#productId").select2("data").length;i++){
				productNameList+=$("#productId").select2("data")[i].text+",";
			}
			productNameList=productNameList.substring(0,productNameList.length-1);
			
			var productId=-1;
			if($("#productId").select2("data").length==1){
				productId=$("#productId").select2("data")[0].id;
			}
			
			$.ajax({
				type : "post",
				url : config.getDomain()+"/order/check?code="+$(".code").val(),
				dataType : "json",
				success : function(data) {
					if(data.code=="0000"){
						if(data.map.totalRecord>2){
							$("body").message({
								type : 'error',
								content : '存在相同的合同号，请重新输入！'
							});
							config.unload();
							return;
						}else{
							var ssTime=util.getTimeVal($(".ssTime"))==""?"":util.getTimeVal($(".ssTime"))+" 00:00:00";
							var eeTime=util.getTimeVal($(".eeTime"))==""?"":util.getTimeVal($(".eeTime"))+" 00:00:00";
							var aaTime=util.getTimeVal($(".arrivalTime"))==""?"":util.getTimeVal($(".arrivalTime"))+" 00:00:00";
							
							$.ajax({
								type : "post",
								url : config.getDomain()+"/order/update",
								data : {
									"code" : $(".code").val(),
									"id" : $(".inputId").val(),
									"title" : $(".orderTitle").val(),
									"intentionId" : $(".intention").attr("data"),
									"type" : $(".type").attr("data"),
									"clientId" : $(".clientId").attr("data"),
									"productId" : productId,
					"productIdList":$("#productId").val(),
					"productNameList":productNameList,
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
									"spDes" : $(".spDes").val(),
									"tArrivalTime" :aaTime,
//					"signDate" : $(".signDate1").val()+" "+$("#signDate2").val()+":00.0",
									"signDate" :util.getTimeVal($(".gTime")),
									"tStartDate" :ssTime,
									"tEndDate" :eeTime,
									"protectStoragePrice":$(".protectStoragePrice").val(),
									"tradeType" : $(".tradeType").attr("data"),
									"tradeTypeNameList":$(".tradeType").val(),
									"taxType" : $(".taxType").val(),
									"createUserId" : $(".createUserName").attr("data"),
									"status" :status,
									"tReviewTime":reviewTime,
									"reviewContent":reviewContent
								},
								// data:JSON.stringify(sendGroup),
								dataType : "json",
								success : function(data) {
									config.unload();
									if (data.code == "0000") {
										$("body").message({
											type : 'success',
											content : '合同修改成功'
										});
										initEdit($(".inputId").val());
										

										
//						window.location.href = "#/contractGet?id="+$(".inputId").val();
									}else {
										$("body").message({
											type : 'error',
											content : '合同修改失败'
										});
									}
								}
							});
							
						}
					}
				}
			});
			
			
		}
	}
	
	var deleteItem =function (id){
		var data = $('[data-role="contractGrid"]').getGrid().selectedRowsIndex();
		var $this = $('[data-role="contractGrid"]');
		
		$this.confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
//				deleteIntent($('[data-role="contractGrid"]').getGrid().selectedRows(), $this);
				
				var data = "";
		            data += ("contractIds=" +id);
		        
		        $.post(config.getDomain()+'/order/delete', data).done(function (data) {
		            if (data.code == "0000") {
		            	$('[data-role="contractGrid"]').message({
		                    type: 'success',
		                    content: '删除成功'
		                });
		                $('[data-role="contractGrid"]').grid('refresh');
		            } else {
		                $('body').message({
		                    type: 'error',
		                    content: data.msg
		                });
		            }
		        }).fail(function (data) {
		        	$('[data-role="contractGrid"]').message({
		                type: 'error',
		                content: '删除失败'
		            });
		        });
				
			}
		});
	}
	
	var deleteIntent = function(intents, grid) {
        dataGrid = grid;

        var data = "";
        $.each(intents, function (i, role) {
            data += ("contractIds=" + role.id + "&");
        });
        data = data.substring(0, data.length - 1);
        
        $.post(config.getDomain()+'/order/delete', data).done(function (data) {
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
			title : "合同编号",
			name : "code",
			width:80,
			render: function(item, name, index){
				if(item.status>3){
					return item.code;
				}else{
					if(config.hasPermission('ACONTRACTDETAIL')){
					return "<a href='#/contractGet?id="+item.id+"'>"+item.code+"</a>";
					}else{
						return item.code;
					}
				}
			}
		}, {
			title : "合同名称",
			name : "title"
		}, {
			title : "签约客户",
			name : "clientName"
		},{
			title : "类型",
			name : "typeName",
			width:50
		}, {
			title : "货品",
			name : "productNameList",
			width:80
		}, {
			title : "数量(吨)",
			name : "quantity",
			width:70
		},{
			title : "非保税仓储费(元/吨)",
			name : "storagePrice",
			width:70
		},{
			title : "保税仓储费(元/吨)",
			name : "protectStoragePrice",
			width:70
		},{
			title : "码头通过费(元/吨)",
			name : "passPrice",
			width:70
		},{
			title : "超期费(元/吨)",
			name : "overtimePrice",
			width:70
		},
		{
			title : "起止日期",
			name : "overtimePrice",
			width:70,
			render: function(item, name, index){
				if(item.startDate&&item.endDate){
					return item.startDate.split(" ")[0]+"至"+item.endDate.split(" ")[0];
				}else
				if(item.startDate){
					return item.startDate.split(" ")[0];
				}else if(item.endDate){
					return item.endDate.split(" ")[0];
				}else{
					return "";
				}
			
			}
		},
		{
			title : "总金额(元)",
			name : "totalPrice",
			width:65
		}, 
		{
			title : "签约时间",
			name : "mSignDate",
			width: 80,
			render: function(item, name, index){
				return item.mSignDate.split(" ")[0];
			}
		},
		{
			title : "状态",
			name : "statusName",
			width: 70,
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
				case 3:
					return "<span style='color:red'>"+item.statusName+"</span>";
					break;
				case 4:
					return "<span style='color:red'>"+item.statusName+"</span>";
					break;
				case 5:
					return "<span style='color:red'>"+item.statusName+"</span>";
					break;
				case 6:
					return "<span style='color:red'>"+item.statusName+"</span>";
					break;
				}
			}
		}, {
			title : "操作",
			name : "id",
			width: 120,
			render: function(item, name, index){
				var re="";
				if(item.status==0){
					
					if(config.hasPermission('ACONTRACTUPDATE')){
						re+="<shiro:hasPermission name='ACONTRACTUPDATE'><a href='#/contractGet?id="+item.id+"'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-edit' title='修改'></span></a></shiro:hasPermission>";
						
					}
					if(config.hasPermission('ACONTRACTDELETE')){
						re+="<shiro:hasPermission name='ACONTRACTDELETE'><a href='javascript:void(0)' onclick='Contract.deleteItem("+item.id+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a></shiro:hasPermission>";
//						
					}
				}else if(item.status==1){
					if(config.hasPermission('ACONTRACTUPDATE')){
						
						re+= "<shiro:hasPermission name='ACONTRACTUPDATE'><a href='#/contractGet?id="+item.id+"'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-eye-open' title='查看'></span></a></shiro:hasPermission>";
					}
					if(config.hasPermission('ACONTRACTDELETE')){
						re+="<shiro:hasPermission name='ACONTRACTDELETE'><a href='javascript:void(0)' onclick='Contract.doSubmit("+item.id+",6)' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='作废'></span></a></shiro:hasPermission>";
						
					}
				}else if(item.status==2){
					
					if(config.hasPermission('ACONTRACTUPDATE')){
						re+="<shiro:hasPermission name='ACONTRACTUPDATE'><a href='#/contractGet?id="+item.id+"'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-eye-open' title='查看'></span></a></shiro:hasPermission>";
					}
					if(config.hasPermission('ACHANGECONTRACT')){
						re+="<shiro:hasPermission name='ACHANGECONTRACT'><a href='#/contractChange?id="+item.id+"' class='btn btn-xs blue'><span class='fa fa-recycle' title='合同变更'></span></a></shiro:hasPermission>";
						
					}
					if(config.hasPermission('ACONTRACTDELETE')){
					re+="<shiro:hasPermission name='ACONTRACTDELETE'><a href='javascript:void(0)' onclick='Contract.doSubmit("+item.id+",6)' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='作废'></span></a></shiro:hasPermission>";
					}
				}else if(item.status==3){
					if(config.hasPermission('ACONTRACTUPDATE')){
					re+= "<shiro:hasPermission name='ACONTRACTUPDATE'><a href='#/contractGet?id="+item.id+"'  class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-eye-open' title='查看'></span></a></shiro:hasPermission>";
					}
					if(config.hasPermission('ACONTRACTDELETE')){
						
						re+= "<shiro:hasPermission name='ACONTRACTDELETE'><a href='javascript:void(0)' onclick='Contract.deleteItem("+item.id+")' class='btn btn-xs red'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a></shiro:hasPermission>";
					}
//					"<a href='javascript:void(0)' onclick='Contract.doSubmit("+item.id+",2)' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-check' title='审核'></span></a>";
				}
				if(item.fileUrl){
					
					if(config.hasPermission('ACONTRACTFILE')){
					
					re+="<a href='javascript:void(0)' onclick='Contract.openFileModal("+item.id+")' class='btn btn-xs blue'><span class='fa fa-arrow-up   ' title='上传合同'></span></a><a href='"+getRootPath()+item.fileUrl+"' onclick='' class='btn btn-xs blue'><span class='fa fa-arrow-down   ' title='查看文件'></span></a>";
					}
				}else{
					
					if(config.hasPermission('ACONTRACTFILE')){
					re+="<a href='javascript:void(0)' onclick='Contract.openFileModal("+item.id+")' class='btn btn-xs blue'><span class='fa fa-arrow-up   ' title='上传合同'></span></a>";
					}
				}
				return re;
				}
		}  ];

	
		/*解决id冲突的问题*/
		$('[data-role="contractGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			/*gridName为唯一标识*/
			gridName:'contract',
			stateSave:true,
			url : config.getDomain()+"/order/list"
		});
		//初始化按钮操作
		$(".btn-add").unbind('click'); 
		$(".btn-add").click(function() {
			window.location.href = "#/contractAdd?intentId=0";
		});
		$(".btn-remove").unbind('click'); 
		$(".btn-remove").click(function() {
			var data = $('[data-role="contractGrid"]').getGrid().selectedRowsIndex();
			var $this = $('[data-role="contractGrid"]');
			if (data.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要撤销的记录'
				});
				return;
			}
			
//			var cando=true;
//			 $.each($('[data-role="contractGrid"]').getGrid().selectedRows(), function (i, role) {
//		        	if(role.status!=0&&role.status!=3){
//		        		$this.confirm({
//		    				content : '存在无法删除的合同！！请重新选择！',
//		    				concel:true,
//		    				callBack : function() {
//		    				}
//		    			});
//		        		cando=false;
//		        		return false;
//		        	}
//		        });
//			 if(cando){
				 $this.confirm({
					 content : '确定要撤销所选记录吗?',
					 callBack : function() {
						 deleteIntent($('[data-role="contractGrid"]').getGrid().selectedRows(), $this);
					 }
				 });
//			 }
			
			
		});
		$(".btn-modify").unbind('click'); 
		$(".btn-modify").click(function() {
			var data = $('[data-role="contractGrid"]').getGrid().selectedRowsIndex();
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
			window.location.href = "#/contractGet?id="+$('[data-role="contractGrid"]').getGrid().selectedRows()[0].id;
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
            $("#contractListForm").find('.form-control').each(function(){
                var $this = $(this);
                var name = $this.attr('name');
                 if(name){
                    params[name] = $this.val();
                }
            });
           $('[data-role="contractGrid"]').getGrid().search(params);
		},
		doAdd : function(status) {
			save(status,0);
		},
		doChange1:function(status){
			change(status,0);
		},
		doEdit : function(status) {
			update(status);
		},
		doDelete : function() {
			
		},
		initAdd:function(intentId){
			config.initSidebar();
			initFormIput();
			initAddSelection(intentId);
		},
		doChange:function(id){
			initChange(id);
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
		initContractChange : function(id){
			initContractChange(id);
		},
		openFileModal:function (id){
			openFileModal(id);
		},
		openApproveModal:function (type){
			openApproveModal(type);
		},
		openCopyModal:function (){
			openCopyModal();
		},
		doApprove:function(type){
			doApprove(type);
		},
		initSelect:function(){
			initSelect();
		},
		addNewClient:function(){
			addNewClient();
		},
		exportExcel:exportExcel,
		reset:reset
	};
	
	
	
}();