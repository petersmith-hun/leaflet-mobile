package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import hu.psprog.leaflet.mobile.model.DocumentDetails;

import java.util.Optional;

/**
 * DAO for {@link DocumentDetails} operations.
 *
 * @author Peter Smith
 */
@Dao
public interface DocumentDAO {

    /**
     * Stores given {@link DocumentDetails} object.
     *
     * @param documentDetails {@link DocumentDetails} object to store
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DocumentDetails documentDetails);

    /**
     * Retrieves stored {@link DocumentDetails} identified by given link.
     *
     * @param link link to identify {@link DocumentDetails}
     * @return identified {@link DocumentDetails} as Optional, or empty Optional if not present
     */
    @Query("select * from cache_document_details where link = :link;")
    Optional<DocumentDetails> findDocument(String link);
}
