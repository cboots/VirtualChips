package com.cfms.virtualpot.free.test;

import junit.framework.TestCase;

import com.cfms.virtualpot.lib.game.OrderedChipStack;
import com.cfms.virtualpot.lib.game.ChipStack.Color;

public class OrderedChipStackManipulationTest extends TestCase {

	public void testCreateEmptyStack() {
		OrderedChipStack stack = new OrderedChipStack();
		for (int i = 0; i < OrderedChipStack.NUM_COLORS; i++) {
			assertEquals(0, stack.getCount(i));
			assertEquals(0, stack.getCount(OrderedChipStack.Color.values()[i]));
		}
		Color[] order = stack.getOrder();
		assertEquals(0, order.length);
	}

	public void testCreateFullStack() {
		OrderedChipStack stack = new OrderedChipStack(new int[] { 10, 0, 20,
				25, 30 });
		assertEquals(10, stack.getCount(OrderedChipStack.Color.WHITE));
		assertEquals(0, stack.getCount(OrderedChipStack.Color.RED));
		assertEquals(20, stack.getCount(OrderedChipStack.Color.BLUE));
		assertEquals(25, stack.getCount(OrderedChipStack.Color.GREEN));
		assertEquals(30, stack.getCount(OrderedChipStack.Color.BLACK));

		Color[] order = stack.getOrder();
		assertEquals(85, order.length);
		int index = 0;
		for (int i = 0; i < 30; i++, index++) {
			assertEquals(Color.BLACK, order[index]);
		}
		for (int i = 0; i < 25; i++, index++) {
			assertEquals(Color.GREEN, order[index]);
		}
		for (int i = 0; i < 20; i++, index++) {
			assertEquals(Color.BLUE, order[index]);
		}
		for (int i = 0; i < 10; i++, index++) {
			assertEquals(Color.WHITE, order[index]);
		}
	}

	public void testAddChips() {
		OrderedChipStack stack = new OrderedChipStack();
		for (int i = 0; i < OrderedChipStack.NUM_COLORS; i++) {
			assertEquals(0, stack.getCount(i));
			stack.AddChips(i, 10);
			assertEquals(10, stack.getCount(i));
		}
	}

	public void testAddChipsByColor() {
		OrderedChipStack stack = new OrderedChipStack(new int[] { 5, 5, 0, 0,
				10 });
		assertEquals(5, stack.getCount(OrderedChipStack.Color.RED));
		stack.AddChips(OrderedChipStack.Color.RED, 15);
		assertEquals(20, stack.getCount(OrderedChipStack.Color.RED));
	}

	public void testRemoveChips() {
		int[] init = new int[] { 50, 25, 20, 50, 10 };
		OrderedChipStack stack = new OrderedChipStack(init);
		for (int i = 0; i < OrderedChipStack.NUM_COLORS; i++) {
			assertEquals(init[i], stack.getCount(i));
			stack.RemoveChips(i, 10);
			assertEquals(init[i] - 10, stack.getCount(i));
		}
	}

	public void testRemoveChipsByColor() {
		OrderedChipStack stack = new OrderedChipStack(new int[] { 50, 25, 20,
				50, 10 });
		assertEquals(10, stack.getCount(Color.BLACK));
		stack.RemoveChips(Color.BLACK, 5);
		assertEquals(5, stack.getCount(Color.BLACK));
	}

	public void testAddStack() {
		OrderedChipStack stack = new OrderedChipStack(new int[] { 50, 5, 10,
				15, 20 });
		OrderedChipStack stack2 = new OrderedChipStack(new int[] { 5, 10, 15,
				25, 0 });
		stack.AddChips(stack2);
		assertEquals(55, stack.getCount(0));
		assertEquals(15, stack.getCount(1));
		assertEquals(25, stack.getCount(2));
		assertEquals(40, stack.getCount(3));
		assertEquals(20, stack.getCount(4));
	}

	public void testRemoveStack() {
		OrderedChipStack stack = new OrderedChipStack(new int[] { 50, 25, 20,
				25, 20 });
		OrderedChipStack stack2 = new OrderedChipStack(new int[] { 5, 10, 15,
				25, 0 });
		stack.RemoveChips(stack2);
		assertEquals(45, stack.getCount(0));
		assertEquals(15, stack.getCount(1));
		assertEquals(5, stack.getCount(2));
		assertEquals(0, stack.getCount(3));
		assertEquals(20, stack.getCount(4));
	}

	public void testRemoveTooMany() {
		OrderedChipStack stack = new OrderedChipStack();
		try {
			stack.RemoveChips(0, 10);
			fail("Exception should have been thrown.");
		} catch (IllegalArgumentException ex) {

		} catch (Exception e) {
			fail("Unknown Exception Thrown");
		}
	}

	public void testIndexOutOfRangeException() {
		OrderedChipStack stack = new OrderedChipStack();
		try {
			stack.RemoveChips(-1, 0);
			fail("Exception should have been thrown.");
		} catch (IllegalArgumentException ex) {

		} catch (Exception e) {
			fail("Unknown Exception Thrown");
		}

		try {
			stack.RemoveChips(OrderedChipStack.NUM_COLORS, 0);
			fail("Exception should have been thrown.");
		} catch (IllegalArgumentException ex) {

		} catch (Exception e) {
			fail("Unknown Exception Thrown");
		}

		try {
			stack.AddChips(-1, 0);
			fail("Exception should have been thrown.");
		} catch (IllegalArgumentException ex) {

		} catch (Exception e) {
			fail("Unknown Exception Thrown");
		}

		try {
			stack.AddChips(OrderedChipStack.NUM_COLORS, 0);
			fail("Exception should have been thrown.");
		} catch (IllegalArgumentException ex) {

		} catch (Exception e) {
			fail("Unknown Exception Thrown");
		}
	}

	public void testCopyStack() {
		OrderedChipStack org = new OrderedChipStack(new int[] { 5, 10, 15, 20,
				25 });
		OrderedChipStack copy = new OrderedChipStack(org);

		// Test copy
		for (int i = 0; i < OrderedChipStack.NUM_COLORS; i++) {
			assertEquals(org.getCount(i), copy.getCount(i));
		}

		// Test deep copy
		assertNotSame(org, copy);
		copy.AddChips(0, 10);
		assertEquals(15, copy.getCount(0));
		assertFalse(15 == org.getCount(0));
	}

	public void testCopyStackMethod() {
		OrderedChipStack org = new OrderedChipStack(new int[] { 5, 10, 15, 20,
				25 });
		OrderedChipStack copy = (OrderedChipStack) org.copy();
		;

		// Test copy
		for (int i = 0; i < OrderedChipStack.NUM_COLORS; i++) {
			assertEquals(org.getCount(i), copy.getCount(i));
		}
		// Test order copy
		Color[] corder = copy.getOrder();
		Color[] order = org.getOrder();
		assertEquals(corder.length, order.length);
		for (int i = 0; i < order.length; i++) {
			assertEquals(corder[i], order[i]);
		}

		// Test deep copy
		assertNotSame(org, copy);
		copy.AddChips(0, 10);
		assertEquals(15, copy.getCount(0));
		assertFalse(15 == org.getCount(0));

	}

	public void testStacksEqual() {
		OrderedChipStack stack1 = new OrderedChipStack(new int[] { 10, 15, 25,
				30, 1 });
		OrderedChipStack stack2 = new OrderedChipStack(stack1);
		OrderedChipStack stack3 = new OrderedChipStack(new int[] { 10, 10, 25,
				30, 1 });

		assertTrue(stack1.stacksMatch(stack2));
		assertFalse(stack2.stacksMatch(stack3));

	}

	public void testTransferStacks() {
		OrderedChipStack stack = new OrderedChipStack(new int[] { 50, 25, 20,
				25, 20 });
		OrderedChipStack stack2 = new OrderedChipStack(new int[] { 5, 10, 15,
				25, 0 });

		OrderedChipStack.TransferChips(stack, stack2, 0, 40);
		OrderedChipStack.TransferChips(stack, stack2, Color.RED, 15);
		OrderedChipStack.TransferChips(stack, stack2, new int[] { 0, 0, 20, 25,
				20 });

		assertEquals(10, stack.getCount(0));
		assertEquals(10, stack.getCount(1));
		assertEquals(0, stack.getCount(2));
		assertEquals(0, stack.getCount(3));
		assertEquals(0, stack.getCount(4));

		assertEquals(45, stack2.getCount(0));
		assertEquals(25, stack2.getCount(1));
		assertEquals(35, stack2.getCount(2));
		assertEquals(50, stack2.getCount(3));
		assertEquals(20, stack2.getCount(4));
	}

	public void testGetValue() {
		OrderedChipStack stack = new OrderedChipStack(new int[] { 50, 10, 2, 2,
				4 });

		double value = stack.getValue(new double[] { 1, 5, 10, 25, 100 });
		assertEquals(570.0, value);

	}

	public void testMakeChange() {

		OrderedChipStack stack = new OrderedChipStack(new int[] { 0, 10, 1, 2,
				1 });
		double value = stack.getValue(new double[] { 1, 5, 10, 25, 100 });

		stack.makeChange(new double[] { 1, 5, 10, 25, 100 }, Color.RED, 2);// 10,
																			// 8,
																			// 1,
																			// 2,
																			// 1

		// Exchange all Black for Green
		stack.makeChange(new double[] { 1, 5, 10, 25, 100 }, Color.BLACK);// 10,
																			// 8,
																			// 1,
																			// 6,
																			// 0
		stack.makeChange(new double[] { 1, 5, 10, 25, 100 }, Color.GREEN, 1,
				Color.RED);// 10, 13, 1, 5, 0

		assertTrue(stack.stacksMatch(new int[] { 10, 13, 1, 5, 0 }));
		assertEquals(value, stack.getValue(new double[] { 1, 5, 10, 25, 100 }));
	}

	public void testMakeChangeNotDivisible() {

		OrderedChipStack stack = new OrderedChipStack(new int[] { 0, 0, 0, 10,
				0 });
		double value = stack.getValue(new double[] { 1, 2, 5, 25, 100 });

		stack.makeChange(new double[] { 1, 2, 5, 25, 100 }, Color.GREEN, 1,
				Color.RED);// 1, 12, 0, 9, 0

		assertTrue(stack.stacksMatch(new int[] { 1, 12, 0, 9, 0 }));
		assertEquals(value, stack.getValue(new double[] { 1, 2, 5, 25, 100 }));
	}

	public void testAddToTop() {
		OrderedChipStack stack = new OrderedChipStack();
		stack.AddToTop(Color.BLACK);
		stack.AddToTop(Color.BLUE);
		stack.AddToTop(Color.WHITE);
		stack.AddToTop(Color.RED);

		Color[] order = stack.getOrder();
		assertEquals(Color.BLACK, order[0]);
		assertEquals(Color.BLUE, order[1]);
		assertEquals(Color.WHITE, order[2]);
		assertEquals(Color.RED, order[3]);
	}

	public void testAddToTopOrderedChipStack() {
		OrderedChipStack stack = new OrderedChipStack();
		stack.AddToTop(Color.BLACK);
		stack.AddToTop(Color.BLUE);
		stack.AddToTop(Color.WHITE);
		stack.AddToTop(Color.RED);

		OrderedChipStack stack2 = new OrderedChipStack();
		stack2.AddToTop(Color.BLUE);
		stack2.AddToTop(Color.WHITE);

		stack.AddToTop(stack2);

		Color[] order = stack.getOrder();
		assertEquals(Color.BLACK, order[0]);
		assertEquals(Color.BLUE, order[1]);
		assertEquals(Color.WHITE, order[2]);
		assertEquals(Color.RED, order[3]);
		assertEquals(Color.BLUE, order[4]);
		assertEquals(Color.WHITE, order[5]);
	}

	public void testRemoveFromMiddle() {
		OrderedChipStack stack = new OrderedChipStack();
		stack.AddToTop(Color.BLACK);
		stack.AddToTop(Color.BLUE);
		stack.AddToTop(Color.WHITE);
		stack.AddToTop(Color.RED);

		stack.RemoveChips(Color.BLUE, 1);

		assertEquals(0, stack.getCount(Color.BLUE));
		Color[] order = stack.getOrder();
		assertEquals(3, order.length);
		assertEquals(Color.BLACK, order[0]);
		assertEquals(Color.WHITE, order[1]);
		assertEquals(Color.RED, order[2]);
	}

	
	public void testRemoveTop()
	{
		OrderedChipStack stack = new OrderedChipStack();
		stack.AddToTop(Color.BLACK);
		stack.AddToTop(Color.BLUE);
		stack.AddToTop(Color.WHITE);
		stack.AddToTop(Color.RED);

		Color color = stack.RemoveTop();

		Color[] order = stack.getOrder();
		assertEquals(3, order.length);
		assertEquals(Color.BLACK, order[0]);
		assertEquals(Color.BLUE, order[1]);
		assertEquals(Color.WHITE, order[2]);
		assertEquals(Color.RED, color);
	}
}
