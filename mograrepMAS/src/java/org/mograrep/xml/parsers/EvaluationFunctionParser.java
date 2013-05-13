package org.mograrep.xml.parsers;

import org.mograrep.model.evalFunctions.AboveEvaluationFunction;
import org.mograrep.model.evalFunctions.BelowEvaluationFunction;
import org.mograrep.model.evalFunctions.EvaluationFunction;
import org.mograrep.model.evalFunctions.InrangeEvaluationFunction;
import org.mograrep.xml.XMLHelper;
import org.w3c.dom.Element;

public class EvaluationFunctionParser implements GenericParser<EvaluationFunction>{

	@Override
	public EvaluationFunction generateFromElement(Element e) {
		EvaluationFunction evf = null;

		Element evaltype = XMLHelper.getChildElementByTagName(e, "evaltype");

		if(evaltype!=null)
		{
			String type = evaltype.getTextContent();
			switch(type.toLowerCase())
			{
			case "inrange":
				evf = new InrangeEvaluationFunction();
				break;
			case "below":
				evf = new BelowEvaluationFunction();
				break;
			case "above":
				evf = new AboveEvaluationFunction();
				break;
			default:
				break;
			}
		}

		return evf;
	}


}
