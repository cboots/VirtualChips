package com.cfms.virtualpot.free.test;

import android.test.ActivityInstrumentationTestCase2;

import com.cfms.virtualpot.lib.activity.*;
import com.cfms.virtualpot.lib.game.*;
import com.cfms.virtualpot.lib.game.ChipStack.Color;

public class ChipStackManipulationTest extends
		ActivityInstrumentationTestCase2<PokerActivity> {

	PokerActivity mActivity;
	
	public ChipStackManipulationTest()
	{
		super(PokerActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
        mActivity = this.getActivity();
        
    }
    
    public void testCreateEmptyStack()
    {
    	ChipStack stack = new ChipStack();
    	for(int i = 0; i < ChipStack.NUM_COLORS; i++)
    	{
    		assertEquals(0, stack.getCount(i));
    		assertEquals(0, stack.getCount(ChipStack.Color.values()[i]));
    	}
    }
    
    public void testCreateFullStack()
    {
    	ChipStack stack = new ChipStack(new int[]{10, 0, 20, 25, 30});
    	assertEquals(10, stack.getCount(ChipStack.Color.WHITE));
    	assertEquals( 0, stack.getCount(ChipStack.Color.RED));
    	assertEquals(20, stack.getCount(ChipStack.Color.BLUE));
    	assertEquals(25, stack.getCount(ChipStack.Color.GREEN));
    	assertEquals(30, stack.getCount(ChipStack.Color.BLACK));
    }
    
    public void testAddChips()
    {
    	ChipStack stack = new ChipStack();
    	for(int i = 0; i < ChipStack.NUM_COLORS; i++)
    	{
    		assertEquals(0, stack.getCount(i));
    		stack.AddChips(i, 10);
    		assertEquals(10, stack.getCount(i));
    	}
    }

    public void testAddChipsByColor()
    {
    	ChipStack stack = new ChipStack(new int[]{5, 5, 0, 0, 10});
    	assertEquals(5, stack.getCount(ChipStack.Color.RED));
    	stack.AddChips(ChipStack.Color.RED, 15);
    	assertEquals(20, stack.getCount(ChipStack.Color.RED));    	
    }
    
    public void testRemoveChips()
    {
    	int[] init = new int[]{50, 25, 20, 50, 10};
    	ChipStack stack = new ChipStack(init);
    	for(int i = 0; i < ChipStack.NUM_COLORS; i++)
    	{
    		assertEquals(init[i], stack.getCount(i));
    		stack.RemoveChips(i, 10);
    		assertEquals(init[i] - 10, stack.getCount(i));
    	}
    }
    
    public void testRemoveChipsByColor()
    {
    	ChipStack stack = new ChipStack(new int[]{50, 25, 20, 50, 10});
    	assertEquals(10, stack.getCount(Color.BLACK));
    	stack.RemoveChips(Color.BLACK, 5);
    	assertEquals(5, stack.getCount(Color.BLACK));	
    }

}
