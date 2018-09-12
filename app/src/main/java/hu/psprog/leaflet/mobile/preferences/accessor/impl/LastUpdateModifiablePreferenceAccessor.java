package hu.psprog.leaflet.mobile.preferences.accessor.impl;

import android.content.SharedPreferences;
import hu.psprog.leaflet.mobile.preferences.accessor.ApplicationPreference;
import hu.psprog.leaflet.mobile.preferences.accessor.ModifiablePreferenceAccessor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

/**
 * Provides access for the last update shared preference.
 * Able to read and update as well.
 *
 * @author Peter Smith
 */
@Singleton
public class LastUpdateModifiablePreferenceAccessor implements ModifiablePreferenceAccessor<Date> {

    private static final String SHARED_PREFERENCE_KEY_LAST_UPDATE = ApplicationPreference.LAST_UPDATE.getPreferenceKey();
    private static final int DEFAULT_VALUE = 0;

    private SharedPreferences sharedPreferences;

    @Inject
    public LastUpdateModifiablePreferenceAccessor(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void updatePreference(Date value) {
        sharedPreferences.edit()
                .putLong(SHARED_PREFERENCE_KEY_LAST_UPDATE, convertDate(value))
                .apply();
    }

    /**
     * Returns currently stored last update date value.
     * If it does not exist, null reference is returned.
     *
     * @return last update date or {@code null}
     */
    @Override
    public Date getPreference() {

        Date lastUpdateDate = null;
        long time = sharedPreferences.getLong(SHARED_PREFERENCE_KEY_LAST_UPDATE, DEFAULT_VALUE);
        if (time != DEFAULT_VALUE) {
            lastUpdateDate = convertDate(time);
        }

        return lastUpdateDate;
    }

    private long convertDate(Date date) {
        return date.getTime();
    }

    private Date convertDate(long time) {
        return new Date(time);
    }
}
