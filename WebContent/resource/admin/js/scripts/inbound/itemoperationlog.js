var ItemOperation = function() {
	var dialog 	= null;    		// 对话框
	var dataGrid 	= null; 		// Grid对象
	var status = "" ;
	var page = "" ;
	var testObject=null;
	var testObjectHtml='';
	var updateData=null;
	
	var startTime=null;
	
	var open=false;
	var iDex=null;
	//编辑调度日志基本信息
	var editLogWork=function (dataSelected,index){
		$.get(config.getResource()+ "/pages/inbound/operationlog/edit_operationlog.jsp")
		.done(function(data) {
			initFormIput();
			dialog = $(data);
			dialog.modal({
				keyboard: true
			});
			util.initTimePicker(dialog);
//			//初始化时间控件
//			dialog.find('.date-picker').datepicker({
//			    rtl: Metronic.isRTL(),
//			    orientation: "right",
//			    format: "yyyy-mm-dd",
//			    autoclose: true
//			});
//			dialog.find('.timepicker-24').timepicker({
//		        autoclose: true,
//		        minuteStep: 1,
//		        defaultTime:"12:00",
//		        showSeconds: false,
//		        showMeridian: false
//		    });
			if(dataSelected.arrivalTime!=null&&dataSelected.arrivalTime!=0){
//				var arrivalTime=new Date(dataSelected.arrivalTime*1000).Format("yyyy-MM-dd hh:mm:ss");
				var arrivalTime=dataSelected.mArrivalTime;
				dialog.find("#arrivalTime1").val(arrivalTime.split(" ")[0]);
				dialog.find("#arrivalTime2").val(arrivalTime.split(" ")[1].substring(0,5));
			}
			if(dataSelected.openPump!=null&&dataSelected.openPump!=0){
//				var openPump=new Date(dataSelected.openPump*1000).Format("yyyy-MM-dd hh:mm:ss");
				var openPump=dataSelected.mOpenPump;
				dialog.find("#openPump1").val(openPump.split(" ")[0]);
				dialog.find("#openPump2").val(openPump.split(" ")[1].substring(0,5));
			}
			if(dataSelected.stopPump!=null&&dataSelected.stopPump!=0){
//				var stopPump=new Date(dataSelected.stopPump*1000).Format("yyyy-MM-dd hh:mm:ss");
				var stopPump=dataSelected.mStopPump;
				dialog.find("#stopPump1").val(stopPump.split(" ")[0]);
				dialog.find("#stopPump2").val(stopPump.split(" ")[1].substring(0,5));
			}
			if(dataSelected.tearPipeTime!=null&&dataSelected.tearPipeTime!=0){
//				var tearPipeTime=new Date(dataSelected.tearPipeTime*1000).Format("yyyy-MM-dd hh:mm:ss");
				var tearPipeTime=dataSelected.mTearPipeTime;
				dialog.find("#tearPipeTime1").val(tearPipeTime.split(" ")[0]);
				dialog.find("#tearPipeTime2").val(tearPipeTime.split(" ")[1].substring(0,5));
			}
			if(dataSelected.leaveTime!=null&&dataSelected.leaveTime!=0){
//				var leaveTime=new Date(dataSelected.leaveTime*1000).Format("yyyy-MM-dd hh:mm:ss");
				var leaveTime=dataSelected.mLeaveTime;
				dialog.find("#leaveTime1").val(leaveTime.split(" ")[0]);
				dialog.find("#leaveTime2").val(leaveTime.split(" ")[1].substring(0,5));
			}
//			if(dataSelected.notificationTime!=null&&dataSelected.notificationTime!=0){
//				var notificationTime=new Date(dataSelected.notificationTime*1000).Format("yyyy-MM-dd hh:mm:ss");
//				dialog.find("#notificationTime1").val(notificationTime.split(" ")[0]);
//				dialog.find("#notificationTime2").val(notificationTime.split(" ")[1].substring(0,5));
//			}
			
			//查询验证记录
			$.ajax({
				async:false,
	  			type : "get",
	  			url : config.getDomain()+"/inboundserial/getnotify?workId="+dataSelected.workId,
	  			dataType : "json",
	  			success : function(data) {
	  				config.unload();
	  				if(data.code=="0000"){
	  					for(var i=0;i<data.data.length;i++){
	  						var info=data.data[i];
	  						var str = "<tr class='notify'>"+
	  						"<td class='gNotifyTime'><div class='form-group'>"+
	  						"<div class='input-group'>"+
	  						"<div class='col-md-7' style='padding-right: 0px;'><input style='text-align:right;border-right:0;' id='notificationTime1' key='1'  class='form-control form-control-inline date-picker col-md-8 notificationTime1'  type='text' value='"+info.notificationTime.split(" ")[0]+"' />"+
	  						"</div>"+
	  						"<div class='col-md-5' style='padding-left: 0px;'><input style='border-left:0;' type='text'  id='notificationTime2' class='form-control col-md-4  timepicker timepicker-24 notificationTime2' value='"+info.notificationTime.split(" ")[1]+"'/> </div>"+
	  						"</div>"+
	  						"</div>"+
	  						"</div>"+
	  						"</td>"+
	  						"<td>"+
	  						"<input type='hidden' maxlength='16' name='id' id='id' value="+info.id+" /><input type='text' id='notificationNum' name='notificationNum'  class='form-control notificationNum' value='"+info.notificationNum+"'/>"+
	  						"</td>"+
	  						"<td>"+
	  						"<input type='text' maxlength='64' id='notification' name='notification' data-provide='typeahead'  class='form-control notification' value='"+info.notification+"' />"+
	  						"</td>"+
	  						"<td>"+
	  						"<input type='text' maxlength='16' id='notificationUserName' name='notificationUserName' data-provide='typeahead' class='form-control notificationUserName' value='"+info.notificationUserName+"'  />"+
	  						"</td><td><a class='btn btn-xs blue' onclick='ItemOperation.deleteNotificationInfo(this);' href='javascript:void(0)'><span title='撤销' class='glyphicon glyphicon glyphicon-remove'></span></a></td>"+
	  						"</tr>" ;
	  						dialog.find("#tbody").append(str) ;
	  					}
	  					
	  					dialog.find("#tbody tr").each(function(){
	  						var notification=$(this).find('#notification');
	  					$(notification).typeahead({
	  					      source: function(query, process) {
	  					    	 process(["合格", "不合格"]);
	  					      },
	  					      noselect:function(query){}
	  					   });
	  					});

	  					util.initTimePicker(dialog);
//	  				//初始化时间控件
//	  					dialog.find('.date-picker').datepicker({
//	  					    rtl: Metronic.isRTL(),
//	  					    orientation: "right",
//	  					    format: "yyyy-mm-dd",
//	  					    autoclose: true
//	  					});
//	  					dialog.find('.timepicker-24').timepicker({
//	  				        autoclose: true,
//	  				        minuteStep: 1,
//	  				        defaultTime:"12:00",
//	  				        showSeconds: false,
//	  				        showMeridian: false
//	  				    });
	  					
	  					
	  					
	  				}
	  			}
			});
			
			dialog.find("#notifiAdd").click(function(){
				var str = "<tr class='notify'>"+
							"<td class='gNotifyTime'><div class='form-group'>"+
										"<div class='input-group'>"+
											         "<div class='col-md-7' style='padding-right: 0px;'><input style='text-align:right;border-right:0;'  id='notificationTime1' key='1'  class='form-control form-control-inline date-picker col-md-8 notificationTime1'  type='text' />"+
												 "</div>"+
											         "<div class='col-md-5' style='padding-left: 0px;'><input style='border-left:0;'  type='text'  id='notificationTime2' class='form-control col-md-4  timepicker timepicker-24 notificationTime2' > </div>"+
											     "</div>"+
										"</div>"+
									"</div>"+
								"</td>"+
								"<td>"+
											"<input type='hidden' id='id' name='id' /><input type='text' id='notificationNum' maxlength='16' name='notificationNum'  class='form-control notificationNum' />"+
								"</td>"+
								"<td>"+
											"<input type='text' id='notification' name='notification' data-provide='typeahead' maxlength='64'  class='form-control notification' />"+
								"</td>"+
								"<td>"+
											"<input type='text' maxlength='16' id='notificationUserName' name='notificationUserName' data-provide='typeahead' class='form-control notificationUserName' />"+
								"</td><td><a class='btn btn-xs blue' onclick='ItemOperation.deleteNotificationInfo(this);' href='javascript:void(0)'><span title='撤销' class='glyphicon glyphicon glyphicon-remove'></span></a></td>"+
								"</tr>" ;
				dialog.find("#tbody").append(str) ;
				dialog.find("#tbody tr").each(function(){
					var notification=$(this).find('#notification');
				$(notification).typeahead({
				      source: function(query, process) {
				         process(["合格", "不合格"]);
				      },
				      noselect:function(query){}
				   });
				});
				util.initTimePicker(dialog);
				
//				config.load();
//				$.ajax({
//					async:false,
//		  			type : "get",
//		  			url : config.getDomain()+"/auth/user/get?pagesize=0",
//		  			dataType : "json",
//		  			success : function(data) {
//		  				config.unload();
//		  				dialog.find("#tbody tr").each(function(){
//							var notificationUserName=$(this).find('#notificationUserName');
//		  				$(notificationUserName).typeahead({
//							source: function(query, process) {
//								var results = _.map(data.data, function (product) {
//			                        return product.name;
//			                    });
//			                    process(results);
//							},
//						noselect:function(query){
//							var client = _.find(data.data, function (p) {
//		                    return p.name == query;
//		                });
//							if(client==null){
//								$(notificationUserName).removeAttr("data");
//								$(notificationUserName).val("");
//							}else{
//								$(notificationUserName).attr("data",client.id);
//							}
//						}
//		  				});
//		  				});
//		  			}
//						});
				
				
				return false ;
			});
			
			
			dialog.find(".evaluate").val(dataSelected.evaluate);
			dialog.find(".evaluateUserName").val(dataSelected.evaluateUserName);
//			dialog.find(".evaluateUserName").attr("data",dataSelected.evaluateUserId);
			dialog.find(".description").val(dataSelected.description);
//			$.ajax({
//			async:false,
//  			type : "get",
//  			url : config.getDomain()+"/auth/user/get?pagesize=0",
//  			dataType : "json",
//  			success : function(data) {
//  				config.unload();
////  				dialog.find('#notificationUserName').typeahead({
////					source: function(query, process) {
////						var results = _.map(data.data, function (product) {
////	                        return product.name;
////	                    });
////	                    process(results);
////					},
////				noselect:function(query){
////					var client = _.find(data.data, function (p) {
////                    return p.name == query;
////                });
////					if(client==null){
////						dialog.find('#notificationUserName').removeAttr("data");
////						dialog.find('#notificationUserName').val("");
////					}else{
////						dialog.find('#notificationUserName').attr("data",client.id);
////					}
////				}
////				});
//				
//  				dialog.find('#evaluateUserName').typeahead({
//					source: function(query, process) {
//						var results = _.map(data.data, function (product) {
//	                        return product.name;
//	                    });
//	                    process(results);
//					},
//				noselect:function(query){
//					var client = _.find(data.data, function (p) {
//                    return p.name == query;
//                });
//					if(client==null){
//						dialog.find('#evaluateUserName').removeAttr("data");
//						dialog.find('#evaluateUserName').val("");
//					}else{
//						dialog.find('#evaluateUserName').attr("data",client.id);
//					}
//				}
//				});
//  				
//  				}
//			});
			
			dialog.find(".update").click(function(){
				config.load();
//				var arrivalTime=dialog.find(".arrivalTime1").val()==""?"":new Date((dialog.find(".arrivalTime1").val()+" "+dialog.find("#arrivalTime2").val()+":00").replace(new RegExp("-","gm"),"/")).getTime()/1000;
//				var openPump=dialog.find(".openPump1").val()==""?"":new Date((dialog.find(".openPump1").val()+" "+dialog.find("#openPump2").val()+":00").replace(new RegExp("-","gm"),"/")).getTime()/1000;
//				var stopPump=dialog.find(".stopPump1").val()==""?"":new Date((dialog.find(".stopPump1").val()+" "+dialog.find("#stopPump2").val()+":00").replace(new RegExp("-","gm"),"/")).getTime()/1000;
//				var tearPipeTime=dialog.find(".tearPipeTime1").val()==""?"":new Date((dialog.find(".tearPipeTime1").val()+" "+dialog.find("#tearPipeTime2").val()+":00").replace(new RegExp("-","gm"),"/")).getTime()/1000;
//				var leaveTime=dialog.find(".leaveTime1").val()==""?"":new Date((dialog.find(".leaveTime1").val()+" "+dialog.find("#leaveTime2").val()+":00").replace(new RegExp("-","gm"),"/")).getTime()/1000;
//				var notificationTime=dialog.find(".notificationTime1").val()==""?"":new Date((dialog.find(".notificationTime1").val()+" "+dialog.find("#notificationTime2").val()+":00").replace(new RegExp("-","gm"),"/")).getTime()/1000;
//				
				
				var arrivalTime=dialog.find(".arrivalTime1").val()==-1?"":util.formatLong(util.getTimeVal(dialog.find(".gArrivalTime")));
				var openPump=dialog.find(".openPump1").val()==""?-1:util.formatLong(util.getTimeVal(dialog.find(".gOpenPump")));
				var stopPump=dialog.find(".stopPump1").val()==""?-1:util.formatLong(util.getTimeVal(dialog.find(".gStopPump")));
				var tearPipeTime=dialog.find(".tearPipeTime1").val()==""?-1:util.formatLong(util.getTimeVal(dialog.find(".gTearPipeTime")));
				var leaveTime=dialog.find(".leaveTime1").val()==""?-1:util.formatLong(util.getTimeVal(dialog.find(".gLeaveTime")));
				
				var workTime=-1;
				if(openPump!=-1&&stopPump!=-1){
					workTime=util.getDifferTime(util.getTimeVal(dialog.find(".gOpenPump")),util.getTimeVal(dialog.find(".gStopPump")));
					console.log(workTime);
					if(workTime==''){
						workTime=-1;
					}
				}
				
				
				
				var notificationList = new Array() ;
				dialog.find("#tbody tr").each(function(){
					if($(this).find("input[name=notificationUserName]").val()){
						var notificationTime=$(this).find(".notificationTime1").val()==""?null:util.formatLong(util.getTimeVal($(this).find(".gNotifyTime")));
						var notification = {
								"notificationNum":$(this).find("input[name=notificationNum]").val(),
								"notification":$(this).find("input[name=notification]").val(),
								"notificationUserName":$(this).find("input[name=notificationUserName]").val(),
								"notificationTime":notificationTime,
								"workId":dataSelected.workId,
								"id":parseInt($(this).find("input[name=id]").val())
						};
						notificationList.push(notification);
					}
				});
				
				
				$.ajax({
					async:false,
					type : "post",
					url : config.getDomain()+"/inboundoperation/updatework",
					dataType : "json",
					data:{
						"id":dataSelected.workId,
						"arrivalId":dataSelected.arrivalId,
						"arrivalTime":arrivalTime,
		  	            "openPump":openPump,
		  	            "stopPump":stopPump,
		  	            "tearPipeTime":tearPipeTime,
		  	            "leaveTime":leaveTime,
		  	            "description":dialog.find(".description").val(),
		  	            "notificationList":JSON.stringify(notificationList),
//		  	            "notificationTime":notificationTime,
//		  	            "notificationNum":$(".notificationNum").val(),
//		  	            "notification":$(".notification").val(),
//		  	            "notificationUserId":$(".notificationUserName").attr("data"),
		  	            "evaluate":dialog.find(".evaluate").val(),
//		  	            "evaluateUserId":dialog.find(".evaluateUserName").attr("data"),
		  	            "evaluateUser":dialog.find(".evaluateUserName").val(),
		  	            "workTime":workTime
					},
					success : function(data) {
						config.unload();
		  				if (data.code == "0000") {
							$("body").message({
			                    type: 'success',
			                    content: '修改成功'
			                });
							dialog.remove();
							open=true
							$('[data-role="logGrid"]').grid('refresh');
							iDex=index;
//							openEdit(index);
						} else {
							$("body").message({
			                    type: 'error',
			                    content: '修改失败'
			                });
//							dialog.remove();
							$('[data-role="logGrid"]').grid('refresh');
					}
					}
				});
			});
				
				
			});
			
	}
	
	//添加调度天气预报记录
	var addDutyWeather = function (dispatchId){
		config.unload();
		$.get(config.getResource()+ "/pages/inbound/operationlog/addDutyWeather.jsp")
		.done(function(data) {
			initFormIput();
			dialog = $(data);
			util.initTimePicker(dialog);
//			dialog.find('.timepicker-24').timepicker({
//				autoclose : true,
//				minuteStep : 1,
//				showSeconds : false,
//				showMeridian : false
//			});
//			
//			dialog.find('.timepicker').parent('.input-group').on(
//					'click',
//					'.input-group-btn',
//					function(e) {
//						e.preventDefault();
//						$(this).parent('.input-group').find('.timepicker')
//								.timepicker('showWidget');
//					});
		dialog.find('#save').on('click', function() {
			config.load();
			$.ajax({
				type : "post",
				url : config.getDomain()+"/inboundserial/adddutyweather",
				dataType : "json",
				data : {
					"weather" : dialog.find(".weather").val(),
					"port" : dialog.find(".port").val(),
					"forecastUser":dialog.find(".forecastUser").val(),
					"dutyUser":dialog.find(".dutyUser").val(),
					"time" : dialog.find("#time").val(),
					"dispatchId" : dispatchId
				},
				success : function(data) {
					config.unload();
					if (data.code == "0000") {
						$("body").message({
		                    type: 'success',
		                    content: '添加成功'
		                });
						$('[data-role="weatherGrid"]').grid('refresh');
						dialog.remove();
					} else {
						$('[data-role="weatherGrid"]').message({
		                    type: 'error',
		                    content: '添加失败'
		                });
						dialog.remove();
					}
				}
			});
		}).end().modal({
			keyboard : true
		});
	});
	}
	
	
	//添加调度值班记录
	var addlogmsg=function(dispatchId){
		config.unload();
		$.get(config.getResource()+ "/pages/inbound/operationlog/addLogMsg.jsp")
		.done(function(data) {
			dialog = $(data);
			initFormIput();
			util.initTimePicker(dialog);
//			dialog.find('.timepicker-24').timepicker({
//				autoclose : true,
//				minuteStep : 1,
//				showSeconds : false,
//				showMeridian : false
//			});
//			
//			dialog.find('.timepicker').parent('.input-group').on(
//					'click',
//					'.input-group-btn',
//					function(e) {
//						e.preventDefault();
//						$(this).parent('.input-group').find('.timepicker')
//								.timepicker('showWidget');
//					});
			
//			config.load();
//			$.ajax({
//	  			type : "get",
//	  			url : config.getDomain()+"/baseController/getWeather",
//	  			dataType : "json",
//	  			success : function(data) {
//	  				config.unload();
//	  				dialog.find('#weather').typeahead({
//	  					source: function(query, process) {
//	  						var results = _.map(data.data, function (product) {
//	  	                        return product.value;
//	  	                    });
//	  	                    process(results);
//	  					},
//	  				noselect:function(query){
//	  					var client = _.find(data.data, function (p) {
//	                        return p.value == query;
//	                    });
//	  					if(client==null){
//	  						dialog.find('#weather').removeAttr("data");
//	  						dialog.find('#weather').val("");
//	  					}else{
//	  						dialog.find('#weather').attr("data",client.key);
//	  					}
//	  				}
//	  				});
//	  			}
//	  		});
			
			dialog.find('#save').on('click', function() {
				config.load();
				$.ajax({
					type : "post",
					url : config.getDomain()+"/inboundserial/addlogduty",
					dataType : "json",
					data : {
//						"weather" : dialog.find("#weather").attr("data"),
						"content" : dialog.find(".content").val(),
						"time" : dialog.find("#time").val(),
						"dispatchId" : dispatchId
					},
					success : function(data) {
						config.unload();
						if (data.code == "0000") {
							$("body").message({
			                    type: 'success',
			                    content: '添加成功'
			                });
							$('[data-role="logworkGrid"]').grid('refresh');
							dialog.remove();
						} else {
							$('[data-role="logworkGrid"]').message({
			                    type: 'error',
			                    content: '添加失败'
			                });
							dialog.remove();
						}
					}
				});
			}).end().modal({
				keyboard : true
			});
		});
	};
	
	//删除调度值班记录
var deleteItem = function(id){
		
		var $this = $('[data-role="logworkGrid"]');
		
		$this.confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
//				deleteIntent($('[data-role="contractGrid"]').getGrid().selectedRows(), $this);
				
				var data = "";
		            data += ("id=" +id);
		        
		        $.post(config.getDomain()+'/inboundserial/delectlogduty', data).done(function (data) {
		            if (data.code == "0000") {
		            	$('[data-role="logworkGrid"]').message({
		                    type: 'success',
		                    content: '删除成功'
		                });
		                $('[data-role="logworkGrid"]').grid('refresh');
		            } else {
		                $('body').message({
		                    type: 'error',
		                    content: data.msg
		                });
		            }
		        }).fail(function (data) {
		        	$('[data-role="logworkGrid"]').message({
		                type: 'error',
		                content: '删除失败'
		            });
		        });
				
			}
		});
		
		
	}
	
//删除天气预报
var deleteWeatherItem=function(id){
	var $this = $('[data-role="weatherGrid"]');
	
	$this.confirm({
		content : '确定要撤销所选记录吗?',
		callBack : function() {
//			deleteIntent($('[data-role="contractGrid"]').getGrid().selectedRows(), $this);
			
			var data = "";
	            data += ("id=" +id);
	        
	        $.post(config.getDomain()+'/inboundserial/delectdutyweather', data).done(function (data) {
	            if (data.code == "0000") {
	            	$('[data-role="weatherGrid"]').message({
	                    type: 'success',
	                    content: '删除成功'
	                });
	                $('[data-role="weatherGrid"]').grid('refresh');
	            } else {
	                $('body').message({
	                    type: 'error',
	                    content: data.msg
	                });
	            }
	        }).fail(function (data) {
	        	$('[data-role="weatherGrid"]').message({
	                type: 'error',
	                content: '删除失败'
	            });
	        });
			
		}
	});
}

	//当班调度作业记录
	var logmsg=function(transportId){
		
		
		
		
		config.load();
		$.ajax({
			type : "get",
			url : config.getDomain()+"/notify/list?createTime="+startTime+"&isList=1&types=0",
			dataType : "json",
			success : function(data) {
				$("#logmsgTable").children("tbody").each(function(){
					$(this).children("tr").remove();
				});
				var goods="";
				if(data.data){
					for(var i=0;i<data.data.length;i++){
						var content=data.data[i].content;
						var mContentData=JSON.stringify(content);
						  mContentData=mContentData.substring(1,mContentData.length-1);
						  mContentData="{"+mContentData.replace(/{\\"/g,"\"").replace(/\\":\\"/g,"\":\"").replace(/\\"\,\\"/g,"\"\,\"").replace(/\\"}/g,"\"")+"}";
						  var contentData=eval("("+mContentData+")");
						  if(contentData.pipingTaskSVG){
						goods+='<tr><td><div  id="contentDiv" style="border: 1px solid #d8d8d8;position: relative;  cursor: move; height:auto;">'+contentData.pipingTaskSVG.replace(/\\/g,"")+'</div></td>';
						goods+="<td>"+data.data[i].createUserName+"</td></tr>";}
					}
					$("#logmsgTable").children("tbody").append(goods);
				}
			}
		}
		);
		
		
		
		
		
		config.load();
		$.ajax({
			type : "get",
			url : config.getDomain()+"/notify/list?createTime="+startTime+"&isList=1&types=4,5,17,10,9,8,6,7,1",
			dataType : "json",
			async : false,
			success : function(data) {
				config.unload();
				$("#logOthermsgTable").children("tbody").each(function(){
					$(this).children("tr").remove();
				});
				var goods="";
				if(data.data){
					for(var i=0;i<data.data.length;i++){
						var svgInfo ;
						var taskMsg;
							var content=data.data[i].content;
							var mContentData;
							if(data.data[i].type==1||data.data[i].type==10){
								mContentData=JSON.parse(content);
								taskMsg=mContentData.taskMsg;
							}else{
							 mContentData=JSON.stringify(content);
							  mContentData=mContentData.substring(1,mContentData.length-1);
							  mContentData="{"+mContentData.replace(/{\\"/g,"\"").replace(/\\":\\"/g,"\":\"").replace(/\\"\,\\"/g,"\"\,\"").replace(/\\"}/g,"\"")+"}";
							  var contentData=eval("("+mContentData+")");
							  if(contentData.contentDivSVG){
								  svgInfo = contentData.contentDivSVG.replace(/\\/g,"") ;
							  }else if(data.data[i].secPFDSvg){
								  svgInfo=data.data[i].secPFDSvg;
							  }else{
								  svgInfo="";
							  }
							  taskMsg=contentData.taskMsg ;
						}
							
						var typeName="";
						  switch (data.data[i].type){
						  case 4:
							  typeName="管线清洗(码头)";
							  break;
						  case 5:
							  typeName="管线清洗(库区)";
							  break;
						  case 17:
							  typeName="车发换罐";
							  break;
						  case 12:
							  typeName="倒罐";
							  break;
						  case 2:
							  typeName="打循环(码头)";
							  break;
						  case 3:
							  typeName="打循环(库区)";
							  break;
						  case 10:
							  typeName="储罐开人孔";
							  break;
						  case 9:
							  typeName="储罐放水";
							  break;
						  case 8:
							  typeName="清罐";
							  break;
						  case 11:
							  typeName="传输";
							  break;
						  case 6:
							  typeName="扫线(码头)";
							  break;
						  case 7:
							  typeName="扫线(库区)";
							  break;
						  case 1:
							  typeName="苯加热";
							  break;
							  
						  }
						  goods+="<tr><td style='text-align: center;vertical-align:middle'>"+typeName+"</td>"+
						  "<td style='text-align: center;vertical-align:middle'>"+util.isNull(taskMsg)+"</td>";
						  if(data.data[i].type==17||data.data[i].type==2||data.data[i].type==3){
							  goods+='<td><div  id="contentDiv" style="border: 1px solid #d8d8d8;position: relative;  cursor: move; height: auto;">'+svgInfo+'</div></td>';
								
						  }else if(data.data[i].type==1||data.data[i].type==10){
							  goods+="<td style='text-align: center;vertical-align:middle'>"+mContentData.taskMsg+"</td>";
						  }
						  else{
							  goods+='<td><div  id="contentDiv" style="border: 1px solid #d8d8d8;position: relative;  cursor: move; height: auto;">'+svgInfo+'</div></td>';
							  
						  }
						goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].createUserName+"</td>";
						if(data.data[i].arrivalType==2&&data.data[i].type==11){
							goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].openPump+"</td>";
							goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].stopPump+"</td></tr>";
						
						}else{
							
							goods+="<td></td>" +
							"<td></td></tr>";
						}
					}
					$("#logOthermsgTable").children("tbody").append(goods);
					
					
				}
			}
		}
		);
		
		
		$.ajax({
			type : "get",
			url : config.getDomain()+"/notify/list?createTime="+startTime+"&isList=1&types=11",
			dataType : "json",
			async : false,
			success : function(data) {
				config.unload();
				$("#logOthermsgTable").children("tbody").each(function(){
					$(this).children("tr").remove();
				});
				var goods="";
				if(data.data){
					for(var i=0;i<data.data.length;i++){
						var svgInfo ;
						var taskMsg;
							var content=data.data[i].content;
							var mContentData;
							if(data.data[i].type==1||data.data[i].type==10){
								mContentData=JSON.parse(content);
								taskMsg=mContentData.taskMsg;
							}else{
							 mContentData=JSON.stringify(content);
							  mContentData=mContentData.substring(1,mContentData.length-1);
							  mContentData="{"+mContentData.replace(/{\\"/g,"\"").replace(/\\":\\"/g,"\":\"").replace(/\\"\,\\"/g,"\"\,\"").replace(/\\"}/g,"\"")+"}";
							  var contentData=eval("("+mContentData+")");
							  if(contentData.contentDivSVG){
								  svgInfo = contentData.contentDivSVG.replace(/\\/g,"") ;
							  }else if(data.data[i].secPFDSvg){
								  svgInfo=data.data[i].secPFDSvg;
							  }else{
								  svgInfo="";
							  }
							  taskMsg=contentData.taskMsg ;
						}
							
						var typeName="";
						  switch (data.data[i].type){
						  case 4:
							  typeName="管线清洗(码头)";
							  break;
						  case 5:
							  typeName="管线清洗(库区)";
							  break;
						  case 17:
							  typeName="车发换罐";
							  break;
						  case 12:
							  typeName="倒罐";
							  break;
						  case 2:
							  typeName="打循环(码头)";
							  break;
						  case 3:
							  typeName="打循环(库区)";
							  break;
						  case 10:
							  typeName="储罐开人孔";
							  break;
						  case 9:
							  typeName="储罐放水";
							  break;
						  case 8:
							  typeName="清罐";
							  break;
						  case 11:
							  typeName="传输";
							  break;
						  case 6:
							  typeName="扫线(码头)";
							  break;
						  case 7:
							  typeName="扫线(库区)";
							  break;
						  case 1:
							  typeName="苯加热";
							  break;
							  
						  }
						  goods+="<tr><td style='text-align: center;vertical-align:middle'>"+typeName+"</td>"+
						  "<td style='text-align: center;vertical-align:middle'>"+util.isNull(taskMsg)+"</td>";
						  if(data.data[i].type==17||data.data[i].type==2||data.data[i].type==3){
							  goods+='<td><div  id="contentDiv" style="border: 1px solid #d8d8d8;position: relative;  cursor: move; height: auto;">'+svgInfo+'</div></td>';
								
						  }else if(data.data[i].type==1||data.data[i].type==10){
							  goods+="<td style='text-align: center;vertical-align:middle'>"+mContentData.taskMsg+"</td>";
						  }
						  else{
							  goods+='<td><div  id="contentDiv" style="border: 1px solid #d8d8d8;position: relative;  cursor: move; height: auto;">'+svgInfo+'</div></td>';
							  
						  }
						goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].createUserName+"</td>";
						if(data.data[i].arrivalType==2&&data.data[i].type==11){
							goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].openPump+"</td>";
							goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].stopPump+"</td></tr>";
						
						}else{
							
							goods+="<td></td>" +
							"<td></td></tr>";
						}
					}
					$("#logOthermsgTable").children("tbody").append(goods);
					
					
				}
			}
		}
		);
		
		$.ajax({
			type : "get",
			async : false,
			url : config.getDomain()+"/notify/list?createTime="+startTime+"&isList=1&types=3",
			dataType : "json",
			success : function(data) {
				var goods="";
				if(data.data){
					for(var i=0;i<data.data.length;i++){
						var svgInfo ;
						var taskMsg;
							var content=data.data[i].content;
							var mContentData;
							if(data.data[i].type==1||data.data[i].type==10){
								mContentData=JSON.parse(content);
								taskMsg=mContentData.taskMsg;
							}else{
							 mContentData=JSON.stringify(content);
							  mContentData=mContentData.substring(1,mContentData.length-1);
							  mContentData="{"+mContentData.replace(/{\\"/g,"\"").replace(/\\":\\"/g,"\":\"").replace(/\\"\,\\"/g,"\"\,\"").replace(/\\"}/g,"\"")+"}";
							  var contentData=eval("("+mContentData+")");
							  if(contentData.contentDivSVG){
								  svgInfo = contentData.contentDivSVG.replace(/\\/g,"") ;
							  }else if(data.data[i].secPFDSvg){
								  svgInfo=data.data[i].secPFDSvg;
							  }else{
								  svgInfo="";
							  }
							  taskMsg=contentData.taskMsg ;
						}
							
						var typeName="";
						  switch (data.data[i].type){
						  case 4:
							  typeName="管线清洗(码头)";
							  break;
						  case 5:
							  typeName="管线清洗(库区)";
							  break;
						  case 17:
							  typeName="车发换罐";
							  break;
						  case 12:
							  typeName="倒罐";
							  break;
						  case 2:
							  typeName="打循环(码头)";
							  break;
						  case 3:
							  typeName="打循环(库区)";
							  break;
						  case 10:
							  typeName="储罐开人孔";
							  break;
						  case 9:
							  typeName="储罐放水";
							  break;
						  case 8:
							  typeName="清罐";
							  break;
						  case 11:
							  typeName="传输";
							  break;
						  case 6:
							  typeName="扫线(码头)";
							  break;
						  case 7:
							  typeName="扫线(库区)";
							  break;
						  case 1:
							  typeName="苯加热";
							  break;
							  
						  }
						  goods+="<tr><td style='text-align: center;vertical-align:middle'>"+typeName+"</td>"+
						  "<td style='text-align: center;vertical-align:middle'>"+util.isNull(taskMsg)+"</td>";
						  if(data.data[i].type==17||data.data[i].type==2||data.data[i].type==3){
							  goods+='<td><div  id="contentDiv" style="border: 1px solid #d8d8d8;position: relative;  cursor: move; height: auto;">'+svgInfo+'</div></td>';
								
						  }else if(data.data[i].type==1||data.data[i].type==10){
							  goods+="<td style='text-align: center;vertical-align:middle'>"+mContentData.taskMsg+"</td>";
						  }
						  else{
							  goods+='<td><div  id="contentDiv" style="border: 1px solid #d8d8d8;position: relative;  cursor: move; height: auto;">'+svgInfo+'</div></td>';
							  
						  }
						goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].createUserName+"</td>";
					
						
						if(data.data[i].arrivalType==2&&data.data[i].type==11){
							goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].openPump+"</td>";
							goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].stopPump+"</td></tr>";
						
						}else{
							
							goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].openPumpTime+"</td>";
							goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].stopPumpTime+"</td></tr>";
						}
						
					}
					$("#logOthermsgTable").children("tbody").append(goods);
					
				}
			}
		});
		
		
		
		
		
		
		$.ajax({
			type : "get",
			async : false,
			url : config.getDomain()+"/notify/list?createTime="+startTime+"&isList=1&types=12",
			dataType : "json",
			success : function(data) {
				config.unload();
				var goods="";
				if(data.data){
					for(var i=0;i<data.data.length;i++){
						var svgInfo ;
						var taskMsg;
							var content=data.data[i].content;
							var mContentData;
							if(data.data[i].type==1||data.data[i].type==10){
								mContentData=JSON.parse(content);
								taskMsg=mContentData.taskMsg;
							}else{
							 mContentData=JSON.stringify(content);
							  mContentData=mContentData.substring(1,mContentData.length-1);
							  mContentData="{"+mContentData.replace(/{\\"/g,"\"").replace(/\\":\\"/g,"\":\"").replace(/\\"\,\\"/g,"\"\,\"").replace(/\\"}/g,"\"")+"}";
							  var contentData=eval("("+mContentData+")");
							  if(contentData.contentDivSVG){
								  svgInfo = contentData.contentDivSVG.replace(/\\/g,"") ;
							  }else if(data.data[i].secPFDSvg){
								  svgInfo=data.data[i].secPFDSvg;
							  }else{
								  svgInfo="";
							  }
							  taskMsg=contentData.taskMsg ;
						}
							
						var typeName="";
						  switch (data.data[i].type){
						  case 4:
							  typeName="管线清洗(码头)";
							  break;
						  case 5:
							  typeName="管线清洗(库区)";
							  break;
						  case 17:
							  typeName="车发换罐";
							  break;
						  case 12:
							  typeName="倒罐";
							  break;
						  case 2:
							  typeName="打循环(码头)";
							  break;
						  case 3:
							  typeName="打循环(库区)";
							  break;
						  case 10:
							  typeName="储罐开人孔";
							  break;
						  case 9:
							  typeName="储罐放水";
							  break;
						  case 8:
							  typeName="清罐";
							  break;
						  case 11:
							  typeName="传输";
							  break;
						  case 6:
							  typeName="扫线(码头)";
							  break;
						  case 7:
							  typeName="扫线(库区)";
							  break;
						  case 1:
							  typeName="苯加热";
							  break;
							  
						  }
						  goods+="<tr><td style='text-align: center;vertical-align:middle'>"+typeName+"</td>"+
						  "<td style='text-align: center;vertical-align:middle'>"+util.isNull(taskMsg)+"</td>";
						  if(data.data[i].type==17||data.data[i].type==2||data.data[i].type==3){
							  goods+='<td><div  id="contentDiv" style="border: 1px solid #d8d8d8;position: relative;  cursor: move; height: auto;">'+svgInfo+'</div></td>';
								
						  }else if(data.data[i].type==1||data.data[i].type==10){
							  goods+="<td style='text-align: center;vertical-align:middle'>"+mContentData.taskMsg+"</td>";
						  }
						  else{
							  goods+='<td><div  id="contentDiv" style="border: 1px solid #d8d8d8;position: relative;  cursor: move; height: auto;">'+svgInfo+'</div></td>';
							  
						  }
						goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].createUserName+"</td>";
					
						goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].openPumpTime+"</td>";
						goods+="<td style='text-align: center;vertical-align:middle'>"+data.data[i].stopPumpTime+"</td></tr>";
						
					}
					$("#logOthermsgTable").children("tbody").append(goods);
				}
			}
		});
		
		
//		var columns=[{
//			title:"内容",
//			name:"tubeWork",
//			render: function(item, name, index){
//				var content=item.content;
//				var mContentData=JSON.stringify(content);
//				  console.log(mContentData);
//				  mContentData=mContentData.substring(1,mContentData.length-1);
//				  mContentData="{"+mContentData.replace(/{\\"/g,"\"").replace(/\\":\\"/g,"\":\"").replace(/\\"\,\\"/g,"\"\,\"").replace(/\\"}/g,"\"")+"}";
//				  var contentData=eval("("+mContentData+")");
//				  
//				var s=  '<div  id="contentDiv" style="border: 1px solid #d8d8d8;position: relative;  cursor: move; width: 631px; height: 350px;">'+'"'+contentData.pipingTaskSVG+'"'+'</div>';
//					
//				  return s;
//			}
//		},{
//			title:"记录人",
//			name:"checkUserName"
//		}];
//		
//
//		$('[data-role="logmsgGrid"]').grid({
//			identity : 'id',
//			columns : columns,
//			isShowIndexCol : false,
//			isShowPages : false,
////			url : config.getDomain()+"/inboundserial/logmsglist?transportId="+transportId+"&types="+8
//			url : config.getDomain()+"/notify/list?createTime="+startTime+"&type=0"
//			});
//		
//		
//		var columns1=[{
//			title:"内容",
//			name:"content",
//			render: function(item, name, index){
//				if(item.type==0&&item.checkType==4){
//					var dockWork=item.dockWork==null?"":item.dockWork;
//					return "码头记录:"+dockWork;
//				}
//				if(item.checkType==11){
//					var powerWork=item.powerWork==null?"":item.powerWork;
//					return "动力班记录:"+powerWork;
//				}
//				if(item.type==1&&item.checkType==15){
//					var dockWork=item.dockWork==null?"":item.dockWork;
//					return "打循环记录:"+dockWork;
//				}
//		}
//		},{
//			title:"记录人",
//			name:"checkUserName"
//		}];
//		
//
//		$('[data-role="logOhtermsgGrid"]').grid({
//			identity : 'id',
//			columns : columns1,
//			isShowIndexCol : false,
//			isShowPages : false,
//			url : config.getDomain()+"/inboundserial/logmsglist?transportId="+transportId+"&types="+"4,11,15"
//		});
		
	};
	
	
	var logwork=function(dispatchId){
		var columns=[{
			title:"时间",
			name:"time"
		},{
			title:"内容",
			name:"content"
		},{
			title:"值班人",
			name:"createUserName"
		},{
			title : "操作",
			name : "id",
			render: function(item, name, index){
					return " <shiro:hasPermission name='ADUTYDELETE'><a href='javascript:void(0)' onclick='ItemOperation.deleteItem("+item.id+")' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a></shiro:hasPermission>";
			}
		}];
		

		$('[data-role="logworkGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain()+"/inboundserial/getlogduty?dispatchId="+dispatchId
		});
		
		
		
		var columns1=[{
			title:"时间",
			name:"time"
		},{
			title:"天气预报",
			name:"weather"
		},{
			title:"码头天气",
			name:"port"
		},{
			title:"值班人员",
			name:"dutyUser"
		},{
			title : "操作",
			name : "id",
			render: function(item, name, index){
					return " <shiro:hasPermission name='ADUTYDELETE'><a href='javascript:void(0)' onclick='ItemOperation.deleteWeatherItem("+item.id+")' class='btn btn-xs blue'><span class='glyphicon glyphicon glyphicon-remove' title='删除'></span></a></shiro:hasPermission>";
			}
		}];
		

		$('[data-role="weatherGrid"]').grid({
			identity : 'id',
			columns : columns1,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain()+"/inboundserial/getdutyweather?dispatchId="+dispatchId
		});
		
		
		var columns2=[{
			title:"系统检查",
			name:"sysName",
			render: function(item, name, index){
				return "<a href='javascript:void(0);'  onClick='ItemOperation.dialogSys("+index+",1)'>"+item.sysName+"</a>";
				}
		},{
			title:"检查情况",
			name:"sysStatus"
		},{
			title:"处理情况",
			name:"result"
		}];
		

		$('[data-role="sysGrid"]').grid({
			identity : 'id',
			columns : columns2,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain()+"/inboundserial/getdutySys?type=1&dispatchId="+dispatchId
		});
		
		
		var columns3=[{
			title:"系统参数检查",
			name:"sysParName",
			render: function(item, name, index){
				return "<a href='javascript:void(0);'  onClick='ItemOperation.dialogSys("+index+",2)'>"+item.sysName+"</a>";
				}
		},{
			title:"检查情况",
			name:"sysStatus"
		},{
			title:"处理情况",
			name:"result"
		}];
		

		$('[data-role="sysParGrid"]').grid({
			identity : 'id',
			columns : columns3,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain()+"/inboundserial/getdutySys?type=2&dispatchId="+dispatchId
		});
		
		var columns4=[{
			title:"系统检查",
			name:"sysName",
			render: function(item, name, index){
				return "<a href='javascript:void(0);'  onClick='ItemOperation.dialogSys("+index+",3)'>"+item.sysName+"</a>";
				}
		},{
			title:"检查情况",
			name:"sysStatus"
		},{
			title:"处理情况",
			name:"result"
		}];
		

		$('[data-role="sysGridw"]').grid({
			identity : 'id',
			columns : columns4,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain()+"/inboundserial/getdutySys?type=3&dispatchId="+dispatchId
		});
		
		
		var columns5=[{
			title:"系统参数检查",
			name:"sysParName",
			render: function(item, name, index){
				return "<a href='javascript:void(0);'  onClick='ItemOperation.dialogSys("+index+",4)'>"+item.sysName+"</a>";
				}
		},{
			title:"检查情况",
			name:"sysStatus"
		},{
			title:"处理情况",
			name:"result"
		}];
		

		$('[data-role="sysParGridw"]').grid({
			identity : 'id',
			columns : columns5,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain()+"/inboundserial/getdutySys?type=4&dispatchId="+dispatchId
		});
		
		
		
	};
	
	
	
	/*--------------------------------------------------------------*/
	function   changetab(obj,item){
		$(this).parent().addClass("active").siblings().removeClass("active");
		$("#portlet_tab"+item).addClass("active").siblings().removeClass("active");
		if(item==2){
			Array.prototype.contains = function(item){
         	   return RegExp("\\b"+item+"\\b").test(this);
            };
            var transportList=[];
            var strtran="";
			for(var i=0;i<$('[data-role="logGrid"]').getGrid().getAllItems().length;i++){
				var transportId=$('[data-role="logGrid"]').getGrid().getAllItems()[i].transportId;
				if(!transportList.contains(transportId)){
					transportList.push(transportId);
					strtran+=transportId+",";
				}
			}
			strtran=strtran.substring(0, strtran.length - 1);
			logmsg(strtran);
		}
		if(item==3){
			var dispatchId=$(".inputId").val();
			logwork(dispatchId);
			$(".pluswork").unbind('click'); 
			$(".pluswork").click(function(){
				config.load();
				addlogmsg(dispatchId);
			});
			$(".addWeather").unbind('click'); 
			$(".addWeather").click(function(){
				config.load();
				addDutyWeather(dispatchId);
			});
			
		}
	}
	/*----------------------------------------------------------------*/
	var init = function(start,end) {
		initFormIput();
		// 对Date的扩展，将 Date 转化为指定格式的String
		// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
		// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
		// 例子： 
		// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
		// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
		Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "h+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
		
		startTime=null;
		startTime=start;
		config.load();
		
		
		$(".datetime").text(new Date(start*1000).Format("yyyy-MM-dd")+"   "+util.getDayOfWeek(new Date(start*1000).Format("yyyy-MM-dd")));
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/inboundserial/loginfo?startTime="+start+"&endTime="+end,
			dataType : "json",
			success : function(data) {
				if(data.data[0]!=null){
//				$(".arrivalId").val(data.data[0].arrivalId);
				$(".inputId").val(data.data[0].dispatchId);
				$(".weather").val(data.data[0].weatherName);
//				$(".weather").attr("data",data.data[0].weather);
				$(".windD").val(data.data[0].windDName);
//				$(".windD").attr("data",data.data[0].windDirection);
				$(".windP").val(data.data[0].windPName);
				$(".temp").val(data.data[0].sTemperature);
				
//				$(".windP").attr("data",data.data[0].windPower);
//				$(".dispatch").val(data.data[0].dispatchUserName);
//				$(".delivery").val(data.data[0].deliveryUserName);
//				$(".dayWord").val(data.data[0].dayWordUserName);
//				$(".dock").val(data.data[0].dockUserName);
//				$(".power").val(data.data[0].powerUserName);
				$(".dispatch").val(data.data[0].dispatchUser);
				$(".delivery").val(data.data[0].deliveryUser);
				$(".dayWord").val(data.data[0].dayWordUser);
				$(".dock").val(data.data[0].dockUser);
				$(".power").val(data.data[0].powerUser);
				$(".description").val(data.data[0].description);
				}else{
					$.ajax({
						type : "post",
						url : config.getDomain()+"/inboundserial/addDispatch",
						dataType : "json",
						data:{
//							
							"time":start
							
						},
						success : function(data) {
						
							if(data.msg.id!=0){
								$(".inputId").val(data.map.id);
							}
					}
				});
				}
			}
		});
		
		
//		$.ajax({
//			async:false,
//  			type : "get",
//  			url : config.getDomain()+"/baseController/getWeather",
//  			dataType : "json",
//  			success : function(data) {
//  				$('#weather').typeahead({
//  					source: function(query, process) {
//  						var results = _.map(data.data, function (product) {
//  	                        return product.value;
//  	                    });
//  	                    process(results);
//  					},
//  					
//  				noselect:function(query){
//  					var client = _.find(data.data, function (p) {
//                        return p.value == query;
//                    });
//  					if(client==null){
//  						$('#weather').removeAttr("data");
//  						$('#weather').val("");
//  					}else{
//  						$('#weather').attr("data",client.key);
//  					}
//  				}
//  				});
//  			}
//  		});
		
//		$.ajax({
//			async:false,
//  			type : "get",
//  			url : config.getDomain()+"/baseController/getWindDirection",
//  			dataType : "json",
//  			success : function(data) {
//  				$('#windD').typeahead({
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
//  						$('#windD').removeAttr("data");
//  						$('#windD').val("");
//  					}else{
//  						$('#windD').attr("data",client.key);
//  					}
//  				}
//  				});
//  			}
//  		});
		
//		$.ajax({
//			async:false,
//  			type : "get",
//  			url : config.getDomain()+"/baseController/getWindPower",
//  			dataType : "json",
//  			success : function(data) {
//  				$('#windP').typeahead({
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
//  						$('#windP').removeAttr("data");
//  						$('#windP').val("");
//  					}else{
//  						$('#windP').attr("data",client.key);
//  					}
//  				}
//  				});
//  			}
//  		});
		
		
//		$.ajax({
//			async:false,
//  			type : "get",
//  			url : config.getDomain()+"/auth/user/get?pagesize=0",
//  			dataType : "json",
//  			success : function(data) {
//  				
//  				$('#dayWord').typeahead({
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
//  						$('#dayWord').removeAttr("data");
//  						$('#dayWord').val("");
//  					}else{
//  						$('#dayWord').attr("data",client.id);
//  					}
//  				}
//  				});
//  				
//  				$('#dock').typeahead({
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
//  						$('#dock').removeAttr("data");
//  						$('#dock').val("");
//  					}else{
//  						$('#dock').attr("data",client.id);
//  					}
//  				}
//  				});
//  				
//  				$('#power').typeahead({
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
//  						$('#power').removeAttr("data");
//  						$('#power').val("");
//  					}else{
//  						$('#power').attr("data",client.id);
//  					}
//  				}
//  				});
//  				
//  				$('#dispatch').typeahead({
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
//  						$('#dispatch').removeAttr("data");
//  						$('#dispatch').val("");
//  					}else{
//  						$('#dispatch').attr("data",client.id);
//  					}
//  				}
//  				});
//  				
//  				$('#delivery').typeahead({
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
//  						$('#delivery').removeAttr("data");
//  						$('#delivery').val("");
//  					}else{
//  						$('#delivery').attr("data",client.id);
//  					}
//  				}
//  				});
//  				
//  			}
//  		});
		

		if($(".inputId").val()!=0){
		$.ajax({
			async:false,
			url:config.getDomain()+"/inboundserial/getDispatchConnect?dispatchId="+$(".inputId").val(),
			dataType:'json',
			success : function(data) {
				for(var i=0;i<data.data.length;i++){
					if(data.data[i].type==11){
						$("#dispatch1").attr("value",($("#dispatch1").attr("value")==null?"":$("#dispatch1").attr("value"))+data.data[i].userId+",");
					}
					if(data.data[i].type==12){
						$("#dispatch2").attr("value",($("#dispatch2").attr("value")==null?"":$("#dispatch2").attr("value"))+data.data[i].userId+",");
					}
					if(data.data[i].type==21){
						$("#delivery1").attr("value",($("#delivery1").attr("value")==null?"":$("#delivery1").attr("value"))+data.data[i].userId+",");
					}
					if(data.data[i].type==22){
						$("#delivery2").attr("value",($("#delivery2").attr("value")==null?"":$("#delivery2").attr("value"))+data.data[i].userId+",");
					}
					if(data.data[i].type==31){
						$("#dayWord1").attr("value",($("#dayWord1").attr("value")==null?"":$("#dayWord1").attr("value"))+data.data[i].userId+",");
					}
					if(data.data[i].type==32){
						$("#dayWord2").attr("value",($("#dayWord2").attr("value")==null?"":$("#dayWord2").attr("value"))+data.data[i].userId+",");
					}
					if(data.data[i].type==41){
						$("#dock1").attr("value",($("#dock1").attr("value")==null?"":$("#dock1").attr("value"))+data.data[i].userId+",");
					}
					if(data.data[i].type==42){
						$("#dock2").attr("value",($("#dock2").attr("value")==null?"":$("#dock2").attr("value"))+data.data[i].userId+",");
					}
					if(data.data[i].type==51){
						$("#power1").attr("value",($("#power1").attr("value")==null?"":$("#power1").attr("value"))+data.data[i].userId+",");
					}
					if(data.data[i].type==52){
						$("#power2").attr("value",($("#power2").attr("value")==null?"":$("#power2").attr("value"))+data.data[i].userId+",");
					}
					
				}
				
				if($("#dispatch1").attr("value")){
					$("#dispatch1").attr("value",$("#dispatch1").attr("value").substring(0,$("#dispatch1").attr("value").length-1));
				}
				if($("#dispatch2").attr("value")){
					$("#dispatch2").attr("value",$("#dispatch2").attr("value").substring(0,$("#dispatch2").attr("value").length-1));
				}
				if($("#delivery1").attr("value")){
					$("#delivery1").attr("value",$("#delivery1").attr("value").substring(0,$("#delivery1").attr("value").length-1));
				}
				if($("#delivery2").attr("value")){
					$("#delivery2").attr("value",$("#delivery2").attr("value").substring(0,$("#delivery2").attr("value").length-1));
				}
				if($("#dayWord1").attr("value")){
					$("#dayWord1").attr("value",$("#dayWord1").attr("value").substring(0,$("#dayWord1").attr("value").length-1));
				}
				if($("#dayWord2").attr("value")){
					$("#dayWord2").attr("value",$("#dayWord2").attr("value").substring(0,$("#dayWord2").attr("value").length-1));
				}
				if($("#dock1").attr("value")){
					$("#dock1").attr("value",$("#dock1").attr("value").substring(0,$("#dock1").attr("value").length-1));
				}
				if($("#dock2").attr("value")){
					$("#dock2").attr("value",$("#dock2").attr("value").substring(0,$("#dock2").attr("value").length-1));
				}
				if($("#power1").attr("value")){
					$("#power1").attr("value",$("#power1").attr("value").substring(0,$("#power1").attr("value").length-1));
				}
				if($("#power2").attr("value")){
					$("#power2").attr("value",$("#power2").attr("value").substring(0,$("#power2").attr("value").length-1));
				}
				
				
			}
		});
		}
		
            	
//		$.ajax({
//    		async:false,
//			url:config.getDomain()+"/auth/user/get?pagesize=0",
//			dataType:'json',
//			success : function(data) {
//				config.unload();
//				var array1=new Array();
//				for(var i=0;i<data.data.length;i++){
//						array1.push({id:data.data[i].id,text:data.data[i].name});
//				}
//				
//				
//				$("#dispatch1").select2({
//		            tags: function(query){
//		            	return array1;
//		            	
//		            }
//				});
//				
//				$("#dispatch2").select2({
//		            tags: function(query){
//		            	return array1;
//		            	
//		            }
//				});
//		            	
//				
//				$("#delivery1").select2({
//					tags: function(query){
//		            	return array1;
//		            	
//		            }
//		        });
//				$("#delivery2").select2({
//					tags: function(query){
//		            	return array1;
//		            	
//		            }
//		        });
//				
//				$("#dayWord1").select2({
//					tags: function(query){
//		            	return array1;
//		            	
//		            }
//		        });
//				$("#dayWord2").select2({
//					tags: function(query){
//		            	return array1;
//		            	
//		            }
//		        });
//				
//				$("#dock1").select2({
//					tags: function(query){
//		            	return array1;
//		            	
//		            }
//		        });
//				$("#dock2").select2({
//					tags: function(query){
//		            	return array1;
//		            	
//		            }
//		        });
//				
//				$("#power1").select2({
//					tags: function(query){
//		            	return array1;
//		            	
//		            }
//		        });
//				$("#power2").select2({
//					tags: function(query){
//		            	return array1;
//		            	
//		            }
//		        });
//			}
//			});
		
            	$.ajax({
    				url:config.getDomain()+"/auth/user/get?pagesize=0&category=JOB",
    				dataType:'json',
    				success : function(data) {
    					config.unload();
    					var array1=new Array();
    					var array2=new Array();
    					var array3=new Array();
    					var array4=new Array();
    					var array5=new Array();
    					for(var i=0;i<data.data.length;i++){
    						if(data.data[i].sn=="DD"){
    							array1.push({id:data.data[i].id,text:data.data[i].name});
    						}
    						if(data.data[i].sn=="FH"){
    							array2.push({id:data.data[i].id,text:data.data[i].name});
    						}
    						if(data.data[i].sn=="RB"){
    							array3.push({id:data.data[i].id,text:data.data[i].name});
    						}
    						if(data.data[i].sn=="MT"){
    							array4.push({id:data.data[i].id,text:data.data[i].name});
    						}
    						if(data.data[i].sn=="DL"){
    							array5.push({id:data.data[i].id,text:data.data[i].name});
    						}
    					}
    					
    					
    					$("#dispatch1").select2({
    			            tags: function(query){
    			            	return array1;
    			            	
    			            }
    					});
    					
    					$("#dispatch2").select2({
    			            tags: function(query){
    			            	return array1;
    			            	
    			            }
    					});
    			            	
    					
    					$("#delivery1").select2({
    						tags: function(query){
    			            	return array2;
    			            	
    			            }
    			        });
    					$("#delivery2").select2({
    						tags: function(query){
    			            	return array2;
    			            	
    			            }
    			        });
    					
    					$("#dayWord1").select2({
    						tags: function(query){
    			            	return array3;
    			            	
    			            }
    			        });
    					$("#dayWord2").select2({
    						tags: function(query){
    			            	return array3;
    			            	
    			            }
    			        });
    					
    					$("#dock1").select2({
    						tags: function(query){
    			            	return array4;
    			            	
    			            }
    			        });
    					$("#dock2").select2({
    						tags: function(query){
    			            	return array4;
    			            	
    			            }
    			        });
    					
    					$("#power1").select2({
    						tags: function(query){
    			            	return array5;
    			            	
    			            }
    			        });
    					$("#power2").select2({
    						tags: function(query){
    			            	return array5;
    			            	
    			            }
    			        });
    				}
    				});
		
        $(".saveButton,.saveDescription").unbind('click'); 
		$(".saveButton,.saveDescription").click(function(){
//			var cando=false;
//			$(".form-horizontal").children("tr").each(function(){
//				if($(this).children("input").val()!=null){
//					cando=true;
//					alert(cando);
//					return;
//				}
//			});
			
			if($(".inputId").val()!=0){
				config.load();
				$.ajax({
					type : "post",
					url : config.getDomain()+"/inboundserial/updateDispatch",
					dataType : "json",
					data:{
						"id": $(".inputId").val(),
//						"weather":$(".weather").attr("data"),
//						"windDirection":$(".windD").attr("data"),
//						"windPower":$(".windP").attr("data"),
						"sTemperature":$(".temp").val(),
						"sWindDirection":$(".windD").val(),
						"sWeather":$(".weather").val(),
						"sWindPower":$(".windP").val(),
						"time":start,
						
						"description":$(".description").val(),
						
						"DD1":$("#dispatch1").val(),
						"DD2":$("#dispatch2").val(),
						"FH1":$("#delivery1").val(),
						"FH2":$("#delivery2").val(),
						"RB1":$("#dayWord1").val(),
						"RB2":$("#dayWord2").val(),
						"MT1":$("#dock1").val(),
						"MT2":$("#dock2").val(),
						"DL1":$("#power1").val(),
						"DL2":$("#power2").val()
						
						
//						"dispatchUserId":$(".dispatch").attr("data"),
//						"deliveryUserId":$(".delivery").attr("data"),
//						"dayWordUserId":$(".dayWord").attr("data"),
//						"dockUserId":$(".dock").attr("data"),
//						"powerUserId":$(".power").attr("data")
//						"dispatchUser":$(".dispatch").val(),
//						"deliveryUser":$(".delivery").val(),
//						"dayWordUser":$(".dayWord").val(),
//						"dockUser":$(".dock").val(),
//						"powerUser":$(".power").val(),
						
						
						
					},
					success : function(data) {
						config.unload();
						if (data.code == "0000") {
							$("body").message({
			                    type: 'success',
			                    content: '保存成功'
			                });
						} else {
							$("body").message({
			                    type: 'error',
			                    content: '保存失败'
			                });
					}
				}
			});
			}else{
				config.load();
				$.ajax({
					type : "post",
					url : config.getDomain()+"/inboundserial/addDispatch",
					dataType : "json",
					data:{
//						"weather":$(".weather").attr("data"),
//						"windDirection":$(".windD").attr("data"),
//						"windPower":$(".windP").attr("data"),
						"sWindDirection":$(".windD").val(),
						"sWeather":$(".weather").val(),
						"sWindPower":$(".windP").val(),
						"time":start,
						"description":$(".description").val(),
						
						"DD1":$("#dispatch1").val(),
						"DD2":$("#dispatch2").val(),
						"FH1":$("#delivery1").val(),
						"FH2":$("#delivery2").val(),
						"RB1":$("#dayWord1").val(),
						"RB2":$("#dayWord2").val(),
						"MT1":$("#dock1").val(),
						"MT2":$("#dock2").val(),
						"DL1":$("#power1").val(),
						"DL2":$("#power2").val()
						
//						"dispatchUserId":$(".dispatch").attr("data"),
//						"deliveryUserId":$(".delivery").attr("data"),
//						"dayWordUserId":$(".dayWord").attr("data"),
//						"dockUserId":$(".dock").attr("data"),
//						"powerUserId":$(".power").attr("data"),
//						"arrivalId" : $(".arrivalId").val(),
//						"dispatchUser":$(".dispatch").val(),
//						"deliveryUser":$(".delivery").val(),
//						"dayWordUser":$(".dayWord").val(),
//						"dockUser":$(".dock").val(),
//						"powerUser":$(".power").val(),
					},
					success : function(data) {
						config.unload();
						if (data.code == "0000") {
							$("body").message({
			                    type: 'success',
			                    content: '保存成功'
			                });
							if(data.msg.id!=0){
								$(".inputId").val(data.msg.id);
								
							}
						} else {
							$("body").message({
			                    type: 'error',
			                    content: '保存失败'
			                });
					}
				}
			});
			}
		});
		
		
		
		
		//表单字段
		var columns = [ {
			title : "船名",
			width: 100,
			name : "shipName",
			render: function(item, name, index){
				return "<a href='javascript:void(0)' onclick='ItemOperation.openEdit("+index+")'>"+item.shipName+"</a>";
			}
		}, {
			title : "品种",
			width: 100,
			name : "productName"
		},{
			title : "卸货数量(吨)",
			width: 100,
			name : "tankAmount"
		},{
			title : "工艺管线",
			width: 100,
			name : "tubeName",
			render:function(item,name,index){
				if(item.tubeName!=null){
					return item.tubeName.replace(new RegExp(",","gm"),"<br></br>");
				}
				else{
					return "";
				}
			}
		},{
			title : "泊位",
			width: 100,
			name : "berthName"
			
		},{
			title : "到港时间",
			width: 100,
			name : "arrivalTime",
			render:function(item,name,index){
				if(item.arrivalTime==null||item.arrivalTime==0){
					return "";
				}
				return item.mArrivalTime;
//				return util.getSubTime(new Date(item.arrivalTime*1000).Format("yyyy-MM-dd hh:mm:ss"),2);
			}
		},{
			title : "开泵时间",
			width: 100,
			name : "openPump",
			render:function(item,name,index){
				if(item.openPump==null||item.openPump==0){
					return "";
				}
				return item.mOpenPump;
//				return util.getSubTime(new Date(item.openPump*1000).Format("yyyy-MM-dd hh:mm:ss"),2);
			}
		},{
			title : "停泵时间",
			width: 100,
			name : "mStopPump",
			render:function(item,name,index){
				if(item.stopPump==null||item.stopPump==0){
					return "";
				}
//				return util.getSubTime(new Date(item.stopPump*1000).Format("yyyy-MM-dd hh:mm:ss"),2);
				return item.mStopPump;
			}
		},{
			title : "拆管时间",
			width: 100,
			name : "tearPipeTime",
			render:function(item,name,index){
				if(item.tearPipeTime==null||item.tearPipeTime==0){
					return "";
				}
//				return util.getSubTime(new Date(item.tearPipeTime*1000).Format("yyyy-MM-dd hh:mm:ss"),2);
				return item.mTearPipeTime;
			}
		},{
			title : "离港时间",
			width: 100,
			name : "leaveTime",
			render:function(item,name,index){
				if(item.leaveTime==null||item.leaveTime==0){
					return "";
				}
//				return util.getSubTime(new Date(item.leaveTime*1000).Format("yyyy-MM-dd hh:mm:ss"),2);
				return item.mLeaveTime;
			}
		},{
			title : "接收罐号",
			width: 100,
			name : "tankCode",
			render:function(item,name,index){
				 var testObject=item.tankCode;
				 var tankId=item.tankId;
				var testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					var text=testObject[i];
					if(i>0){
						if(testObject[i]==testObject[i-1]){
							text="&nbsp";
						}
					}
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">  <a href="#/tanklog?tankId='+tankId[i]+'&tankName='+text+'&type=1">'+text+'</a> </td></tr>';
					}else{
						testObjectHtml+='<tr><td> <a href="#/tanklog?tankId='+tankId[i]+'&tankName='+text+'&type=1">'+text+'</a> </td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "前尺-液位(毫米)",
			name : "startLevel",
			width: 100,
			render:function(item,name,index){
				 testObject=item.startLevel;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "后尺-液位(毫米)",
			width: 100,
			name : "endLevel",
			render:function(item,name,index){
				 testObject=item.endLevel;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "前尺-重量(吨)",
			width: 100,
			name : "startWeight",
			render:function(item,name,index){
				 testObject=item.startWeight;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "后尺-重量(吨)",
			width: 100,
			name : "endWeight",
			render:function(item,name,index){
				 testObject=item.endWeight;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "前尺-温度(℃)",
			width: 100,
			name : "startTemperature",
			render:function(item,name,index){
				 testObject=item.startTemperature;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "后尺-温度(℃)",
			width: 100,
			name : "endTemperature",
			render:function(item,name,index){
				 testObject=item.endTemperature;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "机房实收(吨)",
			width: 100,
			name : "realAmount",
			render:function(item,name,index){
				 testObject=item.realAmount;
				 testObjectHtml='<table class="table inmtable" style="margin-bottom: 0px;" >'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "计量实收(吨)",
			width: 100,
			name : "measureAmount",
			render:function(item,name,index){
				 testObject=item.measureAmount;
				 testObjectHtml='<table class="table inmtable"  style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "实收差异(吨)",
			width: 100,
			name : "differAmount",
			render:function(item,name,index){
				 testObject=item.differAmount;
				 testObjectHtml='<table class="table inmtable"  style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "货主",
			width: 100,
			name : "clientName",
			render:function(item,name,index){
				if(item.clientName!=null){
					return item.clientName.replace(new RegExp(",","gm"),"<br></br>");
					
				}
				else{
					return "";
				}
			}
		},{
			title : "服务评价",
			width: 100,
			name : "evaluate"
		},{
			title : "评价人",
			width: 100,
			name : "evaluateUserName"
		},{
			title : "备注",
			width: 100,
			name : "description"
		},{
			title : "验证时间",
			width: 200,
			name : "notificationTime",
			render:function(item,name,index){
				 testObject=item.notificationTime;
				 testObjectHtml='<table class="table inmtable"  style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "报告号",
			width: 100,
			name : "notificationNum",
			render:function(item,name,index){
				 testObject=item.notificationNum;
				 testObjectHtml='<table class="table inmtable"  style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "验证结论",
			width: 100,
			name : "notification",
			render:function(item,name,index){
				 testObject=item.notification;
				 testObjectHtml='<table class="table inmtable"  style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		},{
			title : "验证人",
			width: 100,
			name : "notificationUserName",
			render:function(item,name,index){
				 testObject=item.notificationUserName;
				 testObjectHtml='<table class="table inmtable"  style="margin-bottom: 0px;">'
				for(var i=0;i<testObject.length;i++){
					if(i!=testObject.length-1){
					testObjectHtml+='<tr><td style="border-bottom:1px solid #ddd;">'+testObject[i]+'</td></tr>';
					}else{
						testObjectHtml+='<tr><td>'+testObject[i]+'</td></tr>';
					}
				}
                   testObjectHtml+='</table>';  	
                   return testObjectHtml;
				
			}
		}];
		$(".modify").unbind('click'); 
		$('.modify').click(function(){
			var index = $('[data-role="logGrid"]').getGrid().selectedRowsIndex();
			var $this=$(this);
			if (index.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要修改的数据'
				});
				return;
			}
			updateData=$('[data-role="logGrid"]').getGrid().selectedRows()[0];
			window.location.href="#/updateoperationlog?id="+index;
		});
		//js异步加载URL数据
		$('[data-role="logGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : false,
			url : config.getDomain()+"/inboundserial/loglist?startTime="+start+"&endTime="+end,
			callback:function(){
				if(open){
					openEdit(iDex);
					open=false;
					iDex=null;
				}
			}
		});
		
		$(".btn-modify").unbind('click'); 
		$(".btn-modify").click(function(){
			var index = $('[data-role="logGrid"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (index.length == 0) {
				$this.message({
					type : 'warning',
					content : '请选择要修改的调度日志'
				});
				return;
			}
			var dataSelected=$('[data-role="logGrid"]').getGrid().selectedRows()[0];
			editLogWork(dataSelected);
		});
		
	};
	
	function openEdit(index){
		var dataSelected=$('[data-role="logGrid"]').getGrid().getAllItems()[index];
		
		editLogWork(dataSelected,index);
	}
	
	function deleteNotificationInfo(obj){
		var id = $(obj).parents(".notify").find("input[name=id]").val();
		if(id){
			$.ajax({
				type : "post",
				url :config.getDomain()+"/inboundserial/deletenotify",
				data : {
					"id" : id,
					},
				dataType : "json",
				success:function(data){
					if(data.code=='0000'){
						$('body').message({
							type:'success',
							content:"删除成功"	
						});
					}else{
						$('body').message({
							type:'error',
							content:"删除失败"
						});
					}
				}
			});
		}
		
		$(obj).parents(".notify").remove() ;
	}
	
	function dialogSys(index,type) {
		$.get(config.getResource() + "/pages/inbound/operationlog/dialog_sys.jsp")
				.done(function(data) {
					dialog = $(data);
					mDialog=dialog;
					initDialogSys(dialog, index,type);
					dialog.modal({
						
						keyboard : true
					});
					
					
				});

	}
	
function initDialogSys(dialog, index,type){
	var indexDate;
		if(type==1){
			
			 indexData=$('[data-role="sysGrid"]').getGrid().getItemByIndex(index);
		}else if(type==2){

			 indexData=$('[data-role="sysParGrid"]').getGrid().getItemByIndex(index);
		} else if(type==3){
			
			 indexData=$('[data-role="sysGridw"]').getGrid().getItemByIndex(index);
		}else if(type==4){

			 indexData=$('[data-role="sysParGridw"]').getGrid().getItemByIndex(index);
		}
		
		dialog.find("#sysStatus").val( indexData.sysStatus);
		dialog.find("#result").val(indexData.result);
		
		
		
		dialog.find("#save").click(function(){
			config.load();
			$.ajax({
				type : "post",
				url : config.getDomain()+"/inboundserial/updatedutySys",
				dataType : "json",
				data : {
					"id":indexData.id,
					"result":dialog.find("#result").val(),
					"sysStatus":dialog.find("#sysStatus").val(),
					"dispatchId":indexData.dispatchId,
					"sysName":indexData.sysName,
					"type":indexData.type,
				},
				success : function(data) {
					config.unload();
					if (data.code == "0000") {
						$("body").message({
							type: 'success',
							content: '更新成功'
								
						});
						dialog.remove();
						if(type==1){
						$('[data-role="sysGrid"]').getGrid().refresh();
						} else if(type==2){
							$('[data-role="sysParGrid"]').getGrid().refresh();
						}else if(type==3){
							$('[data-role="sysGridw"]').getGrid().refresh();
						}else if(type==4){
							$('[data-role="sysParGridw"]').getGrid().refresh();
						}
					} else {
						$("body").message({
							type: 'error',
							content: '更新失败'
						});
					}
				}
			});
		});
		
		
		
	}
	
/*----------------------------------------------------------------------*/
	return {
		init : function(start,end) {
			init(start,end);
			
		},
		initView:function(page){
			initView(page) ;
		},
		changetab:changetab,
		deleteItem : function(id){
			deleteItem(id);
		},
		deleteWeatherItem:function(id){
			deleteWeatherItem(id);
		},
		deleteNotificationInfo:function(obj){
			deleteNotificationInfo(obj);
		},
		openEdit:function(index){
			openEdit(index);
		},
		dialogSys:dialogSys
		
	};
}();