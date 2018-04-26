<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生产指标完成情况</title>
</head>
<body>
	<div class="row">
		<div class="col-md-12">
			<div class="portlet">
				<div class="portlet-title hidden">
					<div class="caption">
						<i class="fa fa-list-alt"></i>生产指标完成情况
						<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right">
							<a style="padding-left: 20px;" class="hidden-print" onclick="ProductionTarget.exportProductionTarget();">
								<i class="fa fa-file-excel-o">&nbsp;导出</i>
							</a>
						</span>
					</div>
				</div>
				<div>
					<form action="#" class="form-horizontal contract-update-form cflltzlb">
						<div id="roleManagerQueryDivId">
							<div class="row">
								<div class="form-body inOutPort">
									<label class="control-label col-md-1">日期:</label>
									<div class="col-md-2" style="display: block;padding-right: 0;">
										<input class="form-control month-picker" id="date" style="width:180px;" type="text" placeholder="日期">
									</div>
									<div class="col-md-2">
									  <button type="button" class="btn btn-success search" onclick="ProductionTarget.queryResult();">
											<span class="glyphicon glyphicon-search"></span>&nbsp;
									  </button>
								    </div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div>
			  <div class="portlet-body">
				<div class="table-container" style="font-size: 14px;padding-left: 20px;">
					2015年  月主要生产指标完成情况：<br>
					&nbsp;&nbsp;本月码头吞吐量    万吨，同比减少    %，作业船舶   艘，同比减少     %，<br>
					其中船舶进货量     万吨、  艘，船舶发货量     万吨、   艘；装车站发货量    万吨、车发    车次；<br>
					通过单位码头船舶进货量    万吨、  艘，船舶发货量    万吨、   艘。转输     吨，  次；其中输入    吨，  次；输出    吨，  次。<br>
					&nbsp;&nbsp;月度平均储存量     万吨，占最大储存量     %。其中：<br>
					&nbsp;&nbsp;甲  醇：保税罐储存量     %，非保税罐储存量     %。<br>
					&nbsp;&nbsp;乙二醇：保税罐储存量     %，非保税罐储存量     %。<br>
					&nbsp;&nbsp;成品油：汽油储存量     %，0#柴油储存量     %。<br>
					&nbsp;&nbsp;全年累计吞吐量      万吨，同比减少     %，总作业船舶   艘，同比减少     %，<br>
					其中累计船舶进货量      万吨、   艘，船舶发货量     万吨 、   艘；<br>
					累计车发量     万吨、     车次；累计通过单位吞吐量     万吨、   艘。<br>
					其中累计通过单位船舶进货量     万吨、   艘，船舶发货量    万吨、  艘。累计转输     吨，  次；<br>
					其中输入     吨，  次；输出     吨，  次。
				</div>
			</div>
		 </div>
		</div>
	</div>
</body>
</html>