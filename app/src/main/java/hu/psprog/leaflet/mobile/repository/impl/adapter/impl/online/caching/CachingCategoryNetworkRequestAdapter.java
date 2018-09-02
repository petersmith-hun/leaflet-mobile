package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching;

import hu.psprog.leaflet.mobile.model.CategoryList;
import hu.psprog.leaflet.mobile.repository.impl.adapter.CategoryAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.CategoryDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.CategoryNetworkRequestAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link CategoryAdapter} implementation to store an online call's response in the local cache.
 *
 * @author Peter Smith
 */
@Singleton
public class CachingCategoryNetworkRequestAdapter implements CategoryAdapter {

    private CategoryNetworkRequestAdapter categoryNetworkRequestAdapter;
    private CategoryDAO categoryDAO;

    @Inject
    public CachingCategoryNetworkRequestAdapter(CategoryNetworkRequestAdapter categoryNetworkRequestAdapter, LeafletLocalCacheDatabase leafletLocalCacheDatabase) {
        this.categoryNetworkRequestAdapter = categoryNetworkRequestAdapter;
        this.categoryDAO = leafletLocalCacheDatabase.categoryDAO();
    }

    @Override
    public CategoryList getPublicCategories() {

        CategoryList categoryList = categoryNetworkRequestAdapter.getPublicCategories();
        categoryDAO.insertAll(categoryList.getCategories());

        return categoryList;
    }
}
