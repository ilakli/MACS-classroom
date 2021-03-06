String.prototype.replaceAll = function(search, replacement) {
	var target = this;
	return target.replace(new RegExp(search, 'g'), replacement);
};

$(document).ready(function() {
	
	$(".lecturerAddButton").on('click', function() {
		
		var str = $("#lecturerAddServlet").parent().html();

		str = str.substr(str.indexOf("<"));

		str = str.substr(0, str.indexOf("<inp"));

		str = str.replaceAll('<span>', '');

		str = str.replaceAll('</span>', ' ');

		var servlet = $("#lecturerAddServlet").val();
		
		var classId = $("#classroomId").val();
	
		$(this).parent().find("span").remove();

		$("#lecturerAddServlet").parent().find("span").remove();
		
		$.ajax({
			url : servlet,
			type : 'POST',
			data : {
				email : str,
				classroomID : classId
			},
			success : function(result) {
				location.reload();
			}
		});

	});
});