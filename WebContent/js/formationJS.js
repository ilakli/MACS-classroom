function main() {
  $(".group-quick-edit").hide();
  $(".group-persons").hide();
  $(".grevent-items").hide();
  
  $(".display-quick-edit-button").on("click",
	function (){
	$(this).next().slideToggle(400);
	//$(this).toggleClass('active');
  });
  
  $(".display-persons-button").on("click",
	function (){
    $(this).next().slideToggle(400);
    //$(this).toggleClass('active');
  });
  
  $(".display-grevent-button").on("click",
	function (){
	$(this).next().slideToggle(400);
    //$(this).toggleClass('active');
  });
  
  
}

$(document).ready(main);