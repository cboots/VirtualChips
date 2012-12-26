package com.cfms.virtualpot.free.test;

import android.test.ActivityInstrumentationTestCase2;
import com.cfms.virtualpot.lib.activity.PokerActivity;

public class PokerActivityTest extends
		ActivityInstrumentationTestCase2<PokerActivity> {

	PokerActivity mActivity;
	
	public PokerActivityTest()
	{
		super(PokerActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
        mActivity = this.getActivity();
        
    }
    
    public void testPreconditions() {
    	assertNotNull(mActivity);
    }
    

    public void testGetTag(){
    	assertEquals("PokerActivity", mActivity.getTag());
    	
    }
}
