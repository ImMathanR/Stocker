package me.immathan.stocker

import android.content.Context
import me.immathan.stocker.cache.CacheSettings
import me.immathan.stocker.internal.Request
import me.immathan.stocker.utils.Logger

/**
 *
 * Stocker is an Effective Asset download library which can download images, pdf, documents, json
 * documents and caches the resources(currently just the memory cache. Also, create the [Stocker] instance
 * in the [onCreate] of the [Activity] or [Application] class.
 * <br><br>
 * [Stocker] instance can be created with the help of the builder class [Stocker.Builder]
 * and also you can supply the [CacheSettings] to be used.
 *
 * @author Mathan on 23/11/18
 */
class Stocker private constructor(builder : Stocker.Builder) {

    /**
     * Facade pattern to hide all the details of the implementations
     */
    companion object {
        private var stockerProxy: StockerProxy? = null
        private val TAG = Stocker::class.java.simpleName!!
    }

    init {
        if(stockerProxy == null) {
            stockerProxy = StockerImpl(builder.context, builder.cacheSettings!!)
        }
    }

    /**
     * To download an image with the callback [ResponseHandler]
     */
    fun fetch(url: String, responseHandler: ResponseHandler): Request {
        Logger.i(TAG, "Fetch url: $url")
        return stockerProxy!!.fetch(url, responseHandler)
    }

    /**
     * To cancel the [Request] received from the [Stocker.fetch] method.
     */
    fun cancel(request: Request) {
        Logger.i(TAG, "Cancel request with url: ${request.url}")
        stockerProxy!!.cancel(request)
    }

    /**
     * Builder method to easily construct the [Stocker] instance with configurations
     */
    class Builder(val context: Context) {

        internal var cacheSettings: CacheSettings? = null

        /**
         * To setup Cache settings
         */
        fun cacheSettings(cacheSettings: CacheSettings) : Builder {
            this.cacheSettings = cacheSettings
            return this
        }

        fun build() : Stocker {
            return Stocker(this)
        }
    }
}