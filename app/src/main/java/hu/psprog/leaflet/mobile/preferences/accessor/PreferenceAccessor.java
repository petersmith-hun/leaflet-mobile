package hu.psprog.leaflet.mobile.preferences.accessor;

/**
 * Interface for implementations to provide access for a Shared Preferences entry.
 *
 * @author Peter Smith
 */
public interface PreferenceAccessor<T> {

    /**
     * Returns preference value as type of T.
     *
     * @return preference value
     */
    T getPreference();
}
