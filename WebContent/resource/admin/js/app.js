/*******************************************************************************
 * Metronic AngularJS App Main Script
 ******************************************************************************/

/* Metronic App */
var MetronicApp = angular.module("MetronicApp", [
    "ui.router", 
    "ui.bootstrap", 
    "oc.lazyLoad",  
    "ngSanitize" 
]); 

/* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
MetronicApp.config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
	
    $ocLazyLoadProvider.config({
        cssFilesInsertBefore: 'ng_load_plugins_before'
    });
}]);

/* Setup global settings */
MetronicApp.factory('settings', ['$rootScope', function($rootScope) {
    // supported languages
    var settings = {
        layout: {
            pageSidebarClosed: false, // sidebar menu state
            pageBodySolid: false, // solid body color state
            pageAutoScrollOnLoad: 1000 // auto scroll to top on page load
        },
        layoutImgPath: Metronic.getAssetsPath() + 'admin/layout/img/',
        layoutCssPath: Metronic.getAssetsPath() + 'admin/layout/css/'
    };

    $rootScope.settings = settings;

    return settings;
}]);

/* Setup App Main Controller */
MetronicApp.controller('AppController', ['$scope', '$rootScope', function($scope, $rootScope) {
    $scope.$on('$viewContentLoaded', function() {
        Metronic.initComponents(); // init core components
        // Layout.init(); // Init entire layout(header, footer, sidebar, etc) on
		// page load if the partials included in server side instead of loading
		// with ng-include directive
    });
}]);

/*******************************************************************************
 * Layout Partials. By default the partials are loaded through AngularJS
 * ng-include directive. In case they loaded in server side(e.g: PHP include
 * function) then below partial initialization can be disabled and Layout.init()
 * should be called on page load complete as explained above.
 ******************************************************************************/
 
/* Setup Layout Part - Header */
MetronicApp.controller('HeaderController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initHeader(); // init header
//        config.initMessageTimmer();
//        remote.jsjac.chat.login("xiewei","skycloud",true);
    });
}]);

/* Setup Layout Part - Sidebar */
MetronicApp.controller('SidebarController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initSidebar(); // init sidebar
    });
}]);

/* Setup Layout Part - Quick Sidebar */
MetronicApp.controller('QuickSidebarController', ['$scope', function($scope) {    
    $scope.$on('$includeContentLoaded', function() {
        setTimeout(function(){
            QuickSidebar.init(); // init quick sidebar
        }, 2000);
    });
}]);

/*
 * Setup Layout Part - Theme Panel
 * MetronicApp.controller('ThemePanelController', ['$scope', function($scope) {
 * $scope.$on('$includeContentLoaded', function() { Demo.init(); // init theme
 * panel }); }]);
 */

/* Setup Layout Part - Footer */
MetronicApp.controller('FooterController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
    	
        Layout.initFooter(); // init footer
    });
}]);

MetronicApp.factory('appInjector', ['$q','$location', function($q,$location) {
    var appInjector = {
		request : function(request) {
			//config.load();
			return request;
		},
		response : function(response) {
			//config.unload();
			
			return response;
		}
    };
    return appInjector;
}]);

/* Setup Rounting For All Pages */
MetronicApp.config(['$stateProvider', '$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
    // Redirect any unmatched url
    $urlRouterProvider.otherwise("/dashboard");
    
    $httpProvider.interceptors.push('appInjector');
   
    $stateProvider
        // Dashboard
        .state('dashboard', {
            url: "/dashboard",
            templateUrl: config.getResource()+"/pages/dashboard.jsp",            
            data: {pageTitle: '控制台'}
        })
        
        /**
         * 月仓储入库明细表
         */
        .state('monthInStorage',{
	    	url:'/monthInStorage',
	    	templateUrl:config.getResource()+"/pages/report/monthInStorage.jsp",
	    	data: {pageTitle: '月仓储入库明细表'},
	    	controller:function($scope,$stateParams){
	    		MonthInStorage.init();
	    	}
	    })
	    
	    /**
         * 长江石化码头货物进出港通过量统计
         */
        .state('inOutPort',{
	    	url:'/inOutPort',
	    	templateUrl:config.getResource()+"/pages/report/inOutPort.jsp",
	    	data: {pageTitle: '货物进出港通过量统计'},
	    	controller:function($scope,$stateParams){
	    		InOutPort.init();
	    	}
	    })
	    
	    /**
         * 储罐周转率
         */
        .state('tankRate',{
	    	url:'/tankRate',
	    	templateUrl:config.getResource()+"/pages/report/tankRate.jsp",
	    	data: {pageTitle: '储罐周转率'},
	    	controller:function($scope,$stateParams){
	    		TankRate.init();
	    	}
	    })
	    
	    /**
         * 管道运输通过明细表
         */
        .state('pipeDetail',{
	    	url:'/pipeDetail',
	    	templateUrl:config.getResource()+"/pages/report/pipeDetail.jsp",
	    	data: {pageTitle: '管道运输通过明细表'},
	    	controller:function($scope,$stateParams){
	    		PipeDetail.init();
	    	}
	    })
	    
	    /**
         * 吞吐量统计表
         */
        .state('throughput',{
	    	url:'/throughput',
	    	templateUrl:config.getResource()+"/pages/report/throughput.jsp",
	    	data: {pageTitle: '吞吐量统计表'},
	    	controller:function($scope,$stateParams){
	    		Throughput.init();
	    	}
	    })
	    
	    /**
         * 码头进出港统计表
         */
        .state('dockPort',{
	    	url:'/dockPort',
	    	templateUrl:config.getResource()+"/pages/report/dockPort.jsp",
	    	data: {pageTitle: '码头进出港统计表'},
	    	controller:function($scope,$stateParams){
	    		DockPort.init();
	    	}
	    })
	    
	    /**
         * 外贸进出港统计表
         */
        .state('tradePort',{
	    	url:'/tradePort',
	    	templateUrl:config.getResource()+"/pages/report/tradePort.jsp",
	    	data: {pageTitle: '外贸进出港统计表'},
	    	controller:function($scope,$stateParams){
	    		TradePort.init();
	    	}
	    })
	    
	    /**
         * 库容状态统计
         */
        .state('storageState',{
	    	url:'/storageState',
	    	templateUrl:config.getResource()+"/pages/report/storageState.jsp",
	    	data: {pageTitle: '库容状态统计'},
	    	controller:function($scope,$stateParams){
	    		StorageState.init();
	    	}
	    })
	    
	    /**
         * 装车站发货统计表
         */
        .state('stationDeliver',{
	    	url:'/stationDeliver',
	    	templateUrl:config.getResource()+"/pages/report/stationDeliver.jsp",
	    	data: {pageTitle: '装车站发货统计表'},
	    	controller:function($scope,$stateParams){
	    		StationDeliver.init();
	    	}
	    })
	    
	    /**
         * 通过单位统计表
         */
        .state('unitStatis',{
	    	url:'/unitStatis',
	    	templateUrl:config.getResource()+"/pages/report/unitStatis.jsp",
	    	data: {pageTitle: '通过单位统计表'},
	    	controller:function($scope,$stateParams){
	    		UnitStatis.init();
	    	}
	    })
	    
	    /**
         * 年度管道运输汇总表
         */
        .state('yearPipe',{
	    	url:'/yearPipe',
	    	templateUrl:config.getResource()+"/pages/report/yearPipe.jsp",
	    	data: {pageTitle: '年度管道运输汇总表'},
	    	controller:function($scope,$stateParams){
	    		YearPipe.init();
	    	}
	    })
	    
	    /**
         * 年度仓储汇总表
         */
        .state('yearStorage',{
	    	url:'/yearStorage',
	    	templateUrl:config.getResource()+"/pages/report/yearStorage.jsp",
	    	data: {pageTitle: '年度仓储汇总表'},
	    	controller:function($scope,$stateParams){
	    		YearStorage.init();
	    	}
	    })
	    
	     /**
         * 年度泊位利用率
         */
        .state('yearBerth',{
	    	url:'/yearBerth',
	    	templateUrl:config.getResource()+"/pages/report/yearBerth.jsp",
	    	data: {pageTitle: '年度泊位利用率'},
	    	controller:function($scope,$stateParams){
	    		YearBerth.init();
	    	}
	    })
	    
	    /**
         * 月度泊位利用率
         */
        .state('monthBerth',{
	    	url:'/monthBerth',
	    	templateUrl:config.getResource()+"/pages/report/monthBerth.jsp",
	    	data: {pageTitle: '月度泊位利用率'},
	    	controller:function($scope,$stateParams){
	    		MonthBerth.init();
	    	}
	    })
	    
	    /**
         * 生产指标完成情况
         */
        .state('productionTarget',{
	    	url:'/productionTarget',
	    	templateUrl:config.getResource()+"/pages/report/productionTarget.jsp",
	    	data: {pageTitle: '生产指标完成情况'},
	    	controller:function($scope,$stateParams){
	    		ProductionTarget.init();
	    	}
	    })
	    
         /**
			 * ------------------------------------------------------------
			 * 合同意向管理开始
			 * --------------------------------------------------------------
			 */
        // 合同意向管理
         .state('instent', {
            url: "/instent",// 其他jsp获取方式 eg:#/instent
            templateUrl: config.getResource()+"/pages/contract/intent/list.jsp",// 打开的jsp界面
            data: {pageTitle: '合同意向'},
            controller: function($scope,$stateParams){
            	Intent.init();
            } 
        })
        
        // 添加合同意向管理
         .state('intentAdd', {
            url: "/intentAdd",
            templateUrl: config.getResource()+"/pages/contract/intent/add.jsp",
            controller: function($scope,$stateParams){
            	Intent.initAdd();
            	Intent.initSelect();
            } 
            
        })
        // 修改合同意向管理
        .state('intentGet', {
            url: "/intentGet?id",
            templateUrl: config.getResource()+"/pages/contract/intent/edit.jsp",
            controller: function($scope,$stateParams){
            	$scope.id = $stateParams.id;
            	var id=$scope.id;
            	Intent.initAdd();
            	Intent.initEdit(id);
//            	Intent.initSelect();
            } 
        })
        
        // 消息中心
        .state('message', {
        	url: "/message",
        	templateUrl: config.getResource()+"/pages/message/message.jsp",
        	data: {pageTitle: '消息'},
            controller: function($scope,$stateParams){
            	Todo.init();
            	Message.init();
            }
        })
        
         // 审批中心
        .state('approve', {
        	url: "/approve",
        	templateUrl: config.getResource()+"/pages/approve/list.jsp",
        	data: {pageTitle: '审批'},
            controller: function($scope,$stateParams){
            	Approve.init();
            }
        })
        
        
              // 货批汇总
        .state('cargolist', {
        	url: "/cargolist",
        	templateUrl: config.getResource()+"/pages/statistics/cargo/list.jsp",
        	data: {pageTitle: '货批汇总'},
            controller: function($scope,$stateParams){
            	Cargo.init();
            }
        })
        
              // 货体汇总
        .state('goodslist', {
        	url: "/goodslist",
        	templateUrl: config.getResource()+"/pages/statistics/goods/list.jsp",
        	data: {pageTitle: '货体汇总'},
            controller: function($scope,$stateParams){
            	Goods.init();
            }
        })
        
        
        .state('loglist', {
        	url: "/loglist",
        	templateUrl: config.getResource()+"/pages/statistics/log/list.jsp",
        	data: {pageTitle: '分类日志查询'},
            controller: function($scope,$stateParams){
            	GoodsLog.init();
            }
        })
        
         /**
			 * ------------------------------------------------------------
			 * 合同管理开始
			 * --------------------------------------------------------------
			 */
        // 合同管理
         .state('contract', {
            url: "/contract",
            templateUrl: config.getResource()+"/pages/contract/contract/list.jsp",
            data: {pageTitle: '合同'},
            controller: function($scope,$stateParams){
            	
            	sessionStorage.setItem("repairType", 0); 
            	console.log(sessionStorage.getItem("repairType")+"-------");
            }
        })
         // 添加合同
         .state('contractAdd', {
            url: "/contractAdd?intentId",
            templateUrl: config.getResource()+"/pages/contract/contract/add.jsp",
            controller: function($scope,$stateParams){
            	$scope.intentId = $stateParams.intentId;
            	var intentId=$scope.intentId;
            	Contract.initAdd(intentId);
            	Contract.initSelect();
            	$(".form_datetime").datetimepicker({
                    autoclose: true,
                    isRTL: Metronic.isRTL(),
                    format: "yyyy-mm-dd hh:ii:ss",
                    pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
                });
            }
            })
        // 修改合同
        .state('contractGet', {
            url: "/contractGet?id",
            templateUrl: config.getResource()+"/pages/contract/contract/edit.jsp",
            controller: function($scope,$stateParams){
            	$scope.id = $stateParams.id;
            	var id=$scope.id;
            	Contract.initAdd(0);
            	Contract.initEdit(id);
            	$(".form_datetime").datetimepicker({
		            autoclose: true,
		            isRTL: Metronic.isRTL(),
		            format: "yyyy-mm-dd hh:ii:ss",
		            pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
		        });
            }
        })
        // 合同变更
        .state('contractChange', {
            url: "/contractChange?id",
            templateUrl: config.getResource()+"/pages/contract/contract/change.jsp",
            controller: function($scope,$stateParams){
            	$scope.id = $stateParams.id;
            	var id=$scope.id;
            	Contract.initAdd(0);
            	Contract.initContractChange(id);
            	$(".form_datetime").datetimepicker({
		            autoclose: true,
		            isRTL: Metronic.isRTL(),
		            format: "yyyy-mm-dd hh:ii:ss",
		            pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
		        });
            }
        })
         
         /**
			 * ------------------------------------------------------------
			 * 到港计划开始
			 * --------------------------------------------------------------
			 */
        
        // 到港计划管理
         .state('arrivalForeshow', {
            url: "/arrivalForeshow",
            templateUrl: config.getResource()+"/pages/inbound/arrivalForeshow/list.jsp",
            data: {pageTitle: '船期预告'},
            controller: function($scope,$stateParams){
            	ArrivalForeshow.init();
            	if (jQuery().datepicker) {
    				$('.date-picker').datepicker({
		            autoclose: true,
		            isRTL: Metronic.isRTL(),
		            pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
		        });
            	}
            }
            
        })
        
        // 到港计划管理
         .state('arrival', {
            url: "/arrival",
            templateUrl: config.getResource()+"/pages/inbound/arrival/list.jsp",
            data: {pageTitle: '到港计划'},
            controller: function($scope,$stateParams){
            	Arrival.init();
            	if (jQuery().datepicker) {
    				$('.date-picker').datepicker({
		            autoclose: true,
		            isRTL: Metronic.isRTL(),
		            pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
		        });
            	}
            }
            
        })
        
         // 添加到港信息
         .state('arrivalAdd', {
            url: "/arrivalAdd",
            templateUrl: config.getResource()+"/pages/inbound/arrival/add.jsp",
            controller: function($scope,$stateParams){
            	Arrival.initAdd();
            	$('.date-picker').datepicker({
   	             rtl: Metronic.isRTL(),
   	             autoclose: true
   	         });
   	         $('body').removeClass("modal-open");
            }
        })
        // 修改到港信息
        .state('arrivalGet', {
            url: "/arrivalGet?id",
            templateUrl: config.getResource()+"/pages/inbound/arrival/edit.jsp",
            controller: function($scope,$stateParams){
            	$scope.id = $stateParams.id;
            	var id=$scope.id;
            	Arrival.initAdd();
            	Arrival.initEdit(id);
            	$('.date-picker').datepicker({
      	             rtl: Metronic.isRTL(),
      	             autoclose: true
      	         });
      	         $('body').removeClass("modal-open");
            } 
        })
        
        
        
        
         /**
			 * ------------------------------------------------------------
			 * 货批入库开始
			 * --------------------------------------------------------------
			 */
        // 货批入库管理
         .state('storage', {
            url: "/storage",
            templateUrl: config.getResource()+"/pages/inbound/storage_confirm/list.jsp",
            data: {pageTitle: '货批入库'},
            controller: function($scope,$stateParams){

            	Storage.init();
            }
        })
        
         // 编辑货批入库
         .state('storageEdit', {
            url: "/storageEdit?id",
            templateUrl: config.getResource()+"/pages/inbound/storage_confirm/edit.jsp",
            controller: function($scope,$stateParams){
            	$scope.id = $stateParams.id;
            	var id=$scope.id;
            	Storage.initEdit(id);

            }
        })
        
         
        // 虚拟库列表
         .state('virtualCargo', {
            url: "/virtualCargo",
            templateUrl: config.getResource()+"/pages/inbound/virtualCargo/list.jsp",
            data: {pageTitle: '虚拟库'},
            controller: function($scope,$stateParams){

            	VirtualCargo.init();
            }
        })
        
        // 嫁接
         .state('graftingCargo', {
            url: "/graftingCargo",
            templateUrl: config.getResource()+"/pages/inbound/virtualCargo/graftingCargo.jsp",
            data: {pageTitle: '嫁接'},
            controller: function($scope,$stateParams){

            	VirtualCargo.initGrafting();
            }
        })
        
        
         // 添加虚拟库
         .state('addVirtualCargo', {
            url: "/addVirtualCargo",
            templateUrl: config.getResource()+"/pages/inbound/virtualCargo/add.jsp",
            data: {pageTitle: '虚拟库'},
            controller: function($scope,$stateParams){

            	VirtualCargo.initAdd();
            }
        })
        
         // 虚拟库列表
         .state('cargoPK', {
            url: "/cargoPK",
            templateUrl: config.getResource()+"/pages/inbound/cargoPK/list.jsp",
            data: {pageTitle: '盘库'},
            controller: function($scope,$stateParams){

            	CargoPK.init();
            }
        })
        // 虚拟库列表
         .state('productChange', {
            url: "/productChange",
            templateUrl: config.getResource()+"/pages/inbound/productChange/list.jsp",
            data: {pageTitle: '品名更换'},
            controller: function($scope,$stateParams){

            	ProductChange.init();
            }
        })
        
        
        /**
		 * ------------------------------------------------------------ 提单管理
		 * --------------------------------------------------------------
		 */
       
        
        
        // 货体管理
         .state('goodsManager', {
            url: "/goodsManager",
            templateUrl: config.getResource()+"/pages/outbound/goodsManager/list.jsp",
            data: {pageTitle: '货体管理'},
            controller: function($scope,$stateParams){

            	goodsManager.init();
            }
        })
        
        // 编辑货批入库
         .state('goodsManagerEdit', {
            url: "/goodsManagerEdit?id&productId",
            templateUrl: config.getResource()+"/pages/outbound/goodsManager/edit.jsp",
            controller: function($scope,$stateParams){
            	$scope.id = $stateParams.id;
            	var id=$scope.id;
            	$scope.productId = $stateParams.productId;
            	var productId=$scope.productId;
            	goodsManager.initEdit(id);
            	goodsManager.setProductId(productId);

            }
        })
        
        // 提单管理
         .state('lading', {
            url: "/lading",
            templateUrl: config.getResource()+"/pages/outbound/lading/ladingList.jsp",
            data: {pageTitle: '提单出库'},
            controller: function($scope,$stateParams){
            	Lading.init();
            }
        })
        
         // 新建提单
         .state('ladingAdd', {
            url: "/ladingAdd",
            templateUrl: config.getResource()+"/pages/outbound/lading/ladingAdd.jsp",
            controller: function($scope,$stateParams){
            	Lading.initAdd();
            	if (jQuery().datepicker) {
    				$('.date-picker').datepicker({
		            autoclose: true,
		            isRTL: Metronic.isRTL(),
		            pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
		        });
            	}
            }
        })
        
        // 修改提单
         .state('ladingEdit', {
            url: "/ladingEdit?id&type",
            templateUrl: config.getResource()+"/pages/outbound/lading/ladingEdit.jsp",
            controller: function($scope,$stateParams){
            	$scope.id = $stateParams.id;
            	$scope.type = $stateParams.type;
            	var id=$scope.id;
            	var type=$scope.type;
            	Lading.initEdit(id,type);
            	if (jQuery().datepicker) {
    				$('.date-picker').datepicker({
		            autoclose: true,
		            isRTL: Metronic.isRTL(),
		            pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
		        });
            	}
            }
        })
        
        /**
		 * ------------------------------------------------------------ 出库到港计划
		 * --------------------------------------------------------------
		 */
        
         // 出库到港计划管理
         .state('shipArrival', {
            url: "/shipArrival",
            templateUrl: config.getResource()+"/pages/outbound/arrival/list.jsp",
            data: {pageTitle: '船发出库到港计划'},
            controller: function($scope,$stateParams){
            	shipArrival.init();
            }
            
        })
        
         // 修改到港信息
        .state('shipArrivalGet', {
            url: "/shipArrivalGet?id",
            templateUrl: config.getResource()+"/pages/outbound/arrival/add.jsp",
            controller: function($scope,$stateParams){
            	
            	shipArrival.initOutBoundPlan($stateParams.id);
            	if (jQuery().datepicker) {
    				$('.date-picker').datepicker({
		            autoclose: true,
		            isRTL: Metronic.isRTL(),
		            pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
		        });
            	}
            } 
        })

        
        // 船发出库---添加到港信息
         .state('shipArrivalAdd', {
            url: "/shipArrivalAdd",
            templateUrl: config.getResource()+"/pages/outbound/arrival/add.jsp",
            controller: function($scope,$stateParams){
            	shipArrival.initOutBoundPlan();
            	if (jQuery().datepicker) {
    				$('.date-picker').datepicker({
		            autoclose: true,
		            isRTL: Metronic.isRTL(),
		            pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
		        });
            	}
            }
        })

         // 发货开票
        .state('invoice/make', {
        	url: "/invoice/make",
        	templateUrl: config.getResource()+"/pages/outbound/invoice/invoice.jsp",
        	controller: function($scope,$stateParams){
        		Invoice.initMake() ;
        	}
        })
        // 仓储费
        .state('storageFee', {
        	url: "/storageFee?type",
        	templateUrl: config.getResource()+"/pages/inbound/storageFee/storageFeeList.jsp",
        	controller: function($scope,$stateParams){
        		StorageFee.init();
//        		StorageFee.initBtn();
        		if($stateParams.type!=null)
        			$("#tabSelect").text($stateParams.type);
            	}
        })
        //单个仓储费查询（首期费，超期费）
        .state('storageFee/get',{
        	url:"/storageFee/get?id&type",
        	templateUrl:config.getResource()+"/pages/inbound/storageFee/storageFeeList.jsp",
        	controller:function($scope,$stateParams){
        		//id 仓储费（首期，超期）type:类别 1，首期(货批) 2，首期费(包罐保量) 3.超期费(货批)4.超期费(提单)
        		StorageFee.initBtn();
        		if($stateParams.type==1){
        			$('#tab1').click();
        			StorageFee.dialogInitialFee(undefined,1,$stateParams.id);
        		}else if($stateParams.type==2){
        			$('#type').val(2);
        			$("#tab1").click();
        			StorageFee.dialogInitialFee(undefined,2,$stateParams.id);
        		}else if($stateParams.type==3||$stateParams.type==4){
        			$('#tab2').click();
        			var ecType=$stateParams.type-2;
        			StorageFee.dialogExceedFee(undefined,ecType,false,$stateParams.id);
        		}
        	}
        })
        // 开票列表页
        .state('invoice/list', {
        	url: "/invoice/list",
        	templateUrl: config.getResource()+"/pages/outbound/invoice/list.jsp",
        	controller: function($scope,$stateParams){
        		Invoice.initList() ;
        	}
        })
        // 船发流量计台账
        .state('shipDeliveryMeasure/list', {
        	url: "/shipDeliveryMeasure/list?shipId",
        	templateUrl: config.getResource()+"/pages/outbound/shipDeliveryMeasure/list.jsp",
        	controller: function($scope,$stateParams){
        		shipDeliveryMeasure.initList($stateParams.shipId) ;
        	}
        })
        // 查询车位发货日报表信息
        .state('parkDailyStatement/list', {
        	url: "/parkDailyStatement/list",
        	templateUrl: config.getResource()+"/pages/outbound/vehicleDeliveryStatement/parkDailyStatementList.jsp",
        	controller: function($scope,$stateParams){
        		vehicleDeliveryStatement.initParkDailyStatement() ;
        	}
        })
        // 查询称重发货日报表信息
        .state('weighDailyStatement/list', {
        	url: "/weighDailyStatement/list",
        	templateUrl: config.getResource()+"/pages/outbound/vehicleDeliveryStatement/weighDailyStatementList.jsp",
        	controller: function($scope,$stateParams){
        		weighDataTable.init();
        	}
        })
        // 查询车位发货月报表
        .state('vehicleMonthlyStatement/list', {
        	url: "/vehicleMonthlyStatement/list",
        	templateUrl: config.getResource()+"/pages/outbound/vehicleDeliveryStatement/vehicleMonthlyStatementList.jsp",
        	controller: function($scope,$stateParams){
        		vehicleDeliveryStatement.initVehicleMonthlyStatement();
        	}
        })
        // 查询车位发货历史累计量报表
        .state('vehicleHistoryCumulantStatement/list', {
        	url: "/vehicleHistoryCumulantStatement/list",
        	templateUrl: config.getResource()+"/pages/outbound/vehicleDeliveryStatement/vehicleHistoryCumulantStatementList.jsp",
        	controller: function($scope,$stateParams){
        		vehicleDeliveryStatement.initVehicleHistoryCumulantStatement() ;
        	}
        })
        // 查询车位流量计月报总表
        .state('vehicleMonthlyTotalStatement/list', {
        	url: "/vehicleMonthlyTotalStatement/list",
        	templateUrl: config.getResource()+"/pages/outbound/vehicleDeliveryStatement/vehicleMonthlyTotalStatementList.jsp",
        	controller: function($scope,$stateParams){
        		vehicleDeliveryStatement.initVehicleMonthlyTotalStatement() ;
        	}
        })
        // 查询货品月发货量报表
        .state('productMonthlyStatement/list', {
        	url: "/productMonthlyStatement/list",
        	templateUrl: config.getResource()+"/pages/outbound/vehicleDeliveryStatement/productMonthlyStatementList.jsp",
        	controller: function($scope,$stateParams){
        		vehicleDeliveryStatement.initProductMonthlyStatement() ;
        	}
        })
        // 船发流量台账修改页 添加页
        .state('shipDeliveryMeasure/addOrEdit', {
        	url: "/shipDeliveryMeasure/addOrEdit?id",
        	templateUrl: config.getResource()+"/pages/outbound/shipDeliveryMeasure/edit.jsp",
        	controller: function($scope,$stateParams){
        		shipDeliveryMeasure.initEdit($stateParams.id) ;
        	}
        })
        // 船发流量台账修改页 添加页
        .state('weighBridge', {
        	url: "/weighBridge",
        	templateUrl: config.getResource()+"/pages/outbound/weighBridge/weighBridge.jsp",
        	controller: function($scope,$stateParams){
        		weighBridge.initWeight() ;
        	}
        })
        // 船发流量台账修改页 添加页
        .state('shipWeighBridge', {
        	url: "/shipWeighBridge",
        	templateUrl: config.getResource()+"/pages/outbound/weighBridge/shipWeighBridge.jsp",
        	controller: function($scope,$stateParams){
        		shipWeighBridge.initWeight($(".shipWeighBridgeBody")) ;
        	}
        })
        // 开票编辑页
        .state('invoice/get', {
        	url: "/invoice/get?id",
        	templateUrl: config.getResource()+"/pages/outbound/invoice/edit.jsp",
        	controller: function($scope,$stateParams){
        		Invoice.initEdit($stateParams.id) ;
        	}
        })

        
        // 船舶出库
         .state('outboundserial/list', {
            url: "/outboundserial/list",
            data: {pageTitle: '船发作业'},
            templateUrl: config.getResource()+"/pages/outbound/outbound/list_outboundoperation.jsp",
            controller: function($scope,$stateParams){
            	$("#tabTitle").text("船发作业");
            	Outbound.init(1) ;
            }
        })// 船舶出库
         .state('outboundzhuanshu/list', {
            url: "/outboundzhuanshu/list",
            data: {pageTitle: '转输输出'},
            templateUrl: config.getResource()+"/pages/outbound/outbound/list_outboundoperation.jsp",
            controller: function($scope,$stateParams){
            	$("#tabTitle").text("转输输出");
            	Outbound.init(2);
            }
        })
             // 船舶出库查看
         .state('outboundserial/get', {
            url: "/outboundserial/get?status&id",
            templateUrl: config.getResource()+"/pages/outbound/outbound/detail_outboundoperation.jsp",
            controller: function($scope,$stateParams){
            	Outbound.initView($stateParams.status,$stateParams.id,1) ;
            }
        }).state('outboundzhuanshu/get', {
            url: "/outboundzhuanshu/get?status&id",
            templateUrl: config.getResource()+"/pages/outbound/outbound/detail_outboundoperation.jsp",
            controller: function($scope,$stateParams){
            	Outbound.initView($stateParams.status,$stateParams.id,2) ;
            }
        })
         /**
			 * ------------------------------------------------------------
			 * 车发出库开始
			 * --------------------------------------------------------------
			 */
         // 车发出库
         .state('outboundtruckserial/list', {
            url: "/outboundtruckserial/list",
            data: {pageTitle: '车发作业'},
            templateUrl: config.getResource()+"/pages/outbound/truckwork/list_truckserial.jsp",
            controller: function($scope,$stateParams){
            	v_outbound.init() ;
            }
        })
       // 车发出库查看
         .state('outboundtruckserial/get', {
            url: "/outboundtruckserial/get?status&id&serial",
            templateUrl: config.getResource()+"/pages/outbound/truckwork/outbound_tab.jsp",
            controller: function($scope,$stateParams){
            	v_outbound.initTab($stateParams.status,$stateParams.id,$stateParams.serial) ;
            }
        })
    // 船发分流台账
         .state('shipflowbook/list', {
            url: "/shipflowbook/list?start&end",
            data: {pageTitle: '船发分流'},
            templateUrl: config.getResource()+"/pages/shipflowbook/shipflowbook_list.jsp",
            controller: function($scope,$stateParams){
            	ShipFlowBook.init($stateParams.start,$stateParams.end) ;
            }
        })
         // 码头规费
        .state('arrivalBill/list',{
        	url:"/arrivalBill/list",
        	templateUrl:config.getResource()+"/pages/inbound/dockFee/dockFeeList.jsp",
        	data:{pageTitle:"码头规费"},
        	controller: function($scope,$stateParams){
        		DockFee.initTable(2);
        		DockFee.initSearchCtr();
        	}
        })
        
        // 码头规费修改
        .state('bill/Edit',{
        	url:"/billEdit?id",
        	templateUrl:config.getResource()+"/pages/inbound/arrivalBill/edit.jsp",
        	data:{pageTitle:"码头规费修改"},
        	controller: function($scope,$stateParams){
        		arrivalBill.initEdit($stateParams.id);
        	}
        })

	   /**
		 * 
		 * ---------------------------------------------- 分流台账日志
		 * ---------------------------------------------
		 */
        .state('o_operationlog',{
        	url:"/o_operationlog",
        	templateUrl:config.getResource()+"/pages/outbound/operationlog/list_operation.jsp",
        	data:{pageTitle:"调度日志"},
        	controller:function($scope,$stateParams){
        		Metronic.init();
        		o_ListOperation.init();
        	}
        })
        /**
		 * ------------------------------------------------------------ 权限管理开始
		 * --------------------------------------------------------------
		 */
        .state('user/list', {// 用户管理
            url: "/user/list",
            templateUrl: config.getResource()+"/pages/auth/user/list.jsp",
            controller: function($scope,$stateParams,$ocLazyLoad){
            	User.init();
            }
        })
        
        .state('user/grantRole', {// 用户管理
            url: "/user/grantRole?id&name",
            templateUrl: config.getResource()+"/pages/auth/user/grantAuthorityToUser.jsp",
            controller: function($scope,$stateParams,$ocLazyLoad){
            	User.grantRole($stateParams.id,$stateParams.name);
            }
        })
        
        .state('role/list', {// 角色管理
            url: "/role/list",
            templateUrl: config.getResource()+"/pages/auth/role/list.jsp",
            controller: function($scope,$stateParams,$ocLazyLoad){
            	Role.init();
            }
        })
        
        .state('role/role', {// 角色管理
            url: "/role/role?id",
            templateUrl: config.getResource()+"/pages/auth/role/role.jsp",
            controller: function($scope,$stateParams,$ocLazyLoad){
            	Role.openRole($stateParams.id);
            }
        })
        
        .state('role/grantPermission', {// 权限分配
            url: "/role/grantPermission?id&name",
            templateUrl: config.getResource()+"/pages/auth/role/grantPermissionToRole.jsp",
            controller: function($scope,$stateParams,$ocLazyLoad){
            	Role.grantPermission($stateParams.id,$stateParams.name);
            }
        })
        
        .state('resource/list', {// 资源管理
            url: "/resource/list",
            templateUrl: config.getResource()+"/pages/auth/resource/list.jsp",
            controller: function($scope,$stateParams,$ocLazyLoad){
            	Resource.init();
            }
        })
        .state('organization/list', {// 资源管理
            url: "/organization/list",
            templateUrl: config.getResource()+"/pages/auth/organization/list.jsp",
            controller: function($scope,$stateParams,$ocLazyLoad){
            	Organization.init();
            }
        })
        .state('baseinfo/list', {// 基础管理
            url: "/baseinfo/list?item",
            templateUrl: config.getResource()+"/pages/auth/baseinfo/list_baseinfo.jsp",
            controller: function($scope,$stateParams,$ocLazyLoad){
//            	BaseInfo.changetab("",$stateParams.item);
            }
        })
        .state('user/acount', {// 用户基本信息
            url: "/user/acount?id",
            data:{pageTitle:"个人信息"},
            templateUrl: config.getResource()+"/pages/auth/user/account.jsp",
            controller: function($scope,$stateParams,$ocLazyLoad){
            	User.initAccount($stateParams.id);
            },
        	resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        insertBefore: '#ng_load_plugins_before', // load the above css files before '#ng_load_plugins_before'
                        files: [
//                                config.getResource()+'/global/plugins/jcrop/css/jquery.Jcrop.min.css',
//                                config.getResource()+'/global/plugins/jcrop/js/jquery.Jcrop.min.js'
                        ] 
                    });
                }]
            }
        })
        .state('session', {// 用户session
            url: "/session",
            data:{pageTitle:"在线用户"},
            templateUrl: config.getResource()+"/pages/auth/user/session.jsp",
            controller: function($scope,$stateParams,$ocLazyLoad){
            	Session.init();
            }
        })
        /**
		 * ------------------------------------------------------------ 权限管理结束
		 * --------------------------------------------------------------
		 */
        /**
		 * jhy
		 *  ------------------------------------------------ 
		 *  入库作业
		 * -------------------------------------------------
		 */
        //入库作业列表
        .state('inboundoperation',{
        	url:'/inboundoperation',
        	templateUrl:config.getResource()+"/pages/inbound/inboundoperation/list_inboundoperation.jsp",
        	data:{pageTitle:"入库作业列表"},
        	controller:function($scope,$stateParams){
        		$("#isTransport").text(1);//入库作业
        		$("#tabTitle").text("入库作业");
        		var status='2,3,4,5,6,7,8';
    			var params={
    				'statuskey':status
    			}
        		InboundOperation.initSearch(1);//初始化搜索
        		if(config.hasPermission('ANOTICEDODYNAMIC')&&!config.hasPermission('ANOTICEDODOCK')||!config.hasPermission('ANOTICEDODYNAMIC')&&config.hasPermission('ANOTICEDODOCK')){//动力班，操作班
                    $("#inbound_tab5").click();        			
        		}
        		else if(config.hasPermission('AUPLOADINGSAVE')&&!config.hasPermission('ANOTICEDISPATCH')){
        			 $("#inbound_tab7").click();    
        		}
        		else{
        			$("#inbound_tab1").click(); 
//        			InboundOperation.initTab(1,0,params);//初始化首页面
        		}
        		
            	if (jQuery().datepicker) {
    				$('.date-picker').datepicker({
    					rtl: Metronic.isRTL(),
    				    orientation: "left",
    				    format: "yyyy-mm-dd",
    				    showInputs:true,
    				    startView:"days",
    				    minViewMode:"days",
    				    showDay:"false",
    		            disableMousewheel:false,
    		            changeMonth :false,
    				    autoclose: true,
		        });
            	}
        	}
        })
        //转输作业列表
        .state('inboundzhuanshu',{
        	url:'/inboundzhuanshu',
        	templateUrl:config.getResource()+"/pages/inbound/inboundoperation/list_inboundoperation.jsp",
        	data:{pageTitle:"转输作业列表"},
        	controller:function($scope,$stateParams){
        		$("#tabTitle").text("转输输入");
        		$("#isTransport").text(2);//转输作业
        		var status='2,3,4,5,6,7,8';
    			var params={
    				'statuskey':status
    			}
        		InboundOperation.initSearch(2);//初始化搜索
        		if(config.hasPermission('ANOTICEDODYNAMIC')&&!config.hasPermission('ANOTICEDODOCK')||!config.hasPermission('ANOTICEDODYNAMIC')&&config.hasPermission('ANOTICEDODOCK')){//动力班，操作班
                    $("#inbound_tab5").click();        			
        		}
        		else if(config.hasPermission('AUPLOADINGSAVE')&&!config.hasPermission('ANOTICEDISPATCH')){
        			 $("#inbound_tab8").click();    
        		}
        		else{
        			$("#inbound_tab1").click(); 
//        			InboundOperation.initTab(1,0,params);//初始化首页面
        		}
        		
            	if (jQuery().datepicker) {
    				$('.date-picker').datepicker({
    					rtl: Metronic.isRTL(),
    				    orientation: "left",
    				    format: "yyyy-mm-dd",
    				    showInputs:true,
    				    startView:"days",
    				    minViewMode:"days",
    				    showDay:"false",
    		            disableMousewheel:false,
    		            changeMonth :false,
    				    autoclose: true,
		        });
            	}
        	
        	}
        })
        //修改入库作业
        .state('updateinboundoperation',{
        	url:'/updateinboundoperation',
        	templateUrl:config.getResource()+"/pages/inbound/inboundoperation/update_inboundoperation.jsp",
        	data:{pageTitle:"入库作业列表"},
        	controller:function($scope,$stateParams){
        	}
        })
        //进入流程
        .state('inboundoperation/get',{
        	url:"/inboundoperation/get?state&arrivalId&productId&orderNum&backflowId&isTransport",
        	templateUrl:config.getResource()+"/pages/inbound/inboundoperation/detail_inboundoperation.jsp",
        	data:{pageTitle:"入库作业"},
        	 controller: function($scope,$stateParams){
        		 $scope.isTransport = $stateParams.isTransport;
        		 $scope.state=$stateParams.state;
        		 $scope.arrivalId=$stateParams.arrivalId;
//        		 $scope.index=$stateParams.index;
        		 $scope.productId=$stateParams.productId;
        		 $scope.orderNum=$stateParams.orderNum;
        		 $scope.backflowId=$stateParams.backflowId;
//             	  var page = $scope.page ;	
             	  var state=$scope.state;
             	  var arrivalId=$scope.arrivalId;
//             	  var index=$scope.index;
             	  var productId=$scope.productId;
             	 var orderNum=$scope.orderNum;
             	  var backflowId=$scope.backflowId;
             	  var isTransport=$scope.isTransport;
             	 InboundOperation.initGlobalValue();//初始化全局变量
             	 InboundOperation.initView(state,arrivalId,productId,orderNum,backflowId,isTransport);
             }
        })
        //进入流程
        .state('inboundzhuanshu/get',{
        	url:"/inboundzhuanshu/get?state&arrivalId&productId&orderNum&backflowId&isTransport",
        	templateUrl:config.getResource()+"/pages/inbound/inboundoperation/detail_inboundoperation.jsp",
        	data:{pageTitle:"转输作业"},
        	 controller: function($scope,$stateParams){
        		 $scope.isTransport = $stateParams.isTransport;
        		 $scope.state=$stateParams.state;
        		 $scope.arrivalId=$stateParams.arrivalId;
//        		 $scope.index=$stateParams.index;
        		 $scope.productId=$stateParams.productId;
        		 $scope.orderNum=$stateParams.orderNum;
        		 $scope.backflowId=$stateParams.backflowId;
//             	  var page = $scope.page ;	
             	  var state=$scope.state;
             	  var arrivalId=$scope.arrivalId;
//             	  var index=$scope.index;
             	  var productId=$scope.productId;
             	 var orderNum=$scope.orderNum;
             	  var backflowId=$scope.backflowId;
             	  var isTransport=$scope.isTransport;
             	 InboundOperation.initGlobalValue();//初始化全局变量
             	 InboundOperation.initView(state,arrivalId,productId,orderNum,backflowId,isTransport);
             }
        })
        .state('notify',{
        	url:"/notify",
        	templateUrl:config.getResource()+"/pages/inbound/notify/list.jsp",
        	data:{pageTitle:"通知单"},
        	controller:function($scope,$stateParams) {
        		if (jQuery().datepicker) {
    				$('.date-picker').datepicker({
    					rtl: Metronic.isRTL(),
    				    orientation: "left",
    				    format: "yyyy-mm-dd",
    				    showInputs:true,
    				    startView:"days",
    				    minViewMode:"days",
    				    showDay:"false",
    		            disableMousewheel:false,
    		            changeMonth :false,
    				    autoclose: true,
		        });
            	}
        	}
        })
        
        .state('notifydetail',{
        	url:"/notify/detail?item",
        	templateUrl:config.getResource()+"/pages/inbound/notify/list_detail.jsp",
        	data:{pageTitle:"通知单"},
        	controller:function($scope,$stateParams) {
        		NotifyList.listGrid($stateParams.item);   //显示通知单按钮及列表详情
        		notify.initNotifyBtn();
        		if (jQuery().datepicker) {
    				$('.date-picker').datepicker({
    					rtl: Metronic.isRTL(),
    				    orientation: "left",
    				    format: "yyyy-mm-dd",
    				    showInputs:true,
    				    startView:"days",
    				    minViewMode:"days",
    				    showDay:"false",
    		            disableMousewheel:false,
    		            changeMonth :false,
    				    autoclose: true,
		        });
            	}
        	}
        })
        /**
		 * 
		 * ---------------------------------------------- 调度日志
		 * ---------------------------------------------
		 */
        .state('operationlog',{
        	url:"/operationlog",
        	templateUrl:config.getResource()+"/pages/inbound/operationlog/list_operationlog.jsp",
        	data:{pageTitle:"调度日志"},
        	controller:function($scope,$stateParams){
        		Metronic.init();
        		ListOperation.init();
        	}
        })
        .state('itemoperationlog',{
        	url:'/itemoperationlog?start&end',
        	templateUrl:config.getResource()+"/pages/inbound/operationlog/item_operationlog.jsp",
        	data:{pageTitle:"每日调度日志"},
        	controller:function($scope,$stateParams){
        		var start=$stateParams.start;
        		var end=$stateParams.end;
        		ItemOperation.init(start,end);
        	}
        })
        .state('updateoperationlog',{
        	url:'/updateoperationlog?id',
        	templateUrl:config.getResource()+"/pages/inbound/operationlog/update_operationlog.jsp",
        	data:{pageTitle:"修改每日调度日志"},
        	controller:function($scope,$stateParams){
        		var id=$stateParams.id;
        		ItemOperation.initUpdate(id);
        		$(".form_datetime").datetimepicker({
                    autoclose: true,
                    isRTL: Metronic.isRTL(),
                    format: "yyyy-mm-dd hh:ii:ss",
                    pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
                });
        	}
        })
        
        
        /**
         * 修改分流台账信息
         */
        .state('updateshipflowbooklog',{
        	url:'/updateshipflowbooklog?id',
        	templateUrl:config.getResource()+"/pages/shipflowbook/update_operationlog.jsp",
        	data:{pageTitle:"编辑分流台账信息"},
        	controller:function($scope,$stateParams){
        		ShipFlowBook.initEdit($stateParams.id);
        	}
        })
       /**
		 * ----------------------------------------------------- 储罐台账
		 * -----------------------------------------------------
		 */
        .state('tanklog',{
        	url:'/tanklog?tankId&tankName&type',
        	templateUrl:config.getResource()+"/pages/inbound/tanklog/list_tanklog.jsp",
        	data:{pageTitle:"储罐台账"},
        	controller:function($scope,$stateParams){
        		
        		if($stateParams.type==1){
        			TankLog.initTank(1,$stateParams.tankId,$stateParams.tankName);
        			TankLog.initBtn();
        			if($stateParams.tankId&&$stateParams.tankName)
        			{
        				console.log($stateParams.tankName);
        				$('#tankId1').val($stateParams.tankName);
        				$('#tankId1').attr("data",$stateParams.tankId);
        			}
        		}else{
        			
        			if($stateParams.tankId&&$stateParams.tankName)
        			{
        				$('#tankId1').val($stateParams.tankName);
        				$('#tankId1').attr("data",$stateParams.tankId);
        			}
        			TankLog.initTank(0,0,0);
        			TankLog.initBtn();
        		}
        		
        	}
        })

        
        
        
        /*.state('tanklog',{
        	url:'/tanklog',
        	templateUrl:config.getResource()+"/pages/inbound/tanklog/list_tanklog.jsp",
        	data:{pageTitle:"储罐台账"},
        	controller:function($scope,$stateParams){
        		TankLog.initTank();
        		TankLog.initBtn();
        	}
        })*/
        .state('addtanklog',{
        	url:'/addtanklog?tankId&tankName',
        	templateUrl:config.getResource()+"/pages/inbound/tanklog/add_tanklog.jsp",
        	data:{pageTitle:"添加储罐台账"},
        	controller:function($scope,$stateParams){
        		TankLog.initAdd($stateParams.tankId,$stateParams.tankName);
        		$(".form_datetime").datetimepicker({
		            autoclose: true,
		            isRTL: Metronic.isRTL(),
		            format: "yyyy-mm-dd hh:ii:ss",
		            pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
		        });
        	}
        })
         .state('updatetanklog',{
        	url:'/updatetanklog?id',
        	templateUrl:config.getResource()+"/pages/inbound/tanklog/update_tanklog.jsp",
        	data:{pageTitle:"修改储罐台账"},
        	controller:function($scope,$stateParams){
        		$scope.id = $stateParams.id;
            	var id=$scope.id;
        		TankLog.initUpdate(id);
        		$(".form_datetime").datetimepicker({
		            autoclose: true,
		            isRTL: Metronic.isRTL(),
		            format: "yyyy-mm-dd hh:ii:ss",
		            pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
		        });
        	}
        })
        
    /**
     * -----------------------------------------------------------------------------------------
     * --------------------港务开始----------------------------------------------------------------
     * -----------------------------------------------------------------------------------------
     */
   
     .state('sys/log',{
    	url:'/sys/log',
    	templateUrl:config.getResource()+"/pages/system/log/list.jsp",
    	controller:function($scope,$stateParams){
    		Log.init();
    	}
    })
     .state('goodsoperate/log',{
    	url:'/goodsoperate/log',
    	templateUrl:config.getResource()+"/pages/statistics/log/log.jsp",
    	controller:function($scope,$stateParams){
    		GoodsLog.init();
    	}
    })
    /**
     * -----------------------------------------------------------------------------------------
     * --------------------港务结束----------------------------------------------------------------
     * -----------------------------------------------------------------------------------------
     */
    .state('weight',{
    	url:'/outbound/weight',
    	templateUrl:config.getResource()+"/pages/outbound/weight.jsp",
    	controller:function($scope,$stateParams){
    		
    	}
    })
    /**
     * -----------------------------------------------------------------------------------------
     * ------------------账单管理------------------------------------------------------------------
     * ----------------------------------------------------------------------------------------
     */
    .state("feebill",{
    	url:'/feebill',
    	templateUrl:config.getResource()+"/pages/feebill/feeBillList.jsp",
    	controller:function($scope,$stateParams){
    		feeBill.initTableBtn();
    		feeBill.initTable(1);
    	}
    })
    /**
     * 查看单个账单
     * 
     */
    .state("feebill/get",{
    	url:'/feebill/get?id',
    	templateUrl:config.getResource()+"/pages/feebill/feeBillList.jsp",
    	controller:function($scope,$stateParams){
    		feeBill.initTableBtn();
    		feeBill.initTable(2);
    		feeBill.dialogFeeBill(undefined,$stateParams.id);
    	}
    })
    /**
     * -----------------------------------------------------------------------------------------
     * ------------------账单管理------------------------------------------------------------------
     * ----------------------------------------------------------------------------------------
     */
    .state("dockfeebill",{
    	url:'/dockfeebill',
    	templateUrl:config.getResource()+"/pages/feebill/dockFeeBillList.jsp",
    	controller:function($scope,$stateParams){
    		DockFeeBill.initSearchCtr();
    		DockFeeBill.initTable(1);
    	}
    })
    /**
     * 查看单个账单
     * 
     */
    .state("dockfeebill/get",{
    	url:'/dockfeebill/get?id',
    	templateUrl:config.getResource()+"/pages/feebill/feeBillList.jsp",
    	controller:function($scope,$stateParams){
    		DockFeeBill.initSearchCtr();
    		DockFeeBill.initTable(2);
    		DockFeeBill.dialogDockFeeBill(undefined,$stateParams.id);
    	}
    })
    /**
     * -----------------------------------------------------------------------------------------
     * ------------------方案管理------------------------------------------------------------------
     * ----------------------------------------------------------------------------------------
     */
    /**
     * 靠泊方案
     */
    .state("berthplan",{
    	url:'/berthplan',
    	templateUrl:config.getResource()+"/pages/planmanager/planManagerList.jsp",
    	controller:function($scope,$stateParams){
    		$(".titlemanager").empty();
    		$(".titlemanager").append('<i class="fa fa-yelp"></i>靠泊方案<a style="padding-left: 20px;" href="javascript:void(0);" onclick="PlanManager.exportExcel()"><i class="fa fa-file-excel-o">&nbsp;导出</i></a>');
    		PlanManager.init(1);
    	}
    })
    /**
     * 接卸方案
     */
    .state("unloadingplan",{
    	url:'/unloadingplan',
    	templateUrl:config.getResource()+"/pages/planmanager/planManagerList.jsp",
    	controller:function($scope,$stateParams){
    		$(".titlemanager").empty();
    		$(".titlemanager").append('<i class="fa fa-yelp"></i>接卸方案<a style="padding-left: 20px;" href="javascript:void(0);" onclick="PlanManager.exportExcel()"><i class="fa fa-file-excel-o">&nbsp;导出</i></a>');
    		PlanManager.init(2);
    	}
    })
    /**
     * 倒罐方案
     */
    .state("changetankplan",{
    	url:'/changetankplan',
    	templateUrl:config.getResource()+"/pages/planmanager/planManagerList.jsp",
    	controller:function($scope,$stateParams){
    		$(".titlemanager").empty();
    		$(".titlemanager").append('<i class="fa fa-yelp"></i>倒罐方案<a style="padding-left: 20px;" href="javascript:void(0);" onclick="PlanManager.exportExcel()"><i class="fa fa-file-excel-o">&nbsp;导出</i></a>');
    		PlanManager.init(3);
    	}
    })
    /**
     * 打循环
     */
    .state("backflowplan",{
    	url:'/backflowplan',
    	templateUrl:config.getResource()+"/pages/planmanager/planManagerList.jsp",
    	controller:function($scope,$stateParams){
    		$(".titlemanager").empty();
    		$(".titlemanager").append('<i class="fa fa-yelp"></i>打循环方案<a style="padding-left: 20px;" href="javascript:void(0);" onclick="PlanManager.exportExcel()"><i class="fa fa-file-excel-o">&nbsp;导出</i></a>');
    		PlanManager.init(4);
    	}
    })
    /**
     * 添加打循环
     */
    .state("/backflowplan/addbackflow",{
    	url:'/backflowplan/addbackflow',
    	templateUrl:config.getResource()+"/pages/planmanager/backFlow.jsp",
    	controller:function($scope,$stateParams){
    		$(".planTitle").empty().append('打循环方案');
    		PlanManager.initBackFLow(null,0,1);
    	}
    })
    /**
     * 添加倒罐
     */
    .state("/changetankplan/addchangetank",{
    	url:'/changetankplan/addchangetank',
    	templateUrl:config.getResource()+"/pages/planmanager/backFlow.jsp",
    	controller:function($scope,$stateParams){
    		$(".planTitle").empty().append('倒罐方案');
    		PlanManager.initBackFLow(null,0,3);
    	}
    })
    /**
     * 查看打循环
     */
    .state("/backflowplan/getbackflow",{
    	url:'/backflowplan/getbackflow?id&status',
    	templateUrl:config.getResource()+"/pages/planmanager/backFlow.jsp",
    	controller:function($scope,$stateParams){
    		$(".planTitle").empty().append('打循环方案');
    		$scope.id=$stateParams.id;
    		$scope.status=$stateParams.status;
    		PlanManager.initBackFLow($scope.id,$scope.status);
    	}
    })
    /**
     * 查看倒罐方案
     */
    .state("/changetankplan/getchangetank",{
    	url:'/changetankplan/getchangetank?id&status',
    	templateUrl:config.getResource()+"/pages/planmanager/backFlow.jsp",
    	controller:function($scope,$stateParams){
    		$(".planTitle").empty().append('倒罐方案');
    		$scope.id=$stateParams.id;
    		$scope.status=$stateParams.status;
    		PlanManager.initBackFLow($scope.id,$scope.status);
    	}
    })
    /**
     * 生产报表
     */
    .state("/reportform/list",{
    	url:'/reportform/list',
    	templateUrl:config.getResource()+"/pages/reportform/list.jsp",
    	controller:function($scope,$stateParams){
    		ReportForm.init();
    	}
    })
    /**
     * 生产报表
     */
    .state("/tankmeasure",{
    	url:'/tankmeasure/list',
    	templateUrl:config.getResource()+"/pages/auth/baseinfo/tankmeasure/list.jsp",
    	controller:function($scope,$stateParams){
    		TankMeasure.init();
    	}
    }) /**
     * 海关放行统计
     */
    .state("/customsrelease",{
    	url:'/customsrelease/list',
    	templateUrl:config.getResource()+"/pages/inbound/customsrelease/list.jsp",
    	controller:function($scope,$stateParams){
    		customsRelease.init();
    	}
    }) /**
     * 发货查询
     */
    .state("/invoicequery",{
    	url:'/invoicequery/list',
    	templateUrl:config.getResource()+"/pages/statistics/invoiceQuery/list.jsp",
    	controller:function($scope,$stateParams){
    		InvoiceQuery.init();
    	}
    })
     .state("/inboundbook",{
    	url:'/inboundbook/list',
    	templateUrl:config.getResource()+"/pages/report/inboundBook.jsp",
    	controller:function($scope,$stateParams){
    		InBoundBook.init();
    	}})
     .state("/outboundbook",{
     	url:'/outboundbook/list',
     	templateUrl:config.getResource()+"/pages/report/outboundBook.jsp",
     	controller:function($scope,$stateParams){
     		OutBoundBook.init();
     	}
    })
    ;
}]);


/* Init global settings and run the app */
MetronicApp.run(["$rootScope", "settings", "$state", function($rootScope, settings, $state) {
    // $rootScope.$state = $state; // state to be accessed from view
	 $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
		 sessionStorage.setItem("repairType", 0); 
       config.unload();
       //初始化导航
       try{
    	   var _menu_active = $(".page-sidebar-menu").find("a[href='#"+toState.url+"']");
    	   if(toState.url.indexOf("?")> 0) {
    		   _menu_active = $(".page-sidebar-menu").find("a[href='#"+toState.url.substring(0,toState.url.indexOf("?"))+"']");
    	   }
    	   if(_menu_active.length > 0) {
    		   $(".page-breadcrumb").html("");
        	   var sub_menu = _menu_active.parents("ul[class='sub-menu']");
        	   if(sub_menu.length > 0) {
        		   $(".page-breadcrumb").append("<li>"+sub_menu.parent("li").children("a[class='base']").html()+"<i class='fa fa-angle-right'></i></li>");  
        	   }
        	   $(".page-breadcrumb").append("<li>"+_menu_active.html()+"</li>"); 
    	   }else if(toState.data != null && toState.data.pageTitle != null) {
    		   $(".page-breadcrumb").html("");
    		   $(".page-breadcrumb").append("<li><i class='fa fa-home'></i><span class='title'>"+toState.data.pageTitle+"</span></li>");
    	   }
       }catch(err) {
       }
       clearTimeout(config.timmer.weightTimmer);//清除称重调用定时器
    });
}]);