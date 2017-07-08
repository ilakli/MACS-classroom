$(document).ready(function() { 
	$(".emails input").on({
		focusout : function() {
			var txt = this.value.replace(/[^a-z0-9\+\-\.\#\@]/ig, '');
																		
			if (txt)
				$("<span/>", {
					text : txt.toLowerCase(),
					insertBefore : this
				});
			this.value = "";
		},
		keyup : function(ev) {
			if (/(188|13|32)/.test(ev.which))
				$(this).focusout();
		}
	});

	$('.emails').on('click', 'span', function() {
		$(this).remove();
	});

});


/*
<div class="emails">
  								<input type="text" value="" placeholder="Add Email" />
  								
							</div>
*/