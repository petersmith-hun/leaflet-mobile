package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online;

import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.document.DocumentDataModel;
import hu.psprog.leaflet.bridge.service.DocumentBridgeService;
import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.conversion.impl.DocumentConverter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.DocumentAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * @author Peter Smith
 */
@Singleton
public class DocumentNetworkRequestAdapter extends BridgeCallerNetworkRequestAdapter implements DocumentAdapter {

    private DocumentBridgeService documentBridgeService;
    private DocumentConverter documentConverter;

    @Inject
    public DocumentNetworkRequestAdapter(DocumentBridgeService documentBridgeService, DocumentConverter documentConverter) {
        this.documentBridgeService = documentBridgeService;
        this.documentConverter = documentConverter;
    }

    @Override
    public Optional<DocumentDetails> getDocument(String link) {

        return Optional.ofNullable(callBridge(() -> {
            WrapperBodyDataModel<DocumentDataModel> response = documentBridgeService.getDocumentByLink(link);
            return documentConverter.convert(response);
        }));
    }
}
