var ListOperation = function(){
	
var init=function(){
	var date = new Date();
	   var d = date.getDate();
	   var m = date.getMonth();
	   var y = date.getFullYear();

	   var h = {};//头部标题
       //宽度设置
	   if (Metronic.isRTL()) {
	       if ($('#calendar').parents(".portlet").width() <= 720) {
	           $('#calendar').addClass("mobile");
	           h = {
	               right: 'title',
	               center: 'prevYear,year,nextYear,',
	               left: 'agendaDay, agendaWeek,prevYear,prev,next,nextYear'
	           };
	       } else {
	           $('#calendar').removeClass("mobile");
	           h = {
	               right: 'title',
	               center: 'prevYear,year,nextYear,',
	               left: 'agendaDay, agendaWeek, prevYear,prev,next,nextYear'
	           };
	       }
	   } else {
	       if ($('#calendar').parents(".portlet").width() <= 720) {
	           $('#calendar').addClass("mobile");
	           h = {
	               left: 'title',
	               center: 'prevYear,day,nextYear,',
	               right: 'today,month,agendaWeek,agendaDay'
	           };
	       } else {
	           $('#calendar').removeClass("mobile");
	           h = {
	               left: 'title',
	               center: '',
	               right: 'prevYear,prev,next,nextYear'
	           };
	       }
	   }
	   //清空
	   $('#calendar').fullCalendar('destroy'); // destroy the calendar
	   //初始化
	   $('#calendar').fullCalendar({ //re-initialize the calendar
	       header: h,
//	       timeFormat:'h:mm',
	       height : window.innerHeight-250,
	       timezone : 'local',
		   lang: 'zh-cn',
	       defaultView: 'month', // change default view with available options from http://arshaw.com/fullcalendar/docs/views/Available_Views/ 
	       slotMinutes: 15,
	       editable: false,     
	       droppable: true, // this allows things to be dropped onto the calendar !!!
	       drop: function(date, allDay) { // this function is called when something is dropped

	           // retrieve the dropped element's stored Event Object
	           var originalEventObject = $(this).data('eventObject');
	           // we need to copy it, so that multiple events don't have a reference to the same object
	           var copiedEventObject = $.extend({}, originalEventObject);

	           // assign it the date that was reported
	           copiedEventObject.start = date;
	           copiedEventObject.allDay = allDay;
	           copiedEventObject.className = $(this).attr("data-class");

	           // render the event on the calendar
	           // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
	           $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

	           // is the "remove after drop" checkbox checked?
	           if ($('#drop-remove').is(':checked')) {
	               // if so, remove the element from the "Draggable Events" list
	               $(this).remove();
	           }
	       },
	      
	       events: function(start, end,timezone,callback) {
	    	   config.load();
	    	   var events = [];
	           
	    	   
	    	   
	           $.ajax({
	               url: config.getDomain()+"/inboundserial/loghave",
	               type : "post",
	               dataType : "json",
	               async : false,
	               data: {
	                   // our hypothetical feed requires UNIX timestamps
	                   "startTime": Math.round(new Date(start).getTime() / 1000-28800),
	                   "endTime": Math.round(new Date(end).getTime() / 1000-28800)
	               },
	               success: function(data) {
	            	   config.unload();
	            	   var arrivalTimeList=[];
	                   var arrivalId=[];
	                   Date.prototype.Format = function (fmt) { //author: meizz 
	           		    var o = {
	           		        "M+": this.getMonth() + 1, //月份 
	           		        "d+": this.getDate(), //日 
	           		        "H+": this.getHours(), //小时 
	           		        "m+": this.getMinutes(), //分 
	           		        "s+": this.getSeconds(), //秒 
	           		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	           		        "S": this.getMilliseconds() //毫秒 
	           		    };
	           		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	           		    for (var k in o)
	           		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	           		    return fmt;
	           		}
	                   Array.prototype.contains = function(item){
	                	   return RegExp("\\b"+item+"\\b").test(this);
	                   };
	                   
	                   if(data.data.length>0){
	                	   for(var i=0;i<data.data.length;i++){
	                   arrivalTimeList.push(new Date(data.data[i].arrivalTime*1000).Format("yyyy-MM-dd"));
//	                   alert(new Date(data.data[i].arrivalTime*1000).Format("yyyy-MM-dd"));
	                   arrivalId.push(data.data[i].arrivalId);
	                	   }
	                   }
	                   var count=Math.round((new Date(end).getTime()-new Date(start).getTime())/86400000);
	                   
	                   for(var c=0;c<count;c++){
	                	   var logCount=getCount(data.data,arrivalTimeList,new Date(new Date(start).getTime()+86400000*c).Format("yyyy-MM-dd"));
	                	   var color="grey";
	                	   if(logCount>=1&&logCount<=2){
	                		   color="blue";
	                	   }
	                	   if(logCount>=3&&logCount<=4){
	                		   color="green";
	                	   }
	                	   if(logCount>4){
	                		   color="red";
	                	   }
	                	   if(config.hasPermission('MDISPATCHLOG')){
	                	   events.push({
	                		  id:'1',
	                		  ignoreTimezone:false,
	                		  title:new Date(new Date(start).getTime()+86400000*c).Format("MM-dd")+"              调度日志船舶数:"+logCount,
	                		  backgroundColor:  Metronic.getBrandColor(color),
	                		  start:new Date(new Date(start).getTime()+86400000*c).Format("yyyy-MM-dd")
	                	   });
	                	   }
	                   }
//	                   callback(events);
//	                   alert(count);
	                   
//	                   if(data.data.length>0){
//	                	   for(var i=0;i<data.data.length;i++){
//	                		   if(!arrivalTimeList.contains(new Date(data.data[i].arrivalTime*1000).Format("yyyy-MM-dd"))){
//	                		   events.push({
//	                			   id:'1',
//	                			   ignoreTimezone : false,
//	                			   title: '查看调度日志',
//	                			   backgroundColor:  Metronic.getBrandColor('blue'),
//	                			   start: new Date(data.data[i].arrivalTime*1000).Format("yyyy-MM-dd") // will be parsed
//	                		   });
//	                		   arrivalTimeList.push(new Date(data.data[i].arrivalTime*1000).Format("yyyy-MM-dd"));
//	                	   }
//	                	   }
//	                	   callback(events);
//	               }
	               }
	           });
	           
	           $.ajax({
	               url: config.getDomain()+"/outboundserial/loghave",
	               type : "post",
	               async : false,
	               dataType : "json",
	               data: {
	                   // our hypothetical feed requires UNIX timestamps
	                   "startTime": Math.round(new Date(start).getTime() / 1000-28800),
	                   "endTime": Math.round(new Date(end).getTime() / 1000-28800)
	               },
	               success: function(data) {
	            	   var arrivalTimeList=[];
	                    
	                   Date.prototype.Format = function (fmt) { //author: meizz 
	           		    var o = {
	           		        "M+": this.getMonth() + 1, //月份 
	           		        "d+": this.getDate(), //日 
	           		        "h+": this.getHours(), //小时 
	           		        "m+": this.getMinutes(), //分 
	           		        "s+": this.getSeconds(), //秒 
	           		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	           		        "S": this.getMilliseconds() //毫秒 
	           		    };
	           		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	           		    for (var k in o)
	           		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	           		    return fmt;
	           		}
	                   Array.prototype.contains = function(item){
	                	   return RegExp("\\b"+item+"\\b").test(this);
	                   };
	                   if(data.data.length>0){
	                	   for(var i=0;i<data.data.length;i++){
	                		   arrivalTimeList.push(new Date(data.data[i].stopPump*1000).Format("yyyy-MM-dd"));
	                	   }
	                   }
	                   
	                   var count=Math.round((new Date(end).getTime()-new Date(start).getTime())/86400000);
	                   for(var c=0;c<count;c++){
	                	   var logCount=getFLCount(arrivalTimeList,new Date(new Date(start).getTime()+86400000*c).Format("yyyy-MM-dd"));
	                	   var color="grey";
	                	   if(logCount>=1&&logCount<=2){
	                		   color="blue";
	                	   }
	                	   if(logCount>=3&&logCount<=4){
	                		   color="green";
	                	   }
	                	   if(logCount>4){
	                		   color="red";
	                	   }
	                	   if(config.hasPermission('MSHIPLEDGER')){
	                	   events.push({
	                		  id:'2',
	                		  ignoreTimezone:false,
	                		  title:new Date(new Date(start).getTime()+86400000*c).Format("MM-dd")+"              分流台账记录数:"+logCount,
	                		  backgroundColor:  Metronic.getBrandColor(color),
	                		  start:new Date(new Date(start).getTime()+86400000*c).Format("yyyy-MM-dd")
	                	   });
	                	   }
	                   }
	                   callback(events);
	                   /*if(data.data.length>0){
	                	   for(var i=0;i<data.data.length;i++){
	                		   if(!arrivalTimeList.contains(new Date(data.data[i].stopPump*1000).Format("yyyy-MM-dd"))){
	                		   events.push({
	                			   id:'1',
	                			   ignoreTimezone : false,
	                			   title: '查看分流台账',
	                			   backgroundColor:  Metronic.getBrandColor('blue'),
	                			   start: new Date(data.data[i].stopPump*1000).Format("yyyy-MM-dd") // will be parsed
	                		   });
	                		   arrivalTimeList.push(new Date(data.data[i].stopPump*1000).Format("yyyy-MM-dd"));
	                	   }
	                	   }
	                	   callback(events);
	               }*/
	               }
	           });
	           
	           
	       },
	       eventClick:function(event){
	    	   if(event.id==1){
	    		   
	    		   window.location.href = "#/itemoperationlog?start="+(new Date(event.start).getTime()/1000)+"&end="+((new Date(event.start).getTime()+86400000)/1000);
	    	   }else{
	    		   window.location.href = "#/shipflowbook/list?id=0&start="+(new Date(event.start).getTime()/1000)+"&end="+((new Date(event.start).getTime())/1000+86400);
			  		
	    	   }
		  		}
	   });
};	

 var getCount=function(data,arrivalTimeList,date){
	 var count=0;
	   var shipId=[];
	   
	 for(var i=0;i<arrivalTimeList.length;i++){
		 if (arrivalTimeList[i]==date){
			 if(!shipId.contains(data[i].shipId)){
				 count+=1;
				 shipId.push(data[i].shipId);
			 }
		 }
	 }
	 return count;
 };
 
//过去每天的分流台账数量
 var getFLCount=function(arrivalTimeList,date){
 	 var count=0;
 	 for(var i=0;i<arrivalTimeList.length;i++){
 		 if (arrivalTimeList[i]==date){
 			 count+=1;
 		 }
 	 }
 	 return count;
 };
 

	return {
		init: function(){
			init();
		}
	};
}();