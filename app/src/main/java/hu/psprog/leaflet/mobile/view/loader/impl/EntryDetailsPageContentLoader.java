package hu.psprog.leaflet.mobile.view.loader.impl;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import hu.psprog.leaflet.mobile.R;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.view.helper.HTMLRenderer;
import hu.psprog.leaflet.mobile.view.loader.AbstractDefaultContentLoader;
import hu.psprog.leaflet.mobile.view.loader.AbstractPageableContentLoader;
import hu.psprog.leaflet.mobile.viewmodel.EntryDetailsViewModel;
import io.reactivex.Observable;

import java.util.Optional;

/**
 * {@link AbstractPageableContentLoader} implementation for loading {@link EntryDetails} contents.
 *
 * @author Peter Smith
 */
public class EntryDetailsPageContentLoader extends AbstractDefaultContentLoader<EntryDetails> {

    public static final String ARG_ENTRY_LINK = "entry_link";

    private EntryDetailsViewModel entryDetailsViewModel;
    private HTMLRenderer htmlRenderer;

    @BindView(R.id.detailsAuthor)
    TextView authorTextView;

    @BindView(R.id.detailsTitle)
    TextView titleTextView;

    @BindView(R.id.detailsCreatedDate)
    TextView createdDateTextView;

    @BindView(R.id.detailsContent)
    WebView contentWebView;

    @BindView(R.id.entryDetailsScrollView)
    ScrollView scrollView;

    @BindString(R.string.exceptionMessageLinkNotSpecified)
    String exceptionMessageLinkNotSpecified;

    @BindView(R.id.entryDetailsProgressBar)
    ProgressBar progressBar;

    public EntryDetailsPageContentLoader(Fragment fragment, View view) {
        super(fragment, view);
        ButterKnife.bind(this, getView());
        entryDetailsViewModel = ViewModelProviders.of(getFragment()).get(EntryDetailsViewModel.class);
        htmlRenderer = new HTMLRenderer(view);
    }

    @Override
    protected Observable<EntryDetails> callViewModel() {
        return extractLink()
                .map(entryDetailsViewModel::getEntryDetails)
                .orElseGet(() -> Observable.error(new IllegalArgumentException(exceptionMessageLinkNotSpecified)));
    }

    @Override
    protected void handleInProgress() {
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void handleSuccess(EntryDetails response) {
        authorTextView.setText(response.getAuthor());
        titleTextView.setText(response.getTitle());
        createdDateTextView.setText(response.getCreatedDate());
        htmlRenderer.render(contentWebView, response.getContent());
        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
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
}
