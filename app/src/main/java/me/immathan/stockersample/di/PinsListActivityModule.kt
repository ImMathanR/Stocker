package me.immathan.stockersample.di

import android.support.v4.app.FragmentActivity
import me.immathan.stockersample.domain.FetchPinsUseCase
import me.immathan.stockersample.network.ApiInterface
import me.immathan.stockersample.network.RestAdapter
import me.immathan.stockersample.presentation.PinsListViewModel
import me.immathan.stockersample.presentation.PinsListViewModelFactory

/**
 * @author Mathan on 25/11/18
 */
interface PinsListActivityModule {

    fun getPinsViewModelFactory(fetchPinsUseCase: FetchPinsUseCase): PinsListViewModelFactory

    fun getPinsViewModel(fragmentActivity: FragmentActivity, pinsUseCase: FetchPinsUseCase): PinsListViewModel

    fun getFetchPinsUseCas(apiInterface: ApiInterface) :FetchPinsUseCase

}