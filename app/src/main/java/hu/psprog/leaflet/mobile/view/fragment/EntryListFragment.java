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
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.view.adapter.EntryListRecyclerViewAdapter;
import hu.psprog.leaflet.mobile.viewmodel.EntryListViewModel;

public class EntryListFragment extends Fragment {

    private OnEntryItemSelectedListener itemSelectionListener;
    private EntryListViewModel entryListViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entryListViewModel = ViewModelProviders.of(this).get(EntryListViewModel.class);
        entryListViewModel.loadEntryPage(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_entry_list, container, false);

        entryListViewModel.getEntrySummaryPage().observe(this, entrySummaryPage -> {
            if (view instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(new EntryListRecyclerViewAdapter(entrySummaryPage.getEntrySummaryList(), itemSelectionListener));
            }
        });

        return view;
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
