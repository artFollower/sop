<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.fc-event{
font-size: 15px;
line-height: 2;
}
</style>
</head>
<body>
	<div class="row">
	<div class="col-md-12">
	<div class="portlet box blue calendar" style="border-color: #777;">
	  <div class="portlet-title" style="background-color: #777;">
	  <div class="caption">
	  <i class="fa fa-gift"></i>
	     分流台账
	  </div>
	  </div>
	  
	<div class="portlet-body">
	<div class="row">
            <div class="col-md-12">
       <div id="calendar" class="has-toolbar"></div>
       </div>
       </div>
       </div>
	</div>
	</div>
</div>
</body>
</html>