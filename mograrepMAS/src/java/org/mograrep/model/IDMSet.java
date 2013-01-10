package org.mograrep.model;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;

public abstract class IDMSet implements InstanceDataModifier{

	//List<InstanceDataModifier> genericModifiers;
	List<InstanceDataValueModifier> valueModifiers; //current level modifiers
	List<InstanceDataModifier> ciModifiers; //current level modifiers

	String name;

	public IDMSet(String name)
	{
		this.name = name;
		valueModifiers = new ArrayList<>();
		ciModifiers = new ArrayList<>();
	}

	//generic parent

	public abstract boolean isApplicable(List<IRI> chain);

	public void addValueModifier(InstanceDataValueModifier idgm)
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
			for(InstanceDataValueModifier i: valueModifiers)
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
