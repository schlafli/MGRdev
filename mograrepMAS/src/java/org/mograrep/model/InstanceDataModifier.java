package org.mograrep.model;

import java.util.List;

import org.semanticweb.owlapi.model.IRI;

public interface InstanceDataModifier {
	
	public boolean applyModifier(ContextInformation ci);

	public boolean isApplicable(List<IRI> chain);
	
	public String getModifierName();
	
}
