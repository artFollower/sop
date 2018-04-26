/**dashboard主页面*/
var dashboard =function(){
	var intervalId=-1;
	var userId=null;
	var init=function(id){
		userId=id;
//		clearInterval(intervalId);
		//初始化个人信息
		initAuthUser(id);
		//初始化系统消息
		initSystemMsg();
		//初始化业务消息
        initOperationMsg();
		//初始化日志
		initOperationLog(id);
//		intervalId=setInterval(initUpdate,10000);
	}
	var initUpdate=function(){
		initAuthUser(userId);
		initSystemMsg();
        initOperationMsg();
		initOperationLog(userId);
	};
	//初始化个人信息
	var initAuthUser=function(id){
		$.ajax({
			type:'get',
			url:config.getDomain()+"/auth/user/get?id="+id,
			dataType:'json',
			success:function(data){
				if(data.code=='0000'){
					$("#name").text(util.isNull(data.data[0].name));
					$("#category").text(util.isNull(data.data[0].category));
					$("#phone").text(util.isNull(data.data[0].phone));
					$("#email").text(util.isNull(data.data[0].email));
					var role="";
					for(var i in data.data[0].roles){
						role+=data.data[0].roles[i].name+",";
					}
	                $("#roles").text(role.substring(0,role.length-1));				
					var deps="";
					for(var i in data.data[0].deps){
						deps+=data.data[0].deps[i].name+",";
					}
					$("#deps").text(deps.substring(0,deps.length-1));
//					if(data.data[0].photo!=null){
//						
//						$("#photo").attr('src',data.data[0].photo);
//					}else{
////						$("#photo").attr('src',"resource/admin/layout/img/default.gif");
//						$("#photo").attr('src',"resource/admin/layout/img/profile-img.jpg");
//					}
				}
			}
		});
	};
	
	//初始化系统消息
	var initSystemMsg=function(){
		$.ajax({
			type:'get',
			url:config.getDomain()+"/messageCenter/get",
			data:{type:2},
			dataType:'json',
			success:function(data){
				if(data.code=='0000'){
					var html='';
					for(var item in data.data){
						html+='<li>'
							+'<div class="col1  col-md-8">'
							+'<div class="cont">'
								+'<div class="cont-col1">'
									+'<div class="label label-sm label-success">'
										+'<i class="fa fa-bell-o"></i>'
									+'</div>'
								+'</div>'
								+'<div class="cont-col2">'
									+'<div class="desc">'
									+data.data[item].content
									+'</div>'
								+'</div>'
							+'</div>'
						+'</div>'
						+'<div class="col2 col-md-4">'
							+'<div class="date">'
							+data.data[item].createTime
							+'</div>'
						+'</div>'
					+'</li>';
					}
					$(".sysMsgUL").empty();
					$(".sysMsgUL").append(html);	
				}
			}
		});
	};
	//初始化业务消息
	var initOperationMsg=function(){
		$.ajax({
			type:'get',
			url:config.getDomain()+"/messageCenter/get",
			data:{type:1},
			dataType:'json',
			success:function(data){
				if(data.code=='0000'){
					var html='';
					var color=["green","purple "];
					for(var i=0 ;i<data.data.length;i++){
					
						var readStatus;
						var userStatus;
						var timeStatus;
						var url="";
						var onclick="";
						var sendUserName=data.data[i].sendUserName==null?"系统公告":data.data[i].sendUserName;
						//未读的加粗
						if(data.data[i].status==1){
							if(data.data[i].content!=null){
								readStatus="<span>"+data.data[i].content+"</span>";
							}else{
								readStatus="<span>系统错误，该消息有误，请联系管理员</span>";
							}
							if(data.data[i].status!=null){
								userStatus="<span>"+sendUserName+"</span>";
							}else{
								userStatus="<span>系统</span>";
							}
							timeStatus="<span>"+data.data[i].createTime+"</span>";
							onclick="onclick='Message.setRead("+data.data[i].id+","+data.data[i].type+",this)'";
						}
						else{
							if(data.data[i].content!=null){
								readStatus=data.data[i].content;
							}else{
								readStatus="系统错误，该消息有误，请联系管理员";
							}
							if(data.data[i].status!=null){
								userStatus=sendUserName;
							}else{
								userStatus="系统";
							}
							timeStatus=data.data[i].createTime;
						}
						
						if(data.data[i].url){
							url="<div class='pull-right' style='padding-right:30px'><a href='"+data.data[i].url+"' class='btn btn-xs blue'>查看</a></div>";
						}
						
						html+="<div "+onclick+" class='todo-tasklist-item todo-tasklist-item-border-"+color[i%color.length]+"'>" +
							"<div class='todo-tasklist-item-text'>"+userStatus+"</div>"+
								"<div class='todo-tasklist-item-text'>"+readStatus+"</div>" +
								"<div class='todo-tasklist-controls pull-left'>" +
								"<span class='todo-tasklist-date'><i" +
								" class='fa fa-calendar'></i> "+timeStatus+" </span></div>" +url+" </div>";
					}
					$(".operationMsgUL").append(html);	
				}
			}
		});
	};
	//初始化任务
	var initTask=function(){
		
	};
	//初始化操作日志
	var initOperationLog=function(id){
		$.ajax({
           type:"get",
           url:config.getDomain()+"/sys/log/get?user.id="+id,
           dataType:'json',
           success:function(data){
        	   if(data.code=='0000'){
        		   if(data.data.length>0){
        			   var html='';
            		   for(var i=0;i<data.data.length;i++){
            			   var content=data.data[i].name+util.isNull(data.data[i].type);
            			   if(data.data[i].object!=null){
            				   content+="---"+data.data[i].object;
            			   }
            			  html+='<li class="timeline-blue">'
    						+'<div class="timeline-time">'
    						+'<span class="time">'+data.data[i].time.substring(11,16)+'</span>'
    						+'</div>'
    						+'<div class="timeline-icon"></div>'
    						+'<div class="timeline-body">'
    						+'<div class="timeline-content">'+content+'</div>'
    						+'<div class="timeline-footer">'
    						+'<span class="date">'+data.data[i].time.substring(0,10)+'</span>' 
    						+'</div>'
    						+'</div>'
    						+'</li>'
            		   }
            		   $(".operationLog").empty();
            		   $(".operationLog").append(html);
        		   }
        		   
        	   }
           }
		});
	};
	
	
	
	return {
		init:init,
		
	};
}();

/*//初始化日历控件
var initCalendar=function(){
	if (!jQuery().fullCalendar) {
        return;
    }
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();

    var h = {};//头部
        if (Metronic.isRTL()) {
            h = {
                right: 'title',
                center: '',
                left: 'prev,next,today,month,agendaWeek,agendaDay'
            };
        } else {
            h = {
                left: 'title',
                center: '',
                right: 'prev,next,today,month,agendaWeek,agendaDay'
            };
        }
    $('#calendar').fullCalendar('destroy'); // destroy the calendar 销毁
    $('#calendar').fullCalendar({ //re-initialize the calendar 重新初始化
        disableDragging: false,
        header: h,
        lang: 'zh-cn',
        editable: true,
        events: []
    });
};*/
