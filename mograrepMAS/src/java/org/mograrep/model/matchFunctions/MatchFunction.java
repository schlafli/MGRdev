package org.mograrep.model.matchFunctions;

import java.util.List;

import org.mograrep.model.ContextDeviation;
import org.mograrep.model.ContextInformation;
import org.semanticweb.owlapi.model.IRI;

public abstract class MatchFunction {


	public boolean isApplicable(ContextDeviation dev)
	{
		return isApplicable(dev.getParents(), dev.getType(), dev.getAData().getDataProperty());
	}
	
	public abstract boolean isApplicable(List<ContextInformation> parents, IRI cType, IRI property);


}
