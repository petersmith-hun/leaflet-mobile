package hu.psprog.leaflet.mobile.repository.impl;

import io.reactivex.Observable;

import java.io.Serializable;

/**
 * @author Peter Smith
 */
class ObservableFactory {

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
