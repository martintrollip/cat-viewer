package martintrollip.cats.app.data.source.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CatsApiService {

    private val apiClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().client(apiClient)
                .baseUrl("https://api.thecatapi.com/api/images/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    val catsApi: CatsApi = getRetrofit().create(CatsApi::class.java)
}