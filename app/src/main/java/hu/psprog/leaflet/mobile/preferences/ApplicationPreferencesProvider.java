package hu.psprog.leaflet.mobile.preferences;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import hu.psprog.leaflet.mobile.preferences.accessor.impl.DeviceIDPreferenceAccessor;
import hu.psprog.leaflet.mobile.preferences.accessor.impl.LastUpdateModifiablePreferenceAccessor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

/**
 * Provides controlled access for {@link SharedPreferences}.
 *
 * @author Peter Smith
 */
@Singleton
public class ApplicationPreferencesProvider {

    private DeviceIDPreferenceAccessor deviceIDPreferenceAccessor;
    private LastUpdateModifiablePreferenceAccessor lastUpdateModifiablePreferenceAccessor;

    @Inject
    public ApplicationPreferencesProvider(DeviceIDPreferenceAccessor deviceIDPreferenceAccessor,
                                          LastUpdateModifiablePreferenceAccessor lastUpdateModifiablePreferenceAccessor) {
        this.deviceIDPreferenceAccessor = deviceIDPreferenceAccessor;
        this.lastUpdateModifiablePreferenceAccessor = lastUpdateModifiablePreferenceAccessor;
    }

    /**
     * Returns stored device ID.
     *
     * @return stored device ID
     */
    public String getDeviceID() {
        return deviceIDPreferenceAccessor.getPreference();
    }

    /**
     * Returns last update date.
     *
     * @return last update date, or {@code null} if not present
     */
    @Nullable
    public Date getLastUpdateDate() {
        return lastUpdateModifiablePreferenceAccessor.getPreference();
    }

    /**
     * Updates last update date to the current.
     */
    public void updateLastUpdateDate() {
        lastUpdateModifiablePreferenceAccessor.updatePreference(new Date());
    }
}
