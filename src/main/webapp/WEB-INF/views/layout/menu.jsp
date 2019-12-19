<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*"%>
<%@ page import="org.infosys.vo.common.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>UML Editor</title>
<c:url var="home" value="/" scope="request" />

<spring:url value="/resources/core/css/vendor" var="vendor_css" />
<spring:url value="/resources/core/css/uml" var="uml_css" />
<spring:url value="/resources/core/js/vendor" var="vendor_js" />
<spring:url value="/resources/core/js/uml" var="uml_js" />


<!-- CSS LIBRARY-->

<link type="text/css" rel="stylesheet" href="${uml_css}/menu.css">
<link type="text/css" rel="stylesheet" href="${uml_css}/base.css">
<link type="text/css" rel="stylesheet" href="${uml_css}/style.css">

<!-- JQUERY JS LIBRARY-->
<script src="${vendor_js}/jquery.min.js"></script>

<!-- JOINT JS LIBRARY-->
<script src="${uml_js}/menu.js" type="text/javascript"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>

</head>
<body>
	<div id='cssmenu'>
		<ul>
			<li><a href='/UMLEngineering/home' id="home"><span>Home</span></a></li>
			<li class='active has-sub'><a href='#'><span>File</span></a>
				<ul>
					<li class='has-sub'><a href='#'><span>New</span></a>
						<ul>
							<li><a href='/UMLEngineering/' id="new"><span>Class
										Diagram</span></a></li>
							<li class='last'><a href='/UMLEngineering/erd'><span>ER
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

			<li><a href='/UMLEngineering/work' id="work"><span>work</span></a></li>
			<li><a href='/UMLEngineering/gDrive'><span>gDrive</span></a></li>
			<li class='last'><a href='/UMLEngineering/index/index/'><span>index</span></a></li>
			<li><a href='/UMLEngineering/index/home' id="index"><span>Log
						In/Sign Up</span></a></li>
			<li class='has-sub'><a href='#'><span>Settings</span></a>
				<ul>
					<li><a href='/UMLEngineering/logOut' id="logOut"><span>Sign
								Out</span></a></li>
					<li class='last'><a href='/UMLEngineering/changePassword'
						id="changePassword"><span>Change Password</span></a></li>
				</ul></li>

			<!-- <li><a onclick="testOpen();" href="javascript:void(0);">New
					Test</a></li> -->
		</ul>
	</div>
</body>
</html>