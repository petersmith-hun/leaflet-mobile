package hu.psprog.leaflet.mobile.view.loader.impl;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.view.loader.AbstractDefaultContentLoader;
import hu.psprog.leaflet.mobile.view.loader.AbstractPageableContentLoader;
import hu.psprog.leaflet.mobile.viewmodel.DocumentDetailsViewModel;
import io.reactivex.Observable;
import ru.noties.markwon.Markwon;
import ru.noties.markwon.SpannableConfiguration;

import java.util.Optional;

/**
 * {@link AbstractPageableContentLoader} implementation for loading {@link DocumentDetails} contents.
 *
 * @author Peter Smith
 */
public class DocumentDetailsPageContentLoader extends AbstractDefaultContentLoader<DocumentDetails> {

    public static final String ARG_DOCUMENT_LINK = "document_link";

    private DocumentDetailsViewModel documentDetailsViewModel;
    private SpannableConfiguration spannableConfiguration;

    @BindView(R.id.documentTitle)
    TextView documentTitleTextView;

    @BindView(R.id.documentContent)
    TextView contentTextView;

    @BindView(R.id.documentDetailsScrollView)
    NestedScrollView scrollView;

    @BindView(R.id.documentDetailsProgressBar)
    ProgressBar progressBar;

    @BindString(R.string.exceptionMessageLinkNotSpecified)
    String exceptionMessageLinkNotSpecified;

    public DocumentDetailsPageContentLoader(Fragment fragment, View view, ViewModelProvider.Factory viewModelFactory, SpannableConfiguration spannableConfiguration) {
        super(fragment, view);
        this.spannableConfiguration = spannableConfiguration;
        ButterKnife.bind(this, getView());
        documentDetailsViewModel = ViewModelProviders.of(getFragment(), viewModelFactory).get(DocumentDetailsViewModel.class);
    }

    @Override
    protected Observable<DocumentDetails> callViewModel() {
        return extractLink()
                .map(documentDetailsViewModel::getDocument)
                .orElseGet(() -> Observable.error(new IllegalArgumentException(exceptionMessageLinkNotSpecified)));
    }

    @Override
    protected void handleInProgress() {
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void handleSuccess(DocumentDetails response) {
        documentTitleTextView.setText(response.getTitle());
        Markwon.setMarkdown(contentTextView, spannableConfiguration, response.getContent());
        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void handleException(Throwable throwable) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getFragment().getContext(), R.string.failedToLoadDocument, Toast.LENGTH_SHORT).show();
    }

    private Optional<String> extractLink() {
        return Optional.ofNullable(getFragment().getArguments())
                .map(bundle -> bundle.getString(ARG_DOCUMENT_LINK));
    }
}
