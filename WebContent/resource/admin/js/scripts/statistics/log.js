var GoodsLog= function() {

	var searchCount=0;
	
	var order=0;//0：建仓倒序  1：建仓正序  2：罐号正序 3：罐号倒序
	
	var show=0;//0:未清盘  1:全部
	
	var showType=0;
	
	var goodsId=0;
	
	var sType=0;

	var showVirTime=1;
	
	var columnCheckArray=[0,1,2,3,4,5,7,8,9,10,11,12,13];
	//导出通知单 
	function exportExcel(){
		
		var isDim=0;
		if($("#isDim").is(':checked')){
			isDim=1;
		}
		
		var st = $("#startTime").val() ;
		var ed = $("#endTime").val() ;
		var pid = util.isNull($("#product").attr("data"),1) ;
		var cid = util.isNull($("#client").attr("data"),1) ;
		var type=0;
		$('[name="radio"]').each(function(){
			if(this.checked){
				if($(this).attr("data")){
					
					type=$(this).attr("data");
				}
			}
		});
		
		var url = config.getDomain()+"/statistics/exportLogExcel?isDim="+isDim+"&ladingCode="+$(".ladingCode").val()+"&showVirTime="+showVirTime+"&goodsCode="+$('.goodsCode').val()+"&cargoCode="+$('.cargoCode').val()+"&isFinish="+show+"&&name=货体汇总&&productId="+pid+"&&clientId="+cid+"&&sStartTime="+st+"&&sEndTime="+ed+"&&type="+type+"&&showType="+showType ;
		window.open(url) ;
	}
	
	
	
	
	var reset=function(){
		$("#goodsCode").val("");
		$("#cargoCode").val("");
		$("#ladingCode").val("");
		$("#client").val("");
		$("#product").val("");
		$("#product").removeAttr("data");
		refreshCargo(0);
		refreshClient(0);
		
	}
	 
	var search = function() {
//		console.log($(".tankNum").val());
//		var tank = $(".tankNum").val();
//		initTank(tank);
//		
		
		
		if($('#startTime').val()==""||$('#endTime').val()==""){
			$("body").message({
				type : 'error',
				content : '请选择时间！'
			});
			return;
		}	
		var params = {};
		
		var isDim=0;
		if($("#isDim").is(':checked')){
			isDim=1;
		}
		params['isDim']=isDim;
		params['showVirTime']=showVirTime;
        $("#logForm").find('.form-control').each(function(){
            var $this = $(this);
            var name = $this.attr('name');
             if(name){
            	 if($this.attr("data")){
            		 params[name] = $this.attr("data");
            	 }else{
            		 params[name] = $this.val();
            	 }
            }
        });
        
        
        $('[name="radio"]').each(function(){
			if(this.checked){
				if($(this).attr("data")){
					
					params['type']=$(this).attr("data");
				}
			}
		});
        
        
        sType=params['type'];
        //进出存
        if(params['type']==21){
        	
        	
        	if(!params['ladingCode']){
        		
        		if(!params['clientId']){
        			$("body").message({
        				type: 'error',
        				content: '请选择一个货主'
        			});
        			return;
        		}
        	}
        	
        	
        	$("#inout").show();
        	
        	setGridType(2);
        	showType=2;
        	$('[data-role="GoodslogGrid"]').getGrid().search(params);
        }
        //发货清单
        else if (params['type']==22){
        	
        	if(!params['ladingCode']){
        		
        		if(!params['clientId']){
        			$("body").message({
        				type: 'error',
        				content: '请选择一个货主'
        			});
        			return;
        		}
        		if(!params['cargoCode']){
        			$("body").message({
        				type: 'error',
        				content: '请输入一个货批'
        			});
        			return;
        		}
        	}
        	
        	
        	
        	$("#inout").hide();
        	setGridType(4);
        	showType=4;
        	$('[data-role="GoodslogGrid"]').getGrid().search(params);
        	
        }
        //通过货批
        else if(params['type']==11){
        	$("#inout").hide();
        	setGridType(5);
        	showType=5;
        	$('[data-role="GoodslogGrid"]').getGrid().search(params);
        }
        
        else if (params['type']==5||params['type']==51||params['type']==52||params['type']==53){
        	
        	$("#inout").hide();
        	setGridType(6);
        	showType=1;
        	$('[data-role="GoodslogGrid"]').getGrid().search(params);
        	
        	
        }
        
        else{
        	
        	

        	$("#inout").hide();
        	setGridType(1);
        	showType=1;
        	$('[data-role="GoodslogGrid"]').getGrid().search(params);
        	
        	
        
        }
       
       
     //获取条件下的统计数据
       $('[data-role="GoodslogGrid"]').find(".totalFeeDiv").remove();
       if(params['type']){
    	   if(params['type']!=4&&params['type']!=21&&params['type']!=11){
    		   
    		   $.ajax({
    			   type:'get',
    			   url:config.getDomain()+"/statistics/getlogTotal",
    			   data:params,
    			   dataType:'json',
    			   success:function(data){
    				   util.ajaxResult(data,'统计',function(ndata){
    					   var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
    					   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总操作量: </label>" +
    					   "<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].logTotal+" 吨</label>"	+				
    					   "</div>";
    					   $('[data-role="GoodslogGrid"]').find(".grid-body").parent().append(totalHtml);
    				   },true);
    			   }
    		   });
    	   }else if(params['type']==21){
    		   $.ajax({
    			   type:'get',
    			   url:config.getDomain()+"/statistics/getlogTotal",
    			   data:params,
    			   dataType:'json',
    			   success:function(data){
    				   util.ajaxResult(data,'统计',function(ndata){
    					   var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
    					   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总剩余量: </label>" +
    					   "<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].logTotal+" 吨</label>"	+				
    					   "</div>";
    					   $('[data-role="GoodslogGrid"]').find(".grid-body").parent().append(totalHtml);
    				   },true);
    			   }
    		   });
    	   }else if(params['type']==11){
    		   $.ajax({
    			   type:'get',
    			   url:config.getDomain()+"/statistics/getPassTotal",
    			   data:params,
    			   dataType:'json',
    			   success:function(data){
    				   util.ajaxResult(data,'统计',function(ndata){
    					   var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
    					   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计划量: </label>" +
    					   "<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].planTotal+" 吨</label>"	+	
    					   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总商检量: </label>" +
    					   "<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].inspectTotal+" 吨</label>"	+
    					   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>已开票量: </label>" +
    					   "<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].KPTotal+" </label>"	+
    					   
    					   "</div>";
    					   $('[data-role="GoodslogGrid"]').find(".grid-body").parent().append(totalHtml);
    				   },true);
    			   }
    		   });
    	   }
    	   
    	   
       }
       
	};
	
	

	function refreshClient(cargoId){
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/storageConfirm/getClientByCargoId?cargoId="+cargoId,
			dataType : "json",
			success : function(data) {
				config.unload();
				
				$('#client').typeahead('hide');
  				$('#client').remove();
  				console.log(1);
  				$('.cClient').append("<input id='client' type='text' data-provide='typeahead' name='clientId'  class='form-control client'>");
  				
				
				$('#client').typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return  product.clientName+"["+product.code+"]";
  	                    });
  	                    process(results);
  					},updater: function (item) {
	  					var client = _.find(data.data, function (p) {
	                        return p.clientName+"["+p.code+"]" == item;
	                    });
	  					if(client!=null){
	  						$('#client').attr("data",client.clientId);
	  						
	  					}
	  					return item;
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.clientName+"["+p.code+"]" == query;
                    });
  					if(client==null){
  						$('#client').removeAttr("data");
  						$('#client').val("");
  						if(!$('#cargoCode').val())
  						refreshCargo(0);
  					}else{
  						$('#client').attr("data",client.clientId);
  						if(!$('#cargoCode').val())
  						refreshCargo(client.clientId);
  					}
  				}
  				});
			}
		});
	}
	function refreshCargo(clientId){
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/storageConfirm/getCargoByClientId?clientId="+clientId,
			dataType : "json",
			success : function(data) {
				config.unload();
				
				$('#cargoCode').typeahead('hide');
  				$('#cargoCode').remove();
  				$('.cCode').append("<input id='cargoCode' type='text' data-provide='typeahead' name='cargoCode'  class='form-control cargoCode'>");
  				
				
				$('#cargoCode').typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product.cargoCode;
  	                    });
  	                    process(results);
  					},updater: function (item) {
	  					var client = _.find(data.data, function (p) {
	                        return p.cargoCode == item;
	                    });
	  					if(client!=null){
//	  						$('#cargoCode').attr("data",client.cargoId);
	  						
	  					}
	  					return item;
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.cargoCode == query;
                    });
  					if(client==null){
//  						$('#cargoCode').removeAttr("data");
  						$('#cargoCode').val("");
  						if(!$('#client').val())
  						refreshClient(0);
  					}else{
//  						$('#cargoCode').attr("data",client.cargoId);
  						if(!$('#client').val())
  						refreshClient(client.cargoId);
  					}
  				}
  				});
			}
		});
	}
	
	
	
	var handleRecords = function() {
		
		$(".showVirTime").unbind('click'); 
		$(".showVirTime").popover();
		$(".showVirTime").click(function(){
			if(showVirTime==1){
				$(".showVirTime").addClass("green");
				showVirTime=2;
				$(".showVirTime").attr("data-content","转账时间");
			}else{
				
				$(".showVirTime").removeClass("green");
				$(".showVirTime").attr("data-content","原始时间");
				showVirTime=1;
			}
//			search();
		});
		
		
		$(".inout").hide();
		$("#io").click(function(){
			$(".inout").show();
		});
		$(".i").click(function(){
			$(".inout").hide();
		});
		
		var firstDate = new Date();

		firstDate.setMonth(0, 1); //第一天
		
		$('#startTime').val(new XDate(firstDate).toString('yyyy-MM-dd'));
		$('#endTime').val(new Date().Format("yyyy-MM-dd"));
		
		$('.date-picker').datepicker({
			  rtl: Metronic.isRTL(),
			    orientation: "left",
			    format: "yyyy-mm-dd",
			    showInputs:true,
			    startView:"days",
			    minViewMode:"days",
			    showDay:"false",
	            disableMousewheel:false,
	            changeMonth :false,
			    autoclose: true,
	});
		
		config.load();
		
//		util.urlHandleTypeaheadAllData("/baseController/getClientName",$('#client'),function(item){return item.name+"["+item.code+"]";});
		
		refreshCargo(0);
		
		
		refreshClient(0);
		
		
		$.ajax({
			async:false,
			type : "get",
			url : config.getDomain()+"/product/select",
			dataType : "json",
			success : function(data) {
				config.unload();
				$('#product').typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product.name;
  	                    });
  	                    process(results);
  					},updater: function (item) {
	  					var client = _.find(data.data, function (p) {
	                        return p.name == item;
	                    });
	  					if(client!=null){
	  						$('#product').attr("data",client.id);
	  					}
	  					return item;
  					},
  				noselect:function(query){
  					var client = _.find(data.data, function (p) {
                        return p.name == query;
                    });
  					if(client==null){
  						$('#product').removeAttr("data");
  						$('#product').val("");
  					}else{
  						$('#product').attr("data",client.id)
  					}
  				}
  				});
			}
		});
		
		
//		$.ajax({
//  			type : "get",
//  			url : config.getDomain()+"/client/select",
//  			dataType : "json",
//  			success : function(data) {
//  				config.unload();
//  				$('#client').typeahead({
//  					source: function(query, process) {
//  						var results = _.map(data.data, function (product) {
//  	                        return product.name;
//  	                    });
//  	                    process(results);
//  					},
//  					updater: function (item) {
//	  					var client = _.find(data.data, function (p) {
//	                        return p.name == item;
//	                    });
//	  					if(client!=null){
//	  						$('#client').attr("data",client.id);
//	  					}
//	  					return item;
//  					},
//  				noselect:function(query){
//  					var client = _.find(data.data, function (p) {
//                        return p.name == query;
//                    });
//  					if(client==null){
//  						$('#client').removeAttr("data");
//  						$('#client').val("");
//  					}else{
//  						$('#client').attr("data",client.id)
//  					}
//  				}
//  				});
//  			}
//  		});
		
		

		
		search();
		
		
		$("#inout").unbind('click'); 
    	$("#inout").click(function(){
    		if($('#startTime').val()==""||$('#endTime').val()==""){
    			$("body").message({
    				type : 'error',
    				content : '请选择时间！'
    			});
    			return;
    		}	
    		var params = {};
    		
    		var isDim=0;
    		if($("#isDim").is(':checked')){
    			isDim=1;
    		}
    		params['isDim']=isDim;
    		
            $("#logForm").find('.form-control').each(function(){
                var $this = $(this);
                var name = $this.attr('name');
                 if(name){
                	 if($this.attr("data")){
                		 params[name] = $this.attr("data");
                	 }else{
                		 params[name] = $this.val();
                	 }
                }
            });
            
            
            $('[name="radio"]').each(function(){
    			if(this.checked){
    				if($(this).attr("data")){
    					
    					params['type']=$(this).attr("data");
    				}
    			}
    		});
            
           
            setGridType(3);
            showType=3;
            $('[data-role="GoodslogGrid"]').getGrid().search(params);
           
           
         //获取条件下的统计数据
           $('[data-role="GoodslogGrid"]').find(".totalFeeDiv").remove();
           if(params['type']){
        	   if(params['type']!=4&&params['type']!=21){
        		   
        		   $.ajax({
        			   type:'get',
        			   url:config.getDomain()+"/statistics/getlogTotal",
        			   data:params,
        			   dataType:'json',
        			   success:function(data){
        				   util.ajaxResult(data,'统计',function(ndata){
        					   var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
        					   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总操作量: </label>" +
        					   "<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].logTotal+" 吨</label>"	+				
        					   "</div>";
        					   $('[data-role="GoodslogGrid"]').find(".grid-body").parent().append(totalHtml);
        				   },true);
        			   }
        		   });
        	   }else if(params['type']==21){
        		   $.ajax({
        			   type:'get',
        			   url:config.getDomain()+"/statistics/getlogTotal",
        			   data:params,
        			   dataType:'json',
        			   success:function(data){
        				   util.ajaxResult(data,'统计',function(ndata){
        					   var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
        					   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总剩余量: </label>" +
        					   "<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].logTotal+" 吨</label>"	+				
        					   "</div>";
        					   $('[data-role="GoodslogGrid"]').find(".grid-body").parent().append(totalHtml);
        				   },true);
        			   }
        		   });
        	   }
        	   
        	   
           }
           
    	});
		
		
		
		
	};

	var setGridType=function(type){
		$('[data-role="GoodslogGrid"]').remove();
		
		$("#logGrid").append("<div data-role='GoodslogGrid'>");
		if (type==1){
						var colums = [
			{
				title : "货权",
				name : "clientName"
			},
			{
				title : "提单货主",
				name : "goodsLadingClientName"
			},	
			{
				title : "货品",
				name : "productName"
			},{
				title : "货批",
				name : "cargoCode",
				render : function(item, name, index) {
					
					if(item.cargoCode!=null){
						return "<a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("
						+ item.goodsId
						+ ")' id='goodsShow'>"+item.cargoCode+"</a>";
						
					}else{
						return "";
					}
					

				}
				
			},
			{
				title : "上下家",
				name : "ladingClientName",
				render : function(item, name, index) {
					if(item.type==1){
						return item.shipName;
					}else if(item.type==2){
						return item.ladingInClientName;
					}else{
						
						return item.ladingClientName;
					}
				}	
			},
			{
				title : "方式",
				name : "type",
				render : function(item, name, index) {
					switch (item.type) {
					case 1:
						return "入库";
						break;
					case 2:
						return "调入";
						break;
					case 3:
						return "调出";
						break;
					case 4:
						return "封放";
						break;
					case 5:
						
						if(item.actualType>0){
							return "发货";
							
						}else{
							return "发货待提";
						}
						
						break;
					case 6:
						return "退回";
						break;
					case 61:
						return "调入退回";
						break;
					case 62:
						return "调出退回";
						break;
					case 7:
						return "预入库";
						break;
					case 9:
						return "扣损";
						break;
					case 10:
						
						if(item.deliverNo=="1"){
							return "扣损";
						}
						else{
							
							return "商检&封放";
						}
						break;
					case 11:
						return "清盘";
						break;
					case 12:
						return "复盘";
						break;
					}
				}
			},
			{
				title : "发货罐号",
				name : "tankName",
				render : function(item, name, index) {
//					return new Date(item.createTime * 1000)
//							.Format("yyyy-MM-dd HH:mm:ss");
					if(item.tankName1){
						
						return item.tankName1;
					}else if(item.tankName){
						return item.tankName;
					}else{
						return "";
					}
					
				}
			}, 
			{
				title : "日期",
				name : "createTime",
				render : function(item, name, index) {
//					return new Date(item.createTime * 1000)
//							.Format("yyyy-MM-dd HH:mm:ss");
				return item.mtime;
				}
			}, {
				title : "单号",
				name : "ladingCode1",
				render : function(item, name, index) {
					if(item.ladingEvidence!=null&&item.ladingEvidence!=""){
						return item.ladingEvidence;
					}
					if(item.nextLadingCode!=null&&item.nextLadingCode!=""){
						return item.nextLadingCode;
						
					}else{
						return item.ladingCode1;
					}
				}
			}, {
				title : "货量变化(吨)",
				name : "goodsChange",
				render : function(item, name, index) {
					if(sType==1){
						
					
					if(item.type==1){
						return parseFloat(item.mGoodsChange).toFixed(3);
					}else{
						return parseFloat(item.mGoodsChange).toFixed(3);
					}
					}else{
						
						return parseFloat(item.goodsChange).toFixed(3);
					}
				}
			},{
				title : "存量(吨)",
				name : "surplus",
				render : function(item, name, index) {
					
//					if(item.type==5&&item.actualNum==0){
//						return "";
//					}else{
					
					if(item.type==1){
						return "";
					}
					
						return parseFloat(item.surplus).toFixed(3);
//					}
				}
			}, {
				title : "封量(吨)",
				name : "goodsSave",
				render : function(item, name, index) {
					
					if(item.type==1){
						return "";
					}
					
					if(item.type==5&&item.actualNum==0){
						return "";
					}else{
					return parseFloat(item.goodsSave).toFixed(3);
					}
				}
			},   {
				title : "发货单",
				name : "serial",
				render : function(item, name, index) {
					if(item.type==5){
						return item.serial;
					}else{
						return "";
					}
				}
			}, {
				title : "车船号",
				name : "carshipCode"
			} ];
			
			$('[data-role="GoodslogGrid"]').grid({
			identity : 'id',
			columns : colums,
			lockWidth : false,
			isShowIndexCol : false,
			gridName:'logfenlei',
			stateSave:true,
			// isUserLocalData: true,
			// localData : data,
			autoLoad:false,
//			printHeadder : function() {
//				return "测试页眉";
//			},
//			printFotter : function() {
//				return "测试页脚";
//			},
			callback:function(){
				util.setColumnsVisable($('[data-role="GoodslogGrid"]'),[0],columnCheckArray,function(){columnCheckArray=util.getColumnsCheckArray();});
			},
			url : config.getDomain() + "/statistics/getLog"
			});
		}
		//进出存
		else if(type==2){
			
			
				var colums = [
					{
						title : "货权",
						name : "clientName",
							render : function(item, name, index) {
								if(index==0){
									goodsId=0;
								}
								if(item.goodsId==goodsId){
									return "";
								}else{
								
									return item.clientName;
								}
							}	
					},	{
						title : "提单货主",
						name : "goodsLadingClientName",
						render : function(item, name, index) {
							if(item.goodsId==goodsId){
								return "";
							}else{
							
								return item.goodsLadingClientName;
							}
						}	
					},
					{
						title : "货品",
						name : "productName",
						render : function(item, name, index) {
							if(item.goodsId==goodsId){
								return "";
							}else{
								return item.productName;
							}
						}	
					},{
						title : "货批",
						name : "cargoCode",
						render : function(item, name, index) {
							if(item.goodsId==goodsId){
								return "";
							}else{
								goodsId=item.goodsId;
								return item.cargoCode;
							}
						}	
					},
					{
						title : "上下家/来源",
						name : "ladingClientName",
						render : function(item, name, index) {
							if(item.type==1){
								return item.shipName;
							}else if(item.type==2){
								return item.ladingInClientName;
							}else{
								
								return item.ladingClientName;
							}
						}	
					},
					{
						title : "方式",
						name : "type",
						render : function(item, name, index) {
							switch (item.type) {
							case 1:
								return "入库";
								break;
							case 2:
								
								if(item.ladingType==1){
									return "货权调入";
								}else if(item.ladingType==2){
									return "提货权调入";
								}
								break;
							case 3:
								
								if(item.ladingType==1){
									return "货权转移";
								}else if(item.ladingType==2){
									return "提货权转卖";
								}
								break;
							case 4:
								return "封放";
								break;
							case 5:
								
								if(item.actualType>0){
									return "发货";
									
								}else{
									return "发货待提";
								}
								
								break;
							case 6:
								return "退回";
								break;
							case 61:
								return "调入退回";
								break;
							case 62:
								return "调出退回";
								break;
							case 7:
								return "预入库";
								break;
							case 9:
								return "扣损";
								break;
							case 10:
								if(item.deliverNo=="1"){
									return "扣损";
								}else{
									
									return "商检&封放";
								}
								break;
							case 11:
								return "清盘";
								break;
							case 12:
								return "复盘";
								break;
							}
						}
					},
					{
						title : "日期",
						name : "createTime",
						render : function(item, name, index) {
//							return new Date(item.createTime * 1000)
//									.Format("yyyy-MM-dd HH:mm:ss");
							return item.mtime;
						}
					}, {
						title : "单号",
						name : "ladingCode1",
						render : function(item, name, index) {
							if(item.ladingEvidence!=null&&item.ladingEvidence!=""){
							
								return "<a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("
								+ item.goodsId
								+ ")' id='goodsShow'>"+item.ladingEvidence+"</a>";
								
							}
							if(item.nextLadingCode!=null&&item.nextLadingCode!=""){
								return "<a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("
								+ item.goodsId
								+ ")' id='goodsShow'>"+item.nextLadingCode+"</a>";
							
							}else{
								if(item.ladingCode1!=null){
									
									return "<a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("
									+ item.goodsId
									+ ")' id='goodsShow'>"+item.ladingCode1+"</a>";
								}else {
									return "";
								}
							
							}
						}
					}, {
						title : "货量变化(吨)",
						name : "goodsChange",
						render : function(item, name, index) {
							return parseFloat(item.goodsChange).toFixed(3);
						}
					},{
						title : "存量(吨)",
						name : "surplus",
						render : function(item, name, index) {
							
							if(item.type==5&&item.actualNum==0){
								return "";
							}else{
								return util.FloatAdd(parseFloat(item.surplus).toFixed(3),parseFloat(item.goodsSave).toFixed(3));
							}
						}
					},{
						title : "封量(吨)",
						name : "goodsSave",
						render : function(item, name, index) {
							if(item.type==5&&item.actualNum==0){
								return "";
							}else{
							return parseFloat(item.goodsSave).toFixed(3);
							}
						}
					},{
						title : "发货单",
						name : "serial",
						render : function(item, name, index) {
							if(item.type==5){
								return item.serial;
							}else{
								return "";
							}
						}
					}, {
						title : "车船号",
						name : "carshipCode"
					} ];
					
					$('[data-role="GoodslogGrid"]').grid({
					identity : 'id',
					columns : colums,
					lockWidth : false,
					isShowIndexCol : false,
//					isShowPages : false,
					isPrint:true,
					// isUserLocalData: true,
					// localData : data,
					printHeadder : function() {
						return "";
					},
					printFotter : function() {
						return "";
					},
					autoLoad:false,
					url : config.getDomain() + "/statistics/getLog"
					});
		}else if (type==3){
			
			var colums = [
							{
								title : "货权",
								name : "clientName",
									render : function(item, name, index) {
										if(index==0){
											goodsId=0;
										}
										if(item.goodsId==goodsId){
											return "";
										}else{
										
											return item.clientName;
										}
									}	
							},	{
								title : "提单货主",
								name : "goodsLadingClientName",
								render : function(item, name, index) {
									if(item.goodsId==goodsId){
										return "";
									}else{
									
										return item.goodsLadingClientName;
									}
								}
							},
							{
								title : "货品",
								name : "productName",
								render : function(item, name, index) {
									if(item.goodsId==goodsId){
										return "";
									}else{
										return item.productName;
									}
								}	
							},{
								title : "货批",
								name : "cargoCode",
								render : function(item, name, index) {
									if(item.goodsId==goodsId){
										return "";
									}else{
										goodsId=item.goodsId;
										return item.cargoCode;
									}
								}	
							},
							{
								title : "上下家/来源",
								name : "ladingClientName",
								render : function(item, name, index) {
									if(item.type==1){
										return item.shipName;
									}else if (item.type==5){
										return "";
									}else if(item.type==2){
										return item.ladingInClientName;
									}else{	
										return item.ladingClientName;
									}
									
								}	
								
							},
							{
								title : "方式",
								name : "type",
								render : function(item, name, index) {
									switch (item.type) {
									case 1:
										return "入库";
										break;
									case 2:
										
										if(item.ladingType==1){
											return "货权调入";
										}else if(item.ladingType==2){
											return "提货权调入";
										}
										break;
									case 3:
										
										if(item.ladingType==1){
											return "货权转移";
										}else if(item.ladingType==2){
											return "提货权转卖";
										}
										break;
									case 4:
										return "封放";
										break;
									case 5:
										
										if(item.actualType>0){
											return "发货";
											
										}else{
											return "发货待提";
										}
										
										break;
									case 6:
										return "退回";
										break;
									case 61:
										return "调入退回";
										break;
									case 62:
										return "调出退回";
										break;
									case 7:
										return "预入库";
										break;
									case 9:
										return "扣损";
										break;
									case 10:
										if(item.deliverNo=="1"){
											return "扣损";
										}
										else{
											
											return "商检&封放";
										}
										break;
									case 11:
										return "清盘";
										break;
									case 12:
										return "复盘";
										break;
									}
								}
							},
							{
								title : "日期",
								name : "createTime",
								render : function(item, name, index) {
									
									if(item.type==5){
										return "";
										
									}else{
										
//										return new Date(item.createTime * 1000)
//										.Format("yyyy-MM-dd HH:mm:ss");
										return item.mtime;
									}
									
								}
							}, {
								title : "单号",
								name : "ladingCode1",
								render : function(item, name, index) {
									
									if(item.type==5){
										return "";
									}else{
										
										if(item.ladingEvidence!=null&&item.ladingEvidence!=""){
											
											return "<a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("
											+ item.goodsId
											+ ")' id='goodsShow'>"+item.ladingEvidence+"</a>";
											
										}
										if(item.nextLadingCode!=null&&item.nextLadingCode!=""){
											return "<a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("
											+ item.goodsId
											+ ")' id='goodsShow'>"+item.nextLadingCode+"</a>";
											
										}else{
											if(item.ladingCode1!=null){
												
												return "<a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("
												+ item.goodsId
												+ ")' id='goodsShow'>"+item.ladingCode1+"</a>";
											}else {
												return "";
											}
									}
									
									
									}
								}
							}, {
								title : "货量变化(吨)",
								name : "goodsChange",
								render : function(item, name, index) {
									return parseFloat(item.goodsChange).toFixed(3);
								}
							},{
								title : "存量(吨)",
								name : "surplus",
								render : function(item, name, index) {
									
									if(item.type==5&&item.actualNum==0){
										return "";
									}else{
										return util.FloatAdd(parseFloat(item.surplus).toFixed(3),parseFloat(item.goodsSave).toFixed(3));
									}
								}
							}, {
								title : "封量(吨)",
								name : "goodsSave",
								render : function(item, name, index) {
									if(item.type==5&&item.actualNum==0){
										return "";
									}else{
									return parseFloat(item.goodsSave).toFixed(3);
									}
								}
							}, ];
							
							$('[data-role="GoodslogGrid"]').grid({
							identity : 'id',
							columns : colums,
							lockWidth : false,
							isShowIndexCol : false,
							isShowPages : false,
							isPrint:true,
							// isUserLocalData: true,
							// localData : data,
							printHeadder : function() {
								return "";
							},
							printFotter : function() {
								return "";
							},
							autoLoad:false,
							url : config.getDomain() + "/statistics/getEasyInOutLog"
							});
			
			
			
		}else if(type==4){
			var colums = [
			  			{
			  				title : "次级货主",
			  				name : "clientName",
			  				render : function(item, name, index) {
								
								if(item.clientName!=null){
									return "<a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("
									+ item.goodsId
									+ ")' id='goodsShow'>"+item.clientName+"</a>";
									
								}else{
									return "";
								}
								

							}
			  			},	
			  			{
			  				title : "提货单位",
			  				name : "ladingClientName"
			  			},
			  			{
			  				title : "发货单",
			  				name : "serial",
							render : function(item, name, index) {
								if(item.type==5){
									return item.serial;
								}else{
									return "";
								}
							}
			  			},
			  			 {
			  				title : "单号",
			  				name : "ladingCode1"
			  				
			  			},{
			  				title : "提货凭证",
			  				name : "ladingEvidence"
			  			},    {
			  				title : "车船号",
			  				name : "carshipCode"
			  			} ,
			  			{
			  				title : "日期",
			  				name : "createTime",
			  				render : function(item, name, index) {
//			  					return new Date(item.createTime * 1000)
//			  							.Format("yyyy-MM-dd HH:mm:ss");
			  					return item.mtime;
			  				}
			  			}, {
			  				title : "计发数(吨)",
			  				name : "deliverNum",
			  				render : function(item, name, index) {
			  					return parseFloat(item.deliverNum).toFixed(3);
			  				}
			  			}, {
			  				title : "实发数(吨)",
			  				name : "goodsChange",
			  				render : function(item, name, index) {
			  					return parseFloat(-item.goodsChange).toFixed(3);
			  				}
			  			}];
			  			
			  			$('[data-role="GoodslogGrid"]').grid({
			  			identity : 'id',
			  			columns : colums,
			  			lockWidth : false,
			  			isShowIndexCol : false,
			  			
			  			// isUserLocalData: true,
			  			// localData : data,
			  			autoLoad:false,
//			  			printHeadder : function() {
//			  				return "测试页眉";
//			  			},
//			  			printFotter : function() {
//			  				return "测试页脚";
//			  			},
			  			url : config.getDomain() + "/statistics/getLog"
			  			});
			  		}
		//通过货批
		else if(type==5){
						var colums = [
							  			{
							  				title : "货主",
							  				name : "clientName",
							  			
							  			},	{
							  				title : "船名",
							  				name : "shipName",
							  			
							  			},	{
							  				title : "到港日期",
							  				name : "arrivalStartTime",
							  				render: function(item, name, index){
							  					if(item.arrivalStartTime){
							  						return item.arrivalStartTime.split(" ")[0];
							  					}else{
							  						return "";
							  					}
							  					}
							  			
							  			},
							  			{
							  				title : "货批号",
							  				name : "code"
							  			},
							  			{
							  				title : "货品",
							  				name : "productName"
							  			},
							  			 {
							  				title : "贸易类型",
							  				name : "ladingCode1",
							  				render : function(item, name, index) {
							  					if(item.taxType==1){
							  						return "内贸";
							  					}
							  					if(item.taxType==2){
							  						return "外贸";
							  					}
							  					if(item.taxType==3){
							  						return "保税";
							  					}
							  				}
							  				
							  			},{
							  				title : "计划数量",
							  				name : "goodsPlan"
							  			},    {
							  				title : "商检数",
							  				name : "goodsInspect"
							  			},    {
							  				title : "开票状态",
							  				name : "goodsInspect",
							  				render : function(item, name, index) {
							  					if(item.billingStatus==1){
							  						return "已开票";
							  					}
							  					else
							  					{
							  						return "未开票";
							  					}
							  				}
							  			} ];
							  			
							  			$('[data-role="GoodslogGrid"]').grid({
							  			identity : 'id',
							  			columns : colums,
							  			lockWidth : false,
							  			isShowIndexCol : false,
							  			autoLoad:false,
							  			url : config.getDomain() + "/statistics/getPassCargo"
							  			});
							  		}
		else if (type==6){
			var colums = [
			  			{
			  				title : "货权",
			  				name : "clientName"
			  			},
			  			{
			  				title : "提单货主",
			  				name : "goodsLadingClientName"
			  			},	
			  			{
			  				title : "货品",
			  				name : "productName"
			  			},{
			  				title : "货批",
			  				name : "cargoCode",
			  				render : function(item, name, index) {
			  					
			  					if(item.cargoCode!=null){
			  						return "<a href='javascript:void(0)'  onclick='GoodsLZ.openGoodsLZ("
			  						+ item.goodsId
			  						+ ")' id='goodsShow'>"+item.cargoCode+"</a>";
			  						
			  					}else{
			  						return "";
			  					}
			  					

			  				}
			  				
			  			},
			  			{
			  				title : "上下家",
			  				name : "ladingClientName",
			  				render : function(item, name, index) {
			  					if(item.type==1){
			  						return item.shipName;
			  					}else if(item.type==2){
			  						return item.ladingInClientName;
			  					}else{
			  						
			  						return item.ladingClientName;
			  					}
			  				}	
			  			},
			  			{
			  				title : "方式",
			  				name : "type",
			  				render : function(item, name, index) {
			  					switch (item.type) {
			  					case 1:
			  						return "入库";
			  						break;
			  					case 2:
			  						return "调入";
			  						break;
			  					case 3:
			  						return "调出";
			  						break;
			  					case 4:
			  						return "封放";
			  						break;
			  					case 5:
			  						
			  						if(item.actualType>0){
			  							return "发货";
			  							
			  						}else{
			  							return "发货待提";
			  						}
			  						
			  						break;
			  					case 6:
			  						return "退回";
			  						break;
			  					case 61:
			  						return "调入退回";
			  						break;
			  					case 62:
			  						return "调出退回";
			  						break;
			  					case 7:
			  						return "预入库";
			  						break;
			  					case 9:
			  						return "扣损";
			  						break;
			  					case 10:
			  						
			  						if(item.deliverNo=="1"){
			  							return "扣损";
			  						}
			  						else{
			  							
			  							return "商检&封放";
			  						}
			  						break;
			  					case 11:
			  						return "清盘";
			  						break;
			  					case 12:
			  						return "复盘";
			  						break;
			  					}
			  				}
			  			},
			  			{
			  				title : "发货罐号",
			  				name : "tankName",
			  				render : function(item, name, index) {
//			  					return new Date(item.createTime * 1000)
//			  							.Format("yyyy-MM-dd HH:mm:ss");
			  					if(item.tankName1){
			  						
			  						return item.tankName1;
			  					}else if(item.tankName){
			  						return item.tankName;
			  					}else{
			  						return "";
			  					}
			  					
			  				}
			  			}, 
			  			{
			  				title : "日期",
			  				name : "createTime",
			  				render : function(item, name, index) {
//			  					return new Date(item.createTime * 1000)
//			  							.Format("yyyy-MM-dd HH:mm:ss");
			  				return item.mtime;
			  				}
			  			}, {
			  				title : "单号",
			  				name : "ladingCode1",
			  				render : function(item, name, index) {
			  					if(item.ladingEvidence!=null&&item.ladingEvidence!=""){
			  						return item.ladingEvidence;
			  					}
			  					if(item.nextLadingCode!=null&&item.nextLadingCode!=""){
			  						return item.nextLadingCode;
			  						
			  					}else{
			  						return item.ladingCode1;
			  					}
			  				}
			  			}, {
			  				title : "货量变化(吨)",
			  				name : "goodsChange",
			  				render : function(item, name, index) {
			  					if(sType==1){
			  						
			  					
			  					if(item.type==1){
			  						return parseFloat(item.mGoodsChange).toFixed(3);
			  					}else{
			  						return parseFloat(item.mGoodsChange).toFixed(3);
			  					}
			  					}else{
			  						
			  						return parseFloat(item.goodsChange).toFixed(3);
			  					}
			  				}
			  			},{
			  				title : "存量(吨)",
			  				name : "surplus",
			  				render : function(item, name, index) {
			  					
//			  					if(item.type==5&&item.actualNum==0){
//			  						return "";
//			  					}else{
			  					
			  					if(item.type==1){
			  						return "";
			  					}
			  					
			  						return parseFloat(item.surplus).toFixed(3);
//			  					}
			  				}
			  			}, {
			  				title : "封量(吨)",
			  				name : "goodsSave",
			  				render : function(item, name, index) {
			  					
			  					if(item.type==1){
			  						return "";
			  					}
			  					
			  					if(item.type==5&&item.actualNum==0){
			  						return "";
			  					}else{
			  					return parseFloat(item.goodsSave).toFixed(3);
			  					}
			  				}
			  			},   {
			  				title : "发货单",
			  				name : "serial",
			  				render : function(item, name, index) {
			  					if(item.type==5){
			  						return item.serial;
			  					}else{
			  						return "";
			  					}
			  				}
			  			}, {
			  				title : "车船号",
			  				name : "carshipCode"
			  			},
			  			{
			  				title : "皮重",
			  				name : "actualRoughWeight",
			  				render : function(item, name, index) {
			  					if(item.actualRoughWeight){
			  						return item.actualRoughWeight;
			  					}else if(item.inWeigh){
			  						return item.inWeigh;
			  					}else {
			  						return "";
			  					}
			  				}
			  			},{
			  				title : "毛重",
			  				name : "carshipCode",
			  				render : function(item, name, index) {
			  					if(item.actualTareWeight){
			  						return item.actualTareWeight;
			  					}else if(item.outWeigh){
			  						return item.outWeigh;
			  					}else {
			  						return "";
			  					}
			  				}
			  			}];
			  			
			  			$('[data-role="GoodslogGrid"]').grid({
			  			identity : 'id',
			  			columns : colums,
			  			lockWidth : false,
			  			isShowIndexCol : false,
			  			gridName:'logfenlei',
			  			stateSave:true,
			  			// isUserLocalData: true,
			  			// localData : data,
			  			autoLoad:false,
//			  			printHeadder : function() {
//			  				return "测试页眉";
//			  			},
//			  			printFotter : function() {
//			  				return "测试页脚";
//			  			},
			  			callback:function(){
			  				util.setColumnsVisable($('[data-role="GoodslogGrid"]'),[0],columnCheckArray,function(){columnCheckArray=util.getColumnsCheckArray();});
			  			},
			  			url : config.getDomain() + "/statistics/getLog"
			  			});
			  		}

};
	
	return {
		init : function() {
			handleRecords();
		},
		exportExcel:exportExcel,
		search:search,
		reset:reset
		
	};
	
	
	
}();