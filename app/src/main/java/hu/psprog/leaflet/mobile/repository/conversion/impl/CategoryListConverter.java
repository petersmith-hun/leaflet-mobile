package hu.psprog.leaflet.mobile.repository.conversion.impl;

import hu.psprog.leaflet.api.rest.response.category.CategoryListDataModel;
import hu.psprog.leaflet.mobile.model.CategoryList;
import hu.psprog.leaflet.mobile.repository.conversion.Converter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

/**
 * @author Peter Smith
 */
@Singleton
public class CategoryListConverter implements Converter<CategoryListDataModel, CategoryList> {

    private CategoryConverter categoryConverter;

    @Inject
    public CategoryListConverter(CategoryConverter categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    @Override
    public CategoryList convert(CategoryListDataModel source) {
        return CategoryList.getBuilder()
                .withCategories(source.getCategories().stream()
                        .map(categoryConverter::convert)
                        .collect(Collectors.toList()))
                .build();
    }
}
