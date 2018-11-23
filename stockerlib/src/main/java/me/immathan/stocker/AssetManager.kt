package me.immathan.stocker

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.immathan.stocker.cache.*
import me.immathan.stocker.network.Downloader
import me.immathan.stocker.utils.Logger
import java.util.*

/**
 * @author Mathan on 23/11/18
 */
class AssetManager(context: Context, private val cacheSettings: CacheSettings) {

    /**
     * List of Caches that will added based on the Configuration selected
     */
    private val cacheMakers = LinkedList<Cache<Any, Any>>()

    private val downloadCallbacks = hashMapOf<String, MutableList<CacheListener>>()

    private val downloader = Downloader()

    companion object {
        private val TAG = AssetManager::class.java.simpleName!!
    }

    init {
        when(cacheSettings.getCacheStrategy()) {
            CacheStrategy.NO_CACHE -> {
                Logger.d(TAG, "No caching added")
            }
            CacheStrategy.MEM_CACHE -> {
                Logger.d(TAG, "Preparing Memory Cache")
                cacheMakers.add(StockerMemCache.getInstance(context, cacheSettings.getMemCacheSize()))
            }
            CacheStrategy.DISK_CACHE -> { // This is assuming that if the disk cache is enabled
                // Memory cache can be added as an additional caching layer
                Logger.d(TAG, "Preparing Disk cache and Mem Cache")
                cacheMakers.add(StockerMemCache.getInstance(context, cacheSettings.getMemCacheSize()))
                cacheMakers.add(StockerDiskCache.getInstance(context, cacheSettings.getMemCacheSize()))
                // In case Disk cache also added in the feature
            }
        }
    }

    fun fetch(url: String, cacheListener: CacheListener) {
        synchronized(downloadCallbacks) {
            val cacheListeners = downloadCallbacks[url]
            if(cacheListeners != null) {
                cacheListeners.add(cacheListener)
                downloadCallbacks[url] = cacheListeners
                return
            } else {
                downloadCallbacks[url] = mutableListOf(cacheListener)
            }
        }

        Logger.d(TAG, "Fetch before: ${Thread.currentThread().name}")
        GlobalScope.launch(Dispatchers.IO) {
            Logger.d(TAG, "Fetch inside launch : ${Thread.currentThread().name}")
            for (cache in cacheMakers) {
                Logger.d(TAG, "Total size: ${cache.sizeOf()} and available size: ${cache.remainingSize()}")
                val result = cache.get(url).await()
                if(result != null) {
                    Logger.d(TAG, "Cache hit: ${cache.name()}")
                    sendAssetCacheResponse(url, true, result)
                    return@launch
                } // This means the Cache is not available
            }
            val response = downloader.download(url)
            if (response == null) {
                sendAssetCacheResponse(url, false, null)
                return@launch
            } else {
                if(cacheSettings.getCacheStrategy() == CacheStrategy.NO_CACHE) {
                    sendAssetCacheResponse(url, true, response)
                    return@launch
                }
                // Adding the asset to the different cache
                for(cache in cacheMakers) {
                    cache.set(url, response).await()
                }
                for (cache in cacheMakers) {
                    val result = cache.get(url).await()
                    if(result != null) {
                        sendAssetCacheResponse(url, true, result)
                    }
                }
            }
        }
    }

    private fun sendAssetCacheResponse(url: String, status: Boolean, result: Any?) {
        synchronized(downloadCallbacks) {
            val cacheListeners = downloadCallbacks[url]
            if(cacheListeners != null) {
                for(cacheListener in cacheListeners) {
                    cacheListener(url, status, result)
                }
            }
            downloadCallbacks.remove(url)
        }
    }
}

typealias CacheListener = (url: String, status: Boolean, value: Any?) -> Unit
