package hu.psprog.leaflet.mobile.config.dagger.module;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hu.psprog.leaflet.mobile.service.LocalCacheUpdaterService;
import hu.psprog.leaflet.mobile.service.impl.LocalCacheUpdaterServiceImpl;
import hu.psprog.leaflet.mobile.view.activity.MainActivity;
import hu.psprog.leaflet.mobile.view.fragment.DocumentDetailsFragment;
import hu.psprog.leaflet.mobile.view.fragment.EntryDetailsFragment;
import hu.psprog.leaflet.mobile.view.fragment.EntryListFragment;

/**
 * Dagger module to configure injection contribution for activities.
 *
 * @author Peter Smith
 */
@Module
public abstract class LeafletMobileApplicationModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract DocumentDetailsFragment documentDetailsFragment();

    @ContributesAndroidInjector
    abstract EntryDetailsFragment entryDetailsFragment();

    @ContributesAndroidInjector
    abstract EntryListFragment entryListFragment();

    @Binds
    public abstract LocalCacheUpdaterService bindLocalCacheUpdaterService(LocalCacheUpdaterServiceImpl localCacheUpdaterService);
}
