package hu.psprog.leaflet.mobile.config.dagger.component;

import dagger.Component;
import dagger.android.AndroidInjector;
import hu.psprog.leaflet.mobile.LeafletMobileApplication;
import hu.psprog.leaflet.mobile.config.dagger.module.ApplicationModule;
import hu.psprog.leaflet.mobile.config.dagger.module.LeafletMobileApplicationModule;
import hu.psprog.leaflet.mobile.config.dagger.module.RepositoryModule;
import hu.psprog.leaflet.mobile.config.dagger.module.RetrofitRESTClientConfigurationModule;
import hu.psprog.leaflet.mobile.config.dagger.module.SpannableConfigurationModule;
import hu.psprog.leaflet.mobile.config.dagger.module.ViewModelFactoryModule;

import javax.inject.Singleton;

/**
 * Dagger component that configures dependency injection for given modules.
 *
 * @author Peter Smith
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        RetrofitRESTClientConfigurationModule.class,
        LeafletMobileApplicationModule.class,
        RepositoryModule.class,
        SpannableConfigurationModule.class,
        ViewModelFactoryModule.class})
public interface LeafletMobileApplicationComponent extends AndroidInjector<LeafletMobileApplication> {
}
