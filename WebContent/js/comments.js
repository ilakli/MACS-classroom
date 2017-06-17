$(document).ready(function() {
	
	$(".comments-form").submit(function(e) {
		e.preventDefault();
		
		var postId = $(this).find('> input').val();
		var commentText = $(this).find('> textarea').val();
	
		
		
		var ul = $(this).prev();
		
		$.ajax({
			url : "CommentServlet",
			type: 'POST',
			data: {
				postId: postId,
				personId: 1,
				commentText: commentText
			},
			success : function(result) {
				
				var commentBody = "<div class=\"w3-card-4\"> <div class=\"w3-container\"> <img src=\"img_avatar3.png\" alt=\"Avatar\" class=\"w3-left w3-circle\" style=\"width: 10%;\"> <h4>"
					+ "George Cercvadze" + "</h4> <p style=\"padding-left: 11%;\">" + commentText
					+ "</p> </div> </div>";
				
				var commentHtml = " <li class=\"list-group-item\">" + commentBody + "</li>";
				
				ul.append(commentHtml);
			}
		});
		$(this).find('> textarea').val("");
	});
});