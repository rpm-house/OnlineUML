var urlPrefix = "http://localhost:8080/UMLEngineering/";
(function() {
	$(document).ready(
			function() {

				/*
				 * $("#submit_email").click(function() { $(".emailMask").hide();
				 * $("#email_content").fadeOut(); $(".workspaceMask").show();
				 * $("#workspace_content").fadeIn(); });
				 */

				$("#cancel_workspace").click(function() {
					alert("cancel");
					$(".workspaceMask").hide();
					$("#workspace_content").fadeOut();
				});
				$("#createAccount").click(
						function() {

							var name = $("#name").val();
							var password = $("#password").val();
							var mobile = $("#mobile").val();
							var email = $("#email").val();
							var workspace = $("#workspace").val();

							var url = urlPrefix + "saveWorkspace";
							var val = name + "#" + password + "#" + mobile
									+ "#" + email + "#" + workspace;
							$
									.ajax({
										url : url,
										contentType : "application/json",
										type : 'post',
										cache : false,
										data : val,
										success : function(data, status) {
											alert("status: " + status,
													+"data: " + data);
											$(".workspaceMask").hide();
											$("#workspace_content").fadeOut();
											$(".resultMask").show();
											$("#result_content").fadeIn();
											$("#result_text").html(data);
										}
									});
						});

				$("#ok_result").click(function() {
					$(".resultMask").hide();
					$("#result_content").fadeOut();
				});

			});
})();
