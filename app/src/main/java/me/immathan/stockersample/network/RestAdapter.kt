package me.immathan.stockersample.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Mathan on 20/11/18
 */
class RestAdapter
() {

    companion object {
        private const val API_BASE = " http://pastebin.com/raw/"
    }

    private val httpClient: OkHttpClient

    val apiInterface: ApiInterface

    init {
        val builder = OkHttpClient.Builder()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)

        httpClient = builder.build()

        val retrofit = Retrofit.Builder()
                .client(httpClient)
                .baseUrl(API_BASE)
                .addConverterFactory(GsonConverterFactory.create()).build()
        apiInterface = retrofit.create(ApiInterface::class.java)
    }

}