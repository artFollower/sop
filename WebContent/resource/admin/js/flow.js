var graph;
var model;
var editor;
var interTime = 6000;
var bTubeIds="-1";//主管线
var tankIds="-1";//罐
var parkIds="-1";
var intankIds="-1";//入罐
var outtankIds="-1";//出罐
var pupmIds="-1";//泵
var berthIds="-1";//泊位

function initFlowGraph() {
	graph = null;
	model = null;
	editor = null;
	bTubeIds="-1";//主管线
	tankIds="-1";//罐
	parkIds="-1";
	intankIds="-1";//入罐
	outtankIds="-1";//出罐
	pupmIds="-1";//泵
	berthIds="-1";//泊位
}
// 初始化图形
function flow(xml,hasTool,enabled,obj,hasPark,hasPupm,pumpType,productId,productName,hasBerth) {
	initFlowGraph();
	var centerDiv = null;
	var toolbarContainer = null;
	var berthName='';
	if(obj == undefined) {
		centerDiv = $('#graphContainer');//工艺流程区
		toolbarContainer = $('#toolbarContainer');//工具区
		berthName=$('#berthName').val();
	}else {
		centerDiv = $(obj).find('#graphContainer');
		toolbarContainer = $(obj).find('#toolbarContainer');
		berthName=$(obj).find('#berthName').val();
	}
	if (!mxClient.isBrowserSupported()) {
		mxUtils.error('浏览器不支持', 200, false);
	} else {

		// 图形显示区域DIV
		var container = document.createElement('div');
		//container.className = 'imgContentDivStyle';
		container.style.position = 'absolute';
		container.style.overflow = 'none';
		container.style.width = '97%';
		container.style.height = '92%';
		container.style.margin = 'auto';
		container.style.background = 'url("'+mxBasePath+'/images/grid.gif")';

		// 禁用浏览器自带的右键菜单
		mxEvent.disableContextMenu(container);

		if (mxClient.IS_IE) {
			new mxDivResizer(container);
			new mxDivResizer(outline);
		}

		// 工具栏 Div------
		if(hasTool == null || hasTool) {
			var tbContainer = document.createElement('div');
			tbContainer.style.position = 'absolute';
			var toolbar = new mxToolbar(tbContainer);
			toolbar.enabled = false;
			

			$(toolbarContainer).append(tbContainer); //添加工具栏
		}
		
		$(centerDiv).append(container);

		editor = new mxEditor();
		graph = editor.graph;
		editor.setGraphContainer(container);
		model = graph.getModel();

		graph.setConnectable(true);
		new mxKeyHandler(graph);

		graph.getView().updateStyle = true; // 设置样式可变
		graph.setCellsResizable(true); // 节点大小可否改变
		graph.setConnectable(true); // 连接线
		graph.setDropEnabled(true);
		
		graph.setPanning(true);
		graph.setTooltips(true); // 是否显示提示,默认显示Cell的名称
		mxGraphHandler.prototype.guidesEnabled = true;// 显示细胞位置标尺
		graph.setAllowDanglingEdges(false); // 禁止连接线 晃动
		graph.setMultigraph(false);
		
		//限制区域
		graph.maximumGraphBounds = new mxRectangle(0, 0, $('#graphContainer').width(), $('#graphContainer').height());
		graph.minimumContainerSize = new mxRectangle(0, 0, $('#graphContainer').width(), $('#graphContainer').height());
		graph.setResizeContainer(false);
		
		var keyHandler = new mxDefaultKeyHandler(editor);
		keyHandler.bindAction(46, 'delete');
		
		if(enabled == undefined) {
			graph.setEnabled(true);
		}else {
			graph.setEnabled(enabled);
		}
		


		// 添加右键菜单
		graph.panningHandler.factoryMethod = function(menu, cell, evt) {
			if(productName!=undefined&&cell!=null){
			cell.ptid=productId;
			cell.ptname=productName;
			}
			if(cell&&cell.type=="tank"&&pumpType==1){
				cell.tanktype=0;
			}
			return createPopupMenu(graph, menu, cell, evt);
			
		};

		// 添加工具栏图标
		if(hasTool == null || hasTool) {
			var addVertex = function(icon, w, h, style,tip,type) {
				var vertex = new mxCell(null, new mxGeometry(0, 0, w, h), style);
				vertex.setVertex(true);
	
				addToolbarItem(graph, toolbar, vertex, icon,tip,type);
			};
	
			if(hasPark&&hasBerth==undefined) {
				// （图形地址，宽，高，形状，备注，标识）
				addVertex(mxBasePath + '/../images/rectangle.gif', 60, 36, 'shape=rectangle','车位','park');
			}
			addVertex(mxBasePath + '/../images/hexagon.gif', 60, 36, 'shape=hexagon;','主管线','bTube');
			addVertex(mxBasePath + '/../images/rhombus.gif',  60, 36, 'shape=ellipse','过渡管线','sTube');
			addVertex(mxBasePath + '/../images/rectangle.gif', 60, 36, 'shape=rectangle','货罐','tank');
			if(hasPupm){
				addVertex(mxBasePath + '/../images/triangle.gif', 60, 36, 'shape=triangle','泵','pupm');
			}
		}
		
		var style = graph.getStylesheet().getDefaultEdgeStyle();
		
		graph.getStylesheet().getDefaultVertexStyle()['fillColor'] = '#FFFFFF';
		graph.getStylesheet().getDefaultVertexStyle()['strokeColor'] = '#000000';
		graph.getStylesheet().getDefaultVertexStyle()['fontColor'] = '#000000';
		graph.getStylesheet().getDefaultEdgeStyle()['strokeColor'] = '#000000';
		graph.getStylesheet().getDefaultEdgeStyle()['fontColor'] = '#000000';
		graph.getStylesheet().getDefaultEdgeStyle()['startSize'] = '8';
		graph.getStylesheet().getDefaultEdgeStyle()['endSize'] = '8';
		graph.getStylesheet().getDefaultEdgeStyle()['labelBackgroundColor'] = '#FFFFFF';
		
		style[mxConstants.STYLE_EDGE] = mxEdgeStyle.ElbowConnector;
		style[mxConstants.STYLE_ELBOW] = mxConstants.ELBOW_VERTICAL;
		style[mxConstants.STYLE_ROUNDED] = true;
		mxConstants.DEFAULT_FONTSIZE=14;
		if(xml != undefined) {
			// 加载数据
			model.beginUpdate();
			try {				
				var doc = mxUtils.parseXml(xml.replace(/\\n/g,"").replace(/\\\"/g,"\""));
				var dec = new mxCodec(doc);
				var _model = dec.decode(doc.documentElement);
				graph.getModel().mergeChildren(_model.getRoot().getChildAt(0), graph.getDefaultParent());
				graph.refresh();
			} finally {
				model.endUpdate();
			}
		}else{
			if(hasPark == undefined || !hasPark) {
				var berth = graph.insertVertex(graph.getDefaultParent(), null, berthName, 20, 60, 60, 48);
				berth.type = "berth";
			}
		}

	}
};

function noticeFlow(centerDiv,toolbarContainer,xml) {
	initFlowGraph();
	var _graph = null;
	if (!mxClient.isBrowserSupported()) {
		mxUtils.error('浏览器不支持', 200, false);
	} else {
		// 图形显示区域DIV
		var container = document.createElement('div');
		//container.className = 'imgContentDivStyle';
		container.style.position = 'static';
		container.style.overflow = 'none';
		container.style.width = $(centerDiv).width()+"px";
		container.style.height = $(centerDiv).height()+"px";
		container.style.margin = 'auto';
		container.style.background = 'url("'+mxBasePath+'/images/grid.gif")';
		
		// 禁用浏览器自带的右键菜单
		mxEvent.disableContextMenu(container);

		if (mxClient.IS_IE) {
			new mxDivResizer(container);
			new mxDivResizer(outline);
		}

		// 工具栏 Div------
		var tbContainer = document.createElement('div');
		tbContainer.style.position = 'relative';
		var toolbar = new mxToolbar(tbContainer);
		toolbar.enabled = false;
		$(toolbarContainer).empty();
		$(toolbarContainer).append(tbContainer); //添加工具栏
		$(centerDiv).empty();
		$(centerDiv).append(container);
		
		var editor = new mxEditor();

		_graph = editor.graph;
		editor.setGraphContainer(container);
		model = _graph.getModel();
		 _graph.cellRenderer.isLabelEvent = function(state, evt)  
         {  
             var source = mxEvent.getSource(evt);  

             // FIXME: 在GC中没有滚动事件  
             return state.text != null &&  
                 source != state.text.node &&  
                 source != state.text.node.getElementsByTagName('div')[0];  
         }; 
		_graph.setConnectable(true);
		new mxKeyHandler(_graph);

		_graph.getView().updateStyle = true; // 设置样式可变
		_graph.setCellsResizable(true); // 节点大小可否改变
		_graph.setConnectable(true); // 连接线
		_graph.setDropEnabled(true);
		
		
		_graph.setPanning(true);
		_graph.setTooltips(true); // 是否显示提示,默认显示Cell的名称
		mxGraphHandler.prototype.guidesEnabled = true;// 显示细胞位置标尺
		_graph.setAllowDanglingEdges(false); // 禁止连接线 晃动
		_graph.setMultigraph(true);
				
		//限制区域
		_graph.maximumGraphBounds = new mxRectangle(0, 0, $(centerDiv).width(), $(centerDiv).height());
		_graph.minimumContainerSize = new mxRectangle(0, 0, $(centerDiv).width(), $(centerDiv).height());
		_graph.setResizeContainer(false);
		
		var keyHandler = new mxDefaultKeyHandler(editor);
		keyHandler.bindAction(46, 'delete');
		
		// 添加右键菜单
		_graph.panningHandler.factoryMethod = function(menu, cell, evt) {
			return createPopupMenu(_graph, menu, cell, evt);
		};

		// 添加工具栏图标
		var addVertex = function(icon, w, h, style,tip,type) {
			var vertex = new mxCell(null, new mxGeometry(0, 0, w, h), style);
			vertex.setVertex(true);
			vertex.setValue("Text Here");

			addToolbarItem(_graph, toolbar, vertex, icon,tip,type);
		};

			addVertex(mxBasePath + '/../images/text.gif', 80, 16, 'shape=rectangle','TEXT','text');
		
		var style = _graph.getStylesheet().getDefaultEdgeStyle();
		
		_graph.getStylesheet().getDefaultVertexStyle()['fillColor'] = 'none';
		_graph.getStylesheet().getDefaultVertexStyle()['strokeColor'] = 'none';
		_graph.getStylesheet().getDefaultVertexStyle()['gradientColor'] = 'none';
		_graph.getStylesheet().getDefaultVertexStyle()['fontColor'] = '#000000';
		_graph.getStylesheet().getDefaultEdgeStyle()['strokeColor'] = '#000000';
		_graph.getStylesheet().getDefaultEdgeStyle()['fontColor'] = '#000000';
		_graph.getStylesheet().getDefaultEdgeStyle()['startSize'] = '1';
		_graph.getStylesheet().getDefaultEdgeStyle()['endSize'] = '5';
		_graph.getStylesheet().getDefaultEdgeStyle()['labelBackgroundColor'] = 'none';
		
		style[mxConstants.STYLE_EDGE] = mxEdgeStyle.ElbowConnector;
		style[mxConstants.STYLE_ELBOW] = mxConstants.ELBOW_VERTICAL;
		mxConstants.DEFAULT_FONTSIZE=14;
		style[mxConstants.STYLE_ROUNDED] = true;
	}
	return _graph;
}
function  addGraphVertex(_graph,toolbarContainer){
	
	// 工具栏 Div------
	var tbContainer = document.createElement('div');
	tbContainer.style.position = 'relative';
	var toolbar = new mxToolbar(tbContainer);
	toolbar.enabled = false;
	$(toolbarContainer).empty();
	$(toolbarContainer).append(tbContainer); //添加工具栏
	var vertex = new mxCell(null, new mxGeometry(0, 0,80, 16), 'shape=rectangle');
	vertex.setVertex(true);
	vertex.setValue("Text Here");
	addToolbarItem(_graph, toolbar, vertex, mxBasePath + '/../images/text.gif','TEXT','text');
}



function initXml(_graph,xml){
	if(xml) {
		if(!_graph){
			var editor = new mxEditor();
			_graph = editor.graph;
		}
		// 加载数据
		var model=_graph.getModel();
		model.beginUpdate();
		try {	
			xml=xml.replace(/\\n/g,"").replace(/\\\"/g,"\"");
			var doc = mxUtils.parseXml(xml);
			var dec = new mxCodec(doc);
			var _model = dec.decode(doc.documentElement);
			model.mergeChildren(_model.getRoot().getChildAt(0), _graph.getDefaultParent());
			_graph.refresh();
		} finally {
			model.endUpdate();
		}
	}
}


function graphDisable() {
	graph.setEnabled(false);
}

function graphEnabled() {
	graph.setEnabled(true);
}

function graphCleanCache(){
	 bTubeIds="-1";//管线
	 tankIds="-1";//罐
	 parkIds="-1";
	 intankIds="-1";//入罐
	 outtankIds="-1";//出罐
	 pupmIds="-1";//泵
}
//初始化
function initCache(tank,tube,inTank,outTank,pupm){
	if(tank!=null&&tank!=undefined&&tank!=",null"&&tank!=",")
	tankIds+=tank;
	if(tube!=null&&tube!=undefined&&tube!=",null"&&tube!=",")
	bTubeIds+=tube;
	if(inTank!=null&&inTank!=undefined&&inTank!=",null"&&inTank!=",")
	intankIds+=inTank;	
	if(outTank!=null&&outTank!=undefined&&outTank!=",null"&&outTank!=",")
	outtankIds+=outTank;	
	if(pupm!=null&&pupm!=undefined&&pupm!=",null"&&pupm!=",")
	pupmIds+=pupm;	
}
//添加工具
function addToolbarItem(graph, toolbar, prototype, image,tip,type) {
	var funct = function(graph, evt, cell) {
		graph.stopEditing(false);

		var pt = graph.getPointForEvent(evt);
		var vertex = graph.getModel().cloneCell(prototype);
		vertex.geometry.x = pt.x;
		vertex.geometry.y = pt.y;
		vertex.type = type;
		graph.addCell(vertex);
		graph.setSelectionCell(vertex);
	};

	// Creates the image which is used as the drag icon (preview)
	var img = toolbar.addMode(tip, image, funct);
	mxUtils.makeDraggable(img, graph, funct);
}

// 创建右键菜单
function createPopupMenu(graph, menu, cell, evt) {
//	var parent = graph.getDefaultParent();
	if (cell != null) {
		if (!cell.edge) {
			var type = cell.type;
			if(type == 'berth') {
				menu.addItem('关联泊位', null, function() {
					Flow.link(type,cell,graph);
				});
			}else if(type == 'bTube') {
				menu.addItem('关联主管线', null, function() {
					Flow.link(type,cell,graph);
				});
			}else if(type == 'sTube') {
				menu.addItem('关联过渡管线', null, function() {
					Flow.link(type,cell,graph);
				});
			}else if(type == 'tank') {
				if(cell.tanktype!=undefined&&cell.tanktype==0&&cell.tankflag==undefined){
					menu.addItem('关联倒出货罐', null, function() {
						cell.tankflag=2;//导出
						Flow.link(type,cell,graph);
					});
					menu.addItem('关联倒入货罐', null, function() {
						cell.tankflag=1;//导入
						Flow.link(type,cell,graph);
					});
				}else if(cell.tanktype!=undefined&&cell.tanktype==0&&cell.tankflag!=undefined&&cell.tankflag==2){
					menu.addItem('关联倒出货罐', null, function() {
						cell.tankflag=2;//导出
						Flow.link(type,cell,graph);
					});
				}else if(cell.tanktype!=undefined&&cell.tanktype==0&&cell.tankflag!=undefined&&cell.tankflag==1){
					menu.addItem('关联倒入货罐', null, function() {
						cell.tankflag=1;//导入
						Flow.link(type,cell,graph);
					});
				}else{
					menu.addItem('关联货罐', null, function() {
						Flow.link(type,cell,graph);
					});
				}
			}else if(type == 'park') {
				menu.addItem('关联车位', null, function() {
					Flow.link(type,cell,graph);
				});
			}else if(type == 'pupm') {
				menu.addItem('关联泵', null, function() {
					Flow.link(type,cell,graph);
				});
			}
			if(type!='berth')
			menu.addItem('删除', null, function() {
				deleteSub(cell,graph);
			});
//			menu.addItem('获取XML', null, function() {
//				getImgXml();
//			});
//			menu.addItem('保存图片', null, function() {
//				saveImgData();
//			});
		} 
	} else {
	}
};

// 获取XML数据
function getImgXml() {
	var enc = new mxCodec(mxUtils.createXmlDocument());
	var node = enc.encode(model);
	var content = mxUtils.getPrettyXml(node);
	return content;
}

function getXml(_graph) {
	if(!_graph){
		_graph=graph;	
	}
	var enc = new mxCodec(mxUtils.createXmlDocument());
	var node = enc.encode(_graph.getModel());
	var content = mxUtils.getPrettyXml(node);
	return content;
}

function getSVG() {
	return del_last(graph.container);
//	return graph.container.outerHTML;
}

function getSvg(_graph){
	if(!_graph){
      	_graph=graph;	
	}
	return del_last(_graph.container);
}

function del_last(elem){
	$this=$(elem);
	$this.find("svg>g:last-child>g:last-child").attr("class","ss");
//	$this.find('[pointer-events="none"]').parent().attr("class","ss");
	$this.find(".ss").empty();
	return $this.html();
}
//检验工艺流程是否有值
function validateSVG(){
 var cells=graph.model.cells;
 for(var i in cells){
	 var cell=cells[i];
	 if(cell.type=='bTube'||cell.type=='sTube'||cell.type=='tank'||cell.type=='pupm'||cell.type == 'berth'||cell.type == 'park'){
         if(cell.value==null||cell.value==undefined||cell.value==''){
        	 return false;
         }else {
        	 continue;
         }   		 
	 }
 }
 return true;
}


// 保存数据
function saveImgData() {
	var enc = new mxCodec(mxUtils.createXmlDocument());
	var node = enc.encode(model);
	var xmlContent = mxUtils.getPrettyXml(node);

	new $.msgbox({
				onClose : function() {
					if (this.getValue()) {
						
					}
				},
				type : 'confirm',
				content : '<div style="text-align:center;margin-top:10px;">保存当前图形？</div>'
			}).show();

}

// 删除当前Cell
function deleteSub(cell,graph) {
	var cells = [];
	graph.traverse(cell, true, function(vertex) {

		//cell.getId();
		//将之前的还原给list
		if(cell.type=='tank'){
			if(cell.tankflag==1){
				intankIds+=",";
				if(intankIds.indexOf(","+cell.key+",")!=-1){
					intankIds=intankIds.replace(","+cell.key+",",',');
					//处理储罐名显示
					var intankVal=","+$("#intankIds").val()+",";
					if(intankVal.indexOf(","+cell.value+",")!=-1){
						intankVal=intankVal.replace(","+cell.value+",",',');
						if(intankVal.length==1){
							$("#intankIds").val('');
						}else{
						$("#intankIds").val(intankVal.substring(1,intankVal.length-1));}
					}
					$("#flowmessage").val(util.FloatSub($("#flowmessage").val(),cell.tanknum));
					InboundOperation.checkBackFlowNum();
				}
				intankIds=intankIds.substring(0,intankIds.length-1);
				//处理管线id
				$("#intankIds").attr("data",intankIds.replace("-1,",""));
			}else if(cell.tankflag==2){
				outtankIds+=",";
				if(outtankIds.indexOf(","+cell.key+",")!=-1){
					outtankIds=outtankIds.replace(","+cell.key+",",',');
					//处理储罐名显示
					var outtankVal=","+$("#outtankIds").val()+",";
					if(outtankVal.indexOf(","+cell.value+",")!=-1){
						outtankVal=outtankVal.replace(","+cell.value+",",',');
						if(outtankVal.length==1){
							$("#outtankIds").val('');
						}else{
						$("#outtankIds").val(outtankVal.substring(1,outtankVal.length-1));}
					}
				}
				outtankIds=outtankIds.substring(0,outtankIds.length-1);
				//处理管线id
				$("#outtankIds").attr("data",outtankIds.replace("-1,",""));
			}else{
			tankIds+=",";
			if(tankIds.indexOf(","+cell.key+",")!=-1){
				tankIds=tankIds.replace(","+cell.key+",",',');
				//处理储罐名显示
				var tankVal=","+$("#tankIds").val()+",";
				if(tankVal.indexOf(","+cell.value+",")!=-1){
					tankVal=tankVal.replace(","+cell.value+",",',');
					if(tankVal.length==1){
						$("#tankIds").val('');
					}else{
					$("#tankIds").val(tankVal.substring(1,tankVal.length-1));}
				}
				$("#unloadingmsg").val(util.FloatSub($("#unloadingmsg").val(),cell.tanknum));
				InboundOperation.checkFreeNum();
			}
			tankIds=tankIds.substring(0,tankIds.length-1);
			//处理管线id
			$("#tankIds").attr("data",tankIds.replace("-1,",""));
			}
		}else if(cell.type=='bTube'||cell.type=='sTube'){
			bTubeIds+=",";
			if(bTubeIds.indexOf(","+cell.key+",")!=-1){
				bTubeIds=bTubeIds.replace(","+cell.key+",",',');
				//处理管线名显示
				var tubeVal=","+$("#tubeIds").val()+",";
				if(tubeVal.indexOf(","+cell.value+",")!=-1){
					tubeVal=tubeVal.replace(","+cell.value+",",',');
					if(tubeVal.length==1){
						$("#tubeIds").val('');
					}else{
					$("#tubeIds").val(tubeVal.substring(1,tubeVal.length-1));
					}
				}
				
			}
			bTubeIds=bTubeIds.substring(0,bTubeIds.length-1);
			//处理管线id
			$("tubeIds").attr("data",bTubeIds.replace("-1,",''));
		}else if(cell.type=='pupm'){
			pupmIds+=",";
			if(pupmIds.indexOf(","+cell.key+",")!=-1){
				pupmIds=pupmIds.replace(","+cell.key+",",',');
				//处理管线名显示
				var pupmVal=","+$("#pupmIds").val()+",";
				if(pupmVal.indexOf(","+cell.value+",")!=-1){
					pupmVal=pupmVal.replace(","+cell.value+",",',');
					if(pupmVal.length==1){
						$("#pupmIds").val('');
					}else{
					$("#pupmIds").val(pupmVal.substring(1,pupmVal.length-1));
					}
				}
				
			}
			pupmIds=pupmIds.substring(0,pupmIds.length-1);
			//处理泵id
			$("pupmIds").attr("data",pupmIds.replace("-1,",''));	
		}else if(cell.type=='berth'){
			//泊位
			
			
			
		}
		
//		cells.push(vertex);
		cells.push(cell);
		return true;
	});
	graph.removeCells(cells);
};

// 放大
function imgZoomIn() {
	graph.zoomIn();
}

// 缩小
function imgZoomOut() {
	graph.zoomOut();
}

// 还原
function imgZoomActual() {
	graph.zoomActual();
}

// 打印
function imgPreview() {
	var preview = new mxPrintPreview(graph, 1);
	preview.open();
}

// 上一步
function undoOpt() {
	editor.execute("undo");
}

// 下一步
function redoOpt() {
	editor.execute("redo");
}



var Flow = function() {
	var  dialog=null
	var link = function(type,cell,graph) {
		$.get(config.getResource()+"/pages/common/flow.jsp").done(function(data){
			dialog = $(data);
			var params={};
			util.urlHandleTypeahead("/product/select",dialog.find('#productId'));//初始化货品搜索
			if(cell.ptname!=undefined){
			dialog.find("#productId").val(cell.ptname);//itemGoodsData inboundoperation.js的全局变量
			dialog.find("#productId").attr('data',cell.ptid);
			 params={'productId':cell.ptid};
			 dialog.find("#productId").attr('disabled','disabled');
				dialog.find("#productId").attr('readonly','readonly');
				dialog.find(".searchDiv,.search,.reset").hide();
			}else{
				dialog.find("#productId").removeAttr('disabled');
				dialog.find("#productId").removeAttr('readonly');
				dialog.find(".searchDiv,.search,.reset").show();
			}
			dialog.find(".pumpDiv").hide();
			//重置
			dialog.find(".reset").click(function(){
				dialog.find('#productId').attr("data","");
					dialog.find('#productId').val("");
			});
			//搜索
			dialog.find(".search").click(function(){
				params={'productId':dialog.find("#productId").attr("data")};
				if(type=='pupm'){
					params.type=dialog.find("#type").val();
				}
				dialog.find('#flow').getGrid().search(params);
			});
			
			var url = "";
			var columns=null;
			if(type == 'berth'){
				dialog.find('.modal-title').html("关联泊位");
				url = config.getDomain()+"/berth/list";
				columns=[{title:'名称',name:"name",render:function(item,name,index){
					return "<a href='javascript:Flow.doLink("+cell.id+","+index+",\""+type+"\")'>"+item[name]+"</a>";
				}},{
					title:"船长(米)",
					name:"limitLength",
					render:function(item,name,index){
						return "≤ "+item.limitLength;
					}
				},{
					title:"吃水(米)",
					name:"limitDrought",
					render:function(item,name,index){
						return "≤ "+item.limitDrought;
					}
				},{
					title:"最大载重吨(吨)",
					name:"limitDisplacement",
					render:function(item,name,index){
						return "≤"+item.limitDisplacement;
					}
				}];
				
			}else if(type == 'bTube') {
				dialog.find('.modal-title').html("关联大管");
				bTubeIds=handleIds(bTubeIds);
				url = config.getDomain()+"/tube/list?type=0&&ids="+bTubeIds;
				columns=[{title:"名称",name:"name",render:function(item, name, index) {
					return "<a href='javascript:Flow.doLink("+cell.id+","+index+",\""+type+"\")'>"+item[name]+"</a>";
				}},{title:"货品",name:"productName"},{title:"状态",name:"description"}];
			}else if(type == 'sTube') {
				dialog.find('.modal-title').html("关联小管");
				bTubeIds=handleIds(bTubeIds);
				url = config.getDomain()+"/tube/list?type=1&&ids="+bTubeIds;
				columns=[{title:"名称",name:"name",render:function(item, name, index) {
					return "<a href='javascript:Flow.doLink("+cell.id+","+index+",\""+type+"\")'>"+item[name]+"</a>";
				}},{title:"货品",name:"productName"},{title:"状态",name:"description"}];
			}else if(type == 'tank') {
				dialog.find('.modal-title').html("关联货罐");
				if(cell.tankflag==1){
					intankIds=handleIds(intankIds);
					url = config.getDomain()+"/tank/list?ids="+intankIds;
				}else if(cell.tankflag==2){
					outtankIds=handleIds(outtankIds);
					url = config.getDomain()+"/tank/list?ids="+outtankIds;
				}else{
					tankIds=handleIds(tankIds);
					url = config.getDomain()+"/tank/list?ids="+tankIds;
				}
				columns=[{title:"罐号",name:"code",render:function(item, name, index) {
					return "<a href='javascript:Flow.doLink("+cell.id+","+index+",\""+type+"\")'>"+item[name]+"</a>";
				}},{title:"罐内货品",name:"productName"},{title:"空余容量",name:"capacityFree"},{title:"状态",name:"description"}];
			}else if(type == 'park') {
				dialog.find('.modal-title').html("关联车位");
//				dialog.find('.search').hide();
				url = config.getDomain()+"/park/list?ids="+parkIds;
				columns=[{title:"车位",name:"name",render:function(item, name, index) {
					return "<a href='javascript:Flow.doLink("+cell.id+","+index+",\""+type+"\")'>"+item[name]+"</a>";
				}},{title:"货品",name:"productName"}];
			}else if(type== 'pupm'){
				dialog.find(".pumpDiv").show();
				dialog.find('.modal-title').html("关联泵");
				url = config.getDomain()+"/pump/list?ids="+pupmIds;
				columns=[{title:"泵号",name:"name",render:function(item, name, index) {
					return "<a href='javascript:Flow.doLink("+cell.id+","+index+",\""+type+"\")'>"+item[name]+"</a>";
				}},{title:"货品",name:"productName"},{title:'性质',render:function(item){
					if(item.type==1){
						return "船发泵";
					}else if(item.type==2){
						return "车发泵 ";
					}else {
						return "";
					}
				}}];
			}
			if(type=='berth'){
                dialog.find('#goodsSearchForm').hide();				
			}
			dialog.find('[data-dismiss="modal"]').click(function(){
				dialog.remove();
			});
			
			dialog.find('#flow').grid({
	             identity: 'id',
	             isShowIndexCol : false,
	             columns:columns,
	             pageSize:10,
	             isShowPages : true,
	             searchCondition:params,
	             url: url,
	         });
			dialog.find('#save').on('click',function(){}).end().modal({
				keyboard: true
			});
			dialog.find('#close,.flowClose').on('click',function(){
				dialog.remove();
			});
		});
	};
	
	var doLink = function(id,index,type) {
		var cell = graph.model.getCell(id);
		if(cell == null) {
			return;
		}

		var row = dialog.find("div[data-role='flowTable']").getGrid().getItemByIndex(index);
		//将之前的还原
		if(cell.type=='tank'){
			if(cell.tankflag==1){
				intankIds+=",";
				if(intankIds.indexOf(","+cell.key+",")!=-1){
					intankIds=intankIds.replace(","+cell.key+",",',');
					//处理储罐名显示
					var intankVal=","+$("#intankIds").val()+",";
					if(intankVal.indexOf(","+cell.value+",")!=-1){
						intankVal=intankVal.replace(","+cell.value+",",',');
						if(intankVal.length==1){
							$("#intankIds").val('');
						}else{
						$("#intankIds").val(intankVal.substring(1,intankVal.length-1));}
					}
					$("#flowmessage").val(util.FloatSub($("#flowmessage").val(),cell.tanknum));
					InboundOperation.checkBackFlowNum();
				}
				intankIds=intankIds.substring(0,intankIds.length-1);
				//处理管线id
				$("#intankIds").attr("data",intankIds.replace("-1,",""));
			}else if(cell.tankflag==2){
				outtankIds+=",";
				if(outtankIds.indexOf(","+cell.key+",")!=-1){
					outtankIds=outtankIds.replace(","+cell.key+",",',');
					//处理储罐名显示
					var outtankVal=","+$("#outtankIds").val()+",";
					if(outtankVal.indexOf(","+cell.value+",")!=-1){
						outtankVal=outtankVal.replace(","+cell.value+",",',');
						if(outtankVal.length==1){
							$("#outtankIds").val('');
						}else{
						$("#outtankIds").val(outtankVal.substring(1,outtankVal.length-1));}
					}
				}
				outtankIds=outtankIds.substring(0,outtankIds.length-1);
				//处理管线id
				$("#outtankIds").attr("data",outtankIds.replace("-1,",""));
			}else{
			tankIds+=",";
			if(tankIds.indexOf(","+cell.key+",")!=-1){
				tankIds=tankIds.replace(","+cell.key+",",',');
				//处理储罐名显示
				var tankVal=","+$("#tankIds").val()+",";
				if(tankVal.indexOf(","+cell.value+",")!=-1){
					tankVal=tankVal.replace(","+cell.value+",",',');
					if(tankVal.length==1){
						$("#tankIds").val('');
					}else{
					$("#tankIds").val(tankVal.substring(1,tankVal.length-1));}
				}
				$("#unloadingmsg").val(util.FloatSub($("#unloadingmsg").val(),cell.tanknum));
				InboundOperation.checkFreeNum();
			}
			tankIds=tankIds.substring(0,tankIds.length-1);
			//处理管线id
			$("#tankIds").attr("data",tankIds.replace("-1,",""));
			}
		}else if(cell.type=='bTube'||cell.type=='sTube'){
			bTubeIds+=",";
			if(bTubeIds.indexOf(","+cell.key+",")!=-1){
				bTubeIds=bTubeIds.replace(","+cell.key+",",',');
				//处理管线名显示
				var tubeVal=","+$("#tubeIds").val()+",";
				if(tubeVal.indexOf(","+cell.value+",")!=-1){
					tubeVal=tubeVal.replace(","+cell.value+",",',');
					if(tubeVal.length==1){
						$("#tubeIds").val('');
					}else{
					$("#tubeIds").val(tubeVal.substring(1,tubeVal.length-1));
					}
				}
				
			}
			bTubeIds=bTubeIds.substring(0,bTubeIds.length-1);
			//处理管线id
			$("tubeIds").attr("data",bTubeIds.replace("-1,",''));
		}else if(cell.type=='pupm'){
			pupmIds+=",";
			if(pupmIds.indexOf(","+cell.key+",")!=-1){
				pupmIds=pupmIds.replace(","+cell.key+",",',');
				//处理管线名显示
				var pupmVal=","+$("#pupmIds").val()+",";
				if(pupmVal.indexOf(","+cell.value+",")!=-1){
					pupmVal=pupmVal.replace(","+cell.value+",",',');
					if(pupmVal.length==1){
						$("#pupmIds").val('');
					}else{
					$("#pupmIds").val(pupmVal.substring(1,pupmVal.length-1));
					}
				}
				
			}
			pupmIds=pupmIds.substring(0,pupmIds.length-1);
			//处理泵id
			$("pupmIds").attr("data",pupmIds.replace("-1,",''));	
		}
		//赋值
		if((type == 'bTube')||(type=='sTube')){
			bTubeIds+=","+row.id;
			var tubeVal=$("#tubeIds").val();
			if(tubeVal==""){
				$("#tubeIds").val(row.name);
			}else{
				if(type=='bTube'){
					$("#tubeIds").val(row.name+","+tubeVal);	
				}else{
					$("#tubeIds").val(tubeVal+","+row.name);
				}
			}
			var tubeData=$("#tubeIds").attr("data");
			if(tubeData==undefined||tubeData==""){
				$("#tubeIds").attr("data",row.id);
			}else{
				$("#tubeIds").attr("data",tubeData+","+row.id);
			}
		}else if(type == 'tank'){
			if(cell.tankflag==2){
				outtankIds+=","+row.id;
				var outtankVal=$("#outtankIds").val();
				if(outtankVal==""){
					$('#outtankIds').val(row.code);
				}else{
					$('#outtankIds').val(outtankVal+","+row.code);
				}
				var outtankData=$("#outtankIds").attr("data");
				if(outtankData==undefined){
					$("#outtankIds").attr("data",row.id);
				}else{
					$("#outtankIds").attr("data", outtankData+","+row.id);
				}
			}else if(cell.tankflag==1){
				intankIds+=","+row.id;
				var intankVal=$("#intankIds").val();
				if(intankVal==""){
					$('#intankIds').val(row.code);
				}else{
					$('#intankIds').val(intankVal+","+row.code);
				}
				$("#flowmessage").val(util.FloatAdd($("#flowmessage").val(),row.capacityFree));
				InboundOperation.checkBackFlowNum();
				var intankData=$("#intankIds").attr("data");
				if(intankData==undefined||intankData=='-1'||intankData==null){
					$("#intankIds").attr("data",row.id);
				}else{
					$("#intankIds").attr("data", intankData+","+row.id);
				}
			}else{
			tankIds+=","+row.id;
			var tankVal=$("#tankIds").val();
			if(tankVal==""){
				$('#tankIds').val(row.code);
			}else{
				$('#tankIds').val(tankVal+","+row.code);
			}
			$("#unloadingmsg").val(util.FloatAdd($("#unloadingmsg").val(),row.capacityFree));
			InboundOperation.checkFreeNum();
			var tankData=$("#tankIds").attr("data");
			if(tankData==undefined){
				$("#tankIds").attr("data",row.id);
			}else{
				$("#tankIds").attr("data", tankData+","+row.id);
			}
		}
		}else if(type=='pupm'){
			pupmIds+=","+row.id;
			var pupmVal=$("#pupmIds").val();
			if(pupmVal==""){
				$('#pupmIds').val(row.name);
			}else{
				$('#pupmIds').val(pupmVal+","+row.name);
			}
			var pupmData=$("#pupmIds").attr("data");
			if(pupmData==""){
				$("#pupmIds").attr("data",row.id);
			}else{
				$("#pupmIds").attr("data", pupmData+","+row.id);
			}
		}
		
		
		cell.key=row.id;
		if(type == 'tank'){
			cell.valueChanged(row.code);
			cell.tanknum=row.capacityFree;
		}else{
			cell.valueChanged(row.name);
		}
		graph.refresh();
		if($('div #flow').getGrid()!=null)
		$('div #flow').getGrid().destory();//清除缓存
//		$("div #flow").parents(".modal").remove();
		if($(".dialogChoiceItem")){
			$(".dialogChoiceItem").remove();
		}
	};
	function handleIds(id){
		if(id&&id.length>0&&id.substring(id.length-1,id.length)==','){
			return id.substring(0,id.length-1).replace('undefined','-1');
		}else {
			return id.replace('undefined','-1');
		}
	}
	
	return {
		link : link,
		doLink : doLink
	};
}();
