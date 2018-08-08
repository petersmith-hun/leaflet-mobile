package hu.psprog.leaflet.mobile.bridge.support;

import hu.psprog.leaflet.bridge.client.request.RequestAdapter;
import hu.psprog.leaflet.mobile.BuildConfig;

/**
 * @author Peter Smith
 */
public class AndroidBridgeRequestAdapter implements RequestAdapter {

    @Override
    public String provideDeviceID() {
        // TODO generate device ID
        return BuildConfig.API_CLIENT_ID;
    }

    @Override
    public String provideClientID() {
        return BuildConfig.API_CLIENT_ID;
    }

    @Override
    public void consumeAuthenticationToken(String token) {

    }
}
