package hu.psprog.leaflet.mobile.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dagger.android.support.AndroidSupportInjection;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.view.loader.impl.EntryDetailsPageContentLoader;
import hu.psprog.leaflet.mobile.viewmodel.factory.DependencyInjectingViewModelFactory;

import javax.inject.Inject;

import static hu.psprog.leaflet.mobile.view.loader.impl.EntryDetailsPageContentLoader.ARG_ENTRY_LINK;

/**
 * View for {@link EntryDetails} as {@link Fragment}.
 */
public class EntryDetailsFragment extends Fragment {

    @Inject
    DependencyInjectingViewModelFactory viewModelFactory;

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
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_entry_details, container, false);
        new EntryDetailsPageContentLoader(this, view, viewModelFactory).loadContent();

        return view;
    }
}
