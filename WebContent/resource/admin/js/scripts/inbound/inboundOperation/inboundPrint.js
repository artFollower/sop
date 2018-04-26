var InboundPrint=function(){
	var LODOP; //声明为全局变量 
	var CreatedOKLodop7766=null;//打印控件
	
	function print(obj,id){
		var arrivalId=$(obj).attr('data');
		var cargoId=(id?id:undefined);
		config.load();
		$.ajax({
			type:'post',
			url:config.getDomain()+'/inboundoperation/getPrintList',
			dataType:'json',
			data:{id:arrivalId,cargoId:cargoId},
			success:function(data){
				util.ajaxResult(data,"打印",function(ndata){
					if(ndata&&ndata.length>0){
						CreatePrintPage(ndata);
						console.log(ndata);
					}else{
						$("body").message({type : 'error',content : '没有可打印的入库信息'});
					}
				});
			}
		});
		
	};
/**************************************************************/	
	 function CreatePrintPage(objlist){
	    LODOP=getLodop();  
	    for(var i = 0 ;i<objlist.length ;i++){
//	    	LODOP.PRINT_INIT("");	
			if(i == (objlist.length-1))
			    LODOP.SET_PRINT_PAGESIZE(0,"831px","550px","");
			else
				LODOP.SET_PRINT_PAGESIZE(0,"831px","350px","");
			printKp(objlist[i]);
			LODOP.PRINT();
	    }
	  };
/**************************************************************/	  
	//打印成单子 
		function printKp(obj)
		  {
			//距上边 110 象素，左边 33 像素，宽 750象素，高 900 象素
		    var nowTime=util.isNull(obj.reviewTime);
		    LODOP.ADD_PRINT_TEXT(73,102,200,30,util.isNull(obj.cargoCode));
		    LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		    
		    LODOP.ADD_PRINT_TEXT(100,130,178,30,util.isNull(obj.clientName));
		    LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		    if(nowTime){
		    LODOP.ADD_PRINT_TEXT(73,490,80,30,nowTime.substring(0,4));
		    LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		    LODOP.ADD_PRINT_TEXT(73,550,40,30,nowTime.substring(5,7));
		    LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		    LODOP.ADD_PRINT_TEXT(73,602,40,30,nowTime.substring(8,10));
		    LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		    }
		    LODOP.ADD_PRINT_TEXT(100,345,186,30,util.isNull(obj.shipName));
		    LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		    LODOP.ADD_PRINT_TEXT(110,580,178,30,util.isNull(obj.productName));
		    LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		   
		    LODOP.ADD_PRINT_TEXT(150,119,178,30,util.isNull(obj.tankName));
		    LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		    
		    LODOP.ADD_PRINT_TEXT(145,460,80,30,util.isNull(obj.arrivalTime).substring(0,4));
		    LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		    LODOP.ADD_PRINT_TEXT(145,520,40,30,util.isNull(obj.arrivalTime).substring(5,7));
		    LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		    LODOP.ADD_PRINT_TEXT(145,582,40,30,util.isNull(obj.arrivalTime).substring(8,10));
		    LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		    
		    LODOP.ADD_PRINT_TEXT(230,185,164,30,util.toDecimal3(obj.goodsTank));
		    LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
		  };	  
/**************************************************************/	
	function getLodop(oOBJECT,oEMBED){
		/**************************
		  本函数根据浏览器类型决定采用哪个页面元素作为Lodop对象：
		  IE系列、IE内核系列的浏览器采用oOBJECT，
		  其它浏览器(Firefox系列、Chrome系列、Opera系列、Safari系列等)采用oEMBED,
		  如果页面没有相关对象元素，则新建一个或使用上次那个,避免重复生成。
		  64位浏览器指向64位的安装程序install_lodop64.exe。
		**************************/
			var strHtmInstall="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='"+config.getDomain()+"/resource/plugins/install_lodop32.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
	        var strHtmUpdate="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='"+config.getDomain()+"/resource/plugins/install_lodop32.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
	        var strHtm64_Install="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='"+config.getDomain()+"/resource/plugins/install_lodop64.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
	        var strHtm64_Update="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='"+config.getDomain()+"/resource/plugins/install_lodop64.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
	        var strHtmFireFox="<br><br><font color='#FF00FF'>（注意：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它）</font>";
	        var strHtmChrome="<br><br><font color='#FF00FF'>(如果此前正常，仅因浏览器升级或重安装而出问题，需重新执行以上安装）</font>";
	        var LODOP;		
			try{	
			     //=====判断浏览器类型:===============
			     var isIE	 = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
			     var is64IE  = isIE && (navigator.userAgent.indexOf('x64')>=0);
			     //=====如果页面有Lodop就直接使用，没有则新建:==========
			     if (oOBJECT!=undefined || oEMBED!=undefined) { 
		               	 if (isIE) 
			             LODOP=oOBJECT; 
			         else 
			             LODOP=oEMBED;
			     } else { 
				 if (CreatedOKLodop7766==null){
		          	     LODOP=document.createElement("object"); 
				     LODOP.setAttribute("width",0); 
		                     LODOP.setAttribute("height",0); 
				     LODOP.setAttribute("style","position:absolute;left:0px;top:-100px;width:0px;height:0px;");  		     
		                     if (isIE) LODOP.setAttribute("classid","clsid:2105C259-1E0C-4534-8141-A753534CB4CA"); 
				     else LODOP.setAttribute("type","application/x-print-lodop");
				     document.documentElement.appendChild(LODOP); 
			             CreatedOKLodop7766=LODOP;		     
		 	         } else 
		                     LODOP=CreatedOKLodop7766;
			     };
			     //=====判断Lodop插件是否安装过，没有安装或版本过低就提示下载安装:==========
			     if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
			    	 $.get(config.getResource()+"/plugins/addplugins.jsp").done(function(data){
				 			dialog = $(data) ;
				 			if (navigator.userAgent.indexOf('Chrome')>=0)
				 				 dialog.find(".plugins").append(strHtmChrome) ;
				             if (navigator.userAgent.indexOf('Firefox')>=0)
				            	 dialog.find(".plugins").append(strHtmFireFox) ;
				             if (is64IE) document.write(dialog.find(".plugins").append(strHtm64_Install)); else
				             if (isIE)   document.write(dialog.find(".plugins").append(strHtmInstall));    else
				                 dialog.find(".plugins").append(strHtmInstall) ;
				 			dialog.modal({
				 				keyboard: true
				 			});
				 		});
			     } else 
			     if (LODOP.VERSION<"6.1.9.6") {
			             if (is64IE) document.write(strHtm64_Update); else
			             if (isIE) document.write(strHtmUpdate); else
			             document.documentElement.innerHTML=strHtmUpdate+document.documentElement.innerHTML;
			    	     return LODOP;
			     };
			     //=====如下空白位置适合调用统一功能(如注册码、语言选择等):====	     

			     LODOP.SET_LICENSES("昆明易极信息技术有限公司","055716580837383919278901905623","","");
			     //============================================================	     
			     return LODOP; 
			} catch(err) {
			     if (is64IE)	
		            document.documentElement.innerHTML="Error:"+strHtm64_Install+document.documentElement.innerHTML;else
		            document.documentElement.innerHTML="Error:"+strHtmInstall+document.documentElement.innerHTML;
			     return LODOP; 
			};
		}
	return {
		print:print
	}
}();