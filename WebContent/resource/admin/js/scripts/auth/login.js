var domain = "";
var Login = function() {

	var handleLogin = function() {
		$('.login-form').validate({
			errorElement : 'span', //default input error message container
			errorClass : 'help-block', // default input error message class
			focusInvalid : false, // do not focus the last invalid input
			rules : {
				username : {
					required : true
				},
				password : {
					required : true
				},
				remember : {
					required : false
				}
			},

			messages : {
				username : {
					required : "账号不能为空."
				},
				password : {
					required : "密码不能为空."
				}
			},

			invalidHandler : function(event, validator) { //display error alert on form submit   
				$('.alert-danger', $('.login-form')).show();
			},

			highlight : function(element) { // hightlight error inputs
				$(element).closest('.form-group').addClass('has-error'); // set error class to the control group
			},

			success : function(label) {
				label.closest('.form-group').removeClass('has-error');
				label.remove();
			},

			errorPlacement : function(error, element) {
				error.insertAfter(element.closest('.input-icon'));
			},

			submitHandler : function(form) {
				
			}
		});

		$('.login-form input').keypress(function(e) {
			if (e.which == 13) {
				if ($('.login-form').validate().form()) {
					login();
				}
				return false;
			}
		});

		$(".login-button").click(function() {
			if ($('.login-form').validate().form()) {
				login();
			}
		});
	}
	
	var login = function() {
		//登录
		$(".login-button").attr('disabled', 'disabled').html('正在登录...');
		$.ajax({
    		url: domain+"/login",
    		data: $('.login-form').serialize(),
    		type: "POST",
    		success: function(data){
    			if(data.code == "0000"){
    				$('.login-form').message({
    					type: 'success',
    					content:  '登录成功！'
    				});
    				if($("input[name='rememberMe']").attr("checked"))  {
    					$.cookie('account', $("input[name='account']").val());
        				$.cookie('password', $("input[name='password']").val());
        				$.cookie('remember', true);
    				}else {
    					$.cookie('account', "");
        				$.cookie('password', "");
        				$.cookie('remember', "0");
    				}
    				
//    				remote.jsjac.chat.login($("input[name='account']").val(),$("input[name='password']").val(),true);
    				window.location.href=domain;
    				
    			}else{
    				$(".login-button").removeAttr('disabled').html('登录');
    				$('.login-form').message({
    					type: 'error',
    					content: data.msg
    				});
    			}
    		}
    	});
	};

	var handleForgetPassword = function() {
		$('.forget-form').validate({
			errorElement : 'span', //default input error message container
			errorClass : 'help-block', // default input error message class
			focusInvalid : false, // do not focus the last invalid input
			ignore : "",
			rules : {
				email : {
					required : true,
					email : true
				}
			},

			messages : {
				email : {
					required : "Email is required."
				}
			},

			invalidHandler : function(event, validator) { //display error alert on form submit   

			},

			highlight : function(element) { // hightlight error inputs
				$(element).closest('.form-group').addClass('has-error'); // set error class to the control group
			},

			success : function(label) {
				label.closest('.form-group').removeClass('has-error');
				label.remove();
			},

			errorPlacement : function(error, element) {
				error.insertAfter(element.closest('.input-icon'));
			},

			submitHandler : function(form) {
				form.submit();
			}
		});

		$('.forget-form input').keypress(function(e) {
			if (e.which == 13) {
				if ($('.forget-form').validate().form()) {
					$('.forget-form').submit();
				}
				return false;
			}
		});

		jQuery('#forget-password').click(function() {
			jQuery('.login-form').hide();
			jQuery('.forget-form').show();
		});

		jQuery('.back-btn').click(function() {
			jQuery('.login-form').show();
			jQuery('.forget-form').hide();
		});
	};

	return {
		//main function to initiate the module
		init : function() {

			handleLogin();
			handleForgetPassword();
			
			if($.cookie('remember') && 0 != $.cookie('remember')) {
				$("input[name='account']").val($.cookie('account'));
				$("input[name='password']").val($.cookie('password'));
				$("input[name='rememberMe']").attr("checked",$.cookie('remember'));
			}
		}

	};

}();

/*
消息提示组件
*/ + function($) {"use strict";
	var Message = function(container, options) {
		this.container = $(container);
		this.$element = $(Message.DEFAULTS.TEMPLATE);
		this.options = options;
		this.init();
	};
	Message.DEFAULTS = {
		delay : 2000,
		type : 'info'
	};
	Message.prototype.init = function() {
		var self = this;
		$('.message').remove();
		this.content = this.$element.find('[data-toggle="content"]').html(this.options.content);
		switch(this.options.type){
			case 'success':
				this.content.before($('<div style="float:left;"><span class="glyphicon glyphicon-ok-sign" style="margin-right: 5px; font-size:18px;"/></div>'));
				this.$element.addClass('alert-success');
				break;
			case 'info':
				this.content.before($('<div style="float:left;"><span class="glyphicon glyphicon-info-sign" style="margin-right: 5px;font-size:18px;"/></div>'));
				this.$element.addClass('alert-info');
				break;
			case 'warning':
				this.content.before($('<div style="float:left;"><span class="glyphicon glyphicon-warning-sign" style="margin-right: 5px;font-size:18px;"/></div>'));
				this.$element.addClass('alert-warning');
				break;
			case 'error':
				this.content.before($('<div style="float:left;"><span class="glyphicon glyphicon-remove-sign" style="margin-right: 5px;font-size:18px; "/></div>'));
				this.$element.addClass('alert-danger');
				break;
		}
		this.$element.appendTo($('body')).fadeIn(400, function() {
			var width = self.$element.find('[data-toggle="content"]').outerWidth(true) * 0.5 + 20;
			var height = self.$element.find('[data-toggle="content"]').outerHeight(true) * 0.5 + 20;
			var left = self.container.offset().left + self.container.outerWidth(true) * 0.5 - width;
			var top = self.container.offset().top + self.container.outerHeight(true) * 0.5 - height;

			self.$element.css({
				'position' : 'fixed',
				'left' : left + 'px',
				'top' : top + 'px'
			})
		});
		setTimeout(function() {
 			self.$element.fadeOut(1000, function() {
 				$(this).remove();
 			});
		}, this.options.delay);
	};
	Message.DEFAULTS.TEMPLATE = '<div class="alert message" style="min-width: 120px;max-width: 300px; padding: 8px;text-align: left;z-index: 20000;">' + '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>' + '<span data-toggle="content" style="font-size:14px; word-wrap:break-word;"></span>&nbsp;&nbsp;</div>';
	var old = $.fn.message;
	$.fn.message = function(option) {
		return this.each(function() {
			var $this = $(this);
			var data = $this.data('koala.message');
			var options = $.extend({}, Message.DEFAULTS, $this.data(), typeof option == 'object' && option);
			$this.data('koala.message', ( data = new Message(this, options)));
			if ( typeof option == 'string') {
				data[option]();
			}
		});
	};
	$.fn.message.Constructor = Message;
	$.fn.message.noConflict = function() {
		$.fn.message = old;
		return this;
	};
}(window.jQuery)