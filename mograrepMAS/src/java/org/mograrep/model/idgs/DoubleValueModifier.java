package org.mograrep.model.idgs;

import java.util.Random;

import org.mograrep.model.ContextData;
import org.mograrep.model.InstanceDataValueModifier;
import org.mograrep.model.RandomHolder;
import org.mograrep.model.cdvs.DoubleContextDataValue;
import org.semanticweb.owlapi.model.IRI;

public abstract class DoubleValueModifier implements InstanceDataValueModifier {
	
	double offset;
	double stdDev;
	IRI matchProperty;
	

	public DoubleValueModifier(double offset, double stdDev, IRI matchProperty, String name)
	{
		this.offset = offset;
		this.stdDev = stdDev;
		this.matchProperty = matchProperty;
	}
	
	public DoubleValueModifier(double offset, double stdDev, IRI matchProperty)
	{
		this(offset, stdDev, matchProperty, matchProperty.getFragment());
	}
	
	@Override
	public boolean applyModifier(ContextData cd) {
		if(this.isApplicable(cd))
		{
			DoubleContextDataValue dcdv = (DoubleContextDataValue)cd.getValue();
			Random r = RandomHolder.getInstance();
			double newVal = (r.nextGaussian()*this.stdDev)+this.offset;
			dcdv.addValue(newVal);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean isApplicable(ContextData cd) {
		if(IDGMatchHelper.matchIRIs(cd.getDataProperty(), matchProperty))
		{
			return true;
		}else
		{
			return false;
		}
	}

	@Override
	public String getModifierName() {
		// TODO Auto-generated method stub
		return null;
	}

}
