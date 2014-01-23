package com.cfms.virtualpot.lib.game;


public class GridStack extends ChipStack {

	private int mMaxStackHeight = 10;
	OrderedChipStack[][] mGrid = null;
	private int mRows = 0;
	private int mCols = 0;

	public GridStack(int rows, int cols, int maxHeight) {
		mChips = new int[NUM_COLORS];
		mGrid = new OrderedChipStack[rows][cols];
		mRows = rows;
		mCols = cols;
		setMaxStackHeight(maxHeight);
	}

	public GridStack(int rows, int cols, int maxHeight, int[] chips) {
		this(rows, cols, maxHeight);

		for (int i = 0; i < NUM_COLORS; i++) {
			AddChips(i, chips[i]);
		}
	}

	public int getRowCount() {
		return mRows;
	}

	public int getColCount() {
		return mCols;
	}

	public OrderedChipStack getStack(int r, int c) {
		return mGrid[r][c];
	}

	public int getMaxStackHeight() {
		return mMaxStackHeight;
	}

	public void setMaxStackHeight(int maxHeight) {
		if (maxHeight <= 0)
			throw new IllegalArgumentException("Max Hieght Must be Positive");
		mMaxStackHeight = maxHeight;
	}

	@Override
	public void AddChips(int cidx, int quantity) {
		if (cidx < 0 || cidx >= NUM_COLORS) {
			throw new IllegalArgumentException("Color index out of range");
		}
		if (quantity < 0) {
			throw new IllegalArgumentException("Cannot add negative chips");
		}

		// Find first stack that has a higher denomination chip or is empty
		int r = 0;
		int c = 0;
		boolean done = false;
		while (r < mRows && !done) {
			while (c < mCols && !done) {
				if (hasHigherDenomChipOrEmpty(r, c, cidx)) {
					done = true;
					break;
				}
				c++;
			}
			if (done)
				break;
			r++;
			c = 0;
		}

		// If no space, stack them at the end for now
		// TODO improve this behavior to distribute chips
		if (r == mRows && c == mCols) {
			PlaceChips(mRows - 1, mCols - 1, cidx, quantity);
			return;
		}
		
		// Top off previous stack and make space until chips are filled up
		c--;
		if (c < 0) {
			r--;
			c = mCols - 1;
		}
		if (r < 0) {
			c = 0;
			r = 0;
		}

		// Top off
		int diff;
		if (mGrid[r][c] == null)
			mGrid[r][c] = new OrderedChipStack();
		
		diff = mMaxStackHeight - mGrid[r][c].getCount();
		
		if(diff > 0)
		{
			//If previous stack has some of the same color or is empty
			if((mGrid[r][c].getCount(cidx) > 0 && (r > 0 || c > 0))||(diff == mMaxStackHeight))
			{
				diff = Math.min(diff, quantity);
				this.PlaceChips(r, c, cidx, diff);
				quantity -= diff;
			}
		}
		

		while (quantity > 0) {
			c++;
			if (c == mCols) {
				r++;
				c = 0;
			}
			InsertBlank(r, c);
			diff = Math.min(mMaxStackHeight, quantity);
			this.PlaceChips(r, c, cidx, diff);
			quantity -= diff;
		}
	}

	private boolean hasHigherDenomChipOrEmpty(int r, int c, int cidx) {
		if (mGrid[r][c] == null || mGrid[r][c].getCount() == 0) {
			return true;
		}

		for (int i = cidx + 1; i < NUM_COLORS; i++) {
			if (mGrid[r][c].getCount(i) > 0) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Inserts a null space at the specified grid(r,c) Will shift the entire row
	 * up and wrap around to the next row if no space available.
	 */
	public void InsertBlank(int r, int c) {
		int firstBlankR = r;
		int firstBlankC = c;

		while (firstBlankR < this.mRows) {
			while (firstBlankC < this.mCols) {
				// if empty o
				if (mGrid[firstBlankR][firstBlankC] == null)
					break;
				if (mGrid[firstBlankR][firstBlankC].getCount() == 0) {
					mGrid[firstBlankR][firstBlankC] = null;
					break;
				}

				firstBlankC++;
			}
			firstBlankC = 0;
			firstBlankR++;
		}

		// Shift rows
		int rDest = firstBlankR;
		int cDest = firstBlankC;

		if (rDest == mRows) {
			// no empty slots above space. Stack last two together
			rDest = mRows - 1;
			cDest = mCols - 2;
			MoveStack(rDest, cDest, rDest, cDest + 1);
		}

		// While on rows above insert row
		while (rDest > r) {
			// Shift null space to first in this row
			while (cDest > 0) {
				MoveStack(rDest, cDest - 1, rDest, cDest);
				cDest--;
			}
			// Shift around the previous row
			MoveStack(rDest - 1, mCols - 1, rDest, 0);
			rDest--;
			cDest = mCols - 1;
		}

		// Null space is at the end of the current row
		while (cDest > c) {
			MoveStack(rDest, cDest - 1, rDest, cDest);
			cDest--;
		}
	}

	public void MoveStack(int rFrom, int cFrom, int rTo, int cTo) {
		PlaceStack(rTo, cTo, mGrid[rFrom][cFrom]);
		RemoveStack(rFrom, cFrom);
	}

	public void PlaceStack(int r, int c, OrderedChipStack stack) {
		if (stack == null)
			return;

		for(int i = 0; i<NUM_COLORS;i++)
		{
			mChips[i] += stack.getCount(i);
		}
		
		if (mGrid[r][c] == null) {
			mGrid[r][c] = new OrderedChipStack(stack);
		} else {
			mGrid[r][c].AddToTop(stack);
		}
	}
	
	public void RemoveStack(int r, int c)
	{
		if(mGrid[r][c] == null)
			return;
		
		for(int i = 0; i < NUM_COLORS; i++)
		{
			mChips[i] -= mGrid[r][c].getCount(i);
		}
		mGrid[r][c] = null;
	}

	@Override
	public void RemoveChips(int cidx, int quantity) {
		if (cidx < 0 || cidx >= NUM_COLORS) {
			throw new IllegalArgumentException("Color index out of range");
		}
		if (mChips[cidx] < quantity) {
			throw new IllegalArgumentException("Not enough chips left");
		}
		
		boolean done = false;
		int r = mRows - 1;
		int c = mCols - 1;
		
		while(r >= 0 && !done)
		{
			while(c >= 0 && !done)
			{
				if(mGrid[r][c] != null)
				{
					int diff = Math.min(quantity, getStack(r,c).getCount(cidx));
					quantity -= diff;
					RemoveChips(r, c, cidx, diff);
				}
				if(quantity == 0)
					done = true;
				c--;
			}
			r--;
			c = mCols - 1;
		}
	}
	
	public void RemoveChips(int r, int c, int cidx, int quantity)
	{
		if(mGrid[r][c] == null)
		{
			if(quantity > 0)
				throw new IllegalArgumentException("Removing from an empty stack");
			return;
		}
		
		mChips[cidx] -= quantity;
		mGrid[r][c].RemoveChips(cidx, quantity);
	}
	
	public void PlaceStack(int r, int c, ChipStack stack) {
		if (stack == null)
			return;

		if (mGrid[r][c] == null) {
			mGrid[r][c] = new OrderedChipStack();
		}
		
		for(int i = 0; i<NUM_COLORS;i++)
		{
			mChips[i] += stack.getCount(i);
		}
		
		mGrid[r][c].AddChips(stack);
	}

	public void PlaceChips(int r, int c, Color color, int quantity) {
		PlaceChips(r, c, color.ordinal(), quantity);
	}

	public void PlaceChips(int r, int c, int cidx, int quantity) {
		if (mGrid[r][c] == null) {
			mGrid[r][c] = new OrderedChipStack();
		}
		mChips[cidx] += quantity;
		mGrid[r][c].AddChips(cidx, quantity);
	}

	public void PlaceChip(int r, int c, Color chip) {
		if (mGrid[r][c] == null) {
			mGrid[r][c] = new OrderedChipStack();
		}

		mChips[chip.ordinal()] += 1;
		mGrid[r][c].AddToTop(chip);

	}

}
