function validateFormAdd() {
	
	var res = confirm("Do you want to add one section.");
		
	if (res != true){
		return false;
	}
}


function validateFormDelete() {
	
	var res = confirm("Do you want to delete last section.");
		
	if (res != true){
		return false;
	}
}