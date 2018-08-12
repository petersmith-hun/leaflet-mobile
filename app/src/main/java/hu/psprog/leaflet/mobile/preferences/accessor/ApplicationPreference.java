package hu.psprog.leaflet.mobile.preferences.accessor;

/**
 * Enum for application preferences and their keys stored in Shared Preferences.
 *
 * @author Peter Smith
 */
public enum ApplicationPreference {

    DEVICE_ID("device_id");

    private String preferenceKey;

    ApplicationPreference(String preferenceKey) {
        this.preferenceKey = preferenceKey;
    }

    public String getPreferenceKey() {
        return preferenceKey;
    }
}
