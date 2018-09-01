package hu.psprog.leaflet.mobile.repository.conversion.impl;

import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.entry.EntryListDataModel;
import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Converts {@link EntryListDataModel} wrapped in {@link WrapperBodyDataModel} to {@link EntrySummaryPage}.
 *
 * @author Peter Smith
 */
@Singleton
public class EntryPageTransformer {

    @Inject
    public EntryPageTransformer() {
    }

    /**
     * Converts page of entries without category information.
     *
     * @param source page of entries as {@link WrapperBodyDataModel<EntryListDataModel>}
     * @return converted {@link EntrySummaryPage} object
     */
    public EntrySummaryPage transform(WrapperBodyDataModel<EntryListDataModel> source) {
        return transform(source, null);
    }

    /**
     * Converts page of entries with category information.
     *
     * @param source page of entries as {@link WrapperBodyDataModel<EntryListDataModel>}
     * @param category category of entries on page
     * @return converted {@link EntrySummaryPage} object
     */
    public EntrySummaryPage transform(WrapperBodyDataModel<EntryListDataModel> source, Category category) {

        return EntrySummaryPage.getBuilder()
                .withPage(source.getPagination().getPageNumber())
                .withHasNext(source.getPagination().isHasNext())
                .withHasPrevious(source.getPagination().isHasPrevious())
                .withCategoryID(extractCategoryID(category))
                .withEntrySummaryList(extractEntrySummaries(source))
                .build();
    }

    private Long extractCategoryID(Category category) {
        return Optional.ofNullable(category)
                .map(Category::getId)
                .orElse(null);
    }

    private List<EntrySummary> extractEntrySummaries(WrapperBodyDataModel<EntryListDataModel> source) {
        return source.getBody().getEntries().stream()
                .map(entryDataModel -> EntrySummary.getBuilder()
                        .withLink(entryDataModel.getLink())
                        .withTitle(entryDataModel.getTitle())
                        .withPrologue(entryDataModel.getPrologue())
                        .build())
                .collect(Collectors.toList());
    }
}
