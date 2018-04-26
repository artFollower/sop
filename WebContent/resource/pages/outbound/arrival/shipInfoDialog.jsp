<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style>
.modal-dialog {
	margin: 30px auto;
	width: 60%;
}
</style>
<div class="modal fade">
	<div class="modal-dialog modal-wide">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title">船舶基本信息</h4>
			</div>
			<div class="modal-body" style="padding-left:45px; padding-right:65px;">
			<div class="row">
				<div class="col-md-12">
					<form class="form-horizontal addGoodsForm">
						<div class="form-group">
							<table width="100%">
						
						<tr>
							<td class=" col-md-4" >
								<div class="form-group">
									<label class="control-label col-md-4">船长（米）</label>
									<div class="col-md-8 " id="shipLenth">
									<input id="shipLenth" readonly type="text"   class="form-control shipLenth">
									</div>
								</div>
							</td>
							
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">船宽（米）</label>
									<div class="col-md-8 " id="shipWidth">
									<input id="shipWidth" readonly type="text"   class="form-control shipWidth">
									</div>
								</div>
							</td>
							</tr>
						
						<tr>
							<td class=" col-md-4" >
								<div class="form-group">
									<label class="control-label col-md-4">满载吃水（米）</label>
									<div class="col-md-8 " id="shipDraught">
									<input id="shipDraught" readonly type="text"  class="form-control shipDraught">
									</div>
								</div>
							</td>
							
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">载重吨</label>
									<div class="col-md-8 " id="loadCapacity">
									<input id="loadCapacity" readonly type="text"  class="form-control loadCapacity">
									</div>
								</div>
							</td>
							</tr>
							
							<tr>
							<td class=" col-md-4" >
								<div class="form-group">
									<label class="control-label col-md-4">总吨</label>
									<div class="col-md-8 " id="grossTons">
									<input id="grossTons" readonly type="text"  class="form-control grossTons">
									</div>
								</div>
							</td>
							
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">净吨</label>
									<div class="col-md-8 " id="netTons">
									<input id="netTons" readonly type="text"  class="form-control netTons">
									</div>
								</div>
							</td>
							</tr>
							<tr>
							<td class=" col-md-4" >
								<div class="form-group">
									<label class="control-label col-md-4">船籍</label>
									<div class="col-md-8 " id="shipRegistry">
									<input id="shipRegistry" readonly type="text"  class="form-control shipRegistry">
									</div>
								</div>
							</td>
							
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">建造年份</label>
									<div class="col-md-8 " id="buildYear">
									<input id="buildYear" readonly type="text"  class="form-control buildYear">
									</div>
								</div>
							</td>
							</tr>
							
							<tr>
							<td class=" col-md-4" >
								<div class="form-group">
									<label class="control-label col-md-4">船主</label>
									<div class="col-md-8 " id="owner">
									<input id="owner" readonly type="text"  class="form-control owner">
									</div>
								</div>
							</td>
							
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">负责人</label>
									<div class="col-md-8 " id="manager">
									<input id="manager" readonly type="text"  class="form-control manager">
									</div>
								</div>
							</td>
							</tr>
							
							<tr>
							<td class=" col-md-4" >
								<div class="form-group">
									<label class="control-label col-md-4">联系人</label>
									<div class="col-md-8 " id="contactName">
									<input id="contactName" readonly type="text"  class="form-control contactName">
									</div>
								</div>
							</td>
							
							<td class=" col-md-4">
								<div class="form-group">
									<label class="control-label col-md-4">联系电话</label>
									<div class="col-md-8 " id="contactPhone">
									<input id="contactPhone" readonly type="text"  class="form-control contactPhone">
									</div>
								</div>
							</td>
							</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
			</div>
		</div>
	</div>
</div>
<script>
		
	
		
	</script>