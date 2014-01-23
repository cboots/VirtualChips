package com.cfms.virtualpot.lib.game;

public class Pot extends ChipStack {

	public Pot() {
		super();
	}

	public Pot(int[] chips) {
		super(chips);
	}

	public Pot(ChipStack old) {
		super(old);
	}

	/*
	 * Splits the pot according to the given scheme. chipVals - array of chip
	 * values (see TableStakes) ways - an array of proportional measures of how
	 * to split pot. Example {1, 1} indicates the pot is evenly split into 2 {2,
	 * 1, 1} indicates the pot is to be split three ways, half to the first
	 * player, a quarter to each of the next two players Returns array of
	 * ChipStacks in the same order as as 'ways' with an extra entry for odd change
	 * Game will decide what to do with change
	 */
	public ChipStack[] splitPot(int[] ways, double[] chipVals) {
		ChipStack[] splits = new ChipStack[ways.length+1];

		for (int i = 0; i < ways.length; i++) {
			splits[i] = new ChipStack();
		}

		int totalWays = 0;
		for (int i = 0; i < ways.length; i++) {
			totalWays += ways[i];
		}

		// Split chips evenly as much as possible
		// Go from top denomination and make change
		for (int c = NUM_COLORS - 1; c >= 0; c--) {
			int count = this.getCount(c);
			int split = count / totalWays;
			int remainder = count % totalWays;
			for (int i = 0; i < ways.length; i++) {
				// Transfer the chips from pot to the split
				ChipStack.TransferChips(this, splits[i], c, split * ways[i]);
			}
			
			//If non-zero remainder, Try to make change from lower denominations
			if(remainder > 0)
			{
				if(this.getValue(chipVals) > totalWays * chipVals[c])
				{
					//Enough remaining for even split this round
					for(int i = 0; i < ways.length; i++)
					{
						//Value that must be filled for this split
						double quota = ways[i] * chipVals[c];
						//Subtracting from highest denominations first, fill quota
						for(int change = c; change >= 0; change--)
						{
							if(mChips[change] > 0)
							{
								//Subtract as much as possible or needed
								if(quota >= mChips[change]*chipVals[change])
								{
									//All chips of denomination [change] are needed to fill quota
									quota -= mChips[change]*chipVals[change];
									TransferChips(this, splits[i], change, mChips[change]);
								}else{
									//More than we need. Take only what we must
									int transfer = (int) (quota / chipVals[change]);
									quota -= transfer*chipVals[change];
									TransferChips(this, splits[i], change, transfer);
								}
							}
							//If we've made all the change we need, quit
							if(quota == 0)
								break;
						}
					}
					
				}
				else{
					//If lowest denomination, leave as remainder.
					if(c == 0)
						break;
					
					//Make change from current denomination
					this.makeChange(chipVals, c);
				}	
			}
			
		}

		//Return odds as is in last split
		splits[ways.length] = new ChipStack(this);
		return splits;
	}

}
