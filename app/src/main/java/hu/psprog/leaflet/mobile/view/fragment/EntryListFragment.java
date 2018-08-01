package hu.psprog.leaflet.mobile.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.view.adapter.EntryListRecyclerViewAdapter;
import hu.psprog.leaflet.mobile.viewmodel.EntryListViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class EntryListFragment extends Fragment {

    private OnEntryItemSelectedListener itemSelectionListener;
    private EntryListViewModel entryListViewModel;
    private EntryListRecyclerViewAdapter entryListRecyclerViewAdapter;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private int currentPage = 1;
    private int scrollOffset = 0;
    private boolean hasNextPage;
    private boolean hasPreviousPage;

    @BindView(R.id.entryListProgressBar)
    ProgressBar progressBar;

    @BindView(R.id.entryList)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entryListViewModel = ViewModelProviders.of(this).get(EntryListViewModel.class);
        entryListRecyclerViewAdapter = new EntryListRecyclerViewAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_entry_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setAdapter(entryListRecyclerViewAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == SCROLL_STATE_IDLE) {
                    int lastItemOffset = layoutManager.getItemCount() - layoutManager.findLastVisibleItemPosition() - 1;
                    if (lastItemOffset == 0 && hasNextPage) {
                        hasNextPage = false;
                        scrollOffset = layoutManager.getItemCount();
                        loadPage(++currentPage);
                    }
                }
            }
        });

        loadPage(currentPage);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEntryItemSelectedListener) {
            itemSelectionListener = (OnEntryItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnEntryItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        itemSelectionListener = null;
    }

    public interface OnEntryItemSelectedListener {
        void onListFragmentInteraction(EntrySummary item);
    }

    private void loadPage(int page) {
        Disposable subscriptionResult = entryListViewModel.getEntrySummaryPage(page)
                .doOnSubscribe(disposable -> showProgressBar())
                .subscribe(this::updateView, this::handleException);
        disposables.add(subscriptionResult);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void handleException(Throwable throwable) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Failed to load entries", Toast.LENGTH_SHORT).show();
    }

    private void updateView(EntrySummaryPage entrySummaryPage) {
        updatePagination(entrySummaryPage);
        updateAdapter(entrySummaryPage.getEntrySummaryList());
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.scrollToPosition(scrollOffset);
        progressBar.setVisibility(View.GONE);
    }

    private void updatePagination(EntrySummaryPage entrySummaryPage) {
        hasNextPage = entrySummaryPage.hasNext();
        hasPreviousPage = entrySummaryPage.hasPrevious();
    }

    private void updateAdapter(List<EntrySummary> entrySummaryList) {
        int currentItemCount = entryListRecyclerViewAdapter.getItemCount();
        entryListRecyclerViewAdapter.setEntryItemSelectedListener(itemSelectionListener);
        entryListRecyclerViewAdapter.appendEntryList(entrySummaryList);
        entryListRecyclerViewAdapter.notifyItemRangeInserted(currentItemCount, entrySummaryList.size());
    }
}
