<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>

	<div class="form-actions fluid">
		<div class="col-md-offset-3 col-md-9">
			<a href="javascript:void(0)"
				onclick="openModal('<%=basePath%>resources/pages/inbound/notice/dock_notify.jsp','#ajax1')"
				id="dockAdd">
				<button id="btn" class="btn blue">
					<i class="icon-edit"></i>码头船发作业通知单
				</button> </a> <a href="javascript:void(0)"
				onclick="openModal('<%=basePath%>resources/pages/inbound/notice/piping_notify.jsp','#ajax1')"
				id="dockAdd">
				<button id="btn" class="btn blue">
					<i class="icon-edit"></i>配管作业通知单
				</button> </a> <a href="javascript:void(0)"
				onclick="openModal('<%=basePath%>resources/pages/inbound/notice/dynamic_notify.jsp','#ajax1')"
				id="dockAdd">
				<button id="btn" class="btn blue">
					<i class="icon-edit"></i>操作班船发作业通知单
				</button> </a>
		</div>
	</div>


	<div class="row">
		<div class="col-md-12">
			<div class="portlet box purple">
				<div class="portlet-title">
					<div class="caption">一、储罐的安排</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
					<!-- --------------------- -->
					<form class="form-horizontal">
						<div class="form-body">
							<div class="form-group">
								<label class="control-label col-md-2">使用储罐的编号</label>
								<div class="col-md-3">
									<label class="control-label">T601、T702</label>
								</div>
								<label class="control-label col-md-2">前期储存的物料名</label>
								<div class="col-md-3">
									<label class="control-label">乙二醇</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label">使用情况</label>
								<div class="col-md-10">
									<label class="control-label">运作良好</label>
								</div>
							</div>
						</div>
					</form>
					<!-- --------------------- -->
				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>


	<div class="row">
		<div class="col-md-12">
			<div class="portlet box purple">
				<div class="portlet-title">
					<div class="caption">二、管线的安排</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
					<!-- --------------------- -->
					<form class="form-horizontal">
						<div class="form-body">
							<div class="form-group">
								<label class="control-label col-md-2">使用管线的编号</label>
								<div class="col-md-3">
									<label class="control-label">D534</label>
								</div>
								<label class="control-label col-md-2">前期储存的物料名</label>
								<div class="col-md-3">
									<label class="control-label">乙二醇</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label">更换品种前的清洗情况</label>
								<div class="col-md-10">
									<label class="control-label">清洗完毕</label>
								</div>
							</div>
						</div>
					</form>
					<!-- --------------------- -->
				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>


	<div class="row">
		<div class="col-md-12">
			<div class="portlet box purple">
				<div class="portlet-title">
					<div class="caption">三、管线的准备及检查情况</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
					<!-- --------------------- -->
					<table class="table table-striped table-hover table-bordered"
						id="sample_editable_1">
						<thead>
							<tr>
								<th>日期</th>
								<th>作业内容</th>
								<th>作业人</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>9.24</td>
								<td>检查，大EG14-->T903、T902</td>
								<td>徐宪峰</td>
								<td class="center">/</td>
							</tr>
							<tr>
								<td>9.25</td>
								<td>检查，大EG14-->T903、T902</td>
								<td>顾友洁</td>
								<td class="center">/</td>
							</tr>
						</tbody>
					</table>
					<!-- --------------------- -->
				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>


	<div class="row">
		<div class="col-md-12">
			<div class="portlet box purple">
				<div class="portlet-title">
					<div class="caption">四、发货前各岗位的最后检查确认工作</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
					<!-- --------------------- -->
					<table class="table table-striped table-hover table-bordered"
						id="list-table">
						<thead>
							<tr>
								<th>日期</th>
								<th>岗位</th>
								<th>检查内容</th>
								<th>检查结果</th>
								<th>处理情况</th>
								<th>确认人</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>12.12</td>
								<td>码头</td>
								<td>船上品种和岸上所使用的管线中的物料的确认</td>
								<td>正常</td>
								<td>无</td>
								<td>倪</td>
							</tr>
							<tr>
								<td>12.12</td>
								<td>动力班</td>
								<td>呼吸阀、人孔及储罐附件、管线及附件、管线连接情况的核对</td>
								<td>正常</td>
								<td>无</td>
								<td>周</td>
							</tr>
							<tr>
								<td>12.12</td>
								<td>计量班</td>
								<td>高位报警及储罐联锁</td>
								<td>正常</td>
								<td>无</td>
								<td>李</td>
							</tr>

							<tr>
								<td>12.12</td>
								<td>调度及中控室</td>
								<td>SCADA系统接卸数量与罐容量核对</td>
								<td>正常</td>
								<td>无</td>
								<td>王</td>
							</tr>

						</tbody>
					</table>
					<!-- --------------------- -->
				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>



	<div class="row">
		<div class="col-md-12">
			<div class="portlet box purple">
				<div class="portlet-title">
					<div class="caption">五、船舶发货过程</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
					<!-- --------------------- -->
					<form class="form-horizontal">
					<div class="form-body">
					<div class="form-group">
						<label class="control-label col-md-2">船名</label>
						<div class="col-md-2">
							<label class="control-label">长城号</label>
						</div>
						<label class="control-label col-md-2">泊位号</label>
						<div class="col-md-2">
							<label class="control-label">#1泊位</label>
						</div>
						<label class="control-label col-md-2">调度值班</label>
						<div class="col-md-2">
							<label class="control-label">倪</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-2">码头值班</label>
						<div class="col-md-2">
							<label class="control-label">顾</label>
						</div>
						<label class="control-label col-md-2">动力班值班</label>
						<div class="col-md-2">
							<label class="control-label">周</label>
						</div>
						<label class="control-label col-md-2">到港时间</label>
						<div class="col-md-2">
							<label class="control-label">2014-12-12 16:00</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-2">联检时间</label>
						<div class="col-md-2">
							<label class="control-label">2014-12-12 18:00</label>
						</div>
					</div>
					<h4 class="form-section">开泵前的验证</h4><hr>
					<div class="form-group">
						<label class="control-label col-md-2">通知人</label>
						<div class="col-md-2">
							<label class="control-label">周</label>
						</div>
						<label class="control-label col-md-2">通知时间</label>
						<div class="col-md-2">
							<label class="control-label">2014-12-12 21:00</label>
						</div>
						<label class="control-label col-md-2">通知内容</label>
						<div class="col-md-2">
							<label class="control-label">没有内容</label>
						</div>
					</div>
					<h4 class="form-section">开泵前确认</h4><hr>
					<div class="form-group">
						<label class="control-label col-md-2">动力班</label>
						<div class="col-md-2">
							<label class="control-label">周</label>
						</div>
						<label class="control-label col-md-2">码头</label>
						<div class="col-md-2">
							<label class="control-label">倪</label>
						</div>
						<label class="control-label col-md-2">船方</label>
						<div class="col-md-2">
							<label class="control-label">李</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-2">码头电动阀测试</label>
						<div class="col-md-2">
							<label class="control-label">OK</label>
						</div>
						<label class="control-label col-md-2">开泵时间</label>
						<div class="col-md-2">
							<label class="control-label">2014-12-12 19:00</label>
						</div>
						<label class="control-label col-md-2">停泵时间</label>
						<div class="col-md-2">
							<label class="control-label">2014-12-12 22:00</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-2">机房收货量</label>
						<div class="col-md-2">
							<label class="control-label">4000</label>
						</div>
						<label class="control-label col-md-2">储罐收获量</label>
						<div class="col-md-2">
							<label class="control-label">3900</label>
						</div>
						<label class="control-label col-md-2">差异</label>
						<div class="col-md-2">
							<label class="control-label">100</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-2">累计在港时间</label>
						<div class="col-md-2">
							<label class="control-label">5小时</label>
						</div>
						<label class="control-label col-md-2">累计作业时间</label>
						<div class="col-md-2">
							<label class="control-label">6小时</label>
						</div>
						<label class="control-label col-md-2">本次接卸提供服务评价</label>
						<div class="col-md-2">
							<label class="control-label">满意</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-2">评价人</label>
						<div class="col-md-2">
							<label class="control-label">王</label>
						</div>
					</div>
					</div>
					</form>
					<!-- --------------------- -->
				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>


	<div class="row">
		<div class="col-md-12">
			<div class="portlet box purple">
				<div class="portlet-title">
					<div class="caption">六、发货过程中码头及库区异常情况及处置记录</div>
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
					</div>
				</div>
				<div class="portlet-body">
					<!-- --------------------- -->
					<form class="form-horizontal">
						<div class="form-body">
							<table width="100%">

								<tr>
									<td colspan="2">
										<div class="form-group">
											<div class="col-md-12">
												<label class="control-label">没有特殊情况</label>
											</div>
										</div></td>
								</tr>
							</table>
						</div>
					</form>
					<!-- --------------------- -->
				</div>
			</div>
			<!-- END EXAMPLE TABLE PORTLET-->
		</div>
	</div>

	<!-- 页面内容开结束-->
	<%-- <script src="<%=basePath%>resources/admin/scripts/table-editable.js"></script>  --%>
	<script type="text/javascript">
		$(function() {

			$('body').removeClass("modal-open"); // fix bug when inline picker is used in modal
			$("#dockAdd").click(function() {
				$(".button-dock").attr("data-dismiss", "");
			});

			function restoreRow(oTable, nRow) {
				var aData = oTable.fnGetData(nRow);
				var jqTds = $('>td', nRow);

				for ( var i = 0, iLen = jqTds.length; i < iLen; i++) {
					oTable.fnUpdate(aData[i], nRow, i, false);
				}

				oTable.fnDraw();
			}

			function editRow(oTable, nRow) {
				var aData = oTable.fnGetData(nRow);
				var jqTds = $('>td', nRow);
				jqTds[0].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[0] + '">';
				jqTds[1].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[1] + '">';
				jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[2] + '">';
				jqTds[3].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[3] + '">';
				jqTds[4].innerHTML = '<a class="edit" href="">保存</a>';
				jqTds[5].innerHTML = '<a class="cancel" href="">取消</a>';
			}

			function saveRow(oTable, nRow) {
				var jqInputs = $('input', nRow);
				oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
				oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
				oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
				oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
				oTable.fnUpdate('<a class="edit" href="">编辑</a>', nRow, 4,
						false);
				oTable.fnUpdate('<a class="delete" href="">删除</a>', nRow, 5,
						false);
				oTable.fnDraw();
			}

			function cancelEditRow(oTable, nRow) {
				var jqInputs = $('input', nRow);
				oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
				oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
				oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
				oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
				oTable.fnUpdate('<a class="edit" href="">编辑</a>', nRow, 4,
						false);
				oTable.fnDraw();
			}

			var oTable = $('#sample_editable_1').dataTable({
				"aLengthMenu" : [ [ 5, 15, 20, -1 ], [ 5, 15, 20, "All" ] // change per page values here
				],
				// set the initial value
				"iDisplayLength" : 5,
				"bPaginate" : false,
				"sPaginationType" : "bootstrap",
				"oLanguage" : {
					"sLengthMenu" : "_MENU_ records",
					"oPaginate" : {
						"sPrevious" : "Prev",
						"sNext" : "Next"
					}
				},
				"aoColumnDefs" : [ {
					'bSortable' : false,
					'aTargets' : [ 0 ]
				} ]
			});

			jQuery('#sample_editable_1_wrapper .dataTables_filter input')
					.addClass("form-control input-medium"); // modify table search input
			jQuery('#sample_editable_1_wrapper .dataTables_length select')
					.addClass("form-control input-small"); // modify table per page dropdown
			jQuery('#sample_editable_1_wrapper .dataTables_length select')
					.select2({
						showSearchInput : false
					//hide search box with special css class
					}); // initialize select2 dropdown

			var nEditing = null;

			$('#sample_editable_1_new')
					.click(
							function(e) {
								e.preventDefault();
								var aiNew = oTable
										.fnAddData([
												'',
												'',
												'',
												'',
												'<a class="edit" href="">编辑</a>',
												'<a class="cancel" data-mode="new" href="">取消</a>' ]);
								var nRow = oTable.fnGetNodes(aiNew[0]);
								editRow(oTable, nRow);
								nEditing = nRow;
							});

			$('#sample_editable_1 a.delete').live('click', function(e) {
				e.preventDefault();

				if (confirm("您确定要删除这一条记录？") == false) {
					return;
				}

				var nRow = $(this).parents('tr')[0];
				oTable.fnDeleteRow(nRow);
				/*  alert("Deleted! Do not forget to do some ajax to sync with backend :)"); */
			});

			$('#sample_editable_1 a.cancel').live('click', function(e) {
				e.preventDefault();
				if ($(this).attr("data-mode") == "new") {
					var nRow = $(this).parents('tr')[0];
					oTable.fnDeleteRow(nRow);
				} else {
					restoreRow(oTable, nEditing);
					nEditing = null;
				}
			});

			$('#sample_editable_1 a.edit').live('click', function(e) {
				e.preventDefault();

				/* Get the row as a parent of the link that was clicked on */
				var nRow = $(this).parents('tr')[0];

				if (nEditing !== null && nEditing != nRow) {
					/* Currently editing - but not this row - restore the old before continuing to edit mode */
					restoreRow(oTable, nEditing);
					editRow(oTable, nRow);
					nEditing = nRow;
				} else if (nEditing == nRow && this.innerHTML == "保存") {
					/* Editing this row and want to save it */
					saveRow(oTable, nEditing);
					nEditing = null;
					alert("添加成功！");
				} else {
					/* No edit in progress - let's start one */
					editRow(oTable, nRow);
					nEditing = nRow;
				}
			});

			$(".form_datetime").datetimepicker({
				autoclose : true,
				isRTL : App.isRTL(),
				format : "yyyy-mm-dd hh:ii:ss",
				pickerPosition : (App.isRTL() ? "bottom-right" : "bottom-left")
			});

		});
		function initEdit(obj) {
			$(obj).hide();
			var _tr = $(obj).parents("tr");
			$(_tr).find(".date").show();
			$(_tr).find("#dateSp").hide();
			$(_tr).find(".result").show();
			$(_tr).find("#resultSp").hide();
			$(_tr).find(".manage").show();
			$(_tr).find("#manageSp").hide();
			$(_tr).find(".sure").show();
			$(_tr).find("#sureSp").hide();
			$(_tr).find(".edit").hide();
			$(_tr).find(".confirm").show();
			$(_tr).find(".concel").show();
		}
		function concel(obj) {
			$(obj).hide();
			var _tr = $(obj).parents("tr");
			var _truckCode = $(_tr).find(".date");
			$(_truckCode).hide();
			var _truckCodeSp = $(_tr).find("#dateSp");
			$(_truckCodeSp).show();
			var _truckName = $(_tr).find(".result");
			$(_truckName).hide();
			var _truckNameSp = $(_tr).find("#resultSp");
			$(_truckNameSp).show();
			var _truckLoadCapacity = $(_tr).find(".manage");
			$(_truckLoadCapacity).hide();
			var _truckLoadCapacitySp = $(_tr).find("#manageSp");
			$(_truckLoadCapacitySp).show();
			var _truckCompany = $(_tr).find(".sure");
			$(_truckCompany).hide();
			var _truckCompanySp = $(_tr).find("#sureSp");
			$(_truckCompanySp).show();
			$(_tr).find(".edit").show();
			$(_tr).find(".confirm").hide();
			$(_tr).find(".concel").hide();

			$(_truckCode).val($(_truckCodeSp).text());
			$(_truckName).val($(_truckNameSp).text());
			$(_truckLoadCapacity).val($(_truckLoadCapacitySp).text());
			$(_truckCompany).val($(_truckCompanySp).text());
		}

		function confirmS(obj) {
			$(obj).hide();
			var _tr = $(obj).parents("tr");
			var _truckCode = $(_tr).find(".date");
			var _truckCodeSp = $(_tr).find("#dateSp");
			$(_truckCodeSp).text($(_truckCode).val());
			$(_truckCode).hide();
			$(_truckCodeSp).show();

			var _tr = $(obj).parents("tr");
			var _truckName = $(_tr).find(".result");
			var _truckNameSp = $(_tr).find("#resultSp");
			$(_truckNameSp).text($(_truckName).val());
			$(_truckName).hide();
			$(_truckNameSp).show();

			var _tr = $(obj).parents("tr");
			var _truckLoadCapacity = $(_tr).find(".manage");
			var _truckLoadCapacitySp = $(_tr).find("#manageSp");
			$(_truckLoadCapacitySp).text($(_truckLoadCapacity).val());
			$(_truckLoadCapacity).hide();
			$(_truckLoadCapacitySp).show();

			var _tr = $(obj).parents("tr");
			var _truckCompany = $(_tr).find(".sure");
			var _truckCompanySp = $(_tr).find("#sureSp");
			$(_truckCompanySp).text($(_truckCompany).val());
			$(_truckCompany).hide();
			$(_truckCompanySp).show();

			$(_tr).find(".edit").show();
			$(_tr).find(".confirm").hide();
			$(_tr).find(".concel").hide();

		}
	</script>
</body>
</html>