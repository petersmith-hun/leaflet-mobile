package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import io.reactivex.Observable;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of {@link EntryRepository}.
 *
 * @author Peter Smith
 */
public class DummyEntryRepositoryImpl implements EntryRepository {

    private static final int ITEM_COUNT_PER_PAGE = 10;
    private static final String PROLOGUE = "Lorem ipsum dolor sit amet";
    private static final String LINK_PATTERN = "item-%s";
    private static final String TITLE_PATTERN = "Title #%s";

    private ObservableFactory observableFactory;

    public DummyEntryRepositoryImpl() {
        // TODO configure Dagger DI
        this.observableFactory = new ObservableFactory();
    }

    @Override
    public Observable<EntryDetails> getEntry(String link) {
        return null;
    }

    @Override
    public Observable<EntrySummaryPage> getPageOfEntries(int page) {
        return observableFactory.create(() -> {
            Thread.sleep(2000); // simulating long network call
            return EntrySummaryPage.getBuilder()
                    .withPage(page)
                    .withHasNext(page < 5)
                    .withEntrySummaryList(IntStream.range(0, ITEM_COUNT_PER_PAGE)
                            .map(index -> calculateID(index, page))
                            .mapToObj(index -> EntrySummary.getBuilder()
                                    .withLink(String.format(LINK_PATTERN, index))
                                    .withTitle(String.format(TITLE_PATTERN, index))
                                    .withPrologue(PROLOGUE)
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        });
    }

    @Override
    public Observable<EntrySummaryPage> getPageOfEntriesByCategory(int page, Category category) {
        return null;
    }

    private int calculateID(int index, int page) {
        return ITEM_COUNT_PER_PAGE * (page - 1) + index + 1;
    }
}
