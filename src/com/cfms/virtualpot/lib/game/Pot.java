package com.cfms.virtualpot.lib.game;

import com.cfms.virtualpot.lib.game.ChipStack.Color;


public class Pot {
	//Pot does not keep track of side pots, game handles that
	ChipStack[] mPlayerPots = null; 
	
	
	public Pot(int numPlayers)
	{
		mPlayerPots = new ChipStack[numPlayers];	
		for(int i = 0; i < numPlayers; i++)
		{
			mPlayerPots[i] = new ChipStack();
		}
	}

	public int getPlayerCount()
	{
		if(mPlayerPots == null)
			return 0;
		
		return mPlayerPots.length;
	}
	
	public int getCount(Color c) {
		int count = 0;
		for(int i = 0; i < this.getPlayerCount(); i++)
		{
			count += mPlayerPots[i].getCount(c);
		}
		
		return count;
	}

	public int getCount(int cidx) {
		int count = 0;
		for(int i = 0; i < this.getPlayerCount(); i++)
		{
			count += mPlayerPots[i].getCount(cidx);
		}
		
		return count;
	}
	
	public int getCount(Color c, int player) {
		return mPlayerPots[player].getCount(c);
	}

	public int getCount(int cidx, int player) {
		return mPlayerPots[player].getCount(cidx);
	}
	
	/*
	 * 
	 */
	public ChipStack getPlayerPot(int player)
	{
		return mPlayerPots[player];
	}
	
	public ChipStack getPlayerPotCopy(int player)
	{
		return new ChipStack(mPlayerPots[player]);
	}
	
	public void addPlayerChips(int player, Color c, int quantity)
	{
		mPlayerPots[player].AddChips(c, quantity);
	}
	
	public void addPlayerChips(int player, int cidx, int quantity)
	{
		mPlayerPots[player].AddChips(cidx, quantity);
	}
	
	public void addPlayerChips(int player, ChipStack stack)
	{
		mPlayerPots[player].AddChips(stack);
	}
	
	public void removePlayerChips(int player, Color c, int quantity)
	{
		mPlayerPots[player].RemoveChips(c, quantity);
	}
	
	public void removePlayerChips(int player, int cidx, int quantity)
	{
		mPlayerPots[player].RemoveChips(cidx, quantity);
	}
	
	public void removePlayerChips(int player, ChipStack stacks)
	{
		mPlayerPots[player].RemoveChips(stacks);
	}
}
