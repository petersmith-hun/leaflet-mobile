package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import hu.psprog.leaflet.mobile.model.EntryDetails;

import java.util.Optional;

/**
 * @author Peter Smith
 */
@Dao
public interface EntryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntryDetails entryDetails);

    @Query("select * from cache_entry_details where link = :link;")
    Optional<EntryDetails> findEntry(String link);
}
