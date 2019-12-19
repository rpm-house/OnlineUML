package org.infosys.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
/*import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;*/

public class CallHierarchyClass {/*
	public HashSet<IMethod> getCallersOf(IMethod m) {

		CallHierarchy callHierarchy = CallHierarchy.getDefault();
		MethodWrapper mw = callHierarchy.getCallerRoot(m);
		HashSet<IMethod> callers = new HashSet<IMethod>();
		 for (MethodWrapper mw : methodWrappers) { 
		MethodWrapper[] mw2 = mw.getCalls(new NullProgressMonitor());
		HashSet<IMethod> temp = getIMethods(mw2);
		callers.addAll(temp);
		// }

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

	
	IMethod findMethod(IType obj, String methodName) throws JavaModelException
	{
	    //IType type = project.findType(typeName);

	    IMethod[] methods = obj.getMethods();
	    IMethod theMethod = null;

	    for (int i = 0; i < methods.length; i++)
	    {
	        IMethod imethod = methods[i];
	        if (imethod.getElementName().equals(methodName)) {
	            theMethod = imethod;
	        }
	    }

	    if (theMethod == null)
	    {           
	        System.out.println("Error, method" + methodName + " not found");
	        return null;
	    }

	    return theMethod;
	}
	public List<String> getInvokeMethod(String str) throws JavaModelException {

		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		Hashtable<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
		parser.setCompilerOptions(options);

		parser.setResolveBindings(true);
		parser.setSource(str.toCharArray());
		parser.setBindingsRecovery(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		System.out.println(cu.getTypeRoot());
		for(Object  obj : cu.types())
		{
			System.out.println(obj);
			findMethod(((IType)obj),"");
		
		}
		return null;
	}
	
	public List<String>  parseFilesInvokeMeth(String dirPath) throws IOException, JavaModelException {
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
		return null;
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
	


	public static void main(String[] args) throws IOException, JavaModelException, ClassNotFoundException {
		CallHierarchyClass callHierarchyClass = new CallHierarchyClass();
		String path = "D:\\projects\\biz\\workspace\\SampleJava";
		String dirPath = path + File.separator + "src" + File.separator;
		callHierarchyClass.parseFilesInvokeMeth(dirPath);

		
		
		Type[] types = Class.forName("java.util.HashMap").getGenericInterfaces();
		for(Type type:types)
		{
		IMethod m = callHierarchyClass.findMethod(type, "world");
		
	    Set<IMethod> methods = new HashSet<IMethod>();
	    methods = callHierarchyClass.getCallersOf(m);
	    for (Iterator<IMethod> i = methods.iterator(); i.hasNext();)
	    {
	        System.out.println(i.next().toString());
	    }
		}
	}
*/}
