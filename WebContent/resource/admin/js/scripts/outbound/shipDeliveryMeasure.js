//流量计台账
var shipDeliveryMeasure = function(){
	//初始化列表
	function initList(shipId){
		initSearchCtr();
		initTable(shipId);
	};
	function initSearchCtr(){
		util.initTimePicker($(".sdm"));
		$(".btn-modify").click(function() {
			var data = $('[data-role="shipDeliveryMeasure"]').getGrid().selectedRowsIndex();
			var $this = $(this);
			if (data.length == 0){
				$(body).message({ type : 'warning',content : '请选择要修改的记录'});
				return;}
			dataGrid = $(this);
			initEdit($('[data-role="shipDeliveryMeasure"]').getGrid().selectedRows()[0].id);
		});
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$(".search").click(function() {
			var shipDeliverMeasureDto = {
					'shipName':$("#ship").val(),
					'startTime':$("#startTime").val(),
					'endTime':$("#endTime").val()
			};
	        $('[data-role="shipDeliveryMeasure"]').getGrid().search(shipDeliverMeasureDto);
		});
	};
	
	function initTable(shipId){
		var columns = 
		[{title : "日期",name : "startTime"
		},{title : "船名",name : "shipName"
		},{title : "泊位",name : "berthName"
		},{title : "罐号",name : "tankName"
		},{title : "管线号",name : "tubeName"
		},{title : "前尺（吨）",render: function(item){
			return util.toDecimal3(item.startLevel,true);}
		},{title : "后尺（吨）",render: function(item){
				return util.toDecimal3(item.endLevel,true);}
		},{title : "流量计（吨）",render: function(item){
				return util.toDecimal3(item.flowmeter,true);}
		},{title : "机房实发量",name : "realAmount",
		},{title : "船方（吨）",name : "shipAmount"
		},{title : "计量（吨）",render: function(item){
				return util.toDecimal3(item.metering,true);}
		},{title : "流-计",render: function(item){
				return util.FloatSub(item.flowmeter,item.metering,3);}
		},{title : "雷-计",render: function(item){
				return util.FloatSub(item.realAmount,item.metering,3);}
		},{title : "船-计",render: function(item){
				return util.FloatSub(item.shipAmount,item.metering,3);}
		},{title : "检尺人",name : "userName",
		}, {title : "操作",render: function(item){
			return "<div class='input-group-btn' style='width:40px;'>"
			+"<a href='javascript:void(0);' onclick='shipDeliveryMeasure.initEdit("+item.id+")' class='btn btn-xs blue'>"
			+"<span class='glyphicon glyphicon-edit' title='修改'></span></a>"
			+"<a href='javascript:void(0);' style='margin:0 2px;' onclick='shipDeliveryMeasure.del("+item.id+")' class='btn btn-xs red'>"
			+"<span class='glyphicon glyphicon-remove' title='删除'></span></a></div>";
			}
		}];
		
		if($('[data-role="shipDeliveryMeasure"]').getGrid()!=null)
			$('[data-role="shipDeliveryMeasure"]').getGrid().destory();
		$('[data-role="shipDeliveryMeasure"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			url : config.getDomain()+"/shipDeliveryMeasure/list"
		});
	};
/*******************************************************************************/
	function initEdit(id){
		$.get(config.getResource()+ "/pages/outbound/shipDeliveryMeasure/edit.jsp").done(function(data) {
			var dialog = $(data);
			initCtr(dialog);
			initMsg(dialog,id);
			
		});
	};
	function initCtr(dialog){
		initFormIput(dialog);
		dialog.modal({keyboard : true});
		util.initTimePicker(dialog);
		dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
		//提交按钮点击事件
		dialog.find("#submit").click(function(){
				var data={
						"id":util.isNull(dialog.find("#id").val(),1),
						"arrivalId":dialog.find("#arrivalId").val(),
						"startLevel":util.isNull(dialog.find("#startLevel").val(),1),
						"endLevel":util.isNull(dialog.find("#endLevel").val(),1),
						"shipAmount":util.isNull(dialog.find("#shipAmount").val(),1),
						"metering":util.isNull(dialog.find("#metering").val(),1)
				};
				$.ajax({
					type : "post",
					url : config.getDomain() + "/shipDeliveryMeasure/addorupdate",
					dataType : "json",
					data:data,
					success : function(data) {
						util.ajaxResult(data,'提交',function(ndata,nmap){
							initMsg(dialog,nmap.id);
							if($('[data-role="shipDeliveryMeasure"]'))
							 util.updateGridRow($('div[data-role="shipDeliveryMeasure"]'),{id:nmap.id,url:'/shipDeliveryMeasure/list'});
						});
					}});
			});
			
	};
	function initMsg(dialog,id){
		dialog.find("#id").val(id);
		if(id){
			$.ajax({
				type : "post",
				url : config.getDomain() + "/shipDeliveryMeasure/get",
				dataType : "json",
				data:{"id":id},
				success : function(data){
					util.ajaxResult(data,"初始化信息",function(ndata){
						if(ndata&&ndata.length>0){
							var itemMsg=ndata[0];
							dialog.find("#shipName").val(itemMsg.shipName);
							dialog.find("#tankName").attr("data",itemMsg.tankId).val(itemMsg.tankName);
							dialog.find("#berthName").val(itemMsg.berthName);
							dialog.find("#tubeName").val(util.isNull(itemMsg.tubeName));
						util.initTimeVal(dialog.find(".startTime"),itemMsg.openPumpTime);
						util.initTimeVal(dialog.find(".endTime"),itemMsg.stopPumpTime);
						dialog.find("#startLevel").val(itemMsg.startLevel);
						dialog.find("#endLevel").val(itemMsg.endLevel);
						dialog.find("#flowmeter").val(itemMsg.flowmeter);
						dialog.find("#metering").val(itemMsg.metering);
						dialog.find("#shipAmount").val(itemMsg.shipAmount);
						dialog.find("#flowmeterDiff").val(util.FloatSub(itemMsg.flowmeter,itemMsg.metering,3));
						dialog.find("#meteringDiff").val(util.FloatSub(itemMsg.realAmount,itemMsg.metering,3));
						dialog.find("#shipAmountDiff").val(util.FloatSub(itemMsg.shipAmount,itemMsg.metering,3));
						dialog.find("#realAmount").val(itemMsg.realAmount);
						dialog.find("#checkUserName").val(itemMsg.userName);
						dialog.find("#arrivalId").val(itemMsg.arrivalId);
						}
					},true);
				}
			});
		}
	};
	
	//监控船方
	function shipVal(obj,value){
		var dialog=$(obj).closest(".modal-dialog");
		dialog.find("#shipAmountDiff").val(util.FloatSub(value,dialog.find("#metering").val(),3));
	}
	function flowVal(obj){
		var dialog=$(obj).closest(".modal-dialog");
		dialog.find("#flowmeter").val(util.FloatSub(dialog.find("#endLevel").val(),dialog.find("#startLevel").val(),3));
		dialog.find("#flowmeterDiff").val(util.FloatSub(dialog.find("#flowmeter").val(),dialog.find("#metering").val(),3));
	}
	function meteringVal(obj,value){
		var dialog=$(obj).closest(".modal-dialog");
		dialog.find("#shipAmountDiff").val(util.FloatSub(dialog.find("#shipAmount").val(),value,3));
		dialog.find("#flowmeterDiff").val(util.FloatSub(dialog.find("#flowmeter").val(),value,3));
		dialog.find("#meteringDiff").val(util.FloatSub(dialog.find("#realAmount").val(),value,3));
	}
	//删除功能
	function del(id){
		dataGrid = $('[data-role="shipDeliveryMeasure"]');
        dataGrid.confirm({
			content : '确定要删除所选记录吗?',
			callBack : function() {
				 $.post(config.getDomain() + "/shipDeliveryMeasure/delete", "id="+id).done(function (data) {
					 util.ajaxResult(data,"删除",function(){
						 dataGrid.grid('refresh');
					 });
			        }).fail(function (data) {
			            dataGrid.message({
			                type: 'error',
			                content: '删除失败'
			            });
			        });
			}
		});
		
	};
	function exportExcel(){
		var url=config.getDomain()+'/shipDeliveryMeasure/exportExcel?id=id';
		
		if(util.isNull($("#ship").val())!='')
			url+='&shipName='+$("#ship").val();
		if(util.isNull($("#startTime").val())!='')
			url+='&startTime='+$("#startTime").val();
		if(util.isNull($("#endTime").val())!='')
			url+='&endTime='+$("#endTime").val()
		window.open(url);
	};
	
	//列表页
	return {
		initList:initList,
		initEdit:initEdit,
		del:del,
		shipVal:shipVal,
		flowVal:flowVal,
		meteringVal:meteringVal,
		exportExcel:exportExcel
	};
}() ;