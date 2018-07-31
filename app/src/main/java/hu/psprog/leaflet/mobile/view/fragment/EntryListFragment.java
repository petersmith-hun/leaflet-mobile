package hu.psprog.leaflet.mobile.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class EntryListFragment extends Fragment {

    private OnEntryItemSelectedListener itemSelectionListener;
    private EntryListViewModel entryListViewModel;
    private EntryListRecyclerViewAdapter entryListRecyclerViewAdapter;
    private final CompositeDisposable disposables = new CompositeDisposable();

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

        Disposable subscriptionResult = entryListViewModel.getEntrySummaryPage(1)
                .doOnSubscribe(disposable -> showProgressBar())
                .subscribe(this::updateView, this::handleException);
        disposables.add(subscriptionResult);

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

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void handleException(Throwable throwable) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Failed to load entries", Toast.LENGTH_SHORT).show();
    }

    private void updateView(EntrySummaryPage entrySummaryPage) {
        updateAdapter(entrySummaryPage.getEntrySummaryList());
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(entryListRecyclerViewAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void updateAdapter(List<EntrySummary> entrySummaryList) {
        entryListRecyclerViewAdapter.setEntryItemSelectedListener(itemSelectionListener);
        entryListRecyclerViewAdapter.setEntryList(entrySummaryList);
    }
}
