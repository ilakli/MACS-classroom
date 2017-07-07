function onSignIn(googleUser) {
	alert("FUUUUUUUUUUUUUUUUUUUCK");
	var profile = googleUser.getBasicProfile();

	var firstName = profile.getGivenName();
	var lastName = profile.getFamilyName();
	var img = profile.getImageUrl();
	var email = profile.getEmail();
	
	alert(lastName);
	alert(img);
	
	$.ajax({
		url : "LoginServlet",
		type: 'POST',
		data: {
			firstName: firstName,
			lastName: lastName,
			email: email,
			image: img
		},
		success : function(result) {
			window.location = "index.jsp"
		}
	});
};