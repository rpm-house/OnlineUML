package org.infosys.bo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.infosys.vo.common.ClassMembers;

public class GenerateJavaCode {

	public static void main(String[] args) {

		// old();
		GenerateJavaCode code = new GenerateJavaCode();
		code.generate("", "D:\\projects\\KBE\\GenCode");
	}

	public void generate(String jsonData, String path) {
		JsonParserBO parser = new JsonParserBO();
		List<ClassMembers> classList = parser.getClassList(jsonData);
		//List<ClassMembers> classList = parser.getClassList();
		Iterator<ClassMembers> classIterator = classList.iterator();
		StringBuffer bufferContent;
		while (classIterator.hasNext()) {
			bufferContent = new StringBuffer();
			ClassMembers classMembers = classIterator.next();
			String packageName = null;
			if(null!=classMembers.getPackageName())
			{
				packageName = classMembers.getPackageName();
			}
			else
			{
				packageName ="com.xyz";
			}
			String className = classMembers.getClassName();
			String fileName = className;
			bufferContent.append("package");
			bufferContent.append(" ");
			bufferContent.append(packageName);
			bufferContent.append(";");
			bufferContent.append(System.lineSeparator());
			bufferContent.append(System.lineSeparator());
			if (null != classMembers.getImportList() && classMembers.getImportList().size() > 0) {
				Iterator<String> importIterator = classMembers.getImportList().iterator();
				while (importIterator.hasNext()) {
					bufferContent.append("import").append(" ").append(importIterator.next()).append(";");
					bufferContent.append(System.lineSeparator());
				}
				bufferContent.append(System.lineSeparator());
				bufferContent.append(System.lineSeparator());
			}
			if (null != classMembers.getClassModifiers()) {
				String[] temp;
				String delimiter = ":";
				temp = classMembers.getClassModifiers().split(delimiter);
				for (String a : temp) {
					bufferContent.append(a);
					if (a.startsWith("@")) {
						bufferContent.append(System.lineSeparator());
					}
					else  {
						bufferContent.append(" ");
					}
				}
			}
			else
			{
				bufferContent.append("public");
			}
			if (null != classMembers.getType() && classMembers.getType().equalsIgnoreCase("uml:Class")) {
				bufferContent.append(" ");
				bufferContent.append("class");
			}
			else if(null != classMembers.getType() && classMembers.getType().equalsIgnoreCase("uml:Interface"))
			{
				bufferContent.append(" ");
				bufferContent.append("interface");
			}
			bufferContent.append(" ");
			bufferContent.append(className);
			
			if (null != classMembers.getSuperInterfaces() && !(classMembers.getSuperInterfaces().equalsIgnoreCase(","))
					&& !(classMembers.getSuperInterfaces().equalsIgnoreCase(""))) {
				bufferContent.append(" ");
				bufferContent.append("implements");
				bufferContent.append(" ");
				bufferContent.append(classMembers.getSuperInterfaces());
				if (0 < bufferContent.lastIndexOf(","))
					bufferContent.replace(bufferContent.lastIndexOf(","), bufferContent.lastIndexOf(",") + 1, "");
			}
			if (null != classMembers.getSuperClass()) {
				bufferContent.append(" ");
				bufferContent.append("extends");
				bufferContent.append(" ");
				bufferContent.append(classMembers.getSuperClass());
			}
			// bufferContent.append(System.lineSeparator());
			bufferContent.append("{");
			bufferContent.append(System.lineSeparator());
			bufferContent.append(System.lineSeparator());
			if (null != classMembers.getAttributeList() && classMembers.getAttributeList().size() > 0) {
				Iterator<Map<String, String>> attributeIterator = classMembers.getAttributeList().iterator();
				while (attributeIterator.hasNext()) {
					Map<String, String> attributeMap = attributeIterator.next();
					String[] temp;
					String delimiter = " ";
					temp = attributeMap.get("ActualName").split(delimiter);
					for (String a : temp) {
						bufferContent.append(a).append(" ");
						if (a.startsWith("@")) {
							bufferContent.append(System.lineSeparator());
						}
					}

					bufferContent.append(System.lineSeparator());
				}
				bufferContent.append(System.lineSeparator());
			}

			if (null != classMembers.getMethodList() && classMembers.getMethodList().size() > 0) {
				Iterator<Map<String, String>> methodIterator = classMembers.getMethodList().iterator();
				while (methodIterator.hasNext()) {
					Map<String, String> methodMap = methodIterator.next();
					if (null != methodMap.get("ActualModifiers")) {
						String[] temp;
						String delimiter = ":";
						temp = methodMap.get("ActualModifiers").split(delimiter);
						for (String m : temp) {
							bufferContent.append(m);
							if (m.startsWith("@")) {
								bufferContent.append(System.lineSeparator());
							}
						}

						bufferContent.append(" ");
					}
					if (null != methodMap.get("ReturnType")) {
						bufferContent.append(methodMap.get("ReturnType"));
						bufferContent.append(" ");
					}
					if (null != methodMap.get("Name")) {
						bufferContent.append(methodMap.get("Name"));
						// bufferContent.append(" ");
					}
					if (null != methodMap.get("Params")) {
						bufferContent.append("(");
						bufferContent.append(methodMap.get("Params"));
						bufferContent.append(")");
					} else {
						bufferContent.append("()");
					}

					if (null != methodMap.get("Body")) {
						// bufferContent.append(System.lineSeparator());
						bufferContent.append(methodMap.get("Body"));
					} else {
						bufferContent.append(";");
						bufferContent.append(System.lineSeparator());
					}
				}
			}
			bufferContent.append(System.lineSeparator());
			bufferContent.append("}");
			createFile(bufferContent.toString(), fileName, path, packageName);
		}
	}

	public void createFile(String content, String fileName, String path, String packageName) {
		try {
			path = createDir(path, packageName);
			String javaFile = path + File.separator + fileName + ".java";

			File file = new File(javaFile);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("javaFile: " + javaFile + "  created.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String createDir(String path, String packageName) {
		String folderName = packageName.replace(".", File.separator);
		if(null != packageName && !(" ".equalsIgnoreCase(packageName)))
		{
		path = path + File.separator + "src" + File.separator + folderName;
		}
		else
		{
			path = path + File.separator + "src";
		}
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
			System.out.println(path + " Dir Created.");
		}
		return path;

	}
	/*
	 * private static void old() throws IOException { // Initialize XMI factory
	 * to register XMI resources XMLTypeFactoryImpl xmiResourceFactoryImpl = new
	 * XMLTypeFactoryImpl() { public Resource createResource(URI uri) {
	 * XMIResource xmiResource = new XMIResourceImpl(uri); return xmiResource; }
	 * }; Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
	 * "xmi", xmiResourceFactoryImpl);
	 * 
	 * 
	 * // Generate java source code from a normal java xmi model
	 * Generate_JavaStructures javaGenerator = new Generate_JavaStructures(
	 * URI.createFileURI("D:\\projects\\KBE\\xmiParse\\final.xmi"), new
	 * File("myOutputFolderJava"), new ArrayList<Object>());
	 * javaGenerator.doGenerate(null); }
	 */
}
