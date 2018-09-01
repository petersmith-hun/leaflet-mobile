package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.mobile.model.CategoryList;
import hu.psprog.leaflet.mobile.repository.CategoryRepository;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.CategoryLocalCacheAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching.CachingCategoryNetworkRequestAdapter;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Predicate;

/**
 * Implementation of {@link CategoryRepository}.
 *
 * @author Peter Smith
 */
@Singleton
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final Predicate<CategoryList> CATEGORY_LIST_FALLBACK_PREDICATE = categoryList -> categoryList.getCategories().isEmpty();

    private OfflineFirstCallFactory offlineFirstCallFactory;
    private CategoryLocalCacheAdapter categoryLocalCacheAdapter;
    private CachingCategoryNetworkRequestAdapter cachingCategoryNetworkRequestAdapter;

    @Inject
    public CategoryRepositoryImpl(OfflineFirstCallFactory offlineFirstCallFactory, CategoryLocalCacheAdapter categoryLocalCacheAdapter,
                                  CachingCategoryNetworkRequestAdapter cachingCategoryNetworkRequestAdapter) {
        this.offlineFirstCallFactory = offlineFirstCallFactory;
        this.categoryLocalCacheAdapter = categoryLocalCacheAdapter;
        this.cachingCategoryNetworkRequestAdapter = cachingCategoryNetworkRequestAdapter;
    }

    @Override
    public Observable<CategoryList> getPublicCategories() {
        return offlineFirstCallFactory.create(
                () -> categoryLocalCacheAdapter.getPublicCategories(),
                () -> cachingCategoryNetworkRequestAdapter.getPublicCategories(),
                CATEGORY_LIST_FALLBACK_PREDICATE);
    }
}
