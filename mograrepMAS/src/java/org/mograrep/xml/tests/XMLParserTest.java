package org.mograrep.xml.tests;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.mograrep.xml.XMLParser;

public class XMLParserTest {

//	@Test
//	public void testGetDocumentReference() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testLoadDOMFromXMLFile() {
		String inputFile = "testInput.xml";
		String relativePath = "org/mograrep/xml/tests/" + inputFile;
		URI fileURI = null;
		
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try {
			 fileURI = cl.getResource(relativePath).toURI();
		} catch (URISyntaxException | NullPointerException e) {
			fail("Can't get fileURI");
		}
		XMLParser parser = new XMLParser();
		assert(parser.loadDOMFromXMLFile(fileURI));
	}

}
