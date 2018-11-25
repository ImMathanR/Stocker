package me.immathan.stockersample.domain

import me.immathan.stockersample.model.PinModel
import me.immathan.stockersample.network.ApiInterface
import retrofit2.Call

/**
 * @author Mathan on 25/11/18
 */
class FetchPinsUseCase(private val apiInterface: ApiInterface) {

    fun getPins(): Call<List<PinModel>> {
        return apiInterface.getPins()
    }

}