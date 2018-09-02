package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline;

import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.OrderBy;
import hu.psprog.leaflet.mobile.model.OrderDirection;
import hu.psprog.leaflet.mobile.repository.impl.adapter.EntryAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.EntryDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.helper.EntrySummaryPageCacheReadHelper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * {@link EntryAdapter} implementation for offline data sources.
 * This implementation retrieves data from Room-based local caching solution.
 *
 * @author Peter Smith
 */
@Singleton
public class EntryLocalCacheAdapter implements EntryAdapter {

    private EntryDAO entryDAO;
    private EntrySummaryPageCacheReadHelper entrySummaryPageCacheReadHelper;

    @Inject
    public EntryLocalCacheAdapter(LeafletLocalCacheDatabase leafletLocalCacheDatabase, EntrySummaryPageCacheReadHelper entrySummaryPageCacheReadHelper) {
        this.entryDAO = leafletLocalCacheDatabase.entryDAO();
        this.entrySummaryPageCacheReadHelper = entrySummaryPageCacheReadHelper;
    }

    @Override
    public Optional<EntryDetails> getEntry(String link) {
        return entryDAO.findEntry(link);
    }

    @Override
    public EntrySummaryPage getPageOfEntries(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection) {
        return entrySummaryPageCacheReadHelper.getPage(page, 0);
    }

    @Override
    public EntrySummaryPage getPageOfEntriesByCategory(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection, Category category) {
        return entrySummaryPageCacheReadHelper.getPage(page, category.getId());
    }
}
