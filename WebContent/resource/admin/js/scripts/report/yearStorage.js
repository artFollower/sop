/**
 * 年度仓储汇总表
 */

var YearStorage = function(){
	
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
		
		util.urlHandleTypeahead("/product/select",$('#productName')) ;
		 
		$("#date").val(getThisYear());
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
		
		buildTab();
	};
	
	/**
	 * 形成表格
	 */
	var buildTab = function(){
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain()+"/report/list",
			data : {
				"module": 4,
				"statisYear": $("#date").val(),
				"productId": ($("#productName").attr("data")==null?0:$("#productName").attr("data"))
			},
			dataType : "json",
			success : function(resp){
				var str = "";
				$("#yearStorageBody").empty();
				
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
							str += "<tr><td>"+(resp.data[i].statisDate==null?"":resp.data[i].statisDate)+"</td><td>"+(resp.data[i].productName==null?"":resp.data[i].productName)+
							       "</td><td>"+(resp.data[i].goodsTotal==null?"0":resp.data[i].goodsTotal)+"</td><td>"+resp.data[i].goodsInPass+
							       "</td><td>"+(resp.data[i].goodsOut==null?"":resp.data[i].goodsOut)+"</td><td>"+(resp.data[i].goodsCurrent==null?"":resp.data[i].goodsCurrent)+
							       "</td></tr>";
						}
						
						$("#yearStorageBody").append(str);
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
			buildTab();
		},
		queryResult:function(){
			queryResult();
		}
	}
}();