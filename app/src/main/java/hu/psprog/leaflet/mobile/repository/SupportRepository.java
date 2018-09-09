package hu.psprog.leaflet.mobile.repository;

import io.reactivex.Observable;

/**
 * Support repository operations.
 * These are application-global operations, and should be reachable from every part of the application.
 *
 * @author Peter Smith
 */
public interface SupportRepository extends Repository {

    /**
     * Clears every attached table in the local cache database.
     *
     * @return boolean operation result wrapped as {@link Observable}
     */
    Observable<Boolean> clearLocalCache();
}
