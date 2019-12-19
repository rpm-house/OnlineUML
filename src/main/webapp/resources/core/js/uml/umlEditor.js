var graph, paper;
// graph.setSrc("Class");
var urlPrefix = "http://localhost:8022/UMLEngineering/";
(function() {
	var uml;

	SVGElement.prototype.getTransformToElement = SVGElement.prototype.getTransformToElement
			|| function(toElement) {
				return toElement.getScreenCTM().inverse().multiply(
						this.getScreenCTM());
			};

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

						function renderSaveToDrive() {
							var fileName1 = $("#file_Gtext").val();
							var fileName = "D:\\biz\\uml\\" + fileName + ".txt";
							/*
							 * var jsonString = JSON.stringify(graph);
							 * 
							 * var fso = new ActiveXObject(
							 * "Scripting.FileSystemObject"); var fh =
							 * fso.CreateTextFile(fileName, true);
							 * fh.WriteLine(jsonString); fh.Close();
							 */

							gapi.savetodrive
									.render(
											'savetodrive-div',
											{
												src : 'https://www.visual-paradigm.com/tutorials/reverse-ddl.jsp',
												filename : fileName1,
												sitename : 'UML Engineering'
											});
						}
						document.getElementById('render-link')
								.addEventListener('click', renderSaveToDrive);

						$("#render-link").click(function() {
							$(".saveGMask").show();
							$("#save_Gcontent").fadeIn();
						});

						$("#cancel_Google").click(function() {
							$(".saveGMask").hide();
							$("#save_Gcontent").fadeOut();
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
										function(evt) {

											validateField('#import_text');

											var result;
											var fileName = $("#import_text")
													.val();
											alert("fileName: " + fileName)
											
											
											if (fileName == "") {
												fileName = "D:\\projects\\KBE\\uml_Json\\classDiagram1.txt";
											}
											
											
												/*//alert("Chrome: "+fileName);
												 var value = $('input#import_text')[0];
												 //alert('value:'+value)
												 var file = value.files[0];
												// alert("file: "+file);
												 
												 var fr = new FileReader();
											      fr.onload = receivedText;
											      //fr.readAsText(file);
											      //alert('fr:'+fr)
											    

											    function receivedText(e) {
											      lines = e.target.result;
											      var newArr = JSON.parse(lines); 
											      alert('newArr:'+newArr)
											      $(".mask").hide();
													$("#import_content")
															.fadeOut();
													  graph
														.fromJSON(JSON
																.parse(lines));
											    }
											saveGraph();
											alert('gr:'+graph)
											$(".mask").hide();
															$("#import_content")
																	.fadeOut();
											return graph;
											*/
											
											
											var url = urlPrefix + "read?data="
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
															$(".mask").hide();
															$("#import_content")
																	.fadeOut();
															alert("result1: "
																	+ data);

															var obj = $
																	.parseJSON(data);

															var srcType = obj.srcType;

															if (null != srcType
																	&& srcType == "Class") {

																graph
																		.fromJSON(JSON
																				.parse(data));
																saveGraph();
																return graph;
															} else {
																alert("Entity diagram can not be opened in UML Editor");
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

						function testOpen() {
							alert("hh");
						}

						/*
						 * function openFiles() { var result; alert("fileName: " +
						 * "Hao") var fileNameNo = $("#open_text_id").val(); var
						 * fileName = $("#open_text" + fileNameNo).val();
						 * 
						 * alert("fileName: " + fileName); if (fileName == "") {
						 * fileName =
						 * "D:\\projects\\KBE\\uml_Json\\classDiagram1.txt"; }
						 * var url = urlPrefix + "read?data=" + fileName;
						 * 
						 * $.ajax({ url : url, success : function(data) { //
						 * $('#response').html(data); // alert("data:" + data);
						 * result = data; // alert("result:" + // result);
						 * $(".mask").hide(); $("#import_content").fadeOut();
						 * $(".resultMask").show();
						 * $("#result_content").fadeIn();
						 * 
						 * $("#result_text").html(data); // alert("result1: " + //
						 * result) return graph.fromJSON(JSON.parse(result)) }
						 * }); }
						 */
						$("#save").click(function() {

							/*
							 * showModalDialog("index", "",
							 * "width:400px;height:400px;resizeable:yes;");
							 */
							
							  var isUserLogged = $("#isUserLogged").val(); 
							  alert("isUserLogged:" +  isUserLogged);
									  var n =  "true".localeCompare(isUserLogged); 
							   alert("n = " +  n); 
							  if (n > 0) { window.location = urlPrefix +
							  'index'; }
							 
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
											validateField('#save_text, #savePath_text');
											var fileLocation = $(
													"#savePath_text").val();
											var fileName = $("#save_text")
													.val();

											fileName = fileLocation + fileName
													+ ".json";
											alert("fileName: " + fileName)
											if (fileName == "") {
												fileName = "D:\\projects\\KBE\\uml_Json\\classDiagram1.txt";
											}
											graph.setSrc("Class");
											var jsonString = JSON
													.stringify(graph);
											saveGraph();
											/*
											 * jsonString = jsonString +
											 * "^fileName=" + fileName
											 */
											alert(jsonString);
											var url = urlPrefix
													+ "save?fileName="
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

						$("#ok_result").click(function() {
							$(".resultMask").hide();
							$("#result_content").fadeOut();
						});
						/*
						 * $("#save").click(function() {
						 * 
						 * var jsonString = JSON.stringify(graph);
						 * alert(jsonString);
						 * 
						 * var url = "http://localhost:8008/UMLWeb/save/";
						 * $.ajax({ url : url, contentType : "application/json",
						 * type : 'post', data : jsonString, dataType : 'json',
						 * error : function(data) { alert(data); alert(url);
						 * display(data); } }); });
						 */

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

											fileName = fileLocation + fileName
													+ ".xmi";

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
															alert("result1: "
																	+ result);
															return graph
																	.fromJSON(JSON
																			.parse(result))
														}
													});
										});

						$("#forward").click(function() {
							validateGraph();
							validateDuplicateElements();
							$(".forwardMask").show();
							$("#forward_content").fadeIn();
							
							/*
							 * if(navigator.userAgent.indexOf("Chrome") != -1 ) {
							 * $("#browseForOthers").show();
							 * $("#browseForIE").hide(); } else {
							 * $("#browseForIE").show();
							 * $("#browseForOthers").hide(); }
							 */
							
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
											// alert("fileName: " +
											// fileName)
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

														}
													});
										});

						$("#new").click(function() {
							graph.clear();
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
						$("#codeSnippet").click(function() {
							$(".codeSnippetMask").show();
							$("#codeSnippet_content").fadeIn();

						});
						$("#cancel_codeSnippet").click(function() {
							$(".codeSnippetMask").hide();
							$("#codeSnippet_content").fadeOut();
						});
						$("#submit_codeSnippet").click(function() {

						});

						/*
						 * $("#openSvg").click( function() { alert(hai); var
						 * svgDoc = paper.svg; alter("doc : " + svgDoc); var
						 * serializer = new XMLSerializer(); var svgString =
						 * serializer .serializeToString(svgDoc); alter("String : " +
						 * svgString) return svgString; });
						 */
						$("#add_attribut").click(function() {
							addAttribut("");
						});
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

						$("#restore").click(restoreGraph);
						$("#openPng").click(openPngImage);
						$("#generateSource").click(generateSource);
						$("#links").change(changeDefaultLink);

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

						$("#openSvg")
								.click(
										function() {
											alert("hai");
											var svgDoc = paper.svg;
											var serializer = new XMLSerializer();
											var svgString = serializer
													.serializeToString(svgDoc);
											alert("hai SVG" + svgString);

											var canvas = document
													.getElementById('canvas');
											var ctx = canvas.getContext('2d');

											var data = '<svg xmlns="http://www.w3.org/2000/svg" width="200" height="200">'
													+ '<foreignObject width="100%" height="100%">'
													+ '<div xmlns="http://www.w3.org/1999/xhtml" style="font-size:40px">'
													+ '<em>I</em> like '
													+ '<span style="color:white; text-shadow:0 0 2px blue;">'
													+ 'cheese</span>'
													+ '</div>'
													+ '</foreignObject>'
													+ '</svg>';

											var DOMURL = window.URL
													|| window.webkitURL
													|| window;

											var img = new Image();
											var svg = new Blob(
													[ svgString ],
													{
														type : 'image/svg+xml;charset=utf-8'
													});
											var url = DOMURL
													.createObjectURL(svg);

											img.onload = function() {
												ctx.drawImage(img, 0, 0);
												DOMURL.revokeObjectURL(url);
											}

											img.src = url;

										});

						classes = {

							interface : getDefaultUML(uml.Interface,
									"Interface", 70, 0),
							/*
							 * abstract : getDefaultUML(uml.Abstract,
							 * "Abstract", 130, 0),
							 */
							class : getDefaultUML(uml.Class, "Class", 70, 110)
						/*
						 * , package : getDefaultUML(uml.Package, "Package", 70,
						 * 210)
						 */

						};
						_.each(classes, function(c) {
							tools.addCell(c);
						});
						// Need to remove
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
						/*
						 * var classes = {
						 * 
						 * mammal: new uml.Interface({ position: { x:300 , y: 50 },
						 * size: { width: 240, height: 100 }, name: 'Mammal',
						 * attributes: ['dob: Date'], methods: ['+
						 * setDateOfBirth(dob: Date): Void','+ getAgeAsDays():
						 * Numeric','+ isOk(): Bool'] }),
						 * 
						 * person: new uml.Abstract({ position: { x:300 , y: 250 },
						 * size: { width: 240, height: 100 }, name: 'Person',
						 * attributes: ['-firstName: String','-lastName:
						 * String'], methods: ['+ setName(first: String, last:
						 * String): Void','+ getName(): String'] }),
						 * 
						 * bloodgroup: new uml.Class({ position: { x:20 , y: 190 },
						 * size: { width: 220, height: 100 }, name:
						 * 'BloodGroup', attributes: ['bloodGroup: String'],
						 * methods: ['+ isCompatible(bG: String): Boolean'] }),
						 * 
						 * address: new uml.Class({ position: { x:630 , y: 190 },
						 * size: { width: 160, height: 100 }, name: 'Address',
						 * attributes: ['houseNumber: Integer','streetName:
						 * String','town: String','postcode: String'], methods: []
						 * }),
						 * 
						 * man: new uml.Class({ position: { x:190 , y: 400 },
						 * size: { width: 180, height: 50 }, name: 'Man' }),
						 * 
						 * woman: new uml.Class({ position: { x:470 , y: 400 },
						 * size: { width: 180, height: 50 }, name: 'Woman',
						 * methods: ['+ giveABrith(): Person []'] }) };
						 * 
						 * _.each(classes, function(c) { graph.addCell(c); });
						 * var relations = [ new uml.Generalization({ source: {
						 * id: classes.man.id }, target: { id: classes.person.id
						 * },router: { name: 'manhattan' }}), new
						 * uml.Generalization({ source: { id: classes.woman.id },
						 * target: { id: classes.person.id },router: { name:
						 * 'manhattan' }}), new uml.Implementation({ source: {
						 * id: classes.person.id }, target: { id:
						 * classes.mammal.id },router: { name: 'manhattan' }}),
						 * new uml.Aggregation({ source: { id: classes.person.id },
						 * target: { id: classes.address.id }, router: { name:
						 * 'manhattan' }, labels : [ { position: .1, attrs: {
						 * rect: { fill: 'white' }, text: { fill: 'black', text:
						 * "*" }}}, { position: .9, attrs: { rect: { fill:
						 * 'white' }, text: { fill: 'black', text: "1" }}} ] }),
						 * new uml.Composition({ source: { id: classes.person.id },
						 * target: { id: classes.bloodgroup.id }, router: {
						 * name: 'manhattan' }, labels : [ { position: .1,
						 * attrs: { rect: { fill: 'white' }, text: { fill:
						 * 'black', text: "1" }}}, { position: .9, attrs: {
						 * rect: { fill: 'white' }, text: { fill: 'black', text:
						 * "1" }}} ]}), new uml.Association({ source: { id:
						 * classes.man.id }, target: { id: classes.woman.id },
						 * router: { name: 'manhattan' }, labels : [ { position:
						 * .1, attrs: { rect: { fill: 'white' }, text: { fill:
						 * 'black', text: "1" }}}, { position: .9, attrs: {
						 * rect: { fill: 'white' }, text: { fill: 'black', text:
						 * "1" }}} ]}) ];
						 * 
						 * _.each(relations, function(r) { graph.addCell(r); });
						 */
						paper
								.on(
										'cell:pointerclick',
										function(cellView, evt, x, y) {
											$("#attributs").hide();
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
			selectedElement.model.label(2, {
				sourceVal : $("#source_card").val(),
				targetVal : $("#target_card").val()
			});

			alert($("#source_card").val());
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

	function addAttribut(val) {
		var div = $("#attributs").children(".visible_content");
		div.append("<div class='attribut'><input value='" + val
				+ "'/><div class='delete_elt'>X</div></div>");
		$(".attribut input").unbind();
		$(".attribut input").bind("blur", refreshCurrentClass);
		$(".delete_elt").unbind();
		$(".delete_elt").bind("click", removeElt);
	}
	function removeElt() {
		$(this).parent().remove();
		refreshCurrentClass();
	}

	function addMethod(val) {
		var div = $("#methods").children(".visible_content");
		div.append("<div class='method'><input value='" + val
				+ "' /><div class='delete_elt'>X</div></div>");
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
				width : 110,
				height : 100
			},
			name : name,
			attributes : [ 'int attr1' ],
			methods : [ 'void method1()' ]
		});
	}

	function addClass() {
		var newClass = new UMLClass();
		classDiagram.addElement(newClass);
		// Drawing the diagram
		classDiagram.draw();

	}

	var timerSave = null;
	function saveGraph() {
		if (timerSave) {
			clearTimeout(timerSave);
		}
		if (typeof (Storage) !== "undefined") {
			timerSave = window.setTimeout(function() {
				localStorage.setItem("graph", JSON.stringify(graph.toJSON()));
			}, 20);
		} else {
			if (console)
				console.log("Can't save graph cause storage not supported");
		}
	}

	function saveUserGraph(graphId) {

		if (typeof (Storage) !== "undefined") {
			localStorage.setItem(graphId, JSON.stringify(graph.toJSON()));
		} else {
			if (console)
				console.log("Can't save graph cause storage not supported");
		}
	}

	function restoreGraph() {
		if (typeof (Storage) !== "undefined" && localStorage.getItem("graph")) {
			graph.fromJSON(eval("(" + localStorage.getItem("graph") + ")"));
		}
	}

	function getUserGraph(graphId) {
		if (typeof (Storage) !== "undefined" && localStorage.getItem(graphId)) {
			graph.fromJSON(eval("(" + localStorage.getItem(graphId) + ")"));
		}
	}

	function openPngImage() {
		alert("Jakkamma");
		var windowFeatures = 'menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes';
		var windowName = _.uniqueId('png_output');
		var imageWindow = window.open('', windowName, windowFeatures);

		paper.toPNG(function(dataURL) {
			imageWindow.document.write('<img src="' + dataURL + '"/>');
		}, {
			padding : 10
		});
	}

	$('#openJpg')
			.on(
					'click',
					function() {

						var windowFeatures = 'menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes';
						var windowName = _.uniqueId('jpg_output');
						var imageWindow = window.open('', windowName,
								windowFeatures);

						paper.toJPEG(function(dataURL) {
							imageWindow.document.write('<img src="' + dataURL
									+ '"/>');
						}, {
							padding : 10,
							quality : .7
						});
					});

	function generateSource() {
		joint.uml.Generator.languages = [];
		$("input[name='language[]']:checked")
				.each(
						function() {
							joint.uml.Generator.languages[joint.uml.Generator.languages.length] = $(
									this).val();
						});
		joint.uml.Generator.generate(graph);
		saveGraph();
		$(".codeTemplateMask").hide();
		$("#codeTemplate_content").fadeOut();

	}

	var lastEvent = null;

	function allowDrop(ev) {
		// ev.preventDefault();
		alert("allowDrop");
	}

	function drag(ev) {
		alert("drag");
		// ev.dataTransfer.setData("text", ev.target.id);
	}

	function drop(ev) {
		// ev.preventDefault();
		/*
		 * var data = ev.dataTransfer.getData("text");
		 * ev.target.appendChild(document.getElementById(data));
		 */
		alert("drop");
	}

	$(".link-tool").drag(function(evt) {
		alert("drag1");
	});

	/*
	 * function drop(ev) { evt.preventDefault(); evt.stopPropagation();
	 * validateLink(); }
	 */
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

								var src = graph.getCell(link.get("source").id)
										.get("type");
								var tar = null;
								if (undefined == link.get("target").id) {
									link.remove();
									alert("Please link with valid target");
								} else {
									tar = graph.getCell(link.get("target").id)
											.get("type");
								}

								validateLink(src, tar, link);
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

	function validateLink(src, tar, link) {
		var linkType = link.get("type");
		// alert("src: " + src + " tar: " + tar + " linkType: " + linkType);
		if (null != src && "uml.Class" == src && null != tar
				&& "uml.Class" == tar && "uml.Implementation" == linkType) {
			alert("Not valid class Realtion");
			link.remove();
		}
		if (null != src && "uml.Class" == src && null != tar
				&& "uml.Interface" == tar && "uml.Implementation" != linkType) {
			alert("Not valid Interfcae Realtion");
			link.remove();
		}
		if (null != src && "uml.Interface" == src && null != tar
				&& "uml.Interface" == tar && "uml.Generalization" != linkType) {
			alert("Not valid Interfcae Realtion");
			link.remove();
		}
		if (null != src && "uml.Interface" == src && null != tar
				&& "uml.Class" == tar) {
			alert("Not valid Interfcae Realtion");
			link.remove();
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
			alert("Please draw a Class or an Interface!");
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
			if (this['name'] != undefined && this['name'] != null) {
				arr.push(this['name']);
			}
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