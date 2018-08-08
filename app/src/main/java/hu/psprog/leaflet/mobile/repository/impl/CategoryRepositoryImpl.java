package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.api.rest.response.category.CategoryListDataModel;
import hu.psprog.leaflet.bridge.service.CategoryBridgeService;
import hu.psprog.leaflet.mobile.model.CategoryList;
import hu.psprog.leaflet.mobile.repository.CategoryRepository;
import hu.psprog.leaflet.mobile.repository.conversion.impl.CategoryListConverter;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Peter Smith
 */
@Singleton
public class CategoryRepositoryImpl implements CategoryRepository {

    private ObservableFactory observableFactory;
    private CategoryBridgeService categoryBridgeService;
    private CategoryListConverter categoryListConverter;

    @Inject
    public CategoryRepositoryImpl(ObservableFactory observableFactory, CategoryBridgeService categoryBridgeService,
                                  CategoryListConverter categoryListConverter) {
        this.observableFactory = observableFactory;
        this.categoryBridgeService = categoryBridgeService;
        this.categoryListConverter = categoryListConverter;
    }

    @Override
    public Observable<CategoryList> getPublicCategories() {
        return observableFactory.create(() -> {
            CategoryListDataModel response = categoryBridgeService.getPublicCategories();
            return categoryListConverter.convert(response);
        });
    }
}
