<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<head>
<style type="text/css">
 .grid-table-body table td {
    border-color: transparent #dddddd #dddddd transparent !important;
    border-width: 0 1px 1px 0 !important;
    vertical-align: top !important;
}
</style>
</head>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet box grey tabbable">
				<div class="portlet-title ">
					<div class="caption">
						<i class="icon-reorder"></i>调度日志
					</div>
				</div>
				<div class="portlet-body ">
					<div class=" portlet-tabs">
						<ul class="nav nav-tabs">
							<li><a href="javascript:void(0)" onclick="ItemOperation.changetab(this,2)"  data-toggle="tab" id="tab3">当班调度作业记录</a></li>
							<li><a href="javascript:void(0)" onclick="ItemOperation.changetab(this,3)"  data-toggle="tab" id="tab2">调度值班记录</a></li>
							<li class="active"><a href="javascript:void(0)" onclick="ItemOperation.changetab(this,1)"  data-toggle="tab" id="tab1">日志记录</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="portlet_tab1">
								<!--日志记录  -->
								<form action="#" class="form-horizontal">
									<table width="100%">
										<tr>
											<td colspan="3"><h4>基本信息</h4></td>
										</tr>
										<tr>
											<td>
												<div class="form-group">
													<label class="control-label col-md-6">日期:</label>
													<div class="col-md-6">
													<label class="control-label">2014-12-01</label>
													</div>
												</div>
											</td>
											<td>
												<div class="form-group">
													<label class="control-label col-md-6">气温:</label>
													<div class="col-md-6">
													<label class="control-label">6℃</label>
<!-- 														<input type="text" class="form-control" /> -->
													</div>
												</div>
											</td>
											<td>
												<div class="form-group">
													<label class="control-label col-md-6">当班调度:</label>
													<div class="col-md-6">
													<label class="control-label">刘德信</label>
<!-- 														<input type="text" class="form-control" /> -->
													</div>
												</div>
											</td>
											<td class="col-md-3">
												<div class="form-group">
													<label class="control-label col-md-6">发货班:</label>
													<div class="col-md-6">
													<label class="control-label">王德年</label>
<!-- 														<input type="text" class="form-control" /> -->
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="form-group">
													<label class="control-label col-md-6">日班:</label>
													<div class="col-md-6">
													<label class="control-label">辛德勒</label>
<!-- 														<input type="text" class="form-control" /> -->
													</div>
												</div>
											</td>
											<td>
												<div class="form-group">
													<label class="control-label col-md-6">码头值班:</label>
													<div class="col-md-6">
													<label class="control-label">刘海龙</label>
<!-- 														<input type="text" class="form-control" /> -->
													</div>
												</div>
											</td>
											<td>
												<div class="form-group">
													<label class="control-label col-md-6">动力班:</label>
													<div class="col-md-6">
													<label class="control-label">查理斯</label>
<!-- 														<input type="text" class="form-control" /> -->
													</div>
												</div>
											</td>
										</tr>

									</table>
								</form>
								<div class="btn-group buttons" >
			<button class="btn btn-primary  check" type="button"><span class="glyphicon glyphicon-plus"></span>添加</button>
			<button class="btn btn-primary modify" type="button"><span class="glyphicon glyphicon-edit"></span>修改</button>
			<button class="btn btn-success roleManagerQuery" type="button"><span class="glyphicon glyphicon-search"></span>&nbsp;高级搜索&nbsp; <span class="caret"></span> </button>
			</div>
								<div class="loggrid" data-role="logGrid"></div>
							</div>
							<div class="tab-pane" id="portlet_tab2">
								<!-- 调度值班记录  -->

							<div class="row">
		<div class="col-md-12">
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
				<div class="portlet-body">
					<div class="table-toolbar">
						<div class="btn-group">
							<button id="sample_editable_1_new" class="btn green">
								新增记录 <i class="icon-plus"></i>
							</button>
						</div>
						
					</div>
					<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
						<thead>
							<tr>
								<th>日期</th>
								<th>内容</th>
								<th>编辑</th>
								<th>删除</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>9.24</td>
								<td>检查，大EG14-->T903、T902</td>
								<td><a class="edit" href="javascript:;">编辑</a>
								</td>
								<td><a class="delete" href="javascript:;">删除</a>
								</td>
							</tr>
							<tr>
								<td>9.25</td>
								<td>检查，大EG14-->T903、T902</td>
								<td><a class="edit" href="javascript:;">编辑</a>
								</td>
								<td><a class="delete" href="javascript:;">删除</a>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="form-actions fluid">
						<div class="col-md-offset-3 col-md-9">
							<button type="button" class="btn green sbtn">提交</button>
						</div>
					</div>
				</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>


							</div>
							<div class="tab-pane" id="portlet_tab3">
								<!-- 当班调度作业记录 -->
	<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form class="form-horizontal">
				<div class="form-body">
					<table width="100%">

						<tr>
							<td colspan="2">
								<div class="form-group">
									<div class="col-md-12">
										<textarea class="form-control description" rows="3"
											name="description"></textarea>
									</div>
								</div></td>
						</tr>
					</table>
				</div>

				<div class="form-actions fluid">
					<div class="col-md-offset-3 col-md-9">
						<button type="button" class="btn btn-primary">提交</button>

					</div>
				</div>
			</form>
			<!-- END FORM-->
		</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	