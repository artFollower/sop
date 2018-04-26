<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
<style type="text/css">
label {
    font-size: 12px;
}
.grid-table-body {
    height: 315px;
    min-height: 50px;
    overflow: auto;
}
</style>
</head>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box grey">
			<div class="portlet-body form">
						<!-- BEGIN FORM-->
						<form id="arrivaldetailForm" class="form-horizontal">
						<div class="form-body">
						   <table width="100%">
						   <tr>
						   <td colspan="5"><h4 class="form-section">基本信息</h4></td>
						   </tr>
						   <tr>
						   <td><div class="form-group">
						   <label id="tp1" class="col-md-6 control-label">船舶英文名:</label>
						   <label class="control-label mda col-md-6" key="shipName" style="text-align:left" ></label>
						   <label class="hidden tab4_0"></label>
						   </div></td>
						   <td><div class="form-group notTransport">
						   <label class="col-md-6 control-label">船舶中文名:</label>
						   <label class="control-label mda col-md-6" key="shipRefName" style="text-align:left"  ></label>
						   </div></td>
						   <td><div class="form-group">
						   <label id="tp2" class="col-md-6 control-label">预计到港日期:</label>
						   <label class="control-label mda col-md-6" key="arrivalStartTime" style="text-align:left"></label>
						   </div></td>
						   </tr>
						   <tr>
						   <td class="notTransport"><div class="form-group">
						   <label class="col-md-6 control-label">船长(米):</label>
						   <label class="control-label mda col-md-6" key="shipLenth" style="text-align:left"></label>
						   </div></td>
						   <td class="notTransport"><div class="form-group">
						   <label class="col-md-6 control-label">船宽(吨):</label>
						   <label class="control-label mda col-md-6" key="shipWidth" style="text-align:left"></label>
						   </div></td>
						   <td class="notTransport"><div class="form-group">
						   <label class="col-md-6 control-label">到港吃水(米):</label>
						   <label class="control-label mda col-md-6" key="shipArrivalDraught" style="text-align:left"></label>
						   </div></td>
						   
						   </tr>
						   <tr>
						    <td class="notTransport"><div class="form-group">
						   <label class="col-md-6 control-label">载重吨(吨):</label>
						   <label class="control-label mda col-md-6" key="loadCapacity" style="text-align:left"></label>
						   </div></td>
						    <td class="notTransport"><div class="form-group">
						   <label class="col-md-6 control-label">净吨(吨):</label>
						   <label class="control-label mda col-md-6" key="netTons" style="text-align:left"></label>
						   </div></td>
						   <td class="notTransport"><div class="form-group">
						   <label class="col-md-6 control-label">泊位:</label>
						   <label class="control-label mda col-md-6" key="berthName" style="text-align:left"></label>
						   </div></td>
						   </tr>
						   <shiro:hasPermission name="ACHECKINBOUNDGOODSDETAIL">
						   <tr>
						   <td colspan="5"><h4 class="form-section">货品信息&nbsp;<a href="javascript:void(0)"
						onclick="InboundOperation.openHide(this,3);"><i
						class="fa fa-chevron-left"></i></a></h4></td>
						   </tr>
						   <tr>
						   <td colspan="6">  
						   <div class="form-group dialog-warning3" hidden="true">   
						    <div class="col-md-8 col-md-offset-2">                                
						   <div data-role="goodsmsgGrid" class="mda " key="goodsMsg" style="width:100%"></div>
						   <div data-role="goodstotalmsgGrid" style="width:100%"></div>
						   </div>
						   </div>
						   </td>
						   </tr>
						   </shiro:hasPermission>
						   </table>
						    <Input type="hidden" data-role="inboundoperationGrid"/>
						</div>
						</form>
						<!-- END FORM-->
					</div>
					</div>
			<div class=" tabs-top tabbable tabbable-custom boxless portlet-body" >
				<ul class="nav nav-tabs">
				<shiro:hasPermission name="MINBOUNDWORKPLAN">
					<li class=""><a data-toggle="tab" href="javascript:void(0);" id="tab1">作业计划</a>
					</li>
					</shiro:hasPermission>
								<shiro:hasPermission name="MINBOUNDBERTHING">
					<li class=""><a data-toggle="tab" href="javascript:void(0);" id="tab2">靠泊方案</a>
					</li>
					</shiro:hasPermission>
								<shiro:hasPermission name="MUNLOADINGPROGRAM">
					<li class="dropdown active ">
                           <a data-toggle="dropdown" class="dropdown-toggle" id="tab3" href="javascript:void(0);">接卸方案 <i class="fa fa-angle-down"></i></a>
                           <ul role="menu" class="dropdown-menu ul_tab3">
                              <li class="active"><a  onclick="InboundOperation.changeUnloadData(0,this,3);return false;" id="tab3_0" href="javascript:void(0);">接卸方案</a></li>
                              <li class="divider"></li>
                             <%--  <shiro:hasPermission name="AUNLOADINGADD"> --%>
                              <li><a type="button" style="color:#fff" class="btn btn-primary" id="addUnloading">添加</a></li>
                             <%--  </shiro:hasPermission> --%>
                           </ul>
                        </li>
					</shiro:hasPermission>
								<shiro:hasPermission name="MUNLOADINGWORK">
					<li class="dropdown active">
                           <a data-toggle="dropdown" class="dropdown-toggle" id="tab4" href="javascript:void(0);">接卸准备 <i class="fa fa-angle-down"></i></a>
                           <ul role="menu" class="dropdown-menu ul_tab4">
                              <li class="active"><a  id="tab4_0" onclick="InboundOperation.changeUnloadData(0,this,4)" href="javascript:void(0);">接卸准备</a></li>
                              <li class="divider"></li>
                           </ul>
                        </li>
					</shiro:hasPermission>
								<shiro:hasPermission name="MBACKFLOWPROGRAM">
					<li class="dropdown active notTransport">
                           <a data-toggle="dropdown" class="dropdown-toggle" id="tab5" href="javascript:void(0);">打循环方案<i class="fa fa-angle-down"></i></a>
                           <ul role="menu" class="dropdown-menu ul_tab5">
                              <li class="divider"></li>
                              <%-- <shiro:hasPermission name="ABACKFLOWADD"> --%>
                              <li><a type="button" style="color:#fff" class="btn btn-primary" id="addBackFlow">添加</a></li>
                             <%--  </shiro:hasPermission> --%>
                           </ul>
                        </li>
					 </shiro:hasPermission>
								<shiro:hasPermission name="MAMOUNTCONFIRM">
					<li class=""><a data-toggle="tab" href="javascript:void(0);" id="tab6">数量确认</a>
					</li>
					</shiro:hasPermission>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active green" id="tab"></div>
				</div>
			</div>
		</div>
	</div>	