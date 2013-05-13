package org.mograrep.xml;

import org.mograrep.xml.parsers.ParserParser;

public interface SectionHolder {

	@SuppressWarnings("rawtypes")
	public ParserParser getSection(String sectionName);

}
