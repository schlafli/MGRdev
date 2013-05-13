package org.mograrep.xml.parsers;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mograrep.model.InstanceDataValueModifier;
import org.mograrep.model.evalFunctions.EvaluationFunction;
import org.mograrep.model.matchFunctions.MatchFunction;
import org.mograrep.xml.XMLHelper;
import org.mograrep.xml.XMLParser;
import org.w3c.dom.Element;

public class ParserParser<T> {

	//private T t;
	Map<String, GenericParser<T>> parserMap;
	Element section=null;
	Element configSection=null;
	Element	dataSection=null;

	public ParserParser()
	{
		parserMap = new HashMap<>();
	}

//	protected ParserParser(boolean instantiate)
//	{
//		
//	}

	public boolean loadSections(Element rootSection)
	{
		boolean success = true;
		this.section = rootSection;

		configSection = XMLHelper.getChildElementByTagName(rootSection, "config");
		dataSection = XMLHelper.getChildElementByTagName(rootSection, "data");

		if(dataSection==null)
		{
			success = false;
			System.err.println("Cannot load data section of: <"+ rootSection.getTextContent() +">");
		}

		if(configSection==null)
		{
			success = false;
			System.err.println("Cannot load config section of: <"+ rootSection.getTextContent() +">");
		}

		return success;
	}

	public T getFunctionByID(String id)
	{
		Element mfe = XMLHelper.getChildElementByTagNameWithAttr(dataSection, "function", "id", id);

		if(mfe!=null)
		{
			String parser = mfe.getAttribute("parser");
			if(!parser.equals("") && parserMap.containsKey(parser))
			{
				return parserMap.get(parser).generateFromElement(mfe);
			}
		}
		return null;
	}

	public boolean instantiateParsers()
	{

		if(configSection!=null)
		{
			List<Element> parsers = XMLHelper.getChildElementsByTagName(configSection, "parser");
			for(Element parser : parsers)
			{
				String typeName = XMLHelper.getChildElementByTagName(parser, "typeName").getTextContent();
				String className = XMLHelper.getChildElementByTagName(parser, "className").getTextContent();

				try {
					@SuppressWarnings("unchecked")
					GenericParser<T> mfp = (GenericParser<T>) Class.forName(className).newInstance();
					parserMap.put(typeName, mfp);
					System.out.println("Successfully loaded:"+className);
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | RuntimeException e ) {
					e.printStackTrace();
//					System.err.println(e.toString());
				} 
			}
			return true;
		}	
		return false;
	}

//	public Class getType()
//	{
//		return t.getClass();
//	}
	//public static <T> Map<String, T> instantiateParsers(Element configSection, Class<T> clazz)
	//{
	//	if(configSection!=null)
	//	{
	//		Map<String, T> parserMap = new HashMap<String, T>();
	//		List<Element> parsers = XMLHelper.getChildElementsByTagName(configSection, "parser");
	//		for(Element parser : parsers)
	//		{
	//			String typeName = XMLHelper.getChildElementByTagName(parser, "typeName").getTextContent();
	//			String className = XMLHelper.getChildElementByTagName(parser, "className").getTextContent();
	//
	//			try {
	//				GenericParser<T> mfp = (GenericParser<T>) Class.forName(className).newInstance();
	//				parserMap.put(typeName, mfp);
	//				System.out.println("Successfully loaded:"+className);
	//			} catch (InstantiationException | IllegalAccessException
	//					| ClassNotFoundException e) {
	//				System.err.println(e.toString());
	//			} 
	//		}
	//		return true;
	//	}	
	//	return false;
	//}
	
	public static void main(String args[])
	{	
		XMLParser parser = new XMLParser();
		URI source=XMLHelper.getAbsoluteFilePath("org/mograrep/xml/tests", "testInput.xml");

		System.out.println(parser.loadDOMFromXMLFile(source));

		ParserParser<MatchFunction> ppmf = new ParserParser<>();
		ParserParser<InstanceDataValueModifier> ppvm = new ParserParser<>();
		ParserParser<EvaluationFunction> ppef = new ParserParser<>();
		
		
		Element mfs = XMLHelper.getChildElementByTagName(parser.getDocumentReferenceAsElement(), "matchfunctions");
		Element vms = XMLHelper.getChildElementByTagName(parser.getDocumentReferenceAsElement(), "valuemodifiers");
		Element ems = XMLHelper.getChildElementByTagName(parser.getDocumentReferenceAsElement(), "evaluationfunctions");
		
		
		
		ppmf.loadSections(mfs);
		ppvm.loadSections(vms);
		ppef.loadSections(ems);
		
		
		System.out.println("instantiating:"+ppmf.instantiateParsers());
		System.out.println("instantiating:"+ppvm.instantiateParsers());
		System.out.println("instantiating:"+ppef.instantiateParsers());
		
		MatchFunction mf = ppmf.getFunctionByID("1");
		System.out.println((mf!=null)?mf.toString():"null");
		
		InstanceDataValueModifier a = ppvm.getFunctionByID("101");
		System.out.println((a!=null)?a.getModifierName():"null");
		
		EvaluationFunction e = ppef.getFunctionByID("201");
		System.out.println((e!=null)?e.toString():"null");
		
		
	}
}
