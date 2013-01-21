package org.mograrep.model.idgs.impl.contextmodifiers;

import java.util.ArrayList;
import java.util.List;

import org.mograrep.model.idgs.IRIMatchHelper;
import org.semanticweb.owlapi.model.IRI;

public class SubTypeLocationModifier extends LocationModifier {

	IRI SubType;
	ArrayList<IRI> endChain;
	
	public SubTypeLocationModifier(double xOffset, double xStdDev, double yOffset, double yStdDev, IRI SubType)
	{
		super(xOffset, xStdDev, yOffset, yStdDev);
		this.SubType = SubType;
		endChain = new ArrayList<>(2);
		endChain.add(this.SubType);
		endChain.add(this.locationIRI);
	}
	
	public boolean isApplicable(List<IRI> chain) {
			return IRIMatchHelper.matchEndChain(chain, endChain);
	}
}
