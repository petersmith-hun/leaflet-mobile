package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching;

import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.impl.adapter.DocumentAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.DocumentDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.DocumentNetworkRequestAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * @author Peter Smith
 */
@Singleton
public class CachingDocumentNetworkRequestAdapter implements DocumentAdapter {

    private DocumentNetworkRequestAdapter documentNetworkRequestAdapter;
    private DocumentDAO documentDAO;

    @Inject
    public CachingDocumentNetworkRequestAdapter(DocumentNetworkRequestAdapter documentNetworkRequestAdapter, LeafletLocalCacheDatabase leafletLocalCacheDatabase) {
        this.documentNetworkRequestAdapter = documentNetworkRequestAdapter;
        this.documentDAO = leafletLocalCacheDatabase.documentDAO();
    }

    @Override
    public Optional<DocumentDetails> getDocument(String link) {

        Optional<DocumentDetails> optionalDocumentDetails = documentNetworkRequestAdapter.getDocument(link);
        optionalDocumentDetails.ifPresent(documentDAO::insert);

        return optionalDocumentDetails;
    }
}
