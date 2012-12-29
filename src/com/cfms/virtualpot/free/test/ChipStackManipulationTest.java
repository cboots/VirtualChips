package com.cfms.virtualpot.free.test;

import junit.framework.TestCase;

import com.cfms.virtualpot.lib.game.ChipStack;
import com.cfms.virtualpot.lib.game.ChipStack.Color;

public class ChipStackManipulationTest extends TestCase {

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

    public void testAddStack()
    {
    	ChipStack stack = new ChipStack(new int[]{50, 5, 10, 15, 20});
    	ChipStack stack2 = new ChipStack(new int[]{5, 10, 15, 25, 0});
    	stack.AddChips(stack2);
    	assertEquals(55, stack.getCount(0));
    	assertEquals(15, stack.getCount(1));
    	assertEquals(25, stack.getCount(2));
    	assertEquals(40, stack.getCount(3));
    	assertEquals(20, stack.getCount(4));
    }
    

    public void testRemoveStack()
    {
    	ChipStack stack = new ChipStack(new int[]{50, 25, 20, 25, 20});
    	ChipStack stack2 = new ChipStack(new int[]{5, 10, 15, 25, 0});
    	stack.RemoveChips(stack2);
    	assertEquals(45, stack.getCount(0));
    	assertEquals(15, stack.getCount(1));
    	assertEquals( 5, stack.getCount(2));
    	assertEquals( 0, stack.getCount(3));
    	assertEquals(20, stack.getCount(4));
    }
    
    public void testRemoveTooMany()
    {
    	ChipStack stack = new ChipStack();
    	try{
    		stack.RemoveChips(0, 10);
    		fail("Exception should have been thrown.");
    	}catch(IllegalArgumentException ex)
    	{
    		
    	}catch(Exception e)
    	{
    		fail("Unknown Exception Thrown");
    	}
    }
    

    public void testIndexOutOfRangeException()
    {
    	ChipStack stack = new ChipStack();
    	try{
    		stack.RemoveChips(-1, 0);
    		fail("Exception should have been thrown.");
    	}catch(IllegalArgumentException ex)
    	{
    		
    	}catch(Exception e)
    	{
    		fail("Unknown Exception Thrown");
    	}
    	
    	try{
    		stack.RemoveChips(ChipStack.NUM_COLORS, 0);
    		fail("Exception should have been thrown.");
    	}catch(IllegalArgumentException ex)
    	{
    		
    	}catch(Exception e)
    	{
    		fail("Unknown Exception Thrown");
    	}
    	
    	try{
    		stack.AddChips(-1, 0);
    		fail("Exception should have been thrown.");
    	}catch(IllegalArgumentException ex)
    	{
    		
    	}catch(Exception e)
    	{
    		fail("Unknown Exception Thrown");
    	}
    	
    	try{
    		stack.AddChips(ChipStack.NUM_COLORS, 0);
    		fail("Exception should have been thrown.");
    	}catch(IllegalArgumentException ex)
    	{
    		
    	}catch(Exception e)
    	{
    		fail("Unknown Exception Thrown");
    	}
    }
    
    public void testCopyStack()
    {
    	ChipStack org = new ChipStack(new int[]{5, 10, 15, 20, 25});
    	ChipStack copy = new ChipStack(org);
    	
    	//Test copy
    	for(int i = 0; i < ChipStack.NUM_COLORS; i++)
    	{
    		assertEquals(org.getCount(i), copy.getCount(i));
    	}
    	
    	//Test deep copy
    	assertNotSame(org, copy);
    	copy.AddChips(0, 10);
    	assertEquals(15, copy.getCount(0));
    	assertFalse(15 == org.getCount(0));
    }
    
    public void testCopyStackMethod()
    {
    	ChipStack org = new ChipStack(new int[]{5, 10, 15, 20, 25});
    	ChipStack copy = org.copy();;
    	
    	//Test copy
    	for(int i = 0; i < ChipStack.NUM_COLORS; i++)
    	{
    		assertEquals(org.getCount(i), copy.getCount(i));
    	}
    	
    	//Test deep copy
    	assertNotSame(org, copy);
    	copy.AddChips(0, 10);
    	assertEquals(15, copy.getCount(0));
    	assertFalse(15 == org.getCount(0));
    }
    
    public void testStacksEqual()
    {
    	ChipStack stack1 = new ChipStack(new int[]{10, 15, 25, 30, 1});
    	ChipStack stack2 = new ChipStack(stack1);
    	ChipStack stack3 = new ChipStack(new int[]{10, 10, 25, 30, 1});
    	
    	assertTrue(stack1.stacksMatch(stack2));
    	assertFalse(stack2.stacksMatch(stack3));
    	
    }
    
    public void testTransferStacks()
    {
    	ChipStack stack = new ChipStack(new int[]{50, 25, 20, 25, 20});
    	ChipStack stack2 = new ChipStack(new int[]{5, 10, 15, 25, 0});
    	
    	ChipStack.TransferChips(stack, stack2, 0, 40);
    	ChipStack.TransferChips(stack, stack2, Color.RED, 15);
    	ChipStack.TransferChips(stack, stack2, new int[]{0, 0, 20, 25, 20});
    	
    	
    	assertEquals(10, stack.getCount(0));
    	assertEquals(10, stack.getCount(1));
    	assertEquals( 0, stack.getCount(2));
    	assertEquals( 0, stack.getCount(3));
    	assertEquals( 0, stack.getCount(4));
    	
    	assertEquals(45, stack2.getCount(0));
    	assertEquals(25, stack2.getCount(1));
    	assertEquals(35, stack2.getCount(2));
    	assertEquals(50, stack2.getCount(3));
    	assertEquals(20, stack2.getCount(4));
    }
    
    public void testGetValue()
    {
    	ChipStack stack = new ChipStack(new int[]{50, 10, 2, 2, 4});
    	
    	double value = stack.getValue(new double[]{1, 5, 10, 25, 100});
    	assertEquals(570.0, value);
    	
    }
    
}
