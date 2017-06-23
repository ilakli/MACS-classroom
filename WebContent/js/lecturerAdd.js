$(document).ready(function() { 
	
	$(".lecturerAddButton").on('click', function() {
		alert("Ya Man");
		
		
		var str = $(this).parent().html();
		
		
		
		str = str.substr(str.indexOf("<"));
		
		str = str.substr(0,str.indexOf("<inp"));
		
		
		alert("Yy Man");

		str = str.replaceAll('<span>', '');

		str = str.replaceAll('</span>', ' ');

		
		
		var servlet = $(this).next().val();
		
		
		alert("Yo Man");
		
		$(this).parent().find("span").remove();
		
		alert("Le Man");
		alert(str);
		alert(servlet);
		
		
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