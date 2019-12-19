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
<link type="text/css" rel="stylesheet"
	href="${vendor_css}/joint.min.css">
<link type="text/css" rel="stylesheet" href="${uml_css}/umlEditor.css">
<link type="text/css" rel="stylesheet" href="${uml_css}/menu.css">
<link type="text/css" rel="stylesheet" href="${uml_css}/base.css">
<link type="text/css" rel="stylesheet" href="${uml_css}/style.css">
<link type="text/css" rel="stylesheet" href="${uml_css}/popUp.css">

<!-- <link
	href='http://fonts.googleapis.com/css?family=Ubuntu:300,400,700,400italic'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Oswald:400,300,700'
	rel='stylesheet' type='text/css'> -->




<!-- JQUERY JS LIBRARY-->
<script src="${vendor_js}/jquery.min.js"></script>
<!-- JOINT JS LIBRARY-->
<script src="${vendor_js}/joint_uml.js" type="text/javascript"></script>
<script src="${vendor_js}/joint.shapes.uml.min.js"
	type="text/javascript"></script>
<script src="${vendor_js}/joint.uml.js" type="text/javascript"></script>
<script src="${vendor_js}/joint.format.svg.js" type="text/javascript"></script>


<!-- EXTERNAL JS LIBRARY-->
<script src="${uml_js}/jszip.min.js" type="text/javascript"></script>
<script src="${uml_js}/FileSaver.js" type="text/javascript"></script>
<script src="${uml_js}/umlEditor.js" type="text/javascript"></script>
<script src="${uml_js}/menu.js" type="text/javascript"></script>
<script src="${uml_js}/gDriveOps.js" type="text/javascript"></script>
<%-- <script src="${uml_js}/gDriveOpenFile.js" type="text/javascript"></script> --%>
<%-- <script src="${uml_js}/gDriveSaveFile.js" type="text/javascript"></script> --%>
<!-- <script src="https://apis.google.com/js/platform.js" async defer></script> -->
<script type="text/javascript" src="https://apis.google.com/js/api.js?onload=onApiLoad"></script>
<script type="text/javascript" src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>
<!-- <script type="text/javascript" src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>
<script type="text/javascript" src="https://apis.google.com/js/api.js?onload=onApiLoad"></script>
<script type="text/javascript" src="https://apis.google.com/js/client.js"></script>
 <script type="text/javascript" src="https://apis.google.com/js/api.js"></script>-->
<script language="JavaScript">
	function getFolder() {
		alert("hai");
		return showModalDialog("work", "",
				"width:400px;height:400px;resizeable:yes;");
	}
</SCRIPT>
</head>
<body>

	<div id='cssmenu'>
		<ul>
			<li><a href='/' id="home"><span>Home</span></a></li>
			<li><a href='/index' id="index"><span>Log
						In / Sign Up</span></a></li>
						
						<li><a href='/uml' id="new"><span>Class
										Diagram</span></a></li>
							<li class='last'><a href='/erd'><span>ER
										Diagram</span></a></li>
			<li class='has-sub'><a href='#'><span>Settings</span></a>
				<ul>
					<li><a href='/logOut' id="logOut"><span>Sign
								Out</span></a></li>
					<li class='last'><a href='/changePassword'
						id="changePassword"><span>Change Password</span></a></li>
				</ul></li>

								<input type="hidden" id="render-link" name="render-link" 	value="false">
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
				<input type="hidden" name="workspace" id="workspace"
				value=<%=user.getWorkspace()%>>
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