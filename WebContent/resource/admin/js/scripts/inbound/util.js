/**工具js*/
var util = function() {
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
	/**校验选择列表数据的数量*/
	function validateData(obj,item){
		var data = $(obj).getGrid().selectedRowsIndex();
		var isOK=true;
		if (data.length == 0) {
		$("body").message({
		type : 'warning',
		content : '请选择要处理的选项'
		});
		isOK=false;
		}else if(data.length>1){
			if(item!=1){//不校验大于1的情况
			$('body').message({
				type:"warning",
				content:'只能选择一个选项'
			});
			isOK=false;
			}
		}
		return isOK;
	};
	/**处理异步请求的结果*/
	function handleResult(data,item){
		if(data.code=='0000'){
			$('body').message({
				type:'success',
				content:msgContent(item,true)	
			});
		}else{
			$('body').message({
				type:'error',
				content:msgContent(item,false)	
			});
		}
	};
	/**处理下拉列表*/
	function  handleTypeahead(data,obj,key1,key2,isEmpty,callback){
		var akey="name";
		var bkey="id";
		if(key1!=undefined&&key1!=null){
			akey=key1;
		}
		if(key2!=undefined&&key2!=null){
			bkey=key2;
		}
		$(obj).typeahead({
				source: function(query, process) {
					var results = _.map(data, function (item) {
							return item[akey];
                  });
                  process(results);
				},
				updater: function (item) {
  					var client = _.find(data, function (p) {
                        return p[akey] == item;
                    });
  					if(client!=null)
  					{
  						$(obj).attr("data",client[akey])
  					}
  				
  					return item;
				},
				noselect: function (query) {
					var item = _.find(data, function (p) {
						return p[akey] == query;
                });
					if(item==null&&!isEmpty){
						if(callback){
							callback();
						}
						$(obj).attr("data","");
						$(obj).val("");
					}else{ 
						if(item){
							$(obj).attr("data",item[bkey]);
							if(callback){
								callback(item,obj);
							}
						}
					}
					
				}
			});
	}
	//url
	function urlHandleTypeahead(url,obj,key1,key2,isEmpty,callback){
			config.load();
		$.ajax({
  			type : "get",
  			url : config.getDomain()+url,
  			data:{pagesize:0},
  			dataType : "json",
  			success : function(data) {
  				config.unload();
  				if(data.code == "0000"){
  					util.handleTypeahead(data.data,obj,key1,key2,isEmpty,callback);
  				}
  			}
		});
	}
	//显示所有数据
	function urlHandleTypeaheadAllData(url,obj,showbackval,showbackdata,callback,count){
		$.ajax({
  			type : "get",
  			url : config.getDomain()+url,
  			data:{pagesize:0},
  			dataType : "json",
  			success : function(data) {
  				config.unload();
  				if(data.code == "0000"){
  					util.handleTypeaheadAllData(data.data,obj,showbackval,showbackdata,callback,count);  					
  				}
  			}
		});
	}
	function handleTypeaheadAllData(data,obj,showbackval,showbackdata,callback,count){
		var akey="name";
		var bkey="id";
		$(obj).typeahead({
			source: function(query, process) {
				var results = _.map(data, function (item) {
					if(showbackval&&item){
					return showbackval(item);	
					}else{
						return item[akey];
					}	
              });
              process(results);
			},
			items:count?count:100,
				menu:"<ul class=\"typeahead dropdown-menu\" style='height: 260px;width:95%;overflow-y: auto;overflow-x: hidden;'></ul>",
			noselect: function (query) {
				var item = _.find(data, function (p) {
					if(showbackval&&p){
  						return 	showbackval(p)==query;
  						}else{
  							return p[akey] ==query;
  						}
            });
				if(!item){
					if(callback){
						callback();
					}
					$(obj).removeAttr("data");
//					$(obj).val("");
				}else{ 
					if(item){
						if(showbackdata){ 
							$(obj).attr("data",showbackdata(item));
						} else if(item[bkey]){
							$(obj).attr("data",item[bkey]);
						}
						if(callback){
							callback(item,obj);
						}
					}
				}
				
			}
		});
	}
	/**消息*/
	function msgContent(item,isSucceed){
		if(isSucceed){
			if(item==0){
				return "添加成功";
			}else if(item==1){
				return "修改成功";
			}else if(item==2){
				return "删除成功";
			}
		}else{
			if(item==0){
				return "添加失败";
			}else if(item==1){
				return "修改失败";
			}else if(item==2){
				return "删除失败";
			} 			
		}
	};
	/**校验float数字*/
	function checkFloat(obj){
		var v=$(obj).val();
	    var pattern = /^[0-9]*\.?[0-9]*$/; //匹配非负整数
	    if (pattern.test(v)) {
	    }else{
	    	$(obj).message({
				type:'error',
				content:'请填写数字'
			});
			$(obj).val("");
	    }
	};
	/**校验数字*/
	function checkNum(obj){
		var reg=/^[0-9]*$/;
		if(reg.test($(obj).val())){
		}else{
			$(obj).message({
				type:'error',
				content:'请填写数字'
			});
			$(obj).val("");
		}
	};
	/**时间格式化为long类型并除以1000*/
	function formatLong(time){
		if(time==null||(time.length!=19&&time.length!=10)||time==''){
			return -1;
		}else{
			if(time.length==10){
				time=time+" 00:00:00";
			}
		var time2 = time.replace(/-/g, "/");
		var d = new Date(time2);
		return d.getTime()/1000;
		}
		return "";
	};
	/**校验是否是yyyy-dd-mm HH:mm:ss格式*/
	function validateFormat(time){
		if(!time||(time.length!=19&&time.length!=10)){
			return false;
		}else{
			return true;
		}
	}
	
	/**处理null值*/
	function isNull(obj,item){
		if(obj==undefined||obj==null||obj=='null'||obj==''||obj=='NaN'){
			if(item||item==1){//校验数字
				return 0;
			}else{
				return "";
			}
		}else{
			if(item&&item==1){
				return parseFloat(obj);
			}else{
				return obj;
			}
		}
	};
	/**初始化时间控件*/
	function initTimePicker(obj,isAll){
//		$(obj).find('.timepicker-24').timepicker({
//            autoclose: true,
//            minuteStep: 1,
//            defaultTime:"",
//            showSeconds: false,
//            showMeridian: false
//        });
		
		
		$(obj).find('.timepicker-24').inputmask(isAll?"hh:mm:ss":"hh:mm", {
//            "placeholder": "",
        }); 
//        	$(obj).find('.timepicker-24').attr('placeholder','hh:mm');
		$(obj).find('.date-picker').datepicker({
		    rtl: Metronic.isRTL(),
		    language: "zh-CN",
		    orientation: "left",
		    format: "yyyy-mm-dd",
		    showInputs:true,
            disableMousewheel:false,
		    autoclose: true
		});
		$(obj).find('.date-picker').change(function(){
			$this=$(this);
			$this.closest('.input-group').find('.timepicker-24').focus();
			setCursorPosition($this.closest('.input-group').find('.timepicker-24'),0);
		});
		$(obj).find('.timepicker-24').blur(function(){
			$this=$(this);
			var value=$this.val();
			if(value.indexOf('m')!=-1){
				value=value.replace(/m/g,'0');
			}
			if(value.indexOf('h')!=-1){
				value=value.replace(/h/g,'0');
			}
			if(value.indexOf('s')!=-1&&isAll){
				value=value.replace(/s/g,'0');
			}
			
			$this.val(value);
		});
	};
	/**初始化时间控件值*/
	function initTimeVal(obj,value,isAll){
		if(validateFormat(value)){
		$(obj).find(".form-control").each(function(){
			$this=$(this);
			if($this.hasClass("date-picker")){
				$this.datepicker("setDate",value.substring(0,10));
			}else if($this.hasClass("timepicker")){
//				$this.timepicker("setTime",value.substring(11,16));
				
				if(isAll){
					$this.val(value.substring(11,19));
				}else if(value.substring(11,19)=="00:00:01"){
					$this.val("00:00");
				}else if(value.substring(11,16)!="00:00"){
					$this.val(value.substring(11,16));
				}
			}
		});
		}
	};
	/**获取时间*/
	function getTimeVal(obj,isAll){
		var a,b;
		$(obj).find(".form-control").each(function(){
			$this=$(this);
			if($this.hasClass("date-picker")){
				a=$this.val();
			}else if($this.hasClass("timepicker")){
				b=$this.val();
				if(b==undefined||b==""){
					b="00:00";
				}else if(b=="00:00"){
					b="00:00:01";
				}
				//处理mm
				if(b.indexOf('m')!=-1){
					b=b.replace(/m/g,'0');
				}
				if(b.indexOf('h')!=-1){
					b=b.replace(/h/g,'0');
				}
				if(b.indexOf('s')!=-1&&isAll){
					b=b.replace(/s/g,'0');
				}
			}
		});
		if(a==undefined||a==""){
			return "";
		}else if(b=="00:00"){
			return a;
		}else if(b=="00:00:01"||isAll){
			return a+" "+b;
		}else{
			return a+" "+b+":00";
		}
	}
	/**初始化数据*/
	function initFormParams(form, obj) {
		try {
			for ( var item in obj) {				
				$(form).find("input[id='#" + item + "']").val(obj[item]);
				$(form).find("label[id='#" + item + "']").text(obj[item]);
				$(form).find("select[id='#" + item + "']").children("option").each(function() {
					if($(this).val() == obj[item]) {
						$(this).attr("selected","true");
					}
				});
			}
		} catch (err) {

		}
	}
	function cleanTime(obj){
		$this=$(obj).closest(".input-group");
		$this.find("input[type='text']").each(function(){
			$(this).val("");
			$(this).removeAttr();
		});
		
	}
	/**获取当前时间*/
	function currentTime(item){ 
        var now = new Date();
       
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
       
        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var ss=now.getSeconds();           //秒
        var clock = year + "-";
       
        if(month < 10)
            clock += "0";
       
        clock += month + "-";
       
        if(day < 10)
            clock += "0";
        clock += day;
          if(item==1){
         clock+= " ";
       
        if(hh < 10)
            clock += "0";
           
        clock += hh + ":";
        if (mm < 10) clock += '0'; 
        clock += mm+":"; 
        if(ss<10) clock+='0';
        clock+=ss;
          }
        return(clock); 
    };
	//浮点数加法运算 
	function FloatAdd(arg1, arg2,dcl) {
	    var r1, r2, m;
	    if (!$.isNumeric(arg1)) {    
	    	arg1=0;
	        } 
		    if (!$.isNumeric(arg2)) {    
		    	arg2=0;  
		        } 
	    try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
	    try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
	    m = Math.pow(10, Math.max(r1, r2));
	    if($.isNumeric(dcl)&&dcl==2){
	    	return toDecimal2((arg1 * m + arg2 * m) / m,true);
	    }else{
	    	return toDecimal3((arg1 * m + arg2 * m) / m,true);
	    }
	}

	//浮点数减法运算 
	function FloatSub(arg1, arg2,dcl) {
	    var r1, r2, m, n;
	    
	    if (!$.isNumeric(arg1)) {    
	    	arg1=0;
	        } 
		    if (!$.isNumeric(arg2)) {    
		    	arg2=0;  
		        } 
	    
	    try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
	    try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
	    m = Math.pow(10, Math.max(r1, r2));
	    //动态控制精度长度 
	    n = (r1 >= r2) ? r1 : r2;
	    if($.isNumeric(dcl)&&dcl==2){
	    	return toDecimal2(((arg1 * m - arg2 * m) / m).toFixed(n),true);
	    }else{
	    	return toDecimal3(((arg1 * m - arg2 * m) / m).toFixed(n),true);
	    }
	}

	//浮点数乘法运算 
	function FloatMul(arg1, arg2,dcl) {
		 if (!$.isNumeric(arg1)) {    
		    	arg1=0;
		        } 
			    if (!$.isNumeric(arg2)) {    
			    	arg2=0;  
			        } 
	    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	    try { m += s1.split(".")[1].length } catch (e) { }
	    try { m += s2.split(".")[1].length } catch (e) { }
	    if($.isNumeric(dcl)&&dcl==2){
	    	return toDecimal2(Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m),true);
	    }else{
	    	return toDecimal3(Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m),true);
	    }
	}


	//浮点数除法运算 
	function FloatDiv(arg1, arg2,dcl) {
	    var t1 = 0, t2 = 0, r1, r2;
	    if (!$.isNumeric(arg1)) {    
	    	arg1=0;
	        } 
		    if (!$.isNumeric(arg2)) {    
		    	arg2=0;  
		        } 
	    try { t1 = arg1.toString().split(".")[1].length } catch (e) { }
	    try { t2 = arg2.toString().split(".")[1].length } catch (e) { }
	    with (Math) {
	        r1 = Number(arg1.toString().replace(".", ""));
	        r2 = Number(arg2.toString().replace(".", ""));
	        if($.isNumeric(dcl)&&dcl==2){
	        	 return toDecimal2((r1 / r2) * pow(10, t2 - t1),true);
	        }else{
	        	 return toDecimal3((r1 / r2) * pow(10, t2 - t1),true);
	        }
	    }
	}   
	//获得日期是星期几
	  function getDayOfWeek(dayValue){
	      var day = new Date(Date.parse(dayValue.replace(/-/g, '/'))); //将日期值格式化
	      var today = new Array("星期天","星期一","星期二","星期三","星期四","星期五","星期六");
	      return today[day.getDay()] //day.getDay();根据Date返一个星期中的某一天，其中0为星期日
	   }
		 //保留三位小数  如果全部都保存为三位isAll=true
	function toDecimal3(x,isAll) {    
	         var f = parseFloat(x);
	         if (!$.isNumeric(f)) {    
	             return 0;    
	         }    
	          f = Math.round((f*1000).toFixed(2))/1000;    
	         var s = f.toString();    
	         var rs = s.indexOf('.'); 
	         if(isAll){
	         if (rs < 0) {    
	             rs = s.length;    
	             s += '.';    
	         }    
	         while (s.length <= rs + 3) {    
	             s += '0';    
	         }  
	         }
	         return s;    
	     } 
	//保留两位小数
	function toDecimal2(x,isAll) {    
        var f = parseFloat(x);
        if (!$.isNumeric(f)) {    
            return 0;    
        }   
         f = Math.round((f*100).toFixed(2))/100;   
        var s = f.toString();    
        var rs = s.indexOf('.');   
        if(isAll){
        if (rs < 0) {
            rs = s.length;    
            s += '.';    
        }    
        while (s.length <= rs + 2) {    
            s += '0';    
        } 
        }
        return s;    
    } 
	
	function getAZ(num){
		var str='';
		switch(num){
		case '0':
			str='A';
		  break;
		case '1':
			str='B';
		  break;
		case '2':
			str='C';
		  break;
		case '3':
			str='D';
		  break;
		case '4':
			str='E';
		  break;
		case '5':
			str='F';
		  break;
		case '6':
			str='G';
		  break;
		case '7':
			str='H';
		  break;
		case '8':
			str='I';
		  break;
		case '9':
			str='J';
		  break;
		case '10':
			str='K';
		  break;
		case '11':
			str='L';
		  break;
		case '12':
			str='M';
		  break;
		case '13':
			str='N';
		  break;
		case '14':
			str='O';
		  break;
		case '15':
			str='P';
		  break; 
		case '16':
			str='Q';
		  break;  
		case '17':
			str='R';
		  break; 
		  default:
			  break;
		}
		return str;
	};
	//设置光标位置函数 
	function setCursorPosition(ctrl, pos){ 
	if(ctrl.setSelectionRange){ 
	ctrl.focus(); 
	ctrl.setSelectionRange(pos,pos); 
	} 
	else if (ctrl.createTextRange) { 
	var range = ctrl.createTextRange(); 
	range.collapse(true); 
	range.moveEnd('character', pos); 
	range.moveStart('character', pos); 
	range.select(); 
	} 
	};
	//截取时间
	function getSubTime(time,index){
		if(validateFormat(time)&&time.length==19||validateFormat(time)&&time.length==10){
			if(index==1){
				return time.substring(0,10);
			}else if(index==2){
				return time.substring(0,16);
			}else{
				return time.substring(11,19);
			}
		}else{
			return "";
		}
	};
    function addDate(startTime,date){
    	if(!startTime&&startTime!=10&&startTime!=16){
    		return "";
    	}else{
    		var time2 = startTime.replace(/-/g, "/");
			var d = new Date(time2);
			d.setTime(d.getTime() + date * 86400000);
			var t = new Date(d.getTime());
			return t.Format("yyyy-MM-dd");
    	}
    }
    //比较两个format类型时间的大小
	function compareTime(firstTime,endTime){
		if(firstTime&&endTime){
		if(firstTime.length!=19&&firstTime.length!=10){
			return true;
		}
		if(endTime.length!=19&&endTime.length!=10){
			return false;
		}
		if(firstTime.length==10){
			firstTime+=" 00:00:00";
		}
		if(endTime.length==10){
			endTime+=" 00:00:00";
		}
		
		var d1=new Date(firstTime.replace(/-/g, "/"));
		var d2=new Date(endTime.replace(/-/g, "/"));
		if(Date.parse(d1)>Date.parse(d2)){
			return false;
		}else{
			return true;
		}
		}else{
			return true;
		}
	};
	//获取时间差值 时间类型 yyyy-mm-dd hh:ss:mm
	function getDifferTime(time1,time2,type){
		var timeLong1=formatLong(time1);
		var timeLong2=formatLong(time2);
		if(timeLong1==-1||timeLong2==-1){
		return "";	
		}
		var differ=FloatSub(timeLong2,timeLong1);
		if(type&&type==1){
			return parseFloat(differ);
		}else if(type&&type==2){
              return parseFloat(FloatDiv(differ,86400,2));			
		}else{
		var differH=FloatDiv(differ,3600,2);
		if(differH>0){
			return differH;
		}else{
			return "";
		}
		}
	};
	
	
	//获取时间差值 时间类型 yyyy-mm-dd hh:ss:mm
	function getDifferSQTime(time1,time2,type){
		var timeLong1=formatLong(time1);
		var timeLong2=formatLong(time2);
		if(timeLong1==-1||timeLong2==-1){
		return "";	
		}
		var differ=FloatSub(timeLong2,timeLong1);
		if(type&&type==1){
			return parseFloat(differ);
		}else if(type&&type==2){
              return parseFloat(FloatDiv(differ,86400,2));			
		}else{
		var differH=FloatDiv(differ,3600,2);
			return differH;
		}
	};
	
	
	function isNum(x){
		 var f = parseFloat(x);
	        if (!$.isNumeric(f)) {    
	            return undefined;    
	        }else{
	        	return x;
	        }  
		
	}
	function  ajaxResult(data,str,success,isShow,unsuccess){
		if(data){
			config.unload();
		if(data.code=='0000'){
			if(success){
				success(data.data,data.map,data.msg);
			}
			if(isShow==undefined){
			$('body').message({
				type:'success',
				content:str+"成功"
			});}
		}else{
			
			var remsg=str+"失败";
			if(data.msg&&data.msg.length>0){
				remsg=data.msg;
			}
			if(unsuccess)
				unsuccess();
			$('body').message({
				type:'error',
				content:remsg
			});
		}}else{
			var remsg=str+"失败";
			if(data&&data.msg&&data.msg.length>0){
				remsg=data.msg;
			}
			$('body').message({
				type:'error',
				content:remsg
			});
		}
	};
	function findDate(){
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth()+1;
		var day = date.getDate();
		var thisDate = year+""+(month<10?"0"+month:""+month)+(day<10?"0"+day:""+day);
		return thisDate;
	}
	//校验个位数
	function clearSingleNum(obj){
		//先把非数字的都替换掉，除了数字和.
		obj.value = obj.value.replace(/[^\d]/g, "");
		if(obj.value&&obj.value.length>1){
			obj.value=obj.value.substring(0,1);
		}
	}
	
	function changeTopNum(obj,event){
			var nums = $(obj).val();
			var e = event || window.event || arguments.callee.caller.arguments[0];
			var newStr = nums.substring(0,1);
				if(e && e.keyCode>=49 && e.keyCode <= 57) //1-9
				{ 
		            newStr=(e.keyCode-48);   
		        }else if(e && e.keyCode>=97 && e.keyCode <= 105){
		        	newStr=(e.keyCode-96); 
		        }
			$(obj).val(newStr);
	}
	function formatString(timeL){
		if(timeL==null||timeL==""){
			return "";
		}else{
			return new Date(timeL*1000).Format("yyyy-MM-dd hh:mm:ss");
		}
		
	}
	
	function getNo(count){
		count=count+"";
		var zero=6-count.length;
		var sql="";
		for(var i=0;i<zero;i++){
			sql+="0";
			
		}
		
		return "No."+sql+count;
	}
	
	function updateGridRow(obj,params){
		if(!params.key) params.key='id';
	 $.ajax({
		 type:'post',
		 url:config.getDomain()+params.url,
		 dataType:'json',
//		 pageSize:$(obj).getGrid().pageSize,
//		 pageNo:$(obj).getGrid().pageNo,
		 data:params,
		 success:function(data){
			 util.ajaxResult(data,'获取数据',function(ndata){
				 if(ndata&&ndata.length==1){
					 $(obj).getGrid().updateRows(params[params.key],ndata[0]);				 					 
				 }
			 },true);
		 }
	 });
	};
	
	function deleteGridRow(obj){
		var num=isNull($(".records>[data-role='end-record']").text(),1)-isNull($(".records>[data-role='start-record']").text(),1);
		if(num<1&&isNull($(".records>[data-role='start-record']").text(),1)>10)
			$(".pagination>li[data-value='"+(util.isNull($(obj).getGrid().pageNo,1)-1)+"']").click();
		else
			$(".pagination>li[data-value='"+util.isNull($(obj).getGrid().pageNo,1)+"']").click();
	};
	
	function initDatePicker(obj){
		if (jQuery().datepicker){
			var $this=(obj?$(obj).find(".date-picker"):$('.date-picker'));
				$this.datepicker({
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
        });}
	};
	function initMonthPicker(obj){
		if (jQuery().datepicker){
			var $this=(obj?$(obj).find(".date-picker"):$('.date-picker'));
				$this.datepicker({
				rtl: Metronic.isRTL(),
			    orientation: "left",
			    format: "yyyy-mm",
			    showInputs:true,
			    startView:"months",
			    minViewMode:"months",
			    showDay:"false",
	            disableMousewheel:false,
	            changeMonth :false,
			    autoclose: true,
        });
		}
	};
	function querySlideToggle(){
		$("#roleManagerQueryDivId").slideToggle("slow");
	};
	
	function setColumnsVisable(obj,noHiddenArray,initCheckArray,callback){
		var msg="";
		if(!noHiddenArray)
			noHiddenArray=[];
		if(!initCheckArray)
			initCheckArray=[];
		$(".columnToggler").remove();
		var html='<div class="actions  pull-right columnToggler"> <div class="btn-group "> <button aria-expanded="true" data-toggle="dropdown"  id="columnTogglerCtr" class="btn default ">筛选'+
		'<span class="fa fa-angle-down"></span></button><div id="column_toggler" class="dropdown-menu hold-on-click dropdown-checkboxes  pull-right">';
		$(obj).find(".grid-table-head th").each(function(){
			$this=$(this);
			if($this.attr('index')){
				msg=$.inArray(util.isNull($this.attr('index'),1), noHiddenArray)!=-1?'disabled="disabled"':"";
				html+='<label><input type="checkbox" checked="" '+msg+' index="'+$this.attr('index')+'">'+$this.text()+'</label>';
			}
		});
		html+='</div></div></div>';
		$(html).insertBefore($(obj).find(".grid"));
		 $('#column_toggler input[type="checkbox"]').change(function(){
	            var index=$(this).attr("index");
	            if($(this).is(':checked')){
	            	$(obj).find('td[index='+index+'],th[index='+index+']').removeClass("hidden");
	            }else{
	            	$(obj).find('td[index='+index+'],th[index='+index+']').addClass("hidden");
	            }
	        });
		 if(initCheckArray.length>0){
			 $('#column_toggler input[type="checkbox"]').each(function(){
				 $this=$(this);
				 if($this.attr('index')&&$.inArray(util.isNull($this.attr('index'),1), initCheckArray)==-1){
				 $this.click();	 
				 }
			 });
		 }
		 $('#columnTogglerCtr').click(function(){
			 if($('#columnTogglerCtr').text()=='确认'){
				 $('#columnTogglerCtr').empty().append('筛选<i class="fa fa-angle-down"></i>').removeClass("blue").addClass("default");
				 if(callback)
				 callback();	 
			 }else if($('#columnTogglerCtr').text()=='筛选'){
				 $('#columnTogglerCtr').empty().append('确认<i class="fa fa-angle-down"></i>').removeClass("default").addClass("blue");
			 }
		 });
		 
	};
	function getColumnsCheckArray(){
		var array=[];
		var isAll=true;
		$('#column_toggler input[type="checkbox"]').each(function(){
			 $this=$(this);
			 if($this.attr('index')){
			 if($this.is(':checked')){
				array.push(util.isNull($this.attr('index'),1)); 
			 }else{
				 isAll=false;
			 }}
		 });
		if(isAll){
			return undefined;
		}else {
			return array;
		}
		
	};
	
	
	/**获取历史记录列表*/
	function  dialogShowLog(obj,type,target){
		$.get(config.getResource()+ "/pages/inbound/inboundoperation/notice/dialog_message.jsp").done(function(data) {
			var mdialog = $(data);
			//1.接卸（码头）通知单记录，2.接卸（动力班）通知单记录3.打循环通知单记录，4.配管通知单记录
			function initMsg(obj,type){
				var pageSize,pageNo;
		    	 var columns=[{title:"序号",width:60,render:function(item,name,index){
		    		pageSize=$(obj).getGrid().pageSize;
		   			pageNo=$(obj).getGrid().pageNo;
		   			 return pageSize*pageNo+index+1;  }
		    	 },{title:"时间",name:"createTime"
		    	 },{title:"记录",name:"content"}];
		    	 if($(obj).getGrid()!=null)$(obj).getGrid().destory();
		      $(obj).grid({
		    	  	identity : 'id',
					columns : columns,
					isShowIndexCol : true,
					isShowPages : true,
					url : config.getDomain() + "/noticelog/list?type="+type
		      });    	 
		     };
			initMsg(mdialog.find("[data-role=msgGrid]"),type);
			mdialog.find("#suremsg").on('click',function(){					
				if(util.validateData(mdialog.find("[data-role=msgGrid]"))){
				var data=mdialog.find("[data-role=msgGrid]").getGrid().selectedRows()[0];
				$(target).val(data.content);
				mdialog.remove();
				}
			});
			mdialog.modal({
				keyboard : true
			});
		});
	};	
	function saveLog(obj,type,target){
		if(util.isNull($(target).val(),1)!=0){
		config.load();
		$.ajax({type:"post",
			url:config.getDomain()+"/noticelog/add",
			data:{'type':type,'content':$(target).val(),
			 'createTime':util.formatLong(util.currentTime(1))
			},
			dataType:"json",
			success:function(data){
				util.ajaxResult(data,'保存');
			}
		});
	}};
	function getStrSearchCondition(object){
		return JSON.stringify(object).replace("{","&").replace(/\"/g,"").replace(/:/g,"=").replace(/,/g,"&").replace("}","");
	};
	function getEndDayOfMonth(month,type){
		if(month&&month.length==7){
			var date=new Date(month.substring(0,4),(parseInt(month.substring(6,7))-1),'01');
			date.setDate(1);
			date.setMonth(date.getMonth()+1);
//			var date=new Date(month.substring(0,4),(parseInt(month.substring(6,7))+1),1);
			var mDate=new Date(date.getTime()-1000*60*60*24);
			if(type)
				return month+"-"+mDate.getDate();
//				return month+'-'+mDate.getDate();
			else
				return month+"-"+mDate.getDate()+' 23:59:59';
		}else{
			return undefined;
		}
	};
	function getNameByEncode(name){
		name=encodeURI(name);
		return encodeURI(name);
	}
	
	return {
		validateData:validateData,//校验选择列表数据的数量
		handleResult:handleResult,//处理异步请求的结果
		msgContent:msgContent,//处理异步请求的结果
		handleTypeahead:handleTypeahead,//处理下拉列表
		urlHandleTypeahead:urlHandleTypeahead,//处理下拉列表
		urlHandleTypeaheadAllData:urlHandleTypeaheadAllData,//处理下拉显示所有并且自定义显示内容
		handleTypeaheadAllData:handleTypeaheadAllData,
		checkFloat:checkFloat,//
		checkNum:checkNum,
		formatLong:formatLong,
		isNull:isNull,
		cleanTime:cleanTime,
		currentTime:currentTime,
		FloatAdd:FloatAdd,
		FloatSub:FloatSub,
		FloatMul:FloatMul,
		FloatDiv:FloatDiv,
		initTimeVal:initTimeVal,
		getTimeVal:getTimeVal,
		validateFormat:validateFormat,
		getDayOfWeek:getDayOfWeek,
		toDecimal3:toDecimal3,
		toDecimal2:toDecimal2,
		getAZ:getAZ,
		addDate:addDate,
		getSubTime:getSubTime,
		compareTime:compareTime,
		getDifferTime:getDifferTime,
		isNum:isNum,
		findDate:findDate,
		ajaxResult:ajaxResult,
		clearSingleNum:clearSingleNum,
		changeTopNum:changeTopNum,
		formatString:formatString,
		getNo:getNo,
		updateGridRow:updateGridRow,
		deleteGridRow:deleteGridRow,
		initTimePicker:initTimePicker,
		initDatePicker:initDatePicker,
		initMonthPicker:initMonthPicker,
		getEndDayOfMonth:getEndDayOfMonth,
		querySlideToggle:querySlideToggle,
		setColumnsVisable:setColumnsVisable,
		getColumnsCheckArray:getColumnsCheckArray,
		getDifferSQTime:getDifferSQTime,
		dialogShowLog:dialogShowLog,
		saveLog:saveLog,
		getStrSearchCondition:getStrSearchCondition,
		getNameByEncode:getNameByEncode
};
	}();