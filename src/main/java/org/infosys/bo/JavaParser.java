package org.infosys.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.infosys.vo.common.SourceCodeClassMembers;
import org.infosys.vo.common.SourceCodeFieldMembers;
import org.infosys.vo.common.SourceCodeMethodMembers;
import org.infosys.vo.json.Relations;

public class JavaParser {

	Map<String, List<SourceCodeClassMembers>> codeMap = new HashMap<String, List<SourceCodeClassMembers>>();
	List<SourceCodeClassMembers> classList;
	List<String> packageList = new ArrayList<String>();
	List<Relations> relationList = new ArrayList<Relations>();
	Map<String, String> nameIdMap = new HashMap<String, String>();
	String packageName;
	//to be Removed
	Map<Integer, String> testMap1 = new HashMap<Integer, String>();
	Map<String, Integer> testMap2 = new HashMap<String, Integer>();
	Map<String, SourceCodeClassMembers> testMap3 = new HashMap<String, SourceCodeClassMembers>();
	Map<Integer, SourceCodeClassMembers> testMap4 = new HashMap<Integer, SourceCodeClassMembers>();
	Map<SourceCodeClassMembers,Integer> testMap5 = new HashMap<SourceCodeClassMembers,Integer>();
	Integer integerTest;
	Float floatTest;
	Object objectTest;
	

	public Map<String, List<SourceCodeClassMembers>> parse(String str) {
		final List<SourceCodeMethodMembers> methodList = new ArrayList<SourceCodeMethodMembers>();
		final List<SourceCodeFieldMembers> fieldList = new ArrayList<SourceCodeFieldMembers>();
		final SourceCodeClassMembers classMembers = new SourceCodeClassMembers();
		final List<String> importList = new ArrayList<String>();
		final List<SimpleType> interfaceList = new ArrayList<SimpleType>();
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			public boolean visit(PackageDeclaration pd) {
				packageName = pd.getName().getFullyQualifiedName().trim();
				classMembers.setPackageName(packageName);
				if (!packageList.contains(packageName)) {
					packageList.add(packageName);
					classList = new ArrayList<SourceCodeClassMembers>();
				}
				return true;
			}

			public boolean visit(ImportDeclaration id) {
				importList.add(id.getName().getFullyQualifiedName());
				classMembers.setImportList(importList);
				return false;
			}

			@SuppressWarnings("unchecked")
			public boolean visit(TypeDeclaration td) {
				classMembers.setModifiers(td.modifiers());
				if (td.isInterface()) {
					classMembers.setType("uml.Interface");
				} else {
					classMembers.setType("uml.Class");
				}
				String className = td.getName().getFullyQualifiedName();
				String classId = getId();
				classMembers.setName(className);
				classMembers.setId(classId);
				nameIdMap.put(className, classId);
				classMembers.setNameIdMap(nameIdMap);
				setNameIdMap(nameIdMap);
				if (null != td.getSuperclassType()) {
					String superClassName = td.getSuperclassType().toString();
					classMembers.setSuperClass(superClassName);
					setRelation(classId, superClassName, "uml.Generalization");
				}
				if (null != td.superInterfaceTypes()) {
					interfaceList.addAll(td.superInterfaceTypes());
					classMembers.setSuperInterfaces(interfaceList);
					for (Object xx : interfaceList) {
						setRelation(classId, xx, "uml.Implementation");
					}
				}
				for (MethodDeclaration meth : td.getMethods()) {
					SourceCodeMethodMembers methodMembers = new SourceCodeMethodMembers();
					
					//System.out.println(meth.getAST().);
					
					final List actualModifiers = new ArrayList();
					actualModifiers.addAll(meth.modifiers());
					methodMembers.setActualModifiers(actualModifiers);
					List modifiers = new ArrayList();
					modifiers.addAll(meth.modifiers());
					//System.out.println(meth.modifiers());
					getModifiers(modifiers);
					methodMembers.setModifiers(modifiers);
					if (null != meth.getReturnType2()) {
						if(meth.getReturnType2().isPrimitiveType())
						{
							methodMembers.setTypeOfReturnType("Primitive");
							methodMembers.setReturnType(meth.getReturnType2().toString());
							//System.out.println("Primitive: " +meth.getReturnType2().toString());
						}
						else if(meth.getReturnType2().isSimpleType())
						{
							methodMembers.setTypeOfReturnType("Simple");
							methodMembers.setReturnType(meth.getReturnType2().toString());
							//System.out.println("Simple: " +meth.getReturnType2().toString());
						}
						else if(meth.getReturnType2().isParameterizedType())
						{
							methodMembers.setTypeOfReturnType("Parameterized");
							methodMembers.setReturnType(getParameterizedFieldType(meth.getReturnType2().toString()));
							//System.out.println("Parameterized: " +meth.getName());
						}
						else
						{
							methodMembers.setReturnType(meth.getReturnType2().toString());
						}
					}
					methodMembers.setId(getId());
					methodMembers.setName(meth.getName().getFullyQualifiedName());
					methodMembers.setParameters(meth.parameters());
					//System.out.println("Parameters :"+meth.parameters());
					if (!td.isInterface() && null != meth.getBody()) {
						methodMembers.setBody(meth.getBody().toString());
					}
					if(null != meth.parameters() && meth.parameters().size()>0)
					{
						List<String> methodParameterList = new ArrayList<String>();
						
						for (Object o : meth.parameters()) {
							String Param =  null;
							if(((SingleVariableDeclaration) o).getType().isParameterizedType())
							{
								 Param = ((SingleVariableDeclaration) o).getName().toString() + ":"
										+ getParameterizedFieldType(((SingleVariableDeclaration) o).getType().toString());
							}
							else
							{
								 Param = ((SingleVariableDeclaration) o).getName().toString() + ":"
											+ ((SingleVariableDeclaration) o).getType().toString();
							}
							
							if(((SingleVariableDeclaration) o).getType().isPrimitiveType())
							{
								Param = Param +":" +"Primitive";
							}
							else if(((SingleVariableDeclaration) o).getType().isSimpleType())
							{
								Param = Param +":" +"Simple";
							}
							else if(((SingleVariableDeclaration) o).getType().isParameterizedType())
							{
								Param = Param +":" +"Parameterized";
							}
							else if(((SingleVariableDeclaration) o).getType().isArrayType())
							{
								Param = Param +":" +"Array";
							}
							methodParameterList.add(Param);
						}
						// System.out.println(methodParameterList);
						methodMembers.setParameterPair(methodParameterList);
					}
					methodList.add(methodMembers);
					//System.out.println("Methods : " + methodMembers);
				}
				classMembers.setMethods(methodList);
				for (FieldDeclaration field : td.getFields()) {
					SourceCodeFieldMembers fieldMembers = new SourceCodeFieldMembers();
					fieldMembers.setActualName(field.toString());
					List modifiers = field.modifiers();
					getModifiers(modifiers);
					fieldMembers.setModifiers(field.modifiers());
					fieldMembers.setId(getId());
					if(null!=field.getJavadoc())
					{
					field.getJavadoc().delete();
					}
					
					fieldMembers.setName(field.toString());
					if(field.fragments().iterator().hasNext())
					{
						Object o = field.fragments().iterator().next();
						fieldMembers.setSimpleName(((VariableDeclarationFragment) o).getName().toString());
						if(null != ((VariableDeclarationFragment) o).getInitializer())
						{
							fieldMembers.setInitializer(((VariableDeclarationFragment) o).getInitializer().toString());
						}
					}
					
					if(field.getType().isPrimitiveType())
					{
						fieldMembers.setTypeOfDataType("Primitive");	
						fieldMembers.setDataType(field.getType().toString());
						//System.out.println(field.toString() +"type  :"+ field.getType() + " isPrimitive Type:"+field.getType().isPrimitiveType());
					}
					else if(field.getType().isSimpleType())
					{
						fieldMembers.setTypeOfDataType("Simple");	
						fieldMembers.setDataType(field.getType().toString());
						//System.out.println(field.toString() +"type  :"+ field.getType() +  " isSimpleType:"+field.getType().isSimpleType());
					}
					else if(field.getType().isParameterizedType())
					{
					fieldMembers.setTypeOfDataType("Parameterized");
					fieldMembers.setDataType(getParameterizedFieldType(field.getType().toString()));
					//getParameterizedFieldType(field.getType().toString());
					//System.out.println(field.toString() +"type  :"+ field.getType() +  " isParameterizedType:"+field.getType().isParameterizedType());
					//System.out.println("Field Type :" + fieldMembers.getDataType()+ " Type Of Field Type :" +fieldMembers.getTypeOfDataType());
					}
					else
					{
					fieldMembers.setTypeOfDataType("Others");
					fieldMembers.setDataType(field.getType().toString());
					//System.out.println(field.toString() +"type  :"+ field.getType() +  " another Type");
					}
					fieldList.add(fieldMembers);
					//System.out.println("Field Type :" + fieldMembers.getDataType()+ " Type Of Field Type :" +fieldMembers.getTypeOfDataType());
				}
				
				
				classMembers.setFeilds(fieldList);
				// System.out.println("Classes : "+classMembers);
				classList.add(classMembers);
				return true;
			}

			private String getParameterizedFieldType1(String type) {
				String dataType = null;
				String typeFor = null;
				String[] fieldRef = type.split("<");
				for(String f : fieldRef)
				{
					if(f.endsWith(">"))
					{
						dataType = f.replace(">", "").trim();
						if(dataType.contains(","))
						{
							typeFor = "Map";
						}
						else
						{
							typeFor = "Others";
						}
					}
				}
				//System.out.println("Type For: "+typeFor+" type: "+dataType);
				return "Type For: "+typeFor+"type: "+dataType;
			}
			private String getParameterizedFieldType(String field) {
				String[] fieldRef = field.split("<");
				StringBuffer typeBuffer = new StringBuffer();
				for(String f : fieldRef)
				{
					
					if(f.endsWith(">"))
					{
					f = f.replace(">", "");
					}
					if(f.contains(","))
					{
						//typeBuffer.append("MapChild").append(":");
						String[] fieldRef1 = f.split(",");
						for(String f1 : fieldRef1)
						{
							typeBuffer.append(f1).append(",");
						}
						if (0 < typeBuffer.lastIndexOf(","))
						{
							typeBuffer.replace(typeBuffer.lastIndexOf(","), typeBuffer.lastIndexOf(",") + 1, "");
						}
						typeBuffer.append("#");
					}
					else
					{
						
						typeBuffer.append(f).append("#");
					}
					
				}
				if (0 < typeBuffer.lastIndexOf("#"))
				{
					typeBuffer.replace(typeBuffer.lastIndexOf("#"), typeBuffer.lastIndexOf("#") + 1, "");
				}
				//System.out.println("In:: "+field+ "  Out:: "+typeBuffer.toString());
				return typeBuffer.toString();
			}
			private void getModifiers(List modifiers) {
				if (modifiers.iterator().hasNext()) {
					if (modifiers.iterator().next().toString().startsWith("@")) {
						modifiers.remove(modifiers.iterator().next());
					}
				}
			}
			/*private void trimField(String field) {
				if (modifiers.iterator().hasNext()) {
					if (modifiers.iterator().next().toString().startsWith("@")) {
						modifiers.remove(modifiers.iterator().next());
					}
				}
			}
*/
			private void setRelation(String classId, Object targetName, String type) {
				Relations relation = new Relations();
				relation.setId(getId());
				relation.setSourceId(classId);
				relation.setTargetName(targetName.toString());
				relation.setType(type);
				relationList.add(relation);
				setRelationList(relationList);
			}

		});
		codeMap.put(packageName, classList);
		return codeMap;
	}

	// read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

	// loop directory to get file list
	public Map<String, List<SourceCodeClassMembers>> parseFilesInDir(String dirPath) throws IOException {
		 System.out.println("Dir Path : "+ dirPath);
		File root = new File(dirPath);
		File[] files = root.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				System.out.println("Is File" + f.getAbsolutePath());
				parse(readFileToString(f.getAbsolutePath()));
			} else if (f.isDirectory()) {
				System.out.println("Is Dir" + f.getAbsolutePath());
				if(f.getName().equalsIgnoreCase("test")
						|| f.getName().equalsIgnoreCase("resources")
						|| f.getName().equalsIgnoreCase("webapp"))
				{
				System.out.println("not neccessary");
				}
				else
				{
					parseFilesInDir(f.getAbsolutePath());
				}
			}
		}
		return codeMap;
	}

	public String getId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public List<Relations> getRelationList() {
		return relationList;
	}

	public void setRelationList(List<Relations> relationList) {
		this.relationList = relationList;
	}

	public Map<String, String> getNameIdMap() {
		return nameIdMap;
	}

	public void setNameIdMap(Map<String, String> nameIdMap) {
		this.nameIdMap = nameIdMap;
	}

	
		public static void main(String[] args) throws IOException {

		JavaParser parser = new JavaParser();
		// File dirs = new File(".");
		String path = "D:\\projects\\biz\\workspace\\UMLWeb";
		/*
		 * String dirPath = dirs.getCanonicalPath() + File.separator + "src" +
		 * File.separator;
		 */
		String dirPath = path + File.separator + "src" + File.separator;
		
		
		Map<String, List<SourceCodeClassMembers>> codeMap = parser.parseFilesInDir(dirPath);
		System.out.println("Map Size" + codeMap.size());

		for (Entry<String, List<SourceCodeClassMembers>> mem : codeMap.entrySet()) {
			System.out.println(mem.getKey());
			System.out.println("Class Size:" + mem.getValue().size());
			/*for (SourceCodeClassMembers class1 : mem.getValue()) {
				System.out.println(class1.toString());

			}*/
		}

	}
}