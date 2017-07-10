String.prototype.replaceAll = function(search, replacement) {
	var target = this;
	return target.replace(new RegExp(search, 'g'), replacement);
};

$(document).ready(function() { 
	
	$(".globalLecturerAddButton").on('click', function() {
		
		var str = $("#globalLecturerAddServlet").parent().html();
		
		str = str.substr(str.indexOf("<"));
		
		str = str.substr(0,str.indexOf("<inp"));

		str = str.replaceAll('<span>', '');

		str = str.replaceAll('</span>', ' ');
	
		var servlet = $("#globalLecturerAddServlet").val();
		
		$(this).parent().find("span").remove();
		

		$("#globalLecturerAddServlet").parent().find("span").remove();
		
		
		$.ajax({
			url : servlet,
			type: 'POST',
			data: {
				email: str
			},
			success : function(result) {
				location.reload();
			}
		});

	});
});