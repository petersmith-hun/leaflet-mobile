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

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * @author Peter Smith
 */
@Singleton
public class CachingEntryNetworkRequestAdapter implements EntryAdapter {

    private EntryNetworkRequestAdapter entryNetworkRequestAdapter;
    private EntryDAO entryDAO;

    @Inject
    public CachingEntryNetworkRequestAdapter(EntryNetworkRequestAdapter entryNetworkRequestAdapter, LeafletLocalCacheDatabase leafletLocalCacheDatabase) {
        this.entryNetworkRequestAdapter = entryNetworkRequestAdapter;
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
        // TODO add caching

        return entrySummaryPage;
    }

    @Override
    public EntrySummaryPage getPageOfEntriesByCategory(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection, Category category) {

        EntrySummaryPage entrySummaryPage = entryNetworkRequestAdapter.getPageOfEntriesByCategory(page, limit, orderBy, orderDirection, category);
        // TODO add caching

        return entrySummaryPage;
    }
}
