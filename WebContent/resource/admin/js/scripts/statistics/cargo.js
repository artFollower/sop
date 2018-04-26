var Cargo = function() {
   
	var init = function(){
		initCtr();
		initTable();
	};
	
	var initCtr = function(){
		util.initDatePicker();//初始化日期
		util.urlHandleTypeaheadAllData("/product/select",$('#productId')); // 初始化货品
		util.urlHandleTypeaheadAllData("/client/select",$('#clientId')); // 初始化货品
		$("#startTime").datepicker("setDate",util.currentTime(0).substring(0,8)+"01");
		$("#endTime").datepicker("setDate",util.currentTime(0));
		
		$("#searchCargo").click(function(){
			$('div[data-role="cargoListGrid"]').getGrid().search(getSearchCondition());
			$("#isShowAll").attr('checked',false);
        	$(".totalCargoDiv").remove();
		});
		
		$(".reset").click(function(){
			$("#productId,#clientId,#cargoCode,#shipName").val("").removeAttr('data');
			$("#startTime").datepicker("setDate",util.currentTime(0).substring(0,8)+"01");
			$("#endTime").datepicker("setDate",util.currentTime(0));
			$("#showVir").removeClass("red").attr("data-content","主库").attr('data',1);
			$("#showVirTime").removeClass("green").attr("data-content","转账时间").attr('data',2);
			$("#showText").html("显示未清盘");
			$('#sText').html("当前显示全部");
			$("#isFinish").val(0);
			$("#isShowAll").attr('checked',false);
			$("#searchCargo").click();
		});
		
		
		$("#showVir,#showVirTime").popover();
		$("#showVir").click(function(){
			var $this=$(this);
			var showVir=util.isNull($this.attr('data'),1);
			if(showVir==1)
				$this.addClass("blue").attr("data-content","主副库").attr('data',2);
			else if(showVir==2)
				$this.removeClass("blue").addClass("red").attr("data-content","副库").attr('data',3);
			else
				$this.removeClass("red").attr("data-content","主库").attr('data',1);
			$("#searchCargo").click();
		});
		 
		$("#showVirTime").click(function(){
			var $this=$(this);
			if(util.isNull($this.attr('data'),1) == 2)
				$this.addClass("green").attr("data-content","原始时间").attr('data',1);
			else
				$this.removeClass("green").attr("data-content","转账时间").attr('data',2);
			$("#searchCargo").click();
		});
		
		
		$("#isShowAll").click(function(){
			$this=$(this);
			if($this.is(":checked")){
				config.load();
				$.ajax({
                    type:'get',
					url:config.getDomain()+"/statistics/getCargoListTotal",
					data:getSearchCondition(),
					dataType:'json',
					success:function(data){
						util.ajaxResult(data,'统计',function(ndata){
							var  totalHtml="<div class='form-group totalCargoDiv' style='margin-left: 0px; margin-right: 0px;'>"+
							"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
							"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>当日剩余存量: </label>"+
							"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+
							util.toDecimal3(ndata[0].goodsTotal,true)+" 吨</label>"+				
							"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>本段实发数: </label>" +
							"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+
							util.toDecimal3(ndata[0].goodsben,true)+" 吨</label></div>";
							$('[data-role="cargoListGrid"]').find(".grid-body").parent().append(totalHtml);
						},true);
					}
				});
			}else{
				$this.attr('checked',false);
	        	$(".totalCargoDiv").remove();
			}
			});
		
	};
	
	var getSearchCondition = function(){
		if(util.isNull($("#startTime").val(),1) == 0){
			$("body").message({type:'warning',content:'请填写起始日期'});
			return false;
		}
		if(util.isNull($("#endTime").val(),1) == 0){
			$("body").message({type:'warning',content:'请填写截止日期'});
			return false;
		}
		return {
			startTime:util.formatLong($("#startTime").val()+" 00:00:00"),
			endTime:util.formatLong($("#endTime").val()+" 23:59:59"),
			productId:$('#productId').attr('data'),
			clientId:$('#clientId').attr('data'),
			cargoCode:$('#cargoCode').val(),
			goodsCode:$('#goodsCode').val(),
			showVir:$('#showVir').attr('data'),//1主库2主副库3副库
			showVirTime:$('#showVirTime').attr('data'),//1原始时间2转账时间
			isFinish:$('#isFinish').val(),//1显示全部0显示未清盘
			sStartTime:$("#startTime").val(),
			sEndTime:$("#endTime").val()
		}
	};
	
	var initTable = function(){
		config.load();
		var columns=[ {title:"货主",render: function(item){
				return "<a href='javascript:void(0)' onclick='CargoLZ.openCargoLZ("+ item.id
				+ ")' >"+ item.clientName + "</a>";}
		},{title:"货批号",name:"cargoCode"},{title:"货品",name:"productName"
		},{title:"进货船",render: function(item){ return util.isNull(item.shipName)+"("+util.isNull(item.shipRefName)+")";}
		},{title:"到港日期",name:"arrivalTime"
		},{title:"入港确认单",render: function(item){ return item.connectNo?item.connectNo.replace("No.",""):"";}
		},{title:"入港联系单",render: function(item){ return item.planNo?item.planNo.replace("No.",""):"";}
		},{title:"入库损耗单",render: function(item){ return item.inboundNo?item.inboundNo.replace("No.",""):"";}
		},{title:"仓储性质",render: function(item){ return getStorageType(item.storageType);}
		},{title:"商检(吨)",name:"goodsInspect",},{title:"入库(吨)",name:"goodsTotal"
		},{title:"封量(吨)",name:"goodsSave"},{title:"之前发货(吨)",name:"goodsDeliverBefore"
		},{title:"本段实发数(吨)",name:"goodsDeliverCurrent"},{title:"实发总数(吨)",name:"goodsCurrent"
		},{title:"结存量(吨)",name:"goodsLeft"}];
		
		if($('[data-role="cargoListGrid"]').getGrid()!=null)
			$('[data-role="cargoListGrid"]').getGrid().destory();
		/*解决id冲突的问题*/
		$('[data-role="cargoListGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			searchCondition:getSearchCondition(),
			isShowPages : true,
			url :config.getDomain()+"/statistics/cargoList",
			callback:function(){
				config.unload();
				$('[data-role="cargoListGrid"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
			}
		});
	};
	
	var getStorageType = function(type){
		switch (type) {
		case '1':
			return "一般";
		case '2':
			return "保税临租";
		case '3':
			return "包罐";
		case '4':
			return "临租";
		case '5':
			return "通过";
		default:
			return "";
		}
	};
	
	var dayStatics = function(){
		$.get(config.getResource()+"/pages/statistics/cargo/day_statics.jsp").done(function(data){
			var dialog = $(data);
			util.initDatePicker(dialog);
			dialog.modal({ keyboard : true });
			dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
			dialog.find("#time").on("changeDate",function(ev){
		    	dialog.find('[data-role="dayGrid"]').getGrid().search({time:dialog.find("#time").val()});
			});
			dialog.find('[data-role="dayGrid"]').grid({
				identity : 'id',
				columns : [{title : "日期", name : "time"},{title : "操作",render: function(item){
							return "<a href='"+config.getDomain()+"/"+item.url+"'  class='btn btn-xs blue'><span class='fa fa-arrow-down ' title='导出'></span></a>";
					}}],
				isShowIndexCol : false,
				isShowPages : false,
				autoLoad:true,
				url : config.getDomain()+"/statistics/getDayStatics?type=2"
			});
			
			
		});
	};
	
/******************************************/
	var exportExcel = function(type){
		if(util.isNull($("#productId").attr("data"),1) == 0 && type == 2){
			$("body").message({ type: 'warning', content: '请输入一个货品' });
			return;
		}
			var url = config.getDomain()+"/statistics/exportCargoListExcel?type="+type+"&name="+util.currentTime() 
			+"&"+util.getStrSearchCondition(getSearchCondition());
			console.log(url);
			window.open(url);
			
		
		
	};
	return {
		init:init,
		exportExcel:exportExcel,
		dayStatics:dayStatics
	};
	
	
	
}();