package hu.psprog.leaflet.mobile.config.dagger.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dagger.Module;
import dagger.Provides;
import hu.psprog.leaflet.mobile.BuildConfig;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client.CategoryRESTClient;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client.DocumentRESTClient;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client.EntryRESTClient;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client.support.ClientIDInterceptor;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client.support.DeviceIDInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.inject.Singleton;

/**
 * Dagger module to configure Bridge services.
 *
 * @author Peter Smith
 */
@Module
public class RetrofitRESTClientConfigurationModule {

    @Provides
    @Singleton
    public CategoryRESTClient categoryRESTCliente(Retrofit retrofit) {
        return retrofit.create(CategoryRESTClient.class);
    }

    @Provides
    @Singleton
    public DocumentRESTClient documentRESTClient(Retrofit retrofit) {
        return retrofit.create(DocumentRESTClient.class);
    }

    @Provides
    @Singleton
    public EntryRESTClient entryRESTClient(Retrofit retrofit) {
        return retrofit.create(EntryRESTClient.class);
    }

    @Provides
    @Singleton
    public Retrofit baseRetrofitClient(OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_HOST_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper()))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public OkHttpClient okHttpClient(ClientIDInterceptor clientIDInterceptor, DeviceIDInterceptor deviceIDInterceptor) {

        return new OkHttpClient.Builder()
                .addNetworkInterceptor(clientIDInterceptor)
                .addNetworkInterceptor(deviceIDInterceptor)
                .build();
    }

    private ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }
}
