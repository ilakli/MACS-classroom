$(document).ready(function() {

	$(".ui .button").click(function (){
		
		var _this = this;
		
		setTimeout( function() {
			$(_this).prop('disabled',true);
		}, 10);
	});
	
});