package com.cfms.virtualpot.lib.game;


public class TableStakes {
	
	private double[] mChipValues = new double[ChipStack.NUM_COLORS];
	
	public TableStakes()
	{
		mChipValues[ChipStack.Color.WHITE.ordinal()] = 1.0;
		mChipValues[ChipStack.Color.RED.ordinal()]   = 5.0;
		mChipValues[ChipStack.Color.BLUE.ordinal()]  = 10.0;
		mChipValues[ChipStack.Color.GREEN.ordinal()] = 25.0;
		mChipValues[ChipStack.Color.BLACK.ordinal()] = 100.0;
		
	}
	
	public double[] getChipValues()
	{
		return mChipValues;
	}
	
}
