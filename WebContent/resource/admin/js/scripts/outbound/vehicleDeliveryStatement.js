var vehicleDeliveryStatement = function(){
	function initParkSelect(){
		util.urlHandleTypeahead("/baseController/getParkName",$("#parkId")) ;
	}
	function initTankSelect(){
		util.urlHandleTypeahead("/tank/list",$("#tankId"),"code") ;
	}
	function initProductSelect(){
		$.ajax({
			type : "post",
			url : config.getDomain()+"/product/select",
			dataType : "json",
			success : function(data) {
				$("#productId").typeahead({
					source: function(query, process) {
						var results = _.map(data.data, function (product) {
							return product.name;
						});
						process(results);
					},
					noselect:function(query){
						var client = _.find(data.data, function (p) {
							return p.name == query;
						});
						if(client==null){
							$("#productId").attr("data","");
							$("#productId").val("");
						}else{
							$("#productId").attr("data",client.id) ;
						}
					}
				});
			}
		});
	}
	function initDate(obj){
		$(obj).find('.date-picker').datepicker({
		    rtl: Metronic.isRTL(),
		    language: "zh-CN",
		    orientation: "left",
		    format: "yyyy-mm-dd",
		    showInputs:true,
	        disableMousewheel:false,
		    autoclose: true
		});
	}
	function initDateForMonth(obj){
		$(obj).find('.date-picker').datepicker({
			rtl: Metronic.isRTL(),
		    language: "zh-CN",
		    orientation: "left",
		    format: "yyyy-mm",
		    showInputs:true,
		    startView:"year",
		    minViewMode:"months",
            disableMousewheel:false,
		    autoclose: true
		});
	}
	//列表页
	function initWeighDailyStatement(){
		initTankSelect();
		initProductSelect();
		
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
		
		initDate($(".parkDailyStatement")) ;
		$(".parkDailyStatement").find('.date-picker').datepicker({
			rtl: Metronic.isRTL(),
			language: "zh-CN",
			orientation: "left",
			format: "yyyy-mm-dd",
			showInputs:true,
			disableMousewheel:false,
			autoclose: true
		});
		var columns = [  {
			title : "罐号",
			name : "code"
		},{
			title : "发货口",
			name : "inPort"
		},{
			title : "发货量",
			name : "deliveryNum"
		}];
		
		/*解决id冲突的问题*/
		$('[data-role="parkDailyStatement"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain()+"/vehicleDeliveryStatement/queryWeighDailyStatement"
		});
		
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$(".search").click(function() {
			var vehicleDeliveryStatementDto = {};
			vehicleDeliveryStatementDto.date = $("#date").val() ;
			vehicleDeliveryStatementDto.productId = util.isNull($("#productId").attr("data"),1);
			vehicleDeliveryStatementDto.tankId= util.isNull($("#tankId").attr("data"),1);
			$('[data-role="parkDailyStatement"]').getGrid().search(vehicleDeliveryStatementDto);
		});
	}
	//列表页
	function initParkDailyStatement(){
		initParkSelect() ;
		initDate($(".parkDailyStatement")) ;
		$(".parkDailyStatement").find('.date-picker').datepicker({
		    rtl: Metronic.isRTL(),
		    language: "zh-CN",
		    orientation: "left",
		    format: "yyyy-mm-dd",
		    showInputs:true,
	        disableMousewheel:false,
		    autoclose: true
		});
		var columns = [  {
			title : "车发起止时间",
			name : "meterStartTime",
			render: function(item, name, index){
				return item.meterStartTime+"<br>"+item.meterEndTime;
			}
		},{
			title : "车位",
			name : "parkName"
		},{
			title : "流水号",
			name : "serialNo"
		},{
			title : "类型",
			name : "type",
			render: function(item, name, index){
				return item.type==1?"手动":"自动";
			}
		},{
			title : "小表读数起（千克）",
			name : "meterStart"
		},{
			title : "小表读数止（千克）",
			name : "meterEnd"
		}, {
			title : "小表实发（千克）",
			name : "meterActualNum"
		},{
			title : "预置量（千克）",
			name : "reserveNum"
		},{
			title : "差异（千克）",
			name : "diffNum"
		}];
		
		/*解决id冲突的问题*/
		$('[data-role="parkDailyStatement"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain()+"/vehicleDeliveryStatement/queryParkDailyStatement"
		});
		
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$(".search").click(function() {
			var vehicleDeliveryStatementDto = {};
			vehicleDeliveryStatementDto.parkId= util.isNull($("#parkId").attr("data"),1);
			vehicleDeliveryStatementDto.date = $("#date").val() ;
	       $('[data-role="parkDailyStatement"]').getGrid().search(vehicleDeliveryStatementDto);
		});
	}
	//列表页
	function initProductMonthlyStatement(){
		initProductSelect() ;
		initDateForMonth(".productMonthlyStatement") ;
		var columns = [  {
			title : "货品",
			name : "productName"
		},{
			title : "小表实发数（千克）",
			name : "meterActualNum"
		}];
		/*解决id冲突的问题*/
		$('[data-role="productMonthlyStatement"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain()+"/vehicleDeliveryStatement/queryProductMonthlyStatement"
		});
		
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$(".search").click(function() {
			var vehicleDeliveryStatementDto = {};
			vehicleDeliveryStatementDto.productId= util.isNull($("#productId").attr("data"),1);
			vehicleDeliveryStatementDto.date = $("#date").val() ;
			$('[data-role="productMonthlyStatement"]').getGrid().search(vehicleDeliveryStatementDto);
		});
	}
	//列表页
	function initVehicleHistoryCumulantStatement(){
		initParkSelect() ;
		initDate($(".vehicleHistoryCumulantStatement")) ;
		var columns = [  {
			title : "车位",
			name : "parkName"
		},{
			title : "时间",
			name : "meterStartTime"
		},{
			title : "累计量",
			name : "meterActualNum"
		}];
		/*解决id冲突的问题*/
		$('[data-role="vehicleHistoryCumulantStatement"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain()+"/vehicleDeliveryStatement/queryVehicleHistoryCumulantStatement"
		});
		
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$(".search").click(function() {
			var vehicleDeliveryStatementDto = {};
			vehicleDeliveryStatementDto.parkId= util.isNull($("#parkId").attr("data"),1);
			vehicleDeliveryStatementDto.date = $("#date").val() ;
			$('[data-role="vehicleHistoryCumulantStatement"]').getGrid().search(vehicleDeliveryStatementDto);
		});
	}
	//列表页
	function initVehicleMonthlyStatement(){
		initParkSelect() ;
		initDateForMonth($(".vehicleMonthlyStatement")) ;
		var columns = [  {
			title : "日期",
			name : "meterStartTime"
		},{
			title : "小表读数起（千克）",
			name : "meterStart"
		},{
			title : "小表读数止（千克）",
			name : "meterEnd"
		},{
			title : "小表实发数（千克）",
			name : "meterActualNum"
		},{
			title : "预置量（千克）",
			name : "reserveNum"
		}, {
			title : "差异",
			name : "diffNum"
		}];
		/*解决id冲突的问题*/
		$('[data-role="vehicleMonthlyStatement"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain()+"/vehicleDeliveryStatement/queryVehicleMonthlyStatement"
		});
		
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$(".search").click(function() {
			var vehicleDeliveryStatementDto = {};
			vehicleDeliveryStatementDto.parkId= util.isNull($("#parkId").attr("data"),1);
			vehicleDeliveryStatementDto.date = $("#date").val() ;
			$('[data-role="vehicleMonthlyStatement"]').getGrid().search(vehicleDeliveryStatementDto);
		});
	}
	//列表页
	function initVehicleMonthlyTotalStatement(){
		initParkSelect() ;
		initDateForMonth($(".vehicleMonthlyTotalStatement")) ;
		var columns = [  {
			title : "车位",
			name : "parkName"
		},{
			title : "货品",
			name : "productName"
		},{
			title : "流量计小表读数",
			name : "meterActualNum"
		}];
		
		/*解决id冲突的问题*/
		$('[data-role="vehicleMonthlyTotalStatement"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : true,
			isShowPages : true,
			url : config.getDomain()+"/vehicleDeliveryStatement/queryVehicleMonthlyTotalStatement"
		});
		
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$(".search").click(function() {
			var vehicleDeliveryStatementDto = {};
			vehicleDeliveryStatementDto.parkId= util.isNull($("#parkId").attr("data"),1);
			vehicleDeliveryStatementDto.date = $("#date").val() ;
			$('[data-role="vehicleMonthlyTotalStatement"]').getGrid().search(vehicleDeliveryStatementDto);
		});
	}
	return {
		initWeighDailyStatement:function(){
			initWeighDailyStatement() ;
		},
		initParkDailyStatement:function(){
			initParkDailyStatement() ;
		},
		initProductMonthlyStatement:function(){
			initProductMonthlyStatement() ;
		},
		initVehicleHistoryCumulantStatement:function(){
			initVehicleHistoryCumulantStatement() ;
		},
		initVehicleMonthlyStatement:function(){
			initVehicleMonthlyStatement() ;
		},
		initVehicleMonthlyTotalStatement:function(){
			initVehicleMonthlyTotalStatement() ;
		}
	};
}() ;