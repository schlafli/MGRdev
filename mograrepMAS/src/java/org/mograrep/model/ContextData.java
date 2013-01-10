package org.mograrep.model;

import org.mograrep.model.cdvs.ContextDataValue;
import org.semanticweb.owlapi.model.IRI;

/**
 * A representation of data for a context
 * 
 * @author schlafli
 *
 */
public class ContextData {

	private IRI dataProperty;
	
	private int dataValueType=0;
	
	private ContextDataValue min = null;
	private ContextDataValue max = null;
	
	private ContextDataValue value = null;
	
	public ContextData(IRI dataPropertyIRI){
		this.dataProperty = dataPropertyIRI;
	}
	
	public IRI getDataProperty() {
		return dataProperty;
	}
	
	public int getDataValueType()
	{
		return dataValueType;
	}
	
	public void setDataProperty(IRI dataProperty) {
		this.dataProperty = dataProperty;
	}
	public ContextDataValue getMin() {
		return min;
	}
	public void setMin(ContextDataValue min) {
		if(dataValueType==0)
		{
			dataValueType = min.getDataValueID();
		}
		this.min = min;
	}
	public ContextDataValue getMax() {
		return max;
	}
	public void setMax(ContextDataValue max) {
		if(dataValueType==0)
		{
			dataValueType = max.getDataValueID();
		}
		this.max = max;
	}
	public ContextDataValue getValue() {
		return value;
	}
	public void setValue(ContextDataValue value) {
		if(dataValueType==0)
		{
			dataValueType = value.getDataValueID();
		}
		this.value = value;
	}
	
	public ContextData deepCopy()
	{
		ContextData cd = new ContextData(dataProperty);
		if(max!=null)
		{
			cd.max = max.deepCopy();
		}
		if(min!=null)
		{
			cd.min = min.deepCopy();
		}
		if(value!=null)
		{
			cd.value = value.deepCopy();
		}
		return cd;
	}
	
	public String toString(){
		String ret = dataProperty.getFragment() + " (";
		if(value!=null){
			ret += value.toString();
		}else{
			ret+= min.toString() +" to " +max.toString();
		}
		return ret+")";
	}
	
	public boolean sameType(ContextData cd)
	{
		return getDataProperty().toString().equals(cd.getDataProperty().toString());
	}
}
