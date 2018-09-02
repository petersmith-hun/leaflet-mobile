package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching;

import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.OrderBy;
import hu.psprog.leaflet.mobile.model.OrderDirection;
import hu.psprog.leaflet.mobile.repository.impl.adapter.EntryAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.EntryDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.EntryNetworkRequestAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching.helper.EntrySummaryPageCacheWriteHelper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * {@link EntryAdapter} implementation to store an online call's response in the local cache.
 *
 * @author Peter Smith
 */
@Singleton
public class CachingEntryNetworkRequestAdapter implements EntryAdapter {

    private EntryNetworkRequestAdapter entryNetworkRequestAdapter;
    private EntrySummaryPageCacheWriteHelper entrySummaryPageCacheWriteHelper;
    private EntryDAO entryDAO;

    @Inject
    public CachingEntryNetworkRequestAdapter(EntryNetworkRequestAdapter entryNetworkRequestAdapter, EntrySummaryPageCacheWriteHelper entrySummaryPageCacheWriteHelper,
                                             LeafletLocalCacheDatabase leafletLocalCacheDatabase) {
        this.entryNetworkRequestAdapter = entryNetworkRequestAdapter;
        this.entrySummaryPageCacheWriteHelper = entrySummaryPageCacheWriteHelper;
        this.entryDAO = leafletLocalCacheDatabase.entryDAO();
    }

    @Override
    public Optional<EntryDetails> getEntry(String link) {

        Optional<EntryDetails> optionalEntryDetails = entryNetworkRequestAdapter.getEntry(link);
        optionalEntryDetails.ifPresent(entryDAO::insert);

        return optionalEntryDetails;
    }

    @Override
    public EntrySummaryPage getPageOfEntries(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection) {

        EntrySummaryPage entrySummaryPage = entryNetworkRequestAdapter.getPageOfEntries(page, limit, orderBy, orderDirection);
        entrySummaryPageCacheWriteHelper.storePage(entrySummaryPage);

        return entrySummaryPage;
    }

    @Override
    public EntrySummaryPage getPageOfEntriesByCategory(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection, Category category) {

        EntrySummaryPage entrySummaryPage = entryNetworkRequestAdapter.getPageOfEntriesByCategory(page, limit, orderBy, orderDirection, category);
        entrySummaryPageCacheWriteHelper.storePage(entrySummaryPage);

        return entrySummaryPage;
    }
}
