/**
 * 年度泊位利用率
 */

var YearBerth = function(){
	
	/**
	 * 初始化查询条件
	 */
	var initCondition = function(){
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
		 
		$("#date").val(getThisYear());
		
		util.urlHandleTypeahead("/product/select",$('#productName')) ;
	};
	
	/**
	 * 获取当年年份
	 */
	var getThisYear = function(){
		var date = new Date();
		var year = date.getFullYear();
		return year;
	};
	
	/**
	 * 查询按钮点击事件
	 */
	var queryResult = function(){
		var statisMonth = $("#date").val();
		if(statisMonth == null || statisMonth == "")
		{
			 $("body").message({
				type : 'warning',     
				content : '日期不能为空！'
			});
			 
			return;
		}
	};
	
	/**
	 * 导出Excel
	 */
	var exportYearBerth = function(){
		
	};
	
	return {
		init:function(){
			//初始化查询条件
			initCondition();
		},
		queryResult:function(){
			queryResult();
		},
		exportYearBerth:function(){
			exportYearBerth();
		}
	}
}();