var DockFeeBill=function(){
	var systemUserId,systemUserName;
	var columnCheckArray;
	//改变标签
	function changeTab(obj,item){
		$(obj).parent().addClass("active").siblings().removeClass("active");
		initTable(item);
	};
	//初始化查询
	function initSearchCtr(){
		util.initDatePicker();
		getSystemUserMsg();
		util.urlHandleTypeaheadAllData("/shipagent/select",$("#feeHead"),function(item){return item.name});
		$("#searchFee").click(function(){
			$('[data-role="dockFeeBillGrid"]').getGrid().search(getSearchCondition());
		});	
		$("#reset").click(function(){
			$("#startTime,#endTime,#feeHead,#billCode,#feeBillCode").val("");
			$("#billType,#tradeType,#type").val(0);
			$("#billStatus").val(-1);
			$("#searchFee").click();
		});
		
		$("#makeBill").mouseover(function(){
			 $("#arrivalTimeType").css("color","red");
		  }); 
		$("#makeBill").mouseout(function(){
			 $("#arrivalTimeType").css("color","#333333");
			  });
		$("#makeBill").click(function(){
			if(util.isNull($("#startTime").val(),1)==0){
				$("body").message({type:'warning',content:'请填写起始日期'});
				return false;
			}
			if(util.isNull($("#endTime").val(),1)==0){
				$("body").message({type:'warning',content:'请填写截止日期'});
				return false;
			} 
			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+'/dockfeebill/makefeebill',
				data:{startTime:util.formatLong($("#startTime").val()+" 00:00:00"),
					endTime:util.formatLong($("#endTime").val()+" 23:59:59"),
					type:1,
					'feeHead':$("#feeHead").val(),
					'billType':$("#billType").val(),
					'tradeType':$("#tradeType").val(),
					'billTime':$("#billTime").val()?util.formatLong($("#billTime").val()+" 00:00:00"):-1},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,"生成",function(ndata){
						$('[data-role="dockFeeBillGrid"]').getGrid().refresh();
					});
				}
			});
		});
	};
	
	//获取查询条件
	function getSearchCondition(){
		
		var params={
				'code':$("#feeBillCode").val(),
				'feeHead':$("#feeHead").val(),
				'billType':$("#billType").val(),
				'tradeType':$("#tradeType").val(),
				'type':$("#type").val(),
				'billStatus':$("#billStatus").val()
		};
		if($("#timeType").val()==2&&$("#arrivalTimeType").attr('display')=='none'){
			params.billingStartTime=util.formatLong($("#startTime").val()+" 00:00:00");
			params.billingEndTime=util.formatLong($("#endTime").val()+" 23:59:59");
		}else{
			params.startTime=util.formatLong($("#startTime").val()+" 00:00:00");
			params.endTime=util.formatLong($("#endTime").val()+" 23:59:59");
		}
		return params;
	};
	//初始化列表
	function initTable(item){
		if(item==1){
			$(".chargeDiv").show();
			$(".feeBillDiv").hide();
			$(".billTimeDiv").show();
			initFeeChargeTable();
		}else{
			$(".feeBillDiv").show();
			$(".chargeDiv").hide();
			$(".chargeDivType").show();
			$(".billTimeDiv").hide();
			initFeeBillTable();
		}
	};
/***********************************************************************************/
	function initFeeChargeTable(){
		var columns=[{title:' ',width:30,render:function(item,name,index){
			return '<a href="javascript:void(0);" data="'+item.clientName+'" onclick="DockFeeBill.showItemFeeChargeTable(this,'+item.billType+','+index+
			')"><span class="fa fa-plus-square-o" style="font-size:17px"></span></a>';
		}},{title:'船代单位',name:'clientName'},{title:'总金额(元)',render:function(item){ return util.toDecimal2(item.totalFee,true);
		}},{title:'发票类型',render:function(item){ return (item.billType==1?'手撕发票':'增值税发票');}}];
		
		if($('[data-role="dockFeeBillGrid"]').getGrid()!=null)
			$('[data-role="dockFeeBillGrid"]').getGrid().destory();
		$('[data-role="dockFeeBillGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			showPage:9,
			pageSize:50,
			searchCondition:getSearchCondition(),
	        url:config.getDomain()+"/dockfeecharge/getFeeHeadList",
	        callback:function(){
	        	$('[data-role="dockFeeBillGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
	        }
		});
	};
	
	function showItemFeeChargeTable(obj,billType,index){
		var clientName=$(obj).attr('data');
		var iTr=$(obj).closest('tr');
		if($(obj).find('span').attr('class')=='fa fa-plus-square-o'){
			$(obj).find('span').removeClass('fa fa-plus-square-o').addClass('fa fa-minus-square-o');
			var html='<tr class="detail"><td colspan="6"><div class="col-md-12">'+
			'<div data-role="feeDetailGrid'+index+'"></div></td></tr>';
		    $(html).insertAfter(iTr);
		    initItemFeeChargeTable(clientName,billType,index);
		}else{
			$(obj).find('span').removeClass('fa fa-minus-square-o').addClass('fa fa-plus-square-o ');
			iTr.next('tr[class="detail"]').remove();
		}
	};
	
	function initItemFeeChargeTable(clientName,billType,index){
		var columns=[{title:'费用项',name:'feeTypeStr'},{title:'对方单位',name:'clientName'},{title:'船名',name:'boundMsg'},
		             {title:'到港日期',name:'arrivalTime'},{title:'到港类型',name:'arrivalType'},{title:'发票代码',name:'billCode'},
		             {title:'结算单编号',render:function(item){
	            	 return '<a href="javascript:void(0);" onclick="DockFee.initDockFeeDialog('
	            	 +item.dockFeeId+','+item.arrivalId+')">'+item.code+'</a>'
		             }},{title:'发生日期',name:'arrivalTime'},{title:'金额',render:function(item){
		             return util.toDecimal2(item.totalFee,true);}},{title:'实收金额',render:function(item){
			             return util.toDecimal2(item.discountFee,true);}},{title:'账单号',name:'feebillcode'}];
		var params=getSearchCondition();
		params.clientName=clientName;
		params.billType=billType;
		
		$('[data-role=feeDetailGrid'+index+']').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			pageSize:20,
			searchCondition:params,
	        url:config.getDomain()+"/dockfeecharge/list",
	        callback:function(){
	        	initItemTableGridCallBackHtml(index,params);
	    		initItemTableGridCallBackCtr(index);
	        }});
	};
	
		function initItemTableGridCallBackHtml(index,params){
			var html="<div class='form-group detailTotal"+index+"' style='margin-left: 0px; margin-right: 0px;'>"+
	        "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>选择账单中金额：</label>" +
	         "<label class='control-label' style='margin-left: 10px;margin-right:20px;' id='choiceTotalFee"+
	         index+"'></label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>元</label></div>";	        	
	        $('[data-role=feeDetailGrid'+index+']').find(".detailTotal"+index).remove();
	        $('[data-role=feeDetailGrid'+index+']').find(".grid-body").attr('style','min-height:0px;').parent().append(html);
	        
		   	var html1='<tr class="detail"><td colspan="6"><div class="modal-footer">'          
					+'<button class="btn btn-primary" type="button" onclick="DockFeeBillDialog.dialogDockFeeBill(undefined,'+index+')" >生成账单</button>'
					+'<button class="btn btn-primary" type="button" onclick="DockFeeBillDialog.dialogDockFillBillList('+index+',\''+params.clientName+'\','+params.tradeType+')">添加到已有账单</button>'
					 +'<button class="btn btn-primary" type="button" onclick="DockFeeBill.copyDockFeeCharge('+index+')" >复制费用项</button>'
//			     		
					+'</div></td></tr>';
		   	$('[data-role=feeDetailGrid'+index+']').find(".detail").remove();
		   	$('[data-role=feeDetailGrid'+index+']').find("tfoot").append(html1);
		};
		
		
		//拷贝费用项
		function copyDockFeeCharge(index){
			var	feeData=$('[data-role="feeDetailGrid'+index+'"]').getGrid().selectedRows();	
			if(feeData==undefined||feeData.length==0||feeData.length>1){
				$('body').message({
					type:'warning',
					content:'请选择一个费用项'
				});
				return false;
			}else if(feeData.length==1){
				var itemfeeCharge={
						
						'type':1,
						'feeType':feeData[0].feeType,//靠泊费,
						'clientName':feeData[0].clientName,
						'unitFee':feeData[0].unitFee,
						'totalFee':feeData[0].totalFee,
						'discountFee':feeData[0].discountFee,
						'dockFeeId':feeData[0].dockFeeId,
						
				}
				
				$.ajax({
					type:'post',
					url:config.getDomain()+"/dockfee/copy",
					data:itemfeeCharge,
					dataType:'json',
					success:function(data){
						util.ajaxResult(data,'复制费用项',function(){
							$('[data-role="feeDetailGrid'+index+'"]').getGrid().refresh();
						});
					}
				});
			}
		};
		
		
		function initItemTableGridCallBackCtr(index){
			// 单个tr点击
	    	$('[data-role=feeDetailGrid'+index+']').find(".grid-body>table>tbody>tr").each(function(){
	    		$this=$(this);
	    		$this.click(function(){
	    			$a=$(this);
	    			$b=$a.find('div[class="checkerbox checked"]');
	    			initItemTrCtr(index);
	    		});
	    		$this.find('div[class="checkerbox"]').click(function(){
	    			$b=$(this);
	    			$a=$b.closest('tr');
	    			initItemTrCtr(index);
	    		});
	    	});
	    	//全选
	    	$('[data-role=feeDetailGrid'+index+']').find('div[data-role="selectAll"]').click(function(){
	    		$this=$(this);
	    		if($this.hasClass('checked')){
	    			var totalAmount=0;
	    			$('[data-role=feeDetailGrid'+index+']').find(".grid-body>table>tbody>tr").each(function(){
	    				$itemTr=$(this);
	                    if($itemTr.find("td[index='10']").text()!=""){
	                    	$itemTr.removeClass('success');
	                    	$itemTr.find('div[class="checkerbox checked"]').removeClass('checked');
	                    }else{
	                    	totalAmount=util.FloatAdd(totalAmount,$itemTr.find("td[index='9']").text(),2);
	                    }                  	        				
	    			});	
	    			$("#choiceTotalFee"+index).text(totalAmount);
	    		}else{
	    			$("#choiceTotalFee"+index).text(0);
	    		}
	    	});
		};
			function initItemTrCtr(index){
				if($a.hasClass('success')){
		            if($a.find("td[index='10']").text()!=""){
		            	$a.removeClass('success');
		            	$b.removeClass('checked');
		            }else{
		            	$("#choiceTotalFee"+index).text(util.FloatAdd(util.isNull($("#choiceTotalFee"+index).text(),1),$a.find("td[index='9']").text(),2));
		            }                  	        				
				}else{
					$("#choiceTotalFee"+index).text(util.FloatSub(util.isNull($("#choiceTotalFee"+index).text(),1),$a.find("td[index='9']").text(),2));
				}
			};
/***********************************************************************************/	
	function initFeeBillTable(){
		var columns=[{title:"账单号",render:function(item){
			if(config.hasPermission("ADOCKFEEBILLCHECK"))
				return '<a href="javascript:void(0)" onclick="DockFeeBillDialog.dialogDockFeeBill('+item.id+')">'+item.code+'</a>';
			else
				return item.code;
					}},{title:"发票类型",name:"tradeTypeStr"},{title:"费用类型",name:"feeTypeStr"},
		             {title:"开票抬头",name:"feeHead"},
		             {title:"发票号",name:"billingCode"},
		             {title:"账单日期",render:function(item){
		            	 return util.getSubTime(item.accountTimeStr,1);
		             }},{title:"应收金额(元)",render:function(item){return util.toDecimal2(item.totalFee,true);}},
		             {title:"已收金额(元)",render:function(item){return util.toDecimal2(item.accountTotalFee,true);}},
		             {title:"未收金额(元)",render:function(item){
		            	 return util.FloatSub(item.totalFee,item.accountTotalFee,2);
		             }},{title:"审核人",name:"reviewUserName"},
		             {title:"审核时间",render:function(item){
		            	 return util.getSubTime(item.reviewTimeStr,1);
		             }},{title:"最近到账操作人",name:"fundsUserName"},
		             {title:"最近到账时间",render:function(item){
		            	 return util.getSubTime(item.fundsTimeStr,1);
		             }},{title:"开票人",name:"billingUserName"},
		             {title:"开票时间",render:function(item){
		            	 return util.getSubTime(item.billingTimeStr,1);
		             }},{title:"账单状态",render:function(item){
		            	 if(item.status==0){ return "<label style='color:#99CC33'>未提交</label>";}
			        	   else if(item.status==1){ return "<label style='color:#666699'>审核中</label>";}
			        	   else if(item.status==2&&item.billingStatus==0&&item.fundsStatus==0){ return "<label style='color:#9966CC'>已审核</label>";}
			        	   else if(item.status==2&&item.billingStatus==0&&item.fundsStatus==2){ return "<label style='color:#d64635'>已到账</label>";}
			        	   else if(item.status==2&&item.billingStatus==1&&item.fundsStatus!=2){ return "<label style='color:#d64635'>已开发票</label>";}
			        	   else if(item.status==2&&item.billingStatus==1&&item.fundsStatus==2){ return "<label style='color:#0099CC'>已完成</label>";}
		             }},{title:"操作",render:function(item){
		            		 var html='<div class="input-group-btn">';
		            		 if(config.hasPermission("ADOCKFEEBILLCHECK")){
			            		html+='<a href="javascript:void(0)"  style="margin:0 3px"  onclick="DockFeeBillDialog.dialogDockFeeBill('+item.id+
			            		')" class="btn btn-xs blue" ><span class="glyphicon glyphicon-edit" title="修改"></span></a>';}
		            		 if(config.hasPermission("ADOCKFEEBILLDELETE")&&item.status==0){
		            			 html+='<a href="javascript:void(0)" onclick="DockFeeBill.deleteDockFeebill('+item.id+
		            			 ')" style="margin:0 3px" class="btn btn-xs red " ><span class="glyphicon glyphicon-remove" title="删除"></span></a>';
		            		 }
		            		 html+='</div>';
		        	         return html;
		            	 }
		             }];
		if($('[data-role="dockFeeBillGrid"]').getGrid()!=null)
			$('[data-role="dockFeeBillGrid"]').getGrid().destory();
		$('[data-role="dockFeeBillGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			showPage:9,
	        url:config.getDomain()+"/dockfeebill/list",
	        callback:function(){
	        	$('[data-role="dockFeeBillGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
	        	calculateTotalFeeBill();
	        	util.setColumnsVisable($('div[data-role="dockFeeBillGrid"]'),[0,15],columnCheckArray,function(){columnCheckArray=util.getColumnsCheckArray();});
	        }
		});
	};
	
	
	//统计
	function calculateTotalFeeBill(){
				$.ajax({
                    type:'post',
					url:config.getDomain()+"/dockfeebill/gettotalfee",
					data:getSearchCondition(),
					dataType:'json',
					success:function(data){
						util.ajaxResult(data,'统计',function(ndata){
							var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
			                   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>应收金额: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].totalFee+" 元</label>"	+				
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>已收金额: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].accountTotalFee+" 元</label>"	+
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>未收金额: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.FloatSub(ndata[0].totalFee,ndata[0].accountTotalFee,2)+" 元</label>"	+
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>未税金额: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].unTaxFee+" 元</label>"	+
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>税额: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].taxFee+" 元</label>"	+
			                   		
							        "</div>";
							$(".totalFeeDiv").remove();
							$('[data-role="dockFeeBillGrid"]').find(".grid-body").parent().append(totalHtml);
						},true);
					}
				});
	};
	
	
	function deleteDockFeebill(id){
		if(id){
			$('body').confirm({
				content:'确定要删除吗?',
				callBack:function(){
					$.ajax({
					type:'post',
					url:config.getDomain()+"/dockfeebill/delete",
					data:{id:id},
					dataType:'json',
					success:function(data){
						util.ajaxResult(data,'删除',function(){
							$('[data-role="dockFeeBillGrid"]').getGrid().refresh();
						});
					}
					
				});
					}
			});
		}
	};
/*********************************************************************************/
	//获取用户
	function getSystemUserMsg(){
		if(systemUserId){
			return systemUserId;
		}else{
			$.ajax({
				type:'post',
				url:config.getDomain()+"/initialfee/getsystemuser",
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'获取系统信息',function(ndata){
						systemUserId=ndata[0].userId;
						systemUserName=ndata[0].userName;
						return systemUserId;},true);
				}});}
	};
	function exportExcel(type){
		if(type==1){
		   var url=config.getDomain()+"/dockfee/exportExcel?nowTime="+util.currentTime(0)
		   if(util.isNull($("#feeHead"),1)!=0){
			   url+="&clientName="+$("#feeHead").val();
		   }else{
			 url+="&clientName=null";  
		   }
		   if(util.isNull($("#startTime").val(),1)==0){
				$('body').message({
					type:'warning',
					content:'请选择起始日期'
				});
				return;
			}else{
				url+="&startTime="+util.formatLong($("#startTime").val()+" 00:00:00");
			}
			if(util.isNull($("#endTime").val(),1)==0){
				$('body').message({
					type:'warning',
					content:'请选择止计日期'
				});
				return;
			}else{
				url+="&endTime="+util.formatLong($("#endTime").val()+" 23:59:59");
			}
			window.open(url);
		}else if(type==2){
			var url=config.getDomain()+"/dockfeebill/exportExcel?type=2&1=1"+util.getStrSearchCondition(getSearchCondition());
			console.log(url);
			window.open(url);
		}else if(type==3){
			var url=config.getDomain()+"/dockfeebill/exportExcel?type=3&1=1"+util.getStrSearchCondition(getSearchCondition());
			console.log(url);
			window.open(url);
		}
	   }
	
	return{
		changeTab:changeTab,
		initSearchCtr:initSearchCtr,
		initTable:initTable,
		getSystemUserMsg:getSystemUserMsg,
		showItemFeeChargeTable:showItemFeeChargeTable,
		initFeeBillTable:initFeeBillTable,
		deleteDockFeebill:deleteDockFeebill,
		exportExcel:exportExcel,
		copyDockFeeCharge:copyDockFeeCharge
	};
}();