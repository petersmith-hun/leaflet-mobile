package hu.psprog.leaflet.mobile.view.helper;

import android.view.View;
import android.webkit.WebView;
import butterknife.BindString;
import butterknife.ButterKnife;
import hu.psprog.leaflet.mobile.R;

/**
 * Renders HTML content for usage in WebView.
 *
 * @author Peter Smith
 */
public class HTMLRenderer {

    private static final String BASE_URL = "file:///android_asset/";
    private static final String MIME_TYPE = "text/html";
    private static final String ENCODING = "UTF-8";
    private static final String HISTORY_URL = null;

    @BindString(R.string.detailsPageCSSTag)
    String detailsPageCSSTag;

    public HTMLRenderer(View view) {
        ButterKnife.bind(this, view);
    }

    /**
     * Renders HTML content and loads into given {@link WebView}.
     *
     * @param webView {@link WebView} instance to load content into
     * @param source source HTML content
     */
    public void render(WebView webView, String source) {
        webView.loadDataWithBaseURL(BASE_URL, prependCSSMetaToContent(source), MIME_TYPE, ENCODING, HISTORY_URL);
    }

    private String prependCSSMetaToContent(String source) {
        return detailsPageCSSTag + source;
    }
}
