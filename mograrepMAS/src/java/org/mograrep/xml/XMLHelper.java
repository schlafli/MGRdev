package org.mograrep.xml;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLHelper {


	public static URI getAbsoluteFilePath(String relativePath, String inputFile)
	{
		while(relativePath.startsWith("/"))
		{
			relativePath = relativePath.substring(1);
		}
		while(relativePath.endsWith("/"))
		{
			relativePath = relativePath.substring(0, relativePath.length()-1);
		}
		relativePath +=  "/" + inputFile;
		URI fileURI = null;

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try {
			fileURI = cl.getResource(relativePath).toURI();
		} catch (URISyntaxException | NullPointerException e) {
			fileURI = null;
		}
		return fileURI;
	}

	public static Element getChildElementByTagName(Element parent, String name)
	{
		return getChildElementByTagNameWithAttr(parent, name, null, null);
	}

	public static List<Element> getChildElementsByTagName(Element parent, String name)
	{
		return getChildElementsByTagNameWithAttr(parent, name, null, null);
	}

	public static Element getChildElementByTagNameWithAttr(Element parent, String name, String attributeName, String attributeValue)
	{
		List<Element> results = getChildElementsByTagNameWithAttr(parent, name, attributeName, attributeValue);
		if(results.size()==1)
		{
			return results.get(0);
		}else
		{
			return null;
		}
	}
	
	
	public static List<Element> getChildElementsByTagNameWithAttr(Element parent, String name, String attributeName, String attributeValue)
	{
		NodeList children = parent.getChildNodes();
		ArrayList<Element> matches = new ArrayList<>();

		for(int i=0;i<children.getLength();i++)
		{
			//System.out.println(children.item(i).getNodeType());
			if(children.item(i).getNodeType()==Node.ELEMENT_NODE)
			{
				Element child = (Element)children.item(i);
				//System.out.println(child.getTagName());
				if(child.getTagName().equals(name))
				{
					if(attributeName==null || attributeName.equals(""))
					{
						matches.add(child);

					}else
					{
						if(child.hasAttribute(attributeName))
						{
							if(child.getAttribute(attributeName).equals(attributeValue))
							{
								matches.add(child);
							}
						}
					}
					
				}
			}
		}
		return matches;
	}
}
