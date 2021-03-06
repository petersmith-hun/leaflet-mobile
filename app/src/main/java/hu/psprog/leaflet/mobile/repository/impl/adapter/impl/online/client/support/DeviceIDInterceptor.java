package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client.support;

import hu.psprog.leaflet.mobile.preferences.ApplicationPreferencesProvider;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

/**
 * OkHttp {@link Interceptor} implementation adding device ID to every request.
 *
 * @author Peter Smith
 */
@Singleton
public class DeviceIDInterceptor implements Interceptor {

    private static final String HEADER_DEVICE_ID = "X-Device-ID";

    private ApplicationPreferencesProvider applicationPreferencesProvider;

    @Inject
    public DeviceIDInterceptor(ApplicationPreferencesProvider applicationPreferencesProvider) {
        this.applicationPreferencesProvider = applicationPreferencesProvider;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(appendRequest(chain.request()));
    }

    private Request appendRequest(Request request) {
        return request.newBuilder()
                .addHeader(HEADER_DEVICE_ID, applicationPreferencesProvider.getDeviceID())
                .build();
    }
}
