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
 * @author Peter Smith
 */
@Singleton
public class OfflineFirstCallFactory {

    private ObservableFactory observableFactory;

    @Inject
    public OfflineFirstCallFactory(ObservableFactory observableFactory) {
        this.observableFactory = observableFactory;
    }

    public <T extends Serializable> Observable<T> create(Supplier<T> offlineCall, Supplier<T> onlineCall) {
        return create(offlineCall, onlineCall, Objects::isNull);
    }

    public <T extends Serializable> Observable<T> create(Supplier<T> offlineCall, Supplier<T> onlineCall, Predicate<T> fallbackPredicate) {
        return observableFactory.create(() -> {

            T result = offlineCall.get();
            if (fallbackPredicate.test(result)) {
                result = onlineCall.get();
            }

            return result;
        });
    }

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
