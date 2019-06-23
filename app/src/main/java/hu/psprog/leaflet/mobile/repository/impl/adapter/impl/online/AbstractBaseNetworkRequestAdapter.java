package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online;

import retrofit2.Call;
import retrofit2.Response;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Abstract base implementation for online adapter implementations,
 * that calls Leaflet backend application via Retrofit REST client implementations to retrieve data.
 *
 * @author Peter Smith
 */
abstract class AbstractBaseNetworkRequestAdapter {

    private static final String BACKEND_RESPONSE_NON_SUCCESSFUL_MESSAGE = "Backend response was %d";
    private static final int HTTP_STATUS_NOT_FOUND = 404;

    /**
     * Calls backend via the specified Retrofit {@link Call} supplier.
     * In case of successful call, mapping is also done.
     *
     * @param callSupplier Retrofit {@link Call} supplier
     * @param mapperFunction mapper function to convert raw response to the expected type
     * @param <S> source (raw response) type
     * @param <T> target (converted) response type
     * @return converted response as an object of type T
     */
    <S, T> T callBackend(Supplier<Call<S>> callSupplier, Function<S, T> mapperFunction) {

        Response<S> callResult;
        try {
            callResult = callSupplier.get().execute();
        } catch (Exception e) {
            throw new RuntimeException("Failed to reach API service.", e);
        }

        return extractResult(mapperFunction, callResult);
    }

    private <S, T> T extractResult(Function<S, T> mapperFunction, Response<S> callResult) {

        T convertedResult = null;
        if (callResult.isSuccessful()) {
            convertedResult = mapperFunction.apply(callResult.body());
        } else if (!isResourceNotFound(callResult)) {
            throw new RuntimeException(String.format(BACKEND_RESPONSE_NON_SUCCESSFUL_MESSAGE, callResult.code()));
        }

        return convertedResult;
    }

    private <S> boolean isResourceNotFound(Response<S> callResult) {
        return callResult.code() == HTTP_STATUS_NOT_FOUND;
    }
}
