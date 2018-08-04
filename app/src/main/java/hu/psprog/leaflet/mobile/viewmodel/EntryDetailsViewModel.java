package hu.psprog.leaflet.mobile.viewmodel;

import android.arch.lifecycle.ViewModel;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import hu.psprog.leaflet.mobile.repository.impl.DummyEntryRepositoryImpl;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * {@link ViewModel} implementation for entry details operation.
 *
 * @author Peter Smith
 */
public class EntryDetailsViewModel extends ViewModel {

    private EntryRepository entryRepository;

    public EntryDetailsViewModel() {
        // TODO configure Dagger DI
        this.entryRepository = new DummyEntryRepositoryImpl();
    }

    public Observable<EntryDetails> getEntryDetails(String link) {
        return entryRepository.getEntry(link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
