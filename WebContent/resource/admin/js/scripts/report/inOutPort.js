/**
 * 货物进出港通过量统计
 */

var InOutPort = function(){
	
	/**
	 * 初始化查询条件
	 */
	var initCondition = function(){
		util.initDatePicker();
		$("#startTime").val(util.currentTime(0));
		$("#endTime").val(util.currentTime(0));
	};
	/**
	 * 表格数据
	 */
	var buildTab = function(){
		
		var columns=[{title:'序号',render:function(item,name,index){
			return index+1;
		}},{title:'到港日期',name:'arrivalTime'},{title:'船名',name:'shipName'},{title:'泊位',name:'berthName'},
		{title:'计划量（吨）',name:'goodsTotal'},{title:'到港类型',name:'arrivalType'},{title:"备注",name:"description"}];
		if($('div[data-role="inOutPortGrid"]').getGrid()!=null){
			$('div[data-role="inOutPortGrid"]').getGrid().destory();
		}
		
		$('div[data-role="inOutPortGrid"]').grid({
			    identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : false,
				searchCondition:{"startTime": $("#startTime").val(),
				                "endTime": $("#endTime").val(),
				                "clientId":$("#clientId").val(),
				                'module' :2},
		       url:config.getDomain()+"/report/list" ,
		       callback:function(){
		    	   var totalNum=0;
		    	   var gridData=$('div[data-role="inOutPortGrid"]').getGrid().getAllItems();
		    	   var total=0;
		        	for(var i in gridData){
		        	 if(gridData[i]&&gridData[i].goodsTotal){
		        		 totalNum=util.FloatAdd(totalNum,gridData[i].goodsTotal);
		        		}
		        	}
		    	   
		    	   var  totalHtml="<div class='form-group totalFeeDiv' style='margin-left: 0px; margin-right: 0px;'><label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计：</label>"+
					"<label class='control-label' style='margin-left: 10px;margin-right:10px;'>总计划量: </label>" +
              		"<label class='control-label' style='margin-left: 10px;margin-right:20px;'>"+totalNum+" 吨</label></div>";
					$(".totalFeeDiv").remove();
					$('div[data-role="inOutPortGrid"]').find(".grid-body").parent().append(totalHtml);		    	   
		    	   
		       }
		});
	};
	/**
	 * 查询按钮点击事件
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
		search:search
	}
}();