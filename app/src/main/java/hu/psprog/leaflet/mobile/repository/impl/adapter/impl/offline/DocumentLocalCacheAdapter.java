package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline;

import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.impl.adapter.DocumentAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.DocumentDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;
import hu.psprog.leaflet.mobile.util.logging.LogUtility;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * {@link DocumentAdapter} implementation for offline data sources.
 * This implementation retrieves data from Room-based local caching solution.
 *
 * @author Peter Smith
 */
@Singleton
public class DocumentLocalCacheAdapter implements DocumentAdapter {

    private static final String LOG_TAG = "document_adapter::offline";

    private DocumentDAO documentDAO;

    @Inject
    public DocumentLocalCacheAdapter(LeafletLocalCacheDatabase leafletLocalCacheDatabase) {
        this.documentDAO = leafletLocalCacheDatabase.documentDAO();
    }

    @Override
    public Optional<DocumentDetails> getDocument(String link) {
        LogUtility.debug(LOG_TAG, "Retrieving document '%s' from local cache", link);
        return documentDAO.findDocument(link);
    }
}
