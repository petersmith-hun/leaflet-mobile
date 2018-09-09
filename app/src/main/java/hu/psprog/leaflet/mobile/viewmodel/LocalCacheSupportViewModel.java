package hu.psprog.leaflet.mobile.viewmodel;

import android.arch.lifecycle.ViewModel;
import hu.psprog.leaflet.mobile.repository.SupportRepository;
import io.reactivex.Observable;

/**
 * {@link ViewModel} implementation for repository support operations.
 *
 * @author Peter Smith
 */
public class LocalCacheSupportViewModel extends ViewModel {

    private SupportRepository supportRepository;

    public LocalCacheSupportViewModel(SupportRepository supportRepository) {
        this.supportRepository = supportRepository;
    }

    public Observable<Boolean> refresh() {
        return supportRepository.clearLocalCache();
    }
}
