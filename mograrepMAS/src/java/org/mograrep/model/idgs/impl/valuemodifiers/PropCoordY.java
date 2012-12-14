package org.mograrep.model.idgs.impl.valuemodifiers;

import org.mograrep.model.idgs.DoubleValueModifier;
import org.semanticweb.owlapi.model.IRI;

public class PropCoordY extends DoubleValueModifier {
	
	public PropCoordY(double offset, double stdDev) {
		super(offset, stdDev, IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#coordinateY"), "modCoordinateY");
		// TODO Auto-generated constructor stub
	}

}
