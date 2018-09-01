package hu.psprog.leaflet.mobile.repository.impl.adapter;

import hu.psprog.leaflet.mobile.model.DocumentDetails;

import java.util.Optional;

/**
 * @author Peter Smith
 */
public interface DocumentAdapter {

    Optional<DocumentDetails> getDocument(String link);
}
