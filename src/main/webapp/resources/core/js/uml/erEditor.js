var graph, paper;
// graph.setSrc("Entity");
var urlPrefix = "http://localhost:8085/UMLEngineering/";
(function() {
	var uml;

	var currentScale = 1;
	var selectedElement = null;

	$(document)
			.ready(
					function() {
						uml = joint.shapes.uml;
						initEditor();
						$("#source_card").blur(refreshCurrentLink);
						$("#target_card").blur(refreshCurrentLink);

						$("#zoomMore").click(function() {
							if (currentScale < 1) {
								currentScale += 0.1;
								paper.scale(currentScale);
								updateEditor(selectedElement);
							}
						});

						$("#zoomLess").click(function() {
							if (currentScale > 0.1) {
								currentScale -= 0.1;
								paper.scale(currentScale);
								updateEditor(selectedElement);
							}
						});

						$("#saveGD").click(function() {
							validateGraph();
							validateDuplicateElements();
							$(".saveGMask").show();
							$("#save_Gcontent").fadeIn();

						});

						$("#cancel_GD").click(function() {
							$(".saveGMask").hide();
							$("#save_Gcontent").fadeOut();
						});

						$("#submit_GD").click(function() {
							validateField('#file_Gtext');
							saveGraph();
							openFiles(2);
						});

						$("#import").click(function() {
							$(".mask").show();
							$("#import_content").fadeIn();
						});
						$("#cancel_import").click(function() {
							$(".mask").hide();
							$("#import_content").fadeOut();
						});
						$("#submit_import")
								.click(
										function() {
											validateField('#import_text');
											var result;
											var fileName = $("#import_text")
													.val();
											alert("fileName: " + fileName)
											if (fileName == "") {
												fileName = "D:\\projects\\KBE\\uml_Json\\ERDiagram1.txt";
											}

											var url = urlPrefix + "read?data="
													+ fileName;

											$
													.ajax({
														url : url,
														success : function(data) {
															alert("data:"
																	+ data);

															$(".mask").hide();
															$("#import_content")
																	.fadeOut();
															var obj = $
																	.parseJSON(data);

															var srcType = obj.srcType;

															if (null != srcType
																	&& srcType == "Entity") {

																graph
																		.fromJSON(JSON
																				.parse(data));
																saveGraph();
																return graph;
															} else {
																alert("Class Diagram can not be opened in ER Editor");
																evt
																		.preventDefault();
																return null;
															}

														}
													});

										});

						$("#importXMI").click(function() {
							$(".importXmiMask").show();
							$("#importXmi_content").fadeIn();
						});
						$("#cancel_importXmi").click(function() {
							$(".importXmiMask").hide();
							$("#importXmi_content").fadeOut();
						});
						$("#submit_importXmi")
								.click(
										function() {
											validateField('#importXmi_text');
											var result;
											var fileName = $("#importXmi_text")
													.val();
											alert("fileName: " + fileName)
											if (fileName == "") {
												fileName = "D:\\projects\\KBE\\uml_Json\\classDiagram1.txt";
											}
											var url = urlPrefix
													+ "importXmi?fileName="
													+ fileName;

											$
													.ajax({
														url : url,
														cache : false,
														success : function(data) {
															console
																	.log(
																			"Result from Json: ",
																			data);
															result = data;
															$(".importXmiMask")
																	.hide();
															$(
																	"#importXmi_content")
																	.fadeOut();
															alert("result1: "
																	+ result);
															graph
																	.fromJSON(JSON
																			.parse(result))
															saveGraph();
															return graph;
														}
													});
										});

						$("#save").click(function() {
							validateGraph();
							validateDuplicateElements();
							$(".saveMask").show();
							$("#save_content").fadeIn();

						});
						$("#cancel_save").click(function() {
							$(".saveMask").hide();
							$("#save_content").fadeOut();
						});
						$("#submit_save")
								.click(
										function() {
											validateDuplicateElements();
											validateField('#savePath_text,#save_text ');
											var fileLocation = $(
													"#savePath_text").val();
											var fileName = $("#save_text")
													.val();

											fileName = fileLocation + fileName
													+ ".json";
											// alert("fileName: " + fileName)
											if (fileName == "") {
												fileName = "D:\\projects\\KBE\\uml_Json\\ER_Diagram1.txt";
											}
											graph.setSrc("Entity");
											var jsonString = JSON
													.stringify(graph);
											saveGraph();
											alert(jsonString);
											var url = urlPrefix
													+ "save?fileName="
													+ fileName;

											$
													.ajax({
														url : url,
														contentType : "application/json",
														type : 'post',
														data : jsonString,
														success : function(
																data, status) {

															$(".saveMask")
																	.hide();
															$("#save_content")
																	.fadeOut();
															$(".resultMask")
																	.show();
															$("#result_content")
																	.fadeIn();

															$("#result_text")
																	.html(data);

														}
													});
										});

						$("#saveImage").click(function() {
							validateGraph();
							validateDuplicateElements();
							$(".saveImageMask").show();
							$("#saveImage_content").fadeIn();
						});
						$("#cancel_saveImage").click(function() {
							$(".saveImageMask").hide();
							$("#saveImage_content").fadeOut();
						});
						$("#submit_saveImage")
								.click(
										function() {
											validateField('#saveImage_text, #saveImagePath_text, #saveImage_Format');
											var fileLocation = $(
													"#saveImagePath_text")
													.val();

											var fileName = $("#saveImage_text")
													.val();
											fileName = fileLocation + fileName;
											alert("fileName: " + fileName)
											var fileType = $(
													"#saveImage_Format").val();
											alert("fileType: " + fileType);

											if (fileName == "") {
												fileName = "D:\\projects\\KBE\\uml_Json\\classDiagram1.txt";
											}
											saveGraph();
											var svgDoc = paper.svg;
											var serializer = new XMLSerializer();
											var svgString1 = serializer
													.serializeToString(svgDoc);
											var svgString = svgString1
													.replace(
															"xmlns=\"http://www.w3.org/2000/svg\"",
															" ");
											alert(svgString);
											var url = urlPrefix
													+ "saveImage?fileName="
													+ fileName + "&fileType="
													+ fileType;
											$
													.ajax({
														url : url,
														contentType : "application/json",
														type : 'post',
														cache : false,
														data : svgString,
														success : function(
																data, status) {
															alert(
																	"status: "
																			+ status,
																	+"data: "
																			+ data);
															$(".saveImageMask")
																	.hide();
															$(
																	"#saveImage_content")
																	.fadeOut();
															$(".resultMask")
																	.show();
															$("#result_content")
																	.fadeIn();

															$("#result_text")
																	.html(data);

														}
													});
										});

						$("#ok_result").click(function() {
							$(".resultMask").hide();
							$("#result_content").fadeOut();
						});

						$("#reverse").click(function() {
							$(".reverseMask").show();
							$("#reverse_content").fadeIn();
						});
						$("#cancel_reverse").click(function() {
							$(".reverseMask").hide();
							$("#reverse_content").fadeOut();
						});
						$("#submit_reverse")
								.click(
										function() {
											validateField('#reverse_text');
											var result;
											var fileName = $("#reverse_text")
													.val();
											alert("Project Dir Path: "
													+ fileName)
											if (fileName == "") {
												fileName = "D:\\projects\\biz\\workspace\\UMLWeb";
											}
											var url = urlPrefix
													+ "reverse?fileName="
													+ fileName;

											$
													.ajax({
														url : url,
														cache : false,
														success : function(data) {
															console
																	.log(
																			"Result from Json: ",
																			data);
															result = data;
															$(".reverseMask")
																	.hide();
															$(
																	"#reverse_content")
																	.fadeOut();

															alert("result1: "
																	+ result);

															graph
																	.fromJSON(JSON
																			.parse(result))
															saveGraph();
															return graph;

														}
													});
										});

						$("#exportXMI").click(function() {
							validateGraph();
							validateDuplicateElements();
							$(".exportMask").show();
							$("#export_content").fadeIn();
						});
						$("#cancel_export").click(function() {
							$(".exportMask").hide();
							$("#export_content").fadeOut();
						});
						$("#submit_export")
								.click(
										function() {

											validateField('#export_text,#exportPath_text');
											var fileName = $("#export_text")
													.val();
											var fileLocation = $(
													"#exportPath_text").val();

											fileName = fileLocation + fileName;

											// alert("fileName: " + fileName)
											if (fileName == "") {
												fileName = "D:\\projects\\KBE\\uml_Json\\classDiagram1.txt";
											}
											var jsonString = JSON
													.stringify(graph);
											saveGraph();
											/*
											 * jsonString = jsonString +
											 * "&fileName=" + fileName
											 */
											alert(jsonString);
											var url = urlPrefix
													+ "exportXmi?fileName="
													+ fileName;
											$
													.ajax({
														url : url,
														contentType : "application/json",
														type : 'post',
														cache : false,
														data : jsonString,
														success : function(
																data, status) {
															alert(
																	"status: "
																			+ status,
																	+"data: "
																			+ data);
															$(".exportMask")
																	.hide();
															$("#export_content")
																	.fadeOut();
															$(".resultMask")
																	.show();
															$("#result_content")
																	.fadeIn();

															$("#result_text")
																	.html(data);

														}
													});
										});

						$("#forward").click(function() {
							validateGraph();
							validateDuplicateElements();
							$(".forwardMask").show();
							$("#forward_content").fadeIn();
						});
						$("#cancel_forward").click(function() {
							$(".forwardMask").hide();
							$("#forward_content").fadeOut();
						});
						$("#submit_forward")
								.click(
										function() {
											validateField('#forward_text');
											var fileName = $("#forward_text")
													.val();
											// alert("fileName: " + fileName)
											if (fileName == "") {
												fileName = "D:\\projects\\KBE\\uml_Json\\classDiagram1.txt";
											}
											var jsonString = JSON
													.stringify(graph);
											saveGraph();
											/*
											 * jsonString = jsonString +
											 * "&fileName=" + fileName
											 */
											alert(jsonString);
											var url = urlPrefix
													+ "generateJava?fileName="
													+ fileName;
											$
													.ajax({
														url : url,
														contentType : "application/json",
														type : 'post',
														cache : false,
														data : jsonString,
														success : function(
																data, status) {
															alert(
																	"status: "
																			+ status,
																	+"data: "
																			+ data);
															$(".forwardMask")
																	.hide();
															$(
																	"#forward_content")
																	.fadeOut();
															$(".resultMask")
																	.show();
															$("#result_content")
																	.fadeIn();

															$("#result_text")
																	.html(data);
															alert("result1: "
																	+ result);
															return graph
																	.fromJSON(JSON
																			.parse(result))
														}
													});
										});

						$("#codeTemplate").click(function() {
							validateGraph();
							validateDuplicateElements();
							$(".codeTemplateMask").show();
							$("#codeTemplate_content").fadeIn();
						});
						$("#cancel_codeTemplate").click(function() {
							$(".codeTemplateMask").hide();
							$("#codeTemplate_content").fadeOut();
						});
						$("#submit_codeTemplate").click(function() {
							alert("hai");

							/*
							 * joint.uml.Generator.languages = []; $(
							 * "input[name='language[]']:checked") .each(
							 * function() {
							 * joint.uml.Generator.languages[joint.uml.Generator.languages.length] = $(
							 * this) .val(); }); for (var j = 0; j <
							 * joint.uml.Generator.languages.length; j++) { var
							 * language =
							 * joint.uml.Generator.languages.languages[j];
							 * alert(language); }
							 */
							joint.uml.Generator.generate(graph);
							// alert("code temp1" + $(this).val());

						});

						$("#add_attribut").click(function() {
							addAttribut("");
							// addKey("");
						});
						/*
						 * $("#add_key").click(function() { addKey(""); });
						 */
						$("#add_method").click(function() {
							addMethod("");
						});
						$("#className input").blur(refreshCurrentClass);

						$(".collapse").click(
								function() {
									var div = $(this).parent().parent()
											.children(".visible_content");
									if (div.is(":visible")) {
										$(this).html("v");
									} else {
										$(this).html("^");
									}
									div.slideToggle();
								});

						/* $("#generateSource").click(generateSource); */

						$("#generateSource")
								.click(
										function() {
											validateGraph();
											validateDuplicateElements();
											$(".dbMask").show();
											$("#db_content").fadeIn();
											$("#db_url")
													.val(
															"jdbc:oracle:thin:@localhost:1521:SID");

											$("#db_userName").val("root");
											$("#db_password").val("root");
											$('input:radio[name=dbVendor]')[0].checked = true;

										});
						$("#cancel_db").click(function() {
							$(".dbMask").hide();
							$("#db_content").fadeOut();
						});
						$("input[name=dbVendor]")
								.change(
										function() {
											var db = $(
													"input[name=dbVendor]:checked")
													.val()
											if (db == "MySQL") {
												$("#db_url")
														.val(
																"jdbc:mysql://localhost:3306/Database");
											}
											if (db == "Oracle") {
												$("#db_url")
														.val(
																"jdbc:oracle:thin:@localhost:1521:SID");

											}
											$("#db_userName").val("root");
											$("#db_password").val("root");
										});

						$("#submit_db")
								.click(
										function() {
											validateField('#db_url, #db_userName, #db_password');
											var db = $(
													"input[name=dbVendor]:checked")
													.val()
											var db_url = $("#db_url").val();
											var db_userName = $("#db_userName")
													.val();
											var db_password = $("#db_password")
													.val();

											var dbDetails = "&db_url=" + db_url
													+ "&db_userName="
													+ db_userName
													+ "&db_password="
													+ db_password;
											var jsonString = JSON
													.stringify(graph);
											saveGraph();
											alert("dbDetails :" + dbDetails);
											jsonString = jsonString + dbDetails;
											var url = urlPrefix + "generateDB/";
											$
													.ajax({
														url : url,
														contentType : "application/json",
														type : 'post',
														data : jsonString,
														success : function(
																data, status) {

															$(".dbMask").hide();
															$("#db_content")
																	.fadeOut();
															$(".resultMask")
																	.show();
															$("#result_content")
																	.fadeIn();

															$("#result_text")
																	.html(data);

														}
													});
										});

						$("#reverseDB")
								.click(
										function() {
											$(".reverseDBMask").show();
											$("#reverseDB_content").fadeIn();
											$("#reverseDB_url")
													.val(
															"jdbc:oracle:thin:@localhost:1521:SID");

											$("#reverseDB_userName")
													.val("root");
											$("#reverseDB_password")
													.val("root");
											$('input:radio[name=reverseDBVendor]')[0].checked = true;

										});
						$("#cancel_reverseDB").click(function() {
							$(".reverseDBMask").hide();
							$("#reverseDB_content").fadeOut();
						});
						$("input[name=reverseDBVendor]")
								.change(
										function() {
											var db = $(
													"input[name=reverseDBVendor]:checked")
													.val()
											if (db == "MySQL") {
												$("#reverseDB_url")
														.val(
																"jdbc:mysql://localhost:3306/Database");
											}
											if (db == "Oracle") {
												$("#reverseDB_url")
														.val(
																"jdbc:oracle:thin:@localhost:1521:SID");

											}
											$("#reverseDB_userName")
													.val("root");
											$("#reverseDB_password")
													.val("root");
										});

						$("#submit_reverseDB")
								.click(
										function() {
											validateField('#reverseDB_url, #reverseDB_userName, #reverseDB_password');
											var db = $(
													"input[name=reverseDBVendor]:checked")
													.val()
											var db_url = $("#reverseDB_url")
													.val();
											var db_userName = $(
													"#reverseDB_userName")
													.val();
											var db_password = $(
													"#reverseDB_password")
													.val();

											var dbDetails = "db_url=" + db_url
													+ "&db_userName="
													+ db_userName
													+ "&db_password="
													+ db_password;

											alert("dbDetails :" + dbDetails);
											var url = urlPrefix + "reverseDB/";
											$
													.ajax({
														url : url,
														contentType : "application/json",
														type : 'post',
														data : dbDetails,
														success : function(
																data, status) {

															$(".reverseDBMask")
																	.hide();
															$(
																	"#reverseDB_content")
																	.fadeOut();

															graph
																	.fromJSON(JSON
																			.parse(data));
															saveGraph();
															return graph;
														}
													});
										});

						$("#generateForwardORM")
								.click(
										function() {
											validateGraph();
											validateDuplicateElements();
											$(".ormDBMask").show();
											$("#ormDB_content").fadeIn();
											$("#ormDB_url")
													.val(
															"jdbc:oracle:thin:@localhost:1521:SID");

											$("#ormDB_userName").val("root");
											$("#ormDB_password").val("root");
											$('input:radio[name=ormDBVendor]')[0].checked = true;

										});
						$("#cancel_ormDB").click(function() {
							$(".ormDBMask").hide();
							$("#ormDB_content").fadeOut();
						});
						$("input[name=ormDBVendor]")
								.change(
										function() {
											var db = $(
													"input[name=ormDBVendor]:checked")
													.val()
											if (db == "MySQL") {
												$("#ormDB_url")
														.val(
																"jdbc:mysql://localhost:3306/Database");
											}
											if (db == "Oracle") {
												$("#ormDB_url")
														.val(
																"jdbc:oracle:thin:@localhost:1521:SID");

											}
											$("#ormDB_userName").val("root");
											$("#ormDB_password").val("root");
										});

						$("#submit_ormDB")
								.click(
										function() {
											validateField('#ormDB_url, #ormDB_userName, #ormDB_password');

											var projectPath = $(
													"#ormProjectPath_text")
													.val();
											var projectName = $(
													"#projectName_text").val();
											var packageName = $(
													"#packageName_text").val();

											var db = $(
													"input[name=ormDBVendor]:checked")
													.val()
											var db_url = $("#ormDB_url").val();
											var db_userName = $(
													"#ormDB_userName").val();
											var db_password = $(
													"#ormDB_password").val();

											var dbDetails = "&db_url=" + db_url
													+ "&db_userName="
													+ db_userName
													+ "&db_password="
													+ db_password
													+ "&projectPath="
													+ projectPath
													+ "&projectName="
													+ projectName
													+ "&packageName="
													+ packageName + "&db=" + db;

											var jsonString = JSON
													.stringify(graph);
											saveGraph();
											alert("dbDetails :" + dbDetails);
											jsonString = jsonString + dbDetails;
											var url = urlPrefix
													+ "generateORM/";
											$
													.ajax({
														url : url,
														contentType : "application/json",
														type : 'post',
														data : jsonString,
														success : function(
																data, status) {

															$(".ormDBMask")
																	.hide();
															$("#ormDB_content")
																	.fadeOut();
															$(".resultMask")
																	.show();
															$("#result_content")
																	.fadeIn();

															$("#result_text")
																	.html(data);

														}
													});
										});

						$("#restore").click(restoreGraph);
						// $("#openPng").click(openPngImage);
						$("#generateSource").click(generateSource);
						$("#links").change(changeDefaultLink);

						$("#new").click(function() {
							graph.clear();
							localStorage.removeItem("ERD_graph");
						});

						var tools = new joint.dia.Graph;
						var paperTools = new joint.dia.Paper({
							el : $('#tools_paper'),
							width : $("#tools_paper").width(),
							height : $("#tools_paper").height(),
							gridSize : 1,
							interactive : false,
							model : tools
						});

						graph = new joint.dia.Graph;
						paper = new joint.dia.Paper({
							el : $('#diagrammContent'),
							width : $("#classDiagram").width() * 2,
							height : $("#classDiagram").height() * 2,
							defaultLink : new uml.Association,
							gridSize : 1,
							model : graph
						});

						classes = {

							/*
							 * interface : getDefaultUML(uml.Interface,
							 * "Interface", 70, 0),
							 */
							/*
							 * abstract : getDefaultUML(uml.Abstract,
							 * "Abstract", 130, 0),
							 */
							class : getDefaultUML(uml.Class, "Entity", 40, 0)
						/*
						 * , package : getDefaultUML(uml.Package, "Package", 70,
						 * 210)
						 */

						};
						_.each(classes, function(c) {
							tools.addCell(c);
						});

						restoreGraph();

						paperTools
								.on(
										'cell:pointerdown',
										function(cellView, evt, x, y) {

											var fake = $(
													"<div class='fake'><div id='fake'></div></div>")
													.appendTo("body").css(
															"left", x + "px")
													.css("top", y + "px");

											var fakeGraph = new joint.dia.Graph;
											var fakePaper = new joint.dia.Paper(
													{
														el : $('#fake'),
														width : $("#fake")
																.width(),
														height : $("#fake")
																.height(),
														gridSize : 1,
														model : fakeGraph
													});
											var umlElt = getDefaultUML(
													eval(cellView.model
															.get("type")),
													cellView.model.get("name"),
													0, 0);// _.extend({},
											// cellView);

											fakePaper.addCell(umlElt);

											$("body")
													.mousemove(
															function(evt) {
																fake
																		.css(
																				"left",
																				(evt.pageX - 40)
																						+ "px")
																		.css(
																				"top",
																				(evt.pageY - 40)
																						+ "px");
															});
											$("body")
													.mouseup(
															function(evt) {
																if (evt.pageX
																		- $(
																				"#tools")
																				.width()
																		- 40 > 0)// if
																// we
																// are
																// on
																// target
																// paper
																// we
																// add
																// the
																// new
																// class
																{
																	var finalUmlElt = getDefaultUML(
																			eval(cellView.model
																					.get("type")),
																			cellView.model
																					.get("name"),
																			evt.pageX
																					- $(
																							"#tools")
																							.width()
																					- 40,
																			evt.pageY - 40);

																	graph
																			.addCell(finalUmlElt);
																	$("body")
																			.unbind(
																					"mousemove");
																	$("body")
																			.unbind(
																					"mouseup");
																}
																fake.remove();
															});
										});

						graph.on("change", saveGraph).on('add', saveGraph).on(
								'remove', saveGraph);

						paper
								.on(
										'cell:pointerclick',
										function(cellView, evt, x, y) {
											$("#attributs").hide();
											// $("#keys").hide();
											$("#methods").hide();
											$("#className").hide();
											$("#association").hide();

											if (cellView.model instanceof joint.shapes.uml.Aggregation
													|| cellView.model instanceof joint.shapes.uml.Composition
													|| cellView.model instanceof joint.shapes.uml.Association) {
												selectedElement = cellView;
												$("#source_card")
														.val(
																cellView.model
																		.label(0).attrs.text.text);
												$("#target_card")
														.val(
																cellView.model
																		.label(1).attrs.text.text);
												$("#association").show();
												$("#editor").hide();
											} else if (cellView.model instanceof joint.shapes.uml.Class) {
												selectedElement = cellView;
												updateEditor(selectedElement);

											}

										});
						paper.on('blank:pointerdown', function(cellView, evt,
								x, y) {
							selectedElement = null;
							$("#editor").hide();
							$("#attributs").hide();
							// $("#keys").hide();
							$("#methods").hide();
							$("#association").hide();
							$("#className").hide();
						});
						paper
								.on(
										'cell:pointermove',
										function(cellView, evt, x, y) {
											selectedElement = cellView;
											cellView.model.toFront();
											if (cellView.model instanceof joint.shapes.uml.Aggregation
													|| cellView.model instanceof joint.shapes.uml.Composition
													|| cellView.model instanceof joint.shapes.uml.Association) {
												if (cellView.model.label(0)) {
													$("#source_card")
															.val(
																	cellView.model
																			.label(0).attrs.text.text);
												} else {
													$("#source_card").val("1");
												}
												if (cellView.model.label(1)) {
													$("#target_card")
															.val(
																	cellView.model
																			.label(1).attrs.text.text);
												} else {
													$("#target_card").val("1");
												}

												$("#association").show();
												$("#editor").hide();
												$("#attributs").hide();
												// $("#keys").hide();
												$("#methods").hide();
												$("#className").hide();
											} else if (cellView.model instanceof joint.shapes.uml.Class) {
												updateEditor(cellView);
											}
										});
					});

	function refreshCurrentLink() {
		if (selectedElement != null
				&& (selectedElement.model instanceof joint.shapes.uml.Aggregation
						|| selectedElement.model instanceof joint.shapes.uml.Composition || selectedElement.model instanceof joint.shapes.uml.Association)) {
			selectedElement.model.label(0, {
				position : .1,
				attrs : {
					rect : {
						fill : 'white'
					},
					text : {
						fill : 'black',
						text : $("#source_card").val()
					}
				}
			});
			selectedElement.model.label(1, {
				position : .9,
				attrs : {
					rect : {
						fill : 'white'
					},
					text : {
						fill : 'black',
						text : $("#target_card").val()
					}
				}
			});
			saveGraph();
		}
	}

	function refreshCurrentClass() {
		if (selectedElement != null
				&& selectedElement.model instanceof joint.shapes.uml.Class) {

			var cell = graph.getCell(selectedElement.model.get("id"));
			var height = 35;
			var attributes = [];
			var methods = [];
			$("#attributs").find("input").each(function() {
				attributes[attributes.length] = $(this).val();
				height += 15;
			});

			$("#methods").find("input").each(function() {
				methods[methods.length] = $(this).val();
				height += 15;
			});
			cell.set("attributes", attributes);
			cell.set("methods", methods);
			cell.set("name", $("#className").find("input").val());
			if (cell.get("size").height < height) {
				cell.resize(cell.get("size").width, height);
			}

			updateEditor(selectedElement);
			saveGraph();
		}
	}

	/*
	 * <div class='langage_content'>" + "<input type = 'radio' name='key'
	 * value='Primary' checked >Primary " + "<input type = 'radio' name='key'
	 * value='Foriegn'>Foriegn
	 */

	function addAttribut1(val) {
		var div = $("#attributs").children(".visible_content");
		div.append("<div class='attribut'><input value='" + val + "'/>"
				+ "<div class='delete_elt'>X</div></div>");
		$(".attribut input").unbind();
		$(".attribut input").bind("blur", refreshCurrentClass);
		$(".delete_elt").unbind();
		$(".delete_elt").bind("click", removeElt);
	}

	function addAttribut(val) {
		var div = $("#attributs").children(".visible_content");
		div.append("<div class='attribut'><input value='" + val + "'/>"
		// + "<div class = 'key'><input type = 'radio' name= 'keys'
		// value='Primary'/>pk<input type = 'radio' name= 'keys'
		// value='Foriegn'/>fk</div>"
		+ "<div class='delete_elt'>X</div></div>");
		$(".attribut input").unbind();
		$(".attribut input").bind("blur", refreshCurrentClass);
		$(".delete_elt").unbind();
		$(".delete_elt").bind("click", removeElt);
	}
	function removeElt() {
		$(this).parent().remove();
		key.remove();
		refreshCurrentClass();
	}

	/*
	 * function addKey(val) { var div = $("#keys").children(".visible_content");
	 * div .append("<div class='method'><input type = 'radio' name= 'keys'
	 * value='Primary'/>Primary" + "<input type = 'radio' name= 'keys'
	 * value='Foriegn'/>Foriegn" + "<div class='delete_elt'>X</div></div>");
	 * $(".key input").unbind(); $(".key input").bind("blur",
	 * refreshCurrentClass); }
	 */

	function addMethod(val) {
		var div = $("#methods").children(".visible_content");
		div.append("<div class='method'><input value='" + val + "'/>"
				+ "<div class='delete_elt'>X</div></div>");
		$(".method input").unbind();
		$(".method input").bind("blur", refreshCurrentClass);
		$(".delete_elt").unbind();
		$(".delete_elt").bind("click", removeElt);
	}

	function getDefaultUML(classe, name, x, y) {
		return new classe({
			position : {
				x : x,
				y : y
			},
			size : {
				width : 150,
				height : 100
			},
			name : name,
			attributes : [ 'int userId' ],
			methods : [ 'primaryKey userId' ]
		});
	}

	function addClass() {
		var newClass = new UMLClass();
		classDiagram.addElement(newClass);
		// / / Drawing the diagram c
		lassDiagram.draw();

	}

	var timerSave = null;
	function saveGraph() {
		if (timerSave) {
			clearTimeout(timerSave);
		}
		if (typeof (Storage) !== "undefined") {
			timerSave = window.setTimeout(function() {
				localStorage.setItem("ERD_graph", JSON
						.stringify(graph.toJSON()));
			}, 2000);
		} else {
			if (console)
				console.log("Can't save graph cause storage not supported");
		}

	}

	function restoreGraph() {
		if (typeof (Storage) !== "undefined"
				&& localStorage.getItem("ERD_graph")) {
			graph.fromJSON(eval("(" + localStorage.getItem("ERD_graph") + ")"));
		}
	}

	function generateSource() {
		joint.uml.Generator.langages = [];
		$("input[name='langage[]']:checked")
				.each(
						function() {
							joint.uml.Generator.langages[joint.uml.Generator.langages.length] = $(
									this).val();
						});
		joint.uml.Generator.generate(graph);
	}
	var lastEvent = null;

	function initEditor() {
		$(".delete").mousedown(function(evt) {
			evt.preventDefault();
			evt.stopPropagation();
			graph.getCell(selectedElement.model.get("id")).remove();
			$("#editor").hide();
		});
		$(".duplicate").mousedown(
				function(evt) {
					evt.preventDefault();
					evt.stopPropagation();
					var newCell = graph
							.getCell(selectedElement.model.get("id")).clone();
					graph.addCell(newCell);
					newCell.translate(10, 10);
					$("#editor").hide();
				});

		$(".connect").mousedown(
				function(evt) {
					evt.preventDefault();
					evt.stopPropagation();
					var cell = graph.getCell(selectedElement.model.get("id"));

					var link = paper.getDefaultLink();
					if (link instanceof joint.shapes.uml.Aggregation
							|| link instanceof joint.shapes.uml.Composition
							|| link instanceof joint.shapes.uml.Association) {
						link.label(0, {
							position : .1,
							attrs : {
								rect : {
									fill : 'white'
								},
								text : {
									fill : 'black',
									text : 1
								}
							}
						});
						link.label(1, {
							position : .9,
							attrs : {
								rect : {
									fill : 'white'
								},
								text : {
									fill : 'black',
									text : 1
								}
							}
						});
						$("#source_card").val(1);
						$("#target_card").val(1);
					}
					link.set("source", {
						id : selectedElement.model.get("id")
					});
					link.set("target", paper.snapToGrid({
						x : evt.clientX,
						y : evt.clientY
					}));

					graph.addCell(link, {
						validation : false
					});
					var linkView = paper.findViewByModel(link);
					linkView.startArrowheadMove("target");

					$("body").mouseup(
							function(evt) {
								linkView.pointerup(evt);
								var src = graph.getCell(link.get("source").id);
								var tar = null;
								if (undefined == link.get("target").id) {
									link.remove();
									alert("Please link with valid target");
								} else {
									tar = graph.getCell(link.get("target").id);
									alert("cell.: " + cell.get("methods"));
									// var methods = cell.model.get("methods");
									// for (var i = 0; i < methods.length; i++)
									// {
									// addMethod(methods[i]);
									// }
									var targetCell = graph.getCell(link
											.get("target").id);
									alert("target: "
											+ targetCell.get("methods"));
								}
								$("body").unbind();
							});
					$("body").mousemove(function(evt) {
						var coords = paper.snapToGrid({
							x : evt.clientX,
							y : evt.clientY
						});
						linkView.pointermove(evt, coords.x, coords.y)
					});

					$("#editor").hide();
				});

		$(".n")
				.mousedown(
						function(evt) {
							evt.preventDefault();
							evt.stopPropagation();
							$("body").mouseup(function(evt) {
								evt.preventDefault();
								evt.stopPropagation();
								$("body").unbind();
							});
							$("body")
									.mousemove(
											function(evt) {
												if (lastEvent != null) {
													var cell = graph
															.getCell(selectedElement.model
																	.get("id"));
													var view = $("#"
															+ selectedElement.id);
													var step = Math
															.abs(lastEvent.pageY
																	- evt.pageY) > 10 ? (lastEvent.pageY
															- evt.pageY > 0 ? 10
															: -10)
															: lastEvent.pageY
																	- evt.pageY;
													if (cell.get("size").height > 10
															|| step > 0) {
														cell.translate(0,
																-(step));
														cell
																.resize(
																		cell
																				.get("size").width,
																		cell
																				.get("size").height
																				+ (step));
														updateEditor(selectedElement);
													}
												}
												lastEvent = evt;
											});
						});
		$(".s")
				.mousedown(
						function(evt) {
							evt.preventDefault();
							evt.stopPropagation();
							$("body").mouseup(function(evt) {
								evt.preventDefault();
								evt.stopPropagation();
								$("body").unbind();
							});
							$("body")
									.mousemove(
											function(evt) {
												if (lastEvent != null) {
													var cell = graph
															.getCell(selectedElement.model
																	.get("id"));
													var step = Math
															.abs(lastEvent.pageY
																	- evt.pageY) > 10 ? (lastEvent.pageY
															- evt.pageY > 0 ? 10
															: -10)
															: lastEvent.pageY
																	- evt.pageY;
													if (cell.get("size").height > 10
															|| step < 0) {
														cell
																.resize(
																		cell
																				.get("size").width,
																		cell
																				.get("size").height
																				- (step));
														updateEditor(selectedElement);
													}
												}
												lastEvent = evt;
											});
						});
		$(".e")
				.mousedown(
						function(evt) {
							evt.preventDefault();
							evt.stopPropagation();
							$("body").mouseup(function(evt) {
								evt.preventDefault();
								evt.stopPropagation();
								$("body").unbind();
							});
							$("body")
									.mousemove(
											function(evt) {
												if (lastEvent != null) {
													var cell = graph
															.getCell(selectedElement.model
																	.get("id"));
													var step = Math
															.abs(lastEvent.pageX
																	- evt.pageX) > 10 ? (lastEvent.pageX
															- evt.pageX > 0 ? 10
															: -10)
															: lastEvent.pageX
																	- evt.pageX;

													if (cell.get("size").width > 10
															|| step < 0) {
														cell
																.resize(
																		cell
																				.get("size").width
																				- (step),
																		cell
																				.get("size").height);
														updateEditor(selectedElement);
													}
												}
												lastEvent = evt;
											});
						});
		$(".w")
				.mousedown(
						function(evt) {
							evt.preventDefault();
							evt.stopPropagation();
							$("body").mouseup(function(evt) {
								evt.preventDefault();
								evt.stopPropagation();
								$("body").unbind();
							});
							$("body")
									.mousemove(
											function(evt) {
												if (lastEvent != null) {
													var cell = graph
															.getCell(selectedElement.model
																	.get("id"));
													var step = Math
															.abs(lastEvent.pageX
																	- evt.pageX) > 10 ? (lastEvent.pageX
															- evt.pageX > 0 ? 10
															: -10)
															: lastEvent.pageX
																	- evt.pageX;

													if (cell.get("size").width > 10
															|| step > 0) {
														cell.translate(-(step),
																0);
														cell
																.resize(
																		cell
																				.get("size").width
																				+ (step),
																		cell
																				.get("size").height);
														updateEditor(selectedElement);
													}
												}
												lastEvent = evt;
											});
						});
		$(".se")
				.mousedown(
						function(evt) {
							evt.preventDefault();
							evt.stopPropagation();
							$("body").mouseup(function(evt) {
								evt.preventDefault();
								evt.stopPropagation();
								$("body").unbind();
							});
							$("body")
									.mousemove(
											function(evt) {
												if (lastEvent != null) {
													var cell = graph
															.getCell(selectedElement.model
																	.get("id"));
													var stepX = Math
															.abs(lastEvent.pageX
																	- evt.pageX) > 10 ? (lastEvent.pageX
															- evt.pageX > 0 ? 10
															: -10)
															: lastEvent.pageX
																	- evt.pageX;
													var stepY = Math
															.abs(lastEvent.pageY
																	- evt.pageY) > 10 ? (lastEvent.pageY
															- evt.pageY > 0 ? 10
															: -10)
															: lastEvent.pageY
																	- evt.pageY;

													if (cell.get("size").width > 10
															|| stepX < 0) {
														cell
																.resize(
																		cell
																				.get("size").width
																				- (stepX),
																		cell
																				.get("size").height);
													}
													if (cell.get("size").height > 10
															|| stepY < 0) {
														cell
																.resize(
																		cell
																				.get("size").width,
																		cell
																				.get("size").height
																				- (stepY));
													}
													updateEditor(selectedElement);
												}
												lastEvent = evt;
											});
						});
		$(".sw")
				.mousedown(
						function(evt) {
							evt.preventDefault();
							evt.stopPropagation();
							$("body").mouseup(function(evt) {
								evt.preventDefault();
								evt.stopPropagation();
								$("body").unbind();
							});
							$("body")
									.mousemove(
											function(evt) {
												if (lastEvent != null) {
													var cell = graph
															.getCell(selectedElement.model
																	.get("id"));
													var stepX = Math
															.abs(lastEvent.pageX
																	- evt.pageX) > 10 ? (lastEvent.pageX
															- evt.pageX > 0 ? 10
															: -10)
															: lastEvent.pageX
																	- evt.pageX;
													var stepY = Math
															.abs(lastEvent.pageY
																	- evt.pageY) > 10 ? (lastEvent.pageY
															- evt.pageY > 0 ? 10
															: -10)
															: lastEvent.pageY
																	- evt.pageY;

													if (cell.get("size").width > 10
															|| stepX > 0) {
														cell.translate(-stepX,
																0);
														cell
																.resize(
																		cell
																				.get("size").width
																				+ stepX,
																		cell
																				.get("size").height);
													}
													if (cell.get("size").height > 10
															|| stepY < 0) {
														cell
																.resize(
																		cell
																				.get("size").width,
																		cell
																				.get("size").height
																				- stepY);
													}
													updateEditor(selectedElement);
												}
												lastEvent = evt;
											});
						});
		$(".nw")
				.mousedown(
						function(evt) {
							evt.preventDefault();
							evt.stopPropagation();
							$("body").mouseup(function(evt) {
								evt.preventDefault();
								evt.stopPropagation();
								$("body").unbind();
							});
							$("body")
									.mousemove(
											function(evt) {
												if (lastEvent != null) {
													var cell = graph
															.getCell(selectedElement.model
																	.get("id"));
													var stepX = Math
															.abs(lastEvent.pageX
																	- evt.pageX) > 10 ? (lastEvent.pageX
															- evt.pageX > 0 ? 10
															: -10)
															: lastEvent.pageX
																	- evt.pageX;
													var stepY = Math
															.abs(lastEvent.pageY
																	- evt.pageY) > 10 ? (lastEvent.pageY
															- evt.pageY > 0 ? 10
															: -10)
															: lastEvent.pageY
																	- evt.pageY;

													if (cell.get("size").width > 10
															|| stepX > 0) {
														cell.translate(
																-(stepX), 0);
														cell
																.resize(
																		cell
																				.get("size").width
																				+ (stepX),
																		cell
																				.get("size").height);
													}
													if (cell.get("size").height > 10
															|| stepY > 0) {
														cell.translate(0,
																-(stepY));
														cell
																.resize(
																		cell
																				.get("size").width,
																		cell
																				.get("size").height
																				+ (stepY));
													}
													updateEditor(selectedElement);
												}
												lastEvent = evt;
											});
						});
		$(".ne")
				.mousedown(
						function(evt) {
							evt.preventDefault();
							evt.stopPropagation();
							$("body").mouseup(function(evt) {
								evt.preventDefault();
								evt.stopPropagation();
								$("body").unbind();
							});
							$("body")
									.mousemove(
											function(evt) {
												if (lastEvent != null) {
													var cell = graph
															.getCell(selectedElement.model
																	.get("id"));
													var stepX = Math
															.abs(lastEvent.pageX
																	- evt.pageX) > 10 ? (lastEvent.pageX
															- evt.pageX > 0 ? 10
															: -10)
															: lastEvent.pageX
																	- evt.pageX;
													var stepY = Math
															.abs(lastEvent.pageY
																	- evt.pageY) > 10 ? (lastEvent.pageY
															- evt.pageY > 0 ? 10
															: -10)
															: lastEvent.pageY
																	- evt.pageY;

													if (cell.get("size").width > 10
															|| stepX < 0) {
														cell
																.resize(
																		cell
																				.get("size").width
																				- (stepX),
																		cell
																				.get("size").height);
													}
													if (cell.get("size").height > 10
															|| stepY > 0) {
														cell.translate(0,
																-(stepY));
														cell
																.resize(
																		cell
																				.get("size").width,
																		cell
																				.get("size").height
																				+ (stepY));
													}
													updateEditor(selectedElement);
												}
												lastEvent = evt;
											});
						});
	}

	function updateEditor(cell) {
		if (cell != null) {
			$("#association").show();
			$("#attributs").show();
			$("#methods").show();
			$("#className").show();
			$("#attributs").children(".visible_content").html("");
			$("#methods").children(".visible_content").html("");

			var attributes = cell.model.get("attributes");

			for (var i = 0; i < attributes.length; i++) {
				addAttribut(attributes[i]);
			}
			var methods = cell.model.get("methods");
			for (var i = 0; i < methods.length; i++) {
				addMethod(methods[i]);
			}

			$("#className").find("input").val(cell.model.get("name"));
			$("#editor").css(
					"top",
					($("#" + cell.id).offset().top - 2 + $("#classDiagram")
							.scrollTop())
							+ "px");
			$("#editor").css(
					"left",
					($("#" + cell.id).offset().left
							- $("#classDiagram").offset().left - 2 + $(
							"#classDiagram").scrollLeft())
							+ "px");

			$("#editor").width(
					(cell.model.get("size").width + 2) * currentScale);
			$("#editor").height(
					(cell.model.get("size").height + 2) * currentScale);
			$("#editor").show();
		}
	}

	function changeDefaultLink() {
		switch (parseInt($("#links").val())) {
		case 1:
			paper.options.defaultLink = new joint.shapes.uml.Composition;
			break;
		case 2:
			paper.options.defaultLink = new joint.shapes.uml.Aggregation;
			break;
		case 3:
			paper.options.defaultLink = new joint.shapes.uml.Generalization;
			break;
		case 4:
			paper.options.defaultLink = new joint.shapes.uml.Implementation;
			break;
		default:
			paper.options.defaultLink = new joint.shapes.uml.Association;

		}
	}

	function validateLink(src, tar, linkType) {
		alert("src: " + src + " tar: " + tar + " linkType: " + linkType);
		if (null != src && "uml.Class" == src && null != tar
				&& "uml.Class" == tar && "uml.Implementation" == linkType) {
			alert("Not valid class Realtion");
		}
		if (null != src && "uml.Class" == src && null != tar
				&& "uml.Interface" == tar && "uml.Implementation" != linkType) {
			alert("Not valid Interfcae Realtion");
		}
		if (null != src && "uml.Interface" == src && null != tar
				&& "uml.Interface" == tar && "uml.Generalization" != linkType) {
			alert("Not valid Interfcae Realtion");
		}
		if (null != src && "uml.Interface" == src && null != tar
				&& "uml.Class" == tar) {
			alert("Not valid Interfcae Realtion");
		}
	}

	function validateField(field) {
		var isValid = true;
		$(field).each(function() {
			if ($.trim($(this).val()) == '') {
				isValid = false;
				$(this).css({
					"border" : "1px solid red",
					"background" : "#FFCECE"
				});
			} else {
				$(this).css({
					"border" : "",
					"background" : ""
				});
			}
		});
		if (isValid == false)
			e.preventDefault();
	}

	function validateGraph() {
		String
		jsonString = JSON.stringify(graph);
		var obj = jQuery.parseJSON(jsonString);
		if (null == obj.cells || obj.cells == "") {
			alert("Please draw an Entity!");
			e.preventDefault();
			return false;
		} else {
			return true;
		}
	}
	function validateDuplicateElements() {
		var name = '';
		var arr = [];
		var jsonString = JSON.stringify(graph);
		var obj = $.parseJSON(jsonString);
		$.each(obj.cells, function() {
			arr.push(this['name']);
		});
		var sorted_arr = arr.slice().sort();
		var results = [];
		for (var i = 0; i < arr.length - 1; i++) {
			if (sorted_arr[i + 1] == sorted_arr[i]) {
				results.push(sorted_arr[i]);
			}
		}
		if (results.length == 0)
			return true;
		else {
			alert("There are " + results.length
					+ " duplicate element/s, Please rename!");
			e.preventDefault();
			return false;
		}
	}

})();