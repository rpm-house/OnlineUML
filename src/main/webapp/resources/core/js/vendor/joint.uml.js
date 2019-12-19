joint.uml = {};

// Template used for generate java sources
joint.uml.xmiTemplate = [
		"package <%= package %>;",
		"<% _.each(imports,function(file,key,list){ ",
		"%>import <%= package %>.<%= file %>;",
		"<% }); %>",
		"public <%= type %> <%= name %> <%= options %>",
		"{",
		"<% _.each(attributes,function(attribute,key,list){ %>",
		"	<%= attribute.visibility %> <%= attribute.type %> <%= attribute.name %>;",
		"<% }); %>",
		"<% _.each(methods,function(method,key,list){ %>",
		"	<%= method.visibility %> <%= method.returnType %> <%= method.name %>(<%= method.params %>)<%",
		" if(type != 'interface'){%>",
		"	{",
		"",
		"	}<% } %>;",
		"<% }); %>",
		"<% if(type != 'interface'){%>",
		"<% _.each(attributes,function(attribute,key,list){ %>",
		"	public <%= attribute.type %> <%= attribute.getter %>()",
		"	{",
		"		return this.<%= attribute.name %>;",
		"	}",
		"	public void <%= attribute.setter %>(<%= attribute.type %> <%= attribute.name %>)",
		"	{", "		this.<%= attribute.name %> = <%= attribute.name %>;", "	}",
		"<% }); %>", "<% } %>", "}" ].join("\n");

// Template used for generate java sources
joint.uml.javaTemplate = [
		"package <%= package %>;",
		"<% _.each(imports,function(file,key,list){ ",
		"%>import <%= package %>.<%= file %>;",
		"<% }); %>",
		"public <%= type %> <%= name %> <%= options %>",
		"{",
		"<% _.each(attributes,function(attribute,key,list){ %>",
		"	<%= attribute.visibility %> <%= attribute.type %> <%= attribute.name %> <%= attribute.intialize %>;",
		"<% }); %>",
		"<% _.each(methods,function(method,key,list){ %>",
		"	<%= method.visibility %> <%= method.returnType %> <%= method.name %>(<%= method.params %>)<%",
		" if(type != 'interface'){%>",
		"	{",
		"",
		"	}<% } %>",
		"<% }); %>",
		"<% if(type != 'interface'){%>",
		"<% _.each(attributes,function(attribute,key,list){ %>",
		"	public <%= attribute.type %> <%= attribute.getter %>()",
		"	{",
		"		return this.<%= attribute.name %>;",
		"	}",
		"	public void <%= attribute.setter %>(<%= attribute.type %> <%= attribute.name %>)",
		"	{", "		this.<%= attribute.name %> = <%= attribute.name %>;", "	}",
		"<% }); %>", "<% } %>", "}" ].join("\n");

// Template used for generate VB sources
joint.uml.vbTemplate = [
		"Namespace <%= package %>;",
		"<% _.each(imports,function(file,key,list){ ",
		"%>import <%= package %>.<%= file %>;",
		"<% }); %>",
		"public <%= type %> <%= name %> <%= options %>",
		"{",
		"<% _.each(attributes,function(attribute,key,list){ %>",
		"	<%= attribute.visibility %> <%= attribute.type %> <%= attribute.name %>;",
		"<% }); %>",
		"<% _.each(methods,function(method,key,list){ %>",
		"	<%= method.visibility %> <%= method.returnType %> <%= method.name %>(<%= method.params %>)<%",
		" if(type != 'interface'){%>",
		"	{",
		"",
		"	}<% } %>;",
		"<% }); %>",
		"<% if(type != 'interface'){%>",
		"<% _.each(attributes,function(attribute,key,list){ %>",
		"	public <%= attribute.type %> <%= attribute.getter %>()",
		"	{",
		"		return this.<%= attribute.name %>;",
		"	}",
		"	public void <%= attribute.setter %>(<%= attribute.type %> <%= attribute.name %>)",
		"	{", "		this.<%= attribute.name %> = <%= attribute.name %>;", "	}",
		"<% }); %>", "<% } %>", "}" ].join("\n");

// Template used for generate php sources
joint.uml.phpTemplate = [
		"<?php",
		"<% _.each(imports,function(file,key,list){ ",
		"%>require_once \"<%= file %>.php\";",
		"<% }); %>",
		"<%= type %> <%= name %> <%= options %>",
		"{",
		"<% _.each(attributes,function(attribute,key,list){ ",
		"%>	<%= attribute.visibility %> $<%= attribute.name %>;",
		"<% }); %>",
		"<% _.each(methods,function(method,key,list){ ",
		"%>	<%= method.visibility %> function <%= method.name %>(<%= method.params %>)<%",
		" if(type != 'interface'){%>", "	{", "", "	}<% }else{ %>;<% } %>",
		"<% }); %>", "<% if(type != 'interface'){%>",
		"<% _.each(attributes,function(attribute,key,list){ %>",
		"	public function <%= attribute.getter %>()", "	{",
		"		return $this-><%= attribute.name %>;", "	}",
		"	public function <%= attribute.setter %>($<%= attribute.name %>)",
		"	{", "		$this-><%= attribute.name %> = $<%= attribute.name %>;", "	}",
		"<% }); %>", "<% } %>", "}" ].join("\n");

// Template used for generate ios header sources
joint.uml.objChTemplate = [
		"<% _.each(imports,function(file,key,list){ ",
		"%>#import \"<%= file %>.h\";",
		"<% }); %>",
		"@<%= type %> <%= name %> <%= options %>",
		"<% if(type == 'interface'){%>{<% _.each(attributes,function(attribute,key,list){%>",
		"<% if(attribute.visibility == 'private') { %>	<%= attribute.type %> <%= attribute.name %>;\n<% }",
		"});%>}\n<%} ",
		" _.each(attributes,function(attribute,key,list){ ",
		" if(attribute.visibility != 'private') { %>	@property <%= attribute.type %> <%= attribute.name %>;\n<% }",
		" }); %>",
		"<% _.each(methods,function(method,key,list){ %>",
		"<% if(method.visibility != 'private') { %>	-(<%= method.returnType %>)<%= method.name %>:<%= method.params %>;<% } %>",
		"<% }); %>", "@end" ].join("\n");
// Template used for generate ios content sources
joint.uml.objCmTemplate = [
		"#import \"<%= name %>.h\"\n",
		"@implementation <%= name %>",
		"<% _.each(attributes,function(attribute,key,list){ ",
		" if(attribute.visibility != 'private') { %>	@synthesize <%= attribute.name %> = _<%= attribute.name %>;\n<% }",
		" }); %>",
		"<% _.each(methods,function(method,key,list){ %>",
		"	-(<%= method.returnType %>)<%= method.name %>:<%= method.params %>{}",
		"<% }); %>", "@end" ].join("\n");
// Template used for generate ios swift sources
joint.uml.swiftTemplate = [
		"\n\n<%= type %> <%= name %> <%= options %>\n{",
		"<% _.each(attributes,function(attribute,key,list){ %>",
		"	var <%= attribute.name %> : <%= attribute.type %>",
		"<% }); %>",
		"	init(<%= constructor %>)\n	{",
		"<% _.each(attributes,function(attribute,key,list){ %>",
		"	self.<%= attribute.name %> = <%= attribute.name %>",
		"<% }); %>",
		"	}",
		"<% _.each(methods,function(method,key,list){ %>",
		"	func <%= method.name %>(<%= method.params %>) <%= method.returnType %>{}",
		"<% }); %>", "}" ].join("\n");

// Bean to represent a class
joint.uml.ClassModel = function() {
	this.name = "", this.constructor = "", this.imports = [];
	this.type = "", this.options = "", // extend or implement class
	this.attributes = [], this.methods = []
}

// Bean to represent an attribut
joint.uml.AttributeModel = function() {
	this.visibility = "", this.type = "", this.getter = "", this.setter = "",
			this.name = ""

}

// Bean to represent a method
joint.uml.MethodModel = function() {
	this.visibility = "", this.returnType = "", this.params = "",
			this.name = ""
};

joint.uml.Generator = {
	languages : [ "java", "vb", "objC", "php", "swift" ],// java languages to
	// generate
	primitives : [ "int", "long", "float", "double", "char", "string", "bool",
			"boolean" ],
	objCEquivalent : {
		"Integer" : "NSInteger",
		"Long" : "NSNumber*",
		"Float" : "NSNumber*",
		"Double" : "NSNumber*",
		"String" : "NSString*",
		"Date" : "NSDate*",
		"Numeric" : "NSNumber*",
		"Bool" : "BOOL",
		"Void" : "void"
	},
	swiftEquivalent : {
		"Integer" : "Int",
		"Long" : "Int32",
		"Date" : "NSDate",
		"Numeric" : "Double",
		"Boolean" : "Bool",
		"Void" : ""
	},

	isPrimitive : function(text) {
		for (var i = 0; i < this.primitives.length; i++) {
			if (this.primitives[i].trim() == text) {
				return true;
			}
		}
		return false;
	},
	/**
	 * Simple method to capitalize string
	 */
	capitalize : function(string) {
		return string.charAt(0).toUpperCase() + string.slice(1);
	},
	/**
	 * Generate model sources for all language enabled
	 */
	generate : function(graph) {
		var zip = new JSZip();
		zip.file("diagram.txt", JSON.stringify(graph.toJSON()));// save diagram
		// as json
		// (backup)
		var classes = graph.getElements();
		for (var i = 0; i < classes.length; i++) {
			var classe = classes[i];
			for (var j = 0; j < this.languages.length; j++) {
				var language = this.languages[j];
				var folder = zip.folder(language);
				console.log(language + " class " + classe);
				switch (language) {
				case "java":
					folder.file(classe.get("name") + ".java", this
							.generateJava(graph, classe));
					break;
				case "vb":
					folder.file(classe.get("name") + ".vb", this.generateVB(
							graph, classe));
					break;
				case "php":
					folder.file(classe.get("name") + ".php", this.generatePhp(
							graph, classe));
					break;
				case "swift":
					folder.file(classe.get("name") + ".swift", this
							.generateSwift(graph, classe));
					break;
				case "objC":
					folder.file(classe.get("name") + ".h", this.generateObjCh(
							graph, classe));
					if (classe instanceof joint.shapes.uml.Interface) {
						// protocol doesn't have .m file
					} else {
						folder.file(classe.get("name") + ".m", this
								.generateObjCm(graph, classe));
					}

					break;
				}
			}

		}
		var content = zip.generate({
			type : "blob"
		});
		saveAs(content, "code.zip");
	},

	/**
	 * generate java class for a given uml class
	 * 
	 */
	generateJava : function(graph, model) {
		var obj = new joint.uml.ClassModel();
		var type = "class";
		// Check model type
		if (model.get("type") == "uml.Abstract") {
			type = "abstract class";
		} else if (model.get("type") == "uml.Interface") {
			type = "interface";
		}
		obj.package = "com.company";
		var imports = obj.imports;
		obj.type = type;
		obj.name = model.get("name");

		var links = graph.getConnectedLinks(model);
		var optionsExtend = "";
		var optionsInterface = [];
		// If class have generalisation or implementation
		for (var j = 0; j < links.length; j++) {
			var link = links[j];
			if (model.get("id") == link.get("source").id) {
				switch (link.get("type")) {
				case "uml.Generalization":
					imports[imports.length] = graph.getCell(
							link.get("target").id).get("name");
					optionsExtend = "extends "
							+ graph.getCell(link.get("target").id).get("name");
					break;
				case "uml.Implementation":
					imports[imports.length] = graph.getCell(
							link.get("target").id).get("name");
					optionsInterface[optionsInterface.length] = graph.getCell(
							link.get("target").id).get("name");
					break;
				}
			}
		}
		obj.imports = imports;
		obj.imports = obj.imports.concat(this.getImports(graph, model));
		obj.options = optionsExtend;
		if (optionsInterface.length > 0) {
			obj.options += " implements " + optionsInterface.join(", ");
		}

		var attributes = model.get("attributes");
		var methods = model.get("methods");

		for (var i = 0; i < attributes.length; i++) {
			var attrObj = new joint.uml.AttributeModel();

			// var attributeParts = attributes[i].trim().match(/(.*)\s/);
			// var attributeParts = attributes[i].split(" ");
			var a = attributes[i].trim().split("=");
			var intialize;
			if (a[1] != null) {
				intialize = " = " + a[1];
			}

			var visibility;
			var type;
			var name;
			var parts = [];
			var attributeParts = [];
			parts = a[0].split(" ");
			for (var k = 0; k < parts.length; k++) {
				if (parts[k] != "") {
					attributeParts.push(parts[k]);
				}
			}
			if (attributeParts.length == 2) {
				visibility = "private";
				type = attributeParts[0].trim();
				name = attributeParts[1].trim();
			} else if (attributeParts.length > 2) {
				visibility = attributeParts[0].trim();
				type = attributeParts[1].trim();
				name = attributeParts[2].trim();
			}

			// alert("visibility: " + visibility + " type: " + type + " name :"+
			// name);
			attrObj.visibility = visibility;
			attrObj.type = type;
			attrObj.name = name;
			attrObj.intialize = intialize;
			attrObj.getter = "get" + this.capitalize(attributeParts[1]);
			attrObj.setter = "set" + this.capitalize(attributeParts[1]);
			obj.attributes[obj.attributes.length] = attrObj;

		}

		// Retreive links to add attributes
		for (var j = 0; j < links.length; j++) {
			var link = links[j];
			var cardinality = "1";
			var toLink = null;
			switch (link.get("type")) {
			case "uml.Aggregation":
				if (model.get("id") == link.get("source").id) {
					toLink = graph.getCell(link.get("target").id);
					cardinality = link.label(1).attrs.text.text;
				}
				break;
			case "uml.Association":
			case "uml.Composition":
				if (model.get("id") == link.get("source").id) {
					toLink = graph.getCell(link.get("target").id);
					cardinality = link.label(1).attrs.text.text;
				} else if (model.get("id") == link.get("target").id) {
					toLink = graph.getCell(link.get("source").id);
					cardinality = link.label(0).attrs.text.text;
				}
				break;

			}
			// UML link was found we add the attribute of the other class
			if (toLink != null) {
				var attrObj = new joint.uml.AttributeModel();
				attrObj.visibility = "private";
				attrObj.name = toLink.get("name").toLowerCase();

				if (cardinality == "1" || cardinality == "0..1") {
					attrObj.type = toLink.get("name");
				} else {
					attrObj.name += attrObj.name.slice(-1) == "s" ? "es" : "s";
					attrObj.type = "ArrayList<" + toLink.get("name") + ">";
				}

				attrObj.getter = "get" + this.capitalize(attrObj.name);
				attrObj.setter = "set" + this.capitalize(attrObj.name);
				obj.attributes[obj.attributes.length] = attrObj;
			}

		}

		for (var i = 0; i < methods.length; i++) {
			var methodObj = new joint.uml.MethodModel();
			var visibility;
			var returnType;
			var name;
			var params;
			var m = methods[i].trim().split("\(");
			// var methodParts =
			// methods[i].trim().match(/([+\-#])(.*)\((.*)\):?(.*)/);
			var parts = [];
			var method1 = [];
			parts = m[0].split(" ");
			for (var k = 0; k < parts.length; k++) {
				if (parts[k] != "") {
					method1.push(parts[k]);
				}
			}
			var method2 = method1 + "(" + m[1];
			var methodParts;
			if (method1.length == 2) {
				methodParts = method2.toString().trim().match(
						/(.*),(.*)\((.*)\):?(.*)/);
				visibility = "private";
				returnType = methodParts[1].trim();
				name = methodParts[2].trim();
				params = methodParts[3].trim();
			} else if (method1.length > 2) {
				methodParts = method2.toString().trim().match(
						/(.*),(.*),(.*)\((.*)\):?(.*)/);
				var visibility1 = methodParts[1].trim();
				visibility = visibility1.split(',').join(' ');
				returnType = methodParts[2].trim();
				name = methodParts[3].trim();
				params = methodParts[4].trim();
			}

			// alert("visiblity: " + visibility + " returnType : " + returnType
			// + " name::" + name + " ::params:" + params);

			if (methodParts == null) {
				if (console)
					console
							.log("ERROR : method "
									+ methods[i].trim()
									+ " not match visibility name(params):return so it's not exported");
				continue;
			}
			methodObj.visibility = visibility;
			methodObj.returnType = returnType;
			methodObj.name = name;
			methodObj.params = params;
			obj.methods[obj.methods.length] = methodObj;
		}
		return _.template(joint.uml.javaTemplate, obj);
	},

	/**
	 * generate VB class for a given uml class
	 * 
	 */
	generateVB : function(graph, model) {
		var obj = new joint.uml.ClassModel();
		var type = "class";
		// Check model type
		if (model.get("type") == "uml.Abstract") {
			type = "abstract class";
		} else if (model.get("type") == "uml.Interface") {
			type = "interface";
		}
		obj.package = "com.joint";
		var imports = obj.imports;
		obj.type = type;
		obj.name = model.get("name");

		var links = graph.getConnectedLinks(model);

		var optionsExtend = "";
		var optionsInterface = [];
		// If class have generalisation or implementation
		for (var j = 0; j < links.length; j++) {
			var link = links[j];
			if (model.get("id") == link.get("source").id) {
				switch (link.get("type")) {
				case "uml.Generalization":
					imports[imports.length] = graph.getCell(
							link.get("target").id).get("name");
					optionsExtend = "extends "
							+ graph.getCell(link.get("target").id).get("name");
					break;
				case "uml.Implementation":
					imports[imports.length] = graph.getCell(
							link.get("target").id).get("name");
					optionsInterface[optionsInterface.length] = graph.getCell(
							link.get("target").id).get("name");
					break;
				}
			}
		}
		obj.imports = imports;
		obj.imports = obj.imports.concat(this.getImports(graph, model));
		obj.options = optionsExtend;
		if (optionsInterface.length > 0) {
			obj.options += " implements " + optionsInterface.join(", ");
		}

		var attributes = model.get("attributes");
		var methods = model.get("methods");

		for (var i = 0; i < attributes.length; i++) {
			var attrObj = new joint.uml.AttributeModel();

			/*
			 * var attributeParts = attributes[i].split(":"); var isPublic =
			 * attributeParts[0].indexOf("+") != -1; var isPrivate =
			 * attributeParts[0].indexOf("-") != -1; var isProtected =
			 * attributeParts[0].indexOf("#") != -1;
			 * 
			 * attributeParts[0] = attributeParts[0].replace("-",
			 * "").replace("+", "").replace("#", "").trim(); var visibility =
			 * "public"; if (isPrivate) visibility = "private"; if (isProtected)
			 * visibility = "protected";
			 * 
			 * attrObj.visibility = visibility; attrObj.type =
			 * attributeParts[1].trim(); attrObj.name = attributeParts[0];
			 * attrObj.getter = "get" + this.capitalize(attributeParts[0]);
			 * attrObj.setter = "set" + this.capitalize(attributeParts[0]);
			 * obj.attributes[obj.attributes.length] = attrObj;
			 */
			var a = attributes[i].trim().split("=");
			var intialize;
			if (a[1] != null) {
				intialize = " = " + a[1];
			}

			var visibility;
			var type;
			var name;
			var parts = [];
			var attributeParts = [];
			parts = a[0].split(" ");
			for (var k = 0; k < parts.length; k++) {
				if (parts[k] != "") {
					attributeParts.push(parts[k]);
				}
			}
			if (attributeParts.length == 2) {
				visibility = "private";
				type = attributeParts[0].trim();
				name = attributeParts[1].trim();
			} else if (attributeParts.length > 2) {
				visibility = attributeParts[0].trim();
				type = attributeParts[1].trim();
				name = attributeParts[2].trim();
			}

			// alert("visibility: " + visibility + " type: " + type + " name :"+
			// name);
			attrObj.visibility = visibility;
			attrObj.type = type;
			attrObj.name = name;
			attrObj.intialize = intialize;
			attrObj.getter = "get" + this.capitalize(attributeParts[1]);
			attrObj.setter = "set" + this.capitalize(attributeParts[1]);
			obj.attributes[obj.attributes.length] = attrObj;

		}

		// Retreive links to add attributes
		for (var j = 0; j < links.length; j++) {
			var link = links[j];
			var cardinality = "1";
			var toLink = null;
			switch (link.get("type")) {
			case "uml.Aggregation":
				if (model.get("id") == link.get("source").id) {
					toLink = graph.getCell(link.get("target").id);
					cardinality = link.label(1).attrs.text.text;
				}
				break;
			case "uml.Association":
			case "uml.Composition":
				if (model.get("id") == link.get("source").id) {
					toLink = graph.getCell(link.get("target").id);
					cardinality = link.label(1).attrs.text.text;
				} else if (model.get("id") == link.get("target").id) {
					toLink = graph.getCell(link.get("source").id);
					cardinality = link.label(0).attrs.text.text;
				}
				break;

			}
			// UML link was found we add the attribute of the other class
			if (toLink != null) {
				var attrObj = new joint.uml.AttributeModel();
				attrObj.visibility = "private";
				attrObj.name = toLink.get("name").toLowerCase();

				if (cardinality == "1" || cardinality == "0..1") {
					attrObj.type = toLink.get("name");
				} else {
					attrObj.name += attrObj.name.slice(-1) == "s" ? "es" : "s";
					attrObj.type = "ArrayList<" + toLink.get("name") + ">";
				}

				attrObj.getter = "get" + this.capitalize(attrObj.name);
				attrObj.setter = "set" + this.capitalize(attrObj.name);
				obj.attributes[obj.attributes.length] = attrObj;
			}

		}

		/*
		 * for (var i = 0; i < methods.length; i++) { var methodObj = new
		 * joint.uml.MethodModel(); var methodParts = methods[i].trim().match(
		 * /([+\-#])(.*)\((.*)\):?(.*)/); if (methodParts == null) { if
		 * (console) console .log("ERROR : method " + methods[i].trim() + " not
		 * match visibility name(params):return so it's not exported");
		 * continue; } var isPublic = methodParts[1] == "+"; var isPrivate =
		 * methodParts[1] == "-"; var isProtected = methodParts[1] == "#";
		 * 
		 * var visibility = "public"; if (isPrivate) visibility = "private"; if
		 * (isProtected) visibility = "protected";
		 * 
		 * if (methodParts[4] == "") { methodParts[4] = "void"; } var params =
		 * methodParts[3].split(","); var methodParams = []; for (var k = 0; k <
		 * params.length; k++) { var parts = params[k].split(":");
		 * methodParams[methodParams.length] = parts.reverse().join(" ")
		 * .trim(); }
		 * 
		 * methodObj.visibility = visibility; methodObj.name =
		 * methodParts[2].trim(); methodObj.returnType = methodParts[4].trim();
		 * methodObj.params = methodParams.join(", ").trim();
		 * obj.methods[obj.methods.length] = methodObj; }
		 * 
		 * 
		 */

		for (var i = 0; i < methods.length; i++) {
			var methodObj = new joint.uml.MethodModel();
			var visibility;
			var returnType;
			var name;
			var params;
			var m = methods[i].trim().split("\(");
			// var methodParts =
			// methods[i].trim().match(/([+\-#])(.*)\((.*)\):?(.*)/);
			var parts = [];
			var method1 = [];
			parts = m[0].split(" ");
			for (var k = 0; k < parts.length; k++) {
				if (parts[k] != "") {
					method1.push(parts[k]);
				}
			}
			var method2 = method1 + "(" + m[1];
			var methodParts;
			if (method1.length == 2) {
				methodParts = method2.toString().trim().match(
						/(.*),(.*)\((.*)\):?(.*)/);
				visibility = "private";
				returnType = methodParts[1].trim();
				name = methodParts[2].trim();
				params = methodParts[3].trim();
			} else if (method1.length > 2) {
				methodParts = method2.toString().trim().match(
						/(.*),(.*),(.*)\((.*)\):?(.*)/);
				var visibility1 = methodParts[1].trim();
				visibility = visibility1.replace(",", " ");
				returnType = methodParts[2].trim();
				name = methodParts[3].trim();
				params = methodParts[4].trim();
			}

			// alert("visiblity: " + visibility + " returnType : " + returnType
			// + " name::" + name + " ::params:" + params);

			if (methodParts == null) {
				if (console)
					console
							.log("ERROR : method "
									+ methods[i].trim()
									+ " not match visibility name(params):return so it's not exported");
				continue;
			}
			methodObj.visibility = visibility;
			methodObj.returnType = returnType;
			methodObj.name = name;
			methodObj.params = params;
			obj.methods[obj.methods.length] = methodObj;
		}
		return _.template(joint.uml.vbTemplate, obj);
	},
	generatePhp : function(graph, model) {
		var obj = new joint.uml.ClassModel();
		var type = "class";
		// Check model type
		if (model.get("type") == "uml.Abstract") {
			type = "abstract class";
		} else if (model.get("type") == "uml.Interface") {
			type = "interface";
		}

		var imports = obj.imports;
		obj.type = type;
		obj.name = model.get("name");

		var links = graph.getConnectedLinks(model);

		var optionsExtend = "";
		var optionsInterface = [];
		// If class have generalisation or implementation
		for (var j = 0; j < links.length; j++) {
			var link = links[j];
			if (model.get("id") == link.get("source").id) {
				switch (link.get("type")) {
				case "uml.Generalization":
					imports[imports.length] = graph.getCell(
							link.get("target").id).get("name");
					optionsExtend = "extends "
							+ graph.getCell(link.get("target").id).get("name");
					break;
				case "uml.Implementation":
					imports[imports.length] = graph.getCell(
							link.get("target").id).get("name");
					optionsInterface[optionsInterface.length] = graph.getCell(
							link.get("target").id).get("name");
					break;
				}
			}
		}
		obj.imports = imports;
		obj.imports = obj.imports.concat(this.getImports(graph, model));

		obj.options = optionsExtend;
		if (optionsInterface.length > 0) {
			obj.options += " implements " + optionsInterface.join(", ");
		}

		var attributes = model.get("attributes");
		var methods = model.get("methods");

		for (var i = 0; i < attributes.length; i++) {
			var attrObj = new joint.uml.AttributeModel();

			var attributeParts = attributes[i].split(":");
			var isPublic = attributeParts[0].indexOf("+") != -1;
			var isPrivate = attributeParts[0].indexOf("-") != -1;
			var isProtected = attributeParts[0].indexOf("#") != -1;

			attributeParts[0] = attributeParts[0].replace("-", "").replace("+",
					"").replace("#", "").trim();
			var visibility = "public";
			if (isPrivate)
				visibility = "private";
			if (isProtected)
				visibility = "protected";

			attrObj.visibility = visibility;
			attrObj.type = attributeParts[1].trim();
			attrObj.name = attributeParts[0];
			attrObj.getter = "get" + this.capitalize(attributeParts[0]);
			attrObj.setter = "set" + this.capitalize(attributeParts[0]);
			obj.attributes[obj.attributes.length] = attrObj;

		}

		// Retreive links to add attributes
		for (var j = 0; j < links.length; j++) {
			var link = links[j];
			var cardinality = "1";
			var toLink = null;
			switch (link.get("type")) {
			case "uml.Aggregation":
				if (model.get("id") == link.get("source").id) {
					toLink = graph.getCell(link.get("target").id);
					cardinality = link.label(1).attrs.text.text;
				}
				break;
			case "uml.Association":
			case "uml.Composition":
				if (model.get("id") == link.get("source").id) {
					toLink = graph.getCell(link.get("target").id);
					cardinality = link.label(1).attrs.text.text;
				} else if (model.get("id") == link.get("target").id) {
					toLink = graph.getCell(link.get("source").id);
					cardinality = link.label(0).attrs.text.text;
				}
				break;

			}
			// UML link was found we add the attribute of the other class
			if (toLink != null) {
				var attrObj = new joint.uml.AttributeModel();
				attrObj.visibility = "private";
				attrObj.name = toLink.get("name").toLowerCase();

				if (cardinality == "1" || cardinality == "0..1") {
					attrObj.type = toLink.get("name");
				} else {
					attrObj.name += attrObj.name.slice(-1) == "s" ? "es" : "s";
					attrObj.type = "array()";
				}

				attrObj.getter = "get" + this.capitalize(attrObj.name);
				attrObj.setter = "set" + this.capitalize(attrObj.name);
				obj.attributes[obj.attributes.length] = attrObj;
			}

		}

		for (var i = 0; i < methods.length; i++) {
			var methodObj = new joint.uml.MethodModel();
			var methodParts = methods[i].trim().match(
					/([+\-#])(.*)\((.*)\):?(.*)/);
			if (methodParts == null) {
				if (console)
					console
							.log("ERROR : method "
									+ methods[i].trim()
									+ " not match visibility name(params):return so it's not exported");
				continue;
			}
			var isPublic = methodParts[1] == "+";
			var isPrivate = methodParts[1] == "-";
			var isProtected = methodParts[1] == "#";

			var visibility = "public";
			if (isPrivate)
				visibility = "private";
			if (isProtected)
				visibility = "protected";

			if (methodParts[5] == "") {
				methodParts[5] = "void";
			}
			var params = methodParts[3].split(",");
			var methodParams = [];
			if (methodParts[3].trim() != "") {
				for (var k = 0; k < params.length; k++) {
					var parts = params[k].split(":");
					methodParams[methodParams.length] = "$" + parts[0].trim();
				}
			}

			methodObj.visibility = visibility;
			methodObj.name = methodParts[2].trim();
			methodObj.returnType = methodParts[4].trim();
			methodObj.params = methodParams.join(", ").trim();
			obj.methods[obj.methods.length] = methodObj;

		}
		return _.template(joint.uml.phpTemplate, obj);
	},
	generateObjCh : function(graph, model) {
		var obj = new joint.uml.ClassModel();
		var type = "interface";
		// Check model type
		if (model.get("type") == "uml.Abstract") {
			// there no abstract under obj c so make normal class
		} else if (model.get("type") == "uml.Interface") {
			type = "protocol";
		}

		obj.type = type;
		var imports = obj.imports;
		obj.name = model.get("name");

		var links = graph.getConnectedLinks(model);
		var optionsExtend = "";
		var optionsInterface = [];
		// If class have generalisation or implementation
		for (var j = 0; j < links.length; j++) {
			var link = links[j];
			if (model.get("id") == link.get("source").id) {
				switch (link.get("type")) {
				case "uml.Generalization":
					imports[imports.length] = graph.getCell(
							link.get("target").id).get("name");
					optionsExtend = ": "
							+ graph.getCell(link.get("target").id).get("name");
					break;
				case "uml.Implementation":
					imports[imports.length] = graph.getCell(
							link.get("target").id).get("name");
					optionsInterface[optionsInterface.length] = graph.getCell(
							link.get("target").id).get("name");
					break;
				}
			}
		}
		obj.imports = imports;
		obj.imports = obj.imports.concat(this.getImports(graph, model));
		// Object extend NSObject by default
		if (optionsExtend == "" && type == "interface") {
			optionsExtend = ": NSObject";
		}

		obj.options = optionsExtend;

		// Protocols
		if (optionsInterface.length > 0) {
			obj.options += " <" + optionsInterface.join(", ") + ">";
		}

		var attributes = model.get("attributes");
		obj.attributes = this.getObjCAttributes(graph, model);

		var methods = model.get("methods");
		obj.methods = this.getObjCMethods(graph, model);

		return _.template(joint.uml.objChTemplate, obj);
	},
	generateObjCm : function(graph, model) {
		var obj = new joint.uml.ClassModel();
		var type = "interface";
		// Check model type
		if (model.get("type") == "uml.Abstract") {
			// there no abstract under obj c so make normal class
		} else if (model.get("type") == "uml.Interface") {
			type = "protocol";
		}

		obj.type = type;
		obj.name = model.get("name");

		var links = graph.getConnectedLinks(model);
		var optionsExtend = "";
		var optionsInterface = [];
		// If class have generalisation or implementation
		for (var j = 0; j < links.length; j++) {
			var link = links[j];
			if (model.get("id") == link.get("source").id) {
				switch (link.get("type")) {
				case "uml.Generalization":
					optionsExtend = ": "
							+ graph.getCell(link.get("target").id).get("name");
					break;
				case "uml.Implementation":
					optionsInterface[optionsInterface.length] = graph.getCell(
							link.get("target").id).get("name");
					break;
				}
			}
		}

		// Object extend NSObject by default
		if (optionsExtend == "" && type == "interface") {
			optionsExtend = ": NSObject";
		}

		obj.options = optionsExtend;

		// Protocols
		if (optionsInterface.length > 0) {
			obj.options += " <" + optionsInterface.join(", ") + ">";
		}

		var attributes = model.get("attributes");
		obj.attributes = this.getObjCAttributes(graph, model);
		var methods = model.get("methods");
		obj.methods = this.getObjCMethods(graph, model);

		return _.template(joint.uml.objCmTemplate, obj);
	},
	getObjCAttributes : function(graph, model) {
		var attributes = model.get("attributes");
		var objAttributes = [];
		for (var i = 0; i < attributes.length; i++) {
			var attrObj = new joint.uml.AttributeModel();

			var attributeParts = attributes[i].split(":");
			var isPublic = attributeParts[0].indexOf("+") != -1;
			var isPrivate = attributeParts[0].indexOf("-") != -1;
			var isProtected = attributeParts[0].indexOf("#") != -1;

			attributeParts[0] = attributeParts[0].replace("-", "").replace("+",
					"").replace("#", "").trim();
			var visibility = "public";
			if (isPrivate)
				visibility = "private";
			if (isProtected)
				visibility = "protected";

			attrObj.visibility = visibility;
			if (typeof this.objCEquivalent[attributeParts[1].trim()] == "undefined") {
				if (this.isPrimitive(attributeParts[1].trim())) {
					attrObj.type = attributeParts[1].trim();
				} else if (attributeParts[1].indexOf("[]") != -1) {
					methodObj.returnType = "NSMutableArray*";
				} else {
					attrObj.type = attributeParts[1].trim() + "*";
				}
			} else {
				attrObj.type = this.objCEquivalent[attributeParts[1].trim()];
			}

			attrObj.name = attributeParts[0];
			objAttributes[objAttributes.length] = attrObj;
		}

		var links = graph.getConnectedLinks(model);

		// Retreive links to add attributes
		for (var j = 0; j < links.length; j++) {
			var link = links[j];
			var cardinality = "1";
			var toLink = null;
			switch (link.get("type")) {
			case "uml.Aggregation":
				if (model.get("id") == link.get("source").id) {
					toLink = graph.getCell(link.get("target").id);
					cardinality = link.label(1).attrs.text.text;
				}
				break;
			case "uml.Association":
			case "uml.Composition":
				if (model.get("id") == link.get("source").id) {
					toLink = graph.getCell(link.get("target").id);
					cardinality = link.label(1).attrs.text.text;
				} else if (model.get("id") == link.get("target").id) {
					toLink = graph.getCell(link.get("source").id);
					cardinality = link.label(0).attrs.text.text;
				}
				break;

			}
			// UML link was found we add the attribute of the other class
			if (toLink != null) {
				var attrObj = new joint.uml.AttributeModel();
				attrObj.visibility = "public";
				attrObj.name = toLink.get("name").toLowerCase();

				if (cardinality == "1" || cardinality == "0..1") {
					attrObj.type = toLink.get("name") + "*";
				} else {
					attrObj.name += attrObj.name.slice(-1) == "s" ? "es" : "s";
					attrObj.type = "NSMutableArray*";
				}
				objAttributes[objAttributes.length] = attrObj;
			}

		}
		return objAttributes;
	},
	getObjCMethods : function(graph, model) {
		var methods = model.get("methods");
		var objMethods = [];
		for (var i = 0; i < methods.length; i++) {
			var methodObj = new joint.uml.MethodModel();
			var methodParts = methods[i].trim().match(
					/([+\-#])(.*)\((.*)\):?(.*)/);
			if (methodParts == null) {
				if (console)
					console
							.log("ERROR : method "
									+ methods[i].trim()
									+ " not match visibility name(params):return so it's not exported");
				continue;
			}
			var isPublic = methodParts[1] == "+";
			var isPrivate = methodParts[1] == "-";
			var isProtected = methodParts[1] == "#";

			var visibility = "public";
			if (isPrivate)
				visibility = "private";
			if (isProtected)
				visibility = "protected";

			if (methodParts[5] == "") {
				methodParts[5] = "void";
			}
			var params = methodParts[3].split(",");
			var methodParams = [];
			if (params != "") {
				for (var k = 0; k < params.length; k++) {
					var parts = params[k].split(":");

					if (typeof this.objCEquivalent[parts[1].trim()] == "undefined") {
						if (this.isPrimitive(parts[1].trim())) {
							parts[1] = "(" + parts[1].trim() + ")";
						} else {
							parts[1] = "(" + parts[1].trim() + "*)";
						}

					} else {
						parts[1] = "(" + this.objCEquivalent[parts[1].trim()]
								+ ")";
					}

					if (methodParams.length == 0) {
						methodParams[methodParams.length] = parts.reverse()
								.join(" ").trim();
					} else {
						methodParams[methodParams.length] = "and"
								+ this.capitalize(parts[0].trim()) + ":"
								+ parts.reverse().join(" ").trim();
					}

				}
			}

			methodObj.visibility = visibility;
			methodObj.name = methodParts[2].trim();

			if (typeof this.objCEquivalent[methodParts[4].trim()] == "undefined") {
				if (this.isPrimitive(methodParts[4].trim())) {
					methodObj.returnType = methodParts[4].trim();
				} else if (methodParts[4].indexOf("[]") != -1) {
					methodObj.returnType = "NSMutableArray*";
				} else {
					methodObj.returnType = methodParts[4].trim() + "*";
				}

			} else {
				methodObj.returnType = this.objCEquivalent[methodParts[4]
						.trim()];
			}

			methodObj.params = methodParams.join(" ").trim();
			objMethods[objMethods.length] = methodObj;

		}
		return objMethods;
	},
	generateSwift : function(graph, model) {
		var obj = new joint.uml.ClassModel();
		var type = "class";
		// Check model type
		if (model.get("type") == "uml.Abstract") {
			// type = "abstract class";
		} else if (model.get("type") == "uml.Interface") {
			type = "protocol";
		}
		var imports = obj.imports;
		obj.type = type;
		obj.name = model.get("name");

		var links = graph.getConnectedLinks(model);

		var optionsExtend = "";
		var optionsInterface = [];
		// If class have generalisation or implementation
		for (var j = 0; j < links.length; j++) {
			var link = links[j];
			if (model.get("id") == link.get("source").id) {
				switch (link.get("type")) {
				case "uml.Generalization":
					imports[imports.length] = graph.getCell(
							link.get("target").id).get("name");
					optionsExtend = ": "
							+ graph.getCell(link.get("target").id).get("name");
					break;
				case "uml.Implementation":
					imports[imports.length] = graph.getCell(
							link.get("target").id).get("name");
					optionsInterface[optionsInterface.length] = graph.getCell(
							link.get("target").id).get("name");
					break;
				}
			}
		}
		obj.imports = imports;
		obj.imports = obj.imports.concat(this.getImports(graph, model));
		obj.options = optionsExtend;
		if (optionsInterface.length > 0) {
			obj.options += " , " + optionsInterface.join(", ");
		}

		var attributes = model.get("attributes");
		var methods = model.get("methods");

		for (var i = 0; i < attributes.length; i++) {
			var attrObj = new joint.uml.AttributeModel();

			var isArray = false;
			if (attributes[i].indexOf("[]") != -1) {
				isArray = true;
			}

			var attributeParts = attributes[i].split(":");
			var isPublic = attributeParts[0].indexOf("+") != -1;
			var isPrivate = attributeParts[0].indexOf("-") != -1;
			var isProtected = attributeParts[0].indexOf("#") != -1;

			attributeParts[0] = attributeParts[0].replace("-", "").replace("+",
					"").replace("#", "").trim();
			var visibility = "public";
			if (isPrivate)
				visibility = "private";
			if (isProtected)
				visibility = "protected";

			attrObj.visibility = visibility;
			if (isArray) {
				attributeParts[1] = attributeParts[1].replace("[]", "").trim();
				if (typeof this.swiftEquivalent[attributeParts[1]] != "undefined") {
					attrObj.type = "Array<"
							+ this.swiftEquivalent[attributeParts[1]] + ">";
				} else {
					attrObj.type = "Array<" + attributeParts[1] + ">";
				}

			} else {
				if (typeof this.swiftEquivalent[attributeParts[1].trim()] != "undefined") {
					attrObj.type = this.swiftEquivalent[attributeParts[1]
							.trim()];
				} else {
					attrObj.type = attributeParts[1].trim();
				}
			}

			attrObj.name = attributeParts[0];
			obj.attributes[obj.attributes.length] = attrObj;

		}

		// Retreive links to add attributes
		for (var j = 0; j < links.length; j++) {
			var link = links[j];
			var cardinality = "1";
			var toLink = null;
			switch (link.get("type")) {
			case "uml.Aggregation":
				if (model.get("id") == link.get("source").id) {
					toLink = graph.getCell(link.get("target").id);
					cardinality = link.label(1).attrs.text.text;
				}
				break;
			case "uml.Association":
			case "uml.Composition":
				if (model.get("id") == link.get("source").id) {
					toLink = graph.getCell(link.get("target").id);
					cardinality = link.label(1).attrs.text.text;
				} else if (model.get("id") == link.get("target").id) {
					toLink = graph.getCell(link.get("source").id);
					cardinality = link.label(0).attrs.text.text;
				}
				break;

			}
			// UML link was found we add the attribute of the other class
			if (toLink != null) {
				var attrObj = new joint.uml.AttributeModel();
				attrObj.visibility = "private";
				attrObj.name = toLink.get("name").toLowerCase();

				if (cardinality == "1" || cardinality == "0..1") {
					attrObj.type = toLink.get("name");
				} else {
					attrObj.name += attrObj.name.slice(-1) == "s" ? "es" : "s";
					attrObj.type = "Array<" + toLink.get("name") + ">";
				}
				obj.attributes[obj.attributes.length] = attrObj;
			}

		}

		var constructor = [];
		for (var i = 0; i < obj.attributes.length; i++) {
			constructor[constructor.length] = obj.attributes[i].name + " : "
					+ obj.attributes[i].type;
		}

		obj.constructor = constructor.join(", ");

		for (var i = 0; i < methods.length; i++) {
			var methodObj = new joint.uml.MethodModel();
			var methodParts = methods[i].trim().match(
					/([+\-#])(.*)\((.*)\):?(.*)/);
			if (methodParts == null) {
				if (console)
					console
							.log("ERROR : method "
									+ methods[i].trim()
									+ " not match visibility name(params):return so it's not exported");
				continue;
			}
			var isPublic = methodParts[1] == "+";
			var isPrivate = methodParts[1] == "-";
			var isProtected = methodParts[1] == "#";

			var visibility = "public";
			if (isPrivate)
				visibility = "private";
			if (isProtected)
				visibility = "protected";

			if (!methodParts[4]
					|| methodParts[4].toLowerCase().trim() == "void") {
				methodParts[4] = "";
			} else if (methodParts[4].indexOf("[]") != -1) {
				methodParts[4] = methodParts[4].replace("[]", "").trim();
				if (typeof this.swiftEquivalent[methodParts[4]] != "undefined") {
					methodParts[4] = "-> Array<"
							+ this.swiftEquivalent[methodParts[4]] + ">";
				} else {
					methodParts[4] = "-> Array<" + methodParts[4] + ">";
				}
			} else {
				if (typeof this.swiftEquivalent[methodParts[4].trim()] != "undefined") {
					methodParts[4] = "-> "
							+ this.swiftEquivalent[methodParts[4].trim()];
				} else {
					methodParts[4] = "-> " + methodParts[4].trim();
				}
			}

			var params = methodParts[3].split(",");
			var methodParams = [];
			if (params != "") {
				for (var k = 0; k < params.length; k++) {
					var parts = params[k].split(":");

					if (typeof this.swiftEquivalent[parts[1].trim()] == "undefined") {
						parts[1] = parts[1].trim();
					} else {
						parts[1] = this.swiftEquivalent[parts[1].trim()];
					}

					methodParams[methodParams.length] = parts.join(" : ")
							.trim();
				}
			}
			methodObj.visibility = visibility;
			methodObj.name = methodParts[2].trim();
			methodObj.returnType = methodParts[4].trim();
			methodObj.params = methodParams.join(", ").trim();
			obj.methods[obj.methods.length] = methodObj;

		}
		console.log(_.template(joint.uml.swiftTemplate, obj));
		return _.template(joint.uml.swiftTemplate, obj);
	},
	getImports : function(graph, model) {
		var objImports = [];
		var links = graph.getConnectedLinks(model);
		// Retreive links to add attributes
		for (var j = 0; j < links.length; j++) {
			var link = links[j];
			var toLink = null;
			switch (link.get("type")) {
			case "uml.Aggregation":
				if (model.get("id") == link.get("source").id) {
					toLink = graph.getCell(link.get("target").id);
				}
				break;
			case "uml.Association":
			case "uml.Composition":
				if (model.get("id") == link.get("source").id) {
					toLink = graph.getCell(link.get("target").id);
				} else if (model.get("id") == link.get("target").id) {
					toLink = graph.getCell(link.get("source").id);
				}
				break;

			}
			// UML link was found we add the attribute of the other class
			if (toLink != null) {
				objImports[objImports.length] = toLink.get("name");
			}

		}
		return objImports;
	},
}