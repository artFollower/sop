//changeTab tab标签改变
//initTableBtn 初始化列表按钮
//initTable 初始化列表（1.收费项列表，2.账单列表）
//initChargeTable 收费项列表
//initFeeBillTable 账单列表 
//deleteFeebill 删除账单 
//addTable 收费项列表子列表 
//initDetailTable 开票单号详情列表 
//dialogFillBillList 账单列表---添加到已有账单
//insertToBill 添加已有账单
//dialogFeeBill 账单详情
//initFeeBillControl 初始化账单控件 
//getChargeTableData 获取费用表数据
//initFeeBillData 初始化账单数据
//showFeeBillControl 根据状态显示控件
//removeFeeCharge 从账单移除费用项
//updateAccountFee 修改删除账单信息

/**账单管理*/
var feeBill=function(){
	var systemUserId;
	var systemUserName;
	var isOpenConfirm=true;
	var columnCheckArray;
	//tab标签改变
	function  changeTab(obj,item){
		$(obj).parent().addClass("active").siblings().removeClass("active");
		$("#clientName,#startTime,#endTime,#feeHead,#billingStartTime,#billingEndTime,#billCode,#billingCode,#cargoId,#productId").val("").removeAttr("data");
		checkAllFeeType();
		initTable(item);
	}
	//初始化列表按钮
	function initTableBtn(){
		util.urlHandleTypeaheadAllData("/baseController/getClientName",$('#clientName'),function(item){return item.name+"["+item.code+"]";},undefined,undefined,200);
		util.urlHandleTypeaheadAllData("/baseController/getClientName",$('#feeHead'),function(item){return item.name+"["+item.code+"]";},undefined,undefined,200);
		util.urlHandleTypeaheadAllData("/baseController/getcargolist",$("#cargoId"),function(item){return item.code});
		util.urlHandleTypeaheadAllData("/product/select",$('#productId')); // 初始化货品
		util.initDatePicker();
		
		$("#searchFee").click(function(){
		$('[data-role="feeBillGrid"]').getGrid().search(getSearchCondition());
		});	
		
		$("#reset").click(function(){
			$("#clientName,#startTime,#totalFee,#endTime,#feeHead,#billingStartTime,#billingEndTime,#billCode,#billingCode,#cargoId,#productId").val("").removeAttr("data");
			checkAllFeeType(); 
			$("#searchFee").click();
		});
		
		$("#isShowAll").click(function(){
			calculateTotalFeeBill();
			});
		$.ajax({
			type:'post',
			url:config.getDomain()+"/initialfee/getsystemuser",
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取系统信息',function(ndata){
					systemUserId=ndata[0].userId;
					systemUserName=ndata[0].userName;
				},true);
			}
		});
	};
	//统计
	function calculateTotalFeeBill(){
		$this=$("#isShowAll");
		if($this.is(":checked")){
			if($('[data-role="feeBillGrid"]').getGrid()!=null&&$('#tab2').closest('li').hasClass('active')){
				$.ajax({
                    type:'post',
					url:config.getDomain()+"/feebill/gettotalfee",
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
							$('[data-role="feeBillGrid"]').find(".grid-body").parent().append(totalHtml);
						},true);
					}
				});
			} else{
				$this.attr('checked',false);
			}
		}else{
			$this.attr('checked',false);
        	$(".totalFeeDiv").remove();
		}
	};
	
	
	
	//获取查询条件
	function getSearchCondition(){
		var params={
				cargoId:$("#cargoId").attr("data"),
				cargoCode:$("#cargoId").val(),
				productId:$("#productId").attr("data"),
				clientId:$('#clientName').attr('data'),
				feeHead:$('#feeHead').attr('data'),
				totalFee:$("#totalFee").val(),
				feeTypes:getFeeTypeSelected(),
				billType:$('#type').val(),
				code:$('#billCode').val(),
				billingCode:$("#billingCode").val(),
				billStatus:$("#billStatus").val()
			};
		if($("#timeType").val()==1){
			params.startTime=util.formatLong($("#startTime").val()+" 00:00:00");
			params.endTime=util.formatLong($("#endTime").val()+" 23:59:59");
		}else{
			params.billingStartTime=util.formatLong($("#startTime").val()+" 00:00:00");
			params.billingEndTime=util.formatLong($("#endTime").val()+" 23:59:59");
		}
		return params;
	}
	
	
	//删除账单
	function deleteFeebill(id,isTrue){
		if(id){
			$('body').confirm({
				content:(isTrue?'确定要删除吗?':"已开发票无法删除。"),
				callBack:function(){
					if(isTrue){
					$.ajax({
					type:'post',
					url:config.getDomain()+"/feebill/delete",
					data:{id:id},
					dataType:'json',
					success:function(data){
						util.ajaxResult(data,'删除',function(){
							$('[data-role="feeBillGrid"]').getGrid().refresh();
						});
					}
				});
					}
				}
			});
		}
	};
	/************************************************************************/
	//初始化列表（1.收费项列表，2.账单列表）
	function initTable(item){
		if(item==1){
			$("#isShowAll").attr('checked',false);
			$(".chargeDiv").show();
			$(".feeBillDiv").hide();
			initChargeTable();
		}else{
			$("#isShowAll").attr('checked',true);
			$(".feeBillDiv").show();
			$(".chargeDiv").hide();
			initFeeBillTable();
		}
	};
	
	//收费项列表
	function initChargeTable(){
		var columns=[
				{title:' ',width:30,render:function(item){
					return '<a href="javascript:void(0);" onclick="feeBill.addTable(this,'+item.feeHead+')"><span class="fa fa-plus-square-o" style="font-size:17px"></span></a>';
				}},{title:'开票抬头',name:'feeHeadName'},{title:'总计金额(元)',name:'allTotalFee',render:function(item){return util.toDecimal2(item.allTotalFee,true);}},{title:'费用项数量',name:'unFinal',render:function(item){return util.toDecimal3(item.allTotalFee,true);}}
		];
		if($('[data-role="feeBillGrid"]').getGrid()!=null){
			$('[data-role="feeBillGrid"]').getGrid().destory();
		}
		$('[data-role="feeBillGrid"]').grid({
			    identity : 'feeHead',
				columns : columns,
				searchCondition:{billType:$('#type').val()},
				isShowIndexCol : false,
				isShowPages : true,
				showPage:9,
		       url:config.getDomain()+"/feecharge/feechargelist" 
		}
		);
	}
	//账单列表
	function initFeeBillTable(){
		var columns=[{title:"账单号",name:"code",render:function(item){
			if(config.hasPermission("AFEEBILLITEMCHECK"))
			return '<a href="javascript:void(0)" onclick="feeBill.dialogFeeBill(undefined,'+item.id+')">'+item.code+'</a>';
			else
				return item.code;
					}},
		             {title:"开票抬头",name:"feeHeadName"},
		             {title:"费用类型",render:function(item){
		            	return getFeeTypeStr(item.feeType);
		             }},
		             {title:"货批号",name:"cargoCodes",render:function(item){
		            	return "<label style='min-width:120px;max-width:140px;'>"+util.isNull(item.cargoCodes)+"</label>"; 
		             }},{title:"货品名称",name:"productnames"},
		             {title:"发票号",name:"billingCode"},
		             {title:"开票时间",name:"billingTimeStr",render:function(item,name,index){
		            	 return util.getSubTime(item.billingTimeStr,1);
		             }},
		             
		             {title:"应收金额(元)",name:"totalFee",render:function(item){return util.toDecimal2(item.totalFee,true);}},
		             {title:"已收金额(元)",name:"accountTotalFee",render:function(item){return util.toDecimal2(item.accountTotalFee,true);}},
		             {title:"未收金额(元)",render:function(item,name,index){
		            	 return util.FloatSub(item.totalFee,item.accountTotalFee,2);
		             }},
		             {title:"审核人",name:"reviewUserName"},
		             {title:"审核时间",name:"reviewTimeStr",render:function(item,name,index){
		            	 return util.getSubTime(item.reviewTimeStr,1);
		             }},
		             {title:"最近到账操作人",name:"fundsUserName"},
		             {title:"最近到账时间",name:"fundsTimeStr",render:function(item,name,index){
		            	 return util.getSubTime(item.fundsTimeStr,1);
		             }},
		             {title:"开票人",name:"billingUserName"},
		             {title:"账单日期",name:"accountTimeStr",render:function(item,name,index){
		            	 return util.getSubTime(item.accountTimeStr,1);
		             }},
		             {title:"账单状态",render:function(item,name,index){
		            	 if(item.status==0){ return "<label style='color:#99CC33'>未提交</label>";}
			        	   else if(item.status==1){ return "<label style='color:#666699'>审核中</label>";}
			        	   else if(item.status==2&&item.billingStatus==0&&item.fundsStatus==0){ return "<label style='color:#9966CC'>已审核</label>";}
			        	   else if(item.status==2&&item.billingStatus==0&&item.fundsStatus==2){ return "<label style='color:#d64635'>已到账</label>";}
			        	   else if(item.status==2&&item.billingStatus==1&&item.fundsStatus!=2){ return "<label style='color:#d64635'>已开发票</label>";}
			        	   else if(item.status==2&&item.billingStatus==1&&item.fundsStatus==2){ return "<label style='color:#0099CC'>已完成</label>";}
		             }},{
		            	 title:"操作",render:function(item,name,index){
		            		 var html='<div class="input-group-btn" style="80px;">';
		            		if(config.hasPermission("AFEEBILLITEMCHECK")){
		            		 html+='<a href="javascript:void(0)"  style="margin:0 3px"  onclick="feeBill.dialogFeeBill(undefined,'+item.id+')" class="btn btn-xs blue" >'
		            		 	+'<span class="glyphicon glyphicon-edit" title="修改"></span></a>';}
		            		if(config.hasPermission("AFEEBILLDELETE")){
		            		 html+='<a href="javascript:void(0)"  style="margin:0 3px" onclick="feeBill.deleteFeebill('+item.id+','+(item.status!=2||item.billingStatus!=1)+')" class="btn btn-xs red " >'
		            		 	+'<span class="glyphicon glyphicon-remove" title="删除"></span></a>';}
		            		if(config.hasPermission("AFEEBILLFILE")){
		            		 html+='<a href="javascript:void(0)"  style="margin:0 3px;" onclick="Outbound.fileManage('+item.id+',2)" class="btn btn-xs blue">'
		            		 	+'<span class="fa '+(util.isNull(item.fileNum,1)==0?'fa-arrow-up':'fa-arrow-down')+'" title="附件管理"></span></a></div>';}
		            		 return html;
		            	 }
		             }];
		if($('[data-role="feeBillGrid"]').getGrid()!=null){
			$('[data-role="feeBillGrid"]').getGrid().destory();
		}
		$('[data-role="feeBillGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			showPage:20,
			pageSize:10,
	        url:config.getDomain()+"/feebill/list",
	        callback:function(){
	        	$('[data-role="feeBillGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
	        	calculateTotalFeeBill();
	        	util.setColumnsVisable($('div[data-role="feeBillGrid"]'),[0,17],columnCheckArray,function(){columnCheckArray=util.getColumnsCheckArray();});
	        	$('[data-role="feeBillGrid"]').find("th[index='0'],th[index='6'],th[index='13'],th[index='15'],th[index='16']").unbind('click').bind('click', function() {
	    			var params = getSearchCondition();
	    			params.indexTh =$(this).attr('index');
	    			$('[data-role="feeBillGrid"]').getGrid().search(params);
	    		});
	        }
		});
	};
	/***********************************************/
	
	//收费项列表子列表
	function addTable(obj,feeHead){
	  	$(obj).find('span').attr('class');
		if($(obj).find('span').attr('class')=='fa fa-plus-square-o'){
			$(obj).find('span').removeClass('fa fa-plus-square-o').addClass('fa fa-minus-square-o');
		var iTr=$(obj).closest('tr');
		var html='<tr class="detail"><td colspan="6">'
			+'<div class="col-md-12"><div data-role="feeDetailGrid'+feeHead+'"></div>'
            +'<div class="modal-footer">'          
			+'<button class="btn btn-primary" type="button" onclick="feeBill.dialogFeeBill('+feeHead+')" >生成账单</button>'
			+'<button class="btn btn-primary" type="button" onclick="feeBill.dialogFillBillList('+feeHead+')">添加到已有账单</button>'
			 +'<button class="btn btn-primary" type="button" onclick="feeBill.copyFeeCharge('+feeHead+')" >复制费用项</button>'
     		+'</div>'
			+'</td></tr>';
	    $(html).insertAfter(iTr);
	    initDetailTable(feeHead);
		}else{
			$(obj).find('span').removeClass('fa fa-minus-square-o').addClass('fa fa-plus-square-o ');
			var iTr=$(obj).closest('tr');
			iTr.next('tr[class="detail"]').remove();
		}
	};
	//拷贝费用项
	function copyFeeCharge(feeHead){
		var	feeData=$('[data-role="feeDetailGrid'+feeHead+'"]').getGrid().selectedRows();	
		if(feeData==undefined||feeData.length==0||feeData.length>1){
			$('body').message({
				type:'warning',
				content:'请选择一个费用项'
			});
			return false;
		}else if(feeData.length==1){
			var itemfeeCharge={
					type:1,
					feeType:feeData[0].feeType,
					unitFee:feeData[0].unitFee,
					totalFee:feeData[0].totalFee,
					feeHead:feeHead,
					productId:feeData[0].productId,
					initialId:feeData[0].initialId,
					exceedId:feeData[0].exceedId,
					feeCount:util.isNull(feeData[0].feeCount,0),
					clientId:feeData[0].clientId,
					createTime:util.formatLong(util.currentTime(1)),
					discountFee:feeData[0].discountFee,
					description:feeData[0].description,
					isCopy:1
			}
			
			$.ajax({
				type:'post',
				url:config.getDomain()+"/feecharge/add",
				data:itemfeeCharge,
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'复制费用项',function(){
						$('[data-role="feeDetailGrid'+feeHead+'"]').getGrid().refresh();
					});
				}
			});
		}
	};
	
	function deleteCopyFeeCharge(feechargeId,feeHead){
           if(feechargeId){
        	   $('body').confirm({
   				content:'确定要删除吗?',
   				callBack:function(){
   					$.ajax({
   					type:'post',
   					url:config.getDomain()+"/feecharge/delete",
   					data:{id:feechargeId},
   					dataType:'json',
   					success:function(data){
   						util.ajaxResult(data,'删除',function(){
   							$('[data-role="feeDetailGrid'+feeHead+'"]').getGrid().refresh();
   						});
   					}
   				});
   					}
   			});
           }
	}
	
	//开票单号详情列表
	function initDetailTable(feeHead){
		var columns=[{title:'费用项',render:function(item,name,index){
			return getFeeTypeStr(item.feeType);
		}},{title:'货品名称',name:'productName'},
		{title:'货主单位',name:'clientName'},
		{title:'提单号',render:function(item){
		    if(item.feeType==5&&item.ladingCode){
		    	return util.isNull(item.ladingCode);
		    }else{
		    	return "";
		    }
		}},
		{title:'货批号',render:function(item){
			if(item.cargoId!=null&&item.cargoId!=0){
				return '<label><a onclick="CargoLZ.openCargoLZ('+item.cargoId+')" href="javascript:void(0)">'+util.isNull(item.cargoCode)+'</a></label>';
			}else{
				var data=item.inboundMsg;
				if(data!=null&&data.length>1){
				var html='<table class="table inmtable" style="margin-bottom: 0px;">';
			    for(var i=0;i<data.length;i++){
			    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label><a onclick="CargoLZ.openCargoLZ('+util.isNull(data[i].cargoId,1)+')" href="javascript:void(0)">'+util.isNull(data[i].cargoCode)+'</a></label></td></tr>';
			    	}
			    
			    html += '</table>';
			    return html;
				}else if(data!=null&&data.length==1){
					return '<label><a onclick="CargoLZ.openCargoLZ('+util.isNull(data[0].cargoId,1)+')" href="javascript:void(0)">'+util.isNull(data[0].cargoCode)+'</a></label>';
				}else{
				return "";
			}
				}
		}},{
			title:'入库船信',render:function(item){
				if(item.cargoId!=null&&item.cargoId!=0){
					return '<label>'+util.isNull(item.inboundMsg)+'</label>';
				}else{
					var data=item.inboundMsg;
					if(data!=null&&data.length>1){
					var html='<table class="table inmtable" style="margin-bottom: 0px;">';
				    for(var i=0;i<data.length;i++){
				    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label>'+util.isNull(data[i].inboundMsg)+'</label></td></tr>';
				    	}
				    html += '</table>';
				    return html;
					}else if(data!=null&&data.length==1){
						return '<label>'+util.isNull(data[0].inboundMsg)+'</label>';
					}else{
					return "";
				}
				}
			}
		},
		{title:'关联对象',render:function(item){
			if(item.initialId&&item.initialId!=0&&item.feeType!=6){
				return '<a href="javascript:void(0);" onclick="InitialFee.ajaxInitialFee('+item.initialId+')">首期费'+item.initialCode+'</a>';
			}else if(item.exceedId&&item.exceedId!=0){
				return '<a href="javascript:void(0);" onclick="StorageFee.ajaxExceedFee('+item.exceedId+')" >超期费'+item.exceedCode+'</a>';
			}else if(item.initialId&&item.initialId!=0&&item.feeType==6){
				return '<a href="javascript:void(0);" onclick="StorageFee.ajaxOtherFee('+item.initialId+')" >其他费'+item.otherCode+'</a>';
			}
			
		}},
		{title:'创建日期',render:function(item){
			return util.getSubTime(item.createTimeStr,1);
		}},
		{title:'单价(元/吨)',name:'unitFee',render:function(item){return util.toDecimal2(item.unitFee,true);}},
		{title:'数量(吨)',render:function(item){
			if(item.feeType!=5){ return util.toDecimal3(item.feeCount,true);}
		}},
		{title:'总价(元)',name:'totalFee',render:function(item){return util.toDecimal2(item.totalFee,true);}},
		{title:'账单号',name:'feebillCode'},{title:'操作',render:function(item){
			if(item.isCopy&&item.isCopy==1&&!item.feebillId){
				return '<a href="javascript:void(0)" onclick="feeBill.deleteCopyFeeCharge('+item.id+','+item.feeHead+')" style="margin:0 3px" class="btn btn-xs red " ><span class="glyphicon glyphicon-remove" title="删除"></span></a>';
			}
		}}];
		
		var params={
				clientId:$('#clientName').attr('data'),
				feeHead:feeHead,
				billType:$('#type').val(),
				startTime:util.formatLong($("#startTime").val()+" 00:00:00"),
				endTime:util.formatLong($("#endTime").val()+" 23:59:59"),
				type:1
		}	
		
		$('[data-role=feeDetailGrid'+feeHead+']').grid({
			identity : 'feeHead',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			searchCondition:params,
	        url:config.getDomain()+"/feecharge/list",
	        callback:function(){
	        	$('.inmtable').closest("td").css('padding','0px');
	        	//监听选择，统计总量
	        	$('[data-role=feeDetailGrid'+feeHead+']').find(".grid-body>table>tbody>tr").each(function(){
	        		$this=$(this);
	        		$this.click(function(){
	        			$a=$(this);
	        			if($a.hasClass('success')){
                            if($a.find("td[index='11']").text()!=""){
                            	$a.removeClass('success');
                            	$a.find('div[class="checkerbox checked"]').removeClass('checked');
                            }else{
                            	$("#choiceTotalFee"+feeHead).text(util.FloatAdd($("#choiceTotalFee"+feeHead).text(),$a.find("td[index='10']").text(),2));
                            	$("#choiceTotalNum"+feeHead).text(util.FloatAdd($("#choiceTotalNum"+feeHead).text(),$a.find("td[index='9']").text(),3));
                            }                  	        				
	        			}else{
	        				$("#choiceTotalFee"+feeHead).text(util.FloatSub($("#choiceTotalFee"+feeHead).text(),$a.find("td[index='10']").text(),2));
	        				$("#choiceTotalNum"+feeHead).text(util.FloatSub($("#choiceTotalNum"+feeHead).text(),$a.find("td[index='9']").text(),3));
	        			}
	        			
	        		});
	        		$this.find('div[class="checkerbox"]').click(function(){
	        			$b=$(this);
	        			$a=$b.closest('tr');
	        			if($a.hasClass('success')){
                            if($a.find("td[index='11']").text()!=""){
                            	$a.removeClass('success');
                            	$b.removeClass('checked');
                            }else{
                            	$("#choiceTotalFee"+feeHead).text(util.FloatAdd(util.isNull($("#choiceTotalFee"+feeHead).text(),1),$a.find("td[index='10']").text(),2));
                            	$("#choiceTotalNum"+feeHead).text(util.FloatAdd($("#choiceTotalNum"+feeHead).text(),$a.find("td[index='9']").text(),3));
                            }                  	        				
	        			}else{
	        				$("#choiceTotalFee"+feeHead).text(util.FloatSub(util.isNull($("#choiceTotalFee"+feeHead).text(),1),$a.find("td[index='10']").text(),2));
	        				$("#choiceTotalNum"+feeHead).text(util.FloatSub($("#choiceTotalNum"+feeHead).text(),$a.find("td[index='9']").text(),3));
	        			}
	        			
	        		});
	        	});
	        	//全选
	        	$('[data-role=feeDetailGrid'+feeHead+']').find('div[data-role="selectAll"]').click(function(){
	        		$this=$(this);
	        		if($this.hasClass('checked')){
	        			var totalAmount=0,totalNum=0;
	        			$('[data-role=feeDetailGrid'+feeHead+']').find(".grid-body>table>tbody>tr").each(function(){
	        				$itemTr=$(this);
                            if($itemTr.find("td[index='11']").text()!=""){
                            	$itemTr.removeClass('success');
                            	$itemTr.find('div[class="checkerbox checked"]').removeClass('checked');
                            }else{
                            	totalAmount=util.FloatAdd(totalAmount,$itemTr.find("td[index='10']").text(),2);
                            	totalNum=util.FloatAdd(totalNum,$itemTr.find("td[index='9']").text(),3);
                            }                  	        				
	        			});	
	        			$("#choiceTotalFee"+feeHead).text(totalAmount);
	        			$("#choiceTotalNum"+feeHead).text(totalNum);
	        		}else{
	        			$("#choiceTotalFee"+feeHead).text(0);
	        			$("#choiceTotalNum"+feeHead).text(0);
	        		}
	        	});
	        	
                 var html="<div class='form-group detailTotal"+feeHead+"' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>选择账单中金额：</label>" +
                 		"<label class='control-label' style='margin-left: 10px;margin-right:20px;' id='choiceTotalFee"+feeHead+"'></label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>元，数量：</label>"+
                 		"<label class='control-label' style='margin-left: 10px;margin-right:20px;' id='choiceTotalNum"+feeHead+"'></label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>吨</label></div>";	        	
                 $('[data-role=feeDetailGrid'+feeHead+']').find(".detailTotal"+feeHead).remove();
	        	$('[data-role=feeDetailGrid'+feeHead+']').find(".grid-body").parent().append(html);
	        }
		});
	};
	/****************************************************************************/
      //账单列表---添加到已有账单
	function  dialogFillBillList(feeHead){
		$.get(config.getResource()+ "/pages/feebill/dialog_feebilllist.jsp").done(function(data){
			var dialog = $(data);
			var columns=[{title:'账单号',name:'code',render:function(item,name,index){
				return '<a href="javascript:feeBill.insertToBill('+feeHead+','+item.feeHead+','+item.id+','+util.isNull(item.totalFee,1)+')"  >'+item.code+'</a>'
			}},{title:'开票抬头',name:'feeHeadName'}];
			if(dialog.find('[data-role="feeBillListTable"]').getGrid()!=null){
				dialog.find('[data-role="feeBillListTable"]').getGrid().destory();
			}
			if(feeHead){
			dialog.find('[data-role="feeBillListTable"]').grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : true,
				/*searchCondition:{feeHead:feeHead},*/
		       url:config.getDomain()+"/feebill/list?statusStr=0,1,3"//
		       
			});
			}
			dialog.modal({
				keyboard : true
			});
		});
	};
	//添加已有账单
	function insertToBill(feeHead,mfeeHead,feebillId,totalFee ){
		if($('.addLivingFeeBill')&&isOpenConfirm){
			isOpenConfirm=false;
			$('body').confirm({
				content:'确定要添加到已有账单中吗',
				cancel:function(){
					isOpenConfirm=true;
				},
				callBack:function(){
			//添加进入账单然后打开
			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+"/feecharge/addall",
				data:{"feechargelist":getChargeTableData($('[data-role="feeDetailGrid'+feeHead+'"]').getGrid().selectedRows(),feebillId)},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,"添加",function(ndata){
//						 $.ajax({type:'post',
//                   	      url:config.getDomain()+"/feecharge/update",
//                   	      data:{feebillId:feebillId,feeHead:mfeeHead},
//                   	      dataType:'json',
//                   	      success:function(data){
//                   	    util.ajaxResult(data,'添加',function(){
                   	    	$.ajax({type:'post',
                   	    		url:config.getDomain()+"/feebill/update",
                   	    		    data:{id:feebillId,totalFee:util.FloatAdd(util.isNull(totalFee,1),util.isNull($("#choiceTotalFee"+feeHead).text(),1))},
                   	    	        dataType:'json',
                   	    	        success:function(data){
                   	    	        	util.ajaxResult(data,'添加',function(){
                   	    	        		isOpenConfirm=true;
                   	    	        	 //移除账单列表dialog
                    						$('.addLivingFeeBill').remove();
                    						//打开账单详情dialog
                    						dialogFeeBill(feeHead,feebillId);
                    						$('[data-role=feeDetailGrid'+feeHead+']').getGrid().refresh();
                   	    	        	});
                   	    	        }
                   	    		});
//                   	    },true);  	  
//                   	      }
//                      });
						
					},true);
				}
			});
		}
			});
			
			
			
		}
	};
	
	
	/**************************************************************************************/
	//账单详情  只有 feeHead 添加到新的账单， feeHead，feebillId存在  添加到已有账单
	function  dialogFeeBill(feeHead,feebillId){
		var isOk=true;
		if(feebillId){
			isOk=true;
		}else{
			if(feeHead&&$('[data-role="feeDetailGrid'+feeHead+'"]').length!=0){
				var	feeData=$('[data-role="feeDetailGrid'+feeHead+'"]').getGrid().selectedRows();	
				if(feeData==undefined||feeData.length==0){
					$('body').message({
						type:'warning',
						content:'请选择一个费用项'
					});
					isOk=false;
				}else {
					var feeType=feeData[0].feeType;	
					var isReturn=false;
						for(var i=0;i<feeData.length;i++){
							if(feeType!=feeData[i].feeType){
								$('body').message({
									type:'warning',
									content:'请选择同一类费用项'
								});
								isReturn=true;
								break;
							}
						}
						if(isReturn){
							return ;
						}
						
					}
			}
		}
		if(isOk){
		$.get(config.getResource()+ "/pages/feebill/dialog_feebill.jsp").done(function(data) {
			var dialog = $(data);
			initFeeBillControl(dialog,feeHead,feebillId);
			initFeeBillData(dialog,feebillId);
		});
		}
	};
	//初始化账单控件
	function initFeeBillControl(dialog,feeHead,feebillId){
		initFormIput(dialog);
		util.initTimePicker(dialog); 
		dialog.modal({keyboard : true});
		/**生成账单初始化费用*/
		if(!feebillId&&feeHead&&$('[data-role="feeDetailGrid'+feeHead+'"]').length!=0){
		var	feeData=$('[data-role="feeDetailGrid'+feeHead+'"]').getGrid().selectedRows();
			var columns=[{title:'费用项',render:function(item,name,index){
				return getFeeTypeStr(item.feeType);
			}},{title:'货品名称',name:'productName'},
			{title:'货主单位',name:'clientName'},
			{title:'货批号',render:function(item){
				if(item.cargoId!=null&&item.cargoId!=0){
					return '<label><a onclick="CargoLZ.openCargoLZ('+item.cargoId+')" href="javascript:void(0)">'+util.isNull(item.cargoCode)+'</a></label>';
				}else{
					var data=item.inboundMsg;
					if(data!=null&&data.length>1){
					var html='<table class="table inmtable" style="margin-bottom: 0px;">';
				    for(var i=0;i<data.length;i++){
				    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label><a onclick="CargoLZ.openCargoLZ('+util.isNull(data[i].cargoId,1)+')" href="javascript:void(0)">'+util.isNull(data[i].cargoCode)+'</a></label></td></tr>';
				    	}
				    
				    html += '</table>';
				    return html;
					}else if(data!=null&&data.length==1){
						return '<label><a onclick="CargoLZ.openCargoLZ('+util.isNull(data[0].cargoId,1)+')" href="javascript:void(0)">'+util.isNull(data[0].cargoCode)+'</a></label>';
					}else{
					return "";
				}
					}
			}},
			{title:'关联对象',render:function(item){
				if(item.initialId&&item.initialId!=0&&item.feeType!=6){
					return '<a href="javascript:void(0);" onclick="InitialFee.ajaxInitialFee('+item.initialId+')">首期费'+item.initialCode+'</a>';
				}else if(item.exceedId&&item.exceedId!=0){
					return '<a href="javascript:void(0);" onclick="StorageFee.ajaxExceedFee('+item.exceedId+')" >超期费'+item.exceedCode+'</a>';
				}else if(item.initialId&&item.initialId!=0&&item.feeType==6){
					return '<a href="javascript:void(0);" onclick="StorageFee.ajaxOtherFee('+item.initialId+')" >其他费'+item.otherCode+'</a>';
				}
				
			}},
			{title:'创建日期',render:function(item){
				return util.getSubTime(item.createTimeStr,1);
			}},
			{title:'单价(元/吨)',render:function(item){
				return util.toDecimal2(item.unitFee,true);
			}},
			{title:'数量(吨)',render:function(item){
				if(item.feeType!=5){ return util.isNull(item.feeCount);}
			}},
			{title:'总价(元)',name:'totalFee'},{title:'折扣后金额(元)',width:120, render:function(item){
				return '<input class="discountFee form-control" style="width:120px;" onkeyup="config.clearNoNum(this,2)" onchange="feeBill.changeTotalFee(this)" maxlength="20"  key="'+item.id+'" data="'+item.discountFee+'" value="'+item.discountFee+'">'
				
			}}];
			dialog.find("#detailFeeBillCharge").grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : false,
				isUserLocalData:true,
				localData:feeData
			});
			
			if(feeData[0]){
				dialog.find('#feeHeadName,#clientName').val(feeData[0].feeHeadName);
				dialog.find('#feeHeadName,#clientName').attr('data',feeData[0].feeHead);
			var totalFee=0;
			for(var i in feeData){
				if(feeData[i].discountFee)
				totalFee=util.FloatAdd(totalFee,feeData[i].discountFee);
			}
                 var html="<div class='form-group totalGridDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总金额：</label>" +
                 		"<label class='control-label' style='margin-left: 10px;margin-right:20px;' id='totalFee'>"+util.toDecimal2(totalFee,true)+"</label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>元</label></div>";	
                 dialog.find(".totalGridDiv").remove();
	        	dialog.find("#detailFeeBillCharge").find(".grid-body").parent().append(html);
	        	dialog.find('#detailFeeBillCharge').find(".grid-body").attr('style','min-height:0px;');
			dialog.find("#hasTotalFee").text(util.toDecimal2(totalFee,true));
			dialog.find("#hasTotalFee").attr('data',totalFee);
			dialog.find("#feeType").text(feeData[0].feeType);
			}
		}
		/*********初始化抬头******/
//		util.urlHandleTypeahead("/baseController/getClientName",dialog.find('#feeHeadName'),function(item){return item.name+"["+item.code+"]";},undefined,undefined,200);
		util.urlHandleTypeaheadAllData("/baseController/getClientName",dialog.find('#feeHeadName'),function(item){return item.name+"["+item.code+"]";},undefined,undefined,200);
		
		/**********添加修改抬头*********/
		dialog.find(".addFeeHead").click(function(){
			BaseInfo.openClientDialog(null,function(id,name){
				util.urlHandleTypeaheadAllData("/baseController/getClientName",dialog.find('#feeHeadName'),function(item){return item.name+"["+item.code+"]";},undefined,undefined,200);
				
//				util.urlHandleTypeahead("/baseController/getClientName",dialog.find('#feeHeadName'));
				if(name&&id){
					dialog.find("#feeHeadName").val(name);
                    dialog.find("#feeHeadName").attr('data',id);	
                    //同时更新费用项的抬头
                    dialog.find("#feeHeadName").removeAttr('disabled');
                    dialog.find(".modifyFeeHead").text("确认修改");
				}
				
			});
			
		});
		dialog.find("#back").click(function(){
			var id=dialog.find("#id").text();
			$.get(config.getResource()+ "/pages/feebill/reback.jsp").done(function(data) {
				var mdialog = $(data);
				initFormIput(mdialog);
				mdialog.find("#btnSubmit").click(function(){
					$.ajax({
						type:'post',
						url:config.getDomain()+"/feebill/rollback",
						data:{id:id,status:mdialog.find("#backStatus").val()},
						dataType:'json',
						success:function(data){
							util.ajaxResult(data,'回退',function(ndata){
								mdialog.remove();
								initFeeBillData(dialog,id);	
								if($('[data-role="feeBillGrid"]'))
           						 util.updateGridRow($('[data-role="feeBillGrid"]'),{id:id,url:'/feebill/list'});
							});
						}
					});
				});
				mdialog.modal({
					keyboard : true
				});
			});
		});
		
		dialog.find(".modifyFeeHead").click(function(){
			$this=$(this);
			var text=$this.text();
			if(text=='修改'){
				dialog.find("#feeHeadName").removeAttr('disabled');
				$this.text('确认修改');
			}else if(text=='确认修改'){
				dialog.find("#feeHeadName").attr('disabled','disabled');
				if(dialog.find("#id").text()&&dialog.find("#feeHeadName").attr('data')){
//				$.ajax({
//					type:'post',
//					url:config.getDomain()+"/feecharge/update",
//					data:{feebillId:dialog.find("#id").text(),feeHead:dialog.find("#feeHeadName").attr('data')},
//					dataType:'json',
//					success:function(data){
//						util.ajaxResult(data,'修改',function(){
							
                           $.ajax({type:'post',
                        	      url:config.getDomain()+"/feebill/update",
                        	      data:{id:dialog.find("#id").text(),feeHead:dialog.find("#feeHeadName").attr('data')},
                        	      dataType:'json',
                        	      success:function(data){
                        	    util.ajaxResult(data,'修改',function(){
                        	    	initFeeBillData(dialog,dialog.find('#id').text());
                        	    	if($('[data-role="feeBillGrid"]'))
            						 util.updateGridRow($('[data-role="feeBillGrid"]'),{id:dialog.find("#id").text(),url:'/feebill/list'});	
                        	    });  	  
                        	      }
                           });
//						},true);
//					}
//				});
				}
				$this.text('修改');
			}
		});
		
		
		/*********编辑*********/
		dialog.find(".editFeeCharge").click(function(){
			$this=$(this);
			var id=dialog.find("#id").text();
			var key=$this.attr('key');
			var totalFee=dialog.find("#totalFee").text();
			if(id&&key==0){
	    	var columns=[{title:'费用项',render:function(item,name,index){
	    		return getFeeTypeStr(item.feeType);
			}},{title:'货品名称',name:'productName'},
			{title:'货主单位',name:'clientName'},
			{title:'货批号',render:function(item){
				if(item.cargoId!=null&&item.cargoId!=0){
					return '<label><a onclick="CargoLZ.openCargoLZ('+item.cargoId+')" href="javascript:void(0)">'+util.isNull(item.cargoCode)+'</a></label>';
				}else{
					var data=item.inboundMsg;
					if(data!=null&&data.length>1){
					var html='<table class="table inmtable" style="margin-bottom: 0px;">';
				    for(var i=0;i<data.length;i++){
				    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label><a onclick="CargoLZ.openCargoLZ('+util.isNull(data[i].cargoId,1)+')" href="javascript:void(0)">'+util.isNull(data[i].cargoCode)+'</a></label></td></tr>';
				    	}
				    
				    html += '</table>';
				    return html;
					}else if(data!=null&&data.length==1){
						return '<label><a onclick="CargoLZ.openCargoLZ('+util.isNull(data[0].cargoId,1)+')" href="javascript:void(0)">'+util.isNull(data[0].cargoCode)+'</a></label>';
					}else{
					return "";
				}
					}
			}},
			{title:'关联对象',render:function(item){
				if(item.initialId&&item.initialId!=0&&item.feeType!=6){
					return '<a href="javascript:void(0);" onclick="InitialFee.ajaxInitialFee('+item.initialId+')">首期费'+item.initialCode+'</a>';
				}else if(item.exceedId&&item.exceedId!=0){
					return '<a href="javascript:void(0);" onclick="StorageFee.ajaxExceedFee('+item.exceedId+')" >超期费'+item.exceedCode+'</a>';
				}else if(item.initialId&&item.initialId!=0&&item.feeType==6){
					return '<a href="javascript:void(0);" onclick="StorageFee.ajaxOtherFee('+item.initialId+')" >其他费'+item.otherCode+'</a>';
				}
				
			}},
			{title:'创建日期',render:function(item){
				return util.getSubTime(item.createTimeStr,1);
			}},
			{title:'单价(元/吨)',render:function(item){
				return util.toDecimal2(item.unitFee,true);
			}},
			{title:'数量(吨)',render:function(item){
				if(item.feeType!=5){ return util.isNull(item.feeCount);}
			}},
			{title:'总价(元)',name:'totalFee'},{title:'折扣后金额(元)',width:120, render:function(item){
				return '<input class="discountFee form-control" style="width:120px;" onkeyup="config.clearNoNum(this,2)" onchange="feeBill.changeTotalFee(this)" maxlength="20"  key="'+item.id+'" data="'+item.discountFee+'" value="'+item.discountFee+'">'
				
			}},
			{title:'操作',render:function(item,name,index){
				return '<a class="btn btn-xs red" onclick="feeBill.removeFeeCharge('+item.id+','+item.totalFee+','+id+')" href="javascript:void(0)"><span title="移除" class="glyphicon glyphicon glyphicon-remove"></span></a>';
			}}];
	    	if(dialog.find("#detailFeeBillCharge").getGrid()!=undefined)
				dialog.find("#detailFeeBillCharge").getGrid().destory();
			dialog.find("#detailFeeBillCharge").grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : false,
				searchCondition:{feebillId:id},
				pageSize:0,
		        url:config.getDomain()+"/feecharge/list",
		        callback:function(){
			        	var mdata=dialog.find("#detailFeeBillCharge").getGrid().getAllItems();
			        	var total=0;
			        	for(var i in mdata){
			        		if(mdata[i]&&mdata[i].totalFee){
			        			total=util.FloatAdd(total,mdata[i].discountFee);
			        		}
			        	}
		                 var html="<div class='form-group totalGridDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总金额：</label>" +
		                 		"<label class='control-label' style='margin-left: 10px;margin-right:20px;' id='totalFee'>"+util.toDecimal2(total,true)+"</label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>元</label></div>";	
		                 dialog.find(".totalGridDiv").remove();
		                 dialog.find('#detailFeeBillCharge').find(".grid-body").attr('style','min-height:0px;');
			        	dialog.find("#detailFeeBillCharge").find(".grid-body").parent().append(html);
			        }
			});
			$this.attr('key',1);
			$this.find('.fa').text('编辑完成');
			
			}else if(id&&key==1){
				var columns=[{title:'费用项',render:function(item,name,index){
					return getFeeTypeStr(item.feeType);
				}},{title:'货品名称',name:'productName'},
				{title:'货主单位',name:'clientName'},
				{title:'货批号',render:function(item){
					if(item.cargoId!=null&&item.cargoId!=0){
						return '<label><a onclick="CargoLZ.openCargoLZ('+item.cargoId+')" href="javascript:void(0)">'+util.isNull(item.cargoCode)+'</a></label>';
					}else{
						var data=item.inboundMsg;
						if(data!=null&&data.length>1){
						var html='<table class="table inmtable" style="margin-bottom: 0px;">';
					    for(var i=0;i<data.length;i++){
					    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label><a onclick="CargoLZ.openCargoLZ('+util.isNull(data[i].cargoId,1)+')" href="javascript:void(0)">'+util.isNull(data[i].cargoCode)+'</a></label></td></tr>';
					    	}
					    
					    html += '</table>';
					    return html;
						}else if(data!=null&&data.length==1){
							return '<label><a onclick="CargoLZ.openCargoLZ('+util.isNull(data[0].cargoId,1)+')" href="javascript:void(0)">'+util.isNull(data[0].cargoCode)+'</a></label>';
						}else{
						return "";
					}
						}
				}},
				{title:'关联对象',render:function(item){
					if(item.initialId&&item.initialId!=0&&item.feeType!=6){
						return '<a href="javascript:void(0);" onclick="InitialFee.ajaxInitialFee('+item.initialId+')">首期费'+item.initialCode+'</a>';
					}else if(item.exceedId&&item.exceedId!=0){
						return '<a href="javascript:void(0);" onclick="StorageFee.ajaxExceedFee('+item.exceedId+')" >超期费'+item.exceedCode+'</a>';
					}else if(item.initialId&&item.initialId!=0&&item.feeType==6){
						return '<a href="javascript:void(0);" onclick="StorageFee.ajaxOtherFee('+item.initialId+')" >其他费'+item.otherCode+'</a>';
					}
					
				}},
				{title:'创建日期',render:function(item){
					return util.getSubTime(item.createTimeStr,1);
				}},
				{title:'单价(元/吨)',render:function(item){
					return util.toDecimal2(item.unitFee,true);
				}},
				{title:'数量(吨)',render:function(item){
					if(item.feeType!=5){ return util.isNull(item.feeCount);}
				}},
				{title:'总价(元)',name:'totalFee'},{title:'折扣后金额(元)',width:120, render:function(item){
					return '<input class="discountFee form-control" style="width:120px;" onkeyup="config.clearNoNum(this,2)" onchange="feeBill.changeTotalFee(this)" maxlength="20"  key="'+item.id+'" data="'+item.discountFee+'" value="'+item.discountFee+'">'
					
				}}];
		    	if(dialog.find("#detailFeeBillCharge").getGrid()!=undefined)
					dialog.find("#detailFeeBillCharge").getGrid().destory();
				dialog.find("#detailFeeBillCharge").grid({
					identity : 'id',
					columns : columns,
					isShowIndexCol : false,
					isShowPages : false,
					searchCondition:{feebillId:id},
					pageSize:0,
			        url:config.getDomain()+"/feecharge/list",
			        callback:function(){
			        	var mdata=dialog.find("#detailFeeBillCharge").getGrid().getAllItems();
			        	var total=0;
			        	for(var i in mdata){
			        		if(mdata[i]&&mdata[i].totalFee){
			        			total=util.FloatAdd(total,mdata[i].discountFee);
			        		}
			        	}
		                 var html="<div class='form-group totalGridDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总金额：</label>" +
		                 		"<label class='control-label' style='margin-left: 10px;margin-right:20px;' id='totalFee'>"+util.toDecimal2(total,true)+"</label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>元</label></div>";	
		                 dialog.find(".totalGridDiv").remove();
			        	dialog.find("#detailFeeBillCharge").find(".grid-body").parent().append(html);
			        	dialog.find('#detailFeeBillCharge').find(".grid-body").attr('style','min-height:0px;');
			        }
				});
				$this.attr('key',0);
				$this.find('.fa').text('编辑');
			}
		});
		/******************/
		dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
		/*****************/
		dialog.find('#fundsTotalFee').change(function(){
			if(util.FloatSub(dialog.find('#hasTotalFee').text(),dialog.find('#fundsTotalFee').val())>=0){
				dialog.find('#hasTotalFee').text(util.FloatSub(dialog.find('#hasTotalFee').text(),dialog.find('#fundsTotalFee').val(),2));
			}else{
				$('body').message({
					type:'warning',
					content:'到账金额超过剩余金额'
				});
//				dialog.find('#fundsTotalFee').val("");
			}
		});
		/*****************/
		//编辑
		dialog.find("#openFundsTable").click(function(){
			$this=$(this);
			var key=$this.attr('key');
			var id=dialog.find("#id").text();
			if(id&&key==0){
				var columns=[{title:'到账确认人',name:'accountUserName'},{title:'到账日期',render:function(item,name,index){
					return util.getSubTime(item.accountTimeStr,1);
				}},{title:'到账金额(元)',name:'accountFee'},{title:'操作',render:function(item){
					 return  '<div class="input-group-btn"><a href="javascript:void(0)"  style="margin:0 4px" data='+item.accountUserId+'  onclick="feeBill.updateAccountFee(this,'+item.id+','+id+')" class="btn btn-xs blue" ><span class="glyphicon glyphicon-edit" title="修改"></span></a>'
        	         +'<a href="javascript:void(0)" onclick="feeBill.deleteAccountFee(this,'+item.id+','+id+')" style="margin:0 4px" class="btn btn-xs red " ><span class="glyphicon glyphicon-remove" title="删除"></span></a></div>';
				}}];
				dialog.find('[data-role="fundsDetailTable"]').show();
				if(dialog.find('[data-role="fundsDetailTable"]').getGrid()!=undefined)
					dialog.find('[data-role="fundsDetailTable"]').getGrid().destory();
					
				dialog.find('[data-role="fundsDetailTable"]').grid({
					identity : 'id',
					columns : columns,
					isShowIndexCol : false,
					isShowPages : false,
					searchCondition:{feebillId:id},
			        url:config.getDomain()+"/accountbilllog/list"
				});
				$this.attr('key',1);
			}else{
				dialog.find('[data-role="fundsDetailTable"]').hide();
				$this.attr('key',0);
			}
		});
		/************/
		dialog.find('#submitto').click(function(){
			if(dialog.find('.submitto').attr('data')&&dialog.find('.submitto').attr('data')!=0){
			   var datast={ids:dialog.find('.submitto').attr('data'),typeId:dialog.find('#id').text(),type:5,content:dialog.find('#comment').val(),typeStatus:2,
					   url:'#/feebill/get?id='+dialog.find('#id').text(),
						msgContent:dialog.find('#createUserName').text()+"提交的一份账单已审批通过，请您获悉。"};
			   var datact={ids:dialog.find('.copyto').attr('data'),typeId:dialog.find('#id').text(),type:5,url:'#/feebill/get?id='+dialog.find('#id').text(),
						msgContent:dialog.find('#createUserName').text()+"提交的一份账单已审批通过，请您获悉。"};
			  workFlow(dialog,datast,datact,function(data){
				  if(data.code=='0000'){
		                 $('#body').message({
		                	type:'success',
		                	content:'提交成功'
		                 });			  
		                 dialog.remove();
				  }
			  });
			}else{
				$('body').message({
					type:'warning',
					content:'请填写提交对象'
				});
			}
			});
		/*****************/
		dialog.find(".sureBilling").click(function(){
			$this=$(this);
			var text=$this.text();
			if(text=='修改'){
				dialog.find("#billingCode,.billingTime,#unTaxFee,#taxRate,#taxFee").removeAttr('disabled');
				$this.text('确认修改');
			}else if(text=='确认修改'){
				dialog.find("#billingCode,.billingTime,#unTaxFee,#taxRate,#taxFee").attr('disabled','disabled');
				$.ajax({
					type:'post',
					url:config.getDomain()+"/feebill/update",
					data:{id:dialog.find('#id').text(),billingCode:dialog.find('#billingCode').val(),billingUserId:systemUserId,
						billingTime:util.formatLong(dialog.find(".billingTime").val()),
						unTaxFee:dialog.find("#unTaxFee").val(),
	                    taxRate:dialog.find("#taxRate").val(),
	                    taxFee:dialog.find("#taxFee").val(),
	                    billingContent:dialog.find("#billingContent").val()
						},
					dataType:'json',
					success:function(data){
						util.ajaxResult(data,'修改',function(){
							initFeeBillData(dialog,dialog.find('#id').text());
						});
					}
				});
				$this.text('修改');
			}
		});
		/*********未税金额自动计算***********/
		dialog.find("#unTaxFee,#taxRate").keyup(function(){
			if(util.isNull(dialog.find("#unTaxFee").val(),1)!=0&&util.isNull(dialog.find("#taxRate").val(),1)!=0){
				dialog.find("#taxFee").val(util.FloatMul(util.isNull(dialog.find("#unTaxFee").val(),1),util.isNull(dialog.find("#taxRate").val(),1),2));
			}
		});
		/****************/
		//保存提交
		dialog.find(".save").click(function(){
			$this=$(this);
			var isOk=true;
			var status=$this.attr('key');
			if(status!=5){
				
				$(this).attr('disabled','disabled');
			}
			var data,resultStr;
			var url;
			var feebillId=dialog.find('#id').text();
			if(status==0||status==1){
				data={
						code:dialog.find("#code").text(),
						feeType:dialog.find("#feeType").text(),
						feeHead:dialog.find("#feeHeadName").attr('data'),
						clientId:dialog.find("#clientName").attr('data'),
						totalFee:dialog.find("#totalFee").text(),//总价
						description:dialog.find("#description").val(),
						accountTime:util.formatLong(dialog.find("#accountTime").val()),
						createUserId:systemUserId,
						createTime:util.formatLong(util.currentTime(0)),
                        status: util.isNull($this.attr('data'),1)==1?undefined:status					
				}
				if(status==1){
					resultStr='提交';
					if(dialog.find('.submitto').attr('data')&&dialog.find('.submitto').attr('data')!=0){
					if(feebillId){
						var datast={ids:dialog.find('.submitto').attr('data'),typeId:feebillId,type:5,typeStatus:0,
								url:'#/feebill/get?id='+feebillId,
								msgContent:systemUserName+"提交了一份客户为"+dialog.find("#feeHeadName").val()+"的"+dialog.find("#code").text()+"账单，请您审批。"};
						   var datact={ids:dialog.find('.copyto').attr('data'),typeId:feebillId,type:5,url:'#/feebill/get?id='+feebillId,
									msgContent:systemUserName+"提交了一份客户为"+dialog.find("#feeHeadName").val()+"的"+dialog.find("#code").text()+"账单，请您获悉。"};
						  workFlow(dialog,datast,datact);
						}}else{
							$('body').message({
								type:'warning',
								content:'请填写提交对象'
							});
							isOk=false;
						}
				}else{
					resultStr='保存';
				}
			}else if(status==2){
				resultStr='审核';
				data={id:feebillId,totalFee:dialog.find("#totalFee").text(),status:status};
				//工作流提交
				var datast={ids:systemUserId,typeId:feebillId,type:5,content:dialog.find('#comment').val(),typeStatus:2,
						url:'#/feebill/get?id='+feebillId,
						msgContent:dialog.find('#createUserName').text()+"提交的一份客户为"+dialog.find("#feeHeadName").val()+"的"+dialog.find("#code").text()+"账单已审批通过，请您获悉。"};
				var datact={ids:dialog.find('.copyto').attr('data'),typeId:feebillId,type:5,url:'#/feebill/get?id='+feebillId,
						msgContent:dialog.find('#createUserName').text()+"提交的一份客户为"+dialog.find("#feeHeadName").val()+"的"+dialog.find("#code").text()+"账单已审批通过，请您获悉。"};
				  workFlow(dialog,datast,datact,undefined,true);
			}else if(status==3){
				resultStr='回退';
				data={id:feebillId,status:status};
				var datast={ids:systemUserId,typeId:dialog.find('#id').text(),type:5,content:dialog.find('#comment').val(),typeStatus:3,
						url:'#/feebill/get?id='+feebillId,
						msgContent:dialog.find('#createUserName').text()+"提交的一份客户为"+dialog.find("#feeHeadName").val()+"的"+dialog.find("#code").text()+"账单审批不通过，请您获悉。"};
				var datact={ids:dialog.find('.copyto').attr('data'),typeId:feebillId,type:5,url:'#/feebill/get?id='+feebillId,
						msgContent:dialog.find('#createUserName').text()+"提交的一份客户为"+dialog.find("#feeHeadName").val()+"的"+dialog.find("#code").text()+"账单审批不通过，请您获悉。"};
				  workFlow(dialog,datast,datact,undefined,true);
			}
			else if(status==4){
				if(!config.validateForm(dialog.find(".feeDiv"))||util.FloatSub(util.isNull(dialog.find('#hasTotalFee').attr('data'),1),util.isNull(dialog.find('#fundsTotalFee').val(),1))<0||dialog.find('#fundsTotalFee').val()==0){
					$('body').message({
						type:'warning',
						content:'本次到账不合理'
					});
                   isOk=false;  					
				}
				resultStr='到账';
				var fundsStatus=0;
				if(util.FloatAdd(util.isNull(dialog.find('#hasTotalFee').text(),1),0)>0){
					fundsStatus=1;
				}else if(util.FloatAdd(util.isNull(dialog.find('#hasTotalFee').text(),1),0)<=0){
					fundsStatus=2;
				}
				data={id:feebillId,
					 totalFee:dialog.find("#totalFee").text(),
					 fundsStatus:fundsStatus
						}
			}else if(status==5){
				if(!config.validateForm(dialog.find(".billingCodeDiv"))||$this.text().indexOf('确认开票')==-1){
	                   isOk=false;  					
					}
				resultStr='开票';
				data={id:feebillId,
						billingCode:dialog.find('#billingCode').val(),
						billingUserId:systemUserId,
						billingTime:util.formatLong(dialog.find('.billingTime').val()),
						unTaxFee:dialog.find("#unTaxFee").val(),
	                    taxRate:dialog.find("#taxRate").val(),
	                    taxFee:dialog.find("#taxFee").val(),
	                    billingContent:dialog.find("#billingContent").val(),
						billingStatus:1
						}
			}
			
			if(feebillId){
				url=config.getDomain()+"/feebill/update";
				data.id=feebillId;
			}else{
				url=config.getDomain()+"/feebill/add";
			}
			if(isOk){
			config.load();
			$.ajax({
				type:'post',
				url:url,
				data:data,
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,resultStr,function(ndata,nmap){
						if(status==0||status==1){
							if(!feebillId){
								feebillId=nmap.id;
								if(status==1){
									var datast={ids:dialog.find('.submitto').attr('data'),typeId:feebillId,type:5,typeStatus:0,
											url:'#/feebill/get?id='+feebillId,
											msgContent:systemUserName+"提交了一份客户为"+dialog.find("#feeHeadName").val()+"的"+dialog.find("#code").text()+"账单，请您审批。"};
									var datact={ids:dialog.find('.copyto').attr('data'),typeId:feebillId,type:5,url:'#/feebill/get?id='+feebillId,
											msgContent:systemUserName+"提交了一份客户为"+dialog.find("#feeHeadName").val()+"的"+dialog.find("#code").text()+"账单，请您获悉。"};
									workFlow(dialog,datast,datact);								
								}
							}
							$.ajax({
								type:'post',
								url:config.getDomain()+"/feecharge/addall",
								data:{"feechargelist":getChargeTableData(dialog.find("#detailFeeBillCharge").getGrid().getAllItems(),feebillId,dialog.find("#detailFeeBillCharge"))},
								dataType:'json',
								success:function(data){
									if($('[data-role=feeDetailGrid'+feeHead+']'))
										 $('[data-role=feeDetailGrid'+feeHead+']').getGrid().refresh();
									else if($('[data-role="feeBillGrid"]'))
										util.updateGridRow($('[data-role="feeBillGrid"]'),{id:feebillId,url:'/feebill/list'});
									initFeeBillData(dialog,feebillId);
								}
							});
						}else if(status==4){
							$.ajax({
								type:'post',
								url:config.getDomain()+"/accountbilllog/add",
								data:{feebillId:feebillId,accountUserId:systemUserId,
									accountTime:util.formatLong(dialog.find(".fundsTime").val()),accountFee:dialog.find("#fundsTotalFee").val()},
								dataType:'json',
								success:function(data){
									
									if($('[data-role="feeBillGrid"]'))
										util.updateGridRow($('[data-role="feeBillGrid"]'),{id:feebillId,url:'/feebill/list'});	
									
									initFeeBillData(dialog,feebillId);
										
										if(dialog.find('[data-role="fundsDetailTable"]').getGrid()!=undefined)
											dialog.find('[data-role="fundsDetailTable"]').getGrid().refresh();
										
										
								}
							});
						}else{
							if($('[data-role="feeBillGrid"]'))
							util.updateGridRow($('[data-role="feeBillGrid"]'),{id:feebillId,url:'/feebill/list'});	
								
								
								
//							$('[data-role="feeBillGrid"]').getGrid().refresh();
							initFeeBillData(dialog,feebillId);
						}
						$this.removeAttr('disabled');
					});
				}
			});
			}
		});
	};
	
	
	
	
	
	//获取费用表数据
    function getChargeTableData(feeChargedata,feebillId,obj){
    	var data;
    	if(feeChargedata){
    		data=feeChargedata;
    		for(var i in data){
    			if(data[i]){
    				data[i].feebillId=feebillId;
    				if(obj)
    				data[i].discountFee=$(obj).find('input[key="'+data[i].id+'"]').val();
    			}
    		}
    		return JSON.stringify(data);
    	}else{
    		return null;
    	}
    };	
	//初始化账单数据
	function initFeeBillData(dialog,id){
		dialog.find('.save,#submitto,.hideDiv').hide();
		dialog.find('#fundsTotalFee').val("");
		dialog.find(".fundsTime").datepicker('setDate',util.currentTime(0));
		if(!id){
			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+"/feebill/getcodenum",
				dataType:'json',
				success:function(data){
					config.unload();
					if(data.code=='0000'){
						dialog.find("#code").text(data.data[0].codeNum);
					}else{
						$('body').message({
							type:'error',
							content:'获取编号失败'
						});
					}
				}
			});
			dialog.find("#accountTime").datepicker('setDate',util.currentTime(0));
			dialog.find("button[key='0'],button[key='1'],.subtoDiv").show();
			dialog.find(".editFeeCharge").hide();
		}else{
             //初始化费用
	    	var columns=[{title:'费用项',render:function(item,name,index){
	    		return getFeeTypeStr(item.feeType);
			}},{title:'货品名称',name:'productName'},
			{title:'货主单位',name:'clientName'},
			{title:'货批号',render:function(item){
				if(item.cargoId!=null&&item.cargoId!=0){
					return '<label><a onclick="CargoLZ.openCargoLZ('+item.cargoId+')" href="javascript:void(0)">'+util.isNull(item.cargoCode)+'</a></label>';
				}else{
					var data=item.inboundMsg;
					if(data!=null&&data.length>1){
					var html='<table class="table inmtable" style="margin-bottom: 0px;">';
				    for(var i=0;i<data.length;i++){
				    	html+='<tr><td style="border-bottom:1px solid #ddd;"><label><a onclick="CargoLZ.openCargoLZ('+util.isNull(data[i].cargoId,1)+')" href="javascript:void(0)">'+util.isNull(data[i].cargoCode)+'</a></label></td></tr>';
				    	}
				    
				    html += '</table>';
				    return html;
					}else if(data!=null&&data.length==1){
						return '<label><a onclick="CargoLZ.openCargoLZ('+util.isNull(data[0].cargoId,1)+')" href="javascript:void(0)">'+util.isNull(data[0].cargoCode)+'</a></label>';
					}else{
					return "";
				}
					}
			}},
			{title:'关联对象',render:function(item){
				if(item.initialId&&item.initialId!=0&&item.feeType!=6){
					return '<a href="javascript:void(0);" onclick="InitialFee.ajaxInitialFee('+item.initialId+')">首期费'+item.initialCode+'</a>';
				}else if(item.exceedId&&item.exceedId!=0){
					return '<a href="javascript:void(0);" onclick="StorageFee.ajaxExceedFee('+item.exceedId+')" >超期费'+item.exceedCode+'</a>';
				}else if(item.initialId&&item.initialId!=0&&item.feeType==6){
					return '<a href="javascript:void(0);" onclick="StorageFee.ajaxOtherFee('+item.initialId+')" >其他费'+item.otherCode+'</a>';
				}
				
			}},
			{title:'创建日期',render:function(item){
				return util.getSubTime(item.createTimeStr,1);
			}},
			{title:'单价(元/吨)',render:function(item){
				return util.toDecimal2(item.unitFee,true);
			}},
			{title:'数量(吨)',render:function(item){
				if(item.feeType!=5){ return util.isNull(item.feeCount);}
			}},
			{title:'总价(元)',name:'totalFee'},{title:'折扣后金额(元)',width:120, render:function(item){
				return '<input class="discountFee form-control" style="width:160px;" onkeyup="config.clearNoNum(this,2)" onchange="feeBill.changeTotalFee(this)" maxlength="20"  key="'+item.id+'" data="'+item.discountFee+'" value="'+item.discountFee+'">'
			}}];
	    	
	    	if(dialog.find("#detailFeeBillCharge").getGrid()!=null){
	    		dialog.find("#detailFeeBillCharge").getGrid().destory();
	    	}
			dialog.find("#detailFeeBillCharge").grid({
				identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : false,
				pageSize:0,
				searchCondition:{feebillId:id},
		        url:config.getDomain()+"/feecharge/list",
		        callback:function(){
		        	var mdata=dialog.find("#detailFeeBillCharge").getGrid().getAllItems();
		        	var total=0;
		        	for(var i in mdata){
		        		if(mdata[i]&&mdata[i].totalFee){
		        			total=util.FloatAdd(total,mdata[i].discountFee);
		        		}
		        	}
	                 var html="<div class='form-group totalGridDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总金额：</label>" +
	                 		"<label class='control-label' style='margin-left: 10px;margin-right:20px;' id='totalFee'>"+util.toDecimal2(total,true)+"</label><label class='control-label' style='margin-left: 10px;margin-right:20px;'>元</label></div>";	
	                 dialog.find(".totalGridDiv").remove();
	                 dialog.find('#detailFeeBillCharge').find(".grid-body").attr('style','min-height:0px;');
		        	dialog.find("#detailFeeBillCharge").find(".grid-body").parent().append(html);
		        	config.load();
		    		$.ajax({
		    			type:'post',
		    			url:config.getDomain()+"/feebill/list",
		    			data:{id:id,pagesize:0},
		    			dataType:'json',
		    			success:function(data){
		    				util.ajaxResult(data,"初始化",function(ndata){
		    					dialog.find("#id").text(ndata[0].id);
		    					dialog.find("#code").text(ndata[0].code);
		    					dialog.find('#feeHeadName').val(ndata[0].feeHeadName);
		    					dialog.find('#feeHeadName').attr('data',ndata[0].feeHead);
		    					dialog.find('#feeType').text(ndata[0].feeType);
		    					dialog.find('#clientName').text(ndata[0].feeHeadName);
		    					dialog.find('#clientName').attr('data',ndata[0].feeHead);
		    					dialog.find("#description").val(ndata[0].description);
		    					dialog.find('#totalFee').attr('data',ndata[0].totalFee);
		    					dialog.find('#unTaxFee').val(util.isNull(ndata[0].unTaxFee));
		    					dialog.find('#taxRate').val(util.isNull(ndata[0].taxRate));
		    					dialog.find('#taxFee').val(util.isNull(ndata[0].taxFee));
		    					//没有的自动计算未税金额税额税率
		    					if(!ndata[0].taxRate){
		    						var tRate;
		    						if(ndata[0].feeType==8){
		    							tRate=0.11;
		    						}
		    						else{
		    							tRate=0.06
		    						}
		    						dialog.find('#taxRate').val(tRate);
		    					}
		    					if(!ndata[0].unTaxFee){
		    						var tRate;
		    						if(ndata[0].feeType==8){
		    							tRate=0.11;
		    						}
		    						else{
		    							tRate=0.06
		    						}
		    						dialog.find('#unTaxFee').val(util.FloatDiv(ndata[0].totalFee,(1+tRate),2));
		    					}
		    					if(!ndata[0].taxFee){
		    						dialog.find('#taxFee').val(util.FloatSub(ndata[0].totalFee,dialog.find('#unTaxFee').val(),2));
		    					}
		    					dialog.find("#billingContent").val(util.isNull(ndata[0].billingContent));
		    					dialog.find("#fundsAllTotalFee").text(util.isNull(ndata[0].accountTotalFee));
		    					dialog.find("#hasTotalFee").text(util.FloatSub(ndata[0].totalFee,util.isNull(ndata[0].accountTotalFee,1),2));
		    					dialog.find('#hasTotalFee').attr('data',util.FloatSub(ndata[0].totalFee,util.isNull(ndata[0].accountTotalFee,1),2));
		    					dialog.find("#createUserName").text(util.isNull(ndata[0].createUserName));
		    					dialog.find("#fundsUserId").text(util.isNull(ndata[0].fundsUserName));
		    					dialog.find("#fundsTime").text(util.getSubTime(ndata[0].fundsTimeStr,1));
		    					dialog.find("#billingUserId").text(util.isNull(ndata[0].billingUserName));
		    					if(ndata[0].billingTimeStr){
		    						dialog.find(".billingTime").datepicker("setDate",util.getSubTime(ndata[0].billingTimeStr,1));
		    					}else{
		    						dialog.find(".billingTime").datepicker('setDate',util.currentTime(0));
		    					}
		    					dialog.find("#billingTime").text(util.getSubTime(ndata[0].billingTimeStr,1));
		    					dialog.find("#billingCode").val(util.isNull(ndata[0].billingCode));
		    					dialog.find("#accountTime").datepicker('setDate',util.getSubTime(ndata[0].accountTimeStr,1));
		    					//显隐状态控制默认隐藏
		    					showFeeBillControl(dialog,ndata[0].status,ndata[0].fundsStatus,ndata[0].billingStatus);
		    					if(parseFloat(util.FloatSub(ndata[0].totalFee,util.isNull(ndata[0].accountTotalFee,1),2))<=0){
		    						dialog.find(".feeDiv").hide();
		    					}
		    					var reviewStatus=ndata[0].status;
		    					//初始化审批信息
		    					$.ajax({
		    						type:'post',
		    						url:config.getDomain()+"/approvecontent/getapprovecontent",
		    						dataType:'json',
		    						data:{workId:id,type:5},
		    						success:function(data){
		    							util.ajaxResult(data,'获取',function(ndata){
		    								if(ndata.length>0){
		    									var html='';
		    									var userIdStr="a";
		    									for(var i in ndata){
		    										userIdStr+=ndata[i].userId+"a";
		    										if(ndata[i].typeStatus!=0){
		    											var rs="";
		    											if(ndata[i].typeStatus==2){
		    												rs="结果：通过";
		    											}else{
		    												rs="结果：不通过";
		    											}
		    											html+='<div class="form-group">'
		    												+'<label class="col-md-2 control-label">审批意见:</label>'
		    												+'<label class="col-md-10 control-label" style="text-align:left">'+util.isNull(ndata[i].content)+'</label>'
		    												+'</div>'
		    												+'<div class="form-group">'
		    												+'<label class="control-label col-md-2">审批人:</label>'
		    												+'<label class="control-label col-md-4" style="text-align:left">'+util.isNull(ndata[i].userName)+'</label>'
		    												+'<label class="control-label col-md-2">审批日期:</label>'
		    												+'<label class="control-label col-md-4" style="text-align:left">'+util.getSubTime(ndata[i].mReviewTime,1)+'</label>' 
		    												+'</div>';
		    										}
		    									}
		    									dialog.find(".commentsDiv").empty();
		    									dialog.find(".commentsDiv").append(html);
		    								}
		    								if(reviewStatus==1&&userIdStr.indexOf("a"+systemUserId+"a")!=-1){
		    									dialog.find("button[key='2'],button[key='3'],#submitto,.subtoDiv,.commentDiv,.createDiv").show();  		    									
		    								}else{
		    									
		    								}
		    							},true);
		    						}
		    						});
		    				},true);
		    			}
		    		});
		        }
			});
		//
		}
			
		
		//缓存
		$.ajax({
  			type : "get",
  			url : config.getDomain()+"/approvecontent/checkcache?workType=5",
  			dataType : "json",
  			success : function(data) {
  				if(data.data.length>0){
  					for(var i=0;i<data.data.length;i++){
  						if(data.data[i].type==1){
  							dialog.find(".submitto").val(data.data[i].name);
  							dialog.find(".submitto").attr("data",data.data[i].ids);
  						}else{
  							dialog.find(".copyto").val(data.data[i].name);
  							dialog.find(".copyto").attr("data",data.data[i].ids);
  						}
  					}
  				}
  			}
  			});
	
	}
	
	/*********根据状态显示控件*********/
	function showFeeBillControl(dialog,status,fundsStatus,billingStatus){
		
		if(status==0||status==3){
			dialog.find("button[key='0'],button[key='1'],.subtoDiv").show();
		}else if(status==1){
			dialog.find(".discountFee").attr('readonly','readonly');
		}else if(status==2){
//			dialog.find("button[key='0']").show().attr("data",1);
			dialog.find(".discountFee").attr('readonly','readonly');
			dialog.find(".editFeeCharge").hide();
			dialog.find("#back").show();
			if(fundsStatus==0&&billingStatus==0){
				dialog.find("button[key='4'],button[key='5'],.createDiv,.commentsDiv,.feeDiv,.billingCodeDiv").show();
			}else if(fundsStatus==1&&billingStatus==0){
				dialog.find("button[key='4'],button[key='5'],.createDiv,.commentsDiv,.fundsDiv,.feeDiv,.billingCodeDiv").show();
			}else if(fundsStatus==2&&billingStatus==0){
				dialog.find("button[key='4'],button[key='5'],.createDiv,.commentsDiv,.feeDiv,.fundsDiv,.billingCodeDiv").show();
			}else if((fundsStatus==0||fundsStatus==1)&&billingStatus==1){
				dialog.find("#feeHeadDiv").hide();
				dialog.find("#billingCode,.billingTime,#unTaxFee,#taxRate,#taxFee,#billingContent").attr('disabled','disabled');
				dialog.find("button[key='4'],button[key='5'],.createDiv,.commentsDiv,.fundsDiv,.billingDiv,.feeDiv,.billingCodeDiv").show();
				dialog.find("button[key='5']").text("修改");
			}else{
				dialog.find("#feeHeadDiv").hide();
				dialog.find("button[key='5'],.createDiv,.commentsDiv,.fundsDiv,.billingDiv,.billingCodeDiv").show();
				dialog.find("#billingCode,.billingTime").attr('disabled','disabled');
				dialog.find("button[key='5']").text("修改");
			}
		}
		//未到账时如果没有到账操作权限不显示到账时间
		if(fundsStatus==0){
			if(!config.hasPermission('AFEEBILLFUND')){
				$("#funttime").hide();
			}
		}
		
	};
	/*****************/
	//从账单移除费用项
	function removeFeeCharge(feechargeId,totalFee,feebillId){
		var total=util.isNull($('#hasTotalFee').text(),1);
		var feebillTotalFee=util.isNull($(".totalGridDiv>#totalFee").text(),1);
		$('#hasTotalFee').text(util.FloatSub(total,totalFee,2)).attr('data',util.FloatSub(total,totalFee,2));
		if(parseFloat(util.FloatSub(total,totalFee,2))>0){
		$.ajax({
			type:'post',
			url:config.getDomain()+"/feecharge/cleanfrombill",
			data:{id:feechargeId},
			dataType:'json',
			success:function(data){
                 util.ajaxResult(data,'移除',function(){
                	 //更新总价
                	 $.ajax({type:'post',
            	    		url:config.getDomain()+"/feebill/update",
            	    		    data:{id:feebillId,totalFee:util.FloatSub(feebillTotalFee,totalFee,2)},
            	    	        dataType:'json',
            	    	        success:function(data){
            	    	        	util.ajaxResult(data,'移除',function(){
            	    	        		 $("#detailFeeBillCharge").getGrid().refresh();
            	    	        		 
            	    	        	});
            	    	        }
            	    		});
                	
                 },true);				
			}
		});
		}else{
			$('body').message({
				type:'warning',
				content:'至少包含一个费用项'
			});
		}
	}
	/********************/
	   /**工作流提交*/
	function  workFlow(obj,datast,datact,callBack,isTrue){
		 //提交
		if($(obj).find('.submitto').attr('data')&&$(obj).find('.submitto').attr('data')!=0||isTrue){
             $.ajax({
            	type:'post',
            	url:config.getDomain()+"/approvecontent/sendcheck",
            	dataType:'json',
            	data:datast,
            	success:function(data){
            		config.unload();
            		   if(callBack)
            			callBack(data);
            	} 
             });			
		}
		//抄送
		if($(obj).find('.copyto').attr('data')&&$(obj).find('.copyto').attr('data')!=0){
			config.load();
			$.ajax({
            	type:'post',
            	url:config.getDomain()+"/approvecontent/sendcopy",
            	dataType:'json',
            	data:datact,
            	success:function(data){
            		config.load();
            		if(data.code=='0000'){
            		}
            	} 
             });	
		}
	};
	/**************************************************************修改删除账单到账信息*********************************************/
	function updateAccountFee(obj,id,feebillId){
		var nTr=$(obj).closest("tr");
		var userId=$(obj).attr('data');
		var userName=$(nTr).find('td[index="0"]').text();
		var time=$(nTr).find('td[index="1"]').text();
		var count=$(nTr).find('td[index="2"]').text();	
		nTr.empty();
		$(nTr).append('<td index="0"><input type="text" class="form-control" style="width:100%" data='+userId+' value='+userName+' ></td>'
				+'<td index="1"><input type="text" class="form-control date date-picker" style="width:100%" ></td>'
				+'<td index="2"><input type="text" class="form-control" style="width:100%" data='+count+' value='+count+' ></td>'
				+'<td index="3"><div class="input-group-btn">'
				+'<a href="javascript:void(0)"  style="margin:0 4px" onclick="feeBill.sureAccountFee(this,'+id+','+feebillId+')" class="btn btn-xs blue" >'
				+'<span class="glyphicon glyphicon glyphicon-ok" title="确定"></span></a>'
   	            +'<a href="javascript:void(0)" onclick="feeBill.cancelAccountFee(this,'+id+','+feebillId+')" style="margin:0 4px" class="btn btn-xs blue " >'
   	            +'<span class="fa fa-undo" title="取消"></span></a></div></td>');
		$.ajax({
			url:config.getDomain()+"/auth/user/get",
			dataType:'json',
			success : function(data) {
				var array1=new Array();
				for(var i=0;i<data.data.length;i++){
						array1.push({id:data.data[i].id,name:data.data[i].name});
				}
				util.handleTypeahead(array1,$(nTr).find("td[index='0'] input"),null,null,true);
			}
		});
		util.initTimePicker($(nTr));
		$(nTr).find("td[index='1'] input").datepicker('setDate',time);
	};
	//确定修改到账
	function sureAccountFee(obj,id,feebillId){
		var mDialog=$(obj).closest(".modal-dialog");
		var tabe=$(obj).closest('div [data-role="fundsDetailTable"]');
		var nTr=$(obj).closest("tr");
		var userId=$(nTr).find("td[index='0'] input").attr('data');
		var time=util.formatLong($(nTr).find("td[index='1'] input").val());
		var count=$(nTr).find("td[index='2'] input").val();
		var leftCount=$(nTr).find("td[index='2'] input").attr('data');
		
		if(count>util.FloatAdd(leftCount,$(mDialog).find("#hasTotalFee").text())){
			$('body').message({
				type:'warning',
				content:'修改的金额不合理'
			});
		}else{
			var fundsStatus=0;
			if(util.FloatAdd($(mDialog).find("#hasTotalFee").text(),0)>0&&util.FloatSub(util.FloatSub(count,leftCount),$(mDialog).find("#hasTotalFee").text())==0)//当前状态为1，修改后为2
			{
				fundsStatus=2;
			}else if($(mDialog).find("#hasTotalFee").text()==0&&count!=leftCount){
				fundsStatus=1;
			}
			
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/accountbilllog/update",
			data:{id:id,
				accountUserId:userId,
				accountTime:time,
				accountFee:count},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'修改',function(){
					refreshFeeBill(mDialog,feebillId,function(){
						if(fundsStatus!=0){
							$.ajax({
								type:'post',
								url:config.getDomain()+"/feebill/update",
								data:{id:feebillId,fundsStatus:fundsStatus},
								dataType:'json',
								success:function(){}
							});
						}
						tabe.getGrid().refresh();
					});
				});
			}
		});
		}
	};
	//取消修改到账
    function cancelAccountFee(obj){
    	var tabe=$(obj).closest('div [data-role="fundsDetailTable"]');
    	tabe.getGrid().refresh();
    }   	
    //删除到账记录
	function deleteAccountFee(obj,id,feebillId){
		if(id){
	var mDialog=$(obj).closest(".modal-dialog");	
			
	$('body').confirm({
		content:'确定要删除吗?',
		callBack:function(){
		var mDialog=$(obj).closest(".modal-dialog");
		var tabe=$(obj).closest('div [data-role="fundsDetailTable"]');
		var fundsStatus=0;
		if($(mDialog).find("#hasTotalFee").text()==0){
			fundsStatus=1;
		}
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/accountbilllog/delete",
			data:{id:id},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'删除',function(){
					if(fundsStatus!=0){
						$.ajax({
							type:'post',
							url:config.getDomain()+"/feebill/update",
							data:{id:feebillId,fundsStatus:fundsStatus},
							dataType:'json',
							success:function(){}
						});
					}
					refreshFeeBill(mDialog,feebillId,function(){
						tabe.getGrid().refresh();
					});
				});
			}
		});
		}});}
	};
/**************************************************************修改删除账单到账信息end*********************************************/
	
	//刷新数据
	function refreshFeeBill(obj,feebillId,callback){
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+"/feebill/list",
			data:{id:feebillId},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'初始化到账',function(ndata){
					$(obj).find("#fundsAllTotalFee").text(util.isNull(ndata[0].accountTotalFee));
					$(obj).find("#hasTotalFee").text(util.FloatSub(ndata[0].totalFee,$(obj).find("#fundsAllTotalFee").text(),2));
					$(obj).find('#hasTotalFee').attr('data',util.FloatSub($(obj).find('#totalFee').attr('data'),$(obj).find("#fundsAllTotalFee").text(),2));
					
					if(util.FloatSub($(obj).find('#totalFee').attr('data'),$(obj).find("#fundsAllTotalFee").text(),2)<=0){
						$(obj).find(".feeDiv").hide();
					}else{
						$(obj).find(".feeDiv,button[key='4']").show();
					}
					if($('[data-role="feeBillGrid"]').getGrid()!=undefined)
						util.updateGridRow($('[data-role="feeBillGrid"]'),{id:feebillId,url:'/feebill/list'});
					if(callback)
					callback();
				},true);
			}
			});
	};
	//修改总价
	function changeTotalFee(obj){
		var dialog=$(obj).closest(".modal-dialog");
		var totalFee=0,i=0;
		dialog.find("#detailFeeBillCharge").find('.discountFee').each(function(){
    	   $this=$(this);
           totalFee=util.FloatAdd(totalFee,util.isNull($this.val(),1),2);    	   
       });	
		dialog.find("#totalFee").text(util.toDecimal2(totalFee,true));
       $("#hasTotalFee").text(util.toDecimal2(totalFee,true)).attr('data',totalFee);
	};
	//导出excel
	function exportXML(obj,type){
		var _dialog=$(obj).parents(".portlet-body");
		var url = config.getDomain()+"/common/exportExcel?type="+type;
		if(util.isNull($("#cargoId").attr("data"),0)!=0){
			url+="&&cargoId="+$("#cargoId").attr("data");
		}
		if(util.isNull($("#cargoId").val(),0)!=0){
			url+="&&cargoCode="+$("#cargoId").val();
		}
		if(util.isNull($("#productId").attr("data"),0)!=0){
			url+="&&productId="+$("#productId").attr("data");
		}
		if(util.isNull($("#clientName").attr("data"),0)!=0){
			url+="&&clientId="+$("#clientName").attr("data");
		}
		if(util.isNull($("#feeHead").attr("data"),0)!=0){
			url+="&&feeHead="+$("#feeHead").attr("data");
		}
		if(util.isNull($("#totalFee").val(),0)!=0){
			url+="&&totalFee="+$("#totalFee").val();
		}
			url+="&&feeTypes="+getFeeTypeSelected();
		if(util.isNull($("#type").val(),0)!=0){
			url+="&&billType="+$("#type").val();
		}
		if(util.isNull($("#billCode").val(),0)!=0){
			url+="&&code="+$("#billCode").val();
		}
		if(util.isNull($("#billingCode").val(),0)!=0){
			url+="&&billingCode='"+$("#billingCode").val()+"'";
		}
		if(util.isNull($("#billStatus").val(),0)!=-1){
			url+="&&billStatus="+$("#billStatus").val();
		}
		if($("#timeType").val()==1){
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.formatLong(_dialog.find("#startTime").val()+" 00:00:00");
			}
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.formatLong(_dialog.find("#endTime").val()+" 23:59:59");
			}
		}else{
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&billingStartTime="+util.formatLong(_dialog.find("#startTime").val()+" 00:00:00");
			}
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&billingEndTime="+util.formatLong(_dialog.find("#endTime").val()+" 23:59:59");
			}
		}
		
		if(type==52){
			url+="&&name="+util.getSubTime(util.currentTime(1),1);
			window.open(url);
		}else if(type==54){
			url+="&&name="+util.getSubTime(util.currentTime(1),1);
			window.open(url);
		}else if(type==1){
			
		}
		
		
	};
	function getFeeTypeStr(feeType){
		if(feeType){
		if(feeType==1){ return '仓储费';}
		else if(feeType==2){ return '保安费';}
		else if(feeType==3){ return '包罐费';}
		else if(feeType==4){ return '超量费';}
		else if(feeType==5){ return '超期费';}
		else if(feeType==6){ return '其他费';}
		else if(feeType==8){ return '通过费';}
//		else if(feeType==9){ return '油品费';}
//		else if(feeType==10){return 'LAB';}
		}else {
			return "";
		}
	}
	//获取选中的费用类型
	function getFeeTypeSelected(){
		var array='';
		var isAll=true;
		$('#feeTypeDiv input[type="checkbox"]').each(function(){
			 $this=$(this);
			 if($this.is(':checked')){
				array+= $this.val()+",";
			 }else{
				 isAll=false;
			 }
		 });
		if(isAll){
			return null;
		}else {
			return array.length>1?array.substring(0,array.length-1):'-1';
		}
	}
	function checkAllFeeType(){
		$('#feeTypeDiv input[type="checkbox"]').each(function(){
			 $this=$(this);
			 $this.attr("checked",true);
		 });
	}
	function exportDetailBillFee(obj){
		var id=$(obj).closest(".modal-dialog").find("#id").text();
		var url=config.getDomain()+"/feebill/exportDetailFee?id="+id;
		window.open(url);
	};
	return {
		changeTab:changeTab,
		initTableBtn:initTableBtn,
		initTable:initTable,
		deleteFeebill:deleteFeebill,
		dialogFeeBill:dialogFeeBill,
		dialogFillBillList:dialogFillBillList,
		addTable:addTable,
		copyFeeCharge:copyFeeCharge,
		deleteCopyFeeCharge:deleteCopyFeeCharge,
		insertToBill:insertToBill,
		removeFeeCharge:removeFeeCharge,
		updateAccountFee:updateAccountFee,
		deleteAccountFee:deleteAccountFee,
		cancelAccountFee:cancelAccountFee,
		sureAccountFee:sureAccountFee,
		deleteAccountFee:deleteAccountFee,
		refreshFeeBill:refreshFeeBill,
		changeTotalFee:changeTotalFee,
		exportXML:exportXML,
		exportDetailBillFee:exportDetailBillFee
	}
}();