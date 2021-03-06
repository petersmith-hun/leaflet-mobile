package hu.psprog.leaflet.mobile.repository.impl;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;

/**
 * Factory that wraps a supplier's result into an Observable.
 *
 * @author Peter Smith
 */
@Singleton
public class ObservableFactory {

    private static final String LOG_TAG = ObservableFactory.class.getSimpleName();

    @Inject
    public ObservableFactory() {
    }

    /**
     * Wraps a call in an emitter based observable.
     * If an exception is thrown, the emitter will send an error event.
     *
     * @param supplier service call as {@link ExceptionThrowingSupplier}
     * @param <T> return type of the service call (must be {@link Serializable})
     * @return created {@link Observable}
     */
    <T extends Serializable> Observable<T> create(ExceptionThrowingSupplier<T> supplier) {
        return Observable
                .create(createEmitter(supplier))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private <T extends Serializable> ObservableOnSubscribe<T> createEmitter(ExceptionThrowingSupplier<T> supplier) {
        return emitter -> {
            try {
                emitter.onNext(supplier.get());
            } catch (Exception exc) {
                Log.e(LOG_TAG, "Failed to get result from API server", exc);
                emitter.onError(exc);
            }
        };
    }

    interface ExceptionThrowingSupplier<T> {
        T get() throws Exception;
    }
}
