package hu.psprog.leaflet.mobile.repository;

import hu.psprog.leaflet.mobile.model.CategoryList;
import io.reactivex.Observable;

/**
 * Repository interface for accessing category data.
 * As accessing data might require network communication, every repository method
 * returns requested data wrapped as {@link Observable}.
 *
 * @author Peter Smith
 */
public interface CategoryRepository extends Repository {

    /**
     * Returns list of public categories.
     *
     * @return list of public categories in {@link CategoryList} object wrapped as {@link Observable}
     */
    Observable<CategoryList> getPublicCategories();
}
