

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}


$(document).ready(function() { 
	
	
	$(".ui .button").click(function (){
		
		var _this = this;
		
		setTimeout( function() {
			$(_this).prop('disabled',true);
		}, 10);
		
	});
	
	
	$(".emails input").on('keyup', function(ev){
		if (/(188|13|32)/.test(ev.which))
			$(this).focusout();
	});
	
	$(".emails input").on('paste', function(){
		$(this).focusout();
	});
	
	
	
	$(".emails input").on({
		focusout : function() {
			var _this = this;
			
			setTimeout( function() {
				var mails = _this.value.split(/[ ,\n]+/);
				console.log(mails);
				_this.value = "";
				
				for (i = 0; i < mails.length; i++) { 
					
					if (validateEmail(mails[i])){
						$("<span/>", {
							text : mails[i].toLowerCase(),
							insertBefore : _this
						});
					}
				}
				
	        }, 100);
			
		}
	});
	
	$('.emails').on('click', 'span', function() {
		$(this).remove();
	});
	

});