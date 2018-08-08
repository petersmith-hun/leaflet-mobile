package hu.psprog.leaflet.mobile.bridge.support;

import hu.psprog.leaflet.bridge.client.request.RequestAuthentication;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter Smith
 */
public class AndroidRequestAuthentication implements RequestAuthentication {

    @Override
    public Map<String, String> getAuthenticationHeader() {
        return new HashMap<>();
    }
}
