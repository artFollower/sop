//泵棚周转量
var PumpShedRotation =function(){
	
	function init(){
		initSearchCondition();
		initTable();
	};
	
	function initSearchCondition(){
		util.initMonthPicker();
		$("#startTime,#endTime").datepicker('setDate',util.currentTime(0));
		
		$("#pumpSRSearch").click(function(){
			if(validateCondition()){
				initTable();		
			}
		});
	};
	
	function getSearchCondition(){
		return {
			startTime:$("#startTime").val(),
			endTime:$("#endTime").val(),
			module:16
		}
	};
	
	function initTable(){
		var title=['船发作业','转输作业','车发作业','打循环作业','倒罐作业','总计'];
		if(validateCondition()){
			$.ajax({
				type:'post',
				url:config.getDomain()+'/report/list',
				dataType:'json',
				data:getSearchCondition(),
				success:function(data){
					util.ajaxResult(data,"获取信息",function(ndata){
						if(ndata&&ndata.length>0){
							var html='';
							var a=0,b=0,c=0,d=0,e=0,f=0,g=0,item=0;
							for(var i=0;i<5;i++){
								var a1=0,b1=0,c1=0,d1=0,e1=0,f1=0,g1=0;
								a1=ndata[i].actNum;
								b1=ndata[5+i].actNum;
								c1=ndata[10+i].actNum;
								d1=ndata[15+i].actNum;
								e1=ndata[20+i].actNum;
								f1=ndata[25+i].actNum;
								g1=ndata[30+i].actNum;
								a=util.FloatAdd(a,a1);
								b=util.FloatAdd(b,b1);
								c=util.FloatAdd(c,c1);
								d=util.FloatAdd(d,d1);
								e=util.FloatAdd(e,e1);
								f=util.FloatAdd(f,f1);
								g=util.FloatAdd(g,g1);
								html+='<tr><td>'+title[i]+'</td>'+
								'<td>'+a1+'</td>'+
								'<td>'+b1+'</td>'+
								'<td>'+c1+'</td>'+
								'<td>'+d1+'</td>'+
								'<td>'+e1+'</td>'+
								'<td>'+f1+'</td>'+
								'<td>'+g1+'</td>'+
								'<td>'+util.FloatAdd(util.FloatAdd(util.FloatAdd(a1,b1),util.FloatAdd(c1,d1)),util.FloatAdd(util.FloatAdd(e1,f1),g1))+'</td></tr>';
							}
							html+='<tr><td>'+title[5]+'</td>'+
							'<td>'+a+'</td>'+
							'<td>'+b+'</td>'+
							'<td>'+c+'</td>'+
							'<td>'+d+'</td>'+
							'<td>'+e+'</td>'+
							'<td>'+f+'</td>'+
							'<td>'+g+'</td>'+
							'<td>'+util.FloatAdd(util.FloatAdd(util.FloatAdd(a,b),util.FloatAdd(c,d)),util.FloatAdd(util.FloatAdd(e,f),g))+'</td></tr>';
							
							$("#pumpShedRotationBody").empty().append(html);
						}
					},true);
				}
			});
		
		}
	};
	function validateCondition(){
		if(util.isNull($("#startTime").val())==''){
			$('body').message({type:"warning",content:'请填写开始时间'});
			return false;
		}else if(util.isNull($("#endTime").val())==''){
			$('body').message({type:"warning",content:'请填写结束时间'});
			return false;
		}else
			return true;
		
	}
	
	return{
		init:init
	}
}();