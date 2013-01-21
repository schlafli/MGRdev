package org.mograrep.model.evalFunctions;

import org.mograrep.model.ContextDeviation;



public interface EvaluationFunction {
	
	/*
	 * We have a flattened view of the context, with only the CIs with values being evaluated
	 * If we just use the context deviation we get:
	 * (chain IRI)->(ContextDeviation) 
	 * 
	 */
	
	
	//also, a deviation is just 1 property so each evaluationFunction is over 1 property

	
	public double getEvaluation(ContextDeviation dev);
	
	
	/*TODO:
	 * 
	 *	Decide on how evaluation functions will work:
	 *		1: By datatype -> e.g. double value
	 *		2: By property -> this is really just an extension of the first one...
	 *
	 *	Make some initial functions
	 *
	 *	Start with the xml specification of experiments
	 *
	 *	Modify the DataValueModifiers to be completely generic 
	 *		This is so they can be completely specified in the XML
	 */
	
	
	/*
	 * We could have 3 initial functions. Good if: within range, below range, above range
	 * 
	 * 
	 * 
	 */
	
	
//	/**
//	 * Compares two contexts and returns an evaluation
//	 * @param agreed The agreed context
//	 * @param performed	The performed context
//	 * @return A value representing the preference (up to the user to interpret this)
//	 */
//	public double compare(ContextInformation agreed, ContextInformation performed);
	
//	/**
//	 * Different preference function may carry different weights, this gets method gets the weight of this one
//	 * @return A double representing the weight (between 0 and whatever)
//	 */
//	public double getWeight();
	
	
//	public boolean isApplicable(ContextData cd);
	
//	public double setWeight(double weight);
}
