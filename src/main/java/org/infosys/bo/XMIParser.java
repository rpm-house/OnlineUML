package org.infosys.bo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.infosys.vo.common.ClassMembers;
import org.infosys.vo.common.FieldModifier;
import org.infosys.vo.common.RelationMembers;
import org.infosys.vo.common.SourceCodeClassMembers;
import org.infosys.vo.common.SourceCodeFieldMembers;
import org.infosys.vo.common.SourceCodeMethodMembers;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMIParser {

	JavaParser javaParser = new JavaParser();
	Map<String, String> nameIdMap = new HashMap<String, String>();
	List<String> primitiveDataTypeList = new ArrayList<String>();
	Map<String, List<SourceCodeClassMembers>> codeMap = new HashMap<String, List<SourceCodeClassMembers>>();
	/*
	 * private String configFile;
	 * 
	 * public void setFile(String configFile) { this.configFile = configFile; }
	 * 
	 * public void saveConfig() throws Exception { // create an XMLOutputFactory
	 * XMLOutputFactory outputFactory = XMLOutputFactory.newInstance(); //
	 * create XMLEventWriter XMLEventWriter eventWriter =
	 * outputFactory.createXMLEventWriter(new FileOutputStream(configFile)); //
	 * create an EventFactory XMLEventFactory eventFactory =
	 * XMLEventFactory.newInstance(); XMLEvent end =
	 * eventFactory.createDTD("\n"); // create and write Start Tag StartDocument
	 * startDocument = eventFactory.createStartDocument();
	 * eventWriter.add(startDocument);
	 * 
	 * // create config open tag StartElement configStartElement =
	 * eventFactory.createStartElement("", "", "uml-Package");
	 * eventWriter.add(configStartElement); eventWriter.add(end);
	 * 
	 * // Write the different nodes createNode(eventWriter, "packagedElement");
	 * 
	 * eventWriter.add(eventFactory.createEndElement("", "", "uml-Package"));
	 * eventWriter.add(end); eventWriter.add(eventFactory.createEndDocument());
	 * eventWriter.close(); }
	 * 
	 * private void createNode(XMLEventWriter eventWriter, String name) throws
	 * XMLStreamException {
	 * 
	 * XMLEventFactory eventFactory = XMLEventFactory.newInstance(); XMLEvent
	 * end = eventFactory.createDTD("\n"); XMLEvent tab =
	 * eventFactory.createDTD("\t"); // create Start node StartElement sElement
	 * = eventFactory.createStartElement("", "", name); eventWriter.add(tab);
	 * eventWriter.add(sElement); // create Content
	 * 
	 * Characters characters = eventFactory.createCharacters(value);
	 * eventWriter.add(characters);
	 * 
	 * // create End node EndElement eElement =
	 * eventFactory.createEndElement("", "", name); eventWriter.add(eElement);
	 * eventWriter.add(end);
	 * 
	 * }
	 */
	public static void main(String[] args) {

		String fileName = "D:/projects/biz/workspace/UMLWeb/UMLWeb.xmi";
		XMIParser parser = new XMIParser();
		
		 /*try { 
			 parser.setFile("D/projects/KBE/xmiParse/configFile.xmi");
		  parser.saveConfig(); } catch (Exception e) {
		  
		  e.printStackTrace(); } XMIParser xmiParser = new XMIParser();*/
		 
		//parser.createXMI(fileName, "");
		parser.testReadXMI();
	}

	public void createXMI(String fileName, String jsonData) {
		JsonParserBO parser = new JsonParserBO();

		List<ClassMembers> classList = parser.getClassList(jsonData);
		//List<ClassMembers> classList = parser.getClassList();

		try {
			Element root = new Element("Package", "uml", "http://schema.omg.org/spec/UML/2.2");
			Namespace xmi = Namespace.getNamespace("xmi", "http://schema.omg.org/spec/XMI/2.1");
			root.addNamespaceDeclaration(xmi);
			Namespace xsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			root.addNamespaceDeclaration(xsi);
			Namespace ecore = Namespace.getNamespace("ecore", "http://www.eclipse.org/emf/2002/Ecore");
			root.addNamespaceDeclaration(ecore);
			root.setAttribute("version", "2.1", xmi);
			root.setAttribute("id", "XMI_Id_0001", xmi);
			root.setAttribute("schemaLocation",
					"http://schema.omg.org/spec/UML/2.2 http://www.eclipse.org/uml2/3.0.0/UML", xsi);
			root.setAttribute("name", "UMLWeb_v0.0.01");
			Document doc = new Document(root);
			Iterator<ClassMembers> classIterator = classList.iterator();
			while (classIterator.hasNext()) {
				ClassMembers classMembers = classIterator.next();
				Element packagedElement = new Element("packagedElement");
				packagedElement.setAttribute("type", classMembers.getType(), xmi);
				packagedElement.setAttribute("id", classMembers.getId(), xmi);
				if (null != classMembers.getClassName())
					packagedElement.setAttribute("name", classMembers.getClassName());
				else
					packagedElement.setAttribute("name", "Realtions");
				if(null !=classMembers.getClassModifiers())
				{
					String classModifier = classMembers.getClassModifiers();
					if (classModifier.trim().equalsIgnoreCase("abstract")) {
						packagedElement.setAttribute("isAbstract", "true");
					}
				}
				
				if (null != classMembers.getAttributeList()) {
					List<Map<String, String>> attributeList = classMembers.getAttributeList();
					Iterator<Map<String, String>> attributeIterator = attributeList.iterator();
					while (attributeIterator.hasNext()) {
						Map<String, String> attributeMap = attributeIterator.next();
						Element ownedAttribute = new Element("ownedAttribute");
						ownedAttribute.setAttribute(new Attribute("type", "uml:Property", xmi));
						ownedAttribute.setAttribute("id", attributeMap.get("Id"), xmi);
						ownedAttribute.setAttribute("name", attributeMap.get("SimpleName"));
						if (null != attributeMap.get("Modifiers")
								&& !attributeMap.get("Modifiers").equalsIgnoreCase(" ")) {
							FieldModifier fieldModifier = getFieldModifiers(attributeMap.get("Modifiers"));
							if (null != fieldModifier.getVisiblity()
									&& !(fieldModifier.getVisiblity().equalsIgnoreCase(" "))) {
								ownedAttribute.setAttribute("visibility", fieldModifier.getVisiblity());
							} else {
								ownedAttribute.setAttribute("visibility", "package");
							}
							if (fieldModifier.isFinal()) {
								ownedAttribute.setAttribute("isLeaf", "true");
							}
							if (fieldModifier.isStatic()) {
								ownedAttribute.setAttribute("isStatic", "true");
							}
						} else {
							ownedAttribute.setAttribute("visibility", "package");
						}

						String typeOfType = attributeMap.get("TypeOfDataType").trim();
						String dataType = attributeMap.get("DataType").trim();
						if (null != typeOfType && typeOfType.equalsIgnoreCase("Simple")) {
							String classType = classMembers.getNameIdMap().get(dataType);
							if (null != classType) {
								ownedAttribute.setAttribute("type", classType);
							} else {
								Element type = new Element("type");
								type.setAttribute(new Attribute("type", "uml:PrimitiveType", xmi));
									type.setAttribute("href", "http://schema.omg.org/spec/UML/2.2/uml.xml#String");
								ownedAttribute.addContent(type);
							}
						} else if (null != typeOfType && typeOfType.equalsIgnoreCase("Primitive")) {
							Element type = new Element("type");
							type.setAttribute(new Attribute("type", "uml:PrimitiveType", xmi));
							type.setAttribute("href", "JavaPrimitiveTypes.library.xmi#" + dataType);
							ownedAttribute.addContent(type);
						} else if (null != typeOfType && typeOfType.equalsIgnoreCase("Parameterized")) {
							ownedAttribute = getXMIForParameterized(xmi, classMembers, ownedAttribute, doc, dataType);
						}

						if (null != attributeMap.get("Initializer")) {
							Element defaultValue = new Element("defaultValue");
							defaultValue.setAttribute(new Attribute("type", "uml:OpaqueExpression", xmi));
							defaultValue.setAttribute("id", javaParser.getId(), xmi);
							Element language = new Element("language");
							language.setText("Java");
							Element body = new Element("body");
							body.setText(attributeMap.get("Initializer"));
							defaultValue.addContent(language);
							defaultValue.addContent(body);
							ownedAttribute.addContent(defaultValue);
						}

						packagedElement.addContent(ownedAttribute);
					}
				}

				if (0 <= classMembers.getMethodList().size()) {
					List<Map<String, String>> methodList = classMembers.getMethodList();
					Iterator<Map<String, String>> iterator = methodList.iterator();
					while (iterator.hasNext()) {
						Map<String, String> methodMap = iterator.next();
						Element ownedOperation = new Element("ownedOperation");
						ownedOperation.setAttribute(new Attribute("type", "uml:Operation", xmi));
						System.out.println("Method Name :"+methodMap.get("Name"));
						ownedOperation.setAttribute("id", methodMap.get("Id"), xmi);
						ownedOperation.setAttribute("name", methodMap.get("Name"));
						if (null != methodMap.get("Modifiers") && !methodMap.get("Modifiers").equalsIgnoreCase(" ")) {
							FieldModifier fieldModifier = getFieldModifiers(methodMap.get("Modifiers"));
							if (null != fieldModifier.getVisiblity()
									&& !(fieldModifier.getVisiblity().equalsIgnoreCase(" "))) {
								ownedOperation.setAttribute("visibility", fieldModifier.getVisiblity());
							} else {
								ownedOperation.setAttribute("visibility", "package");
							}
							if (fieldModifier.isFinal()) {
								ownedOperation.setAttribute("isLeaf", "true");
							}
							if (fieldModifier.isStatic()) {
								ownedOperation.setAttribute("isStatic", "true");
							}
						} else {
							ownedOperation.setAttribute("visibility", "package");
						}
						if (null != methodMap.get("ParamPair") && (" ".equalsIgnoreCase(methodMap.get("ParamPair")))) {
							String parameters[] = methodMap.get("ParamPair").split("&");
							for (int p = 0; p < parameters.length; p++) {
								String params[] = parameters[p].split(":");
								Element ownedParameter = new Element("ownedParameter");
								ownedParameter.setAttribute(new Attribute("type", "uml:Parameter", xmi));
								ownedParameter.setAttribute("id", javaParser.getId(), xmi);
								
								ownedParameter.setAttribute("name", params[0]);
								String parameterTypeOfType = params[2].trim();
								String parameterType = params[1].trim();
								if (null != parameterTypeOfType && parameterTypeOfType.equalsIgnoreCase("Simple")) {
								String paramType = classMembers.getNameIdMap().get(parameterType);
								if (null != paramType) {
									ownedParameter.setAttribute("type", paramType);
								} else {
									Element type = new Element("type");
									type.setAttribute(new Attribute("type", "uml:PrimitiveType", xmi));
										type.setAttribute("href", "http://schema.omg.org/spec/UML/2.2/uml.xml#String");
									ownedParameter.addContent(type);
								}
								}
								 else if (null != parameterTypeOfType && parameterTypeOfType.equalsIgnoreCase("Primitive")) {
										Element type = new Element("type");
										type.setAttribute(new Attribute("type", "uml:PrimitiveType", xmi));
										type.setAttribute("href", "JavaPrimitiveTypes.library.xmi#" + parameterType);
										ownedParameter.addContent(type);
									} else if (null != parameterTypeOfType && parameterTypeOfType.equalsIgnoreCase("Parameterized")) {
										ownedParameter = getXMIForParameterized(xmi, classMembers, ownedParameter, doc, parameterType);
									}
									else if (null != parameterTypeOfType && parameterTypeOfType.equalsIgnoreCase("Array")) {
										ownedParameter = getXMIforArray(xmi, classMembers, ownedParameter, parameterType);
									}
								ownedOperation = ownedOperation.addContent(ownedParameter);
							}
						}

						if (null != methodMap.get("ReturnType")) {
							Element ownedParameter = new Element("ownedParameter");
							ownedParameter.setAttribute(new Attribute("type", "uml:Parameter", xmi));
							ownedParameter.setAttribute("id", javaParser.getId(), xmi);
							ownedParameter.setAttribute("name", "ReturnParameter");
							ownedParameter.setAttribute("direction", "return");

							String typeOfType = methodMap.get("TypeOfReturnType").trim();
							String dataType = methodMap.get("ReturnType").trim();
							if (null != typeOfType && typeOfType.equalsIgnoreCase("Simple")) {
								String returnType = classMembers.getNameIdMap().get(dataType);
								if (null != returnType) {
									ownedParameter.setAttribute("type", returnType);
								} else {
									Element type = new Element("type");
									type.setAttribute(new Attribute("type", "uml:PrimitiveType", xmi));
										type.setAttribute("href", "http://schema.omg.org/spec/UML/2.2/uml.xml#String");
									ownedParameter.addContent(type);
								}
							} else if (null != typeOfType && typeOfType.equalsIgnoreCase("Primitive") && !(dataType.equalsIgnoreCase("void"))) {
								Element type = new Element("type");
								type.setAttribute(new Attribute("type", "uml:PrimitiveType", xmi));
								type.setAttribute("href", "JavaPrimitiveTypes.library.xmi#" + dataType);
								ownedParameter.addContent(type);
							} else if (null != typeOfType && typeOfType.equalsIgnoreCase("Parameterized")) {
								ownedParameter = getXMIForParameterized(xmi, classMembers, ownedParameter, doc, dataType);
							}
							ownedOperation.addContent(ownedParameter);
						}
						
						/*Element body = new Element("body");
						body.setText(methodMap.get("Body"));
						ownedOperation.addContent(body);*/
						packagedElement.addContent(ownedOperation);
					}
				}

				if (null != classMembers.getRelationList()) {
					Iterator<RelationMembers> relationIterator = classMembers.getRelationList().iterator();
					while (relationIterator.hasNext()) {
						RelationMembers relationMembers = relationIterator.next();
						Element relation = null;
						if ("uml:Implementation".equalsIgnoreCase(relationMembers.getType())) {
							relation = new Element("interfaceRealization");
							relation.setAttribute("type", "uml:InterfaceRealization", xmi);
							relation.setAttribute("id", relationMembers.getId(), xmi);
							packagedElement.setAttribute("clientDependency", relationMembers.getId());
							relation.setAttribute("supplier", relationMembers.getTargetId());
							relation.setAttribute("contract", relationMembers.getTargetId());
							relation.setAttribute("client", relationMembers.getSourceId());
						} else if ("uml:Generalization".equalsIgnoreCase(relationMembers.getType())) {
							relation = new Element("generalization");
							relation.setAttribute("type", "uml:Generalization", xmi);
							relation.setAttribute("id", relationMembers.getId(), xmi);
							// packagedElement.setAttribute("clientDependency",
							// relationMembers.getId());
							relation.setAttribute("general", relationMembers.getTargetId());
						}

						packagedElement.addContent(relation);

					}
				}
				doc.getRootElement().addContent(packagedElement);
			}

			XMLOutputter xmlOutput = new XMLOutputter();

			// display ml
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(fileName));
			// xmlOutput.output(doc, new
			// FileWriter("D:/projects/KBE/xmiParse/configFile2.xmi"));
			// xmlOutput.output(doc, new
			// FileWriter("D:\\projects\\KBE\\xmiParse\\configFile1.xmi"));
			// xmlOutput.output(doc, System.out);
			System.out.println("File Saved!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Element getXMIForParameterized(Namespace xmi, ClassMembers classMembers, Element ownedAttribute, Document doc,
			String dataType) {
		 System.out.println("Data Type :"+dataType);
		// System.out.println("Name :"+attributeMap.get("SimpleName"));
		String[] dataTypeArray = dataType.split("#");
		// System.out.println("Data Type Length:"+dataTypeArray.length);
		if (dataTypeArray.length == 2) {
			ownedAttribute = getXMIfor2Params(xmi, classMembers, ownedAttribute, dataType);
		} else if (dataTypeArray.length > 2) {
			ownedAttribute =getXMIfor3Params(xmi, classMembers, ownedAttribute, doc, dataTypeArray);

		}
		return ownedAttribute;
	}

	private Element getXMIfor3Params(Namespace xmi, ClassMembers classMembers, Element ownedAttribute, Document doc,
			String[] dataTypeArray) {
		String dataType = null;
		System.out.println(
				"Parent :" + dataTypeArray[0] + " Child : " + dataTypeArray[1] + " Grand Child : " + dataTypeArray[2]);
		String parent = dataTypeArray[0].trim();
		String child = dataTypeArray[1].trim();
		String grandChild = dataTypeArray[2].trim();
		String actualId = null;
		String param1 = null;
		String param2 = null;
		String object = null;
		String refId = null;
		String name = null;
		if (parent.equalsIgnoreCase("Map") && child.contains(",")) {
			if (grandChild.contains(",")) {
				String[] grandChildArray = grandChild.split(",");
				param1 = grandChildArray[0];
				param2 = grandChildArray[1];
				actualId = classMembers.getNameIdMap().get(grandChildArray[1]);
			} else {
				param1 = grandChild;
				param2 = null;
				actualId = classMembers.getNameIdMap().get(grandChild);
			}
			if (child.contains(",")) {
				String[] childArray = child.split(",");
				object = childArray[1];
			} else {
				object = child;
			}
			name = getNameOfObject(param1, param2, object, name);
			if (null != nameIdMap.get(name)) {
				refId = nameIdMap.get(name);
			} else {
				refId = xmiForMap(xmi, object, param1, param2, actualId, doc, "UMLWeb", name);
				nameIdMap.put(name, refId);
			}
			dataType = parent + "#" + "Ref@" + refId;
			ownedAttribute = getXMIfor2Params(xmi, classMembers, ownedAttribute, dataType);
		} else if (parent.equalsIgnoreCase("List") && !(child.contains(","))) {
			if (grandChild.contains(",")) {
				String[] grandChildArray = grandChild.split(",");
				param1 = grandChildArray[0];
				param2 = grandChildArray[1];
				actualId = classMembers.getNameIdMap().get(grandChildArray[1]);
			} else {
				param1 = grandChild;
				param2 = null;
				actualId = classMembers.getNameIdMap().get(grandChild);
			}
			object = child;
			name = getNameOfObject(param1, param2, object, name);
			if (null != nameIdMap.get(name)) {
				refId = nameIdMap.get(name);
			} else {
				refId = xmiForMap(xmi, object, param1, param2, actualId, doc, "UMLWeb", name);
				nameIdMap.put(name, refId);
			}
			dataType = parent + "#" + "Ref@" + refId;
			ownedAttribute = getXMIfor2Params(xmi, classMembers, ownedAttribute, dataType);
		}

		if (child.equalsIgnoreCase("Map") && grandChild.contains(",")) {
			String[] grandChildArray = grandChild.split(",");
			actualId = classMembers.getNameIdMap().get(grandChildArray[1]);
			object = child;
			param1 = grandChildArray[0];
			param2 = grandChildArray[1];
			name = getNameOfObject(param1, param2, object, name);
			if (null != nameIdMap.get(name)) {
				refId = nameIdMap.get(name);
			} else {
				refId = xmiForMap(xmi, object, param1, param2, actualId, doc, "UMLWeb", name);
				nameIdMap.put(name, refId);
			}
		} else if (child.equalsIgnoreCase("List") && !(grandChild.contains(","))) {
			actualId = classMembers.getNameIdMap().get(grandChild);
			object = child;
			param1 = grandChild;
			param2 = null;
			name = getNameOfObject(param1, param2, object, name);
			if (null != nameIdMap.get(name)) {
				refId = nameIdMap.get(name);
			} else {
				refId = xmiForMap(xmi, object, param1, param2, actualId, doc, "UMLWeb", name);
				nameIdMap.put(name, refId);
			}
		}
		return ownedAttribute;
	}

	private String getNameOfObject(String param1, String param2, String object, String name) {
		if (object.equalsIgnoreCase("Map")) {
			name = "MapOf" + param1 + "and" + param2;
		} else if (object.equalsIgnoreCase("List")) {
			name = "ListOf" + param1;
		}
		return name;
	}

	private Element getXMIforArray(Namespace xmi, ClassMembers classMembers, Element ownedAttribute, String dataType) {
		String paramType; 
		if(dataType.trim().contains("["))
		{
			dataType = dataType.substring(0, dataType.lastIndexOf("["));
		}
		paramType = dataType.trim();
		String classType;
		classType = classMembers.getNameIdMap().get(paramType);
		if (null != classType) {
			ownedAttribute.setAttribute("type", classType);
		} else {
			Element type = new Element("type");
			type.setAttribute(new Attribute("type", "uml:PrimitiveType", xmi));
			if(primitiveDataTypeList.size()>0 && primitiveDataTypeList.contains(paramType))
			{
				type.setAttribute("href", "JavaPrimitiveTypes.library.xmi#" + paramType);
			}
			else
			{
				type.setAttribute("href", "http://schema.omg.org/spec/UML/2.2/uml.xml#"+paramType);
			}
			ownedAttribute.addContent(type);
		}
			Element upperValue = new Element("upperValue");
			upperValue.setAttribute(new Attribute("type", "uml:LiteralUnlimitedNatural", xmi));
			upperValue.setAttribute(new Attribute("id", javaParser.getId(), xmi));
			upperValue.setAttribute("value", "*");
			ownedAttribute.addContent(upperValue);
			Element lowerValue = new Element("lowerValue");
			lowerValue.setAttribute(new Attribute("type", "uml:LiteralInteger", xmi));
			lowerValue.setAttribute(new Attribute("id", javaParser.getId(), xmi));
			return ownedAttribute.addContent(lowerValue);
		
	}
	private Element getXMIfor2Params(Namespace xmi, ClassMembers classMembers, Element ownedAttribute, String dataType) {

		String[] dataTypeArray = dataType.split("#");
		// System.out.println("Parent :"+dataTypeArray[0] +" Child :
		// "+dataTypeArray[1]);
		String firstParent = dataTypeArray[0].trim();
		String paramType = dataTypeArray[1].trim();
		// This need to be re looked - Now followed RSA behavior
		if (paramType.contains(",")) {
			String[] paramTypeArray = paramType.split(",");
			paramType = paramTypeArray[1].trim();
		}
		String classType;
		if (paramType.contains("@")) {
			String[] paramTypeArray = paramType.split("@");
			classType = paramTypeArray[1].trim();
		} else {
			classType = classMembers.getNameIdMap().get(paramType);
		}
		if (null != firstParent && firstParent.equalsIgnoreCase("List")) {
			ownedAttribute.setAttribute("isOrdered", "true");
			ownedAttribute.setAttribute("isUnique", "false");
		}
		if (null != classType) {
			ownedAttribute.setAttribute("type", classType);
		} else {
			Element type = new Element("type");
			type.setAttribute(new Attribute("type", "uml:PrimitiveType", xmi));
			if(primitiveDataTypeList.size()>0 && primitiveDataTypeList.contains(paramType))
			{
				type.setAttribute("href", "JavaPrimitiveTypes.library.xmi#" + paramType);
			}
			else
			{
				type.setAttribute("href", "http://schema.omg.org/spec/UML/2.2/uml.xml#"+paramType);
			}
			ownedAttribute.addContent(type);
		}
		if (null != firstParent && (firstParent.equalsIgnoreCase("List") || firstParent.equalsIgnoreCase("Map"))) {
			Element upperValue = new Element("upperValue");
			upperValue.setAttribute(new Attribute("type", "uml:LiteralUnlimitedNatural", xmi));
			upperValue.setAttribute(new Attribute("id", javaParser.getId(), xmi));
			upperValue.setAttribute("value", "*");
			ownedAttribute.addContent(upperValue);
			Element lowerValue = new Element("lowerValue");
			lowerValue.setAttribute(new Attribute("type", "uml:LiteralInteger", xmi));
			lowerValue.setAttribute(new Attribute("id", javaParser.getId(), xmi));
			ownedAttribute.addContent(lowerValue);
		}
		return ownedAttribute;
	}

	private String xmiForMap(Namespace xmi, String object, String param1, String param2, String actualId, Document doc,
			String projId, String name) {
		String param = null;
		String signatureRef = null;
		String formalRef = null;
		boolean actualRequired = false;
		int x = 0;
		if (object.equalsIgnoreCase("Map")) {
			formalRef = "mmi:///#jtp^name=K[jbintype^name=java.util.Map[project^id=";
			signatureRef = "mmi:///#jtsig^name=Map[jbintype^name=java.util.Map[project^id=";
			x = 2;
		} else if (object.equalsIgnoreCase("List")) {
			name = "ListOf" + param1;
			formalRef = "mmi:///#jtp^name=E[jbintype^name=java.util.List[project^id=";
			signatureRef = "mmi:///#jtsig^name=List[jbintype^name=java.util.List[project^id=";
			x = 1;
		}
		String ownedPackagedElementId = javaParser.getId();
		Element ownedPackagedElement = new Element("packagedElement");
		ownedPackagedElement.setAttribute("type", "uml:Interface", xmi);
		ownedPackagedElement.setAttribute("id", ownedPackagedElementId, xmi);
		ownedPackagedElement.setAttribute("name", name);
		Element templateBinding = new Element("templateBinding");
		templateBinding.setAttribute("type", "uml:TemplateBinding", xmi);
		templateBinding.setAttribute("id", javaParser.getId(), xmi);
		Element signature = new Element("signature");
		signature.setAttribute("type", "uml:RedefinableTemplateSignature", xmi);
		signature.setAttribute("href", signatureRef + projId + "]]$uml.RedefinableTemplateSignature");
		templateBinding.addContent(signature);
		for (int i = 1; i <= x; i++) {
			if (i == 1) {
				param = param1;
			} else {
				param = param2;
			}
			Element parameterSubstitution = new Element("parameterSubstitution");
			parameterSubstitution.setAttribute("type", "uml:TemplateParameterSubstitution", xmi);
			parameterSubstitution.setAttribute("id", javaParser.getId(), xmi);
			if (null == actualId) {
				actualId = javaParser.getId();
				actualRequired = true;
			}
			parameterSubstitution.setAttribute("actual", actualId);
			Element formal = new Element("formal");
			formal.setAttribute("type", "uml:ClassifierTemplateParameter", xmi);
			formal.setAttribute("href", formalRef + projId + "]]$uml.ClassifierTemplateParameter");
			parameterSubstitution.setContent(formal);
			if (actualRequired) {
				Element ownedActual = new Element("ownedActual");
				ownedActual.setAttribute("type", "uml:Class", xmi);
				ownedActual.setAttribute("id", actualId, xmi);
				ownedActual.setAttribute("name", param);
				parameterSubstitution.setContent(ownedActual);
			}
			templateBinding.addContent(parameterSubstitution);
		}
		//ownedPackagedElement.addContent(templateBinding);
		doc.getRootElement().addContent(ownedPackagedElement);
		return ownedPackagedElementId;
	}

	public Object convertJsonToJava(String jsonData, Object obj) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			obj = mapper.readValue(jsonData, Object.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public SourceCodeMethodMembers convertJsonToJava(String jsonData, SourceCodeMethodMembers obj) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			obj = mapper.readValue(jsonData, SourceCodeMethodMembers.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public List<SourceCodeMethodMembers> getMethodObjectList(List<String> methodList) {
		List<SourceCodeMethodMembers> methodsList = new ArrayList<SourceCodeMethodMembers>();
		for (String meth : methodList) {
			SourceCodeMethodMembers codeMethodMembers = new SourceCodeMethodMembers();
			ObjectMapper mapper = new ObjectMapper();
			try {
				/*
				 * mapper.configure(JsonParser.Feature.CANONICALIZE_FIELD_NAMES,
				 * true); mapper.configure(JsonParser.Feature.
				 * ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
				 * mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
				 * mapper.configure(JsonParser.Feature.
				 * ALLOW_NON_NUMERIC_NUMBERS, true);
				 * mapper.configure(JsonParser.Feature.
				 * ALLOW_NUMERIC_LEADING_ZEROS, true);
				 * mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,
				 * true); mapper.configure(JsonParser.Feature.
				 * ALLOW_UNQUOTED_CONTROL_CHARS, true);
				 * mapper.configure(JsonParser.Feature.
				 * ALLOW_UNQUOTED_FIELD_NAMES, true);
				 * mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
				 * mapper.configure(JsonParser.Feature.INTERN_FIELD_NAMES,
				 * true);
				 */

				codeMethodMembers = mapper.readValue(meth, SourceCodeMethodMembers.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			methodsList.add(codeMethodMembers);
		}
		// System.out.println(methodArray);
		return methodsList;
	}

	FieldModifier getFieldModifiers(String modifiers) {
		FieldModifier fieldModifier = new FieldModifier();
		String delimeter = ":";
		String modifierArray[] = modifiers.split(delimeter);
		for (String modifier : modifierArray) {
			if (modifier.trim().equalsIgnoreCase("private") || modifier.trim().equalsIgnoreCase("protected")
					|| modifier.trim().equalsIgnoreCase("public")) {
				fieldModifier.setVisiblity(modifier.trim());
			} else if (modifier.trim().equalsIgnoreCase("final")) {
				fieldModifier.setFinal(true);
			} else if (modifier.trim().equalsIgnoreCase("static")) {
				fieldModifier.setStatic(true);
			} else if (modifier.trim().equalsIgnoreCase("abstract")) {
				fieldModifier.setAbstract(true);
			}
		}
		return fieldModifier;
	}
	

	public List<String> getPrimitiveDataTypeList() {
		return primitiveDataTypeList;
	}

	public void setPrimitiveDataTypeList(List<String> primitiveDataTypeList) {
		primitiveDataTypeList.add("byte");
		primitiveDataTypeList.add("short");
		primitiveDataTypeList.add("int");
		primitiveDataTypeList.add("long");
		primitiveDataTypeList.add("float");
		primitiveDataTypeList.add("double");
		primitiveDataTypeList.add("char");
		primitiveDataTypeList.add("boolean");
		this.primitiveDataTypeList = primitiveDataTypeList;
	}
	
public  Map<String, List<SourceCodeClassMembers>> readXMI(String xmiFileName)
{
	SAXBuilder builder = new SAXBuilder();
	  /*File xmlFile = new File("D:/projects/biz/workspace/UMLWeb/UMLWeb.xmi");*/
	File xmlFile = new File(xmiFileName);
		List<SourceCodeClassMembers> classList = new ArrayList<SourceCodeClassMembers>();
		Map<String, String> nameIdMap = new HashMap<String, String>();

	  try {

		Document document = (Document) builder.build(xmlFile);
		Element rootNode = document.getRootElement();
		List<Element> list = rootNode.getChildren("packagedElement");
		for (int i = 0; i < list.size(); i++) {
		   Element node = (Element) list.get(i);
		   SourceCodeClassMembers classMembers = new SourceCodeClassMembers();
		   List<SourceCodeFieldMembers> fieldList = new ArrayList<SourceCodeFieldMembers>();
		   List<SourceCodeMethodMembers> methodList = new ArrayList<SourceCodeMethodMembers>();
		   Namespace xmi = Namespace.getNamespace("xmi", "http://schema.omg.org/spec/XMI/2.1");
		   classMembers.setName(node.getAttributeValue("name"));
		   classMembers.setId(node.getAttribute("id",xmi).getValue());
		   if(null!= node.getAttribute("type",xmi).getValue() && node.getAttribute("type",xmi).getValue().equalsIgnoreCase("uml:Class"))
		   {
		   classMembers.setType("uml.Class");
		   }
		   else if(null!= node.getAttribute("type",xmi).getValue() && node.getAttribute("type",xmi).getValue().equalsIgnoreCase("uml:Interface"))
		   {
			   classMembers.setType("uml.Interface");
		   }
		   nameIdMap.put(node.getAttributeValue("name"), node.getAttribute("id",xmi).getValue());
		   classMembers.setNameIdMap(nameIdMap);
		   /*classMembers.setPackageName(packageName);
		   classMembers.setImportList(importList);*/
		   List<String> classDependencyList = new ArrayList<String>(); 
		   if(0 < node.getChildren("generalization").size())
		   {
			   classMembers.setSuperClass(node.getChildren("generalization").get(0).getAttributeValue("general"));
		   }
		   if(0 < node.getChildren("interfaceRealization").size())
		   {
			   classDependencyList.add(node.getAttributeValue("clientDependency"));
			   classMembers.setSuperInterfaceList(classDependencyList);
		   }
		   else
		   {
			   classMembers.setSuperInterfaceList(classDependencyList);   
		   }
		   classMembers.setModifierList(getModifierList(node));
		   List<Element> variableList = node.getChildren("ownedAttribute");
		   for(Element var:variableList)
		   {
			   SourceCodeFieldMembers fieldMembers = new SourceCodeFieldMembers();
			   String dataType = getDataType(var);
			   getDataType(fieldMembers, dataType);
			   fieldMembers.setModifiers(getModifierList(var));
			   fieldMembers.setActualName(var.getAttributeValue("name"));
			   fieldMembers.setName(var.getAttributeValue("name"));
			   fieldMembers.setId(var.getAttribute("id",xmi).getValue());
			   fieldMembers.setSimpleName(var.getAttributeValue("name"));
			  if(var.getChildren("defaultValue").size()>0)
			  {
				   if(var.getChildren("defaultValue").get(0).getChildren("body").size()>0)
				   {
					   fieldMembers.setInitializer(var.getChildren("defaultValue").get(0).getChildren("body").get(0).getText());
				   }
			   }
			   fieldList.add(fieldMembers);
		   }
		   classMembers.setFeilds(fieldList);
		   List<Element> operationList = node.getChildren("ownedOperation");
		   for(Element meth:operationList)
		   {
			   SourceCodeMethodMembers methodMembers = new SourceCodeMethodMembers();
			   System.out.println("Meth Name: "+meth.getAttributeValue("name"));
			   methodMembers.setName(meth.getAttributeValue("name"));
			   methodMembers.setId(meth.getAttribute("id",xmi).getValue());
			   getTypeParams(meth, methodMembers);
			   methodMembers.setModifiers(getModifierList(meth));
			   methodMembers.setActualModifiers(getModifierList(meth));
			  // methodMembers.setBody(body);
			   methodList.add(methodMembers);
		   }
		   classMembers.setMethods(methodList);
		   classList.add(classMembers);
		}

	  } catch (IOException io) {
		System.out.println(io.getMessage());
	  } catch (JDOMException jdomex) {
		System.out.println(jdomex.getMessage());
	  }
	codeMap.put("", classList);
	return codeMap;
}

	private void getTypeParams(Element meth, SourceCodeMethodMembers methodMembers) {
		List<String> parametersList = new ArrayList<String>();
		List<String> parameterPair = new ArrayList<String>();
		if(meth.getChildren("ownedParameter").isEmpty() || meth.getChildren("ownedParameter").size() == 1)
		{
			methodMembers.setParameterPair(parameterPair);
			methodMembers.setParameters(parametersList);;
		}
		System.out.println("Size: "+meth.getChildren("ownedParameter").size());
		for (Element ownedParameter : meth.getChildren("ownedParameter")) {
			if ("ReturnParameter".equalsIgnoreCase(ownedParameter.getAttributeValue("name"))) {
				if (null != ownedParameter.getChildren("type") && !(ownedParameter.getChildren("type").isEmpty())) {
					for (Element type : ownedParameter.getChildren("type")) {
						getDataType(methodMembers, type.getAttributeValue("href"));
					}
				} else  if (ownedParameter.getChildren("type").isEmpty()){
					if(null != ownedParameter.getAttributeValue("type"))
					{
					getDataType(methodMembers, ownedParameter.getAttributeValue("type"));
					}
					else  {
						getDataType(methodMembers, "void");
					}
				}
				
			} 
			if (!("ReturnParameter".equalsIgnoreCase(ownedParameter.getAttributeValue("name")))) {
				String name  = ownedParameter.getAttributeValue("name");
				if (null != ownedParameter.getChildren("type") && !(ownedParameter.getChildren("type").isEmpty())) {
					for (Element type : ownedParameter.getChildren("type")) {
						getParamType(methodMembers,parametersList, parameterPair, name, type.getAttributeValue("href"));
					}
				} else {
					getParamType(methodMembers, parametersList, parameterPair, name, ownedParameter.getAttributeValue("type"));
				}
			}
		}
	}

private void getDataType(SourceCodeFieldMembers fieldMembers, String dataType) {
	if(dataType.startsWith("JavaPrimitiveTypes"))
	   {
		fieldMembers.setTypeOfDataType("Primitive");
	   }
	   else
	   {
		   fieldMembers.setTypeOfDataType("Simple");
	   }
	   if(dataType.contains("#"))
	   {
		   String[] dataTypeArray = dataType.split("#");
		   fieldMembers.setDataType(dataTypeArray[1]);
	   }
	   else
	   {
		   fieldMembers.setDataType(dataType);
	   }
}

private void getDataType(SourceCodeMethodMembers methMembers, String dataType) {
	if(dataType.startsWith("JavaPrimitiveTypes"))
	   {
		methMembers.setTypeOfReturnType("Primitive");
	   }
	   else
	   {
		   methMembers.setTypeOfReturnType("Simple");
	   }
	   if(dataType.contains("#"))
	   {
		   String[] dataTypeArray = dataType.split("#");
		   methMembers.setReturnType(dataTypeArray[1]);
	   }
	   else
	   {
		   methMembers.setReturnType(dataType);
	   }
}

private void getParamType(SourceCodeMethodMembers methMembers,List<String> parametersList, List<String> parameterPair, String name, String dataType) {
	StringBuffer paramPairBuffer =  new StringBuffer();
	StringBuffer paramBuffer =  new StringBuffer();
	
	paramPairBuffer.append(name).append(":");
	 if(dataType.contains("#"))
	   {
		   String[] dataTypeArray = dataType.split("#");
		   paramPairBuffer.append(dataTypeArray[1]);
		   paramBuffer.append(dataTypeArray[1]).append(" ");
	   }
	   else
	   {
		   paramBuffer.append(dataType).append(" ");
		   paramPairBuffer.append(dataType);
	   }
	if(dataType.startsWith("JavaPrimitiveTypes"))
	   {
		paramPairBuffer.append(":").append("Primitive");
	   }
	   else
	   {
		   paramPairBuffer.append(":").append("Simple");
	   }
		paramBuffer.append(name);
		parameterPair.add(paramPairBuffer.toString());
		parametersList.add(paramBuffer.toString());
	   methMembers.setParameterPair(parameterPair);
	   methMembers.setParameters(parametersList);;
}

private String getDataType(Element node) {
	String dataType = null;
	if(null != node.getAttributeValue("type"))
	   {
		dataType =  node.getAttributeValue("type");
	   }
	   else if(!node.getChildren("type").isEmpty())
	   {
		   dataType = node.getChildren("type").get(0).getAttributeValue("href");
	   }
	return dataType;
}

private List<String> getModifierList(Element node) {
	List<String> modifierList = new ArrayList<String>();
	if(null!= node.getAttributeValue("visibility"))
	   {
		modifierList.add(node.getAttributeValue("visibility"));
	   }
	   if(null!= node.getAttributeValue("isAbstract"))
	   {
		   modifierList.add("Abstract");
	   }
	return modifierList;
}

public void testReadXMI()
{
	XMIParser parser = new XMIParser();
	//File xmlFile = new File("D:/projects/biz/workspace/UMLWeb/UMLWeb.xmi");
	 Map<String, List<SourceCodeClassMembers>> javaMap = parser.readXMI("D:/projects/biz/workspace/UMLWeb/UMLWeb.xmi");
	 for (Entry<String, List<SourceCodeClassMembers>> mem : javaMap.entrySet()) {
	for(SourceCodeClassMembers classMembers:mem.getValue())
	{
		//System.out.println("Class: "+classMembers.getName());
		//System.out.println("Class Type: "+classMembers.getType());
		//System.out.println("Class Map: "+classMembers.getNameIdMap());
		//System.out.println("Class Modifier: "+classMembers.getModifierList());
		//System.out.println("Class Super Class: "+classMembers.getSuperClass());
		//System.out.println("Class Super Iface: "+classMembers.getSuperInterfaceList());
		List<SourceCodeFieldMembers> fieldList = classMembers.getFeilds();
		/*for(SourceCodeFieldMembers fieldMembers:fieldList)
		{
			//System.out.println("Field = "+fieldMembers.getName());
			//System.out.println("Field Data Type = "+fieldMembers.getDataType());
			//System.out.println("Field Modifiers = "+fieldMembers.getModifiers());
			//System.out.println("Field Initializer = "+fieldMembers.getInitializer());
		}*/
		List<SourceCodeMethodMembers> methodList = classMembers.getMethods();
		for(SourceCodeMethodMembers methMembers:methodList)
		{
			System.out.println("Mthod = "+methMembers.getName());
			//System.out.println("Retuern Type = "+methMembers.getReturnType());
			//System.out.println("getTypeOfReturnType = "+methMembers.getTypeOfReturnType());
			//System.out.println("getParameterPair = "+methMembers.getParameterPair());
			System.out.println("getParameters = "+methMembers.getParameters());
			//System.out.println("Field Modifiers = "+methMembers.getModifiers());
			//System.out.println("Field Initializer = "+fieldMembers.getInitializer());
		}
	}
	 }
}
}
