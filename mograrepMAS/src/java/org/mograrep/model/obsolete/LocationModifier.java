package org.mograrep.model.obsolete;


import org.mograrep.model.IDMSet;
import org.mograrep.model.idgs.DoubleValueModifier;
import org.mograrep.model.idgs.MatchedInstanceDataValueModifier;
import org.mograrep.model.matchFunctions.ExactMatchFunction;
import org.semanticweb.owlapi.model.IRI;

public class LocationModifier extends IDMSet {
	
	IRI locationIRI;
	public LocationModifier(double xOffset, double xStdDev, double yOffset, double yStdDev)
	{
		super("LocationModifier("+xOffset+", "+xStdDev +", "+yOffset +", "+yStdDev+")");
		
		this.setMatchFunction(new ExactMatchFunction(IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#Location"), null));
		
		ExactMatchFunction eamfX = new ExactMatchFunction(IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#coordinateX"));
		ExactMatchFunction eamfY = new ExactMatchFunction(IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#coordinateY"));
		MatchedInstanceDataValueModifier modX = new MatchedInstanceDataValueModifier(new DoubleValueModifier(xOffset, xStdDev), eamfX, "modCoordinateX");
		MatchedInstanceDataValueModifier modY = new MatchedInstanceDataValueModifier(new DoubleValueModifier(yOffset, yStdDev), eamfY, "modCoordinateY");
		
		this.addValueModifier(modX);
		this.addValueModifier(modY);
		
	}
	

}
