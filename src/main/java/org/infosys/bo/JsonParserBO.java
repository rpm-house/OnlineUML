package org.infosys.bo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SimpleType;
import org.infosys.vo.common.ClassMembers;
import org.infosys.vo.common.ColumnMembers;
import org.infosys.vo.common.DBProperties;
import org.infosys.vo.common.ERTables;
import org.infosys.vo.common.RelationMembers;
import org.infosys.vo.common.SourceCodeClassMembers;
import org.infosys.vo.common.SourceCodeFieldMembers;
import org.infosys.vo.common.SourceCodeMethodMembers;
import org.infosys.vo.common.TableMembers;
import org.infosys.vo.json.Attrs;
import org.infosys.vo.json.JavaJsonMapper;
import org.infosys.vo.json.JsonFormForJointJs;
import org.infosys.vo.json.Position;
import org.infosys.vo.json.Relations;
import org.infosys.vo.json.Size;
import org.infosys.vo.json.Source;
import org.infosys.vo.json.Target;
import org.infosys.vo.json.UmlClassAttrsRect;
import org.infosys.vo.json.UmlClassAttrsText;
import org.infosys.vo.json.UmlClassMethodsRect;
import org.infosys.vo.json.UmlClassMethodsText;
import org.infosys.vo.json.UmlClassNameRect;
import org.infosys.vo.json.UmlClassNameText;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

public class JsonParserBO {

	JavaParser javaParser = new JavaParser();
	XMIParser xmiParser = new XMIParser();
	DBGenerator dbGenerator =  new DBGenerator();

	public static void main(String[] args) {
		JsonParserBO jsonParser = new JsonParserBO();
		// jsonParser.javaToJsonParser();

		// jsonParser.getClassList();
		jsonParser.readXmi("D:/projects/biz/workspace/UMLWeb/UMLWeb.xmi");

	}

	public List<ClassMembers> getClassList() {
		String filePath = "D:/projects/biz/workspace/UMLWeb/json.txt";
		JSONObject jsonObject = null;
		Object obj = null;
		try {
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();

			obj = jsonParser.parse(new FileReader(filePath));
			// jsonObject = (JSONObject) jsonParser.parse(reader);
			System.out.println(obj);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getClassList(obj.toString());
	}

	public List<ClassMembers> getClassList(String data) {
		List<ClassMembers> classList = new ArrayList<ClassMembers>();
		List<RelationMembers> relationList = new ArrayList<RelationMembers>();
		List<SourceCodeMethodMembers> methodList;
		List<RelationMembers> classRelationList;
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
			System.out.println(jsonObject);
			JSONArray cells = (JSONArray) jsonObject.get("cells");
			JSONArray methodObjectList;
			System.out.println(cells);
			Iterator i1 = cells.iterator();
			while (i1.hasNext()) {
				JSONObject innerObj = (JSONObject) i1.next();
				if (null != innerObj.get("name") && !("".equalsIgnoreCase((String) innerObj.get("name")))) {
					methodList = new ArrayList<SourceCodeMethodMembers>();
					ClassMembers classMembers = new ClassMembers();
					classMembers.setClassName((String) innerObj.get("name"));
					classMembers.setId((String) innerObj.get("id"));
					classMembers.setNameIdMap((Map<String, String>) innerObj.get("nameIdMap"));
					classMembers.setType((String) innerObj.get("type").toString().replace('.', ':'));
					if(null != innerObj.get("attributeList"))
					{
					classMembers.setAttributeList((List<Map<String, String>>) innerObj.get("attributeList"));
					}
					else
					{
					classMembers.setAttributeList(getAttributeMap((List<String>) innerObj.get("attributes")));
					//classMembers.setAttributes((List<String>)innerObj.get("attributes"));
					}
					
					if(null != innerObj.get("methodList"))
					{
					classMembers.setMethodList((List<Map<String, String>>) innerObj.get("methodList"));
					}
					else
					{
						classMembers.setMethodList(getMethodMap((List<String>) innerObj.get("methods")));
						//classMembers.setMethods((List<String>) innerObj.get("methods"));
					}
					classMembers.setPackageName((String) innerObj.get("packageName"));
					classMembers.setImportList((List<String>) innerObj.get("importList"));
					classMembers.setClassModifiers((String) innerObj.get("classModifiers"));
					classMembers.setSuperInterfaces((String) innerObj.get("superInterfaces"));
					classMembers.setSuperClass((String) innerObj.get("superClass"));
					classList.add(classMembers);
				} else {
					RelationMembers relationMembers = new RelationMembers();
					relationMembers.setId((String) innerObj.get("id"));
					relationMembers.setType((String) innerObj.get("type").toString().replace('.', ':'));
					JSONObject srcObject = (JSONObject) innerObj.get("source");
					JSONObject targetObject = (JSONObject) innerObj.get("target");
					if (null != srcObject) {
						relationMembers.setSourceId((String) srcObject.get("id"));
					}
					if (null != targetObject) {
						relationMembers.setTargetId((String) targetObject.get("id"));
					}
					relationList.add(relationMembers);
				}

			}
			System.out.println("Class List :" + classList);
			System.out.println("Relation List : " + relationList);
			Iterator relationIterator = relationList.iterator();
			while (relationIterator.hasNext()) {
				RelationMembers relation = (RelationMembers) relationIterator.next();
				String srcId = relation.getSourceId();
				Iterator classIterator = classList.iterator();
				while (classIterator.hasNext()) {
					ClassMembers classes = (ClassMembers) classIterator.next();
					if (classes.getId().equalsIgnoreCase(srcId)) {
						classes.getRelationList().add(relation);
					}

				}
			}
			Iterator i2 = classList.iterator();
			while (i2.hasNext()) {
				System.out.println(i2.next());
			}

		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return classList;
	}

	public SourceCodeMethodMembers convertJsonToJava(String jsonData, SourceCodeMethodMembers obj) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
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
	/*
	 * public List<ClassMembers> getClassList(String data) { List<ClassMembers>
	 * classList = new ArrayList<ClassMembers>(); List<ClassMembers>
	 * relationList = new ArrayList<ClassMembers>(); List<ClassMembers>
	 * classRelationList = new ArrayList<ClassMembers>(); try { JSONParser
	 * jsonParser = new JSONParser(); JSONObject jsonObject = (JSONObject)
	 * jsonParser.parse(data); System.out.println(jsonObject); JSONArray cells =
	 * (JSONArray) jsonObject.get("cells"); System.out.println(cells); Iterator
	 * i1 = cells.iterator(); // take each value from the json array separately
	 * while (i1.hasNext()) { ClassMembers classMembers = new ClassMembers();
	 * JSONObject innerObj = (JSONObject) i1.next(); if (null !=
	 * innerObj.get("name") && !("".equalsIgnoreCase((String)
	 * innerObj.get("name")))) { classMembers.setClassName((String)
	 * innerObj.get("name")); classMembers.setId((String) innerObj.get("id"));
	 * classMembers.setType((String)
	 * innerObj.get("type").toString().replace('.', ':'));
	 * classMembers.setAttributes((List<String>) innerObj.get("attributes"));
	 * classMembers.setMethods((List<String>) innerObj.get("methods"));
	 * classList.add(classMembers); } else { classMembers.setId((String)
	 * innerObj.get("id")); classMembers.setType((String)
	 * innerObj.get("type").toString().replace('.', ':'));
	 * classMembers.setAttributes((List<String>) innerObj.get("attributes"));
	 * JSONObject srcObject = (JSONObject) innerObj.get("source"); JSONObject
	 * targetObject = (JSONObject) innerObj.get("target"); System.out.println(
	 * "Source: "+srcObject); System.out.println("Target: "+targetObject);
	 * if(null != srcObject) { System.out.println("Source Id: "
	 * +srcObject.get("id")); classMembers.setSourceId((String)
	 * srcObject.get("id")); } if(null != targetObject) { System.out.println(
	 * "Target Id: "+targetObject.get("id")); classMembers.setTargetId((String)
	 * targetObject.get("id")); } relationList.add(classMembers); }
	 * 
	 * } System.out.println("Class List :"+classList); System.out.println(
	 * "Relation List : "+relationList); Iterator relationIterator =
	 * relationList.iterator(); // take each value from the json array
	 * separately while (relationIterator.hasNext()) { ClassMembers relation =
	 * (ClassMembers) relationIterator.next(); String srcId =
	 * relation.getSourceId(); Iterator classIterator = classList.iterator(); //
	 * take each value from the json array separately while
	 * (classIterator.hasNext()) { ClassMembers classes = (ClassMembers)
	 * classIterator.next(); if(classes.getId().equalsIgnoreCase(srcId)) {
	 * classRelationList.add(relation); classes.setRelations(classRelationList);
	 * }
	 * 
	 * } classList.remove(relation); }
	 * 
	 * 
	 * 
	 * Iterator i2 = classList.iterator(); // take each value from the json
	 * array separately while (i2.hasNext()) { System.out.println(i2.next()); }
	 * 
	 * } catch (ParseException ex) { ex.printStackTrace(); } catch
	 * (NullPointerException ex) { ex.printStackTrace(); } return classList; }
	 */

	/*
	 * public List<ClassMembers> getClasses() { String filePath =
	 * "D:\\projects\\KBE\\java2json\\20.txt"; List<ClassMembers> classList =
	 * new ArrayList<ClassMembers>(); List<ClassMembers> relationList = new
	 * ArrayList<ClassMembers>(); List<ClassMembers> classRelationList = new
	 * ArrayList<ClassMembers>();
	 * 
	 * try { // read the json file FileReader reader = new FileReader(filePath);
	 * 
	 * JSONParser jsonParser = new JSONParser(); JSONObject jsonObject =
	 * (JSONObject) jsonParser.parse(reader); System.out.println(jsonObject);
	 * JSONArray cells = (JSONArray) jsonObject.get("cells");
	 * System.out.println(cells); Iterator i1 = cells.iterator(); // take each
	 * value from the json array separately while (i1.hasNext()) { ClassMembers
	 * classMembers = new ClassMembers(); JSONObject innerObj = (JSONObject)
	 * i1.next(); if (null != innerObj.get("name") &&
	 * !("".equalsIgnoreCase((String) innerObj.get("name")))) {
	 * classMembers.setClassName((String) innerObj.get("name"));
	 * classMembers.setId((String) innerObj.get("id"));
	 * classMembers.setType((String)
	 * innerObj.get("type").toString().replace('.', ':'));
	 * classMembers.setAttributes((List<String>) innerObj.get("attributes"));
	 * classMembers.setMethods((List<String>) innerObj.get("methods"));
	 * classList.add(classMembers); } else { classMembers.setId((String)
	 * innerObj.get("id")); classMembers.setType((String)
	 * innerObj.get("type").toString().replace('.', ':'));
	 * classMembers.setAttributes((List<String>) innerObj.get("attributes"));
	 * JSONObject srcObject = (JSONObject) innerObj.get("source"); JSONObject
	 * targetObject = (JSONObject) innerObj.get("target"); System.out.println(
	 * "Source: "+srcObject); System.out.println("Target: "+targetObject);
	 * if(null != srcObject) { System.out.println("Source Id: "
	 * +srcObject.get("id")); classMembers.setSourceId((String)
	 * srcObject.get("id")); } if(null != targetObject) { System.out.println(
	 * "Target Id: "+targetObject.get("id")); classMembers.setTargetId((String)
	 * targetObject.get("id")); } relationList.add(classMembers); }
	 * 
	 * } System.out.println("Class List :"+classList); System.out.println(
	 * "Relation List : "+relationList); Iterator relationIterator =
	 * relationList.iterator(); // take each value from the json array
	 * separately while (relationIterator.hasNext()) { ClassMembers relation =
	 * (ClassMembers) relationIterator.next(); String srcId =
	 * relation.getSourceId(); Iterator classIterator = classList.iterator(); //
	 * take each value from the json array separately while
	 * (classIterator.hasNext()) { ClassMembers classes = (ClassMembers)
	 * classIterator.next(); if(classes.getId().equalsIgnoreCase(srcId)) {
	 * classRelationList.add(relation); classes.setRelations(classRelationList);
	 * }
	 * 
	 * } classList.remove(relation); }
	 * 
	 * 
	 * 
	 * Iterator i2 = classList.iterator(); // take each value from the json
	 * array separately while (i2.hasNext()) { System.out.println(i2.next()); }
	 * 
	 * } catch (FileNotFoundException ex) { ex.printStackTrace(); } catch
	 * (IOException ex) { ex.printStackTrace(); } catch (ParseException ex) {
	 * ex.printStackTrace(); } catch (NullPointerException ex) {
	 * ex.printStackTrace(); } return classList; }
	 */
	private static List<ERTables> getTables() {
		String filePath = "D:\\projects\\KBE\\uml_Json\\ERDgm1.txt";
		List<ERTables> tableList = new ArrayList<ERTables>();

		try {
			// read the json file
			FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			System.out.println(jsonObject);
			JSONArray cells = (JSONArray) jsonObject.get("cells");
			System.out.println(cells);
			Iterator i1 = cells.iterator();
			// take each value from the json array separately
			while (i1.hasNext()) {
				ERTables erTables = new ERTables();
				JSONObject innerObj = (JSONObject) i1.next();
				if (null != innerObj.get("name") && !("".equalsIgnoreCase((String) innerObj.get("name")))) {
					erTables.setTableName((String) innerObj.get("name"));
					erTables.setAttributes((List<String>) innerObj.get("attributes"));
					erTables.setKeys((List<String>) innerObj.get("methods"));
					tableList.add(erTables);
				}
			}

			Iterator i2 = tableList.iterator();
			// take each value from the json array separately
			while (i2.hasNext()) {
				System.out.println(i2.next());
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return tableList;
	}

	public List<ERTables> getTableList(String jsonString) {
		// String filePath = "D:\\projects\\KBE\\uml_Json\\ERDgm1.txt";
		List<ERTables> tableList = new ArrayList<ERTables>();

		try {
			// read the json file
			// FileReader reader = new FileReader(filePath);

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
			System.out.println(jsonObject);
			JSONArray cells = (JSONArray) jsonObject.get("cells");
			System.out.println(cells);
			Iterator i1 = cells.iterator();
			// take each value from the json array separately
			while (i1.hasNext()) {
				ERTables erTables = new ERTables();
				JSONObject innerObj = (JSONObject) i1.next();
				if (null != innerObj.get("name") && !("".equalsIgnoreCase((String) innerObj.get("name")))) {
					erTables.setTableName((String) innerObj.get("name"));
					erTables.setAttributes((List<String>) innerObj.get("attributes"));
					erTables.setKeys((List<String>) innerObj.get("methods"));
					tableList.add(erTables);
				}
			}

			Iterator i2 = tableList.iterator();
			// take each value from the json array separately
			while (i2.hasNext()) {
				System.out.println(i2.next());
			}

		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return tableList;
	}

	private static List<String> queryForCreateTable(List<ERTables> tableList) {
		List<String> queryList = new ArrayList<String>();
		List<String> primaryKeyList;
		List<String> foriegnKeyList;
		StringBuffer createQuery = null;
		String[] temp;
		String delimiter = ":";
		Iterator<ERTables> iterator = tableList.iterator();
		while (iterator.hasNext()) {
			createQuery = new StringBuffer();
			primaryKeyList = new ArrayList<String>();
			foriegnKeyList = new ArrayList<String>();
			ERTables erTables = iterator.next();
			createQuery.append("CREATE TABLE  ").append(erTables.getTableName()).append(" ( ");
			Iterator<String> attributeIterator = erTables.getAttributes().iterator();
			while (attributeIterator.hasNext()) {
				createQuery.append(" ");
				String column = attributeIterator.next();
				temp = column.split(delimiter);
				createQuery.append(temp[0]).append(" ").append(temp[1]).append(" , ");
			}
			Iterator<String> keyIterator = erTables.getKeys().iterator();
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				temp = key.split(delimiter);
				if (temp[1].trim().equalsIgnoreCase("Primary")) {
					primaryKeyList.add(temp[0]);
				} else if (temp[1].trim().equalsIgnoreCase("Foriegn")) {
					foriegnKeyList.add(temp[0]);
				}
			}
			int pKey = 0;
			int fKey = 0;
			Iterator<String> pKeyIterator = primaryKeyList.iterator();
			while (pKeyIterator.hasNext()) {
				createQuery.append(" ");
				if (pKey == 0) {
					createQuery.append("PRIMARY KEY (").append(pKeyIterator.next());
				} else {
					createQuery.append(" , ").append(pKeyIterator.next());
				}

				pKey++;
			}
			createQuery.append(" ) ");

			Iterator<String> fKeyIterator = foriegnKeyList.iterator();
			while (fKeyIterator.hasNext()) {
				createQuery.append("  , ");
				if (fKey == 0) {
					createQuery.append("Foriegn KEY (").append(fKeyIterator.next());
				} else {
					createQuery.append(" , ").append(fKeyIterator.next());
				}

				fKey++;
			}
			createQuery.append(" ) ");

			createQuery.append(" ); ");
			System.out.println("createQuery : " + createQuery);
			queryList.add(createQuery.toString());
		}

		System.out.println("createQueryList : " + queryList);
		return queryList;

	}

	void javaToJsonParser() {
		String jsonFilePath = "D:\\projects\\biz\\workspace\\UMLWeb\\json.txt";
		// String path =
		// "D:\\projects\\KBE\\KBE_Framework\\KBE_Framework\\KBE_Framework";
		// String path =
		// "D:\\projects\\biz\\workspace\\UMLWeb\\src\\main\\java\\org\\infosys";
		String path = "D:\\projects\\biz\\workspace\\UMLWeb";
		// String path =
		// "D:\\projects\\KBE\\KBE_Framework\\KBE_Framework\\KBE_Framework";
		// System.out.println("Path :"+path);
		// String dirPath = path + File.separator + "src" + File.separator;
		try {
			FileWriter writer = new FileWriter(jsonFilePath);
			String json = new Gson().toJson(javaToJsonParser(path));
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String readXmi(String xmiFileName) {
		Map<String, List<SourceCodeClassMembers>> javaMap = null;
		javaMap = xmiParser.readXMI(xmiFileName);
		return new Gson().toJson(javaJsonParser(javaMap));
	}

	public String javaToJsonParser(String path) {
		String dirPath = path + File.separator + "src" + File.separator;
		Map<String, List<SourceCodeClassMembers>> javaMap = null;
		try {
			javaMap = javaParser.parseFilesInDir(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Gson().toJson(javaJsonParser(javaMap));

	}
	
	public String dbJsonParser(DBProperties dbProperties) {
		List<TableMembers> tableMemberList = dbGenerator.getTables(dbProperties);
		List<JavaJsonMapper> cells = new ArrayList<JavaJsonMapper>();
		JsonFormForJointJs jsonFormForJointJs = new JsonFormForJointJs();
		int w = 1;
		int x = 20;
		int y = 40;
		int z = 10;
		int cols = 5;
		int h = 0;
			for (TableMembers tableMembers : tableMemberList) {
				JavaJsonMapper mapper = new JavaJsonMapper();
				mapper.setName(tableMembers.getName());
				mapper.setType("uml.Class");
				mapper.setId(javaParser.getId());
				Map columnMap = getColumns(tableMembers.getColumnList());
				mapper.setAttributes((List<String>) columnMap.get("column"));
				mapper.setMethods((List<String>) columnMap.get("key"));
				mapper.setAttrs(getAttrs(tableMembers));
				int columnSize = ((List<String>) (columnMap.get("column"))).size();
				int keySize = ((List<String>) (columnMap.get("key"))).size();
				
				if ((columnSize + keySize) <= 5) {
					h = 5;
				} else {
					h = columnSize + keySize;
				}
				mapper.setSize(getSize(h));
				mapper.setPosition(getPossition(x, y));
				mapper.setAngle(0);
				mapper.setZ(z);
				cells.add(mapper);
				y = y + h * 20 + 20;
				if (w % cols == 0) {
					x = x + 500;
					y = 40;
				}
				z = z + 100;
				w++;
			}
			jsonFormForJointJs.setCells(cells);
			return new Gson().toJson(jsonFormForJointJs);
	}

	public JsonFormForJointJs javaJsonParser(Map<String, List<SourceCodeClassMembers>> javaMap) {
		List<JavaJsonMapper> cells = new ArrayList<JavaJsonMapper>();
		JsonFormForJointJs jsonFormForJointJs = new JsonFormForJointJs();
		int w = 1;
		int x = 20;
		int y = 40;
		int z = 10;
		int cols = 5;
		int h = 0;
		for (Entry<String, List<SourceCodeClassMembers>> mem : javaMap.entrySet()) {
			for (SourceCodeClassMembers classMembers : mem.getValue()) {
				JavaJsonMapper mapper = new JavaJsonMapper();
				mapper.setName(classMembers.getName());
				mapper.setId(classMembers.getId());
				mapper.setNameIdMap(classMembers.getNameIdMap());
				mapper.setType(classMembers.getType());
				mapper.setAttributes(getAttributes(classMembers.getFeilds()));
				mapper.setAttributeList(getAttributeMapList(classMembers.getFeilds()));
				mapper.setMethods(getMethods(classMembers.getMethods()));
				mapper.setMethodList(getMethodMapList(classMembers.getMethods()));
				mapper.setPackageName(classMembers.getPackageName());
				mapper.setImportList(classMembers.getImportList());
				if (null != classMembers.getModifierList() && (!(classMembers.getModifierList().isEmpty()))) {
					mapper.setClassModifiers(getClassModifierList(classMembers.getModifierList()));
				} else if (null != classMembers.getModifiers() && (!(classMembers.getModifiers().isEmpty()))) {
					mapper.setClassModifiers(getClassModifiers(classMembers.getModifiers()));
				}
				if (null != classMembers.getSuperInterfaceList()
						&& (!(classMembers.getSuperInterfaceList().isEmpty()))) {
					mapper.setSuperInterfaces(getSuperIfaceList(classMembers.getSuperInterfaceList()));
				} else if (null != classMembers.getSuperInterfaces()
						&& (!(classMembers.getSuperInterfaces().isEmpty()))) {
					mapper.setSuperInterfaces(getSuperIfaces(classMembers.getSuperInterfaces()));
				}
				mapper.setSuperClass(classMembers.getSuperClass());
				mapper.setAttrs(getAttrs(classMembers));
				if ((classMembers.getFeilds().size() + classMembers.getMethods().size()) <= 5) {
					h = 5;
				} else {
					h = classMembers.getFeilds().size() + classMembers.getMethods().size();
				}
				mapper.setSize(getSize(h));
				mapper.setPosition(getPossition(x, y));
				mapper.setAngle(0);
				mapper.setZ(z);
				cells.add(mapper);
				/*
				 * x = x + 500; if(w%cols==0) { y = y + 180; x = 10; } z = z +
				 * 100; w++;
				 */

				// y = y + 180;
				y = y + h * 20 + 20;
				if (w % cols == 0) {
					x = x + 500;
					y = 40;
				}
				z = z + 100;
				w++;

			}

		}

		for (Relations relations : javaParser.getRelationList()) {
			String targetId = javaParser.getNameIdMap().get(relations.getTargetName());
			if ((null != targetId) && !("".equalsIgnoreCase(targetId))) {
				JavaJsonMapper mapper = new JavaJsonMapper();
				mapper.setType(relations.getType());
				mapper.setZ(z);
				mapper.setId(relations.getId());
				Source source = new Source();
				source.setId(relations.getSourceId());
				mapper.setSource(source);
				Target target = new Target();
				target.setId(targetId);
				mapper.setTarget(target);
				cells.add(mapper);
				/*
				 * x = x + 200; if(w%5==0) { y = y + 180; x = 10; } z = z + 100;
				 * w++;
				 */

				// y = y + 180;
				y = y + h * 20 + 20;
				if (w % cols == 0) {
					x = x + 500;
					y = 40;
				}
				z = z + 100;
				w++;
			}

		}
		jsonFormForJointJs.setCells(cells);
		System.out.println("Json Data:" + jsonFormForJointJs);
		System.out.println("Json::" + jsonFormForJointJs);
		return jsonFormForJointJs;

	}

	public List<String> getMethods(List<SourceCodeMethodMembers> methodList) {
		List<String> methodArray = new ArrayList<String>();
		for (SourceCodeMethodMembers meth : methodList) {
			StringBuffer method = new StringBuffer();
			for (Object methMod : meth.getModifiers()) {
				method.append(methMod);
				method.append(" ");
			}
			if (null != meth.getReturnType()) {
				method.append(meth.getReturnType());
			}
			method.append(" ");
			method.append(meth.getName());
			method.append("(");
			for (Object params : meth.getParameters()) {
				method.append(params);
				method.append(" , ");
			}
			if (0 < method.lastIndexOf(","))
				method.replace(method.lastIndexOf(","), method.lastIndexOf(",") + 1, "");
			method.append(")");
			methodArray.add(method.toString());
		}
		// System.out.println(methodArray);
		return methodArray;
	}

	public List<String> getMethodLists(List<SourceCodeMethodMembers> methodList) {
		List<String> methodArray = new ArrayList<String>();
		for (SourceCodeMethodMembers meth : methodList) {
			StringBuffer method = new StringBuffer();
			for (Object methMod : meth.getModifiers()) {
				method.append("Modifiers:");
				method.append(methMod);
				method.append(", ");
			}
			if (0 < method.lastIndexOf(","))
				method.replace(method.lastIndexOf(","), method.lastIndexOf(",") + 1, "#");
			if (null != meth.getReturnType()) {
				method.append("ReturnType:");
				method.append(meth.getReturnType());
			}
			method.append("#");
			method.append("Id:");
			method.append(meth.getId());
			method.append("#");
			method.append("Name:");
			method.append(meth.getName());
			method.append("#");
			for (Object params : meth.getParameters()) {
				method.append("Params:");
				method.append(params);
				method.append(" , ");
			}
			if (0 < method.lastIndexOf(","))
				method.replace(method.lastIndexOf(","), method.lastIndexOf(",") + 1, "#");
			method.append("Body:");
			method.append(meth.getBody());
			methodArray.add(method.toString());
		}
		System.out.println(methodArray);
		return methodArray;
	}

	public List<Map<String, String>> getMethodMapList(List<SourceCodeMethodMembers> methodList) {
		List<Map<String, String>> methodArray = new ArrayList<Map<String, String>>();
		Map<String, String> methodMap;
		for (SourceCodeMethodMembers meth : methodList) {
			methodMap = new HashMap<String, String>();
			if (null != meth.getActualModifiers() && meth.getActualModifiers().size() > 0) {
				StringBuffer methodModBuffer = new StringBuffer();
				for (Object methMod : meth.getActualModifiers()) {
					methodModBuffer.append(methMod);
					methodModBuffer.append(": ");

				}
				if (0 < methodModBuffer.lastIndexOf(":")) {
					methodModBuffer.replace(methodModBuffer.lastIndexOf(":"), methodModBuffer.lastIndexOf(":") + 1, "");
				}
				// System.out.println("methMod:"+methodModBuffer.toString());
				methodMap.put("ActualModifiers", methodModBuffer.toString());
			}
			if (null != meth.getModifiers() && meth.getModifiers().size() > 0) {
				StringBuffer methodModBuffer = new StringBuffer();
				for (Object methMod : meth.getModifiers()) {
					methodModBuffer.append(methMod);
					methodModBuffer.append(": ");

				}
				if (0 < methodModBuffer.lastIndexOf(":")) {
					methodModBuffer.replace(methodModBuffer.lastIndexOf(":"), methodModBuffer.lastIndexOf(":") + 1, "");
				}
				methodMap.put("Modifiers", methodModBuffer.toString());
			}
			if (null != meth.getTypeOfReturnType()) {
				methodMap.put("TypeOfReturnType", meth.getTypeOfReturnType());
			}
			if (null != meth.getReturnType()) {
				methodMap.put("ReturnType", meth.getReturnType());
			}
			if (null != meth.getId()) {
				methodMap.put("Id", meth.getId());
			}
			if (null != meth.getName()) {
				methodMap.put("Name", meth.getName());
			}

			if (null != meth.getParameters() && meth.getParameters().size() > 0) {
				StringBuffer methodParamBuffer = new StringBuffer();
				for (Object params : meth.getParameters()) {
					methodParamBuffer.append(params);
					methodParamBuffer.append(" , ");
				}
				if (0 < methodParamBuffer.lastIndexOf(",")) {
					methodParamBuffer.replace(methodParamBuffer.lastIndexOf(","),
							methodParamBuffer.lastIndexOf(",") + 1, "");
				}
				methodMap.put("Params", methodParamBuffer.toString());
			}
			if (null != meth.getParameterPair() && meth.getParameterPair().size() > 0) {
				StringBuffer methodParamBuffer = new StringBuffer();
				for (Object params : meth.getParameterPair()) {
					methodParamBuffer.append(params);
					methodParamBuffer.append(" & ");
				}
				if (0 < methodParamBuffer.lastIndexOf("&")) {
					methodParamBuffer.replace(methodParamBuffer.lastIndexOf("&"),
							methodParamBuffer.lastIndexOf("&") + 1, "");
				}
				methodMap.put("ParamPair", methodParamBuffer.toString());
			}

			/*
			 * if(null != meth.getBody()) { methodMap.put("Body",
			 * meth.getBody()); }
			 */
			methodArray.add(methodMap);
		}
		// System.out.println("Mathod Arry: "+methodArray);
		return methodArray;
	}

	public List<Map<String, String>> getAttributeMapList(List<SourceCodeFieldMembers> fieldList) {
		List<Map<String, String>> fieldArray = new ArrayList<Map<String, String>>();
		Map<String, String> fieldMap;
		for (SourceCodeFieldMembers field : fieldList) {
			fieldMap = new HashMap<String, String>();
			fieldMap.put("Id", field.getId());
			fieldMap.put("ActualName", field.getActualName());
			fieldMap.put("Name", field.getName());
			fieldMap.put("SimpleName", field.getSimpleName());
			// System.out.println(field.getSimpleName()+"::"
			// +field.getDataType());
			if (null != field.getTypeOfDataType()) {
				fieldMap.put("TypeOfDataType", field.getTypeOfDataType());
			}
			if (null != field.getDataType()) {
				fieldMap.put("DataType", field.getDataType());
			}

			if (null != field.getModifiers() && field.getModifiers().size() > 0) {
				StringBuffer fieldModBuffer = new StringBuffer();
				for (Object fieldMod : field.getModifiers()) {
					fieldModBuffer.append(fieldMod);
					fieldModBuffer.append(": ");
				}
				if (0 < fieldModBuffer.lastIndexOf(":")) {
					fieldModBuffer.replace(fieldModBuffer.lastIndexOf(":"), fieldModBuffer.lastIndexOf(":") + 1, "");
				}
				fieldMap.put("Modifiers", fieldModBuffer.toString());
			}
			fieldMap.put("Initializer", field.getInitializer());
			fieldArray.add(fieldMap);
		}
		return fieldArray;
	}

	
	
	public String getClassModifiers(List<Modifier> modifiersList) {
		StringBuffer modBuffer = new StringBuffer();
		if (modifiersList.isEmpty()) {
			modBuffer.append("public");
		} else {
			for (Object mod : modifiersList) {
				modBuffer.append(mod);
				modBuffer.append(":");

			}
		}
		return modBuffer.toString();
	}

	public String getClassModifierList(List<String> modifiersList) {
		StringBuffer modBuffer = new StringBuffer();
		if (modifiersList.isEmpty()) {
			modBuffer.append("public");
		} else {
			for (Object mod : modifiersList) {
				modBuffer.append(mod);
				modBuffer.append(":");

			}
		}
		return modBuffer.toString();
	}

	public String getSuperIfaces(List<SimpleType> ifaceList) {
		StringBuffer ifaceBuffer = new StringBuffer();
		for (Object iface : ifaceList) {
			ifaceBuffer.append(iface);
			ifaceBuffer.append(",");
		}
		return ifaceBuffer.toString();
	}

	public String getSuperIfaceList(List<String> ifaceList) {
		StringBuffer ifaceBuffer = new StringBuffer();
		for (Object iface : ifaceList) {
			ifaceBuffer.append(iface);
			ifaceBuffer.append(",");
		}
		return ifaceBuffer.toString();
	}

	public List<String> getMethodList(List<SourceCodeMethodMembers> methodList) {
		List<String> methodsList = new ArrayList<String>();
		Map<String, String> methodMap;
		for (SourceCodeMethodMembers meth : methodList) {
			// SourceCodeMethodMembers codeMethodMembers = new
			// SourceCodeMethodMembers();
			// codeMethodMembers = meth;
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = null;
			try {
				jsonInString = mapper.writeValueAsString(meth);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("jsonInString:" + jsonInString);
			methodsList.add(jsonInString);
			/*
			 * //SourceCodeMethodMembers codeMethodMembers = new
			 * SourceCodeMethodMembers(); codeMethodMembers.setId(meth.getId());
			 * codeMethodMembers.setModifiers(meth.getActualModifiers());
			 * codeMethodMembers.setReturnType(meth.getReturnType());
			 * codeMethodMembers.setName(meth.getName());
			 * codeMethodMembers.setParameters(meth.getParameters());
			 * codeMethodMembers.setBody(meth.getBody());
			 * methodsList.add(codeMethodMembers.toString());
			 */
			/*
			 * methodMap = new HashMap<String, String>(); methodMap.put("id",
			 * meth.getId()); methodMap.put("modifiers",
			 * meth.getActualModifiers().toString()); methodMap.put("id",
			 * meth.getReturnType()); methodMap.put("id",
			 * meth.getParameters().toString());
			 */

		}
		// System.out.println(methodArray);
		return methodsList;
	}
	
	public Map<String, List<String>> getColumns(List<ColumnMembers> columnList) {
		List<String> columnArray = new ArrayList<String>();
		List<String> keyArray = new ArrayList<String>();
		Map<String, List<String>> columnMap = new HashMap<String, List<String>>();
		for (ColumnMembers column : columnList) {
			columnArray.add(column.getName()+ ": "+ column.getType());
			if(null != column.getKey() && !(column.getKey().equalsIgnoreCase("")))
			{
			keyArray.add(column.getName()+ ": "+ column.getKey());
			}
		}
		columnMap.put("column", columnArray);
		columnMap.put("key", keyArray);
		return columnMap;
	}

	public List<String> getAttributes(List<SourceCodeFieldMembers> fieldList) {
		List<String> attrArray = new ArrayList<String>();
		for (SourceCodeFieldMembers field : fieldList) {
			attrArray.add(field.getName().toString());
		}
		// System.out.println(attrArray);
		return attrArray;
	}

	public List<String> getAttributeList(List<SourceCodeFieldMembers> fieldList) {
		List<String> attrArray = new ArrayList<String>();
		for (SourceCodeFieldMembers field : fieldList) {
			SourceCodeFieldMembers codeFieldMembers = new SourceCodeFieldMembers();
			codeFieldMembers.setActualName(field.getActualName());
			codeFieldMembers.setId(field.getId());
			attrArray.add(codeFieldMembers.toString());
		}
		return attrArray;
	}

	public Attrs getAttrs(SourceCodeClassMembers classMembers) {
		Attrs attrs = new Attrs();
		UmlClassAttrsRect umlClassAttrsRect = new UmlClassAttrsRect();
		UmlClassMethodsRect umlClassMethodsRect = new UmlClassMethodsRect();
		UmlClassNameRect umlClassNameRect = new UmlClassNameRect();
		umlClassNameRect.setTransform("translate(0,0)");
		umlClassNameRect.setHeight(40);
		umlClassAttrsRect.setTransform("translate(0,40)");
		umlClassAttrsRect.setHeight(40);
		umlClassMethodsRect.setTransform("translate(0,80)");
		umlClassMethodsRect.setHeight(40);

		UmlClassNameText umlClassNameText = new UmlClassNameText();
		UmlClassAttrsText umlClassAttrsText = new UmlClassAttrsText();
		UmlClassMethodsText umlClassMethodsText = new UmlClassMethodsText();
		umlClassNameText.setText(classMembers.getName());
		umlClassAttrsText.setText(getAttributes(classMembers.getFeilds()).toString());
		umlClassMethodsText.setText(getMethods(classMembers.getMethods()).toString());
		attrs.setUmlClassNameRect(umlClassNameRect);
		attrs.setUmlClassAttrsRect(umlClassAttrsRect);
		attrs.setUmlClassMethodsRect(umlClassMethodsRect);
		attrs.setUmlClassNameText(umlClassNameText);
		attrs.setUmlClassAttrsText(umlClassAttrsText);
		attrs.setUmlClassMethodsText(umlClassMethodsText);
		return attrs;

	}
	public Attrs getAttrs(TableMembers tableMembers) {
		Attrs attrs = new Attrs();
		UmlClassAttrsRect umlClassAttrsRect = new UmlClassAttrsRect();
		UmlClassMethodsRect umlClassMethodsRect = new UmlClassMethodsRect();
		UmlClassNameRect umlClassNameRect = new UmlClassNameRect();
		umlClassNameRect.setTransform("translate(0,0)");
		umlClassNameRect.setHeight(40);
		umlClassAttrsRect.setTransform("translate(0,40)");
		umlClassAttrsRect.setHeight(40);
		umlClassMethodsRect.setTransform("translate(0,80)");
		umlClassMethodsRect.setHeight(40);

		UmlClassNameText umlClassNameText = new UmlClassNameText();
		UmlClassAttrsText umlClassAttrsText = new UmlClassAttrsText();
		UmlClassMethodsText umlClassMethodsText = new UmlClassMethodsText();
		umlClassNameText.setText(tableMembers.getName());
		umlClassAttrsText.setText(getColumns(tableMembers.getColumnList()).get("column").toString());
		umlClassMethodsText.setText(getColumns(tableMembers.getColumnList()).get("key").toString());
		attrs.setUmlClassNameRect(umlClassNameRect);
		attrs.setUmlClassAttrsRect(umlClassAttrsRect);
		attrs.setUmlClassMethodsRect(umlClassMethodsRect);
		attrs.setUmlClassNameText(umlClassNameText);
		attrs.setUmlClassAttrsText(umlClassAttrsText);
		attrs.setUmlClassMethodsText(umlClassMethodsText);
		return attrs;

	}

	public Size getSize(int h) {
		Size size = new Size();
		size.setHeight(h * 20);
		size.setWidth(400);
		return size;

	}

	public Position getPossition(int x, int y) {
		Position possition = new Position();
		possition.setX(x);
		possition.setY(y);
		return possition;

	}
	
	public List<Map<String, String>> getAttributeMap(List<String> attributes) {
		List<Map<String, String>> fieldArray = new ArrayList<Map<String, String>>();
		Map<String, String> fieldMap;
		StringBuffer fieldModBuffer;
		StringBuffer actualNameBuffer;
		
		for(String field: attributes )
		{
			fieldMap = new HashMap<String, String>();
			fieldModBuffer = new StringBuffer();
			actualNameBuffer = new StringBuffer();
			String[] temp;
			String delimiter = " ";
			String[] temp1;
			String delimiter1 = "=";
			temp = field.split(delimiter);
			int length = temp.length;
			temp1 = temp[length-1].split(delimiter1);
			int initLength = temp1.length;
			boolean init = false;
			if(initLength > 1)
			{
				init = true;
			}
			if((2 == length))
			{
				fieldMap.put("DataType", temp[0]);
				actualNameBuffer.append(temp[0]).append(" ");
				if(init)
				{
					fieldMap.put("SimpleName", temp1[0]);
					actualNameBuffer.append(temp1[0]).append(" = ");
					fieldMap.put("Initializer", temp1[1]);
					actualNameBuffer.append(temp1[1]).append(";");
					
				}
				else
				{
					fieldMap.put("SimpleName", temp[1]);
					actualNameBuffer.append(temp[1]).append(";");
				}
			}
			else if((2 < length))
			{
				for(int i = 0; i<(length-2); i++ )
				{
					fieldModBuffer.append(temp[i]);
					fieldModBuffer.append(": ");
					actualNameBuffer.append(temp[i]).append(" ");
				}
				if (0 < fieldModBuffer.lastIndexOf(":")) {
					fieldModBuffer.replace(fieldModBuffer.lastIndexOf(":"), fieldModBuffer.lastIndexOf(":") + 1, "");
				}
				fieldMap.put("Modifiers", fieldModBuffer.toString());
				
				fieldMap.put("DataType", temp[length-2]);
				actualNameBuffer.append(temp[length-2]).append(" ");
				
				if(init)
				{
					fieldMap.put("SimpleName", temp1[0]);
					actualNameBuffer.append(temp1[0]).append(" = ");
					fieldMap.put("Initializer", temp1[1]);
					actualNameBuffer.append(temp1[1]).append(";");
				}
				else
				{
					fieldMap.put("SimpleName", temp[length-1]);
					actualNameBuffer.append(temp[length-1]).append(";");
				}
				
			}
			fieldMap.put("ActualName", actualNameBuffer.toString());
			fieldMap.put("Id", javaParser.getId());
			fieldMap.put("TypeOfDataType", "Primitive");
			fieldArray.add(fieldMap);
		}
		return fieldArray;
	}
	public List<Map<String, String>> getMethodMap(List<String> methods) {
		List<Map<String, String>> methodArray = new ArrayList<Map<String, String>>();
		Map<String, String> methodMap;
		StringBuffer methodModBuffer;
		StringBuffer methodParamBuffer;
		StringBuffer methodParamPairBuffer = new StringBuffer();
		for(String method: methods )
		{
			methodMap = new HashMap<String, String>();
			methodModBuffer = new StringBuffer();
			methodParamBuffer = new StringBuffer();
			methodParamPairBuffer = new StringBuffer();
			/*Scanner scanner = new Scanner(method);
			 scanner.useDelimiter("\\(");
			 while(scanner.hasNext())
			 {
				 temp1
				 System.out.println(scanner.next());
			 }*/
			String[] temp;
			String delimiter = " ";
			String[] temp1;
			String delimiter1 = "\\(";
			String[] temp2;
			String[] temp3;
			String delimiter2 = ",";
			temp1 = method.split(delimiter1);
			temp = temp1[0].split(delimiter);
			
			String methodParams = temp1[1];
			 if (methodParams != null && methodParams.length() > 0 && methodParams.charAt(methodParams.length()-1)==')') {
				 methodParams = methodParams.substring(0, methodParams.length()-1);
			    }
			
			temp2 = methodParams.split(delimiter2);
			int length = temp.length;
			if((2 == length))
			{
				methodMap.put("Name", temp[1]);
				methodMap.put("ReturnType", temp[0]);
			}
			else if((2 < length))
			{
				methodMap.put("Name", temp[length-1]);
				methodMap.put("ReturnType", temp[length-2]);
				for(int i = 0; i<(length-2); i++ )
				{
					methodModBuffer.append(temp[0]);
					methodModBuffer.append(": ");
				}
				if (0 < methodModBuffer.lastIndexOf(":")) {
					methodModBuffer.replace(methodModBuffer.lastIndexOf(":"), methodModBuffer.lastIndexOf(":") + 1, "");
				}
				methodMap.put("Modifiers", methodModBuffer.toString());
				methodMap.put("ActualModifiers", methodModBuffer.toString());
			}
			
			for(int i=0; i<temp2.length; i++)
			{
				methodParamBuffer.append(temp2[i]);
				methodParamBuffer.append(" , ");
				temp3 = temp2[i].split(delimiter2);
				for(int j=0; i<temp3.length; i++)
				{
				methodParamPairBuffer.append(temp3[j]);
				methodParamPairBuffer.append(" & ");
				}
			}
			if (0 < methodParamBuffer.lastIndexOf(",")) {
				methodParamBuffer.replace(methodParamBuffer.lastIndexOf(","),
						methodParamBuffer.lastIndexOf(",") + 1, "");
			}
			methodMap.put("Params", methodParamBuffer.toString().trim());
			
		if (0 < methodParamPairBuffer.lastIndexOf("&")) {
			methodParamPairBuffer.replace(methodParamPairBuffer.lastIndexOf("&"),
					methodParamPairBuffer.lastIndexOf("&") + 1, "");
		}
		methodMap.put("ParamPair", methodParamPairBuffer.toString().trim());
			methodMap.put("Id", javaParser.getId());
			methodMap.put("TypeOfReturnType", "Primitive");
			methodArray.add(methodMap);
	}
		return methodArray;
	}

}
