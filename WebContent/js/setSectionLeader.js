

String.prototype.replaceAll = function(search, replacement) {
	var target = this;
	return target.replace(new RegExp(search, 'g'), replacement);
};

$(document).ready(function() { 

	$(".sectionLeaderSetButton").on('click', function() {
		
		
		
		
		var str = $(this).parent().html();
		
		
		
		str = str.substr(str.indexOf("<"));
		
		str = str.substr(0,str.indexOf("<inp"));
		
		
	

		str = str.replaceAll('<span>', '');

		str = str.replaceAll('</span>', ' ');


		
		var sectionN = $(this).next().val();
		
		
		var servlet = $(this).next().next().val();
	
		
		var classroomId = $("#classroom-id").val();
	
	
		
		$(this).parent().find("span").remove();
		
		
		
		
		$.ajax({
			url : servlet,
			type: 'POST',
			data: {
				sectionLeaderEmail: str,
				sectionN: sectionN,
				classroomID: classroomId
			},
			success : function(result) {
				alert("Done");
			}
		});

	});
});