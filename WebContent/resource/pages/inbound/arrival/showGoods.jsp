<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="modal fade" >
	<div class="modal-dialog modal-wide" style="width:820px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">货体流转图</h4>
			</div>
		<div class="modal-body">
			 <table id="treeTable1" style="width:100%" class="table">
			 <thead>
                                 <tr>
                                    <th>货体代码</th>
                                    <th>货主名</th>
                                    <th>提单代码</th>
                                    <th>提单类型</th>
                                    <th>货体总量</th>
                                    <th>调入总量</th>
                                    <th>调出总量</th>
                                    <th>当前存量</th>
                                    <th>剩余存量</th>
                                 </tr>
                              </thead>
                              <tbody></tbody>
			</table>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn default" data-dismiss="modal">关闭</button>
		</div>
	</div>
	<!-- /.modal-content -->
	</div>
</div>
 