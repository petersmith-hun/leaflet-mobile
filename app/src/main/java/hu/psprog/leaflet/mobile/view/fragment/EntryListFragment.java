package hu.psprog.leaflet.mobile.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.view.adapter.EntryListRecyclerViewAdapter;
import hu.psprog.leaflet.mobile.view.loader.impl.EntrySummaryPageContentLoader;
import io.reactivex.disposables.CompositeDisposable;

public class EntryListFragment extends Fragment {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private OnEntryItemSelectedListener itemSelectionListener;
    private EntryListRecyclerViewAdapter entryListRecyclerViewAdapter;

    @BindView(R.id.entryList)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entryListRecyclerViewAdapter = new EntryListRecyclerViewAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_entry_list, container, false);
        ButterKnife.bind(this, view);
        entryListRecyclerViewAdapter.setEntryItemSelectedListener(itemSelectionListener);
        EntrySummaryPageContentLoader entrySummaryPageContentLoader = new EntrySummaryPageContentLoader(this, view, entryListRecyclerViewAdapter);

        recyclerView.setVisibility(View.GONE);
        recyclerView.setAdapter(entryListRecyclerViewAdapter);
        recyclerView.addOnScrollListener(entrySummaryPageContentLoader.getScrollListener());

        entrySummaryPageContentLoader.loadContent();

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
}
