package hu.psprog.leaflet.mobile.viewmodel;

import android.arch.lifecycle.ViewModel;
import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import hu.psprog.leaflet.mobile.repository.impl.DummyEntryRepositoryImpl;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.function.Supplier;

/**
 * {@link ViewModel} implementation for entry listing operations.
 *
 * @author Peter Smith
 */
public class EntryListViewModel extends ViewModel {

    private final EntryRepository entryRepository;

    public EntryListViewModel() {
        // TODO configure Dagger DI
        this.entryRepository = new DummyEntryRepositoryImpl();
    }

    public Observable<EntrySummaryPage> getEntrySummaryPage(int page) {
        return createObservable(() -> entryRepository.getPageOfEntries(page));
    }

    public Observable<EntrySummaryPage> getEntrySummaryPageByCategory(int page, long categoryID) {
        return createObservable(() -> entryRepository.getPageOfEntriesByCategory(page, buildCategory(categoryID)));
    }

    private Category buildCategory(long categoryID) {
        return Category.getBuilder()
                .withId(categoryID)
                .build();
    }

    private Observable<EntrySummaryPage> createObservable(Supplier<Observable<EntrySummaryPage>> supplier) {
        return supplier.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
