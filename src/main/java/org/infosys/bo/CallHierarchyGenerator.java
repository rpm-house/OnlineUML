package org.infosys.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
/*import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;*/

import com.google.gson.Gson;

public class CallHierarchyGenerator {/*

	public HashSet<IMethod> getCallersOf(IMethod m) {

		CallHierarchy callHierarchy = CallHierarchy.getDefault();
		// IMember[] members = { m };
		
		 * MethodWrapper[] methodWrappers =
		 * callHierarchy.getCallerRoots(members);
		 
		MethodWrapper methodWrapper = callHierarchy.getCallerRoot(m);
		HashSet<IMethod> callers = new HashSet<IMethod>();
		MethodWrapper[] mw2 = methodWrapper.getCalls(new NullProgressMonitor());
		HashSet<IMethod> temp = getIMethods(mw2);
		callers.addAll(temp);
		return callers;
	}

	HashSet<IMethod> getIMethods(MethodWrapper[] methodWrappers) {
		HashSet<IMethod> c = new HashSet<IMethod>();
		for (MethodWrapper m : methodWrappers) {
			IMethod im = getIMethodFromMethodWrapper(m);
			if (im != null) {
				c.add(im);
			}
		}
		return c;
	}

	IMethod getIMethodFromMethodWrapper(MethodWrapper m) {
		try {
			IMember im = m.getMember();
			if (im.getElementType() == IJavaElement.METHOD) {
				return (IMethod) m.getMember();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	IMethod findMethod(IType type, String methodName) throws JavaModelException {
		// IType type = project.findType(typeName);

		IMethod[] methods = type.getMethods();
		IMethod theMethod = null;

		for (int i = 0; i < methods.length; i++) {
			IMethod imethod = methods[i];
			if (imethod.getElementName().equals(methodName)) {
				theMethod = imethod;
			}
		}

		if (theMethod == null) {
			System.out.println("Error, method" + methodName + " not found");
			return null;
		}

		return theMethod;
	}

	public Map<String, List<String>> getMethods1() {
		System.out.println("MethodInvocation " + invocationMethodMap);
		return invocationMethodMap;
	}
	
	public Map<String, List<String>> getMethods2() {
		System.out.println("hierarchyMethodMap " + hierarchyMethodMap);
		return hierarchyMethodMap;
	}

	public List<String> getMethods() {
		System.out.println("MethodDeclaration " + methods);
		return methods;
	}

	
	 * List<MethodInvocation> methods1 = new ArrayList<MethodInvocation>();
	 * List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	 
	Map<String, List<String>> invocationMethodMap = new HashMap<String, List<String>>();
	Map<String, List<String>> hierarchyMethodMap = new HashMap<String, List<String>>();
	List<String> methods1 = new ArrayList<String>();
	List<String> methods = new ArrayList<String>();

	public void getCallHierarchy(String str) {

		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {

			private String activeMethod;
			private String invokeMethod;

			public boolean visit(TypeDeclaration td) {

				return true;
			}

			@Override
			public boolean visit(MethodDeclaration node) {
				activeMethod = node.getName().toString();
				methods.add(activeMethod);
				return super.visit(node);
			}

			@Override
			public boolean visit(MethodInvocation node) {
				invokeMethod = node.getName().toString();
				methods1.add(invokeMethod);
				if (invocationMethodMap.get(activeMethod) == null) {
					invocationMethodMap.put(activeMethod, new ArrayList<String>());
				}
				invocationMethodMap.get(activeMethod).add(invokeMethod);

				
				 * if (node.resolveMethodBinding() != null) { IMethodBinding
				 * declarationOfInvokedMethod = node
				 * .resolveMethodBinding().getMethodDeclaration();
				 * 
				 * System.out.println(String.format(
				 * "invocation of \"%s\" is resolved to declaration \"%s\"",
				 * node, declarationOfInvokedMethod));
				 * 
				 * }
				 

				return super.visit(node);
			}
			
			 * @Override public boolean visit(CompilationUnit node) {
			 * System.out.println("Ref: "+node.toString()); return false;
			 * 
			 * }
			 
			
			 * public boolean visit(SimpleName node) { //if
			 * (activeMethod.equalsIgnoreCase(node.getFullyQualifiedName())) {
			 * System.out.println("Usage of '" + node + "' at line " +
			 * cu.getLineNumber(node.getStartPosition())); //} return true; }
			 

		});

		
		 * public boolean visit(VariableDeclarationFragment node) { SimpleName
		 * name = node.getName(); this.names.add(name.getIdentifier());
		 * //System.out.println("Declaration of '"+name+"' at line"
		 * +cu.getLineNumber(name.getStartPosition())); return false; // do not
		 * continue to avoid usage info }
		 

		
		 * public boolean visit(IMethod node) { System.out.println(node); String
		 * name = node.getElementName(); this.names.add(name);
		 * System.out.println(name); //System.out.println("Declaration of '"
		 * +name+"' at line"+cu.getLineNumber(name.getStartPosition())); return
		 * false; // do not continue to avoid usage info }
		 * 
		 * public boolean visit(SimpleName node) { if
		 * (this.names.contains(node.getIdentifier())) { System.out.println(
		 * "Usage of '" + node + "' at line " +
		 * cu.getLineNumber(node.getStartPosition())); } return true; }
		 * 
		 * });
		 
		
		 * public static Type getElementType(Type type) { if (!
		 * type.isArrayType()) return type; return
		 * ((ArrayType)type).getElementType(); }
		 
		
		 * ASTParser parser = ASTParser.newParser(AST.JLS3);
		 * parser.setSource(str.toCharArray());
		 * parser.setKind(ASTParser.K_COMPILATION_UNIT); final CompilationUnit
		 * cu = (CompilationUnit) parser.createAST(null);
		 * System.out.println(cu.getTypeRoot());
		 * System.out.println(cu.getTypeRoot().getJavaProject());
		 
		
		 * public boolean visit(MethodInvocation node) { Expression exp =
		 * node.getExpression(); ITypeBinding typeBinding =
		 * node.getExpression().resolveTypeBinding(); System.out.println(
		 * "Type: " + typeBinding.toString()); }
		 
		
		 * for(Object obj : cu.types()) {
		 * 
		 * System.out.println("obj : "+obj); TypeDeclaration type =
		 * (TypeDeclaration) obj; System.out.println("type : "
		 * +type.getAST().newCompilationUnit()); }
		 
		
		 * TypeDeclaration type = (TypeDeclaration) cu.types().get(0);
		 * MethodDeclaration[] methodList1 = type.getMethods();
		 * System.out.println("Proj : "+methodList1);
		 * 
		 * 
		 * for(MethodDeclaration m : methodList1 ) { getCallersOf(m); }
		 

		// CompilationUnit compilationUnit = null;
		// AST ast = AST.newAST(AST.JLS3);
		// CompilationUnit unit = ast.newCompilationUnit();

		// ICompilationUnit cu1 = (ICompilationUnit) unit.getJavaElement();

		// getProject();

		
		 * ICompilationUnit unit = (ICompilationUnit) cu.getRoot();
		 * 
		 * try { IType[] typeDeclarationList = (IType[]) unit.getAllTypes(); for
		 * (IType typeDeclaration : typeDeclarationList) { IMethod[] methodList
		 * = typeDeclaration.getMethods(); for (IMethod method : methodList) {
		 * getCallersOf(method); } } }
		 * 
		 * catch (JavaModelException e) { e.printStackTrace(); }
		 
	}

	public List getMethodList(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			private String activeMethod;
			private String className;
			public boolean visit(TypeDeclaration td) {
				className = td.getName().toString();
				return true;
			}

			@Override
			public boolean visit(MethodDeclaration node) {
				activeMethod = className + "." + node.getName().toString();
				methods.add(activeMethod);
				return super.visit(node);
			}
		});
		return methods;
	}

	public Map<String, List<String>> getMethodMap(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			private String activeMethod;
			private String className;
			private String invokeMethod;

			public boolean visit(TypeDeclaration td) {
				className = td.getName().toString();
				return true;
			}

			@Override
			public boolean visit(MethodDeclaration node) {
				activeMethod = className + "." + node.getName().toString();
				// methods.add(activeMethod);
				return super.visit(node);
			}

			@Override
			public boolean visit(MethodInvocation node) {
				invokeMethod = className + "." + node.getName().toString();
				methods1.add(invokeMethod);
				if (invocationMethodMap.get(activeMethod) == null) {
					invocationMethodMap.put(activeMethod, new ArrayList<String>());
				}
				if (methods.contains(invokeMethod)) {
					if (!(invocationMethodMap.get(activeMethod).contains(invokeMethod))) {
						invocationMethodMap.get(activeMethod).add(invokeMethod);
					}
				}
				return super.visit(node);
			}
		});
		// System.out.println(invocationMethodMap);
		return invocationMethodMap;
	}

	
	List<String> callMethodList = new ArrayList<String>();
	Map<String,String> callMethodMap = new HashMap<String,String>();
	public List<String> getCallMethod(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			private String packageName;
			private String className;
			StringBuffer buffer; 
			int numberOfArgs = 0;
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
				numberOfArgs = 0;
				//buffer.append("MethodName:");
				buffer.append(packageName+"_"+className + "_" +node.getName());
				buffer.append(getParameters(node));
				callMethodList.add(buffer.toString());
				callMethodMap.put(node.getName().toString(), buffer.toString());
				return super.visit(node);
			}
			private String getParameters(MethodDeclaration node) {
				StringBuffer paramBuffer = new StringBuffer();
				paramBuffer.append("(");
				for(Object param :node.parameters())
				{
					paramBuffer.append(param).append(",");
					numberOfArgs++;
				}
				if (0 < paramBuffer.lastIndexOf(","))
				paramBuffer.replace(paramBuffer.lastIndexOf(","), paramBuffer.lastIndexOf(",") + 1, "");
				paramBuffer.append(")");
				return paramBuffer.toString();
			}
		});
		return callMethodList;
	}

	
	List<String> invokeMethodList = new ArrayList<String>();
	public List<String> getInvokeMethod(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			StringBuffer buffer; 
			int numberOfArgs = 0;
			@Override
			public boolean visit(MethodDeclaration node) {
				buffer = new StringBuffer();
				numberOfArgs = 0;
				getParameters(node);
				String key = node.getName().toString();
				buffer.append("MethodName:");
				buffer.append(callMethodMap.get(key));
				buffer.append(",MethodInvokes:");
				return super.visit(node);
			}
			@Override
			public boolean visit(MethodInvocation node) {
				numberOfArgs = 0;
				getParameters(node);
				String key = node.getName().toString();
				//System.out.println("key:"+key);
				if(null!= callMethodMap.get(key))
				{
				buffer.append(callMethodMap.get(key));
				buffer.append(",");
				}
				if (0 < buffer.lastIndexOf(","))
					buffer.replace(buffer.lastIndexOf(","), buffer.lastIndexOf(",") + 1, "");
				//System.out.println("invokeMethod:"+buffer.toString());
				invokeMethodList.add(buffer.toString());
				//System.out.println("noOfInvokes: "+);
				return super.visit(node);
			}
			
			private String getParameters(MethodDeclaration node) {
				StringBuffer paramBuffer = new StringBuffer();
				paramBuffer.append("(");
				for(Object param :node.parameters())
				{
					paramBuffer.append(param).append(",");
					numberOfArgs++;
				}
				if (0 < paramBuffer.lastIndexOf(","))
				paramBuffer.replace(paramBuffer.lastIndexOf(","), paramBuffer.lastIndexOf(",") + 1, "");
				paramBuffer.append(")");
				//System.out.println("Params call:"+paramBuffer.toString());
				return paramBuffer.toString();
			}
			private String getParameters(MethodInvocation node) {
				StringBuffer paramBuffer = new StringBuffer();
				paramBuffer.append("(");
				for(Object param :node.arguments())
				{
					paramBuffer.append(param).append(",");
					numberOfArgs++;
				}
				if (0 < paramBuffer.lastIndexOf(","))
				paramBuffer.replace(paramBuffer.lastIndexOf(","), paramBuffer.lastIndexOf(",") + 1, "");
				paramBuffer.append(")");
				//System.out.println("Params invokes:"+paramBuffer.toString());
				return paramBuffer.toString();
			}
		});
		return invokeMethodList;
	}

	
Map<String, String> getcallMethodMap()
{
	System.out.println(callMethodMap);
	return callMethodMap;
}
	
	List<String> methodList = new ArrayList<String>();
	public List<String> getInvoke(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			private String activeMethod;
			private String className;
			private String invokeMethod;
			StringBuffer buffer; 
			
			public boolean visit(TypeDeclaration td) {
				className = td.getName().toString();
				return true;
			}

			@Override
			public boolean visit(MethodDeclaration node) {
				//activeMethod = className + "." + node.getName().toString();
				// methods.add(activeMethod);
				buffer = new StringBuffer();
				buffer.append("MethodName:");
				buffer.append(className + "." +node.getName());
				buffer.append(",MethodInvokes:");
				return super.visit(node);
			}

			@Override
			public boolean visit(MethodInvocation node) {
				//invokeMethod = className + "." + node.getName().toString();
				buffer.append(node.getName());
				buffer.append(",");
				methodList.add(buffer.toString());
				return super.visit(node);
			}
			
		});
		
		// System.out.println(invocationMethodMap);
		return methodList;
	}

	public Map<String, List<String>> getMethodHierarchyMap(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			private String activeMethod;
			private String className;
			private String invokeMethod;

			public boolean visit(TypeDeclaration td) {
				className = td.getName().toString();
				return true;
			}

			@Override
			public boolean visit(MethodDeclaration node) {
				activeMethod = className + "." + node.getName().toString();
				// methods.add(activeMethod);
				return super.visit(node);
			}

			@Override
			public boolean visit(MethodInvocation node) {
				invokeMethod = className + "." + node.getName().toString();
				methods1.add(invokeMethod);
				if (hierarchyMethodMap.get(invokeMethod) == null) {
					hierarchyMethodMap.put(invokeMethod, new ArrayList<String>());
				}
				if (methods.contains(activeMethod)) {
					if (!(hierarchyMethodMap.get(invokeMethod).contains(activeMethod))) {
						hierarchyMethodMap.get(invokeMethod).add(activeMethod);
					}
				}
				return super.visit(node);
			}
		});
		// System.out.println(invocationMethodMap);
		return hierarchyMethodMap;
	}

	public String getActualMethodName(MethodDeclaration declaration)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(getModifiers(declaration.modifiers()));
		buffer.append(" ");
		if(null!= declaration.getReturnType2())
		{
			buffer.append(declaration.getReturnType2());
		}
		else
		{
			buffer.append("void");
		}
		buffer.append(declaration.getName()).append(" (");
		buffer.append(getParameters(declaration.parameters())).append(" );");
		return null;
		
	}
	public String getActualMethodName(MethodInvocation declaration)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(declaration.getName()).append(" (");
		buffer.append(getParameters(declaration.typeArguments())).append(" );");
		return null;
		
	}
	
	private String getModifiers(List modifiers) {
		if (modifiers.iterator().hasNext()) {
			if (modifiers.iterator().next().toString().startsWith("@")) {
				modifiers.remove(modifiers.iterator().next());
			}
		}
		StringBuffer methodModBuffer = new StringBuffer();
		for (Object methMod : modifiers) {
			methodModBuffer.append(methMod);
			methodModBuffer.append(" ");
			
		}
		return methodModBuffer.toString();
	}
	
	private String getParameters(List params)
 {
		StringBuffer paramBuffer = new StringBuffer();
		if (null != params && params.size() > 0) {
			for (Object o : params) {
				paramBuffer.append(o).append(" ");
			}
		}
		return paramBuffer.toString();
	}
	
	private static ICompilationUnit parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(str.toCharArray());
		parser.setResolveBindings(true);
		return (ICompilationUnit) parser.createAST(null); // parse
	}

	private void getProject() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		// Get all projects in the workspace
		IProject[] projects = root.getProjects();
		for (IProject project : projects) {
			System.out.println("Proj: " + project);
		}
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

	public void parseFilesInDir(String dirPath) throws IOException {
		// System.out.println("Dir Path : "+ dirPath);
		File root = new File(dirPath);
		File[] files = root.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				// System.out.println("Is File" + f.getAbsolutePath());
				getMethodList(readFileToString(f.getAbsolutePath()));
			} else if (f.isDirectory()) {
				// System.out.println("Is Dir" + f.getAbsolutePath());
				parseFilesInDir(f.getAbsolutePath());
			}
		}
	}

	public void parseFilesforList(String dirPath) throws IOException {
		// System.out.println("Dir Path : "+ dirPath);
		File root = new File(dirPath);
		File[] files = root.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				// System.out.println("Is File" + f.getAbsolutePath());
				getMethodList(readFileToString(f.getAbsolutePath()));
			} else if (f.isDirectory()) {
				// System.out.println("Is Dir" + f.getAbsolutePath());
				parseFilesforList(f.getAbsolutePath());
			}
		}
	}

	public Map<String, List<String>>  parseFilesforMap(String dirPath) throws IOException {
		// System.out.println("Dir Path : "+ dirPath);
		File root = new File(dirPath);
		File[] files = root.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				// System.out.println("Is File" + f.getAbsolutePath());
				getMethodMap(readFileToString(f.getAbsolutePath()));
			} else if (f.isDirectory()) {
				// System.out.println("Is Dir" + f.getAbsolutePath());
				parseFilesforMap(f.getAbsolutePath());
			}
		}
		return invocationMethodMap;
	}
	
	public Map<String, List<String>>  parseFilesforHierarchyMap(String dirPath) throws IOException {
		// System.out.println("Dir Path : "+ dirPath);
		File root = new File(dirPath);
		File[] files = root.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				// System.out.println("Is File" + f.getAbsolutePath());
				getMethodHierarchyMap(readFileToString(f.getAbsolutePath()));
			} else if (f.isDirectory()) {
				// System.out.println("Is Dir" + f.getAbsolutePath());
				parseFilesforHierarchyMap(f.getAbsolutePath());
			}
		}
		return invocationMethodMap;
	}
	public List<String>  parseFilesInvoke(String dirPath) throws IOException {
		// System.out.println("Dir Path : "+ dirPath);
		File root = new File(dirPath);
		File[] files = root.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				// System.out.println("Is File" + f.getAbsolutePath());
				getInvoke(readFileToString(f.getAbsolutePath()));
			} else if (f.isDirectory()) {
				// System.out.println("Is Dir" + f.getAbsolutePath());
				parseFilesInvoke(f.getAbsolutePath());
			}
		}
		return methodList;
	}

	public List<String>  parseFilesCall(String dirPath) throws IOException {
		// System.out.println("Dir Path : "+ dirPath);
		File root = new File(dirPath);
		File[] files = root.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				// System.out.println("Is File" + f.getAbsolutePath());
				getCallMethod(readFileToString(f.getAbsolutePath()));
			} else if (f.isDirectory()) {
				// System.out.println("Is Dir" + f.getAbsolutePath());
				parseFilesCall(f.getAbsolutePath());
			}
		}
		return callMethodList;
	}
	public List<String>  parseFilesInvokeMeth(String dirPath) throws IOException {
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
		return invokeMethodList;
	}
	
	void javaToJsonParser()
	{
		String jsonFilePath = "D:\\projects\\biz\\workspace\\UMLWeb\\json_IKP_BizAPI.txt";
		//String path = "D:\\projects\\KBE\\KBE_Framework\\KBE_Framework\\KBE_Framework";
				//String path = "D:\\projects\\biz\\workspace\\UMLWeb\\src\\main\\java\\org\\infosys";
				String path = "D:\\projects\\biz\\workspace\\UMLWeb";
				//String path = "D:\\projects\\KBE\\KBE_Framework\\KBE_Framework\\KBE_Framework";
				//System.out.println("Path :"+path);
				//String dirPath = path + File.separator + "src" + File.separator;
		try {
		FileWriter writer = new FileWriter(jsonFilePath);
		String json = new Gson().toJson(getMethods1());
		writer.write(json);
		writer.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}

	public static void main(String[] args) {
		CallHierarchyGenerator callGen = new CallHierarchyGenerator();
		String path = "D:\\projects\\biz\\workspace\\UMLWeb";
		// String dirPath = path + File.separator + "src" + File.separator;
		//String dirPath = "D:\\projects\\biz\\workspace\\UMLWeb\\src\\main\\java\\org\\infosys\\bo";
		//String path = "D:\\projects\\KBE\\KBE_Platform_Wspace\\IKP_v1\\org.infosys.kbe.businessapi";
		String dirPath = path + File.separator + "src" + File.separator;
		try {
			//callGen.parseFilesforList(dirPath);
			//callGen.parseFilesforMap(dirPath);
			//callGen.parseFilesforHierarchyMap(dirPath);
			
			//System.out.println(callGen.parseFilesCall(dirPath));
			callGen.parseFilesCall(dirPath);
			//callGen.getcallMethodMap();
			//callGen.parseFilesInvokeMeth(dirPath);
			System.out.println(callGen.parseFilesInvokeMeth(dirPath));
			//System.out.println(callGen.parseFilesInvokeMeth(dirPath));
			
			//System.out.println(callGen.parseFilesInvoke(dirPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//callGen.getMethods();
		//callGen.getMethods1();
		//callGen.getMethods2();
		
		
		//callGen.getMethods1();
		//System.out.println(callGen.getFinalMap());
		//callGen.javaToJsonParser();
	}

	public  Map<String, List<String>> getFinalMap() {
		Map<String, List<String>> finalMap = new HashMap<String, List<String>>();
		List<String> methodList = null;
		for (Entry<String, List<String>> e : getMethods1().entrySet()) {
			String key = e.getKey();
			Object value = e.getValue();
			 //System.out.println("key:"+key +" # value:"+ value);
			for (String m : e.getValue()) {
				if(null !=finalMap.get(m))
				{
				finalMap.get(m).add(key);
				}
				else
				{
				methodList = new ArrayList<String>();
				methodList.add(key);
				finalMap.put(m, methodList);
				}
				
			}
		}
		return finalMap;
	}

*/}
