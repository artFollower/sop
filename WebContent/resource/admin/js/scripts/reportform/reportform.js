//统计报表
var ReportForm=function(){		
	
/***************/	
function init(){
	$("#reportListTab").change(function(){
		$this=$(this);
		switch($this.val()){
		case '1':
			//月仓储入库明细表
			$("#tab").empty().load(config.getResource()+ "/pages/report/monthInStorage.jsp",function(){
				MonthInStorage.init();
			});
		break;
		case '2':
			//货物进出港通过量统计表
			$("#tab").empty().load(config.getResource()+ "/pages/report/inOutPort.jsp",function(){
				InOutPort.init();
			});
		break;
		case '3':
			//储罐周转率
			$("#tab").empty().load(config.getResource()+ "/pages/report/tankRate.jsp",function(){
				TankRate.init();
			});
		break;
		case '4':
			//管道运输通过明细表
			$("#tab").empty().load(config.getResource()+ "/pages/report/pipeDetail.jsp",function(){
				PipeDetail.init();
			});
		break;
		case '5':
			//吞吐量统计表
			$("#tab").empty().load(config.getResource()+ "/pages/report/throughput.jsp",function(){
				Throughput.init();
			});
		break;
		case '6':
			//码头进出港统计表
			$("#tab").empty().load(config.getResource()+ "/pages/report/dockPort.jsp",function(){
				DockPort.init();
			});
		break;
		case '7':
			//外贸进出港统计表
			$("#tab").empty().load(config.getResource()+ "/pages/report/tradePort.jsp",function(){
				TradePort.init();
			});
		break;
		case '8':
			//库容状态统计
			$("#tab").empty().load(config.getResource()+ "/pages/report/storageState.jsp",function(){
				StorageState.init();
			});
		break;
		case '9':
			//装车站发货统计表
			$("#tab").empty().load(config.getResource()+ "/pages/report/stationDeliver.jsp",function(){
				StationDeliver.init();
			});
		break;
		case '10':
			//通过单位统计表
			$("#tab").empty().load(config.getResource()+ "/pages/report/unitStatis.jsp",function(){
				UnitStatis.init();
			});
		break;
		case '11':
			//年度管道运输汇总表
			$("#tab").empty().load(config.getResource()+ "/pages/report/yearPipe.jsp",function(){
				YearPipe.init();
			});
		break;
		case '12':
			//年度仓储汇总表
			$("#tab").empty().load(config.getResource()+ "/pages/report/yearStorage.jsp",function(){
				YearStorage.init();
			});
		break;
		case '13':
			//月度泊位利用率
			$("#tab").empty().load(config.getResource()+ "/pages/report/monthBerth.jsp",function(){
				MonthBerth.init();
			});
		break;
		case '14':
			//调度日志导出
			$("#tab").empty().load(config.getResource()+ "/pages/report/inboundBook.jsp",function(){
				InBoundBook.init();
			});
			
		break;
		case '15':
			//分流台账导出
			$("#tab").empty().load(config.getResource()+ "/pages/report/outboundBook.jsp",function(){
				OutBoundBook.init();
			});
		break;
		case '16':
			//泵棚周转量
			$("#tab").empty().load(config.getResource()+ "/pages/report/pumpShedRotation.jsp",function(){
				PumpShedRotation.init();
			});
			
		break;
		default:
		break;
		}
	});
	if(config.hasPermission('MMONTHSTORAGE')){
		$("#reportListTab").val(1);
		$("#reportListTab").change();
	}else if(config.hasPermission('MPUMPSHEDROTATION')){
		$("#reportListTab").val(16);
		$("#reportListTab").change();
}
	};
/**************/
	function exportExcel(){
		var item=$("#reportListTab").val();
		switch(item){
		case '1':
			var url = config.getDomain()+"/export/exportMonthStorage?module=1";
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.isNull($("#startTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择起始日期'
				});
				return;
			}
			
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.isNull($("#endTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择止计日期'
				});
				return;
			}
			console.log(url);
			window.open(url);
		break;
		case '2':
			//货物进出港通过量统计表
			var url = config.getDomain()+"/export/exportInOut?module=2";
			if(util.isNull($("#clientId").val(),1)!=0){
				url+="&&clientId="+$("#clientId").val()+"&&clientName="+$("#clientId>option:selected").text()
			}else{
				$('body').message({
					type:'warning',
					content:'请选择客户名称！'
				});
				return;	
			}
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.isNull($("#startTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择起始日期'
				});
				return;
			}
			
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.isNull($("#endTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择止计日期'
				});
				return;
			}
			console.log(url);
			window.open(url);
		break;
		case '3':
			//储罐周转率
			var url = config.getDomain()+"/export/exportTankTurnRate?module=3";
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.isNull($("#startTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择起始日期'
				});
				return;
			}
			
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.isNull($("#endTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择止计日期'
				});
				return;
			}
			console.log(url);
			window.open(url);
		break;
		case '4':
			//管道运输通过明细表
			var url = config.getDomain()+"/export/exportPipe?module=3";
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.isNull($("#startTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择起始日期'
				});
				return;
			}
			
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.isNull($("#endTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择止计日期'
				});
				return;
			}
			console.log(url);
			window.open(url);
			
		break;
		case '5'://吞吐量统计表
			var url=config.getDomain()+"/export/exportThroughput?module=11&&planTank=0&&name="+util.getSubTime(util.currentTime(1),1);
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.isNull($("#startTime").val()+"-01");
				
			}else {
				$('body').message({
					type:'warning',
					content:'请选择月份'
				});
				return;
			}
			
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.getEndDayOfMonth($("#endTime").val(),1);
			}else {
				$('body').message({
					type:'warning',
					content:'请选择止计日期'
				});
				return;
			}
			console.log(url);
			window.open(url);
			
			
		break;
		case '6':
			//码头进出港统计表
			var url = config.getDomain()+"/export/exportDock?planTank=0&&name="+util.getSubTime(util.currentTime(1),1);
			
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.isNull($("#startTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择起始日期'
				});
				return;
			}
			
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.isNull($("#endTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择止计日期'
				});
				return;
			}
			console.log(url);
			window.open(url);
		break;
		case '7':
			//外贸进出港统计表
			var url = config.getDomain()+"/export/exportTrade?planTank=0&&name="+util.getSubTime(util.currentTime(1),1);
			
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.isNull($("#startTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择起始日期'
				});
				return;
			}
			
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.isNull($("#endTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择止计日期'
				});
				return;
			}
			console.log(url);
			window.open(url);
		break;
		case '8':
			//库容状态统计
			$("#tab").empty().load(config.getResource()+ "/pages/report/storageState.jsp",function(){
				StorageState.init();
			});
		break;
		case '9':
			//装车站发货统计表
			var url = config.getDomain()+"/vehicleDeliveryStatement/exportExcel?type=5&&name="+util.getSubTime(util.currentTime(1),1);
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.formatLong($("#startTime").val()+" 00:00:00");
			}else{
				$("body").message({
					type:'warning',
					content:'请填写起始日期'
				});
				return false;
			}
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.formatLong($("#endTime").val()+" 23:59:59");
			}else{
				$("body").message({
					type:'warning',
					content:'请填写截止日期'
				});
				return false;
			}
			window.open(url);
		break;
		case '10':
			//外贸进出港统计表
			var url = config.getDomain()+"/export/exportUnit?module=11&&planTank=0&&name="+util.getSubTime(util.currentTime(1),1);
			
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.isNull($("#startTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择起始日期'
				});
				return;
			}
			
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.isNull($("#endTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择止计日期'
				});
				return;
			}
			console.log(url);
			window.open(url);
		break;
		case '11':
			//年度管道运输汇总表
			console.log(util.isNull($("#date").val(),1));
			if(util.isNull($("#date").val(),1)!=0){
				var url = config.getDomain()+"/report/exportyearpipe?statisYear="+$("#date").val()+"&&name="+util.getSubTime(util.currentTime(1),1);;
				console.log(url);
				window.open(url);
			}else{
				$('body').message({
					type:'warning',
					content:'请选择年份'
				});
			}
		break;
		case '12':
			//年度仓储汇总表
			if(util.isNull($("#date").val(),1)!=0)
			{
				var url = config.getDomain()+"/export/exportStorage?planTank=0&&statisMonth="+$("#date").val()+"&&name="+util.getSubTime(util.currentTime(1),1);
				window.open(url);
			}
			else
			{
				$('body').message({
					type:'warning',
					content:'请选择月份'
				});
			}
		break;
		case '13':
				var url = config.getDomain()+"/export/exportmonthberth?";
				
				if(util.isNull($("#startTime").val(),1)!=0){
					url+="startTime="+util.isNull($("#startTime").val());
				}else {
					$('body').message({
						type:'warning',
						content:'请选择起始日期'
					});
					return;
				}
				
				if(util.isNull($("#endTime").val(),1)!=0){
					url+="&&endTime="+util.isNull($("#endTime").val());
				}else {
					$('body').message({
						type:'warning',
						content:'请选择止计日期'
					});
					return;
				}
				window.open(url);
		break;
		case '14':
			//调度日志明细表
			var url = config.getDomain()+"/export/inboundbook?name="+util.getSubTime(util.currentTime(1),1);
			
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+new Date(new Date( $("#startTime").val()).toLocaleDateString()).getTime()/1000;
			}else {
				$('body').message({
					type:'warning',
					content:'请选择起始日期'
				});
				return;
			}
			
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+(new Date(new Date( $("#endTime").val()).toLocaleDateString()).getTime()+86400000)/1000;
			}else {
				$('body').message({
					type:'warning',
					content:'请选择止计日期'
				});
				return;
			}
			console.log(url);
			window.open(url);
		break;
		case '15':
			//分流台账明细表
			var url = config.getDomain()+"/export/outboundbook?name="+util.getSubTime(util.currentTime(1),1);
			
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="&&startTime="+util.isNull($("#startTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择起始日期'
				});
				return;
			}
			
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.isNull($("#endTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择止计日期'
				});
				return;
			}
			 if(util.isNull($("#shipName").val(),1)!=0)
				 url+="&&shipName="+util.isNull($("#shipName").val());
			window.open(url);
		break;
		
		
		case '16':
			var url = config.getDomain()+"/export/exportpsrotation?";
			
			if(util.isNull($("#startTime").val(),1)!=0){
				url+="startTime="+util.isNull($("#startTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择起始日期'
				});
				return;
			}
			
			if(util.isNull($("#endTime").val(),1)!=0){
				url+="&&endTime="+util.isNull($("#endTime").val());
			}else {
				$('body').message({
					type:'warning',
					content:'请选择止计日期'
				});
				return;
			}
			console.log(url);
			window.open(url);
			break;
		default:
			break;
		}
	};

/*************/
return{	
		init:init,//初始化报表列表信息
		exportExcel:exportExcel
	};
}();