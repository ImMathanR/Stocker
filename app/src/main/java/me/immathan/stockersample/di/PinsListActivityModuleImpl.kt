package me.immathan.stockersample.di

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import me.immathan.stockersample.domain.FetchPinsUseCase
import me.immathan.stockersample.network.ApiInterface
import me.immathan.stockersample.presentation.PinsListViewModel
import me.immathan.stockersample.presentation.PinsListViewModelFactory

/**
 * @author Mathan on 25/11/18
 */
class PinsListActivityModuleImpl : PinsListActivityModule {

    override fun getPinsViewModelFactory(fetchPinsUseCase: FetchPinsUseCase): PinsListViewModelFactory {
        return PinsListViewModelFactory(fetchPinsUseCase)
    }

    override fun getPinsViewModel(fragmentActivity: FragmentActivity, pinsUseCase: FetchPinsUseCase): PinsListViewModel {
        return ViewModelProviders.of(fragmentActivity, getPinsViewModelFactory(pinsUseCase)).get(PinsListViewModel::class.java)
    }

    override fun getFetchPinsUseCas(apiInterface: ApiInterface): FetchPinsUseCase {
        return FetchPinsUseCase(apiInterface)
    }


}