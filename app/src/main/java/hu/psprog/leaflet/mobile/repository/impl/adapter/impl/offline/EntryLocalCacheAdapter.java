package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline;

import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.OrderBy;
import hu.psprog.leaflet.mobile.model.OrderDirection;
import hu.psprog.leaflet.mobile.repository.impl.adapter.EntryAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.EntryDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * @author Peter Smith
 */
@Singleton
public class EntryLocalCacheAdapter implements EntryAdapter {

    private EntryDAO entryDAO;

    @Inject
    public EntryLocalCacheAdapter(LeafletLocalCacheDatabase leafletLocalCacheDatabase) {
        this.entryDAO = leafletLocalCacheDatabase.entryDAO();
    }

    @Override
    public Optional<EntryDetails> getEntry(String link) {
        return entryDAO.findEntry(link);
    }

    @Override
    public EntrySummaryPage getPageOfEntries(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection) {
        return null;
    }

    @Override
    public EntrySummaryPage getPageOfEntriesByCategory(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection, Category category) {
        return null;
    }
}
