package me.immathan.stockersample.network

import me.immathan.stockersample.model.PinModel
import retrofit2.Call
import retrofit2.http.GET

/**
 * @author Mathan on 24/11/18
 */
interface ApiInterface {

    @GET("wgkJgazE")
    fun getPins(): Call<List<PinModel>>

}