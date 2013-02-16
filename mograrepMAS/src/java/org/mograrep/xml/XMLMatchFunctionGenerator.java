package org.mograrep.xml;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import org.mograrep.model.matchFunctions.MatchFunction;
import org.mograrep.xml.parsers.MatchFunctionParser;
import org.w3c.dom.Element;

public class XMLMatchFunctionGenerator {
	
	HashMap<String, MatchFunctionParser> parserMap;
	Element matchFunctionSectionRoot = null;
	Element matchFunctionRoot = null;
	Element matchFunctionConfig = null;
	
	public XMLMatchFunctionGenerator()
	{
		parserMap = new HashMap<>();
	}
	
	public boolean loadMatchFunctionSection(Element experimentSectionRoot)
	{
		if(matchFunctionSectionRoot==null)
		{
			matchFunctionSectionRoot = XMLHelper.getChildElementByTagName(experimentSectionRoot, "matchfunctions");
		}else
		{
			System.err.println("root already loaded");
			return true;
		}
		
		if(matchFunctionSectionRoot==null)
		{
			System.err.println("Error, cannot load matchfunction section");
			return false;
		}
		else
		{
			matchFunctionConfig = XMLHelper.getChildElementByTagName(matchFunctionSectionRoot, "config");
			matchFunctionRoot = XMLHelper.getChildElementByTagName(matchFunctionSectionRoot, "functions");
			
			System.out.println((matchFunctionConfig!=null)?"Config load successfull":"Cannot load config section");
			System.out.println((matchFunctionRoot!=null)?"FunctionRoot load successfull":"Cannot load function root section");
			
			return matchFunctionConfig!=null;
		}
	}
	
	public MatchFunction getMatchFunctionByID(String id)
	{
		Element mfe = XMLHelper.getChildElementByTagNameWithAttr(matchFunctionRoot, "matchfunction", "id", id);
		
		if(mfe!=null)
		{
			String parser = mfe.getAttribute("parser");
			if(!parser.equals("") && parserMap.containsKey(parser))
			{
				return parserMap.get(parser).generateFunctionFromElement(mfe);
			}
		}
		return null;
	}
	
	public boolean instantiateParsers()
	{
		if(matchFunctionConfig!=null)
		{
			List<Element> parsers = XMLHelper.getChildElementsByTagName(matchFunctionConfig, "parser");
			for(Element parser : parsers)
			{
				String typeName = XMLHelper.getChildElementByTagName(parser, "typeName").getTextContent();
				String className = XMLHelper.getChildElementByTagName(parser, "className").getTextContent();
				
				//System.out.println(typeName);
				//System.out.println(className);
				
				try {
					MatchFunctionParser mfp = (MatchFunctionParser) Class.forName(className).newInstance();
					parserMap.put(typeName, mfp);
					System.out.println("Successfully loaded:"+className);
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e) {
					System.err.println(e.toString());
				} 
			}
			return true;
		}	
		return false;
	}
	
//	public static boolean getMatchFunctions(XMLParser parser)
//	{
//		Node mfs = parser.getSection("matchfunctions");
//		
//		if(mfs!=null)
//		{
//			return true;
//		}else
//		{
//			return false;
//		}
//	}
	
	
//	public static List<IRI> getMatchProperties(Element matchFunctionElement)
//	{
//		List<Element> properties = XMLHelper.getChildElementsByTagName(matchFunctionElement, "matchproperty");
//		List<IRI> iris = new ArrayList<>(properties.size());
//		if(properties.size()!=0)
//		{
//			for(Element child: properties)
//			{
//				
//			}
//		}
//		
//		
//	}
	
//	public static MatchFunction getMatchFunction(Element matchFunctionElement)
//	{
//		
//	}
	
	public static void main(String [] args)
	{
		XMLParser parser = new XMLParser();
		URI source=XMLHelper.getAbsoluteFilePath("org/mograrep/xml/tests", "testInput.xml");
		
		System.out.println(parser.loadDOMFromXMLFile(source));
		XMLMatchFunctionGenerator gen = new XMLMatchFunctionGenerator();
		gen.loadMatchFunctionSection(parser.getDocumentReferenceAsElement());
		gen.instantiateParsers();
		
		MatchFunction mf = gen.getMatchFunctionByID("1");
		System.out.println((mf!=null)?mf.toString():"null");
		
		
	}
}
