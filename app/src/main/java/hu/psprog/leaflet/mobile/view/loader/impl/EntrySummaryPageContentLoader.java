package hu.psprog.leaflet.mobile.view.loader.impl;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.view.adapter.EntryListRecyclerViewAdapter;
import hu.psprog.leaflet.mobile.view.loader.AbstractPageableContentLoader;
import hu.psprog.leaflet.mobile.viewmodel.EntryListViewModel;
import io.reactivex.Observable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static hu.psprog.leaflet.mobile.view.activity.MainActivity.INTENT_PARAMETER_CATEGORY_ID;

/**
 * {@link AbstractPageableContentLoader} implementation for loading {@link EntrySummaryPage} contents.
 *
 * @author Peter Smith
 */
public class EntrySummaryPageContentLoader extends AbstractPageableContentLoader<EntrySummaryPage> {

    private EntryListViewModel entryListViewModel;
    private EntryListRecyclerViewAdapter entryListRecyclerViewAdapter;

    @BindView(R.id.entryListProgressBar)
    ProgressBar progressBar;

    @BindView(R.id.entryList)
    RecyclerView recyclerView;

    public EntrySummaryPageContentLoader(Fragment fragment, View view, EntryListRecyclerViewAdapter entryListRecyclerViewAdapter) {
        super(fragment, view);
        ButterKnife.bind(this, getView());
        this.entryListRecyclerViewAdapter = entryListRecyclerViewAdapter;
        entryListViewModel = ViewModelProviders.of(getFragment()).get(EntryListViewModel.class);
    }

    @Override
    protected Observable<EntrySummaryPage> callViewModel() {
        return extractCategoryID()
                .map(categoryID -> entryListViewModel.getEntrySummaryPageByCategory(getCurrentPage(), categoryID))
                .orElseGet(() -> entryListViewModel.getEntrySummaryPage(getCurrentPage()));
    }

    @Override
    protected void handleInProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void handleSuccess(EntrySummaryPage response) {
        updateAdapter(response.getEntrySummaryList());
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.scrollToPosition(getScrollOffset());
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void handleException(Throwable throwable) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getFragment().getContext(), R.string.entrySummaryPageLoadFailure, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Predicate<EntrySummaryPage> nextPagePredicate() {
        return EntrySummaryPage::hasNext;
    }

    private Optional<Long> extractCategoryID() {
        return Optional.ofNullable(getFragment().getArguments())
                .map(bundle -> bundle.getLong(INTENT_PARAMETER_CATEGORY_ID));
    }

    private void updateAdapter(List<EntrySummary> entrySummaryList) {
        int currentItemCount = entryListRecyclerViewAdapter.getItemCount();
        entryListRecyclerViewAdapter.appendEntryList(entrySummaryList);
        entryListRecyclerViewAdapter.notifyItemRangeInserted(currentItemCount, entrySummaryList.size());
    }
}
