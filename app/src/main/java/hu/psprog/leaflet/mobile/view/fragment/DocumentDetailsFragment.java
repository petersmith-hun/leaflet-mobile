package hu.psprog.leaflet.mobile.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dagger.android.support.AndroidSupportInjection;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.view.loader.impl.DocumentDetailsPageContentLoader;
import hu.psprog.leaflet.mobile.viewmodel.factory.DependencyInjectingViewModelFactory;
import ru.noties.markwon.SpannableConfiguration;

import javax.inject.Inject;

import static hu.psprog.leaflet.mobile.view.loader.impl.DocumentDetailsPageContentLoader.ARG_DOCUMENT_LINK;

/**
 * View for {@link DocumentDetails} as {@link Fragment}.
 */
public class DocumentDetailsFragment extends Fragment {

    @Inject
    DependencyInjectingViewModelFactory viewModelFactory;

    @Inject
    SpannableConfiguration spannableConfiguration;

    public static DocumentDetailsFragment newInstance(DocumentType documentType) {

        DocumentDetailsFragment fragment = new DocumentDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DOCUMENT_LINK, documentType.getDocumentLink());
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

        View view = inflater.inflate(R.layout.fragment_document_details, container, false);
        new DocumentDetailsPageContentLoader(this, view, viewModelFactory, spannableConfiguration).loadContent();

        return view;
    }

    public enum DocumentType {

        INTRODUCTION("introduction");

        private String documentLink;

        DocumentType(String documentLink) {
            this.documentLink = documentLink;
        }

        public String getDocumentLink() {
            return documentLink;
        }
    }
}
