String.prototype.replaceAll = function(search, replacement) {
	var target = this;
	return target.replace(new RegExp(search, 'g'), replacement);
};

$(document).ready(function() { 

	$(".personAddButton").on('click', function() {

		var str = $(this).parent().html();
		
		
		
		str = str.substr(str.indexOf("<"));
		
		str = str.substr(0,str.indexOf("<inp"));
		
		
		

		str = str.replaceAll('<span>', '');

		str = str.replaceAll('</span>', ' ');

		
		
		var servlet = $(this).next().val();
		
		var classId = $(this).next().next().val();
		
		$(this).parent().find("span").remove();
		
		var ul = $(this).parent().parent().parent();
		
		
		$.ajax({
			url : servlet,
			type: 'POST',
			data: {
				email: str,
				classroomID: classId
			},
			success : function(result) {
				if(ul.children().last().text() === "Empty"){
					ul.children().last().remove();
				}
				
				while(true){
					var index = str.indexOf(" ");
					if(index == -1){
						break;
					}
					
					var currentString = str.substr(0,index);

					ul.append("<li>" + currentString + "</li>");
					
					str = str.substr(index+1);
						
					if(str === " "){
						break;
					}
					
				}
			}
		});

	});

	$(".emails input").on({
		focusout : function() {
			var txt = this.value.replace(/[^a-z0-9\+\-\.\#\@]/ig, '');
																		
			if (txt)
				$("<span/>", {
					text : txt.toLowerCase(),
					insertBefore : this
				});
			this.value = "";
		},
		keyup : function(ev) {
			if (/(188|13|32)/.test(ev.which))
				$(this).focusout();
		}
	});

	$('.emails').on('click', 'span', function() {
		$(this).remove();
	});

});