package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import hu.psprog.leaflet.mobile.model.EntryDetails;

import java.util.Optional;

/**
 * DAO for {@link EntryDetails} operations.
 *
 * @author Peter Smith
 */
@Dao
public interface EntryDAO {

    /**
     * Stores given {@link EntryDetails} object.
     *
     * @param entryDetails {@link EntryDetails} object to store
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntryDetails entryDetails);

    /**
     * Retrieves stored {@link EntryDetails} object identified by given link.
     *
     * @param link link to identify {@link EntryDetails}
     * @return identified {@link EntryDetails} as Optional, or empty Optional if not present
     */
    @Query("select * from cache_entry_details where link = :link;")
    Optional<EntryDetails> findEntry(String link);
}
