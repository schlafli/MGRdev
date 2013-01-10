package org.mograrep.model.intEst;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.mograrep.model.ContextData;
import org.mograrep.model.ContextDeviation;
import org.mograrep.model.ContextInformation;
import org.mograrep.model.IDGGeneric;
import org.mograrep.model.InteractionEstimator;
import org.mograrep.model.cdvs.ContextDataValue;
import org.mograrep.model.utils.ContextDataUtils;

public class SimpleAverageEstimator implements InteractionEstimator {
	
	ArrayList<ContextDeviation> contextHistory;
	
	public SimpleAverageEstimator()
	{
		contextHistory = new ArrayList<>();
	}
	
	@Override
	public void train(
			ArrayList<Pair<ContextInformation, ContextInformation>> data) {
		for(Pair<ContextInformation, ContextInformation> pair: data)
		{
			this.addToTraining(pair);
		}
	}

	@Override
	public boolean supportsIncremental() {
		return true;
	}

	@Override
	public boolean addToTraining(Pair<ContextInformation, ContextInformation> data) {
		List<ContextDeviation> cds = ContextDeviation.getDeviationsFromContextInformation(data.getValue0(), data.getValue1());
		contextHistory.addAll(cds);
		return true;
	}

	
	private Pair<ArrayList<Pair<ContextDeviation, Integer>>, Integer> getApplicableContexts(ContextInformation ci, ContextData cd)
	{
		
		ArrayList<Pair<ContextDeviation, Integer>> ret = new ArrayList<>();
		List<ContextInformation> parents = ci.getParents();
		
		int maxMatchDepth = 0;
		
		for(ContextDeviation cdv: contextHistory)
		{
			if(cd.sameType(cdv.getaData()))
			{
				if(ci.getType().equals(cdv.getType()))
				{
					int matchDepth = parents.size();
					if(matchDepth>cdv.getParents().size())
					{
						matchDepth = cdv.getParents().size();
					}
					int matchLevel = 1;
					for(int i=0;i<matchDepth;i++)
					{
						ContextInformation ciPar = parents.get(parents.size()-(i+1));
						ContextInformation cdvPar = cdv.getParents().get(cdv.getParents().size()-(i+1));
						
						if(ciPar.sameType(cdvPar))
						{
							matchLevel++;
						}else
						{
							break;
						}
					}	
					if(matchLevel>maxMatchDepth)
					{
						maxMatchDepth = matchLevel;
					}
					ret.add(new Pair<ContextDeviation, Integer>(cdv, matchLevel));
				}
			}
		}
		
		return new Pair<ArrayList<Pair<ContextDeviation,Integer>>, Integer>(ret, maxMatchDepth);
	}
	
	
	@Override
	public Pair<ContextInformation, Double> predict(ContextInformation agreed) {
		//find values in agreed, 
		//see if there are the same types in the data, average the data then set the predicted task
		ContextInformation performed = agreed.copy();
		IDGGeneric.generateContextValues(performed);
		List<ContextInformation> ciWithValues = performed.flatten();
		
		if(ciWithValues.isEmpty())
		{
			System.err.println("Le context, she contain noo value!?!");
		}else{
			for(ContextInformation aprop: ciWithValues)
			{
				for(ContextData cd: aprop.getValues())
				{
					//check all ones plus up 1 or 2
					Pair<ArrayList<Pair<ContextDeviation, Integer>>, Integer> matchingDeviations = getApplicableContexts(aprop, cd);
					
					double mean=0;
					double total = 0;
					
					for(Pair<ContextDeviation, Integer> matchDev: matchingDeviations.getValue0())
					{
						double weight = (matchDev.getValue1()/(1.0*matchingDeviations.getValue1()));
						mean+= (matchDev.getValue0().asDelta())*weight;
						total+=weight;
					}
					if(total>0)
					{
						mean/=total;
					}else
					{
						mean = 0;
					}
					
					ContextDataValue conDV = cd.getValue();
					cd.setValue(ContextDataUtils.addViaDoubleConversion(conDV, mean));
				}
			}
		}
		
		// TODO Auto-generated method stub
		return new Pair<ContextInformation, Double>(performed, 1.0);
	}

}
