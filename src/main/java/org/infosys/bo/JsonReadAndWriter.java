package org.infosys.bo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JsonReadAndWriter {

	
	public boolean write(String fileName, String jsonData)
	{
	
		System.out.println("FileName:"+fileName);
			File file;
			FileWriter fileWriter;
			try {
				file = new File(fileName);
				fileWriter = new FileWriter(file.getAbsoluteFile());
				fileWriter.write(jsonData);
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Convertor convertor = new Convertor();
			convertor.convertJsonToXML(jsonData,fileName);
			convertor.convertJsonToXMLDOM(jsonData, fileName);
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + jsonData);
			return true;
		
	}
	
	public String read(String fileName)
	{
		JSONParser parser = new JSONParser();
		Object obj = null;
		File file;
		FileReader fileReader;
		try {
			file = new File(fileName);
			fileReader = new FileReader(file.getAbsoluteFile());
			obj = parser.parse(fileReader);
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//JSONObject jsonObject = (JSONObject) obj;
		System.out.println( obj.toString());
		return obj.toString();

	}
	
	public String readObject(File data)
	{
		JSONParser parser = new JSONParser();
		Object obj = null;
		File file;
		FileReader fileReader;
		try {
			file = data;
			fileReader = new FileReader(file.getAbsoluteFile());
			obj = parser.parse(fileReader);
			//fileReader.close();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//JSONObject jsonObject = (JSONObject) obj;
		System.out.println( obj.toString());
		return obj.toString();

	}
	
	public String writeToFile(String fileName, String data)
	{
			File file;
			FileWriter fileWriter;
			try {
				file = new File(fileName);
				fileWriter = new FileWriter(file.getAbsoluteFile());
				fileWriter.write(data);
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Successfully Copied String to File...");
			System.out.println("data: " + data);
			return fileName;
		
	}
	
	public static void main(String[] args) {
		JsonReadAndWriter readAndWriter = new JsonReadAndWriter();
		readAndWriter.read("D:/projects/biz/workspace/UMLWeb/json.txt");
	}
}
