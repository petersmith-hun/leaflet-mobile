package hu.psprog.leaflet.mobile.viewmodel;

import android.arch.lifecycle.ViewModel;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import io.reactivex.Observable;

/**
 * {@link ViewModel} implementation for entry details operation.
 *
 * @author Peter Smith
 */
public class EntryDetailsViewModel extends ViewModel {

    private EntryRepository entryRepository;

    public EntryDetailsViewModel(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public Observable<EntryDetails> getEntryDetails(String link) {
        return entryRepository.getEntry(link);
    }
}
