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
			var clientHeight = screen.height;
			var top = clientHeight* 0.5 - height;
//			var top = self.container.offset().top + self.container.outerHeight(true) * 0.5 - height;
			
			self.$element.css({
				'position' : 'fixed',
				'left' : left + 'px',
				'top' : top + 'px'
			});
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
			var data = $this.data('skycloud.message');
			var options = $.extend({}, Message.DEFAULTS, $this.data(), typeof option == 'object' && option);
			$this.data('skycloud.message', ( data = new Message(this, options)));
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