/***
Wrapper/Helper Class for datagrid based on jQuery Datatable Plugin
***/
var Datatable = function() {

    var tableOptions; // main options
    var dataTable; // datatable object
    var table; // actual table jquery object
    var tableContainer; // actual table container object
    var tableWrapper; // actual table wrapper jquery object
    var tableInitialized = false;
    var ajaxParams = {}; // set filter mode
    var the;

    var countSelectedRecords = function() {
        var selected = $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).size();
        var text = tableOptions.dataTable.language.metronicGroupActions;
        if (selected > 0) {
            $('.table-group-actions > span', tableWrapper).text(text.replace("_TOTAL_", selected));
        } else {
            $('.table-group-actions > span', tableWrapper).text("");
        }
    };
    
    $.extend(true, $.fn.DataTable.TableTools.classes, {
        "container": "btn-group tabletools-dropdown-on-portlet",
        "buttons": {
            "normal": "btn btn-sm default",
            "disabled": "btn btn-sm default disabled"
        },
        "collection": {
            "container": "DTTT_dropdown dropdown-menu tabletools-dropdown-menu"
        }
    });

    return {

        //main function to initiate the module
        init: function(options) {

            if (!$().dataTable) {
                return;
            }

            the = this;

            // default settings
            options = $.extend(true, {
                src: "", // actual table  
                filterApplyAction: "filter",
                filterCancelAction: "filter_cancel",
                resetGroupActionInputOnSuccess: true,
                loadingMessage: '正在加载...',
                dataTable: {
                    "dom": "<'table-scrollable't><'row'<'col-md-12 col-sm-12'pli>>", // datatable layout
                    "pageLength": 25, // default records per page
                    "language": { // language settings
                        // metronic spesific
                        "metronicGroupActions": "_TOTAL_ records selected:  ",
                        "metronicAjaxRequestGeneralError": "内部错误. 请联系管理员",

                        // data tables spesific
                        "lengthMenu": "每页 _MENU_ 条",
                        "info": "<span class='seperator'>|</span>共 _TOTAL_ 条",
                        "infoEmpty": "<span class='seperator'>|</span>共0条数据",
                        "emptyTable": "无数据",
                        "zeroRecords": "无符合条件的数据",
                        "paginate": {
                            "previous": "<",
                            "next": ">",
                            "last": ">>",
                            "first": "<<",
                            "page": "第",
                            "pageOf": "页,共"
                        }
                    },

                    "orderCellsTop": true,
                    "columnDefs": [{ // define columns sorting options(by default all columns are sortable extept the first checkbox column)
                        'orderable': false,
                        'targets': [0]
                    }],
                    //"pagingType": "bootstrap_extended", // pagination type(bootstrap, bootstrap_full_number or bootstrap_extended)
                    "pagingType": 'full_numbers' , // pagination type(bootstrap, bootstrap_full_number or bootstrap_extended)
                    "autoWidth": true, // disable fixed width and enable fluid table
                    "processing": true, // enable/disable display message box on record load
                    "serverSide": true, // enable/disable server side ajax loading
                    "bFilter" : true,
                    "ajax": { // define ajax settings
                        "url": "", // ajax URL
                        "type": "POST", // request type
                        "timeout": 20000,
                        "data": function(data) { // add request parameters before submit
                        	 $.each(ajaxParams, function(key, value) {
                                 data[key] = value;
                             });
                        	 var queryClientIds = $("#search").attr("data");
                        	 if(queryClientIds == null || queryClientIds == undefined) {
                        		 queryClientIds = "";
                        	 }
                            data["queryClientIds"] = queryClientIds;
                        },
                        "beforeSend" : function() {
                        	config.load();
                        },
                        "complete" : function() {
                        	config.unload();
                        },
                        "dataSrc": function(res) { // Manipulate the data returned from the server

                            if (res.customActionStatus) {
                                if (tableOptions.resetGroupActionInputOnSuccess) {
                                    $('.table-group-action-input', tableWrapper).val("");
                                }
                            }

                            if ($('.group-checkable', table).size() === 1) {
                                $('.group-checkable', table).attr("checked", false);
                                $.uniform.update($('.group-checkable', table));
                            }

                            if (tableOptions.onSuccess) {
                                tableOptions.onSuccess.call(undefined, the);
                            }

                            Metronic.unblockUI(tableContainer);
                            
                            return res.data;
                        },
                        "error": function() { // handle general connection errors
                            if (tableOptions.onError) {
                                tableOptions.onError.call(undefined, the);
                            }

                            Metronic.unblockUI(tableContainer);
                        }
                    },

                    "drawCallback": function(oSettings) { // run some code on table redraw
                        if (tableInitialized === false) { // check if table has been initialized
                            tableInitialized = true; // set table initialized
                            table.show(); // display table
                        }
                        Metronic.initUniform($('input[type="checkbox"]', table)); // reinitialize uniform checkboxes on each table reload
                        countSelectedRecords(); // reset selected records indicator
                    },
                    "tableTools": {
                        "sSwfPath": config.getDomain()+"/resource/global/plugins/datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf",
                        "aButtons": [{
                            "sExtends": "pdf",
                            "sButtonText": "PDF"
                        }, {
                            "sExtends": "csv",
                            "sButtonText": "CSV"
                        }, {
                            "sExtends": "xls",
                            "sButtonText": "Excel"
                        }, {
                            "sExtends": "print",
                            "sButtonText": "Print",
                            "sInfo": 'Please press "CTR+P" to print or "ESC" to quit',
                            "sMessage": "Generated by DataTables"
                        }]
                    }
                }
            }, options);

            tableOptions = options;

            // create table's jquery object
            table = $(options.src);
            tableContainer = table.parents(".table-container");

            // apply the special class that used to restyle the default datatable
            var tmp = $.fn.dataTableExt.oStdClasses;

            $.fn.dataTableExt.oStdClasses.sWrapper = $.fn.dataTableExt.oStdClasses.sWrapper + " dataTables_extended_wrapper";
            $.fn.dataTableExt.oStdClasses.sFilterInput = "form-control input-small input-sm input-inline";
            $.fn.dataTableExt.oStdClasses.sLengthSelect = "form-control input-xsmall input-sm input-inline";

            // initialize a datatable
            dataTable = table.DataTable(options.dataTable);

            // revert back to default
            $.fn.dataTableExt.oStdClasses.sWrapper = tmp.sWrapper;
            $.fn.dataTableExt.oStdClasses.sFilterInput = tmp.sFilterInput;
            $.fn.dataTableExt.oStdClasses.sLengthSelect = tmp.sLengthSelect;

            // get table wrapper
            tableWrapper = table.parents('.dataTables_wrapper');

            // build table group actions panel
            if ($('.table-actions-wrapper', tableContainer).size() === 1) {
                $('.table-group-actions', tableWrapper).html($('.table-actions-wrapper', tableContainer).html()); // place the panel inside the wrapper
                $('.table-actions-wrapper', tableContainer).remove(); // remove the template container
            }
            // handle group checkboxes check/uncheck
            $('.group-checkable', table).change(function() {
                var set = $('tbody > tr > td:nth-child(1) input[type="checkbox"]', table);
                var checked = $(this).is(":checked");
                $(set).each(function() {
                    $(this).attr("checked", checked);
                });
                $.uniform.update(set);
                countSelectedRecords();
            });

            // handle row's checkbox click
            table.on('change', 'tbody > tr > td:nth-child(1) input[type="checkbox"]', function() {
                countSelectedRecords();
            });

            // handle filter submit button click
//            table.on('click', '.filter-submit', function(e) {
//                e.preventDefault();
//                the.submitFilter();
//            });
            $('.filter-submit').click(function(e) {
            	e.preventDefault();
                the.submitFilter();
            });

            // handle filter cancel button click
//            table.on('click', '.filter-cancel', function(e) {
//                e.preventDefault();
//                the.resetFilter();
//            });
            $('.filter-cancel').click(function(e) {
            	e.preventDefault();
                the.resetFilter();
            });
            
          
            
        },

        submitFilter: function() {
            the.setAjaxParam("action", tableOptions.filterApplyAction);

            // get all typeable inputs
            $('textarea.form-filter, select.form-filter, input.form-filter:not([type="radio"],[type="checkbox"])').each(function() {
            	if($(this).hasClass("form-filter-data")) {
            		the.setAjaxParam($(this).attr("name"), $(this).attr("data"));
            	}else if($(this).hasClass("date-picker")) {
            		var _date = $(this).val();
            		var _time = $(this).parents(".input-group").find(".timepicker");
            		if(_time) {
            			_date += " "+$(_time).val();
            		}
            		the.setAjaxParam($(this).attr("name"), _date);
            	} else {
            		the.setAjaxParam($(this).attr("name"), $(this).val());
            	}
                
            });

            // get all checkboxes
            $('input.form-filter[type="checkbox"]:checked').each(function() {
                the.addAjaxParam($(this).attr("name"), $(this).val());
            });

            // get all radio buttons
            $('input.form-filter[type="radio"]:checked').each(function() {
                the.setAjaxParam($(this).attr("name"), $(this).val());
            });
            dataTable.ajax.reload();
        },

        resetFilter: function() {
            $('textarea.form-filter, select.form-filter, input.form-filter').each(function() {
            	if($(this).hasClass("form-filter-data")) {
            		$(this).attr("data","");
            	}
            	$(this).val("");
            });
            $('input.form-filter[type="checkbox"]').each(function() {
                $(this).attr("checked", false);
            });
            the.clearAjaxParams();
            the.addAjaxParam("action", tableOptions.filterCancelAction);
            dataTable.ajax.reload();
        },

        getSelectedRowsCount: function() {
            return $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).size();
        },

        getSelectedData: function() {
            var rows = [];
            $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).each(function() {
            	for(var i in dataTable.data()) {
            		if($(this).val() == dataTable.data()[i]['id']) {
            			rows.push(dataTable.data()[i]);
            		}
            	}
            });
            return rows;
        },
        getSelectedRows: function() {
            var rows = [];
            $('tbody > tr > td:nth-child(1) input[type="checkbox"]:checked', table).each(function() {
                rows.push($(this).val());
            });

            return rows;
        },
        setAjaxParam: function(name, value) {
            ajaxParams[name] = value;
        },

        addAjaxParam: function(name, value) {
            if (!ajaxParams[name]) {
                ajaxParams[name] = [];
            }

            skip = false;
            for (var i = 0; i < (ajaxParams[name]).length; i++) { // check for duplicates
                if (ajaxParams[name][i] === value) {
                    skip = true;
                }
            }

            if (skip === false) {
                ajaxParams[name].push(value);
            }
        },

        clearAjaxParams: function(name, value) {
            ajaxParams = {};
        },

        getDataTable: function() {
            return dataTable;
        },

        getTableWrapper: function() {
            return tableWrapper;
        },

        gettableContainer: function() {
            return tableContainer;
        },

        getTable: function() {
            return table;
        }

    };

};