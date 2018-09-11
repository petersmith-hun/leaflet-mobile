package hu.psprog.leaflet.mobile.preferences.accessor;

/**
 * Interface for implementations to provide write access for a Shared Preferences entry.
 *
 * @author Peter Smith
 */
public interface ModifiablePreferenceAccessor<T> extends PreferenceAccessor<T> {

    /**
     * Updates a shared preference to the given value.
     * Creates the shared preference if it does not already exist.
     *
     * @param value new preference value
     */
    void updatePreference(T value);
}
