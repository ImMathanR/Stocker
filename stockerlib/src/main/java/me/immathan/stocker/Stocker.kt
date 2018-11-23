package me.immathan.stocker

import android.content.Context
import me.immathan.stocker.cache.CacheSettings
import me.immathan.stocker.internal.Request

/**
 * @author Mathan on 23/11/18
 */
class Stocker private constructor(builder : Stocker.Builder) {

    /**
     * Facade pattern to hide all the details of the implementations
     */
    companion object {
        private var stockerProxy: StockerProxy? = null
    }

    init {
        if(stockerProxy == null) {
            stockerProxy = StockerImpl(builder.context, builder.cacheSettings!!)
        }
    }

    fun fetch(url: String, responseHandler: ResponseHandler) {
        stockerProxy!!.fetch(url, responseHandler)
    }

    fun cancel(request: Request) {
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
        public fun cacheSettings(cacheSettings: CacheSettings) : Builder {
            this.cacheSettings = cacheSettings
            return this
        }

        public fun build() : Stocker {
            return Stocker(this)
        }
    }
}