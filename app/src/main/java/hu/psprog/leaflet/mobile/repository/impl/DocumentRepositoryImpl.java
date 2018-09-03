package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.DocumentRepository;
import hu.psprog.leaflet.mobile.repository.impl.adapter.DocumentAdapter;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Implementation of {@link DocumentRepository}.
 *
 * @author Peter Smith
 */
@Singleton
public class DocumentRepositoryImpl implements DocumentRepository {

    private DocumentAdapter documentLocalCacheAdapter;
    private DocumentAdapter cachingDocumentNetworkRequestAdapter;
    private OfflineFirstCallFactory offlineFirstCallFactory;

    @Inject
    public DocumentRepositoryImpl(@Named("documentLocalCacheAdapter") DocumentAdapter documentLocalCacheAdapter,
                                  @Named("cachingDocumentNetworkRequestAdapter") DocumentAdapter cachingDocumentNetworkRequestAdapter,
                                  OfflineFirstCallFactory offlineFirstCallFactory) {
        this.offlineFirstCallFactory = offlineFirstCallFactory;
        this.documentLocalCacheAdapter = documentLocalCacheAdapter;
        this.cachingDocumentNetworkRequestAdapter = cachingDocumentNetworkRequestAdapter;
    }

    @Override
    public Observable<DocumentDetails> getDocument(String link) {
        return offlineFirstCallFactory.createStrict(
                () -> documentLocalCacheAdapter.getDocument(link),
                () -> cachingDocumentNetworkRequestAdapter.getDocument(link));
    }
}
