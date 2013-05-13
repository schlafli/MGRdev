package org.mograrep.xml.parsers;

import org.w3c.dom.Element;

public interface GenericParser<T> {

	public T generateFromElement(Element e);
	
}
