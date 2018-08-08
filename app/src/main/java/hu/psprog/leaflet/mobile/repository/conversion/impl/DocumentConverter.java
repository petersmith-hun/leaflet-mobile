package hu.psprog.leaflet.mobile.repository.conversion.impl;

import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.document.DocumentDataModel;
import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.conversion.Converter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Peter Smith
 */
@Singleton
public class DocumentConverter implements Converter<WrapperBodyDataModel<DocumentDataModel>, DocumentDetails> {

    @Inject
    public DocumentConverter() {
    }

    @Override
    public DocumentDetails convert(WrapperBodyDataModel<DocumentDataModel> source) {

        DocumentDataModel documentDataModel = source.getBody();
        return DocumentDetails.getBuilder()
                .withLink(documentDataModel.getLink())
                .withTitle(documentDataModel.getTitle())
                .withContent(documentDataModel.getContent())
                .build();
    }
}
