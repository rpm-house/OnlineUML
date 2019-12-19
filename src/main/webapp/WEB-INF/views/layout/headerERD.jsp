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


<!-- JQUERY JS LIBRARY-->
<script src="${vendor_js}/jquery.min.js"></script>
<!-- JOINT JS LIBRARY-->
<script src="${vendor_js}/joint_uml.js" type="text/javascript"></script>
<script src="${vendor_js}/joint.shapes.uml.er.min.js"
	type="text/javascript"></script>
<script src="${vendor_js}/joint.uml.js" type="text/javascript"></script>
<script src="${vendor_js}/joint.format.svg.js" type="text/javascript"></script>


<!-- EXTERNAL JS LIBRARY-->
<script src="${uml_js}/jszip.min.js" type="text/javascript"></script>
<script src="${uml_js}/FileSaver.js" type="text/javascript"></script>
<script src="${uml_js}/erEditor.js" type="text/javascript"></script>
<script src="${uml_js}/menu.js" type="text/javascript"></script>
<script src="${uml_js}/gDriveOps.js" type="text/javascript"></script>

<script type="text/javascript"
	src="https://apis.google.com/js/api.js?onload=onApiLoad"></script>
<script type="text/javascript"
	src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>


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

			<li class='active has-sub'><a href='#'><span>File</span></a>
				<ul>
					<li class='last'><a href='/erd' id="new"><span>New
						</span></a></li>

					<li><a href='#' id="import"><span>Open From Local</span></a>
					<li><a href='#' id="import1" onclick="openFiles(1);"><span>Open
								From GDrive</span></a>
					<li class='has-sub'><a href='#'><span>Save As</span></a>
						<ul>
							<li><a href='#' id="save"><span>JSON</span></a></li>
							<li class='last'><a href='#' id="saveImage"><span>Image</span></a></li>

						</ul></li>
					<li><a href='#' id="saveGD"><span>Save In GDrive</span></a>
					<!-- <li><a href='#' id="exportXMI"><span>Export XMI</span></a></li>
					<li><a href='#' id="importXMI"><span>Import XMI</span></a></li> -->

				</ul></li>
			<li class='has-sub'><a href='#'><span>Engineering</span></a>
				<ul>
				
				<li><a href='#' id="generateSource"><span>Generate DB</span></a></li>
				<li><a href='#' id="reverseDB"><span>Reverse DB</span></a></li>
				<li><a href='#' id="generateForwardORM"><span>Generate ORM Code From ER Diagram</span></a></li>
				<li><a href='#' id="generateReverseORM"><span>Generate ORM Code From DB Tables</span></a></li>
				</ul></li>

			<li><a href='/uml' id="new"><span>Class
						Diagram</span></a></li>

			<input type="hidden" id="render-link" name="render-link"
				value="false">
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
				value="true"> <input type="hidden" name="workspace"
				id="workspace" value=<%=user.getWorkspace()%>>
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