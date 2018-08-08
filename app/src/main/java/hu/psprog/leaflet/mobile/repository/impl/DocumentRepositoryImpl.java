package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.document.DocumentDataModel;
import hu.psprog.leaflet.bridge.service.DocumentBridgeService;
import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.DocumentRepository;
import hu.psprog.leaflet.mobile.repository.conversion.impl.DocumentConverter;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Peter Smith
 */
@Singleton
public class DocumentRepositoryImpl implements DocumentRepository {

    private ObservableFactory observableFactory;
    private DocumentBridgeService documentBridgeService;
    private DocumentConverter documentConverter;

    @Inject
    public DocumentRepositoryImpl(ObservableFactory observableFactory, DocumentBridgeService documentBridgeService,
                                  DocumentConverter documentConverter) {
        this.observableFactory = observableFactory;
        this.documentBridgeService = documentBridgeService;
        this.documentConverter = documentConverter;
    }

    @Override
    public Observable<DocumentDetails> getDocument(String link) {
        return observableFactory.create(() -> {
            WrapperBodyDataModel<DocumentDataModel> response = documentBridgeService.getDocumentByLink(link);
            return documentConverter.convert(response);
        });
    }
}
