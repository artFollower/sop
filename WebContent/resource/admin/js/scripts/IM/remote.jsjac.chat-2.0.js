/**
 * IM chat jsjac remote message
 * @author: hoojo
 * @email: hoojo_@126.com
 * @blog http://hoojo.cnblogs.com & http://blog.csdn.net/IBM_hoojo
 * @createDate: 2012-5-24
 * @version 2.0
 * @requires jQuery v1.2.3 or later
 * Copyright (c) 2012 M. hoo
 **/
 
var mUserName="";
var mPass="";

var iq;
var roster;
var usersHidden = false;

/* *** cascading onconnect handlers *** */
function getRoster(iq) {
	console.log(2);
 if (!iq || iq.getType() != 'result') {
   if (iq)
     console.log("Error fetching roster:\n"+iq.getDoc().xml,1);
   else
   	console.log("Error fetching roster",1);
   return;
 }
 
 console.log("got roster:\n"+iq.getDoc().xml,2);
 console.log(iq.getQuery());
 console.log(iq.getQuery().childNodes);
 
 QuickSidebar.initUserList(iq.getQuery().childNodes);
 
 
// roster = new Roster(iq.getQuery().childNodes,null);
// roster.usersHidden = usersHidden;
// roster.nick = jid.substring(0,jid.indexOf('@')); // remember nick for 1:1 Chats
 
 // get saved state
 iq = new JSJaCIQ();
 iq.setIQ(null,'get','jwchat_state');
 var query = iq.setQuery('jabber:iq:private');
 query.appendChild(iq.getDoc().createElement('jwchat')).setAttribute('xmlns','jwchat:state');
 remote.connection.send(iq,getSavedState);
}

function getSavedState(iq) {
	console.log(3);
 if (!iq || iq.getType() != 'result')
   if (iq)
   	console.log("Error retrieving saved state:\n"+iq.getDoc().xml,1);
   else
   	console.log("Error retrieving saved state",1);
 
 if (iq && iq.getType() == 'result') {
	  console.log(iq.getDoc().xml,3);
   var jNode = iq.getNode().getElementsByTagName('jwchat').item(0);
   for (var i=0; i<jNode.childNodes.length; i++) {
     var item = jNode.childNodes.item(i);
     if (item.nodeName == 'presence' && item.firstChild && onlstat == '' && 
     item.firstChild.nodeValue != 'offline')
	onlstat = item.firstChild.nodeValue;
     if (item.nodeName == 'onlmsg' && item.firstChild && onlmsg == '')
	onlmsg = item.firstChild.nodeValue;
     if (item.nodeName == 'hiddenGroups' && item.firstChild) {
	var hiddenGroups = item.firstChild.nodeValue.split(',');
	for (var j=0; j<hiddenGroups.length; j++)
	  if (hiddenGroups[j] != '')
	    roster.hiddenGroups[hiddenGroups[j]] = true;
     }
   }
 }
 
 // get prefs
 iq = new JSJaCIQ();
 iq.setIQ(null,'get','jwchat_prefs');
 var query = iq.setQuery('jabber:iq:private');
 query.appendChild(iq.getDoc().createElement('jwchat')).setAttribute('xmlns','jwchat:prefs');
 
 remote.connection.send(iq,getPrefs);
}

function getPrefs(iq) {
	console.log(4);
// if (!iq || iq.getType() != 'result')
//   if (iq)
//   	console.log("Error retrieving preferences:\n"+iq.getDoc().xml,1);
//   else
//   	console.log("Error retrieving preferences",1);
// 
// if (iq && iq.getType() == 'result') {
//   Debug.log(iq.getDoc().xml,3);
//   if (iq.getNode().getElementsByTagName('jwchat').item(0)) {
//     var jNode = iq.getNode().getElementsByTagName('jwchat').item(0);
//     for (var i=0; i<jNode.childNodes.length; i++) {
//	switch (jNode.childNodes.item(i).nodeName) {
//	case 'usersHidden':
//	  if (eval(jNode.childNodes.item(i).firstChild.nodeValue) != usersHidden)
//	    roster.toggleHide();
//	  break;
//	case 'timerval':
//	  timerval = eval(jNode.childNodes.item(i).firstChild.nodeValue);
//	  con.setPollInterval(timerval);
//	  break;
//	case 'autoPopup':
//	  autoPopup = eval(jNode.childNodes.item(i).firstChild.nodeValue);
//	  break;
//	case 'autoPopupAway':
//	  autoPopupAway = eval(jNode.childNodes.item(i).firstChild.nodeValue);
//	  break;
//	case 'playSounds':
//	  playSounds = eval(jNode.childNodes.item(i).firstChild.nodeValue);
//	  break;
//	case 'focusWindows':
//	  focusWindows = eval(jNode.childNodes.item(i).firstChild.nodeValue);
//	  break;
//	case 'timestamps':
//	  timestamps = eval(jNode.childNodes.item(i).firstChild.nodeValue);
//	  break;
//	case 'enableLog':
//	  enableLog = eval(jNode.childNodes.item(i).firstChild.nodeValue);
//	  break;
//	}
//     }
//   }
// }
// 
 // print roster
// console.log(roster.print());
// roster.print();
}



var remote = {
	debug: "info, error",
	chat: "body",
	receiver: "#to", // 接受者jquery expression
	console: {
		errorEL: function () {
			if ($(remote.chat).get(0)) {
				return $(remote.chat).find("#error");
			} else {
				return $("body").find("#error");
			}
		},
		infoEL: function () {
			if ($(remote.chat).get(0)) {
				return $(remote.chat).find("#info");
			} else {
				return $("body").find("#info");
			}
		},
		// debug info
		info: function (html) {
//			if (~remote.debug.indexOf("info")) {
//				remote.console.infoEL().append(html);
//				remote.console.infoEL().get(0).lastChild.scrollIntoView();
//			}
		},
		// debug error
		error: function (html) {
			if (~remote.debug.indexOf("error")) {
				remote.console.errorEL().append(html); 
			}
		},
		// clear info/debug console
		clear: function (s) {
			if ("debug" == s) {
				remote.console.errorEL().html("");
			} else {
				remote.console.infoEL().html("");
			}
		}
	},
	
	userAddress: function (user) {
		if (user) {
			if (!~user.indexOf("@")) {
				user += "@" + remote.jsjac.domain;// + "/" + remote.jsjac.resource;
			} else if (~user.indexOf("/")) {
				user = user.substr(0, user.indexOf("/"));
			}
		}
		return user;
	},
	//链接参数。
	jsjac: {
		httpbase: "/JHB/", //请求后台http-bind服务器url
		domain: "192.168.3.116", //"192.168.5.231", // 192.168.5.231 当前有效域名
		username: "",
		pass: "",
		timerval: 2000, // 设置请求超时
		resource: "WebIM", // 链接资源标识
		register: true // 是否注册
	}
};
remote.jsjac.chat = {
	writeReceiveMessage: function () {
	},
	setState: function () {
		var onlineStatus = new Object();
		onlineStatus["available"] = "在线";
		onlineStatus["chat"] = "欢迎聊天";
		onlineStatus["away"] = "离开";
		onlineStatus["xa"] = "不可用";
		onlineStatus["dnd"] = "请勿打扰";
		onlineStatus["invisible"] = "隐身";
		onlineStatus["unavailable"] = "离线";
		remote.jsjac.chat.state = onlineStatus;
		return onlineStatus;
	},
	state: null,
	init: function () {
		// Debugger plugin
		if (typeof (Debugger) == "function") {
			remote.dbger = new Debugger(2, remote.jsjac.resource);
			remote.dbger.start();
		} else {
	    	// if you're using firebug or safari, use this for debugging
	    	// oDbg = new JSJaCConsoleLogger(2);
	    	// comment in above and remove comments below if you don't need debugging
			remote.dbger = function () {
			};
			remote.dbger.log = function () {
			};
		}
		
//		try { 
//			// try to resume a session
//			if (JSJaCCookie.read("btype").getValue() == "binding") {
//				remote.connection = new JSJaCHttpBindingConnection({ "oDbg": remote.dbger});
//				rdbgerjac.chat.setupEvent(remote.connection);
//				if (remote.connection.resume()) {
//					remote.console.clear("debug");
//				}
//			} 
//		} catch (e) {
//			remote.console.errorEL().html(e.name + ":" + e.message);
//		} // reading cookie failed - never mind
		
		remote.jsjac.chat.setState();
	},
	login: function (username,pass,register) {
		
		console.log("username="+username+"-------pass="+pass);
		
		mUserName=username;
		mPass=pass;
		console.log($('basePath').context.baseURI);
		remote.console.clear("debug"); // reset
		try {
			// 链接参数
			var connectionConfig = remote.jsjac;
			
			// Debugger console
			if (typeof (oDbg) != "undefined") {
				connectionConfig.oDbg = oDbg;
			}
			var connection = new JSJaCHttpBindingConnection(connectionConfig);
			remote.connection = connection;
			// 安装（注册）Connection事件模型
			remote.jsjac.chat.setupEvent(connection);
	
	    	// setup args for connect method
				//connectionConfig = new Object();
				//connectionConfig.domain = loginForm.domain.value;
				connectionConfig.username = username;
				connectionConfig.pass = pass
				connectionConfig.register = register;
			// 连接服务器
			connection.connect(connectionConfig);
			console.log(1);
			remote.jsjac.chat.changeStatus("available", "online", 1, "chat");
		} catch (e) {
			console.log(2);
			remote.console.errorEL().html(e.toString());
		}
	},
	// 改变用户状态
	changeStatus: function (type, status, priority, show) {
		type = type || "unavailable";
		status = status || "online";
		priority = priority || "1";
		show = show || "chat";
		var presence = new JSJaCPresence();
		presence.setType(type); // unavailable invisible
		if (remote.connection) {
			//remote.connection.send(presence);
		}
		
		//presence = new JSJaCPresence();
		presence.setStatus(status); // online
		presence.setPriority(priority); // 1
		presence.setShow(show); // chat
		if (remote.connection) {
			remote.connection.send(presence);
		}
	},
	
	// 为Connection注册事件
	setupEvent: function (con) {
		var remoteChat = remote.jsjac.chat;
	    con.registerHandler('message', remoteChat.handleMessage);
	    con.registerHandler('presence', remoteChat.handlePresence);
	    con.registerHandler('iq', remoteChat.handleIQ);
	    con.registerHandler('onconnect', remoteChat.handleConnected);
	    con.registerHandler('onerror', remoteChat.handleError);
	    con.registerHandler('status_changed', remoteChat.handleStatusChanged);
	    con.registerHandler('ondisconnect', remoteChat.handleDisconnected);
	
	    con.registerIQGet('query', NS_VERSION, remoteChat.handleIqVersion);
	    con.registerIQGet('query', NS_TIME, remoteChat.handleIqTime);
	},
	// 发送远程消息
	sendMessage: function (msg, to) {
		try {
			if (msg == "") {
				return false;
			}
			var user = "";
			if (to) {
				if (!~to.indexOf("@")) {
					user += "@" + remote.jsjac.domain;
					to += "/" + remote.jsjac.resource;
				} else if (~to.indexOf("/")) {
					user = to.substr(0, to.indexOf("/"));
				}
			} else {
				// 向chat接收信息区域写消息
				if (remote.jsjac.chat.writeReceiveMessage) {
					var html = "你没有指定发送者的名称";
					alert(html);
					//remote.jsjac.chat.writeReceiveMessage(receiverId, "server", html, false);
				}
				return false;
			}
			var userJID = "u" + hex_md5(user);
//			$("#" + userJID).find(remote.receiver).val(to);
			// 构建jsjac的message对象
			var message = new JSJaCMessage();
//			message.setTo(new JSJaCJID(to));
			
			to=to.substring(0,to.indexOf("@"));
			to=to+"@dadi-pc";
			
			message.setTo(to);
			message.setType("chat"); // 单独聊天，默认为广播模式
			message.setBody(msg);
			// 发送消息
			console.log(message);
			remote.connection.send(message);
			return false;
		} catch (e) {
			var html = "<div class='msg error''>Error: " + e.message + "</div>";
			remote.console.info(html);
			return false;
		}
	},
	// 退出、断开链接
	logout: function () {
		var presence = new JSJaCPresence();
		presence.setType("unavailable");
		if (remote.connection) {
			remote.connection.send(presence);
			remote.connection.disconnect();
		}
	},
	errorHandler: function (event) {
		console.log("errorHandler");
		var e = event || window.event;
		remote.console.errorEL().html(e);
		if (remote.connection && remote.connection.connected()) {
			remote.connection.disconnect();
		}
		return false;
	},
	unloadHandler: function () {
		console.log("unloadHandler");
		var con = remote.connection;
		if (typeof con != "undefined" && con && con.connected()) {
	  		// save backend type
			if (con._hold) { // must be binding
				(new JSJaCCookie("btype", "binding")).write();
			} 
			if (con.suspend) {
				con.suspend();
			}
		}
	},
	writeMessage: function (userJID, userName, content) {
		console.log("writeMessage");
		// 向chat接收信息区域写消息
		if (remote.jsjac.chat.writeReceiveMessage && !!content) {
			remote.jsjac.chat.writeReceiveMessage(userJID, userName, content, false);
		}
	},
	// 重新连接服务器
	reconnection: function () {
		remote.jsjac.register = false;
		if (remote.connection.connected()) {
			remote.connection.disconnect();
		}
		remote.jsjac.chat.login(mUserName,mPass,false);
	},
	/* ########################### Handler Event ############################# */
	
	handleIQ: function (aIQ) {
		console.log("handleIQ");
//		var html = "<div class='msg'>IN (raw): " + aIQ.xml().htmlEnc() + "</div>";
//		remote.console.info(html);
//		QuickSidebar.getRoster();
		remote.connection.send(aIQ.errorReply(ERR_FEATURE_NOT_IMPLEMENTED));
	},
	handleMessage: function (aJSJaCPacket) {
		console.log("handleMessage");
		
		var user = aJSJaCPacket.getFromJID().toString();
		//var userName = user.split("@")[0];
		//var userJID = "u" + hex_md5(user);
		var content = aJSJaCPacket.getBody();
		
		console.log("消息来自---"+user+"&&&&内容---"+content);
		
		QuickSidebar.getMessage(user,content);
		
//		var html = "";
//		html += "<div class=\"msg\"><b>消息来自 " + user + ":</b><br/>";
//		html += content.htmlEnc() + "</div>";
//		remote.console.info(html);
//		
//		$.WebIM.messageHandler(user, content);
	},
	handlePresence: function (aJSJaCPacket) {
		console.log("handlePresence");
		
		var user = aJSJaCPacket.getFromJID();
		var userName = user.toString().split("@")[0];
		var html = "<div class=\"msg\">";
		if (!aJSJaCPacket.getType() && !aJSJaCPacket.getShow()) {
			html += "<b>" + userName + " 上线了.</b>";
		} else {
			html += "<b>" + userName + " 设置 presence 为： ";
			if (aJSJaCPacket.getType()) {
				html += aJSJaCPacket.getType() + ".</b>";
			} else {
				html += aJSJaCPacket.getShow() + ".</b>";
			}
			if (aJSJaCPacket.getStatus()) {
				html += " (" + aJSJaCPacket.getStatus().htmlEnc() + ")";
			}
		}
		html += "</div>";
		remote.console.info(html);
		
		// 向chat接收信息区域写消息
		remote.jsjac.chat.writeMessage("", userName, html);
	},
	handleError: function (event) {
		console.log("handleError");
		var e = event || window.event;
		
		console.log(e.getAttribute("code"));
		
//		var html = "An error occured:<br />" 
//			+ ("Code: " + e.getAttribute("code") 
//			+ "\nType: " + e.getAttribute("type") 
//			+ "\nCondition: " + e.firstChild.nodeName).htmlEnc();
//		remote.error(html);
		
		var content = "";
		switch (e.getAttribute("code")) {
			case "401":
				console.log("401");
				content = "登陆验证失败！";
				break;
			// 当注册发现重复，表明该用户已经注册，那么直接进行登陆操作			
			case "409":
				console.log("409");
				//content = "注册失败！\n\n请换一个用户名！";
				remote.jsjac.chat.reconnection();
//				remote.jsjac.register = false;
//				if (remote.connection.connected()) {
//					remote.connection.disconnect();
//				}
//				remote.jsjac.chat.login(mUserName,mPass,false);
				
				break;
			case "503":
				console.log("503");
				content = "无法连接到IM服务器，请检查相关配置！";
				break;
			case "500":
				console.log("500");
				var contents = "服务器内部错误！\n\n连接断开！<br/><a href='javascript: self.parent.remote.jsjac.chat.reconnection();'>重新连接</a>";
				remote.jsjac.chat.writeMessage("", "系统", contents);
				break;
			default:
				break;
		}
		if (content) {
			alert("WeIM: " + content);
		}
		if (remote.connection.connected()) {
			remote.connection.disconnect();
		}
	},
	// 状态变化触发事件
	handleStatusChanged: function (status) {
		console.log("handleStatusChanged-----"+status);
		var mState="";
		if(status=="connecting"){
			mState="登录中";
		}
		else if(status=="processing"){
			mState="在线";
		}
		else if (status == "disconnecting") {
			mState="离线";
		}
		QuickSidebar.stateChange(mState);
		
//		remote.console.info("<div>当前用户状态: " + status + "</div>");
//		remote.dbger.log("当前用户状态: " + status);
//		if (status == "disconnecting") {
//			var html = "<b style='color:red;'>你离线了！</b>";
//			// 向chat接收信息区域写消息
//			remote.jsjac.chat.writeMessage("", "系统", html);
//		}
	},
	// 建立链接触发事件方法
	handleConnected: function () {
		console.log("handleConnected");
		remote.console.clear("debug"); // reset
		//发送给服务器告知登陆成功，服务器更新状态，若不执行，服务器就显示脱机
		remote.connection.send(new JSJaCPresence());
		
		 iq = new JSJaCIQ();
		  iq.setIQ(null,'get','roster_1');
		  iq.setQuery('jabber:iq:roster');
		  remote.connection.send(iq,getRoster); // cascading information retrieval
		
//		QuickSidebar.getRoster();
	},
	// 断开链接触发事件方法
	handleDisconnected: function () {
		console.log("handleDisconnected");
	},
	handleIqVersion: function (iq) {
		console.log("handleIqVersion");
		remote.connection.send(iq.reply([
			iq.buildNode("name", remote.jsjac.resource), 
			iq.buildNode("version", JSJaC.Version), 
			iq.buildNode("os", navigator.userAgent)
		]));
		return true;
	},
	handleIqTime: function (iq) {
		console.log("handleIqTime");
		var now = new Date();
		remote.connection.send(iq.reply([
			iq.buildNode("display", now.toLocaleString()), 
			iq.buildNode("utc", now.jabberDate()), 
			iq.buildNode("tz", now.toLocaleString().substring(now.toLocaleString().lastIndexOf(" ") + 1))
		]));
		return true;
	}
};