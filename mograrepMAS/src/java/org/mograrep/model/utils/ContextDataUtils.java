package org.mograrep.model.utils;

import java.util.Collection;
import org.mograrep.model.cdvs.ContextDataValue;
import org.mograrep.model.cdvs.DoubleContextDataValue;
import org.mograrep.model.cdvs.IntegerContextDataValue;

public class ContextDataUtils {

	public static DoubleContextDataValue subtract(ContextDataValue a, ContextDataValue b)
	{
		return new DoubleContextDataValue(a.asDouble() - b.asDouble());
	}
	
	public static ContextDataValue addViaDoubleConversion(ContextDataValue a, double toAdd)
	{
		
		ContextDataValue ret = null;
		switch(a.getDataValueID())
		{
		
		case ContextDataValue.DOUBLE:
			ret = DoubleContextDataValue.fromDouble(a.asDouble()+toAdd);
			break;
		case ContextDataValue.INTERGER:
			ret = IntegerContextDataValue.fromDouble(a.asDouble()+toAdd);
			break;
		default:
			System.out.println("Error creating context data type:\"" + a.getDataValueID()+"\" using DoubleContextDataValue");
			ret = DoubleContextDataValue.fromDouble(a.asDouble()+toAdd);
			break;
		}
		return ret;
		
		
	}
	
	
	public static DoubleContextDataValue getMean(ContextDataValue a, ContextDataValue b)
	{
		return null;
	}

	public static DoubleContextDataValue getMean(Collection<ContextDataValue> a)
	{
		return null;
	}
	
	public static DoubleContextDataValue getStdDev(Collection<ContextDataValue> a)
	{
		return null;
	}
	
	
	public static DoubleContextDataValue sum(Collection<ContextDataValue> a)
	{
		
		return null;
	}
	
	public static DoubleContextDataValue multiply(ContextDataValue a, ContextDataValue b)
	{
		return null;
	}
	
	public static DoubleContextDataValue divide(ContextDataValue a, double divisor){
		return null;
	}
	
	public static DoubleContextDataValue divide(ContextDataValue a, ContextDataValue b){
		return null;
	}
	
	
	public static DoubleContextDataValue sqrt(ContextDataValue a)
	{
		
		return null;
	}
	
	public static DoubleContextDataValue add(ContextDataValue a, ContextDataValue b)
	{	
		return null;
	}
	
	
	
}
