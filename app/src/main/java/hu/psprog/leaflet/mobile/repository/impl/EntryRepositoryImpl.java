package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.OrderBy;
import hu.psprog.leaflet.mobile.model.OrderDirection;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import hu.psprog.leaflet.mobile.repository.impl.adapter.EntryAdapter;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Named;
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

    private EntryAdapter entryLocalCacheAdapter;
    private EntryAdapter cachingEntryNetworkRequestAdapter;
    private OfflineFirstCallFactory offlineFirstCallFactory;

    @Inject
    public EntryRepositoryImpl(@Named("entryLocalCacheAdapter") EntryAdapter entryLocalCacheAdapter,
                               @Named("cachingEntryNetworkRequestAdapter") EntryAdapter cachingEntryNetworkRequestAdapter,
                               OfflineFirstCallFactory offlineFirstCallFactory) {
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
                () -> entryLocalCacheAdapter.getPageOfEntries(page, DEFAULT_LIMIT, DEFAULT_ORDER_BY, DEFAULT_ORDER_DIRECTION),
                () -> cachingEntryNetworkRequestAdapter.getPageOfEntries(page, DEFAULT_LIMIT, DEFAULT_ORDER_BY, DEFAULT_ORDER_DIRECTION));
    }

    @Override
    public Observable<EntrySummaryPage> getPageOfEntriesByCategory(int page, Category category) {
        return offlineFirstCallFactory.create(
                () -> entryLocalCacheAdapter.getPageOfEntriesByCategory(page, DEFAULT_LIMIT, DEFAULT_ORDER_BY, DEFAULT_ORDER_DIRECTION, category),
                () -> cachingEntryNetworkRequestAdapter.getPageOfEntriesByCategory(page, DEFAULT_LIMIT, DEFAULT_ORDER_BY, DEFAULT_ORDER_DIRECTION, category));
    }
}
