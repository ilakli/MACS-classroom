$(document).ready(function() {
	
	$("#ADD_COMMENT_BUTTON").on("click", function (){
		
		
		var commentText = $(this).prev().find('#COMMENT_TEXT').val();
		var personId = $(this).find('#PERSON_ID').val();
		var studentAssignmentId = $(this).find('#STUDENT_ASSIGNMENT_ID').val();
		
		alert(studentAssignmentId);
		
		
	});
});