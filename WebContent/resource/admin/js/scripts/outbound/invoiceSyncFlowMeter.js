/**
 * 手动同步流量计
 */
var InvoiceSyncFlowMeter = function() {
	var initDialog = function() {
		$.get(config.getResource()+ "/pages/outbound/invoice/syncInvoice.jsp").done(function(data) {
			var dialog = $(data);
			initDialogControl(dialog);
		});
	};
	
	var initDialogControl = function(dialog) {
		dialog.modal({ keyboard : true });
		dialog.find('[data-dismiss="modal"]').click(function(){dialog.remove();});
		dialog.find("#syncInvoice").click(function(){
			var serial = util.isNull(dialog.find("#serial").val());
			if(serial == '' || serial.length != 11){
				$('body').message({type:'warning',content:'请正确填写通知单号！'});
				return;
			}
			config.load();
			$.ajax({
				type:'post',
				url:config.getDomain()+"/weighBridge/syncSerialToFlowMeter",
				data:{serial:serial},
				dataType:'json',
				success:function(data){
					util.ajaxResult(data,"同步",function(){
						dialog.find("#serial").val("");
					});
				}
			});
		});
	};
	
	return {
		initDialog:initDialog
	}
}();