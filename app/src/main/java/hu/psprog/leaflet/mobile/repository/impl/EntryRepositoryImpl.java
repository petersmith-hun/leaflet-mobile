package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.OrderBy;
import hu.psprog.leaflet.mobile.model.OrderDirection;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.EntryLocalCacheAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching.CachingEntryNetworkRequestAdapter;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Implementation of {@link EntryRepository}.
 *
 * @author Peter Smith
 */
@Singleton
public class EntryRepositoryImpl implements EntryRepository {

    private static final int DEFAULT_LIMIT = 10;
    private static final OrderBy.Entry DEFAULT_ORDER_BY = OrderBy.Entry.CREATED;
    private static final OrderDirection DEFAULT_ORDER_DIRECTION = OrderDirection.DESC;

    private EntryLocalCacheAdapter entryLocalCacheAdapter;
    private CachingEntryNetworkRequestAdapter cachingEntryNetworkRequestAdapter;
    private OfflineFirstCallFactory offlineFirstCallFactory;

    @Inject
    public EntryRepositoryImpl(EntryLocalCacheAdapter entryLocalCacheAdapter, CachingEntryNetworkRequestAdapter cachingEntryNetworkRequestAdapter,
                               OfflineFirstCallFactory offlineFirstCallFactory) { // TODO fix dependencies (change to interfaces)
        this.entryLocalCacheAdapter = entryLocalCacheAdapter;
        this.cachingEntryNetworkRequestAdapter = cachingEntryNetworkRequestAdapter;
        this.offlineFirstCallFactory = offlineFirstCallFactory;
    }

    @Override
    public Observable<EntryDetails> getEntry(String link) {
        return offlineFirstCallFactory.createStrict(
                () -> entryLocalCacheAdapter.getEntry(link),
                () -> cachingEntryNetworkRequestAdapter.getEntry(link));
    }

    @Override
    public Observable<EntrySummaryPage> getPageOfEntries(int page) {
        return offlineFirstCallFactory.create(
                () -> null,
                () -> cachingEntryNetworkRequestAdapter.getPageOfEntries(page, DEFAULT_LIMIT, DEFAULT_ORDER_BY, DEFAULT_ORDER_DIRECTION));
    }

    @Override
    public Observable<EntrySummaryPage> getPageOfEntriesByCategory(int page, Category category) {
        return offlineFirstCallFactory.create(
                () -> null,
                () -> cachingEntryNetworkRequestAdapter.getPageOfEntriesByCategory(page, DEFAULT_LIMIT, DEFAULT_ORDER_BY, DEFAULT_ORDER_DIRECTION, category));
    }
}
