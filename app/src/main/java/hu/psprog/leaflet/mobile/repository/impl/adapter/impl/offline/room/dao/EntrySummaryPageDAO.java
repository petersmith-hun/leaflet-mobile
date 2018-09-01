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
 * @author Peter Smith
 */
@Dao
public interface EntrySummaryPageDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntrySummaryPage entrySummaryPage);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntrySummarySummaryPageJoin entrySummarySummaryPageJoin);

    @Query("select * from cache_entry_summary_pages where page = :page and category_id = :categoryID;")
    Optional<EntrySummaryPage> findEntrySummaryPage(int page, long categoryID);

    @Query("select * from cache_entry_summaries_summary_pages where page = :page and category_id = :categoryID;")
    List<EntrySummarySummaryPageJoin> getSummaryPageJoins(int page, long categoryID);
}
