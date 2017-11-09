package com.armatys.std;


import android.content.Intent;
import android.util.Log;

/**
 * Enumerates Intent(s) used in application.
 */
public enum Intents {

    /**
     * Indicates that movie should be shown.
     *
     * Requires {@link IntentExtraKeys#MOVIE_PATH}
     */
    SHOW_MOVIE;


    private final String TAG = Intents.class.getName();

	/**
	 * Intent action, must be the same as defined in AndroidManifest.xml
	 */
	private final String action;

	private final int flags;

	/**
	 * Creates new intent, action is generated based on package name and enum.
	 */
	private Intents() {
		this(null, 0);
	}

	/**
	 * Creates new intent with explicitly set flags.
	 */
	private Intents(int flags) {
		this(null, 0);
	}

	/**
	 * Creates new intent with explicitly set action.
	 */
	private Intents(String action) {
		this(action, 0);
	}

	private Intents(String action, int flags) {
		this.action = action != null ? action : Intents.class.getPackage().getName() + '.' + name();
		this.flags = flags;
		Log.d(TAG, String.format("Created intent action %s", this.action));
	}

	public boolean equals(Intent intent) {
		return equals(intent.getAction());
	}

	public boolean equals(String action) {
		return this.action.equals(action);
	}

	public String getAction() {
		return action;
	}

	public Intent getIntent() {
		return new Intent(action).setFlags(flags);
	}

	@Override
	public String toString() {
		return "Intent: " + action;
	}
}
