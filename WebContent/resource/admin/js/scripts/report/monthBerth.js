/**
 * 年度泊位利用率
 */

var MonthBerth = function(){
	
	/**
	 * 初始化查询条件
	 */
	var initCondition = function(){
		//年份控件
		util.initMonthPicker();
		$("#startTime,#endTime").datepicker('setDate',util.currentTime(0));
		
		initView();
	};
	
	/**
	 * 查询按钮点击事件
	 */
	var queryResult = function(){
		if(util.isNull($("#startTime").val(),1)==0||util.isNull($("#endTime").val(),1)==0)
		{
			 $("body").message({
				type : 'warning',     
				content : '时间区间不能为空！'
			});
			return;
		}else{
			initView();
		}
	};
	
	function initView(){
		if(util.isNull($("#startTime").val(),1)!=0&&util.isNull($("#endTime").val(),1)!=0){
			var params={};
			params.startTime=$("#startTime").val();
			params.endTime=$("#endTime").val();
			$.ajax({
				type:'post',
				url:config.getDomain()+"/report/monthberth",
				dataType:'json',
				data:params,
				success:function(data){
					util.ajaxResult(data,'获取',function(ndata){
						if(ndata&&ndata.length==1){
							var html="";
							var a=0;
							var b=0;
							for(var i=1;i<8;i++){
								a=0;b=0;c=0;d=0;e=0;
								if(ndata[0][i+'0#普通柴油'])
								a=ndata[0][i+'0#普通柴油'].replace('%','');
								if(ndata[0][i+'0#车用柴油V'])
								b=ndata[0][i+'0#车用柴油V'].replace('%','');
								if(ndata[0][i+'丙烷/丁烷'])
									c=ndata[0][i+'丙烷/丁烷'].replace('%','');
								if(ndata[0][i+'丙烷'])
									d=ndata[0][i+'丙烷'].replace('%','');
								if(ndata[0][i+'丁烷'])
									e=ndata[0][i+'丁烷'].replace('%','');
								html+='<tr>'+
								'<td>'+i+'#泊位'+'</td>'+
								'<td>'+(util.isNull(ndata[0][i+'乙二醇'],1)==0?'0%':util.isNull(ndata[0][i+'乙二醇']))+'</td>'+
								'<td>'+(util.isNull(ndata[0][i+'甲醇'],1)==0?'0%':util.isNull(ndata[0][i+'甲醇']))+'</td>'+
								'<td>'+(util.isNull(ndata[0][i+'苯'],1)==0?'0%':util.isNull(ndata[0][i+'苯']))+'</td>'+
								'<td>'+(util.isNull(ndata[0][i+'正构烷烃'],1)==0?'0%':util.isNull(ndata[0][i+'正构烷烃']))+'</td>'+
								'<td>'+(util.isNull(ndata[0][i+'直链烷基苯'],1)==0?'0%':util.isNull(ndata[0][i+'直链烷基苯']))+'</td>'+
								'<td>'+(parseFloat(util.FloatAdd(a,b))==0?'0%':(util.FloatAdd(a,b,2)+'%'))+'</td>'+
								/*'<td>'+(util.isNull(ndata[0][i+'93#车用汽油V'],1)==0?'0%':util.isNull(ndata[0][i+'93#车用汽油V']))+'</td>'+*/
								'<td>'+(util.isNull(ndata[0][i+'92#车用汽油V'],1)==0?'0%':util.isNull(ndata[0][i+'92#车用汽油V']))+'</td>'+
								'<td>'+(util.isNull(util.FloatAdd(util.FloatAdd(c,d,2),e,2),1)==0?'0%':(util.FloatAdd(util.FloatAdd(c,d,2),e,2)+'%'))+'</td>'+
								'<td>'+(util.isNull(ndata[0][i+'VCM'],1)==0?'0%':util.isNull(ndata[0][i+'VCM']))+'</td>'+
								'<td>'+(util.isNull(ndata[0][i+'BP厂'],1)==0?'0%':util.isNull(ndata[0][i+'BP厂']))+'</td>'+
								'<td>'+(util.isNull(ndata[0][i+'华东厂'],1)==0?'0%':util.isNull(ndata[0][i+'华东厂']))+'</td>'+
								'<td>'+(util.isNull(ndata[0][i],1)==0?'0%':util.isNull(ndata[0][i]))+'</td>'+
								'</tr>'
							}
							$("#monthBerthBody").empty().append(html);
						}
						
					},true);
					
				}
			});
			
		}else{
			$('body').message({
				type:'warning',
				content:'请选择区间'
			});
		}
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