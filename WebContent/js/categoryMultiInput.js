$(document).ready(function() { 
	
	$(".categories input").on({
		focusout : function() {
			var txt = this.value;
																		
			if (txt)
				$("<span/>", {
					text : txt,
					insertBefore : this
				});
			this.value = "";
		},
		keyup : function(ev) {
			if (/(13)/.test(ev.which))
				$(this).focusout();
		}
	});

	$('.categories').on('click', 'span', function() {
		$(this).remove();
	});

});