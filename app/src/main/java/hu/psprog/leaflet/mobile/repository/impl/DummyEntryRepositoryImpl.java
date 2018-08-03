package hu.psprog.leaflet.mobile.repository.impl;

import android.util.Log;
import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import io.reactivex.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
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
    private static final String DEFAULT_TITLE_PATTERN = "[Default] Title #%s";
    private static final String CATEGORY_TITLE_PATTERN = "[Category] Title #%s";
    private static final String CATEGORY_PROLOGUE_PATTERN = "From category=%s";
    private static final String AUTHOR = "petersmith";
    private static final String CREATED_DATE = "Sunday, July 1, 2018 6:25 PM";
    private static final String TITLE = "JWT authentikáció Spring Security alatt";
    private static final String DEFAULT_CONTENT = "default_content";

    private ObservableFactory observableFactory;

    public DummyEntryRepositoryImpl() {
        // TODO configure Dagger DI
        this.observableFactory = new ObservableFactory();
    }

    @Override
    public Observable<EntryDetails> getEntry(String link) {
        return observableFactory.create(() -> {
            Thread.sleep(1000); // simulating long network call
            return EntryDetails.getBuilder()
                    .withAuthor(AUTHOR)
                    .withContent(getContent())
                    .withCreatedDate(CREATED_DATE)
                    .withTitle(TITLE)
                    .build();
        });
    }

    @Override
    public Observable<EntrySummaryPage> getPageOfEntries(int page) {
        return observableFactory.create(() -> {
            Thread.sleep(1000); // simulating long network call
            return EntrySummaryPage.getBuilder()
                    .withPage(page)
                    .withHasNext(page < 5)
                    .withEntrySummaryList(IntStream.range(0, ITEM_COUNT_PER_PAGE)
                            .map(index -> calculateID(index, page))
                            .mapToObj(index -> EntrySummary.getBuilder()
                                    .withLink(String.format(LINK_PATTERN, index))
                                    .withTitle(String.format(DEFAULT_TITLE_PATTERN, index))
                                    .withPrologue(PROLOGUE)
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        });
    }

    @Override
    public Observable<EntrySummaryPage> getPageOfEntriesByCategory(int page, Category category) {
        return observableFactory.create(() -> {
            Thread.sleep(1000); // simulating long network call
            return EntrySummaryPage.getBuilder()
                    .withPage(page)
                    .withHasNext(page < 3)
                    .withEntrySummaryList(IntStream.range(0, ITEM_COUNT_PER_PAGE)
                            .map(index -> calculateID(index, page))
                            .mapToObj(index -> EntrySummary.getBuilder()
                                    .withLink(String.format(LINK_PATTERN, index))
                                    .withTitle(String.format(CATEGORY_TITLE_PATTERN, index))
                                    .withPrologue(String.format(CATEGORY_PROLOGUE_PATTERN, category.toString()))
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        });
    }

    private String getContent() {

        return getEntryContent()
                .map(inputStream -> {
                    String result;
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                        result = reader.lines()
                                .collect(Collectors.joining());
                    } catch (IOException exc) {
                        Log.e(DummyEntryRepositoryImpl.class.getSimpleName(), "Failed to read dummy entry content", exc);
                        result = DEFAULT_CONTENT;
                    }

                    return result;
                })
                .orElse(DEFAULT_CONTENT);
    }

    private Optional<InputStream> getEntryContent() {
        return Optional.ofNullable(getClass().getResourceAsStream("/res/raw/entry.html"));
    }

    private int calculateID(int index, int page) {
        return ITEM_COUNT_PER_PAGE * (page - 1) + index + 1;
    }
}
