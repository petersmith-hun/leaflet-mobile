package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching.helper;

import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.EntrySummarySummaryPageJoin;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.EntrySummaryDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.EntrySummaryPageDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Helper class for storing a response in the local cache.
 *
 * @author Peter Smith
 */
@Singleton
public class EntrySummaryPageCacheWriteHelper {

    private EntrySummaryDAO entrySummaryDAO;
    private EntrySummaryPageDAO entrySummaryPageDAO;

    @Inject
    public EntrySummaryPageCacheWriteHelper(LeafletLocalCacheDatabase leafletLocalCacheDatabase) {
        this.entrySummaryDAO = leafletLocalCacheDatabase.entrySummaryDAO();
        this.entrySummaryPageDAO = leafletLocalCacheDatabase.entrySummaryPageDAO();
    }

    /**
     * Stores given {@link EntrySummaryPage} in the local cache.
     *
     * Steps done by the implementation:
     *  1) Stores the entry summaries appear in this summary page.
     *  2) Stores summary page meta information.
     *  3) Creates join entries between summary and summary page records.
     *
     * @param entrySummaryPage {@link EntrySummaryPage} to store
     */
    public void storePage(EntrySummaryPage entrySummaryPage) {
        saveSummaryList(entrySummaryPage);
        saveSummaryPageMetaInfo(entrySummaryPage);
        joinSummaryPagesAndSummaries(entrySummaryPage);
    }

    private void saveSummaryList(EntrySummaryPage entrySummaryPage) {
        entrySummaryDAO.insertAll(entrySummaryPage.getEntrySummaryList());
    }

    private void saveSummaryPageMetaInfo(EntrySummaryPage entrySummaryPage) {
        entrySummaryPageDAO.insert(entrySummaryPage);
    }

    private void joinSummaryPagesAndSummaries(EntrySummaryPage entrySummaryPage) {
        AtomicInteger index = new AtomicInteger(0);
        entrySummaryPage.getEntrySummaryList().forEach(entrySummary -> {
            EntrySummarySummaryPageJoin join = buildEntrySummarySummaryPageJoin(entrySummaryPage, entrySummary, index.getAndIncrement());
            entrySummaryPageDAO.insert(join);
        });
    }

    private EntrySummarySummaryPageJoin buildEntrySummarySummaryPageJoin(EntrySummaryPage entrySummaryPage, EntrySummary entrySummary, int order) {
        return EntrySummarySummaryPageJoin.getBuilder()
                .withCategoryID(entrySummaryPage.getCategoryID())
                .withPage(entrySummaryPage.getPage())
                .withLink(entrySummary.getLink())
                .withOrder(order)
                .build();
    }
}
