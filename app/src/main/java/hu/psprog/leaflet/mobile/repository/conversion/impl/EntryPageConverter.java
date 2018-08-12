package hu.psprog.leaflet.mobile.repository.conversion.impl;

import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.entry.EntryListDataModel;
import hu.psprog.leaflet.mobile.model.EntrySummary;
import hu.psprog.leaflet.mobile.model.EntrySummaryPage;
import hu.psprog.leaflet.mobile.repository.conversion.Converter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts {@link EntryListDataModel} wrapped in {@link WrapperBodyDataModel} to {@link EntrySummaryPage}.
 *
 * @author Peter Smith
 */
@Singleton
public class EntryPageConverter implements Converter<WrapperBodyDataModel<EntryListDataModel>, EntrySummaryPage> {

    @Inject
    public EntryPageConverter() {
    }

    @Override
    public EntrySummaryPage convert(WrapperBodyDataModel<EntryListDataModel> source) {

        return EntrySummaryPage.getBuilder()
                .withPage(source.getPagination().getPageNumber())
                .withHasNext(source.getPagination().isHasNext())
                .withHasPrevious(source.getPagination().isHasPrevious())
                .withEntrySummaryList(extractEntrySummaries(source))
                .build();
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
