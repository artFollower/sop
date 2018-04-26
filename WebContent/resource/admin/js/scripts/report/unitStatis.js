/**
 * 通过单位统计表
 */

var UnitStatis = function(){
	
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
		
		dateResult();
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
	 * 日期限定以及改变报表日期
	 */
	var dateResult = function(){
		var statisMonth = $("#startTime").val();
		var year = statisMonth.substring(0,4);
		if(statisMonth == null || statisMonth == "")
		{
			 $("body").message({
				type : 'warning',     
				content : '日期不能为空！'
			});
			 
			return;
		}
		
		$("#thisYear").empty();
		$("#lastYear").empty();
		
		$("#thisYear").append(year+"年吞吐量");
		$("#lastYear").append(parseInt(year)-1+"年吞吐量");
	};
	
	/**
	 * 查询按钮点击事件
	 */
	var queryResult = function(){
		dateResult();
		
		buildTab();
	};
	
	/**
	 * 构建表格
	 */
	var buildTab = function(){
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain()+"/report/list",
			data : {
				"module": 10,
				"startTime": $("#startTime").val(),
				"endTime":$("#endTime").val()
			},
			dataType : "json",
			success : function(resp){
				var str = "";
				$("#unitStatisBody").empty();
				
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
							str += "<tr><td>"+(resp.data[i].clientName)+"</td><td>"+(resp.data[i].productName)+
							       "</td><td>"+(resp.data[i].tankTotal)+"</td><td>"+(resp.data[i].goodsTotal)+
							       "</td><td>&nbsp;</td><td>&nbsp;</td><td></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
						}
						
						$("#unitStatisBody").append(str);
					}
				}
				config.unload();
			}
		});
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