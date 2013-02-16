package org.mograrep.xml.parsers;

import org.mograrep.model.matchFunctions.MatchFunction;
import org.w3c.dom.Element;

public interface MatchFunctionParser {

	public MatchFunction generateFunctionFromElement(Element e);
	
}
