$(document).ready(function() {
	
	$("#ADD_COMMENT_BUTTON").click(function (){
		
		
		var commentText = $(this).prev().find('#COMMENT_TEXT').val();
		var personId = $(this).find('#PERSON_ID').val();
		var studentAssignmentId = $(this).find('#STUDENT_ASSIGNMENT_ID').val();
		var personImgURL =  $(this).find('#PERSON_IMG_URL').val();
		var personName =  $(this).find('#PERSON_NAME').val();
		
		
		/*
		alert(personImgURL);
		alert(commentText);
		alert(personId);
		alert(studentAssignmentId);
		*/
		
		$.ajax({
			url : "AssignmentCommentServlet",
			type: 'POST',
			data: {
				studentAssignmentId: studentAssignmentId,
				personId: personId,
				commentText: commentText
			},
			success : function(result) {
				
				var toAdd = "<div class=\"comment\">" +
				    "<a class=\"avatar\">" +
				      "<img src=\"" + personImgURL + "\">" +
				    "</a>" + 
				      "<div class=\"content\">" +
				      "<a class=\"author\">" + personName + "</a>" +
				      "<div class=\"metadata\">" +
				        "<span class=\"date\">" + result + "</span>" +
				      "</div>" +
				      "<div class=\"text\">" +
				      commentText +
				      "</div>" +
				      
				    "</div>" +
				  "</div>";
				
				
				var commentsBox = document.getElementById('ALL_COMMENTS');
				commentsBox.innerHTML += toAdd;
				
			}
		});
		
		var commentTextarea = document.getElementById('COMMENT_TEXT');
		commentTextarea.value = '';
	});
});