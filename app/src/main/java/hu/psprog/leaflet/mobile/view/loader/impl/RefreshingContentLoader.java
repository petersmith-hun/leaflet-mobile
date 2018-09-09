package hu.psprog.leaflet.mobile.view.loader.impl;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.view.loader.AbstractDefaultContentLoader;
import hu.psprog.leaflet.mobile.viewmodel.LocalCacheSupportViewModel;
import io.reactivex.Observable;

import java.util.function.Consumer;

/**
 * {@link AbstractDefaultContentLoader} implementation for forcibly refreshing contents.
 *
 * @author Peter Smith
 */
public class RefreshingContentLoader extends AbstractDefaultContentLoader<Boolean> {

    private LocalCacheSupportViewModel localCacheSupportViewModel;
    private Consumer<Fragment> fragmentRefreshConsumer;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    public RefreshingContentLoader(Fragment fragment, View view, ViewModelProvider.Factory viewModelFactory, Consumer<Fragment> fragmentRefreshConsumer) {
        super(fragment, view);
        this.fragmentRefreshConsumer = fragmentRefreshConsumer;
        ButterKnife.bind(this, view);
        localCacheSupportViewModel = ViewModelProviders.of(getFragment(), viewModelFactory).get(LocalCacheSupportViewModel.class);
    }

    @Override
    protected Observable<Boolean> callViewModel() {
        return localCacheSupportViewModel.refresh();
    }

    @Override
    protected void handleInProgress() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    protected void handleSuccess(Boolean response) {
        fragmentRefreshConsumer.accept(getFragment());
        refreshLayout.setRefreshing(false);
    }

    @Override
    protected void handleException(Throwable throwable) {
        Toast.makeText(getFragment().getContext(), R.string.failedToRefreshMessage, Toast.LENGTH_SHORT).show();
        refreshLayout.setRefreshing(false);
    }
}
