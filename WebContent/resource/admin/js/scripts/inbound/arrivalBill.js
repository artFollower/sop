var arrivalBill=function(){
	 var LODOP; //声明为全局变量 
	 var arrivalBillMsg;
	 var systemUserId;
	 var systemUserName;
	//初始化
	function init(){
		initSearchCtr();
		initTable();
	};
	
    function initSearchCtr(){
    	util.urlHandleTypeahead("/baseController/getShipName",$('#shipName'));
		$('.date-picker').datepicker({
    	    rtl: Metronic.isRTL(),
    	    orientation: "right",
    	    format: "yyyy-mm-dd",
    	    autoclose: true
    	});
		$(".btn-search").click(function() {
			$("#roleManagerQueryDivId").slideToggle("slow");
		});
		$(".search").click(function() {
			var shipArrivalDto = {};
	        $("#intentListForm").find('.form-control').each(function(){
	            var $this = $(this);
	            var name = $this.attr('name');
	             if(name){
	            	 shipArrivalDto[name] = $this.val();
	            }
	        });
	       $('[data-role="arrivalBillGrid"]').getGrid().search(shipArrivalDto);
		});
		//获取系统userId
		$.ajax({
			type:'post',
			url:config.getDomain()+"/initialfee/getsystemuser",
			dataType:'json',
			success:function(data){
				util.ajaxResult(data,'获取系统信息',function(ndata){
					systemUserId=ndata[0].userId;
					systemUserName=ndata[0].userName;
				},true);
			}
		});
    }	
    
	function initTable(){
		var columns = [ {
			title : "船舶英文名",
			name : "shipName",
			render:function(item,name,index){
				return "<a href='#/billEdit?id="+item.id+"'  class='blue'>"+item.shipName+"</a>";
			}
		}, {
			title : "船舶中文名",
			name : "refName"
		},{
			title : "日期",
			name : "createTime"
		},{
			title : "发票号码",
			name : "code"
		}, {
			title : "船代",
			name : "name"
		}, {
			title : "合计(元)",
			render:function(item){
				return util.toDecimal3(item.total,true);
			}
		}, {
			title : "操作",
			name : "id",
			render: function(item, name, index){
					if(item.id==null||item.id==""){
						return "" ;
					}else{
						return "<a href='#/billEdit?id="+item.id+"'  class='btn btn-xs blue'><span class='fa fa-edit' title='修改'></span></a>";
					}
			}
		}  ];
		/*解决id冲突的问题*/
		$('[data-role="arrivalBillGrid"]').grid({
			identity : 'id',
			columns : columns,
			isShowIndexCol : false,
			isShowPages : true,
			url:config.getDomain()+"/arrivalBill/list"
		});
	}
	

	function initEdit(id){
		initFormIput();
		initCtr();
		initMsg(id);
	};
	function initCtr(){
		$("#save,#submit").click(function(){
			var status=$(this).attr('data');
			if(!config.validateForm($(".arrivalBillEdit"))){
				$("body").message({
					type : 'error',
					content : '必填信息未填写完整'
				});
				return false ;
			}
			var billDto={
					"arrivalBill":{
						'id': $("#id").val(),
						'code':$("#code").val(),
						'contractName':$("#contractName").val(),
						'tradeType':$("#tradeType").val(),
						'accountType':$("#accountType").val(),
						'status':status,
						'createUserId':systemUserId,
						'createTime':util.formatLong(util.currentTime(1))
					},
					"chargeInfo" :{
						'id': $("#acId").val(),
						'KBF':util.isNull($("#KBF").val(),1),
						'TSKBF':util.isNull($("#TSKBF").val(),1),
						'DSF':util.isNull($("#DSF").val(),1),
						'QTF':util.isNull($("#QTF").val(),1),
						'description':util.isNull($("#description").val(),1),
						'num':util.isNull($("#num").val(),1)==0?undefined:util.isNull($("#num").val(),1),
						'overTime':util.isNull($("#overTime").val(),1)==0?undefined:util.isNull($("#overTime").val(),1),
						'billId': $("#id").val(),
						'type':status
					}
			};
			$.ajax({
				type : "post",
				url : config.getDomain()+"/arrivalBill/save",
				data : {'billDto':JSON.stringify(billDto)},
				dataType : "json",
				success : function(data) {
					util.ajaxResult(data,status==0?'保存':'提交',function(){
						initMsg($("#id").val());
					});
				}
			});
		});

	};
	function initMsg(id){
		$.ajax({
			type:'get',
			url:config.getDomain()+"/arrivalBill/get?id="+id,
			success:function(data){
				util.ajaxResult(data,"获取码头规费信息",function(ndata){
					if(ndata&&ndata.length>0){
						arrivalBillMsg=ndata[0];
						var item=ndata[0];
						$("#id").val(item.id) ;
						$("#code").val(item.code) ;
						$("#contractName").val(util.isNull(item.contractName)) ;
						$("#accountType").val(item.accountType) ;
						$("#tradeType").val(item.tradeType) ;
						$("#arrivalTime").val(item.arrivalTime) ;
						$("#shipName").val(item.shipName+"/"+item.refName) ;
						$("#port").val(util.isNull(item.port));
                        $("#status").val(item.status);
						if(item.status&&item.status==1){
							$("#save").text("回退");
							$("#submit").hide();
							$("#createUserId").text(util.isNull(item.createUserName)).attr('data',item.createUserId);
							$("#createTime").text(item.createTime);
							$("#createUserDiv").show();
						}else{
							$("#save").text("保存");
							$("#submit").show();
							$("#createUserDiv").hide();
						}
						initArrivalCharge(item.accountType,item);
					}
				},true);
	
			}
		});	
	};
	
	function  initArrivalCharge(accountType,data){
		var html="";
	            if(util.isNull(accountType,1)==1){
	            	html+='<tr><td><input type="hidden" value="'+data.arrivalChargeId+'"  id="acId" />'+
	            	'<label class="control-label" style="text-align:center">停泊费</label></td>'+
					'<td colspan="4">净吨（'+util.toDecimal3(data.netTons,true)+'）×单价（固定０．２5）×在港天数（'+util.isNull(data.actualDay,1)+'）</td>'+
					'<td><input maxlength="10" type="text" class="form-control " readonly="readonly" id="KBF" value="'+util.toDecimal2(data.KBF,true)+'"  /></td>'+//靠泊费
					'</tr>' +
                    '<tr><td><label class="control-label" style="text-align:center">特殊靠泊费</label></td>'+
					'<td colspan="4"><label class="control-label"  style="float:left;">净吨（'+util.toDecimal3(data.netTons,true)+'）×０．１５×超期小时</label><input type="text" maxlength="6" class="form-control" value="'+util.toDecimal2(data.overTime,true)+'" onkeyup="config.clearNoNum(this,2);arrivalBill.initTSKBF(this.value);" style="width:76px;float:left;" id="overTime"></td>'+
					'<td><input type="text" maxlength="10" class="form-control " readonly="readonly"  id="TSKBF" value="'+util.toDecimal2(data.TSKBF,true)+'"/></td>'+// 特殊靠泊费
					'</tr>' +
				  '<tr><td><label class="control-label" style="text-align:center">淡水费</label></td>'+
					'<td colspan="4"><label class="control-label"  style="float:left;">单价（１６.00）×数量</label><input type="text" class="form-control" maxlength="10"  id="num" value="'+util.toDecimal3(data.num,true)+'" style="width:76px;float:left;" onkeyup="config.clearNoNum(this,2);arrivalBill.initDSF(this.value);" /><label class="control-label"  style="float:left;">吨</label></td>'+
					'<td><input type="text" maxlength="10" class="form-control" readonly="readonly"  id="DSF" value="'+util.toDecimal2(data.DSF,true)+'"/></td>'+//淡水费
					'</tr>' +
				'<tr><td><label class="control-label" style="text-align:center">其它费用</label></td>'+
					'<td colspan="4"><label class="control-label"  style="float:left;">说明：</label><input type="text" maxlength="50" class="form-control" id="description" style="width:80%;float:left;"  value="'+util.isNull(data.description)+'" /></td>'+
					'<td><input type="text" maxlength="10" class="form-control" onkeyup="config.clearNoNum(this,2);" id="QTF" value="'+util.toDecimal2(data.QTF,true)+'"/></td>'+//其他费
					'</tr>' ;
	            	  $("#arrivalChargeInfo").empty().append(html);
	            	  if(util.isNull(data.KBF,1)==0){
	            		  $("#KBF").val(util.FloatMul(util.isNull(data.netTons,1)*0.25,util.isNull(data.actualDay,1),2));
	            	  }
	            	  if(util.isNull(data.TSKBF,1)==0){
	            		  $("#TSKBF").val(util.FloatMul(util.isNull(data.netTons,1)*0.15,util.isNull(data.overTime,1),2));
	            	  }
	            	  if(util.isNull(data.DSF,1)==0){
	            		  $("#DSF").val(util.FloatMul(16,data.num,2));
	            	  }
	            	  
	            }else if(util.isNull(accountType,1)==2){
	            	html+='<tr><td><input type="hidden" value="'+data.arrivalChargeId+'"  id="acId" /><label class="control-label" style="text-align:center">靠泊费</label></td>'+
	    			'<td colspan="4"> （载重吨＜＝1000吨）净吨（吨） ×单价（0.08） ×1；  （载重吨>1000吨）净吨（吨） ×单价（0.08）（'+util.toDecimal3(data.netTons,true)+'） ×在港天数（'+util.isNull(data.actualDay,1)+'） </td>'+
	    			'<td><input type="text" maxlength="10" class="form-control" readonly="readonly" id="KBF" value="'+util.toDecimal2(data.KBF,true)+'"  /></td>'+// 靠泊费
	    			'</tr>' +
	    			'<tr><td><label class="control-label" style="text-align:center">淡水费</label></td>'+
	    				'<td colspan="4"><label class="control-label"  style="float:left;">单价（１６.00）×数量</label><input maxlength="10" type="text" class="form-control"  id="num" value="'+util.toDecimal3(data.num,true)+'" style="width:76px;float:left;" onkeyup="config.clearNoNum(this,2);arrivalBill.initDSF(this.value);" /><label  style="float:left;" class="control-label">吨</label></td>'+
	    				'<td><input type="text" maxlength="10" class="form-control"  readonly="readonly" id="DSF" value="'+util.toDecimal2(data.DSF,true)+'"  /></td>'+//淡水费
	    				'</tr>' +
	    			'<tr><td><label class="control-label" style="text-align:center">其他费用</label></td>'+
	    				'<td colspan="4"><label class="control-label"  style="float:left;">说明：</label><input type="text" maxlength="50"  class="form-control"  id="description" style="width:80%;float:left;" value="'+util.isNull(data.description)+'" /></td>'+
	    				'<td><input type="text" maxlength="10" class="form-control" onkeyup="config.clearNoNum(this,2);" id="QTF" value="'+util.toDecimal2(data.QTF,true)+'"  /></td>'+
	    				'</tr>' ;
	            	  $("#arrivalChargeInfo").empty().append(html);
	            	  if(util.isNull(data.KBF,1)==0){
	      				if(util.isNull(data.netTons,1)!=0){
	      					if(util.isNull(data.netTons)<=1000){
	      						$("#KBF").val(util.FloatMul(util.isNull(data.netTons,1),0.08,2)) ;
	      					}else if(util.isNull(data.netTons)>1000){
	      						$("#KBF").val(util.FloatMul(util.isNull(data.netTons,1)*0.08,util.isNull(data.actualDay,1),2));
	      					}
	      				}
	            	  }
	            	  if(util.isNull(data.DSF,1)==0){
	            		  $("#DSF").val(util.FloatMul(16,data.num,2));
	            	  }
	            }
	          
        
	};
	
	 //特殊靠泊费
    function initTSKBF(value){
   	 $("#TSKBF").val(util.FloatMul(util.isNull(arrivalBillMsg.netTons,1)*0.15,util.isNull(value,1),2)) ;
    }	
    //淡水费
    function initDSF(value){
    	$("#DSF").val(util.FloatMul(util.isNull(value,1),16,2));
    }
    //选择账单类型
    function setAccountType(obj){
    	arrivalBillMsg.KBF=0;
    	initArrivalCharge(obj.value,arrivalBillMsg);
    }
	
    /****************************************************************************/
  //打印码头规费结算单
	function print(){
		$.ajax({
			type : "post",
			url : config.getDomain()+"/arrivalBill/get?id="+$("input[name=id]").val(),
			dataType : "json",
			success : function(data) {
				util.ajaxResult(data,'打印获取数据',function(ndata){
					if(ndata&&ndata.length==1){
						CreatePrintPage(ndata[0]);
						LODOP.PRINT();
					}
					
				},true);
			}
	    });
	} 
    
	  function CreatePrintPage(data) {
			var total = util.toDecimal2(util.isNull(data.KBF,1)+util.isNull(data.XJLF,1)+util.isNull(data.GKZYBGF,1)+util.isNull(data.DSF,1)+util.isNull(data.QTF,1),true) ;
			var date = new Date() ;
		    LODOP=getLodop();  
			LODOP.PRINT_INITA(0,0,920,670,"");
			LODOP.ADD_PRINT_SETUP_BKIMG("..\\print\\B-025码头规费结算单.jpg");
			LODOP.ADD_PRINT_TEXT(150,140,200,30,util.isNull(data.agentName));//船代
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(195,204,207,38,"a");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(200,440,170,30,util.isNull(data.arrivalTime));//到港时间
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(150,440,170,30,util.isNull(data.contractName));//合同号
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(250,440,170,30,util.isNull(data.code));
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(350,440,170,30,"b");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(358,416,84,21,"c");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(356,243,117,25,"d");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(250,140,200,30,util.isNull(data.shipName)+"/"+util.isNull(data.refName));//船舶中英文名
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(154,746,119,35,"e");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(350,610,80,30,util.toDecimal2(data.KBF,true));
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(358,752,106,21,"f");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(396,241,100,20,"g");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(396,418,84,20,"h");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(397,520,100,20,"i");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
//			LODOP.ADD_PRINT_TEXT(390,610,80,30,arrivalCharge.XJLF);
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(392,755,100,20,"j");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(432,241,100,20,"k");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(432,417,82,20,"l");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(434,521,100,20,"m");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
//			LODOP.ADD_PRINT_TEXT(430,610,80,30,arrivalCharge.GKZYBGF);
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(435,750,100,20,"n");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(475,240,100,20,"o");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(470,340,100,30,util.toDecimal2(data.num));//淡水费吨数
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(470,440,170,30,"p");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(470,610,80,30,util.toDecimal2(data.DSF,true));//淡水费
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(477,748,100,20,"q");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(515,239,100,20,"r");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(517,414,90,20,"s");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(513,516,100,20,"t");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(510,610,80,30,util.toDecimal2(data.QTF,true));//其他费
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(515,752,100,20,"u");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(552,237,100,20,"v");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(549,413,90,20,"w");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(550,515,100,20,"x");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(550,610,80,30,util.toDecimal2(total,true));
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(553,754,100,20,"y");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(599,416,100,20,"z");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(599,709,100,20,"aa");
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			LODOP.ADD_PRINT_TEXT(120,630,150,30,date.getFullYear()+"  "+(date.getMonth()+1)+"  "+date.getDate());
			LODOP.SET_PRINT_STYLEA(0,"FontSize",11);
			
		  };   
		//打印控件
			var CreatedOKLodop7766=null;

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


				     //============================================================	     
				     return LODOP; 
				} catch(err) {
				     if (is64IE)
			            document.documentElement.innerHTML="Error:"+strHtm64_Install+document.documentElement.innerHTML;else
			            document.documentElement.innerHTML="Error:"+strHtmInstall+document.documentElement.innerHTML;
				     return LODOP; 
				};
			};
			/***************************************************************************************/
				
     return{
	init:init,
	initEdit:initEdit,
	initTSKBF:initTSKBF,//设置特殊靠泊费
	initDSF:initDSF,//设置淡水费
	setAccountType:setAccountType,
	print:print	//打印
    };
}();