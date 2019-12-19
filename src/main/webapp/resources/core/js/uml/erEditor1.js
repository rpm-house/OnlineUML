var graph, paper;
(function() {
	var erd;

	var currentScale = 1;
	var selectedElement = null;

	$(document)
			.ready(
					function() {
						erd = joint.shapes.erd;
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
											var result;
											var fileName = $("#import_text")
													.val();
											// alert("fileName: " + fileName)
											if (fileName == "") {
												fileName = "D:\\projects\\KBE\\uml_Json\\classDiagram1.txt";
											}
											var url = "http://localhost:8008/UMLWeb/read?data="
													+ fileName;

											$.ajax({
												url : url,
												success : function(data) {
													// $('#response').html(data);
													// alert("data:" + data);
													result = data;
													// alert("result:" +
													// result);
													$(".mask").hide();
													$("#import_content")
															.fadeOut();
													// alert("result1: " +
													// result)
													return graph.fromJSON(JSON
															.parse(result))
												}
											});
										});

						$("#save").click(function() {
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

											var fileName = $("#save_text")
													.val();
											// alert("fileName: " + fileName)
											if (fileName == "") {
												fileName = "D:\\projects\\KBE\\uml_Json\\classDiagram1.txt";
											}
											var jsonString = JSON
													.stringify(graph);
											jsonString = jsonString
													+ "&fileName=" + fileName
											// alert(jsonString);
											var url = "http://localhost:8008/UMLWeb/save/";
											$
													.ajax({
														url : url,
														contentType : "application/json",
														type : 'post',
														data : jsonString,
														dataType : 'json',
														error : function(data) {
															// alert(data);
															// alert(url);
															display(data);
														}
													});

											$(".saveMask").hide();
											$("#save_content").fadeOut();
										});

						$("#new").click(function() {
							graph.clear();
						});

						/*
						 * $("#add_attribut").click(function() {
						 * addAttribut(""); });
						 * $("#add_method").click(function() { addMethod("");
						 * });
						 */
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
							defaultLink : new erd.Line,
							gridSize : 1,
							model : graph
						});

						entities = {
							entity : getDefaultERD(erd.Entity, "Entity", 0, 0)
						/*
						 * , key : getDefaultERD(erd.Key, "Key", 120, 0)
						 */
						/*
						 * weakEntity : getDefaultERD(erd.WeakEntity,
						 * "WeakEntity", 0, 120), relationship :
						 * getDefaultERD(erd.Relationship, "Relationship", 120,
						 * 0), identifyingRelationship : getDefaultERD(
						 * erd.IdentifyingRelationship,
						 * "IdentifyingRelationship", 120, 120), attribute :
						 * getDefaultERD(erd.Attribute, "Attribute", 0, 250)
						 */

						};
						_.each(entities, function(c) {
							tools.addCell(c);
						});

						// restoreGraph();

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
											var umlElt = getDefaultERD(
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
																	var finalUmlElt = getDefaultERD(
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
											$("#methods").hide();
											$("#className").hide();
											$("#association").hide();

											if (cellView.model instanceof joint.shapes.erd.Line) {
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
											} else if (cellView.model instanceof joint.shapes.erd.Entity) {
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
											if (cellView.model instanceof joint.shapes.erd.Line) {
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
											} else if (cellView.model instanceof joint.shapes.erd.Entity) {
												updateEditor(cellView);
											}
										});
					});

	function refreshCurrentLink() {
		if (selectedElement != null
				&& (selectedElement.model instanceof joint.shapes.erd.Line)) {
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
				&& selectedElement.model instanceof joint.shapes.erd.Entity) {

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

	function getDefaultERD(classe, name, x, y) {
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
			attributes : [ '+ attr1 : Integer' ],
			methods : [ '+ method1(): Void' ]
		});
	}

	function addEntity() {
		var newEntity = new erd.Entity();
		classDiagram.addElement(newEntity);
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
			}, 2000);
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

		$(".connect").mousedown(function(evt) {
			evt.preventDefault();
			evt.stopPropagation();
			var cell = graph.getCell(selectedElement.model.get("id"));

			var link = paper.getDefaultLink();
			if (link instanceof joint.shapes.erd.Line) {
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

			$("body").mouseup(function(evt) {
				linkView.pointerup(evt);
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
		paper.options.defaultLink = new joint.shapes.erd.Line;
	}
})();