$(document).ready(function() { 

	$(".studentAddSeminarButton").on('click', function() {
	
		
		
		var str = $(this).parent().html();
		
		
		
		str = str.substr(str.indexOf("<"));
		
		str = str.substr(0,str.indexOf("<inp"));
		
		
		

		str = str.replaceAll('<span>', '');

		str = str.replaceAll('</span>', ' ');

	
		
		var servlet = $(this).next().next().val();
		
		var seminarN = $(this).next().val();
		
		var classroomId = $("#classroom-id").val();
	
		
		
		
		$(this).parent().find("span").remove();
		
		
		
		
		$.ajax({
			url : servlet,
			type: 'POST',
			data: {
				studentEmail: str,
				seminarN: seminarN,
				classroomID: classroomId
			},
			success : function(result) {
				alert("Done");
			}
		});

	});
});