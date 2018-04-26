<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="portlet box grey">
	<div class="portlet-title hidden">
		<div class="caption">查看靠泊方案</div>
	</div>
	<div class="portlet-body form berthplan">
		<form class="form-horizontal">
			<div class="form-body">
			  
				<button type="button" class="btn btn-info col-md-offset-2 berthAssess">靠泊评估</button>
				<div class="form-group">
					<div class="col-md-9 col-md-offset-2">
					<h4 style="text-align:center">码头泊位接收指南和限制条件</h4>
						<div data-role="berthGrid" style="width:100%"></div>
					</div>
					</div>
					<div class="form-group">
                 <div class="col-md-12">
                 <label class="control-label col-md-2">富裕水深(米)</label>
						<div class="col-md-4">
						<label class="control-label" id="richDraught"></label>
                 </div>
                 <label class="control-label col-md-2">现场风向风力(级)</label>
                 <div class="col-md-4">
                 <label class="control-label" id="windPower"></label>
                 </div>
                 </div>
                 </div>
                 <div class="form-group" style="margin-top:38px;">
					<div class="col-md-12">
						<label class="col-md-2 control-label">泊位概况</label> 
						<div class="col-md-9">
						<textarea readonly class="form-control" rows="5" id="description"></textarea>
						</div>
					</div>
				</div>
                 
				<div class="form-group">
					<div class="col-md-12">
						<label class="col-md-2 control-label">安全措施</label> 
						<div class="col-md-9">
						<textarea readonly class="form-control" rows="5" id="safeInfo"></textarea>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="col-md-12">
						<label class="col-md-2 control-label">审核意见</label>
						<div class="col-md-9">
							<textarea readonly class="form-control" id="comment"></textarea>
						</div>
					</div>
				</div>
                
				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">制定人:</label>
						<div class="col-md-8">
							<label class="control-label" id="createUserId"></label>
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">制定日期:</label>
						<div class="col-md-8">
							<label class="control-label" id="createTime"></label>
						</div>
					</div>
					</div>

				<div class="form-group">
					<div class="col-md-6">
						<label class="control-label col-md-4">审批人:</label>
						<div class="col-md-8">
							<label class="control-label" id="reviewUserId"></label>
						</div>
					</div>
					<div class="col-md-6">
						<label class="control-label col-md-4">审批日期:</label>
						<div class="col-md-8">
							<label class="control-label" id="reviewTime" ></label>
						</div>
					</div>
				</div>
			</div>
		</form>
		<!-- END FORM-->
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal"
				onclick="javascript:history.go(-1);">返回</button>
		<shiro:hasPermission name="ABACKSTATUS">
		     <button type="button" class="btn btn-primary"
							onclick="InboundOperation.cleanToStatus(4)">回退到当前状态</button>
							</shiro:hasPermission>
		</div>
	</div>
</div>
<<script type="text/javascript">
$(function(){
	InboundOperation.initBerthPlan($(".berthplan"),2);
});
function initGrid(id){
	var columns=[{
		title:"泊位",
		name:"name",
		render:function(item,name,index){
			if(id!='-1'){
				$("#description").val(item.description);
				}
			return item.name;
		}
	},{
		title:"船长(米)",
		name:"limitLength",
		render:function(item,name,index){
			return "≤"+item.limitLength;
		}
	},{
		title:"吃水(米)",
		name:"limitDrought",
		render:function(item,name,index){
			return "≤"+item.limitDrought;
		}
	},{
		title:"最大载重(吨)",
		name:"limitDisplacement",
		render:function(item,name,index){
			return "≤"+item.limitDisplacement;
		}
	}];
	if($('[data-role="berthGrid"]').getGrid()!=null){
		$('[data-role="berthGrid"]').getGrid().destory();
	}
	var murl=config.getDomain() + "/berth/list"
	var isshow=true;
	if(id!='-1'){
		murl+="?id="+id;
		isshow=false;
	}
	$('[data-role="berthGrid"]').grid({
		identity : 'id',
		columns : columns,
		isShowIndexCol : isshow,
		isShowPages : false,
		url : murl
	});
	
}

</script>


