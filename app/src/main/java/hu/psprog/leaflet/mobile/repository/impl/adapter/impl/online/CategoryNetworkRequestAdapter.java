package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online;

import hu.psprog.leaflet.mobile.model.CategoryList;
import hu.psprog.leaflet.mobile.repository.conversion.impl.CategoryListConverter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.CategoryAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client.CategoryRESTClient;
import hu.psprog.leaflet.mobile.util.logging.LogUtility;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link CategoryAdapter} implementation for online data sources.
 * This implementation retrieves data from Leaflet backend via Bridge.
 *
 * @author Peter Smith
 */
@Singleton
public class CategoryNetworkRequestAdapter extends AbstractBaseNetworkRequestAdapter implements CategoryAdapter {

    private static final String LOG_TAG = "category_adapter::online";

    private CategoryRESTClient categoryRESTClient;
    private CategoryListConverter categoryListConverter;

    @Inject
    public CategoryNetworkRequestAdapter(CategoryRESTClient categoryRESTClient, CategoryListConverter categoryListConverter) {
        this.categoryRESTClient = categoryRESTClient;
        this.categoryListConverter = categoryListConverter;
    }

    @Override
    public CategoryList getPublicCategories() {

        LogUtility.debug(LOG_TAG, "Requesting public categories from API service");

        return callBackend(
                () -> categoryRESTClient.getPublicCategories(),
                categoryListConverter::convert);
    }
}
