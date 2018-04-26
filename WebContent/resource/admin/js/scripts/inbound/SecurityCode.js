//验证码
var  SecurityCode=function(){
	var timmer=new Object();
  function init(obj){// code  权限
  var status=$(obj).attr("data");
	if(status==0){
	$(obj).attr("data",1).attr("disabled","disabled");
	var key=$(obj).attr("code");
	var userId=$(obj).closest("div").find("#reviewCodeUserId").attr("data");
	var msg=$(obj).closest("div").find("#securityCodeContent").text();
	//工艺，品质，直接
	if($(obj).closest("div").find("#verigy")){
		$(obj).closest("div").find("#verigy").click(function(){
			$(obj).attr("code",$(this).val());
		});
	}
	
	if(util.isNull(userId,1)!=0){
	doSendSecurityCode(obj,msg,key,userId);
	initTimmer(60,function callback(item){
		$(obj).text(item+"秒");
		if(item==60)
		$(obj).attr("disabled","disabled");
	},function setVal(){
		$(obj).text("再次发送").attr("data",0).removeAttr("disabled");
	});	
	} 
	}else{
		$("body").message({
            type: 'warning',
            content: "请填写审核人"
        });
	}
  };	
  //生成和发送验证码
  function doSendSecurityCode(obj,msg,code,userId){
	  config.load();
	  $.ajax({
		  type:"post",
		  url:config.getDomain()+"/securityCode/send",
		  data:{key:code,userId:userId,content:msg},
		  dataType:'json',
		  success:function(data){
			util.ajaxResult(data,"发送",function(){
				clearTimmer(obj);
			});
		  }
	  });
  };
  function initTimmer(count,callback,setVal){
	   var securityCodeTimmer=setInterval(function(){
			  if(count>0){
				  callback(count--);
			  }else{
				  clearInterval(timmer.securityCodeTimmer);   
				  if(setVal)
					  setVal();
			  }
		  },1000);     
	   timmer.securityCodeTimmer=securityCodeTimmer;  
  }
  function clearTimmer(obj){
	  clearInterval(timmer.securityCodeTimmer);
	  $(obj).text("再次发送").attr("data",0).removeAttr("disabled"); 
  }
  function verigy(obj){
	  $(obj).closest("div").find("#securityCodeBtn").attr("code",$(obj).val()); 
  }
	return{
		init:init,
		timmer:timmer,
		verigy:verigy
	}
}();