/**
 * 库容状态统计
 */

var StorageState = function(){
	Highcharts.setOptions({
        lang: {
             printChart:"打印图表",
              downloadJPEG: "下载JPEG 图片" , 
              downloadPDF: "下载PDF文档"  ,
              downloadPNG: "下载PNG 图片"  ,
              downloadSVG: "下载SVG 矢量图" , 
              exportButtonTitle: "导出图片" 
        }
    });
	/**
	 * 初始化查询条件
	 */
	var initCondition = function(){
		util.initDatePicker();
		$("#startTime").val(util.currentTime(0));
		$("#endTime").val(util.currentTime(0));
		initTankList();
	};
	
	/**
	 * 图形数据
	 */
	var chartData = function(){
		config.load();
		var title=$("#startTime").val().substring(0,4)+'年'+$("#startTime").val().substring(5,7)+'月份库容状态统计';
		var endTime=util.isNull($("#endTime").val());
		$.ajax({
			type : "post",
			url : config.getDomain()+"/chart/findChart",
			data : {
				"startTime": $("#startTime").val(),
				"endTime":endTime,
				"tankKeys":getNotCheckedTankKeys(),
			},
			dataType : "json",
			success : function(data){
				util.ajaxResult(data,'获取罐容',function(ndata){
					var xAxisDetail =new Array();//日期
					var yAxisDetail =[{name:'汽油',data:[]},{name:'乙二醇',data:[]},{name:'柴油',data:[]},
					                  {name:'甲醇',data:[]},{name:'正构烷烃',data:[]},{name:'LAB',data:[]},
					                  {name:'苯',data:[]},{name:'总量',data:[]}];
					var totalDetail=new Array();
					if(ndata&&ndata.length>0){
						var day;
						var differDay=parseInt(util.getDifferTime($("#startTime").val(),endTime,2));
						 for(var i=0;i<=differDay;i++){
							//横坐标
							 xAxisDetail.push(util.addDate($("#startTime").val(),i).substring(8,10));
							//初始化数据
							for(var j=0;j<8;j++){
							yAxisDetail[j].data[i]=0;
							}
							}
						for(var i=0,len=ndata.length;i<len;i++){
							//当天的总量
							differDay=parseInt(util.getDifferTime($("#startTime").val(),ndata[i].DTime.substring(0,10),2));
							yAxisDetail[7].data[differDay]=parseFloat(util.FloatAdd(yAxisDetail[7].data[differDay],ndata[i].hasTotalNum,3));
							//每种货品在当天的量
							$.each(yAxisDetail, function(cur,t){
						          if(yAxisDetail[cur].name==ndata[i].productName){
						        	  yAxisDetail[cur].data[differDay]=ndata[i].hasTotalNum;
						          };
						       });
						//获取最后一天的总量
						if(endTime==ndata[i].DTime){
							totalDetail.push({
								name:ndata[i].productName,
								num:ndata[i].totalNum
							});
						}	
						};
						buildChart(xAxisDetail,yAxisDetail,totalDetail,title);
					}
				},true);
			}
		});
	};
	
	/**
	 * 形成图形
	 */
	function buildChart(xAxisDetail,yAxisDetail,totalDetail,textContent){
		$('#storageState').highcharts({
	        title: { text: textContent,x: -20},
	        chart:{
	        	style:{
	        		marginLeft:'20'
	        	}
	        },
	        credits:{
	        	 enabled:true,                    // 默认值，如果想去掉版权信息，设置为false即可
	        	text:getTotalNumXML(totalDetail),               // 显示的文字
	        	position:{                          // 位置设置
	        		align: 'right',
	        		x:-5,
	        		verticalAlign: 'bottom',
	        		y: -200
	        	},
	        	style: {                            // 样式设置
	        		cursor: 'pointer',
	        		color: 'black',
	        		fontSize: '12px'
	        	}},
	        xAxis: {
	        	title: {text: '日期'},
	        	categories: xAxisDetail,
	        },
	        yAxis: {min:0,
	            title: {text: '重量 (吨)'},
	            plotLines: [{ value: 0,width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	        	pointFormat: '{series.name}: <b>{point.y:.3f} 吨</b>'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'top',
	            x: 0,  
	            y: 100,  
	            borderWidth: 0
	        },
	        series:yAxisDetail
	    });
	};
	
	/**
	 * 查询按钮点击事件
	 */
	var queryResult = function(){
		
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
		chartData();
	};
	
	function  initTankList(){
		$.ajax({
			type:'post',
			url:config.getDomain()+"/tank/list?pagesize=0&type=0",
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取储罐数据',function(ndata){
					if(ndata&&ndata.length>0){
						var html='<div class="actions  pull-right columnToggler"> <div class="btn-group "> <a aria-expanded="true" data-toggle="dropdown" href="javascript:void(0)" id="columnTogglerCtr" class="btn default ">储罐'+
						'<i class="fa fa-angle-down"></i></a><div id="tank_toggler" class="dropdown-menu hold-on-click dropdown-checkboxes  pull-right" style="height: 260px; width: 95%; overflow-y: auto; overflow-x: hidden;">';
						for(var i=0,len=ndata.length;i<len;i++){
							html+='<label><input type="checkbox" checked="" id="'+ndata[i].key+'">'+ndata[i].code+'</label>';
						}
						html+='</div></div></div>';
						$("#tankIds").append(html);
					}
				},true);
			}
		});
	};
	
	function getNotCheckedTankKeys(){
		var array='';
		$('#tank_toggler input[type="checkbox"]').each(function(){
			 $this=$(this);
			 if($this.attr('id')){
			 if(!$this.is(':checked')){
				array+="'"+util.isNull($this.attr('id'))+"',";
			 }else{
			 }}
		 });
		return array.length>0?array.substring(0,array.length-1):array;
	}
	
	function getTotalNumXML(dataArray){
		var html='<strong>各货品最大存储量<br><br></strong>';
		if(dataArray&&dataArray.length>0){
		for(var i=0,len=dataArray.length;i<len;i++){
			html+='<strong>'+dataArray[i].name+
			'</strong>:'+dataArray[i].num+'吨<br><br>';
		}
		}
		return html;
	};
	return {
		init:function(){
			//初始化查询条件
			initCondition();
		},
		queryResult:function(){
			queryResult();
		},
		initTankList:initTankList,
		getNotCheckedTankKeys:getNotCheckedTankKeys
	}
}();