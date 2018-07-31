package hu.psprog.leaflet.mobile.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.view.fragment.EntryListFragment.OnEntryItemSelectedListener;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class EntryListRecyclerViewAdapter extends RecyclerView.Adapter<EntryListRecyclerViewAdapter.ViewHolder> {

    private List<EntrySummary> entryList;
    private OnEntryItemSelectedListener entryItemSelectedListener;

    public void setEntryItemSelectedListener(OnEntryItemSelectedListener entryItemSelectedListener) {
        this.entryItemSelectedListener = entryItemSelectedListener;
    }

    public void setEntryList(List<EntrySummary> entryList) {
        this.entryList = entryList;
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
        holder.titleTextView.setText(entryList.get(position).getTitle());
        holder.prologueTextView.setText(entryList.get(position).getPrologue());

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
        final View mView;
        final TextView titleTextView;
        final TextView prologueTextView;
        EntrySummary entryItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            titleTextView = view.findViewById(R.id.entryTitle);
            prologueTextView = view.findViewById(R.id.entryPrologue);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("titleTextView", titleTextView.getText())
                    .append("prologueTextView", prologueTextView.getText())
                    .toString();
        }
    }
}
