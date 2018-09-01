package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import hu.psprog.leaflet.mobile.model.EntrySummary;

import java.util.List;

/**
 * @author Peter Smith
 */
@Dao
public interface EntrySummaryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EntrySummary> entrySummaryList);

    @Query("select * from cache_entry_summaries where link = :link;")
    EntrySummary findEntrySummary(String link);
}
