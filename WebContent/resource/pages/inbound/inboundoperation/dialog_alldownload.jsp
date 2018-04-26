<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<style>
<!--
#flow {
	width: 100%;
}
.modal-dialog {
	margin: 30px auto;
	width: 500px;
}
</style>
<div class="modal fade addLivingFeeBill">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">导出列表</h4>
			</div>
			<div class="modal-body">
			
				<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form action="#" class="form-horizontal" >
				<div class="form-body">
					<table width="100%">
					<tr>
					<td class=" col-md-12">
								<div class="form-group">
									<label class="control-label col-md-8" style="text-align: center">接卸方案</label>
									<div  >
									<a href="javascript:void(0)" id="alldown1"  class="control-label btn btn-xs blue"><span class="fa fa-file-excel-o" title="导出"></span></a>
		 						</div>
								</div>
							</td>
					</tr>
					<tr>
					<td class=" col-md-12">
								<div class="form-group">
									<label class="control-label col-md-8" style="text-align: center">靠泊方案</label>
									<div  >
									<a href="javascript:void(0)" id="alldown2"  class="control-label btn btn-xs blue"><span class="fa fa-file-excel-o" title="导出"></span></a>
		 						</div>
								</div>
							</td>
					</tr>
					<tr>
					<td class=" col-md-12">
								<div class="form-group">
									<label class="control-label col-md-8" style="text-align: center">动力班接卸作业通知单</label>
									<div  >
									<a href="javascript:void(0)" id="alldown3"  class="control-label btn btn-xs blue"><span class="fa fa-file-excel-o" title="导出"></span></a>
		 						</div>
								</div>
							</td>
					</tr>
					<tr>
					<td class=" col-md-12">
								<div class="form-group">
									<label class="control-label col-md-8" style="text-align: center">码头接卸作业通知单</label>
									<div  >
									<a href="javascript:void(0)" id="alldown4"  class="control-label btn btn-xs blue"><span class="fa fa-file-excel-o" title="导出"></span></a>
		 						</div>
								</div>
							</td>
					</tr>
					<tr>
					<td class=" col-md-12">
								<div class="form-group">
									<label class="control-label col-md-8" style="text-align: center">打循环方案</label>
									<div  >
									<a href="javascript:void(0)" id="alldown5"  class="control-label btn btn-xs blue"><span class="fa fa-file-excel-o" title="导出"></span></a>
		 						</div>
								</div>
							</td>
					</tr>
					<tr>
					<td class=" col-md-12">
								<div class="form-group">
									<label class="control-label col-md-8" style="text-align: center">倒罐方案</label>
									<div  >
									<a href="javascript:void(0)" id="alldown6"  class="control-label btn btn-xs blue"><span class="fa fa-file-excel-o" title="导出"></span></a>
		 						</div>
								</div>
							</td>
					</tr>
					</table>
				</div>
				</form>
				</div>
				
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal"  class="btn btn-default" id="close">取消</button>
			</div>
		</div>
	</div>
</div>