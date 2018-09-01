package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching.helper;

import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.EntrySummarySummaryPageJoin;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.EntrySummaryDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.EntrySummaryPageDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
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
        entrySummaryPage.getEntrySummaryList().forEach(entrySummary -> {
            EntrySummarySummaryPageJoin join = buildEntrySummarySummaryPageJoin(entrySummaryPage, entrySummary);
            entrySummaryPageDAO.insert(join);
        });
    }

    private EntrySummarySummaryPageJoin buildEntrySummarySummaryPageJoin(EntrySummaryPage entrySummaryPage, EntrySummary entrySummary) {
        return EntrySummarySummaryPageJoin.getBuilder()
                .withCategoryID(entrySummaryPage.getCategoryID())
                .withPage(entrySummaryPage.getPage())
                .withLink(entrySummary.getLink())
                .build();
    }
}
