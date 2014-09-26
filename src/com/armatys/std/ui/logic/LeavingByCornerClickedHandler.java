package com.armatys.std.ui.logic;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marmatys on 26.09.14.
 */
public class LeavingByCornerClickedHandler {
    private static final String TAG = LeavingByCornerClickedHandler.class.getName();
    private static final long MAX_PERIOD = 5000l;

    private static int MAX_DISTANCE = 100;

    private enum Corner {
        LEFT_TOP,
        RIGHT_TOP,
        RIGHT_BOTTOM,
        LEFT_BOTTOM
    }

    private static Map<Corner, Long> cornerToTime = new HashMap<Corner, Long>();

    static {
        setDefaultState();;
    }

    private static void setDefaultState() {
        for (Corner corner : Corner.values()) {
            cornerToTime.put(corner, 0l);
        }
    }

    public static void clearState() {
        setDefaultState();
    }

    public static boolean shouldExitAfterClicked(MotionEvent event, View view) {
        Corner corner = getCorner(event, view);
        if (corner == null) {
            Log.v(TAG, "Not clicked at corner - then should work");
            return false;
        }

        Log.d(TAG, "Corner added " + corner);
        cornerToTime.put(corner, new Date().getTime());

        if (wasAllClicksDuringLastPeriod()) {
            Log.d(TAG, "Was clicked during last period - so should exit");
            return true;
        }

        return false;
    }

    private static boolean wasAllClicksDuringLastPeriod() {
        Long min = Collections.min(cornerToTime.values());
        Long max = Collections.max(cornerToTime.values());
        Log.v(TAG, "min: " + min + " max: " + max);
        return max - min < MAX_PERIOD;
    }

    private static Corner getCorner(MotionEvent event, View view) {
        if (event.getX() < MAX_DISTANCE && event.getY() < MAX_DISTANCE) {
            return Corner.LEFT_TOP;
        }

        if (view.getWidth() - event.getX() < MAX_DISTANCE && event.getY() < MAX_DISTANCE) {
            return Corner.RIGHT_TOP;
        }

        if (view.getWidth() - event.getX() < MAX_DISTANCE && view.getHeight() - event.getY() < MAX_DISTANCE) {
            return Corner.RIGHT_BOTTOM;
        }

        if (event.getX() < MAX_DISTANCE && view.getHeight() - event.getY() < MAX_DISTANCE) {
            return Corner.LEFT_BOTTOM;
        }

        return null;
    }
}
