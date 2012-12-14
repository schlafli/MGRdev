package org.mograrep.model.cdvs;


public class DoubleContextDataValue extends ContextDataValue {
	
	private Double value;
	
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
	

}
