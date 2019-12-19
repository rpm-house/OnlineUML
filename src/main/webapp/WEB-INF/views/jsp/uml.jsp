<body>
	<%@include file="/WEB-INF/views/layout/header.jsp"%>
	<script language="JavaScript">
		function getFolder() {
			 alert("dialog");
			//var path = showModalDialog("work", "","width:400px;height:400px;resizeable:yes;");
			var path = window.showModalDialog("work", "","width:400px;height:400px;resizeable:yes;");
			
			//var path1 = window.showModalDialog("@Url.Action('work', 'Attachment')", "List", "scrollbars=no,resizable=no,width=400,height=280");
			alert(path);
			return path;
					} 
		
				/* function getFolder()
					{
						var reader = new FileReader();

						reader.onload = function (event) {
						    document.getElementById("fakeupload").src = event.target.result;
						    alert(event.target.result);
						};
						alert(document.getElementById("path").files[0]);
						
					alert(reader.readAsDataURL(document.getElementById("path").files[0]));
					} */
					function handleFiles(files) {
						alert('handleFiles');
					    var file = files[0];
					    var reader = new FileReader();
					    reader.onload = onFileReadComplete;
					    reader.readAsText(file);
					    alert(file);
					    alert(reader)
					}
		 
		 $(document).ready( function() {
			    $('#container_id').fileTree({
			        root: '/some/folder/',
			        script: 'jqueryFileTree.asp',
			        expandSpeed: 1000,
			        collapseSpeed: 1000,
			        multiFolder: false
			    }, function(file) {
			        alert(file);
			    });
			});
	</SCRIPT>

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
			<div class="tools connect" title="Connect" id="connect"
				onclick="validateLink();"></div>
		</div>
		<div id="diagrammContent"></div>
	</div>

	<div class="mask"></div>
	<div id="import_content">
		<ul id="tabOut">
			<li class="active">
				<table>
					<tr>
						<td colspan="2"><input type=file name="file" id="import_text"
							placeholder="File Path" ></td>
							<!-- onchange="handleFiles(this.files)" -->
					</tr>
					<tr>
						<td><button id="submit_import">Open</button></td>
						<td><button id="cancel_import">Cancel</button></td>
					</tr>
				</table>
			</li>
		</ul>
		<!-- 
	
	
	
		<br> <label for="fileName" class="language_content">Json
			File Path : </label><input type=file name="file" id="import_text">
		<textarea id="import_text" placeholder="paste content of your diagram.txt or JSON representation of your diagram"></textarea><br/>
		<br> <br>
		 <span class="close-btn" id="cancel_import"><a href="#">X</a></span>
		<button id="submit_import">Import</button> -->

	</div>

	<div class="importXmiMask"></div>
	<div id="importXmi_content">
		<ul id="tabOut">
			<li class="active">
				<table>
					<tr>
						<td colspan="2"><input type=file name="file"
							id="importXmi_text" placeholder="File Path" ></td>
					</tr>
					<tr>
						<td>
							<button id="submit_importXmi">Import</button>
						</td>
						<td>
							<button id="cancel_importXmi">Cancel</button>
						</td>
					</tr>
				</table>
			</li>
		</ul>
		<!-- <span class="close-btn" id="cancel_importXmi"><a href="#">X</a></span> -->
	</div>

	<div class="saveGMask"></div>
	<div id="save_Gcontent">

		<ul id="tabOut">
			<li class="active">
				<table>
					<tr>
						<td colspan="2"><input type=text name="fileNameGD"
							id="file_Gtext" placeholder="File Name"></td>
					</tr>
					<tr>
						<td><button id="submit_GD">Save</button></td>
						<td><button id="cancel_GD">Cancel</button></td>
					</tr>
				</table>
			</li>
		</ul>
		<!-- <br> <input type=text name="fileName" id="file_Gtext"
			placeholder="D:\UMLEngineering\workspace\ClassDiagram1"> 
		<input type="button" id="doitButton" style="display: none"
			value="Do it" /> <br> <input type="button" id="authorizeButton"
			style="display: none" value="Authorize" /> <br> <br>
		<button id="cancel_Google">Cancel</button>
		<span class="close-btn" id="cancel_Google"><a href="#">X</a></span> -->
	</div>

	<div class="saveMask"></div>
	<div id="save_content">
		<!-- <textarea id="save_text" placeholder="paste content of your diagram.txt or JSON representation of your diagram"></textarea> -->
		<%-- <table>
			<tr>
				<td><label for="fileType" class="language_content">File
						Location </label></td>
				<td>
				<select id="fileLocation" onchange="changeFileLocation();">
				<option value="0" selected="selected">File Location</option>
						<%
							if (null != request.getSession().getAttribute("user")) {
								User user = (User) request.getSession().getAttribute("user");
						%>
						<option value="<%=user.getWorkspace()%>" ><%=user.getWorkspace()%></option>
						<%
							}
						%>
						<option value="GDrive" >GDrive</option>
				</select></td>
			</tr>
			<tr>
				<td><label for="fileName" class="language_content">File
						Name </label></td>
				<td><input type=text name="folderLocation" id="save_text"
					placeholder="File Name"></td>
			</tr>
			<tr>
			<td><button id="cancel_save" style="display: none">Cancel</button></td>
			<td><button id="authorizeButton" style="display: none">Authorize</button>
			<button id="doitButton" style="display: none">Save</button>
			
			<%
				if (null != request.getSession().getAttribute("user")) {
					User user = (User) request.getSession().getAttribute("user");
			%>
		
				<button id="submit_save" style="display: none">Save</button>
				
			<%
				}
			%>
			</td>
			</tr>
		</table> --%>
		<ul id="tabOut">
			<li class="active">
				<table>
					<tr>
						<td colspan="2"><input type=text name="saveFile"
							id="save_text" placeholder="File Name"></td>
					</tr>
					<tr>
						<td colspan="2"><input type=text readonly name="saveLocation"
							id="savePath_text" placeholder="File Path"
							onclick="saveLocation.value=getFolder()"></td>
						<td><input type="button" value="Browse"
							onclick="saveLocation.value=getFolder()"></td>
					</tr>
					<tr>
						<td><button id="submit_save">Save</button></td>
						<td><button id="cancel_save">Cancel</button></td>
					</tr>
				</table>
			</li>
		</ul>
	</div>


	<div class="saveImageMask"></div>
	<div id="saveImage_content">
		<ul id="tabOut">
			<li class="active">
				<table>
					<tr>
						<td colspan="2"><input type=text name="saveImage"
							id="saveImage_text" placeholder="File Name"></td>
					</tr>
					<tr>
						<td colspan="2"><select id="saveImage_Format">
								<option value=" " selected="selected">File Type</option>
								<option value="PNG">PNG</option>
								<option value="JPG">JPG</option>
								<option value="SVG">SVG</option>
						</select></td>
					</tr>
					<tr>
						<td colspan="2"><input type=text readonly
							name="saveImageLocation" id="saveImagePath_text"
							placeholder="File Path"
							onclick="saveImageLocation.value=getFolder()"></td>
						<td><input type="button" value="Browse"
							onclick="saveImageLocation.value=getFolder()"></td>
					</tr>
					<tr>
						<td><button id="submit_saveImage">Save</button></td>
						<td><button id="cancel_saveImage">Cancel</button></td>
					</tr>
				</table>
			</li>
		</ul>
	</div>

	<div class="exportMask"></div>
	<div id="export_content">
		<ul id="tabOut">
			<li class="active">
				<table>
					<tr>
						<td colspan="2"><input type=text name="exportXmi"
							id="export_text" placeholder="File Name"></td>
					</tr>
					<tr>
						<td colspan="2"><input type=text readonly
							name="exportXmiLocation" id="exportPath_text"
							placeholder="XMI Path"
							onclick="exportXmiLocation.value=getFolder()"></td>
						<td><input type="button" value="Browse"
							onclick="exportXmiLocation.value=getFolder()"></td>
					</tr>
					<tr>
						<td>
							<button id="submit_export">Export</button>
						</td>
						<td>
							<button id="cancel_export">Cancel</button>
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</div>

	<div class="forwardMask"></div>
	<div id="forward_content">
		<ul id="tabOut">
			<li class="active">
				<table>
					<tr>
						<td colspan="2"><input type=text readonly
							name="forwardProjectLocation" id="forward_text"
							placeholder="Project Path"
							onclick="forwardProjectLocation.value=getFolder()"></td>

						<td><input type="button" value="Browse"
							onclick="forward_text.value=getFolder()" id="browseForIE"> 
							
						<input id="fakeupload" name="fakeupload[]" class="inputfile fakeupload" type="text" />
<!-- <input id="path" name="path[]" class="inputfile realupload" type="file" value="" onchange="javascript:document.getElementById('fakeupload').value = document.getElementById('path').value;" /> -->
 <input id="path" name="path[]" class="inputfile realupload" type="file" value="" onchange="getFolder();" />
												</tr>
					<tr>
						<td>
							<button id="submit_forward">Generate</button>
						</td>
						<td>
							<button id="cancel_forward">Cancel</button>
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</div>

	<div class="codeTemplateMask"></div>
	<div id="codeTemplate_content">
		<ul id="tabOut">
			<li class="active">

				<table>
					<tr>
						<td><input type="checkbox" name="language[]"
							id="language_java" value="java" /><label for="language_java">Java</label></td>
						<td><input type="checkbox" name="language[]"
							id="language_cpp" value="vb" /><label for="language_vb">VB</label></td>
					</tr>
					<tr>
						<td>
							<button id="generateSource">Generate</button>
						</td>
						<td>
							<button id="cancel_codeTemplate">Cancel</button>
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</div>



	<div class="codeSnippetMask"></div>
	<div id="codeSnippet_content">
		<ul id="tabOut">
			<li class="active">

				<table>
					<tr>
					<tr>
						<td><input type="radio" name="codeLanguage" value="java"
							id="java" checked>Java</td>
						<td><input type="radio" name="codeLanguage" value="vb"
							id="vb">VB</td>
					</tr>
					<tr>
						<td colspan="2"><select id="saveImage_Format">
								<option value=" " selected="selected">Functionality</option>
								<option value="for">For Loop</option>
								<option value="efor">Enhanced For</option>
								<option value="while">While</option>
								<option value="doWhile">DO While</option>
								<option value="doWhile">DO While</option>
						</select></td>
					</tr>
					<tr>
						<td>
							<button id="generateCode">Generate</button>
						</td>
						<td>
							<button id="cancel_codeSnippet">Cancel</button>
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</div>


	<div class="reverseMask"></div>
	<div id="reverse_content">
		<ul id="tabOut">
			<li class="active">
				<table>
					<tr>
						<td colspan="2"><input type=text readonly
							name="reverseProjectLocation" id="reverse_text"
							placeholder="Project Path"
							onclick="reverseProjectLocation.value=getFolder()"></td>
						<td><input type="button" value="Browse"
							onclick="reverseProjectLocation.value=getFolder()"></td>
					</tr>
					<tr>
						<td>
							<button id="submit_reverse">Reverse</button>
						</td>
						<td><button id="cancel_reverse">Cancel</button></td>
					</tr>
				</table>
			</li>
		</ul>
	</div>

	<div class="resultMask"></div>

	<div id="result_content">


		<ul id="tabOut">
			<li class="active">
				<table>
					<tr>
						<td><div id="result_text"></div></td>
					</tr>
					<tr>
						<td><button id="ok_result">OK</button></td>
					</tr>
				</table>
			</li>
		</ul>
	</div>

	<div id="tools">
		<div id="tools_paper"></div>
		<br /> <label for="links" class="language_content">Links type
			: </label> <select id="links">
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
		<!-- <div class="tools_content">
			<div class="title">
				<div class="collapse">^</div>
				Model generation
			</div>
			<div class="visible_content">
				<div class="language_content">
					<input type="checkbox" name="language[]" id="language_java"
						value="java" /><label for="language_java">Java</label><br />
					<input type="checkbox"  name="language[]" id="language_php" value="php" /><label for="language_php">PHP</label><br />
				<input type="checkbox"  name="language[]" id="language_objc" value="objC" /><label for="language_objc">Objective C</label><br />
				<input type="checkbox"  name="language[]" id="language_swift" value="swift" /><label for="language_swift">Swift</label><br />
					<input type="checkbox" name="language[]" id="language_cpp"
						value="vb" /><label for="language_vb">VB</label><br />
				</div>
				<button id="generateSource">Generate</button>
			</div>
		</div> -->
	</div>



	<!-- <div id="paypal">
	<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
<input type="hidden" name="cmd" value="_donations">
<input type="hidden" name="business" value="rmdmohan27@gmail.com">
<input type="hidden" name="lc" value="US">
<input type="hidden" name="item_name" value="UMLWEB">
<input type="hidden" name="no_note" value="0">
<input type="hidden" name="currency_code" value="USD">
<input type="hidden" name="bn" value="PP-DonationsBF:btn_donateCC_LG.gif:NonHostedGuest">
<input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
</form> -->


	<!-- <form action="https://www.paypal.com/cgi-bin/webscr" target="blank" method="post" style="margin: 0;">
	<input type="hidden" name="cmd" value="_s-xclick">
	<input type="hidden" name="hosted_button_id" value="H7Z5SKVRNANZQ">
	<input type="image" src="https://www.paypal.com/en_US/i/btn/btn_donate_SM.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
	<img alt="Paypal donation" border="0" src="https://www.paypal.com/fr_FR/i/scr/pixel.gif" width="1" height="1">
</form> -->


	<!-- </div> -->
</body>
</html>