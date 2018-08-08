package hu.psprog.leaflet.mobile.repository.conversion.impl;

import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.entry.ExtendedEntryDataModel;
import hu.psprog.leaflet.mobile.model.EntryDetails;
import hu.psprog.leaflet.mobile.repository.conversion.Converter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Peter Smith
 */
@Singleton
public class EntryConverter implements Converter<WrapperBodyDataModel<ExtendedEntryDataModel>, EntryDetails> {

    @Inject
    public EntryConverter() {
    }

    @Override
    public EntryDetails convert(WrapperBodyDataModel<ExtendedEntryDataModel> source) {

        ExtendedEntryDataModel extendedEntryDataModel = source.getBody();
        return EntryDetails.getBuilder()
                .withAuthor(extendedEntryDataModel.getUser().getUsername())
                .withTitle(extendedEntryDataModel.getTitle())
                .withContent(extendedEntryDataModel.getContent())
                .withCreatedDate(extendedEntryDataModel.getCreated())
                .build();
    }
}
