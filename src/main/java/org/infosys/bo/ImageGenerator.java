package org.infosys.bo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.image.TIFFTranscoder;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ImageGenerator {
	
	public void generateOutput(String format, String svgString, String fileName)
 {
		Transcoder transcoder = null;
		String svgFileName = null;
		TranscoderInput input;
		OutputStream ostream = null;
		try {
			//JsonReadAndWriter readAndWriter = new JsonReadAndWriter();
			//svgFileName = readAndWriter.writeToFile(fileName+".svg", svgString);
			//svgFileName = "D:/projects/biz/UMLWeb/images/zz.svg";
			svgFileName = fileName+".svg";
			if (format.equalsIgnoreCase("JPG")) {
				transcoder = new JPEGTranscoder();
				/*transcoder.addTranscodingHint(JPEGTranscoder.KEY_XML_PARSER_CLASSNAME,
				        "org.apache.crimson.parser.XMLReaderImpl");*/
				transcoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(1.0));
				ostream = new FileOutputStream(fileName+"."+"jpg");
			}
			else if (format.equalsIgnoreCase("PNG")) {
				transcoder = new PNGTranscoder();
				ostream = new FileOutputStream(fileName+"."+"png");
			}
			else if (format.equalsIgnoreCase("TIF")) {
				transcoder = new TIFFTranscoder();
				ostream = new FileOutputStream(fileName+"."+"tif");
			}
			/*else if (format.equalsIgnoreCase("pdf")) {
				transcoder = new PDFTranscoder();
				ostream = new FileOutputStream("out."+format);
			}*/
			//input = new TranscoderInput(new FileInputStream(svgFileName));
			String svgURI = new File(svgFileName).toURL().toString();
			System.out.println(svgURI);
	        input = new TranscoderInput(svgURI);
			TranscoderOutput output = new TranscoderOutput(ostream);
			transcoder.transcode(input, output);
			ostream.flush();
			ostream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TranscoderException e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.exit(0);
	}
	
	public  String RemoveAllXmlNamespace(String xmlData)
    {
		SAXBuilder builder = new SAXBuilder();
    try {
        
        Document doc = builder.build(new File(xmlData));
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        out.output(doc, System.out);

        //
        // Get the root element and find a child named row and remove
        // its attribute named "userid"
        //
       /* Element root = doc.getRootElement();
        root.getChild("row").removeAttribute("userid");*/
        out.output(doc, System.out);
        System.out.println(out);
    } catch (JDOMParseException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (JDOMException e) {
		e.printStackTrace();
	}
	return xmlData;
	}
	
	public static void main(String[] args) {
		
		ImageGenerator imageGenerator = new ImageGenerator();
		imageGenerator.generateOutput("png", "", "D:/projects/biz/UMLWeb/images/3");
		String svgFileName = "D:/projects/biz/UMLWeb/images/zz.svg";
		//imageGenerator.RemoveAllXmlNamespace(svgFileName);
	}
}
