package org.mograrep.xml.parsers;


import org.mograrep.model.InstanceDataValueModifier;
import org.mograrep.model.idgs.DoubleValueModifier;
import org.mograrep.xml.XMLHelper;
import org.w3c.dom.Element;

public class DoubleValueModifierParser implements GenericParser<InstanceDataValueModifier> {

	@Override
	public InstanceDataValueModifier generateFromElement(Element e) {
		Element offset = XMLHelper.getChildElementByTagName(e, "offset");
		Element stdDev = XMLHelper.getChildElementByTagName(e, "stddev");
		Element name = XMLHelper.getChildElementByTagName(e, "name");
		DoubleValueModifier dvm = null;
		if(offset!=null && stdDev!=null)
		{
			try
			{
				double d_o = Double.parseDouble(offset.getTextContent());
				double d_s = Double.parseDouble(stdDev.getTextContent());
				if(name!=null)
				{
					dvm = new DoubleValueModifier(d_o, d_s, name.getTextContent());
				}
				else
				{
					dvm = new DoubleValueModifier(d_o, d_s);
				}
				
			}catch(RuntimeException e1)
			{
				System.err.println("Cannot create DoubleValueModifier, vars cannot be parsed");
			}
			
			
		}
		return dvm;
	}
	
}
