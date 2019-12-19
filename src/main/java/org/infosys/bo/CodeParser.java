package org.infosys.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class CodeParser {

	public static void main(String[] args) {
		CodeParser codeParser = new CodeParser();
		codeParser.javaToJsonParser();
	}

	
	
	public CodeParser() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	void javaToJsonParser()
	{
		Convertor obj = new Convertor();
	Gson gson = new Gson();

	// convert java object to JSON format,
	// and returned as JSON formatted string
	String json = gson.toJson(obj);

	try {
		//write converted json data to a file named "file.json"
		FileWriter writer = new FileWriter("D:\\projects\\KBE\\java2json\\file.json");
		writer.write(json);
		writer.close();

	} catch (IOException e) {
		e.printStackTrace();
	}

	System.out.println(json);

}

	public List<String> readJavaFiles() {

		// listFilesAndFilesSubDirectories("D:\\projects\\biz\\workspace\\UMLWeb\\src\\main\\java");
		try {
			readFile(new File(
					"D:\\projects\\biz\\workspace\\UMLWeb\\src\\main\\java\\org\\infosys\\bo\\CodeParser.java"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void listFilesAndFilesSubDirectories(String directoryName) {
		File directory = new File(directoryName);
		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				System.out.println(file.getAbsolutePath());
			} else if (file.isDirectory()) {
				listFilesAndFilesSubDirectories(file.getAbsolutePath());
			}
		}
	}

	private static void readFile(File fin) throws IOException {
		String[] temp1 = null;
		String delimiter1 = " ";

		String[] temp;
		String delimiter = ".java";

		String fileName = fin.getName();
		System.out.println(fileName);
		temp = fileName.trim().split(delimiter);
		System.out.println(temp.length);
		System.out.println(temp[0]);
		String tempClassName = temp[0];
		FileInputStream fis = new FileInputStream(fin);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = null;
		while ((line = br.readLine()) != null) {
			line.trim();
			/*while (line.startsWith("\t")) {
				line = line.substring(0, line.length()-1);
			}*/
			List<String> lines = Arrays.asList(line.split(delimiter1));
			if (line.startsWith("public") || line.startsWith("private") || line.startsWith("protected")) {
				if (lines.contains("class") && lines.contains(tempClassName)) {
					System.out.println("Class:" + tempClassName);
					
				}
				else if(!lines.contains("class") && lines.contains(tempClassName))
				{
					System.out.println("Constructor:" + tempClassName);
				}
				else if(lines.contains("()"))
				{
					System.out.println("method:" + lines);
				}
			} else if (line.startsWith("class")) {
				if (lines.contains(tempClassName)) {
					System.out.println("Class:" + tempClassName);
				}
			}

			System.out.println(line);
		}
		br.close();
	}
}
