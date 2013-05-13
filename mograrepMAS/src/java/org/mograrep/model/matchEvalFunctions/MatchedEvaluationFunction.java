package org.mograrep.model.matchEvalFunctions;

import org.mograrep.model.ContextDeviation;
import org.mograrep.model.evalFunctions.EvaluationFunction;
import org.mograrep.model.matchFunctions.MatchFunction;

public class MatchedEvaluationFunction {

	private MatchFunction mf;
	private EvaluationFunction ef;
	
	public MatchedEvaluationFunction(MatchFunction matchF, EvaluationFunction evalF)
	{
		this.mf = matchF;
		this.ef = evalF;
	}
	
	public boolean isApplicable(ContextDeviation dev)
	{
		return mf.isApplicable(dev);
	}
	
	public double getEvaluation(ContextDeviation dev)
	{
		return ef.getEvaluation(dev);
	}

	public String toString()
	{
		return "MEF: match("+mf.toString()+"), eval:("+ef.toString()+")";
		
	}
}
