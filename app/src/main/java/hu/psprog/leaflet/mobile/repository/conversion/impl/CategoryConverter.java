package hu.psprog.leaflet.mobile.repository.conversion.impl;

import hu.psprog.leaflet.api.rest.response.category.CategoryDataModel;
import hu.psprog.leaflet.mobile.model.Category;
import hu.psprog.leaflet.mobile.repository.conversion.Converter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Peter Smith
 */
@Singleton
public class CategoryConverter implements Converter<CategoryDataModel, Category> {

    @Inject
    public CategoryConverter() {
    }

    @Override
    public Category convert(CategoryDataModel source) {
        return Category.getBuilder()
                .withId(source.getId())
                .withName(source.getTitle())
                .build();
    }
}
