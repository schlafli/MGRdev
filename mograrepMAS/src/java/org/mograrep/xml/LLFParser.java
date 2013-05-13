package org.mograrep.xml;

import java.net.URI;
import java.util.HashMap;

import org.mograrep.model.InstanceDataValueModifier;
import org.mograrep.model.evalFunctions.EvaluationFunction;
import org.mograrep.model.matchEvalFunctions.MatchedEvaluationFunction;
import org.mograrep.model.matchFunctions.MatchFunction;
import org.mograrep.xml.parsers.ExtraDataParserParser;
import org.mograrep.xml.parsers.ParserParser;
import org.w3c.dom.Element;

public class LLFParser implements SectionHolder{

	
	ParserParser<MatchFunction> matchFunctions;
	ParserParser<InstanceDataValueModifier> instanceDataValueModifiers;
	ParserParser<EvaluationFunction> evaluationFunctions;
	
	private HashMap<String, ParserParser> ppMap;
	
	public LLFParser()
	{
		ppMap = new HashMap<>();
	}
	
	public void setSection(String sectionName, ParserParser pp)
	{
		ppMap.put(sectionName, pp);
	}
	

	@Override
	public ParserParser getSection(String sectionName) {
		return ppMap.get(sectionName);
	}

	public static void main(String [] args)
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
		
		
		Element mevf = XMLHelper.getChildElementByTagName(parser.getDocumentReferenceAsElement(), "matchedfunctions");
		
		
		
		ppmf.loadSections(mfs);
		ppvm.loadSections(vms);
		ppef.loadSections(ems);
		
		System.out.println("instantiating:"+ppmf.instantiateParsers());
		System.out.println("instantiating:"+ppvm.instantiateParsers());
		System.out.println("instantiating:"+ppef.instantiateParsers());
		
		LLFParser llfp = new LLFParser();
		llfp.setSection("matchfunctions", ppmf);
		llfp.setSection("valuemodifiers", ppvm);
		llfp.setSection("evaluationfunctions", ppef);
		
		ExtraDataParserParser<MatchedEvaluationFunction> edppmef = new ExtraDataParserParser<>();
		edppmef.loadSections(mevf);
		System.out.println("instantiating:"+edppmef.instantiateParsers(llfp));
		
		
		
		MatchFunction mf = ppmf.getFunctionByID("1");
		System.out.println((mf!=null)?mf.toString():"null");
		
		InstanceDataValueModifier a = ppvm.getFunctionByID("101");
		System.out.println((a!=null)?a.getModifierName():"null");
		
		EvaluationFunction e = ppef.getFunctionByID("201");
		System.out.println((e!=null)?e.toString():"null");
		
		MatchedEvaluationFunction mef = edppmef.getFunctionByID("1001");
		System.out.println((mef!=null)?mef.toString():"null");
		
		
	}

}
