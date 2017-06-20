function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();

	var firstName = profile.getGivenName();
	var lastName = profile.getFamilyName();
	var img = profile.getImageUrl();
	var email = profile.getEmail();
	
	
	$.ajax({
		url : "AddPersonServlet",
		type: 'POST',
		data: {
			firstName: firstName,
			lastName: lastName,
			email: email
		},
		success : function(result) {
			
		}
	});
};