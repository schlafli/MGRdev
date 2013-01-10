package org.mograrep.model.cdvs;

public class IntegerContextDataValue extends ContextDataValue {
	int value;
	
	
	public static IntegerContextDataValue fromDouble(double d)
	{
		return new IntegerContextDataValue((int)Math.round(d));
	}
	
	public IntegerContextDataValue(int value)
	{
		this.value = value;
		this.dataValueID = ContextDataValue.INTERGER;
	}
	
	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public String toString() {
		return ""+value;
	}


	public void setValue(int value) {
		this.value = value;
	}

	public int addValue(int value) {
		this.value += value;
		return this.value;
	}

	@Override
	public ContextDataValue deepCopy() {
		return new IntegerContextDataValue(value);
	}

	@Override
	public double asDouble() {
		return value;
	}

}
