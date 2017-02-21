package org.vr.app.common.lifecycles;

import android.content.UriMatcher;

/**
 * Created by vladimirrybkin on 30/01/2017.
 */
public class LifeCyclesUriMatcher {

    public static UriMatcher URIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String AUTHORITY_PRECONDITIONS = "preconditions";
    public static final String AUTHORITY_SCREEN = "screen";

    public static final int DEFAULT_PRECONDITIONS_ID = 1;
    public static final int SPLASH_SCREEN_ID = 2;
    public static final int HOME_SCREEN_ID = 3;

    static {
        URIMatcher.addURI(AUTHORITY_PRECONDITIONS, "default", DEFAULT_PRECONDITIONS_ID);
        URIMatcher.addURI(AUTHORITY_SCREEN, "splash", SPLASH_SCREEN_ID);
        URIMatcher.addURI(AUTHORITY_SCREEN, "home", HOME_SCREEN_ID);
    }

}
