<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class=" modal-footer deliverReadyDiv" style="padding-top: 0px; padding-bottom: 0px; border-top: 0px;">
	<shiro:hasPermission name="AOUTBOUNDWORKNOTICE">
	<button onclick="Outbound.dialogDockOutboundNotify(this);" style="margin-left:4px" class="btn btn-primary" id="dockshipNotice" >
					<i class="icon-edit"></i>码头船发作业通知单</button></shiro:hasPermission>
				<shiro:hasPermission name="AOuTBOUNDPOWERNOTICE">
				<button onclick="Outbound.dialogOperationOutboundNotify(this);" style="margin-left:4px" class="btn btn-primary" id="dynamicshipNotice">
					<i class="icon-edit"></i>操作班船发作业通知单</button></shiro:hasPermission>
				<shiro:hasPermission name="AOUTBOUNDZHUANSHUNOTICE">
				<button id="zhuanshuNotice" class="btn btn-primary" style="margin-left:4px" onclick="Outbound.dialogZhuanshuNotify(this);">
	<i class="icon-edit"></i>转输作业通知单</button></shiro:hasPermission>
</div>
	<div class="portlet-body form deliverReadyDiv">
		<form action="#" class="form-horizontal">
			<div class="form-body">
				<div class="form-group" style="display: none;">
				<label class="hidden" id="transportId"></label>
				<h4 class="form-section">基本信息</h4>
						<label class="control-label col-md-1">使用罐号</label>
						<div class="col-md-3">
							<input type="text" id="tankIds" data="" readonly class="form-control"  />
						</div>
						<label class="control-label col-md-1">使用管线</label>
						<div class="col-md-3">
							<input type="text" id="tubeIds" readonly  class="form-control" />
						</div>
						<label class="control-label col-md-1">使用泵号</label>
						<div class="col-md-3">
							<input type="text" id="pupmIds" readonly  class="form-control" />
						</div>
						<div class="col-md-3 hidden">
						<label class="control-label col-md-4">计划泊位</label>
						<div class="col-md-8">
							<input type="text" data-required="1" class="form-control"  id="berthName"/>

						</div>
					</div>
						</div>
	            <div class="form-group">
	            <label class="control-label col-md-2">开泵时间：</label>
	             <label style="text-align:left;" class="control-label col-md-4" id="openPumpTime"></label>
	              <label class="control-label col-md-2">停泵时间：</label>
	               <label style="text-align:left;"  class="control-label col-md-4" id="stopPumpTime"> </label>
	            
	            </div>
				<h4 class="form-section">工艺流程</h4>
				<div class="form-group">
					<div class="col-md-12">
					<div id="contentDiv" class="content" style="border: 1px solid rgb(216, 216, 216); position: relative; overflow-x: hidden; overflow-y: auto; cursor: move; width: 631px; height: 350px; margin: 0 auto;">
					<div id="toolbarContainer" style="overflow: hidden; width: 100px; height: 20px; margin-top: 0px; z-index: 999; border: 1px solid #d8d8d8; border-radius: 3px;"></div>
					 <div id="graphContainer" style="overflow: hidden; width: 100%; height: 320px; margin-top: 0px;"></div>
                     </div>
						</div>
				</div>
			</div>
		</form>
        <div  class="modal-footer firDiv">
				<button type="button" class="btn btn-default" data-dismiss="modal" onclick="javascript:history.go(-1);">返回</button>
				<shiro:hasPermission name="AOUTBOUNDWORKUPDATE"><button type="button" key="0"  class="btn btn-primary"  id="save">保存</button></shiro:hasPermission>
				<shiro:hasPermission name="AOUTBOUNDWORKUPDATE"><button type="button" key="2" class="btn btn-primary" id="submit">提交</button></shiro:hasPermission>
				</div>
</div>