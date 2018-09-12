package hu.psprog.leaflet.mobile.view.loader.impl;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.service.LocalCacheUpdaterService;
import hu.psprog.leaflet.mobile.view.loader.AbstractDefaultContentLoader;
import io.reactivex.Observable;

import java.util.function.Consumer;

/**
 * {@link AbstractDefaultContentLoader} implementation for forcibly refreshing contents.
 *
 * @author Peter Smith
 */
public class RefreshingContentLoader extends AbstractDefaultContentLoader<Boolean> {

    private LocalCacheUpdaterService localCacheUpdaterService;
    private Consumer<Fragment> fragmentRefreshConsumer;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    public RefreshingContentLoader(Fragment fragment, View view, LocalCacheUpdaterService localCacheUpdaterService, Consumer<Fragment> fragmentRefreshConsumer) {
        super(fragment, view);
        this.localCacheUpdaterService = localCacheUpdaterService;
        this.fragmentRefreshConsumer = fragmentRefreshConsumer;
        ButterKnife.bind(this, view);
    }

    @Override
    protected Observable<Boolean> callViewModel() {
        return localCacheUpdaterService.update(true);
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
