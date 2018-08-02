package hu.psprog.leaflet.mobile.view.loader.impl;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.view.loader.AbstractDefaultContentLoader;
import hu.psprog.leaflet.mobile.viewmodel.EntryDetailsViewModel;
import io.reactivex.Observable;

import java.util.Optional;

/**
 * @author Peter Smith
 */
public class EntryDetailsPageContentLoader extends AbstractDefaultContentLoader<EntryDetails> {

    public static final String ARG_ENTRY_LINK = "entry_link";

    private static final String BASE_URL = "file:///android_asset/";
    private static final String MIME_TYPE = "text/html";
    private static final String ENCODING = "UTF-8";
    private static final String HISTORY_URL = null;

    private EntryDetailsViewModel entryDetailsViewModel;

    @BindView(R.id.detailsAuthor)
    TextView authorTextView;

    @BindView(R.id.detailsTitle)
    TextView titleTextView;

    @BindView(R.id.detailsCreatedDate)
    TextView createdDateTextView;

    @BindView(R.id.detailsContent)
    WebView contentWebView;

    @BindString(R.string.detailsPageCSSTag)
    String detailsPageCSSTag;

    @BindString(R.string.exceptionMessageLinkNotSpecified)
    String exceptionMessageLinkNotSpecified;

    @BindView(R.id.entryDetailsProgressBar)
    ProgressBar progressBar;

    public EntryDetailsPageContentLoader(Fragment fragment, View view) {
        super(fragment, view);
        ButterKnife.bind(this, getView());
        entryDetailsViewModel = ViewModelProviders.of(getFragment()).get(EntryDetailsViewModel.class);
    }

    @Override
    protected Observable<EntryDetails> callViewModel() {
        return extractLink()
                .map(entryDetailsViewModel::getEntryDetails)
                .orElseGet(() -> Observable.error(new IllegalArgumentException(exceptionMessageLinkNotSpecified)));
    }

    @Override
    protected void handleInProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void handleSuccess(EntryDetails response) {
        authorTextView.setText(response.getAuthor());
        titleTextView.setText(response.getTitle());
        createdDateTextView.setText(response.getCreatedDate());
        renderContent(response);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void handleException(Throwable throwable) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getFragment().getContext(), R.string.entryFailedToLoad, Toast.LENGTH_SHORT).show();
    }

    private Optional<String> extractLink() {
        return Optional.ofNullable(getFragment().getArguments())
                .map(bundle -> bundle.getString(ARG_ENTRY_LINK));
    }

    private void renderContent(EntryDetails response) {
        contentWebView.loadDataWithBaseURL(BASE_URL, prependCSSMetaToContent(response), MIME_TYPE, ENCODING, HISTORY_URL);
    }

    private String prependCSSMetaToContent(EntryDetails response) {
        return detailsPageCSSTag + response.getContent();
    }
}
