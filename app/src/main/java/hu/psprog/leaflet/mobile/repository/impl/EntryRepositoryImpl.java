package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.entry.EntryListDataModel;
import hu.psprog.leaflet.api.rest.response.entry.ExtendedEntryDataModel;
import hu.psprog.leaflet.bridge.client.domain.OrderBy;
import hu.psprog.leaflet.bridge.client.domain.OrderDirection;
import hu.psprog.leaflet.bridge.service.EntryBridgeService;
import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import hu.psprog.leaflet.mobile.repository.conversion.impl.EntryConverter;
import hu.psprog.leaflet.mobile.repository.conversion.impl.EntryPageConverter;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Implementation of {@link EntryRepository}.
 *
 * @author Peter Smith
 */
@Singleton
public class EntryRepositoryImpl implements EntryRepository {

    private static final int DEFAULT_LIMIT = 10;
    private static final OrderBy.Entry DEFAULT_ORDER_BY = OrderBy.Entry.CREATED;
    private static final OrderDirection DEFAULT_ORDER_DIRECTION = OrderDirection.DESC;

    private ObservableFactory observableFactory;
    private EntryBridgeService entryBridgeService;
    private EntryConverter entryConverter;
    private EntryPageConverter entryPageConverter;

    @Inject
    public EntryRepositoryImpl(ObservableFactory observableFactory, EntryBridgeService entryBridgeService,
                               EntryConverter entryConverter, EntryPageConverter entryPageConverter) {
        this.observableFactory = observableFactory;
        this.entryBridgeService = entryBridgeService;
        this.entryConverter = entryConverter;
        this.entryPageConverter = entryPageConverter;
    }

    @Override
    public Observable<EntryDetails> getEntry(String link) {
        return observableFactory.create(() -> {
            WrapperBodyDataModel<ExtendedEntryDataModel> response = entryBridgeService.getEntryByLink(link);
            return entryConverter.convert(response);
        });
    }

    @Override
    public Observable<EntrySummaryPage> getPageOfEntries(int page) {
        return observableFactory.create(() -> {
            WrapperBodyDataModel<EntryListDataModel> response = entryBridgeService
                    .getPageOfPublicEntries(page, DEFAULT_LIMIT, DEFAULT_ORDER_BY, DEFAULT_ORDER_DIRECTION);
            return entryPageConverter.convert(response);
        });
    }

    @Override
    public Observable<EntrySummaryPage> getPageOfEntriesByCategory(int page, Category category) {
        return observableFactory.create(() -> {
            WrapperBodyDataModel<EntryListDataModel> response = entryBridgeService
                    .getPageOfPublicEntriesByCategory(category.getId(), page, DEFAULT_LIMIT, DEFAULT_ORDER_BY, DEFAULT_ORDER_DIRECTION);
            return entryPageConverter.convert(response);
        });
    }
}
