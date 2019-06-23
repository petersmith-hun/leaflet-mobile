package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client.support;

import hu.psprog.leaflet.mobile.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

/**
 * OkHttp {@link Interceptor} implementation adding client ID to every request.
 *
 * @author Peter Smith
 */
@Singleton
public class ClientIDInterceptor implements Interceptor {

    private static final String HEADER_CLIENT_ID = "X-Client-ID";

    @Inject
    public ClientIDInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(appendRequest(chain.request()));
    }

    private Request appendRequest(Request request) {
        return request.newBuilder()
                .addHeader(HEADER_CLIENT_ID, BuildConfig.API_CLIENT_ID)
                .build();
    }
}
