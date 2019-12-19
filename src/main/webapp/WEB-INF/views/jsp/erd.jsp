
<body>
	<%@include file="/WEB-INF/views/layout/headerERD.jsp"%>
	<script language="JavaScript">
		function getFolder() {
			var path = showModalDialog("work", "",
					"width:400px;height:400px;resizeable:yes;");
			/* document.getElementById('reverse_text').value = path;
			document.getElementById('forward_text').value = path; */
			return path;
		}
	</SCRIPT>
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
			<div class="tools connect" title="Connect"></div>
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
							placeholder="File Path"></td>
					</tr>
					<tr>
						<td><button id="submit_import">Open</button></td>
						<td><button id="cancel_import">Cancel</button></td>
					</tr>
				</table>
			</li>
		</ul>
	</div>


	<div class="importXmiMask"></div>
	<div id="importXmi_content">
		<ul id="tabOut">
			<li class="active">
				<table>
					<tr>
						<td colspan="2"><input type=file name="file"
							id="importXmi_text" placeholder="File Path"></td>
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



	<div class="dbMask"></div>
	<div id="db_content">
		<ul id="tabOut">
			<li class="active">
				<table>
					<tr>
						<td><input type="radio" name="dbVendor" value="Oracle"
							id="oracle" checked>Oracle</td>
						<td><input type="radio" name="dbVendor" value="MySQL"
							id="mySQL">MySQL</td>
					</tr>
					<tr>
						<td colspan="2"><input type=text name="db_url" id="db_url"
							placeholder="Data Base URL"></td>
					</tr>
					<tr>
						<td colspan="2"><input type=text name="db_userName"
							id="db_userName" placeholder="User Name"></td>
					</tr>

					<tr>
						<td colspan="2"><input type=password name="db_password"
							id="db_password" placeholder="Password"></td>
					</tr>
					<tr>
						<td><button id="cancel_db">Cancel</button></td>
						<td><button id="submit_db">Save</button></td>
					</tr>
				</table>
			</li>
		</ul>
	</div>

	<div class="reverseDBMask"></div>
	<div id="reverseDB_content">
		<ul id="tabOut">
			<li class="active">
				<table>
					<tr>
						<td><input type="radio" name="reverseDBVendor" value="Oracle"
							id="oracle" checked>Oracle</td>
						<td><input type="radio" name="reverseDBVendor" value="MySQL"
							id="mySQL">MySQL</td>
					</tr>
					<tr>
						<td colspan="2"><input type=text name="reverseDB_url"
							id="reverseDB_url" placeholder="Data Base URL"></td>
					</tr>
					<tr>
						<td colspan="2"><input type=text name="reverseDB_userName"
							id="reverseDB_userName" placeholder="User Name"></td>
					</tr>

					<tr>
						<td colspan="2"><input type=password
							name="reverseDB_password" id="reverseDB_password"
							placeholder="Password"></td>
					</tr>
					<tr>
						<td><button id="cancel_reverseDB">Cancel</button></td>
						<td><button id="submit_reverseDB">Save</button></td>
					</tr>
				</table>
			</li>
		</ul>
	</div>

	<div class="ormDBMask"></div>
	<div id="ormDB_content">
		<ul id="tabOut">
			<li class="active">
				<table>

					<tr>

						<td colspan="2"><input type=text readonly
							name="ormProjectLocation" id="ormProjectPath_text"
							placeholder="Project Path"
							onclick="ormProjectLocation.value=getFolder()"></td>
						<td><input type="button" value="Browse"
							onclick="ormProjectLocation.value=getFolder()"></td>
					</tr>
					<tr>
						<td colspan="2"><input type=text name="projectName"
							id="projectName_text" placeholder="Project Name"></td>
					</tr>
					<tr>
						<td colspan="2"><input type=text name="packageName"
							id="packageName_text" placeholder="Package Name"></td>
					</tr>

					<tr>
						<td><input type="radio" name="ormDBVendor" value="Oracle"
							id="oracle" checked>Oracle</td>
						<td><input type="radio" name="ormDBVendor" value="MySQL"
							id="mySQL">MySQL</td>
					</tr>
					<tr>
						<td colspan="2"><input type=text name="ormDB_url"
							id="ormDB_url" placeholder="Data Base URL"></td>
					</tr>
					<tr>
						<td colspan="2"><input type=text name="ormDB_userName"
							id="ormDB_userName" placeholder="User Name"></td>
					</tr>

					<tr>
						<td colspan="2"><input type=password name="ormDB_password"
							id="ormDB_password" placeholder="Password"></td>
					</tr>
					<tr>
						<td><button id="cancel_ormDB">Cancel</button></td>
						<td><button id="submit_ormDB">Save</button></td>
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
						<td colspan="2"><input type=text name="exportXmiLocation"
							id="exportPath_text" placeholder="XMI Path"></td>
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
						<td colspan="2"><input type=text
							name="forwardProjectLocation" id="forward_text"
							placeholder="Project Path"></td>
						<td><input type="button" value="Browse"
							onclick="forwardProjectLocation.value=getFolder()"></td>
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
							<button id="generateSource1">Generate</button>
						</td>
						<td>
							<button id="cancel_codeTemplate">Cancel</button>
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
						<td colspan="2"><input type=text
							name="reverseProjectLocation" id="reverse_text"
							placeholder="Project Path"></td>
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
		<!-- <label for="links">Links type : </label> <select id="links">
			<option value="0" selected="selected">Association</option>
			<option value="1">Composition</option>
			<option value="2">Aggregation</option>
			<option value="3">Generalisation</option>
			<option value="4">Implementation</option>
		</select><br /> -->

		<div class="tools_content" id="association" style="display: none;">
			<div class="title">
				<div class="collapse">^</div>
				Cardinalities
			</div>
			<div class="visible_content">
				<input type="text" id="source_card" placeholder="source default 1" />
				<input type="text" id="target_card" placeholder="target default 1" />
			</div>
		</div>
		<div class="tools_content" id="className" style="display: none;">
			<div class="title">
				<div class="collapse">^</div>
				Entity name
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
				Keys
				<div class="add" id="add_method">+</div>
			</div>
			<div class="visible_content"></div>
		</div>
		<!-- <div class="tools_content">
			<div class="title">
				<div class="collapse">^</div>
				DB generation
			</div>
			<div class="visible_content">
				<div class="langage_content">
				<input type="radio" name="dbVendor" value="Oracle" id="oracle" checked><label for="langage_oracle">Oracle</label><br />
  				<input type="radio" name="dbVendor" value="MySQL" id="mySQL"><label for="langage_mySQL">MySQL</label><br>
				</div>
				<button id="generateSource">Generate DB Tables</button>
			</div>
		</div> -->
	</div>


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