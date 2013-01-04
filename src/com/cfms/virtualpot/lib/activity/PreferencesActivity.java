package com.cfms.virtualpot.lib.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.cfms.virtualpot.lib.R;
import com.cfms.virtualpot.lib.utils.Config;

public class PreferencesActivity extends PreferenceActivity {

	public static final String PREF_FLURRY_ENABLED = "flurry_enabled";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		int api = Config.getAPILevel();
		if(api >= Build.VERSION_CODES.HONEYCOMB)
		{
			//Use fragment
			this.setContentView(R.layout.preferences_hc_layout);
		}else{
			//Use deprecated normal preference activity
			addPreferencesFromResource(R.xml.preferences);
		}
	}

	public static SharedPreferences GetPreferences(Context c) {
		return PreferenceManager.getDefaultSharedPreferences(c);
	}

	public static Editor GetEditor(Context c) {

		return PreferencesActivity.GetPreferences(c).edit();
	}

	public static boolean getBoolean(Context c, String key)
	{
		Resources res = c.getResources();
		int id = res.getIdentifier(key, "bool", c.getPackageName());
		return PreferencesActivity.GetPreferences(c).getBoolean(key, res.getBoolean(id));
	}
	
	public static String getString(Context c, String key)
	{
		Resources res = c.getResources();
		int id = res.getIdentifier(key, "string", c.getPackageName());
		return PreferencesActivity.GetPreferences(c).getString(key, res.getString(id));
	}
	
	public static int getInteger(Context c, String key)
	{
		Resources res = c.getResources();
		int id = res.getIdentifier(key, "integer", c.getPackageName());
		return PreferencesActivity.GetPreferences(c).getInt(key, res.getInteger(id));
	}
	
    private final String TAG = "PreferencesActivity";
	public String getTag() {
		return TAG;
	}
	
}
