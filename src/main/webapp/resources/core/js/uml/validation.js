(document).ready(function() {
	$('#submit_import').click(function(e) {

		alert("submit import");
		var isValid = true;
		$('#import_text').each(function() {
			if ($.trim($(this).val()) == '') {
				isValid = false;
				$(this).css({
					"border" : "1px solid red",
					"background" : "#FFCECE"
				});
			} else {
				$(this).css({
					"border" : "",
					"background" : ""
				});
			}
		});
		if (isValid == false)
			e.preventDefault();
	});
});