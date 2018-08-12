package hu.psprog.leaflet.mobile.preferences;

import android.content.SharedPreferences;
import hu.psprog.leaflet.mobile.preferences.accessor.impl.DeviceIDPreferenceAccessor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Provides controlled access for {@link SharedPreferences}.
 *
 * @author Peter Smith
 */
@Singleton
public class ApplicationPreferencesProvider {

    private DeviceIDPreferenceAccessor deviceIDPreferenceAccessor;

    @Inject
    public ApplicationPreferencesProvider(DeviceIDPreferenceAccessor deviceIDPreferenceAccessor) {
        this.deviceIDPreferenceAccessor = deviceIDPreferenceAccessor;
    }

    /**
     * Returns stored device ID.
     *
     * @return stored device ID
     */
    public String getDeviceID() {
        return deviceIDPreferenceAccessor.getPreference();
    }
}
