package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online;

import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.conversion.impl.DocumentConverter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.DocumentAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client.DocumentRESTClient;
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
public class DocumentNetworkRequestAdapter extends AbstractBaseNetworkRequestAdapter implements DocumentAdapter {

    private static final String LOG_TAG = "document_adapter::online";

    private DocumentRESTClient documentRESTClient;
    private DocumentConverter documentConverter;

    @Inject
    public DocumentNetworkRequestAdapter(DocumentRESTClient documentRESTClient, DocumentConverter documentConverter) {
        this.documentRESTClient = documentRESTClient;
        this.documentConverter = documentConverter;
    }

    @Override
    public Optional<DocumentDetails> getDocument(String link) {

        LogUtility.debug(LOG_TAG, "Requesting document '%s' from API service", link);
        DocumentDetails documentDetails = callBackend(
                () -> documentRESTClient.getDocumentByLink(link),
                documentConverter::convert);

        return Optional.ofNullable(documentDetails);
    }
}
