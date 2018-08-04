package hu.psprog.leaflet.mobile.viewmodel;

import android.arch.lifecycle.ViewModel;
import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.DocumentRepository;
import hu.psprog.leaflet.mobile.repository.impl.DummyDocumentRepositoryImpl;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * {@link ViewModel} implementation for details details operation.
 *
 * @author Peter Smith
 */
public class DocumentDetailsViewModel extends ViewModel {

    private DocumentRepository documentRepository;

    public DocumentDetailsViewModel() {
        // TODO configure Dagger DI
        this.documentRepository = new DummyDocumentRepositoryImpl();
    }

    public Observable<DocumentDetails> getDocument(String link) {
        return documentRepository.getDocument(link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
