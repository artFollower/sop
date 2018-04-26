/**
 * 月仓储入库明细表
 */

var MonthInStorage = function(){
	var pageSize,pageNo;
	/**
	 * 初始化查询条件
	 */
	var initCondition = function(){
		util.initDatePicker();
		$("#startTime").val(util.currentTime(0));
		$("#endTime").val(util.currentTime(0));
		buildTab();
	};
	
	/**
	 * 形成表格
	 */
	function buildTab(){
		var columns=[{title:'序号',render:function(item,name,index){
			 pageSize=$('div[data-role="monthInStorageGrid"]').getGrid().pageSize;
			  pageNo=$('div[data-role="monthInStorageGrid"]').getGrid().pageNo;
			 return pageSize*pageNo+index+1;
		}},{title:'日期',name:'arrivalTime'},{title:'船英文名',name:'shipName'},{title:'船中文名',name:'shipRefName'},
		             {title:'货品',name:'productName'},{title:'泊位',name:'berthName'},{title:'罐号',name:'tankName'}
		             ,{title:'入库数量（吨）',name:'goodsTotal'}];
		if($('div[data-role="monthInStorageGrid"]').getGrid()!=null){
			$('div[data-role="monthInStorageGrid"]').getGrid().destory();
		}
		$('div[data-role="monthInStorageGrid"]').grid({
			    identity : 'id',
				columns : columns,
				isShowIndexCol : false,
				isShowPages : true,
				searchCondition:{"startTime": $("#startTime").val(),
				    "endTime": $("#endTime").val(),
					  'module' : 1},
		       url:config.getDomain()+"/report/list" 
		});
	};
	
	/**
	 * 查询按钮点击事件
	 */
	function search(){
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