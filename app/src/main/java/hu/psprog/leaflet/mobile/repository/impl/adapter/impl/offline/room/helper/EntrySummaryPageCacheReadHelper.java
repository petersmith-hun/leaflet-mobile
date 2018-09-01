package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.helper;

import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.EntrySummarySummaryPageJoin;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.EntrySummaryDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.EntrySummaryPageDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Peter Smith
 */
@Singleton
public class EntrySummaryPageCacheReadHelper {

    private EntrySummaryDAO entrySummaryDAO;
    private EntrySummaryPageDAO entrySummaryPageDAO;

    @Inject
    public EntrySummaryPageCacheReadHelper(LeafletLocalCacheDatabase leafletLocalCacheDatabase) {
        this.entrySummaryDAO = leafletLocalCacheDatabase.entrySummaryDAO();
        this.entrySummaryPageDAO = leafletLocalCacheDatabase.entrySummaryPageDAO();
    }

    public EntrySummaryPage getPage(int page, long categoryID) {

        return entrySummaryPageDAO.findEntrySummaryPage(page, categoryID)
                .map(this::rebuildPageWithSummaryList)
                .orElse(null);
    }

    private EntrySummaryPage rebuildPageWithSummaryList(EntrySummaryPage entrySummaryPage) {

        return EntrySummaryPage.getBuilder()
                .withCategoryID(entrySummaryPage.getCategoryID())
                .withPage(entrySummaryPage.getPage())
                .withHasNext(entrySummaryPage.hasNext())
                .withHasPrevious(entrySummaryPage.hasPrevious())
                .withEntrySummaryList(getEntrySummaries(entrySummaryPage.getPage(), entrySummaryPage.getCategoryID()))
                .build();
    }

    private List<EntrySummary> getEntrySummaries(int page, long categoryID) {

        return entrySummaryPageDAO.getSummaryPageJoins(page, categoryID).stream()
                .map(EntrySummarySummaryPageJoin::getLink)
                .map(entrySummaryDAO::findEntrySummary)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
