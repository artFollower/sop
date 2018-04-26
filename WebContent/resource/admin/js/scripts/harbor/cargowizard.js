var CargoWizard = function () {


    return {
        //main function to initiate the module
        init: function (catogary) {
            if (!jQuery().bootstrapWizard) {
                return;
            }
            
            initFormIput();

            function format(state) {
                if (!state.id) return state.text; // optgroup
                return "<img class='flag' src='"+config.getResource()+"/global/img/flags/" + state.id.toLowerCase() + ".png'/>&nbsp;&nbsp;" + state.text;
            }
            
            var form = $('#submit_form');
            var error = $('.alert-danger', form);
            var success = $('.alert-success', form);


            var displayConfirm = function() {
            	
            };

            var handleTitle = function(tab, navigation, index) {
                var total = navigation.find('li').length;
                var current = index + 1;
                // set wizard title
                $('.step-title', $('#form_wizard')).text('(' + (index + 1) + '/' + total+')');
                // set done steps
                jQuery('li', $('#form_wizard')).removeClass("done");
                var li_list = navigation.find('li');
                for (var i = 0; i < index; i++) {
                    jQuery(li_list[i]).addClass("done");
                }

                if (current == 1) {
                    $('#form_wizard').find('.button-previous').hide();
                } else {
                    $('#form_wizard').find('.button-previous').show();
                }

                if (current >= total) {
                    $('#form_wizard').find('.button-next').hide();
                    $('#form_wizard').find('.button-submit').show();
                    displayConfirm();
                } else {
                    $('#form_wizard').find('.button-next').show();
                    $('#form_wizard').find('.button-submit').hide();
                }
                Metronic.scrollTo($('.page-title'));
            }

            // default form wizard
            $('#form_wizard').bootstrapWizard({
                'nextSelector': '.button-next',
                'previousSelector': '.button-previous',
                onTabClick: function (tab, navigation, index, clickedIndex) {
                    return false;
                    /*
                    success.hide();
                    error.hide();
                    if (form.valid() == false) {
                        return false;
                    }
                    handleTitle(tab, navigation, clickedIndex);
                    */
                },
                onNext: function (tab, navigation, index) {
                    success.hide();
                    error.hide();

                    if (form.valid() == false) {
                        return false;
                    }

                    handleTitle(tab, navigation, index);
                },
                onPrevious: function (tab, navigation, index) {
                    success.hide();
                    error.hide();

                    handleTitle(tab, navigation, index);
                },
                onTabShow: function (tab, navigation, index) {
                    var total = navigation.find('li').length;
                    var current = index + 1;
                    var $percent = (current / total) * 100;
                    $('#form_wizard').find('.progress-bar').css({
                        width: $percent + '%'
                    });
                }
            });

            $('#form_wizard').find('.button-previous').hide();
            $('#form_wizard .button-submit').click(function () {
            	
            	if(!config.validateForm($("#submit_form").find("#tab1"))) {
            		$("body").message({
						type: 'warning',
						content: '报文信息未填写完整'
					});
            		return;
            	}
            	if(!config.validateForm($("#submit_form").find("#tab2"))) {
            		$("body").message({
						type: 'warning',
						content: '船舶信息未填写完整'
					});
            		return;
            	}
            	
            	if(config.validateForm($("#submit_form").find("#tab3"))) {
            		var messagehead = new Object();
            		$("#submit_form").find("#tab1").find("input,select").each(function() {
            			if($(this).attr("name") != undefined) {
            				messagehead[$(this).attr("name")] = $(this).val();
            			}
            		});
            		
            		var ship = new Object();
            		$("#submit_form").find("#tab2").find("input,select").each(function() {
            			if($(this).attr("name") != undefined) {
            				ship[$(this).attr("name")] = $(this).val();
            			}
            		});
            		ship['catogary'] = catogary;
                	var discharges = $('[data-role="cargoGrid"]').getGrid().getAllItems();
                	config.load();
					$.ajax({
			    		url: config.getDomain()+"/esb/harborShip/create",
			    		data: "harborShip="+JSON.stringify({
			    			'harborShip' : ship,
			    			'messageHead' : messagehead,
			    			'discharges' : discharges
			    		}),
			    		type: "POST",
			    		success: function(data){
			    			config.unload();
			    			if(data.code == "0000"){
			    				window.location.href = "#port";
			    			}
			    			$("body").message({
								type: data.code == "0000" ? 'success' : 'error',
								content: data.msg
							});
			    		},
			    		fail : function(data) {
			    			config.unload();
			    			$("body").message({
								type: 'error',
								content: data.msg
							});
			    		}
			    	});
				}
            	
            }).hide();
        }

    };

}();