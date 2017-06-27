String.prototype.replaceAll = function(search, replacement) {
	var target = this;
	return target.replace(new RegExp(search, 'g'), replacement);
};

$(document).ready(function() { 
	
	$(".lecturerAddButton").on('click', function() {
		
		
		
		var str = $(this).parent().html();
		
		
		
		str = str.substr(str.indexOf("<"));
		
		str = str.substr(0,str.indexOf("<inp"));
		
		
		

		str = str.replaceAll('<span>', '');

		str = str.replaceAll('</span>', ' ');

		
		
		var servlet = $(this).next().val();
		
		
		
		
		$(this).parent().find("span").remove();
		
		
		
		
		$.ajax({
			url : servlet,
			type: 'POST',
			data: {
				email: str
			},
			success : function(result) {
				alert("Done");
			}
		});

	});
});