<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="rows">
<div class="col-md-12">
		<div class="portlet">
			<div class="portlet-title">
				<div class="caption">
					<i class="fa fa-indent"></i>生产报表
					<span style="font-size: small;margin: 0;padding-right: 18px;" class="pull-right">
							<a style="padding-left: 20px;" class="hidden-print" onclick="ReportForm.exportExcel();" >
								<i class="fa fa-file-excel-o">&nbsp;导出</i>
							</a>
							<!-- <a style="padding-left: 20px;" class="hidden-print" onclick="ReportForm.exportTankExcel();" >
								<i class="fa fa-file-excel-o">&nbsp;罐检量导出</i>
							</a> -->
						</span>
				</div>
             </div>
         <div class="portlet-body">
         <div class="rows">
            <form  class="form-horizontal searchCondition">
            <div class="form-body">
            <div class="form-group">
            <div class="col-md-6">
              <label class="col-md-3 control-label">报表目录:</label>
              <div class="col-md-9">
				<select class="form-control" id="reportListTab">
				    <shiro:hasPermission name="MMONTHSTORAGE">
					    <option value="1" >月仓储入库明细表</option>
					    </shiro:hasPermission>
					    <shiro:hasPermission name="MSTOCKPORT">
						<option value="2">货物进出港通过量统计表</option>
						</shiro:hasPermission>
						<shiro:hasPermission name="MTWOTANKRATE">
						<option value="3">储罐周转率</option>
						</shiro:hasPermission>
						<shiro:hasPermission name="MINOUTPORT">
						<option value="4">管道运输通过明细表</option>
						</shiro:hasPermission>
						<shiro:hasPermission name="MTHROUGHT">
						<option value="5">吞吐量统计表</option>
						</shiro:hasPermission>
						<shiro:hasPermission name="MDOCKPORT">
						<option value="6">码头进出港统计表</option>
						</shiro:hasPermission>
						<shiro:hasPermission name="MTRADEPORT">
						<option value="7">外贸进出港统计表</option>
						</shiro:hasPermission>
						<shiro:hasPermission name="MSTORAGESTATE">
						<option value="8">库容状态统计</option>
						</shiro:hasPermission>
						<shiro:hasPermission name="MSTATIONDELIVER">
						<option value="9">装车站发货统计表</option>
						</shiro:hasPermission>
						<shiro:hasPermission name="MUNITTHROUGHT">
						<option value="10">通过单位统计表</option>
						</shiro:hasPermission>
						<shiro:hasPermission name="MYEARPIPE">
						<option value="11">年度管道运输汇总表</option>
						</shiro:hasPermission>
						<shiro:hasPermission name="MMONTHBERTH">
						<option value="13">泊位利用率</option>
						</shiro:hasPermission>
						  <shiro:hasPermission name="MPUMPSHEDROTATION">  
						<option value="16">泵棚周转量</option>
						  </shiro:hasPermission>  
						</select>
			</div>
              </div>
              </div>
              <HR align='center' class="col-md-12"  color='#987cb9' SIZE='1'>
            </div>
              </form>
         <div class="col-md-12">
		<div class="tab-content">
         <div id="tab"></div>
		</div>
		</div>
         </div>
         </div>  
</div>
</div>
</div>
