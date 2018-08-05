package hu.psprog.leaflet.mobile.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.view.loader.impl.DocumentDetailsPageContentLoader;

import static hu.psprog.leaflet.mobile.view.loader.impl.DocumentDetailsPageContentLoader.ARG_DOCUMENT_LINK;

/**
 * View for {@link DocumentDetails} as {@link Fragment}.
 */
public class DocumentDetailsFragment extends Fragment {

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_document_details, container, false);
        new DocumentDetailsPageContentLoader(this, view).loadContent();

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
