$(document).ready(function() {
	
	$("#COMMENT_MENU_BAR").click(function (){

		var commentMenuBar = document.getElementById('COMMENT_MENU_BAR');
		var staffCommentMenuBar = document.getElementById('STAFF_COMMENT_MENU_BAR');
		var commentsBox = document.getElementById('ALL_COMMENTS');
		var commentAddingForm = document.getElementById('COMMENT_ADDING_FORM');
		var staffCommentsBox = document.getElementById('ALL_STAFF_COMMENTS');
		var staffCommentAddingForm = document.getElementById('STAFF_COMMENT_ADDING_FORM');
		
		
		
		commentMenuBar.className = 'item active';
		staffCommentMenuBar.className = 'item';
		
		commentsBox.style.display = 'block';
		commentAddingForm.style.display = 'block';
		staffCommentsBox.style.display = 'none';
		staffCommentAddingForm.style.display = 'none';
	});
	
	$("#STAFF_COMMENT_MENU_BAR").click(function (){

		var commentMenuBar = document.getElementById('COMMENT_MENU_BAR');
		var staffCommentMenuBar = document.getElementById('STAFF_COMMENT_MENU_BAR');
		var commentsBox = document.getElementById('ALL_COMMENTS');
		var commentAddingForm = document.getElementById('COMMENT_ADDING_FORM');
		var staffCommentsBox = document.getElementById('ALL_STAFF_COMMENTS');
		var staffCommentAddingForm = document.getElementById('STAFF_COMMENT_ADDING_FORM');
		
		commentMenuBar.className = 'item';
		staffCommentMenuBar.className = 'item active';
		
		commentsBox.style.display = 'none';
		commentAddingForm.style.display = 'none';
		staffCommentsBox.style.display = 'block';
		staffCommentAddingForm.style.display = 'block';
	});
	
	$(".ui .button").click(function (){
		
		var _this = this;
		
		setTimeout( function() {
			$(_this).prop('disabled',true);
		}, 10);
		
	});
	
});