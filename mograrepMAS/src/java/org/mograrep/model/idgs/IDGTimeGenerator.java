package org.mograrep.model.idgs;

import java.util.List;

import org.mograrep.model.ContextData;
import org.mograrep.model.InstanceDataGenerator;
import org.semanticweb.owlapi.model.IRI;

public class IDGTimeGenerator implements InstanceDataGenerator {

	double offset, stdev;
	String generatorName;
	List<IRI> matchItems;
	
	public IDGTimeGenerator(double offset, double sd, String name)
	{
		this.offset = offset;
		this.stdev = sd;
		this.generatorName = name;
	}
	
	
	public IDGTimeGenerator(double offset, double sd)
	{
		this(sd, offset, "TimeOffsetSD("+offset+", "+sd+")");
	}
	
	public String getGeneratorName() {
		return generatorName;
	}

	public double matchType(List<IRI> chain) {
	return 1;	
	}

	public List<ContextData> generateData(List<IRI> chain) {
		// TODO Auto-generated method stub
		return null;
	}

}
