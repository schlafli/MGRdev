package org.mograrep.model.evalFunctions;

import java.util.ArrayList;
import org.mograrep.model.ContextDeviation;
import org.mograrep.model.ContextInformation;
import org.mograrep.model.IDMSet;
import org.mograrep.model.idgs.impl.contextmodifiers.LocationModifier;
import org.mograrep.model.idgs.impl.contextmodifiers.SubTypeLocationModifier;
import org.mograrep.model.intEst.tests.EstimatorTester;
import org.mograrep.model.matchEvalFunctions.MatchedEvaluationFunction;
import org.mograrep.model.matchFunctions.ExactMatchFunction;
import org.semanticweb.owlapi.model.IRI;

public class EvaluationFunctionTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ContextInformation agreed = EstimatorTester.generateContext();
		IRI deliveryLocation = IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#DeliveryLocation");
		IRI coordinateX = IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#coordinateX");
		//IRI coordinateY = IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#coordinateY");
		IRI locationIRI = IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#Location");
		
		
		//LocationModifier locationModifier = new LocationModifier(5.0, 0.1, 1.0, 0.1);
		LocationModifier locationModifier = new SubTypeLocationModifier(5.0, 0.1, 1.0, 0.1, deliveryLocation);
		
		ArrayList<IDMSet> mods = new ArrayList<>();

		mods.add(locationModifier);
		ContextInformation performed = EstimatorTester.generatePerformedContext(agreed, mods);
		System.out.println(performed.getFormattedView(0, " "));
		//ContextDeviation dev = ContextDeviation.getDeviationsFromContextInformation(agreed, performed).get(0);
		//System.out.println(dev.toString());
		//TODO: modify the modifier so that the deviation will be testable
		ExactMatchFunction mf = new ExactMatchFunction(coordinateX);
		mf.addParentIRI(locationIRI);
		mf.addParentIRI(deliveryLocation);
		
		MatchedEvaluationFunction mef = new MatchedEvaluationFunction(mf, new InrangeEvaluationFunction());
		for(ContextDeviation dev: ContextDeviation.getDeviationsFromContextInformation(agreed, performed))
		{
			if(mef.isApplicable(dev))
			{
				
			}
			System.out.println("Applicable:"+mef.isApplicable(dev) + " Evaluation:"+mef.getEvaluation(dev));
		}
		
	}

}
