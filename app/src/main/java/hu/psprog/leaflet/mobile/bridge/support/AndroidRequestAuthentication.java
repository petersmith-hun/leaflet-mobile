package hu.psprog.leaflet.mobile.bridge.support;

import hu.psprog.leaflet.bridge.client.request.RequestAuthentication;

import java.util.HashMap;
import java.util.Map;

/**
 * Bridge {@link RequestAuthentication} implementation.
 * As request authentication is currently not needed, this implementation does nothing.
 *
 * @author Peter Smith
 */
public class AndroidRequestAuthentication implements RequestAuthentication {

    @Override
    public Map<String, String> getAuthenticationHeader() {
        return new HashMap<>();
    }
}
