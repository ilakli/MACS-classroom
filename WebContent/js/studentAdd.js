String.prototype.replaceAll = function(search, replacement) {
	var target = this;
	return target.replace(new RegExp(search, 'g'), replacement);
};

$(document).ready(function() {
	
	$(".personAddButton").on('click', function() {
		
		
		
		var str = $("#studentServlet").parent().html();
		
		str = str.substr(str.indexOf("<"));

		str = str.substr(0, str.indexOf("<inp"));

		str = str.replaceAll('<span>', '');

		str = str.replaceAll('</span>', ' ');
		
		
		
		var servlet = $("#studentServlet").val();
		
		var classId = $("#classroomId").val();
		
		$("#studentServlet").parent().find("span").remove();

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