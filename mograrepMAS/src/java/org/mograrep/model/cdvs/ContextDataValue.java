package org.mograrep.model.cdvs;

public abstract class ContextDataValue {
	public static final int DOUBLE = 1;
	public static final int INTERGER = 2;
	
	
	protected int dataValueID;
	
	public abstract Object getValue();
	
	public int getDataValueID(){
		return dataValueID;
	}
	
	public abstract boolean greaterThan(ContextDataValue value);
	public abstract boolean lessThan(ContextDataValue value);
	public abstract boolean isEqual(ContextDataValue value);
	public abstract boolean isComparable(ContextDataValue value);
	
	public abstract double asDouble();
	
	public abstract ContextDataValue deepCopy();
	
	public abstract String toString();
}
