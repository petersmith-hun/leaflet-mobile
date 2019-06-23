package hu.psprog.leaflet.mobile.repository.conversion.impl;

import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.entry.ExtendedEntryDataModel;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.repository.conversion.Converter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Converts {@link ExtendedEntryDataModel} wrapped in {@link WrapperBodyDataModel} to {@link EntryDetails}.
 *
 * @author Peter Smith
 */
@Singleton
public class EntryConverter implements Converter<WrapperBodyDataModel<ExtendedEntryDataModel>, EntryDetails> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy. LLLL dd., EEEE");

    @Inject
    public EntryConverter() {
    }

    @Override
    public EntryDetails convert(WrapperBodyDataModel<ExtendedEntryDataModel> source) {

        ExtendedEntryDataModel extendedEntryDataModel = source.getBody();
        return EntryDetails.getBuilder()
                .withLink(source.getBody().getLink())
                .withAuthor(extendedEntryDataModel.getUser().getUsername())
                .withTitle(extendedEntryDataModel.getTitle())
                .withContent(extendedEntryDataModel.getRawContent())
                .withCreatedDate(formatCreationDate(extendedEntryDataModel))
                .build();
    }

    private String formatCreationDate(ExtendedEntryDataModel entryDataModel) {

        return extractCreationDate(entryDataModel)
                .withZoneSameInstant(ZoneId.systemDefault())
                .format(DATE_TIME_FORMATTER);
    }

    private ZonedDateTime extractCreationDate(ExtendedEntryDataModel entryDataModel) {

        return Optional.ofNullable(entryDataModel.getPublished())
                .orElse(entryDataModel.getCreated());
    }
}
