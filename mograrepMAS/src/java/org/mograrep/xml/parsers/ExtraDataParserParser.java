package org.mograrep.xml.parsers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.mograrep.xml.SectionHolder;
import org.mograrep.xml.XMLHelper;
import org.w3c.dom.Element;

public class ExtraDataParserParser<T> {

	
	Map<String, ExtraDataGenericParser<T>> parserMap;
	Element section=null;
	Element configSection=null;
	Element	dataSection=null;
	
	
	public ExtraDataParserParser()
	{
		parserMap = new HashMap<>();
		
	}
	
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

	
	public boolean instantiateParsers(SectionHolder sh)
	{	
		if(configSection!=null)
		{
			List<Element> parsers = XMLHelper.getChildElementsByTagName(configSection, "parser");
			for(Element parser : parsers)
			{
				String typeName = XMLHelper.getChildElementByTagName(parser, "typeName").getTextContent();
				String className = XMLHelper.getChildElementByTagName(parser, "className").getTextContent();
				List<Element> requiredSections = XMLHelper.getChildElementsByTagName(parser, "requiredSection");
				try {
					@SuppressWarnings("unchecked")
					ExtraDataGenericParser<T> edgp = (ExtraDataGenericParser<T>) Class.forName(className).newInstance();
					for(Element reqSec: requiredSections)
					{
						String sectionName = XMLHelper.getChildElementByTagName(reqSec, "section").getTextContent();
						String asName = XMLHelper.getChildElementByTagName(reqSec, "asName").getTextContent();
						
						@SuppressWarnings("rawtypes")
						ParserParser pp = sh.getSection(sectionName);
						
						edgp.addPP(asName, pp);
						
						//asName is the name that is the XML tag used
						//section is the name of the section 
					}
					
					
					parserMap.put(typeName, edgp);
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
}
