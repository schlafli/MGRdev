package org.mograrep.model.idgs.impl.valuemodifiers;

import org.mograrep.model.idgs.DoubleValueModifier;
import org.semanticweb.owlapi.model.IRI;

public class PropCoordX extends DoubleValueModifier {
	
	public PropCoordX(double offset, double stdDev) {
		super(offset, stdDev, IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#coordinateX"), "modCoordinateX");
		// TODO Auto-generated constructor stub
	}

}
