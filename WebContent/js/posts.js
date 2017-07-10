$(document).ready(function() {
	
	$("#ADD_POST_BUTTON").click(function (){
		
		var postText = $(this).prev().find('#POST_TEXT').val();
		var classroomId = $(this).find('#CLASSROOM_ID').val();
		var personId =  $(this).find('#PERSON_ID').val();
		/*
		alert(postText);
		alert(classroomId);
		alert(personId);
		*/
		
		$.ajax({
			url : "PostServlet",
			type: 'POST',
			data: {
				classroomID: classroomId,
				postText: postText,
				currentPerson: personId
			},
			success : function(result) {
				location.reload();
			}
		});
	
	});

})