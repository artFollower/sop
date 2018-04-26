/**
 * 长江石化管道运输通过明细表
 */

var PipeDetail = function(){
	/**
	 * 初始化查询条件
	 */
	var initCondition = function(){
		util.initDatePicker();
		$("#startTime").val(util.currentTime(0));
		$("#endTime").val(util.currentTime(0));
		
	};
	/**
	 * 表格生成函数
	 */
	var buildTab = function(){
		$.ajax({
			type : "post",
			url : config.getDomain()+"/report/list",
			data : {
				"module": 3,
				"startTime": $("#startTime").val(),
				"endTime": $("#endTime").val()
			},
			dataType : "json",
			success : function(data){
				util.ajaxResult(data,'获取数据',function(ndata){
					if(ndata&&ndata.length>0){
						var arrivalDate,startDate,startTime,endDate,endTime;
						var itemMsg;
						var str = "";
						for(var i=0,len=ndata.length;i<len;i++){
							itemMsg=ndata[i];
							if(itemMsg.startTime&&itemMsg.startTime.length==19){
								arrivalDate=itemMsg.startTime.substring(5,10);
								startDate=itemMsg.startTime.substring(8,10);
								startTime=itemMsg.startTime.substring(11,16);
							}else{
								arrivalDate='';
								startDate='';
								startTime='';
							}
							if(itemMsg.leaveTime&&itemMsg.leaveTime.length==19){
								endDate=itemMsg.leaveTime.substring(8,10);
								endTime=itemMsg.leaveTime.substring(11,16);
							}else{
								endDate='';
								endTime='';
							}
							str += "<tr><td class='tabStyle'>"+(i+1)+"</td><td class='tabStyle'>"+arrivalDate+"</td><td class='tabStyle'>"+startDate+
						       "</td><td class='tabStyle'>"+startTime+"</td><td class='tabStyle'>"+endDate+
						       "</td><td class='tabStyle'>"+endTime+"</td><td class='tabStyle'>"+util.isNull(itemMsg.berthName)+
						       "</td><td class='tabStyle'></td><td class='tabStyle'>"+util.isNull(itemMsg.clientName)+
						       "</td><td class='tabStyle'>"+util.isNull(itemMsg.productName)+"</td><td class='tabStyle'>"+util.isNull(itemMsg.goodsPlan)+
						       "</td><td class='tabStyle'>"+util.isNull(itemMsg.shipName)+"</td><td class='tabStyle'>"+util.isNull(itemMsg.description)+
						       "</td></tr>";
						}
						$("#pipeDetailBody").empty().append(str);
					}
				},true);
			}
		});
	};
	
	/**
	 * 查询按钮函数
	 */
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
	
	return {
		init:function(){
			//初始化查询条件
			initCondition();
		},
		search:function(){
			search();
		}
	}
}();