package hu.psprog.leaflet.mobile.service.impl;

import hu.psprog.leaflet.mobile.preferences.ApplicationPreferencesProvider;
import hu.psprog.leaflet.mobile.repository.SupportRepository;
import hu.psprog.leaflet.mobile.service.LocalCacheUpdaterService;
import hu.psprog.leaflet.mobile.util.logging.LogUtility;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link LocalCacheUpdaterService}.
 *
 * @author Peter Smith
 */
@Singleton
public class LocalCacheUpdaterServiceImpl implements LocalCacheUpdaterService {

    private static final String LOG_TAG = "local_cache_update_svc";
    private static final int STALE_DATA_THRESHOLD_IN_DAYS = 7;

    private ApplicationPreferencesProvider applicationPreferencesProvider;
    private SupportRepository supportRepository;

    @Inject
    public LocalCacheUpdaterServiceImpl(ApplicationPreferencesProvider applicationPreferencesProvider, SupportRepository supportRepository) {
        this.applicationPreferencesProvider = applicationPreferencesProvider;
        this.supportRepository = supportRepository;
    }

    @Override
    public Observable<Boolean> update(boolean forced) {

        Observable<Boolean> result;
        if (forced || updateRequired()) {
            result = supportRepository.clearLocalCache();
            applicationPreferencesProvider.updateLastUpdateDate();
            LogUtility.debug(LOG_TAG, "Updated local cache with forced mode = %s", forced);
        } else {
            result = Observable.just(false);
            LogUtility.debug(LOG_TAG, "Updating local cache skipped");
        }

        return result;
    }

    private boolean updateRequired() {
        return Optional.ofNullable(applicationPreferencesProvider.getLastUpdateDate())
                .map(this::isStale)
                .orElse(false);
    }

    private boolean isStale(Date lastUpdateDate) {

        long difference = System.currentTimeMillis() - lastUpdateDate.getTime();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(difference);

        return differenceInDays >= STALE_DATA_THRESHOLD_IN_DAYS;
    }
}
