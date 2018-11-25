package me.immathan.stocker

import android.content.Context
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.immathan.stocker.cache.CacheSettings
import me.immathan.stocker.internal.*

/**
 * Actual implementation of [Stocker].
 * @author Mathan on 23/11/18
 */
class StockerImpl(val context: Context, cacheSettings: CacheSettings) : StockerProxy {

    private val assetManager = AssetManager(context, cacheSettings)
    private val imageLoader = ImageLoader(assetManager)

    companion object {
        private val TAG = StockerImpl::class.java.simpleName!!
    }

    override fun fetch(url: String, responseHandler: ResponseHandler): Request {
        val cacheListener = getCacheListener(responseHandler)
        assetManager.fetch(url, cacheListener)
        return Request(url, cacheListener)
    }

    private fun getCacheListener(responseHandler: ResponseHandler): CacheListener {
        return { _, status, response ->
            val responseObject : Response
            responseObject = if(response != null) {
                Response(status, response as ByteArray?)
            } else {
                val error = StockerError("Download failed")
                Response(false, error = error)
            }
            val result = Result(responseObject)
            GlobalScope.launch(Dispatchers.Main) {
                responseHandler(result)
            }
        }
    }

    override fun cancel(request: Request) {
        assetManager.cancel(request)
    }

    override fun bind(url: String, imageVIew: ImageView) {
        imageLoader.bind(url, imageVIew)
    }

}