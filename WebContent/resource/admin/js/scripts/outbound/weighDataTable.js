//称重信息统计
var  weighDataTable=function(){
	var columnCheckArray;
	var pageSize,pageNo;
	function init(){
		initSearchCtr();
		initTable();
	}
	function initSearchCtr(){
		initFormIput();
		initMouseCtr();
		if (jQuery().datepicker) {
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
        });}
		//年份控件
		$('.year-picker').datepicker({
			  rtl: Metronic.isRTL(),
			    language: "zh-CN",
			    orientation: "left",
			    format: "yyyy",
			    showInputs:true,
			    startView:"years",
			    minViewMode:"years",
			    showDay:"false",
	            disableMousewheel:false,
	            changeMonth :false,
			    autoclose: true,
		});
		$("#year").val(new Date().getFullYear());
		$("#month").val((new Date().getMonth()+1));
		util.urlHandleTypeaheadAllData("/park/list",$('#inPort'));
		util.urlHandleTypeaheadAllData("/product/select",$('#productId'));
		util.urlHandleTypeaheadAllData("/client/select",$('#clientId'));
		util.urlHandleTypeaheadAllData("/baseController/getTankCode",$('#tankId'),function(item){return item.code});
		 //高级搜索显示
		$("#searchBtn").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$("#reset").click(function(){
			$('#productId,#clientId,#tankId,#inport,#startTime,#endTime').removeAttr('data').val('');
			$("#search").click();
		});
		$("#search").click(function(){
			$('div[data-role="weighData"]').getGrid().search(getSearchCondition());
			checkTotalMsg($("#isShowAll"));
		});
	  
		$("#isShowAll").click(function(){
			checkTotalMsg($(this));
		});
		$("#isShowOutbound,#isShowTruck").click(function(){
			showShipAndTruck($(this));
		});
	}
	
	function getSearchCondition(){
		return {
			productId:util.isNull($('#productId').attr('data'),1),
			tankId:util.isNull($('#tankId').attr('data'),1),
			clientId:(util.isNull($('#clientId').attr('data'),1)==0?undefined:util.isNull($('#clientId').attr('data'),1)),
			inPort:util.isNull($('#inPort').val(),1)==0?undefined:util.isNull($('#inPort').val()),
			startTime:util.formatLong($("#startTime").val()+" 00:00:00"),
			endTime:util.formatLong($("#endTime").val()+" 23:59:59"),
			timeType:$("#timeType").val(),
			year:util.isNull($("#year").val(),1),
			month:util.isNull($("#month").val(),1),
			isShowOutbound:$("#isShowOutbound").attr("data"),
			isShowTruck:$("#isShowTruck").attr("data")
		};
	};
	
	
	
	function  checkTotalMsg(obj){
		$this=$(obj);
		if($this.is(":checked")){//
			if($('[data-role="weighData"]').getGrid()!=null){
				var params={
						productId:util.isNull($('#productId').attr('data'),1),
						tankId:util.isNull($('#tankId').attr('data'),1),
						inPort:util.isNull($('#inPort').val(),1)==0?undefined:util.isNull($('#inPort').val()),
						clientId:(util.isNull($('#clientId').attr('data'),1)==0?undefined:util.isNull($('#clientId').attr('data'),1)),
						timeType:$("#timeType").val(),		
						startTime:util.formatLong($("#startTime").val()+" 00:00:00"),
						endTime:util.formatLong($("#endTime").val()+" 23:59:59"),
						isShowOutbound:$("#isShowOutbound").attr("data"),
						isShowTruck:$("#isShowTruck").attr("data")
				}	
				//获取条件下的统计数据
				$.ajax({
                    type:'post',
					url:config.getDomain()+"/vehicleDeliveryStatement/getTotalNum",
					data:params,
					dataType:'json',
					success:function(data){
						util.ajaxResult(data,'统计',function(ndata){
							var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
			                   "<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总实发量: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].totalNum+" 吨</label>"	+				
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总车船次量: </label>" +
			                   		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+ndata[0].truckNum+" 次</label>"	+
							        "</div>";
							$(".totalFeeDiv").remove();
							$('[data-role="weighData"]').find(".grid-body").parent().append(totalHtml);
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
	function showShipAndTruck(obj){
		$this=$(obj);
		if($this.is(":checked")){
			$this.attr('data',2);
		}else{
			$this.attr('data',1);
		}
		$("#search").click();
	}
	
	function initTable(){
		 var columns=[{title:"序号",render:function(item,name,index){
			  pageSize=$('div[data-role="weighData"]').getGrid().pageSize;
			  pageNo=$('div[data-role="weighData"]').getGrid().pageNo;
			 return pageSize*pageNo+index+1;
		 }},{title:'通知单号',name:'serial'},{title:'开票时间',name:'invoiceTime'},{title:'车牌号',name:'truckName'},{title:'货主',name:'clientName'},{title:'货品',name:'productName'},{title:'罐号',name:'tankName'},
		             {title:'发货口',name:'inPort'},{title:'计发数（吨）',name:'deliverNum'},{title:'实发量（吨）',name:'actualNum'},{title:'出库时间',name:'createTime'},
		             {title:'仓号',name:'storageInfo'},{title:'发货提单号',name:'ladingEvidence'}];	
		
		if($('div[data-role="weighData"]').getGrid()){
			$('div[data-role="weighData"]').getGrid().destory();
		}
		
		$('div[data-role="weighData"]').grid({
			identity : 'id',
			columns : columns,
			searchCondition:getSearchCondition(),
			isShowIndexCol : false,
			isShowPages : true,
			url : config.getDomain()+"/vehicleDeliveryStatement/queryWeighDailyStatement",
			callback:function(){
				$('[data-role="weighData"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
				util.setColumnsVisable($('div[data-role="weighData"]'),[0],columnCheckArray,function(){columnCheckArray=util.getColumnsCheckArray();});
			}
		});
	};
	 
	function exportXML(type){
		var url = config.getDomain()+"/vehicleDeliveryStatement/exportExcel?type="+type+"&&name="+util.getSubTime(util.currentTime(1),1);
		if(type==1){//导出储品发货统计表
			if(util.isNull($("#year").val(),1)!=0){
				url+="&&year="+util.isNull($("#year").val(),1);
			}else{
				$("body").message({
					type:'warning',
					content:'请填写年份日期'
				});
				return false;
			}
			if(util.isNull($("#month").val(),1)!=0){
				
				url+="&&month="+util.isNull($("#month").val(),1);
				
			}
			window.open(url);
		}else if(type==2){//导出车发统计表
			if(util.isNull($("#year").val(),1)!=0){
				url+="&&year="+util.isNull($("#year").val(),1);
			}else{
				$("body").message({
					type:'warning',
					content:'请填写年份日期'
				});
				return false;
			}
			if(util.isNull($("#month").val(),1)!=0){
				
				url+="&&month="+util.isNull($("#month").val(),1);
				
			}else{
				$("body").message({
					type:'warning',
					content:'请填写月份日期'
				});
				return false;
			}
			window.open(url);
		}else if(type==3){//导出发货明细表
			url+="&&isShowTruck=2";
			if(util.isNull($('#productId').attr('data'),1)!=0)
				url+="&&productId="+util.isNull($('#productId').attr('data'),1);
				if(util.isNull($('#tankId').attr('data'),1)!=0)
				url+="&&tankId="+util.isNull($('#tankId').attr('data'),1);
				if(util.isNull($('#inPort').val(),1)!=0)
				 url+="&&inPort="+util.isNull($('#inPort').val());
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.formatLong($("#startTime").val()+" 00:00:00");
			}else{
				$("body").message({
					type:'warning',
					content:'请填写起始日期'
				});
				return false;
			}
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.formatLong($("#endTime").val()+" 23:59:59");
			}else{
				$("body").message({
					type:'warning',
					content:'请填写截止日期'
				});
				return false;
			}
			console.log(url);
			window.open(url);
		}else if(type==4){//导出车发统计表
			if(util.isNull($("#year").val(),1)!=0){
				url+="&&year="+util.isNull($("#year").val(),1);
			}else{
				$("body").message({
					type:'warning',
					content:'请填写年份日期'
				});
				return false;
			}
			if(util.isNull($("#month").val(),1)!=0){
				url+="&&month="+util.isNull($("#month").val(),1);
			}else{
				$("body").message({
					type:'warning',
					content:'请填写月份日期'
				});
				return false;
			}
			if(util.isNull($("#productId").attr('data'),1)!=0)
				url+="&&productId="+util.isNull($("#productId").attr('data'),1);
			if(util.isNull($("#clientId").attr('data'),1)!=0)
				url+="&&clientId="+util.isNull($("#clientId").attr('data'),1);
			window.open(url);
		}else if(type==5){//导出全车位统计表
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.formatLong($("#startTime").val()+" 00:00:00");
			}else{
				$("body").message({
					type:'warning',
					content:'请填写起始日期'
				});
				return false;
			}
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.formatLong($("#endTime").val()+" 23:59:59");
			}else{
				$("body").message({
					type:'warning',
					content:'请填写截止日期'
				});
				return false;
			}
			window.open(url);
		}
	};
	
	function initMouseCtr(){
		 $("#ex1").mouseover(function(){
			 $(".year,.month").css("color","red");
			  });
		$("#ex1").mouseout(function(){
			 $(".year,.month").css("color","#333333");
			  });
		$("#ex2").mouseover(function(){
			 $(".year,.month").css("color","red");
		  });
		$("#ex2").mouseout(function(){
			 $(".year,.month").css("color","#333333");
			  });
		$("#ex3").mouseover(function(){
			 $(".time,.productId,.tankId,.inPort").css("color","red");
		  });
		$("#ex3").mouseout(function(){
			 $(".time,.productId,.tankId,.inPort").css("color","#333333");
			  });
		$("#ex4").mouseover(function(){
			 $(".year,.month,.productId,.clientId").css("color","red");
		});
		$("#ex4").mouseout(function(){
			 $(".year,.month,.productId,.clientId").css("color","#333333");
		});
		$("#ex5").mouseover(function(){
			 $(".time").css("color","red");
		});
		$("#ex5").mouseout(function(){
			 $(".time").css("color","#333333");
		});
	}
	
	 
	 return{
		 init:init,
		 exportXML:exportXML
	 }
 }();