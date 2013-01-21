package org.mograrep.model.idgs.impl.contextmodifiers;

import java.util.List;

import org.mograrep.model.IDMSet;
import org.mograrep.model.idgs.IRIMatchHelper;
import org.mograrep.model.idgs.impl.valuemodifiers.PropCoordX;
import org.mograrep.model.idgs.impl.valuemodifiers.PropCoordY;
import org.semanticweb.owlapi.model.IRI;

public class LocationModifier extends IDMSet {
	
	IRI locationIRI;
	public LocationModifier(double xOffset, double xStdDev, double yOffset, double yStdDev)
	{
		super("LocationModifier("+xOffset+", "+xStdDev +", "+yOffset +", "+yStdDev+")");
		this.addValueModifier(new PropCoordX(xOffset, xStdDev));
		this.addValueModifier(new PropCoordY(yOffset, yStdDev));
		locationIRI = IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#Location");
	}
	
	@Override
	public boolean isApplicable(List<IRI> chain) {
		return IRIMatchHelper.matchFinal(chain, locationIRI);
	}
	
	

}
