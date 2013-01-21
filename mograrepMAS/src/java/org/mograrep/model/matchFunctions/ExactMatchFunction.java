package org.mograrep.model.matchFunctions;

import java.util.ArrayList;
import java.util.List;

import org.mograrep.model.ContextInformation;
import org.mograrep.model.idgs.IRIMatchHelper;
import org.semanticweb.owlapi.model.IRI;

public class ExactMatchFunction extends MatchFunction {

	private List<IRI> matchChain;
	
	public ExactMatchFunction(IRI property)
	{
		matchChain = new ArrayList<>();
		matchChain.add(property);
	}
	
	public void addParentIRI(IRI parent)
	{
		matchChain.add(0, parent);
	}
	
	
	public boolean isApplicable(List<ContextInformation> parents, IRI cType,
			IRI property) {
		
		
		List<IRI> sourceChain = new ArrayList<>(parents.size()+2);
		for(ContextInformation ci : parents)
		{
			sourceChain.add(ci.getType());
		}
		sourceChain.add(cType);
		sourceChain.add(property);
		
		return IRIMatchHelper.matchEndChain(sourceChain, matchChain);
	}

}
