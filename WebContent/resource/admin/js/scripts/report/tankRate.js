/**
 * 储罐周转率
 */

var TankRate = function(){
	
	/**
	 * 初始化查询条件
	 */
	var initCondition = function(){
		util.initDatePicker();
		$("#startTime").val(util.currentTime(0));
		$("#endTime").val(util.currentTime(0));
//		StorageState.initTankList();
	};
	
	
	var search = function(){
		if(util.isNull($("#startTime").val(),1)==0){
			$('body').message({
				type:'warning',
				content:'请选择起始日期'
			});
			return;
		}
		if(util.isNull($("#endTime").val(),1)==0){
			$('body').message({
				type:'warning',
				content:'请选择止计日期'
			});
			return;
		}
		buildTab();
	};
	function buildTab(){
		
		$.ajax({
			url:config.getDomain()+'/report/list',
			type:'post',
			dataType:'json',
			data:{
				startTime:$("#startTime").val(),
				endTime:$("#endTime").val(),
//				tankKeys:StorageState.getNotCheckedTankKeys(),
				module:4
			},
			success:function(data){
			util.ajaxResult(data,'获取列表数据',function(ndata){
				if(ndata&&ndata.length>0){
					var html='';
					for(var i=0,len=ndata.length;i<len;i++){
						if(i!=8){
						html+='<tr><td>'+ndata[i].productName+'</td>'+
						        '<td>'+ndata[i].tankType+'</td>'+
						        '<td>'+ndata[i].maxNum+'</td>'+
						        '<td>'+ndata[i].inboundNum+'</td>'+
						        '<td>'+ndata[i].totalInboundNum+'</td>'+
						        '<td>'+util.toDecimal2(ndata[i].turnRate,true)+'</td>'+
						        '<td>'+util.toDecimal2(ndata[i].totalTurnRate,true)+'</td></tr>'
						}
					}
					$("#tankRateBody").empty().append(html);
				}
			},true);
			}
		});
	}
	
	
	
	return {
		init:function(){
			//初始化查询条件
			initCondition();
			
		},
		search:search
	}
}();