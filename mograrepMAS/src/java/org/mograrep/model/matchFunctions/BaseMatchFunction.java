package org.mograrep.model.matchFunctions;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;

public class BaseMatchFunction extends MatchFunction {


	private List<IRI> matchChain;
	private List<IRI> matchProperties;


	public BaseMatchFunction()
	{

	}

	public BaseMatchFunction(IRI baseIRI, IRI propertyIRI)
	{
		this.addParentIRI(baseIRI);
		this.addPropertyIRI(propertyIRI);
	}


	public void addParentIRI(IRI parent)
	{
		if(parent!=null){
			if(matchChain == null)
			{
				matchChain = new ArrayList<>();
			}
			matchChain.add(0, parent);
		}
	}

	public void addPropertyIRI(IRI property)
	{
		if(property!=null){
			if(matchProperties==null)
			{
				matchProperties = new ArrayList<>();
			}
			matchProperties.add(property);
		}
	}


	@Override
	public boolean isApplicable(List<IRI> sourceChain, IRI property) {
		// TODO Auto-generated method stub
		return false;
	}

}
