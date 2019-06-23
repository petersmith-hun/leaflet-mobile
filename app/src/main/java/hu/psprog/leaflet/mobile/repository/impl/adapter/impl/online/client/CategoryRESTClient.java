package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client;

import hu.psprog.leaflet.api.rest.response.category.CategoryListDataModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * REST client interface for category related operations.
 *
 * @author Peter Smith
 */
public interface CategoryRESTClient {

    /**
     * Returns list of public categories.
     *
     * @return list of public categories
     */
    @GET("/categories/public")
    Call<CategoryListDataModel> getPublicCategories();
}
