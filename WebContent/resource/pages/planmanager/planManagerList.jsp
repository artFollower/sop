<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
	<head>
	<style type="text/css">
	.todo-sidebar {
    float: left;
    margin-right: 20px;
    width: 160px;
    }
    .portlet.light > .portlet-title {
    min-height: 24px;
    padding: 0;
    }
    .todo-sidebar {
    float: left;
    margin-right: 20px;
    width: 140px;
    }
	</style>
	</head>
<div class="rows">
<div class="row page-header-fixed page-sidebar-closed-hide-logo page-sidebar-closed page-quick-sidebar-over-content page-container-bg-solid page-compact">
	<div class="col-md-12">
		<div class="portlet">
		<div class="portlet-title">
			<div class="caption titlemanager">
				<i class="fa fa-yelp">方案管理</i>
			</div>
		</div>
		<div class="portlet-body">
		<div class="row">
		<div class="col-md-12">
				<!-- 搜索条件 -->
				<div style="margin-bottom:5px;">
				<form action="#" class="form-horizontal form-bordered">
				<div id="roleManagerQueryDivId" hidden="true">
						<div class="row">
						  <div class="col-md-12  ">
						  <div class="col-md-3 shipDiv " style="padding-left: 0px;">
						  <label id="planType" class="hidden"></label>
						   <label id="type" class="hidden"></label>
						  <label class="control-label col-md-4" style="padding-left: 0px;">船名:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='20' name="shipName" id="shipId">
						  </div>
						  </div>
						  <div class="col-md-3  unloadingDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">货品名:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='20'  data-provide="typeahead" name="productId" id="productId">
						  </div>
						  </div>
						  <div class="col-md-3 shipDiv" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">泊位:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <input class="form-control" maxlength='10'  data-provide="typeahead" name="berthId" id="berthId">
						  </div>
						  </div>
						  <div class="col-md-3" style="padding-left: 0px;">
						  <label class="control-label col-md-4" style="padding-left: 0px;">状态:</label>
						  <div class="col-md-8" style="padding-left: 0px; padding-right: 0px;">
						  <select class="form-control" id="status" name="status">
						  <option value="-1">全部</option>
						  <option value="0">未提交</option>
						  <option value="1">审核中</option>
						  <option value="2">已完成</option>
						  </select>
						  </div>
						  </div>
						  </div>
					</div>
					<div class="col-md-12" style="margin-top:25px;">
					<div class="col-md-6" style="padding-left: 0px;padding-right:0px;">
							<label class="control-label col-md-2" style="padding-left: 0px;">时间:</label>
							<div class="col-md-10" style="padding-left: 0px;">
								<div class="input-group date-picker input-daterange"
									data-date-format="yyyy-mm-dd">
									<input type="text" class="form-control" name="startTime"
										 id="startTime">
									<span class="input-group-addon">到</span> <input
										type="text" class="form-control" name="endTime"  id="endTime">
								</div>
							</div>
						</div>
						<div class="col-md-4 col-md-offset-1 btn-group"
								style="float: left; padding-left: 0px;">
								<button type="button"
									class="btn btn-success" id="searchPlan">
									<span class="fa fa-search"></span>&nbsp;
								</button>
								<button type="button" style="margin-left: 8px;"
									class="btn btn-primary " id="reset">
									<span class="fa fa-undo"></span>&nbsp;
								</button>
							</div>
					</div>
					</div>
			    </form>
				</div>
				<!-- 搜索条件 -->
				<!-- 搜索按钮 -->
				<div class="btn-group buttons col-md-12 searchDiv" style="margin-top:15px;">
	             <a href="#/backflowplan/addbackflow" class="btn btn-default btn-circle mar-r-10 addBackFlow" type="button" style="display:none;">
				<span class="fa fa-plus"></span>&nbsp;添加&nbsp;</a> 
				<a href="#/changetankplan/addchangetank" class="btn btn-default btn-circle mar-r-10 addChangeTank" type="button" style="display:none;">
				<span class="fa fa-plus"></span>&nbsp;添加&nbsp;</a> 
	            <button class="btn btn-default btn-circle mar-r-10 roleManagerQuery" type="button">
			    <span class="fa fa-search"></span><span class="text">搜索</span>
		      </button>
	            </div>
	            <!-- 搜索按钮 -->
	            <!-- 列表 -->
	            <div class="col-md-12">
	            <div data-role="managerGrid"></div>
	            </div>
	            </div>
	            <!-- 列表 -->
				</div>
				</div>
              </div>
             </div>
		</div>
		</div>				