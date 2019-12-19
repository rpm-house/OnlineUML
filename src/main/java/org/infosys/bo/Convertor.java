package org.infosys.bo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Convertor {
	private Transformer<Object> transformer;
   
	

   

    public void convertJsonToXML(String jsonObject, String fileName){
    	transformer = new Transformer<Object>();
    	File file;
		FileWriter fileWriter;
		try {
			file = new File("D:/projects/KBE/uml_Json/test1.xml");
			fileWriter = new FileWriter(file.getAbsoluteFile());
			
			fileWriter.write(transformer.getXMLFromObject(jsonObject));
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    }
    
    public void convertJsonToXMLDOM(String jsonObject, String fileName){
    	transformer = new Transformer<Object>();
    	File file;
		FileWriter fileWriter;
		try {
			file = new File("D:/projects/KBE/uml_Json/test2.xml");
			fileWriter = new FileWriter(file.getAbsoluteFile());
			
			fileWriter.write(transformer.getXMLFromObjectUsingDomDriver(jsonObject));
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    }
    
    
    
}
