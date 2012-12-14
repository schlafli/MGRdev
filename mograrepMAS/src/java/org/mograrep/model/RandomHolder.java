package org.mograrep.model;

import java.util.Random;

public class RandomHolder {
	private static Random rand = new Random();
	
	public static Random getInstance()
	{
		return rand;
	}
}
