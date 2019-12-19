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

<!-- JQUERY JS LIBRARY-->
<script src="${vendor_js}/jquery.min.js"></script>

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
</head>
<body>

<div id="wrapper">

		<div id="top-bar"></div>

		<header>
			<div id="title" class="container">

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
</div>
		</header>
		
<%-- <canvas id="canvas" style="border:2px solid black;" width="200" height="200">
</canvas> --%>

  <!--   <img id="canvasImg" alt="Right click to save me!"> -->
<!-- <div id="floatingTools">
<ul>
  <li><button id="new">New</button></li>
			<li><button id="import">Open</button></li>
			<li><button id="save">Save</button></li>
			<li><a href="/UMLWeb/erd" >ER</a></li>
</ul>
</div> -->
	

       <%--  <% 
            String file = "D:\\projects\\KBE\\uml_Json"; 

            File f = new File(file);
            //out.println(f);
            String [] fileNames = f.list();
            //out.println(fileNames);
            File [] fileObjects= f.listFiles();
            //out.println(fileObjects);
        %>
       <!-- <UL> -->
        <%
            for (int i = 0; i < fileObjects.length; i++) {
               /*  if(!fileObjects[i].isDirectory()){ */
        %>
        <!-- <LI> -->
        <input type="hidden" id="open_text_id" value="<%= i %>">
         <input type="hidden" id="open_text<%=i%>" value="D:\\projects\\KBE\\uml_Json\\<%= fileNames[5] %>">
        
        
        <%
                }
              
           /*  } */
        %>
         <a href="javascript:void(0);"  onclick="testOpen();"><span><%= fileNames[5] %></span></a> --%>
         
        <!-- </UL> -->

	<!-- <div id="floatingTools">
		<button id="save">Save</button>
		<button id="import">Open</button>
		<button id="new">New</button>

		<button id="zoomMore"></button>
	<button id="zoomLess"></button>
	</div>
 -->
<div id="main">
			<div class="container">
		<ul id="tabs">
					<li class="active">Log in</li>
					<li>Sign Up</li>

				</ul>
				<ul id="tab">
					<li class="active">
	<div id="classDiagram">
		<div id="editor">
			<div class="resize nw"></div>
			<div class="resize n"></div>
			<div class="resize ne"></div>
			<div class="resize e"></div>
			<div class="resize se"></div>
			<div class="resize s"></div>
			<div class="resize sw"></div>
			<div class="resize w"></div>
			<div class="tools delete" title="Delete"></div>
			<div class="tools duplicate" title="Duplicate"></div>
			<div class="tools connect" title="Connect"></div>
		</div>
		<div id="diagrammContent"></div>
	</div>
	

	<div class="mask"></div>
	<div id="import_content">
		<input type=file name="file" id="import_text">
		<!-- <textarea id="import_text" placeholder="paste content of your diagram.txt or JSON representation of your diagram"></textarea><br/> -->
		<button id="cancel_import">Cancel</button>
		<button id="submit_import">Import</button>
	</div>
	
	<div class="importXmiMask"></div>
	<div id="importXmi_content">
		<input type=file name="file" id="importXmi_text">
		<button id="cancel_importXmi">Cancel</button>
		<button id="submit_importXmi">Import</button>
	</div>

<div class="saveGMask"></div>
	<div id="save_Gcontent">
	<input type=text name="fileName" id="file_Gtext"
			placeholder="UMLEng_ClassDiagram">
		<div id="savetodrive-div"></div>
		<button id="cancel_Google">Cancel</button>
	</div>

	<div class="saveMask"></div>
	<div id="save_content">
		<!-- <textarea id="save_text" placeholder="paste content of your diagram.txt or JSON representation of your diagram"></textarea> -->
		<input type=text name="folderLocation" id="save_text"
			placeholder="D:\projects\KBE\uml_Json\ClassDiagram">
		<button id="cancel_save">Cancel</button>
		<button id="submit_save">Save</button>
	</div>
	
	
	<div class="saveImageMask"></div>
	<div id="saveImage_content">
		<!-- <textarea id="save_text" placeholder="paste content of your diagram.txt or JSON representation of your diagram"></textarea> -->
		<input type=text name="folderLocation" id="saveImage_text"
			placeholder="D:\projects\KBE\uml_Json\ClassDiagram"><br>
			<label for="fileType">File Type : </label> <select id="saveImage_Format">
			<option value="PNG" selected="selected">PNG</option>
			<option value="JPG">JPG</option>
			<option value="SVG">SVG</option>
		</select>
		<button id="cancel_saveImage">Cancel</button>
		<button id="submit_saveImage">Save</button>
	</div>
	
	

<div class="exportMask"></div>
	<div id="export_content">
		<!-- <textarea id="save_text" placeholder="paste content of your diagram.txt or JSON representation of your diagram"></textarea> -->
		<input type=text name="folderLocation" id="export_text"
			placeholder="D:\projects\KBE\uml_Json\ClassDiagram">
		<button id="cancel_export">Cancel</button>
		<button id="submit_export">Export</button>
	</div>
	
	<div class="forwardMask"></div>
	<div id="forward_content">
		<!-- <textarea id="save_text" placeholder="paste content of your diagram.txt or JSON representation of your diagram"></textarea> -->
		<input type=text name="folderLocation" id="forward_text"
			placeholder="D:\projects\KBE\uml_Json">
		<button id="cancel_forward">Cancel</button>
		<button id="submit_forward">Generate</button>
	</div>

<div class="reverseMask"></div>
	<div id="reverse_content">
		<!-- <textarea id="save_text" placeholder="paste content of your diagram.txt or JSON representation of your diagram"></textarea> -->
		<input type=text name="folderLocation" id="reverse_text"
			placeholder="D:\projects\KBE\uml_Json\ClassDiagram">
		<button id="cancel_reverse">Cancel</button>
		<button id="submit_reverse">Reverse</button>
	</div>

<div class="resultMask"></div>

	<div id="result_content">
	<div id="result_text">
	</div>
	<button id="ok_result">OK</button>
	</div>
</div>
		</div>
	</li>
					<li>	
			<div id="tools">
		<div id="tools_paper"></div>
		<label for="links">Links type : </label> <select id="links">
			<option value="0" selected="selected">Association</option>
			<option value="1">Composition</option>
			<option value="2">Aggregation</option>
			<option value="3">Generalisation</option>
			<option value="4">Implementation</option>
		</select><br />

		<div class="tools_content" id="association" style="display: none;">
			<div class="title">
				<div class="collapse">^</div>
				Associations
			</div>
			<div class="visible_content">
				<label for="source_card">Cardinalities</label><br /> <input
					type="text" id="source_card" placeholder="source default 1" /> <input
					type="text" id="target_card" placeholder="target default 1" />
			</div>
		</div>
		<div class="tools_content" id="className" style="display: none;">
			<div class="title">
				<div class="collapse">^</div>
				Class name
			</div>
			<div class="visible_content">
				<input placeholder="Class name" value="" />
			</div>
		</div>
		 <div class="tools_content" id="attributs" style="display: none;">
			<div class="title">
				<div class="collapse">^</div>
				Attributes
				<div class="add" id="add_attribut">+</div>
			</div>
			<div class="visible_content"></div>
		</div>
		<div class="tools_content" id="methods" style="display: none;">
			<div class="title">
				<div class="collapse">^</div>
				Methods
				<div class="add" id="add_method">+</div>
			</div>
			<div class="visible_content"></div>
		</div>
		<div class="tools_content">
			<div class="title">
				<div class="collapse">^</div>
				Model generation
			</div>
			<div class="visible_content">
				<div class="langage_content">
					<input type="checkbox" name="langage[]" id="langage_java"
						value="java" /><label for="langage_java">Java</label><br />
					<input type="checkbox"  name="langage[]" id="langage_php" value="php" /><label for="langage_php">PHP</label><br />
				<input type="checkbox"  name="langage[]" id="langage_objc" value="objC" /><label for="langage_objc">Objective C</label><br />
				<input type="checkbox"  name="langage[]" id="langage_swift" value="swift" /><label for="langage_swift">Swift</label><br />
					<input type="checkbox" name="langage[]" id="langage_cpp" value="vb" /><label
						for="langage_vb">VB</label><br />
				</div>
				<button id="generateSource">Generate</button>
			</div>
		</div>
	</div></li>
			</ul>
			</div>
		</div>
	<footer>
	
  </footer>
		<!-- /footer -->



	</div>
	<!-- /#wrapper -->
	

		

	<!-- <div id="paypal">
<form action="https://www.paypal.com/cgi-bin/webscr" target="blank" method="post" style="margin: 0;">
	<input type="hidden" name="cmd" value="_s-xclick">
	<input type="hidden" name="hosted_button_id" value="632YE7W64P2NW">
	<input type="image" src="https://www.paypal.com/en_US/i/btn/btn_donate_SM.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
	<img alt="Paypal donation" border="0" src="https://www.paypal.com/fr_FR/i/scr/pixel.gif" width="1" height="1">
</form>
</div> -->
</body>
</html>