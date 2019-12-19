package org.infosys.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class CallHierarchyGen {

	List<CallHierarchyVO> ListMethods = new ArrayList<CallHierarchyVO>();
	CallHierarchyListVO hierarchyListVO = new CallHierarchyListVO();
	List<String> invokeList;
	CallHierarchyVO hierarchyVO;
	Map<String, String> callMethodMap = new HashMap<String, String>();
	Map<String, String> classNameMap;
	Map<String, String> fieldNameMap;

	public Map<String, String> getCallMethod(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			StringBuffer buffer;
			StringBuffer methodBuffer;
			StringBuffer paramBuffer;
			StringBuffer parameterBuffer;
			private String packageName;
			private String className;

			public boolean visit(PackageDeclaration pd) {
				packageName = pd.getName().toString();
				return true;
			}

			public boolean visit(TypeDeclaration td) {
				className = td.getName().toString();
				return true;
			}

			@Override
			public boolean visit(MethodDeclaration node) {
				buffer = new StringBuffer();
				methodBuffer = new StringBuffer();
				paramBuffer = new StringBuffer();
				parameterBuffer = new StringBuffer();
				if (node.parameters().size() > 0) {
					paramBuffer.append("_").append(node.parameters().size());
					parameterBuffer.append("(");
					for (Object o : node.parameters()) {
						paramBuffer.append("_").append(((SingleVariableDeclaration) o).getType().toString());
						parameterBuffer.append(((SingleVariableDeclaration) o).getType().toString()).append(" ")
								.append(((SingleVariableDeclaration) o).getName().toString());
						parameterBuffer.append(",");
					}
					if (0 < parameterBuffer.lastIndexOf(","))
						parameterBuffer.replace(parameterBuffer.lastIndexOf(","), parameterBuffer.lastIndexOf(",") + 1,
								"");
					parameterBuffer.append(")");
				} else {
					parameterBuffer.append("()");
				}
				buffer.append(packageName + "_" + className + "_" + node.getName()).append(parameterBuffer);
				methodBuffer.append(className + "_" + node.getName()).append(paramBuffer);
				System.out.println("Method Name :" + methodBuffer.toString());
				callMethodMap.put(methodBuffer.toString(), buffer.toString());
				return super.visit(node);
			}
		});
		return callMethodMap;
	}

	public CallHierarchyListVO getInvokeMethod(String str) {

		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		Hashtable<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
		parser.setCompilerOptions(options);
		parser.setSource(str.toCharArray());
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			private String packageName;
			private String className;
			private String methodName;

			StringBuffer buffer;
			StringBuffer parameterBuffer;

			String invokeMethod = null;
			StringBuffer invokeClassMethod = null;

			public boolean visit(PackageDeclaration pd) {
				packageName = pd.getName().toString();
				return true;
			}

			public boolean visit(TypeDeclaration td) {
				fieldNameMap = new HashMap<String, String>();
				className = td.getName().toString();
				return true;
			}

			@Override
			public boolean visit(MethodDeclaration node) {
				invokeList = new ArrayList<String>();
				hierarchyVO = new CallHierarchyVO();
				classNameMap = new HashMap<String, String>();
				parameterBuffer = new StringBuffer();
				buffer = new StringBuffer();

				if (node.parameters().size() > 0) {
					parameterBuffer.append("(");
					for (Object o : node.parameters()) {
						fieldNameMap.put(((SingleVariableDeclaration) o).getName().toString().trim(),
								((SingleVariableDeclaration) o).getType().toString().trim());
						parameterBuffer.append(((SingleVariableDeclaration) o).getType().toString()).append(" ")
								.append(((SingleVariableDeclaration) o).getName().toString());
						parameterBuffer.append(",");
					}
					if (0 < parameterBuffer.lastIndexOf(","))
						parameterBuffer.replace(parameterBuffer.lastIndexOf(","), parameterBuffer.lastIndexOf(",") + 1,
								"");
					parameterBuffer.append(")");
				} else {
					parameterBuffer.append("()");
				}
				methodName = node.getName().toString();
				buffer.append(packageName + "_" + className + "_" + methodName).append(parameterBuffer);
				hierarchyVO.setMethodName(buffer.toString());
				return super.visit(node);
			}

			@Override
			public boolean visit(ClassInstanceCreation node) {
				String classNameIn = node.getParent().toString();
				String[] temp;
				String delimiter = "=";
				temp = classNameIn.split(delimiter);
				classNameMap.put(temp[0].trim(), node.getType().toString().trim());
				return false;
			}

			@Override
			public boolean visit(FieldDeclaration node) {
				for (Object o : node.fragments()) {
					String[] temp;
					String delimiter = "=";
					temp = o.toString().split(delimiter);
					fieldNameMap.put(temp[0].trim(), node.getType().toString().trim());
				}
				// System.out.println("methodName :"+className+"-" +methodName+"
				// node :"+fieldNameMap);
				return true;
			}

			@Override
			public boolean visit(VariableDeclarationStatement node) {
				for (Object o : node.fragments()) {
					String[] temp;
					String delimiter = "=";
					temp = o.toString().split(delimiter);
					fieldNameMap.put(temp[0].trim(), node.getType().toString().trim());
				}
				// System.out.println("methodName :"+className+"-" +methodName+"
				// node :"+fieldNameMap);
				return true;
			}

			@Override
			public boolean visit(MethodInvocation node) {
				StringBuffer argBuffer = new StringBuffer();
				invokeMethod = node.getName().toString();
				invokeClassMethod = new StringBuffer();
				if (null != node.getExpression() && null != classNameMap.get(node.getExpression().toString().trim())) {
					invokeClassMethod.append(classNameMap.get(node.getExpression().toString().trim()) + "_")
							.append(invokeMethod);
				}

				if (!node.arguments().isEmpty()) {
					argBuffer.append(node.arguments().size());
					for (Object o : node.arguments()) {
						if (null != o.getClass().getName()
								&& "org.eclipse.jdt.core.dom.SimpleName".equalsIgnoreCase(o.getClass().getName())) {
							argBuffer.append("_").append(fieldNameMap.get(o.toString().trim()));
						} else {
							if (null != o.getClass().getSimpleName()
									&& o.getClass().getSimpleName().equalsIgnoreCase("StringLiteral")) {
								argBuffer.append("_").append("String");
							} else if (null != o.getClass().getSimpleName()
									&& o.getClass().getSimpleName().equalsIgnoreCase("ClassInstanceCreation")) {
								String[] temp;
								String delimiter = " ";
								temp = o.toString().split(delimiter);
								argBuffer.append("_").append(temp[1].substring(0, temp[1].lastIndexOf("(")));
							}  else if (null != o.getClass().getSimpleName()
									&& o.getClass().getSimpleName().equalsIgnoreCase("ArrayAccess")) {
								String act =  null;
								String key = o.toString().substring(0, o.toString().lastIndexOf("["));
								System.out.println("key:"+key);
								String val = fieldNameMap.get(key);
								//System.out.println("val type :"+val.getClass());
								/*if(val.contains("["))
								{
								 act = val.substring(0, val.lastIndexOf("["));
								}
								else
								{
									act = val;
								}*/
								argBuffer.append("_").append(val);
							}else {

								if (isInt(o.toString())) {
									argBuffer.append("_").append("int");
								} else if (isDouble(o.toString())) {
									argBuffer.append("_").append("double");
								}

								
							}
						}
						System.out.println("arg type :" + o.getClass().getSimpleName());
						System.out.println("arg :" + o.toString());
					}

					//System.out.println("argBuffer : " + argBuffer);
					invokeClassMethod.append("_").append(argBuffer);
				}

				if (null != callMethodMap.get(invokeClassMethod.toString())) {
					invokeList.add(callMethodMap.get(invokeClassMethod.toString()));
					System.out.println("Invoke Method: "+callMethodMap.get(invokeClassMethod.toString()));
				}
				hierarchyVO.setMethodInvokes(invokeList);
				ListMethods.add(hierarchyVO);
				return super.visit(node);
			}
		});
		HashSet<CallHierarchyVO> hashSet = new HashSet<CallHierarchyVO>(ListMethods);
		List<CallHierarchyVO> arrayList2 = new ArrayList<CallHierarchyVO>(hashSet);
		hierarchyListVO.setListMethods(arrayList2);
		return hierarchyListVO;
	}

	public Map<String, String> parseFilesCallMeth(String dirPath) throws IOException {
		// System.out.println("Dir Path : "+ dirPath);
		File root = new File(dirPath);
		File[] files = root.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				// System.out.println("Is File" + f.getAbsolutePath());
				getCallMethod(readFileToString(f.getAbsolutePath()));
			} else if (f.isDirectory()) {
				// System.out.println("Is Dir" + f.getAbsolutePath());
				parseFilesCallMeth(f.getAbsolutePath());
			}
		}
		return callMethodMap;
	}

	public CallHierarchyListVO parseFilesInvokeMeth(String dirPath) throws IOException {
		// System.out.println("Dir Path : "+ dirPath);
		File root = new File(dirPath);
		File[] files = root.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				// System.out.println("Is File" + f.getAbsolutePath());
				getInvokeMethod(readFileToString(f.getAbsolutePath()));
			} else if (f.isDirectory()) {
				// System.out.println("Is Dir" + f.getAbsolutePath());
				parseFilesInvokeMeth(f.getAbsolutePath());
			}
		}
		return hierarchyListVO;
	}

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

	public CallHierarchyListVO getHierarchyListVO() {
		return hierarchyListVO;
	}

	boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static void main(String[] args)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String path = "D:\\projects\\biz\\workspace\\UMLWeb";
		String dirPath = path + File.separator + "src" + File.separator;
		CallHierarchyGen callHierarchyGen = new CallHierarchyGen();
		System.out.println(callHierarchyGen.getCallHierarchyMethod(dirPath));
	}

	public  CallHierarchyListVO getCallHierarchyMethod(String dirPath) throws IOException  {
		parseFilesCallMeth(dirPath);
		return parseFilesInvokeMeth(dirPath);
	}

}
