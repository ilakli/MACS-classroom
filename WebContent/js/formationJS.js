function main() {
  $(".group-persons").hide();
  $(".grevent-items").hide();
  
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