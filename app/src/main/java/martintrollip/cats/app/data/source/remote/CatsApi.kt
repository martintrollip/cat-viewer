package martintrollip.cats.app.data.source.remote

import kotlinx.coroutines.Deferred
import martintrollip.cats.app.data.model.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApi {

    @GET("get")
    fun getImages(@Query("format") format: String = "json",
                             @Query("results_per_page") results_per_page: Int = 100,
                             @Query("size") size: String = "small",
                             @Query("type") type: String = "png",
                             @Query("page") page: Int = 1
    ): Deferred<Response<List<Cat>>>
}