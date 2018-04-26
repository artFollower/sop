//发货查询
var InvoiceQuery=function(){
	var columnCheckArray;
	var timmer=new Object();
	var showVir=1;
	var pageSize,pageNo;
	function init(){
		initCtr();
		refreshTable();
		initTable();
	}
	//初始化查询控件
	function initCtr(){
		util.initDatePicker();
		util.urlHandleTypeaheadAllData("/product/select",$('#productName')); // 初始化货品
		$("#startTime").val(util.currentTime(0));
		$("#endTime").val(util.currentTime(0));
		$("#search").click(function(){
			$("[data-role='invoicequeryGrid']").getGrid().search(getSearchCondition());			
			$("#isShowAll").click();
		});
		//重置
		$(".reset").click(function(){
			$("#vsName,#serial,#ladingEvidence,#productId,#cargoCode").val('').removeAttr();
			$("#deliverType").val(0);
			$("#status").val(-1);
			$("#startTime").val('');
			$("#endTime").val('');
			$(".search").click();
		});
		$("#refreshQuery").popover();
		$("#refreshQuery").click(function(){
			if(showVir==1){
				$(this).addClass("blue");
				showVir=2;
			}else if(showVir==2){
				$(this).removeClass("blue");
				showVir=1;
			}});
		$("#isShowAll").click(function(){
			checkTotalMsg($(this));
		});
	};
	function getSearchCondition(){
		return {
		   vsName:$("#vsName").val(),
	       productId:util.isNull($("#productName").attr("data"),1),
	       ladingEvidence:$("#ladingEvidence").val(),//提单号
	       deliverType:$("#deliverType").val(),
	       serial:$("#serial").val(),
	       startTime:util.formatLong($("#startTime").val()+" 00:00:00"),
		   endTime:util.formatLong($("#endTime").val()+" 23:59:59"),
		   timeType:$("#timeType").val(),
		   cargoCode:$("#cargoCode").val(),
		   status:$("#status").val()
		}
	};
	function  checkTotalMsg(obj){
		$this=$(obj);
		if($this.is(":checked")){//
			if($('[data-role="invoicequeryGrid"]').getGrid()!=null){
				//获取条件下的统计数据
				$.ajax({
                    type:'post',
					url:config.getDomain()+"/invoice/getTotalNum",
					data:getSearchCondition(),
					dataType:'json',
					success:function(data){
						util.ajaxResult(data,'统计',function(ndata){
							var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
							"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总开票量: </label>" +
	                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].deliverNum+" 吨</label>"	+	
			                   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总实发量: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].totalNum+" 吨</label>"	+				
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总车（船）次量: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].countNum+" 次</label>"	+
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总返重: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+util.isNull(ndata[0].measureWeigh)+" 吨</label>"	+
							        "</div>";
							$(".totalFeeDiv").remove();
							$('[data-role="invoicequeryGrid"]').find(".grid-body").parent().append(totalHtml);
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
	}
	//初始化列表
	function initTable(){
		var columns=[{title:"序号",render:function(item,name,index){
			 pageSize=$('div[data-role="invoicequeryGrid"]').getGrid().pageSize;
			  pageNo=$('div[data-role="invoicequeryGrid"]').getGrid().pageNo;
			 return pageSize*pageNo+index+1;
		}},{title:"通知单号",name:"serial"},{title:"货批号",name:"cargoCode"},{title:"货品",name:"productName"},{title:"开票量(吨)",render:function(item){return util.toDecimal3(item.deliverNum,true);}},
		             {title:"实发量(吨)",render:function(item){return util.toDecimal3(item.actualNum,true);}},{title:"表返值",render:function(item){
		            	 if(item.deliverType==1){
		            		 return item.measureWeigh;
		            	 }else{
		            		 return '';	
		            	 }
		             }},
		             {title:"鹤口/泊位",name:"inPort"},{title:"货主名称",name:"clientName"},{title:"提货单位",name:"ladingClientName"},{title:"提单号",name:"ladingEvidence"},
		             {title:"车船号",name:"vsName"},{title:"开票时间",name:"invoiceTime"},
//		             {title:"货体号",name:"goodsCode"},{title:"原号",name:"yuanhao"},
//		             {title:"调号",name:"diaohao"},
		             {title:"出库时间",render:function(item){
		            	 if(util.isNull(item.status)==1){
		            		 return item.createTime;
		            	 }
		             }}, 
		             {title:"发货状态",render:function(item){
		            	 if(util.isNull(item.status)==0){
		            		 return "<font color='#1d953f'>未出库</font>";
		            	 }else if(util.isNull(item.status)==1){
		            		 return "<font color='#826858'>已出库</font>";
		            	 }
		             }}];
		if($("[data-role='invoicequeryGrid']").getGrid()!=undefined){
			$("[data-role='invoicequeryGrid']").getGrid().destory();
		}
		
		$("[data-role='invoicequeryGrid']").grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			searchCondition:getSearchCondition(),
			pageSize:20,
			showPage:9,
			isShowPages : true,
			url : config.getDomain()+"/invoice/invoicequery",
			callback:function(){
				$('[data-role="invoicequeryGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
				util.setColumnsVisable($('div[data-role="invoicequeryGrid"]'),[0,13],columnCheckArray,function(){columnCheckArray=util.getColumnsCheckArray();});
			}
		});
	};
	
	function  refreshTable(){
		var flag = true;
		var invoiceQueryTimmer = setInterval(function() {
			if($("[data-role='invoicequeryGrid']").getGrid()!=undefined&&showVir==2){
				$("[data-role='invoicequeryGrid']").getGrid().search(getSearchCondition());		
			}else if($("[data-role='invoicequeryGrid']").getGrid()==undefined){
				clearInterval(timmer.invoiceQueryTimmer);
			}
		},8000);//1000为1秒钟 
		timmer.invoiceQueryTimmer = invoiceQueryTimmer;
	}
	return {
		init:init,
		timmer:timmer
	}
}();