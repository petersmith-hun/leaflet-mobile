package hu.psprog.leaflet.mobile.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.view.adapter.EntryListRecyclerViewAdapter;
import hu.psprog.leaflet.mobile.view.fragment.dummy.DummyContent;
import hu.psprog.leaflet.mobile.view.fragment.dummy.DummyContent.DummyItem;

public class EntryListFragment extends Fragment {

    private OnEntryItemSelectedListener itemSelectionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_entry_list, container, false);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(new EntryListRecyclerViewAdapter(DummyContent.ITEMS, itemSelectionListener));
        }

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
        void onListFragmentInteraction(DummyItem item);
    }
}
