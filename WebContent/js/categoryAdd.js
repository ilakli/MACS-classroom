String.prototype.replaceAll = function(search, replacement) {
	var target = this;
	return target.replace(new RegExp(search, 'g'), replacement);
};

$(document).ready(function() { 

	$(".categoryAddButton").on('click', function() {
	
		
		
		var str = $(this).parent().parent().html();
		
		str = str.substr(str.indexOf("<span>"));
		
		str = str.substr(0,str.indexOf("<inp"));
		
		
		

		str = str.replaceAll('<span>', '');

		str = str.replaceAll('</span>', '#');

	
		
		var servlet = $(this).next().val();
		
		var classroomId = $("#classroomID").val();
	
		
		
		
		$(this).parent().find("span").remove();
		
		
		alert("STR IS: " + str);
		
		$.ajax({
			url : servlet,
			type: 'POST',
			data: {
				categories: str,
				classroomID: classroomId
			},
			success : function(result) {
				alert("Done");
			}
		});

	});
});