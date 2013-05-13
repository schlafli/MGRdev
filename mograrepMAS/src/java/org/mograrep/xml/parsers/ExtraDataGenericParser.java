package org.mograrep.xml.parsers;

import java.util.HashMap;

import org.w3c.dom.Element;

public abstract class ExtraDataGenericParser<T>{


	@SuppressWarnings("rawtypes")
	private HashMap<String, ParserParser> map;

	@SuppressWarnings("rawtypes")
	protected ParserParser getPP(String name)
	{
		return map.get(name);
	}
	
	public ExtraDataGenericParser()
	{
		map = new HashMap<>();
	}

	public boolean addPP(String name, @SuppressWarnings("rawtypes") ParserParser pp)
	{
		if(map.containsKey(name))
		{
			return false;
		}else
		{
			map.put(name, pp);
			return true;
		}
	}
	
	public abstract T generateFromElement(Element e);


}
