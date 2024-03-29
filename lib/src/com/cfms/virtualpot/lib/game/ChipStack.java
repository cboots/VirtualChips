package com.cfms.virtualpot.lib.game;

public class ChipStack {
	public static enum Color {
		WHITE, RED, BLUE, GREEN, BLACK
	};

	public final static int NUM_COLORS = 5;
	protected int[] mChips = null;

	public ChipStack() {
		mChips = new int[NUM_COLORS];
	}

	public ChipStack(int[] chips) {
		if (chips.length != NUM_COLORS) {
			throw new IllegalArgumentException(
					"Incorrect Array Size, must be NUM_COLORS");
		}
		mChips = new int[NUM_COLORS];
		for (int i = 0; i < NUM_COLORS; i++) {
			mChips[i] = chips[i];
		}
	}
	
	public ChipStack(ChipStack old)
	{
		mChips = new int[NUM_COLORS];
		for (int i = 0; i < NUM_COLORS; i++) {
			mChips[i] = old.mChips[i];
		}
	}

	public void AddChips(Color c, int quantity) {
		AddChips(c.ordinal(), quantity);
	}

	public void AddChips(int cidx, int quantity) {
		if (cidx < 0 || cidx >= NUM_COLORS) {
			throw new IllegalArgumentException("Color index out of range");
		}
		if (quantity < 0) {
			throw new IllegalArgumentException("Cannot add negative chips");
		}
		mChips[cidx] += quantity;
	}

	public void AddChips(ChipStack other) {
		for (int i = 0; i < NUM_COLORS; i++) {
			AddChips(i, other.getCount(i));
		}
	}

	public void RemoveChips(Color c, int quantity) {
		RemoveChips(c.ordinal(), quantity);
	}

	public void RemoveChips(int cidx, int quantity) {
		if (cidx < 0 || cidx >= NUM_COLORS) {
			throw new IllegalArgumentException("Color index out of range");
		}
		if (mChips[cidx] < quantity) {
			throw new IllegalArgumentException("Not enough chips left");
		}
		mChips[cidx] -= quantity;
	}

	public void RemoveChips(ChipStack remove) {
		for (int i = 0; i < NUM_COLORS; i++) {
			RemoveChips(i, remove.getCount(i));
		}
	}

	public int getCount(Color c) {
		return getCount(c.ordinal());
	}

	public int getCount(int idx) {
		return mChips[idx];
	}
	
	public ChipStack copy()
	{
		ChipStack copy = new ChipStack(this);
		return copy;
	}
	
	/*
	 * Returns true if stacks have the same quantity of each denomination
	 * Does not check for equivalent value.
	 */
	public boolean stacksMatch(ChipStack other)
	{
		return stacksMatch(other.mChips);
	}
	
	/*
	 * Returns true if stacks have the same quantity of each denomination
	 * Does not check for equivalent value.
	 */
	public boolean stacksMatch(int[] otherChips)
	{
		for(int i = 0; i < NUM_COLORS; i++)
		{
			if(otherChips[i] != mChips[i])
				return false;
		}
		
		return true;
	}
	
	public double getValue(double[] chipVals)
	{
		double value = 0;
		if(chipVals.length != NUM_COLORS)
		{
			throw new IllegalArgumentException("chipVals array the wrong length. Must be NUM_COLORS long.");
		}
		
		for(int i = 0; i < NUM_COLORS; i++)
		{
			value += chipVals[i]*getCount(i);
		}
		
		return value;
	}
	
	public static void TransferChips(ChipStack from, ChipStack to, Color color, int quantity)
	{
		from.RemoveChips(color, quantity);
		to.AddChips(color, quantity);
	}
	
	public static void TransferChips(ChipStack from, ChipStack to, int cidx, int quantity)
	{
		from.RemoveChips(cidx, quantity);
		to.AddChips(cidx, quantity);
	}
	
	public static void TransferChips(ChipStack from, ChipStack to, int[] transfer)
	{
		ChipStack transferStack = new ChipStack(transfer);
		from.RemoveChips(transferStack);
		to.AddChips(transferStack);
	}
	
	public void makeChange(double[] chipVals, Color breakColor)
	{
		makeChange(chipVals, breakColor.ordinal());
	}
	
	public void makeChange(double[] chipVals, int breakCidx)
	{
		makeChange(chipVals, breakCidx, getCount(breakCidx));
	}
	
	public void makeChange(double[] chipVals, Color breakColor, int quantity)
	{
		makeChange(chipVals, breakColor.ordinal(), quantity);
		
	}
	
	public void makeChange(double[] chipVals, int breakCidx, int quantity)
	{
		makeChange(chipVals, breakCidx, quantity, breakCidx - 1);
	}

	public void makeChange(double[] chipVals, Color breakColor, int quantity, Color changeColor)
	{
		makeChange(chipVals, breakColor.ordinal(), quantity, changeColor.ordinal());
	}
	
	public void makeChange(double[] chipVals, int breakCidx, int quantity, int changeCidx)
	{
		this.RemoveChips(breakCidx, quantity);
		double quota = chipVals[breakCidx] * quantity;
		for(int c = changeCidx; c >= 0; c--)
		{
			int change = (int) (quota / chipVals[c]);
			quota -= change*chipVals[c];
			this.AddChips(c, change);
			
			//If we've made all the change we need, quit
			if(quota == 0)
				break;
		}
	}
	
	public int getCount()
	{
		int sum = 0;
		for(int i = 0; i< NUM_COLORS;i++)
		{
			sum += mChips[i];
		}
		return sum;
	}
	
}
