package me.immathan.stockersample.di

import android.app.Application
import android.content.Context
import me.immathan.stocker.Stocker
import me.immathan.stocker.cache.CacheSettings
import me.immathan.stocker.cache.CacheStrategy
import me.immathan.stockersample.network.ApiInterface
import me.immathan.stockersample.network.RestAdapter

/**
 * @author Mathan on 25/11/18
 */
class AppModuleImpl(val application: Application): AppModule {

    override val apiInterface: ApiInterface
        get() = RestAdapter().apiInterface

    override val context: Context
        get() =  application.applicationContext

    override val stocker: Stocker
        get() = Stocker.Builder(context)
                    .cacheSettings(object: CacheSettings {
                        override fun getCacheStrategy(): CacheStrategy {
                            return CacheStrategy.MEM_CACHE
                        }
                    })
                    .build()
}