<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*"%>
<%@ page import="org.infosys.vo.common.User"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<!-- title and meta -->
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<meta name="description"
	content="A neat and simple tabbed content area with CSS and jQuery" />

<title>UML Editor</title>
<c:url var="home" value="/" scope="request" />

<spring:url value="/resources/core/css/vendor" var="vendor_css" />
<spring:url value="/resources/core/css/uml" var="uml_css" />
<spring:url value="/resources/core/js/vendor" var="vendor_js" />
<spring:url value="/resources/core/js/uml" var="uml_js" />


<!-- CSS LIBRARY-->
<link
	href='http://fonts.googleapis.com/css?family=Ubuntu:300,400,700,400italic'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Oswald:400,300,700'
	rel='stylesheet' type='text/css'>

<link type="text/css" rel="stylesheet"
	href="${vendor_css}/joint.min.css">
<link type="text/css" rel="stylesheet" href="${uml_css}/umlEditor.css">
<link type="text/css" rel="stylesheet" href="${uml_css}/menuHome.css">
<link type="text/css" rel="stylesheet" href="${uml_css}/base.css">
<link type="text/css" rel="stylesheet" href="${uml_css}/style.css">



<!-- JQUERY JS LIBRARY-->
<script src="${vendor_js}/jquery.min.js"></script>
<script src="${uml_js}/jquery-1.9.1.min.js"></script>

<!-- JOINT JS LIBRARY-->
<script src="${vendor_js}/joint_uml.js" type="text/javascript"></script>
<script src="${vendor_js}/joint.shapes.uml.min.js"
	type="text/javascript"></script>
<script src="${vendor_js}/joint.uml.js" type="text/javascript"></script>
<script src="${vendor_js}/joint.format.svg.js" type="text/javascript"></script>

<!-- EXTERNAL JS LIBRARY-->
<script src="${uml_js}/FileSaver.js" type="text/javascript"></script>
<script src="${uml_js}/jszip.min.js" type="text/javascript"></script>
<script src="${uml_js}/umlEditor.js" type="text/javascript"></script>
<script src="${uml_js}/menu.js" type="text/javascript"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>

<!-- JS LIBRARY FOR HOME -->
<script src="${uml_js}/home.js" type="text/javascript"></script>
<script src="${uml_js}/modernizr.js"></script>
<script src="${uml_js}/tabs.js"></script>



</head>
<body>
	<div id='cssmenu'>
		<ul>
			<li><a href='/home' id="home"><span>Home</span></a></li>
			<li class='active has-sub'><a href='#'><span>File</span></a>
				<ul>
					<li class='has-sub'><a href='#'><span>New</span></a>
						<ul>
							<li><a href='/' id="new"><span>Class
										Diagram</span></a></li>
							<li class='last'><a href='/erd'><span>ER
										Diagram</span></a></li>
						</ul></li>
					<li><a href='#' id="import"><span>Open</span></a>
					<li class='has-sub'><a href='#'><span>Save As</span></a>
						<ul>
							<li><a href='#' id="save"><span>JSON</span></a></li>
							<li class='last'><a href='#' id="saveImage"><span>Image</span></a></li>
							<li class='last'><a href="javascript:void(0)"
								id="render-link"><span> In Google Drive</span></a></li>
						</ul></li>
					<li><a href='#' id="exportXMI"><span>Export XMI</span></a></li>
					<li><a href='#' id="importXMI"><span>Import XMI</span></a></li>

				</ul></li>
			<li class='has-sub'><a href='#'><span>Engineering</span></a>
				<ul>
					<li><a href='#' id="forward"><span>Forward</span></a></li>
					<li class='last'><a href='#' id="reverse"><span>Reverse</span></a></li>
				</ul></li>

			<li><a href='/work' id="work"><span>work</span></a></li>
			<li><a href='/gDrive'><span>gDrive</span></a></li>
			<li class='last'><a href='/index/index/'><span>index</span></a></li>
			<li><a href='/index/home' id="index"><span>Log
						In/Sign Up</span></a></li>
			<li class='has-sub'><a href='#'><span>Settings</span></a>
				<ul>
					<li><a href='/logOut' id="logOut"><span>Sign
								Out</span></a></li>
					<li class='last'><a href='/changePassword'
						id="changePassword"><span>Change Password</span></a></li>
				</ul></li>

			<!-- <li><a onclick="testOpen();" href="javascript:void(0);">New
					Test</a></li> -->
		</ul>
		<div id="user">
			<%
				if (null != request.getSession().getAttribute("user")) {
					User user = (User) request.getSession().getAttribute("user");
					out.println("Hi " + user.getName() + "!");
			%>
			<input type="hidden" name="isUserLogged" id="isUserLogged"
				value="true">
			<%
				} else {
			%>
			<input type="hidden" name="isUserLogged" id="isUserLogged"
				value="false">
			<%
				}
			%>
		</div>
	</div>

</body>
</html>