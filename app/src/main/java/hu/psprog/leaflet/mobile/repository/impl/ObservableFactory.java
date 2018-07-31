package hu.psprog.leaflet.mobile.repository.impl;

import io.reactivex.Observable;

import java.io.Serializable;

/**
 * Factory that wraps a supplier's result into an Observable.
 *
 * @author Peter Smith
 */
class ObservableFactory {

    /**
     * Wraps a call in an emitter based observable.
     * If an exception is thrown, the emitter will send an error event.
     *
     * @param supplier service call as {@link BridgeResultSupplier}
     * @param <T> return type of the service call (must be {@link Serializable})
     * @return created {@link Observable}
     */
    <T extends Serializable> Observable<T> create(BridgeResultSupplier<T> supplier) {

        return Observable.create(emitter -> {
            try {
                emitter.onNext(supplier.get());
            } catch (Exception exc) {
                emitter.onError(exc);
            }
        });
    }

    interface BridgeResultSupplier<T> {
        T get() throws Exception; // todo change later to CommunicationFailureException
    }
}
