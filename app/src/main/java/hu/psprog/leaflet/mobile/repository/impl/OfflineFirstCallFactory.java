package hu.psprog.leaflet.mobile.repository.impl;

import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Factory to create a proper offline-first call chain.
 * Offline-first call means, that a supplier is called for a response, which we assume is originated from an offline source (local cache).
 * If that call cannot give a proper result, then we try an other source, assuming it's an online, up-to-date source (network call).
 *
 * @author Peter Smith
 */
@Singleton
public class OfflineFirstCallFactory {

    private ObservableFactory observableFactory;

    @Inject
    public OfflineFirstCallFactory(ObservableFactory observableFactory) {
        this.observableFactory = observableFactory;
    }

    /**
     * Creates an offline-first call chain with default fallback predicate.
     *
     * Steps done by the created call chain:
     *  1) Offline-call supplier gets called.
     *  2) Default fallback predicate is evaluated on the result of the offline call supplier.
     *     The default fallback predicate is only checking, if the result object is null or not.
     *  2/a) In case of the result object not being null, it is returned.
     *  2/b) Otherwise the online-call supplier gets called, and regardless its response, it is returned.
     *
     * @param offlineCall offline-call supplier, a response supplier, that is assumed to be originated from a local (cache) source
     * @param onlineCall online-call supplier, a response supplier, that is assumed to be originated from a remote (network) source
     * @param <T> T type of response
     * @return generated response of type T wrapped as Observable
     */
    public <T extends Serializable> Observable<T> create(Supplier<T> offlineCall, Supplier<T> onlineCall) {
        return create(offlineCall, onlineCall, Objects::isNull);
    }

    /**
     * Creates an offline-first call chain with the specified fallback predicate.
     *
     * Steps done by the created call chain:
     *  1) Offline-call supplier gets called.
     *  2) Specified fallback predicate is evaluated on the result of the offline call supplier.
     *  2/a) In case of the predicate being true, it is returned.
     *  2/b) Otherwise the online-call supplier gets called, and regardless its response, it is returned.
     *
     * @param offlineCall offline-call supplier, a response supplier, that is assumed to be originated from a local (cache) source
     * @param onlineCall online-call supplier, a response supplier, that is assumed to be originated from a remote (network) source
     * @param fallbackPredicate custom fallback predicate, which is evaluated to decide if the online-call supplier is needed to be called
     * @param <T> T type of response
     * @return generated response of type T wrapped as Observable
     */
    public <T extends Serializable> Observable<T> create(Supplier<T> offlineCall, Supplier<T> onlineCall, Predicate<T> fallbackPredicate) {
        return observableFactory.create(() -> {

            T result = offlineCall.get();
            if (fallbackPredicate.test(result)) {
                result = onlineCall.get();
            }

            return result;
        });
    }

    /**
     * Creates an offline-first call chain with optional-driven fallback strategy.
     * Strict behavior means, we always expect a result, and if we do not get it, an exception will be thrown.
     *
     * Steps done by the created call chain:
     *  1) Offline-call supplier gets called.
     *  2) Returned offline-call supplier response is checked.
     *  2/a) If it's present, response is returned.
     *  2/b) Otherwise online-call supplier gets called.
     *  3) Returned online-call supplier response is checked.
     *  3/a) If it's present, response is returned.
     *  3/b) Otherwise a runtime exception is thrown.
     *
     * @param offlineCall offline-call supplier, a response supplier, that is assumed to be originated from a local (cache) source
     * @param onlineCall online-call supplier, a response supplier, that is assumed to be originated from a remote (network) source
     * @param <T> T type of response
     * @return generated response of type T wrapped as Observable
     */
    public <T extends Serializable> Observable<T> createStrict(Supplier<Optional<T>> offlineCall, Supplier<Optional<T>> onlineCall) {
        return observableFactory.create(() -> offlineCall.get()
                .orElseGet(() -> onlineCall.get()
                        .orElseGet(getNotFoundSupplier())));
    }

    private <T extends Serializable> Supplier<T> getNotFoundSupplier() {
        return () -> {
            throw new RuntimeException("Requested item not found");
        };
    }
}
