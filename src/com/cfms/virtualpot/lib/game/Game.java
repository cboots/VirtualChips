package com.cfms.virtualpot.lib.game;

import java.util.List;

public class Game {

	TableStakes mStakes = null;
	Pot mMainPot = null;
	List<Pot> mSidePots = null;
	
	
	public Game()
	{
		mStakes = new TableStakes();
	}
	
	
	
}
