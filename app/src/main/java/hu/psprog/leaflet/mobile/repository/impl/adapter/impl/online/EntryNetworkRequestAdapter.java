package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online;

import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.OrderBy;
import hu.psprog.leaflet.mobile.model.OrderDirection;
import hu.psprog.leaflet.mobile.repository.conversion.impl.EntryConverter;
import hu.psprog.leaflet.mobile.repository.conversion.impl.EntryPageTransformer;
import hu.psprog.leaflet.mobile.repository.impl.adapter.EntryAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client.EntryRESTClient;
import hu.psprog.leaflet.mobile.util.logging.LogUtility;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * {@link EntryAdapter} implementation for online data sources.
 * This implementation retrieves data from Leaflet backend via Bridge.
 *
 * @author Peter Smith
 */
@Singleton
public class EntryNetworkRequestAdapter extends AbstractBaseNetworkRequestAdapter implements EntryAdapter {

    private static final String LOG_TAG = "entry_adapter::online";

    private EntryRESTClient entryRESTClient;
    private EntryConverter entryConverter;
    private EntryPageTransformer entryPageTransformer;

    @Inject
    public EntryNetworkRequestAdapter(EntryRESTClient entryRESTClient, EntryConverter entryConverter, EntryPageTransformer entryPageTransformer) {
        this.entryRESTClient = entryRESTClient;
        this.entryConverter = entryConverter;
        this.entryPageTransformer = entryPageTransformer;
    }

    @Override
    public Optional<EntryDetails> getEntry(String link) {

        LogUtility.debug(LOG_TAG, "Requesting entry '%s' from API service", link);
        EntryDetails entryDetails = callBackend(
                () -> entryRESTClient.getEntryByLink(link),
                entryConverter::convert);

        return Optional.ofNullable(entryDetails);
    }

    @Override
    public EntrySummaryPage getPageOfEntries(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection) {

        LogUtility.debug(LOG_TAG, "Requesting summary page #%d from API service", page);

        return callBackend(
                () -> entryRESTClient.getPageOfPublicEntries(page, limit, orderBy, orderDirection),
                entryPageTransformer::transform);
    }

    @Override
    public EntrySummaryPage getPageOfEntriesByCategory(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection, Category category) {

        LogUtility.debug(LOG_TAG, "Requesting summary page #%d for category #%d from API service", page, category.getId());

        return callBackend(
                () -> entryRESTClient.getPageOfPublicEntriesByCategory(category.getId(), page, limit, orderBy, orderDirection),
                entryPageTransformer::transform);
    }
}
