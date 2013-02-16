package org.mograrep.model.obsolete;

import java.util.ArrayList;
import org.mograrep.model.matchFunctions.ExactMatchFunction;
import org.semanticweb.owlapi.model.IRI;

public class SubTypeLocationModifier extends LocationModifier {

	IRI SubType;
	ArrayList<IRI> endChain;
	
	public SubTypeLocationModifier(double xOffset, double xStdDev, double yOffset, double yStdDev, IRI SubType)
	{
		super(xOffset, xStdDev, yOffset, yStdDev);
		ExactMatchFunction eamf = (ExactMatchFunction)mf;
		eamf.addParentIRI(SubType);
	}
}
