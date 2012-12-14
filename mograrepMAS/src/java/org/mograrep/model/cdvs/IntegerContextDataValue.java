package org.mograrep.model.cdvs;

public class IntegerContextDataValue extends ContextDataValue {
	int value;
	
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

}
