<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/resource/global/plugins/jquery.min.js" type="text/javascript"></script>
<table>
	<tr><td>原密码:</td><td><input type="password" name="opswd"></td></tr>
	<tr><td>新密码:</td><td><input type="password" name="password"></td></tr>
	<tr><td>确认新密码:</td><td><input type="password" name="repswd"></td></tr>
	<shiro:hasPermission name="userUpdate">
	<tr><td></td><td><input type="button" class="submit" value="提交"></td></tr>
	</shiro:hasPermission>
</table>
<input type="hidden" name="id" value="<shiro:principal property='id'/>">
<script type="text/javascript">
	$(function() {
		$(".submit").click(function() {
			if($("input[name='opswd']").val() == "") {
				alert("原密码不能为空");
				return;
			}
			if($("input[name='password']").val() == "") {
				alert("新密码不能为空");
				return;
			}
			if($("input[name='password']").val() != $("input[name='repswd']").val()) {
				alert("确认密码与新密码不一致");
				return;
			}
			
			var data = "id="+$("input[name='id']").val()+"&password="+$("input[name='password']").val()
				+"&opswd="+$("input[name='opswd']").val();

			$.ajax({
	    		url: "<%=basePath %>/auth/user/changePaswd",
	    		data: data,
	    		type: "POST",
	    		success: function(data){
	    			alert(data.msg);
	    			if(data.code == "0000") {
	    				window.close();
	    			}
	    		}
	    	});
		
			
		});
	});
</script>