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
<title>Login/ Create Account</title>

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
<script language="JavaScript">
	function getFolder() {
		alert("hai");
		return showModalDialog("work", "",
				"width:400px;height:400px;resizeable:yes;");
	}
</SCRIPT>
<!-- js -->
<script src="${uml_js}/jquery-1.9.1.min.js"></script>
<script src="${uml_js}/modernizr.js"></script>
<script src="${uml_js}/tabs.js"></script>


</head>



<body>
	<spring:url value="/signUp" var="signUp" />
	<spring:url value="/login" var="login" />
	<div id="wrapper">

		<div id="top-bar">
			<!--  <div class="container clearfix">
        <span class="all-labs"><a href="http://www.callmenick.com/labs">&larr; all labs</a></span>
        <span class="back-to-tutorial"><a href="http://www.callmenick.com/labs/simple-tabbed-content-area-with-css-and-jquery">back to the tutorial</a></span>
    </div> -->
		</div>
		<!-- /#top-bar -->

		<header>
			<div id="title" class="container">
				<!--   <h1>Tabbed Content with CSS and jQuery</h1>
        <h2>A neat and simple tabbed content area with CSS and jQuery</h2> -->
			</div>
		</header>
		<!-- /header -->


		<div id="main">
			<div class="container">
				<ul id="tabs">
					<li class="active">Log in</li>
					<li>Sign Up</li>

				</ul>
				<ul id="tab">
					<li class="active"><form:form method="post"
							modelAttribute="user" action="${login}">
							<input type="hidden" value=${user.page } name="page">
							<table>
								<tr>

									<td align="left"><input type=text name="loginMail"
										id="loginMail" placeholder="Mail"></td>
								</tr>

								<tr>

									<td align="left">OR</td>
								</tr>
								<tr>

									<td align="left"><input type=text name="loginMobile"
										id="loginMobile" placeholder="Mobile No"></td>
								</tr>
								<tr>
									<td align="left"><input type=password name="loginPassword"
										id="loginPassword" placeholder="Password"></td>
								</tr>


								<tr>
									<td><input type="submit" value="Login" /></td>
								</tr>

								<tr>
									<td align="left"><a href='/UMLEngineering/changePassword'
										id="changePassword"><span>Update Password</span></a></td>
								</tr>
								
								<tr>
									<td align="left"><a href='/UMLEngineering/newPassword'
										id="newPassword"><span>New Password</span></a></td>
								</tr>
							</table>
						</form:form></li>
					<li><form:form method="post" modelAttribute="user"
							action="${signUp}">
							<input type="hidden" value=${user.page } name="page">
							<table>
								<tr>

									<td align="left"><input type=text name="name" id="name"
										placeholder="Name"></td>
								</tr>
								<tr>

									<td align="left"><input type=password name="password"
										id="password" placeholder="Password"></td>
								</tr>
								<tr>

									<td align="left"><input type=text name="mobile"
										id="mobile" placeholder="Mobile No"></td>
								</tr>
								<tr>

									<td align="left"><input type=text name="email" id="email"
										placeholder="Email"></td>
								</tr>

								<tr>
									<td align="left"><input type="text" name="workspace"
										id="workspace" placeholder="Workspace"> <input
										type="button" value="Browse"
										onclick="this.form.workspace.value=getFolder()"></td>
								</tr>
								<tr>
									<td><input type="submit" value="Sign Up" /></td>
								</tr>

							</table>
						</form:form>

						<div class="resultMask"></div>
						<div id="result_content">
							<div id="result_text"></div>
							<button id="ok_result">OK</button>
						</div></li>
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