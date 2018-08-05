package hu.psprog.leaflet.mobile.view.loader;

import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;
import android.view.View;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * {@link ContentLoader} implementation providing common tooling for non-pageable contents.
 * Handling of content loading starts by calling callViewModel method and consists of three parts:
 *  - handling "in progress" status - model has been requested, but not yet retrieved
 *  - handling successful retrieval
 *  - handling failed retrieval
 *
 * @param <T> type of model to populate view from
 * @author Peter Smith
 */
public abstract class AbstractDefaultContentLoader<T> implements ContentLoader {

    private final CompositeDisposable disposables = new CompositeDisposable();

    private Fragment fragment;
    private View view;

    private T lastResponse;

    public AbstractDefaultContentLoader(Fragment fragment, View view) {
        this.fragment = fragment;
        this.view = view;
    }

    @Override
    public void loadContent() {
        Disposable subscriptionResult = callViewModel()
                .doOnSubscribe(disposable -> handleInProgress())
                .subscribe(this::doHandleSuccess, this::handleException);
        disposables.add(subscriptionResult);
    }

    @Override
    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public View getView() {
        return view;
    }

    protected T getLastResponse() {
        return lastResponse;
    }

    /**
     * Calls implemented {@link ViewModel} to retrieve model as {@link Observable}.
     *
     * @return retrieved model as {@link Observable}
     */
    protected abstract Observable<T> callViewModel();

    /**
     * Handles "in progress" status during content loading.
     */
    protected abstract void handleInProgress();

    /**
     * Handles successful status of content loading.
     *
     * @param response response of {@link ViewModel} as T
     */
    protected abstract void handleSuccess(T response);

    /**
     * Handles failed status of content loading.
     *
     * @param throwable exception thrown during retrieval
     */
    protected abstract void handleException(Throwable throwable);

    private void doHandleSuccess(T response) {
        lastResponse = response;
        handleSuccess(response);
    }
}
