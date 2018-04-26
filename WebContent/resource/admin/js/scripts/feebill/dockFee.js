//码头规费
var DockFee=function(){
	var systemUserId,systemUserName;
	//切换标签
	function changeTab(obj,item){
		$(obj).parent().addClass("active").siblings().removeClass("active");
		initTable(item);
	}
	//初始化列表控件
	function initSearchCtr(){
		util.initMonthPicker();
		//船代
  		util.urlHandleTypeaheadAllData("/shipagent/select",$("#clientName"),function(item){return item.name	});
		$("#searchFee").click(function(){
		 $('div[data-role="dockFeeGrid"]').getGrid().search(getSearchCondition());
		});	
		$("#reset").click(function(){
			$("#clientName,#startTime,#endTime,#code,#shipName").val('').removeAttr();
			$("#arrivalType").val(0);
			$("#searchFee").click();
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
	
	function getSearchCondition(){
		return {
			code:$("#code").val(),
			shipName:$("#shipName").val(),
			clientName:$("#clientName").val(),
			startTime:util.formatLong($("#startTime").val()+"-01 00:00:00"),
			endTime:util.formatLong(util.getEndDayOfMonth($("#startTime").val())),
			arrivalType:$("#arrivalType").val()==0?"":$("#arrivalType").val(),
			billType:$("#billType").val()==0?"":$("#billType").val(),
			status:$("#status").val()==-1?"":$("#status").val()
		}
	}
	
	
	function deleteDockFee(id){
		$('body').confirm({
			content:'确定要删除该结算单吗?',
			callBack:function(){
				config.load();
				$.ajax({
					type:'get',
					url:config.getDomain()+"/dockfee/delete?id="+id,
					dataType:'json',
					success:function(data){
						util.ajaxResult(data,'清除',function(ndata,nmap,mMsg){
							if(mMsg&&mMsg.length>0){
								$('body').message({
									type:'warning',
									content:mMsg
								});
							}else{
								$('body').message({
									type:'success',
									content:'清除成功'
								});
								$('div[data-role="dockFeeGrid"]').getGrid().refresh();
							}
						},true);
					}
				});
			}
		});
	};
	
	function initTable(item){
		$(".dockFeeDiv").hide();
		var columns,url;
		if(item==1){
			$(".dockFeeDiv").hide();
			url='/dockfee/getarrivallist/';
			columns=[{title:'船舶英文名',name:'shipName'},{title:'船舶中文名',name:'refName'},
			         {title:'到港时间',name:'arrivalTime'},{title:'到港类型',name:'arrivalType'},{title:"贸易类型",render:function(item){
			        	 if(item.shipType&&item.shipType==1){
			        		 return "内贸";
			        	 }else{
			        		 return "外贸";
			        	 }
			         }},
			         {title:'船代',name:'shipAgentName'},{title:'货品名',name:'productName'},{title:'操作',render:function(item){
			        	  return '<div style="width:100px;" class="input-group-btn"><a href="javascript:void(0)" style="margin:0 2px;" onclick="DockFee.initDockFeeDialog(undefined,'+item.arrivalId+')"'
			        	 +' class="btn btn-xs blue" ><span class="glyphicon glyphicon-edit" title="生成结算单"></span></a></div>';
			         }}];
		}else if(item==2){
			$(".dockFeeDiv").show();
			url='/dockfee/list/';
			columns=[{title:'结算单编号',render:function(item){ return '<a href="javascript:void(0)" onclick="DockFee.initDockFeeDialog('+item.id+','+item.arrivalId+')">'+item.code+'</a>'}},
			         {title:'对方单位',name:'clientName'},{title:'船名',name:'boundMsg'},{title:'到港日期',name:'arrivalTime'},{title:'到港类型',name:'arrivalType'},{title:"贸易类型",render:function(item){
			        	 return (item.tradeType==1?'外贸':'内贸');
			         }},{title:"发票类型",name:"",render:function(item){
			        	 return (item.billType==1?'手撕发票':'增值税发票');
			         }},{title:'计算总金额（元）',name:"totalFee"},{title:'实际总金额（元）',name:"discountFee"},{title:"状态",render:function(item){
			        	 if(item.status==0){ return "<label style='color:#99CC33'>未提交</label>";}
			      	   else if(item.status==1){ return "<label style='color:#666699'>已提交</label>";}
			      	   else if(item.status==2){ return "<label style='color:#9966CC'>已生成账单</label>";}
			      	   else if(item.status==3){ return "<label style='color:#d64635'>已开票</label>";}
			      	   else if(item.status==4){ return "<label style='color:#FF9966'>已完成</label>";}
			         }},{title:'操作',render:function(item){
			        	 var html='<div style="width:100px;" class="input-group-btn">';
			        	 if(config.hasPermission('APIERFEEUPDATE'))
			        	    html+='<a href="javascript:void(0)"  style="margin:0 3px"  onclick="DockFee.initDockFeeDialog('+item.id+','+item.arrivalId+')" class="btn btn-xs blue" ><span class="glyphicon glyphicon-edit" title="修改"></span></a>';
			        	 if(config.hasPermission('APIERFEEDELETE')&&item.status<1)
			        		 html+='<a href="javascript:void(0)" onclick="DockFee.deleteDockFee('+item.id+')" style="margin:0 3px" class="btn btn-xs red " ><span class="glyphicon glyphicon-remove" title="删除"></span></a>';
			        	 html+='</div>';
			        	 return html;
			         }}];
		}
		if($('div[data-role="dockFeeGrid"]').getGrid()!=null)
			$('div[data-role="dockFeeGrid"]').getGrid().destory();
		
		$('div[data-role="dockFeeGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			showPage:9,
			url : config.getDomain()+url,
			callback:function(){
				$('div[data-role="dockFeeGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
			}
		});

	}
	
	function initDockFeeDialog(id,arrivalId){
		$.get(config.getResource()+ "/pages/inbound/dockFee/dockFeeDialog.jsp").done(function(data){
			var dialog = $(data);
			dialog.find("#arrivalId").text(arrivalId);
			initDialogCtr(dialog);
			if(id)
			initDialogMsg(dialog,id);
			else
			initDialogBaseMsg(dialog,arrivalId);
		});
	};
	//初始化dialog的时间
	function initDialogCtr(dialog){
		initFormIput(dialog);
		dialog.modal({keyboard : true});
		dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
		util.initTimePicker(dialog);//初始化时间
		
		//账单类型
		dialog.find("#save,#submit,#modifyFeeCharge").click(function(){
			doDockFeeSubmit(dialog,$(this));
		});
	};
	
	function doDockFeeSubmit(dialog,obj){

		var status=$(obj).attr('data');
		var msg="";
		if(status==0){ msg=$(obj).text();
		}else if(status==1){ msg="提交";
		}else{ msg="修改";
		status=undefined;
		}
		if(!config.validateForm(dialog.find(".dockFeeDiv"))){
			$("body").message({
				type : 'error',
				content : '必填信息未填写完整'
			});
			return false ;
		}
		var tradeType=util.isNull(dialog.find("#tradeType").val(),1);
		var dockFeeDto={
				"dockfee":{
					'id': util.isNull(dialog.find("#id").text(),1)==0?undefined:util.isNull(dialog.find("#id").text(),1),
					'code':dialog.find("#code").text(),//结算单编号
					'billCode':dialog.find("#billCode").val(),
					'tradeType':dialog.find("#tradeType").val(),//账单类型，2.内贸账单，1.外贸账单
					'accountType':dialog.find("#accountType").val(),//结算类型，1.现结，2.月结
					'billType':dialog.find("#billType").val(),//发票类型：1.手撕发票，2.增值发票
					'arrivalId':dialog.find("#arrivalId").text(),
					'clientName':dialog.find("#clientName").val(),
					'productName':dialog.find("#productName").val(),
					'contractName':dialog.find("#contractName").val(),
					'overTime':dialog.find("#overTime").val(),
					'waterWeigh':dialog.find("#waterWeigh").val(),
					'accountTime':util.formatLong(dialog.find("#accountTime").val()),
					'status':status,
					'createUserId':systemUserId,
					'createTime':util.formatLong(util.currentTime(1))
				}
					}
		var feeChargeList=new Array();
		feeChargeList.push({'id':util.isNull(dialog.find("#berthChargeId").text(),1)==0?undefined:util.isNull(dialog.find("#berthChargeId").text(),1),
				'type':status,
				'feeType':1,//靠泊费,
				'clientName':dialog.find("#clientName").val(),
				'billCode':dialog.find("#billCode").val(),
				'unitFee':(util.isNull(dialog.find("#billType").val(),1)==1?0.08:0.25),
				'totalFee':util.FloatAdd(util.isNull(dialog.find("#berthCharge").val(),1),util.isNull(dialog.find("#tsBerthCharge").val(),1),2),
				'discountFee':(tradeType==2?util.isNull(dialog.find("#berthDiscountFee").val(),1):util.FloatAdd(util.isNull(dialog.find("#berthCharge").val(),1),util.isNull(dialog.find("#tsBerthCharge").val(),1),2))
			});
		//如果金额为0
		console.log(util.isNull(feeChargeList[0].totalFee,1));
		if(util.isNull(feeChargeList[0].totalFee,1) == 0&&status!=0){
			$('body').message({type:'error',content:"靠泊费为0元,无法操作。"});
			return;
		}
		if(status!=undefined){
		
			if(util.isNull(dialog.find("#waterCharge").val(),1)!=0){
				feeChargeList.push({'id':util.isNull(dialog.find("#waterChargeId").text(),1)==0?undefined:util.isNull(dialog.find("#waterChargeId").text(),1),
						'type':status,
						'feeType':2,//淡水费,
						'clientName':dialog.find("#clientName").val(),
						'billCode':dialog.find("#billCode").val(),
						'unitFee':16,
						'feeCount':dialog.find("#waterWeigh").val(),
						'totalFee':dialog.find("#waterCharge").val(),
						'discountFee':dialog.find("#waterCharge").val()
				});
			}
		}
		if(status!=undefined){
			
			if(util.isNull(dialog.find("#otherCharge").val(),1)!=0){
				feeChargeList.push({'id':util.isNull(dialog.find("#otherChargeId").text(),1)==0?undefined:util.isNull(dialog.find("#otherChargeId").text(),1),
						'type':status,
						'feeType':3,//其他费
						'clientName':dialog.find("#clientName").val(),
						'billCode':dialog.find("#billCode").val(),
						'totalFee':dialog.find("#otherCharge").val(),
						'discountFee':dialog.find("#otherCharge").val(),
						'description':dialog.find("#description").val()
				});
			}
		}
		dockFeeDto.feeChargeList=feeChargeList;
		$.ajax({
			type : "post",
			url : config.getDomain()+"/dockfee/andorupdate",
			data : {'dockFeeDto':JSON.stringify(dockFeeDto)},
			dataType : "json",
			success : function(data) {
				util.ajaxResult(data,msg,function(ndata,nmap){
					var id=util.isNull(dialog.find("#id").text(),1)==0?nmap.id:util.isNull(dialog.find("#id").text(),1);
					initDialogMsg(dialog,id);
					if($('div[data-role="dockFeeGrid"]').getGrid()&&$("#tab2").closest("li").hasClass("active"))
						util.updateGridRow($('[data-role="dockFeeGrid"]'),{id:dialog.find("#id").text(),url:'/dockfee/list'});
				});
			}
		});
	};
	
	
	//初始化基本信息
	function initDialogBaseMsg(dialog,arrivalId){
		//初始化结算日期
		dialog.find("#accountTime").datepicker("setDate",util.currentTime(0));
		dialog.find("#createUserDiv").hide();
		//获取结算单编号
		 config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+"/dockfee/getcodenum",
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'获取编号',function(ndata,nmap){ dialog.find("#code").text(nmap.codeNum); },true);
				}
			});
			//获取基本信息
		$.ajax({
			type:'post',
			url:config.getDomain()+"/dockfee/getarrivallist",
			data:{arrivalId:arrivalId,pagesize:0},
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取船信息',function(ndata){ 
                //对方单位：如果存在船代，则填写船代，没有为空，品名，船名，发生日期，
				// 船净吨，在港天数，
				if(ndata&&ndata.length==1){
					dialog.find("#shipName").val(util.isNull(ndata[0].shipName)+"/"+util.isNull(ndata[0].refName));
					dialog.find("#arrivalTime").val(ndata[0].arrivalTime);
					dialog.find("#clientName").val(util.isNull(ndata[0].shipAgentName));
					dialog.find("#productName").val(util.isNull(ndata[0].productName));
					dialog.find("#stayDay").text(ndata[0].stayDay);
					dialog.find("#netTons").text(ndata[0].netTons);
					if(util.isNull(ndata[0].shipType,0)!=1){
						dialog.find("#tradeType").val(1);
						dialog.find("#accountType").val(2);
						dialog.find("#billType").val(2);
						initArrivalCharge(dialog,1);
					}else{
						initArrivalCharge(dialog,2);
					}
				}
				},true);
			}
		});
		
	};
	//初始化信息
	function initDialogMsg(dialog,id){
		//获取结算单编号
		 config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+"/dockfee/getdockfeemsg",
				data:{id:id},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'获取基本信息',function(ndata,nmap){
                      if(ndata&&ndata.length==1){
                    	dialog.find("#id").text(id);
                    	dialog.find("#shipName").val(util.isNull(ndata[0].shipName)+"/"+util.isNull(ndata[0].refName));
      					dialog.find("#arrivalTime").val(ndata[0].arrivalTime);
      					dialog.find("#clientName").val(util.isNull(ndata[0].clientName));
      					dialog.find("#productName").val(util.isNull(ndata[0].productName));
      					dialog.find("#code").text(ndata[0].code);
      					dialog.find("#accountTime").datepicker("setDate",util.getSubTime(util.formatString(ndata[0].accountTime),1));
      					dialog.find("#contractName").val(util.isNull(ndata[0].contractName));
      					dialog.find("#accountType").val(ndata[0].accountType) ;
      					dialog.find("#tradeType").val(ndata[0].tradeType) ;
      					dialog.find("#billType").val(ndata[0].billType) ;
      					dialog.find("#stayDay").text(ndata[0].stayDay);
      					dialog.find("#netTons").text(ndata[0].netTons);
      					dialog.find("#status").text(ndata[0].status);
      					initArrivalCharge(dialog,ndata[0].tradeType);  
                    	 //初始化费用项
      					dialog.find("#waterWeigh").val(ndata[0].waterWeigh);//淡水吨
                    	dialog.find("#overTime").val(ndata[0].overTime); //超期时间
                    	dialog.find("#waterWeigh,#overTime").keyup();
                    	var chargeList=ndata[0].feecharge;
                    	if(chargeList&&chargeList.length>0){
                    	var size=chargeList.length;
                    		for(var i=0;i<size;i++){
                    			if(chargeList[i].feeType&&chargeList[i].feeType==3){
                    				dialog.find("#otherChargeId").text(chargeList[i].id);
                    				 dialog.find("#otherCharge").val(chargeList[i].totalFee);
             				       dialog.find("#description").val(util.isNull(chargeList[i].description));
                    			}else if(chargeList[i].feeType&&chargeList[i].feeType==1){
                    				dialog.find("#berthChargeId").text(chargeList[i].id);
                    				dialog.find("#berthDiscountFee").val(chargeList[i].discountFee);
                    			}else if(chargeList[i].feeType&&chargeList[i].feeType==2){
                    				dialog.find("#waterChargeId").text(chargeList[i].id);
                    			}
                    		}
                    	}
                    	if(ndata[0].status&&ndata[0].status==1){
                    		dialog.find("#save").text("回退");
                    		dialog.find("#submit").hide();
                    		dialog.find("#createUserId").text(util.isNull(ndata[0].createUserName)).attr('data',ndata[0].createUserId);
                    		dialog.find("#createTime").text(ndata[0].createTimeStr);
                    		if(ndata[0].tradeType==2) 
                    			dialog.find(".modifyDiv").show();
                    		dialog.find("#createUserDiv").show();
						}else if(ndata[0].status&&ndata[0].status>1){
                    		dialog.find("#save,#submit").hide();
                    		dialog.find("#createUserId").text(util.isNull(ndata[0].createUserName)).attr('data',ndata[0].createUserId);
                    		dialog.find("#createTime").text(ndata[0].createTimeStr);
//                    		if(ndata[0].tradeType==2)
//                    			dialog.find(".modifyDiv").show();
                    		dialog.find("#createUserDiv").show();
						}else{
							dialog.find("#save").text("保存");
							dialog.find("#submit").show();
							dialog.find("#createUserDiv").hide();
						}
                      }
					},true);
				}
			});
		
		
		
		
	};
	
	
	function setTradeType(obj){
		initArrivalCharge($(obj).closest(".modal-dialog"),obj.value);
	};
	
	function initArrivalCharge(dialog,type){
		var stayDay=dialog.find("#stayDay").text();
		var netTons=dialog.find("#netTons").text();
		var html="";
        if(util.isNull(type,1)==1){//外贸账单
        	html+='<tr><td><label class="hidden" id="berthChargeId"></label>'+
        	'<label class="control-label" style="text-align:center" >停泊费</label></td>'+
			'<td colspan="4">净吨（'+util.toDecimal3(netTons,true)+'）×单价（固定０．２5）×在港天数（'+util.isNull(stayDay,1)+'）</td>'+
			'<td><input maxlength="10" type="text" class="form-control " readonly="readonly" id="berthCharge" /></td>'+//靠泊费
			'</tr>' +
            '<tr><td><label class="control-label" style="text-align:center">特殊靠泊费</label></td>'+
			'<td colspan="4"><label class="control-label"  style="float:left;">净吨（'+util.toDecimal3(netTons,true)+'）×０．１５×超期小时</label><input type="text" maxlength="6" class="form-control"  onkeyup="config.clearNoNum(this,2);DockFee.initTSKBF(this,value);" style="width:76px;float:left;" id="overTime"></td>'+
			'<td><input type="text" maxlength="10" class="form-control " readonly="readonly"  id="tsBerthCharge"/></td>'+// 特殊靠泊费
			'</tr>' +
		  '<tr><td><label class="hidden" id="waterChargeId"></label><label class="control-label" style="text-align:center">淡水费</label></td>'+
			'<td colspan="4"><label class="control-label"  style="float:left;">单价（１６.00）×数量</label><input type="text" class="form-control" maxlength="10"  id="waterWeigh" style="width:76px;float:left;" onkeyup="config.clearNoNum(this,2);DockFee.initDSF(this,value);" /><label class="control-label"  style="float:left;">吨</label></td>'+
			'<td><input type="text" maxlength="10" class="form-control" readonly="readonly" onkeyup="config.clearNoNum(this,2);"  id="waterCharge" /></td>'+//淡水费
			'</tr>' +
		'<tr><td><label class="hidden" id="otherChargeId"></label><label class="control-label" style="text-align:center">其它费用</label></td>'+
			'<td colspan="4"><label class="control-label"  style="float:left;">说明：</label><input type="text" maxlength="50" class="form-control" id="description" style="width:80%;float:left;" /></td>'+
			'<td><input type="text" maxlength="10" class="form-control" onkeyup="config.clearNoNum(this,2);" id="otherCharge" /></td>'+//其他费
			'</tr>' ;
        	dialog.find("#discountFeeTh").hide();
        	dialog.find("#arrivalChargeInfo").empty().append(html);
          dialog.find("#berthCharge").val(util.FloatMul(util.isNull(netTons,1)*0.25,util.isNull(stayDay,1),2));
        	  
        }else if(util.isNull(type,1)==2){//内贸账单
        	
        	html+='<tr><td><label class="hidden" id="berthChargeId"></label><label class="control-label" style="text-align:center">靠泊费</label></td>'+
			'<td colspan="4">净吨（吨）（'+util.toDecimal3(netTons,true)+'） ×单价（0.08） ×在港天数（'+util.isNull(stayDay,1)+'）</td>'+
			'<td><input type="text" maxlength="10" class="form-control" readonly="readonly" id="berthCharge"   /></td>'+// 靠泊费
			'<td class="input-group"><input type="text" maxlength="10" class="form-control" id="berthDiscountFee" />';
        	if(config.hasPermission('MMODIFYFEECHARGE')){
			html+='<div class="input-group-btn modifyDiv" style="padding-left:0px;padding-right:0px;">'+
            '<button type="button" class="btn btn-primary" status=3 id="modifyFeeCharge">修改</button></div>';
        	}
           html+='</td></tr>' +
			'<tr><td><label class="hidden" id="waterChargeId"></label><label class="control-label" style="text-align:center">淡水费</label></td>'+
				'<td colspan="4"><label class="control-label"  style="float:left;">单价（１６.00）×数量</label><input maxlength="10" type="text" class="form-control"  id="waterWeigh" style="width:76px;float:left;" onkeyup="config.clearNoNum(this,2);DockFee.initDSF(this,value);" /><label  style="float:left;" class="control-label">吨</label></td>'+
				'<td><input type="text" maxlength="10" class="form-control"  readonly="readonly" id="waterCharge"  /></td>'+//淡水费
				'<td></td>'+
				'</tr>' +
			'<tr><td><label class="hidden" id="otherChargeId"></label><label class="control-label" style="text-align:center">其他费用</label></td>'+
				'<td colspan="4"><label class="control-label"  style="float:left;">说明：</label><input type="text" maxlength="50"  class="form-control"  id="description" style="width:80%;float:left;"  /></td>'+
				'<td><input type="text" maxlength="10" class="form-control" onkeyup="config.clearNoNum(this,2);" id="otherCharge" /></td>'+
				'<td></td>'+
				'</tr>' ;
        	dialog.find("#discountFeeTh").show();
        	dialog.find("#arrivalChargeInfo").empty().append(html);
        	if(util.isNull(netTons,1)>0)
        		dialog.find("#berthCharge,#berthDiscountFee").val(util.FloatMul(util.isNull(netTons,1)*0.08,util.isNull(stayDay,1),2));
        	dialog.find("#modifyFeeCharge").click(function(){
        		 doDockFeeSubmit(dialog,$(this));
        	});
        } 
 
	}
	//特殊靠泊费
    function initTSKBF(obj,value){
    	var dialog=$(obj).closest(".modal-dialog");
    	var netTons=util.isNull(dialog.find('#netTons').text(),1);
    	dialog.find("#tsBerthCharge").val(util.FloatMul(util.isNull(netTons,1)*0.15,util.isNull(value,1),2));
    }	
    //淡水费
    function initDSF(obj,value){
    	var tr=$(obj).closest("tr");
    	tr.find("#waterCharge").val(util.FloatMul(util.isNull(value,1),16,2));
    }
    
   function exportExcel(){
	   var url=config.getDomain()+"/dockfee/exportExcel?nowTime="+util.currentTime(0);
	   if(util.isNull($("#clientName"),1)!=0){
		   url+="&clientName="+$("#clientName").val();
	   }else{
		 url+="&clientName=null";  
	   }
	   if(util.isNull($("#startTime").val(),1)==0){
			$('body').message({
				type:'warning',
				content:'请选择月份日期'
			});
			return;
		}else{
			url+="&startTime="+util.formatLong($("#startTime").val()+"-01 00:00:00");
			url+="&endTime="+util.formatLong(util.getEndDayOfMonth($("#startTime").val()));
		}
		window.open(url);
   }
   function exportListExcel(){
	   var url=config.getDomain()+"/dockfee/exportListExcel?nowTime="+util.currentTime(0);
	   if(util.isNull($("#code").val())!='')
		   url+="&code="+util.isNull($("#code").val());
	   if(util.isNull($("#shipName").val())!="")
		   url+="&shipName="+util.isNull($("#shipName").val());
	   if(util.isNull($("#clientName").val())!="")
		   url+="&clientName="+util.isNull($("#clientName").val());
	   if(util.isNull($("#arrivalType").val())!="")
		   url+="&arrivalType="+util.isNull($("#arrivalType").val());
	   if(util.isNull($("#billType").val())!="")
		   url+="&billType="+util.isNull($("#billType").val());
	   if(util.isNull($("#status").val())!="")
		   url+="&status="+util.isNull($("#status").val());
	   if(util.isNull($("#startTime").val(),1)==0){
			$('body').message({
				type:'warning',
				content:'请选择月份日期'
			});
			return;
		}else{
			url+="&startTime="+util.formatLong($("#startTime").val()+"-01 00:00:00")
			+"&endTime="+util.formatLong(util.getEndDayOfMonth($("#startTime").val()));
		}
	   console.log(url);
	   window.open(url);
   }
	return{
		changeTab:changeTab,
		initSearchCtr:initSearchCtr,
		initTable:initTable,
		initDockFeeDialog:initDockFeeDialog,
		deleteDockFee:deleteDockFee,
		setTradeType:setTradeType,
		initTSKBF:initTSKBF,
		initDSF:initDSF,
		exportExcel:exportExcel,
		exportListExcel:exportListExcel
	}
	
}();