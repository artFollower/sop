var BerthDetail=function(){
	function initList(){
		$.get(config.getResource()+ "/pages/report/berthDetailList.jsp").done(
         function(data) {
		var dialog = $(data);
		initSearchCondition(dialog);			
		initTable(dialog);
		 });	
	};
	function initSearchCondition(dialog){
		dialog.modal({keyboard : true});
		util.urlHandleTypeaheadAllData("/product/select",dialog.find('#productId'));
		util.urlHandleTypeahead("/berth/list",dialog.find("#berthId"));
		util.initMonthPicker(dialog);
		dialog.find("#startTime").datepicker("setDate",util.currentTime(0).substring(0,7));
		dialog.find("#search").click(function(){
			if(util.isNull(dialog.find("#startTime").val())!="")
			dialog.find('[data-role="berthDetailTable"]').getGrid().search(getSearchCondition(dialog));
			else
				$('body').message({type:'warning',content:'请选择时间'});
		});
	};
	function getSearchCondition(dialog){
		return {
			productId:dialog.find("#productId").attr("data"),
			berthId:dialog.find("#berthId").attr("data"),
			startTime:dialog.find("#startTime").val()
		}
	};
	function initTable(dialog){
		var columns=[{title:"泊位",name:"berthName"},{title:"类型",render:function(item){
			if(item.type==1) return "入库";
			else if(item.type==2) return "出库";
			else if(item.type==3) return "入库通过";
			else if(item.type==5) return "出库通过";
			else return "";
		}},{title:"船名",name:"shipName"},{title:"货品",name:"productName"}
		,{title:"到港时间",name:"arrivalTimeStr"},{title:"离港时间",name:"leaveTimeStr"},{title:"在港时间（h）",name:"timeCount"}];
		if(dialog.find('[data-role="berthDetailTable"]').getGrid()!=null)
			dialog.find('[data-role="berthDetailTable"]').getGrid().destory();
		dialog.find('[data-role="berthDetailTable"]').grid({
			identity : 'id',
			columns : columns,
			showPage:8,
			searchCondition:getSearchCondition(dialog),
			isShowIndexCol : false,
			isShowPages : true,
			url : config.getDomain()+"/report/getBerthDetailList",
			callback:function(){
				dialog.find('[data-role="berthDetailTable"]').find('div[class="grid-body"],thead[class="grid-table-head"]').css('width','100%');
			}
		});
	};
	
	return {
		initList:initList
	}
}();