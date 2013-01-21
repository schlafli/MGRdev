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

	@Override
	public boolean greaterThan(ContextDataValue value) {
		return this.asDouble()>value.asDouble();
	}

	@Override
	public boolean lessThan(ContextDataValue value) {
		return this.asDouble()>value.asDouble();
	}

	@Override
	public boolean isEqual(ContextDataValue value) {
		return this.getValue() ==  (int)Math.round(value.asDouble());
	}

	@Override
	public boolean isComparable(ContextDataValue value) {
		if(value.getDataValueID()==ContextDataValue.DOUBLE || value.getDataValueID()==ContextDataValue.INTERGER)
		{
			return true;
		}else{
			return false;
		}
	}

}
