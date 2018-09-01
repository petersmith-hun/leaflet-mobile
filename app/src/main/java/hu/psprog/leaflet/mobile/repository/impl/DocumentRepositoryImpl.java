package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.DocumentRepository;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.DocumentLocalCacheAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching.CachingDocumentNetworkRequestAdapter;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Implementation of {@link DocumentRepository}.
 *
 * @author Peter Smith
 */
@Singleton
public class DocumentRepositoryImpl implements DocumentRepository {

    private OfflineFirstCallFactory offlineFirstCallFactory;
    private DocumentLocalCacheAdapter documentLocalCacheAdapter;
    private CachingDocumentNetworkRequestAdapter cachingDocumentNetworkRequestAdapter;

    @Inject
    public DocumentRepositoryImpl(OfflineFirstCallFactory offlineFirstCallFactory, DocumentLocalCacheAdapter documentLocalCacheAdapter,
                                  CachingDocumentNetworkRequestAdapter cachingDocumentNetworkRequestAdapter) {
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
