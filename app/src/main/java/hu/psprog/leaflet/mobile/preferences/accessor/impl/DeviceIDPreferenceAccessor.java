package hu.psprog.leaflet.mobile.preferences.accessor.impl;

import android.content.SharedPreferences;
import hu.psprog.leaflet.mobile.preferences.accessor.ApplicationPreference;
import hu.psprog.leaflet.mobile.preferences.accessor.PreferenceAccessor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

/**
 * Provides access for device ID application preference via Shared Preferences by returning the stored value.
 * If device ID is currently not present in shared preferences, a random ID will be generated and persisted.
 *
 * @author Peter Smith
 */
@Singleton
public class DeviceIDPreferenceAccessor implements PreferenceAccessor<String> {

    private static final String SHARED_PREFERENCE_KEY_DEVICE_ID = ApplicationPreference.DEVICE_ID.getPreferenceKey();
    private static final String DEFAULT_VALUE = null;

    private SharedPreferences sharedPreferences;

    @Inject
    public DeviceIDPreferenceAccessor(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public String getPreference() {

        if (!sharedPreferences.contains(SHARED_PREFERENCE_KEY_DEVICE_ID)) {
            sharedPreferences.edit()
                    .putString(SHARED_PREFERENCE_KEY_DEVICE_ID, UUID.randomUUID().toString())
                    .apply();
        }

        return sharedPreferences.getString(SHARED_PREFERENCE_KEY_DEVICE_ID, DEFAULT_VALUE);
    }
}
