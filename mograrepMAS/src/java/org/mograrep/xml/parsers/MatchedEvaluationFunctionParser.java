package org.mograrep.xml.parsers;

import org.mograrep.model.evalFunctions.EvaluationFunction;
import org.mograrep.model.matchEvalFunctions.MatchedEvaluationFunction;
import org.mograrep.model.matchFunctions.MatchFunction;
import org.mograrep.xml.XMLHelper;
import org.w3c.dom.Element;

public class MatchedEvaluationFunctionParser extends ExtraDataGenericParser<MatchedEvaluationFunction> {

	@SuppressWarnings("unchecked")
	@Override
	public MatchedEvaluationFunction generateFromElement(Element e) {
		String matchFuncionString = "matchFunction";
		String evaluationFunctionString = "evaluationFunction";
		
		
		Element matchf = XMLHelper.getChildElementByTagName(e, matchFuncionString);
		Element evalf = XMLHelper.getChildElementByTagName(e, evaluationFunctionString);
		
		MatchedEvaluationFunction mef = null;
		
		
		if(matchf!=null && evalf!=null)
		{
			String mfid = XMLHelper.getChildElementByTagName(matchf, "id").getTextContent();
			String efid = XMLHelper.getChildElementByTagName(evalf, "id").getTextContent();
			
			ParserParser<EvaluationFunction> ppef = getPP(evaluationFunctionString);
			ParserParser<MatchFunction> ppmf = getPP(matchFuncionString);
			
			EvaluationFunction ef = ppef.getFunctionByID(efid);
			MatchFunction mf = ppmf.getFunctionByID(mfid);
			
			
			if(mf!=null && ef!=null)
			{
				mef = new MatchedEvaluationFunction(mf, ef);
			}else
			{
				System.out.println((mf==null)?((ef==null)?"mf==ef==null":"mf==null"):"ef==null");
			}
			
		}else
		{
			System.out.println((matchf==null)?((matchf==null)?"matchf==evalf==null":"matchf==null"):"evalf==null");
		}
		
		
		
		return mef;
	}

}
