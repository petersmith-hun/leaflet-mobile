package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import hu.psprog.leaflet.mobile.view.fragment.dummy.DummyContent;
import io.reactivex.Observable;

import java.util.stream.Collectors;

/**
 * Implementation of {@link EntryRepository}.
 *
 * @author Peter Smith
 */
public class DummyEntryRepositoryImpl implements EntryRepository {

    private ObservableFactory observableFactory;

    public DummyEntryRepositoryImpl(ObservableFactory observableFactory) {
        this.observableFactory = observableFactory;
    }

    @Override
    public Observable<EntryDetails> getEntry(String link) {
        return null;
    }

    @Override
    public Observable<EntrySummaryPage> getPageOfEntries(int page) {
        return observableFactory.create(() -> EntrySummaryPage.getBuilder()
                .withPage(1)
                .withEntrySummaryList(DummyContent.ITEMS.stream()
                        .map(dummyItem -> EntrySummary.getBuilder()
                                .withLink(dummyItem.id)
                                .withTitle(dummyItem.details)
                                .withPrologue(dummyItem.content)
                                .build())
                        .collect(Collectors.toList()))
                .build());
    }

    @Override
    public Observable<EntrySummaryPage> getPageOfEntriesByCategory(int page, Category category) {
        return null;
    }
}
