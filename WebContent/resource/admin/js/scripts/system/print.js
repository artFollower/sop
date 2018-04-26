var CommonPrint = function() {
	
	var gridPrint = function(grid,printHeadder,printFotter) {
		$.get(config.getResource() + "/pages/system/gridPrintPriview.jsp").done(function(data) {
            dialog = $(data);

            var columns = new Array();
            var _columns = grid.options.columns;
            for(var i = 0;i<_columns.length;i++) {
            	var item = _columns[i];
            	try{
            		if(item.unPrint == undefined || item.unPrint == null || !item.unPrint) {
                		columns.push(item);
                	}
            	}catch(err) {}
            }
            if(printHeadder != null && typeof printHeadder === "function") {
            	var header = printHeadder();
            	dialog.find('#gridPrintPriview').append(header);
			}
            dialog.find('#gridPrintPriview').grid({
                identity: "id",
                isShowIndexCol : false, //是否显示索引列
                isShowPages : false, //是否显示分页
        		isUserLocalData : true, //是否使用本地数据源
                localData : grid.items,
                columns: columns,
                callback : function() {
                	dialog.find('.grid-body').attr("style","min-height: 150px;");
                }
            });
            
           
            if(printFotter != null && typeof printFotter === "function") {
            	var fotter = printFotter();
            	dialog.find('#gridPrintPriview').append(fotter);
			}
            
            dialog.find('.btn-print').on('click',function() {//打印
            	dialog.find('#gridPrintPriview').jqprint();
            }).end().modal({
                keyboard: true
            });
            dialog.find('.btn-close').on('click',function() {//关闭
                dialog.remove();
            });
        });
	};
	
	return {
		gridPrint : gridPrint
	};
}();