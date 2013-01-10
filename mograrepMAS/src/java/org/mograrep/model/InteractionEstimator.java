package org.mograrep.model;

import java.util.ArrayList;

import org.javatuples.Pair;

public interface InteractionEstimator {

	
	public void train(ArrayList<Pair<ContextInformation, ContextInformation>> data);
	
	public boolean supportsIncremental();
	
	public boolean addToTraining(Pair<ContextInformation, ContextInformation> data);
	
	
	
	public Pair<ContextInformation, Double> predict(ContextInformation agreed);
	
}
