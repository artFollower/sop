var NotifyList=function(){
	
	function listGrid(type){
		initSearchCtrShowOrHide(type);
		$("#type").val(type);
		if ($('[data-role="notifyGrid"]').getGrid() != null)
			$('[data-role="notifyGrid"]').getGrid().destory();	
		$('[data-role="notifyGrid"]').grid({
			identity : 'id',
			columns : getTableColumns(type),
			isShowIndexCol : true,
			isShowPages : true,
			searchCondition : getSearchCondition(type),
			gridName:"notifyListgrid"+type,
			stateSave:true,
			url : config.getDomain() + "/notify/list",
			callback:function(){
				initTableGridCallBack(type);
			}
		});
		
	};
	
	function initSearchCtrShowOrHide(type){
		
		if(type!=-1){
			 $(".notifyButtons").show();
			 $("#tab"+parseFloat(util.FloatAdd(10,type))).parent().addClass("active").siblings().removeClass("active");//改变点击效果
			 $('[name="types"]').val(type);
		if(type.length>2){
			 $(".notifyAdd").hide();
		}else if(type==2||type==3||type==11||type==12||type==13||type==14||type==15||type==16){
			 $(".notifyAdd,.notifyRemove").hide();
			 $(".notifyModify").show();
		}else if(type==17){
			$("#tab27").parent().addClass("active").siblings().removeClass("active");//改变点击效果
			 $(".notifyModify").hide();
			 $(".notifyAdd,.notifyRemove").show();
		}else{
			$(".notifyRemove,.notifyAdd").show();
		}
	}}
	//use
	function getSearchCondition(type){
		var params={'isList':1,'types':type};
		 $("#notifyListForm").find('.form-control').each(function(){
          var $this = $(this);
         	 var name=$this.attr('name');
         	 if(name&&$this.val()!=""){
         		 if(name.indexOf("Time")!=-1){
         			params[name]=util.formatLong($this.val()); 
         		 }else{
         		 params[name]=$this.val();
         		 }
         	 }else{
         		 params[name]=null;
         	 }
      });
		 return params;
	};
	
	
	function initTableGridCallBack(type){
		
	};
	
	function getTableColumns(type){
	
 		switch(type){
		case '0'://配管作业通知单
			return [{title:'编号',render:function(item){ 
				return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
				+ item.code.substring(1, item.code.length)+ "</a>";}}
	         ,{title:'作业要求',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskRequire;
	        	 else return "";}}
	         ,{title : "创建时间",render : function(item) {
					return util.getSubTime(item.createTime, 1);}}
	         ,{title : "创建人",name : "createUserName"}
	         ,{title : "作业前",name : "strn"}
	         ,{title : "作业后",name : "endn"}
	         ,{title : "状态",render : function(item) {
					return getStatusStr(item.state);
	         }}];
		case '1'://苯加热作业通知单
			return [{title:'编号',render:function(item){ 
				return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
				+ item.code.substring(1, item.code.length)+ "</a>";}}
			 ,{title:'作业任务',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskMsg;
	        	 else return "";}}
			 ,{title:'罐号',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.tankId;
	        	 else return "";}}
			 ,{title:'管线',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.tubeId;
	        	 else return "";}}
	         ,{title : "创建时间",render : function(item) {
					return util.getSubTime(item.createTime, 1);}}
	         ,{title : "创建人",name : "createUserName"}
	         ,{title : "作业前",name : "strn"}
	         ,{title : "作业后",name : "endn"}
	         ,{title : "状态",render : function(item) {
					return getStatusStr(item.state);
	         }}];
		case '2'://打循环作业通知单（码头）
			return [{title:'编号',render:function(item){ 
			return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
			+ item.code.substring(1, item.code.length)+ "</a>";}}
          /*,{title:'作业任务',render:function(item){
        	 var msg=handleSVGJSON(item.content,type);
        	 if(msg) return msg.taskMsg;
        	 else return "";}}*/
          ,{title:'船名',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 var shipname=handleSVGJSONship(msg.taskMsg);
	        	 if(shipname) return shipname;
	        	 else return "";}}
         ,{title:'货品',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 var Good=handleSVGJSONGood(msg.taskMsg);
	        	 if(Good) return Good;
	        	 else return "";}}
         ,{title:'数量（吨）',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 var num=handleSVGJSONnum(msg.taskMsg);
	        	 if(num) return num;
	        	 else return "";}}
         ,{title : "泊位",render:function(item){
        	 var bw=handleSVGJSONbw(item.content);
        	 if(bw.length>=4) return bw;
        	 else return "";}}
         ,{title : "创建时间",render : function(item) {
				return util.getSubTime(item.createTime, 1);}}
         //,{title : "船信",name : "shipInfo"}
         ,{title : "创建人",name : "createUserName"}
         ,{title : "作业前",name : "strn"}
         ,{title : "作业后",name : "endn"}
         ,{title : "状态",render : function(item) {
				return getStatusStr(item.state);
         }}];
		case '3'://打循环作业通知单（库区）
			return [{title:'编号',render:function(item){ 
				return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
				+ item.code.substring(1, item.code.length)+ "</a>";}}
	         /* ,{title:'作业任务',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskMsg;
	        	 else return "";}}*/
	          ,{title:'船名',render:function(item){
		        	 var msg=handleSVGJSON(item.content,type);
		        	 var shipname=handleSVGJSONship(msg.taskMsg);
		        	 if(shipname) return shipname;
		        	 else return "";}}
	          ,{title:'货品',render:function(item){
		        	 var msg=handleSVGJSON(item.content,type);
		        	 var Good=handleSVGJSONGood(msg.taskMsg);
		        	 if(Good) return Good;
		        	 else return "";}}
	          ,{title:'数量（吨）',render:function(item){
		        	 var msg=handleSVGJSON(item.content,type);
		        	 var num=handleSVGJSONnum(msg.taskMsg);
		        	 if(num) return num;
		        	 else return "";}}
	          ,{title : "泊位",render:function(item){
		        	 var bw=handleSVGJSONbw(item.content);
		        	 if(bw.length>=4) return bw;
		        	 else return "";}}
	         ,{title : "创建时间",render : function(item) {
					return util.getSubTime(item.createTime, 1);}}
	         //,{title : "船信",name : "shipInfo"}
	         ,{title : "创建人",name : "createUserName"}
	         ,{title : "作业前",name : "strn"}
	         ,{title : "作业后",name : "endn"}
	         ,{title : "状态",render : function(item) {
					return getStatusStr(item.state);
	         }}];
		case '4'://管线清洗作业通知单（码头）
			return [{title:'编号',render:function(item){ 
				return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
				+ item.code.substring(1, item.code.length)+ "</a>";}}
	         ,{title:'作业要求',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskRequire;
	        	 else return "";}}
	         ,{title:'管线号',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.tubeId;
	        	 else return "";}}
	         ,{title:'物料名',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.productId;
	        	 else return "";}}
	         ,{title : "创建时间",render : function(item) {
					return util.getSubTime(item.createTime, 1);}}
	         ,{title : "创建人",name : "createUserName"}
	         ,{title : "作业前",name : "strn"}
	         ,{title : "作业后",name : "endn"}
	         ,{title : "状态",render : function(item) {
					return getStatusStr(item.state);
	         }}];
		case '5'://管线清洗作业通知单（库区）
			return [{title:'编号',render:function(item){ 
				return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
				+ item.code.substring(1, item.code.length)+ "</a>";}}
	         ,{title:'作业要求',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskRequire;
	        	 else return "";}}
	         ,{title:'管线号',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.tubeId;
	        	 else return "";}}
	         ,{title:'物料名',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.productId;
	        	 else return "";}}
	         ,{title : "创建时间",render : function(item) {
					return util.getSubTime(item.createTime, 1);}}
	         ,{title : "创建人",name : "createUserName"}
	         ,{title : "作业前",name : "strn"}
	         ,{title : "作业后",name : "endn"}
	         ,{title : "状态",render : function(item) {
					return getStatusStr(item.state);
	         }}];
		case '6'://扫线作业通知单（码头）
			return [{title:'编号',render:function(item){ 
				return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
				+ item.code.substring(1, item.code.length)+ "</a>";}}
	         ,{title:'作业任务',render:function(item){
            	 var msg=handleSVGJSON(item.content,type);
    	         if(msg) return msg.taskMsg;
    	         else return "";}}
	         ,{title:'作业要求',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskRequire;
	        	 else return "";}}
	         ,{title : "创建时间",render : function(item) {
					return util.getSubTime(item.createTime, 1);}}
	         ,{title : "创建人",name : "createUserName"}
	         ,{title : "作业前",name : "strn"}
	         ,{title : "作业后",name : "endn"}
	         ,{title : "状态",render : function(item) {
					return getStatusStr(item.state);
	         }}];
		case '7'://扫线作业通知单（库区）
			return [{title:'编号',render:function(item){ 
				return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
				+ item.code.substring(1, item.code.length)+ "</a>";}}
			 ,{title:'作业任务',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskMsg;
	        	 else return "";}}
	         ,{title:'作业要求',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskRequire;
	        	 else return "";}}
	         ,{title : "创建时间",render : function(item) {
					return util.getSubTime(item.createTime, 1);}}
	         ,{title : "创建人",name : "createUserName"}
	         ,{title : "作业前",name : "strn"}
	         ,{title : "作业后",name : "endn"}
	         ,{title : "状态",render : function(item) {
					return getStatusStr(item.state);
	         }}];
		case '8'://清罐作业通知单
			return [{title:'编号',render:function(item){ 
				return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
				+ item.code.substring(1, item.code.length)+ "</a>";}}
	         ,{title:'作业要求',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskRequire;
	        	 else return "";}}
	         ,{title:'罐号',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.tankId;
	        	 else return "";}}
	         ,{title:'物料名称',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.productId;
	        	 else return "";}}
	         ,{title:'数量',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.goodsNum;
	        	 else return "";}}
	         ,{title:'泵号',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.pupmId;
	        	 else return "";}}
	         ,{title : "创建时间",render : function(item) {
					return util.getSubTime(item.createTime, 1);}}
	         ,{title : "创建人",name : "createUserName"}
	         ,{title : "作业前",name : "strn"}
	         ,{title : "作业后",name : "endn"}
	         ,{title : "状态",render : function(item) {
					return getStatusStr(item.state);
	         }}];
		case '9'://储罐放水通知单
			return [{title:'编号',render:function(item){ 
				return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
				+ item.code.substring(1, item.code.length)+ "</a>";}}
	         ,{title:"作业任务",render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskMsg;
	        	 else return "";}}
	         ,{title : "创建时间",render : function(item) {
					return util.getSubTime(item.createTime, 1);}}
	         ,{title : "创建人",name : "createUserName"}
	         ,{title : "作业前",name : "strn"}
	         ,{title : "作业后",name : "endn"}
	         ,{title : "状态",render : function(item) {
					return getStatusStr(item.state);
	         }}];
		case '10'://储罐开人孔作业通知单
			return [{title:'编号',render:function(item){ 
				return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
				+ item.code.substring(1, item.code.length)+ "</a>";}}
	         ,{title:'作业任务',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskMsg;
	        	 else return "";}}
	         ,{title:'罐号',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.tankId;
	        	 else return "";}}
	         ,{title:'物料名称',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.productId;
	        	 else return "";}}
	         ,{title : "创建时间",render : function(item) {
					return util.getSubTime(item.createTime, 1);}}
	         ,{title : "创建人",name : "createUserName"}
	         ,{title : "作业前",name : "strn"}
	         ,{title : "作业后",name : "endn"}
	         ,{title : "状态",render : function(item) {
					return getStatusStr(item.state);
	         }}];
		case '11'://专输作业通知单
			return [{title:'编号',render:function(item){ 
			return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
			+ item.code.substring(1, item.code.length)+ "</a>";}}
         ,{title:'作业任务',render:function(item){
        	 var msg=handleSVGJSON(item.content,type);
        	 if(msg) return msg.taskMsg;
        	 else return "";}}
         ,{title:'作业要求',render:function(item){
        	 var msg=handleSVGJSON(item.content,type);
        	 if(msg) return msg.taskRequire;
        	 else return "";}}
         ,{title:'物料名称',render:function(item){
        	 var msg=handleSVGJSON(item.content,type);
        	 if(msg) return msg.productId;
        	 else return "";}}
         ,{title : "创建时间",render : function(item) {
				return util.getSubTime(item.createTime, 1);}}
         //,{title : "船信",name : "shipInfo"}
         ,{title : "创建人",name : "createUserName"}
         ,{title : "作业前",name : "strn"}
         ,{title : "作业后",name : "endn"}
         ,{title : "状态",render : function(item) {
				return getStatusStr(item.state);
         }}];
		case '12'://倒罐作业通知单
			return [{title:'编号',render:function(item){ 
				return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
				+ item.code.substring(1, item.code.length)+ "</a>";}}
	         ,{title:'作业任务',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskMsg;
	        	 else return "";}}
	         ,{title:'作业要求',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskRequire;
	        	 else return "";}}
	         ,{title : "创建时间",render : function(item) {
					return util.getSubTime(item.createTime, 1);}}
	         ,{title : "创建人",name : "createUserName"}
	         ,{title : "作业前",name : "strn"}
	         ,{title : "作业后",name : "endn"}
	         ,{title : "状态",render : function(item) {
					return getStatusStr(item.state);
	         }}];
		case '13'://接卸作业通知单（码头）
			return [{title:'编号',render:function(item){ 
			return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
			+ item.code.substring(1, item.code.length)+ "</a>";}}
			/* ,{title:'作业任务',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskMsg;
	        	 else return "";}}*/
			 ,{title:'船名',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 var shipname=handleSVGJSONship(msg.taskMsg);
	        	 if(shipname) return shipname;
	        	 else return "";}}
          ,{title:'货品',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 var Good=handleSVGJSONGood(msg.taskMsg);
	        	 if(Good) return Good;
	        	 else return "";}}
          ,{title:'数量（吨）',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 var num=handleSVGJSONnum(msg.taskMsg);
	        	 if(num) return num;
	        	 else return "";}}
			 ,{title:'作业要求',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskRequire;
	        	 else return "";}}
			 ,{title : "泊位",render:function(item){
	        	 var bw=handleSVGJSONbw(item.content);
	        	 if(bw.length>=4) return bw;
	        	 else return "";}}
         ,{title : "创建时间",render : function(item) {
				return util.getSubTime(item.createTime, 1);}}
         //,{title : "船信",name : "shipInfo"}
         ,{title : "创建人",name : "createUserName"}
         ,{title : "作业前",name : "strn"}
         ,{title : "作业后",name : "endn"}
         ,{title : "状态",render : function(item) {
				return getStatusStr(item.state);
         }}];
		case '14'://接卸作业通知单（库区）
			return [{title:'编号',render:function(item){ 
			return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
			+ item.code.substring(1, item.code.length)+ "</a>";}}
			/* ,{title:'作业任务',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskMsg;
	        	 else return "";}}*/
			 ,{title:'船名',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 var shipname=handleSVGJSONship(msg.taskMsg);
	        	 if(shipname) return shipname;
	        	 else return "";}}
          ,{title:'货品',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 var Good=handleSVGJSONGood(msg.taskMsg);
	        	 if(Good) return Good;
	        	 else return "";}}
          ,{title:'数量（吨）',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 var num=handleSVGJSONnum(msg.taskMsg);
	        	 if(num) return num;
	        	 else return "";}}
          ,{title : "泊位",render:function(item){
	        	 var bw=handleSVGJSONbw(item.content);
	        	 if(bw.length>=4) return bw;
	        	 else return "";}}
			 ,{title:'作业要求',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskRequire;
	        	 else return "";}}
         ,{title : "创建时间",render : function(item) {
				return util.getSubTime(item.createTime, 1);}}
        // ,{title : "船信",name : "shipInfo"}
         ,{title : "创建人",name : "createUserName"}
         ,{title : "作业前",name : "strn"}
         ,{title : "作业后",name : "endn"}
         ,{title : "状态",render : function(item) {
				return getStatusStr(item.state);
         }}];
	case '15'://船发作业通知单（码头）
		return [{title:'编号',render:function(item){ 
			return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
			+ item.code.substring(1, item.code.length)+ "</a>";}}
	/*	,{title:'作业任务',render:function(item){
       	 var msg=handleSVGJSON(item.content,type);
       	 if(msg) return msg.taskMsg;
       	 else return "";}}*/
		 ,{title:'船名',render:function(item){
        	 var msg=handleSVGJSON(item.content,type);
        	 var shipname=handleSVGJSONship(msg.taskMsg);
        	 if(shipname) return shipname;
        	 else return "";}}
      ,{title:'货品',render:function(item){
        	 var msg=handleSVGJSON(item.content,type);
        	 var Good=handleSVGJSONGoodship(msg.taskMsg);
        	 if(Good) return Good;
        	 else return "";}}
      ,{title:'数量（吨）',render:function(item){
        	 var msg=handleSVGJSON(item.content,type);
        	 var num=handleSVGJSONnumship(msg.taskMsg);
        	 if(num) return num;
        	 else return "";}}
      ,{title : "泊位",render:function(item){
     	 var bw=handleSVGJSONbw(item.content);
     	 if(bw.length>=4) return bw;
     	 else return "";}}
		 ,{title:'作业要求',render:function(item){
       	 var msg=handleSVGJSON(item.content,type);
       	 if(msg) return msg.taskRequire;
       	 else return "";}}
         ,{title : "创建时间",render : function(item) {
				return util.getSubTime(item.createTime, 1);}}
       // ,{title : "船信",name : "shipInfo"}
         ,{title : "创建人",name : "createUserName"}
         ,{title : "作业前",name : "strn"}
         ,{title : "作业后",name : "endn"}
         ,{title : "状态",render : function(item) {
				return getStatusStr(item.state);
         }}];
		case '16'://船发作业通知单（库区）
			return [{title:'编号',render:function(item){ 
			return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
			+ item.code.substring(1, item.code.length)+ "</a>";}}
			/*,{title:'作业任务',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskMsg;
	        	 else return "";}}*/
			 ,{title:'船名',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 var shipname=handleSVGJSONship(msg.taskMsg);
	        	 if(shipname) return shipname;
	        	 else return "";}}
          ,{title:'货品',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 var Good=handleSVGJSONGoodship(msg.taskMsg);
	        	 if(Good) return Good;
	        	 else return "";}}
          ,{title:'数量（吨）',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 var num=handleSVGJSONnumship(msg.taskMsg);
	        	 if(num) return num;
	        	 else return "";}}
          ,{title : "泊位",render:function(item){
	        	 var bw=handleSVGJSONbw(item.content);
	        	 if(bw.length>=4) return bw;
	        	 else return "";}}
			 ,{title:'作业要求',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskRequire;
	        	 else return "";}}
         ,{title : "创建时间",render : function(item) {
				return util.getSubTime(item.createTime, 1);}}
        // ,{title : "船信",name : "shipInfo"}
         ,{title : "创建人",name : "createUserName"}
         ,{title : "作业前",name : "strn"}
         ,{title : "作业后",name : "endn"}
         ,{title : "状态",render : function(item) {
				return getStatusStr(item.state);
         }}];
		case '17'://发车换罐作业通知
			return [{title:'编号',render:function(item){ 
				return "<a href='javascript:void(0);'onClick='notify.init("+ item.type+ ",\""+ item.code+ "\")' >"
				+ item.code.substring(1, item.code.length)+ "</a>";}}
	         ,{title:'作业任务',render:function(item){
	        	 var msg=handleSVGJSON(item.content,type);
	        	 if(msg) return msg.taskMsg;
	        	 else return "";}}
	         ,{title : "创建时间",render : function(item) {
					return util.getSubTime(item.createTime, 1);}}
	         ,{title : "创建人",name : "createUserName"}
	         ,{title : "作业前",name : "strn"}
	         ,{title : "作业后",name : "endn"}
	         ,{title : "状态",render : function(item) {
					return getStatusStr(item.state);
	         }}];
		}
	};
	function handleSVGJSON(content,type){
		if(content){
			if(type==1||type==10){
				return eval("("+content+")");
				//return SON.parse(content);
			}else{
			 var mContentData=JSON.stringify(content);
				mContentData=mContentData.substring(1,mContentData.length-1);
				mContentData="{"+mContentData.replace(/{\\"/g,"\"").replace(/\\":\\"/g,"\":\"").replace(/\\"\,\\"/g,"\"\,\"").replace(/\\"}/g,"\"")+"}";
			    return eval("("+mContentData+")");
			}
		}else{
			return null;
		}
	};
	
	function handleSVGJSONbw(content){
		if(content){
		    var mContentData=JSON.stringify(content);
			 var bw=mContentData.substring(mContentData.indexOf("泊")-2,mContentData.indexOf("泊")+2);
			  return bw;
			     }
			    else{
					return null;
				}
	};
	
	//船名
	function handleSVGJSONship(content){
		if(content.indexOf("】")+2==content.indexOf("吨"))
			{
			return null;
			}else{
		    content = content.substring(content.indexOf("【") + 1,content.indexOf("】"));
			return content;
			}
	};
	//货品
    function handleSVGJSONGood(content){
    	
    	 content = content.substring(content.indexOf("】") + 2,content.length);
    	 content = content.substring(0,content.indexOf("】"));
		 return content;
	};
	//数量
	function handleSVGJSONnum(content){
		if(content.indexOf("】")+2==content.indexOf("吨"))
		{
			content = content.substring(content.indexOf("【") + 1,content.indexOf("】"));	
		return content;
		}else{
   	 content = content.substring(content.indexOf("】") + 2,content.length);
   	 content = content.substring(content.indexOf("】")+2,content.length);
   	 content = content.substring(0,content.indexOf("】"));
	return content;
		}
	};
	//货品船发
    function handleSVGJSONGoodship(content){
    	 content = content.substring(content.indexOf("】") + 5,content.length);
    	 content = content.substring(0,content.indexOf("】"));
		 return content;
	};
	//数量船发
	function handleSVGJSONnumship(content){
		if(content.indexOf("】")+2==content.indexOf("吨"))
		{
			content = content.substring(content.indexOf("【") + 1,content.indexOf("】"));	
		return content;
		}else{
   	 content = content.substring(content.indexOf("】") + 5,content.length);
   	 content = content.substring(content.indexOf("】")+4,content.length);
   	 content = content.substring(0,content.indexOf("】"));
	return content;
		}
	};
	
	function getStatusStr(state){
		if (state || state == 0) {
			if (state == 0) {
				return "<label style='color:#666699;font-weight:bold;'>未发布</label>";
			} else if (state == 1) {
				return "<label style='color:#99CC33;font-weight:bold;'>已发布</label>";
			} else if (state == 2) {
				return "<label style='color:#9966CC;font-weight:bold;'>作业前完成</label>";
			} else if (state == 3) {
				return "<label style='color:#996699;font-weight:bold;'>作业中完成</label>";
			} else if (state == 4) {
				return "<label style='color:#FF9966;font-weight:bold;'>已完成</label>";
			}
		}
	}
	
return {
	listGrid:listGrid
}	
}();