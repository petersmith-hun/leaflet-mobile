package hu.psprog.leaflet.mobile.repository;

import hu.psprog.leaflet.mobile.model.DocumentDetails;
import io.reactivex.Observable;

/**
 * Repository interface for accessing document data.
 * As accessing data might require network communication, every repository method
 * returns requested data wrapped as {@link Observable}.
 *
 * @author Peter Smith
 */
public interface DocumentRepository extends Repository {

    /**
     * Returns document as {@link DocumentDetails} identified by given link.
     *
     * @param link link as the ID of the document
     * @return identified {@link DocumentDetails} as {@link Observable}
     */
    Observable<DocumentDetails> getDocument(String link);
}
