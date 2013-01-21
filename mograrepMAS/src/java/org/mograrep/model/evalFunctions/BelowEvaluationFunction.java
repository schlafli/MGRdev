package org.mograrep.model.evalFunctions;

import org.mograrep.model.ContextData;
import org.mograrep.model.ContextDeviation;
import org.mograrep.model.IDGGeneric;

public class BelowEvaluationFunction implements EvaluationFunction {


	@Override
	public double getEvaluation(ContextDeviation dev) {
		ContextData agreed = dev.getAData();
		ContextData performed = dev.getPData();

		if(performed.getValue()==null)
		{
			performed = performed.deepCopy();
			IDGGeneric.generateAverageValues(performed);
		}


		//TODO: Have a better way to evaluate context
		boolean good = false;

		if(agreed.getValue()==null)
		{
			if(agreed.getMin()!=null)
			{
				//This means we have a value bound by min and max
				if(performed.getValue().lessThan(agreed.getMin()))
				{
					//Good
					good = true;
				}
			}
		}else
		{
			//This means we have an agreed value, rather than range
			if(performed.getValue().lessThan(agreed.getValue()))
			{
				//Good
				good = true;
			}
		}

		return (good)?1.0:0.0;
	}

}
