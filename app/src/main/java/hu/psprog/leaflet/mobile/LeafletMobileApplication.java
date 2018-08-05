package hu.psprog.leaflet.mobile;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import hu.psprog.leaflet.mobile.config.dagger.component.DaggerLeafletMobileApplicationComponent;
import hu.psprog.leaflet.mobile.config.dagger.component.LeafletMobileApplicationComponent;

import javax.inject.Inject;

/**
 * Extended {@link Application} implementation to configure Dagger dependency injection on app startup.
 *
 * @author Peter Smith
 */
public class LeafletMobileApplication extends Application implements HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidActivityInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidFragmentInjector;

    private LeafletMobileApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerLeafletMobileApplicationComponent.create();
        applicationComponent.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidFragmentInjector;
    }
}
