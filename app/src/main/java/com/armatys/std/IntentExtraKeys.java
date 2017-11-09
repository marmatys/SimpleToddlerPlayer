package com.armatys.std;

/**
 * Keys that are should be used in intents.
 */
public enum IntentExtraKeys {
	//
	// In alphabetical order
	//

    /**
     * Path to movie that should be played.
     */
    MOVIE_PATH;

	/**
	 * Returns unique key.
	 */
	public final String getKey() {
		return toString();
	}
}
