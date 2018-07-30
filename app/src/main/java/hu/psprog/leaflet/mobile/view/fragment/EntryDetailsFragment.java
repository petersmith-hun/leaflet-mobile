package hu.psprog.leaflet.mobile.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hu.psprog.leaflet.mobile.R;

public class EntryDetailsFragment extends Fragment {

    private static final String ARG_ENTRY_LINK = "entry_link";

    private String entryLink;

    public static EntryDetailsFragment newInstance(String entryLink) {

        EntryDetailsFragment fragment = new EntryDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ENTRY_LINK, entryLink);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            entryLink = getArguments().getString(ARG_ENTRY_LINK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entry_details, container, false);
    }
}
