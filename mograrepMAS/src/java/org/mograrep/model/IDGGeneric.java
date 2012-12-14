package org.mograrep.model;

import java.util.TreeSet;

import org.mograrep.model.cdvs.ContextDataValue;
import org.mograrep.model.cdvs.DoubleContextDataValue;
import org.mograrep.model.cdvs.IntegerContextDataValue;



public class IDGGeneric {

	public static void generateContextValues(ContextInformation ci)
	{
		if(ci.hasValues()){
			generateContextValueData(ci);
		}
		if(ci.hasContext())
		{
			for(ContextInformation rci:ci.getContextInformationList())
			{
				generateContextValues(rci);
			}
		}
		
	}

	private static void generateContextValueData(ContextInformation ci)
	{
		if(ci.hasValues())
		{
			TreeSet<ContextData> cdt = ci.getValues();
			for(ContextData cd: cdt)
			{
				if(cd.getValue()!=null)
				{
					continue;
				}else
				{
					cd.setValue(generateValue(cd.getMin(), cd.getMax()));
					cd.setMax(null);
					cd.setMin(null);
				}
			}
		}
	}

	private static ContextDataValue generateValue(ContextDataValue min, ContextDataValue max)
	{
		ContextDataValue cdv;
		int type;

		if(min!=null)
		{
			type = min.getDataValueID();
		}else
		{
			type = max.getDataValueID();
		}

		switch(type)
		{
		case ContextDataValue.DOUBLE:
			cdv = generateDoubleValue((DoubleContextDataValue)min, (DoubleContextDataValue)max); 
			break;
		case ContextDataValue.INTERGER:
			cdv = generateIntegerValue((IntegerContextDataValue)min, (IntegerContextDataValue)max);
			break;

		default:
			cdv = null;
			break;
		}
		
		return cdv;
	}

	private static DoubleContextDataValue generateDoubleValue(DoubleContextDataValue min, DoubleContextDataValue max)
	{
		double value;
		if(min!=null)
		{
			if(max!=null)
			{
				value = (min.getValue()+max.getValue())/2.0;
			}else
			{
				value = min.getValue()+1.0;//this could/should be done better
			}
		}else
		{
			value = max.getValue()-1.0;
		}
		return new DoubleContextDataValue(value);
	}
	
	private static IntegerContextDataValue generateIntegerValue(IntegerContextDataValue min, IntegerContextDataValue max)
	{
		int value;
		if(min!=null)
		{
			if(max!=null)
			{
				value = (int) Math.round((min.getValue()+max.getValue())/2.0);
			}else
			{
				value = min.getValue()+1;//this could/should be done better
			}
		}else
		{
			value = max.getValue()-1;
		}
		return new IntegerContextDataValue(value);
	}
}
