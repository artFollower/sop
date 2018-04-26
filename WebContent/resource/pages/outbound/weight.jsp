<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
.page-500 .number {
    color: #ec8c8c;
    display: inline-block;
    font-size: 128px;
    font-weight: 300;
    letter-spacing: -10px;
    line-height: 128px;
    text-align: right;
    margin : auto;
    width : 80%;
}
.page-500 .unit {
    color: #000;
    display: inline-block;
    font-size: 60px;
    font-weight: 100;
    letter-spacing: -10px;
    line-height: 88px;
    text-align: center;
    margin : auto;
    width : 20%;
}
</style>
<div class="portlet">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-ship"></i>车发称重
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 page-500">
			<div class=" number">00.00</div><div class="unit">吨</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn blue button-open">开启</button>
		<button type="button" class="btn blue button-close hidden">停止</button>
	</div>
</div>
<script type="text/javascript">
	var weightTimmer;
	var weightFlag = true;
	$(function() {
		
		$(".button-close").click(function() {
			$(".button-open").removeClass("hidden");
			$(this).addClass("hidden");
			clearTimeout(weightTimmer);
		});
		$(".button-open").click(function() {
			$(".button-close").removeClass("hidden");
			$(this).addClass("hidden");
			getWeight();
		});
	});
	function getWeight() {
		weightTimmer = setInterval(function() {
			if(weightFlag) {
				weightFlag = false;
				$.ajax({
					type : "post",
					url : config.getDomain()+"/comm/get",
					dataType : "json",
					success : function(data) {
						weightFlag = true;
						if(data.code=="0000"){
							$(".page-500 .number").html(data.msg);
						}
					}
				});
			}
		},100);
	}
</script>
