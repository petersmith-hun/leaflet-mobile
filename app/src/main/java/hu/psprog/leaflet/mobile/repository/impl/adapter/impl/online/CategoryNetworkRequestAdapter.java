package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online;

import hu.psprog.leaflet.api.rest.response.category.CategoryListDataModel;
import hu.psprog.leaflet.bridge.service.CategoryBridgeService;
import hu.psprog.leaflet.mobile.model.CategoryList;
import hu.psprog.leaflet.mobile.repository.conversion.impl.CategoryListConverter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.CategoryAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Peter Smith
 */
@Singleton
public class CategoryNetworkRequestAdapter extends BridgeCallerNetworkRequestAdapter implements CategoryAdapter {

    private CategoryBridgeService categoryBridgeService;
    private CategoryListConverter categoryListConverter;

    @Inject
    public CategoryNetworkRequestAdapter(CategoryBridgeService categoryBridgeService, CategoryListConverter categoryListConverter) {
        this.categoryBridgeService = categoryBridgeService;
        this.categoryListConverter = categoryListConverter;
    }

    @Override
    public CategoryList getPublicCategories() {

        return callBridge(() -> {
            CategoryListDataModel response = categoryBridgeService.getPublicCategories();
            return categoryListConverter.convert(response);
        });
    }
}
