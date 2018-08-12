package hu.psprog.leaflet.mobile.bridge.support;

import hu.psprog.leaflet.bridge.client.request.RequestAdapter;
import hu.psprog.leaflet.mobile.BuildConfig;
import hu.psprog.leaflet.mobile.preferences.ApplicationPreferencesProvider;

/**
 * Bridge {@link RequestAdapter} implementation for Android system.
 * Device ID is provided from shared preferences, generated per installation.
 * Client ID is provided from {@link BuildConfig}.
 *
 * @author Peter Smith
 */
public class AndroidBridgeRequestAdapter implements RequestAdapter {

    private ApplicationPreferencesProvider applicationPreferencesProvider;

    public AndroidBridgeRequestAdapter(ApplicationPreferencesProvider applicationPreferencesProvider) {
        this.applicationPreferencesProvider = applicationPreferencesProvider;
    }

    @Override
    public String provideDeviceID() {
        return applicationPreferencesProvider.getDeviceID();
    }

    @Override
    public String provideClientID() {
        return BuildConfig.API_CLIENT_ID;
    }

    @Override
    public void consumeAuthenticationToken(String token) {

    }
}
