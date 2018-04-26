<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="modal fade">
	<div class="modal-dialog  modal-full">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">货物信息</h4>
			</div>
			<div class="modal-body" style="padding-left: 15px; padding-right: 15px;">
				<form class="form-horizontal cargo-form">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">提单号<span class="required"> * </span> 
								</label>
								<div class="col-md-9">
									<input type="text" class="form-control" maxlength="20" name="bl" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">收货人<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" class="form-control" maxlength="70" name="consingee" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">发货人<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" class="form-control" maxlength="70" name="shipper" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">托运人<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" name="consignor" maxlength="70" class="form-control" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">货物名称<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" class="form-control" maxlength="70" name="cargoName" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">货物类别 
								</label>
								<div class="col-md-9">
									<!--<input type="text" name="cargoType" maxlength="6" class="form-control"/>  -->
									<select   name="cargoType" maxlength="6" class="form-control">
							          	 <option value="">请选择货物类别</option>
							             <option value="0200">石油、天然气及制品</option>
							             <option value="0100">煤炭及制品</option>
							             <option value="0201">其中:原油</option>
							             <option value="0300">金属矿石</option>
							             <option value="0400">钢铁</option>
							             <option value="0500">矿物性建筑材料</option>
							             <option value="0600">水泥</option>
							             <option value="0700">木材</option>
							             <option value="0800">非金属矿石</option>
							             <option value="0801">其中：磷矿</option>
							             <option value="0900">化学肥料及农药</option>
							             <option value="1000">盐</option>
							             <option value="1100">粮食</option>
							             <option value="1200">机械、设备、电器</option>
							             <option value="1300">化工原料及制品</option>
							             <option value="1400">有色金属</option>
							             <option value="1500">轻工、医药产品</option>
							             <option value="1501">其中：日用工业品</option>
							             <option value="1600">农林牧渔业产品</option>
							             <option value="1601">其中：棉花</option>
							             <option value="1700">其他</option>
							             <option value="1800">集装箱运量(TEU)</option>
							             <option value="1900">集装箱货运量</option>
							             <option value="2000">滚装车辆数</option>
							             <option value="2100">滚装车辆货运量</option>
						         	</select>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">净重(吨)<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" class="form-control" maxlength="5" name=grossWeight data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">曾缴情况<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<select  name="pay" class="form-control" data-required="1"  data-type="Require">
							          	 <option value="1">无特殊情况</option>
							             <option value="2">曾按沿海港国内出口标准缴纳</option>
							             <option value="3">曾按内河港国内出口标准缴纳</option>
							             <option value="4">曾按国外进出口标准缴纳</option>
							             <option value="5">装货港未缴纳港口建设费</option>
							             <option value="6">符合其他免征条件</option>
						         	</select>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">危险品<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<select  name="danger" class="form-control" data-required="1"  data-type="Require">
							             <option value="N">非危险品</option>
							          	 <option value="Y">危险品</option>
						         	</select>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">危险品序号 
								</label>
								<div class="col-md-9">
									<input type="text" maxlength="32" name="dangerNO" class="form-control"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">中转标志<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<select  name="transfer" class="form-control" data-required="1"  data-type="Require">
							          	 <option value="Y">中转</option>
							             <option value="N">非中转</option>
							         </select>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">内外贸<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<select  name="ifcsumType" class="form-control" data-required="1"  data-type="Require">
							          	 <option value="I">内贸</option>
							             <option value="F">外贸</option>
							         </select>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">启运港<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" class="form-control" maxlength="35" name="startPort" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">目的港<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" name="disPort" maxlength="35" class="form-control" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">卸货港代码<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" class="form-control" maxlength="5" name="dischargePortCode" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">卸货港<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" name="dischargePort" maxlength="35" class="form-control" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">装货港代码<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" class="form-control" maxlength="5" name="loadPortCode" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">装货港<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" name="loadPort" maxlength="35" class="form-control" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">交货地代码<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" class="form-control" maxlength="5" name="deliveryCode" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-md-3">交货地<span class="required"> * </span>  
								</label>
								<div class="col-md-9">
									<input type="text" name="delivery" maxlength="35" class="form-control" data-required="1"  data-type="Require"/>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="close">取消</button>
				<button type="button" class="btn btn-success" id="save">提交</button>
				<button type="button" class="btn btn-success hidden" id="copy">复制</button>
			</div>
		</div>
	</div>
</div>