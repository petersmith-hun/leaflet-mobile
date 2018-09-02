package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.EntrySummarySummaryPageJoin;

import java.util.List;
import java.util.Optional;

/**
 * DAO for {@link EntrySummaryPage} operations.
 *
 * @author Peter Smith
 */
@Dao
public interface EntrySummaryPageDAO {

    /**
     * Stores given {@link EntrySummaryPage}.
     *
     * @param entrySummaryPage {@link EntrySummaryPage} object to store
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntrySummaryPage entrySummaryPage);

    /**
     * Stores given {@link EntrySummarySummaryPageJoin} object.
     * This object specifies the many-to-many relations between EntrySummaryPage and EntrySummary records.
     *
     * @param entrySummarySummaryPageJoin {@link EntrySummarySummaryPageJoin} object to store
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntrySummarySummaryPageJoin entrySummarySummaryPageJoin);

    /**
     * Returns a stored {@link EntrySummaryPage} for given page and category ID.
     *
     * @param page page number
     * @param categoryID category ID
     * @return stored {@link EntrySummaryPage} or empty optional if not present
     */
    @Query("select * from cache_entry_summary_pages where page = :page and category_id = :categoryID;")
    Optional<EntrySummaryPage> findEntrySummaryPage(int page, long categoryID);

    /**
     * Returns a stored {@link EntrySummarySummaryPageJoin} for given page and category ID.
     *
     * @param page page number
     * @param categoryID category ID
     * @return stored {@link EntrySummarySummaryPageJoin} or empty optional if not present
     */
    @Query("select * from cache_entry_summaries_summary_pages where page = :page and category_id = :categoryID;")
    List<EntrySummarySummaryPageJoin> getSummaryPageJoins(int page, long categoryID);
}
