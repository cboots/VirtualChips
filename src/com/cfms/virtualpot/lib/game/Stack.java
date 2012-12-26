package com.cfms.virtualpot.lib.game;

public class Stack {
	public static enum Color {
		WHITE, RED, BLUE, GREEN, BLACK
	};

	public final static int NUM_COLORS = 5;
	int[] mChips = null;

	public Stack() {
		mChips = new int[NUM_COLORS];
	}

	public Stack(int[] chips) {
		if (chips.length != NUM_COLORS) {
			throw new IllegalArgumentException(
					"Incorrect Array Size, must be NUM_COLORS");
		}
		mChips = new int[NUM_COLORS];
		for (int i = 0; i < NUM_COLORS; i++) {
			mChips[i] = chips[i];
		}
	}

	public void AddChips(Color c, int quantity) {
		AddChips(c.ordinal(), quantity);
	}

	public void AddChips(int cidx, int quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException("Cannot add negative chips");
		}
		if (cidx < 0 || cidx >= NUM_COLORS) {
			throw new IllegalArgumentException("Color index out of range");
		}
		mChips[cidx] += quantity;
	}

	public void AddChips(Stack other) {
		for (int i = 0; i < NUM_COLORS; i++) {
			AddChips(i, other.getCount(i));
		}
	}

	public void RemoveChips(Color c, int quantity) {
		RemoveChips(c.ordinal(), quantity);
	}

	public void RemoveChips(int cidx, int quantity) {
		if (mChips[cidx] < quantity) {
			throw new IllegalArgumentException("Not enough chips left");
		}
		if (cidx < 0 || cidx >= NUM_COLORS) {
			throw new IllegalArgumentException("Color index out of range");
		}
		mChips[cidx] -= quantity;
	}

	public void RemoveChips(Stack remove) {
		for (int i = 0; i < NUM_COLORS; i++) {
			RemoveChips(i, remove.getCount(i));
		}
	}

	public int getCount(Color c) {
		return mChips[c.ordinal()];
	}

	public int getCount(int idx) {
		return mChips[idx];
	}
}
