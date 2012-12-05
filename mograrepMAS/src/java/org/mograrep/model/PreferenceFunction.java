package org.mograrep.model;



public interface PreferenceFunction {
	
	/**
	 * Compares two contexts and returns an evaluation
	 * @param agreed The agreed context
	 * @param performed	The performed context
	 * @return A value representing the preference (up to the user to interpret this)
	 */
	public double compare(ContextInformation agreed, ContextInformation performed);
	
	/**
	 * Different preference function may carry different weights, this gets method gets the weight of this one
	 * @return A double representing the weight (between 0 and whatever)
	 */
	public double getWeight();
	
	public double setWeight(double weight);
}
