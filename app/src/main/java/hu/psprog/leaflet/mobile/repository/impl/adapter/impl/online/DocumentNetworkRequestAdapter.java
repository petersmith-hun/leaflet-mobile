package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online;

import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.document.DocumentDataModel;
import hu.psprog.leaflet.bridge.service.DocumentBridgeService;
import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.conversion.impl.DocumentConverter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.DocumentAdapter;
import hu.psprog.leaflet.mobile.util.logging.LogUtility;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * {@link DocumentAdapter} implementation for online data sources.
 * This implementation retrieves data from Leaflet backend via Bridge.
 *
 * @author Peter Smith
 */
@Singleton
public class DocumentNetworkRequestAdapter extends BridgeCallerNetworkRequestAdapter implements DocumentAdapter {

    private static final String LOG_TAG = "document_adapter::online";

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
            LogUtility.debug(LOG_TAG, "Requesting document '%s' from API service", link);
            WrapperBodyDataModel<DocumentDataModel> response = documentBridgeService.getDocumentByLink(link);
            return documentConverter.convert(response);
        }));
    }
}
