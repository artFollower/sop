/**
 * 树表组件
 * @author 解伟
 * @version 1.0.1
 * @lastUpdateDate 20151114
 * @mail xiewei@skycloudtech.com
 */
(function($) {
	var opts = null;

	var deapth = 0;
	var wordLength = 0;
	var headWidth = 0;

	function renderHeader(container) {
		var _html = "";
		var columns = opts.columns;
		for ( var j in columns) {
			var column = columns[j];
			if (j == 1) {
				_html += "<div class='' style='padding: 0px;'>"
			}
			if (j == 0) {
				_html += "<div class='first skytreegrid-head'>" + column.title
						+ "</div>";
			}
			else {
				headWidth += !isNull(column.width) ? column.width : 80;
				_html += "<div class='skytreegrid-th' style='width:"
						+ (!isNull(column.width) ? column.width : "80")
						+ "px;'>" + column.title + "</div>";
			}
			if (j == (columns.length - 1)) {
				_html += "</div>"
			}
		}
		_html += "<div class='clearfix'></div>";
		$(container).append(_html);
	}
	//初始化界面
	function renderData(data, index) {
		var _html = "";
		_html += "<ul class='jstree-children'>";
		var columns = opts.columns;
		for ( var i in data) {
			var node = data[i];
			_html += "<li class='jstree-node";
			_html += isLeaf(node);
			_html += isLast(data, i);
			_html += "' ";
			if (index == 0) {
				_html += "style='margin-left: 16px;'";
			}
			_html += "><div class='row'>";
			for ( var j in columns) {
				var column = columns[j];
				if (j == 1) {
					_html += "<div style='padding: 0px;'>"
				}
				if (j == 0) {
					try{
						if (node[column.name].length > wordLength) {
							wordLength = node[column.name].length;
						}
					}catch(err){}
					if (column.render != undefined && column.render != null) {
						_html += "<div class='first skytreegrid" + isLeaf(node)
								+ "' style='margin-left: -"
								+ (parseInt(index * 24)+1) + "px; padding-left: "
								+ (24 * parseInt(index) + 15)
								+ "px;'><i class='jstree-icon jstree-ocl'></i>"
								+ column.render(node, column.name, i)
								+ "</div>";
					}
					else {
						_html += "<div class='first skytreegrid" + isLeaf(node)
								+ "'style='margin-left: -"
								+ (parseInt(index) * 24+1) + "px; padding-left: "
								+ (24 * parseInt(index) + 15)
								+ "px;'><i class='jstree-icon jstree-ocl'></i>"
								+ node[column.name] + "</div>";
					}
				}
				else {
					if (column.render != undefined && column.render != null) {
						_html += "<div class='"
								+ isLastColumn(columns.length, j)
								+ " skytreegrid" + isLeaf(node)
								+ "' style='width:"
								+ (!isNull(column.width) ? column.width : "80")
								+ "px;'>" + column.render(node, column.name, i)
								+ "</div>";
					}
					else {
						_html += "<div class='"
								+ isLastColumn(columns.length, j)
								+ " skytreegrid' style='width:"
								+ (!isNull(column.width) ? column.width : "80")
								+ "px;'>" + node[column.name] + "</div>";
					}
				}
				if (j == (columns.length - 1)) {
					_html += "</div>"
				}
			}
			_html += "</div>";
			if (node.children != undefined && node.children != null
					&& node.children.length != 0) {
				var _index = parseInt(index) + 1;
				_html += renderData(node.children, _index);
				if (_index > deapth) {
					deapth = _index;
				}
			}
			_html += "</li>";
		}

		_html += "</ul>";
		return _html;
	}
	//判断是否是最后一个单元格
	function isLastColumn(columnLength, index) {
		if ((columnLength - 1) == index) { return " skytreegrid-last"; }
		return "";
	}
	//判断是否是叶子节点
	function isLeaf(node) {
		if (node.children == undefined || node.children == null
				|| node.children.length == 0) {
			return " jstree-leaf";
		}
		else {
			return " jstree-open";
		}
	}
	//判断是否是最后一个节点
	function isLast(data, index) {
		if ((data.length - 1) == index) {
			return " jstree-last";
		}
		else {
			return "";
		}
	}
	//加载数据
	function loadData(container) {
		
		var localdata=opts.localdata;
		if(opts.localdata){

			headWidth = 0;
			deapth = 0;
			wordLength = 0;
			var html = renderData(localdata, 0);
			renderHeader(container);
			$(container).append(html);
			open(container);
			var maxFirst = 0;
			$.each($(container).find(".first"),function(id,obj) {
				var ml = $(obj).css("margin-left");
				ml = parseInt(ml.replace("px",'').replace("-",""));
				if(ml > maxFirst) {
					maxFirst = ml;
				}
			});
			console.log(maxFirst);
			$(container).css("width",
					(maxFirst + wordLength * 15 + 34+headWidth) + "px");
			$(container).find(".first").css("width",
					(maxFirst + wordLength * 15 + 34) + "px");
		
		}else{
			$.ajax({
				type : "post",
				url : opts.url,
				dataType : "json",
				success : function(data) {
					if (data.code == "0000") {
						headWidth = 0;
						deapth = 0;
						wordLength = 0;
						var html = renderData(data.data, 0);
						renderHeader(container);
						$(container).append(html);
						open(container);
						var maxFirst = 0;
						$.each($(container).find(".first"),function(id,obj) {
							var ml = $(obj).css("margin-left");
							ml = parseInt(ml.replace("px",'').replace("-",""));
							if(ml > maxFirst) {
								maxFirst = ml;
							}
						});
						console.log(maxFirst);
						$(container).css("width",
								(maxFirst + wordLength * 15 + 34+headWidth) + "px");
						$(container).find(".first").css("width",
								(maxFirst + wordLength * 15 + 34) + "px");
					}
				}
			});
		}
		
		
		
	}

	//展开
	function open(container) {
		$(container).find("div .jstree-open").unbind();
		$(container).find("div .jstree-open").click(function() {
			$(this).parent(".row").next("ul").hide();
			$(this).addClass("jstree-closed");
			$(this).removeClass("jstree-open");
			close(container);
		});
	}
	//关闭
	function close(container) {
		$(container).find("div .jstree-closed").unbind();
		$(container).find("div .jstree-closed").click(function() {
			$(this).parent(".row").next("ul").show();
			$(this).addClass("jstree-open");
			$(this).removeClass("jstree-closed");
			open(container);
		});
	}

	$.fn.cloudTreeGrid = function(options) {
		opts = options;
		loadData($(this));
	};
	/**
	 *  Plugin's default options
	 */
	$.fn.cloudTreeGrid.defaults = {
		initialState : 'expanded',
		saveState : false,
		saveStateMethod : 'cookie',
		saveStateName : 'tree-grid-state',
		expanderTemplate : '<span class="cloudTreeGrid-expander"></span>',
		indentTemplate : '<span class="cloudTreeGrid-indent"></span>',
		expanderExpandedClass : 'fa fa-minus-square-o',
		expanderCollapsedClass : 'fa fa-plus-square-o',
		treeColumn : 0,
		columns : [],
		url : '',
		localdata : '',
		getExpander : function() {
			return $(this).find('.cloudTreeGrid-expander');
		},
		getNodeId : function() {
			var template = /cloudTreeGrid-([A-Za-z0-9_-]+)/;
			if (template.test($(this).attr('class'))) { return template.exec($(
					this).attr('class'))[1]; }
			return null;
		},
		getParentNodeId : function() {
			var template = /cloudTreeGrid-parent-([A-Za-z0-9_-]+)/;
			if (template.test($(this).attr('class'))) { return template.exec($(
					this).attr('class'))[1]; }
			return null;
		},
		getNodeById : function(id, cloudTreeGridContainer) {
			var templateClass = "cloudTreeGrid-" + id;
			return cloudTreeGridContainer.find('tr.' + templateClass);
		},
		getChildNodes : function(id, cloudTreeGridContainer) {
			var templateClass = "cloudTreeGrid-parent-" + id;
			return cloudTreeGridContainer.find('tr.' + templateClass);
		},
		getcloudTreeGridContainer : function() {
			return $(this).closest('table');
		},
		getRootNodes : function(cloudTreeGridContainer) {
			var result = $
					.grep(
							cloudTreeGridContainer.find('tr'),
							function(element) {
								var classNames = $(element).attr('class');
								var templateClass = /cloudTreeGrid-([A-Za-z0-9_-]+)/;
								var templateParentClass = /cloudTreeGrid-parent-([A-Za-z0-9_-]+)/;
								return templateClass.test(classNames)
										&& !templateParentClass
												.test(classNames);
							});
			return $(result);
		},
		getAllNodes : function(cloudTreeGridContainer) {
			var result = $.grep(cloudTreeGridContainer.find('tr'), function(
					element) {
				var classNames = $(element).attr('class');
				var templateClass = /cloudTreeGrid-([A-Za-z0-9_-]+)/;
				return templateClass.test(classNames);
			});
			return $(result);
		},
		//Events
		onCollapse : null,
		onExpand : null,
		onChange : null

	};
})(jQuery);
