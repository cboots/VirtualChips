package com.cfms.virtualpot.lib.game;

import java.util.LinkedList;
import java.util.ListIterator;

public class OrderedChipStack extends ChipStack {

	protected LinkedList<Color> mOrder = null;

	public OrderedChipStack() {
		mChips = new int[NUM_COLORS];
		generateDefaultOrder();
	}

	public OrderedChipStack(int[] chips) {
		if (chips.length != NUM_COLORS) {
			throw new IllegalArgumentException(
					"Incorrect Array Size, must be NUM_COLORS");
		}
		mChips = new int[NUM_COLORS];
		for (int i = 0; i < NUM_COLORS; i++) {
			mChips[i] = chips[i];
		}

		generateDefaultOrder();
	}

	public OrderedChipStack(ChipStack old) {
		mChips = new int[NUM_COLORS];
		for (int i = 0; i < NUM_COLORS; i++) {
			mChips[i] = old.mChips[i];
		}
		generateDefaultOrder();
	}

	private void generateDefaultOrder() {
		mOrder = new LinkedList<Color>();
		for (int c = 0; c < NUM_COLORS; c++) {
			Color col = Color.values()[c];
			for (int i = 0; i < mChips[c]; i++) {
				mOrder.addFirst(col);
			}
		}
	}

	public OrderedChipStack(OrderedChipStack old) {
		mChips = new int[NUM_COLORS];
		for (int i = 0; i < NUM_COLORS; i++) {
			mChips[i] = old.mChips[i];
		}
		
		mOrder = new LinkedList<Color>();
		mOrder.addAll(old.mOrder);
		
	}
	
	public Color[] getOrder()
	{
		return mOrder.toArray(new Color[mOrder.size()]);
	}
	
	public OrderedChipStack copy()
	{
		return new OrderedChipStack(this);
	}
	
	public void AddToTop(Color color)
	{
		this.AddChips(color, 1);
	}
	
	public void AddToTop(OrderedChipStack stack)
	{
		for(int i = 0; i < NUM_COLORS; i++)
		{
			mChips[i] += stack.mChips[i];
		}
		
		mOrder.addAll(stack.mOrder);
	}
	
	@Override
	public void AddChips(int cidx, int quantity) {
		if (cidx < 0 || cidx >= NUM_COLORS) {
			throw new IllegalArgumentException("Color index out of range");
		}
		if (quantity < 0) {
			throw new IllegalArgumentException("Cannot add negative chips");
		}
		mChips[cidx] += quantity;
		
		for(int i = 0; i < quantity; i++)
		{
			mOrder.addLast(Color.values()[cidx]);
		}
	}
	
	@Override
	public void AddChips(ChipStack other) {
		for (int i = 0; i < NUM_COLORS; i++) {
			AddChips(i, other.getCount(i));
		}
		
		for (int c = NUM_COLORS - 1; c >= 0; c--) {
			Color col = Color.values()[c];
			for (int i = 0; i < other.mChips[c]; i++) {
				mOrder.addLast(col);
			}
		}
	}
	
	@Override
	public void RemoveChips(int cidx, int quantity) {
		if (cidx < 0 || cidx >= NUM_COLORS) {
			throw new IllegalArgumentException("Color index out of range");
		}
		if (mChips[cidx] < quantity) {
			throw new IllegalArgumentException("Not enough chips left");
		}
		mChips[cidx] -= quantity;
		
		ListIterator<Color> e = mOrder.listIterator();
		
		while(e.hasNext() && quantity > 0)
		{
			Color col = e.next();
			if(col.ordinal() == cidx)
			{
				e.remove();
				quantity--;
			}
		}
	}
	
	public Color RemoveTop()
	{
		Color top = mOrder.getLast();
		mOrder.removeLast();
		mChips[top.ordinal()] -= 1;
		return top;
	}
}
