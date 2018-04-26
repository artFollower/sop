/**
 * 年度管道运输汇总表
 */

var YearPipe = function(){
	
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
		initView();
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
		}else{
			initView();
		}
	};
	
 function initView(){
	 if(util.isNull($("#date").val(),1)!=0){
		 $.ajax({
				type:'post',
				url:config.getDomain()+"/report/yearpipe",
				dataType:'json',
				data:{statisYear:$("#date").val()},
				success:function(data){
					util.ajaxResult(data,'获取',function(ndata){
						if(ndata){
							var html='';
							var a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0,k=0,j=0;
							var itemmsg='';
						for(var i=0;i<12;i++){
							itemmsg=(i+1)<10?('0'+(i+1)):(i+1);
							html+='<tr>'+
							'<td>'+itemmsg+'月'+'</td>'+
							'<td>'+util.toDecimal3(ndata[0][itemmsg],true)+'</td>'+
							'<td>'+util.toDecimal3(ndata[1][itemmsg],true)+'</td>'+
							'<td>'+util.toDecimal3(ndata[2][itemmsg],true)+'</td>'+
							'<td>'+util.toDecimal3(ndata[3][itemmsg],true)+'</td>'+
							'<td>'+util.toDecimal3(ndata[4][itemmsg],true)+'</td>'+
							'<td>'+util.toDecimal3(ndata[5][itemmsg],true)+'</td>'+
							'<td>'+util.toDecimal3(ndata[6][itemmsg],true)+'</td>'+
							'<td>'+util.toDecimal3(ndata[7][itemmsg],true)+'</td>'+
							'<td>'+util.toDecimal3(ndata[8][itemmsg],true)+'</td>'+
							'<td>'+util.toDecimal3(ndata[9][itemmsg],true)+'</td>'+
							'</tr>';
							a=util.FloatAdd(a,ndata[0][itemmsg]);
							b=util.FloatAdd(b,ndata[1][itemmsg]);
							c=util.FloatAdd(c,ndata[2][itemmsg]);
							d=util.FloatAdd(d,ndata[3][itemmsg]);
							e=util.FloatAdd(e,ndata[4][itemmsg]);
							f=util.FloatAdd(f,ndata[5][itemmsg]);
							g=util.FloatAdd(g,ndata[6][itemmsg]);
							h=util.FloatAdd(h,ndata[7][itemmsg]);
							k=util.FloatAdd(k,ndata[8][itemmsg]);
							j=util.FloatAdd(j,ndata[9][itemmsg]);
						}
						html+='<tr>'+
						'<td>'+'总计'+'</td>'+
						'<td>'+util.toDecimal3(a,true)+'</td>'+
						'<td>'+util.toDecimal3(b,true)+'</td>'+
						'<td>'+util.toDecimal3(c,true)+'</td>'+
						'<td>'+util.toDecimal3(d,true)+'</td>'+
						'<td>'+util.toDecimal3(e,true)+'</td>'+
						'<td>'+util.toDecimal3(f,true)+'</td>'+
						'<td>'+util.toDecimal3(g,true)+'</td>'+
						'<td>'+util.toDecimal3(h,true)+'</td>'+
						'<td>'+util.toDecimal3(k,true)+'</td>'+
						'<td>'+util.toDecimal3(j,true)+'</td>'+
						'</tr>';
						$('#yearPipeBody').empty().append(html);
						}
					},true);
					
					
				}
				});
		}else{
			$('body').message({
				type:'warning',
				content:'请选择年份'
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