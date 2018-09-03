package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import hu.psprog.leaflet.mobile.model.EntrySummary;

import java.util.List;

/**
 * DAO for {@link EntrySummary} operations.
 *
 * @author Peter Smith
 */
@Dao
public interface EntrySummaryDAO {

    /**
     * Stores given list of {@link EntrySummary} objects.
     *
     * @param entrySummaryList {@link List} of {@link EntrySummary} objects to store
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EntrySummary> entrySummaryList);

    /**
     * Returns stored {@link EntrySummary} identified by given link.
     *
     * @param link link to identify {@link EntrySummary}
     * @return identified {@link EntrySummary} or null if not present
     */
    @Query("select * from cache_entry_summaries where link = :link;")
    EntrySummary findEntrySummary(String link);
}
