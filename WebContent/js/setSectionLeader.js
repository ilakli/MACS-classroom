

String.prototype.replaceAll = function(search, replacement) {
	var target = this;
	return target.replace(new RegExp(search, 'g'), replacement);
};

$(document).ready(function() { 

	$(".sectionLeaderSetButton").on('click', function() {
		
		alert("clicked");
		
		
		var str = $(this).parent().html();
		
		
		
		str = str.substr(str.indexOf("<"));
		
		str = str.substr(0,str.indexOf("<inp"));
		
		
		alert("clicked1");

		str = str.replaceAll('<span>', '');

		str = str.replaceAll('</span>', ' ');

		alert("Good " + str);
		

		
		var sectionN = $(this).next().val();
		alert("Good " + sectionN);
		
		var servlet = $(this).next().next().val();
		alert("Good " + servlet);
		
		var classroomId = $("#classroom-id").val();
	
		alert("Good " + classroomId);
		
		$(this).parent().find("span").remove();
		
		
		alert(sectionN + classroomId + str);
		
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