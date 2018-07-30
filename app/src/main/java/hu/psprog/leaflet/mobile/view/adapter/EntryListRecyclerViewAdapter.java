package hu.psprog.leaflet.mobile.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.view.fragment.EntryListFragment.OnEntryItemSelectedListener;
import hu.psprog.leaflet.mobile.view.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

public class EntryListRecyclerViewAdapter extends RecyclerView.Adapter<EntryListRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> entryList;
    private final OnEntryItemSelectedListener entryItemSelectedListener;

    public EntryListRecyclerViewAdapter(List<DummyItem> items, OnEntryItemSelectedListener listener) {
        entryList = items;
        entryItemSelectedListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.entryItem = entryList.get(position);
        holder.mIdView.setText(entryList.get(position).id);
        holder.mContentView.setText(entryList.get(position).content);

        holder.mView.setOnClickListener(view -> {
            if (null != entryItemSelectedListener) {
                entryItemSelectedListener.onListFragmentInteraction(holder.entryItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem entryItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
