var SendMsg=function(){
	var timmer=new Object();
	function sendMsg(obj){
		var key=$(obj).attr("code");
		var msg=$(obj).closest(".amountAffirm").find("#securityCodeContent").text();
		
		doSendMsg(obj,key,msg);
		initTimmer(60,function callback(item){
			$(obj).text(item+"秒");
			if(item==60)
			$(obj).attr("disabled","disabled");
		},function setVal(){
			$(obj).text("再次通知").attr("data",0).removeAttr("disabled");
		});
		
	};
	
	function doSendMsg(obj,key,msg){
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+'/securityCode/sendMsg',
			dataType:'json',
			data:{key:key,content:msg},
			success:function(data){
				util.ajaxResult(data,"通知",function(){
					clearTimmer(obj);
				});
			}
		});
		
	};
	
	function initTimmer(count,callback,setVal){
		   var sendMsgTimmer=setInterval(function(){
				  if(count>0){
					  callback(count--);
				  }else{
					  clearInterval(timmer.sendMsgTimmer);   
					  if(setVal)
						  setVal();
				  }
			  },1000);     
		   timmer.sendMsgTimmer=sendMsgTimmer;  
	  }
	  function clearTimmer(obj){
		  clearInterval(timmer.sendMsgTimmer);
		  $(obj).text("再次通知").attr("data",0).removeAttr("disabled"); 
	  }
return{
	sendMsg:sendMsg
	
}	
	
}();