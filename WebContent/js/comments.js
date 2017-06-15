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
				var commentHtml = " <li class=\"list-group-item\">" + commentText + "    Author: 1 </li>";
				ul.append(commentHtml);
			}
		});
	});
});