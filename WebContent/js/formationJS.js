function main() {
  $(".group-persons").hide();
  
  $(".display-persons-button").on("click",
	function (){
    $(this).next().slideToggle(400);
    //$(this).toggleClass('active');
  });
  
  
}

$(document).ready(main);