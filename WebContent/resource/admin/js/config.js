var config = function() {
	var domain = "";
	var resource = "";
	var timmer = new Object();
	var wTimmer = new Object();
	var weightFlag = true;
	
	var load = function(container) {
		var container = $(".page-container");
		var box = "<div id='loading-box' style='margin:auto;width:100%;height:100%;z-index:39999;box-shadow:4px 4px 3px rgba(20%,20%,40%,0.5);position: absolute;cursor: not-allowed;'>"+
							"<div class='page-spinner-bar' ng-spinner-bar=''><div class='bounce1'></div><div class='bounce2'></div><div class='bounce3'></div></div></div>";
		$(container).append(box);
	};

	var unload = function() {
		$("#loading-box").remove();
		weightFlag = false;
	};
	/**
	 * 显示提示信息
	 */
	var showErrorMessage = function($element) {
		$($element).pulsate({
			color : "#399bc3",
			repeat : false
		});
	};
	
	var findUserId = function(){
		var userId = $("#homePage").attr("ng-data");
		return userId;
	};

	var validation = function($element, $dataType) {
		var data = $($element).val();
		if ($dataType == null || $dataType == "") {
			$dataType = "Require";
		}
		switch ($dataType) {
		case "Require":
			if (data == null || data == ""
					|| data.replace(/(^\s*)|(\s*$)/g, "") == "") {
				showErrorMessage($element);
				return false;
			}
			return true;
			break;
		case "email":
			var reg = new RegExp(/^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/);
			if (!reg.test(data)) {
				showErrorMessage($element);
				return false;
			}
			return true;
			break;
		case "mobile":
			var reg = new RegExp(/^(1[3|5|8]{1}\d{9})$/);
			if (!reg.test(data)) {
				showErrorMessage($element);
				return false;
			}
			return true;
			break;
		default:
			if (!eval(validateExpr).test(value)) {
				return false;
			}
			return true;
			break;
		}
	};
	
	var initSidebar = function() {
//		$(".page-sidebar-menu").find("a").not(".base").click(function() {
//			$(".page-breadcrumb").children("li").not(".base").remove();
//			$(".page-breadcrumb").append("<li>"+$(this).parent("li").html()+"</li>");
//		});
	};
	
	var initMessageTimmer = function() {
		var flag = true;
		var messageTimmer = setInterval(function() {
			if(flag) {
				flag = false;
				toastr.clear();
				$.ajax({
					type : "post",
					url : config.getDomain()+"/messageCenter/get?pagesize=7&status=1",
					dataType : "json",
					success : function(data) {
						if(data.code=="0000"){
							flag = true;
							$(".message-number").each(function() {
								$(this).html(data.map.totalRecord);
							});
							var html = "";
							for(var i=0;i<data.data.length;i++) {
								html +="<li>"+
										"<a href='"+(data.data[i].url != null ? data.data[i].url : "#/message")+"'>"+
										"<span class='photo'>"+
										"<img src='"+config.getDomain()+"/resource/admin/layout/img/avatar2.jpg' class='img-circle' alt=''>"+
										"</span>"+
										"<span class='subject'>"+
										"<span class='from'>"+data.data[i].sendUserName+"</span>"+
										"<span class='time'>"+data.data[i].createTime+"</span>"+
										"</span>"+
										"<span class='message'>"+data.data[i].content+
										"</a>"+
									"</li>";
							}
							
							toastr.info( 
									"<a href='"+(data.data[0].url != null ? data.data[0].url : "#/message")+"' onclick='Message.setRead("+data.data[0].id+",1,this)'>"+
									data.data[0].content
									+"</a>",
									"消息"
							);
							$("#message-head-list").children().remove();
							$("#message-head-list").append(html);
						}
					}
				});
			}
		},10000);//1000为1秒钟 
		
		timmer.messageTimmer = messageTimmer;
	};
	
	var getWeight = function(){
		var weightFlag = true;
		var weightTimmer = setInterval(function() {
			if(weightFlag){
				weightFlag = false;
				$.ajax({
					type : "post",
					url : config.getDomain()+"/comm/get",
					data:{"url":$("#dibang").val()},
					dataType : "json",
					complete :function(){
						weightFlag = true;
					},
					success : function(data) {
						if(data.code=="0000"){
							$("#dVal-info").addClass("hidden");
							$("#dVal").val(data.msg);
						}else {
							$("#dVal-info").removeClass("hidden");
							clearInterval(timmer.weightTimmer);
							$('body').confirm({
								content:'地磅读数停止更新，请刷新页面并联系管理员18801773224。',
								callBack:function(){
									return;
								}});
						}
					}
				});
			}
		},1000);
		
		timmer.weightTimmer  = weightTimmer;
	};
	
	var _typeSearch = function(contanier,url,key) {
		$.ajax({
			async:false,
			type : "post",
			url : config.getDomain()+url,
			success : function(data) {
				
				$(contanier).typeahead({
  					source: function(query, process) {
  						var results = _.map(data.data, function (product) {
  	                        return product[key];
  	                    });
  	                    process(results);
  					},
  					updater:function(item){
  						var head = _.find(data.data, function (p) {
  	                        return p.title == item;
  	                    });
  						return item;
  					},
  				noselect:function(query){
  					var head = _.find(data.data, function (p) {
                        return p[key] == query;
                    });
  					if(head==null){
  						$(contanier).attr("data","");
  						$(contanier).val("");
  					}else{
  						$(contanier).attr("data",head.id);
  					}
  				}
  				});
			}
		});
	};
	var typeHead = function(contanier,url,key) {
		_typeSearch(contanier,url,key);
		$(contanier).keypress(function(event) {
			if(event.keyCode == 13) {
			}
		});
	};
	
	var isNull = function(obj) {
		if(obj != null && obj != "" && obj != undefined) {
			return false;
		}else {
			return true;
		}
	};
	
	var permission = null;
	//校验权限
	var hasPermission = function(_permission) {
		try{
			for(var per in permission) {
				if(permission[per] == _permission) {
					return true;
				}
			}
		}catch(e) {
			
		}
		return false;
	};

	return {
		setPermission : function(_permission) {
			permission = _permission;
		},
		getPermission : function() {
			return permission;
		},
		hasPermission : hasPermission,
		isNull : isNull,
		timmer : timmer,
		wTimmer : wTimmer,
		typeHead : typeHead,
		initMessageTimmer : initMessageTimmer,
		getWeight : getWeight,
		initSidebar : initSidebar,
		load : load,
		unload : unload,
		showErrorMessage : showErrorMessage,
		getDomain : function() {
			return domain;
		},
		setDomain : function(path) {
			domain = path;
		},
		getResource : function() {
			return resource;
		},
		setResource : function(path) {
			resource = path;
		},
		validation : function($element, $dataType) {
			return validation($element, $dataType);
		},
		validateForm : function($form) {
			var flag = true;
			$form.find("input,select,div").each(function() {
				if ($(this).attr("data-required") == 1) {
					if (!validation($(this), $(this).attr("data-type"))) {
						flag = false;
					}
				}else if($(this).attr("data-type")&&$(this).val()){
					if (!validation($(this), $(this).attr("data-type"))) {
						flag = false;
					}
				}
			});
			if(!flag) {
				$("body").message({
                    type: 'warning',
                    content: '有必填字段未填写,请您仔细核对'
                });
			}
			return flag;
		},
		clearNoNum : function(obj,savePointNum) {
			clearNoNum(obj,savePointNum);
		},
		clearNoletter:function(obj){
			clearNoletter(obj);
		},
		getRootPath:function(){
			getRootPath();
		},
		findUserId:findUserId,
		flowAreaINput : function() {
			$(".area-input").val($(".area-input").val()+" → ");
			$(".area-input").focus();
		}
	};
}();

function clearNoNum(obj,savePointNum) {
	var point=3;
	if($.isNumeric(savePointNum)){
		point=savePointNum;
	}
	
	
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.-]/g, "");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g, "");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g, ".");
	//保证只有出现一个-而没有多个.
	obj.value = obj.value.replace(/\-{2,}/g, "-");
	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",".");
	if(obj.value.split(".").length>1)
	if(obj.value&&obj.value.split(".")[1].length>point){
		obj.value =parseFloat(obj.value).toFixed(point);
	}
	if((obj.value&&obj.value.split(".")[0].length>1||(obj.value&&obj.value.length>2&&obj.value.indexOf(".")<0))&&obj.value.substring(0, 1)=="0"){
		obj.value=obj.value.substring(1,obj.value.length);
	}
}

function clearNoletter(obj){
	//把非数字的都替换掉，除了数字和.,
	obj.value = obj.value.replace(/[^\d.,，]/g, "");
}

function fixNum(obj,num){
	obj.val().toFixed(num);
}

function initFormParams(form, obj) {
	try {
		for ( var item in obj) {
			$(form).find("input[name='" + item + "']").val(obj[item]);
			$(form).find("textarea[name='" + item + "']").val(obj[item]);
			$(form).find("select[name='" + item + "']").children("option").each(function() {
				if($(this).val() == obj[item]) {
					$(this).attr("selected","true");
				}
			});
		}
	} catch (err) {

	}
	
	
}

//获取项目路径
function getRootPath(){
	var curWwwPath=window.document.location.href;    
	var pathName=window.document.location.pathname;    
	var pos=curWwwPath.indexOf(pathName);    
	var localhostPaht=curWwwPath.substring(0,pos);    
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);    
	return(localhostPaht+projectName);
}

function initFormIput(obj) {
	     
	      if(obj){
	    	  $(obj).find('input[maxlength]').maxlength({
	    		  document:$(obj),
				  alwaysShow: true,
				  warningClass: "label label-success",
				  limitReachedClass: "label label-danger",
				  separator: '个字符,可输入',
				  preText: '已输入',
				  postText: '个字符或数字.',
				  placement:'bottom-left-inside',
				  validate: true
			  });
	      }else{
	    	  $('input[maxlength]').maxlength({
				  alwaysShow: true,
				  warningClass: "label label-success",
				  limitReachedClass: "label label-danger",
				  separator: '个字符,可输入',
				  preText: '已输入',
				  postText: '个字符或数字.',
				  validate: true
			  }); 
	      }
	
		  
    
    $(".number").inputmask({
        "mask": "9",
        "repeat": 1000,
        "greedy": false
    });
}

function isNull(obj) {
	if(obj != undefined && obj != null) {
		return false;
	}
	return true;
}