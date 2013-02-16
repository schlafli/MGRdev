package org.mograrep.model.matchFunctions;

import java.util.ArrayList;
import java.util.List;

import org.mograrep.model.ContextDeviation;
import org.mograrep.model.ContextInformation;
import org.semanticweb.owlapi.model.IRI;

public abstract class MatchFunction {


	public boolean isApplicable(ContextDeviation dev)
	{
		return isApplicable(dev.getParents(), dev.getType(), dev.getAData().getDataProperty());
	}
	
	public boolean isApplicable(List<ContextInformation> parents, IRI cType, IRI property)
	{
		List<IRI> sourceChain = new ArrayList<>(parents.size()+1);
		for(ContextInformation ci : parents)
		{
			sourceChain.add(ci.getType());
		}
		sourceChain.add(cType);
		//sourceChain.add(property);
		return this.isApplicable(sourceChain, property);
	}
	
	public abstract boolean isApplicable(List<IRI> sourceChain, IRI property);

}
