package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online;

import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.entry.EntryListDataModel;
import hu.psprog.leaflet.api.rest.response.entry.ExtendedEntryDataModel;
import hu.psprog.leaflet.bridge.service.EntryBridgeService;
import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.model.OrderBy;
import hu.psprog.leaflet.mobile.model.OrderDirection;
import hu.psprog.leaflet.mobile.repository.conversion.impl.EntryConverter;
import hu.psprog.leaflet.mobile.repository.conversion.impl.EntryPageTransformer;
import hu.psprog.leaflet.mobile.repository.impl.adapter.EntryAdapter;
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
public class EntryNetworkRequestAdapter extends BridgeCallerNetworkRequestAdapter implements EntryAdapter {

    private static final String LOG_TAG = "entry_adapter::online";

    private EntryBridgeService entryBridgeService;
    private EntryConverter entryConverter;
    private EntryPageTransformer entryPageTransformer;

    @Inject
    public EntryNetworkRequestAdapter(EntryBridgeService entryBridgeService, EntryConverter entryConverter, EntryPageTransformer entryPageTransformer) {
        this.entryBridgeService = entryBridgeService;
        this.entryConverter = entryConverter;
        this.entryPageTransformer = entryPageTransformer;
    }

    @Override
    public Optional<EntryDetails> getEntry(String link) {

        return Optional.ofNullable(callBridge(() -> {
            LogUtility.debug(LOG_TAG, "Requesting entry '%s' from API service", link);
            WrapperBodyDataModel<ExtendedEntryDataModel> response = entryBridgeService.getEntryByLink(link);
            return entryConverter.convert(response);
        }));
    }

    @Override
    public EntrySummaryPage getPageOfEntries(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection) {

        return callBridge(() -> {
            LogUtility.debug(LOG_TAG, "Requesting summary page #%d from API service", page);
            WrapperBodyDataModel<EntryListDataModel> response = entryBridgeService
                    .getPageOfPublicEntries(page, limit, mapOrderBy(orderBy), mapOrderDirection(orderDirection));
            return entryPageTransformer.transform(response);
        });
    }

    @Override
    public EntrySummaryPage getPageOfEntriesByCategory(int page, int limit, OrderBy.Entry orderBy, OrderDirection orderDirection, Category category) {

        return callBridge(() -> {
            LogUtility.debug(LOG_TAG, "Requesting summary page #%d for category #%d from API service", page, category.getId());
            WrapperBodyDataModel<EntryListDataModel> response = entryBridgeService
                    .getPageOfPublicEntriesByCategory(category.getId(), page, limit, mapOrderBy(orderBy), mapOrderDirection(orderDirection));
            return entryPageTransformer.transform(response, category);
        });
    }

    private hu.psprog.leaflet.bridge.client.domain.OrderBy.Entry mapOrderBy(OrderBy.Entry orderBy) {
        return hu.psprog.leaflet.bridge.client.domain.OrderBy.Entry.valueOf(orderBy.name());
    }

    private hu.psprog.leaflet.bridge.client.domain.OrderDirection mapOrderDirection(OrderDirection orderDirection) {
        return hu.psprog.leaflet.bridge.client.domain.OrderDirection.valueOf(orderDirection.name());
    }
}
