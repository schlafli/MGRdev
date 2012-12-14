package org.mograrep.model;


public interface InstanceDataValueModifier {

	public boolean applyModifier(ContextData cd);

	public boolean isApplicable(ContextData cd);
	
	public String getModifierName();
}
