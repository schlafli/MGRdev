package org.mograrep.model;

import java.util.ArrayList;
import java.util.List;

import org.mograrep.model.idgs.MatchedInstanceDataValueModifier;
import org.mograrep.model.matchFunctions.MatchFunction;
import org.semanticweb.owlapi.model.IRI;

public class IDMSet implements InstanceDataModifier{

	//List<InstanceDataModifier> genericModifiers;
	List<MatchedInstanceDataValueModifier> valueModifiers; //current level modifiers
	List<InstanceDataModifier> ciModifiers; //current level modifiers

	String name;
	
	protected MatchFunction mf;
	
	public MatchFunction getMatchFunction()
	{
		return mf;
	}
	
	public IDMSet(String name)
	{
		this.name = name;
		valueModifiers = new ArrayList<>();
		ciModifiers = new ArrayList<>();
	}

	public void setMatchFunction(MatchFunction matchFunc)
	{
		this.mf = matchFunc;
	}
	

	public boolean isApplicable(List<IRI> chain)
	{
		return mf.isApplicable(chain, null);
	}

	public void addValueModifier(MatchedInstanceDataValueModifier idgm)
	{
		valueModifiers.add(idgm);
	}

	public void addCIModifier(InstanceDataModifier idgm)
	{
		ciModifiers.add(idgm);
	}

	@Override
	public boolean applyModifier(ContextInformation ci) {
		if(ci.hasContext())
		{
			for(InstanceDataModifier i:ciModifiers)
			{
				for(ContextInformation ci2:ci.getContextInformationList())
				{
					if(i.isApplicable(ci2.getTypeChain()))
					{
						i.applyModifier(ci2);
					}	
				}
			}
		}
		if(ci.hasValues())
		{
			for(MatchedInstanceDataValueModifier i: valueModifiers)
			{
				for(ContextData cd:ci.getValues())
				{
					if(i.isApplicable(cd))
					{
						i.applyModifier(cd);
					}
				}
			}
		}

		return true;
	}

	@Override
	public String getModifierName() {
		return name;
	}

	public static boolean applyIDMSetToContext(IDMSet idms, ContextInformation ci)
	{
		boolean applied = false;
		if(idms.isApplicable(ci.getTypeChain()))
		{
			idms.applyModifier(ci);
			applied = true;
		}else
		{	
			if(ci.hasContext()){
				for(ContextInformation ciRec: ci.getContextInformationList() )
				{
					applied |= applyIDMSetToContext(idms, ciRec);

				}
			}
		}
		return applied;
	}

}
