package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.client;

import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.document.DocumentDataModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * REST client interface for document related operations.
 *
 * @author Peter Smith
 */
public interface DocumentRESTClient {

    /**
     * Returns details detailed information of document identified by given link (for visitors).
     *
     * @param link link to identify document
     * @return identified document
     */
    @GET("/documents/link/{link}")
    Call<WrapperBodyDataModel<DocumentDataModel>> getDocumentByLink(@Path("link") String link);
}
