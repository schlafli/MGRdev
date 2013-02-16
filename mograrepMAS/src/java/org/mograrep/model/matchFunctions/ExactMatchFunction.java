package org.mograrep.model.matchFunctions;

import java.util.ArrayList;
import java.util.List;


import org.mograrep.utils.IRIMatchHelper;
import org.semanticweb.owlapi.model.IRI;

public class ExactMatchFunction extends MatchFunction {

	private List<IRI> matchChain;
	private List<IRI> matchProperties;

	public ExactMatchFunction()
	{

	}

	public ExactMatchFunction(IRI property)
	{
		addPropertyIRI(property);
	}

	public ExactMatchFunction(List<IRI> parents, List<IRI> properties)
	{
		if(parents!=null){
			for(int i = parents.size()-1;i>=0;i--)
			{
				addParentIRI(parents.get(i));
			}
		}
		if(properties!=null){
			for(IRI prop: properties)
			{
				addPropertyIRI(prop);
			}
		}
		//addPropertyIRI(property);
	}

	public ExactMatchFunction(IRI concept, IRI property)
	{
		addParentIRI(concept);
		addPropertyIRI(property);
	}

	public void addParentIRI(IRI parent)
	{
		if(parent!=null)
		{
			if(matchChain == null)
			{
				matchChain = new ArrayList<>();
			}

			matchChain.add(0, parent);
		}
	}

	public void addPropertyIRI(IRI property)
	{
		if(property != null){
			if(matchProperties==null)
			{
				matchProperties = new ArrayList<>();
			}
			matchProperties.add(property);
		}
	}

	private boolean matchProperty(IRI property)
	{
		if(property!=null && matchProperties!=null && matchProperties.size()!=0){
			return IRIMatchHelper.matchAnyIRIInList(matchProperties, property);
		}else
		{
			return true;
		}
	}

	private boolean matchTypes(List<IRI> sourceChain)
	{
		if(sourceChain!=null && matchChain!=null && matchChain.size()!=0)
		{
			return IRIMatchHelper.matchEndChain(sourceChain, matchChain);
		}else
		{ 
			return true;
		}
	}

	public boolean isApplicable(List<IRI> sourceChain, IRI property){
		return matchTypes(sourceChain) && matchProperty(property);
	}
	
	public String toString()
	{
		String ret = "ExactMatchFunction(<";
		for(IRI parent: matchChain)
		{
			ret+=parent.getFragment()+", ";
		}
		ret = ret.substring(0, ret.length()-2) +">, <";
		for(IRI prop: matchProperties)
		{
			ret+=prop.getFragment()+", ";
		}
		ret = ret.substring(0, ret.length()-2) +">)";
		return ret;
	}
}
