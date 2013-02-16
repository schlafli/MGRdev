package org.mograrep.model.idgs;

import org.mograrep.model.ContextData;
import org.mograrep.model.InstanceDataValueModifier;
import org.mograrep.model.matchFunctions.MatchFunction;

public class MatchedInstanceDataValueModifier {

	InstanceDataValueModifier idvm;
	MatchFunction mf;
	String name;
	
	public MatchedInstanceDataValueModifier(InstanceDataValueModifier idvm, MatchFunction mf, String name)
	{
		this.idvm = idvm;
		this.mf = mf;
		this.name = "midvm__"+name +"__"+idvm.getModifierName();
	}
	
	public MatchedInstanceDataValueModifier(InstanceDataValueModifier idvm, MatchFunction mf)
	{
		this(idvm, mf, "blank");
	}
	
	
	public boolean applyModifier(ContextData cd)
	{
		return idvm.applyModifier(cd);
	}
	
	public String getModifierName()
	{
		return name;
	}
	
	public boolean isApplicable(ContextData cd){
		return mf.isApplicable(null, cd.getDataProperty());
	}
	
}
