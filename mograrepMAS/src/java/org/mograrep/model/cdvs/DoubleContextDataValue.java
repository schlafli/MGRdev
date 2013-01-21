package org.mograrep.model.cdvs;


public class DoubleContextDataValue extends ContextDataValue {
	
	private Double value;
	
	
	public static DoubleContextDataValue fromDouble(double d)
	{
		return new DoubleContextDataValue(d);
	}
	
	public DoubleContextDataValue(double value){
		this.value = value;
		this.dataValueID = ContextDataValue.DOUBLE;
	}


	public Double getValue() {
		return value;
	}


	@Override
	public String toString() {
		return ""+value;
	}
	
	
	public void setValue(double value) {
		this.value = value;
	}

	public double addValue(double value) {
		this.value += value;
		return this.value;
	}


	@Override
	public ContextDataValue deepCopy() {
		return new DoubleContextDataValue(value);
	}


	@Override
	public double asDouble() {
		return value;
	}

	@Override
	public boolean greaterThan(ContextDataValue value) {
		return this.getValue() > value.asDouble();
	}

	@Override
	public boolean lessThan(ContextDataValue value) {
		return this.getValue() < value.asDouble();
	}

	@Override
	public boolean isEqual(ContextDataValue value) {
		double tollerance = 0.001; //the values are considered the same if they are within 0.1%
		
		double val_max = this.getValue() * (1.0+tollerance);
		double val_min = this.getValue() * (1.0-tollerance);
		
		return (value.asDouble()>=val_min && value.asDouble()<=val_max);
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
