package me.immathan.stocker

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.immathan.stocker.cache.CacheSettings
import me.immathan.stocker.internal.Response
import me.immathan.stocker.internal.Result
import me.immathan.stocker.internal.StockerError
import me.immathan.stocker.utils.Logger

/**
 * Actual implementation of [Stocker].
 * @author Mathan on 23/11/18
 */
class StockerImpl(val context: Context, cacheSettings: CacheSettings) : StockerProxy {

    private val assetManager = AssetManager(context, cacheSettings)

    companion object {
        private val TAG = StockerImpl::class.java.simpleName!!
    }

    override fun fetch(url: String, responseHandler: ResponseHandler) {
        GlobalScope.launch(Dispatchers.IO) {
            assetManager.fetch(url) { _, status, response ->
                val responseObject : Response
                responseObject = if(response != null) {
                    Response(status, response as ByteArray?)
                } else {
                    val error = StockerError("Download failed")
                    Response(false, error = error)
                }
                val result = Result(responseObject)
                Logger.d(TAG, "Before result: ${Thread.currentThread().name}")
                GlobalScope.launch(Dispatchers.Main) {
                    Logger.d(TAG, "Result main: ${Thread.currentThread().name}")
                    responseHandler(result)
                }
            }
        }
    }
}