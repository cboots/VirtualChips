package com.cfms.virtualpot.free.test;

import junit.framework.TestCase;

import com.cfms.virtualpot.lib.game.ChipStack;
import com.cfms.virtualpot.lib.game.Pot;
import com.cfms.virtualpot.lib.game.TableStakes;

public class PotManipulationTest extends TestCase {

    public void testSplitPotOneWay()
    {
    	Pot pot = new Pot(new int[]{50, 10, 2, 2, 4});
    	ChipStack potCopy = new ChipStack(pot);
    	TableStakes stakes = new TableStakes();
    	
    	ChipStack[] fullPot = pot.splitPot(new int[]{1}, stakes.getChipValues());
    	
    	assertEquals(2, fullPot.length);
    	assertTrue(potCopy.stacksMatch(fullPot[0]));
    	assertTrue(fullPot[1].stacksMatch(new ChipStack()));
    	
    	
    }

    
    public void testSplitPotTwoWays()
    {
    	Pot pot = new Pot(new int[]{50, 10, 2, 2, 4});
    	ChipStack halfPot = new ChipStack(new int[]{25, 5, 1, 1, 2});
    	
    	TableStakes stakes = new TableStakes();
    	
    	ChipStack[] splits = pot.splitPot(new int[]{1, 1}, stakes.getChipValues());
    	
    	assertEquals(3, splits.length);
    	assertTrue(halfPot.stacksMatch(splits[0]));
    	assertTrue(halfPot.stacksMatch(splits[1]));
    	assertTrue(splits[2].stacksMatch(new ChipStack()));
    	
    }
    
    

    public void testSplitPotTwoWaysMismatched()
    {
    	Pot pot = new Pot(new int[]{9, 1, 1, 0, 0});
    	ChipStack halfPot0 = new ChipStack(new int[]{2, 0, 1, 0, 0});
    	ChipStack halfPot1 = new ChipStack(new int[]{7, 1, 0, 0, 0});
    	
    	TableStakes stakes = new TableStakes();
    	
    	ChipStack[] splits = pot.splitPot(new int[]{1, 1}, stakes.getChipValues());
    	
    	assertEquals(3, splits.length);
    	assertTrue(halfPot0.stacksMatch(splits[0]));
    	assertTrue(halfPot1.stacksMatch(splits[1]));
    	assertTrue(splits[2].stacksMatch(new int[]{0, 0, 0, 0, 0}));
    	
    }

    public void testSplitPotTwoWaysOdd()
    {
    	Pot pot = new Pot(new int[]{5, 10, 2, 2, 4});
    	ChipStack halfPot = new ChipStack(new int[]{2, 5, 1, 1, 2});
    	
    	TableStakes stakes = new TableStakes();
    	
    	ChipStack[] splits = pot.splitPot(new int[]{1, 1}, stakes.getChipValues());
    	
    	assertEquals(3, splits.length);
    	assertTrue(halfPot.stacksMatch(splits[0]));
    	assertTrue(halfPot.stacksMatch(splits[1]));
    	assertTrue(splits[2].stacksMatch(new int[]{1, 0, 0, 0, 0}));
    	
    }
    
    public void testSplitPotThreeWays()
    {
    	Pot pot = new Pot(new int[]{6, 14, 3, 3, 6});
    	ChipStack halfPot0 = new ChipStack(new int[]{0, 5, 1, 1, 2});
    	ChipStack halfPot1 = new ChipStack(new int[]{0, 5, 1, 1, 2});
    	ChipStack halfPot2 = new ChipStack(new int[]{5, 4, 1, 1, 2});
    	
    	TableStakes stakes = new TableStakes();
    	
    	ChipStack[] splits = pot.splitPot(new int[]{1, 1, 1}, stakes.getChipValues());
    	
    	assertEquals(4, splits.length);
    	assertTrue(halfPot0.stacksMatch(splits[0]));
    	assertTrue(halfPot1.stacksMatch(splits[1]));
    	assertTrue(halfPot2.stacksMatch(splits[2]));
    	assertTrue(splits[3].stacksMatch(new int[]{1, 0, 0, 0, 0}));	
    }
    
    public void testSplitPotThreeWaysWeighted()
    {
    	Pot pot = new Pot(new int[]{50, 10, 4, 4, 8});
    	ChipStack halfPot0 = new ChipStack(new int[]{20, 6, 2, 2, 4});
    	ChipStack halfPot1 = new ChipStack(new int[]{15, 2, 1, 1, 2});
    	ChipStack halfPot2 = new ChipStack(new int[]{15, 2, 1, 1, 2});
    	
    	TableStakes stakes = new TableStakes();
    	
    	ChipStack[] splits = pot.splitPot(new int[]{2, 1, 1}, stakes.getChipValues());
    	
    	assertEquals(4, splits.length);
    	assertTrue(halfPot0.stacksMatch(splits[0]));
    	assertTrue(halfPot1.stacksMatch(splits[1]));
    	assertTrue(halfPot2.stacksMatch(splits[2]));
    	assertTrue(splits[3].stacksMatch(new int[]{0, 0, 0, 0, 0}));	
    }
    
    
    
}
