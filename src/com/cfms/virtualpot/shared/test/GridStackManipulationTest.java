package com.cfms.virtualpot.shared.test;

import junit.framework.TestCase;

import com.cfms.virtualpot.lib.game.ChipStack;
import com.cfms.virtualpot.lib.game.ChipStack.Color;
import com.cfms.virtualpot.lib.game.GridStack;
import com.cfms.virtualpot.lib.game.OrderedChipStack;

public class GridStackManipulationTest extends TestCase {

	public void testCreateEmptyGrid() {
		GridStack grid = new GridStack(5, 3, 10);
		assertEquals(5, grid.getRowCount());
		assertEquals(3, grid.getColCount());
		assertEquals(10, grid.getMaxStackHeight());
	}

	public void testCreateSimple() {
		GridStack grid = new GridStack(5, 3, 10, new int[] { 15, 5, 0, 0, 0 });

		// Test agragate counts
		assertEquals(15, grid.getCount(Color.WHITE));
		assertEquals(5, grid.getCount(Color.RED));

		// Test stacking order
		OrderedChipStack stack1 = grid.getStack(0, 0);
		OrderedChipStack stack2 = grid.getStack(0, 1);
		OrderedChipStack stack3 = grid.getStack(0, 2);

		assertEquals(10, stack1.getCount(Color.WHITE));
		assertEquals(5, stack2.getCount(Color.WHITE));
		assertEquals(5, stack3.getCount(Color.RED));

	}

	public void testMoveStack() {
		GridStack grid = new GridStack(2, 5, 10);
		grid.PlaceStack(0, 0, new ChipStack(new int[] { 5, 0, 0, 0, 0 }));
		grid.PlaceChips(0, 1, Color.RED, 2);
		grid.PlaceStack(0, 2, new OrderedChipStack(new int[] { 0, 0, 5, 1, 0 }));
		grid.PlaceStack(0, 3, new ChipStack(new int[] { 0, 0, 0, 0, 1 }));

		// Test move to empty space
		grid.MoveStack(0, 0, 1, 0);
		assertNull(grid.getStack(0, 0));
		OrderedChipStack stack1 = grid.getStack(1, 0);
		assertNotNull(stack1);
		assertEquals(5, stack1.getCount(Color.WHITE));

		// Test move to full space
		grid.MoveStack(0, 3, 0, 2);
		assertNull(grid.getStack(0, 3));
		stack1 = grid.getStack(0, 2);
		assertNotNull(stack1);
		assertEquals(5, stack1.getCount(Color.BLUE));
		assertEquals(1, stack1.getCount(Color.GREEN));
		assertEquals(1, stack1.getCount(Color.BLACK));
		Color[] order = stack1.getOrder();
		assertEquals(Color.GREEN, order[0]);
		assertEquals(Color.BLUE, order[1]);
		assertEquals(Color.BLUE, order[2]);
		assertEquals(Color.BLUE, order[3]);
		assertEquals(Color.BLUE, order[4]);
		assertEquals(Color.BLUE, order[5]);
		assertEquals(Color.BLACK, order[6]);

		// Test move empty space
		grid.MoveStack(1, 1, 0, 1);
		stack1 = grid.getStack(0, 1);
		assertNotNull(stack1);
		assertEquals(2, stack1.getCount(Color.RED));
	}

	public void testPlaceStack() {
		GridStack grid = new GridStack(2, 5, 10);
		grid.PlaceStack(0, 0, new ChipStack(new int[] { 5, 0, 0, 0, 0 }));
		grid.PlaceChips(0, 1, Color.RED, 2);

		// Test place to empty space
		grid.PlaceChip(1, 0, Color.WHITE);
		OrderedChipStack stack1 = grid.getStack(1, 0);
		assertNotNull(stack1);
		assertEquals(1, stack1.getCount(Color.WHITE));

		// Test place to full space
		grid.PlaceChip(0, 0, Color.WHITE);
		stack1 = grid.getStack(0, 0);
		assertNotNull(stack1);
		assertEquals(6, stack1.getCount(Color.WHITE));

		// Test move to full space
		grid.PlaceStack(1, 1, new OrderedChipStack(new int[] { 0, 1, 1, 0, 0 }));
		stack1 = grid.getStack(1, 1);
		assertNotNull(stack1);
		assertEquals(1, stack1.getCount(Color.RED));
		assertEquals(1, stack1.getCount(Color.BLUE));

		// Test move to full space
		grid.PlaceStack(0, 1, new OrderedChipStack(new int[] { 0, 1, 1, 0, 0 }));
		stack1 = grid.getStack(0, 1);
		assertNotNull(stack1);
		assertEquals(3, stack1.getCount(Color.RED));
		assertEquals(1, stack1.getCount(Color.BLUE));

		Color[] order = stack1.getOrder();
		assertEquals(Color.RED, order[0]);
		assertEquals(Color.RED, order[1]);
		assertEquals(Color.BLUE, order[2]);
		assertEquals(Color.RED, order[3]);

		grid.PlaceChips(1, 2, Color.GREEN, 2);
		stack1 = grid.getStack(1, 2);
		assertEquals(2, stack1.getCount(Color.GREEN));
		
	}
	
	public void testInsertBlank()
	{
		GridStack grid = new GridStack(2, 5, 10);
		grid.PlaceChips(0, 0, Color.WHITE, 5);
		grid.PlaceChips(0, 1, Color.RED, 5);
		grid.PlaceChips(0, 2, Color.GREEN, 5);
		grid.PlaceChips(0, 3, Color.BLACK, 5);
		
		grid.InsertBlank(0, 2);
		assertNull(grid.getStack(0, 2));
		assertNotNull(grid.getStack(0, 0));
		assertNotNull(grid.getStack(0, 1));
		assertNotNull(grid.getStack(0, 3));
		assertNotNull(grid.getStack(0, 4));
		
		assertEquals(5, grid.getStack(0, 3).getCount(Color.GREEN));
		assertEquals(5, grid.getStack(0, 4).getCount(Color.BLACK));
		
		//Test row wrap
		grid.InsertBlank(0,3);
		assertNull(grid.getStack(0, 2));
		assertNull(grid.getStack(0, 3));
		assertNotNull(grid.getStack(0, 0));
		assertNotNull(grid.getStack(0, 1));
		assertNotNull(grid.getStack(0, 4));
		assertNotNull(grid.getStack(1, 0));

		assertEquals(5, grid.getStack(0, 4).getCount(Color.GREEN));
		assertEquals(5, grid.getStack(1, 0).getCount(Color.BLACK));
		
	}
	
	public void testRemoveChips()
	{
		GridStack grid = new GridStack(2, 5, 10);
		grid.PlaceChips(0, 0, Color.WHITE, 10);
		grid.PlaceChips(0, 1, Color.WHITE, 10);
		grid.PlaceChips(0, 2, Color.WHITE, 5);
		grid.PlaceChips(0, 3, Color.RED, 10);
		grid.PlaceChips(0, 4, Color.BLUE, 10);
		grid.PlaceChips(1, 0, Color.BLACK, 1);
		
		
		grid.RemoveChips(new ChipStack(new int[]{16, 9, 0, 0, 1}));
		assertEquals(9, grid.getStack(0, 0).getCount(Color.WHITE));
		assertEquals(0, grid.getStack(0, 1).getCount(Color.WHITE));
		assertEquals(0, grid.getStack(0, 2).getCount(Color.WHITE));
		assertEquals(1, grid.getStack(0, 3).getCount(Color.RED));
		assertEquals(10, grid.getStack(0, 4).getCount(Color.BLUE));
		assertEquals(0, grid.getStack(1, 0).getCount(Color.BLACK));
		
		assertEquals(25-16, grid.getCount(Color.WHITE));
		assertEquals(10-9, grid.getCount(Color.RED));
		assertEquals(10-0, grid.getCount(Color.BLUE));
		assertEquals(1-1, grid.getCount(Color.BLACK));
	}
	
	public void testBugEmptyMChipsPlaceChips()
	{
		GridStack grid = new GridStack(2, 5, 10);
		grid.PlaceChips(0, 0, Color.WHITE, 10);
		grid.PlaceChips(0, 1, Color.WHITE, 10);
		grid.PlaceChips(0, 2, Color.WHITE, 5);
		grid.PlaceChips(0, 3, Color.RED, 10);
		grid.PlaceChips(0, 4, Color.BLUE, 10);
		grid.PlaceChips(1, 0, Color.BLACK, 1);
		
		
		assertEquals(25, grid.getCount(Color.WHITE));
		assertEquals(10, grid.getCount(Color.RED));
		assertEquals(10, grid.getCount(Color.BLUE));
		assertEquals( 0, grid.getCount(Color.GREEN));
		assertEquals( 1, grid.getCount(Color.BLACK));
	}
	public void testBugEmptyMChipsPlaceStack()
	{
		GridStack grid = new GridStack(2, 5, 10);
		grid.PlaceStack(0, 0, new ChipStack(new int[] {10, 5, 0, 0, 0}));
		grid.PlaceStack(0, 1, new OrderedChipStack(new int[] {10, 5, 0, 2, 1}));
		grid.PlaceChips(0, 2, Color.WHITE, 5);
		grid.PlaceChips(0, 3, Color.RED, 10);
		grid.PlaceChips(0, 4, Color.BLUE, 10);
		grid.PlaceChips(1, 0, Color.BLACK, 1);
		
		
		assertEquals(25, grid.getCount(Color.WHITE));
		assertEquals(20, grid.getCount(Color.RED));
		assertEquals(10, grid.getCount(Color.BLUE));
		assertEquals( 2, grid.getCount(Color.GREEN));
		assertEquals( 2, grid.getCount(Color.BLACK));
	}
	
	public void testBugEmptyMChipsGridMove()
	{
		GridStack grid = new GridStack(2, 5, 10);
		grid.PlaceChips(0, 0, Color.WHITE, 10);
		grid.PlaceChips(0, 1, Color.WHITE, 10);
		grid.PlaceChips(0, 2, Color.WHITE, 5);
		grid.PlaceChips(0, 3, Color.RED, 10);
		grid.PlaceChips(0, 4, Color.BLUE, 10);
		grid.PlaceChips(1, 0, Color.BLACK, 1);
		
		grid.MoveStack(0, 0, 1, 0);
		
		assertEquals(25, grid.getCount(Color.WHITE));
		assertEquals(10, grid.getCount(Color.RED));
		assertEquals(10, grid.getCount(Color.BLUE));
		assertEquals( 0, grid.getCount(Color.GREEN));
		assertEquals( 1, grid.getCount(Color.BLACK));
	}
	

	public void testEmptyMChipsGridAddRemove()
	{
		GridStack grid = new GridStack(2, 5, 10);
		grid.AddChips(Color.WHITE, 10);
		grid.AddChips(Color.WHITE, 10);
		grid.AddChips(Color.WHITE, 5);
		grid.AddChips(Color.RED, 10);
		grid.AddChips(Color.BLUE, 10);
		grid.AddChips(Color.BLACK, 2);
		grid.RemoveChips(Color.BLACK, 1);
		
		grid.MoveStack(0, 0, 1, 0);
		
		assertEquals(25, grid.getCount(Color.WHITE));
		assertEquals(10, grid.getCount(Color.RED));
		assertEquals(10, grid.getCount(Color.BLUE));
		assertEquals( 0, grid.getCount(Color.GREEN));
		assertEquals( 1, grid.getCount(Color.BLACK));
	}
	
	public void testRemoveStack()
	{
		GridStack grid = new GridStack(2, 5, 10);
		grid.AddChips(Color.WHITE, 10);
		grid.AddChips(Color.WHITE, 10);
		grid.AddChips(Color.WHITE, 5);
		
		assertEquals(10, grid.getStack(0, 0).getCount(Color.WHITE));
		assertEquals(10, grid.getStack(0, 1).getCount(Color.WHITE));
		assertEquals(5, grid.getStack(0, 2).getCount(Color.WHITE));
		
		grid.RemoveStack(0,1);
		assertEquals(15, grid.getCount(Color.WHITE));
		assertNull(grid.getStack(0,1));
	}

	public void testBugRemoveStackNull()
	{
		GridStack grid = new GridStack(2, 5, 10);
		try{
			grid.RemoveStack(0, 0);
		}catch(Exception ex)
		{
			fail("Crash on Empty Stack");
		}
		
	}
}
