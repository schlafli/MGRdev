package org.mograrep.model.idgs;

import java.util.Random;

import org.mograrep.model.ContextData;
import org.mograrep.model.InstanceDataValueModifier;
import org.mograrep.model.RandomHolder;
import org.mograrep.model.cdvs.ContextDataValue;
import org.mograrep.model.cdvs.DoubleContextDataValue;

public class DoubleValueModifier implements InstanceDataValueModifier {
	
	double offset;
	double stdDev;
	String name;
	

	public DoubleValueModifier(double offset, double stdDev,String name)
	{
		this.offset = offset;
		this.stdDev = stdDev;
		this.name = name;
	}
	
	public DoubleValueModifier(double offset, double stdDev)
	{
		this(offset, stdDev, "_dbvm("+offset+", "+stdDev+")");
	}
	
	
	
	@Override
	public boolean applyModifier(ContextData cd) {
		//we need to do the following check, as a generic matcher may be used with this value modifier
		//
		//	The alternative would be to make this value modifier apply to all doubles, and the matcher to specify details.
		// 2nd approach selected
		if(cd.getDataValueType() == ContextDataValue.DOUBLE)
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

//	@Override
//	public boolean isApplicable(ContextData cd) {
//		if(IRIMatchHelper.matchIRIs(cd.getDataProperty(), matchProperty))
//		{
//			return true;
//		}else
//		{
//			return false;
//		}
//	}

	@Override
	public String getModifierName() {
		return name;
	}

}
