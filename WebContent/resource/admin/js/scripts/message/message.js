var Message = function() {
	
	var intervalId=0;
	
	var currentType=0;
	
	var page=0;
	
	var all=0;
	var task=0;
	var system=0;
	
	function init(){
//		clearInterval(intervalId);
		$(".mScroller").each(function() {
            if (!$(this).attr("data-initialized")) {
                return; // exit
            }
            $(".mScroller").slimScroll({
                allowPageScroll: true, // allow page scroll when the element scroll is ended
                size: '7px',
                color: ($(this).attr("data-handle-color") ? $(this).attr("data-handle-color") : '#bbb'),
                wrapperClass: ($(this).attr("data-wrapper-class") ? $(this).attr("data-wrapper-class") : 'slimScrollDiv'),
                railColor: ($(this).attr("data-rail-color") ? $(this).attr("data-rail-color") : '#eaeaea'),
                position: 'right',
                height: ($(this).attr("data-height") ? $(this).attr("data-height") : $(this).css('height')),
                alwaysVisible: ($(this).attr("data-always-visible") == "1" ? true : false),
                railVisible: ($(this).attr("data-rail-visible") == "1" ? true : false),
//                disableFadeOut: false
            });

            $(this).attr("data-initialized", "1");
        });
		
		$("#sendMessage").unbind('click'); 
		$("#sendMessage").click(function(){
			$.ajax({
				type : "post",
				url : config.getDomain()+"/messageCenter/sendSystemMessage",
				dataType : "json",
				data:{
					"content":$(".taskContent").val()
				},
				success : function(data) {
					if(data.code=="0000"){
						$("body").message({
		                    type: 'success',
		                    content: '发送成功'
		                });
						$(".taskContent").val("");
						getMessageCount(true);
						
					}else{
						$("body").message({
		                    type: 'error',
		                    content: '发送失败'
		                });
						$(".taskContent").val("");
					}
				}
			});
		});
		

		getMessageCount(false);
		
		getMessage(0,0);
		
		//定时器
//		intervalId=setInterval(timeget, 10000);
		
	}

	function timeget(){
		getMessageCount(true);
//		getMessage(currentType,0);
	}
	
	
	function getMessageCount(doUpdate){
		$.ajax({
			type : "get",
			url : config.getDomain()+"/messageCenter/getCount",
			dataType : "json",
			success : function(data) {
				if(data.code=="0000"){
					if(doUpdate&&currentType==0&&$(".all").html()<data.data[0].allCount){
						getMessage(currentType,0);
					}
					if(doUpdate&&currentType==1&&$(".task").html()<data.data[0].taskCount){
						getMessage(currentType,0);
					}
					if(doUpdate&&currentType==2&&$(".system").html()<data.data[0].systemCount){
						getMessage(currentType,0);
					}
					$(".task").html(data.data[0].taskCount);
					$(".system").html(data.data[0].systemCount);
					$(".all").html(data.data[0].allCount);
				}
			}
		});
	}
	
	
	
	function getMessage(type,currentPage){
		var url=type==0?(config.getDomain()+"/messageCenter/get?pagesize=10&page="+currentPage):(config.getDomain()+"/messageCenter/get?type="+type+"&pagesize=10&page="+currentPage);
		$.ajax({
			type : "get",
			url : url,
			dataType : "json",
			success : function(data) {
				if(data.code=="0000"){
					if(currentPage==0){
						$(".todo-tasklist").remove();
					}else{
						$(".more").remove();
					}
					if(data.data.length>0){				
						var color=["green","purple "];
						var div0="<div class='todo-tasklist'>";
						var div1="";
						for(var i=0;i<data.data.length;i++){
							var readStatus;
							var userStatus;
							var timeStatus;
							var url="";
							var onclick="";
							var sendUserName=data.data[i].sendUserName==null?"系统公告":data.data[i].sendUserName;
							//未读的加粗
							if(data.data[i].status==1){
								if(data.data[i].content!=null){
									readStatus="<span style='font-weight:bold'>"+data.data[i].content+"</span>";
								}else{
									readStatus="<span style='font-weight:bold'>系统错误，该消息有误，请联系管理员</span>";
								}
								if(data.data[i].status!=null){
									userStatus="<span style='font-weight:bold'>"+sendUserName+"</span>";
								}else{
									userStatus="<span style='font-weight:bold'>系统</span>";
								}
								timeStatus="<span style='font-weight:bold'>"+data.data[i].createTime+"</span>";
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
								url="<div class='pull-right' style='padding-right:30px'><a href='"+data.data[i].url+"' class='btn btn-primary'>查看</a></div>";
							}
							
							div1+="<div "+onclick+" class='todo-tasklist-item todo-tasklist-item-border-"+color[i%color.length]+"'>" +
								"<div class='todo-tasklist-item-text'>"+userStatus+"</div>"+
									"<div class='todo-tasklist-item-text'>"+readStatus+"</div>" +
									"<div class='todo-tasklist-controls pull-left'>" +
									"<span class='todo-tasklist-date'><i" +
									" class='fa fa-calendar'></i> "+timeStatus+" </span></div>" +url+" </div>";
						}
						if(data.map.totalRecord<10*(currentPage+1)){
							div1+="<div class='todo-tasklist-item' onclick='Message.getMore()'> <div  style='text-align:center'> 全部加载完成 </div> </div>";
						}else{
							div1+="<div class='todo-tasklist-item more' onclick='Message.getMore()'> <div style='text-align:center'> 点击加载更多  </div>  </div>";
						}
						if(currentPage==0){
							div0+=div1;
							div0+="</div>";
							$("#tasklist").append(div0);
						}else{
							$(".todo-tasklist").append(div1);
						}
						
					}
					
				}
			}
		});
	}
	
	
	function setRead(id,type,obj){
		
		$.ajax({
			type : "get",
			url : config.getDomain()+"/messageCenter/updateReadStatus?ids="+id,
			dataType : "json",
			success : function(data) {
				$(obj).children("div").each(function(){
					$(this).find("span").attr("style","");
				});
				
				if(type==1){
					$(".task").html(($(".task").html()-1));
					$(".all").html(($(".all").html()-1));
				}
				if(type==2){
					$(".system").html(($(".system").html()-1));
					$(".all").html(($(".all").html()-1));
				}
				$(obj).attr("onclick","");
			}
		});
		
		
		
	}
	
	function changetask(obj, item) {
		$(obj).parent().addClass("active").siblings().removeClass("active");
		$(obj).parent().parent().children("li").each(function(){
			$(this).find("span").removeClass("badge-active");
		});
		$(obj).find("span").addClass("badge-active");
		getMessageCount(false);
		getMessage(item,0);
		currentType=item;
		page=0;
		if(item==2){
			$("#sendForm").show();
		}else{
			$("#sendForm").hide();
		}
	};
	
	function getMore(){
		page+=1;
		getMessage(currentType, page);
	}
	
	//设置全部消息为已读
	function setAllRead(){
		$.ajax({
			type : "get",
			url : config.getDomain()+"/messageCenter/setAllRead",
			dataType : "json",
			success : function(data) {
				$(".todo-tasklist").children("div").each(function(){
					$(this).find("span").attr("style","");
					$(this).attr("onclick","");
					
					
				});
				$(".more").attr("onclick","Message.getMore()");
				
					$(".task").html(0);
					$(".system").html(0);
					$(".all").html(0);
			}
		});
		
	}
	
	return {
		changetask : changetask,
		init:init,
		getMore:getMore,
		setAllRead:setAllRead,
		setRead : function(id,type,obj){
			setRead(id,type,obj);
		}
	}
	
}();