/**
 * 吞吐量统计表
 */

var Throughput = function(){
	var initCondition = function(){
		util.initMonthPicker();
		$("#startTime,#endTime").datepicker("setDate",util.currentTime(0).substring(0,7));
		$("#startTime,#endTime").change(function(){
			$("#throughtMsg").val("").removeAttr('data');
			$("#save").attr('disabled','disabled');
		})
		$("#save").click(function(){
			$this=$(this);
			if(config.validateForm($(".msgDiv"))){
				config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+'/report/addorupdateThroughMsg',
				data:{
					id:$this.attr('data'),
					time:util.formatLong($("#startTime").val()+'-01 00:00:00'),
					content:$("#throughtMsg").val()
				},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,'保存',function(ndata,nmap){
						$this.attr('data',nmap.id);
					});
				}
			});
			}
		});
		buildTab();
	};
	var buildTab = function(){
		$("#monthTh").text('01-'+$("#startTime").val().substring(5,7)+'月累计');
		$("#thisYearTh").text($("#startTime").val().substring(0,4)+'年吞吐量');
		$("#lastYearTh").text((parseInt($("#startTime").val().substring(0,4))-1)+'年吞吐量');
		$("#thisYearCountTh").text($("#startTime").val().substring(0,4)+'年船舶数');
		$("#throughtMsg").val("").removeAttr('data');
		$("#save").removeAttr('disabled');
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain()+"/report/list",
			data : {
				"module": 5,
				"startTime": $("#startTime").val()+"-01",
				"endTime":util.getEndDayOfMonth($("#endTime").val(),1)
			},
			dataType : "json",
			success : function(data){
				var str ='';
				$("#throughputBody").empty();
				util.ajaxResult(data,'获取吞吐量',function(ndata,nmap){
					if(nmap&&nmap.content){
						$("#throughtMsg").val(util.isNull(nmap.content));
						$("#save").attr('data',nmap.id);
					}
					if(ndata&&ndata.length>0){
						var map=null;
						for(var i=0,len=ndata.length;i<len;i++){
							map=ndata[i];
						str+='<tr><td>'+map.tab+'</td><td>'+map.totalNum+'</td>'+
						'<td>'+map.lastYTotalNum+'</td><td>'+parseFloat(util.FloatMul(map.numRate,100))+'</td>'+
						'<td>'+map.count+'</td><td>'+map.lastYCount+'</td>'+
						'<td>'+map.countRate+'</td><td>'+map.accLastMNum+'</td>'+
						'<td>'+map.accLastMCount+'</td><td>'+map.accTotalNum+'</td>'+
						'<td>'+map.accLastYTotalNum+'</td><td>'+parseFloat(util.FloatMul(map.accNumRate,100))+'</td>'+
						'<td>'+map.accCount+'</td><tr>';
						}
						$("#throughputBody").append(str);	
					}
				},true);
			}
		});
	};
	var queryResult = function(){
		if(util.isNull($("#startTime").val(),1)==0){
			$('body').message({
				type:'warning',
				content:'请选择开始月份'
			});
			return;
		}
		if(util.isNull($("#endTime").val(),1)==0){
			$('body').message({
				type:'warning',
				content:'请选择截止月份'
			});
			return;
		}
		buildTab();
	};
	
	return {
		init:function(){
			initCondition();
		},
		queryResult:function(){
			queryResult();
		}
	}
}();