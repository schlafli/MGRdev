package org.mograrep.model.intEst.tests;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.mograrep.kbrep.OWLInstantGenerator;
import org.mograrep.model.ContextInformation;
import org.mograrep.model.IDGGeneric;
import org.mograrep.model.IDMSet;
import org.mograrep.model.InteractionEstimator;
import org.mograrep.model.intEst.SimpleAverageEstimator;
import org.mograrep.model.obsolete.SubTypeLocationModifier;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLFacet;

public class EstimatorTester {


	public static ContextInformation generateContext(){
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory fac = manager.getOWLDataFactory();
		//ActionExpression ae = new ActionExpression("Action_A");

		OWLClass action = fac.getOWLClass(IRI.create("http://www.csd.abdn.ac.uk/~schlafli/TREvCOnt#Action"));
		OWLObjectProperty charachterizedBy = fac.getOWLObjectProperty(IRI.create("http://www.semanticweb.org/ontologies/2012/7/CoOL#characterizedBy"));
		OWLClass deliveryLocation = fac.getOWLClass(IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#DeliveryLocation"));
		OWLClass pickupLocation = fac.getOWLClass(IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#PickupLocation"));
		OWLObjectProperty hasQuality = fac.getOWLObjectProperty(IRI.create("http://www.semanticweb.org/ontologies/2012/7/CoOL#hasQuality"));
		OWLClass location = fac.getOWLClass(IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#Location"));
		OWLDataProperty coordinateX = fac.getOWLDataProperty(IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#coordinateX"));
		OWLDataProperty coordinateY = fac.getOWLDataProperty(IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#coordinateY"));


		//OWLDataSomeValuesFrom valInRange = fac.getOWLDataSomeValuesFrom(coordinateX, fac.getOWLDatatypeMinMaxExclusiveRestriction(0.1, 2.0));
		OWLDatatypeRestriction valRange = fac.getOWLDatatypeRestriction(fac.getDoubleOWLDatatype(), fac.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, 0.1), fac.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, 2.0));


		//		check(action);
		//		check(charachterizedBy);
		//		check(deliveryLocation);
		//		check(hasQuality);
		//		check(location);

		OWLClassExpression locationAndCoordinate = fac.getOWLObjectIntersectionOf(location, fac.getOWLDataSomeValuesFrom(coordinateX, valRange), fac.getOWLDataSomeValuesFrom(coordinateY, valRange));
		OWLClassExpression hasQualitySomeLocation = fac.getOWLObjectSomeValuesFrom(hasQuality, locationAndCoordinate);
		OWLClassExpression deliveryLocationAndHasQualitySomeLocation  = fac.getOWLObjectIntersectionOf(deliveryLocation, hasQualitySomeLocation);
		OWLClassExpression charBySome = fac.getOWLObjectSomeValuesFrom(charachterizedBy, deliveryLocationAndHasQualitySomeLocation);

		OWLClassExpression pickupLAHQSL = fac.getOWLObjectIntersectionOf(pickupLocation, hasQualitySomeLocation);
		OWLClassExpression charBySome2 = fac.getOWLObjectSomeValuesFrom(charachterizedBy, pickupLAHQSL);


		OWLClassExpression all = fac.getOWLObjectIntersectionOf(action, charBySome, charBySome2);


		//Set<OWLClassExpression> aset = all.getNestedClassExpressions();

		//		for(OWLClassExpression e: aset){
		//			System.out.println(e.toString());
		//		}

		//ae.setOWLClassExpression(all);

		ContextInformation ci = OWLInstantGenerator.getContextFromClassExpression(all);
		//ContextInformation agreed = OWLInstantGenerator.getContextFromClassExpression(all);
//		ContextInformation agreed = ci.copy();
//
//
//		IDGGeneric.generateContextValues(ci);
//
//		LocationModifier lm = new LocationModifier(0, 0.5, 0, 0.5);
//		System.out.println(IDMSet.applyIDMSetToContext(lm, ci));
//		System.out.println(ci.getFormattedView(0," "));
//
//
//		System.out.println(agreed.getFormattedView(0," "));
//
//
//		List<ContextDeviation> cds = ContextDeviation.getDeviationsFromContextInformation(agreed, ci);
//
//		for(ContextDeviation cd: cds)
//		{
//			System.out.println(cd.toString());
//		}
//		System.out.println("\n\n");
//		for(ContextInformation c: ci.flatten())
//		{
//			System.out.println(c.toString());	
//		}


		//		AgentRef a = new AgentRef("AgentBob");
		//		System.out.println("registerAction: " +match.registerAction(a, ae));

		//don't return yourself
		//		match.listActions(ae);


		return ci;
	}

	

	public static ContextInformation generatePerformedContext(ContextInformation agreed, IDMSet modifier)
	{
		ArrayList<IDMSet> mods = new ArrayList<>(1);
		mods.add(modifier);
		return generatePerformedContext(agreed, mods);
	}
	
	public static ContextInformation generatePerformedContext(ContextInformation agreed, List<IDMSet> modifiers)
	{
		ContextInformation performed = agreed.copy();
		IDGGeneric.generateContextValues(performed);
		for(IDMSet mod: modifiers)
		{
			IDMSet.applyIDMSetToContext(mod, performed);
		}
		return performed;
	}
	
	
	public static void createAndAddInteraction(InteractionEstimator iest)
	{
		ContextInformation agreed = generateContext();
		IRI deliveryLocation = IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#DeliveryLocation");
		IRI pickupLocation = IRI.create("http://www.semanticweb.org/ontologies/2012/8/domain1#PickupLocation");
		
		SubTypeLocationModifier deliveryLocationModifier = new SubTypeLocationModifier(1.0, 0.1, 1.0, 0.1, deliveryLocation);
		SubTypeLocationModifier pickupLocationModifier = new SubTypeLocationModifier(-1.0, 0.1, -1.0, 0.1, pickupLocation);
		
		ArrayList<IDMSet> mods = new ArrayList<>();
		
		mods.add(pickupLocationModifier);
		mods.add(deliveryLocationModifier);
		
		ContextInformation performed = generatePerformedContext(agreed, mods);
		iest.addToTraining(new Pair<ContextInformation, ContextInformation>(agreed, performed));
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Creating InteractionEstimator");
		InteractionEstimator iest = new SimpleAverageEstimator();
		
		System.out.println("Creating and adding new action/context");
		for(int i=0;i<1000;i++)
		{
			createAndAddInteraction(iest);
			
		}
		
		System.out.println("done!");
		
		ContextInformation prototype = generateContext();
		ContextInformation predicted = iest.predict(prototype).getValue0();
		
		System.out.println(prototype.getFormattedView(0, " "));
		System.out.println(predicted.getFormattedView(0, " "));
		
	}

}
