package com.cfms.virtualpot.lib.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * The Class Config. Contains static methods that control access to features,
 * URL links, ads, and other version specific options.
 */
public class Config {

	/** The Constant FLURRY_KEY_PRO. */
	// static final private String FLURRY_KEY_PAID = "";

	/** The Constant FLURRY_KEY_LITE. */
	static final public String FLURRY_KEY_FREE = "KDZFQMGM25DDYBMPXJYT";

	/** The Constant FLURRY_KEY_DEBUG. */
	static final private String FLURRY_KEY_DEBUG = "HHYBJTCF2D7T37NZJB36";

	/** The Constant PRIVACY_POLICY_URL. */
	static final private String PRIVACY_POLICY_URL = "http://www.codefusionmobile.com/privacy-policy.html";

	/**
	 * Gets the flurry api key.
	 * 
	 * @param ctx
	 *            the ctx
	 * @return the flurry api key
	 */
	public static String getFlurryApiKey(Context ctx) {
		if (Config.isDebuggable(ctx))
			return FLURRY_KEY_DEBUG;
		else {
//			if (isPaidVersion(ctx))
//				return FLURRY_KEY_PAID;
//			else
				return FLURRY_KEY_FREE;
		}
	}

	/**
	 * Gets the privacy policy url.
	 * 
	 * @return the privacy policy url
	 */
	public static String getPrivacyPolicyUrl() {
		return PRIVACY_POLICY_URL;
	}

	/**
	 * Returns true if paid version.
	 * 
	 * @param ctx
	 *            the ctx
	 * @return the version
	 */
	public static boolean isPaidVersion(Context ctx) {
		return ctx.getPackageName().equals("com.cfms.virtualpot.paid");
	}

	/**
	 * Checks if is debuggable. Used to differentiate release code from debug
	 * code by manifest debuggable tag.
	 * 
	 * @param ctx
	 *            the ctx
	 * @return true, if is debuggable
	 */
	public static boolean isDebuggable(Context ctx) {
		int flags = ctx.getApplicationInfo().flags;
		boolean debuggable = (0 != (flags & ApplicationInfo.FLAG_DEBUGGABLE));
		DebugLog.D("Config", "isDebuggable() returned " + debuggable);
		return debuggable;
	}

	public static int getAPILevel() {
		return Integer.valueOf(android.os.Build.VERSION.SDK_INT);
	}

}
