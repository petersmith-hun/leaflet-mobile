package hu.psprog.leaflet.mobile.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.function.Supplier;

/**
 * {@link ViewModel} implementation for entry listing operations.
 *
 * @author Peter Smith
 */
public class EntryListViewModel extends ViewModel {

    private final MutableLiveData<EntrySummaryPage> entrySummaryPage = new MutableLiveData<>();
    private final EntryRepository entryRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public EntryListViewModel(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public void loadEntryPage(int page) {
        disposables.add(subscribe(() -> entryRepository.getPageOfEntries(page)));
    }

    public void loadEntryPageByCategory(int page, long categoryID) {
        disposables.add(subscribe(() -> entryRepository.getPageOfEntriesByCategory(page, buildCategory(categoryID))));
    }

    public LiveData<EntrySummaryPage> getEntrySummaryPage() {
        return entrySummaryPage;
    }

    private Category buildCategory(long categoryID) {
        return Category.getBuilder()
                .withId(categoryID)
                .build();
    }

    private Disposable subscribe(Supplier<Observable<EntrySummaryPage>> supplier) {
        return supplier.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnSubscribe(disposable -> ...) todo command view to show loading indicator
                .subscribe(entrySummaryPage::setValue, throwable -> {}); // todo do something better
    }
}
