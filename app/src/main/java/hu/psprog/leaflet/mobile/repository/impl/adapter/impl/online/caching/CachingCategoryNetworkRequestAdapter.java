package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching;

import hu.psprog.leaflet.mobile.model.CategoryList;
import hu.psprog.leaflet.mobile.repository.impl.adapter.CategoryAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.dao.CategoryDAO;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.room.database.LeafletLocalCacheDatabase;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * {@link CategoryAdapter} implementation to store an online call's response in the local cache.
 *
 * @author Peter Smith
 */
@Singleton
public class CachingCategoryNetworkRequestAdapter implements CategoryAdapter {

    private CategoryAdapter categoryNetworkRequestAdapter;
    private CategoryDAO categoryDAO;

    @Inject
    public CachingCategoryNetworkRequestAdapter(@Named("categoryNetworkRequestAdapter") CategoryAdapter categoryNetworkRequestAdapter,
                                                LeafletLocalCacheDatabase leafletLocalCacheDatabase) {
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
