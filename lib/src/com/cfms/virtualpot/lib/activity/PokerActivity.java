package com.cfms.virtualpot.lib.activity;

import com.cfms.virtualpot.lib.R;

import android.app.Activity;
import android.os.Bundle;

public class PokerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public String getTag()
    {
    	return "PokerActivity";
    }
}