<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/resource/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="<%=basePath %>/resource/admin/js/flow.js" type="text/javascript"></script>
<script src="<%=basePath %>/resource/global/plugins/mxgraph/src/js/mxClient.js" type="text/javascript"></script>
<script type="text/javascript">
	mxBasePath = '<%=request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ request.getContextPath() %>/resource/global/plugins/mxgraph/src';
</script>

<input type="button" id="fb" value="第一个"/>
<input type="button" id="sb" value="第二个"/><br>

<div id="toolbarContainer" style="overflow: hidden; width: 100px; height: 22px; z-index: 999; border: 1px solid #d8d8d8; border-radius: 3px;"></div>
<div id="contentDiv" class="content" style="border: 1px solid #d8d8d8; border-radius: 3px; float: left; height: 350px; width: 100%;">
	<div id="graphContainer" style="overflow: hidden; width: 100%; height: 330px; margin-top: 0px;"></div>
</div>
<div id="toolbarContainer1" style="overflow: hidden; width: 100px; height: 22px; z-index: 999; border: 1px solid #d8d8d8; border-radius: 3px;"></div>
<div id="contentDiv1" class="content" style="border: 1px solid #d8d8d8; border-radius: 3px; float: left; height: 350px; width: 100%;">
	<div id="graphContainer1" style="overflow: hidden; width: 100%; height: 330px; margin-top: 0px;"></div>
</div>
<script type="text/javascript">
		$(function() {

			var graph1 = noticeFlow($('#graphContainer'),$('#toolbarContainer'));
			
			var graph2 = noticeFlow($('#graphContainer1'),$('#toolbarContainer1'));
			
			$("#fb").click(function() {
				alert(getXml(graph1));
			});
			
			$("#sb").click(function() {
				alert(getXml(graph2));
			});

		});
		
		
		
	</script>
