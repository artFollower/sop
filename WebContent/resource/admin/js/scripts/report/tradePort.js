/**
 * 外贸进出港统计表
 */

var TradePort = function(){
	
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
	 * 构建报表
	 */
	var buildTab = function(){
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain()+"/report/list",
			data : {
				"module": 7,
				"startTime": $("#startTime").val(),
				"endTime": $("#endTime").val()
			},
			dataType : "json",
			success : function(resp){
				var str = "";
				$("#tradePortBody").empty();
				
				if(resp==null || resp.length == 0)
				{
					return;
				}
				else
				{
					if(resp.data != null && resp.data.length > 0)
					{
						for(var i=0;i<resp.data.length;i++)
						{
							str += "<tr><td>"+(resp.data[i].productName==null?"":resp.data[i].productName)+"</td><td>"+(resp.data[i].berthName==null?"":resp.data[i].berthName)+
							       "</td><td>"+(resp.data[i].inOutType==null?"":resp.data[i].inOutType)+"</td><td>"+(resp.data[i].deliverNum==null?"":resp.data[i].deliverNum)+
							       "</td><td>"+(resp.data[i].shipNum==null?0:resp.data[i].shipNum)+"</td></tr>";
						}
						
						$("#tradePortBody").append(str);
					}
				}
				config.unload();
			}
		});
	};
	
	/**
	 * 查询按钮函数
	 */
	var queryResult = function(){
		var statisMonth = $("#startTime").val();
		if(statisMonth == null || statisMonth == "")
		{
			 $("body").message({
				type : 'warning',     
				content : '日期不能为空！'
			});
			 
			return;
		}
		
		buildTab();
	};
	
	return {
		init:function(){
			//初始化查询条件
			initCondition();
		},
		queryResult:function(){
			queryResult();
		}
	}
}();