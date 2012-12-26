package com.cfms.virtualpot.free.test;

import android.test.ActivityInstrumentationTestCase2;

import com.cfms.virtualpot.lib.activity.*;
import com.cfms.virtualpot.lib.game.*;
import com.cfms.virtualpot.lib.game.ChipStack.Color;

public class PotManipulationTest extends
		ActivityInstrumentationTestCase2<PokerActivity> {

	PokerActivity mActivity;
	
	public PotManipulationTest()
	{
		super(PokerActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
        mActivity = this.getActivity();
        
    }
    
    public void testCreateEmptyPot()
    {
    	Pot pot = new Pot(4);
    	assertEquals(4, pot.getPlayerCount());
    	for(int i = 0; i < ChipStack.NUM_COLORS; i++)
    	{
    		assertEquals(0, pot.getCount(i));
    		assertEquals(0, pot.getCount(Color.values()[i]));
    	}
    }
    
    public void testAddPlayerChips()
    {
    	Pot pot = new Pot(4);
    	pot.addPlayerChips(0, Color.RED, 15);
    	assertEquals(15, pot.getCount(Color.RED));
    	assertEquals(15, pot.getCount(Color.RED, 0));
    	assertEquals(0, pot.getCount(Color.RED, 1));
    	assertEquals(0, pot.getCount(Color.RED, 2));
    	assertEquals(0, pot.getCount(Color.RED, 3));

    	pot.addPlayerChips(1, 0, 10);
    	pot.addPlayerChips(2, 0,  5);
    	assertEquals(15, pot.getCount(0));
    	assertEquals( 0, pot.getCount(0, 0));
    	assertEquals(10, pot.getCount(0, 1));
    	assertEquals( 5, pot.getCount(0, 2));
    	assertEquals( 0, pot.getCount(0, 3));
    	
    	ChipStack stack = new ChipStack(new int[]{20, 10, 5, 5, 5});
    	pot.addPlayerChips(3, stack);
    	assertTrue(stack.stacksMatch(pot.getPlayerPot(3)));
    }
    
    public void testRemovePlayerChips()
    {
    	Pot pot = new Pot(4);
    	pot.addPlayerChips(0, new ChipStack(new int[]{10, 15, 20, 0, 1}));
    	
    	pot.removePlayerChips(0, 0, 8);
    	assertEquals(2, pot.getCount(0, 0));
    	//Left with 2, 15, 20, 0, 1

    	pot.removePlayerChips(0, Color.RED, 7);
    	assertEquals(8, pot.getCount(Color.RED, 0));
    	//Left with 2, 8, 20, 0, 1
    	
    	pot.removePlayerChips(0, new ChipStack(new int[]{2, 8, 20, 0, 1}));
    	assertTrue((new ChipStack()).stacksMatch(pot.getPlayerPot(0)));
    }
    
    public void testGetPlayerStack()
    {
    	Pot pot = new Pot(2);
    	pot.addPlayerChips(0, Color.WHITE, 10);
    	pot.addPlayerChips(0, Color.RED, 15);
    	pot.addPlayerChips(1, Color.WHITE, 25);
    	pot.addPlayerChips(1, Color.RED, 12);
    	ChipStack stack0 = pot.getPlayerPot(0);
    	ChipStack stack1 = pot.getPlayerPot(1);
    	
    	assertTrue(new ChipStack(new int[]{10, 15, 0, 0, 0}).stacksMatch(stack0));
    	assertTrue(new ChipStack(new int[]{25, 12, 0, 0, 0}).stacksMatch(stack1));
    	
    	//Test for shallow copy
    	assertSame(stack0, pot.getPlayerPot(0));
    	stack0.AddChips(2, 10);
    	assertEquals(10, pot.getPlayerPot(0).getCount(2));
    }
    
    public void testGetPlayerStackCopy()
    {
    	Pot pot = new Pot(2);
    	pot.addPlayerChips(0, Color.WHITE, 10);
    	pot.addPlayerChips(0, Color.RED, 15);
    	pot.addPlayerChips(1, Color.WHITE, 25);
    	pot.addPlayerChips(1, Color.RED, 12);
    	ChipStack stack0 = pot.getPlayerPotCopy(0);
    	ChipStack stack1 = pot.getPlayerPotCopy(1);
    	
    	assertTrue(new ChipStack(new int[]{10, 15, 0, 0, 0}).stacksMatch(stack0));
    	assertTrue(new ChipStack(new int[]{25, 12, 0, 0, 0}).stacksMatch(stack1));
    	
    	//Test for shallow copy
    	assertNotSame(stack0, pot.getPlayerPotCopy(0));
    	stack0.AddChips(2, 10);
    	assertFalse(10 == pot.getPlayerPotCopy(0).getCount(2));
    }
    
    
}
