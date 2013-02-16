package org.mograrep.xml.parsers;

import java.util.ArrayList;
import java.util.List;

import org.mograrep.model.matchFunctions.ExactMatchFunction;
import org.mograrep.model.matchFunctions.MatchFunction;
import org.mograrep.xml.XMLHelper;
import org.semanticweb.owlapi.model.IRI;
import org.w3c.dom.Element;

public class ExactMatchFunctionParser implements MatchFunctionParser {

	
	

	@Override
	public MatchFunction generateFunctionFromElement(Element e) {
		
		List<Element> matchProps = XMLHelper.getChildElementsByTagName(e, "matchproperty");
		
		List<Element> matchConcepts = XMLHelper.getChildElementsByTagName(e, "matchconcept");
		
		
		//since this is an ExactMatchFunction it will have 0-any matchProperties
		//				and 0-any parents, with at most 1 per level
		ArrayList<IRI> matchProperties = null;
		
		if(matchProps.size()>0)
		{
			matchProperties = new ArrayList<>(matchProps.size());
			for(Element prop : matchProps)
			{
				String iri = XMLHelper.getChildElementByTagName(prop, "value").getTextContent();
				matchProperties.add(IRI.create(iri));
			}
		}
		
		ArrayList<IRI> parents = null;
		if(matchConcepts.size()>0)
		{
			parents = new ArrayList<>(matchConcepts.size());
			for(Element concept: matchConcepts)
			{
				String iri = XMLHelper.getChildElementByTagName(concept, "value").getTextContent();
				int index = Integer.parseInt(XMLHelper.getChildElementByTagName(concept, "level").getTextContent());
				index = (matchConcepts.size() - 1) - index;
				parents.add(index, IRI.create(iri));
			}
		}
		
		
		return new ExactMatchFunction(parents, matchProperties);
	}

}
