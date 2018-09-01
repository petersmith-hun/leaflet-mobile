package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import hu.psprog.leaflet.mobile.model.DocumentDetails;

import java.util.Optional;

/**
 * @author Peter Smith
 */
@Dao
public interface DocumentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DocumentDetails documentDetails);

    @Query("select * from cache_document_details where link = :link")
    Optional<DocumentDetails> findDocument(String link);
}
