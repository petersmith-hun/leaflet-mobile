package hu.psprog.leaflet.mobile.repository.impl.adapter;

import hu.psprog.leaflet.mobile.model.DocumentDetails;

import java.util.Optional;

/**
 * Adapter to provide document information from different sources.
 *
 * @author Peter Smith
 */
public interface DocumentAdapter {

    /**
     * Returns requested {@link DocumentDetails} identified by given link.
     *
     * @param link link of the requests document as String
     * @return DocumentDetails wrapped as {@link Optional}
     */
    Optional<DocumentDetails> getDocument(String link);
}
