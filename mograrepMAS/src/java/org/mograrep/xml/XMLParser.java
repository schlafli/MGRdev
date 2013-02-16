package org.mograrep.xml;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLParser {

	Document inputDoc;

	public XMLParser()
	{

	}

	public Element getSection(String sectionName)
	{
		Element doc = this.getDocumentReferenceAsElement();
		
		List<Element> matches = XMLHelper.getChildElementsByTagName(doc, sectionName);
		//System.out.println(matches.size());
		if(matches.size()==1)
		{
			return matches.get(0);
		}
		return null;
	}
	
	public Element getDocumentReferenceAsElement()
	{
		return getDocumentReference().getDocumentElement();
	}
	
	public Document getDocumentReference()
	{
		return inputDoc;
	}
	
	public boolean loadDOMFromXMLFile(URI source)
	{
		inputDoc = null;
		boolean success = false;

		InputStream is = null;

		switch(source.getScheme().toLowerCase())
		{
		case "file":
			String filename=null;
			try{
				filename = source.toURL().getFile();
				if(new File(filename).exists())
				{
					is = new FileInputStream(filename);
				}
			} catch (Exception e)
			{
				System.err.println("Malformed URI: Not a valid URL");
				break;
			}
			//If we got here, there was no problem with the URI/file formatting

			break;
		default:
			System.err.println("Sorry scheme \""+source.getScheme()+"\" is not supported");
		}

		if(is!=null)
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			
			try {
				builder = dbf.newDocumentBuilder();
				inputDoc = builder.parse(is);
				is.close();
				success = true;
			} catch (ParserConfigurationException e1) {
				System.out.println("Error creating document builder, whatever that means");
			} catch (IOException e) {
				System.out.println("Error closing InputStream: Should not happen");
			} catch (SAXException e) {
				System.err.println("Something, something, something XML problem");
				//e.printStackTrace();
			}
			
		}
		return success;
	}

	public static void main(String args)
	{

		System.out.println("'Olla");

	}


}
