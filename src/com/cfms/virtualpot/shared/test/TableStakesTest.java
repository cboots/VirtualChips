package com.cfms.virtualpot.shared.test;

import junit.framework.TestCase;

import com.cfms.virtualpot.lib.game.TableStakes;

public class TableStakesTest extends TestCase {

    
    public void testDefaults()
    {
    	TableStakes stakes = new TableStakes();
    	
    	double[] values = stakes.getChipValues();
    	assertEquals(1.0,   values[0]);
    	assertEquals(5.0,   values[1]);
    	assertEquals(10.0,  values[2]);
    	assertEquals(25.0,  values[3]);
    	assertEquals(100.0, values[4]);
    	
    }
    
}
