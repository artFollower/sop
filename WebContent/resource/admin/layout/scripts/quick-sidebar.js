/**
Core script to handle the entire theme and core functions
**/
var QuickSidebar = function () {
var iq;
var roster;
	

var currentMessageUser="";

//var status=0;//0:不在聊天界面   1：在聊天界面



//收到消息自动滚到最后
var getLastPostPos = function() {
    var wrapper = $('.page-quick-sidebar-wrapper');
    var wrapperChat = wrapper.find('.page-quick-sidebar-chat');
	 var chatContainer = wrapperChat.find(".page-quick-sidebar-chat-user-messages");
    var height = 0;
    chatContainer.find(".post").each(function() {
        height = height + $(this).outerHeight();
    });

    return height;
}; 


//发消息/接受消息
var preparePost = function(dir, time, name, avatar, message) {
    var tpl = '';
    tpl += '<div class="post '+ dir +'">';
    tpl += '<img class="avatar" alt="" src="' + Layout.getLayoutImgPath() + avatar +'.jpg"/>';
    tpl += '<div class="message">';
    tpl += '<span class="arrow"></span>';
    tpl += '<a href="#" class="name">'+name+'</a>&nbsp;';
    tpl += '<span class="datetime">' + time + '</span>';
    tpl += '<span class="body">';
    tpl += message;
    tpl += '</span>';
    tpl += '</div>';
    tpl += '</div>';

    return tpl;
};
	var getMessage=function(user,content){
		
		
		//计数
		if(content!=""){
			
			var currentUID=currentMessageUser.substring(0,currentMessageUser.indexOf('@'));
			var uid=user.substring(0,user.indexOf('@'));
			if(currentUID!=uid){
				var count=$('#'+uid).html()==""?0:$('#'+uid).html();
				//该用户的count
				$('#'+uid).html(parseFloat(count)+1);
				//总count
				var IMCount=$('#IMCount').html()==""?0:$('#IMCount').html();
				$('#IMCount').html(parseFloat(IMCount)+1);
				
			}
			
	        var wrapper = $('.page-quick-sidebar-wrapper');
	        var wrapperChat = wrapper.find('.page-quick-sidebar-chat');
			var chatContainer = wrapperChat.find(".page-quick-sidebar-chat-user-messages");
			//在输入框内填入
			var time = new Date();
            var message = preparePost('in', (time.getHours() + ':' + time.getMinutes()), uid, 'avatar2', content);
            message = $(message);
            chatContainer.find("#"+uid).append(message);

            chatContainer.slimScroll({
                scrollTo: getLastPostPos()
            });
			
			
		}
		
	}


//获得好友列表
	var initUserList=function(items){
		var userList=$('.list-items');
		var str="";
		var userDiv="";
		for (var i=0;i<items.length;i++) {
//			items.item(i).getAttribute('name');
			str+='<li class="media">'+
			'<div class="media-status">'+
			'<span class="badge badge-success" id="'+items.item(i).getAttribute('jid').substring(0,items.item(i).getAttribute('jid').indexOf('@'))+'"></span>'+
			'</div>'+
//			'<img class="media-object" src="/resource/admin/layout/img/avatar3.jpg" alt="...">'+
			'<div class="media-body">'+
			'<h4 class="media-heading">'+items.item(i).getAttribute('name')+'</h4>'+
			
			'</div>'+
			'<input id="jid" style="display:none;" value="'+items.item(i).getAttribute('jid')+'" />'+
			
			'</li>';
			
			userDiv+="<div class='mId' id="+items.item(i).getAttribute('jid').substring(0,items.item(i).getAttribute('jid').indexOf('@'))+"></div>";
			
			
		}
		var wrapper = $('.page-quick-sidebar-wrapper');
        var wrapperChat = wrapper.find('.page-quick-sidebar-chat');
		var chatContainer = wrapperChat.find(".page-quick-sidebar-chat-user-messages");
		chatContainer.append(userDiv);
		
		userList.append(str);
		
//		QuickSidebar.init();
		handleQuickSidebarChat();
		
	};
	
	//改变状态
	var stateChange=function(state){
//		$('.state').text(state);
		$('.state').html(state);
	};
	
    // Handles quick sidebar toggler
    var handleQuickSidebarToggler = function () {
    	
//    	remote.jsjac.chat.login("gumin","123456",true);
    	
    	
        // quick sidebar toggler
        $('.top-menu .dropdown-quick-sidebar-toggler a, .page-quick-sidebar-toggler').click(function (e) {
            $('body').toggleClass('page-quick-sidebar-open'); 
        });
    };

    // Handles quick sidebar chats
    var handleQuickSidebarChat = function () {
        var wrapper = $('.page-quick-sidebar-wrapper');
        
        
        var wrapperChat = wrapper.find('.page-quick-sidebar-chat');

        var initChatSlimScroll = function () {
            var chatUsers = wrapper.find('.page-quick-sidebar-chat-users');
            var chatUsersHeight;

            chatUsersHeight = wrapper.height() - wrapper.find('.nav-justified > .nav-tabs').outerHeight();

            // chat user list 
            Metronic.destroySlimScroll(chatUsers);
            chatUsers.attr("data-height", chatUsersHeight);
            Metronic.initSlimScroll(chatUsers);

            var chatMessages = wrapperChat.find('.page-quick-sidebar-chat-user-messages');
            var chatMessagesHeight = chatUsersHeight - wrapperChat.find('.page-quick-sidebar-chat-user-form').outerHeight() - wrapperChat.find('.page-quick-sidebar-nav').outerHeight();

            // user chat messages 
            Metronic.destroySlimScroll(chatMessages);
            chatMessages.attr("data-height", chatMessagesHeight);
            Metronic.initSlimScroll(chatMessages);
        };

        initChatSlimScroll();
        Metronic.addResizeHandler(initChatSlimScroll); // reinitialize on window resize

        
        //进入聊天窗口,如果之前没进过，就创建一个uid的div，
        wrapper.find('.page-quick-sidebar-chat-users .media-list > .media').click(function () {
        	
//	        var wrapper = $('.page-quick-sidebar-wrapper');
//	        var wrapperChat = wrapper.find('.page-quick-sidebar-chat');
			var chatContainer = wrapperChat.find(".page-quick-sidebar-chat-user-messages");
			
			
        	
        	
        	
//        	status=1;
        	currentMessageUser=$(this).find('#jid').val();
        	
        	var countId=currentMessageUser.substring(0,currentMessageUser.indexOf('@'));
        	var c=$('#'+countId).html()==""?0:$('#'+countId).html();
        	if(chatContainer.find("#"+countId)){
        		
        		
        	}else{
        		var str="<div class='mId' id="+countId+"></div>";
        		chatContainer.append(str);
        		
        	}
        	
        	 chatContainer.find(".mId").each(function() {
        	      if($(this).attr("id")!=countId){
        	    	  $(this).hide();
        	      }else{
        	    	  $(this).show();
        	      }
        	    });
        		
        	var IMCount=$('#IMCount').html()==""?0:$('#IMCount').html();
        	//总数-
			$('#IMCount').html(parseFloat(IMCount)-c);	
			//当前聊天清零
			$('#'+countId).html("");
        			
            wrapperChat.addClass("page-quick-sidebar-content-item-shown");
        });

        
        //返回用户列表
        wrapper.find('.page-quick-sidebar-chat-user .page-quick-sidebar-back-to-list').click(function () {
//        	status=0;
        	currentMessageUser="";
            wrapperChat.removeClass("page-quick-sidebar-content-item-shown");
        });

        var handleChatMessagePost = function (e) {
            e.preventDefault();

            var chatContainer = wrapperChat.find(".page-quick-sidebar-chat-user-messages");
            
            //聊天输入框内容
            var input = wrapperChat.find('.page-quick-sidebar-chat-user-form .form-control');

            var text = input.val();
            if (text.length === 0) {
                return;
            }

//            var preparePost = function(dir, time, name, avatar, message) {
//                var tpl = '';
//                tpl += '<div class="post '+ dir +'">';
//                tpl += '<img class="avatar" alt="" src="' + Layout.getLayoutImgPath() + avatar +'.jpg"/>';
//                tpl += '<div class="message">';
//                tpl += '<span class="arrow"></span>';
//                tpl += '<a href="#" class="name">'+name+'</a>&nbsp;';
//                tpl += '<span class="datetime">' + time + '</span>';
//                tpl += '<span class="body">';
//                tpl += message;
//                tpl += '</span>';
//                tpl += '</div>';
//                tpl += '</div>';
//
//                return tpl;
//            };

            // handle post
            var time = new Date();
            var message = preparePost('out', (time.getHours() + ':' + time.getMinutes()), "gumin", 'avatar3', text);
            message = $(message);
            
            var countId=currentMessageUser.substring(0,currentMessageUser.indexOf('@'));
            chatContainer.find("#"+countId).append(message);
            
            //发送消息
            
            
            remote.jsjac.chat.sendMessage(text, currentMessageUser);
            

//            var getLastPostPos = function() {
//                var height = 0;
//                chatContainer.find(".post").each(function() {
//                    height = height + $(this).outerHeight();
//                });
//
//                return height;
//            };           

            chatContainer.slimScroll({
                scrollTo: getLastPostPos()
            });

            input.val("");
            
            
//            // simulate reply
//            setTimeout(function(){
//                var time = new Date();
//                var message = preparePost('in', (time.getHours() + ':' + time.getMinutes()), "Ella Wong", 'avatar2', 'Lorem ipsum doloriam nibh...');
//                message = $(message);
//                chatContainer.append(message);
//
//                chatContainer.slimScroll({
//                    scrollTo: getLastPostPos()
//                });
//            }, 3000);
        };

        wrapperChat.find('.page-quick-sidebar-chat-user-form .btn').click(handleChatMessagePost);
        wrapperChat.find('.page-quick-sidebar-chat-user-form .form-control').keypress(function (e) {
            if (e.which == 13) {
                handleChatMessagePost(e);
                return false;
            }
        });
    };

    // Handles quick sidebar tasks
    var handleQuickSidebarAlerts = function () {
        var wrapper = $('.page-quick-sidebar-wrapper');
        var wrapperAlerts = wrapper.find('.page-quick-sidebar-alerts');

        var initAlertsSlimScroll = function () {
            var alertList = wrapper.find('.page-quick-sidebar-alerts-list');
            var alertListHeight;

            alertListHeight = wrapper.height() - wrapper.find('.nav-justified > .nav-tabs').outerHeight();

            // alerts list 
            Metronic.destroySlimScroll(alertList);
            alertList.attr("data-height", alertListHeight);
            Metronic.initSlimScroll(alertList);
        };

        initAlertsSlimScroll();
        Metronic.addResizeHandler(initAlertsSlimScroll); // reinitialize on window resize
    };

    // Handles quick sidebar settings
    var handleQuickSidebarSettings = function () {
        var wrapper = $('.page-quick-sidebar-wrapper');
        var wrapperAlerts = wrapper.find('.page-quick-sidebar-settings');

        var initSettingsSlimScroll = function () {
            var settingsList = wrapper.find('.page-quick-sidebar-settings-list');
            var settingsListHeight;

            settingsListHeight = wrapper.height() - wrapper.find('.nav-justified > .nav-tabs').outerHeight();

            // alerts list 
            Metronic.destroySlimScroll(settingsList);
            settingsList.attr("data-height", settingsListHeight);
            Metronic.initSlimScroll(settingsList);
        };

        initSettingsSlimScroll();
        Metronic.addResizeHandler(initSettingsSlimScroll); // reinitialize on window resize
    };

    return {
    	stateChange:function(state){
    		stateChange(state);
    	},
    	initUserList:function(items){
    		initUserList(items);
    	},
    	getMessage:function(user,content){
    		getMessage(user,content);
    	},
        init: function () {
            //layout handlers
            handleQuickSidebarToggler(); // handles quick sidebar's toggler
            handleQuickSidebarChat(); // handles quick sidebar's chats
            handleQuickSidebarAlerts(); // handles quick sidebar's alerts
            handleQuickSidebarSettings(); // handles quick sidebar's setting
        }
    };

}();