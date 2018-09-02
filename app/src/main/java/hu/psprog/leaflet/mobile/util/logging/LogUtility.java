package hu.psprog.leaflet.mobile.util.logging;

import android.util.Log;
import hu.psprog.leaflet.mobile.BuildConfig;

/**
 * Logging wrapper utility class.
 *
 * @author Peter Smith
 */
public final class LogUtility {

    private LogUtility() {
        // prevent instantiation
    }

    /**
     * Writes debug log only if the application is compiled in debug mode.
     *
     * @param tag log tag
     * @param message log message
     */
    public static void debug(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    /**
     * Writes parameterized debug log only if the application is compiled in debug mode.
     *
     * @param tag log tag
     * @param message log message
     * @param parameters log message parameters
     */
    public static void debug(String tag, String message, Object... parameters) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, String.format(message, parameters));
        }
    }
}
