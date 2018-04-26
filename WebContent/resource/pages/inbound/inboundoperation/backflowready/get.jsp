<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="rows col-md-12 backflowready">
		<div class="btn-group buttons">
				<button id="btn" class="btn btn-primary" onclick="InboundOperation.dialogBackFlowNotify();">
					<i class="icon-edit"></i>打回流作业通知单
				</button>
<!-- 				<button id="btn" style="margin-left:4px" class="btn btn-primary" onclick="InboundOperation.dialogPipingNotify(2);"> -->
<!-- 					<i class="icon-edit"></i>配管作业通知单 -->
<!-- 				</button>  -->
				<button id="btn" style="margin-left:4px" class="btn btn-primary" onclick="InboundOperation.dialogDynamicNotify(2);">
					<i class="icon-edit"></i>动力班作业通知单
				</button>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal" onclick="javascript:history.go(-1);">返回</button>
			<button type="button" class="btn btn-primary" id="submit">提交</button>
		</div>
		</div>
		<script>
		$(function(){
			InboundOperation.initBackFlowReady($(".backflowready"));
		});
		</script>