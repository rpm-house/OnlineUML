<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" class="no-js">

<head>

<!-- title and meta -->
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<meta name="description"
	content="A neat and simple tabbed content area with CSS and jQuery" />
<title>Change Password</title>

<spring:url value="/resources/core/css/vendor" var="vendor_css" />
<spring:url value="/resources/core/css/uml" var="uml_css" />
<spring:url value="/resources/core/js/vendor" var="vendor_js" />
<spring:url value="/resources/core/js/uml" var="uml_js" />


<!-- css -->
<link
	href='http://fonts.googleapis.com/css?family=Ubuntu:300,400,700,400italic'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Oswald:400,300,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="${uml_css}/base.css" />
<link rel="stylesheet" href="${uml_css}/style.css" />
<link type="text/css" rel="stylesheet" href="${uml_css}/home.css">
<script src="${vendor_js}/jquery.min.js"></script>
<script src="${uml_js}/home.js" type="text/javascript"></script>

<!-- js -->
<script src="${uml_js}/jquery-1.9.1.min.js"></script>
<script src="${uml_js}/modernizr.js"></script>
<script src="${uml_js}/tabs.js"></script>




</head>

<body>

	<spring:url value="/updatePassword" var="updatePassword" />
	<div id="wrapper">

		<div id="top-bar"></div>

		<header>
			<div id="title" class="container"></div>
		</header>
		<!-- /header -->


		<div id="main">
			<div class="container">
				<ul id="tabs">
					<li class="active">Change Password</li>
				</ul>
				<ul id="tab">
					<li class="active"><form:form method="post"
							modelAttribute="user" action="${updatePassword}">
							<input type="hidden" value=${user.page } name="page">
							<table>
								<tr>

									<td align="left"><input type=text name="recoveryMail"
										id="recoveryMail" placeholder="Mail"></td>
								</tr>
								<tr>
									<td align="left"><input type=password name="oldPassword"
										id="oldPassword" placeholder="Password"></td>
								</tr>
								<tr>
									<td align="left"><input type=password name="newPassword"
										id="newPassword" placeholder="New Password"></td>
								</tr>
								<tr>
									<td><input type="submit" value="Update" /></td>
								</tr>

							</table>
						</form:form></li>
				</ul>
			</div>
		</div>
		<!-- #main -->


		<footer> </footer>
		<!-- /footer -->



	</div>
	<!-- /#wrapper -->

</body>
</html>