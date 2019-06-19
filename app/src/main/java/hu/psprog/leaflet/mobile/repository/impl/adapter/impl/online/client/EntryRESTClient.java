package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client;

import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.entry.EntryListDataModel;
import hu.psprog.leaflet.api.rest.response.entry.ExtendedEntryDataModel;
import hu.psprog.leaflet.mobile.model.OrderBy;
import hu.psprog.leaflet.mobile.model.OrderDirection;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * REST client interface for entry related operations.
 *
 * @author Peter Smith
 */
public interface EntryRESTClient {

    /**
     * Returns basic information of given page of public entries.
     *
     * @param page page number (page indexing starts at 1)
     * @param limit number of entries on one page
     * @param orderBy order by {@link OrderBy.Entry} options
     * @param orderDirection order direction (ASC|DESC)
     * @return page of public entries
     */
    @GET("/entries/page/{page}")
    Call<WrapperBodyDataModel<EntryListDataModel>> getPageOfPublicEntries(@Path("page") int page,
                                                                          @Query("limit") int limit,
                                                                          @Query("orderBy") OrderBy.Entry orderBy,
                                                                          @Query("orderDirection") OrderDirection orderDirection);

    /**
     * Returns basic information of given page of public entries filtered by given category ID.
     *
     * @param categoryID category ID to filter by
     * @param page page number (page indexing starts at 1)
     * @param limit number of entries on one page
     * @param orderBy order by {@link OrderBy} options
     * @param orderDirection order direction (ASC|DESC)
     * @return page of public entries under given category
     */
    @GET("/entries/category/{id}/page/{page}")
    Call<WrapperBodyDataModel<EntryListDataModel>> getPageOfPublicEntriesByCategory(@Path("id") Long categoryID,
                                                                                    @Path("page") int page,
                                                                                    @Query("limit") int limit,
                                                                                    @Query("orderBy") OrderBy.Entry orderBy,
                                                                                    @Query("orderDirection") OrderDirection orderDirection);

    /**
     * Returns entry identified by given link.
     *
     * @param link link to identify entry
     * @return identified entry
     */
    @GET("/entries/link/{link}")
    Call<WrapperBodyDataModel<ExtendedEntryDataModel>> getEntryByLink(@Path("link") String link);
}
