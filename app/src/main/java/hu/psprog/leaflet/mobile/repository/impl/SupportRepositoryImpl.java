package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.mobile.repository.SupportRepository;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Implementation of {@link SupportRepository}.
 *
 * @author Peter Smith
 */
@Singleton
public class SupportRepositoryImpl implements SupportRepository {

    private LeafletLocalCacheDatabase leafletLocalCacheDatabase;
    private ObservableFactory observableFactory;

    @Inject
    public SupportRepositoryImpl(LeafletLocalCacheDatabase leafletLocalCacheDatabase, ObservableFactory observableFactory) {
        this.leafletLocalCacheDatabase = leafletLocalCacheDatabase;
        this.observableFactory = observableFactory;
    }

    @Override
    public Observable<Boolean> clearLocalCache() {

        return observableFactory.create(() -> {
            leafletLocalCacheDatabase.clearAllTables();
            return true;
        });
    }
}
