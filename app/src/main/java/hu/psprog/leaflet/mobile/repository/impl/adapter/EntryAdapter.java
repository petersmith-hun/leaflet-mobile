package hu.psprog.leaflet.mobile.repository.impl.adapter;

import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.OrderBy;
import hu.psprog.leaflet.mobile.model.OrderDirection;

import java.util.Optional;

/**
 * Adapter to provide entry information from different sources.
 *
 * @author Peter Smith
 */
public interface EntryAdapter {

    /**
     * Returns {@link EntryDetails} identified by given link.
     *
     * @param link link of the requested entry as String
     * @return EntryDetails wrapped as {@link Optional}
     */
    Optional<EntryDetails> getEntry(String link);

    /**
     * Returns a page of entry summaries according to the given pagination settings.
     *
     * @param page page number
     * @param limit maximum number of entry summaries on a single page
     * @param orderBy order by field
     * @param orderDirection order direction
     * @return populated {@link EntrySummaryPage}
     */
    EntrySummaryPage getPageOfEntries(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection);

    /**
     * Returns a page of entry summaries according to the given pagination settings filtered by given category.
     *
     * @param page page number
     * @param limit maximum number of entry summaries on a single page
     * @param orderBy order by field
     * @param orderDirection order direction
     * @param category category to filter by
     * @return populated {@link EntrySummaryPage}
     */
    EntrySummaryPage getPageOfEntriesByCategory(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection, Category category);
}
