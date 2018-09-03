package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching;

import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.impl.adapter.DocumentAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.DocumentDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * {@link DocumentAdapter} implementation to store an online call's response in the local cache.
 *
 * @author Peter Smith
 */
@Singleton
public class CachingDocumentNetworkRequestAdapter implements DocumentAdapter {

    private DocumentAdapter documentNetworkRequestAdapter;
    private DocumentDAO documentDAO;

    @Inject
    public CachingDocumentNetworkRequestAdapter(@Named("documentNetworkRequestAdapter") DocumentAdapter documentNetworkRequestAdapter,
                                                LeafletLocalCacheDatabase leafletLocalCacheDatabase) {
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
