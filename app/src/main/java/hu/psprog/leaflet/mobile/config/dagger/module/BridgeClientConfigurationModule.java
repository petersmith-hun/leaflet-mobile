package hu.psprog.leaflet.mobile.config.dagger.module;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import dagger.Module;
import dagger.Provides;
import hu.psprog.leaflet.bridge.client.BridgeClient;
import hu.psprog.leaflet.bridge.client.handler.InvocationFactory;
import hu.psprog.leaflet.bridge.client.handler.ResponseReader;
import hu.psprog.leaflet.bridge.client.impl.BridgeClientImpl;
import hu.psprog.leaflet.bridge.client.impl.InvocationFactoryImpl;
import hu.psprog.leaflet.bridge.client.impl.ResponseReaderImpl;
import hu.psprog.leaflet.bridge.client.request.RequestAdapter;
import hu.psprog.leaflet.bridge.client.request.RequestAuthentication;
import hu.psprog.leaflet.bridge.client.request.strategy.CallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.DeleteCallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.GetCallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.PostCallStrategy;
import hu.psprog.leaflet.bridge.client.request.strategy.impl.PutCallStrategy;
import hu.psprog.leaflet.mobile.BuildConfig;
import hu.psprog.leaflet.mobile.bridge.support.AndroidBridgeRequestAdapter;
import hu.psprog.leaflet.mobile.bridge.support.AndroidRequestAuthentication;
import hu.psprog.leaflet.mobile.bridge.support.JerseyFilterExclusionFeature;
import hu.psprog.leaflet.mobile.preferences.ApplicationPreferencesProvider;

import javax.inject.Singleton;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.Arrays;
import java.util.List;

/**
 * Dagger module to configure Bridge Client.
 *
 * @author Peter Smith
 */
@Module
public class BridgeClientConfigurationModule {

    @Provides
    @Singleton
    public RequestAdapter requestAdapter(ApplicationPreferencesProvider applicationPreferencesProvider) {
        return new AndroidBridgeRequestAdapter(applicationPreferencesProvider);
    }

    @Provides
    @Singleton
    public RequestAuthentication requestAuthentication() {
        return new AndroidRequestAuthentication();
    }

    @Provides
    @Singleton
    public BridgeClient createBridgeClient(InvocationFactory invocationFactory, ResponseReader responseReader) {
        return new BridgeClientImpl(createWebTarget(), invocationFactory, responseReader);
    }

    @Provides
    @Singleton
    public ResponseReader responseReader(RequestAdapter requestAdapter) {
        return new ResponseReaderImpl(requestAdapter);
    }

    @Provides
    @Singleton
    public InvocationFactory invocationFactory(RequestAdapter requestAdapter, RequestAuthentication requestAuthentication) {
        return new InvocationFactoryImpl(requestAuthentication, getCallStrategies(), requestAdapter);
    }

    private WebTarget createWebTarget() {
        return ClientBuilder.newBuilder()
                .register(JacksonJsonProvider.class)
                .register(JerseyFilterExclusionFeature.class)
                .build()
                .target(BuildConfig.API_HOST_URL);
    }

    private List<CallStrategy> getCallStrategies() {
        return Arrays.asList(new PostCallStrategy(), new GetCallStrategy(), new PutCallStrategy(), new DeleteCallStrategy());
    }
}
