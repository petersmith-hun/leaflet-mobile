package hu.psprog.leaflet.mobile.repository;

import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import io.reactivex.Observable;

/**
 * Repository interface for accessing entry data.
 * As accessing data might require network communication, every repository method
 * returns requested data wrapped as {@link Observable}.
 *
 * @author Peter Smith
 */
public interface EntryRepository extends Repository {

    /**
     * Returns entry as {@link EntryDetails} identified by given link.
     *
     * @param link link as the ID of the entry
     * @return identified {@link EntryDetails} as {@link Observable}
     */
    Observable<EntryDetails> getEntry(String link);

    /**
     * Returns page of entry summaries as {@link EntrySummaryPage}.
     *
     * @param page number of page to retrieve
     * @return requested {@link EntrySummaryPage}
     */
    Observable<EntrySummaryPage> getPageOfEntries(int page);

    /**
     * Returns page of entry summaries as {@link EntrySummaryPage} filtered by given {@link Category}.
     *
     * @param page number of page to retrieve
     * @param category category to filter entries by
     * @return requested {@link EntrySummaryPage}
     */
    Observable<EntrySummaryPage> getPageOfEntriesByCategory(int page, Category category);
}
