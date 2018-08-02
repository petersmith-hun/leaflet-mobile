package hu.psprog.leaflet.mobile.view.loader;

import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Predicate;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Extended {@link AbstractDefaultContentLoader} implementation providing common tooling for pageable contents.
 * This abstract implementation is designed for {@link RecyclerView}s, so it implements pagination via {@link RecyclerView.OnScrollListener}.
 *
 * @param <T> type of model to populate view from
 * @author Peter Smith
 */
public abstract class AbstractPageableContentLoader<T extends Serializable> extends AbstractDefaultContentLoader<T> {

    private int currentPage = 1;
    private int scrollOffset = 0;

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (scrollFinished(newState) && hasNextPage() && isAllItemVisible(layoutManager)) {
                scrollOffset = layoutManager.getItemCount();
                currentPage++;
                loadContent();
            }
        }
    };

    public AbstractPageableContentLoader(Fragment fragment, View view) {
        super(fragment, view);
    }

    public RecyclerView.OnScrollListener getScrollListener() {
        return scrollListener;
    }

    protected int getScrollOffset() {
        return scrollOffset;
    }

    protected int getCurrentPage() {
        return currentPage;
    }

    /**
     * Provides predicate to decide whether {@link ViewModel} can provide a next page.
     *
     * @return next page predicate
     */
    protected abstract Predicate<T> nextPagePredicate();

    private boolean hasNextPage() {
        return Optional.ofNullable(getLastResponse())
                .map(nextPagePredicate()::test)
                .orElse(false);
    }

    private boolean scrollFinished(int newState) {
        return newState == SCROLL_STATE_IDLE;
    }

    private boolean isAllItemVisible(LinearLayoutManager layoutManager) {
        return getLastItemOffset(layoutManager) == 0;
    }

    private int getLastItemOffset(LinearLayoutManager layoutManager) {
        return layoutManager.getItemCount() - layoutManager.findLastVisibleItemPosition() - 1;
    }
}
