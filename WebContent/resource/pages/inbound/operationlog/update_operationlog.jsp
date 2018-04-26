<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
			<div class="row">
				<div class="col-md-12">
				<div class="portlet box grey">
				 <div class="portlet-title">
				  <div class="caption">修改调度日志</div>
           			 </div>
				  <div class="portlet-body form">
					<form class="form-horizontal addGoodsForm">
					<div classs="form-body" style="padding-top:10px;">
					<table width="100%">
					<tr><td><div class="form-group" >
							<label class="control-label col-md-4">船名:</label>
							<div class="col-md-8" >
							 <input type="text" id="a" class="form-control"/>
							</div>
						</div></td><td><div class="form-group">
							<label class="control-label col-md-4">品种:</label>
							<div class="col-md-8">
						  <input type="text" id="b"  class="form-control"/>
							</div>
						</div></td><td><div class="form-group">
							<label class="control-label col-md-4">卸货数量(t):</label>
							<div class="col-md-8">
						  <input type="text" id="c"  class="form-control"/>
							</div>
						</div></td></tr>
					<tr><td><div class="form-group">
							<label class="control-label col-md-4">工艺管线:</label>
							<div class="col-md-8" >
							 <input type="text" id="e" class="form-control"/>
							</div>
						</div></td><td><div class="form-group">
							<label class="control-label col-md-4">泊位:</label>
							<div class="col-md-8" >
							 <input type="text" id="f" class="form-control"/>
							</div>
						</div></td><td><div class="form-group">
							<label class="control-label col-md-4">到港时间:</label>
							<div class="col-md-8" >
							<div class="input-group date form_datetime">                                       
		                                    <input type="text" size="16" readonly class="form-control signDate" id="g"  data-required="1">
		                                    <span class="input-group-btn">
		                                    	<button class="btn default date-set" type="button"><i class="icon-calendar"></i></button>
		                                    </span>
		                                 </div>
							</div>
						</div></td></tr>
					<tr><td><div class="form-group">
							<label class="control-label col-md-4">开泵时间:</label>
							<div class="col-md-8" >
							<div class="input-group date form_datetime">                                       
		                                    <input type="text" size="16" readonly class="form-control signDate" id="h"  data-required="1">
		                                    <span class="input-group-btn">
		                                    	<button class="btn default date-set" type="button"><i class="icon-calendar"></i></button>
		                                    </span>
		                                 </div>
							</div>
						</div></td><td><div class="form-group">
							<label class="control-label col-md-4">停泵时间:</label>
							<div class="col-md-8" >
							 <div class="input-group date form_datetime">                                       
		                                    <input type="text" size="16" readonly class="form-control signDate" id="i"  data-required="1">
		                                    <span class="input-group-btn">
		                                    	<button class="btn default date-set" type="button"><i class="icon-calendar"></i></button>
		                                    </span>
		                                 </div>
							</div>
						</div></td><td><div class="form-group">
							<label class="control-label col-md-4">拆管时间:</label>
							<div class="col-md-8" >
							 <div class="input-group date form_datetime">                                       
		                                    <input type="text" size="16" readonly class="form-control signDate" id="ii"  data-required="1">
		                                    <span class="input-group-btn">
		                                    	<button class="btn default date-set" type="button"><i class="icon-calendar"></i></button>
		                                    </span>
		                                 </div>
							</div>
						</div></td></tr>
						<tr><td><div class="form-group">
							<label class="control-label col-md-4">离港时间:</label>
							<div class="col-md-8" >
							 <div class="input-group date form_datetime">                                       
		                                    <input type="text" size="16" readonly class="form-control signDate" id="j"  data-required="1">
		                                    <span class="input-group-btn">
		                                    	<button class="btn default date-set" type="button"><i class="icon-calendar"></i></button>
		                                    </span>
		                                 </div>
							</div>
						</div></td><td><div class="form-group">
							<label class="control-label col-md-4">货主:</label>
							<div class="col-md-8">
						  <input type="text" id="t"  class="form-control"/>
							</div>
						</div></td></tr>
						<tr><td colspan="2"><span class="form-section"></span><h4>验证记录</h4></td></tr>
						<tr><td><div class="form-group">
							<label class="control-label col-md-4">送达时间:</label>
							<div class="col-md-8">
						   <div class="input-group date form_datetime">                                       
		                                    <input type="text" size="16" readonly class="form-control signDate" id="u"  data-required="1">
		                                    <span class="input-group-btn">
		                                    	<button class="btn default date-set" type="button"><i class="icon-calendar"></i></button>
		                                    </span>
		                                 </div> 
							</div>
						</div></td><td><div class="form-group">
							<label class="control-label col-md-4">报告号:</label>
							<div class="col-md-8" >
							 <input type="text" id="v" class="form-control"/>
							</div>
						</div></td><td><div class="form-group">
							<label class="control-label col-md-4">验证结论:</label>
							<div class="col-md-8">
						  <input type="text" id="w"  class="form-control"/>
							</div>
						</div></td></tr>
					<tr><td><div class="form-group">
							<label class="control-label col-md-4">通知人:</label>
							<div class="col-md-8">
						  <input type="text" id="x"  class="form-control"/>
							</div>
						</div></td></tr>
					<tr><td><div class="form-group">
							<label class="control-label col-md-4">服务评价:</label>
							<div class="col-md-8" >
							 <input type="text" id="y" class="form-control"/>
							</div>
						</div></td><td><div class="form-group">
							<label class="control-label col-md-4">评价人:</label>
							<div class="col-md-8" >
							 <input type="text" id="z" class="form-control"/>
							</div>
						</div></td></tr>
					<tr><td colspan="2"><div class="form-group">
							<label class="control-label col-md-2">备注:</label>
							<div class="col-md-10" >
							 <textarea type="text" id="aa" class="form-control description" rows=1></textarea>
							</div>
						</div></td></tr>
						</table>
						</div>
					</form>
					</div>
				</div>
				<div class="portlet box yellow">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-edit"></i>操作记录
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
				</div>
			</div>
			<div class="portlet-body">
				<div class="table-toolbar">
					<div class="btn-group">
						<a href="javascript:void(0)"  id="goodsAdd">
							<button id="sample_editable_1_new" class="btn blue">
								<i class="icon-plus"></i>添加
							</button>
						</a>
					</div>
				</div>
				<table class="table table-striped table-hover table-bordered" id="operationdata">
					<thead>
					<tr>     
					<td></td>
							<td colspan="2" align="center">机房液位(mm)</td>
							<td colspan="2" align="center">机房重量(t)</td>
							<td colspan="2" align="center">温度℃</td>
							<td colspan="3" align="center">实收数(t)</td>
							<td></td>
						</tr>
						<tr>
						   <th>罐号</th>
							<th>前尺</th>
							<th>后尺</th>
							<th>前尺</th>
							<th>后尺</th>
							<th>前尺</th>
							<th>后尺</th>
							<th>机房</th>
							<th>计量</th>
							<th>差异</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div>
		</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"  onclick="window.location='#/itemoperationlog'">返回</button>
				<button type="button" class="btn btn-success" id="save">提交</button>
			</div>
				</div>
			</div>
