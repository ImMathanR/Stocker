package me.immathan.stockersample.di

import android.content.Context
import me.immathan.stocker.Stocker
import me.immathan.stockersample.network.ApiInterface

/**
 * @author Mathan on 24/11/18
 */
interface AppModule {

    val context: Context
    val apiInterface: ApiInterface
    val stocker: Stocker

}