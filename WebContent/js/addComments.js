$(document).ready(function() {
	
	$(".commentButton").click(function (){
		var postId = $(this).next().val();
		var personId = $(this).next().next().val();
		var personImgURL = $(this).next().next().next().val();
		var personName = $(this).next().next().next().next().val();
		var personSurname = $(this).next().next().next().next().next().val();
		var commentText = $(this).prev().val();
		
		var _this = $(this);
		
		$.ajax({
			url : "CommentServlet",
			type: 'POST',
			data: {
				postId: postId,
				personId: personId,
				commentText: commentText
			},
			success : function(result) {
				
				var toAdd = "<div class = \"comment\">" +
									"<a class = \"avatar\">" + 
									"<img src = \"" + personImgURL + "\">" +
								"</a>" +
								"<div class = \"content\">" + 
									"<a class = \"author\">" + personName + " " + personSurname + "</a>" +
									"<div class = \"metadata\">" +
										"<span class = \"date\">" + result + "</span>" +
									"</div>" +
									"<div class = \"text\">" +
										"<pre>" + commentText + "</pre>" +
									"</div>" +
								"</div>" + 
							"</div>";
				
				$("#" + postId + "commentAdding").before(toAdd);
	
			}
		});
		
		$(this).prev().val('');
		
		
		
	});
});