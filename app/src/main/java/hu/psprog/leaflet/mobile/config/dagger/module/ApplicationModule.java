package hu.psprog.leaflet.mobile.config.dagger.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import hu.psprog.leaflet.mobile.LeafletMobileApplication;

import javax.inject.Singleton;

/**
 * Module to configure Application instance injection.
 *
 * @author Peter Smith
 */
@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(LeafletMobileApplication leafletMobileApplication) {
        this.application = leafletMobileApplication;
    }

    @Provides
    @Singleton
    public Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    public SharedPreferences providesSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
