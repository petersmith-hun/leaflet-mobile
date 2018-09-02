package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline;

import hu.psprog.leaflet.mobile.model.CategoryList;
import hu.psprog.leaflet.mobile.repository.impl.adapter.CategoryAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.CategoryDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;
import hu.psprog.leaflet.mobile.util.logging.LogUtility;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link CategoryAdapter} implementation for offline data sources.
 * This implementation retrieves data from Room-based local caching solution.
 *
 * @author Peter Smith
 */
@Singleton
public class CategoryLocalCacheAdapter implements CategoryAdapter {

    private static final String LOG_TAG = "category_adapter::offline";

    private CategoryDAO categoryDAO;

    @Inject
    public CategoryLocalCacheAdapter(LeafletLocalCacheDatabase leafletLocalCacheDatabase) {
        this.categoryDAO = leafletLocalCacheDatabase.categoryDAO();
    }

    @Override
    public CategoryList getPublicCategories() {

        LogUtility.debug(LOG_TAG, "Retrieving public categories from local cache");
        return CategoryList.getBuilder()
                .withCategories(categoryDAO.getAllCategories())
                .build();
    }
}
