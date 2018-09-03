package hu.psprog.leaflet.mobile.repository.impl;

import hu.psprog.leaflet.mobile.model.CategoryList;
import hu.psprog.leaflet.mobile.repository.CategoryRepository;
import hu.psprog.leaflet.mobile.repository.impl.adapter.CategoryAdapter;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Named;
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

    private CategoryAdapter categoryLocalCacheAdapter;
    private CategoryAdapter cachingCategoryNetworkRequestAdapter;
    private OfflineFirstCallFactory offlineFirstCallFactory;

    @Inject
    public CategoryRepositoryImpl(@Named("categoryLocalCacheAdapter") CategoryAdapter categoryLocalCacheAdapter,
                                  @Named("cachingCategoryNetworkRequestAdapter") CategoryAdapter cachingCategoryNetworkRequestAdapter,
                                  OfflineFirstCallFactory offlineFirstCallFactory) {
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
