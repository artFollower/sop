var Log = function() {
	
	var showContent = function(content) {
		$.get(config.getResource() + "/pages/system/dialog.jsp").done(function(data) {
            dialog = $(data);
            
            dialog.find("#logPriview").append(content);
            
            dialog.find('.btn-close').on('click',function() {//关闭
                dialog.remove();
            }).end().modal({
                keyboard: true
            });
        });
	};
	
	var init = function() {
		//初始化对象操作数据
		$.ajax({
			type:'get',
			url:config.getDomain()+"/common/params",
			dataType:'json',
			success:function(data){
				if(data.code == "0000") {
					for(var i=0;i<data.data.length;i++) {
						var _data = data.data[i];
						if(_data.type == "LOGOBJECT") {
							$("select[name='object']").append("<option value='"+_data.key+"'>"+_data.name+"</option>");
						}else {
							$("select[name='type']").append("<option value='"+_data.key+"'>"+_data.name+"</option>");
						}
					}
				}
			}
		});
		//初始化用户搜索
		var all = [];
		var loading = false;
		$("input[name='user'").typeahead({
			items: 10, //最多显示个数
			delay: 1000 , //延迟时间
			source: function(query, process) {
				if(query != null && query != "" && !loading) {
					config.load();
					loading = true;
					$.ajax({
						url: config.getDomain()+"/auth/user/get",
						type: 'post',
						data: 'pagesize=10&name=' + query,
						complete : function() {
							config.unload();
							loading = false;
						},
						success: function(data) {
							var results = [];
							try {
								all = [];
								 for (var i=0;i<data.data.length;i++) {
									 var _data = data.data[i];
									 all.push({
										'id':_data.id,
										'name' : _data.name
									});
								}
								 var results = _.map(all, function (one) {
									 return one.name;
								 });
							}catch(err) {
							}
							process(results);
						}
					});
				}
			},
			updater:function(item){
				var one = _.find(all, function (p) {
					return p.name == item;
				});
				$("input[name='user'").attr("data",one.id);
				$("input[name='user'").val(one.name);
				return item;
			},
			//移除控件时调用的方法
			noselect: function(query) {
				//匹配不到就去掉id，让内容变空，否则给id
				var one = _.find(all, function (p) {
					return p.name == query;
				});
				if(one==null){
					$("input[name='user'").removeAttr("data");
					$("input[name='user'").val("");
				}else{
					$("input[name='user'").attr("data",one.id);
				}
			}
		});
		
		$('[data-role="logGrid"]').grid({//获取日志信息
            identity:"id",
            url : config.getDomain()+"/sys/log/get",
            columns: [{
				title : "用户",
				name : "name"
			},{
				title : "IP地址",
				name : "ip"
			}, {
				title : "时间",
				name : "time"
			},{
				title : "对象",
				name : "object"
			}, {
				title : "操作",
				name : "type",
				render : function(item, name, index) {
					var re = new RegExp(/\"/g);
					var content = item.content;
					content = content.replace(re,"");
					return "<a href='javascript:void(0)' onclick='Log.showContent(\""+content+"\")'>"+item.type+"</a>";
				}
			}]
     });
		
	
		$("#logSearch").click(function() {
			var _user = $("input[name='user']").attr("data");
			var params = {
				'user.id':_user=="" ? 0 : _user,
				'object' : $("select[name='object']").val(),
				'type' : $("select[name='type']").val(),
				'startTime' : $("input[name='startTime']").val(),
				'endTime': $("input[name='endTime']").val(),
				'content': $("input[name='content']").val()
			};
           $('[data-role="logGrid"]').getGrid().search(params);
		});
		$("#logClear").click(function() {
			$("input[name='user']").removeAttr("data");
			$("input[name='user']").val("");
			$("select[name='type']").val("");
			$("input[name='startTime']").val("");
			$("input[name='endTime']").val("");
			$("input[name='content']").val("");
           $('[data-role="logGrid"]').getGrid().search({'content':'','user.id':'0','object' : '','type' : '','startTime' : '','endTime': ''});
		});
		
		$('.date-picker').datepicker({
			  rtl: Metronic.isRTL(),
			    orientation: "left",
			    format: "yyyy-mm-dd",
			    showInputs:true,
			    startView:"days",
			    minViewMode:"days",
			    showDay:"false",
	            disableMousewheel:false,
	            changeMonth :false,
			    autoclose: true,
		});
	};
	
	return {
		init : init,
		showContent : showContent
	};
}();