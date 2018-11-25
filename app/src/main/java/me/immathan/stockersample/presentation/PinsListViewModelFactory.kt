package me.immathan.stockersample.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.immathan.stockersample.domain.FetchPinsUseCase

/**
 * @author Mathan on 25/11/18
 */
class PinsListViewModelFactory(private val fetchPinsUseCase: FetchPinsUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PinsListViewModel::class.java)) {
            return PinsListViewModel(fetchPinsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}