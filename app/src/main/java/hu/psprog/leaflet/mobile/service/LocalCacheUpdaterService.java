package hu.psprog.leaflet.mobile.service;

import io.reactivex.Observable;

/**
 * Non-background service for updating local content cache.
 *
 * @author Peter Smith
 */
public interface LocalCacheUpdaterService {

    /**
     * Updates local cache if needed.
     * With {@code true} passed to the {@code forced} parameter, local cache is forcibly updated.
     *
     * @param forced request forced update with {@code true} value
     * @return observable with {@code true} value on success, or {@code false} if no update happened
     */
    Observable<Boolean> update(boolean forced);
}
