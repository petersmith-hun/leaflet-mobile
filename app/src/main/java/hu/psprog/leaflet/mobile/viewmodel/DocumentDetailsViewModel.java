package hu.psprog.leaflet.mobile.viewmodel;

import android.arch.lifecycle.ViewModel;
import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.DocumentRepository;
import io.reactivex.Observable;

/**
 * {@link ViewModel} implementation for details details operation.
 *
 * @author Peter Smith
 */
public class DocumentDetailsViewModel extends ViewModel {

    private DocumentRepository documentRepository;

    public DocumentDetailsViewModel(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Observable<DocumentDetails> getDocument(String link) {
        return documentRepository.getDocument(link);
    }
}
