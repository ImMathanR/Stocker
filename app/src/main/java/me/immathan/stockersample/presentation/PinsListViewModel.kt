package me.immathan.stockersample.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import me.immathan.stockersample.domain.FetchPinsUseCase
import me.immathan.stockersample.model.PinModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Mathan on 25/11/18
 */
class PinsListViewModel(private val fetchPinsUseCase: FetchPinsUseCase): ViewModel() {

    companion object {
        private val TAG = PinsListViewModel::class.java.simpleName!!
    }

    val pinsLoadLiveData = MutableLiveData<List<PinModel>>()

    val pinsErrorLiveData = MutableLiveData<String>()

    val loadingLiveData = MutableLiveData<Boolean>()

    fun getPins() {
        loadingLiveData.value = true

        fetchPinsUseCase.getPins().enqueue(object: Callback<List<PinModel>> {

            override fun onFailure(call: Call<List<PinModel>>?, t: Throwable?) {
                loadingLiveData.value = false
                pinsErrorLiveData.value = t?.message
            }

            override fun onResponse(call: Call<List<PinModel>>?, response: Response<List<PinModel>>?) {
                loadingLiveData.value = false
                pinsLoadLiveData.value = response?.body()
            }
        })
    }

}