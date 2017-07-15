function validateSectionAdd() {
	
	var res = confirm("Do you want to add one section.");
		
	if (res != true){
		return false;
	}
}


function validateSectionDelete() {
	
	var res = confirm("Do you want to delete last section.");
		
	if (res != true){
		return false;
	}
}