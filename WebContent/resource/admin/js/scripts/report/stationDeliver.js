/**
 * 装车站发货统计表
 */

var StationDeliver = function(){
	
	/**
	 * 初始化查询条件
	 */
	var initCondition = function(){
		util.initDatePicker();
		$("#startTime").val(util.currentTime(0));
		$("#endTime").val(util.currentTime(0));
	};
	
	/**
	 * 生成表格
	 */
	var buildTabTotal = function(){
		config.load();
		$.ajax({
			type : "post",
			url : config.getDomain()+"/report/list",
			data : {
				"module":9,
				"startTime": $("#startTime").val(),
				"endTime": $("#endTime").val()
			},
			dataType : "json",
			success : function(data){
			 util.ajaxResult(data,"车发数据",function(ndata){
				 if(ndata&&ndata.length>0){
				var productList=ndata[0].productNames; 
				var dataList=ndata[0].data;
				 var htmlHeadA='<tr><th class="tabStyle" rowspan="2">发货口</th>',htmlHeadB='<tr>';
				 for(var i=0,len=productList.length;i<len;i++){
					 htmlHeadA+='<th class="tabStyle" colspan="2" >'+productList[i].productName+'</th>';
					 htmlHeadB+='<th>重量</th><th>次数</th>';
					 
				 }
				 htmlHeadA+='<th class="tabStyle" colspan="2" >总计</th>';
				 htmlHeadB+='<th class="tabStyle" >重量</th><th class="tabStyle" >次数</th>';
				 htmlHeadA+='</tr>';
				 htmlHeadB+='</tr>';
				 $("#sdHead").empty().append(htmlHeadA+htmlHeadB);
				 var itemPort;
				 var totalCount=0,totalNum=0;
				 var htmlBody='';
				 var lastTr=new Array();
				 for(var i=0;i<=productList.length;i++){
					 lastTr.push({
						 num:0,
						 count:0
					 });
				 }
				 for(var j=0;j<49;j++){
					  htmlBody+='<tr>';
					  totalCount=0,totalNum=0;
					 for(var i=0,len=productList.length;i<len;i++){
						 itemPort='A'+(j<9?('0'+(j+1)):(j+1)); 
						 if(i==0){
							 if(j!=48){
								 htmlBody+='<td class="tabStyle">'+itemPort+'</td>';
							 }else{
								 htmlBody+='<td class="tabStyle">TZ</td>';
							 }
						 }
						 if(j!=48){
						 htmlBody+='<td class="tabStyle">'+dataList[i][itemPort].num+'</td><td class="tabStyle">'+dataList[i][itemPort].count+'</td>';
						 totalNum=util.FloatAdd(totalNum,dataList[i][itemPort].num,3);
						 totalCount+=dataList[i][itemPort].count;
						 lastTr[i].num=util.FloatAdd(lastTr[i].num,dataList[i][itemPort].num,3);
						 lastTr[i].count=lastTr[i].count+dataList[i][itemPort].count;
						 }else{
							 htmlBody+='<td class="tabStyle">'+dataList[i]['TZ'].num+'</td><td class="tabStyle">'+dataList[i]['TZ'].count+'</td>';
							 totalNum=util.FloatAdd(totalNum,dataList[i]['TZ'].num,3);
							 totalCount+=dataList[i]['TZ'].count;
							 lastTr[i].num=util.FloatAdd(lastTr[i].num,dataList[i]['TZ'].num,3);
							 lastTr[i].count=lastTr[i].count+dataList[i]['TZ'].count;
						 }
					 }
					 htmlBody+='<td class="tabStyle">'+parseFloat(totalNum)+'</td><td class="tabStyle">'+totalCount+'</td>';
					 lastTr[len].num=util.FloatAdd(lastTr[i].num,totalNum,3);
					 lastTr[len].count=lastTr[i].count+totalCount;
					 
					 
					  htmlBody+='</tr>';}
				 htmlBody+='<tr><td class="tabStyle">总计</td>';
				 for(var m=0,len=lastTr.length;m<len;m++){
					 htmlBody+='<td class="tabStyle">'+parseFloat(lastTr[m].num)+'</td><td class="tabStyle">'+lastTr[m].count+'</td>';
				 }
				 htmlBody+='</tr>';
				 $("#sdBody").empty().append(htmlBody);
				 } 
			 },true);	
			}
		});
	};
	/**
	 * 查询按钮事件
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
		buildTabTotal();
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