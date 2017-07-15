function validateSeminarAdd() {
	
	var res = confirm("Do you want to add one seminar.");
		
	if (res != true){
		return false;
	}
}


function validateSeminarDelete() {
	
	var res = confirm("Do you want to delete last seminar.");
		
	if (res != true){
		return false;
	}
}