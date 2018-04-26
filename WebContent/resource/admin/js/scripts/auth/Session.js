var Session = function() {
	
	var distorySession = function(sessionId) {
        dataGrid = $('[data-role="sessionGrid"]');
        
        dataGrid.confirm({
			content : '确定要撤销所选记录吗?',
			callBack : function() {
				 $.post(config.getDomain()+'/auth/session/delete', "sessionId="+sessionId).done(function (data) {
						 $("body").message({
		                    type: data.code == "0000" ? 'success' : 'error',
		                    content: data.code == "0000" ? '用户注销成功' : '用户注销失败'
		                });
						 dataGrid.grid('refresh');
			        }).fail(function (data) {
			        	$("body").message({
			                type: 'error',
			                content: '网络繁忙,请稍后再试'
			            });
			        });
			}
		});
        
    };
	
	var init = function() {
		$('[data-role="sessionGrid"]').grid({
            identity:"id",
            isShowIndexCol : false,
            isShowPages : false,
            url : config.getDomain()+"/auth/session/list",
            columns: [{
				title : "账号",
				name : "principals",
				render : function(item, name, index) {
					try{
						return item.principals["realmNames"][0];						
					}catch(err) {
						return "未登录";
					}
				}
			}, {
				title : "IP地址",
				name : "host" 
			}, {
				title : "登录时间",
				name : "startTimestamp" 
			}, {
				title : "最近操作时间",
				name : "lastAccessTime" 
			},{
				title : "超时时间",
				name : "timeout",
				render : function(item, name, index) {
					return item["timeout"]/60/1000+"分钟";
				}
			}, {
				title : "联系电话",
				name : "principals",
				render : function(item, name, index) {
					try{
						return item.principals["primaryPrincipal"]["phone"];						
					}catch(err) {
						return "未知";
					}
				}
			},{
				title : "操作",
				name : "id",
				render: function(item, name, index){
					return '<div class="btn-group btn-group-xs btn-group-solid">'
						+'<button class="btn btn-primary" type="button" title="注销" onclick="Session.distorySession(\''+item.id+'\')"><span class="glyphicon glyphicon-remove"></span></button>'
						+'</div>';
				}
			}
            ]
     });
		
		$(".refresh").click(function() {
			$('[data-role="sessionGrid"]').grid('refresh');
		});
	};
	
	return {
		init : init,
		distorySession : distorySession
	};
}();