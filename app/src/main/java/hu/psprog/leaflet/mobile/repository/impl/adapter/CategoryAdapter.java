package hu.psprog.leaflet.mobile.repository.impl.adapter;

import hu.psprog.leaflet.mobile.model.CategoryList;

/**
 * Adapter to provide category information from different sources.
 *
 * @author Peter Smith
 */
public interface CategoryAdapter {

    /**
     * Returns list of public categories wrapped as {@link CategoryList}.
     *
     * @return list of public categories as {@link CategoryList}
     */
    CategoryList getPublicCategories();
}
