/**
 * 生产指标完成情况
 */

var ProductionTarget = function(){
	
	/**
	 * 初始化查询条件
	 */
	var initCondition = function(){
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
		$("#startTime").val(util.currentTime(0));
		$("#endTime").val(util.currentTime(0));
		
	};
	
	/**
	 * 获取当前月份
	 */
	var getThisMonth = function(){
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth()+1;
		var thisMonth = year + "-" + (month<10?"0"+month:month);
		
		return thisMonth;
	};
	
	/**
	 * 查询事件
	 */
	var queryResult = function(){
		
	};
	
	/**
	 * 导出Excel
	 */
	var exportProductionTarget = function(){
		
	};
	
	return {
		init:function(){
			//初始化查询条件
			initCondition();
		},
		queryResult:function(){
			queryResult();
		},
		exportProductionTarget:function(){
			exportProductionTarget();
		}
	}
}();